package com.prosnav.oms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.util.jdbcUtil;

public class mailDao {

	 private static JdbcTemplate jt = (JdbcTemplate) jdbcUtil
	            .getBean("template");
	 /**
	  * 查询已发送邮件信息
	  * @param subjectId
	  * @return
	  * 

	  */
	 public static List<Map<String,Object>> smailMessage(String subjectId,String fromaddr){
		 String sql = "select mail_subjectid,mail_flag,mail_reciveperson,mail_subject,mail_firmid,"
		 		+ "mail_sendperson,mail_content"
		 		+ " from mail_info where mail_subjectId=? and mail_reciveperson=?";
		 return jt.queryForList(sql, subjectId,fromaddr);
	 }
	 /**
	  * 查询是否已经插入
	  * @param subjectId
	  * @param flag
	  * @return
	  */
	 public static List<Map<String,Object>> checkMail(String subjectId,String flag){
		 String sql="select mail_subjectid,mail_flag,mail_reciveperson,mail_subject,mail_firmid,"
		 		+ "mail_sendperson,mail_content from mail_info where mail_subjectId=? and mail_flag=?";
		 return jt.queryForList(sql,subjectId,flag);
	 }
	 /**
	  * 更改未发送为已发送
	  * @param subjectId
	  * @return
	  */
	 public static int updateFlag(String subjectId){
		 String sql ="update mail_info set mail_flag='0' where mail_subjectId=?";
		 return jt.update(sql,subjectId);
	 }
	 
	 /**
	  * 更改subjectId的标志为已接收
	  * @param subjectId
	  * @return
	  */
	 public static int UpdateFlag(String subjectId,String content,String fromaddr){
		 String sql="update mail_info set mail_flag = '2',mail_content=? where mail_subjectid=? and mail_reciveperson=?";
		 return jt.update(sql,content,subjectId,fromaddr);
	 }
	 /**
	  * 查询业务类型
	  * @param subjectId
	  * @return
	  */
	 public static List<Map<String, Object>> mail_select (String subjectId){
		 String sql="select mail_businessprocess from  mail_info  where mail_subjectid=? ";
		 return jt.queryForList(sql,subjectId);
	 }
	 
	 /**
	  * 添加发送记录
	  * @param subjectId
	  * @param flag
	  * @param recivePerson
	  * @param attachment
	  * @return
	  */
	 public static int addMessage
	 (SentMailInfoBean sentmail,String subjectId,String flag){
		 String recivePerson=sentmail.getReviceMailaddr();
		 String subject=sentmail.getSubject();
		 String firmId=sentmail.getFirmId();
		 String sendPerson=sentmail.getSentMailaddr();
		 String mail_busstype = sentmail.getMail_busstype();
		 String mail_businessprocess = sentmail.getMail_businessprocess();
		 String content=null;
		 String sql = "INSERT INTO public.mail_info(mail_subjectid, mail_flag, mail_reciveperson, mail_subject, mail_firmid, "
		 		+ "mail_sendperson, mail_content, mail_busstype, mail_businessprocess)"
		 		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		 int a = jt.update(sql,subjectId,flag,recivePerson,subject,
				 firmId,sendPerson,content,mail_busstype,mail_businessprocess);
		 return a;
	 }
	 /**
	  * 查询是否插入过此id
	  */
	 public static int select_subject_id(String subject_id){
		 String sql ="select count(*) from mail_info where mail_subjectid=?";
		 return jt.queryForInt(sql,subject_id);
	 }
	 /**
	  * 如果有过提交审批则更改邮件记录
	  * @param subjectId
	  * @param flag
	  */
	 public static void updateMessage(String subjectId,String flag){
		 String sql="update mail_info set mail_flag = ? where mail_subjectid =?";
		 jt.update(sql,flag,subjectId);
	 }
}
