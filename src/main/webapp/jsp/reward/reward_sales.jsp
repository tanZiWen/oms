<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>reward_sales</title>
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

$(function(){ 
	 var a = '<%=request.getAttribute("role_id")%>' ;
	 if(a=='1'){
		 $("#reward_minbatch").val("0");
			$("#reward_maxbatch").val("3");
			reward('-1');
			$("#reserve_minbatch").val("0");
			$("#reserve_maxbatch").val("3");
			reserve('-1'); 
	 }
	 if(a=='2'){
			$("#reward_minbatch").val("0");
			$("#reward_maxbatch").val("3");
			reward('-1');
			$("#group_minbatch").val("0");
			$("#group_maxbatch").val("3");
			group('-1');
			$("#reserve_minbatch").val("0");
			$("#reserve_maxbatch").val("3");
			reserve('-1'); 
	 }
	

})


	//多条件查询
	function reward_search() {
		var cust_type = $("#cust_type").val().trim();
		var cust_name = $("#cust_name").val().trim();
		var order_no = $("#order_no").val().trim();
		var cust_cell = $("#cust_cell").val().trim();
		var prod_type = $("#prod_type").val().trim();
		var count_batch = $("#count_batch").val().trim();
		
		if (cust_name == "请输入客户名称") {
			cust_name = "";
		}
		if (order_no == "请输入订单号") {
			order_no = "";
		}
		if (cust_cell == "请输入客户电话号码"||cust_cell=="请输入机构注册号" ){
			cust_cell = "";
		}
		if (count_batch == "请输入奖金计算批次") {
			count_batch = "";
		}
		$.ajax({
					url : 'rewardSearch.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'cust_type' : cust_type,
						'cust_name' : cust_name,
						'order_no' : order_no,
						'cust_cell' : cust_cell,
						'prod_type' : prod_type,
						'count_batch' : count_batch,
					},
					success : function(data) {
						$("#reward_info").empty();
						if (data.status == 1) {
							var rewardInfo = data.reward_list;
							for (var i = 0; i < rewardInfo.length; i++) {
								//判断是否为空
								if (rewardInfo[i].count_batch == null
										|| rewardInfo[i].count_batch == "") {
									rewardInfo[i].count_batch = " ";
								}
								if (rewardInfo[i].prod_name == null
										|| rewardInfo[i].prod_name == "") {
									rewardInfo[i].prod_name = " ";
								}
								if (rewardInfo[i].order_no == null
										|| rewardInfo[i].order_no == "") {
									rewardInfo[i].order_no = " ";
								}
								if (rewardInfo[i].cust_name == null
										|| rewardInfo[i].cust_name == "") {
									rewardInfo[i].cust_name = " ";
								}
								if (rewardInfo[i].order_amount == null
										|| rewardInfo[i].order_amount == "") {
									rewardInfo[i].order_amount = " ";
								}
								if (rewardInfo[i].has_reward == null
										|| rewardInfo[i].has_reward == "") {
									rewardInfo[i].has_reward = " ";
								}
								//自动生成
								
								var reward_info = '<tr class="default" id="cust_list ">'
										+ '<td>'
										+ '<input name="items" value="'
							        + rewardInfo[i].reward_id 
							        +'" type="checkbox">'
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].count_batch
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].prod_name
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].order_no
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].cust_name
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].order_amount
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].has_reward
										+ '</td>' + '</tr>';
								$("#reward_info").append(reward_info);
							}
							var reward_info='<tr> '
								+'<td colspan="7" style="text-align: center;"><a'
								+' style="width: 20%" id="reward_save" ' 
								+'href="javascript:void(0);" onclick="saveReward()"'
								+'style="outline: none;">确认 </a></td>'
						+'</tr>';
							$("#reward_info").append(reward_info);
							$("#reward_save").addClass("btn btn-info top10");
						} else {
							var reward_info = '<tr class="default">'
									+ '<th colspan="12" style="text-align: center ;">'
									+ " 未查到相关数据" + '</th>' + '</tr>';
							$("#reward_info").append(reward_info);
							
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
			alert("当前没有可发放的奖金");
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
		var r = confirm("确认提交发放奖金吗？")
		if (r == false) {
			return false;
		}
		$.ajax({
			url : 'rewardSubmit.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'id' : id
			},
			success : function(data) {
				if (data.status == 1) {
					alert("提交成功！");
					window.location.href = "selectReward.action";
				} else {
					alert("提交失败！");
					window.location.href = "selectReward.action";
				}
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});

	}
	//客户类型选择
	function paychange() {

		var cust_type = $("#cust_type").val();
		if (cust_type == "1") {
			$("#cust_cell_li").hide();
			$("#cust_name_li").hide();
		} else {
			if (cust_type == "2") {
				$("#cust_name_li").show();
				$("#cust_cell_li").show();
				$("#cust_cell").val("请输入客户电话号码");
				$('#cust_cell').focus(function() {
					//获得焦点时，如果值为默认值，则设置为空  
					if ($(this).val() == "请输入客户电话号码") {
						$(this).val("");
					}
				});
				$('#cust_cell').blur(function() {
					//失去焦点时，如果值为空，则设置为默认值  
					if ($(this).val() == "") {
						$(this).val("请输入客户电话号码");
						;
					}
				});
			} else {
				if (cust_type == "3") {
					$("#cust_cell_li").show();
					$("#cust_name_li").show();
					$("#cust_cell").val("请输入机构注册号");
					$('#cust_cell').focus(function() {
						//获得焦点时，如果值为默认值，则设置为空  
						if ($(this).val() == "请输入机构注册号") {
							$(this).val("");
						}
					});
					$('#cust_cell').blur(function() {
						//失去焦点时，如果值为空，则设置为默认值  
						if ($(this).val() == "") {
							$(this).val("请输入机构注册号");
							;
						}
					});
				}
			}
		}
	}
	//奖金类型选择
	function rewardchange() {

		var reward_type = $("#reward_type").val();
		if (reward_type == 1) {
			$("#reward").show();
			$("#reserve").hide();
			$("#group").hide();
		}
		if (reward_type == 2) {
			$("#reward").hide();
			$("#reserve").show();
			$("#group").hide();
		}
		if (reward_type == 3) {
			$("#reward").hide();
			$("#reserve").hide();
			$("#group").show();
		}

	}
	//销售确认发放预留
	function reserve_confirm() {
		
		var reserve_id = "";
		if ($("input[name='reserve']").length <= 0) {
			alert("当前没有可发放的预留");
			return false;
		} 
		
		if ($("input[name='reserve']:checkbox:checked").length > 0) {
			$("input[name='reserve']:checkbox:checked").each(function() {
				reserve_id += $(this).val() + ",";
			})
		} else {
			alert('请您先选择可发放的预留!');
			return false;
		}
		var r = confirm("确认提交发放预留吗？")
		if (r == false) {
			return false;
		}
		$.ajax({
			url : 'reserve_sales_confirm.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				"reserve_id" : reserve_id,
			},
			success : function(data) {
				if (data.status == 1) {
					alert("提交成功!");
					window.location.href = "selectReward.action";
				} else {
					alert("提交失败!");
					window.location.href = "selectReward.action";
				}
			},
			error : function(result) {
				$("#reserve_caches").empty();
				alert('系统异常,请稍后再试!');
			}
		});
	}
	//销售确认发放团队奖金
	function group_confirm() {
		
		var group_id="";
		if ($("input[name='group']").length <= 0) {
			alert("当前没有可提交的团队奖金");
			return false;
		} 
		
		if ($("input[name='group']:checkbox:checked").length > 0) {
			$("input[name='group']:checkbox:checked").each(function() {
				group_id += $(this).val() + ",";
			})
		} else {
			alert('请您先选择可发放的奖金!');
			return false;
		}
		var r = confirm("确认提交团队奖金吗？")
		if (r == false) {
			return false;
		}
		$.ajax({
			url : 'group_confirm.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				"group_id" : group_id,
			},
			success : function(data) {
				if (data.status == 1) {
					alert("提交成功!");
					window.location.href = "selectReward.action";
				} else {
					alert("提交失败!");
					window.location.href = "selectReward.action";
				}
			},
			error : function(result) {
				$("#reserve_caches").empty();
				alert('系统异常,请稍后再试!');
			}
		});
	}
	window.onload = function() {
	        	 var a = '<%=request.getAttribute("role_id")%>' ;
	        	 if(a=='1'){
	        		 document.getElementById("reward_type").options.add(new Option("个人奖金",'1'));
	        		 document.getElementById("reward_type").options.add(new Option("预留奖金",'2'));
	        		 $("#reward_group").hide();
	        	 }
	        	 if(a=='2'){
	        		 document.getElementById("reward_type").options.add(new Option("个人奖金",'1'));
	        		 document.getElementById("reward_type").options.add(new Option("预留奖金",'2'));
	        		 document.getElementById("reward_type").options.add(new Option("团队奖金",'3'));
	        	 }
		$("#reserve").hide();
		$("#group").hide();
	}
	
	//团队奖金多条件查询
	function group_search() {

		var group_count_batch = $("#group_count_batch").val().trim();
		if (group_count_batch == "请输入计算批次") {
			group_count_batch = "";
		}
		$.ajax({
					url : 'group_sales_select.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'group_count_batch' : group_count_batch,
					},
					success : function(data) {
						$("#group_info").empty();
						if (data.status == 1) {
							var rewardInfo = data.group_list;
							for (var i = 0; i < rewardInfo.length; i++) {
								//自动生成
								
								var reward_info = '<tr class="default" id="cust_list ">'
										+ '<td>'
										+ '<input name="group" value="'
							        + rewardInfo[i].id 
							        +'" type="checkbox">'
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].reward_batch_id
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].order_no
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].reward_amount
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].reward_return
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].count_time
										+ '</td>'
									     + '</tr>';
								$("#group_info").append(reward_info);
							}
							var reward_info=' <th colspan="6" style="text-align: center;"><a '
							+' href="javascript:void(0);"  id="group_save" '
							+' style="width: 20%" onclick="group_confirm();"> 保存 </a></th> ';
							$("#group_info").append(reward_info);
							$("#group_save").addClass("btn btn-info top10");
						} else {
							var reward_info = '<tr class="default" id="cust_list " >'
									+ '<th colspan="6" style="text-align: center ;">'
									+ " 未查到相关数据" + '</th>' + '</tr>';
							$("#group_info").append(reward_info);
						}
					},
					error : function(result) {
						alert('系统异常,请稍后再试!');
					}
				});
	}
	
	//预留多条件查询
	function yuliu_search() {

		var yuliu_count_batch = $("#yuliu_count_batch").val().trim();
		if (yuliu_count_batch == "请输入预留计算批次") {
			yuliu_count_batch = "";
		}
		$.ajax({
					url : 'reserve_sales_select.action', //后台处理程序     
					type : 'post', //数据发送方式     
					dataType : 'json', //接受数据格式        
					data : {
						'yuliu_count_batch' : yuliu_count_batch,
					},
					
					success : function(data) {
						$("#yuliu_info").empty();
						if (data.status == 1) {
							var rewardInfo = data.reserve_list;
							for (var i = 0; i < rewardInfo.length; i++) {
								//自动生成
								var reward_info = '<tr class="default" id="cust_list ">'
										+ '<td>'
										+ '<input name="reserve" value="'
							        + rewardInfo[i].reserve_id 
							        +'" type="checkbox">'
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].sales_name
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].count_batch
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].reserve
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].reserve_kou
										+ '</td>'
										+ '<td>'
										+ rewardInfo[i].reserve_give
										+ '</td>'
									     + '</tr>';
								$("#yuliu_info").append(reward_info);
							}
							var reward_info='<th colspan="6" style="text-align: center;"><a '
								+'  style="width: 20%" id="reserve_save" '
									+'	href="javascript:void(0);" onclick="reserve_confirm();"> '
										+' 保存 </a></th>';
							$("#yuliu_info").append(reward_info);
							$("#reserve_save").addClass("btn btn-info top10");
						} else {
							var reward_info = '<tr class="default" id="cust_list " >'
									+ '<th colspan="6" style="text-align: center ;">'
									+ " 未查到相关数据" + '</th>' + '</tr>';
							$("#yuliu_info").append(reward_info);
						}
					},
					error : function(result) {
						alert('系统异常,请稍后再试!');
					}
				});
	}
	
	
	
	//预留- 批次分组
	  function reserve(sign) {
		   var size="";
		   if(sign==-1){
			   size=$("#reserve_minbatch").val();
		   }
		   if(sign==1){
			   size=$("#reserve_maxbatch").val();
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
						 $("#reservebatch").empty();
						var rewardInfo = data.reservecount;
						var reward_info='<table><tr align="center"><td><button onclick="reserve('+-1+')"><<</button></td>';
						for (var i = 0; i < rewardInfo.length; i++) {
							 reward_info+='<td><a '
								+' href="search_reserve_list.action?count_batch='
								+rewardInfo[i].count_batch
								+'" '
								+'	class="btn btn-danger" style="display: inline-block;">'
								+rewardInfo[i].count_batch 
								+'</a></td>';
							  
						}
						reward_info+='<td><button onclick="reserve('+1+')">>></button></td></tr></table>';
						$("#reservebatch").append(reward_info);
							   $("#reserve_minbatch").val(data.minsize);
							   $("#reserve_maxbatch").val(data.maxsize);
					}else{
					/* 	alert("没有更多数据!"); */
					}
				},
				error : function(result) {
					alert('系统异常,请稍后再试!');
				} 
		   });
		
	}
	//团队--批次分组
	 function group(sign) {
		   var size="";
		   if(sign==-1){
			   size=$("#group_minbatch").val();
		   }
		   if(sign==1){
			   size=$("#group_maxbatch").val();
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
						$("#groupbatch").empty();
						var rewardInfo = data.reservecount;
						var reward_info='<table><tr align="center"><td><button onclick="group('+-1+')"> << </button></td>';
						for (var i = 0; i < rewardInfo.length; i++) {
							 reward_info+='<td><a '
								+' href="search_group_list.action?count_batch='
								+rewardInfo[i].reward_batch_id
								+'" '
								+'	class="btn btn-danger" style="display: inline-block;">'
								+rewardInfo[i].reward_batch_id 
								+'</a></td>';
							  
						}
						reward_info+='<td><button onclick="group('+1+')"> >> </button></td></tr></table>';
						$("#groupbatch").append(reward_info);
							   $("#group_minbatch").val(data.minsize);
							   $("#group_maxbatch").val(data.maxsize);
					}else{
						/* alert("没有更多数据!"); */
					}
				},
				error : function(result) {
					alert('系统异常,请稍后再试!');
				} 
		   });
		
	}
	//个人奖金--批次分组
	 function reward(sign) {
		   var size="";
		   if(sign==-1){
			   size=$("#reward_minbatch").val();
		   }
		   if(sign==1){
			   size=$("#reward_maxbatch").val();
		   }
		  
		   $.ajax({
				url : 'rewardbatch.action', //后台处理程序     
				type : 'post', //数据发送方式     
				dataType : 'json', //接受数据格式        
				data : {
					'sign' : sign,
					'size' : size,
				},
				success : function(data) {
					
					if (data.status == 1) {
						  $("#rewardbatch").empty();
						var rewardInfo = data.reservecount;
						 var reward_info='<table><tr align="center"><td><button onclick="reward('+-1+')"><<</button></td>';
						for (var i = 0; i < rewardInfo.length; i++) {
							 reward_info+='<td><a '
								+' href="search_reward_list.action?count_batch='
								+rewardInfo[i].count_batch
								+'" target="_top" '
								+'	class="btn btn-danger" style="display: inline-block;">'
								+rewardInfo[i].count_batch 
								+'</a></td>';
							  
						} 
						 reward_info+='<td><button onclick="reward('+1+')">>></button></td></tr></table>';
						         $("#rewardbatch").append(reward_info);
							   $("#reward_minbatch").val(data.minsize);
							   $("#reward_maxbatch").val(data.maxsize);
					}else{
						/* alert("没有更多数据!"); */
					}
				},
				error : function(result) {
					alert('系统异常,请稍后再试!');
				} 
		   });
		
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
					<li class="active">我的奖金</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="http://www.jq22.com/myhome#" data-toggle="modal"
						data-target="#exampleModal2"> <img id="tou"
						src="image/person.png" class="f imgr20">
					</a>
					<div class="f imgf20">
						<h4>我的奖金管理</h4>

					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> 我的奖金查询<br>

					</div>
					<ul class="list-group">
						<li class="list-group-item"><i class="fa fa-user-secret"></i>
							<span class="cust_state">奖金类型</span> <select id="reward_type"
							name="reward_type" onclick="rewardchange()"
							style="width: 90px; height: 28px; outline: none;">
								
						</select></li>
					</ul>
					<div id="reward">
						<ul class="list-group">
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<span class="cust_state">客户类型</span> <select id="cust_type"
								name="cust_type" onclick="paychange()"
								style="width: 90px; height: 28px; outline: none;">

									<option selected="selected" value="1">全部</option>
									<option value="2">个人客户</option>
									<option value="3">机构客户</option>
							</select></li>
							<li id="cust_name_li" style="display: none"
								class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input id="cust_name" name="cust_name" type="text"
								value="请输入客户名称"
								onblur="if(this.value=='')this.value=this.defaultValue"
								onfocus="if(this.value==this.defaultValue) this.value=''"
								style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input id="order_no" name="order_no" type="text" value="请输入订单号"
								onblur="if(this.value=='')this.value=this.defaultValue"
								onfocus="if(this.value==this.defaultValue) this.value=''"
								style="width: 90%; border: none; outline: none;"></li>
							<li id="cust_cell_li" style="display: none"
								class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input id="cust_cell" name="cust_cell" type="text"
								value="请输入客户电话号码"
								onblur="if(this.value=='')this.value=this.defaultValue"
								onfocus="if(this.value==this.defaultValue) this.value=''"
								style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<span class="cust_state">产品类型</span> <select id="prod_type"
								name="prod_type"
								style="width: 90px; height: 28px; outline: none;">
									<option selected="selected" value=""></option>
									<option value="1">直销产品</option>
									<option value="2">代销产品</option>
							</select></li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input id="count_batch" name="count_batch" type="text"
								value="请输入奖金计算批次"
								onblur="if(this.value=='')this.value=this.defaultValue"
								onfocus="if(this.value==this.defaultValue) this.value=''"
								style="width: 90%; border: none; outline: none;"></li>

						</ul>
						<button id="reward_search" class="btn btn-info top10"
						style="width: 100%" onclick="reward_search()">点击查询</button>
					</div>
					<!-- 预留条件查询 -->
					<div style="disply: none" id="reserve">
						<ul class="list-group">
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input id="yuliu_count_batch" name="yuliu_count_batch" type="text"
								value="请输入预留计算批次"
								onblur="if(this.value=='')this.value=this.defaultValue"
								onfocus="if(this.value==this.defaultValue) this.value=''"
								style="width: 90%; border: none; outline: none;"></li>

						</ul>
						<button id="yuliu_search" class="btn btn-info top10"
						style="width: 100%" onclick="yuliu_search()">点击查询</button>
					</div>
					<!-- 团队奖金条件查询 -->
					<div style="disply: none" id="group">
						<ul class="list-group">
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input id="group_count_batch" name="group_count_batch" type="text"
								value="请输入计算批次"
								onblur="if(this.value=='')this.value=this.defaultValue"
								onfocus="if(this.value==this.defaultValue) this.value=''"
								style="width: 90%; border: none; outline: none;"></li>

						</ul>
						<button id="group_search" class="btn btn-info top10"
						style="width: 100%" onclick="group_search()">点击查询</button>
					</div>

					

				</div>
				<div class="df"></div>
			</div>


			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<!-- 	<div class="myNav row">
						<button onclick="saveReward()" style="outline: none;">
							<i class="glyphicon glyphicon-plus"></i> 佣金发放
						</button>

					</div> -->
					<div class="row topx myMinh" style="">

						<div class="spjz" style="">

							<div class="panel panel-default" style="width: 100%;" >
							
								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>奖金列表

									</h3>
									
								</div>
								<div class="panel-body" style="overflow:auto;">
									<!-- <p>本次编号：20160515</p> -->
									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<th><input type="checkbox" name="allChecked"
													id="allChecked" onclick="DoCheck()" />全选</th>
												<th>批次</th>
												<th>产品名称</th>
												<th>订单号</th>
												<th>客户</th>
												<th>订单金额</th>
												<th>可发放奖金</th>

											</tr>
										</thead>
										<tbody id="reward_info">

											<!-- <tr class="default" id="cust_list">

												<td><input type="checkbox"></td>

												<td>20160501</td>
												<td>常春藤美元母基金</td>
												<td>2313123123</td>
												<td>张淼</td>
												<td>2000000</td>
												<td>20000</td>
											</tr> -->

											<s:if test="#request.status==1 ">
												<s:iterator value="#request.rewardList" var="rewardLi">
													<tr class="default" id="reward_list">
														<td><input name="items"
															value="${rewardLi.reward_id }" type="checkbox"></td>
														<td>${rewardLi.count_batch }</td>
														<td>${rewardLi.prod_name }</td>
														<td>${rewardLi.order_no}</td>
														<td>${rewardLi.cust_name }</td>
														<td>${rewardLi.order_amount }</td>
														<td>${rewardLi.has_reward }</td>
													</tr>
												</s:iterator>
												<tr>
													<td colspan="7" style="text-align: center;"><a
														class="btn btn-info top10" style="width: 20%"
														href="javascript:void(0);" onclick="saveReward()"
														style="outline: none;"> 保存 </a></td>
												</tr>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr class="default">
													<th colspan="7" style="text-align: center;">${reward_list }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<div id="rewardbatch" style="text-align: center; margin: 15px;">
									<table>
									<tr align="center"><td><button onclick="reward('-1')"><<</button></td>
										<s:if test="#request.rewardbatch==1 ">
											<s:iterator value="#request.rewardcount" var="rewardcou">
												<td><a
													href="search_reward_list.action?count_batch=${rewardcou.count_batch}" target="_top"
													class="btn btn-danger" style="display: inline-block;">${rewardcou.count_batch }</a>
											</td>
											</s:iterator>
										</s:if>
										<!-- <button type="button" class="btn btn-danger"
											data-toggle="modal" data-target="#myModal">2015040101</button>
										<button type="button" class="btn btn-danger"
											data-toggle="modal" data-target="#myModal">2015070101</button>
										<button type="button" class="btn btn-danger"
											data-toggle="modal" data-target="#myModal">2015100101</button> -->
												<td><button onclick="reward('1')">>></button></td></tr>
									</table>	
									</div>
									<a><input id="reward_minbatch" type="hidden" value="0"></a>
										<a><input id="reward_maxbatch" type="hidden" value="3"></a>
										<%--  <a class="left carousel-control"
										href="javascript:void(0)" onclick="reward('-1')" role="button"
										data-slide="prev" style="background-image: none;">  <span
										class="glyphicon glyphicon-chevron-left" aria-hidden="true"
										style="color: #333;"></span>  <span class="sr-only">Previous</span>
									</a> 
									<a class="right carousel-control"
										href="javascript:void(0)" onclick="reward('1')" role="button"
										data-slide="next" style="background-image: none; color: #333;">
										<span class="glyphicon glyphicon-chevron-right"
										aria-hidden="true"></span> <span class="sr-only">Next</span>
									</a> --%>
								</div>
								<h4 style="font-size: 14px;color: red;text-align: center;"><p>*每月25号（包含25日）之前提交的奖金申请，将于当月工资内发放（即次月10日发放）</p><p>25日之后提交的奖金申请，将于下月工资内发放，如遇节假日，具体日期请与人事部联系，谢谢配合。</p></h4>
							</div>
							
							<!-- 预留****** -->
							<div class="panel panel-default" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>预留发放

									</h3>
								</div>
								<div class="panel-body" style="overflow:auto;">
									<!-- <p>本次编号：20160515</p> -->
									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<th><input type="checkbox" name="allChecked"
													id="allChecked" onclick="DoCheck()" />全选</th>
												<th>姓名</th>
												<th>批次</th>
												<th>预留金额</th>
												<th>扣除金额</th>
												<th>发放金额</th>

											</tr>
										</thead>
										<tbody id="yuliu_info">
											<s:if test="#request.status1==1 ">
												<s:iterator value="#request.reserveLi" var="reserveList">
													<tr class="default" id="reserve_list">
														<td><input name="reserve"
															value="${reserveList.reserve_id }" type="checkbox"></td>
														<td>${reserveList.sales_name }</td>
														<td>${reserveList.count_batch }</td>
														<td>${reserveList.reserve}</td>
														<td>${reserveList.reserve_kou}</td>
														<td>${reserveList.reserve_give}</td>
													</tr>
												</s:iterator>
												<th colspan="6" style="text-align: center;"><a
													class="btn btn-info top10" style="width: 20%"
													href="javascript:void(0);" onclick="reserve_confirm();">
														保存 </a></th>
											</s:if>
											<s:elseif test="#request.status1==2">
												<tr class="default">
													<th colspan="6" style="text-align: center;">${reserve_list }</th>
												</tr>
											</s:elseif>
											
								

										</tbody>
									</table>
									<!-- 历史纪录 -->
										 <div id="reservebatch" style="text-align: center; margin: 15px;">
										 <table>
									<tr align="center"><td><button onclick="reserve('-1')"><<</button></td>
										<s:if test="#request.reservebatch==1 ">
											<s:iterator value="#request.reservecount" var="reservecou">
												<td><a
													href="search_reserve_list.action?count_batch=${reservecou.count_batch}"
													class="btn btn-danger" style="display: inline-block;">${reservecou.count_batch }</a>
											</td>
											</s:iterator>
										</s:if>
										<!-- <button type="button" class="btn btn-danger"
											data-toggle="modal" data-target="#myModal">2015040101</button>
										<button type="button" class="btn btn-danger"
											data-toggle="modal" data-target="#myModal">2015070101</button>
										<button type="button" class="btn btn-danger"
											data-toggle="modal" data-target="#myModal">2015100101</button> -->
											<td><button onclick="reserve('1')">>></button></td></tr>
									</table>
									</div> 
									<a><input id="reserve_minbatch" type="hidden" value="0"></a>
										<a><input id="reserve_maxbatch" type="hidden" value="3"></a>
				                    <%--  <a class="left carousel-control"
										href="javascript:void(0)" onclick="reserve('-1')" role="button"
										data-slide="prev" style="background-image: none;">  <span
										class="glyphicon glyphicon-chevron-left" aria-hidden="true"
										style="color: #333;"></span>  <span class="sr-only">Previous</span>
									</a> 
									<a class="right carousel-control"
										href="javascript:void(0)" onclick="reserve('1')" role="button"
										data-slide="next" style="background-image: none; color: #333;">
										<span class="glyphicon glyphicon-chevron-right"
										aria-hidden="true"></span> <span class="sr-only">Next</span>
									</a> --%>
								</div>
							</div>
								</div>
							</div>
							<!-- ********* -->

							<!-- 团队奖金****** -->
									<!-- <p>本次编号：20160515</p> -->
									<div class="panel panel-default" id="reward_group" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>团队奖金发放

									</h3>
								</div>
								<div class="panel-body" style="overflow:auto;">
									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<th><input type="checkbox" name="allChecked"
													id="allChecked" onclick="DoCheck()" />全选</th>
												<th>奖金批次</th>
												<th>订单号</th>
												<th>下单金额</th>
												<th>可发放金额</th>
												<th>奖金计算日期</th>
											</tr>
										</thead>
										<tbody id="group_info">
											<s:if test="#request.status2==1 ">
												<s:iterator value="#request.reward_group" var="reward_gr">
													<tr class="default" id="reserve_list">
														<td><input name="group"
															value="${reward_gr.id }" type="checkbox"></td>
														<td>${reward_gr.reward_batch_id }</td>
														<td>${reward_gr.order_no }</td>
														<td>${reward_gr.sum_amount }</td>
														<td>${reward_gr.reward_return}</td>
														<td>${reward_gr.count_time}</td>
													</tr>
												</s:iterator>
												<th colspan="6" style="text-align: center;"><a
													href="javascript:void(0);" class="btn btn-info top10"
													style="width: 20%" onclick="group_confirm();"> 保存 </a></th>
											</s:if>
											<s:elseif test="#request.status2==2">
												<tr class="default">
													<th colspan="6" style="text-align: center;">${group_list }</th>
												</tr>
											</s:elseif>

										</tbody>
									</table>
									
									<div id="groupbatch" style="text-align: center; margin: 15px;">
									<!-- 团队奖金历史记录 -->
								 <table>
									
									<tr align="center"><td><button onclick="group('-1')"><<</button></td>
										<s:if test="#request.groupbatch==1 ">
											<s:iterator value="#request.groupcount" var="groupcou">
												<td><a
													href="search_group_list.action?count_batch=${groupcou.reward_batch_id}"
													class="btn btn-danger" style="display: inline-block;">${groupcou.reward_batch_id }</a>
											</td>
											</s:iterator>
										</s:if>
											<td><button onclick="group('1')">>></button></td></tr>
									</table> 	
									</div>
									<a><input id="group_minbatch" type="hidden" value="0"></a>
										<a><input id="group_maxbatch" type="hidden" value="3"></a>
										<%--  <a class="left carousel-control"
										href="javascript:void(0)" onclick="group('-1')" role="button"
										data-slide="prev" style="background-image: none;">  <span
										class="glyphicon glyphicon-chevron-left" aria-hidden="true"
										style="color: #333;"></span>  <span class="sr-only">Previous</span>
									</a> 
									<a class="right carousel-control"
										href="javascript:void(0)" onclick="group('1')" role="button"
										data-slide="next" style="background-image: none; color: #333;">
										<span class="glyphicon glyphicon-chevron-right"
										aria-hidden="true"></span> <span class="sr-only">Next</span>
									</a> --%>
								</div>
							</div>
							<!-- ********* -->

						</div>
					</div>
				</div>
			</div>
			<!--end 右-->
	


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


</body>
</html>
<script>
	function DoCheck() {
		var ch = document.getElementsByName("choose");
		$('input[type=checkbox]')
				.prop('checked', $(allChecked).prop('checked'));
	}

	
</script>