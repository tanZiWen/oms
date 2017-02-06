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
<script src="<%=request.getContextPath()%>/js/call_center.js"></script>

<script src="/OMS/js/cust.js"></script>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
$(function(){
	pagequery();
});
function query(totalNum,PageNum,PageSize){
	NavUtil.PageSize = PageSize;
	NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
	NavUtil.bindPageEvent(loadData);
	$("#pagenum").val(totalNum);
}
function loadData(){
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=NavUtil.PageNum;
	pagequery();
}
function pagequery(){
	var data = {"PageNum": $('#PageNum').val(),"PageSize": 10};
	var order_no = $('#order_no').val();
	if(order_no == "订单编号") {
		order_no = "";
	}
	data.order_no = order_no;
	$.ajax({
		url:"query_call_record.action",
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
				var list = data.recordList;
				for(var i=0;i<list.length;i++){
					var tbody = '<tr class="default">'
						+ '<td>'+list[i].order_no+'</td>'
						+ '<td>'+list[i].cust_name+'</td>'
						+ '<td>'+list[i].real_name+'</td>'
						+ '<td>'+list[i].prod_name+'</td>'
						+ '<td><a href="javascript:show_recorded('+list[i].order_no+')">录音</a>'
						+ '</td></tr>';
					$('#tbody').append(tbody);
				}
			}
		},
	error: function() {
		alert("错误");
	}
		
	});
}
function show_recorded(order_no) {
	var data = {order_no: order_no};
	$.ajax({
		url:"call_recorded.action",
		type:"post",
		dataType : "json",
		data: data,
		success : function(data) {
			var callRecordList = data.callRecordList;
			$('#recordList').empty();
			var tbody = "";
			for(var i = 0; i< callRecordList.length; i++) {
				tbody += "<div><audio controls><source src='http://"+data.recordHost+"/%7B"+callRecordList[i].record_id+ "%7D.mp4', type='audio/mp4'></audio><div>";
			}
			tbody += "";
			console.log(tbody);
			$('#recordList').append(tbody);
			$('#recordedModal').modal('show');
		}
	});
	
}
</script>
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
                    <li class="active">录音管理</li>
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
						<h4>录音管理</h4>
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
			                <input id="order_no" type="text" value="订单编号"
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
					<div class="row topx myMinh" style="">
						<div class="spjz" style="">
							<div class="panel panel-default" style="width: 100%;">
								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>录音管理列表
									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<th>订单编号</th>
												<th>客户姓名</th>
												<th>RM姓名</th>
												<th>产品名称</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="tbody">
										<s:if test="#request.status==1 ">
										<s:iterator value="#request.callCustList" var="lis">
											<tr class="default">
													<td>${lis.cust_name}</td>
                                                    <td>${lis.real_name}</td>
												    <td>
												    	<a href="javascript:call_phone('${lis.cust_id},${lis.order_no} }')">录音</a>
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
	 <div class="modal" id="recordedModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 640px;">
				<div class="modal-content">
					<div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		                <h4 class="modal-title" id="myModalLabel">录音</h4>
	                </div>
					<div class="modal-body">
						<div id="recordList" class="text-center"></div>
			 		</div>
			 		<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">取消</button>
								</div>
							</div>
		 		</div>
	 		</div>
 		</div>
</body>
</html>