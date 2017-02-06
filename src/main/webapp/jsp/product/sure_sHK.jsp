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
<link href="<%=request.getContextPath() %>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker.min.js"></script>
<script src="/OMS/js/order.js"></script>
<script>
	var n = 1;
	/* var return_money = '${finfo.return_money}';
	var return_coe = '${finfo.return_coe}';
	var hkno = '${finfo.hkno}'; */
	
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">

//	var flag=1;  //1为财务   2为运营
/* 	$(function(){
	  	if(flag==1){
			$("#sale_rtn").hide();
		}else if(flag==2){
			$("#f_rtn").hide();
		}  
	}); */
	
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
	})
	function cancel_sHK(){
		parent.location.reload();
	}

	
	function sure_sHK(){
		//alert(123);return;
		var pro_id = $("#prod_id").val();
		//if(flag==1){
	/* 	var return_money = $("#s_return_money").val();
		var return_coe = $("#s_return_coe").val();
		var hkno = $("#hkno").html(); */
		var s_return_money = $("#s_return_money").val();
		var s_return_coe = $("#s_return_coe").val();
		var s_return_date = $("#s_return_date").val();
		var shkno = $("#shkno").html();
		if(s_return_money == null || s_return_money == ""){
			document.getElementById('content').innerHTML="请输入回款金额";
			$('#myal').modal('show');
			//alert("请输入回款金额");
			return;
		}
		
		if(s_return_coe == null || s_return_coe == ""){
			document.getElementById('content').innerHTML="请输入回款比例";
			$('#myal').modal('show');
			//alert("请输入回款比例");
			return;
		}
		
		if(s_return_date == null || s_return_date == ""){
			document.getElementById('content').innerHTML="请输入回款日期";
			$('#myal').modal('show');
			//alert("请输入回款比例");
			return;
		}
		
/* 		
		$.ajax({
			url : 'sure_HK_add.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'prod_id' : pro_id,
				'return_money' : return_money,
				'return_coe' : return_coe,
				'hkno':hkno
			},
			success : function(data) {
				if(data.status==1){
	        		//alert(data.msg);
	        		document.getElementById('content').innerHTML=data.msg;
					$('#myal').modal('show');
	        		parent.location.reload();
	        	}else if(data.status==2){
	        		//alert(data.msg);
	        		document.getElementById('content').innerHTML=data.msg;
					$('#myal').modal('show');
	        	} 

				parent.location.reload();
			},error:function(result){
				document.getElementById('content').innerHTML='系统异常,请稍后再试!';
				$('#myal').modal('show');
				//alert('系统异常,请稍后再试!');
			}
		});
		
		}else if(flag==2){ */
			
			$.ajax({
				url : 'sure_sHK_add.action', //后台处理程序     
				type : 'post', //数据发送方式     
				dataType : 'json', //接受数据格式        
				data : {
					'prod_id' : pro_id,
					's_return_money' : s_return_money,
					's_return_coe' : s_return_coe,
					's_return_date': s_return_date,
					'shkno':shkno
				},
				success : function(data) {
					if(data.status==1){
		        		//alert(data.msg);
		        		document.getElementById('content').innerHTML=data.msg;
	        			$('#myal').modal('show');
		        		parent.location.reload();
		        	}else if(data.status==2){
		        		document.getElementById('content').innerHTML=data.msg;
		        		$('#myal').modal('show');
		        		//alert(data.msg);
		        	}

					location.reload();
				},error:function(result){
					//alert('系统异常,请稍后再试!');
					document.getElementById('content').innerHTML='系统异常,请稍后再试!';
	        		$('#myal').modal('show');
				}
			});
		//}
		
	}

</script>
</head>
<body>

<div id="myal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      
        <div id="content" style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
        </div>
          <div class="row text-center" style="margin-bottom: 15px;">
            <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" ">关闭</button>
          </div>
  </div>
</div>
</div>

	<%-- 	<div class="panel panel-default" id="f_rtn" style="width: 100%;">
			<div class="panel panel-default" style="width: 100%;">
				<ul id='list_ul' class="list-group">
					<li class="list-group-item"
						style="background-color: rgb(245, 245, 245); cursor: pointer;">
						<h3 class="panel-title" style="color: #a52410;">
							<span class="glyphicon glyphicon-th" aria-hidden="true"
								style="margin-right: 6px;"></span>财务回款 <input type="hidden"
								id="prod_id" name="prod_id"
								value="<%=request.getParameter("prod_id")%>"> <input
								type="hidden" id="cor_org" name="cor_org"
								value="<%=request.getParameter("cor_org")%>">
						</h3>
					</li>
					<li class="list-group-item item_border"
						style="border-top: 1px solid #ddd;">回款编号：<span id="hkno">${hkNo.hkno}</span></li>
					<li class="list-group-item item_border"
						style="border-top: 1px solid #ddd; border-bottom: 0;">回款金额： <input
						type="number"
						style="border: none; width: 90%; color: #333; outline: none;"
						id="return_money" name="return_money">
					</li>
					<li class="list-group-item item_border"
						style="border-top: 1px solid #ddd;">回款系数： <input type="number"
						style="border: none; width: 90%; color: #333; outline: none;"
						id="return_coe" name="return_coe">
					</li>
				</ul>
			</div>
		</div> --%>
		<!-- 运营 -->
		<div class="panel panel-default" id="sale_rtn"
			style="width: 100%;">
			<div class="panel panel-default" style="width: 100%;">
				<ul id='list_ul' class="list-group">
					<li class="list-group-item"
						style="background-color: rgb(245, 245, 245); cursor: pointer;">
						<h3 class="panel-title" style="color: #a52410;">
							<span class="glyphicon glyphicon-th" aria-hidden="true"
								style="margin-right: 6px;"></span>项目发布回款<input type="hidden"
								id="prod_id" name="prod_id"
								value="<%=request.getParameter("prod_id")%>"> <input
								type="hidden" id="cor_org" name="cor_org"
								value="<%=request.getParameter("cor_org")%>">
						</h3>
					</li>
					<li class="list-group-item item_border"
						style="border-top: 1px solid #ddd;">回款编号：<span id="shkno">${shkNo.shkno}</span>
					</li>
					<li class="list-group-item item_border"
						style="border-top: 1px solid #ddd; border-bottom: 0;">回款金额： <input
						style="border: none; width: 90%; color: #333; outline: none;"
						id="s_return_money" name="s_return_money" oninput="qianfenwei('s_return_money')">
					</li>
					<li class="list-group-item item_border"
						style="border-top: 1px solid #ddd;">回款系数： <input type="number"
						style="border: none; width: 90%; color: #333; outline: none;"
						id="s_return_coe" name="s_return_coe">
					</li>
					<li class="list-group-item item_border"
						style="border-top: 1px solid #ddd;">回款日期：
                    	<span style="margin-right: 4px;"><input id="s_return_date" class="form_datetime" maxlength="20" type="text" style="border: none; width: 90%; color: #333; outline: none;">
						</span>
						<!--  <input type="number"
						style="border: none; width: 90%; color: #333; outline: none;"
						id="return_coe" name="return_coe"> -->
					</li>
				</ul>
			</div>
		</div>
		<!--主体-->
		<div class="row text-center">
			<button style="margin-right: 40px;" data-dismiss="modal"
				class="btn btn-lg btn-default" onclick="cancel_sHK()">取消</button>
			<button class="btn btn-lg" data-dismiss="modal"
				style="background-color: #5bc0de; color: #fff;" onclick="sure_sHK()">确定</button>
		</div>
</body>
</html>