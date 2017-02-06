package com.prosnav.oms.action;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.RewardDao;
import com.prosnav.oms.util.jdbcUtil;



public class RewardAction {
	HttpServletResponse resp = ServletActionContext.getResponse();
	HttpServletRequest req = ServletActionContext.getRequest();
	JSONObject json = new JSONObject();
	Map<String, Object> map = new HashMap<String, Object>();
	HttpSession session = req.getSession();		
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	

	// 销售 -已计算奖金和预留发放，团队奖金列表
	public String selectReward() {
		// 获取登录用户id
		
		/*  User user = (User) session.getAttribute(Constants.USER); loginDao
		 login = new loginDao(); long role_id = login.role(user.get_id());
		 */
		User user = (User) session.getAttribute(Constants.USER);
		if(user==null){
			return "index";
		}
		long role_id = user.getRole_id();
		long ueser_id=user.get_id();
		/*System.out.println("销售登录ID"+ueser_id);*/
		req.setAttribute("role_id", role_id);
       /* System.out.println(ueser_id);*/
		String result = "";
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		/*
		 * List<Map<String, Object>> rewardLi = new ArrayList<Map<String,
		 * Object>>();
		 */
		List<Map<String, Object>> reserveLi = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reward_group = new ArrayList<Map<String, Object>>();
		List<Map<String,Object>> rewardcount= new ArrayList<Map<String, Object>>();
		List<Map<String,Object>> reservecount= new ArrayList<Map<String, Object>>();
		List<Map<String,Object>> groupcount= new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			//个人奖金
			rewardList = od.rewardList(ueser_id);
			rewardcount=od.search_reward_batch(ueser_id,0);
			/* rewardLi = od.reward_count(); */
			//预留发放
			reserveLi = od.reserve_sales_search(ueser_id);
			reservecount=od.search_yuliu_batch(ueser_id,0);
			//团队奖金
			reward_group = od.reward_group_search(ueser_id);
			groupcount=od.search_group_batch(ueser_id,0);
			// 奖金可发放列表
			if (rewardList != null && rewardList.size() > 0) {
				req.setAttribute("status", "1");
				req.setAttribute("rewardList", rewardList);
				

			} else {
				req.setAttribute("status", "2");
				req.setAttribute("reward_list", "未找到相关数据");
			}
			if(rewardcount!=null&&rewardcount.size()>0){
				req.setAttribute("rewardbatch", 1);
				req.setAttribute("rewardcount",rewardcount);
			}else{
				req.setAttribute("rewardbatch", 2);
			}
			// 预留可发放列表
			if (reserveLi != null && reserveLi.size() > 0) {
				req.setAttribute("status1", "1");
				req.setAttribute("reserveLi", reserveLi);
				
			} else {
				req.setAttribute("status1", "2");
				req.setAttribute("reserve_list", "未找到相关数据");
			}
			if(reservecount!=null&&reservecount.size()>0){
				req.setAttribute("reservebatch", 1);
				req.setAttribute("reservecount",reservecount);
			}else{
				req.setAttribute("reservebatch", 2);
			}
			// 团队奖金可发放列表
			if (reward_group != null && reward_group.size() > 0) {
				req.setAttribute("status2", "1");
				req.setAttribute("reward_group", reward_group);
				
			} else {
				req.setAttribute("status2", "2");
				req.setAttribute("group_list", "未找到相关数据");
			}
			if(groupcount!=null && groupcount.size()>0){
				req.setAttribute("groupbatch", 1);
				req.setAttribute("groupcount",groupcount);
			}else{
				req.setAttribute("groupbatch", 2);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;
	}
	//进入团队计算页面
	public String tuandui(){
		List<Map<String,Object>> groupcount=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> groupyear=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> groupList=new ArrayList<Map<String,Object>>();
		RewardDao rd = new RewardDao();
		try{
			groupList=rd.group_count_select();
			groupyear=rd.search_group_year();
		groupcount=rd.search_group_batch_all(0);
		
		if(groupList!=null&&groupList.size()>0){
			req.setAttribute("group", 1);
			req.setAttribute("groupList", groupList);
		}else{
			req.setAttribute("group", 2);
			req.setAttribute("groupList", "未查询到计算信息");
		}
		
		if(groupyear!=null&&groupyear.size()>0){
			req.setAttribute("year", 1);
			req.setAttribute("groupyear", groupyear);
		}else{
			req.setAttribute("year",2);
		}
		if(groupcount!=null&&!groupcount.equals("")){
			req.setAttribute("batch", 1);
			req.setAttribute("groupcount",groupcount);
		}else{
			req.setAttribute("batch",2);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 销售---- 多条件查询奖金
	public void rewardSearch() {
		List<Map<String, Object>> reward_list = null;
		RewardDao od = new RewardDao();
		// 获取登录用户id
				/*
				 * User user = (User) session.getAttribute(Constants.USER); loginDao
				 * login = new loginDao(); long role_id = login.role(user.get_id());
				 */
		
		User user = (User) session.getAttribute(Constants.USER);
/*		if(user==null){
			   json.put("status", "3");
		        try{
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
		        } catch (Exception e) {
					e.printStackTrace();
				}
		}*/
		long role_id = user.getRole_id();
		long ueser_id=user.get_id();
		req.setAttribute("role_id", role_id);

		try {
			Object obj = req.getParameter("order_no").equals("") ? null : Long
					.parseLong(req.getParameter("order_no"));
			Object count_batch = req.getParameter("count_batch").equals("") ? null
					: Long.parseLong(req.getParameter("count_batch"));
			Object cust_cell = req.getParameter("cust_cell").equals("") ? null
					: Long.parseLong(req.getParameter("cust_cell"));
			Map<String, Object> sqlparam = new HashMap<String, Object>();
			sqlparam.put("cust_type", req.getParameter("cust_type"));
			sqlparam.put("cust_name", req.getParameter("cust_name"));
			sqlparam.put("order_no", obj);
			sqlparam.put("cust_cell", cust_cell);
			sqlparam.put("prod_type", req.getParameter("prod_type"));
			sqlparam.put("count_batch", count_batch);

			if (sqlparam.get("cust_type").equals(null)
					|| sqlparam.get("cust_type").equals("")) {
				req.setAttribute("code", "err001");
				req.setAttribute("msg", "数据类型错误");
			} else {
				reward_list = od.CustSelByParam(sqlparam,ueser_id);
			}

			if (reward_list != null && reward_list.size() > 0) {
				json.put("reward_list", reward_list);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("reward_list", "未找到相关数据");
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
	//个人--批次分页查询
	public void rewardbatch() {
		String siz = req.getParameter("size");
		String sign=req.getParameter("sign");
		int size=Integer.parseInt(siz);
		int minsize=0;
		int maxsize=3;
		if(sign=="1"||sign.equals("1")){
		minsize=size;
		maxsize=size+3;
		}
		if(sign=="-1"||sign.equals("-1")){
			if(size!=0){
				
				maxsize=size;
				size=size-3;
				minsize=size;
			}
		}
		List<Map<String,Object>> reservecount=new ArrayList<Map<String,Object>>();
		User user =(User) session.getAttribute(Constants.USER);
				if(user==null){
		        json.put("status", "3");
		        try{
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
		        } catch (Exception e) {
					e.printStackTrace();
				}
				}
				long role_id = user.getRole_id();
				long ueser_id=user.get_id();
				req.setAttribute("role_id", role_id);
		try {
			RewardDao rd = new RewardDao();
             if(role_id==1||role_id==2){
            	 reservecount = rd.search_reward_batch(ueser_id, size);
             }else{
            	 reservecount=rd.search_reward_batch_all(size);
             }
			
			if (reservecount!=null&&reservecount.size()>0) {
				json.put("status", "1");
				json.put("reservecount", reservecount);
				json.put("minsize", minsize);
				json.put("maxsize", maxsize);
			} else {
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
	//预留--批次分页查询
	public void reservebatch() {
		String siz = req.getParameter("size");
		String sign=req.getParameter("sign");
		int size=Integer.parseInt(siz);
		int minsize=0;
		int maxsize=3;
		if(sign=="1"||sign.equals("1")){
		minsize=size;
		maxsize=size+3;
		}
		if(sign=="-1"||sign.equals("-1")){
			if(size!=0){
				maxsize=size;
				size=size-3;
				minsize=size;
				
			}
		}
		List<Map<String,Object>> reservecount=new ArrayList<Map<String,Object>>();
		User user = (User) session.getAttribute(Constants.USER);
		/*		if(user==null){
				  json.put("status", "3");
		        try{
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
		        } catch (Exception e) {
					e.printStackTrace();
				}
				}*/
				long role_id = user.getRole_id();
				long ueser_id=user.get_id();
				req.setAttribute("role_id", role_id);
		try {
			RewardDao rd = new RewardDao();
             if(role_id==1||role_id==2){
            	 reservecount = rd.search_yuliu_batch(ueser_id, size);
             }else{
            	 reservecount=rd.search_yuliu_batch_all(size);
             }
			
			if (reservecount!=null&&reservecount.size()>0) {
				json.put("status", "1");
				json.put("reservecount", reservecount);
				json.put("minsize", minsize);
				json.put("maxsize", maxsize);
			} else {
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
	//团队奖金--批次分页查询
	public void groupbatch() {
		String siz = req.getParameter("size");
		String sign=req.getParameter("sign");
		int size=Integer.parseInt(siz);
		int minsize=0;
		int maxsize=3;
		if(sign=="1"||sign.equals("1")){
		minsize=size;
		maxsize=size+3;
		}
		if(sign=="-1"||sign.equals("-1")){
			if(size!=0){
				
				maxsize=size;
				size=size-3;
				minsize=size;
			}
		}
		List<Map<String,Object>> reservecount=new ArrayList<Map<String,Object>>();
		User user = (User) session.getAttribute(Constants.USER);
		/*		if(user==null){
				  json.put("status", "3");
		        try{
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
		        } catch (Exception e) {
					e.printStackTrace();
				}
				}*/
				long role_id = user.getRole_id();
				long ueser_id=user.get_id();
				req.setAttribute("role_id", role_id);
		try {
			RewardDao rd = new RewardDao();
             if(role_id==1||role_id==2){
            	 reservecount = rd.search_group_batch(ueser_id, size);
             }else{
            	 reservecount=rd.search_group_batch_all(size);
             }
			
			if (reservecount!=null&&reservecount.size()>0) {
				json.put("status", "1");
				json.put("reservecount", reservecount);
				json.put("minsize", minsize);
				json.put("maxsize", maxsize);
			} else {
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
	
	
	
	// 个人--奖金历史记录查询
	public String search_reward_list() {
		List<Map<String, Object>> reward_list = new ArrayList<Map<String, Object>>();
		String result = "";
		RewardDao od = new RewardDao();
		// 获取登录用户id
				/*
				 * User user = (User) session.getAttribute(Constants.USER); loginDao
				 * login = new loginDao(); long role_id = login.role(user.get_id());
				 */
		User user = (User) session.getAttribute(Constants.USER);
		if(user==null){
			return "index";
		}
		long role_id = user.getRole_id();
		long ueser_id=user.get_id();
		req.setAttribute("role_id", role_id);
		
		int PageNum = 1;
		if(req.getParameter("PageNum") != null) {
			PageNum = Integer.parseInt(req.getParameter("PageNum"));
		}
		int PageSize = 10;
		if(req.getParameter("PageSize") != null) {
			PageSize = Integer.parseInt(req.getParameter("PageSize"));
		}
		int SkipSize = (PageNum - 1) * PageSize;
		int totalNum = 0;
		try {
			if(role_id==1|| role_id==2){
				reward_list = od.search_reward_list(ueser_id);
			}else{
				reward_list = od.search_reward_list_all(SkipSize, PageSize, null, null, null);
				totalNum = od.search_reward_all_count().size();
			}
			if (reward_list != null && reward_list.size() > 0) {
				req.setAttribute("status", "1");
				req.setAttribute("totalNum", totalNum);
				req.setAttribute("PageNum", PageNum);
				req.setAttribute("rewardList", reward_list);

			} else {
				req.setAttribute("status", "2");
				req.setAttribute("reward_list", "未找到相关数据");
			}
			result = "success";
		} catch (Exception e) {
			result = "error";
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
		}
		return result;
	}
	
	public void reward_list() {
		List<Map<String, Object>> reward_list = new ArrayList<Map<String, Object>>();
		String result = "";
		RewardDao od = new RewardDao();
		// 获取登录用户id
				/*
				 * User user = (User) session.getAttribute(Constants.USER); loginDao
				 * login = new loginDao(); long role_id = login.role(user.get_id());
				 */
		User user = (User) session.getAttribute(Constants.USER);
	
		long role_id = user.getRole_id();
		long ueser_id=user.get_id();
		req.setAttribute("role_id", role_id);
		int PageNum = 1;
		if(req.getParameter("PageNum") != null) {
			PageNum = Integer.parseInt(req.getParameter("PageNum"));
		}
		int PageSize = 10;
		if(req.getParameter("PageSize") != null) {
			PageSize = Integer.parseInt(req.getParameter("PageSize"));
		}
		int SkipSize = (PageNum - 1) * PageSize;
		int totalNum = 0;
		try {
			req.setCharacterEncoding("UTF-8");
			String area = req.getParameter("area");
			String product = req.getParameter("product");
			String sales = req.getParameter("rm");
			if(role_id==1|| role_id==2){
				reward_list = od.search_reward_list(ueser_id);
			}else{
				reward_list = od.search_reward_list_all(SkipSize, PageSize, area, product, sales);
				totalNum = od.search_reward_all_count().size();
			}
			if (reward_list != null && reward_list.size() > 0) {
				json.put("status", "1");
				json.put("totalNum", totalNum);
				json.put("PageNum", PageNum);
				json.put("list", reward_list);

			} else {
				json.put("status", "2");
				json.put("reward_list", "未找到相关数据");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();;
		} catch (Exception e) {
			result = "error";
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
		}
	}
	// 团队奖金历史记录查询
		public String search_group_list() {
			List<Map<String, Object>> reward_list = new ArrayList<Map<String, Object>>();
			String result = "";
			RewardDao od = new RewardDao();
			String countbatch = req.getParameter("count_batch");
			long count_batch = Long.parseLong(countbatch);
			// 获取登录用户id
					/*
					 * User user = (User) session.getAttribute(Constants.USER); loginDao
					 * login = new loginDao(); long role_id = login.role(user.get_id());
					 */
			User user = (User) session.getAttribute(Constants.USER);
			if(user==null){
				return "index";
			}
			long role_id = user.getRole_id();
			long ueser_id=user.get_id();
			req.setAttribute("role_id", role_id);

			try {
				if(role_id==2){
				reward_list = od.search_group_list(ueser_id,count_batch);
				}else{
					reward_list = od.search_group_list_all(count_batch);
				}
				if (reward_list != null && reward_list.size() > 0) {
					req.setAttribute("status", "1");
					req.setAttribute("rewardList", reward_list);

				} else {
					req.setAttribute("status", "2");
					/*req.setAttribute("reward_list", "未找到相关数据");*/
				}
				result = "success";
			} catch (Exception e) {
				result = "error";
				e.printStackTrace();
				req.setAttribute("msg", "系统异常");
			}
			return result;
		}
		
		// 预留--历史记录查询
		public String search_reserve_list() {
			List<Map<String, Object>> reward_list = new ArrayList<Map<String, Object>>();
			String result = "";
			RewardDao od = new RewardDao();
			String countbatch = req.getParameter("count_batch");
			long count_batch = Long.parseLong(countbatch);
			// 获取登录用户id
					/*
					 * User user = (User) session.getAttribute(Constants.USER); loginDao
					 * login = new loginDao(); long role_id = login.role(user.get_id());
					 */
			User user = (User) session.getAttribute(Constants.USER);
			if(user==null){
				return "index";
			}
			long role_id = user.getRole_id();
			long ueser_id=user.get_id();
			req.setAttribute("role_id", role_id);

			try {
				if(role_id==1|| role_id==2){
				reward_list = od.search_yuliu_list(ueser_id,count_batch);
				}else{
					reward_list = od.search_yuliu_list_all(count_batch);
				}
				if (reward_list != null && reward_list.size() > 0) {
					req.setAttribute("status", "1");
					req.setAttribute("rewardList", reward_list);

				} else {
					req.setAttribute("status", "2");
					req.setAttribute("reward_list", "未找到相关数据");
				}
				result = "success";
			} catch (Exception e) {
				result = "error";
				e.printStackTrace();
				req.setAttribute("msg", "系统异常");
			}
			return result;
		}
		
		
		

	// 销售--提交奖金
	public void rewardSubmit() {
		String id = req.getParameter("id");
		/*System.out.println(id);*/
		try {
			RewardDao rd = new RewardDao();
			int[] i = rd.rewardSubmit(id);
			/*System.out.println(i);*/
			if (i.length > 0) {
				/*System.out.println(i);*/
				json.put("status", "1");
			} else {
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

	// 计算页面-- 奖金核算个人
	@SuppressWarnings("unused")
	public String reward_count() {
		String result = "";
		Object ob = "";
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> rewardcount = new ArrayList<Map<String, Object>>();
		RewardDao rd = new RewardDao();
		try {
			int j = 0;
			j = rd.saveTempora();
			rewardList = rd.reward_count();
			rewardcount=rd.search_reward_batch_all(0);
			if (rewardList != null && rewardList.size() > 0) {
				req.setAttribute("status", "1");
				req.setAttribute("rewardList", rewardList);
			
			} else {
				req.setAttribute("status", "2");
				req.setAttribute("reward_list", "未找到相关数据");
			}
			if(rewardcount!=null&&rewardcount.size()>0){
				req.setAttribute("batch", 1);
				req.setAttribute("rewardcount", rewardcount);
			}else{
				req.setAttribute("batch", 2);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;
	}

	// 奖金计算页面--多条件查询
	public void reward_count_search() {
		String area = req.getParameter("area");
		String product = req.getParameter("product");
		String sales = req.getParameter("rm");
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			rewardList = od.reward_count_select(area, product, sales);
			if (rewardList != null && rewardList.size() > 0) {
				json.put("rewardList", rewardList);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("rewardList", "未找到相关数据");
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

	// 奖金计算页面 ——奖金保存
	public void save_reward() {
		String rewardid = req.getParameter("reward_id");
		List<Object[]> list = new ArrayList<Object[]>();
		
		RewardDao rd = new RewardDao();
		String[] reward = rewardid.split(",");
		for (int i = 0; i < reward.length; i++) {
			Object[] reward_id = { Long.parseLong(reward[i]) };
			
			list.add(reward_id);
		}
		/* Object[] ob=reward_id; */
		try {
			
			int[] i = rd.rewaord_save(list);
			
			if (i.length > 0) {
				long count_batch = jdbcUtil.seq();
				
				rd.reward_save_update(list, count_batch,reward);
				json.put("status", "1");
			} else {
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

	// 奖金计算查询

	/*
	 * public String reword_count_info() { String result=""; long reward_id=0;
	 * try{ String rewardid = req.getParameter("reward_id");
	 * reward_id=Long.parseLong(rewardid); }catch(Exception e){
	 * e.printStackTrace(); } List<Map<String, Object>> rewardList = new
	 * ArrayList<Map<String, Object>>(); RewardDao rd=new RewardDao(); try {
	 * rewardList=rd.reward_count_search(reward_id); if (rewardList != null &&
	 * rewardList.size() > 0) { req.setAttribute("status", "1");
	 * req.setAttribute("rewardList", rewardList); } else {
	 * req.setAttribute("status", "2"); req.setAttribute("reward_list",
	 * "未找到相关数据"); } result = "success"; } catch (Exception e) {
	 * e.printStackTrace(); result = "error"; req.setAttribute("msg", "系统异常"); }
	 * return result; }
	 */
	// 奖金计算--变更后查询
	/*
	 * public String reword_count_changeinfo(){ String result = ""; Object ob =
	 * ""; List<Map<String, Object>> rewardList = new ArrayList<Map<String,
	 * Object>>(); RewardDao rd = new RewardDao(); try { rewardList =
	 * rd.reward_count(); if (rewardList != null && rewardList.size() > 0) {
	 * req.setAttribute("status", "1"); req.setAttribute("rewardList",
	 * rewardList); } else { req.setAttribute("status", "2");
	 * req.setAttribute("reward_list", "未找到相关数据"); } result = "success"; } catch
	 * (Exception e) { e.printStackTrace(); result = "error";
	 * req.setAttribute("msg", "系统异常"); } return result; }
	 */
	/*
	 * //奖金计算页面提交 public void reward_count_save(){
	 * 
	 * } //奖金计算汇总 public void reward_count_sum(){
	 * 
	 * }
	 */
	// 奖金计算页面变更
	/*
	 * public void reword_count_change(){ List<Map<String, Object>> rewardList =
	 * new ArrayList<Map<String, Object>>(); RewardDao rd=new RewardDao();
	 * String hasreward=req.getParameter("has_reward"); double
	 * has_reward=Double.parseDouble("hasreward"); String
	 * reservereward=req.getParameter("reserve_reward"); double
	 * reserve_reward=Double.parseDouble("reservereward"); String
	 * rewardid=req.getParameter("reward_id"); long
	 * reward_id=Long.parseLong(rewardid); try{ int
	 * j=rd.reword_count_change(has_reward,reserve_reward,reward_id); if(j>0){
	 * json.put("status", "1"); }else{ json.put("status", "2"); }
	 * resp.setContentType("text;charset=utf-8");
	 * resp.setCharacterEncoding("UTF-8"); resp.getWriter().print(json);
	 * resp.getWriter().flush(); resp.getWriter().close(); }catch(Exception e){
	 * e.printStackTrace(); e.printStackTrace(); req.setAttribute("msg",
	 * "系统异常"); } }
	 */
	// 奖金计算页面-预留款--多条件查询
	public void reward_yuliu_search() {
		String area = req.getParameter("area");
		String product = req.getParameter("product");
		String sales = req.getParameter("rm");
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			rewardList = od.reward_yuliu_select(area, product, sales);
			if (rewardList != null && rewardList.size() > 0) {
				json.put("rewardList", rewardList);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("rewardList", "未找到相关数据");
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

	//奖金计算页面- 预留款 --列表
	public String yuliu_select() {
		String result = "";
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reservecount = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			rewardList = od.yuliuList();
			reservecount=od.search_yuliu_batch_all(0);
			if (rewardList != null && rewardList.size() > 0) {
				req.setAttribute("status", "1");
				req.setAttribute("rewardList", rewardList);
				

			} else {
				req.setAttribute("status", "2");
				req.setAttribute("reward_list", "未找到相关数据");
			}
			if(reservecount!=null&&reservecount.size()>0){
				req.setAttribute("batch", "1");
				req.setAttribute("reservecount", reservecount);
			}else{
				req.setAttribute("batch", 2);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;
	}

	//奖金计算页面- 预留款--更改
	public void reserve_change() {
		String salesid = req.getParameter("sales_id");
		long sales_id = Long.parseLong(salesid);
		String res = req.getParameter("reserve");
		String sales_name = req.getParameter("sales_name");
		String area = req.getParameter("area");
		double reserve = Double.parseDouble(res);
		String sign = req.getParameter("sign");
		String remark = req.getParameter("remark");
		String sum_reserve=req.getParameter("sum_reserve");
		long reserve_id = jdbcUtil.seq();
		RewardDao od = new RewardDao();
		try {
			int j = od.reserve_change(sales_name, area, reserve_id, sales_id,
					reserve, sign,  remark,sum_reserve);
			if (j > 0) {
				json.put("status", "1");
			} else {
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

	//奖金计算页面-- 预留确认
	public void reserve_confirm() {
		RewardDao od = new RewardDao();
		long count_batch = jdbcUtil.seq();
		try {
			int j = od.reserve_confirm(count_batch);
			if (j > 0) {
				json.put("status", "1");
			} else {
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

	//销售页面- 预留销售确认
	public void reserve_sales_confirm() {
		String reserveid = req.getParameter("reserve_id");
		RewardDao od = new RewardDao();
		try {
			int j[] = od.reserve_sales_confirm(reserveid);
			if (j.length > 0) {
				json.put("status", "1");
			} else {
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

	//销售页面- 预留款-多条件查询
	public void reserve_sales_select() {
		User user = (User) session.getAttribute(Constants.USER);
		String result="";
		if(user==null){
			result="index";
		}
		if(!result.equals("index")){
		long role_id = user.getRole_id();
		long ueser_id=user.get_id();
		req.setAttribute("role_id", role_id);

		String yuliu_count_batch = req.getParameter("yuliu_count_batch");
		long count_batch = 0;
		if (yuliu_count_batch != null && !yuliu_count_batch.equals("")) {
			count_batch = Long.parseLong(yuliu_count_batch);
		}
		List<Map<String, Object>> reserve_list = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			reserve_list = od.reserve_sales_select(count_batch,ueser_id);
			if (reserve_list != null && reserve_list.size() > 0) {
				json.put("reserve_list", reserve_list);
				json.put("status", "1");
			} else {
				json.put("reserve_list", "未查询到数据");
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
		}else{
			try {
				json.put("index", "index");
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

	//核对页面- 预留款--多条件查询
	public void reserve_check_sear() {
		String sales_name = req.getParameter("sales_name");
		List<Map<String, Object>> reserve_list = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			reserve_list = od.reserve_check_sear(sales_name);
			if (reserve_list != null && reserve_list.size() > 0) {
				json.put("reserve_list", reserve_list);
				json.put("status", "1");
			} else {
				json.put("reserve_list", "未查询到数据");
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

	//核对页面- 预留款--保存
	public void reserve_check_save() {
		RewardDao od = new RewardDao();
		long give_batch=jdbcUtil.seq();
		try {
			int j = od.reserve_check_save(give_batch);
			if (j > 0) {
				json.put("status", "1");
			} else {
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

	//核对页面-预留款 --列表
	public String reserve_check_search() {

		String result = "";
		List<Map<String, Object>> reserveList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reservecount = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			reserveList = od.reserve_check_search();
			reservecount=od.search_yuliu_givebatch_all(0);
			if (reserveList != null && reserveList.size() > 0) {
				req.setAttribute("status", "1");
				req.setAttribute("reserveList", reserveList);
				

			} else {
				req.setAttribute("status", "2");
				req.setAttribute("reserve_list", "未找到相关数据");
			}
			if(reservecount!=null&&reservecount.size()>0){
				req.setAttribute("batch", 1);
				req.setAttribute("reservecount", reservecount);
			}else{
				req.setAttribute("batch", 2);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;
	}

	//核对页面- 预留--详情
	public void reserve_check_all() {

		String salesid = req.getParameter("sales_id");
		String create_time=req.getParameter("create_time");
		long sales_id = 0;
		if (salesid != null && !salesid.equals("")) {
			sales_id = Long.parseLong(salesid);
		}
		List<Map<String, Object>> reserveList = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			reserveList = od.reserve_check_all(sales_id,create_time);
			if (reserveList != null && reserveList.size() > 0) {
				json.put("reserveList", reserveList);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("rewardList", "未找到相关数据");
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

	// 预留扣减或发放查询
	public void reserve_save() {
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			rewardList = od.reserve_save();
			if (rewardList != null && rewardList.size() > 0) {
				json.put("rewardList", rewardList);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("rewardList", "未找到相关数据");
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

	//核对页面-个人 奖金-列表
	public String reward_give() {
		String result = "";
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		List<Map<String,Object>> rewardcount=new ArrayList<Map<String,Object>>();
		
		User user = (User) session.getAttribute(Constants.USER);
		if(user==null){
			return "index";
		}
		long role_id = user.getRole_id();
		req.setAttribute("role_id", role_id);
		
		
		RewardDao rd = new RewardDao();
		
		try {
			
			  /*String give_batch=""; try{ give_batch =
			  rd.searchBatch().get(0).get("give_batch") .toString();
			  }catch(Exception e){ give_batch = ""; }
			*/
			long count_batch = jdbcUtil.seq();
			rewardList = rd.searchReword();
			rewardcount=rd.search_reward_givebatch_all(0);
			if (rewardList != null && rewardList.size() > 0) {
				req.setAttribute("status", "1");
				req.setAttribute("rewardList", rewardList);
				
				/* req.setAttribute("give_batch", give_batch); */
				req.setAttribute("count_batch", count_batch);
			} else {
				req.setAttribute("status", "2");
				/* req.setAttribute("give_batch", give_batch); */
				req.setAttribute("reward_list", "未找到相关数据");
			}
			if(rewardcount!=null&&rewardcount.size()>0){
				req.setAttribute("batch", 1);
				req.setAttribute("rewardcount", rewardcount);
			}else{
				req.setAttribute("batch", 2);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;
	}

	// 奖金核对页面 -个人奖金——多条件查询
	public void reward_give_info() {
		String rm = req.getParameter("rm");
		String employeecode = req.getParameter("employee_code");
		String product_type = req.getParameter("product_type");
		long employee_code=0;
		if(employeecode!=null&&!employeecode.equals("")){
			employee_code=Long.parseLong(employeecode);
		}
		List<Map<String, Object>> rewardList = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			rewardList = od.reward_give_info(rm, employee_code, product_type);
			if (rewardList != null && rewardList.size() > 0) {
				json.put("rewardList", rewardList);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("rewardList", "未找到相关数据");
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

	// 奖金核对页面 -个人奖金——奖金发放
	public void reward_check_save() {
		/*String id = req.getParameter("id");*/
		String give_batch=req.getParameter("count_batch");
		try {
			RewardDao rd = new RewardDao();
			int i = rd.reward_check_save(give_batch);
			if (i > 0) {
				json.put("status", "1");
				json.put("rewardList", "更改成功");
			} else {
				json.put("status", "2");
				json.put("rewardList", "更改失败");
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

	// 销售界面--多条件查询团队奖金
	public void group_sales_select() {
		// 获取登录用户id
		/*
		 * User user = (User) session.getAttribute(Constants.USER); loginDao
		 * login = new loginDao(); long role_id = login.role(user.get_id());
		 */
		User user = (User) session.getAttribute(Constants.USER);
	/*	if(user==null){
			   json.put("status", "3");
		        try{
				resp.setContentType("text;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().print(json);
				resp.getWriter().flush();
				resp.getWriter().close();
		        } catch (Exception e) {
					e.printStackTrace();
				}
		}*/
		long role_id = user.getRole_id();
		long ueser_id=user.get_id();
		req.setAttribute("role_id", role_id);

		
		String group_count_batch = req.getParameter("group_count_batch");
		long count_batch = 0;
		if (group_count_batch != null && !group_count_batch.equals("")) {
			count_batch = Long.parseLong(group_count_batch);
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			RewardDao rd = new RewardDao();
			list = rd.group_sales_select(count_batch,ueser_id);
			if (list != null && list.size() > 0) {
				json.put("status", "1");
				json.put("group_list", list);
			} else {
				json.put("status", "2");
				json.put("group_list", "未查询到信息");
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

	// 销售确认--团队奖金
	public void group_confirm() {
		String groupid = req.getParameter("group_id");
		try {
			RewardDao rd = new RewardDao();
			int[] i = rd.group_confirm(groupid);
			if (i.length > 0) {
				json.put("status", "1");
				json.put("rewardList", "更改成功");
			} else {
				json.put("status", "2");
				json.put("rewardList", "更改失败");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
		}
	}

	// 核对页面--团队奖金列表
	public String reward_check_search() {
		String result = "";
		List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
		List<Map<String,Object>> groupcount=new ArrayList<Map<String,Object>>();
		RewardDao rd = new RewardDao();
		try {

			groupList = rd.reward_check_search();
			groupcount=rd.search_group_givebatch_all(0);
			if (groupList != null && groupList.size() > 0) {
				req.setAttribute("status", "1");
				req.setAttribute("groupList", groupList);
			
			} else {
				req.setAttribute("status", "2");
				req.setAttribute("group_list", "未找到相关数据");
			}
			if(groupcount!=null&&groupcount.size()>0){
				req.setAttribute("batch", 1);
				req.setAttribute("groupcount", groupcount);
			}else{
				req.setAttribute("batch", 2);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;
	}

	// 核对页面--团队奖金发放
	public void group_check_save() {
		
		try {
			long give_batch=jdbcUtil.seq();
			RewardDao rd = new RewardDao();
			int i = rd.group_check_save(give_batch);
			if (i > 0) {
				json.put("status", "1");
				json.put("rewardList", "更改成功");
			} else {
				json.put("status", "2");
				json.put("rewardList", "更改失败");
			}
			resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "系统异常");
		}
	}

	// 核对页面--团队奖金多条件查询
	public void group_check_select() {
		String group_name = req.getParameter("group_name");
		String sales_name = req.getParameter("sales_name");
		List<Map<String, Object>> group_list = new ArrayList<Map<String, Object>>();
		try {
			RewardDao rd = new RewardDao();
			group_list = rd.group_check_select(group_name, sales_name);
			if (group_list != null && group_list.size() > 0) {
				json.put("status", "1");
				json.put("group_list", group_list);
			} else {
				json.put("status", "2");
				json.put("group_list", "更改失败");
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

	// 奖金计算Action  给下拉框赋值
	public String reward_count_reckon() {
		List<Map<String,Object>> productList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> areaList=new ArrayList<Map<String,Object>>();
		String result = "";
		User user = (User) session.getAttribute(Constants.USER);
			if(user==null){
				return "index";
			}
			long role_id = user.getRole_id();
			req.setAttribute("role_id", role_id);
			
			
		RewardDao od = new RewardDao();
		try {
			productList = od.reward_count_prod();
			areaList = od.reward_count_area();
			if (productList != null && productList.size() > 0) {
				req.setAttribute("status1", "1");
				req.setAttribute("productList", productList);

			} else {
				req.setAttribute("status1", "2");
				req.setAttribute("productList", "未找到相关数据");
			}
			if (areaList != null && areaList.size() > 0) {
				req.setAttribute("status2", "1");
				req.setAttribute("areaList", areaList);

			} else {
				req.setAttribute("status2", "2");
				req.setAttribute("productList", "未找到相关数据");
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			req.setAttribute("msg", "系统异常");
		}
		return result;
	}
	//团队考核
	public void group_check(){
		/*String year=req.getParameter("year");*/
		String group_year=req.getParameter("group_year");
		String group_stage=req.getParameter("group_stage");
		String kaohe_start_time="";
		String kaohe_end_time="";
		if(group_stage.equals("1")){
			kaohe_start_time=group_year+"-01-01";
			kaohe_end_time=group_year+"-06-30";
		}
          if(group_stage.equals("2")){
        	  kaohe_start_time=group_year+"-07-01";
  			kaohe_end_time=group_year+"-12-31";
		}
		try {
			RewardDao rd = new RewardDao();
			int j = rd.group_check(kaohe_start_time,kaohe_end_time);
			if (j > 0) {
				json.put("status", "1");
			} else {
				if(j<0){
				json.put("status", "2");
				json.put("mesg", "考核失败,请确认是否已经进行过本阶段团队考核！");
				}else{
					json.put("status", "2");
					json.put("mesg", "没有达标的团队！");
				}
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

	// 团队奖金计算--页面 奖金计算
	public void group_count_info() {
		/* String quarter=req.getParameter("quarter"); */
		 String check_end_time=req.getParameter("check_end_time");
		 String check_start_time=req.getParameter("check_start_time");
		RewardDao rd = new RewardDao();
		List<Map<String, Object>> group_list = new ArrayList<Map<String, Object>>();
		try {
			// 根据不同季度进行查询 将结果插入到团队奖金计算临时表中
			rd.group_count_temporary(check_start_time,check_end_time);
			// 根据奖金计算临时表中的数据进行计算奖金计算
				group_list = rd.group_count();
				if (group_list != null && group_list.size() > 0) {
					json.put("status", "1");
					json.put("group_list", group_list);
				} else {
					json.put("status", "2");
					json.put("group_list", "未查询到信息");
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

	// 团队奖金计算页面-奖金保存
	public void group_count_save() {
		String id = req.getParameter("id");
		try {
			RewardDao rd = new RewardDao();
			
			int j[] = rd.group_count_save(id);
			if (j.length > 0) {
				json.put("status", "1");
			} else {
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

	// 团队奖金计算-多条件查询
	public void reward_group_select() {
		String area = req.getParameter("area");
		String product = req.getParameter("product");
		String sales = req.getParameter("rm");
		List<Map<String, Object>> group_list = new ArrayList<Map<String, Object>>();
		RewardDao od = new RewardDao();
		try {
			group_list = od.reward_group_select(area, product, sales);
			if (group_list != null && group_list.size() > 0) {
				json.put("group_list", group_list);
				json.put("status", "1");
			} else {
				json.put("status", "2");
				json.put("rewardList", "未找到相关数据");
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
	
	/**
	 * 报表导出
	 */
	public void reward_count_report() {
		try {
			log.info("进图report");
			String name=req.getParameter("filename");
			String batch=req.getParameter("batch");
			long give_batch=0;
			if(batch!=null&&!batch.equals("")){
				give_batch=Long.parseLong(batch);
			}
		
			/*JSONObject json = new JSONObject();*/
			/*String remoteFilePath ="/home/report.xls";*/
			/*String remoteFilePath ="/home/oms/"+name;*/
			String remoteFilePath ="C:/Users/Lan/Desktop/baobiao/"+name;
			// 输出Excel
			File fileWrite = new File(remoteFilePath);
		
			log.info("创建excel");
			int f=0;
			if(!fileWrite.exists()){
			fileWrite.createNewFile();
			f=1;
			}
			OutputStream os = new FileOutputStream(fileWrite);
			
			reward_count_writeExcel(os,name,f,give_batch);
			/*String localFilePath = req.getParameter("localFilePath");*/
			reward_count_downloadFile(remoteFilePath, resp);
		/*	resp.setContentType("text;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(json);
			resp.getWriter().flush();
			resp.getWriter().close();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写入excel
	 * 
	 * @param os
	 * @param rs
	 */
	public void reward_count_writeExcel(OutputStream os,String name,int f,long give_batch) {
		try {
			/**
			 * 只能通过API提供的工厂方法来创建Workbook，而不能使用WritableWorkbook的构造函数，
			 * 因为类WritableWorkbook的构造函数为protected类型
			 * method(1)直接从目标文件中读取WritableWorkbook wwb =
			 * Workbook.createWorkbook(new File(targetfile)); method(2)如下实例所示
			 * 将WritableWorkbook直接写入到输出流
			 * 
			 */
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			// 创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet("报表", 0);

			// **************往工作表中添加数据*****************

			int i = 0;
			int j = 0;

			// 下面for循环里面的rs.getMetaData().GetColumnCount() 获取数据库中某个表的列总数
			/*
			 * for (int k = 0; k < rs.getMetaData().getColumnCount(); k++) {
			 * //rs.getMetaData().getColumnName()获取表的列名。并添加到
			 * excel表Label里,Label(i,j,s)表示i列j行添加s,s必须是String ws.addCell(new
			 * Label(k, 0, rs.getMetaData().getColumnName(k + 1))); }
			 */
			RewardDao rd = new RewardDao();
			SqlRowSet rs=null;
			//个人奖金
			if(name.equals("rewardcou.xls")){
			rs = rd.reward_count_report(give_batch);
			
			ws.addCell(new Label(0, 0,"销售ID"));
			ws.addCell(new Label(1, 0,"销售姓名"));
			ws.addCell(new Label(2, 0,"下单金额"));
			ws.addCell(new Label(3, 0,"回款金额"));
			ws.addCell(new Label(4, 0,"总奖金"));
			ws.addCell(new Label(5, 0,"可发放奖金"));
			ws.addCell(new Label(6, 0,"预留金额"));
			ws.addCell(new Label(7, 0,"计算批次"));
			
			}
			//预留
			if(name.equals("reservecou.xls")){
				rs = rd.reserve_count_report(give_batch);
				
				ws.addCell(new Label(0, 0,"销售ID"));
				ws.addCell(new Label(1, 0,"销售姓名"));
				ws.addCell(new Label(1, 0,"日期"));
				ws.addCell(new Label(2, 0,"预留总金额"));
				ws.addCell(new Label(3, 0,"预留发放金额"));
				ws.addCell(new Label(4, 0,"预留扣除金额"));
				
			}
			//团队奖金
			if(name.equals("groupcou.xls")){
				rs = rd.group_count_report(give_batch);
			
				ws.addCell(new Label(0, 0,"团队ID"));
				ws.addCell(new Label(1, 0,"团队名称"));
				ws.addCell(new Label(2, 0,"团队长姓名"));
				ws.addCell(new Label(3, 0,"订单号"));
				ws.addCell(new Label(4, 0,"订单版本号"));
				ws.addCell(new Label(5, 0,"下单金额"));
				ws.addCell(new Label(6, 0,"回款金额"));
				ws.addCell(new Label(7, 0,"可发放奖金"));
				
			}
			
			
			while (rs.next()) {
				// 算法，依次添加数据库中所有符合的数据到excel中
				for (int k = 0; k < rs.getMetaData().getColumnCount(); k++) {
					ws.addCell(new Label(k, j + i + 1, rs.getString(k + 1)));
				}
				i++;
			}

			// 1.添加Label对象

			// 写入工作表
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**  
     * 下载远程文件并保存到本地  
     * @param remoteFilePath 远程文件路径   
     * @param localFilePath 本地文件路径  
     */
	public void reward_count_downloadFile(String remoteFilePath, HttpServletResponse response) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(remoteFilePath);
		try {
			bis = new BufferedInputStream(new FileInputStream(f));
			 byte[] buffer = new byte[bis.available()];
			 bis.read(buffer);
			 bis.close();
			 // 清空response
	            response.reset();
	            response.setContentType("application/octet-stream");
	            // 设置response的Header
	            response.addHeader("Content-Disposition", "attachment;filename=" +
	            URLEncoder.encode(f.getName(), "utf-8"));
	            //response.
	            response.addHeader("Content-Length", "" + f.length());
	            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	            
	            toClient.write(buffer);
	            toClient.flush();
	            toClient.close();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				//bos.close();
			} catch (IOException e) {
				log.debug(e.toString());
			}
		}
	}
	
	
	//个人--发放批次分页查询
		public void reward_givebatch() {
			String siz = req.getParameter("size");
			String sign=req.getParameter("sign");
			int size=Integer.parseInt(siz);
			int minsize=0;
			int maxsize=3;
			if(sign=="1"||sign.equals("1")){
			minsize=size;
			maxsize=size+3;
			}
			if(sign=="-1"||sign.equals("-1")){
				if(size!=0){
					
					maxsize=size;
					size=size-3;
					minsize=size;
				}
			}
			List<Map<String,Object>> reservecount=new ArrayList<Map<String,Object>>();
			User user =(User) session.getAttribute(Constants.USER);
					/*if(user==null){
			        json.put("status", "3");
			        try{
					resp.setContentType("text;charset=utf-8");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(json);
					resp.getWriter().flush();
					resp.getWriter().close();
			        } catch (Exception e) {
						e.printStackTrace();
					}
					}*/
					long role_id = user.getRole_id();
					long ueser_id=user.get_id();
					req.setAttribute("role_id", role_id);
			try {
				RewardDao rd = new RewardDao();
	           
	            	 reservecount=rd.search_reward_givebatch_all(size);
	          
				
				if (reservecount!=null&&reservecount.size()>0) {
					json.put("status", "1");
					json.put("reservecount", reservecount);
					json.put("minsize", minsize);
					json.put("maxsize", maxsize);
				} else {
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
		//预留--发放批次分页查询
		public void reserve_givebatch() {
			String siz = req.getParameter("size");
			String sign=req.getParameter("sign");
			int size=Integer.parseInt(siz);
			int minsize=0;
			int maxsize=3;
			if(sign=="1"||sign.equals("1")){
			minsize=size;
			maxsize=size+3;
			}
			if(sign=="-1"||sign.equals("-1")){
				if(size!=0){
					maxsize=size;
					size=size-3;
					minsize=size;
					
				}
			}
			List<Map<String,Object>> reservecount=new ArrayList<Map<String,Object>>();
			User user = (User) session.getAttribute(Constants.USER);
			/*		if(user==null){
					  json.put("status", "3");
			        try{
					resp.setContentType("text;charset=utf-8");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(json);
					resp.getWriter().flush();
					resp.getWriter().close();
			        } catch (Exception e) {
						e.printStackTrace();
					}
					}*/
					long role_id = user.getRole_id();
					long ueser_id=user.get_id();
					req.setAttribute("role_id", role_id);
			try {
				RewardDao rd = new RewardDao();
	            
	            	 reservecount=rd.search_yuliu_givebatch_all(size);
	           
				
				if (reservecount!=null&&reservecount.size()>0) {
					json.put("status", "1");
					json.put("reservecount", reservecount);
					json.put("minsize", minsize);
					json.put("maxsize", maxsize);
				} else {
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
		//团队奖金--发放批次分页查询
		public void group_givebatch() {
			String siz = req.getParameter("size");
			String sign=req.getParameter("sign");
			int size=Integer.parseInt(siz);
			int minsize=0;
			int maxsize=3;
			if(sign=="1"||sign.equals("1")){
			minsize=size;
			maxsize=size+3;
			}
			if(sign=="-1"||sign.equals("-1")){
				if(size!=0){
					
					maxsize=size;
					size=size-3;
					minsize=size;
				}
			}
			List<Map<String,Object>> reservecount=new ArrayList<Map<String,Object>>();
			User user = (User) session.getAttribute(Constants.USER);
			/*		if(user==null){
					  json.put("status", "3");
			        try{
					resp.setContentType("text;charset=utf-8");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().print(json);
					resp.getWriter().flush();
					resp.getWriter().close();
			        } catch (Exception e) {
						e.printStackTrace();
					}
					}*/
					long role_id = user.getRole_id();
					long ueser_id=user.get_id();
					req.setAttribute("role_id", role_id);
			try {
				RewardDao rd = new RewardDao();
	            
	            	 reservecount=rd.search_group_givebatch_all(size);
	           
				
				if (reservecount!=null&&reservecount.size()>0) {
					json.put("status", "1");
					json.put("reservecount", reservecount);
					json.put("minsize", minsize);
					json.put("maxsize", maxsize);
				} else {
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
		
		
		
		// 个人--奖金发放历史记录查询
		public String search_rewardgive_list() {
			List<Map<String, Object>> reward_list = new ArrayList<Map<String, Object>>();
			String result = "";
			RewardDao od = new RewardDao();
			String givebatch = req.getParameter("give_batch");
			long give_batch = Long.parseLong(givebatch);
			// 获取登录用户id
					/*
					 * User user = (User) session.getAttribute(Constants.USER); loginDao
					 * login = new loginDao(); long role_id = login.role(user.get_id());
					 */
			User user = (User) session.getAttribute(Constants.USER);
			if(user==null){
				return "index";
			}
			long role_id = user.getRole_id();
			long ueser_id=user.get_id();
			req.setAttribute("role_id", role_id);
			try {
			
					reward_list = od.search_rewardgive_list_all(give_batch);
				
				if (reward_list != null && reward_list.size() > 0) {
					req.setAttribute("give_batch",give_batch);
					req.setAttribute("status", "1");
					req.setAttribute("rewardList", reward_list);

				} else {
					req.setAttribute("status", "2");
					req.setAttribute("reward_list", "未找到相关数据");
				}
				result = "success";
			} catch (Exception e) {
				result = "error";
				e.printStackTrace();
				req.setAttribute("msg", "系统异常");
			}
			return result;
		}
		// 团队奖金发放历史记录查询
			public String search_groupgive_list() {
				List<Map<String, Object>> reward_list = new ArrayList<Map<String, Object>>();
				String result = "";
				RewardDao od = new RewardDao();
				String givebatch = req.getParameter("give_batch");
				long give_batch = Long.parseLong(givebatch);
				// 获取登录用户id
						/*
						 * User user = (User) session.getAttribute(Constants.USER); loginDao
						 * login = new loginDao(); long role_id = login.role(user.get_id());
						 */
				User user = (User) session.getAttribute(Constants.USER);
				if(user==null){
					return "index";
				}
				long role_id = user.getRole_id();

				req.setAttribute("role_id", role_id);

				try {
				
						reward_list = od.search_groupgive_list_all(give_batch);
					
					if (reward_list != null && reward_list.size() > 0) {
						req.setAttribute("give_batch",give_batch);
						req.setAttribute("status", "1");
						req.setAttribute("rewardList", reward_list);

					} else {
						req.setAttribute("status", "2");
						/*req.setAttribute("reward_list", "未找到相关数据");*/
					}
					result = "success";
				} catch (Exception e) {
					result = "error";
					e.printStackTrace();
					req.setAttribute("msg", "系统异常");
				}
				return result;
			}
			
			// 预留--发放历史记录查询
			public String search_reservegive_list() {
				List<Map<String, Object>> reward_list = new ArrayList<Map<String, Object>>();
				String result = "";
				RewardDao od = new RewardDao();
				String givebatch = req.getParameter("give_batch");
				long give_batch = Long.parseLong(givebatch);
				// 获取登录用户id
						/*
						 * User user = (User) session.getAttribute(Constants.USER); loginDao
						 * login = new loginDao(); long role_id = login.role(user.get_id());
						 */
				User user = (User) session.getAttribute(Constants.USER);
				if(user==null){
					return "index";
				}
				long role_id = user.getRole_id();
			
				req.setAttribute("role_id", role_id);

				try {
					
						reward_list = od.search_yuliugive_list_all(give_batch);
					
					if (reward_list != null && reward_list.size() > 0) {
						req.setAttribute("give_batch",give_batch);
						req.setAttribute("status", "1");
						req.setAttribute("rewardList", reward_list);

					} else {
						req.setAttribute("status", "2");
						req.setAttribute("reward_list", "未找到相关数据");
					}
					result = "success";
				} catch (Exception e) {
					result = "error";
					e.printStackTrace();
					req.setAttribute("msg", "系统异常");
				}
				return result;
			}

	
	
}
