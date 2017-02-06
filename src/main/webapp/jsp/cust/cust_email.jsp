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
$(function(){
	statusVal=0;
	/* $('.buttonStatus').bind("click", function() {
		statusVal = $(this).val();
		cust_search(1);
	});  */
	pagequery();
});
function query(totalNum,PageNum,PageSize){
	NavUtil.PageSize = PageSize;
	NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
	NavUtil.bindPageEvent(loadData);
}
function loadData(){
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=NavUtil.PageNum;
	pagequery();
}
function accessory_detail(files) {
	$('#filenames').html(files)
	$('#showAccessoryModal').modal('show');
}

function content_detail(i) {
	$('#filenames').html(maillist[i].mail_content)
	$('#showAccessoryModal').modal('show');
}
//初始化
var maillist;
function pagequery(){
	var data = {"PageNum": $('#PageNum').val(),"PageSize": 10};
	var cust_name = $('#cust_name').val();
	var subject = $('#subject').val();
	if(cust_name == "请输入客户名称") {
		cust_name = "";
	}
	if(subject == "请输入邮件主题") {
		subject = "";
	}
	data.cust_name = cust_name;
	data.subject = subject;
	$.ajax({
		url:"query_cust_email.action",
		type:"post",
		dataType : "json",
		data: data,
		success : function(data) {
			$("#tbody").empty();
			$('#page1').empty();
			query(data.totalNum, data.pagenum, 10);
			if(data.totalNum==0){
				$('#page1').empty();
			}
			if(data.status==1){
				var list = data.list;
				maillist = list;
				for(var i=0;i<list.length;i++){
					var tbody= '<tr class="default">'
						+ '<td>'+list[i].mail_receive_name+'</td>'
						+ '<td>'+list[i].mail_subject+'</td>'
						+ '<td>'+moment(list[i].c_time).format("YYYY-MM-DD")+'</td>'
						+ "<td><a href=javascript:accessory_detail('"+list[i].mail_files_name+"')>详情</a></td>"
						+ "<td><a href=javascript:content_detail("+i+")>详情</a></td>"
						+ "<td><a href=javascript:transmit('"+list[i].mail_subjectid+"')>转发</a></td>"
						+ ' </tr>';
					$('#tbody').append(tbody);
				}
				
				//alert(a);
			}
		},
	error: function() {
		alert("错误");
	}
		
	});
}
function transmit(subjectid) {
	window.location.href ="send_email_page.action?subjectid="+subjectid;
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
                    <li class="active">客户邮件</li>
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
						<h4>客户邮件</h4>
					</div>
					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> 客户信息查询<br>
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
			                <input id="subject" type="text" value="请输入邮件主题" 
			                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
			                style="width: 90%;border:none;outline: none;">
			            </li>
			            <li class="list-group-item">
					       <a class="btn btn-info top10" href="javascript:pagequery()" style="width: 100%">
					         点击查询
					       </a>
		       			</li>
	       			</ul>
				</div>
				<div class="df"></div>
			</div>
		
			<!-- 右 -->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<div class="myNav row">
					<a href="javascript:addEmail()" style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
						添加邮件
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
												<th>客户姓名</th>
												<th>邮件主题</th>
												<th>发送时间</th>
												<th>附件</th>
												<th>正文</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="tbody">
										<s:if test="#request.status==1 ">
										<s:iterator value="#request.list" var="lis">
											<tr class="default">
													<td>${lis.mail_receive_name}</td>
                                                    <td>${lis.mail_subject}</td>
													<td>${lis.c_time}</td>
												    <td><a href="javascript:accessory_detail('${lis.mail_files_name}')">详情</a></td>
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
									<div class="pagin">
										<div class="message">
											共<input id = "pagenum" value = "${totalNum}" readOnly="true" style=" width: 10%;border:none;outline: none;">条记录
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
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	 <div class="modal" id="showAccessoryModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 640px;" >
					<div class="modal-content">
						<div class="modal-header">
			                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			                <h4 class="modal-title" id="myModalLabel">详情</h4>
						</div>
						<div class="modal-body" style="padding-top: 0;">
							<div style="margin-top: 15px" class="row" >
								<div class="text-left" style="word-wrap: break-word; word-break: break-all; margin-left: 20px;">
									<label id="filenames"></label>
								</div>
							</div>
						</div>
						<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">关闭</button>
								</div>
							</div>
				</div>
			</div>
		</div>
</body>
</html>