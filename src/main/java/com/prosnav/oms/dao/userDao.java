package com.prosnav.oms.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.oms.util.jdbcUtil;

public class userDao {
	private JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");

	public List<Map<String, Object>> getTeamUser(long user_id) {
		String sql = "SELECT user_id,  real_name,  email FROM upm_user "
				+ "where user_id=(SELECT  leader_id FROM public.upm_user WHERE user_id=?)";
		return jt.queryForList(sql, user_id);
	}

	/**
	 * header.jsp权限 客户资源管理
	 */
	public List<Map<String, Object>> userCust(long role_id) {
		String sql = "SELECT func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2 "
				+ " where t1.func_id=t2.func_id and func_limit='2' and (func_menu='客户资源管理' or func_menu='机构')";
		return jt.queryForList(sql, role_id);

	}

	/**
	 * header.jsp权限 产品
	 */
	public List<Map<String, Object>> userProduct(long role_id) {
		String sql = "SELECT func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2 "
				+ " where t1.func_id=t2.func_id and func_limit='2' and (func_menu='产品' or func_menu='gp')";
		return jt.queryForList(sql, role_id);

	}

	/**
	 * header.jsp权限 订单
	 */
	public List<Map<String, Object>> userOrder(long role_id) {
		String sql = "SELECT func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2 "
				+ " where t1.func_id=t2.func_id and func_limit='1' and func_menu='订单'";
		return jt.queryForList(sql,role_id);

	}

	/**
	 * header.jsp权限 奖金
	 */
	public List<Map<String, Object>> userReward(long role_id) {
		String sql = "SELECT func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2 "
				+ "where t1.func_id=t2.func_id and func_limit='2' and func_menu='奖金'";
		return jt.queryForList(sql, role_id);

	}

	/**
	 * header.jsp权限 报表
	 */
	public List<Map<String, Object>> userReport(long role_id) {
		String sql = "SELECT func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2 "
				+ " where t1.func_id=t2.func_id and func_limit='1' and func_menu='报表'";
		return jt.queryForList(sql,role_id);

	}
	
	/**
	 * header.jsp权限 报表
	 */
	public List<Map<String, Object>> userdiffcoe(long role_id) {
		String sql = "SELECT func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2 "
				+ " where t1.func_id=t2.func_id and func_limit='2' and func_menu='产品系数'";
		return jt.queryForList(sql,role_id);

	}
	
	/**
	 * header.jsp权限 客户分配
	 */
	
	public List<Map<String, Object>> userCustDistribution(long role_id) {
		String sql = "select func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2"
				+ " where t1.func_id=t2.func_id and func_limit='2' and func_menu='客户分配'";
		return jt.queryForList(sql, role_id);
	}
	
	/**
	 * header.jsp权限 客户邮箱
	 */
	
	public List<Map<String, Object>> userCustEmail(long role_id) {
		String sql = "select func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2"
				+ " where t1.func_id=t2.func_id and func_limit='2' and func_menu='客户邮件'";
		return jt.queryForList(sql, role_id);
	}
	
	/**
	 * header.jsp权限 邮箱管理
	 */
	
	public List<Map<String, Object>> userEmailManage(long role_id) {
		String sql = "select func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2"
				+ " where t1.func_id=t2.func_id and func_limit='2' and func_menu='邮件管理'";
		return jt.queryForList(sql, role_id);
	}
	
	/**
	 * header.jsp权限 客户外拨
	 */
	
	public List<Map<String, Object>> userCustCall(long role_id) {
		String sql = "select func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2"
				+ " where t1.func_id=t2.func_id and func_limit='2' and func_menu='客户外拨'";
		return jt.queryForList(sql, role_id);
	}
	
	/**
	 * header.jsp权限 外拨管理
	 */
	
	public List<Map<String, Object>> userCallManage(long role_id) {
		String sql = "select func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2"
				+ " where t1.func_id=t2.func_id and func_limit='2' and func_menu='外拨管理'";
		return jt.queryForList(sql, role_id);
	}
	
	/**
	 * header.jsp权限 录音管理
	 */
	
	public List<Map<String, Object>> userRecordManage(long role_id) {
		String sql = "select func_describle,func_url from func_info t1,(SELECT func_id from role_func where role_id=?) t2"
				+ " where t1.func_id=t2.func_id and func_limit='2' and func_menu='录音管理'";
		return jt.queryForList(sql, role_id);
	}

	/**
	 * 插入历史信息
	 * 
	 * @param json
	 * @param id
	 * @param remark
	 * @param type
	 */
	public void addtask(JSONObject json, long id, String remark, String type,
			long user_id) {
		String sql = "insert into task(content,id,remark,type,version,user_id)values('"
				+ json + "',?,?,?,default,?) ";
		jt.update(sql, id, remark, type, user_id);
	}
	
	/**
	 * 获取user信息by realname
	 */
	public List<Map<String, Object>> getUserByRealname(String realName) {
		String sql = "select real_name, email, user_id, status from upm_user where real_name = ? and status = 'ok'";
		return jt.queryForList(sql, realName);
	}
	
	/**
	 * 获取所有在职客户数据
	 */
	
	public List<Map<String, Object>> getCustServiceUser() {
		String sql = "select * from upm_user where status = 'ok' and role_codes && '{customer_service_handle,customer_service_manage}'::character varying[]";
		return jt.queryForList(sql);
	}
	
	/**
	 * 根据用户id获取用户信息
	 */
	public List<Map<String, Object>> getUserById(Long user_id) {
		String sql = "select * from upm_user where user_id=?";
		return jt.queryForList(sql, user_id);
	}
	
	/**
	 * 根据角色获取用户信息
	 */
	public List<Map<String, Object>> getUsertByRoleCode(String[] roleCodes) {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i < roleCodes.length; i++) {
			if(i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(roleCodes[i]);
			sb.append("'");
		}
		String str = sb.toString();
		String sql = "select user_id, user_name, real_name, email from upm_user where status='ok' and role_codes && ARRAY["+str+"]::character varying[]";
		return jt.queryForList(sql);
	}
}
