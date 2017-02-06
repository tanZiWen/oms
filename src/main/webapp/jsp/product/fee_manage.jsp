<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>fee_manage</title>
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
<%-- <script src="<%=request.getContextPath() %>/js/hm.js"></script> --%>
<script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>

<script>var n = 1;</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/m.js" charset="UTF-8"></script>
<link href="<%=request.getContextPath() %>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
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
</script>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
String now_date = df.format(new Date());
%>
<script type="text/javascript">
$(window).load(function(){ 
		var length = <%=request.getAttribute("count") %>
		for(var i =0;i<length;i++){
			var boarddiv = "<div class='thumbnail' style='float:left;margin-left:15px;' id=test"+i+">"
			+"<p>开始时间：<input id=s_raise_date"+i+" name=s_raise_date"+i+" size='14' type='text' value='<%=now_date %>'"
			+"	 class='form_datetime'/></p>   "
			+"<p>结束时间：<input id=e_raise_date"+i+" name=e_raise_date"+i+" size='14' type='text' value='<%=now_date %>' "
				+"			 class='form_datetime'/></p>"
		    +"<p>利润提成：<input id=carry"+i+" type='text' size='14'/></p>"
		    +"<p style='display:none;'>利润提成：<input id=mng_module"+i+" value='0' type='text' size='14'/></p></div>";
			
		$(document.getElementById("appInfo")).append(boarddiv); 
		}	
		
		for(var k =0;k<length;k++){
	var feediv = "<div class='thumbnail' style='float:left;margin-left:15px;' id=test"+k+">"
	+"<p>开始时间：<input id=sraise_date"+k+" name=sraise_date"+i+" size='14' type='text' value='<%=now_date %>'"
	+"	 class='form_datetime'/></p>   "
	+"<p>结束时间：<input id=eraise_date"+k+" name=eraise_date"+i+" size='14' type='text' value='<%=now_date %>' "
		+"			 class='form_datetime'/></p>"
    +"<p>管理费用：<input id=mngfee"+k+" type='text' size='14'/></p> "
    +"<p style='display:none;'>管理费用：<input id=mngmodule"+k+" value='1' type='text' size='14'/></p> </div>";
	
		$(document.getElementById("mngFeeInfo")).append(feediv); 
		}
});  

	function feeConfirm(){
		var a = JSON.parse("{\"data\":[]}");
		var length = $('#length').val();
		for(var j=0;j<length;j++){
			var feeObj = new Object();
			
			feeObj.s_raise_date = $("#s_raise_date"+j).val();
			feeObj.e_raise_date = $("#e_raise_date"+j).val();
			feeObj.mng_fee = $("#carry"+j).val();
			feeObj.mng_module = $('#mng_module'+j).val();
			feeObj.mng_type = "";
			a.data.push(feeObj);
		}
		for(var j=0;j<length;j++){
			var feeObj = new Object();
		
			
			feeObj.s_raise_date = $("#sraise_date"+j).val();
			feeObj.e_raise_date = $("#eraise_date"+j).val();
			feeObj.mng_fee = $("#mngfee"+j).val();
			feeObj.mng_module = $('#mngmodule'+j).val();
			feeObj.mng_type = $('#mng_type').val();	
			a.data.push(feeObj);
		}
		  var json=JSON.stringify(a);
		  var lp_id = $('#lp_id').val();
		  var prod_id = $('#prod_id').val();
		 $.ajax({         
	  	    url:'fee_mana.action',  //后台处理程序     
	        type:'post',         //数据发送方式     
	        dataType:'json',     //接受数据格式        
	        data:{
				"json":json,"lp_id":lp_id,"prod_id":prod_id
	        },
	        success:function (data){
	        	if(data.status==1){
					//alert(data.msg);
	        		document.getElementById('content').innerHTML=data.msg;
	        		$('#myal').modal('show');
				}else if(data.status==2){
					//alert(data.msg);
					document.getElementById('content').innerHTML=data.msg;
	        		$('#myal').modal('show');
				}
	        },error:function(result){
	        	//alert('系统异常,请稍后再试!');
	        	document.getElementById('content').innerHTML='系统异常,请稍后再试!';
        		$('#myal').modal('show');
	        }
	    }); 
	}
	function save(){
		
		var a = JSON.parse("{\"data\":[]}");
		var length = $('#appInfo').children().length;
		var length1 = $('#mngFeeInfo').children().length;
		for(var j=0;j<length;j++){
			var feeObj = new Object();
			
			feeObj.s_raise_date = $("#s_raise_date"+j).val();
			feeObj.e_raise_date = $("#e_raise_date"+j).val();
			feeObj.mng_fee = $("#carry"+j).val();
			feeObj.mng_module = $('#mng_module'+j).val();
			feeObj.prod_fee_id = $('#prod_fee_id'+j).val();
			a.data.push(feeObj);
		}
		
		for(var j=0;j<length1;j++){
			var feeObj = new Object();
			feeObj.s_raise_date = $("#sraise_date"+j).val();
			feeObj.e_raise_date = $("#eraise_date"+j).val();
			feeObj.mng_fee = $("#mngfee"+j).val();
			feeObj.mng_module = $('#mngmodule'+j).val();
			feeObj.prod_fee_id = $('#prodfee_id'+j).val();
			a.data.push(feeObj);
		}
		  var json=JSON.stringify(a);
		  var lp_id = $('#lp_id').val();
		  var prod_id = $('#prod_id').val();
		 $.ajax({         
	  	    url:'save_fee_mana.action',  //后台处理程序     
	        type:'post',         //数据发送方式     
	        dataType:'json',     //接受数据格式        
	        data:{
				"json":json,"lp_id":lp_id,"prod_id":prod_id
	        },
	        success:function (data){
	        	if(data.status==1){
	        		//alert()
	        		document.getElementById('content').innerHTML="修改成功";
	        		$('#myal').modal('show');
	        	}
	        },error:function(result){
	        	//alert('系统异常,请稍后再试!');
	        	document.getElementById('content').innerHTML='系统异常,请稍后再试!';
        		$('#myal').modal('show');
	        }
	    }); 
	}
	//查询该lp下是否有费用管理
	
	function lp_fee_mana(){
		var lp_id = $('#lp_id').val();
		  var prod_id = $('#prod_id').val();
		  var length = $('#length').val();
		 $.ajax({         
		  	    url:'lp_fee_mana.action',  //后台处理程序     
		        type:'post',         //数据发送方式     
		        dataType:'json',     //接受数据格式        
		        data:{
					"lp_id":lp_id,"prod_id":prod_id
		        },
		        success:function (data){
		        	$('#mngFeeInfo').empty();
		        	$('#appInfo').empty();
		        	if(data.status==2){
		        		var profit = data.lp_feeList;
		        		var manage = data.ma_feeList;
		        		for(var i=0;i<profit.length;i++){
		        			var boarddiv = "<div class='thumbnail' style='float:left;margin-left:15px;' id=test"+i+">"
		        			+"<p>开始时间：<input id=s_raise_date"+i+" name=s_raise_date"+i+" size='14' type='text' value="+profit[i].start_date+""
		        			+"	class='form_datetime'/></p>   "
		        			+"<p>结束时间：<input id=e_raise_date"+i+" name=e_raise_date"+i+" size='14' type='text' value="+profit[i].end_date+" "
		        				+"			 class='form_datetime'/></p>"
		        		    +"<p>利润提成：<input id=carry"+i+" type='text' size='14' value="+profit[i].mng_fee+" /></p>"
		        		    +"<p style='display:none;'>利润提成：<input id=mng_module"+i+" value="+profit[i].mng_module+" type='text' size='14' /></p>"
		        		    +"<p style='display:none;'>id：<input id=prod_fee_id"+i+" value="+profit[i].prod_fee_id+" type='text' size='14' /></p></div>";
		        			
		        				$("#appInfo").append(boarddiv); 
		        		}
		        		for(var i=0;i<manage.length;i++){
		        			var boarddivl = "<div class='thumbnail' style='float:left;margin-left:15px;' id=test"+i+">"
		        			+"<p>开始时间：<input id=sraise_date"+i+" name=s_raise_date"+i+" size='14' type='text' value="+manage[i].start_date+""
		        			+"	 class='form_datetime'/></p>   "
		        			+"<p>结束时间：<input id=eraise_date"+i+" name=e_raise_date"+i+" size='14' type='text' value="+manage[i].end_date+" "
		        			+"			 class='form_datetime'/></p>"
		        		    +"<p>管理费用：<input id=mngfee"+i+" type='text' size='14' value="+manage[i].mng_fee+" /></p>"
		        		    +"<p style='display:none;'>mng_module：<input id=mngmodule"+i+" value="+manage[i].mng_module+" type='text' size='14' /></p>"
		        		    +"<p style='display:none;'>id：<input id=prodfee_id"+i+" value="+manage[i].prod_fee_id+" type='text' size='14' /></p></div>";
		        			
		        				$("#mngFeeInfo").append(boarddivl); 
		        		} 
/* 		        		document.getElementById('queding').style.display="none";
		        		document.getElementById('baocun').style.display="block"; */
		        		document.getElementById('queding').style.attr=("display","hidden");
		        		document.getElementById('baocun').style.display="block";
		        	}else if(data.status==0){
		        		var count = data.count;
		        		for(var i=0;i<count;i++){
		        			var boarddiv = "<div class='thumbnail' style='float:left;margin-left:15px;' id=test"+i+">"
		        			+"<p>开始时间：<input id=s_raise_date"+i+" name=s_raise_date"+i+" size='14' type='text' value='<%=now_date %>' "
		        			+"	 class='form_datetime'/></p>   "
		        			+"<p>结束时间：<input id=e_raise_date"+i+" name=e_raise_date"+i+" size='14' type='text' value='<%=now_date %>' "
		        				+"			 class='form_datetime'/></p>"
		        		    +"<p>利润提成：<input id=carry"+i+" type='text' size='14'  /></p>"
		        		    +"<p style='display:none;'>利润提成：<input id=mng_module"+i+" value=0 type='text' size='14' /></p>"
		        		    +"<p style='display:none;'>id：<input id=prod_fee_id"+i+"  type='text' size='14' /></p></div>";
		        			
		        				$("#appInfo").append(boarddiv); 
		        		}
		        		for(var i=0;i<count;i++){
		        			var boarddivl = "<div class='thumbnail' style='float:left;margin-left:15px;' id=test"+i+">"
		        			+"<p>开始时间：<input id=sraise_date"+i+" name=s_raise_date"+i+" size='14' type='text' value='<%=now_date %>'"
		        			+"	 class='form_datetime'/></p>   "
		        			+"<p>结束时间：<input id=eraise_date"+i+" name=e_raise_date"+i+" size='14' type='text' value='<%=now_date %>' "
		        			+"			 class='form_datetime'/></p>"
		        		    +"<p>费用管理：<input id=mngfee"+i+" type='text' size='14'  /></p>"
		        		    +"<p style='display:none;'>mng_module：<input id=mngmodule"+i+" value=1  type='text' size='14' /></p>"
		        		    +"<p style='display:none;'>id：<input id=prodfee_id"+i+"  type='text' size='14' /></p></div>";
		        			
		        				$("#mngFeeInfo").append(boarddivl); 
		        		} 
/* 		        		document.getElementById('queding').style.display="block";
		        		document.getElementById('baocun').style.display="none"; */
		        		document.getElementById('queding').visible=true;
		        		document.getElementById('baocun').visible=false;
		        	}
		        	
		        },error:function(result){
		        	//alert('系统异常,请稍后再试!');
		        	document.getElementById('content').innerHTML='系统异常,请稍后再试!';
	        		$('#myal').modal('show');
		        }
		    }); 
	}
	</script>

</head>
<body>

<!-- <form id='form1'>
 <input type="button" value="addNew" onclick='addNewLine()' /> 
</form>
<form id='form2'>
</form> -->

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

<input type="hidden" id="length"  value="<%=request.getAttribute("count") %>"/>
<input type="hidden" id="prod_id" name="prod_id" value="<%=request.getParameter("prod_id")%>"/>
					<ul class="list-group">
                       <li class="list-group-item" style="color:#a52410;background-color: rgb(245,245,245);">
                       <h3 class="panel-title" style="color:#a52410;">
                       <span class="glyphicon glyphicon-plus" aria-hidden="true"  style="margin-right: 6px;"></span>
                      		 选择LP</h3>
                       </li>
                       
                       <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>LP名称&nbsp;:</span>
                      		<select id="lp_id" name="lp_id" onchange="lp_fee_mana()">  
                      		<option value="">请选择合伙企业名称</option>
                       		<s:iterator value="#request.lpList" var="lpList">
						        <option value='<s:property value="#lpList.lp_id" />'><s:property value="#lpList.partner_com_name"/></option>  
					  		</s:iterator>
					    </select> 
					    </li>            
                    </ul>
   
 	 <div id='jump' class="panel panel-default" style="width:100%;">
	           <div class="panel-heading" style="">
	              <h3 class="panel-title" style="color:#a52410;">
	               <span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>利润提成
	              </h3>
	           </div>
	           		
	           	<div class="panel-body" id="appInfo">
	           	</div>	
	           		
	            <div class="row text-center" style="margin-bottom: 30px;">
	 
	    		</div>
  	</div>
	<div id='jump' class="panel panel-default" style="width:100%;">
           <div class="panel-heading" >
              <h3 class="panel-title" style="color:#a52410;">
               <span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>管理费
              </h3>
           </div>

           <div class="panel-body">
                        <span>管理费类型&nbsp;:</span>
					    <select id="mng_type" name="mng_type">  
						       <!--  <option value="">---请选择---</option>   -->
                       		<s:iterator  value="#request.mngFeeType" var="mngFeeType">
						        <option value='<s:property value="#mngFeeType.dict_value"/>'><s:property value="#mngFeeType.dict_name"/></option>  
					  		</s:iterator>
					    </select>
				<div class="panel-body" id="mngFeeInfo">
           		</div>		    
           </div>
            <div class="row text-center" style="margin-bottom: 30px;">
		       <button id="queding" class="btn btn-lg" data-dismiss="modal" style="display:none;background-color:#5bc0de; color: #fff;" onclick="feeConfirm()">确定</button>
		       <button id="baocun" class="btn btn-lg" data-dismiss="modal" style="display:none;background-color:#5bc0de; color: #fff;" onclick="save()">保存</button>
    </div>
  </div>
</body>
</html>