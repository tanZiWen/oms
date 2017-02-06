package com.prosnav.oms.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Ctx {
	private static WebApplicationContext ctx;
	
	public static ApplicationContext springContext(String name){
		ApplicationContext ctx = new ClassPathXmlApplicationContext(name);
		
		return ctx;
	}
	
	public static ApplicationContext springContext(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-bean.xml");		
		return ctx;
	}
	public static Object get(String name){
	
		return ctx.getBean(name);
	}
	
	public static Object get(String name,HttpServletRequest req){
		ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
		return ctx.getBean(name);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> c ,HttpServletRequest req) {
		ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
		return (T) get(c.getSimpleName());
	}

	public static void setCtx(WebApplicationContext c) {
		ctx = c;
	
	//	System.out.println(c.getBean("dataSource"));
	}
	
	/*public static DataSource getDataSource() {
		System.out.println(ctx);
		DataSource ds = (DataSource) Ctx.get("dataSource");
		return ds;
	}*/

	/*
	 * public static String getRootPath() { URL url =
	 * Ctx.class.getClassLoader().getResource(""); String file = url.getFile();
	 * String end = "WEB-INF/classes/"; String rootPath = null;
	 * if(file.endsWith(end)){ rootPath =
	 * file.substring(0,file.length()-end.length()); } return rootPath; }
	 */

	static public String getRootPath() {
		return ctx.getServletContext().getRealPath("/");
	}
	


}
