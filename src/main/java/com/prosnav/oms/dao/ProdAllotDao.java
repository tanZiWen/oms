package com.prosnav.oms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.util.jdbcUtil;

public class ProdAllotDao {
	private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil
            .getBean("template");
	public List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	String sql="";
	
	
	public int prod_allot(long prod_allot_id,String dept_name,Double dept_allot,long prod_id){
		
		String sql="insert into product_allot values(?,?,?,?)";
		return jt.update(sql,prod_allot_id,dept_name,dept_allot,prod_id);
	} 
	
	
	public int prod_update_allot(Double dept_allot,String dept_name,long prod_id){
		String sql = "update product_allot set dept_allot=? where dept_no=? and prod_id = ?";
		return jt.update(sql,dept_allot,dept_name,prod_id);
	} 
	

	public int prod_count_allot(long prod_id,String dept_name){
		String sql = "select count(1) from product_allot where prod_id=? and dept_no=?";
		return jt.queryForInt(sql,prod_id,dept_name);
	}
	
	
	
	public int prod_mjjdMng(long prod_id,String dept_name){
		String sql = "select t2.dict_name,t1.dept_no,t1.create_time,t1.cust_money||'('|| t1.create_time||')' pin, t1.dept_allot, coalesce(t1.cust_money,0) cust_money, coalesce(t1.pay_amount,0) pay_amount,cast(coalesce(t1.cust_money,0)/t1.dept_allot as numeric(2)) yqzb, cast(coalesce(t1.pay_amount,0)/t1.dept_allot as numeric(2)) ycjzb ,t1.prod_id	from (select a.prod_id,a.dept_no, a.dept_allot,	a.cust_money,create_time,sum(case when t.pay_type='1' then t.pay_amount else -t.pay_amount end ) pay_amount	 from (select a.prod_id,a.dept_no, a.dept_allot ,sum(b.cust_money) cust_money from product_allot a left join product_progress b on a.prod_id = b.prod_id and a.dept_no = b.dept_dist group by a.prod_id,a.dept_no, a.dept_allot	) a	left join (select  b.create_time,b.prod_no,c.pay_amount,c.pay_type from order_info b, payment_info c where b.order_no = c.order_no) t on t.prod_no = a.prod_id	group by a.prod_id,a.dept_no,a.dept_allot,a.cust_money,create_time) t1, data_dict t2 where t2.dict_type = 'dist' and t2.dict_value = t1.dept_no	and t1.prod_id=29 and t1.dept_no = '1'";
		return jt.queryForInt(sql,prod_id,dept_name);
	}
	
	
	
	
}
