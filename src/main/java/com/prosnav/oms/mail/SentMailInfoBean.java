package com.prosnav.oms.mail;

import java.io.File;

import com.prosnav.oms.util.mailCache;

public class SentMailInfoBean {
	private String subject="";
	private String reviceMailaddr="";
	private String sentMailaddr="";
	private String mailContent="";
	private String firmId="";
	private String subjectId="";
	private String mail_busstype="";
	private String mail_businessprocess="";
	private String ccAddress;
	private String username = mailCache.user;
	private String password = mailCache.pwd;
	private String host = mailCache.host;
	private String from = mailCache.from;
	private String contentType;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getReviceMailaddr() {
		return reviceMailaddr;
	}
	public void setReviceMailaddr(String reviceMailaddr) {
		this.reviceMailaddr = reviceMailaddr;
	}
	public String getSentMailaddr() {
		return sentMailaddr;
	}
	public void setSentMailaddr(String sentMailaddr) {
		this.sentMailaddr = sentMailaddr;
	}
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	private String filePath="";
	public String getMail_busstype() {
		return mail_busstype;
	}
	public void setMail_busstype(String mail_busstype) {
		this.mail_busstype = mail_busstype;
	}
	public String getMail_businessprocess() {
		return mail_businessprocess;
	}
	public void setMail_businessprocess(String mail_businessprocess) {
		this.mail_businessprocess = mail_businessprocess;
	}
	public String getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
	private File[] files;

	public File[] getFiles() {
		return files;
	}
	public void setFiles(File[] files) {
		this.files = files;
	}
	
	private String[] ccAddrs;
	
	public String[] getCcAddrs() {
		return ccAddrs;
	}
	public void setCcAddrs(String[] ccAddrs) {
		this.ccAddrs = ccAddrs;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
