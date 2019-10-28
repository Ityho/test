<html>
<head>
    <!-- saved from url=(0014)index:internet -->
<#--<%@ include file="top.jsp" %>-->
    <#include "../common/top.ftl"/>
    var ctx = '${staticResourcePath}';

    <#--引入新版head信息=========================================================start-->
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
    <meta charset="GBK">
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
<s:include value="/view/tips.jsp"></s:include>
<s:include value="/view/navigate.jsp">
    <s:param name="isShowTop">1</s:param>
    <s:param name="currentPage">hot</s:param>
</s:include>
<#--头部导航 end-->

<#--底部-->
<s:if test="#attr.admin==null">
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
                        <s:if test="#attr.admin!=null">
                            @click='goTab(2)'
                        </s:if>
                        <s:if test="#attr.admin==null">
                            id="login" @click="goto(35)"
                        </s:if>
                        >
                        <a href="javascript:void(0)">重大事件</a>
                        </li>
                        <!--   <li :class="{active:activeTab == 3}">
                              <a href="javascript:void(0)" @click='goTab(3)'>热门人物</a>
                          </li> -->

                        <li :class="{active:activeTab == 4}" class="col-md-4"
                        <s:if test="#attr.admin!=null">
                            @click='goTab(4)'
                        </s:if>
                        <s:if test="#attr.admin==null">
                            id="login" @click="goto(36)"
                        </s:if>
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

</s:if>
<s:if test="#attr.admin!=null">
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
                        <s:if test="#attr.admin!=null">
                            @click='goTab(2)'
                        </s:if>
                        <s:if test="#attr.admin==null">
                            id="login" @click="goto(35)"
                        </s:if>
                        >
                        <a href="javascript:void(0)">重大事件</a>
                        </li>
                        <!--   <li :class="{active:activeTab == 3}">
                              <a href="javascript:void(0)" @click='goTab(3)'>热门人物</a>
                          </li> -->

                        <li :class="{active:activeTab == 4}" class="col-md-4"
                        <s:if test="#attr.admin!=null">
                            @click='goTab(4)'
                        </s:if>
                        <s:if test="#attr.admin==null">
                            id="login" @click="goto(36)"
                        </s:if>
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

</s:if>
<s:include value="view/bottoms.jsp"></s:include>

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
<s:if test="#attr.admin==null">
    <script type="text/javascript" src="${staticResourcePath}/css/indexV4/base/js/tool/tool-main.js?v=${sysConfig}"></script>
</s:if>
<s:else>
    <script type="text/javascript" src="${staticResourcePath}/css/indexV4/base/js/tool/tool.js?v=${sysConfig}"></script>
</s:else>
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