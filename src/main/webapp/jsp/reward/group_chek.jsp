<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>reward_check</title>
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
<!--[if lt IE 9]>
 	<script src="<%=request.getContextPath()%>/js/respond.min.js"></script> 
	<script src="<%=request.getContextPath()%>/js/html5shiv.min.js"></script>    
<![endif]-->
<link href="<%=request.getContextPath()%>/css/my.css" rel="stylesheet"
	media="screen">
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script>
	var n = 1;
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
//保存
 function saveReward() {
		var r = confirm("确认发放团队奖金吗？")
		if (r == false) {
			return false;
		}
		   $.ajax({
				url : 'group_check_save.action', //后台处理程序     
				type : 'post', //数据发送方式     
				dataType : 'json', //接受数据格式        
	             data : { },
				success : function(data) {
					if(data.status==1){
					alert("更改成功!");
					window.location.href ="reward_check_search.action";
					}else{
						alert("更改失败!");
					}
				},
				error : function(result) {
					$("#reserve_caches").empty();
					alert('系统异常,请稍后再试!');
				}
			});
	}
	//多条件查询
	function group_select() {
		var group_name=$("#group_name").val().trim();
		var sales_name = $("#sales_name").val().trim();
		if (group_name == "请输入团队名称") {
			group_name = "";
		}
		if (sales_name == "请输入团队长姓名") {
			sales_name = "";
		}
		$.ajax({
					url : 'group_check_select.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'group_name':group_name,
						'sales_name' : sales_name,
					},
					success : function(data) {
						$("#group_info").empty();
						if (data.status == 1) {
							var rewardInfo = data.group_list;
							for (var i = 0; i < rewardInfo.length; i++) {			
								//自动生成
								var reward_info = '<tr class="default" id="cust_list ">'
										+ '<td>'
										+ rewardInfo[i].name
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].sales_name
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].order_no
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].sum_amount
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].reward_return
										+ '</td>'
										+ '</tr>';
								$("#group_info").append(reward_info);
							}
							var reward_info='<tr> '
								+' <td colspan="13" style="text-align: center;"><a id="group_save" '
								+' href="javascript:void(0);" onclick="saveReward()" '
								+' style="outline: none;"><i></i> 奖金发放</a></td> '
								+' </tr>';
							$("#group_info").append(reward_info);
							$("#group_save").addClass("btn btn-info top10");
						} else {
							var reward_info = '<tr class="default" id="cust_list " >'
									+ '<th colspan="12" style="text-align: center ;">'
									+ " 未查到相关数据" + '</th>' + '</tr>';
							$("#group_info").append(reward_info);
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
				url : 'group_givebatch.action', //后台处理程序     
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
								+' href="search_groupgive_list.action?give_batch='
								+rewardInfo[i].give_batch
								+'" '
								+'	class="btn btn-danger" style="display: inline-block;">'
								+rewardInfo[i].give_batch 
								+'</a></td>';
							 
						}
						reward_info+='<td><button onclick="subgo('+1+')">>></button></td></tr></table>';
						 $("#countbatch").append(reward_info);
						   
							   $("#minbatch").val(data.minsize);
							   $("#maxbatch").val(data.maxsize);
					}else{
						/*  alert("没有更多数据!");  */
					}
				},
				error : function(result) {
					$("#countbatch").empty();
				} 
		   });
		
	}
	 $(function(){ 

		$("#minbatch").val("0");
		$("#maxbatch").val("3");
		subgo('-1');

		})
	 
	 //报表导出
	 function export_report(){
	var localFilePath = $('#localFilePath').val();
	var filename="groupcou.xls";
	var batch="";
	//location.href="";
	window.open('reward_count_report.action?filename='+filename+'&batch='+batch, '_blank');

}	
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->
	<%@ include file="../header.jsp"%>


	<!--主体-->

	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="http://www.jq22.com/">首页</a></li>
					<li class="active">团队奖金核对</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="#" data-toggle="modal" data-target="#exampleModal2"> <img
						id="tou" src="image/person.png" class="f imgr20">
					</a>
					<div class="f imgf20">
						<h4>团队奖金核对</h4>
					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> 奖金查询<br>

					</div>

					<ul class="list-group">
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input id="group_name" name="group_name" type="text" value="请输入团队名称"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
						<!-- <li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input id="employee_code" name="employee_code" type="text"
							value="请输入团队编号"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li> -->
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input id="sales_name" name="sales_name" type="text" value="请输入团队长姓名"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
					</ul>

					<button class="btn btn-info top10" onclick="group_select()"
						style="width: 100%">点击查询</button>




				</div>
				<div class="df"></div>
			</div>


			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<div class="myNav row">
						 <a href="/OMS/reward_give.action" style="outline: none;"><i
							class="glyphicon glyphicon-plus"></i> 奖金发放</a> <a
							href="/OMS/reserve_check_search.action" style="outline: none;"><i
							class="glyphicon glyphicon-plus"></i> 预留发放</a>
							<a
							href="/OMS/reward_check_search.action" style="outline: none;"><i
							class="glyphicon glyphicon-plus"></i> 团队奖金发放</a>
							<%--  <span id='reward_YL' style="cursor:pointer;margin-left: 10px;" >
							 <i class="glyphicon glyphicon-plus"></i>奖金发放 </span> 
							  <span id='reward_YL' style="cursor:pointer;margin-left: 10px;" >
							  <i class="glyphicon glyphicon-plus"></i>预留发放</span> 
							   <span id='reward_YL' style="cursor:pointer;margin-left: 10px;" >
							   <i class="glyphicon glyphicon-plus"></i>团队奖金发放 </span>  --%>
							    <span id='reward_YL' style="cursor:pointer;margin-left: 10px;" >
							    <a href="javascript:export_report()">
							    <i class="glyphicon glyphicon-plus"></i>导出报表 </a></span> 
							    
					</div>
					<div class="row topx myMinh" style="">

						<div class="spjz" style="">

							<div class="panel panel-default" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>团队奖金列表

									</h3>
								</div>
								<div class="panel-body" style="overflow:auto;">
									<%-- <p>
										本次编号：<input type="text" readonly="readonly" id="count_batch"
											name="count_batch" value="${count_batch }"
											style="width: 90%; border: none; outline: none;">
									</p> --%>
									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<!-- <th><input type="checkbox" name="allChecked"
													id="allChecked" onclick="DoCheck()" />全选</th> -->
											<!-- 	<th>团队编号</th> -->
												<th>团队名称</th>
												<th>团队长名称</th>
												<th>订单编号</th>
												<th>订单金额</th>
												<th>发放金额</th>
											</tr>
											
											<!-- <tr class="demo_tr">
												<th><input type="checkbox" name="allChecked"
													id="allChecked" onclick="DoCheck()" />全选</th>
												<th>销售</th>
												<th>地区</th>
												<th>应发放</th>
												<th>已预留</th>
												<th>可发放</th>
											</tr> -->
										</thead>
										<tbody id="group_info">

											<!--  <tr class="default" id="cust_list">
            <td>20160501</td>
            <td>20160501</td>
            <td>RM_1</td>
            <td>2000000</td>
            <td>200000</td>
            </tr> -->
											<s:if test="#request.status==1 ">
												<s:iterator value="#request.groupList" var="groupLi">
													<tr class="default" id="cust_list" style="cursor: pointer;">
														<%-- <td><input value="${groupLi.group_id }"
															name="items" type="checkbox"></td> --%>
														<td>${groupLi.name }</td>
														<td>${groupLi.sales_name }</td>
														<td>${groupLi.order_no }</td>
														<td>${groupLi.sum_amount }</td>
														<td>${groupLi.reward_return }</td>
													</tr>
												</s:iterator>
												<tr>
													<td colspan="13" style="text-align: center;"><a class="btn btn-info top10"
														href="javascript:void(0);" onclick="saveReward()"
														style="outline: none;"><i></i> 奖金发放</a></td>
												</tr>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr>
													<th colspan="13" style="text-align: center;">${group_list }</th>
												</tr>
											</s:elseif>


										</tbody>
									</table>
									<div id="countbatch" style="text-align: center; margin: 15px;">
									<table>
									<tr align="center"><td><button onclick="subgo('-1')"><<</button></td>
										<s:if test="#request.batch==1 ">
											<s:iterator value="#request.groupcount" var="groupcou">
												<td><a
													href="search_groupgive_list.action?give_batch=${groupcou.give_batch}"
													class="btn btn-danger" style="display: inline-block;">${groupcou.give_batch }</a>
											 </td>
											</s:iterator>
										</s:if>
										<td><button onclick="subgo('1')">>></button></td>
										</tr>
									</table>
									</div>
									<a><input id="minbatch" type="hidden" value="0"></a>
										<a><input id="maxbatch" type="hidden" value="3"></a>
										<%--  <a class="left carousel-control"
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
							</div>


						</div>
					</div>
				</div>
			</div>
			<!--end 右-->
		</div>


		<!--底部-->
		<nav class="foot navbar-inverse navbar-fixed-bottom">
			<ul class="list-inline">
				<li class="footer-ss"><a href="javascript:void(0)"
					data-container="body" data-toggle="popover" data-placement="top"
					data-html="true" data-content=" " data-original-title="" title="">更多
						&nbsp;<i class="fa fa-angle-down"></i>
				</a></li>
				<li class="footer-ss">在线反馈</li>
				<li class="footer-ss">帮助中心</li>
				<li>Copyright © 2016 帆茂</li>
			</ul>
			<ul class="list-inline text-right">
				<li></li>
			</ul>
		</nav>


	</div>

</body>
</html>
<script>
	function DoCheck() {
		var ch = document.getElementsByName("choose");
		$('input[type=checkbox]')
				.prop('checked', $(allChecked).prop('checked'));
	}
</script>
