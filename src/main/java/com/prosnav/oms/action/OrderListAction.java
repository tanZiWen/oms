package com.prosnav.oms.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
import javax.servlet.http.HttpSession;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Else;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.GpDao;
import com.prosnav.oms.dao.OrderDao;
import com.prosnav.oms.dao.CustDao;
import com.prosnav.oms.dao.DictDao;
import com.prosnav.oms.dao.userDao;
import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.util.Download;
import com.prosnav.oms.util.jdbcUtil;
import com.prosnav.oms.util.mailCache;
import com.prosnav.oms.util.sendMail;

import sun.java2d.pipe.SpanClipRenderer;

public class OrderListAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	JSONObject json = new JSONObject();
	OrderDao ord = new OrderDao();
	Map<String, Object> map = new HashMap<String, Object>();
	HttpSession session = req.getSession();
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 查询订单列表信息
	public String orderList() {
		
		String result = "";
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		User user = (User) session.getAttribute(Constants.USER);
		if(user==null){
			return "index";
		}
		long role_id = user.getRole_id();
		//System.out.println("role_id======="+role_id);
		req.setAttribute("role_id", role_id);
		int cou=0;
		try {
			OrderDao od = new OrderDao();
			if (role_id == 1) {// 销售
				//list = od.sale_orderInfoSelect(10, 0, user.get_id());
				cou=od.sale_orderInfoSelect_cou(user.get_id());
			} else if (role_id == 2) {
				//list = od.team_sale_orderInfoSelect(10, 0, user.get_id());
				cou=od.team_sale_orderInfoSelect_cou(user.get_id());
			} else if (role_id == 3) {
				//list = od.org_team_sale_orderInfoSelect(10, 0, user.get_id());
				cou=od.org_team_sale_orderInfoSelect_cou(user.get_id());
			} else if (role_id == 4) {
				//list = od.orderInfoSelect(user.get_id(),10, 0);
				cou=od.orderInfoSelect_cou(user.get_id());
				
			} else if(role_id == 5){
				//list = od.orderInfoSelect(10, 0);
				cou=od.orderInfoSelect_cou();
			}else if(role_id == 6){
				//list = od.prod_orderInfoSelect(user.get_id(), 10, 0);
				cou=od.prod_orderInfoSelect_cou(user.get_id());
			}else if(role_id == 7){
				//list = od.orderInfoSelect(10, 0);
				cou=od.orderInfoSelect_cou();
			}else if(role_id == 8){
				//list = od.orderInfoSelect(10, 0);
				cou=od.orderInfoSelect_cou();
			}else if(role_id == 9){
				//list = od.orderInfoSelect(10, 0);
				cou=od.orderInfoSelect_cou();
			}else if(role_id == 15) {
				cou=od.orderInfoSelect_cou();
			}
			result = "success";
			
			
			req.setAttribute("status", "1");
			req.setAttribute("totalNum", cou);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;

	}
	
	// 分页查询订单列表信息
		public void orderPageList() {
			
			/*String result = "";*/
			String pa=req.getParameter("pageNum");
			int page=0;
			if(pa!=null&&!pa.equals("")){
				page=(Integer.parseInt(pa)-1)*10;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			User user = (User) session.getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			req.setAttribute("role_id", role_id);
			int cou=0;
			try {
				OrderDao od = new OrderDao();
				if (role_id == 1) {// 销售
					list = od.sale_orderInfoSelect(10, page, user.get_id());
					cou=od.sale_orderInfoSelect_cou(user.get_id());
				} else if (role_id == 2) {
					list = od.team_sale_orderInfoSelect(10, page, user.get_id());
					cou=od.team_sale_orderInfoSelect_cou(user.get_id());
				} else if (role_id == 3) {
					list = od.org_team_sale_orderInfoSelect(10,page, user.get_id());
					cou=od.org_team_sale_orderInfoSelect_cou(user.get_id());
				} else if (role_id == 4) {
					list = od.orderInfoSelect(user.get_id(),10,page);
					cou=od.orderInfoSelect_cou(user.get_id());
				} else if(role_id == 5){
					list = od.orderInfoSelect(10, page);
					cou=od.orderInfoSelect_cou();
				}else if(role_id == 6){
					list = od.prod_orderInfoSelect(user.get_id(), 10, page);
					cou=od.prod_orderInfoSelect_cou(user.get_id());
				}else if(role_id == 7){
					list = od.orderInfoSelect(10,page);
					cou=od.orderInfoSelect_cou();
				}else if(role_id == 8){
					list = od.orderInfoSelect(10, page);
					cou=od.orderInfoSelect_cou();
				}else if(role_id == 9){
					list = od.orderInfoSelect(10, page);
					cou=od.orderInfoSelect_cou();
				}else if(role_id == 15) {
					list = od.orderInfoSelect(10, page);
					cou=od.orderInfoSelect_cou();
				}
			
				if (list != null && list.size() > 0) {
					//对标费 千分位规范
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i).get("magt_fee")!=null&&list.get(i).get("magt_fee")!=""){
						String magt_fee1 = list.get(i).get("magt_fee").toString();
						
						BigDecimal b = new BigDecimal(magt_fee1);
						DecimalFormat d1 = new DecimalFormat("#,##0.######");
						d1.setRoundingMode(RoundingMode.FLOOR);
						list.get(i).put("magt_fee1", d1.format(b));
						}else{
							list.get(i).put("magt_fee1", "0");
						}
						//对认缴金额千分位规范
						if(list.get(i).get("order_amount")!=null&&list.get(i).get("order_amount")!=""){
							String order_amount = list.get(i).get("order_amount").toString();						
							BigDecimal b = new BigDecimal(order_amount);
							DecimalFormat d1 = new DecimalFormat("#,##0.######");
							d1.setRoundingMode(RoundingMode.FLOOR);
							list.get(i).put("order_amount", d1.format(b));
							}else{
								list.get(i).put("buy_fee1", "0");
							}
					}
					
					json.put("status", "1");
					for(int i=0; i< list.size(); i++) {
						list.get(i).put("order_no", list.get(i).get("order_no").toString());
						list.get(i).put("order_version", list.get(i).get("order_version").toString());
					}
					json.put("orderList", list);
					
					/*req.setAttribute("size", cou);*/

				} else {
					json.put("status", "2");
					json.put("orderList", "未找到相关数据");
				}
				json.put("PageNum", pa);
				json.put("totalNum", cou);
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				/*result = "error";*/
				req.setAttribute("msg", "系统异常");
			}
		

		}

	// 业绩分配
	@SuppressWarnings("unchecked")
	public void achievementChan() {
		String obj = req.getParameter("data");
		JSONObject json = JSONObject.parseObject(obj);
		List<Map<String, Object>> list = (List<Map<String, Object>>) json
				.get("data");
		int j = list.size() - 1;
		Object orderno = list.get(j).get("order_no");
		long order_no = Long.parseLong(orderno.toString());
		Object orderversion = list.get(j).get("order_version");
		long order_version = Long.parseLong(orderversion.toString());
		try {
			if (list != null && list.size() > 0) {
				OrderDao od = new OrderDao();
				od.achievementChange(list, order_no, order_version);
				json.put("msg", "更改成功");
				json.put("desc", "1");
			} else {
				json.put("msg", "更改失败");
				json.put("desc", "0");
			}

			resp.setContentType("text;charset=utf-8");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
		}

	}

	// 查询订单列表信息
	public String orderEdit() {
		String result = "";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> custSelectList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> orgSelectList = new ArrayList<Map<String, Object>>();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			String orderno = req.getParameter("order_no");
			if (orderno == "" | orderno == null)
				orderno = "0";
			long order_no = Long.parseLong(orderno);
			String orderversion = req.getParameter("order_version");
			if (orderversion == "" | orderversion == null)
				orderversion = "0";
			long order_version = Long.parseLong(orderversion);
			OrderDao od = new OrderDao();
			list = od.orderEditSelect(order_no,order_version);
			custSelectList = od.custSelect();
			if (custSelectList != null && custSelectList.size() > 0) {
				req.setAttribute("custSelectList", custSelectList);
			}
			orgSelectList = od.orgSelect();
			if (orgSelectList != null && orgSelectList.size() > 0) {
				req.setAttribute("orgSelectList", orgSelectList);
			}
			DictDao dict = new DictDao();
			
			List<Map<String, Object>> areaList = dict.queryOrder("dist");
			
			if (areaList != null && areaList.size() > 0) {
				req.setAttribute("areaList", areaList);
			}
			List<Map<String, Object>> prod_list = od.product();
			if(list!=null && list.size() > 0){
				req.setAttribute("prod_id", list.get(0).get("prod_no"));
				req.setAttribute("prod_list", prod_list);
				long prod_id = (Long) list.get(0).get("prod_no");
				List<Map<String, Object>> lp_list = od.order_prod(prod_id);
				if(lp_list!=null){
					req.setAttribute("lp_list", lp_list);
					
				}
			}
			if (list != null && list.size() > 0) {
				req.setAttribute("orderEdit", list.get(0));
				req.setAttribute("cust_type", list.get(0).get("cust_type"));
				req.setAttribute("cust_no",list.get(0).get("cust_no") );
				result = "orderEdit";
			} else {
				result = "error";
				req.setAttribute("msg", "系统异常");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;

	}
	
//模糊查询客户姓名
	public void sealect_custna(){
		List<Map<String, Object>> custOrgList = new ArrayList<Map<String, Object>>();
		String result="";
		String cust_name=req.getParameter("cust_name");
		String cust_type=req.getParameter("cust_type");
		OrderDao od=new OrderDao();
		try{
		custOrgList=od.custOrgSelect(cust_type, cust_name);
		if(custOrgList!=null&&custOrgList.size()>0){
			json.put("custOrgList", custOrgList);
			json.put("status", "1");
		} else {
			json.put("status", "2");
			json.put("list", "未找到相关数据");
		}
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
			result="error";
		}
	}
	

	// 提交审批订单信息
	public void orderSave() {
		try {
			int i = 0;
			long part_comp = 0;
			double order_amount = 0;
			double acount_fee = 0;
			double buy_fee = 0;
			double pri_fee = 0;
			User user = (User) req.getSession().getAttribute(Constants.USER);
			String cust_type = req.getParameter("cust_type");
			String custno = req.getParameter("cust_no");
			long cust_no = Long.parseLong(custno);
			String prod_id = req.getParameter("prod_no");
			String partcomp = req.getParameter("part_comp");
			if (!"".equals(partcomp) && partcomp != null) {
				part_comp = Long.parseLong(partcomp);
			}
			String order_type = req.getParameter("order_type");
			String orderamount = req.getParameter("order_amount").replaceAll(",", "");
			if (!"".equals(orderamount)) {
				order_amount = Double.parseDouble(orderamount);
			}
			String contract_type = req.getParameter("contract_type");
			String contract_no = req.getParameter("contract_no");
			String is_id = req.getParameter("is_id");
			String id_no = req.getParameter("id_no");
			String prod_diffcoe = req.getParameter("prod_diffcoe1");
			String prifee = req.getParameter("pri_fee").replaceAll(",", "");
			if (!"".equals(prifee)) {
				pri_fee = Double.parseDouble(prifee);
			}
			String acountfee = req.getParameter("acount_fee").replaceAll(",", "");
			if (!"".equals(acountfee)) {
				acount_fee = Double.parseDouble(acountfee);
			}
			String buyfee = req.getParameter("buy_fee").replaceAll(",", "");
			if (!"".equals(buyfee)) {
				buy_fee = Double.parseDouble(buyfee);
			}
			String start_fee = req.getParameter("start_fee").replaceAll(",", "");
			String bank_no = req.getParameter("bank_no");
			String bank_card = req.getParameter("bank_card");
			String remark = req.getParameter("remark");
			String orderversion = req.getParameter("order_version");
			long old_order_version = Long.parseLong(orderversion);
			String orderno = req.getParameter("order_no");
			String area = req.getParameter("area");
			String cust_email = req.getParameter("cust_email");
			String mail_address = req.getParameter("mail_address");
			String work_address = req.getParameter("work_address");
			String cust_cell = req.getParameter("cust_cell");
			String extra_award = req.getParameter("extra_award");
			String cust_address = req.getParameter("cust_address");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
			Date first_pay_time = sdf.parse(req.getParameter("first_pay_time"));
			String comment = req.getParameter("comment");
			Date entry_time = sdf.parse(req.getParameter("entry_time"));
			long order_no = Long.parseLong(orderno);
			long prod_no = Long.parseLong(prod_id);
			List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
			OrderDao od = new OrderDao();
			// 获取版本号
			Long order_version = jdbcUtil.seq();
			// 获取原订单信息
			orderList = od.oldOrder(order_no, old_order_version);
			if (orderList != null && orderList.size() > 0) {
				String late_join_fee = "";
				late_join_fee = (String) orderList.get(0).get("late_join_fee");
				
				Object delayfee = orderList.get(0).get("delay_fee");
				double delay_fee = 0;
				if(delayfee != null) {
					delay_fee = Double.parseDouble(delayfee.toString());
				}
//				Date entry_time = (Date) orderList.get(0).get("entry_time");
				
				Object breakfee = orderList.get(0).get("break_fee");
				double break_fee = 0;
				if(breakfee != null) {
					break_fee = Double.parseDouble(breakfee.toString());
				}
				long email_id = jdbcUtil.seq();
				long register = (Long) orderList.get(0).get("register");
				// 将原订单设为无效
				int j = od.orderChange(order_no, old_order_version);
				if (j > 0) {
					userDao udao = new userDao();
					List<Map<String, Object>> userlist = udao.getTeamUser(user.get_id());
					String email = "";
					String real_name = "";
					if (userlist != null && userlist.size() > 0) {
						email = (String) userlist.get(0).get("email");
						real_name = (String) userlist.get(0).get("real_name");
					}
						if (!"".equals(email)&&!"".equals(real_name)) {
							String message = req.getParameter("message");
							SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");
							SentMailInfoBean sentmsg = new SentMailInfoBean();
							// sentmsg.setFirmId("001");
							sentmsg.setSubjectId("" + email_id);
							sentmsg.setSentMailaddr(mailCache.from);
							sentmsg.setReviceMailaddr(email);
							sentmsg.setMail_busstype("更改订单审批");
							sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.mailorder");
							sentmsg.setMailContent(
									"更改订单：					\n" + 
									"尊敬的" + real_name+ "： 					\n" 
									+ "您好， " + sdfg.format(new Date()) + ","
									+ user.getRealName() + "在OMS系统更改了新订单，现等待您审批。\n"
									+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
									+ "---InfoBegin---					\n" 
									+message 
									+ "---InfoEnd--- \n");
							sentmsg.setSubject("更改订单审批");
							sendMail.sendMessage(sentmsg, true);
							//插入订单
							od.orderEditSave(area, part_comp, contract_type, contract_no, is_id, id_no, start_fee,
									late_join_fee, remark, bank_no, bank_card, "1", order_amount, pri_fee, acount_fee,
									delay_fee, entry_time, break_fee, order_version, buy_fee, order_type, order_no, cust_no,
									prod_no, email_id, old_order_version, register, prod_diffcoe,cust_type, cust_email, mail_address,
									work_address, cust_cell, extra_award, first_pay_time, comment, cust_address);
							json.put("status", "1");
							json.put("msg", "提交审批成功，请耐心等待审批");
							//将记录插入到task表中
							add(req, email_id, user.get_id());
						}else{
							json.put("status", "2");
							json.put("msg", "未找到审核人的邮箱或者是姓名，不能更改");
						}
					
				} else {
					json.put("status", "2");
					json.put("msg", "插入失败");
				}
			} else {
				json.put("status", "2");
				json.put("msg", "插入失败");
			}
			// json.put("success", i);

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	

	/**
	 * 根据币种查询
	 */
	public void ordermoney() {
		JSONObject json = new JSONObject();
		try {
			String prod_status = req.getParameter("prod_status");
			List<Map<String, Object>> list = ord.orderSelect(prod_status, 10, 0);
			if(list.size()>0){
				json.put("list", list);
				json.put("status", "1");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 首页---- 根据条件查询订单信息
	public void orderSelect() {
		List<Map<String, Object>> order_list = new ArrayList<Map<String, Object>>();
		OrderDao od = new OrderDao();
		User user = (User) session.getAttribute(Constants.USER);
		String pa=req.getParameter("pageNum");
		int page=0;
		if(pa!=null&&!pa.equals("")){
			page=(Integer.parseInt(pa)-1)*10;
		}
		long role_id = user.getRole_id();
		int cou=0;
		try {
			String prod_status = req.getParameter("status");
			String cust_name = req.getParameter("cust_name");
			String customer_cell = req.getParameter("customer_cell");
			String sales_name = req.getParameter("sales_name");
			String prod_name = req.getParameter("prod_name");
			String order_no = req.getParameter("order_no");
			if(role_id==1){
				order_list = od.sale_orderSelect(prod_status, cust_name, 
						customer_cell, sales_name, prod_name, 10, page, user.get_id(), order_no);
				cou=od.sale_orderSelect_cou(prod_status, cust_name, 
						customer_cell, sales_name, prod_name,  user.get_id(), order_no);
			}else if(role_id==2){
				order_list = od.team_sale_orderSelect(prod_status, cust_name, customer_cell, 
						sales_name, prod_name, 10, page, user.get_id(), order_no);
				cou= od.team_sale_orderSelect_cou(prod_status, cust_name, customer_cell, 
						sales_name, prod_name, user.get_id(), order_no);
			}else if(role_id==3){
				order_list = od.org_orderSelect(prod_status, cust_name, customer_cell,
						sales_name, prod_name, 10, page, user.get_id(), order_no);
				cou= od.org_orderSelect_cou(prod_status, cust_name, customer_cell,
						sales_name, prod_name,  user.get_id(), order_no);
			}else if(role_id==4){
				order_list = od.option_orderSelect(prod_status, cust_name, customer_cell, 
						sales_name, prod_name, user.get_id(), 10,page, order_no);
				cou= od.option_orderSelect_cou(prod_status, cust_name, customer_cell, 
						sales_name, prod_name, user.get_id(), order_no);
			}else if(role_id==5){
				order_list = od.orderSelect(prod_status, cust_name, customer_cell,
						sales_name, prod_name, 10, page, order_no);
				cou= od.orderSelect_cou(prod_status, cust_name, customer_cell,
						sales_name, prod_name, order_no);
			}else if(role_id==6){
				order_list = od.prod_orderSelect(prod_status, cust_name,
						customer_cell, sales_name, prod_name, user.get_id(), 10, page);
				cou = od.prod_orderSelect_cou(prod_status, cust_name,
						customer_cell, sales_name, prod_name, user.get_id());
			}else if(role_id==7){
				order_list = od.orderSelect(prod_status, cust_name, customer_cell,
						sales_name, prod_name, 10, page, order_no);
				cou = od.orderSelect_cou(prod_status, cust_name, customer_cell,
						sales_name, prod_name, order_no);
			}else if(role_id==8){
				order_list = od.orderSelect(prod_status, cust_name, customer_cell,
						sales_name, prod_name, 10, page, order_no);
				cou= od.orderSelect_cou(prod_status, cust_name, customer_cell,
						sales_name, prod_name, order_no);
			}else if(role_id==9){
				order_list = od.orderSelect(prod_status, cust_name, customer_cell,
						sales_name, prod_name, 10, page, order_no);
				cou= od.orderSelect_cou(prod_status, cust_name, customer_cell,
						sales_name, prod_name, order_no);
			}else if(role_id == 15) {
				order_list = od.orderSelect(prod_status, cust_name, customer_cell,
						sales_name, prod_name, 10, page, order_no);
				cou= od.orderSelect_cou(prod_status, cust_name, customer_cell,
						sales_name, prod_name, order_no);
			}
				
			
			if (order_list != null && order_list.size() > 0) {
				//对标费进行千分位规范
				for (int i = 0; i < order_list.size(); i++) {
					if(order_list.get(i).get("magt_fee")!=null&&order_list.get(i).get("magt_fee")!=""){
					String magt_fee1 = order_list.get(i).get("magt_fee").toString();
					
					BigDecimal b = new BigDecimal(magt_fee1);
					DecimalFormat d1 = new DecimalFormat("#,##0.######");
					d1.setRoundingMode(RoundingMode.FLOOR);
					order_list.get(i).put("magt_fee1", d1.format(b));
					}else{
						order_list.get(i).put("magt_fee1", "0");
					}
					//对认缴金额进行千分位规范
					if(order_list.get(i).get("order_amount")!=null&&order_list.get(i).get("order_amount")!=""){
						String buy_fee1 = order_list.get(i).get("order_amount").toString();
						
						BigDecimal b = new BigDecimal(buy_fee1);
						DecimalFormat d1 = new DecimalFormat("#,##0.######");
						d1.setRoundingMode(RoundingMode.FLOOR);
						order_list.get(i).put("order_amount", d1.format(b));
						}else{
							order_list.get(i).put("order_amount", "0");
						}
				}
				for(int i=0; i< order_list.size(); i++) {
					order_list.get(i).put("order_no", order_list.get(i).get("order_no").toString());
					order_list.get(i).put("order_version", order_list.get(i).get("order_version").toString());
				}
				json.put("list", order_list);
				json.put("tatolNum", cou);
				json.put("pageNum", pa);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("list", "未找到相关数据");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			// return "error";
		}
	}

	// 付款详情
	public void searchPayment() {
		List<Map<String, Object>> payment_list = new ArrayList<Map<String, Object>>();
		OrderDao od = new OrderDao();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		String order_no = req.getParameter("order_no");
		String orderversion = req.getParameter("order_version");
		if (order_no == "" || order_no == null)
			order_no = "00";
		long order_id = Long.parseLong(order_no);
		if (orderversion == "" || orderversion == null)
			orderversion = "00";
		long order_version = Long.parseLong(orderversion);
		try {
			payment_list = od.paymentDetail(order_id, order_version, 10, 0);
			if (payment_list != null && payment_list.size() > 0) {
				json.put("payment_list", payment_list);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("list", "未找到相关数据");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
		}
	}

	// 订单详情页面--- 订单详情基本信息
	public String order_Detail() {
		
		List<Map<String, Object>> order_list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> RM_list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> payment_list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reward_list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> orderchangelist = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> orderchange_list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> drawback_list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> plan_list = new ArrayList<Map<String, Object>>();
		String change_resaon = "";
		OrderDao od = new OrderDao();
		String result = "";
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			if(user==null){
				return "index";
			}
			long role_id = user.getRole_id();
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			String order_no = req.getParameter("order_no");
			String orderversion = req.getParameter("order_version");
			if (order_no == "" || order_no == null)
				order_no = "00";
			long order_id = Long.parseLong(order_no);
			if (orderversion == "" || orderversion == null)
				orderversion = "00";
			long order_version = Long.parseLong(orderversion);
			/**
			 * 查询订单的基本信息
			 */
			order_list = od.orderDetail(order_id,order_version);
			if (order_list.size() > 0 && order_list != null) {
				String order_type = (String) order_list.get(0)
						.get("order_type");
				String contract_type = (String) order_list.get(0).get(
						"contract_type");

				List<Map<String, Object>> order_typeZidian = od
						.searchZiDian1(order_type);
				String order_type_zidian = "";
				if (order_typeZidian != null && order_typeZidian.size() > 0) {
					order_type_zidian = order_typeZidian.get(0)
							.get("dict_name").toString();
				}
				List<Map<String, Object>> contract_typeZidan = od
						.searchZiDian2(contract_type);
				String contract_type_zidan = "";
				if (contract_typeZidan != null && contract_typeZidan.size() > 0) {
					contract_type_zidan = contract_typeZidan.get(0)
							.get("dict_name").toString();
				}

				req.setAttribute("zidian1", order_type_zidian);
				req.setAttribute("zidian2", contract_type_zidan);
				req.setAttribute("status1", "1");
				req.setAttribute("order_list", order_list.get(0));
			} else {
				req.setAttribute("status1", "2");
				req.setAttribute("order_li", "未查询到该订单信息");
			}
			// 查询回款
			List<Map<String, Object>> return_list = new ArrayList<Map<String, Object>>();
			return_list = od.select(order_id);

			if (return_list != null && return_list.size() > 0) {
				req.setAttribute("lis", return_list);
				req.setAttribute("status9", "1");
			} else {
				req.setAttribute("status9", "0");
				req.setAttribute("list", "未找到相关数据");
			}
			req.setAttribute("role_id", role_id);
			if (role_id == 1) {// 销售
				
				result="orderDetail";
			} else if (role_id == 2) {
				result="orderDetail";
			} else if (role_id == 3) {
				result="orderDetail";
			} else if (role_id == 4) {
				result="orderDetail";
			} else if(role_id == 5){
				result="orderDetail";
			}else if(role_id == 6){
				result="orderDetail";
			}else if(role_id == 7){
				result="orderDetail";
			}else if(role_id == 8){
				return "finance_order_Detail";
			}else if(role_id == 9){
				return "finance_order_Detail";
			}else if(role_id == 15) {
				result="orderDetail";
			}
			
			// 业绩分配查询
			RM_list = od.RMDetail(order_id, order_version, 10, 0);
			if (RM_list != null && RM_list.size() > 0) {
				req.setAttribute("status2", "1");
				req.setAttribute("RM_list", RM_list);
			} else {
				req.setAttribute("status2", "2");
				req.setAttribute("RM_li", "未查询到该销售信息");
			}
			// 付款信息
			payment_list = od.paymentDetail(order_id, order_version, 10, 0);
			int payment_ma = od.searchPayment(order_id);
			if (payment_list != null && payment_list.size() > 0) {
				double pay_amount1 = 0;
				double pay_amount2 = 0;
				Object ob1 = new Object();
				Object ob2 = new Object();
				payment_list.get(0).put("mum", 1);
				if (payment_list.size() > 1) {
					for (int i = 0; i < payment_list.size() - 1; i++) {
						ob1 = payment_list.get(i).get("pay_amount");
						pay_amount1 = Double.parseDouble(ob1.toString());
						int j = i + 1;
						ob2 = payment_list.get(j).get("pay_amount");
						pay_amount2 = Double.parseDouble(ob2.toString());
						payment_list.get(j).put("pay_amount",
								pay_amount1 + pay_amount2);
						payment_list.get(j).put("mum", i + 2);
					}
				}
				req.setAttribute("status3", "1");
				req.setAttribute("payment_list", payment_list);
				req.setAttribute("payment_ma", payment_ma);
			} else {
				req.setAttribute("status3", "2");
				req.setAttribute("payment_li", "未查询到付款信息");
			}
			// 退款信息
			drawback_list = od.drawbackDetail(order_id, order_version, 10, 0);
			if (drawback_list != null && drawback_list.size() > 0) {
				req.setAttribute("status4", "1");
				req.setAttribute("drawback_list", drawback_list);
			} else {
				req.setAttribute("status4", "2");
				req.setAttribute("drawback_li", "未查询到退款信息");
			}

			// 奖金发放记录
			reward_list = od.rewardDetail(order_id, 10, 0);
			if (reward_list != null && reward_list.size() > 0) {
				req.setAttribute("status5", "1");
				req.setAttribute("reward_list", reward_list);
			} else {
				req.setAttribute("status5", "2");
				req.setAttribute("reward_li", "未查询到奖金发放记录信息");
			}
			// 缴款计划记录
			plan_list = od.planDetail(order_id, order_version, 10, 0);
			if (plan_list != null && plan_list.size() > 0) {
				req.setAttribute("status7", "1");
				req.setAttribute("plan_list", plan_list);
			} else {
				req.setAttribute("status7", "2");
				req.setAttribute("plan_li", "未查询到缴款计划");
			}
			// 业绩查询
			List<Map<String, Object>> achievement_list = new ArrayList<Map<String, Object>>();
			achievement_list = od.achievementDetail(order_id, order_version,
					10, 0);
			if (achievement_list != null && achievement_list.size() > 0) {
				req.setAttribute("status8", "1");
				req.setAttribute("achievement_list", achievement_list);
			} else {
				req.setAttribute("status8", "2");
				req.setAttribute("achievement_li", "未查询到业绩信息");
			}
			// 订单变更记录
			orderchangelist = od.orderChangeDetail(order_id,order_version, 10, 0);
			if (orderchangelist != null && orderchangelist.size() > 0) {
				req.setAttribute("status6", "1");
				req.setAttribute("change_list", orderchangelist);
			} else {
				req.setAttribute("status6", "2");
				req.setAttribute("orderchange_li", "未查询到该订单变更记录信息");
			}

			
			//查询退款阶段
			List<Map<String, Object>> back_list = od.back(order_id,order_version );
			if(back_list.size()>0){
				req.setAttribute("back_list", back_list);
				req.setAttribute("desc", 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "数据库异常");
			result = "error";
		}
		return result;
	}

	// 业绩
	public void SearchAchievement() {
		OrderDao od = new OrderDao();
		String order_no = req.getParameter("order_no");
		String orderversion = req.getParameter("order_version");
		if (order_no == "" || order_no == null)
			order_no = "00";
		long order_id = Long.parseLong(order_no);
		if (orderversion == "" || orderversion == null)
			orderversion = "00";
		long order_version = Long.parseLong(orderversion);
		List<Map<String, Object>> achievement_list = new ArrayList<Map<String, Object>>();
		JSONObject order_json = new JSONObject();

		try {
			achievement_list = od.achievementDetail(order_id, order_version,
					10, 0);
			if (achievement_list != null && achievement_list.size() > 0) {
				order_json.put("list", achievement_list);
				order_json.put("status", "1");
			} else {
				order_json.put("status", "2");
				order_json.put("msg", "未找到相关数据");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(order_json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	// 订单详情--
	public void orderRM() {
		List<Map<String, Object>> order_list = new ArrayList<Map<String, Object>>();
		OrderDao od = new OrderDao();
		JSONObject order_json = new JSONObject();
		try {
			String order_no = req.getParameter("order_no");
			if (order_no == "" | order_no == null)
				order_no = "00";
			long order_id = Long.parseLong(order_no);
			order_list = od.orderRM(order_id);
			if (order_list != null && order_list.size() > 0) {
				order_json.put("msg", order_list);
				order_json.put("status", "1");
			} else {
				order_json.put("status", "2");
				order_json.put("msg", "未找到相关数据");
			}

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(order_json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 查询合伙企业
	public void order_prod() {
		try {
			String prod_nos = req.getParameter("prod_no");
			
			if (!"".equals(prod_nos) && prod_nos != null) {
				String prod_no = prod_nos.split(",")[0];
				OrderDao ord = new OrderDao();
				long prodno = Long.parseLong(prod_no);
				List<Map<String, Object>> list = ord.order_prod(prodno);
				if (list.size() > 0 && list != null) {
					json.put("list", list);
					json.put("desc", "1");
				} else {
					json.put("desc", "2");
					json.put("msg", "未找到数据");
				}
			} else {
				json.put("desc", "3");
				json.put("msg", "产品不能为空");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[.0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	/**
	 * 计算指导标费
	 */
	public void Calculation() {
		try {
			String prod_no = req.getParameter("prod_no");
			String order_amount = req.getParameter("order_amount");
			String part_comp = req.getParameter("part_comp");
			if (!"".equals(order_amount) && order_amount != null) {
				OrderDao ord = new OrderDao();
				double orderamount = Double.parseDouble(order_amount);
				long prodno = Long.parseLong(prod_no);
				long lp_id = Long.parseLong(part_comp);
				List<Map<String, Object>> list = ord.Calculation(prodno, lp_id);
				if (list.size() > 0 && list != null) {
					String proddiffcoe = (String) list.get(0).get(
							"prod_diffcoe");
					if(isNumeric(proddiffcoe)) {
						double prod_diffcoe = Double.parseDouble(proddiffcoe);
						double pri_fee = prod_diffcoe * orderamount;
						json.put("pri_fee", pri_fee);
						json.put("prod_diffcoe", prod_diffcoe);
						json.put("desc", "1");
					}else {
						json.put("desc", "2");
						json.put("msg", "未找到系数无法计算");
					}
				} else {
					json.put("desc", "2");
					json.put("msg", "未找到系数无法计算");
				}
			} else {
				json.put("desc", "3");
				json.put("msg", "产品不能为空");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 订单详情--打款记录
	public void orderPay() {
		List<Map<String, Object>> order_list = new ArrayList<Map<String, Object>>();
		OrderDao od = new OrderDao();
		JSONObject order_json = new JSONObject();
		try {
			String order_no = req.getParameter("order_no");
			long order_id = Long.parseLong(order_no);
			order_list = od.orderPay(order_id);
			if (order_list != null && order_list.size() > 0) {
				order_json.put("msg", order_list);
				order_json.put("status", "1");
			} else {
				order_json.put("status", "2");
				order_json.put("msg", "未找到相关数据");
			}

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(order_json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 缴款计划
	public void do_Play() {
		String payamount = req.getParameter("pay_amount").replace(",", "");
		double pay_amount = Double.parseDouble(payamount);
		String stop_time = req.getParameter("stop_time");
		String pay_step = req.getParameter("order_pay_step");
		String pay_per = req.getParameter("pay_per");
		String order_no = req.getParameter("order_no");
		long order_id = Long.parseLong(order_no);
		String orderversion = req.getParameter("order_version");
		long order_version = Long.parseLong(orderversion);
		try {
			OrderDao od = new OrderDao();
			od.doPay(pay_amount, stop_time, pay_step, order_id, pay_per,
					order_version);
			json.put("status", "1");
			/*if (i > 0) {
				json.put("list", i);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("list", "该条记录已存在");
			}*/
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "数据库异常");
		}
		/* return result; */
	}

	/**
	 * 确认打款添加
	 */
	public void payment() {
		String result = "";
		int i = 0;
		String custid = req.getParameter("cust_no");
		long cust_id = Long.parseLong(custid);
		String orderno = req.getParameter("order_no");
		long order_no = Long.parseLong(orderno);
		long order_version = Long.parseLong(req.getParameter("order_version"));
		String cust_type = req.getParameter("cust_type");
		String payamount = req.getParameter("pay_amount").replace(",", "");
		String transaction_type = req.getParameter("transaction_type");
		String remark = req.getParameter("remark");
		double pay_amount = Double.parseDouble(payamount);
		String pay_time = req.getParameter("pay_time");
		String pay_step = req.getParameter("pay_step");
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			OrderDao od = new OrderDao();
			i = od.payment(cust_id, order_no, transaction_type, remark,
					cust_type, pay_amount, pay_time, pay_step,order_version);
			
			List<Map<String, Object>> o = od.getOrderPruductInfo(order_no);
			if("1".equals(o.get(0).get("prod_type"))) {
				userDao userDao = new userDao();
				String[] codes = {"customer_service_manage"};
				List<Map<String, Object>> list = userDao.getUsertByRoleCode(codes);
				SentMailInfoBean sentmsg = new SentMailInfoBean();
				StringBuffer sb = new StringBuffer();
				sb.append("回访外呼任务【缴款确认日");
				sb.append(pay_time);
				sb.append("】【订单号");
				sb.append(order_no);
				sb.append("】");
				sentmsg.setSubject(sb.toString());
				sentmsg.setSentMailaddr(mailCache.from);
				int k = 0;
				String revice_p = "";
				List<String> list2 = new ArrayList<String>();
				for(int j=0; j< list.size(); j++) {
					if(list.get(j).get("email") != null) {
						if(k == 0) {
							revice_p = (String) list.get(j).get("email");
						}else {
							list2.add((String)list.get(j).get("email") );
						}
						k++;
					}
				}
				sentmsg.setReviceMailaddr(revice_p);
				sentmsg.setCcAddrs(list2.toArray(new String[0]));
				sentmsg.setContentType("html");
				sb.setLength(0);
				CustDao custDao = new CustDao();
				list = custDao.getCustListById(cust_id);
				sb.append("您好，<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户 <font color='red'>");
				sb.append(list.get(0).get("cust_name"));
				sb.append("</font>的订单<font color='red'>");
				sb.append(order_no);
				sb.append("</font>于<font color='red'>");
				sb.append(pay_time);
				sb.append("</font>已进行缴款确认，请尽快联系客户进行确认,谢谢。");
				sentmsg.setMailContent(sb.toString());
				sendMail.sendMessage(sentmsg, true);
			}
			if (i > 0) {
				json.put("list", i);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("list", "该条记录已存在");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}

	}

	/**
	 * 添加退款信息
	 */
	public void refund() {
		int i = 0;
		String custid = req.getParameter("cust_no");
		long cust_id = Long.parseLong(custid);
		String orderno = req.getParameter("order_version");
		long order_no = Long.parseLong(orderno);
		String cust_type = req.getParameter("cust_type");
		String payamount = req.getParameter("pay_amount");
		double pay_amount = Double.parseDouble(payamount);
		String pay_time = req.getParameter("pay_time");
		String pay_step = req.getParameter("pay_step");
		long order_id = Long.parseLong(req.getParameter("order_no"));
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			OrderDao od = new OrderDao();
			od.refund(cust_id, order_no, cust_type, pay_amount, pay_time, pay_step, order_id);
			json.put("list", i);
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			req.setAttribute("msg", "系统异常");
		}

	}

	/**
	 * 新建订单个人客户查询
	 */
	public void orderCust() {
		List<Map<String, Object>> order_list = new ArrayList<Map<String, Object>>();
		OrderDao od = new OrderDao();

		try {
			// String state = req.getParameter("state");
			String tel = req.getParameter("tel");
			
			order_list = od.querycust(tel);

			// order_list = od.queryorg(tel);

			// String prod_name = req.getParameter("prod_name");

			// order_list = od.orderSelect(prod_status,
			// cust_name,customer_cell,prod_name);
			if (order_list != null && order_list.size() > 0) {
				json.put("list", order_list);
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
			}

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			// req.setAttribute("msg", "系统异常");
			// return "error";
		}
	}

	/**
	 * 新建订单机构客户查询
	 */
	public void orderOrg() {
		List<Map<String, Object>> order_list = new ArrayList<Map<String, Object>>();
		OrderDao od = new OrderDao();

		try {
			// String state = req.getParameter("state");
			String tel = req.getParameter("tel");
			order_list = od.queryorg(tel);

			if (order_list != null && order_list.size() > 0) {
				
				json.put("list", order_list);
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
			}

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			
			// return "error";
		}
	}
	/**
	 * 客户、机构业绩分配初始查询
	 */
	public void  cust_org_select(){
		try {
			
			OrderDao od = new OrderDao();
			String cust_type = req.getParameter("cust_type");
			long cust_id = Long.parseLong(req.getParameter("cust_no"));
			if("1".equals(cust_type)){
				//long cust_id = Long.parseLong(req.getParameter("cust_no"));
				List<Map<String, Object>> parner_list = od.querycust_parner(cust_id);
				if(parner_list!=null){
					json.put("parner_list", parner_list);
					json.put("desc", "1");
				}else{
					json.put("desc", "0");
					json.put("parner_list", "未找到数据");
				}
			}else if("2".equals(cust_type)){
				//long org_id = Long.parseLong(req.getParameter("cust_no"));
				List<Map<String, Object>> parner_list = od.queryorg_parner(cust_id);
				if(parner_list!=null){
					json.put("parner_list", parner_list);
					json.put("desc", "1");
				}else{
					json.put("desc", "0");
					json.put("parner_list", "未找到数据");
				}
			}
			
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	/**
	 * 指定销售查询
	 */
	public void orderSales() {
		OrderDao od = new OrderDao();
		try {
			String sale_name = req.getParameter("sale_name");
			String  distribution= req.getParameter("distribution");
			String acount_fee = req.getParameter("acount_fee");
			String pri_fee = req.getParameter("pri_fee");
			json.put("distribution", distribution);
			if(!"".equals(acount_fee)&&acount_fee!=null){
				json.put("achievement", acount_fee);
			}else{
				json.put("achievement", pri_fee);
			}
			// String tel = req.getParameter("tel");
			List<Map<String, Object>> list = od.sales_achievement(sale_name);
			if (list !=null&&list.size()>0) {
				
				//List<Map<String, Object>> ach_list = od.sales_achievement(sale_id);
				
				json.put("desc", "1");
				json.put("list", list);
				
			}else{
				json.put("desc", "0");
				json.put("msg", "未找到相关数据");
			}

			

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			// return "error";
		}
	}
	
	
	/**
	 * 添加订单
	 */
	public void orderInsert() {
		OrderDao od = new OrderDao();
		JSONObject js = new JSONObject();
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			if(user!=null){
			String obj = req.getParameter("data");
			JSONObject json = JSONObject.parseObject(obj);
			List<Map<String, Object>> list = (List<Map<String, Object>>) json.get("data");
			String cust_no = (String) list.get(0).get("cust_no");
			if (!"".equals(cust_no) && cust_no != null) {
				od.orderInsert(list,user.get_id(),user);
				js.put("desc", "1");
			} else {
				js.put("desc", "0");
				js.put("msg", "未找到客户");
			}
			}else{
				js.put("desc", "2");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(js);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			// return "error";
		}
	}

	/**
	 * kcy查询
	 */
	public void kycselect() {
		try {
			String cust_no = req.getParameter("cust_no");
			String cust_type = req.getParameter("cust_type");
			
			OrderDao ord = new OrderDao();
			if (!"".equals(cust_no) && cust_no != null) {
				long custno = Long.parseLong(cust_no);
				if("1".equals(cust_type)){
					List<Map<String, Object>> list = ord.kycselect(custno);
					if (list.size() > 0 && list != null) {
						json.put("desc", "1");
						json.put("list", list);
					} else {
						json.put("desc", "2");// 未找到相关数据
						json.put("msg", "未找到相关数据");
					}
				}else if("2".equals(cust_type)){
					List<Map<String, Object>> list = ord.org_cust(custno);
					if (list.size() > 0 && list != null) {
						json.put("desc", "1");
						json.put("list", list);
					} else {
						json.put("desc", "2");// 未找到相关数据
						json.put("msg", "未找到相关数据");
					}
				}
				

			} else {
				json.put("desc", "0");
				json.put("msg", "未找到客户");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 进入新建订单页面
	 * 
	 * @return
	 */
	public String skiporder() {
		OrderDao ord = new OrderDao();
		DictDao dict = new DictDao();
		long prodno=0;
		List<Map<String, Object>> list = ord.product();
		if (list.size() > 0 && list != null) {
			req.setAttribute("status", "1");
			req.setAttribute("list", list);
			prodno=(Long) list.get(0).get("prod_id");
		}
		
		List<Map<String, Object>> lp_list = ord.order_prod(prodno);
		if (lp_list.size() > 0 && lp_list != null) {
			req.setAttribute("lplist", lp_list);
			req.setAttribute("desc", "1");
		} else {
			req.setAttribute("desc", "2");
			req.setAttribute("msg", "未找到数据");
		}
		String ord_type = "ord_type";
		String con_type = "con_type";
		String area_type = "dist";
		String idtype = "idtype";
		List<Map<String, Object>> order_typeList = dict.queryOrder(ord_type);
		List<Map<String, Object>> con_typeList = dict.queryOrder(con_type);
		List<Map<String, Object>> areaList = dict.queryOrder(area_type);
		List<Map<String, Object>> idtypeList = dict.queryOrder(idtype);
		if (order_typeList.size() > 0 && order_typeList != null) {
			req.setAttribute("order_list", order_typeList);
		}
		if (con_typeList.size() > 0 && con_typeList != null) {
			req.setAttribute("con_list", con_typeList);
		}
		if (areaList.size() > 0 && areaList != null) {
			req.setAttribute("areaList", areaList);
		}
		if (idtypeList.size() > 0 && idtypeList != null) {
			req.setAttribute("idtypeList", idtypeList);
		}
		return "success";
	}

	// 更新
	public void returnsave() {
		OrderDao od = new OrderDao();
		try {
			String returncoe = req.getParameter("return_coe");
			if (!"".equals(returncoe) && returncoe != null) {
				double return_coe = Double.parseDouble(returncoe);

				// String prod_flag = req.getParameter("prod_flag");

				String orderid = req.getParameter("sale_id");
				long order_id = Long.parseLong(orderid);

				String prod_rsid = req.getParameter("prod_rs_id");
				long prod_rs_id = Long.parseLong(prod_rsid);

				od.update(return_coe, order_id, prod_rs_id);

				json.put("msg", "更新成功，我们已经将你的更改提交审批");
				json.put("status", "1");
			} else {
				json.put("msg", "系数不能为空");
				json.put("status", "0");
			}

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			// return "error";
		}
	}
	
	/**
	 *订单 通过/驳回审批
	 */
	public void approve() {
		OrderDao od = new OrderDao();
		try {
			String check = req.getParameter("check");
			long order_no = Long.parseLong(req.getParameter("order_no"));
			long order_version = Long.parseLong(req.getParameter("order_version"));
			
			if ("pass".equals(check) ) {
				od.pass_approve(order_no, order_version);
				json.put("msg", "更新成功，我们已经将你的更改提交审批");
				json.put("status", "1");
			} else {
				json.put("status", "0");
			}
			if("reject".equals(check)){
				od.regect_approve(order_no, order_version);
				json.put("msg", "更新成功，我们已经将你的更改提交审批");
				json.put("status", "1");
			}else {
				json.put("status", "0");
			}

			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			// return "error";
		}
	}
	
	/**
	 *缴款计划记录 通过/驳回审批
	 */
	public void call_approve() {
		OrderDao od = new OrderDao();
		try {
			String pay_state = req.getParameter("pay_state");
			long payment_id = Long.parseLong(req.getParameter("payment_id"));
			
			od.call_approve(payment_id, pay_state);
			json.put("msg", "更新成功，我们已经将你的更改提交审批");
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			// return "error";
		}
	}
	
	/**
	 *打退款 通过/驳回审批
	 */
	public void play_approve() {
		OrderDao od = new OrderDao();
		try {
			String pay_state = req.getParameter("pay_state");// 驳回或者通过
			long payment_id = Long.parseLong(req.getParameter("payment_id"));
			
			od.play_approve(payment_id ,pay_state);
			json.put("msg", "更新成功，我们已经将你的更改提交审批");
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			// return "error";
		}
	}
	/**
	 * 打款退款删除
	 */
	public void del_payment() {
		OrderDao od = new OrderDao();
		try {
			
			long payment_id = Long.parseLong(req.getParameter("payment_id"));
			od.del_payment(payment_id);
			json.put("msg", "删除成功");
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	/**
	 * 缴款删除
	 */
	public void del_call_payment() {
		OrderDao od = new OrderDao();
		try {
			
			long payment_id = Long.parseLong(req.getParameter("payment_id"));
			od.del_call_payment(payment_id);
			json.put("msg", "删除成功");
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	/**
	 * 查询变更详情
	 */
		public void change_detail() {
			OrderDao od = new OrderDao();
			try {
				
				long order_no = Long.parseLong(req.getParameter("order_no"));
				long order_version = Long.parseLong(req.getParameter("order_version"));
				long sales_id = Long.parseLong(req.getParameter("sales_id"));
				List<Map<String, Object>> list =od.changedetail(order_no, order_version, sales_id);
				//od.del_payment(payment_id);
				//json.put("msg", "删除成功");
				json.put("status", "1");
				json.put("change_list", list.get(0));
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();

			} catch (Exception e) {
				e.printStackTrace();
				
			}
	}
	/**
	 * 回款修改审批
	 */
	public void return_approve(){
		OrderDao od = new OrderDao();
		try {
			String prod_rsid = (String) req.getParameter("prod_rs_id");
			long prod_rs_id = 0;
			if(!"".equals(prod_rsid)&&prod_rsid!=null){
				prod_rs_id = Long.parseLong(prod_rsid);
						
			}
			String prod_flag = req.getParameter("prod_flag");
			od.return_approve(prod_rs_id,prod_flag);
			
			//od.del_payment(payment_id);
			//json.put("msg", "删除成功");
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();

		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
		/**
		 * 报表导出
		 */
		public void order_report() {
			try {
				User  user = (User) req.getSession().getAttribute(Constants.USER);
				long role_id = user.getRole_id();
				String remoteFilePath ="/var/www/oms/report/order_report.xls";
				// 输出Excel
				File fileWrite = new File(remoteFilePath);
				SqlRowSet rs =null;
				
				if(!fileWrite.exists())
				fileWrite.createNewFile();
				OutputStream os = new FileOutputStream(fileWrite);
				OrderDao od = new OrderDao();
				if (role_id == 1) {// 销售
					rs = od.r_sale_orderInfoSelect(10, 0, user.get_id());
				} else if (role_id == 2) {
					rs = od.r_team_sale_orderInfoSelect(10, 0, user.get_id());
				} else if (role_id == 3) {
					rs = od.r_org_team_sale_orderInfoSelect(10, 0, user.get_id());
				} else if (role_id == 4) {
					rs = od.r_orderInfoSelect(user.get_id(),10, 0);
					req.setAttribute("role", "1");
				} else if(role_id == 5){
					rs = od.r_orderInfoSelect(10, 0);
					req.setAttribute("role", "2");
				}else if(role_id == 6){
					rs = od.r_prod_orderInfoSelect(user.get_id(), 10, 0);
					req.setAttribute("role", "3");
				}else if(role_id == 7){
					rs = od.r_orderInfoSelect(10, 0);
					req.setAttribute("role", "4");
				}else if(role_id == 8){
					rs = od.r_orderInfoSelect(10, 0);
				}else if(role_id == 9){
					rs = od.r_orderInfoSelect(10, 0);
				}
				// rep.export_report();
				String[] o ={"序号","订单号","日期","地区","RM","产品名称","合伙企业","产品渠道","客户","认购金额","币种","产品系数","标费","状态"};
				Download.writeExcel(os, rs,o);
				Download.downloadFile(remoteFilePath, resp);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 添加到task表中记录
		 * @param req
		 */
		private void add(HttpServletRequest req,long id,long user_id) {
			String cust_type = req.getParameter("old_cust_type");
			String cust_no = req.getParameter("old_cust_na");
			String prod_id = req.getParameter("old_prod_no");
			String part_comp = req.getParameter("old_part_comp");
			String order_type = req.getParameter("old_order_type");
			String order_amount = req.getParameter("old_order_amount");
			String contract_type = req.getParameter("old_contract_type");
			String contract_no = req.getParameter("old_contract_no");
			String is_id = req.getParameter("old_is_id");
			String is_no = req.getParameter("old_is_no");
			String prod_diffcoe = req.getParameter("old_prod_diffcoe1");
			String pri_fee = req.getParameter("old_pri_fee");
			String acount_fee = req.getParameter("old_acount_fee");
			String buy_fee = req.getParameter("old_buy_fee");
			String start_fee = req.getParameter("old_start_fee");
			String bank_no = req.getParameter("old_bank_no");
			String bank_card = req.getParameter("old_bank_card");
			String remark = req.getParameter("old_remark");
			json.put("cust_type", cust_type);
			json.put("cust_no", cust_no);
			json.put("prod_id", prod_id);
			json.put("part_comp", part_comp);
			json.put("order_type", order_type);
			json.put("order_amount", order_amount);
			json.put("contract_type", contract_type);
			json.put("contract_no", contract_no);
			json.put("is_id", is_id);
			json.put("is_no", is_no);
			json.put("prod_diffcoe", prod_diffcoe);
			json.put("pri_fee", pri_fee);
			json.put("acount_fee", acount_fee);
			json.put("buy_fee", buy_fee);
			json.put("start_fee", start_fee);
			json.put("bank_no", bank_no);
			json.put("bank_card", bank_card);
			json.put("remark", remark);
			userDao us = new userDao();
			us.addtask(json, id, "订单更改审批", "order", user_id);
			
		}
		/**
		 * 重置
		 */
		public void order_reset(){
			try {
				JSONObject json = new JSONObject();
				User user = (User) req.getSession().getAttribute(Constants.USER);
				if(!"".equals(req.getParameter("email_id"))&&req.getParameter("email_id")!=null){
					long email_id = Long.parseLong(req.getParameter("email_id"));
					GpDao gp = new GpDao();
					List<Map<String, Object>> list = gp.gp_reset(email_id,user.get_id());
					if(list.size()>0&&list!=null){
						Object content = (Object) list.get(0).get("content");
						JSONObject jsonob = JSONObject.parseObject(content.toString());
						OrderDao ord = new OrderDao();
						ord.order_reset(jsonob, email_id);
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
		//查询客户
		public void find(){
			try {
			String cust_type = req.getParameter("cust_type");
			String cust_name = req.getParameter("cust_name");
			List<Map<String, Object>> custOrgList =ord.custOrgSelect(cust_type,cust_name);
			if(custOrgList!=null&&custOrgList.size()>0){
				json.put("custOrgList", custOrgList);
				json.put("status", "1");
			} else {
				json.put("status", "2");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void giveup_call(){
			try {
			    String orderno = req.getParameter("order_no");
			    long order_no = Long.parseLong(orderno);
			    String orderversion = req.getParameter("order_version");
			    long order_version = Long.parseLong(orderversion);
			    ord.giveup_call(order_no,order_version);
			    json.put("status", "1");
			    resp.setContentType("text;charset=utf-8");
			    resp.setCharacterEncoding("UTF-8");
			    resp.getWriter().print(json);
			    resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
