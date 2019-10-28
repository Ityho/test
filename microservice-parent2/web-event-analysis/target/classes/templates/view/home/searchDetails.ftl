
        <#include "../../init_top.ftl" >
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		<meta name="renderer" content="webkit">
		<meta name="format-detection" content="telephone=no" />
		<meta property="wb:webmaster" content="ff5fa17a9f9796d9" />
		<!-- 微博 -->
		<meta property="qc:admins" content="2404321175711636" />
		<!-- QQ -->
		<meta name="description" content="舆情、商情就用微舆情">
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css"/>
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css"/>
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css" />
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/loading.css" />
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/modal.css?v=${SYSTEM_INIT_TIME}" />
		<script src="${staticResourcePathH5}/js/home/navigator/wyrem.js"></script>

        <script src="${staticResourcePathH5}/js/vue/vue.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/vue/moment.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/sweetalert.min.js?v=${SYSTEM_INIT_TIME}" ></script>


		<title>微热点</title>
		<style>

		</style>
	</head>
        <body class="mainBackground rel">
        <div class="wui-bar wui-tag">
            <a href="javascript:history.go(-1)" class="wui-back mui-action-back wui-icon-nav wui-pull-left iconfont icon-fanhui">
            </a>
            <!--搜索框-->
            <div class="wui-search-default">
                <form action="" onsubmit="return false;">
                    <div>
                        <input type="search" value="" id="getSearch" class="wui-search-input" placeholder="搜索人名、企业名、品牌名或事件关键词">
                        <span class="wui-search-close">取消</span>
                    </div>
                </form>
            </div>
        </div>
        <div class="wui-shade" style="display: none"></div>

        <div id="app">
            <div class="wui-content rel" >

                <input id="searchTitle" value="${searchTitle}"   style="display: none">
                <input id="searchKeyword" value="${searchKeyword}"   style="display: none">
                <input id="searchFilterKeyword" value="${searchFilterKeyword}"   style="display: none">
                <input id="platform" name="platform" value="${platform}" style="display: none">
                <input id="shareCode" name="shareCode" value="${shareCode}" style="display: none">

                <!-- 选项-->
                <div class="barBackground">
                    <h3 class="wui-title"  v-text="searchKeyword1" ></h3>
                    <ul class="wui-tabs wui-segmented-control clearfix">
                        <li class="active">
                            <a href="javascript:;">热度指数</a>
                        </li>
                        <li>
                            <a href="javascript:;">微博情绪</a>
                        </li>
                    </ul>
                </div>
                <!--body-->
                <div class="tab-content">
                    <div class="tab-item in active">
                        <!--时间选择-->
                        <div class="text-center" style="padding: 25px 15px;">
                            <ul class="wui-tabs-defalut">
                                <li class="active">
                                    <a href="javascript:;">24小时</a>
                                </li>
                                <li>
                                    <a href="javascript:;">72小时</a>
                                </li>
                            </ul>
                        </div>
                        <!-- 全网热度概述 start -->
                        <section>
                            <div class="wui-head">
                                <h3>全网热度概述
                                    <div class="inline-block margin-left-5 wui-tooltips">
                                        <i class="iconfont icon-jieshi fontColor_1"></i>
                                    </div>
                                    <div class="wui-tooltips-body">
                                        该数据为您显示：在指定时间范围内，该关键词的全网热度指数值。
                                    </div>
                                </h3>
                            </div>

                            <div class="wui-section-body padding-15">
                                <div class="wui-row clearfix">
                                    <div class="col-sm-6">
                                        <div class="wui-wedge wui-wedge-card">
                                            <span>查询时间段</span>
                                            <p><span class="font-size-22 fontColor_1 font-weight-600" v-text="date"></span></p>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="wui-wedge wui-wedge-card">
                                            <span>热度指数均值</span>
                                            <p><span class="font-size-22 fontColor_1 font-weight-600" v-text="hotAvg"></span></p>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="wui-wedge wui-wedge-card">
                                            <span>1小时热度变化趋势</span>
                                            <!--
                                        下降
                                        <i class="iconfont icon-xiajiang1 font-size-20 fontColor_3"></i>
                                        上升
                                        <i class="iconfont icon-shangsheng1 font-size-20 fontColor_2"></i>
                                        <i class="iconfont icon-chiping font-size-20 fontColor_4" v-if="hotAvgQs==0"></i>
                                        -->

                                            <p><span class="font-weight-600 fontColor_1 font-size-24 " v-text="hotAvgQs"></span><i class="iconfont icon-xiajiang1 font-size-20 fontColor_3" v-if="hotAvgQs<0"></i><i class="iconfont icon-shangsheng1 font-size-20 fontColor_2" v-if="hotAvgQs>0"></i></p>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="wui-wedge wui-wedge-card">
                                            <span>热度指数峰值</span>
                                            <p><span class="font-size-22 fontColor_1 font-weight-600" v-text="topHotAvg"></span></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <!-- 全网热度概述 end -->
                        <!-- 全网热度趋势 start -->
                        <section>
                            <div class="wui-head">
                                <h3>全网热度趋势
                                    <div class="inline-block margin-left-5 wui-tooltips">
                                        <i class="iconfont icon-jieshi fontColor_1"></i>
                                    </div>
                                    <div class="wui-tooltips-body">
                                        该数据为您显示：在指定时间范围内，该关键词分时段的热度值变化走势。
                                    </div>
                                </h3>
                            </div>

                            <div class="wui-section-body padding-15">
                                <div class="text-center fontColor_1 font-size-14">
                                    <span v-text="searchKeyword1"></span>
                                </div>
                                <div class="text-center margin-10">
                                    <span class="font-size-12 nowrap margin-right-10 fontColor_0"><i class="legend-icon" ></i> 热度趋势</span>
                                    <span class="font-size-12 nowrap margin-right-10 fontColor_0"><i class="legend-icon-average"></i> 热度均值</span>
                                </div>
                                <div id="lineEcharts" data-type="hotline" style="height: 350px">
                                    <div class="spinner1">
                                        <div class="spinner-container container1">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                        <div class="spinner-container container2">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                        <div class="spinner-container container3">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </section>
                        <!-- 全网热度趋势 end -->
                        <!--全网关键词云-->
                        <section>
                            <div class="wui-head">
                                <h3>全网关键词云
                                    <div class="inline-block margin-left-5 wui-tooltips">
                                        <i class="iconfont icon-jieshi fontColor_1"></i>
                                    </div>
                                    <div class="wui-tooltips-body">
                                        该数据为您显示：利用自然语义分析法，对事件、人物、品牌、地域中所提及的关键词进行分词聚合，呈现出被提及次数最多的关键词；字号越大的词组，被提及次数越多。
                                    </div>
                                </h3>
                            </div>

                            <div class="wui-section-body wBWordClouds">
                                <div id="wordcloud" data-type="wordcloud"  style="height: 350px">
                                    <div class="spinner1">
                                        <div class="spinner-container container1">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                        <div class="spinner-container container2">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                        <div class="spinner-container container3">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <p class="fontColor_0 font-size-12 padding-15">在与 <span class="fontColor_1 " v-text="searchKeyword1"> </label></span>相关的全部信息中，被提及频次最高的词语分别为 <span class="fontColor_1" v-text="hotResultMsg"> </span></p>
                        </section>
                        <div class="endline">
                            <span>我们是有底线的哦～</span>
                        </div>
                    </div>
                    <div class="tab-item">
                        <!--时间选择-->
                        <div class="text-center" style="padding: 25px 15px;">
                            <ul class="wui-tabs-defalut">
                                <li class="active">
                                    <a href="javascript:;">24小时</a>
                                </li>
                                <li>
                                    <a href="javascript:;">72小时</a>
                                </li>
                            </ul>
                        </div>
                        <!--图表-->
                        <section>
                            <div class="wui-head">
                                <h3>微博情绪占比
                                    <div class="inline-block margin-left-5 wui-tooltips">
                                        <i class="iconfont icon-jieshi fontColor_1"></i>
                                    </div>
                                    <div class="wui-tooltips-body">
                                        在指定时间范围内，该关键词的各类情绪声量的对比呈现和成分占比。
                                    </div>
                                </h3>
                            </div>
                            <div class="wui-section-body padding-15">
                                <div id="emotionpieEchart" style="height: 300px;" class="echarts" data-type = "emotionProportion" ng-init = "doEmotionProportion()">
                                    <div class="spinner1">
                                        <div class="spinner-container container1">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                        <div class="spinner-container container2">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                        <div class="spinner-container container3">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <!--微博情绪地图-->
                        <section>
                            <div class="wui-head">
                                <h3>微博情绪地图
                                    <div class="inline-block margin-left-5 wui-tooltips">
                                        <i class="iconfont icon-jieshi fontColor_1"></i>
                                    </div>
                                    <div class="wui-tooltips-body">
                                        情绪地图”是指基于深度学习的语义情感分类模型，在人类常见情绪分类的基础上、对社会化媒体文本大数据中的潜在情绪进行判别和归类统计，最终呈现为情绪在时间和空间维度上的分布。在地图上，仅展示我国34个省级行政区域（包括23个省，5个自治区，4个直辖市，以及香港，澳门2个特别行政区）的声量TOP10。
                                    </div>
                                </h3>
                            </div>
                            <div class="wui-section-body padding-15">
                                <div>
                                    <ul class="wui-tabs wui-emotion-nav clearfix">

                                        <li class="active">  <a href="javascript:;">显著</a></li>
                                        <li  >  <a href="javascript:;" >喜悦</a></li>
                                        <li  >  <a href="javascript:;" >中性</a></li>
                                        <li  >  <a href="javascript:;" >愤怒</a></li>
                                        <li  >  <a href="javascript:;" >悲伤</a></li>
                                        <li  >  <a href="javascript:;" >惊奇</a></li>
                                        <li  >  <a href="javascript:;" >恐惧</a></li>
                                    </ul>
                                </div>
                                <div class="tabs-ebody">
                                    <div class="tabs-item active">
                                        <div id="emotionMap" style="height: 350px"></div>
                                        <div class="wui-table-content">
                                            <h3>微博声量TOP10地区显著情绪</h3>
                                            <table id="xzMoodTop10Desc" class="wui-table" style="width: 100%;">
                                                <thead>
                                                <tr>
                                                    <th width="25%">排行</th>
                                                    <th width="25%">地区</th>
                                                    <th width="25%">微博总声量</th>
                                                    <th width="25%">显著情绪</th>
                                                </tr>
                                                </thead>


                                                <tbody id="c6_tb" v-for="(area,index) in areaStatistics">
                                                <tr>
                                                    <td>{{index+1}}</td>
                                                    <td>{{area[0]}}</td>
                                                    <td>{{area[1]}}</td>
                                                    <td>
                                                        <div  v-if="area[2] == '喜悦'">
                                                            <a href="javascript:;" class="wui-color-yellow">{{area[2]}}</a>
                                                        </div>
                                                        <div  v-if="area[2]=='愤怒'">
                                                            <a href="javascript:;" class="wui-color-red">{{area[2]}}</a>
                                                        </div>
                                                        <div  v-if="area[2]=='悲伤'">
                                                            <a href="javascript:;" class="wui-color-blue">{{area[2]}}</a>
                                                        </div>
                                                        <div  v-if="area[2]=='惊奇'">
                                                            <a href="javascript:;" class="wui-color-green">{{area[2]}}</a>
                                                        </div>
                                                        <div  v-if="area[2]=='恐惧'">
                                                            <a href="javascript:;" class="wui-color-gray">{{area[2]}}</a>
                                                        </div>
                                                        <div  v-if="area[2]=='中性'">
                                                            <a href="javascript:;" class="wui-color-gray_drak">{{area[2]}}</a>
                                                        </div>

                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section>
                            <div class="wui-head">
                                <h3>微博情绪趋势
                                    <div class="inline-block margin-left-5 wui-tooltips">
                                        <i class="iconfont icon-jieshi fontColor_1"></i>
                                    </div>
                                    <div class="wui-tooltips-body">
                                        在指定时间范围内，该关键词的情绪声量在时间维度上的变化趋势。
                                    </div>
                                </h3>
                            </div>
                            <div class="wui-section-body padding-15">
                                <div id="contentTrends" style="height: 300px" ng-init = "getContentTrends()"  class="echarts" data-type = "trendsLine">
                                    <div class="spinner1">
                                        <div class="spinner-container container1">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                        <div class="spinner-container container2">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                        <div class="spinner-container container3">
                                            <div class="circle1"></div>
                                            <div class="circle2"></div>
                                            <div class="circle3"></div>
                                            <div class="circle4"></div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </section>
                        <section>
                            <div class="wui-head">
                                <h3>微博用户情绪洞察
                                    <div class="inline-block margin-left-5 wui-tooltips">
                                        <i class="iconfont icon-jieshi fontColor_1"></i>
                                    </div>
                                    <div class="wui-tooltips-body" style="left: 9.375rem;">
                                        该数据为您显示：<br/>提及该关键词时，用户属性（性别、认证类型）和情绪维度（中性、喜悦、愤怒、悲伤、惊奇、恐惧）以及情感维度（敏感和非敏感）的关联统计分析。
                                    </div>
                                </h3>
                            </div>
                            <div class="wui-section-body padding-15">
                                <h4 class="yuan-emotion-title">
                                    <span>六元情绪洞察</span>
                                </h4>
                                <div>
                                    <p class="echartTitle"><em></em>情绪分布<span class="fz14 fc_dark_grey">「性别」</span></p>
                                    <div id="emotionSex" style="height: 300px;" data-type = "emotionSex">
                                        <div class="spinner1">
                                            <div class="spinner-container container1">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container2">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container3">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <p class="echartTitle"><em></em>情绪分布<span class="fz14 fc_dark_grey" >「用户认证类型」</span></p>
                                    <div id="emotionType" style="height: 300px;" data-type = "emotionType">
                                        <div class="spinner1">
                                            <div class="spinner-container container1">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container2">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container3">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <h4 class="yuan-emotion-title">
                                    <span>二元情绪洞察</span>
                                </h4>
                                <div class="text-center margin-10">
                                    <span class="color_3"><i class="icon-square"></i> 非敏感</span>
                                    <span class="color_4"><i class="icon-square"></i> 敏感</span>
                                </div>
                                <p class="echartTitle"><em></em>性别</p>
                                <div class="row clearfix">
                                    <div class="col-sm-6">
                                        <div id="emotionSex2M" style="height: 160px;">
                                            <div class="spinner1">
                                                <div class="spinner-container container1">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container2">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container3">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <p class="font-size-12 color_4 text-center" > 敏感 <span v-text="emotionSex2M_M"></span></p>
                                        <p class="font-size-12 color_3 text-center"> 非敏感 <span v-text="emotionSex2M_F"></span></p>
                                    </div>
                                    <div class="col-sm-6">
                                        <div id="emotionSex2F" style="height: 160px;">
                                            <div class="spinner1">
                                                <div class="spinner-container container1">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container2">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container3">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <p class="font-size-12 color_4 text-center" > 敏感 <span v-text="emotionSex2F_M"></span></p>
                                        <p class="font-size-12 color_3 text-center"> 非敏感 <span v-text="emotionSex2F_F"></span></p>
                                    </div>
                                </div>
                                <div>
                                    <p class="echartTitle margin-top-15"><em></em>用户认证类型</p>
                                    <div id="emotionType2" style="height: 200px;" data-type = "emotionType2">
                                        <div class="spinner1">
                                            <div class="spinner-container container1">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container2">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container3">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <p class="echartTitle margin-top-15"><em></em>粉丝数量分布</p>
                                    <div id="emotionFans" style="height: 200px;">
                                        <div class="spinner1">
                                            <div class="spinner-container container1">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container2">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container3">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <p class="echartTitle margin-top-15"><em></em>转发层级</p>
                                    <div class="rel">
                                        <div id="getEmotionLevel" style="height: 170px;">
                                            <div class="spinner1">
                                                <div class="spinner-container container1">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container2">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container3">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div style="position: absolute;right: 35px;top: 68px;">
                                            <p class="font-size-12 color_4 text-center" v-text="emotionLevel1_M"></p>
                                            <p class="font-size-12 color_3 text-center" v-text="emotionLevel1_F"></p>
                                        </div>
                                    </div>
                                    <div class="rel">
                                        <div id="getEmotionLevel2" style="height: 170px;">
                                            <div class="spinner1">
                                                <div class="spinner-container container1">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container2">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container3">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div style="position: absolute;right: 35px;top: 68px;">
                                            <p class="font-size-12 color_4 text-center" v-text="emotionLevel2_M"></p>
                                            <p class="font-size-12 color_3 text-center" v-text="emotionLevel2_F"></p>
                                        </div>
                                    </div>
                                    <div class="rel">
                                        <div id="getEmotionLevel3" style="height: 170px;">
                                            <div class="spinner1">
                                                <div class="spinner-container container1">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container2">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container3">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div style="position: absolute;right: 35px;top: 68px;">
                                            <p class="font-size-12 color_4 text-center" v-text="emotionLevel3_M"></p>
                                            <p class="font-size-12 color_3 text-center" v-text="emotionLevel3_F"></p>
                                        </div>
                                    </div>
                                    <div class="rel">
                                        <div id="getEmotionLevel4" style="height: 170px;">
                                            <div class="spinner1">
                                                <div class="spinner-container container1">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container2">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                                <div class="spinner-container container3">
                                                    <div class="circle1"></div>
                                                    <div class="circle2"></div>
                                                    <div class="circle3"></div>
                                                    <div class="circle4"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div style="position: absolute;right: 35px;top: 68px;">
                                            <p class="font-size-12 color_4 text-center" v-text="emotionLevel4_M"></p>
                                            <p class="font-size-12 color_3 text-center" v-text="emotionLevel4_F"></p>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <p class="echartTitle margin-top-15"><em></em>兴趣标签</p>
                                    <div>
                                        <div>
                                            <div class="label-emotion-title margin-bottom-15 margin-top-10">
                                                <span>非敏感言论用户兴趣标签</span>
                                            </div>
                                            <ul class="terms square  clearfix text-center" id="interestWordCloud-zm">
                                                <div class="spinner1">
                                                    <div class="spinner-container container1">
                                                        <div class="circle1"></div>
                                                        <div class="circle2"></div>
                                                        <div class="circle3"></div>
                                                        <div class="circle4"></div>
                                                    </div>
                                                    <div class="spinner-container container2">
                                                        <div class="circle1"></div>
                                                        <div class="circle2"></div>
                                                        <div class="circle3"></div>
                                                        <div class="circle4"></div>
                                                    </div>
                                                    <div class="spinner-container container3">
                                                        <div class="circle1"></div>
                                                        <div class="circle2"></div>
                                                        <div class="circle3"></div>
                                                        <div class="circle4"></div>
                                                    </div>
                                                </div>
                                            </ul>
                                        </div>
                                        <div class="">
                                            <div class="label-emotion-title margin-bottom-15 margin-top-10">
                                                <span>敏感言论用户兴趣标签</span>
                                            </div>
                                            <ul class="terms conside clearfix text-center" id="interestWordCloud-fm">
                                                <div class="spinner1">
                                                    <div class="spinner-container container1">
                                                        <div class="circle1"></div>
                                                        <div class="circle2"></div>
                                                        <div class="circle3"></div>
                                                        <div class="circle4"></div>
                                                    </div>
                                                    <div class="spinner-container container2">
                                                        <div class="circle1"></div>
                                                        <div class="circle2"></div>
                                                        <div class="circle3"></div>
                                                        <div class="circle4"></div>
                                                    </div>
                                                    <div class="spinner-container container3">
                                                        <div class="circle1"></div>
                                                        <div class="circle2"></div>
                                                        <div class="circle3"></div>
                                                        <div class="circle4"></div>
                                                    </div>
                                                </div>
                                            </ul>

                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <p class="echartTitle margin-top-15"><em></em>口碑热词</p>
                                    <div class="label-emotion-title margin-bottom-15 margin-top-10">
                                        <span>非敏感高频词</span>
                                    </div>
                                    <div id="zmHotWord" style="height: 200px;">

                                        <ul class="terms square  clearfix" id="zmHotWord-ul">

                                        </ul>
                                        <div class="spinner1">
                                            <div class="spinner-container container1">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container2">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container3">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="label-emotion-title margin-bottom-15 margin-top-10">
                                        <span>敏感高频词</span>
                                    </div>
                                    <div id="fmHotWord" style="height: 200px;">
                                        <ul class="terms conside clearfix" id="fmHotWord-ul">
                                        </ul>
                                        <div class="spinner1">
                                            <div class="spinner-container container1">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container2">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                            <div class="spinner-container container3">
                                                <div class="circle1"></div>
                                                <div class="circle2"></div>
                                                <div class="circle3"></div>
                                                <div class="circle4"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </section>
                        <div class="endline">
                            <span>我们是有底线的哦～</span>
                        </div>
                    </div>
                </div>
            </div>
            <div id="wui-modal-tootips2" class="wui-modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="wui-modal-body text-center">
                            <a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
                            <h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">温馨提示</h3>
                            <p class="font-size-12 color_1"> 您查询的：'<label v-text="keywordStr"></label>' 详情数据，需升级到专业版查看，可以联系客服小姐姐哦～</p>
                        </div>
                        <div class="modal-footer clearfix">
                            <a href="javascript:;" class="btn" data-dismiss="modal">确定</a>
                            <a href="javascript:;" class="btn fontColor_1"  onclick="window.open('http://wpa.qq.com/msgrd?v=3&amp;uin=3002432217&amp;site=qq&amp;menu=yes','_blank')">联系客服</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


<#--        <div id="showCode" style="margin-top: 10px;margin-bottom: 10px;margin-left: 5px; display:none;">-->
<#--            <div id="float-captcha"></div>-->
<#--            <span id="wait" class="show">正在加载验证码......</span>-->
<#--        </div>-->

        <script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/echarts/echarts3.js?v=${SYSTEM_INIT_TIME}" charset="utf-8"></script>
        <script src="${staticResourcePathH5}/js/echarts/china.js?v=${SYSTEM_INIT_TIME}" charset="utf-8"></script>
        <script src="${staticResourcePathH5}/js/echarts/wordcloud.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/echarts/searchEchart.js"></script>
        <script src="${staticResourcePathH5}/js/newHome/module.js?v=${SYSTEM_INIT_TIME}" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" src="${staticResourcePath}/js/utils/utils.js"></script>
        <script type="text/javascript" src="http://static.geetest.com/static/tools/gt.js"></script>
        <script type="text/javascript" src="${staticResourcePath}/js/geetest.js"></script>
        <script src="${staticResourcePathH5}/js/vue/home/hotSearch.js?v=${SYSTEM_INIT_TIME}"></script>

        <script>

            if (app){
                if (app.index == 0){
                    app.loadMore(-1);
                }else{
                    app.loadMore(0);
                }
            }
            $("#getSearch").bind("search", function() {
                var target = $(this);
                $(".wui-shade").hide();
                target.parent().removeClass('wui-flex-search')

                app.searchEnter();

            });
            $(window).scroll(function(){
                var totalheight =parseFloat($(window).scrollTop())+ parseFloat($(window).height());
                if (app.index == 1){
                    if (totalheight > 1100){
                        if(app.loadMore_flag) {
                            app.loadMore(1);
                            app.loadMore_flag = false;
                        }
                    }
                    if(totalheight>2500){
                        if(app.loadMore_flag_two){
                            console.log("加载新数据Two");
                            app.loadMore(2);
                            app.loadMore_flag_two=false;
                        }
                    }
                }
            });

            function searchReload(){
                app.sealReload();
                $("#showCode").hide();
            }

            $(function() {
                //切换
                $('.wui-segmented-control li').on("click", function() {
                    $(this).addClass('active').siblings().removeClass('active');
                    var index = $(this).index();
                    $(".tab-content .tab-item").eq(index).addClass('in active').siblings().removeClass('in active');
                    app.index = index;
                    app.checkIndex(index,app.keyword1);
                })
                //微博情绪地图切换
                $('.wui-emotion-nav li').on("click", function() {
                    $(this).addClass('active').siblings().removeClass('active');
                    var index = $(this).index();

                    app.changeEmotionMap(index);

                    $(".tabs-ebody .tabs-item").eq(index).addClass('active').siblings().removeClass('active');
                })
                //时间
                $('.wui-tabs-defalut li').on("click", function() {
                    $(this).addClass('active').siblings().removeClass('active');
                    app.initFlag();


                    var index = $(this).index();
                    if (index == 0){
                        if (app.index == 0){
                            app.date = 24;
                        } else {
                            app.emotionDate = 24;
                        }
                    } else {
                        if (app.index == 0){
                            app.date = 72;
                        } else {
                            app.emotionDate = 72;
                        }
                    }
                    if (app.index==0) {
                        app.loadMore(-1);
                    }else {
                        app.loadMore(0);
                    }
                })

                $('.wui-search-input').on("click", function() {
                    var target = $(this);
                    $(".wui-shade").show();
                    target.parent().addClass('wui-flex-search')

                })
                $('.wui-search-close').on('click', function() {
                    var target = $(this);
                    $(".wui-shade").hide();
                    target.parent().removeClass('wui-flex-search')
                });


                $('.wui-tooltips').on('click', function(e) {
                    e.stopPropagation();
                    $(this).parent().find('.wui-tooltips-body').show()
                })
                $(document).mouseup(function(e) {
                    var _con = $('.wui-tooltips-body '); // 设置目标区域
                    if(!_con.is(e.target) && _con.has(e.target).length === 0) {
                        $('.wui-tooltips-body ').hide()
                    }
                });

            })
        </script>
        </body>
</html>
