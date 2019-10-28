<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<%
	String njxBasePath = request.getContextPath();
%>

<style>
body {
	margin: 0px;
	padding: 0px;
	font-size: 12px;
	font-family: "微软雅黑", Arial, Helvetica, sans-serif;
	background-color: #0891ff;
}

div {
	margin: 0px auto;
}

.main {
	background-image: url(<%=njxBasePath%>/images/main404bg.png);
	background-repeat: no-repeat;
	background-position: center top;
	width: 1000px;
	height: 600px;
	position: relative;
	color: #fff;
}

.main .m1 {
	position: absolute;
	font-size: 24px;
	top: 450px;
	text-align: center;
	width: 100%;
}

.main .m2 {
	position: absolute;
	font-size: 16px;
	top: 500px;
	text-align: center;
	width: 100%;
}

.main .m2 a {
	color: #fff;
	text-decoration: none;
	margin-right: 30px;
}

.main .m2 a:hover {
	text-decoration: underline;
}

.main .m2 .icon {
	background-repeat: no-repeat;
	background-position: left center;
	padding-left: 35px;
}

.main .m2 .icon1 {
	background-image: url(<%=njxBasePath%>/images/m2_icon1.png);
}

.main .m2 .icon2 {
	background-image: url(<%=njxBasePath%>/images/m2_icon2.png);
}
/*------底部样式----------*/
#footer {
	border-bottom: none;
	text-align: center;
	overflow: hidden;
	padding-top: 10px;
	padding-right: 0;
	padding-bottom: 30px;
	padding-left: 0;
	width: 100%;
}

#footer, #footer a {
	color: #a7daff;
	text-decoration: none;
}

#footer a {
	margin-left: 20px;
	margin-right: 20px;
}

#footer a:hover {
	color: #a7daff;
	text-decoration: underline;
}

#footer p {
	line-height: 35px;
}
</style>

<body>
	<!-- 
	截图！整个页面！
	<hr />
	<s:property value="exceptionStack" />
	 -->


	<div class="main">
		<div class="m1">你要的页面已经去火星了...</div>
		<div class="m2">
			<a href="javascript:location.href='<%=njxBasePath %>/home.shtml'" class="icon icon1">返回首页</a><a href="javascript:history.back();" class="icon icon2">上一步</a>
		</div>
	</div>


	<!--底部部分代码 start-->
	<jsp:include page="/view/bottom.jsp" />
	<!--底部部分代码 end-->
</body>

</HTML>
