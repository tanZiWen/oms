package com.prosnav.oms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.util.jdbcUtil;

public class RegJdDao {
	private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil
            .getBean("template");
	public List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

	public int[] prod_progress(ArrayList<Object[]> li){
		
		String sql="INSERT INTO public.product_progress(product_p_id,"
				+ " prod_id, prod_allot_id, cust_name, sale_name,"
				+ " dept_dist, cust_money, deal_odds, remarks,sum_excp) "
				+ "VALUES (?, ?, ?, ?, ?, ? ,?, ?, ?, ?)";
		return jt.batchUpdate(sql, li);
		
	}
}
