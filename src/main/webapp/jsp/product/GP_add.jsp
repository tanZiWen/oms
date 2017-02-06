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
<script src="/OMS/js/gp.js"></script>
<script>var n = 1;</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/m.js" charset="UTF-8"></script>
</head>

<body data-spy="scroll" data-target=".navbar-example">
<!--nav-->
<%@ include file="../header.jsp" %>




<!--主体-->
      
      <div class="container m0 bod top70" id="zt">
        <div class="row">
        <div class="col-md-6 t0b0 ">
           <ol class="breadcrumb t0b0">
            <li><a href="/OMS">首页</a></li>
            <li class="active">个人中心</li>
          </ol>
        </div>
        <div class="col-md-6 t0b0 txtr">
        
        </div>
      </div>
      
            <div class="row top10 mym">
        <!--左-->
         


          <!--end 左-->
        <!--右-->
         <form id="formid" method = 'post' action="/OMS/addGp.action"   >
        <div class="col-md-8 myright" style="width: 100%;margin:0 auto;padding-bottom: 30px;">
            <!-- 1 -->
            <div class="myright-n">
            <div class="myNav row" style="border-bottom: 1px solid #a52410;">             
               <!-- <a href="#"><i class="glyphicon glyphicon-floppy-saved"></i> 保存 </a> -->
                <h4 style="color: #a52410;">GP信息添加</h4>
          
             </div>
     
      </div>  
        <!-- 2 -->
        <div class="panel panel-default" style="width:100%;">
                     
        <input type="hidden" name="status" id="status" />    
                    <ul class="list-group">
                        
                       <li class="list-group-item" style="background-color: rgb(245,245,245);font-weight: bold;">
                       机构客户基本资料
                       </li> 
                       <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>GP名称</span>
                        <input id="gp_name" maxlength="50" name="gp_name" type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span> 营业执照号</span>
                        <input  maxlength="50" id="bus_license" name="bus_license"  style="width: 75%;border:none;outline: none;">
                       </li>
                       <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>法定代表人</span>
                        <input maxlength="20" id="legal_resp" name="legal_resp" type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>管理方</span>
                        <input id="gp_dept" name="gp_dept" type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                       
                        <li class="list-group-item" style="border-bottom: 0;">
                         <span style="color: red;margin-right: 4px;float: left;">*</span>
                        <span>基金业协会备案号</span>
                        <input  id="fund_no" name="fund_no"  style="width:80%;border:none;outline: none;">
                       </li>
                        <li class="list-group-item">
                        
                        <span>开户行</span>
                        <input  id="open_bank" name="open_bank" type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                         <li class="list-group-item">
                         
                        <span>帐号</span>
                        <input  id="bank_account" name="bank_account"  style="width: 90%;border:none;outline: none;">
                       </li>
                         <li class="list-group-item">
                        
                        <span>注册地址</span>
                        <input id="regit_addr" name="regit_addr" type="text" style="width: 90%;border:none;outline: none;">
                       </li>
                     
                    </ul> 
        </div>
        <div class="row text-center">
      <a href="gp_select.action" style="margin-right: 5px;" data-dismiss="modal" class="btn btn-lg btn-default">取消</a>
       <input type="button" value="保存" data-dismiss="modal"
                             class="btn btn-lg btn-success" onclick="addGp('4')" >
       <input type="button" value="提交审批" data-dismiss="modal" style="background-color:#5bc0de; color: #fff;"
                             class="btn btn-lg btn-success" onclick="addGp('3')" >
    </div>
  </div>
</form>
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
<div id="mydiv" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog" style="width: 400px;">
   <div class="modal-content" style="border-top-right-radius: 10px;border-top-left-radius: 10px;">
         <div class="modal-header" style="height: 45px;width: 100%;line-height: 45px;position: relative;background-color:#6699FF;border-top-right-radius: 6px;border-top-left-radius: 6px;">

          <h4 style="text-align:center;margin:0;padding: 0;color: #fff;">提示信息</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="position: absolute;top:32%;right: 15px;outline: none;color: #333">
                  &times;
            </button>
      
         </div>
      
        <div style="text-align: center;height:70px;font-size:16px;line-height: 70px;width:100%;">
         是否继续添加？
        
        </div>

    
          <div class="row text-center" style="margin-bottom: 15px;">
            <button style="margin-right: 15px;width:60px;height: 35px;border:1px solid #ddd;border-radius: 2px;" data-dismiss="modal"
              class=" " onclick="gpno()">否</button>

            <button id="customerCreateBtn" data-dismiss="modal"
              style="width:60px;height: 35px;background-color: #6699FF;border:none;border-radius: 2px;" onclick="gpyes()">是</button>
          </div>
    
    
  </div>
</div>
</div>
           <!--end 右-->
  <!-- 
          <div class="modal-footer">
    <div class="row text-center">
      <button style="margin-right: 40px;" data-dismiss="modal" class="btn btn-lg btn-default">取消</button>
      <button class="btn btn-lg" data-dismiss="modal" onclick="show_indiv()" style="background-color:#5bc0de; color: #fff;">提交审批</button>
    </div>
  </div> -->
                


<!--底部-->
<nav class="foot navbar-inverse navbar-fixed-bottom">
  <ul class="list-inline">
      <li class="footer-ss">
        <a href="javascript:void(0)" data-container="body" data-toggle="popover" data-placement="top" data-html="true" data-content=" " data-original-title="" title="">更多 &nbsp;<i class="fa fa-angle-down"></i></a></li>
      <li class="footer-ss">在线反馈</li>
      <li class="footer-ss">帮助中心</li>
      <li>Copyright © 2016 </li>
    </ul>
    <ul class="list-inline text-right">
      <li>
     
      </li>
    </ul>
</nav>

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