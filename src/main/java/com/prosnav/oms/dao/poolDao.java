package com.prosnav.oms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.util.jdbcUtil;

public class poolDao {
	private JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");
	//查询已签单的客户
	
	public List<Map<String, Object>> sign(){
		
		String sql = "SELECT a.c_t,b.id,c.cust_name,c.cust_cell "
				+ " from (SELECT max(entry_time) c_t,cust_no from order_info GROUP BY cust_no) a "
				+ " left JOIN bill_info b on a.cust_no=b.id "
				+ " left join cust_info c on b.id=c.cust_id  "
				+ " where b.effect_sign='1' ";
		return jt.queryForList(sql);
	} 
//查询未签单客户	
public List<Map<String, Object>> Nosign(){
		
		String sql = "SELECT t1.id,t2.cust_reg_time,t2.cust_name,t2.cust_cell "
				+ " FROM public.bill_info t1 "
				+ "left join cust_info t2 on t1.id = t2.cust_id "
				+ "where t1.effect_sign='0'  ";
		return jt.queryForList(sql);
	} 

/**
 * 把客户扔进公共池
 * @param cust
 * @return
 */
public void throwpool(long cust_id) {
	String sql = "UPDATE public.sales_cust_rel set  "
			+ "cust_belong_state='0' WHERE cust_id=?";
	
	jt.update(sql, cust_id);

}
/**
 * 根据客户id查询
 * @param cust_id
 * @return
 */
public List<Map<String, Object>> sale_mail(long cust_id){
	String sql = "select t2.email from sales_cust_rel t1 "
			+ "left join upm_user t2 on t1.sales_id = t2.user_id "
			+ "where t1.cust_id=?";
	return jt.queryForList(sql,cust_id);
}
/**
 * 查询销售是否在职
 * @return
 */

public List<Map<String, Object>> desert_job() {
	String sql = "select cust_id from upm_user u,sales_cust_rel s where u.user_id=s.sales_id and status !='ok'";
	return jt.queryForList(sql);
}

}
