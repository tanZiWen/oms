package com.prosnav.oms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.util.jdbcUtil;

public class testDao {
	
	 private static JdbcTemplate jdbcT = (JdbcTemplate) jdbcUtil
	            .getBean("template");
	 public static void test(String m){
			System.out.println(jdbcT);
			int a = jdbcT.update("insert into test values(?)","13");
			System.out.println(a);
		}
	 public static List<Map<String,Object>> tableSelect(int pagenum,int count){
		 int n = (pagenum-1)*count;
		 return jdbcT.queryForList("SELECT cust_id, cust_name, cust_type, cust_tel, cust_cell, cust_email, cust_wechat, cust_level, cust_belong "
		 		+ "FROM cust_indiv_info limit ?,?",n,count);
	 }
	 public static List<Map<String,Object>> tableSelect1(String cust_name){
		 return jdbcT.queryForList("SELECT * "
		 		+ "FROM cust_indiv_info where cust_name=?",cust_name);
	 }
	 public static List<Map<String,Object>> personSelect(int page,int count){
		 return jdbcT.queryForList("SELECT * "
		 		+ "FROM cust_indiv_info limit ? offset ? ",count,page);
	 }
	 public static int personCount(){
		 return jdbcT.queryForInt("select count(*) from cust_indiv_info ");
	 }
}
