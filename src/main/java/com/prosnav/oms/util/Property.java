package com.prosnav.oms.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.prosnav.common.Constants;

public class Property extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4836730449254415734L;
	Properties prop = new Properties();     
	public void init() throws ServletException {
		try {
			 prop.load(this.getClass().getResourceAsStream("/prop.properties"));     ///加载属性列表
			 Iterator<String> it= prop.stringPropertyNames().iterator();
			 while(it.hasNext()){
				 String key=it.next();
				 Constants.CALLCENTER_HOST = prop.getProperty("callcenter.host", "192.168.8.206");          
				 Constants.CALLCENTER_PORT = Integer.parseInt(prop.getProperty("callcenter.port", "880"));
				 Constants.RECORDING_HOST = prop.getProperty("recording.host", "192.168.8.206");   
				 Constants.RECORDING_PORT = Integer.parseInt(prop.getProperty("recording.port", "55503"));
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
