package com.prosnav.oms.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.management.monitor.StringMonitorMBean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.util.jdbcUtil;
import com.prosnav.oms.util.mailCache;
import com.prosnav.oms.util.sendMail;

public class GpDao {
	private JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");
	/**
	 * 新建jp
	 * @param gp_id
	 * @param gp_name
	 * @param gp_dept
	 * @param bus_license
	 * @param legal_resp
	 * @param fund_no
	 * @param open_bank
	 * @param bank_account
	 * @param regit_addr
	 * @return
	 */
	public void  addGp( String gp_name, String gp_dept, String bus_license, 
			String legal_resp, String fund_no, String open_bank, String bank_account,
			String regit_addr, String status,User user){
		long gp_id = jdbcUtil.seq();
		String sql = "INSERT INTO public.gp_info(gp_id, gp_name, gp_dept, bus_license, "
				+ "legal_resp, fund_no, open_bank, bank_account, regit_addr,status,user_id)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		jt.update(sql,gp_id, gp_name, gp_dept, bus_license, 
				legal_resp, fund_no, open_bank, bank_account, regit_addr,status,user.get_id());
		if("3".equals(status)){
			userDao udao = new userDao();
			List<Map<String, Object>> userlist =udao.getTeamUser(user.get_id());
			String email="";
			String real_name = "";
			if(userlist!=null&&userlist.size()>0){
				email=(String) userlist.get(0).get("email");
				real_name =(String) userlist.get(0).get("real_name");
			}
			SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SentMailInfoBean sentmsg = new SentMailInfoBean();
			//sentmsg.setFirmId("001");
			sentmsg.setSubjectId(""+gp_id);
			sentmsg.setSentMailaddr(mailCache.from);
			sentmsg.setReviceMailaddr(email);
			sentmsg.setCcAddress(user.getEmail());
			sentmsg.setMail_busstype("新建gp审批");
			sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.mailgp");
			sentmsg.setMailContent("创建gp：					\n"
					+ "尊敬的"+real_name+"： 					\n"
					+ "您好， "+sdfg.format(new Date())+","+user.getRealName()+"在OMS系统创建了新订单，现等待您审批。\n"
					+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
					+ "---InfoBegin---					\n"
					+ "【GP名称】：	"+gp_name+"\n"
					+ "【营业执照注册号】：	"+bus_license+"\n"
					+ "【法定代表人】：	"+legal_resp+"\n"
					+ "【基金业协会备案号】：	"+fund_no+"\n"
					+ "【开户行】：	"+open_bank+"\n"
					+ "【账号】：	"+bank_account+"\n"
					+ "---InfoEnd--- \n"					
	);
			sentmsg.setSubject("新建gp审批");
			sendMail.sendMessage(sentmsg, true);
		}
		
	}
	
	
	public void edit(long gp_id , String open_bank, String bank_account, String regit_addr,String gp_remark,
			String status,String message,User user){
		String sql ="UPDATE public.gp_info SET   "
				+ "  open_bank=?, bank_account=?, regit_addr=?,  gp_remark=? "
				+ "WHERE gp_id=?";
		jt.update(sql, open_bank, bank_account, regit_addr,gp_remark,gp_id);
		
	}
	/**
	 * 查询gp列表
	 * @return
	 */
	public List<Map<String, Object>> gp_select(int m,int n) {
		String sql = "SELECT t1.gp_id, t1.gp_name, t1.gp_dept, t1.bus_license, t1.legal_resp, t1.fund_no, "
				+ "t1.open_bank, t1.bank_account, t1.regit_addr, t1.lp, t1.gp_remark,t2.dict_name status_name "
				+ " FROM gp_info t1 "
				+ " left join data_dict t2 on t1.status=t2.dict_value and t2.dict_type='prodChe' "
				
				+ " limit ? OFFSET ?";
		List<Map<String, Object>> list = jt.queryForList(sql,n,m);
		return list;
	}
	
	public int gp_selectCount() {
		String sql = "SELECT count(*)"
				+ " FROM gp_info ";
		int count = jt.queryForInt(sql);
		return count;
	}
	//gp name分页查询总数
	public int gp_select_fenye_Count(String gp_name,String gp_dept) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(*)"
				+ " FROM gp_info where  1=1 ");//nm
				sql.append("");
				
		if (!"".equals(gp_name) && gp_name != null) {
			sql.append(" and gp_name like '%"+gp_name+"%' ");
			//argsList.add();
		}
		if (!"".equals(gp_dept) && gp_dept != null) {
			sql.append(" and gp_dept like '%"+gp_dept+"%' ");
			//argsList.add();
		}
		
		int count = jt.queryForInt(sql.toString());
		return count;
	}
	/**
	 * 根据id查询详情
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> detail(long id) {
		String sql = "SELECT t1.gp_id, t1.gp_name, t1.gp_dept, t1.bus_license, t1.legal_resp, t1.fund_no, "
				+ "t1.open_bank, t1.bank_account, t1.regit_addr, t1.lp, t1.gp_remark,t2.dict_name status_name,t1.status "
				+ " FROM gp_info t1 "
				+ " left join data_dict t2 on t1.status=t2.dict_value and t2.dict_type='prodChe' "
				+ " where gp_id=?";

		return jt.queryForList(sql, id);
	}
	
	
	/**
	 * 更新gp基本信息后提交审批
	 * @param org_submit
	 * @return
	 */
	public void gp_submit(long gp_id ,String gp_name, String gp_dept, String bus_license, 
			String legal_resp, String fund_no, String open_bank, String bank_account, String regit_addr,String gp_remark,
			String status,String message,User user,boolean state) {
		
		String sql ="UPDATE public.gp_info SET  gp_name=?, gp_dept=?, "
				+ "bus_license=?, legal_resp=?, fund_no=?,  open_bank=?,"
				+ " bank_account=?, regit_addr=?,  gp_remark=? ,status=?"
				+ "WHERE gp_id=?";
		jt.update(sql,gp_name, gp_dept, bus_license, 
				legal_resp, fund_no, open_bank, bank_account, regit_addr,gp_remark,status,gp_id);
		userDao udao = new userDao();
		List<Map<String, Object>> userlist =udao.getTeamUser(user.get_id());
		String email="";
		String real_name = "";
		if(userlist!=null&&userlist.size()>0){
			email=(String) userlist.get(0).get("email");
			real_name =(String) userlist.get(0).get("real_name");
		}
		SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");
		SentMailInfoBean sentmsg = new SentMailInfoBean();
		//sentmsg.setFirmId("001");
		sentmsg.setSubjectId(""+gp_id);
		sentmsg.setSentMailaddr(mailCache.from);
		sentmsg.setReviceMailaddr(email);
		sentmsg.setCcAddress(user.getEmail());
		sentmsg.setMail_busstype("gp审批");
		sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.mailgp");
		sentmsg.setMailContent("gp：					\n"
				+ "尊敬的"+real_name+"： 					\n"
				+ "您好， "+sdfg.format(new Date())+","+user.getRealName()+"在OMS系统更改了gp，现等待您审批。\n"
				+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
				+ "---InfoBegin---					\n"
				+ "变更前信息：				变更后信息：	\n"
				+message
				+ "---InfoEnd--- \n"					
);
		sentmsg.setSubject("更改gp审批");
		sendMail.sendMessage(sentmsg, true);
		

	}
	/**
	 * gp基本信息审批通过
	 * @param org_submit
	 * @return
	 */
	public void gp_pass(long gp_id) {
		String sql = "UPDATE public.gp_info  SET status='2'  WHERE status='3' and gp_id=?";
		
		jt.update(sql, gp_id);

	}
	/**
	 * gp基本信息审批不通过
	 * @param org_submit
	 * @return
	 */
	public void gp_nopass(long gp_id) {
		String sql = "UPDATE public.gp_info  SET status='1'  WHERE status='3' and gp_id=?";
		
		jt.update(sql, gp_id);

	}
	/**
	 * 查询gp名称
	 * 
	 * @param org_name
	 * @return
	 */
	public List<Map<String, Object>> GP_select(String gp_name,String gp_dept,int m,int n) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT t1.gp_id, t1.gp_name, t1.gp_dept, t1.bus_license, t1.legal_resp, t1.fund_no, "
				+ "t1.open_bank, t1.bank_account, t1.regit_addr, t1.lp, t1.gp_remark,t2.dict_name status_name "
				+ " FROM gp_info t1 "
				+ " left join data_dict t2 on t1.status=t2.dict_value and t2.dict_type='prodChe' "
				+ "  where  1=1 ");//nm
				sql.append("");
				
		if (!"".equals(gp_name) && gp_name != null) {
			sql.append(" and t1.gp_name like '%"+gp_name+"%' ");
			//argsList.add();
		}
		if (!"".equals(gp_dept) && gp_dept != null) {
			sql.append(" and t1.gp_dept like '%"+gp_dept+"%' ");
			//argsList.add();
		}
		sql.append(" limit ? OFFSET ? ");
		 
		return jt.queryForList(sql.toString(),n,m);
	}
/**
 * 邮件审批gp
 * @param gp_id
 * @param state
 */
	public void gp_approve(long gp_id,String state){
		String sql = "UPDATE public.gp_info SET status=? WHERE gp_id=?";
		jt.update(sql,state,gp_id);
	}
	/**
	 * 报表导出excel(全量)
	 * @return
	 */
	public SqlRowSet gp_report_leader(){
		String sql = "SELECT distinct row_number() OVER () as rownum, t1.gp_name, t1.gp_dept, t1.bus_license, t1.legal_resp, "
				+ "t1.fund_no, t1.open_bank,t1.bank_account, t1.regit_addr, t1.gp_remark, t2.dict_name  "
				+ "FROM public.gp_info t1  "
				+ "LEFT JOIN data_dict t2 on t1.status = t2.dict_value and t2.dict_type='prodChe'  "
				+ "order by rownum";
		return jt.queryForRowSet(sql);
	}
	/**
	 * 报表导出excel(个人录入)
	 * @return
	 */
	public SqlRowSet gp_report_opera(long user_id){
		String sql = "SELECT distinct row_number() OVER () as rownum, t1.gp_name, t1.gp_dept, t1.bus_license, t1.legal_resp, "
				+ "t1.fund_no, t1.open_bank,t1.bank_account, t1.regit_addr, t1.gp_remark, t2.dict_name  "
				+ "FROM public.gp_info t1 "
				+ " LEFT JOIN data_dict t2 on t1.status = t2.dict_value and t2.dict_type='prodChe'  "
				+ "left join upm_user t3 on t1.user_id=t3.user_id   "
				+ "where t1.user_id = "+user_id+"  "
				+ "order by rownum";
		return jt.queryForRowSet(sql);
	}
	
	/**
	 * 重置
	 * @param gp_id
	 * @return
	 */
	public List<Map<String, Object>> gp_reset(long gp_id,long user_id){
		String sql ="select content from task "
				+ "where version =(select max(version) from task where id=? and user_id=?  group by id)";
		return jt.queryForList(sql,gp_id,user_id);
	}
	/**
	 * 重置数据到数据库
	 * @param json
	 * @param gp_id
	 */
	public void gpreset(JSONObject json ,long gp_id){
		String sql="UPDATE public.gp_info SET  gp_name=?, gp_dept=?, bus_license=?, legal_resp=?, fund_no=?, "
				+ "open_bank=?, bank_account=?, regit_addr=?  WHERE gp_id=? ";
		jt.update(sql,json.get("gp_name"),json.get("gp_dept"),json.get("bus_license"),json.get("legal_resp"),
				json.get("fund_no"),json.get("open_bank"),json.get("bank_account"),json.get("regit_addr"),gp_id);
	}
}
