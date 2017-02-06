function addorg(state) {
	
	var a = JSON.parse("{\"data\":[]}");
	
	var PayObj1 = new Object(); 
	var org_name = $('#org_name').val().trim();
	var org_license = $('#org_license').val().trim();
	var org_legal = $('#org_legal').val().trim();
	if(org_name==""){
		alert("请输入机构名称");
		return;
	}
	if(org_license==""){
		alert("请输入营业执照注册号");
		return;
	}
	if(org_legal==""){
		alert("请输入法人名称");
		return;
	}
	var aa = document.getElementsByName("check");
    for (var i = 0; i < aa.length; i++) {
        if (aa[i].checked) {
        	var PayObj = new Object();
        	PayObj.cust_name = document.getElementById("playlistTable").rows[i+1].cells[1].innerHTML; 
        	PayObj.sales_name = document.getElementById("playlistTable").rows[i+1].cells[2].innerHTML;
        	PayObj.sub_amount = document.getElementById("playlistTable").rows[i+1].cells[3].innerHTML.replace("<br>","");
    		PayObj.proport = document.getElementById("playlistTable").rows[i+1].cells[4].innerHTML.replace("<br>","");
    		PayObj.cust_id = document.getElementById("playlistTable").rows[i+1].cells[5].innerHTML;
    		PayObj.sales_id = document.getElementById("playlistTable").rows[i+1].cells[6].innerHTML;
    		a.data.push(PayObj);
    		
        }
       
    }
    PayObj1.org_name =      $('#org_name').val();
    PayObj1.org_type =      $('#org_type').val();
	PayObj1.org_code_cert = $('#org_code_cert').val();
	PayObj1.org_license =   $('#org_license').val();
	PayObj1.org_legal =     $('#org_legal').val();
	PayObj1.taxid =         $('#taxid').val();
	PayObj1.reg_capital =   $('#reg_capital').val();
	PayObj1.reg_address =   $('#reg_address').val();
	PayObj1.reg_date =      $('#reg_date').val();
	PayObj1.opera_period =      $('#opera_period').val();
	PayObj1.state =      state;
	a.data.push(PayObj1);
    var obj=JSON.stringify(a);
    showLoad();
	$.ajax({
		url : "addorg.action",
		type : "post",
		dataType : "json",
		data : {"data":obj},// 你的formid

		success : function(data) {
			hideLoad();
			if(data.desc==1){
				disp_confirm(data.msg);
			}else if(data.desc==2){
				alert(data.msg);
			}else{
				alert("添加失败");
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
function disp_confirm(msg)
{
var r=confirm(msg+'是否继续添加')
if (r==true)
  {
	location.href="skiporg.action";
  }
else
  {
	
	location.href="org.action";
  }
}
function edit(){
	if($('#org_name').val()!=$('#old_org_name').val()){
		alert("更改姓名需要审批");
		return;
	}
	if($('#org_license').val()!=$('#old_org_license').val()){
		alert("更改营业执照号需要审批");
		return;
	}
	if($('#org_legal').val()!=$('#old_org_legal').val()){
		alert("更改法人需要审批");
		return;
	}
	showLoad();
	$.ajax({
		url : "org_edit.action",
		type : "post",
		dataType : "json",
		data :$('#formid').serialize(),// 你的formid
		success : function(data) {
			if(data.desc==1){
				alert(data.msg);
			}else{
				alert("修改失败");
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
// 查看合伙人
function queryorg() {
	var condition = $("#condition").val();
	
	$.ajax({
				url : "queryorg.action",
				type : "post",
				dataType : "json",
				data : {
					"condition" : condition
				},
				success : function(data) {
					//$("#partner").empty();
					var list = data.list;
					
						var a = '<tr>'
							+ '<td><input  name="check" type="checkbox" /></td>'
							+ '<td>'+list.cust_name+'  </td>'
							+ '<td >'+list.sales_name+' </td>'
							+ '<td contentEditable="true"></td>'
							+ '<td contentEditable="true"></td>'
							+ '<td style="display:none" >'+list.cust_id+'</td>'
							+ '<td style="display:none" >'+list.sales_id+'</td>'
							+ '</tr>';
					$("#partner").append(a);
					
					
				},
				error: function() {
					var msg = escape(escape("数据异常"));
					location.href="error.action?msg="+msg
				}
			});
	
}
function query(totalNum,PageNum){
	$('#page1').empty();
	
	
	NavUtil.PageSize = $("#PageSize").val();
	NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
	
	NavUtil.bindPageEvent(loadData1);
}
function loadData1(){
	
	//分页2
	
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=document.getElementById("PageNum").value;
	org_select(PageNum);
	//document.getElementById("form1").submit();
}

//绑定ENTER事件
document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		org_select(1);
	}
};
//机构名称查询
function org_select(pageNum){
	var org_name=$("#org_name").val().trim();
	var org_license=$("#org_license").val().trim();
	if(org_name=="请输入机构客户名称"){
		org_name="";
	}
	if(org_license=="请输入营业执照号"){
		org_license="";
	}
	$.ajax({
		url : "org_select.action",
		type : "post",
		dataType : "json",
		data : {
			"org_name" : org_name,
			"org_license" : org_license,
			"PageNum":pageNum,
			"PageSize":10
		},
		success : function(data) {
			$("#tbody").empty();
			if(data.status==1){
			
			var list = data.list;
			//alert(data.totalNum);
			 query(data.totalNum,data.PageNum);
			 $('#pagenum').val(data.totalNum);
			for(var i=0;i<list.length;i++){
			var tbody ='<tr class="default" onclick="org_detail('+list[i].org_id+')">'
				+'<td>'+list[i].org_name+'</td>'
				+'<td>'+list[i].org_license+'</td>'
				+'<td>'+list[i].org_legal+'</td>'
				+'<td>'+list[i].reg_date+'</td>'
				+'<td>'+list[i].org_members+'</td>'
				+'<td>'+list[i].state_name+'</td>'
				+'</tr>';
				
			$("#tbody").append(tbody);
		}
			}
			if(data.status==0){
				var tbody ='<tr >'
				+'<th colspan="5" style="text-align:center;">'+data.list+'</th>'
				+'</tr>';
				$("#tbody").append(tbody);
				$('#page1').empty();
				$('#pagenum').val('0');
				
			}
			
		},
		error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
function pagequeryorg(){
	$.ajax({
		url:"pagequer.action",
		type:"post",
		dataType : "json",
		data:{"PageNum":$('#PageNum').val(),"PageSize":10,"count":$('#pagenum').val()},
		success : function(data) {
			$("#tbody").empty();
			if(data.status==1){
				var lis = data.list;
				orginfo(data.totalNum,data.PageNum,10);
				if(data.totalNum==0){
					$('#page1').empty();
				}
				for(var i=0;i<lis.length;i++){
					var tbody='<tr class="default" onclick="org_detail('+lis[i].org_id+')">'
						+'<td>'+lis[i].org_name+'</td>'
						+'<td>'+lis[i].org_license +'</td>'
						+'<td>'+lis[i].org_legal +'</td>'
						+'<td>'+lis[i].reg_date +'</td>'
						+'<td>'+lis[i].org_members +'</td>'
						+'<td>'+lis[i].state_name+'</td>'
						+'</tr>';
					$('#tbody').append(tbody);
				}
				//var a = $('#pagenum').innerText();
				//alert(a);
			}
		},
	error: function() {
		var msg = escape(escape("数据异常"));
		location.href="error.action?msg="+msg
	}
		
	});
}
//机构名称分页查询
function pagequery1(PageNum){
	$.ajax({
		url:"pagequeryorg.action",
		type:"post",
		dataType : "json",
		data:{"PageNum":PageNum,"PageSize":10},
		success : function(data) {
			$("#tbody").empty();
			if(data.status==1){
				var lis = data.list;
				
				for(var i=0;i<lis.length;i++){
					var tbody='<tr class="default" onclick="org_detail('+lis[i].org_id+')">'
						+'<td>'+lis[i].org_name+'</td>'
						+'<td>'+lis[i].org_license +'</td>'
						+'<td>'+lis[i].org_legal +'</td>'
						+'<td>'+lis[i].reg_date +'</td>'
						+'<td>'+lis[i].org_members +'</td>'
						+'<td>'+list[i].state_name+'</td>'
						+'</tr>';
					$('#tbody').append(tbody);
				}
				//var a = $('#pagenum').innerText();
				//alert(a);
			}
		},
	error: function() {
		var msg = escape(escape("数据异常"));
		location.href="error.action?msg="+msg
	}
		
	});
}
function error(){
	var msg = escape(escape("数据异常"));
	location.href="error.action?msg="+msg
}
//提交审批
function org_submit(){
	var a = JSON.parse("{\"data\":[]}");
	var pay = new Object();
	var rows = document.getElementById("playlistTable").rows.length; // 获得行数(包括thead)
	var colums = document.getElementById("playlistTable").rows[0].cells.length; // 获得列数
	for (var i = 1; i < rows; i++) { // 每行
		var b = new Object();
		b.cust_id = document.getElementById("playlistTable").rows[i].cells[0].innerHTML;
		b.proport = document.getElementById("playlistTable").rows[i].cells[3].innerHTML; 
		b.sub_amount = document.getElementById("playlistTable").rows[i].cells[4].innerHTML; 
		a.data.push(b);
		}
	pay.org_name = $('#org_name').val().trim();
	pay.org_type =      $('#org_type').val().trim();
	pay.org_code_cert = $('#org_code_cert').val().trim();
	pay.org_license =   $('#org_license').val().trim();
	pay.org_legal =     $('#org_legal').val().trim();
	pay.taxid =         $('#taxid').val().trim();
	pay.reg_capital =   $('#reg_capital').val().trim();
	pay.reg_address =   $('#reg_address').val().trim();
	pay.reg_time =      $('#reg_time').val().trim();
	pay.org_id =        $('#org_id').val().trim();
	pay.state = $('#state').val().trim();
	a.data.push(pay);
	var obj=JSON.stringify(a);
	$.ajax({
		url : "org_submit.action",
		type : "post",
		dataType : "json",
		data :{"data":obj},// 你的formid

		success : function(data) {
			if(data.desc==1){
				alert(data.msg);
				location.href="org_detail.action?id="+$('#org_id').val();
			}else if(data.desc==2){
				alert(data.msg);
			}else{
				alert("您当前尚未对需要提交审批的字段做更改，无法提交审批")
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
//审批通过
function org_pass(){
	var org_id = $("#org_id").val();
	$.ajax({
		url : "/OMS/org_pass.action",
		type : "post",
		dataType : "json",
		data :{"org_id":org_id},// 你的formid

		success : function(data) {
			if(data.desc==1){
				alert(data.msg);
				/*location.href="org_detail.action";*/
			}else{
				/*alert("审批已通过");*/
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
//审批不通过
function org_nopass(){
	var org_id = $("#org_id").val();
	$.ajax({
		url : "/OMS/org_nopass.action",
		type : "post",
		dataType : "json",
		data :{"org_id":org_id},// 你的formid

		success : function(data) {
			if(data.desc==1){
				alert(data.msg);
				/*location.href="org_detail.action";*/
			}else{
				/*alert("审批已通过");*/
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
//重置
function org_reset(){
	var org_id = $("#org_id").val();
	$.ajax({
		url : "org_reset.action",
		type : "post",
		dataType : "json",
		data :{"org_id":org_id},// 你的formid

		success : function(data) {
			if(data.desc==1){
				alert("重置成功")
				location.href="org_detail.action?id="+$('#org_id').val();
			}else{
				alert("未找到历史数据，重置失败");
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}