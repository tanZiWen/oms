package com.prosnav.oms.util;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServlet;

import org.springframework.jdbc.core.JdbcTemplate;

public class mailCache extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000877788257275848L;
	public static  String host =null;
	
	public static  String user =null;
	public static  String pwd =null;
	public static  String from =null; 
	public static  String imaphost=null;
	public void init(){
		JdbcTemplate jt = (JdbcTemplate) jdbcUtil
	            .getBean("template");
		List<Map<String,Object>> list = jt.queryForList( "select mail_user_host,mail_user_username,mail_user_pwd,mail_user_imaphost,mail_user_fromaddr "
				+ "from mail_user_info where type='oa'");
		user=(String)list.get(0).get("mail_user_username");
		pwd=(String)list.get(0).get("mail_user_pwd");
		from=(String)list.get(0).get("mail_user_fromaddr");
		imaphost=(String)list.get(0).get("mail_user_imaphost");
		host=(String)list.get(0).get("mail_user_host");
	}
	public void destroy(){

	}

}
