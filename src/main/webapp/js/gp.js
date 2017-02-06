function pagequery(totalNum,PageNum,PageSize){
		NavUtil.PageSize = PageSize;
		NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
		NavUtil.bindPageEvent(loadData);
	}
	function loadData(){
		
		//分页2
		$("#totalNum").val(NavUtil.totalNum);
		$("#PageNum").val(NavUtil.PageNum);
		var PageNum=document.getElementById("PageNum").value;
		
		pagequery_gp();
		//document.getElementById("form1").submit();
	}

function addGp(status){
	var gp_name = $('#gp_name').val().trim();
	var bus_license = $('#bus_license').val().trim();
	var legal_resp = $('#legal_resp').val().trim();
	var gp_dept = $('#gp_dept').val().trim();
	var fund_no = $('#fund_no').val().trim();
	var status = $('#status').val(status);
	
	if(gp_name==""){
		alert("请输入GP名称");
		return;
	}
	if(bus_license==""){
		alert("请输入营业执照号");
		return;
	}
	if(legal_resp==""){
		alert("请输入法定代表人");
		return;
	}
	if(gp_dept==""){
		alert("请输入管理方");
		return;
	}
	if(fund_no==""){
		alert("请输入基金业协会备案号");
		return;
	}
	showLoad();
	$.ajax({
		url : "/OMS/gp_addGp.action",
		type : "post",
		dataType : "json",
		data : $('#formid').serialize(),// 你的formid

		success : function(data) {
			hideLoad();
			if(data.desc==1){
				$('#mydiv').modal('show');
				
			}else{
				document.getElementById('content').innerHTML='添加失败';
				$('#myal').modal('show');
			}
		}
	});
}
//查看gp详情

function disp_confirm(msg)
{
var r=confirm(msg+'是否继续添加')
if (r==true)
  {
	location.href="skipgp.action";
  }
else
  {
	
	location.href="gp_select.action";
  }
}
function gpyes(){
	location.href="skipgp.action";
}
function gpno(){
	location.href="gp_select.action";
}
function GP_select(PageNum){
	
	var gp_name = $('#gp_name').val().trim();
	var gp_dept = $('#gp_dept').val().trim();
	if(gp_name=="请输入GP名称"){
		gp_name="";
	}
	if(gp_dept=="请输入管理方"){
		gp_dept="";
	}
	$('#page1').empty();
	$.ajax({
		url : "/OMS/GP_select.action",
		type : "post",
		dataType : "json",
		data : {"gp_name":gp_name,"PageNum":PageNum,"gp_dept":gp_dept},
		success : function(datas) {
			//$('#table').empty();
			$('#tbody').empty();
			
			if(datas.status==1){
				var list = datas.list;
				query(datas.totalNum,datas.PageNum,datas.PageSize);
				for(var i =0;i<list.length;i++){
					var tr = '<tr class="default" onclick="gp_detail('+list[i].gp_id+')">'
					+'<td>'+list[i].gp_name+'</td>'
					+'<td>'+list[i].bus_license+' </td>'
					+'<td>'+list[i].legal_resp+' </td>'
					+'<td>'+list[i].fund_no+' </td>'
					+'<td>'+list[i].gp_dept+' </td>'
					+'<td>'+list[i].status_name +'</td>'
					/*+'<td>'+list[i].regit_addr+' </td>'*/
					+'</tr>';
					$('#tbody').append(tr);
				
			}
				$('#count').val(datas.totalNum);
			 //$('#table tr').attr("onclick","gp_detail("+datas.list.gp_id+")");//绑定跳转页面时间
			}
			if(datas.status==0){
				var tr ='<tr><th colspan="6" style="text-align:center;">'+datas.list+'</th></tr>';
				$("#tbody").append(tr);
			}
		}
	});
}

function query(totalNum,PageNum,PageSize){
	
	
	
	NavUtil.PageSize = PageSize;
	NavUtil.setPage("page1",parseInt(totalNum),parseInt(PageNum));
	
	NavUtil.bindPageEvent(loadData1);
}
function loadData1(){
	
	//分页2
	
	$("#totalNum").val(NavUtil.totalNum);
	$("#PageNum").val(NavUtil.PageNum);
	var PageNum=document.getElementById("PageNum").value;
	GP_select(PageNum);
	//document.getElementById("form1").submit();
}
function edit(){
	if($('#gp_name').val().trim()!=$('#old_gp_name').val().trim()){
		alert("gp名称更改,需要提交审批");
		return;
	}
	if($('#bus_license').val().trim()!=$('#old_bus_license').val().trim()){
		alert("营业执照号更改，需要提交审批");
		return;
	}
	if($('#legal_resp').val().trim()!=$('#old_legal_resp').val().trim()){
		alert("法定代表人更改，需要提交审批");
		return;
	}
	if($('#gp_dept').val().trim()!=$('#old_gp_dept').val().trim()){
		alert("管理方更改，需要提交审批");
		
		return;
	}
	
	if($('#fund_no').val().trim()!=$('#old_fund_no').val().trim()){
		alert("基金业协会备案号更改，需要提交审批");
		return;
	}
	
	showLoad(); 
	$.ajax({
		url : "/OMS/GP_edit.action",
		type : "post",
		dataType : "json",
		data :$('#formid').serialize(),// 你的formid

		success : function(data) {
			hideLoad();
			if(data.desc==1){
				alert(data.msg);
			}else{
				alert("修改失败");
			}
		}
	});
}
function pagequery_gp(){
	var PageNum = $('#PageNum').val();
	
	$.ajax({
		url:"pagegpquery.action",
		type:"post",
		dataType : "json",
		data:{"PageNum":PageNum,"PageSize":10,"count":$('#count').val()},
		success : function(data) {
			pagequery(data.totalNum,data.pagenum,10);
			if(data.totalNum==0){
				$('#page1').empty();
			}
			$("#tbody").empty();
			if(data.status==1){
				var lis = data.list;
				
				for(var i=0;i<lis.length;i++){
					var tbody='<tr class="default" onclick="gp_detail('+lis[i].gp_id+')">'
						+'<td>'+lis[i].gp_name+'</td>'
						+'<td>'+lis[i].bus_license +'</td>'
						+'<td>'+lis[i].legal_resp +'</td>'
						+'<td>'+lis[i].fund_no +'</td>'
						+'<td>'+lis[i].gp_dept +'</td>'
						+'<td>'+lis[i].status_name +'</td>'
						/*+'<td>'+lis[i].regit_addr +'</td>'*/
						+'</tr>';
					$('#tbody').append(tbody);
				}
				//var a = $('#pagenum').innerText();
				//alert(a);
			}
		},
	error: function() {
		alert("错误");
	}
		
	});
}

//提交审批
function gp_submit(){
	var gp_id = $("#gp_id").val();
	var state ="0";
	if($('#gp_name').val().trim()!=$('#old_gp_name').val().trim()){
		state="1";
		
		$('#message').val($('#old_gp_name').val().trim()+'				'+$('#gp_name').val().trim()+'\n');
	}
	if($('#bus_license').val().trim()!=$('#old_bus_license').val().trim()){
		state="1";
		$('#message').val($('#old_bus_license').val().trim()+'				'+$('#bus_license').val().trim()+'\n');
	}
	if($('#legal_resp').val().trim()!=$('#old_legal_resp').val().trim()){
		state="1";
		$('#message').val($('#old_legal_resp').val().trim()+'				'+$('#legal_resp').val().trim()+'\n');
	}
	if($('#gp_dept').val().trim()!=$('#old_gp_dept').val().trim()){
		state="1";
		$('#message').val($('#old_gp_dept').val().trim()+'				'+$('#gp_dept').val().trim()+'\n');
	}
	if($('#fund_no').val().trim()!=$('#old_fund_no').val().trim()){
		state="1";
		$('#message').val($('#old_fund_no').val().trim()+'				'+$('#fund_no').val().trim()+'\n');
	}
	if(state=="0"){
		alert("尚未更改需要提交审批的字段");
		return;
	}
	$.ajax({
		url : "/OMS/gp_submit.action",
		type : "post",
		dataType : "json",
		data :$('#formid').serialize(),// 你的formid

		success : function(data) {
			if(data.desc==1){
				alert(data.msg);
			}else{
				/*alert("信息已提交，待审批");*/
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
//审批通过
function gp_pass(){
	var gp_id = $("#gp_id").val();
	$.ajax({
		url : "/OMS/gp_pass.action",
		type : "post",
		dataType : "json",
		data :{"gp_id":gp_id},// 你的formid

		success : function(data) {
			if(data.desc==1){
				alert(data.msg);
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
function gp_nopass(){
	var gp_id = $("#gp_id").val();
	$.ajax({
		url : "/OMS/gp_nopass.action",
		type : "post",
		dataType : "json",
		data :{"gp_id":gp_id},// 你的formid

		success : function(data) {
			if(data.desc==1){
				alert(data.msg);
			}else{
				/*alert("审批已通过");*/
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
//gp列表导出报表
function gp_report(){
	var localFilePath = $('#localFilePath').val();
	window.open('gp_report.action', '_blank');

}
//gp重置
function gp_reset(){
	var gp_id = $("#gp_id").val();
	$.ajax({
		url : "gp_reset.action",
		type : "post",
		dataType : "json",
		data :{"gp_id":gp_id},// 你的formid

		success : function(data) {
			if(data.desc==1){
				var list = data.content;
				$("#gp_name").val(list.gp_name);
				$("#bus_license").val(list.bus_license);
				$("#legal_resp").val(list.legal_resp);
				$("#gp_dept").val(list.gp_dept);
				$("#fund_no").val(list.fund_no);
				$("#open_bank").val(list.open_bank);
				$("#bank_account").val(list.bank_account);
				$("#regit_addr").val(list.regit_addr);
			}else{
				alert("未找到历史数据")
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
