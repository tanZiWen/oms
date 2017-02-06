package com.prosnav.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tab.MyPhoner;


public class Constants {
	public static final String JWT = "jwt";
	public static final String USER = "sessionUser";
	public static final String SIGNED_JWT = "signed_jwt";
	public static final String FUNC_KEY = "prosnav:function:";
	public static final String SEND_CUST_EMAIL = "/home/deployer/oms/doc/";
	public static Map<String, Object> cache = new HashMap<String, Object>();
	public static String CALLCENTER_HOST = "192.168.8.206";
	public static int CALLCENTER_PORT = 880;
	public static String RECORDING_HOST = "192.168.8.206";
	public static int RECORDING_PORT = 55503;
	public static Map<String, List<Map<String, Object>>> dictMapByType = new HashMap<String, List<Map<String, Object>>>();

}
