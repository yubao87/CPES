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
<link rel="stylesheet" href="${APP_PATH }/css/pagination.css">
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
							<i class="glyphicon glyphicon-th"></i> 数据列表
							<span style="float: right;" id="showTimeS"></span>
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id=querytext class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button id="queryBtn" type="button" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" id="batDeleteBtn" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary" style="float: right;" onclick="window.location.href='${APP_PATH }/user/add.htm'">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox"></th>
										<th>帐号</th>
										<th>名称</th>
										<th>邮箱</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
	<script src="${APP_PATH }/jquery/jquery.pagination.js"></script>
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
			
			$("#queryBtn").click(function(){
				var querytext = $("#querytext");
				if(querytext.val() == ""){
					layer.msg("分页查询失败", {time:1500, icon:5, shift:6},function(){
						querytext.focus();
					});
					return;
				}
				
				pageQuery(0);
			});
			
			pageQuery(0);
			
			//给全选框添加点击事件
			$(".table thead :checkbox").click(function(){
				var flg = this.checked;
				$(".table tbody :checkbox").each(function(index, cb){
					cb.checked = flg;
				});
			});
			
			$("#batDeleteBtn").click(function(){
				//获取选中的用户复选框的数量
				var users = $(".table tbody input:checked");
				if(users.length > 0 ){
					layer.confirm("删除选择的用户信息是否继续?",  {icon: 3, title:'提示'}, function(cindex){
					   
// 						var ids = ""; 
						
// 						$.each(users,function(index, user){
// 							if(index != 0){
// 								ids += "&";
// 							}
// 							ids += "id=" + user.value;
// 						});
						var dataObj = {};
						
						$.each(users,function(index,user){
							dataObj["users[" + index + "].id"] = user.value;
						});
						//删除用户信息
					    $.ajax({
					    	url : "${APP_PATH}/user/doDeletes.do",
					    	type : "POST",
					    	data : dataObj,
					    	success : function(result){
					    		if(result.success){
					    			layer.msg("选择的用户信息删除成功", {time:1500, icon:6},function(){
					    				pageQuery(0);
					    			});
					    		} else {
					    			layer.msg("选择的用户信息删除失败", {time:1500, icon:5});
					    		}
					    	}
					    });
						layer.close(cindex);
					}, function(cindex){
					    layer.close(cindex);
					});
				} else {
					layer.msg("请选择需要删除的用户信息", {time:1500, icon:5, shift:6});
				}
				
			});
		});
		
		var pagesize = 10;
		function pageQuery(pageIndex){
			var lodingIndex = -1;
			var paramObj = {
					"pageno" : pageIndex + 1 ,
					"pagesize" : pagesize
			};
			
			var querytext = $("#querytext");
			if(querytext.val() != ""){
// 				paramObj.queryText = queryText.val();
				paramObj["querytext"] = querytext.val();
			}
			
			$.ajax({
				url : "${APP_PATH}/user/pageQuery.do",
				type : "POST",
				data : paramObj,
				beforeSend : function(){
					lodingIndex = layer.load(2, {time : 10 * 1000});
				},
				success : function(result){
					layer.close(lodingIndex);
					if(result.success){
						var pageObj = result.page;
						//循环遍历用户数据
						//index当前循环的索引，user 当前循环的临时变量
						var concat = "";
						$.each(pageObj.datas,function(index, user){
							concat += '<tr>';
							concat += '	<td>' + (index+1) + '</td>';
							concat += '	<td><input type="checkbox" value="' + user.id + '"></td>';
							concat += '	<td>' + user.loginacct + '</td>';
							concat += '	<td>' + user.username + '</td>';
							concat += '	<td>' + (user.email?user.email:"") + '</td>';
							concat += '	<td>';
							concat += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/user/assign/'+ user.id+ '.htm\'" class="btn btn-success btn-xs">';
							concat += '			<i class=" glyphicon glyphicon-user"></i>';
							concat += '		</button>';
							concat += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/user/edit/' + user.id + '.htm\'" class="btn btn-primary btn-xs">';
							concat += '			<i class=" glyphicon glyphicon-pencil"></i>';
							concat += '		</button>';
							concat += '		<button type="button" onclick="deleteUser(' + user.id + ', \'' + user.loginacct + '\')" class="btn btn-danger btn-xs">';
							concat += '			<i class=" glyphicon glyphicon-remove"></i>';
							concat += '		</button>';
							concat += '	</td>';
							concat += '</tr>';
						});
						
						$(".table tbody").html(concat);
						
						//生成页码
						$("#Pagination").pagination(pageObj.totalsize, {
							num_edge_entries: 1, //边缘页数
							num_display_entries: 4, //主体页数
							callback: pageQuery,
							current_page : pageIndex,
							items_per_page:pagesize,//每页显示1项
							prev_text : "上一页",
							next_text:"下一页"
						}); 
						
					} else {
						layer.msg("分页查询失败", {time:1500, icon:5, shift:6});
					}
				}
			});
		}
		
		function deleteUser(id,loginacct){
			layer.confirm("删除用户【" + loginacct + "】信息是否继续?",  {icon: 3, title:'提示'}, function(cindex){
			    //删除用户信息
			    $.ajax({ 
			    	url : "${APP_PATH}/user/delete.do",
			    	type : "POST",
			    	data : {
			    		id : id
			    	},
			    	success : function(result){
			    		if(result.success){
			    			layer.msg("用户【" + loginacct +"】删除成功", {time:1500, icon:6},function(){
			    				pageQuery(0);
			    			});
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
