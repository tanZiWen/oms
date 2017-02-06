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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.inject.util.Function;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.CustDao;
import com.prosnav.oms.dao.DictDao;
import com.prosnav.oms.dao.GpDao;
import com.prosnav.oms.dao.OrgDao;
import com.prosnav.oms.dao.userDao;
import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.model.DictReference;
import com.prosnav.oms.util.Download;
import com.prosnav.oms.util.Escape;
import com.prosnav.oms.util.jdbcUtil;
import com.prosnav.oms.util.mailCache;
import com.prosnav.oms.util.sendMail;
import com.sun.xml.internal.org.jvnet.fastinfoset.VocabularyApplicationData;

import tab.MyPhoner;

/**
 * 个人客户
 * 
 * @author qinzw
 *
 */
public class CustAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private String comp_name;
	private String comp_type;
	private String license;
	private String legal;
	private String taxid;
	private String org_code_cert;
	private double reg_capital;
	private String reg_address;
	private String reg_date;
	private String opera_period;
	private String family_name;
	private String reg_time;
	private String textarea;
	static String state;
	static String cust_name;
	static String cust_cell;
	static String sales_name;
	static String cust_level;
	private MyPhoner phoner;
	 // 封装上传文件域的属性
    private File filename;
    
	public File getFilename() {
		return filename;
	}

	public void setFilename(File filename) {
		this.filename = filename;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}

	public void setTextarea(String textarea) {
		this.textarea = textarea;
	}

	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}

	public void setComp_type(String comp_type) {
		this.comp_type = comp_type;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}

	public void setOrg_code_cert(String org_code_cert) {
		this.org_code_cert = org_code_cert;
	}

	public void setReg_capital(double reg_capital) {
		this.reg_capital = reg_capital;
	}

	public void setReg_address(String reg_address) {
		this.reg_address = reg_address;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public void setOpera_period(String opera_period) {
		this.opera_period = opera_period;
	}
	
	
	public String run() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		String result = "";
		req.setAttribute("role_id", role_id);
		try {
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listcount = new ArrayList<Map<String, Object>>();
			if(role_id==1){//销售本人
				list = cust.inquiry_sale(0, 10,user_id);
				listcount = cust.inquiry_saleCount(user_id);
				
			}else if(role_id==2){//团队长及其队员
				list = cust.inquiry_leader(0, 10, user_id);
				hide_secret_prop(list, user);
				listcount = cust.inquiry_leaderCount(user_id);
				
			}else if(role_id==3){//分公司领导
				list = cust.inquiry(0, 10, user_id);
				hide_secret_prop(list, user);
				listcount = cust.inquiryCount(user_id);
				
			}else if(role_id==4){//运营操作
				list = cust.inquiryAll(0, 10);
				listcount = cust.inquiryCountAll();
				
			}
			else if(role_id==5){//运营领导
				list = cust.inquiryAll(0, 10);
				listcount = cust.inquiryCountAll();
				
			}
			if(listcount != null && listcount.size() > 0){
			req.setAttribute("totalNum", listcount.get(0).get("count"));
			}
			req.setAttribute("PageNum", "1");
			req.setAttribute("PageSize", "10");
			if (list.size() > 0 && list != null) {
				req.setAttribute("list", list);
				req.setAttribute("cust_detail", list);
				req.setAttribute("status", "1");

			} else {
				req.setAttribute("status", "2");
				req.setAttribute("cust_detail", "未找到相关数据");
			}
			
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			result = "error";
		}
		return result;
	}

	/**
	 * 点页码分页查询
	 */
	public void pagequery() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
				
		}
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		JSONObject json = new JSONObject();
		List<Map<String, Object>> listcount = new ArrayList<Map<String, Object>>();
		try {
			int PageNum = Integer.parseInt(req.getParameter("PageNum"));
			int n = Integer.parseInt(req.getParameter("PageSize"));
			int m = (PageNum - 1) * n;
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if(role_id==1){//销售本人
				list = cust.inquiry_sale(m, 10,user_id);
				listcount = cust.inquiry_saleCount(user_id);
				
			}else if(role_id==2){//团队长及其队员
				list = cust.inquiry_leader(m, 10,user_id);
				hide_secret_prop(list, user);
				listcount = cust.inquiry_leaderCount(user_id);
				
			}else if(role_id==3){//分公司领导
				list = cust.inquiry(m, 10,user_id);
				hide_secret_prop(list, user);
				listcount = cust.inquiryCount(user_id);
				
			}else if(role_id==4){//运营操作
				list = cust.inquiryAll(m, 10);
				listcount = cust.inquiryCountAll();
				
			}
			else if(role_id==5){//运营领导
				list = cust.inquiryAll(m, 10);
				listcount = cust.inquiryCountAll();
				
			}
			if (list.size() > 0 && list != null) {
				json.put("list", list);
				json.put("pagenum", PageNum);
				json.put("totalNum", listcount.get(0).get("count"));
				json.put("status", "1");

			} else {
				json.put("status", "2");
				json.put("cust_detail", "未找到相关数据");
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
	 * 详细信息查询
	 * 
	 * @return
	 */

	public String detail() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		String result = "";
		req.setAttribute("role_id", role_id);
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		long cust_id = Long.parseLong(req.getParameter("cust_id").toString());
		String cust_name = req.getParameter("cust_name");
		cust_name = Escape.unescape(cust_name);
		req.setAttribute("cust_name", cust_name);
		String flag = req.getParameter("flag");
		String level = "level";
		String sex = "sex";
		String idtype = "idtype";
		Object[] o = { level, sex, idtype };
		
		DictDao dict = new DictDao();
		for (int i = 0; i < o.length; i++) {
			List<Map<String, Object>> list = dict.queryDict(o[i].toString());
			if (list.size() > 0) {
				req.setAttribute("list" + i, list);
			} else {
				req.setAttribute("list" + i, "未找到");
			}
		}

		CustDao cust = new CustDao();
		try {
			
			// 个人客户详情
			if ("1".equals(flag)) {
				/*if(role_id==1){//销售本人
					result = "sale_cust";
				}else if(role_id==2){//团队长及其队员
					result = "teamleader_cust";				
				}else if(role_id==3){//分公司领导
					result = "teamleader_cust";
				}else if(role_id==4){//运营操作
					result = "opera_cust";
				}else if(role_id==5){//运营领导
					result = "teamleader_cust";
				}*/
				List<Map<String, Object>> list = cust.detail(cust_id);
				List<Map<String, Object>> list1 = cust.detaillist(cust_id);
				if(role_id==2 || role_id==3){//销售本人
					hide_secret_prop(list1, user);
				}
				List<Map<String, Object>> listOrder = cust.detailOrderlist(cust_id);
				if (list != null && list.size() > 0) {
					req.setAttribute("cust_detail", list);
					req.setAttribute("status", "1");

				} else {
					req.setAttribute("status", "2");
					req.setAttribute("cust_detail", "未找到相关数据");
				}
				if (list1 != null && list1.size() > 0) {
					req.setAttribute("cust_detail1", list1.get(0));
					// session.setAttribute("cust_id",
					// list1.get(0).get("cust_id"));
					req.setAttribute("status1", "1");
				} else {
					req.setAttribute("status1", "2");
					req.setAttribute("cust_detail", "未找到相关数据");
				}
				if (listOrder != null && listOrder.size() > 0) {
					req.setAttribute("custOrder", listOrder);
					req.setAttribute("status10", "1");
				} else {
					req.setAttribute("status10", "2");
					req.setAttribute("custOrder", "未找到相关数据");
				}
				req.setAttribute("cust_name", cust_name);
				
				result = "cust";
			}

			// 公司详情
			if ("3".equals(flag)) {
				/*if(role_id==1){//销售本人
					result = "sale_comp";
				}else if(role_id==2){//团队长及其队员
					result = "leader_comp";				
				}else if(role_id==3){//分公司领导
					result = "leader_comp";
				}else if(role_id==4){//运营操作
					result = "leader_comp";
				}else if(role_id==5){//运营领导
					result = "leader_comp";
				}*/
				List<Map<String, Object>> list2 = cust.detail_com(cust_id);
				List<Map<String, Object>> list6 = cust.detail_com1(cust_id);
				List<Map<String, Object>> list7 = cust.detail_com2(cust_id);
				/* String comp_id = req.getParameter("comp_id"); */

				if (list2 != null && list2.size() > 0) {
					req.setAttribute("comp_detail", list2.get(0));
					req.setAttribute("status2", "1");
				} else {
					/* PrintWriter out */
					req.setAttribute("status2", "2");
					req.setAttribute("comp_detail", "未找到相关数据");
					/*
					 * req.setAttribute("msg", "该客户还未创建公司"); return "error";
					 */
					/*
					 * out = resp.getWriter();
					 * out.print("<script>alert('该客户还未创建公司')</script>");
					 * out.print("<script>location.href="+
					 * "company_detail.action?cust_id="+cust_id+"&flag=3"+
					 * ";</script>"); out.close();
					 */
				}
				if (list6 != null && list6.size() > 0) {
					req.setAttribute("comp_detail1", list6);
					req.setAttribute("status6", "1");
				} else {
					req.setAttribute("status6", "2");
					req.setAttribute("comp_detail1", "未找到相关数据");

				}
				long comp_id = 0;
				if (list7 != null && list7.size() > 0) {
					comp_id = Long.parseLong(list7.get(0).get("comp_id").toString());

					req.setAttribute("comp_detail2", list7.get(0));

					req.setAttribute("status7", "1");

				} else {
					req.setAttribute("status7", "2");
					req.setAttribute("comp_detail2", "未找到相关数据");
				}
				req.setAttribute("cust_id", cust_id);
				req.setAttribute("comp_id", comp_id);

				result = "comp";
			}

			// 家族详情
			if ("2".equals(flag)) {
				/*if(role_id==1){//销售本人
					result = "sale_family";
				}else if(role_id==2){//团队长及其队员
					result = "leader_family";				
				}else if(role_id==3){//分公司领导
					result = "leader_family";
				}else if(role_id==4){//运营操作
					result = "opera_family";
				}else if(role_id==5){//运营领导
					result = "leader_family";
				}*/
				List<Map<String, Object>> list3 = cust.detail_fam(cust_id);
				List<Map<String, Object>> list4 = cust.detail_fam1(cust_id);
				List<Map<String, Object>> list8 = cust.detail_fam2(cust_id);

				if (list3 != null && list3.size() > 0) {

					for (int i = 0; i < o.length; i++) {
						List<Map<String, Object>> list5 = dict.queryDict(o[i].toString());
						if (list5.size() > 0) {
							req.setAttribute("family_cust_name", list3.get(0).get("family_cust_name"));

							req.setAttribute("famliy_detail", list3.get(0));
							req.setAttribute("status3", "1");
							req.setAttribute("list5" + i, list5);
						} else {
							req.setAttribute("list5" + i, "未找到");
						}
					}

				} else {
					for (int i = 0; i < o.length; i++) {
						List<Map<String, Object>> list5 = dict.queryDict(o[i].toString());
						if (list5.size() > 0) {
							req.setAttribute("status3", "2");
							req.setAttribute("famliy_detail", "未找到相关数据");
							req.setAttribute("list5" + i, list5);
						} else {
							req.setAttribute("list5" + i, "未找到");
						}
					}
					/* PrintWriter out; */
					/*req.setAttribute("status3", "2");
					req.setAttribute("famliy_detail", "未找到相关数据");*/
					/*
					 * req.setAttribute("msg", "该客户还未创建家族"); return "error";
					 */
					/*
					 * out = resp.getWriter();
					 * out.print("<script>alert('该客户还未创建家族')</script>");
					 * out.print(
					 * "<script>location.href='/OMS/family_detail.action'</script>"
					 * ); out.close();
					 */
				}
				if (list4 != null && list4.size() > 0) {
					req.setAttribute("famliy_detail1", list4);
					req.setAttribute("status4", "1");

				} else {
					req.setAttribute("status4", "2");
					req.setAttribute("famliy_detail1", "未找到相关数据");
				}

				long family_id1 = 0;
				long member_id = 0;
				if (list8 != null && list8.size() > 0) {
					family_id1 = Long.parseLong(list8.get(0).get("family_id").toString());
					member_id = Long.parseLong(list8.get(0).get("member_id").toString());
					req.setAttribute("famliy_detail2", list8);
					req.setAttribute("status8", "1");

				} else {
					req.setAttribute("status8", "2");
					req.setAttribute("famliy_detail2", "未找到相关数据");
				}
				req.setAttribute("family_id1", family_id1);
				req.setAttribute("member_id", member_id);
				req.setAttribute("cust_id", cust_id);
				req.setAttribute("cust_name", cust_name);
				
				result = "family";
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("code", "104");
			req.setAttribute("msg", "数据查询异常");
			req.setAttribute("url", "cust.action");
			result = "error";
		}
		return result;
	}

	/**
	 * 个人客户 家族成员异步刷新
	 * 
	 * @author qinzw
	 *
	 */
	public void show_detail() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			// HttpSession session = req.getSession();
			// int cust_id = (Integer) session.getAttribute("cust_id");
			long family_id = Long.parseLong(req.getParameter("family_id"));
			long member_id = Long.parseLong(req.getParameter("member_id"));
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = cust.show_detail(family_id, member_id);
			if(list != null && list.size() > 0){
			json.put("list", list.get(0));
			}
			json.put("desc", "1");
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

	/**
	 * 公司异步列表刷新
	 */
	public void compList() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			// HttpSession session = req.getSession();
			// int cust_id = (Integer) session.getAttribute("cust_id");
			long cust_id = Long.parseLong(req.getParameter("cust_id"));
			long comp_id = Long.parseLong(req.getParameter("comp_id"));
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = cust.compList(cust_id, comp_id);
			// List<Map<String, Object>> list =
			// cust.show_detail(cust_id,family_id);
			if(list != null && list.size() > 0){
			json.put("list", list.get(0));
			}
			json.put("desc", "1");
			req.setAttribute("cust_id1", cust_id);
			req.setAttribute("comp_id", comp_id);
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
	
	
	/**
	 * 查询，刷新列表
	 */
	public void cust_search() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
				
		}
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			state = req.getParameter("state");
			cust_name = req.getParameter("cust_name");
			cust_cell = req.getParameter("cust_cell");
			sales_name = req.getParameter("sales_name");
			cust_level = req.getParameter("cust_level");
			int PageNum = Integer.parseInt(req.getParameter("PageNum"));
			int n = Integer.parseInt(req.getParameter("PageSize"));
			int m = (PageNum - 1) * n;
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			int count = 0;
			if(role_id==1){//销售本人
				list = cust.sale_cust_select(state, cust_name, cust_cell, user_id, sales_name, cust_level,m, 10);
				count = cust.sale_cust_select1(state, cust_name, cust_cell, user_id, sales_name, cust_level);
				
			}else if(role_id==2){//团队长及其队员
				list = cust.team_cust_select(user_id, state, cust_name, cust_cell, sales_name, cust_level,m, 10);
				hide_secret_prop(list, user);
				count = cust.team_cust_select1(user_id, state, cust_name, cust_cell, sales_name, cust_level);
				
			}else if(role_id==3){//分公司领导
				list = cust.leader_cust_select(user_id, state, cust_name, cust_cell, sales_name, cust_level,m, 10);
				hide_secret_prop(list, user);
				count = cust.leader_cust_select1(user_id, state, cust_name, cust_cell, sales_name, cust_level);
				
			}else if(role_id==4){//运营操作
				list = cust.all_cust_select(state, cust_name, cust_cell, sales_name, cust_level,m, 10);
				count = cust.all_cust_select1(state, cust_name, cust_cell, sales_name, cust_level);
				
			}
			else if(role_id==5){//运营领导
				list = cust.all_cust_select(state, cust_name, cust_cell, sales_name, cust_level,m, 10);
				count = cust.all_cust_select1(state, cust_name, cust_cell, sales_name, cust_level);
				
			}
			
			if (list.size() > 0 && list != null) {
				req.setAttribute("list", list);

			}
			if (list != null && list.size() > 0) {
				json.put("list", list);
				json.put("state", state);
				json.put("totalNum", count);
				json.put("PageNum", PageNum);
				json.put("PageSize", "10");
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
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
	 * 索引点页码分页查询
	 */
	public void pagequeryselect() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			
		}
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		JSONObject json = new JSONObject();
		try {
			int PageNum = Integer.parseInt(req.getParameter("PageNum"));
			int n = Integer.parseInt(req.getParameter("PageSize"));
			int m = (PageNum - 1) * n;
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if(role_id==1){//销售本人
				list = cust.sale_cust_select(state, cust_name, cust_cell, user_id, sales_name, cust_level,m, 10);
				
			}else if(role_id==2){//团队长及其队员
				list = cust.team_cust_select(user_id, state, cust_name, cust_cell, sales_name, cust_level,m, 10);
				hide_secret_prop(list, user);
			}else if(role_id==3){//分公司领导
				list = cust.leader_cust_select(user_id, state, cust_name, cust_cell, sales_name, cust_level,m, 10);
				hide_secret_prop(list, user);
			}else if(role_id==4){//运营操作
				list = cust.all_cust_select(state, cust_name, cust_cell, sales_name, cust_level,m, 10);
				
			}
			else if(role_id==5){//运营领导
				list = cust.all_cust_select(state, cust_name, cust_cell, sales_name, cust_level,m, 10);
				
			}
			if (list.size() > 0 && list != null) {
				json.put("list", list);
				json.put("pagenum", PageNum);
				json.put("status", "1");

			} else {
				json.put("status", "2");
				json.put("cust_detail", "未找到相关数据");
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
	 * 跳转至新建页面
	 * 
	 * @return
	 */
	/*
	 * public String skip(){
	 * 
	 * return "success"; }
	 */

	/**
	 * 新增客户拜访记录
	 * 
	 * @throws ParseException
	 * 
	 */
	public void addSee() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			// HttpSession session = req.getSession();
			// int cust_id = (Integer) session.getAttribute("cust_id");
			long cust_id = Long.parseLong(req.getParameter("cust_id"));
			String see_date = req.getParameter("see_date");
			String see_member = req.getParameter("see_member");
			String see_desc = req.getParameter("textarea");
			/*
			 * String see_id = req.getParameter("see_id"); String cust_id =
			 * req.getParameter("cust_id"); String email_id =
			 * req.getParameter("email_id");
			 */
			CustDao cust = new CustDao();
			cust.addSee(cust_id, see_date, see_member, see_desc);
			json.put("msg", "添加成功");
			json.put("desc", "1");
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

	/**
	 * 新增客户公司
	 * 
	 * @throws ParseException
	 * 
	 */
	public void addMember() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			// HttpSession session = req.getSession();
			// int cust_id = (Integer) session.getAttribute("cust_id");
			long cust_id = Long.parseLong(req.getParameter("cust_id"));
			String comp_name = req.getParameter("comp_name");
			String comp_type = req.getParameter("comp_type");
			String license = req.getParameter("license");
			String legal = req.getParameter("legal");
			String taxid = req.getParameter("taxid");
			String org_code_cert = req.getParameter("org_code_cert");
			double reg_capital = 0;
			if (req.getParameter("reg_capital") != "") {
				reg_capital = Double.parseDouble(req.getParameter("reg_capital"));
			}
			String reg_address = req.getParameter("reg_address");
			String reg_date = req.getParameter("reg_date");
			String opera_period = req.getParameter("opera_period");
			String state = req.getParameter("state");
			CustDao cust = new CustDao();
			cust.addMember(cust_id, comp_name, comp_type, license, legal, taxid, org_code_cert, reg_capital,
					reg_address, reg_date, opera_period, state);
			json.put("msg", "添加成功");
			json.put("desc", "1");
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

	/**
	 * 添加前查询客户是否存在
	 */
	public void querycust() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String condition = req.getParameter("condition");
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = cust.querycust(condition);

			if (list != null && list.size() > 0) {
				json.put("list", list.get(0));
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
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
	 * 新增客户基本信息
	 * 
	 * @throws ParseException
	 * 
	 */
	public void addCustinfo() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
				
		}
		/*System.out.println(user.toString1());*/
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		JSONObject json = new JSONObject();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String cust_type = req.getParameter("cust_type");
			String cust_reg_time = req.getParameter("cust_reg_time");
			String cust_name = req.getParameter("cust_name");
			String cust_sex = req.getParameter("cust_sex");
			String cust_birth = req.getParameter("cust_birth");
			String cust_level = req.getParameter("cust_level");
			String cust_idnum = req.getParameter("cust_idnum");
			String cust_idtype = req.getParameter("cust_idtype");
			String city = req.getParameter("city");
			String email = req.getParameter("email");
			String wechat = req.getParameter("wechat");
			String qq = req.getParameter("qq");
			String address = req.getParameter("address");
			String company = req.getParameter("company");
			String id_address = req.getParameter("id_address");
			String profession = req.getParameter("profession");
			String state = req.getParameter("state");
			String cust_cell = req.getParameter("cust_cell");
			String cust_risk = req.getParameter("cust_risk");
			String see_date = req.getParameter("see_date");
			String see_member = req.getParameter("see_member");
			String see_desc = req.getParameter("see_desc");
			String sales_name = req.getParameter("sales_name");
			String sales_post = req.getParameter("sales_post");
			String allot_date = req.getParameter("allot_date");
			String recycle_date = req.getParameter("recycle_date");
			String recycle_reason = req.getParameter("recycle_reason");
			String sales_area = req.getParameter("sales_area");
			String cust_state = req.getParameter("cust_state");
			String effect_sign = req.getParameter("effect_sign");
			String family_cust_desc = req.getParameter("family_cust_desc");
			String family_mem_num = req.getParameter("family_mem_num");
			String recorder = req.getParameter("recorder");
			String remark = req.getParameter("remark");
			String family_cust_level = req.getParameter("family_cust_level");
			String relation = req.getParameter("relation");

			CustDao cust = new CustDao();

			list = cust.checkcustcell(cust_cell);
			if (list != null && list.size() > 0) {
			long count = (Long) list.get(0).get("count");
			/*System.out.println(count);*/
			if (count > 0) {
				json.put("status", "0");
				json.put("msg", "客户已存在，请勿重复添加");

			} else {
				/*System.out.println("添加客户");*/
				long cust_id = cust.addCustinfo(user,cust_reg_time, cust_name, cust_sex, cust_birth, cust_level, cust_idnum,
						cust_idtype, city, email, wechat, qq, address, company, id_address, profession, state,
						cust_cell, cust_risk, see_date, see_member, see_desc, sales_name, sales_post, allot_date,
						recycle_date, recycle_reason, sales_area, cust_state, cust_type, effect_sign, family_name,
						reg_time, textarea, comp_name, comp_type, license, legal, org_code_cert, reg_capital,
						reg_address, reg_date, opera_period, taxid, family_cust_desc, family_mem_num, recorder, remark,
						family_cust_level, relation);
				json.put("list", list);
				json.put("status", "1");
				json.put("msg", "添加成功");
				json.put("cust_id", cust_id);
				json.put("cust_name", cust_name);
			}
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

	/**
	 * 新增客户公司信息
	 * 
	 * @throws ParseException
	 * 
	 */
	public void addCompinfo() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		String custid = req.getParameter("cust_id");
		long cust_id = Long.parseLong(custid);
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String comp_name = req.getParameter("comp_name");
			String comp_type = req.getParameter("comp_type");
			String license = req.getParameter("license");
			String legal = req.getParameter("legal");
			String taxid = req.getParameter("taxid");
			String org_code_cert = req.getParameter("org_code_cert");
			double reg_capital = 0;
			if (req.getParameter("reg_capital") != "") {
				reg_capital = Double.parseDouble(req.getParameter("reg_capital"));
			}
			String reg_address = req.getParameter("reg_address");
			String reg_date = req.getParameter("reg_date");
			String opera_period = req.getParameter("opera_period");
			String state = req.getParameter("state");
			CustDao cust = new CustDao();
			cust.addCompinfo(cust_id, comp_name, comp_type, license, legal, taxid, org_code_cert, reg_capital,
					reg_address, reg_date, opera_period, state);
			json.put("msg", "添加成功");
			json.put("cust_id", cust_id);
			json.put("desc", "1");
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

	/**
	 * 新增客户家族信息
	 * 
	 * @throws ParseException
	 * 
	 */
	public void addFamilyinfo() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		try {
			resp.setCharacterEncoding("utf-8");
			HttpServletRequest req = ServletActionContext.getRequest();

			String family_name = req.getParameter("family_name");
			/* String reg_time = req.getParameter("reg_time"); */
			String family_cust_desc = req.getParameter("family_cust_desc");
			String family_cust_level = req.getParameter("family_cust_level");
			String relation = req.getParameter("relation");
			String cust_reg_time = req.getParameter("cust_reg_time");
			String cust_name = req.getParameter("cust_name");
			String cust_name1 = req.getParameter("cust_name1");
			String cust_sex1 = req.getParameter("cust_sex1");
			String cust_cell1 = req.getParameter("cust_cell1");
			String cust_birth1 = req.getParameter("cust_birth1");
			String cust_idtype1 = req.getParameter("cust_idtype1");
			String cust_idnum1 = req.getParameter("cust_idnum1");
			String city1 = req.getParameter("city1");
			String email1 = req.getParameter("email1");
			String wechat1 = req.getParameter("wechat1");
			String qq1 = req.getParameter("qq1");
			String address1 = req.getParameter("address1");
			String company1 = req.getParameter("company1");
			String id_address1 = req.getParameter("id_address1");
			String profession1 = req.getParameter("profession1");
			String see_date1 = req.getParameter("see_date1");
			String see_member1 = req.getParameter("see_member1");
			String see_desc1 = req.getParameter("see_desc1");

			CustDao cust = new CustDao();
			String cust_idcheck = req.getParameter("cust_idcheck");
			String[] cust_idchecks = cust_idcheck.split(",");
			List<Object[]> custList = new ArrayList<Object[]>();
			String custid = req.getParameter("cust_id");
			long cust_id = Long.parseLong(custid);
			long family_id = jdbcUtil.seq();
			List<Map<String, Object>> cust_list = cust.select_cust_id(cust_id);
			Object[] cust_object = new Object[] { "本人", family_id, cust_id,
					(String) cust_list.get(0).get("email"), (String) cust_list.get(0).get("cust_reg_time"),
					(String) cust_list.get(0).get("cust_name"), (String) cust_list.get(0).get("cust_sex"),
					(String) cust_list.get(0).get("cust_birth"), (String) cust_list.get(0).get("cust_level"),
					(String) cust_list.get(0).get("cust_idnum"), (String) cust_list.get(0).get("cust_idtype"),
					(String) cust_list.get(0).get("city"), (String) cust_list.get(0).get("wechat"),
					(String) cust_list.get(0).get("qq"), (String) cust_list.get(0).get("address"),
					(String) cust_list.get(0).get("company"), (String) cust_list.get(0).get("id_address"),
					(String) cust_list.get(0).get("profession"), jdbcUtil.seq(), (String) cust_list.get(0).get("state"),
					(String) cust_list.get(0).get("cust_cell")

			};
			custList.add(cust_object);
         if(!"".equals(cust_idcheck)&&cust_idcheck != null){
			for (int i = 0; i < cust_idchecks.length; i++) {
				long selecust = Long.parseLong(cust_idchecks[i]);
				List<Map<String, Object>> list = cust.select_cust_id(selecust);
				Object[] object = new Object[] { relation, family_id, selecust, (String) list.get(0).get("email"),
						(String) list.get(0).get("cust_reg_time"), (String) list.get(0).get("cust_name"),
						(String) list.get(0).get("cust_sex"), (String) list.get(0).get("cust_birth"),
						(String) list.get(0).get("cust_level"), (String) list.get(0).get("cust_idnum"),
						(String) list.get(0).get("cust_idtype"), (String) list.get(0).get("city"),
						(String) list.get(0).get("wechat"), (String) list.get(0).get("qq"),
						(String) list.get(0).get("address"), (String) list.get(0).get("company"),
						(String) list.get(0).get("id_address"), (String) list.get(0).get("profession"), jdbcUtil.seq(),
						(String) list.get(0).get("state"), (String) list.get(0).get("cust_cell")

				};

				// object[i]=list;
				custList.add(object);
			}
         }
			// object[i]=list;
			//custList.add(object);
			
			cust.addFamilyinfo(cust_id, family_id, family_name, family_cust_desc, family_cust_level, relation, cust_reg_time,
					cust_name, cust_name1, cust_sex1, cust_cell1,
					cust_birth1, cust_idtype1, cust_idnum1, city1, email1, wechat1, qq1, address1, company1,
					id_address1, profession1, see_date1, see_member1, see_desc1, custList);
			json.put("msg", "添加成功");
			json.put("cust_id", cust_id);
			json.put("desc", "1");
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

	// 添加处跳转页面
	public String skipadd() {

		String result = "success";
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		String level = "level";
		String sex = "sex";
		String idtype = "idtype";
		Object[] o = { level, sex, idtype };
		DictDao dict = new DictDao();
		for (int i = 0; i < o.length; i++) {
			List<Map<String, Object>> list = dict.queryDict(o[i].toString());
			if (list.size() > 0) {
				req.setAttribute("list" + i, list);
			} else {
				req.setAttribute("list" + i, "未找到");
			}
		}
		return result;
	}

	/**
	 * 更新客户基本信息(不再使用)
	 * 
	 * @throws ParseException
	 * 
	 */
	public void cust_edit() {
		String obj = req.getParameter("data");
		JSONObject json = JSONObject.parseObject(obj);
		JSONObject js = new JSONObject();
		String cust_name = (String) req.getParameter("cust_name");
		String cust_sex = (String) req.getParameter("cust_sex");
		String cust_birth = (String) req.getParameter("cust_birth");
		String cust_level = (String) req.getParameter("cust_level");
		String cust_idnum = (String) req.getParameter("cust_idnum");
		String cust_idtype = (String) req.getParameter("cust_idtype");
		String city = (String) req.getParameter("city");
		String email = (String) req.getParameter("email");
		String wechat = (String) req.getParameter("wechat");
		String qq = (String) req.getParameter("qq");
		String address = (String) req.getParameter("address");
		String company = (String) req.getParameter("company");
		String profession = (String) req.getParameter("profession");
		String cust_risk = (String) req.getParameter("cust_risk");
		
		try {
			long cust_id = Long.parseLong(req.getParameter("cust_id"));
			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			CustDao cust = new CustDao();
//			cust.saveCust(cust_id, cust_name, cust_sex, cust_birth, cust_level, cust_idnum, cust_idtype, 
//					city, email, wechat, qq, address, company,profession, cust_risk);
			js.put("msg", "修改成功");
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
	 * 更新客户基本信息
	 * 
	 * @throws ParseException
	 * 
	 */
	public void cust_submit() {
		JSONObject js = new JSONObject();
		String custid = req.getParameter("cust_id");
		long cust_id = Long.parseLong(custid);
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			String cust_name = (String) req.getParameter("cust_name");
			String cust_sex = (String) req.getParameter("cust_sex");
			String cust_birth = (String) req.getParameter("cust_birth");
			String cust_level = (String) req.getParameter("cust_level");
			String cust_idnum = (String) req.getParameter("cust_idnum");
			String cust_idtype = (String) req.getParameter("cust_idtype");
			String city = (String) req.getParameter("city");
			String email = (String) req.getParameter("email");
			String wechat = (String) req.getParameter("wechat");
			String qq = (String) req.getParameter("qq");
			String address = (String) req.getParameter("address");
			String company = (String) req.getParameter("company");
			String profession = (String) req.getParameter("profession");
			String cust_risk = (String) req.getParameter("cust_risk");
			CustDao cust = new CustDao();
			cust.cust_submit(cust_id,cust_name,cust_sex,cust_birth,cust_level,cust_idnum,cust_idtype,
					city,email,wechat,qq,address,company,profession,cust_cell,cust_risk);
			js.put("msg", "信息已提交");
			
			String former_cust_name = req.getParameter("former_cust_name");
			String former_cust_cell = req.getParameter("former_cust_cell");
			String former_cust_idtype = req.getParameter("former_cust_idtype");
			String former_cust_idnum = req.getParameter("former_cust_idnum");
			String former_cust_sex  = req.getParameter("former_cust_sex"); 
			String former_cust_birth = req.getParameter("former_cust_birth"); 
			String former_cust_level = req.getParameter("former_cust_level"); 
			String former_sales_name = req.getParameter("former_sales_name"); 
			String former_cust_reg_time = req.getParameter("former_cust_reg_time"); 
			String former_city = req.getParameter("former_city"); 
			String former_email = req.getParameter("former_email"); 
			String former_wechat = req.getParameter("former_wechat"); 
			String former_qq = req.getParameter("former_qq"); 
			String former_profession = req.getParameter("former_profession"); 
			String former_company = req.getParameter("former_company"); 
			String former_sales_area = req.getParameter("former_sales_area"); 
			String former_address = req.getParameter("former_address"); 
			String former_cust_risk = req.getParameter("former_cust_risk"); 
			JSONObject json = new JSONObject();
			json.put("cust_name", former_cust_name);
			json.put("cust_cell", former_cust_cell);
			json.put("cust_idtype", former_cust_idtype);
			json.put("cust_idnum", former_cust_idnum);
			json.put("cust_sex", former_cust_sex);
			json.put("cust_birth", former_cust_birth);
			json.put("cust_level", former_cust_level);
			json.put("sales_name", former_sales_name);
			json.put("cust_reg_time", former_cust_reg_time);
			json.put("city", former_city);
			json.put("email", former_email);
			json.put("wechat", former_wechat);
			json.put("qq", former_qq);
			json.put("profession", former_profession);
			json.put("company", former_company);
			json.put("sales_area", former_sales_area);
			json.put("address", former_address);
			json.put("cust_risk", former_cust_risk);
			cust.addtask(json, cust_id, "个人客户基本信息更改提交", "cust",user.get_id());
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
	 * 更新客户基本信息 后提交审批通过
	 * 
	 * @throws ParseException
	 * 
	 */
	public void cust_pass() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String custid = req.getParameter("cust_id");
		long cust_id = Long.parseLong(custid);
		try {

			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			CustDao cust = new CustDao();
			cust.cust_pass(cust_id);
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
	 * 更新客户基本信息 后提交审批不通过
	 * 
	 * @throws ParseException
	 * 
	 */
	public void cust_nopass() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String custid = req.getParameter("cust_id");
		long cust_id = Long.parseLong(custid);
		try {

			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			CustDao cust = new CustDao();
			cust.cust_nopass(cust_id);
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
	 * 更新家族基本信息 后提交审批
	 * 
	 * @throws ParseException
	 * 
	 */
	public void fammember_submit() {
		try {
			JSONObject js = new JSONObject();
			User user = (User) req.getSession().getAttribute(Constants.USER);
			String message="";
			String status ="0";
			String family_id1 = req.getParameter("family_id1");
			long family_id = Long.parseLong(family_id1);
			String memberid = req.getParameter("member_id");
			long member_id = Long.parseLong(memberid);
			String family_cust_desc = req.getParameter("family_cust_desc");
			String family_cust_name = req.getParameter("family_cust_name");
			String relation = req.getParameter("relation");
			String cust_name = req.getParameter("cust_name");
			String cust_cell = req.getParameter("cust_cell");
			String cust_birth = req.getParameter("cust_birth");
			String cust_idnum = req.getParameter("cust_idnum");
			String city = req.getParameter("city");
			String email = req.getParameter("email");
			String wechat = req.getParameter("wechat");
			String qq = req.getParameter("qq");
			String address = req.getParameter("address");
			String company = req.getParameter("company");
			String id_address = req.getParameter("id_address");
			String profession = req.getParameter("profession");
			String family_cust_level = req.getParameter("family_cust_level");
			CustDao cust = new CustDao();
			//查询成员基本信息是否有变化
			List<Map<String, Object>> member_list = cust.member_list(family_id, member_id);
			List<Map<String, Object>> family_list = cust.family_list(family_id);
			if(member_list!=null&&member_list.size()>0){
				if(!cust_name.equals(member_list.get(0).get("cust_name"))){
					message+="【客户名称】："+member_list.get(0).get("cust_name")+"【客户名称】："+cust_name+"\n";
					status="1";
				}
				if(!cust_cell.equals(member_list.get(0).get("cust_cell"))){
					message+="【客户手机】："+member_list.get(0).get("cust_cell")+"【客户手机】："+cust_cell+"\n";
					status="1";
				}
				if(!cust_idnum.equals(member_list.get(0).get("cust_idnum"))){
					message+="【证件号码】："+member_list.get(0).get("cust_idnum")+"【证件号码】："+cust_idnum+"\n";
					status="1";
				}
			}else{
				js.put("msg", "未找到成员信息");
				status="2";
			}
			if(family_list.size()>0&&family_list!=null){
				if(!family_cust_desc.equals(family_list.get(0).get("family_cust_desc"))){
					message+="【证件号码】："+member_list.get(0).get("family_cust_desc")+"【证件号码】："+family_cust_desc+"\n";
					status="1";
				}
			}else{
				js.put("msg", "未找到家族信息");
				status="2";
			}
			if("1".equals(status)){
				JSONObject taskjson = new JSONObject();
				taskjson.put("family_list", family_list);
				taskjson.put("member_list", member_list);
				userDao us = new userDao();
				us.addtask(taskjson, family_id, "家族审批", "family",user.get_id() );
				cust.fammember_submit(family_id,member_id, family_cust_desc, family_cust_name, 
						relation, cust_name, cust_cell, cust_birth, cust_idnum, city, email, wechat, qq,
						address, company, id_address, profession);
				status="1";
				js.put("msg", "修改成功");
				userDao udao = new userDao();
				List<Map<String, Object>> userlist =udao.getTeamUser(user.get_id());
				String email_no="";
				String real_name = "";
				if(userlist!=null&&userlist.size()>0){
					email_no=(String) userlist.get(0).get("email");
					real_name =(String) userlist.get(0).get("real_name");
				}
				SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");
				SentMailInfoBean sentmsg = new SentMailInfoBean();
				//sentmsg.setFirmId("001");
				sentmsg.setSubjectId(""+family_id);
				sentmsg.setSentMailaddr(mailCache.from);
				sentmsg.setReviceMailaddr(email_no);
				sentmsg.setMail_busstype("家族审批");
				sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.mailfamily");
				sentmsg.setMailContent("家族：					\n"
						+ "尊敬的"+real_name+"： 					\n"
						+ "您好， "+sdfg.format(new Date())+","+user.getRealName()+"在OMS系统更改了家族，现等待您审批。\n"
						+ "---InfoBegin---					\n"
						+ "变更前信息：				变更后信息：	\n"
						+message
						+ "---InfoEnd--- \n"					
		);
				sentmsg.setSubject("更改家族审批");
				sendMail.sendMessage(sentmsg, true);
			}
			
			js.put("desc", status);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(js);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * 更新家族基本信息 后提交审批通过
	 * 
	 * @throws ParseException
	 * 
	 */
	public void fammember_pass() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String familyid = req.getParameter("family_id");
		long family_id = Long.parseLong(familyid);
		try {

			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			CustDao cust = new CustDao();
			cust.fammember_pass(family_id);
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
	 * 更新家族基本信息 后提交审批不通过
	 * 
	 * @throws ParseException
	 * 
	 */
	public void fammember_nopass() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String familyid = req.getParameter("family_id");
		long family_id = Long.parseLong(familyid);
		try {

			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			CustDao cust = new CustDao();
			cust.fammember_nopass(family_id);
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
	 * 更新客户公司信息
	 * 
	 * @throws ParseException
	 * 
	 */
	public void comp_edit() {
		JSONObject js = new JSONObject();
		String custid = req.getParameter("cust_id1");
		long cust_id = Long.parseLong(custid);
		String compid = req.getParameter("comp_id");
		long comp_id = Long.parseLong(compid);
		try {

			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			CustDao cust = new CustDao();
			cust.saveComp(cust_id, comp_id, comp_name, comp_type, license, legal, org_code_cert, reg_capital,
					reg_address, reg_date, opera_period, taxid);
			js.put("msg", "修改成功");
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
	 * 更新客户家族成员信息
	 * 
	 * @throws ParseException
	 * 
	 */
	public void fammember_edit() {
		JSONObject js = new JSONObject();
		String family_id1 = req.getParameter("family_id1");
		long family_id = Long.parseLong(family_id1);
		String memberid = req.getParameter("member_id");
		long member_id = Long.parseLong(memberid);
		String family_cust_desc = req.getParameter("family_cust_desc");
		String family_cust_name = req.getParameter("family_cust_name");
		String relation = req.getParameter("relation");
		String cust_name = req.getParameter("cust_name");
		String cust_cell = req.getParameter("cust_cell");
		String cust_birth = req.getParameter("cust_birth");
		String cust_idnum = req.getParameter("cust_idnum");
		String city = req.getParameter("city");
		String email = req.getParameter("email");
		String wechat = req.getParameter("wechat");
		String qq = req.getParameter("qq");
		String address = req.getParameter("address");
		String company = req.getParameter("company");
		String id_address = req.getParameter("id_address");
		String profession = req.getParameter("profession");
		try {

			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			CustDao cust = new CustDao();
			cust.fammember_edit(family_id, member_id, family_cust_desc, family_cust_name, relation, cust_name,
					cust_cell, cust_birth, cust_idnum, city, email, wechat, qq, address, company, id_address,
					profession);
			js.put("msg", "修改成功");
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

			e.printStackTrace();
		}

	}

	/**
	 * 有家族后，添加家族成员前查询客户是否存在
	 */
	public void queryFamMem() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String queryfm = req.getParameter("queryfm");
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = cust.queryFamMem(queryfm);

			if (list != null && list.size() > 0) {
				json.put("list", list.get(0));
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
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
	 * 添加家族弹框里的成员前查询客户是否存在
	 */
	public void queryFamilyMember() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String queryFM = req.getParameter("queryFM");
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = cust.queryFamilyMember(queryFM);

			if (list != null && list.size() > 0) {
				json.put("list", list.get(0));
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
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
	 * 添加家族成员页面字典值填充
	 */
	public void fam() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = cust.fam();

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					json.put("list" + i, list.get(i));
				}

				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
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
	 * 添加客户所有信息 后保存
	 * 
	 * @throws ParseException
	 * 
	 */
	public void skipaddsave() {
		JSONObject js = new JSONObject();
		try {
			long cust_id = Long.parseLong(req.getParameter("cust_id"));
			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			CustDao cust = new CustDao();
			cust.skipaddsave(cust_id);
			js.put("msg", "信息已保存");
			js.put("status", "1");
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
	 * 添加客户所有信息 后提交审批
	 * 
	 * @throws ParseException
	 * 
	 */
	public void skipaddpass() {
		JSONObject js = new JSONObject();
		User user = (User) req.getSession().getAttribute(Constants.USER);
		try {
			long cust_id = Long.parseLong(req.getParameter("cust_id"));
			String cust_name = req.getParameter("cust_name");
			String cust_sex = req.getParameter("cust_sex");
			String custcell = req.getParameter("cust_cell");
			String cust_cell = custcell .substring(0,3) + "****" + custcell .substring(7, custcell .length());
			String see_desc = req.getParameter("see_desc");
			// String m =addByUrl("",j,"post");
			// System.out.println(m);
			
			String email_leader = "";
			String real_name_leader = "";
			userDao us = new userDao();
			List<Map<String, Object>> list=us.getTeamUser(user.get_id());
			if(list != null && list.size()>0){
				email_leader = (String)list.get(0).get("email");
				real_name_leader = (String)list.get(0).get("real_name");
			}
			if("".equals(email_leader)){
				js.put("status", "2");
			}
			else{
			CustDao cust = new CustDao();
			cust.skipaddpass(cust_id,cust_name,cust_sex,cust_cell,see_desc,user,email_leader,real_name_leader);
			js.put("msg", "信息已提交，待审批");
			js.put("status", "1");
			}
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
	 * 添加客户家族的 家庭成员信息
	 * 
	 * @throws ParseException
	 * 
	 */
	public void addFamilymember() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		try {
			resp.setCharacterEncoding("utf-8");
			HttpServletRequest req = ServletActionContext.getRequest();
			String relation = req.getParameter("relation");
			String cust_reg_time = req.getParameter("cust_reg_time");
			String cust_name = req.getParameter("cust_name");
			String cust_name3 = req.getParameter("cust_name3");
			String cust_sex3 = req.getParameter("cust_sex3");
			String cust_cell3 = req.getParameter("cust_cell3");
			String cust_birth3 = req.getParameter("cust_birth3");
			String cust_idtype3 = req.getParameter("cust_idtype3");
			String cust_idnum3 = req.getParameter("cust_idnum3");
			String city3 = req.getParameter("city3");
			String relation3 = req.getParameter("relation3");
			String email3 = req.getParameter("email3");
			String wechat3 = req.getParameter("wechat3");
			String qq3 = req.getParameter("qq3");
			String address3 = req.getParameter("address3");
			String company3 = req.getParameter("company3");
			String id_address3 = req.getParameter("id_address3");
			String profession3 = req.getParameter("profession3");
			String see_date3 = req.getParameter("see_date3");
			String see_member3 = req.getParameter("see_member3");
			String see_desc3 = req.getParameter("see_desc3");

			CustDao cust = new CustDao();
			String cust_idcheck1 = req.getParameter("cust_idcheck1");
			String[] cust_idchecks = cust_idcheck1.split(",");
			List<Object[]> custList = new ArrayList<Object[]>();
			String familyid = req.getParameter("family_id");
			long family_id = Long.parseLong(familyid);
			
			/*List<Map<String, Object>> cust_list = cust.select_family_id(family_id);*/
			if(!"".equals(cust_idcheck1)&&cust_idcheck1 != null){
			for (int i = 0; i < cust_idchecks.length; i++) {
				long selecust = Long.parseLong(cust_idchecks[i]);
				List<Map<String, Object>> list = cust.select_cust_id(selecust);
				Object[] object = new Object[] { relation, family_id, selecust, (String) list.get(0).get("email"),
						(String) list.get(0).get("cust_reg_time"), (String) list.get(0).get("cust_name"),
						(String) list.get(0).get("cust_sex"), (String) list.get(0).get("cust_birth"),
						(String) list.get(0).get("cust_level"), (String) list.get(0).get("cust_idnum"),
						(String) list.get(0).get("cust_idtype"), (String) list.get(0).get("city"),
						(String) list.get(0).get("wechat"), (String) list.get(0).get("qq"),
						(String) list.get(0).get("address"), (String) list.get(0).get("company"),
						(String) list.get(0).get("id_address"), (String) list.get(0).get("profession"), jdbcUtil.seq(),
						(String) list.get(0).get("state"), (String) list.get(0).get("cust_cell")

				};

				// object[i]=list;
				custList.add(object);
			}
            }
			// object[i]=list;
			//custList.add(object);
			
			cust.addFamilymember(family_id,relation, cust_reg_time,cust_name,
					 cust_name3,  cust_sex3, cust_cell3, cust_birth3, cust_idtype3, cust_idnum3,relation3,
					city3, email3, wechat3, qq3, address3, company3, id_address3, 
					profession3, see_date3, see_member3, see_desc3, custList);
			json.put("msg", "添加成功");
			/*json.put("cust_id", cust_id);*/
			json.put("desc", "1");
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
	
	/**
	 * 报表导出
	 */
	public void cust_report() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			
		}
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		try {
			
			JSONObject json = new JSONObject();
			HttpServletResponse response = ServletActionContext.getResponse();
			String remoteFilePath ="/var/www/oms/report/customer.xls";
			// 输出Excel
			File fileWrite = new File(remoteFilePath);
			CustDao rep = new CustDao();
			if(!fileWrite.exists())
			fileWrite.createNewFile();
			String[] o = {"序号","地区","客户名称","性别","客户手机号","销售名","级别","状态","证件类型","证件号","客户生日","城市","电子邮箱","微信","QQ","地址","雇主名称","职业","录入时间"};
			OutputStream os = new FileOutputStream(fileWrite);
			if(role_id==1){//销售本人
				SqlRowSet rs = rep.cust_report_sale(user_id);
				Download.writeExcel(os, rs, o );
				Download.downloadFile(remoteFilePath, response);
			}else if(role_id==2){//团队长及其队员
				SqlRowSet rs = rep.cust_report_team(user_id);
				Download.writeExcel(os, rs, o);
				Download.downloadFile(remoteFilePath, response);			
			}else if(role_id==3){//分公司领导
				SqlRowSet rs = rep.cust_report_leader(user_id);
				Download.writeExcel(os, rs, o);
				Download.downloadFile(remoteFilePath, response);
			}else if(role_id==4){//运营操作
				SqlRowSet rs = rep.cust_report();
				Download.writeExcel(os, rs, o);
				Download.downloadFile(remoteFilePath, response);
			}else if(role_id==5){//运营领导
				SqlRowSet rs = rep.cust_report();
				Download.writeExcel(os, rs, o);
				Download.downloadFile(remoteFilePath, response);
			}
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 下载kyc模板
	 */
	public void kyc_mould() {
		try {
			String remoteFilePath = "/var/www/oms/report/KYCmould.xls";
			Download.downloadFile(remoteFilePath, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 家族重置重置
	 */
	public void custfam_reset(){
		try {
			JSONObject json = new JSONObject();
			User user = (User) req.getSession().getAttribute(Constants.USER);
			if(!"".equals(req.getParameter("family_id"))&&req.getParameter("family_id")!=null){
				long family_id = Long.parseLong(req.getParameter("family_id"));
				long member_id = Long.parseLong(req.getParameter("member_id"));
				GpDao gp = new GpDao();
				List<Map<String, Object>> list = gp.gp_reset(family_id,user.get_id());
				if(list.size()>0&&list!=null){
					Object content = (Object) list.get(0).get("content");
					JSONObject jsonob = JSONObject.parseObject(content.toString());
					CustDao cust = new CustDao();
					cust.family_reset(jsonob, family_id, member_id);
					json.put("content", jsonob);
					json.put("desc", "1");
				}
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
	/**
	 * 客户重置
	 */
	public void cust_reset(){
		try {
			JSONObject json = new JSONObject();
			User user = (User) req.getSession().getAttribute(Constants.USER);
			if(!"".equals(req.getParameter("cust_id"))&&req.getParameter("cust_id")!=null){
				long cust_id = Long.parseLong(req.getParameter("cust_id"));
				GpDao gp = new GpDao();
				List<Map<String, Object>> list = gp.gp_reset(cust_id,user.get_id());
				if(list.size()>0&&list!=null){
					Object content = (Object) list.get(0).get("content");
					JSONObject jsonob = JSONObject.parseObject(content.toString());
					CustDao cust = new CustDao();
					cust.cust_reset(jsonob, cust_id);
					json.put("content", jsonob);
					json.put("desc", "1");
				}
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

	/**
	 * 客户分配页
	 * @return
	 */
	public String cust_distribution() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		long role_id = user.getRole_id();
		String user_area = user.getArea();
		String result = "";
		req.setAttribute("role_id", role_id);
		try {
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listcount = new ArrayList<Map<String, Object>>();
			
			list = cust.distribution_inquiry(10, 0, user_area);
			for (int i = 0; i < list.size(); i++) {
				String custId = String.valueOf(list.get(i).get("cust_id"));
				list.get(i).put("cust_id", custId);
				
				if ("0".equals(list.get(i).get("cust_belong_state"))) {
					list.get(i).put("cust_belong_state", "是");
				} else if ("1".equals(list.get(i).get("cust_belong_state"))) {
					list.get(i).put("cust_belong_state", "否");
				}
			}
			listcount = cust.distribution_inquiryCount(user_area);
			
			if(listcount != null && listcount.size() > 0){
			req.setAttribute("totalNum", listcount.get(0).get("count"));
			}
			req.setAttribute("PageNum", "1");
			req.setAttribute("PageSize", "10");
			if (list.size() > 0 && list != null) {
				req.setAttribute("list", list);
				req.setAttribute("status", "1");
			} else {
				req.setAttribute("status", "2");
			}
			
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			result = "error";
		}
		return result;
	}
	
	/**
	 * 客户分配页 查询，刷新列表
	 */
	public void distribution_search() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
				
		}
		DictDao dictDao = new DictDao();
		List<DictReference> references = new ArrayList<DictReference>();
		List<String> list1 = new ArrayList<String>();
		list1.add("0");
		list1.add("1");
		List<String> list2 = new ArrayList<String>();
		list2.add("1");
		list2.add("2");
		references.add(new DictReference("cust_belong_state", list1));
		references.add(new DictReference("level", list2));
		List<Map<String, Object>> dictItems = dictDao.batchQuery(references);
		Map<String, Object> referencesMap = new HashMap<String, Object>();
		for(int i=0;i<dictItems.size();i++) {
			String key = (String)dictItems.get(i).get("dict_type") + "#" + dictItems.get(i).get("dict_value");
			
			referencesMap.put(key, dictItems.get(i));
		}
		String user_area = user.getArea();
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			state = req.getParameter("state");
			cust_name = req.getParameter("cust_name");
			cust_cell = req.getParameter("cust_cell");
			sales_name = req.getParameter("sales_name");
			cust_level = req.getParameter("cust_level");
			int PageNum = Integer.parseInt(req.getParameter("PageNum"));
			int pageSize = Integer.parseInt(req.getParameter("PageSize"));
			int offset = (PageNum - 1) * pageSize;
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			int count = 0;
			
			//分公司领导
			list = cust.distribution_cust_select(user_area, state, cust_name, cust_cell, sales_name, cust_level,offset, 10);
			for (int i = 0; i < list.size(); i++) {
				String custId = String.valueOf(list.get(i).get("cust_id"));
				list.get(i).put("cust_id", custId);
				
				if ("0".equals(list.get(i).get("cust_belong_state"))) {
					list.get(i).put("cust_belong_state", "是");
				} else if ("1".equals(list.get(i).get("cust_belong_state"))) {
					list.get(i).put("cust_belong_state", "否");
				}
			}
			count = cust.distribution_cust_select1(user_area, state, cust_name, cust_cell, sales_name, cust_level);
				
			if (list.size() > 0 && list != null) {
				req.setAttribute("list", list);
			}
			if (list != null && list.size() > 0) {
				json.put("list", list);
				json.put("state", state);
				json.put("totalNum", count);
				json.put("PageNum", PageNum);
				json.put("PageSize", "10");
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
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
	 * 客户分配页面 点页码分页查询
	 */
	public void distributionPageQuery() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
				
		}
		String userArea = user.getArea();
		JSONObject json = new JSONObject();
		List<Map<String, Object>> listcount = new ArrayList<Map<String, Object>>();
		try {
			int PageNum = Integer.parseInt(req.getParameter("PageNum"));
			int pageSize = Integer.parseInt(req.getParameter("PageSize"));
			int offset = (PageNum - 1) * pageSize;
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			//分公司领导
			list = cust.distribution_inquiry(10, offset, userArea);
			for (int i = 0; i < list.size(); i++) {
				String custId = String.valueOf(list.get(i).get("cust_id"));
				list.get(i).put("cust_id", custId);
				
				if ("0".equals(list.get(i).get("cust_belong_state"))) {
					list.get(i).put("cust_belong_state", "是");
				} else if ("1".equals(list.get(i).get("cust_belong_state"))) {
					list.get(i).put("cust_belong_state", "否");
				}
			}
			listcount = cust.distribution_inquiryCount(userArea);
			if (list.size() > 0 && list != null) {
				json.put("list", list);
				json.put("pagenum", PageNum);
				json.put("totalNum", listcount.get(0).get("count"));
				json.put("status", "1");

			} else {
				json.put("status", "2");
				json.put("cust_detail", "未找到相关数据");
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
	 * 客户分配页 查询所在地区所有销售
	 */
	public void transferSalesList() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
				
		}
		String userArea = user.getArea();
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			//分公司领导
			list = cust.transferSalesSelect(userArea);
				
			if (list.size() > 0 && list != null) {
				req.setAttribute("list", list);
			}
			if (list != null && list.size() > 0) {
				json.put("list", list);
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
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
	 * 客户分配页 重新分配客户
	 */
	public void transferSales() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
				
		}
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String cust_id = req.getParameter("cust_id");
			String sales_id = req.getParameter("sales_id");
			long customerId = Long.parseLong(cust_id);
			long salesId = Long.parseLong(sales_id);
			CustDao cust = new CustDao();
			
			//重新分配客户
			cust.transferSales(customerId, salesId);
				
			json.put("desc", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String cust_email() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		String result = "";
		try {
			result = "success";
		}catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}
	
	public void query_cust_email() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		int PageNum = 1;
		int pageSize = 10;
		JSONObject json = new JSONObject();
		try {
			if(req.getParameter("PageNum") != null && !"".equals(req.getParameter("PageNum"))) {
				PageNum = Integer.parseInt(req.getParameter("PageNum"));
			}
			if(req.getParameter("PageSize") != null && !"".equals(req.getParameter("PageSize"))) {
				pageSize = Integer.parseInt(req.getParameter("PageSize"));
			}
			String cust_name = req.getParameter("cust_name");
			String subject = req.getParameter("subject");
			int offset = (PageNum - 1) * pageSize;
			CustDao cust = new CustDao();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = cust.getCustMailInfoList(pageSize, offset, cust_name, subject, user.get_id());
			List<Map<String, Object>> listcount = new ArrayList<Map<String, Object>>();
			listcount = cust.getCustMailTotalCount(cust_name, subject, user.get_id());
			if (list.size() > 0 && list != null) {
				json.put("list", list);
				json.put("pagenum", PageNum);
				json.put("totalNum",listcount.get(0).get("count"));
				json.put("status", "1");

			} else {
				json.put("status", "2");
				json.put("cust_detail", "未找到相关数据");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String send_email_page() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		String result = "";
		JSONObject json = new JSONObject();
		try {
			
			List<Map<String, Object>> originatorList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> subjectList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> custMailList = new ArrayList<Map<String, Object>>();
			CustDao cust = new CustDao();
			
			String subjectid = req.getParameter("subjectid");
			if(subjectid != null && subjectid != "") {
				custMailList = cust.getCustMailBySubjectId(subjectid);
			}
			originatorList = cust.getOriginatorList();
			subjectList = cust.getSubjectListByUserId(user.get_id());
			
			if(custMailList.size() > 0) {
				req.setAttribute("content", custMailList.get(0).get("mail_content"));
			}
			req.setAttribute("originatorList", originatorList);
			req.setAttribute("subjectList", subjectList);
			
			result = "success";
		}catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}
	
	public void add_originator() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String orig_email = req.getParameter("orig_email");
			String orig_name = req.getParameter("orig_name");
			CustDao cust = new CustDao();
			cust.add_originator(orig_email, orig_name, user.get_id(), user.getRealName());
			json.put("desc", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add_subject() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String sub_name = req.getParameter("sub_name");
			CustDao cust = new CustDao();
			cust.add_subject(sub_name, user.get_id(), user.getRealName());
			json.put("desc", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void select_email_cust() {
		List<Map<String, Object>> custList = new ArrayList<Map<String, Object>>();
		JSONObject json = new JSONObject();
		try {
			String cust_name= req.getParameter("cust_name");
			CustDao cust = new CustDao();
			custList = cust.getCustListByName(cust_name);
			for(Map<String, Object> c: custList) {
				if(c.get("email") != null && checkEmail(c.get("email").toString())) {
					c.put("is_exist_email", "是");
				}else {
					c.put("is_exist_email", "否");
				}
				c.put("email", "");
			}
			json.put("desc", "1");
			json.put("custList", custList);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send_cust_email() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		JSONObject json = new JSONObject();
		String info = "邮件发送成功!";
		String desc = "1";
		try {
			String originator = req.getParameter("originator");
			if(originator == null || "".equals(originator.trim()) || !checkEmail(originator)) {
				info = "发件人不合法,邮件发送失败!";
				desc = "2";
			}
			String subject = req.getParameter("subject");
			if(subject == null || "".equals(subject.trim())) {
				info = "主题不能为空,邮件发送失败!";
				desc = "2";
			}
			String cust_id = req.getParameter("cust_id");
			if(cust_id == null || "".equals(cust_id.trim())) {
				info = "收件人不能为空,邮件发送失败!";
				desc = "2";
						
			}
			String cust_name = req.getParameter("cust_name");
			String content = req.getParameter("content");
			if(content == null || "".equals(content.trim())) {
				info = "发送内容不能为空,邮件发送失败!";
				desc = "2";
			}
			
			String[] filenames = req.getParameterValues("filenames[]");
			String[] cc_emails = req.getParameterValues("cc_emails[]");
			System.out.println(cc_emails);
 			if("1".equals(desc)) {
				SentMailInfoBean sentmsg = new SentMailInfoBean();
				CustDao cust = new CustDao();
				List<Map<String, Object>> list = cust.getCustEmailById(Long.parseLong(cust_id));
				String email = (String) list.get(0).get("email");
				if(email != null && checkEmail(email)) {
					List<Map<String, Object>> mailConfig = cust.getMailConfig(originator);
					if(mailConfig == null || mailConfig.size() == 0) {
						info = "发件邮箱不存在!";
						desc = "2";
					}else {
						sentmsg.setHost(mailConfig.get(0).get("mail_user_host").toString());
						sentmsg.setPassword(mailConfig.get(0).get("mail_user_pwd").toString());
						sentmsg.setUsername(mailConfig.get(0).get("mail_user_username").toString());
						long mail_subjectid = jdbcUtil.seq();
						sentmsg.setSubjectId(""+mail_subjectid);
						sentmsg.setSentMailaddr(mailConfig.get(0).get("mail_user_fromaddr").toString());
						sentmsg.setReviceMailaddr(email);
						sentmsg.setMailContent(content);
						sentmsg.setCcAddrs(cc_emails);
						sentmsg.setContentType("html");
						String dirs = Constants.SEND_CUST_EMAIL+user.get_id();
						File[] files = new File(dirs).listFiles();
						List<File> l = new ArrayList<File>();
						String fileNames = "";
						if(filenames != null && files != null) {
							for(int i=0; i < files.length; i++) {
								for(int j=0 ; j < filenames.length; j++) {
									System.out.println("1"+filenames[j]);
									if(files[i].getName().equals(filenames[j])) {
										System.out.println(filenames[j]);
										l.add(files[i]);
										if(i==0) {
											fileNames += files[i].getName();
										}else {
											fileNames += "," + files[i].getName();
										}
									}
								}
							}
						}
						sentmsg.setFiles(l.toArray(new File[l.size()]));
						sentmsg.setSubject(subject);
						sendMail.sendMessage(sentmsg, true);
						if(files != null) {
							for(int i=0; i < files.length; i++) {
								
								files[i].delete();
							}
						}
						cust.insertCustMailInfo(mail_subjectid, subject, originator, email, content, fileNames, user.get_id(), user.getRealName(), cust_name);
					}
				}else {
					info = "客户邮箱不存在!";
					desc = "2";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		json.put("desc", desc);
		json.put("info", info);
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String email_manage() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		String result = "";
		try{
			List<Map<String, Object>> emailList = new ArrayList<Map<String, Object>>();
			CustDao cust = new CustDao();
			emailList = cust.getMailByUser(user.get_id());
			req.setAttribute("emailList", emailList);
			result = "success";
		}catch (Exception e) {
			result = "error";
			e.printStackTrace();
		}
		return result;
	}
	
	public void edit_email_pwd() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		JSONObject json = new JSONObject();
		try {
			CustDao cust = new CustDao();
			String email = req.getParameter("email");
			String pwd = req.getParameter("pwd");
			cust.updateMailPwdByUserAndMail(email, user.get_id(), pwd);
			json.put("desc", "1");
			json.put("info", "修改成功!");
			
		} catch (Exception e) {
			json.put("desc", "2");
			json.put("info", "修改失败!");
			e.printStackTrace();
		}
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void email_connect_test() {
		JSONObject json = new JSONObject();
		String info = "测试成功!";
		String desc = "1";
		try {
			String email = req.getParameter("email");
			CustDao cust = new CustDao();
			List<Map<String, Object>> mailConfig = cust.getMailConfig(email);
			if(mailConfig == null || mailConfig.size() == 0) {
				info = "该邮件不存在,请联系管理员！";
				desc = "2";
			}else {
				SentMailInfoBean sentmsg = new SentMailInfoBean();
				sentmsg.setHost(mailConfig.get(0).get("mail_user_host").toString());
				sentmsg.setPassword(mailConfig.get(0).get("mail_user_pwd").toString());
				sentmsg.setUsername(mailConfig.get(0).get("mail_user_username").toString());
				boolean b = sendMail.testConnect(sentmsg);
				if(!b) {
					desc = "2";
					info = "邮件配置失败，请验证密码或联系管理员!";
				}
			}
			json.put("desc", desc);
			json.put("info", info);
		} catch (Exception e) {
			json.put("desc", "2");
			json.put("info", "测试失败!");
			e.printStackTrace();
		}
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void select_cc_rm() {
		JSONObject json = new JSONObject();
		String info = "测试成功!";
		String desc = "1";
		try {
			String rm_name = req.getParameter("rm_name");
			userDao userDao = new userDao();
			List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
			userList = userDao.getUserByRealname(rm_name);
			for(Map<String, Object> u: userList) {
				if(u.get("email") != null && checkEmail(u.get("email").toString())) {
					u.put("is_exist_email", "是");
				}else {
					u.put("is_exist_email", "否");
				}
			}
			json.put("desc", "1");
			json.put("userList", userList);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String cust_call() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		String result = "";
		try{			
			req.setAttribute("status", "1");
			req.setAttribute("host", Constants.CALLCENTER_HOST);
			req.setAttribute("port", Constants.CALLCENTER_PORT);
			req.setAttribute("username", user.getUsername());
			req.setAttribute("ext", user.getExtno());
			result = "success";
		}catch (Exception e) {
			result = "error";
			e.printStackTrace();
		}
		return result;
	}
	
	public String call_manage() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		String result = "";
		try{
			List<Map<String, Object>> callDistribList = new ArrayList<Map<String, Object>>();
			CustDao cust = new CustDao();
			callDistribList = cust.getCallDistribList();
			req.setAttribute("status", 1);
			req.setAttribute("callDistribList", callDistribList);
			result = "success";
		}catch (Exception e) {
			result = "error";
			e.printStackTrace();
		}
		return result;
	}
	
	public String show_call_distrib_page() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		String result = "";
		try{
			List<Map<String, Object>> custServiceList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> prodInfoList = new ArrayList<Map<String, Object>>();
			CustDao custDao = new CustDao();
			userDao userDao = new userDao();
			
			prodInfoList = custDao.getProdInfoExistOrder();
			custServiceList = userDao.getCustServiceUser();
			Map<String, List<Map<String, Object>>> dictMap = Constants.dictMapByType;
			req.setAttribute("custServiceList", custServiceList);
			req.setAttribute("service_id", req.getParameter("service_id"));
			req.setAttribute("custtypeList", dictMap.get("custtype"));
			req.setAttribute("prodInfoList", prodInfoList);
			result = "success";
		}catch (Exception e) {
			result = "error";
			e.printStackTrace();
		}
		return result;
	}
	
	public void call_search_cust() {
		JSONObject json = new JSONObject();
		try {
			List<Map<String, Object>> callCustList = new ArrayList<Map<String, Object>>();
			CustDao cust = new CustDao();
			String cust_type = req.getParameter("cust_type");
			String cust_name = req.getParameter("cust_name");
			String prod_no = req.getParameter("prod_no");
			String order_no = req.getParameter("order_no");
			if(cust_type != null && !"".equals(cust_type)) {
				if("1".equals(cust_type)) {
					callCustList = cust.getCustOrderList(prod_no, order_no, cust_name);
				}else {
					callCustList = cust.getOrgOrderList(prod_no, order_no, cust_name);
				}
			}else {
				callCustList = cust.getOrderListByProdNo(prod_no, order_no);
				for(Map<String, Object> m: callCustList) {
					if("1".equals((String)m.get("cust_type"))) {
						List<Map<String, Object>> list = cust.getCustListById((Long)m.get("cust_no"));
						m.put("cust_name", list.get(0).get("cust_name"));
						m.put("real_name", list.get(0).get("real_name"));
					}else {
						OrgDao orgDao = new OrgDao();
						List<Map<String, Object>> list = orgDao.getOrgById((Long)m.get("cust_no"));
						List<Map<String, Object>> custList = cust.getCustListById((Long)m.get("investor_no"));
						m.put("cust_name", list.get(0).get("org_name"));
						m.put("real_name", custList.get(0).get("real_name"));
					}
					
				}
			}
			json.put("desc", "1");
			json.put("callCustList", callCustList);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void call_distrib_cust() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		JSONObject json = new JSONObject();
		String info = "分配成功!";
		String desc = "1";
		try {
			String service_id = req.getParameter("user_id");
			if(service_id == null || "".equals(service_id)) {
				desc = "2";
				info = "客户人员不能为空!";
			}
			String service_name = req.getParameter("user_name");
			String[] orderNos = req.getParameterValues("orderNos[]");
			String distrib_id = req.getParameter("distrib_id");
			CustDao cust = new CustDao();
			if("1".equals(desc)) {
				cust.insertCustDistrib(Long.parseLong(service_id), service_name, user.get_id(), user.getRealName(), orderNos, distrib_id);
			}
			json.put("desc", desc);
			json.put("info", info);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void call_edit_info() {
		JSONObject json = new JSONObject();
		try {
			String id = req.getParameter("id");
			CustDao custDao = new CustDao();
			List<Map<String, Object>> callCustList = new ArrayList<Map<String, Object>>();
			if(id != null && !"".equals(id)) {
				callCustList = custDao.getCallCustListById(Long.parseLong(id));
			
				json.put("callCustList", callCustList);
			}
			json.put("desc", 1);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void del_call_distrib() {
		JSONObject json = new JSONObject();
		String info = "删除成功!";
		String desc = "1";
		try {
			String call_cust_id = req.getParameter("call_cust_id");
			CustDao custDao = new CustDao();
			
			if(call_cust_id != null && !"".equals(call_cust_id)) {
				custDao.delCallCust(Long.parseLong(call_cust_id));
			}else {
				info = "错误，删除失败!";
				desc = "2";
			}
			json.put("desc", desc);
			json.put("info", info);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void call_phone() {
		JSONObject json = new JSONObject();
		String info = "呼叫成功!";
		String desc = "1";
		User user = (User) req.getSession().getAttribute(Constants.USER);
		try {
			String order_no = req.getParameter("order_no");
			CustDao custDao = new CustDao();
			OrgDao orgDao = new OrgDao();
			List<Map<String, Object>> custList = new ArrayList<Map<String, Object>>();
			if(order_no != null && !"".equals(order_no)) {
				custList = custDao.getVerifyInfoByOrderNo(Long.parseLong(order_no), user.get_id());
				if(custList.size() > 0) {
					if("1".equals((String) custList.get(0).get("cust_type"))) {
						List<Map<String, Object>> list = custDao.getCustListById((Long) custList.get(0).get("cust_no"));
						custList.get(0).put("cust_name", list.get(0).get("cust_name"));
						custList.get(0).put("cust_cell", list.get(0).get("cust_cell"));
						custList.get(0).put("investor_name", "");
						custList.get(0).put("org_legal", "");
					}else {
						List<Map<String, Object>> list = orgDao.getOrgById((Long) custList.get(0).get("cust_no"));
						custList.get(0).put("cust_name", list.get(0).get("org_name"));
						custList.get(0).put("org_legal", list.get(0).get("org_legal"));
						list = custDao.getCustListById((Long) custList.get(0).get("investor_no"));
						custList.get(0).put("investor_name", list.get(0).get("real_name"));
						custList.get(0).put("cust_cell", list.get(0).get("cust_cell"));
					}
					String cust_cell = (String) custList.get(0).get("cust_cell");
					phoner = new MyPhoner(Constants.CALLCENTER_HOST, Constants.CALLCENTER_PORT);
					String dial = phoner.Dial(cust_cell, "", user.getExtno());
					json.put("dial", JSON.parse(dial));
					custList.get(0).put("cust_cell", "");
					json.put("verifyInfo", custList.get(0));
				}else {
					info = "错误,呼叫失败";
					desc = "2";
				}
			}else {
				info = "错误，呼叫失败!";
				desc = "2";
			}
			
			json.put("desc", desc);
			json.put("info", info);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void show_call_phone_detail() {
		JSONObject json = new JSONObject();
		User user = (User) req.getSession().getAttribute(Constants.USER);
		try {
			String order_no = req.getParameter("order_no");
			CustDao custDao = new CustDao();
			OrgDao orgDao = new OrgDao();
			List<Map<String, Object>> custList = new ArrayList<Map<String, Object>>();
			if(order_no != null && !"".equals(order_no)) {
				custList = custDao.getVerifyInfoByOrderNo(Long.parseLong(order_no), user.get_id());
				if(custList.size() > 0) {
					if("1".equals((String) custList.get(0).get("cust_type"))) {
						List<Map<String, Object>> list = custDao.getCustListById((Long) custList.get(0).get("cust_no"));
						custList.get(0).put("cust_name", list.get(0).get("cust_name"));
						custList.get(0).put("cust_cell", list.get(0).get("cust_cell"));
						custList.get(0).put("investor_name", "");
						custList.get(0).put("org_legal", "");
					}else {
						List<Map<String, Object>> list = orgDao.getOrgById((Long) custList.get(0).get("cust_no"));
						custList.get(0).put("cust_name", list.get(0).get("org_name"));
						custList.get(0).put("org_legal", list.get(0).get("org_legal"));
						list = custDao.getCustListById((Long) custList.get(0).get("investor_no"));
						custList.get(0).put("investor_name", list.get(0).get("real_name"));
						custList.get(0).put("cust_cell", list.get(0).get("cust_cell"));
					}
					custList.get(0).put("cust_cell", "");
					json.put("verifyInfo", custList.get(0));
				}
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void remark_call() {
		JSONObject json = new JSONObject();
		String info = "备注成功!";
		String desc = "1";
		User user = (User) req.getSession().getAttribute(Constants.USER);
		try {
			String order_no = req.getParameter("order_no");
			String is_self = req.getParameter("is_self");
			String is_id_no = req.getParameter("is_id_no");
			String id_no = req.getParameter("id_no");
			String is_id_address = req.getParameter("is_id_address");
			String id_address = req.getParameter("id_address");
			String is_partnership = req.getParameter("is_partnership");
			String partnership = req.getParameter("partnership");
			String is_order_amount = req.getParameter("is_order_amount");
			String order_amount = req.getParameter("order_amount");
			String is_mail_address = req.getParameter("is_mail_address");
			String mail_address = req.getParameter("mail_address");
			String work_address = req.getParameter("work_address");
			String remark = req.getParameter("remark");
			String cust_email = req.getParameter("cust_email");
			String is_email = req.getParameter("is_email");
			CustDao custDao = new CustDao();
			if(is_self != null && "1".equals(is_self) && !"2".equals(is_email)) {
				if(cust_email == null || !checkEmail(cust_email)) {
					desc = "2";
					info ="邮件地址格式不正确!";
				}
			}
			if("1".equals(desc)) {
				if(order_no != null && !"".equals(order_no)) {
					Long id = custDao.insertCallRecode(remark, user.getUsername(), user.get_id(), Long.parseLong(order_no), 
							cust_email, is_self, is_id_no, id_no, is_id_address, id_address, is_partnership, partnership,
							is_order_amount, order_amount, is_mail_address, mail_address, work_address, is_email);
				}else{
					info = "添加失败!";
					desc = "2";
				}
			}
			json.put("desc", desc);
			json.put("info", info);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void query_cust_call() {
		JSONObject json = new JSONObject();
		String info = "备注成功!";
		String desc = "1";
		User user = (User) req.getSession().getAttribute(Constants.USER);
		int PageNum = 1;
		int pageSize = 10;
		try {
			if(req.getParameter("PageNum") != null && !"".equals(req.getParameter("PageNum"))) {
				PageNum = Integer.parseInt(req.getParameter("PageNum"));
			}
			if(req.getParameter("PageSize") != null && !"".equals(req.getParameter("PageSize"))) {
				pageSize = Integer.parseInt(req.getParameter("PageSize"));
			}
			String cust_name = req.getParameter("cust_name");
			String prod_name = req.getParameter("prod_name");
			CustDao custDao = new CustDao();
			int offset = (PageNum - 1) * pageSize;
			List<Map<String, Object>> callCustList = new ArrayList<Map<String, Object>>();
			callCustList = custDao.getCallCustListByServiceId(user.get_id(), offset, pageSize, cust_name, prod_name);
			Long count = custDao.getCallCustListCount(user.get_id(), cust_name, prod_name);
			if(callCustList.size() > 0 ) {
				json.put("status", 1);
				json.put("callCustList", callCustList);
				json.put("pagenum", PageNum);
				json.put("totalNum", count);
			} else {
				json.put("status", "2");
				json.put("callCustList", "未找到相关数据");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add_recording() {
		JSONObject json = new JSONObject();
		User user = (User) req.getSession().getAttribute(Constants.USER);
		String info = "录音保存成功";
		String desc = "1";
		try {
			String order_no = req.getParameter("order_no");
			String record_id = req.getParameter("record_id");
			if(order_no == null || "".equals(order_no.trim())) {
				info = "订单号不存在，录音保存失败!";
				desc = "2";
			}
			if(record_id == null || "".equals(record_id.trim())) {
				info = "获取录音记录失败";
				desc = "2";
			}
			if("1".equals(desc)) {
				CustDao custDao = new CustDao();
				custDao.insertCustRecord(user.get_id(), user.getRealName(), Long.parseLong(order_no), record_id);
			}
		}catch (Exception e) {
			e.printStackTrace();
			info = "录音保存失败";
			desc = "2";
		}
		try {
			json.put("desc", desc);
			json.put("info", info);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String record_manage() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		if(user == null){
			return "index";		
		}
		String result = "";
		try{
			List<Map<String, Object>> callDistribList = new ArrayList<Map<String, Object>>();
			CustDao cust = new CustDao();
			callDistribList = cust.getCallDistribList();
			req.setAttribute("status", 1);
			req.setAttribute("callDistribList", callDistribList);
			result = "success";
		}catch (Exception e) {
			result = "error";
			e.printStackTrace();
		}
		return result;
	}
	
	public void call_recorded() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		JSONObject json = new JSONObject();
		String desc = "1";
		try{
			List<Map<String, Object>> callRecordList = new ArrayList<Map<String, Object>>();
			CustDao cust = new CustDao();
			String order_no = req.getParameter("order_no");
			if(order_no == null || "".equals(order_no.trim())) {
				desc = "2";
			}
			if("1".equals(desc)) {
				callRecordList = cust.getCallRecordByOrdeno(Long.parseLong(order_no));
				json.put("callRecordList", callRecordList);
				json.put("recordHost", Constants.RECORDING_HOST+":"+Constants.RECORDING_PORT);
			}
			json.put("desc", desc);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void query_call_record() {
		JSONObject json = new JSONObject();
		String info = "备注成功!";
		String desc = "1";
		User user = (User) req.getSession().getAttribute(Constants.USER);
		int PageNum = 1;
		int pageSize = 10;
		try {
			if(req.getParameter("PageNum") != null && !"".equals(req.getParameter("PageNum"))) {
				PageNum = Integer.parseInt(req.getParameter("PageNum"));
			}
			if(req.getParameter("PageSize") != null && !"".equals(req.getParameter("PageSize"))) {
				pageSize = Integer.parseInt(req.getParameter("PageSize"));
			}
			String order_no = req.getParameter("order_no");
			CustDao custDao = new CustDao();
			int offset = (PageNum - 1) * pageSize;
			List<Map<String, Object>> recordList = new ArrayList<Map<String, Object>>();
			int count = 0;
			if(user.getRole_id() == 13) {
				recordList = custDao.getCallRecordList(user.get_id(), order_no, pageSize, offset);
				List<Map<String, Object>> callRecord = custDao.getCallRecordCount(user.get_id(), order_no);
				count = callRecord.size();
			}else if(user.getRole_id() == 14 || user.getRole_id() == 4 || user.getRole_id() == 5) {
				recordList = custDao.getAllCallRecordList(order_no, pageSize, offset);
				List<Map<String, Object>> callRecord = custDao.getAllCallRecordCount(order_no);
				count =callRecord.size();
			}
			if(recordList.size() > 0 ) {
				json.put("status", 1);
				json.put("recordList", recordList);
				json.put("pagenum", PageNum);
				json.put("totalNum", count);
			} else {
				json.put("status", "2");
				json.put("callCustList", "未找到相关数据");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void hide_secret_prop(List<Map<String, Object>> list, User user) {
		for(int i=0; i<list.size(); i++) {
			if(user.get_id() != (Long)list.get(i).get("sales_id")) {
				list.get(i).put("cust_cell", "********");
				list.get(i).put("email", "********");
				list.get(i).put("address", "********");
			}
		}
	}
	
	public static boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
}
