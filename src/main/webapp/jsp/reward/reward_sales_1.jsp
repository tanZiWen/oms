<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../css/bootstrap.min.css">
<script src="../../js/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/bootstrap-button.js"></script>
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
function family_detail(){
	window.location.href="http://localhost:8080/abcprmap/bootstrap_test.jsp";
}

function del_tr(obj){
	var tr=obj.parentNode.parentNode;
	var tbody=tr.parentNode;
	tbody.removeChild(tr);
	
	var tb = document.getElementById('tb_1');
    tb.deleteRow(1);
}

function DoCheck()
{	
	var ch=document.getElementsByName("choose");
	 $('input[type=checkbox]').prop('checked', $(allChecked).prop('checked'));
  	/*
  	//方法二
  	if(document.getElementsByName("allChecked")[0].checked==true)
	{
		for(var i=0;i<ch.length;i++)
		{
			ch[i].checked=true;
		}
	}else
	  {
		for(var i=0;i<ch.length;i++)
		{
			ch[i].checked=false;
		}
	} */
} 

$(function(){
	$(".fontColor").css("font-family","Helvetica Neue, Helvetica, Arial, sans-serif");
	$(".fontColor").css("color","#999999");
	$(".fontColor").css("font-size","85%");
	$('#tab_sort').bootstrapTable({sortable:true});
}); 

</script>

<title>我的奖金</title>
</head>
<body>

<!-- 下拉菜单 -->
<%@ include file="../header.jsp"%>

<!--   添加成员 -->
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
   <div class="modal-dialog">
      <div class="modal-content">
      	<div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel" style="text-align:center">
				我的奖金（历史）
            </h4>
         </div>
         <div class="modal-body" >
			<table class="table">
				<thead style="background-color:#666666;color:white">
					<tr>
						<th  style="vertical-align: center;">
							季度批次号
						</th>
						<th>
							产品名称
						</th>
						<th>
							订单号
						</th>
						<th>
							客户
						</th>
						<th>
							订单金额
						</th>
						<th>
							奖金
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							2016040101
						</td>
						<td>
							常春藤美元母基金
						</td>
						<td>
							2313123123
						</td>
						<td>
							张望
						</td>
						<td>
							2000000
						</td>
						<td>
							20000
						</td>
					</tr>
					<tr>
						<td>
							2016040101
						</td>
						<td>
							常春藤美元母基金
						</td>
						<td>
							2313123124
						</td>
						<td>
							张望
						</td>
						<td>
							2000000
						</td>
						<td>
							20000
						</td>
					</tr>
					<tr>
						<td>
							2016040101
						</td>
						<td>
							常春藤美元母基金
						</td>
						<td>
							2313123125
						</td>
						<td>
							张望
						</td>
						<td>
							2000000
						</td>
						<td>
							20000
						</td>
					</tr>
					<tr>
						<td>
							2016040101
						</td>
						<td>
							常春藤美元母基金
						</td>
						<td>
							2313123125
						</td>
						<td>
							张望
						</td>
						<td>
							2000000
						</td>
						<td>
							20000
						</td>
					</tr>
				</tbody>
			</table>
		 </div>
				
				<div class="modal-footer">
					<div class="row text-center">
						<button style="margin-right: 40px;" data-dismiss="modal"
							class="btn btn-lg btn-default">取消</button>
						<button id="customerCreateBtn" data-dismiss="modal"
							class="btn btn-lg btn-success" onclick="add_member(3)">确定</button>
					</div>
				</div>
			</div>
</div>
</div>


<div class="container" style="width:100%;padding-left:0">
   <div class="row">
      <div class="col-md-2" style="margin-top:100px;">
       <div class="navbar-fixed-left">
			<div class="btn-group-vertical">
			  <!-- <button type="button" class="btn btn-default" data-toggle="modal"  data-target="#myModal" style="height:70px;" onclick="add_member(1)">新建家族</button> -->
			</div>
       </div>
      </div>
      <div class="col-md-10"  style="min-height:20px;margin-top:90px;padding-top:10px;">
         <div class="container-fluid">
		<div class="panel panel-default" style="width:876px;">
			<div class="panel-body">
				   	<div class="row">
				   		<label class="col-md-3" style="text-align:right;margin-top:15px">客户姓名/手机</label>
				   		<input type="text" class="holo col-md-9" style="width: 40%;vertical-align: top">
				   	</div>
					<div class="row">
						<label class="col-md-3" style="text-align:right;margin-top:15px">产品</label>
						<select style="height:30px;width:350px;margin-top:10px">
							<option>
								常春藤美元母基金
							</option>
							<option>
								万达院线
							</option>
						</select>
					</div>
					<div class="row">
						<label class="col-md-3" style="text-align:right;margin-top:15px">产品类型</label>
						<div class="col-md-9" style="margin-top:10px;padding-left:0;">
						<div data-toggle="buttons-radio" class="btn-group" >
							<button value="ALL" class="btn btn-sm btn-light-info active"> 直销产品</button>
							<button value="SI" class="btn btn-sm btn-light-info"> 代销产品</button>
						</div>
						</div>
					</div>
					
					<div class="row">
						<label class="col-md-3" style="text-align:right;margin-top:15px">奖金发放批次</label>
						<div class="col-md-9" style="margin-top:10px;padding-left:0;">
							<input type="text" class="holo col-md-11" style="width: 40%;vertical-align: top">
							<div class="col-md-1">
						   		 <button class="btn btn-default" type="button" onclick="">
				                     	查找
				                 </button>
							</div>
						</div>
					</div>
				</div>
			</div>

				<br>
				<span>本次编号：20160401</span>
				<div class="panel panel-default" style="width:876px;">
				  
				   <div class="panel-body">
						<table class="table" id="tab_sort">
							<thead style="background-color:#666666;color:white">
								<tr>
									<th  style="vertical-align: center;">
										批次
									</th>
									<th>
										产品名称
									</th>
									<th  data-sortable="true">
										订单号
									</th>
									<th>
										客户
									</th>
									<th>
										订单金额
									</th>
									<th>
										可发放奖金
									</th>
									<th>
										<input type="checkbox" name="allChecked" id="allChecked"  onclick="DoCheck()"/>全选
									</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										2016040101
									</td>
									<td>
										常春藤美元母基金
									</td>
									<td>
										2313123123
									</td>
									<td>
										张望
									</td>
									<td>
										2000000
									</td>
									<td>
										20000
									</td>
									<td>
										<input type="checkbox" name="choose"/>
									</td>
								</tr>
								<tr>
									<td>
										2016040101
									</td>
									<td>
										常春藤美元母基金
									</td>
									<td>
										2313123124
									</td>
									<td>
										张望
									</td>
									<td>
										2000000
									</td>
									<td>
										20000
									</td>
									<td>
										<input type="checkbox" name="choose"/>
									</td>
								</tr>
								<tr>
									<td>
										2016040101
									</td>
									<td>
										常春藤美元母基金
									</td>
									<td>
										2313123126
									</td>
									<td>
										张望
									</td>
									<td>
										2000000
									</td>
									<td>
										20000
									</td>
									<td>
										<input type="checkbox" name="choose"/>
									</td>
								</tr>
								<tr>
									<td>
										2016010104
									</td>
									<td>
										常春藤美元母基金
									</td>
									<td>
										2313123125
									</td>
									<td>
										张望
									</td>
									<td>
										2000000
									</td>
									<td>
										20000
									</td>
									<td>
										<input type="checkbox" name="choose"/>
									</td>
								</tr>
							</tbody>
						</table>
						
						
						<div style="text-align:center;margin:15px;">
							<button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015010101</button>
							<button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015040101</button>
							<button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015070101</button>
							<button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015100101</button>
						</div>
						
				   </div>
				</div>
	
				<button type="button" class="btn btn-primary" data-toggle="button"
				style="margin-left: 35%;"> 佣金发放
				</button>
				
			</div>

      </div>

   </div>

</div>


</body>
</html>





