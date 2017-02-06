<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html >
<head>
	<meta charset="UTF-8">
	<title>index</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
</head>
<body>
	<!-- 下拉菜单 -->
<%@ include file="jsp/header.jsp"%>  
<div style="width:80%;margin:0 auto;overflow: hidden;height: 550px;">
	<a href="<%=request.getContextPath() %>/jsp/cust/cust_list.jsp"><img src="<%=request.getContextPath() %>/image/index_4.png" alt="" style="width: 100%;height: 100%;"></a>
</div>
<div id="footer" data-viewgroup="fullScreenLayers" data-viewid="fsl-1"><div class="container"><div class="row"><div style="margin: 20px 0; font-size: small;" class="col-md-6 text-muted"><p class="text-left">Copyright © 2014 帆茂投资管理有限公司</p></div><div style="margin: 20px 0; font-size: small;" class="col-md-6 text-muted"><p class="text-right">帮助</p></div></div></div></div>
</body>
</html>