<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="GB18030">
  <head>
    <meta charset="GB18030">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/main.css">
	<link rel="stylesheet" href="${APP_PATH }/css/doc.min.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><img src="${APP_PATH }/img/logo.png" width="100" style="float:left;margin-top:5px;"><a class="navbar-brand" style="font-size:32px;" href="#">认证流程审批系统</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
				<%@include file="/WEB-INF/jsp/common/userinfo.jsp" %>
			</li>
            <li style="margin-left:10px;padding-top:8px;">
				<button type="button" class="btn btn-default btn-danger">
				  <span class="glyphicon glyphicon-question-sign"></span> 帮助
				</button>
			</li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        	<%@ include file="/WEB-INF/jsp/common/menu.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="${APP_PATH }/main.htm">首页</a></li>
				  <li><a href="${APP_PATH }/user/index.htm">数据列表</a></li>
				  <li class="active">分配角色</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">角色列表<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
			  	<center>
				<form class="form-inline">
				  <div class="form-group">
				  	<select id="leftRoles" multiple="multiple" class="form-control" size="10" style="overflow: hidden; width: 140px;">
				  		<c:forEach items="${unAssignList }" var="role">
				  			<option value="${role.id }">${role.name }</option>
				  		</c:forEach>
				  	</select>
				  </div>
				  <div class="form-group">
				  	<ul id="btnGroup" style="margin-right: 40px;">
				  		<li id="left2RightBtn" class="btn btn-default"><i class="glyphicon glyphicon-chevron-right"></i></li>
				  		<br/>
				  		<br/>
				  		<li id="right2LeftBtn" class="btn btn-default"><i class="glyphicon glyphicon-chevron-left"></i></li>
				  	</ul>
				  	
				  </div>
				  <div class="form-group">
					<select id="rightRoles" multiple="multiple" class="form-control" size="10" style="overflow: hidden; width: 140px">
				  		<c:forEach items="${assignList }" var="role">
				  			<option value="${role.id }">${role.name }</option>
				  		</c:forEach>
				  	</select>
				  </div>
				</form>
				</center>
			  </div>
			</div>
        </div>
      </div>
    </div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			<h4 class="modal-title" id="myModalLabel">帮助</h4>
		  </div>
		  <div class="modal-body">
			<div class="bs-callout bs-callout-info">
				<h4>没有默认类</h4>
				<p>警告框没有默认类，只有基类和修饰类。默认的灰色警告框并没有多少意义。所以您要使用一种有意义的警告类。目前提供了成功、消息、警告或危险。</p>
			  </div>
			<div class="bs-callout bs-callout-info">
				<h4>没有默认类</h4>
				<p>警告框没有默认类，只有基类和修饰类。默认的灰色警告框并没有多少意义。所以您要使用一种有意义的警告类。目前提供了成功、消息、警告或危险。</p>
			  </div>
		  </div>
		  
		  <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary">Save changes</button>
		  </div>
		 
		</div>
	  </div>
	</div>
    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	<script src="${APP_PATH }/layer/layer.js"></script>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			    
			    $("#left2RightBtn").click(function(){
			    	var selRoles = $("#leftRoles :selected");
			    	if( selRoles.length == 0){
			    		layer.msg("请选择需要分配的角色数据", {time:1500, icon:5, shift:6});
			    		return;
			    	}
			    	
			    	var dataObj = {"userid":"${user.id}"};
			    	
			    	$.each(selRoles ,function( index, n){
			    		dataObj["ids[" + index + "]"] = n.value;
			    	});
			    	
			    	//新增关系
			    	$.ajax({
			    		url : "${APP_PATH}/user/assignRoles.do",
			    		type : "POST",
			    		data : dataObj,
			    		success : function(result){
			    			if(result.success){
			    				$("#rightRoles").append(selRoles);
			    			}
			    		}
			    	});
			    	
			    	
			    });
			    
			    $("#right2LeftBtn").click(function(){
			    	var selRoles = $("#rightRoles :selected");
			    	if( selRoles.length == 0){
			    		layer.msg("请选择需要分配的角色数据", {time:1500, icon:5, shift:6});
			    		return;
			    	}
			    	
			    	var dataObj = {"userid":"${user.id}"};
			    	$.each(selRoles, function(index,n){
			    		dataObj["ids[" + index + "]"] = n.value;
			    	});
			    	//删除关系
			    	$.ajax({
			    		url : "${APP_PATH}/user/unAssignRoles.do",
			    		type : "POST",
			    		data : dataObj,
			    		success : function(result){
			    			if(result.success){
			    				$("#leftRoles").append(selRoles);
			    			}
			    		}
			    	});
			    	
			    });
            });
            
        </script>
  </body>
</html>
