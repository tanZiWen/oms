package com.prosnav.oms.mail.mailbusinessdw;

import com.prosnav.oms.dao.ProdInfoDao;
import com.prosnav.oms.mail.AMailBusinessDW;
 

public class ProdMail extends AMailBusinessDW {

	public ProdMail(String inbusinessId, String instatus) {
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
			state="1";
		}
		ProdInfoDao pid = new ProdInfoDao();
		pid.prod_approve(email_id, state);
		
		
		return false;
	}}