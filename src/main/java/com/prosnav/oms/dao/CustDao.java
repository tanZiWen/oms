package com.prosnav.oms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.util.jdbcUtil;
import com.prosnav.oms.util.mailCache;
import com.prosnav.oms.util.sendMail;

import sun.nio.fs.MacOSXFileSystemProvider;

public class CustDao {
		private  JdbcTemplate jt = (JdbcTemplate) jdbcUtil
	            .getBean("template");
		
		
		/**
		 * 客户列表查询(全量)
		 * @return
		 */
		public List<Map<String ,Object>> inquiryAll(int m,int n){
			String sql ="select  distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell,"
					+ "t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,"
					+ "t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,"
					+ "t2.sales_name,t2.sales_area,t2.cust_belong_state,"
					+ "t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,"
					+ "t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, "
					+ "t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type, "
					+ "t7.dict_name dist_name,t7.dict_value dist_value,t7.dict_type dist_type "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' "
					+ "order by t1.cust_id  "
					+ "limit ? OFFSET ?";
			List<Map<String ,Object>> list = jt.queryForList(sql,n,m);
			return list;
		}
	
		
		/**
		 * 客户列表查询（销售个人）
		 * @return
		 */
		public List<Map<String ,Object>> inquiry_sale(int m,int n,long user_id){
			String sql ="select  distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell,"
					+ "t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,"
					+ "t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,"
					+ "t2.sales_name,t2.sales_area,t2.cust_belong_state,"
					+ "t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,"
					+ "t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, "
					+ "t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type, "
					+ "t7.dict_name dist_name,t7.dict_value dist_value,t7.dict_type dist_type "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' "
					+ "where t6.user_id = ?"
					+ "order by t1.cust_id  "
					+ "limit ? OFFSET ?";
			List<Map<String ,Object>> list = jt.queryForList(sql,user_id,n,m);
			return list;
		}
		
		/**
		 * 客户列表查询（团队长及其队员）
		 * @return
		 */
		public List<Map<String ,Object>> inquiry_leader(int m,int n,long user_id){
			String sql ="select  distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell,"
					+ "t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,"
					+ "t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,"
					+ "t2.sales_id,t2.sales_name,t2.sales_area,t2.cust_belong_state,"
					+ "t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,"
					+ "t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, "
					+ "t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type,  "
					+ "t7.dict_name dist_name,t7.dict_value dist_value,t7.dict_type dist_type "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' "
					+ "where t2.sales_id  in(select a.user_id from upm_user a,"
					+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
					+ "where a.workgroup_id=b.workgroup_id)"
					+ "order by t1.cust_id  "
					+ "limit ? OFFSET ?";
			List<Map<String ,Object>> list = jt.queryForList(sql,user_id,n,m);
			return list;
		}
		/**
		 * 客户列表查询(大区领导)
		 * @return
		 */
		public List<Map<String ,Object>> inquiry(int m,int n,long user_id){
			String sql ="select  distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell,"
					+ "t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,"
					+ "t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,"
					+ "t2.sales_name,t2.sales_area,t2.cust_belong_state,t2.sales_id,"
					+ "t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,"
					+ "t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, "
					+ "t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type,  "
					+ "t7.dict_name dist_name,t7.dict_value dist_value,t7.dict_type dist_type "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' "
					+ "where t2.sales_id in(select a.user_id from upm_user a,"
					+ "(select user_id,org_code from upm_user where user_id=?) b  "
					+ "where a.org_code=b.org_code) "
					+ "order by t1.cust_id  "
					+ "limit ? OFFSET ?";
			List<Map<String ,Object>> list = jt.queryForList(sql,user_id,n,m);
			return list;
		}
		
		/**
		 * 客户分配页面 列表查询
		 * @return
		 */
		public List<Map<String ,Object>> distribution_inquiry(int limit, int offset, String userArea){
			String sql ="select distinct b.cust_id, a.real_name, c.cust_belong_state, b.cust_name from upm_user a, "
					+ "cust_info b, sales_cust_rel c "
					+ "where a.user_id = c.sales_id and b.cust_id = c.cust_id and a.area = ?"
					+ "limit ? OFFSET ?";
			List<Map<String ,Object>> list = jt.queryForList(sql, userArea,limit,offset);
			return list;
		}
		
		/**
		 * 客户分配页面 信息列表总数查询
		 * @return
		 */
		public List<Map<String, Object>> distribution_inquiryCount(String user_area){
			String sql ="select count(*) from sales_cust_rel c, upm_user u, cust_info s where u.user_id = c.sales_id "
					+ "and s.cust_id = c.cust_id and u.area = ?";
			return jt.queryForList(sql, user_area);
		}
		
		/**
		 * 客户分配页面 客户名称等查询刷新列表
		 * @param customerName
		 * @return
		 */
		public List<Map<String,Object>> distribution_cust_select(String userArea, String state,String customerName,String customerMobile,String salesName,String customerLevel,int offset,int limit){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct b.cust_id, a.real_name, c.cust_belong_state, b.cust_name from upm_user a, "
					+ "cust_info b, sales_cust_rel c "
					+ "where a.user_id = c.sales_id and b.cust_id = c.cust_id");
			if (!StringUtils.isBlank(salesName)) {
		    sb.append(" and c.sales_name like ? ");
		    argsList.add("%"+salesName+"%");
		    }
			if("0".equals(state) || "1".equals(state)) {
				sb.append(" and c.cust_belong_state = ?");
				argsList.add(state);
			}
			if (!StringUtils.isBlank(customerName)) {
			  sb.append(" and b.cust_name like ? ");
			  argsList.add("%"+customerName+"%");
		    }
			
			if (!StringUtils.isBlank(customerMobile)) {
			  sb.append(" and b.cust_cell like ? ");
			  argsList.add("%"+customerMobile+"%");
		    }
		    
		    if("1".equals(customerLevel) || "2".equals(customerLevel)) {
				sb.append(" and b.cust_level = ?");
				argsList.add(customerLevel);
			}
		    sb.append(" and a.area = ?");
		    argsList.add(userArea);
			sb.append(" limit "+limit+" OFFSET "+offset+" ");
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForList(sb.toString(), parms);
		}
		/**
		 * 客户分配页面 客户名称等查询分页刷新列表数量
		 * @param cust_name
		 * @return
		 */
		public int distribution_cust_select1(String userArea, String state,String customerName,String customerMobile,String salesName,String customerLevel){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) from sales_cust_rel c, upm_user a, cust_info b where a.user_id = c.sales_id "
					+ "and b.cust_id = c.cust_id");
			if (!StringUtils.isBlank(salesName)) {
			    sb.append(" and c.sales_name like ? ");
			    argsList.add("%"+salesName+"%");
			}
			
			if("0".equals(state) || "1".equals(state)) {
				sb.append(" and c.cust_belong_state = ?");
				argsList.add(state);
			}
			
			if (!StringUtils.isBlank(customerName)) {
				sb.append(" and b.cust_name like ? ");
				argsList.add("%"+customerName+"%");
			}
				
			if (!StringUtils.isBlank(customerMobile)) {
				sb.append(" and b.cust_cell like ? ");
				argsList.add("%"+customerMobile+"%");
			}
			    
			if("1".equals(customerLevel) || "2".equals(customerLevel)) {
				sb.append(" and b.cust_level = ?");
				argsList.add(customerLevel);
			}
			    sb.append(" and a.area = ?");
			    argsList.add(userArea);
			
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForInt(sb.toString(), parms);
		}
		
		/**
		 * 客户分配页面 查询所在分公司所有销售
		 * @return
		 */
		public List<Map<String ,Object>> transferSalesSelect(String userArea){
			String sql ="select a.real_name, a.user_id from upm_user a where a.role_codes && Array "
					+ "['sale_self','sale_team_manage','sale_company_manage']::character varying[] "
					+ "and a.status = 'ok' "
					+ "and a.area = ?";
			List<Map<String ,Object>> list = jt.queryForList(sql, userArea);
			return list;
		}
		
		/**
		 * 重新分配客户
		 * @param cust_id
		 * @return
		 */
		public void transferSales(long cust_id, long sales_id) {
			String sql = "UPDATE sales_cust_rel "
					+ "SET sales_id = ?, cust_belong_state = '1' WHERE cust_id = ?";
			jt.update(sql, sales_id, cust_id);
			System.out.println("cust_id:" + cust_id + " sales_id:" + sales_id);
		}
		
		/**
		 * 报表导出excel(销售个人)
		 * @return
		 */
		public SqlRowSet cust_report_sale(long user_id){
			String sql = "select  distinct row_number() OVER () as rownum, t7.dict_name dist_name,t1.cust_name,t5.dict_name sex_name,t1.cust_cell,"
					+ "t2.sales_name,t4.dict_name level_name,t3.dict_name state_name,t8.dict_name idtype_name,t1.cust_idnum,"
					+ "t1.cust_birth,t1.city,t1.email,t1.wechat,t1.qq,t1.address,t1.company,t1.profession,t1.cust_reg_time  "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' "
					+ "left join data_dict t8 on t8.dict_value = t1.cust_idtype and t8.dict_type = 'idtype' "
					+ "where t6.user_id = "+user_id+""
					+ "order by rownum";
			return jt.queryForRowSet(sql);
		}
		/**
		 * 报表导出excel(团队)
		 * @return
		 */
		public SqlRowSet cust_report_team(long user_id){
			String sql = "select  distinct row_number() OVER () as rownum, t7.dict_name dist_name,t1.cust_name,t5.dict_name sex_name,t1.cust_cell,"
					+ "t2.sales_name,t4.dict_name level_name,t3.dict_name state_name,t8.dict_name idtype_name,t1.cust_idnum,"
					+ "t1.cust_birth,t1.city,t1.email,t1.wechat,t1.qq,t1.address,t1.company,t1.profession,t1.cust_reg_time  "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' "
					+ "left join data_dict t8 on t8.dict_value = t1.cust_idtype and t8.dict_type = 'idtype' "
					+ "where t2.sales_id  in(select a.user_id from upm_user a,"
					+ "(select user_id,workgroup_id from upm_user where user_id="+user_id+") b  "
					+ "where a.workgroup_id=b.workgroup_id)  "
					+ "order by rownum";
			return jt.queryForRowSet(sql);
		}
		/**
		 * 报表导出excel(分公司领导)
		 * @return
		 */
		public SqlRowSet cust_report_leader(long user_id){
			String sql = "select  distinct row_number() OVER () as rownum, t7.dict_name dist_name,t1.cust_name,t5.dict_name sex_name,t1.cust_cell,"
					+ "t2.sales_name,t4.dict_name level_name,t3.dict_name state_name,t8.dict_name idtype_name,t1.cust_idnum,"
					+ "t1.cust_birth,t1.city,t1.email,t1.wechat,t1.qq,t1.address,t1.company,t1.profession,t1.cust_reg_time  "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' "
					+ "left join data_dict t8 on t8.dict_value = t1.cust_idtype and t8.dict_type = 'idtype' "
					+ "where t2.sales_id in(select a.user_id from upm_user a,"
					+ "(select user_id,org_code from upm_user where user_id="+user_id+") b  "
					+ "where a.org_code=b.org_code) "
					+ "order by rownum";
			return jt.queryForRowSet(sql);
		}
		/**
		 * 报表导出excel(全量)
		 * @return
		 */
		public SqlRowSet cust_report(){
			String sql = "select  distinct row_number() OVER () as rownum, t7.dict_name dist_name,t1.cust_name,t5.dict_name sex_name,t1.cust_cell,"
					+ "t2.sales_name,t4.dict_name level_name,t3.dict_name state_name,t8.dict_name idtype_name,t1.cust_idnum,"
					+ "t1.cust_birth,t1.city,t1.email,t1.wechat,t1.qq,t1.address,t1.company,t1.profession,t1.cust_reg_time  "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' "
					+ "left join data_dict t8 on t8.dict_value = t1.cust_idtype and t8.dict_type = 'idtype' "
					+ "order by rownum";
			return jt.queryForRowSet(sql);
		}
		
		//拜访记录查询
		public List<Map<String,Object>> detail(long id){
			String sql = "SELECT t1.cust_id,t3.see_id,t3.cust_id,t3.see_date,t3.see_member,t3.see_desc,t3.email_id "
					+ "FROM cust_info t1,cust_see_info t3 where t1.cust_id=t3.cust_id and t1.cust_id=?";
			
			return jt.queryForList(sql,id);
		}
		//个人信息详情展示
		public List<Map<String,Object>> detaillist(long id){
			String sql = "select distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell,"
					+ "t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,"
					+ "t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,"
					+ "t2.sales_name,t2.sales_area,t2.cust_belong_state,t2.sales_id,"
					+ "t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,"
					+ "t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, "
					+ "t6.dict_name idtype_name,t6.dict_value idtype_value,t6.dict_type idtype_type, "
					+ "t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type "
					+ "from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id  and t2.cust_belong_state='1' "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "left join data_dict t6 on t6.dict_value = t1.cust_idtype and t6.dict_type = 'idtype' "
					+ "where t1.cust_id=?";
			
			return jt.queryForList(sql,id);
		}
		//个人成交记录列表显示
				public List<Map<String,Object>> detailOrderlist(long id){
					String sql = "SELECT t2.area, t2.part_comp, t2.contract_type, t2.contract_no, t2.is_checked, t2.stateflag, "
							+ "to_char(coalesce(t2.order_amount,0), 'fm99,999,999,999,999,999,990.00') order_amount, t2.create_time,t2.prod_diffcoe, "
							+ "t2.order_version, t2.order_type, t2.order_no, t2.cust_no, t2.prod_no, t2.email_id,"
							+ "t3.sales_point, t3.stateflag, t3.order_no, t3.sales_id, to_char(coalesce(t3.magt_fee,0), 'fm99,999,999,999,999,999,990.00') mag_fee, t3.sales_name, "
							+ "t3.order_version, t3.email_id,t3.magt_fee, t3.cost_model,t4.prod_id, t4.prod_name, t4.prod_type,t8.partner_com_name,"
							+ "t1.cust_id, t1.cust_name, t1.cust_idnum, t1.cust_idtype, t1.state, t1.cust_cell, t1.cust_risk,"
							+ "t5.dict_name as order_state_name, t6.dict_name as area_name, t7.dict_name as prod_type_name  "
							+ "FROM public.order_info t2  "
							+ "inner JOIN public.sale_order t3 on t2.order_no = t3.order_no  "
							+ "LEFT JOIN public.product_info t4 on t2.prod_no = t4.prod_id  "
							+ "LEFT JOIN lp_info t8 on t2.part_comp = t8.lp_id  "
							+ "LEFT JOIN public.cust_info t1 on t2.cust_no = t1.cust_id  "
							+ "LEFT JOIN data_dict t5 on t5.dict_type = 'ord_flag' and t5.dict_value = t2.is_checked  "
							+ "LEFT JOIN data_dict t6 on t6.dict_type = 'dist' and t6.dict_value = t2.area  "
							+ "LEFT JOIN data_dict t7 on t7.dict_type = 'prodType' and t7.dict_value = t4.prod_type  "
							+ "where t2.is_checked = '2' and t1.cust_id=?";
					
					return jt.queryForList(sql,id);
				}
		//个人公司信息详情展示
		public List<Map<String,Object>> detail_com(long id){
			String sql = "SELECT t1.comp_id,t1.comp_name,t1.comp_type,t1.license,t1.legal,t1.taxid,t1.org_code_cert,"
					+ "to_char(coalesce(t1.reg_capital,0), 'fm99,999,999,999,999,999,990.00')money,"
					+ "t1.reg_address,t1.reg_date,t1.opera_period,t1.state,t2.cust_id,t2.cust_name,t3.comp_id,t3.cust_id  "
					+ "FROM comp_info t1,cust_info t2,cust_comp_rel t3 "
					+ "where t1.comp_id=t3.comp_id and t2.cust_id=t3.cust_id and t2.cust_id=? "
					+ "order by t1.comp_id";
			
			return jt.queryForList(sql,id);
		}
		//个人公司列表
		public List<Map<String,Object>> detail_com1(long id){
			String sql = "SELECT t1.comp_id,t1.comp_name,t2.cust_id "
					+ "FROM comp_info t1,cust_info t2,cust_comp_rel t3 where t1.comp_id=t3.comp_id and t2.cust_id=t3.cust_id and t2.cust_id=?"
					+ " order by t1.comp_id";
			
			return jt.queryForList(sql,id);
		}
		//个人公司列表公司id获取
				public List<Map<String,Object>> detail_com2(long id){
					String sql = "SELECT t1.comp_id "
							+ "FROM cust_comp_rel t1 where t1.cust_id=?"
							+ " order by t1.comp_id";
					
					return jt.queryForList(sql,id);
				}
		/**
		 * 个人公司列表详情，异步刷新
		 * @param id
		 * @return
		 */
		public List<Map<String,Object>> compList(long cust_id,long comp_id){
			String sql = "SELECT t1.comp_id,t1.comp_name,t1.comp_type,t1.license,t1.legal,t1.taxid,t1.org_code_cert,"
					+ "to_char(coalesce(t1.reg_capital,0), 'fm99,999,999,999,999,999,990.00')money,t1.reg_address,"
					+ "t1.reg_date,t1.opera_period,t1.state,t3.email_id,t2.cust_id,t2.cust_name,t3.comp_id,t3.cust_id "
					+ "FROM comp_info t1,cust_info t2,cust_comp_rel t3 where t1.comp_id=t3.comp_id and t2.cust_id=t3.cust_id and t2.cust_id=? and t1.comp_id=?";
			
			return jt.queryForList(sql,cust_id,comp_id);
		}
		
		//个人家族成员详情展示
		public List<Map<String,Object>> detail_fam(long id){
			String sql = "SELECT  t2.family_id, t2.family_name, t2.family_cust_host_id, t2.family_cust_name, "
					+ "t2.family_cust_desc, t2.family_mem_num, t2.recorder, t2.reg_time, t2.remark, t2.family_cust_level,"
					+ "t3.family_cust_name, t3.relation, t3.family_id, t3.cust_id, t3.email_id, t3.cust_reg_time, "
					+ "t3.cust_name, t3.cust_sex, t3.cust_cell, t3.cust_birth, t3.cust_idtype, t3.cust_idnum, t3.city, "
					+ "t3.email, t3.wechat, t3.qq, t3.address, t3.company, t3.id_address, t3.profession, "
					+ "t3.see_date, t3.see_member, t3.see_desc, t3.member_id, t3.state, t3.cust_level,"
					+ "t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, "
					+ "t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type,"
					+ "t6.dict_name state_name,t6.dict_value state_value,t6.dict_type state_type,"
					+ "t7.dict_name idtype_name,t7.dict_value idtype_value,t7.dict_type idtype_type,  "
					+ "t8.dict_name family_state_name,t8.dict_value family_state_value,t8.dict_type family_state_type  "
					+ "FROM family_info t2  "
					+ "inner join family_member_rel t3 on t2.family_id=t3.family_id  "
					+ "left join data_dict t4 on t4.dict_value = t3.cust_level and t4.dict_type = 'level'  "
					+ "left join data_dict t5 on t5.dict_value = t3.cust_sex and t5.dict_type = 'sex'  "
					+ "left join data_dict t6 on t6.dict_value = t3.state and t6.dict_type = 'state'  "
					+ "left join data_dict t7 on t7.dict_value = t3.cust_idtype and t7.dict_type = 'idtype'  "
					+ "left join data_dict t8 on t8.dict_value = t2.family_cust_level and t8.dict_type = 'family_state'  "
					+ "where  t2.family_cust_host_id=?  "
					+ "ORDER BY t3.member_id";
			
			return jt.queryForList(sql,id);
		}
		//个人家族成员列表展示
		public List<Map<String,Object>> detail_fam1(long id){
			String sql = "SELECT t1.family_id, t1.family_name, t1.family_cust_host_id, t1.reg_time,"
					+ "t2.family_cust_name, t2.relation, t2.family_id, t2.cust_id, t2.member_id, t2.cust_name "
					+ " FROM family_info  t1,family_member_rel t2  "
					+ " where t1.family_id=t2.family_id and t1.family_cust_host_id=?"
					+ "  order by t2.member_id";
			
			return jt.queryForList(sql,id);
		}
		
		//个人家族列表 ，成员id获取
		public List<Map<String,Object>> detail_fam2(long id){
			String sql = "SELECT t1.family_id, t1.family_cust_host_id, t2.family_id, t2.cust_id, t2.cust_name, t2.member_id  "
					+ "FROM  family_info t1,family_member_rel t2  "
					+ "where t1.family_id=t2.family_id  and t1.family_cust_host_id=? "
					+ " ORDER BY t2.member_id";
			
			return jt.queryForList(sql,id);
		}
		
		//个人家族成员列表异步刷新
				public List<Map<String,Object>> show_detail(long family_id,long member_id){
					String sql = "SELECT t2.family_id,t2.family_name,t2.family_cust_name,t2.family_cust_desc,t2.family_mem_num,t2.reg_time,t2.family_cust_level,"
							+ "t3.family_cust_name, t3.relation, t3.family_id, t3.cust_id, t3.email_id, t3.member_id, t3.cust_reg_time, t3.cust_name, t3.cust_sex, t3.cust_cell, "
							+ "t3.cust_birth, t3.cust_idtype, t3.cust_idnum, t3.city, t3.email, t3.wechat, t3.qq, t3.address, t3.company, t3.id_address, t3.profession, t3.see_date, "
							+ "t3.see_member, t3.see_desc, "
							+ "t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, "
							+ "t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type,"
							+ "t6.dict_name state_name,t6.dict_value state_value,t6.dict_type state_type,"
							+ "t7.dict_name idtype_name,t7.dict_value idtype_value,t7.dict_type idtype_type "
							+ "FROM  family_info t2  "
							+ "inner join family_member_rel t3 on t2.family_id = t3.family_id  "
							+ "left join data_dict t4 on t4.dict_value = t3.cust_level and t4.dict_type = 'level'  "
							+ "left join data_dict t5 on t5.dict_value = t3.cust_sex and t5.dict_type = 'sex'  "
							+ "left join data_dict t6 on t6.dict_value = t3.state and t6.dict_type = 'state'  "
							+ "left join data_dict t7 on t7.dict_value = t3.cust_idtype and t7.dict_type = 'idtype'  "
							+ "where  t2.family_id =t3.family_id and t2.family_id=? and t3.member_id = ?"
							+ "  order by t3.member_id";
					
					return jt.queryForList(sql,family_id,member_id);
				}
				
		/**
		 * 客户信息列表总数查询(全量)
		 * @return
		 */
		public List<Map<String, Object>> inquiryCountAll(){
			String sql ="select count(*) from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ";
			return jt.queryForList(sql);
		}
		/**
		 * 客户信息列表总数查询(大区领导)
		 * @return
		 */
		public List<Map<String, Object>> inquiryCount(long user_id){
			String sql ="select count(*) from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id  "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "where t2.sales_id in(select a.user_id from upm_user a,"
					+ "(select user_id,org_code from upm_user where user_id=?) b  "
					+ "where a.org_code=b.org_code) ";
			return jt.queryForList(sql,user_id);
		}
		/**
		 * 客户信息列表总数查询(（团队长及其队员）)
		 * @return
		 */
		public List<Map<String, Object>> inquiry_leaderCount(long user_id){
			String sql ="select count(*) from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id  "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "where t2.sales_id  in(select a.user_id from upm_user a,"
					+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
					+ "where a.workgroup_id=b.workgroup_id)  ";
			return jt.queryForList(sql,user_id);
		}
		/**
		 * 客户信息列表总数查询(（销售本人）)
		 * @return
		 */
		public List<Map<String, Object>> inquiry_saleCount(long user_id){
			String sql ="select count(*) from cust_info t1 "
					+ "inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' "
					+ "left join upm_user t6 on t2.sales_id=t6.user_id  "
					+ "left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' "
					+ "left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' "
					+ "left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' "
					+ "where t6.user_id = ?";
			return jt.queryForList(sql,user_id);
		}
		/**
		 * 查询客户名称等刷新列表(销售个人)
		 * @param cust_name
		 * @return
		 */
		public List<Map<String,Object>> sale_cust_select(String state,String cust_name,String cust_cell, long user_id, String sales_name,String cust_level,int m,int n){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell, ");
			sb.append(" t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,");
			sb.append(" t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,");
			sb.append(" t2.sales_name,t2.sales_area,t2.cust_belong_state,");
			sb.append(" t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,");
			sb.append(" t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, ");
			sb.append(" t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type,t7.dict_name dist_name ");
			sb.append(" from cust_info t1 ");
			sb.append(" inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' ");
			if (!"".equals(sales_name) && sales_name != null) {
		    sb.append(" and  t2.sales_name like ? ");
		    argsList.add("%"+sales_name+"%");
		    }
			sb.append(" left join upm_user t6 on t2.sales_id=t6.user_id ");
			sb.append(" left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' ");
			sb.append(" left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' ");
			sb.append(" left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ");
			sb.append("left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' ");
			sb.append(" where t6.user_id = "+user_id+" ");
			if (!"".equals(state) && state != null && state != "0" && !state.equals("0")) {
			  sb.append(" and t3.dict_value = ? ");
			  argsList.add(state);
		     }
			if (!"".equals(cust_name) && cust_name != null) {
			  sb.append(" and t1.cust_name like ? ");
			  argsList.add("%"+cust_name+"%");
		    }
		    if (!"".equals(cust_cell) && cust_cell != null) {
			  sb.append(" and t1.cust_cell like ? ");
			  argsList.add("%"+cust_cell+"%");
		    }
		    if (!"".equals(cust_level) && cust_level != null) {
			  sb.append(" and t4.dict_value = ? ");
			  argsList.add(cust_level);
			    }
			sb.append(" order by t1.cust_id ");
			sb.append(" limit "+n+" OFFSET "+m+" ");
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForList(sb.toString(), parms);
		}
		/**
		 * 分页查询客户名称等刷新列表(销售个人)
		 * @param cust_name
		 * @return
		 */
		public int sale_cust_select1(String state,String cust_name,String cust_cell,long user_id,String sales_name,String cust_level){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select  count(*) from cust_info t1 ");
			sb.append(" inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' ");
			if (!"".equals(sales_name) && sales_name != null) {
		    sb.append(" and  t2.sales_name like ? ");
		    argsList.add("%"+sales_name+"%");
		    }
			sb.append(" left join upm_user t6 on t2.sales_id=t6.user_id ");
			sb.append(" left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' ");
			sb.append(" left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' ");
			sb.append(" left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ");
			sb.append(" where t6.user_id = "+user_id+" ");
			if (!"".equals(state) && state != null && state != "0" && !state.equals("0")) {
			  sb.append(" and t3.dict_value = ? ");
			  argsList.add(state);
		     }
			if (!"".equals(cust_name) && cust_name != null) {
			  sb.append(" and t1.cust_name like ? ");
			  argsList.add("%"+cust_name+"%");
		    }
		    if (!"".equals(cust_cell) && cust_cell != null) {
			  sb.append(" and t1.cust_cell like ? ");
			  argsList.add("%"+cust_cell+"%");
		    }
		    if (!"".equals(cust_level) && cust_level != null) {
			  sb.append(" and t4.dict_value = ? ");
			  argsList.add(cust_level);
			 }
			
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForInt(sb.toString(), parms);
		}
		
		/**
		 * 查询客户名称等刷新列表(团队长及其队员)
		 * @param cust_name
		 * @return
		 */
		public List<Map<String,Object>> team_cust_select(long user_id, String state,String cust_name,String cust_cell,String sales_name,String cust_level,int m,int n){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell, ");
			sb.append(" t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,");
			sb.append(" t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,");
			sb.append(" t2.sales_name,t2.sales_area,t2.cust_belong_state,t2.sales_id,");
			sb.append(" t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,");
			sb.append(" t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, ");
			sb.append(" t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type,t7.dict_name dist_name ");
			sb.append(" from cust_info t1 ");
			sb.append(" inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' ");
			if (!"".equals(sales_name) && sales_name != null) {
		    sb.append(" and  t2.sales_name like ? ");
		    argsList.add("%"+sales_name+"%");
		    }
			sb.append(" left join upm_user t6 on t2.sales_id=t6.user_id ");
			sb.append(" left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' ");
			sb.append(" left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' ");
			sb.append(" left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ");
			sb.append("left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' ");
			sb.append(" where t2.sales_id  in(select a.user_id from upm_user a,(select user_id,workgroup_id from upm_user where user_id="+user_id+") b  where a.workgroup_id=b.workgroup_id)  ");
			if (!"".equals(state) && state != null && state != "0" && !state.equals("0")) {
				  sb.append(" and t3.dict_value = ? ");
				  argsList.add(state);
			    }	
			if (!"".equals(cust_name) && cust_name != null) {
			  sb.append(" and t1.cust_name like ? ");
			  argsList.add("%"+cust_name+"%");
		    }
		    if (!"".equals(cust_cell) && cust_cell != null) {
			  sb.append(" and t1.cust_cell like ? ");
			  argsList.add("%"+cust_cell+"%");
		    }
		    if (!"".equals(cust_level) && cust_level != null) {
				  sb.append(" and t4.dict_value = ? ");
				  argsList.add(cust_level);
			    }
			sb.append(" order by t1.cust_id ");
			sb.append(" limit "+n+" OFFSET "+m+" ");
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForList(sb.toString(), parms);
		}
		/**
		 * 分页查询客户名称等刷新列表(团队长及其队员)
		 * @param cust_name
		 * @return
		 */
		public int team_cust_select1(long user_id, String state,String cust_name,String cust_cell,String sales_name,String cust_level){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select  count(*) from cust_info t1 ");
			sb.append(" inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' ");
			if (!"".equals(sales_name) && sales_name != null) {
		    sb.append(" and  t2.sales_name like ? ");
		    argsList.add("%"+sales_name+"%");
		    }
			sb.append(" left join upm_user t6 on t2.sales_id=t6.user_id ");
			sb.append(" left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' ");
			sb.append(" left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' ");
			sb.append(" left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ");
			sb.append(" where t2.sales_id  in(select a.user_id from upm_user a,(select user_id,workgroup_id from upm_user where user_id="+user_id+") b  where a.workgroup_id=b.workgroup_id)  ");
			if (!"".equals(state) && state != null && state != "0" && !state.equals("0")) {
				  sb.append(" and t3.dict_value = ? ");
				  argsList.add(state);
			    }	
			if (!"".equals(cust_name) && cust_name != null) {
			  sb.append(" and t1.cust_name like ? ");
			  argsList.add("%"+cust_name+"%");
		    }
		    if (!"".equals(cust_cell) && cust_cell != null) {
			  sb.append(" and t1.cust_cell like ? ");
			  argsList.add("%"+cust_cell+"%");
		    }
		    if (!"".equals(cust_level) && cust_level != null) {
				  sb.append(" and t4.dict_value = ? ");
				  argsList.add(cust_level);
			 }
			
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForInt(sb.toString(), parms);
		}
		/**
		 * 查询客户名称等刷新列表(大区领导)
		 * @param cust_name
		 * @return
		 */
		public List<Map<String,Object>> leader_cust_select(long user_id, String state,String cust_name,String cust_cell,String sales_name,String cust_level,int m,int n){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell, ");
			sb.append(" t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,");
			sb.append(" t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,");
			sb.append(" t2.sales_name,t2.sales_area,t2.cust_belong_state,t2.sales_id,");
			sb.append(" t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,");
			sb.append(" t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, ");
			sb.append(" t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type,t7.dict_name dist_name ");
			sb.append(" from cust_info t1 ");
			sb.append(" inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' ");
			if (!"".equals(sales_name) && sales_name != null) {
		    sb.append(" and  t2.sales_name like ? ");
		    argsList.add("%"+sales_name+"%");
		    }
			sb.append(" left join upm_user t6 on t2.sales_id=t6.user_id ");
			sb.append(" left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' ");
			sb.append(" left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' ");
			sb.append(" left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ");
			sb.append("left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' ");
			sb.append(" where t2.sales_id in(select a.user_id from upm_user a,(select user_id,org_code from upm_user where user_id="+user_id+") b  where a.org_code=b.org_code)  ");
			if (!"".equals(state) && state != null && state != "0" && !state.equals("0")) {
				  sb.append(" and t3.dict_value = ? ");
				  argsList.add(state);
			    }	
			if (!"".equals(cust_name) && cust_name != null) {
			  sb.append(" and t1.cust_name like ? ");
			  argsList.add("%"+cust_name+"%");
		    }
		    if (!"".equals(cust_cell) && cust_cell != null) {
			  sb.append(" and t1.cust_cell like ? ");
			  argsList.add("%"+cust_cell+"%");
		    }
		    if (!"".equals(cust_level) && cust_level != null) {
				  sb.append(" and t4.dict_value = ? ");
				  argsList.add(cust_level);
			    }
			sb.append(" order by t1.cust_id ");
			sb.append(" limit "+n+" OFFSET "+m+" ");
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForList(sb.toString(), parms);
		}
		/**
		 * 分页查询客户名称等刷新列表(大区领导)
		 * @param cust_name
		 * @return
		 */
		public int leader_cust_select1(long user_id, String state,String cust_name,String cust_cell,String sales_name,String cust_level){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select  count(*) from cust_info t1 ");
			sb.append(" inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' ");
			if (!"".equals(sales_name) && sales_name != null) {
		    sb.append(" and  t2.sales_name like ? ");
		    argsList.add("%"+sales_name+"%");
		    }
			sb.append(" left join upm_user t6 on t2.sales_id=t6.user_id ");
			sb.append(" left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' ");
			sb.append(" left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' ");
			sb.append(" left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ");
			sb.append(" where t2.sales_id in(select a.user_id from upm_user a,(select user_id,org_code from upm_user where user_id="+user_id+") b  where a.org_code=b.org_code)  ");
			if (!"".equals(state) && state != null && state != "0" && !state.equals("0")) {
				  sb.append(" and t3.dict_value = ? ");
				  argsList.add(state);
			    }	
			if (!"".equals(cust_name) && cust_name != null) {
			  sb.append(" and t1.cust_name like ? ");
			  argsList.add("%"+cust_name+"%");
		    }
		    if (!"".equals(cust_cell) && cust_cell != null) {
			  sb.append(" and t1.cust_cell like ? ");
			  argsList.add("%"+cust_cell+"%");
		    }
		    if (!"".equals(cust_level) && cust_level != null) {
				  sb.append(" and t4.dict_value = ? ");
				  argsList.add(cust_level);
			    }
			
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForInt(sb.toString(), parms);
		}
		
		/**
		 * 查询客户名称等刷新列表(全量)
		 * @param cust_name
		 * @return
		 */
		public List<Map<String,Object>> all_cust_select(String state,String cust_name,String cust_cell,String sales_name,String cust_level,int m,int n){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct t1.cust_id,t1.cust_reg_time,t1.cust_name,t1.cust_sex,t1.cust_cell, ");
			sb.append(" t1.cust_birth,t1.cust_level,t1.cust_idnum,t1.cust_idtype,t1.city,t1.email,t1.wechat,t1.qq,");
			sb.append(" t1.address,t1.company,t1.id_address,t1.profession,t1.state,t2.email_id,");
			sb.append(" t2.sales_name,t2.sales_area,t2.cust_belong_state,");
			sb.append(" t3.dict_name state_name,t3.dict_value state_value,t3.dict_type state_type,");
			sb.append(" t4.dict_name level_name,t4.dict_value level_value,t4.dict_type level_type, ");
			sb.append(" t5.dict_name sex_name,t5.dict_value sex_value,t5.dict_type sex_type,t7.dict_name dist_name ");
			sb.append(" from cust_info t1 ");
			sb.append(" inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' ");
			if (!"".equals(sales_name) && sales_name != null) {
		      sb.append(" and  t2.sales_name like ? ");
		      argsList.add("%"+sales_name+"%");
		    }
			sb.append(" left join upm_user t6 on t2.sales_id=t6.user_id ");
			sb.append(" left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' ");
			sb.append(" left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' ");
			sb.append(" left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ");
			sb.append("left join data_dict t7 on t7.dict_value = t6.area and t7.dict_type = 'dist' ");
			sb.append(" where 1=1 ");
			if (!"".equals(state) && state != null && state != "0" && !state.equals("0")) {
			  sb.append(" and t3.dict_value = ? ");
			  argsList.add(state);
			 }
		    if (!"".equals(cust_name) && cust_name != null) {
			  sb.append(" and t1.cust_name like ? ");
			  argsList.add("%"+cust_name+"%");
		    }
		    if (!"".equals(cust_cell) && cust_cell != null) {
			  sb.append(" and t1.cust_cell like ? ");
			  argsList.add("%"+cust_cell+"%");
		    }
		    if (!"".equals(cust_level) && cust_level != null) {
			  sb.append(" and t4.dict_value = ? ");
			  argsList.add(cust_level);
		    }
			sb.append(" order by t1.cust_id ");
			sb.append(" limit "+n+" OFFSET "+m+" ");
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForList(sb.toString(), parms);
		}
		/**
		 * 分页查询客户名称等刷新列表(全量)
		 * @param cust_name
		 * @return
		 */
		public int all_cust_select1(String state,String cust_name,String cust_cell,String sales_name,String cust_level){
			List<String> argsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append(" select  count(*) from cust_info t1 ");
			sb.append(" inner join sales_cust_rel t2 on t1.cust_id=t2.cust_id and t2.cust_belong_state='1' ");
			if (!"".equals(sales_name) && sales_name != null) {
		    sb.append(" and  t2.sales_name like ? ");
		    argsList.add("%"+sales_name+"%");
		    }
			sb.append(" left join data_dict t3 on t3.dict_value = t1.state and t3.dict_type = 'state' ");
			sb.append(" left join data_dict t4 on t4.dict_value = t1.cust_level and t4.dict_type = 'level' ");
		    sb.append(" left join data_dict t5 on t5.dict_value = t1.cust_sex and t5.dict_type = 'sex' ");
			sb.append(" where 1=1 ");
			if (!"".equals(state) && state != null && state != "0" && !state.equals("0")) {
			  sb.append(" and t3.dict_value = ? ");
			  argsList.add(state);
		     }
			if (!"".equals(cust_name) && cust_name != null) {
			  sb.append(" and t1.cust_name like ? ");
			  argsList.add("%"+cust_name+"%");
		    }
		    if (!"".equals(cust_cell) && cust_cell != null) {
			  sb.append(" and t1.cust_cell like ? ");
			  argsList.add("%"+cust_cell+"%");
		    }
		    if (!"".equals(cust_level) && cust_level != null) {
				  sb.append(" and t4.dict_value = ? ");
				  argsList.add(cust_level);
			    }
			Object[] parms = new Object[argsList.size()];
			for (int i = 0; i < argsList.size(); i ++) {
				String str = argsList.get(i);
				parms[i] = str;
			}
			return jt.queryForInt(sb.toString(), parms);
		}
		
		/*测试查询条件*/

		/**
		 * 添加客户拜访记录
		 * @param addsee
		 * @return
		 */
		public int addSee(long cust_id,String see_date, String see_member, String see_desc) 
				throws Exception {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date seedate = null;
				if(see_date!=""){
					seedate = sdf.parse(see_date);
				}
				long see_id = jdbcUtil.seq();
				long email_id = jdbcUtil.seq();
				String sql = "INSERT INTO public.cust_see_info"
						+ "(see_id, cust_id, see_date, see_member, see_desc, email_id) "
						+ "VALUES (?,?, ?, ?, ?, ?)";
				
				return jt.update(sql,see_id, cust_id, seedate, see_member, see_desc, email_id);
			
		}
		/**
		 * 添加客户公司
		 * @param addMember
		 * @return
		 */
		public void addMember(long cust_id,String comp_name, String comp_type, String license, String legal, String taxid, String org_code_cert, double reg_capital, String reg_address,
				String reg_date, String opera_period, String state)
				throws Exception {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date seedate = null;
				Date operaperiod = null;
				if(reg_date!=""){
					seedate = sdf.parse(reg_date);
				}
				if(opera_period!=""){
					operaperiod = sdf.parse(opera_period);
				}
				Object[] o = new Object[] { comp_name, comp_type, license, legal, taxid, org_code_cert, reg_capital,
						reg_address,reg_date, opera_period, state};
				long comp_id = jdbcUtil.seq();
				
				String sql = "INSERT INTO comp_info(comp_id, comp_name, comp_type, license, legal, taxid, org_code_cert, "
						+ "reg_capital, reg_address, reg_date, opera_period, state) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				jt.update(sql,comp_id, comp_name, comp_type, license, legal, taxid, org_code_cert,
						reg_capital, reg_address, reg_date, opera_period, state);
				/*int comp_id1 = insertAndGetKey(sql, o, "comp_id");*/
				long email_id = jdbcUtil.seq();
				String sql1 = "INSERT INTO public.cust_comp_rel(cust_id, comp_id, email_id) VALUES (?, ?, ?)";
				
			 jt.update(sql1,cust_id, comp_id,email_id);
			
		}
		
		//获取id
		public int insertAndGetKey(final String sql, final Object[] o, String id) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jt.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

					// String sql_sms = "insert into
					// sms(title,content,date_s,form,sffs,by1,by2,by3) values
					// (?,?,'"+dates+"',?,?,?,?,?)";
					PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					for (int i = 0; i < o.length; i++) {
						ps.setObject(i + 1, o[i]);
					}

					return ps;
				}
			}, keyHolder);
			Map<String, Object> generatedId = keyHolder.getKeyList().get(0);
			Integer com_id = (Integer) generatedId.get(id);
			// Long generatedId = keyHolder.getKey().longValue();
			// int a =Integer.parseInt(o_id);
			return com_id;
		}
		/**
		 * 添加前查询客户是否存在
		 * 
		 * @param condition
		 * @return
		 */
		public List<Map<String, Object>> querycust(String condition) {
			String sql = "SELECT t1.cust_id, t1.cust_name, t1.cust_cell, "
					+ "t2.sales_id, t2.cust_id, t2.sales_name, t2.sales_area "
					+ "FROM cust_info t1, sales_cust_rel t2 "
					+ "where t1.cust_id=t2.cust_id and t1.cust_cell=?";
			return jt.queryForList(sql, condition);
		}	
		/**
		 * 插入前先查询是否已在系统中
		 * @param custcell
		 * @return
		 */
		public  List<Map<String,Object>> checkcustcell(String cust_cell){
			String sql="select count(1) from cust_info where cust_cell=? limit 1";
			return jt.queryForList(sql,cust_cell);
		}
		/**
		 * 添加客户基本信息
		 * @param addCustinfo
		 * @return
		 */
		public long addCustinfo(User user,String cust_reg_time,String cust_name,String cust_sex,String cust_birth,String cust_level,
				String cust_idnum,String cust_idtype,String city,String email,String wechat,String qq,String address,String company,
				String id_address,String profession,String state,String cust_cell,String cust_risk,
				String see_date,String see_member,String see_desc,
				String sales_name, String sales_post, String allot_date, String recycle_date, String recycle_reason, String sales_area, String cust_state,
				String cust_type, String effect_sign,
				String family_name,String reg_time,String textarea,
			    String comp_name, String comp_type, String license, String legal, String org_code_cert, double reg_capital, 
				String reg_address, String  reg_date, String opera_period, String taxid,
				String family_cust_desc,String family_mem_num,String recorder,String remark,String family_cust_level,
				String relation) 
				throws Exception {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date seedate = null;
				if(see_date!=""){
					seedate = sdf.parse(see_date);
				}
				long cust_id = jdbcUtil.seq();
				long see_id = jdbcUtil.seq();
				long email_id = jdbcUtil.seq();
				long sales_id = jdbcUtil.seq();
				Object[] o = new Object[] {cust_name, cust_sex, cust_birth, cust_level, 
						cust_idnum, cust_idtype, city, email, wechat, qq, address, company,id_address, profession, cust_cell, cust_risk};
				String sql = "INSERT INTO cust_info(cust_id, cust_reg_time, cust_name, cust_sex, "
						+ "cust_birth, cust_level, cust_idnum, cust_idtype, city, email, wechat, qq, address, company,"
						+ " id_address, profession, state, cust_cell, cust_risk) "
						+ "VALUES (?, current_date, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '1', ?, ?)";
				jt.update(sql,cust_id, cust_name, cust_sex,cust_birth, "1", cust_idnum, cust_idtype, city, email, wechat, qq, address, company,
						id_address, profession, cust_cell, cust_risk);
                /*int cust_id1 = insertAndGetKey(sql, o, "cust_id");*/
				//拜访记录
				String sql1 = "INSERT INTO cust_see_info(see_id, cust_id, see_date, see_member, see_desc, email_id)"
						+ " VALUES (?, ?, ?, ?, ?, ?)";
				
				jt.update(sql1,see_id, cust_id, seedate, see_member, see_desc, email_id);
				
				String sql2 = "INSERT INTO public.sales_cust_rel(sales_id, cust_id, sales_name, sales_post, allot_date, recycle_date, "
						+ "recycle_reason, sales_area, cust_belong_state, email_id) "
						+ "VALUES (?, ?, ?, ?, '', '', '', ?, '1', ?)";
				
				jt.update(sql2, user.get_id(), cust_id, user.getRealName(), user.getPosition(), user.getArea(), jdbcUtil.seq());
				
				String sql3 = "INSERT INTO public.bill_info(id, cust_type, effect_sign, email_id) "
						+ "VALUES (?, '1', '0', ?)";
				jt.update(sql3, cust_id,jdbcUtil.seq());
				return cust_id;
				
		}
		
		
		/**
		 * 添加客户公司基本信息
		 * @param addCompinfo
		 * @return
		 */
		public void addCompinfo(long cust_id,String comp_name, String comp_type, String license, String legal, String taxid, String org_code_cert, double reg_capital, String reg_address,
				String reg_date, String opera_period, String state){
				
				long comp_id = jdbcUtil.seq();
				
				String sql = "INSERT INTO comp_info(comp_id, comp_name, comp_type, license, legal, taxid, org_code_cert, "
						+ "reg_capital, reg_address, reg_date, opera_period, state) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				jt.update(sql,comp_id, comp_name, comp_type, license, legal, taxid, org_code_cert,
						reg_capital, reg_address, reg_date, opera_period, state);
				
				long email_id = jdbcUtil.seq();
				String sql1 = "INSERT INTO public.cust_comp_rel(cust_id, comp_id, email_id) VALUES (?, ?, ?)";
				
			 jt.update(sql1,cust_id, comp_id,email_id);
				
		}
		
		/**
		 * 添加客户家族及成员信息
		 * @param see_desc1 
		 * @param see_member1 
		 * @return
		 */
		public void addFamilyinfo(long cust_id,long family_id,String family_name,String family_cust_desc,String family_cust_level,
				String  relation,String  cust_reg_time,String cust_name,
				String  cust_name1, String  cust_sex1,String  cust_cell1, String cust_birth1, String cust_idtype1, String cust_idnum1,
				String city1, String email1, String wechat1, String qq1, String address1, String company1, String id_address1, 
				String profession1, String see_date1, String see_member1, String see_desc1,List<Object[]> custList) {
				
				//家族信息
				/*long family_id = jdbcUtil.seq();*/
				String sqlFamily ="INSERT INTO public.family_info(family_id, "
				   		+ "family_name, family_cust_host_id, family_cust_name, family_cust_desc, "
				   		+ " reg_time, family_cust_level) "
				   		+ "VALUES (?, ?, ?, ?, ?, current_date, '1')";
				   jt.update(sqlFamily,family_id,family_name, cust_id, cust_name, family_cust_desc);
				   
								
				String sqlfamilymember ="insert into public.family_member_rel(relation, family_id, cust_id, email, cust_reg_time, "
						+ "cust_name, cust_sex,  cust_birth,cust_level,cust_idnum, cust_idtype,  "
						+ "city,  wechat, qq, address, company, id_address, profession, "
						+ "member_id, state, cust_cell )"
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				jt.batchUpdate(sqlfamilymember, custList);
			   
				long email_id1 = jdbcUtil.seq();
				long member_id1 = jdbcUtil.seq();				
				String sqlFamily_rel="INSERT INTO public.family_member_rel(family_cust_name, relation, "
						+ "family_id, email_id, cust_reg_time, cust_name, cust_sex, cust_cell, "
						+ "cust_birth, cust_idtype, cust_idnum, city, email, wechat, qq, address, company, "
						+ "id_address, profession, see_date, see_member, see_desc, member_id ) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				jt.update(sqlFamily_rel,cust_name, relation, family_id, email_id1, cust_reg_time, cust_name1,
						cust_sex1, cust_cell1, cust_birth1, cust_idtype1, cust_idnum1, city1, 
						email1,wechat1, qq1, address1, company1, id_address1, 
						profession1, see_date1, see_member1, see_desc1,member_id1);
				
		}
		/**
		 * 查询家族信息
		 * @param family_id
		 * @return
		 *//*
		public List<Map<String, Object>> select_family_id(long family_id){
			String sqlCust = "SELECT family_id, family_name, family_cust_host_id, family_cust_name, "
					+ "family_cust_desc, family_mem_num, recorder, reg_time, remark, family_cust_level "
					+ " FROM public.family_info  where family_id = ?";
			return jt.queryForList(sqlCust,family_id);
		}*/
		/**
		 * 查询家族成员
		 * @param cust_id
		 * @return
		 */
		public List<Map<String, Object>> select_cust_id(long cust_id){
			String sqlCust = "SELECT cust_id, cust_reg_time, cust_name, cust_sex, cust_birth, cust_level,"
		    		+ " cust_idnum, cust_idtype, city, email, wechat, qq, address, company, id_address, profession, "
		    		+ "state, cust_cell  FROM public.cust_info where cust_id = ?";
			return jt.queryForList(sqlCust,cust_id);
		}
		/**
		 * 有家族后，添加家庭成员
		 *  
		 * 
		 * @return
		 */
		public void addFamilymember(long family_id, String  relation,String  cust_reg_time,String cust_name,
				String  cust_name3, String  cust_sex3,String  cust_cell3, String cust_birth3, String cust_idtype3, String cust_idnum3,String relation3,
				String city3, String email3, String wechat3, String qq3, String address3, String company3, String id_address3, 
				String profession3, String see_date3, String see_member3, String see_desc3,List<Object[]> custList) {
				
			
								
				String sqlfamilymember ="insert into public.family_member_rel(relation, family_id, cust_id, email, cust_reg_time, "
						+ "cust_name, cust_sex,  cust_birth,cust_level,cust_idnum, cust_idtype,  "
						+ "city,  wechat, qq, address, company, id_address, profession, "
						+ "member_id, state, cust_cell )"
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				jt.batchUpdate(sqlfamilymember, custList);
			   
				long email_id3 = jdbcUtil.seq();
				long member_id3 = jdbcUtil.seq();				
				String sqlFamily_rel="INSERT INTO public.family_member_rel(family_cust_name, relation, "
						+ "family_id, email_id, cust_reg_time, cust_name, cust_sex, cust_cell, "
						+ "cust_birth, cust_idtype, cust_idnum, city, email, wechat, qq, address, company, "
						+ "id_address, profession, see_date, see_member, see_desc, member_id ) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				jt.update(sqlFamily_rel,cust_name, relation3, family_id, email_id3, cust_reg_time, cust_name3,
						cust_sex3, cust_cell3, cust_birth3, cust_idtype3, cust_idnum3, city3, 
						email3,wechat3, qq3, address3, company3, id_address3, 
						profession3, see_date3, see_member3, see_desc3,member_id3);
				
		}
		/**
		 * 
		 * 有家族后，添加家族成员前查询客户是否存在
		 * 
		 * @param condition
		 * @return
		 */
		public List<Map<String, Object>> queryFamMem(String queryfm) {
			String sql = "SELECT t1.cust_id, t1.cust_name, t1.cust_reg_time, t1.cust_sex, t1.cust_birth, t1.cust_level, t1.cust_idnum, "
					+ "t1.cust_idtype, t1.city, t1.email, t1.wechat, t1.qq, t1.address, t1.company, t1.id_address, t1.profession, "
					+ "t1.state, t1.cust_cell, t1.cust_risk, "
					+ "t2.sales_id, t2.cust_id, t2.sales_name, t2.sales_area "
					+ "FROM cust_info t1, sales_cust_rel t2 "
					+ "where t1.cust_id=t2.cust_id and t1.cust_cell=?";
			return jt.queryForList(sql, queryfm);
		}
		/**
		 * 
		 * 添加家族成员前查询客户是否存在
		 * 
		 * @param condition
		 * @return
		 */
		public List<Map<String, Object>> queryFamilyMember(String queryFM) {
			String sql = "SELECT t1.cust_id, t1.cust_name, t1.cust_reg_time, t1.cust_sex, t1.cust_birth, t1.cust_level, t1.cust_idnum, "
					+ "t1.cust_idtype, t1.city, t1.email, t1.wechat, t1.qq, t1.address, t1.company, t1.id_address, t1.profession, "
					+ "t1.state, t1.cust_cell, t1.cust_risk, "
					+ "t2.sales_id, t2.cust_id, t2.sales_name, t2.sales_area "
					+ "FROM cust_info t1, sales_cust_rel t2 "
					+ "where t1.cust_id=t2.cust_id and t1.cust_cell=?";
			return jt.queryForList(sql, queryFM);
		}	
		/**
		 * 更新客户基本信息(不再使用)
		 * @param saveCust
		 * @return
		 */
		public void saveCust(long cust_id,String cust_name,String cust_sex,String cust_birth,String cust_level,
				String cust_idnum,String cust_idtype,String  city,String email,String wechat,String qq,
				String address,String company,String profession,String cust_risk) {
			String sql = "UPDATE public.cust_info "
					+ "SET cust_name=?, cust_sex=?, cust_birth=?, cust_level=?, cust_idnum=?, "
					+ "cust_idtype=?, city=?, email=?, wechat=?, qq=?, address=?, company=?, "
					+ " profession=?, cust_risk=?, "
					+ "state='3' WHERE  cust_id=?";
			
			jt.update(sql, cust_name,cust_sex,cust_birth,cust_level,cust_idnum,cust_idtype,
					city,email,wechat,qq,address,company,profession,cust_risk,cust_id);

		}
		/**
		 * 更新客户基本信息
		 * @param cust_submit
		 * @return
		 */
		public void cust_submit(long cust_id,String cust_name,String cust_sex,String cust_birth,String cust_level,
				String cust_idnum,String cust_idtype,String  city,String email,String wechat,String qq,
				String address,String company,String profession,String cust_cell,String cust_risk) {
			String sql = "UPDATE public.cust_info "
					+ "SET cust_name=?, cust_sex=?, cust_birth=?, cust_level=?, cust_idnum=?, "
					+ "cust_idtype=?, city=?, email=?, wechat=?, qq=?, address=?, company=?, "
					+ " profession=?, cust_risk=? " + "WHERE  cust_id=?";
			
			jt.update(sql, cust_name,cust_sex,cust_birth,cust_level,cust_idnum,cust_idtype,
					city,email,wechat,qq,address,company,profession,cust_risk,cust_id);
		}
		/**
		 * 把历史信息保留起来，以备还原
		 * @param json
		 * @param id
		 * @param remark
		 * @param type
		 */
		public void addtask(JSONObject json ,long id,String remark,String type,long user_id){
			String sql ="insert into task(content,id,remark,type,version,user_id)values('"+json+"',?,?,?,default,?) ";
			jt.update(sql, id,remark,type,user_id);
		}
		/**
		 * 客户基本信息审批通过
		 * @param cust_submit
		 * @return
		 */
		public void cust_pass(long cust_id) {
			String sql = "UPDATE public.cust_info "
					+ "SET state='3' WHERE state='2' and cust_id=?";
			
			jt.update(sql, cust_id);

		}
		/**
		 * 客户基本信息审批不通过
		 * @param cust_submit
		 * @return
		 */
		public void cust_nopass(long cust_id) {
			String sql = "UPDATE public.cust_info "
					+ "SET state='4' WHERE state='2' and cust_id=?";
			
			jt.update(sql, cust_id);

		}
		
		/**
		 * 更新家族信息后提交审批
		 * @param fammember_submit
		 * @return
		 */
		public void fammember_submit(long family_id,long member_id,String family_cust_desc,String family_cust_name,String relation,
				String cust_name,String cust_cell,String cust_birth,String cust_idnum,String city,
				String email,String wechat,String qq,String address,String company,String id_address,String  profession) {
			String sql = "UPDATE public.family_info "
					+ "SET family_cust_level = '2', family_cust_desc=?  WHERE family_id=?";
			jt.update(sql, family_cust_desc, family_id);
			
			String sql1 = "UPDATE public.family_member_rel  "
					+ "SET family_cust_name=?, relation=?,cust_name=?, cust_cell=?, cust_birth=?, "
					+ " cust_idnum=?, city=?, email=?, wechat=?, qq=?, address=?, company=?, id_address=?, "
					+ "profession=?  "
					+ "WHERE  family_id=? and member_id=?";
			
			jt.update(sql1, family_cust_name, relation, cust_name, cust_cell, cust_birth, cust_idnum, city, email,
					wechat, qq,address,company,id_address,profession,family_id,member_id);

		}
		/**
		 * 家族基本信息审批通过
		 * @param fammember_pass
		 * @return
		 */
		public void fammember_pass(long family_id) {
			String sql = "UPDATE public.family_info  "
					+ "SET family_cust_level='3'  WHERE family_cust_level='2' and family_id=?";
			
			jt.update(sql, family_id);

		}
		/**
		 * 家族基本信息审批不通过
		 * @param fammember_nopass
		 * @return
		 */
		public void fammember_nopass(long family_id) {
			String sql = "UPDATE public.family_info  "
					+ "SET family_cust_level='4'  WHERE family_cust_level='2' and family_id=?";
			
			jt.update(sql, family_id);

		}
		/**
		 * 针对客户所有信息进行保存
		 * @param cust_submit
		 * @return
		 */
		public void skipaddsave(long cust_id) {
			String sql = "UPDATE public.cust_info "
					+ "SET state='1' WHERE cust_id=?";
			
			jt.update(sql, cust_id);

		}
		/**
		 * 针对客户所有信息进行提交审批
		 * @param cust_submit
		 * @return
		 */
		public void skipaddpass(long cust_id,String cust_name,String cust_sex,String cust_cell,String see_desc, 
				User user,String email_leader,String real_name_leader) {
			String sql = "UPDATE public.cust_info "
					+ "SET state='2' WHERE state='1' and cust_id=?";
			
			jt.update(sql, cust_id);
			
			SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SentMailInfoBean sentmsg = new SentMailInfoBean();
			//sentmsg.setFirmId("001");
			sentmsg.setSubjectId(""+cust_id);
			sentmsg.setSentMailaddr(mailCache.from);
			sentmsg.setReviceMailaddr(email_leader);
			sentmsg.setCcAddress(user.getEmail());
			sentmsg.setMail_busstype("新建个人客户审批");
			sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.mailcust");
			sentmsg.setMailContent("创建个人客户：					\n"
					+ "尊敬的（"+real_name_leader+"）：  					\n"
					+ "您好，"+sdfg.format(new Date())+" "+user.getRealName()+"（销售操作名）在OMS系统创建个人客户信息，现等待您审批。\n"
					+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
					+ "---InfoBegin---					\n"
					+ "【客户姓名】：	"+cust_name+"				\n"
					+ "【性别】：	"+cust_sex+"				\n"
					+ "【联系方式】："+cust_cell+"					\n"
					+ "【拜访记录】：	"+see_desc+"				\n"
					+ "---InfoEnd--- \n"					
	);
			sentmsg.setSubject("新建客户审批");
			sendMail.sendMessage(sentmsg, true);

		}
		/**
		 * 邮件审批客户信息
		 * @param cust_id
		 * @param status
		 */
		public void mail_check(long cust_id,String state) {
			String sql = "UPDATE public.cust_info SET state=? WHERE cust_id=?";
			 jt.update(sql,state,cust_id);
		}
		/**
		 * 邮件审批家族信息
		 * @param cust_id
		 * @param status
		 */
		public void mail_check_family(long family_id,String state) {
			String sql = "UPDATE public.family_info SET family_cust_level=? WHERE family_id=?";
			 jt.update(sql,state,family_id);
		}
		/**
		 * 更新客户公司信息
		 * @param saveCust
		 * @return
		 */
		public void saveComp(long cust_id,long comp_id,String comp_name,String comp_type,String license,
				String legal,String org_code_cert,double reg_capital,String reg_address,String reg_date,
				String opera_period,String taxid) {
			String sql = "UPDATE public.comp_info "
					+ "SET  comp_name=?, comp_type=?, license=?, legal=?, org_code_cert=?, "
					+ "reg_capital=?, reg_address=?, reg_date=?, opera_period=?, taxid=? "
					+ "WHERE  comp_id=?";
			
			jt.update(sql, comp_name, comp_type, license, legal, org_code_cert, reg_capital, reg_address, reg_date,
					opera_period, taxid,comp_id);

		}
		/**
		 * 更新客户家族及成员信息
		 * @param saveCust
		 * @return
		 */
		public void fammember_edit(long family_id,long member_id,String family_cust_desc,String family_cust_name,String relation,
				String cust_name,String cust_cell,String cust_birth,String cust_idnum,String city,
				String email,String wechat,String qq,String address,String company,String id_address,String  profession) {
			String sql = "UPDATE public.family_info "
					+ "SET  family_cust_desc=?  WHERE family_id=?";
			jt.update(sql, family_cust_desc, family_id);
			
			String sql1 = "UPDATE public.family_member_rel  "
					+ "SET family_cust_name=?, relation=?,cust_name=?, cust_cell=?, cust_birth=?, "
					+ " cust_idnum=?, city=?, email=?, wechat=?, qq=?, address=?, company=?, id_address=?, "
					+ "profession=?  "
					+ "WHERE  family_id=? and member_id=?";
			
			jt.update(sql1, family_cust_name, relation, cust_name, cust_cell, cust_birth, cust_idnum, city, email,
					wechat, qq,address,company,id_address,profession,family_id,member_id);

		}
		
		
		/**
		 * 添加客户家族及成员信息     （重复了）
		 * @param add
		 * @return
		 */
		public void addFamilymem(long cust_id,String family_name,String family_cust_desc,
				String reg_time,String family_cust_level,String  relation,String  cust_reg_time,String cust_name,
				String  cust_sex,String  cust_cell,String  cust_birth,String  cust_idtype,String  cust_idnum,String  city,String  email,String  wechat,
				int count_num, String  qq,String  address,String company,String id_address,String profession,String see_date,String see_member,String see_desc) {
				
			   String sqlfam = "select count(*) into count_num from family_member_rel where cust_id = ?";  
			  if (count_num<=0){
				//家族信息
				long family_id = jdbcUtil.seq();
			   String sqlFamily ="INSERT INTO public.family_info(family_id, "
			   		+ "family_name, family_cust_host_id, family_cust_name, family_cust_desc, "
			   		+ " recorder, reg_time, family_cust_level) "
			   		+ "VALUES (?, ?, ?, ?, ?, ?, current_date, ?)";
			    
				jt.update(sqlFamily,family_id,family_name, cust_id, cust_name, family_cust_desc, family_cust_level);
				}
				else{
				long email_id = jdbcUtil.seq();
				long member_id = jdbcUtil.seq();
				String sqlFamily_rel="INSERT INTO public.family_member_rel(family_cust_name, relation, "
						+ "family_id, cust_id, email_id, cust_reg_time, cust_name, cust_sex, cust_cell, "
						+ "cust_birth, cust_idtype, cust_idnum, city, email, wechat, qq, address, company, "
						+ "id_address, profession, see_date, see_member, see_desc, member_id ) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				jt.update(sqlFamily_rel,cust_name, relation, cust_id, email_id, cust_reg_time, cust_name, cust_sex, 
						cust_cell, cust_birth, cust_idtype, cust_idnum, city, email, wechat, qq, address, company, id_address, profession, see_date, 
						see_member, see_desc,member_id);
				}
				
		}
		
		
		/**
		 * 添加家族成员填充字典值
		 * @return
		 */
		public List<Map<String, Object>> fam(){
			String sql ="select  t3.dict_name lel_name,t3.dict_value lel_value,t3.dict_type lel_type  "
					+ "from data_dict t3 WHERE t3.dict_type = 'level'  UNION ALL  "
					+ "SELECT t4.dict_name sex_name,t4.dict_value sex_value,t4.dict_type sex_type  "
					+ "FROM data_dict T4 WHERE t4.dict_type = 'sex' UNION ALL  "
					+ "SELECT t5.dict_name idtype_name,t5.dict_value idtype_value,t5.dict_type idtype_type "
					+ " FROM data_dict T5 WHERE t5.dict_type = 'idtype'";
			return jt.queryForList(sql);
		}
		/**
		 * 添加kyc
		 * @param datas
		 */
		public void kycinsert(String[] datas){
			

		}
		/**
		 * 重置家族信息
		 * @param json
		 * @param family_id
		 */
		public void family_reset(JSONObject json,long family_id,long member_id){
			String familysql = "UPDATE public.family_info SET  family_cust_desc=? WHERE family_id=?";
			List<Map<String, Object>> family_list = 
					(List<Map<String, Object>>) json.get("family_list");
			List<Map<String, Object>> member_list = 
					(List<Map<String, Object>>) json.get("member_list");
			String relation = (String) member_list.get(0).get(("relation"));
			String cust_name = (String) member_list.get(0).get(("cust_name"));
			String cust_cell = (String) member_list.get(0).get(("cust_cell"));
			String cust_idnum = (String) member_list.get(0).get(("cust_idnum"));
			String wechat = (String) member_list.get(0).get(("wechat"));
			String qq = (String) member_list.get(0).get(("qq"));
			String company = (String) member_list.get(0).get(("company"));
			String profession =(String) member_list.get(0).get(("profession"));
			String membersql = "UPDATE public.family_member_rel "
					+ "SET relation=?,cust_name=?,  cust_cell=?,  cust_idnum=?, "
					+ "wechat=?, qq=?, company=?, profession=? "
					+ "WHERE family_id =? and member_id=?";
			jt.update(familysql,family_list.get(0).get("family_cust_desc"),family_id);
			jt.update(membersql,relation,cust_name,cust_cell,cust_idnum,
					wechat,qq,company,profession,family_id,member_id);
		}
		/**
		 * 家族关系表信息
		 * @param family_id
		 * @param member_id
		 * @return
		 */
		public List<Map<String, Object>> member_list(long family_id,long member_id){
			String sql="SELECT family_cust_name, relation, family_id, cust_id, email_id, cust_reg_time, "
					+ "cust_name, cust_sex, cust_cell, cust_birth, cust_idtype, cust_idnum, "
					+ "city, email, wechat, qq, address, company, id_address, profession, "
					+ "see_date, see_member, see_desc, member_id, state, cust_level "
					+ "FROM public.family_member_rel where family_id=? and member_id=?";
			return jt.queryForList(sql,family_id,member_id);
		}
		
		/**
		 * 家族表信息
		 * @param family_id
		 * @param member_id
		 * @return
		 */
		public List<Map<String, Object>> family_list(long family_id){
			String sql="SELECT family_id, family_name, family_cust_host_id, family_cust_name, "
					+ "family_cust_desc, family_mem_num, recorder, reg_time, remark, "
					+ "family_cust_level "
					+ "FROM public.family_info where family_id=?";
			return jt.queryForList(sql,family_id);
		}
		/**
		 * 客户重置
		 * @param json
		 * @param cust_id
		 */
		public void cust_reset(JSONObject json,long cust_id){
			String sql = "UPDATE public.cust_info   "
					+ "SET cust_name=?, cust_sex=?, cust_birth=?, cust_level=?, cust_idnum=?, "
					+ "cust_idtype=?, city=?, email=?, wechat=?, qq=?, address=?, company=?, "
					+ " profession=?, cust_cell=?, cust_risk=? "
					+ "WHERE cust_id=?";
			String cust_name = json.get("cust_name").toString();
			String cust_cell = json.get("cust_cell").toString();
			String cust_idtype = json.get("cust_idtype").toString();
			String cust_idnum = json.get("cust_idnum").toString();
			String cust_sex = json.get("cust_sex").toString();
			String cust_birth = json.get("cust_birth").toString();
			String cust_level = json.get("cust_level").toString();
			String city = json.get("city").toString();
			String email = json.get("email").toString();
			String wechat = json.get("wechat").toString();
			String qq = json.get("qq").toString();
			String profession = json.get("profession").toString();
			String company = json.get("company").toString();
			String address = json.get("address").toString();
			String cust_risk = json.get("cust_risk").toString();
			jt.update(sql,cust_name,cust_sex,cust_birth,cust_level,cust_idnum,
					cust_idtype,city,email,wechat,qq,address,company,
					profession,cust_cell,cust_risk,cust_id);
		}
		
		/**
		 * @param orig_email
		 * @param orig_name
		 */
		public void add_originator(String orig_email, String orig_name, long user_id, String user_name) {
			String sql = "INSERT INTO cust_orig(id, email, name, c_time, c_user_id, c_user_name)VALUES (?, ?, ?, current_date, ?, ?);";
			jt.update(sql, jdbcUtil.seq(), orig_email, orig_name, user_id, user_name);
		}
		
		/**
		 * @param sub_name
		 */
		public void add_subject(String sub_name, long user_id, String user_name) {
			String sql = "INSERT INTO cust_subject(id, name, c_time, c_user_id, c_user_name) VALUES (?, ?, current_date, ?, ?);";
			jt.update(sql, jdbcUtil.seq(), sub_name, user_id, user_name);
		}
		
		/**
		 * 获取发件人邮箱
		 */
		public List<Map<String ,Object>> getOriginatorList(){
			String sql ="SELECT mail_user_username FROM mail_user_info where type='customer'";
			List<Map<String ,Object>> list = jt.queryForList(sql);
			return list;
		}
		
		/**
		 * 获取主题邮箱
		 */
		public List<Map<String ,Object>> getSubjectListByUserId(long user_id){
			String sql ="SELECT id, name, c_time, c_user_id, c_user_name FROM cust_subject where c_user_id =?";
			List<Map<String ,Object>> list = jt.queryForList(sql, user_id);
			return list;
		}
		
		/**
		 * 获取发送邮件客户
		 */
		public List<Map<String, Object>> getCustListByName(String cust_name) {
			String sql = "select c.cust_id, c.cust_name, c.email, u.real_name from cust_info c, upm_user u, sales_cust_rel s where c.cust_id = s.cust_id and u.user_id = s.sales_id and c.cust_name=?";
			List<Map<String, Object>> list = jt.queryForList(sql, cust_name);
			return list;
		}
		
		/**
		 * 获取客户邮箱
		 */
		
		public List<Map<String, Object>> getCustEmailById(Long cust_id) {
			String sql = "select email from cust_info where cust_id = ?";
			List<Map<String, Object>> list = jt.queryForList(sql, cust_id);
			return list;
		}
		
		/**
		 * 获取发送邮箱配置信息
		 * 
		 */
		 public List<Map<String, Object>> getMailConfig(String email) {
			 String sql = "select * from mail_user_info where mail_user_username=?";
			 List<Map<String, Object>> list = jt.queryForList(sql, email);
			 return list;
		 }
		 
		 /**
		  * 获取邮件信息
		  */
		 public List<Map<String, Object>> getMailByUser(Long user_id) {
			 String sql = "select mail_user_username from mail_user_info where user_id = ?";
			 List<Map<String, Object>> list = jt.queryForList(sql, user_id);
			 return list;
		 }
		 
		 /**
		  * 更新邮件密码
		  */
		 
		  public int updateMailPwdByUserAndMail(String email, Long user_id, String pwd) {
			  String sql = "update mail_user_info set mail_user_pwd=?, u_time=current_date  where mail_user_username=? and user_id = ?";
			  return jt.update(sql, pwd, email, user_id);
		  }
		  
		  /**
		   * 添加客服发送邮件
		   */
		  
		  public int insertCustMailInfo(Long mail_subjectid, String mail_subject, String mail_sendperson, String mail_reciveperson, String mail_content, String fileNames, Long user_id, String user_name, String mail_receive_name) {
			  String sql = "INSERT INTO cust_mail_info(mail_subjectid, mail_subject, mail_sendperson, mail_reciveperson, mail_content, mail_files_name, user_id, user_name, c_time, mail_receive_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, current_date, ?);";
			  return jt.update(sql, mail_subjectid, mail_subject, mail_sendperson, mail_reciveperson, mail_content, fileNames, user_id, user_name, mail_receive_name);
		  }
		  
		  /**
		   * 客户邮件列表
		   */
		  
		  public List<Map<String, Object>> getCustMailInfoList(int pageSize, int offset, String cust_name, String subject, long user_id) {
			  String sql = "select mail_subjectid, mail_subject, mail_receive_name, c_time, mail_files_name, mail_content from cust_mail_info where user_id="+user_id;
			  if(cust_name != null && !"".equals(cust_name.trim())) {
				  sql += " and mail_receive_name = '"+cust_name+"'";
			  }
			  
			  if(subject != null && !"".equals(subject.trim())) {
					  sql += " and mail_subject='"+subject+"'";
			  }
			  sql += " order by mail_subject desc";
			  sql += " limit "+pageSize+" OFFSET "+offset+"";
			  return jt.queryForList(sql);
		  }
		  
		  /**
		   * 获取邮件列表总数
		   */
		  
		  public List<Map<String, Object>> getCustMailTotalCount(String cust_name, String subject, long user_id) {
			  String sql = "select count(*) from cust_mail_info where user_id="+user_id;
			  if(cust_name != null && !"".equals(cust_name.trim())) {
				  sql += " and mail_receive_name = '"+cust_name+"'";
			  }
			  
			  if(subject != null && !"".equals(subject.trim())) {
				  sql += " and mail_subject='"+subject+"'";
			  }
			  return jt.queryForList(sql);
		  }
		  
		  /**
		   * 根据邮件subjectid获取发送邮件内容
		   */
		  
		  public List<Map<String, Object>> getCustMailBySubjectId(String subjectid) {
			  String sql = "select mail_content from cust_mail_info where mail_subjectid=?";
			  return jt.queryForList(sql, subjectid);
		  }
		  
		  /**
		   * 客服分配外拨列表
		   */
		  
		  public List<Map<String, Object>> getCallCustInfoList(String cust_name, String area, String prod_no) {
			  String sql = "select oi.order_no, ci.cust_name, u.real_name, u.user_id, pi.prod_name from order_info oi, product_info pi, cust_info ci, sales_cust_rel s, upm_user u where oi.prod_no = pi.prod_id and ci.cust_id = oi.cust_no and ci.cust_id = s.cust_id and u.user_id = s.sales_id";
			  if(cust_name != null && !"".equals(cust_name.trim())) {
				  sql += " and ci.cust_name = '"+cust_name+"'";
			  }
			  
			  if(prod_no != null && !"".equals(prod_no.trim())) {
				  sql += " and oi.prod_no = "+Long.parseLong(prod_no);
			  }
			  return jt.queryForList(sql);
		  }
		  
		  /**
		   * 插入外拨分配记录
		   */
		  public void insertCustDistrib(Long service_id, String service_name, Long user_id, String user_name, String[] orderNos, String distrib_id) {
			  String sql = "";
			  if(distrib_id == null || "".equals(distrib_id)) {
				  sql = "INSERT INTO call_distrib(id, service_id, service_name, c_user_id, c_user_name, c_time, u_user_id, u_user_name, u_time, status)VALUES (?, ?, ?, ?, ?, current_date, ?, ?, current_date, ?);";
				  Long cust_distrib_id = jdbcUtil.seq();
				  jt.update(sql, cust_distrib_id, service_id, service_name, user_id, user_name, user_id, user_name, 1);
				  if(orderNos != null && orderNos.length > 0) {
					  for(int i = 0; i < orderNos.length; i++) {
						  sql = "INSERT INTO call_order_rel(call_distrib_id, order_no, status) VALUES (?, ?, ?);";
						  jt.update(sql, cust_distrib_id, Long.parseLong(orderNos[i]), 1);
					  }
				  }
			  }else {
				  sql = "UPDATE call_distrib SET service_id=?, service_name=?, u_user_id=?, u_user_name=?, u_time=current_date where id=?";
				  jt.update(sql, service_id, service_name, user_id, user_name, Long.parseLong(distrib_id));
				  
				  
				  sql = "UPDATE call_order_rel SET status=2 where call_distrib_id="+Long.parseLong(distrib_id);
				  if(orderNos != null && orderNos.length > 0) {
					  sql += " and order_no not in (";
					  for(int i = 0; i < orderNos.length; i++) {
						  if(i==0) {
							  sql += Long.parseLong(orderNos[i]);
						  }else {
							  sql += "," + Long.parseLong(orderNos[i]);
						  }
						  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						  String str = "select count(*) from call_order_rel where order_no = ? and call_distrib_id =?";
						  list = jt.queryForList(str, Long.parseLong(orderNos[i]), Long.parseLong(distrib_id));
						  if((Long) list.get(0).get("count") == 0) {
							  str = "INSERT INTO call_order_rel(call_distrib_id, order_no, status) VALUES (?, ?, ?);";
							  jt.update(str, Long.parseLong(distrib_id), Long.parseLong(orderNos[i]), 1);
						  }else {
							  str = "UPDATE call_order_rel SET status=2 where call_distrib_id=? and order_no=?";
							  jt.update(str, jdbcUtil.seq(), Long.parseLong(orderNos[i]));
						  }
					  }
					  sql +=")";
				  }
				  
				  jt.update(sql);
			  }
		  }
		  
		  /**
		   * 外拨管理列表
		   */
		  
		 public List<Map<String, Object>> getCallDistribList() {
			 String sql = "select id, service_name, service_id from call_distrib where status=1";
			 return jt.queryForList(sql);
		 }
		 
		 /**
		  * 根据id获取外拨列表
		  */
		 
		 public List<Map<String, Object>> getCallCustListById(Long id) {
			 String sql = "select oi.order_no, ci.cust_name, cd.service_id, u.real_name, pi.prod_name from call_distrib cd, call_order_rel cl, cust_info ci, sales_cust_rel s, upm_user u, order_info oi, product_info pi where cd.id = cl.call_distrib_id and oi.prod_no = pi.prod_id and oi.order_no=cl.order_no and oi.cust_no = ci.cust_id and ci.cust_id = s.cust_id and s.sales_id = u.user_id and cd.id=? and cl.status=1";
			 List<Map<String, Object>> custList = jt.queryForList(sql, id);
			 sql = "select o.org_name as cust_name, oi.order_no, pi.prod_name from order_info oi, org_info o, product_info pi, call_distrib cd, call_order_rel co where co.order_no = oi.order_no and co.call_distrib_id = cd.id and  pi.prod_id = oi.prod_no and o.org_id = oi.cust_no and cd.id=? and co.status=1";
			 List<Map<String, Object>> orgList = jt.queryForList(sql, id);
			 for(Map<String, Object> m: orgList) {
				 m.put("real_name", "");
				 custList.add(m);
			 }
			 return custList;
		 }
		 
		 /**
		  * 根据客服人员id获取外拨列表
		  */
		 
		 public List<Map<String, Object>> getCallCustListByServiceId(Long service_id, int offset, int pageSize, String cust_name, String prod_name) {
			 String sql = "select oi.order_no, ci.cust_name, cd.service_id, u.real_name, pi.prod_name from call_distrib cd, call_order_rel cl, cust_info ci, sales_cust_rel s, upm_user u, order_info oi, product_info pi where cd.id = cl.call_distrib_id and oi.prod_no = pi.prod_id and oi.order_no=cl.order_no and oi.cust_no = ci.cust_id and ci.cust_id = s.cust_id and s.sales_id = u.user_id and cd.service_id=? and cl.status=1";
			 if(cust_name != null && !"".equals(cust_name)) {
				 sql += " and ci.cust_name = '"+cust_name +"'";
			 }
			 if(prod_name != null && !"".equals(prod_name)) {
				 sql += " and pi.prod_name = '"+prod_name+"'";
			 }
			 sql += " limit "+pageSize+" OFFSET "+offset;
			 
			 List<Map<String, Object>> custList = jt.queryForList(sql, service_id);
			 
			 sql = "select o.org_name as cust_name, oi.order_no, pi.prod_name from order_info oi, org_info o, product_info pi, call_distrib cd, call_order_rel co where co.order_no = oi.order_no and co.call_distrib_id = cd.id and  pi.prod_id = oi.prod_no and o.org_id = oi.cust_no and cd.service_id=? and co.status=1";
			 
			 if(cust_name != null && !"".equals(cust_name)) {
				 sql += " and o.org_name = '"+cust_name +"'";
			 }
			 if(prod_name != null && !"".equals(prod_name)) {
				 sql += " and pi.prod_name = '"+prod_name+"'";
			 }
			 
			 sql += " limit "+pageSize+" OFFSET "+offset;
			 
			 List<Map<String, Object>> orgList = jt.queryForList(sql, service_id);
			 
			 for(Map<String, Object> m: orgList) {
				 m.put("real_name", "");
				 custList.add(m);
			 }
			 return custList;
		 }
		 
		 public Long getCallCustListCount(Long service_id, String cust_name, String prod_name) {
			 String sql = "select count(*) from call_distrib cd, call_order_rel cl, cust_info ci, sales_cust_rel s, upm_user u, order_info oi, product_info pi where cd.id = cl.call_distrib_id and oi.prod_no = pi.prod_id and oi.order_no=cl.order_no and oi.cust_no = ci.cust_id and ci.cust_id = s.cust_id and s.sales_id = u.user_id and cd.service_id=? and cl.status=1";
			 if(cust_name != null && !"".equals(cust_name)) {
				 sql += " and ci.cust_name = '"+cust_name +"'";
			 }
			 if(prod_name != null && !"".equals(prod_name)) {
				 sql += " and pi.prod_name = '"+prod_name+"'";
			 }
			 
			 List<Map<String, Object>> custList = jt.queryForList(sql, service_id);
			 
			 sql = "select count(*) from order_info oi, org_info o, product_info pi, call_distrib cd, call_order_rel co where co.order_no = oi.order_no and co.call_distrib_id = cd.id and  pi.prod_id = oi.prod_no and o.org_id = oi.cust_no and cd.service_id=? and co.status=1";
			 
			 if(cust_name != null && !"".equals(cust_name)) {
				 sql += " and o.org_name = '"+cust_name +"'";
			 }
			 if(prod_name != null && !"".equals(prod_name)) {
				 sql += " and pi.prod_name = '"+prod_name+"'";
			 }
			 
			 List<Map<String, Object>> orgList = jt.queryForList(sql, service_id);
			 
			 Long count = (Long)custList.get(0).get("count") + (Long)orgList.get(0).get("count");
			 return count;
		 }
		 
		 /**
		  * 删除外拨分配
		  */
		 
		 public void delCallCust(Long call_distrib_id) {
			 String sql = "update call_distrib set status = 2 where id = ?";
			 jt.update(sql, call_distrib_id);
			 
			 sql = "update call_order_rel set status = 2 where call_distrib_id = ?";
			 
			 jt.update(sql, call_distrib_id);
		 }
		 
		 /**
		  * 根据客户id获取客户信息
		  */
		 public List<Map<String, Object>> getCustListById(Long cust_id) {
			 String sql = "select cust_cell, cust_name, real_name from cust_info ci, upm_user u, sales_cust_rel s where ci.cust_id = ? and u.user_id = s.sales_id and ci.cust_id = s.cust_id";
			 return jt.queryForList(sql, cust_id);
		 }
		 
		 /**
		  * 插入拨打电话记录
		  */
		 
		 public Long insertCallRecode(String remark, String user_name, Long user_id, Long order_no, String cust_email, String is_self, String is_id_no, String id_no, String is_id_address, String id_address,
				 String is_partnership, String partnership, String is_order_amount, String order_amount, String is_mail_address, String mail_address, String work_address, String is_email) {
			 String sql = "INSERT INTO call_record(record_id, remark, c_user_id, c_user_name, order_no, c_time, cust_email, is_self, is_id_match, id_no, is_id_address_match, id_address, is_partership_match, partnership, is_order_amount_match, order_amount, is_mail_address_match, mail_address, work_address, is_email) VALUES (?, ?, ?, ?, ?, current_date, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?);";
			 Long id = jdbcUtil.seq();
			 jt.update(sql, id, remark, user_id, user_name, order_no, cust_email, is_self, is_id_no, id_no, is_id_address, id_address, is_partnership, partnership, is_order_amount, order_amount, is_mail_address, mail_address, work_address, is_email);
			 return id;
		 }
		 
		 /**
		  * 根据客户邮箱获取客户信息
		  */
		 
		 public List<Map<String, Object>> getCustCountByEmail(String cust_email) {
			 String sql = "select count(*) from cust_info where email=?";
			 return jt.queryForList(sql, cust_email);
		 }
		 
		 /**
		  * 邮件验证审核
		  */
		 
		 public void validateCallRecordEmail(Long id, String comment) {
			 String sql = "update call_record set email_comment = ?, email_status = '2' where record_id = ?";
			 jt.update(sql, comment);
		 }
		 
		 /**
		  * 根据订单号获取拨打验证信息
		  */
		 public List<Map<String, Object>> getVerifyInfoByOrderNo(Long order_no, Long service_id) {
			 String sql = "select distinct(oi.order_no), oi.cust_no, li.partner_com_name, oi.cust_type, oi.investor_no, oi.id_no, oi.order_amount, pi.prod_name, oi.mail_address, oi.work_address, oi.cust_address from order_info oi, product_info pi, call_order_rel cl, call_distrib cd, lp_info li where cl.order_no = oi.order_no and cl.call_distrib_id = cd.id and  pi.prod_id = oi.prod_no and oi.order_no = ? and cd.service_id =? and cl.status=1 and li.lp_id = oi.part_comp";
			 return jt.queryForList(sql, order_no, service_id);
		 }
		 

			/**
			 * 获取所有已下单的产品信息
			 */
			
			public List<Map<String, Object>> getProdInfoExistOrder() {
				String sql = "select distinct(oi.prod_no), pi.prod_name from order_info oi, product_info pi where oi.prod_no = pi.prod_id";
				return jt.queryForList(sql);
			}
			
			/**
			 * 获取客户订单信息
			 */
			
			public List<Map<String, Object>> getCustOrderList(String prod_no, String order_no, String cust_name) {
				String sql = "select oi.order_no, u.real_name, ci.cust_name, pi.prod_name from order_info oi, cust_info ci, sales_cust_rel s, upm_user u, product_info pi where oi.cust_no = ci.cust_id and oi.cust_type = '1' and u.user_id = s.sales_id and ci.cust_id = s.cust_id and pi.prod_id = oi.prod_no";
				if(prod_no != null && !"".equals(prod_no)) {
					sql += " and oi.prod_no="+Long.parseLong(prod_no);
				}
				
				if(order_no != null && !"".equals(order_no)) {
					sql += " and oi.order_no="+Long.parseLong(order_no);
				}
				
				if(cust_name != null && !"".equals(cust_name)) {
					sql += " and ci.cust_name='"+cust_name+"'";
				}
				return jt.queryForList(sql);
			}
			/**
			 * 获取机构订单信息
			 */
			public List<Map<String, Object>> getOrgOrderList(String prod_no, String order_no, String cust_name) {
				String sql = "select oi.order_no, o.org_name as cust_name, pi.prod_name, u.real_name from order_info oi, org_info o, product_info pi, upm_user u, cust_info ci, sales_cust_rel sl where u.user_id = sl.sales_id and ci.cust_id = sl.cust_id and ci.cust_id = oi.investor_no and oi.cust_type = '2' and pi.prod_id = oi.prod_no and o.org_id = oi.cust_no";
				if(prod_no != null && !"".equals(prod_no)) {
					sql += " and oi.prod_no="+Long.parseLong(prod_no);
				}
				
				if(order_no != null && !"".equals(order_no)) {
					sql += " and oi.order_no="+Long.parseLong(order_no);
				}
				
				if(cust_name != null && !"".equals(cust_name)) {
					sql += " and o.org_name='"+cust_name+"'";
				}
				return jt.queryForList(sql);
			}
			
			/**
			 * 根据产品名称获取订单和产品信息
			 */
			
			public List<Map<String, Object>> getOrderListByProdNo(String prod_no, String order_no) {
				String sql = "select pi.prod_name, oi.cust_type, oi.cust_no, oi.order_no, oi.investor_no from order_info oi, product_info pi where oi.prod_no = pi.prod_id";
				if(prod_no != null && !"".equals(prod_no)) {
					sql += " and oi.prod_no="+Long.parseLong(prod_no);
				}
				
				if(order_no != null && !"".equals(order_no)) {
					sql += " and oi.order_no="+Long.parseLong(order_no);
				}
				return jt.queryForList(sql);
			}
			
			/**
			 * 插入客户录音记录
			 */
			public void insertCustRecord(Long user_id, String real_name, Long order_no, String record_id) {
				String sql = "INSERT INTO cust_record(cust_record_id, order_no, record_id, c_user_id, c_user_name, c_time)VALUES (?, ?, ?, ?, ?, current_date);";
				jt.update(sql, jdbcUtil.seq(), order_no, record_id, user_id, real_name);
			}
			
			/**
			 * 客户录音记录
			 */
			
			public List<Map<String, Object>> getCallRecordByOrdeno(Long order_no) {
				String sql = "select record_id from cust_record where order_no = ?";
				return jt.queryForList(sql, order_no);
			}
			
			/**
			 * 根据用户id客户录音列表
			 */
			public List<Map<String, Object>> getCallRecordList(Long user_id, String order_no, int pageSize, int offset) {
				String sql = "select distinct(cr.order_no), pi.prod_name, ci.cust_name, o.org_name, u.real_name from cust_record cr left join order_info oi on oi.order_no = cr.order_no left join product_info pi on pi.prod_id = oi.prod_no left join cust_info ci on oi.cust_no = ci.cust_id left join org_info o on o.org_id = oi.cust_no left join sales_cust_rel sc on sc.cust_id = ci.cust_id left join upm_user u on u.user_id = sc.sales_id where cr.c_user_id = ?";
				if(order_no == null || !"".equals(order_no)) {
					sql += " and cr.order_no="+Long.parseLong(order_no);
				}
				sql += " limit "+pageSize+" OFFSET "+offset;
				return jt.queryForList(sql, user_id);
			}
			
			/**
			 * 根据用户id客户录音列表数量
			 */
			
			public List<Map<String, Object>> getCallRecordCount(Long user_id, String order_no) {
				String sql = "select distinct(order_no) from cust_record cr where cr.c_user_id = ?";
				if(order_no == null || !"".equals(order_no)) {
					sql += " and cr.order_no="+Long.parseLong(order_no);
				}
				return jt.queryForList(sql, user_id);
			}
			
			/**
			 * 获取客户录音列表
			 */
			
			public List<Map<String, Object>> getAllCallRecordList(String order_no, int pageSize, int offset) {
				String sql = "select distinct(cr.order_no), pi.prod_name, ci.cust_name, o.org_name, u.real_name from cust_record cr left join order_info oi on oi.order_no = cr.order_no left join product_info pi on pi.prod_id = oi.prod_no left join cust_info ci on oi.cust_no = ci.cust_id left join org_info o on o.org_id = oi.cust_no left join sales_cust_rel sc on sc.cust_id = ci.cust_id left join upm_user u on u.user_id = sc.sales_id";
				if(order_no == null || !"".equals(order_no)) {
					sql += " where cr.order_no="+Long.parseLong(order_no);
				}
				sql += " limit "+pageSize+" OFFSET "+offset;
				return jt.queryForList(sql);
			}
			
			/**
			 * 获取客户录音列表数量
			 */
			
			public List<Map<String, Object>> getAllCallRecordCount(String order_no) {
				String sql = "select distinct(order_no) from cust_record";
				if(order_no == null || !"".equals(order_no)) {
					sql += " where order_no="+Long.parseLong(order_no);
				}
				return jt.queryForList(sql);
			}
		}