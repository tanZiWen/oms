package com.prosnav.oms.mail.mailbusinessdw;

import com.prosnav.oms.dao.OrgDao;
import com.prosnav.oms.mail.AMailBusinessDW;

public class mailorg extends AMailBusinessDW{

	public mailorg(String inbusinessId, String instatus) {
		super(inbusinessId, instatus);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean Run() {
		// TODO Auto-generated method stub
		String id = this.getBid();
		String status = this.getStatus();//y或n
		long email_id = Long.parseLong(id);
		OrgDao org = new OrgDao();
		if("y".equalsIgnoreCase(status)){
			org.org_approve(email_id, "3");
		}else{
			org.org_approve(email_id, "4");
			
		}
		
		return false;
	}

}
