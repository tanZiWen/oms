package com.prosnav.oms.action;
/*package com.work.action.comm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
//import com.util.Tojson;
import com.work.dao.loginDao;

public class loginAction  extends ActionSupport implements ServletRequestAware,
ServletResponseAware, SessionAware{
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = -5961780771027189388L;
	HttpServletRequest req;
	HttpServletResponse resp;
	public void run(){
		//Tojson json = new Tojson();
		Map<String,Object> map = new HashMap<String, Object>();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//int count= loginDao.loginPwd(username, password);
		List<Map<String,Object>> list = loginDao.login(username, password);
		
		if(list.size()>0&&list!=null){
			Cookie c = new Cookie("cuno", list.get(0).get("userid").toString());
			c.setMaxAge(60*60);
			resp.addCookie(c);
			
			map.put("desc", "0");
			
		}else{
			map.put("desc", "1");
			map.put("msg", "未找到相关信息");
		}
		json.printJson(resp, json.objectToJson(map));
	}
	
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		resp=arg0;
	}

	
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		req=arg0;
	}

}
*/