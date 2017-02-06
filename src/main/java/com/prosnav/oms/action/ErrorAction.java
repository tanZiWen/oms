package com.prosnav.oms.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.util.Escape;
import com.prosnav.oms.util.sendMail;

public class ErrorAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	public String run(){
		
		try{
			req.setCharacterEncoding("UTF-8");
		//req.setCharacterEncoding("utf-8");
		String msg = req.getParameter("msg");
		String url = req.getParameter("url");
		String code = req.getParameter("code");
		msg = Escape.unescape(msg);
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		req.setAttribute("code", code);
		return "error";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		
	}
}
