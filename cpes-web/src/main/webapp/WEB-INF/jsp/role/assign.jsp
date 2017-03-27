<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="GB18030">
<head>
<meta charset="GB18030">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet"
	href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
<link rel="stylesheet" href="${APP_PATH }/css/main.css">
<link rel="stylesheet" href="${APP_PATH }/ztree/zTreeStyle.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<div>
					<img src="${APP_PATH }/img/logo.png" width="100"
						style="float: left; margin-top: 5px;"><a
						class="navbar-brand" style="font-size: 32px;" href="#">认证流程审批系统</a>
				</div>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li style="padding-top: 8px;"><%@include
							file="/WEB-INF/jsp/common/userinfo.jsp"%>
					</li>
					<li style="margin-left: 10px; padding-top: 8px;">
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
			<%@ include file="/WEB-INF/jsp/common/menu.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 许可数据列表
						</h3>
					</div>
					<div class="panel-body">
						<ul id="permissionTree" class="ztree"></ul>
					</div>
					<div class="panel-footer">
						<button type="button" class="btn btn-primary" onclick="assignPermission()">
							<i class="glyphicon glyphicon-check"></i> 授权
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		</div>
	  </div>
	</div>
	
	<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
	<script src="${APP_PATH }/jquery/jquery.form.js"></script>
	<script src="${APP_PATH }/ztree/jquery.ztree.all-3.5.min.js"></script>
	<script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	<script src="${APP_PATH }/layer/layer.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});

			var setting = {
				check: {
					enable : true //复选框
				},
				async: {
					enable : true,
					url : "${APP_PATH}/permission/loadDatas.do?roleid=${role.id}",
					autoParam:["id","name=n","level=lv"]
				},
				view: {
					selectedMulti: false,
					addDiyDom: function(treeId, treeNode){
						var icoObj = $("#" + treeNode.tId + "_ico");
						if ( treeNode.icon ) {
							icoObj.removeClass("button ico_docu ico_open").addClass("fa fa-fw " + treeNode.icon).css("background","");
						}
					}
				}
			};
			
			$.fn.zTree.init($("#permissionTree"), setting);
			
		});
		
		function assignPermission(){
			var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
			var nodes = treeObj.getCheckedNodes(true);
			var dataObj = {"roleid" : "${role.id}"};
			$.each(nodes, function(index , n){
				dataObj["ids[" + index + "]"] = n.id;
			});
			$.ajax({
				url : "${APP_PATH}/role/doAssign.do",
				type : "POST",
				data : dataObj,
				success : function(result){
					if(result.success){
						layer.msg("角色授权成功", {time:1500, icon:6});
					} else {
						layer.msg("角色授权失败", {time:1500, icon:5, shift:6});
					}
				}
				
			});
		}
	</script>
</body>
</html>
