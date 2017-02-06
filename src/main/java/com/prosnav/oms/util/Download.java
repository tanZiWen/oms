package com.prosnav.oms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Download {
	/**  
     * 下载远程文件并保存到本地  
     * @param remoteFilePath 远程文件路径   
     * @param localFilePath 本地文件路径  
     */
	public static void downloadFile(String remoteFilePath, HttpServletResponse response) {
		
		BufferedInputStream bis = null;
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
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 写入excel
	 * 
	 * @param os
	 * @param rs
	 */
	public static void writeExcel(OutputStream os, SqlRowSet rs,String[] o) {
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
			
			  for (int k = 0; k < o.length; k++) {
			  //rs.getMetaData().getColumnName()获取表的列名。并添加到
			  //excel表Label里,Label(i,j,s)表示i列j行添加s,s必须是String 
			  ws.addCell(new Label(k, 0, o[k]));
			  }
			 
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
}
