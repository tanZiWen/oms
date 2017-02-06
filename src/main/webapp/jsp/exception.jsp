<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>exception</title>
	<style>	

	</style>
</head>
<body style="background: url(image/404_1.png) no-repeat;background-size: cover;">
	<div style="margin-top: 9%;width: 200px;margin-left: 15%;
	color: #222;font-family: '微软雅黑';">
		<h1 style="font-weight: normal;">访问页面异常</h1>
		<p style="color: #70a4b2;font-size: 22px;line-height: 18px;">错误code:${code } </p>
		<p style="color: #70a4b2;font-size: 22px;line-height: 18px;">${msg } </p>
		<!-- <p style="color: #222;font-size: 18px;line-height: 18px;font-weight: bold;">或者</p> -->
		<p style="line-height: 18px;"><a href="${url }" style="color: #70a4b2;font-size: 18px;text-decoration: none;" >返回</a></p>
	</div>
</body>
</html>