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
	function showCallCustModal() {
		$('#addCallCustModal').modal('show');
	}
	function GetRequest() {  
	    var url = location.search; //获取url中"?"符后的字串   
	    var theRequest = new Object();  
	    if (url.indexOf("?") != -1) {  
	        var str = url.substr(1);  
	        strs = str.split("&");  
	        for (var i = 0; i < strs.length; i++) {  
	            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);  
	        }  
	    }  
	    return theRequest;  
	}  
	var custInfo = [];
	var orderNos = [];
	var distrib_id;
	$(function() {
		var param = GetRequest(); 
		if(param.id) {
			var data = {id: param.id};
			distrib_id = param.id;
			$('#callCustListbody').empty();
			var tbody = "";
			$.ajax({
				url: '/OMS/call_edit_info.action',
				type: 'get',
				dataType: 'JSON',
				data: data,
				success: function(data) {
					var callCustList = data.callCustList;
					for(var i=0; i< callCustList.length;i++) {
						custInfo.push({order_no: callCustList[i].order_no, cust_name: callCustList[i].cust_name, rm_name: callCustList[i].real_name, prod_name: callCustList[i].prod_name});
						orderNos.push(callCustList[i].order_no);
						tbody += '<tr class="default">'
							+ '<td>'+callCustList[i].order_no+'</td>'
							+ '<td>'+callCustList[i].cust_name+'</td>'
							+ '<td>'+callCustList[i].real_name+'</td>'
							+ '<td>'+callCustList[i].prod_name+'</td>'
							+ '<td><a style="cursor:pointer;" href="javascript:void(0)" onclick="delCallCust('+callCustList[i].order_no+')">删除</a>'
							+ '</td></tr>';
					}
					$('#callCustListbody').append(tbody);
				}
			});
		}
	})
	
	function searchCust() {
		var cust_type = $('#cust_type:checked').val();
		var cust_name = $('#cust_name').val();
		var prod_no = $('#prod_no').val();
		var order_no = $('#order_no').val();
		if(!cust_type && cust_name) {
			alert("请先选择客户类型!");
			return false;
		}
		if(!cust_name && !prod_no && !cust_type && !order_no) {
			alert("请至少选择一个查询条件!");
			return false;
		}
		var data = {cust_type: cust_type, cust_name: cust_name, prod_no: prod_no, order_no: order_no};
		$.ajax({
			url: '/OMS/call_search_cust.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				if(data.desc == "1") {
					var list = data.callCustList;
					var tbody = "";
					$('#addCallCustModal #callCustbody').empty();
					for(var i = 0; i < list.length; i++) {
						tbody += '<tr class="default">'
							+ '<td>'+list[i].order_no+'</td>'
							+ '<td>'+list[i].cust_name+'</td>'
							+ '<td>'+list[i].real_name+'</td>'
							+ '<td>'+list[i].prod_name+'</td>'
							+ '<td><input name="select_val" id="select_val" type="checkbox" value="'+list[i].order_no+','+list[i].cust_name+','+list[i].real_name+','+list[i].prod_name+'"/>'
							+ '</td></tr>';
					}
					$('#addCallCustModal #callCustbody').append(tbody);
				}
			}
		});
	}
	function selectCallCust() {
		var boxes = $('#callCustbody #select_val');
		for(var k = 0; k< boxes.length; k++) {
			if(boxes[k].checked) {
				if(boxes[k].value) {
					var val = boxes[k].value;
					var vals = val.split(',');
					custInfo.push({order_no: vals[0], cust_name: vals[1], rm_name: vals[2], prod_name: vals[3]});
					orderNos.push(vals[0]);
				}
			}
		}
		resetCallTabel(custInfo)
		$('#addCallCustModal').modal('hide');
	}

	var selectState = false;
	function selectAll() {
		var boxes = $('#callCustbody #select_val');
		for(k in boxes) {
			boxes[k].checked = !selectState;
		}
		selectState = !selectState;
	}
	Array.prototype.removeObj = function(val) {
		 var index;
		 for(var k = 0; k< custInfo.length; k++) {
			 if(val == custInfo[k].order_no) {
				 index = k;
				 break;
			 }
		 }
		 if (index > -1) {
		 	this.splice(index, 1);
		 }
	 };
	 Array.prototype.remove = function(val) {
		 var index = this.indexOf(val);
		 if (index > -1) {
		 	this.splice(index, 1);
		 }
	 };
	function delCallCust(val) {
		custInfo.removeObj(val);
		orderNos.remove(val);
		resetCallTabel(custInfo)
	}
	
	function resetCallTabel(custInfo) {
		$('#callCustListbody').empty();
		var tbody = "";
		for(var k = 0; k< custInfo.length; k++) {
			tbody += '<tr class="default">'
				+ '<td>'+custInfo[k].order_no+'</td>'
				+ '<td>'+custInfo[k].cust_name+'</td>'
				+ '<td>'+custInfo[k].rm_name+'</td>'
				+ '<td>'+custInfo[k].prod_name+'</td>'
				+ '<td><a style="cursor:pointer;" href="javascript:void(0)" onclick="delCallCust('+custInfo[k].order_no+')">删除</a>'
				+ '</td></tr>';
		}
		$('#callCustListbody').append(tbody);
	}
	function distrib_cust() {
		var cust_service = $('#cust_service').val();
		var user_id ="";
		var user_name = "";
		if(cust_service) {
			var vs = cust_service.split(',');
			user_id = vs[0];
			user_name = vs[1];
		}
		if(!user_id) {
			alert("客户人员不能为空！");
			return false;
		}
		
		var data = {user_id: user_id, user_name: user_name, orderNos: orderNos, distrib_id: distrib_id};
		$.ajax({
			url: '/OMS/call_distrib_cust.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				alert(data.info)
				if(data.desc == "1") {
					window.location.href = "/OMS/call_manage.action";
				}
			}
		});
	}
</script>
<body data-spy="scroll" data-target=".navbar-example">
	<%@ include file="../header.jsp"%>
	  
      <div class="container m0 bod top70" id="zt">
        <div class="row">
	        <div class="col-md-6 t0b0 ">
	           <ol class="breadcrumb t0b0">
	            <li><a href="<%=request.getContextPath() %>/jsp/index.jsp"><i class='fa  fa-home'></i>&nbsp;首页</a></li>
	            <li><a href="/OMS/call_manage.action">外拨管理</a></li>
	            <li class="active">客户分配</li>
	            <li><input name="cust_id" id="cust_id" type="hidden" value=""/></li>
	          </ol>
	        </div>
        </div>
         <div class="row top10 mym">
          	<div class="panel panel-default" style="width:80%;margin:0 auto;align:center;border:0;" id='myModalNext'>
            	<div class="modal-body">
	        	   <!-- <form id="formid" method = 'post' action="addCustinfo.action">    -->
                    <ul class="list-group">
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                             客户分配
                       </li> 
                       <li class="list-group-item">
	                       <span style="color: red;margin-right: 4px;float: left;">*</span>
	                       <span>客服人员</span>
   			               <select id="cust_service" name="cust_service"  required="required">
	                       		<s:iterator value="#request.custServiceList" var="custService">
	                       			<s:if test="#request.service_id == #custService.user_id">
							        	<option selected="selected" value='<s:property value="#custService.user_id"/>,<s:property value="#custService.real_name"/>'><s:property value="#custService.real_name"/></option>  
						  			</s:if>
						  			<s:else>
						        		<option value='<s:property value="#custService.user_id"/>,<s:property value="#custService.real_name"/>'><s:property value="#custService.real_name"/></option>  
						  			</s:else>
						  		</s:iterator>
	                       </select>
                       </li>
                       <li class="list-group-item">
	                       <span style="color: red;margin-right: 4px;float: left;">*</span>
	                       <span>分配客户</span>
   			               <button type="button" class="btn btn-warning btn-xs" onclick="showCallCustModal()"><i class="glyphicon glyphicon-plus"></i></button>
                       </li>
                       <li class="list-group-item">
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
								<tbody id="callCustListbody">
									<s:iterator value="#request.callCustList" var="lis">
											<tr class="default">
													<td>${lis.cust_name}</td>
													<td>${lis.real_name}</td>
											    	<td><a style="cursor:pointer;" href="javascript:void(0)" onclick="delCallCust('${lis.cust_id}')">删除</a>
												 </tr>
									</s:iterator>
								</tbody>
							</table>
						</li>
                       <li class="list-group-item">
                           <div class="row text-center" style="margin:25px 0;">
                        	<a href="call_manage.action"><button  data-dismiss="modal"
                           class="btn btn-lg btn-default">取消</button></a>
                            <input type="button" value="确定" data-dismiss="modal"
                            class="btn btn-lg btn-success" onclick="distrib_cust()" >
                      		</div>
      					 </li>
                     </ul>
               	</div>
          </div>
     </div>
     
     <div class="modal" id="addCallCustModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 960px;">
				<div class="modal-content">
					<div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		                <h4 class="modal-title" id="myModalLabel">新增分配</h4>
	                </div>
					<div class="modal-body">
						 <li class="list-group-item">
						 	<div style="margin-top: 15px" class="row col-md-offset-2">
						 		<div class="col-md-8 text-center">
						 			<span>产品名称:</span>
						 			<select id="prod_no" name="prod_no"  required="required">
						 			<option value=""></option>
	                       			<s:iterator  value="#request.prodInfoList" var="prod">
							        	<option value='<s:property value="#prod.prod_no"/>'><s:property value="#prod.prod_name"/></option>  
						  			</s:iterator>
	                       			</select>
						 		</div>
						 	</div>
						 	<div style="margin-top: 15px" class="row col-md-offset-2">
						 		<div class="col-md-8 text-center">
									<input name='order_no' id='order_no' type='text' placeholder="请输入订单号"
										class="form-control" />
						 		</div>
						 	</div>
						 	<div style="margin-top: 15px" class="row col-md-offset-2">
						 		<div class="col-md-6 text-center">
						 			<span>客户类型:</span>
						 			<s:iterator  value="#request.custtypeList" var="custtype">
							        	<input type="radio" name="cust_type" id="cust_type" value='<s:property value="#custtype.dict_value"/>'><s:property value="#custtype.dict_name" />
						  			</s:iterator>
						 		</div>
						 	</div>
							<div style="margin-top: 15px" class="row col-md-offset-3">
								<div class="col-md-6 text-center">
									<input name='cust_name' id='cust_name' type='text' placeholder="请输入客户/机构名称"
										class="form-control" />
								</div>
							</div>
							<div style="margin-top: 15px" class="row col-md-offset-2">
								<div class="col-md-6 text-center">
									<button class="btn btn-md btn-success" onclick="searchCust()">查找</button>
								</div>
							</div>
						 </li>
						 <li class="list-group-item">
						<table class="table cust_table">
							<thead>
								<tr class="demo_tr">
									<th>订单编号</th>
									<th>客户姓名</th>
									<th>RM姓名</th>
									<th>产品名称</th>
									<th>勾选<input type="checkbox" onClick="selectAll()" /></th>
								</tr>
							</thead>
							<tbody id="callCustbody">
							</tbody>
						</table>
						</li>
					</div>
					<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">取消</button>
									<button class="btn btn-lg btn-success"
										onclick="selectCallCust()">确认</button>
								</div>
							</div>
				</div>
			</div>
		</div>
</body>
</html>