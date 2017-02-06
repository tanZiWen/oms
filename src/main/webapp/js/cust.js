var statusVal;
function Cust() {
	
}

//客户列表导出报表
function cust_report(){
	var localFilePath = $('#localFilePath').val();
	//location.href="";
	window.open('cust_report.action', '_blank');

}

function kyc_mould(){
	window.open('kyc_mould.action', '_blank');

}

//文件上传

function upload() {
	var file = document.getElementById("file1");
	var cust_id = $("#cust_id6").val(); 
	$('#file1FileName').val(file);
	 document.getElementById("form_file").submit();
	
}
//添加kyc
function kycinsert() {
	 document.getElementById("kyc_insert_form_file").submit();
	
}
//添加绑定点击事件,客户信息成功保存
function skipaddsave() {
	var cust_id = $("#cust_id").val(); 
	$.ajax({
		url : "/OMS/skipaddsave.action",
		type : "post",
		dataType : "json",
		data : {"cust_id" : cust_id},// 你的formid

		success : function(data) {
			if (data.status == 1) {
				var list = data.list;
				$("#cust_id").val(data.cust_id);
				alert(data.msg);
				/*alert("提交成功，待审批");*/
				window.location.href ="cust.action";
			}
			else {
				alert("提交失败");
			}
		}
	});
	
}

//添加绑定点击事件,客户信息添加成功提交审批
function skipaddpass() {
	var cust_id = $("#cust_id").val(); 
	var cust_name = $("#cust_name").val(); 
	var cust_sex = $("#cust_sex").find("option:selected").text(); 
	var cust_cell = $("#cust_cell").val(); 
	var see_desc = $("#see_desc").val(); 
	showLoad();
	$.ajax({
		url : "/OMS/skipaddpass.action",
		type : "post",
		dataType : "json",
		data : {"cust_id" : cust_id,
			"cust_name" : cust_name,
			"cust_sex" : cust_sex,
			"cust_cell" : cust_cell,
			"see_desc" : see_desc},// 你的formid

		success : function(data) {
			hideLoad();
			if (data.status == 1) {
				var list = data.list;
				$("#cust_id").val(data.cust_id);
				alert(data.msg);
				/*alert("提交成功，待审批");*/
				window.location.href ="cust.action";
			} 
			else if(data.status == 2){
				alert("未找到领导邮箱，邮件发送失败！\n"+
						"但信息已保存到系统中");
				window.location.href ="cust.action";
			}
			else {
				alert("提交失败");
			}
		}
	});
	
}
//添加页面点取消返回列表
function skipadd3() {
	
	window.location.href ="cust.action";
}
//客户信息添加完成提示
function skipadd1() {
	alert("添加成功");
	
	window.location.href ="skipadd.action";
}
//添加绑定点击事件
function skipadd() {
	
	window.location.href ="skipadd.action";
}
//添加客户基本信息
function addCustinfo() {
	
	var cust_name = $('#cust_name').val().trim();
	var cust_sex = $('#cust_sex').val().trim();
	var cust_cell = $('#cust_cell').val().trim();
	var see_date = $('#see_date').val().trim();
	var see_desc = $('#see_desc').val().trim();
	$('#list_div_2').css("display", "none");
	if (cust_name == "") {
		alert("请输入客户姓名");
		return;
	}
	if (cust_sex == "") {
		alert("请输入客户性别");
		return;
	}
	if (cust_cell == "") {
		alert("请输入客户手机号");
		return;
	}
	if (see_date == "") {
		alert("请输入客户拜访日期");
		return;
	}
	if (see_desc == "") {
		alert("请输入客户拜访描述内容");
		return;
	}
	$.ajax({
		url : "/OMS/addCustinfo.action",
		type : "post",
		dataType : "json",
		data : $('#formid').serialize(),// 你的formid

		success : function(data) {
			if (data.status == 1) {
				var list = data.list;
				$("#cust_id").val(data.cust_id);
				$("#family_name").val(data.cust_name+"--家族")
				var next_btn=document.getElementById('next_btn');
				var list_div_2=document.getElementById('list_div_2');
				list_div_2.style.display='block';
				next_btn.style.display='none';
			} else if(data.status == 0){
				
				alert(data.msg);
				window.location.href ="skipadd.action";
			}
			else {
				alert("添加失败");
			}
		}
	});
}

//添加客户公司信息
function addCompinfo() {	
	var comp_name = $('#comp_name').val().trim();
	var cust_id=$('#cust_id').val().trim();
	if (comp_name == "") {
		alert("请输入公司名称");
		return;
	}
	/*alert($('#formid1').serialize());*/
	$.ajax({
		url : "/OMS/addCompinfo.action?cust_id="+cust_id,
		type : "post",
		dataType : "json",
		data : $('#formid1').serialize(),// 你的formid

		success : function(data) {
			
			if (data.desc == 1) {
				/*comp_confirm(data.msg)*/
				alert("公司信息已保存");
				$("#cust_id").val(data.cust_id)
				$("#comp_id").val(data.comp_id)
				/*$('.bs-example-modal-sm').modal('show');
				location.href="skipadd.action";*/
			} else {
				alert("添加失败");
			}
		}
	});
}
//是否继续添加公司信息
function comp_confirm(msg)
{
var r=confirm(msg+'是否继续添加')
if (r==true)
  {
	location.href="skipadd.action";
  }
else
  {
	
	location.href="pagequery.action";
  }
}
//客户
function custMessage() {
	var cust_name = $('#cust_name').val().trim();
	var cust_tel = $('#cust_tel').val().trim();
	var cust_idcard = $('#cust_idcard').val().trim();
	$.ajax({
		url : "",
		type : "json",
		dataType : "",
		data : {
			"cust_name" : cust_name,
			"cust_tel" : cust_tel,
			"cust_idcard" : cust_idcard
		},
		success : function(data) {
			if (data.desc == 0) {

			} else if (data.desc == 1) {

			}
		}
	});
}

//绑定ENTER事件
document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		cust_search(1);
	}
};

// 索引查询
function cust_search(PageNum) {
	var state = statusVal;
	var cust_name = $("#cust_name").val();
	var cust_cell = $("#cust_cell").val();
	var sales_name = $("#sales_name").val();
	var cust_level = $("#cust_level").val();
	if (sales_name == "请输入销售名") {
		sales_name = "";
	}
	if (cust_name == "请输入客户名称") {
		cust_name = "";
	}
	if (cust_cell == "请输入电话号码") {
		cust_cell = "";
	}
	$.ajax({
				url : "cust_search.action",
				type : "post",
				dataType : "json",
				data : {
					"state" : state,
					"cust_name" : cust_name,
					"cust_cell" : cust_cell,
					"sales_name" : sales_name,
					"cust_level" : cust_level,
					"PageNum": PageNum,
					"PageSize": 10
				},
				success : function(data) {
					$("#tbody").empty();
					if (data.status == 1) {
						var list = data.list;
						/*alert(list[0].cust_id)*/
						query1(data.totalNum,data.PageNum);
						var a = $('#pagenum').val(data.totalNum);
						for (var i = 0; i < list.length; i++) {
							var tbody = '<tr class="default">'
									+ '<td>'+list[i].dist_name+'</td>'
									+ '<td>'+list[i].cust_name+'</td>'
									+ '<td>'+list[i].sex_name+'</td>'
									+ '<td>'+list[i].cust_cell+'</td>'
									+ '<td>'+list[i].cust_idnum+'</td>'
									+ '<td>'+list[i].sales_name+'</td>'
									+ '<td>'+list[i].level_name+'</td>'
									+ '<td>'+list[i].state_name+'</td>'
									+ '<td><a href="javascript:company_detail('+list[i].cust_id+')">公司</a></td>'
									+ '<td><a href="javascript:famliy_detail('+list[i].cust_id+')">家族</a></td>'
									+ '<td><a href="javascript:cust_detail('+list[i].cust_id+')">详情</a></td>'
									+ ' </tr>';
							$("#tbody").append(tbody);
						}
					}
					if (data.status == 0) {
						var a = $('#pagenum').val("0");
						$('#page1').empty();
						var tbody = '<tr >'
								+ '<th colspan="12" style="text-align:center;">'
								+ data.list + '</th>' + '</tr>';
						$("#tbody").append(tbody);
					}

				}
			});
}

//客户分配页面 索引查询
function distribution_search(PageNum) {
	var state = statusVal;
	var cust_name = $("#cust_name").val();
	var cust_cell = $("#cust_cell").val();
	var sales_name = $("#sales_name").val();
	var cust_level = $("#cust_level").val();
	if (sales_name == "请输入销售名") {
		sales_name = "";
	}
	if (cust_name == "请输入客户名称") {
		cust_name = "";
	}
	if (cust_cell == "请输入电话号码") {
		cust_cell = "";
	}
	$.ajax({
				url : "distribution_search.action",
				type : "post",
				dataType : "json",
				data : {
					"state" : state,
					"cust_name" : cust_name,
					"cust_cell" : cust_cell,
					"sales_name" : sales_name,
					"cust_level" : cust_level,
					"PageNum": PageNum,
					"PageSize": 10
				},
				success : function(data) {
					$("#tbody").empty();
					if (data.status == 1) {
						var list = data.list;
						/*alert(list[0].cust_id)*/
						query1(data.totalNum,data.PageNum);
						var a = $('#pagenum').val(data.totalNum);
						for (var i = 0; i < list.length; i++) {
							var tbody = '<tr class="default">'
									+ '<td>'+list[i].cust_name+'</td>'
									+ '<td>'+list[i].real_name+'</td>'
									+ '<td>'+list[i].cust_belong_state+'</td>'
									+ '<td><a href="javascript:openTansferDialog(\'' + list[i].cust_id
									+ '\',\'' + list[i].cust_name + '\', \'' + list[i].real_name + '\');">重新分配</a></td>'
									+ ' </tr>';
							$("#tbody").append(tbody);
						}
					}
					if (data.status == 0) {
						var a = $('#pagenum').val("0");
						$('#page1').empty();
						var tbody = '<tr >'
								+ '<th colspan="12" style="text-align:center;">'
								+ data.list + '</th>' + '</tr>';
						$("#tbody").append(tbody);
					}

				}
			});
}

//客户分配页面 分页查询
function distributionPageQuery(){
	$.ajax({
		url:"distribution_pagequery.action",
		type:"post",
		dataType : "json",
		data:{"PageNum":$('#PageNum').val(),"PageSize":10},
		
		success : function(data) {
			$("#tbody").empty();
			$('#page1').empty();
			query1(data.totalNum,data.pagenum,10);
			if(data.totalNum==0){
				$('#page1').empty();
			}
			if(data.status==1){
				var list = data.list;
				
				for(var i=0;i<list.length;i++){
					var tbody= '<tr class="default">'
						+ '<td>'+list[i].cust_name+'</td>'
						+ '<td>'+list[i].real_name+'</td>'
						+ '<td>'+list[i].cust_belong_state+'</td>'
						+ '<td><a href="javascript:openTansferDialog(\'' + list[i].cust_id
						+ '\',\'' + list[i].cust_name + '\', \'' + list[i].real_name + '\');">重新分配</a></td>'
						+ ' </tr>';
					$('#tbody').append(tbody);
				}
				
			}
		},
	error: function() {
		alert("错误");
	}
		
	});
}

function cust_search1(state,PageNum) {
	var cust_name = $("#cust_name").val();
	var cust_cell = $("#cust_cell").val();
	var sales_name = $("#sales_name").val();
	var cust_level = $("#cust_level").val();
	if (sales_name == "请输入销售名") {
		sales_name = "";
	}
	if (cust_name == "请输入客户名称") {
		cust_name = "";
	}
	if (cust_cell == "请输入电话号码") {
		cust_cell = "";
	}
	$.ajax({
				url : "cust_search.action",
				type : "post",
				dataType : "json",
				data : {
					"state" : state,
					"cust_name" : cust_name,
					"cust_cell" : cust_cell,
					"sales_name" : sales_name,
					"cust_level" : cust_level,
					"PageNum":PageNum,
					"PageSize":10
				},
				success : function(data) {
					$("#tbody").empty();
					if (data.status == 1) {
						var list = data.list;
						/*alert(list[0].cust_id)*/
						query1(data.totalNum,data.PageNum);
						var a = $('#pagenum').val(data.totalNum);
						for (var i = 0; i < list.length; i++) {
							var tbody = '<tr class="default">'
									+ '<td>'+list[i].sales_area+'</td>'
									+ '<td>'+list[i].cust_name+'</td>'
									+ '<td>'+list[i].sex_name+'</td>'
									+ '<td>'+list[i].cust_cell+'</td>'
									+ '<td>'+list[i].cust_idnum+'</td>'
									+ '<td>'+list[i].sales_name+'</td>'
									+ '<td>'+list[i].level_name+'</td>'
									+ '<td>'+list[i].state_name+'</td>'
									+ '<td><a href="javascript:company_detail('+list[i].cust_id+')">公司</a></td>'
									+ '<td><a href="javascript:famliy_detail('+list[i].cust_id+')">家族</a></td>'
									+ '<td><a href="javascript:cust_detail('+list[i].cust_id+')">详情</a></td>'
									+ ' </tr>';
							$("#tbody").append(tbody);
						}
					}
					if (data.status == 0) {
						var a = $('#pagenum').val("0");
						$('#page1').empty();
						var tbody = '<tr >'
								+ '<th colspan="12" style="text-align:center;">'
								+ data.list + '</th>' + '</tr>';
						$("#tbody").append(tbody);
					}

				}
			});
}

// 添加拜访记录
function addSee() {
	var see_date = $('#see_date').val().trim();
	var see_member = $('#see_member').val().trim();
	var see_desc = $('#see_desc').val().trim();
	if (see_date == "") {
		alert("请确认日期");
		return;
	}
	if (see_desc == "") {
		alert("请添加描述");
		return;
	}
	$.ajax({
		url : "addSee.action",
		type : "post",
		dataType : "json",
		data : $('#formid').serialize(),// 你的formid

		success : function(data) {
			if (data.desc == 1) {
				alert(data.msg);
			} else {
				alert("添加失败");
			}
		}
	});
}

//添加客户家族信息
function addFamilyinfo() {
	var family_name = $('#family_name').val().trim();
	var cust_sex = $('#cust_sex').val().trim();
	var cust_cell = $('#cust_cell').val().trim();
	var cust_id=$('#cust_id').val().trim();
	
	$.ajax({
		url : "/OMS/addFamilyinfo.action?cust_id="+cust_id,
		type : "post",
		dataType : "json",
		/*data : {"data":JSON.stringify(a)},*/// 你的formid
        data:$('#formid2').serialize(),
        
		success : function(data) {
			if (data.desc == 1) {
				document.getElementById('content').innerHTML=data.msg;
				$('#tishi').modal('show');
				$("#cust_id").val(data.cust_id)
				$("#family_id").val(data.family_id)
				$("#member_id").val(data.member_id)
				/*location.href="skipadd.action";*/
			} else {
				alert("添加失败");
			}
		}
	});
}

//最初新建未建家族，在详情中添加客户家族信息
function addFamily() {
	var family_name = $('#family_name').val().trim();
	
	var cust_id=$('#cust_id').val().trim();
	
	$.ajax({
		url : "/OMS/addFamilyinfo.action?cust_id="+cust_id,
		type : "post",
		dataType : "json",
		/*data : {"data":JSON.stringify(a)},*/// 你的formid
        data:$('#formid2').serialize(),
        
		success : function(data) {
			if (data.desc == 1) {
				document.getElementById('content').innerHTML=data.msg;
				$('#tishi').modal('show');
				$("#cust_id").val(data.cust_id)
				$("#family_id").val(data.family_id)
				$("#member_id").val(data.member_id)
				/*location.href="skipadd.action";*/
			} else {
				alert("添加失败");
			}
		}
	});
}
//添加公司成员
function addMember() {
	var comp_name = $('#comp_name').val().trim();
	if (comp_name == "") {
		alert("请输入公司名称");
		return;
	}
/*alert(2);*/
	$.ajax({
		url : "addMember.action",
		type : "post",
		dataType : "json",
		data : $('#formid').serialize(),// 你的formid

		success : function(data) {
			if (data.desc == 1) {
				alert(data.msg);
			} else {
				alert("添加失败");
			}
		}
	});
}
//异步刷新家族成员
function show_detail(family_id,member_id) {

	$.ajax({
				url : "show_detail.action",
				type : "post",
				dataType : "json",
				data : {
					"family_id" : family_id,
					"member_id" : member_id
				},

				success : function(data) {
					$("#ul").empty();

					var list = data.list;

					 var ul = '<li class="list-group-item" style="background-color: rgb(245,245,245);">'
						 +'<h3 class="panel-title" style="color:#a52410;"><span class="glyphicon glyphicon-edit" aria-hidden="true"  style="margin-right: 6px;"></span>成员基本信息</h3></li>'
						 +'<li class="list-group-item item_border" >姓名：<input name="cust_name" id="cust_name" value="'+list.cust_name+'" type="text"  style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/><input name="family_id1" id="family_id1" value="'+list.family_id+'" type="hidden"><input name="member_id" id="member_id" value="'+list.member_id+'" type="hidden"></li>'
						 +'<li class="list-group-item item_border" >性别：<span contentEditable="true">'+list.sex_name+'</span></li>'
						 +'<li class="list-group-item item_border">状态：<span contentEditable="true">'+list.state_name+'</span></li>'
						 +'<li class="list-group-item item_border">联系电话：<input name="cust_cell" id="cust_cell" value="'+list.cust_cell+'" type="tel" maxlength = "11"  style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/></li>'
						 +'<li class="list-group-item item_border">关系：<input name="relation" id="relation" value="'+list.relation+'" type="text" maxlength = "10" style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/></li>'
						 +'<li class="list-group-item item_border">证件类型：<span contentEditable="true">'+list.idtype_name+'</span></li>'
						 +'<li class="list-group-item item_border">证件号：<input name="cust_idnum" id="cust_idnum" value="'+list.cust_idnum+'" type="text" maxlength = "60" style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/></li>'
						 +'<li class="list-group-item item_border">级别：<span contentEditable="true">'+list.level_name+'</span></li>'
						 +'<li class="list-group-item item_border">微信号：<input name="wechat" id="wechat" value="'+list.wechat+'" type="text" maxlength = "50"  style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/></li>'
						 +'<li class="list-group-item item_border">QQ：<input name="qq" id="qq" value="'+list.qq+'" type="text" maxlength = "50" style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/></li>'
						 +'<li class="list-group-item item_border" style="border-bottom: none;">职业：<input name="profession" id="profession" value="'+list.profession+'" type="text"  maxlength = "30" style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/></li>'
						 +'<li class="list-group-item item_border">雇主名称：<input name="company" id="company" value="'+list.company+'" type="text"  maxlength = "50" style="width: 65%;margin-left: 2%;border:none;outline: none;" contentEditable="true"/></li>';

					$("#ul").append(ul);
				}

			});
}
//异步刷新公司列表
function compList(cust_id,comp_id){
	$.ajax({
		url : "compList.action",
		type : "post",
		dataType : "json",
		data : {"cust_id":cust_id,"comp_id":comp_id},

		success : function(data) {
			
			$("#ul").empty();
			var list = data.list;
			if (data.desc == 1) {
				
                var li = '<li class="list-group-item" style="background-color: rgb(245, 245, 245);"><h3 class="panel-title" style="color: #a52410;"><span class="glyphicon glyphicon-edit" aria-hidden="true" style="margin-right: 6px;"></span>公司基本信息</h3>'
                	
                    +'<li class="list-group-item">公司名称：<input name="comp_name" id="comp_name" value="'+list.comp_name+'" type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;" maxlength = "30" contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border">公司类型：<input name="comp_type" id="comp_type" value="'+list.comp_type+'" type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;" maxlength = "30" contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border">营业执照注册号(统一社会信用代码)：<input name="license" id="license" value="'+list.license+'" type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;"  maxlength = "30" contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border" style="border-bottom: 0;">法人：<input name="legal" id="legal" value="'+list.legal+'" type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;" maxlength = "20"  contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border">组织机构代码证：<input name="org_code_cert" id="org_code_cert" value="'+list.org_code_cert+'" type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;" maxlength = "30" contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border">税务登记号：<input name="taxid" id="taxid" value="'+list.taxid+'" type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;"  maxlength = "100" contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border">成立日期：<input name="reg_date" id="reg_date" value="'+list.reg_date+'" class="form_datetime"  type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;"  maxlength = "10" contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border">经营期限：<input name="opera_period" id="opera_period" value="'+list.opera_period+'" class="form_datetime"  type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;"  maxlength = "10" contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border">注册资本：<input name="reg_capital" id="reg_capital" value="'+list.money+'" type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;" maxlength = "20"  contentEditable="true"/></li>'
    				+'<li class="list-group-item item_border">注册地址：<input name="reg_address" id="reg_address" value="'+list.reg_address+'" type="text" style="width: 65%;margin-left: 2%;border:none;outline: none;" maxlength = "100" contentEditable="true"/></li>';
			
			$("#ul").append(li);	
			$('#cust_id1').val(list.cust_id );
			$('#comp_id').val(list.comp_id);
			}
			 else {
				alert("查询失败");
			}
		}
	});	
}

//添加前查询客户是否存在
function querycust(){
	
	var condition = $("#queryer").val();
	
	if (condition == "") {
		alert("录入前请先查询该客户是否已在系统中");
		return;
	}
	
	$.ajax({
		
				url : "/OMS/querycust.action",
				type : "post",
				dataType : "json",
				data : {
					
					"condition" : condition
				},
				
				success : function(data) {
					if (data.status == 1) {
					$("#queryer").empty();
					alert("该客户已在--"+data.list.sales_name+"--名下，如有问题，请线下沟通！");
					/*$("#queryer").append(a);*/
					} else if(data.status == 0) {
						alert("未找到该客户，请先录入！");
					}					
				},
				error: function() {
					alert("未找到相关数据！");
				}
			});
	
}
//初始化
function pagequery(){
	$.ajax({
		url:"pagequery.action",
		type:"post",
		dataType : "json",
		data:{"PageNum":$('#PageNum').val(),"PageSize":10},
		
		success : function(data) {
			$("#tbody").empty();
			$('#page1').empty();
			query(data.totalNum,data.pagenum,10);
			if(data.totalNum==0){
				$('#page1').empty();
			}
			if(data.status==1){
				var list = data.list;
				
				for(var i=0;i<list.length;i++){
					var tbody= '<tr class="default">'
						+ '<td>'+list[i].dist_name+'</td>'
						+ '<td>'+list[i].cust_name+'</td>'
						+ '<td>'+list[i].sex_name+'</td>'
						+ '<td>'+list[i].cust_cell+'</td>'
						+ '<td>'+list[i].cust_idnum+'</td>'
						+ '<td>'+list[i].sales_name+'</td>'
						+ '<td>'+list[i].level_name+'</td>'
						+ '<td>'+list[i].state_name+'</td>'
						+ '<td><a href="javascript:company_detail('+list[i].cust_id+')">公司</a></td>'
						+ '<td><a href="javascript:famliy_detail('+list[i].cust_id+')">家族</a></td>'
						+ '<td><a href="javascript:cust_detail('+list[i].cust_id+')">详情</a></td>'
						+ ' </tr>';
					$('#tbody').append(tbody);
				}
				
				//alert(a);
			}
		},
	error: function() {
		alert("错误");
	}
		
	});
}
//索引分页查询
function pagequeryselect(PageNum){
	$.ajax({
		url:"pagequeryselect.action",
		type:"post",
		dataType : "json",
		data:{"PageNum":PageNum,"PageSize":10},
		success : function(data) {
			$("#tbody").empty();
			if(data.status==1){
				var list = data.list;
				/*var a = $('#pagenum').val(data.totalNum);*/
				
				for(var i=0;i<list.length;i++){
					var tbody= '<tr class="default">'
						+ '<td>'+list[i].sales_area+'</td>'
						+ '<td>'+list[i].cust_name+'</td>'
						+ '<td>'+list[i].sex_name+'</td>'
						+ '<td>'+list[i].cust_cell+'</td>'
						+ '<td>'+list[i].cust_idnum+'</td>'
						+ '<td>'+list[i].sales_name+'</td>'
						+ '<td>'+list[i].level_name+'</td>'
						+ '<td>'+list[i].state_name+'</td>'
						+ '<td><a href="javascript:company_detail('+list[i].cust_id+')">公司</a></td>'
						+ '<td><a href="javascript:famliy_detail('+list[i].cust_id+')">家族</a></td>'
						+ '<td><a href="javascript:cust_detail('+list[i].cust_id+')">详情</a></td>'
						+ ' </tr>';
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
//索引分页
function query1(totalNum,PageNum){
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
	cust_search(PageNum);
	
	//document.getElementById("form1").submit();
}



//更新客户基本信息（不再使用）
function cust_edit(){
	if($('#cust_name').val().trim()!=$('#former_cust_name').val().trim()){
		alert("客户名称不能更改，若要更改，请提交审批");
		return;
		$('#message').val($('#former_cust_name').val().trim()+'	'+$('#cust_name').val().trim()+'\n');
	}
	if($('#cust_cell').val().trim()!=$('#former_cust_cell').val().trim()){
		alert("客户手机号不能更改，若要更改，请提交审批");
		return;
		$('#message').val($('#former_cust_cell').val().trim()+'	'+$('#cust_cell').val().trim()+'\n');
	}
	if($('#cust_idtype').val().trim()!=$('#former_cust_idtype').val().trim()){
		alert("客户证件类型不能更改，若要更改，请提交审批");
		return;
		$('#message').val($('#former_cust_idtype').val().trim()+' '+$('#cust_idtype').val().trim()+'\n');
	}
	if($('#cust_idnum').val().trim()!=$('#former_cust_idnum').val().trim()){
		alert("客户证件号不能更改，若要更改，请提交审批");
		return;
		$('#message').val($('#former_cust_idnum').val().trim()+' '+$('#cust_idnum').val().trim()+'\n');
	}
	$.ajax({
		url : "/OMS/cust_edit.action",
		type : "post",
		dataType : "json",
		data :$("#formidcust").serialize(),// 你的formid

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

//更新客户基本信息
function cust_submit(){
	var cust_id = $("#cust_id").val(); 
	if($('#cust_cell').val().trim()!=$('#former_cust_cell').val().trim()){
		alert("联系电话不能更改");
		return;
	}
	$.ajax({
		url : "/OMS/cust_submit.action",
		type : "post",
		dataType : "json",
		data :$("#formidcust").serialize(),// 你的formid

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
//审批通过
function cust_pass(){
	var cust_id = $("#cust_id").val();
	$.ajax({
		url : "/OMS/cust_pass.action",
		type : "post",
		dataType : "json",
		data :{"cust_id":cust_id},// 你的formid

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
function cust_nopass(){
	var cust_id = $("#cust_id").val();
	$.ajax({
		url : "/OMS/cust_nopass.action",
		type : "post",
		dataType : "json",
		data :{"cust_id":cust_id},// 你的formid

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

//更改客户公司信息
function comp_edit(){
	
	$.ajax({
		url : "/OMS/comp_edit.action",
		type : "post",
		dataType : "json",
		data :$("#formidcomp").serialize(),// 你的formid

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
//更改客户家族成员信息
function fammember_edit(){
	$.ajax({
		url : "/OMS/fammember_edit.action",
		type : "post",
		dataType : "json",
		data :$("#formidfammember").serialize(),// 你的formid

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
//重置客户家族信息
function custfam_reset(){
	var family_id  = $('#family_id1').val();
	var member_id = $('#member_id').val();
	$.ajax({
		url : "custfam_reset.action",
		type : "post",
		dataType : "json",
		data :{"member_id":member_id,"family_id":family_id},

		success : function(data) {
			if(data.desc==1){
				alert("重置成功");
				var cust_name = $('#cust_name').val();
				/* var msg = escape(escape(msg));
				 var cust_id = $('#cust_id').val();
				   location.href="cust_detail.action?cust_id="+cust_id + "&cust_name=" +msg+"&flag=2";*/
			}else{
				alert("修改失败");
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
//重置客户详情信息
function cust_reset(){
	var cust_id  = $('#cust_id').val();
	$.ajax({
		url : "cust_reset.action",
		type : "post",
		dataType : "json",
		data :{"cust_id":cust_id},

		success : function(data) {
			if(data.desc==1){
				alert("重置成功");
				var cust_name = $('#cust_name').val();
				location.href="cust_detail.action?cust_id="+cust_id+"&flag=1";
				/* var msg = escape(escape(msg));
				 var cust_id = $('#cust_id').val();
				   location.href="cust_detail.action?cust_id=201607100000001&flag=1";*/
			}else{
				alert("修改失败");
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
//有家族后，添加家族成员前，查询是否已存在
function queryFamMem() {
	var queryfm = $("#queryfm").val();
	if (queryfm == "") {
		alert("录入前请先查询该客户是否已在系统中");
		return;
	}
	$.ajax({
				url : "queryFamMem.action",
				type : "post",
				dataType : "json",
				data : {
					"queryfm" : queryfm
				},
				success : function(data) {
					//$("#queryFM").empty();
					var list = data.list;
					
						var a = '<tr>'
							+ '<td><input onclick="checkb1()" id='+list.cust_id+' value="'+list.cust_id+'"  name="check" type="checkbox" /></td>'
							+ '<td>'+list.cust_name+'  </td>'
							/*+ '<td style="display:none" >'+list.cust_sex+'  </td>' 
							+ '<td style="display:none" >'+list.cust_cell+'  </td>' 
							+ '<td style="display:none" >'+list.cust_birth+'  </td>' 
							+ '<td style="display:none" >'+list.cust_idtype+'  </td>' 
							+ '<td style="display:none" >'+list.cust_idnum+'  </td>' 
							+ '<td style="display:none" >'+list.city+'  </td>' 
							+ '<td style="display:none" >'+list.email+'  </td>' 
							+ '<td style="display:none" >'+list.wechat+'  </td>' 
							+ '<td style="display:none" >'+list.qq+'  </td>' 
							+ '<td style="display:none" >'+list.address+'  </td>' 
							+ '<td style="display:none" >'+list.company+'  </td>' 
							+ '<td style="display:none" >'+list.id_address+'  </td>' 
							+ '<td style="display:none" >'+list.profession+'  </td>'
							+ '<td style="display:none" >'+list.cust_id+'</td>'*/
							+ '</tr>';
					$("#partner1").append(a);
					
					
				},
				error: function() {
					var msg = escape(escape("数据异常"));
					location.href="error.action?msg="+msg
				}
			});
	
}
//添加家族弹框里的成员前，查询是否已存在
function queryFamilyMember() {
	var queryFM = $("#queryFM").val();
	if (queryFM == "") {
		alert("录入前请先查询该客户是否已在系统中");
		return;
	}
	$.ajax({
				url : "queryFamilyMember.action",
				type : "post",
				dataType : "json",
				data : {
					"queryFM" : queryFM
				},
				success : function(data) {
					//$("#queryFM").empty();
					var list = data.list;
					
						var a = '<tr>'
							+ '<td><input onclick="checkb()" id='+list.cust_id+' value="'+list.cust_id+'"  name="check" type="checkbox" /></td>'
							+ '<td>'+list.cust_name+'  </td>'
							/*+ '<td style="display:none" >'+list.cust_sex+'  </td>' 
							+ '<td style="display:none" >'+list.cust_cell+'  </td>' 
							+ '<td style="display:none" >'+list.cust_birth+'  </td>' 
							+ '<td style="display:none" >'+list.cust_idtype+'  </td>' 
							+ '<td style="display:none" >'+list.cust_idnum+'  </td>' 
							+ '<td style="display:none" >'+list.city+'  </td>' 
							+ '<td style="display:none" >'+list.email+'  </td>' 
							+ '<td style="display:none" >'+list.wechat+'  </td>' 
							+ '<td style="display:none" >'+list.qq+'  </td>' 
							+ '<td style="display:none" >'+list.address+'  </td>' 
							+ '<td style="display:none" >'+list.company+'  </td>' 
							+ '<td style="display:none" >'+list.id_address+'  </td>' 
							+ '<td style="display:none" >'+list.profession+'  </td>'
							+ '<td style="display:none" >'+list.cust_id+'</td>'*/
							+ '</tr>';
					$("#partner").append(a);
					
					
				},
				error: function() {
					var msg = escape(escape("数据异常"));
					location.href="error.action?msg="+msg
				}
			});
	
}
function checkb(){
	var ids = new Array();
	var j = 0;
	if ($("input[name='check']:checkbox:checked").length > 0) {
		box = '';
		$("input[name='check']:checkbox:checked").each(function() {
			ids[j] = $(this).val();
			j = j + 1;
		})
	} 
	$('#cust_idcheck').val(ids)
	/*var  values = {};
	
	//var  check=$(this).attr("checked");//判断是否选中
	if($('#'+id).is(':checked')){ 
	var  value=id+',';
	values.push(value);
	}*/
	
	/*alert();
	var che =$('#cust_idcheck').val() ;
	if($('#'+id).is(':checked')){
		che = id+',';
	}
	$('#cust_idcheck').val()
	alert(che);*/
}
//添加家庭成员时查，勾上
function checkb1(){
	var ids = new Array();
	var j = 0;
	if ($("input[name='check']:checkbox:checked").length > 0) {
		box = '';
		$("input[name='check']:checkbox:checked").each(function() {
			ids[j] = $(this).val();
			j = j + 1;
		})
	} 
	$('#cust_idcheck1').val(ids)
	
}
//添加客户家族的 家庭成员信息
function addFamilymember() {
	
	var family_name = $('#family_name').val().trim();
	/*var cust_sex = $('#cust_sex').val().trim();
	var cust_cell = $('#cust_cell').val().trim();
	var cust_id=$('#cust_id').val().trim();
	var cust_name=$('#cust_name').val().trim();*/

	$.ajax({
		url : "/OMS/addFamilymember.action?cust_id="+cust_id,
		type : "post",
		dataType : "json",
		/*data : {"data":JSON.stringify(a)},*/// 你的formid
        data:$('#formid5').serialize(),
        
		success : function(data) {
			
			if (data.desc == 1) {
				document.getElementById('content').innerHTML=data.msg;
				$('#tishi').modal('show');
				$("#cust_id").val(data.cust_id)
				$("#family_id").val(data.family_id)
				$("#member_id").val(data.member_id)
				/*location.href="skipadd.action";*/
			} else {
				alert("添加失败");
			}
		}
	});
}


//家族信息变更后提交审批
function fammember_submit(){
	var family_id = $("#family_id").val(); 
	$.ajax({
		url : "/OMS/fammember_submit.action",
		type : "post",
		dataType : "json",
		data :$('#formidfammember').serialize(),// 你的formid
		success : function(data) {
			if(data.desc==1){
				alert(data.msg);
				
			}else if(data.desc==2){
				alert(data.msg);
			}else{
				
			}
		},error: function() {
			var msg = escape(escape("数据异常"));
			location.href="error.action?msg="+msg
		}
	});
}
//家族信息提交后审批通过
function fammember_pass(){
	var family_id = $("#family_id").val();
	$.ajax({
		url : "/OMS/fammember_pass.action",
		type : "post",
		dataType : "json",
		data :{"family_id":family_id},// 你的formid

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
//家族信息提交后审批不通过
function fammember_nopass(){
	var family_id = $("#family_id").val();
	$.ajax({
		url : "/OMS/fammember_nopass.action",
		type : "post",
		dataType : "json",
		data :{"family_id":family_id},// 你的formid

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

//跳转添加邮件界面
function addEmail() {
	window.location.href ="send_email_page.action";
}

//千分位校验
function formatNum(num,n){  
    //参数说明：num 要格式化的数字 n 保留小数位      
    num = String(num.toFixed(n));      
    var re = /^((\d){1,3}[.])?(\d)+$|^((\d){1,3})(([,](\d){3})+)[.](\d)+$/;      
    while(re.test(num)) {  
        num = num.replace(re,"$1,$2");  
    }  
    return num;  
} 
function format (num) {
    return (num.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}