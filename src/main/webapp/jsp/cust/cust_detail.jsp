<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
String now_date = df.format(new Date());
%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_detail</title>
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
<script src="<%=request.getContextPath() %>/js/cust.js"></script>
<script>var n = 1;</script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/m.js" charset="UTF-8"></script>

<script type="text/javascript">
	$(function(){
		var cust_sex = "${cust_detail1.cust_sex}";
		var cust_idtype = "${cust_detail1.cust_idtype}";
		var cust_level = "${cust_detail1.cust_level}";
		$("#cust_sex").val(cust_sex);
		$("#cust_idtype").val(cust_idtype);
		$("#cust_level").val(cust_level);
	});
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
           <li><a href="<%=request.getContextPath() %>/jsp/index.jsp"><i class='fa  fa-home'></i>&nbsp;首页</a></li>
           <li><a href="/OMS/cust.action">个人客户管理</a></li>
           <li class="active">个人--详情</li>
          </ol>
        </div>
        <div class="col-md-6 t0b0 txtr">
        
        </div>
      </div>
      <div class="row top10 mym">
        <!--左-->
       <form id="formidcust" method='post' action="cust_edit.action">
      <input name="cust_id" id="cust_id" type="hidden"  value="${cust_detail1.cust_id }"/>    
	  <div class="col-md-4 myleft" style="width: 25%;">
      <div class="myleft-n">
       
         <a href="#" data-toggle="modal" data-target="#exampleModal2">
            <img id="tou" src="/OMS/image/person1.png" class="f imgr20">
         </a>   
       <div class="f imgf20" style="width:60%;height: 90px;line-height: 15px;">
          
            <h4>${cust_detail1.cust_name}
            <span style="font-size: 22px;color: #a52410;margin-left: 15px;">${cust_detail1.state_name}</span></h4>
            <p>${cust_detail1.sex_name}</p>
            <p>${cust_detail1.cust_cell}</p> 
                    
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
    <div class="myleft-n">
        <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
         <i class="fa fa-lightbulb-o"></i> 个人基本资料 <br>
         <input id="state" name="state" value="${cust_detail1.state}"  type="hidden" >
                     <input id="message" name="message" value="0"  type="hidden" >
        </div>
        <ul class="list-group" >
        <!-- id="list-grop" -->
            <li class="list-group-item">客户姓名：<input name="cust_name" id="cust_name" maxlength = "10" value="${cust_detail1.cust_name}" type="text" 
            style="width: 50%;margin-left: 2%;border:none;outline: none;" contentEditable="true" />
            <input id="former_cust_name" name="former_cust_name" value="${cust_detail1.cust_name}" type="hidden">
            </li>
            <li class="list-group-item">联系电话：<input name="cust_cell" id="cust_cell" maxlength = "11" readonly value="${cust_detail1.cust_cell}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" />
            <input id="former_cust_cell" name="former_cust_cell" value="${cust_detail1.cust_cell}" type="hidden">
            </li>
            <li class="list-group-item">证件类型：<select id="cust_idtype" name="cust_idtype" style="width: 40%;margin-left: 2%">
                        	<s:iterator  value="#request.list2" var="idtype">
						        <option value='<s:property value="#idtype.lel_value"/>'><s:property value="#idtype.lel_name"/></option>  
					  		</s:iterator>
                        </select>
            <input id="former_cust_idtype" name="former_cust_idtype" value="${cust_detail1.cust_idtype}" type="hidden">
            </li>
             <li class="list-group-item">证件号：<input name="cust_idnum" id="cust_idnum" maxlength = "30" value="${cust_detail1.cust_idnum}" type="text" 
            style="width: 68%;margin-left: 0%;border:none;outline: none;" />
            <input id="former_cust_idnum" name="former_cust_idnum" value="${cust_detail1.cust_idnum}" type="hidden">
            </li>
            <li class="list-group-item">性别：<select id="cust_sex" name="cust_sex"  required="required">
                       		<s:iterator  value="#request.list1" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                       </select>
            <input id="former_cust_sex" name="former_cust_sex" value="${cust_detail1.cust_sex}" type="hidden">
            </li>
            <li class="list-group-item">生日：<input name="cust_birth" id="cust_birth" value="${cust_detail1.cust_birth}" class="form_datetime " type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" />
            <input id="former_cust_birth" name="former_cust_birth" value="${cust_detail1.cust_birth}" type="hidden">
            </li>
             <li class="list-group-item">级别：<select id="cust_level" name="cust_level" disabled>
                        <s:iterator  value="#request.list0" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                         </select>
            <input id="former_cust_level" name="former_cust_level" value="${cust_detail1.cust_level}" type="hidden">
            </li>
             <li class="list-group-item">销售名：<input name="sales_name" id="sales_name" value="${cust_detail1.sales_name}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" />
            <input id="former_sales_name" name="former_sales_name" value="${cust_detail1.sales_name}" type="hidden">
            </li>
             <li class="list-group-item">录入时间：<input name="cust_reg_time" id="cust_reg_time" readonly value="${cust_detail1.cust_reg_time}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" />
            <input id="former_cust_reg_time" name="former_cust_reg_time" value="${cust_detail1.cust_reg_time}" type="hidden">
            </li>
            <li class="list-group-item"> 城市：<input name="city" id="city" value="${cust_detail1.city}" type="text" 
            style="width: 60%;margin-left: 2%;border:none;outline: none;" contentEditable="true" />
             <input id="former_city" name="former_city" value="${cust_detail1.city}" type="hidden">
            </li>
            <li class="list-group-item"> 电子邮箱：<input name="email" id="email" value="${cust_detail1.email}" type="text" 
            style="width: 68%;margin-left: 0;border:none;outline: none;" contentEditable="true" />
             <input id="former_email" name="former_email" value="${cust_detail1.email}" type="hidden">
            </li>
             <li class="list-group-item"> 微信号：<input name="wechat" id="wechat" value="${cust_detail1.wechat}" type="text" 
            style="width: 60%;margin-left: 2%;border:none;outline: none;" contentEditable="true" />
             <input id="former_wechat" name="former_wechat" value="${cust_detail1.wechat}" type="hidden">
            </li>
             <li class="list-group-item"> QQ：<input name="qq" id="qq" value="${cust_detail1.qq}" type="text" 
            style="width: 60%;margin-left: 2%;border:none;outline: none;" contentEditable="true" />
            <input id="former_qq" name="former_qq" value="${cust_detail1.qq}" type="hidden">
            </li>
            <li class="list-group-item">职业：<input name="profession" id="profession" value="${cust_detail1.profession}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" />
            <input id="former_profession" name="former_profession" value="${cust_detail1.profession}" type="hidden">
            </li>
             <li class="list-group-item">雇主名称：<input name="company" id="company" value="${cust_detail1.company}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" />
            <input id="former_company" name="former_company" value="${cust_detail1.company}" type="hidden">
            </li>
             <li class="list-group-item">地区：<input name="sales_area" id="sales_area" readonly value="${cust_detail1.sales_area}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" />
            <input id="former_sales_area" name="former_sales_area" value="${cust_detail1.sales_area}" type="hidden">
            </li>
             <li class="list-group-item">联系地址：<input name="address" id="address" maxlength = "200" value="${cust_detail1.address}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" />
            <input id="former_address" name="former_address" value="${cust_detail1.address}" type="hidden">
            </li>
             <li class="list-group-item">风险等级：<input name="cust_risk" id="cust_risk" value="${cust_detail1.cust_risk}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true" />&nbsp;&nbsp;&nbsp;
            <input id="former_cust_risk" name="former_cust_risk" value="${cust_detail1.cust_risk}" type="hidden">
            </li>
        </ul> 
    </div>
    <div class="df"></div>
</div>
</form>
          <!--end 左-->
        <!--右-->
        <div class="col-md-8 myright" style="width: 75%;">
            <div class="myright-n">
            <div class="myNav row"> 
            <s:if test="#request.role_id==1||#request.role_id==2||#request.role_id==3">  
               <!-- <a href="javascript:cust_edit()" style="outline: none;"><i class="glyphicon glyphicon-floppy-disk"></i> 保存</a> -->  
               <a href="javascript:cust_submit()"style="outline: none;"><i class="glyphicon glyphicon-open-file"></i> 提交</a> 
               <a href="javascript:cust_reset()"><i class="glyphicon glyphicon-floppy-saved"></i> 重置 </a>
               <a href="#" style="outline: none;" data-toggle="modal"  data-target="#modal_return"><i class="glyphicon glyphicon-plus"></i>添加拜访记录 </a>
            </s:if>
               <s:if test="#request.role_id==4">
              <a href="#" style="outline: none;" data-toggle="modal"  data-target="#kyc_insert"><i class="glyphicon glyphicon-plus"></i>录入KYC</a>
              <a href="#" style="outline: none;" data-toggle="modal"  data-target="#up"><i class="glyphicon glyphicon-plus"></i>上传KYC附件</a>
              <a href="#" style="outline: none;" data-toggle="modal"  data-target="#up"><i class="glyphicon glyphicon-plus"></i>上传风险评级表</a>
              <a href="#" style="outline: none;" data-toggle="modal"  data-target="#up"><i class="glyphicon glyphicon-plus"></i>上传客户资产证明</a>
          </s:if>
          <s:if test="#request.role_id==3">
           <a href="#"style="outline: none;" data-toggle="modal"  data-target="#ispass"><i class="glyphicon glyphicon-open-file"></i> 审批</a>
           </s:if>
             </div>
            <div class="row topx myMinh" style="">
            
            <div class="spjz" style="">
                        
           <div class="panel panel-default" style="width:100%;">
         
            <div class="panel panel-default" style="width:100%;">
           <div class="panel-heading">
              <h3 class="panel-title" style="color:#a52410;"><span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>
              拜访记录</h3>
           </div>
           <div class="panel-body">
           <table class="table cust_table" style="table-layout:fixed;">                     
                        <thead>
                          <tr class="demo_tr">
                            <th>日期</th>
                            <th>陪同人员</th>
                            <th>描述</th>
                            <!-- <th></th> -->
                          </tr>
                        </thead>
                        <tbody>
                        <s:if test="#request.status==1">
                        <s:iterator value="#request.cust_detail" var="lis">
                          <tr>
                            <td>${lis.see_date}</td>
                            <td><s:property value="#lis.see_member" /></td>
                            <td class='cust_desc'><s:property value="#lis.see_desc" />&nbsp;&nbsp;
                              <!-- <button type="button" class="btn btn-default text-right"
                                data-toggle="modal" data-target="#opener"
                                style="height: 30px;">更多</button> -->
                            </td>
                           
                          </tr>
                          </s:iterator>
                          </s:if>
                        </tbody>
                    </table>
          
           </div>
           
        </div>  
<!-- kyc录入提示框 -->
<div id="kyc_insert" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">KYC录入提示</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      <form id="kyc_insert_form_file" action="kycinsert.action" enctype="multipart/form-data" method="post">
        <div style="text-align: left;height:150px;font-size:16px;line-height: 70px;width:100%;">
                      
                     KYC录入：<input type="file" name="file1"><br/>
                     <input name="cust_id5" id="cust_id5" type="hidden"   value="${cust_detail1.cust_id }"/>
        <input type="hidden" id="kycpath" name="kycpath" value=""/>
        
        </div>   
          <div class="row text-center" style="margin-bottom: 15px;">
              
              <button onclick="kyc_mould(); return false;"
            	  style="margin-right: 15px;width:80px;background-color:#6699FF;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              >下载模板</button>
              <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" ">取消</button> 
            <input type="submit" value="提交" id="customerCreateBtn" data-dismiss="modal"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" onclick="kycinsert()">
              
            
          </div>
          </form>
    
    
  </div>
</div>
</div>

        <!--  -->
  <div class="panel panel-default" style="width:100%;">
           <div class="panel-heading">
              <h3 class="panel-title" style="color:#a52410;">
               <span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>成交记录
             
              </h3>
           </div>
           <div class="panel-body">
            <table class="table cust_table" style="table-layout:fixed;">
              <thead>
                <tr class="demo_tr">
                  <th>订单号</th>
                  <th>日期</th>
                  <th>地区</th>
                  <!-- <th>职级</th> -->
                  <th>销售名</th>
                  <!-- <th>配对方</th> -->
                  <th>产品名称</th>
                  <th>合伙企业</th>
                  <th>产品渠道</th>
                  <th>客户姓名</th>
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
                  <td><s:property value="#lis.partner_com_name" /></td>
                  <td><s:property value="#lis.prod_type_name" /></td>
                  <td><s:property value="#lis.cust_name" /></td>
                  <td><s:property value="#lis.order_amount" /></td>
                  <td><s:property value="#lis.prod_diffcoe" /></td>
                  <td><s:property value="#lis.mag_fee" /></td>
                  <td><s:property value="#lis.order_state_name" /></td>
                </tr>
                </s:iterator>
              </tbody>
            </table>
            
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
                             class="btn btn-lg btn-success" onclick="cust_nopass()" >
              <input type="button" value="是" style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="cust_pass()" >
            
          </div>
    
    
  </div>
</div>
</div>

<!--文件上传提示弹框  -->
  <div id="up" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">文件上传提示</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      <form id="form_file" action="load.action" enctype="multipart/form-data" method="post">
        <div style="text-align: left;height:150px;font-size:16px;line-height: 70px;width:100%;">
                      
                                                                       上传文件：<input type="file" id="file1" name="file1"><br/>
                                                                <input type="hidden" id="file1FileName" name="file1FileName" value=""/>
                               
                      <input name="cust_id5" id="cust_id6" type="hidden"   value="${cust_detail1.cust_id }"/>
   
        
        </div>
       

    
          <div class="row text-center" style="margin-bottom: 15px;">
              <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" ">取消</button> 
            <input type="button" value="提交" id="customerCreateBtn" data-dismiss="modal" onclick="upload()"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;">
              
              <!-- <input type="button" value="取消" style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
                             class="btn btn-lg btn-success" >
              <input type="button" value="确定" style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" data-dismiss="modal"
                             class="btn btn-lg btn-success" > -->
            
          </div>
          </form>
    
    
  </div>
</div>
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


<!--拜访记录详情  -->
<div class="modal" id="opener" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 650px;height: 300px;">
                 <!--右-->
        <div class="col-md-8 myright" style="width: 100%;">
            <div class="myright-n">
      
            <div class="row topx myMinh" style="border:0;padding: 0;">
            
            <div class="spjz" style="border:0;">
                  <div class="panel panel-default" style="width:100%;">   
                    <ul class="list-group" style="margin-bottom: 25px;">
                        
                       <li class="list-group-item" style="color:#a52410;background-color: rgb(245,245,245);">
                       <h3 class="panel-title" style="color:#a52410;">
                       <span class="glyphicon glyphicon-plus" aria-hidden="true"  style="margin-right: 6px;"></span>
                                                描述内容</h3>
                       </li>                      
                    </ul> 
                           <span style="vertical-align: top;margin-left: 15px;"></span>
                           <s:property value="#lis.see_desc" />

<!--主体-->
                  <hr>
          <div class="row text-center" style="margin:20px 0;">
      <button style="margin-right: 40px;" data-dismiss="modal" class="btn btn-lg btn-default">关闭</button>
      <!-- <button class="btn btn-lg" data-dismiss="modal" onclick="show_indiv()" style="background-color:#5bc0de; color: #fff;">添加</button> -->
    </div>
  </div>
 
  
          </div>
        </div>
      </div>
           <!--end 右-->
    </div>  
           
  </div>
</div>

<!-- end 弹窗 -->


<!--添加拜访记录弹窗  -->
<div class="modal" id="modal_return" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 650px;height: 300px;">
                 <!--右-->
        <div class="col-md-8 myright" style="width: 100%;">
            <div class="myright-n">
      
            <div class="row topx myMinh" style="border:0;padding: 0;">
            
            <div class="spjz" style="border:0;">
            <form id="formid" method = 'post' action="addSee.action"   >
            <input name="cust_id" id="cust_id" type="hidden"  value="${cust_detail1.cust_id }"/>
                  <div class="panel panel-default" style="width:100%;">   
                    <ul class="list-group" style="margin-bottom: 25px;">
                        
                       <li class="list-group-item" style="color:#a52410;background-color: rgb(245,245,245);">
                       <h3 class="panel-title" style="color:#a52410;">
                       <span class="glyphicon glyphicon-plus" aria-hidden="true"  style="margin-right: 6px;"></span>
                       添加拜访记录</h3>
                       </li> 
                       <li class="list-group-item">
                        
                        <span>日期&nbsp;:</span>
                        <input name="see_date" id="see_date" required="required" value="<%=now_date %>" readonly class="form_datetime " type="text" style="width: 20%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item">
                        
                        <span> 陪同人员&nbsp;:</span>
                        <input name="see_member" id="see_member"  type="text" maxlength = "50" style="width: 75%;border:none;outline: none;">
                         <span style="display: inline-block;height: 100%;">
                      
                       <i style="font-size: 10px;">备注( 陪同人员可添加多人，用逗号分隔 )</i>
                      </span> 
                       </li>
                      
                    </ul> 
                          <span style="vertical-align: top;margin-left: 15px;">描述 </span>
                          <textarea name="textarea" id="see_desc" maxlength = "500" required="required" cols="80" rows="6" style="outline: none;"></textarea>

<!--主体-->
     <div class="modal-footer" style="border:none;">
          <div class="row text-center" style="margin:20px 0;">
      <button style="margin-right: 40px;" data-dismiss="modal" class="btn btn-lg btn-default">取消</button>
      <input type="button" value="添加" class="btn btn-lg" data-dismiss="modal" onclick="addSee()" style="background-color:#5bc0de; color: #fff;"/>
    </div>
  </div>
  
          </div>
            </form>
 
        </div>
      </div>
           <!--end 右-->
    </div>  
           
  </div>
</div>
</div>
<!-- end 弹窗 -->


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

   $(function(){
		$(".form_datetime").datetimepicker({
			 format: 'yyyy-mm-dd',  
	       weekStart: 1,  
	       autoclose: true,  
	       startView: 2,  
	       minView: 2,  
	       forceParse: false,  
	       language: 'zh-CN' ,
	       endDate: Infinity
		});
		
	/* 	$('#datetimepicker_end').datetimepicker({
			     weekStart : 1,
			     todayBtn : 1,
			     autoclose : 1,
			     todayHighlight : 1,
			     minView : 0,
			     forceParse : 1,
			     showMeridian : 0
			  }); */

		
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

<!-- 左边变色特效 -->
  <script>
  window.onload=function(){
     var list_grop=document.getElementById('list-grop');
     var list_li=list_grop.getElementsByTagName('li');

     for(var i=0;i<list_li.length;i++){
      // list_li[i].index=i;
      list_li[i].onclick=function(){
        
        for(var j=0;j<list_li.length;j++){
          list_li[j].style.background='white';
        }
        this.style.background='#ace1f1';

      }
     }
  }
   
  </script>
        
   <script>
   	jQuery(function(){
     //使用class选择器;例如:list对象->tbody->td对象.
    $(".cust_table tbody td").each(function(i){

                //给td设置title属性,并且设置td的完整值.给title属性.
                $(this).attr("title",$(this).text());

      });
    $("td").css("overflow", "hidden").css("text-overflow","ellipsis");
});
   </script>
<script>
function format (num) {
    return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}
</script>

</div></body></html>