<#--<%@ page pageEncoding="utf-8" language="java"%>-->
<#--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>-->
<#--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>-->

<html>
<head>
<#--<%-->
<#--String njxBasePath = request.getContextPath();-->
<#--String staticResourcePath = njxBasePath;-->
<#--%>-->
	<#assign njxBasePath = request.contextPath />
    <#--<script type="text/javascript">-->
        <#--var staticResourcePath = "${njxBasePath}";-->
    <#--</script>-->
    <!-- saved from url=(0014)index:internet -->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <meta charset="UTF-8">
    <title>${reportName}-微热点(微舆情)-社会化大数据应用平台</title>
    <META name="keywords" content="微热点(微舆情)_网络舆情监测">
    <META name="description" content="微热点(微舆情)_网络舆情监测">
    <link rel="stylesheet" type="text/css" href="${njxBasePath}/css/font-icon.css" />
    <link href="${njxBasePath}/css/style.css" rel="stylesheet" type="text/css">
    <link href="${njxBasePath}/css/common.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${njxBasePath}/css/dayReport.css" />
    <link rel="stylesheet" type="text/css" href="${njxBasePath}/css/iconfont.css" />
    <link href="${njxBasePath}/css/tips.css" rel="stylesheet" type="text/css">
    <link rel="shortcut icon" sizes="16x16" href="${njxBasePath}/images/addhomescreen/icon-16x16.png">
    <link rel="apple-touch-icon-precomposed" href="${njxBasePath}/images/addhomescreen/icon-152x152.png">
    <link rel="shortcut icon" sizes="196x196" href="${njxBasePath}/images/addhomescreen/icon-196x196.png">

    <script type="text/javascript" src="${njxBasePath}/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${njxBasePath}/js/index.js"></script>
    <script src="${njxBasePath}/js/jquery.JPlaceholder.js"></script>

    <script type="text/javascript" src="${njxBasePath}/js/echarts/echarts.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${njxBasePath}/js/echarts/chart/line.js" charset="UTF-8"></script>
    <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#uuid=ad6ca8fe-7189-423a-b7b6-ed44605dd77a&style=-1"></script>
</head>
<style>
    .main_list li .m_l {
        float: left;
        text-align: center;
    }

    .main_list li .m_l>img {
        width: 20px;
        height: 20px;
        border-radius: 50%
    }

    .scale {
        transform: scale(.9, .9);
        -ms-transform: scale(.9, .9);
        -webkit-transform: scale(.9, .9);
        -o-transform: scale(.9, .9);
        -moz-transform: scale(.9, .9);
    }
</style>
<body class="mobileStyle dayReport">
<input type="hidden" id="userId" value="${keywordReportRecord.userId}">
<input type="hidden" id="keywordId" value="${keywordReportRecord.keywordId}">
<input type="hidden" id="code" value="${keywordReportRecord.shareCode}">
<input type="hidden" value="${filterNum}">
<input type="hidden" id="wbTop" value="${wbTop}">
<input type="hidden" id="fwbTop" value="${fwbTop}">
<!--head代码 start-->
<div id="head" class="rel h_line">
    <div class="head-top">
        <span class="logo logoJC float_l" onclick="javascript:location.href='http://www.wrd.cn'"></span>
        <span class="r_btn abs" style="top: 14px;">
				<a class="loginBtn loginBtnReport" href="javascript:void(0);" title="微信" onclick="sharePage(event,'weixin');return false;">
					<img src="${njxBasePath}/images/mobile.png" style="margin-right: 5px; width: 12px">在手机上查看</a>
			</span>
    </div>
</div>
<!--head代码 end-->
<script>
    function sharePage(event,type){
        $("#cancleBtn").show();
        var url = location.href;
        var title = $(document).attr("title");
        bShare.addEntry({
            title:title,
            url: url
        });
        //清除自定义分享内容的方法，在设置前清空，重新自定义内容
        bShare.entries = [];
        bShare.init();
        bShare.share(event,type,0);
    }
    $(function(){
        var bs = {
            versions: function () {
                var u = navigator.userAgent, app = navigator.appVersion;
                return {
                    windowsPhone: u.indexOf('IEMobile') > -1,
                    trident: u.indexOf('Trident') > -1,
                    presto: u.indexOf('Presto') > -1,
                    webKit: u.indexOf('AppleWebKit') > -1,
                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') === -1,
                    mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/) || !!u.match(/IEMobile/),
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
                    android: u.indexOf('Android') > -1 || u.indexOf('UCBrowser') > -1,
                    iPhone: u.indexOf('iPhone') > -1,
                    iPad: u.indexOf('iPad') > -1,
                    webApp: u.indexOf('Safari') === -1
                };
            } (),
            language: (navigator.browserLanguage || navigator.language).toLowerCase()
        };
        if (bs.versions.mobile) {
            if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.windowsPhone) {
                $("#head").hide();
                $(".page-container2").css("margin-top","0px");
                $("#reportTime").hide();

            }
        }

    });
</script>
<div class="page-container2">
    <!--日报 start-->
    <div class="content mt15">
        <!--报告头部 start-->
        <div class="row-fluid">
            <div class="info" style="height: 44px;background-color:#007bdd;">
			<#--<%-- <img src="<%=njxBasePath %>/reportView/images/logo-w.png" style="width: 85px; padding: 8px 0px 0px 10px;"> --%>-->
                <div class="float_l align_l" style="margin: 10px 0px 0px 12px; color: #000; font-weight: bold;">
                    <div style="width:10%;float:left;height:24px;width:24px;border-radius:50%; background: url(${userHead!''}) no-repeat center; background-size: cover;"></div>
                    <div style="height: 24px;line-height: 24px;margin: 0px 0px 0px 30px;font-size: 12px;color:#ffffff;font-weight: normal;">${nickname!'' }</div>
                </div>
                <div class="clear"></div>
            </div>
            <div class="con Report-header" style="background-color:#007bdd;padding: 0px 0px 20px 0px;">
                <div class="" style="font-size: 18px;color:#ffffff;text-align: center;padding-right: 8px;">【${reportName}】&nbsp;微热点(微舆情)监测日报</div>
                <div style="font-size: 14px;color:#ffffff;">${reportTime}&nbsp;00:00~23:59</div>
            </div>
        </div>
        <div class="empty" style="display:${showFlag}"></div>
        <!--报告头部 end-->
        <div class="row-fluid" style="background-color: #f3f3f3; margin-top: -10px;" <#if !sources??>style="display: none" </#if>>
            <div class="con" style="text-align: center;">
                <#if sources?? && (sources?size > 0)>
                    <div class="sourceTitle">
                        <i class="iconfont icon-total i1"></i>
                        <span style="width: 100%;">全部</span>
                        <span>${totalNum}</span>
                    </div>
                    <#list sources as source>
                        <#if (source.name)?? && source.name == '网站'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-website i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '微博'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-weibo2 i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '新闻'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-news2 i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '论坛'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-bbs2 i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '客户端'>
                            <div class="sourceTitle t1">
                                <i class="iconfont icon-app i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '微信'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-weixin2 i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '报刊'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-baokan i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '政务'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-zhengwu i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '博客'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-boke i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '视频'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-video i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        <#elseif (source.name)?? && source.name == '外媒'>
                            <div class="sourceTitle">
                                <i class="iconfont icon-waimei i1"></i>
                                <span style="width: 100%;">${source.name}</span>
                                <span>${source.num}</span>
                            </div>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="clear"></div>
        </div>

        <!--非微主要观点Top20 start-->
        <div class="row-fluid" style="<#if !fwbList??>display: none</#if>">
            <div class="con">
                <div class="title">
                    <div class="titleBlue" style="background-color: #007bdd;">非微博重点内容</div>
                </div>
                <div class="main_list">
                    <#if fwbList?? && (fwbList?size > 0)>
                        <ul>
                            <#list fwbList as icn>
                            <#--<#forEach var="icn" items="${fwbList}" begin="0" end="9" varStatus="status">-->
                                <li style="padding: 30px 0px 0px 10px; min-height: initial">
                                    <div style="float: left; color: #333333; font-size: 14px;">重点内容${icn_index+1}</div>
                                </li>
                                <li style="padding: 0 10px; min-height: initial">
                                <#--<%-- <div class="m_l" style="<c:if test="${ status.index==9}">margin-left:-63px;</c:if><c:if test="${ status.index!=9}">margin-left:-55px;</c:if>margin-top:20px;width:44px;">-->
                                <#--<div id="fmg_${status.index}" class="scale"style="line-height: 44px;text-align: center;background: url(${njxBasePath}/images/investjd.png);">-->
                                <#--<span style="width:100%;float:left;">${mg.percentStr}</span>-->
                                <#--<input type="hidden"  name="fmPercent" value = "${mg.percentStr}">-->
                                <#--</div>-->
                                <#--<c:if test="${icn.customFlag1 == 4}">-->
                                <#--<span style="width:100%;float:left;font-size:14px;color:#ff2121;">敏感</span>-->
                                <#--</c:if>-->
                                <#--</div>  --%>-->
                                    <div class="" style="margin-left: 30px; margin-top: -1px; width: 125px; height: 20px;">
                                        <span style="width: 35%; float: left; font-size: 14px; padding-left: 10px;">(${icn.percentStr})</span>
                                        <input type="hidden" name="mgPercent" value="${icn.percentStr}">
                                        <#if ((icn.customFlag1)?? && icn.customFlag1 != 4)>
                                            <span style="float: left; font-size: 14px; color: #ff2121;">敏感</span>
                                        </#if>
                                    </div>
                                    <div class="">
                                        <p class="quickLink">
                                            <a target="_blank" href="${icn.webpageUrl}" style="margin-left: -5px; font-size: 14px; color: #333333;">
                                                【${(icn.title)!''}】${(icn.summary)!''} </a>
                                        </p>

                                        <#if (icn.originType=='wb'||icn.originType=='txwb')&&(icn.forwarderContent??)>
                                            <div class="zfCon">${icn.forwarderContent}</div>
                                        </#if>

                                        <div class="infor" style="margin-top: 0px;">
                                            <div class="float_l">
                                                <div class="m_l">
                                                    <#if (icn.profileImageUrl)??>
                                                        <img src='${icn.profileImageUrl}' />
                                                    </#if>
                                                    <#if !(icn.profileImageUrl)??&&(icn.originType=='wb')>
                                                        <#if (icn.captureWebsiteName=="腾讯微博")??>
                                                            <img src="${njxBasePath}/images/userlogo/app-more-icon-txwb.jpg" />
                                                        </#if>
                                                        <#if (icn.captureWebsiteName=="新浪微博")??>
                                                            <img src="${njxBasePath}/images/userlogo/app-more-icon-weibo.jpg" />
                                                        </#if>
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='sp')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-video.jpg" />
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='wx')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-weixin.jpg" />
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='xw')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-news.jpg" />
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='lt')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-bbs.jpg" />
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='bk')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-blog.jpg" />
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='app')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-app.jpg" />
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='zw')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-affairs.jpg" />
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='baokan')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-press.jpg" />
                                                    <#elseif !(icn.profileImageUrl)??&&(icn.originType=='jw')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-media.jpg" />
                                                    <#elseif !(icn.profileImageUrl)?? && (icn.originType=='wz')??>
                                                        <img src="${njxBasePath}/images/userlogo/app-more-icon-web.jpg" />
                                                    </#if>
                                                </div>
                                                <#if (icn.captureWebsiteName)??>
                                                <#--<#when !(empty icn.captureWebsiteName)??>-->
                                                    ${icn.captureWebsiteName}
                                                <#else >
                                                <#--<#otherwise>-->
                                                    网站媒体
                                                <#--</#otherwise>-->
                                                </#if>
                                                <span>&nbsp;等${(icn.repeatNum)!'0'}条相似文章</span>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </li>
                            </#list>
                        </ul>
                    <#else>
                        <br>
                        <div align="center" style="padding-top: 50px">
                            <p style="display: inline; font-size: 14px">
                                <img src="${njxBasePath }/images/warn.png" style="width: 60px"><br />此时间段暂无信息
                            </p>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
        <!--非微博主要观点 end-->
        <!--微博主要观点 start-->
        <div class="row-fluid"
				<#if !wbList??>style="display: none" </#if>>
            <div class="con">
                <div class="title">
                    <div class="titleBlue">${mgTitle}</div>
                </div>
                <div class="main_list">
                    <#if wbList?? && (wbList?size > 0)>
                        <ul>
                            <#list wbList as icn>
                                <li style="padding: 30px 0px 0px 10px; min-height: initial">
                                    <div style="float: left; color: #333333; font-size: 14px;">重点内容${icn_index+1}</div>
                                </li>
                                <li style="padding: 0 10px; min-height: initial"">
                                    <div class="" style="margin-left: 30px; margin-top: -1px; width: 125px; height: 20px;">
                                        <span style="width: 35%; float: left; font-size: 14px; padding-left: 10px;">(${icn.percentStr})</span>
                                        <input type="hidden" name="mgPercent" value="${icn.percentStr}">
                                        <#if ((icn.customFlag1)?? && icn.customFlag1 != 4)>
                                            <span style="float: left; font-size: 14px; color: #ff2121;">敏感</span>
                                        </#if>
                                    </div>
                                    <div class="">
                                        <p class="quickLink">
                                            <a target="_blank" href="${icn.webpageUrl}" style="font-size: 14px; color: #333333;">
                                                <#if (icn.summary)??>
                                                ${icn.summary}
                                            </#if>
                                            </a>
                                        </p>
                                        <#if (icn.originType=='wb'||icn.originType=='txwb')&&(icn.forwarderContent)??>
                                            <div class="zfCon">${icn.forwarderContent}</div>
                                        </#if>

                                        <div class="infor" style="margin-top: 0px;">
                                            <div class="float_l">
                                                <div class="m_l" style="width: 100% !important; text-align: left;">
                                                    <#if (icn.profileImageUrl)??>
                                                        <img src='${icn.profileImageUrl}' />
                                                    </#if>
                                                    ${icn.author!''}

                                                    <span>&nbsp;等${icn.repeatNum!''}条近似微博</span>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </li>
                            </#list>
                        </ul>
                    <#else>
                        <br>
                        <div align="center" style="padding-top: 50px">
                            <p style="display: inline; font-size: 14px">
                                <img src="${njxBasePath }/images/warn.png" style="width: 60px"><br />此时间段暂无信息
                            </p>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
        <!--微博主要观点 end-->
			<#if !loginType??>
				<!--分享组件部分 start-->
				<div class="row-fluid" style="text-align: center; margin: 10px 0 0;">
                    <a class="visible-xs" href="http://d.51wyq.cn">
                        <img width="100%" src="${njxBasePath}/images/bottomPic2.jpg" /></a>
                    <a class="visible-lg" href="http://d.51wyq.cn">
                        <img width="100%" src="${njxBasePath}/images/bottomPic2.jpg" /></a>
                </div>
				<!--分享组件部分 end-->
			</#if>
    </div>
</div>
<!--日报 end-->
</div>

<div id="cancleBtn" class="cancleBtn"></div>

<!--底部部分代码 start-->
<div class="h35 clear"></div>
<div id="footer">
    <p>
        <a href="http://www.wrd.cn/aboutUs.shtml">关于我们</a> | <a href="http://www.wrd.cn/contectUs.shtml">联系我们</a> | <a href="http://www.wrd.cn/help.shtml">帮助中心</a>
    </p>
    <p>&copy; Copyright wrd.cn 2002-2017 All Rights Reserved 沪ICP备05035762号-9｜增值电信业务经营许可证B2-20090366</p>
</div>
<!--底部部分代码 end-->
<script type="text/javascript">
    window.onload = function(){
        var countWb = $("#wbTop").val();
        var countFwb = $("#fwbTop").val();
        if(countWb > 0){
            var fm = "#mg_";
            for(var i = 0;i< countWb;i++){
                var fmId = "";
                fmId = fm+i;
                console.log(fmId);
                console.log($(fmId).find('input[name=mgPercent]').val().replace("%",""));
                var object = document.getElementById(fmId.replace("#",""));

                loadImg($(fmId).find('input[name=mgPercent]').val().replace("%",""),object);
            }
        }
        if(countFwb > 0){
            var fm = "#fmg_";
            for(var i = 0;i< countFwb;i++){
                var fmId = "";
                fmId = fm+i;
                console.log(fmId);
                console.log($(fmId).find('input[name=fmPercent]').val().replace("%",""));
                var object = document.getElementById(fmId.replace("#",""));
                loadImg($(fmId).find('input[name=fmPercent]').val().replace("%",""),object);
            }
        }
    }
    function loadImg (data,object){

        var i = 100;
        if(i>data){
            i=data
        }
        object.innerHTML = i+'%';
        if(i < 1){
            i=1;
        }
        var imgLeft = -(i*44+(i*10))+'px';
        object.style.backgroundPosition = imgLeft+'\t'+'0px';


    }
</script>
<script>
    $(function() {
        $(".julei .main_list").each(function(i){
            $("#cluster"+(i)+">ul>li:gt(0)").hide();
        });
        $(".bottomMore").on("click",function(){
            if(!$(this).find("img").hasClass("rotate180")){
                $(this).parents(".julei .main_list").find(">ul>li:gt(0)").show(300);
                $(this).html("<img src='images/expand3-32.png' class='rotate180'/>");
            }else{
                $(this).parents(".julei .main_list").find(">ul>li:gt(0)").hide(300);
                $(this).html("<img src='images/expand3-32.png' class='rotate0'/>");
            }
        });
        $("#cancleBtn").on("click",function(){
            $("#bsWXBox").hide();
            $("#cancleBtn").hide();
        });
    });

    <#--//LineChart('${lineChat}',"hotLine");-->

    function LineChart(data,dom){
        if (data==null||data==""){
            //document.getElementById(dom).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=njxBasePath %>/images/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            $("#"+dom).parents(".row-fluid").hide();
            return false;
        }
        data1 = eval(data)[1][0];
        data = eval(data)[0][0];
        console.log(data);
        console.log(data1);
        var splitNum = 0;
        if(data.datetime.length>12){
            splitNum = 2;
        }
        /*$.each(data.data,function(){
         this.symbolSize = 6;
         this.itemStyle={'normal':{'lineStyle':{'width':2.8}}};
         });*/
        var config = require(
                [
                    'echarts',
                    'echarts/chart/line'
                ],
                function (ec) {
                    var chart1 = ec.init(document.getElementById(dom));
                    var option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter:function(params){
                                var v = new Date(parseInt(params[0].name)).format("MM-dd");

                                for (var i = 0, l = params.length; i < l; i++) {
                                    v += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                                }

                                return v;
                            }
                        },
                        /*   toolbox: {
                              show : true,
                              orient:'horizontal',
                              y:10,
                              x:'right',

                              feature : {
                                  mark : {show: false},
                                  dataView : {
                                      show: false,
                                      readOnly: false,
                                      lang: ['数据视图', '关闭', '刷新']
                                  },
                                  restore : {show: true},
                                  saveAsImage : {
                                      show: true,
                                      name:data.title
                                  },
                              }
                          }, */
                        legend: {
                            data:["全部"],
                            show:false
                        },
                        grid:{
                            x:40,
                            x2:30,
                            y:10
                        },
                        xAxis:[{
                            type : 'category',//category|time
                            boundaryGap: false ,
                            data : data.datetime,

                            splitLine:{
                                show:false
                            },
                            splitNumber:splitNum,
                            axisLabel : {
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#000"
                                },
                                formatter:function(v){
                                    v = new Date(parseInt(v)).format("MM-dd hh:00");
                                    return v;
                                }
                            },
                        }
                        ],
                        yAxis : [{
                            type : 'value',
                            /*axisLine: {
                             onZero: false,
                             show:false
                             },
                             splitLine:{
                             show:false
                             },
                            splitArea:{
                                show:true,
                                areaStyle:{
                                    color:['#08172c','#08172c']
                                }
                            },*/
                            axisLabel:{
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#000"
                                },
                                formatter:function(v){
                                    if(v>=1000){
                                        return (v/1000)+"k";
                                    }else{
                                        return v;
                                    }
                                }
                            },
                        }],


                        calculable : false,
                        series : [
                            {
                                name:"非敏感",
                                type:"line",
                                smooth:true,
                                itemStyle : {
                                    normal : {
                                        color:'#3454a1',
                                        lineStyle:{
                                            color:'#3454a1'
                                        }
                                    }
                                },
                                data:data.data

                            },
                            {
                                name:"敏感",
                                type:"line",
                                smooth:true,
                                itemStyle : {
                                    normal : {
                                        color:'#c1222a',
                                        lineStyle:{
                                            color:'#c1222a'
                                        }
                                    }
                                },
                                data:data1.data

                            }
                        ]
                    }
                    chart1.setOption(option);
                    chart1.setTheme('infographic');
                    var enConfig = require('echarts/config');

                }
        );
    }

    function recordSign(){
        $.ajax({
            url : "${njxBasePath}/recordSign.shtml?userId="+$("#userId").val(),
            type : "get",
            success : function(result){
            }
        })
    }

    function recordDown(){
        $.ajax({
            url : "${njxBasePath}/recordDown.shtml?userId=" + $("#userId").val(),
            type : "get",
            success : function(result) {
            }
        })
    }
</script>
</body>
</html>