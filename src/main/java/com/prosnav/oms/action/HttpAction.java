package com.prosnav.oms.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

public class HttpAction {
	/**
	 * 用户接口post请求
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String addByUrl(String path,Map<String, Object> map) throws Exception {
		String encoding = "UTF-8";
		String resp = "";
		//path = "http://192.168.1.28:9090/oauth/v1/users?code=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ0YW55dWFuIiwiY2lkIjoiMTIzIiwiZXhwIjoxNDY0ODMzODA1fQ.Oh68SYwHkHqm0eWQQmENcLYY9mp5EVxSsykwv0LVDyo";
		byte[] data = null;//params.getBytes(encoding);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		  Set<Map.Entry<String,Object>> sets=map.entrySet();
		  for(Map.Entry<String,Object> entry:sets){
			  conn.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
			  if(entry.getKey().equals("Parameters")){
				  data = String.valueOf(entry.getValue()).getBytes(encoding);
			  }
		  }
		//conn.setRequestProperty("Parameters", String.valueOf(data));
		conn.setConnectTimeout(5 * 1000);
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		//System.out.println(conn.getResponseCode()); // 响应代码 200表示成功
		if (conn.getResponseCode() == 200) {
			InputStream inStream = conn.getInputStream();
			resp = convertStreamToString(inStream);
			
		}
		return resp;
	}
/**
 * 用户接口get请求
 * @param path
 * @throws Exception
 */
	 public static  void sendSms(String path) throws Exception{
	       
	        path =" http://192.168.1.28:9090/oauth/v1/token?grant_type=refresh_token&refresh_token=rFG4v6jYSRavXXurEC1Zyg&client_id=client&client_secret=client&grant_type=refresh_token";
	        URL url =new URL(path);
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setConnectTimeout(5*1000);
	        conn.setRequestMethod("GET");
	        InputStream inStream = conn.getInputStream();    
	       // byte[] data = StreamTool.readInputStream(inStream);
	        String result=convertStreamToString(inStream);
	        System.out.println(result);
	    }
	/**
	 * 将input转为String
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {

			while ((line = reader.readLine()) != null) {

				sb.append(line);

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return sb.toString();

	}
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		json.put("pageIndex", 1);
		json.put("pageCount", 10);
		json.put("userInfo", "张");
		map.put("Parameters", json);
		map.put("Content-Type", "application/json; charset=utf-8");
		
		try {
			String s = addByUrl("",   map);
			System.out.println(s);
			//sendSms("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
