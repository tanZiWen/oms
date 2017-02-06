package com.prosnav.oms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.interceptor.FuncInfo;
import com.prosnav.oms.util.jdbcUtil;

public class FuncInfoDao {
	private JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");

	@SuppressWarnings("unchecked")
	public List<FuncInfo> QueryAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT func_id, func_url, func_menu, func_limit, func_describle, func_key, "
				+ "func_action FROM public.func_info";
		return jt.query(sql, new BeanPropertyRowMapper(FuncInfo .class));
	}
	public List<FuncInfo> Querybyuserid(long id) {
		// TODO Auto-generated method stub
		String sql = "SELECT  t.func_id, t.func_url, t.func_menu, t.func_limit, t.func_describle, t.func_key, "
				+ "t.func_action FROM public.func_info t,public.role_func t1 "
				+ "where t.func_id=t1.func_id and t1.role_id=?";
		List<FuncInfo> funlist = new ArrayList<FuncInfo>();
		//jt.
		List<Map<String, Object>> list = jt.queryForList(sql,id);
		for(int i=0;i<list.size();i++){
			long func_id = (Long) list.get(i).get("func_id");//Long.parseLong((String)list.get(i).get("func_id"));
			FuncInfo func = new FuncInfo();
			func.setFunc_action((String)list.get(i).get("func_action"));
			func.setFunc_describle((String)list.get(i).get("func_describle"));
			func.setFunc_id(func_id);
			func.setFunc_limit((String)list.get(i).get("func_limit"));
			func.setFunc_menu((String)list.get(i).get("func_menu"));
			func.setFunc_url((String)list.get(i).get("func_url"));
			func.setFuncKey((String)list.get(i).get("func_key"));
			funlist.add(func);
		}
		return funlist;
	}
	
}
