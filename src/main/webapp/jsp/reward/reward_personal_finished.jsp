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
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/pageStyle.css">
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script>
$(function(){
	statusVal=0;
	$('.buttonStatus').bind("click", function() {
		statusVal = $(this).val();
		cust_search(1);
	});
	query("${totalNum}", "${PageNum}", "10");
});
//初始化
function pagequery(rm, product, area){
	var data = {"PageNum":NavUtil.PageNum, "PageSize":10};
	if(rm=="请输入销售名"){
		rm="";
	}
	if(rm) {
		data.rm = rm;
	}
	if(product) {
		data.product = product;
	}
	if(area) {
		data.area = area;
	}
	$.ajax({
		url:"reward_list.action",
		type:"post",
		dataType : "json",
		data:data,
		
		success : function(data) {
			$("#tbody").empty();
			$('#page1').empty();
			query(data.totalNum, NavUtil.PageNum, 10);
			if(data.totalNum==0){
				$('#page1').empty();
			}
			if(data.status==1){
				var list = data.list;
				
				for(var i=0;i<list.length;i++){
					var tbody= '<tr class="default">'
						+ '<td>'+list[i].sales_name+'</td>'
						+ '<td>'+list[i].area+'</td>'
						+ '<td>'+list[i].prod_name+'</td>'
						+ '<td>'+list[i].order_time+'</td>'
						+ '<td>'+list[i].order_amount+'</td>'
						+ '<td>'+list[i].prod_diffcoe+'</td>'
						+ '<td>'+list[i].return_coe+'</td>'
						+ '<td>'+list[i].sum_reward+'</td>'
						+ '<td>'+list[i].has_reward+'</td>';
						if(list[i].quarter_index) {
							tbody += '<td>'+list[i].quarter_index+'</td>'
						}else {
							tbody += '<td> </td>'
						}
						if(list[i].quarter_deduction) {
							tbody += '<td>'+list[i].quarter_deduction+'</td>'
						}else {
							tbody += '<td> </td>'
						}
						tbody += ' </tr>';
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

$('#search', window.parent.document).click(function(){
	var rm=$('#rm', window.parent.document).val();
	var product=$('#product', window.parent.document).val();
	var area=$('#area', window.parent.document).val();
	pagequery(rm, product, area);
});

function query(totalNum,PageNum,PageSize){
	NavUtil.PageSize = PageSize;
	NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
	NavUtil.bindPageEvent(loadData);
}
function loadData(){
	pagequery();
}
</script>
</head>
<body>
	<!-- 奖金发放-个人 -->
	<div class="panel panel-default" style="width: 100%;">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>发放完成-个人列表
			</h3>
		</div>
		<div class="panel-body" style="overflow:auto;">
			<table class="table cust_table" id="reward_count_save"
				style="font-size: 10px;">
				<thead>
					<tr class="demo_tr">
						<th>销售</th>
						<th>地区</th>
						<th>产品名称</th>
						<th>下单时间</th>
						<th>下单金额</th>
						<th>产品系数</th>
						<th>回款系数</th>
						<th>可发奖金</th>
						<th>应发奖金</th>
						<th>责任金(季度)</th>
						<th>剩余责任金</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<s:if test="#request.status==1 ">
						<s:iterator value="#request.rewardList" var="rewardLi">
							<tr class="default" id="cust_list" style="cursor: pointer;">
								<td>${rewardLi.sales_name }</td>
								<td>${rewardLi.area }</td>
								<td>${rewardLi.prod_name }</td>
								<td>${rewardLi.order_time }</td>
								<td>${rewardLi.order_amount }</td>
								<td>${rewardLi.prod_diffcoe }%</td>
								<td>${rewardLi.return_coe }%</td>
								<td>${rewardLi.sum_reward }</td>
								<td>${rewardLi.has_reward }</td>
								<td>${rewardLi.quarter_index }</td>
								<td>${rewardLi.quarter_deduction }</td>
							</tr>
						</s:iterator>
					</s:if>
				</tbody>
			</table>
			<div class="pagin">
				<div class="message">共<input id = "pagenum" value = "${totalNum}" readOnly="true" style=" width: 10%;border:none;outline: none;">条记录</div>
			</div>
			<div id="page1" class="page"></div>
		</div>
	</div>
</body>
</html>

