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
	<style type="text/css">
	    .goback {
      position: absolute;
      left: 18px;
      border: 20px solid transparent;
      border-right: 20px solid black;
	
    }
    
    .goback:hover {
      border-right: 20px solid red;
	 
    }
    
    .goback:after {
      content: '';
      position: absolute;
      top: -20px;
      left: -14px;
      border: 20px solid transparent;
      border-right: 20px solid #fff;
	     
    }
    .goback_right {
      position: absolute;
      left: 18px;
      border: 20px solid transparent;
      border-right: 20px solid black;
	
    }
    
    .goback_right:hover {
      border-right: 20px solid red;
	 
    }
    
    .goback_right:after {
      content: '';
      position: absolute;
      top: -20px;
      left: -14px;
      border: 20px solid transparent;
      border-right: 20px solid #fff;
	     
    } 
	</style>

<script type="text/javascript">
//保存
	   function reseerve_check_save() {
			var r = confirm("确认发放预留吗？")
			if (r == false) {
				return false;
			}
		   $.ajax({
				url : 'reserve_check_save.action', //后台处理程序     
				type : 'post', //数据发送方式     
				dataType : 'json', //接受数据格式        
	             data : { },
				success : function(data) {
					if(data.status==1){
					alert("更改成功!");
					window.location.href ="reserve_check_search.action";
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
	   function reserve_check_sear() {
		var sales_name=$("#rm").val();
		if(sales_name=="请输入销售姓名"){
			sales_name="";
		}
		
		$.ajax({
			url : 'reserve_check_sear.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'sales_name' : sales_name,
			},
			success : function(data) {
				$("#reserve_give_info").empty();
				
				if (data.status == 1) {
					var reserveInfo = data.reserve_list;
					
					for (var i = 0; i < reserveInfo.length; i++) {
						//自动生成
						var reserve_inf="";
						 reserve_inf= '<tr class="default" >'
								+ '<td>'
								+ reserveInfo[i].sales_id
								+ '</td>'
								+ '<td>'
								+ reserveInfo[i].sales_name
								+ '</td>'
								+ '<td>'
								+ reserveInfo[i].sum_reserve
								+ '</td>'
								+ '<td>'
								+ reserveInfo[i].give_reserve   
								+ '</td>'
								+ '<td> <input id="create_time" readonly="readonly" value=" '     
								+ reserveInfo[i].create_time
								+ ' "></td>'
								+ '<td><a onclick="reserve_check_all('
								+reserveInfo[i].sales_id
								+')">详情</a></td>'
								+ '</tr>';
							
						$("#reserve_give_info").append(reserve_inf);
					}
					//alert(reserve_inf);
					var reserve_inf='<tr><td colspan="6" style="text-align: center;"><a id="reward_save" '
						+' href="javascript:void(0);" onclick="reseerve_check_save()" '
						+'	style="outline: none;"><i></i> 发放</a></td></tr>';
						$("#reserve_give_info").append(reserve_inf);
						 $("#reward_save").addClass("btn btn-info top10");
				} else {
					var reserve_inf = '<tr class="default" id="cust_list " >'
							+ '<th colspan="12" style="text-align: center ;">'
							+ " 未查到相关数据" + '</th>' + '</tr>';
					$("#reserve_give_info").append(reserve_inf);
				}
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});
	}
	   //查看详情
	   function reserve_check_all(sales_id) {
           var create_time=$("#create_time").val();
			$.ajax({
				url : 'reserve_check_all.action', //后台处理程序     
				type : 'post', //数据发送方式     
				dataType : 'json', //接受数据格式        
				data : {
					'sales_id' : sales_id,
					'create_time' :create_time,
				},
				success : function(data) {
					$('#modal_caches').modal('show');
					$("#reserve_all").empty();
					if (data.status == 1) {
						var reserveInfo = data.reserveList;
						for (var i = 0; i < reserveInfo.length; i++) {
							//自动生成
							var reserve_info = '<tr class="default" id="cust_list" style="cursor: pointer;" >'
									+ '<td>'
									+ reserveInfo[i].sales_id
									+ '</td>'
									+ '<td>'
									+ reserveInfo[i].count_batch
									+ '</td>'
									+ '<td>'
									+ reserveInfo[i].reserve
									+ '</td>'
									+ '<td>'
									+ reserveInfo[i].reserve_kou
									+ '</td>'
									+ '<td>'
									+ reserveInfo[i].reserve_give
									+ '</td>'
									+ '<td>'
									+ reserveInfo[i].create_time
									+ '</td>'
									+ '</tr>';
							$("#reserve_all").append(reserve_info);
						}
					} else {
						var reserve_info = '<tr class="default" id="cust_list " >'
								+ '<th colspan="12" style="text-align: center ;">'
								+ " 未查到相关数据" + '</th>' + '</tr>';
						$("#reserve_all").append(reserve_info);
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
				url : 'reserve_givebatch.action', //后台处理程序     
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
								+' href="search_reservegive_list.action?give_batch='
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
					alert('系统异常,请稍后再试!');
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
	var filename="reservecou.xls";
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
					<li class="active">预留发放核对</li>
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
						<h4>预留发放核对</h4>
					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> 预留查询<br>

					</div>

					<ul class="list-group">
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input id="rm" name="rm" type="text" value="请输入销售姓名"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
						<!-- 	<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input id="employee_code" name="employee_code" type="text" value="请输入员工编号"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li> -->
					</ul>

					<button class="btn btn-info top10" onclick="reserve_check_sear()"
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
							class="glyphicon glyphicon-plus"></i> 预留发放</a> <a
							href="/OMS/reward_check_search.action" style="outline: none;"><i
							class="glyphicon glyphicon-plus"></i> 团队奖金发放</a>
							 <span id='reward_YL' style="cursor:pointer;margin-left: 10px;" >
							 <a href="javascript:export_report()">
							    <i class="glyphicon glyphicon-plus"></i>导出报表</a> </span> 


					</div>
					<div class="row topx myMinh" style="">

						<div class="spjz" style="">

							<div class="panel panel-default" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>预留发放列表

									</h3>
								</div>
								<div class="panel-body" style="overflow:auto;">
									<%-- 	<p>
										本次编号：<input type="text" readonly="readonly" id="count_batch"
											name="count_batch" value="${count_batch }"
											style="width: 90%; border: none; outline: none;">
									</p> --%>
									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<!-- <th><input type="checkbox" name="allChecked"
													id="allChecked" onclick="DoCheck()" />全选</th> -->
												<th>销售ID</th>
												<th>销售姓名</th>
												<th>预留总额</th>
												<th>预留发放金额</th>
												<th>日期</th>
												<th>详情</th>
											</tr>
										</thead>
										
										<tbody id="reserve_give_info">
											<s:if test="#request.status==1 ">
												<s:iterator value="#request.reserveList" var="reserveLi">
													<tr class="default" id="cust_list" style="cursor: pointer;">
														<%-- <td><input value="${rewardLi.sales_id }"
															name="items" type="checkbox"></td> --%>
														<td>${reserveLi.sales_id }</td>
														<td>${reserveLi.sales_name }</td>
														<td>${reserveLi.sum_reserve }</td>
														<td>${reserveLi.give_reserve }</td>
														<td><input id="create_time" readonly="readonly" value="${reserveLi.create_time }"> </td>
														<td><a
															onclick="reserve_check_all(${reserveLi.sales_id })">详情</a></td>
													</tr>

												</s:iterator>
												<tr>
												<td colspan="6" style="text-align: center;"><a class="btn btn-info top10"
														href="javascript:void(0);" onclick="reseerve_check_save()"
														style="outline: none;"><i></i> 发放</a></td>
													<!-- <td class="btn btn-info top10" colspan="6" style="text-align: center;"><button
															onclick="reseerve_check_save()">发放</button></td> -->
												</tr>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr>
													<th colspan="6" style="text-align: center;">${reserve_list }</th>
												</tr>
											</s:elseif>
										
										</tbody>

									</table>
									<div>
									<div id="countbatch" style="text-align: center; margin: 15px;">
									<table>
									<tr align="center"><td><button onclick="subgo('-1')"><<</button></td>
										<s:if test="#request.batch==1 ">
									      
											<s:iterator value="#request.reservecount" var="reservecou">
											<td>	<a
													href="search_reservegive_list.action?give_batch=${reservecou.give_batch}"
													class="btn btn-danger" style="display: inline-block;">${reservecou.give_batch }</a>
										  </td>
											</s:iterator>
										</s:if>
										<td><button onclick="subgo('1')">>></button></td>
										</tr>
									</table>	
                                  </div>
                                  <a><input id="minbatch" type="hidden" value="0"></a>
										<a><input id="maxbatch" type="hidden" value="3"></a>
			<%-- 	                      <a class="left carousel-control"
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
									</a>  --%>
									</div>
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

	<!-- 查看详情弹窗 -->
	<div class="modal" id="modal_caches" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px;">
				<div class="myright-n">
					<div class="row topx myMinh" style="border: 0; padding: 0;">
						<div class="spjz" style="">
							<div class="panel panel-default" style="width: 100%;">
								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>预留发放详情
									</h3>
								</div>
								<div class="panel-body">
									<table class="table cust_table" id="achievement_li123">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>销售ID</th>
												<th>计算批次</th>
												<th>预留金额</th>
												<th>预留扣除金额</th>
												<th>预留发放金额</th>
												<th>计算日期</th>
											</tr>
										</thead>
										<tbody id="reserve_all">
										</tbody>
									</table>

								</div>
							</div>
							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									onclick="dele()" class="btn btn-lg btn-default">取消</button>
								<!-- <input type="button" class="btn btn-lg" data-dismiss="modal" onclick="show_indiv()" style="background-color: #5bc0de; color: #fff;"> -->
								<button class="btn btn-lg" data-dismiss="modal"
									onclick="reserve_confirm()"
									style="background-color: #5bc0de; color: #fff;">确定</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
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
