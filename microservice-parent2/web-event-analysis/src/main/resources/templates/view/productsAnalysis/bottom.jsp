<%@page import="com.xd.iis.sp.weidingyue.SystemConfig"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String base = request.getContextPath();

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	SystemConfig systemConfig = (SystemConfig) wac.getBean("systemConfig");
	String staticResourcePath = systemConfig.getFileviewPath() + "web";
%>
 <div class="h35 clear"></div>
	<div id="footer"> 
	<p><a href="<%=base %>/aboutUs.shtml">关于我们</a>  |  <a href="<%=base %>/contectUs.shtml">联系我们</a>  |  <a href="<%=base %>/help.shtml">帮助中心</a></p> 
	<p>
		<div style="width:300px;margin:0 auto; padding:20px 0;">
			<a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=31011502031598" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;">
				<img src="<%=request.getContextPath() %>/images/beian.png" style="float:left;"/>
				<p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#939393;">沪ICP备09090754号-24</p>
				<%--cnzz统计代码start  --%>
<!-- 
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?bda531e3d034b85211d0555ecaa6517b";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script> -->
				
				<script type="text/javascript" src="<%=staticResourcePath%>/js/cnzz.js"></script>
				<%--cnzz统计代码end  --%>
			</a>
		</div>
	</p>
	<!-- 
	<p><a href="http://www.cyberpolice.cn/" target="_blank">
	<img src="<%=staticResourcePath %>/images/police.png" width="102" height="35"></a>
	<a href="http://www.zx110.org/" target="_blank">
	<img src="<%=staticResourcePath %>/images/zx110.png" width="79" height="35"></a>
	</p>
	 -->
	
	</div>
<%@ include file="kf_chat.jsp" %>