<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/pagination.css">
    <style>
    .tree li {
        list-style-type: none;
        cursor:pointer;
    }
    table tbody tr:nth-child(odd){background:#F4F4F4;}
    table tbody td:nth-child(even){color:#C00;}
    </style>
  </head>

  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" cert="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><img src="${APP_PATH}/img/logo.png" width="100" style="float:left;margin-top:5px;"><a class="navbar-brand" style="font-size:32px;" href="#">认证流程审批系统</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
                <#include "common/userinfo.ftl">
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
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <#include "common/menu.ftl">
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
              </div>
              <div class="panel-body">
<form class="form-inline" cert="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">登陆账号</div>
      <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" onclick="deleteCerts()" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/cert/add.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
                  <th width="30"><input type="checkbox"></th>
                  <th>任务名称</th>
                  <th>流程名称</th>
                  <th>申请人名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
              </tbody>
              <tfoot>
                 <tr >
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

    <script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH}/jquery/jquery.pagination.js"></script>
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${APP_PATH}/script/docs.min.js"></script>
    <script src="${APP_PATH}/layer/layer.js"></script>
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
                
                $(".table thead :checkbox").click(function(){
                	// 回调方法中关键字this表示DOM对象
                	//var flg = this.checked;
                	var that = this;
                	// 获取资质的复选框
                	$(".table tbody :checkbox").each(function(i, n){
                		n.checked = that.checked;
                	});
                });
                
                pageQuery(0);
            });
            var pagesize = 10;
            
            function deleteCerts() {
                layer.confirm("删除选择的资质信息，是否继续",  {icon: 3, title:'提示'}, function(cindex){
                	
                	var dataObj = {};
                	// 获取选择的资质主键
                    $(".table tbody :checkbox:checked").each(function(i, n){
                    	dataObj["ids["+i+"]"] = n.value;
                    });
                    // 删除资质信息
                    $.ajax({
                        url : "${APP_PATH}/cert/deletes.do",
                        type : "POST",
                        data : dataObj,
                        success : function( r ) {
                            if ( r.success ) {
                                pageQuery(0);
                            } else {
                                layer.msg("资质数据删除失败", {time:1000, icon:5, shift:6});
                            }
                        }
                    });
                    
                    layer.close(cindex);
                }, function(cindex){
                    layer.close(cindex);
                });
            }
            
            function deleteCert( id, certname ) {
                layer.confirm("删除"+certname+"的资质信息，是否继续",  {icon: 3, title:'提示'}, function(cindex){
                    // 删除资质信息
                	$.ajax({
                		url : "${APP_PATH}/cert/delete.do",
                		type : "POST",
                		data : {
                			"id" : id
                		},
                		success : function( r ) {
                			if ( r.success ) {
                				pageQuery(0);
                			} else {
                                layer.msg("资质数据删除失败", {time:1000, icon:5, shift:6});
                			}
                		}
                	});
                    
                	layer.close(cindex);
                }, function(cindex){
                    layer.close(cindex);
                });
            }
            
            function pageQuery(pageIndex) {
            	var loadingIndex = -1;
            	
            	var paramObj = {
                    "pageno" : pageIndex+1,
                    "pagesize" : pagesize
                };
            	
            	$.ajax({
            		url : "${APP_PATH}/auth/pageQuery.do",
            		type : "POST",
            		data : paramObj,
            		beforeSend : function() {
            			loadingIndex = layer.load(2, {time: 10*1000});
            		},
            		success : function( r ) {
            			layer.close(loadingIndex);
            			if ( r.success ) {
            				// 获取分页对象
            				var pageObj = r.page;
            				// 循环遍历资质数据
            				var content = "";
            				$.each(pageObj.datas, function(i, task){
            					// 字符串中相同类型的引号不能嵌套使用
                                content += '<tr>';
	                            content += '    <td>'+(i+1)+'</td>';
	                            content += '    <td><input type="checkbox" value="'+task.id+'"></td>';
	                            content += '    <td>'+task.name+'</td>';
	                            content += '    <td>'+task.pdname+'</td>';
	                            content += '    <td>'+task.username+'</td>';
	                            content += '    <td>';
	                            content += '        <button type="button" onclick="window.location.href=\'${APP_PATH}/auth/show.htm?memberid='+task.memberid+'&taskid='+task.id+'\'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
	                            content += '    </td>';
	                            content += '  </tr>';
            				});
            				
            				$(".table tbody").html(content);
            				
            				// 生成页码
            		        $("#Pagination").pagination(pageObj.totalsize, {
            		            num_edge_entries: 1, //边缘页数
            		            num_display_entries: 4, //主体页数
            		            callback: pageQuery,
            		            current_page:pageIndex,
            		            prev_text:"上一页",
            		            next_text:"下一页",
            		            items_per_page:pagesize //每页显示1项
            		        });
            				
            			} else {
            	            layer.msg("分页查询失败", {time:1500, icon:5, shift:6});
            			}
            		}
            	});
            }
        </script>
  </body>
</html>