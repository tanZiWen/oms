<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_detail</title>
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
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script>
	var n = 1;
	var role_id = "${role_id}";
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8">
	
	
</script>
<script type="text/javascript">
$(function(){
});
function export_report(){
	var localFilePath = $('#localFilePath').val();
	//location.href="";
	window.open('export_report.action', '_blank');

}	

function f_exceport_report(){
	//location.href="";
	window.open('f_exceport_report.action', '_blank');

}
function saveit(src) { 
	I1.document.location = src; 
	savepic(); 
	} 
	function savepic() { 
	if (I1.document.readyState == "complete") { 
	I1.document.execCommand("saveas"); 
	} else { 
	window.setTimeout("savepic()", 10); 
	} 
	} 
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
<!--nav-->
<%@ include file="../header.jsp" %>


<!--主体-->
  		
      <div class="container m0 bod top70" id="zt">
		  	<div class="row">
				<div class="col-md-6 t0b0 ">
					 <ol class="breadcrumb t0b0">
					  <li><a href="#">首页</a></li>
					  <li class="active">报表</li>
					</ol>
				</div>
				<div class="col-md-6 t0b0 txtr">
				
				</div>
			</div>
			
            <div class="row top10 mym">
				<!--左-->
			    
<div class="col-md-4 myleft" style="width: 25%;">
    <div class="myleft-n">
       
         <a href="/OMS" data-toggle="modal" data-target="#exampleModal2">
            <img id="tou" src="image/person.png" class="f imgr20">
         </a>   
        <div class="f imgf20">
            <h4>报表管理</h4>
            
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
    <div class="myleft-n">
		  <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
         <i class="glyphicon glyphicon-search"></i>报表描述......<br>
         
        </div>
        <ul class="list-group">
					<s:iterator value="#request.report_list" var="report">
					<li class="list-group-item">
					<a href="javascript:${report.func_remark }()">${report.func_describle }</a>
					</li>
					</s:iterator>
						
						
		 </ul>
    </div>
    <div class="df"></div>
</div>

			
			
			  <!--右-->
			  <div class="col-md-8 myright" style="width: 75%;">
			  		<div class="myright-n" >
			  		<div id="export" class="myNav row">
			  		<a href="javascript:export_report()"><i class="glyphicon glyphicon-list-alt"></i>
							导出报表</a>
					</div>
						<div class="row topx myMinh"style="border:0;padding: 0;">
						
						<div class="spjz">
                        
		
				<div class="panel panel-default" style="width:100%;">
				
				   <div class="panel-heading">

				      <h3 class="panel-title" style="color: #a52410;">
				      <span class="glyphicon glyphicon-th"></span>报表
				      <span style="float: right;"></span></h3>
				   </div>
				   
				   <%-- <div class="panel-body">
				    <div class="input-group"> 
				        <div style="position: relative;"><input type="text" class="form-control"  style="width: 300px;height: 34px;" value="请输入电话号码"  onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''">
		              <button data-toggle="modal" type="submit" id="queryQuick" class="btn btn-primary m-l-5" style="outline: none;height: 34px;position: absolute;right:-42px;top: 0;border-bottom-left-radius: 0;border-top-left-radius: 0;">
			                 <span style="margin-right: 5px;" class="glyphicon glyphicon-search"></span>
		              </button>
		              </div>
	                </div> --%>
	                
				   		<div class="panel-body x-scroll">
						<table class="table">
						<thead id="thead">
						<tr  class="demo_tr">
						<th>产品编号</th>
						<th>产品名</th>
						<th>合伙企业</th>
						<th>产品经理</th>
						<th>募集状态</th>
						<th>投资状态</th>
						<th>币种</th>
						<th>认缴额(万元)</th>
						<th>投资期</th>
						<th>回收期</th>
						<th>费用类型</th>
						<th>费用金额</th>
						<th>费用比例</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>募集费用</th>
						</tr>
						</thead>
						<tbody id="tbody">
							 <s:iterator value="#request.list" var="lpList">
						<tr class="default">
							<td><s:property value="#lpList.prod_id" /></td>
							<td><s:property value="#lpList.prod_name" /></td>
							<td><s:property value="#lpList.partner_com_name" /></td>
							<td><s:property value="#lpList.real_name" /></td>
							<td><s:property value="#lpList.status_name" /></td>
							<td><s:property value="#lpList.invest_name" /></td>
							<td><s:property value="#lpList.currency_name" /></td>
							<td><s:property value="#lpList.pay_sum" /></td>
							<td><s:property value="#lpList.invest_year" /></td>
							<td><s:property value="#lpList.reback" /></td>
							<td><s:property value="#lpList.fee_type" /></td>
							<td><s:property value="#lpList.manage_fee" /></td>
							<td><s:property value="#lpList.fee_ratio" /></td>
							<td><s:property value="#lpList.start_date" /></td>
							<td><s:property value="#lpList.end_date" /></td>
							<td><s:property value="#lpList.raise_fee" /></td>
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
           <!--end 右-->
    </div>  


<!--end主体-->


<!--end主体-->

<!--底部-->
<nav class="foot navbar-inverse navbar-fixed-bottom">
	<ul class="list-inline">
      <li class="footer-ss">
      	<a href="javascript:void(0)" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content=" " data-original-title="" title="">更多 &nbsp;<i class="fa fa-angle-down"></i></a></li>
      <li class="footer-ss">在线反馈</li>
      <li class="footer-ss">帮助中心</li>
      <li>Copyright © 2016 帆茂</li>
    </ul>
    <ul class="list-inline text-right">
   	 	<li>
     
    	</li>
    </ul>
</nav>


</div></body></html>
<script type="text/javascript">
	function fin_exceport_report(){
		$.ajax({
			url : 'fin_exceport_report.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
			},
			success : function(data) {
				$('#export').empty();
				var a ='<a href="javascript:export_report()">'
				+'<i class="glyphicon glyphicon-list-alt"></i>导出报表</a>';
				$('#export').append(a);
				$('#thead').empty();
				var thead = "<tr class='demo_tr'><th>产品编号</th>"
				+"<th>产品名</th>  "
				+"<th>合伙企业</th>"
				+"<th>产品经理</th>"
				+"<th>募集状态</th>"
				+"<th>投资状态</th>"
				+"<th>币种</th>"
				+"<th>认缴额(万元)</th>"
				+"<th>投资期</th>        "
				+"<th>回收期</th>        "
				+"<th>费用类型</th>    "
				+"<th>费用金额</th>    "
				+"<th>费用比例</th>    "
				+"<th>开始时间</th>    "
				+"<th>结束时间</th>    "
				+"<th>募集费用</th> </tr>   ";
				$('#thead').append(thead);
				var list = data.list;
				$('#tbody').empty();
				if(data.desc==1){
					for(var i=0;i<list.length;i++){
						var tbody = " <tr class='default'> "
						+"		<td>"+list[i].prod_id+" /></td>             "
						+"		<td>"+list[i].prod_name+" /></td>           "
						+"		<td>"+list[i].partner_com_name+" /></td>    "
						+"		<td>"+list[i].real_name+" /></td>           "
						+"		<td>"+list[i].status_name+" /></td>         "
						+"		<td>"+list[i].invest_name+" /></td>         "
						+"		<td>"+list[i].currency_name+" /></td>       "
						+"		<td>"+list[i].pay_sum+" /></td>             "
						+"		<td>"+list[i].invest_year+" /></td>         "
						+"		<td>"+list[i].reback+" /></td>              "
						+"		<td>"+list[i].fee_type +"/></td>            "
						+"		<td>"+list[i].manage_fee+" /></td>          "
						+"		<td>"+list[i].fee_ratio+" /></td>           "
						+"		<td>"+list[i].start_date+" /></td>          "
						+"		<td>"+list[i].end_date +"/></td>            "
						+"		<td>"+list[i].raise_fee +"/></td>           "
						+"	</tr>";
						$('#tbody').append(tbody);
					}
				}else{
					alert("尚未找到数据");
				}
				
			},
			error : function(result) {
				alert('系统异常,请稍后再试!');
			}
		});
	}
	
function family_report(){
	$.ajax({
		url : 'family_report.action', //后台处理程序     
		type : 'post', //数据发送方式     
		dataType : 'json', //接受数据格式        
		data : {
		},
		success : function(data) {
			$('#export').empty();
			var a ='<a href="javascript:f_exceport_report()">'
			+'<i class="glyphicon glyphicon-list-alt"></i>导出报表</a>';
			$('#export').append(a);
			$('#thead').empty();
			var thead = "<tr class='demo_tr'><th>家族名称</th>"
			+"<th>客户名称</th>  "
			+"<th>产品名称</th>"
			+"<th>合伙企业</th>"
			+"<th>订单号</th>"
			+"<th>下单金额</th>"
			+"<th>销售名</th>"
			+"<th>产品类型</th>"
			+"<th>币种</th>        "
			+"<th>订单状态</th>        "
			+" </tr>   ";
			$('#thead').append(thead);
			var list = data.list;
			$('#tbody').empty();
			if(data.desc==1){
				for(var i=0;i<list.length;i++){
					var tbody = " <tr class='default'> "
					+"		<td>"+list[i].family_name+" </td>             "
					+"		<td>"+list[i].cust_name+" </td>           "
					+"		<td>"+list[i].prod_name+" </td>    "
					+"		<td>"+list[i].partner_com_name+" </td>           "
					+"		<td>"+list[i].order_no+" </td>         "
					+"		<td>"+list[i].order_amount+" </td>         "
					+"		<td>"+list[i].sales_name+" </td>       "
					+"		<td>"+list[i].prod_type+" </td>             "
					+"		<td>"+list[i].bizhong+" </td>         "
					+"		<td>"+list[i].ischecked+" </td>              "
					+"	</tr>";
					$('#tbody').append(tbody);
				}
			}else{
				alert("未找到数据")
			}
			
		},
		error : function(result) {
			alert('系统异常,请稍后再试!');
		}
	});
}
</script>