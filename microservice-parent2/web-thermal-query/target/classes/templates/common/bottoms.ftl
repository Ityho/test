<#--<%@page language="java" contentType="text/html; charset=gbk"-->
		 <#--pageEncoding="gbk"%>-->
<#--<%@ taglib prefix="s" uri="/struts-tags"%>-->
<#--<%@ include file="/resourcePath.jsp"%>-->
<#include "../common/resourcePath.ftl"/>
<#--<%--<link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/css/site.css?v=<%=systemConfig.SYSTEMINITTIME %>"/>--%>-->

<#if !admin1??>
    <div class="w-main-footer layout-media min-layout1200 bgChangeColor_1">
        <div class="w-layout wmf-content clearfix">
            <div class="wmf-cell">
                <p><i class="icon margin-right-5">&#xe694;</i> <a href="${njxBasePath}/help.shtml">帮助中心</a></p>
                <p><i class="icon margin-right-5">&#xe755;</i><a href="${njxBasePath}/aboutUs.shtml">关于我们</a></p>
                <p><i class="icon margin-right-5">&#xe6a7;</i><a href="${njxBasePath}/contectUs.shtml">联系我们</a></p>
            </div>
            <div class="wmf-know">
                <ul class="clearfix">
                    <li>
                        <h3 class="wmf-know-title">热点发现</h3>
                        <p><a href="javascript:">热点事件</a></p>
                        <p><a href="javascript:void(0);" onclick="getRan()" data-toggle="modal" data-target="#loginModal">热门微博</a></p>
                        <p><a href="javascript:void(0);" onclick="getRan()" data-toggle="modal" data-target="#loginModal">重大事件</a></p>
                    </li>
                    <li>
                        <h3 class="wmf-know-title">传播评估</h3>
                        <p><a href="javascript:void(0);" onclick="getRan()" data-toggle="modal" data-target="#loginModal">新闻稿件传播统计</a></p>
                        <h3 class="wmf-know-title margin-top">大数据报告</h3>
                        <p><a href="${njxBasePath}/infoData.shtml">大数据解读</a></p>
                    </li>
                    <li>
                        <h3 class="wmf-know-title">大数据工具</h3>
                        <p><a  href="javascript:void(0);" onclick="getRan()" data-toggle="modal" data-target="#loginModal">全网事件分析</a></p>
                        <p><a href="javascript:void(0);" onclick="getRan()" data-toggle="modal" data-target="#loginModal">微博事件分析</a></p>
                        <p><a href="javascript:void(0);" onclick="getRan()" data-toggle="modal" data-target="#loginModal">微博传播效果分析</a></p>
                        <p><a href="javascript:void(0);" onclick="getRan()" data-toggle="modal" data-target="#loginModal">竞品分析</a></p>
                        <p><a href=href="javascript:void(0);" onclick="getRan()" data-toggle="modal" data-target="#loginModal">评论分析</a></p>
                    </li>
                    <li>
                        <h3 class="wmf-know-title">专业解决方案</h3>
                        <p><a href="https://www.yqt365.com/goToNewGeneralizeMain.action?searchFrom=0&logonOrRegister=0&webGamesType=9&industryType=2" target="_blank">舆情通</a></p>
                        <p><a href="https://www.u-mei.com/web/index.html?extendtype=030012" target="_blank">U媒</a></p>
                    </li>
                    <li class="wmf-know-last">
                        <div class="inline-block text-left">
                            <h3 class="wmf-know-title">官方微博</h3>
                            <p>
                                <img src="${staticResourcePath}/css/indexV4/global/img/weibo.jpg"
                                     style="width: 80px;"/>
                            </p>
                        </div>
                        <div class="inline-block text-left">
                            <h3 class="wmf-know-title">官方微信</h3>
                            <p>
                                <img src="${staticResourcePath}/css/indexV4/global/img/weixin.jpg"
                                     style="width: 80px;"/>
                            </p>
                        </div>
                        <p class="white">客服咨询</p>
                        <p>客服TEL: 4006007505</p>
                        <p>客服QQ: 3002432217  3002436852
                        </p>
                    </li>
                </ul>
            </div>
        </div>
        <p class="copyright">
            <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=31011502001915" target="_blank"
               class="v_a_m"><img
                    src="${staticResourcePath}/css/indexV4/base/images/beian.png" alt="">
                <span class="v_a_m color_5">沪公网安备 31011502001915号 沪ICP备09090754号-24</span>
                <a href="https://www.cnzz.com/stat/website.php?web_id=1255980179" target="_blank" title="站长统计">
                    <img src="${staticResourcePath}/css/indexV4/base/images/pic1.gif" alt="">
                </a>
            </a>
        </p>
    </div>
<#else>
    <div class="w-main-footer min-layout1200 bgChangeColor_1">
        <div class="copyright" style="border-top: 0">
            <div class="clearfix color_5 margin-bottom-20">
                <a href="${njxBasePath}/aboutUs.shtml" class="margin-horizontal-20 color_5">关于我们</a>|
                <a href="${njxBasePath}/contectUs.shtml" class="margin-horizontal-20 color_5">联系我们</a>|
                <a href="${njxBasePath}/help.shtml" class="margin-horizontal-20 color_5">帮助中心</a>|
                <a href="https://www.yqt365.com/goToNewGeneralizeMain.action?searchFrom=0&logonOrRegister=0&webGamesType=9&industryType=3" class="margin-horizontal-20 color_5" target="_blank">舆情通</a>|
                <a href="https://www.u-mei.com/web/index.html?extendtype=030013" class="margin-horizontal-20 color_5" target="_blank">U媒</a>
            </div>
            <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=31011502001915" target="_blank"
               class="v_a_m"><img
                    src="${staticResourcePath}/css/indexV4/base/images/beian.png" alt="">
                <span class="v_a_m color_5">沪公网安备 31011502001915号 沪ICP备09090754号-24</span>
                <a href="https://www.cnzz.com/stat/website.php?web_id=1255980179" target="_blank" title="站长统计">
                    <img src="${staticResourcePath}/css/indexV4/base/images/pic1.gif" alt="">
                </a>
            </a>
        </div>
    </div>
    <!--     <div id="footer"> -->
    <#--<%--     <p><a href="<%=base%>/aboutUs.shtml">关于我们</a>  |  <a href="<%=base%>/contectUs.shtml">联系我们</a>  |  <a href="<%=base%>/help.shtml">帮助中心</a> --%>-->
    <!--         |  <a href="http://wgw.city.sina.com.cn/" target="_blank">微官网</a> -->
    <!--         |  <a href="https://www.u-mei.com/" target="_blank">U媒</a> -->
    <!--         |  <a href="http://www.51cjyy.com/"  target="_blank">超级应援</a> -->
    <!--     </p> -->

    <!--     <p> -->
    <!--         <div style="margin:0 auto; padding:20px 0;"> -->
    <!--             <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=31011502001915" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;"> -->
    <!--                 <img src="<%=staticResourcePath %>/images/beian.png" style="float:left;"/> --%>
    <!--                 <p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#939393;">沪公网安备 31011502001915号 沪ICP备09090754号-24</p> -->
    <!--                 cnzz统计代码start  -->
    <!--                 <script type="text/javascript" src="<%=staticResourcePath%>/js/cnzz.js?v=<%=systemConfig.SYSTEMINITTIME %>"></script> -->
    <!--                 cnzz统计代码end  -->
    <!--             </a> -->
    <!--         </div> -->
    <!--     </p> -->
    <!--     </div> -->
    <script type="text/javascript" src="${staticResourcePath}/js/operatelog.js?v=${sysConfig}"></script>
</#if>
<script>
    $(function(){
        setTimeout(function() {
            $('.fixedActive a').removeClass('bounce animated');
        }, 2000);
        $(".fixedActive .close").on("click",function(){
            $(this).parent().hide();
        });
    });
</script>