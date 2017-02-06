<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>reward_yuliu</title>
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

window.onload = function(){
	$('#pro', window.parent.document).hide();
}

//多条件查询
$('#search', window.parent.document).click(function(){
	var rm = $('#rm', window.parent.document).val();
	var product=$('#product', window.parent.document).find("option:selected").text();
	var area=$('#area', window.parent.document).val();
	if(rm=="请输入销售名"){
		rm="";
	}
	$.ajax({
		url : 'reward_yuliu_search.action', //后台处理程序     
		type : 'post', //数据发送方式     
		dataType : 'json', //接受数据格式        
		data : {
			'rm' : rm,
			'product' : product,
			'area' : area,
		},
		success : function(data) {
			$("#reword_yuliu").empty();
		/* 	window.parent.main.document.location.reload(); */
			 if (data.status == 1) {
				var rewardInfo = data.rewardList;
				for (var i = 0; i < rewardInfo.length; i++) {
					//判断是否为空
					if (rewardInfo[i].sales_name == null
							|| rewardInfo[i].sales_name == "") {
						rewardInfo[i].sales_name = " ";
					}
					if (rewardInfo[i].area == null
							|| rewardInfo[i].area == "") {
						rewardInfo[i].area = " ";
					}
					if (rewardInfo[i].sum1 == null
							|| rewardInfo[i].sum1 == "") {
						rewardInfo[i].sum1 = " ";
					}
					if (rewardInfo[i].prod_name == null
							|| rewardInfo[i].prod_name == "") {
						rewardInfo[i].prod_name = " ";
					}
					if (rewardInfo[i].order_amount == null
							|| rewardInfo[i].order_amount == "") {
						rewardInfo[i].order_amount = " ";
					}
					if (rewardInfo[i].magt_feet == null
							|| rewardInfo[i].magt_feet == "") {
						rewardInfo[i].magt_feet = " ";
					}
					/* 
					id="${rewardLi.sales_id }
					<td>${rewardLi.sales_name }</td>
					<td>${rewardLi.area }</td>
					<td>${rewardLi.sum_reserve }</td>
					<td>${rewardLi.reserve_kou }</td>
					<td>${rewardLi.reserve_give }</td>
					<td>${rewardLi.reserve_surplus }</td>
					<td>0</td> */
					//自动生成
					var reward_info = '<tr class="default" id="cust_list ">'
							+ '<td>'
							+ rewardInfo[i].sales_name
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].area
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].sum_reserve
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].reserve_kou
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].reserve_give
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].reserve_surplus
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].reserve_change
							+ '</td>'
							+'<td><a href="javascript:void(0);" '
							+'	onclick="reserve_pay('
							+rewardInfo[i].sales_id
							+');" '
							+'	style="outline: none;"> <!-- <i class="glyphicon glyphicon-plus"></i> --> '
							+'		发放 '
							+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0);" '
							+'	onclick="reserve_kou('
									+rewardInfo[i].sales_id
							+');" '
							+'	style="outline: none;"> <!-- <i class="glyphicon glyphicon-plus"></i> --> '
							+'		扣除 '
							+'	</a></td> '
							+ '</tr>';
					$("#reword_yuliu").append(reward_info);
				}
				var reward_info='<tr> '
				+'	<th colspan="9" style="text-align: center;"> '
				+' <button id="reward_search" class="btn btn-info top10" '
				+' 	style="width: 20%" data-toggle="modal" '
				+'	data-target="#modal_caches" onclick="reserve_save()">保存</button> '
				+'	</th> '
				+' </tr> ';
				$("#reword_yuliu").append(reward_info);
			} else {
				var reward_info = '<tr class="default" id="cust_list " >'
						+ '<th colspan="8" style="text-align: center ;">'
						+ " 未查到相关数据" + '</th>' + '</tr>';
				$("#reword_yuliu").append(reward_info);
			}
		},
		error : function(result) {
			alert('系统异常,请稍后再试!');
		}
	});
	});
	
	var sign="1";
	var sales_id=0;
	var reserve_surplus=0;
	
	//发放前检查预留
	function reserve_pay(sales) {
		sales_id=sales;
		  reserve_surplus=$("#"+sales_id).children('td').eq(5).html();
		 if( parseFloat(reserve_surplus)<= parseFloat(0.00)){
			 alert("当前没有可发放的预留!");
			 return false;
		 }
		$('#modal_ach').modal('show');
		$("#sp1").html("奖金发放页面");
		$("#sp2").html("发放金额");
		sign="2";
		
	}
	
	//扣除前检查预留
	function reserve_kou(sales) {
		sales_id=sales;
		 reserve_surplus=$("#"+sales_id).children('td').eq(5).html();
		 if( parseFloat(reserve_surplus)<= parseFloat(0.00)){
			 alert("当前没有可扣除的预留!");
			 return false;
		 }
		$('#modal_ach').modal('show');
		$("#sp1").html("奖金扣除页面");
		$("#sp2").html("扣除金额");
		sign="1";
		
	}
	
	//预留发放或扣除
	function res_pay() {
        var reserve=$("#res_give_num").val().trim();
        var remark=$("#res_give_remark").val().trim();
        var sales_name=$("#"+sales_id).children('td').eq(0).html();
        var area=$("#"+sales_id).children('td').eq(1).html();
        var sum_reserve=$("#"+sales_id).children('td').eq(2).html();
        //进行校验
        if(parseFloat(reserve_surplus)<= parseFloat(0.00)){
        	$('#modal_ach').modal('hide');
        	return false;
        }
	    if( parseFloat(reserve)> parseFloat(reserve_surplus)){
		    alert("预留剩余金额小于"+reserve+",发放失败!");
		    $('#modal_ach').modal('hide');
		    return false;
	    }
	    var s= parseFloat(reserve_surplus)-parseFloat(reserve);
	    $("#"+sales_id).children('td').eq(5).html( s);
	    //对页面数据进行更改
		$("#"+sales_id).children('td').eq(6).html(reserve);
		$("#"+sales_id).children('td').eq(5).html( parseInt(reserve_surplus)-parseInt(reserve));
		$.ajax({
			url : 'reserve_change.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data :{
				"sales_name" : sales_name,
				"area" : area,
				"sign" : sign,
				"sales_id" : sales_id,
				"reserve" :reserve,
				"remark" : remark,
				"sum_reserve" : sum_reserve,
				},
			success : function(data) {
				if(data.status==1){
					alert("预留调整成功！");
					$("#res_give_num").val("");
					$("#res_give_remark").val("");
					window.location.href = "yuliu.action";
				}else{
					alert("更改失败！");
					window.location.href = "yuliu.action";
				}
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});
	}
	//刷新页面
	  function dele(){
		  $("#res_give_num").val("");
			$("#res_give_remark").val("");
			$('#modal_ach').modal('hide');
  }
	//保存按钮点击进行预留发放和扣除信息查询
	function reserve_save(){
		$.ajax({
			url : 'reserve_save.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
             data : {},
			success : function(data) {
				$("#reserve_caches").empty();
				if(data.status==1){
					$("#reserve_caches").empty();
						var reserveInfo = data.rewardList;
						for (var i = 0; i < reserveInfo.length; i++) {
							var reserveInf = '<tr class="default" id="cust_list ">'
								+ '<td>'
								+ reserveInfo[i].sales_id
								+ '</td>'
								+ '<td>'
								+ reserveInfo[i].sales_name
								+ '</td>'
								+ '<td>'
								+ reserveInfo[i].reserve_give
								+ '</td>'
								+ '<td>'
								+ reserveInfo[i].reserve_kou
								+ '</td>'
								+ '</tr>';
							$("#reserve_caches").append(reserveInf);
						}

				} else {
							var reserveInf = '<tr class="default" id="cust_list " >'
									+ '<th colspan="4" style="text-align: center ;">'
									+ " 未查到预留计算信息" + '</th>' + '</tr>';
							$("#reserve_caches").append(reserveInf);
						}
			},
			error : function(result) {
				$("#reserve_caches").empty();
				alert('系统异常,请稍后再试!');
			}
		});
	}
	//确认更改
	function reserve_confirm() {
		var r = confirm("确认保存更改吗？")
		if (r == false) {
			return false;
		}
		$.ajax({
			url : 'reserve_confirm.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
             data : {},
			success : function(data) {
				if(data.status==1){
				alert("更改成功!");
				window.location.href = "yuliu.action";
				}else{
					alert("更改失败!");
					window.location.href = "yuliu.action";
				}
			},
			error : function(result) {
				$("#reserve_caches").empty();
				alert('系统异常,请稍后再试!');
			}
		});
	}
	
	
	//批次分组
	  function subgo(sign) {
		   var size="";
		   if(sign==-1){
			   size=$("#minbatch").val();
		   }
		   if(sign==1){
			   size=$("#maxbatch").val();
		   }
		   $.ajax({
				url : 'reservebatch.action', //后台处理程序     
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
								+' href="search_reserve_list.action?count_batch='
								+rewardInfo[i].count_batch
								+'" target="_top" '
								+'	class="btn btn-danger" style="display: inline-block;">'
								+rewardInfo[i].count_batch 
								+'</a></td>';
							  
						}
						reward_info+='	<td><button onclick="subgo('+1+')">>></button></td></tr></table>';
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
	//页面刷新验证  onbeforeunload   未写
	
</script>
</head>
<body>

	<!-- 奖金发放-团队 -->
	<div class="panel panel-default" style="width: 100%;">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>奖金发放-预留列表
			</h3>
		</div>
		<div class="panel-body" style="overflow:auto;">
			<table class="table cust_table" style="font-size: 10px;">
				<thead>
					<tr class="demo_tr">
						<!-- <th>选择</th> -->
						<th>销售</th>
						<th>地区</th>
						<th>预留总金额</th>
						<th>扣除金额</th>
						<th>已发放</th>
						<th>剩余金额</th>
						<th>已调整</th>
						<!-- 	<th>最终发放</th> -->
						<th>设置</th>

					</tr>
				</thead>
				<tbody id="reword_yuliu">

					<s:if test="#request.status==1 ">
						<s:iterator value="#request.rewardList" var="rewardLi">
							<tr class="default" id="${rewardLi.sales_id }"
								style="cursor: pointer;">
								<td>${rewardLi.sales_name }</td>
								<td>${rewardLi.area }</td>
								<td>${rewardLi.sum_reserve }</td>
								<td>${rewardLi.reserve_kou }</td>
								<td>${rewardLi.reserve_give }</td>
								<td>${rewardLi.reserve_surplus }</td>
								<td>${rewardLi.reserve_change }</td>
								<%--  <td><a
									href="<%=request.getContextPath()%>/jsp/reward/reward_set_1.jsp"><span
										class="glyphicon glyphicon-cog"></span></a></td>  --%>
								<td><a href="javascript:void(0);"
									onclick="reserve_pay(${rewardLi.sales_id });"
									style="outline: none;"> <!-- <i class="glyphicon glyphicon-plus"></i> -->
										发放
								</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0);"
									onclick="reserve_kou(${rewardLi.sales_id });"
									style="outline: none;"> <!-- <i class="glyphicon glyphicon-plus"></i> -->
										扣除
								</a></td>
								<!-- data-toggle="modal" data-target="#modal_ach" -->
								<!-- data-toggle="modal" data-target="#modal_ach" -->
							</tr>
						</s:iterator>
						<tr>
							<th colspan="9" style="text-align: center;">
								<button id="reward_search" class="btn btn-info top10"
									style="width: 20%" data-toggle="modal"
									data-target="#modal_caches" onclick="reserve_save()">预留发放</button>
							</th>
						</tr>
					</s:if>
					<s:elseif test="#request.status==2">
						<tr>
							<th colspan="9" style="text-align: center;">${reward_list }</th>
						</tr>
					</s:elseif>


				</tbody>
			</table>
				<div id="countbatch" style="text-align: center; margin: 15px;">
				<table>
									<tr align="center"><td><button onclick="subgo('-1')"><<</button></td>
										<s:if test="#request.batch==1 ">
											<s:iterator value="#request.reservecount" var="reservecou">
											<td>	<a
													href="search_reserve_list.action?count_batch=${reservecou.count_batch}" target="_top"
													class="btn btn-danger" style="display: inline-block;">${reservecou.count_batch }</a>
											</td>
											</s:iterator>
										</s:if>
										<td><button onclick="subgo('1')">>></button></td></tr>
									</table>
									</div>
									<a><input id="minbatch" type="hidden" value="0"></a>
										<a><input id="maxbatch" type="hidden" value="3"></a>
				                   <%--   <a class="left carousel-control"
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
	<!-- 预留发放弹出框 -->
	<div class="modal" id="modal_ach" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px;">
				<!-- 	<div class="myright-n">

					<div class="row topx myMinh" style="border: 0; padding: 0;">
 -->
				<div class="spjz" style="">
					<ul class="list-group">

						<li class="list-group-item"
							style="color: #a52410; background-color: rgb(245, 245, 245);">
							<h3 class="panel-title" style="color: #a52410;">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"
									style="margin-right: 6px;" id="sp1"></span>
							</h3>
						</li>
						<li class="list-group-item"><span id="sp2">发放金额&nbsp;:</span>
							<input type="number" max="1000" id="res_give_num" value=""
							style="width: 75%; border: none; outline: none;"></li>
						<li class="list-group-item"><span>备注&nbsp;:</span> <input
							type="text" id="res_give_remark" value=""
							style="width: 75%; border: none; outline: none;"></li>
					</ul>
					<div class="row text-center">
						<button style="margin-right: 40px;" data-dismiss="modal"
							onclick="dele()" class="btn btn-lg btn-default">取消</button>
						<!-- <input type="button" class="btn btn-lg" data-dismiss="modal" onclick="show_indiv()" style="background-color: #5bc0de; color: #fff;"> -->
						<button class="btn btn-lg" data-dismiss="modal"
							onclick="res_pay()"
							style="background-color: #5bc0de; color: #fff;">确定</button>
					</div>
				</div>
			</div>
		</div>
		<!-- </div> -->
		<!-- </div> -->
	</div>
	<!-- 确认弹窗 -->
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
											style="margin-right: 6px;"></span>奖金计算核对页面
									</h3>
								</div>
								<div class="panel-body" style="overflow:auto;">
									<table class="table cust_table" id="achievement_li123">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>销售ID</th>
												<th>销售姓名</th>
												<th>预留发放</th>
												<th>预留扣除</th>
											</tr>
										</thead>
										<tbody id="reserve_caches">
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

