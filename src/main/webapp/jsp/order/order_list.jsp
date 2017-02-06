<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>order_list</title>
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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/pageStyle.css">
<!--[if lt IE 9]>
 	<script src="<%=request.getContextPath()%>/js/respond.min.js"></script> 
	<script src="<%=request.getContextPath()%>/js/html5shiv.min.js"></script>    
<![endif]-->
<link href="<%=request.getContextPath()%>/css/my.css" rel="stylesheet"
	media="screen">
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/moment.js"></script>
<script src="<%=request.getContextPath()%>/js/order.js"></script>
<script>
	var n = 1;
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script type="text/javascript">
	$(function() {
		statusVal=0;
		$('.buttonStatus').bind("click", function() {
			statusVal = $(this).val();
			order_search(1);
		});
		orderPageList();
		
	});

	function loadData() {

		//分页2

		$("#totalNum").val(NavUtil.totalNum);
		$("#PageNum").val(NavUtil.PageNum);
		var PageNum = document.getElementById("PageNum").value;
		orderPageList();
		//document.getElementById("form1").submit();
	}

	/* 点击查看全部信息 */
	/* function order_detail(order_no, order_version) {
		 window.location.href = "order_Detail.action?order_no=" + order_no
				+ "&order_version=" + order_version; 
	} */
	
	
	function ordermoney(prod_status) {

		//var prod_status = $('#state > .btn.active').val().trim();
		
		$.ajax({
			url : 'ordermoney.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'prod_status' : prod_status
				
			},
			success : function(data) {
				$("#order_info").empty();
				if(data.status==1){
				
					var orderInfo = data.list;
					for (var i = 0; i < orderInfo.length; i++) {
						//自动生成
						var prod_info = '<tr class="default" id="cust_list ">'
								+ '<td>'+ orderInfo[i].order_no+ '</td>'
								+ '<td>'+ orderInfo[i].create_time+ '</td>'
								+ '<td>'+ orderInfo[i].area+ '</td>'
								+ '<td>'+ orderInfo[i].sales_name+ '</td>'
								+ '<td>'+ orderInfo[i].prod_name+ '</td>'
								+ '<td>'+ orderInfo[i].part_comp+ '</td>'
								+ '<td>'+ orderInfo[i].prod_type+ '</td>'
								+ '<td>'+ orderInfo[i].cust_name+ '</td>'
								+ '<td>'+ orderInfo[i].buy_fee1+ '</td>'
								+ '<td>'+ orderInfo[i].bizhong+ '</td>'
								+ '<td>'+ orderInfo[i].prod_diffcoe+ '</td>'
								+ '<td>'+ orderInfo[i].magt_fee1+ '</td>'
								+ '<td>'+ orderInfo[i].dict_name+ '</td>'
								+ '<td><a href="javascript:order_detail('+ orderInfo[i].order_no+ ','+ orderInfo[i].order_version+ ')">详情</a></td>'
								+ '</tr>';
						$("#order_info").append(prod_info);
						//$("td").css("overflow", "hidden").css("text-overflow",
						//		"ellipsis");
						  //使用class选择器;例如:list对象->tbody->td对象.
					    $(".order_table tbody td").each(function(i){

					                //给td设置title属性,并且设置td的完整值.给title属性.
					                $(this).attr("title",$(this).text());

					      });
					}
				 } else {
					var prod_info = '<tr class="default" id="cust_list " >'
							+ '<th colspan="12" style="text-align: center ;">'
							+ " 未查到相关数据" + '</th>' + '</tr>';
					$("#order_info").append(prod_info);
				} 
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});
	}
	function query(totalNum,PageNum,PageSize){
		NavUtil.PageSize = PageSize;
		NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
		NavUtil.bindPageEvent(loadData1);
	}
	function loadData1(){
		//分页2
		$("#totalNum").val(NavUtil.totalNum);
		$("#PageNum").val(NavUtil.PageNum);
		var PageNum=document.getElementById("PageNum").value;
		order_search(PageNum);
		//document.getElementById("form1").submit();
	}
	function order_search(pageNum) {
		var role_id = ${role_id};
		//var pageNum = $('#')
		var status = statusVal;;
		var cust_name = $("#cust_name").val().trim();
		var customer_cell = $("#customer_cell").val().trim();
		var sales_name ="";
		var prod_name = $("#prod_name").val().trim();
		var order_no = $("#order_no").val().trim();
		if (cust_name == "请输入客户名称") {
			cust_name = "";
		}
		if(role_id>3){
			sales_name=$('#sales_name').val().trim();
			if (sales_name == "请输入销售姓名") {
				sales_name = "";
			}
		}
		
		if (customer_cell == "请输入客户电话号码") {
			customer_cell = "";
		}
		if (prod_name == "请输入产品名称") {
			prod_name = "";
		}
		
		if(order_no == "请输入订单号") {
			order_no = "";
		}
		
		$.ajax({
			url : 'orderSelect.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'status' : status,
				'cust_name' : cust_name,
				'customer_cell' : customer_cell,
				'sales_name' :sales_name,
				'prod_name' : prod_name,
				'order_no': order_no,
				'pageNum':pageNum
			},
			success : function(data) {
				$("#order_info").empty();
				$('#page1').empty();
				$('#tatolNum').val(data.tatolNum);
				query(data.tatolNum,data.pageNum,10)
				if (data.status == 1) {
					var orderInfo = data.list;
					for (var i = 0; i < orderInfo.length; i++) {
						//判断是否为空
						if (orderInfo[i].prod_diffcoe == null
								|| orderInfo[i].prod_diffcoe == "") {
							orderInfo[i].prod_diffcoe = " ";
						}
						if (orderInfo[i].cust_name == null
								|| orderInfo[i].cust_name == "") {
							orderInfo[i].cust_name = " ";
						}
						if (orderInfo[i].order_no == null
								|| orderInfo[i].order_no == "") {
							orderInfo[i].order_no = " ";
						}
						if (orderInfo[i].create_time == null
								|| orderInfo[i].create_time == "") {
							orderInfo[i].create_time = " ";
						}else {
							orderInfo[i].create_time  = moment(orderInfo[i].create_time).format("YYYY-MM-DD");
						}
						if (orderInfo[i].area == null
								|| orderInfo[i].area == "") {
							orderInfo[i].area = " ";
						}
						if (orderInfo[i].sales_name == null
								|| orderInfo[i].sales_name == "") {
							orderInfo[i].sales_name = " ";
						}
						if (orderInfo[i].prod_name == null
								|| orderInfo[i].prod_name == "") {
							orderInfo[i].prod_name = " ";
						}
						if (orderInfo[i].part_comp == null
								|| orderInfo[i].part_comp == "") {
							orderInfo[i].part_comp = " ";
						}
						if (orderInfo[i].prod_type == null
								|| orderInfo[i].prod_type == "") {
							orderInfo[i].prod_type = " ";
						}
						if (orderInfo[i].buy_fee == null
								|| orderInfo[i].buy_fee == "") {
							orderInfo[i].buy_fee = " ";
						}
						if (orderInfo[i].prod_type == null
								|| orderInfo[i].prod_type == "") {
							orderInfo[i].prod_type = " ";
						}
						if (orderInfo[i].magt_fee == null
								|| orderInfo[i].magt_fee == "") {
							orderInfo[i].magt_fee = " ";
						}
						if(orderInfo[i].cust_cell == null
								|| orderInfo[i].cust_cell == "") {
							orderInfo[i].cust_cell = " ";
						}
						if(orderInfo[i].investor_name == null
								|| orderInfo[i].investor_name == "") {
							orderInfo[i].investor_name = " ";
						}
						var date = date_time(orderInfo[i].entry_time,"yyyy-MM-dd");
						//自动生成
						var prod_info = '<tr class="default" id="cust_list ">'
								+ '<td>'
								+ orderInfo[i].order_no
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].create_time
								+ '</td>'
								+ '<td>'
								+ date
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].area
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].sales_name
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].order_cell
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].prod_name
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].partner_com_name
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].prod_type
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].cust_name
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].investor_name
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].order_amount
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].bizhong
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].prod_diffcoe
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].magt_fee1
								+ '</td>'
								+ '<td>'
								+ orderInfo[i].dict_name
								+ '</td>'
								+ '<td><a href="order_Detail.action?order_version='+ orderInfo[i].order_version+ '&order_no='+ orderInfo[i].order_no+'">详情</a></td>'
								+ '</tr>';
								
						$("#order_info").append(prod_info);
						$("td").css("overflow", "hidden").css("text-overflow",
								"ellipsis");
						  //使用class选择器;例如:list对象->tbody->td对象.
					    $(".order_table tbody td").each(function(i){

					                //给td设置title属性,并且设置td的完整值.给title属性.
					                $(this).attr("title",$(this).text());

					      });
					}
				} else {
					var prod_info = '<tr class="default" id="cust_list " >'
							+ '<th colspan="12" style="text-align: center ;">'
							+ " 未查到相关数据" + '</th>' + '</tr>';
					$("#order_info").append(prod_info);
					$('#page1').empty();
					$('#tatolNum').val(0);
				}
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});
	}
	
	function date_time(time, format){
	    var t = new Date(time);
	    var tf = function(i){return (i < 10 ? '0' : '') + i};
	    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
	        switch(a){
	            case 'yyyy':
	                return tf(t.getFullYear());
	                break;
	            case 'MM':
	                return tf(t.getMonth() + 1);
	                break;
	            case 'mm':
	                return tf(t.getMinutes());
	                break;
	            case 'dd':
	                return tf(t.getDate());
	                break;
	            case 'HH':
	                return tf(t.getHours());
	                break;
	            case 'ss':
	                return tf(t.getSeconds());
	                break;
	        }
	    })
	}
	//绑定ENTER事件
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			order_search(1);
		}
	};
	$(function(){
	     //使用class选择器;例如:list对象->tbody->td对象.
	    $(".order_table tbody td").each(function(i){

	                //给td设置title属性,并且设置td的完整值.给title属性.
	                $(this).attr("title",$(this).text());

	      });
	});
	
	function order_report(){
		//location.href="";
		window.open('order_report.action', '_blank');

	}
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->
	<%@ include file="/jsp/header.jsp"%>


	<!--主体-->
	<input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
	<input type="hidden" name="PageNum1" id="PageNum" value="1" />
	<input type="hidden" name="PageSize" id="PageSize" value="10" />
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
						<i class="glyphicon glyphicon-search"></i> 订单查询<br>
					</div>
					<div id="state" data-toggle="buttons-radio" class="btn-group"
						style="margin-bottom: 10px; width: 100%;">
						<button style="width: 25%; float: left;" value="0"
							class="buttonStatus btn btn-sm btn-light-info active" >全部</button>
						<button style="width: 25%; float: left;" value="1"
							class="buttonStatus btn btn-sm btn-light-info "  >人民币</button>
						<button style="width: 25%; float: left;" value="3"
							class="buttonStatus btn btn-sm btn-light-info"  >美元</button>
						<button style="width: 25%; float: left;" value="2"
							class="buttonStatus btn btn-sm btn-light-info"  >港币</button>
					</div>
					<ul class="list-group">
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input type="text" value="请输入客户名称" id="cust_name"
							name="cust_name"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input type="text" value="请输入客户电话号码" id="customer_cell"
							name="customer_cell"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
						<s:if test="#request.role_id!=1&&#request.role_id!=2&&#request.role_id!=3">
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input type="text" value="请输入销售姓名" id="sales_name"
							name="sales_name"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
						</s:if>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input type="text" value="请输入产品名称" id="prod_name"
							name="prod_name"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input type="text" value="请输入订单号" id="order_no"
							name="order_no"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
						<!-- 重复信息     删除     <li class="list-group-item">

                <i class="fa fa-user-secret"></i>&nbsp;
                <input type="text" value="投资币种"
                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
                 style="width: 90%;border:none;outline: none;">
            </li> -->


					</ul>
					<button class="btn btn-info top10" style="width: 100%"
						onclick="order_search(1)">点击查询</button>
				</div>
				<div class="df"></div>
			</div>

			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
				
					<div class="myNav row">
					<s:if test="#request.role_id==4">
						<a href="skiporder.action" style="outline: none;"><i
							class="glyphicon glyphicon-plus"></i> 新建订单</a>
						</s:if>
						<a href="javascript:order_report()"><i class="glyphicon glyphicon-list-alt"></i>
							导出报表</a>
					</div>
				
				
					<div class="row topx myMinh">

						<div class="spjz">

							<div class="panel panel-default" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>订单列表

									</h3>
								</div>
								<div class="panel-body x-scroll">

									<table class="table">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>订单号</th>
												<th>创建日期</th>
												<th>下单日期</th>
												<th>地区</th>
												<!--  <th>职级</th> -->
												<!--  <th>身份证号</th> -->
												<th>RM</th>
												<!--   <th>陪对方</th> -->
												<th>订单电话</th>
												<th>产品名称</th>
												<th>合伙企业</th>
												<th>产品渠道</th>
												<th>客户</th>
												<th>投资人</th>
												<th>认缴金额</th>
												<th>币种</th>
												<!--   <th>业绩金额</th> -->
												<th>产品系数</th>
												<th>标费</th>
												<th>状态</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="order_info">
											<%-- <s:if test="#request.status==1 ">
												<s:iterator value="#request.orderList" var="orderInfo">
													<tr class="default" id="cust_list" style="cursor: pointer;">
														<td >${orderInfo.order_no }</td>
														<td>${orderInfo.create_time }</td>
														<td>${orderInfo.area }</td>
														<td>${orderInfo.sales_name }</td>
														<td>${orderInfo.prod_name }</td>
														<td>${orderInfo.partner_com_name }</td>
														<td>${orderInfo.prod_type }</td>
														<td>${orderInfo.cust_name }</td>
														<td>${orderInfo.order_amount }</td>
														<td>${orderInfo.bizhong }</td>
														<td>${orderInfo.prod_diffcoe }</td>
														<td>${orderInfo.magt_fee1 }</td>
														<td>${orderInfo.dict_name }</td>
														<td><a
															href="javascript:order_detail('${orderInfo.order_no}','${orderInfo.order_version}')">详情</a></td>
														 <td>${prodInfo.prod_diffcoe }</td>
													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr>
													<th colspan="14" style="text-align: center;">${orderList }</th>
												</tr>
											</s:elseif> --%>
										</tbody>
									</table>
									<div class="pagin">
										<div class="message">
											共<i class="blue"><input style="border: none;width:20px;" id="tatolNum" value="${totalNum}"/></i>条记录
										</div>
										
									</div>
									
										<div id="page1" class="page"></div>
									
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

		<script>
			var _hmt = _hmt || [];
			(function() {
				var hm = document.createElement("script");
				hm.src = "//hm.baidu.com/hm.js?b3a3fc356d0af38b811a0ef8d50716b8";
				var s = document.getElementsByTagName("script")[0];
				s.parentNode.insertBefore(hm, s);
			})();
		</script>


		<!--end底部-->

		<script type="text/javascript">
			function tx() {
				$.ajax({
					type : "POST",
					url : "myPhotoSave.aspx",
					data : "tx=" + myFrame.window.tx,
					success : function(msg) {
						if (msg != "n") {
							$("#tou").attr('src', "tx/" + msg + ".png");
						}
					}
				});

			}
			function clockms(id) {
				var yz = $.ajax({
					type : 'post',
					url : 'mess.aspx',
					data : {
						id : id
					},
					cache : false,
					dataType : 'text',
					success : function(data) {

					},
					error : function() {
					}
				});
			}
			function order_detail_company(obj) {
				window.location.href = "order_Detail.action?order_no=" + obj;
			}
		</script>
		<script>
			$(function() {
				$("td").css("overflow", "hidden").css("text-overflow",
						"ellipsis");
			});
		</script>

	</div>
</body>
</html>