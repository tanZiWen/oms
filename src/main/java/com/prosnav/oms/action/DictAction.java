package com.prosnav.oms.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.oms.dao.DictDao;

public class DictAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();

	public void selectDict() {
		JSONObject json = new JSONObject();
		try {
			String level = req.getParameter("level");
			String sex = req.getParameter("sex");
			String idtype = req.getParameter("idtype");	
			Object[] o = {level,sex,idtype};
			DictDao dict = new DictDao();
			for(int i=0;i<o.length;i++){
				List<Map<String, Object>> list = dict.queryDict(level);
				if(list.size()>0){
					json.put("list"+i, list);
				}else{
					json.put("list"+i, "未找到");
				}
			}
			
			
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
