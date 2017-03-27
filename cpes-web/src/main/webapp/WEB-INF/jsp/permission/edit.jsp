<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-header">
	<button id="closeBtn" type="button" class="close" data-dismiss="modal">
		<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">帮助</h4>
</div>
<div class="modal-body">
	<form action="${APP_PATH}/permission/update.do" method="POST"
		role="form">
		<input type="hidden" name="id" value="${permission.id }" />
		<div class="form-group">
			<label for="fname">许可名称</label> <input type="text"
				class="form-control" name="name" id="fname"
				value="${permission.name}" placeholder="请输入许可名称">
		</div>
		<div class="form-group">
			<label for="fname">链接地址</label> <input type="text"
				class="form-control" name="url" id="furl" value="${permission.url }"
				placeholder="请输入链接地址">
		</div>
	</form>
</div>

<div class="modal-footer">
	<button id="uptBtn" type="button" class="btn btn-success">
			<i class="glyphicon glyphicon-plus"></i> 修改
		</button>
		<button type="button" class="btn btn-danger">
			<i class="glyphicon glyphicon-refresh"></i> 重置
		</button>
</div>
<script type="text/javascript">
	$("#uptBtn").click(updateSubmitFrom);

	function updateSubmitFrom() {
		var fname = $("#fname");
		if (fname.val() == "") {
			layer.msg("许可名称不能为空", {
				time : 1500,
				icon : 5,
				shift : 6
			}), function() {
				fname.focus();
			};
			return;
		}

		<c:if test="${param.level == '2'}">
		var furl = $("#furl");
		if (furl.val() == "") {
			layer.msg("链接地址不能为空", {
				time : 1500,
				icon : 5,
				shift : 6
			}), function() {
				furl.focus();
			};
			return;
		}
		</c:if>

		var lodingIndex = -1;
		//获取树形数据选择的节点
		var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
		var nodes = treeObj.getSelectedNodes();
		var node = nodes[0];
		$("form:eq(1)").ajaxSubmit({
			beforeSubmit : function() {
				lodingIndex = layer.load(2, {
					time : 10 * 1000
				});
			},
			success : function(result) {
				layer.close(lodingIndex);
				if (result.success) {
					layer.msg("修改成功", {
						time : 1500,
						icon : 6
					}, function() {
						//window.location.href = "${APP_PATH}/permission/index.htm";
						treeObj.reAsyncChildNodes(null, "refresh");
						$("#closeBtn").click();
					});
				} else {
					layer.msg("修改失败", {
						time : 1500,
						icon : 5,
						shift : 6
					});
				}
			}
		});
		return;
	}
</script>
