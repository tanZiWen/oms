package com.prosnav.oms.mail.mailbusinessdw;

import com.prosnav.oms.dao.GpDao;
import com.prosnav.oms.mail.AMailBusinessDW;

public class mailgp extends AMailBusinessDW {

	public mailgp(String inbusinessId, String instatus) {
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
		GpDao gp = new GpDao();
		if("y".equalsIgnoreCase(status)){
			state="2";
			gp.gp_approve(email_id, state);
		}else{
			state="1";
			gp.gp_approve(email_id, state);
		}
		return false;
	}

}
