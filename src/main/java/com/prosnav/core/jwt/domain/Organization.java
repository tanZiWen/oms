package com.prosnav.core.jwt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Organization extends MongoEntity {
	
	private String code;
	private String type;
	private String name;
	private String parentCode;
	private boolean deleted;
	
	public Organization() {}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String toString() {
		return new ToStringBuilder(this)
			.append("_id", _id)
			.append("code", code)
			.append("name", name)
			.toString();
	}
}
