package com.prosnav.oms.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.oms.dao.LPAddDao;
import com.prosnav.oms.util.jdbcUtil;

public class LPAddAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	JSONObject json = new JSONObject();
	LPAddDao LPAddDao = new LPAddDao(); 
	ArrayList<Object[]> li=new ArrayList<Object[]>();
	
	
	public void LPAdd(){

		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		
		Map<String,Object> params=new HashMap<String,Object>();
		
		//String GP_name =req.getParameter("gp_name");
		String partner_com_name =req.getParameter("partner_com_name");
		String fund_type =req.getParameter("fund_type");
		String prod_diffcoe =req.getParameter("prod_diffcoe");
		String bus_license =req.getParameter("bus_license");
		String legal_resp =req.getParameter("legal_resp");
		String fund_no =req.getParameter("fund_no");
//		String pay_sum =req.getParameter("pay_sum");
//		String pay_true =req.getParameter("pay_true");
		String open_bank =req.getParameter("open_bank");
		String bank_account =req.getParameter("bank_account");
		String open_bank1 =req.getParameter("open_bank1");
		String bank_account1 =req.getParameter("bank_account1");
		String regit_addr =req.getParameter("regit_addr");
		String prod_remark =req.getParameter("prod_remark");
		String fund_start_fee =req.getParameter("fund_start_fee");
		String fund_bnf_allot =req.getParameter("fund_bnf_allot");
		String cor_com_fee =req.getParameter("cor_com_fee");
		String fund_type_fee =req.getParameter("fund_type_fee");
		String true_pay =req.getParameter("true_pay");
		String gplp_pay =req.getParameter("gplp_pay");
		String renew_period =req.getParameter("renew_period");
		String renew_year =req.getParameter("renew_year");
		String invest_year =req.getParameter("invest_year");
		String first_delivery =req.getParameter("first_delivery");
		String invest_goal =req.getParameter("invest_goal");
		String invest_period =req.getParameter("invest_period");
		String mana_decr =req.getParameter("mana_decr");
		String buy_decr =req.getParameter("buy_decr");
		String delay_decr =req.getParameter("delay_decr");
		String late_join_decr =req.getParameter("late_join_decr");
		String invest_goal_decr =req.getParameter("invest_goal_decr");
		String see_sale =req.getParameter("see_sale");
		String pro_id =req.getParameter("prod_id");
		String area =req.getParameter("area");
		//int prod_id=Integer.parseInt(pro_id);
		long prod_id=Long.parseLong(pro_id);
		long lp_id = jdbcUtil.seq();
		params.put("lp_id",lp_id);
		//params.put("GP_name",GP_name);
		params.put("renew_year",renew_year);
		params.put("invest_year",invest_year);
		params.put("partner_com_name",partner_com_name);
		params.put("fund_type" ,fund_type );
		params.put("prod_diffcoe",prod_diffcoe);
		params.put("bus_license" ,bus_license);
		params.put("legal_resp" ,legal_resp);
		params.put("fund_no",fund_no);
		params.put("open_bank" ,open_bank );
		params.put("bank_account" ,bank_account);
		params.put("open_bank1" ,open_bank1);
		params.put("bank_account1",bank_account1);
		params.put("regit_addr",regit_addr);
		params.put("prod_remark",prod_remark);
		params.put("fund_start_fee",fund_start_fee);
		params.put("fund_bnf_allot"  ,fund_bnf_allot);
		params.put("cor_com_fee",cor_com_fee);
		params.put("fund_type_fee",fund_type_fee);
		params.put("true_pay",true_pay);
		params.put("gplp_pay",gplp_pay);
		params.put("renew_period" ,renew_period );
		params.put("first_delivery",first_delivery);
		params.put("invest_goal",invest_goal);
		params.put("invest_period",invest_period);
		params.put("prod_id",prod_id);
		params.put("mana_decr",mana_decr);
		params.put("buy_decr",buy_decr);
		params.put("delay_decr",delay_decr);
		params.put("late_join_decr",late_join_decr);
		params.put("invest_goal_decr",invest_goal_decr);
		params.put("see_sale",see_sale);
		params.put("area",area);
		//System.out.println(params);
		try {
		int result = LPAddDao.add_LP(params);
		//Map<String, Object> map = new HashMap<String, Object>();
		json.put("list", result);
		//PrintWriter out;
			if (result == 1) {// 1 代表成功 0代表失败
				//long max_id = LPAddDao.max_LPID();
				long email_id = jdbcUtil.seq();
				int result2 = LPAddDao.lp_Prod_Add(lp_id,prod_id,email_id);
				if (result2 == 1){
					json.put("status", "1");
					json.put("msg", "新增成功");
				
				}
				else {
					json.put("status", "2");
					json.put("msg", "新增失败");
				
				}
			} else {
				json.put("status", "2");
				json.put("msg", "新增失败");
				
			}
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
