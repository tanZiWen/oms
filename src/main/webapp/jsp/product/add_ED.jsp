<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add_ED</title>
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
<script type="text/javascript">
	function checkNum(){
		var dept_allot=$("#dept_allot").val();
		if(dept_allot.match(/\D/)){
			//alert("分配金额必须为数字");
			document.getElementById('content').innerHTML="分配金额必须为数字";
			$('#myal').modal('show');
		}
	}
	
	function cancel_ED(){
		parent.location.reload();
	}

	
	function prod_allot(){
		var dept_allot=$("#dept_allot").val();
		if(dept_allot==null || dept_allot== ''){
			document.getElementById('content').innerHTML="请输入分配金额";
			$('#myal').modal('show');
			//alert("请输入分配金额");
			return;
		} 
		
		var prod_id = $("#prod_id",parent.document).val();
		var dept_name = $("#dept_name").val();
		var dept_allot = $("#dept_allot").val();
		
		$.ajax({         
	  	    url:'prodAllot.action',  //后台处理程序     
	        type:'post',         //数据发送方式     
	        dataType:'json',     //接受数据格式        
	        data:{
				'prod_id':prod_id,
				'dept_name':dept_name,
				'dept_allot':dept_allot
	        },
	        success:function (data){
	        	if(data.status==1){
	        		//alert(data.msg);
	        		//是否继续添加	
	        		disp_confirm(data.msg);
	        	
	        		//parent.location.reload();
	        	}else if(data.status==2){
	        		//alert(data.msg);
	        		document.getElementById('content').innerHTML=data.msg;
	        		$('#myal').modal('show');
	        	}
	        },error:function(result){
	        	//alert('系统异常,请稍后再试!');
	        	document.getElementById('content').innerHTML='系统异常,请稍后再试!';
        		$('#myal').modal('show');
	        }
	    }); 
	}
	
	function disp_confirm(msg)
	{
	var r=confirm(msg+'，是否继续添加')
	if (r==true)
	  {
		location.href="/OMS/add_ED.action";
		//prod_allot();
	  }
	else
	  {
		parent.location.reload();
	//location.href="org.action";
	  }
	}
</script>
</head>

<body >
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
<!-- <form action="/OMS/prodAllot.action" id="form1" name="form1" method="post"> -->
                    <ul class="list-group">
                        
                       <li class="list-group-item" style="color:#a52410;background-color: rgb(245,245,245);">
                       <h3 class="panel-title" style="color:#a52410;">
                       <span class="glyphicon glyphicon-plus" aria-hidden="true"  style="margin-right: 6px;"></span>
                       额度分配</h3>
                       </li>
                       <li class="list-group-item">
                        
                        <span>地区&nbsp;:</span>
                        <!-- <input required="required" type="text" id="dept_name" name="dept_name" style="width: 90%;border:none;outline: none;"> -->
                        <select id="dept_name" name="dept_name">  
                       		<s:iterator  value="#request.distDict" var="distDict">
						        <option value='<s:property value="#distDict.dict_value"/>'><s:property value="#distDict.dict_name"/></option>  
					  		</s:iterator>
					    </select>
                       </li>
                       <li class="list-group-item">
                        
                        <span> 金额&nbsp;:</span>
                        <input type="number" maxlength="20" required="required" id="dept_allot" name="dept_allot" style="width: 15%;border:none;outline: none;">
                       </li>
                    </ul> 
               
          <div class="row text-center">
      <button style="margin-right: 40px;" data-dismiss="modal" class="btn btn-lg btn-default" onclick="cancel_ED()">取消</button>
      <button class="btn btn-lg" data-dismiss="modal" onclick="prod_allot()" style="background-color:#5bc0de; color: #fff;">添加</button>
    </div>
    <!-- </form> -->
</body>
</html>