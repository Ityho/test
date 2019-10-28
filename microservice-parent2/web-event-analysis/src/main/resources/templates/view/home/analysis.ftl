<#include "../../init_top.ftl" >
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css?v=${SYSTEM_INIT_TIME}"/>
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css?v=${SYSTEM_INIT_TIME}" />
<script src="${staticResourcePathH5}/js/newHome/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>
<title>微热点——用大数据洞察传播</title>
</head>
<body class="mainBackground">
<div class="rel margin-bottom-50">
    <!--列表-->
    <section class="wui-analysis">
        <ul>
            <li class="wui-analysis-item wui-aitem_1">
                <div>
                    <h3 class="wui-aitem-title"> 竞品分析</h3>
                    <p class="wui-aitem-body"><a href="compet/productsReport">我的报告(${productCount!0})</a>
                        <a href="javascript:location.href ='${basePath}/compet/lookShareCodeReport?shareCode=fQodqoXdeqGixtI&isApp=1';" class="margin-left-20">查看案例</a></p>
                </div>
                <a class="fontColor_1 font-size-14" href="${basePath}/compet/productsAnalysis">
                    创建分析 <i class="iconfont icon-you font-size-12"></i>
                </a>

            </li>
            <li class="wui-analysis-item wui-aitem_2">
                <div>
                    <h3 class="wui-aitem-title">全网事件分析</h3>
                    <p class="wui-aitem-body">
                        <a href="completeList">我的报告(${eventCount!0})</a>
                        <a onclick="javascript:location.href ='${basePath}/analysisPreviewDemo?tickets=CTjV50Dct6qV0EAn_pZvrGWVFEDDISYY';" class="margin-left-20">查看案例</a>
                    </p>
                </div>
                <a class="fontColor_1 font-size-14" href="${basePath}/createAnalysis">
                    创建分析 <i class="iconfont icon-you font-size-12"></i>
                </a>

            </li>
            <li class="wui-analysis-item wui-aitem_3">
                <div>
                    <h3 class="wui-aitem-title">微博事件分析</h3>
                    <p class="wui-aitem-body">
                        <a href="weibo/weiboCompleteList">我的报告(${weiboEventCount!0})</a>
                        <a onclick="javascript:location.href ='${basePath}/weibo/analysisPreviewDemo?tickets=CTjV50Dct6pwRwmgkB_2JtOFHMDTQIlK';" class="margin-left-20">查看案例</a>
                    </p>
                </div>
                <a class="fontColor_1 font-size-14" href="${basePath}/weibo/createWeiBoAnalysis?createType=1">
                    创建分析 <i class="iconfont icon-you font-size-12"></i>
                </a>

            </li>
            <li class="wui-analysis-item wui-aitem_4">
                <div>
                    <h3 class="wui-aitem-title">微博传播效果分析</h3>
                    <p class="wui-aitem-body"><a href="weiboAnalysis/history">我的报告(${singleWeiboCount!0})</a>
                        <a onclick="javascript:location.href ='${basePath}/weiboAnalysis/demo?tickets=w1wyqwfxwyq14378190911141628347';" class="margin-left-20">查看案例</a>
                </div>
                <a class="fontColor_1 font-size-14" href="${basePath}/weiboAnalysis/weiboAnalysisIndex">
                    创建分析 <i class="iconfont icon-you font-size-12"></i>
                </a>

            </li>
        </ul>
    </section>
</div>
<!--底部导航-->
<div class="wui-tabbar wui-tabbar-fixed">
    <a class="wui-tabbar-item" href="${njxBasePath}/home">
        <div class="wui-tabbar-item_icon">
        </div>
        <div class="wui-tabbar-item_text">首页</div>
    </a>
    <a class="wui-tabbar-item" href="${njxBasePath}/infoData">
        <div class="wui-tabbar-item_icon wui-icon-data">
        </div>
        <div class="wui-tabbar-item_text">大数据报告</div>
    </a>
    <a class="wui-tabbar-item wui-tabbar-item-active" href="${njxBasePath}/analysis">
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
<script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script>
    var contextPath= "${request.contextPath}";
</script>
</body>
</html>
