package com.prosnav.oms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
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

public class OrgDao {
	private JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");

	
	/**
	 * 更新机构基本信息后提交审批
	 * @param org_submit
	 * @return
	 */
	public void org_submit(long id) {
		String sql = "UPDATE public.org_info "
				+ "SET state='2' WHERE  org_id=?";
		
		jt.update(sql, id);

	}
	/**
	 * 机构基本信息审批通过
	 * @param org_submit
	 * @return
	 */
	public void org_pass(long org_id) {
		String sql = "UPDATE public.org_info "
				+ "SET state='3' WHERE state='2' and org_id=?";
		
		jt.update(sql, org_id);

	}
	/**
	 * 机构基本信息审批不通过
	 * @param org_submit
	 * @return
	 */
	public void org_nopass(long org_id) {
		String sql = "UPDATE public.org_info "
				+ "SET state='4' WHERE state='2' and org_id=?";
		
		jt.update(sql, org_id);

	}
	/**
	 * 机构邮件审批
	 * @param org_id
	 */
	public void org_approve(long org_id,String state) {
		String sql = "UPDATE public.org_info "
				+ "SET state=? WHERE  org_id=?";
		
		jt.update(sql,state, org_id);

	}
	/**
	 * 机构列表总数查询
	 * @return
	 */
	public int inquiryCount(){
		String sql ="select count(*) from org_info";
		return jt.queryForInt(sql);
	}
	/**
	 * 机构查询分公司职能条数
	 * @param user_id
	 * @return
	 */
	public int org_team_inquiryCount(long user_id){
		String sql ="select count(*) from org_info t1 "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,org_code from upm_user where user_id=?) b  "
				+ "where a.org_code=b.org_code)";
		return jt.queryForInt(sql,user_id);
	}
	
	/**
	 * 机构查询团队职能条数
	 * @param user_id
	 * @return
	 */
	public int team_sale_inquiryCount(long user_id){
		String sql ="select count(*) from org_info t1 "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id in (select a.user_id from upm_user a,"
				+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
				+ "where a.workgroup_id=b.workgroup_id)";
		return jt.queryForInt(sql,user_id);
	}
	/**
	 * 机构查询销售条数
	 * @param user_id
	 * @return
	 */
	public int sale_inquiryCount(long user_id){
		String sql ="select count(*) from org_info t1 "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id =?";
		return jt.queryForInt(sql,user_id);
	}
	/**
	 * 机构列表查询(运营操作)
	 * 
	 * @return
	 */
	public List<Map<String, Object>> inquiry(int m,int n) {
		String sql = "select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' limit ? OFFSET ?";
		List<Map<String, Object>> list = jt.queryForList(sql,n,m);
		return list;
	}
	
	/**
	 * 销售个人机构列表查询
	 * 
	 * @return
	 */
	public List<Map<String, Object>> sale_inquiry(int m,int n,long user_id) {
		String sql = "select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id=? limit ? OFFSET ?";
		List<Map<String, Object>> list = jt.queryForList(sql,user_id,n,m);
		return list;
	}
	
	/**
	 * 销售团队长机构列表查询
	 * 
	 * @return
	 */
	public List<Map<String, Object>> team_sale_inquiry(int m,int n,long user_id) {
		String sql = "select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
				+ "where a.workgroup_id=b.workgroup_id)"
				+ " limit ? OFFSET ?";
		List<Map<String, Object>> list = jt.queryForList(sql,user_id,n,m);
		return list;
	}
	
	/**
	 * 销售分公司职能机构列表查询
	 * 
	 * @return
	 */
	public List<Map<String, Object>> org_team_sale_inquiry(int m,int n,long user_id) {
		String sql = "select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,org_code from upm_user where user_id=?) b  "
				+ "where a.org_code=b.org_code)"
				+ " limit ? OFFSET ?";
		List<Map<String, Object>> list = jt.queryForList(sql,user_id,n,m);
		return list;
	}

	/**
	 * 根据id查询详情
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> detail(long id) {
		String sql = "SELECT o.org_id, o.org_name, o.org_type, o.org_license, o.org_legal, "
				+ "o.taxid, o.org_code_cert, o.reg_capital, o.reg_address, o.reg_date, o.opera_period,"
				+ " o.reg_time, o.state,  o.org_members, t2.dict_name state_name " + "FROM org_info o  "
				+ " left join data_dict t2 on o.state=t2.dict_value and t2.dict_type='state' "
				+ " where o.org_id=? ";

		return jt.queryForList(sql, id);
	}
	
	/**
	 * 根据id查询成交记录
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> recode(long id) {
		String sql = "SELECT t2.area, t2.part_comp, t2.contract_type, t2.contract_no, t2.is_checked, t2.stateflag, "
				+ "t2.order_amount, t2.pri_fee, t2.acount_fee, t2.create_time,t2.prod_diffcoe, "
				+ "t2.order_version, t2.order_type, t2.order_no, t2.cust_no, t2.prod_no, "
				+ "t2.email_id,t3.sales_point, t3.stateflag, t3.order_no, t3.sales_id, t3.magt_fee, t3.sales_name, "
				+ "t3.order_version, t3.email_id, t3.cost_model,t4.prod_id, t4.prod_name, t4.prod_type,"
				+ "t1.org_name,"
				+ "t5.dict_name as order_state_name, "
				+ "t6.dict_name as area_name, "
				+ "t7.dict_name as prod_type_name  "
				+ "FROM public.order_info t2  "
				+ "inner JOIN public.sale_order t3 on t2.order_no = t3.order_no  "
				+ "LEFT JOIN public.product_info t4 on t2.prod_no = t4.prod_id  "
				+ "LEFT JOIN public.org_info t1 on t2.cust_no = t1.org_id  "
				+ "LEFT JOIN data_dict t5 on t5.dict_type = 'ord_flag' and t5.dict_value = t2.is_checked  "
				+ "LEFT JOIN data_dict t6 on t6.dict_type = 'dist' and t6.dict_value = t2.area  "
				+ "LEFT JOIN data_dict t7 on t7.dict_type = 'prodType' and t7.dict_value = t4.prod_type "
				+ "where t1.org_id= ?";

		return jt.queryForList(sql, id);
	}

	public List<Map<String, Object>> detail1(long id) {
		String sql = "SELECT o.org_id," + "oc.cust_id,oc.partner,oc.match_RM,oc.sub_amount,oc.proport "
				+ "FROM org_info o,org_cust_rel oc where o.org_id=oc.org_id and o.org_id=? ";
		return jt.queryForList(sql, id);
	}

	/**
	 * 查询
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> queryorg(String condition) {
		String sql = "SELECT t1.sales_name,t2.cust_name,t1.cust_id,t1.sales_id FROM sales_cust_rel t1,cust_info t2 "
				+ " where t1.cust_id=t2.cust_id and t1.cust_id=(SELECT c.cust_id FROM cust_info c where c.cust_cell=? or c.cust_name like ?)";
		return jt.queryForList(sql, condition, '%' + condition + '%');
	}

	/**
	 * 数据插入
	 * 
	 * @param org_name
	 * @param org_type
	 * @param org_license
	 * @param org_legal
	 * @param taxid
	 * @param org_code_cert
	 * @param reg_capital
	 * @param reg_address
	 * @param reg_date
	 * @param opera_period
	 * @param reg_time
	 * @param state
	 * @param org_members
	 * @param sales_name
	 * @param cust_name
	 * @param samount
	 * @param proportion
	 * @return
	 * @throws ParseException
	 */
	public void addorg(List<Map<String,Object>> list,User user) throws Exception {
		List<Object[]> orgList = new ArrayList<Object[]>();
		String org_name = (String) list.get(list.size()-1).get("org_name");
		String org_type = (String) list.get(list.size()-1).get("org_type");
		String org_license = (String) list.get(list.size()-1).get("org_license");
		String org_legal = (String) list.get(list.size()-1).get("org_legal");
		double taxid = 0;
		if (!"".equals(list.get(list.size()-1).get("taxid"))) {
			taxid = Double.parseDouble((String)list.get(list.size()-1).get("taxid"));
		}
		String org_code_cert = (String) list.get(list.size()-1).get("org_code_cert");
		String reg_capital = (String) list.get(list.size()-1).get("reg_capital");
		String reg_address = (String) list.get(list.size()-1).get("reg_address");
		String reg_date = (String) list.get(list.size()-1).get("reg_date");
		String opera_period = (String) list.get(list.size()-1).get("opera_period");
		String state = (String) list.get(list.size()-1).get("state");
		int members = list.size();
		long org_id = jdbcUtil.seq();
		//String reg_time = (String) list.get(list.size()).get("reg_time");
		
		//String state = (String) list.get(list.size()).get("state");
		Object[] o = new Object[] { org_name, org_type, org_license, org_legal, taxid, org_code_cert, reg_capital,
				reg_address,org_id,  reg_date, opera_period,members,state };
		String sql = "";
		sql = "INSERT INTO public.org_info(org_name, org_type, org_license, org_legal, taxid, org_code_cert, "
				+ "reg_capital, reg_address,    org_id,reg_date,opera_period,org_members,state)"
				+ " VALUES (?, ?, ?, ?, ?, ?,  ?, ?, ?,?,?,?,?)";

		 //int org_id = insertAndGetKey(sql, o, "org_id");
		
		 
		 
		addCourse(sql, o, "org_id");

		String sql1 = "INSERT INTO public.org_cust_rel"
				+ "(org_id, cust_id, partner, match_rm, sub_amount, proport,sales_id,email_id)"
				+ "VALUES (?, ?, ?, ?, ?, ?,?,?)";
		String org_partner = "";
		for (int i = 0; i < list.size() - 1; i++) {
			double sub_amount = 0;
			if(!"".equals(list.get(i).get("sub_amount"))){
				sub_amount=Double.parseDouble((String)list.get(i).get("sub_amount"));
			}
			org_partner=org_partner+"【合伙人】:"+list.get(i).get("cust_name")+"【指定销售】："+list.get(i).get("sales_name")+"\n";
			Object[] e = { org_id,
					Long.parseLong((String)list.get(i).get("cust_id")),
					list.get(i).get("cust_name"),
					list.get(i).get("sales_name"),
					sub_amount,
					list.get(i).get("proport") ,
					Long.parseLong((String)list.get(i).get("sales_id")),
					jdbcUtil.seq()
					 };
			orgList.add(e);
		}
		jt.batchUpdate(sql1, orgList);
		//添加机构到签单表
		String billsql = "INSERT INTO public.bill_info"
				+ "(id, cust_type, effect_sign, email_id) "
				+ "VALUES (?, ?, ?, ?);";
		jt.update(billsql,org_id,'2','0',jdbcUtil.seq());
		
		if("2".equals(state)){
			String email="";
			String real_name = "";
			userDao udao = new userDao();
			List<Map<String, Object>> userlist =udao.getTeamUser(user.get_id());
			if(userlist!=null&&userlist.size()>0){
				email=(String) userlist.get(0).get("email");
				real_name =(String) userlist.get(0).get("real_name");
			}
			SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SentMailInfoBean sentmsg = new SentMailInfoBean();
			//sentmsg.setFirmId("001");
			sentmsg.setSubjectId(""+org_id);
			sentmsg.setSentMailaddr(mailCache.from);
			sentmsg.setReviceMailaddr(email);
			sentmsg.setMail_busstype("新建机构审批");
			sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.mailorg");
			sentmsg.setMailContent("创建机构：					\n"
					+ "尊敬的"+real_name+"： 					\n"
					+ "您好， "+sdfg.format(new Date())+","+user.getRealName()+"（员工姓名）在OMS系统创建了新机构，现等待您审批。\n"
					+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
					+ "---InfoBegin---					\n"
					+ "【公司名称】：	"+org_name+"\n"
					+ "【营业执照注册号】：	"+org_license+"\n"
					+ "【法人】：	"+org_legal+"\n"
					+ org_partner
					+ "---InfoEnd--- \n"					
	);
			sentmsg.setSubject("新建机构审批");
			sendMail.sendMessage(sentmsg, true);
		}
	}
/**
 * 保存机构
 * @param list
 */
	public void saveOrg(String org_type,String org_code_cert,String reg_capital,
			String reg_address,String taxid,String opera_period,String reg_time,long org_id) {
		String sql = "UPDATE public.org_info " 
				+ "SET org_type=?, org_code_cert=?,reg_capital=?,"
				+ "reg_address=?,taxid=? ,opera_period=?,reg_time=? "
				+ " WHERE org_id=?";
		jt.update(sql, org_type, org_code_cert, reg_capital, 
				reg_address, taxid,opera_period,reg_time, 
				org_id);
	}
	/**
	 * 机构提交审批
	 * @param list
	 */
		public void submitOrg(List<Map<String, Object>> list) {
			int size = list.size() - 1;
			List<Object[]> orgList = new ArrayList<Object[]>();
			String sql = "UPDATE public.org_info " 
					+ "SET org_name=?, org_type=?, org_license=?, org_legal=?, "
					+ "org_code_cert=?, reg_capital=?, reg_address=?,  " 
					+ "reg_time=?,  taxid=?,state=? " 
					+ " WHERE org_id=?";
			String org_name = (String) list.get(size).get("org_name");
			String org_type = (String) list.get(size).get("org_type");
			String org_license = (String) list.get(size).get("org_license");
			String org_legal = (String) list.get(size).get("org_legal");
			String org_code_cert = (String) list.get(size).get("org_code_cert");
			String reg_capital = (String) list.get(size).get("reg_capital");
			String reg_address = (String) list.get(size).get("reg_address");
			String reg_time = (String) list.get(size).get("reg_time");
			String taxid = (String) list.get(size).get("taxid");
			long org_id = Long.parseLong((String) list.get(size).get("org_id"));
			jt.update(sql,org_name, org_type, org_license, org_legal,
					org_code_cert, reg_capital, reg_address, 
					reg_time, taxid,"2",
					org_id);
			for (int i = 0; i < list.size() - 1; i++) {
				double sub_amount = 0;
				if(list.get(i).get("sub_amount")!=""){
					sub_amount=Double.parseDouble((String)list.get(i).get("sub_amount"));
				}
				Object[] e = { sub_amount, list.get(i).get("proport"), org_id,
						Long.parseLong((String)list.get(i).get("cust_id")) };
				orgList.add(e);
			}
			String sql1 = "UPDATE public.org_cust_rel SET   sub_amount=?, proport=? WHERE org_id=? and cust_id=? ";
			jt.batchUpdate(sql1, orgList);

		}

	/**
	 * 查询机构名称(全量)
	 * 
	 * @param org_name
	 * @return
	 */
	public List<Map<String, Object>> org_select(int m,int n,String org_name,String org_license) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ " where  1=1 ");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		sql.append(" limit ? OFFSET ? ");
		 
		return jt.queryForList(sql.toString(),n,m);
	}
	
	
	/**
	 * 查询机构名称(销售个人)
	 * 
	 * @param org_name
	 * @return
	 */
	public List<Map<String, Object>> org_select_sale(int m,int n,long user_id,String org_name,String org_license) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id= "+user_id+"  ");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		sql.append(" limit ? OFFSET ? ");
		 
		return jt.queryForList(sql.toString(),n,m);
	}
	/**
	 * 查询机构名称(销售个人)数量
	 * 
	 * @param org_name
	 * @return
	 */
	public int org_select_sale_count(long user_id,String org_name,String org_license) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select count(*) from org_info t1 "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id= "+user_id+"  ");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		 
		return jt.queryForInt(sql.toString());
	}
	/**
	 * 查询机构名称(销售团队)
	 * 
	 * @param org_name
	 * @return
	 */
	public List<Map<String, Object>> org_select_team(int m,int n,long user_id,String org_name,String org_license) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,workgroup_id from upm_user where user_id="+user_id+") b  "
				+ "where a.workgroup_id=b.workgroup_id) ");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		sql.append(" limit ? OFFSET ? ");
		 
		return jt.queryForList(sql.toString(),n,m);
	}
	/**
	 * 查询机构名称(销售团队)数量
	 * 
	 * @param org_name
	 * @return
	 */
	public int org_select_team_count(long user_id,String org_name,String org_license) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select count(*) from org_info t1 "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,workgroup_id from upm_user where user_id="+user_id+") b  "
				+ "where a.workgroup_id=b.workgroup_id) ");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		 
		return jt.queryForInt(sql.toString());
	}
	/**
	 * 查询机构名称(销售大区领导)
	 * 
	 * @param org_name
	 * @return
	 */
	public List<Map<String, Object>> org_select_lead(int m,int n,long user_id,String org_name,String org_license) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,org_code from upm_user where user_id="+user_id+") b  "
				+ "where a.org_code=b.org_code)");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		sql.append(" limit ? OFFSET ? ");
		 
		return jt.queryForList(sql.toString(),n,m);
	}
	/**
	 * 查询机构名称(销售大区领导)数量
	 * 
	 * @param org_name
	 * @return
	 */
	public int org_select_lead_count(long user_id,String org_name,String org_license) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select count(*) from org_info t1 "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id "
				+ "where t3.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,org_code from upm_user where user_id="+user_id+") b  "
				+ "where a.org_code=b.org_code)");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		 
		return jt.queryForInt(sql.toString());
	}
	/**
	 * 查询机构名称总数
	 * @param org_name
	 * @return
	 */
	public int org_selectCount(String org_name,String org_license){
		StringBuffer sql = new StringBuffer();

		sql.append("select count(*) from org_info where  1=1 ");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		int count = jt.queryForInt(sql.toString());
		return count;
	}

	
	/**
	 * 查询销售名下的机构名称
	 * 
	 * @param org_name
	 * @return
	 */
	public List<Map<String, Object>> sale_org_select(int m,int n,String org_name,String org_license,long user_id) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id"
				+ " where  t3.sales_id=? ");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		sql.append(" limit ? OFFSET ? ");
		 
		return jt.queryForList(sql.toString(),user_id,n,m);
	}
	
	
	/**
	 * 查询销售团队名下的机构名称
	 * 
	 * @param org_name
	 * @return
	 *//*
	public List<Map<String, Object>> team_sale_org_select(int m,int n,String org_name,String org_license,long user_id) {
		
		StringBuffer sql = new StringBuffer();

		sql.append("select t1.org_id,t1.org_name,t1.org_license,t1.org_legal,"
				+ "t1.reg_date,t1.org_members,t1.state,t2.dict_name state_name from org_info t1"
				+ " left join data_dict t2 on t1.state=t2.dict_value and t2.dict_type='state' "
				+ "left join org_cust_rel t3 on  t3.org_id=t1.org_id"
				+ " where  t3.sales_id in(select a.user_id from upm_user a,"
				+ "(select user_id,workgroup_id from upm_user where user_id= '"+user_id+"') b  "
				+ "where a.workgroup_id=b.workgroup_id) ");//nm
				sql.append("");				
		if (!"".equals(org_name) && org_name != null) {
			sql.append(" and t1.org_name like '%"+org_name+"%' ");
		}
		if (!"".equals(org_license) && org_license != null) {
			sql.append(" and t1.org_license like '%"+org_license+"%' ");
			//argsList.add();
		}
		sql.append(" limit ? OFFSET ? ");
		 
		return jt.queryForList(sql.toString(),n,m);
	}*/
	
	public long addCourse(String sql, Object[] o, String id) {
		final String innersql = sql;
		final Object[] innerO = o;
		KeyHolder keyHolder = new GeneratedKeyHolder();

		// int projectid = jt.update(sql);

		try {
			jt.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = jt.getDataSource().getConnection().prepareStatement(innersql,
							Statement.RETURN_GENERATED_KEYS);
					for (int i = 0; i < innerO.length; i++) {
						ps.setObject(i + 1, innerO[i]);
					}

					return ps;
				}
			}, keyHolder);
		} catch (DataAccessException e) {

			e.printStackTrace();
		}
		Map<String, Object> generatedId = keyHolder.getKeyList().get(0);
		long o_id = (Long) generatedId.get(id);
		// System.out.println("自动插入id============================" +
		// keyHolder.getKey().intValue());
		return o_id;

	}

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
		Integer o_id = (Integer) generatedId.get(id);
		// Long generatedId = keyHolder.getKey().longValue();
		// int a =Integer.parseInt(o_id);
		return o_id;
	}
	/**
	 * 机构报表导出销售个人
	 * @return
	 */
	public SqlRowSet reprot_sale(long user_id){
		String sql="SELECT row_number() OVER () as rownum,  partner,org_name, org_license, org_legal,"
				+ "reg_date,org_members,t3.dict_name,  "
				+ "match_rm, sub_amount, proport "
				+ "FROM public.org_info t1 "
				+ "right join org_cust_rel t2 on t1.org_id = t2.org_id "
				+ "left join data_dict t3 on t1.state = t3.dict_value and t3.dict_type='state' "
				+ "where t2.sales_id=? order by rownum";
		return jt.queryForRowSet(sql,user_id);
	}
	/**
	 * 机构报表导出销售个人
	 * @param user_id
	 * @return(select a.user_id from upm_user a,"
				+ "(select user_id,org_code from upm_user where user_id=?) b  "
				+ "where a.org_code=b.org_code)"
	 */
	public SqlRowSet reprot_team_sale(long user_id){
		String sql="SELECT row_number() OVER () as rownum, partner,org_name, org_license, org_legal,"
				+ "reg_date,org_members,t3.dict_name,  "
				+ "match_rm, sub_amount, proport "
				+ "FROM public.org_info t1 "
				+ "right join org_cust_rel t2 on t1.org_id = t2.org_id "
				+ "left join data_dict t3 on t1.state = t3.dict_value and t3.dict_type='state' "
				+ "where t2.sales_id in (select a.user_id from upm_user a,"
				+ "(select user_id,workgroup_id from upm_user where user_id=?) b  "
				+ "where a.workgroup_id=b.workgroup_id) order by rownum";
		return jt.queryForRowSet(sql,user_id);
	}
	/**
	 * 分公司报表导出
	 * @param user_id
	 * @return
	 */
	public SqlRowSet reprot_org_sale(long user_id){
		String sql="SELECT row_number() OVER () as rownum, partner,org_name, org_license, org_legal,"
				+ "reg_date,org_members,t3.dict_name,  "
				+ "match_rm, sub_amount, proport "
				+ "FROM public.org_info t1 "
				+ "right join org_cust_rel t2 on t1.org_id = t2.org_id "
				+ "left join data_dict t3 on t1.state = t3.dict_value and t3.dict_type='state' "
				+ "where t2.sales_id in (select a.user_id from upm_user a,"
				+ "(select user_id,org_code from upm_user where user_id=?) b  "
				+ "where a.org_code=b.org_code) order by rownum";
		return jt.queryForRowSet(sql,user_id);
	}
	/**
	 * 导出机构报表全量
	 * @return
	 */
	public SqlRowSet reprot(){
		String sql="SELECT row_number() OVER () as rownum, partner,org_name, org_license, org_legal,"
				+ "reg_date,org_members,t3.dict_name,  "
				+ "match_rm, sub_amount, proport "
				+ "FROM public.org_info t1 "
				+ "right join org_cust_rel t2 on t1.org_id = t2.org_id "
				+ "left join data_dict t3 on t1.state = t3.dict_value and t3.dict_type='state' "
				+ "order by rownum";
		return jt.queryForRowSet(sql);
	}
	/**
	 * 比较机构info表的数据
	 * 
	 * @param org_id
	 * @return
	 */
	public List<Map<String, Object>> org_compare(long org_id){
		String sql = "select org_name,org_license,org_legal from org_info where org_id=?";
		return jt.queryForList(sql,org_id);
	}
	/**
	 * 比较机构客户关系表的数据
	 * @param org_id
	 * @param cust_id
	 * @return
	 */
	public List<Map<String, Object>> org_rel_compare(long org_id,long cust_id){
		String sql = "select match_rm,partner,sub_amount,proport from org_cust_rel where org_id=? and cust_id=?";
		return jt.queryForList(sql,org_id,cust_id);
	}
	/**
	 * 查询是否有重复的org_license
	 * @param org_license
	 * @return
	 */
	public List<Map<String, Object>> org_license(String org_license){
		String sql = "select org_name from org_info where org_license=?";
		return jt.queryForList(sql,org_license);
	}
	/**
	 * 查询变更前数据
	 * @param org_id
	 * @return
	 */
	public List<Map<String, Object>> org_task(long org_id){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String orgsql="SELECT t1.org_id, t1.org_name, t1.org_type, t1.org_license, t1.org_legal, t1.org_code_cert, "
				+ "t1.reg_capital, t1.reg_address, t1.org_members, t1.taxid, t1.reg_date, "
				+ "t1.opera_period, t1.reg_time,t2.sub_amount,t2.proport,t2.cust_id FROM public.org_info t1 "
				+ "left join org_cust_rel t2 on t1.org_id=t2.org_id "
				+ "where t1.org_id=?";
		list = jt.queryForList(orgsql,org_id);
		return list;
		
	}
	/**
	 * 重置机构数据
	 * @param org_id
	 * @param content
	 */
	public void org_reset(List<Map<String, Object>> list) {
		int size = 0;
		List<Object[]> orgList = new ArrayList<Object[]>();
		String sql = "UPDATE public.org_info " 
				+ "SET   org_type=?, org_license=?, org_legal=?, "
				+ "org_code_cert=?, reg_capital=?, reg_address=?,  " 
				+ "reg_time=?,  taxid=? " 
				+ " WHERE org_id=?";
		String org_type = (String) list.get(size).get("org_type");
		String org_license = (String) list.get(size).get("org_license");
		String org_legal = (String) list.get(size).get("org_legal");
		String org_code_cert = (String) list.get(size).get("org_code_cert");
		String reg_capital = (String) list.get(size).get("reg_capital");
		String reg_address = (String) list.get(size).get("reg_address");
		String reg_time = (String) list.get(size).get("reg_time");
		String taxid = (String) list.get(size).get("taxid");
		long org_id =  (Long) list.get(size).get("org_id");
		jt.update(sql, org_type, org_license, org_legal,
				org_code_cert, reg_capital, reg_address, 
				reg_time, taxid,
				org_id);
		for (int i = 0; i < list.size() ; i++) {
			double sub_amount = Double.parseDouble( list.get(i).get("sub_amount").toString());
			
			Object[] e = { sub_amount, list.get(i).get("proport"), org_id,
					list.get(i).get("cust_id") };
			orgList.add(e);
		}
		String sql1 = "UPDATE public.org_cust_rel SET sub_amount=?, proport=? WHERE org_id=? and cust_id=? ";
		jt.batchUpdate(sql1, orgList);

	}
	
	/**
	 * 根据机构id获取机构信息
	 */
	public List<Map<String, Object>> getOrgById(Long id) {
		String sql = "select org_name, org_license, org_legal from org_info where org_id=?";
		return jt.queryForList(sql, id);
	}
}
