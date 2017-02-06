package com.prosnav.oms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.userDao;

public class UserAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	JSONObject json = new JSONObject();
	Map<String, Object> map = new HashMap<String, Object>();
	HttpSession session = req.getSession();
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public void user() {
		User user = (User) session.getAttribute(Constants.USER);
		if (user == null) {
		/*	return "index";*/
		}
		long role_id = user.getRole_id();
		long ueser_id = user.get_id();
		/* System.out.println("销售登录ID"+ueser_id); */
		req.setAttribute("role_id", role_id);
		String result = "";
		List<Map<String, Object>> custList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reportList = new ArrayList<Map<String, Object>>();

		try {
			userDao us = new userDao();
			// 客户资源管理
			custList = us.userCust(role_id);
			if (custList != null && custList.size() > 0) {
				json.put("cust", "1");
				json.put("custList", custList);
			} else {
				json.put("cust", "2");

			}
			// 产品
			productList = us.userProduct(role_id);
			if (productList != null && productList.size() > 0) {
				json.put("product", "1");
				json.put("productList", productList);
			} else {
				json.put("product", "2");
			}
			// 订单
			orderList = us.userOrder(role_id);
			if (orderList != null && orderList.size() > 0) {
				json.put("order", "1");
				json.put("orderList", orderList);
			} else {
				json.put("order", "2");
			}
			// 奖金
			rewardList = us.userReward(role_id);
			if (rewardList != null && rewardList.size() > 0) {
				json.put("reward", "1");
				json.put("rewardList", rewardList);
			} else {
				json.put("reward", "2");
			}
			// 报表
			reportList = us.userReport(role_id);
			if (reportList != null && reportList.size() > 0) {
				json.put("report", "1");
				json.put("reportList", reportList);
			} else {
				json.put("report", "2");
			}
			//产品系数
			List<Map<String, Object>> diffcoeList = us.userdiffcoe(role_id);
			if(diffcoeList!=null&&diffcoeList.size()>0){
				json.put("diffcoe", "1");
				json.put("diffcoeList", diffcoeList);
			}else {
				json.put("diffcoe", "2");
			}
			//客户分配
			List<Map<String, Object>> custDistributionList = us.userCustDistribution(role_id);
			if(custDistributionList!=null&&custDistributionList.size()>0){
				json.put("custDistribution", "1");
				json.put("custDistributionList", custDistributionList);
			}else {
				json.put("custDistribution", "2");
			}
			//客户邮件
			List<Map<String, Object>> custEmailList = us.userCustEmail(role_id);
			if(custEmailList!=null&&custEmailList.size()>0){
				json.put("custEmail", "1");
				json.put("custEmailList", custEmailList);
			}else {
				json.put("custEmail", "2");
			}
			//客户管理
			List<Map<String, Object>> emailManageList = us.userEmailManage(role_id);
			if(emailManageList!=null&&emailManageList.size()>0){
				json.put("emailManage", "1");
				json.put("emailManageList", emailManageList);
			}else {
				json.put("emailManage", "2");
			}
			//客户外拨
			List<Map<String, Object>> custCallList = us.userCustCall(role_id);
			if(custCallList!=null&&custCallList.size()>0){
				json.put("custCall", "1");
				json.put("custCallList", custCallList);
			}else {
				json.put("custCallList", "2");
			}
			//外拨管理
			List<Map<String, Object>> callManageList = us.userCallManage(role_id);
			if(callManageList!=null&&callManageList.size()>0){
				json.put("callManage", "1");
				json.put("callManageList", callManageList);
			}else {
				json.put("callManage", "2");
			}
			//录音管理
			List<Map<String, Object>> recordManageList = us.userRecordManage(role_id);
			if(recordManageList!=null&&recordManageList.size()>0){
				json.put("recordManage", "1");
				json.put("recordManageList", recordManageList);
			}else {
				json.put("recordManage", "2");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		
         
		/*return result;*/
	}
}
