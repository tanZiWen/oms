package com.prosnav.oms.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;


public class jdbcUtil {
	/*public static JdbcTemplate jdbcTemplate() {
		JdbcTemplate template = new JdbcTemplate(Ctx.getDataSource());
		return template;
	}*/
private static ApplicationContext  ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    
    public static Object getBean(String beanName){
         return ctx.getBean(beanName);
    }  
    private static JdbcTemplate jt =(JdbcTemplate) getBean("template");
   
    /*public void cunshu(){
    	 String param2Value = (String) jdbcTemplate.execute(   
        	     new CallableStatementCreator() {   
        	        public CallableStatement createCallableStatement(Connection con) throws SQLException {   
        	           String storedProc = "{call testpro(?,?)}";// 调用的sql   
        	           CallableStatement cs = con.prepareCall(storedProc);   
        	           cs.setString(1, "p1");// 设置输入参数的值   
        	           cs.registerOutParameter(2, OracleTypes.VARCHAR);// 注册输出参数的类型   
        	           return cs;   
        	        }   
        	     }, new CallableStatementCallback() {   
        	         public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
        	           cs.execute();   
        	           return cs.getString(2);// 获取输出参数的值   
        	     }   
        	  });   
    }*/
    @SuppressWarnings("unchecked")
	public  List<Map<String,Object>> test(String sql,final Object[] o) {  
    	List<Map<String,Object>> locationInfo=null;  
    	locationInfo=(List<Map<String, Object>>) jt.execute(sql, new PreparedStatementCallback(){  
    	    public Object doInPreparedStatement(PreparedStatement stmt) throws SQLException, DataAccessException {  
    	List<HashMap<String,Object>> infoList=new ArrayList<HashMap<String,Object>>(); 
    		for(int i=0;i<o.length;i++){
    			stmt.setObject(i+1, o[i]);
    		}
    	     
    	    ResultSet rs = stmt.executeQuery();  
    	    ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
    	    while(rs.next()){  
    	         Map<String,Object> infoMap=new HashMap<String,Object>(); 
    	         for (int j = 1; j < columnCount + 1; j++) {
    	        	 infoMap.put(rsmd.getColumnLabel(j), rs.getObject(j)); // 将每一行数据放入map
					}
    	              infoList.add((HashMap<String, Object>) infoMap);  
    	    }  
    	    return infoList;  
    	}});  
    	return locationInfo; 
    }
   /* *//**
     * 生成序列号
     * @return
     *//*
    public static long seqbakup(){
    	Date date = new Date();
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
    	String data = format.format(date);
    	String sql="select * from public.seq_id()";
    	String sql1="SELECT * FROM seq_nu";
    	jt.queryForList(sql);
    	List<Map<String, Object>> list = jt.queryForList(sql1);
    	String seq = (String) list.get(0).get("table_id");
    	seq = data+seq  ;
    	long a = Long.parseLong(seq);
    	return a;
    }*/
    /**
     * 生成序列号
     * @return
     */
    public static long seq(){
    	Date date = new Date();
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
    	String data = format.format(date);
    	String sql="select * from public.seq_id()";
    	List<Map<String, Object>> list = jt.queryForList(sql);
    	String seq = (String) list.get(0).get("seq_id");
    	/*seq = data+seq  ;
*/    	long a = Long.parseLong(seq);
    	return a;
    }
   /*public static void main(String[] args) {
	System.out.println(seq());
}*/
}
