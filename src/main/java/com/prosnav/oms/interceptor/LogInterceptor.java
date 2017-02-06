package com.prosnav.oms.interceptor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LogInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 7013816941154484816L;
	
	@SuppressWarnings("rawtypes")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = "";
		Class clazz = invocation.getAction().getClass();
		Logger log = LoggerFactory.getLogger(clazz);
		Map<String, Object> params = invocation.getInvocationContext().getParameters();
		String methodName = invocation.getProxy().getMethod();
		String fulMethodName = clazz.getName() + "." + methodName;
		log.info("[Request info] Request action: {} parameters:{}",  new String[]{fulMethodName, params.toString()});
		try {
			result = invocation.invoke();
		} catch(Exception e) {
			log.error(">>>>>>>>>>>>>>", "Request " + fulMethodName + " failed.<<<<<<<<<<<<<<", e);
			return "error";
		}
		log.info("[Request info] The result of {} is \"{}\"", new String[]{fulMethodName, result});
		return result;
	}
	
	public void init() {
	}
}
