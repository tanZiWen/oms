<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>prod_diffcoe</title>
<meta name="description" content="帆茂投资">
<meta name="keywords" content="帆茂投资">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pageStyle.css">
<%@ include file="../header.jsp"%>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
String now_date = df.format(new Date());
%>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script type="text/javascript">
	var role_id = "${role_id}";
</script>
<script>
	$(function() {
		query_diffcoe();
	});
	
</script>
<script type="text/javascript">
$(function(){
	$(".form_datetime").datetimepicker({
		 format: 'yyyy-mm-dd',  
       weekStart: 1,  
       autoclose: true,  
       startView: 2,  
       minView: 2,  
       forceParse: false,  
       language: 'zh-CN'  
	});
	
});

	
	
	
	function formate(d){
		return d>9?d:'0'+d;
		}
	
</script>

<script type="text/javascript">
	function query_diffcoe(){
		$.ajax({
			url : 'query_diffcoe.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data:{"PageNum":$('#PageNum').val(),"PageSize":$('#PageSize').val()},
				//'json':json,
			success : function(data) {
				$('#count_sum').val(data.totalNum);
				var totalNum = data.totalNum;
				if(totalNum==0){
					$('#page1').empty();
					$('#prod_info').empty();
				}else{
					select_diffcoeing(data.PageNum,data.totalNum);
					var list = data.list;
					$('#prod_info').empty();
					for(var i=0;i<list.length;i++){
						var tr = '<tr class="default"  >'
							+'<td>'+list[i].prod_name+'('+list[i].partner_com_name+')'+'</td>'
							+'<td>'+list[i].prod_diffcoe+'</td>'
							+'</tr>';
						$('#prod_info').append(tr);
					}
				}
			},error:function(result){
				alert("操作失败");
			}
		});
}
function query_diffcoeing(PageNum,totalNum){
	NavUtil.PageSize = $("#PageSize").val();
	NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
	NavUtil.bindPageEvent(loadData);
}
function loadData(){
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=document.getElementById("PageNum").value;
	query_diffcoe()
}


function select_diffcoe(pageNum){
	var prod_name = $('#prod_name').val().trim();
	if(prod_name=='请输入产品名称'){
		prod_name="";
	}
	$.ajax({
		url : 'select_diffcoe.action', //后台处理程序     
		type : 'post', //数据发送方式     
		dataType : 'json', //接受数据格式        
		data:{"PageNum":pageNum,"PageSize":$('#PageSize').val(),
				"prod_name":prod_name
		},
			//'json':json,
		success : function(data) {
			$('#count_sum').val(data.totalNum);
			var totalNum = data.totalNum;
			if(totalNum==0){
				$('#page1').empty();
				$('#prod_info').empty();
			}else{
				select_diffcoeing(data.PageNum,data.totalNum);
				var list = data.list;
				$('#prod_info').empty();
				for(var i=0;i<list.length;i++){
					var tr = '<tr class="default"  >'
						+'<td>'+list[i].prod_name+'</td>'
						+'<td>'+list[i].prod_diffcoe+'</td>'
						+'</tr>';
					$('#prod_info').append(tr);
				}
			}
			
		},error:function(result){
			alert("操作失败");
		}
	});
}
function select_diffcoeing(PageNum,totalNum){
NavUtil.PageSize = $("#PageSize").val();
NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
NavUtil.bindPageEvent(loadData1);
}
function loadData1(){
$("#totalNum").val(NavUtil.totalNum);
$("#PageNum").val(NavUtil.PageNum);
var PageNum=document.getElementById("PageNum").value;
select_diffcoe(PageNum);
}

document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		select_diffcoe(1);
	}
};
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
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->
	 <input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
	 <input type="hidden" name="PageNum1" id="PageNum" value="1" />
	 <input type="hidden" name="PageSize" id="PageSize" value="10" />
	<!--主体-->
	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="<%=request.getContextPath()%>/jsp/index.jsp">
							<i class='fa  fa-home'></i>&nbsp;首页
					</a></li>
					<li class="active">产品系数</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="#" data-toggle="modal"
						data-target="#exampleModal2"> <img id="tou"
						src="/OMS/image/person.png" class="f imgr20">
					</a>
					<div class="f imgf20">
						<h4>产品管理</h4>

					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<!-- <form action="prodSelect.action" id="form1" name="form1"
					method="post"> -->
					<div class="myleft-n">
						<div class="alert alert-warning top20" role="alert"
							style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
							<i class="glyphicon glyphicon-search"></i> 产品信息查询<br>

						</div>
				
					

						<ul class="list-group">
							
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input type="text" value="" id="prod_name" name="prod_name" placeholder="请输入产品名称"
								style="width: 90%; border: none; outline: none;"></li>
							
							
						</ul>
						
						<button class="btn btn-info top10" style="width: 90%;float:left;margin-left: 2px;" onclick="select_diffcoe(1)">查询</button>
					</div>
				<!-- </form> -->
				<div class="df"></div>
			</div>


			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					
					<div class="myNav row">
						

					</div>
					
					<div class="row topx myMinh">

						<div class="spjz">

							<div class="panel panel-default" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>产品列表

									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table" id="prodTab">
										<thead>
											<tr class="demo_tr">
												<th>产品名称</th>
												<th>产品系数<th>
											</tr>
										</thead>
										<tbody id="prod_info">
											
										</tbody>
									</table>
									


									<div class="pagin">
										<div class="message" id = "totalNumDiv">
											共<input id = "count_sum" value = "" style="width: 10%;border:none;outline: none;">条记录
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





	</div>
</body>
</html>