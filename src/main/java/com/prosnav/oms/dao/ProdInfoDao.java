package com.prosnav.oms.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.oms.util.jdbcUtil;

public class ProdInfoDao {
	private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil
            .getBean("template");
	public List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	String sql="";
	
	/**
	 * 查询全量产品信息
	 * @return
	 */
	/*public List<Map<String,Object>> prodInfoSelectAll(){
		sql="SELECT t1.prod_id, t1.prod_name, t1.prod_type,t1.cor_org, "
				+ "t1.prod_currency, t1.s_raise_date,  t1.invest_goal,"
				+ "t1.pay_sum, t1.pay_true, t1.prod_status, "
				+ "t2.dict_name prod_type,t2.dict_value product_value,"
				+ "t2.dict_type product_type,t3.dict_name prod_currency,"
				+ "t3.dict_value money_value,t3.dict_type money_type  "
				+ "FROM public.product_info t1	"
				+ "inner join data_dict t2 on t2.dict_value=t1.prod_type "
				+ "and t2.dict_type='prodType' "
				+ "inner join data_dict t3 on t3.dict_value=t1.prod_currency"
				+ " and t3.dict_type='prodCur'";
		list = jt.queryForList(sql);
		return list;
	}*/
	
	
	/**
	 * 按产品名称查询
	 * @return
	 */
/*	public List<Map<String,Object>> prodInfoSelect(String prod_name,String prod_type,
			String prod_status,String cor_org,Date s_raise_date){
		sql="select prod_name,prod_type,cor_org,invest_goal,pay_sum,pay_true,"
				+ "s_raise_date,prod_currency from product_info "
				+ "where prod_name=? and prod_type=?  and prod_status=? "
				+ "and cor_org=? and s_raise_date=?";
		list = jt.queryForList(sql,prod_name,prod_type,prod_status,cor_org,s_raise_date);
		return list;
	}*/
	
	
//	
	public int raise_fee(Double raise_fee,long prod_id){
		String sql = "update product_info set raise_fee=? where prod_id=?";
		return jt.update(sql,raise_fee,prod_id);
	}

	/**
	 * 查询产品下募集费用有没有值
	 * @param prod_id
	 * @return
	 */
/*	public List<Map<String,Object>>  re_fee(long prod_id){
	String sql = "select count(1) from product_info where raise_fee is not null and prod_id=?";
	return jt.queryForList(sql,prod_id);
	}*/
	/*
	public  List<Map<String,Object>> checkProdName(String prodName){
		String sql="select count(1) from product_info where prod_name=?";
		return jt.queryForList(sql,prodName);
	}*/
	
	
	/**
	 * 查询产品下募集费用的值
	 * @param prod_id
	 * @return
	 */
	public List<Map<String,Object>> r_fee(long prod_id){
		String sql = "select raise_fee  from product_info where prod_id=?";
		return jt.queryForList(sql,prod_id);
		}
	
	/** 
	 * 按产品ID查询
	 * @param prod_id
	 * @return
	 */
	public List<Map<String,Object>> prodInfoSelectById(long prod_id){
		String sql = "select t1.mail_no,t1.prod_id, t1.prod_name, t2.prodTypeValue,t2.prodTypeName,t1.prod_risk_level, t1.cor_org, t1.prod_mana, "
				+" t1.invest_goal,t1.s_raise_date ,t1.e_raise_date ,t1.gp_name ,t1.gp_id,t3.CurValue,t3.Curname,t4.prodCheValue,t4.prodCheName ,t5.real_name,t5.user_id "
				+" from( select  a.prod_id, a.prod_name, a.prod_type, a.cor_org,a.prod_risk_level, a.prod_mana,a.mail_no,"
				+" a.invest_goal, a.s_raise_date ,a.e_raise_date , a.prod_currency, a.check_status,b.gp_id,"
				+" b.gp_name from product_info a,gp_info b,gp_prod_rela c "
				+" where a.prod_id = c.prod_id and b.gp_id = c.gp_id ) t1,"
				+"	(select b.dict_value prodTypeValue,b.dict_name prodTypeName "
				+" from data_dict b where b.dict_type = 'prodType'  union all  select '' prodTypeValue,'' prodTypeName  from data_dict )t2,"
				+" (select b.dict_value CurValue,b.dict_name Curname "
				+"	from data_dict b where b.dict_type = 'prodCur' union all  select '' CurValue,'' Curname  from data_dict ) t3,"
				+" (select distinct b.dict_value prodCheValue,b.dict_name prodCheName "
				+" from data_dict b where b.dict_type = 'prodChe') t4 , "
				+"(select user_id,real_name  from upm_user where 'production_handle' = any(role_codes) = 't' or 'production_manage' = any(role_codes)='t') t5 "
				+" where t1.prod_type = t2.prodTypeValue and t1.prod_currency = t3.CurValue "
				+" and t1.check_status = t4.prodCheValue and t1.prod_mana =cast( t5.user_id as varchar) and t1.prod_id = ? ";
		list = jt.queryForList(sql,prod_id);
		return list;
	}
	
	/**
	 * 新增产品
	 * @param gp_name
	 * @param prod_name
	 * @param prod_type
	 * @param prod_currency
	 * @param cor_org
	 * @param s_raise_date
	 * @param e_raise_date
	 * @param invest_goal
	 * @return
	 */
	public static int prod_add(long prod_id,String prod_name,String prod_type,
			String prod_currency,String cor_org,Date s_raise_date,Date e_raise_date,String invest_goal,
			String prod_mana,String prod_risk_level,long mail_no,long user_id){
		
		String sql="insert into product_info (prod_id,prod_name,prod_type,"
				+ "prod_currency,cor_org,s_raise_date,e_raise_date,invest_goal,prod_mana,prod_risk_level,check_status,mail_no,user_id,prod_status)"
				+ " values(?,?,?,?,?,?,?,?,?,?,'3',?,?,'1')";
		return jt.update(sql,prod_id,prod_name,prod_type,prod_currency,cor_org,s_raise_date,e_raise_date,invest_goal,prod_mana,prod_risk_level,mail_no,user_id);
	}  
/*	public static int prod_add(long prod_id,String prod_name,long mail_no){
		
		String sql="insert into product_info (prod_id,prod_name,mail_no)"
				+ " values(?,?,?)";
		return jt.update(sql,prod_id,prod_name,mail_no);
	} */
	
	/**
	 * 根据产品ID查询GP列表
	 * @param prod_id
	 * @return
	 */
	public  List<Map<String,Object>> GPList(long prod_id){
		String sql = "select prod_id,a.gp_id,gp_name,bus_license,"
				+ "legal_resp,fund_no,open_bank,bank_account,"
				+ "regit_addr from gp_info a,GP_prod_rela b "
				+ "where a.gp_id = b.gp_id and prod_id=?";
		
		list = jt.queryForList(sql,prod_id);
		return list;
	}
	
	
	/**
	 * 根据产品ID查询LP列表
	 * @param prod_id
	 * @return
	 */
	public  List<Map<String,Object>> LPList(long prod_id){
	
	/*	String sql = "select e.prod_id,a.LP_id,partner_com_name, sum(b.buy_fee) buy_fee,"
		+ "sum(case when c.pay_type='1' then c.pay_amount else -c.pay_amount end ) pay_amount	"
		+ "from (LP_info a left join order_info b on a.partner_com_name = b.part_comp)	"
		+ "left join payment_info c on b.order_no = c.order_no,lp_prod_rela e	"
		+ "where  a.lp_id=e.lp_id and e.prod_id=?	group by e.prod_id,a.LP_id,partner_com_name";
*/
		String sql = "select t2.prod_id,t1.LP_id,t1.partner_com_name, to_char(coalesce(sum(t1.buy_fee),0), 'fm99,999,999,999,999,999,990.00') buy_fee,"
				+"to_char(coalesce(sum(case when t1.pay_type='1' then t1.pay_amount else -t1.pay_amount end),0), 'fm99,999,999,999,999,999,990.00') pay_amount "
				+"from (select a.lp_id, a.partner_com_name,b.order_no,b.buy_fee, c.pay_type, c.pay_amount "
				+"from (LP_info a left join order_info b on a.lp_id = b.part_comp) "
				+"left join payment_info c on b.order_no = c.order_no ) t1, lp_prod_rela t2 "
				+"where t1.lp_id=t2.lp_id and t2.prod_id = ? "
				+"group by t2.prod_id,t1.LP_id,t1.partner_com_name ";
		list = jt.queryForList(sql,prod_id);
		return list; 
	}

	/**
	 * 确认回款(财务)
	 * @param gp_name
	 * @param prod_name
	 * @param prod_type
	 * @param prod_currency
	 * @param cor_org
	 * @param s_raise_date
	 * @param e_raise_date
	 * @param invest_goal
	 * @return
	 */
	public int sure_HK(long prod_rf_id,long prod_id,Double return_money,Date return_date,String hkno){
		String sql="insert into return_money (prod_r_id,prod_id,return_money,return_date,hkno) values(?,?,?,?,?);";
		return jt.update(sql, prod_rf_id,prod_id,return_money,return_date,hkno);
	}

	
	/**
	 * 确认回款(财务)
	 * @param gp_name
	 * @param prod_name
	 * @param prod_type
	 * @param prod_currency
	 * @param cor_org
	 * @param s_raise_date
	 * @param e_raise_date
	 * @param invest_goal
	 * @return
	 */
	public int sure_sHK(long prod_rf_id,long prod_id,Double return_money,Double return_coe,String shkno, Date return_date){
		String sql="insert into product_return_sale (prod_rs_id,prod_id,return_money,return_coe,return_date,shkno)  values(?,?,?,?,?,?)";
		return jt.update(sql, prod_rf_id,prod_id,return_money,return_coe,return_date, shkno);
	}
	
	/**
	 * 查询LP
	 * @param lp_id
	 * @return
	 */
	public List<Map<String,Object>> LPInfo(long lp_id){
		String sql = "select a.area,a.see_sale, a.invest_goal_decr,a.mana_decr,a.buy_decr,a.delay_decr,a.late_join_decr,a.LP_id,GP_name,partner_com_name,fund_type,invest_goal,a.prod_diffcoe,bus_license,invest_year,renew_year,legal_resp,fund_no,"
				+" open_bank, bank_account,open_bank1,bank_account1,regit_addr,prod_remark,fund_start_fee,"
				+" fund_bnf_allot,cor_com_fee,	fund_type_fee,true_pay,gplp_pay,renew_period,first_delivery,"
				+" invest_goal,invest_period ,d.fundName,f.investName  from (LP_info a left join order_info b "
				+" on a.lp_id = b.part_comp) left join payment_info c on b.order_no = c.order_no,"
				+" lp_prod_rela e,	"
				+" (select dict_name fundName,dict_value fundValue	from data_dict "
				+" where dict_type='fundType' union all  select '' fundName,'' fundValue	from data_dict) d,"
				+" (select dict_name investName,dict_value investValue	from data_dict "
				+" where dict_type='invest' union all  select '' investName,'' investValue	from data_dict) f	"
				+" where    d.fundValue = a.fund_type and a.lp_id=e.lp_id  and a.lp_id=? "
				+" group by a.LP_id,GP_name,partner_com_name,fund_type,a.prod_diffcoe,bus_license,legal_resp,	fund_no,"
				+" open_bank, bank_account,open_bank1,bank_account1,regit_addr,prod_remark,fund_start_fee,"
				+" fund_bnf_allot,cor_com_fee,	fund_type_fee,true_pay,gplp_pay,renew_period,first_delivery,"
				+" invest_goal,invest_period ,d.fundName,f.investName,a.mana_decr,"
				+ "a.buy_decr,a.delay_decr,a.late_join_decr,a.invest_goal_decr,a.see_sale,a.area ";
		list = jt.queryForList(sql,lp_id);
		return list;
	}
	
	/**
	 * 新增产品下的LP
	 * @param param
	 * @param lp_id
	 * @return
	 */
	public int LP_info(Map<String,Object> param,long lp_id){
		sql="UPDATE public.lp_info SET "
				//+ "gp_name=?, prod_diffcoe=?, "
				+ "fund_type=?, fund_no=?, bus_license=?, legal_resp=?,"
				+ " pay_sum=?,pay_true=?, open_bank=?, bank_account=?,"
				+ " open_bank1=?, bank_account1=?, regit_addr=?, "
				+ "prod_remark=?, fund_start_fee=?, fund_bnf_allot=?,"
				+ "cor_com_fee=?, fund_type_fee=?, gplp_pay=?, true_pay=?,"
				+ " first_delivery=?,renew_period=?, invest_period=?, "
				+ "invest_goal=?,invest_year=?,renew_year=? ,"
				+ "mana_decr=?,buy_decr=?,delay_decr=?,late_join_decr=?,invest_goal_decr=?,"
				+ "see_sale =?,area=? "
				+ " where lp_id = ? ";
		int result = jt.update(sql,
				//param.get("GP_name"),param.get("prod_diffcoe"),
				param.get("fund_type"),param.get("fund_no"),
				param.get("bus_license"),
				param.get("legal_resp"),param.get("pay_sum"),
				param.get("pay_true"),param.get("open_bank"),param.get("bank_account"),
				param.get("open_bank1"),param.get("bank_account1"),param.get("regit_addr"),
				param.get("prod_remark"),param.get("fund_start_fee"),param.get("fund_bnf_allot"),
				param.get("cor_com_fee"),param.get("fund_type_fee"),param.get("gplp_pay"),
				param.get("true_pay"),param.get("first_delivery"),
				param.get("renew_period"),param.get("invest_period"),
				param.get("invest_goal"),param.get("invest_year"),param.get("renew_year"),
				param.get("mana_decr"),param.get("buy_decr"),param.get("delay_decr"),
				param.get("late_join_decr"),param.get("invest_goal_decr"),
				param.get("see_sale"),param.get("area"),lp_id);
		return result;
		
	}
	
	/**
	 * 根据产品名称，渠道，状态，合作机构，募集开始时间查询产品详情
	 * @param prod_name
	 * @param prod_type
	 * @param prod_status
	 * @param cor_org
	 * @param s_raise_date
	 * @return
	 */
	public  List<Map<String,Object>> prodInfo(String prod_name,String prod_type,
		String prod_status, String lp_name, String cor_org,String s_raise_date,String s_raise_date1,Object user_id,int m,int n){
		String sql="select * from query_product_byUser(?,?,?,?,?,?,?,?,?,?) as a(prod_id bigInt,prod_name varchar,prodTypeName varchar,cor_org varchar, invest_goal varchar, buy_fee text ,pay_amount text,s_raise_date date,Curname varchar,prodCheName varchar,prodStusName varchar)";
		return jt.queryForList(sql, prod_name,prod_type,prod_status,lp_name,cor_org,s_raise_date,s_raise_date1,user_id,m,n);
	}
	
	/**
	 * 产品条数
	 */
	public  List<Map<String,Object>> prodInfo_count(String prod_name,String prod_type,
		String prod_status, String lp_name, String cor_org,String s_raise_date,String s_raise_date1,Object user_id){
		String sql="select * from query_product_count_byUser(?,?,?,?,?,?,?,?)";
		return jt.queryForList(sql, prod_name,prod_type,prod_status,lp_name,cor_org,s_raise_date,s_raise_date1,user_id);
	}
	
	/**
	 * 查询产品经理
	 * @return
	 */
	public  List<Map<String,Object>> mngFeeDictInfo(){
		String sql="select dict_name,dict_value from data_dict where dict_type='mngFee'";
		return jt.queryForList(sql);
	}
	
	
	/**
	 * 查询产品渠道字典值
	 * @return
	 */
	public  List<Map<String,Object>> prodTypeDictInfo(){
		String sql="select dict_name,dict_value from data_dict where dict_type='prodType'";
		return jt.queryForList(sql);
	}
	
	/**
	 * 查询基金类型字典值
	 * @return
	 */
	public  List<Map<String,Object>> findTypeDictInfo(){
		String sql="select dict_name,dict_value from data_dict where dict_type='fundType'";
		return jt.queryForList(sql);
	}
	
	public  List<Map<String,Object>> findareaDictInfo(){
		String sql="select dict_name,dict_value from data_dict where dict_type='dist'";
		return jt.queryForList(sql);
	}
	
	/**
	 * 查询产品币种字典值
	 * @return
	 */
	public  List<Map<String,Object>> prodCurDictInfo(){
		String sql="select dict_name,dict_value from data_dict where dict_type='prodCur'";
		return jt.queryForList(sql);
	}
	
	
	/**
	 * 查询产品币种字典值
	 * @return
	 */
	public  List<Map<String,Object>> prodManaDictInfo(){
		String sql="select user_id,real_name,role_codes,'production_handle'=any(role_codes) as handle,'production_manage'=any(role_codes) as manage "
				+ "from upm_user where 'production_handle'=any(role_codes)='t' or 'production_manage'=any(role_codes)='t' ";
		return jt.queryForList(sql);
	}
	
	
	/**
	 * 查询lp投资状态字典值
	 * @return
	 */
	public  List<Map<String,Object>> prodInvestInfo(){
		String sql="select dict_name,dict_value from data_dict where dict_type='invest'";
		return jt.queryForList(sql);
	}
	
	/**
	 * 查询gp名称
	 * @return
	 */
	public  List<Map<String,Object>> gpName(){
		String sql="select gp_name,gp_id from gp_info where status='2'";
		return jt.queryForList(sql);
	}
	
	/**
	 * 检查产品是否已录入
	 * @param prodName
	 * @return
	 */
	public  List<Map<String,Object>> checkProdName(String prodName){
		String sql="select count(1) from product_info where prod_name=? and check_status !='1'";
		return jt.queryForList(sql,prodName);
	}
	
	/**
	 * 初始化地区
	 * @return
	 */
	public  List<Map<String,Object>> distDictInfo(){
		String sql="select dict_name, dict_value from data_dict where dict_type='dist'";
		return jt.queryForList(sql);
	}
	
	/**
	 * 更新GP和产品关系
	 * @param gp_id
	 * @param prod_id
	 * @return
	 */
	public long gp_prod_rela(long gp_id, long prod_id,long email_id){
		sql="insert into gp_prod_rela values(?,?,?)";
		long result = jt.update(sql,gp_id,prod_id,email_id);
		return result;
	}
	
	
	//max_GPID  
/*	public long max_GPID(){
		sql="select max(prod_id) max_id from product_info";
		long max_id=jt.queryForInt(sql);
		return max_id;
	}*/
	
	/**
	 * 财务回款记录列表
	 * @param prod_id
	 * @return
	 */
	public  List<Map<String,Object>> HKInfo(long prod_id){
		String sql="select e.dict_name status ,a.gp_name ,c.prod_name,"
				+ "to_char(coalesce(b.return_money,0), 'fm99,999,999,999,999,999,990.00') return_money,"
				+ "b.return_date,b.prod_r_id,b.finahk_flag from gp_info a,"
				+ "return_money b,product_info c,"
				+ "gp_prod_rela d ,data_dict e "
				+ "where a.gp_id=d.gp_id and e.dict_value=b.finahk_flag and e.dict_type='f_return' "
				+ "and c.prod_id=d.prod_id and c.prod_id=b.prod_id "
				+ "and c.prod_id=?";
		return jt.queryForList(sql,prod_id);
	}
	
	/**
	 * 销售回款记录列表
	 * @param prod_id
	 * @return
	 */
	public  List<Map<String,Object>> sHKInfo(long prod_id){
		/*String sql="select a.gp_name ,c.prod_name,b.return_money,"
				+ "b.return_date,b.return_coe "
				+ "from gp_info a,product_return_sale b,"
				+ "product_info c,gp_prod_rela d "
				+ "where a.gp_id=d.gp_id and c.prod_id=d.prod_id "
				+ "and c.prod_id=b.prod_id and c.prod_id=?";*/
		
		String sql = "select a.gp_name ,c.prod_name,b.prod_rs_id,"
				+ "to_char(coalesce(b.return_money,0), 'fm99,999,999,999,999,999,990.00') return_money,"
				+ "b.return_date,b.shkno,"
				+ "b.return_coe,b.prod_flag, e.prodFlag	from gp_info a,product_return_sale"
				+ " b,product_info c,gp_prod_rela d ,"
				+ "(select distinct b.dict_value,b.dict_name prodFlag	"
				+ "from data_dict b where b.dict_type = 'prodFlag') e	"
				+ "where a.gp_id=d.gp_id and c.prod_id=d.prod_id"
				+ " and b.prod_flag = e.dict_value and c.prod_id=b.prod_id "
				+ "and c.prod_id=?";
		return jt.queryForList(sql,prod_id);
	}
	
	
	public  List<Map<String,Object>> prod_gress_detail(long prod_id,String dept_no){	
		String sql = "select t2.dict_name,t1.dept_no,t1.create_time,t1.cust_name,t1.remarks,t1.sale_name,"
				+ "t1.cust_money||'('|| t1.create_time||')' pin,"
				+ "to_char(coalesce(t1.dept_allot,0), 'fm99,999,999,999,999,999,990.00') dept_allot, "
				+ "to_char(coalesce(t1.cust_money,0), 'fm99,999,999,999,999,999,990.00') cust_money, "
				+ "to_char(coalesce(t1.pay_amount,0), 'fm99,999,999,999,999,999,990.00') pay_amount,"
				+ "cast(coalesce(t1.cust_money,0)/t1.dept_allot as numeric(2)) "
				+ "yqzb, cast(coalesce(t1.pay_amount,0)/t1.dept_allot as numeric(2)) ycjzb ,"
				+ "t1.prod_id	from "
				+ "(select a.prod_id,a.dept_no, a.dept_allot, a.cust_name,a.remarks,a.sale_name,	"
				+ "a.cust_money,create_time,sum(case when t.pay_type='1' then t.pay_amount else -t.pay_amount end )"
				+ " pay_amount	 from (select a.prod_id,a.dept_no, a.dept_allot ,b.cust_name,b.remarks,b.sale_name,"
				+ "sum(b.cust_money) cust_money from product_allot a left join product_progress b on "
				+ "a.prod_id = b.prod_id and a.dept_no = b.dept_dist group by a.prod_id,a.dept_no, a.dept_allot,"
				+ "b.remarks,b.cust_name,b.sale_name	) a	left join (select  b.create_time,b.prod_no,c.pay_amount,"
				+ "c.pay_type from order_info b, payment_info c where b.order_no = c.order_no) t "
				+ "on t.prod_no = a.prod_id	group by a.prod_id,a.dept_no,a.dept_allot,a.cust_money,create_time, "
				+ "a.cust_name,a.remarks,a.sale_name) t1, data_dict t2 where t2.dict_type = 'dist'	"
				+ "and t2.dict_value = t1.dept_no	and t1.prod_id=?	and t1.dept_no = ? order by prod_id";
		
		return jt.queryForList(sql,prod_id,dept_no);
	}
	
	
	public  List<Map<String,Object>> prod_gress(long prod_id,String dept_no){
		String sql = "select t2.dict_name,t1.dept_no,t1.create_time,"
				+ "t1.cust_money||'('|| t1.create_time||')' pin,"
				+ "to_char(coalesce(t1.dept_allot,0), 'fm99,999,999,999,999,999,990.00') dept_allot, "
				+ "to_char(coalesce(t1.cust_money,0), 'fm99,999,999,999,999,999,990.00') cust_money, "
				+ "to_char(coalesce(t1.pay_amount,0), 'fm99,999,999,999,999,999,990.00') pay_amount,"
				+ "cast(coalesce(t1.cust_money,0)/t1.dept_allot as numeric(8)) yqzb,"
				+ " cast(coalesce(t1.pay_amount,0)/t1.dept_allot as numeric(8)) ycjzb ,"
				+ "t1.prod_id	from (select a.prod_id,a.dept_no, a.dept_allot,	a.cust_money,"
				+ "create_time,sum(case when t.pay_type='1' "
				+ "then t.pay_amount else -t.pay_amount end ) pay_amount	"
				+ " from (select a.prod_id,a.dept_no, a.dept_allot ,sum(b.cust_money) cust_money "
				+ "from product_allot a left join product_progress b on a.prod_id = b.prod_id "
				+ "and a.dept_no = b.dept_dist group by a.prod_id,a.dept_no, a.dept_allot	) a	"
				+ "left join (select  b.create_time,b.prod_no,c.pay_amount,c.pay_type "
				+ "from order_info b, payment_info c where b.order_no = c.order_no) t "
				+ "on t.prod_no = a.prod_id	group by a.prod_id,a.dept_no,a.dept_allot,"
				+ "a.cust_money,create_time) t1, data_dict t2 where t2.dict_type = 'dist' "
				+ "and t2.dict_value = t1.dept_no and t1.prod_id=? and t1.dept_no = ?";
		
		return jt.queryForList(sql,prod_id,dept_no);
	}
	
	
	public  List<Map<String,Object>> prod_gress1(long prod_id){
		String sql = "select t2.dict_name,t1.dept_no,t1.create_time,"
		+ "t1.cust_money||'('|| t1.create_time||')' pin, "
		+ "to_char(coalesce(t1.dept_allot,0), 'fm99,999,999,999,999,999,990.00') dept_allot, "
		+ "to_char(coalesce(t1.cust_money,0), 'fm99,999,999,999,999,999,990.00') cust_money, "
		+ "to_char(coalesce(t1.pay_amount,0), 'fm99,999,999,999,999,999,990.00') pay_amount,"
		+ "cast(coalesce(t1.cust_money,0)/t1.dept_allot as numeric(8)) yqzb,"
		+ " cast(coalesce(t1.pay_amount,0)/t1.dept_allot as numeric(8)) ycjzb ,"
		+ "t1.prod_id	from (select a.prod_id,a.dept_no, a.dept_allot,	a.cust_money,"
		+ "create_time,sum(case when t.pay_type='1' "
		+ "then t.pay_amount else -t.pay_amount end ) pay_amount	"
		+ " from (select a.prod_id,a.dept_no, a.dept_allot ,sum(b.cust_money) cust_money "
		+ "from product_allot a left join product_progress b on a.prod_id = b.prod_id "
		+ "and a.dept_no = b.dept_dist group by a.prod_id,a.dept_no, a.dept_allot	) a	"
		+ "left join (select  b.create_time,b.prod_no,c.pay_amount,c.pay_type "
		+ "from order_info b, payment_info c where b.order_no = c.order_no) t "
		+ "on t.prod_no = a.prod_id	group by a.prod_id,a.dept_no,a.dept_allot,"
		+ "a.cust_money,create_time) t1, data_dict t2 where t2.dict_type = 'dist' "
		+ "and t2.dict_value = t1.dept_no and t1.prod_id=?";
		
		return jt.queryForList(sql,prod_id);
	}
	
	
	
	/*public int checkType(int prod_id,Double return_money,Double return_coe){
		String sql="insert into product_info (prod_rf_id,prod_id,return_money,return_coe,return_date) values(default,?,?,?,current_date);";
		return jt.update(sql, prod_id,return_money,return_coe);
	}*/
	
	/**
	 * 更新产品状态为驳回 1
	 */
	public long closeProd(long prod_id){
		String sql="UPDATE public.product_info SET prod_status='2' WHERE prod_id=?";
		return jt.update(sql, prod_id);
	}
	
	/**
	 * 更新产品状态为驳回 1
	 */
	public long checkProd(long prod_id){
		String sql="UPDATE public.product_info SET check_status='1' WHERE prod_id=?";
		return jt.update(sql, prod_id);
	}
	
	/**
	 * 更新发布标志为5 已发布
	 */
/*	public long prod_flag(long prod_id,String shkno,long order_id){
		String sql="update product_return_sale set prod_flag='2' where prod_id=? and shkno=? and order_id=?";
		return jt.update(sql, prod_id,shkno,order_id);
	}
*/	public long prod_flag(long prod_id,String shkno){
		String sql="update product_return_sale set prod_flag='5' where prod_id=? and shkno=?";
		return jt.update(sql, prod_id,shkno);
	}
	
	/**
	 * 更新产品状态为已审核 2
	 */
	public long checkProd1(long prod_id){
		String sql="UPDATE public.product_info SET check_status='2' WHERE prod_id=?";
		return jt.update(sql, prod_id);
	}
	
	
	/**
	 * 更新产品信息
	 */
	/*public int updateProd(String prod_currency,String s_raise_date,String e_raise_date,String invest_goal,String prod_risk_level,long prod_id){
		String sql="UPDATE public.product_info SET prod_currency=?, s_raise_date=?, e_raise_date=?, invest_goal=?,prod_risk_level=? WHERE prod_id=?";
		return jt.update(sql,prod_currency, s_raise_date,e_raise_date,invest_goal,prod_risk_level,prod_id);
	}*/
	public int updateProd(String prod_currency,String invest_goal,String prod_risk_level,String s_raise_date,String e_raise_date,String prod_mana,long mail_no,long prod_id){
		String sql="UPDATE public.product_info SET prod_currency=?,invest_goal=?,prod_risk_level=? ,s_raise_date=cast(? as date), e_raise_date=cast(? as date),prod_mana=?,mail_no=? WHERE prod_id=?";
		return jt.update(sql,prod_currency, invest_goal,prod_risk_level,s_raise_date,e_raise_date,prod_mana,mail_no,prod_id);
	}
	
	/**
	 * 保存变更前数据
	 */
	public void saveProd(JSONObject json,long mail_no,long prod_id){
		String sql ="insert into task(content,id,remark,type,version,user_id)values('"+json+"',?,'prod更改提交审批','prod',default,?) ";
		 jt.update(sql, mail_no,prod_id);
	}
	/**
	 * 重置
	 */
	public List<Map<String, Object>> prod_reset(long mail_no){
		String sql="";
		sql="SELECT content,user_id from task where id=?";
		return jt.queryForList(sql,mail_no);
	}
	public int prod_res(JSONObject json,Long prod_id){
		String sql="UPDATE public.product_info SET prod_currency=?,invest_goal=?,prod_risk_level=? ,"
				+ "s_raise_date=cast(? as date),"
				+ " e_raise_date=cast(? as date),prod_mana=? WHERE prod_id=?";
		return jt.update(sql,json.get("prod_currency"),json.get("prod_invest"),json.get("prod_risk"),
				json.get("s_raise_date"),json.get("e_raise_date"),json.get("prod_mana"),prod_id);
	}
	
	
	
	public  List<Map<String,Object>> prod_fb(long prod_id,String prod_rs_id,String start_date,String end_date){
		/*String sql = "select a.cust_no,a.prod_no ,a.order_version, a.order_no,c.cust_name,b.prod_id,b.return_money,"
				+ "b.return_coe,b.shkno,b.return_date, d.sales_id, d.sales_name,d.order_version	from order_info a ,"
				+ "sale_order d , product_return_sale b, cust_info c where b.prod_id = a.prod_no "
				+ "and  d.order_version = a.order_version	and c.cust_id = a.cust_no	and a.prod_no=? "
				+ "and prod_flag='1'	and shkno =? and return_date between cast(? as date)  and cast(? as date) ";*/
		String sql="select a.cust_no,a.prod_no ,a.order_version, a.order_no,c.cust_name,b.prod_id,b.return_money,"
				+ "b.return_coe,b.shkno,b.return_date, d.sales_id, d.sales_name,d.order_version, oi.org_name from order_info a	"
				+ "inner join sale_order d on a.order_no=d.order_no and a.stateflag='0' "
				+ "left join product_return_sale b on b.prod_id = a.prod_no "
				+ "left join cust_info c on  c.cust_id = a.cust_no	"
				+ "left join org_info oi on oi.org_id = a.cust_no "
				+ "where a.prod_no=? and prod_flag='4' and a.is_checked='2' and shkno =? and return_date "
				+ "between cast(? as date)  and cast(? as date) ";
		return jt.queryForList(sql,prod_id,prod_rs_id,start_date,end_date);
	}
	
	
	public  List<Map<String,Object>> prod_yfb(long prod_id,String prod_rs_id){
		/*String sql = "select a.cust_no,a.prod_no ,a.order_version, a.order_no,c.cust_name,b.prod_id,b.return_money,"
				+ "b.return_coe,b.shkno,b.return_date, d.sales_id, d.sales_name,d.order_version from order_info a	"
				+ "inner join sale_order d on a.order_no=d.order_no and a.stateflag='0' "
				+ "left join product_return_sale b on b.prod_id = a.prod_no "
				+ "left join cust_info c on  c.cust_id = a.cust_no	"
				+ "where a.prod_no=? and prod_flag='0' and shkno =? ";*/
		String sql = "select order_id,prod_id,cust_name,return_date,pllot_money,return_coe from product_issue where prod_id=? and return_batch=?";
		return jt.queryForList(sql,prod_id,prod_rs_id);
	}
	
	
	public int[] prod_yfb(ArrayList<Object[]> li){
			/*String sql="INSERT INTO public.product_issue(prod_rs_id, shkno,order_id,cust_name, pllot_money, "
					+ "return_coe, return_date, prod_id,sale_id,order_no)  VALUES (?, ?,?, ?, ?, ?, cast(? as date),?,?,?)";*/
			String sql="INSERT INTO public.product_issue(prod_rs_id, return_batch,order_id,cust_name,return_money, "
					+ "return_coe, return_date, prod_id,sale_id,order_no)  VALUES (?, ?,?, ?, ?,?,cast(? as date),?,?,?)";
			return jt.batchUpdate(sql, li);
		}


	/**
	 * 查询lp下是否有费用管理
	 * @param prod_id
	 * @param lp_id
	 * @return
	 */
	public  List<Map<String,Object>> lp_feeList(long prod_id,long lp_id,String mng_module){
		String sql = "SELECT prod_fee_id, lp_id, prod_id, mng_module, mng_type, to_char(start_date,'yyyy-mm-dd') start_date, "
				+ "to_char(end_date,'yyyy-mm-dd') end_date, mng_fee FROM public.product_fee where  prod_id=? and lp_id=? and mng_module=?";
		
		list = jt.queryForList(sql,prod_id,lp_id,mng_module);
		return list; 
	}
	
	
    /**
	 * 根据产品id查询年
	 * @param prod_id
	 * @return
	 */
	public  List<Map<String,Object>> dateList(long prod_id){
		String sql = "SELECT  to_char(s_raise_date,'yyyy') s_raise_date ,to_char( e_raise_date,'yyyy') e_raise_date "
				+ "FROM product_info where prod_id=?";
		
		list = jt.queryForList(sql,prod_id);
		return list; 
	}
	
	
	/**
	 * 批量插入mana
	 * @param list
	 * @throws ParseException 
	 */
	public int[] insert_fee_mana(List<Map<String, Object>> list,long lp_id,long prod_id) throws ParseException{
		String sql = "INSERT INTO public.product_fee("
				+ "prod_fee_id, lp_id, prod_id, mng_module, mng_type, start_date, "
				+ "end_date, mng_fee)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < list.size(); i++) {
			double mng_fee = 0;
			if(!"".equals(list.get(i).get("mng_fee"))&&list.get(i).get("mng_fee")!=null){
				mng_fee = Double.parseDouble((String) list.get(i).get("mng_fee"));
			}
			
			Date s_date = sdf.parse((String)list.get(i).get("s_raise_date")) ;
			Date e_date = sdf.parse((String) list.get(i).get("e_raise_date")) ;
			Object[] e = { jdbcUtil.seq(),lp_id, prod_id,
					list.get(i).get("mng_module"),
					list.get(i).get("mng_type"),
					s_date,
					e_date,
					mng_fee
					 };
			batchArgs.add(e);
		}
		
		return jt.batchUpdate(sql, batchArgs);
	}
	
	public void save_fee_mana(List<Map<String, Object>> list) throws ParseException{
		String sql = "UPDATE public.product_fee SET  mng_fee=? WHERE  prod_fee_id=?";
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (int i = 0; i < list.size(); i++) {
			double mng_fee = 0;
			if(!"".equals(list.get(i).get("mng_fee"))&&list.get(i).get("mng_fee")!=null){
				mng_fee = Double.parseDouble((String) list.get(i).get("mng_fee"));
			}
			Object[] e = { 
					mng_fee,
					Long.parseLong((String) list.get(i).get("prod_fee_id"))
					 };
			batchArgs.add(e);
		}
		jt.batchUpdate(sql, batchArgs);
	}
	
	
	/**
	 * 查询产品渠道字典值
	 * @return
	 */
	public  List<Map<String,Object>> prodTypeDictInfo(String type){
		String sql="";
		if(type=="1"){  //渠道
			 sql="select dict_name,dict_value from data_dict where dict_type='prodType'";
		}
		else if(type=="2"){  //币种
			 sql="select dict_name,dict_value from data_dict where dict_type='prodCur'";
		}else if(type=="3"){
			 sql="select dict_name,dict_value from data_dict where dict_type='prodStus'";
		}
		return jt.queryForList(sql);
	}
	
	
	public  List<Map<String,Object>> HKNoInfo(long prod_id){
		String sql="select count(*)+1||'H'||prod_id as hkno from  return_money where prod_id=? group by prod_id";
		return jt.queryForList(sql,prod_id);
	}
	
	public  List<Map<String,Object>> sHKNoInfo(long prod_id){
		String sql="select count(*)+1||'H'||prod_id as shkno from  product_return_sale where prod_id=? group by prod_id";
		return jt.queryForList(sql,prod_id);
	}
	
	public  List<Map<String,Object>> sHKFNoInfo(long prod_id){
		String sql="select 1||'H'||prod_id as shkno from  product_info where prod_id = ?";
		return jt.queryForList(sql,prod_id);
	}
	
	public  List<Map<String,Object>> HKFNoInfo(long prod_id){
		String sql="select 1||'H'||prod_id as hkno from  product_info where prod_id = ?";
		return jt.queryForList(sql,prod_id);
	}

	public  List<Map<String,Object>> HKSNoInfo(){
		String sql="select  count(*) as hskno from product_return_sale";
		return jt.queryForList(sql);
	}
	
	public  List<Map<String,Object>> HKSNo(){
		String sql="select return_money,return_coe,hkno from return_money where hkno=(select min(hkno) from return_money)";
		return jt.queryForList(sql);
	}
	
	/**
	 * 根据gp_id获取管理方
	 * @return
	 */
	public  List<Map<String,Object>> gp_dept(long gp_id){
		String sql="select gp_dept from gp_info where gp_id=?";
		return jt.queryForList(sql,gp_id);
	}

	
	/**
	 * 获取产品详情页面按钮
	 * @return
	 */
	public List<Map<String, Object>> prodMenuInfo(long role_id) {
		String sql="";
		if(role_id==3){
			sql = "select menu_level_forprod,func_remark,func_describle,button_id,b.role_id from  func_info a,role_info c,role_func b "
					+" where a.func_id=b.func_id and b.role_id = c.role_id  and menu_level_forprod ='6' and b.role_id=? "
					+" order by role_id ";
		}else if(role_id==6){
			sql = "select menu_level_forprod,func_remark,func_describle,button_id,b.role_id from  func_info a,role_info c,role_func b "
			+" where a.func_id=b.func_id and b.role_id = c.role_id  and menu_level_forprod ='6' and b.role_id=? "
			+" order by role_id	";			
		}/*else if(role_id==7){
			sql = "select menu_level_forprod,func_remark,func_describle,button_id,b.role_id,a.func_id from  func_info a,role_info c,role_func b " 
				+" where a.func_id=b.func_id and b.role_id = c.role_id  and menu_level_forprod ='8' and b.role_id=? "
				+" order by role_id";			
		}*/else if(role_id==8){
			sql = "select menu_level_forprod,func_remark,func_describle,button_id,b.role_id,a.func_id from  func_info a,role_info c,role_func b " 
					+" where a.func_id=b.func_id and b.role_id = c.role_id  and menu_level_forprod ='6' and b.role_id=? "
					+" order by role_id";			
		}else if(role_id==9){
			sql = "select menu_level_forprod,func_remark,func_describle,button_id,b.role_id,a.func_id from  func_info a,role_info c,role_func b "
				+" where a.func_id=b.func_id and b.role_id = c.role_id   and b.role_id=? and menu_level_forprod='6' "
				+" order by role_id "; 			
			}
		
		return jt.queryForList(sql,role_id);
	}

	/**
	 * 获取产品详情页面按钮
	 * @return
	 */
	public List<Map<String, Object>> prodMenuInfo1() {
		String sql = "select func_describle,button_id,func_remark  from func_info where menu_level_forprod='7'";
		return jt.queryForList(sql);
	}
	
	/**
	 * 获取产品详情页面按钮
	 * @return
	 */
	public List<Map<String, Object>> prodMenuInfo2() {
		String sql = "select func_describle,button_id,func_remark  from func_info where menu_level_forprod='8'";
		return jt.queryForList(sql);
	}
	
	
	public List<Map<String, Object>> prodStatus(long prod_id) {
		String sql = "select prod_status from product_info  WHERE prod_id=?";
		return jt.queryForList(sql,prod_id);
	}
	
	/**
	 * 根据用户查询地区名称
	 */
	
	public List<Map<String, Object>> dictInfoByUser(long user_id) {
		String sql = " select dict_name,dict_value from upm_user a,data_dict b"
				+" where user_id = ?"
				+" and b.dict_type = 'dist'"
				+" and a.area = b.dict_value";
		return jt.queryForList(sql,user_id);
	}
	
	
	/**
	 * 客户打款列表查询
	 */
	  
		public List<Map<String, Object>> custDKInfo(long prod_id) {
			/*String sql = " select a.part_comp,a.order_no,a.contract_no,a.order_amount,b.pay_per,sum(b.pay_amount),b.stop_time, "
				+" sum(case when c.pay_type='0' then c.pay_amount else -c.pay_amount end ) pay_amount,c.pay_time,(c.pay_amount/b.pay_amount) as sjbl, "
				+" (CASE WHEN a.cust_type='1' THEN  e.cust_name  else  f.org_name  end ) cust_name,d.lp_id,d.partner_com_name "
				+" from order_info a	--订单   "
			    +"  inner join payment_call b on a.order_version = b.order_version and b.stateflag = '0' "
				+"	inner join payment_info c on a.order_no = c.order_id  "
				+"	left join lp_info d on a.part_comp = d.lp_id  "
				+"	left join bill_info g on a.cust_type = g.cust_type and a.cust_no = g.id "
				+"	left join cust_info e on e.cust_id = a.cust_no "
				+"	left join org_info f  on a.cust_no = f.org_id "
				+"	where  b.order_version = c.order_no "
				+"	and b.order_no = c.order_id "
				+"	and b.pay_step = c.pay_step and  lp_id  = a.part_comp and a.prod_no = ? "
				+"	group by a.part_comp,a.order_no,a.contract_no,a.order_amount,a.cust_type,b.pay_per,b.stop_time,c.pay_time,sjbl,d.lp_id,d.partner_com_name,e.cust_name,f.org_name "
				+" ORDER BY d.partner_com_name ";*/
			 String sql = " select a.part_comp,a.order_no,a.contract_no,to_char(coalesce(a.order_amount,0), 'fm99,999,999,999,999,999,990.00') order_amount,b.pay_per,"
			 	+ "to_char(coalesce(sum(b.pay_amount),0), 'fm99,999,999,999,999,999,990.00') true_amount,b.stop_time, "
				+" to_char(coalesce(sum(case when c.pay_type='0' then c.pay_amount else -c.pay_amount end ),0), 'fm99,999,999,999,999,999,990.00') pay_amount,c.pay_time,(c.pay_amount/b.pay_amount) as sjbl, "
				+" (CASE WHEN a.cust_type='1' THEN  e.cust_name  else  f.org_name  end ) cust_name,d.lp_id,d.partner_com_name "
				+" from order_info a  "
			    +"  inner join payment_call b on a.order_version = b.order_version and b.stateflag = '0' "
				+"	inner join payment_info c on a.order_no = c.order_id  "
				+"	left join lp_info d on a.part_comp = d.lp_id  "
				+"	left join bill_info g on a.cust_type = g.cust_type and a.cust_no = g.id "
				+"	left join cust_info e on e.cust_id = a.cust_no "
				+"	left join org_info f  on a.cust_no = f.org_id "
				+"	where  b.order_version = c.order_no "
				+"	and b.order_no = c.order_id "
				+"	and b.pay_step = c.pay_step and  lp_id  = a.part_comp and a.prod_no = ? "
				+"	group by a.part_comp,a.order_no,a.contract_no,a.order_amount,a.cust_type,b.pay_per,b.stop_time,c.pay_time,sjbl,d.lp_id,d.partner_com_name,e.cust_name,f.org_name "
				+" ORDER BY d.partner_com_name "; 
			return jt.queryForList(sql,prod_id);
		}

		/**
		 * 邮件审批
		 * @param email_id
		 * @param status
		 */
		public void prod_approve(long email_id,String status) {
			sql="UPDATE public.product_info SET check_status=? WHERE mail_no=?";
			 jt.update(sql,status,email_id);
		}
		
		
		
		
		/**
		 * 查询报表导出excel
		 * @return
		 */
		public SqlRowSet export_report(String prod_name,String prod_type,
					String prod_status, String lp_name, String cor_org,String s_raise_date,String s_raise_date1,Object user_id,int m,int n){
					String sql="select * from query_product_excel(?,?,?,?,?,?,?,?,?,?) as a(prod_name varchar,prodTypeName varchar,cor_org varchar, buy_fee text ,pay_amount text,s_raise_date date,Curname varchar,prodCheName varchar,prodStusName varchar)";
			return jt.queryForRowSet(sql, prod_name,prod_type,prod_status,lp_name,cor_org,s_raise_date,s_raise_date1,user_id,m,n);
		}
		/**
		 * 查询销售是否可见
		 * @return
		 */
		public List<Map<String, Object>> sale_query_diffcoe(int n,int m,String area){
			String sql = "select t1.prod_name,t2.prod_diffcoe,t2.partner_com_name from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id and position('"+area+"' in area)>0 and t1.check_status='2' limit ? OFFSET ?";
			return jt.queryForList(sql,n,m);
		}
		/**
		 * 销售可见数量
		 * @return
		 */
		public int sale_query_diffcoe_count(String area){
			String sql = "select count(*) from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id and position('"+area+"' in area)>0 and t1.check_status='2'";
			return jt.queryForInt(sql);
		}
		/**
		 * 查询所产品
		 * @return
		 */
		public List<Map<String, Object>> query_diffcoe_all(int n,int m){
			String sql = "select t1.prod_name,t2.prod_diffcoe,t2.partner_com_name from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id and t1.check_status='2'  limit ? OFFSET ?";
			return jt.queryForList(sql,n,m);
		}
		/**
		 * 查询所产品
		 * @return
		 */
		public int query_diffcoe_all_count(){
			String sql = "select count(*) from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id and t1.check_status='2'"
					+ " ";
			return jt.queryForInt(sql);
		}
		/**
		 * 查询自己录入的产品
		 * @param n
		 * @param m
		 * @return
		 */
		public List<Map<String, Object>> query_diffcoe_user(int n,int m,long user_id){
			String sql = "select t1.prod_name,t2.prod_diffcoe from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id and t1.user_id=? and t1.check_status='2' limit ? OFFSET ?";
			return jt.queryForList(sql,user_id,n,m);
		}
		/**
		 * 查询自己录入的数量
		 * @param user_id
		 * @return
		 */
		public int query_diffcoe_user_count(long user_id){
			String sql = "select count(*) from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id and t1.user_id=? and t1.check_status='2'";
			return jt.queryForInt(sql,user_id);
		}
		
		/**
		 * 产品名称查询 	查询销售是否可见
		 * @return
		 */
		public List<Map<String, Object>> sale_select_diffcoe(int n,int m,String prod_name,String area){
			String sql = "select t1.prod_name,t2.prod_diffcoe from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id  "
					+ "and t1.check_status='2' and t1.prod_name like '%"+prod_name+"%' "
					+ "and position('"+area+"' in area)>0 limit ? OFFSET ?";
			return jt.queryForList(sql,n,m);
		}
		/**
		 * 产品名称查询 销售可见数量
		 * @return
		 */
		public int sale_select_diffcoe_count(String prod_name,String area){
			String sql = "select count(*) from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id "
					+ " and t1.check_status='2' and t1.prod_name like '%"+prod_name+"%' "
					+ "and position('"+area+"' in area)>0";
			return jt.queryForInt(sql);
		}
		/**
		 * 产品名称查询 查询所产品
		 * @return
		 */
		public List<Map<String, Object>> select_diffcoe_all(int n,int m,String prod_name){
			String sql = "select t1.prod_name,t2.prod_diffcoe from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id "
					+ "and t1.check_status='2' and see_sale='1' and t1.prod_name like '%"+prod_name+"%' limit ? OFFSET ?";
			return jt.queryForList(sql,n,m);
		}
		/**
		 * 产品名称查询 查询所产品
		 * @return
		 */
		public int select_diffcoe_all_count(String prod_name){
			String sql = "select count(*) from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id "
					+ " and t1.check_status='2' and t1.prod_name like '%"+prod_name+"%'";
			return jt.queryForInt(sql);
		}
		/**
		 * 产品名称查询 查询自己录入的产品
		 * @param n
		 * @param m
		 * @return
		 */
		public List<Map<String, Object>> select_diffcoe_user(int n,int m,long user_id,String prod_name){
			String sql = "select t1.prod_name,t2.prod_diffcoe from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id "
					+ "and t1.user_id=? and t1.check_status='2' and t1.prod_name like '%"+prod_name+"%' limit ? OFFSET ?";
			return jt.queryForList(sql,user_id,n,m);
		}
		/**
		 * 产品名称查询	查询自己录入的数量
		 * @param user_id
		 * @return
		 */
		public int select_diffcoe_user_count(long user_id,String prod_name){
			String sql = "select count(*) from product_info t1,lp_info t2,lp_prod_rela t3 "
					+ "where t2.lp_id = t3.lp_id and t1.prod_id = t3.prod_id "
					+ "and t1.user_id=? and t1.check_status='2' and t1.prod_name like '%"+prod_name+"%'";
			return jt.queryForInt(sql,user_id);
		}
		/**
		 * 通过用户id查询地区
		 * @param user_id
		 * @return
		 */
		public List<Map<String, Object>> arealist(long user_id){
			String sql ="select area from upm_user where user_id =?";
			return jt.queryForList(sql,user_id);
		}
		/**
		 * 查询字典值
		 * @param dict_vuale
		 * @return
		 */
		public List<Map<String, Object>> areadict(String dict_vuale){
			String sql ="SELECT  dict_name, dict_value "
					+ "FROM public.data_dict where dict_type='dist' and dict_value=?;";
			return jt.queryForList(sql,dict_vuale);
		}

		public List<Map<String, Object>> edit_return(long prod_r_id) {
			String sql ="SELECT prod_r_id, "
					+ "to_char(COALESCE(return_money,0.00), 'fm99,999,999,999,999,999,990.00') return_money, "
					+ "prod_id, prod_flag, to_char(return_date,'yyyy-MM-dd'), hkno,  "
					+ "finahk_flag, return_coe "
					+ "FROM public.return_money where prod_r_id=?";
			
			return jt.queryForList(sql,prod_r_id);
		}
//更改回款
		public void edit_return(long prod_r_id, double return_money, Date date) {
			String sql = "update return_money set return_money=?,return_date=? where prod_r_id=?";
			jt.update(sql,return_money,date,prod_r_id);
		}
//审批财务回款
		public void approve_return(long prod_r_id, String finahk_flag) {
			// TODO Auto-generated method stub
			String sql = "update return_money set finahk_flag=? where prod_r_id=?";
			jt.update(sql,finahk_flag,prod_r_id);
		}

		public List<Map<String, Object>> item_edit_return(long prod_rs_id) {
			// TODO Auto-generated method stub
			String sql = "SELECT prod_rs_id, prod_id, issue_org, "
					+ "to_char(COALESCE(return_money,0.00), 'fm99,999,999,999,999,999,990.00') return_money, "
					+ "return_coe, s_date, e_date, return_finance_data,"
					+ " return_sales_data, to_char(return_date,'yyyy-MM-dd') return_date, prod_flag, order_id, shkno "
					+ "FROM public.product_return_sale where prod_rs_id = ?";
			return jt.queryForList(sql,prod_rs_id);
		}

		public void item_edit_return(long prod_rs_id, double return_money, Date date, double return_coe) {
			// TODO Auto-generated method stub
			String sql = "update product_return_sale "
					+ "set return_money=?,return_coe=?,return_date=? where prod_rs_id=?";
			jt.update(sql,return_money,return_coe,date,prod_rs_id);
			
		}

		public void item_approve_return(long prod_rs_id, String prod_flag) {
			// TODO Auto-generated method stub
			String sql = "update product_return_sale set prod_flag=? where prod_rs_id=?" ;
			jt.update(sql,prod_flag,prod_rs_id);
		}
}
