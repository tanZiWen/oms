package com.prosnav.oms.action;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.GpDao;
import com.prosnav.oms.dao.OrgDao;
import com.prosnav.oms.dao.userDao;
import com.prosnav.oms.util.Download;
import com.prosnav.oms.util.Escape;
import com.prosnav.oms.util.jdbcUtil;

public class GpAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	private String gp_name ;
	public void setGp_name(String gp_name) {
		this.gp_name = gp_name;
	}
	/**
	 * 添加jp
	 */
	public void gp_addGp() {
		JSONObject json = new JSONObject();
		
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			String gp_name = req.getParameter("gp_name");
			String gp_dept = req.getParameter("gp_dept");
			String bus_license = req.getParameter("bus_license");
			String legal_resp = req.getParameter("legal_resp");
			
			String fund_no = req.getParameter("fund_no");
			String open_bank = req.getParameter("open_bank");
			String bank_account = req.getParameter("bank_account");
			String regit_addr = req.getParameter("regit_addr");
			String status = req.getParameter("status");
			GpDao gp = new GpDao();
			gp.addGp( gp_name, gp_dept, bus_license, legal_resp, fund_no, 
					open_bank, bank_account, regit_addr,status,user);
			json.put("msg", "添加成功");
			json.put("desc", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("msg", "添加失败");
			json.put("desc", "0");
		}

	}
	/**
	 * 保存gp
	 * 
	 */
	public void edit() {
		JSONObject json = new JSONObject();
		
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long gp_id = Long.parseLong(req.getParameter("gp_id"));
			String gp_name = req.getParameter("gp_name");
			String gp_dept = req.getParameter("gp_dept");
			String bus_license = req.getParameter("bus_license");
			String legal_resp = req.getParameter("legal_resp");
			
			String fund_no = req.getParameter("fund_no");
			String open_bank = req.getParameter("open_bank");
			String bank_account = req.getParameter("bank_account");
			String regit_addr = req.getParameter("regit_addr");
			String gp_remark = req.getParameter("gp_remark");
			String status = req.getParameter("status");
			String message = req.getParameter("message");
			GpDao gp = new GpDao();
			gp.edit(gp_id,  open_bank, bank_account, regit_addr, gp_remark,status,message,user);
			//gp.addGp( gp_name, gp_dept, bus_license, legal_resp, fund_no, open_bank, bank_account, regit_addr);
			json.put("msg", "更新成功");
			json.put("desc", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().println(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("msg", "添加失败");
			json.put("desc", "0");
		}

	}
	/**
	 * 进入gp页面
	 * @return
	 */
	public String gp_select() {
		String result="";
        User user = (User) req.getSession().getAttribute(Constants.USER);
		
		long role_id = user.getRole_id();
		req.setAttribute("role_id", role_id);
		try {
			GpDao gp = new GpDao();
			
			List<Map<String, Object>> list = gp.gp_select(0,10);
			int count = gp.gp_selectCount();
			req.setAttribute("totalNum", count);
			req.setAttribute("PageNum", "1");
			req.setAttribute("PageSize","10" );
			if (list.size() > 0 && list != null) {
				req.setAttribute("list", list);
				req.setAttribute("status", "1");

			} else {
				req.setAttribute("status", "2");
				req.setAttribute("gp_detail", "未找到相关数据");
			}
			
			result="success";
			
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常，请稍后再试");
			result="error";
			
		}
		return result;
	}
	/**
	  * 分页查询
	  */
	public void pagequery(){
		JSONObject json = new JSONObject();
		try{
		int PageNum = Integer.parseInt(req.getParameter("PageNum"));
		int n = Integer.parseInt(req.getParameter("PageSize"));
		int m = (PageNum-1)*n;
		GpDao gp = new GpDao();
		List<Map<String, Object>> list = gp.gp_select(m, n);
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
	 * 详细信息查询
	 * 
	 * @return
	 */
	public String detail() {
		String result = "";
		HttpServletResponse resp = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String gp_id = req.getParameter("id");
        User user = (User) req.getSession().getAttribute(Constants.USER);
		
		long role_id = user.getRole_id();
		req.setAttribute("role_id", role_id);
		long id = Long.parseLong(gp_id);
				
		try {
			GpDao gp = new GpDao();
			List<Map<String, Object>> list = gp.detail(id);
			if (list != null && list.size() > 0) {
				req.setAttribute("gp_detail", list.get(0));
				req.setAttribute("status", "1");
				result="success";
			} else {
				req.setAttribute("msg", "系统异常，请稍后再试");
				result="error";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常，请稍后再试");
			result="error";
		}
		return result;

	}
	
	/**
	 * 更新gp基本信息 后提交审批
	 * 
	 * @throws ParseException
	 * 
	 */
	public void gp_submit() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long gp_id = Long.parseLong(req.getParameter("gp_id"));
			String gp_name = req.getParameter("gp_name");
			String gp_dept = req.getParameter("gp_dept");
			String bus_license = req.getParameter("bus_license");
			String legal_resp = req.getParameter("legal_resp");
			String fund_no = req.getParameter("fund_no");
			String open_bank = req.getParameter("open_bank");
			String bank_account = req.getParameter("bank_account");
			String regit_addr = req.getParameter("regit_addr");
			String gp_remark = req.getParameter("gp_remark");
			String message = req.getParameter("message");
			String status = req.getParameter("status");
			boolean state ;
			if("4".equals(status)){
				state=true;
			}else{
				state=false;
			}
			GpDao gp = new GpDao();
			gp.gp_submit(gp_id, gp_name, gp_dept, bus_license, legal_resp,
					fund_no, open_bank, bank_account, regit_addr, gp_remark,"3",message,user,state);
			js.put("msg", "信息已提交，待审批");
			String old_gp_name = req.getParameter("old_gp_name");
			String old_gp_dept = req.getParameter("old_gp_dept");
			String old_bus_license = req.getParameter("old_bus_license");
			String old_legal_resp = req.getParameter("old_legal_resp");
			String old_fund_no = req.getParameter("old_fund_no");
			String old_open_bank = req.getParameter("old_open_bank");
			String old_bank_account = req.getParameter("old_bank_account");
			String old_regit_addr = req.getParameter("old_regit_addr");
			JSONObject json = new JSONObject();
			json.put("gp_name", old_gp_name);
			json.put("gp_dept", old_gp_dept);
			json.put("bus_license", old_bus_license);
			json.put("legal_resp", old_legal_resp);
			json.put("fund_no", old_fund_no);
			json.put("open_bank", old_open_bank);
			json.put("bank_account", old_bank_account);
			json.put("regit_addr", old_regit_addr);
			userDao us = new userDao();
			us.addtask(json, gp_id, "gp更改提交审批", "gp",user.get_id());
			js.put("desc", "1");			
			resp.getWriter().println(js);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 更新gp基本信息 后提交审批通过
	 * 
	 * @throws ParseException
	 * 
	 */
	public void gp_pass() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String gpid = req.getParameter("gp_id");
		long gp_id = Long.parseLong(gpid);
		try {
			GpDao gp = new GpDao();
			gp.gp_pass(gp_id);
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
	 * 更新gp基本信息 后提交审批不通过
	 * 
	 * @throws ParseException
	 * 
	 */
	public void gp_nopass() {
		JSONObject js = new JSONObject();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		String gpid = req.getParameter("gp_id");
		long gp_id = Long.parseLong(gpid);
		try {
			GpDao gp = new GpDao();
			gp.gp_nopass(gp_id);
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
	 * 跳转至新建页面
	 * 
	 * @return
	 */
	public String skip() {

		return "success";
	}
	/**
	 * 查询gp名称
	 */
	public void GP_select() {
		JSONObject json = new JSONObject();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("utf-8");
		HttpServletRequest req = ServletActionContext.getRequest();
		try {
			String gp_name = req.getParameter("gp_name");
			String gp_dept = req.getParameter("gp_dept");
			GpDao gp = new GpDao();
			int PageNum = 1;
			if(!"".equals(req.getParameter("PageNum"))&&req.getParameter("PageNum")!=null){
				PageNum = Integer.parseInt(req.getParameter("PageNum"));
			}
			
			int n = 10;
			int m = (PageNum-1)*n;
			List<Map<String, Object>> list = gp.GP_select(gp_name,gp_dept,m,n);
			int count = gp.gp_select_fenye_Count(gp_name,gp_dept);
			if (list != null && list.size() > 0) {
				json.put("list", list);
				json.put("totalNum", count);
				json.put("PageNum", PageNum);
				json.put("PageSize",n );
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
	 * 报表导出
	 */
	public void gp_report() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		
		if(user == null){
			
		}
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		try {
			JSONObject json = new JSONObject();
			HttpServletResponse response = ServletActionContext.getResponse();
			String remoteFilePath ="/var/www/oms/report/gp_report.xls";
			// 输出Excel
			File fileWrite = new File(remoteFilePath);
			GpDao rep = new GpDao();
			if(!fileWrite.exists())
			fileWrite.createNewFile();
			String[] o = {"序号","GP名称","管理方","营业执照号","法定代表人","基金业协会备案号","开户行","账号","注册地址","备注","审批状态"};
			OutputStream os = new FileOutputStream(fileWrite);
			if(role_id==6){//产品操作
				SqlRowSet rs = rep.gp_report_leader();
				System.out.println(""+rs.toString());
				Download.writeExcel(os, rs, o );
				Download.downloadFile(remoteFilePath, response);
			}else if(role_id==7){//产品管理
				SqlRowSet rs = rep.gp_report_leader();
				Download.writeExcel(os, rs, o);
				Download.downloadFile(remoteFilePath, response);			
			}
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 重置
	 */
	public void gp_reset(){
		try {
			JSONObject json = new JSONObject();
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long gp_id = Long.parseLong(req.getParameter("gp_id"));
			GpDao gp = new GpDao();
			List<Map<String, Object>> list = gp.gp_reset(gp_id,user.get_id());
			if(list.size()>0&&list!=null){
				Object content = (Object) list.get(0).get("content");
				JSONObject jsonob = JSONObject.parseObject(content.toString());
				gp.gpreset(jsonob, gp_id);
				json.put("content", jsonob);
				json.put("desc", "1");
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
