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
	<link rel="stylesheet" href="${APP_PATH}/css/theme.css">
	<style>

	</style>
  </head>
  <body>
 <div class="navbar-wrapper">
      <div class="container">
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			  <div class="container">
				<div class="navbar-header">
				  <a class="navbar-brand" href="#" style="font-size:32px;">互联网众筹系统</a>
				</div>
            <div id="navbar" class="navbar-collapse collapse" style="float:right;">
              <ul class="nav navbar-nav">
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> 张三<span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="#">会员中心</a></li>
                    <li><a href="#">消息 <span class="badge badge-success">42</span></a></li>
                    <li class="divider"></li>
                    <li><a href="#">退出系统</a></li>
                  </ul>
                </li>
              </ul>
            </div>
			  </div>
			</nav>

      </div>
    </div>

    <div class="container theme-showcase" role="main">
      <div class="page-header">
        <h1>实名认证 - 申请</h1>
      </div>

		<ul class="nav nav-tabs" role="tablist">
		  <li role="presentation" class="active"><a href="#"><span class="badge">1</span> 基本信息</a></li>
		  <li role="presentation"><a href="#"><span class="badge">2</span> 资质文件上传</a></li>
		  <li role="presentation"><a href="#"><span class="badge">3</span> 邮箱确认</a></li>
		  <li role="presentation"><a href="#"><span class="badge">4</span> 申请确认</a></li>
		</ul>
 <!-- 表单开始-->       
		<form id="applyForm" action="${APP_PATH}/member/updateInfo.do" role="form" style="margin-top:20px;">
		  <div class="form-group">
			<label for="exampleInputEmail1">真实姓名</label>
			<input type="text" class="form-control" name="realname" id="frealname" placeholder="请输入真实姓名">
		  </div>
		  <div class="form-group">
			<label for="exampleInputPassword1">身份证号码</label>
			<input type="text" class="form-control" name="cardnum" id="fcardnum" placeholder="请输入18位身份证号码">
		  </div>
		  <div class="form-group">
			<label for="exampleInputPassword1">电话号码</label>
			<input type="text" class="form-control" name="tel" id="ftel" placeholder="请输入手机号码或者座机">
		  </div>
		  
		  <button type="button" id="applyBtn" class="btn btn-success">下一步</button>
		</form>
 <!-- 表单开始-->   
		<hr>
      <!-- Main jumbotron for a primary marketing message or call to action -->
      <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
          <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
          <li data-target="#carousel-example-generic" data-slide-to="1"></li>
          <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner" role="listbox">
          <div class="item active">
            <img data-src="holder.js/1140x500/auto/#777:#555/text:广告1" alt="广告1">
          </div>
          <div class="item">
            <img data-src="holder.js/1140x500/auto/#666:#444/text:广告2" alt="广告2">
          </div>
          <div class="item">
            <img data-src="holder.js/1140x500/auto/#555:#333/text:广告3" alt="广告3">
          </div>
        </div>
        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
          <span class="glyphicon glyphicon-chevron-left"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
          <span class="glyphicon glyphicon-chevron-right"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
	  <hr>
      <footer>
        <p>&copy; 2014 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
      </footer>
    </div> <!-- /container -->
	
    <script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/jquery/jquery.form.js"></script>
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH}/script/docs.min.js"></script>
	<script src="${APP_PATH}/layer/layer.js"></script>
	<script>
        $('#myTab a').click(function (e) {
          e.preventDefault()
          $(this).tab('show')
        });     
        
        // 提交按钮点击事件开始
        $("#applyBtn").click(function(){
    		var frealname = $("#frealname");    
	        if (frealname.val() == "") {
	        	layer.msg("真实姓名不能为空，请输入！", {time:1500, icon:5, shift:6},
	        	function(){frealname.focus();
	        	});
	        return;
	        }
	        var loadingIndex = -1;
	        // 提交表单时 判断用户信息有没有更新成功，更新成功以后 调转至下一个申请页面 上传资质文件
            $("#applyForm").ajaxSubmit({
                beforeSubmit : function(){
                    loadingIndex = layer.load(2, {time: 10*1000});
                },
                success : function(r){
                    layer.close(loadingIndex);
                    if ( r.success ) {
                        window.location.href = "${APP_PATH}/member/apply-2-certfile.htm";
                    } else {
                        layer.msg("用户信息保存失败", {time:1500, icon:5, shift:6});
                    }
                }
            });// ajaxSubmit 结束
        });   // 提交按钮点击事件 结束
	</script>
  </body>
</html>