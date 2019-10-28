<%@ page language="java" pageEncoding="gbk" %>
<%@ page import="java.io.PrintStream"%>  
<%@ page import="java.io.ByteArrayOutputStream"%>  
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="org.apache.log4j.Logger" %>  
<%@ page import="java.text.SimpleDateFormat"%>  
<%
	String njxBasePath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta charset="gbk">
    <title></title>
    <link rel="stylesheet" href="<%=njxBasePath %>/css/style.css" />
    <style>
        html{margin: 0;padding: 0;}
        body{margin: 0;padding: 0;}
    </style>
</head>
<body>
    <div style="line-height: 40px;margin:40% auto 0px;width: 280px;">微博授权出现故障，请稍后再试！！！</div>
    <img style="margin:20% auto;display:block; width: 200px;" alt="" src="<%=njxBasePath %>/images/app-bq1.png">
    <%@ include file="../../buttom.jsp" %>
</body>
</html>