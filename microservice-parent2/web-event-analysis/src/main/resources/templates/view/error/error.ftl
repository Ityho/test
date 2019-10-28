<#include "../../init_top.ftl" >
    <title><%=request.getAttribute("javax.servlet.error.status_code")%>错误页面</title>
    <style>
        html{margin: 0;padding: 0;}
        body{margin: 0;padding: 0;background: #2d3349;}
    </style>
</head>
<body>
<img src="${njxBasePath }/images/error/error.gif" width="100%">
<div style="display:none;">
    错误码： ${request.getAttribute("javax.servlet.error.status_code")} <br>
    信息： ${request.getAttribute("javax.servlet.error.message")} <br>
    异常： ${request.getAttribute("javax.servlet.error.exception_type")} <br>
<#--    <%-->
<#--    final  Logger logger = Logger.getLogger(getClass());-->
<#--    logger.error("error message>>>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())+">>>"+request.getAttribute("javax.servlet.error.message"));-->
<#--    logger.error("error exception_type>>>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())+">>>"+request.getAttribute("javax.servlet.error.exception_type"));-->
<#--    %>-->
</div>
<#include "../../buttom.ftl" >
</body>
</html>