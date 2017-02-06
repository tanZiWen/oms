<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
String now_date = df.format(new Date());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>compay_detail</title>
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
<script src="/OMS/js/cust.js"></script>
<script>
	var n = 1;
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
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
					<li><a href="/OMS/cust.action">个人客户管理</a></li>
					<li class="active">个人-公司</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="http://www.jq22.com/myhome#" data-toggle="modal"
						data-target="#exampleModal2"> <img id="tou"
						src="<%=request.getContextPath()%>/image/person1.png" class="f imgr20">
					</a>
					<div class="f imgf20">
						<h4>个人-${cust_name}</h4>
						
					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">

					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="fa fa-lightbulb-o"></i> 公司介绍 <br>

					</div>
					<%-- <s:set name="name" value="model.userId" /> --%>
                        <s:if test="#request.status6 ==1">
                               <s:iterator value="#request.comp_detail1" var="lis">
									<ul class="list-group" id="list-grop">
										<li class="list-group-item" style="background-color: #ace1f1;">
											<a style="display: inline-block; width: 100%; height: 100%; border: none; outline: none; text-align: center; text-decoration: none; color: #999;"
											href="javascript:compList(${lis.cust_id },${lis.comp_id })">  <s:property
													value="#lis.comp_name" /> </a>
										</li>
									</ul>
					            </s:iterator>
                           </s:if>
                           
                    
                        <s:else>
                              <li>该客户还未添加公司，请添加</li> 
                       </s:else>
					


				</div>
				<div class="df"></div>
			</div>

			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<div class="myNav row">
			<s:if test="#request.role_id==1">
					<s:if test="#request.status6 ==1">
						<a href="javascript:comp_edit()" style="outline: none;"><i class="glyphicon glyphicon-floppy-disk"></i> 保存</a>
						</s:if>
					
                        <a href="#" style="outline: none;" data-toggle="modal"  data-target="#opener6">
                        <i class="glyphicon glyphicon-floppy-saved"></i> 添加公司 </a>
                 </s:if>
					</div>
					<div class="row topx myMinh" style="">

						<div class="spjz" style="">
						<s:if test="#request.status2==1 ">
<form id="formidcomp" method='post' action="comp_edit.action">
      
							<div class="panel panel-default" style="width: 100%;">
							<input  name="cust_id1" id="cust_id1" type="hidden"  value="${cust_id}"/>
                         <input  name="comp_id" id="comp_id" type="hidden"  value="${comp_id }"/> 
								<ul id="ul" class="list-group">
									<li class="list-group-item"
										style="background-color: rgb(245, 245, 245);">
										<h3 class="panel-title" style="color: #a52410;">
											<span class="glyphicon glyphicon-edit" aria-hidden="true"
												style="margin-right: 6px;"></span>公司基本信息
                         
										</h3>

									</li>
									
									<li class="list-group-item">公司名称：<input name="comp_name" id="comp_name" value="${comp_detail.comp_name}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "30" /></li>
            
									<li class="list-group-item item_border">公司类型：<input name="comp_type" id="comp_type" value="${comp_detail.comp_type}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "30"/></li>
            
									<li class="list-group-item item_border">营业执照注册号(统一社会信用代码)：<input name="license" id="license" value="${comp_detail.license}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "30" /></li>

									<li class="list-group-item item_border" style="border-bottom: 0;">法人：<input name="legal" id="legal" value="${comp_detail.legal}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "20" /></li>
									<li class="list-group-item item_border">组织机构代码证：<input name="org_code_cert" id="org_code_cert" value="${comp_detail.org_code_cert}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "30" /></li>

									<li class="list-group-item item_border">税务登记号：<input name="taxid" id="taxid" value="${comp_detail.taxid}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "100" /></li>

                                     <li class="list-group-item item_border">成立日期：<input name="reg_date" id="reg_date" value="${comp_detail.reg_date}" class="form_datetime" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "10" /></li>

									<li class="list-group-item item_border">经营期限：<input name="opera_period" id="opera_period" value="${comp_detail.opera_period}" class="form_datetime" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "10" /></li>
            
									<li class="list-group-item item_border">注册资本：<input name="reg_capital" id="reg_capital" value="${comp_detail.money}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "20" /></li>

									<li class="list-group-item item_border">注册地址：<input name="reg_address" id="reg_address" value="${comp_detail.reg_address}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" maxlength = "100" /></li>


								</ul>

							</div>
							</form>
							</s:if>
						</div>
					</div>
				</div>
				<!--end 右-->
			</div>


<!-- 添加客户公司信息弹窗 -->
<div class="modal" id="opener6" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 980px;"> 
    <form id="formid" method = 'post' action="addMember.action"   > 
    <input name="cust_id" id="cust_id" type="hidden"  value="${cust_id }"/>       
          <div class="panel panel-default" style="width:100%;">
                    <ul class="list-group">
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                      <h4> 添加客户公司信息</h4>
                       </li> 
                       <li class="list-group-item" >
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span> 公司名称</span>
                        <input name="comp_name" id="comp_name" type="text" maxlength = "30" style="width: 75%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                         <%-- <span style="color: red;margin-right: 4px;float: left;">*</span> --%>
                        <span>公司类型</span>
                          <input name="comp_type" id="comp_type" type="text" maxlength = "30" style="width: 75%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                        <span>营业执照注册号(统一社会信用代码)</span>
                      <input name="license" id="license" type="text" maxlength = "30" style="width: 75%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item" style="border-bottom:0;border-top: dashed 1px #ddd;">
                         
                        <span>法人</span>
                         <input name="legal" id="legal" type="text" maxlength = "20" style="width: 75%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                        
                        <span>税务登记号</span>
                        <input name="taxid" id="taxid" type="text" maxlength = "100" style="width: 90%;border:none;outline: none;">
                       </li>
                         <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                         
                        <span>组织机构代码证</span>
                        <input name="org_code_cert" id="org_code_cert" maxlength = "30"  type="text" style="width: 75%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                         
                        <span>成立日期</span>
                        <input name="reg_date" id="reg_date" maxlength = "10"  value="<%=now_date %>" class="form_datetime" type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                         
                        <span>经营期限</span>
                        <input name="opera_period" id="opera_period" maxlength = "10"  value="<%=now_date %>" class="form_datetime" type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                         <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                        
                        <span>注册资本</span>
                        <input name="reg_capital" id="reg_capital" maxlength = "20" type="number" style="width: 90%;border:none;outline: none;">
                       </li>
                       
                          <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                         
                        <span>注册地址</span>
                        <input name="reg_address" id="reg_address" type="text" maxlength = "100" style="width: 90%;border:none;outline: none;">
                       </li>
                         
                    </ul> 
         <div class="row text-center" style="margin:25px 0;">
            <button style="margin-right: 40px;" data-dismiss="modal"
              class="btn btn-lg btn-default">取消</button>

            <button id="customerCreateBtn"  value="添加" data-dismiss="modal"
              class="btn btn-lg btn-success" onclick="addMember()">保存</button>
          </div>
        </div>  
        </form> 
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
								list_li[j].style.background = 'rgb(245,245,245)';
							}
							this.style.background = '#ace1f1';

						}
					}
				}
			</script>
<script>
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
	
});

function newDate(obj) {
	var newTime = new Date(obj); //毫秒转换时间  
	var year = newTime.getFullYear();
	var mon = newTime.getMonth()+1;  //0~11
	var day = newTime.getDate();
	var newDate = year+'-'+mon+'-'+day;
	return newDate
}
</script>




		</div>
</body>
</html>