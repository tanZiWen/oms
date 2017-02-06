package com.prosnav.oms.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.CustDao;
import com.prosnav.oms.dao.GpDao;
import com.prosnav.oms.dao.OrderDao;
import com.prosnav.oms.dao.OrgDao;
import com.prosnav.oms.dao.userDao;
import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.util.Download;
import com.prosnav.oms.util.mailCache;
import com.prosnav.oms.util.sendMail;

/**
 * 机构客户
 * 
 * @author Fu
 *
 */
public class OrgAction extends HttpAction {
	private String org_type = "";
	private String org_code_cert = "";
	private String org_license = "";
	private String org_legal = "";
	private String taxid = "";
	private String reg_capital = "";
	private String reg_address = "";
	private Date reg_time = null;
	private String org_id = "";
	private static String org_name="";
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}

	public void setOrg_code_cert(String org_code_cert) {
		this.org_code_cert = org_code_cert;
	}

	public void setOrg_license(String org_license) {
		this.org_license = org_license;
	}

	public void setOrg_legal(String org_legal) {
		this.org_legal = org_legal;
	}

	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}

	public void setReg_capital(String reg_capital) {
		this.reg_capital = reg_capital;
	}

	public void setReg_address(String reg_address) {
		this.reg_address = reg_address;
	}

	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	
	public String run() {
		String result = "";
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user==null){
			return "index";
		}
		long role_id = user.getRole_id();
		req.setAttribute("role_id", role_id);
		try {
			OrgDao org = new OrgDao();
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			int listcount = 0;
			long user_id = user.get_id();
			if(role_id==1){
				list = org.sale_inquiry(0, 10, user_id);
				listcount = org.sale_inquiryCount(user_id);
			}else if(role_id==2){
				list = org.team_sale_inquiry(0, 10, user_id);
				listcount = org.team_sale_inquiryCount(user_id);
			}else if(role_id==3){
				list = org.org_team_sale_inquiry(0, 10, user_id);
				listcount = org.org_team_inquiryCount(user_id);
			}else if(role_id==4){
				list = org.inquiry(0,10);
				listcount = org.inquiryCount();
			}else if(role_id==5){
				list = org.inquiry(0,10);
				listcount = org.inquiryCount();
			}
			
			req.setAttribute("totalNum", listcount);
			req.setAttribute("PageNum", "1");
			req.setAttribute("PageSize","10" );
			if (list.size() > 0 && list != null) {
				req.setAttribute("list", list);
				req.setAttribute("status", "1");

			} else {
				req.setAttribute("status", "2");
				req.setAttribute("org_detail", "未找到相关数据");
			}

			result = "success";

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "获取数据失败");
			req.setAttribute("code", "104");
			result = "error";

		}
		return result;
	}
	 /**
	  * 分页查询
	  */
	public void pagequery(){
		JSONObject json = new JSONObject();
		try{
		User user = (User) req.getSession().getAttribute(Constants.USER);
		int PageNum = Integer.parseInt(req.getParameter("PageNum"));
		int n = Integer.parseInt(req.getParameter("PageSize"));
		int m = (PageNum-1)*n;
		OrgDao org = new OrgDao();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();//org.inquiry(m,n);
		long user_id = user.get_id();
		long role_id = user.getRole_id();
		if(role_id==1){
			list = org.sale_inquiry(m,n, user_id);
		}else if(role_id==2){
			list = org.team_sale_inquiry(m,n, user_id);
		}else if(role_id==3){
			list = org.org_team_sale_inquiry(m,n, user_id);
		}else if(role_id==4){
			list = org.inquiry(m,n);
		}else if(role_id==5){
			list = org.inquiry(m,n);
		}
		if (list.size() > 0 && list != null) {
			json.put("list", list);
			json.put("pagenum", PageNum);
			json.put("totalNum", req.getParameter("count"));
			json.put("status", "1");

		} else {
			json.put("status", "2");
			json.put("org_detail", "未找到相关数据");
		}
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().println(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询机构名称
	 */
	public void org_select() {
		JSONObject json = new JSONObject();
		try{
		//org_name=req.getParameter("org_name");
		User user = (User) req.getSession().getAttribute(Constants.USER);
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		int PageNum = Integer.parseInt(req.getParameter("PageNum"));
		int n = Integer.parseInt(req.getParameter("PageSize"));
		int m = (PageNum-1)*n;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int count =0;
		OrgDao org = new OrgDao();
		if(role_id==1){
			list = org.org_select_sale(m, n, user_id, org_name, org_license);
			count = org.org_select_sale_count( user_id, org_name, org_license);
		}else if(role_id==2){
			list = org.org_select_team(m, n, user_id, org_name, org_license);
			count = org.org_select_team_count(user_id, org_name, org_license);
		}else if(role_id==3){
			list = org.org_select_lead(m, n, user_id, org_name, org_license);
			count = org.org_select_lead_count(user_id, org_name, org_license);
		}else if(role_id==4){
			list = org.org_select(m,n,org_name,org_license);
			count = org.org_selectCount(org_name, org_license);
		}else if(role_id==5){
			list = org.org_select(m,n,org_name,org_license);
			count = org.org_selectCount(org_name, org_license);
		}
		
		if (list.size() > 0 && list != null) {
			json.put("list", list);
			json.put("totalNum", count);
			json.put("PageNum", PageNum);
			json.put("status", "1");

		} else {
			json.put("status", "2");
			json.put("org_detail", "未找到相关数据");
		}
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().println(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	/**
	  * 机构名称分页查询
	  */
	public void pagequeryorg(){
		JSONObject json = new JSONObject();
		try{
		//org_name=req.getParameter("org_name");
		User user = (User) req.getSession().getAttribute(Constants.USER);
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		int PageNum = Integer.parseInt(req.getParameter("PageNum"));
		int n = Integer.parseInt(req.getParameter("PageSize"));
		int m = (PageNum-1)*n;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int count =0;
		OrgDao org = new OrgDao();
		if(role_id==1){
			list = org.org_select_sale(m, n, user_id, org_name, org_license);
			count = org.org_select_sale_count( user_id, org_name, org_license);
		}else if(role_id==2){
			list = org.org_select_team(m, n, user_id, org_name, org_license);
			count = org.org_select_team_count(user_id, org_name, org_license);
		}else if(role_id==3){
			list = org.org_select_lead(m, n, user_id, org_name, org_license);
			count = org.org_select_lead_count(user_id, org_name, org_license);
		}else if(role_id==4){
			list = org.org_select(m,n,org_name,org_license);
			count = org.org_selectCount(org_name, org_license);
		}else if(role_id==5){
			list = org.org_select(m,n,org_name,org_license);
			count = org.org_selectCount(org_name, org_license);
		}
		
		if (list.size() > 0 && list != null) {
			json.put("list", list);
			json.put("count", count);
			json.put("status", "1");

		} else {
			json.put("status", "2");
			json.put("org_detail", "未找到相关数据");
		}
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().println(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 详细信息查询
	 * 
	 * @return
	 */
	public String detail() {
		String result = "";
        User user = (User) req.getSession().getAttribute(Constants.USER);
		
		long role_id = user.getRole_id();
		req.setAttribute("role_id", role_id);
		long org_id = Long.parseLong(req.getParameter("id"));
		try {
			OrgDao org = new OrgDao();
			List<Map<String, Object>> list = org.detail(org_id);
			List<Map<String, Object>> list1 = org.detail1(org_id);
			List<Map<String, Object>> recodelist = org.recode(org_id);
			if (list != null && list.size() > 0) {
				req.setAttribute("org_detail1", list1);
				req.setAttribute("status1", "1");
			} else {
				req.setAttribute("status1", "2");
				req.setAttribute("org_detail1", "未找到相关数据");
			}
			if (list != null && list.size() > 0) {
				req.setAttribute("org_detail", list.get(0));
				req.setAttribute("status", "1");

			} else {
				req.setAttribute("status", "2");
				req.setAttribute("org_detail", "未找到相关数据");
			}
			if(recodelist!=null&&recodelist.size()>0){
				req.setAttribute("status2", "1");
				req.setAttribute("recodelist", recodelist);
			}else {
				req.setAttribute("status2", "2");
				req.setAttribute("recodelist", "未找到相关数据");
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		return result;

	}

	/**
	 * 跳转至新建页面
	 * 
	 * @return
	 */
	public String skip() {

		return "success";
	}

	/**
	 * 新增机构
	 * 
	 * @throws ParseException
	 * 
	 */
	public void addOrg() {
		//JSONObject json = new JSONObject();

		try {
			String obj = req.getParameter("data");
			User user = (User) req.getSession().getAttribute(Constants.USER);
			JSONObject json = JSONObject.parseObject(obj);
			List<Map<String, Object>> list = (List<Map<String, Object>>) json.get("data");
			
			// String org_members = req.getParameter("org_members");
			
			OrgDao org = new OrgDao();
			String org_license = (String) list.get(list.size()-1).get("org_license");
			List<Map<String, Object>> repeatlist = org.org_license(org_license);
			if(repeatlist.size()>0&&repeatlist!=null){
				json.put("msg", "营业执照号重复，不可添加");
				json.put("desc", "2");
			}else{
				org.addorg(list,user);
				//org.addorg(org_name, org_type, org_license, org_legal, taxid, org_code_cert, reg_capital, reg_address,
					//	reg_date, opera_period, reg_time, state, sales_name, cust_name, samount, proportion, cust_id, check);
				json.put("msg", "添加成功");
				json.put("desc", "1");
			}
			

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void org_edit() {
		try {
			JSONObject js = new JSONObject();	
			String org_type = req.getParameter("org_type");
			String org_code_cert = req.getParameter("org_code_cert");
			String reg_capital = req.getParameter("reg_capital");
			String reg_address = req.getParameter("reg_address");
			String taxid = req.getParameter("taxid");
			String opera_period = req.getParameter("opera_period");
			String reg_time = req.getParameter("reg_time");
			String orgid = req.getParameter("org_id");
			long org_id = Long.parseLong(orgid);
			OrgDao org = new OrgDao();
			org.saveOrg(org_type,org_code_cert,reg_capital,reg_address,taxid,
					opera_period,reg_time,org_id);
			js.put("msg", "更新成功");
			js.put("desc", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(js);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 更新机构基本信息 后提交审批
	 * 
	 * @throws ParseException
	 * 
	 */
	public void org_submit() {
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			String obj = req.getParameter("data");
			String status ="0";
			String message = "";
			JSONObject json = JSONObject.parseObject(obj);
			List<Map<String, Object>> list = (List<Map<String, Object>>) json.get("data");
			JSONObject js = new JSONObject();	
			OrgDao org = new OrgDao();
			String orgid = (String) list.get(list.size()-1).get("org_id");
			long org_id= Long.parseLong(orgid);
			List<Map<String, Object>> org_list = org.org_compare(org_id);
			if(org_list.size()>0&&org_list!=null){
				String org_name =(String) list.get(list.size()-1).get("org_name");
				String dborg_name =(String) org_list.get(0).get("org_name");
				String org_license =(String) list.get(list.size()-1).get("org_license");
				String dborg_license =(String) org_list.get(0).get("org_license");
				String org_legal =(String) list.get(list.size()-1).get("org_legal");
				String dborg_legal =(String) org_list.get(0).get("org_legal");
				if(!org_name.equals(dborg_name)){
					status="1";//可以提交审批
					
					
					message += "【机构名称】："+dborg_name+"		【机构名称】："+org_name+"\n";	 
				}
				if(!org_license.equals(dborg_license)){
					status="1";//可以提交审批
					List<Map<String, Object>> license_list = org.org_license(org_license);
					if(license_list.size()>0&&license_list!=null){
						status="2";
						js.put("msg", "营业执照号重复不得提交审批");
					}
					message += "【营业执照注册号】："+dborg_license+"		【营业执照注册号】："+org_license+"\n";
				}
				if(!org_legal.equals(dborg_legal)){
					status="1";//可以提交审批
					message += "【法人】："+dborg_legal+"		【法人】："+org_legal+"\n";
				}
				
			}
			for (int i = 0; i < list.size() - 1; i++) {
				double sub_amount = 0;
				long cust_id = Long.parseLong((String)list.get(i).get("cust_id"));
				List<Map<String, Object>> org_cust_list = org.org_rel_compare(org_id, cust_id);
				String subamount = (String)list.get(i).get("sub_amount");
				if(!"".equals(subamount)){
					sub_amount = Double.parseDouble(subamount);
				}
				
				double dbsub_amount = Double.parseDouble(org_cust_list.get(0).get("sub_amount").toString()) ;
				String proport = (String) list.get(i).get("proport");
				String dbproport = (String) org_cust_list.get(0).get("proport");
				if(!proport.equals(dbproport)){
					status="1";//可以提交审批
					message += "【合伙人"+(String)org_cust_list.get(i).get("partner")+"的销售"+(String)org_cust_list.get(i).get("match_rm")+"的占比】："+dbproport+
							"		【合伙人"+(String)org_cust_list.get(i).get("partner")+"的销售"+(String)org_cust_list.get(i).get("match_rm")+"的占比】："+proport+"\n";
				}
				if(sub_amount!=dbsub_amount){
					status="1";
					message += "【合伙人"+(String)org_cust_list.get(i).get("partner")+"的销售"+(String)org_cust_list.get(i).get("match_rm")+"认购金额】："+dbsub_amount+
							"		【合伙人"+(String)org_cust_list.get(i).get("partner")+"的销售"+(String)org_cust_list.get(i).get("match_rm")+"认购金额】："+sub_amount+"\n";
				}
			}
			//List<Map<String, Object>> org_cust_list = org.org_rel_compare(org_id, cust_id);
			if("1".equals(status)){
				List<Map<String, Object>> orglist = org.org_task(org_id);
				org.submitOrg(list);
				js.put("msg", "更新成功");
				//发送邮件
				boolean flag = false;
				if("1".equals((String) list.get(list.size()-1).get("state"))){
					flag=true;
				}
				userDao udao = new userDao();
				List<Map<String, Object>> userlist = udao.getTeamUser(user.get_id());
				String email = "";
				String real_name = "";
				if (userlist != null && userlist.size() > 0) {
					email = (String) userlist.get(0).get("email");
					real_name = (String) userlist.get(0).get("real_name");
				}
				SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");
				SentMailInfoBean sentmsg = new SentMailInfoBean();
				sentmsg.setSubjectId(""+org_id);
				sentmsg.setSentMailaddr(mailCache.from);
				sentmsg.setReviceMailaddr(email);
				sentmsg.setMail_busstype("org审批");
				sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.mailorg");
				sentmsg.setMailContent("机构：					\n"
						+ "尊敬的"+real_name+"： 					\n"
						+ "您好， "+sdfg.format(new Date())+","+user.getRealName()+"在OMS系统更改了机构，现等待您审批。\n"
						+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
						+ "---InfoBegin---					\n"
						+ "变更前信息：		变更后信息：	\n"
						+message
						+ "---InfoEnd--- \n"					
		);
				sentmsg.setSubject("修改org审批");
				sendMail.sendMessage(sentmsg, true);
				//将数据存入task表中
				JSONObject taskjson = new JSONObject();
				taskjson.put("orglist", orglist);
				userDao us = new userDao();
				us.addtask(taskjson, org_id, "org更改提交审批", "org",user.get_id());
			}
			js.put("desc", status);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(js);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新机构基本信息 后提交审批通过
	 * 
	 * @throws ParseException
	 * 
	 */
	public void org_pass() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String orgid = req.getParameter("org_id");
		long org_id = Long.parseLong(orgid);
		try {

			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			OrgDao org = new OrgDao();
			org.org_pass(org_id);
			js.put("msg", "审批已通过");
			js.put("desc", "1");
			resp.getWriter().println(js);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 更新机构基本信息 后提交审批不通过
	 * 
	 * @throws ParseException
	 * 
	 */
	public void org_nopass() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String orgid = req.getParameter("org_id");
		long org_id = Long.parseLong(orgid);
		try {

			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			OrgDao org = new OrgDao();
			org.org_nopass(org_id);
			js.put("msg", "审批不通过");
			js.put("desc", "1");
			resp.getWriter().println(js);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 查询合伙人
	 */
	public void queryorg() {
		JSONObject json = new JSONObject();

		try {
			String condition = req.getParameter("condition");
			OrgDao org = new OrgDao();
			List<Map<String, Object>> list = org.queryorg(condition);
			if (list != null && list.size() > 0) {
				json.put("list", list.get(0));
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
/**
 * 报表导出
 */
	public void org_report() {
		try{
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
			String remoteFilePath ="/home/oms/org_report.xls";
			// 输出Excel
			File fileWrite = new File(remoteFilePath);
			SqlRowSet rs =null;
			
			if(!fileWrite.exists())
			fileWrite.createNewFile();
			OutputStream os = new FileOutputStream(fileWrite);
			OrgDao org = new OrgDao();
			if (role_id == 1) {// 销售
				rs =org.reprot_org_sale(user_id);
			} else if (role_id == 2) {
				rs = org.reprot_team_sale(user_id);
			} else if (role_id == 3) {
				rs = org.reprot_org_sale(user_id);
			} else if (role_id == 4) {
				rs = org.reprot();
				
			} else if(role_id == 5){
				rs = org.reprot();
				
			}
			// rep.export_report();
			String[] o ={"序号","合伙人","机构名称","营业执照注册号号","法人","成立日期","成员人数","客户状态","销售","认购金额","占比"};
			Download.writeExcel(os, rs,o);
			Download.downloadFile(remoteFilePath, resp);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 重置
	 */
	public void org_reset(){
		try {
			JSONObject json = new JSONObject();
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long org_id = Long.parseLong(req.getParameter("org_id"));
			GpDao gp = new GpDao();
			List<Map<String, Object>> list = gp.gp_reset(org_id,user.get_id());
			if(list.size()>0&&list!=null){
				Object content = (Object) list.get(0).get("content");
				JSONObject jsonob = JSONObject.parseObject(content.toString());
				List<Map<String, Object>> org_list = (List<Map<String, Object>>) jsonob.get("orglist");
				OrgDao org = new OrgDao();
				org.org_reset(org_list);
				//gp.gpreset(jsonob, org_id);
				//json.put("content", jsonob);
				json.put("desc", "1");
			}else{
				json.put("desc", "2");
			}
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
