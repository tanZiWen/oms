<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>reward_out</title>
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
            <li class="active">奖金</li>
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
            <img id="tou" src="image/person.png" class="f imgr20">
         </a>   
        <div class="f imgf20" style="width:60%;height: 90px;line-height: 15px;">
            <h4>奖金发放</h4>   
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
    <div class="myleft-n">
         	  <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
         <i class="glyphicon glyphicon-search"></i> 奖金发放查询<br>
         
        </div>
 
        <ul class="list-group">
            <li class="list-group-item">

                <i class="fa fa-user-secret"></i>&nbsp;
                地区
                <select name="area" id="area" style="width:70%;height: 30px;outline: none;">
                   <!--  <option value=""></option>
                	<option value="1">上海地区</option>
                	<option value="2">广州地区</option>
                	<option value="3">深圳地区</option>
                	<option value="4">杭州</option> -->
                	<option></option>
                	<s:if test="#request.status2==1 ">
						<s:iterator value="#request.areaList" var="areaLi">
						<option value="${areaLi.dict_name }">${areaLi.dict_name }</option> -->
						</s:iterator>
						</s:if>
						<s:elseif test="#request.status2==2">
							<option colspan="9" style="text-align: center;">${areaList }</option>
					</s:elseif>
                </select>
            </li>
            <li id="pro" class="list-group-item">
                <i class="fa fa-user-secret"></i>&nbsp;
               产品
                <select id="product"  name="product" style="width:70%;height: 30px;outline: none;">
                   <!--  <option value="0"></option>
                	<option value="1">产品1</option>
                	<option value="2">产品2</option>
                	<option value="3">产品3</option> -->
                	<option></option>
                	<s:if test="#request.status1==1 ">
						<s:iterator value="#request.productList" var="productLi">
						<option value="${productLi.prod_name }">${productLi.prod_name }</option> -->
						</s:iterator>
						</s:if>
						<s:elseif test="#request.status1==2">
							<option  >${productList }</option>
					</s:elseif>
                </select>
            </li>
            <li class="list-group-item">
                <i class="fa fa-user-secret"></i>&nbsp;
                <input id="rm" name="rm" type="text" value="请输入销售名" 
                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''"
                style="width: 90%;border:none;outline: none;">
            </li>
           
          
        </ul>

       <a class="btn btn-info top10" id="search" style="width: 100%">
         点击查询
       </a>

     
    </div>
    <div class="df"></div>
</div>

          <!--end 左-->
        <!--右-->
       
        <div class="col-md-8 myright" style="width: 75%;">
            <div class="myright-n">
            <div class="myNav row" id='myNav'>  
              <!--  <button id="save" name="save" style="outline: none;"><i class="glyphicon glyphicon-floppy-disk"></i> 保存</button>   -->  
               <span id='reward_GR' style="cursor:pointer;margin-left: 10px;" ><i class="glyphicon glyphicon-plus"></i>奖金发放-个人 </span> 
               <span id='reward_PER_FIN' style="cursor:pointer;margin-left: 10px;" ><i class="glyphicon glyphicon-plus"></i>发放完成-个人 </span> 
               <span id='reward_TD' style="cursor:pointer;margin-left: 10px;" ><i class="glyphicon glyphicon-plus"></i>奖金发放-团队 </span> 
               <span id='reward_YL' style="cursor:pointer;margin-left: 10px;" ><i class="glyphicon glyphicon-plus"></i>奖金发放-预留 </span> 

             </div>
            <div class="row topx myMinh" >
            
            <div class="spjz">
                        
               <div class="panel panel-default" style="width:100%;">
    <iframe id='pro_frame' name="myFrame" src="reward_count.action" width="100%;"  frameborder="0" scrolling="auto"  style="min-height: 1750px;overflow-x:hidden;"></iframe>
               


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
      <li>Copyright © 2016 </li>
    </ul>
    <ul class="list-inline text-right">
      <li>
     
      </li>
    </ul>
</nav>


<!-- 左边变色特效 -->
  <script>
  window.onload=function(){  
    // iframeadd_LP

    var pro_frame=document.getElementById('pro_frame');
    var reward_GR=document.getElementById('reward_GR');
    var reward_PER_FIN=document.getElementById('reward_PER_FIN');
    var reward_TD=document.getElementById('reward_TD');
    var reward_YL=document.getElementById('reward_YL');

     reward_GR.onclick=function(){
      pro_frame.src="reward_count.action";
      reward_GR.style.color='red';
      reward_PER_FIN.style.color='#333';
      reward_TD.style.color='#333';
      reward_YL.style.color='#333';
     }
     
     reward_PER_FIN.onclick=function() {
    	 pro_frame.src="search_reward_list.action";
         reward_GR.style.color='#333';
         reward_PER_FIN.style.color='red';
         reward_TD.style.color='#333';
         reward_YL.style.color='#333';
     }

     reward_TD.onclick=function(){
      pro_frame.src='tuandui.action';
      reward_TD.style.color='red';
      reward_PER_FIN.style.color='#333';
      reward_GR.style.color='#333';
      reward_YL.style.color='#333';
    }

    reward_YL.onclick=function(){
     pro_frame.src='yuliu.action';
     reward_YL.style.color='red';
     reward_PER_FIN.style.color='#333';
     reward_GR.style.color='#333';
     reward_TD.style.color='#333';
    }
   
  }
   
  </script>

</div></body></html>