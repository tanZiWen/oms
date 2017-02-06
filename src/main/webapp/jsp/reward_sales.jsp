<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<!-- saved from url=(0026)http://www.jq22.com/myhome -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>reward_sales</title>
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
<%@ include file="header.jsp" %>


<!--主体-->
  		
      <div class="container m0 bod top70" id="zt">
		  	<div class="row">
				<div class="col-md-6 t0b0 ">
					 <ol class="breadcrumb t0b0">
					  <li><a href="http://www.jq22.com/">首页</a></li>
					  <li class="active">我的奖金</li>
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
            <img id="tou" src="../../image/person.png" class="f imgr20">
         </a>   
        <div class="f imgf20">
            <h4>我的奖金管理</h4>
            
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
    <div class="myleft-n">
		  <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
         <i class="glyphicon glyphicon-search"></i> 我的奖金查询<br>
         
        </div>
 
        <ul class="list-group">
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
       </a>

      
        

    </div>
    <div class="df"></div>
</div>


			    <!--end 左-->
			  <!--右-->
			  <div class="col-md-8 myright" style="width: 75%;">
			  		<div class="myright-n">
						<div class="myNav row">							
							 <a href="#" style="outline: none;"><i class="glyphicon glyphicon-plus"></i> 佣金发放</a>
                           	
						 </div>
						<div class="row topx myMinh" style="">
						
						<div class="spjz" style="">
                        
							 <div class="panel panel-default" style="width:100%;">
         
            <div class="panel-heading">
              <h3 class="panel-title" style="color:#a52410;position: relative;"><span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>奖金列表
     
              </h3>             
            </div>
           <div class="panel-body">
            
         <p style="float: left;">本次编号：20160515</p> 
<!--  -->

<div id="carousel-example-generic" class="carousel slide" data-interval="false" data-ride="carousel" style="width: 100%;height: 40px;position: relative;clear: both;">


 <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox" style="width: 100%;height: 100%;position: absolute;left: 0;">
    <div class="item active" style="width: 90%;height: 100%;margin:0 auto;overflow: hidden;">
         <div style="text-align:center;margin-top: 3px;">

              <a href="<%=request.getContextPath() %>/jsp/reward/reward_history.jsp" class="btn btn-danger" style="display: inline-block;">2015010101</a>
              <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015040106</button>
              <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015070101</button>
              <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015100106</button>
              <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015100106</button>
               <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015100106</button>
       </div> 

    </div>
    <div class="item" style="width: 90%;height: 100%;margin:0 auto;overflow: hidden;">
      <div style="text-align:center;clear: both;margin-top: 3px;">

              <a href="<%=request.getContextPath() %>/jsp/reward/reward_history.jsp" class="btn btn-danger" style="display: inline-block;">2015010101</a>
              <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015040101</button>
              <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015070101</button>
              <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015100101</button>
              <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015100101</button>
               <button type="button" class="btn btn-danger" data-toggle="modal"  data-target="#myModal">2015100101</button>
       </div> 
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev" style="background-image: none;">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true" style="color: #333;"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next" style="background-image: none;color: #333;">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> 
     <span class="sr-only">Next</span> 
  </a> 
</div>
</div>

           <div style="clear: both;">
            <table class="table cust_table">
            <thead >
            <tr class="demo_tr">
            <th><input type="checkbox" name="allChecked" id="allChecked"  onclick="DoCheck()"/>全选</th>
            <th >批次</th> 
            <th>产品名称</th>
            <th>订单号</th>
            <th>客户</th>
            <th>订单金额</th>
            <th>可发放奖金</th>
          
            </tr>
            </thead>
            <tbody>

            <tr class="default" id="cust_list">
            <td><input type="checkbox"></td>
            
            <td>20160501</td>
            <td>常春藤美元母基金</td>
            <td>2313123123</td>
            <td>张淼</td>
            <td>2000000</td>
            <td>20000</td>
            </tr>
            </tbody>
            </table>
            </div>
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