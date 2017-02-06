<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
String now_date = df.format(new Date());
%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_add</title>
<meta name="description" content="帆茂投资">
<meta name="keywords" content="帆茂投资">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<!-- Bootstrap -->
<link href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="<%=request.getContextPath() %>/css/font-awesome.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/bs-is-fun.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
<!--[if lt IE 9]>
  <script src="<%=request.getContextPath() %>/js/respond.min.js"></script> 
  <script src="<%=request.getContextPath() %>/js/html5shiv.min.js"></script>    
<![endif]-->
<link href="<%=request.getContextPath() %>/css/my.css" rel="stylesheet" media="screen">
<script src="<%=request.getContextPath() %>/js/hm.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/brush.js"></script>
<script src="/OMS/js/cust.js"></script>
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
            <li><a href="<%=request.getContextPath() %>/jsp/index.jsp"><i class='fa  fa-home'></i>&nbsp;首页</a></li>
            <li><a href="/OMS/cust.action">个人客户管理</a></li>
            <li class="active">个人客户信息添加</li>
            <li><input name="cust_id" id="cust_id" type="hidden" value=""/></li>
            
          </ol>
        </div>
        <div class="col-md-6 t0b0 txtr">
        
        </div>
       
      </div>
      
            <div class="row top10 mym">
    
           <div class="col-md-8 myright" style="width: 100%;margin:0 auto;padding-bottom: 30px;">
            <!-- 1 -->
            <div class="myright-n">
            <div class="myNav row" style="border-bottom: 1px solid #a52410;">             
               
                <h4 style="color: #a52410;">个人客户信息添加</h4>
          
             </div>
     
      </div>  
        <!-- 2 -->
        
        <div class="panel panel-default" style="width:98%;margin:0 auto;align:center;border:0;" id='myModalNext'>
                     
         
            <div class="modal-body">
            <div class="container-fluid">
              <div class="carousel slide" data-ride="carousel" data-interval="false" data-wrap="false">

                <!-- Wrapper for slides -->
          <div class="carousel-inner" role="listbox" style="position: relative;padding-bottom: 0px;">

         <div class="item active">
           <div data-toggle="buttons-radio" id="cust_type" name="cust_type" class="btn-group" style="float: left;margin-bottom: 30px;">
                           <div class="panel panel-default" style="width:100%;">
                                        
                   <div class="report_search">
                       <div class="report_label">
                         <label>
                                            按电话号码查询
                           </label>
                       </div>
                    <div class="report_input" >
                      <input id="queryer" type="tel" maxlength = "11" class="form-control" style="width:60%">
                               <span class="report_btn input-group-btn">
                               <a onclick="querycust()"  id="queryQuick" class="btn btn-primary m-l-5" style="outline: none;height: 34px;">
                               <span style="margin-right: 5px;" class="glyphicon glyphicon-search"></span>
                               </a>
                                
                               </span>
                    </div>
                   </div>
               
               <form id="formid" method = 'post' action="addCustinfo.action">   
                    <ul class="list-group">
                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                                  客户基本资料
                       </li> 
                       <li class="list-group-item">
                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span><span>姓名&nbsp;</span>
                        <input type="text" id="cust_name" name="cust_name" maxlength = "10" required="required" placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>性别</span>
                       <select id="cust_sex" name="cust_sex"  required="required">
                       		<s:iterator  value="#request.list1" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                       </select>
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">生日
                       
                        <input type="text" id="cust_birth" name="cust_birth" maxlength = "20" value="<%=now_date %>" class="form_datetime" placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span>手机号
                        
                        <input id="cust_cell" type="tel" required="required" maxlength = "11" name="cust_cell" placeholder="" class="formatNum" >
                      </span>
                      <span style="display: inline-block;width: 16%;height: 100%;">
                       
                        <span>证件类型</span>
                        <select id="cust_idtype" name="cust_idtype">
                        	<s:iterator  value="#request.list2" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                        </select>
                      </span>
                       </li>

                        <li class="list-group-item">
                         <span style="display: inline-block;width: 20%;height: 100%;">证件号
                        
                        <input type="text" id="cust_idnum" name="cust_idnum" maxlength = "30" placeholder="" class="cust_input" >
                        
                       </span>
                       <span style="display: inline-block;width: 20%;height: 100%;">城市
                       
                        <input type="text" id="city" name="city" maxlength = "20"  placeholder="" class="cust_input" >
                      </span>
                       <span style="display: inline-block;width: 20%;height: 100%;">雇主名称
                        
                        <input type="text" id="company" name="company" maxlength = "100"  placeholder="" class="cust_input" >
                        
                       </span>
                       
                       <span style="display: inline-block;width: 20%;height: 100%;">&nbsp;&nbsp;&nbsp;微信号
                       
                        
                        <input type="text" id="wechat" name="wechat" maxlength = "30" placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 16%;height: 100%;">
                        <span>级别</span>
                        <select id="cust_level" name="cust_level">
                        <s:iterator  value="#request.list0" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                         </select>
                      </span>
                    </li>

                     <li class="list-group-item">
                        <span style="display: inline-block;width: 20%;height: 100%;">&nbsp;&nbsp;QQ&nbsp;&nbsp;
                        <input type="text" id="qq" name="qq" maxlength = "30"  placeholder="" class="cust_input" >
                      </span>
                      
                       <span style="display: inline-block;width:20%;height: 100%;">职业
                       
                        <input type="text" id="profession" name="profession" maxlength = "20" placeholder="" class="cust_input" >
                      </span>

                      <span style="display: inline-block;width:25%;height: 100%;">电子邮箱
                        
                        <input type="text" id="email" name="email" maxlength = "30"  placeholder="" class="cust_input" style="width: 70%;" >
                      </span> 

                       <span style="display: inline-block;width: 30%;height: 100%;">地址
                      
                        <input type="text" id="address" name="address" maxlength = "200" placeholder="" class="cust_input" style="width:90%;" >
                      </span>
                     </li>
              
                    </ul> 

                      <ul class="list-group" style="margin-top: 30px;">
                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       <span style="color:red;margin-right: 5px;">*</span> 拜访记录
                       </li> 

                       <li class="list-group-item" style="border-bottom: 0">
                       <span style="display: inline-block;width: 20%;height: 100%;">日期
                       
                        <input type="text" id="see_date" name="see_date"  required="required" value="<%=now_date %>" class="form_datetime" placeholder="" class="cust_input" >
                      </span>
                       <span style="display: inline-block;width: 32%;height: 100%;">陪同人员
                      
                        <input type="text" id="see_member" name="see_member" maxlength = "50"  placeholder="" class="cust_input" style="width: 80%;" >
                      </span>
                       <span style="display: inline-block;width: 32%;height: 100%;">
                      
                       <i style="font-size: 10px;">备注( 陪同人员可添加多人，用逗号分隔 )</i>
                      </span> 
                       </li>

                       </ul>

                        <span style="vertical-align: top;margin-left: 15px;">描述 </span><textarea name="see_desc" id="see_desc" maxlength = "500" required="required" cols="120" rows="4" style="outline: none;"></textarea>
                          <br />
                   <div id='list_div_2' style="display: none;border-top: 1px solid #ddd;">
                      <br />
                      <ul class="list-group" style="margin-top: 30px;">                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       <button type="button"  data-toggle="modal"  data-target="#myModal6" style="height:30px;">公司<span class="glyphicon glyphicon-plus" style="margin-left: 15px;"></span></button> 
                       </li> 
                       </ul>

                      <ul class="list-group" style="margin-top: 5px;">
                        <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       <button type="button"  data-toggle="modal"  data-target="#myModal8" style="height:30px;">家族<span class="glyphicon glyphicon-plus" style="margin-left: 15px;"></span></button>
                       </li> 
                      </ul> 
                       <div class="row text-center" style="margin:25px 0;">
                          <a href="#"><button  data-dismiss="modal"
                           class="btn btn-lg btn-default" onclick="skipadd3()">取消</button></a>
          <!--                    <input type="button" value="保存" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="skipaddsave()" > -->
                             
                            <input type="button" value="提交审批" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="skipaddpass()">
                       </div> 
                      </div>                    
<!--  -->          

                    </form>
                    
                                       
          </div>
          </div>
           </div>

           <!-- 4 -->
            

<!-- btn --><div id='next_btn' class="modal-footer" style="position: absolute;right: 0;border:0;bottom: -10px;">
           
             <button type="button" onclick="addCustinfo()" class="btn btn-primary MN-next">下一步</button>
          </div> 
           
       </div>

              </div>
              <!-- <div class="row text-center" style="margin:25px 0;">
                          <a href="/OMS/cust.action"><button  data-dismiss="modal"
                           class="btn btn-lg btn-default">取消</button></a>
                            <input type="button" value="保存" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addCustinfo()" >
                             
                            <input type="button" value="提交审批" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addCustcheck()">
                       </div> --> 
            </div>

          </div>

           
  </div>
  
  <!--友好提示弹框  -->
  <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      
        <div style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
         是否继续添加？
        
        </div>

    
          <div class="row text-center" style="margin-bottom: 15px;">
            <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" ">否</button>

            <button id="customerCreateBtn" data-dismiss="modal"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" onclick="add_production(4)">是</button>
          </div>
    
    
  </div>
</div>
</div>

<!-- 添加成功提示弹框  -->
  <div class="modal fade bs-example-modal-sm" id = "tishi" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      
        <div id = "content" style="text-align: center;height:120px;font-size:16px;line-height: 70px;width:100%;">
         
        
        </div>

    
          <div class="row text-center" style="margin-bottom: 15px;">

            <button id="customerCreateBtn" data-dismiss="modal"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;">关闭</button>
          </div>
    
    
  </div>
</div>
</div>
<!--传入客户id  -->

<!--  -->
<!-- 添加客户公司信息弹窗 -->
<div class="modal" id="myModal6" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 980px;">
         
        <form id="formid1" method = 'post' action="addCompinfo.action"> 
        
          <div class="panel panel-default" style="width:100%;">
                     
                  <ul class="list-group">
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                      <h4> 添加客户公司信息</h4>
                       </li> 
                       <li class="list-group-item" >
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span> 公司名称</span>
                        <input value="" name="comp_name" id="comp_name" maxlength = "30" required="required" type="text" style="width: 75%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                         <%-- <span style="color: red;margin-right: 4px;float: left;">*</span> --%>
                        <span>公司类型</span>
                          <input name="comp_type" id="comp_type" maxlength = "30" type="text" style="width: 75%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                        <span>营业执照注册号(统一社会信用代码)</span>
                      <input name="license" id="license" type="text" maxlength = "30" style="width: 75%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item" style="border-bottom:0;border-top: dashed 1px #ddd;">
                         
                        <span>法人</span>
                         <input name="legal" id="legal" type="text" maxlength = "20"  style="width: 75%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                        
                        <span>税务登记号</span>
                        <input name="taxid" id="taxid" type="text" maxlength = "100" style="width: 90%;border:none;outline: none;">
                       </li>
                         <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                         
                        <span>组织机构代码证</span>
                        <input name="org_code_cert" id="org_code_cert" maxlength = "30" type="text" style="width: 75%;border:none;outline: none;">
                       </li>
                         
                        <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                        <span>成立日期</span>
                        <input name="reg_date" id="reg_date" value="<%=now_date %>"  class="form_datetime"  type="text" style="width: 20%;border:none;outline: none;">
                       </li>
                        
                         <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                        <span>经营期限</span>
                        <input name="opera_period" id="opera_period" value="<%=now_date %>"  class="form_datetime"  type="text" style="width: 20%;border:none;outline: none;">
                       </li>
                      
                       <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                        <span>注册资本</span>
                        <input name="reg_capital" id="reg_capital" maxlength = "30" type="number" style="width: 90%;border:none;outline: none;">
                       </li>
                       
                          <li class="list-group-item" style="border-top: dashed 1px #ddd;">
                         
                        <span>注册地址</span>
                        <input name="reg_address" id="reg_address" type="text" maxlength = "100"  style="width: 90%;border:none;outline: none;">
                       </li>
                         
                         
                    </ul> 
          <div class="row text-center" style="margin:25px 0;">
                          <a href="#"><button  data-dismiss="modal"
                           class="btn btn-lg btn-default">取消</button></a>
                            <input type="button" value="保存" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addCompinfo()" >
                             
                       </div>
        </div>   
</form>

           </div>
      </div>
</div>
</div>



<!-- 添加客户家族信息弹窗 -->
<div class="modal" id="myModal8" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog" style="width: 980px;">
<form id="formid2" method = 'post' action="addFamilyinfo.action">
     <div class="panel panel-default" style="width:100%;">
       <input type="hidden" name="cust_idcheck" id="cust_idcheck" value="" />
      <li><input name="cust_name" id="cust_name" type="hidden" value=""/></li>
                    <ul class="list-group">
                         <li class="list-group-item" >
                         
                         <h4 style="color: #555;">客户家族资料 
                         <!-- <a href="#" style="float: right;margin-right: 15px;"><span class="glyphicon glyphicon-floppy-disk" ></span>保存</a> --></h4>
                       </li>
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       家族基本信息
                       </li> 
                      
                       <li class="list-group-item" >
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span> 家族名称</span>
                        <input name="family_name" id="family_name" value="" readonly required="required" type="text" style="width: 20%;border:none;outline: none;" />
                       </li>
                        <li class="list-group-item">
                        <span>成立时间</span>
                          <input id="reg_time" name="reg_time" value="<%=now_date %>" readonly class="form_datetime"  style="width: 16%;border:none;outline: none;">
                       </li>
                    </ul> 
                    <div style="width: 100%;margin: 20px 0;">
                     <span style="vertical-align: top;margin-left: 15px;">家族描述 </span><textarea name="family_cust_desc" id="family_cust_desc" maxlength = "500" cols="120" rows="4" style="outline: none;"></textarea>
                     </div>
                   <!--   <li class="list-group-item">
                        <span>备注</span>
                          <input type="text" style="width: 75%;border:none;outline: none;">
                       </li> -->

                      <ul class="list-group">  
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       查找并添加成员
                       </li> 
                     </ul>
  
         <div class="report_search">
                       <div class="report_label">
                         <label>
                                            按电话号码查询
                           </label>
                       </div>
                    <div class="report_input" >
                      <input id="queryFM" type="tel" maxlength = "11" class="form-control" style="width:60%">
                               <span class="report_btn input-group-btn">
                               <a onclick="queryFamilyMember()"  id="queryQuick" class="btn btn-primary m-l-5" style="outline: none;height: 34px;">
                               <span style="margin-right: 5px;" class="glyphicon glyphicon-search"></span>
                               </a>
                                
                               </span>
                    </div>
                   </div>
                   <div class="panel panel-default" style="width:38%;margin:0 auto;align:center;border:0;" id='myModalNext'>
                         <div class="panel-body">
							<table id="playlistTable" class="table cust_table">
							<!-- <thead>
									<tr class="demo_tr">
										<th>选择</th>
										<th>客户名称</th>
									</tr>
								</thead> -->
								<tbody id="partner">
								</tbody>
							</table>
						</div>
						</div>
 
<!-- 信息 -->
           <div id='list_div'>
                    <ul class="list-group">
                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;cursor: pointer;">
                       录入新成员<span class="glyphicon glyphicon-plus" style="margin-left: 15px;"></span>
                       </li> 
                       <li class="list-group-item">
                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span><span>姓名&nbsp;</span>
                        <input type="text" id="cust_name1" name="cust_name1" maxlength = "10" placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span><span>性别</span>
                       <select id="cust_sex1" name="cust_sex1" required="required">
                       		<s:iterator  value="#request.list1" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                       </select>
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">生日
                       
                        <input type="text" id="cust_birth1" name="cust_birth1" maxlength = "20" value="<%=now_date %>"  class="form_datetime"  placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span>手机号
                        
                        <input type="tel"  id="cust_cell1" name="cust_cell1" maxlength = "11" required="required" placeholder="" class="cust_input" >
                      </span>
                      <span style="display: inline-block;width: 16%;height: 100%;">
                       
                        <span id="cust_idtype1">证件类型</span>
                        <select id="cust_idtype" name="cust_idtype1">
                        	<s:iterator  value="#request.list2" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                        </select>
                      </span>
                       </li>

                        <li class="list-group-item">
                         <span style="display: inline-block;width: 20%;height: 100%;">证件号
                        
                        <input type="text" id="cust_idnum1" name="cust_idnum1" maxlength = "30" placeholder="" class="cust_input" >
                        
                       </span>
                       <span style="display: inline-block;width: 20%;height: 100%;">关系
                       
                        <input type="text" id="relation1" name="relation1" maxlength = "10" placeholder="" class="cust_input" >
                      </span>
                       
                       <span style="display: inline-block;width: 20%;height: 100%;">雇主名称
                        
                        <input type="text" id="company1" name="company1" maxlength = "50" placeholder="" class="cust_input" >
                        
                       </span>
                       
                       <span style="display: inline-block;width: 20%;height: 100%;">&nbsp;&nbsp;&nbsp;微信号
                       
                        
                        <input type="text" id="wechat1" name="wechat1" maxlength = "50" placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 16%;height: 100%;">
                        <span>级别</span>
                        <select id="cust_level1" name="cust_level1">
                        <s:iterator  value="#request.list0" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                         </select>
                      </span>
                    </li>

                     <li class="list-group-item">
                        <span style="display: inline-block;width: 20%;height: 100%;">&nbsp;&nbsp;QQ&nbsp;&nbsp;
                        <input type="text" id="qq1" name="qq1" maxlength = "50" placeholder="" class="cust_input" >
                      </span>
                      
                       <span style="display: inline-block;width:20%;height: 100%;">职业
                       
                        <input type="text" id="profession1" name="profession1" maxlength = "30" placeholder="" class="cust_input" >
                      </span>

                      <span style="display: inline-block;width:25%;height: 100%;">电子邮箱
                        
                        <input type="text" id="email1" name="email1" maxlength = "50" placeholder="" class="cust_input" style="width: 70%;" >
                      </span> 
                      <span style="display: inline-block;width: 20%;height: 100%;">城市
                       
                        <input type="text" id="city1" name="city1" maxlength = "50" placeholder="" class="cust_input" >
                      </span>
                      </li>
                      <li class="list-group-item">
                       <span style="display: inline-block;width: 30%;height: 100%;">地址
                      
                        <input type="text" id="address1" name="address1" maxlength = "200" placeholder="" class="cust_input" style="width:90%;" >
                      </span>
                     </li>
              
                    </ul>
                     <ul class="list-group" style="margin-top: 30px;">                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       <span style="color:red;margin-right: 5px;">*</span> 拜访记录
                       </li> 

                       <li class="list-group-item" style="border-bottom: 0">
                       <span style="display: inline-block;width: 20%;height: 100%;">日期
                       
                        <input type="text" id="see_date1" name="see_date1" value="<%=now_date %>" class="form_datetime"  style="width: 50%;border:none;outline: none;" >
                      </span>
                       <span style="display: inline-block;width: 32%;height: 100%;">陪同人员
                      
                        <input type="text" id="see_member1" name="see_member1" maxlength = "50" placeholder="" class="cust_input" style="width: 80%;" >
                      </span>
                       <span style="display: inline-block;width: 32%;height: 100%;">
                      
                       <i style="font-size: 10px;">备注( 陪同人员可添加多人，用逗号分隔 )</i>
                      </span> 
                       </li>

                       <li>
                        
                      </li>
                       </ul>
             <div id='list_div_1'>
                       <span style="vertical-align: top;margin-left: 15px;">描述 </span><textarea name="see_desc1" id="see_desc1" maxlength = "500" required="required" cols="120" rows="4" style="outline: none;"></textarea>

           </div>

       </div>

           <div class="row text-center" style="margin:25px 0;">
                          <a href="#"><button  data-dismiss="modal"
                           class="btn btn-lg btn-default">取消</button></a>
                            <input type="button" value="保存" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addFamilyinfo()" >
                             
                            <!-- <input type="button" value="提交审批" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addCustcheck()"> -->
                       </div>

        </div> 
         </form>  

         </div>
    </div>
         
    </div> 
   

  <script>

      $( function() {
        SyntaxHighlighter.config.clipboardSwf = 'js/clipboard.swf';
        SyntaxHighlighter.all();
        //myModalNext 
        $("#myModalNext .modal-footer button").each(function() {
          $(this).click(function() {
            if ($(this).hasClass("MN-next")) {
              $("#myModalNext .carousel").carousel('next');
              $("#myModalNext .step li.active").next().addClass("active");
            } else {
              $("#myModalNext .carousel").carousel('prev');
              if ($("#myModalNext .step li").length > 1) {
                $($($("#myModalNext .step li.active"))[$("#myModalNext .step li.active").length - 1]).removeClass("active")
              }
            }
          })
        })
      })
      var next_btn=document.getElementById('next_btn');
      var i=1;
       
      /*  if(i=1){
        next_btn.onclick=function(){
           var list_div_2=document.getElementById('list_div_2');
           list_div_2.style.display='block';
           next_btn.style.display='none';
        }

      } */
    

    </script>
<script>
$(function(){
	$(".form_datetime").datetimepicker({
		 format: 'yyyy-mm-dd',  
       weekStart: 1,  
       autoclose: true,  
       startView: 2,  
       minView: 2,  
       forceParse: false,  
       language: 'zh-CN'  
	});
	
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
</div> </body></html>