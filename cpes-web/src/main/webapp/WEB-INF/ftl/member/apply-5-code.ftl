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
		  <li role="presentation"><a href="#"><span class="badge">1</span> 基本信息</a></li>
		  <li role="presentation""><a href="#"><span class="badge">2</span> 资质文件上传</a></li>
		  <li role="presentation"><a href="#"><span class="badge">3</span> 邮箱确认</a></li>
		  <li role="presentation" class="active><a href="#"><span class="badge">4</span> 申请确认</a></li>
		</ul>
<!--提交邮箱验证码开始 -->
	<center id="finish">
		<form id="codeForm" action="${APP_PATH}/member/checkcode.do" method="post">
			<div class="donate-holder">
				<div class="icon-area">
					<i class="icon-check"></i>
				</div>
				<strong>1系统已将实名认证码发送到您的认证邮箱中， 请输入您收到的认证码后继续认证流程</strong>
				<div class="input-area">
					<input type="text" name="authcode" id="authcode" value="" placeholder="请输入认证码">
				</div>
			</div>
			<div class="cs-holder">
				<input id="applyBtn" type="button" value="1提交">
			</div>
		</form>
	</center>
<!-- 提交邮箱验证码结束 -->
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
        
        $("#applyBtn").click(function(){
        
	        var loadingIndex = -1;
            $("#fileForm").ajaxSubmit({
                beforeSubmit : function(){
                    loadingIndex = layer.load(2, {time: 10*1000});
                },
                success : function(r){
                alert(2)
                    layer.close(loadingIndex);
                    if ( r.success ) {
                        layer.msg("验证码验证成功", {time:1500, icon:5, shift:6});
                      //  window.location.href = "${APP_PATH}/member/apply-3.htm";
                      // 把center标签的内容改为 您的实名认证申请已提交 请等待人工审核
                      alert(true)
                    } else {
                        layer.msg("验证码错误，申请失败", {time:1500, icon:5, shift:6});
                    }
                }
            });// ajaxSubmit 结束
        });  // applyBtn click 结束
        			
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
<!-- 邮箱认证开始 --->        
       
<!-- 邮箱认证结束 ---> 

	</script>
  </body>
</html>