package com.prosnav.oms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.oms.dao.GPInfoDao;

public class GPInfoAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	HttpSession session = req.getSession(); 
	//JSONObject json = new JSONObject();
	GPInfoDao gia = new GPInfoDao();
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	JSONObject json = new JSONObject();
	 Map<String, Object> map = new HashMap<String, Object>();

	public String queryGPInfo(){
		String result="";
		try {
			list = gia.GPInfo();
			resp.setContentType("text;charset=utf-8"); 
			resp.setCharacterEncoding("UTF-8");
			req.setAttribute("GPlist", list);
			result="queryGPInfo";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String add_GP() {
		String result="";
		try {
			/*String pro_id = req.getParameter("prod_id");
			int prod_id = Integer.parseInt(pro_id);
			*/
			list = gia.GPInfo();
			req.setAttribute("GPlist", list);
			result="add_GP";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void add_prod_gp(){
		try{
			resp.setContentType("text;charset=utf-8"); 
			resp.setCharacterEncoding("UTF-8");
		String g_id =  req.getParameter("gp_id");
		String pro_id = req.getParameter("prod_id");
		String g_id_orgin = req.getParameter("gp_id_orgin");
		/*int gp_id_orgin=Integer.parseInt(g_id_orgin);
		int gp_id = Integer.parseInt(g_id);
		int prod_id = Integer.parseInt(pro_id);*/
		long gp_id_orgin=Long.parseLong(g_id_orgin);
		long gp_id = Long.parseLong(g_id);
		long prod_id = Long.parseLong(pro_id);
		int result = gia.update_prod_gp(gp_id, prod_id,gp_id_orgin);
	
		if(result>0){
				json.put("status", "1");
				json.put("msg", "添加成功");
			}else{
				json.put("status", "2");
				json.put("msg", "添加失败");
			}
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
