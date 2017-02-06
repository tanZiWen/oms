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
		statusVal=0;
		var host = '${host}';
		var port = '${port}';
		var username = '${username}';
		var ext = '${ext}';
		console.log('host:'+host+', port:'+port);
		var optional = {};
		optional.callCenterAddress = host+ ":" +port;
		optional.callCenterAgent = username;
		optional.callCenterExtension = ext;
		optional.callCenterGroup = "";
		CallCenterController().setup(optional);
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
		var cust_name = $('#cust_name').val();
		var subject = $('#subject').val();
		var prod_name = $('#prod_name').val();
		if(cust_name == "请输入客户/机构名称") {
			cust_name = "";
		}
		if(prod_name == "请输入产品名称") {
			prod_name = "";
		}
		data.cust_name = cust_name;
		data.prod_name = prod_name;
		$.ajax({
			url:"query_cust_call.action",
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
					var list = data.callCustList;
					for(var i=0;i<list.length;i++){
						var tbody= '<tr class="default">'
							+ '<td>'+list[i].order_no+'</td>'
							+ '<td>'+list[i].cust_name+'</td>'
							+ '<td>'+list[i].real_name+'</td>'
							+ '<td>'+list[i].prod_name+'</td>'
							+ '<td><a href="javascript:call_phone('+list[i].order_no+')">拨号</a>'
							+ '<a style="margin-left:5px;" href="javascript:show_call_remark('+list[i].order_no+')">备注</a>'
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
	var orderNo = "";
	function call_phone(order_no) {
		var data = {order_no: order_no};
		$.ajax({
			url: '/OMS/call_phone.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				if(data.desc == 1) {
					if(data.dial.Value == 0) {
						toast({type: "success", body: data.info});
						$('#addCallRemarkModal').modal("show");
						var d = data.verifyInfo;
						orderNo = d.order_no;
						$('#addCallRemarkModal #cust_name').html(d.cust_name);
						$('#addCallRemarkModal #id_no').html(d.id_no);
						$('#addCallRemarkModal #order_amount').html(format(d.order_amount)+"元");
						$('#addCallRemarkModal #prod_name').html(d.prod_name);
						$('#addCallRemarkModal #id_address').html(d.cust_address);
						$('#addCallRemarkModal #org_info').html("法人："+d.org_legal+"   实际投资人："+d.investor_name);
						$('#addCallRemarkModal #partnership_name').html(d.partner_com_name);
					}else {
						toast({type: "warning", body: data.dial.Reason});
					}
				}else {
					toast({type: "warning", body: data.info});
				}
			}
		});
	}
	
	function remark_call() {
		var is_self = $('#addCallRemarkModal #is_self:checked').val();
		if(!is_self) {
			alert("请勾选是否为本人");
			return false;
		}
		var is_id_no = $('#addCallRemarkModal #is_id_no:checked').val();
		var id_no = $('#addCallRemarkModal #new_id_no').val();
		var is_id_address = $('#addCallRemarkModal #is_id_address:checked').val();
		var id_address = $('#addCallRemarkModal #new_id_address').val();
		var is_partnership = $('#addCallRemarkModal #is_partnership:checked').val();
		var partnership = $('#addCallRemarkModal #new_partnership').val();
		var is_order_amount = $('#addCallRemarkModal #is_order_amount:checked').val();
		var order_amount = $('#addCallRemarkModal #new_order_amount').val();
		var is_mail_address = $('#addCallRemarkModal #is_mail_address:checked').val();
		var mail_address = $('#addCallRemarkModal #new_mail_address').val();
		var work_address = $('#addCallRemarkModal #work_address').val();
		var cust_email = $('#addCallRemarkModal #cust_email').val();
		var remark = $('#addCallRemarkModal #remark').val();
		var is_email =$('#addCallRemarkModal #is_email:checked').val();
		if(is_self == "1") {
			if(!is_id_no) {
				alert("请勾选身份证是否匹配");
				return false;
			}
			if(is_id_no == "2") {
				console.log(id_no)
				if(!id_no) {
					alert("请输入不匹配的身份证信息")
					return false;
				}
			}
			if(!is_id_address) {
				alert("请勾选身份证地址是否匹配")
				return false;
			}
			if(is_id_address == "2") {
				if(!id_address) {
					alert("请输入不匹配的身份证地址信息")
					return false;
				}
			}
			if(!is_partnership) {
				alert("请勾选合伙企业名称是否匹配")
				return false;
			}
			if(is_partnership == "2") {
				if(!partnership) {
					alert("请输入不匹配的合伙企业信息")
					return false;
				}
			}
			if(!is_order_amount) {
				alert("请勾选下单金额是否匹配")
				return false;
			}
			if(is_order_amount == "2") {
				if(!order_amount) {
					alert("请输入不匹配的下单金额")
					return false;
				}
			}
			if(!is_mail_address) {
				alert("请勾选邮寄地址与身份证地址是否匹配")
				return false;
			}
			if(is_mail_address == "2") {
				if(!mail_address) {
					alert("请输入不匹配的邮寄地址")
					return false;
				}
			}
		}
		if(!is_email) {
			is_email = '1';
		}
		var data = {order_no: orderNo, is_self: is_self, is_id_no: is_id_no, id_no: id_no, is_id_address: is_id_address, 
				id_address: id_address, is_partnership: is_partnership, partnership: partnership, is_order_amount: is_order_amount,
				is_mail_address: is_mail_address, mail_address: mail_address, work_address: work_address, remark: remark, 
				cust_email: cust_email, is_email: is_email
		};
		$.ajax({
			url: '/OMS/remark_call.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				if(data.desc == "1") {
					toast({type: "success", body: data.info});
					$('#addCallRemarkModal').modal('hide');
					$('#addCallRemarkModal #is_self').attr("checked",false);
					$('#addCallRemarkModal #is_id_no').attr("checked",false);
					$('#addCallRemarkModal #new_id_no').val('');
					$('#addCallRemarkModal #is_id_address').attr("checked",false);
					$('#addCallRemarkModal #new_id_address').val('');
					$('#addCallRemarkModal #is_partnership').attr("checked",false);
					$('#addCallRemarkModal #new_partnership').val('');
					$('#addCallRemarkModal #is_order_amount').attr("checked",false);
					$('#addCallRemarkModal #new_order_amount').val('');
					$('#addCallRemarkModal #is_mail_address').attr("checked",false);
					$('#addCallRemarkModal #new_mail_address').val('');
					$('#addCallRemarkModal #work_address').val('');
					$('#addCallRemarkModal #cust_email').val('');
					$('#addCallRemarkModal #remark').val('');
					$('#addCallRemarkModal #is_email').attr("checked",false);
				}else {
					toast({type: "warning", body: data.info});
				}
			}
		});
	}
	
	function show_call_remark(order_no) {
		orderNo = order_no;
		var data = {order_no: order_no};
		$.ajax({
			url: '/OMS/show_call_phone_detail.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				var d = data.verifyInfo;
				$('#addCallRemarkModal #cust_name').html(d.cust_name);
				$('#addCallRemarkModal #id_no').html(d.id_no);
				$('#addCallRemarkModal #order_amount').html(format(d.order_amount)+"元");
				$('#addCallRemarkModal #prod_name').html(d.prod_name);
				$('#addCallRemarkModal #id_address').html(d.cust_address);
				$('#addCallRemarkModal #org_info').html("法人："+d.org_legal+"   实际投资人："+d.investor_name);
				$('#addCallRemarkModal #partnership_name').html(d.partner_com_name);
				$('#addCallRemarkModal').modal("show");
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
                    <li class="active">客户外拨</li>
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
						<h4>客户外拨</h4>
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
			                <input id="prod_name" type="text" value="请输入产品名称"
			                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''" 
			                style="width: 90%;border:none;outline: none;">
			            </li>
			            <li class="list-group-item">
			                <i class="fa-home"></i>&nbsp;
			                <input id="cust_name" type="text" value="请输入客户/机构名称"
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
											style="margin-right: 6px;"></span>外拨管理列表
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
												    	<a href="javascript:call_phone('${lis.cust_id},${lis.order_no} }')">拨号</a>
												    	<a href="javascript:show_call_remark('${lis.order_no}')">备注</a>
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
	  <div class="modal" id="addCallRemarkModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 960px;">
				<div class="modal-content">
					<div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		                <h4 class="modal-title" id="myModalLabel">备注</h4>
	                </div>
					<div class="modal-body">
					 	<ul class="list-group">
					 		<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
									<div class="col-md-6">
										客户名称/机构名称为:&nbsp;<label id="cust_name"></label>
									</div>
									<div class="col-md-6">
										机构信息:&nbsp;<label id="org_info"></label>
									</div>
									<div class="col-md-6">
										是否为客户/机构投资人本人:&nbsp;
										<input type="radio" id="is_self" name="is_self" value="1">是&nbsp;
						 				<input type="radio" id="is_self" name="is_self" value="2">否
									</div>
							 	</div>
							</li>
					 		<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
									<div class="col-md-6">
										身份证号码为:&nbsp;<label id="id_no"></label>
									</div>
									<div class="col-md-6">
										是否匹配:&nbsp;
										<input type="radio" id="is_id_no" name="is_id_no" value="1">是&nbsp;
						 				<input type="radio" id="is_id_no" name="is_id_no" value="2">否
									</div>
						 			<div class="col-md-6 m-t-10">
							 			<input class="form-control" type="text" id="new_id_no" name="new_id_no" placeholder="请输入不匹配的身份证信息">
						 			</div>
							 	</div>
							</li>
							<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
									<div class="col-md-6">
										身份证地址为:&nbsp;<label id="id_address"></label>
									</div>
									<div class="col-md-6">
										是否匹配:&nbsp;
										<input type="radio" id="is_id_address" name="is_id_address" value="1">是&nbsp;
						 				<input type="radio" id="is_id_address" name="is_id_address" value="2">否
									</div>
						 			<div class="col-md-6 m-t-10">
							 			<input class="form-control" type="text" id="new_id_address" name="new_id_address" placeholder="请输入不匹配的身份证地址">
						 			</div>
							 	</div>
							</li>
							<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
									<div class="col-md-6">
										下单合伙企业名称为:&nbsp;<label id="partnership_name"></label>
									</div>
									<div class="col-md-6">
										下单产品名称为:&nbsp;<label id="prod_name"></label>
									</div>
									<div class="col-md-6">
										合伙企业是否匹配:&nbsp;
										<input type="radio" id="is_partnership" name="is_partnership" value="1">是&nbsp;
						 				<input type="radio" id="is_partnership" name="is_partnership" value="2">否
									</div>
						 			<div class="col-md-6 m-t-10">
							 			<input class="form-control" type="text" id="new_partnership" name="new_partnership" placeholder="请输入不匹配的合伙企业名称">
						 			</div>
							 	</div>
							</li>
							<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
									<div class="col-md-6">
										下单金额为:&nbsp;<label id="order_amount"></label>
									</div>
									<div class="col-md-6">
										是否匹配:&nbsp;
										<input type="radio" id="is_order_amount" name="is_order_amount" value="1">是&nbsp;
						 				<input type="radio" id="is_order_amount" name="is_order_amount" value="2">否
									</div>
						 			<div class="col-md-6 m-t-10">
							 			<input class="form-control" type="text" id="new_order_amount" name="new_order_amount" placeholder="请输入不匹配的下单金额">
						 			</div>
							 	</div>
							</li>
							<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
									<div class="col-md-6">
										邮寄地址是否与身份证地址相同:&nbsp;
										<input type="radio" id="is_mail_address" name="is_mail_address" value="1">是&nbsp;
						 				<input type="radio" id="is_mail_address" name="is_mail_address" value="2">否
									</div>
						 			<div class="col-md-6 m-t-10">
							 			<input class="form-control" type="text" id="new_mail_address" name="new_mail_address" placeholder="请输入不匹配邮寄地址">
						 			</div>
							 	</div>
							</li>
							<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
							 		<div class="text-center col-md-8">
							 			<input class="form-control" type="text" id="work_address" name="work_address" placeholder="请输入客户工作单位">
							 		</div>
							 	</div>
							</li>
							<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
									<div class="col-md-6">
										是否存在邮箱:&nbsp;
										<input type="radio" id="is_email" name="is_email" value="1">是&nbsp;
										<input type="radio" id="is_email" name="is_email" value="2">否&nbsp;
									</div>
							 		<div class="text-center col-md-8">
							 			<input class="form-control" type="text" id="cust_email" name="cust_email" placeholder="请输入客户邮箱">
							 		</div>
							 	</div>
							</li>
							<li class="list-group-item">
								<div style="margin-top: 15px" class="row">
							 		<div class="text-center col-md-8">
							 			<textarea class="form-control" rows="5" cols="80" id="remark" name="remark" placeholder="备注"></textarea>
							 		</div>
							 	</div>
							</li>
						</ul>
					</div>
					<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">取消</button>
									<button class="btn btn-lg btn-success"
										onclick="remark_call()">确认</button>
								</div>
							</div>
				</div>
			</div>
		</div>
</body>
</html>