<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<%@ include file="/jsp/header.jsp"%>
<script src="/OMS/js/org.js"></script>
<script>
	var n = 1;
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
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
});
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->




	<!--主体-->
	<form id="formid" method='post' action="saveOrg.action">
		<input id="org_id" name="org_id" value="${org_detail.org_id}" type="hidden" />
		<input id="state" name="state" value="${org_detail.state}" type="hidden" />
		<div class="container m0 bod top70" id="zt">
			<div class="row">
				<div class="col-md-6 t0b0 ">
					<ol class="breadcrumb t0b0">
						<li><a href="/OMS">首页</a></li>
						<li><a href="org.action">机构客户管理</a></li>
						<li class="active">个人详情</li>
					</ol>
				</div>
				<div class="col-md-6 t0b0 txtr"></div>
			</div>
  <input type="hidden" id="old_org_name"            name="old_org_name"      value="${org_detail.org_name}"     />
  <input type="hidden" id="old_org_type"            name="old_org_type"      value="${org_detail.org_type}"      />
  <input type="hidden" id="old_org_license"         name="old_org_license"   value="${org_detail.org_license}"  />
  <input type="hidden" id="old_org_legal"           name="old_org_legal"     value="${org_detail.org_legal}"    />
  <input type="hidden" id="old_org_code_cert"       name="old_org_code_cert" value="${org_detail.org_code_cert}"/>
  <input type="hidden" id="old_reg_capital"         name="old_reg_capital"   value="${org_detail.reg_capital}"  />
  <input type="hidden" id="old_reg_address"         name="old_reg_address"   value="${org_detail.reg_address}"  />
  <input type="hidden" id="old_state"               name="old_state"         value="${org_detail.state}"        />
  <input type="hidden" id="old_org_members"         name="old_org_members"   value="${org_detail.org_members}"  />
  <input type="hidden" id="old_taxid"               name="old_taxid"         value="${org_detail.taxid}"        />
  <input type="hidden" id="old_reg_date"            name="old_reg_date"      value="${org_detail.reg_date}"     />
  <input type="hidden" id="old_opera_period"        name="old_opera_period"  value="${org_detail.opera_period}" />
  <input type="hidden" id="old_reg_time"            name="old_reg_time"      value="${org_detail.reg_time}"     />

			<div class="row top10 mym">
				<!--左-->

				<div class="col-md-4 myleft" style="width: 25%;">
					<div class="myleft-n">

						<a href="#" data-toggle="modal"
							data-target="#exampleModal2"> <img id="tou"
							src="/OMS/image/person.png" class="f imgr20">
						</a>
						<div class="f imgf20"
							style="width: 60%; height: 90px; line-height: 15px;">
							<h4>${org_detail.org_name}</h4>
							<%-- <p>${org_detail.org_type}</p>
							<p>${org_detail.org_code_cert }</p> --%>
							<p><span style="font-size: 22px;color: #a52410;margin-left: 15px;">${org_detail.state_name }</span></p>


						</div>

						<div class="df"></div>
					</div>
					<div class="df"></div>
					<div class="myleft-n">


						<ul class="list-group">
							<!-- id="list-grop" -->
							<li class="list-group-item">公司名称&nbsp:<input id="org_name"
								name="org_name" value="${org_detail.org_name }"
								type="text"
								style="width: 56%; margin-left: 2%; border: 0; outline: none;">
							</li>
							<li class="list-group-item">公司类型: <input name="org_type" id="org_type"
								value="${org_detail.org_type}" type="text"
								style="width: 58%; margin-left: 2%; border: 0; outline: none;">
								<!--  <select name="" id="">
                <option value="已成交">已成交</option>
                <option value="已成交">已成交</option>
              </select> -->

							</li>

							<li class="list-group-item">组织机构代码&nbsp:<input id="org_code_cert"
								name="org_code_cert" value="${org_detail.org_code_cert }"
								type="text"
								style="width: 56%; margin-left: 2%; border: 0; outline: none;">
							</li>
							<li class="list-group-item">营业执照注册号 （统一社会信用代码）&nbsp:<input
								name="org_license"  type="text" id="org_license"
								value="${org_detail.org_license }"
								style="width: 58%; margin-left: 2%; border: 0; outline: none;">

							</li>
							<li class="list-group-item">法人: <input name="org_legal"  id="org_legal"
								value="${org_detail.org_legal}" type="text"
								style="width: 58%; margin-left: 2%; border: 0; outline: none;">
								<!-- <select name="" id="">
                <option value="nan">男</option>
                <option value="nv">女</option>
              </select> -->
							</li>
							<li class="list-group-item">税务登记号&nbsp:<input id="taxid" name="taxid"
								value="${org_detail.taxid}" type="text"
								style="width: 58%; margin-left: 2%; border: 0; outline: none;">
							</li>
							<li class="list-group-item">注册资本&nbsp:<input
								name="reg_capital" id="reg_capital" value="${org_detail.reg_capital}" type="text"
								style="width: 58%; margin-left: 2%; border: 0; outline: none;"></li>
							<li class="list-group-item">注册地址&nbsp:<input
								value="${org_detail.reg_address}" type="text" name="reg_address"
								id="reg_address"
								style="width: 58%; margin-left: 2%; border: 0; outline: none;">
							</li>
							<li class="list-group-item">经营期限&nbsp:<input
								id="opera_period" name="opera_period" value="${org_detail.opera_period}" type="text"
								style="width: 58%; margin-left: 2%; border: 0; outline: none;"></li>
							<li class="list-group-item">注册时间&nbsp:<input class="form_datetime" id="reg_time"
								value="${org_detail.reg_time}" type="text"
								style="width: 58%; margin-left: 2%; border: 0; outline: none;"></li>




						</ul>


					</div>
					<div class="df"></div>
				</div>

				<!--end 左-->
				<!--右-->
				<div class="col-md-8 myright" style="width: 75%;">
					<div class="myright-n">
						<div class="myNav row">
						<s:if test="#request.role_id==4">
							<a href="javascript:edit()" style="outline: none;"><i
								class="glyphicon glyphicon-floppy-disk"></i> 保存</a>
								<a href="javascript:org_submit()" style="outline: none;"><i
								class="glyphicon glyphicon-floppy-disk"></i> 提交审批</a>
								<a href="javascript:org_reset()" style="outline: none;"><i
								class="glyphicon glyphicon-floppy-disk"></i> 重置</a>
								</s:if>
								<s:if test="#request.role_id==3||#request.role_id==5">
								<a href="#"style="outline: none;" data-toggle="modal"  data-target="#ispass"><i class="glyphicon glyphicon-open-file"></i> 审批</a>
								</s:if>
	

						</div>
						<div class="row topx myMinh" style="">

							<div class="spjz" style="">

								<div class="panel panel-default" style="width: 100%;">

									<div class="panel panel-default" style="width: 100%;">
										<div class="panel-heading">
											<h3 class="panel-title" style="color: #a52410;">
												<span class="glyphicon glyphicon-th" aria-hidden="true"
													style="margin-right: 6px;"></span>机构合伙人
											</h3>
										</div>
										<div class="panel-body">
											<table id="playlistTable" class="table cust_table">
												<thead>
													<tr class="demo_tr">
														<th>合伙人</th>
														<th>配对RM</th>
														<th>占比</th>
														<th>认购金额</th>
													</tr>
												</thead>
												<tbody>
												<s:if test="#request.status1==1 ">
													<s:iterator value="#request.org_detail1" var="list">
														<tr>
															<td style="display:none;">${list.cust_id }</td>
															<td>${list.partner}</td>
															<td>${list.match_rm}</td>
															<td contentEditable="true">${list.proport}</td>
															<td contentEditable="true">${list.sub_amount}</td>
														</tr>
													</s:iterator>
													</s:if>
													<s:elseif test="#request.status1==2" >
												<tr>
													<th colspan="5" style="text-align: center;">${org_detail1 }</th>
												</tr>
											</s:elseif>
												</tbody>
											</table>
										</div>

									</div>

									<!-- 添加拜访记录弹窗 -->

									<!--  -->
									<div class="panel panel-default" style="width: 100%;">
										<div class="panel-heading">
											<h3 class="panel-title" style="color: #a52410;">
												<span class="glyphicon glyphicon-th" aria-hidden="true"
													style="margin-right: 6px;"></span>成交记录

											</h3>
										</div>
										<div class="panel-body">
											<table class="table cust_table">
												<thead>
													<tr class="demo_tr">
														<th>订单号</th>
														<th>日期</th>
														<th>地区</th>
														<th>职级</th>
														<th>销售名</th>
														<!-- <th>配对方</th> -->
														<th>产品名称</th>
														<th>合伙企业</th>
														<th>产品渠道</th>
														<th>机构名称</th>
														<th>下单金额</th>
														<th>产品系数</th>
														<th>标费</th>
														<th>状态</th>
													</tr>
												</thead>
												<tbody>
													<s:iterator value="#request.custOrder" var="lis">
														<tr>
															<td><s:property value="#lis.order_no" /></td>
															<td><s:property value="#lis.create_time" /></td>
															<td><s:property value="#lis.area_name" /></td>
															<%-- <td><s:property value="#lis.sales_name" /></td> --%>
															<td><s:property value="#lis.sales_name" /></td>
															<%-- <td><s:property value="#lis.sales_name" /></td> --%>
															<td><s:property value="#lis.prod_name" /></td>
															<td><s:property value="#lis.part_comp" /></td>
															<td><s:property value="#lis.prod_type_name" /></td>
															<td><s:property value="#lis.org_name" /></td>
															<td><s:property value="#lis.order_amount" /></td>
															<td><s:property value="#lis.prod_diffcoe" /></td>
															<td><s:property value="#lis.acount_fee" /></td>
															<td><s:property value="#lis.order_state_name" /></td>
														</tr>
													</s:iterator>
												</tbody>
											</table>
											<!-- <ul class="pagination pagination-centered">
												<li><a href="#">&laquo;</a></li>
												<li><a href="#">1</a></li>
												<li><a href="#">2</a></li>
												<li><a href="#">3</a></li>
												<li><a href="#">&raquo;</a></li>
											</ul> -->
										</div>
									</div>
									<!--  -->

								</div>
							</div>
						</div>
					</div>
					<!--end 右-->
				</div>


<!--友好提示弹框  -->
  <div id="ispass" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      
        <div style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
         是否审批通过？
        
        </div>

    
          <div class="row text-center" style="margin-bottom: 15px;">
             <!-- <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" ">否</button> 
              <button id="customerCreateBtn" data-dismiss="modal"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" onclick="add_production(4)">是</button>  -->
              
              <input type="button" value="否" style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="org_nopass()" >
              <input type="button" value="是" style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="org_pass()" >
            
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
						$
								.ajax({
									type : "POST",
									url : "myPhotoSave.aspx",
									data : "tx=" + myFrame.window.tx,
									success : function(msg) {
										if (msg != "n") {
											$("#tou").attr('src',
													"tx/" + msg + ".png");
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
		</div>
	</form>
</body>
</html>