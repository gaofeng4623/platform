<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<%--<%@include file="/WEB-INF/views/include/head.jsp" %>--%>
	<link rel="stylesheet" href="${ctxStatic}/css/login/reset.css" />
	<link rel="stylesheet" href="${ctxStatic}/css/login/supersized.css">
	<link rel="stylesheet" href="${ctxStatic}/css/login/login_style.css">
	<link rel="stylesheet" href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css"  />
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
<div id="main">
	<h1 style="margin-bottom:20px; font-size:32px;">${fns:getConfig('productName')}</h1>
	<div class="page-container">
		<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
			<div style="position: relative;"> <i class="icon-user" style="position: absolute;left:65px;top:22px;color:#b3b3b3; font-size:22px;padding:10px 10px 10px 10px;"></i>
				<input type="text" id="username" name="username" class="username" placeholder="用户名" value="">
			</div>
			<div style="position: relative;"> <i class="icon-unlock-alt" style="position: absolute;left:65px;top:22px;color:#b3b3b3; font-size:22px;padding:10px 10px 10px 10px;"></i>
				<input type="password" id="password" name="password" class="password" placeholder="密码" value="">
			</div>
			<button type="submit" style="margin-left:55px;padding-left:25px; letter-spacing: 20px;float:left;-moz-border-radius: 6px 0px 0px 6px;-webkit-border-radius: 6px 0px 0px 6px;border-radius: 6px 0px 0px 6px;">登录</button>
			<button type="reset" style="padding-left:25px; letter-spacing: 20px;float:left; background-color:#fff;color:#666666;border: 1px solid #fff;-moz-border-radius: 0px 6px 6px 0px;-webkit-border-radius: 0px 6px 6px 0px;border-radius: 0px 6px 6px 0px;">重置</button>
			<div class="error"><span>+</span></div>
		</form>
	</div>
	<!-- Javascript -->
	<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
	<script src="${ctxStatic}/common/login/scripts.js"></script>
	<script type="text/javascript">

		$(window).resize(function(){
			$("#main").css({
				top:($(window).height() - 400)/2
			});
		});
		$(window).resize();

	</script>
</body>
</html>