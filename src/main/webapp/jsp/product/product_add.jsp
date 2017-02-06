<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>product_add</title>

<%@ include file="../header.jsp"%>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
String now_date = df.format(new Date());
%>
<script type="text/javascript">

$(function(){
	$(".form_datetime").datetimepicker({
		 format: 'yyyy-mm-dd',  
       weekStart: 1,  
       autoclose: true,  
       startView: 2,  
       minView: 2,  
       forceParse: false,  
       language: 'zh-CN'  
	});
	var gp_id = $('#gp_name option:selected').val();
	if(gp_id!=null && gp_id != ''){
		gp2glf();
	}else{
		alert("GP为空，请先录入GP信息!");
	}
});

function gp2glf(){
	var gp_id = $('#gp_name option:selected').val();

	$.ajax({
		url : 'glf.action', //后台处理程序     
		type : 'post', //数据发送方式     
		dataType : 'json', //接受数据格式        
		data : {
			'gp_id' : gp_id
			},
		success : function(data){
			if(data.status==1){
				var glf =data.glf.gp_dept;
				$("#cor_org").val(glf);
			}
		},error:function(result){
			//alert('系统异常,请稍后再试!');
			document.getElementById('content').innerHTML='系统异常,请稍后再试!';
    		$('#myal').modal('show');
		}
	});
}

function checkProdName(){
	var prod_name=$("#prod_name").val();

	$.ajax({
		url : 'prodAddCheck.action', //后台处理程序     
		type : 'post', //数据发送方式     
		async:  false,
		dataType : 'json', //接受数据格式        
		data : {
			'prod_name' : prod_name
			},
		success : function(data) {
			if(data.status==1){
				//alert("该产品已录入");
				document.getElementById('content').innerHTML="该产品已录入";
	        	$('#myal').modal('show');
				$("#prod_name").val("");
			}
		},error:function(result){
			//alert('系统异常,请稍后再试!');
			document.getElementById('content').innerHTML='系统异常,请稍后再试!';
    		$('#myal').modal('show');
		}
	});
}
	function cancel_prod(){
		document.location.href="/OMS/prodQuery.action";
	}

	
	function add_prodInfo(){
		var prod_name=$("#prod_name").val();
		var cor_org=$("#cor_org").val();
		
		var gp_id= $('#gp_name option:selected').val();
		var gp_name = $('#gp_name option:selected').text();
		var type_name = $('#prod_type option:selected').text();
		var current_name = $('#prod_currency option:selected').text();
		var mana_name = $('#prod_mana option:selected').text();
		
		
		if(prod_name==null || prod_name== ''){
			/* document.getElementById('content').innerHTML="请输入产品名称";
    		$('#myal').modal('show'); */
			alert("请输入产品名称");
			return;
		}   
		
		if(cor_org==null || cor_org== ''){
			/* document.getElementById('content').innerHTML="请输入合作机构";
    		$('#myal').modal('show'); */
			alert("请输入合作机构");
			return;
		}   
		
		if(gp_id==""|| gp_id==null){
		/* 	document.getElementById('content').innerHTML="请录入产品的GP";
    		$('#myal').modal('show'); */
			alert("请录入产品的GP");
			return;
		}
		
		showLoad(); 
		var prod_type = $("#prod_type").val().trim();
		var prod_name = $("#prod_name").val().trim();
		var cor_org = $("#cor_org").val().trim();
		var s_raise_date = $("#s_raise_date").val().trim();
		var e_raise_date = $("#e_raise_date").val().trim();
		var prod_currency = $("#prod_currency").val().trim();
		var invest_goal = $("#invest_goal").val().trim();
		var prod_mana = $("#prod_mana").val();
		var risk_level = $("#risk_level").val().trim();
		$.ajax({
			url : 'prod_addInfo.action', //后台处理程序     
			type : 'post', //数据发送方式     
			//async:  true,
			dataType : 'json', //接受数据格式        
			data : {
				'gp_id' : gp_id,
				'gp_name' : gp_name,
				'type_name' : type_name,
				'current_name' : current_name,
				'mana_name' : mana_name,
				'prod_type' : prod_type,
				'prod_name' : prod_name,
				'cor_org' : cor_org,
				's_raise_date' : s_raise_date,
				'e_raise_date' : e_raise_date,
				'prod_currency' : prod_currency,
				'invest_goal' : invest_goal,
				'prod_mana' : prod_mana,
				'risk_level' : risk_level
				},
			success : function(data) {
				hideLoad();
				if(data.status==1){
					//alert(data.msg);
					alert("提交成功");
					location.href="prodQuery.action";
					//location.history.go(-1);
					//location.href="prodQuery.action";
				}else if(data.status ==2){
					alert(data.msg);
				}
			},error:function(result){
				alert('系统异常,请稍后再试!');
			}
		});
		
	/* 	
		$.ajax({
			url : 'prod_addInfo.action', //后台处理程序     
			type : 'post', //数据发送方式     
			dataType : 'json', //接受数据格式        
			data : {
				'gp_name' : gp_name,
				'prod_type' : prod_type,
				'prod_name' : prod_name,
				'cor_org' : cor_org,
				's_raise_date' : s_raise_date,
				'e_raise_date' : e_raise_date,
				'prod_currency' : prod_currency,
				'invest_goal' : invest_goal,
				'prod_mana' : prod_mana,
				'risk_level' : risk_level
				},
			success : function(data) {
				if (data.status == 1) {
					alert(data.msg);
					//document.getElementById('content').innerHTML=data.msg;
	        		//$('#myal').modal('show'); 
					document.location.href="/OMS/prodQuery.action";
				}else if(data.status ==2){
					alert(data.msg);
					//document.getElementById('content').innerHTML=data.msg;
	        		//$('#myal').modal('show');
					//location.href="/OMS/prodQuery.action";
				}
			},error:function(result){
				alert('系统异常,请稍后再试');
				// document.getElementById('content').innerHTML='系统异常,请稍后再试!';
        		$('#myal').modal('show'); 
			}
		});  */
	}	
</script>

</head>
 
<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->
	<!--主体-->
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
<div class="container m0 bod top70" id="zt">
	 

		<div class="row top10 mym">
			<div class="form-group">
					<div class="col-md-8 myright" style="width: 100%; margin: 0 auto;">
						<!-- 1 -->
						<div class="myright-n">
							<div class="myNav row" style="border-bottom: 1px solid #a52410;">
								<!-- <a href="#"><i class="glyphicon glyphicon-floppy-saved"></i> 保存 </a> -->
								<h4 style="color: #a52410;">产品添加</h4>
							</div>
						</div>
						<!-- 2 -->
						<div class="panel panel-default" style="width: 100%;">
							<ul class="list-group">

								<li class="list-group-item"
									style="background-color: rgb(245, 245, 245); font-weight: bold;">
									产品基本资料</li>
								<li class="list-group-item"><span
									style="color: red; margin-right: 4px; float: left;">*</span> <span>GP名称</span>
										<select id="gp_name" name="gp_name" onchange="gp2glf()">  
				                       		<s:iterator  value="#request.gpName" var="gpName">
										        <option id="gp_name" value='<s:property value="#gpName.gp_id"/>'><s:property value="#gpName.gp_name"/></option>  
									  		</s:iterator>
									    </select>
								</li>
								<li class="list-group-item">
								<span style="color: red; margin-right: 4px; float: left;">*</span> <span>
										产品名称</span> <input  required="required" type="text" id="prod_name"
									name="prod_name" style="width: 75%; border: none; outline: none;" onblur="checkProdName()"></li>
								<li class="list-group-item"><span
									style="color: red; margin-right: 4px; float: left;">*</span> <span>渠道</span>
								<select id="prod_type" name="prod_type">  
                       		<s:iterator  value="#request.prodDict" var="prodDict">
						        <option value='<s:property value="#prodDict.dict_value"/>'><s:property value="#prodDict.dict_name"/></option>  
					  		</s:iterator>
					    </select>
								
								</li>
								<li class="list-group-item"><span style="color: red; margin-right: 4px; float: left;">*</span><span>产品币种</span> 
									<select id="prod_currency" name="prod_currency">  
									    
			                       		<s:iterator  value="#request.prodCurDict" var="prodCurDict">
									        <option value='<s:property value="#prodCurDict.dict_value"/>'><s:property value="#prodCurDict.dict_name"/></option>  
								  		</s:iterator>
								    </select>
								</li>
								<li class="list-group-item"><span style="color: red; margin-right: 4px; float: left;">*</span>
								<span>管理方</span> <input
									type="text" id="cor_org" name="cor_org" required="required"
								style="width: 90%; border: none; outline: none;"></li>
								<li class="list-group-item"><span>开始募集时间</span> 
									<input id="s_raise_date" 
								name="s_raise_date" size="16" type="text" value="<%=now_date %>" readonly class="form_datetime"/>
									</li>
									
								<li class="list-group-item"><span>募集结束时间</span> <input id="e_raise_date" 
								name="e_raise_date" size="16" type="text" value="<%=now_date %>" readonly class="form_datetime"/>
								</li>
								<li class="list-group-item"><span>产品风险等级</span> <input
									type="text" id="risk_level" name="risk_level"
									style="width: 90%; border: none; outline: none;"></li>
								<li class="list-group-item"><span>产品经理</span>
								<select id="prod_mana" name="prod_mana">  
		                       		<s:iterator  value="#request.prodManaDict" var="prodManaDict">
								        <option value='<s:property value="#prodManaDict.user_id"/>'><s:property value="#prodManaDict.real_name"/></option>  
							  		</s:iterator>
					    		</select>
								<!--  <input type="text" id="prod_mana" name="prod_mana"
									style="width: 90%; border: none; outline: none;"> -->
								</li>
								<li class="list-group-item"><span style="">产品描述</span> <textarea
									id="invest_goal" name="invest_goal"
									style="width: 1099px;height:135px;  outline: none;margin-left:25px;"></textarea></li>
								
							</ul>
						</div>
					</div>
				<!--end 右-->
				<div class="modal-footer">
					<div class="row text-center">
						<button style="margin-right: 40px;" data-dismiss="modal"
							onclick="cancel_prod()" class="btn btn-lg btn-default">取消</button>
						<button class="btn btn-lg" data-dismiss="modal"
							onclick="add_prodInfo()" id="submit_btn"
							style="background-color: #5bc0de; color: #fff;">提交审批</button>
					</div>
				</div>
				</div>
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

			<script>
				var _hmt = _hmt || [];
				(function() {
					var hm = document.createElement("script");
					hm.src = "//hm.baidu.com/hm.js?b3a3fc356d0af38b811a0ef8d50716b8";
					var s = document.getElementsByTagName("script")[0];
					s.parentNode.insertBefore(hm, s);
				})();
			</script>


			<!--end底部-->

			<script type="text/javascript">
				function tx() {
					$.ajax({
						type : "POST",
						url : "myPhotoSave.aspx",
						data : "tx=" + myFrame.window.tx,
						success : function(msg) {
							if (msg != "n") {
								$("#tou").attr('src', "tx/" + msg + ".png");
							}
						}
					});

				}
				function clockms(id) {
					var yz = $.ajax({
						type : 'post',
						url : 'mess.aspx',
						data : {
							id : id
						},
						cache : false,
						dataType : 'text',
						success : function(data) {

						},
						error : function() {
						}
					});
				}
			</script>

			<!-- 左边变色特效 -->
			<script>
				window.onload = function() {
					var list_grop = document.getElementById('list-grop');
					var list_li = list_grop.getElementsByTagName('li');

					for (var i = 0; i < list_li.length; i++) {
						// list_li[i].index=i;
						list_li[i].onclick = function() {

							for (var j = 0; j < list_li.length; j++) {
								list_li[j].style.background = 'white';
							}
							this.style.background = '#ace1f1';

						}
					}
				}
			</script>


		</div>

</body>
</html>