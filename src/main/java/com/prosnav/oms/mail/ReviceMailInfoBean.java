package com.prosnav.oms.mail;

import java.util.Date;

public class ReviceMailInfoBean {
	
	public String subjectNum="";
	public String content="";
	public Date reviceDate=null;
	public String fromaddr = "";
	public String status ="";
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFromaddr() {
		return fromaddr;
	}
	public void setFromaddr(String fromaddr) {
		this.fromaddr = fromaddr;
	}
	public String getSubjectNum() {
		return subjectNum;
	}
	public void setSubjectNum(String subjectNum) {
		this.subjectNum = subjectNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReviceDate() {
		return reviceDate;
	}
	public void setReviceDate(Date reviceDate) {
		this.reviceDate = reviceDate;
	}
	

}
