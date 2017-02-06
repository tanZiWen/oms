<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
String now_date = df.format(new Date());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>compay_detail</title>
<meta name="description" content="帆茂投资">
<meta name="keywords" content="帆茂投资">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/font-awesome.min.css"
	rel="stylesheet" media="screen">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
	<link href="<%=request.getContextPath() %>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<!--[if lt IE 9]>
  <script src="<%=request.getContextPath()%>/js/respond.min.js"></script> 
  <script src="<%=request.getContextPath()%>/js/html5shiv.min.js"></script>    
<![endif]-->
<link href="<%=request.getContextPath()%>/css/my.css" rel="stylesheet"
	media="screen">
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker.min.js"></script>
<script src="/OMS/js/cust.js"></script>
<script>
	var n = 1;
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">

 	function group_count_inf(){
		//友好提示 确认是否进行该操作
		var check_start_time=$("#check_start_time").val();
		var check_end_time=$("#check_end_time").val();
		var mydate = new Date();
		var r = confirm("现在是  " + mydate.toLocaleDateString() +   ",您确定进行季度奖金计算吗？")
		if (r == false) {
			return false;
		}
		$.ajax({
					url : 'group_count_info.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'check_start_time' : check_start_time,
						'check_end_time' : check_end_time,
					},
					success : function(data) {
						$("#group_info").empty();
						if (data.status == 1) {
							var rewardInfo = data.group_list;
							for (var i = 0; i < rewardInfo.length; i++) {
								//自动生成
								var reward_info = '<tr class="default" id="cust_list ">'
										+ '<td>'
										+ '<input name="items" value="'
			        + rewardInfo[i].id  
			        +'" type="checkbox">'
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].sales_name
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].name
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].area
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].order_no
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].prod_name
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].sum_amount
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].sum_return
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].reward_return
										+ '</td>'
										+ '</tr>';
								$("#group_info").append(reward_info);
							}
							var reward_info='<td colspan="9" style="text-align: center;"><a '
								+'  style="width: 20%" id="reward_save" '
								+' href="javascript:void(0);" onclick="saveReward()" '
								+' style="outline: none;"> 奖金计算 </a></td> '
							  +'  </tr>';
							  $("#group_info").append(reward_info);
							  $("#reward_save").addClass("btn btn-info top10");

						} else {
							var reward_info = '<tr class="default" id="cust_list " >'
									+ '<th colspan="9" style="text-align: center ;">'
									+ " 未查到相关数据" + '</th>' + '</tr>';
							$("#group_info").append(reward_info);
						}
					},
					error : function(result) {
						alert('系统异常,请稍后再试!');
					}
				});

	} 
	/* 提交数据到后台 */
	 function saveReward() {
		var id = "";
		if ($("input[name='items']").length <= 0) {
			alert("当前没有可保存的奖金");
			return false;
		} 
		if ($("input[name='items']:checkbox:checked").length > 0) {
			$("input[name='items']:checkbox:checked").each(function() {
				id += $(this).val() + ",";
			})
		} else {
			alert('请您先选择可发放的奖金!');
			return false;
		}
		var r = confirm("确认保存已计算的奖金吗？")
		if (r == false) {
			return false;
		}
		$.ajax({
			url : 'group_count_save.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				"id" : id
			},
			success : function(data) {
				if (data.status == 1) {
					alert("保存成功！");
					window.location.href = "tuandui.action";
				} else {
					alert("保存失败！");
					window.location.href = "tuandui.action";
				}
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});

	} 
	//多条件查询
	  $('#search', window.parent.document)
			.click(
					function() {
						var rm = $('#rm', window.parent.document).val();
						var product = $('#product', window.parent.document).val();
						var area = $('#area', window.parent.document).val();
						if (rm == "请输入销售名") {
							rm = "";
						}
						$
								.ajax({
									url : 'reward_group_select.action', //后台处理程序     
									type : 'post', //数据发送方式     
									dataType : 'json', //接受数据格式        
									data : {
										'rm' : rm,
										'product' : product,
										'area' : area,
									},
									success : function(data) {
										$("#group_info").empty();
										if (data.status == 1) {
											var rewardInfo = data.group_list;
											for (var i = 0; i < rewardInfo.length; i++) {
												//自动生成
												var reward_info = '<tr class="default" id="cust_list ">'
														+ '<td>'
														+ '<input name="items" value="'
							        + rewardInfo[i].id 
							        +'" type="checkbox">'
														+ '</td>'
														+ '<td>'
														+ rewardInfo[i].sales_name
														+ '</td>'
														+ '<td>'
														+ rewardInfo[i].name
														+ '</td>'
														+ '<td>'
														+ rewardInfo[i].area
														+ '</td>'
														+ '<td>'
														+ rewardInfo[i].order_no
														+ '</td>'
														+ '<td>'
														+ rewardInfo[i].prod_name
														+ '</td>'
														+ '<td>'
														+ rewardInfo[i].sum_amount
														+ '</td>'
														+ '<td>'
														+ rewardInfo[i].sum_return
														+ '</td>'
														+ '<td>'
														+ rewardInfo[i].reward_return
														+ '</td>' + '</tr>';
												$("#group_info").append(
														reward_info);
											}	
											var reward_info='<tr> <td colspan="9" style="text-align: center;"><a '
												+' class="btn btn-info top10" style="width: 20%" '
												+' href="javascript:void(0);" onclick="saveReward()" '
												+' style="outline: none;"> 奖金计算 </a></td> '
											  +'  </tr>';
											  $("#group_info").append(reward_info);

										} else {
											var reward_info = '<tr class="default" id="cust_list " >'
													+ '<th colspan="9" style="text-align: center ;">'
													+ " 未查到相关数据"
													+ '</th>'
													+ '</tr>';
											$("#group_info")
													.append(reward_info);
										}
									},
									error : function(result) {
										alert('系统异常,请稍后再试!');
									}
								});
					});
	//团队考核
	function group_check() {
		/* if (oa == 1) {
			var me = "上半年";
		} else {
			var me = "下半年";
		} */
		var group_year=$("#group_year").val();
		var group_stage=$("#group_stage").val();
		if(group_stage=='1'){
		var group_type="上半年";
		}
		if(group_stage=='2'){
			var group_type="下半年";
		}
		
		//友好提示 确认是否进行该操作
		var mydate = new Date();
		var r = confirm("现在是   " + mydate.toLocaleDateString() + ",您确定进行"+group_year+"年"+group_type+"的团队考核吗？")
		if (r == false) {
			return false;
		}
		$.ajax({
			url : 'group_check.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				"group_year" : group_year,
				"group_stage" : group_stage,
			},
			success : function(data) {
				if (data.status == 1) {
					alert("考核成功！");
					window.location.href = "tuandui.action";
				} else {
					alert(data.mesg);
					window.location.href = "tuandui.action";
				}
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});
	}


	function subgo(sign) {
		   var size="";
		   if(sign==-1){
			   size=$("#minbatch").val();
		   }
		   if(sign==1){
			   size=$("#maxbatch").val();
		   }
		   $.ajax({
				url : 'groupbatch.action', //后台处理程序     
				type : 'post', //数据发送方式     
				dataType : 'json', //接受数据格式        
				data : {
					'sign' : sign,
					'size' : size,
				},
				success : function(data) {
					
					if (data.status == 1) {
						  $("#countbatch").empty();
						var rewardInfo = data.reservecount;
						var reward_info='<table><tr align="center"><td><button onclick="subgo('+-1+')"><<</button></td>';
						for (var i = 0; i < rewardInfo.length; i++) {
							 reward_info+='<td><a '
								+' href="search_group_list.action?count_batch='
								+rewardInfo[i].reward_batch_id
								+'" target="_top" '
								+'	class="btn btn-danger" style="display: inline-block;">'
								+rewardInfo[i].reward_batch_id 
								+'</a></td>';
							  
						}
						reward_info+='<td><button onclick="subgo('+1+')">>></button></td></tr></table>';
						$("#countbatch").append(reward_info);
							   $("#minbatch").val(data.minsize);
							   $("#maxbatch").val(data.maxsize);
					}else{
						/* alert("没有更多数据!"); */
					}
				},
				error : function(result) {
					alert('系统异常,请稍后再试!');
				} 
		   });
		
	}
	$(function(){ 

	$("#minbatch").val("0");
	$("#maxbatch").val("3");
	subgo('-1');
	})
	
	window.onload = function(){
		$('#pro', window.parent.document).show();
	}
</script>
</head>
<body>
	<!-- 奖金发放-团队 -->
	<div class="panel panel-default" style="width: 100%;">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>奖金发放-团队列表
			</h3>
		</div>
		<div>
			<table class="table cust_table" style="font-size: 10px;">
				<tr>
				<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年份:<select id="group_year"
								name="group_year" 
								style="width: 90px; height: 28px; outline: none;">
                           <s:if test="#request.year==1 ">
									<s:iterator value="#request.groupyear" var="groupye">
						<option value="${groupye.paeam_year }">${groupye.paeam_year }</option> -->
						</s:iterator>
						</s:if>
						<s:elseif test="#request.status1==2">
							<option  ></option>
					</s:elseif>
							</select></th>
					<th>考核阶段:<select id="group_stage"
								name="group_stage" 
								style="width: 90px; height: 28px; outline: none;">

									<option value="1">上半年</option>
									<option value="2">下半年</option>
							</select></th> 
					<th style="text-align: center;">
						<button style="font-size: 15px;" class="demo_tr"
							onclick="group_check()">团队考核</button>&nbsp;&nbsp;&nbsp;
							</th>
						<!-- 	<th>
						<button style="font-size: 15px;" class="demo_tr"
							onclick="group_check('2')">下半年团队考核</button>
					</th> -->
				</tr>
				</table>
				<table class="table cust_table" style="font-size: 10px;">
				<tr>
					<!-- <th style="text-align: center;">
						<button style="font-size: 15px;" class="demo_tr"
							onclick="group_count_inf('1')">第一季度奖金计算</button>&nbsp;&nbsp;&nbsp;
						<button style="font-size: 15px;" class="demo_tr"
							onclick="group_count_inf('2')">第二季度奖金计算</button>&nbsp;&nbsp;&nbsp;
						<button style="font-size: 15px;" class="demo_tr"
							onclick="group_count_inf('3')">第三季度奖金计算</button>&nbsp;&nbsp;&nbsp;
						<button style="font-size: 15px;" class="demo_tr"
							onclick="group_count_inf('4')">第四季度奖金计算</button>
					</th> -->
					<th>起始时间:<input  name="refund_time"  id="check_start_time"
									value="<%=now_date%>" class="form_datetime" type="text"
									></th>
					<th>结束时间:<input  name="refund_time" id="check_end_time"
									value="<%=now_date%>" class="form_datetime" type="text"
									></th> 
					<th style="text-align: center;"><button style="font-size: 15px;" class="demo_tr"
							onclick="group_count_inf()">计算奖金</button></th>
				</tr>

			</table>
		</div>
		<div class="panel-body" style="overflow:auto;">
			<table class="table cust_table" style="font-size: 10px;">

				<thead>
					<tr class="demo_tr">
						<th>选择</th>
						<th>团队长</th>
						<th>团队名称</th>
						<th>地区</th>
						<th>订单号</th>
						<th>产品名称</th>
						<th>订单总金额</th>
						<th>回款金额</th>
						<th>发放奖金</th>

					</tr>
				</thead>
				<tbody id="group_info">
					<s:if test="#request.group==1 ">
						<s:iterator value="#request.groupList" var="groupLi">
								<tr class="default" id="cust_list ">
								<td><input name="items" value="${groupLi.id }" type="checkbox"></td>
								<td>${groupLi.sales_name }</td>
								<td>${groupLi.name }</td>
								<td>${groupLi.area }</td>
								<td>${groupLi.order_no }</td>
								<td>${groupLi.prod_name }</td>
								<td>${groupLi.sum_amount }</td>
								<td>${groupLi.sum_return }</td>
								<td>${groupLi.reward_return }</td>
							</tr>
						</s:iterator>
						<tr>
							<tr> <td colspan="9" style="text-align: center;"><a 
												 class="btn btn-info top10" style="width: 20%" 
												 href="javascript:void(0);" onclick="saveReward()" 
												 style="outline: none;"> 奖金计算 </a></td> 
											 
						</tr>
					</s:if>
					<s:elseif test="#request.group==2">
						<tr>
							<th colspan="9" style="text-align: center;">${groupList }</th>
						</tr>
					</s:elseif>

				</tbody>
				
			</table>
			<div id="countbatch" style="text-align: center; margin: 15px;">
			<table>
									<tr align="center"><td><button onclick="subgo('-1')"><<</button></td>
										<s:if test="#request.batch==1 ">
											<s:iterator value="#request.groupcount" var="groupcou">
												<td><a href="search_group_list.action?count_batch=${groupcou.reward_batch_id}" target="_top"
													class="btn btn-danger" style="display: inline-block;">${groupcou.reward_batch_id }</a>
											   </td>
											</s:iterator>
										</s:if>
												<td><button onclick="subgo('1')">>></button></td></tr>
									</table>
									</div>
									<a><input id="minbatch" type="hidden" value="0"></a>
										<a><input id="maxbatch" type="hidden" value="3"></a>
									<%-- 	 <a class="left carousel-control"
										href="javascript:void(0)" onclick="subgo('-1')" role="button"
										data-slide="prev" style="background-image: none;">  <span
										class="glyphicon glyphicon-chevron-left" aria-hidden="true"
										style="color: #333;"></span>  <span class="sr-only">Previous</span>
									</a> 
									<a class="right carousel-control"
										href="javascript:void(0)" onclick="subgo('1')" role="button"
										data-slide="next" style="background-image: none; color: #333;">
										<span class="glyphicon glyphicon-chevron-right"
										aria-hidden="true"></span> <span class="sr-only">Next</span>
									</a> --%>
		</div>
<script>
$(function(){
	$(".form_datetime").datetimepicker({
		 format: 'yyyy-mm-dd',  
       weekStart: 1,  
       autoclose: true,  
       startView: 2,  
       minView: 2,  
       forceParse: false,  
       language: 'zh-CN'  
	});
	
});

function newDate(obj) {
	var newTime = new Date(obj); //毫秒转换时间  
	var year = newTime.getFullYear();
	var mon = newTime.getMonth()+1;  //0~11
	var day = newTime.getDate();
	var newDate = year+'-'+mon+'-'+day;
	return newDate
}
</script>
	</div>
</body>
</html>

