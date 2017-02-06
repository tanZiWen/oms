<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../css/bootstrap.min.css">
<script src="../../js/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script type="text/javascript">
var flag=0;
function add_member(flag){
	if (flag==2){
		$("#add_member").show();
	}
	else if(flag==1){
		$("#add_member").hide();
		$("#member").hide();
	}
	else if (flag==3){
		$("#member").show();
	}
	else if(flag==4){
		$("#family_list").show();
	}
	else if (flag==5){
		$("#member").show();
	}
}
function add_sales(flag){
	if(flag==1){
		$(".tr_sales").hide();
	}
	else if(flag==2){
		$(".tr_sales").show();
	}
}
function family_detail(){
	window.location.href="bootstrap_test.jsp"; 
}
function del_tr(obj){
	var tr=obj.parentNode.parentNode;
	var tbody=tr.parentNode;
	tbody.removeChild(tr);
	
	var tb = document.getElementById('tb_1');
    tb.deleteRow(1);
    	
}

function confirm_dd(){
	
}

function checkboxOnclick(checkbox){

if ( checkbox.checked == true){
	$("#show_DDdetail").show();
}else{
	$("#show_DDdetail").hide();
}

}

function rule_manage(){
	window.location.href="http://localhost:8080/OMS/jsp/reward/reward_rules.jsp";
}
</script>
<style type="text/css">
	.panel-title{color:#a52410;}
	.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{border-top: 1px solid white;}
</style>
<title>family_detail</title>
</head>
<body>

<!-- 下拉菜单 -->
<%@ include file="../header.jsp"%>

<!-- 新建批次弹窗 -->
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 800px;">
      <div class="modal-content">
         <div class="modal-header">
         <div class="row">
         	<h4 style="text-align:center;" class="col-md-11">新建批次</h4>
            <button type="button" class="close col-md-1" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
         </div>
         </div>
  <div class="modal-body">
	<div class="panel panel-default">
				   
		<div class="panel-body">
				
			<div class="input-group row">
				<div  class="control-label col-md-2" style="margin-top:5px;">
				<label>
					批次号：1
				</label>
				</div>	
				<div  class="control-label col-md-2"  style="margin-top:5px;">
				<label>
					批次说明
				</label>
				</div>
				<div class="control-label col-md-4" style="margin-left: -30px;">
					<input type="text" class="holo" placeholder="请输入批次说明">
	             </div>
	             <div  class="control-label col-md-2"  style="margin-top:5px;">
				<label>
					总金额
				</label>
				</div>
				<div class="control-label col-md-3" style="margin-left: -40px;">
	               <input type="text" class="holo" placeholder="请输入总金额" style="width:100%">
	             </div>
	            </div>
	            <br>
	            
	            <div class="input-group" style="margin-bottom:10px;">
				<div  class="control-label col-md-3" style="margin-left:-20px;">
				<label style="" >
					可按订单号查询
				</label>
				</div>
				<div class="control-label col-md-9" style="margin-left:-70px;">
	               <input type="text" class="form-control" style="width:80%">
	               <span class="input-group-btn">
	                  <button class="btn btn-default" type="button" onclick="list_show(1)">
	                     	查找
	                  </button>
	               </span>
	             </div>
	            </div>
	            
				  <!-- <button type="button" class="btn btn-primary btn-md" onclick="show_detail()">
				     	 添加订单
				  </button>  -->
				  <table class="table"  >
							<thead style="background-color:#666666;color:white">
								<tr>
									<th>
										订单号
									</th>
									<th>
										订单名称
									</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										20160416
									</td>
									<td>
										常青藤基金<input type="checkbox" onclick="checkboxOnclick(this)"/>
									</td>
								</tr>
								<!-- <tr>
									<td>
										20160416
									</td>
									<td>
										常青藤基金<input type="checkbox" onclick="checkboxOnclick(this)"/>
									</td>
								</tr> -->
							</tbody>
							</table>
								
				  <br><br>
				  <div style="display:none;" id="show_DDdetail">
						<table class="table" style="text-align: center;">
							<thead style="background-color:#666666;color:white">
								<tr>
									<th>
										订单号
									</th>
									<th>
										产品名称
									</th>
									<th>
										销售人员
									</th>
									<th>
										发放金额
									</th>
									<th>
										是否发放
									</th>
									<th>
										本次发放批次
									</th>
									<th>
										百分比
									</th>
									<th>
										是否符合发放标准
									</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										20160416
									</td>
									<td>
										常青藤基金
									</td>
									<td>
										Ann
									</td>
									<td width="12%">
										<input type="text" class="holo" style="width: 70%;" placeholder="金额">	
									</td>
									<td>
										<select>
											<option>是</option>
											<option>否</option>
										</select>
									</td>
									<td>
										1	
									</td>
									<td>
										0.1
									</td>
									<td>
										<button type="button" class="btn btn-primary btn-md "  data-toggle="modal"  data-target="#modalrule" aria-hidden="true" onclick="rule_manage()">
										      选择制度条款
										</button>
									</td>
								</tr>
								<tr>
									<td>
										20160416
									</td>
									<td>
										常青藤基金
									</td>
									<td>
										Amy
									</td>
									<td>
										<input type="text" class="holo" style="width: 70%;" placeholder="金额">
									</td>
									<td>
										<select>
											<option>是</option>
											<option>否</option>
										</select>
									</td>
									<td>
										1	
									</td>
									<td>
										0.04
									</td>
									<td>
										<button type="button" class="btn btn-primary btn-md" onclick="rule_manage()">
										      选择制度条款
										</button>
									</td>
								</tr>
								<tr>
									<td>
										20160416
									</td>
									<td>
										常青藤基金
									</td>
									<td>
										Alice
									</td>
									<td>
										<input type="text" class="holo" style="width: 70%;" placeholder="金额">
									</td>
									<td>
										<select>
											<option>是</option>
											<option>否</option>
										</select>
									</td>
									<td>
										1	
									</td>
									<td>
										0.05
									</td>
									<td>
										<button type="button" class="btn btn-primary btn-md" onclick="rule_manage()">选择制度条款</button>
									</td>
								</tr>
							</tbody>
						</table>
						
					</div>	
				   </div>
				</div>
		 </div>

				<div class="modal-footer">
					<div class="row text-center">
						<!-- <button style="margin-right: 40px;" data-dismiss="modal"
							class="btn btn-lg btn-default">取消</button>
 -->
						<button id="customerCreateBtn"  
							class="btn btn-lg btn-success" data-dismiss="modal" onclick="confirm_dd()">确定</button>
					</div>
				</div>
			</div>
</div>
</div>


<div class="modal" id="myModal8" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
         <div class="row">
         	<h4 style="text-align:center;" class="col-md-11">奖金制度</h4>
            <button type="button" class="close col-md-1" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
         </div>
         </div>
         <div class="modal-body" >
				<div class="row">
					<span class="col-md-4" style="text-align: right;">内容</span>&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" class="default" style="width: 40%;height:100px;vertical-align: top">
				</div>
				<br />
				
		 </div>

				<div class="modal-footer">
					<div class="row text-center">
						<button style="margin-right: 40px;" data-dismiss="modal"
							class="btn btn-lg btn-default">取消</button>

						<button id="customerCreateBtn" data-dismiss="modal"
							class="btn btn-lg btn-success" onclick="add_production(4)">确定</button>
					</div>
				</div>
			</div>
</div>
</div>

<!--   添加成员 -->


<!-- 下拉菜单 -->
<!-- <div>
				<nav class="navbar navbar-default" role="navigation">
				   <div>
				      <ul class="nav navbar-nav">
				         <li class="dropdown">
				            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				              	 客户
				               <b class="caret"></b>
				            </a>
				            <ul class="dropdown-menu">
				               <li><a href="#">个人</a></li>
				               <li><a href="#">家族</a></li>
				               <li><a href="#">机构</a></li>
				            </ul>
				         </li>
				         
				         <li class="dropdown">
				            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				              	产品
				               <b class="caret"></b>
				            </a>
				            <ul class="dropdown-menu">
				               <li><a href="#">产品1</a></li>
				               <li><a href="#">产品2</a></li>
				               <li><a href="#">产品3</a></li>
				            </ul>
				         </li>
				         
				         <li class="dropdown">
				            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				              	订单
				               <b class="caret"></b>
				            </a>
				            <ul class="dropdown-menu">
				               <li><a href="#">订单1</a></li>
				               <li><a href="#">订单2</a></li>
				               <li><a href="#">订单3</a></li>
				            </ul>
				         </li>
				      </ul>
				   </div>
				</nav>
</div> -->
<div class="container" style="width:100%;padding-left:0">
   <div class="row">
      <div class="col-md-2" style="margin-top:100px;">
       <div class="navbar-fixed-left">
			<div class="btn-group-vertical">
			  <button type="button" class="btn btn-default" data-toggle="modal"  data-target="#myModal" style="height:70px;" onclick="add_member(5)">新建批次</button>
			</div>
       </div>
      </div>

      <div class="col-md-10"  style="min-height:20px;padding-top:10px;margin-top:90px;">
         <div class="container-fluid">
				
	<!-- 表格信息 -->
	
				<div class="panel panel-default" style="width:876px;">
				   <div class="panel-heading">
				      <h3 class="panel-title">基本信息</h3>
				   </div>
				   
				   <div class="panel-body">
				   		
					<table class="table" id="tb_1">
					<thead>
					<tr>
					<th>发放批次</th>
					<th>总金额</th>
					<th>订单号</th>
					<th></th>
					</tr>
					</thead>
					<tbody>
					<tr>
					<td class="default_1">1</td>
					<td>10</td>
					<td>130528199102021232</td>
					</tr>
					<tr class="default_2">
					<td>1</td>
					<td>6</td>
					<td>58032199102021232</td>
					</tr>
					<tr class="default_3">
					<td>1</td>
					<td>8</td>
					<td>130528199102020934</td>
					</tr>
					<tr class="default_4">
					<td>1</td>
					<td>13</td>
					<td>902032199102021232</td>
					</tr>
					</tbody>
					</table>
						<ul class="pagination pagination-centered" >
						  <li><a href="#">&laquo;</a></li>
						  <li><a href="#">1</a></li>
						  <li><a href="#">2</a></li>
						  <li><a href="#">3</a></li>
						  <li><a href="#">4</a></li>
						  <li><a href="#">5</a></li>
						  <li><a href="#">&raquo;</a></li>
						</ul>
				   </div>
				</div>
			</div>
      </div>
   </div>
</div>
</body>
</html>





