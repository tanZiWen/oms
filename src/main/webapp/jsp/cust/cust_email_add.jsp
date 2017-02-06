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
	href="<%=request.getContextPath()%>/css/bootstrap-select.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/pageStyle.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/dropzone.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-tagsinput.css">
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-button.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-select.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dropzone.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-tagsinput.min.js"></script>
<script src="<%=request.getContextPath()%>/js/ueditor-1.4.3.3/ueditor.config.js"></script>
<script src="<%=request.getContextPath()%>/js/ueditor-1.4.3.3/ueditor.all.min.js"></script>
 <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/js/ueditor-1.4.3.3/lang/zh-cn/zh-cn.js"></script>
<script src="/OMS/js/cust.js"></script>
<script type="text/javascript" src="/OMS/js/NavUtil2.js"></script>
<script>var n = 1;</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<style type="text/css">
	#dropz {
	    min-height: 150px;
	    border: 2px solid rgba(0, 0, 0, 0.3);
	    background: white;
	    padding: 20px 20px;
	    cursor: pointer;
	}
</style>
<script type="text/javascript">
	 var ue = UE.getEditor('container');
	 $('.selectpicker').selectpicker({noneSelectedText:'请选择'});
	 ue.ready(function() {
		 ue.setContent('${content}')
	 });
	 function showSujectModal() {
		 $('#addSujectModal').modal('show');
	 }
	 function addConsignee() {
		 $('#addConsigneeModal').modal('show');
	 }
	 function addCC() {
		 $('#addCCModal').modal('show');
	 }
	 var filenames = [];
	 Array.prototype.remove = function(val) {
		 var index = this.indexOf(val);
		 if (index > -1) {
		 	this.splice(index, 1);
		 }
	 };
	 $(function(){
		 var dropz = new Dropzone("#dropz", {
	        url: "/OMS/send_cust_files.action",
	        method: "post",
	        addRemoveLinks: true,
	        maxFiles: 10,
	        maxFilesize: 20*1024*1024,
	        acceptedFiles: ".pdf,.doc,.xlsx,.docx",
	        init: function() {
	            this.on("success", function(file) {
	            	filenames.push(file.name);
	            });
	            this.on("removedfile", function(file) {
	            	filenames.remove(file.name);
	            });
	        }
	    });
	 });
	$('#consigneeNames').tagsinput('items');
	function addSubject() {
		var sub_name = $('#addSujectModal #sub_name').val();
		if(sub_name == "") {
			alert('主题名不能为空!');
			return false;
		}
		var data = {sub_name: sub_name};
		$.ajax({
			url: '/OMS/add_subject.action',
			type: 'post',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				if(data.desc == 1) {
					alert("添加成功!");
					$('#addSujectModal #sub_name').val('');
					window.location.href = "/OMS/send_email_page.action";
				}else {
					alert("添加失败!");
				}
			}
		});
	}
	function selectRM() {
		var rm_name = $('#addCCModal #rm_name').val();
		var data = {rm_name: rm_name};
		$.ajax({
			url: '/OMS/select_cc_rm.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				if(data.desc == "1") {
					var list = data.userList;
					var tbody = "";
					$('#addCCModal #ccbody').empty();
					for(var i = 0; i < list.length; i++) {
						tbody += '<tr class="default">'
							+ '<td>'+list[i].real_name+'</td>'
							+ '<td>'+list[i].is_exist_email+'</td>'
							+ '<td><input name="user_id" id="user_id" type="radio" value="'+list[i].email+','+list[i].real_name+'"/>'
							+ '</td></tr>';
					}
					$('#addCCModal #ccbody').append(tbody);
				}
			}
		});
	}
	function searchCustByName() {
		var cust_name = $('#addConsigneeModal #cust_name').val();
		var data = {cust_name: cust_name};
		$.ajax({
			url: '/OMS/select_email_cust.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				if(data.desc == "1") {
					var list = data.custList;
					var tbody = "";
					$('#addConsigneeModal #custbody').empty();
					for(var i = 0; i < list.length; i++) {
						tbody += '<tr class="default">'
							+ '<td>'+list[i].cust_name+'</td>'
							+ '<td>'+list[i].real_name+'</td>'
							+ '<td>'+list[i].is_exist_email+'</td>'
							+ '<td><input name="cust_id" id="cust_id" type="radio" value="'+list[i].cust_id+','+list[i].cust_name+','+list[i].is_exist_email+'"/>'
							+ '</td></tr>';
					}
					$('#addConsigneeModal #custbody').append(tbody);
				}
			}
		});
	}
	var cust_id;
	var cust_name;
	var is_exist_email;
	var userEmails = [];
	var userNames = [];
	function selectCust() {
		var v = $('#addConsigneeModal #custbody input:radio:checked').val().split(",");
		if(!v) {
			alert("您还未选择收件人!");
			return false;
		}
		cust_id = v[0];
		cust_name = v[1];
		is_exist_email = v[2];
		
		$('#consignee_name').html(''+cust_name);
		$('#addConsigneeModal').modal('hide');
	}
	
	function selectCC() {
		var value = $('#addCCModal #ccbody input:radio:checked').val();
		if(!value) {
			alert("您还未选择抄送人!");
			return false;
		}
		var values = value.split(",");
		userEmails.push(values[0]);
		userNames.push(" "+values[1]);
		$('#cc_name').html(userNames);
		$('#addCCModal').modal('hide');
	}
	
	function validateEmail(val) {
		var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(!reg.test(val)) {
			return false;
		}
		return true;
	}
	function sendEmail() {
		if(is_exist_email == '否') {
			alert("客户邮箱不存在，请联系对应RM！");
			return false;
		}
		var originator = $('#originator').val();
		var subject = $('#subject').val();
		var content = ue.getContent();
		var data = {content: content, subject: subject, originator: originator, cust_id: cust_id, cust_name: cust_name, filenames: filenames, cc_emails: userEmails};
		showLoad();
		$.ajax({
			url: '/OMS/send_cust_email.action',
			type: 'get',
			dataType: 'JSON',
			data: data,
			success: function(data) {
				hideLoad();
				alert(data.info);
				if(data.desc == "1") {
					window.location.href = "/OMS/send_email_page.action";				
				} 
			}
		});
	}
</script>
</head>
<body data-spy="scroll" data-target=".navbar-example">
	<%@ include file="../header.jsp"%>
	  
      <div class="container m0 bod top70" id="zt">
        <div class="row">
	        <div class="col-md-6 t0b0 ">
	           <ol class="breadcrumb t0b0">
	            <li><a href="<%=request.getContextPath() %>/jsp/index.jsp"><i class='fa  fa-home'></i>&nbsp;首页</a></li>
	            <li><a href="/OMS/cust.action">客户邮件管理</a></li>
	            <li class="active">发送邮件</li>
	            <li><input name="cust_id" id="cust_id" type="hidden" value=""/></li>
	          </ol>
	        </div>
        </div>
         <div class="row top10 mym">
         </div>
            <div class="panel panel-default" style="width:80%;margin:0 auto;align:center;border:0;" id='myModalNext'>
            	<div class="modal-body">
	        	   <!-- <form id="formid" method = 'post' action="addCustinfo.action">    -->
                    <ul class="list-group">
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                             发送邮件
                       </li> 
                       <li class="list-group-item">
	                       <span style="color: red;margin-right: 4px;float: left;">*</span>
	                       <span>发件人</span>
	                        <select id="originator" name="originator"  required="required" class='selectpicker' data-size='5' data-live-search="true">
	                      		<s:iterator  value="#request.originatorList" var="originator">
						        	<option value='<s:property value="#originator.mail_user_username"/>'><s:property value="#originator.mail_user_username"/></option>  
					  			</s:iterator>
                      		</select>
                       </li>
					   <li class="list-group-item">
	                       <span style="color: red;margin-right: 4px;float: left;">*</span>
	                       <span>发件主题</span>
	                       <select id="subject" name="subject"  required="required">
	                       		<s:iterator  value="#request.subjectList" var="subject">
							        <option value='<s:property value="#subject.name"/>'><s:property value="#subject.name"/></option>  
						  		</s:iterator>
	                       </select>
	                       <button type="button" class="btn btn-warning btn-xs" onclick="showSujectModal()"><i class="glyphicon glyphicon-plus"></i></button>
                        </li>
                         <li class="list-group-item">
                           	 <span style="color: red;margin-right: 4px;float: left;">*</span>
	                       	 <span>收件人</span>
	                       	 <label id="consignee_name"></label>
  		                     <button type="button" class="btn btn-warning btn-xs" onclick="addConsignee()"><i class="glyphicon glyphicon-plus"></i></button>
                        </li>
                        
                        <li class="list-group-item">
                           	 <span style="color: red;margin-right: 4px;float: left;">*</span>
	                       	 <span>抄送人</span>
	                       	 <label id="cc_name"></label>
  		                     <button type="button" class="btn btn-warning btn-xs" onclick="addCC()"><i class="glyphicon glyphicon-plus"></i></button>
                        </li>
                        
                        <li class="list-group-item">
       					 	<script id="container" name="content" type="text/plain"></script>
       					 </li>
       					 <li class="list-group-item">
       					 	<div class="needsclick dz-clickable" id="dropz">
							  <div class="dz-message needsclick">
							  </div>
							</div>
       					 </li>
                         <li class="list-group-item">
                            <div class="row text-center" style="margin:25px 0;">
                         	<a href="cust_email.action"><button  data-dismiss="modal"
                            class="btn btn-lg btn-default">取消</button></a>
                             <input type="button" value="发送" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="sendEmail()" >
                       		</div>
       					 </li>
                      </ul>
                  <!-- </form> -->
               </div>
	        </div>
     	 </div>
		 <div class="modal" id="addSujectModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 640px;" >
					<div class="modal-content">
						<div class="modal-header">
			                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			                <h4 class="modal-title" id="myModalLabel">新增发件主题</h4>
						</div>
						<div class="modal-body" style="padding-top: 0;">
							<div style="margin-top: 15px" class="row col-md-offset-2">
								<div class="col-md-8">
									<input type="text" id="sub_name" name="sub_name" placeholder="请输入发件主题"
										class="form-control">
								</div>
							</div>
						</div>
						<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">取消</button>
									<button class="btn btn-lg btn-success" data-dismiss="modal"
										onclick="addSubject()">确认</button>
								</div>
							</div>
				</div>
			</div>
		</div>
		<div class="modal" id="addConsigneeModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 640px;">
				<div class="modal-content">
					<div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		                <h4 class="modal-title" id="myModalLabel">新增收件人</h4>
	                </div>
					<div class="modal-body">
						 <li class="list-group-item">
							<div style="margin-top: 15px" class="row col-md-offset-2">
								<div class="col-md-6">
									<input name='cust_name' id='cust_name' type='text' placeholder="请输入客户名称"
										class="form-control" />
								</div>
								<div>
									<button class="btn btn-md btn-success" onclick="searchCustByName()">查找</button>
								</div>
							</div>
						 </li>
						 <li class="list-group-item">
						<table class="table cust_table">
							<thead>
								<tr class="demo_tr">
									<th>客户姓名</th>
									<th>RM姓名</th>
									<th>客户邮箱是否存在</th>
									<th>勾选</th>
								</tr>
							</thead>
							<tbody id="custbody">
							<s:if test="#request.status==1 ">
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
						</li>
					</div>
					<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">取消</button>
									<button class="btn btn-lg btn-success"
										onclick="selectCust()">确认</button>
								</div>
							</div>
				</div>
			</div>
		</div>
		
		<div class="modal" id="addCCModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 640px;">
				<div class="modal-content">
					<div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		                <h4 class="modal-title" id="myModalLabel">新增抄送人</h4>
	                </div>
					<div class="modal-body">
						 <li class="list-group-item">
							<div style="margin-top: 15px" class="row col-md-offset-2">
								<div class="col-md-6">
									<input name='rm_name' id='rm_name' type='text' placeholder="请输入RM名称"
										class="form-control" />
								</div>
								<div>
									<button class="btn btn-md btn-success" onclick="selectRM()">查找</button>
								</div>
							</div>
						 </li>
						 <li class="list-group-item">
						<table class="table cust_table">
							<thead>
								<tr class="demo_tr">
									<th>RM姓名</th>
									<th>RM邮箱是否存在</th>
									<th>勾选</th>
								</tr>
							</thead>
							<tbody id="ccbody">
							<s:if test="#request.status==1 ">
							<s:iterator value="#request.list" var="lis">
								<tr class="default">
										
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
						</li>
					</div>
					<div class="modal-footer">
								<div class="row text-center">
									<button style="margin-right: 40px;" data-dismiss="modal"
										class="btn btn-lg btn-default">取消</button>
									<button class="btn btn-lg btn-success"
										onclick="selectCC()">确认</button>
								</div>
							</div>
				</div>
			</div>
		</div>
</body>
</html>