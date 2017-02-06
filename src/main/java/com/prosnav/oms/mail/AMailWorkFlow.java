package com.prosnav.oms.mail;

import java.util.List;
import java.util.Map;

import com.prosnav.oms.dao.mailDao;
import com.prosnav.oms.util.MailTimer;

public  abstract class AMailWorkFlow {
	
	public List<Map<String,Object>> GetNoReviceMailFormDb(String subjectId,String fromaddr)
	{
		return mailDao.smailMessage(subjectId,fromaddr);
     };
     
     public List<ReviceMailInfoBean> GetNoReviceMail()
     {return MailTimer.recive();}
     
     public boolean WriteDb(String subjectId,String content,String fromaddr)
     {
    	 
    	 int a = mailDao.UpdateFlag(subjectId,content,fromaddr); 
    	 if(a==1){
    	 return true;
    	 }else{
    		 return false;
    	 }

}
}
