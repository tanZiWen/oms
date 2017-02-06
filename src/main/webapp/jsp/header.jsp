<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>header</title>
<link href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/css/demo.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/css/prettify.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/css/bootstrap-table.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/toastr.min.css">
<link href="<%=request.getContextPath() %>/css/font-awesome.min.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/css/my.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/css-/bs-is-fun.css" rel="stylesheet" media="screen">
<script src="<%=request.getContextPath() %>/js/bootstrapValidator.min.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap-button.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap-typeahead.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap-table.min.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.oLoader.js"></script>
<script src="<%=request.getContextPath() %>/js/toastr.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-button.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/m.js" charset="UTF-8"></script>
<script>var n = 1;</script>
<script type="text/javascript">
/* $(function(){
	window.location.href = "user.action";
}) */
$(function() {
	$.ajax({
		url : 'user.action', //后台处理程序     
		type : 'post', //数据发送方式     
		dataType : 'json', //接受数据格式        
		data : {
			
		},
		success : function(data) {
		
			 if (data.cust == 1) {
				  $("#z3").show(); 
				 var rewardInfo = data.custList;
					for (var i = 0; i < rewardInfo.length; i++) {
						var reward_info = '<li><a href="/OMS/'
						+rewardInfo[i].func_url
						+'"><i class="fa fa-sitemap"></i> '
						+rewardInfo[i].func_describle
				           +'</a> </li>';
						$("#nz3").append(reward_info);
					}
				 }else{$("#z3").hide();}
			 
			if (data.product == 1) {
				 $("#z5").show(); 
				 var rewardInfo = data.productList;
					for (var i = 0; i < rewardInfo.length; i++) {
						var reward_info = '<li><a href="/OMS/'
							+rewardInfo[i].func_url
							+'"><i class="fa fa-sitemap"></i> '
							+rewardInfo[i].func_describle
					           +'</a> </li>';
						$("#nz5").append(reward_info);
					}
				}else{$("#z5").hide();}
			
			if (data.order == 1) { $("#z2").show(); }else{$("#z2").hide();}
			
			if (data.reward == 1) {
 				$("#z4").show(); 
				var rewardInfo = data.rewardList;
				for (var i = 0; i < rewardInfo.length; i++) {
					var reward_info = '<li><a href="/OMS/'
						+rewardInfo[i].func_url
						+'"><i class="fa fa-sitemap"></i> '
						+rewardInfo[i].func_describle
				           +'</a> </li>';
					$("#nz4").append(reward_info);
				}
				}else{$("#z4").hide();}
			
			if (data.report == 1) { $("#z1").show(); }else{$("#z1").hide();} 
			if (data.diffcoe == 1) { $("#z6").show(); }else{$("#z6").hide();}      
			if (data.custDistribution == 1) { $("#z7").show(); }else{$("#z7").hide();}  
			if (data.custEmail == 1){ $("#z8").show(); }else{$("#z8").hide();}  
			if(data.emailManage == 1){ $("#z9").show();}else{$("#z9").hide()}
			if(data.custCall == 1){ $("#z10").show();}else{$("#z10").hide()}
			if(data.callManage == 1){ $("#z11").show();}else{$("#z11").hide()}
			if(data.recordManage == 1){ $("#z12").show();}else{$("#z12").hide()}
		},
		error : function(result) {
			$("#z1").hide();
			$("#z2").hide();
			$("#z3").hide();
			$("#z4").hide();
			$("#z5").hide();
			$("#z6").hide();
			$("#z7").hide();
			$("#z8").hide();
			$("#z9").hide();
			$("#z10").hide();
			$("#z11").hide();
			$("#z12").hide();
		}
	});
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
function showLoad() {
	$('body').oLoader({wholeWindow: true, effect:'slide', hideAfter: 30000});
}

function hideLoad() {
	$('body').oLoader('hide');
}

(function($) {
    toastr.options.extendedTimeOut = 10000000000;
    toastr.options.closeButton = true;
    toastr.options.positionClass = 'toast-top-center';
})(jQuery);


function toast(msg, clear) {
    if(clear) {
        toastr.clear();
    }
    if(msg.type == 'success') {
        toastr.success(msg.body);
    } else if(msg.type == 'info') {
        toastr.info(msg.body);
    } else if(msg.type == 'warning') {
        toastr.warning(msg.body);
    } else if(msg.type == 'error') {
        toastr.error(msg.body);
    } else {
        toastr.info(msg.body);
    }
}
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
<!--nav-->
<nav class="navbar navbar-inverse navbar-fixed-top" style="background-color: #ddd;">
  <div class="container-fluid">
    <div class="navbar-header">
   
      <a class="navbar-logo" href="<%=request.getContextPath() %>/jsp/cust/cust_list.jsp">
      <img src="<%=request.getContextPath() %>/image/logo.jpg" height="100%" alt="帆茂投资"></a>
     </div>
     <div id="navbar" class="navbar-collapse collapse" >
      
      <ul class="nav navbar-nav navbar-right"style="margin-right: 84px;" >
     
        <li>
                                 
            <%-- <div class="myhome">
				<a href="<%=request.getContextPath() %>/jsp/cust/cust_list.jsp">
				<div class='glyphicon glyphicon-user' style='font-size:32px;line-height:40px;'></div></a>
				<div class="myhome-z bh2">
					<a class="bh" href="<%=request.getContextPath() %>/jsp/cust/cust_list.jsp"><i class="fa fa-home"></i> 个人中心</a>
					<a class="bh" href="<%=request.getContextPath() %>/jsp/cust/cust_list.jsp" onclick="myout()"><i class="fa fa-sign-out"></i> 退出登录</a>
				</div>
			</div> --%>
    
        </li>
      </ul>
    </div> 
    <!--/.nav-collapse --> 
  </div>
</nav>
<!--end nav-->
<!--nav-->
<div class="yn jz container-fluid nav-bgn m0 visible-lg visible-md visible-sm" style="padding:0;" id="menu_wrap">	
<div class="container m0" style="position:relative;"> 
    <a class="nzz" href="#" id="z3"><span class="sort"><i class="fa fa-cog"></i> &nbsp;客户资源管理 <i class="fa fa-angle-down"></i> </span>|</a>
    <a class="nzz" href="/OMS/prodQuery.action" id="z5"><span class="sort"><i class="fa  fa-cube"></i> &nbsp;产品 <i class="fa fa-angle-down"></i></span>|</a> 
    <a href="/OMS/orderList.action" id="z2"><span class="sort">订单 </span>|</a>
    <a class="nzz" href="#" id="z4"><span class="sort "><i class="fa  fa-paper-plane-o"></i> &nbsp;奖金管理 <i class="fa fa-angle-down"></i></span>|</a> 
    <a href="/OMS/report_index.action" id="z1"><span class="sort "><i class="fa  fa-paper-plane-o"></i> &nbsp;报表<i class="fa fa-angle-down"></i></span>|</a> 
	<a href="/OMS/prod_diffcoe.action" id="z6"><span class="sort"> <i class="fa  fa-paper-plane-o"></i> &nbsp;产品系数<i class="fa fa-angle-down"></i></span>|</a> 
	<a href="/OMS/cust_distribution.action" id="z7"><span class="sort"><i class="fa  fa-paper-plane-o"></i> &nbsp;客户分配<i class="fa fa-angle-down"></i></span>|</a> 
	<a href="/OMS/cust_email.action" id="z8"><span class="sort">客户邮件</span>|</a> 
	<a href="/OMS/email_manage.action" id="z9"><span class="sort">邮件管理</span>|</a> 
	<a href="/OMS/cust_call.action" id="z10"><span class="sort">客户外拨</span>|</a> 
	<a href="/OMS/call_manage.action" id="z11"><span class="sort">外拨管理</span>|</a> 
	<a href="/OMS/record_manage.action" id="z12"><span class="sort">录音管理</span></a> 
</div>
    
<div class="container-fluid">
  <div id="n1" class="nav-zi ty" style="display: none;">

    <ul id="nz2" class="nn list-inline container m0" style="display: none;">
         <!--  -->
      </ul>
      <ul id="nz3" class="nn list-inline container m0" style="display: none;">
        <!--   <li><a href="/OMS/cust.action"><i class="fa  fa-futbol-o"></i> 
              个人客户管理</a> </li>
         <li><a href="/OMS/org.action"><i class="fa fa-sitemap"></i> 
             机构客户管理</a> </li> -->
         
      </ul>
      <ul id="nz4" class="nn list-inline container m0" style="display: none;">
         
          <!-- <li><a href="/OMS/selectReward.action"><i class="fa fa-paper-plane"></i> 我的奖金</a> </li>
          <li><a href="/OMS/reward_count_reckon.action"><i class="fa fa-check-square-o"></i> 奖金核算</a> </li>
          <li><a href="/OMS/reward_give.action"><i class="glyphicon glyphicon-open-file"></i>  核对发放</a> </li> -->
         <%--  <li><a href="<%=request.getContextPath() %>/jsp/reward/reward_rules.jsp"><i class="fa  fa-check-square"></i> 核对发放</a> </li> --%>
        
  
         
      </ul>
      <ul id="nz5" class="nn list-inline container m0" style="display: none;">
         <!--  <li><a href="/OMS/gp_select.action"><i class="fa  fa-pencil-square"></i> GP</a> </li>
          <li><a href="/OMS/prodQuery.action"><i class="fa fa-cubes"></i> 产品</a> </li>    -->
      </ul>  
  </div>
</div>
 		   
</div>
<!--end nav-->
</div>
</body>
</html>