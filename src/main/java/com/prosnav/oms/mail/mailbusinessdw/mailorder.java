package com.prosnav.oms.mail.mailbusinessdw;

import java.util.List;
import java.util.Map;

import com.prosnav.oms.dao.OrderDao;
import com.prosnav.oms.mail.AMailBusinessDW;



public class mailorder extends AMailBusinessDW {

	public mailorder(String inbusinessId, String instatus) {
		super(inbusinessId, instatus);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean Run() {
		// TODO Auto-generated method stub
		
		String id = this.getBid();
		String status = this.getStatus();//y或n
		String state ="";
		long email_id = Long.parseLong(id);
		if("y".equalsIgnoreCase(status)){
			state="2";
		}else{
			state="3";
		}
		OrderDao ord = new OrderDao();
		ord.mail_approve(email_id, state);
		
		return false;
	}}