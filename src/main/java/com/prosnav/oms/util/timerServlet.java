package com.prosnav.oms.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServlet;

import com.prosnav.oms.dao.OrderDao;

public class timerServlet extends HttpServlet {
	Timer timer = null;
	public void init(){
		
		timer = new Timer();
		 timer.schedule(new MailTimer(), 0, 5*60*1000); 
		 Timer sales = new Timer();
		 
		 
		 sales.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Date  date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
				String today = sdf.format(date);
				if("01-01".equals(today)||"04-01".equals(today)
						||"07-01".equals(today)||"10-01".equals(today)){
					//执行存储过程
					 //得到前3月的时间
					OrderDao ord = new OrderDao();
					ord.update_sales_para();
				}
			}
		}, 0,1000*60*60*24);
		
		 
	}
	public void destroy(){
		timer.cancel();
	}
}
