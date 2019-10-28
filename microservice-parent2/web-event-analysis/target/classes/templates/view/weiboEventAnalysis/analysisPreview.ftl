<#include "../../top.ftl">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" type="text/css" href="${njxBasePath!}/css/font-icon.css?v=${SYSTEMINITTIME}" />
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/analysis/common.css?v=${SYSTEM_INIT_TIME}"/>
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/frame.css?v=${SYSTEM_INIT_TIME}"/>
    <link href="${staticResourcePathH5}/css/analysis/style.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
    <link href="${staticResourcePathH5}/css/other.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
    <link href="${staticResourcePathH5}/css/eventAnalysis.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
    <link href="${staticResourcePathH5}/css/report.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${staticResourcePathH5}/js/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/jquery.chromatable.js?v=${SYSTEM_INIT_TIME}"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/echarts.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/pie.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/bar.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/map.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/line.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/radar.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/wordCloud.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/force.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/tree.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/funnel.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/weiboAnalysisPreview.js?v=${SYSTEM_INIT_TIME}"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/gauge.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <!--分享-->
    <script type="text/javascript" charset="utf-8" src="${staticResourcePath!}/js/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=3&amp;lang=zh"></script>
    <script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/bshareC0.js?v=${SYSTEM_INIT_TIME}"></script>
    <script type="text/javascript" charset="utf-8">
        $(function(){
            var pre = location.href.split("/wbAnalysisShare")[0];
            if(location.href.indexOf("/view")!=-1){
                pre = location.href.split("/view")[0];
            }
            var ticket = '${tickets!}';
            var title = '${reportName!}微博事件分析报告，用数据挖掘真相-微热点';
            document.title = title;
            var url = pre+"/weibo/analysisPreview?isShare=1&tickets="+encodeURIComponent(ticket);
            var wbStr = "wb.wyq.cn";
            if(pre.split(wbStr).length > 1){//针对轻应用分享的h5页面，在电脑端打开的空白的情况(手机端跳转h5,电脑端让其直接跳转到web页面)
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

                if(bs.versions.mobile){
                    if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.windowsPhone) {
                        pre = "http://h5.51wyq.cn";
                        url = pre+"/weibo/analysisPreview?isShare=1&tickets="+encodeURIComponent(ticket);
                    }else{
                        pre = "http://wyq.sina.com";
                        url = pre+"/analysisPreview.shtml?isShare=1&shareType=2&tickets="+encodeURIComponent(ticket);
                    }
                }

            }
            //微博，qq,空间分享格式
            bShare.addEntry({
                title:"[微热点]${reportName!""}",
                summary:"的微博事件分析，用数据挖掘真相",
                url: url,
                pic:staticResourcePathH5+"/images/fenxiang/weibowyq-icon300.jpg"
            });
            //微信分享格式
            bShare.addEntry({
                title:"[来自@微热点]${reportName!""}的微博事件分析，用数据挖掘真相~网络链接",
                url: url,
                pic:staticResourcePathH5+"/images/fenxiang/weibowyq-icon300.jpg"
            });
            bShare.init();
        })
    </script>
    <style id = "style">
        @media only screen and (max-width:600px ) {
            .mobileStyle #top,.mobileStyle #head nav,.mobileStyle #head .nav{display: none;}
            .mobileStyle #head{position: fixed; z-index: 100; top: 0; width: 100%;}
            .mobileStyle #head .logo{margin-left: 10px;}
            .mobileStyle #head .r_btn{right: 15px;}
            .mobileStyle .phoneTopHeight{margin-top: 70px;}
            .mobileStyle #footer,.mobileStyle .h35{display: none;}
            .mobileStyle #head .logo{border: none;}
        }
    </style>
</head>
<script type="text/javascript">saveOperateLog('微博分析查看','1016');</script>
<style>
    #hotNews tr td:first-child{
        text-align:left;
        padding-left: 20px;
    }
</style>
<script language="javascript">
    var echarts;
    require.config({
        paths: {
            echarts: '../js/echarts',
        }
    });
    function checkLength(which) {
        iCount = which.value.replace(/[^\u0000-\u00ff]/g,"aa");
        which.size=iCount.length+1;
    }

    $(function(){
        $("#scrollTab").chromatable({
            width: "100%",
            height: "400px",
            scrolling: "yes"
        });

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
                $('.echartAdpter').css('width', '100%');
                phone = true;
            }
        }
        var url = location.href;
        var isApp = document.getElementById("isApp").value;
        var Uid = "${admin!}";
        if((Uid || isApp==null) && bs.versions.mobile){
            var text = $("#style").text().replace("margin-top: 70px;","margin-top: 0px;")
            $("#style").text(text);
            $("#head").css("display","none");
        }else if(bs.versions.mobile){
            var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
            if (ua.match(/WeiBo/i) == "weibo") {
                //在新浪微博客户端打开
                $(".RegistBtn").css("display","none");
                console.log("在新浪微博客户端打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href="http://apps.weibo.com/3960037780/8rXM111J";
                });
                //});
            }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
                //在微信中打开
                $(".RegistBtn").css("display","none");
                console.log("在微信中打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href="${njxBasePath!}/weiboHome.action";
                });
                //});
            }else{
                //手机浏览器打开
                console.log("手机浏览器打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href="${njxBasePath!}/indexLocal";
                });
                $(".RegistBtn").on("click",function(){
                    location.href="${njxBasePath!}/register";
                });
                //});
            }
        }else{
            //pc中打开
            //$(function(){
            $(".loginBtn").on("click",function(){
                location.href="${webPath!}logon.shtml";
            });
            $(".RegistBtn").on("click",function(){
                location.href="${webPath!}user/goRegister.shtml";
            });
            //})
        }
    });
</script>
<body class="mobileStyle">
<input value="${isApp!}" type="hidden" id = "isApp">
<div id="head" class="rel" <#if isApp!0 == 1>style = 'display:none;'</#if>>
<span class="logo abs"></span>
<div class="nav abs">
    <a href="${webPath!}login.shtml" <#if currentPage!""=="shouye"> class="active"</#if>>首页</a>
    <a href="${webPath!}product.shtml" <#if currentPage!""=="jieshao"> class="active"</#if> >产品介绍</a>
    <a href="${webPath!}novice.shtml" <#if currentPage!""=="novice"> class="active"</#if> >陪你入门</a>
    <a href="${webPath!}help.shtml" <#if currentPage!""=="help"> class="active"</#if> >帮助中心</a>
    <a href="${webPath!}downLoad.shtml" <#if currentPage!""=="download"> class="active"</#if> >客户端下载</a>
</div>
<span class="r_btn abs"><a class="loginBtn">登录</a><a href="javascript:void(0);" class="RegistBtn">注册</a></span>
</div>

<!-- app分享时提供图片 -->
<img src='${njxBasePath!}/images/fenxiang/wxfx-pic4.jpg' style="width:0px; height:0px; overflow:hidden;" />
<div id='wx_pic' style='margin:0 auto;display:none;'>
    <img src='${njxBasePath!}/images/wxfx-pic4.jpg' />
</div>

<div class="page-container reportWeb rel" style="width: 1200px; margin: auto;">
    <ul id="nav2">
        <li class="current"><a href="#pr1">事件简介</a></li>
        <li><a href="#pr2">事件趋势</a></li>
        <li><a href="#pr8">热点词</a></li>
        <li><a href="#pr9">意见领袖</a></li>
        <li><a href="#pr3">热门信息</a></li>
        <li><a href="#pr4">传播途径</a></li>
        <li><a href="#pr7">情绪分析</a></li>
        <li><a href="#pr5">博主分析</a></li>
        <li><a href="#pr6">数据类型</a></li>


    </ul>

    <!--分析完毕 start-->
    <div class="reportPreview">
        <#if isApp!0==1>
            <div class="logo"><img src="${njxBasePath!}/images/logo_analysis.png" width="120"></div>
        </#if>
        <div class="reportCon">

            <!--报告标题 start-->
            <div class="row-fluid">
                <div class="reportTit">
                    <div class="textShow">
                        <h1>${reportName}微博事件分析报告</h1>

                    </div>

                </div>
            </div>

            <!--报告标题 end-->
            <div class="reportBox">
                <!--事件简介 start-->
                <div class="row-fluid">
                    <div class="textShow">
                        <div class="tit titlBar" id="pr1">
                            <h2>事件简介</h2>
                            <div class="tit " >
                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                            </div>

                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                综合该事件的参与微博数，得出事件的总体评价。
                            </div>
                        </div>
                        <div class="textShow">
                            <div class="text">
                                <div class="textCon" id="introduce">
                                    <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                </div>
                            </div>
                        </div>
                        <div class="editBox">
      			<textarea class="inputList" id="textarea3">
本报告就采集的微博文章进行分析，对2016年1月21日到2016年2月20日以来，微博上的相关舆论进行了深入地分析，舆论最高峰出现在2016年1月28日，共有30篇相关舆论。微博分析关键词围绕海口、被困电梯、坠亡，在分析时间范围内，共有55篇相关舆论。最早的舆论在2016年1月21日由来去之间发布，标题为实拍：海口小区住户被困电梯近3小时后坠亡，整个事件由于xxx的转发，将事态推向高点。

      			</textarea>

                        </div>

                    </div>
                </div>
                <!--事件简介 end-->


                <!--网站统计 start-->
                <div class="row-fluid">
                    <div class="textShow">
                        <div class="tit titlBar" id="pr2">
                            <h2>事件趋势</h2>
                            <div class="tit " >
                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                            </div>

                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                该时间段内分时段的微博参与度变化走势。
                            </div>
                        </div>
                        <div class="textShow">
                            <div class="text chart">
                                <div class="textCon">
                                    <div class="mwbBorder">
                                        <div id="container1" class="echartAdpter" style="height: 400px;">
                                            <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                        </div>
                                        <div class="text"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="editBox">
                            <div class="mwbBorder">

					      	<textarea class="inputList" id="textarea3" style="height: auto;">从上图可以看出，整个事件的爆发点是再2－1，新闻类型的数据较为突出，加上微博和论坛网民的关注，将事态发展推向高点。
					      	</textarea>
                            </div>
                        </div>

                    </div>
                </div>
                <!--网站统计 end-->


                <!--热点关键词 start-->
                <div class="row-fluid">
                    <div class="textShow">
                        <div class="tit" id="pr8">
                            <h2>热点词</h2>
                            <div class="tit titlBar" >
                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                            </div>
                            <div class="tipinfo showing">
                                <div class="tiparro">
                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                    <i class="icon-remove close"></i>
                                </div>
                                <div class="tipcont">
                                    利用自然语义分析法，对该事件中所提及的关键词进行分词聚合，呈现出被提及频次最多的关键词；字号越大的词组，被提及频次越多。
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="textShow">
                        <div class="text chart">
                            <div class="textCon">
                                <div class="mwbBorder">
                                    <div id="container7"  class="echartAdpter"  style="height: 410px;">
                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- </div> -->
                    <!-- </div>  -->
                    <!--热点关键词 end-->

                    <!--意见领袖 start-->
                    <div class="row-fluid">
                        <div class="textShow">
                            <div class="tit" id="pr9">
                                <h2>意见领袖</h2>
                                <div class="tit titlBar" >
                                    <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                </div>
                                <div class="tipinfo showing">
                                    <div class="tiparro">
                                        <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                        <i class="icon-remove close"></i>
                                    </div>
                                    <div class="tipcont">
                                        指参与该事件传播的微博用户中，影响力最大（即粉丝量最多）的“活跃分子”。
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row-fluid">
                            <div class="mwbBorder mwbBorder2">
                                <div class="mwblist mwblist3" id="opinionLeader">
                                    <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;"/>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <!--意见领袖 end-->
                <!-- 核心传播人  start -->
                <div class="row-fluid" id="pr6Div">
                    <div class="textShow">
                        <div class="tit" id="pr6">
                            <h2>核心传播人</h2>
                            <div class="tit titlBar" >
                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                            </div>
                            <div class="tipinfo showing">
                                <div class="tiparro">
                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                    <i class="icon-remove close"></i>
                                </div>
                                <div class="tipcont">
                                    在该事件的传播中，发布或转发相关微博数量最多的微博博主。
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="mwbBorder mwbBorder2">
                            <div class="align_c">
                                <ul id="myTab" class="tab_menu phoneTab_menu mainbody clear">
                                    <div class="tab_menuHidden">
                                        <li id="li1" class="current">传播机构</li>
                                        <li id="li2">传播媒体</li>
                                        <li id="li3">传播媒体人</li>
                                        <li id="li4">传播网民</li>
                                    </div>
                                </ul>
                            </div>

                            <div id="myTabContent" class="tab-content">
                                <div class="tab-pane fade in active" id="modelOne" isNull="1">
                                    <div class="mwblist" id="tab_modelOne"></div>
                                </div>
                                <div class="tab-pane fade" id="modelTwo" isNull="1">
                                    <div class="mwblist" id="tab_modelTwo" ></div>
                                </div>
                                <div class="tab-pane fade" id="modelThree" isNull="1">
                                    <div class="mwblist" id="tab_modelThree"></div>
                                </div>
                                <div class="tab-pane fade" id="modelFour" isNull="1">
                                    <div class="mwblist" id="tab_modelFour"></div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <!-- 核心传播人  end -->
                <!--热门信息 start-->
                <div class="row-fluid">
                    <div class="textShow">
                        <div class="tit" id="pr3">
                            <h2>热门信息</h2>
                            <div class="tit titlBar" >
                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                            </div>
                            <div class="tipinfo showing">
                                <div class="tiparro">
                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                    <i class="icon-remove close"></i>
                                </div>
                                <div class="tipcont">
                                    热门微博是按该事件中转发频次排序的原创微博；热门转发是按该事件中引起二次转发频次的转发微博。
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="">
                        <div class="text chart">
                            <div class="textCon">
                                <div class="mwbBorder mwbBorder2">
                                    <div class="yq_con yqtabs" id="hotWBNews">

                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">

                                    </div>



                                </div>

                            </div>
                        </div>
                    </div>

                </div>
                <!--热门信息 end-->

                <!--传播途径－事件溯源 start-->
                <div class="row-fluid">
                    <div class="textShow">
                        <div class="tit" id="pr4">
                            <h2>传播途径</h2>
                            <div class="tit titlBar" >
                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                            </div>
                            <div class="tipinfo showing">
                                <div class="tiparro">
                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                    <i class="icon-remove close"></i>
                                </div>
                                <div class="tipcont">
                                    根据事件传播脉络的时间节点展示关键传播的微博信息。
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="yb2 rel" id="eventProfile" style="min-height: 350px;">
                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                    </div>
                </div>
                <!--传播途径－事件溯源 end-->


                <!--情绪分析 start-->
                <div class="row-fluid">
                    <div class="textShow">
                        <div class="tit" id="pr7">
                            <h2>情绪分析</h2>
                            <div class="tit titlBar" >
                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                            </div>
                            <div class="tipinfo showing">
                                <div class="tiparro">
                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                    <i class="icon-remove close"></i>
                                </div>
                                <div class="tipcont">
                                    对该微博事件的转发、评论文本进行自然语义分析后，所得出的情绪倾向判断。
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="textShow">
                        <div class="text chart">
                            <div class="textCon">
                                <div class="row-fluid">
                                    <div class="mwbBorder">
                                        <h2>
                                            <span><i class="c_1"></i>转发表情分析</span>
                                            <span><i class="c_2"></i>评论表情分析</span>
                                        </h2>
                                        <div class="mwbcon rel" style="min-height:300px;" id="container7_3">
                                            <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="editBox">
                        <div class="mwbBorder">

                        </div>
                        <div class="mwbBorder">
                        </div>
                    </div>
                </div>
                <!--情绪分析 end-->



                <!--博主分析 start-->
                <div class="row-fluid">
                    <div class="textShow">
                        <div class="tit" id="pr5">
                            <h2>博主分析</h2>
                        </div>
                    </div>
                    <div class="textShow">
                        <div class="text chart">
                            <div class="textCon">
                                <div class="row-fluid">
                                    <div class="mwbBorder">
                                        <h2>博主地域分析</h2>
                                        <div class="tit titlBar"style="margin-left:100px;margin-top:-40px;" >
                                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                        </div>
                                        <div class="tipinfo showing">
                                            <div class="tiparro">
                                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                <i class="icon-remove close"></i>
                                            </div>
                                            <div class="tipcont">
                                                根据对该事件进行过转发、评论的所有博主在操作时的IP地址所进行的地域统计。
                                            </div>
                                        </div>
                                        <div class="mwbcon rel">
                                            <div id="container5_1" style="height: 468px;width:100%float: left;"  class="echartAdpter" >
                                                <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                            </div>
                                            <div class="info-section float_r">
                                                <table id="scrollTab" border="" cellspacing="" cellpadding="" class="map_table map_table2">
                                                    <thead>
                                                    <tr>
                                                        <th style="width:144px;">省份</th>
                                                        <th style="width:158px;">信息数</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="c5_tb">
                                                    <tr>
                                                        <td colspan="2">
                                                            <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                                        </td>
                                                    </tr>

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="mwbBorder">
                                        <h3 class="f_c2">＊本次事件中转发数量最多的前10微博博主</h3>
                                        <div class="mwbcon rel">
                                            <div class="table table4 float_l">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <thead>
                                                    <tr>
                                                        <th width="">昵称</th>
                                                        <th width="20%">地域</th>
                                                        <th width="20%">粉丝数</th>
                                                        <th width="20%">微博数</th>
                                                        <th width="20%">参与微博个数（转发）</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="c5_top10">
                                                    <tr>
                                                        <td colspan="5">
                                                            <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class=" mwb5 float_l">
                                        <div class="mwbBorder">
                                            <h2>男女比例</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    参与该事件微博传播的男女占比。
                                                </div>
                                            </div>
                                            <div class="mwbcon">
                                                <div class="rel" style="height: 346px;">
                                                    <div class="float_r echartAdpter" style="width: 70%; margin-right: 0px;height: 300px;" id="container5_2">
                                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                                    </div>
                                                    <div class="genderBox" id="biliBox_container5_2">

                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div class=" mwb5 float_r">
                                        <div class="mwbBorder">
                                            <h2>用户认证</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    参与该事件微博传播的男女占比。
                                                </div>
                                            </div>
                                            <div class="mwbcon">
                                                <div class="rel" style="height: 346px;" >
                                                    <div class="float_r echartAdpter" style="width: 70%; margin-right: 0px;height: 300px;" id="container5_3">
                                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                                    </div>
                                                    <div class="biliBox biliBox2 biliBox4" id="biliBox_container5_3">

                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class=" mwb5 float_l">
                                        <div class="mwbBorder">
                                            <h2>海内外统计</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    参与该事件传播的微博用户中海外用户与国内用户占比。
                                                </div>
                                            </div>
                                            <div class="mwbcon">
                                                <div class="rel" style="height: 346px;">
                                                    <div class="float_r echartAdpter" style="width: 70%; margin-right: 0px;height: 300px;" id="container5_4" >
                                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                                    </div>
                                                    <div class="biliBox" id="biliBox_container5_4">

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class=" mwb5 float_r">
                                        <div class="mwbBorder">
                                            <h2>水军分析</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    根据水军研判模型，基于“微博原创、转发比例”、“用户关注数、粉丝数比例”、辅助“微博用户活跃频次”等判断因子进行综合计算打分，结合水军特征行为数据分析，进行水军判定。
                                                </div>
                                            </div>
                                            <div class="mwbcon">
                                                <div class="rel" style="height: 346px;">
                                                    <div class="float_r echartAdpter" style="width: 70%; margin-right: 0px;height: 300px;" id="container5_5">
                                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px">
                                                    </div>
                                                    <div class="biliBox" id="biliBox_container5_5">

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="editBox">
                        <div class="mwbBorder">
                            <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="博主地域分析" onkeyup="checkLength(this)"/>
                            </h2>

                        </div>
                        <div class="mwbBorder">
                            <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="男女比列" onkeyup="checkLength(this)"/>
                            </h2>

                        </div>
                        <div class="mwbBorder">
                            <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="用户认证" onkeyup="checkLength(this)"/>
                            </h2>
                        </div>
                        <div class="mwbBorder">
                            <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="境内外认证" onkeyup="checkLength(this)"/>
                            </h2>

                        </div>
                        <div class="mwbBorder">
                            <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="水军分析" onkeyup="checkLength(this)"/>

                            </h2>

                        </div>


                    </div>
                </div>
                <!--博主分析 end-->

                <!--数据类型 start-->
                <div class="row-fluid">
                    <div class="textShow">
                        <div class="tit" id="pr6">
                            <h2>数据类型</h2>
                        </div>
                    </div>
                    <div class="textShow">
                        <div class="text chart">
                            <div class="textCon">
                                <div class="row-fluid">
                                    <div class=" mwb5 float_l">
                                        <div class="mwbBorder">
                                            <h2>粉丝分布</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    参与该事件的微博博主的粉丝区间分布。
                                                </div>
                                            </div>
                                            <div id="container6_1" class="mwbcon" style="height: 325px;">
                                                <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                            </div>
                                        </div>
                                    </div>
                                    <div class=" mwb5 float_r">
                                        <div class="mwbBorder">
                                            <h2>敏感占比</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    敏感判定由自建的情感研判模型完成，该模型与哈尔滨工业大学社会计算与信息检索研究中心合作，依托其在业内知名的中文语义分析处理能力，结合机器学习，通过输入人工标注学习训练语料方式，持续提升研判模型准确度；该模型摒弃了仅基于情感词的单一研判方式，通过对内容精准切分词、中文语义分析、通过词距词序词频计算并按权重打分等方式，根据模型训练结果的判定标准，对内容进行情感判定。
                                                </div>
                                            </div>
                                            <div  id="container6_2" class="mwbcon" style="height: 325px;">
                                                <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">

                                            </div>

                                        </div>
                                    </div>

                                </div>
                                <div class="row-fluid">
                                    <div class=" mwb5 float_l">
                                        <div class="mwbBorder">
                                            <h2>原微博转发微博占比</h2>
                                            <div class="tit titlBar" style="margin-left:150px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    原发微博和转发微博的综合占比。
                                                </div>
                                            </div>
                                            <div class="mwbcon">
                                                <div class="rel" style="height: 346px;">
                                                    <div id="container6_3" class="float_r echartAdpter" style="width: 70%; margin-right: 0px;height: 300px;">
                                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                                    </div>
                                                    <div class="biliBox" id="biliBox_container6_3">

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class=" mwb5 float_r">
                                        <div class="mwbBorder">
                                            <h2>转发层级占比</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    参与该事件转发的所有层级数占比。
                                                </div>
                                            </div>
                                            <div class="mwbcon">
                                                <div class="rel" style="height: 346px;">
                                                    <div id="container6_4" class="float_r echartAdpter" style="width: 70%; margin-right: 0px;height:300px;">
                                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                                    </div>
                                                    <div class="biliBox biliBox2 biliBox4" id="biliBox_container6_4">

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="mwbBorder">
                                        <h2>转发地域分布</h2>
                                        <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                        </div>
                                        <div class="tipinfo showing">
                                            <div class="tiparro">
                                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                <i class="icon-remove close"></i>
                                            </div>
                                            <div class="tipcont">
                                                根据对该事件进行转发的所有博主在操作时的IP地址所进行的地域统计。
                                            </div>
                                        </div>
                                        <div class="mwbcon rel"  >
                                            <div id="container6_5" style="height:300px;">
                                                <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                            </div>
                                            <div class="info-section echartAdpter" style=" top: 10px;z-index:1;right:35px;">
                                                <table border="" cellspacing="" cellpadding="" class="map_table map_table2">
                                                    <thead>
                                                    <tr>
                                                        <th width="30%">排名</th>
                                                        <th width="35%">省份</th>
                                                        <th width="35%">转发数</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="container6_7">
                                                    <tr>
                                                        <td colspan="3"><img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;"></td>
                                                    </tr>

                                                    </tbody>
                                                </table>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="row-fluid">
                                        <div class="mwbBorder">
                                            <h2>发布设备分布</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    对关于该事件的微博信息传播的发布设备进行分析统计。
                                                </div>
                                            </div>
                                            <div class="mwbcon">
                                                <div class="rel" style="height: 346px;">
                                                    <div id="container6_6" class="float_r  echartAdpter" style="width: 70%; margin-right: 0px;height:350px;">
                                                        <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                                    </div>
                                                    <div class="biliBox biliBox2 biliBox3" id="biliBox_container6_6">

                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row-fluid" id="pr10Div">
                                        <div class="mwbBorder">
                                            <h2>微博观点分析</h2>
                                            <div class="tit titlBar" style="margin-left:100px;margin-top:-40px;">
                                                <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                                            </div>
                                            <div class="tipinfo showing">
                                                <div class="tiparro">
                                                    <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                                    <i class="icon-remove close"></i>
                                                </div>
                                                <div class="tipcont">
                                                    对该事件微博传播中的网民评论进行抽样聚类分析后得出的结果。
                                                </div>
                                            </div>
                                            <div class="mwbcon">
                                                <div id="container12" class="echartAdpter"  style="height: 600px;">
                                                    <img src="${njxBasePath!}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                                </div>
                                                <div class="viewpoint" id="container12List">

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="editBox">
                            <div class="mwbBorder">
                                <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="粉丝分布" onkeyup="checkLength(this)"/>

                                </h2>

                            </div>
                            <div class="mwbBorder">
                                <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="敏感占比" onkeyup="checkLength(this)"/>
                                </h2>


                            </div>
                            <div class="mwbBorder">
                                <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="转发评论比" onkeyup="checkLength(this)"/>

                                </h2>
                            </div>
                            <div class="mwbBorder">
                                <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="直接转发占比" onkeyup="checkLength(this)"/>

                                </h2>
                            </div>
                            <div class="mwbBorder">
                                <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="转发地域分布" onkeyup="checkLength(this)"/>
                                </h2>

                            </div>
                            <div class="mwbBorder">
                                <h2 style="padding-left: 0; position: relative;"><input type="text" name="" id="" size="20" value="发布设备分布" onkeyup="checkLength(this)"/>
                                </h2>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <#if isApp!0=1>
            <div class="clear" style="height: 35px;"></div>
            <section id = "appdownload" class="section" style="margin-bottom: 0px;bottom:0px;left:0;z-index: 50;width: 100%;position: fixed;background-color: rgba(0,0,0,0.6);">
                <div style="height:45px;">
                    <div style="position: absolute;top:12px;left:10px;height:100%;right:0%;color:#FFF;font-size:16px;"></div>
                    <div class = "subBtn" style="background-color:#fd9237;border-radius:5px;position: absolute;top:8px;font-size:14px;text-align:center;line-height:30px;width: 60%;height:30px;right:20%;color:#FFF;cursor:pointer;" onClick="goSubscribe();">我也要分析</div>
                </div>
            </section>
        </#if>
    </div>
    <!--分析完毕 end-->
</div>

</div>


<script src="${staticResourcePathH5}/js/jquery.nav2.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/jquery.tabs.js?v=${SYSTEM_INIT_TIME}"></script>
<script>
    function sendPost(param,url,callback){
        $.ajax({
            url : url,
            type : "post",
            data : param,
            success : function(data){
                callback(data);
            },
            error:function(){
                sendPost(param,url,callback);
            }
        })
    };

    function goSubscribe(){
        if("${admin!}"){
            location.href = actionBase+"/weibo/createWeiBoAnalysis?createType=1";
        }else{
            location.href = actionBase+"/analysis";
        }
    }

    $(function(){
        $('#nav2').onePageNav();
        $('.yqtabs').Tabs({
            event:'click'
        });
        //显示/隐藏编辑框
        $(".row-fluid .textShow .tool.toolhover #edit").on("click",function(){
            var $this = $(this);
            if($this.parents(".row-fluid").hasClass("hover")){
                $this.parents(".row-fluid").removeClass("hover")
            } else{
                $(".row-fluid").removeClass("hover");
                $this.parents(".row-fluid").addClass("hover");
            }
        });

        //事件走势显示/隐藏编辑框
        $(".row-fluid .yb .tool.toolhover #edit").on("click",function(){
            var $this = $(this);
            if($this.parents(".row-fluid").hasClass("hover")){
                $this.parents(".row-fluid").removeClass("hover")
            } else{
                $(".row-fluid").removeClass("hover");
                $this.parents(".row-fluid").addClass("hover");
            }
        });



        $(".row-fluid .editBox .tool.tooledit #save").on("click",function(){
            var $this = $(this);
            if($this.parents(".row-fluid").hasClass("hover")){
                $this.parents(".row-fluid").removeClass("hover")
            } else{
                $(".row-fluid").removeClass("hover");
                $this.parents(".row-fluid").addClass("hover");
            }
        });

        //统计图样式选中效果
        $(".chartChoice >ul>li").on("click",function(){
            var $this = $(this);
            $this.siblings("li").removeClass("clicked");
            $(this).addClass("clicked");

        });

    });
</script>

<script type="text/javascript">
    /*----外置函数star----*/
    var ie = !!window.attachEvent && !window.opera;
    var ie9 = ie && (!!+"\v1");
    var inputhandler = function(node,fun){
        if("oninput" in node){
            node.oninput = fun;
        }else{
            node.onpropertychange = fun;
        }
        if(ie9) node.onkeyup = fun;
    }

    /*----外置函数end---*/
    var main = document.getElementById("textarea3");
    inputhandler(main,function(){
        if(!ie) main.style.height = 48+"px";
        var height = main.scrollHeight;if(height>=48){
            main.style.height = height+"px";
        }else{
            main.style.height = 48+"px";
        }
    });

    function typicalListAdapter(data){
        /* if (data[4]==null||data[4]=="[]"){
            $("#container10List").hide();
        }else{
            var list = eval(data[4]);
            typicalListJoin(list,"container10List");
        }
        if (data[5]==null||data[5]=="[]"){
            $("#container11List").hide();
        }else{
            var list = eval(data[5]);
            typicalListJoin(list,"container11List");
        } */
        if (data[6]==null||data[6]=="[]"){
            $("#container12List").hide();
        }else{
            var list = eval(data[6]);
            typicalListJoin(list,"container12List");
        }
    }
    function typicalListJoin(data,dom){
        var lenght = data.length>4?4:data.length;
        var title = "网友";
        if(dom=="container10List"){
            title = "媒体";
        }
        var html = '<div class="title"><h1 contenteditable="false" class="contenteditable"><i class="icon-users"></i> '+title+'观点主要表现在如下几个方面：</h1></div><ul>';
        for(var i=0;i<lenght;i++){
            var stat = data[i];
            html += '<li><h2 contenteditable="false" class="contenteditable"><i class="deleLi"></i>'+stat.name+'('+stat.percentStr+')</h2>';
            var ICNList = stat.iContentCommonNetList;
            var len = ICNList.length>3?3:ICNList.length;
            for(var j=0;j<len;j++){
                var icn = ICNList[j];
                var author = icn.author;
                if(!author||author==null||author==""){
                    author = icn.captureWebsiteName;
                }
                html += '<a class="shareA" href="'+icn.webpageUrl+'" target="_blank"><p contenteditable="false" class="contenteditable">'+author+"："+icn.title+'。</p></a>';
            }
            html += '</li>';
        }
        html += '</ul>';
        $("#"+dom).html(html);
    }
    function typicalPieAdapter(data){
        //typicalPieCallBack(data[0],"container10");
        //typicalPieCallBack(data[1],"container11");
        typicalPieCallBack(data[2],"container12");
        typicalListAdapter(data);
        $("#summary").html(data[3]);
    }

    function typicalPieCallBack(data,id){
        var c4 = document.getElementById(id);
        if (data==null||data==""){
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath!}/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return false;
        }else{
            data = eval(data);
            if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
                c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath!}/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                return;
            }else if(id=="container12"){
                if(data[0].data!=null && data[0].data.length>10){
                    data[0].data=data[0].data.slice(0,10);
                }
                if(data[0].legend!=null && data[0].legend.length>10){
                    data[0].legend=data[0].legend.slice(0,10);
                }
                var _chartColumn10 = typicalPieChart2(data[0],id);
            }else{
                //typicalPieChart2(data[0],id);
                viewBarChart(data[0],id);
            }

        }
    }
    function typicalPieCallBack(data,id){
        var c4 = document.getElementById(id);
        if (data==null||data==""){
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath!}/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return false;
        }else{
            data = eval(data);
            if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
                c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath!}/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                return;
            }else if(id=="container12"){
                if(data[0].data!=null && data[0].data.length>10){
                    data[0].data=data[0].data.slice(0,10);
                }
                if(data[0].legend!=null && data[0].legend.length>10){
                    data[0].legend=data[0].legend.slice(0,10);
                }
                var _chartColumn10 = typicalPieChart2(data[0],id);
            }else{
                //typicalPieChart2(data[0],id);
                viewBarChart(data[0],id);
            }

        }
    }
    function typicalPieChart2(data,dom){
        var config = require(
                [
                    'echarts',
                    'echarts/chart/pie',
                ],
                function (ec) {
                    var chart4 = ec.init(document.getElementById(dom));
                    var option = {
                        animation : false,
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        toolbox: {
                            show : true,
                            orient:'vertical',//vertical|horizontal
                            y:30,
                            feature : {
                                mark : {show: false},
                                dataView : {
                                    show: false,
                                    readOnly: false,
                                    lang: ['数据视图', '关闭', '刷新']
                                },
                                restore : {show: false},
                                saveAsImage : {
                                    show: false,
                                    name:data.title,
                                    type:'jpeg',
                                    lang : ['点击保存']
                                }
                            }
                        },
                        calculable : false,
                        legend:{
                            orient : 'vertical',
                            x : 'center',
                            y : '350',
                            data:data.legend,
                            formatter:function(v){
                                return v.length>22? v.substr(0,22)+"...":v;
                            }
                        },
                        series : [
                            {
                                name:data.title,
                                type:'pie',
                                center:['50%',200],
                                radius : ['20%','45%'],
                                roseType : 'radius',
                                startAngle:0,
                                itemStyle : {
                                    normal : {
                                        label : {
                                            show : true,
                                            textStyle:{
                                                fontSize:'12',
                                                fontWeight:'normal'
                                            },
                                            formatter: "{d}%"
                                        },
                                        labelLine : {
                                            show : true
                                        }
                                    },
                                    emphasis : {
                                        label : {
                                            show : false,
                                            position : 'center',
                                            textStyle : {
                                                fontSize : '30',
                                                fontWeight : 'bold'
                                            }
                                        }
                                    }
                                },
                                data:data.data
                            }
                        ]
                    }
                    chart4.setOption(option);
                    var enConfig = require('echarts/config');

                }
        );

    }

    $(function(){
        var params = {"tickets":'${tickets!}',"reportTime":'${reportTime!}'};
        //简介以及发展趋势列表
        sendPost(params,"${njxBasePath!}/weibo/eventProfile",profileCallback);
        //趋势图
        sendPost(params,"${njxBasePath!}/weibo/sitesStatisticsV2",LineCallBack);

        //传播列表
        sendPost(params,"${njxBasePath!}/weibo/propagationPath",pathCallback);

        //关键词云
        sendPost(params,"${njxBasePath!}/weibo/wordCloud",cloudCallback);

        //博主分析
        sendPost(params,"${njxBasePath!}/weibo/blogAnalysis",blogAnalysisCallback);

        //情绪分析
        sendPost(params,"${njxBasePath!}/weibo/emoteAnalysis",emoteAnalysisCallback);

        //数据类型
        sendPost(params,"${njxBasePath!}/weibo/dataType",dataTypeCallback);

        //热门信息
        sendPost(params,"${njxBasePath!}/weibo/hotNews",hotNewsCallback);

        //意见领袖
        sendPost(params,"${njxBasePath!}/weibo/hotPeople",hotPeopleCallBack);
        var flag = "${reportDate!}";
        console.log(flag);
        if(flag=='a'){
            var htStr = "<li class='current'><a href='#pr1'>事件简介</a></li>"+
                    "<li><a href='#pr2'>事件趋势</a></li>"+
                    "<li><a href='#pr8'>热点词</a></li>"+
                    "<li><a href='#pr9'>意见领袖</a></li>"+
                    "<li><a href='#pr6'>核心传播人</a></li>"+
                    "<li><a href='#pr3'>热门信息</a></li>"+
                    "<li><a href='#pr4'>传播途径</a></li>"+
                    "<li><a href='#pr7'>情绪分析</a></li>"+
                    "<li><a href='#pr5'>博主分析</a></li>"+
                    "<li><a href='#pr11'>数据类型</a></li>"+
                    "<li><a href='#pr10'>评论分析</a></li>";
            $("#nav2").html(htStr);
            //核心传播人
            sendPost(params,"${njxBasePath!}/weibo/coreTranInfo",coreTranInfoCallBack);
            //热门评论
            sendPost(params,"${njxBasePath!}/weibo/typicalViews",typicalPieAdapter);
        }else{
            var htStr = "<li class='current'><a href='#pr1'>事件简介</a></li>"+
                    "<li><a href='#pr2'>事件趋势</a></li>"+
                    "<li><a href='#pr8'>热点词</a></li>"+
                    "<li><a href='#pr9'>意见领袖</a></li>"
                    "<li><a href='#pr3'>热门信息</a></li>"+
                    "<li><a href='#pr4'>传播途径</a></li>"+
                    "<li><a href='#pr7'>情绪分析</a></li>"+
                    "<li><a href='#pr5'>博主分析</a></li>"+
                    "<li><a href='#pr11'>数据类型</a></li>";

            $("#nav2").html(htStr);

            //20160902180000 前的报告样式
            $("#pr6Div").hide();
            $("#pr10Div").hide();
        }
        $('#li1').bind('click',function(){
            //tab-pane fade in active
            $("#modelOne").attr("class","tab-pane fade in active");
            $("#modelTwo").attr("class","tab-pane fade");
            $("#modelThree").attr("class","tab-pane fade");
            $("#modelFour").attr("class","tab-pane fade");
            $("#li1").attr("class","current");
            $("#li2").attr("class","");
            $("#li3").attr("class","");
            $("#li4").attr("class","");
            $("#modelOne").show();
            $("#modelTwo").hide();
            $("#modelThree").hide();
            $("#modelFour").hide();
        });

        $('#li2').bind('click',function(){
            $("#modelOne").attr("class","tab-pane fade");
            $("#modelTwo").attr("class","tab-pane fade in active");
            $("#modelThree").attr("class","tab-pane fade");
            $("#modelFour").attr("class","tab-pane fade");
            $("#li1").attr("class","");
            $("#li2").attr("class","current");
            $("#li3").attr("class","");
            $("#li4").attr("class","");
            $("#modelOne").hide();
            $("#modelTwo").show();
            $("#modelThree").hide();
            $("#modelFour").hide();
        });

        $('#li3').bind('click',function(){
            $("#modelOne").attr("class","tab-pane fade");
            $("#modelTwo").attr("class","tab-pane fade");
            $("#modelThree").attr("class","tab-pane fade in active");
            $("#modelFour").attr("class","tab-pane fade");
            $("#li1").attr("class","");
            $("#li2").attr("class","");
            $("#li3").attr("class","current");
            $("#li4").attr("class","");
            $("#modelOne").hide();
            $("#modelTwo").hide();
            $("#modelThree").show();
            $("#modelFour").hide();
        });

        $('#li4').bind('click',function(){
            $("#modelOne").attr("class","tab-pane fade");
            $("#modelTwo").attr("class","tab-pane fade");
            $("#modelThree").attr("class","tab-pane fade");
            $("#modelFour").attr("class","tab-pane fade in active");
            $("#li1").attr("class","");
            $("#li2").attr("class","");
            $("#li3").attr("class","");
            $("#li4").attr("class","current");
            $("#modelOne").hide();
            $("#modelTwo").hide();
            $("#modelThree").hide();
            $("#modelFour").show();
        });

    });
</script>
<script language="javascript">
    $(function() {
        //说明文字显示隐藏
        $(".titlBar .icon-tishi").on("click",function(){
            $(this).parents(".titlBar").next(".tipinfo").toggle(300)
        });
        $(".tipinfo .close").on("click",function(){
            $(this).parents(".tipinfo").hide(300)
        });
    });</script>
<#include "../../buttom.ftl" >
</body>
</html>