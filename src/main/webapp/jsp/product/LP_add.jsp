<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_demo_2</title>
<meta name="description" content="帆茂投资">
<meta name="keywords" content="帆茂投资">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<!-- Bootstrap -->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="<%=request.getContextPath()%>/css/font-awesome.min.css"
	rel="stylesheet" media="screen">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
<!--[if lt IE 9]>
  <script src="<%=request.getContextPath()%>/js/respond.min.js"></script> 
  <script src="<%=request.getContextPath()%>/js/html5shiv.min.js"></script>    
<![endif]-->
<link href="<%=request.getContextPath()%>/css/my.css" rel="stylesheet"
	media="screen">
<script src="<%=request.getContextPath()%>/js/hm.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script>
	var n = 1;
	var prod_id = $("#prod_id", parent.document).val();
	
	

	function cancel_lpAdd(){
		parent.location.reload();
	}

	function checkb(){
		var ids = new Array();
		var j = 0;
		if ($("input[name='Fruit']:checkbox:checked").length > 0) {
			box = '';
			$("input[name='Fruit']:checkbox:checked").each(function() {
				ids[j] = $(this).val();
				j = j + 1;
			})
		} 
		$('#area').val(ids);
		
	}
	function add_lp() {
		
		var f_type=$("#prod_diffcoe").val();
		var partner_com_name = $("#partner_com_name").val();
		
		if(f_type==""){
			document.getElementById('content').innerHTML="产品系数不能为空";
        		$('#myal').modal('show');
			return;
		}
		
		if(partner_com_name==""){
			document.getElementById('content').innerHTML="合伙企业名称不能为空";
        		$('#myal').modal('show');
			return;
		}
		
		
		document.getElementById("prod_id").value = prod_id;
	
		$.ajax({
			url : 'LPAdd.action', //后台处理程序     
			type : 'post', //数据发送方式     
			async:  false,
			dataType : 'json', //接受数据格式        
			data:$('#form1').serialize(),
				//'json':json,
			success : function(data) {
				if (data.status == 1) {
					alert(data.msg);
					/* document.getElementById('content').innerHTML="data.msg";
        			$('#myal').modal('show'); */
					parent.location.reload();
					//alert("走没走");
				}else if(data.status ==2){
					alert(data.msg);
					/* document.getElementById('content').innerHTML=data.msg;
	        		$('#myal').modal('show'); */
				}
			},error:function(result){
				//alert('系统异常,请稍后再试!');
				document.getElementById('content').innerHTML='系统异常,请稍后再试!';
        		$('#myal').modal('show');
			}
		});
		
	}

	function add_attr() {
		var prod_prop_name = $("#prod_prop_name").val();
		var prod_prop_desc = $("#prod_prop_desc").val();
		
		var attr_info ='<tr class="default" id="cust_list production_list">'
		+'<td>'+prod_prop_name+'</td>'
		+'<td>'+prod_prop_desc+'</td>'
		+'</tr>';
		
		if(prod_prop_name==""){
			//alert("特有属性名称为空");
			document.getElementById('content').innerHTML="特有属性名称为空";
        		$('#myal').modal('show');
			return;
		}
		
		if(prod_prop_desc==""){
			//alert("特有属性值为空");
			document.getElementById('content').innerHTML="特有属性值为空";
        		$('#myal').modal('show');
			return;
		}
		
		$("#special_attr").append(attr_info);	
	}
	
</script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/m.js" charset="UTF-8"></script>
<script type="text/javascript">

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

	<form name="form1" id="form1" method="post">
		<ul class="list-group">
			<li class="list-group-item"
				style="background-color: rgb(245, 245, 245);">
				<h3 class="panel-title" style="color: #a52410;">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"
						style="margin-right: 6px;"></span> 添加合伙企业
				</h3>
			</li>

			<li class="list-group-item">

				<h4 style="font-weight: bold;">合伙企业基本信息</h4>

			</li>

			<li class="list-group-item"><!-- <span
				style="margin-right: 4px; float: left; width: 46%;"> GP名称<input
					type="text" id="gp_name" name="gp_name"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 274px;">
			</span> --> 
			
			<span  style="margin-right: 4px; float: left; color:red;">*</span><span style="margin-right: 4px; float: left; width: 46%;">合伙企业名称<input
					id="partner_com_name" name="partner_com_name" type="text" required="required"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 240px;"></span>
<span style="margin-right: 4px;">
					基金类别<!-- (字典值 fundDict)<input type="text" id="fund_type" name="fund_type"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 215px;">
					 -->
					<select id="fund_type" name="fund_type">  
						        <option value="">---请选择---</option>  
                       		<s:iterator  value="#request.fundDict" var="fundDict">
						        <option value='<s:property value="#fundDict.dict_value"/>'><s:property value="#fundDict.dict_name"/></option>  
					  		</s:iterator>
					 </select>
			</span>
			</li>
		<!-- 	<li class="list-group-item" style="border-top: 1px solid #f2f2f2;">

				 
			</li> -->
			<li class="list-group-item"
				style="border-top: 1px solid #f2f2f2; border-bottom: 0;">
				
				<span style="color:red;margin-right: 4px;float: left;">*</span><span
				style="margin-right: 4px; float: left; width: 46%;"> 产品系数<input
					type="text" name="prod_diffcoe" id="prod_diffcoe" required="required"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 265px;">
					<span>%</span>
			</span> 
			<input id="area" name='area' type="hidden"/>
			<span id="arealist" style="margin-right: 4px;">地区：
			
				<s:iterator  value="#request.arealist" var="arealist">
					<input onclick='checkb()' name='Fruit' type='checkbox'
					 value='<s:property value="#arealist.dict_value"/>' /><s:property value="#arealist.dict_name"/>  
				</s:iterator>
				
			</span>
			<%-- <select id="see_sale" name="see_sale">
					<option value="2">否</option>
					<option value="1">是</option>
				</select> --%>
			</li>
			<li class="list-group-item"
				style="border-top: 1px solid #f2f2f2; border-bottom: 0;">
			
			<span
				style="margin-right: 4px; float: left; width: 46%;"> 营业执照号<input
					type="text" name="bus_license" id="bus_license"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 252px;">
			</span> 
			<span style="margin-right: 4px;">法定代表人<input
					name="legal_resp" id="legal_resp" type="text"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 252px;"></span>
			</li>
			<li class="list-group-item" style="border-top: 1px solid #f2f2f2;">

				<span style="margin-right: 4px; float: left; width: 46%;">
					合伙企业开户行1<input id="open_bank" name="open_bank" type="text"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 200px;">
			</span> 
			<span style="margin-right: 4px;">合伙企业银行账户1<input
					 type="text" id="bank_account" name="bank_account"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 214px;"></span>
			</li>

			<li class="list-group-item" style="border-top: 1px solid #f2f2f2;">
				<span style="margin-right: 4px; float: left; width: 46%;">
					合伙企业开户行2<input id="open_bank1" name="open_bank1"  type="text"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 200px;">
			</span> 

				<span style="margin-right: 4px;">合伙企业银行账户2<input
					type="text" id="bank_account1" name="bank_account1"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 214px;"></span>
			</li>
			<li class="list-group-item" style="border-top: 1px solid #f2f2f2;">
			
			<span style="margin-right: 4px; float: left; width: 46%;">
					私募基金管理人登记编号<input name="fund_no" id="fund_no" type="text"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 208px;">
			</span>
			<span style="margin-right: 4px;">注册地址<input id="regit_addr"
					name="regit_addr" type="text"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 265px;"></span>
			</li>
			
			
			<li class="list-group-item"
				style="border-top: 1px solid #f2f2f2; border-bottom: 0;">
			<span style="margin-right: 4px;">备注<input
					name="prod_remark" id="prod_remark" type="text"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 295px;"></span>
			</li>

<%-- 			<li class="list-group-item">

				<h5 style="font-weight: bold;">
					特有属性
					<span style="float: right; margin-right: 10%;"><!-- <button onclick="pldr()">批量导入</button> -->
					</span>
				</h5>

			</li>
			<li class="list-group-item" style="border-top: 1px solid #f2f2f2;">

				<span style="margin-right: 4px; float: left; width: 46%;">
					属性名称<input id="prod_prop_name" name="prod_prop_name" type="text"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 200px;">
			</span> <span style="margin-right: 4px;">内容<input id="prod_prop_desc"
					name="prod_prop_desc" type="text"
					style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd; width: 265px;"></span>
				<a onclick="add_attr()">添加</a>

				<div class="panel panel-default">
					<div class="panel-body">
						<table class="table" id="spec_attr">
							<tbody id="special_attr">
							<!-- <tr>
								<td width="45%"></td>
								<td></td>
							</tr> -->
							</tbody>
						</table>
					</div>
				</div>
			</li>
 --%>

			<li class="list-group-item">

				<h4 style="font-weight: bold;">合同主要条款</h4>

			</li>

		<li class="list-group-item" style="border-top: 1px solid #ddd;"><span>基金筹备费/开办费 </span>
			<input id="fund_start_fee" name="fund_start_fee" type="text"
			style="border: none; width: 80%;  outline: none;">
		</li>

		<li class="list-group-item" style="border-top: 1px solid #ddd;">基金收益分配 
			<input id="fund_bnf_allot" name="fund_bnf_allot" type="text"
			style="border: none; width: 80%; outline: none;">
		</li>

		<li class="list-group-item" style="border-top: 1px solid #ddd;">合伙企业费用
			<input id="cor_com_fee" name="cor_com_fee"  type="text"
			style="outline: none; margin-left: 5px; border: none; border-bottom: 1px solid #ddd;width:600px;">
			 
		</li>

		<li class="list-group-item" style="border-top: 1px solid #ddd;">基金份额类别
			<input id="fund_type_fee" name="fund_type_fee" type="text"
			style="border: none; width: 80%; outline: none;">
		</li>


		<li class="list-group-item" style="border-top: 1px solid #ddd;">GP/LP认缴要求
			<input id="gplp_pay" name="gplp_pay"  type="text"
			style="border: none; width: 80%; outline: none;">
		</li>

		<li class="list-group-item" style="border-top: 1px solid #ddd;">缴付出资
			<input id="true_pay" name="true_pay"  type="text"
			style="border: none; width: 80%; outline: none;">
		</li>

		<li class="list-group-item" style="border-top: 1px solid #ddd;">首次交割日
			<input id="first_delivery" name="first_delivery"  type="text"
			style="border: none; width: 80%; outline: none;">
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">存续期
		<input type="text" id="renew_year" name="renew_year" maxlength="6" style="width: 9%;"/>
			
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">存续期描述
		<input id="renew_period" name="renew_period" type="text"
			style="border: none; width: 80%; outline: none;">
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">投资期
		<input type="text" id="invest_year" name="invest_year" maxlength="6" style="width: 9%;"/>
			
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">投资期描述
		<input id="invest_period" name="invest_period" type="text"
			style="border: none; width: 80%; outline: none;">
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">管理费描述
		<input type="text" id="mana_decr" name="mana_decr"  style="border:none;width: 80%; outline: none;"/>
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">认购费描述
		<input type="text" id="buy_decr" name="buy_decr"  style="border:none;width: 80%; outline: none;"/>
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">滞纳金描述
		<input type="text" id="delay_decr" name="delay_decr"  style="border:none;width: 80%; outline: none;"/>
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">延迟入伙利息描述
		<input type="text" id="late_join_decr" name="late_join_decr"  style="border:none;width: 80%; outline: none;"/>
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">投资状态
			<!-- <input id="invest_goal" name="invest_goal" type="text"
			style="border: none; width: 80%; outline: none;"> -->
					<select id="invest_goal" name="invest_goal">  
						        <option value="">---请选择---</option>  
                       		<s:iterator  value="#request.investDict" var="investDict">
						        <option value='<s:property value="#investDict.dict_value"/>'><s:property value="#investDict.dict_name"/></option>  
					  		</s:iterator>
					 </select>
		</li>
		<li class="list-group-item" style="border-top: 1px solid #ddd;">投资状态描述
		<input type="text" id="invest_goal_decr" name="invest_goal_decr"  style="border:none;width: 80%; outline: none;"/>
		</li>
		</ul>
		<!--主体-->

		<div class="row text-center">
			<button onclick="cancel_lpAdd()" style="margin-right: 40px;" 
				class="btn btn-lg btn-default">取消</button>
			<button onclick="add_lp()" class="btn btn-lg" data-dismiss="modal"
				style="background-color: #5bc0de; color: #fff;">添加</button>
		</div>
		<input id="prod_id" name="prod_id" type="hidden">
	</form>
	<!--end 右-->
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
</body>
</html>