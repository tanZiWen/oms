package com.prosnav.core.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.oms.interceptor.FuncInfo;

public class Main {
	
	public static String zhuanhuan(){
		String fullFileName = "C:/Users/Fu/Desktop/oms项目/userStr(1).json";
	     
	     File file = new File(fullFileName);
	     Scanner scanner = null;
	     StringBuilder buffer = new StringBuilder();
	     try {
	         scanner = new Scanner(file, "utf-8");
	         while (scanner.hasNextLine()) {
	             buffer.append(scanner.nextLine());
	         }

	     } catch (FileNotFoundException e) {
	         // TODO Auto-generated catch block  

	     } finally {
	         if (scanner != null) {
	             scanner.close();
	         }
	     }
	     return buffer.toString();
	}
	 
	public static void main(String[] args) {
		FuncInfo func = new FuncInfo();
		func.setFuncKey("testFunc");
		String funcStr = JSONObject.toJSON(func).toString();
		System.out.println(funcStr);
	}
}
