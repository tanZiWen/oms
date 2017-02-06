<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>奖金发放
</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="../../css/bootstrap.min.css">
<script src="../../js/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/bootstrap-button.js"></script>
<link rel="stylesheet" href="../../css/style.css">

<style>
	.th_text{width:100px;}
	body{overflow-x:hidden;}
	.txt_holo{width:20%!important;}
	.txt_holo_1{width:50%!important;}
	.right_labl{text-align:right;}
	.row_top{margin:10px;}
		
	.table > tbody > tr:hover > td,
	.table > tbody > tr:hover > th {
	background-color: #f5f5f5;
	/* cursor:pointer */
	}
	.table > thead{
	background-color:#666666;
	color:white
	}
	.panel-body{
	padding-left:0;
	padding-right:0;
	}
	.panel-title{color:#a52410;}
	.hide_td_1{display:none;}
	.hide_td_2{display:none;}
</style>

<script type="text/javascript">
function show_td(flag){
	if(flag==1){
		$(".hide_td_1").show();
	}
	else if(flag==2){
		$(".hide_td_2").show();
	}
}
</script>


</head>
<body>
<!-- 下拉菜单 -->
<%@ include file="../header.jsp" %>


<!-- 订单变更奖金设置 -->
<div class="modal" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
      
      	<div class="modal-header">
	         <div class="row">
	         	<h4 style="text-align:left;" class="col-md-11">原订单参数</h4>
	         	<button type="button" class="close col-md-1" data-dismiss="modal" aria-hidden="true">
                  &times;
            	</button>
	         </div>
         </div>
         
         
         <div class="row row_top">
         	<div class="text-muted col-md-3 right_labl">奖金计算</div>
         	<span class="text-muted col-md-3">10%</span>
         	<div class="text-muted col-md-3 right_labl">回款比例</div>
         	<span class="text-muted col-md-3">100%</span>
         </div>
         <div class="row row_top">
         	<div class="text-muted col-md-3  right_labl">应发放</div>
         	<span class="text-muted col-md-3">150000</span>
         	<div class="text-muted col-md-3 right_labl">已预留</div>
         	<span class="text-muted col-md-3">350000</span>
         </div>
         <!-- <div class="row row_top">
         	<div class="text-muted col-md-3  right_labl">本次发放调整</div>
         	<input type="text" class="holo col-md-3 txt_holo">
         	<div class="text-muted col-md-3 right_labl">本次预留调整</div>
         	<input type="text" class="holo col-md-3 txt_holo">
         </div> -->
         <div class="row row_top">
         	<div class="text-muted col-md-6  right_labl"  style="text-align:right;">最终发放</div>
         	<span class="text-muted col-md-3">450000</span>
         </div>
      
         <div class="modal-header">
         <div class="row">
         	<h4 class="col-md-11">更改订单参数</h4>
         </div>
         </div>
         
         <div class="row row_top">
         	<div class="text-muted col-md-3 right_labl">奖金计算</div>
         	<input type="text" class="holo col-md-3 txt_holo">
         	<div class="text-muted col-md-3 right_labl">回款比例</div>
         	<input type="text" class="holo col-md-3 txt_holo">
         </div>
         <div class="row row_top">
         	<div class="text-muted col-md-3  right_labl">应发放</div>
         	<input type="text" class="holo col-md-3 txt_holo">
         	<div class="text-muted col-md-3 right_labl">已预留</div>
         	<input type="text" class="holo col-md-3 txt_holo">
         </div>
         <div class="row row_top">
         	<div class="text-muted col-md-3  right_labl">本次发放调整</div>
         	<input type="text" class="holo col-md-3 txt_holo">
         	<div class="text-muted col-md-3 right_labl">本次预留调整</div>
         	<input type="text" class="holo col-md-3 txt_holo">
         </div>
         <div class="row row_top">
         	<div class="text-muted col-md-6  right_labl"  style="text-align:right;">最终发放</div>
         	<input type="text" class="holo col-md-6 txt_holo">
         </div>
         
         <div class="modal-footer">
					<div class="row text-center">
						<button style="margin-right: 40px;" data-dismiss="modal"
							class="btn btn-lg btn-default">取消</button>

						<button id="customerCreateBtn" data-dismiss="modal"
							class="btn btn-lg btn-success" onclick="fresh_tr()">确定</button>
					</div>
		 </div>

	  </div>
	</div>
</div>



<!-- 团队奖金详情 -->
<div class="modal" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:">
   <div class="modal-dialog modal-lg">
      <div class="modal-content">
         <div class="modal-header">
         <div class="row">
         	<h4 class="col-md-11">团队奖金</h4>
            <button type="button" class="close col-md-1" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
         </div>
         </div>
		 
		 <table class="table">
		 	<thead>
		 	<tr>
		 		<th>销售</th>
		 		<th>订单号</th>
		 		<th>回款金额</th>
		 		<th class="th_text">达标率</th>
		 		<th class="th_text">提成点</th>
		 		<th class="th_text">可发放奖金</th>
		 	</tr>
		 	</thead>
		 	<tr>
		 		<td>sales_2</td>
		 		<td>210129212</td>
		 		<td>320000</td>
		 		<td><input type="text" class="holo"></td>
		 		<td><input type="text" class="holo"></td>
		 		<td class="hide_td_1">20000</td>
		 	</tr>
		 	<tr>
		 		<td>sales_1</td>
		 		<td>210129212</td>
		 		<td>180000</td>
		 		<td><input type="text" class="holo"></td>
		 		<td><input type="text" class="holo"></td>
		 		<td class="hide_td_1">30000</td>
		 	</tr>
		 </table>
		 <div class="row text-center" style="padding-top:15px;margin-bottom:15px;">
						<button class="btn btn-lg btn-success" onclick="show_td(1)">确定</button>
		 </div>
		 
	  </div>
	</div>
</div>


<!-- 预留奖金 -->
<div class="modal" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:">
   <div class="modal-dialog modal-lg">
      <div class="modal-content">
         <div class="modal-header">
         <div class="row">
         	<h4 class="col-md-11">预留奖金</h4>
            <button type="button" class="close col-md-1" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
         </div>
         </div>
		 
		 <table class="table">
		 	<thead>
		 	<tr>
		 		<th>订单号</th>
		 		<th>产品</th>
		 		<th>已预留</th>
		 		<th>已回款</th>
		 		<th class="th_text">扣款</th>
		 		<th>本次发放</th>
		 	</tr>
		 	</thead>
		 	<tr>
		 		<td>210129212</td>
		 		<td>万达院线</td>
		 		<td>100000</td>
		 		<td>200000</td>
		 		<td><input type="text" class="holo"></td>
		 		<td class="hide_td_2">100000</td>
		 	</tr>
		 	<tr>
		 		<td>210129222</td>
		 		<td>万达院线</td>
		 		<td>100000</td>
		 		<td>200000</td>
		 		<td><input type="text" class="holo"></td>
		 		<td class="hide_td_2">100000</td>
		 	</tr>
		 </table>
		 <div class="row text-center" style="padding-top:15px;margin-bottom:15px;">
						<button class="btn btn-lg btn-success" onclick="show_td(2)">确定</button>
		 </div>
	  </div>
	</div>
</div>

<!-- 确定弹窗 -->
<div class="modal" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:">
   <div class="modal-dialog modal-lg">
      <div class="modal-content">
         <div class="modal-header">
         <div class="row">
         	<h4 class="col-md-11">可发放奖金</h4>
            <button type="button" class="close col-md-1" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
         </div>
         </div>
		 
		 
		 		<div id="" class="panel panel-default">
					<div class="panel-body">
						<table class="table">
							<thead>
								<tr>
									<th>RM/顾问</th>
									<th>奖金类型</th>
									<th>奖金</th>
									<th width="150px">调整</th>
									<th>应发放奖金</th>
								</tr>
							</thead>
							<tbody>
								<tr>
								<td>RM_1</td>
								<td>个人奖金</td>
								<td>20000</td>
								<td><input type="text" class="holo"></td>
								<td>20000</td>
								</tr>
								<tr>
								<td>RM_1</td>
								<td>团队奖金</td>
								<td>20000</td>
								<td><input type="text" class="holo"></td>
								<td>20000</td>
								</tr>
								<tr>
								<td>RM_1</td>
								<td>预留奖金</td>
								<td>20000</td>
								<td><input type="text" class="holo"></td>
								<td>20000</td>
								</tr>
								<tr>
								<td>RM_1</td>
								<td>奖金总计</td>
								<td>20000</td>
								<td><input type="text" class="holo"></td>
								<td>60000</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
		 

		 <div class="row text-center" style="padding-top:15px;margin-bottom:15px;">
						<button data-dismiss="modal"
							class="btn btn-lg btn-success">确定</button>
		 </div>
	  </div>
	</div>
</div>



<!-- 		<ul class="nav nav-list bs-docs-sidenav affix" style="margin-top:7%;background-color:white;">
          <li><a href="#reward_top"><i class="icon-chevron-right"></i> 顶部</a></li>
          <li><a href="#reward_indiv"><i class="icon-chevron-right"></i> 个人奖金</a></li>
          <li><a href="#reward_group"><i class="icon-chevron-right"></i> 团队奖金</a></li>
          <li><a href="#reward_pre"><i class="icon-chevron-right"></i> 预留奖金</a></li>
          <li><a href="#reward_bottom"><i class="icon-chevron-right"></i> 底部</a></li>
        </ul>
 -->


<div id="reward_top" class="container" style="width:100%;padding-left:0">

	<div class="row">
		<div class="col-md-12"  style="min-height:20px;padding-top:10px;margin-top:90px;">
			<div class="container-fluid" style="width:90%">
				<div class="panel panel-default">
					<div class="panel-body">
						<span class="text-muted col-md-1 right_labl">部门</span>
						<div class="col-md-2">
			         		<select style="height:30px;margin-top:10px;">
								<option>
								北京地区
								</option>
								<option>
								上海地区
								</option>
								<option>
								深圳地区
								</option>
							</select>
						</div>
						<span class="text-muted col-md-1 right_labl">产品</span>
						<div class="col-md-2">
	      					<select style="height:30px;margin-top:10px;width:100%;">
								<option>
										常春藤美元母基金
								</option>
								<option>
										万达院线
								</option>
							</select>
			         	</div>
			         	<span class="text-muted col-md-1 right_labl">RM</span>
			         	<input type="text" class="holo col-md-2 txt_holo">
			         	<button class="btn btn-sm col-md-offset-1">查找</button>
					</div>
				</div>
				
				
				<div class="panel-group" id="accordion">
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" 
				          href="#collapseOne">
							奖金发放--个人
				        </a>
				      </h4>
				    </div>
				    <div id="collapseOne" class="panel-collapse collapse in">
				      <div class="panel-body">
						<table class="table">
							<thead>
								<tr>
									<th>订单号</th>
									<th>销售</th>
									<th>地区</th>
									<th>产品名称</th>
									<th>进账日期</th>
									<th>下单金额</th>
									<th>知道标费</th>
									<th>回款系数</th>
									<th>回款金额</th>
									<th>应发放</th>
									<th>已预留</th>
									<th>可发放</th>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<tr id="tr_red">
								<td>RM_1</td>
								<td>北京地区</td>
								<td>常春藤美元母基金</td>
								<td>201601122211</td>
								<td>5000000</td>
								<td>2016.4</td>
								<td>5000000</td>
								<td>10%</td>
								<td>150000</td>
								<td>350000</td>
								<td>100%</td>
								<td>4500000</td>
								<td><input type="checkbox" /></td>
								<td><button class="btn btn-sm" data-toggle="modal" data-target="#myModal1">设置</button></td>
								</tr>
								
								
								<tr id="tr_black">
								<td>RM_1</td>
								<td>北京地区</td>
								<td>常春藤美元母基金</td>
								<td>201601122212</td>
								<td>5000000</td>
								<td>2016.4</td>
								<td>5000000</td>
								<td>10%</td>
								<td>150000</td>
								<td>350000</td>
								<td>100%</td>
								<td>4500000</td>
								<td><input type="checkbox" /></td>
								<td><button class="btn btn-sm" data-toggle="modal" data-target="#myModal1">设置</button></td>
								</tr>
								
							</tbody>
						</table>
				      </div>
				    </div>
				  </div>
				  
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" 
				          href="#collapseTwo">
						奖金发放--团队
				        </a>
				      </h4>
				    </div>
				    <div id="collapseTwo" class="panel-collapse collapse">
				      <div class="panel-body">
				    	<table class="table">
							<thead>
								<tr>
									<th>RM/顾问</th>
									<th>部门</th>
									<th>产品</th>
									<th>订单金额</th>
									<th>总标费</th>
									<th>总奖金</th>
									<th>最终发放</th>
									<th style="width:10px;"></th>
									<th style="width:50px;"></th>
								</tr>
							</thead>
							<tbody>
								<tr>
								<td>RM_1</td>
								<td>北京地区</td>
								<td>常春藤美元母基金</td>
								<td>5000000</td>
								<td>4000000</td>
								<td>50000</td>
								<td>4500000</td>
								<td><input type="checkbox" /></td>
								<td><button class="btn btn-sm" data-toggle="modal" data-target="#myModal2">设置</button></td>
								</tr>
							</tbody>
						</table>
				      </div>
				    </div>
				  </div>

				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" 
				          href="#collapseThree">
							奖金发放--预留
				        </a>
				      </h4>
				    </div>
				    <div id="collapseThree" class="panel-collapse collapse">
				      <div class="panel-body">
						<table class="table">
							<thead>
								<tr>
									<th>RM/顾问</th>
									<th>部门</th>
									<th>已预留总金额</th>
									<th>已扣除总金额</th>
									<th>剩余金额</th>
									<th>最终发放</th>
									<th style="width:10px;"></th>
									<th style="width:50px;"></th>
								</tr>
							</thead>
							<tbody>
								<tr>
								<td>RM_1</td>
								<td>北京地区</td>
								<td>200000</td>
								<td>100000</td>
								<td>100000</td>
								<td>200000</td>
								<td><input type="checkbox" /></td>
								<td><button class="btn btn-sm" data-toggle="modal" data-target="#myModal3">设置</button></td>
								</tr>
							</tbody>
						</table>
				      </div>
				    </div>
				  </div>
				</div>
				
				
			</div>
		</div>
		
	</div>

	<div id="reward_bottom" class="row text-center" style="padding-top:15px;margin-bottom:15px;">
			<button id="customerCreateBtn" data-dismiss="modal" class="btn btn-lg btn-success" data-toggle="modal" data-target="#myModal4">确定</button>
	</div>
	
</div>

</body>
</html>