package com.prosnav.core.jwt.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {
	public static boolean isAjaxRequest(HttpServletRequest request){
	    String header = request.getHeader("X-Requested-With");
	    return "XMLHttpRequest".equals(header) ? true:false;
	}
}
