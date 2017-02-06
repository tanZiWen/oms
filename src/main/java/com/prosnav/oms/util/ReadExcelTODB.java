package com.prosnav.oms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


import com.opensymphony.xwork2.Action;
import com.prosnav.oms.dao.CustDao;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelTODB {
	
	public static String data(File file){
		//File file = new File(path);
		String data ="";
		try {
			Workbook wook = Workbook.getWorkbook(file);
			Sheet sheet = wook.getSheet(0);
			int row = sheet.getRows();
			int col = sheet.getColumns();
			
			for(int i=1;i<row;i++){
				for(int j=0;j<col;j++){
					data = data +sheet.getCell(j, i).getContents()+",";
				}
			}
			
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
}
