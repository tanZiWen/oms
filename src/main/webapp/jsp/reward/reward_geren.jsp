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
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
//点击多条件查询
$('#search', window.parent.document).click(function(){
	var rm=$('#rm', window.parent.document).val();
	var product=$('#product', window.parent.document).val();
	var area=$('#area', window.parent.document).val();
	if(rm=="请输入销售名"){
		rm="";
	}

	$.ajax({
		url : 'reward_count_search.action', //后台处理程序     
		type : 'post', //数据发送方式     
		dataType : 'json', //接受数据格式        
		data : {
			'rm' : rm,
			'product' : product,
			'area' : area,
		},
		success : function(data) {
			$("#reward_info").empty();
		/* 	window.parent.main.document.location.reload(); */
			 if (data.status == 1) {
				var rewardInfo = data.rewardList;
				for (var i = 0; i < rewardInfo.length; i++) {
					//判断是否为空
					if (rewardInfo[i].order_no == null
							|| rewardInfo[i].order_no == "") {
						rewardInfo[i].order_no = " ";
					}
					if (rewardInfo[i].sales_name == null
							|| rewardInfo[i].sales_name == "") {
						rewardInfo[i].sales_name = " ";
					}
					if (rewardInfo[i].arer == null
							|| rewardInfo[i].arer == "") {
						rewardInfo[i].arer = " ";
					}
					if (rewardInfo[i].pro_name == null
							|| rewardInfo[i].pro_name == "") {
						rewardInfo[i].pro_name = " ";
					}
					if (rewardInfo[i].order_amount == null
							|| rewardInfo[i].order_amount == "") {
						rewardInfo[i].order_amount = " ";
					}
					if (rewardInfo[i].magt_feet == null
							|| rewardInfo[i].magt_feet == "") {
						rewardInfo[i].magt_feet = " ";
					}
					//自动生成	
					var reward_info = '<tr class="default" id="cust_list ">'
							+ '<td>'
							+ '<input name="items" value="'
							+rewardInfo[i].reward_id
							+'"  type="checkbox">'
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].order_no
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].sales_name
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].area
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].prod_name
							+ '</td>'
							
							+ '<td>'
							+ rewardInfo[i].order_amount
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].magt_fee
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].return_coe
							+ '%</td>'
							+ '<td>'
							+ rewardInfo[i].return_money
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].sum_reward
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].reserve_reward
							+ '</td>'
							+ '<td>'
							+ rewardInfo[i].has_reward
							+ '</td>' 
							<%-- + '<td><a href="<%=request.getContextPath() %>
	/jsp/reward/reward_set_1.jsp"><span class="glyphicon glyphicon-cog"></span></a></td>' --%>
														+ '</tr>';
												$("#reward_info").append(
														reward_info);
											}
				//
			var reward_info =' 	<tr> '
				+' <td colspan="13" style="text-align: center;"><a  '
				+'	 style="width: 20%" id="reward_save" '
				+'	href="javascript:void(0);" onclick="saveReward()" '
				+'	style="outline: none;"> 计算奖金 </a></td> '
				+'	</tr> ';
			$("#reward_info").append(reward_info);
			$("#reward_save").addClass("btn btn-info top10");
										} else {
											var reward_info = '<tr class="default" id="cust_list " >'
													+ '<th colspan="13" style="text-align: center ;">'
													+ " 未查到相关数据"
													+ '</th>'
													+ '</tr>';
											$("#reward_info").append(
													reward_info);
										}
									},
									error : function(result) {
										alert('系统异常,请稍后再试!');
									}
								});
					});

	//点击保存已计算保存
				function saveReward() {
					
						//获取已勾选的数据
						var reward_id="";
						if ($("input[name='items']").length <= 0) {
							alert("当前没有可保存的奖金");
							return false;
						} 
						if ($("input[name='items']:checkbox:checked").length > 0) {
							//获取选中按钮的值
							$("input[name='items']:checkbox:checked").each(
									function() {
										reward_id += $(this).val() + ",";
									})
						} else {
							alert('请您先选择!');
							return false;
						}
						var r = confirm("确认保存计算奖金吗？")
						if (r == false) {
							return false;
						}
						//发送后台处理
						$.ajax({
							url : 'save_reward.action', //后台处理程序     
							type : 'post', //数据发送方式     
							dataType : 'json', //接受数据格式        
							data : {
								'reward_id' : reward_id,
							},
							success : function(data) {
								if(data.status==1){
									alert("奖金计算成功!");
								window.location.href = "reward_count.action";
								}else{
									alert("奖金计算失败!");
									window.location.href = "reward_count.action";
								}
								
							},
							error : function(result) {
								alert('系统异常,请稍后再试!');
							}
						});
					}
	
	//批次分组查询
					 function subgo(sign) {
						   var size="";
						   if(sign==-1){
							   size=$("#minbatch").val();
						   }
						   if(sign==1){
							   size=$("#maxbatch").val();
						   }
						
						   $.ajax({
								url : 'rewardbatch.action', //后台处理程序     
								type : 'post', //数据发送方式     
								dataType : 'json', //接受数据格式        
								data : {
									'sign' : sign,
									'size' : size,
								},
								success : function(data) {
									  
									if (data.status == 1) {
										$("#countbatch").empty();
										var rewardInfo = data.reservecount;
										var reward_info='<table><tr align="center"><td><button onclick="subgo('+-1+')"><<</button></td>';
										for (var i = 0; i < rewardInfo.length; i++) {
											 reward_info+='<td><a '
												+' href="search_reward_list.action?count_batch='
												+rewardInfo[i].count_batch
												+'" target="_top" '
												+'	class="btn btn-danger" style="display: inline-block;">'
												+rewardInfo[i].count_batch 
												+'</a></td>';
											 
										}
										reward_info+='<td><button onclick="subgo('+1+')">>></button></td></tr></table>';
										 $("#countbatch").append(reward_info);
											   $("#minbatch").val(data.minsize);
											   $("#maxbatch").val(data.maxsize);
									}else{
										if(data.status==2){
										/* alert("没有更多数据!"); */
										}else{
											alert("请重新登陆!");
											 window.parent.parent.location.href = "index.jsp";
										}
									}
								},
								error : function(result) {
									alert('系统异常,请稍后再试!');
								} 
						   });
						
					}
						$(function(){ 
						/* 	$("#minbatch").val("0");
						$("#maxbatch").val("3");
						subgo('-1'); */
						})				 
	
						window.onload = function(){
							$('#pro', window.parent.document).show();
						}	
						
				
</script>
</head>
<body>

	<!-- 奖金发放-个人 -->
	<div class="panel panel-default" style="width: 100%;">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>奖金发放-个人列表
			</h3>
		</div>
		<div class="panel-body" style="overflow:auto;">
			<table class="table cust_table" id="reward_count_save"
				style="font-size: 10px;">
				<thead>
					<tr class="demo_tr">
						<th><input type="checkbox" name="allChecked" id="allChecked"
							onclick="DoCheck()" />全选</th>
						<th>订单号</th>
						<th>销售</th>
						<th>地区</th>
						<th>产品名称</th>
						
						<th>下单金额</th>
						<th>指导标费</th>
						<th>回款系数</th>
						<th>回款金额</th>
						<th>应发放</th>
						<th>已预留</th>
						<th>可发放</th>
						<!-- <th>操作</th> -->

					</tr>
				</thead>
				<tbody id="reward_info">
					<%--   <tr>
                <td><input type="checkbox"></td>
                  <td>
                    RM_1
                  </td>
                  <td>
                     北京
                  </td>
                  <td>
                     常春藤美元母基金      
                  </td>
                  <td>201601122211</td>
                  <td>5000000</td>
                  <td>2016.4</td>
                  <td>5000000</td>
                  <td>10%</td>
                  <td>150000</td>
                  <td>350000</td>
                  <td>100%</td>
                  <td>4500000</td>
                  <td><a href="<%=request.getContextPath() %>/jsp/reward/reward_set_1.jsp"><span class="glyphicon glyphicon-cog"></span></a></td>

                </tr>  --%>
					<s:if test="#request.status==1 ">
						<s:iterator value="#request.rewardList" var="rewardLi">
							<tr class="default" id="cust_list" style="cursor: pointer;">
								<td><input value="${rewardLi.reward_id }" name="items"
									type="checkbox"></td>
								<td>${rewardLi.order_no }</td>
								<td>${rewardLi.sales_name }</td>
								<td>${rewardLi.area }</td>
								<td>${rewardLi.prod_name }</td>
								<td>${rewardLi.order_amount }</td>
								<td>${rewardLi.magt_fee }</td>
								<td>${rewardLi.return_coe }%</td>
								<td>${rewardLi.return_money }</td>
								<td>${rewardLi.sum_reward }</td>
								<td>${rewardLi.reserve_reward }</td>
								<td>${rewardLi.has_reward }</td>
								<%-- <td><a href="<%=request.getContextPath() %>/reword_count_info.action?reward_id="
									+ ${rewardLi.reward_id }><span class="glyphicon glyphicon-cog"></span></a></td>  --%>
							</tr>
						</s:iterator>
						<tr>
							<td colspan="13" style="text-align: center;"><a
								class="btn btn-info top10" style="width: 20%"
								href="javascript:void(0);" onclick="saveReward()"
								style="outline: none;"> 计算奖金 </a></td>
						</tr>
					</s:if>
					<s:elseif test="#request.status==2">
						<tr>
							<th colspan="13" style="text-align: center;">${reward_list }</th>
						</tr>
					</s:elseif>
				</tbody>
			</table>
			<div id="countbatch"  style="text-align: center; margin: 15px;">
				<%-- <table>
									<tr align="center"><td><button onclick="subgo('-1')"><<</button></td>
										<s:if test="#request.batch==1 ">
											<s:iterator value="#request.rewardcount" var="rewardcou">
												<td><a
													href="search_reward_list.action?count_batch=${rewardcou.count_batch}" target="_top"
													class="btn btn-danger" style="display: inline-block;">${rewardcou.count_batch }</a>
											</td>
											</s:iterator>
										</s:if>
											<td><button onclick="subgo('1')">>></button></td>
										</tr>
									</table>	 --%>
									</div>
									<a><input id="minbatch" type="hidden" value="0"></a>
										<a><input id="maxbatch" type="hidden" value="3"></a>
										<%--  <a class="left carousel-control"
										href="javascript:void(0)" onclick="subgo('-1')" role="button"
										data-slide="prev" style="background-image: none;">  <span
										class="glyphicon glyphicon-chevron-left" aria-hidden="true"
										style="color: #333;"></span>  <span class="sr-only">Previous</span>
									</a> 
									<a class="right carousel-control"
										href="javascript:void(0)" onclick="subgo('1')" role="button"
										data-slide="next" style="background-image: none; color: #333;">
										<span class="glyphicon glyphicon-chevron-right"
										aria-hidden="true"></span> <span class="sr-only">Next</span>
									</a> --%>
		</div>

	</div>
</body>
</html>

