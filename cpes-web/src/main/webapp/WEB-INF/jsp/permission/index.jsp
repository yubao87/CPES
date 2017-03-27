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
							<i class="glyphicon glyphicon-th"></i> 许可数据列表 数据列表
							<span style="float: right;" id="showTimeS"></span>
						</h3>
					</div>
					<div class="panel-body">
						<ul id="permissionTree" class="ztree"></ul>
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
				async: {
					enable : true,
					url : "${APP_PATH}/permission/loadDatas.do",
					autoParam:["id","name=n","level=lv"]
				},
				view: {
					selectedMulti: false,
					addDiyDom: function(treeId, treeNode){
						var icoObj = $("#" + treeNode.tId + "_ico");
						if ( treeNode.icon ) {
							icoObj.removeClass("button ico_docu ico_open").addClass("fa fa-fw " + treeNode.icon).css("background","");
						}
					},
					addHoverDom: function(treeId, treeNode){
						//treeNode对象的ztree的树形节点对象，包含了树形节点的所有属性字段和ztree自己生成的属性
						
						
						var aObj = $("#" + treeNode.tId + "_a");
						aObj.attr("href", "javascript:;");
						if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
						var s = '<span id="btnGroup'+treeNode.tId+'">';
						if ( treeNode.level == 0 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
						} else if ( treeNode.level == 1 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							if (treeNode.children.length == 0) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
							}
							//s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="${APP_PATH}/permission/add.htm?pid=' + treeNode.id +'" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="${APP_PATH}/permission/add.htm?pid=' + treeNode.id +'" data-toggle="modal" data-target="#myModal" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>'; 
						} else if ( treeNode.level == 2 ) {
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="${APP_PATH}/permission/edit/' + treeNode.id + '.htm?level='+ treeNode.level +'" data-toggle="modal" data-target="#myModal" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
							s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteNode('+ treeNode.id +')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
						}
		
						s += '</span>';
						aObj.after(s);
					},
					removeHoverDom: function(treeId, treeNode){
						$("#btnGroup"+treeNode.tId).remove();
					}
				}
			};
			
			$.fn.zTree.init($("#permissionTree"), setting);
			
			// 由于多个链接指向同一个模态框，为防止缓存影响使用效果，一定要清除模态框内容
			$("#myModal").on("shown.bs.modal", function(){
				$(this).removeData("bs.modal");
			});
			
		});
		
		function deleteNode( id ){
			layer.confirm("删除当前选择的许可信息是否继续?",  {icon: 3, title:'提示'}, function(cindex){
				
				//删除许可信息
			    $.ajax({
			    	url : "${APP_PATH}/permission/delete.do",
			    	type : "POST",
			    	data : {
			    		"id" : id
			    	},
			    	success : function(result){
			    		if(result.success){
			    			layer.msg("许可信息删除成功", {time:1500, icon:6},function(){
			    				var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
			    				treeObj.reAsyncChildNodes(null, "refresh");
			    			});
			    		} else {
			    			layer.msg("许可信息删除失败", {time:1500, icon:5});
			    		}
			    	}
			    });
				layer.close(cindex);
			}, function(cindex){
			    layer.close(cindex);
			});
		}
		
		// 首页显示时间
        $(function showTime() {
			$("#showTimeS").html(new Date().toLocaleString()); 
			setTimeout(showTime, 1000);
		});
	</script>
</body>
</html>
