package com.prosnav.oms.mail.mailbusinessdw;

import java.util.List;
import java.util.Map;

import com.prosnav.oms.dao.CustDao;
import com.prosnav.oms.dao.OrderDao;
import com.prosnav.oms.mail.AMailBusinessDW;



public class mailcust extends AMailBusinessDW {

	public mailcust(String inbusinessId, String instatus) {
		super(inbusinessId, instatus);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean Run() {
		// TODO Auto-generated method stub
		
		String id = this.getBid();
		String status = this.getStatus();//y或n
		String state ="";
		long cust_id = Long.parseLong(id);
		if("y".equalsIgnoreCase(status)){
			state="3";
		}else{
			state="4";
		}
		CustDao cust = new CustDao();
		cust.mail_check(cust_id, state);
		
		return false;
	}}