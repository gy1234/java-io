<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>index html</title>
</head>
<body>
	<h3>
		web服务器启动成功! 系统时间:<%=new SimpleDateFormat("yyyy年MM月dd日 HH:kk:ss")
					.format(new Date())%>
	</h3>
	<h3>数据转换</h3>
	<form action="convert.do" method="get" onsubmit="return subDataCheck()">
		<br>转换原始文件名: <input type="text" id="sfid" name="sfname" /><br>转换方式:
		<input type="radio" name="cmethod" value="1" checked="checked" />
		AMF3转JSON<br> <input type="submit" value="开始转换" />
	</form>
	<script type="text/javascript">
		function subDataCheck() {
			var sfname = document.getElementById("sfid").value;
			if (!sfname) {
				alert("请填写文件名!");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>