package com.prosnav.oms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.util.jdbcUtil;

public class AuthorityDao {
	private  JdbcTemplate jt = (JdbcTemplate) jdbcUtil
            .getBean("template");
	public  int authority(String role,String url){
		String sql ="SELECT count(*) FROM aut_info aut,func_info func,aut_func af " 
				 +"where aut.aut_id=af.aut_id and func.func_id=af.func_id and aut.aut_code=? and func.func_url=? ";
		return jt.queryForInt(sql, role,url);
		
	}
	
}
