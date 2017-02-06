package com.prosnav.oms.exception;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Exception extends ActionSupport{
	 private Exception exception;
	  
	     public Exception getException() {
	         return exception;
	     }
	 
	     public void setException(Exception exception) {
	         this.exception = exception;
	     }
	     public String execute()
	     {
	         ActionContext.getContext().getValueStack().push(this.exception.getActionMessages());//放到值栈顶
	         return this.SUCCESS;
	     }

}
