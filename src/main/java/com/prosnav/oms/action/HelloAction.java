package com.prosnav.oms.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prosnav.oms.services.HeloService;

public class HelloAction {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private String msg;
	private HeloService helloService;
	private String a;
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	private String b;
	private String c;
	
	public String hello() {
		log.info(">>>>>>>>>a:{}, b:{}, c:{}", new Object[]{a, b, c});
		msg = helloService.hello();
		return "success";
	}
	
	public String getMsg() {
		return msg;
	}

	public void setHelloService(HeloService helloService) {
		this.helloService = helloService;
	}
}
