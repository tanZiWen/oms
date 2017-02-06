package com.prosnav.oms.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.util.IdGen;
import com.prosnav.oms.util.jdbcUtil;
import com.prosnav.oms.util.mailCache;
import com.prosnav.oms.util.sendMail;
import com.sun.mail.iap.Literal;

public class OrderDao {
	private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil
			.getBean("template");
	public static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	static String sql = "";

	// 新建订单
	public static int addOrder(String order_no, String prod_no, String cust_no,
			double order_amount, String area, String part_comp,
			String contract_type, String contract_no, String is_id,
			String id_no, double pri_fee, double acount_fee, double buy_fee,
			double start_fee, double delay_fee, double break_fee,
			double late_join_fee, String remark, String bank_no,
			String bank_card, String is_checked, String stateflag,
			String create_time) {
		sql = "INSERT INTO public.order_info(order_no, prod_no, cust_no, order_amount, area, part_comp, contract_type, contract_no, is_id, id_no, pri_fee, acount_fee, buy_fee, start_fee, delay_fee, break_fee, late_join_fee, remark, bank_no, bank_card, is_checked, stateflag, create_time)"
				+ "VALUES(to_char(current_timestamp,'YYYYMMDDHHMISS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, 1, current_timestamp)";
		return jt.update(sql, prod_no, cust_no, order_amount, area, part_comp,
				contract_type, contract_no, is_id, id_no, pri_fee, acount_fee,
				buy_fee, start_fee, delay_fee, break_fee, late_join_fee,
				remark, bank_no, bank_card);
	}

	// 新建订单销售关系
	public static int addRelOrderSales(String order_no, String sales_id,
			String percentage, String order_version, String stateflag) {
		sql = "INSERT INTO public.rel_order_sales(order_no, sales_id, percentage, order_version, stateflag) "
				+ "VALUES(?,?,?,?,?);";
		return jt.update(sql, order_no, sales_id, percentage, order_version,
				stateflag);
	}

	// 订单列表查询全量
	public List<Map<String, Object>> orderInfoSelect(int n, int m) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name,t1.entry_time, "
				+ "t11.dict_name prod_type,t3.prod_name, "
				+ "t1.prod_diffcoe, t1.cust_cell as order_cell, ci.cust_name as investor_name,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "left join cust_info ci on t1.investor_no = ci.cust_id "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' order by t1.order_no limit ? OFFSET ?   ";
		/* limit ? OFFSET ? */
		/* ,n,m */

		list = jt.queryForList(sql,n,m);
		return list;
	}
	// 订单列表查询全量 条数
		public int orderInfoSelect_cou() {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT count(*) "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "  where t1.stateflag='0'   ";
			/* limit ? OFFSET ? */
			/* ,n,m */

			return jt.queryForInt(sql);
		}
	
	/**
	 * 查询运营操作职能
	 * @param user_id
	 * @param n
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> orderInfoSelect(long user_id,int n, int m) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name,t1.entry_time, "
				+ "t11.dict_name prod_type,t3.prod_name, "
				+ "t1.prod_diffcoe, t1.cust_cell as order_cell, ci.cust_name as investor_name,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "left join cust_info ci on t1.investor_no = ci.cust_id "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t1.register=? order by t1.order_no limit ? OFFSET ? ";
		/* limit ? OFFSET ? */
		/* ,n,m */

		list = jt.queryForList(sql,user_id,n,m);
		return list;
	}
	/**
	 * 查询运营操作职能 条数
	 * @param user_id
	 * @param n
	 * @param m
	 * @return
	 */
	public int orderInfoSelect_cou(long user_id) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT count(*) "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t1.register=?   ";
		/* limit ? OFFSET ? */
		/* ,n,m */

		return jt.queryForInt(sql,user_id);
		
	}
	
	/**
	 * 查询产品操作职能
	 * @param user_id
	 * @param n
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> prod_orderInfoSelect(long user_id,int n, int m) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name, "
				+ "t11.dict_name prod_type,t3.prod_name, "
				+ "t1.prod_diffcoe, t1.cust_cell as order_cell,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t3.user_id=? order by t1.order_no limit ? OFFSET ? ";
		/* limit ? OFFSET ? */
		/* ,n,m */

		list = jt.queryForList(sql,user_id,n,m);
		return list;
	}
	/**
	 * 查询产品操作职能 条数
	 * @param user_id
	 * @param n
	 * @param m
	 * @return
	 */
	public int prod_orderInfoSelect_cou(long user_id) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT  count(*)"
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t3.user_id=?  ";
		/* limit ? OFFSET ? */
		/* ,n,m */

		return jt.queryForInt(sql,user_id);
		
	}
	
	//销售订单列表查询
	public List<Map<String, Object>> sale_orderInfoSelect(int n, int m,long sale_id) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name, "
				+ "t11.dict_name prod_type,t3.prod_name, "
				+ "t1.prod_diffcoe, t1.cust_cell as order_cell, ci.cust_name as investor_name,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "left join cust_info ci on t1.investor_no = ci.cust_id "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "  where t1.stateflag='0' and t2.sales_id=? order by t1.order_no limit ? OFFSET ? ";
		/* limit ? OFFSET ? */
		/* ,n,m */

		list = jt.queryForList(sql,sale_id,n,m);
		return list;
	}
	//销售订单列表查询总条数
		public int sale_orderInfoSelect_cou(long sale_id) {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT count(*) "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "  where t1.stateflag='0' and t2.sales_id=?  ";
			/* limit ? OFFSET ? */
			/* ,n,m */

			return jt.queryForInt(sql,sale_id);
		}
	/**
	 * 团队职能列表订单列表查看
	 * @param n
	 * @param m
	 * @param sale_id
	 * @return
	 */
	public List<Map<String, Object>> team_sale_orderInfoSelect(int n, int m,long sale_id) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name, "
				+ "t11.dict_name prod_type,t3.prod_name, "
				+ "t1.prod_diffcoe, t1.cust_cell as order_cell, ci.cust_name as investor_name,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "left join cust_info ci on t1.investor_no = ci.cust_id "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t2.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
				+ "where a.workgroup_id=b.workgroup_id) order by t1.order_no limit ? OFFSET ? ";

		/* limit ? OFFSET ? */
		/* ,n,m */

		list = jt.queryForList(sql,sale_id,n,m);
		return list;
	}
	/**
	 * 团队职能列表订单列表 条数
	 * @param n
	 * @param m
	 * @param sale_id
	 * @return
	 */
	public int team_sale_orderInfoSelect_cou(long sale_id) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT count(*) "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t2.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
				+ "where a.workgroup_id=b.workgroup_id)  ";

		/* limit ? OFFSET ? */
		/* ,n,m */

		return jt.queryForInt(sql,sale_id);
	}
	/**
	 * 分公司职能列表订单列表查看
	 * @param n
	 * @param m
	 * @param sale_id
	 * @return
	 */
	public List<Map<String, Object>> org_team_sale_orderInfoSelect(int n, int m,long sale_id) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name,t1.entry_time, "
				+ "t11.dict_name prod_type,t3.prod_name, "
				+ "t1.prod_diffcoe, t1.cust_cell as order_cell, ci.cust_name as investor_name,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "left join cust_info ci on t1.investor_no = ci.cust_id "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t2.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,org_code from upm_user where user_id=?) b  "
				+ "where a.org_code=b.org_code) order by t1.order_no limit ? OFFSET ?  ";

		/* limit ? OFFSET ? */
		/* ,n,m */

		list = jt.queryForList(sql,sale_id ,n,m);
		return list;
	}
	
	/**
	 * 分公司职能列表订单列表查看 条数
	 * @param n
	 * @param m
	 * @param sale_id
	 * @return
	 */
	public int org_team_sale_orderInfoSelect_cou(long sale_id) {

		/* System.out.println("-----------已进入orderInfoSelect"); */
		sql = "SELECT count(*) "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t2.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,org_code from upm_user where user_id=?) b  "
				+ "where a.org_code=b.org_code)   ";

		/* limit ? OFFSET ? */
		/* ,n,m */

		return jt.queryForInt(sql,sale_id );
	}
	//销售订单列表查询
		public List<Map<String, Object>> teamsale_orderInfoSelect(int n, int m,Object[] team) {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT t1.order_no,t1.order_version, t1.create_time, t1.area,t1.part_comp,t1.cust_no, "
					+ "t1.is_checked, t1.buy_fee, "
					+ "t2.sales_name, t2.magt_fee, t3.prod_name, "
					+ "t3.prod_type,t3.prod_name, "
					+ "t4.prod_diffcoe, "
					+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
					+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
					+ "FROM public.order_info t1 "
					+ "left join sale_order t2 on t2.order_no=t1.order_no  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.partner_com_name=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "  where t1.stateflag='0' and t2.sales_id=? ";
			/* limit ? OFFSET ? */
			/* ,n,m */
			List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
			
			//list = jt.queryForList(sql,sale_id);
			for(int i=0;i<team.length;i++){
				list = jt.queryForList(sql,team[i]);
				
			}
			return list;
		}

	// 订单列表查询 按条件查
	public List<Map<String, Object>> orderSelect(String prod_status,
			String cust_name, String customer_cell, String sales_name, String prod_name, int n,
			int m, String order_no) {
		/* System.out.println("-----------已进入orderSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name,t3.prod_currency, "
				+ "t11.dict_name prod_type,  t1.cust_cell as order_cell,"
				+ "t1.prod_diffcoe,  t1.cust_cell as order_cell,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' ";
		if (prod_status != "0" && !prod_status.equals("0")) {
			sql = sql + " and t3.prod_currency='" + prod_status + "'";
		}

		if (cust_name != "" && cust_name != null) {
			sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
		}
		if (customer_cell != "" && customer_cell != null) {
			sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
		}
		if (sales_name != "" && sales_name!=null) {
			sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
		}
		if (prod_name != "" && prod_name != null) {
			sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
		}
		if (order_no != "" && order_no != null) {
			sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
		}
		// 分页查询
		sql = sql + " order by t1.order_no limit ? OFFSET ?";
		list = jt.queryForList(sql, n, m);
		return list;
	}
	
	// 订单列表查询 按条件查
		public int orderSelect_cou(String prod_status,
				String cust_name, String customer_cell, String sales_name, String prod_name, String order_no) {
			/* System.out.println("-----------已进入orderSelect"); */
			sql = "SELECT count(*)  "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "  where t1.stateflag='0' ";
			if (prod_status != "0" && !prod_status.equals("0")) {
				sql = sql + " and t3.prod_currency='" + prod_status + "'";
			}

			if (cust_name != "" && cust_name != null) {
				sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
			}
			if (customer_cell != "" && customer_cell != null) {
				sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
			}
			if (sales_name != "" && sales_name!=null) {
				sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
			}
			if (prod_name != "" && prod_name != null) {
				sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
			}
			if (order_no != "" && order_no != null) {
				sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
			}
			return jt.queryForInt(sql);

		}
	
	/**
	 * 运营操作职能
	 * @param prod_status
	 * @param cust_name
	 * @param customer_cell
	 * @param sales_name
	 * @param prod_name
	 * @param user_id
	 * @param n
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> option_orderSelect(String prod_status,
			String cust_name, String customer_cell, String sales_name, String prod_name,long user_id, int n,
			int m, String order_no) {
		/* System.out.println("-----------已进入orderSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name,t3.prod_currency, "
				+ "t11.dict_name prod_type, "
				+ "t1.prod_diffcoe,  t1.cust_cell as order_cell,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t1.register=? ";
		if (prod_status != "0" && !prod_status.equals("0")) {
			sql = sql + " and t3.prod_currency='" + prod_status + "'";
		}

		if (cust_name != "" && cust_name != null) {
			sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
		}
		if (customer_cell != "" && customer_cell != null) {
			sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
		}
		if (sales_name != "" && sales_name!=null) {
			sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
		}
		if (prod_name != "" && prod_name != null) {
			sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
		}
		if (order_no != "" && order_no != null) {
			sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
		}
		// 分页查询
		sql = sql + " order by t1.order_no limit ? OFFSET ?";
		list = jt.queryForList(sql,user_id, n, m);
		return list;
	}
	
	/**
	 * 运营操作职能       条数
	 * @param prod_status
	 * @param cust_name
	 * @param customer_cell
	 * @param sales_name
	 * @param prod_name
	 * @param user_id
	 * @param n
	 * @param m
	 * @return
	 */
	public int option_orderSelect_cou(String prod_status,
			String cust_name, String customer_cell, String sales_name, String prod_name,long user_id, String order_no) {
		/* System.out.println("-----------已进入orderSelect"); */
		sql = "SELECT count(*)  "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t1.register=? ";
		if (prod_status != "0" && !prod_status.equals("0")) {
			sql = sql + " and t3.prod_currency='" + prod_status + "'";
		}

		if (cust_name != "" && cust_name != null) {
			sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
		}
		if (customer_cell != "" && customer_cell != null) {
			sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
		}
		if (sales_name != "" && sales_name!=null) {
			sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
		}
		if (prod_name != "" && prod_name != null) {
			sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
		}
		if (order_no != "" && order_no != null) {
			sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
		}
		return  jt.queryForInt(sql,user_id);

	}
	
	/**
	 * 产品操作职能
	 * @param prod_status
	 * @param cust_name
	 * @param customer_cell
	 * @param sales_name
	 * @param prod_name
	 * @param user_id
	 * @param n
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> prod_orderSelect(String prod_status,
			String cust_name, String customer_cell, String sales_name, String prod_name,long user_id, int n,
			int m) {
		/* System.out.println("-----------已进入orderSelect"); */
		sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name,t3.prod_currency, "
				+ "t11.dict_name prod_type, "
				+ "t1.prod_diffcoe,  t1.cust_cell as order_cell,"
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t3.user_id=? ";
		if (prod_status != "0" && !prod_status.equals("0")) {
			sql = sql + " and t3.prod_currency='" + prod_status + "'";
		}

		if (cust_name != "" && cust_name != null) {
			sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
		}
		if (customer_cell != "" && customer_cell != null) {
			sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
		}
		if (sales_name != "" && sales_name!=null) {
			sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
		}
		if (prod_name != "" && prod_name != null) {
			sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
		}
		// 分页查询
		sql = sql + " order by t1.order_no limit ? OFFSET ?";
		list = jt.queryForList(sql,user_id, n, m);
		return list;
	}
	
	/**
	 * 产品操作职能 条数
	 * @param prod_status
	 * @param cust_name
	 * @param customer_cell
	 * @param sales_name
	 * @param prod_name
	 * @param user_id
	 * @param n
	 * @param m
	 * @return
	 */
	public int prod_orderSelect_cou(String prod_status,
			String cust_name, String customer_cell, String sales_name, String prod_name,long user_id) {
		/* System.out.println("-----------已进入orderSelect"); */
		sql = "SELECT count(*)  "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
				+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
				+ "  where t1.stateflag='0' and t3.user_id=? ";
		if (prod_status != "0" && !prod_status.equals("0")) {
			sql = sql + " and t3.prod_currency='" + prod_status + "'";
		}

		if (cust_name != "" && cust_name != null) {
			sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
		}
		if (customer_cell != "" && customer_cell != null) {
			sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
		}
		if (sales_name != "" && sales_name!=null) {
			sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
		}
		if (prod_name != "" && prod_name != null) {
			sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
		}

		return  jt.queryForInt(sql,user_id);
	}
	
	/**
	 * 根据条件查询（销售个人）
	 * @param prod_status
	 * @param cust_name
	 * @param customer_cell
	 * @param sales_name
	 * @param prod_name
	 * @param n
	 * @param m
	 * @return
	 */
		public List<Map<String, Object>> sale_orderSelect(String prod_status,
				String cust_name, String customer_cell, String sales_name, String prod_name, int n,
				int m,long user_id, String order_no) {
			/* System.out.println("-----------已进入orderSelect"); */
			sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
					+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
					+ "t2.sales_name, t2.magt_fee, t3.prod_name,t3.prod_currency, "
					+ "t11.dict_name prod_type,  t1.cust_cell as order_cell,"
					+ "t1.prod_diffcoe, "
					+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
					+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "  where t1.stateflag='0' and t2.sales_id=?   ";
			if (prod_status != "0" && !prod_status.equals("0")) {
				sql = sql + " and t3.prod_currency='" + prod_status + "'";
			}

			if (cust_name != "" && cust_name != null) {
				sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
			}
			if (customer_cell != "" && customer_cell != null) {
				sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
			}
			if (sales_name != "" && sales_name!=null) {
				sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
			}
			if (prod_name != "" && prod_name != null) {
				sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
			}
			if (order_no != "" && order_no != null) {
				sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
			}
			// 分页查询
			sql = sql + " order by t1.order_no limit ? OFFSET ?";
			list = jt.queryForList(sql,user_id, n, m);
			return list;
		}
		
		/**
		 * 根据条件查询（销售个人） 条数
		 * @param prod_status
		 * @param cust_name
		 * @param customer_cell
		 * @param sales_name
		 * @param prod_name
		 * @param n
		 * @param m
		 * @return
		 */
			public int sale_orderSelect_cou(String prod_status,
					String cust_name, String customer_cell, String sales_name, String prod_name, long user_id, String order_no) {
				/* System.out.println("-----------已进入orderSelect"); */
				sql = "SELECT count(*) "
						+ "FROM public.order_info t1 "
						+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
						+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
						+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
						+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
						+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
						+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
						+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
						+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
						+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
						+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
						+ "  where t1.stateflag='0' and t2.sales_id=?   ";
				if (prod_status != "0" && !prod_status.equals("0")) {
					sql = sql + " and t3.prod_currency='" + prod_status + "'";
				}

				if (cust_name != "" && cust_name != null) {
					sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
				}
				if (customer_cell != "" && customer_cell != null) {
					sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
				}
				if (sales_name != "" && sales_name!=null) {
					sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
				}
				if (prod_name != "" && prod_name != null) {
					sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
				}
				
				if (order_no != "" && order_no != null) {
					sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
				}

				return jt.queryForInt(sql,user_id);
			}
		
		/**
		 * 根据条件查询（销售团队老大）
		 * @param prod_status
		 * @param cust_name
		 * @param customer_cell
		 * @param sales_name
		 * @param prod_name
		 * @param n
		 * @param m
		 * @return
		 */
			public List<Map<String, Object>> team_sale_orderSelect(String prod_status,
					String cust_name, String customer_cell, String sales_name, String prod_name, int n,
					int m,long user_id, String order_no) {
				/* System.out.println("-----------已进入orderSelect"); */
				sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
						+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
						+ "t2.sales_name, t2.magt_fee, t3.prod_name,t3.prod_currency, "
						+ "t11.dict_name prod_type, "
						+ "t1.prod_diffcoe, t1.cust_cell as order_cell, "
						+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
						+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
						+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
						+ "FROM public.order_info t1 "
						+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
						+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
						+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
						+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
						+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
						+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
						+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
						+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
						+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
						+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
						+ "  where t1.stateflag='0' and t2.sales_id in(select a.user_id from upm_user a,"
					+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
					+ "where a.workgroup_id=b.workgroup_id)   ";
				if (prod_status != "0" && !prod_status.equals("0")) {
					sql = sql + " and t3.prod_currency='" + prod_status + "'";
				}

				if (cust_name != "" && cust_name != null) {
					sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
				}
				if (customer_cell != "" && customer_cell != null) {
					sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
				}
				if (sales_name != "" && sales_name!=null) {
					sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
				}
				if (prod_name != "" && prod_name != null) {
					sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
				}
				if (order_no != "" && order_no != null) {
					sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
				}
				// 分页查询
				sql = sql + " order by t1.order_no limit ? OFFSET ?";
				list = jt.queryForList(sql,user_id, n, m);
				return list;
			}
			
			/**
			 * 根据条件查询（销售团队老大） 条数
			 * @param prod_status
			 * @param cust_name
			 * @param customer_cell
			 * @param sales_name
			 * @param prod_name
			 * @param n
			 * @param m
			 * @return
			 */
				public int team_sale_orderSelect_cou(String prod_status,
						String cust_name, String customer_cell, String sales_name, String prod_name,long user_id, String order_no) {
					/* System.out.println("-----------已进入orderSelect"); */
					sql = "SELECT count(*)  "
							+ "FROM public.order_info t1 "
							+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
							+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
							+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
							+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
							+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
							+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
							+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
							+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
							+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
							+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
							+ "  where t1.stateflag='0' and t2.sales_id in(select a.user_id from upm_user a,"
						+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
						+ "where a.workgroup_id=b.workgroup_id)   ";
					if (prod_status != "0" && !prod_status.equals("0")) {
						sql = sql + " and t3.prod_currency='" + prod_status + "'";
					}

					if (cust_name != "" && cust_name != null) {
						sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
					}
					if (customer_cell != "" && customer_cell != null) {
						sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
					}
					if (sales_name != "" && sales_name!=null) {
						sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
					}
					if (prod_name != "" && prod_name != null) {
						sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
					}
					if (order_no != "" && order_no != null) {
						sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
					}
					return jt.queryForInt(sql,user_id);
				}
			
			/**
			 * 根据条件查询（销售分公司）
			 * @param prod_status
			 * @param cust_name
			 * @param customer_cell
			 * @param sales_name
			 * @param prod_name
			 * @param n
			 * @param m
			 * @param user_id
			 * @return
			 */
			public List<Map<String, Object>> org_orderSelect(String prod_status,
					String cust_name, String customer_cell, String sales_name, String prod_name, int n,
					int m,long user_id, String order_no) {
				/* System.out.println("-----------已进入orderSelect"); */
				sql = "SELECT t1.order_no,t1.order_version,t1.order_amount, t1.create_time, t10.dict_name area,t1.part_comp,t1.cust_no, "
						+ "t1.is_checked, t1.buy_fee,t4.partner_com_name,t1.entry_time, "
						+ "t2.sales_name, t2.magt_fee, t3.prod_name,t3.prod_currency, "
						+ "t11.dict_name prod_type, "
						+ "t1.prod_diffcoe, t1.cust_cell as order_cell, "
						+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
						+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
						+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
						+ "FROM public.order_info t1 "
						+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
						+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
						+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
						+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
						+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
						+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
						+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
						+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
						+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
						+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
						+ "  where t1.stateflag='0' t2.sales_id in(select a.user_id from upm_user a,"
						+ "(select user_id,org_code from upm_user where user_id=?) b  "
						+ "where a.org_code=b.org_code) ";
				if (prod_status != "0" && !prod_status.equals("0")) {
					sql = sql + " and t3.prod_currency='" + prod_status + "'";
				}

				if (cust_name != "" && cust_name != null) {
					sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
				}
				if (customer_cell != "" && customer_cell != null) {
					sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
				}
				if (sales_name != "" && sales_name!=null) {
					sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
				}
				if (prod_name != "" && prod_name != null) {
					sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
				}
				if (order_no != "" && order_no != null) {
					sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
				}
				// 分页查询
				sql = sql + " order by t1.order_no limit ? OFFSET ?";
				list = jt.queryForList(sql,user_id, n, m);
				return list;
			}
			
			/**
			 * 根据条件查询（销售分公司） 条数
			 * @param prod_status
			 * @param cust_name
			 * @param customer_cell
			 * @param sales_name
			 * @param prod_name
			 * @param n
			 * @param m
			 * @param user_id
			 * @return
			 */
			public int org_orderSelect_cou(String prod_status,
					String cust_name, String customer_cell, String sales_name, String prod_name,long user_id, String order_no) {
				/* System.out.println("-----------已进入orderSelect"); */
				sql = "SELECT count(*)  "
						+ "FROM public.order_info t1 "
						+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
						+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
						+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
						+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
						+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
						+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
						+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
						+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
						+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
						+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
						+ "  where t1.stateflag='0' t2.sales_id in(select a.user_id from upm_user a,"
						+ "(select user_id,org_code from upm_user where user_id=?) b  "
						+ "where a.org_code=b.org_code) ";
				if (prod_status != "0" && !prod_status.equals("0")) {
					sql = sql + " and t3.prod_currency='" + prod_status + "'";
				}

				if (cust_name != "" && cust_name != null) {
					sql = sql + " and cust_name like'" +"%"+cust_name + "%" + "'";
				}
				if (customer_cell != "" && customer_cell != null) {
					sql = sql + "  and cust_cell like '"+ customer_cell + "%" + "'";
				}
				if (sales_name != "" && sales_name!=null) {
					sql = sql + " and t2.sales_name like'" +"%"+ sales_name + "%" + "'";
				}
				if (prod_name != "" && prod_name != null) {
					sql = sql + "  and t3.prod_name like'" +"%"+prod_name + "%" + "'";
				}
				if (order_no != "" && order_no != null) {
					sql = sql + "  and t1.order_no =" + Long.parseLong(order_no);
				}
				return  jt.queryForInt(sql,user_id);
			}
	/**
	 * 根据币种查询
	 * @param prod_status
	 * @param cust_name
	 * @param customer_cell
	 * @param sales_name
	 * @param prod_name
	 * @param n
	 * @param m
	 * @return
	 */
	public List<Map<String, Object>> orderSelect(String prod_status,
			 int n,
			int m) {
		/* System.out.println("-----------已进入orderSelect"); */
		sql = "SELECT t1.order_no,t1.order_version, t1.create_time, t1.area,t1.part_comp,t1.cust_no, "
				+ "t1.is_checked, t1.buy_fee, "
				+ "t2.sales_name, t2.magt_fee, t3.prod_name, "
				+ "t3.prod_type,t3.prod_name, "
				+ "t4.prod_diffcoe, "
				+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ "FROM public.order_info t1 "
				+ "left join sale_order t2 on t2.order_no=t1.order_no  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ "left join LP_info t4 on t4.partner_com_name=t1.part_comp "
				+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+ "  where t1.stateflag='0' ";
		if (prod_status != "0" && !prod_status.equals("0")) {
			sql = sql + " and t3.prod_currency='" + prod_status + "'";
		}

		
		// 分页查询
		sql = sql + "  limit ? OFFSET ?";
		list = jt.queryForList(sql, n, m);
		return list;
	}
	//列表按条件查询
	public List<Map<String, Object>> sale_orderSelect(String prod_status,
			String cust_name, String customer_cell, String sales_name, String prod_name,long sale_id, int n,
			int m) {
		/* System.out.println("-----------已进入orderSelect"); */
		sql = "select * from (SELECT "
				+ "t1.order_no,t1.order_version, "
				+ "t1.create_time, t1.area,t1.part_comp, "
				+ "t1.is_checked, t1.buy_fee, "
				+ "t2.sales_name, t2.magt_fee, "
				+ "t3.prod_type,t3.prod_name,t3.prod_currency,  "
				+ "t4.prod_diffcoe, t8.dict_name as bizhong,t9.dict_name ,  "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
				+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
				+ " FROM public.order_info t1 "
				+ " left join sale_order t2 on t2.order_no=t1.order_no and t2.order_version = t1.order_version  "
				+ " left join product_info t3 on t3.prod_id=t1.prod_no  "
				+ " left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ " LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
				+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
				+" INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
				+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
				+" LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
				+"  where t1.stateflag='0' and t2.sales_id=?" + ") t8 where 1=1 ";
		if (prod_status != "0" && !prod_status.equals("0")) {
			sql = sql + " and t8.prod_currency='" + prod_status + "'";
		}

		if (cust_name != "" && cust_name != null) {
			sql = sql + " and t8.cust_name like'" +"%"+cust_name + "%" + "'";
		}
		if (customer_cell != "" && customer_cell != null) {
			sql = sql + "  and t8.cust_cell like '"+ customer_cell + "%" + "'";
		}
		if (sales_name != "" && sales_name!=null) {
			sql = sql + " and t8.sales_name like'" +"%"+ sales_name + "%" + "'";
		}
		if (prod_name != "" && prod_name != null) {
			sql = sql + "  and t8.prod_name like'" +"%"+prod_name + "%" + "'";
		}
		// 分页查询
		sql = sql + "    limit ? OFFSET ?";
		list = jt.queryForList(sql,sale_id, n, m);
		return list;
	}

	// 根据电话号码获取顾客信息
	public static List<Map<String, Object>> getcustomer_name(
			String customer_cell, int m, int n) {
		sql = "select * from cust_info where cust_cell=? limit ? OFFSET ?";
		list = jt.queryForList(sql, customer_cell, n, m);
		return list;
	}

	// 添加缴款计划
	public void doPay(double pay_amount, String stop_time, String pay_step,
			long order_no, String pay_per, long order_version) {
		sql = "INSERT INTO public.payment_call(payment_id,pay_amount, stop_time, pay_step, order_no, pay_per, order_version, stateflag,pay_state ) "
				+ " VALUES( ?,?, ?,? ,?, ?,?,'0','2')";
		 jt.update(sql,jdbcUtil.seq(), pay_amount, stop_time, pay_step, order_no,
				pay_per, order_version);
		
		/*try {
			return jt.update(sql, pay_amount, stop_time, pay_step, order_no,
					pay_per, order_version);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}*/
	}

	
	/**
	 * 添加打款记录
	 * @param cust_id
	 * @param order_no
	 * @param transaction_type
	 * @param remark
	 * @param cust_type
	 * @param pay_amount
	 * @param pay_time
	 * @param pay_step
	 * @return
	 */
	public int payment(long cust_id, long order_no,String transaction_type,String remark, String cust_type,
			double pay_amount, String pay_time, String pay_step,long order_version) {
		sql = "INSERT INTO public.payment_info("
				+ "pay_step, cust_type, cust_id, order_no,transaction_type,remark, "
				+ "pay_amount, pay_time, pay_type,stateflag,pay_state,order_id,payment_id) "
				+ " VALUES( ?, ?,? ,?,?, ?,?,?,'0','0','2',?,?)";
		try {
			return jt.update(sql, pay_step, cust_type, cust_id, order_version,transaction_type,remark,
					pay_amount, pay_time,order_no,jdbcUtil.seq());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 添加退款
	 * 
	 * @param cust_id
	 * @param order_no
	 * @param cust_type
	 * @param pay_amount
	 * @param pay_time
	 * @return
	 */

	public void refund(long cust_id, long order_no, String cust_type, 
			double pay_amount, String pay_time,String pay_step,long order_id) {
		sql = "INSERT INTO public.payment_info( cust_type, cust_id, order_no, pay_amount,"
				+ " pay_time, pay_type,stateflag,pay_step,order_id,transaction_type,pay_state,payment_id ) "
				+ " VALUES( ?, ?,? ,?, ?,'1','0',?,?,'退款','2',?)";
			 jt.update(sql, cust_type, cust_id, order_no, pay_amount, pay_time,pay_step,order_id,jdbcUtil.seq());
		
	}

	// 注释掉的订单详情基本信息
	/*
	 * public List<Map<String, Object>> orderDetail(String order_no) { sql =
	 * "SELECT order_no, prod_no, cust_no, order_amount, part_comp, contract_type, contract_no,pri_fee, acount_fee, bank_no, bank_card,remark FROM public.order_info where order_no=?"
	 * ; list = jt.queryForList(sql,order_no); return list; }
	 */
	// 订单详情基本信息 ---订单
	public List<Map<String, Object>> orderDetail(long order_id,long order_version) {
		sql = "SELECT t1.order_no, t1.order_type,t1.contract_type, t1.pri_fee,t1.is_checked, "
				+ "t1.acount_fee,  t1.bank_no,t5.prod_name,t5.prod_id,t1.order_amount ,t1.buy_fee,t1.start_fee,"
				+ "t1.bank_card, t1.remark,t1.order_version, t1.id_no, t1.cust_no, t1.cust_email, t1.mail_address, t1.comment, t1.first_pay_time, t1.cust_address, "
				+ " t2.cust_type,t6.partner_com_name, t1.contract_no,"
				+ " CASE WHEN t2.cust_type='1' THEN  t3.cust_name  else  t4.org_name  end  cust_name  "
				+ "FROM public.order_info t1 "
				+ "LEFT JOIN bill_info t2 on t2.id=t1.cust_no  "
				+ "LEFT JOIN cust_info t3 on t3.cust_id=t1.cust_no   "
				+ " LEFT JOIN org_info t4 on t4.org_id=t1.cust_no  "
				+ " LEFT JOIN product_info t5 on t5.prod_id=t1.prod_no  "
				+ " LEFT JOIN lp_info t6 on t6.lp_id=t1.part_comp  "
				+ " where t1.order_no=? and t1.order_version=? and t1.stateflag='0' ";
		List<Map<String, Object>> list = jt.queryForList(sql, order_id,order_version);
		return list;
	}

	// 获取字典值--订单类型
	public List<Map<String, Object>> searchZiDian1(String dict_value) {
		sql = "select t1.dict_name from data_dict t1 where t1.dict_type='ord_type' "
				+ "and t1.dict_value=? and t1.create_by='dingdan'";
		return jt.queryForList(sql, dict_value);
	}

	// 获取字典值--合同类型
	public List<Map<String, Object>> searchZiDian2(String dict_value) {
		sql = "select t1.dict_name from data_dict t1 where t1.dict_type='con_type' "
				+ "and t1.dict_value=? and t1.create_by='dingdan'";
		return jt.queryForList(sql, dict_value);
	}

	// 订单详情基本信息 ---RM
	public List<Map<String, Object>> RMDetail(long order_id,
			long order_version, int n, int m) {
		sql = "SELECT t2.sales_name, t2.sales_point,t2.magt_fee, "
				+ "CASE WHEN t3.cust_type='1' THEN  t4.cust_name  else  t5.org_name  end  cust_name    "
				+ "FROM public.order_info t1 "
				+ "inner join sale_order t2 on t2.order_no=t1.order_no "
				+ "LEFT JOIN bill_info t3 on t3.id=t1.cust_no "
				+ "LEFT JOIN cust_info t4 on t4.cust_id=t1.cust_no  "
				+ "LEFT JOIN org_info t5 on t5.org_id=t1.cust_no  "
				+ " where t1.order_no=? and t1.stateflag='0' and t1.order_version=? limit ? OFFSET ? ";
		list = jt.queryForList(sql, order_id, order_version, n, m);
		return list;
	}

	// 订单详情基本信息 ---打款记录
	/**
	 * 打款记录查询
	 * @param order_id
	 * @param order_version
	 * @param n
	 * @param m
	 * @return
	 */
		public List<Map<String, Object>> paymentDetail(long order_id,
				long order_version, int n, int m) {
			sql="select t1.pay_step, t1.pay_amount,t1.pay_time,t1.payment_id,"
					+ "CASE WHEN  t2.cust_type='1' THEN  t6.cust_name else  t7.org_name  end  cust_name,"
					+ "t7.org_name,t3.stop_time, t1.cust_id, "
					+ "t4.delay_fee,t4.order_amount,"
					+ "t5.prod_name,t4.prod_no,t1.order_no,t1.order_id,t1.pay_type,"
					+ "t9.dict_name pay_dict_name,t1.pay_state  from payment_info t1 "
					+ "left join bill_info t2 on t2.id=t1.cust_id "
					+ "left join cust_info t6 on t6.cust_id=t1.cust_id "
					+ "left join org_info t7 on t7.org_id=t1.cust_id "
					+ "left join order_info t4 on t4.order_version=t1.order_no and t4.cust_no=t1.cust_id and t4.order_no=t1.order_id "
					+ "left join product_info t5 on  t5.prod_id=t4.prod_no "
					+ "left join payment_call t3 on t3.order_no=t1.order_id and t3.pay_step=t1.pay_step and t4.order_version=t3.order_version  "
					+ "left join data_dict t9 on t9.dict_value=t1.pay_state and t9.dict_type='orderflag' "
					+ "where  t1.order_no=? and t1.order_id=? and t1.pay_type='0'  and t1.stateflag='0'   "
					+ "ORDER BY t1.pay_step asc";
			List<Map<String, Object>> list = jt.queryForList(sql, order_version,order_id);
			return list;
		}

	// 查询打款记录
	public int searchPayment(long order_no) {
		int i = 0;
		sql = "select "
				+ "count"
				+ "(pay_step) from payment_info where order_no=? and stateflag='0' and pay_type='0'  GROUP BY order_no";
		try {
			i = jt.queryForInt(sql, order_no);
			return i;
		} catch (Exception e) {
			i = 0;
			return i;
		}
	}

	// 订单详情基本信息--退款信息
	public List<Map<String, Object>> drawbackDetail(long order_id,
			long order_version, int n, int m) {
		sql="select t1.pay_step, t1.pay_amount,t1.pay_time,"
				+ "CASE WHEN  t2.cust_type='1' THEN  t6.cust_name else  t7.org_name  end  cust_name,"
				+ "t7.org_name,t3.stop_time, t1.cust_id,t1.payment_id, "
				+ "t4.delay_fee,t4.order_amount,"
				+ "t5.prod_name,t4.prod_no,t1.order_no,t1.order_id,t1.pay_type,"
				+ "t9.dict_name pay_dict_name,t1.pay_state  from payment_info t1 "
				+ "left join bill_info t2 on t2.id=t1.cust_id "
				+ "left join cust_info t6 on t6.cust_id=t1.cust_id "
				+ "left join org_info t7 on t7.org_id=t1.cust_id "
				+ "left join order_info t4 on t4.order_version=t1.order_no and t4.cust_no=t1.cust_id and t4.order_no=t1.order_id "
				+ "left join product_info t5 on  t5.prod_id=t4.prod_no "
				+ "left join payment_call t3 on t3.order_no=t1.order_id and t3.pay_step=t1.pay_step and t4.order_version=t3.order_version  "
				+ "left join data_dict t9 on t9.dict_value=t1.pay_state and t9.dict_type='orderflag' "
				+ "where  t1.order_no=? and t1.order_id=? and t1.pay_type='1'  and t1.stateflag='0'   "
				+ "ORDER BY t1.pay_step asc";
		List<Map<String, Object>> list = jt.queryForList(sql, order_version,order_id);
		return list;
	}

	// 订单详情基本信息 ---佣金发放记录
	public List<Map<String, Object>> rewardDetail(long order_id, int n, int m) {
		sql = "SELECT t2.reward_out, t2.create_time, "
				+ "CASE WHEN t3.cust_type='1' THEN  t4.cust_name  else  t5.org_name  end  cust_name,  "
				+ "t6.prod_name  " + "FROM public.order_info t1 "
				+ "inner join reward_out t2 on t2.order_no=t1.order_no "
				+ "LEFT JOIN bill_info t3 on t3.id=t1.cust_no "
				+ "LEFT JOIN cust_info t4 on t4.cust_id=t1.cust_no  "
				+ "LEFT JOIN org_info t5 on t5.org_id=t1.cust_no   "
				+ "inner join product_info t6 on t6.prod_id=t1.prod_no "
				+ " where t1.order_no=?  and t1.stateflag='0' limit ? OFFSET ?";
		list = jt.queryForList(sql, order_id, n, m);
		return list;
	}

	// 订单详情基本信息 ---缴款计划
	public List<Map<String, Object>> planDetail(long order_id,
			long order_version, int n, int m) {
		sql = "SELECT t1.payment_id,t1.pay_step, t1.pay_per, t1.pay_amount, t1.stop_time, t1.order_no,t1.pay_step, "
				+ "t1.order_version, t1.stateflag, t1.email_id, t1.pay_state,t3.dict_name,t1.pay_state "
				+ "FROM public.payment_call t1 "
				+ "left join data_dict t3 on t3.dict_value = t1.pay_state and t3.dict_type = 'orderflag'"
				+ "where order_no=? and order_version=? and stateflag='0' ORDER BY pay_step asc   limit ? OFFSET ?";
		list = jt.queryForList(sql, order_id,order_version, n, m);
		return list;
	}

	// 订单详情基本信息---业绩
	public List<Map<String, Object>> achievementDetail(long order_id,
			long order_version, int n, int m) {
		sql = "select * from sale_order where order_no=? and order_version=? limit ? OFFSET ?";
		list = jt.queryForList(sql, order_id, order_version, n, m);
		return list;
	}

	// 订单详情基本信息---业绩分配
	public void achievementChange(List<Map<String, Object>> list, long order_no,
			long order_version) {
		sql = "update sale_order set sales_point=? , magt_fee=?  "
				+ " where sales_id=? and order_no=? and order_version=? ";
			for (int i = 0; i < list.size() - 1; i++) {
				jt.update(sql, (String)list.get(i).get("sales_point"), 
						Double.parseDouble((String)list.get(i).get("magt_fee")),
						Long.parseLong((String) list.get(i).get("sales_id")),
						order_no, order_version);
			}
	}

	// 订单详情基本信息 ---订单变更记录
	public List<Map<String, Object>> orderChangeDetail(long order_id,long order_version, int n,
			int m) {
		sql = "select t1.order_version,t2.sales_name,t2.sales_id,"
				+ "t1.create_time,t1.order_no from order_info t1,sale_order t2  "
				+ "where t1.order_no=t2.order_no and t1.order_no=? and t1.order_version <>? limit ? OFFSET ?";
		list = jt.queryForList(sql, order_id,order_version, n, m);
		return list;
	}
	// 订单详情基本信息 ---订单变更详情
		public List<Map<String, Object>> changedetail(long order_id,long order_version, long sales_id) {
			sql = "SELECT t1.order_no,t1.order_version,t1.order_amount,t1.pri_fee, t1.create_time, t1.area,t1.part_comp,t1.cust_no, "
					+ "t1.is_checked, t1.buy_fee,t1.id_no, "
					+ "t2.sales_name, t2.magt_fee, t3.prod_name,t4.partner_com_name, "
					+ "t3.prod_type,t3.prod_name, "
					+ "t3.prod_diffcoe, "
					+ " t5.cust_type, t8.dict_name as bizhong,t9.dict_name , "
					+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ " CASE WHEN t5.cust_type='1' THEN  t6.cust_cell  else  t7.org_license  end  cust_cell "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no   "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "  where  "
					+ " t1.order_no=t2.order_no and t1.order_no=? and t1.order_version =? and t2.sales_id=?";
			list = jt.queryForList(sql, order_id,order_version, sales_id);
			return list;
		}

	// 订单编辑页面查询
	public List<Map<String, Object>> orderEditSelect(long order_no,long order_version) {
		sql = "SELECT t1.order_no,t1.order_version,t1.area,t1.cust_no,t1.prod_diffcoe, t1.contract_type, t1.is_id,t1.contract_no, "
				+ " t1.order_type,"
				+ "to_char(coalesce(t1.order_amount,0), 'fm99,999,999,999,999,999,990') order_amount,"
				+ "to_char(coalesce(t1.acount_fee,0), 'fm99,999,999,999,999,999,990') acount_fee,"
				+ "to_char(coalesce(t1.pri_fee,0), 'fm99,999,999,999,999,999,990') pri_fee,"
				+ "to_char(coalesce(t1.buy_fee,0), 'fm99,999,999,999,999,999,990') buy_fee,"
				+ "to_char(coalesce(cast(t1.start_fee as numeric),0), 'fm99,999,999,999,999,999,990') start_fee,"
				+ "t1.prod_no, t1.cust_email, t1.mail_address, t1.work_address, t1.cust_cell, t1.first_pay_time, t1.comment, t1.extra_award,"
				+ "t1.remark,t1.bank_no,t1.bank_card,t1.id_no, "
				+ "t3.prod_type,t3.prod_name,t1.part_comp,t1.email_id, t1.cust_address, t1.entry_time,"
				/* + "t4.fund_mana_fee " */
				+ "t5.cust_idnum ,t6.cust_type,"
				+ "CASE WHEN t6.cust_type='1' THEN  t7.cust_name  else  t8.org_name  end  cust_name "
				+ "FROM public.order_info t1 "
				+ "left join sale_order t2 on t2.order_no=t1.order_no and t2.order_version=t1.order_version  "
				+ "left join product_info t3 on t3.prod_id=t1.prod_no "
				+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
				+ "left join cust_info t5 on t5.cust_id=t1.cust_no "
				+ "LEFT JOIN bill_info t6 on t6.id=t1.cust_no "
				+ "LEFT JOIN cust_info t7 on t7.cust_id=t1.cust_no  "
				+ "LEFT JOIN org_info t8 on t8.org_id=t1.cust_no   "
				+ "  where t1.stateflag='0' and t1.order_no=? and t1.order_version=?";
		list = jt.queryForList(sql, order_no,order_version);
		return list;
	}

	// 机构ID和姓名
	public List<Map<String, Object>> orgSelect() {
		sql = "select org_id,org_name from org_info ";
		list = jt.queryForList(sql);
		return list;
	}

	// 个人ID和姓名
	public List<Map<String, Object>> custSelect() {
		sql = "select cust_id,cust_name from cust_info ";
		list = jt.queryForList(sql);
		return list;
	}
     //模糊查询机构和个人
	public List<Map<String, Object>> custOrgSelect(String cust_type,String cust_name) {
		if(cust_type=="1" || cust_type.equals("1")){
		sql = "select cust_id,cust_name from cust_info where cust_name like '"+"%"+cust_name+"%'";
		}else{
			if(cust_type=="2" || cust_type.equals("2")){
				sql = "select org_id as cust_id,org_name as cust_name from org_info where org_name like '"+"%"+cust_name+"%'";
			}else{
				return null;
			}
		}
		list = jt.queryForList(sql);
		return list;
	}
	// 订单设为无效订单
	public int orderChange(long order_no, long order_version) {
		
		sql = "update order_info set stateflag='1' where order_no=? and order_version=?";
		return jt.update(sql, order_no, order_version);
	}

	// 获取更改的订单信息
	public List<Map<String, Object>> oldOrder(long order_no,long order_version) {
		sql = "SELECT area, part_comp, contract_type, contract_no, is_id, id_no, start_fee, "
				+ "late_join_fee, remark, bank_no, bank_card, is_checked, stateflag, "
				+ "order_amount, pri_fee, acount_fee, delay_fee, create_time, break_fee, "
				+ "order_version, buy_fee, order_type, order_no, cust_no, prod_no, "
				+ "email_id, prod_diffcoe, cust_type,entry_time,register "
				+ "FROM public.order_info where order_no=? and order_version=?";
		return jt.queryForList(sql, order_no,order_version);
	}

	// 订单编辑页面保存
	public int orderEditSave(String area, long part_comp,
			String contract_type, String contract_no, String is_id,
			String id_no, String start_fee, String late_join_fee,
			String remark, String bank_no, String bank_card, String is_checked,
			double order_amount, double pri_fee, double acount_fee,
			double delay_fee, Date entry_time, double break_fee,
			Long order_version, double buy_fee, String order_type,
			long order_no, long cust_no, Long prod_no, 
			long email_id,long old_order_version,long register,String prod_diffcoe,String cust_type,
			String cust_email, String mail_address, String work_address, String cust_cell, String extra_award,
			Date first_pay_time, String comment, String cust_address ) {
		sql = "INSERT INTO public.order_info(area,part_comp,contract_type,is_id,id_no,"
				+ "start_fee,late_join_fee,"
				+ "remark,bank_no,bank_card,is_checked,stateflag,order_amount,"
				+ "pri_fee,acount_fee,delay_fee,create_time,break_fee,order_version,buy_fee,"
				+ "order_type,order_no,cust_no,prod_no,email_id,register,entry_time,prod_diffcoe,cust_type,"
				+ "contract_no, cust_email, mail_address, work_address, cust_cell, extra_award, first_pay_time, comment, cust_address) "
				+ "VALUES(?,?,?,?,?,"
				+ "?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,current_date,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?);";
		int i = jt.update(sql, area, part_comp, contract_type, is_id, id_no,
				start_fee, late_join_fee, 
				remark, bank_no, bank_card,is_checked, '0', order_amount,
				pri_fee, acount_fee, delay_fee,break_fee, order_version, buy_fee,
				order_type,order_no, cust_no, prod_no,email_id,register,entry_time,prod_diffcoe,cust_type,
				contract_no, cust_email, mail_address, work_address, cust_cell, extra_award, first_pay_time, comment, cust_address);
		if (i > 0) {
			sql = "update sale_order set order_version=? where order_no=? and order_version=?";
			i = jt.update(sql, order_version, order_no,old_order_version);
		} else {
			i = 0;
		}
		
		return i;
	}

	// 订单--RM
	public List<Map<String, Object>> orderRM(long order_no) {
		sql = "SELECT sales_id, percentage, perct_dot FROM public.rel_order_sales where order_no=?";
		list = jt.queryForList(sql, order_no);
		return list;
	}

	// 获取产品名称
	public List<Map<String, Object>> selectProad(String prod_name) {
		sql = "select prod_id from product_info where prod_name=?";
		list = jt.queryForList(sql, prod_name);
		return list;
	}

	// 订单--打款记录
	public List<Map<String, Object>> orderPay(long order_no) {
		sql = "SELECT pay_amount, pay_step, pay_time, order_no, cust_no, cust_type, stateflag, remark FROM public.order_pay_record where order_no=?";
		list = jt.queryForList(sql, order_no);
		return list;
	}

	/**
	 * 新建订单 客户查询
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> querycust(String condition) {
		String sql = "SELECT t1.sales_name,t2.cust_name,t1.cust_id,t2.cust_cell FROM sales_cust_rel t1,cust_info t2 "
				+ " where t1.cust_id=t2.cust_id and t1.cust_id=(SELECT c.cust_id FROM cust_info c where c.cust_cell=? and c.state='3')";
		return jt.queryForList(sql, condition);
	}

	/**
	 * 新建订单 客户查询
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> queryorg(String condition) {
		String sql = "SELECT org_id, org_name, org_type, org_license, org_legal FROM public.org_info where org_license=? and state='3'";
		return jt.queryForList(sql, condition);
	}
	
	
	/**
	 *查询org下的合伙人
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> queryorg_parner(long org_id) {
		String sql = "SELECT t1.org_id, t1.cust_id, t1.partner, t1.match_rm, t2.sales_id,t2.group_point "
				+ "FROM public.org_cust_rel t1 inner join sales_achievement t2 on t1.sales_id=t2.sales_id where org_id=?";
		return jt.queryForList(sql, org_id);
	}
	/**
	 *查询cust
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> querycust_parner(long cust_id) {
		String sql = "SELECT  t1.cust_id,  t1.sales_name match_rm, t2.sales_id,t2.group_point "
				+ "FROM public.sales_cust_rel t1 inner join sales_achievement t2 on t1.sales_id=t2.sales_id where t1.cust_id=?";
		return jt.queryForList(sql, cust_id);
	}

	/**
	 * 新建订单
	 * 
	 * @param list
	 * @throws ParseException 
	 */
	public void orderInsert(List<Map<String, Object>> list,long user_id,User user) throws ParseException {
		long order_no = jdbcUtil.seq();
		long order_version = jdbcUtil.seq();
		long email_id = jdbcUtil.seq();
		double startfee = 0;
		double late_joinfee = 0;
		double prifee = 0;
		double acountfee = 0;
		double delayfee = 0;
		double breakfee = 0;
		double buyfee = 0;
		double orderamount = 0;
		String sql = "INSERT INTO public.order_info(area, part_comp, contract_type, contract_no, is_id, id_no, start_fee,"
				+ " late_join_fee, remark, bank_no, bank_card, is_checked, stateflag, "
				+ "order_amount, pri_fee, acount_fee, delay_fee, create_time, break_fee, "
				+ "order_version, buy_fee, order_type, order_no, cust_no, prod_no, email_id,register,entry_time,prod_diffcoe,cust_type,"
				+ "first_pay_time, cust_email, mail_address, work_address, cust_cell, comment, extra_award, cust_address,investor_no)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, "
				+ " ?, ?, ?, ?, current_date, ?, " 
				+ "?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String area = (String) list.get(0).get("area");
		String cust_type = (String) list.get(0).get("cust_type"); 
		String area_name = (String) list.get(0).get("area_name");
		String cust_name = (String) list.get(0).get("cust_name");
		String partcomp = (String) list.get(0).get("part_comp");	
		long part_comp = Long.parseLong(partcomp);
		String part_comp_name = (String) list.get(0).get("part_comp_name");//合伙企业
		String contract_type = (String) list.get(0).get("contract_type");
		String contract_no = (String) list.get(0).get("contract_no");
		Integer is_id = (Integer) list.get(0).get("is_id");
		String id_no = (String) list.get(0).get("id_no");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date entry_time = sdf.parse((String) list.get(0).get("create_time"));
		//Date create_time=sdf.parse(strDate);
		//Date create_time = (Date) list.get(0).get("create_time");
		Date first_pay_time = sdf.parse((String) list.get(0).get("first_pay_time"));
		String cust_email = (String) list.get(0).get("cust_email");
		String cust_cell = (String) list.get(0).get("cust_cell");
		String mail_address = (String) list.get(0).get("mail_address");
		String work_address = (String) list.get(0).get("work_address");
		String comment = (String) list.get(0).get("comment");
		String extra_award = (String) list.get(0).get("extra_award");
		String start_fee = (String) list.get(0).get("start_fee");
		if (!"".equals(start_fee))
			startfee = Double.parseDouble(start_fee);
		String late_join_fee = (String) list.get(0).get("late_join_fee");
		if (!"".equals(late_join_fee))
			late_joinfee = Double.parseDouble(late_join_fee);
		String remark = (String) list.get(0).get("remark");
		String bank_no = (String) list.get(0).get("bank_no");
		String bank_card = (String) list.get(0).get("bank_card");
		String is_checked = (String) list.get(0).get("is_checked");
		//String stateflag = (String) list.get(0).get("stateflag");
		String order_amount = (String) list.get(0).get("order_amount");
		if (!"".equals(order_amount))
			orderamount = Double.parseDouble(order_amount);
		String pri_fee = (String) list.get(0).get("pri_fee");
		if (!"".equals(pri_fee))
			prifee = Double.parseDouble(pri_fee);
		String acount_fee = (String) list.get(0).get("acount_fee");
		if (!"".equals(acount_fee))
			acountfee = Double.parseDouble(acount_fee);
		String delay_fee = (String) list.get(0).get("delay_fee");
		if (!"".equals(delay_fee))
			delayfee = Double.parseDouble(delay_fee);
		// String create_time = (String) list.get(0).get("create_time");
		String break_fee = (String) list.get(0).get("break_fee");
		if (!"".equals(break_fee))
			breakfee = Double.parseDouble(break_fee);
		String buy_fee = (String) list.get(0).get("buy_fee");
		if (!"".equals(buy_fee))
			buyfee = Double.parseDouble(buy_fee);
		String order_type = (String) list.get(0).get("order_type");
		String order_type_name = (String) list.get(0).get("order_type_name");
		String cust_no = (String) list.get(0).get("cust_no");
		String cust_address = (String) list.get(0).get("cust_address");
		long custno = 0;
		long prodno = 0;
		long investor_no = 0;
		if (!"".equals(cust_no))
			custno = Long.parseLong(cust_no);
		String prod_no = (String) list.get(0).get("prod_no");
		String prod_name = (String) list.get(0).get("prod_no_name");
		if (!"".equals(prod_no))
			prodno = Long.parseLong(prod_no);
		String investorno = (String) list.get(0).get("investor_no");
		if (investorno != null &&!"".equals(investorno))
			investor_no = Long.parseLong(investorno);
		String prod_diffcoe = (String) list.get(0).get("prod_diffcoe");
		//订单插入
		jt.update(sql, area, part_comp, contract_type, contract_no, is_id.toString(),
				id_no, startfee, late_joinfee, remark, bank_no, bank_card, is_checked,
				"0", orderamount, prifee, acountfee, delayfee, breakfee,
				order_version, buyfee, order_type, order_no, custno, prodno,
				email_id,user_id,entry_time,prod_diffcoe,cust_type,first_pay_time, cust_email,
				mail_address, work_address, cust_cell, comment, extra_award, cust_address, investor_no);
		
		List<Object[]> sale_order_list = new ArrayList<Object[]>();
		String prod_sql = "SELECT t1.e_rate,t1.dict_name FROM product_info t,data_dict t1 "
				+ "where t.prod_id=? and t.prod_currency = t1.dict_value and t1.dict_type='prodCur'";
		List<Map<String, Object>> prod_list = jt.queryForList(prod_sql,prodno);
		double e_rate =1;
		String bizhong = "";
		if(prod_list!=null){
			 e_rate =  Double.parseDouble( prod_list.get(0).get("e_rate").toString());
			 bizhong = prod_list.get(0).get("dict_name").toString();
		}
		String sales_name = "";
		for(int i=1;i<list.size();i++){
			long sales_id = Long.parseLong((String) list.get(i).get("sales_id"));
			double magt_fee = Double.parseDouble((String) list.get(i).get("achievement"));
			magt_fee = e_rate*magt_fee;
			List<Map<String, Object>> deduc_list =jt.queryForList
					("select * from sale_index(?,?,"+magt_fee+",?)",sales_id,(String) list.get(0).get("create_time"),prodno);
			double obc_amount =0;
			double deduction =0;
			if(deduc_list.get(0).get("sale_index")!=null){
				deduction = Double.parseDouble((String) deduc_list.get(0).get("sale_index"));
				obc_amount = magt_fee-deduction;
				if(obc_amount<0){
					obc_amount=0;
				}
				                                                           
			}
			
			Object[] sale_order = {
					(String) list.get(i).get("achievementpoint"),
					"0",
					order_no,
					 sales_id,
					Double.parseDouble((String) list.get(i).get("achievement")) ,
					 (String) list.get(i).get("sales_name"),
					order_version,
					jdbcUtil.seq(),
					(String) list.get(i).get("distribution"),
					Long.parseLong((String) list.get(i).get("cust_id")),
					deduction,
					obc_amount
			};
			sale_order_list.add(sale_order);
			//数据库查询
			sales_name=(String) list.get(i).get("sales_name")+"  ";
			
			
			
		}
		String sqlsale = "INSERT INTO public.sale_order("
				+ "sales_point, stateflag, order_no, sales_id, magt_fee, sales_name, "
				+ "order_version, email_id, cost_model,cust_id,amount_deduction,obc_amount)" + "VALUES (?, ?, ?, ?, ?, ?, "
				+ "?, ?,?,?,?,?)";// prifee
		jt.batchUpdate(sqlsale, sale_order_list);
		//修改机构or客户已签单
		String billsql = "UPDATE public.bill_info "
				+ "SET effect_sign='1' WHERE id=?";
		jt.update(billsql,Long.parseLong((String) list.get(0).get("cust_no")));
		//修改cust成交状态
		if("1".equals(cust_type)){
			String custsql="update cust_info set cust_level='2' where cust_id=?";
			jt.update(custsql,custno);
		}
		//提交审批发送邮件
		if(is_checked.equals("1")){
			userDao udao = new userDao();
			List<Map<String, Object>> userlist =udao.getTeamUser(user_id);
			String email="";
			String real_name = "";
			if(userlist!=null&&userlist.size()>0){
				email=(String) userlist.get(0).get("email");
				real_name =(String) userlist.get(0).get("real_name");
			}
			SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SentMailInfoBean sentmsg = new SentMailInfoBean();
			//sentmsg.setFirmId("001");
			sentmsg.setSubjectId(""+email_id);
			sentmsg.setSentMailaddr(mailCache.from);
			sentmsg.setReviceMailaddr(email);
			sentmsg.setMail_busstype("新建订单审批");
			sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.mailorder");
			sentmsg.setMailContent("创建订单：					\n"
					+ "尊敬的"+real_name+"： 					\n"
					+ "您好， "+sdfg.format(new Date())+","+user.getRealName()+"（员工姓名）在OMS系统创建了新订单，现等待您审批。\n"
					+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
					+ "---InfoBegin---					\n"
					+ "【订单编号】：	"+order_no+"				\n"
					+ "【销售】：	"+sales_name.toString()+"				\n"
					+ "【首次缴款日期】："+sdfg.format(entry_time)+"					\n"
					+ "【地区】：	"+area_name+"				\n"
					+ "【客户姓名】：	"+cust_name+"				\n"
					+ "【产品名称】：	"+prod_name+"				\n"
					+ "【有限合伙企业】	"+part_comp_name+"			\n"
					+ "【订单类型】：	"+order_type_name+"				\n"
					+ "【币种】：	"+bizhong+"				\n"
					+ "【下单金额】：	 "+IdGen.fmtMicrometer(order_amount)+" 				\n"
					+ "【指导/折后标费】：	 "+IdGen.fmtMicrometer(pri_fee)+"			\n"
					+ "---InfoEnd--- \n"					
	);
			sentmsg.setSubject("新建订单审批");
			sendMail.sendMessage(sentmsg, true);
		}
	}
	/**
	 * 查询已经审批通过的产品
	 * @return
	 */
	public List<Map<String, Object>> product() {
		String sql = "SELECT prod_id, prod_name FROM public.product_info where check_status='2'";
		return jt.queryForList(sql);
	}

	//更新
		public int update(double return_coe,long order_id,long prod_rs_id ){
			sql="update product_issue set return_coe=? , prod_flag='1' where sale_id=? and prod_rs_id=?";
			int j=jt.update(sql,return_coe,order_id,prod_rs_id);
			return 0;
		}
		//查询回款
		public List<Map<String ,Object>> select(long order_id){
			sql="select * from product_issue prs "
					+ "left join data_dict t4 on t4.dict_value = prs.prod_flag and t4.dict_type = 'prodFlag' "
					+ "where order_id=? order by prod_rs_id ";
			list=jt.queryForList(sql,order_id);
			return list;
		}


	// 查询kyc
	/**
	 * 客户kyc查询
	 * @param cust_no
	 * @return
	 */
	public List<Map<String, Object>> kycselect(long cust_no) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);// date 换成已经已知的Date对象
		cal.add(Calendar.MONTH, -6);// before 6 month
		Date dat = cal.getTime();

		sql = "select k.kyc_id,to_char(k.reg_time,'yyyy-MM-dd') reg_time from kyc k "
				+ "LEFT JOIN kyc_cust_rel kc on k.kyc_id=kc.kyc_id where kc.cust_id=? and reg_time BETWEEN ? AND ?";
		list = jt.queryForList(sql, cust_no, dat, date);
		return list;
	}
	/**
	 * 查询机构下kyc
	 * @param cust_no
	 * @return
	 */
	public List<Map<String, Object>> org_cust(long cust_no) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);// date 换成已经已知的Date对象
		cal.add(Calendar.MONTH, -6);// before 6 month
		Date dat = cal.getTime();

		sql = "select k.kyc_id,to_char(k.reg_time,'yyyy-MM-dd') reg_time from kyc k "
				+ "LEFT JOIN kyc_cust_rel kc on k.kyc_id=kc.kyc_id "
				+ "where kc.cust_id in(select cust_id from org_cust_rel where org_id=?) and reg_time BETWEEN ? AND ?";
		list = jt.queryForList(sql, cust_no, dat, date);
		return list;
	}
	
	//查询合伙企业
		public List<Map<String, Object>> order_prod(long prod_no) {
			sql="SELECT lp.lp_id,lp.partner_com_name FROM lp_prod_rela lr "
					+ "LEFT JOIN lp_info lp ON lr.lp_id=lp.lp_id WHERE lr.prod_id=?";
			return jt.queryForList(sql,prod_no);
		}
		
		//查询产品系数
		public List<Map<String, Object>> Calculation(long prod_no,long lp_id) {
			sql="SELECT lp.prod_diffcoe FROM lp_prod_rela lr "
					+ "LEFT JOIN lp_info lp ON lr.lp_id=lp.lp_id WHERE lr.prod_id=? and lp.lp_id=?";
			return jt.queryForList(sql,prod_no,lp_id);
		}
		
		//根据员工编号查询销售名
		public List<Map<String, Object>> DesigSales(String employee_code) {
					sql="SELECT  user_id,real_name FROM public.upm_user where employee_code=?";
					return jt.queryForList(sql,employee_code);
				}
		//根据员工编号查询销售名查询提成点
		public List<Map<String, Object>> sales_achievement(String sale_name) {
			sql="SELECT t.user_id, t1.sales_id, t1.start_time,t.employee_code, t1.end_time, t1.group_point, t1.paeam_year,t.real_name "
					+ "FROM public.sales_achievement t1,upm_user t "
					+ "where t1.sales_id=t.user_id and t.status='ok' and t.real_name like '%"+sale_name+"%'";
			//and paeam_year=?
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			
			String year = sdf.format(date);
			return jt.queryForList(sql);
		}
		/**
		 * 订单审批通过	
		 * @param order_no
		 * @param order_version
		 */
		public void pass_approve(long order_no,long order_version) {
			sql="UPDATE public.order_info SET is_checked='2' WHERE order_version=? and order_no=?";
			 jt.update(sql,order_version,order_no);
		}
		/**
		 * 订单审批驳回	
		 * @param order_no
		 * @param order_version
		 */
		public void regect_approve(long order_no,long order_version) {
			sql="UPDATE public.order_info SET is_checked='3' WHERE order_version=? and order_no=?";
			 jt.update(sql,order_version,order_no);
		}
		/**
		 * 查询
		 * @param order_no
		 * @param order_version
		 * @return
		 */
		public List<Map<String, Object>> back(long order_no,long order_version) {
			sql="SELECT pay_step "
					+ "FROM public.payment_info "
					+ "WHERE order_no=? and order_id=?  and pay_type='0'";
			 return jt.queryForList(sql,order_version,order_no);
		}
		
		
		/**
		 * 缴款审批	
		 * @param order_no
		 * @param order_version
		 */
		public void call_approve(long payment_id ,String pay_state) {
			sql="UPDATE public.payment_call SET  pay_state=? WHERE  payment_id=? ";
			 jt.update(sql,pay_state,payment_id);
		}
		
		
		/**
		 * 打款审批通过	
		 * @param order_no
		 * @param order_version
		 */
		public void play_approve(long payment_id,String pay_state) {
			sql="UPDATE public.payment_info SET   pay_state=? "
					+ "WHERE payment_id=?";
			 jt.update(sql,pay_state,payment_id);
		}
		
		
		
		/**
		 * 退款给审批
		 * @param order_no
		 * @param order_version
		 *//*
		public void back_approve(long order_no,long order_version,String pay_step,String pay_type,String pay_state) {
			sql="UPDATE public.payment_info SET   pay_state=? "
					+ "WHERE order_no=? and order_id=? and pay_step=? and pay_type=?";
			 jt.update(sql,pay_state,order_version,order_no,pay_step,pay_type);
		}*/
		/**
		 * 删除退款、打款
		 * @param payment_id
		 */
		public void del_payment(long payment_id) {
			sql="DELETE FROM public.payment_info WHERE payment_id=?";
			 jt.update(sql,payment_id);
		}
		/**
		 * 删除缴款
		 * @param payment_id
		 */
		public void del_call_payment(long payment_id) {
			sql="DELETE FROM public.payment_call WHERE payment_id=?";
			 jt.update(sql,payment_id);
		}
		/**
		 * 邮件审批
		 * @param email_id
		 * @param status
		 */
		public void mail_approve(long email_id,String status) {
			sql="UPDATE public.order_info SET is_checked=? WHERE email_id=?";
			 jt.update(sql,status,email_id);
		}
		/**
		 * 更改sales——index参数累计到下个季度
		 * @param i_time
		 * @param t_time
		 */
		public void update_sales_para() {
			// TODO Auto-generated method stub
			String sql = "select * from add()";
			jt.queryForList(sql);
			
		}
		/**
		 * 回款审批 驳回 或通过
		 * @param prod_rs_id
		 * @param prod_flag
		 */
		public void return_approve(long prod_rs_id, String prod_flag) {
			// TODO Auto-generated method stub
			String sql ="update product_issue set  prod_flag=? where prod_rs_id=?";
			jt.update(sql, prod_flag,prod_rs_id);
			
		}
		
		//销售订单列表查询  excel导出
		public SqlRowSet r_sale_orderInfoSelect(int n, int m,long sale_id) {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT row_number() OVER () as rownum,t1.order_no,t1.entry_time,t10.dict_name area,t2.sales_name,t3.prod_name,t4.partner_com_name,t11.dict_name, "
					+ "CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ "t1.order_amount,   "
					+ "t8.dict_name as bizhong, t1.prod_diffcoe, "
					+ "t2.magt_fee,  t9.dict_name "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "  where t1.stateflag='0' and t2.sales_id=? "
					+ "order by rownum";
			/* limit ? OFFSET ? */
			/* ,n,m */

			SqlRowSet rowset = jt.queryForRowSet(sql,sale_id);
			return rowset;
		}
		/**
		 * 团队职能列表订单列表查看excel导出
		 * @param n
		 * @param m
		 * @param sale_id
		 * @return
		 */
		public SqlRowSet r_team_sale_orderInfoSelect(int n, int m,long sale_id) {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT row_number() OVER () as rownum,t1.order_no,t1.entry_time,t10.dict_name area,t2.sales_name,t3.prod_name,t4.partner_com_name,t11.dict_name, "
					+ "CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ "t1.order_amount,   "
					+ "t8.dict_name as bizhong, t1.prod_diffcoe, "
					+ "t2.magt_fee,  t9.dict_name "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "  where t1.stateflag='0' and t2.sales_id in(select a.user_id from upm_user a,"
						+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
						+ "where a.workgroup_id=b.workgroup_id) "
						+ "order by rownum";

			/* limit ? OFFSET ? */
			/* ,n,m */

			
			return jt.queryForRowSet(sql,sale_id);
		}
		
		/**
		 * 分公司职能列表订单列表查看excel导出
		 * @param n
		 * @param m
		 * @param sale_id
		 * @return
		 */
		public SqlRowSet r_org_team_sale_orderInfoSelect(int n, int m,long sale_id) {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT row_number() OVER () as rownum,t1.order_no,t1.entry_time,t10.dict_name area,t2.sales_name,t3.prod_name,t4.partner_com_name,t11.dict_name, "
					+ "CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ "t1.order_amount,   "
					+ "t8.dict_name as bizhong, t1.prod_diffcoe, "
					+ "t2.magt_fee,  t9.dict_name "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "  where t1.stateflag='0' and t2.sales_id in(select a.user_id from upm_user a,"
					+ "(select user_id,org_code from upm_user where user_id=?) b  "
					+ "where a.org_code=b.org_code)"
					+ "order by rownum";

			/* limit ? OFFSET ? */
			/* ,n,m */

			 
			return jt.queryForRowSet(sql,sale_id);
		}
		/**
		 * 查询运营操作职能报表导出
		 * @param user_id
		 * @param n
		 * @param m
		 * @return
		 */
		public SqlRowSet r_orderInfoSelect(long user_id,int n, int m) {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT row_number() OVER () as rownum,t1.order_no,t1.entry_time,t10.dict_name area,t2.sales_name,t3.prod_name,t4.partner_com_name,t11.dict_name, "
					+ "CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ "t1.order_amount,   "
					+ "t8.dict_name as bizhong, t1.prod_diffcoe, "
					+ "t2.magt_fee,  t9.dict_name "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "  where t1.stateflag='0' and t1.register=? "
					+ " order by rownum";
			/* limit ? OFFSET ? */
			/* ,n,m */

			return jt.queryForRowSet(sql,user_id);
		}
		
		// 订单列表查询全量excel导出
		public SqlRowSet r_orderInfoSelect(int n, int m) {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT row_number() OVER () as rownum,t1.order_no,t1.entry_time,t10.dict_name area,t2.sales_name,t3.prod_name,t4.partner_com_name,t11.dict_name, "
					+ "CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ "t1.order_amount,   "
					+ "t8.dict_name as bizhong, t1.prod_diffcoe, "
					+ "t2.magt_fee,  t9.dict_name "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "  where t1.stateflag='0' "
					+ "order by rownum";
			/* limit ? OFFSET ? */
			/* ,n,m */

			return jt.queryForRowSet(sql);
		}
		
		/**
		 * 查询产品操作职能
		 * @param user_id
		 * @param n
		 * @param m
		 * @return
		 */
		public SqlRowSet r_prod_orderInfoSelect(long user_id,int n, int m) {

			/* System.out.println("-----------已进入orderInfoSelect"); */
			sql = "SELECT row_number() OVER () as rownum,t1.order_no,t1.entry_time,t10.dict_name area,t2.sales_name,t3.prod_name,t4.partner_com_name,t11.dict_name, "
					+ "CASE WHEN t5.cust_type='1' THEN  t6.cust_name  else  t7.org_name  end  cust_name, "
					+ "t1.order_amount,   "
					+ "t8.dict_name as bizhong, t1.prod_diffcoe, "
					+ "t2.magt_fee,  t9.dict_name "
					+ "FROM public.order_info t1 "
					+ "inner join sale_order t2 on t2.order_no=t1.order_no and t1.order_version =t2.order_version  "
					+ "left join product_info t3 on t3.prod_id=t1.prod_no  "
					+ "left join LP_info t4 on t4.lp_id=t1.part_comp "
					+ "LEFT JOIN bill_info t5 on t5.id=t1.cust_no "
					+ " LEFT JOIN cust_info t6 on t6.cust_id=t1.cust_no  "
					+"INNER JOIN data_dict t8 on t8.dict_value=t3.prod_currency and t8.dict_type='prodCur' and t8.create_by='prod' and t8.dict_desc='产品币种'  "
					+" left JOIN data_dict t9 on t9.dict_value=t1.is_checked and t9.dict_type='ord_flag' and t9.create_by='dingdan' and t9.dict_desc='订单状态'  "
					+ "LEFT JOIN org_info t7 on t7.org_id=t1.cust_no  "
					+ "left JOIN data_dict t10 on t10.dict_value=t1.area and t10.dict_type='dist' "
					+ "left JOIN data_dict t11 on t11.dict_value=t3.prod_type and t11.dict_type='prodType'"
					+ "  where t1.stateflag='0' and t3.user_id=? "
					+ "order by rownum";
			/* limit ? OFFSET ? */
			/* ,n,m */

			return jt.queryForRowSet(sql,user_id);
		}
		/**
		 * 重置数据到数据库
		 * @param json
		 * @param gp_id
		 */
		public void order_reset(JSONObject json ,long email_id){
			String sql ="UPDATE public.order_info "
					+ "SET cust_type=?,cust_no=?,prod_no=?,part_comp=?,order_type=?,"
					+ "order_amount=?,acount_fee=?,contract_type=?,contract_no=?,is_id=?,id_no=?,"
					+ "prod_diffcoe=?,  pri_fee=?,buy_fee=?,  start_fee=?, bank_no=?,"
					+ "bank_card=?,remark=? WHERE email_id=?";
			long cust_no = Long.parseLong(json.get("cust_no").toString());
			long prod_id = Long.parseLong(json.get("prod_id").toString());
			long part_comp = Long.parseLong(json.get("part_comp").toString());
			double order_amount = Double.parseDouble(json.get("order_amount").toString());
			double pri_fee = Double.parseDouble(json.get("pri_fee").toString());
			double buy_fee =Double.parseDouble(json.get("buy_fee").toString());
			double start_fee = Double.parseDouble(json.get("start_fee").toString());
			double acount_fee = Double.parseDouble(json.get("acount_fee").toString());
			jt.update(sql,json.get("cust_type"),cust_no,prod_id,part_comp,json.get("order_type"),
					order_amount,acount_fee,json.get("contract_type"),json.get("contract_no"),json.get("is_id"),json.get("id_no"),
					json.get("prod_diffcoe"),pri_fee,buy_fee,start_fee,json.get("bank_no"),
					json.get("bank_card"),json.get("remark"),email_id);
		}

		public List<Map<String, Object>> find(String cust_type, String cust_name) {
			String sql = "select ";
			return jt.queryForList(sql);
		}

		public void giveup_call(long order_no, long order_version) {
			// TODO Auto-generated method stub
			String sql = "update order_info set  is_checked='4' where order_no=? and order_version=?";
			jt.update(sql,order_no,order_version);
		}
		
		/**
		 * 根据订单获取产品信息
		 */
		public List<Map<String, Object>> getOrderPruductInfo(long order_no) {
			String sql = "select prod_type from order_info oi, product_info pi where oi.prod_no = pi.prod_id and oi.order_no = ? ";
			return jt.queryForList(sql, order_no);
		}
}
