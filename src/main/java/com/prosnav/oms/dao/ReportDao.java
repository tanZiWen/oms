package com.prosnav.oms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.prosnav.oms.util.jdbcUtil;

public class ReportDao {
	private  JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");
	/**
	 * 查询报表
	 * @return
	 */
	public List<Map<String, Object>> report_index(){
		String sql =    "SELECT  row_number() OVER () as rownum,al.prod_id, al.prod_name,al.partner_com_name,al.gp_name,al.real_name, al.status_name,al.invest_name,al.currency_name, al.pay_sum, al.invest_year, "
					+" al.reback, al.fee_type,al.manage_fee,al.fee_ratio,al.start_date,al.end_date, al.raise_fee  from  "
			    	+"(SELECT DISTINCT  t4.prod_id, t4.prod_name,t2.partner_com_name,t12.gp_name,t15.real_name , "
					+" t5.dict_name status_name,t3.dict_name invest_name,t6.dict_name currency_name, to_char(coalesce(sum(t1.order_amount),0), 'fm99,999,999,999,999,999,990.00') pay_sum, t2.invest_year, "
					+" (cast(coalesce(t2.renew_year,'0') as NUMERIC)-cast(coalesce(t2.invest_year,'0') as NUMERIC)) reback, '管理费' as fee_type,"
					+" (case when t8.mng_module='1' and t8.mng_type='1' then t8.mng_fee end)manage_fee,"
					+" (case when t8.mng_module='1' and t8.mng_type in ('2','3') then t8.mng_fee end)fee_ratio, "
			    	+" t8.start_date,t8.end_date, coalesce(t4.raise_fee,'0')raise_fee    "
					+" FROM public.order_info t1  "
					+" LEFT JOIN lp_info t2 on t1.part_comp = t2.lp_id "
					+" LEFT JOIN product_info t4 on t1.prod_no = t4.prod_id  "
					+" left join data_dict t3 on t3.dict_value = t2.invest_goal and t3.dict_type = 'invest'  "
					+" left join data_dict t5 on t5.dict_value = t4.prod_status and t5.dict_type = 'prodStus'  "
					+" left join data_dict t6 on t6.dict_value = t4.prod_currency and t6.dict_type = 'prodCur'  "
					+" left join data_dict t7 on t7.dict_value = t4.prod_mana and t7.dict_type = 'prodmana'  "
					+" LEFT JOIN (select * from product_fee t where t.mng_module='1') t8 on t1.prod_no = t8.prod_id and t1.part_comp = t8.lp_id  "
					+" left join data_dict t10 on t10.dict_value = t8.mng_module and t10.dict_type = 'module'  "
					+" left join gp_prod_rela t11 on t4.prod_id=t11.prod_id "
					+" left join gp_info t12 on t11.gp_id = t12.gp_id "
					+" LEFT JOIN upm_user t15 on cast(t15.user_id as varchar) = t4.prod_mana   "
					+" WHERE t1.is_checked = '2' and t1.stateflag = '0' and t4.check_status = '2'  "
					+" GROUP BY t1.is_checked, t1.stateflag, t1.create_time, t1.order_version, t1.buy_fee, t1.order_no, t1.entry_time,t2.partner_com_name,t12.gp_name, "
					+" t2.renew_period,t2.invest_period,t2.invest_goal,t2.renew_year,t2.invest_year,t3.dict_name,t4.prod_id, t4.prod_name, t4.prod_currency, t4.prod_status, t4.raise_fee, t4.prod_mana, "
					+" t5.dict_name,t6.dict_name,t15.real_name,t8.mng_module, t8.mng_type, t8.start_date, "
					+" t8.end_date, t8.mng_fee,t10.dict_name  "
					+" union all "
					+" SELECT DISTINCT t4.prod_id, t4.prod_name,t2.partner_com_name,t12.gp_name,t15.real_name , "
					+" t5.dict_name status_name,t3.dict_name invest_name,t6.dict_name currency_name, to_char(coalesce(sum(t1.order_amount),0), 'fm99,999,999,999,999,999,990.00') pay_sum, t2.invest_year, "
					+" (cast(coalesce(t2.renew_year,'0') as NUMERIC)-cast(coalesce(t2.invest_year,'0') as NUMERIC)) reback,'利润提成' as fee_type,"
					+" (case when t8.mng_module='0' then t8.mng_fee end)cost_scale,"
					+" (case when t8.mng_module='0' and t8.mng_type in('2','3') then t8.mng_fee end)fee_ratio,t8.start_date,t8.end_date, coalesce(t4.raise_fee,'0')raise_fee   "
					+" FROM public.order_info t1  "
					+" LEFT JOIN lp_info t2 on t1.part_comp = t2.lp_id "
					+" LEFT JOIN product_info t4 on t1.prod_no = t4.prod_id  "
					+" left join data_dict t3 on t3.dict_value = t2.invest_goal and t3.dict_type = 'invest'  "
					+" left join data_dict t5 on t5.dict_value = t4.prod_status and t5.dict_type = 'prodStus'  "
					+" left join data_dict t6 on t6.dict_value = t4.prod_currency and t6.dict_type = 'prodCur'  "
					+" left join data_dict t7 on t7.dict_value = t4.prod_mana and t7.dict_type = 'prodmana'  "
					+" LEFT JOIN (select * from product_fee t where t.mng_module='0') t8 on t1.prod_no = t8.prod_id and t1.part_comp = t8.lp_id  "
					+" left join data_dict t10 on t10.dict_value = t8.mng_module and t10.dict_type = 'module'  "
					+" left join gp_prod_rela t11 on t4.prod_id=t11.prod_id "
					+" left join gp_info t12 on t11.gp_id = t12.gp_id "
					+" LEFT JOIN upm_user t15 on cast(t15.user_id as varchar) = t4.prod_mana   "
					+" WHERE t1.is_checked = '2' and t1.stateflag = '0' and t4.check_status = '2'  "
					+" GROUP BY t1.is_checked, t1.stateflag, t1.create_time, t1.order_version, t1.buy_fee, t1.order_no, t1.entry_time,t2.partner_com_name,t12.gp_name, "
					+" t2.renew_period,t2.invest_period,t2.invest_goal,t2.renew_year,t2.invest_year,t3.dict_name,t4.prod_id, t4.prod_name, t4.prod_currency, t4.prod_status, t4.raise_fee, t4.prod_mana, "
					+" t5.dict_name,t6.dict_name,t15.real_name,t8.mng_module, t8.mng_type, t8.start_date, "
					+" t8.end_date, t8.mng_fee,t10.dict_name  "
					+" union all "
					+" SELECT DISTINCT t4.prod_id, t4.prod_name,t2.partner_com_name,t12.gp_name,t15.real_name , "
					+" t5.dict_name status_name,t3.dict_name invest_name,t6.dict_name currency_name, to_char(coalesce(sum(t1.order_amount),0), 'fm99,999,999,999,999,999,990.00') pay_sum, t2.invest_year, "
					+" (cast(coalesce(t2.renew_year,'0') as NUMERIC)-cast(coalesce(t2.invest_year,'0') as NUMERIC)) reback, "
					+" '认购费' as fee_type,t1.buy_fee,(case when t8.mng_module='3' then t8.mng_fee end)fee_ratio,t8.start_date,(SELECT a.end_time from (SELECT max(entry_time) end_time,prod_no from order_info GROUP BY prod_no) a "
					+" LEFT JOIN product_info p ON p.prod_id = a.prod_no WHERE p.prod_status ='2') end_date, coalesce(t4.raise_fee,'0')raise_fee    "
					+" FROM public.order_info t1  "
					+" LEFT JOIN lp_info t2 on t1.part_comp = t2.lp_id "
					+" LEFT JOIN product_info t4 on t1.prod_no = t4.prod_id  "
					+" left join data_dict t3 on t3.dict_value = t2.invest_goal and t3.dict_type = 'invest'  "
					+" left join data_dict t5 on t5.dict_value = t4.prod_status and t5.dict_type = 'prodStus'  "
					+" left join data_dict t6 on t6.dict_value = t4.prod_currency and t6.dict_type = 'prodCur'  "
					+" left join data_dict t7 on t7.dict_value = t4.prod_mana and t7.dict_type = 'prodmana'  "
					+" LEFT JOIN product_fee t8 on t1.prod_no = t8.prod_id and t1.part_comp = t8.lp_id  "
					+" left join data_dict t10 on t10.dict_value = t8.mng_module and t10.dict_type = 'module'  "
					+" left join gp_prod_rela t11 on t4.prod_id=t11.prod_id "
					+" left join gp_info t12 on t11.gp_id = t12.gp_id "
					+" LEFT JOIN upm_user t15 on cast(t15.user_id as varchar) = t4.prod_mana   "
					+" WHERE t1.is_checked = '2' and t1.stateflag = '0' and t4.check_status = '2'  "
					+" GROUP BY t1.is_checked, t1.stateflag, t1.create_time, t1.order_version, t1.buy_fee, t1.order_no, t1.entry_time,t2.partner_com_name,t12.gp_name, "
					+" t2.renew_period,t2.invest_period,t2.invest_goal,t2.renew_year,t2.invest_year,t3.dict_name,t4.prod_id, t4.prod_name, t4.prod_currency, t4.prod_status, t4.raise_fee, t4.prod_mana, "
					+" t5.dict_name,t6.dict_name,t15.real_name,t8.mng_module, t8.mng_type, t8.start_date, "
					+" t8.end_date, t8.mng_fee,t10.dict_name ) al GROUP BY al.prod_id, al.prod_name,al.partner_com_name,al.gp_name,al.real_name, al.status_name,al.invest_name,al.currency_name, al.pay_sum, al.invest_year, "
					+"	al.reback, al.fee_type,al.manage_fee,al.fee_ratio,al.start_date,al.end_date, al.raise_fee";
		return jt.queryForList(sql);
	}
	/**
	 * 查询报表导出excel
	 * @return
	 */
	public SqlRowSet export_report(){
		String sql =    "SELECT  row_number() OVER () as rownum,al.prod_id, al.prod_name,al.partner_com_name,al.gp_name,al.real_name, al.status_name,al.invest_name,al.currency_name, al.pay_sum, al.invest_year, "
					+" al.reback, al.fee_type,al.manage_fee,al.fee_ratio,al.start_date,al.end_date, al.raise_fee  from  "
					+"(SELECT DISTINCT  t4.prod_id, t4.prod_name,t2.partner_com_name,t12.gp_name,t15.real_name , "
					+" t5.dict_name status_name,t3.dict_name invest_name,t6.dict_name currency_name, to_char(coalesce(sum(t1.order_amount),0), 'fm99,999,999,999,999,999,990.00') pay_sum, t2.invest_year, "
					+" (cast(coalesce(t2.renew_year,'0') as NUMERIC)-cast(coalesce(t2.invest_year,'0') as NUMERIC)) reback, '管理费' as fee_type,"
					+" (case when t8.mng_module='1' and t8.mng_type='1' then t8.mng_fee end)manage_fee,"
					+" (case when t8.mng_module='1' and t8.mng_type in ('2','3') then t8.mng_fee end)fee_ratio, "
					+" t8.start_date,t8.end_date, coalesce(t4.raise_fee,'0')raise_fee    "
					+" FROM public.order_info t1  "
					+" LEFT JOIN lp_info t2 on t1.part_comp = t2.lp_id "
					+" LEFT JOIN product_info t4 on t1.prod_no = t4.prod_id  "
					+" left join data_dict t3 on t3.dict_value = t2.invest_goal and t3.dict_type = 'invest'  "
					+" left join data_dict t5 on t5.dict_value = t4.prod_status and t5.dict_type = 'prodStus'  "
					+" left join data_dict t6 on t6.dict_value = t4.prod_currency and t6.dict_type = 'prodCur'  "
					+" left join data_dict t7 on t7.dict_value = t4.prod_mana and t7.dict_type = 'prodmana'  "
					+" LEFT JOIN (select * from product_fee t where t.mng_module='1') t8 on t1.prod_no = t8.prod_id and t1.part_comp = t8.lp_id  "
					+" left join data_dict t10 on t10.dict_value = t8.mng_module and t10.dict_type = 'module'  "
					+" left join gp_prod_rela t11 on t4.prod_id=t11.prod_id "
					+" left join gp_info t12 on t11.gp_id = t12.gp_id "
					+" LEFT JOIN upm_user t15 on cast(t15.user_id as varchar) = t4.prod_mana   "
					+" WHERE t1.is_checked = '2' and t1.stateflag = '0' and t4.check_status = '2'  "
					+" GROUP BY t1.is_checked, t1.stateflag, t1.create_time, t1.order_version, t1.buy_fee, t1.order_no, t1.entry_time,t2.partner_com_name,t12.gp_name, "
					+" t2.renew_period,t2.invest_period,t2.invest_goal,t2.renew_year,t2.invest_year,t3.dict_name,t4.prod_id, t4.prod_name, t4.prod_currency, t4.prod_status, t4.raise_fee, t4.prod_mana, "
					+" t5.dict_name,t6.dict_name,t15.real_name,t8.mng_module, t8.mng_type, t8.start_date, "
					+" t8.end_date, t8.mng_fee,t10.dict_name  "
					+" union all "
					+" SELECT DISTINCT t4.prod_id, t4.prod_name,t2.partner_com_name,t12.gp_name,t15.real_name , "
					+" t5.dict_name status_name,t3.dict_name invest_name,t6.dict_name currency_name, to_char(coalesce(sum(t1.order_amount),0), 'fm99,999,999,999,999,999,990.00') pay_sum, t2.invest_year, "
					+" (cast(coalesce(t2.renew_year,'0') as NUMERIC)-cast(coalesce(t2.invest_year,'0') as NUMERIC)) reback,'利润提成' as fee_type,"
					+" (case when t8.mng_module='0' then t8.mng_fee end)cost_scale,"
					+" (case when t8.mng_module='0' and t8.mng_type in('2','3') then t8.mng_fee end)fee_ratio,t8.start_date,t8.end_date, coalesce(t4.raise_fee,'0')raise_fee   "
					+" FROM public.order_info t1  "
					+" LEFT JOIN lp_info t2 on t1.part_comp = t2.lp_id "
					+" LEFT JOIN product_info t4 on t1.prod_no = t4.prod_id  "
					+" left join data_dict t3 on t3.dict_value = t2.invest_goal and t3.dict_type = 'invest'  "
					+" left join data_dict t5 on t5.dict_value = t4.prod_status and t5.dict_type = 'prodStus'  "
					+" left join data_dict t6 on t6.dict_value = t4.prod_currency and t6.dict_type = 'prodCur'  "
					+" left join data_dict t7 on t7.dict_value = t4.prod_mana and t7.dict_type = 'prodmana'  "
					+" LEFT JOIN (select * from product_fee t where t.mng_module='0') t8 on t1.prod_no = t8.prod_id and t1.part_comp = t8.lp_id  "
					+" left join data_dict t10 on t10.dict_value = t8.mng_module and t10.dict_type = 'module'  "
					+" left join gp_prod_rela t11 on t4.prod_id=t11.prod_id "
					+" left join gp_info t12 on t11.gp_id = t12.gp_id "
					+" LEFT JOIN upm_user t15 on cast(t15.user_id as varchar) = t4.prod_mana   "
					+" WHERE t1.is_checked = '2' and t1.stateflag = '0' and t4.check_status = '2'  "
					+" GROUP BY t1.is_checked, t1.stateflag, t1.create_time, t1.order_version, t1.buy_fee, t1.order_no, t1.entry_time,t2.partner_com_name,t12.gp_name, "
					+" t2.renew_period,t2.invest_period,t2.invest_goal,t2.renew_year,t2.invest_year,t3.dict_name,t4.prod_id, t4.prod_name, t4.prod_currency, t4.prod_status, t4.raise_fee, t4.prod_mana, "
					+" t5.dict_name,t6.dict_name,t15.real_name,t8.mng_module, t8.mng_type, t8.start_date, "
					+" t8.end_date, t8.mng_fee,t10.dict_name  "
					+" union all "
					+" SELECT DISTINCT t4.prod_id, t4.prod_name,t2.partner_com_name,t12.gp_name,t15.real_name , "
					+" t5.dict_name status_name,t3.dict_name invest_name,t6.dict_name currency_name, to_char(coalesce(sum(t1.order_amount),0), 'fm99,999,999,999,999,999,990.00') pay_sum, t2.invest_year, "
					+" (cast(coalesce(t2.renew_year,'0') as NUMERIC)-cast(coalesce(t2.invest_year,'0') as NUMERIC)) reback, "
					+" '认购费' as fee_type,t1.buy_fee,(case when t8.mng_module='3' then t8.mng_fee end)fee_ratio,t8.start_date,(SELECT a.end_time from (SELECT max(entry_time) end_time,prod_no from order_info GROUP BY prod_no) a "
					+" LEFT JOIN product_info p ON p.prod_id = a.prod_no WHERE p.prod_status ='2') end_date, coalesce(t4.raise_fee,'0')raise_fee    "
					+" FROM public.order_info t1  "
					+" LEFT JOIN lp_info t2 on t1.part_comp = t2.lp_id "
					+" LEFT JOIN product_info t4 on t1.prod_no = t4.prod_id  "
					+" left join data_dict t3 on t3.dict_value = t2.invest_goal and t3.dict_type = 'invest'  "
					+" left join data_dict t5 on t5.dict_value = t4.prod_status and t5.dict_type = 'prodStus'  "
					+" left join data_dict t6 on t6.dict_value = t4.prod_currency and t6.dict_type = 'prodCur'  "
					+" left join data_dict t7 on t7.dict_value = t4.prod_mana and t7.dict_type = 'prodmana'  "
					+" LEFT JOIN product_fee t8 on t1.prod_no = t8.prod_id and t1.part_comp = t8.lp_id  "
					+" left join data_dict t10 on t10.dict_value = t8.mng_module and t10.dict_type = 'module'  "
					+" left join gp_prod_rela t11 on t4.prod_id=t11.prod_id "
					+" left join gp_info t12 on t11.gp_id = t12.gp_id "
					+" LEFT JOIN upm_user t15 on cast(t15.user_id as varchar) = t4.prod_mana   "
					+" WHERE t1.is_checked = '2' and t1.stateflag = '0' and t4.check_status = '2'  "
					+" GROUP BY t1.is_checked, t1.stateflag, t1.create_time, t1.order_version, t1.buy_fee, t1.order_no, t1.entry_time,t2.partner_com_name,t12.gp_name, "
					+" t2.renew_period,t2.invest_period,t2.invest_goal,t2.renew_year,t2.invest_year,t3.dict_name,t4.prod_id, t4.prod_name, t4.prod_currency, t4.prod_status, t4.raise_fee, t4.prod_mana, "
					+" t5.dict_name,t6.dict_name,t15.real_name,t8.mng_module, t8.mng_type, t8.start_date, "
					+" t8.end_date, t8.mng_fee,t10.dict_name ) al GROUP BY al.prod_id, al.prod_name,al.partner_com_name,al.gp_name,al.real_name, al.status_name,al.invest_name,al.currency_name, al.pay_sum, al.invest_year, "
					+"	al.reback, al.fee_type,al.manage_fee,al.fee_ratio,al.start_date,al.end_date, al.raise_fee";
		return jt.queryForRowSet(sql);
	}
	/**
	 * 根据角色查询显示报表
	 * @param role_id
	 * @return
	 */
	public List<Map<String, Object>> report_list(long role_id){
		String sql ="select t1.func_url,t1.func_remark,t1.func_describle from func_info t1 "
				+ "left join role_func t2 on t2.func_id=t1.func_id "
				+ "where t2.role_id=? and t1.button_id='report' group by t1.func_id";
		return jt.queryForList(sql,role_id);
	}
	/**
	 * 家族报表查询
	 * @return
	 */
	public List<Map<String, Object>> family_report(){
		String sql = "SELECT t12.family_name,t6.cust_name,t3.prod_name,t4.partner_com_name, t1.order_no,t1.order_amount,  t2.sales_name, "
				+ "t11.dict_name prod_type, t8.dict_name as bizhong,t9.dict_name ischecked "
				+ "FROM public.order_info t1  "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ "left join family_member_rel t6 on t6.cust_id = t1.cust_no "
				+ "left join family_info t12 on t6.family_id = t12.family_id "
				+ "INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur'  "
				+ "left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag'   "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType' "
				+ "where t1.stateflag='0' and t1.register=1000045 order by t12.family_id";
		return jt.queryForList(sql);
	}
	/**
	 * 家族报表查询
	 * @return
	 */
	public SqlRowSet f_exceport_report(){
		String sql = "SELECT  t12.family_name,t6.cust_name,t3.prod_name,t4.partner_com_name, t1.order_no,t1.order_amount,  t2.sales_name, "
				+ "t11.dict_name prod_type, t8.dict_name as bizhong,t9.dict_name ischecked "
				+ "FROM public.order_info t1  "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ "left join family_member_rel t6 on t6.cust_id = t1.cust_no "
				+ "left join family_info t12 on t6.family_id = t12.family_id "
				+ "INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur'  "
				+ "left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag'   "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType' "
				+ "where t1.stateflag='0' and t1.register=1000045 order by t12.family_id";
		return jt.queryForRowSet(sql);
	}
}
