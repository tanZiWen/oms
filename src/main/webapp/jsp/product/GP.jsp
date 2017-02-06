<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>gp</title>
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
<script src="<%=request.getContextPath()%>/js/bootstrap-table.min.js"></script>
<script src="/OMS/js/gp.js"></script>
<script src="/OMS/js/Base64.js"></script>
<script src="/OMS/js.boots"></script>
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
	<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script>
$(function(){
		pagequery_gp();
});

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
	<!--nav-->
	<%@ include file="/jsp/header.jsp"%>
	
	<script type="text/javascript">
	
	</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--主体-->
		<input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
		<input type="hidden" name="PageNum1" id="PageNum" value="${PageNum }" />
		<input type="hidden" name="PageSize" id="PageSize" value="${PageSize }" />
	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="/OMS">首页</a></li>
					<li class="active">GP管理</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="" data-toggle="modal"
						data-target="#exampleModal2"> <img id="tou"
						src="/OMS/image/person.png" class="f imgr20">
					</a>
					<div class="f imgf20">
						<h4>GP管理</h4>

					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> GP查询<br>

					</div>

					<ul class="list-group">
						<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input id="gp_name" type="text" value="请输入GP名称"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
							<input id="gp_dept" type="text" value="请输入管理方"
							onblur="if(this.value=='')this.value=this.defaultValue"
							onfocus="if(this.value==this.defaultValue) this.value=''"
							style="width: 90%; border: none; outline: none;"></li>


						<a class="btn btn-info top10" href="javascript:GP_select(1)" style="width: 100%">
							点击查询 </a>
					</ul>





				</div>
				<div class="df"></div>
			</div>

			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
				<div class="myNav row">
					<s:if test="#request.role_id==6">
						<a href="skipgp.action" style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
							新建GP</a>
						</s:if>
                    <s:if test="#request.role_id==6||#request.role_id==7">
						<a href="javascript:gp_report()"><i class="glyphicon glyphicon-list-alt"></i>
							导出报表</a>
					    </s:if>

					</div>
					<div class="row topx myMinh" style="">

						<div class="spjz" style="">


							<div class="panel panel-default" style="width: 100%;">
								<div class="panel-heading">
									<h3 class="panel-title" style="color: #a52410;">GP列表</h3>
								</div>

								<div class="panel-body">

									<table id="table" class="table cust_table">
										<thead>
											<tr  class="demo_tr">
												<th >GP名称</th>
												<th>营业执照号</th>
												<th>法定代表人</th>
												<th>基金业协会备案号</th>
												<th>管理方</th>
												<th>gp状态</th>
												<!-- <th>注册地址</th> -->
											</tr>
										</thead>
										<tbody id="tbody">

											 <s:if test="#request.status==1 ">
												<s:iterator value="#request.list" var="lis">
													<tr class="default" onclick="gp_detail(${lis.gp_id})">
														<td>${lis.gp_name}</td>
														<td>${lis.bus_license }</td>
														<td>${lis.legal_resp }</td>
														<td>${lis.fund_no }</td>
														<td>${lis.gp_dept }</td>
														<td>${lis.status_name }</td>
														<%-- <td>${lis.regit_addr }</td> --%>
													</tr>
												</s:iterator>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr>
													<th colspan="6" style="text-align: center;">${gp_detail }</th>
												</tr>
											</s:elseif> 
											
										</tbody>
									</table>
									<div  class="pagin">
										<div class="message">
											共<input id="count"  style="width:25px;border-top:0px;border-right:0px;border-left:0px; border-bottom:1px " class="blue" value="${totalNum}"/>条记录
										</div>
										
									</div>
									
									<div id="page1" class="page"></div>
									
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

   function gp_detail(id){
	  /*  var order_no=$(id).find("td:first").text();
		alert(order_no);return;

		alert(id); */
		/* var gp_id = ""+id;
		gp_id = escape("h") */; 
		location.href="gp_detail.action?id="+id;
	}
   document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			GP_select(1);
		}
	};

</script>

		<script type="text/javascript">
var flag=0;
function add_member(flag){
	if (flag==2){
		$("#add_member").show();
	}
	else if(flag==1){
		$("#add_member").hide();
	}
	
	else if (flag==3){
		$("#member").show();
	}
	else if(flag==4){
		$("#family_list").show();
	}
}



</script>



	</div>
</body>
</html>