<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>登陆</title>
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/login">
			用户:<input type="text" name="username"> <br/>
			密码:<input type="password" name="password"> <br/>
			<input type="submit" value="提交">
	</form>
</body>
</html>