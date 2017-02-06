package com.prosnav.oms.mail;




public abstract  class AMailBusinessDW  
{
	String bid=null;
	String status=null;
	
	public String getBid() {
		return bid;
	}

	public String getStatus() {
		return status;
	}

	public AMailBusinessDW(String inbusinessId,String instatus)
	{
		bid=inbusinessId;
		status=instatus;
	}

	
	public boolean Execute()
	{
		return this.Run();
	}
	protected abstract boolean Run();
	
}