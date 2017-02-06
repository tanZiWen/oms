package com.prosnav.oms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.util.jdbcUtil;

public class BonusDao {
	private JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");
	/**
	 * 查询gp列表
	 * @return
	 */
	public List<Map<String, Object>> bonus() {
		String sql = "SELECT prod_rs_id, order_id, prod_id, pllot_money, return_coe, return_date,"
				+ "prod_flag, cust_name, sale_id FROM public.product_issue";
		List<Map<String, Object>> list = jt.queryForList(sql);
		return list;
	}
}
