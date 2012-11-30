<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Recognition (SVM)</title>
</head>
<body>
	<% String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()); %>
	<form action="../org.smarthome.activityrecognition/rest/init" method="GET">
	<p>
	<br/>
	<input type="submit" value="Init AR" />
	</p>
	</form>
	<br/>
	<form action="../org.smarthome.activityrecognition/rest/train" method="GET">
	<p>
	<br/>
	<input type="submit" value="Train AR" />
	</p>
	</form>
</body>
</html>