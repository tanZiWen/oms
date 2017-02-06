<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	String now_date = df.format(new Date());
%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>order_detail_org</title>
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

<script>
	$(function() {
		//var state= $('#prod_status > .btn.active').val();
		$('#myTab li:eq(1) a').tab('show');
		
	});
	//变更记录详情查询
	function change_detail(order_no,order_version,sales_id){
		$.ajax({
			url : 'change_detail.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'sales_id':sales_id,
				'order_no' : order_no,
				'order_version' : order_version
				
			},
			success : function(data) {
				if(data.status==1){
					
					var list = data.change_list;
					$('#change_cust_name').val(list.cust_name);
					$('#change_order_no').val(list.order_no);
					$('#change_pay_amount').val(list.partner_com_name);
					$('#change_creat_time').val(list.create_time);
					$('#change_is_no').val(list.is_no);
					$('#change_pri_fee').val(list.pri_fee);
					$('#change_prod_name').val(list.prod_name);
					$('#modal_change').modal('show');
				}else{
					$('#change_cust_name').val('');
					$('#change_order_no').val('');
					$('#change_pay_amount').val('');
					$('#change_creat_time').val('');
					$('#change_is_no').val('');
					$('#change_pri_fee').val('');
					$('#change_part_comp').val('');
					$('#modal_change').modal('show');
				}
			},
			error : function() {
				var msg = escape(escape("数据查询异常"));
				location.href = "/OMS/error.action?msg=" + msg+"&code=104&url="
			}
		});
	}
	//审核通过
	function approve(check,order_no,order_version){
		$.ajax({
			url : 'approve.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'check' : check,
				'order_no' : order_no,
				'order_version' : order_version
				
			},
			success : function() {
				alert('审批操作成功');
				var order_no = $("#order_no").val();
				window.location.href = "order_Detail.action?order_no=" + order_no
				+ "&order_version=" + order_version;

			},
			error : function(result) {
				alert('操作失败!');
			}
		});
	}
			//打款计划审批
			function play_approve(pay_state,payment_id){
				$.ajax({
					url : 'play_approve.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'pay_state' : pay_state,
						'payment_id':payment_id
						
					},
					success : function() {
						
						var order_no = $("#order_no").val();
						var order_version = $('#order_version').val();
						
						 window.location.href = "order_Detail.action?order_no=" + order_no
						+ "&order_version=" + order_version; 

					},
					error : function(result) {
						alert('操作失败！!');
					}
				});
			}
			//缴款计划
			function call_approve(pay_state,payment_id){
				$.ajax({
					url : 'call_approve.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'pay_state' : pay_state,
						'payment_id' : payment_id
					},
					success : function() {
						
						var order_no = $("#order_no").val();
						var order_version = $('#order_version').val();
						window.location.href = "order_Detail.action?order_no=" + order_no
						+ "&order_version=" + order_version;

					},
					error : function(result) {
						alert('操作失败！!');
					}
				});
			}
			//缴款删除
			function del_call_payment(payment_id){
				$.ajax({
					url : 'del_call_payment.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						
						'payment_id' : payment_id
					},
					success : function() {
						alert("删除成功")
						var order_no = $("#order_no").val();
						var order_version = $('#order_version').val();
						window.location.href = "order_Detail.action?order_no=" + order_no
						+ "&order_version=" + order_version;

					},
					error : function(result) {
						alert('操作失败！!');
					}
				});
			}
			//打款删除
			function del_payment(payment_id){
				$.ajax({
					url : 'del_payment.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						
						'payment_id' : payment_id
					},
					success : function() {
						alert("删除成功")
						var order_no = $("#order_no").val();
						var order_version = $('#order_version').val();
						window.location.href = "order_Detail.action?order_no=" + order_no
						+ "&order_version=" + order_version;

					},
					error : function(result) {
						alert('操作失败！!');
					}
				});
			}
	/*  缴款计划 */
	function doOrderPlay() {
		var pay_amount_play = $("#pay_amount_play").val();
		var stop_time = $("#stop_time").val();
		var order_pay_step = $("#order_pay_step").val();
		var pay_per = $("#pay_per").val();
		var order_no = $("#order_no").val();
		var order_version = $("#order_version").val();
		$.ajax({
			url : 'do_Play.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'pay_amount' : pay_amount_play,
				'stop_time' : stop_time,
				'order_pay_step' : order_pay_step,
				'pay_per' : pay_per,
				'order_no' : order_no,
				'order_version' : order_version,
			},
			success : function() {
				alert("新建缴款计划成功!");
				var order_no = $("#order_no").val();
				window.location.href = "order_Detail.action?order_no=" + order_no
				+ "&order_version=" + order_version;

			},
			error : function(result) {
				alert('新建失败，请确认该计划是否已存在！!');
			}
		});
	}

	/* 付款 */
	function show_pay() {
		var cust_no = $("#cust_no").val();
		var order_no = $("#order_no").val();
		var cust_type = $("#cust_type").val();
		var pay_amount = $("#pay_amount").val();
		var pay_time = $("#pay_time").val();
		var transaction_type=$("#transaction_type").val();
		var pay_step = $("#pay_step").val();
		if (cust_no ==null ||cust_no == "") {
			alert("未能获取到客户ID");
		}
		if(transaction_type=="0"){
			var remark=$("#remark").val();
		}else{
			var remark="无";
		}
		if (order_no ==null ||order_no == "") {
			alert("未能获取到订单号");
			return ;
		}
		if (cust_type ==null ||cust_type == "") {
			alert("未能获取到客户类型");
			return ;
		}
		if (pay_amount ==null ||pay_amount == "") {
			alert("打款金额不能为空");
			return ;
		}
		if (pay_time ==null ||pay_time == "") {
			alert("打款时间不能为空");
			return ;
		}
		if (pay_step ==null ||pay_step == "") {
			alert("打款阶段不能为空");
			return ;
		}
		showLoad()
		$.ajax({
			url : 'payment.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'cust_no' : cust_no,
				'order_no' : order_no,
				'cust_type' : cust_type,
				'remark':remark,
				'pay_amount' : pay_amount,
				'transaction_type' :transaction_type,
				'pay_time' : pay_time,
				'pay_step' : pay_step,
				'order_version':$('#order_version').val()
			},
		success : function() {
			alert("新建打款成功!");
			hideLoad();
			var order_no = $("#order_no").val();
			var order_version=$("#order_version").val();
				window.location.href = "order_Detail.action?order_no=" + order_no
				+ "&order_version=" + order_version;

			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			} 
		});
	}
	/* 退款 */
	function show_refund() {
		var cust_no = $("#cust_no").val();
		var order_no = $("#order_no").val();
		var order_version = $('#order_version').val();
		var cust_type = $("#cust_type").val();
		var refund_amount = $("#refund_amount").val();
		var refund_time = $("#refund_time").val();
		
		$.ajax({
			url : 'refund.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'cust_no' : cust_no,
				'order_no' : order_no,
				'cust_type' : cust_type,
				'pay_amount' : refund_amount,
				'pay_time' : refund_time,
				'order_version':order_version,
				'pay_step':$('#pay_step1').val()
			},
			success : function() {
				alert("新建退款成功!");
				var order_no = $("#order_no").val();
				var order_version=$("#order_version").val();
				window.location.href ="order_Detail.action?order_no=" + order_no
				+ "&order_version=" + order_version;

			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});
	}
	//初始化设置业绩分配文本是否可用
	window.onload=function(){
		$(".a").attr("contentEditable","true");
		$(".b").attr("contentEditable","false");
	}
	//点击事件设置业绩分配文本是否可用
	function show_ms(obj){
		var a=obj;
		if (a=="1"){
			 Achievement_search(a); 
			 $(".a").attr("contentEditable","true");
			$(".b").attr("contentEditable","false");
			 refresh.location.reload(true);  
			
			}
		if (a=="2"){
			 Achievement_search(a); 
			$(".a").attr("contentEditable","false");
			$(".b").attr("contentEditable","true");
			 refresh.location.reload(true); 
		}
	/* 	contentEditable="true"	 */
	}
	//打款类型选择
	function paychange(){
		var transaction_type=$("#transaction_type").val();
		if(transaction_type=="0"){
			$("#remark").attr("type","text");
		}else{
			$("#remark").attr("type","hidden");
		}
	}

	
	//业绩分配
   function achievement(){
	   var a = JSON.parse("{\"data\":[]}");
	   var rows = document.getElementById("achievement_li123").rows.length; // 获得行数(包括thead)
		var colums = document.getElementById("achievement_li123").rows[0].cells.length; // 获得列数
	   for (var i = 1; i <rows; i++) {
	   var achievementObj = new Object();
	   
	   achievementObj.sales_id = document.getElementById("achievement_li123").rows[i].cells[1].innerHTML;
	   achievementObj.magt_fee = document.getElementById("achievement_li123").rows[i].cells[2].innerHTML;
	   achievementObj.sales_point = document.getElementById("achievement_li123").rows[i].cells[3].innerHTML;
	   a.data.push(achievementObj);
	   }
	   var achievementObj1 = new Object();
	   achievementObj1.order_version =${order_list.order_version };
	   achievementObj1.order_no = ${order_list.order_no };
	   a.data.push(achievementObj1);
		var obj=JSON.stringify(a);
		$.ajax({
			url : 'achievementChan.action',
			type : "post",
			dataType : "json",
			data : {"data":obj},// 你的formid
			success : function(data) {
				if(data.desc==1){
					alert("更改成功!");
					var order_no = $("#order_no").val();
					var order_version=$("#order_version").val();
					var order_no = $("#order_no").val();
					var order_version=$("#order_version").val();
					window.location.href ="order_Detail.action?order_no=" + order_no
					+ "&order_version=" + order_version;

				}else{
					alert("添加失败");
				}
			},error: function() {
				var msg = escape(escape("数据异常"));
				location.href="error.action?msg="+msg
			}
		});
   }
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
  function dele(){
	  var order_no = $("#order_no").val();
		var order_version=$("#order_version").val();
			window.location.href = "order_Detail.action?order_no=" + order_no
			+ "&order_version=" + order_version;
  }
  //动态更新业绩
  function Achievement_search(obj) {
	  var order_no = $("#order_no").val();
		var order_version=$("#order_version").val();
		var a=obj;
		$.ajax({
					url : 'SearchAchievement.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'order_no' : order_no,
						'order_version' : order_version,
					},
					success : function(data) {
						$("#achievement_info").empty();
						if (data.status == 1) {
							var achievementInfo = data.list;
							for (var i = 0; i < achievementInfo.length; i++) {				
								//自动生成
								var prod_info = '<tr class="default">'
										+ '<td>'
										+ achievementInfo[i].sales_name
										+ '</td>'
										+ '<td style="display: none;">'
										+ achievementInfo[i].sales_id
										+ '</td>'
										+ '<td class="a">'
										+ achievementInfo[i].magt_fee
										+ '</td>'
										+ '<td class="b">'
										+ achievementInfo[i].sales_point
										+ '</td>'
										+ '</tr>';
								$("#achievement_info").append(prod_info);
							}
							if (a=="1"){
								 $(".a").attr("contentEditable","true");
								$(".b").attr("contentEditable","false");
								 refresh.location.reload(true);  
								
								}
							if (a=="2"){ 
								$(".a").attr("contentEditable","false");
								$(".b").attr("contentEditable","true");
								 refresh.location.reload(true); 
							}
						} else {
							var prod_info = '<tr class="default" id="cust_list " >'
									+ '<th colspan="4" style="text-align: center;">'
									+" 未查到业绩信息"
									+ '</th>'
									+ '</tr>';
							$("#achievement_info").append(prod_info);
						}
					},
					error : function(result) {
						alert('系统异常,请稍后再试!');
					}
				});
	}
//动态加载奖金信息
  function payment_search(obj) {
	  var order_no = $("#order_no").val();
		var order_version=$("#order_version").val();
		var a=obj;
		$.ajax({
					url : 'SearchAchievement.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'order_no' : order_no,
						'order_version' : order_version,
					},
					success : function(data) {
						$("#achievement_info").empty();
						if (data.status == 1) {
							var achievementInfo = data.list;
							for (var i = 0; i < achievementInfo.length; i++) {				
								//自动生成
								var prod_info = '<tr class="default">'
										+ '<td>'
										+ achievementInfo[i].sales_name
										+ '</td>'
										+ '<td style="display: none;">'
										+ achievementInfo[i].sales_id
										+ '</td>'
										+ '<td class="a">'
										+ achievementInfo[i].magt_fee
										+ '</td>'
										+ '<td class="b">'
										+ achievementInfo[i].sales_point
										+ '</td>'
										+ '</tr>';
								$("#achievement_info").append(prod_info);
							}
							if (a=="1"){
								 $(".a").attr("contentEditable","true");
								$(".b").attr("contentEditable","false");
								 refresh.location.reload(true);  
								
								}
							if (a=="2"){ 
								$(".a").attr("contentEditable","false");
								$(".b").attr("contentEditable","true");
								 refresh.location.reload(true); 
							}
						} else {
							var prod_info = '<tr class="default" id="cust_list " >'
									+ '<th colspan="4" style="text-align: center;">'
									+" 未查到业绩信息"
									+ '</th>'
									+ '</tr>';
							$("#achievement_info").append(prod_info);
						}
					},
					error : function(result) {
						alert('系统异常,请稍后再试!');
					}
				});
	}
	function freezeOrder() {
		var order_no = $('#order_no').val();
		var order_version = $('#order_version').val();
		$.ajax({
			url : 'giveup_call.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'order_no' : order_no,
				'order_version' : order_version,
			},
			success : function(data) {
				if(data.stauts==1){
					alert("操作成功");
				}
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
			
		});
		$("#submitHint").modal('show');
	}
	function refresh() {
		var order_no = $('#order_no').val();
		var order_version = $('#order_version').val();
		window.location.href = "order_Detail.action?order_no=" + order_no
		+ "&order_version=" + order_version;
	}
	function giveup_call(){
		$('#freezeOrder').modal('show');
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
					<li><a href="/OMS">首页</a></li>
					<li class="active">订单</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="#"> <img id="tou" src="image/person.png"
						class="f imgr20">
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
						<i class="glyphicon glyphicon-search"></i> 订单基本信息<br>

					</div>
					<ul class="list-group">
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							客户名称：${order_list.cust_name } <input type="hidden" name="cust_no"
							id="cust_no" value="${order_list.cust_no }"> <input
							type="hidden" name="cust_type" id="cust_type"
							value="${order_list.cust_type }"></li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							订单编号：${order_list.order_no }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							订单类型：${zidian1 }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							合同类型：${zidian2 }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							合同号：${order_list.contract_no }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							身份证号：${order_list.id_no }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							身份证地址：${order_list.cust_address }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							邮寄地址：${order_list.mail_address }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							电子邮箱：${order_list.cust_email }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							首次缴款日期：${order_list.first_pay_time }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							指导标费：${order_list.pri_fee }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							合伙企业：${order_list.partner_com_name }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							折后标费：${order_list.acount_fee }</li>
						<li class="list-group-item" style="border-top: 0;"><i
							class="fa fa-user-secret"></i>&nbsp; 开户行：${order_list.bank_no }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							银行卡号：${order_list.bank_card }</li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							备注：${order_list.comment }</li>

					</ul>
				</div>
				<div class="df"></div>
			</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" 
               aria-hidden="true">×
            </button>
            <h4 class="modal-title" id="myModalLabel">
               订单审核
            </h4>
         </div>
         <div class="modal-body">
            	是否确定审核通过该订单？
         </div>
         <div class="modal-footer" align="center">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">取消
            </button>
            <button type="button" class="btn btn-success" onclick="approve('pass','${order_list.order_no}','${order_list.order_version}')">通过审核</button>
            <button type="button" class="btn btn-danger" onclick="approve('reject','${order_list.order_no}','${order_list.order_version}')">驳回</button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div>

			<!--end 左-->
			<!--右<%=request.getContextPath()%>/jsp/cust/cust_add.jsp-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<div class="myNav row">
						<s:if test="#request.role_id==5">
						<s:if test="#request.order_list.is_checked==1">
						<span id='prod_check' style="cursor: pointer; margin-left: 10px;" data-toggle="modal" data-target="#myModal"><i
							class="glyphicon glyphicon-floppy-saved"></i> 审核通过</span>
						</s:if>
						</s:if>
						<s:if test="#request.role_id==4">
							<a href="javascript:void(0)"
								onclick="order_detail('${order_list.order_no}','${order_list.order_version }')"
								style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
								编辑 </a>
							<!-- data-toggle="modal"  data-target="#modal_remit" -->
							<!-- <a href="<%=request.getContextPath()%>/jsp/order/order_dakuan.jsp" style="outline: none;"><i class="glyphicon glyphicon-plus"></i> 确认打款</a> -->
							<a href="#" data-toggle="modal" data-target="#modal_ach"
								style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
								业绩分配</a>
						</s:if>
						<s:if test="#request.role_id==15">
							<a href="#" data-toggle="modal" data-target="#modal_plan"
								style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
								缴款计划</a>
							<a href="#" data-toggle="modal" data-target="#modal_remit"
								style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
								确认打款</a>
							<a href="#" style="outline: none;" data-toggle="modal"
								data-target="#modal_return"><i
								class="glyphicon glyphicon-plus"></i> 退款</a>
							<a href="javascript:giveup_call()" 
								style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
								放棄call款</a>
						</s:if>
					</div>

					<div class="row topx myMinh" style="border: 0; padding: 0;">

						<div class="spjz">
							<!--1  -->
							<div class="panel panel-default" style="width: 100%;">


								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>业绩分配

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>

											<tr class="demo_tr" style="font-size: 10px;">
												<th>客户</th>
												<th>RM</th>
												<th>业绩</th>
												<th>提成点</th>

											</tr>

										</thead>
										<tbody>
											<s:if test="#request.status2==1 ">
												<s:iterator value="#request.RM_list" var="RM">
													<tr class="default" id="cust_list">
														<td>${RM.cust_name }</td>
														<td>${RM.sales_name }</td>
														<td>${RM.magt_fee }</td>
														<td>${RM.sales_point }</td>
													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status2==2">
												<tr class="default">
													<th colspan="4" style="text-align: center;">${RM_li }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->

								</div>
							</div>

							<!-- 1 -->
							<div class="panel panel-default" style="width: 100%;">


								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>缴款计划记录

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>订单号</th>
												<th>缴款阶段</th>
												<th>比例</th>
												<th>金额</th>
												<th>截止时间</th>
												<th>审核状态</th>
												<s:if test="#request.role_id==5">
												<th>审核</th>
												</s:if>
											</tr>
										</thead>
										<tbody>
											<s:if test="#request.status7==1 ">
												<s:iterator value="#request.plan_list" var="plan_li">
													<tr class="default" id="cust_list">
														<td>${plan_li.order_no }</td>
														<td>${plan_li.pay_step }</td>
														<td>${plan_li.pay_per}</td>
														<td>${plan_li.pay_amount }</td>
														<td>${plan_li.stop_time }</td>
														<td>${plan_li.dict_name }</td>
														<s:if test="#request.role_id==5">
														<s:if test="#plan_li.pay_state==1">
															<td><button
																	style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;"
																	value="" class="btn btn-sm btn-light-info active"
																	onclick="call_approve('2','${plan_li.payment_id }')">通过</button>
																<button
																	style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;"
																	value="" class="btn btn-sm btn-light-info active"
																	onclick="call_approve('3','${plan_li.payment_id }')">驳回</button></td>
														</s:if>
														<s:else><td>审核完成</td></s:else>
														
														</s:if>
														<s:if test="#request.role_id==4">
														<s:if test="#plan_li.pay_state==1">
														<td onclick="del_call_payment('${plan_li.payment_id }')"><a href="#">✘</a></td>
														</s:if>
														</s:if>
													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status7==2">
												<tr class="default">
													<th colspan="7" style="text-align: center;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${plan_li }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->

								</div>
							</div>
							<!-- 2 -->
							<div class="panel panel-default" style="width: 100%;">


								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>打款记录

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>客户</th>
												<th>产品</th>
												<th>应缴金额</th>
												<th>打款日期</th>
												<th>实缴金额</th>
												<th>打款分期</th>
												<th>滞纳金</th>
												<th>笔数</th>
												<th>审核状态</th>
												<s:if test="#request.role_id==5">
												<th>审核</th>
												</s:if>
											</tr>
										</thead>
										<tbody>
											<s:if test="#request.status3==1 ">
												<s:iterator value="#request.payment_list" var="payment">
													<tr class="default" id="cust_list">
														<td>${payment.cust_name }</td>
														<td>${payment.prod_name }</td>
														<td>${payment.order_amount}</td>
														<td>${payment.pay_time }</td>
														<td>${payment.pay_amount }</td>
														<td>${payment.pay_step }</td>
														<td>${payment.delay_fee }</td>
														
														<%-- <td>${payment.mum }</td> --%>
														<td href="#" data-toggle="modal"
															data-target="#modal_payment">${payment.mum }</td>
															<td>${payment.pay_dict_name }</td>
														<s:if test="#request.role_id==5">
														<s:if test="#payment.pay_state==1">
															<td><button
																	style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;"
																	value="" class="btn btn-sm btn-light-info active"
																	onclick="play_approve('2','${payment.payment_id }')">通过</button>
																<button
																	style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;"
																	value="" class="btn btn-sm btn-light-info active"
																	onclick="play_approve('3','${payment.payment_id }')">驳回</button></td>
														</s:if>
														<s:else><td>审核完成</td></s:else>
														</s:if>
														<s:if test="#request.role_id==4">
														<s:if test="#payment.pay_state==1">
														<td onclick="del_payment('${payment.payment_id }')"><a href="#">✘</a></td>
														</s:if>
														</s:if>
													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status3==2">
												<tr class="default">
													<th colspan="10" style="text-align: center;">${payment_li }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->

								</div>
							</div>


							<!--3 -->
							<div class="panel panel-default" style="width: 100%;">


								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>退款记录

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>客户名称</th>
												<th>编号</th>
												<th>产品名称</th>
												<th>订单金额</th>
												<th>退款金额</th>
												<th>退款时间</th>
												<th>审核状态</th>
												
												<s:if test="#request.role_id==5">
												<th>审核</th>
												</s:if>
												
											</tr>
										</thead>
										<tbody>
											<s:if test="#request.status4==1 ">
												<s:iterator value="#request.drawback_list" var="drawback_li">
													<tr class="default" id="cust_list">
														<td>${drawback_li.cust_name }</td>
														<td>${drawback_li.order_id }</td>
														<td>${drawback_li.prod_name }</td>
														<td>${drawback_li.order_amount }</td>
														<td>${drawback_li.pay_amount }</td>
														<td>${drawback_li.pay_time }</td>
														<td>${drawback_li.pay_dict_name }</td>
														<s:if test="#request.role_id==5">
												
														<s:if test="#drawback_li.pay_state==1">
															<td><button
																	style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;"
																	value="" class="btn btn-sm btn-light-info active"
																	onclick="play_approve('2','${drawback_li.payment_id }')">通过</button>
																<button
																	style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;"
																	value="" class="btn btn-sm btn-light-info active"
																	onclick="play_approve('3','${drawback_li.payment_id }')">驳回</button>
															</td>
														</s:if>
														<s:else><td>审核完成</td></s:else>
														</s:if>
														<s:if test="#request.role_id==4">
														<s:if test="#drawback_li.pay_state==1">
														<td onclick="del_payment('${drawback_li.payment_id }')"><a href="#">✘</a></td>
														</s:if>
														</s:if>
													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status4==2">
												<tr>
													<th colspan="8" style="text-align: center;">${drawback_li }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->

								</div>
							</div>

							<!-- 4 -->
							<s:if test="#request.role_id==1||#request.role_id==2||#request.role_id==3">
							<div class="panel panel-default" style="width: 100%;">


								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>佣金发放记录

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>客户</th>
												<th>产品</th>
												<th>金额</th>
												<th>日期</th>

											</tr>
										</thead>
										<tbody>
											<s:if test="#request.status5==1 ">
												<s:iterator value="#request.reward_list" var="reward">
													<tr class="default" id="cust_list">
														<td>${reward.cust_name }</td>
														<td>${reward.prod_name }</td>
														<td>${reward.reward_out }</td>
														<td>${reward.create_time }</td>

													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status5==2">
												<tr class="default">
													<th colspan="4" style="text-align: center;">${reward_li }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->

								</div>
							</div>
							</s:if>
							<!--5  -->
							<div class="panel panel-default" style="width: 100%;">


								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>订单变更记录

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<!-- <th>变更字段</th>
												<th>变更前内容</th>
												<th>变更后内容</th>
												<th>变更前金额</th>
												<th>变更后金额</th>
												<th>变更时间</th>
												<th>变更原因</th> -->
												<th>订单号</th>
												<th>销售</th>
												<th>订单版本号</th>
                                                 <th>订单创建日期</th>
                                                 <th></th>

											</tr>
										</thead>
										<tbody>
											<s:if test="#request.status6==1">
												<s:iterator value="#request.change_list"
													var="orderchange">
													<tr class="default" >
													
														<td>${orderchange.order_no }</td>
														<td>${orderchange.sales_name }</td>
														<td>${orderchange.order_version }</td>
														<td>${orderchange.create_time }</td> 
														<td >
														<a
															href="javascript:change_detail('${orderchange.order_no}','${orderchange.order_version}','${orderchange.sales_id}')"
															><i
																class="glyphicon glyphicon-plus"></i> 详情</a>
														</td>
														

													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status6==2">
												<tr class="default">
													<th colspan="7" style="text-align: center;">${orderchange_li }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->

								</div>
							</div>

							<!-- 6 -->
							<div class="panel panel-default" style="width: 100%;">


								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>回款记录

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>回款批次</th>
												<th>时间</th>
												<th>回款系数</th>
												<th>销售</th>
												<th>状态</th>
												<th></th>


											</tr>
										</thead>
										<tbody>
											<s:if test="#request.status9==1 ">
												<s:iterator value="#request.lis" var="re">
													<tr class="default">
														<td>${re.prod_rs_id }</td>
														<td>${re.return_date }</td>
														<td><input type="number" required="required"
															style="border-style: none"
															id='code${re.prod_rs_id }${re.sale_id }'
															value="${re.return_coe}" /></td>
														<td>${re.sale_id }</td>
														<td>${re.dict_name }</td>
														<td>
														<s:if test="#request.role_id==4&&#re.prod_flag==0">
														<button
																style="width: 70%; float: left; text-align: center; padding-left: 0; padding-right: 0;"
																value="" class="btn btn-sm btn-light-info active"
																onclick="return_update('${re.prod_rs_id }', '${re.sale_id }')"
																id='${re.prod_rs_id }${re.sale_id }'>提交审批</button>
														</s:if>
														</td> 

													</tr>
												</s:iterator>
											</s:if>
											<s:else>
												<tr class="default">
													<th colspan="6" style="text-align: center;">未找到相关数据</th>
												</tr>
											</s:else>

										</tbody>
									</table>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->

								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
			<!--end 右-->
		</div>

		<div id="myal" class="modal fade bs-example-modal-sm" tabindex="-1"
			role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog" style="width: 400px;">
				<div class="modal-content"
					style="border-top-right-radius: 10px; border-top-left-radius: 10px;">
					<div class="modal-header"
						style="height: 45px; width: 100%; line-height: 45px; position: relative; background-color: #6699FF; border-top-right-radius: 6px; border-top-left-radius: 6px;">

						<h4
							style="text-align: center; margin: 0; padding: 0; color: #fff;">提示信息</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"
							style="position: absolute; top: 32%; right: 15px; outline: none; color: #333">
							&times;</button>

					</div>

					<div id="content"
						style="text-align: center; height: 70px; font-size: 16px; line-height: 70px; width: 100%;">


					</div>


					<div class="row text-center" style="margin-bottom: 15px;">
						<button
							style="margin-right: 15px; width: 60px; height: 35px; border: 1px solid #ddd; border-radius: 2px;"
							data-dismiss="modal" class=" ">关闭</button>


					</div>


				</div>
			</div>
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




		<!--end底部-->

		<script type="text/javascript">
			function order_detail_company() {
				window.location.href = "order_detail_org.jsp";
			}
			function order_detail(order_no,order_version) {
				window.location.href = "orderEdit.action?order_no=" + order_no+"&order_version="+order_version;
			}
			function return_update(prod_re_id,sale_id){
				
				var id = "#code"+prod_re_id+sale_id;
				console.log(id);
				var return_code = $(id).val();
				$.ajax({
					url : "returnsave.action",
					type : "post",
					dataType : "json",
					data : {
						"sale_id" : sale_id,"prod_rs_id":prod_re_id,
						"return_coe":return_code
					},
					success : function(data) {
						if(data.status==1){
							document.getElementById('content').innerHTML=data.msg;
							$('#myal').modal('show');
							
						}else{
							document.getElementById('content').innerHTML=data.msg;
							$('#myal').modal('show');
						}
						
						
					},
					error: function() {
						var msg = escape(escape("數據異常"));
						location.href="error.action?msg="+msg
					}
				});
			}
		</script>
	</div>


	<!-- 打款计划弹框 -->
	<div class="modal" id="modal_plan" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px; height: 430px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px; height: 100%;">
				<div class="myright-n">
					<div class="row topx myMinh" style="border: 0; padding: 0;">
						<div class="spjz" style="">
							<ul class="list-group">
								<li class="list-group-item"
									style="color: #a52410; background-color: rgb(245, 245, 245);">
									<h3 class="panel-title" style="color: #a52410;">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"
											style="margin-right: 6px;"></span> 缴款计划信息
									</h3>
								</li>
								<!-- <li class="list-group-item">
                        <span>客户名称&nbsp:</span>
                        <input type="text" style="width: 75%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item">
                        <span> 编号&nbsp:</span>
                        <input type="text"  style="width: 75%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item">
                        <span> 产品名称&nbsp:</span>
                        <input type="text" name="prod_name" style="width: 75%;border:none;outline: none;">
                       </li> -->
								<li class="list-group-item"><span> 缴款金额&nbsp;:</span> <input
									required="required" id="pay_amount_play"
									name="pay_amount_play" oninput="qianfenwei('pay_amount_play')"
									style="width: 75%; border: none; outline: none;" /></li>
								<li class="list-group-item"><span> 截至时间&nbsp;:</span> <!-- <input type="text" id="stop_time" name="stop_time" style="width: 75%; border: none; outline: none;" /> -->
									<input name="stop_time" id="stop_time" required="required"
									value="<%=now_date%>" class="form_datetime" type="text"
									style="width: 20%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 缴款分期&nbsp;:</span> <select
									id="order_pay_step" name="order_pay_step">
										<option value="1">1期打款</option>
										<option value="2">2期打款</option>
										<option value="3">3期打款</option>
										<option value="4">4期打款</option>
										<option value="5">5期打款</option>
										<option value="6">6期打款</option>
								</select></li>
								<li class="list-group-item"><span> 缴款比例&nbsp;:</span> <input
									type="number" required="required" id="pay_per" name="pay_per"
									style="width: 75%; border: none; outline: none;" /></li>
								<li><input type="hidden" name="order_no" id="order_no"
									value="${order_list.order_no }"></li>
								<li><input type="hidden" name="order_version"
									id="order_version" value="${order_list.order_version }"></li>
							</ul>
							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									onclick="dele()" class="btn btn-lg btn-default">取消</button>
								<!-- 	<input type="button" class="btn btn-lg" data-dismiss="modal" onclick="doplay()" style="background-color: #5bc0de; color: #fff;">
								  -->
								<button class="btn btn-lg"
									style="background-color: #5bc0de; color: #fff;"
									onclick="doOrderPlay()">提交审批</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<!-- 打款弹框 -->
	<div class="modal" id="modal_remit" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px; height: 430px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px; height: 100%;">
				<div class="myright-n">

					<div class="row topx myMinh" style="border: 0; padding: 0;">

						<div class="spjz" style="">
							<ul class="list-group">

								<li class="list-group-item"
									style="color: #a52410; background-color: rgb(245, 245, 245);">
									<h3 class="panel-title" style="color: #a52410;">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"
											style="margin-right: 6px;"></span> 客户汇款信息
									</h3>
								</li>
								<li class="list-group-item"><span>客户名称&nbsp:</span> <input
									type="text" id="cust_name" name="cust_name"
									value="${order_list.cust_name }" readonly="readonly"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 编号&nbsp:</span> <input
									type="text" name="order_no" value="${order_list.order_no }"
									readonly="readonly"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 产品名称&nbsp:</span> <input
									type="text" value="${order_list.prod_name }" id="prod_name"
									name="prod_name" readonly="readonly"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 打款金额&nbsp:</span> <input
									required="required" name="pay_amount"
									id="pay_amount" oninput="qianfenwei('pay_amount')"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 打款类型&nbsp:</span> <select
									name="transaction_type" id="transaction_type" onchange="paychange()" >
										<option selected value="1">投资款</option>
										<option value="2">认购费</option>
										<option value="3">管理费</option>
										<option value="4">开办费</option>
										<option value="5">滞纳金</option>
										<option value="6">违约金</option>
										<option value="0">其他</option>
								</select>
								<input type="hidden" id="remark" name="remark">
								</li>
								<li class="list-group-item" style="border-top: 0;"><span>
										打款时间&nbsp:</span> <!-- <input type="text" name="pay_time" id="pay_time" style="width: 75%; border: none; outline: none;"> -->
									<input name="pay_time" id="pay_time" required="required"
									value="<%=now_date%>" class="form_datetime" type="text"
									style="width: 20%; border: none; outline: none;"></li>

								<li class="list-group-item"><span> 打款分期&nbsp:</span> <select
									name="pay_step" id="pay_step">
										<!-- 										<option value="">1期打款</option>
										<option value="">2期打款</option>
										<option value="">3期打款</option> -->
										<s:if test="#request.status7==1 ">
										<s:iterator value="#request.plan_list" var="plan_li">
											<option value="${plan_li.pay_step }">${plan_li.pay_step }期打款</option>
										</s:iterator>
										</s:if>
											<s:elseif test="#request.status7==2">
												<option>${plan_li }</option>
											</s:elseif>
								</select></li>

							</ul>

							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									onclick="dele()" class="btn btn-lg btn-default">取消</button>
								<!-- 	<input type="button" class="btn btn-lg" data-dismiss="modal" onclick="show_pay()" style="background-color: #5bc0de; color: #fff;">
								  -->
								<button class="btn btn-lg" data-dismiss="modal"
									onclick="show_pay()"
									style="background-color: #5bc0de; color: #fff;">确定</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- 变更详情弹框 -->
<div class="modal" id="modal_change" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px; height: 430px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px; height: 100%;">
				<div class="myright-n">

					<div class="row topx myMinh" style="border: 0; padding: 0;">

						<div class="spjz" style="">
							<ul class="list-group">

								<li class="list-group-item"
									style="color: #a52410; background-color: rgb(245, 245, 245);">
									<h3 class="panel-title" style="color: #a52410;">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"
											style="margin-right: 6px;"></span> 变更记录详情
									</h3>
								</li>
								<li class="list-group-item"><span>客户名称&nbsp:</span> <input
									type="text" id="change_cust_name" name="change_cust_name"
									value="" readonly="true"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 订单号&nbsp:</span> <input
									type="text" id="change_order_no" name="change_order_no" value="${order_list.order_no }"
									readonly="true"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 产品名称&nbsp:</span> <input
									type="text"  id="change_prod_name"
									name="prod_name" readonly="readonly"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 合伙企业&nbsp:</span> <input
									  name="pay_amount"
									id="change_pay_amount" readonly="true"
									style="width: 75%; border: none; outline: none;"></li>
								
								

								<li class="list-group-item"><span>  指导标费&nbsp:</span>
								<input name="change_pri_fee" id="change_pri_fee" required="required"
									 class="form_datetime" type="text" readonly="true"
									style="width: 20%; border: none; outline: none;">
								</li>
								

							</ul>

							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									 class="btn btn-lg btn-default">关闭</button>
									
								 
								<!-- <button class="btn btn-lg" data-dismiss="modal"
									onclick="show_pay()"
									style="background-color: #5bc0de; color: #fff;">确定</button> -->
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 退款弹框 -->
	<div class="modal" id="modal_return" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px; height: 430px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px; height: 100%;">
				<div class="myright-n">

					<div class="row topx myMinh" style="border: 0; padding: 0;">

						<div class="spjz" style="">
							<ul class="list-group">

								<li class="list-group-item"
									style="color: #a52410; background-color: rgb(245, 245, 245);">
									<h3 class="panel-title" style="color: #a52410;">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"
											style="margin-right: 6px;"></span> 客户退款信息
									</h3>
								</li>
								<li class="list-group-item"><span>客户名称&nbsp:</span> <input
									type="text" id="cust_name" name="cust_name"
									value="${order_list.cust_name }" readonly="readonly"
									style="width: 75%; border: none; outline: none;"> <input
									type="hidden" name="cust_no" id="cust_no"
									value="${order_list.cust_no }"> <input type="hidden"
									name="cust_type" id="cust_type"
									value="${order_list.cust_type }"></li>
								<li class="list-group-item"><span> 编号&nbsp:</span> <input
									type="text" value="${order_list.order_no }" id="order_no"
									name="order_no" readonly="readonly"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 产品名称&nbsp:</span> <input
									type="text" value="${order_list.prod_name }" id="prod_name"
									name="prod_name" readonly="readonly"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 订单金额&nbsp:</span> <input
									type="number" required="required"
									value="${order_list.order_amount }" id="order_amount"
									readonly="readonly" name="order_amount"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item" style="border-top: 0;"><span>
										退款金额&nbsp:</span> <input type="number" id="refund_amount"
									name="refund_amount"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 退款分期&nbsp:</span> <select
									name="pay_step1" id="pay_step1">
										<!-- 										<option value="">1期打款</option>
										<option value="">2期打款</option>
										<option value="">3期打款</option> -->
										<s:if test="#request.desc==1 ">
											<s:iterator value="#request.back_list" var="plan_li">
												<option value="${plan_li.pay_step }">${plan_li.pay_step }期打款</option>
											</s:iterator>
										</s:if>
										<s:else >
											<option>未找到打款信息</option>
										</s:else>
								</select></li>
								<li class="list-group-item"><span> 退款时间&nbsp:</span> <!-- <input id="refund_time" name="refund_time" type="text" style="width: 75%; border: none; outline: none;"> -->
									<input name="refund_time" id="refund_time" required="required"
									value="<%=now_date%>" class="form_datetime" type="text"
									style="width: 20%; border: none; outline: none;"></li>

							</ul>
							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									onclick="dele()" class="btn btn-lg btn-default">取消</button>
								<!-- 	<input type="button" class="btn btn-lg" data-dismiss="modal" onclick="show_refund()" style="background-color: #5bc0de; color: #fff;">
								  -->
								<button class="btn btn-lg" data-dismiss="modal"
									onclick="show_refund()"
									style="background-color: #5bc0de; color: #fff;">确定</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 打款记录弹窗 -->
	<div class="modal" id="modal_payment" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px; height: 430px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px; height: 100%;">
				<div class="myright-n">

					<div class="row topx myMinh" style="border: 0; padding: 0;">

						<div class="spjz" style="">
							<table class="table cust_table">
								<thead>
									<tr class="demo_tr" style="font-size: 10px;">
										<th>客户</th>
										<th>产品</th>
										<th>应缴金额</th>
										<th>截止日期</th>
										<th>实缴金额</th>
										<th>打款分期</th>
										<th>滞纳金</th>
										<th>笔数</th>
									</tr>
								</thead>
								<tbody>
									<s:if test="#request.status3==1 ">
										<s:iterator value="#request.payment_list" var="payment">
											<tr class="default" id="cust_list">
												<td>${payment.cust_name }</td>
												<td>${payment.prod_name }</td>
												<td>${payment.order_amount}</td>
												<td>${payment.stop_time }</td>
												<td>${payment.pay_amount }</td>
												<td>${payment.pay_step }</td>
												<td>${payment.delay_fee }</td>
												<%-- <td>${payment.mum }</td> --%>
												<td href="#" data-toggle="modal"
													data-target="#modal_payment">${payment.mum }</td>
											</tr>
										</s:iterator>
									</s:if>
									<s:elseif test="#request.status3==2">
										<tr class="default">
											<th colspan="8" style="text-align: center;">${payment_li }</th>
										</tr>
									</s:elseif>
								</tbody>
							</table>
							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									onclick="dele()" class="btn btn-lg btn-default">返回</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<div id="submitHint" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog" style="width: 400px;">
    <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      
        <div style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
         提交成功!
        </div>
          <div class="row text-center" style="margin-bottom: 15px;">
            <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #6699FF;border-radius: 2px;" data-dismiss="modal"
              class=""  onclick="refresh()">关闭</button>   
          </div> 
  </div>
</div>
</div>
<div id="freezeOrder" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
         </div>
        <div style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
         是否冻结该订单？
        
        </div>

    
          <div class="row text-center" style="margin-bottom: 15px;">
            <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" ">否</button>

            <button id="customerCreateBtn" data-dismiss="modal"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" onclick="freezeOrder()">是</button>
          </div>
  </div>
</div>
</div>
	<!-- 业绩分配modal_ach -->

	<div class="modal" id="modal_ach" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px;">
				<div class="myright-n">

					<div class="row topx myMinh" style="border: 0; padding: 0;">

						<div class="spjz" style="">
							<ul class="list-group">

								<li class="list-group-item"
									style="color: #a52410; background-color: rgb(245, 245, 245);">
									<h3 class="panel-title" style="color: #a52410;">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"
											style="margin-right: 6px;"></span> 业绩分配
									</h3>
								</li>
								<li class="list-group-item"><span>客户名称&nbsp:</span> <input
									type="text" value="${order_list.cust_name }"
									readonly="readonly"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 订单编号&nbsp:</span> <input
									type="text" readonly="readonly" value="${order_list.order_no }"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 产品名称&nbsp:</span> <input
									type="text" readonly="readonly" value="${order_list.prod_name }"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item"><span> 订单金额&nbsp:</span> <input
									type="number" readonly="readonly"
									value="${order_list.order_amount }"
									style="width: 75%; border: none; outline: none;"></li>
								<li class="list-group-item" style="border-top: 0;"><span>
										标费&nbsp:</span> <input type="text" readonly="readonly"
									value="${order_list.acount_fee }"
									style="width: 75%; border: none; outline: none;"></li>


							</ul>
							<div class="row text-center"
								style="margin: 0 auto; padding: 10px 0;">
								<div data-toggle="buttons-radio" id="ms" name="ms"
									class="btn-group" style="margin-left: 75px;">
									<button value="1" class="btn-sm btn btn-light-info active"
										onclick="show_ms(1)">按标费分配</button>
									<button value="2" class="btn-sm btn btn-light-info"
										onclick="show_ms(2)">按提成点分配</button>
								</div>
							</div>

							<div class="panel panel-default" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>订单列表

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table" id="achievement_li123">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>销售</th>
												<th>业绩</th>
												<th>提成点</th>

											</tr>
										</thead>
										<tbody id="achievement_info">
											<s:if test="#request.status8==1 ">
												<s:iterator value="#request.achievement_list"
													var="achievement_li">
													<tr class="default">
														<td>${achievement_li.sales_name }</td>
														<td style="display: none;">${achievement_li.sales_id }</td>
														<td class="a">${achievement_li.magt_fee }</td>
														<td class="b">${achievement_li.sales_point }</td>
													</tr>

												</s:iterator>
											</s:if>
											<s:elseif test="#request.status8==2">
												<tr>
													<th colspan="5" style="text-align: center;">${achievement_li }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>

								</div>
							</div>
							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									onclick="dele()" class="btn btn-lg btn-default">取消</button>
								<!-- <input type="button" class="btn btn-lg" data-dismiss="modal" onclick="show_indiv()" style="background-color: #5bc0de; color: #fff;"> -->
								<button class="btn btn-lg" data-dismiss="modal"
									onclick="achievement()"
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