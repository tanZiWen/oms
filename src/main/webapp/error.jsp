<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html >
<head>
<!--/jsp/product/product.jsp -->
	<meta charset="UTF-8">
	<title>error</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
</head>
<body>
<div style="width: 100%;margin:0 auto;height: 100%;position: relative;">
<img style="width: 100%;" src="image/404.png" alt="">
<div style="width: 30%;height: 200px;position: absolute;bottom: 0;left:35%;">

<%-- <s:if test='flag=="true"'><div>1</div>
</s:if>   
<s:else></s:else> --%>


<h5></h5>


</div>
<div style="width: 30%;position: absolute;bottom:150px;left:66%;">
	<a href="<%=request.getContextPath() %>/jsp/index.jsp" data-toggle="modal" type="submit" id="queryQuick" class="btn btn-primary m-l-5" style="outline: none;"><i style="margin-right: 5px;" class="glyphicon glyphicon-share-alt"></i><span style="font-size: 14px;">返回</span></a>
</div>
 
</div>
</body>
</html>