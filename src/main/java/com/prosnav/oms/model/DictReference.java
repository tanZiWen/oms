package com.prosnav.oms.model;

import java.util.List;

public class DictReference {
	private String type;

	private List<String> values;
	
	public DictReference(String type, List<String> values) {
		this.type = type;
		this.values = values;
	}
	
	public String getType() {
		return type;
	}

	public List<String> getValues() {
		return values;
	}
	
	public static String generateDictKey(String type, String value) {
		return type + "#" + value;
	}
}