package com.prosnav.oms.action;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
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
import com.prosnav.oms.dao.userDao;
import com.prosnav.oms.util.jdbcUtil;

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class BasicAction {
	protected HttpServletResponse resp = ServletActionContext.getResponse();
	protected HttpServletRequest req = ServletActionContext.getRequest();
	private  JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	public String run(){
		User user = (User) req.getSession().getAttribute(Constants.USER);
		
		user.setRole_id(4);
		req.getSession().setAttribute(Constants.USER, user);
		
		
		return "sucess";
	}
	/**
	 * 报表导出
	 */
	public void export_report() {
		try {
			String remoteFilePath ="/home/report.xls";
			// 输出Excel
			File fileWrite = new File(remoteFilePath);
			fileWrite.createNewFile();
			OutputStream os = new FileOutputStream(fileWrite);
			String sql = "SELECT t4.prod_id, t4.prod_name,t2.partner_com_name,t7.dict_name mana_name, "
					+ " t5.dict_name status_name,t3.dict_name invest_name,t6.dict_name currency_name, to_char(coalesce(sum(t1.order_amount),0), 'fm99,999,999,999,999,999,990.00') pay_sum, t2.invest_year,"
					+ " (cast(coalesce(t2.renew_year,'0') as NUMERIC)-cast(coalesce(t2.invest_year,'0') as NUMERIC)) reback,"
					+ " t10.dict_name module_name,t8.mng_fee,t8.mng_fee cost_scale,t1.buy_fee,t8.start_date,t8.end_date, t1.entry_time, t4.raise_fee "
					+ " FROM public.order_info t1 " + " LEFT JOIN lp_info t2 on t1.part_comp = t2.lp_id "
					+ " LEFT JOIN product_info t4 on t1.prod_no = t4.prod_id and t4.check_status = '2' "
					+ " left join data_dict t3 on t3.dict_value = t2.invest_goal and t3.dict_type = 'invest' "
					+ " left join data_dict t5 on t5.dict_value = t4.prod_status and t5.dict_type = 'prodStus' "
					+ " left join data_dict t6 on t6.dict_value = t4.prod_currency and t6.dict_type = 'prodCur' "
					+ " left join data_dict t7 on t7.dict_value = t4.prod_mana and t7.dict_type = 'prodmana' "
					+ " LEFT JOIN product_fee t8 on t1.prod_no = t8.prod_id and t1.part_comp = t8.lp_id "
					+ " left join data_dict t10 on t10.dict_value = t8.mng_module and t10.dict_type = 'module' "
					+ " WHERE t1.is_checked = '2' and t1.stateflag = '0'  "
					+ " GROUP BY t1.is_checked, t1.stateflag, t1.create_time, t1.order_version, t1.buy_fee, t1.order_no, t1.entry_time,t2.partner_com_name,"
					+ " t2.renew_period,t2.invest_period,t2.invest_goal,t2.renew_year,t2.invest_year,t3.dict_name,t4.prod_id, t4.prod_name, t4.prod_currency, t4.prod_status, t4.raise_fee, t4.prod_mana,"
					+ " t5.dict_name,t6.dict_name,t7.dict_name,t8.mng_module, t8.mng_type, t8.start_date, "
					+ " t8.end_date, t8.mng_fee,t10.dict_name";
			SqlRowSet rs = jt.queryForRowSet(sql);
			writeExcel(os, rs);
			String localFilePath = req.getParameter("localFilePath");
			downloadFile(remoteFilePath, localFilePath);
			
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
	public void downloadFile(String remoteFilePath, String localFilePath) {
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				log.debug(e.toString());
			}
		}
	}
	/*public static void main(String[] args)   
	{   
	try   
	{   
	//读Excel   
	//ExcelHandle.readExcel("f:/testRead.xls");   
	//输出Excel   
	File fileWrite = new File("C:/Users/Fu/Desktop/新建 Microsoft Excel 97-2003 工作表.xls");   
	fileWrite.createNewFile();   
	OutputStream os = new FileOutputStream(fileWrite); 
	String sql = "SELECT t4.prod_id, t4.prod_name,t2.partner_com_name,t7.dict_name mana_name, "
			+ " t5.dict_name status_name,t3.dict_name invest_name,t6.dict_name currency_name, to_char(coalesce(sum(t1.order_amount),0), 'fm99,999,999,999,999,999,990.00') pay_sum, t2.invest_year,"
			+ " (cast(coalesce(t2.renew_year,'0') as NUMERIC)-cast(coalesce(t2.invest_year,'0') as NUMERIC)) reback,"
			+ " t10.dict_name module_name,t8.mng_fee,t8.mng_fee cost_scale,t1.buy_fee,t8.start_date,t8.end_date, t1.entry_time, t4.raise_fee "
			+ " FROM public.order_info t1 "
			+ " LEFT JOIN lp_info t2 on t1.part_comp = t2.lp_id "
			+ " LEFT JOIN product_info t4 on t1.prod_no = t4.prod_id and t4.check_status = '2' "
			+ " left join data_dict t3 on t3.dict_value = t2.invest_goal and t3.dict_type = 'invest' "
			+ " left join data_dict t5 on t5.dict_value = t4.prod_status and t5.dict_type = 'prodStus' "
			+ " left join data_dict t6 on t6.dict_value = t4.prod_currency and t6.dict_type = 'prodCur' "
			+ " left join data_dict t7 on t7.dict_value = t4.prod_mana and t7.dict_type = 'prodmana' "
			+ " LEFT JOIN product_fee t8 on t1.prod_no = t8.prod_id and t1.part_comp = t8.lp_id "
			+ " left join data_dict t10 on t10.dict_value = t8.mng_module and t10.dict_type = 'module' "
			+ " WHERE t1.is_checked = '2' and t1.stateflag = '0'  "
			+ " GROUP BY t1.is_checked, t1.stateflag, t1.create_time, t1.order_version, t1.buy_fee, t1.order_no, t1.entry_time,t2.partner_com_name,"
			+ " t2.renew_period,t2.invest_period,t2.invest_goal,t2.renew_year,t2.invest_year,t3.dict_name,t4.prod_id, t4.prod_name, t4.prod_currency, t4.prod_status, t4.raise_fee, t4.prod_mana,"
			+ " t5.dict_name,t6.dict_name,t7.dict_name,t8.mng_module, t8.mng_type, t8.start_date, "
			+ " t8.end_date, t8.mng_fee,t10.dict_name";
	SqlRowSet rs =  jt.queryForRowSet(sql);
	writeExcel(os,rs);   
	//修改Excel   
	//ExcelHandle.modifyExcel(new file(""),new File(""));   
	}   
	catch(Exception e)   
	{   
	e.printStackTrace();   
	}   
	} */
	
	
}
