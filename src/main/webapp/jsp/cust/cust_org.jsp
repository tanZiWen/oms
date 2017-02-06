<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_org</title>
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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/pageStyle.css">
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="/OMS/js/org.js"></script>
<script>
	var n = 1;
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
	
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script type="text/javascript">
$(function(){
	 pagequeryorg();
});
function orginfo(totalNum,PageNum,PageSize){
	//分页1
	NavUtil.PageSize = PageSize;
	NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
	NavUtil.bindPageEvent(loadData);
}
function loadData(){
	//分页2
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=document.getElementById("PageNum").value;
	pagequeryorg(PageNum);
}
function org_report(){
	
	window.open('org_report.action', '_blank');

}
</script>
<style type="text/css">
.table>tbody>tr:hover>td, .table>tbody>tr:hover>th {
	background-color: #f5f5f5;
	cursor: pointer
}

.table>thead {
	background-color: #666666;
	color: white
}

.panel-body {
	padding-left: 0;
	padding-right: 0;
}

.panel-title {
	color: #a52410;
}
</style>
<%@ include file="/jsp/header.jsp"%>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->


<form action="pagequery.action" id="form">
	<!--主体-->
		<input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
		<input type="hidden" name="PageNum1" id="PageNum" value="${PageNum}" />
		<input type="hidden" name="PageSize" id="PageSize" value="${PageSize}" />
	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="/OMS">首页</a></li>
					<li class="active">机构客户管理</li>
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
						src="/OMS/image/person.png" class="f imgr20">
					</a>
					<div class="f imgf20">
						<h4>机构客户管理</h4>

					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> 机构客户查询<br>

					</div>

					<ul class="list-group">
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input  id="org_name" type="text" value="请输入机构客户名称"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input  id="org_license" type="text" value="请输入营业执照号"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>


						<botton  class="btn btn-info top10" onclick="org_select(1)" 	style="width: 100%"> 点击查询 </botton>
					</ul>





				</div>
				<div class="df"></div>
			</div>


			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
						
							<div class="myNav row">
							<s:if test="#request.role_id==4">
								<a href="<%=request.getContextPath()%>/skiporg.action"
									style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
									新建机构</a>
							</s:if>
							<s:if test="#request.role_id==1||#request.role_id==2||#request.role_id==3||#request.role_id==4||#request.role_id==5">
								<a href="javascript:org_report()"><i
									class="glyphicon glyphicon-list-alt"></i> 导出报表</a>
							</s:if>

						</div>
						
						<div class="row topx myMinh" style="">

						<div class="spjz" style="">


							<div class="panel panel-default" style="width: 100%;">
								<div class="panel-heading">
									<h3 class="panel-title" style="color: #a52410;">机构列表</h3>
								</div>

								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<th>公司名称</th>
												<th>营业执照注册号<br />(统一社会信用代码)
												</th>
												<th>法人</th>
												<th>成立日期</th>
												<th>成员人数</th>
												<th>客户状态</th>
											</tr>
										</thead>
										<tbody id="tbody">
											<s:if test="#request.status==1 ">
												<s:iterator value="#request.list" var="lis">
													<tr class="default" onclick="org_detail(${lis.org_id})">
														<td>${lis.org_name}</td>
														<td>${lis.org_license }</td>
														<td>${lis.org_legal }</td>
														<td>${lis.reg_date }</td>
														<td>${lis.org_members }</td>
														<td>${lis.state_name }</td>
													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr>
													<th colspan="5" style="text-align: center;">${org_detail }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<div class="pagin">
										<div class="message">
											共<input id="pagenum" style="width:20px;border: none;outline: none;" value="${totalNum}" />条记录
										</div>
										
									</div>
									<s:if test="#request.status==1">
									<div id="page1" class="page"></div>
									</s:if>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->
								</div>
							</div>
							<!-- end	 -->
						</div>
					</div>
				</div>
			</div>
			<!--end 右-->
		</div>


		<!--end主体-->


		<!--end主体-->

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
		</script>

		<script type="text/javascript">
			var flag = 0;
			function add_member(flag) {
				if (flag == 2) {
					$("#add_member").show();
				} else if (flag == 1) {
					$("#add_member").hide();
				}

				else if (flag == 3) {
					$("#member").show();
				} else if (flag == 4) {
					$("#family_list").show();
				}
			}
			/* function family_detail() {
				window.location.href = "cust_org_detail.jsp";
			}
			$(function() {
				$(".default").attr("onclick", "family_detail()");
			}); */
			function org_detail(id){
				location.href="org_detail.action?id="+id;
			}
			
		</script>



	</div>
	</form>
</body>
</html>