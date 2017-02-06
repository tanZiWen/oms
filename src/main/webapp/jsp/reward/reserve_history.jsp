<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>reward_history</title>
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
<script>var n = 1;</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">
window.onload = function() {
	 var a = '<%=request.getAttribute("role_id")%>' ;
	 if(a=='11'){
		 $("#report").show();
	 }else{
		 $("#report").hide();
	 }
}

//报表导出
function export_report(){
var localFilePath = $('#localFilePath').val();
var filename="reservecou.xls";
var batch=$('#give_batch').val();
alert(batch);
//location.href="";
window.open('reward_count_report.action?filename='+filename+'&batch='+batch, '_blank');

}	
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
<!--nav-->
<%@ include file="../header.jsp" %>


<!--主体-->
  		
      <div class="container m0 bod top70" id="zt">
		  	<div class="row">
				<div class="col-md-6 t0b0 ">
					 <ol class="breadcrumb t0b0">
					  <li><a href="http://www.jq22.com/">首页</a></li>
					  <li class="active">预留发放</li>
					</ol>
				</div>
				<div class="col-md-6 t0b0 txtr">
				
				</div>
			</div>
			
            <div class="row top10 mym">
				<!--左-->
			    
<div class="col-md-4 myleft" style="width: 25%;">
    <div class="myleft-n">
       
         <a href="http://www.jq22.com/myhome#" data-toggle="modal" data-target="#exampleModal2">
            <img id="tou" src="image/person.png" class="f imgr20">
         </a>   
        <div class="f imgf20">
            <h4>我的奖金管理</h4>
            
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
    <div class="myleft-n">
		  <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
        <!--  <i class="glyphicon glyphicon-search"></i> 我的奖金查询<br> -->
         
        </div>
 
        <%-- <ul class="list-group">
            <li class="list-group-item">

                <i class="fa fa-user-secret"></i>&nbsp;
                <input type="text" value="请输入客户名称" 
                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
                style="width: 90%;border:none;outline: none;">
            </li>
            <li class="list-group-item">
                <i class="fa fa-user-secret"></i>&nbsp;
                <input type="text" value="请输入电话号码" 
                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
                style="width: 90%;border:none;outline: none;">
            </li>
            <li class="list-group-item">
             <i class="fa fa-user-secret"></i>&nbsp;
             <span class="cust_state">产品类型</span>
             <select style="width: 90px;height:28px;outline: none;">
             <option>直销产品</option>
             <option>已审批</option>
             <option>代销产品</option>
             </select>
            </li>
            <li class="list-group-item">

                <i class="fa fa-user-secret"></i>&nbsp;
                <input type="text" value="请输入奖金发放批次"
                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
                 style="width: 90%;border:none;outline: none;">
            </li>
 
        </ul>

       <a class="btn btn-info top10" href="#" style="width: 100%">
         点击查询
       </a> --%>

      
        

    </div>
    <div class="df"></div>
</div>


			    <!--end 左-->
			  <!--右-->
			   <div class="col-md-8 myright" style="width: 75%;">
			  		<div class="myright-n">
					  <!--    <div class="myNav row" id="report">             
            <a href="#" style="outline: none;"><i class="glyphicon glyphicon-plus"></i> 导出报表</a> 
                            
             </div>  -->
						<div class="row topx myMinh" >
						
						<div class="spjz" style="">
                        
					<div class="panel panel-default" style="width:100%;">
         
            <div class="panel-heading">
              <h3 class="panel-title" style="color:#a52410;position: relative;"><span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>我的奖金(历史)
     
              </h3>             
            </div>
           <div id="report">
              <input type="hidden" value="${give_batch }" id="give_batch"> 
              <table>
              <tr>
             
              <th style="text-align: center;">
						<button style="font-size: 15px;" class="demo_tr"
							onclick="export_report()">导出报表</button>&nbsp;&nbsp;&nbsp;
							</th>
              </tr>
              </table></div>
           <div class="panel-body" style="overflow:auto;">
            <table class="table cust_table">
            <thead >
            <tr class="demo_tr">
            <th>销售ID</th>
            <th>销售姓名</th>
            <th>批次号</th> 
           <!--  <th>类型</th> -->
          <th>预留金额</th>
            <th>预留扣除金额</th> 
            <th>预留发放金额</th>
             <th>日期</th> 
          
            </tr>
            </thead>
            <tbody>

            <!-- <tr class="default" id="cust_list">
            <td>20160501</td>
            <td>常春藤美元母基金</td>
            <td>2313123123</td>
            <td>张淼</td>
            <td>2000000</td>
            <td>20000</td>
            </tr> -->
             <s:if test="#request.status==1 ">
				<s:iterator value="#request.rewardList" var="rewardLi">
            <tr>
            
            <td>${rewardLi.sales_id }</td>
            <td>${rewardLi.sales_name }</td>
            <td>${rewardLi.count_batch }</td>
            <td>${rewardLi.reserve }</td>
            <td>${rewardLi.reserve_kou }</td>
            <td>${rewardLi.reserve_give }</td>
             <td>${rewardLi.create_time }</td> 
            
            </tr>
            	</s:iterator>
			</s:if>
            </tbody>
            </table>
           </div>
        </div>
							
						
						</div>
					</div>
				</div>
			</div>
           <!--end 右-->
    </div>  


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


</div></body></html>
<script>
	function DoCheck()
{	
	var ch=document.getElementsByName("choose");
	 $('input[type=checkbox]').prop('checked', $(allChecked).prop('checked'));
} 
</script>