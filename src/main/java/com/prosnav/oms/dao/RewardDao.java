package com.prosnav.oms.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.prosnav.oms.util.jdbcUtil;

public class RewardDao {
	private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil
			.getBean("template");

	//销售 查询可发放订单
	public List<Map<String, Object>> rewardList(long ueser_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";

		sql = "SELECT reward_id, order_no,count_batch ,"
				+ "to_char(COALESCE(has_reward,0.00), 'fm99,999,999,999,999,999,990.00') has_reward, "
				+ " to_char(COALESCE(order_amount,0.00), 'fm99,999,999,999,999,999,990.00') order_amount,"
				+ " prod_name, cust_name " + " from reward_count_grant  "
				+ " where stateflag='0' and sales_id=?";
		list = jt.queryForList(sql, ueser_id);
		return list;
	}

	// 销售奖金提交按钮

	public int[] rewardSubmit(String id) {
		String sql = "";
		/* System.out.println(id); */
		if (id.substring(id.length() - 1, id.length()) == ",") {
			id = id.substring(0, id.length() - 1);
		}
		String[] rewardId = id.split(",");
		List<Object[]> list = new ArrayList<Object[]>();
		// list.add(rewardId);
		/*
		 * int j = 0; long reward_id = 0;
		 */
		for (int i = 0; i < rewardId.length; i++) {
			/*
			 * if(rewardId[i]==null||rewardId[i].equals("")){
			 * System.out.println("ERROR:reward_id="+rewardId[i]);
			 * rewardId[i]="0"; }
			 */
			Object[] sqlparam = { Long.parseLong(rewardId[i]) };
			/* System.out.println(sqlparam); */
			list.add(sqlparam);
		}

		sql = "update reward_count_grant set stateflag='1' where reward_id=?";

		return jt.batchUpdate(sql, list);
	}

	/*
	 * private String[] split(String id, char c) { // TODO Auto-generated method
	 * stub return null; }
	 */

	// 预留计算保存
	public int reserve_change(String sales_name, String area, long reserve_id,
			long sales_id, double reserve, String sign, String remark,
			String sum_reserve) {
		String sql = "";
		/*
		 * if (sign.equals("1")) { sql =
		 * "INSERT INTO reward_resrv_pool(sales_name,area,reserve_id,sales_id "
		 * + " ,reserve ,sign,stateflag,create_time,remark) VALUES( " +
		 * " ?,?,?,?,?,?,'-1',?,? " + " )"; } else {
		 */
		sql = "INSERT INTO reward_resrv_pool(sales_name,area,reserve_id,sales_id "
				+ " ,reserve ,sign,stateflag,create_time,remark) VALUES( "
				+ " ?,?,?,?,?,?,'-1',cast (extract(year from now())-1 as VARCHAR),? "
				+ " )";
		/* } */
		return jt.update(sql, sales_name, area, reserve_id, sales_id, reserve,
				sign, remark);
	}

	// 预留计算页面确认
	public int reserve_confirm(long count_batch) {
		String sql = "UPDATE reward_resrv_pool set stateflag='0',count_batch=? where stateflag='-1'";
		return jt.update(sql, count_batch);
	}

	// 预留发放或扣减查询
	public List<Map<String, Object>> reserve_save() {
		String sql = "select sales_id,sales_name,sign,  "
				+ "  CASE when sign='1' then reserve ELSE 0 end reserve_kou, "
				+ "  case when sign='2' then reserve ELSE 0 end reserve_give "
				+ " from reward_resrv_pool where stateflag='-1' ";
		return jt.queryForList(sql);
	}

	// 计算页面-- 查询所有预留信息
	public List<Map<String, Object>> yuliuList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		sql = "SELECT b.sales_id,b.area,b.sales_name,b.sum_reserve,b.reserve_kou,b.reserve_give,b.reserve_change, "
				+ " ( COALESCE(b.sum_reserve,0)- COALESCE(b.reserve_kou,0)- COALESCE(b.reserve_give,0)) reserve_surplus "
				+ " from ( "
				+ " SELECT sales_id,sales_name,area,  "
				+ " 		  sum(case  when  sign='0' then  reserve end ) sum_reserve,  "
				+ " COALESCE(sum( case  when (stateflag<>'-1' OR stateflag is null) and sign='1' then reserve end ),0) reserve_kou,"
				+ " COALESCE(sum(CASE when stateflag='-1' then reserve end),0) reserve_change , "
				+ " COALESCE(sum(case  when (stateflag<>'-1' OR stateflag is null) and sign='2' then reserve end),0)  reserve_give "
				+ " 		 from reward_resrv_pool "
				+ " where   SUBSTR(create_time,1,4)=cast (extract(year from now())-1 as VARCHAR) "
				+ "   GROUP BY sales_id,sales_name,area ) b "
				+ " where ( COALESCE(b.sum_reserve,0)- COALESCE(b.reserve_kou,0)- COALESCE(b.reserve_give,0))>0";
		list = jt.queryForList(sql);
		return list;
	}

	// 计算页面多条件-- 多条件查询预留信息
	public List<Map<String, Object>> reward_yuliu_select(String area,
			String product, String rm) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer reservesql = new StringBuffer();

		String sql = "select b.sales_id ,b.sales_name,b.area, "
				+ " b.sum_reserve, "
				+ " 		    b.reserve_kou,  "
				+ " 		   b.reserve_give,b.reserve_change,   "
				+ " 		  ( COALESCE(b.sum_reserve,0)- COALESCE(b.reserve_kou,0)- COALESCE(b.reserve_give,0)) reserve_surplus "
				+ " 		 from  "
				+ " 		  ( "
				+ " 		  SELECT sales_id,sales_name,area, "
				+ " 		  COALESCE(sum(case  when  sign='0' then  reserve end),0)  sum_reserve, "
				+ " 		  COALESCE(sum(case  when (stateflag<>'-1' OR stateflag is null) and sign='1' then reserve end ),0) reserve_kou,"
				+ "                          COALESCE(sum(CASE when stateflag='-1' then reserve end),0) reserve_change , "
				+ " 		  COALESCE(sum(case  when (stateflag<>'-1' OR stateflag is null) and sign='2' then reserve end ),0) reserve_give "
				+ " 		  from reward_resrv_pool where   "
				+ "    SUBSTR(create_time,1,4)=cast (extract(year from now())-1 as VARCHAR) "
				+ " 	   GROUP BY sales_id,sales_name,area ) b  "
				+ " 	 where ( COALESCE(b.sum_reserve,0)- COALESCE(b.reserve_kou,0)- COALESCE(b.reserve_give,0))>0    ";
		/*
		 * // 根据产品进行查询 if (product != null && product != "") { sql =
		 * "select b.sales_id ,b.sales_name,b.area,b.prod_name, " +
		 * "( COALESCE(b.sum_reserve,0)- COALESCE(b.reserve_kou,0)- COALESCE(b.reserve_give,0)) reserve_surplus, "
		 * + "		 b.sum_reserve,  " + "			  b.reserve_kou,  " +
		 * "			  b.reserve_give from   " + "			(  " +
		 * "	 SELECT sales_id,sales_name,area,prod_name,  " +
		 * "		 COALESCE(sum(case  when sign='0' then  reserve end),0)  sum_reserve, "
		 * +
		 * "		 COALESCE(sum(case  when sign='1' then reserve end ),0) reserve_kou, "
		 * +
		 * "		COALESCE(sum( case  when sign='2' then reserve end),0)  reserve_give "
		 * + "	 from reward_resrv_pool  " +
		 * " where  SUBSTR(create_time,1,4)=cast (extract(year from now())-1 as VARCHAR)"
		 * + " GROUP BY sales_id,sales_name,area,prod_name ) b " +
		 * "	 where ( COALESCE(b.sum_reserve,0)- COALESCE(b.reserve_kou,0)- COALESCE(b.reserve_give,0))>0 "
		 * ; sql = sql + " and b.prod_name like '%" + product + "%' "; }
		 */
		reservesql.append(sql);

		if (area != null && area != "") {
			reservesql.append(" and b.area like '%" + area + "%'");
		}

		if (rm != null && rm != "") {
			reservesql.append(" and b.sales_name like '%" + rm + "%'");
		}
		/*
		 * if (product != null && product != "") {
		 * reservesql.append(" GROUP BY sales_id,sales_name,area,prod_name "); }
		 * else { reservesql.append(" GROUP BY sales_id,sales_name,area"); }
		 */
		list = jt.queryForList(reservesql.toString());
		return list;
	}

	// 预留 核对页面查询
	public List<Map<String, Object>> reserve_check_search() {
		/*String sql = "SELECT sales_id,sales_name, "
				+ " sum(case WHEN stateflag='1' then reserve ELSE 0 end ) give_reserve, "
				+ " sum(case WHEN sign='0' then reserve ELSE 0 end ) sum_reserve  "
				+ " from reward_resrv_pool where stateflag='1' "
				+ " GROUP BY sales_id,sales_name  ";*/
		String sql="SELECT sales_id,sales_name,SUBSTR(create_time,1,4) create_time, "
				+" sum(case WHEN stateflag='1' and sign='2' then reserve ELSE 0 end ) give_reserve,  "
						 +" sum(case WHEN sign='0' then reserve ELSE 0 end ) sum_reserve  "
						 +" from reward_resrv_pool where  (stateflag<>'0' and stateflag<>'2' and stateflag<>'-1' ) or stateflag is null "
				+" GROUP BY sales_id,sales_name,SUBSTR(create_time,1,4) ";
		return jt.queryForList(sql);
	}

	// 预留核对页面多条件查询
	public List<Map<String, Object>> reserve_check_sear(String sales_name) {
		/*String sql = "SELECT sales_id,sales_name, "
				+ " sum(case WHEN stateflag='1' then reserve ELSE 0 end ) give_reserve, "
				+ " sum(case WHEN sign='0' then reserve ELSE 0 end ) sum_reserve  "
				+ " from reward_resrv_pool where stateflag='1'  ";*/
		String sql="SELECT sales_id,sales_name,SUBSTR(create_time,1,4) create_time, "
		+" sum(case WHEN stateflag='1' and sign='2' then reserve ELSE 0 end ) give_reserve,  "
				 +" sum(case WHEN sign='0' then reserve ELSE 0 end ) sum_reserve  "
				 +" from reward_resrv_pool where ((stateflag<>'0' and stateflag<>'2' and stateflag<>'-1' ) or stateflag is null ) ";
		if (sales_name != null && !sales_name.equals("")) {
			sql += " and sales_name like '%" + sales_name + "%'";
		}
		/*sql += "  GROUP BY sales_id,sales_name ";*/
        sql+="GROUP BY sales_id,sales_name,SUBSTR(create_time,1,4)  ";
		return jt.queryForList(sql);
	}

	// 预留核对页面--详情
	public List<Map<String, Object>> reserve_check_all(long sales_id,String create_time) {
		/*String sql = "select reserve,count_batch,sales_id,create_time from reward_resrv_pool where sales_id=? and stateflag='1' and sign='2'";*/
		String sql="SELECT  "
				+"	t1.sales_id,t1.sales_name,t1.create_time,count_batch, "
						+"	 (SELECT sum(t2.reserve) from reward_resrv_pool t2 where sign='0' "
						 +"	and SUBSTR(t2.create_time,1,4)=t1.create_time and t2.sales_id=t1.sales_id ) reserve, "
						+"	 sum(case when t1.sign='1' then t1.reserve ELSE 0 end) reserve_kou, "
						 +"	 sum(case when t1.sign='2' and t1.stateflag='1' then t1.reserve ELSE 0 end )reserve_give "
			+" from reward_resrv_pool t1"
				+" where sign<>'0' and sales_id=? and create_time=? GROUP BY sales_id,sales_name,create_time,count_batch";
		return jt.queryForList(sql, sales_id,create_time);
	}

	// 预留款核对页面保存
	public int reserve_check_save(long give_batch) {
		String sql = "UPDATE reward_resrv_pool set stateflag='2',give_batch=? WHERE stateflag='1'";
		return jt.update(sql,give_batch);
	}

	// 预留 销售查询
	public List<Map<String, Object>> reserve_sales_search(long ueser_id) {
		/*String sql = "SELECT reserve_id,sales_name,count_batch,reserve from reward_resrv_pool"
				+ " where stateflag='0' and sign='2' and sales_id=? ";*/
		
		String sql= "SELECT  "
				 +"	t1.sales_id,t1.sales_name,t1.create_time,t1.count_batch, "
				  +" (SELECT sum(t2.reserve) from reward_resrv_pool t2 where sign='0' "
				 +"	and SUBSTR(t2.create_time,1,4)=t1.create_time and t2.sales_id=t1.sales_id ) reserve, "
				 +"	 sum(case when t1.sign='1' then t1.reserve ELSE 0 end) reserve_kou, "
				 +"	 sum(case when t1.sign='2' then t1.reserve ELSE 0 end )reserve_give "
				 +" from reward_resrv_pool t1"
				 +" where t1.stateflag='0' and t1.sales_id=?  "
				 +" GROUP BY t1.sales_id,t1.sales_name,t1.create_time,t1.count_batch ";
		return jt.queryForList(sql, ueser_id);
	}

	// 预留 销售页面多条件查询
	public List<Map<String, Object>> reserve_sales_select(long count_batch,
			long ueser_id) {
		/*String sql = "SELECT sales_id,sales_name,count_batch, "
				+ " sum(case WHEN stateflag='0' then reserve ELSE 0 end ) give_reserve, "
				+ " sum(case WHEN sign='0' then reserve ELSE 0 end ) sum_reserve  "
				+ " from reward_resrv_pool where stateflag='0' and sign='2' and sales_id="
				+ ueser_id + " ";*/
		String sql ="SELECT  "
				 +"	t1.sales_id,t1.sales_name,t1.create_time,t1.count_batch, "
				  +" (SELECT sum(t2.reserve) from reward_resrv_pool t2 where sign='0' "
				 +"	and SUBSTR(t2.create_time,1,4)=t1.create_time and t2.sales_id=t1.sales_id ) reserve, "
				 +"	 sum(case when t1.sign='1' then t1.reserve ELSE 0 end) reserve_kou, "
				 +"	 sum(case when t1.sign='2' then t1.reserve ELSE 0 end )reserve_give "
				 +" from reward_resrv_pool t1 "
				 +" where t1.stateflag='0' and t1.sales_id="
				+ ueser_id + " ";
		if (count_batch != 0) {
			sql += " and t1.count_batch = " + count_batch;
		}
		sql += "  GROUP BY t1.sales_id,t1.sales_name,t1.create_time,t1.count_batch ";

		return jt.queryForList(sql);
	}

	// 预留 销售确认
	public int[] reserve_sales_confirm(String reserveid) {
		if (reserveid.substring(reserveid.length() - 1, reserveid.length()) == ",") {
			reserveid = reserveid.substring(0, reserveid.length() - 1);
		}
		String[] reserve_id = reserveid.split(",");

		List<Object[]> list = new ArrayList<Object[]>();

		for (int i = 0; i < reserve_id.length; i++) {
			Object[] sqlparam = { Long.parseLong(reserve_id[i]) };
			list.add(sqlparam);
		}

		/*String sql = "update reward_resrv_pool SET stateflag='1' "
				+ "where stateflag='0' and sign='2' and reserve_id=? ";*/
		String sql="update reward_resrv_pool t1 SET stateflag='1'  from  "
				+" (SELECT create_time,sales_id from reward_resrv_pool where reserve_id=? ) t2 "
		+"		where t1.stateflag='0' and t1.sales_id=t2.sales_id and t1.create_time=t2.create_time";
		return jt.batchUpdate(sql, list);
	}

	// 销售页面--多条件查询可发放奖金
	public List<Map<String, Object>> CustSelByParam(
			Map<String, Object> sqlParam, long ueser_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		// 1-全部
		String selsql = "SELECT t1.reward_id, t1.order_no,t1.count_batch ,to_char(t1.has_reward, 'fm99,999,999,999,999,999,990.00') has_reward, "
				+ " to_char(t1.order_amount, 'fm99,999,999,999,999,999,990.00') order_amount, t1.prod_name,  t1.cust_name "
				+ " from reward_count_grant t1  "
				+ " where t1.stateflag='0' and sales_id= " + ueser_id + " ";

		if (sqlParam.get("cust_type").equals("2"))// 个人
		{
			selsql = "SELECT t1.reward_id, t1.order_no,t1.count_batch ,to_char(t1.has_reward, 'fm99,999,999,999,999,999,990.00') has_reward, "
					+ " to_char(t1.order_amount, 'fm99,999,999,999,999,999,990.00') order_amount, t1.prod_name,  t1.cust_name "
					+ " from reward_count_grant t1  ,cust_info t2"
					+ " where t1.stateflag='0'  and t1.cust_id=t2.cust_id  and t1.sales_id= "
					+ ueser_id + " ";
			if (sqlParam.get("cust_cell") != null
					&& sqlParam.get("cust_cell") != "")
				selsql += " and t2.cust_cell='" + sqlParam.get("cust_cell")
						+ "'";
		}
		if (sqlParam.get("cust_type").equals("3"))// 机构
		{
			selsql = "SELECT t1.reward_id, t1.order_no,t1.count_batch ,to_char(t1.has_reward, 'fm99,999,999,999,999,999,990.00') has_reward, "
					+ " to_char(t1.order_amount, 'fm99,999,999,999,999,999,990.00') order_amount, t1.prod_name,  t1.cust_name "
					+ " from reward_count_grant t1,org_info t2  "
					+ " where t1.stateflag='0' and t1.cust_id=t2.org_id  and t1.sales_id= "
					+ ueser_id + " ";
			if (sqlParam.get("cust_cell") != null
					&& sqlParam.get("cust_cell") != "")
				selsql += "and t2.org_license='" + sqlParam.get("cust_cell")
						+ "'";
		}
		/*
		 * cust_name order_no cust_cell prod_type count_batch
		 */
		sql.append(selsql);

		if (sqlParam.get("cust_name") != null
				&& sqlParam.get("cust_name") != "") {
			sql.append(" and t1.cust_name like '%" + sqlParam.get("cust_name")
					+ "%'");
		}
		if (sqlParam.get("order_no") != null && sqlParam.get("order_no") != "")
			sql.append("and t1.order_no=" + sqlParam.get("order_no"));
		if (sqlParam.get("prod_type") != null
				&& sqlParam.get("prod_type") != "")
			sql.append("and t1.prod_type like '%" + sqlParam.get("prod_type")
					+ "%'");
		if (sqlParam.get("count_batch") != null
				&& sqlParam.get("count_batch") != "")
			sql.append("and t1.count_batch=" + sqlParam.get("count_batch"));
		list = jt.queryForList(sql.toString());
		return list;
	}

	// 查询可发放订单
	/*
	 * public List<Map<String, Object>> rewardSelect(String cust_name, long
	 * order_no, String cust_cell, String prod_type, long count_batch, int m,
	 * int n) { List<Map<String, Object>> list = new ArrayList<Map<String,
	 * Object>>(); String sql = ""; sql =
	 * "SELECT reward_id, order_no,count_batch ,to_char(has_reward, '9,999.99') has_reward, "
	 * +
	 * " to_char(order_amount, '9,999.99') order_amount, prod_name,  cust_name,cust_cell "
	 * + " from reward_count_grant  " + " where stateflag='0'  "; if (cust_name
	 * != "" && cust_name != null) { sql = sql + " and cust_name like'" + "%" +
	 * cust_name + "%" + "'"; } if (order_no != 0) { sql = sql +
	 * " and order_no =" + order_no; } if (cust_cell != "" && cust_cell != null)
	 * { sql = sql + "  and cust_cell like '" + cust_cell + "%" + "'"; } if
	 * (prod_type != "" && prod_type != null) { sql = sql + " and prod_type ='"
	 * + prod_type + "'"; } if (count_batch != 0) { sql = sql +
	 * "  and count_batch =" + count_batch; } list = jt.queryForList(sql);
	 * return list; }
	 */

	// 奖金计算列表
	/*
	 * public List<Map<String, Object>> reward_count() { sql =
	 * "select s1.*,s1.Reward_count_sum*s1.param_yuliu Reward_ount_reserved,s1.Reward_count_sum*(100-s1.param_yuliu) Reward_count_return from ( "
	 * + " SELECT a1.* , a2.return_money ,a2.return_coe  , " +
	 * " (a2.return_money*a2.return_coe-(SELECT a4.index from sales_index a4 where a4.paeam_year='2016')) Reward_count_sum , "
	 * +
	 * " (SELECT a3.reserve from reward_reserve a3 where a3.paeam_year='2016') param_yuliu "
	 * + " from  " + " (SELECT t5.order_no, " + " t5.sales_id, " +
	 * " t5.sales_name , " +
	 * " (select t1.dict_name from  data_dict t1 where t1.dict_type='dist' and t1.dict_value=t4.area) arer , "
	 * +
	 * " (select t2.prod_name from  product_info t2 where t2.prod_id=t4.prod_no) pro_name, "
	 * + " t4.acount_fee, " + " t4.order_amount, " +
	 * " t4.order_version,t5.sales_point,t5.magt_fee " +
	 * " from order_info t4,sale_order t5 " +
	 * " WHERE  t5.stateflag='0'and t5.order_no=t4.order_no ) a1,product_issue a2  "
	 * +
	 * " where a1.sales_id=a2.sale_id and a1.order_version=a2.order_no and a2.prod_flag='0' ) s1 "
	 * ; list = jt.queryForList(sql); return list; }
	 */
	// 奖金计算列表
	public List<Map<String, Object>> reward_count() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		sql = "select sales_name,order_no,reward_id,prod_rs_id,to_char(maget_fee, 'fm99,999,999,999,999,999,990.00') magt_fee, "
				+ " to_char(order_amount, 'fm99,999,999,999,999,999,990.00') order_amount,"
				+ "to_char(return_money, 'fm99,999,999,999,999,999,990.00') return_money,"
				+ "to_char(has_reward, 'fm99,999,999,999,999,999,990.00') has_reward, "
				+ "  to_char(reward_reserve, 'fm99,999,999,999,999,999,990.00') reserve_reward,"
				+ "to_char(sum_reward, 'fm99,999,999,999,999,999,990.00') sum_reward,return_coe, "
				+ " area,prod_name " + " from  " + " reword_count_temporary ";
		list = jt.queryForList(sql);
		return list;
	}

	// 奖金计算临时表
	public int saveTempora() {
		String sql = "";
		sql = " truncate table reword_count_temporary ";
		jt.update(sql);
		sql = "INSERT into reword_count_temporary ( cust_id,cust_name,reward_id,order_no,sales_id,area,prod_name,prod_type,order_version,sales_name,order_amount,return_money,prod_rs_id,return_coe, "
				+ "				 return_batch ,sum_reward,has_reward,reward_reserve    "
				+ "				 ,group_point,reserve,maget_fee	 )"
				+ "	 SELECT a1.cust_no,a1.cust_name,cast(public.seq_id() as BIGINT) reward_id, a1.order_no ,a1.sales_id,a1.arer,a1.prod_name,a1.prod_type,a1.order_version,a1.sales_name,a1.order_amount,  "
				+ "			a2.return_money ,a2.prod_rs_id,a2.return_coe  ,a2.return_batch,"
				+ "  a1.obc_amount*(a2.return_coe/cast(a1.prod_diffcoe as float))*(cast(a1.sales_point as FLOAT)/100) sum_reward,"
				+ "  a1.obc_amount*(a2.return_coe/cast(a1.prod_diffcoe as float))*(cast(a1.sales_point as FLOAT)/100)*((1-cast(a1.reserve as FLOAT)/100)) has_reward,"
				+ " a1.obc_amount*(a2.return_coe/cast(a1.prod_diffcoe as float))*(cast(a1.sales_point as FLOAT)/100)*(cast(a1.reserve as FLOAT)/100) reward_reserve,cast(a1.sales_point as FLOAT),a1.reserve,a1.magt_fee "
				+ "		 	  from   "
				+ "		 	  (SELECT "
				+ "		 	  (SELECT a3.reserve from reward_reserve a3 where t4.entry_time > to_date(a3.start_time,'YYYY-MM-DD') and t4.entry_time < to_date(a3.end_time,'YYYY-MM-DD')) reserve,  "
				+ "		   (SELECT a5.reward_give from reward_reserve a5 where t4.entry_time > to_date(a5.start_time,'YYYY-MM-DD') and t4.entry_time < to_date(a5.end_time,'YYYY-MM-DD')) reward_give , "
				+ "			   t5.order_no,T5.obc_amount,T5.sales_point,t5.magt_fee,    "
				+ "			   t5.sales_id,t4.cust_no,CASE WHEN t7.cust_type='1' THEN (SELECT c1.cust_name from cust_info c1 where c1.cust_id=t7.id )  "
				+ "			      ELSE (SELECT o1.org_name from org_info  o1 where o1.org_id=t7.id ) END cust_name,"
				+ "			  (select t1.dict_name from  data_dict t1 where t1.dict_type='dist' and t1.dict_value=t4.area) arer , "
				+ "			  t6.prod_name,t6.prod_type , "
				+ "			   t4.order_version,t5.sales_name,t4.order_amount,t4.prod_diffcoe   "
				+ "			  from order_info t4,sale_order t5,product_info t6,bill_info t7  "
				+ "			   WHERE  t4.stateflag='0'and t5.order_no=t4.order_no AND t5.order_version=t4.order_version "
				+ " and t6.prod_id=t4.prod_no and t7.id=t4.cust_no ) a1,product_issue a2   "
				+ "	   where a1.sales_id=a2.sale_id and a1.order_version=a2.order_no "
				+ "and a1.order_no=a2.order_id " + " and a2.prod_flag='5' ";
		int j = 0;
		j = jt.update(sql);
		/*
		 * sql="SELECT count(reward_id) from reword_count_temporary"; int
		 * sum=jt.queryForInt(sql); Object[] sqlparam = new Object[sum];
		 * List<Object[]> list = new ArrayList<Object[]>(); for (int i = 0; i <
		 * sum; i++) { sqlparam[i] = jdbcUtil.seq();
		 * 
		 * } sql = "update reword_count_temporary set reward_id=?";
		 * jt.batchUpdate(sql, list);
		 */
		return j;
	}
	//七月份之后的
	public int saveTempora1() {
		String sql = "";
		sql = " truncate table reword_count_temporary ";
		jt.update(sql);
		sql = "INSERT into reword_count_temporary ( cust_id,cust_name,reward_id,order_no,sales_id,area,prod_name,prod_type,order_version,sales_name,order_amount,return_money,prod_rs_id,return_coe, "
				+ "				 return_batch ,sum_reward,has_reward,reward_reserve    "
				+ "				 ,group_point,reserve,maget_fee	 )"
				+ "	 SELECT a1.cust_no,a1.cust_name,cast(public.seq_id() as BIGINT) reward_id, a1.order_no ,a1.sales_id,a1.arer,a1.prod_name,a1.prod_type,a1.order_version,a1.sales_name,a1.order_amount,  "
				+ "			a2.return_money ,a2.prod_rs_id,a2.return_coe  ,a2.return_batch,"
				+ "  a1.obc_amount*(a2.return_coe)*(cast(a1.sales_point as FLOAT)/100) sum_reward,"
				+ "  a1.obc_amount*(a2.return_coe)*(cast(a1.sales_point as FLOAT)/100)*((1-cast(a1.reserve as FLOAT)/100)) has_reward,"
				+ " a1.obc_amount*(a2.return_coe)*(cast(a1.sales_point as FLOAT)/100)*(cast(a1.reserve as FLOAT)/100) reward_reserve,cast(a1.sales_point as FLOAT),a1.reserve,a1.magt_fee "
				+ "		 	  from   "
				+ "		 	  (SELECT "
				+ "		 	  (SELECT a3.reserve from reward_reserve a3 where t4.entry_time > to_date(a3.start_time,'YYYY-MM-DD') and t4.entry_time < to_date(a3.end_time,'YYYY-MM-DD')) reserve,  "
				+ "		   (SELECT a5.reward_give from reward_reserve a5 where t4.entry_time > to_date(a5.start_time,'YYYY-MM-DD') and t4.entry_time < to_date(a5.end_time,'YYYY-MM-DD')) reward_give , "
				+ "			   t5.order_no,T5.obc_amount,T5.sales_point,t5.magt_fee,    "
				+ "			   t5.sales_id,t4.cust_no,CASE WHEN t7.cust_type='1' THEN (SELECT c1.cust_name from cust_info c1 where c1.cust_id=t7.id )  "
				+ "			      ELSE (SELECT o1.org_name from org_info  o1 where o1.org_id=t7.id ) END cust_name,"
				+ "			  (select t1.dict_name from  data_dict t1 where t1.dict_type='dist' and t1.dict_value=t4.area) arer , "
				+ "			  t6.prod_name,t6.prod_type , "
				+ "			   t4.order_version,t5.sales_name,t4.order_amount,t4.prod_diffcoe   "
				+ "			  from order_info t4,sale_order t5,product_info t6,bill_info t7  "
				+ "			   WHERE  t4.stateflag='0'and t5.order_no=t4.order_no AND t5.order_version=t4.order_version "
				+ " and t6.prod_id=t4.prod_no and t7.id=t4.cust_no ) a1,product_issue a2   "
				+ "	   where a1.sales_id=a2.sale_id and a1.order_version=a2.order_no "
				+ "and a1.order_no=a2.order_id " + " and a2.prod_flag='5' ";
		int j = 0;
		j = jt.update(sql);
		/*
		 * sql="SELECT count(reward_id) from reword_count_temporary"; int
		 * sum=jt.queryForInt(sql); Object[] sqlparam = new Object[sum];
		 * List<Object[]> list = new ArrayList<Object[]>(); for (int i = 0; i <
		 * sum; i++) { sqlparam[i] = jdbcUtil.seq();
		 * 
		 * } sql = "update reword_count_temporary set reward_id=?";
		 * jt.batchUpdate(sql, list);
		 */
		return j;
	}

	/*
	 * // 奖金计算页面金额变动 public int reword_count_change(double has_reward, double
	 * reserve_reward, long reward_id) { String sql = ""; sql =
	 * "update reword_count_temporary SET has_reward=?,reserve_reward=? WHERE reward_id=? "
	 * ; return jt.update(sql); }
	 * 
	 * // 查询奖金计算临时表 public List<Map<String, Object>> searchTempora() { String
	 * sql = ""; sql = ""; return jt.queryForList(sql); }
	 */

	// 奖金计算保存
	public int[] rewaord_save(List<Object[]> batchArgs) {
		int[] i = null;
		String sql = "INSERT INTO reward_count_grant ( cust_name,area,reward_id,prod_rs_id "
				+ " ,return_batch "
				+ " ,order_no "
				+ " ,count_batch "
				+ " ,order_version "
				+ " ,sales_id "
				+ " ,return_money "
				+ " ,order_amount "
				+ " ,prod_name "
				+ " ,sales_name "
				+ " ,group_point "
				+ " ,sales_index "
				+ " ,reward_give "
				+ " ,reward_reserve,return_coe "
				+ " ,has_reward " 
				+ " ,reserve "
				+ " ,sum_reward,cust_id,prod_type  "
				+ "     )SELECT  cust_name,area,reward_id,prod_rs_id "
				+ " ,return_batch "
				+ " ,order_no "
				+ " ,count_batch,order_version,sales_id,return_money "
				+ " ,order_amount,prod_name,sales_name "
				+ " ,group_point "
				+ " ,sales_index "
				+ " ,reward_give,reward_reserve "
				+ " ,return_coe "
				+ " ,has_reward "
				+ " ,reserve "
				+ " ,sum_reward,cust_id,prod_type "
				+ "  from reword_count_temporary t1 where reward_id=?";
		i = jt.batchUpdate(sql, batchArgs);

		String prod_sql = "update product_issue t1 set prod_flag='6' from reword_count_temporary t3 "
				+ " where t1.prod_flag='5' and t3.prod_rs_id=t1.prod_rs_id "
				+ "and t3.sales_id=t1.sale_id "
				+ "and t3.order_no=t1.order_id "
				+ " and t3.order_version=t1.order_no "
				+ " and t3.reward_id=?  ";
		int j[] = jt.batchUpdate(prod_sql, batchArgs);
		return i;

	}

	// 奖金计算save后更新其他表
	public void reward_save_update(List<Object[]> batchArgs, long count_batch,String[] reward) throws SQLException {
		// 更新计算表
		String rew_sql = "update reward_count_grant set count_batch= "
				+ count_batch
				+ ",count_time=CURRENT_DATE,stateflag='0' where reward_id=?";
		jt.batchUpdate(rew_sql, batchArgs);
		
		// 将预留数据插入到预留表中
		List<Object[]> grant_list = new ArrayList<Object[]>();
		for(int j=0;j<reward.length;j++){
			List<Map<String, Object>> grant =grant(Long.parseLong(reward[j]));
			Object[] rant ={Long.parseLong(grant.get(0).get("reward_id").toString()),
					grant.get(0).get("return_batch").toString(),
					Long.parseLong(grant.get(0).get("order_no").toString()),
					Long.parseLong(grant.get(0).get("order_version").toString()),
					Long.parseLong(grant.get(0).get("sales_id").toString()),
					Double.valueOf(grant.get(0).get("return_money").toString()),
					Double.parseDouble(grant.get(0).get("return_coe").toString()),
					Double.parseDouble(grant.get(0).get("reward_reserve").toString()),
					grant.get(0).get("sales_name").toString(),
					grant.get(0).get("prod_name").toString(),
					grant.get(0).get("area").toString(),0
					};
			grant_list.add(rant);
		}
		String save_reserve = "INSERT INTO reward_resrv_pool "
				+ "(reserve_id,count_return,order_no,order_version,sales_id,return_money,return_coe,reserve"
				+ ",create_time,sales_name,prod_name,area,sign )  "
				+ "values(?,?,?,?,?,?,?,?,current_date,?,?,?,?)     ";
		int[] j = jt.batchUpdate(save_reserve, grant_list);
		// 删除临时表
		String sql = " truncate table reword_count_temporary ";
		int i = jt.update(sql);
	}
	//查询预留表
public List<Map<String, Object>> grant(long id){
	String sql="SELECT  reward_id, "                                                    
			+ "					  t1.return_batch    "
			+ "					   ,t1.order_no	       "
			+ "					   ,t1.order_version       "
			+ "					   ,t1.sales_id	                "
			+ "					   ,t1.return_money	      "
			+ "					    ,t1.return_coe	                                                                               "
			+ "					  ,t1.reward_reserve	                                                                                     "
			+ "					  ,t1.reward_time,sales_name,prod_name,                                                            "
			+ "   area,0	       "
			+ "from reward_count_grant t1 WHERE t1.reward_id=?  ";
	return jt.queryForList(sql,id);
}
	/*
	 * // 奖金计算查询 public List reward_count_search(long reward_id) { sql =
	 * "select sum_reward,has_reward,reserve from reword_count_temporary" +
	 * " where reward_id=? "; return jt.queryForList(sql, reward_id); }
	 */
	// 奖金计算列表 --多条件查询
	public List<Map<String, Object>> reward_count_select(String area,
			String product, String rm) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		String selsql = "select sales_name,order_no,reward_id,prod_rs_id,"
				+ "to_char(coalesce(maget_fee,0), 'fm99,999,999,999,999,999,990.00') magt_fee, "
				+ " to_char(coalesce(order_amount,0), 'fm99,999,999,999,999,999,990.00') order_amount,"
				+ "to_char(coalesce(has_reward,0), 'fm99,999,999,999,999,999,990.00') has_reward, "
				+ "to_char(coalesce(return_money,0), 'fm99,999,999,999,999,999,990.00') return_money,"
				+ "  to_char(coalesce(reward_reserve,0), 'fm99,999,999,999,999,999,990.00') reserve_reward, "
				+ " to_char(coalesce(sum_reward,0), 'fm99,999,999,999,999,999,990.00') sum_reward,"
				+ "to_char(coalesce(return_coe,0), 'fm99,999,999,999,999,999,990.00') return_coe, "
				+ " area,prod_name " + " from  " + " reword_count_temporary  ";
		sql.append(selsql);
		int j = 0;
		if (area != null && area != "") {
			sql.append(" where area like '%" + area + "%'");
			j++;
		}
		if (product != null && product != "") {
			if (j != 0) {
				sql.append("and prod_name like '%" + product + "%'");
			} else {
				sql.append("where prod_name like '%" + product + "%'");
				j++;
			}
		}
		if (rm != null && rm != "") {
			if (j != 0) {
				sql.append("and sales_name like '%" + rm + "%'");
			} else {
				sql.append("where sales_name like '%" + rm + "%'");
			}
		}
		list = jt.queryForList(sql.toString());
		return list;
	}

	// 奖金计算提交--销售可看
	public int rewardSave(String id, long count_batch) {
		String sql = "";
		sql = "update reward_count_grant set stateflag='1' and give_batch=?  where id=?";
		String[] rewardId = id.split(",");
		int j = 0;
		long reward_id = 0;
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < rewardId.length; i++) {
			if (rewardId[i] != null && rewardId[i] != "") {
				reward_id = Long.parseLong(rewardId[i]);
				list.add(reward_id);
				j++;
			}
		}
		j = 0;
		for (int i = 0; i < list.size(); i++) {
			j = jt.update(sql, count_batch, list.get(i));
		}
		return j;
	}

	// 奖金核对页面- 销售已确认的奖金列表
	public List<Map<String, Object>> searchReword() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		/*
		 * sql =
		 * "select sales_name,order_no,reward_id,prod_rs_id,to_char(maget_fee, '9,999.99') magt_fee, "
		 * +
		 * " to_char(order_amount, '9,999.99') order_amount,to_char(has_reward, '9,999.99') has_reward, "
		 * +
		 * "  to_char(reserve, '9,999.99') reserve_reward,to_char(sum_reward, '9,999.99') sum_reward,return_coe, "
		 * + " area,prod_name " + " from  " +
		 * " reward_count_grant  where stateflag='1'  ";
		 */
		sql = "SELECT sum(sum_reward) sum_reward,sum(has_reward) has_reward ,sum(reserve) reserve, sales_id,area,sales_name  "
				+ " from reward_count_grant "
				+ " where stateflag='1'  "
				+ " GROUP BY sales_id,area,sales_name ";
		list = jt.queryForList(sql);
		return list;
	}

	// 奖金核对页面--多条件 查询销售已确认的奖金
	public List<Map<String, Object>> reward_give_info(String rm,
			long employee_code, String product_type) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		StringBuffer give_sql = new StringBuffer();
		sql = "SELECT sum(sum_reward) sum_reward,sum(has_reward) has_reward ,sum(reserve) reserve, sales_id,area,sales_name   "
				+ " from reward_count_grant " + " where stateflag='1'  ";
		give_sql.append(sql);
		if (rm != null && rm != "") {
			give_sql.append("and sales_name like '%" + rm + "%'");
		}
		if (employee_code != 0) {
			give_sql.append("and sales_id =" + employee_code);
		}
		give_sql.append(" GROUP BY sales_id,area,sales_name   ");
		/*
		 * if (product_type != null && product_type != "") {
		 * give_sql.append("and t1.prod_type like '%" + product_type + "%'"); }
		 */

		list = jt.queryForList(give_sql.toString());
		return list;
	}

	// 奖金核对页面--奖金保存
	public int reward_check_save(String give_batch) {
		String sql = "";
		/*
		 * if (id.substring(id.length() - 1, id.length()) == ",") { id =
		 * id.substring(0, id.length() - 1); } String[] rewardId =
		 * id.split(",");
		 * 
		 * List<Object[]> list = new ArrayList<Object[]>(); //
		 * list.add(rewardId);
		 * 
		 * int j = 0; long reward_id = 0;
		 * 
		 * for (int i = 0; i < rewardId.length; i++) { Object[] sqlparam = {
		 * Long.parseLong(rewardId[i]) }; list.add(sqlparam); }
		 */
		long givebatch = 0;
		if (give_batch != null && !give_batch.equals("")) {
			givebatch = Long.parseLong(give_batch);
		}

		// 更新计算表 将标志设为已发放
		sql = "update reward_count_grant set stateflag='2',give_batch="
				+ givebatch + ",reward_time=CURRENT_DATE where  stateflag='1' ";
		int i = jt.update(sql);
		return i;
	}

	// 销售页面--团队奖金列表
	public List<Map<String, Object>> reward_group_search(long ueser_id) {
		String sql = "select id,group_id,reward_batch_id,order_no,sum_amount,reward_amount,reward_return,SUBSTR(count_time,1,10) count_time "
				+ "from reward_group where stateflag='0' and sales_id=? ";
		return jt.queryForList(sql, ueser_id);
	}

	// 销售页面--多条件查询
	public List<Map<String, Object>> group_sales_select(long count_batch,
			long ueser_id) {
		String sql = "select id,group_id,reward_batch_id,order_no,sum_amount,reward_amount,reward_return,count_time "
				+ "from reward_group where stateflag='0' and sales_id="
				+ ueser_id + " ";
		if (count_batch != 0) {
			sql += " and reward_batch_id =" + count_batch;
		}
		return jt.queryForList(sql);
	}

	// 销售页面--团队奖金确认
	public int[] group_confirm(String groupid) {
		if (groupid.substring(groupid.length() - 1, groupid.length()) == ",") {
			groupid = groupid.substring(0, groupid.length() - 1);
		}
		String[] group_id = groupid.split(",");

		List<Object[]> list = new ArrayList<Object[]>();

		for (int i = 0; i < group_id.length; i++) {
			Object[] sqlparam = { Long.parseLong(group_id[i]) };
			list.add(sqlparam);
		}
		String sql = " UPDATE reward_group set stateflag='1' where id=? and stateflag='0' ";
		return jt.batchUpdate(sql, list);
	}

	// 团队核对页面--团队奖金列表
	public List<Map<String, Object>> reward_check_search() {
		String sql = "select group_id,name,sales_name,reward_batch_id,order_no,sum_amount,reward_amount,reward_return,count_time "
				+ "from reward_group where stateflag='1' ";
		return jt.queryForList(sql);
	}

	// 团队核对页面--奖金发放
	public int group_check_save(long give_batch) {
		String sql = "UPDATE reward_group SET stateflag='2',give_batch=? WHERE stateflag='1' ";
		return jt.update(sql,give_batch);
	}

	// 团队奖金多条件查询
	public List<Map<String, Object>> group_check_select(String name,
			String sales_name) {
		String sql = "select id,group_id,name,sales_name,reward_batch_id,order_no,sum_amount,reward_amount,reward_return,count_time "
				+ "from reward_group where stateflag='1'  ";
		if (name != null && !name.equals("")) {
			sql += " and name like '%" + name + "%' ";
		}
		if (sales_name != null && !sales_name.equals("")) {
			sql += " and sales_name like '%" + sales_name + "%' ";
		}

		return jt.queryForList(sql);
	}

	// 团队计算页面--团队考核
	public int group_check(String kaohe_start_time, String kaohe_end_time) {
		String sql = "";
		sql = "INSERT INTO group_check (sales_id,order_no,order_version,group_id"
				+ ",stateflag,datetime,group_point) "
				+ " SELECT d1.sales_id,d1.order_no,d1.order_version,d2.workgroup_id,'1'"
				+ ",CURRENT_DATE,d4.group_point from "
				+ "sale_order d1,upm_user d2,order_info d3 , group_achievement d4 "
				+ "  where  d3.entry_time >=to_date('"
				+ kaohe_start_time
				+ "','YYYY-MM-DD')  and  d3.entry_time <=to_date('"
				+ kaohe_end_time
				+ "','YYYY-MM-DD')  and to_date(d4.start_time,'YYYY-MM-DD') >= to_date('"
				+ kaohe_start_time
				+ "','YYYY-MM-DD') "
				+ " and  to_date(d4.end_time,'YYYY-MM-DD') <= to_date('"
				+ kaohe_end_time
				+ "','YYYY-MM-DD') "
				+ " and d4.group_id=d2.workgroup_id "
				+ " and d1.sales_id=d2.user_id "
				+ " and d1.order_no=d3.order_no and d1.order_version=d3.order_version and d2.workgroup_id in "
				+ " ( "
				+ " SELECT case when s1.su_reward>s2.group_index*0.6 then s1.workgroup_id   end from "
				+ " (SELECT sum(t1.order_amount) su_reward,t4. workgroup_id "
				+ " FROM  order_info t1, "
				+ " (SELECT t2.*,t3.* from sale_order t2,upm_user t3 where t2.sales_id=t3.user_id) t4  "
				+ " where t1.entry_time >=to_date('"
				+ kaohe_start_time
				+ "','YYYY-MM-DD') and t1.entry_time <=to_date('"
				+ kaohe_end_time
				+ "','YYYY-MM-DD')  GROUP BY t4.workgroup_id) s1,group_index s2 "
				+ " where  to_date(s2.start_time,'YYYY-MM-DD') >=to_date('"
				+ kaohe_start_time
				+ "','YYYY-MM-DD') and  to_date(s2.end_time,'YYYY-MM-DD') <=to_date('"
				+ kaohe_end_time
				+ "','YYYY-MM-DD')     and s2.group_id=s1.workgroup_id  "
				+ " )";
		/*
		 * sql =
		 * "INSERT INTO group_check (sales_id,order_no,order_version,group_id,stateflag,datetime) "
		 * +
		 * "SELECT d1.sales_id,d1.order_no,d1.order_version,d2.workgroup_id,'1',(SELECT now()) from sale_order d1,upm_user d2,order_info d3  "
		 * +
		 * "	   where d1.sales_id=d2.user_id and d1.order_no=d3.order_no and d1.order_version=d3.order_version and  SUBSTR(d3.create_time,1,4)=cast (extract(year from now()) as VARCHAR) "
		 * +
		 * "	  and  SUBSTR(d3.create_time,6,2) <='12' and  SUBSTR(d3.create_time,6,2) >='07'  and d2.workgroup_id in "
		 * + "	  ( " +
		 * "	  SELECT case when s1.su_reward>s2.group_index*0.6 then s1.workgroup_id   end from "
		 * + "	  (SELECT sum(t1.order_amount) su_reward,t4. workgroup_id " +
		 * "		  FROM  order_info t1, " +
		 * "	  (SELECT t2.*,t3.* from sale_order t2,upm_user t3 where t2.sales_id=t3.user_id) t4  "
		 * +
		 * "	  where SUBSTR(t1.create_time,1,4)=cast (extract(year from now()) as VARCHAR)  "
		 * +
		 * "	  and  SUBSTR(t1.create_time,6,2) <='12' and  SUBSTR(t1.create_time,6,2) >='07' "
		 * + "  GROUP BY t4. workgroup_id) s1,group_index s2  " + "  )";
		 */
		try {
			return jt.update(sql);
		} catch (DuplicateKeyException e) {
			return -1;
		}
	}

	// 团队计算页面--团队计算数据查询 将查询结果插入到临时表中
	public int group_count_temporary(String check_start_time,
			String check_end_time) {
		// 删除临时表
		String sql = " truncate table reward_group_temporary ";
		jt.update(sql);
		// 重新向临时表中插入数据
		/* if (quarter.equals("1")) { */
		sql = "INSERT INTO  reward_group_temporary (id,sum_return,order_no,group_id,order_version,group_point,"
				+ "name,sum_amount,area,sales_name,sales_id,prod_name "
				+ ",start_time,end_time ) "
				+ " SELECT cast(public.seq_id() as BIGINT) id,s1.*,s2.name,s4.order_amount,s4.area,s3.user_name,s3.user_id,"
				+ " (SELECT prod_name from product_info where prod_id=s4.prod_no) prod_name "
				+ ",?,? from  "
				+ " (select sum(t1.return_money) sum_return,t1.order_id,t2.group_id,t2.order_version,t2.group_point "
				+ " from product_issue t1 , group_check t2   "
				+ " where  (t1.group_flag<>'1' or t1.group_flag is null) "
				+ "and  t1.return_date > to_date(?, 'yyyy-mm-dd') "
				+ "and t1.return_date < to_date(?, 'yyyy-mm-dd')   "
				+ " and t1.sale_id=t2.sales_id and t1.order_id=t2.order_no and t1.order_no=t2.order_version "
				+ " GROUP BY t2.group_id,t1.order_id,t2.order_version,t2.group_point) s1,upm_workgroup s2,order_info s4,upm_user s3 "
				+ " where s1.group_id=s2.workgroup_id and s1.order_id=s4.order_no and s1.order_version=s4.order_version and "
				+ " s3.workgroup_id=s2.workgroup_id and s3.workgroup_role_codes=('{leader,worker}') "
				+ "  ";
		/*
		 * } else { if (quarter.equals("2")) { sql =
		 * "INSERT INTO  reward_group_temporary (id,sum_return,order_no,group_id,order_version,name,sum_amount,area,sales_name,group_point "
		 * + ",month  ) " +
		 * " SELECT cast(public.seq_id() as BIGINT) id,s1.*,s2.name,s4.order_amount,s4.area,s3.user_name,s5.group_point,'6'  from  "
		 * +
		 * " (select sum(t1.pllot_money) sum_return,t1.order_id,t2.group_id,t2.order_version "
		 * + " from product_issue t1 , group_check t2   " +
		 * " where t1.group_flag<>'1' and SUBSTR(to_char(t1.return_date,'YYYY-MM-DD'),1,4)<cast (extract(year from now()) as VARCHAR) "
		 * + " or SUBSTR(to_char(t1.return_date,'YYYY-MM-DD'),7,1)<'7'   " +
		 * " and t1.sale_id=t2.sales_id and t1.order_id=t2.order_no and t1.order_no=t2.order_version "
		 * +
		 * " GROUP BY t2.group_id,t1.order_id,t2.order_version) s1,upm_workgroup s2,order_info s4,upm_user s3,group_achievement s5 "
		 * +
		 * " where s1.group_id=s2.workgroup_id and s1.order_id=s4.order_no and s1.order_version=s4.order_version and "
		 * +
		 * " s3.workgroup_id=s2.workgroup_id and s3.workgroup_role_codes=('{leader,worker}') "
		 * + " and s5.group_id=s2.workgroup_id"; } else { if
		 * (quarter.equals("3")) { sql =
		 * "INSERT INTO  reward_group_temporary (id,sum_return,order_no,group_id,order_version,name,sum_amount,area,sales_name,group_point "
		 * + " ,month) " +
		 * " SELECT cast(public.seq_id() as BIGINT) id,s1.*,s2.name,s4.order_amount,s4.area,s3.user_name,s5.group_point,'9' from  "
		 * +
		 * " (select sum(t1.pllot_money) sum_return,t1.order_id,t2.group_id,t2.order_version "
		 * + " from product_issue t1 , group_check t2   " +
		 * " where t1.group_flag<>'1' and SUBSTR(to_char(t1.return_date,'YYYY-MM-DD'),1,4)<cast (extract(year from now()) as VARCHAR) "
		 * + " or SUBSTR(to_char(t1.return_date,'YYYY-MM-DD'),6,2)<'10'   " +
		 * " and t1.sale_id=t2.sales_id and t1.order_id=t2.order_no and t1.order_no=t2.order_version "
		 * +
		 * " GROUP BY t2.group_id,t1.order_id,t2.order_version) s1,upm_workgroup s2,order_info s4,upm_user s3,group_achievement s5 "
		 * +
		 * " where s1.group_id=s2.workgroup_id and s1.order_id=s4.order_no and s1.order_version=s4.order_version and "
		 * +
		 * " s3.workgroup_id=s2.workgroup_id and s3.workgroup_role_codes=('{leader,worker}') "
		 * + " and s5.group_id=s2.workgroup_id"; } else { sql =
		 * "INSERT INTO  reward_group_temporary (id,sum_return,order_no,group_id,order_version,name,sum_amount,area,sales_name,group_point "
		 * + ",month ) " +
		 * " SELECT cast(public.seq_id() as BIGINT) id,s1.*,s2.name,s4.order_amount,s4.area,s3.user_name,s5.group_point,'12' from  "
		 * +
		 * " (select sum(t1.pllot_money) sum_return,t1.order_id,t2.group_id,t2.order_version "
		 * + " from product_issue t1 , group_check t2   " +
		 * " where t1.group_flag<>'1' and  SUBSTR(to_char(t1.return_date,'YYYY-MM-DD'),1,4)<cast (extract(year from now()) as VARCHAR) "
		 * + " or SUBSTR(to_char(t1.return_date,'YYYY-MM-DD'),6,2)<='12'   " +
		 * " and t1.sale_id=t2.sales_id and t1.order_id=t2.order_no and t1.order_no=t2.order_version "
		 * +
		 * " GROUP BY t2.group_id,t1.order_id,t2.order_version) s1,upm_workgroup s2,order_info s4,upm_user s3,group_achievement s5 "
		 * +
		 * " where s1.group_id=s2.workgroup_id and s1.order_id=s4.order_no and s1.order_version=s4.order_version and "
		 * +
		 * " s3.workgroup_id=s2.workgroup_id and s3.workgroup_role_codes=('{leader,worker}') "
		 * + " and s5.group_id=s2.workgroup_id"; } } }
		 */
		return jt.update(sql, check_start_time, check_end_time,
				check_start_time, check_end_time);

	}

	// 团队奖金计算页面--计算

	public List<Map<String, Object>> group_count() {
		String sql = "";
		// 币种换算
		sql = "update reward_group_temporary SET"
				+ " sum_return=t2.sum_return,reward_return=t2.sum_return*(t2.group_point/100)  from ( "
				+ "  SELECT case  a3.prod_currency "
				+ "  when '1' then a2.sum_return  "
				+ "  when '2' then a2.sum_return*0.8 "
				+ "  when '3' then a2.sum_return*6 "
				+ "  end  sum_return "
				+ "  ,a2.id,a2.group_point from order_info a1,reward_group_temporary a2,product_info a3  "
				+ "  where a1.order_no=a2.order_no and a1.order_version=a2.order_version and a3.prod_id=a1.prod_no "
				+ " ) t2 where reward_group_temporary.id=t2.id";
		jt.update(sql);
		// 奖金计算查询
		sql = "SELECT id,group_id,name,order_no,prod_name,sum_amount,"
				+ " (SELECT dict_name from data_dict where data_dict.dict_value=reward_group_temporary.area and dict_type='dist' ) area "
				+ ",sales_name,sum_return, "
				+ " reward_return from reward_group_temporary";
		return jt.queryForList(sql);
	}

	// 团队奖金临时表查询
	public List<Map<String, Object>> group_count_select() {
		// 奖金计算查询
		String sql = "";
		sql = "SELECT id,group_id,name,order_no,prod_name,sum_amount,"
				+ " (SELECT dict_name from data_dict where data_dict.dict_value=reward_group_temporary.area and dict_type='dist' ) area "
				+ ",sales_name,sum_return, "
				+ " reward_return from reward_group_temporary";
		return jt.queryForList(sql);
	}

	// 团队计算保存
	public int[] group_count_save(String groupid) {
		if (groupid.substring(groupid.length() - 1, groupid.length()) == ",") {
			groupid = groupid.substring(0, groupid.length() - 1);
		}
		String[] group_id = groupid.split(",");

		List<Object[]> list = new ArrayList<Object[]>();

		for (int i = 0; i < group_id.length; i++) {
			Object[] sqlparam = { Long.parseLong(group_id[i]) };
			list.add(sqlparam);
		}
		String sql = "";

		sql = "INSERT into reward_group (id,group_id, "
				+ "  reward_batch_id,name,order_no,order_version,sales_id,prod_name,sales_name,sum_amount, "
				+ "  sum_return,goal_per,group_point,count_time,reward_return,area,stateflag,start_time,end_time "
				+ "  ) SELECT "
				+ " id,group_id,cast((select * from public.seq_id()) as BIGINT), "
				+ "  name,order_no,order_version,sales_id,prod_name,sales_name, "
				+ "  sum_amount,sum_return,goal_per,group_point,CURRENT_DATE,reward_return,area,0,start_time,end_time "
				+ " from reward_group_temporary where id=?";
		jt.batchUpdate(sql, list);

		sql = "UPDATE product_issue t1 SET group_flag='1' from reward_group_temporary t2, upm_user t3 "
				+ "where (t1.group_flag is null or t1.group_flag<>'1')  "
				+ " and to_char(t1.return_date,'YYYY-MM-DD') < t2.end_time "
				+ "    and to_char(t1.return_date,'YYYY-MM-DD') >t2.start_time "
				+ " and t1.order_id=t2.order_no and t1.order_no=t2.order_version "
				+ " and t3.user_id=t1.sale_id and t3.workgroup_id=t2.group_id "
				+ " and t2.id=? ";

		int[] j = jt.batchUpdate(sql, list);

		// 删除临时表
		sql = " truncate table reward_group_temporary ";
		jt.update(sql);
		return j;
	}

	// 团队奖金计算页面-多条件查询
	public List<Map<String, Object>> reward_group_select(String area,
			String product, String sales) {
		String sql = " SELECT id,group_id,name,order_no,prod_name,sum_amount,area,sales_name,sum_return,reward_return from ( "
				+ " SELECT id,group_id,name,order_no,prod_name,sum_amount,"
				+ "(SELECT dict_name from data_dict where data_dict.dict_value=reward_group_temporary.area and dict_type='dist' ) area,"
				+ "sales_name,sum_return, "
				+ " reward_return from reward_group_temporary  ) t1 ";
		int j = 0;
		if (area != null && !area.equals("")) {
			sql += " where t1.area like '%" + area + "%' ";
			j = j + 1;
		}

		if (sales != null && !sales.equals("")) {
			if (j > 0) {
				sql += " and t1.sales_name like '%" + sales + "%' ";
			} else {
				sql += " where t1.sales_name like '%" + sales + "%' ";
				j = j + 1;
			}
		}
		if (product != null && !product.equals("")) {
			if (j > 0) {
				sql += " and t1.prod_name like '%" + product + "%' ";
			} else {
				sql += " where  t1.prod_name like '%" + product + "%' ";
			}
		}
		return jt.queryForList(sql);
	}

	// 计算页面多条件查询下 产品名称下拉框
	public List<Map<String, Object>> reward_count_prod() {
		String sql = "SELECT prod_name from product_info";
		return jt.queryForList(sql);
	}

	// 计算页面多条件查询下 地区下拉框
	public List<Map<String, Object>> reward_count_area() {
		String sql = "SELECT dict_name from data_dict where dict_type='dist' ";
		return jt.queryForList(sql);
	}

	// 查询已发放奖金的上一个批次
	public List<Map<String, Object>> searchBatch() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		sql = "select give_batch from reward_count_grant where reward_time in ( select MAX(reward_time)  from reward_count_grant where stateflag='1'  )";
		list = jt.queryForList(sql);
		return list;
	}

	// 查询历史批次
	public List<Map<String, Object>> search_reward_count() {
		return null;
	}

	// 查询奖金发放纪录--销售
	public List<Map<String, Object>> search_reward_list(long sales_id) {
		String sql = "";
		sql = "SELECT to_char(has_reward, 'fm99,999,999,999,999,999,990.00') has_reward,count_batch,"
				+ " order_no,to_char(order_amount, 'fm99,999,999,999,999,999,990.00') order_amount, "
				+ " order_no,to_char(reserve, 'fm99,999,999,999,999,999,990.00') reserve, "
				+ " prod_name ,"
				+ "(SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and reward_count_grant.stateflag=t1.dict_value) stateflag ,  "
				+ "	 sales_name,sales_id "
				+ "	from reward_count_grant where sales_id=?";
		return jt.queryForList(sql, sales_id);
	}

	// 查询奖金计算记录 全量
	public List<Map<String, Object>> search_reward_list_all(int SkipSize, int PageSize, String area, String product, String sales) {
		String sql = "";
		sql="SELECT to_char(has_reward, 'fm99,999,999,999,999,999,990.00') has_reward,count_batch, r.order_no,to_char(r.order_amount, 'fm99,999,999,999,999,999,990.00') order_amount, r.order_no,to_char(reserve, 'fm99,999,999,999,999,999,990.00') reserve,  prod_name, r.area, sum_reward, has_reward, (select l.prod_diffcoe from order_info o, lp_info l where o.order_no=r.order_no and o.part_comp=l.lp_id) prod_diffcoe,(SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and r.stateflag=t1.dict_value) stateflag,sales_name, r.sales_id, o.entry_time as order_time, s.quarter_deduction,s.quarter_index,return_coe  from reward_count_grant r left join order_info o on o.order_no=r.order_no left join sales_index s on s.sales_id = r.sales_id and o.entry_time < s.end_time and o.entry_time > s.start_time";
		int j = 0;
		if(area != null && !("").equals(area)) {
			sql += " where r.area like '%" + area + "%'";
			j++;
		}
		if (sales != null && !("").equals(sales)) {
			if (j > 0) {
				sql += " and r.sales_name like '%" + sales + "%' ";
			} else {
				sql += " where r.sales_name like '%" + sales + "%' ";
				j = j + 1;
			}
		}
		if (product != null && !("").equals(product)) {
			if (j > 0) {
				sql += " and r.prod_name like '%" + product + "%' ";
			} else {
				sql += " where  r.prod_name like '%" + product + "%' ";
			}
		}
		sql += "  offset ? limit ?";
		return jt.queryForList(sql, SkipSize, PageSize);
	}
	
	public List<Map<String, Object>> search_reward_all_count() {
		String sql = "";
		sql="select order_no from reward_count_grant";
		return jt.queryForList(sql);
	}
	
	// 查询奖金发放记录 全量
		public List<Map<String, Object>> search_rewardgive_list_all(long give_batch) {
			String sql = "";
			sql = "SELECT to_char(has_reward, 'fm99,999,999,999,999,999,990.00') has_reward,count_batch,order_no,"
					+ "to_char(order_amount, 'fm99,999,999,999,999,999,990.00') order_amount, "
					+ " order_no,to_char(reserve, 'fm99,999,999,999,999,999,990.00') reserve, "
					+ " prod_name , "
					+ "(SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and reward_count_grant.stateflag=t1.dict_value) stateflag , "
					+ "	 sales_name,sales_id "
					+ "	from reward_count_grant where give_batch=? ";
			return jt.queryForList(sql, give_batch);
		}

	// 查询预留计算历史记录 销售
	public List<Map<String, Object>> search_yuliu_list(long sales_id,
			long count_batch) {
		String sql = "";
	/*	sql = "SELECT sales_name,sales_id,count_batch,order_no, "
				+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reserve_state' and t1.dict_value='2') sign, "
				+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and t1.dict_value=reward_resrv_pool.stateflag) stateflag, "
				+ "		reserve,create_time from reward_resrv_pool  where sales_id=? and sign='2' and count_batch=? ";*/
		sql= "SELECT  "
		 +"	t1.sales_id,t1.sales_name,t1.create_time,t1.count_batch, "
		  +" (SELECT sum(t2.reserve) from reward_resrv_pool t2 where sign='0' "
		 +"	and SUBSTR(t2.create_time,1,4)=t1.create_time and t2.sales_id=t1.sales_id ) reserve, "
		 +"	 sum(case when t1.sign='1' then t1.reserve ELSE 0 end) reserve_kou, "
		 +"	 sum(case when t1.sign='2' then t1.reserve ELSE 0 end )reserve_give "
		 +" from reward_resrv_pool t1,"
		 +" (SELECT DISTINCT (create_time) from reward_resrv_pool where count_batch=? ) t2,  "
		  +" (SELECT DISTINCT (sales_id) from reward_resrv_pool where count_batch=? ) t3 "
		 +" where t1.create_time=t2.create_time and t1.sales_id=t3.sales_id and t3.sales_id=? and t1.count_batch=?   "
		 +" GROUP BY t1.sales_id,t1.sales_name,t1.create_time,t1.count_batch ";
		return jt.queryForList(sql, count_batch, count_batch,sales_id,count_batch);
	}

	// 查询预留计算历史记录 全量
	public List<Map<String, Object>> search_yuliu_list_all(long count_batch) {
		String sql = "";
		/*sql = "SELECT sales_name,sales_id,count_batch,order_no,"
				+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reserve_state' and t1.dict_value=reward_resrv_pool.sign) sign,"
				+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and t1.dict_value=reward_resrv_pool.stateflag) stateflag,  "
				+ "reserve,create_time from reward_resrv_pool  where  sign <> '0' and count_batch=? ";*/
		sql= "SELECT  "
		 +"	t1.sales_id,t1.sales_name,t1.create_time,t1.count_batch, "
		  +" (SELECT sum(t2.reserve) from reward_resrv_pool t2 where sign='0' "
		 +"	and SUBSTR(t2.create_time,1,4)=t1.create_time and t2.sales_id=t1.sales_id ) reserve, "
		 +"	 sum(case when t1.sign='1' then t1.reserve ELSE 0 end) reserve_kou, "
		 +"	 sum(case when t1.sign='2' then t1.reserve ELSE 0 end )reserve_give "
		 +" from reward_resrv_pool t1,"
		 +" (SELECT DISTINCT (create_time) from reward_resrv_pool where count_batch=? ) t2,  "
		  +" (SELECT DISTINCT (sales_id) from reward_resrv_pool where count_batch=? ) t3 "
		 +" where t1.create_time=t2.create_time and t1.sales_id=t3.sales_id and t1.count_batch=?  "
		 +" GROUP BY t1.sales_id,t1.sales_name,t1.create_time,t1.count_batch ";
		return jt.queryForList(sql, count_batch,count_batch,count_batch);
	}
	
	// 查询预留发放历史记录 全量
		public List<Map<String, Object>> search_yuliugive_list_all(long give_batch) {
			String sql = "";
			/*sql = "SELECT sales_name,sales_id,count_batch,order_no,"
					+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reserve_state' and t1.dict_value=reward_resrv_pool.sign) sign,"
					+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and t1.dict_value=reward_resrv_pool.stateflag) stateflag,  "
					+ "reserve,create_time from reward_resrv_pool  where  sign <> '0' and give_batch=? ";*/
			sql= "SELECT  "
					 +"	t1.sales_id,t1.sales_name,t1.create_time,t1.give_batch count_batch, "
					  +" (SELECT sum(t2.reserve) from reward_resrv_pool t2 where sign='0' "
					 +"	and SUBSTR(t2.create_time,1,4)=t1.create_time and t2.sales_id=t1.sales_id ) reserve, "
					 +"	 sum(case when t1.sign='1' then t1.reserve ELSE 0 end) reserve_kou, "
					 +"	 sum(case when t1.sign='2' then t1.reserve ELSE 0 end )reserve_give "
					 +" from reward_resrv_pool t1,"
					 +" (SELECT DISTINCT (create_time) from reward_resrv_pool where give_batch=? ) t2,  "
					  +" (SELECT DISTINCT (sales_id) from reward_resrv_pool where give_batch=? ) t3 "
					 +" where t1.create_time=t2.create_time and t1.sales_id=t3.sales_id and t1.give_batch=?  "
					 +" GROUP BY t1.sales_id,t1.sales_name,t1.create_time,t1.give_batch ";
			return jt.queryForList(sql, give_batch,give_batch,give_batch);
		}

	// 团队奖金历史查询-销售
	public List<Map<String, Object>> search_group_list(long sales_id,
			long count_batch) {
		String sql = "";
		sql = " SELECT group_id,name,reward_batch_id,order_no,order_version,sum_return,"
				+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and reward_group.stateflag=t1.dict_value)stateflag, "
				+ "reward_return from reward_group where sales_id=? and reward_batch_id=?  ";
		return jt.queryForList(sql, sales_id, count_batch);
	}

	// 团队奖金计算历史查询-全量
	public List<Map<String, Object>> search_group_list_all(long count_batch) {
		String sql = "";
		sql = "SELECT group_id,name,reward_batch_id,order_no,order_version,"
				+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and reward_group.stateflag=t1.dict_value)stateflag,"
				+ "sum_return,reward_return from reward_group where reward_batch_id=? ";
		return jt.queryForList(sql, count_batch);
	}
	
	// 团队奖金发放历史查询-全量
		public List<Map<String, Object>> search_groupgive_list_all(long give_batch) {
			String sql = "";
			sql = "SELECT group_id,name,reward_batch_id,order_no,order_version,"
					+ " (SELECT t1.dict_name from data_dict t1 where t1.dict_type='reward_state' and reward_group.stateflag=t1.dict_value)stateflag,"
					+ "sum_return,reward_return from reward_group where give_batch=? ";
			return jt.queryForList(sql, give_batch);
		}

	// 查询团队奖金批次-销售
	public List<Map<String, Object>> search_group_batch(long sales_id, int size) {
		String sql = "";
		sql = "SELECT DISTINCT(reward_batch_id) from  reward_group  where sales_id=? ORDER BY reward_batch_id LIMIT 3 OFFSET ? ";
		return jt.queryForList(sql, sales_id, size);
	}

	// 查询团队奖金批次-全量
	public List<Map<String, Object>> search_group_batch_all(int size) {
		String sql = "";
		sql = "SELECT DISTINCT(reward_batch_id) from  reward_group ORDER BY reward_batch_id LIMIT 3 OFFSET ? ";
		return jt.queryForList(sql, size);
	}
	
	// 查询团队奖金发放批次-全量
		public List<Map<String, Object>> search_group_givebatch_all(int size) {
			String sql = "";
			sql = "SELECT DISTINCT(give_batch) from  reward_group where stateflag='2' ORDER BY give_batch LIMIT 3 OFFSET ? ";
			return jt.queryForList(sql, size);
		}

	// 查询预留批次-销售
	public List<Map<String, Object>> search_yuliu_batch(long sales_id, int size) {
		String sql = "";
		sql = "SELECT DISTINCT(count_batch) from reward_resrv_pool where sign='2' and stateflag<>'-1' and sales_id=? ORDER BY count_batch LIMIT 3 OFFSET ? ";
		return jt.queryForList(sql, sales_id, size);
	}

	// 查询预留批次-全量
	public List<Map<String, Object>> search_yuliu_batch_all(int size) {
		String sql = "";
		sql = "SELECT DISTINCT(count_batch) from reward_resrv_pool where sign <> '0' and stateflag<>'-1'  ORDER BY count_batch LIMIT 3 OFFSET ? ";
		return jt.queryForList(sql, size);
	}
	// 查询预留发放批次-全量
	public List<Map<String, Object>> search_yuliu_givebatch_all(int size) {
		String sql = "";
		sql = "SELECT DISTINCT(give_batch) from reward_resrv_pool where sign = '2' and stateflag = '2'  ORDER BY give_batch LIMIT 3 OFFSET ? ";
		return jt.queryForList(sql, size);
	}

	// 查询个人奖金批次-销售
	public List<Map<String, Object>> search_reward_batch(long sales_id, int size) {
		String sql = "";
		sql = "SELECT DISTINCT(count_batch) count_batch from reward_count_grant where sales_id=? ORDER BY count_batch LIMIT 3 OFFSET ? ";
		return jt.queryForList(sql, sales_id, size);
	}
     
	// 查询个人奖金批次-全量
	public List<Map<String, Object>> search_reward_batch_all(int size) {
		String sql = "";
		sql = "SELECT DISTINCT(count_batch) from reward_count_grant ORDER BY count_batch LIMIT 3 OFFSET ? ";
		return jt.queryForList(sql, size);
	}
	
	// 查询个人奖金发放批次-全量
		public List<Map<String, Object>> search_reward_givebatch_all(int size) {
			String sql = "";
			sql = "SELECT DISTINCT(give_batch) from reward_count_grant where stateflag='2' ORDER BY give_batch LIMIT 3 OFFSET ? ";
			return jt.queryForList(sql, size);
		}

	// 查询团队指标年份
	public List<Map<String, Object>> search_group_year() {
		String sql = "SELECT DISTINCT(paeam_year) paeam_year from group_index ";

		return jt.queryForList(sql);
	}

	// 计算-个人奖金报表导出
	public SqlRowSet reward_count_report(long give_batch) {
		String sql = "SELECT sales_id,sales_name,order_amount,"
				+ "return_money,sum_reward,has_reward,reward_reserve,count_batch "
				+ " from reward_count_grant"
				+ " where stateflag='2' ";
		if(give_batch!=0){
			sql+=" and give_batch="+give_batch;
		}
		return jt.queryForRowSet(sql);
	}

	// 计算-预留报表导出
	public SqlRowSet reserve_count_report(long give_batch) {
		String  sql="";
		sql = "SELECT  "
				+ " t1.sales_id,t1.sales_name,t1.create_time, "
				+ " (SELECT sum(t2.reserve) from reward_resrv_pool t2 where sign='0' "
				+ "and SUBSTR(t2.create_time,1,4)=t1.create_time and t2.sales_id=t1.sales_id ), "
				+ " sum(case when t1.sign='1' then t1.reserve ELSE 0 end) reserve_kou, "
				+ " sum(case when t1.sign='2' then t1.reserve ELSE 0 end )reserve_give "
				+ " from reward_resrv_pool t1" 
				+ " where sign<>'0' ";
		if(give_batch!=0){
			/*sql+=" and give_batch="+give_batch;*/
		
		sql="SELECT  "
				+"	t1.sales_id,t1.sales_name,t1.create_time, "
			      +"   (SELECT sum(t2.reserve) from reward_resrv_pool t2 where sign='0' "
						+"	and SUBSTR(t2.create_time,1,4)=t1.create_time and t2.sales_id=t1.sales_id ), "
						+"	 sum(case when t1.sign='1' then t1.reserve ELSE 0 end) reserve_kou, "
						+"	 sum(case when t1.sign='2' then t1.reserve ELSE 0 end )reserve_give "
						+" from reward_resrv_pool t1,"
			+" (SELECT DISTINCT (create_time) from reward_resrv_pool where give_batch="+give_batch+" ) t2,  "
			+" (SELECT DISTINCT (sales_id) from reward_resrv_pool where give_batch="+give_batch+" ) t3 "
			+" where t1.create_time=t2.create_time and t1.sales_id=t3.sales_id   ";
		}
		sql+=" GROUP BY sales_id,sales_name,create_time ";
		return jt.queryForRowSet(sql);
	}

	// 计算-团队奖金报表导出
	public SqlRowSet group_count_report(long give_batch) {
		String sql = "SELECT  "
				+ " group_id,name,sales_name,order_no,order_version, "
				+ " sum_amount,sum_return,reward_return,reward_batch_id "
				+ " from reward_group  " 
				+ " where stateflag='2' ";
		if(give_batch!=0){
			sql+=" and give_batch="+give_batch;
		}
		
		return jt.queryForRowSet(sql);
	}
	public  double doubleToStr(double d) {
	    DecimalFormat format=new DecimalFormat("###0.00");
	    String str = "";
	    str = format.format(d);
	    double dReturn = Double.parseDouble(str);
	    return dReturn;
	  }
}