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
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-button.js"></script>
<script src="/OMS/js/cust.js"></script>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
</head>
<script type="text/javascript">
	var email = "";
	function email_edit(emailval) {
		$('#editEmailModal').modal('show');
		email = emailval;
	}
	function editEmail() {
		var pwd = $('#editEmailModal #pwd').val();
		var data = {email: email, pwd: pwd};
		$.ajax({
			url: '/OMS/edit_email_pwd.action',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				alert(data.info);
				if(data.desc == "1") {
					window.location.href = "/OMS/email_manage.action";				
				} 
			}
		});
	}
	function test_connect(emailval) {
		var data = {email: emailval};
		showLoad();
		$.ajax({
			url: '/OMS/email_connect_test.action',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				hideLoad()
				alert(data.info);
			}
		});
	}
</script>
<body data-spy="scroll" data-target=".navbar-example">
	<%@ include file="../header.jsp"%>
	
	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="<%=request.getContextPath() %>/jsp/index.jsp">
                     <i class='fa  fa-home'></i>&nbsp;首页</a></li>
                    <li class="active">邮件管理</li>
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
						<h4>邮件管理</h4>
					</div>
					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> 客户信息查询<br>
					</div>
				</div>
				<div class="df"></div>
			</div>
			<!-- 右 -->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
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
												<th>邮件名称</th>
												<th>操作</th>
												<th>测试</th>
											</tr>
										</thead>
										<tbody id="tbody">
										<s:iterator value="#request.emailList" var="lis">
											<tr class="default">
												<td>${lis.mail_user_username}</td>
											    <td><a href="javascript:email_edit('${lis.mail_user_username}')">修改密码</a></td>
											 	<td><a href="javascript:test_connect('${lis.mail_user_username}')">连接测试</a></td>
											 </tr>
											</s:iterator>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	
	<div class="modal" id="editEmailModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 640px;" >
					<div class="modal-content">
						<div class="modal-header">
			                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			                <h4 class="modal-title" id="myModalLabel">修改邮件密码</h4>
						</div>
						<div class="modal-body" style="padding-top: 0;">
							<div style="margin-top: 15px" class="row col-md-offset-2">
								<div class="col-md-8">
									<input type="password" id="pwd" name="pwd" placeholder="请输入邮件密码"
										class="form-control">
								</div>
							</div>
						</div>
						<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">取消</button>
									<button class="btn btn-lg btn-success"
										onclick="editEmail()">确认</button>
								</div>
							</div>
				</div>
			</div>
		</div>
</body>
</html>