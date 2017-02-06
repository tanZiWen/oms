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

<body>
 
  <div id='jump' class="panel panel-default" style="width:100%;">
           <div class="panel-heading" >
              <h3 class="panel-title" style="color:#a52410;">
               <span class="glyphicon glyphicon-th" aria-hidden="true"  style="margin-right: 6px;"></span>产品关联订单回款
             
              </h3>
           </div>

           <div class="panel-body">
            <table class="table cust_table">
              <thead>
                <tr class="demo_tr">
                  
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
                <!--     <th>
                    分配金额
                  </th> -->
                 
                </tr>
              </thead>
              <tbody>
                 <s:iterator value="#request.yfabulist" var="yfabulist">
                <tr class="default">
							<!-- <td><input type="checkbox" name="cbox"></td> -->
							<td><s:property value="#yfabulist.order_id" /></td>
							<td><s:property value="#yfabulist.cust_name" /></td>
							<td><s:date name="#yfabulist.return_date"  format="yyyy-MM-dd"/></td>
							<td><s:property value="#yfabulist.return_coe" /></td>
							<%-- <td><s:property value="#yfabulist.pllot_money" /></td> --%>
						<!-- 	<td contentEditable="true"></td> -->
				</tr>
				</s:iterator>
              </tbody>
            </table>
           </div>
            <div class="row text-center" style="margin-bottom: 30px;">
     <!--  <button class="btn btn-lg" data-dismiss="modal" style="background-color:#5bc0de; color: #fff;">确定</button> -->
    </div>
        </div>

</body>
</html>
           <!--end 右-->
  
                






