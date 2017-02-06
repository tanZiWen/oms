<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>order_edit</title>
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
<script src="/OMS/js/order.js"></script>
<script>
	var n = 1;
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<style type='text/css'>
.dropdown-menu {
	height: auto !important;
}
</style>
<script>
	$(function() {
		var order_type = "";
		var contract_type = "";
		var cust_type="";
		var area = "";
		order_type = ${orderEdit.order_type};
		contract_type = ${orderEdit.contract_type};
		cust_type=${orderEdit.cust_type};
		area = "${orderEdit.area}";
		var cust_id =  ${orderEdit.cust_no};
		var is_id = ${orderEdit.is_id};
		var lp_id =${orderEdit.part_comp};
		var prod_id = ${prod_id};
		
		//alert(document.getElementById("cust_type").value);
		//document.getElementById("cust_name").value = ${orderEdit.cust_no};
		/* document.getElementById("cust_type").value = "2";
		alert(document.getElementById("cust_type").value);
		document.getElementById("cust_na").value = ${orderEdit.cust_no}; */
		$('#cust_type').val(cust_type);
		$('#area').val(area);
		$('#cust_na').val(cust_id);
		$('#order_type').val(order_type);
		$('#contract_type').val(contract_type);
		$('#is_id').val(is_id);
		$('#part_comp').val(lp_id);
		$('#prod_no').val(prod_id);
		
		$('#old_cust_type').val(cust_type);
		$('#old_area').val(area);
		$('#old_cust_na').val(cust_id);
		$('#old_order_type').val(order_type);
		$('#old_contract_type').val(contract_type);
		$('#old_is_id').val(is_id);
		$('#old_part_comp').val(lp_id);
		$('#old_prod_no').val(prod_id);
		
		/* document.getElementById("cust_name").value = ${orderEdit.cust_no};
		document.getElementById("cust_name").value = ${orderEdit.cust_no};
		document.getElementById("cust_name").value = ${orderEdit.cust_no}; */
		
	});
	<%-- if (order_type = '1') {
		document.getElementById("order_type").value = "1";
	} else {
		document.getElementById("order_type").value = "2";
	}
	if (contract_type = '1') {
		document.getElementById("contract_type").value = "1";
	} else {
		document.getElementById("contract_type").value = "2";
	}
	if(cust_type=='1'){
		document.getElementById("cust_type").value = <%=request.getAttribute("cust_type")%>;
	}else{
		$('#cust_type').val(cust_type);
		document.getElementById("cust_type").value = "1";
	} --%>
	function orderdetail(){
		var order_on = $('#order_no').val();
		var order_version = $('#order_version').val();
		
		location.href="order_Detail.action?order_no="+order_on+"&order_version="+order_version;
	}
	
	function orderSave1() {
		var state="";
		var msg ="变更前信息				 变更后信息\n";
		if($('#cust_type').val()!=$('#old_cust_type').val()){
			state="1";
			msg=msg+$('#old_cust_type').find("option:selected").text()+'				'+$('#cust_type').find("option:selected").text()+'\n';
		}
		if($('#area').val()!=$('#old_area').val()){
			state="1";
			msg=msg+$('#old_area').find("option:selected").text()+'				'+$('#area').find("option:selected").text()+'\n';
		}
		if($('#cust_na').val()!=$('#old_cust_na').val()){
			state="1";
			msg=msg+$('#old_cust_na').find("option:selected").text()+'				'+$('#cust_na').find("option:selected").text()+'\n';
		}
		if($('#prod_no').val()!=$('#old_prod_no').val()){
			state="1";
			msg=msg+$('#old_prod_no').find("option:selected").text()+'				'+$('#prod_no').find("option:selected").text()+'\n';
		}
		if($('#part_comp').val()!=$('#old_part_comp').val()){
			state="1";
			msg=msg+$('#old_part_comp').find("option:selected").text()+'				'+$('#part_comp').find("option:selected").text()+'\n';
		}
		if($('#order_type').val()!=$('#old_order_type').val()){
			state="1";
			msg=msg+$('#old_order_type').find("option:selected").text()+'				'+$('#order_type').find("option:selected").text()+'\n';
		}
		if($('#order_amount').val()!=$('#old_order_amount').val()){
			state="1";
			msg=msg+$('#old_order_amount').val()+'				'+$('#order_amount').val()+'\n';
		}
		if($('#contract_type').val()!=$('#old_contract_type').val()){
			state="1";
			msg=msg+$('#old_contract_type').find("option:selected").text()+'				'+$('#contract_type').find("option:selected").text()+'\n';
			
		}
		if($('#contract_no').val()!=$('#old_contract_no').val()){
			state="1";
			msg=msg+$('#old_contract_no').val()+'				'+$('#contract_no').val()+'\n';
		}
		if($('#is_id').val()!=$('#old_is_id').val()){
			state="1";
			msg=msg+$('#old_is_id').find("option:selected").text()+'				'+$('#is_id').find("option:selected").text()+'\n';
		}
		if($('#is_no').val()!=$('#old_is_no').val()){
			state="1";
			msg=msg+$('#old_is_no').val()+'				'+$('#is_no').val()+'\n';
		}
		if($('#prod_diffcoe1').val()!=$('#old_prod_diffcoe1').val()){
			state="1";
			
			msg=msg+$('#old_prod_diffcoe1').val()+'				'+$('#prod_diffcoe1').val()+'\n';
		}
		if($('#pri_fee').val()!=$('#old_pri_fee').val()){
			state="1";
			msg=msg+$('#old_pri_fee').val()+'				'+$('#pri_fee').val()+'\n';
		}
		if($('#acount_fee').val()!=$('#old_acount_fee').val()){
			state="1";
			msg=msg+$('#old_acount_fee').val()+'				'+$('#acount_fee').val()+'\n';
		}
		if($('#buy_fee').val()!=$('#old_buy_fee').val()){
			state="1";
			msg=msg+$('#old_buy_fee').val()+'				'+$('#buy_fee').val()+'\n';
		}
		if($('#start_fee').val()!=$('#old_start_fee').val()){
			state="1";
			msg=msg+$('#old_start_fee').val()+'				'+$('#start_fee').val()+'\n';
		}
		if($('#bank_no').val()!=$('#old_bank_no').val()){
			state="1";
			msg=msg+$('#old_bank_no').val()+'				'+$('#bank_no').val()+'\n';
		}
		if($('#bank_card').val()!=$('#old_bank_card').val()){
			state="1";
			msg=msg+$('#old_bank_card').val()+'				'+$('#bank_card').val()+'\n';
		}
		if($('#cust_email').val()!=$('#old_cust_email').val()) {
			state="1";
			msg=msg+$('#old_cust_email').val()+'				'+$('#cust_email').val()+'\n';
		}
		if($('#mail_address').val()!=$('#old_mail_address').val()) {
			state="1";
			msg=msg+$('#old_mail_address').val()+'				'+$('#mail_address').val()+'\n';
		}
		if($('#work_address').val()!=$('#old_work_address').val()) {
			state="1";
			msg=msg+$('#old_work_address').val()+'				'+$('#work_address').val()+'\n';
		}
		if($('#cust_cell').val()!=$('#old_cust_cell').val()) {
			state="1";
			msg=msg+$('#old_cust_cell').val()+'				'+$('#cust_cell').val()+'\n';
		}
		if($('#extra_award').val()!=$('#old_extra_award').val()) {
			state="1";
			msg=msg+$('#old_extra_award').val()+'				'+$('#extra_award').val()+'\n';
		}
		if($('#first_pay_time').val()!=$('#old_first_pay_time').val()) {
			state="1";
			msg=msg+$('#old_first_pay_time').val()+'				'+$('#first_pay_time').val()+'\n';
		}
		if($('#comment').val()!=$('#old_comment').val()) {
			state="1";
			msg=msg+$('#old_comment').val()+'				'+$('#comment').val()+'\n';
		}
		if($('#cust_address').val() != $('#old_cust_address').val()) {
			state="1";
			msg=msg+$('#old_cust_address').val()+'				'+$('#cust_address').val()+'\n';
		}
		if($('#entry_time').val() != $('#old_entry_time').val()) {
			state="1";
			msg=msg+$('#old_entry_time').val()+'				'+$('#entry_time').val()+'\n';
		}
	$('#message').val(msg);
	showLoad(); 
		$.ajax({
			url : 'orderSave.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : $('#formid').serialize(),
			success : function(data) {
				if(data.status==1){
					alert("更改订单成功!");
					hideLoad();
					var order_no = $("#order_no").val();
					var order_version = $("#order_version").val();
					window.location.href = "orderList.action";
				}else{
					alert(data.msg);
					hideLoad();
				}
				
			},
			error : function(result) {
				alert("系统异常！");
			}
		});

	}
	//订单重置
	function order_reset(){
		var email_id = $("#email_id").val();
		
		$.ajax({
			url : "order_reset.action",
			type : "post",
			dataType : "json",
			data :{"email_id":email_id},

			success : function(data) {
				if(data.desc==1){
					var list = data.content;
					$("#cust_type").val(list.cust_type);
					$("#cust_no").val(list.cust_no);
					$("#prod_id").val(list.prod_id);
					$("#part_comp").val(list.part_comp);
					$("#order_type").val(list.order_type);
					$("#order_amount").val(list.order_amount);
					$("#contract_type").val(list.contract_type);
					$("#contract_no").val(list.contract_no);
					$("#is_id").val(list.is_id);
					$("#is_no").val(list.is_no);
					$("#prod_diffcoe").val(list.prod_diffcoe);
					$("#pri_fee").val(list.pri_fee);
					$("#acount_fee").val(list.acount_fee);
					$("#buy_fee").val(list.buy_fee);
					$("#start_fee").val(list.start_fee);
					$("#bank_no").val(list.bank_no);
					$("#bank_card").val(list.bank_card);
					$("#remark").val(list.remark);
					
				}else{
					alert("未找到历史数据")
				}
			},error: function() {
				var msg = escape(escape("数据异常"));
				location.href="error.action?msg="+msg
			}
		});
	}
	function changeValue() {
		var cust_type = $("#cust_type").val();
		var cust_name = $("#cust_na").val().trim();
		$.ajax({
			url : 'order_cust.action',
			type : 'post',
			dataType : 'json',
			data : {
				'cust_type' : cust_type,
				'cust_name' : cust_name,
			},
			success : function(data) {
				$("#cust_na").empty();
				if (data.status == 1) {
					var custOrgListInfo = data.custOrgList;
					for (var i = 0; i < custOrgListInfo.length; i++) {
						if (i == 0) {
							$("#cust_na").prepend(
									"<option value='"+custOrgListInfo[i].cust_id+"'>"
											+ custOrgListInfo[i].cust_name
											+ "</option>");
						} else {
							$("#cust_na").append(
									"<option value='"+custOrgListInfo[i].cust_id+"'>"
											+ custOrgListInfo[i].cust_name
											+ "</option>");
						}
					}
				} else {
					var reward_info = '<tr class="default" id="cust_list " >'
							+ '<th colspan="12" style="text-align: center ;">'
							+ " 未查到相关数据" + '</th>' + '</tr>';
					$("#cust_na").append(cust_org_info);
				}
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});
	}
	function kycselect(){
		var cust_no =$('#cust_na').val() ;
		
		$.ajax({
			url : "/OMS/kycselect.action",
			type : "post",
			dataType : "json",
			data : {
				"cust_no" : cust_no,
				"cust_type":$('#cust_type').val()
			},// 你的formid

			success : function(data) {
							if (data.desc == 1) {
								$('#kyc').val("");
					var lis = data.list;
					
					
					
					//$(".btn btn-primary").attr("data-toggle","modal");
					//$("btnnn").click();
				} else {
					$('#kyc').val('未找到当前客户的kyc');
					$('#submit').hide();
				}
			},
			error : function() {
				var msg = escape(escape("数据查询异常"));
				location.href = "/OMS/error.action?msg=" + msg
			}
		});
		
	}

</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->
	<%@ include file="../header.jsp"%>




	<!--主体-->
	<form action="orderSave.action" method="post" id="formid">
		<div class="container m0 bod top70" id="zt">
			<div class="row">
				<div class="col-md-6 t0b0 ">
					<ol class="breadcrumb t0b0">
						<li><a href="/OMS">首页</a></li>
						<li class="active">订单编辑</li>
					</ol>
				</div>
				<div class="col-md-6 t0b0 txtr"></div>
			</div>

			<div class="row top10 mym">
				<!--左-->

				<div class="col-md-4 myleft" style="width: 25%;">
					<div class="myleft-n">

						<a href="#" data-toggle="modal" data-target="#exampleModal2">
							<img id="tou" src="image/person.png" class="f imgr20">
						</a>
						<div class="f imgf20">
							<h4>订单管理</h4>

						</div>

						<div class="df"></div>
					</div>
					<div class="df"></div>
					<div class="myleft-n">

						<div class="alert alert-warning top20" role="alert"
							style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
							<i class="fa fa-lightbulb-o"></i> 订单功能介绍 <br>

						</div>



					</div>
					<div class="df"></div>
				</div>

				<!--end 左-->
				<!--右-->
				<div class="col-md-8 myright" style="width: 75%;">
					<div class="myright-n">
						<div id="submit"  class="myNav row" onload="go()">

							<a  href="javascript:orderSave1()"><i
								class="glyphicon glyphicon-floppy-saved"></i> 提交审批 </a>
							<a href="javascript:orderdetail()"><i
								class="glyphicon glyphicon-floppy-saved"></i> 取消 </a>
							<a href="javascript:order_reset()"><i
								class="glyphicon glyphicon-floppy-saved"></i> 重置 </a>

						</div>
							
						<div class="row topx myMinh" style="">

							<div class="spjz" style="">

								<div class="panel panel-default" style="width: 100%;">
			<input type="hidden" name="old_order_amount" id="old_order_amount" value="${orderEdit.order_amount }">
			<input type="hidden" name="old_contract_no" id="old_contract_no" value="${orderEdit.contract_no }">
			<input type="hidden" name="old_is_no" id="old_is_no" value="${orderEdit.is_no }">
			<input type="hidden" name="old_prod_diffcoe1" id="old_prod_diffcoe1" value="${orderEdit.prod_diffcoe }">
			<input type="hidden" name="old_pri_fee" id="old_pri_fee" value="${orderEdit.pri_fee }">
			<input type="hidden" name="old_acount_fee" id="old_acount_fee" value="${orderEdit.acount_fee }">
			<input type="hidden" name="old_buy_fee" id="old_buy_fee" value="${orderEdit.buy_fee }">
			<input type="hidden" name="old_start_fee" id="old_start_fee" value="${orderEdit.start_fee }">
			<input type="hidden" name="old_bank_no" id="old_bank_no" value="${orderEdit.bank_no }">
			<input type="hidden" name="old_bank_card" id="old_bank_card" value="${orderEdit.bank_card }">
			<input type="hidden" name="old_remark" id="old_remark" value="${orderEdit.remark }">
			<input type="hidden" name="email_id" id="email_id" value="${orderEdit.email_id }">
			<input type="hidden" name="old_cust_email" id="old_cust_email" value="${orderEdit.cust_email }">
			<input type="hidden" name="old_work_address" id="old_work_address" value="${orderEdit.work_address }">
			<input type="hidden" name="old_mail_address" id="old_mail_address" value="${orderEdit.mail_address }">
			<input type="hidden" name="old_cust_cell" id="old_cust_cell" value="${orderEdit.cust_cell }">
			<input type="hidden" name="old_extra_award" id="old_extra_award" value="${orderEdit.extra_award }">
			<input type="hidden" name="old_first_pay_time" id="old_first_pay_time" value="${orderEdit.first_pay_time }">
			<input type="hidden" name="old_extra_award" id="old_extra_award" value="${orderEdit.extra_award }">
			<input type="hidden" name="old_comment" id="old_comment" value="${orderEdit.comment }">
			<input type="hidden" name="old_cust_address" id="old_cust_address" value="${orderEdit.cust_address }">
			<input type="hidden" name="old_entry_time" id="old_entry_time" value="${orderEdit.entry_time }">
			<input type="hidden" name="message" id="message" value="">
									<ul class="list-group">
										<li class="list-group-item"
											style="background-color: rgb(245, 245, 245);">
											<h3 class="panel-title" style="color: #a52410;">
												<span class="glyphicon glyphicon-edit" aria-hidden="true"
													style="margin-right: 6px;"></span>订单管理

											</h3>

										</li>
										<li class="list-group-item">客户类型：<%-- <input type="text" id="cust_name" name="cust_name"
										value="${orderEdit.cust_name }"
										style="border: none; width: 90%; color: #333; outline: none;"> --%>
											<select onChange="changeF();" name="cust_type" id="cust_type"
											style="outline: 0; width: 90px;">
												<option value="1">个人客户</option>
												<option value="2">机构客户</option>
										</select>
										<select onChange="changeF();" name="old_cust_type" id="old_cust_type"
											style="display:none; outline: 0; width: 90px;">
												<option value="1">个人客户</option>
												<option value="2">机构客户</option>
										</select>
										</li>
										<%-- <li class="list-group-item">
										<button class="glyphicon glyphicon-floppy-saved" onclick="find_cust()">查找</button>
											<input id="find" value="搜索客户姓名"  style="border:none;"
											onblur="if(this.value=='')this.value=this.defaultValue"
											onfocus="if(this.value==this.defaultValue) this.value=''"/>
											
										</li>
										<li class="list-group-item">客户名称：
											<select name="cust_na" id="cust_na" onchange="kycselect()"
											style="outline: 0; width: 90px;" data-provide="typeahead"
											data-items="4">
												<s:iterator value="#request.custSelectList" var="custSe">
													<option value="${custSe.cust_id }">${custSe.cust_name }</option>
												</s:iterator>
												<s:iterator value="#request.orgSelectList" var="orgSe">
													<option value="${orgSe.org_id }">${orgSe.org_name }</option>
												</s:iterator>
										</select>  --%>
										<%-- <input id="kyc" style="border:0px; color:red;"/>
										<select name="old_cust_na" id="old_cust_na" onchange="kycselect()"
											style="display:none; outline: 0; width: 90px;" data-provide="typeahead"
											data-items="4">
												<s:iterator value="#request.custSelectList" var="custSe">
													<option value="${custSe.cust_id }">${custSe.cust_name }</option>
												</s:iterator>
												<s:iterator value="#request.orgSelectList" var="orgSe">
													<option value="${orgSe.org_id }">${orgSe.org_name }</option>
												</s:iterator>
										</select> 
										</li> --%>
										<li class="list-group-item item_border">
											 
					                           地区
					                           <select name="area" id="area">
					                             <s:iterator value="#request.areaList" var ="con">
				                             		<option value="${con.dict_value }" selected="selected">${con.dict_name }</option>
					                            </s:iterator>
					                           </select>
					                          
					                          <select name="old_area" id="old_area" onchange="kycselect()"
													style="display:none; outline: 0; width: 90px;" data-provide="typeahead"
													data-items="4">
												 <s:iterator value="#request.areaList" var ="con">
				                             		<option value="${con.dict_value }" selected="selected">${con.dict_name }</option>
					                            </s:iterator>
											</select>  
					                     
										</li>
										<li class="list-group-item item_border">产品名称： <%-- <input
											id="prod_name" name="prod_name" type="text"
											value="${orderEdit.prod_name }"
											style="border: none; width: 90%; color: #333; outline: none;"> --%>
											<select name="prod_no" id="prod_no"
											style="outline: 0; width: 90px;" onchange="orderchange()">
												<s:iterator value="#request.prod_list" var="prod">
													<option value="${prod.prod_id }">${prod.prod_name }</option>
												</s:iterator>
										</select>
										<select name="old_prod_no" id="old_prod_no"
											style="display:none; outline: 0; width: 90px;" onchange="orderchange()">
												<s:iterator value="#request.prod_list" var="prod">
													<option value="${prod.prod_id }">${prod.prod_name }</option>
												</s:iterator>
										</select>
											<input type="hidden" name="order_version" id="order_veision"
											value="${orderEdit.order_version }">
											<input type="hidden" name="order_no" id="order_no"
											value="${orderEdit.order_no }">
										</li>
										<li class="list-group-item item_border">合伙企业： <%-- <input
											id="prod_name" name="prod_name" type="text"
											value="${orderEdit.prod_name }"
											style="border: none; width: 90%; color: #333; outline: none;"> --%>
											<select name="part_comp" id="part_comp"
											style="outline: 0; width: 90px;">
												<s:iterator value="#request.lp_list" var="lp">
													<option value="${lp.lp_id }">${lp.partner_com_name }</option>
												</s:iterator>
										</select>
										<select name="old_part_comp" id="old_part_comp"
											style=" display:none; outline: 0; width: 90px;">
												<s:iterator value="#request.lp_list" var="lp">
													<option value="${lp.lp_id }">${lp.partner_com_name }</option>
												</s:iterator>
										</select>
											<input type="hidden" name="order_version" id="order_version"
											value="${orderEdit.order_version }">
										</li>
										<li class="list-group-item item_border">订单类型： <select
											name="order_type" id="order_type"
											style="outline: 0; width: 90px;">
												<option value="1">直销订单</option>
												<option value="2">代销订单</option>
										</select>
										 <select
											name="old_order_type" id="old_order_type"
											style=" display:none; outline: 0; width: 90px;">
												<option value="1">直销订单</option>
												<option value="2">代销订单</option>
										</select>
										</li>
										<li class="list-group-item item_border" style="border-bottom: 0;">下单金额：
										<input type="text"
											value="${orderEdit.order_amount }" id="order_amount"
											name="order_amount" oninput="qianfenwei('order_amount')"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<!-- <li class="list-group-item item_border" style="border-bottom: 0;">
                         			  指导标费:<input id="pri_fee" name="pri_fee" readOnly="true"  required="required"  style="border: none; width: 90%; color: #333; outline: none;">
                         				</li> -->
										<li class="list-group-item item_border">合同类型： <select
											name="contract_type" id="contract_type"
											style="outline: 0; width: 90px;">
												<option value="1">A类型</option>
												<option value="2">B类型</option>
												<option value="3">其他</option>
										</select>
										 <select
											name="old_contract_type" id="old_contract_type"
											style=" display:none; outline: 0; width: 90px;">
												<option value="1">A类型</option>
												<option value="2">B类型</option>
										</select>
										</li>
										<li class="list-group-item item_border">合同号 <input
											type="text" value="${orderEdit.contract_no }"
											id="contract_no" name="contract_no"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">证件复印件：
										<select id="is_id" name="is_id" >
											<option value="0">是</option>
                          					<option value="1">否</option>
										</select>
										<select id="old_is_id" name="old_is_id" style="display:none;">
											<option value="0">是</option>
                          					<option value="1">否</option>
										</select>
										<!--  <input
											id="is_id" name="is_id" type="checkbox"
											style="margin-left: 20px;"> -->
										</li>
										<li class="list-group-item item_border">证件号： <input
											id="is_no" name="is_no" type="text"
											required="required" value="${orderEdit.id_no }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li >
										<li class="list-group-item item_border">产品系数：
										<input id="prod_diffcoe1" name="prod_diffcoe1" value="${orderEdit.prod_diffcoe }" readOnly="true"  type="text" style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">指导标费： <input
											id="pri_fee" name="pri_fee"  readOnly="true"
											required="required" value="${orderEdit.pri_fee }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">折后标费： <input
											id="acount_fee" name="acount_fee" oninput="qianfenwei('acount_fee')"
											required="required" value="${orderEdit.acount_fee }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<%-- <li class="list-group-item item_border">管理费： <input
											id="cust1_idnum" name="cust1_idnum" type="text"
											readonly="readonly" required="required" value="5000"
											${orderEdit.cust_idnum }
										style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">管理费调整： <input
											id="new_cust_idnum" name="new_cust_idnum" 
											required="required" value=""
											style="border: none; width: 85%; color: #333; outline: none;">
										</li> --%>
										<li class="list-group-item item_border"
											style="border-bottom: 0;">认购费： <input 
											required="required" id="buy_fee" name="buy_fee" oninput="qianfenwei('buy_fee')"
											value="${orderEdit.buy_fee }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">开办费： <input
											id="start_fee" name="start_fee" oninput="qianfenwei('start_fee')"
											required="required" value="${orderEdit.start_fee }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">开户行： <input
											id="bank_no" name="bank_no" type="text"
											value="${orderEdit.bank_no }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">银行卡号： <input
											id="bank_card" name="bank_card" type="text"
											required="required" value="${orderEdit.bank_card }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">客户邮箱： <input
											id="cust_email" name="cust_email" type="text"
											required="required" value="${orderEdit.cust_email }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">邮寄地址： <input
											id="mail_address" name="mail_address" type="text"
											required="required" value="${orderEdit.mail_address }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">工作地址： <input
											id="work_address" name="work_address" type="text"
											required="required" value="${orderEdit.work_address }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">身份证地址： <input
											id="cust_address" name="cust_address" type="text"
											required="required" value="${orderEdit.cust_address }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">联系电话： <input
											id="cust_cell" name="cust_cell" type="text"
											required="required" value="${orderEdit.cust_cell }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">额外奖励： <input
											id="extra_award" name="extra_award" type="text"
											required="required" value="${orderEdit.extra_award }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">首次缴款日期：<input
											id="first_pay_time" name="first_pay_time" type="text" class="form_datetime"
											required="required" value="${orderEdit.first_pay_time }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">下单日期：<input
											id="entry_time" name="entry_time" type="text" class="form_datetime"
											required="required" value="${orderEdit.entry_time }"
											style="border: none; width: 90%; color: #333; outline: none;">
										</li>
										<li class="list-group-item item_border">备注： <input
											id="comment" name="comment" type="text"
											value="${orderEdit.comment }"
											style="border: none; width: 90%; color: #333; outline: none;">
											<input type="hidden" value="${orderEdit.order_no }"
											name="order_no" id="order_no">
											<input type="hidden" name="cust_no" id="cust_no" value="${orderEdit.cust_no }">
										</li>
									</ul>
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
						<li>Copyright © 2016</li>
					</ul>
					<ul class="list-inline text-right">
						<li></li>
					</ul>
				</nav>
			</div>
	</form>
</body>
</html>