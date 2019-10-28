<!DOCTYPE html>
<html lang="">
<head>
    <#include "../../init_top.ftl" >
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css"/>
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/mui/mui.min.css?v=${SYSTEM_INIT_TIME}"/>
    <link rel="stylesheet" href="${staticResourcePathH5}/css/newHome/swiper.min.css">
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css" />
    <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/modal.css?v=${SYSTEM_INIT_TIME}" />
    <script src="${staticResourcePathH5}/js/newHome/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>
    <title>微热点</title>
    <style>

    </style>
</head>
<body class="mainBackground rel">
<div id="app">

    <div class="wui-bar wui-tag">
        <a href="javascript:history.go(-1)" class="wui-back mui-action-back wui-icon-nav wui-pull-left iconfont icon-fanhui">
        </a>
        <a href="javascript:;" class="wui-icon-nav wui-pull-right font-size-12 fontColor_0 padding-15">
            统计时间：${(sTime)!""}~${(eTime)!""}
        </a>
    </div>
    <div class="wui-content rel">

        <div class="barBackground">
            <h3 class="wui-title">${(data.name)!""}</h3>
            <p class="fontColor_0 font-size-12 margin-left-25 padding-bottom-25 padding-top-10">7日内包含事件数：<span class="fontColor_1">${(data.eventNum)!""}</span><span class="margin-left-15">24小时总信息量：</span><span class="fontColor_1">${(data.numberDay)!""}</span></p>
        </div>
        <!--列表-->
        <section class="">
            <div class="wui-list">
                <ul>

                    <#list  (data.hotIncidentList)! as item>
                        <li class="wui-list-item"  >
                            <a href="javascript:void(0)" onClick="app.getSearchDetail('${(item.incidentTitle)!""}','${(item.keyword)!""}','${(item.filterKeyword)!""}');">
                                <div class="tit nowrap">${(item.incidentTitle)!""}</div>
                                <p class="margin-bottom-15">信息总量：<span class="font-size-18 font-weight-600 fontColor_1">${(item.numberDay)!0}</span><span class="margin-left-35">敏感信息量：</span><span class="font-size-18 font-weight-600 fontColor_1">${(item.mgNumberDay)!0}</span></p>
                                <p><i class="iconfont icon-dingwei-copy margin-right-3"></i>${(item.province)!""}</p>
                                <span class="iconfont icon-you jiantou"></span>
                            </a>
                        </li>
                    </#list>

                </ul>
            </div>
        </section>
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

<script src="${staticResourcePathH5}/js/vue/vue.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/vue/moment.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/vue/home/hotSearchBase.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/newHome/goToTop.js?v=${SYSTEM_INIT_TIME}"></script>





<script>
    $(function () {
        $('.wui-tabs li').on("click",function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $(".tab-content .tab-item").eq(index).addClass('in active').siblings().removeClass('in active');
        })
        //        说明文字显示隐藏
        $(".wui-tipinfo-icon").on("click", function() {
            $(this).parents(".wui-head").next(".wui-tipinfo").toggle(300)
        });
        $(".wui-tipinfo .wui-tipinfo-close").on("click", function() {
            $(this).parents(".wui-tipinfo").hide(300)
        });

        $(".wui-tipinfo .wui-tipinfo-close").on("click", function() {
            $(this).parents(".wui-tipinfo").hide(300)
        });

        var goToTopButton = "<div id=\"wui-back-top\">\n" +
            "        <i class=\"iconfont icon-fanhuidingbu1\"></i>\n" +
            "    </div>";
        $("body").append(goToTopButton); //插入按钮的html标签
        if($(window).scrollTop() < 1) {
            $("#wui-back-top").hide(); //滚动条距离顶端的距离小于showDistance是隐藏goToTop按钮
        }
        $("#wui-back-top").goToTop({
            min: 1,
            fadeSpeed: 500
        });
        $("#wui-back-top").on('click', function(e) {
            e.preventDefault();
            $("html,body").animate({
                scrollTop: 0
            }, "slow");

        });

    })

    var common_keyword_filter = /^[/0-9a-zA-Z\u4e00-\u9fa5\s\.\_\-\[\]@#_《》']+$/;
    var common_keyword_char_filter = /[\s\.\-\[\]@#_《》']/g;

    function checkKeywordFilter(keyword){

        if(keyword == null || keyword == ''){
            return false;
        }
        var newKeywords = $.trim(keyword);
        newKeywords = newKeywords.split(' ').join('');
        newKeywords = newKeywords.split('+').join('');
        newKeywords = newKeywords.split('|').join('');
        newKeywords = newKeywords.split('(').join('');
        newKeywords = newKeywords.split(')').join('');
        if(newKeywords == ''){
            return false;
        }
        if(!common_keyword_filter.test(newKeywords)){
            return false;
        }
        newKeywords = newKeywords.replace(common_keyword_char_filter, '');
        if(newKeywords.length == 0){
            return false;
        }
        return true;
    };





</script>
</body>
</html>
