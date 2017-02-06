package com.prosnav.oms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.util.jdbcUtil;

public class GPInfoDao {
	private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil
            .getBean("template");
	public List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	String sql="";
	
	/**
	 * 查询全量GP信息
	 * @return
	 */
	public List<Map<String,Object>> GPInfo(){
		sql="select GP_id,GP_name from gp_info";
		list=jt.queryForList(sql);
		return list;
	}
	
	public int update_prod_gp(long gp_id, long prod_id,long gp_id_origin){
		String sql="update GP_prod_rela set gp_id=? where prod_id=? and gp_id=?";
		int result = jt.update(sql,gp_id,prod_id,gp_id_origin);
		return result;
	}
}
