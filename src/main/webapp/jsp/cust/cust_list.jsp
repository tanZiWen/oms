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
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-button.js"></script>

<script src="/OMS/js/cust.js"></script>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
$(function(){
	statusVal=0;
	$('.buttonStatus').bind("click", function() {
		statusVal = $(this).val();
		cust_search(1);
	});
	pagequery();

});
	function query(totalNum,PageNum,PageSize){
		NavUtil.PageSize = PageSize;
		NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
		NavUtil.bindPageEvent(loadData);
	}
function loadData(){
	
	//分页2
	
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=NavUtil.PageNum;
	pagequery();
		
}
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->
	<%@ include file="../header.jsp"%>

        <input type="hidden" name="totalNum" id="totalNum" value="${totalNum}" />
		<input type="hidden" name="PageNum1" id="PageNum" value="${PageNum}" />
		<input type="hidden" name="PageSize" id="PageSize" value="${PageSize}" />
	<!--主体-->

	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="<%=request.getContextPath() %>/jsp/index.jsp">
                     <i class='fa  fa-home'></i>&nbsp;首页</a></li>
                    <li class="active">个人客户管理</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->

			<div class="col-md-4 myleft" style="width: 25%;">
				<div class="myleft-n">

					<a href="/OMS/cust.action" data-toggle="modal"
						data-target="#exampleModal2"> <img id="tou"
						src="<%=request.getContextPath() %>/image/person.png" class="f imgr20">
					</a>
					<div class="f imgf20">
						<h4>个人客户管理</h4>

					</div>

					<div class="df"></div>
				</div>
				<div class="df"></div>
				<div class="myleft-n">
					<div class="alert alert-warning top20" role="alert"
						style="background-color: #fefcee; padding-top: 7px; padding-bottom: 7px">
						<i class="glyphicon glyphicon-search"></i> 客户信息查询<br>

					</div>
					<div id="state"  data-toggle="buttons-radio" class="btn-group" style="margin-bottom: 10px;width: 100%;" >
							<button style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="0" class="buttonStatus btn btn-sm btn-light-info active"> 全部</button>
							<button style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="1" class="buttonStatus btn btn-sm btn-light-info"> 已保存</button>
							<button style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="2" class="buttonStatus btn btn-sm btn-light-info"> 待审批</button>
							<button style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="3" class="buttonStatus btn btn-sm btn-light-info"> 已审批</button>
							<button style="width: 20%;float: left;text-align: center;padding-left:0;padding-right:0;" value="4" class="buttonStatus btn btn-sm btn-light-info"> 已拒绝</button>
						</div>
					        <ul class="list-group">
					            <li class="list-group-item">
					
					                <i class="fa-home"></i>&nbsp;
					                <input id="cust_name" type="text" value="请输入客户名称" 
					                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
					                style="width: 90%;border:none;outline: none;">
					            </li>
					            <li class="list-group-item">
					                <i class="fa-home"></i>&nbsp;
					                <input id="cust_cell" type="text" value="请输入电话号码" 
					                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
					                style="width: 90%;border:none;outline: none;">
					            </li>
					         <s:if test="#request.role_id==2||#request.role_id==3||#request.role_id==4||#request.role_id==5"> 
					          <li class="list-group-item">
					                <i class="fa-home"></i>&nbsp;
					                <input id="sales_name" type="text" value="请输入销售名" 
					                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
					                style="width: 90%;border:none;outline: none;">
					            </li>
					         </s:if>   
					            <li class="list-group-item">
					
					         <i class="fa-home"></i>&nbsp;
					         <span class="cust_state">级别</span>
					         <select id="cust_level" onblur="if(this.value=='')this.value=this.defaultValue" style='height:28px;'>
					         <option value="" class="active">-请选择-</option>
					         <option value="1">已见面</option>
					         <option value="2">已成交</option></select>
					            </li>
					          
					        </ul>
					
					       <a class="btn btn-info top10" href="javascript:cust_search(1)" style="width: 100%">
					         点击查询
					       </a>

				</div>
				<div class="df"></div>
			</div>
			<!-- 新增 -->
			<div class="modal" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 980px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" data-dismiss="modal" aria-hidden="true"
								class="close">×</button>
							<div class="row">
								<div class="col-md-3">
									<h4 class="modal-title">客户资料</h4>
								</div>
							</div>
						</div>
						<div class="modal-body" style="padding-top: 0;">
							<div style="margin-top: 15px" class="row">
								<div class="col-md-2 text-right text-muted small">电话号</div>
								<div class="col-md-4">
									<input type="text" id="birthday" name="birthday" placeholder=""
										class="form-control">
								</div>
								<div class="col-md-3">
									<button type="button">查找</button>
								</div>
							</div>
							<hr>
							<div id="customerCreateChangeBelongUserRow"
								style="margin-top: 5px; displap: none;" class="row">
								<div id="customerCreateChangeBelongUserCol"
									class="col-md-12 text-right text-danger"></div>
							</div>
							<div style="margin-top: 15px" class="row">
								<div class="col-md-1 text-right text-muted small">*&nbsp;姓名</div>
								<div class="col-md-2">
									<input type="text" id="name" name="name" placeholder=""
										class="holo">
								</div>
								<div class="col-md-1 text-right text-muted small">*&nbsp;性别</div>
								<div class="col-md-2">
									<select><option>男</option>
										<option>女</option></select>
								</div>
								<div class="col-md-1 text-right text-muted small">生日</div>
								<div class="col-md-2">
									<input type="text" id="birthday" name="birthday"
										placeholder="" class="form-control">
								</div>
								<div class="col-md-1 text-right text-muted small">*&nbsp;手机号</div>
								<div class="col-md-2 input-append">
									<input type="text" id="telNo" name="telNo" placeholder=""
										class="holo required mobile">
								</div>
							</div>

							<div style="margin-top: 15px" class="row">
								<div class="col-md-1 text-right text-muted small">证件类型</div>
								<div class="col-md-2">
									<select>
										<option>身份证</option>
										<option>护照</option>
										<option>港澳通行证</option>
									</select>
								</div>
								<div class="col-md-1 text-right text-muted small">证件号</div>
								<div class="col-md-2 input-append">
									<input type="text" id="telNo" name="telNo" placeholder=""
										class="holo required mobile">
								</div>
								<div class="col-md-1 text-right text-muted small">城市</div>
								<div class="col-md-2">
									<input type="text" id="city" name="city" placeholder=""
										class="holo">
								</div>

								<div class="col-md-1 text-right text-muted small">级别</div>
								<div class="col-md-2">
									<div data-toggle="buttons-radio" id="area" name="area"
										class="btn-group">
									  <select>
					                    <option>已见面</option>
				                    	<option>已成交</option>
					                  </select>
									</div>
								</div>
							</div>

							<div style="margin-top: 15px" class="row">
								<div class="col-md-1 text-right text-muted small">电子邮箱</div>
								<div class="col-md-2">
									<input type="text" id="email" name="email" placeholder=""
										class="holo email">
								</div>
								<div class="col-md-1 text-right text-muted small">微信号</div>
								<div class="col-md-2">
									<input type="text" id="wechatNo" name="wechatNo"
										placeholder="" class="holo">
								</div>
								<div class="col-md-1 text-right text-muted small">QQ</div>
								<div class="col-md-2">
									<input type="text" id="qqNo" name="qqNo" placeholder=""
										class="holo digits">
								</div>
								<div class="col-md-1 text-right text-muted small">地址</div>
								<div class="col-md-2">
									<input type="text" id="homepage" name="homepage"
										placeholder="" class="holo url">
								</div>
							</div>

							<div style="margin-top: 15px" class="row">
								<div class="col-md-1 text-right text-muted small">雇主名称</div>
								<div class="col-md-2">
									<input type="text" id="qqNo" name="qqNo" placeholder=""
										class="holo digits">
								</div>
								<div class="col-md-1 text-right text-muted small">职业</div>
								<div class="col-md-2">
									<input type="text" id="homepage" name="homepage"
										placeholder="" class="holo url">
								</div>

							</div>
						</div>
						<!--  -->
						<div class="modal-header">
							<div class="row">
								<h4 style="text-align: left;" class="col-md-11">*&nbsp;拜访记录</h4>
							</div>
						</div>
						<div class="modal-body" style="padding-top: 0;">
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-1 text-right text-muted small">日期</div>
								<div class="col-md-3">
									<input type="text" class="holo" placeholder="">
								</div>
								<div class="col-md-1 text-right text-muted small">陪同人员</div>
								<div class="col-md-3">
									<input type="text" class="holo" placeholder="">
								</div>
								<div class="col-md-1 text-right text-muted small">备注</div>
								<div class="col-md-3 text-right text-muted small">（陪同人员可添加多人，用逗号分隔）</div>
							</div>

							<div class="row" style="margin-top: 15px;">
								<div class="col-md-1 text-right text-muted small">描述</div>
								<div class="col-md-8">
									<textarea name="textarea" cols="100" rows="4"></textarea>
								</div>
							</div>
						</div>
						<!-- <hr> -->
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12" style="text-align: right;">
								<button type="button" data-toggle="modal"
									data-target="#myModal6" style="height: 30px;">公司</button>
								<button type="button" data-toggle="modal"
									data-target="#myModal8" style="height: 30px;">家族</button>
							</div>
						</div>


						<div class="modal-footer">
							<div class="row text-center">
								<button style="margin-right: 40px;" data-dismiss="modal"
									class="btn btn-lg btn-default">取消</button>
								<button class="btn btn-lg btn-success" data-dismiss="modal"
									onclick="show_indiv()">提交审批</button>
							</div>
						</div>

					</div>

				</div>
			</div>
			<!-- 新增end -->

			<!--end 左-->
			<!--右-->
			<div class="col-md-8 myright" style="width: 75%;">
				<div class="myright-n">
					<div class="myNav row">
					<s:if test="#request.role_id==1||#request.role_id==2||#request.role_id==3">
						<a href="javascript:skipadd()" style="outline: none;"><i class="glyphicon glyphicon-plus"></i>
							添加</a> 
					</s:if>
					<s:if test="#request.role_id==1||#request.role_id==2||#request.role_id==3||#request.role_id==4||#request.role_id==5">
							<a href="javascript:cust_report()"><i class="glyphicon glyphicon-list-alt"></i>
							导出报表</a>
					</s:if>

					</div>
					<div class="row topx myMinh" style="">

						<div class="spjz" style="">

							<div class="panel panel-default" style="width: 100%;">

								<div class="panel-heading">
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>客户资源列表
				<!--  <div data-toggle="buttons-radio" class="btn-group" style="position: absolute;right: 20px;top:-6px;">
							<button value="ALL" class="btn btn-sm btn-light-info"> 全部</button>
							<button value="SI" class="btn btn-sm btn-light-info active"> 已保存</button>
							<button value="PE" class="btn btn-sm btn-light-info"> 待审核</button>
							<button value="PE" class="btn btn-sm btn-light-info"> 已审核</button>
						</div> -->
									</h3>
								</div>
								<div class="panel-body">

									<table class="table cust_table">
										<thead>
											<tr class="demo_tr">
												<th>地区</th>
												<th>客户姓名</th>
												<th>性别</th>
												<th>联系电话</th>
												<th>证件号</th>
												<th>销售名</th>
												<!-- <th>录入时间</th> -->
												<th>级别</th>
												<th>状态</th>
												<th></th>
												<th></th>
												<th></th>
											</tr>
										</thead>
										<tbody id="tbody">
										<s:if test="#request.status==1 ">
									<!-- <tr class="default" onclick="cust_detail(${lis.cust_id})"> -->
										<s:iterator value="#request.list" var="lis">
											<tr class="default">
													<td>${lis.dist_name}</td>
													<td><s:property value="#lis.cust_name" /></td>
													<td><s:property value="#lis.sex_name" /></td>
													<td><s:property value="#lis.cust_cell" /></td>
                                                    <td>${lis.cust_idnum}</td>
													<td>${lis.sales_name}</td>
													<%-- <td><s:property value="#lis.cust_reg_time" /></td> --%>
													<td><s:property value="#lis.level_name" /></td>
													<td><s:property value="#lis.state_name" /></td>
												    <td><a href="javascript:company_detail('${lis.cust_id}','${lis.cust_name}','${lis.comp_id }')">公司</a></td>
												    <td><a href="javascript:famliy_detail('${lis.cust_id}','${lis.cust_name}')">家族</a></td>
												    <td><a href="javascript:cust_detail(${lis.cust_id})">详情</a></td>
												 </tr>
											</s:iterator>
											</s:if>
											<s:elseif test="#request.status==2">
												<tr>
													<th colspan="5" style="text-align: center;">${cust_detail }</th>
												</tr>
											</s:elseif>
										</tbody>
									</table>
									<div class="pagin">
										<div class="message">
											共<input id = "pagenum" value = "${totalNum}" readOnly="true" style=" width: 10%;border:none;outline: none;">条记录
										</div>
										
									</div>
									<div id="page1" class="page"></div>
									<!-- <ul class="pagination pagination-centered">
										<li><a href="#">&laquo;</a></li>
										<li><a href="#">1</a></li>
										<li><a href="#">2</a></li>
										<li><a href="#">3</a></li>
										<li><a href="#">&raquo;</a></li>
									</ul> -->
								</div>
								</div>
							</div>


						</div>
					</div>
				</div>
			</div>
			<!--end 右-->
		</div>
<input id ="custname" name="custname" type="hidden" value='' />


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
				<li>Copyright © 2016 帆茂</li>
			</ul>
			<ul class="list-inline text-right">
				<li></li>
			</ul>
		</nav>

		<script>
    var _hmt = _hmt || [];
    (function () {
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
            type: "POST", url: "myPhotoSave.aspx", data: "tx=" + myFrame.window.tx, success: function (msg) {
                if (msg != "n") {
                    $("#tou").attr('src', "tx/" + msg + ".png");
                }
            }
        });

    }
   function clockms(id) {
       var yz = $.ajax({
           type: 'post',
           url: 'mess.aspx',
           data: {id:id},
           cache: false,
           dataType: 'text',
           success: function (data) {
              
           },
           error: function () { }
       });
   }
   </script>
   <script type="text/javascript">
   function cust_detail(cust_id){
	   
	   location.href="cust_detail.action?cust_id="+cust_id+"&flag=1";
	   
	   } 
   </script>
   <script type="text/javascript">
   function famliy_detail(cust_id,cust_name){
	   var msg = escape(escape(cust_name));
	   location.href="cust_detail.action?cust_id="+cust_id + "&cust_name=" +msg+"&flag=2";
	   
	   }
   </script>
   <script type="text/javascript">
   function company_detail(cust_id,cust_name,comp_id){
   var msg = escape(escape(cust_name));
   location.href="cust_detail.action?cust_id="+cust_id + "&comp_id="+comp_id+"&cust_name=" +msg+"&flag=3";
   }
</script>





	</div>
</body>
</html>