package com.prosnav.oms.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.GPInfoDao;
import com.prosnav.oms.dao.ProdInfoDao;
import com.prosnav.oms.dao.ReportDao;
import com.prosnav.oms.dao.loginDao;
import com.prosnav.oms.dao.userDao;
import com.prosnav.oms.mail.SentMailInfoBean;
import com.prosnav.oms.util.Download;
import com.prosnav.oms.util.jdbcUtil;
import com.prosnav.oms.util.mailCache;
import com.prosnav.oms.util.sendMail;
import com.prosnav.common.Constants;
public class ProdInfoAction {

	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
	List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> prodDict = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> prodStus = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> prodDictChannel = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> prodDictCurrency = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> mngFeeType = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> hkNo  = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> shkNo  = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> fundDict  = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> prodMana  = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> investDict  = new ArrayList<Map<String, Object>>();
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	ProdInfoDao pid = new ProdInfoDao();
	GPInfoDao gid = new GPInfoDao();
	JSONObject json = new JSONObject();
	
	/*User user = (User) req.getSession().getAttribute(Constants.USER);
	long role_id = user.getRole_id();
	long user_id = user.get_id();*/
	
	public String product_add(){
		List<Map<String, Object>> prodDict= pid.prodTypeDictInfo("1");
		req.setAttribute("prodDict", prodDict);
		
		List<Map<String, Object>> prodCurDict = pid.prodCurDictInfo();
		req.setAttribute("prodCurDict", prodCurDict);
		
		prodMana = pid.prodManaDictInfo();
		req.setAttribute("prodManaDict", prodMana);
		
		List<Map<String, Object>> gpName = pid.gpName();
		if(gpName!=null && gpName.size()>0){
			req.setAttribute("gpName", gpName);
		}else{
			req.setAttribute("nullInfo", "没有可用于创建产品的gp，还不能新建产品！");
		}
		
		
		return "product_add";
	}
	
	public String r_fee(){
		String pro_id = req.getParameter("prod_id");
		long prod_id = Long.parseLong(pro_id);

		/*List<Map<String,Object>>  list = pid.re_fee(prod_id);
		long count = (Long)list.get(0).get("count");
		if(count>0){*/
			
			List<Map<String,Object>> r_fee = pid.r_fee(prod_id);
			Object r_f = r_fee.get(0).get("raise_fee");
			
			if(r_f == null){
				r_f = 0;
			}
			
			if(r_fee!=null && r_fee.size()>0){
				req.setAttribute("r_fee",r_f);
			}
		
		
		return "r_fee";
	}
	
	public void raise_fee(){
		try {
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			String pro_id = req.getParameter("prod_id");
			long prod_id = Long.parseLong(pro_id);
			
			String r_fee = req.getParameter("raise_fee");
			Double raise_fee = Double.parseDouble(r_fee);
			
			
				//show value at jsp
			
				int result = pid.raise_fee(raise_fee, prod_id);
				if (result == 1){
					json.put("status", "1");
					json.put("msg", "新增成功");
				}else {
					json.put("status", "2");
					json.put("msg", "新增失败");
			}	
			
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String fee_manage(){
		/*String pro_id = req.getParameter("prod_id");
		long prod_id = Long.parseLong(pro_id);
		list1 = pid.LPList(prod_id);
		req.setAttribute("lpList", list1);
		return "fee_manage";*/
		
		mngFeeType = pid.mngFeeDictInfo();
		req.setAttribute("mngFeeType", mngFeeType);
		
		String pro_id = req.getParameter("prod_id");
		long prod_id = Long.parseLong(pro_id);
		list1 = pid.LPList(prod_id);
		req.setAttribute("lpList", list1);
		List<Map<String, Object>> list = pid.dateList(prod_id);
		if(list!=null&&list.size()>0){
			
			int count = 0;
			String s_date =  (String) list.get(0).get("s_raise_date");
			String e_date = (String) list.get(0).get("e_raise_date");
			Integer sdate = Integer.parseInt(s_date);
			Integer edate = Integer.parseInt(e_date);
			count = edate - sdate;
			req.setAttribute("count",count);
			req.setAttribute("status", "0");
		}else{
			req.setAttribute("status", "1");
			req.setAttribute("msg", "未找到相关数据");
		}
		return "fee_manage";
		
		
	}

	public void prodAddCheck(){
		try {
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			String prod_name = req.getParameter("prod_name");
			list = pid.checkProdName(prod_name);
			long count = (Long)list.get(0).get("count");
			if (count > 0) {
				json.put("list", list);
				json.put("status", "1");
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
			}
		resp.getWriter().print(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
		}
	}
	
	/**
	 * 产品信息全量查询
	 * 
	 * @return
	 */
	public String prodQuery() {
		String result="";
		User user = (User) req.getSession().getAttribute(Constants.USER);
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		//System.out.println("user========="+user.toString());
		req.setAttribute("role_id", role_id);
		//System.out.println("role_id============"+role_id);
		try {
			//list = pid.prodInfoSelectAll();
			if(role_id == 6){
				list = pid.prodInfo("", "", "", "", "", null,null,user_id,0,10);
			}else{
				list = pid.prodInfo("", "", "", "", "", null,null,null,0,10);
			}
			req.setAttribute("prodList", list);
			list1 = gid.GPInfo();
			req.getSession().setAttribute("GPlist", list1);
					
			List<Map<String, Object>> listcount;
			//分页
			if(role_id == 6){
				listcount = pid.prodInfo_count("", "", "", "", "", null,null,user_id);
			}else{
				listcount = pid.prodInfo_count("", "", "", "", "",null, null,null);
			}
			
		//	System.out.println(listcount.get(0).get("query_product_count_byUser"));
			req.setAttribute("totalNum", listcount.get(0).get("query_product_count_byUser"));
			
			prodDict= pid.prodTypeDictInfo("1"); //渠道
			req.setAttribute("prodDict", prodDict);
			
			prodStus= pid.prodTypeDictInfo("3"); //渠道
			req.setAttribute("prodStus", prodStus);
			
			result="prodQuery";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 按条件查询
	 * 
	 * @return
	 */
	public void prodSelect() {
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			String lp_name = req.getParameter("lp_name");
			String prod_name = req.getParameter("prod_name");
			String prod_type = req.getParameter("prod_type");
			String cor_org = req.getParameter("cor_org");
			String prod_status = req.getParameter("prod_status");
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
			String raise_date = req.getParameter("s_raise_date");
			String raise_date1 = req.getParameter("e_raise_date");

			int PageNum = Integer.parseInt(req.getParameter("PageNum"));
			int n = Integer.parseInt(req.getParameter("PageSize"));
			int m = (PageNum-1)*n;
			

			if(raise_date == ""){
				raise_date = null;
			}
			if(raise_date1 == ""){
				raise_date1 = null;
			}
			List<Map<String, Object>> listcount;
			if(role_id == 6){
				listcount = pid.prodInfo_count(prod_name, prod_type, prod_status,lp_name ,cor_org,raise_date,raise_date1,user_id);
			}else{
				listcount = pid.prodInfo_count(prod_name, prod_type, prod_status,lp_name ,cor_org,raise_date,raise_date1,null);
			}
			//req.setAttribute("totalNum1", listcount.get(0).get("query_product_count_byUser"));
		//	System.out.print(listcount);
			//Date s_raise_date =sdf.parse(raise_date);
			//list = pid.prodInfoSelect(prod_name, prod_type, prod_status, cor_org, s_raise_date);
			if(role_id == 6){
				list = pid.prodInfo(prod_name, prod_type, prod_status,lp_name ,cor_org,raise_date,raise_date1,user_id,m,n);
			}else{
				list = pid.prodInfo(prod_name, prod_type, prod_status,lp_name ,cor_org,raise_date,raise_date1,null,m,n);
			}
			//req.setAttribute("prodList", list);
			
			if (list != null && list.size() > 0) {
				json.put("list", list);
				json.put("status", "1");
				json.put("totalNum1", listcount.get(0).get("query_product_count_byUser"));
			} else {
				json.put("status", "0");
				json.put("list", "未找到相关数据");
			}
			//return "prodSelect";
		
		resp.getWriter().print(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
			//return "error";
		}
	}

	/**
	 * 产品新增
	 */
	public void prod_addInfo() {
		
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
		String g_id = req.getParameter("gp_id");
		//int gp_id = Integer.parseInt(g_id);
		long gp_id = Long.parseLong(g_id);
		String gp_name = req.getParameter("gp_name");
		String type_name = req.getParameter("type_name");
		String current_name = req.getParameter("current_name");
		String mana_name = req.getParameter("mana_name");
		String prod_name = req.getParameter("prod_name");
		String prod_type = req.getParameter("prod_type");
		String prod_currency = req.getParameter("prod_currency");
		String cor_org = req.getParameter("cor_org");
		String risk_level=req.getParameter("risk_level");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
		String raise_date = req.getParameter("s_raise_date");
		String raise_date2 = req.getParameter("e_raise_date");
			
			Date s_raise_date = sdf.parse(raise_date);
			Date e_raise_date = sdf.parse(raise_date2);
			String invest_goal = req.getParameter("invest_goal");
			String prod_mana = req.getParameter("prod_mana");
			long prod_id = jdbcUtil.seq();
			long mail_no = jdbcUtil.seq();
			
			long result = 0;
			result = ProdInfoDao.prod_add(prod_id,prod_name, prod_type, prod_currency, cor_org, s_raise_date,
					e_raise_date, invest_goal,prod_mana,risk_level,mail_no,user_id);
			
			json.put("list", result);
			System.out.println("result==="+result);
			if (result == 1) {// 1 代表成功 0代表失败
				//long max_id = pid.max_GPID();
				long email_id =  jdbcUtil.seq();
				long result2 = pid.gp_prod_rela(gp_id,prod_id,email_id);
				System.out.println("result2==="+result2);
				if (result2 == 1){
					json.put("status", "1");
					json.put("msg", "新增成功");
					
					String email="";
					String real_name = "";
					userDao udao = new userDao();
					List<Map<String, Object>> userlist =udao.getTeamUser(user.get_id());
					if(userlist!=null&&userlist.size()>0){
						email=(String) userlist.get(0).get("email");
						real_name =(String) userlist.get(0).get("real_name");
					}
					 	SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");
						SentMailInfoBean sentmsg = new SentMailInfoBean();
						//sentmsg.setFirmId("001");
						sentmsg.setSubjectId(""+mail_no);
						sentmsg.setCcAddress(user.getEmail());
						sentmsg.setSentMailaddr(mailCache.from);
						sentmsg.setReviceMailaddr(email);
						sentmsg.setMail_busstype("新建产品");
						sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.ProdMail");
						sentmsg.setMailContent("创建产品：					\n"
								+ "尊敬的"+real_name+"： 					\n"
								+ "您好，"+sdfg.format(new Date())+"，"+ user.getRealName()+"在OMS系统创建了新产品，现等待您审批。\n"
								+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
								+ "---InfoBegin---					\n"
								+ "【GP名称】：	"+gp_name+"				\n"
								+ "【产品名称】：	"+prod_name+"				\n"
								+ "【产品渠道】：	"+type_name+"				\n"
								+ "【合作机构】：	"+cor_org+"				\n"
								+ "【产品币种】：	"+current_name+"				\n"
								//+ "【客户姓名】：	"+gp_name+"				\n"
								+ "【投资目标】：	"+invest_goal+"				\n"
								+ "【产品风控等级】：	"+risk_level+"				\n"
								+ "【产品经理】：	"+mana_name+"				\n"
								+ "【开始募集时间】：	 "+s_raise_date+"				\n"
								+ "【结束募集时间】：	 "+e_raise_date+" 				\n"
								+ "---InfoEnd--- \n"						
				);
						sentmsg.setSubject("新建产品审批");
						sendMail.sendMessage(sentmsg, true); 
				}
				else {
					json.put("status", "2");
					json.put("msg", "新增失败");
				}			
				}else{
					json.put("status", "2");
					json.put("msg", "新增失败");
				}
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
				
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
		}
	}

	/**
	 * 根据ID查询产品
	 * @return
	 */
	public String production_detail() {
		
		    String result="";
			try {
				User user = (User) req.getSession().getAttribute(Constants.USER);
				long role_id = user.getRole_id();
				long user_id = user.get_id();
				req.setAttribute("role_id", role_id);
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("utf-8");
				String pro_id = req.getParameter("prod_id");
				//int prod_id = Integer.parseInt(pro_id);
				long prod_id = Long.parseLong(pro_id);
				
				list = pid.prodInfoSelectById(prod_id);
				//System.out.println("list:"+list);
				if (list != null && list.size() > 0) {
					req.setAttribute("prodList", list.get(0));
					prodDictChannel= pid.prodTypeDictInfo("1"); //渠道
					req.setAttribute("prodDictChannel", prodDictChannel);

					prodDictCurrency= pid.prodTypeDictInfo("2"); //币种
					req.setAttribute("prodDictCurrency", prodDictCurrency);
					
					prodMana = pid.prodManaDictInfo();
					req.setAttribute("prodManaDict", prodMana);
					
					List<Map<String,Object>> prodStatus =  pid.prodStatus(prod_id);
					req.setAttribute("prodStatus", prodStatus.get(0));
					
					
					if(role_id == 3|| role_id == 6 || role_id == 8 || role_id == 9){
						List<Map<String,Object>> prodMenuInfo = pid.prodMenuInfo(role_id);
						req.setAttribute("prodMenuInfo", prodMenuInfo);
						
					}
					
					if(role_id == 6){
						List<Map<String,Object>> prodMenuInfo1 = pid.prodMenuInfo1();
						req.setAttribute("prodMenuInfo1", prodMenuInfo1);
					}
					
					if(role_id == 7){
					List<Map<String,Object>> prodMenuInfo2 = pid.prodMenuInfo2();
					req.setAttribute("prodMenuInfo2", prodMenuInfo2);
					}
					result="production_detail";
				} else {
					req.setAttribute("prodList", "未找到相关数据");
					result="production_detail";
				}
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("msg", "数据库异常");
				result="error";
			}
			return result;
		} 



	public String product_demo() {
		String result="";
		try {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
			String pro_id = req.getParameter("prod_id");
			long prod_id = Long.parseLong(pro_id);
			req.setAttribute("role_id", role_id);
			
			//角色为大区经理，只显示对应的大区数据，角色为大boss可以看全量的数据，不用根据dept_no查询
			String dept_no = "1";
			String flag="2";  //1为大区经理   2为大boss   
			list = pid.GPList(prod_id);
			list1 = pid.LPList(prod_id);
			List<Map<String, Object>> list2 = pid.HKInfo(prod_id);
			List<Map<String, Object>> list3 = pid.sHKInfo(prod_id);
			List<Map<String, Object>> list4 = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list5 = pid.custDKInfo(prod_id);
			if(flag=="1"){
				list4 = pid.prod_gress(prod_id,dept_no);
			}else{
				list4 = pid.prod_gress1(prod_id);
			}
			//List<Map<String, Object>> list4 = pid.prod_gress(prod_id);
			if(list.size()>0 ){
				req.setAttribute("gpList", list);
			}else{
				req.setAttribute("gpList", "请先录入gp信息");
			}
			req.setAttribute("lpList", list1);
			req.setAttribute("HKlist", list2);
			req.setAttribute("sHKlist", list3);
			req.setAttribute("prodGress", list4);
			req.setAttribute("custDKInfo", list5);
			result= "product_demo";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("msg", "数据库异常");
			result= "error";
		}
		return result;
	}
	

	public String LP_add() {
		fundDict = pid.findTypeDictInfo();
		List<Map<String, Object>> arealist = pid.findareaDictInfo();
		req.setAttribute("fundDict", fundDict);
		req.setAttribute("arealist", arealist);
		investDict = pid.prodInvestInfo();
		req.setAttribute("investDict", investDict);
		return "LP_add";
	}

	public String add_ED() {
		List<Map<String, Object>> distDict = pid.distDictInfo();
		req.setAttribute("distDict", distDict);
		return "add_ED";
	}

	public String reg_JD() {
		User user = (User) req.getSession().getAttribute(Constants.USER);
		long role_id = user.getRole_id();
		long user_id = user.get_id();
		List<Map<String, Object>> distInfo = pid.dictInfoByUser(user_id);
		req.setAttribute("distInfo", distInfo.get(0));
		return "reg_JD";
	}

	public String sure_HK() {
		String result="";
		try {
			String pro_id = req.getParameter("prod_id");
			long prod_id = Long.parseLong(pro_id);
			/*String flag = req.getParameter("flaghk");
		 	if("1".equalsIgnoreCase(flag)){*/
			hkNo = pid.HKNoInfo(prod_id);
			if(hkNo.size()>0){
				req.setAttribute("hkNo", hkNo.get(0));
			}else{
				hkNo = pid.HKFNoInfo(prod_id);
				req.setAttribute("hkNo", hkNo.get(0));
			}
		 	/*}else if("2".equalsIgnoreCase(flag)){
		 		shkNo = pid.sHKNoInfo(prod_id);
				if(shkNo.size()>0){
					req.setAttribute("shkNo", shkNo.get(0));
				}else{
					shkNo = pid.sHKFNoInfo(prod_id);
					req.setAttribute("shkNo", shkNo.get(0));
				}
		 	}*/
			
			result= "sure_HK";
		} catch (Exception e) {
			e.printStackTrace();
			result="error";
		}
		return result;
	}

	
	public void sure_HK_add() {
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try{
		String pro_id = req.getParameter("prod_id");
		/*int prod_id = Integer.parseInt(pro_id);*/
		long prod_id = Long.parseLong(pro_id);
		String rtn_money = req.getParameter("return_money").replace(",", "");;
		String rtn_date = req.getParameter("return_date");
		Double return_money = Double.parseDouble(rtn_money);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date return_date = sdf.parse(rtn_date);
		String hkno = req.getParameter("hkno");
		long prod_rf_id = jdbcUtil.seq();
		
		int result = pid.sure_HK(prod_rf_id,prod_id, return_money, return_date,hkno);
		
		if (result==1) {
			json.put("status", "1");
			json.put("msg", "新增成功");
		}else{
			json.put("status", "2");
			json.put("msg", "新增失败");
		}
		resp.getWriter().print(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
	public String sure_sHK(){
		String result="";
		try {
			String pro_id = req.getParameter("prod_id");
			long prod_id = Long.parseLong(pro_id);
			//String flag = req.getParameter("flaghk");
		 	/*if("1".equalsIgnoreCase(flag)){
			hkNo = pid.HKNoInfo(prod_id);
			if(hkNo.size()>0){
				req.setAttribute("hkNo", hkNo.get(0));
			}else{
				hkNo = pid.HKFNoInfo(prod_id);
				req.setAttribute("hkNo", hkNo.get(0));
			}
		 	}else if("2".equalsIgnoreCase(flag)){*/
		 		shkNo = pid.sHKNoInfo(prod_id);
				if(shkNo.size()>0){
					req.setAttribute("shkNo", shkNo.get(0));
				}else{
					shkNo = pid.sHKFNoInfo(prod_id);
					req.setAttribute("shkNo", shkNo.get(0));
				}
		 	//}
			
			result= "sure_sHK";
		} catch (Exception e) {
			e.printStackTrace();
			result="error";
		}
		return result;
	}
	
	public void sure_sHK_add() {
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try{
		String pro_id = req.getParameter("prod_id");
		//int prod_id = Integer.parseInt(pro_id);
		long prod_id = Long.parseLong(pro_id);
		String rtn_money = req.getParameter("s_return_money").replace(",", "");
		String rtn_coe = req.getParameter("s_return_coe");
		String rtn_date = req.getParameter("s_return_date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date return_date = sdf.parse(rtn_date);
		Double return_money = Double.parseDouble(rtn_money);
		Double return_coe = Double.parseDouble(rtn_coe);
		long prod_rf_id = jdbcUtil.seq();
		
		String shkno = req.getParameter("shkno");
		
		int result = pid.sure_sHK(prod_rf_id,prod_id, return_money, return_coe,shkno, return_date);
		if (result==1) {
			json.put("status", "1");
			json.put("msg", "新增成功");
		}else{
			json.put("status", "2");
			json.put("msg", "新增失败");
		}
		resp.getWriter().print(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
	public String product() {
		return "product";
	}
	
	
	public String product_fabu() {
		String result = "";
		try{
		String pro_id = req.getParameter("prod_id");
		//int prod_id = Integer.parseInt(pro_id);
		long prod_id = Long.parseLong(pro_id);
		
		String hkno = req.getParameter("prod_rs_id");
		//long prod_rs_id = Long.parseLong(pro_rs_id);
		
		 List<Map<String,Object>> list = pid.prod_fb(prod_id,hkno,"1900-01-01","2040-01-01");
		 if (list.size() > 0) {
				req.setAttribute("status", "1");
				req.setAttribute("fblist", list);
			} else {
				req.setAttribute("status", "0");
				req.setAttribute("fblist", "未找到相关数据");
			}
		result= "product_fabu";
	} catch (Exception e) {
		e.printStackTrace();
		result="error";
	}
	return result; 
	}

	
	public void product_fabu_detail() {
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			String pro_id = req.getParameter("prod_id");
			//int prod_id = Integer.parseInt(pro_id);
			long prod_id = Long.parseLong(pro_id);
			
			String hkno = req.getParameter("prod_rs_id");
		//	long prod_rs_id = Long.parseLong(pro_rs_id);
			
			String start_date = req.getParameter("start_date");
			String end_date = req.getParameter("end_date");
			 List<Map<String,Object>> list = pid.prod_fb(prod_id,hkno,start_date,end_date);
			 if (list != null && list.size() > 0) {
					json.put("fabulist", list);
					json.put("status", "1");
				} else {
					json.put("status", "0");
					json.put("fabulist", "未找到相关数据");
				}
				//return "prodSelect";
			
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	
	}
	
	public String product_yifabu() {
		String result;
		try {
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			String pro_id = req.getParameter("prod_id");
			//int prod_id = Integer.parseInt(pro_id);
			long prod_id = Long.parseLong(pro_id);
			
			String hkno = req.getParameter("prod_rs_id");
			
			List<Map<String,Object>> list = pid.prod_yfb(prod_id, hkno);
			 if (list != null && list.size() > 0) {
				 req.setAttribute("yfabulist", list);
				 req.setAttribute("status", "1");
				} else {
					 req.setAttribute("status", "0");
					 req.setAttribute("yfabulist", "未找到相关数据");
				}
			 result= "product_yifabu";
		} catch (Exception e) {
			e.printStackTrace();
			result="error";
		}
		return result;
	}
	
	
	public void product_yfb() {
		ArrayList<Object[]> li=new ArrayList<Object[]>();
		JSONObject jsonList = new JSONObject();
		
			resp.setContentType("text;charset=utf-8"); 
			resp.setCharacterEncoding("UTF-8");
			try {
				String obj = req.getParameter("data");

				JSONObject json = JSONObject.parseObject(obj);
				List<Map<String, Object>> jsonArr = (List<Map<String, Object>>) json.get("data");
			//	System.out.println(jsonArr);
				
				long prod_id=0;
				long order_id=0;
				String shkno = "" ;
				
				for(int i=0;i<jsonArr.size();i++){
				     JSONObject jobj =  (JSONObject) jsonArr.get(i);
				     long prod_is_id = jdbcUtil.seq();
				    
				     shkno = req.getParameter("prod_rs_id");
					 //long prod_rs_id = Long.parseLong(pro_rs_id);
				    
				     Object orde_id = jobj.get("order_id");
				     order_id = Long.parseLong((String) orde_id);
				    /* if(order_id==""||order_id==null){
				    	 order_id="";
				     }*/
				     
				    /* Object pllo_money = jobj.get("pllot_money");
				     double pllot_money = Double.parseDouble((String) pllo_money);*/
				     
				     Object rtn_money = jobj.get("return_money");
				     double return_money = new BigDecimal((String) rtn_money).doubleValue();
				     
				     Object rtn_coe = jobj.get("return_coe");
				     double return_coe = Double.parseDouble((String) rtn_coe);
				     
				     Object return_date = jobj.get("return_date");

				     Object cust_name = jobj.get("cust_name");
				     if(cust_name==""||cust_name==null){
				    	 cust_name="";
				     }
				     
				     Object sal_id = jobj.get("sale_id");
				     long sale_id = Long.parseLong((String) sal_id);
				     
				     Object orde_version = jobj.get("order_version");
				     long order_version = Long.parseLong((String) orde_version);

				     String pro_id = req.getParameter("prod_id");
				     prod_id = Long.parseLong(pro_id);

 
					 Object[] temp={prod_is_id,shkno, order_id,cust_name,return_money,return_coe, return_date,prod_id,sale_id,order_version};
					 li.add(temp);
					

				}
				int []result=pid.prod_yfb(li);				
				if (result.length==0||result==null) {
					jsonList.put("status", "2");
					jsonList.put("msg", "发布失败");
				}else{
					jsonList.put("status", "1");
					jsonList.put("msg", "发布成功");
					pid.prod_flag(prod_id, shkno);
				}
				
				resp.getWriter().print(jsonList);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * 选择lp之后加入列表
	 */
	public void lp_fee_mana(){
		try {
			String pro_id = req.getParameter("prod_id");
			String lpid = req.getParameter("lp_id");
			if(!"".equals(lpid)&&lpid!=null){
				long lp_id = Long.parseLong(lpid);
				long prod_id = Long.parseLong(pro_id);
				List<Map<String, Object>> lp_feeList = pid.lp_feeList(prod_id, lp_id,"0");
				List<Map<String, Object>> ma_feeList = pid.lp_feeList(prod_id, lp_id,"1");
				if(lp_feeList!=null&&lp_feeList.size()>0){
					json.put("lp_feeList", lp_feeList);
					json.put("ma_feeList", ma_feeList);
					json.put("status", "2");
				}else{
					list1 = pid.LPList(prod_id);
					json.put("lpList", list1);
					List<Map<String, Object>> list = pid.dateList(prod_id);
					if(list!=null&&list.size()>0){
						int count = 0;
						String s_date =  (String) list.get(0).get("s_raise_date");
						String e_date = (String) list.get(0).get("e_raise_date");
						Integer sdate = Integer.parseInt(s_date);
						Integer edate = Integer.parseInt(e_date);
						count = edate - sdate;
						json.put("count",count);
						json.put("status", "0");
					}else{
						json.put("status", "1");
						json.put("msg", "未找到相关数据");
					}
				}
				
			}
			
			//json.put("msg", "新增成功");
			
			
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//添加费用管理
			public void fee_mana(){
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("utf-8");
				try {
					String obj = req.getParameter("json");
					JSONObject js = JSONObject.parseObject(obj);
					List<Map<String, Object>> list = (List<Map<String, Object>>) js.get("data");
					long lp_id = Long.parseLong(req.getParameter("lp_id"));
					long prod_id = Long.parseLong(req.getParameter("prod_id"));
					int result[] = pid.insert_fee_mana(list, lp_id, prod_id);
					if (result.length==0||result==null) {
						json.put("status", "1");
						json.put("msg", "新增成功");
					}else{
						json.put("status", "2");
						json.put("msg", "新增失败");
					}
					resp.getWriter().print(json);
					resp.getWriter().flush();
					resp.getWriter().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//更改fee
			public void save_fee_mana(){
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("utf-8");
				try {
					String obj = req.getParameter("json");
					JSONObject js = JSONObject.parseObject(obj);
					List<Map<String, Object>> list = (List<Map<String, Object>>) js.get("data");
					/*long lp_id = Long.parseLong(req.getParameter("lp_id"));
					long prod_id = Long.parseLong(req.getParameter("prod_id"));*/
					pid.save_fee_mana(list);
					//pid.insert_fee_mana(list, lp_id, prod_id);
					json.put("status", "1");
					json.put("msg", "更改成功");
					
					
					resp.getWriter().print(json);
					resp.getWriter().flush();
					resp.getWriter().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
	
	/**
	 * 编辑LP
	 * @return
	 */
	public String LP_edit(){
		String result="";
		try{
		
			String l_id =req.getParameter("lp_id");
			long lp_id = Long.parseLong(l_id);
			list = pid.LPInfo(lp_id);
			List<Map<String, Object>> arealist = pid.findareaDictInfo();
			fundDict = pid.findTypeDictInfo();
			req.setAttribute("fundDict", fundDict);
			req.setAttribute("arealist", arealist);
			investDict = pid.prodInvestInfo();
			req.setAttribute("investDict", investDict);
			
			if(list!=null && list.size()>0){
				req.setAttribute("LpList", list.get(0));
			}else{
				req.setAttribute("LpList", "未找到相关数据");
		}
		result= "LP_edit";
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		req.setAttribute("msg", "数据库异常");
		result= "error";
	}
	return result;
	}
	
	
	public void LPEdit(){
		resp.setContentType("text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		Map<String,Object> params=new HashMap<String,Object>();
		try {
		
		String l_id =req.getParameter("lp_id");
		//int lp_id=Integer.parseInt(l_id);
		long lp_id = Long.parseLong(l_id);
		//String GP_name =req.getParameter("gp_name");
		//String partner_com_name =req.getParameter("partner_com_name");
		String fund_type =req.getParameter("fund_type");
		//String prod_diffcoe =req.getParameter("prod_diffcoe");
		String bus_license =req.getParameter("bus_license");
		String legal_resp =req.getParameter("legal_resp");
		String fund_no =req.getParameter("fund_no");
		String pay_sum =req.getParameter("pay_sum");
		String pay_true =req.getParameter("pay_true");
		String open_bank =req.getParameter("open_bank");
		String bank_account =req.getParameter("bank_account");
		String open_bank1 =req.getParameter("open_bank1");
		String bank_account1 =req.getParameter("bank_account1");
		String regit_addr =req.getParameter("regit_addr");
		String prod_remark =req.getParameter("prod_remark");
		String fund_start_fee =req.getParameter("fund_start_fee");
		String fund_bnf_allot =req.getParameter("fund_bnf_allot");
		String cor_com_fee =req.getParameter("cor_com_fee");
		String fund_type_fee =req.getParameter("fund_type_fee");
		String true_pay =req.getParameter("true_pay");
		String gplp_pay =req.getParameter("gplp_pay");
		String first_delivery =req.getParameter("first_delivery");
		String renew_period =req.getParameter("renew_period");
		String invest_goal =req.getParameter("invest_goal");
		String renew_year =req.getParameter("renew_year");
		String invest_year =req.getParameter("invest_year");
		String invest_period =req.getParameter("invest_period");
		String mana_decr =req.getParameter("mana_decr");
		String buy_decr =req.getParameter("buy_decr");
		String delay_decr =req.getParameter("delay_decr");
		String late_join_decr =req.getParameter("late_join_decr");
		String invest_goal_decr =req.getParameter("invest_goal_decr");
		String see_sale =req.getParameter("see_sale");
		String area =req.getParameter("area");
		//params.put("GP_name",GP_name);
		//params.put("partner_com_name",partner_com_name);
		params.put("fund_type" ,fund_type );
		//params.put("prod_diffcoe",prod_diffcoe);
		params.put("renew_year" ,renew_year);
		params.put("invest_year" ,invest_year);
		params.put("bus_license" ,bus_license);
		params.put("legal_resp" ,legal_resp);
		params.put("fund_no",fund_no);
		params.put("pay_sum",pay_sum);
		params.put("pay_true" ,pay_true);
		params.put("open_bank" ,open_bank );
		params.put("bank_account" ,bank_account);
		params.put("open_bank1" ,open_bank1);
		params.put("bank_account1",bank_account1);
		params.put("regit_addr",regit_addr);
		params.put("prod_remark",prod_remark);
		params.put("fund_start_fee",fund_start_fee);
		params.put("fund_bnf_allot"  ,fund_bnf_allot);
		params.put("cor_com_fee",cor_com_fee);
		params.put("fund_type_fee",fund_type_fee);
		params.put("true_pay",true_pay);
		params.put("gplp_pay",gplp_pay);
		params.put("renew_period" ,renew_period );
		params.put("first_delivery",first_delivery);
		params.put("invest_goal",invest_goal);
		params.put("invest_period",invest_period);
		params.put("mana_decr",mana_decr);
		params.put("buy_decr",buy_decr);
		params.put("delay_decr",delay_decr);
		params.put("late_join_decr",late_join_decr);
		params.put("invest_goal_decr",invest_goal_decr);
		params.put("see_sale",see_sale);
		params.put("area",area);
		//params.put("prod_id",prod_id);
		//System.out.println(params);
		
		int result = pid.LP_info(params,lp_id);
		
		//PrintWriter out;
			
		if (result == 1) {// 1 代表成功 0代表失败
				json.put("status", "1");
				json.put("msg", "新增成功");
			}else {
			json.put("status", "2");
			json.put("msg", "新增失败");
			
		}
		resp.getWriter().print(json);
		resp.getWriter().flush();
		resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		
		public String more_ED(){
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("utf-8");
			
			String result="";
			try{
			String pro_id = req.getParameter("prod_id");
			//int prod_id = Integer.parseInt(pro_id);
			long prod_id = Long.parseLong(pro_id);
			
			String dept_no = req.getParameter("deptNo");
			List<Map<String, Object>> list4 = pid.prod_gress_detail(prod_id,dept_no);
			//List<Map<String, Object>> list4 = pid.prod_gress(prod_id);		
			if (list4.size() > 0) {
				
				req.setAttribute("status", "1");
				req.setAttribute("moreEdDetail", list4);
			} else {
				req.setAttribute("status", "0");
				req.setAttribute("moreEdDetail", "未找到相关数据");
			}
			
			result= "more_ED";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("msg", "数据库异常");
			result= "error";
		}
			
		return result;
			//return "more_ED";
		}
		
		public void save_prod(){
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
			try {
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("utf-8");
				String pro_id = req.getParameter("prod_id");
				
				long prod_id = Long.parseLong(pro_id);
				/*String prod_gp = req.getParameter("prod_gp");
				String prod_na = req.getParameter("prod_na");
				String type_name = req.getParameter("type_name");
				String org_name = req.getParameter("org_name");*/
				long mail_no = jdbcUtil.seq();
				//变更后数据
				String prod_currency = req.getParameter("cur_name");
				String prod_curName = req.getParameter("prod_curName");
				String prod_invest = req.getParameter("prod_invest");
				String prod_risk = req.getParameter("prod_risk");
				String s_raise_date = req.getParameter("sraise_date");
				String e_raise_date = req.getParameter("eraise_date");
				String prod_mana = req.getParameter("prod_mana");
				String prod_manaName = req.getParameter("prod_manaName");
				//变更前数据
				String curValue =req.getParameter("curValue");
				String origin_curName =req.getParameter("origin_curName");
				String prodManaValue =req.getParameter("prodManaValue");
				String origin_prodMana =req.getParameter("origin_prodMana");
				String orign_prod_risk =req.getParameter("orign_prod_risk");
				String orign_prod_invest =req.getParameter("orign_prod_invest");
				String orign_sraise_date =req.getParameter("orign_sraise_date");
				String orign_eraise_date =req.getParameter("orign_eraise_date");
				
				
				int result = pid.updateProd(prod_currency, prod_invest, prod_risk, s_raise_date, e_raise_date,prod_mana,mail_no, prod_id);
				if (result==1) {
					json.put("status", "1");
					json.put("msg", "保存成功");
					String email="";
					String real_name = "";
					userDao udao = new userDao();
					List<Map<String, Object>> userlist =udao.getTeamUser(user.get_id());
					if(userlist!=null&&userlist.size()>0){
						email=(String) userlist.get(0).get("email");
						System.out.println(email);
						real_name =(String) userlist.get(0).get("real_name");
					}
				 	SimpleDateFormat sdfg = new SimpleDateFormat("yyyy-MM-dd");
					SentMailInfoBean sentmsg = new SentMailInfoBean();
					//sentmsg.setFirmId("001");
					sentmsg.setCcAddress(user.getEmail());
					sentmsg.setSubjectId(""+mail_no);
					sentmsg.setSentMailaddr(mailCache.from);
					sentmsg.setReviceMailaddr(email);
					sentmsg.setMail_busstype("修改产品");
					sentmsg.setMail_businessprocess("com.prosnav.oms.mail.mailbusinessdw.ProdMail");
					sentmsg.setMailContent("修改产品：					\n"
							+ "尊敬的"+real_name+"： 					\n"
							+ "您好，"+sdfg.format(new Date())+"，"+ user.getRealName()+"在OMS系统修改了产品信息，现等待您审批。\n"
							+" 回复内容提示：回复“yes”予以通过，回复“no”予以拒绝\n"
							+ "---InfoBegin---					\n"
							+ "变更前信息：		变更后信息：		\n"
						//	+ "【合作机构】：	 "+current_name+" 	【合作机构】：	"+current_name+"				\n"
							+ "【产品币种】：	"+origin_curName+" 	【产品币种】：	"+prod_curName+"				\n"			
							+ "【投资目标】：	"+orign_prod_invest+" 	【投资目标】：	"+prod_invest+"				\n"
							+ "【产品风控等级】：	"+orign_prod_risk+" 	【产品风控等级】：	"+prod_risk+"				\n"
							+ "【产品经理】：	"+origin_prodMana+" 	【产品经理】：	"+prod_manaName+"				\n"
							+ "【开始募集时间】：	 "+orign_sraise_date+" 	【开始募集时间】：	"+s_raise_date+"				\n"
							+ "【结束募集时间】：	 "+orign_eraise_date+" 	【结束募集时间】：	"+e_raise_date+" 				\n"
							+ "---InfoEnd--- \n"						
			);
					sentmsg.setSubject("修改产品审批");
					sendMail.sendMessage(sentmsg, true); 

					
				//保存变更前数据	
					JSONObject json=new JSONObject();
					json.put("prod_currency", curValue);
					json.put("prod_invest", orign_prod_invest);
					json.put("prod_risk", orign_prod_risk);
					json.put("s_raise_date", orign_sraise_date);
					json.put("e_raise_date", orign_eraise_date);
					json.put("prod_mana", prodManaValue);
					
				 pid.saveProd(json, mail_no,prod_id);
						
					
				}else{
					json.put("status", "2");
					json.put("msg", "保存失败");
				}
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*
		 * 		public void save_prod(){
			try {
				String pro_id = req.getParameter("prod_id");
				long prod_id = Long.parseLong(pro_id);
				String prod_currency = req.getParameter("cur_name");
				String prod_invest = req.getParameter("prod_invest");
				String prod_risk = req.getParameter("prod_risk");
				String s_raise_date = req.getParameter("sraise_date");
				String e_raise_date = req.getParameter("eraise_date");
				int result = pid.updateProd(prod_currency, prod_invest, prod_risk, s_raise_date, e_raise_date, prod_id);
				if (result==1) {
					json.put("status", "1");
					json.put("msg", "新增成功");
				}else{
					json.put("status", "2");
					json.put("msg", "新增失败");
				}
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 * */
		
		//重置 
		public void product_reset(){
			String mail_n=req.getParameter("mail_no");
			long mail_no=0;
			if(mail_n!=null&&!mail_n.equals("")){
				mail_no=Long.parseLong(mail_n);
			}
			try{
				List<Map<String, Object>> list = pid.prod_reset(mail_no);
				if(list.size()>0&&list!=null){
					Object content = (Object) list.get(0).get("content");
					Object prodid=(Object)list.get(0).get("user_id");
					long prod_id=Long.parseLong(prodid.toString());
					JSONObject jsonpro = JSONObject.parseObject(content.toString());
					int j=pid.prod_res(jsonpro, prod_id);
					if(j>0){
					json.put("status", "1");
					}else{
						json.put("status", "2");
					}
				}else{
					json.put("status", "2");
				}
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void prod_close(){
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			long result=0;
			try {
				String pro_id = req.getParameter("prod_id");
				long prod_id = Long.parseLong(pro_id);
				
				 result = pid.closeProd(prod_id);
				 if (result==1) {
						json.put("status", "1");
						json.put("msg", "修改成功");
					}else{
						json.put("status", "2");
						json.put("msg", "修改失败");
					}
					
					resp.getWriter().print(json);
					resp.getWriter().flush();
					resp.getWriter().close();
			}catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
		public void prod_check(){
			
			long result=0;
			try {
				String flag = req.getParameter("flag");
				String pro_id = req.getParameter("prod_id");
				long prod_id = Long.parseLong(pro_id);
				//System.out.println( flag == "pass");
				if("pass".equalsIgnoreCase(flag)){
					 result = pid.checkProd1(prod_id);
					
				}else if( "reject".equalsIgnoreCase(flag)){
					 result = pid.checkProd(prod_id);
				}
				
				if (result==1) {
					json.put("status", "1");
					json.put("msg", "修改成功");
				}else{
					json.put("status", "2");
					json.put("msg", "修改失败");
				}
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
		public void glf(){
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			try {
				String g_id = req.getParameter("gp_id");
				long gp_id = Long.parseLong(g_id);
				
				
				List<Map<String,Object>> glf = pid.gp_dept(gp_id);
				if(glf.size()>0 && glf != null){
					json.put("status", "1");
					json.put("glf", glf.get(0));
				}	
					resp.getWriter().print(json);
					resp.getWriter().flush();
					resp.getWriter().close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 报表导出
		 */
		public void export_prod() {
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
			try {
				String[] o = { "产品名称", "渠道", "合作机构", "产品总认缴", "产品总实缴", "开始募集时间","币种","产品状态","审核状态"};
				
				String remoteFilePath ="/var/www/oms/report/prod.xls";
				// 输出Excel
				File fileWrite = new File(remoteFilePath);
				ProdInfoDao pid = new ProdInfoDao();
				log.info("创建excel");
				if(!fileWrite.exists())
				fileWrite.createNewFile();
				OutputStream os = new FileOutputStream(fileWrite);
				
				SqlRowSet rs;
				if(role_id == 6){
					rs = pid.export_report("", "", "", "", "", null,null,user_id,0,10);
				}else{
					rs = pid.export_report("", "", "", "", "", null,null,null,0,10);
				}
				
				Download.writeExcel(os, rs, o);
				Download.downloadFile(remoteFilePath, resp);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 财务总监审核产品发布
		 */
		public void product_fb(){
			
		}
		
		/**
		 * 产品邮件重置
		 */
	/*	public void product_reset(){
			
		}*/
		
		public String prod_diffcoe(){
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
			int totalNum = 0;
			
			req.setAttribute("totalNum", totalNum);
			return "success";
		}
	/**
	 * 初始查询已经分页
	 */
	public void query_diffcoe() {
		try {
			JSONObject json = new JSONObject();
			String area ="";
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
			int totalNum = 0;
			int PageNum = Integer.parseInt(req.getParameter("PageNum"));
			int n = Integer.parseInt(req.getParameter("PageSize"));
			int m = (PageNum - 1) * n;
			List<Map<String, Object>> userlist = pid.arealist(user_id);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if(userlist!=null&&userlist.size()>0){
				area = (String) userlist.get(0).get("area");
				if (role_id == 1 || role_id == 2 || role_id == 3) {
					list = pid.sale_query_diffcoe(n, m,area);
					totalNum = pid.sale_query_diffcoe_count(area);
				} else if (role_id == 6 || role_id == 7 ||role_id==4||role_id==5||role_id==8||role_id==9) {
					list = pid.query_diffcoe_all(n, m);
					totalNum = pid.query_diffcoe_all_count();
				} 
			}else{
				json.put("status", "2");
			}
			json.put("list", list);
			json.put("totalNum", totalNum);
			json.put("PageNum", PageNum);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据产品名称查询
	 */
	public void select_diffcoe() {
		try {
			JSONObject json = new JSONObject();
			String area ="";
			User user = (User) req.getSession().getAttribute(Constants.USER);
			long role_id = user.getRole_id();
			long user_id = user.get_id();
			int totalNum = 0;
			int PageNum = Integer.parseInt(req.getParameter("PageNum"));
			int n = Integer.parseInt(req.getParameter("PageSize"));
			int m = (PageNum - 1) * n;
			String prod_name = req.getParameter("prod_name");
			List<Map<String, Object>> userlist = pid.arealist(user_id);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if(userlist!=null&&userlist.size()>0){
				area = (String) list.get(0).get("area");
				if (role_id == 1 || role_id == 2 || role_id == 3) {
					list = pid.sale_select_diffcoe(n, m,prod_name,area);
					totalNum = pid.sale_select_diffcoe_count(prod_name,area);
				}  else if (role_id == 6||role_id == 7 ||role_id==5||role_id==4) {
					list = pid.select_diffcoe_all(n, m,prod_name);
					totalNum = pid.select_diffcoe_all_count(prod_name);
				}
			}else{
				json.put("status", "2");
			}
			json.put("list", list);
			json.put("totalNum", totalNum);
			json.put("PageNum", PageNum);
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void edit_return() {
		try {
			JSONObject json = new JSONObject();
			String prod_rid  = req.getParameter("prod_r_id");
			long prod_r_id = Long.parseLong(prod_rid);
			List<Map<String, Object>> list = pid.edit_return(prod_r_id);
			if(list.size()>0){
				json.put("list", list.get(0));
				json.put("status", "1");
			}else{
				json.put("status", "2");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void submit_return() {
		try {
			JSONObject json = new JSONObject();
			String prod_rid  = req.getParameter("prod_r_id");
			long prod_r_id = Long.parseLong(prod_rid);
			String returnmoney = req.getParameter("return_money").replaceAll(",", "");
			double return_money = Double.parseDouble(returnmoney);
			String return_date = req.getParameter("return_date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(return_date);
			pid.edit_return(prod_r_id,return_money,date);
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void approve_return() {
		try {
			JSONObject json = new JSONObject();
			String prod_rid  = req.getParameter("prod_r_id");
			long prod_r_id = Long.parseLong(prod_rid);
			String finahk_flag = req.getParameter("finahk_flag");
			pid.approve_return(prod_r_id,finahk_flag);
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void item_edit_return() {
		try {
			JSONObject json = new JSONObject();
			String prod_rid  = req.getParameter("prod_rs_id");
			long prod_rs_id = Long.parseLong(prod_rid);
			List<Map<String, Object>> list = pid.item_edit_return(prod_rs_id);
			if(list.size()>0){
				json.put("list", list.get(0));
				json.put("status", "1");
			}else{
				json.put("status", "2");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void item_submit_return() {
		try {
			JSONObject json = new JSONObject();
			String prod_rid  = req.getParameter("prod_rs_id");
			long prod_rs_id = Long.parseLong(prod_rid);
			String returnmoney = req.getParameter("item_return_money").replaceAll(",", "");
			double return_money = Double.parseDouble(returnmoney);
			String return_date = req.getParameter("item_return_date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(return_date);
			String returncoe = req.getParameter("item_return_coe");
			double return_coe = Double.parseDouble(returncoe);
			pid.item_edit_return(prod_rs_id,return_money,date,return_coe);
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void item_approve_return() {
		try {
			JSONObject json = new JSONObject();
			String prod_rid  = req.getParameter("prod_rs_id");
			long prod_rs_id = Long.parseLong(prod_rid);
			String prod_flag = req.getParameter("prod_flag");
			pid.item_approve_return(prod_rs_id,prod_flag);
			json.put("status", "1");
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
