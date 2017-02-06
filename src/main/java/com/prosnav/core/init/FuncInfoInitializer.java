package com.prosnav.core.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prosnav.common.Constants;
import com.prosnav.oms.dao.DictDao;
import com.prosnav.oms.dao.FuncInfoDao;
import com.prosnav.oms.interceptor.FuncInfo;

@SuppressWarnings("serial")
public class FuncInfoInitializer extends HttpServlet {
//	private StringRedisTemplate redisTemplate;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		//redisTemplate = (StringRedisTemplate) SpringContextUtil.getBean("redisTemplate"); 
		FuncInfoDao funcInfoDao = new FuncInfoDao();
		List<FuncInfo> funcInfoList = funcInfoDao.QueryAll();
		for(FuncInfo func : funcInfoList) {
			saveToRedis(func);
		}
		log.debug("++++++++++++++++All function map:" + Constants.cache.keySet() + "++++++++++++++++++");
//		System.out.println("++++++++++++++++All function map:" + Constants.cache.keySet() + "++++++++++++++++++");
		DictDao dictDao = new DictDao();
		List<Map<String, Object>> dictMap = dictDao.queryDictList();
		List<Map<String, Object>> list = null;
		Map<String, List<Map<String, Object>>> d = new HashMap<String, List<Map<String, Object>>>();
		if(dictMap != null && dictMap.size() > 0) {
			for(Map<String, Object> dict: dictMap) {
				if(!d.containsKey((String)dict.get("dict_type"))) {
					list = new ArrayList<Map<String, Object>>();
				}else {
					list = d.get((String)dict.get("dict_type"));
				}
				list.add(dict);
				d.put((String)dict.get("dict_type"), list);
			}
		}
		Constants.dictMapByType = d;
	}

	private void saveToRedis(FuncInfo func) {
		Constants.cache.put(func.getFunc_action(), func);
	}
}
