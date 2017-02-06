package com.prosnav.oms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.oms.dao.ProdAllotDao;
import com.prosnav.oms.util.jdbcUtil;

public class ProdAllotAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	JSONObject json = new JSONObject();
	ProdAllotDao pad = new ProdAllotDao();
	public void prodAllot() {
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		try {
		String pro_id = req.getParameter("prod_id");
		//int prod_id = Integer.parseInt(pro_id);
		long prod_id = Long.parseLong(pro_id); 
		String dept_name = req.getParameter("dept_name");
		String allot = req.getParameter("dept_allot");
		double dept_allot =  Double.parseDouble(allot);
		long prod_allot_id = jdbcUtil.seq();
		int prod_count_allot = pad.prod_count_allot(prod_id, dept_name);
	//	System.out.println(prod_count_allot);
		if(prod_count_allot>0){
			int result1 = pad.prod_update_allot(dept_allot, dept_name, prod_id);
			if (result1 == 1) {// 1 代表成功 0代表失败
				json.put("status", "1");
				json.put("msg", "新增成功");
			} else {
				json.put("status", "2");
				json.put("msg", "新增失败");
			}
		}else if(prod_count_allot==0){
			int result2 = pad.prod_allot( prod_allot_id,dept_name, dept_allot,prod_id);
			if (result2 == 1) {// 1 代表成功 0代表失败
				json.put("status", "1");
				json.put("msg", "新增成功");
			} else {
				json.put("status", "2");
				json.put("msg", "新增失败");
			}
		}
	
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
