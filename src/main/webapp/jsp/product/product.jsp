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
<title>product</title>
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
	 	if(role_id==6){
			document.getElementById("add_prod").style.display="block";
		}
		
		$('#myTab li:eq(1) a').tab('show');
		queryPaging();
	});
	
	function resetProduct(){
		 $("#prod_status").val("");
		 $("#prod_type").val("");
		
		 $("#prod_name").val("");
		 $("#cor_org").val("");
		 $("#lp_name").val("");
		 $("#s_raise_date").val("");
		 $("#e_raise_date").val("");
	}

	function queryProduct(){
		var status = $('#state > .btn.active').val();
		var lp_name = $("#lp_name").val().trim();
		var prod_name = $("#prod_name").val().trim();
		var prod_status = $("#prod_status").val().trim();
		var cor_org = $("#cor_org").val().trim();
		var prod_type = $("#prod_type").val().trim();
		var s_raise_date = $("#s_raise_date").val().trim();
		var e_raise_date = $("#e_raise_date").val().trim();
		var PageNum = $("#PageNum").val().trim();
		var PageSize = $("#PageSize").val().trim();		
		
			
		$.ajax({
			url : 'prodSelect.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				//'status' : status,
				'prod_status':prod_status,
				'prod_name' : prod_name,
				'cor_org' : cor_org,
				'lp_name':lp_name,
				'prod_type' : prod_type,
				's_raise_date' : s_raise_date,
				'e_raise_date' : e_raise_date,
				'PageNum' : PageNum,
				'PageSize' : PageSize
			},
			success : function(data) {
				$("#prod_info").empty();				
				if (data.status == 1) {
				var prodInfo = data.list;
				var totalNum1 = data.totalNum1;
				$("#totalNum").val(totalNum1);
				$("#count_sum").val(totalNum1);
				//alert(document.getElementById("totalNum").value);
				queryPaging();
				
	        	for(var i=0;i<prodInfo.length;i++){
	        		var prod_info='<tr class="default" id="cust_list production_list" onclick="production_detail('+prodInfo[i].prod_id+')">'
	    				+'<td>'+prodInfo[i].prod_name+'</td>'
	    				+'<td>'+prodInfo[i].prodtypename+'</td>'
	    				+'<td>'+prodInfo[i].cor_org+'</td>'
	    			/* 	+'<td>'+prodInfo[i].invest_goal+'</td>' */
	    				+'<td>'+prodInfo[i].buy_fee+'</td>'
	    				+'<td>'+prodInfo[i].pay_amount+'</td>'
	    				+'<td>'+newDate(prodInfo[i].s_raise_date)+'</td>'
	    				+'<td>'+prodInfo[i].curname+'</td>'
	    				+'<td>'+prodInfo[i].prodstusname +'</td>'
	    				+'<td>'+prodInfo[i].prodchename +'</td>'
	    				+'</tr>';
	    				$("#prod_info").append(prod_info);
	        		}
				}else if(data.status == 0){
					$("#prod_info").append(data.list);
					$("#count_sum").val(0);
				}
				
			},error:function(result){
				//alert('系统异常,请稍后再试!');
				document.getElementById('content').innerHTML="系统异常,请稍后再试!";
        		$('#myal').modal('show');
			}
		});
	}
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

	function production_detail(obj) {
		window.location.href = "production_detail.action?prod_id=" + obj;
	}
	function del_tr(obj) {
		$(obj).parent().parent().hide();
	}

	function newDate(obj) {
		var newTime = new Date(obj); //毫秒转换时间  
		var year = newTime.getFullYear();
		var mon = newTime.getMonth()+1;  //0~11
		var day = newTime.getDate();
		var newDate = formate(year)+'-'+formate(mon)+'-'+formate(day);
		return newDate
	}
	
	function formate(d){
		return d>9?d:'0'+d;
		}
	
</script>

<script type="text/javascript">
	function queryPaging(){
 	 
		var totalNum = $("#totalNum").val();
		var PageNum = $("#PageNum").val();
		
		NavUtil.PageSize = $("#PageSize").val();
		NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
		
		NavUtil.bindPageEvent(loadData);
}

	function export_report(){
		var localFilePath = $('#localFilePath').val();
		window.open('export_prod.action', '_blank');
	}	
	
function loadData(){
	
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=document.getElementById("PageNum").value;
	//alert(PageNum);
	queryProduct();
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
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->
	 <input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
<%-- 	 <input type="hidden" name="PageNum1" id="PageNum" value="${PageNum}" /> --%>
<%-- 	 <input type="hidden" name="PageSize" id="PageSize" value="${PageSize}" /> --%>
	
<!-- 	<input type="hidden" name="totalNum" id="totalNum" value="50" /> -->
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
					<li class="active">产品</li>
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
				
					<!-- 	<div id="state" data-toggle="buttons-radio" class="btn-group" style="margin-bottom: 10px;width: 100%;" >
							<button  style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="" class="btn btn-sm btn-light-info active"> 全部</button>
							<button style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="1" class="btn btn-sm btn-light-info"> 募集中</button>
							<button style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="2" class="btn btn-sm btn-light-info"> 存续</button>
							<button style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="3" class="btn btn-sm btn-light-info"> 已退出</button>
						</div> -->

						<ul class="list-group">
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<span class="prod_state">募集状态</span> 
								<select id="prod_status" name="prod_status">  
							        <option value="">---请选择---</option>  
		                       		<s:iterator  value="#request.prodStus" var="prodStus">
								        <option value='<s:property value="#prodStus.dict_value"/>'><s:property value="#prodStus.dict_name"/></option>  
							  		</s:iterator>
					    		</select>
							</li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input type="text" value="" id="prod_name" name="prod_name" placeholder="请输入产品名称"
								style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input type="text" value="" id="lp_name" name="lp_name" placeholder="请输入合伙企业名称"
								style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<input type="text" value="" id="cor_org" name="cor_org" placeholder="请输入合作机构"
								style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<span class="cust_state">渠道</span>
								<select id="prod_type" name="prod_type">
							        <option value="">---请选择---</option>
		                       		<s:iterator  value="#request.prodDict" var="prodDict">
								        <option value='<s:property value="#prodDict.dict_value"/>'><s:property value="#prodDict.dict_name"/></option>  
							  		</s:iterator>
					    		</select>
							</li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp;
								<%-- <span>开始募集时间</span> --%>
								<input id="s_raise_date" placeholder="募集时间"
								name="s_raise_date" size="10" type="text" value="" readonly class="form_datetime"/>
							<!-- </li>
							<li class="list-group-item"><i class="fa fa-user-secret"></i>&nbsp; -->
								<%-- <span>募集初期结束时间</span> --%>
								--<input id="e_raise_date" placeholder="募集时间"
								name="e_raise_date" size="10" type="text" value="" readonly class="form_datetime"/>
							</li>
						</ul>
						
						<button class="btn btn-info top10" style="width: 45%;float:left;margin-right:2px; " onclick="resetProduct()">重置</button>
						<button class="btn btn-info top10" style="width: 45%;float:left;margin-left: 2px;" onclick="queryProduct()">查询</button>
					</div>
				<!-- </form> -->
				<div class="df"></div>
			</div>


			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<!-- <div class="myNav row">
						<a href="/OMS/product_add.action"  id="add_prod"  
							style="display: none;outline: none;"><i class="glyphicon glyphicon-plus"></i>
							添加产品</a>
				
						<a href="javascript:export_report()"><i class="glyphicon glyphicon-list-alt"></i>
							导出报表</a>
					</div>   -->
					<div class="myNav row">
						<a href="/OMS/product_add.action" id="add_prod"  style="display: none;height:1px;width:650px;outline: none;text-wrap:normal;"><i class="glyphicon glyphicon-plus"></i>
							添加产品</a> 
							<a href="javascript:export_report()"><i class="glyphicon glyphicon-list-alt"></i>
							导出报表</a>

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
												<th>渠道</th>
												<th>管理方</th>
												<!-- <th>产品描述</th> -->
												<th>产品总认缴</th>
												<th>产品总实缴</th>
												<th>开始募集时间</th>
												<th>币种</th>
												<th>产品状态</th>
												<th>审核状态</th>
												<!--  <th>产品系数</th>   -->

											</tr>
										</thead>
										<tbody id="prod_info">
											<s:iterator value="#request.prodList" var="prodInfo">
												<tr class="default" id="cust_list production_list"  onclick="production_detail('${prodInfo.prod_id}')">
													<td>${prodInfo.prod_name }</td>
													<td>${prodInfo.prodTypeName }</td>
													<td>${prodInfo.cor_org }</td>
												<%-- 	<td>${prodInfo.invest_goal }</td> --%>
													<td>${prodInfo.buy_fee }</td>
													<td>${prodInfo.pay_amount }</td>
													<td>${prodInfo.s_raise_date }</td>
													<td>${prodInfo.Curname }</td>
													<td>${prodInfo.prodStusName }</td>
													<td>${prodInfo.prodCheName }</td>
												</tr>
											</s:iterator>
										</tbody>
									</table>
									
<!-- 									<ul class="pagination pagination-centered"> -->
<!-- 										<li><a href="#">&laquo;</a></li> -->
<!-- 										<li><a href="#">1</a></li> -->
<!-- 										<li><a href="#">2</a></li> -->
<!-- 										<li><a href="#">3</a></li> -->
<!-- 										<li><a href="#">&raquo;</a></li> -->
<!-- 									</ul> -->

									<div class="pagin">
										<div class="message" id = "totalNumDiv">
											共<input id = "count_sum" value = "${totalNum}" style="width: 10%;border:none;outline: none;">条记录
										</div>										
									</div>
									<div id="page1" class="page"></div>
									
									<!-- <form action="production_detail.action" id="form2" name="form2" method="post">
										<input type="hidden" name="prod_id"/>
										<input type="hidden" name="prod_name"/>
										<input type="hidden" name="prodTypeName"/>
										<input type="hidden" name="invest_goal"/>
										<input type="hidden" name="buy_fee"/>
										<input type="hidden" name="pay_amount"/>
										<input type="hidden" name="s_raise_date"/>
										<input type="hidden" name="e_raise_date"/>
										<input type="hidden" name="Curname"/>
										<input type="hidden" name="prodCheName"/>
									</form> -->
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--end 右-->
		</div>


		<!--end主体-->
		<!-- <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="exampleModalLabel"><i class="fa fa-pencil-square-o"></i> 签到</h4>
      </div>
      <div class="modal-body" >       
       
         <h4 class="modal-title" id="exampleModalLabel"><i class="fa fa-pencil-square-o"></i> 签到</h4>
      
      </div>      
    </div>
  </div>
</div> -->
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