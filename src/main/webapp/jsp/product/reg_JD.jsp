<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_demo_2</title>
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
<script>
	var n = 1;
	var dist_value ="${distInfo.dict_value}";
	var dist_name ="${distInfo.dict_name}";
</script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
	$(function() {
		
		$("#dist_name").val(dist_name);
		$("#dist_value").val(dist_value);
		
		$("td").attr("contentEditable", "true");
		var rows = document.getElementById("playlistTable").rows.length; //获得行数(包括thead) 
		var colums = document.getElementById("playlistTable").rows[0].cells.length; //获得列数

		if (rows > 1) {// 不要第一行的 thead
			for (var i = 1; i < rows; i++) { //每行 
				var obj = new Object();
				obj.O_NBR = O_NBR;
				var tdValue = $("#playlistTable tr:eq(" + i + ") td:eq(0)")
						.html();
				if (tdValue == "123") {
					obj.P_PAY_TYP = "3";
					obj.P_POS = $("#playlistTable tr:eq(" + i + ") td:eq(5)")
							.html();
					obj.P_NBR = $("#playlistTable tr:eq(" + i + ") td:eq(4)")
							.html();
					obj.P_HL_ZH = $("#playlistTable tr:eq(" + i + ") td:eq(7)")
							.html();
				}
			}
		}
	});
	function add_tr() {
		var t_info = '<tr class="default" id="cust_list production_list">'
				+ '<td></td>' + '<td></td>' + '<td></td>' + '<td></td>'
				+ '<td></td>' + '</tr>';

		$("#prod_info").append(t_info);

		$("td").attr("contentEditable", "true");
	}

	function cancel_regJD(){
		parent.location.reload();
	}
	
	function show_indiv1() {
		var rows = document.getElementById("playlistTable").rows.length; //获得行数(包括thead) 
		var colums = document.getElementById("playlistTable").rows[0].cells.length; //获得列数
		var result = "";
		for (var i = 1; i < rows; i++) {
			for (var j = 0; j < colums; j++) {
				result += $("#playlistTable tr:eq(" + i + ") td:eq(" + j + ")")
						.html();
				result += ',';
			}
			result += "|";
		}
		//return result;
		// alert(result);
	}

	var keysArr = new Array("cust_name", "sale_name", "except_money",
			"deal_success", "remarks");
	function show_indiv() { //tableid是你要转化的表的表名，是一个字符串，如"example" 
		var rows = document.getElementById("playlistTable").rows.length; //获得行数(包括thead) 
		var colums = document.getElementById("playlistTable").rows[0].cells.length; //获得列数 
		var json = "[";
		var tdValue;
		for (var i = 1; i < rows; i++) { //每行 
			json += "{";
			for (var j = 0; j < colums; j++) {
				tdName = keysArr[j]; //Json数据的键 
				json += "\""; //加上一个双引号 
				json += tdName;
				json += "\"";
				json += ":";
				tdValue = document.getElementById("playlistTable").rows[i].cells[j].innerHTML;//Json数据的值 

				json += "\"";
				json += tdValue;
				json += "\"";
				json += ",";
			}
			json = json.substring(0, json.length - 1);
			json += "}";
			json += ",";
		}
		json = json.substring(0, json.length - 1);
		json += "]";
		//return json; 
		var prod_id = $("#prod_id",parent.document).val();
		var dist_value = $("#dist_value").val().trim();
		$.ajax({
			url : 'reg_JDAdd.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'json' : json,
				'prod_id':prod_id,
				'dept_no':dist_value
			},
			success : function(data) {
				if(data.status==1){
	        		alert("登记成功");
	        		/* document.getElementById('content').innerHTML='登记成功';
	        		$('#myal').modal('show');  */
	        		//parent.location.reload();
	        		parent.history.go(0) ;
	        	}else if(data.status==2){
	        		alert(data.msg);
	        		/* document.getElementById('content').innerHTML=data.msg;
	        		$('#myal').modal('show'); */
	        	}
			},error:function(result){
					//alert('系统异常,请稍后再试!');
				document.getElementById('content').innerHTML='系统异常,请稍后再试!';
        		$('#myal').modal('show');
			  } 
		});
	}
</script>
</head>
<body>

	<ul class="list-group">

		<li class="list-group-item"
			style="color: #a52410; background-color: rgb(245, 245, 245);">
			<h3 class="panel-title" style="color: #a52410;">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"
					style="margin-right: 6px;"></span> 额度进度登记
			</h3>
		</li>
		<!--  <li class="list-group-item">
                        
                        <span>产品名称&nbsp;:</span>
                        <input type="text" value="常春藤美元母基金" style="width: 90%;border:none;outline: none;">
                       </li> -->
		<li class="list-group-item"><span> 地区&nbsp;:</span> 
			<input id = "dist_value" name = "dist_value" type="hidden"
			   style="width: 75%; border: none; outline: none;">
			<input id = "dist_name" name = "dist_name" 
			type="text" value="" style="width: 75%; border: none; outline: none;">
		</li>
		<div class="panel panel-default"
			style="width: 100%; border-top-left-radius: 0; border-top-right-radius: 0;">
			<div class="panel-body">
				<table class="table cust_table" id="playlistTable">
					<thead>
						<tr class="demo_tr">
							<th>客户名称</th>
							<th>销售名</th>
							<th>预估金额</th>
							<th>成交概率%</th>
							<th>备注</th>
							<!--  <th>添加</th>
                   
            -->
						</tr>
					</thead>
					<tbody id="prod_info">

						<tr class="default" id="cust_list production_list">
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td style="border: 0px;" onclick="add_tr()"><a href="">添加</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- <ul>
				<li class="list-group-item"
					style="border: 0; border-top: 1px solid #ddd;">
					<h5>汇总</h5>
				</li>
			</ul> -->
		</div>
	</ul>
	<!--主体-->
	<div class="row text-center">
		<button style="margin-right: 40px;" data-dismiss="modal"
			class="btn btn-lg btn-default" onclick="cancel_regJD()">取消</button>
		<button class="btn btn-lg" data-dismiss="modal" onclick="show_indiv()"
			style="background-color: #5bc0de; color: #fff;">确定</button>
	</div>
	<!--end 右-->


</body>
</html>