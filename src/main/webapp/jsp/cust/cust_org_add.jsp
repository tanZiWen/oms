<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_org_add</title>
<meta name="description" content="帆茂投资">
<meta name="keywords" content="帆茂投资">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<!-- Bootstrap -->
<%@ include file="/jsp/header.jsp"%>
<script type="text/javascript">
	$(function() {
		$(".form_datetime").datetimepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			startView : 2,
			minView : 2,
			forceParse : false,
			language : 'zh-CN'
		});
	});
</script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
	<!--nav-->





	<!--主体-->

	<div class="container m0 bod top70" id="zt">
		<div class="row">
			<div class="col-md-6 t0b0 ">
				<ol class="breadcrumb t0b0">
					<li><a href="<%=request.getContextPath()%>/jsp/index.jsp"><i
							class='fa  fa-home'></i>&nbsp首页</a></li>
					<li><a
						href="/OMS/org.action">机构客户管理</a></li>
					<li>新建机构</li>
				</ol>
			</div>
			<div class="col-md-6 t0b0 txtr"></div>
		</div>

		<div class="row top10 mym">
			<!--左-->
			<!--           
<div class="col-md-4 myleft" style="width: 25%;">
    <div class="myleft-n">
       
         <a href="http://www.jq22.com/myhome#" data-toggle="modal" data-target="#exampleModal2">
            <img id="tou" src="../../image/person.png" class="f imgr20">
         </a>   
        <div class="f imgf20">
            <h4>个人-zhangbaiwei</h4>
            
        </div>

        <div class="df"></div>
    </div>
    <div class="df"></div>
    <div class="myleft-n">

        <div class="alert alert-warning top20" role="alert" style="background-color:#fefcee;padding-top:7px;padding-bottom:7px">
         <i class="fa fa-lightbulb-o"></i> 个人介绍 <br>
         
        </div>

        <ul class="list-group" id="list-grop">
            <li class="list-group-item" style="background-color: rgb(245,245,245);">

                <a style="display:inline-block;width: 100%;height:100%;border:none;outline: none;text-align: center;text-decoration: none;color:#999;" href="#">客户资料</a>
               
            </li>
             <li class="list-group-item">

                <a style="display:inline-block;width: 100%;height:100%;border:none;outline: none;text-align: center;text-decoration: none;color:#999;" href="#">公司</a>
               
            </li>
                <li class="list-group-item">

                <a style="display:inline-block;width: 100%;height:100%;border:none;outline: none;text-align: center;text-decoration: none;color:#999;" href="#">家族</a>
               
            </li>

        </ul>


    </div>
    <div class="df"></div>
</div> -->

			<!--end 左-->
			<!--右-->
			<form id="formid" method='post' action="addorg.action">
				<div class="col-md-8 myright" style="width: 100%; margin: 0 auto;">
					<!-- 1 -->
					<div class="myright-n">
						<div class="myNav row" style="border-bottom: 1px solid #a52410;">
							<!-- <a href="#"><i class="glyphicon glyphicon-floppy-saved"></i> 保存 </a> -->
							<h4 style="color: #a52410;">机构客户信息添加</h4>

						</div>

					</div>
					<!-- 2 -->
					<div class="panel panel-default" style="width: 100%;">


						<!--         <div class="report_search">
                       <div class="report_label">
                         <label>
                                 按电话号码查询
                           </label>
                       </div>
                    <div class="report_input" >
                      <input type="text" class="form-control" style="width:60%">
                               <span class="report_btn input-group-btn">
                               <button data-toggle="modal" type="submit" id="queryQuick" class="btn btn-primary m-l-5"       style="outline: none;height: 34px;">
                               <span style="margin-right: 5px;" class="glyphicon glyphicon-search"></span>
                               </button>
                                
                               </span>
                    </div>
            </div>
                   -->

						<ul class="list-group">

							<li class="list-group-item"
								style="background-color: rgb(245, 245, 245); font-weight: bold;">
								机构客户基本资料</li>
							<li class="list-group-item"><span
								style="color: red; margin-right: 4px; float: left;">*</span> <span>公司名称&nbsp;:</span>
								<input name="org_name" id="org_name" maxlength="40" type="text"
								style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><span
								style="color: red; margin-right: 4px; float: left;">*</span> <span>
									营业执照注册号(统一社会信用代码)&nbsp;:</span> <input name="org_license"
								id="org_license"  required="required" 
								style="width: 15%; border: none; outline: none;"></li>
							<li class="list-group-item"><span
								style="color: red; margin-right: 4px; float: left;">*</span> <span>法人&nbsp;:</span>
								<input name="org_legal" id="org_legal" maxlength="30"
								type="text" style="width: 95%; border: none; outline: none;">
							</li>
							<li class="list-group-item"><span>公司类型&nbsp;:</span> <input
								name="org_type" id="org_type" type="text"
								style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><span>组织机构代码证&nbsp;:</span> <input
								name="org_code_cert" id="org_code_cert" required="required" type="number"
								style="width: 15%; border: none; outline: none;"></li>
							<li class="list-group-item"><span>税务登记号&nbsp;:</span> <input
								name="taxid" id="taxid" required="required" type="number"
								style="width: 15%; border: none; outline: none;"></li>
							<li class="list-group-item"><span>注册资本&nbsp;:</span> <input
								name="reg_capital" id="reg_capital" required="required" type="number"
								style="width: 15%; border: none; outline: none;"></li>
							<li class="list-group-item"><span>注册地址&nbsp;:</span> <input
								name="reg_address" id="reg_address" type="text"
								style="width: 90%; border: none; outline: none;"></li>
							<li class="list-group-item"><span>成立日期&nbsp;:</span> <input
								class="form_datetime" name="reg_date" id="reg_date" type="text"
								style="width: 90%; border: none; outline: none;" /></li>
							<li class="list-group-item">
								<!-- <input size="16" type="text" value="2012-06-15" readonly class="form_datetime"> -->
								<span>经营期限&nbsp;:</span> <input class="form_datetime"
								name="opera_period" id="opera_period" type="text"
								style="width: 90%; border: none; outline: none;">
							</li>



						</ul>
					</div>
					<!-- 3 -->
					<!--  -->
					<div class="panel panel-default" style="width: 100%;">
						<div class="panel-heading">
							<h3 class="panel-title" style="color: #a52410;">
								<span class="glyphicon glyphicon-th" aria-hidden="true"
									style="margin-right: 6px;"></span>合伙人

							</h3>
						</div>
						<!-- 查询 -->
						<div class="report_search">
							<div class="report_label">
								<label> 按姓名/电话查询 </label>
							</div>
							<div class="report_input">
								<input id="condition" type="text" class="form-control"
									style="width: 60%"> <span
									class="report_btn input-group-btn"> <a
									href="javascript:queryorg()" id="queryQuick"
									class="btn btn-primary m-l-5"
									style="outline: none; height: 34px; background-color: #5bc0de; border-color: #5bc0de;">
										<span style="margin-right: 5px;"
										class="glyphicon glyphicon-search"></span>
								</a> <!-- <button class="btn btn-default" type="button">
                                查找
                            </button> -->
								</span>
							</div>
						</div>

						<div class="panel-body">
							<table id="playlistTable" class="table cust_table">
								<thead>
									<tr class="demo_tr">
										<th>选择</th>
										<th>合伙人</th>
										<th>配对RM</th>
										<th>认缴金额</th>
										<th>占比</th>

									</tr>
								</thead>
								<tbody id="partner">

								</tbody>
							</table>
							<!--  <ul class="pagination pagination-centered" >
              <li><a href="#">&laquo;</a></li>
              <li><a href="#">1</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">&raquo;</a></li>
            </ul> -->
						</div>
					</div>
 <div class="row text-center" style="margin-bottom: 30px;">
      <a  data-dismiss="modal"
            href="orderList.action" class="btn btn-lg btn-default">取消</a>
      <input type="button" value="保存" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addorg('1')" >
      <input type="button" value="提交审批" class="btn btn-lg" data-dismiss="modal" onclick="addorg('2')" style="background-color:#5bc0de; color: #fff;" />
    </div>
					<!-- <div class="modal-footer" style="border: none;">
						<div class="row text-center">
							 <button style="margin-right: 40px;" data-dismiss="modal" class="btn btn-lg btn-default">取消</button>
							<input value="提交" type="button" class="btn btn-lg"
								data-dismiss="modal" onclick="addorg()"
								style="background-color: #5bc0de; color: #fff;" />
						</div>
					</div> -->
				</div>
			</form>
			<!--end 右-->




			<!--底部-->
			<nav class="foot navbar-inverse navbar-fixed-bottom">
				<ul class="list-inline">
					<li class="footer-ss"><a href="javascript:void(0)"
						data-container="body" data-toggle="popover" data-placement="top"
						data-html="true" data-content=" " data-original-title="" title="">更多
							&nbsp;;<i class="fa fa-angle-down"></i>
					</a></li>
					<li class="footer-ss">在线反馈</li>
					<li class="footer-ss">帮助中心</li>
					<li>Copyright © 2016</li>
				</ul>
				<ul class="list-inline text-right">
					<li></li>
				</ul>
			</nav>

			<script>
				var _hmt = _hmt || [];
				(function() {
					var hm = document.createElement("script");
					hm.src = "//hm.baidu.com/hm.js?b3a3fc356d0af38b811a0ef8d50716b8";
					var s = document.getElementsByTagName("script")[0];
					s.parentNode.insertBefore(hm, s);
				})();
			</script>


			<!--end底部-->
			<script src="/OMS/js/org.js"></script>
			<script type="text/javascript">
				function tx() {
					$.ajax({
						type : "POST",
						url : "myPhotoSave.aspx",
						data : "tx=" + myFrame.window.tx,
						success : function(msg) {
							if (msg != "n") {
								$("#tou").attr('src', "tx/" + msg + ".png");
							}
						}
					});

				}
				function clockms(id) {
					var yz = $.ajax({
						type : 'post',
						url : 'mess.aspx',
						data : {
							id : id
						},
						cache : false,
						dataType : 'text',
						success : function(data) {

						},
						error : function() {
						}
					});
				}
			</script>

			<!-- 左边变色特效 -->
			<script>
				window.onload = function() {
					var list_grop = document.getElementById('list-grop');
					var list_li = list_grop.getElementsByTagName('li');

					for (var i = 0; i < list_li.length; i++) {
						// list_li[i].index=i;
						list_li[i].onclick = function() {

							for (var j = 0; j < list_li.length; j++) {
								list_li[j].style.background = 'white';
							}
							this.style.background = '#ace1f1';

						}
					}
				}
			</script>




		</div>
</body>
</html>