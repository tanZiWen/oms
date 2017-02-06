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
<script src="/OMS/js/bootstrap-datetimepicker.min.js"></script>
<script src="/OMS/js/order.js"></script>
<script>
	var n = 1;
	var role_id = "${role_id}";
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
	function queryPaging(){
 	 
		var totalNum = $("#totalNum").val();
		var PageNum = $("#PageNum").val();
		
		NavUtil.PageSize = $("#PageSize").val();
		NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
		
		NavUtil.bindPageEvent(loadData);
}

function loadData(){
	
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=document.getElementById("PageNum").value;
	//alert(PageNum);
	queryProduct();
}

</script>	
<script>

function getTdValue(a){ //第几列 0-6
	var tableId = document.getElementById("prod_allot"); 
	var rows = document.getElementById("prod_allot").rows.length; //获得行数(包括thead) 
	var colums = document.getElementById("prod_allot").rows[0].cells.length; //获得列数
	var arr = new Array();
	if(a==0){
		for(var i=1;i<rows;i++)
		{ 
			arr.push(tableId.rows[i].cells[a].innerHTML);
		} 		
	}
	else {
		for(var i=1;i<rows;i++)
		{ 
			arr.push(parseFloat(tableId.rows[i].cells[a].innerHTML));
		} 
	}
	return arr;
} 

	$(window.parent.document).find("#pro_frame").load(function() {
		var main = $(window.parent.document).find("#pro_frame");
		var thisheight = $(document).height() + 30;
		main.height(thisheight);
	});
	$(function() {
		$(".form_datetime").datetimepicker({
  			format : 'yyyy-mm-dd',
  			weekStart : 1,
  			autoclose : true,
  			startView : 2,
  			minView : 2,
  			forceParse : false,
  			language : 'zh-CN'
  		});
  		if(role_id == 3){
			document.getElementById("gp_list").style.display="none";
			document.getElementById("lp_list").style.display="none";
			document.getElementById("khdk_list").style.display="none";
			document.getElementById("fhk_list").style.display="none";
			document.getElementById("hk_list").style.display="none";
			document.getElementById("select_order_list").style.display="none";
		}else if(role_id == 6){
			document.getElementById("fhk_list").style.display="none";
			document.getElementById("hk_list").style.display="none";
			document.getElementById("select_order_list").style.display="none";
		}else if(role_id == 7){
			document.getElementById("fhk_list").style.display="none";
			document.getElementById("hk_list").style.display="none";
			document.getElementById("select_order_list").style.display="none";
		}else if(role_id == 8){
			document.getElementById("gp_list").style.display="none";
			document.getElementById("lp_list").style.display="none";
			document.getElementById("p_allot").style.display="none";
			document.getElementById("khdk_list").style.display="none";
			document.getElementById("select_order_list").style.display="none";
		}else if(role_id == 9){
			document.getElementById("gp_list").style.display="none";
			document.getElementById("lp_list").style.display="none";
			document.getElementById("p_allot").style.display="none";
			document.getElementById("khdk_list").style.display="none";
			document.getElementById("select_order_list").style.display="none";
		}else if(role_id == 4 || role_id == 5) {
			document.getElementById("gp_list").style.display="none";
			document.getElementById("lp_list").style.display="none";
			document.getElementById("p_allot").style.display="none";
			document.getElementById("khdk_list").style.display="none";
			document.getElementById("fhk_list").style.display="none";
			document.getElementById("hk_list").style.display="none";
			
		}
		  
		var flag="1";
		//var aaa = "北京";
		var array0 = getTdValue(0);  //第一列
		var array1 = getTdValue(1);  //第二列
		var array2 = getTdValue(2) ; //第三列
		var array3 = getTdValue(3) ; //第四列
		
		if(flag=="1"){
		
			$('#container').highcharts({
			chart : {
				type : 'column'
			},
			title : {
				text : '额度分配及募集进程统计'
			},
			xAxis : {
				categories : array0
			},
			yAxis : {
				title : {
					text : '金额(百万)'
				}
			},
			series : [ {
				name : '配额',
				data : array1
			}, {
				name : '预计成交',
				data : array2
			}, {
				name : '已成交',
				data : array3
			} ]
		});}else{
			$('#container').highcharts({
				chart : {
					type : 'column'
				},
				title : {
					text : '额度分配及募集进程统计'
				},
				xAxis : {
					
					categories :[array0]
				},
				yAxis : {
					title : {
						text : '金额(百万)'
					}
				},
				series : [ {
					name : '配额',
					data : [array1]
				}, {
					name : '预计成交',
					data : [ array2 ]
				}, {
					name : '已成交',
					data : [ array3 ]
				} ]
			});
		}
	});
</script>
<script type="text/javascript">
	function LP_edit(obj) {
		
		location.href = "LP_edit.action?lp_id=" + obj;
	}
	
	function fabu(obj){
		console.log(obj);
		var shkno = obj.split(",")[0];
		var fb_flag = obj.split(",")[1];
		var prod_id = $("#prod_id").val();
		if(fb_flag=="发布"){
			location.href = "product_fabu.action?prod_id=" + prod_id+"&prod_rs_id="+shkno;
		}else if(fb_flag=="已发布"){
			location.href = "product_yifabu.action?prod_id=" + prod_id+"&prod_rs_id="+shkno;
		}
	}
	
	function newDate(obj) {
		var newTime = new Date(obj); //毫秒转换时间  
		var year = newTime.getFullYear();
		var mon = newTime.getMonth()+1;  //0~11
		var day = newTime.getDate();
		var newDate = year+'-'+mon+'-'+day;
		return newDate
	}
		
	
	function more_ED(deptNo){
		//alert(dept_no);
		var prod_id = $("#prod_id").val();
		
		location.href="/OMS/more_ED.action?prod_id="+prod_id+"&deptNo="+deptNo;
	}
	function edit_return(id){
		var prod_r_id = $("#prod_r_id").val(id);
		
		 $.ajax({
			url : '/OMS/edit_return.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'prod_r_id' : id,
				
			},
			success : function(data) {
				if(data.status==1){
					var list = data.list;
					$("#return_money").val(list.return_money);
					$("#return_date").val(list.to_char);
				}
				
				
			}
			,error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
			}); 
		 $('#modal_plan').modal();
		
	}
	
	function submit_return(){
		var prod_r_id = $("#prod_r_id").val();
		var return_money = $("#return_money").val().trim();
		var return_date = $("#return_date").val().trim();
		 $.ajax({
			url : '/OMS/submit_return.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'prod_r_id' : prod_r_id,
				'return_money':return_money,
				'return_date':return_date
				
			},
			success : function(data) {
				if(data.status==1){
					alert("提交成功")
					window.location.reload();
				}else{
					alert("提交失败")
				}
			}
			,error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
			});
	}
	function approve_return(prod_r_id,finahk_flag){
		 $.ajax({
			url : '/OMS/approve_return.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'prod_r_id' : prod_r_id,
				'finahk_flag':finahk_flag,
			},
			success : function(data) {
				if(data.status==1){
					alert("审核成功");
					
				}else{
					alert("审核失败")
				}
			}
			,error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
			});
		 
		 event.stopPropagation();
		 window.location.reload();
		
	}
	
	
	function item_edit_return(id){
		var prod_rs_id = $("#prod_rs_id").val(id);
		
		 $.ajax({
			url : '/OMS/item_edit_return.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'prod_rs_id' : id,
				
			},
			success : function(data) {
				if(data.status==1){
					var list = data.list;
					$("#item_return_money").val(list.return_money);
					$("#item_return_date").val(list.return_date);
					$("#item_return_coe").val(list.return_coe);
				}
				
				
			}
			,error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
			}); 
		 $('#item_modal_plan').modal();
		
	}
	
	function item_submit_return(){
		var prod_rs_id = $("#prod_rs_id").val();
		var item_return_money = $("#item_return_money").val().trim();
		var item_return_date = $("#item_return_date").val().trim();
		var item_return_coe = $("#item_return_coe").val().trim();
		 $.ajax({
			url : '/OMS/item_submit_return.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'prod_rs_id' : prod_rs_id,
				'item_return_money':item_return_money,
				'item_return_date':item_return_date,
				"item_return_coe":item_return_coe
				
			},
			success : function(data) {
				if(data.status==1){
					alert("提交成功");
					 window.location.reload();
				}else{
					alert("提交失败")
				}
				
				
			}
			,error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
			});
	}
	function item_approve_return(prod_rs_id,prod_flag){
		 $.ajax({
			url : '/OMS/item_approve_return.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'prod_rs_id' : prod_rs_id,
				'prod_flag':prod_flag,
			},
			success : function(data) {
				if(data.status==1){
					alert("审核成功");
				}else{
					alert("审核失败")
				}
			}
			,error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
			});
		 
		 event.stopPropagation();
		 window.location.reload();
		
	}
</script>
</head>
<body>
	 <input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
 	 <input type="hidden" name="PageNum1" id="PageNum" value="1" />
	 <input type="hidden" name="PageSize" id="PageSize" value="10" />
	  
	<!-- GP列表 -->
	<div class="panel panel-default" style="width: 100%;" id="gp_list">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>GP列表
					<input type="hidden" value="<%=request.getParameter("prod_id")%>"
					name="prod_id" id="prod_id" />
					
			</h3>
		</div>
		<div class="panel-body">
			<!-- <input id="prod_id" name="prod_id" type="hidden" value=""> -->
			<table class="table cust_table">
				<thead>
					<tr class="demo_tr">
						<th>GP名称</th>
						<th>营业执照号</th>
						<th>法定代表人</th>
						<th>基金协会备案号</th>
						<th>开户行</th>
						<th>账号</th>
						<th>注册地址</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.gpList" var="gpList">
						<tr class="default">
							<td><s:property value="#gpList.gp_name" /></td>
							<td><s:property value="#gpList.bus_license" /></td>
							<td><s:property value="#gpList.legal_resp" /></td>
							<td><s:property value="#gpList.fund_no" /></td>
							<td><s:property value="#gpList.open_bank" /></td>
							<td><s:property value="#gpList.bank_account" /></td>
							<td><s:property value="#gpList.regit_addr" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- 财务回款弹框 -->
	<div class="modal" id="modal_plan" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px; height: 430px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px; height: 100%;">
				<div class="myright-n">
					<div class="row topx myMinh" style="border: 0; padding: 0;">
						<div class="spjz" style="">
							<ul class="list-group">
								<li class="list-group-item"
									style="color: #a52410; background-color: rgb(245, 245, 245);">
									<h3 class="panel-title" style="color: #a52410;">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"
											style="margin-right: 6px;"></span> 财务回款编辑
									</h3>
								</li>
								
								<li class="list-group-item"><span> 回款金额&nbsp;:</span> <input
									 id="return_money" oninput="qianfenwei('return_money')"
									name="return_money"
									style="width: 75%; border: none; outline: none;" /></li>
								
								
								<li class="list-group-item item_border"><span style="margin-right: 4px;">回款日期&nbsp;:
								</span>
								<input id="return_date" class="form_datetime"  
								type="text" style="width: 75%; border: none; outline: none;"/>
								<input type="hidden" name="prod_r_id" id="prod_r_id" value="" />
						</li>
								
							</ul>
							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									onclick="dele()" class="btn btn-lg btn-default">取消</button>
								
								<button class="btn btn-lg"
									style="background-color: #5bc0de; color: #fff;"
									onclick="submit_return()">提交审批</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 项目回款弹框 -->
	<div class="modal" id="item_modal_plan" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 650px; height: 430px;">
			<div class="col-md-8 myright"
				style="width: 100%; border-radius: 4px; height: 100%;">
				<div class="myright-n">
					<div class="row topx myMinh" style="border: 0; padding: 0;">
						<div class="spjz" style="">
							<ul class="list-group">
								<li class="list-group-item"
									style="color: #a52410; background-color: rgb(245, 245, 245);">
									<h3 class="panel-title" style="color: #a52410;">
										<span class="glyphicon glyphicon-plus" aria-hidden="true"
											style="margin-right: 6px;"></span> 项目回款编辑
									</h3>
								</li>
								
								<li class="list-group-item"><span> 回款金额&nbsp;:</span> <input
									 id="item_return_money" oninput="qianfenwei('item_return_money')"
									name="item_return_money"
									style="width: 75%; border: none; outline: none;" /></li>
								
								
								<li class="list-group-item item_border"><span style="margin-right: 4px;">回款日期&nbsp;:
								</span>
								<input id="item_return_date" class="form_datetime"  
								type="text" style="width: 75%; border: none; outline: none;"/>
								<input type="hidden" name="prod_rs_id" id="prod_rs_id" value="" />
								
						</li>
						<li class="list-group-item"><span> 回款比例&nbsp;:</span> <input
									 id="item_return_coe" 
									name="item_return_coe"
									style="width: 75%; border: none; outline: none;" /></li>
								
							</ul>
							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									onclick="dele()" class="btn btn-lg btn-default">取消</button>
								
								<button class="btn btn-lg"
									style="background-color: #5bc0de; color: #fff;"
									onclick="item_submit_return()">提交审批</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 合作企业 -->
	<div class="panel panel-default" style="width: 100%;" id="lp_list">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>合伙企业
			</h3>
		</div>
		<div class="panel-body">
			<table class="table cust_table">
				<thead>
					<tr class="demo_tr">
						<th>合伙企业编号</th>
						<th>合伙企业名称</th>
						<!-- <th>GP名称</th> -->
						<th>合伙企业总认缴</th>
						<th>合伙企业总实缴</th>
						<th>查看</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.lpList" var="lpList">
						<tr class="default">
							<td><s:property value="#lpList.lp_id" /></td>
							<td><s:property value="#lpList.partner_com_name" /></td>
							<%-- <td><s:property value="#lpList.gp_name" /></td> --%>
							<td><s:property value="#lpList.buy_fee" /></td>
							<td><s:property value="#lpList.pay_amount" /></td>
							<td><input id="lp_id" name="lp_id" type="hidden"
								value="<s:property value="#lpList.lp_id"/>" /> <a
								href='javascript:LP_edit(<s:property value="#lpList.lp_id"/>)'><img src="/OMS/image/00.png"
									width="20" height="20" border="0" /></a></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 额度分配 -->
	<div class="panel panel-default" style="width: 100%;"  id = "p_allot">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>募集进度管理

			</h3>
		</div>
		<div class="panel-body">
			<table class="table cust_table" id="prod_allot">
				<thead>
					<tr class="demo_tr">
						<th>地区</th>
						<th>分配金额</th>
						<th>预期成交金额</th>
						<th>已成交金额</th>
						<th>预期成交占比</th>
						<th>已成交占比</th>
						<th>查看</th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="#request.prodGress" var="prodGress">
						<tr class="default">
							<td><s:property value="#prodGress.dict_name" /></td>
							<td><s:property value="#prodGress.dept_allot" /></td>
							<td><s:property value="#prodGress.cust_money" /></td>
							<td><s:property value="#prodGress.pay_amount" /></td>					
							<td><s:property value="#prodGress.yqzb" /></td>
							<td><s:property value="#prodGress.ycjzb" /></td>
							<%-- <td><input id="lp_id" name="lp_id" type="hidden"
								value="<s:property value="#lpList.lp_id"/>" /> <a
								href='javascript:LP_edit()'><img src="/OMS/image/00.png"
									width="20" height="20" border="0" /></a></td> --%>
							<td><a href="javascript:more_ED('<s:property value="#prodGress.dept_no"/>')" >
							<input type="hidden" value="<s:property value="#prodGress.dept_no" />" name="dept_no" id="dept_no">
							<img src="/OMS/image/00.png" width="20" height="20" border="0" /></a></td>		
						</tr>
					</s:iterator>
					<!-- <tr>
						<td>上海</td>
						<td>200万</td>
						<td>200万（2016.3.6）</td>
						<td>200万</td>
						<td>90%</td>
						<td>80%</td>
					</tr> -->
				</tbody>
			</table>
			<div id="container" style="width: 800px; height: 400px;"></div>
		</div>
		<!-- 图形列表 -->
		<!-- end -->
	</div>
	<!-- 客户打款列表 -->
	<div class="panel panel-default" style="width: 100%;" id="khdk_list">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410; position: relative;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>客户打款列表


				<div data-toggle="buttons-radio" class="btn-group"
					style="position: absolute; right: 20px; top: -7px;">
					<button value="ALL" class="btn btn-sm btn-light-info">全部</button>
					<button value="SI" class="btn btn-sm btn-light-info active">
						逾期打款</button>
					<button value="PE" class="btn btn-sm btn-light-info">正常打款</button>
				</div>
			</h3>
		</div>
		<div class="panel-body">
			<table class="table cust_table">
				<thead>
					<tr class="demo_tr">
						<th>合伙企业名称</th>
						<th>客户名称</th>
						<th>订单编号</th>
						<th>合同编号</th>
						<th>订单总金额</th>
					<!-- 	<th>本次缴款比例</th> -->
						<th>应缴金额</th>
						<th>应缴时间</th>
						<th>实际累计金额</th>
						<th>实际缴款时间</th>
						<th>实际缴款比例</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.custDKInfo" var="custDKInfo">
						<tr class="default">
							<td><s:property value="#custDKInfo.partner_com_name" /></td>
							<td><s:property value="#custDKInfo.cust_name" /></td>
							<td><s:property value="#custDKInfo.order_no" /></td>
							<td><s:property value="#custDKInfo.contract_no" /></td>					
							<td><s:property value="#custDKInfo.order_amount" /></td>
						<%-- 	<td><s:property value="#custDKInfo.jkbl" /></td> --%>
							<td><s:property value="#custDKInfo.true_amount" /></td>
							<td><s:date name="#custDKInfo.stop_time"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#custDKInfo.pay_amount" /></td>
							<td><s:date name="#custDKInfo.pay_time"  format="yyyy-MM-dd"/></td>					
							<td><s:property value="#custDKInfo.sjbl" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
						<%-- <div class="pagin">
							<div class="message" id = "totalNumDiv">
								共<input id = "count_sum" value = "${totalNum}" style="width: 10%;border:none;outline: none;">条记录
							</div>										
						</div>
						<div id="page1" class="page"></div> --%>
		</div>
	</div>

	<!-- 财务回款记录 -->
	<div class="panel panel-default" style="width: 100%;" id="fhk_list">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>财务回款记录
			</h3>
		</div>
		<div class="panel-body">
			<p>总回款比例：20%</p>
			<table class="table cust_table">
				<thead>
					<tr class="demo_tr">
					<!-- 	<th>发行机构</th>
						<th>产品</th> -->
						<th>金额(元)</th>
						<th>日期</th>
						<th>审核</th>
<!-- 						<th>回款比例</th> -->
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.HKlist" var="HKlist">
					<s:if test="#HKlist.finahk_flag==0||#HKlist.finahk_flag==2">
					
								
						<tr style="cursor: pointer;" class="default" onclick="edit_return('<s:property value="#HKlist.prod_r_id" />')">
						
							<td><s:property value="#HKlist.return_money" /></td>
							<td><s:date name="#HKlist.return_date"  format="yyyy-MM-dd"/></td>
							<s:if test="#request.role_id==9">
 							<td><button style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;" 
 							value="" class="btn btn-sm btn-light-info active"
							onclick="approve_return('<s:property value="#HKlist.prod_r_id" />','1')"
							>同意</button>
							<button style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;" 
 							value="" class="btn btn-sm btn-light-info active"
							onclick="approve_return('<s:property value="#HKlist.prod_r_id" />','2')"
							>驳回</button></td>
							</s:if>
							<s:else>
							<td><s:property value="#HKlist.status" /></td>
							</s:else>
						</tr>
					
					</s:if>
					
					<s:else>
						<tr class="default" >
						
							<td><s:property value="#HKlist.return_money" /></td>
							<td><s:date name="#HKlist.return_date"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#HKlist.status" /></td>
						</tr>
					</s:else>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 财务回款记录end -->
	<!-- 回款记录 -->
	<div class="panel panel-default" style="width: 100%;" id="hk_list">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;"></span>回款记录
			</h3>
		</div>
		<div class="panel-body">
			<table class="table cust_table">
				<thead>
					<tr class="demo_tr">
					<!-- 	<th>发行机构</th>
						<th>产品</th> -->
						<th>金额(元)</th>
						<th>日期</th>
						<th>回款比例</th>
						<th>发布状态</th>
						
					</tr>
				</thead>
				<tbody>
						<s:iterator value="#request.sHKlist" var="sHKlist">
					<s:if test="#request.role_id==8&&#sHKlist.prod_flag==1">
						<tr style="cursor: pointer;"   class="default" onclick="item_edit_return('<s:property value="#sHKlist.prod_rs_id" />')">
							
							<td><s:property value="#sHKlist.return_money" /></td>
							<td><s:date name="#sHKlist.return_date"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#sHKlist.return_coe" /></td>
							<td>
							
							<input class="btn btn-default btn-sm" type="button"  
									style="background-color: #6A6AFF; color: white; width: 80px; height: 30px; display: inline-block;"
								value="<s:property value="#sHKlist.prodFlag" />"/>
						</td>
						</tr>
					</s:if>
					<s:elseif test="#request.role_id==9">
						<tr style="cursor: pointer;"  class="default"  >
							
							<td><s:property value="#sHKlist.return_money" /></td>
							<td><s:date name="#sHKlist.return_date"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#sHKlist.return_coe" /></td>
							<td>
							<s:if test="#sHKlist.prod_flag==1">
								<button style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;" 
 							value="" class="btn btn-sm btn-light-info active"
							onclick="item_approve_return('<s:property value="#sHKlist.prod_rs_id" />','4')"
							>同意</button>
							<button style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;" 
 							value="" class="btn btn-sm btn-light-info active"
							onclick="item_approve_return('<s:property value="#sHKlist.prod_rs_id" />','3')"
							>驳回</button>
							</s:if>
							<s:if test="#sHKlist.prod_flag==2">
							<input class="btn btn-default btn-sm" type="button" onclick="javascript:fabu('<s:property value="#sHKlist.shkno" />,<s:property value="#sHKlist.prodFlag" />')" 
									style="background-color: #6A6AFF; color: white; width: 80px; height: 30px; display: inline-block;"
								value="<s:property value="#sHKlist.prodFlag" />"/>
							</s:if>
						</td>
						</tr>
					</s:elseif>
					<s:else>
					<tr class="default" >
							<td><s:property value="#sHKlist.return_money" /></td>
							<td><s:date name="#sHKlist.return_date"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#sHKlist.return_coe" /></td>
							<td>
							<input class="btn btn-default btn-sm" type="button"  
									style="background-color: #6A6AFF; color: white; width: 80px; height: 30px; display: inline-block;"
								value="<s:property value="#sHKlist.prodFlag" />"/>
						</td>
						</tr>
					</s:else>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 运营勾选订单 -->
	<div class="panel panel-default" style="width: 100%;" id="select_order_list">
		<div class="panel-heading">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-th" aria-hidden="true"
					style="margin-right: 6px;">勾选订单</span>
			</h3>
		</div>
		<div class="panel-body">
			<table class="table cust_table">
				<thead>
					<tr class="demo_tr">
					<!-- 	<th>发行机构</th>
						<th>产品</th> -->
						<th>金额(元)</th>
						<th>日期</th>
						<th>回款比例</th>
						<th>发布状态</th>
						
					</tr>
				</thead>
				<tbody>
						<s:iterator value="#request.sHKlist" var="sHKlist">
					<s:if test="#request.role_id==8&&#sHKlist.prod_flag==1">
						<tr style="cursor: pointer;"   class="default" onclick="item_edit_return('<s:property value="#sHKlist.prod_rs_id" />')">
							
							<td><s:property value="#sHKlist.return_money" /></td>
							<td><s:date name="#sHKlist.return_date"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#sHKlist.return_coe" /></td>
							<td>
							
							<input class="btn btn-default btn-sm" type="button"  
									style="background-color: #6A6AFF; color: white; width: 80px; height: 30px; display: inline-block;"
								value="<s:property value="#sHKlist.prodFlag" />"/>
						</td>
						</tr>
					</s:if>
					<s:elseif test="#request.role_id==9">
						<tr style="cursor: pointer;"  class="default"  >
							
							<td><s:property value="#sHKlist.return_money" /></td>
							<td><s:date name="#sHKlist.return_date"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#sHKlist.return_coe" /></td>
							<td>
							<s:if test="#sHKlist.prod_flag==1">
								<button style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;" 
 							value="" class="btn btn-sm btn-light-info active"
							onclick="item_approve_return('<s:property value="#sHKlist.prod_rs_id" />','2')"
							>同意</button>
							<button style="width: 40%; float: left; text-align: center; padding-left: 0; padding-right: 0;" 
 							value="" class="btn btn-sm btn-light-info active"
							onclick="item_approve_return('<s:property value="#sHKlist.prod_rs_id" />','3')"
							>驳回</button>
							</s:if>
							<s:if test="#sHKlist.prod_flag==2">
							<input class="btn btn-default btn-sm" type="button" onclick="javascript:fabu('<s:property value="#sHKlist.shkno" />,<s:property value="#sHKlist.prodFlag" />')" 
									style="background-color: #6A6AFF; color: white; width: 80px; height: 30px; display: inline-block;"
								value="<s:property value="#sHKlist.prodFlag" />"/>
							</s:if>
						</td>
						</tr>
					</s:elseif>
					<s:else>
					<tr class="default" >
							<td><s:property value="#sHKlist.return_money" /></td>
							<td><s:date name="#sHKlist.return_date"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#sHKlist.return_coe" /></td>
							<td>
							<input class="btn btn-default btn-sm" type="button"  
									style="background-color: #6A6AFF; color: white; width: 80px; height: 30px; display: inline-block;" onclick="javascript:fabu('<s:property value="#sHKlist.shkno" />,<s:property value="#sHKlist.prodFlag" />')" 
								value="<s:property value="#sHKlist.prodFlag" />"/>
						</td>
						</tr>
					</s:else>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>

