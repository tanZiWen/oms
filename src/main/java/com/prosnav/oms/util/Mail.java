package com.prosnav.oms.util;

import com.prosnav.oms.mail.SentMailInfoBean;

//import com.work.action.BaseAction;

public class Mail {

    public static void sentMail_order(String subjectId,String revice,String busstype,
    		String bussproce,String time,String sale_name ){
    	SentMailInfoBean sentmsg = new SentMailInfoBean();
		sentmsg.setFirmId("001");
		sentmsg.setSubjectId("0111");
		sentmsg.setSentMailaddr(mailCache.from);
		sentmsg.setReviceMailaddr("527928655@qq.com");
		sentmsg.setMail_busstype("新建订单审批");
		sentmsg.setMail_businessprocess("com.fm.mail.mailbusinessdw.mailorder");
		sentmsg.setMailContent("创建订单：					/n"
				+ "尊敬的（运营管理名）： 					/n"
				+ "您好，2014-07-10 00:13（时间）王芸（员工姓名）在OMS系统创建了新订单，现等待您审批。/n"
				+ "---InfoBegin---					/n"
				+ "【订单编号】：	2016062200005				/n"
				+ "【销售】：	张三				/n"
				+ "【首次缴款日期】：	2016年6月25日				/n"
				+ "【地区】：	上海				/n"
				+ "【客户姓名】：	王朋				/n"
				+ "【产品名称】：	元一公园道1号				/n"
				+ "【有限合伙企业】	前海帆茂有限合伙				/n"
				+ "【订单类型】：	直销订单				/n"
				+ "【下单金额】：	 10,000,000.00 				/n"
				+ "【指导/折后标费】：	 30,000,000.00 				/n"
				+ "---InfoEnd--- /n"					
);
		sentmsg.setSubject("新建订单审批");
		sendMail.sendMessage(sentmsg, true);
    }
  


}

