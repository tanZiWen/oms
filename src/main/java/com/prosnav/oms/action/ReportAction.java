package com.prosnav.oms.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.ReportDao;
import com.prosnav.oms.util.Download;
import com.prosnav.oms.util.jdbcUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ReportAction {
	protected HttpServletResponse resp = ServletActionContext.getResponse();
	protected HttpServletRequest req = ServletActionContext.getRequest();
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	public String run(){
		User user = (User) req.getSession().getAttribute(Constants.USER);
		
		user.setRole_id(4);
		
		return "sucess";
	}
	
	
	public String report_index(){
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			ReportDao rep = new ReportDao();
			long role_id = user.getRole_id();
			List<Map<String, Object>> report_list = rep.report_list(role_id);
			req.setAttribute("report_list", report_list);
			List<Map<String, Object> > list = rep.report_index();
			req.setAttribute("list", list);
			
			
		} catch (Exception e) {
			log.debug(e.toString());
		}
		return "report_index";
	}
	/**
	 * 异步刷新财务报表
	 */
	public void fin_exceport_report(){
		try{
			JSONObject json = new JSONObject();
			ReportDao rep = new ReportDao();
			List<Map<String, Object>> list = rep.report_index();
			if(list.size()>0&&list!=null){
				json.put("list", list);
				json.put("desc", "1");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 财务报表导出
	 */
	public void export_report() {
		try {
			
			String remoteFilePath ="/var/www/oms/report/report.xls";
			// 输出Excel
			File fileWrite = new File(remoteFilePath);
			ReportDao rep = new ReportDao();
			log.info("创建excel");
			if(!fileWrite.exists())
			fileWrite.createNewFile();
			OutputStream os = new FileOutputStream(fileWrite);
			
			SqlRowSet rs = rep.export_report();
			String[] o ={"序号",	"产品编号","产品名",	"合伙企业",	"管理人"	,"产品经理","	募集状态","	投资状态",	"币种",	"认缴额(万元)","投资期","回收期","费用类型","费用金额",	"费用比例","开始时间",	"结束时间",	"募集费用"};
			Download.writeExcel(os, rs,o);
			Download.downloadFile(remoteFilePath, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 异步刷新家族报表查询
	 */
	public void family_report(){
		try{
			JSONObject json = new JSONObject();
			ReportDao rep = new ReportDao();
			List<Map<String, Object>> list = rep.family_report();
			if(list.size()>0&&list!=null){
				json.put("list", list);
				json.put("desc", "1");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 家族报表导出
	 */
	public void f_exceport_report() {
		try {
			
			String remoteFilePath ="/var/www/oms/report/report.xls";
			// 输出Excel
			File fileWrite = new File(remoteFilePath);
			ReportDao rep = new ReportDao();
			if(!fileWrite.exists())
			fileWrite.createNewFile();
			OutputStream os = new FileOutputStream(fileWrite);
			
			SqlRowSet rs = rep.f_exceport_report();
			String[] o ={"家族名称","客户名称",	"产品名称",	"合伙企业"	,"订单号","	下单金额","	销售名",	"产品类型",	"币种","订单状态"};
			Download.writeExcel(os, rs,o);
			Download.downloadFile(remoteFilePath, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 写入excel
	 * 
	 * @param os
	 * @param rs
	 */
	public void writeExcel(OutputStream os, SqlRowSet rs) {
		try {
			/**
			 * 只能通过API提供的工厂方法来创建Workbook，而不能使用WritableWorkbook的构造函数，
			 * 因为类WritableWorkbook的构造函数为protected类型
			 * method(1)直接从目标文件中读取WritableWorkbook wwb =
			 * Workbook.createWorkbook(new File(targetfile)); method(2)如下实例所示
			 * 将WritableWorkbook直接写入到输出流
			 * 
			 */
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			// 创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet("报表", 0);

			// **************往工作表中添加数据*****************

			int i = 0;
			int j = 0;

			// 下面for循环里面的rs.getMetaData().GetColumnCount() 获取数据库中某个表的列总数
			/*
			 * for (int k = 0; k < rs.getMetaData().getColumnCount(); k++) {
			 * //rs.getMetaData().getColumnName()获取表的列名。并添加到
			 * excel表Label里,Label(i,j,s)表示i列j行添加s,s必须是String ws.addCell(new
			 * Label(k, 0, rs.getMetaData().getColumnName(k + 1))); }
			 */
			while (rs.next()) {
				// 算法，依次添加数据库中所有符合的数据到excel中
				for (int k = 0; k < rs.getMetaData().getColumnCount(); k++) {
					ws.addCell(new Label(k, j + i + 1, rs.getString(k + 1)));
				}
				i++;
			}

			// 1.添加Label对象

			// 写入工作表
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**  
     * 下载远程文件并保存到本地  
     * @param remoteFilePath 远程文件路径   
     * @param localFilePath 本地文件路径  
     */
	public void downloadFile(String remoteFilePath, HttpServletResponse response) {
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(remoteFilePath);
		try {
			bis = new BufferedInputStream(new FileInputStream(f));
			 byte[] buffer = new byte[bis.available()];
			 bis.read(buffer);
			 bis.close();
			 // 清空response
	            response.reset();
	            response.setContentType("application/octet-stream");
	            // 设置response的Header
	            response.addHeader("Content-Disposition", "attachment;filename=" +
	            URLEncoder.encode(f.getName(), "utf-8"));
	            //response.
	            response.addHeader("Content-Length", "" + f.length());
	            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	            
	            toClient.write(buffer);
	            toClient.flush();
	            toClient.close();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				//bos.close();
			} catch (IOException e) {
				log.debug(e.toString());
			}
		}
	}
}
