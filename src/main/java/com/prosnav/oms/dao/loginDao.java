package com.prosnav.oms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.interceptor.FuncInfo;
import com.prosnav.oms.util.jdbcUtil;

public class loginDao {
	private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil
            .getBean("template");
	public static List<Map<String,Object>> login(String username,String pwd){
		String sql="SELECT userid, username, password FROM login_info where username=? and password=?";
		return jt.queryForList(sql,username,pwd);
	}
	public static int loginPwd(String username,String pwd){
		String sql="select count(*) from login_info where username=? and password=?";
		return jt.queryForInt(sql, username,pwd);
	}
	//获取用户权限
	public static User getuser(User user){
		FuncInfoDao funcInfoDao = new FuncInfoDao();
		String roleid = user.getRoleList().get(0).getCode();
	    long role_id = Long.parseLong(roleid); 
		//通过username password查询id
		List<FuncInfo> funcInfoList = funcInfoDao.Querybyuserid(role_id);
		Map<String, FuncInfo> funcs=new HashMap<String,FuncInfo>();
		for(FuncInfo func : funcInfoList) {
			//saveToRedis(func);
			funcs.put(func.getFunc_action(), func);
			System.out.println(func.getFunc_action());
		}
		/*user.setFunctionMap(funcs);*/
		
		
		return user;
	}
	//通过用户id查询role_id
	public static  List<Map<String,Object>> role(String role_code){
		String sql="SELECT role_id FROM public.role_info where role_code=?";
		return jt.queryForList(sql, role_code);
	}
	
}
