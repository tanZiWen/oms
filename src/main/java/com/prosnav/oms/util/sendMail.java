package com.prosnav.oms.util;

import java.io.File;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.prosnav.oms.dao.mailDao;
import com.prosnav.oms.mail.SentMailInfoBean;

public class sendMail {
	/**
	 * 发送邮件返回true为
	 * @param txt
	 * @param to
	 * @param subject
	 * @param attachment
	 * @param judge
	 * @return
	 *//*
	public static boolean sendmail(String txt,String to ,String subject,File attachment,boolean judge){
		
		String subjectId =IdGen.newId()+"";
		subjectId = subjectId.replace("-", "");
	    subject = subject+subjectId;//自生成的一段id
		boolean flag = send(txt, to, subject, null);
		
		
			mailDao.addMessage(subjectId,"0",to,"否");
			return true
		
	}*/
	/**
	 * 发送邮件并存数据到数据库中
	 * @param sentmail
	 * @param save
	 */
	public static void sendMessage(SentMailInfoBean sentmail, boolean save){
		final SentMailInfoBean sentmailagin=sentmail;
		String subjectId = sentmail.getSubjectId();//IdGen.random(firmId);
		final String subjectIdagin=subjectId;
		boolean flag = send(sentmail,subjectId);
		
		if(flag){
			int count = mailDao.select_subject_id(subjectId);
			if(count>0){
				mailDao.updateMessage(subjectId, "0");
				
			}else{
				mailDao.addMessage(sentmail,subjectId,"0");
			}
		}else{
			mailDao.addMessage(sentmail, subjectId, "1");
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				
				public void run() {
					boolean stop = send(sentmailagin,subjectIdagin);
					System.out.println("stop====="+stop);
					if(stop){
						mailDao.updateFlag(subjectIdagin);
						cancel();
					}
				}
			}, 0, 60*1000);
		}
		
		
		
	}
	/**
	 * 发送邮件
	 * @param sentmail
	 * @param save
	 * @return
	 */
	private static boolean send(SentMailInfoBean sentmail,String subjectId) {  
		 String txt = sentmail.getMailContent();
		 String subject = sentmail.getSubject();
		 String to = sentmail.getReviceMailaddr();
		 File[] files = sentmail.getFiles();
		 String host = sentmail.getHost();
	     String username = sentmail.getUsername();
	     String password =sentmail.getPassword();
    	 String from = sentmail.getSentMailaddr();
    	 String ccaddress = sentmail.getCcAddress();
    	 String[] ccaddrs = sentmail.getCcAddrs();
    	 Session session =null;
    	 MimeMessage message=null;
		 Properties props = new Properties();
	     try {  
	         props.put("mail.smtp.host", host);  
	         props.put("mail.smtp.auth", "true");  
	         session = Session.getDefaultInstance(props,null);  
	         //session.setDebug(true);  
	         message = new MimeMessage(session); 
	      message.setFrom(new InternetAddress(from));
	      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	      if(ccaddress!=null && !"".equals(ccaddress) ){
	    	  InternetAddress internetAddressCC = new InternetAddress(ccaddress);
		      message.addRecipient(Message.RecipientType.CC, internetAddressCC);
	      }
	      if(ccaddrs != null && ccaddrs.length > 0) {
	    	  for(String c: ccaddrs) {
	    		  InternetAddress internetAddressCC = new InternetAddress(c);
			      message.addRecipient(Message.RecipientType.CC, internetAddressCC);
	    	  }
	      }
	      message.setSubject(subject+"-"+subjectId);  
	      Multipart multipart = new MimeMultipart();  
	      BodyPart contentPart = new MimeBodyPart(); 
	      if(sentmail.getContentType() != null) {
	    	  contentPart.setContent(txt, "text/html;charset=gb2312");  
	      }else {
	    	  contentPart.setText(txt);
	      }
	      
	      multipart.addBodyPart(contentPart);  
	   
	      if (files !=null && files.length > 0) {
	    	  for(File file: files) {
	    		  BodyPart attachmentBodyPart = new MimeBodyPart();
	 	          DataSource source = new FileDataSource(file);
	 	          attachmentBodyPart.setDataHandler(new DataHandler(source));
	 	          //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
	 	          //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
	 	          attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
	 	          multipart.addBodyPart(attachmentBodyPart);
	    	  }
	      }
	      message.setContent(multipart);  
	      message.saveChanges();  
	      Transport transport = session.getTransport("smtp");  
	      transport.connect(host, username, password);  
	      transport.sendMessage(message, message.getAllRecipients());  
	      transport.close();
	     
	      return true;
	     } catch (Exception e) {  
	      e.printStackTrace();
	     
	      return false;
	     }  
	    }
	
	public static boolean testConnect(SentMailInfoBean sentmail) {
		 Properties props = new Properties();
		 props.put("mail.smtp.auth", "true");
		 props.put("mail.smtp.starttls.enable", "true");
		 props.put("mail.smtp.host", sentmail.getHost());
		 props.put("mail.smtp.port", "25");
		 final String username = sentmail.getUsername();
		 final String pwd = sentmail.getPassword();
		 Session session = Session.getInstance(props, new Authenticator() {
			 protected PasswordAuthentication getPasswordAuthentication() {
			    return new PasswordAuthentication(username, pwd);
			 }
		 });
		 MimeMessage message = new MimeMessage(session);
		 
         try {
        	 message.setContent("This is a test", "text/plain");
			 message.setFrom(new InternetAddress(sentmail.getUsername()));
			 message.addRecipient(Message.RecipientType.TO,
		             new InternetAddress(sentmail.getUsername()));
			 Transport transport = session.getTransport("smtp");  
		     transport.connect(sentmail.getHost(), username, pwd);  
		     transport.sendMessage(message, message.getAllRecipients());  
		     transport.close();
		     return true;
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		SentMailInfoBean sentmail = new SentMailInfoBean();
		sentmail.setHost("smtp.prosnav.com");
		sentmail.setUsername("oa@prosnav.com");
		sentmail.setPassword("");
		boolean b = testConnect(sentmail);
	}
	
}
