<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
</head>

<body >

           
                     
                   
      

                    <ul class="list-group">
                        
                       <li class="list-group-item" style="color:#a52410;background-color: rgb(245,245,245);">
                       <h3 class="panel-title" style="color:#a52410;">
                       <span class="glyphicon glyphicon-plus" aria-hidden="true"  style="margin-right: 6px;"></span>
                       添加GP</h3>
                       </li> 
                       <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>GP名称&nbsp:</span>
                        <input type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span> 营业执照号&nbsp:</span>
                        <input type="text" style="width: 75%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>法定代表人&nbsp:</span>
                        <input type="text" style="width: 80%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item">
                         
                        <span>基金业协会备案号&nbsp:</span>
                        <input type="text" style="width:80%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item">
                        
                        <span>开户行&nbsp:</span>
                        <input type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                         <li class="list-group-item">
                         
                        <span>帐号&nbsp:</span>
                        <input type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                         <li class="list-group-item">
                        
                        <span>注册地址&nbsp:</span>
                        <input type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                          <li class="list-group-item">
                         
                        <span>GP系&nbsp:</span>
                        <input type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                       
                    </ul> 
      
    
<!--主体-->
   
          <div class="row text-center">
      <button style="margin-right: 40px;" data-dismiss="modal" class="btn btn-lg btn-default">取消</button>
      <button class="btn btn-lg" data-dismiss="modal" onclick="show_indiv()" style="background-color:#5bc0de; color: #fff;">添加</button>
    </div>
  </div>

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
        



</div></body></html>