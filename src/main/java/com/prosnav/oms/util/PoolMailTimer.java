package com.prosnav.oms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.prosnav.oms.dao.poolDao;
import com.prosnav.oms.mail.SentMailInfoBean;

public class PoolMailTimer extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		/**
		 * 查询所有的客户比较是否签单，有的话查询订单信息表最新一次的时间
		 * 没有的话，查询客户信息表判断录入时间与当前系统时间差  15 、18个月判断分别做不同任务
		 */
		poolDao pol = new poolDao();
		List<Map<String, Object>> signlist = pol.sign();
		List<Map<String, Object>> nosignlist =pol.Nosign();
		List<Map<String, Object>> cust_list = pol. desert_job();
		if(signlist.size()>0 && signlist != null){
			for(int i = 0;i<signlist.size();i++){
				String create_time =  signlist.get(i).get("c_t").toString();
				String custid =  signlist.get(i).get("id").toString();
				long cust_id = Long.parseLong(custid);
				String cust_name =  signlist.get(i).get("cust_name").toString();
				String cust_cell =  signlist.get(i).get("cust_cell").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date order_create = sdf.parse(create_time);
					Date date = new Date();
					long l = date.getTime()-order_create.getTime();//时间差
					double month = l/(1000*60*60*24);
					
					if(month<15){
						System.out.println("签单的还没到15个月");
					}else if(15<=month&&month<=18){
						System.out.println("给签单的发邮件");
						List<Map<String, Object>> salelist = pol.sale_mail(cust_id);
						if(salelist.size()>0&&salelist!=null){
								String email = (String) salelist.get(0).get("email");
								SentMailInfoBean sentmsg = new SentMailInfoBean();
								sentmsg.setSubjectId(""+cust_id);
								sentmsg.setSentMailaddr(mailCache.from);
								sentmsg.setReviceMailaddr(email);
								sentmsg.setMail_busstype("公共池");
								sentmsg.setMail_businessprocess("");
								sentmsg.setMailContent("客户未下单提醒：					\n"
										+ "您好，您在OMS系统创建的个人客户"+cust_name+"("+cust_cell+")已超过15个月未下单，当超过18个月还未下单将会被扔进。\n"
													
						);
								sentmsg.setSubject("给签单的发邮件");
								sendMail.sendMessage(sentmsg, true);
						}
						//发邮件
						
					}else {
						//扔进公共池
						pol.throwpool(cust_id);
						System.out.println("给签单的扔进公共池");
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
		}
		
		if(nosignlist.size()>0 && nosignlist != null){
			for(int i = 0;i<nosignlist.size();i++){
				String create_time =  nosignlist.get(i).get("cust_reg_time").toString();
				String custid =  nosignlist.get(i).get("id").toString();
				long cust_id = Long.parseLong(custid);
				String cust_name =  nosignlist.get(i).get("cust_name").toString();
				String cust_cell =  nosignlist.get(i).get("cust_cell").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				try {
					int result = 0;
					c1.setTime(sdf.parse(create_time));
					c2.setTime(sdf.parse(sdf.format(new Date())));						
					result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
					int month = result == 0?1:Math.abs(result);
					if(month<15){
						System.out.println("未签单的还没到15个月");
					}else if(15<=month&&month<=18){
						System.out.println("给未签单的发邮件");
						//发邮件
						List<Map<String, Object>> salelist = pol.sale_mail(cust_id);
						if(salelist.size()>0&&salelist!=null){
								String email = (String) salelist.get(0).get("email");
								SentMailInfoBean sentmsg = new SentMailInfoBean();
								sentmsg.setSubjectId(""+cust_id);
								sentmsg.setSentMailaddr(mailCache.from);
								sentmsg.setReviceMailaddr(email);
								sentmsg.setMail_busstype("公共池");
								sentmsg.setMail_businessprocess("");
								sentmsg.setMailContent("客户未下单提醒：					\n"
										+ "您好，您在OMS系统创建的个人客户"+cust_name+"("+cust_cell+")已超过15个月未下单，当超过18个月还未下单将会被扔进。\n"
													
						);
								sentmsg.setSubject("给未签单的发邮件");
								sendMail.sendMessage(sentmsg, true);
						}
					}else {
						//扔进公共池
						pol.throwpool(cust_id);
						System.out.println("给未签单的扔进公共池");
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			
		}
		
		//离职员工的客户放入公共池
		if(cust_list!=null&&cust_list.size()>0){
			for(int i = 0;i<cust_list.size();i++){
				String custid =cust_list.get(i).get("cust_id").toString();
						long cust_id = Long.parseLong(custid);
						pol.throwpool(cust_id);
			}
			
		}

	}

}
