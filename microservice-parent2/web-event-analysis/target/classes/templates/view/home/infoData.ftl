<#include "../../init_top.ftl" >
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
		<link rel="stylesheet" href="${staticResourcePathH5}/css/newHome/swiper.min.css">
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css" />
        <script src="${staticResourcePathH5}/js/newHome/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>

		<title>微热点——用大数据洞察传播</title>
		<style>
            [v-cloak] {
                display: none !important;
            }
        </style>
	</head>
	<body class="mainBackground" >
    <div id="app">
        <!--        tab bar-->
        <ul class="wui-tabs wui-segmented-control2  clearfix">
        <#--                <li :class="activeClass == item.type ? 'active':''" class="active" v-for="(item,index) in typeList"><a href="javascript:;" v-text="item.value" ></a></li>-->
            <li :class="activeClass == item.type ? 'active':''"  v-for="(item,index) in typeList" @click="searchRefresh(index,item)"><a href="javascript:;" v-text="item.value" ></a></li>
        <#--                <li><a href="javascript:;">时事</a></li>-->
        <#--                <li><a href="javascript:;">社会</a></li>-->
        <#--                <li><a href="javascript:;">体育</a></li>-->
        <#--                <li><a href="javascript:;">科技</a></li>-->
        <#--                <li><a href="javascript:;">娱乐</a></li>-->
        </ul>
        <div class="rel margin-bottom-50" v-cloak>
            <!--列表-->
            <section class="tab-content">
                <div class="tab-item in active">
                    <div class="wui-infoList">
                        <ul>
                            <li class="wui-infoList-item" v-for="(item,index) in list"  v-cloak  >
                                <a :href="'getBigDataDetail?id='+item.id"></a>
                                <div>
                                    <div class="tit" v-text="item.title"></div>
                                    <p><span class="wui-badge-outline" v-text="item.typeValue"></span><span class="float_r" v-cloak>{{item.createTime | formatDate('YYYY年MM月DD日')}}</span></p>
                                </div>
                                <div class="rel">
                                    <img v-bind:src="item.imageUrl" alt="">
                                    <span v-if="item.packagePrice!=0&&item.packagePrice!=null&&idList.indexOf(item.id)==-1" class="wui-pay-state font-size-12 stateColor_2">付费</span>
                                </div>
                            </li>
                        </ul>

                        <div id="d_loading" class="text-center margin-vertical-10" style="color: #8F9DBA">
                            <div class="inline-block w-ball-clip-rotate la-sm v_a_m margin-right-5"><div></div></div><span class="v_a_m">加载中</span>
                        </div>

                        <p id="getButtom" style="display: none" class="wui-end" v-cloak>我们也是有底线的哦~</p>
                    </div>
                </div>

            </section>
        </div>
        <!--底部导航-->
        <div class="wui-tabbar wui-tabbar-fixed">
            <a class="wui-tabbar-item" href="${njxBasePath}/home">
                <div class="wui-tabbar-item_icon">
                </div>
                <div class="wui-tabbar-item_text">首页</div>
            </a>
            <a class="wui-tabbar-item wui-tabbar-item-active" href="${njxBasePath}/infoData">
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
    </div>
    <script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/vue/moment.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/echarts/echarts.min.js"></script>
    <#--        <script src="${staticResourcePathH5}/js/newHome/searchEchart.js"></script>-->
    <script src="${staticResourcePathH5}/js/vue/vue.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/utils/utils.js"></script>
    <script src="${staticResourcePathH5}/js/jquery/jquery.endless-scroll-1.3.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/vue/home/bigData.js"></script>

    <script>
        $(function () {
            $('.wui-segmented-control2 li').on("click",function () {
                $(this).addClass('active').siblings().removeClass('active');
                var index = $(this).index();
                $(".tab-content .tab-item").eq(0).addClass('in active').siblings().removeClass('in active');
            })
        })
    </script>
    </body>
</html>
