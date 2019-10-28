<#include "../../init_top.ftl" >
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css?v=${SYSTEM_INIT_TIME}"/>
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" href="${staticResourcePathH5}/css/newHome/mui/mui.min.css?v=${SYSTEM_INIT_TIME}">
        <link href="${staticResourcePathH5}/css/newHome/mui/mui.picker.min.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" />
        <link href="${staticResourcePathH5}/css/newHome/mui/mui.poppicker.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" />
		<link rel="stylesheet" href="${staticResourcePathH5}/css/newHome/swiper.min.css?v=${SYSTEM_INIT_TIME}">
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/modal.css?v=${SYSTEM_INIT_TIME}" />
		<script src="${staticResourcePathH5}/js/newHome/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>
		<title>微热点</title>
		<style>

		</style>
	</head>
	<body class="mainBackground">
        <div id="app">
		<div class="rel" id="bigEvent" v-cloak>
		<!-- 轮播图 start -->
		<div class="wui-swiper wui-dark-shadow">
			<div class="swiper-container">
				<div class="swiper-wrapper">
                    <div class="swiper-slide" v-for="(item,index) in bigDataList " @click="getBigDataDetail(item.id)" >
                        <img v-bind:src="item.imageUrl"/>
                        <span class="wui-abs-top" v-text="item.typeValue"></span>
                        <div class="wui-abs-bottom" >
                            <p v-text="item.title" > </p>
                        </div>
                    </div>
                </div>
				<div class="swiper-pagination"></div>
			</div>
		</div>
		<!-- 轮播图 end -->
		<!-- search start -->
		<div class="wui-panel margin-top-30">
			<div class="wui-panel-head">
				<h3>热度指数</h3>
			</div>
<#--            <div class="wui-search clearfix">-->
<#--                <input type="search" value="" id="getSearch" class="wui-search-input" placeholder="搜索人名、企业名、品牌名或事件关键词" @keyup.enter="searchEnter" />-->
<#--                <span class="wui-search-btn"><i class="iconfont icon-sousuo1"></i></span>-->
<#--            </div>-->
            <form action="" onsubmit="return false;">
                <div class="wui-search clearfix">
                    <input type="search" value="" id="getSearch" class="wui-search-input" placeholder="搜索人名、企业名、品牌名或事件关键词"/>
                    <span class="wui-search-btn"><i class="iconfont icon-sousuo1"></i></span>
                </div>
            </form>

        </div>
        <!-- search end -->
        <!-- 热门事件 start -->
        <section class="wui-panel margin-top-20">
            <div class="wui-panel-head">
                <ul class="wui-tabs clearfix">
                    <li class="active"><a href="javascript:;">热门事件</a></li>
                    <li data-index="1"><a href="javascript:;">重大事件</a></li>
                </ul>
                <div class="wui-dropdown wui-panel-action">
                    <a id='showHotPicker' href="javascript:;" data-id="1"><span class="hotResult">全部</span> <i class="iconfont icon-icon-downicon font-size-12 vertical-align-middle"></i></a>
                    <a id='showCityPicker' href="javascript:;" data-id="2"><span class="cityResult">地域</span><i class="iconfont icon-icon-downicon font-size-12 vertical-align-middle"></i></a>
                </div>
            </div>
            <div class="tab-content" >
                <div class="tab-item fade in active">
                    <div class="wui-panel-body">
                        <p class="font-size-10 fontColor_0 margin-top-15 margin-bottom-10">24小时共计：事件数 <span class="fontColor_1" v-text=eventNum></span>，其中 <span class="fontColor_2" v-text=upEventNum > </span>件热度上升，<span class="fontColor_3 "v-text=downEventNum > </span>件热度下降</p>
                        <div class="wui-panel-item" v-for="(bh,index) in rankData" @click="getSearchDetail(bh.incidentTitle,bh.keyword,bh.filterKeyword)">
                            <div class="wui-item-left">
                                <p class="margin-bottom-15"><span class="vertical-align-middle" v-text="(index+1)+'.'"></span>
                                    <span class="title nowrap"  v-if="bh.incidentTitle.length>18"> {{bh.incidentTitle.substring(0,18)+'...'}}</span>
                                    <span class="title nowrap"  v-if="bh.incidentTitle.length<=18"> {{bh.incidentTitle}}</span>

                                    <span class="wui-badge wui-badge-orange" v-if="
                                             ( bh.labelNames.split(',').indexOf('明星')>-1
                                            || bh.labelNames.split(',').indexOf('电影')>-1
                                            || bh.labelNames.split(',').indexOf('电视剧')>-1
                                            || bh.labelNames.split(',').indexOf('综艺')>-1
                                            || bh.labelNames.split(',').indexOf('文化')>-1)
                                             && 30 <= (bh.ratioHotDay).toFixed(2)
                                             &&(bh.ratioHotDay).toFixed(2)< 50
                                             && 60<=bh.ratioHotTopCustom">热</span>
                                    <span class="wui-badge wui-badge-red" v-if="
                                             (bh.labelNames.split(',').indexOf('明星')>-1
                                            || bh.labelNames.split(',').indexOf('电影')>-1
                                            || bh.labelNames.split(',').indexOf('电视剧')>-1
                                            || bh.labelNames.split(',').indexOf('综艺')>-1
                                            || bh.labelNames.split(',').indexOf('文化')>-1)
                                            &&  (bh.ratioHotDay).toFixed(2)>=50
                                            && bh.ratioHotTopCustom >= 80">爆</span>
                                    <span class="wui-badge wui-badge-blue" v-if="
                                             ( bh.labelNames.split(',').indexOf('明星')>-1
                                            || bh.labelNames.split(',').indexOf('电影')>-1
                                            || bh.labelNames.split(',').indexOf('电视剧')>-1
                                            || bh.labelNames.split(',').indexOf('综艺')>-1
                                            || bh.labelNames.split(',').indexOf('文化')>-1)
                                             && 15 <= (bh.ratioHotDay).toFixed(2)
                                             && (bh.ratioHotDay).toFixed(2)<30
                                             && 'bh.createTime | del()' < '2' ">新</span>
                                    <span class="wui-badge wui-badge-orange" v-if="
                                            bh.labelNames.split(',').indexOf('小事件')>-1
                                            && 1 <= (bh.ratioHotDay).toFixed(2)
                                            &&(bh.ratioHotDay).toFixed(2)<2
                                            && 10 <= bh.ratioHotTopCustom">热</span>
                                    <span class="wui-badge wui-badge-red" v-if="
                                            bh.labelNames.split(',').indexOf('小事件')>-1
                                            && 2 <= (bh.ratioHotDay).toFixed(2)
                                            && bh.ratioHotTopCustom >= 20">爆</span>
                                    <span class="wui-badge wui-badge-blue" v-if="
                                            bh.labelNames.split(',').indexOf('小事件')>-1
                                            && 0.8 <= (bh.ratioHotDay).toFixed(2)
                                            &&(bh.ratioHotDay).toFixed(2) < 1
                                            && 'bh.createTime | del()' < '2'">新</span>
                                    <span class="wui-badge wui-badge-orange" v-if="
                                            bh.labelNames.split(',').indexOf('明星')==-1
                                            && bh.labelNames.split(',').indexOf('电影')==-1
                                            && bh.labelNames.split(',').indexOf('电视剧')==-1
                                            && bh.labelNames.split(',').indexOf('综艺')==-1
                                            && bh.labelNames.split(',').indexOf('文化')==-1
                                            && bh.labelNames.split(',').indexOf('小事件')==-1
                                            && 10 <= (bh.ratioHotDay).toFixed(2)
                                            &&(bh.ratioHotDay).toFixed(2) <= 30
                                            && 30 <= bh.ratioHotTopCustom">热</span>
                                    <span class="wui-badge wui-badge-red" v-if="
                                             bh.labelNames.split(',').indexOf('明星')==-1
                                            && bh.labelNames.split(',').indexOf('电影')==-1
                                            && bh.labelNames.split(',').indexOf('电视剧')==-1
                                            && bh.labelNames.split(',').indexOf('综艺')==-1
                                            && bh.labelNames.split(',').indexOf('文化')==-1
                                            && bh.labelNames.split(',').indexOf('小事件')==-1
                                             && 30 <= (bh.ratioHotDay).toFixed(2)  && bh.ratioHotTopCustom >=60">爆</span>
                                    <span class="wui-badge wui-badge-blue" v-if="
                                             bh.labelNames.split(',').indexOf('明星')==-1
                                            && bh.labelNames.split(',').indexOf('电影')==-1
                                            && bh.labelNames.split(',').indexOf('电视剧')==-1
                                            && bh.labelNames.split(',').indexOf('综艺')==-1
                                            && bh.labelNames.split(',').indexOf('文化')==-1
                                            && bh.labelNames.split(',').indexOf('小事件')==-1
                                            && 5 <= (bh.ratioHotDay).toFixed(2)
                                            && (bh.ratioHotDay).toFixed(2)< 10
                                            && 'bh.createTime | del()' < '2'">新</span>
                                </p>
                            </div>
                            <div class="wui-item-right">
                                <p class="fontColor_0 font-size-14 abs" style="top: 8px"><span class="wui-badge-outline" v-text="bh.labelNames"></span><span class="margin-left-10"><i class="iconfont icon-dingwei-copy margin-right-3"></i>{{bh.province}}</span></p>
                                <div class="text-right">
                                    <span class="fontColor_0">热度指数</span>
                                    <span class="num" v-text="parseFloat(bh.ratioHotDay).toFixed(2)"></span>
                                    <i class="iconfont icon-shangsheng1 fontColor_2 font-size-18" v-if="parseFloat(bh.differenceDay).toFixed(2)>0"></i>
                                    <i class="iconfont icon-xiajiang1 fontColor_3 font-size-18" v-if="parseFloat(bh.differenceDay).toFixed(2)<0"></i>
                                    <i class="iconfont icon-chiping fontColor_4 font-size-18" v-if="parseFloat(bh.differenceDay).toFixed(2)==0"></i>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-item fade">
                    <a class="wui-panel-body big">
                        <p class="font-size-10 fontColor_0 margin-top-15 margin-bottom-10 "> 统计时间：<label v-text="bigEventStartTime"></label>~<label v-text="bigEventEndTime"></label></p>

                        <a class="wui-panel-item"  v-for="(bigEvent,index) in allBigEventList" @click="getBigEventDetail(bigEvent.id)">
                            <div class="wui-item-left padding-right-20">
                                <p class="margin-bottom-15"><span class="vertical-align-middle" v-text="index+1+'.'"></span> <span class="title nowrap" style="width: 80%" v-text="bigEvent.name"></span></p>
                                <p class="fontColor_0 font-size-12"><span>7日内包含事件数：</span><span class="fontColor_1" v-text="bigEvent.eventNum"></span><span class="margin-left-30">24小时总信息量：
                                            <span class="fontColor_1" v-if = "bigEvent.numberDay >= 10000" v-text="(bigEvent.numberDay / 10000).toFixed(2) + 'W'"></span>
                                            <span class="fontColor_1" v-else = "bigEvent.numberDay < 10000" v-text="bigEvent.numberDay"></span>
                                        </span>
                                </p>
                            </div>
                            <div class="wui-item-right abs_icon">
                                <i class="iconfont icon-you color_1"></i>
                            </div>
                        </a>
                </div>
            </div>
        </section>
    <!-- 热门事件 end -->
    <!-- 解决方案 start -->
    <section class="wui-panel">
        <div class="wui-panel-head">
            <h3>解决方案</h3>
        </div>
        <div class="wui-row clearfix line-outline">
            <div class="col-sm-4 wui-wedge">
                <p>舆</p>
                <span>新浪舆情通</span>
            </div>
            <div class="col-sm-4 wui-wedge">
                <p>铀</p>
                <span>铀媒</span>
            </div>
            <div class="col-sm-4 wui-wedge">
                <p>行</p>
                <span>行业解决方案</span>
            </div>
        </div>
        <div class="wui-row clearfix margin-bottom-80">
            <div class="col-sm-6 wui-wedge border-after">
                <div>
                    <i class="iconfont icon-diannao" style="font-size: 45px;color: #8F9DBA;"></i>
                </div>
                <span style="color: #FFFFFF;margin-bottom: 5px">微热点电脑版</span>
                <a href="javascript:;" class="font-size-12 block" style="color: #FFFFFF">www.wrd.cn</a>
            </div>
            <div  class="col-sm-6 wui-wedge" @click="downloadApp">
                <div>
                    <i class="iconfont icon-shouji1" style="font-size: 45px;color: #8F9DBA;"></i>
                </div>
                <span style="color: #FFFFFF;margin-bottom: 5px">微热点APP</span>
                <a href="javascript:;" class="fontColor_1 font-size-12 block">立即下载</a>
            </div>
        </div>
    </section>
            <span  v-if="isShowBar">
                <div class="wui-tabbar wui-tabbar-fixed">
                <a class="wui-tabbar-item wui-tabbar-item-active" href="${njxBasePath}/home">
                    <div class="wui-tabbar-item_icon">
                    </div>
                    <div class="wui-tabbar-item_text">首页</div>
                </a>
                <a class="wui-tabbar-item" href="${njxBasePath}/infoData">
                    <div class="wui-tabbar-item_icon wui-icon-data">
                    </div>
                    <div class="wui-tabbar-item_text">大数据报告</div>
                </a>
                <a class="wui-tabbar-item" href="${njxBasePath}/analysis">
                    <div class="wui-tabbar-item_icon wui-icon-analysis">
                    </div>
                    <div class="wui-tabbar-item_text">热点分析</div>
                </a>
                <a class="wui-tabbar-item" href="${njxBasePath}/uMedia">
                    <div class="wui-tabbar-item_icon wui-icon-um">
                    </div>
                    <div class="wui-tabbar-item_text">新媒体运营</div>
                </a>
                <a class="wui-tabbar-item" href="${njxBasePath}/user">
                    <div class="wui-tabbar-item_icon wui-icon-my">
                    </div>
                    <div class="wui-tabbar-item_text">我</div>
                </a>
            </div>
            </span>
</div>
            <!--温馨提示-->
            <div id="wui-modal-tootips" class="wui-modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="wui-modal-body text-center">
                            <a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
                            <h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">温馨提示</h3>
                            <p class="font-size-12 color_1">您好，双向筛选属于定制服务，请联系客服进行地域+热点类型的定制服务。</p>
                        </div>

                        <div class="modal-footer clearfix">
                            <a href="javascript:;" class="btn" data-dismiss="modal">确定</a>
                            <a href="javascript:;" class="btn fontColor_1"  onclick="window.open('http://wpa.qq.com/msgrd?v=3&amp;uin=3002432217&amp;site=qq&amp;menu=yes','_blank')">联系客服</a>
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
                            <p class="font-size-12 color_1"> 您查询的：'<label v-text="keyword"></label>' 详情数据，需升级到专业版查看，可以联系客服小姐姐哦～</p>
                        </div>
                        <div class="modal-footer clearfix">
                            <a href="javascript:;" class="btn" data-dismiss="modal">确定</a>
                            <a href="javascript:;" class="btn fontColor_1"  onclick="window.open('http://wpa.qq.com/msgrd?v=3&amp;uin=3002432217&amp;site=qq&amp;menu=yes','_blank')">联系客服</a>
                        </div>
                    </div>
                </div>
            </div>
</div>


<script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/vue/moment.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/newHome/mui.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/newHome/mui.picker.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/newHome/mui.poppicker.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/newHome/city.data.js?v=${SYSTEM_INIT_TIME}" type="text/javascript" charset="utf-8"></script>
<script src="${staticResourcePathH5}/js/swiper.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/vue/vue.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/vue/home/hotEvent.js?v=${SYSTEM_INIT_TIME}"></script>
<script>




    $("#getSearch").bind("search", function() {
         app.searchEnter();
    });

    //选择器
    mui.init();
    mui.ready(function () {
        mui('.wui-dropdown').on('tap','a',function () {
            this.classList.add('active');
        })
        var hotPicker = new mui.PopPicker();
        hotPicker.setData(app.hotLabelData);
        var showHotPickerButton = document.getElementById('showHotPicker');
        var hotResult = document.getElementsByClassName("hotResult");
        showHotPickerButton.addEventListener('tap', function(event) {
            if ( app.province1 != "全部" && app.province1 != "" && app.province1 != "地域"){
                $('#wui-modal-tootips').modal('show')
                // swal("您好，目前不支持双向选择! 如有疑问请联系客服");
            }else {
                hotPicker.setData(app.hotLabelData);
                hotPicker.show(function(items) {
                    hotResult[0].innerText = items[0].text;
                    app.labels1 = items[0].value;
                    app.getHotEventList();
                    app.getBigEventList();
                    app.getHotEventCount();
                });
            }
        }, false);
        //级联示例
        var cityPicker = new mui.PopPicker({
            layer: 1
        });
        cityPicker.setData(cityData2);
        var showCityPickerButton = document.getElementById('showCityPicker');
        var cityResult = document.getElementsByClassName('cityResult');
        showCityPickerButton.addEventListener('tap', function(event) {

            if (app.labels1 != "0" && app.labels1 != ""){
                $('#wui-modal-tootips').modal('show')
                // swal("您好，目前不支持双向选择! 如有疑问请联系客服");
            }else {
                cityPicker.show(function(items) {
                    cityResult[0].innerText = items[0].text;
                    app.province1 = items[0].text;
                    // app.city = items[1].text;
                    app.getHotEventList();
                    app.getBigEventList();
                    app.getHotEventCount();
                });
            }
        }, false);
    })
</script>
</body>
</html>
