var statusVal;
function cust_select() {
	//var state = $('#cust_type > .btn.active').val();
	var tel = $('#tel').val();
	$.ajax({
		url : "/OMS/orderCust.action",
		type : "post",
		dataType : "json",
		data : {
			
			"tel" : tel
		},
		success : function(data) {
			$('#tbody').empty();
			if (data.status == 1) {
				var lis = data.list;

				for (var i = 0; i < lis.length; i++) {
					var tbody = '<tr class="default" >'
						+ '<td style="display:none">'+ lis[i].cust_id + '</td>'
						+ '<td>'+ lis[i].cust_name + '</td>'
						+ '<td>'+ lis[i].cust_cell + '</td>'
						+ '<td>'+ lis[i].sales_name + '</td>'
						+ '</tr>';
					$('#tbody').append(tbody);
					$("#cust_no").val(lis[i].cust_id);
					$("#cust_name").val(lis[i].cust_name);
				}
				$('#cust_type').val('1');
				// var a = $('#pagenum').innerText();
				// alert(a);
			}else{
				$("#cust_no").val('');
				$("#cust_name").val('');
				document.getElementById('content').innerHTML="未找到相关数据";
				$('#myal').modal('show');
			}
		},
		error : function() {
			var msg = escape(escape("數據異常"));
			location.href = "error.action?msg=" + msg
		}

	});
	
}
	  
function org_select() {
	//var state = $('#cust_type > .btn.active').val();
	var tel = $('#tel1').val();
	$.ajax({
		url : "/OMS/orderOrg.action",
		type : "post",
		dataType : "json",
		data : {
			
			"tel" : tel
		},
		success : function(data) {
			$('#tbody1').empty();
			if (data.status == 1) {
				var lis = data.list;
				var sals_list = data.parner_list;
				for (var i = 0; i < lis.length; i++) {
					var tbody = '<tr class="default" >'
						+ '<td style="display:none">'+ lis[i].org_id + '</td>'
						+ '<td>'+ lis[i].org_name + '</td>'
						+ '<td>'+ lis[i].org_license + '</td>'
						+ '<td>'+ lis[i].org_legal + '</td>'
						+ '</tr>';
					$("#cust_no").val(lis[i].org_id);
					
					$("#cust_name").val(lis[i].org_name);
					$('#tbody1').append(tbody);
				}
				$('#cust_type').val('2');
				// var a = $('#pagenum').innerText();
				// alert(a);
				
			}else{
				
				$("#cust_no").val('');
				$("#cust_name").val('');
				document.getElementById('content').innerHTML="未找到相关数据";
				$('#myal').modal('show');
			}
		},
		error : function() {
			var msg = escape(escape("數據異常"));
			location.href = "error.action?msg=" + msg
		}

	});
	
}
function cust_org_select(){
	var cust_type=$('#cust_type').val();
	var cust_no = $('#cust_no').val();
	$.ajax({
		url : "cust_org_select.action",
		type : "post",
		dataType : "json",
		data : {
			"cust_type" : cust_type,
			"cust_no":cust_no
		},
		success : function(data) {
			$('#tbody_sale').empty();
			if (data.desc == 1) {
				var lis = data.list;
				var sals_list = data.parner_list;
				
				// var a = $('#pagenum').innerText();
				// alert(a);
				var achievment ;
				
				if($('#acount_fee').val().trim()!=""){
					achievment = $('#acount_fee').val();
				}else{
					
					achievment = $('#pri_fee').val();
				}
				for (var i = 0; i < sals_list.length; i++) {
					var tbody = '<tr class="default" >'
						+ '<td style="display:none">'+ sals_list[i].sales_id + '</td>'
						+ '<td><input  name="check" type="checkbox" /></td>'
						+ '<td>'+ sals_list[i].match_rm + '</td>'
						+ '<td class="a" contentEditable="true">'+ achievment + '</td>'
						+ '<td class="b" >'+ sals_list[i].group_point + '</td>'
						+ '<td style="display:none">'+ sals_list[i].cust_id + '</td>'
						+ '</tr>';
					
					$('#tbody_sale').append(tbody);
				}
			}else{
				
				
			}
		},
		error : function() {
			var msg = escape(escape("數據異常"));
			location.href = "error.action?msg=" + msg
		}

	});
}
function order_insert(is_checked) {
	

	var btn =document.getElementById('submiting');
	btn.disabled='disabled';
	var time = $('#create_time').val().trim();
	if(time==""){
		alert('请选择录入时间');
		btn.disabled=false;
		return;
	}
	var a = JSON.parse("{\"data\":[]}");
	var pay = new Object();
	var aa = document.getElementsByName("check");
    for (var i = 0; i < aa.length; i++) {
        if (aa[i].checked) {
        	pay.is_id = 0;
        }else{
        	pay.is_id = 1;
        }
       
    }
    pay.cust_type = $('#cust_type > .btn.active').val().trim() ;
    pay.cust_name = $('#cust_name').val().trim();
	pay.order_type = $('#order_type').val().trim();
	pay.order_type_name=$('#order_type').find("option:selected").text()
	pay.order_amount = $('#order_amount').val().trim().replaceAll(",","");
	pay.area = $('#area').val().trim();
	pay.area_name=$('#area').find("option:selected").text();
	pay.part_comp = $('#part_comp').val();
	if(!pay.part_comp){
		alert('请选择合伙企业');
		btn.disabled=false;
		return;
	}
	pay.part_comp_name=$('#part_comp').find("option:selected").text();
	pay.contract_type = $('#contract_type').val().trim();
	pay.contract_no = $('#contract_no').val().trim();
	pay.is_checked = is_checked;
	pay.id_no = $('#id_no').val().trim();
	pay.pri_fee = $('#pri_fee').val().trim().replaceAll(",","");
	pay.acount_fee = $('#acount_fee').val().trim().replaceAll(",","");
	pay.buy_fee = $('#buy_fee').val().trim().replaceAll(",","");
	pay.start_fee = $('#start_fee').val().trim().replaceAll(",","");
	pay.delay_fee = $('#delay_fee').val().trim().replaceAll(",","");
	pay.break_fee = $('#break_fee').val().trim().replaceAll(",","");
	pay.late_join_fee = $('#late_join_fee').val().trim().replaceAll(",","");
//	pay.remark = $('#remark').val().trim();
	pay.bank_no = $('#bank_no').val().trim();
	pay.bank_card = $('#bank_card').val().trim();
	pay.prod_no = $('#prod_no').val().trim();
	pay.prod_no_name=$('#prod_no').find("option:selected").text();
	pay.create_time = $('#create_time').val().trim();
	pay.prod_diffcoe = $('#prod_diffcoe1').val().trim();
	pay.first_pay_time = $('#first_pay_time').val().trim();
	if(pay.first_pay_time == "") {
		alert('请选择首次缴款日期');
		btn.disabled=false;
		return;
	}
	pay.mail_address = $('#mail_address').val().trim();
	pay.work_address = $('#work_address').val().trim();
	pay.cust_email = $('#cust_email').val().trim();
	pay.cust_cell = $('#cust_cell').val().trim();
	pay.comment = $('#comment').val().trim();
	pay.extra_award = $('#extra_award').val().trim();
	pay.cust_address = $('#cust_address').val().trim();
	pay.investor_no = investor_no;
	var rows = document.getElementById("playlistTable").rows.length; // 获得行数(包括thead)
	var colums = document.getElementById("playlistTable").rows[0].cells.length; // 获得列数
	if(rows<=1){
		document.getElementById('content').innerHTML="未找到相关销售信息，不能添加订单";
		$('#myal').modal('show');
		btn.disabled=false;
		return ;
	}
	
	pay.cust_no = $('#cust_no').val();
	var rows1 = document.getElementById("list").rows.length; // 获得行数(包括thead)
	var colums1 = document.getElementById("list").rows[0].cells.length; // 获得列数
	//alert(rows1);
	/*for (var i = 1; i < rows1; i++) { // 每行
		var b = new Object();
		pay.cust_no = $('#cust_no').val();//document.getElementById("list").rows[i].cells[0].innerHTML;
		pay.cust_name = document.getElementById("list").rows[i].cells[1].innerHTML;
		//a.data.push(b);
	}*/
	a.data.push(pay);
	var sale_status=0;
	for (var i = 0; i < aa.length; i++) { // 每行
		 if (aa[i].checked) {
			var b = new Object();
			b.sales_id = document.getElementById("playlistTable").rows[i+1].cells[0].innerHTML;
			b.sales_name = document.getElementById("playlistTable").rows[i+1].cells[2].innerHTML;
			b.achievement = document.getElementById("playlistTable").rows[i+1].cells[3].innerHTML.replaceAll(",","");
			b.achievementpoint = document.getElementById("playlistTable").rows[i+1].cells[4].innerHTML;
			b.cust_id = document.getElementById("playlistTable").rows[i+1].cells[5].innerHTML;
			b.distribution = $('#ms > .btn.active').val().trim() ;
			a.data.push(b);
			sale_status=1;
		}
		//a.data.push(b);
	}
	if(sale_status==0){
		alert("未找到销售信息，请勾选");
		btn.disabled=false;
		return;
	}
	
	var obj = JSON.stringify(a);
	showLoad(); 
	$.ajax({
		url : "/OMS/orderInsert.action",
		type : "post",
		dataType : "json",
		data : {
			"data" : obj
		},// 你的formid

		success : function(data) {
			investor_no = "";
			$('#investor_no').val('');
			if (data.desc == 1) {
				$('#submitHint').modal('show');
//				location.href="orderList.action";

				//$(".btn btn-primary").attr("data-toggle","modal");
				//$("btnnn").click();
			} else {
				document.getElementById('content').innerHTML=data.msg;
				$('#myal').modal('show');
				btn.disabled=false;
			}
			hideLoad();
		},
		error : function() {
			var msg = escape(escape("数据插入异常"));
			location.href = "/OMS/error.action?msg=" + msg
		}
	});
}



function refreshOrder() {
	location.href="orderList.action";
	$('#submitHint').modal('');
}

function qianfenwei(id){
	var num = $("#"+id).val()
	var no= format(num);
	$("#"+id).val(no);
	if(id=="order_amount"){
		Calculation();
	}
	
}
function format(val){  
	return val.replace(/[^0-9,]*/g,"").replace(/,/g, '').split("").reverse().join("").replace(/(\d{3})(?=[^$])/g, "$1,").split("").reverse().join("");
}
function kycselect(){
	var cust_no =$('#cust_no').val() ;
	
//	$.ajax({
//		url : "/OMS/kycselect.action",
//		type : "post",
//		dataType : "json",
//		data : {
//			"cust_no" : cust_no,
//			"cust_type":$('#cust_type').val()
//		},// 你的formid
//
//		success : function(data) {
//			$('#tbody2').empty();
//			if (data.desc == 1) {
//				
//				var lis = data.list;
//				
//				for (var i = 0; i < lis.length; i++) {
//					var tbody = '<tr class="default" >'
//						+ '<td >'+ lis[i].kyc_id + '</td>'
//						+ '<td>'+ lis[i].reg_time + '</td>'
//						
//						+ '</tr>';
//					$('#kyc').val(lis[i].kycid);
//					$('#tbody2').append(tbody);
//				}
//				
//				
//				//$(".btn btn-primary").attr("data-toggle","modal");
//				//$("btnnn").click();
//			} else {
//				document.getElementById('content').innerHTML=data.msg;
//				$('#myal').modal('show');
//			}
//		},
//		error : function() {
//			var msg = escape(escape("数据查询异常"));
//			location.href = "/OMS/error.action?msg=" + msg
//		}
//	});
	
}

//查找是否有kyc
function selectkyc(){
//	var rows = document.getElementById("kcy").rows.length; // 获得行数(包括thead)
//	var colums = document.getElementById("kcy").rows[0].cells.length; // 获得列数
//	var re=0;
//	for (var i = 1; i < rows; i++) { // 每行
//		var kcyid = document.getElementById("kcy").rows[i].cells[0].innerHTML;
//		if(kcyid!=undefined){
//			re=1;
//		}
//		//a.data.push(b);
//	}
//	return re;
	return 1;
}
function order(){
	location.href="skiporder.action";
}
function order1(){
	location.href="orderList.action";
}
function custtype(){
	$('#cust').show();
	$('#org').hide();
}
function orgtype(){
	
	$('#org').show();
	$('#cust').hide();
	
}
function orderchange(){
	
	var prod_no = $('#prod_no').val();
	$.ajax({
		url : "order_prod.action",
		type : "post",
		dataType : "json",
		data : {
			"prod_no" : prod_no
		},// 你的formid

		success : function(data) {
			$('#part_comp').empty();
			if(data.desc==1){
				var list = data.list;
				for(var i=0;i<list.length;i++){
					var a ='<option value='+list[i].lp_id+'>'+list[i].partner_com_name+'</option>';
					$('#part_comp').append(a);
				}
			}else if(data.desc==2){
				document.getElementById('content').innerHTML=data.msg;
				$('#myal').modal('show');
			}else if(data.desc==3){
				document.getElementById('content').innerHTML=data.msg;
				$('#myal').modal('show');
			}
		},
		error:function(){
			var msg = escape(escape("数据查询异常"));
			location.href = "/OMS/error.action?msg=" + msg
		}
	});
}
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
function Calculation(){
	var order_amount = $('#order_amount').val().toString();
	var prod_no = $('#prod_no').val();
	var part_comp = $('#part_comp').val();
	$.ajax({
		url : "Calculation.action",
		type : "post",
		dataType : "json",
		data : {
			"order_amount" : order_amount.replaceAll(",",""),
			"part_comp":part_comp,
			"prod_no":prod_no
		},
		success : function(data) {
			$('#pri_fee').empty();
			if(data.desc==1){
				$('#pri_fee').val(data.pri_fee);
				qianfenwei('pri_fee');
				$('#prod_diffcoe1').val(data.prod_diffcoe);
			}else if(data.desc==2){
				document.getElementById('content').innerHTML=data.msg;
				$('#myal').modal('show');
				$('#pri_fee').val("计算失败");
			}
		},
		error:function() {
			var msg = escape(escape("数据查询异常"));
			location.href = "/OMS/error.action?msg=" + msg+"&code=104&url=skiporder.action"
		}
	});
}
function orderSales(){
	var sale_name = $('#sale_name').val().trim();
	var distribution = $('#ms > .btn.active').val().trim();
	var acount_fee = $('#acount_fee').val().trim();
	var pri_fee = $('#pri_fee').val().trim();
	if(sale_name==""||sale_name=="请输入销售员工编号"){
		document.getElementById('content').innerHTML="请输入销售员工编号";
		$('#myal').modal('show');
		return;
	}
	$.ajax({
		url : "orderSales.action",
		type : "post",
		dataType : "json",
		data : {
			"sale_name" : sale_name,
			"distribution":distribution,
			"acount_fee":acount_fee,
			"pri_fee":pri_fee
		},
		success : function(data) {
			//$('#tbody_sale').empty();
			if(data.desc==1){
				var tbody = '<tr class="default" >'
						+ '<td style="display:none;" class="a">'+data.list[0].user_id+'</td>'
						+ '<td><input  name="check" type="checkbox" /></td>'
						+ '<td >'+data.list[0].real_name+'</td>' 
						+ '<td class="a" contentEditable="true">'+data.achievement+'</td>'
						+ '<td class="b">'+data.list[0].group_point+'</td>'
						
						+ '<td style="display:none;">'+$('#cust_no').val()+ '</td>'
						+ '</tr>';
				$('#tbody_sale').append(tbody);
			}else if(data.desc==0){
				document.getElementById('content').innerHTML=data.msg;
				$('#myal').modal('show');
				
			}
		},
		error:function() {
			var msg = escape(escape("数据查询异常"));
			location.href = "/OMS/error.action?msg=" + msg+"&code=104&url=skiporder.action"
		}
	});
}
//点击事件设置业绩分配文本是否可用
function show_ms(obj){
	var a=obj;
	if (a=="1"){
		// Achievement_search(a); 
		 $(".a").attr("contentEditable","true");
		$(".b").attr("contentEditable","false");
		 refresh.location.reload(true);  
		
		}
	if (a=="2"){
		// Achievement_search(a); 
		$(".a").attr("contentEditable","false");
		$(".b").attr("contentEditable","true");
		 refresh.location.reload(true); 
	}
/* 	contentEditable="true"	 */
}

function orderinfo(totalNum,PageNum,PageSize){
	//分页1
	NavUtil.PageSize = $("#PageSize").val();
	NavUtil.setPage("page1", parseInt(totalNum), parseInt(PageNum));
	NavUtil.bindPageEvent(loadData);
}

function orderPageList(){
	$.ajax({
		url:"orderPageList.action",
		type : "post",
		dataType : "json",
		data : {
			"pageNum" : $("#PageNum").val(),
			
		},
		success : function(data) {
			$('#order_info').empty();
			orderinfo(data.totalNum,data.PageNum,10);
			if(data.totalNum==0){
				$('#page1').empty();
			}
			if(data.status==1){
				var list = data.orderList;
				for(var i=0;i<list.length;i++){
					var date = date_time(list[i].entry_time,"yyyy-MM-dd");
					var create_time = moment(list[i].create_time).format("YYYY-MM-DD");
					if(list[i].investor_name == null
							|| list[i].investor_name == "") {
						list[i].investor_name = " ";
					}
					var tbody="<tr>" 
					+"<td>"+list[i].order_no +"</td>        "
					+"<td>"+create_time +"</td>      "
					+"<td>"+date+"</td>      "
					+"<td>"+list[i].area +"</td>             "
					+"<td>"+list[i].sales_name +"</td>       "
					+"<td>"+list[i].order_cell +"</td>       "
					+"<td>"+list[i].prod_name +"</td>        "
					+"<td>"+list[i].partner_com_name +"</td> "
					+"<td>"+list[i].prod_type +"</td>        "
					+"<td>"+list[i].cust_name +"</td>        "
					+"<td>"+list[i].investor_name +"</td>        "
					+"<td>"+list[i].order_amount +"</td>     "
					+"<td>"+list[i].bizhong +"</td>          "
					+"<td>"+list[i].prod_diffcoe +"</td>     "
					+"<td>"+list[i].magt_fee1 +"</td>        "
					+"<td>"+list[i].dict_name +"</td>        "
					+ '<td><a href="order_Detail.action?order_version='+list[i].order_version+'&order_no='+ list[i].order_no+ '">详情</a></td>'
					+"</tr>";
					$('#order_info').append(tbody);
				}
			}
		},
		error:function() {
			var msg = escape(escape("数据查询异常"));
			location.href = "/OMS/error.action?msg=" + msg+"&code=104&url="
		}
	});
}
function find_cust(){
	$.ajax({
		url:"find.action",
		type : "post",
		dataType : "json",
		data : {
			"cust_name" : $("#find").val(),
			"cust_type" : $('#cust_type').val()
			
		},
		success : function(data) {
			if(data.status==1){
				$('#cust_na').val(data.custOrgList[0].cust_id);
				kycselect();
			}else{
				alert("未找到客户信息");
			}
				
		},
		error:function() {
			var msg = escape(escape("数据查询异常"));
			location.href = "/OMS/error.action?msg=" + msg+"&code=104&url="
		}
	});
}

function showAddInvestor() {
	$('#addInvestorModal').modal('show');
}

