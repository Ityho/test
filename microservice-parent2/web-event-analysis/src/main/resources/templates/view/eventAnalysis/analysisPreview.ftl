<#include "../../top.ftl" >
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/analysis/common.css?v=${SYSTEM_INIT_TIME}"/>
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/frame.css?v=${SYSTEM_INIT_TIME}"/>
    <link href="${staticResourcePathH5}/css/analysis/style.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
    <link href="${staticResourcePathH5}/css/other.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
    <link href="${staticResourcePathH5}/css/eventAnalysis.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
    <link href="${staticResourcePathH5}/css/report.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${njxBasePath}/js/echarts/chart/tree3.min.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script src="${staticResourcePathH5}/js/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/jquery.chromatable.js?v=${SYSTEM_INIT_TIME}"></script>
    <script type="text/javascript" src="${njxBasePath}/js/echarts/echarts4.min.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${njxBasePath}/js/echarts/echarts.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/pie.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/bar.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/map.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/line.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/radar.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/wordCloud.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/force.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/funnel.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticResourcePathH5}/js/eventAnalysisPreview.js?v=${SYSTEM_INIT_TIME}"></script>
    <link type="text/css" rel="stylesheet" href="${staticResourcePathH5}/css/popModal.css?v=${SYSTEM_INIT_TIME}">
    <!--分享-->
    <script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=3&amp;lang=zh"></script>
    <script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/bshareC0.js?v=${SYSTEM_INIT_TIME}"></script>
    <script type="text/javascript" charset="utf-8">
        var staticResourcePathH5='${staticResourcePathH5}';
        $(function(){
            var pre = location.href.split("/view")[0];
            var ticket = '${tickets}';
            var title = '${reportName}事件分析报告，用数据挖掘真相-微热点';
            document.title = title;
            var url = pre+"/view/eventAnalysis1/analysisPreview.action?isShare=1&tickets="+encodeURIComponent(ticket);
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
                        url = pre+"/view/eventAnalysis1/analysisPreview.action?isShare=1&tickets="+encodeURIComponent(ticket);
                    }else{
                        pre = "http://wyq.sina.com";
                        url = pre+"/analysisPreview.shtml?isShare=1&shareType=1&tickets="+encodeURIComponent(ticket);
                    }
                }

            }
            //微博，qq,空间分享格式
            bShare.addEntry({
                title:"[微热点]${reportName}",
                summary:"的全网事件分析，用数据挖掘真相",
                url: url,
                pic:staticResourcePathH5+"/images/fenxiang/weibowyq-icon300.jpg"
            });
            //微信
            bShare.addEntry({
                title:"[来自@微热点]${reportName}的全网事件分析，用数据挖掘真相~网络链接",
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
<style>
    #hotNews tr td:first-child{
        text-align:left;
        padding-left: 20px;
    }
</style>
<script>
    var phone = false;
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
                //$('.fenxiang').css('display', 'none');
                //$('#weibo_task_result_star_content_div,#weibo_task_result_line_content_div,#weibo_task_result_fans_div').css('height', '220px');
                $('.echartAdpter').css('width', '100%');
                phone = true;
            }
        }
        // $(".fenxiang,.fenxiang2").show();
        var url = location.href;
        var isApp = document.getElementById("isApp").value;
        var Uid = "${admin!}";
        //console.log( Uid);
        if((Uid == "") && bs.versions.mobile){
            var text = $("#style").text().replace("margin-top: 70px;","margin-top: 0px;");
            $("#style").text(text);
            $("#head").css("display","none");
            $("#headText").css("display","none");
            $(function(){
                $(".loginBtn").on("click",function(){
                    location.href="${njxBasePath}/indexLocal";
                });
                $(".RegistBtn").on("click",function(){
                    location.href="${njxBasePath}/register";
                });
            });

        }else if(bs.versions.mobile && Uid!=""){
            var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
            if (ua.match(/WeiBo/i) == "weibo") {
                //在新浪微博客户端打开
                //document.querySelector(".RegistBtn").style.display="none";
                $(".RegistBtn").css("display","none");
                //console.log("在新浪微博客户端打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href="http://apps.weibo.com/3960037780/8rXM111J";
                });
                //});
            }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
                //在微信中打开
                //document.querySelector(".RegistBtn").style.display="none";
                $(".RegistBtn").css("display","none");
                //console.log("在微信中打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href="${njxBasePath}/analysis";
                });
                //});
            }else{
                //手机浏览器打开
                //console.log("手机浏览器打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href='${njxBasePath}/createAnalysis?createType=1';
                });
                $(".RegistBtn").on("click",function(){
                    location.href="${njxBasePath}/register.action";
                });
                //});
            }
        }else if(bs.versions.mobile){
            var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
            if (ua.match(/WeiBo/i) == "weibo") {
                //在新浪微博客户端打开
                $(".RegistBtn").css("display","none");
                //console.log("在新浪微博客户端打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href="http://apps.weibo.com/3960037780/8rXM111J";
                });
                //});
            }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
                //在微信中打开
                $(".RegistBtn").css("display","none");
                //console.log("在微信中打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href="${njxBasePath}/analysis";
                });
                //});
            }else{
                //手机浏览器打开
                //console.log("手机浏览器打开");
                //$(function(){
                $(".loginBtn").on("click",function(){
                    location.href="${njxBasePath}/indexLocal";
                });
                $(".RegistBtn").on("click",function(){
                    location.href="${njxBasePath}/register";
                });
                //});
            }
        }else{
            //pc中打开
            //$(function(){
            $(".loginBtn").on("click",function(){
                location.href="${webPath}logon.shtml";
            });
            $(".RegistBtn").on("click",function(){
                location.href="${webPath}user/goRegister.shtml";
            });
            //})
        }
    });
</script>
<body class="mobileStyle">
<input value="${isApp!}" type="hidden" id = "isApp">
<img src='${njxBasePath}/images/fenxiang/wxfx-pic1.jpg' style="width:0px; height:0px; overflow:hidden;" />
<div id='wx_pic' style='margin:0 auto;display:none;'>
    <img src='${njxBasePath}/images/wxfx-pic4.jpg' />
</div>

<div class="page-container reportWeb rel" style="width: 1200px; margin: auto;">
    <ul id="nav2">
        <li class="current"><a href="#pr1">事件简介</a></li>
        <li><a href="#pr2">事件走势</a></li>
        <li><a href="#pr3">网站统计</a></li>
        <li><a href="#pr4">数据类型</a></li>
        <li><a href="#pr5">关键词云</a></li>
        <li><a href="#pr6">热门信息</a></li>
        <li><a href="#pr7">热点网民</a></li>
        <li><a href="#pr8">传播路径</a></li>
        <li><a href="#pr9">相关词</a></li>
        <li><a href="#pr10">网民观点</a></li>
        <li><a href="#pr11">热点总结</a></li>
    </ul>

    <!--分析完毕 start-->
    <div class="reportPreview">
        <s:if test = 'isApp==1'>
            <div class="logo"style="margin-left:5px;"><img src="${njxBasePath}/images/logo_analysis.png" width="120"></div>
        </s:if>
    <div class="reportCon">
        <!--报告标题 start-->
        <div class="row-fluid">
            <div class="reportTit">
                <div class="textShow">
                    <h1>${reportName}事件分析报告</h1>
                </div>
            </div>
        </div>

        <!--报告标题 end-->
        <div class="reportBox">

            <!--事件简介 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr1">
                        <h2>事件简介</h2>
                    </div>
                </div>

                <div class="textShow">
                    <div class="text">
                        <div class="textCon" id="introduce">
                            <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px">
                        </div>
                    </div>

                </div>
                <div class="editBox">
      			<textarea class="inputList" id="textarea3">本报告就采集的文章进行分析，对2016年1月21日到2016年2月20日以来，互联网上的相关舆论进行了深入地分析，舆论最高峰出现在2016年1月28日，共有30篇相关舆论。文章分析关键词围绕海口、被困电梯、坠亡，在分析时间范围内，共有55篇相关舆论。最早的舆论在2016年1月21日发布在中国网，表题为实拍：海口小区住户被困电梯近3小时后坠亡。后续舆论主要集中在境内，新闻类型的相关舆论最多，主要来源于搜狐、中青网、新民网、百度、中国江苏网等极大站点。总体来说，整个事件发展趋势较为平缓，详细报告如下。
      			</textarea>

                </div>

            </div>
            <!--事件简介 end-->

            <!--事件走势 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr2">
                        <h2>事件走势</h2>
                    </div>
                </div>
                <div class="yb2 rel" id="eventProfile">
                    <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px">
                </div>
            </div>
            <!--事件走势 end-->

            <!--网站统计 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr3">
                        <h2>网站统计</h2>
                        <!--
                        <span class="rightbtn rightbtn2">
                            <button onclick="javascript:return false;" style="margin-top:-10px;"id="notifyModal_ex1"  class="hintModal"><img src="${njxBasePath}/images/querybtn.png"><div class="hintModal_container">时间段内全部来源信息的分时统计</div></button>
                        </span> -->
                    </div>
                </div>

                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder">
                                <div class="mwbcon rel">
                                    <div id="container1" class="echartAdpter" style="height: 400px;">
                                        <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px">
                                    </div>
                                </div>
                                <div class="text"></div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
            <!--网站统计 end-->

            <!--数据类型 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr4">
                        <h2>数据类型</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="row-fluid">
                                <div class=" mwb5 float_l">
                                    <div class="mwbBorder">
                                        <h2><div style="float:left;width:20%;">情感分析</div>
                                            <!--
                                           <span class="rightbtn rightbtn2">
                                               <button style="margin-top:-8px;"onclick="javascript:return false;" id="notifyModal_ex1"  class="hintModal"><img style="width:20px" src="${njxBasePath}/images/querybtn.png"><div class="hintModal_container">时间段内敏感和非敏感信息的占比</div></button>
                                           </span>--></h2>
                                        <div class="mwbcon">
                                            <div id="container2" class="echartAdpter" style="height: 300px;">
                                                <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class=" mwb5 float_r">
                                    <div class="mwbBorder">
                                        <h2>境内外分布</h2>
                                        <div class="mwbcon">
                                            <div id="container3"  class="echartAdpter"  style="height: 300px;">
                                                <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class=" mwb5 float_l">
                                    <div class="mwbBorder">
                                        <h2>媒体来源占比</h2>
                                        <div class="mwbcon mwbcon2">
                                            <div id="container4">
                                                <ul class="circularChart">
                                                    <li class="c1">
                                                        <p>各类型媒体报道</p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c2">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c3">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c4">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c5">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c6">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c7">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c8">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c9">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c10">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c11">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c12">
                                                        <p></p>
                                                        <p></p>
                                                    </li>

                                                </ul>

                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class=" mwb5 float_r">
                                    <div class="mwbBorder">
                                        <h2><div style="float:left;width:25%;">媒体活跃度</div>
                                            <!--
                                            <span class="rightbtn rightbtn2">
                                                <button style="margin-top:-8px;"onclick="javascript:return false;" id="notifyModal_ex1"  class="hintModal"><img style="width:20px" src="${njxBasePath}/images/querybtn.png"><div class="hintModal_container">时间段内发布量最大的前十家媒体</div></button>
                                            </span>--></h2>
                                        <div class="mwbcon mwbcon2">
                                            <div id="container5" class="echartAdpter"  style="height: 410px;">
                                                <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="mwbBorder">
                                    <h2>地域分布图</h2>
                                    <div class="mwbcon rel">
                                        <div id="container6" class="echartAdpter"  style="width: 550px;height: 468px;float: left;">
                                            <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px">
                                        </div>
                                        <div class="info-section">
                                            <table id="scrollTab" border="" cellspacing="" cellpadding="" class="map_table map_table2">
                                                <thead>
                                                <tr>
                                                    <th width="48%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;省份</th>
                                                    <th width="">信息数</th>
                                                </tr>
                                                </thead>
                                                <tbody id="c6_tb">

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--数据类型 end-->
            <!--关键词云 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr5">
                        <h2>关键词云</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder">
                                <div class="mwbcon rel">
                                    <div id="container7" class="echartAdpter"  style="height: 410px;">
                                        <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <!--关键词云 end-->
            <!--热门信息 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr6">
                        <h2>热门信息</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <table border="0" cellspacing="0" cellpadding="0" class="table table2 float_l">
                                <thead>
                                <tr>
                                    <th width="">标题</th>
                                    <th width="20%">来源站点</th>
                                    <th width="10%" id="hotTime">时间</th>
                                    <th width="15%">转载数</th>
                                </tr>
                                </thead>
                                <tbody id="hotNews">
                                <tr>
                                    <td><img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;"> </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>
            <!--热门信息 end-->
            <!--热点网民 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr7">
                        <h2>热点网民</h2>
                    </div>
                </div>
                <div class="">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder mwbBorder2">
                                <div class="yq_con yqtabs tabsBorder">
                                    <ul class="tab_menu clear">
                                        <li class="current">全部</li>
                                        <li>微博</li>
                                        <li>论坛</li>
                                        <li>博客</li>
                                    </ul>
                                    <div class="tab_box" id="hotPeople">
                                        <div>
                                            <td><img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;"> </td>
                                        </div>
                                        <div class="hide">

                                        </div>
                                        <div class="hide">

                                        </div>
                                        <div class="hide">

                                        </div>
                                    </div>
                                    <div id="peopleTxt" class="text2" style="margin:10px;margin-top: 20px;text-indent:0px;">
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>

            </div>
            <!--热点网民 end-->
            <!--传播途径 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr8">
                        <h2>传播途径</h2>
                        <!--
                        <span class="rightbtn rightbtn2">
                            <button onclick="javascript:return false;" style="margin-top:-10px;"id="notifyModal_ex1"  class="hintModal"><img src="${njxBasePath}/images/querybtn.png"><div class="hintModal_container">时间段内非微博信息的传播轨迹图</div></button>
                        </span>-->
                    </div>
                </div>

                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder">
                                <div class="mwbcon rel">
                                    <div id="container8" class="echartAdpter"  style="height: 600px;">
                                        <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                    </div>
                                </div>

                                <div id="pathTxt" style="background-color:#FFF;margin:10px;"class="text">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--传播途径 end-->
            <!--相关词 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr9">
                        <h2>相关词</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder">
                                <div class="mwbcon rel">
                                    <div id="container9" class="echartAdpter"  style="height: 410px;">
                                        <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--相关词 end-->
            <!--网民观点 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr10">
                        <h2>网民观点</h2>

                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="row-fluid">
                                <div class="mwbBorder">
                                    <h2>新闻观点分析</h2>
                                    <div class="mwbcon">
                                        <div id="container10" class="echartAdpter"  style="height: 410px;">
                                            <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                        </div>
                                        <div class="viewpoint" id="container10List">

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="mwbBorder">
                                    <h2>论坛观点分析</h2>
                                    <div class="mwbcon">
                                        <div id="container11" class="echartAdpter"  style="height: 410px;">
                                            <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                        </div>
                                        <div class="viewpoint" id="container11List">

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="mwbBorder">
                                    <h2>微博观点分析</h2>
                                    <div class="mwbcon">
                                        <div id="container12" class="echartAdpter"  style="height: 600px;">
                                            <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;">
                                        </div>
                                        <div class="viewpoint" id="container12List">

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <!--网民观点 end-->
            <!--热点总结 start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr11">
                        <h2>热点总结</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon" id="summary" style="margin:10px;">
                            <img src="${njxBasePath}/images/loading_c.gif" style="width: 15px;height: 15px;">
                        </div>
                    </div>
                </div>

            </div>
            <!--热点总结 end-->

        </div>

    </div>

    <s:if test = 'isApp!=1'>
        <div class="clear" style="height: 35px;"></div>
        <section id = "appdownload" class="section" style="margin-bottom: 0px;bottom:0px;left:0;z-index: 50;width: 100%;position: fixed;background-color: rgba(0,0,0,0.6);">
            <div style="height:45px;">
                <div style="position: absolute;top:12px;left:10px;height:100%;right:0%;color:#FFF;font-size:16px;"></div>
                <div class = "subBtn" style="background-color:#fd9237;border-radius:5px;position: absolute;top:8px;font-size:14px;text-align:center;line-height:30px;width: 60%;height:30px;right:20%;color:#FFF;cursor:pointer;" onClick="goSubscribe();">我也要分析</div>
            </div>
        </section>
    </s:if>

</div>
</div>
<!--分析完毕 end-->
<script type="text/javascript">saveOperateLog('事件分析查看','1013');</script>
<script src="${staticResourcePathH5}/js/jquery.nav2.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/jquery.tabs.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/popModal.js?v=${SYSTEM_INIT_TIME}"></script>
<script>
    $(function(){
        $('#nav2').onePageNav();
        $('.yqtabs').Tabs({
            event:'click'
        });
        //显示/隐藏编辑框
        /* $(".row-fluid .textShow .tool.toolhover #edit").on("click",function(){
             var $this = $(this);
             if($this.parents(".row-fluid").hasClass("hover")){
                 $this.parents(".row-fluid").removeClass("hover")
             } else{
                 $(".row-fluid").removeClass("hover");
                 $this.parents(".row-fluid").addClass("hover");
             }
         });*/

        //事件走势显示/隐藏编辑框
        /* $(".row-fluid .yb .tool.toolhover #edit").on("click",function(){
             var $this = $(this);
             if($this.parents(".row-fluid").hasClass("hover")){
                 $this.parents(".row-fluid").removeClass("hover")
             } else{
                 $(".row-fluid").removeClass("hover");
                 $this.parents(".row-fluid").addClass("hover");
             }
         });*/



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
</script>
<script type="text/javascript">
    function sendPost(param,url,callback){
        $.ajax({
            url : url,
            type : "post",
            data : param,
            success : function(data){
                callback(data);
            },
            error:function(jqXHR, textStatus, errorThrown){
                /*弹出jqXHR对象的信息*/
                //alert(jqXHR.responseText);
                //alert(jqXHR.status);
                //alert(jqXHR.readyState);
                //alert(jqXHR.statusText);
                /*弹出其他两个参数的信息*/
                //alert(textStatus);
                //alert(errorThrown);
                sendPost(param,url,callback);
            }
        })
    };
    function goSubscribe(){
        if("${admin!}"){
            location.href = actionBase+"/createAnalysis?createType=1";
        }else{
            location.href = actionBase+"/analysis";
        }
    }



    $(function(){
        var params = {"tickets":'${tickets!}',"reportTime":'${reportTime}'};
        sendPost(params,"${njxBasePath}/eventProfile",profileCallback);
        sendPost(params,"${njxBasePath}/sitesStatisticsV2",LineCallBack);
        sendPost(params,"${njxBasePath}/dataType",QGPieAdapter);
        sendPost(params,"${njxBasePath}/mediaMap",mediaCallback);//媒体来源占比
        sendPost(params,"${njxBasePath}/wordCloud",cloudCallback);
        sendPost(params,"${njxBasePath}/hotNews",hotNewsCallback);
        sendPost(params,"${njxBasePath}/propagationPath",pathCallback);
        sendPost(params,"${njxBasePath}/relatedWord",relatedWordCallback);
        sendPost(params,"${njxBasePath}/typicalViews",typicalPieAdapter);//微博观点分析
        sendPost(params,"${njxBasePath}/hotPeople",hotPeopleCallback);
    });
</script>
<#include "../../buttom.ftl" >
</body>
</html>