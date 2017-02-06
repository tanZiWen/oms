package com.prosnav.oms.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.util.jdbcUtil;

public class LPAddDao {
	private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");
	
	public List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	String sql = "";
	
/*	public static int prod_allot(long prod_allot_id,String dept_name, Double dept_allot) {
		String sql = "insert into product_allot values(?,?,?)";
		return jt.update(sql,prod_allot_id, dept_name, dept_allot);
	}
*/

	public int add_LP(Map<String,Object> param)
	{
		String sql = "insert into LP_info(partner_com_name,lp_id,fund_type,"
				+ "prod_diffcoe,bus_license,legal_resp,fund_no,open_bank,"
				+ "bank_account,open_bank1,bank_account1,regit_addr,prod_remark,"
				+ "fund_start_fee,fund_bnf_allot,cor_com_fee,fund_type_fee,true_pay,"
				+ "gplp_pay,renew_period,first_delivery,invest_goal,invest_period,renew_year,invest_year,"
				+ "mana_decr,buy_decr,delay_decr,late_join_decr,invest_goal_decr,see_sale,area) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?)";
		
		return jt.update(sql,param.get("partner_com_name"),
				param.get("lp_id"),//param.get("partner_com_no"),
		param.get("fund_type"),param.get("prod_diffcoe"),param.get("bus_license"),
		param.get("legal_resp"),param.get("fund_no"),
		//param.get("pay_sum"),	param.get("pay_true"),
		param.get("open_bank"),param.get("bank_account"),
		param.get("open_bank1"),param.get("bank_account1"),param.get("regit_addr"),
		param.get("prod_remark"),param.get("fund_start_fee"),param.get("fund_bnf_allot"),
		param.get("cor_com_fee"),param.get("fund_type_fee"),param.get("true_pay"),
		param.get("gplp_pay"),param.get("renew_period"),param.get("first_delivery"),
		param.get("invest_goal"),param.get("invest_period"),param.get("renew_year"),param.get("invest_year"),
		param.get("mana_decr"),param.get("buy_decr"),param.get("delay_decr"),
		param.get("late_join_decr"),param.get("invest_goal_decr"),param.get("see_sale"),param.get("area"));
	}
	
/*	public int max_LPID(){
		sql="select max(lp_id) max_id from LP_info";
		int max_id=jt.queryForInt(sql);
		return max_id;
	}*/
	
	//关系表
	public int lp_Prod_Add(long lp_id, long prod_id,long email_id) {
		String sql = "insert into LP_prod_rela values(?,?,?)";
		return jt.update(sql, lp_id, prod_id,email_id);
	}
	
	//特殊属性
	public int[] spec_attr(ArrayList<Object[]> li) {
		String sql = "insert into prod_prop values(?,?)";
		return jt.batchUpdate(sql, li);
	}
}
