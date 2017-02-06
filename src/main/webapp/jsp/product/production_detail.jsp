<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>product_detail</title>
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

<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath() %>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>

<script type="text/javascript">	
	var n = 1;
	var prod_id = <%=request.getParameter("prod_id")%>;
	var prodTypeValue= "${prodList.prodTypeValue}";
	var curValue= "${prodList.curValue}";  
	var prodManaValue= "${prodList.user_id}";  
	var prod_status = "${prodStatus.prod_status}"
	var role_id = "${role_id}";
	
	var origin_curName= "${prodList.Curname}"; 
	var origin_prodMana= "${prodList.real_name}"; 
	var orign_prod_invest = "${prodList.invest_goal}";   
	var orign_prod_risk = "${prodList.prod_risk_level}";	
	var orign_sraise_date ="${prodList.s_raise_date}";	 
	var orign_eraise_date = "${prodList.e_raise_date}";
	//1 财务  2 运营
	var flaghk = 2;
	$(function() {
	//alert(role_id);
	
	
	
  	if(role_id == 7){
		$("#close_button").show();
	}
  	
  	if(role_id==6){
  		$("#reset").show();
  	}else{
  		$("#reset").hide();
  	}
	  
		if(prod_status == "2"){
/* 			document.getElementById('close_button').style.display="none"; */
			$("#close_button").attr("disabled","true");
		}
			
		
		$(".form_datetime").datetimepicker({
			 format: 'yyyy-mm-dd',  
	       weekStart: 1,  
	       autoclose: true,  
	       startView: 2,  
	       minView: 2,  
	       forceParse: false,  
	       language: 'zh-CN'  
		});
		
		window.frames["pro_frame"].location.href="/OMS/product_demo.action?prod_id="+prod_id;
		$("#type_name").val(prodTypeValue); 
		$("#cur_name").val(curValue);
		$("#prod_mana").val(prodManaValue);
	});

	function save_prod(){
		
		//var prod_gp = $("#prod_gp").val();  
		//var prod_na	= $("#prod_na").val();
		//var type_name = $("#type_name").val();	
		//var org_name = $("#org_name").val();	
		var cur_name = $("#cur_name").val();	
		var prod_invest = $("#prod_invest").val();  
		var prod_risk = $("#prod_risk").val();	
		var sraise_date = $("#sraise_date").val();	 
		var eraise_date = $("#eraise_date").val();
		var prod_mana = $("#prod_mana").val();
		
		var prod_curName =$('#cur_name option:selected').text(); ;
		var prod_manaName = $('#prod_mana option:selected').text();;
		
	var change=0;
		
	/* 	if( curValue!=cur_name){alert("产品币种已修改，将再次审批");} 
		if( prodManaValue!=prod_mana){alert("产品经理已修改，将再次审批");} 
		if( orign_prod_risk!=prod_risk){alert("产品产品风控等级已修改，将再次审批");} 
		if( orign_prod_invest!=prod_invest){alert("投资目标已修改，将再次审批");} 
		if( orign_sraise_date!=sraise_date){alert("开始募集时间已修改，将再次审批");} 
		if( orign_eraise_date!=eraise_date){alert("募集结束时间已修改，将再次审批");} */ 
	if( curValue!=cur_name){change=1;} 
	if( prodManaValue!=prod_mana){change=1;} 
	if( orign_prod_risk!=prod_risk){change=1;} 
	if( orign_prod_invest!=prod_invest){change=1;} 
	if( orign_sraise_date!=sraise_date){change=1;} 
	if( orign_eraise_date!=eraise_date){change=1;}
	if(change==0){
		alert("数据未作更改!");
		return false;
	}else{
		var r = confirm("是否提交审批？")
		if (r == false) {
			return false;
		}
	}
		
		$.ajax({         
	  	    url:'save_prod.action',  //后台处理程序     
	        type:'post',         //数据发送方式     
	        async:true,
	        dataType:'json',     //接受数据格式        
	        data:{			
	        	"prod_id" :prod_id,
	    		"cur_name":cur_name,
	    		"prod_curName":prod_curName,
	    		"prod_invest":prod_invest,
	    		"prod_risk":prod_risk,
	    		"sraise_date":sraise_date,
	    		"eraise_date":eraise_date,
	    		"prod_mana":prod_mana,
	    		"prod_manaName":prod_manaName,
	    		"curValue":curValue ,
	    		"prodManaValue":prodManaValue,
	    		"origin_curName":origin_curName,
	    		"origin_prodMana":origin_prodMana,
	    		"orign_prod_risk":orign_prod_risk,
	    		"orign_prod_invest":orign_prod_invest,
	    		"orign_sraise_date":orign_sraise_date,
	    		"orign_eraise_date":orign_eraise_date
	        },
	        success:function (data){
	        	if(data.status==1){
	        		alert(data.msg);
	        		/* document.getElementById('content').innerHTML='保存成功';
        			$('#myal').modal('show');  */
	        		parent.location.reload();
	        	}else if(data.status==2){
	        		alert(data.msg);
	        		document.getElementById('content').innerHTML=data.msg;
        			$('#myal').modal('show');
	        	}
	        },error:function(result){
	        	//alert('系统异常,请稍后再试!');
	        	document.getElementById('content').innerHTML='系统异常,请稍后再试!';
    			$('#myal').modal('show');
	        }
	    }); 
	}

	function prod_close(){
		 var prod_id = $("#prod_id").val(); 
		$.ajax({         
	  	    url:'prod_close.action',  //后台处理程序     
	        type:'post',         //数据发送方式     
	        dataType:'json',     //接受数据格式        
	        data:{
				'prod_id':prod_id
	        },
	        success:function (data){
	        	if(data.status==1){
	        		alert(data.msg);
	        		parent.location.reload();
	        	}else if(data.status==2){
	        		alert(data.msg);
	        	}
	        },error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
	    }); 
	}
	
	function pass_check(obj){
		var flag = obj;
		var prod_id = $("#prod_id").val(); 
		$.ajax({         
	  	    url:'prod_check.action',  //后台处理程序     
	        type:'post',         //数据发送方式     
	        dataType:'json',     //接受数据格式        
	        data:{
				'flag':flag,
				'prod_id':prod_id
	        },
	        success:function (data){
	        	if(data.status==1){
	        		alert(data.msg);
	        		parent.location.reload();
	        	}else if(data.status==2){
	        		alert(data.msg);
	        	}
	        },error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
	    }); 
	
	}
	//重置操作
	function reset() {
		var mail_no=$("#mail_no").val();
		var prod_id = $("#prod_id").val(); 
		if(mail_no==""){
			return false;
		}
		alert(mail_no);
		$.ajax({         
	  	    url:'product_reset.action',  //后台处理程序     
	        type:'post',         //数据发送方式     
	        dataType:'json',     //接受数据格式        
	        data:{
				'mail_no':mail_no
	        },
	        success:function (data){
	        	if(data.status==1){
	        		alert("重置成功!");
	        		window.location.href = "production_detail.action?prod_id="+prod_id;
	        	}else if(data.status==2){
	        		alert("重置失败!");
	        	}
	        },error:function(result){
	        	alert('系统异常,请稍后再试!');
	        }
		  }); 
		
	}
	
/* 	
	function pass_reject(){
		alert(321);
		parent.location.reload();
	} */
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" 
               aria-hidden="true">×
            </button>
            <h4 class="modal-title" id="myModalLabel">
               产品审核
            </h4>
         </div>
         <div class="modal-body">
            	是否确定审核通过该产品？
         </div>
         <div class="modal-footer" align="center">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">取消
            </button>
            <button type="button" class="btn btn-success" onclick="pass_check('pass')">通过审核</button>
            <button type="button" class="btn btn-danger" onclick="pass_check('reject')">驳回</button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" id="prod_save" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" 
               aria-hidden="true">×
            </button>
            <h4 class="modal-title" id="myModalLabel">
               保存产品
            </h4>
         </div>
         <div class="modal-body">
            	确定保存产品信息吗？
         </div>
         <div class="modal-footer" align="center">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">取消
            </button>
            <button type="button" class="btn btn-success" onclick="save_prod()">保存</button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="modal fade" id="close_prod" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header" >
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
            关闭产品
            </h4>
         </div>
         <div class="modal-body">
        	确定要关闭该产品吗？
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">取消
            </button>
            <button type="button" class="btn btn-primary" onclick="prod_close()">
               	关闭产品
            </button>
         </div>
      </div>/.modal-content
</div>/.modal
</div>
	<!--nav-->
	<%@ include file="../header.jsp"%>
	<!--主体-->
	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="<%=request.getContextPath()%>/jsp/index.jsp">
							<i class='fa  fa-home'></i>&nbsp;首页
					</a></li>
					<li class="active">产品</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="#" data-toggle="modal" data-target="#exampleModal2"> <img
						id="tou" src="/OMS/image/person.png" class="f imgr20">
					</a>
					<div class="f imgf20"
						style="width: 60%; height: 90px; line-height: 15px;">
						<h4>${prodList.prod_name}</h4>
						<button id="close_button" class="btn btn-success btn-lg" data-toggle="modal" data-target="#close_prod" style="width: 87%;height: 56%;display:none;">关闭产品</button>
					   <!-- <div id='close_div' class="alert alert-warning top20" role="alert" style="
					        background-color:#006633;text-align: center;position: relative;padding:5px 0;color:#fff;">
					        <p id='close_p'>关闭</p> -->
					        <!--  <button id='close_btn' style="width: 100%;height: 100%;opacity: 0;position: absolute;left: 0;top:0;"></button> --> 
				         
				        <!-- </div> -->
						<%-- <p>总认缴:${prodList.pay_sum}</p>
						<p>总实缴:${prodList.pay_true}</p>
						 --%>
						<input type="hidden" value="<%=request.getParameter("prod_id")%>"
							id="prod_id" />
						
					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n1">
				
					  <!--  <div id='close_div' class="alert alert-warning top20" role="alert" style="
					        background-color:#006633;text-align: center;padding:5px 0;color:#fff;">
					        <p id='close_p'>关闭</p>
					         <button id='close_btn' style="width: 100%;height: 100%;opacity: 0;left: 0;top:0;"></button> 
				         
				        </div> -->
					<!-- <div id='close_div' class="alert alert-warning top20" role="alert"
						style="background-color: #006633; text-align: center; position: relative; padding: 5px 0; color: #fff;">
						<p id='close_p'>关闭</p>
						<button id='close_btn'
							style="width: 100%; height: 100%; opacity: 0; position: absolute; left: 0; top: 0;"></button>
					</div> -->
					<input type="hidden" id="gp_id" name="gp_name" value="${prodList.gp_id}"/>
					<ul class="list-group">
						<li class="list-group-item">GP名称：${prodList.gp_name}</li>
						<li class="list-group-item">产品渠道：${prodList.prodTypeName}</li>
						<li class="list-group-item">合作机构：${prodList.cor_org}</li>
						<li class="list-group-item">产品币种：${prodList.Curname}</li>
						<li class="list-group-item">投资目标：${prodList.invest_goal}</li>
						<li class="list-group-item">产品风控等级：${prodList.prod_risk_level}</li>
						<%--  <li class="list-group-item">
             产品币种：${prodList.prod_currency}</li> --%>
						<li class="list-group-item">开始募集时间：${prodList.s_raise_date}</li>
						<li class="list-group-item">募集结束时间：${prodList.e_raise_date}</li>
						<li class="list-group-item">产品状态：${prodList.prodCheName}</li>
						<li class="list-group-item">产品经理：${prodList.real_name}</li>
					</ul>
				</div>
				<div class="df"></div>
			</div>

			<!--end 左-->
			<!--右-->

			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<div class="myNav row" id='myNav'>
						<%-- <span id='save_prod' style="cursor: pointer; margin-left: 10px;"  data-toggle="modal" data-target="#prod_save"><i class="glyphicon glyphicon-floppy-disk"></i> 保存</span> 
						<span id='add_GP' style="cursor: pointer; margin-left: 10px;"><i class="fa fa-plus"></i> 修改GP</span> 
						<span id='add_LP' style="cursor: pointer; margin-left: 10px;"><i class="fa fa-plus"></i> 添加合伙企业 </span> 
						<span id='add_ED' style="cursor: pointer; margin-left: 10px;"><i class="fa fa-sign-out"></i>  </span> 
						<span id='reg_JD' style="cursor: pointer; margin-left: 10px;"><i class="fa fa-pencil-square-o"></i> 进度登记 </span> 
						<span id='sure_HK' style="cursor: pointer; margin-left: 10px;"><i class="fa fa-check-square-o"></i> 确认回款 </span> 
						<span id='fee_manage' style="cursor: pointer; margin-left: 10px;"><i
							class="fa fa-cog"></i> 费用管理</span>
						<span id='prod_check' style="cursor: pointer; margin-left: 10px;" data-toggle="modal" data-target="#myModal"><i	class="glyphicon glyphicon-floppy-saved"></i> 审核通过</span>
						<span id='r_fee' style="cursor: pointer; margin-left: 10px;"><i
							class="fa fa-sign-out"></i> 募集费用 </span>  --%>
						
					<s:iterator value="#request.prodMenuInfo" var="prodMenuInfo">
							<span id='<s:property value="#prodMenuInfo.button_id"/>' onclick="menuonclick('<s:property value="#prodMenuInfo.button_id" />')" style="cursor: pointer; margin-left: 10px;"><i class="<s:property value="#prodMenuInfo.func_remark"/>"></i><s:property value="#prodMenuInfo.func_describle"/></span> 
					</s:iterator> 
					<s:iterator value="#request.prodMenuInfo1" var="prodMenuInfo1">
							<span id='<s:property value="#prodMenuInfo1.button_id"/>' style="cursor: pointer; margin-left: 10px;"  data-toggle="modal" data-target="#prod_save"><i class="<s:property value="#prodMenuInfo1.func_remark"/>"></i><s:property value="#prodMenuInfo1.func_describle"/></span> 
					</s:iterator> 
					  
					<s:iterator value="#request.prodMenuInfo2" var="prodMenuInfo2">
							<span id='<s:property value="#prodMenuInfo2.button_id"/>' style="cursor: pointer; margin-left: 10px;"  data-toggle="modal" data-target="#myModal"><i class="<s:property value="#prodMenuInfo2.func_remark"/>"></i><s:property value="#prodMenuInfo2.func_describle"/></span> 
					</s:iterator>  
					<span id='reset' onclick="reset();" style="cursor: pointer; margin-left: 10px;">重置</span> 
					</div>
					<div class="row topx myMinh">

						<div class="spjz">

							<div class="panel panel-default" style="width: 100%;">
								<div class="panel panel-default" style="width: 100%;">
									<!--产品基本信息  -->
									<ul id='list_ul' class="list-group">
										<li class="list-group-item"
											style="background-color: rgb(245, 245, 245); cursor: pointer;">
											<h3 class="panel-title" style="color: #a52410;">
												<span class="glyphicon glyphicon-edit" aria-hidden="true"
													style="margin-right: 6px;"></span>产品基本信息 <span
													class="glyphicon glyphicon-eye-open" style="float: right;"></span>
											</h3>

										</li>

										<li class="list-group-item item_border"
											style="border-top: 1px solid #ddd;">GP名称： 
											<input type="text" id="prod_gp" name = "prod_gp" disabled="disabled" 
											style="border: none; width: 90%; color: #333; outline: none;"
											value="${prodList.gp_name}">
										</li>
										<li class="list-group-item item_border">产品名称 
										<input type="text" id = "prod_na" name = "prod_na" disabled="disabled" 
											style="border: none; width: 90%; color: #333; outline: none;"
											value="${prodList.prod_name}">
										</li>
										<li class="list-group-item item_border">产品渠道： 
											<select id="type_name" name="type_name" disabled="disabled">  
					                       		<s:iterator  value="#request.prodDictChannel">
														<option value="${dict_value}">${dict_name}</option>
										  		</s:iterator>
								           </select>
										</li>
										<li class="list-group-item item_border" 
											style="border: 0; border-top: 1px dashed #ddd;">管理方： 
											<input type="text" id ="org_name" name = "org_name" disabled="disabled" 
											style="border: none; width: 90%; color: #333; outline: none;"
											value="${prodList.cor_org}">
										</li>
										<li class="list-group-item item_border">产品币种： 										
										<select id="cur_name" name="cur_name">  
											<option value="">---请选择---</option>
				                       		<s:iterator  value="#request.prodDictCurrency">
										    	<option value="${dict_value}">${dict_name}</option> 
									  		</s:iterator>
							           </select>
										</li>
										<li class="list-group-item item_border">投资目标： <input
											type="text" id = "prod_invest" name = "prod_invest" 
											style="border: none; width: 90%; color: #333; outline: none;"
											value="${prodList.invest_goal}">
										</li>				
										<li class="list-group-item item_border">产品风控等级： <input
											type="text" id = "prod_risk" name = "prod_risk"
											style="border: none; width: 85%; color: #333; outline: none;"
											value="${prodList.prod_risk_level}">
										</li>
										<li class="list-group-item item_border">开始募集时间：
										 	<%-- <input	type="text" id = "sraise_date" name = "sraise_date"
											style="border: none; width: 85%; color: #333; outline: none;"
											value="${prodList.s_raise_date}"> --%>
											<input id="sraise_date" placeholder="募集时间"
											name="sraise_date" size="14" type="text" value="${prodList.s_raise_date}" 
											readonly class="form_datetime"/>
										</li>
										<li class="list-group-item item_border">募集结束时间： 
											<%-- <input	type="text" id = "eraise_date" name = "eraise_date"
											style="border: none; width: 85%; color: #333; outline: none;"
											value="${prodList.e_raise_date}"> --%>
											<input id="eraise_date" placeholder="募集时间"
											name="eraise_date" size="14" type="text" value="${prodList.e_raise_date}" 
											readonly class="form_datetime"/>
										</li>
										<li class="list-group-item item_border">产品经理： 
											<%-- <input type="text" id = "prod_mana" name = "prod_mana"
											style="border: none; width: 85%; color: #333; outline: none;"
											value="${prodList.e_raise_date}"> --%>
											<select id="prod_mana" name="prod_mana">  
					                       		<s:iterator  value="#request.prodManaDict">
														<option value="${user_id}">${real_name}</option>
										  		</s:iterator>
								            </select>
										</li>
										<%-- <li>
										  <input type="text" value="${prodList.mail_no}" id="mail_no">
										</li> --%>
										<!-- <li class="list-group-item item_border" >产品系数：
             <input type="text" style="border:none;width: 90%;color: #333;outline: none;" value="3">
             </li> -->
									</ul>

								</div>
								<iframe id='pro_frame' name="pro_frame" width="100%;" width="700"
									height="300" frameborder="0" scrolling="auto"></iframe>
						
						<!-- document.write("<iframe src='xx.aspx?id=" + id + "'></iframe>") -->
							</div>
						</div>
					</div>
				</div>
				<!--end 右-->
			</div>
			<!--底部-->
			<nav class="foot navbar-inverse navbar-fixed-bottom">
				<ul class="list-inline">
					<li class="footer-ss"><a href="javascript:void(0)"
						data-container="body" data-toggle="popover" data-placement="top"
						data-html="true" data-content=" " data-original-title="" title="">更多
							&nbsp;<i class="fa fa-angle-down"></i>
					</a></li>
					<li class="footer-ss">在线反馈</li>
					<li class="footer-ss">帮助中心</li>
					<li>Copyright © 2016</li>
				</ul>
				<ul class="list-inline text-right">
					<li></li>
				</ul>
			</nav>
			<!-- 左边变色特效 -->
			<script>
				window.onload = function() {
					// iframeadd_LP
					// display
					var list_ul = document.getElementById('list_ul');
					var list_li = list_ul.getElementsByTagName('li');
					var isDisplay = true;
					for (var i = 1; i < list_li.length; i++) {
						list_li[i].style.display = 'none';
					}
					list_li[0].onclick = function() {

						if (!isDisplay) {
							for (var i = 1; i < list_li.length; i++) {
								list_li[i].style.display = 'none';
							}
							isDisplay = true;
						} else {
							for (var i = 1; i < list_li.length; i++) {
								list_li[i].style.display = 'block';
							}
							isDisplay = false;
						}
					}
					/* 
					var add_GP = document.getElementById('add_GP');
					var add_LP = document.getElementById('add_LP');
					var add_ED = document.getElementById('add_ED');
					var reg_JD = document.getElementById('reg_JD');
					var sure_HK = document.getElementById('sure_HK');
					var prod_check = document.getElementById('prod_check');
					var r_fee = document.getElementById('r_fee');
					var fee_manage = document.getElementById('fee_manage');
					var save_prod = document.getElementById('save_prod');
				
					var pro_frame = document.getElementById('pro_frame');
					
					add_GP.onclick = function() {
						pro_frame.src = 'GP_add_demo.action';
						add_GP.style.color = 'red';
						add_LP.style.color = '#333';
						add_ED.style.color = '#333';
						reg_JD.style.color = '#333';
						sure_HK.style.color = '#333';
						fee_manage.style.color = '#333';
					}
					  

						add_LP.onclick = function() {
							pro_frame.src = 'LP_add.action';
							add_LP.style.color = 'red';
							add_GP.style.color = '#333';
							add_ED.style.color = '#333';
							reg_JD.style.color = '#333';
							sure_HK.style.color = '#333';
							fee_manage.style.color = '#333';
						}
					 

						add_ED.onclick = function() {
							pro_frame.src = 'add_ED.action';
							add_ED.style.color = 'red';
							add_GP.style.color = '#333';
							add_LP.style.color = '#333';
							reg_JD.style.color = '#333';
							sure_HK.style.color = '#333';
							fee_manage.style.color = '#333';
						}	
					

						reg_JD.onclick = function() {
							pro_frame.src = 'reg_JD.action';
							reg_JD.style.color = 'red';
							add_GP.style.color = '#333';
							add_LP.style.color = '#333';
							add_ED.style.color = '#333';
							sure_HK.style.color = '#333';
							fee_manage.style.color = '#333';
						}
						
						sure_HK.onclick = function() {
							var prod_id = $("#prod_id").val();
							var cor_org = $("#cor_org").val();

							pro_frame.src = 'sure_HK.action?prod_id=' + prod_id
									+ "&cor_org=" + cor_org+"&flaghk="+flaghk;
							sure_HK.style.color = 'red';
							add_GP.style.color = '#333';
							add_LP.style.color = '#333';
							add_ED.style.color = '#333';
							reg_JD.style.color = '#333';
							fee_manage.style.color = '#333';
						}
	
						prod_check.onclick = function() {
							pro_frame.src = 'prod_check.action';
							prod_check.style.color = 'red';
							add_GP.style.color = '#333';
							add_LP.style.color = '#333';
							add_ED.style.color = '#333';
							reg_JD.style.color = '#333';
							sure_HK.style.color = '#333';
							fee_manage.style.color = '#333';
							r_fee.style.color = '#333';
						}
						
						r_fee.onclick = function() {
							
							pro_frame.src = 'r_fee.action';
							add_GP.style.color = '#333';
							add_LP.style.color = '#333';
							add_ED.style.color = '#333';
							reg_JD.style.color = '#333';
							sure_HK.style.color = '#333';
							fee_manage.style.color = '#333';
							r_fee.style.color = 'red';
							
						}
						
						fee_manage.onclick = function() {
							var prod_id = $("#prod_id").val();
							pro_frame.src = 'fee_manage.action?prod_id=' + prod_id;
							fee_manage.style.color = 'red';
							add_GP.style.color = '#333';
							add_LP.style.color = '#333';
							add_ED.style.color = '#333';
							reg_JD.style.color = '#333';
							sure_HK.style.color = '#333';
						}
						
						save_prod.onclick = function() {
							pro_frame.src = 'save_prod.action';
							save_prod.style.color = 'red';
							add_GP.style.color = '#333';
							add_LP.style.color = '#333';
							add_ED.style.color = '#333';
							reg_JD.style.color = '#333';
							sure_HK.style.color = '#333';
							fee_manage.style.color = '#333';
							r_fee.style.color = '#333';
						}
					*/	
				}
			</script>
			<script>
				// 按钮变色
		/* 	    var close_btn=document.getElementById('close_btn');
			    var close_div=document.getElementById('close_div');
			    var close_p=document.getElementById('close_p');
			    var isClick=false;

          		close_btn.onclick=function(){

              if(!isClick){
                close_div.style.backgroundColor='red';
                close_p.innerHTML='已关闭'; 
                close_btn.disabled=true;
                isClick=true;
              }
              else{
                close_div.style.backgroundColor='#006633';
                close_p.innerHTML='关闭';
                close_p.style.color='white';
                isClick=false;
              }
         } */
				
				//function menuonclick(id,url)
				function menuonclick(obj)
				{
					//取span全集 置灰
					//document.getElementById('myNav').getElementsByTagName('span').style.color= '#333';
					var pro_frame = document.getElementById('pro_frame');
					var prod_id = $("#prod_id").val();
					var cor_org = $("#cor_org").val();
					var action=obj+".action?prod_id="+prod_id+"&cor_org="+cor_org+"&flaghk="+flaghk;
					var color = obj;
					//var obj=document.getElementById('close_btn');
					document.getElementById(obj).style.color = 'red';
					//obj.src=url;
					pro_frame.src =action;
					//alert(123);
				}
			</script>
		</div>
</body>
</html>