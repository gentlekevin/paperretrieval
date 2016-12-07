<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>学术资源统一检索系统</title>
<link rel="stylesheet" href="${ctx}/static/styles/pintuer.css">
<script src="${ctx}/static/js/jquery.js"></script>
<script src="${ctx}/static/js/pintuer.js"></script>

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico"
	rel="shortcut icon" />
<link href="#/favicon.ico" rel="bookmark icon" />
<script type="text/javascript">
	function subForm() {
		$("#searchForm").submit();
	}
</script>
<style>
.demo-nav.fixed.fixed-top {
	z-index: 8;
	background: #fff;
	width: 100%;
	padding: 0;
	border-bottom: solid 3px #0a8;
	-webkit-box-shadow: 0 3px 6px rgba(0, 0, 0, .175);
	box-shadow: 0 3px 6px rgba(0, 0, 0, .175);
}
</style>
</head>

<body>
	<!--顶部-->
	<div class="layout bg-black bg-inverse">
		<div class="container height-large">
			欢迎使用<a href="${ctx}/">统一检索系统</a>
		</div>
</div>
	
	<!--导航-->
	<div class="demo-nav padding-big-top padding-big-bottom fixed">
		<div class="container padding-top padding-bottom">

			<div class="line">
				<div class="xl12 xs3 xm3 xb2">
					<button class="button icon-navicon float-right"
						data-target="#header-demo"></button>
					<a target="_blank" href="#"><img src="${ctx}/static/images/logo.png"
						alt="paperretrieval" /></a>
				</div>
				<div class=" xl12 xs9 xm9 xb10 nav-navicon" id="header-demo">

					<div class="xs8 xm6 xb5 padding-small">
						<ul class="nav nav-menu nav-inline nav-big">
							<li><a href="${ctx}">首页</a></li>

							<li class="active"><a href="#">关于实验室<span class="arrow"></span></a>
								<ul class="drop-menu">
									<li><a href="#">介绍</a></li>
									<li><a href="#">实验室人员<span class="arrow"></span></a>
										<ul>
											<li><a href="#">博士</a></li>
											<li><a href="#">研究生</a></li>
										</ul></li>
									<li><a href="#">科技成果介绍</a></li>
								</ul></li>
							<li><a href="#">加入我们</a></li>
						</ul>
					</div>
				</div>

			</div>
		</div>
	</div>
	<%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%>
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
		</div>
	<%
	}
	
	%>
	<div style="width: 50%; margin: 5% 10% 0% 15%;">
		<form method="post" class="form form-block" action="${ctx}/login">
			<div class="form-group">
				<div class="label">
					<label for="username"> 账号</label>
				</div>
				<div class="field">
					<input type="text" class="input" id="username" name="username" style="width: 400px"
						size="50" data-validate="required:必填" placeholder="手机/邮箱/账号" />
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="password"> 密码</label>
				</div>
				<div class="field">
					<input type="password" class="input" id="password" name="password" style="width: 400px"
						size="50" data-validate="required:必填" placeholder="请输入密码" />
				</div>
			</div>
			<div class="form-button">
				<button class="button bg-green" type="submit">登录</button>
				<button class="button bg-yellow form-reset" type="reset"> 重设</button>
			</div>
		</form>
	</div>
	
</body>
</html>