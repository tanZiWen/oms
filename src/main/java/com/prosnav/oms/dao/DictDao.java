package com.prosnav.oms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.model.DictReference;
import com.prosnav.oms.util.jdbcUtil;

public class DictDao {
	private JdbcTemplate jt = (JdbcTemplate) jdbcUtil.getBean("template");
	/**
	 * 查询字典值
	 * @param type
	 * @param value
	 * @return
	 */
	public List<Map<String,Object>> queryDict(String level){
		String sql="select t.lel_name,t.lel_value,t.lel_type from (select  t3.dict_name lel_name,t3.dict_value lel_value,t3.dict_type lel_type "
				+ "from data_dict t3 WHERE t3.dict_type = 'level' "
				+ "UNION ALL "
				+ "SELECT t4.dict_name sex_name,t4.dict_value sex_value,t4.dict_type sex_type "
				+ "FROM data_dict T4 WHERE t4.dict_type = 'sex' UNION ALL "
				+ "SELECT t5.dict_name idtype_name,t5.dict_value idtype_value,t5.dict_type idtype_type "
				+ "FROM data_dict T5 WHERE t5.dict_type = 'idtype')t "
				+ "where t.lel_type=?";
		
		return jt.queryForList(sql,level);
		 
	}
	/**
	 * 查询字典值
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> queryOrder(String type){
		String sql="select * from data_dict where dict_type=?";
		return jt.queryForList(sql,type);
	}
	public Map<String, Object> batchQueryAndMap(List<DictReference> references) {
		List<Map<String, Object>> dictItems = batchQuery(references);
		Map<String, Object> referencesMap = new HashMap<String, Object>();
		for(int i=0;i<dictItems.size();i++) {
			referencesMap.put(DictReference.generateDictKey((String)dictItems.get(i).get("dict_type"), (String)dictItems.get(i).get("dict_value")), dictItems.get(i));
		}
		
		return referencesMap;
	}
	
	public List<Map<String, Object>> queryDictList() {
		String sql = "select * from data_dict";
		return jt.queryForList(sql);
	}
	
	public List<Map<String, Object>> batchQuery(List<DictReference> references){
//		List<String> keys = typeAndValues.keySet().toArray(a)
		StringBuffer sb = new StringBuffer("select * from data_dict where ");
		for(int i=0;i<references.size();i++) {
			if(i > 0) {
				sb.append(" or ");
			}
			
			sb.append("(dict_type='");
			sb.append(references.get(i).getType());
			sb.append("' and dict_value in (");
			
			List<String> values = references.get(i).getValues();
			
			for(int j=0;j<values.size();j++) {
				if(j > 0) {
					sb.append(",");
				}
				
				sb.append("'");
				sb.append(values.get(j));
				sb.append("'");
			}
			sb.append("))");
		}
		sb.append(" order by dict_type");
		
//		System.out.println("query reference sql: " + sb.toString());
		
		return jt.queryForList(sb.toString());
	}
}
