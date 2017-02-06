<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_distribution</title>
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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/pageStyle.css">
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-button.js"></script>
<script src="/OMS/js/cust.js"></script>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
	$(function() {
		statusVal = -1;
		$('.buttonStatus').bind("click", function() {
			statusVal = $(this).val();
			distribution_search(1);
		});
		distributionPageQuery();
	});

	function openTansferDialog(customerId, customerName, salesName) {
		$('#customerId').val(customerId);
		$('#customerName').text(customerName);
		$('#salesName').text(salesName);
		$('#transferDialog').modal('show');
		
		$.ajax({
			url : "transfersales_list.action",
			type : "get",
			dataType : "json",
			success : function(response) {
				$("#candidateSalesTbody").empty();
				if (response.status == 1) {
					var list = response.list;
					for (var i = 0; i < list.length; i++) {
						console.log("salesId", list[i].user_id);
						var tbody = '<tr class="default">'
								+ '<td>'+list[i].real_name+'</td>'
								+ '<td>' + '</td>'
								+ '<td>' + '</td>'
								+ '<td><a href="javascript:doTransfer(\'' + customerId
								+ '\',\'' + list[i].user_id + '\');">分配给Ta</a></td>'
								+ ' </tr>';
						$("#candidateSalesTbody").append(tbody);
					}
				}
				if (response.status == 0) {
					var tbody = '<tr >'
							+ '<th colspan="12" style="text-align:center;">'
							+ response.list + '</th>' + '</tr>';
					$("#candidateSalesTbody").append(tbody);
				}

			}
		});
	}
	
	function doTransfer(customerId, salesId) {
		$.ajax({
			url : "transfer_sales.action",
			type : "post",
			dataType : "json",
			data : {
				"cust_id" : customerId,
				"sales_id" : salesId,
			},
			success : function(response) {
				$("#candidateSalesTbody").empty();
				if(response.desc == 1) {
					alert("分配成功");
					location.href = "cust_distribution.action";
				} else {
					alert("分配失败");
				}
			}
		});
	}
	function query(totalNum, PageNum, PageSize) {
		NavUtil.PageSize = PageSize;
		NavUtil.setPage("page1", parseInt(totalNum), parseInt(PageNum));
		NavUtil.bindPageEvent(loadData);
	}
	function loadData() {

		//分页2

		$("#totalNum").val(NavUtil.totalNum);
		$("#PageNum").val(NavUtil.PageNum);
		var PageNum = NavUtil.PageNum;
		distributionPageQuery();

	}
	//绑定ENTER事件
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			distribution_search(1);
		}
	};
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<div class="modal fade" id="transferDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<input type="hidden" id="customerId"></input>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">重新分配销售人员</h4>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<div class="cos-xs-12">
								<div class="col-xs-2 text-right text-default" style="margin-right:0px;margin-left:0px">客户姓名:&nbsp;</div>
								<div id="customerName" class="col-xs-4 text-left text-default" style="margin-right:0px;margin-left:0px"></div>
								<div class="col-xs-2 text-right text-default" style="margin-right:0px;margin-left:0px">原销售姓名:&nbsp;</div>
								<div id="salesName" class="col-xs-4 text-left text-default" style="margin-right:0px;margin-left:0px"></div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<table class="table cust_table">
									<thead>
										<tr class="demo_tr">
											<th>销售姓名</th>
											<th></th>
											<th></th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="candidateSalesTbody">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!-- <div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal">完成</button>
				</div> -->
			</div>
		</div>
	</div>
	<!--nav-->
	<%@ include file="../header.jsp"%>

        <input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
		<input type="hidden" name="PageNum1" id="PageNum" value="${PageNum}" />
		<input type="hidden" name="PageSize" id="PageSize" value="${PageSize}" />
	<!--主体-->

	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="<%=request.getContextPath() %>/jsp/index.jsp">
                     <i class='fa  fa-home'></i>&nbsp;首页</a></li>
                    <li class="active">客户分配</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="/OMS/cust.action" data-toggle="modal"
						data-target="#exampleModal2"> <img id="tou"
						src="<%=request.getContextPath() %>/image/person.png" class="f imgr20">
					</a>
					<div class="f imgf20">
						<h4>客户分配</h4>

					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> 客户信息查询<br>

					</div>
					<div id="state"  data-toggle="buttons-radio" class="btn-group" style="margin-bottom: 10px;width: 100%;" >
							<button style="width: 33.3333%;float: left;text-align: center;padding-left:0;padding-right:0;" value="-1" class="buttonStatus btn btn-sm btn-light-info active"> 全部</button>
							<button style="width: 33.3333%;float: left;text-align: center;padding-left:0;padding-right:0;" value="0" class="buttonStatus btn btn-sm btn-light-info"> 公共池客户</button>
							<button style="width: 33.3333%;float: left;text-align: center;padding-left:0;padding-right:0;" value="1" class="buttonStatus btn btn-sm btn-light-info"> 非公共池客户</button>
						</div>
					        <ul class="list-group">
					            <li class="list-group-item">
					
					                <i class="fa-home"></i>&nbsp;
					                <input id="cust_name" type="text" value="请输入客户名称" 
					                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
					                style="width: 90%;border:none;outline: none;">
					            </li>
					            <li class="list-group-item">
					                <i class="fa-home"></i>&nbsp;
					                <input id="cust_cell" type="text" value="请输入电话号码" 
					                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
					                style="width: 90%;border:none;outline: none;">
					            </li>
					         <s:if test="#request.role_id==2||#request.role_id==3||#request.role_id==4||#request.role_id==5"> 
					          <li class="list-group-item">
					                <i class="fa-home"></i>&nbsp;
					                <input id="sales_name" type="text" value="请输入销售名" 
					                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
					                style="width: 90%;border:none;outline: none;">
					            </li>
					         </s:if>   
					            <li class="list-group-item">
					
					         <i class="fa-home"></i>&nbsp;
					         <span class="cust_state">级别</span>
					         <select id="cust_level" onblur="if(this.value=='')this.value=this.defaultValue" style='height:28px;'>
					         <option value="" class="active">-请选择-</option>
					         <option value="1">已见面</option>
					         <option value="2">已成交</option></select>
					            </li>
					          
					        </ul>
					
					       <a class="btn btn-info top10" href="javascript:distribution_search(1)" style="width: 100%">
					         点击查询
					       </a>

				</div>
				<div class="df"></div>
			</div>

			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<div class="row myMinh" style="">

						<div class="spjz" style="">

							<div class="panel panel-default" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>客户资源列表
									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<th>客户姓名</th>
												<th>销售名</th>
												<th>是否公共资源池客户</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="tbody">
										<s:if test="#request.status==1">
										<s:iterator value="#request.list" var="lis">
											<tr class="default">
													<td><s:property value="#lis.cust_name" /></td>
													<td>${lis.real_name}</td>
													<td><s:property value="#lis.cust_belong_state" /></td>
													<td>
														<a href="javascript:openTansferDialog('${lis.cust_id}','${lis.cust_name}','${lis.real_name}');">重新分配</a>
													</td>
												 </tr>
											</s:iterator>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr>
													<th colspan="5" style="text-align: center;">${cust_detail}</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<div class="pagin">
										<div class="message">
											共<input id = "pagenum" value = "${totalNum}" readOnly style=" width: 10%;border:none;outline: none;">条记录
										</div>
										
									</div>
									<div id="page1" class="page"></div>
								</div>
								</div>
							</div>


						</div>
					</div>
				</div>
			</div>
			<!--end 右-->
		</div>
<input id ="custname" name="custname" type="hidden" value='' />


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
    (function () {
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
            type: "POST", url: "myPhotoSave.aspx", data: "tx=" + myFrame.window.tx, success: function (msg) {
                if (msg != "n") {
                    $("#tou").attr('src', "tx/" + msg + ".png");
                }
            }
        });

    }
   function clockms(id) {
       var yz = $.ajax({
           type: 'post',
           url: 'mess.aspx',
           data: {id:id},
           cache: false,
           dataType: 'text',
           success: function (data) {
              
           },
           error: function () { }
       });
   }
   </script>
</body>
</html>