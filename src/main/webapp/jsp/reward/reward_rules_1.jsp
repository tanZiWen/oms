<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../css/bootstrap.min.css">
<script src="../../js/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script type="text/javascript">
var flag=0;
function add_production(flag){
  
  if (flag==2){
    $("#add_production").show();
  }
  else if(flag==1){
    $("#add_production").hide();
    $("#production").hide();
  }
  
  else if (flag==3){
    $("#production").show();
  }
  else if(flag==4){
    $("#production_list").show();
  }
  else if (flag==5){
    $("#production").show();
  }
  else if(flag==6){
      $("#family_list").show();
  }
}
function production_detail(){
  window.location.href="http://localhost:8080/OMS/jsp/production_detail.jsp"; 
}
function del_tr(obj){

  var tr=obj.parentNode.parentNode;
  var tbody=tr.parentNode;
  tbody.removeChild(tr);
  
  var tb = document.getElementById('tb_1');
    tb.deleteRow(1);
      
}

</script>

<title>制度管理</title>
</head>
<body>

<!-- 下拉菜单 -->
<%@ include file="../header.jsp"%>

<!-- 新增制度弹窗 -->
<div class="modal" id="myModal8" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
         <div class="row">
          <h4 style="text-align:center;" class="col-md-11">奖金制度</h4>
            <button type="button" class="close col-md-1" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
         </div>
         </div>
         <div class="modal-body" >
        <div class="row">
          <span class="col-md-4" style="text-align: right;">内容</span>&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="text" class="default" style="width: 40%;height:100px;vertical-align: top">
        </div>
        <br />
        
     </div>

        <div class="modal-footer">
          <div class="row text-center">
            <button style="margin-right: 40px;" data-dismiss="modal"
              class="btn btn-lg btn-default">取消</button>

            <button id="customerCreateBtn" data-dismiss="modal"
              class="btn btn-lg btn-success" onclick="add_production(4)">确定</button>
          </div>
        </div>
      </div>
</div>
</div>



<div class="container" style="width:100%;padding-left:0">
   <div class="row">
      <div class="col-md-2" style="margin-top:100px;">
       <div class="navbar-fixed-left">
      <div class="btn-group-vertical">
        <button type="button" class="btn btn-default" data-toggle="modal"  data-target="#myModal8" style="height:70px;" onclick="add_production(5)">新增条款</button>
                
      </div>
       </div>
      </div>

      <div class="col-md-10"  style="min-height:20px;margin-top:90px;padding-top:10px;">
         <div class="container-fluid">
        
  <!-- 表格信息 -->     
        <div class="panel panel-default" style="width:876px;">
           <div class="panel-heading">
              <h3 class="panel-title">奖金制度</h3>
           </div>
           <div class="panel-body">
            <table class="table">
          <tr class="default_1">
          <td>1：     按业绩分配</td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td>
          <img src="../../image/u4.png" data-toggle="modal"  data-target="#myModal8" width="22" height="22" border="0" /> 
          </td>
          <td><button class="close" onclick="del_tr(this)" type="button"> <img src="../../image/u118.png" width="30" height="30" border="0" /> </button> </td>
          </tr>
          <tr class="default_2">
          <td>2：     按工作内容与性质</td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td>
          <img src="../../image/u4.png" data-toggle="modal"  data-target="#myModal8" width="22" height="22" border="0" />
          </td>
          <td><button class="close" onclick="del_tr(this)" type="button"> <img src="../../image/u118.png" width="30" height="30" border="0" /> </button> </td>
          </tr>
            </table>
           </div>
        </div>      
      </div>
      </div>
   </div>
</div>
</body>
</html>





