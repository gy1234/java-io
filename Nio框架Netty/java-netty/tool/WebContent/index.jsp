<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>index html</title>
</head>
<body>

	<p>
		手游-皇城战web服务器启动成功! 系统时间:<%= new SimpleDateFormat("yyyy年MM月dd日 HH:kk:ss").format(new Date())%>
	</p>
</body>
</html>