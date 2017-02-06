package com.prosnav.oms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prosnav.oms.dao.RegJdDao;
import com.prosnav.oms.util.jdbcUtil;

public class RegJDAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	Map<String,Object> params=new HashMap<String,Object>();
	ArrayList<Object[]> li=new ArrayList<Object[]>();

	RegJdDao rjd = new RegJdDao();
	
	
	public void reg_JDAdd(){
		resp.setContentType("text;charset=utf-8"); 
		resp.setCharacterEncoding("UTF-8");
		try {
			String json = req.getParameter("json");
			JSONArray jsonArr = JSON.parseArray(json);  
			//System.out.println("json2Array()方法：jsonArr=="+jsonArr); 
			//json2Array()方法：jsonArr==[{"cust_name":"gf","sale_name":"xdd","except_money":"vdfv","remarks":"vfvbg","deal_success":"fvdf"},{"cust_name":"vfv","sale_name":"dfve","except_money":"dfsdf","remarks":"fsf","deal_success":"sf"}]
		    for(int i=0;i<jsonArr.size();i++){
			     JSONObject jobj =  (JSONObject) jsonArr.get(i);
			     Object cust_name = jobj.get("cust_name");
			     if(cust_name==""||cust_name==null){
			    	 cust_name="";
			     }
			     Object sale_name = jobj.get("sale_name");
			     if(sale_name==""||sale_name==null){
			    	 sale_name="";
			     }
			         
			     Object remarks = jobj.get("remarks");
			     if(remarks==""||remarks==null){
			    	 remarks="";
			     }
			     
			     Object exp_money = jobj.get("except_money");
			     Double except_money = Double.parseDouble((String) exp_money);
			     if(except_money==0||except_money==null){
			    	 except_money=0.0;
			     }
			     
			     Object deal_succ = jobj.get("deal_success");
			     Double deal_success = Double.parseDouble((String) deal_succ);
			     if(deal_success==0||deal_success==null){
			    	 deal_success=0.0;
			     }
			     String pro_id = req.getParameter("prod_id");
			     long prod_id = Long.parseLong(pro_id);
			     long product_p_id = jdbcUtil.seq();
			     long prod_allot_id = jdbcUtil.seq();
			     String dept_dist = req.getParameter("dept_no");
			     double sum_excp = 1234;
			     Object[] temp={product_p_id,prod_id,prod_allot_id,cust_name,sale_name,dept_dist,except_money,deal_success,remarks,sum_excp};
			     li.add(temp);
			}
			int []result=rjd.prod_progress(li);		
			
			JSONObject jsonList = new JSONObject();
			
			if (result.length==0||result==null) {
				jsonList.put("status", "2");
				jsonList.put("msg", "新增失败");
			}else{
				jsonList.put("status", "1");
				jsonList.put("msg", "新增成功");
			}
			
			resp.getWriter().print(jsonList);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
