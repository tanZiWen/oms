package com.prosnav.oms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.prosnav.oms.mail.FlowWorkMail;
import com.prosnav.oms.mail.ReviceMailInfoBean;
import com.sun.mail.imap.IMAPStore;

public class MailTimer extends TimerTask {
	 private String saveAttachPath = ""; //附件下载后的存放目录   
	@Override
	public void run( ) {
		FlowWorkMail mailop=new FlowWorkMail();
		mailop.WorkFlowOP();
		// List<String> list = recive();
		//更改数据库标志为已回复
		
		

	}
	/**
	 * 接收未读邮件
	 * @param subjectId
	 */
	public static  List<ReviceMailInfoBean> recive() {
		IMAPStore store = null;
	    Folder folder = null;
	    InputStream in =null;
	    List<ReviceMailInfoBean> msgList = new ArrayList<ReviceMailInfoBean>();
	    ReviceMailInfoBean rmb = new ReviceMailInfoBean();
	    try {
	    	Properties props = new Properties();
	    	/*in =MailTimer.class.getClassLoader().getResourceAsStream("mail.properties");        
	    	props.load(in);*/
	    	String host = mailCache.imaphost;
	    	String username = mailCache.user;
	    	String password = mailCache.pwd;
	    	props.put("mail.store.protocol","imap");
	    	props.put("mail.imap.host",host);
	        Session session = Session.getInstance(props);
	        //session.setDebug(true); 
	        store=(IMAPStore) session.getStore("imap");
	        store.connect( username, password);
	        folder = store.getFolder("INBOX");
	        folder.open(Folder.READ_WRITE);
	        Message message[] = folder.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));
	        for(int i=0; i<message.length;i++){
	        	 MimeMessage msg = (MimeMessage) message[i];
	        	 InternetAddress address[] = (InternetAddress[]) msg.getFrom();   
	             String from = address[0].getAddress();   
	             if (from == null)   
	                 from = "";   
	             String fromaddr =  from ;
	        	 String subject = msg.getSubject();
	        	 Date date =msg.getSentDate();
		         StringBuffer content = new StringBuffer();  
	   			 getMailTextContent((Part)msg, content);
	   			 rmb.setContent(content.toString());
	   			 rmb.setReviceDate(date);
	   			 rmb.setSubjectNum(subject);
	   			 rmb.setFromaddr(fromaddr);
	   			 rmb.setStatus("0");
	   			 msgList.add(rmb);
	   			 msg.setFlag(Flags.Flag.SEEN,true);
	           
	        }
  	    } catch (NoSuchProviderException e) {
	        e.printStackTrace();
	        
	       
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        rmb.setStatus("1");
	        msgList.add(rmb);
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally {
	        try {
	            if (folder != null) {
	                folder.close(false);
	            }
	            if (store != null) {
	                store.close();
	            }
	            if(in!=null){
	            	in.close();
	            }
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            
	        } catch(IOException e1){
	        	e1.printStackTrace();
	        }
	    }
	    return msgList;
	}

	/**
	 * 发件人
	 * @param msg
	 * @return
	 * @throws MessagingException
	 */
	public static String getfromaddr(MimeMessage msg) throws MessagingException{
		InternetAddress address[] = (InternetAddress[]) msg.getFrom();
		String personal = address[0].getPersonal();   
        if (personal == null)   
            personal = ""; 
        String from = address[0].getAddress();   
        if (from == null)   
            from = "";   
         
        String fromaddr =personal+  "<" + from + ">";
        return fromaddr;
	}
	public String getPersonal(MimeMessage msg)throws MessagingException{
		InternetAddress address[] = (InternetAddress[]) msg.getFrom();
		 String personal = address[0].getPersonal();   
	        if (personal == null)   
	            personal = ""; 
		return personal;
	}
	/**
	 * 获取正文
	 * @param part
	 * @param content
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {  
	    boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;   
	    if (part.isMimeType("text/*") && !isContainTextAttach) {  
	    	String mailcontent = part.getContent().toString();
	    	if(part.isMimeType("text/html")){
	    		String content_mail =part.getContent().toString();
		    	if(part.isMimeType("text/html")){
		    		Document doc =  Jsoup.parse(content_mail);//解析HTML字符串返回一个Document实现
		    		//Element link = doc.select("div").get(0);//查找第一个a元素docc
		    		String text = doc.body().text(); // "An example link"//取得字符串中的文本
		    		content_mail = text;
		    		
		    	}
		        content.append(content_mail); 
	    	}
	    	content.append(mailcontent); 
	    } else if (part.isMimeType("message/rfc822")) {   
	        getMailTextContent((Part)part.getContent(),content);  
	    } else if (part.isMimeType("multipart/*")) {  
	        Multipart multipart = (Multipart) part.getContent();  
	        BodyPart bodyPart = multipart.getBodyPart(0);  
	        getMailTextContent(bodyPart,content);
	        /*for (int i = 0; i < partCount; i++) {  
	            BodyPart bodyPart = multipart.getBodyPart(i);  
	            getMailTextContent(bodyPart,content);  
	        }  */
	    }  
	}
	
	  /**  
     * 判断此邮件是否包含附件  
     */  
    public boolean isContainAttach(Part part) throws Exception {   
        boolean attachflag = false;   
        String contentType = part.getContentType();   
        if (part.isMimeType("multipart/*")) {   
            Multipart mp = (Multipart) part.getContent();   
            for (int i = 0; i < mp.getCount(); i++) {   
                BodyPart mpart = mp.getBodyPart(i);   
                String disposition = mpart.getDisposition();   
                if ((disposition != null)   
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition   
                                .equals(Part.INLINE))))   
                    attachflag = true;   
                else if (mpart.isMimeType("multipart/*")) {   
                    attachflag = isContainAttach((Part) mpart);   
                } else {   
                    String contype = mpart.getContentType();   
                    if (contype.toLowerCase().indexOf("application") != -1)   
                        attachflag = true;   
                    if (contype.toLowerCase().indexOf("name") != -1)   
                        attachflag = true;   
                }   
            }   
        } else if (part.isMimeType("message/rfc822")) {   
            attachflag = isContainAttach((Part) part.getContent());   
        }   
        return attachflag;   
    }   
  
   
   
}
