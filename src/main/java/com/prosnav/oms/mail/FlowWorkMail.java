package com.prosnav.oms.mail;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import com.prosnav.oms.dao.mailDao;



public class FlowWorkMail extends AMailWorkFlow implements IMailWorkFlow{

	public boolean WorkFlowOP() {
		try{
		//System.out.println("开启邮件扫描");
		List<ReviceMailInfoBean> noReviceMail=GetNoReviceMail();
		for(int n=0;n<noReviceMail.size();n++)
		{
			//System.out.println("读取未读邮件");
			if("1".equals(noReviceMail.get(n).getStatus())){
				System.out.println("5分鐘之後連接");
			}else{
			String content = noReviceMail.get(n).getContent();
			//System.out.println("获取邮件内容"+content);
  			 content.replace("\n", "");
  			 content.replace("\\s", "");
  			 content = content.substring(0,1);
  			 //System.out.println("content==="+content);
			String fromaddr = noReviceMail.get(n).getFromaddr();
			String subjectId = noReviceMail.get(n).getSubjectNum();
			subjectId = subjectId.split("-")[1];
			System.out.println(subjectId);
			//System.out.println("查询已发送邮件");
			List<Map<String,Object>> nomailformdb=this.GetNoReviceMailFormDb(subjectId,fromaddr);
			if(nomailformdb!=null&&nomailformdb.size()>0){
				//System.out.println("比较判断执行数据库操作");
				if("y".equalsIgnoreCase(content)||"n".equalsIgnoreCase(content)){
					this.WriteDb( subjectId,content,fromaddr);
				}else{
					this.WriteDb( subjectId,"N",fromaddr);
				}
				List<Map<String, Object>> list = mailDao.mail_select(subjectId);
				String typeclass="";
				System.out.println("查询打印邮件表中的类型的size"+list.size());
				if(list.size()>0)
				 typeclass = (String) list.get(0).get("mail_businessprocess");
				Class cls = Class.forName(typeclass);
				Class[] paramTypes = { String.class, String.class};
				Object[] params = {subjectId, content}; // 方法传入的参数

				Constructor con = cls.getConstructor(paramTypes);     //主要就是这句了
				AMailBusinessDW bdw = (AMailBusinessDW) con.newInstance(params);  //BatcherBase 为自定义类
				bdw.Execute();
				
				
				System.out.println(subjectId);
				
			}
			}
			
			//this.WriteDb(String subjectid,String content);
		}
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
  

}
