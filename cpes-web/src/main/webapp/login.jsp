<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--  ^_^ 2017年3月4日 ^_^ 下午12:50:37 ^_^ -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="GB18030">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keys" content="">
<meta name="author" content="">
<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
<link rel="stylesheet" href="${APP_PATH }/css/login.css">
<style>
</style>
</head>
<body>
<!--   logo及认证开始 -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<div>
					<img src="img/logo.png" width="100"
						style="float: left; margin-top: 5px;"><a
						class="navbar-brand" style="font-size: 32px;" href="#">认证流程审批系统</a>
				</div>
			</div>
		</div>
	</nav>
<!--   logo及认证结束 --> 
    
<!-- 登录表单开始 -->
	<div class="container">

		<form class="form-signin" role="form">
			<h2 class="form-signin-heading">
				<i class="glyphicon glyphicon-leaf"></i> 用户登录
			</h2>
			<div class="form-group has-success has-feedback">
				<input type="text" class="form-control" id="floginacct"
					value="${ cookie.nameCook.value }" placeholder="请输入登录账号" autofocus> <span
					class="glyphicon glyphicon-ok form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="password" class="form-control" id="fuserpswd"
					value="${ cookie.pswdCook.value }" placeholder="请输入登录密码" style="margin-top: 10px;"> 
	<%-- 			value="${ empty username ? '' : username }"  --%>
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<select id="usertype" class="form-control">
					<option value="0">会员登录</option>
					<option value="1">管理登录</option>
				</select>
			</div>
			<div class="checkbox">
				<label> 
	<%--         value="${ cookie.nameCook.value }"/> --%>		
				<input type="checkbox" value="remember-me">
					记${cookie.nameCook.value}住我
				</label>
			</div>
			<a class="btn btn-lg btn-success btn-block" id="loginBtn"><i
				class="glyphicon glyphicon-log-in"></i> 登录</a>
		</form>
	</div>
<!-- 登录表单结束 -->	
	<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
	<script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/layer/layer.js"></script>
	<script type="text/javascript">
	
		// 提取事件加enter登录功能 把事件提取 加上enter的对应的值就可以了
	    $(function(){
		    $("#loginBtn").click(login);
		    $(window).keydown(function(e){
		    	if(e.which==13){
		    		login();
		    	}
		    });
	    });

		// login 登录方法验证 用于点击登录按钮触发事件
		var login = function() {
			var floginacct = $("#floginacct");
			if (floginacct.val().trim() == "") {
// 引入layer.js 放到jQuery的下面， 第一个参数是提示信息，第二个参数是时间，第五个图标是哭脸(第六个是笑脸) ，第三个参数是回调函数 
				layer.msg("登陆账号不能为空，请输入", {time:1500, icon:5, shift:6}, function(){
	            	floginacct.focus();
	            });
				return false;
			}

			var fuserpswd = $("#fuserpswd");
			if (fuserpswd.val().trim() == "") {
				 layer.msg("登陆密码不能为空，请输入", {time:1500, icon:5, shift:6}, function(){
		            	fuserpswd.focus();
		         });
				return false;
			}

			var loadingIndex = -1;
			// 获取用户类型 并判断
			var usertype = $("#usertype").val();
			var url = "";
			if (usertype == "1") {
				url = "${APP_PATH}/dologin.do";
			} else {
				url = "${APP_PATH}/dologinByMember.do";
			}
			/*  使用ajax实现数据的操作 */
			$.ajax({
				type : "POST",
				url : url,
				data : {
					"loginacct" : floginacct.val(),
					"userpswd" : fuserpswd.val()
				},
				beforeSend : function() {
					loadingIndex = layer.load(2, {time : 10 * 1000});
				},
				success : function(result) {
					layer.close(loadingIndex);
					if (result.success) {
						//判断用户类型 跳转那个页面
						if ( usertype == '0' ) {
                    		window.location.href = "${APP_PATH}/member.htm";
	                    } else {
	                    	window.location.href = "${APP_PATH}/main.htm";
	                    }
					} else {
						layer.msg("用户信息不存在，请重新输入", {time:1500, icon:5, shift:6}, function(){
	                    	floginacct.focus();
	                    });
					}
				}//success 结束
			});//ajax 结束
		};// var login方法结束
		
	</script>
</body>
</html>