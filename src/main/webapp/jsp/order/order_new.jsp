<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
 <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>order_new</title>
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

<script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/brush.js"></script>
<script src="/OMS/js/order.js"></script>
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
            <li class="active">新建订单</li>
          </ol>
        </div>
        <div class="col-md-6 t0b0 txtr">
        
        </div>
      </div>
      <input type="hidden" id="cust_type" value="" />
            <div class="row top10 mym">
    
           <div class="col-md-8 myright" style="width: 100%;margin:0 auto;padding-bottom: 30px;">
            <!-- 1 -->
            <div class="myright-n">
            <div class="myNav row" style="border-bottom: 1px solid #a52410;">             
               
                <h4 style="color: #a52410;">订单管理</h4>
          
             </div>
     
      </div>  
        <!-- 2 -->
        <div class="panel panel-default" style="width:90%;margin:0 auto;border:0;" id='myModalNext'>
                     
          <ul class="nav nav-pills nav-justified step step-progress" id='nav_ul'>
              <li class="active"><a>客户类型选择<span class="caret"></span></a>
              </li>
              <li><a>step2<span class="caret"></span></a>
              </li>
              <li><a>step3<span class="caret"></span></a>
              </li>
              <li><a>step4<span class="caret"></span></a>
              </li>
              <li><a>step5<span class="caret"></span></a>
              </li>
             <!--  <li><a>step5<span class="caret"></span></a>
              </li>
              <li><a>step6<span class="caret"></span></a>
              </li> -->
            </ul>
            <div class="modal-body">
            <div class="container-fluid"  >
              <div class="carousel slide" data-ride="carousel" data-interval="false" data-wrap="false">

                <!-- Wrapper for slides -->
          <div class="carousel-inner" role="listbox" style="position: relative;padding-bottom: 40px;">

         <div class="item active">
            <h4  style="font-weight: bold;color: red;">第一步</h4>
              <h5 style="font-weight: bold;">客户类型选择</h5>
           <div data-toggle="buttons-radio" id="cust_type" name="cust_type" class="btn-group" style="float: left;margin-bottom: 30px;">
            <button disabled="disabled" id="btn_1" value="1" class="btn btn-sm btn-light-info active" onclick="custtype()"> 个人客户</button>
            <button disabled="disabled" id="btn_2" value="2" class="btn btn-sm btn-light-info" onclick="orgtype()"> 机构客户</button>
          </div>
           </div>
          	<style>
          	input::-webkit-calendar-picker-indicator {
			    display: none;
			    -webkit-appearance: none;
			}
          	</style>
          	<script>
          	function onInput() {
          		$.ajax({
          			url : "/OMS/orderSales.action",
          			type : "post",
          			dataType : "json",
          			data : {
          				"sale_name" : $('#sale_name').val(),
          			},
          			success : function(data) {
          				$('#pasta').empty();
          				if (data.desc == 1) {
          					var list = data.list;
          					for(var i=0;i<list.length;i++){
          						var option = '<option onclick="selectItem()" label='+list[i].employee_code+' value='+list[i].real_name+'></option>';
          						$('#pasta').append(option);
          					}
          				} 
          			},
          			error : function() {
          				var msg = escape(escape("数据查询异常"));
          				location.href = "/OMS/error.action?msg=" + msg
          			}
          		});
          	    var val = $("#sale_name").val();
          	    var opts = $("#pasta").find("option");
          	    for (var i = 0; i < opts.length; i++) {
          	      if (opts[i].value === val) {
          	    	orderSales();
          	        break;
          	      }
          	    }
          	  }
			</script>
           <div id='cust' style="clear: both;display: none;border-top: 1px solid #ddd;">
           
             <h4  style="font-weight: bold;color: red;">第二步</h4>
              <h5 style="font-weight: bold;">客户查询</h5>
               <span style="float:left;position: relative;margin-bottom: 10px;"><input id="tel" type="text" class="form-control"  style="width: 300px;" value="请输入电话号码" 
                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''">
               <button  onclick="cust_select()" data-toggle="modal" type="submit" id="queryQuick" class="btn btn-primary m-l-5" style="outline: none;height: 34px;position: absolute;right:-42px;top: 0;border-bottom-left-radius: 0;border-top-left-radius: 0;">
               <span style="margin-right: 5px;" class="glyphicon glyphicon-search"></span>
              </button>
              </span>
			
       <table id="list" class="table cust_table" style="width: 99%;">
            <thead >
            <tr class="demo_tr" >
            <th >客户名称</th> 
            <th>联系方式</th>
            <th>RM</th>
           <!--  <th>新建订单</th> -->
            </tr>
            </thead>
            <tbody id="tbody">

            
            </tbody>
            </table>
           </div>
           
    <!--选机构的第二步  -->  
    
    <div id='org' style="clear: both;display: none;border-top: 1px solid #ddd;">
    
             <h4  style="font-weight: bold;color: red;">第二步</h4>
              <h5 style="font-weight: bold;">机构查询</h5>
               <span style="float:left;position: relative;margin-bottom: 10px;"><input id="tel1" type="text" class="form-control"  style="width: 300px;" value="请输入营业执照号" 
                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''">
               <button  onclick="org_select()" data-toggle="modal" type="submit" id="queryQuick" class="btn btn-primary m-l-5" style="outline: none;height: 34px;position: absolute;right:-42px;top: 0;border-bottom-left-radius: 0;border-top-left-radius: 0;">
               <span style="margin-right: 5px;" class="glyphicon glyphicon-search"></span>
              </button>
              </span>

       <table id="list" class="table cust_table" style="width: 99%;">
            <thead >
            <tr class="demo_tr" >
            <th >机构名称</th> 
            <th>营业执照号</th>
            <th>法人</th>
           <!--  <th>新建订单</th> -->
            </tr>
            </thead>
            <tbody id="tbody1">

            
            </tbody>
            </table>
           </div>
    <!-- end -->    
    <input type="hidden" id ="cust_no" value=""/>
    <input type="hidden" id="kycid"/>
<div id='list_div_3' style="clear: both;display: none;border-top: 1px solid #ddd;">
            <h4  style="font-weight: bold;color: red;">第三步</h4>
             <!--  <button class="btn btn-primary m-l-5"  onclick="kycselect()" style=" width: 80px;height: 40px;" >
              <h5 style="font-weight: bold;">kyc查询</h5>
           kyc查询
               
              </button> -->
              
              <table id="kcy" class="table cust_table" style="width: 99%;">
            <thead >
            <tr class="demo_tr" >
            <th >kcyid</th> 
            <th>客户kyc录入时间</th>
            
           <!--  <th>新建订单</th> -->
            </tr>
            </thead>
            <tbody id="tbody2">

            
            </tbody>
            </table>
          </div>
           </div>
           <!--3  -->
           <input id="prod_diffcoe" type="hidden" value="${prod_diffcoe }" />
           <div id='list_div_4' style="clear: both;display: none;border-top: 1px solid #ddd;">
             <h4  style="font-weight: bold;color: red;">第四步</h4>
              <h5 style="font-weight: bold;">订单信息</h5>
                 <ul class="list-group" style="height: 800px;">
                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);" >
                       <h3 class="panel-title" style="color:#a52410;">
                       <span class="glyphicon glyphicon-plus" aria-hidden="true"  style="margin-right: 6px;"></span>
                       新建订单</h3>
                       </li> 

                        <li class="list-group-item" >
                         
                        <h4 style="font-weight: bold;">订单信息</p>
                        
                       </li>
                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         
                         <span style="margin-right: 4px;float: left;width: 46%;">
                           客户名称<input id="cust_name" name="cust_name" type="text" readOnly="true" style="outline: none;margin-left: 5px;border:none;">
                         </span>
                        <span style="margin-right: 4px;">产品名称</span>
                        <s:if test="#request.status==1 ">
												
							<select name="prod_no" id="prod_no" onchange="orderchange()">
							<s:iterator value="#request.list" var="lis">
                             <option value="${lis.prod_id }">${lis.prod_name }</option>
                             </s:iterator>
                           </select>
												
											</s:if>
                       </li>
                       <li class="list-group-item" style="border-top:1px solid #f2f2f2;border-bottom:0;">
                         
                         <span style="margin-right: 4px;float: left;width: 46%;">
                           销售类型
                         <select id="order_type" name="order_type" >  
                         	 <s:iterator value="#request.order_list" var ="con">
                             <option value="${con.dict_value }">${con.dict_name }</option>
                            </s:iterator>
                         </select>
                         </span>
                        <span style="margin-right: 4px;"><span style="color: red; margin-right: 4px; float: left;">
                        *</span>下单金额<input id="order_amount" name="order_amount" oninput="qianfenwei('order_amount')" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 265px;"></span>
                       </li>

                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;border-bottom: 0;">
                         
                         <span style="margin-right: 4px;float: left;width: 46%;">
                           地区
                           <select name="area" id="area">
                             <s:iterator value="#request.areaList" var ="con">
                             <option value="${con.dict_value }">${con.dict_name }</option>
                            </s:iterator>
                           </select>
                           
                         </span>
                        <span style="margin-right: 4px;"><span style="color: red; margin-right: 4px; float: left;">*</span>合伙企业
                        <select name="part_comp" id="part_comp">
                        <s:if test="#request.desc==1">
                             <s:iterator value="#request.lplist" var ="con">
                             <option value="${con.lp_id }">${con.partner_com_name }</option>
                            </s:iterator>
                            </s:if>
                           <%--  <s:else>
                            	<option>未找到该产品下对应的lp</option>
                            </s:else> --%>
                           </select>
                           
                        </span>
                       </li>

                       <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         
                         <span style="margin-right: 4px;float: left;width: 46%;">
                           合同类型
                           <select name="contract_type" id="contract_type">
                           <s:iterator value="#request.con_list" var ="con">
                             <option value="${con.dict_value }">${con.dict_name }</option>
                            </s:iterator>
                           </select>
                         </span>
                        <span style="margin-right: 4px;">合同号<input id="contract_no" name="contract_no"  required="required" type="text" maxlength="100" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                       </li>

                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         
                         <span style="margin-right: 4px;float: left;width: 46%;">
                          是否 证件复印件<select id="is_id" name="is_id">
                          <option value="0">是</option>
                          <option value="1">否</option>
                          </select>
                           <!-- <input id="is_id" name="is_id" type="checkbox" style="margin-left: 15px;"> -->
                         </span>
                         <span style="margin-right: 4px;">证件类型
                         <select name="id_type" id="id_type">
                            <s:iterator value="#request.idtypeList" var ="con">
                             <option value="${con.dict_value }">${con.dict_name }</option>
                            </s:iterator>
                           </select>
                         </span>
                        
                       </li>
                       
                   <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         
                         <span style="margin-right: 4px;float: left;width: 46%;">证件号
                         <input id="id_no" name="id_no"  maxlength="50" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;">
                         </span>
                        <span style="margin-right: 4px;">产品系数
                        <input id="prod_diffcoe1" name="prod_diffcoe1" readOnly="true" maxlength="50" type="text" style="width:100px;border-top:0px;border-right:0px;border-left:0px; border-bottom:1px">
                        </span>
                       </li>
                   
                       <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         
                         <span style="margin-right: 4px;float: left;width: 46%;">
                           指导标费<input id="pri_fee" name="pri_fee" readOnly="true"  
                            style="border-left:0px;border-top:0px;border-right:0px;outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 265px;">
                         </span>
                        <span style="margin-right: 4px;">折后标费<input id="acount_fee" name="acount_fee" maxlength="20" oninput="qianfenwei('acount_fee')" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 265px;"></span>
                       </li>

                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         
                          <span style="margin-right: 4px;float: left;width: 46%;">认购费<input id="buy_fee"  maxlength="20" oninput="qianfenwei('buy_fee')" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                        <span style="margin-right: 4px;">开办费<input id="start_fee" name="start_fee"  maxlength="20" oninput="qianfenwei('start_fee')" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                       </li>

                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         
                          <span style="margin-right: 4px;float: left;width: 46%;">滞纳金<input id="delay_fee"  maxlength="20" oninput="qianfenwei('delay_fee')" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                        <span style="margin-right: 4px;">违约金<input id="break_fee"  maxlength="20" name="break_fee" oninput="qianfenwei('break_fee')" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                       </li>
                       

                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         <span style="margin-right: 4px;float: left;width: 46%;">
                           延期入伙利息<input id="late_join_fee" name="late_join_fee" oninput="qianfenwei('late_join_fee')" maxlength="40" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 240px;">
                         </span>
        		         <span style="margin-right: 4px;">身份证地址<input id="cust_address"  type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
<%--                         <span style="margin-right: 4px;">备注<input id="remark" name="remark" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 293px;"></span>
 --%>                        
                       </li>

                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         
                          <span style="margin-right: 4px;float: left;width: 46%;">开户行<input id="bank_no"  maxlength="20" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                         <span style="margin-right: 4px;"><span style="color: red; margin-right: 4px; float: left;">*</span>下单日期<input id="create_time" class="form_datetime" maxlength="20" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                        
                       </li>
                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                         <span style="margin-right: 4px;float: left;width: 46%;">银行卡号
                        <!--  <input  type="text" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');"> --> 
                         <input id="bank_card" name="bank_card"  style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 265px;"
                         onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');"></span>
                         <span style="margin-right: 4px;"><input class="form_datetime" type="text" style="display:none;outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                         <span style="margin-right: 4px;"><span style="color: red; margin-right: 4px; float: left;">*</span>首次缴款日期<input id="first_pay_time" class="form_datetime" maxlength="20" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                       </li>
                       <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                          <span style="margin-right: 4px;float: left;width: 46%;">邮寄地址<input id="mail_address"   type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
      		              <span style="margin-right: 4px;">工作单位<input id="work_address" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                       </li>
                        <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
      		              <span style="margin-right: 4px;float: left;width: 46%;">邮箱<input id="cust_email"   type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
        		          <span style="margin-right: 4px;">联系电话<input id="cust_cell"  maxlength="20" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
                       </li>
                       <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
                  		  <span style="margin-right: 4px;float: left;width: 46%;">额外奖励<input id="extra_award"  maxlength="20" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span>
          		          <span style="margin-right: 4px;">实际投资人<input id="investor_no" readonly="readonly"  maxlength="20" type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 278px;"></span><button type="button" class="btn btn-warning btn-xs" onclick="showAddInvestor()"><i class="glyphicon glyphicon-plus"></i></button>
                       </li>
                       <li class="list-group-item" style="border-top:1px solid #f2f2f2;">
       		              <span style="margin-right: 4px;">备注<input id="comment"  type="text" style="outline: none;margin-left: 5px;border:none;border-bottom: 1px solid #ddd;width: 800px;"></span>
                       </li>
                    </ul> 
           </div>

           <!-- 4 -->
            <div id='list_div_5' style="clear: both;display: none;border-top: 1px solid #ddd;">
             <h4  style="font-weight: bold;color: red;">第五步</h4>
              <h5 style="font-weight: bold;">指定销售</h5>
<!-- 查找销售员工 -->
               <span style="float:left;position: relative;margin-bottom: 10px;">
               <!-- <input  id="inputval" > -->
			
			
               <input list="pasta" id="sale_name" type="text" class="form-control" oninput="onInput()" style="width: 300px;" value="请输入销售姓名" 
                onblur="if(this.value=='')this.value=this.defaultValue"  onfocus="if(this.value==this.defaultValue) this.value=''">
                <datalist id="pasta">
				</datalist>
              <%--  <button onclick="orderSales()" data-toggle="modal" type="submit" id="queryQuick" class="btn btn-primary m-l-5" style="outline: none;height: 34px;position: absolute;right:-42px;top: 0;border-bottom-left-radius: 0;border-top-left-radius: 0;">
               <span style="margin-right: 5px;" class="glyphicon glyphicon-search"></span>
              </button> --%>
              </span>
              <br />
             
              <%-- <span style="float:center;position: relative;margin-bottom: 10px;">
               <!-- <div class="row text-center" style="margin: 0 auto; padding: 10px 0;">
								<div data-toggle="buttons-radio" id="ms" name="ms"
									class="btn-group" style="margin-left: 75px;">
									<button value="1"  class="btn-sm btn btn-light-info active"
										onclick="show_ms(1)">按标费分配</button>
									<button value="2" class="btn-sm btn btn-light-info"
										onclick="show_ms(2)">按提成点分配</button>
								</div>
							</div> -->
                </span> --%>
							<div class="panel panel-default" style="width: 100%;margin: 0 auto; padding:0">

								 <div class="panel-heading" >
								
									<h3 class="panel-title"
										style="color: #a52410; position: relative;">
										<%-- <span class="glyphicon glyphicon-th" aria-hidden="true"
											style="margin-right: 6px;"></span>订单列表 --%>

									</h3>
								</div>
								<div class="row text-center" style="margin: 0 auto; padding: 10px 0;">
								<div data-toggle="buttons-radio" id="ms" name="ms"
									class="btn-group" style="margin-left: 75px;">
									<button value="1"  class="btn-sm btn btn-light-info active"
										onclick="show_ms(1)">按标费分配</button>
									<button value="2" class="btn-sm btn btn-light-info"
										onclick="show_ms(2)">按提成点分配</button>
								</div>
							</div>
								<div class="panel-body">

									<table class="table cust_table" id="playlistTable">
										<thead>
											<tr class="demo_tr" style="font-size: 10px;">
												<th>选择</th>
												<th>销售</th>
												
												<th>业绩</th>
												<th>提成点</th>

											</tr>
										</thead>
										<tbody id="tbody_sale" >
                                    
											
											
											
										</tbody>
									</table>

								</div>
							</div>
				<!-- <div class="row text-center"
								style="margin: 0 auto; padding: 10px 0;">
								<div data-toggle="buttons-radio" id="ms" name="ms"
		
		
									class="btn-group" style="margin-left: 75px;">
									<button value="1"  class="btn-sm btn btn-light-info active"
										onclick="show_ms(1)">按标费分配</button>
									<button value="2" class="btn-sm btn btn-light-info"
										onclick="show_ms(2)">按提成点分配</button>
								</div>
							</div>
                              
				            <table id="playlistTable" class="table cust_table" style="width: 99%;">
				            <thead >
				            <tr class="demo_tr">
				            <th >销售</th> 
				            <th>业绩</th>
				            <th>业绩点</th>
				            </tr>
				            </thead>
				            <tbody>
				
				            <tr class="default" id="cust_list">
				            <td style="display:none;">12132</td>
				            <td>RM_1</td>
				            <td>100%</td>
				            <td>100%</td>
				            </tr>
				            </tbody>
				            </table> -->
                   
     <div class="row text-center" style="margin-bottom: 30px;">
      <a  data-dismiss="modal"
            href="orderList.action" class="btn btn-lg btn-default">取消</a>
      <input type="button" value="保存" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="order_insert('0')" >
      <input type="button" value="提交审批" data-dismiss="modal" id="submiting"
                             class="btn btn-lg " onclick="order_insert('1')" style="background-color:#5bc0de; color: #fff;" >
      <!-- <button class="btn btn-lg" data-dismiss="modal" onclick="" style="background-color:#5bc0de; color: #fff;">提交审核</button> -->
    </div>

    
            </div>


<!-- btn -->
           <div id='next_btn' class="modal-footer" style="position: absolute;right: 0;border:0;bottom: -10px;">
           
             <button type="button" class="btn btn-primary MN-next">下一步</button>
          </div>
       </div>

              </div>
            </div>

          </div>

           
  </div>
  <div class="modal" id="addInvestorModal" tabindex="-1" role="dialog"
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
										<th>勾选</th>
									</tr>
								</thead>
								<tbody id="custbody">
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
<div id="myal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      
        <div id="content" style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
       
        
        </div>

    
          <div class="row text-center" style="margin-bottom: 15px;">
            <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" ">关闭</button>
          </div>
  </div>
</div>
</div>
<div id="submitHint" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog" style="width: 400px;">
    <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      
        <div style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
         提交成功!
        </div>
          <div class="row text-center" style="margin-bottom: 15px;">
            <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #6699FF;border-radius: 2px;" data-dismiss="modal"
              class=""  onclick="refreshOrder()">关闭</button>   
          </div> 
  </div>
</div>

<div id="mydiv" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
         </div>
        <div style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
         是否继续添加？
        
        </div>

    
          <div class="row text-center" style="margin-bottom: 15px;">
            <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" " onclick="order1()">否</button>

            <button id="customerCreateBtn" data-dismiss="modal"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" onclick="order()">是</button>
          </div>
  </div>
</div>
  <script>
  var investor_no;
	var cust_name;
	function selectCust() {
		var v = $('#addInvestorModal #custbody input:radio:checked').val().split(",");
		if(!v) {
			alert("您还未选择投资人!");
			return false;
		}
		investor_no = v[0];
		cust_name = v[1];
		
		$('#investor_no').val(''+cust_name);
		$('#addInvestorModal').modal('hide');
	}
  function searchCustByName() {
		var cust_name = $('#addInvestorModal #cust_name').val();
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
					$('#addInvestorModal #custbody').empty();
					for(var i = 0; i < list.length; i++) {
						tbody += '<tr class="default">'
							+ '<td>'+list[i].cust_name+'</td>'
							+ '<td>'+list[i].real_name+'</td>'
							+ '<td><input name="cust_id" id="cust_id" type="radio" value="'+list[i].cust_id+','+list[i].cust_name+'"/>'
							+ '</td></tr>';
					}
					$('#addInvestorModal #custbody').append(tbody);
				}
			}
		});
	}
      $( function() {
    	  $(".form_datetime").datetimepicker({
  			format : 'yyyy-mm-dd',
  			weekStart : 1,
  			autoclose : true,
  			startView : 2,
  			minView : 2,
  			forceParse : false,
  			language : 'zh-CN'
  		});
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
       
       if(i=1){
        next_btn.onclick=function(){
        	document.getElementById('btn_1').disabled=false;
        	document.getElementById('btn_2').disabled=false;
        	var bt1 = $('#cust_type > .btn.active').val();
        	var list_div_2 ;
        	if(bt1=="1"){
        		list_div_2=document.getElementById('cust');
        	}
        	if(bt1=="2"){
        		list_div_2=document.getElementById('org');
        	}
        	//if(bt1)
           
           list_div_2.style.display='block';
           i++;
            if(i=2){
         next_btn.onclick=function(){
           var list_div_3=document.getElementById('list_div_3');
           list_div_3.style.display='block';
           //kycselect();
           kycselect();
        	   i++;
          
           if(i=3){
            next_btn.onclick=function(){
            	var a = selectkyc();
            	var list;
            	if(a==1){
            		list= 'list_div_4';
            	}else{
            		list="";
            		document.getElementById('content').innerHTML="未找到kyc客户信息";
    				$('#myal').modal('show');
            	}
            	 
               var list_div_4=document.getElementById(list);
               list_div_4.style.display='block';
               i++;
               if(i=3){
                   next_btn.onclick=function(){
                	   cust_org_select();
                      var list_div_4=document.getElementById('list_div_5');
                      list_div_4.style.display='block';
                      next_btn.style.display='none';
                   }
                  
                  }
               //next_btn.style.display='none';
            }
           
           }

        }
      }
        }

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
</div>
</div></body></html>