<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GP_detail</title>
<meta name="description" content="帆茂投资">
<meta name="keywords" content="帆茂投资">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<!-- Bootstrap -->
<link href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/css/font-awesome.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
<!--[if lt IE 9]>
 	<script src="<%=request.getContextPath() %>/js/respond.min.js"></script> 
	<script src="<%=request.getContextPath() %>/js/html5shiv.min.js"></script>    
<![endif]-->
<link href="<%=request.getContextPath() %>/css/my.css" rel="stylesheet" media="screen">
<script src="<%=request.getContextPath() %>/js/hm.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<script src="/OMS/js/gp.js"></script>
<script>var n = 1;</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/m.js" charset="UTF-8"></script>
<style type="text/css">
.table > tbody > tr:hover > td,
.table > tbody > tr:hover > th {
	background-color: #f5f5f5;
	cursor:pointer
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
</style>
</head>

<body data-spy="scroll" data-target=".navbar-example">
<!--nav-->
<%@ include file="../header.jsp" %>


<!--主体-->
  		
      <div class="container m0 bod top70" id="zt">
		  	<div class="row">
				<div class="col-md-6 t0b0 ">
					 <ol class="breadcrumb t0b0">
					  <li><a href="/OMS">首页</a></li>
					  <li ><a href="gp_select.action">GP管理</a></li>
					  <li class="active">详情</li>
					</ol>
				</div>
				<div class="col-md-6 t0b0 txtr">
				
				</div>
			</div>
		<form id="formid" method = 'post' action="/OMS/edit.action"   >	
            <div class="row top10 mym">
				<!--左-->
		<input id="gp_id" name="gp_id" type="hidden" value="${gp_detail.gp_id }" />    
<div class="col-md-4 myleft" style="width: 25%;">
    <div class="myleft-n">
       
         <a href="http://www.jq22.com/myhome#" data-toggle="modal" data-target="#exampleModal2">
            <img id="tou" src="/OMS/image/person.png" class="f imgr20">
         </a>   
        <div class="f imgf20">
            <h4>${gp_detail.gp_name }</h4>
            <h4><span style="font-size: 22px;color: #a52410;margin-left: 15px;">${gp_detail.status_name }</span></h4>
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
   
    <div class="myleft-n">
		  <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
         <input name="gp_remark" style="border: none;outline: none;" class="glyphicon glyphicon-search" value="${gp_detail.gp_remark }" />  <br>
         
        </div>
    </div>
    <div class="df"></div>
</div>

			    <!--end 左-->
			  <!--右-->
			  <div class="col-md-8 myright" style="width: 75%;">
			  		<div class="myright-n">
			  		<div class="myNav row">	
			  		<s:if test="#request.role_id==6">
							 <a href="javascript:edit()" style="outline: none;"><i class="glyphicon glyphicon-plus"></i> 保存</a>
							 <a href="javascript:gp_submit()" style="outline: none;"><i class="glyphicon glyphicon-plus"></i> 提交审批</a>
						 </s:if>
						 <s:if test="#request.role_id==7">
						 <a href="#"style="outline: none;" data-toggle="modal"  data-target="#gp_ispass">
						 <i class="glyphicon glyphicon-plus"></i> 审批</a>
						</s:if>
						<a href="javascript:gp_reset()" style="outline: none;"><i class="glyphicon glyphicon-plus"></i> 重置</a>
						</div>
						<div class="row topx myMinh" style="">
						
						<div class="spjz" style="">
                    <!-- 2 -->
        <div class="panel panel-default" style="width:100%;">
                     <input id="status" name="status" value="${gp_detail.status }"  type="hidden" >
                     <input id="message" name="message" value="0"  type="hidden" >
            
                    <ul class="list-group">
                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       GP基本资料
                       </li> 
                       <li class="list-group-item" style="border-bottom: 1px dashed #ddd;">
                         
                        <span>GP名称&nbsp:</span>
                        <input id="gp_name" name="gp_name" type="text" value="${gp_detail.gp_name }" style="width: 90%;border:none;outline: none;">
                        <input id="old_gp_name" name="old_gp_name" type="hidden" value="${gp_detail.gp_name }" >
                       </li>
                       <li class="list-group-item" style="border-top: 1px dashed #ddd;">
                         
                        <span>营业执照号&nbsp:</span>
                        <input id="bus_license" name="bus_license" value="${gp_detail.bus_license }"  type="text" style="width: 85%;border:none;outline: none;">
                         <input id="old_bus_license" name="old_bus_license" value="${gp_detail.bus_license }"  type="hidden" >
                       </li>
                       <li class="list-group-item" style="border-top: 1px dashed #ddd;">
                         
                        <span>法定代表人&nbsp:</span>
                        <input id="legal_resp" name="legal_resp" value="${gp_detail.legal_resp }"  type="text" style="width: 85%;border:none;outline: none;">
                        <input id="old_legal_resp" name="old_legal_resp" value="${gp_detail.legal_resp }"  type="hidden" >
                       </li>
                        <li class="list-group-item" style="border-top: 1px dashed #ddd;">
                         
                        <span>基金业协会备案号&nbsp:</span>
                        <input id="fund_no" name="fund_no" value="${gp_detail.fund_no }"  type="text" style="width: 85%;border:none;outline: none;">
                        <input id="old_fund_no" name="old_fund_no" value="${gp_detail.fund_no }"  type="hidden" >
                       </li>
                        <li class="list-group-item" style="border-top: 1px dashed #ddd;">
                        
                        <span>开户行&nbsp:</span>
                        <input id="open_bank" name="open_bank" value="${gp_detail.open_bank }"  type="text" style="width: 90%;border:none;outline: none;">
                        <input id="old_open_bank" name="old_open_bank" value="${gp_detail.open_bank }"  type="hidden" >
                       </li>
                         <li class="list-group-item" style="border-top: 1px dashed #ddd;">
                         
                        <span>账号&nbsp:</span>
                        <input id="bank_account" name="bank_account" value="${gp_detail.bank_account }"  type="text" style="width: 90%;border:none;outline: none;">
                        <input id="old_bank_account" name="old_bank_account" value="${gp_detail.bank_account }"  type="hidden" >
                       </li>
                         <li class="list-group-item" style="border-top: 1px dashed #ddd;">
                        
                        <span>注册地址&nbsp:</span>
                        <input id="regit_addr" name="regit_addr" value="${gp_detail.regit_addr }"  type="text" style="width: 90%;border:none;outline: none;">
                        <input id="old_regit_addr" name="old_regit_addr" value="${gp_detail.regit_addr }"  type="hidden" >
                       <%-- </li>
                          <li class="list-group-item" style="border-top: 1px dashed #ddd;">
                         
                        <span>管理的合伙企业&nbsp:</span>
                        <input id="lp" name="lp" value="${gp_detail.lp }"  type="text" style="width: 85%;border:none;outline: none;">
                        <input id="ol_lp" name="old_lp" value="${gp_detail.lp }"  type="hidden" >
                       </li> --%>
              
                          <li class="list-group-item" style="border-top: 1px dashed #ddd;">
                         
                        <span>管理方&nbsp:</span>
                        <input id="gp_dept" name="gp_dept" value="${gp_detail.gp_dept }"  type="text" style="width: 90%;border:none;outline: none;">
                        <input id="old_gp_dept" name="old_gp_dept" value="${gp_detail.gp_dept }"  type="hidden" >
                       </li>

        
                     
                    </ul> 
        </div>
             
		
			<!-- end	 -->	
						</div>
					</div>
				</div>
			</div>
           <!--end 右-->
    </div>  
</form>

<!--end主体-->
<!--友好提示弹框  -->
  <div id="gp_ispass" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
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
                             class="btn btn-lg btn-success" onclick="gp_nopass()" >
              <input type="button" value="是" style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="gp_pass()" >
            
          </div>
    
    
  </div>
</div>
</div>

<!--end主体-->

<!--底部-->
<nav class="foot navbar-inverse navbar-fixed-bottom">
	<ul class="list-inline">
      <li class="footer-ss">
      	<a href="javascript:void(0)" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content=" " data-original-title="" title="">更多 &nbsp;<i class="fa fa-angle-down"></i></a></li>
      <li class="footer-ss">在线反馈</li>
      <li class="footer-ss">帮助中心</li>
      <li>Copyright © 2016 帆茂</li>
    </ul>
    <ul class="list-inline text-right">
   	 	<li>
     
    	</li>
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
var flag=0;
function add_member(flag){
	if (flag==2){
		$("#add_member").show();
	}
	else if(flag==1){
		$("#add_member").hide();
	}
	
	else if (flag==3){
		$("#member").show();
	}
	else if(flag==4){
		$("#family_list").show();
	}
}
function family_detail(){
	window.location.href="GP_detail.jsp"; 
}

</script>



</div></body></html>