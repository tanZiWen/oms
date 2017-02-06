package com.prosnav.oms.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.Function;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.loginDao;

public class AuthInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 7013816941154484816L;
	private String ignoredUrls;
	private List<String> ignoredUrlList;
	//Log log = LogFactory.getLog(this.getClass());
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//System.out.println("进入第二个拦截");
		String authKey = getAuthKey(invocation);
		/*System.out.println("authKey="+authKey);
		System.out.println("ingnor="+ignoredUrlList);*/
		Class clazz = invocation.getAction().getClass();
		Logger log = LoggerFactory.getLogger(clazz);
		//System.out.println("authKey===="+authKey);
		if(authKey==null||authKey=="")
			return "unauthorized";
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(Constants.USER);
		
		if(user==null){
			//System.out.println("user为空");
			return "unauthorized";
		}
		
		long role_id = 0;
		if(user.getRoleList().size()==0){
			//System.out.println("没有role——id");
			return "unauthorized";
		}
		String role_code = user.getRoleList().get(0).getCode();
		
		List<Map<String, Object>> list = loginDao.role(role_code);
		
		if(list!=null&&list.size()>0){
			role_id = (Long) list.get(0).get("role_id");
			
		}
		
		user.setRole_id(role_id);
		
		ServletActionContext.getRequest().getSession().setAttribute(Constants.USER, user);
		
		
		if (ignoredUrlList.contains(authKey)) {
			//System.out.println("进入忽视页面");
			return invocation.invoke();
		}
	 
		Map<String, Function> map = user.getFunctionMap();
		

		if (!map.keySet().contains(authKey)) {
			/*System.out.println(authKey);
			System.out.println(map.keySet());*/
			
			return "unauthorized";
		}
		
		return invocation.invoke();
	}
	@SuppressWarnings({"unchecked"})
	private String getAuthKey(ActionInvocation invocation) throws Exception {
		@SuppressWarnings("rawtypes")
	
		Class clazz = invocation.getAction().getClass();
		String methodName = invocation.getProxy().getMethod();
		String funcAction = clazz.getName() + "." + methodName;
		//System.out.println("get"+funcAction);
		String funcKey = null;
		try
		{
		   //System.out.println(((FuncInfo)Constants.cache.get(funcAction)));
		   funcKey=((FuncInfo)Constants.cache.get(funcAction)).getFuncKey();
	    }
		catch(Exception e)
		{}
		return funcKey;
	}
	
	public String getIgnoredUrls() {
		return ignoredUrls;
	}
	public void setIgnoredUrls(String ignoredUrls) {
		this.ignoredUrls = ignoredUrls;
	}
	public void init() {
		initIgnoredList();
	}
	private void initIgnoredList() {
		ignoredUrlList = new ArrayList<String>();
		if (ignoredUrls == null) {
			return;
		}
		for (String url : this.ignoredUrls.split(",")) {
			ignoredUrlList.add(url.trim());
		}
	}

}
