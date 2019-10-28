<#--<%@page import="org.apache.commons.lang3.StringUtils" %>-->
<#--<%@ page language="java" pageEncoding="gbk" %>-->
<#--<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>-->
<#--<%@ taglib prefix="s" uri="/struts-tags" %>-->
<#--<c:set var="ctx" value="${pageContext.request.contextPath}"/>-->
<#--<#assign staticResourcePath = request.contextPath />-->

<html>
<head>
    <#include "../common/top.ftl"/>
    <script type="text/javascript"
            src="${staticResourcePath}/js/common/common-checkKeyword.js?v=${sysConfig}"></script>
    <script type="text/javascript">
        if (window.parent.length > 0)
            window.parent.location = location;
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
            }(),
            language: (navigator.browserLanguage || navigator.language).toLowerCase()
        };
        if (bs.versions.mobile) {
            if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.windowsPhone) {
                window.location = "http://h5.51wyq.cn";
            }
        }
    </script>
    <script type="text/javascript">
        vartop = 0;//swiper.min.js需要，缺失会报错
        var ctx = '${staticResourcePath}';
        var basePath = '<%=njxBasePath%>';
        var mobile_filter = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
        var password_filter = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\,\~]{6,20}$/;
        <#--//var baseAction = '${ctx}';-->
        $(function () {
            if ($('#kickLogout').val() == 'true')
                alert('你的账号于' + $('#kickTime').val() + '在另一台电脑登录。如非本人操作，则密码可能已泄露，建议前往www.wrd.cn微热点(微舆情)用户中心修改密码。');

            // 实名认证
            var needRealName = '<%=request.getParameter("needRealName") %>';
            if (needRealName == 'true') {
                var nickname=${nickname};
                    // nickname = request.getParameter("nickname");
                    if (StringUtils.isNotBlank(nickname))
                        nickname = new String(nickname.getBytes("iso-8859-1"), "gbk");
                $('#phoneVerificationModal2 #nickname').text('<%=nickname %>');
                $('#phoneVerificationModal2 #userHead').attr('src', '<%=request.getParameter("userHead") %>');
                $('#phoneVerificationModal2 #userId').val('<%=request.getParameter("userId") %>');
                $("#phoneVerificationModal2").modal({show: true});
            }

            // 短信验证
            var needLoginCount = '<%=request.getParameter("needLoginCount") %>';
            if (needLoginCount == 'true') {
            <#--var request=${request};-->
                var nickname=${nickname};
                // nickname = request.getParameter("nickname");
                if (StringUtils.isNotBlank(nickname))
                    nickname = new String(nickname.getBytes("iso-8859-1"), "gbk");

                $('#phoneVerificationModal1 #nickname1').text('<%=nickname %>');
                $('#phoneVerificationModal1 #userHead1').attr('src', '<%=request.getParameter("userHead") %>');
                var loginCountMobile = '<%=request.getParameter("mobile") %>';
                $('span[name="mobilenumber1"]').text(loginCountMobile.substring(0, 3) + '****' + loginCountMobile.substring(7));
                $("#phoneVerificationModal1").modal({show: true});
            }

            /**
             * 获取验证码
             */
            $("#getValidation").click(function () {
                var username = $.trim($("#phoneVerificationModal2 #username").val());
                var password = $.trim($("#phoneVerificationModal2 #password").val());
                if (username != null && username != '' && password != null && password != '' && mobile_filter.test(username) && password_filter.test(password)) {
                    $.ajax({
                        type: "post",
                        url: ctx+"/user/checkMobile",
                        data: {
                            'user.mobile': username
                        },
                        cache: false,
                        success: function (data1) {
                            if (data1.replace(/\r\n/g, '') == 1) {
                                $("#messageValidation").text("手机号码已存在！");
                                $("#messageValidation").css("display", "block");
                            } else {
                                $.ajax({
                                    url: ctx+"/view/user/sendVcodeSMS.action",
                                    type: 'post',
                                    data: {'mobile': username},
                                    success: function (data) {
                                        if (!$.isEmptyObject(data)) {
                                            $("#messageValidation").css("display", "block");
                                            if (data.succ) {
                                                realNameTimer(120);
                                                $("#messageValidation").text('验证短信已发送，请输入验证码，完成绑定！');
                                            }
                                            if (!data.succ)
                                                $("#messageValidation").text(data.msg);
                                        }
                                    }
                                })
                            }
                        }
                    });
                }
            });

            $('#getValidation1').click(function () {
                var loginCountMobile = '<%=request.getParameter("mobile") %>';
                if (loginCountMobile != null && loginCountMobile != '') {
                    $.ajax({
                        url: ctx+"/view/user/sendVcodeSMS.action",
                        type: 'post',
                        data: {'mobile': loginCountMobile},
                        success: function (data) {
                            if (!$.isEmptyObject(data)) {
                                $("#messageValidation1").css("display", "block");
                                if (data.succ) {
                                    realNameTimer(120);
                                    $("#messageValidation1").text('验证短信已发送，请输入验证码，完成绑定！');
                                }
                                if (!data.succ)
                                    $("#messageValidation1").text(data.msg);
                            }
                        }
                    })
                }
            });

            record(1, null);

            $('#zc').click(function () {
                record(2, ctx+'/user/goRegister.shtml');
            });

            $('#cpjs').click(function () {
                record(3, ctx+'/product.shtml');
            });

            $('#pnrm').click(function () {
                record(4, ctx+'/novice.shtml');
            });

            $('#bzzx').click(function () {
                record(5, ctx+'/help.shtml');
            });

            $('#khd').click(function () {
                record(6, ctx+'/downLoad.shtml');
            });

            $('#loginBtn').click(function () {
                record(7, null);
            });

            $('#qqLoginBtn').click(function () {
                record(8, null);
            });

            $('#wxLoginBtn').click(function () {
                record(9, null);
            });

            $('#wbLoginBtn').click(function () {
                record(10, null);
            });
        });

        function record(type, url) {
            $.ajax({
                url: ctx+'/tuiguang/tg/record',
                type: 'POST',
                data: {
                    'from': 2,
                    'type': type
                },
                success: function (result) {
                    if (result) {
                        if (url)
                            location.href = url;
                    }
                }
            });
        }

        function realNameTimer(intDiff) {
            if (intDiff == 0) {
                $('button[name="getValidation"]').html('获取验证码');
                $('button[name="getValidation"]').removeAttr("disabled");
            } else {
                $('button[name="getValidation"]').html(intDiff + '秒');
                $('button[name="getValidation"]').attr("disabled", "true");
                intDiff--;
                setTimeout(function () {
                    realNameTimer(intDiff);
                }, 1000);
            }
        }

        function realNameJump(count) {
            window.setTimeout(function () {
                count--;
                if (count > 0) {
                    $("#localmiao").text(count);
                    realNameJump(count);
                } else {
                    $("#VerificationModal2").modal('hide');
                    var platformType = '<%=request.getParameter("platformType") %>';
                    if (platformType == 1) {
                        location.href = ctx+'/thirdParty/toSinaWeiboLogin.action';
                    } else if (platformType == 2) {
                        location.href = ctx+'/thirdParty/toWeixinLogin.action';
                    } else if (platformType == 3) {
                        location.href = ctx+'/thirdParty/toTencentLogin.action';
                    }
                }
            }, 1000);
        }

        /**
         * 立即验证
         */
        function realNameCheck() {
            var username = $.trim($("#phoneVerificationModal2 #username").val());
            var password = $.trim($("#phoneVerificationModal2 #password").val());
            var vcode = $.trim($("#phoneVerificationModal2 #pCode").val());
            if (vcode != null && vcode != '' && username != null && username != '' && password != null && password != '' && mobile_filter.test(username) && password_filter.test(password)) {
                $.ajax({
                    url: ctx+"/view/user/checkSMS.action",
                    type: "post",
                    data: {"authcode": vcode, 'mobile': username},
                    success: function (data) {
                        if (data) {
                            $("#phoneVerificationModal2").modal('hide');
                            $("#VerificationModal2").modal({show: true});
                            $("#mobilenumber").text(username.substring(0, 3) + '****' + username.substring(7));

                            $.ajax({
                                url: ctx+'/view/user/realName.action',
                                type: 'post',
                                data: {
                                    'admin.userId': $('#phoneVerificationModal2 #userId').val(),
                                    'admin.mobile': username,
                                    'admin.password': password
                                },
                                success: function () {
                                }
                            });

                            realNameJump(4);
                        } else {
                            $("#messageValidation").text("验证失败！");
                            $("#messageValidation").css("display", "block");
                        }
                    }
                })
            }
        }

        function realLoginCount() {
            var loginCountMobile = '<%=request.getParameter("mobile") %>';
            var vcode = $.trim($("#phoneVerificationModal1 #pCode1").val());
            if (vcode != null && vcode != '' && loginCountMobile != null && loginCountMobile != '') {
                $.ajax({
                    url: ctx+"/view/user/checkSMS.action",
                    type: "post",
                    data: {"authcode": vcode, 'mobile': loginCountMobile},
                    success: function (data) {
                        if (data) {
                            $("#phoneVerificationModal1").modal('hide');
                            $("#VerificationModal2").modal({show: true});
                            $("#mobilenumber").text(loginCountMobile.substring(0, 3) + '****' + loginCountMobile.substring(7));

                            realNameJump(4);
                        } else {
                            $("#messageValidation1").text("验证失败！");
                            $("#messageValidation1").css("display", "block");
                        }
                    }
                })
            }
        }
    </script>
    <#--引入新版head信息=========================================================start-->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="author" content="">
    <meta name="keywords" content="微热点,微信舆情监测,微博舆情监控,舆情监测, 舆情APP ,舆情客户端，舆情软件,互联网舆情,视频舆情,海外媒体，舆情APP ,舆情客户端，舆情预警">
    <meta name="description"
          content="微热点是中国最大的微博舆情舆论服务平台，提供网页、微博、微信等全媒体舆情信息网以及网络舆情,舆情负面事件分析,舆情管理,军犬舆情, 舆情报告,舆情监控, 舆情监测软件,海外媒体监测,竞争情报服务等。">
    <meta name="searchtitle" content="微热点,舆情监控,舆情监测,微博舆情监控,舆情监控软件,舆情监测系统,互联网舆情监测,舆情监控系统,互联网舆情监控,舆情监测软件,网络舆情监控系统">
    <title>微热点(微舆情)官方网站(wrd.cn)-媒体传播大数据应用平台|热度指数|传播分析|口碑分析|微博情绪</title>
    <link rel="stylesheet" href="${staticResourcePath}/css/indexV4/base/css/common.css">
    <link rel="stylesheet" href="${staticResourcePath}/css/indexV4/base/css/style.css"/>
    <link rel="stylesheet" href="${staticResourcePath}/css/indexV4/base/css/font-icon.css"/>
    <link rel="stylesheet" href="${staticResourcePath}/css/indexV4/global/vendor/particles/particles.min.css">
    <link rel="stylesheet" href="${staticResourcePath}/css/indexV4/global/css/w-home.css">
    <link rel="stylesheet" href="${staticResourcePath}/css/indexV4/base/js/tool/tool.min.css">
    <link rel="stylesheet" href="${staticResourcePath}/css/indexV4/global/css/activity.css">
    <link rel="stylesheet" href="${staticResourcePath}/css/indexV4/global/css/fontIcon.css">
    <link rel="stylesheet" href="${staticResourcePath}/css/newHome.css">
    <style>
        #mapMin, #dataMap {
            width: 100%;
            height: 690px;
        }
        [v-cloak]
        {
            display: none;
        }
    </style>
    <#--引入新版head信息end================================================================-->

</head>
<body>
<#--头部导航 start-->
<#include "../common/tips.ftl"/>
<#--<#assign isShowTop="1"/>-->
<#--<#assign currentPage="hot"/>-->
<#include "../common/navigate.ftl"/>

<#--头部导航 end-->

<#--底部-->
<input id = "loginsign" type="hidden" value="${login}">
<#if admin1??>
    <!--搜索-->
    <div class="w-container min-layout1200" id="home">
        <div class="w-container-top">
            <div id="particles-js" class="count-particles"></div>
            <div class="mainBox">
                <div class="main-title">
                    <h2>
                        <img src="${staticResourcePath}/css/indexV4/base/images/logo.png" class="v_a_m margin-right-5"
                             alt=""><font class="v_a_m">网络传播热度指数</font>
                    </h2>
                </div>
                <div class="main-search clearfix">
                    <div class="inline-block rel">
                        <div class="pull-left rel sizeCount">
                            <input type="password" style="display: none">
                            <input type="text" class="search-input" style="background-color: #12174D"
                                   placeholder="请输入人名、地名、机构、公司或事件关键词" id="search-keyword" autocomplete="off"/>
                            <#--<div class="count">-->
                            <#--<span class="sizeNum"></span>-->
                            <#--</div>-->
                        </div>
                        <div class="main-search-btn pull-left">
                            <span onclick="goOpenTools();">查询</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main-tabs">
                <div class="w-layout clearfix">
                    <ul class="row">
                        <li :class="{active:activeTab == 1}" class="col-md-4" @click='goTab(1)'>
                            <a href="javascript:void(0)">热门事件</a><span class="color_5 font-size-16 icon-explain" data-target="#explainModal" data-toggle="modal"><i class="icon">&#xe694;</i></span>
                        </li>
                        <li :class="{active:activeTab == 2}" class="col-md-4"
                        <#if admin1??>
                            @click='goTab(2)'
                        <#else>
                            id="login" @click="goto(35)"
                        </#if>
                        >
                        <a href="javascript:void(0)">重大事件</a>
                        </li>
                        <!--   <li :class="{active:activeTab == 3}">
                              <a href="javascript:void(0)" @click='goTab(3)'>热门人物</a>
                          </li> -->

                        <li :class="{active:activeTab == 4}" class="col-md-4"
                        <#if admin1??>
                            @click='goTab(4)'
                        <#else >
                            id="login" @click="goto(36)"
                        </#if>
                        >
                        <a href="javascript:void(0)">热门微博</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="activeTab1" class="w-container-body">
            <div class="data-content active"></div>
        </div>
        <div id="activeTab2" class="w-container-body">
            <div class="data-content active"></div>
        </div>
        <!-- <div id="activeTab3" class="w-container-body"></div> -->
        <div id="activeTab4" class="w-container-body">
            <div class="data-content active"></div>
        </div>
    </div>

<#else>
    <!--搜索-->
    <div class="w-container min-layout1200" id="home">
        <div class="w-container-top">
            <div id="particles-js" class="count-particles"></div>
            <div class="mainBox">
                <div class="main-title">
                    <h2>
                        <img src="${staticResourcePath}/css/indexV4/base/images/logo.png" class="v_a_m margin-right-5"
                             alt=""><font class="v_a_m">网络传播热度指数</font>
                    </h2>
                </div>
                <div class="main-search clearfix">
                    <div class="inline-block rel" @focusout="miss()">
                        <div class="pull-left rel sizeCount">
                            <input type="password" style="display: none">
                            <input type="text" class="search-input" style="background-color: #12174D"
                                   @mousedown="searchHistory()"
                                   oninput="thinkKeywords(this,0)"
                                   placeholder="请输入人名、地名、机构、公司或事件关键词" id="search-keyword" autocomplete="off"/>
                        </div>
                        <div class="main-search-btn pull-left">
                            <span onclick="goOpenTools();">查询</span>
                        </div>
                        <ul id="hotwords" class="hotwords" v-if="show" v-cloak>
                            <li class="hwtitle"><span style="display:block; float:left;">最近搜索</span> <span class="scjl" @click="del()">删除搜索记录</span></li>
                            <li v-for="k in keyWordLogs" @click="gokw(k)"><a href='javascript:void(0)'><h1 v-text="k"></h1></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="main-tabs">
                <div class="w-layout clearfix">
                    <ul class="row">
                        <li :class="{active:activeTab == 1}" class="col-md-4" @click='goTab(1)'>
                            <a href="javascript:void(0)">热门事件</a><span class="color_5 font-size-16 icon-explain" data-target="#explainModal" data-toggle="modal"><i class="icon">&#xe694;</i></span>
                        </li>
                        <li :class="{active:activeTab == 2}" class="col-md-4"
                        <#if admin1??>
                            @click='goTab(2)'
                        <#else>
                            id="login" @click="goto(35)"
                        </#if>
                        >
                        <a href="javascript:void(0)">重大事件</a>
                        </li>
                        <!--   <li :class="{active:activeTab == 3}">
                              <a href="javascript:void(0)" @click='goTab(3)'>热门人物</a>
                          </li> -->

                        <li :class="{active:activeTab == 4}" class="col-md-4"
                        <#if admin1??>
                            @click='goTab(4)'
                        <#else>
                            id="login" @click="goto(36)"
                        </#if>
                        >
                        <a href="javascript:void(0)">热门微博</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="activeTab1" class="w-container-body">
            <div class="data-content active"></div>
        </div>
        <div id="activeTab2" class="w-container-body">
            <div class="data-content active"></div>
        </div>
        <!-- <div id="activeTab3" class="w-container-body"></div> -->
        <div id="activeTab4" class="w-container-body">
            <div class="data-content active"></div>
        </div>
    </div>

</#if>
<#include "../common/bottoms.ftl"/>

<input type="hidden" id="kickLogout" value='<s:property value="kickLogout" />'/>
<input type="hidden" id="kickTime" value='<s:property value="kickTime" />'/>
<input type="hidden" value="${url}" id="viewUri" name="viewUri"/>

<form id="searchForm" action="" method="post" target="_blank">
    <input type="hidden" name="title" id="title" value="">
    <input type="hidden" name="keyword" id="keyword" value="">
    <input type="hidden" name="filterKeyword" id="filterKeyword" value="">
    <input type="hidden" name="categoryId" id="categoryId" value="">
    <input type="hidden" name="categoryType" id="categoryType" value="">
    <input type="hidden" name="secondCategory" id="secondCategory" value=""/>
    <input type="hidden" name="num" id="num" value="0"/>
    <input type="hidden" name="categoryLevel" id="categoryLevel" value="0"/>
    <input type="hidden" name="date" id="date" value="24"/>
    <input type="hidden" id="isAll" name="isAll" value="0">
    <input type="hidden" name="secondClassifyName" id="secondClassifyName" value=""/>
    <input type="hidden" name="threeClassifyName" id="threeClassifyName" value=""/>
</form>
<!--活动-->
<div class="activityDis" style="display: none">
    <a class="activity-img" data-target="#example2" data-toggle="modal">
        <img src="${staticResourcePath}/css/indexV4/base/images/activity_dis_img.png" alt="">
    </a>
    <span class="activity-close"><i class="icon icon-guanbi"></i></span>
</div>
<!--活动弹框-->
<div class="modal-default modal-circle fade Discount" id="example2" tabindex="-1" role="dialog" aria-labelledby="example1">
    <div class="modal-dialog" role="document" style="width: 723px;height: 464px">
        <div class="modal-content">
            <a href="javascript:;" class="default-close" data-dismiss="modal" aria-label="Close">×</a>
            <div class="modal-body">
                <div>
                    <img class="activity_dis_title" src="${staticResourcePath}/css/indexV4/base/images/activity_dis_title.png" alt="">
                    <div id="countdownTime" class="activity-time iconFamily">

                    </div>
                </div>

                <div style="padding-top: 100px">
                    <ul class="w-tabs-2 clearfix padding-left-40">
                        <li class="active">
                            <a href="#dispage1" data-toggle="tab">基础优惠卷</a>
                        </li>
                        <li>
                            <a href="#dispage2" data-toggle="tab">热度指数折扣券</a>
                        </li>
                        <li>
                            <a href="#dispage3" data-toggle="tab">新闻稿折扣券</a>
                        </li>
                        <li>
                            <a href="#dispage4" data-toggle="tab">信息监测折扣券</a>
                        </li>
                    </ul>
                    <div class="tab-content padding-vertical-15 padding-horizontal-40">
                        <div id="dispage1" class="tab-pane fade active in">
                            <div class="padding-vertical-50">
                                <div class="coupon block">
                                    <p class="coupon-title">微积分抵用券</p>
                                    <p>无最低消费限制</p>
                                    <p>领取后7日内有效</p>
                                    <div class="coupon-price">
                                        <span class="coupon-num">500</span>
                                        <a href="" class="coupon-btn">立即领取</a>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">暂不领取</a>
                            </div>
                        </div>
                        <div id="dispage2" class="tab-pane fade">
                            <div class="row clearfix">
                                <div class="col-w-5">
                                    <div class="coupon block">
                                        <p class="coupon-title">微积分抵用券</p>
                                        <p>满900微积分可用</p>
                                        <p>领取后30日内有效</p>
                                        <div class="coupon-price">
                                            <span class="coupon-num">300</span>
                                            <a href="javascript:void(0);" class="coupon-btn">立即领取</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-w-5">
                                    <div class="coupon block">
                                        <p class="coupon-title">微积分抵用券</p>
                                        <p>满5000微积分可用</p>
                                        <p>领取后30日内有效</p>
                                        <div class="coupon-price">
                                            <span class="coupon-num">2000</span>
                                            <a href="javascript:void(0);" class="coupon-btn">立即领取</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-w-5">
                                    <div class="coupon block">
                                        <p class="coupon-title">微积分抵用券</p>
                                        <p>满14000微积分可用</p>
                                        <p>领取后30日内有效</p>
                                        <div class="coupon-price">
                                            <span class="coupon-num">6000</span>
                                            <a href="javascript:void(0);" class="coupon-btn">立即领取</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-w-5">
                                    <div class="coupon block">
                                        <p class="coupon-title">微积分抵用券</p>
                                        <p>满25000微积分可用</p>
                                        <p>领取后30日内有效</p>
                                        <div class="coupon-price">
                                            <span class="coupon-num">9000</span>
                                            <a href="javascript:void(0);" class="coupon-btn">立即领取</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">暂不领取</a>
                            </div>
                        </div>
                        <div id="dispage3" class="tab-pane fade">
                            <div class="row clearfix">
                                <div class="col-w-5">
                                    <div class="coupon block">
                                        <p class="coupon-title">免费体验券</p>
                                        <p>三次免费分析机会</p>
                                        <p>领取后3日内有效</p>
                                        <div class="coupon-price">
                                            <span class="coupon-num">3次</span>
                                            <a href="javascript:void(0);" class="coupon-btn-red">去使用</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-w-5">
                                    <div class="coupon block">
                                        <p class="coupon-title">打包券</p>
                                        <p>2000微积分</p>
                                        <p>领取后30日内有效</p>
                                        <div class="coupon-price">
                                            <span class="coupon-num">1次</span>
                                            <a href="javascript:void(0);" class="coupon-btn">立即领取</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-w-5">
                                    <div class="coupon block">
                                        <p class="coupon-title">打包券</p>
                                        <p>10000微积分</p>
                                        <p>领取后永久有效</p>
                                        <div class="coupon-price">
                                            <span class="coupon-num">3次</span>
                                            <a href="javascript:void(0);" class="coupon-btn">立即领取</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-w-5">
                                    <div class="coupon block">
                                        <p class="coupon-title">打包券</p>
                                        <p>10000微积分</p>
                                        <p>领取后永久有效</p>
                                        <div class="coupon-price">
                                            <span class="coupon-num">5次</span>
                                            <a href="javascript:void(0);" class="coupon-btn">立即领取</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">暂不领取</a>
                            </div>
                        </div>
                        <div id="dispage4" class="tab-pane fade">
                            <div class="padding-vertical-50">
                                <div class="coupon block">
                                    <p class="coupon-title">微积分抵用券</p>
                                    <p>领取后30日内有效</p>
                                    <div class="coupon-price">
                                        <span class="coupon-num">10000</span>
                                        <a href="" class="coupon-btn">立即领取</a>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">暂不领取</a>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!--手机验证弹框 start-->
<div id="phoneVerificationModal2" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 500px; margin-left: -250px;">
    <input type="hidden" id="userId" />
    <div class="modal-header border-n rel" style="z-index: 1;">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    </div>
    <div class="modal-body" style="overflow-y:  initial;">
        <div class="align_c">
            <img id="userHead" class="img-circle ml20 mb10" width="80px" height="80px"/>
            <h1 class="fz20">亲爱的 <span class="fc_orange" id="nickname"></h1>
            <h2 style="text-align: left;margin-left: 20px;"><br /><font color="red">* 为了您的个人账号安全，请先进行手机验证！</font></h2>
        </div>
        <!--login登录框代码 start-->
        <div class="login">
            <div class="phoneLogin">
                <div class="input phone wy_dl rel">
                    <input style="border: 0;" class="inputText" type="text" id="username" name="username" autocomplete="off" placeholder="手机号" value="" >
                </div>

                <div class="input password wy_pass rel">
                    <input style="border: 0;" class="inputText" id="password" type="password" onfocus="this.type=;&#39;password&;#39;" autocomplete="off" placeholder="输入密码">
                </div>
                <div>
                    <div class="float_l code input rel" style="width:220px;">
                        <input id="pCode" name="imgVcode"  class="inputYZM" type="text" placeholder="请输入验证码" >

                    </div>
                    <div class="float_l imgVcode" style="width:200px;margin-top: 10px;margin-left: 10px;">
                        <button class="btn btn-default btn-big btn-block" style="padding: 16px 40px;" id="getValidation" name="getValidation">获取验证码</button>
                    </div>
                    <div  class="clear"></div>
                </div>


                <div class="error mt10 mb10" id="messageValidation" style="display: none;">
                </div>

                <div class="login_btn" onclick="realNameCheck()">立即验证</div>
            </div>
        </div>
        <!--login登录框代码 end-->
    </div>

</div>
<!--手机验证弹框 end-->
<!--验证通过弹框 start-->
<div id="VerificationModal2" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none; width: 500px; margin-left: -250px;">
    <div class="modal-header border-n rel" style="z-index: 1;">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    </div>
    <div class="modal-body" style="overflow-y:  initial;">
        <div class="align_c fc_orange mt35 mb35">
            <h1 class="fz20" style="padding-left: 20px;">亲爱的微友，恭喜您！</h1>
            <h2 class="fz18 fc_dark_grey">已成功完成手机号码【<span id="mobilenumber"></span>】的验证！</h2>
        </div>
        <div class="align_c mb35">
            <button class="btn btn-warning btn-big"><span id="localmiao">3</span>秒后跳转</button>
        </div>
    </div>
</div>
<!--验证通过弹框 end-->
<!--温馨提示-->
<div id="tipsModal" class="modal-default modal-blue fade" role="dialog" tabindex="-1" aria-labelledby="tipsModal"
     aria-hidden="true">
    <div class="modal-dialog" role="document" style="width: 380px;margin-top: 270px">
        <div class="modal-content modal-border-top">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 class="modal-title">提示</h3>
            </div>
            <div class="modal-body text-center">
                <p class="fz16 p_v_20">很抱歉，目前该地域暂无数据</p>
                <div class="p_v_10">
                    <a href="javascript:void(0);" class="btn btn-big w-btn-blue300 " data-dismiss="modal" aria-hidden="true">我知道了</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!--收费提示-->
<div id="moneyTip" class="modal-default modal-blue fade" role="dialog" tabindex="-1" aria-labelledby="tipsModal"
     aria-hidden="true">
    <div class="modal-dialog" role="document" style="width: 380px;margin-top: 270px">
        <div class="modal-content modal-border-top">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 class="modal-title">提示</h3>
            </div>
            <div class="modal-body text-center">
                <p class="fz16 p_v_20">您好，双向筛选属于定制服务，请联系客服进行地域+热点类型的定制服务。</p>
                <div class="p_v_10">
                    <div class="btn btn-big w-btn-blue300" data-dismiss="modal" onclick="window.open('http://wpa.qq.com/msgrd?v=3&uin=3002432217&site=qq&menu=yes','_blank')">联系客服</div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--手机验证弹框 start-->
<div id="phoneVerificationModal1" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none; width: 500px; margin-left: -250px;">
    <div class="modal-header border-n rel" style="z-index: 1;">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    </div>
    <div class="modal-body" style="overflow-y:  initial;">
        <div class="align_c">
            <img id="userHead1" class="img-circle ml20 mb10" width="80px" height="80px"/>
            <h1 class="fz20">亲爱的 <span class="fc_orange" id="nickname1"></h1>
            <h2 style="text-align: left;margin-left: 20px;"><br/><font color="red">* 为了您的个人账号安全，请先对手机号码 <span
                    name="mobilenumber1"></span> 进行短信验证！</font></h2>
        </div>
        <!--login登录框代码 start-->
        <div class="login">
            <div class="phoneLogin">
                <div>
                    <div class="float_l code input rel" style="width: 277px;">
                        <input id="pCode1" name="imgVcode" class="inputYZM" type="text" placeholder="请输入验证码">
                    </div>
                    <div class="float_l imgVcode" style="width: 140px;">
                        <button class="btn btn-default btn-big btn-block" style="padding: 16px 40px;"
                                id="getValidation1" name="getValidation">获取验证码
                        </button>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="error mt10 mb10" id="messageValidation1" style="display: none;"></div>
                <div class="login_btn" onclick="realLoginCount()">立即验证</div>
            </div>
        </div>
        <!--login登录框代码 end-->
    </div>

</div>
<!--手机验证弹框 end-->
<!--功能说明弹框-->
<div id="explainModal" class="modal-default fade"  tabindex="-1" role="dialog" aria-labelledby="explainModal">
    <div class="modal-dialog" role="document" style="width: 723px;height: 464px">
        <div class="modal-content">
            <div class="modal-header modal-border-top">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title font-size-18" id="myModalLabel">热门事件功能介绍</h4>
            </div>
            <div class="modal-body padding-30">
                <p class="font-size-16"><span class="font-size-18 color_1 font-weight-600">热点事件</span><span class="padding-horizontal-5">——</span>全国发生的热门事件通过聚类生成，以热度指数排序</p>
                <div class="explain-feature">
                    <span class="explain-feature-title">操作说明</span>
                    <div>
                        <img src="${staticResourcePath}/css/indexV4/base/images/ct.png" alt="" width="100%">
                    </div>
                </div>
                <div class="text-center margin-vertical-20 font-size-14 color_7">
                    <input type="checkbox" style="margin: 0" class="margin-right-5"><span>下次登录不再自动显示</span>
                </div>
                <div class="text-center">
                    <a class="btn btn-warning w100" href="javascript:void(0);" data-dismiss="modal" aria-label="Close">确认</a>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    // 刷新内容
    var loaders = '<i class="icon-reload-a fa-spin"></i>';

    function showLogin() {
        $("#loginModal").modal();

    }

    function getHots(n) {
        //actionBase + "/heatKeywords.action"
        $.ajax({
            url: actionBase + "/rankClassifyList.action",
            type: "post",
            data: {
                "parentId": n,
                "page": 1,
                "pagesize": 100,
                "forLoginPage": 1,
                "isAll": 0
                /*"flag":1,
                'province':"",
                forLoginPage:1*/
            },
            beforeSend: function () {
//                 $("#rankList").loader('show',loaders);
                chartLoading("rankList");
            },
            success: function (data) {
                $("#rankList").html(data);
            }
        });
    }

    function goOpenTools() {
        var index = $("#myTab li").index($("#myTab .active"));
        var kv = $("#search-keyword").val();
        if ($.trim(kv) == "人名、企业名、品牌名或事件关键词") {
            showMsgInfo(0, "请输入搜索词!", 0);
            return;
        }

        if ($.trim(kv).length > 20) {
            showMsgInfo(0, "搜索词不得大于20个字!", 0);
            return;
        }

        if ($.trim(kv) == "" || $.trim(kv) == "请输入您想查询的关键字") {
            showMsgInfo(0, "请输入你想搜索的词语", 0);
            return;
        }
        if ($.trim(kv).length == 1) {
            showMsgInfo(0, "请至少输入2个字符", 0);
            return;
        }
        var keyWordsList = kv.split(",");
        for (n in keyWordsList) {
            if (!checkKeywordFilter(keyWordsList[n])) {
                showMsgInfo(0, "关键词只能包含中文、英文和数字！", 0);
                return;
            }
            if (!checkSingleKeyword(keyWordsList[n])) {
                showMsgInfo(0, "必须输入两个字的词语！", 0);
                return;
            }
        }

        kv = $.trim(kv).replace(/\s+/g, "+");

        $("#title,#keyword").val(kv);
        $("#filterKeyword").val("");
        formSubmit(kv, index);
    }

    function formSubmit(kv, index) {
        var submitFlag = false;
        var form = document.getElementById("searchForm");
        form.action = actionBase + "/goSearch.shtml";
        $("#searchType").val(index);
        form.submit();
    }

    function rankToSearch(n) {
        var title = $("#name_" + n).val();
        var keyword = $("#keyword_" + n).val();
        var filterKeyword = $("#filterKeyword_" + n).val();
        var categoryId = $("#categoryIds_" + n).val();
        if ('undefined' == keyword) {
            keyword = "";
        } else {
            keyword = keyword.replace(/\r\n/g, "");
        }
        if ('undefined' == filterKeyword) {
            filterKeyword = "";
        } else {
            filterKeyword = filterKeyword.replace(/\r\n/g, "");
        }
        $("#title").val(title);
        $("#keyword").val(keyword);
        $("#filterKeyword").val(filterKeyword);
        $("#categoryId").val(categoryId);

        formSubmit(keyword, 0)
    }

    // var mySwiper = new Swiper('.swiper-container', {
    //     autoplay: 5000,//可选选项，自动滑动
    //     speed: 800,
    //     pagination: '#page-pagination',
    //     paginationClickable: true,
    //     autoplayDisableOnInteraction: false
    // })
    var adminJs = "${admin}";
    $(function () {
//     	新版样式 start
// 		$("#noLoginCssId").attr("style","background: transparent;");
        $("#noLoginCssId").attr("class", "w-head abs w-headHover min-layout1200");
        //轮播图
        // var mySwiper = new Swiper('.banner-page', {
        //     pagination: '.banner-pagination',
        //     paginationClickable: true,
        //     speed: 500,
        //     loop: true,
        //     autoplay: 4500,
        //     autoplayDisableOnInteraction: false
        // });


//     	新版样式 end
        $(".w-headbar-search").show();
        $('#head').addClass('head-bg');
        $('#head').addClass('head-croll');

        $('#loginModal').keydown(function (event) {
            if (event.keyCode == 13) {
                event.preventDefault();
                $("#loginBtn").click();
            }
        });

        $('#search-keyword').keydown(function (event) {
            if (event.keyCode == 13) {
                event.preventDefault();
                goOpenTools();
            }
        });

        if ('<%=request.getParameter("login")%>' == 1) {
            $("#login").click();
        }
        if ('<%=request.getParameter("login")%>' == 2) {
            $("#toRegisterID").click();
        }
    <#if msg??>
            var i = ${selectLoginType};
        if (i == 1) {
            $("#messageLoginId").show();
            $("#miMaLoginID").hide();
            $("#mobile_show_info").show().html(msg);
        } else {
            $("#messageLoginId").hide();
            $("#miMaLoginID").show();
            $("#new_mfzc").show().html(msg);
        }
        $("#login").click();
    </#if>

        // goRankingList(1);

        // $.datepicker.regional['zh-CN'] = {
        //     changeMonth: true,
        //     changeYear: true,
        //     clearText: '清除',
        //     clearStatus: '清除已选日期',
        //     closeText: '确定',
        //     closeStatus: '不改变当前选择',
        //     prevText: '<上月',
        //     prevStatus: '显示上月',
        //     prevBigText: '<<',
        //     prevBigStatus: '显示上一年',
        //     nextText: '下月>',
        //     nextStatus: '显示下月',
        //     nextBigText: '>>',
        //     nextBigStatus: '显示下一年',
        //     currentText: '今天',
        //     currentStatus: '显示本月',
        //     monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        //     monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
        //     monthStatus: '选择月份',
        //     yearStatus: '选择年份',
        //     weekHeader: '周',
        //     weekStatus: '年内周次',
        //     dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        //     dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        //     dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
        //     dayStatus: '设置 DD 为一周起始',
        //     dateStatus: '选择 m月 d日, DD',
        //     dateFormat: 'yy-mm-dd',
        //     firstDay: 1,
        //     initStatus: '请选择日期',
        //     isRTL: false
        // };
        // $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
        if ($("#timeQuantum:visible")) {
            if ($('#showStarttime').val() == '') {
                $('#showStarttime').val(getNowFormatDate());
            }

            if ($('#showEndtime').val() == "") {
                $('#showEndtime').val(getNowFormatDate());
            }

//             $('#showStarttime,#showEndtime').datetimepicker({
//                 minDate: "2017-07-01 00:00:00",
// //	 				minDate:endd,
//                 maxDate: new Date(),
//                 timeInput: true,
//                 showSecond: true,
//                 timeFormat: 'HH:mm:ss',
//                 closeText: '确定'
//             })
        }

    });

    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + date.getHours() + seperator2 + date.getMinutes()
                + seperator2 + date.getSeconds();
        return currentdate;
    }
</script>

<script type="text/javascript">

    $(function () {
        $(".rightNav>ul>li").on("click", function () {
            $(this).parents(".rightNav").find("li").removeClass("active");
            $(this).addClass("active");
        });

        //搜索
        $(".btn-search").on("mouseenter", function (e) {
            $(this).addClass("btn-search-in");
            e.stopPropagation();
        });
        $(".btn-search").on("click", function (e) {
            e.stopPropagation();
        });

        $("body").on("click", function () {
            $(".btn-search").removeClass("btn-search-in");
        });
        $('.inner-search-input').bind('input propertychange', function () {
            $(this).parents(".btn-search").addClass("btn-search-in");
        });
    });

    function submitRankFrom() {
        var rankTypeListVal = $("#rankTypeListVal").val();
        var rankLabelListVal = $("#rankLabelListVal").val();
        var rankAreaListVal = $("#rankAreaListVal").val();
        var starttime = $("#showStarttime").val();
        var endtime = $("#showEndtime").val();
        var specificVal = $("#specificVal").val();

        var d1 = new Date(starttime.replace(/\-/g, "\/"));
        var d2 = new Date(endtime.replace(/\-/g, "\/"));
        if (starttime != "" && endtime != "" && d1 >= d2) {
            showMsgInfo(0, "开始时间不能大于或等于结束时间！", 0);
            return;
        }
        if (starttime.length < 18 || endtime.length < 18) {
            showMsgInfo(0, "选择开始时间或者结束时间有误！", 0);
            return;
        }
        $.ajax({
            url: actionBase + "/submitRankFrom.action",
            type: "post",
            data: {
                "time": rankTypeListVal,
                "categoryLevel": rankLabelListVal,
                "province": rankAreaListVal,
                "starttime": starttime,
                "endtime": endtime,
                "keyword": specificVal
            },
            success: function (data) {
                if (data.code == "0000") {
                    showMsgInfo(0, "保存成功！", 0);
                }
            }
        });


    }

    function findKeyword() {
        $.ajax({
            url: actionBase + "/heatKeywords.action",
            type: "post",
            data: {
                "flag": 1,
                'province': "",
                'page': 1,
                "pagesize": 100
            },
            success: function (data) {
                $("#rankList").html(data);
            }
        });
    }

    function btn_searchTextOnBlur(obj) {
        if (obj.value == '') {
            $(obj).parents(".btn-search").removeClass("btn-search-in");
        }
    }

    function goActivePay() {
        var href = '<%=njxBasePath%>/goActivePay.shtml';
        window.open(href, "_blank");
    }

</script>
<#--新版-->
<script src="${staticResourcePath}/css/indexV4/vue.min.js"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${staticResourcePath}/css/indexV4/moment.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${staticResourcePath}/css/indexV4/global/vendor/particles/particles.min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${staticResourcePath}/css/indexV4/global/vendor/particles/particlesbase.json"></script>
<script type="text/javascript" src="${staticResourcePath}/css/indexV4/global/js/countStr.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${staticResourcePath}/css/indexV4/global/vendor/echarts/echarts4.0.2.min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${staticResourcePath}/css/indexV4/global/vendor/echarts/echarts-liquidfill2.min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${staticResourcePath}/css/indexV4/global/vendor/echarts/map/china.js"></script>
<script src="${staticResourcePath}/js/stickUp.min.js?v=${sysConfig}"></script>
<script src="${staticResourcePath}/js/module.js?v=${sysConfig}" charset="utf-8"></script>
<script src="${staticResourcePath}/js/emotionMap/emotionMapchart.js?v=${sysConfig}"></script>
<script src="${staticResourcePath}/js/icheck.js?v=${sysConfig}"></script>
<script src="${staticResourcePath}/css/indexV4/global/vendor/echarts/echarts-componet.js?v=${sysConfig}" charset="utf-8"></script>
<script src="${staticResourcePath}/js/home/home.js?v=${sysConfig}" charset="utf-8"></script>
<script src="${staticResourcePath}/css/indexV4/global/js/lottie.min.js"></script>
<script src="${staticResourcePath}/css/indexV4/global/js/lottie-animate.js"></script>
<#if admin1??>
    <script type="text/javascript" src="${staticResourcePath}/css/indexV4/base/js/tool/tool-main.js?v=${sysConfig}"></script>
<#else>
    <script type="text/javascript" src="${staticResourcePath}/css/indexV4/base/js/tool/tool.js?v=${sysConfig}"></script>
</#if>

<script type="text/javascript" charset="utf-8" src="${staticResourcePath}/css/indexV4/global/vendor/echarts/map/TweenMax.min.js" ></script>
<script type="text/javascript" charset="utf-8" src="${staticResourcePath}/css/indexV4/global/vendor/echarts/map/easeljs.min.js" ></script>
<script type="text/javascript" charset="utf-8" src="${staticResourcePath}/css/indexV4/global/vendor/echarts/map/lodash.min.js" ></script>
<script type="text/javascript" charset="utf-8" src="${staticResourcePath}/css/indexV4/global/vendor/echarts/map/myTooltip.js" ></script>


<script type="text/javascript">
    $(function () {
        //滚动
        $(window).scroll(function () {
            if ($(document).scrollTop() > 120) {
                $('.w-head.headFixed').addClass('bgColor_1')
            } else {
                $('.w-head.headFixed').removeClass('bgColor_1')
            }
        });
        $('.main-tabs li').on('click', function () {
            var index = $(this).index();
            var islogin=$("#isLogin").val();
            if((index==1||index==2)&&islogin!="true"){
                return
            }
            $(this).addClass('active').siblings().removeClass('active');

            $('.w-container-body').find('.data-content').removeClass('active').eq(index).addClass('active');
        });
        // $('.sizeCount').countStr({
        //     length: 10,
        //     input: 'input',
        //     child: '.sizeNum'
        // });

        //查看详情
        $('.wbp-item-info').on('click', function () {
            var $li = $(this).parents(".wb-p-item").siblings(".wb-p-item");
            $li.find(".wbp-item-info").removeClass('active');
            $li.find(".wb-sub-card").hide();
            $(this).toggleClass("active");
            $(this).nextAll(".wb-sub-card").toggle();
            if ($(this).hasClass("active")) {
                $(this).removeClass('active');

            } else {
                $(this).addClass('active');
            }
        })
    });
</script>

</body>
</html>