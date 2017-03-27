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

	<nav class="navbar navbar-inverse navbar-fixed-top" cert="navigation">
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
					<li style="padding-top: 8px;">
						<#include "common/userinfo.ftl">
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
			<#include "common/menu.ftl">
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
							<span style="float: right;" id="showTimeS"></span>
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" cert="form" style="float: left;">
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
						<button type="button" onclick="deleteCerts()" id="batDeleteBtn" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary" style="float: right;" onclick="window.location.href='${APP_PATH }/cert/add.htm'">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th style="width: 80px; text-align: center;">证件图片</th>
										<th style="text-align: center;">商业公司</th>
										<th style="text-align: center;">个体工商户</th>
										<th style="text-align: center;">个人经营</th>
										<th style="text-align: center;">政府及非营利组织</th>
									</tr>
								</thead>
								<tbody>
									<#list certs as cert>
										<tr style=" text-align: center;">
											<td width="80">${cert.name}</td>
											<td><input type="checkbox" data-acctype="0" data-certid="${cert.id}" style="width: 20px;height: 20px;" /></td>
											<td><input type="checkbox" data-acctype="1" data-certid="${cert.id}" style="width: 20px;height: 20px;" /></td>
											<td><input type="checkbox" data-acctype="2" data-certid="${cert.id}" style="width: 20px;height: 20px;" /></td>
											<td><input type="checkbox" data-acctype="3" data-certid="${cert.id}" style="width: 20px;height: 20px;" /></td>
										</tr>
										</center>
									</#list>
								</tbody>
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
			
			//对已经分类的数据进行复选框勾选
			<#list certtypes as certtype>
				var box = $(".table :checkbox[data-acctype='${certtype.acctype}'][data-certid='${certtype.certid}']");
				box[0].checked = true;
			</#list>
			$(".table :checkbox").click(function(){
				var flag = this.checked;
				var acctype = $(this).attr("data-acctype");
				var certid = $(this).attr("data-certid");
				if(flag){
					$.ajax({
						url : "${APP_PATH}/certtype/insert.do",
						type : "POST",
						data : {
							"acctype" : acctype,
							"certid" : certid
						}
					});
				} else {
					$.ajax({
						url : "${APP_PATH}/certtype/delete.do",
						type : "POST",
						data : {
							"acctype" : acctype,
							"certid" : certid
						}
					});
				}
			});
		});
						
		// 首页显示时间
        $(function showTime() {
			$("#showTimeS").html(new Date().toLocaleString()); 
			setTimeout(showTime, 1000);
		});
	</script>
</body>
</html>
