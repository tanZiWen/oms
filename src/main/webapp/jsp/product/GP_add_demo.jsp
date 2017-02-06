<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cust_demo_2</title>
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

$(function(){
	var gp_id = $("#gp_id",parent.document).val();
	document.getElementById("gp_id_orgin").value = gp_id;
});

function cancel_gp(){
	parent.location.reload();
}

function add_prod_gp(){
	var selectObj=document.getElementById("gp_id");
	//var gp_name= selectObj.options[selectObj.selectedIndex].innerText;
	var gp_id = $("#gp_id").val();
	
	var prod_id = $("#prod_id",parent.document).val();
	var gp_id_orgin = $("#gp_id_orgin").val();
	
	//alert(prod_id);
	$.ajax({         
  	    url:'add_prod_gp.action',  //后台处理程序     
        type:'post',         //数据发送方式     
        dataType:'json',     //接受数据格式        
        data:{
			//'gp_name':gp_name,
			'gp_id_orgin':gp_id_orgin,
			'gp_id':gp_id,
			'prod_id':prod_id
        },
        success:function (data){
        	if(data.status==1){
        		//alert(data.msg);
        		/* document.getElementById('content').innerHTML=data.msg;
        		$('#myal').modal('show');
        		setTimeout(add_prod_gp(),5000); */
        		parent.location.reload();
        		// parent.history.go(0) ;
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

		<!-- <form action="add_prod_gp.action" id="form1" name="form1" method="post"> -->
                    <ul class="list-group">
                       <li class="list-group-item" style="color:#a52410;background-color: rgb(245,245,245);">
                       <h3 class="panel-title" style="color:#a52410;">
                       <span class="glyphicon glyphicon-plus" aria-hidden="true"  style="margin-right: 6px;"></span>
                      		 修改GP</h3>
                       </li> 
                       
                       <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>GP名称&nbsp;:</span><input id="gp_id_orgin" name="gp_id_orgin" type="hidden"/>
                         <select id="gp_id" name="gp_id">  
                       		<s:iterator  value="#request.GPlist" var="gpInfo">
						        <option value='${gpInfo.gp_id}'><s:property value="#gpInfo.gp_name"/></option>  
					  		</s:iterator>
					    </select>
							<input type="hidden" id="card1">
                       </li>
                    </ul>
          <div class="row text-center">
      <button onclick="cancel_gp()" style="margin-right: 40px;" data-dismiss="modal" class="btn btn-lg btn-default">取消</button>
      <button class="btn btn-lg" data-dismiss="modal" onclick="add_prod_gp()" style="background-color:#5bc0de; color: #fff;">修改</button>
    </div>
<!--   </form>  -->  
<!--end 右-->
<script>
    var _hmt = _hmt || [];
    (function () {
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
            type: "POST", url: "myPhotoSave.aspx", data: "tx=" + myFrame.window.tx, success: function (msg) {
                if (msg != "n") {
                    $("#tou").attr('src', "tx/" + msg + ".png");
                }
            }
        });

    }
   function clockms(id) {
       var yz = $.ajax({
           type: 'post',
           url: 'mess.aspx',
           data: {id:id},
           cache: false,
           dataType: 'text',
           success: function (data) {
              
           },
           error: function () { }
       });
   }
</script>

<!-- 左边变色特效 -->
  <script>
  window.onload=function(){
     var list_grop=document.getElementById('list-grop');
     var list_li=list_grop.getElementsByTagName('li');

     for(var i=0;i<list_li.length;i++){
      // list_li[i].index=i;
      list_li[i].onclick=function(){
        
        for(var j=0;j<list_li.length;j++){
          list_li[j].style.background='white';
        }
        this.style.background='#ace1f1';
      }
     }
  }
    
  </script>
</body></html>