package com.prosnav.oms.util;

import java.util.List;
import java.util.Map;

public class test {
	
	public static void main(String[] args){
		jdbcUtil j = new jdbcUtil();
		String sql = "select * from query_product_info(?,?) as  a(prod_name varchar,prod_type varchar,cor_org varchar,invest_goal varchar,pay_sum numeric,pay_true numeric,s_raise_date date,prod_currency varchar)";
		Object[] o = {10,"2"};
		List<Map<String, Object>> list = j.test(sql, o);
		System.out.println(list);
		
	}

}

