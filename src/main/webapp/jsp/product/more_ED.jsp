<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_demo_2</title>
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

<body >
     <div id='jump' class="panel panel-default" style="width:100%;">
         
            <div class="panel-heading">
              <h3 class="panel-title" style="color:#a52410;position: relative;"><span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>上海额度进度明细
       
              </h3>             
            </div>
           <div class="panel-body">
              
            <table class="table cust_table">
            <thead >
            <tr class="demo_tr">
            <th >地区</th> 
            <th>预期成交金额</th>
            <th>已成交金额</th>
           <!--  <th>录入时间</th> -->
            </tr>
            </thead>
            <tbody>
			<s:iterator value="#request.moreEdDetail" var="moreEdDetail">
						<tr class="default">
							<td><s:property value="#moreEdDetail.dict_name" /></td>
							<td><s:property value="#moreEdDetail.cust_money" /></td>
							<td><s:property value="#moreEdDetail.pay_amount" /></td>
						</tr>
			</s:iterator>

            <!-- <tr class="default" id="cust_list production_list">
            <td>上海</td>
            <td>200万</td>
            <td>200万</td>
            <td>2016-1-1</td>
            </tr> -->
            </tbody>
            </table>
           
           </div>
        </div>

      <div class="panel panel-default" style="width:100%;">
         
       
           <div class="panel-body">
              
            <table class="table cust_table">
            <thead >
            <tr class="demo_tr">
           <!--  <th >产品名称</th> 
            <th>分公司</th> -->
            <th>理财经理</th>
            <th>客户姓名</th>
           <!--  <th>状态</th> -->
            <th>金额</th>
            <th>情况备注</th>
            
           
            </tr>
            </thead>
            <tbody>

            <s:iterator value="#request.moreEdDetail" var="moreEdDetail">
						<tr class="default" id="cust_list production_list">
							<td><s:property value="#moreEdDetail.sale_name" /></td>
							<td><s:property value="#moreEdDetail.cust_name" /></td>
							<td><s:property value="#moreEdDetail.cust_money" /></td>
							<td><s:property value="#moreEdDetail.remarks" /></td>
						</tr>
			</s:iterator>
            <!-- <tr class="default" id="cust_list production_list"> 
            <td>上海</td>
            <td>200万</td>
            <td>200万</td>
            <td>2016-1-1</td>
            <td>*</td>
            <td>*</td>
            <td>*</td> 
            </tr>-->
            
            </tbody>
            </table>
           
           </div>
        </div>       


                

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

</body></html>