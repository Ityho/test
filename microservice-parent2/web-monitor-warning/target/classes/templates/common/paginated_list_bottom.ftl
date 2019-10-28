<#--<%@ page isELIgnored="false"%>-->
<#--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>-->
<#--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>-->
<#assign base = request.contextPath />
<link rel="stylesheet" type="text/css" href="${base}/css/bottom_styleNew.css" media="screen"/>
	<div style="float:right;padding-right:25px;margin-top:-15px;width:auto;font-size: 12px;min-height:60px;">
		<div id="Pagination"></div>
		<input name="page" type="hidden" value="${page}" />
	</div>
	<script type="text/javascript">
		<#--/* $(function() {-->
			<#--$("#Pagination").twbsPagination({totalPages: ${maxpage},-->
				<#--startPage: ${page},-->
				<#--visiblePages: 10,-->
				<#--hrefVariable: ${page},-->
				 <#--first: '首页',-->
		         <#--prev: '上一页',-->
		         <#--next: '下一页',-->
				<#--onPageClick: function (event, page) {-->
					<#--GotoPage(page);-->
				<#--}-->
			<#--});-->
		<#--}); */-->
	</script>