<#include "../../init_top.ftl" >
	<title>案例库</title>
	<link rel="stylesheet" href="${staticResourcePathH5}/caseBase/css/weui.css?v=${SYSTEM_INIT_TIME}">
	<link rel="stylesheet" href="${staticResourcePathH5}/caseBase/css/common.css?v=${SYSTEM_INIT_TIME}">
	<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/caseBase/css/iconfont.css?v=${SYSTEM_INIT_TIME}"/>
	<link rel="stylesheet" href="${staticResourcePathH5}/caseBase/css/style.css?v=${SYSTEM_INIT_TIME}">
	<script src="${staticResourcePathH5}/caseBase/js/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>
	<style>
        .err {
            font-size: 0.9375rem;
            color: #999999;
            padding-top: 5.625rem;
        }

        .err img {
            width: 8.125rem;
            height: 4.375rem;
        }


    </style>
</head>

<body>
<div id="app">
    <div class="container">
        <div class="page-head">
            <!--logo-->
            <div class="logo">
                <img src="${staticResourcePathH5}/caseBase/images/logo-big.png" alt="" /><span>案例库</span>
            </div>
            <div class="search_wrap weui-flex" >
                <div class="search_box" >
                    <!--<icon class="icon-search iconfont icon-xiazai5"></icon>-->
                    <input id="title" type="search" placeholder="搜索事件名称或关键词" class="input" size="14"></input>
                </div>
                <div v-show="clo" class="search-cancel" @click="goClose()" >取消</div>
            </div>
            <div>
                <div class="nav nav-scroll-x">
                    <div class="nav-scroll-item" v-for="(item,ind) in navData" :data-current="ind" :index="ind"
                         :class="currentTab == ind ?'active':''" @click="switchNav(item.id,ind)">
                        {{item.text}}
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-content">
            <block v-if="errShow">
                <div class="panel-title">
                    <div class="panel-title-h"> 热度榜单</div>
                    <div class="panel-head-action">
                        <button class="weui-btn label-btn-cir active" v-if="order===2&&sort=== 1" :data-index="1"
                                @click="reloadData(2,2)">
                            时间降序
                        </button>
                        <button class="weui-btn label-btn-cir active" v-if="order===2&&sort=== 2" :data-index="1"
                                @click="reloadData(2,1)">
                            时间升序
                        </button>
                        <button class="weui-btn label-btn-cir" v-if="order===1" :data-index="1"
                                @click="reloadData(2,1)">
                            时间降序
                        </button>
                        <button class="weui-btn label-btn-cir active" v-if="order===1&&sort === 1" :data-index="2"
                                @click="reloadData(1,2)">
                            热度降序
                        </button>
                        <button class="weui-btn label-btn-cir active" v-if="order===1&&sort === 2" :data-index="2"
                                @click="reloadData(1,1)">
                            热度升序
                        </button>
                        <button class="weui-btn label-btn-cir" v-if="order===2" :data-index="2"
                                @click="reloadData(1,1)">
                            热度降序
                        </button>
                    </div>
                </div>
                <div v-cloak >

                    <div v-if="item.eventLabel != 18" v-for="(item, index) in listData" :index="index">
                        <div >
                            <div class="media" hover-class="bg_01" @click="ontip(item.id)">
                                <div class="wui-media-item">
                                    <div class="wui-media-item-info nowrap">
                                        {{item.title}}
                                    </div>
                                    <div class="text-right rel">
                                        <div class="wui-media-item-action">
                                            <div class="badge badge-color1" v-if="item.eventLabel === 1">
                                                时事
                                            </div>
                                            <div class="badge badge-color2" v-if="item.eventLabel === 2">
                                                社会
                                            </div>
                                            <div class="badge badge-color3" v-if="item.eventLabel === 3">
                                                体育
                                            </div>
                                            <div class="badge badge-color4" v-if="item.eventLabel === 4">
                                                科技
                                            </div>
                                            <div class="badge badge-color5" v-if="item.eventLabel === 5">
                                                灾害
                                            </div>
                                            <div class="badge badge-color6" v-if="item.eventLabel === 6">
                                                娱乐
                                            </div>
                                            <div class="badge badge-color7" v-if="item.eventLabel === 7">
                                                人物
                                            </div>
                                            <div class="badge badge-color8" v-if="item.eventLabel === 8">
                                                财经
                                            </div>
                                            <div class="badge badge-color9" v-if="item.eventLabel === 9">
                                                房产
                                            </div>
                                            <div class="badge badge-color10" v-if="item.eventLabel === 10">
                                                金融
                                            </div>
                                            <div class="badge badge-color11" v-if="item.eventLabel === 11">
                                                医疗
                                            </div>
                                            <div class="badge badge-color12" v-if="item.eventLabel === 12">
                                                教育
                                            </div>
                                            <div class="badge badge-color13" v-if="item.eventLabel === 13">
                                                文化
                                            </div>
                                            <div class="badge badge-color14" v-if="item.eventLabel === 14">
                                                汽车
                                            </div>
                                            <div class="badge badge-color15" v-if="item.eventLabel === 15">
                                                旅游
                                            </div>
                                            <div class="badge badge-color16" v-if="item.eventLabel === 16">
                                                政务
                                            </div>
                                            <div class="badge badge-color17" v-if="item.eventLabel === 17">
                                                法治
                                            </div>
                                            <div class="badge badge-color18" v-if="item.eventLabel === 19">
                                                突发
                                            </div>
                                            <span class="media-item-time">{{item.startTimeStr}}</span>
                                        </div>
                                        <div>
                                            <div class="fz12 fc_9 inline-block">热度</div>
                                            <div class="media-item-index">{{item.hotValue}}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="md-tips">
                        <div v-if="hasMore">
                            <a  href="javascript:;" @click="getServerData()">查看更多</a>
                            <!-- <image class="md-tips-image" src="images/loading.gif" mode="aspectFill"></image>
                            <text class="md-tips-text">正在加载...</text> -->
                        </div>
                        <div v-else>
                            <div class="fz14 text-center">没有更多数据</div>
                        </div>
                    </div>


                </div>
            </block>
            <div class="text-center err" v-else>
                <img src="${staticResourcePathH5}/caseBase/images/404.png" alt="">
                <div class="margin-top-30">网络开小差啦</div>
                <div>刷新一下试试吧</div>
            </div>
        </div>

    </div>
</div>
<script src="${staticResourcePathH5}/js/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/caseBase/js/vue.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/caseBase/js/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/vue/casebase/index.js?v=${SYSTEM_INIT_TIME}"></script>
<script>
    $("#title").bind("search", function() {
        var target = $(this);
        // $(".wui-shade").hide();
        app.clo=true;
        target.parent().removeClass('wui-flex-search')

        app.searchClick();

    });
</script>
</body>
</html>