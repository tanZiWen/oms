package com.prosnav.core.jwt;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTSigner.Options;
import com.auth0.jwt.JWTVerifier;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.utils.ServletUtil;

public abstract class BasicJwtFilter implements Filter {

	private static final int JWT_AGE = 3600 * 24 * 7;
	private static final String AUTHORIZE_TOKEN = "authorize_token";
	
	private static byte[] CLIENT_SECRET;
	
	private String privateKey;
	
	private String loginUrl;
	private String alg;
	private String cid;
	protected String userUrl;
	private String exclude;
	protected String userKey;
	private List<String> skipUrls = new ArrayList<String>();
	
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}


	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain fc)
			throws IOException, ServletException {
		//System.out.println("进入outh");
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		String uri = req.getRequestURI();
		StringBuffer requestURL = req.getRequestURL();
		String queryString = req.getQueryString();
		String fullUrl = StringUtils.isEmpty(queryString) ? requestURL.toString() : requestURL.append("?").append(queryString).toString();
		String loginUrlWithTarget = loginUrl + "?target=" + URLEncoder.encode(fullUrl, "utf-8");
		req.setAttribute("portalLogin", loginUrl);
		
		if (isSkipped(uri)) {
			fc.doFilter(arg0, arg1);
			return;
		}
		String signedStr = getJwtFromRequest(req);
		if (StringUtils.isEmpty(signedStr)) {
			if (ServletUtil.isAjaxRequest(req)) {
				resp.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}
			resp.sendRedirect(loginUrlWithTarget);
			return;
		}
		Map<String, Object> claims = null;
		try {
			if ((claims = validateJwt(signedStr)) == null) {
				resp.sendRedirect(loginUrlWithTarget);
				return;
			}
		} catch (Exception e) {
			if (ServletUtil.isAjaxRequest(req)) {
				resp.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}
			resp.sendRedirect(loginUrlWithTarget);
			return;
		}
		String nSignedStr = refreshJWT(claims);
		claims.put("cid", cid);
		req.setAttribute(Constants.JWT, claims);
		Cookie cookie = new Cookie(AUTHORIZE_TOKEN, nSignedStr);
		String userName = (String)claims.get("aud");
		req.getSession().setAttribute(Constants.USER, loadUser(userName, nSignedStr));
		cookie.setHttpOnly(false);
		cookie.setPath(req.getContextPath());
		resp.addCookie(cookie);
		fc.doFilter(arg0, arg1);
	}
	
	protected abstract Object loadUser(String userName, String jwt);
	
	public String requestUser(String jwt){
		BufferedReader in = null;
		try {
			URL url = new URL(userUrl + "?code=" + jwt);
			URLConnection conn = url.openConnection();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String userStr = new String();
			String buf;
			while ((buf = in.readLine()) != null) {
				userStr += buf;
			}
			return userStr;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private String getJwtFromRequest(HttpServletRequest req) {
		String ah = req.getHeader("Authorization");
		if (!StringUtils.isEmpty(ah)) {
			if (ah.length() > 6 && "BEARER ".equals(ah.substring(0, 7).toUpperCase())) {
				return ah.substring(7);
			}
		}
		String jwt = (String) req.getParameter("token");
		if (!StringUtils.isEmpty(jwt)) {
			return jwt;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (AUTHORIZE_TOKEN.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	private Map<String, Object> validateJwt(String signedStr) throws ServletException {
		JWTVerifier jv = new JWTVerifier(CLIENT_SECRET);
		Map<String, Object> claims = null;
		try {
			claims = jv.verify(signedStr);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		if (claims.get("aud") == null) {
			return null;
		}
		long expireAt = (Integer)claims.get("exp");
		long now = System.currentTimeMillis() / 1000;
		if (now > expireAt) {
			return null;
		}
		return claims;
	}

	private String refreshJWT(Map<String, Object> claims) {
		Options op = new Options();
		op.setAlgorithm(Algorithm.valueOf(alg.toUpperCase()));
		JWTSigner signer = new JWTSigner(CLIENT_SECRET);
		claims.put("cid", cid);
		claims.put("exp", System.currentTimeMillis() / 1000 + JWT_AGE);
		return signer.sign(claims, op);
	}
	
	private void initPrivateKey() throws ServletException {
		InputStream in = null;
		try {
			in = new FileInputStream(privateKey);
			int len = in.available();
			byte[] buf = new byte[len];
			in.read(buf);
			CLIENT_SECRET = buf;
		} catch (IOException e) {
			throw new ServletException(e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw new ServletException(e);
			}
		}
	}
	
	private void initExcludeUrls() {
		if (StringUtils.isEmpty(exclude)) {
			return;
		}
		String[] urls = exclude.split(",");
		for (String u : urls) {
			skipUrls.add(u.trim());
		}
		return;
	}
	
	private boolean isSkipped(String url) {
		for (String skipUrl : skipUrls) {
			String regEx = "css|image|bootstrapcss|bootstrapjs|js"; //表示a或F   

			Pattern pat = Pattern.compile(regEx);   

			Matcher mat = pat.matcher(url);   

			boolean rs = mat.find();
			if (url.equals(skipUrl)||rs) {
				return true;
			}
		}
		return false;
	}

	public void init(FilterConfig arg0) throws ServletException {
		initParams(arg0);
		initPrivateKey();
		initExcludeUrls();
		initComponent();
	}
	protected abstract void initComponent();
	
	private void initParams(FilterConfig arg0){
		this.privateKey = arg0.getInitParameter("privateKey");
		this.loginUrl=arg0.getInitParameter("loginUrl");
		this.alg=arg0.getInitParameter("alg");
		this.cid=arg0.getInitParameter("cid");
		this.userUrl=arg0.getInitParameter("userUrl");
		this.exclude=arg0.getInitParameter("exclude");
		this.userKey = "ps:" + this.cid + ":user:";
	}
}
