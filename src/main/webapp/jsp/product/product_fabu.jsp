<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_org_add</title>
<meta name="description" content="帆茂投资">
<meta name="keywords" content="帆茂投资">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<!-- Bootstrap -->
<link href="<%=request.getContextPath() %>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
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
<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker.min.js"></script>

<script>
var n = 1;
var prod_id = <%=request.getParameter("prod_id")%>;
var prodTypeValue= "${prodList.prodTypeValue}";
var curValue= "${prodList.curValue}";
</script>
<script type="text/javascript"
src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<%
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
String now_date = df.format(new Date());
%>
<script type="text/javascript">

function newDate(obj) {
	var newTime = new Date(obj); //毫秒转换时间  
	var year = newTime.getFullYear();
	var mon = newTime.getMonth()+1;  //0~11
	var day = newTime.getDate();
	var newDate = formate(year)+'-'+formate(mon)+'-'+formate(day);
	return newDate
}


function formate(d){
	return d>9?d:'0'+d;
	}
	
	function product_fb1(){
		var ch=document.getElementsByName("cbox");
			if(document.getElementsByName("allChecked")[0].checked==true){
				for(var i=0;i<ch.length;i++){
					ch[i].checked=true;
					}
				}else{
					for(var i=0;i<ch.length;i++){
						ch[i].checked=false;
					}
				}
		}

	function product_fb(){		
		  var a = JSON.parse("{\"data\":[]}");
		    var aa = document.getElementsByName("cbox");
		var rows = document.getElementById("productFB").rows.length;
	//	var r_money = parseFloat(document.getElementById("productFB").rows[1].cells[8].innerHTML);
	    for (var i = 0; i < aa.length; i++) {
	    	
	    	
	     /*    if (aa[i].checked) {
	        	for(var k=1;k<rows;k++){			
	    			var p_money = document.getElementById("productFB").rows[k].cells[5].innerHTML;
	    			var sum_money = 0;
	    			sum_money = parseFloat(sum_money)+parseFloat(p_money);
	    		}
			} */
	    }
	
	  /*   
		if(sum_money <r_money){
			alert("回款金额没有被全部分配");return;
		}
		if(sum_money >r_money){
			alert("回款金额小于分配金额");return;
		}
		if(sum_money == 0){
			alert("分配金额不能为空");return;
		} */
		
	    for (var i = 0; i < aa.length; i++) {
	        if (aa[i].checked) {
	        	var PayObj = new Object();
	        	PayObj.order_id = document.getElementById("productFB").rows[i+1].cells[1].innerHTML; 
	        	PayObj.cust_name = document.getElementById("productFB").rows[i+1].cells[2].innerHTML;
	        	PayObj.return_date = document.getElementById("productFB").rows[i+1].cells[3].innerHTML;
	    		PayObj.return_coe = document.getElementById("productFB").rows[i+1].cells[4].innerHTML;
	    	/* 	PayObj.pllot_money = document.getElementById("productFB").rows[i+1].cells[5].innerHTML;	    	
	    		PayObj.sale_id = document.getElementById("productFB").rows[i+1].cells[6].innerHTML;
	    		PayObj.order_version = document.getElementById("productFB").rows[i+1].cells[7].innerHTML;
	    		PayObj.return_money = document.getElementById("productFB").rows[i+1].cells[8].innerHTML; */
	    		PayObj.sale_id = document.getElementById("productFB").rows[i+1].cells[5].innerHTML;
	    		PayObj.order_version = document.getElementById("productFB").rows[i+1].cells[6].innerHTML;
	    		PayObj.return_money = document.getElementById("productFB").rows[i+1].cells[7].innerHTML;
	    		
	    		a.data.push(PayObj);
	        }
	    }
	    var json=JSON.stringify(a);
		var prod_id = $("#prod_id").val();
		var prod_rs_id = $("#prod_rs_id").val();
		
		$.ajax({
			url : "/OMS/product_yfb.action",
			type : "post",
			dataType : "json",
			data :{
				"data":json,
				"prod_id":prod_id,
				"prod_rs_id":prod_rs_id
			},

			success : function(data) {
				if(data.status==1){
					 alert(data.msg);
					/* document.getElementById('content').innerHTML=data.msg;
	        		$('#myal').modal('show'); */
				}else if(data.status==2){
					alert(data.msg);
					/* document.getElementById('content').innerHTML=data.msg;
	        		$('#myal').modal('show'); */
				}
				parent.location.reload();
			},error: function() {
				var msg = escape(escape("数据异常"));
				location.href="error.action?msg="+msg
			}
		});
	} 
	
	function query_fbInfo(){
		var prod_id = $("#prod_id").val();
		var prod_rs_id = $("#prod_rs_id").val();
		var start_date = $("#start_date").val();
		var end_date = $("#end_date").val();
		
		$.ajax({
			url : "/OMS/product_fabu_detail.action",
			type : "post",
			dataType : "json",
			data :{
				"prod_id":prod_id,
				"prod_rs_id":prod_rs_id,
				"start_date":start_date,
				"end_date":end_date
			},
			success : function(data) {
				$("#fb_info").empty();
				if (data.status == 1) {
				var fbInfo = data.fabulist;
				
	        	for(var i=0;i<fbInfo.length;i++){
	        		var fb_info='<tr class="default">'
	    				+'<td><input type="checkbox" name="cbox"></td>'
	    				+'<td>'+fbInfo[i].order_no+'</td>'
	    				+'<td>'+fbInfo[i].cust_name+'</td>'
	    				+'<td>'+newDate(fbInfo[i].return_date)+'</td>'
	    				+'<td>'+fbInfo[i].return_coe+'</td>'
	    				/* +'<td contentEditable="true"></td>' */
	    				+'</tr>';
	    				$("#fb_info").append(fb_info);
	        		}
				}else if(data.status == 0){
					$("#fb_info").append(data.fbInfo);
				}
			},error: function() {
				var msg = escape(escape("数据异常"));
				location.href="error.action?msg="+msg
			}
		});
	}
</script>

<script src="<%=request.getContextPath() %>/js/bootstrap-datetimepicker.min.js"></script>
<link href="<%=request.getContextPath() %>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
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
</head>

<body>

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

  <div id='jump' class="panel panel-default" style="width:100%;">
           <div class="panel-heading" >
              <h3 class="panel-title" style="color:#a52410;">
               <span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>产品关联订单回款
             	<input type="hidden" id="prod_id" name="prod_id" value="<%=request.getParameter("prod_id")%>"/>
              </h3>
           </div>
			<table>
				<tbody>
					<tr>
						<td>
						<span>输入起止时间</span>
						<input id="start_date" name="start_date" size="14" type="text" value="<%=now_date %>" 
						 class="form_datetime" onchange="query_fbInfo()"/>
						--
						<input id="end_date" name="end_date" size="14" type="text" value="<%=now_date %>" 
						 class="form_datetime" onchange="query_fbInfo()"/>
						</td>
					</tr>
				</tbody>
			</table>	


           <div class="panel-body"><input type="hidden" id="prod_rs_id" name="prod_rs_id" value="<%=request.getParameter("prod_rs_id")%>"/>
            <table class="table cust_table" id="productFB">
              <thead>
                <tr class="demo_tr">
                    <th>
                    <input type="checkbox" name="allChecked" onclick="product_fb1()"/>
                  </th>
                  <th>
                    订单号
                  </th>
                  <th>
                    客户名称
                  </th>
                  <th>
                    回款时间
                  </th>
                  <th>
                    回款系数
                  </th>
                 <!--    <th>
                    分配金额
                  </th> -->
                 
                </tr>
              </thead>
              <tbody id="fb_info">
              <!--  <tr>
	                    <td>
	                   <input type="checkbox" name="cbox">
	                  </td>
	                  <td>32523532534</td>
	                  <td>张三</td>
	                  <td>2016-10-01</td>
	                  <td>1</td>
	                   <td>1000</td>
	                   <td>100</td>
	                   <td>100</td>
	                   <td>1000</td>
                	</tr>    -->
                  <s:iterator value="#request.fblist" var="fblist">
                
                <tr class="default">
							<td><input type="checkbox" name="cbox"></td>
							<td><s:property value="#fblist.order_no" /></td>
							<td><s:property value="#fblist.cust_name" />  <s:property value="#fblist.org_name" /></td>
							<td><s:date name="#fblist.return_date" format="yyyy-MM-dd"/></td>
							<td><s:property value="#fblist.return_coe" /></td>
							<!-- <td contentEditable="true"></td> -->
							<td style="display: none;"><s:property value="#fblist.sales_id"/></td>
							<td style="display: none;"><s:property value="#fblist.order_version"/></td>
							<td style="display: none;"><s:property value="#fblist.return_money"/></td>
				</tr>			
				</s:iterator>  
              </tbody>
            </table>          
           </div>
            <div class="row text-center" style="margin-bottom: 30px;">
      <button class="btn btn-lg" data-dismiss="modal" onclick="product_fb()"
      style="background-color:#5bc0de; color: #fff;">确定</button>
    </div>
   </div>
</body>
</html>