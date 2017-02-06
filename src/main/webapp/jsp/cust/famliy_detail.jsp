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
<title>family_detail</title>
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
</head>

<body data-spy="scroll" data-target=".navbar-example">
<!--nav-->
<%@ include file="../header.jsp" %>




<!--主体-->
      
      <div class="container m0 bod top70" id="zt">
        <div class="row">
        <div class="col-md-6 t0b0 ">
           <ol class="breadcrumb t0b0">
              <li><a href="<%=request.getContextPath() %>/jsp/index.jsp">
                  <i class='fa  fa-home'></i>&nbsp;首页</a>
                 </li>
              <li><a href="/OMS/cust.action">个人客户管理</a></li>
                <li class="active">个人-家族</li>
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
            <img id="tou" src="<%=request.getContextPath()%>/image/person2.png" class="f imgr20">
         </a>   
        <div class="f imgf20">
            <h4>
            ${cust_name}--家族</h4>
            <s:if test="#request.status3==1">
            <h4><span style="font-size: 22px;color: #a52410;margin-left: 15px;">${famliy_detail.family_state_name}</span></h4>
            </s:if>
            
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
    <div class="myleft-n">

        <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
         <i class="fa fa-lightbulb-o"></i> 家庭成员列表 <br>
         
        </div>
        <s:if test="#request.status4==1">
        <ul class="list-group" id="list-grop">
        <s:iterator value="#request.famliy_detail1" var="lis">
            <li class="list-group-item" style="background-color:#ace1f1;">

                <a href="javascript:show_detail(${lis.family_id },${lis.member_id })" style="display:inline-block;width: 100%;height:100%;border:none;outline: none;text-align: center;text-decoration: none;color:#999;" href="#">
                
                ${lis.cust_name}
                </a>
               <input name="family_id" id="family_id" type="hidden"  value="${famliy_detail.family_id }"/>
            </li>
            </s:iterator>
        </ul>
              </s:if>
                <s:else>
                              <li >该客户还未添加家族，请添加</li><br />
                              <!-- <li style="color: #a52410;">注： <p>1.&nbsp;先添加客户家族的基本信息</p>
                                     <p>2.&nbsp;再添加家庭成员信息</p></li> --> 
                       </s:else>
    </div>
    <div class="df"></div>
</div>

          <!--end 左-->
        <!--右-->
        <div class="col-md-8 myright" style="width: 75%;">
            <div class="myright-n">
            <div class="myNav row">  
      <s:if test="#request.role_id==1||#request.role_id==2||#request.role_id==3"> 
            <s:if test="#request.status4==1">          
               <a href="javascript:fammember_edit()"><i class="glyphicon glyphicon-floppy-saved"></i> 保存 </a>
               <a href="javascript:fammember_submit()"><i class="glyphicon glyphicon-floppy-saved"></i> 提交审批 </a>
                <a href="javascript:custfam_reset()"><i class="glyphicon glyphicon-floppy-saved"></i> 重置 </a> 
             </s:if>  
               <s:if test="#request.status3==1">
               </s:if>
               <s:else>
               <a href="#" style="outline: none;" data-toggle="modal"  data-target="#myModal8"><i class="glyphicon glyphicon-floppy-saved"></i> 添加家族 </a>
               </s:else>
               
             <s:if test="#request.status3==1">
             <a href="#" style="outline: none;" data-toggle="modal"  data-target="#opener8"><i class="glyphicon glyphicon-floppy-saved"></i> 添加家庭成员 </a>
             </s:if>
        </s:if>
        <s:if test="#request.role_id==3">
             <s:if test="#request.status4==1">
               <a href="#"style="outline: none;" data-toggle="modal"  data-target="#fam_ispass"><i class="glyphicon glyphicon-floppy-saved"></i> 审批 </a>
               </s:if>
         </s:if>
             <s:else>
             </s:else>
             </div>
             
      <form id="formidfammember" method = 'post' action="fammember_edit.action"   >
            <div class="row topx myMinh" style="">
            <!-- <input name="family_id1" id="family_id1"   value=""/> 
            <input name="member_id" id="member_id"   value=""/>  -->
            
            <div class="spjz" style="">
          <s:if test="#request.status3==1">
            <div style="margin-bottom: 25px;font-size: 16px;margin-left: 10px;">
            	  <p>家族名称：${famliy_detail.family_name}</p>
            	  <%-- <p>家庭成员数：${famliy_detail.family_mem_num}</p> --%>
                   <p>成立时间：${famliy_detail.reg_time}</p>
            	  <p>家族描述：<input name="family_cust_desc" id="family_cust_desc" value="${famliy_detail.family_cust_desc}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/></p>
                  <input type="hidden" id="family_cust_level" name ="family_cust_level" value="${famliy_detail.family_cust_level}"/>
            </div>
                   </s:if>
                   <s:if test="#request.status3==1">  
               <div class="panel panel-default" style="width:100%;">
                <!--  <ul class="list-group" style="margin-bottom: 25px;">
            <li class="list-group-item">
              
            
                         
                  
            </li>
            </ul> -->
         
           <ul id="ul" class="list-group">
            <li class="list-group-item" style="background-color: rgb(245,245,245);">
               <h3 class="panel-title" style="color:#a52410;"><span class="glyphicon glyphicon-edit" aria-hidden="true"  style="margin-right: 6px;"></span>成员基本信息<%-- --${famliy_detail.cust_name} --%>
             
              </h3>             
                  
            </li>
             <li class="list-group-item item_border" >姓名：<input name="cust_name" id="cust_name" maxlength = "10" required="required"  value="${famliy_detail.cust_name}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/>
             <!-- <input type="text" style="border:none;width: 90%;color: #333;outline: none;"> -->
             <input name="family_id1" id="family_id1" type="hidden"  value="${famliy_detail.family_id}"/> 
            <input name="member_id" id="member_id" type="hidden"  value="${famliy_detail.member_id}"/> 
             </li>
             <li class="list-group-item item_border" >性别：<span>${famliy_detail.sex_name}</span>
             <%-- <input name="sex_name" id="sex_name" value="${famliy_detail.sex_name}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/> --%>
             
             </li>
             <li class="list-group-item item_border">状态：<span>${famliy_detail.state_name}</span><%-- <input name="state" id="state" value="${famliy_detail.state_name}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/> --%>
             
             </li>
             <li class="list-group-item item_border">联系电话：<input name="cust_cell" id="cust_cell" type="tel" maxlength = "11" value="${famliy_detail.cust_cell}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/>
             
             </li>
             <li class="list-group-item item_border">关系：<input name="relation" id="relation" maxlength = "10"  value="${famliy_detail.relation}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/>
             
             </li>
              <li class="list-group-item item_border">证件类型：<span>${famliy_detail.idtype_name}</span>
              <%-- <input name="idtype_name" id="idtype_name" value="${famliy_detail.idtype_name}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/> --%>
             
             </li>
             <li class="list-group-item item_border">证件号：<input name="cust_idnum" id="cust_idnum" maxlength = "60"  value="${famliy_detail.cust_idnum}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/>
             
             </li>
             <li class="list-group-item item_border">级别：<span>${famliy_detail.level_name}</span>
             
             </li>
             <li class="list-group-item item_border">微信号：<input name="wechat" id="wechat" maxlength = "50"  value="${famliy_detail.wechat}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/>
             
             </li>
             <li class="list-group-item item_border">QQ：<input name="qq" id="qq" maxlength = "50" value="${famliy_detail.qq}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/>
             
             </li>
             <li class="list-group-item item_border" style="border-bottom: none;">职业：<input name="profession" id="profession" maxlength = "30" value="${famliy_detail.profession}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/>
             
             </li>
             <li class="list-group-item item_border">雇主名称：<input name="company" id="company" maxlength = "50" value="${famliy_detail.company}" type="text" 
            style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/>
             
             </li>
               
        </ul>

            </div>
            </s:if>
          </div>
        </div>
        </form>
        
      </div>
           <!--end 右-->
    </div>  
<!--友好提示弹框  -->
  <div id="fam_ispass" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
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
                             class="btn btn-lg btn-success" onclick="fammember_nopass()" >
              <input type="button" value="是" style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="fammember_pass()" >
            
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
            <a href="cust_detail.action?cust_id=${cust_id }&flag=2">
            <button id="customerCreateBtn" data-dismiss="modal"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" >关闭</button></a>
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
       <li><input name="cust_id" id="cust_id" type="hidden"  value="${cust_id}"/></li>
      <li><input name="cust_name" id="cust_name" type="hidden"  value="${cust_name}"/></li>
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
                        <input name="family_name" id="family_name" value="${cust_name}--家族" readonly required="required" type="text" style="width: 20%;border:none;outline: none;" />
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
                       		<s:iterator  value="#request.list51" var="sex">
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
                        	<s:iterator  value="#request.list52" var="sex">
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
                        <s:iterator  value="#request.list50" var="sex">
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
                             class="btn btn-lg btn-success" onclick="addFamily()" >
                             
                            <!-- <input type="button" value="提交审批" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addCustcheck()"> -->
                       </div>

        </div> 
         </form>  

         </div>
    </div>

<!--添加-家庭成员-弹框  -->
<div class="modal" id="opener8" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  
   <div class="modal-dialog" style="width: 976px;height: 300px;">
                 <!--右-->
              <form id="formid5" method = 'post' action="addFamilymember.action"   >
              <input type="hidden" name="cust_idcheck1" id="cust_idcheck1" value="" />
              <li><input name="family_id" id="family_id" type="hidden"  value="${family_id1}"/></li>
  <%-- <s:if test="#request.status3==1">
              <input name="family_id" id="family_id" type="hidden"  value="${famliy_detail.family_id }"/> --%>
       
        <div class="col-md-8 myright" style="width: 100%;">
            <div class="myright-n">
      
                    <div class="col-md-8 myright" style="width: 100%;margin:0 auto;">
            <!-- 1 -->
            <div class="myright-n">
            <div class="myNav row" style="border-bottom: 1px solid #a52410;">             
               <!-- <a href="#"><i class="glyphicon glyphicon-floppy-saved"></i> 保存 </a> -->
                <h4 style="color: #a52410;">添加家庭成员</h4>
             </div>
     
      </div>  
      
        <!-- 2 -->
        <div class="report_search">
                       <div class="report_label">
                         <label>
                                            按电话号码查询
                           </label>
                       </div>
                    <div class="report_input" >
                      <input id="queryfm" type="tel" maxlength = "11" class="form-control" style="width:60%">
                               <span class="report_btn input-group-btn">
                               <a onclick="queryFamMem()"  id="queryQuick" class="btn btn-primary m-l-5" style="outline: none;height: 34px;">
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
								<tbody id="partner1">

								</tbody>
							</table>
						</div>
						</div>
        <div class="panel panel-default" style="width:100%;">
                <div id='list_div'>
                    <ul class="list-group">
                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;cursor: pointer;">
                       家庭成员信息<span class="glyphicon glyphicon-plus" style="margin-left: 15px;"></span>
                       </li> 
                       <li class="list-group-item">
                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span><span>姓名&nbsp;</span>
                        <input type="text" id="cust_name3" name="cust_name3" maxlength = "10" required="required"  placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span><span>性别</span>
                       <select id="cust_sex3" name="cust_sex3" required="required">
                       	     <s:iterator  value="#request.list51" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                       </select>
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">生日
                       
                        <input type="text" id="cust_birth3" name="cust_birth3" value="<%=now_date %>" readonly class="form_datetime"  placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 20%;height: 100%;">
                        <span style="color: red;margin-right: 4px;float: left;">*</span>手机号
                        
                        <input type="tel" id="cust_cell3" name="cust_cell3" maxlength = "11" required="required"  placeholder="" class="cust_input" >
                      </span>
                      <span style="display: inline-block;width: 16%;height: 100%;">
                       
                        <span id="cust_idtype">证件类型</span>
                        <select id="cust_idtype3" name="cust_idtype3">
                        	<s:iterator  value="#request.list52" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                        </select>
                      </span>
                       </li>

                        <li class="list-group-item">
                         <span style="display: inline-block;width: 20%;height: 100%;">证件号
                        
                        <input type="text" id="cust_idnum3" name="cust_idnum3" maxlength = "60" placeholder="" class="cust_input" >
                        
                       </span>
                       <span style="display: inline-block;width: 20%;height: 100%;">关系
                       
                        <input type="text" id="relation3" name="relation3" maxlength = "10" placeholder="" class="cust_input" >
                      </span>
                       
                       <span style="display: inline-block;width: 20%;height: 100%;">雇主名称
                        
                        <input type="text" id="company3" name="company3" maxlength = "50" placeholder="" class="cust_input" >
                        
                       </span>
                       
                       <span style="display: inline-block;width: 20%;height: 100%;">&nbsp;&nbsp;&nbsp;微信号
                       
                        
                        <input type="text" id="wechat3" name="wechat3" maxlength = "50" placeholder="" class="cust_input" >
                      </span>

                       <span style="display: inline-block;width: 16%;height: 100%;">
                        <span>级别</span>
                        <select id="cust_level3" name="cust_level3">
                        <s:iterator  value="#request.list50" var="sex">
						        <option value='<s:property value="#sex.lel_value"/>'><s:property value="#sex.lel_name"/></option>  
					  		</s:iterator>
                         </select>
                      </span>
                    </li>

                     <li class="list-group-item">
                        <span style="display: inline-block;width: 20%;height: 100%;">&nbsp;&nbsp;QQ&nbsp;&nbsp;
                        <input type="text" id="qq3" name="qq3" maxlength = "50" placeholder="" class="cust_input" >
                      </span>
                      
                       <span style="display: inline-block;width:20%;height: 100%;">职业
                       
                        <input type="text" id="profession3" name="profession3" maxlength = "30" placeholder="" class="cust_input" >
                      </span>

                      <span style="display: inline-block;width:25%;height: 100%;">电子邮箱
                        
                        <input type="text" id="email3" name="email3" maxlength = "50"  placeholder="" class="cust_input" style="width: 70%;" >
                      </span> 
                      <span style="display: inline-block;width: 20%;height: 100%;">城市
                       
                        <input type="text" id="city3" name="city3" maxlength = "50" placeholder="" class="cust_input" >
                      </span>
                      </li>
                      <li class="list-group-item">
                       <span style="display: inline-block;width: 30%;height: 100%;">地址
                      
                        <input type="text" id="address3" name="address3" maxlength = "200" placeholder="" class="cust_input" style="width:90%;" >
                      </span>
                     </li>
              
                    </ul>
                     <ul class="list-group" style="margin-top: 30px;">                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       <span style="color:red;margin-right: 5px;">*</span> 拜访记录
                       </li> 

                       <li class="list-group-item" style="border-bottom: 0">
                       <span style="display: inline-block;width: 20%;height: 100%;">日期
                       
                        <input type="text" id="see_date3" name="see_date3" value="<%=now_date %>" class="form_datetime"  style="width: 50%;border:none;outline: none;" >
                      </span>
                       <span style="display: inline-block;width: 32%;height: 100%;">陪同人员
                      
                        <input type="text" id="see_member3" name="see_member3" maxlength = "50" placeholder="" class="cust_input" style="width: 80%;" >
                      </span>
                       <span style="display: inline-block;width: 32%;height: 100%;">
                      
                       <i style="font-size: 10px;">备注( 陪同人员可添加多人，用逗号分隔 )</i>
                      </span> 
                       </li>
              </ul>
             <div id='list_div_1'>
                       <span style="vertical-align: top;margin-left: 15px;">描述 </span><textarea name="see_desc3" id="see_desc3" maxlength = "500" required="required" cols="120" rows="4" style="outline: none;"></textarea>

           </div>
           <br />
           <%-- <ul class="list-group" style="margin-top: 30px;">                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                                                                                         家族信息
                       </li> 
                        <li class="list-group-item" style="border-bottom: 0">        
                       <span style="vertical-align: top;margin-left: 10px;">家族描述 </span><textarea name="family_cust_desc" id="family_cust_desc" maxlength = "500" required="required" cols="120" rows="4" style="outline: none;"></textarea>
                            </li>
           </ul> --%>

       </div>

           <div class="row text-center" style="margin:25px 0;">
                          <a href="/OMS/famliy_detail.action"><button  data-dismiss="modal"
                           class="btn btn-lg btn-default">取消</button></a>
                            <input type="button" value="保存" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addFamilymember()" >
                             
                            <!-- <input type="button" value="提交审批" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addCustcheck()"> -->
                       </div>
        </div>
        
  </div>
      </div>
           <!--end 右-->
           
    </div>  
    
       </form>
   
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

<!-- 左边变色特效 -->
  <script>
  window.onload=function(){
     var list_grop=document.getElementById('list-grop');
     var list_li=list_grop.getElementsByTagName('li');

     for(var i=0;i<list_li.length;i++){
      // list_li[i].index=i;
      list_li[i].onclick=function(){
        
        for(var j=0;j<list_li.length;j++){
          list_li[j].style.background='rgb(245,245,245)';
        }
        this.style.background='#ace1f1';

      }
     }
  }
   
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
<%-- <script>
$(function(){
	alert()
	$.ajax({
		url : "/OMS/fam.action",
		type : "post",
		dataType : "json",
		data :{},

		success : function(data) {
			var list = data.list0;
			
			
				var ocust_sex="<option>"+list.lel_name+"</option>";
				$('#cust_sex').append(ocust_sex);
			
		}
		
	})
});
</script> --%>


</div></body></html>