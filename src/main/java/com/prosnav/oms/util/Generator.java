package com.prosnav.oms.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Generator {
	private final static byte[] lock = new byte[0];
	public static String getCode(String s) {
		synchronized(lock) {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = dateFormat.format(c.getTime());
			return String.format(str, s);
		}
	}
}
