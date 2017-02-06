package com.prosnav.oms.interceptor;

public class FuncInfo {
	private String funcKey;
	private long func_id;
	private String func_url;
	private String func_menu;
	private String func_limit;
	private String func_describle;
	private String func_action;
	public String getFuncKey() {
		return funcKey;
	}
	public void setFuncKey(String funcKey) {
		this.funcKey = funcKey;
	}
	
	public long getFunc_id() {
		return func_id;
	}
	public void setFunc_id(long func_id) {
		this.func_id = func_id;
	}
	public String getFunc_url() {
		return func_url;
	}
	public void setFunc_url(String func_url) {
		this.func_url = func_url;
	}
	public String getFunc_menu() {
		return func_menu;
	}
	public void setFunc_menu(String func_menu) {
		this.func_menu = func_menu;
	}
	public String getFunc_limit() {
		return func_limit;
	}
	public void setFunc_limit(String func_limit) {
		this.func_limit = func_limit;
	}
	public String getFunc_describle() {
		return func_describle;
	}
	public void setFunc_describle(String func_describle) {
		this.func_describle = func_describle;
	}
	public String getFunc_action() {
		return func_action;
	}
	public void setFunc_action(String func_action) {
		this.func_action = func_action;
	}
	@Override
	public String toString() {
		return "FuncInfo [funcKey=" + funcKey + ", func_id=" + func_id + ", func_url=" + func_url + ", func_menu="
				+ func_menu + ", func_limit=" + func_limit + ", func_describle=" + func_describle + ", func_action="
				+ func_action + "]";
	}

	


}
