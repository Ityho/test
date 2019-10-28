<!DOCTYPE html>

<#--<%@page import="com.xd.iis.sp.weidingyue.SystemConfig"%>-->
<#--<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>-->
<#--<%@page import="org.springframework.web.context.WebApplicationContext"%>-->
<#--<%@page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>-->
<#--<%@ taglib prefix="s" uri="/struts-tags" %>-->
<#--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>-->
<#--<%-->
<#--String njxBasePath = request.getContextPath();-->
<#--String base = request.getContextPath();-->
<#---->
<#--WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());-->
<#--SystemConfig systemConfig = (SystemConfig) wac.getBean("systemConfig");-->
<#--String staticResourcePath = njxBasePath;-->
<#--String staticResourcePathH5 = SysConfig.CDN_FILE_VIEW_PATH_H5;-->
<#--staticResourcePathH5 = njxBasePath;-->
<#--Long SYSTEM_INIT_TIME = SystemConfig.SYSTEMINITTIME;-->
<#--%>-->
<#--<#assign staticResourcePathH5=${SysConfig.CDN_FILE_VIEW_PATH_H5}>-->
<#--<#assign SYSTEM_INIT_TIME=${SysConfig.SYSTEM_INIT_TIME}>-->
<#--<#assign SYSTEM_INIT_TIME="1557361961778">-->

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="renderer" content="webkit">
    <meta name="format-detection" content="telephone=no" />
    <meta property="wb:webmaster" content="ff5fa17a9f9796d9" /><!-- 微博 -->
    <meta property="qc:admins" content="2404321175711636" /><!-- QQ -->
    <meta name="description" content = "新浪舆情是中国最大的微博舆情舆论服务平台，提供网页、微博、微信等全媒体舆情信息网以及网络舆情,舆情负面事件分析,舆情管理,军犬舆情, 舆情报告,舆情监控, 舆情监测软件,海外媒体监测,竞争情报服务等。">
    <link rel="shortcut icon" sizes="16x16" href="${staticResourcePathH5}/images/addhomescreen/icon-16x16.png">
    <link rel="apple-touch-icon-precomposed" href="${staticResourcePathH5}/images/addhomescreen/icon-152x152.png">
    <link rel="shortcut icon" sizes="196x196" href="${staticResourcePathH5}/images/addhomescreen/icon-196x196.png">
    <!-- 	<title>新浪微热点官方网站（wrd.cn）-社会化大数据搜索|热度指数|传播分析|口碑分析|微博情绪</title> -->
    <title>微热点</title>
    <div id="shareImage" style="display:none;"><img src="${staticResourcePathH5}/images/fenxiang/weibowyq-icon300.jpg"></div>
    <script src="${staticResourcePathH5}/js/jquery-2.1.1.min.js?v=${SYSTEM_INIT_TIME}" ></script>
    <script type="text/javascript">
        var staticResourcePathH5="${staticResourcePathH5}";
        var staticResourcePath="${staticResourcePath}";
        var njxBasePath = "${njxBasePath}";
        var actionBase = "${njxBasePath}";
        $('.m-top-bar .nav-main h3.m-text-cut').css('max-width','8rem');
    </script>

    <script src="${staticResourcePathH5}/js/operatelog.js?v=${SYSTEM_INIT_TIME}"></script>