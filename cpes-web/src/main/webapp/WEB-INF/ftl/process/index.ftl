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
						<button type="button" onclick="deleteProcDecs()" id="batDeleteBtn" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button id="uploadBtn" type="button" class="btn btn-primary" style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 上传流程文件
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox"></th>
										<th>key</th>
										<th>名称</th>
										<th>版本</th>
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

<!-- 上传表单 开始-->
    <form id="fileForm" action="${APP_PATH}/process/deployProcDef.do" method="post" enctype="multipart/form-data">
        <input type="hidden" name="testid" value="123">
        <input id="ffile" style="display:none;" type="file" name="procDefFile">
    </form>
<!-- 上传表单 结束-->
	<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
	<script src="${APP_PATH }/jquery/jquery.pagination.js"></script>
	<script src="${APP_PATH}/jquery/jquery.form.js"></script>
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
			});// 菜单展示功能结束
				
			pageQuery(0);
			
			<!-- 文件上传开始 --->
			$("#uploadBtn").click(function(){
				// 点击按钮等同于点击文件域
                $("#ffile").click();
            });
			$("#ffile").change(function(enent){
				// 判断文件上传的大小 如果上传多个文件只取第一个文件
				var files = event.target.files,file;
				if (files && files.length > 0) {
					file = files[0];
				}
				// 判断文件上传的大小 file.size, 单位字节Byte
				if ( file.size > 2 * 1024 * 1024 ) {
					layer.msg("上传的流程定义文件不能超出2M的大小", {time:1500, icon:5, shift:6});
					$("#fileForm")[0].reset();
					return;
				}
				// 上传文件
				var loadingIndex = -1;
				$("#fileForm").ajaxSubmit({
				      beforeSubmit : function(){
				      	loadingIndex = layer.load(2,{time: 10 *1000});
				      },
	                  success : function(r){
	                        layer.close(loadingIndex);
	                        if ( r.success ) {
	                            layer.msg("流程定义部署成功", {time:1500, icon:6}, function(){
	           // 表单重置，jQuery没有这个方法，DOM对象有，所以需要转换为Dom对象才可以使用表单重置功能
	                                $("#fileForm")[0].reset();
	                                pageQuery(0);
	                                
	                            });
	                        } else {
	                            layer.msg("流程定义部署失败", {time:1500, icon:5, shift:6});
	                        }
	                    }
				});// ajaxSubmit 结束
				
			});
			<!-- 文件上传结束 --->
			
		});// 文档加载结束
		
			//给全选框添加点击事件
			$(".table thead :checkbox").click(function(){
				var flg = this.checked;
				$(".table tbody :checkbox").each(function(index, cb){
					cb.checked = flg;
				});
			});
			
			// 批量删除
			$("#batDeleteBtn").click(function(){
				//获取选中的流程定义复选框的数量
				var certs = $(".table tbody input:checked");
				if(certs.length > 0 ){
					layer.confirm("确定要删除这 "+certs.length+" 条流程定义信息吗?",  {icon: 3, title:'提示'}, function(cindex){
						// 定义一个空的json对象 用于封装数据
						var dataObj = {};
						$.each(certs,function(index,cert){
							dataObj["ids[" + index + "]"] = cert.value;
						});
						//删除流程定义信息
					    $.ajax({
					    	url : "${APP_PATH}/cert/doDeletes.do",
					    	type : "POST",
					    	data : dataObj,
					    	success : function(result){
					    		if(result.success){
					    			layer.msg("选择的流程定义信息删除成功", {time:1500, icon:6},function(){
					    				pageQuery(0);
					    			});
					    		} else {
					    			layer.msg("选择的流程定义信息删除失败", {time:1500, icon:5});
					    		}
					    	}
					    });// ajax结束
						layer.close(cindex);
					}, function(cindex){
					    layer.close(cindex);
					});
				} else {
					layer.msg("请选择需要删除的流程定义信息", {time:1500, icon:5, shift:6});
				}
				
			});// 批量删除结束
		
		
		// 分页查询 每页显示10条
		var pagesize = 10;
		function pageQuery(pageIndex){
			var lodingIndex = -1;
			var paramObj = {
					"pageno" : pageIndex + 1 ,
					"pagesize" : pagesize
			};
			
			$.ajax({
				url : "${APP_PATH}/process/pageQuery.do",
				type : "POST",
				data : paramObj,
				beforeSend : function(){
					lodingIndex = layer.load(2, {time : 10 * 1000});
				},
				success : function(result){
					layer.close(lodingIndex);
					if(result.success){
						var pageObj = result.page;
						//循环遍历流程定义数据
						//index当前循环的索引，procDef 当前循环的临时变量
						var concat = "";
						$.each(pageObj.datas,function(index, procDef){
							concat += '<tr>';
							concat += '	<td>' + (index+1) + '</td>';
							concat += '	<td><input type="checkbox" value="' + procDef.id + '"></td>';
							concat += '	<td>' + procDef.key + '</td>';
							concat += '	<td>' + procDef.name + '</td>';
							concat += '	<td>' + procDef.version + '</td>';
							concat += '	<td>';
							concat += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/process/showImg.htm?pdid='+procDef.id+'\'" class="btn btn-danger btn-xs">';
							concat += '		<button type="button" onclick="deleteProcDec(\'' + procDef.id + '\', \'' + procDef.name + '\')" class="btn btn-danger btn-xs">';
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
			});// ajax结束
		}//pageQuery 结束
		
		// 删除单个流程定义信息
		function deleteProcDec(id,pdname){
			layer.confirm("删除【" + pdname + "】的流程定义信息是否继续?",  {icon: 3, title:'提示'}, function(cindex){
			    //删除流程定义信息
			    $.ajax({ 
			    	url : "${APP_PATH}/process/delete.do",
			    	type : "POST",
			    	data : {
			    		"id" : id
			    	},
			    	success : function(result){
			    		if(result.success){
			    			layer.msg("流程定义【" + pdname +"】删除成功", {time:1000, icon:6},function(){
			    				pageQuery(0);
			    			});
			    		}else{
			    			layer.msg("流程定义删除失败", {time:1500, icon:5});
			    		}
			    	}
			    });// ajax 结束
				layer.close(cindex);
			}, function(cindex){
			    layer.close(cindex);
			}); // 删除提示结束
		} // 删除单个流程定义信息 结束
				
		// 首页显示时间
        $(function showTime() {
			$("#showTimeS").html(new Date().toLocaleString()); 
			setTimeout(showTime, 1000);
		});
	</script>
</body>
</html>
