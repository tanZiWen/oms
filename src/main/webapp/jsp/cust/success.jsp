<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
 	 //分页1
 	 
	
		var totalNum = $("#totalNum").val();
		var PageNum = $("#PageNum").val();
		
		NavUtil.PageSize = $("#PageSize").val();
		NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
		
		NavUtil.bindPageEvent(loadData);
		/* alert($("#PageSize").val()); */
		//document.getElementById("PageNum2").value=PageNum;
		//pagequery();

});

function loadData(){
	
	//分页2
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=document.getElementById("PageNum").value;
	pagequeryorg(PageNum);
	//document.getElementById("form1").submit();
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
					<li><a href="<%=request.getContextPath() %>/jsp/index.jsp"><i class='fa  fa-home'></i>&nbsp;首页</a></li>
           <li><a href="/OMS/cust.action">个人客户管理</a></li>
           <li  class="active"><a href="cust_detail.action?cust_id=${cust_id }&flag=1">个人--详情</a></li>
           <li  class="">操作成功</li>
				</ol>
			</div>
			
		</div>
			<!--左-->
			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
							<div class="panel panel-default" style="width: 100%;">
								<div class="panel-body">
									<table class="table cust_table">
										<tbody id="tbody">
											<tr>
												<td style="border:none;outline: none;">客户kyc录入成功</td>
												<td style="border:none;outline: none;"><a href="cust_detail.action?cust_id=${cust_id}&flag=1">返回</a></td>
											</tr>
										</tbody>
									</table>
									
								</div>
							</div>
							<!-- end	 -->
						
				</div>
			</div>
			<!--end 右-->
		


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