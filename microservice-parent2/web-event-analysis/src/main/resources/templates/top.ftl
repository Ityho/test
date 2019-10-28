<#include "frame_top.ftl">
<!-- 通用样式 start -->
<link rel="stylesheet" href="${staticResourcePathH5}/css/style.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/layout.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/layout20160825.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/tips.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/animation.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/fsgallery.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/fonts-icomoon.css?v=${SYSTEM_INIT_TIME}"/>
<link rel="stylesheet" href="${staticResourcePathH5}/css/iconfont.css?v=${SYSTEM_INIT_TIME}"/>
<link rel="stylesheet" href="${staticResourcePathH5}/css/loading.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/sweetalert.css?v=${SYSTEM_INIT_TIME}">
<link rel="stylesheet" href="http://at.alicdn.com/t/font_189126_38986hdj15ib2o6r.css?v=${SYSTEM_INIT_TIME}">
<script src="http://at.alicdn.com/t/font_1nyink751a46lxr.js?v=${SYSTEM_INIT_TIME}"></script>
<!-- 通用样式 end -->
<!-- 通用js start -->
<#include "view/commonJsp/zepto.ftl" >
<script src="${staticResourcePathH5}/js/index.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/navigate.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/sweetalert.min.js?v=${SYSTEM_INIT_TIME}" ></script>
<script src="${staticResourcePathH5}/js/utils/utils.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5 }/js/vue/home/base.js?v=${SYSTEM_INIT_TIME }" ></script>
<!-- 通用js end -->

<#--<c:if test="${param.returning == 2 }">-->
<#--<div id = "UserStretch" class="dialog" style="display: block;width:70%;left: 15%;top:35%;">-->
<#--<div id = "closehy" class="dialog" style="display: block;left:92%;top:-7%;width: 30px;height: 30px;margin-left:-15px;background:transparent;box-shadow:none;position:absolute;">-->
<#--<img alt="" src="${staticResourcePathH5}/images/stretch/close.png?v=${SYSTEM_INIT_TIME}" width="100%">-->
<#--</div>-->
<#--<div style = "background: url('<%=staticResourcePathH5 %>/images/stretch/dialog-background.png?v=<%=SYSTEM_INIT_TIME %>') no-repeat;background-size:100%;margin-top:-50px;">-->
<#--<s:if test="admin.userHead == null || admin.userHead == ''">-->
<#--<img src="${staticResourcePathH5}/images/defaultHead.jpg?v=${SYSTEM_INIT_TIME}" style="border-radius:50%;width:60px;margin:0px auto;display: block;padding-top:6px;">-->
<#--</s:if>-->
<#--<s:else>-->
<#--<img alt="" src="${admin.userHead}" style="border-radius:50%;width:60px;margin:0px auto;display: block;padding-top:6px;">-->
<#--</s:else>-->
<#--<div style="height:40px;line-height:50px;text-align:center;color:#fff;">-->
<#--<c:if test="${empty admin.nickname}">${admin.username}</c:if><c:if test="${ not empty admin.nickname}">${admin.nickname}</c:if>-->
<#--</div>-->
<#--<div style="height:40px;line-height:30px;text-align:center;color:#fff;">-->
<#--欢迎回来-->
<#--</div>-->
<#--</div>-->
<#--<div style="margin:25px 10px 25px 25px;">-->
<#--<div style="color:#ff8400;font-size:18px;line-height:30px;"><img src="${staticResourcePathH5}/images/stretch/kingship.png?v=${SYSTEM_INIT_TIME}" style="width:25px;margin-top: -4px;"><span>&nbsp;温馨提示</span></div>-->
<#--<div style="font-size:14px;line-height:30px;display: none;" id = "userstretch_keyword_num"></div>-->
<#--<div style="font-size:14px;line-height:30px;margin-top:5px;" id = "userstretch_title"><span>免费使用1个监测方案7天</span>&nbsp;<a href = "<%=njxBasePath %>/keywordInfo.shtml" style="display: inline-block;width: 55px;height:20px;line-height:20px;text-align:center;color:#d56073;border-radius:5px;border:solid 1px #d56073;font-size:12px;">去使用</a></div>-->
<#--</div>-->
<#--</div>-->
<#--<div class="zhezhao zhezhaohy" style="display: block;"></div>-->
<#--<script>-->
<#--$(function(){-->
<#--$("#closehy").on("click",function(){-->
<#--$(".dialog").fadeOut("slow",function(){-->
<#--if(location.href.indexOf("?returning=1&") != -1){-->
<#--location.href = location.href.replace("?returning=1&","?");-->
<#--}else if(location.href.indexOf("?returning=1") != -1){-->
<#--location.href = location.href.replace("?returning=1","");-->
<#--}else if(location.href.indexOf("&returning=1") != -1){-->
<#--location.href = location.href.replace("&returning=1","");-->
<#--}-->
<#--});-->
<#--$(".zhezhaohy").fadeOut();-->
<#--});-->
<#--$(".zhezhaohy").on("click",function(){-->
<#--$("#closehy").click();-->
<#--})-->
<#--});-->
<#--</script>-->
<#--</c:if>-->