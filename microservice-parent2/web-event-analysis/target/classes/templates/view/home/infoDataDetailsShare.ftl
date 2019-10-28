<#include "../../init_top.ftl" >
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/mui/mui.min.css?v=${SYSTEM_INIT_TIME}"/>
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css" />

        <script src="${staticResourcePathH5}/js/newHome/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>
		<title>微热点——用大数据洞察传播</title>
        <style>
            <style type="text/css">
              .article-body span{
                  background: transparent !important;
                  color: #FFFFFF !important;
              }
                .article-body img{
                    width: 100% !important;
                }
        </style>

</style>
	</head>
	<body class="mainBackground">
    <div class="wui-bar wui-tag" style="background-color: #1B172C;">
        <a href="javascript:history.go(-1)" class="wui-back mui-action-back wui-icon-nav wui-pull-left iconfont icon-fanhui">
        </a>

	<div class="rel wui-content margin-bottom-50">
            <!--列表-->
            <section>
                <div class="article-content">
                    <div class="article-title">
                        ${(adminWb.title)!""}
                    </div>
                    <p class="article-act"><span>微热点</span> <span class="margin-left-20">${adminWb.createTime?string('MM-dd hh:mm')}</span></p>
                    <div class="article-body" id="article-body">
                        <#--&nbsp;&nbsp;${(adminWb.content)!""}-->
                    </div>
                </div>
            </section>
		</div>

          
<!--导航-->
		<div class="wui-tabbar wui-tabbar-fixed">
                <a class="wui-tabbar-item" href="home.html">
                    <div class="wui-tabbar-item_icon">
                    </div>
                    <div class="wui-tabbar-item_text">首页</div>
                </a>
                <a class="wui-tabbar-item" href="infoData.html">
                    <div class="wui-tabbar-item_icon wui-icon-data">
                    </div>
                    <div class="wui-tabbar-item_text">大数据报告</div>
                </a>
                <a class="wui-tabbar-item" href="analysis.html">
                    <div class="wui-tabbar-item_icon wui-icon-analysis">
                    </div>
                    <div class="wui-tabbar-item_text">热点分析</div>
                </a>
                <a class="wui-tabbar-item" href="uMedia">
                    <div class="wui-tabbar-item_icon wui-icon-um">
                    </div>
                    <div class="wui-tabbar-item_text">新媒体运营</div>
                </a>
                <a class="wui-tabbar-item" href="user.html">
                    <div class="wui-tabbar-item_icon wui-icon-my">
                    </div>
                    <div class="wui-tabbar-item_text">我</div>
                </a>
            </div>



<!--    返回顶部-->
<!--    <div id="wui-back-top">-->
<!--        <i class="iconfont icon-fanhuidingbu1"></i>-->
<!--    </div>-->
        <script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/newHome/goToTop.js?v=${SYSTEM_INIT_TIME}"></script>
        <script  src="${staticResourcePathH5}/js/newHome/mui.min.js?v=${SYSTEM_INIT_TIME}"> </script>
		<script>
            $(function(){
                var goToTopButton = "<div id=\"wui-back-top\">\n" +
                    "        <i class=\"iconfont icon-fanhuidingbu1\"></i>\n" +
                    "    </div>";
                $("body").append(goToTopButton);  //插入按钮的html标签
                if($(window).scrollTop() < 1) {
                    $("#wui-back-top").hide();//滚动条距离顶端的距离小于showDistance是隐藏goToTop按钮
                }
                $("#wui-back-top").goToTop({
                    min:1,
                    fadeSpeed: 500
                });
                $("#wui-back-top").on('click',function(e){
                    e.preventDefault();
                    $("html,body").animate({scrollTop:0},"slow");
                });
            });
            var content='${(adminWb.content)!""}';
            var new_content=content.replace(/<(?!img|p|\/p).*?>/g, "");
            $("#article-body").html(new_content);
		</script>
	</body>
</html>
