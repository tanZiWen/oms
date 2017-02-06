<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_demo</title>
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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-tagsinput.css">
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-button.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-tagsinput.min.js"></script>
<script src="<%=request.getContextPath()%>/js/moment.js"></script>
<script src="/OMS/js/cust.js"></script>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
	function showDistributionPage() {
		window.location.href = "/OMS/show_call_distrib_page.action";
	}
	var call_cust_id ;
	function del_call_cust(id) {
		call_cust_id = id;
		$('#showDelModal').modal('show');
	}
	function delCallCust() {
		var data = {call_cust_id: call_cust_id};
		$.ajax({
			url: '/OMS/del_call_distrib.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				alert(data.info);
				if(data.desc == 1) {
					$('#showDelModal').modal('hide');
					window.location.href = "/OMS/call_manage.action";
				}
			}
		});
	}
</script>
</head>
<body data-spy="scroll" data-target=".navbar-example">
	<%@ include file="../header.jsp"%>
	<input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
	<input type="hidden" name="PageNum1" id="PageNum" value="${PageNum}" />
	<input type="hidden" name="PageSize" id="PageSize" value="${PageSize}" />
	<div class="container m0 bod top70" id="cust_email">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="<%=request.getContextPath() %>/jsp/index.jsp">
                     <i class='fa  fa-home'></i>&nbsp;首页</a></li>
                    <li class="active">外拨管理</li>
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
						<h4>外拨管理</h4>
					</div>
					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
				</div>
				<div class="df"></div>
			</div>
		
			<!-- 右 -->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<div class="myNav row">
					<a href="javascript:showDistributionPage()" style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
						分配客户
					</a> 
					</div>
					<div class="row topx myMinh" style="">
						<div class="spjz" style="">
							<div class="panel panel-default" style="width: 100%;">
								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>邮件管理列表
									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<th>客服人员</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="tbody">
										<s:if test="#request.status==1 ">
										<s:iterator value="#request.callDistribList" var="lis">
											<tr class="default">
													<td>${lis.service_name}</td>
												    <td>
												    	<a href="/OMS/show_call_distrib_page.action?id=${lis.id}&service_id=${lis.service_id}">编辑</a>
												    	<a href="javascript:del_call_cust('${lis.id}')">删除</a>
												    </td>
												 </tr>
											</s:iterator>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr>
													<th colspan="5" style="text-align: center;">${cust_detail }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
								</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	
	 <div class="modal" id="showDelModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 640px;" >
					<div class="modal-content">
						<div class="modal-header">
			                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			                <h4 class="modal-title" id="myModalLabel">删除分配</h4>
						</div>
						<div class="modal-body" style="padding-top: 0;">
							<div style="margin-top: 15px" class="row" >
								<div class="text-center">
									<span>是否确认删除？</span>
								</div>
							</div>
						</div>
						<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">取消</button>
									<button style="margin-right: 40px;"
										class="btn btn-lg btn-success" onclick="delCallCust()">确认</button>
								</div>
							</div>
				</div>
			</div>
		</div>
</body>
</html>