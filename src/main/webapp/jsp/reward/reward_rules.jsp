<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>reward_rules</title>
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
					  <li class="active">奖金制度</li>
					</ol>
				</div>
				<div class="col-md-6 t0b0 txtr">
				
				</div>
			</div>
			
            <div class="row top10 mym">
				<!--左-->
			    
<div class="col-md-4 myleft" style="width: 25%;">
    <div class="myleft-n">
       
         <a href="#" data-toggle="modal" data-target="#exampleModal2">
            <img id="tou" src="../../image/person.png" class="f imgr20">
         </a>   
        <div class="f imgf20">
            <h4>奖金制度</h4>
            
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
    <div class="myleft-n">
		  <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
         <i class="glyphicon glyphicon-search"></i> 奖金制度描述<br>
         
        </div>

    </div>
    <div class="df"></div>
</div>


			    <!--end 左-->
			  <!--右-->
			  <div class="col-md-8 myright" style="width: 75%;">
			  		<div class="myright-n">
						<div class="myNav row">							 <a href="#" style="outline: none;"><i class="glyphicon glyphicon-plus"></i>保存</a>
							 <a href="#" style="outline: none;"><i class="glyphicon glyphicon-plus"></i> 新增条款</a>
                           	
						 </div>
						<div class="row topx myMinh" style="">
						
						<div class="spjz" style="">
                             
                              <div class="panel panel-default" style="width:100%;">
         
           <ul class="list-group">
            <li class="list-group-item" style="background-color: rgb(245,245,245);">
               <h3 class="panel-title" style="color:#a52410;"><span class="glyphicon glyphicon-edit" aria-hidden="true"  style="margin-right: 6px;"></span>奖金制度设置
             
              </h3>             
                  
            </li>
           
             <li class="list-group-item">   
             <input type="text" style="border:none;width: 90%;color: #333;outline: none;"  value="1:按业绩分配">
             <a href="#"><span class="glyphicon glyphicon-trash" style="float: right;" title="删除"></span></a>
             </li>
             <li class="list-group-item item_border" >
             <input type="text" style="border:none;width: 90%;color: #333;outline: none;"value="2：按工作内容与性质">
             <a href="#"><span class="glyphicon glyphicon-trash" style="float: right;" title="删除"></span></a>
             </li>
             
        </ul>

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