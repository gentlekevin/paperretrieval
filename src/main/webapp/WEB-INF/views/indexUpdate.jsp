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

</head>

<body>

	<div style="width: 50%; margin: 5% 10% 0% 15%;">
		<form method="post" class="form form-block" action="${ctx}/login">
			<div class="form-group">
				<div class="label">
					<label for="username"> 索引更新时间</label>
				</div>
				<div class="field">
					<input type="text" class="input" id="username" name="username" style="width: 400px"
						size="50" data-validate="required:必填" placeholder="" />
				</div>
			</div>
		
			<div class="form-button">
				<button class="button bg-green" type="button">修改时间</button>
				<button class="button bg-yellow" type="button">立即更新</button>
			</div>
		</form>
	</div>
	
</body>
</html>