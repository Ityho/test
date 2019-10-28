<#include "../../init_top.ftl" >
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/mui/mui.min.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css" />
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/modal.css"/>
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
	</head>
	<body  class="mainBackground">
    <div class="wui-bar wui-tag" style="background-color: #1B172C;">
        <a href="javascript:history.go(-1)" class="wui-back mui-action-back wui-icon-nav wui-pull-left iconfont icon-fanhui">
        </a>
    </div>
    <input type="hidden" id="packagePrice" value="${adminWb.packagePrice}"/>
    <input type="hidden" id="credit" value="${credit}"/>
    <input type="hidden" id="eventLabel" value="${adminWb.eventLabel}"/>
    <div id="app" class="rel wui-content margin-bottom-50">
        <!--列表-->
        <section>
            <div class="article-content">
                <div class="article-title">
                ${(adminWb.title)!""}
                </div>
                <p class="article-act"><span>微热点</span> <span class="margin-left-20">${adminWb.createTime?string('MM-dd hh:mm')}</span></p>
                    <#if (adminWb.packagePrice!=0)>
                        <div v-if="dataShow==false" class="article-body article-lock" id="article-body">
                        <#--&nbsp;&nbsp;${(adminWb.content)!""}-->
                        </div>
                    </#if>
                    <#if (adminWb.packagePrice!=0)>
                        <div v-if="dataShow==true" class="article-body" id="article-body1">
                        <#--&nbsp;&nbsp;${(adminWb.content)!""}-->
                        </div>
                        <div v-show="dataShow" class="wui-endline margin-vertical-20">
                            <span>此报告可以去微热点网页版www.wrd.cn下载哦</span>
                        </div>
                    </#if>
                    <#if (adminWb.packagePrice==0)>
                        <div v-if="dataShow==false" class="article-body" id="article-body2">
                        <#--&nbsp;&nbsp;${(adminWb.content)!""}-->
                        </div>
                    </#if>
            <#--<div class="wui-endline margin-vertical-20">-->
            <#--<span>此报告可以去微热点网页版www.wrd.cn下载哦</span>-->
            <#--</div>-->
            </div>
            <!--price-->
                <#if (adminWb.packagePrice!=0)>
                  <div v-show="!dataShow" class="article-mask-bottom">
                      <div class="inner">
                          <p class="price">${adminWb.packagePrice}元/份</p>
                          <a href="javascript:;" onclick="goPayreport()" class="btn btn-warning-o btn-big btn-article-buy">购买即可享有完整报告</a>
                          <p class="color_1 font-size-12 margin-top-10">报告类型：<span class="margin-left-5">${adminWb.typeValue}</span><span class="margin-left-20">已售次数：<span class="margin-left-5">${adminWb.readNumber}次</span></span>
                          </p>
                      </div>
                  </div>
                </#if>

        </section>
        <section>
            <div class="wui-card">
                <div class="wui-card-title">
                    <h3>同类型热点事件TOP5</h3>
                </div>
                <div class="wui-card-content">
                    <ul class="infoTop">
                        <li v-for="(item,index) in newLike">
                        <#--<span v-if="item.packagePrice!=0&&item.packagePrice!=null&&idList.indexOf(item.id)==-1" class="wui-pay-state font-size-12 stateColor_2">付费</span>-->
                            <a :href="'getBigDataDetail?id='+item.id">
                                <span class="infoTop-num" v-text="index+1">01</span>
                                <div class="nowrap infoTop-txt" v-text="item.title">川航“史诗级”备降</div>
                                <i class="iconfont icon-you"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </section>
        <!--弹框微积分支付提示-->
        <div id="wjf-tootips" class="wui-modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="wui-modal-body text-center">
                        <a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
                        <h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">温馨提示</h3>
                        <p class="font-size-12 color_1">当前可用微积分为 <span class="fontColor_1" id="zCredit">${credit}</span></p>
                        <p class="font-size-12 color_1">本次购买需花费  <span class="fontColor_1" id="pCredit">9999</span></p>
                    </div>

                    <div class="modal-footer clearfix">
                        <a href="javascript:;" class="btn" data-dismiss="modal">取消</a>
                        <a href="javascript:;" @click="buyBigDate()" class="btn fontColor_1">确认支付</a>
                    </div>
                </div>
            </div>
        </div>
        <!--弹框微积分不够了-->
        <div id="pay-wjf" class="wui-modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="wui-modal-body text-center">
                        <a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
                        <h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">微积分不够啦</h3>
                        <p class="font-size-12 color_1">当前可用微积分为 <span class="fontColor_1">${credit}</span></p>
                        <p class="font-size-12 color_1">还需要 <span class="fontColor_1" id="shortCredit">9999</span>才能购买</p>
                    </div>

                    <div class="modal-footer clearfix">
                        <a href="javascript:;" class="btn" data-dismiss="modal">取消</a>
                        <a href="${njxBasePath}/userCenter/goBuy?type=0" class="btn fontColor_1">去充值</a>
                    </div>
                </div>
            </div>
        </div>
        <!--弹框支付成功-->
        <div id="pay-success" class="wui-modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="wui-modal-body text-center">
                        <a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
                        <h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">支付成功啦</h3>
                        <p class="font-size-12 color_1">您的深度报告已经制作成功</p>
                        <p class="font-size-12 color_1">可前往“<span  class="fontColor_1">我-大数据深度报告</span>”查看</p>
                    </div>

                    <div class="modal-footer clearfix">
                        <a href="javascript:;" class="btn" data-dismiss="modal" >取消</a>
                        <a href="${njxBasePath}/infoDataReport" @click="goBuyed()" class="btn fontColor_1" >立即查看</a>
                    </div>
                </div>
            </div>
        </div>
        <form  name="payForm" method="post" id = "selfProductForm">
            <input id = "payType" type="hidden" name="payType" value="00"/>
            <#if (adminWb.packagePrice==399)>
                <input id = "productPackageId"  type="hidden" name="myProducts['product68'].keywordPackageNum"  value="1" />
            <#elseif (adminWb.packagePrice==299)>
                <input id = "productPackageId"  type="hidden" name="myProducts['product69'].keywordPackageNum"  value="1" />
            <#else>
                <input id = "productPackageId"  type="hidden" name="myProducts['product70'].keywordPackageNum"  value="1" />
            </#if>

        <#--<input id = "productFlag"  type="hidden" name="productFlag" value="product1002" />-->
            <input id = "useCredit1" type="hidden" name="useCredit" value="true"/>
            <input id = "orderType" type="hidden" name="orderType" value="1"/>
            <input id = "orderType" type="hidden" name="orderType" value="1"/>
            <input type="hidden" id="infoDataId" name="infoDataId" value="${adminWb.id}"/>
        </form>
    </div>
    <div id="d_loading" class="text-center margin-vertical-10" style="color: #8F9DBA">
        <div class="inline-block w-ball-clip-rotate la-sm v_a_m margin-right-5"><div></div></div><span class="v_a_m">加载中</span>
    </div>
    <div id="getButtom" style="display: none" class="endline padding-vertical-20">
        <span>我们是有底线的</span>
    </div>
    <!--    返回顶部-->
    <!--    <div id="wui-back-top">-->
    <!--        <i class="iconfont icon-fanhuidingbu1"></i>-->
    <!--    </div>-->
    <script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/bootstrap.min.js"></script>
    <script src="${staticResourcePathH5}/js/newHome/goToTop.js?v=${SYSTEM_INIT_TIME}"></script>
    <script  src="${staticResourcePathH5}/js/newHome/mui.min.js?v=${SYSTEM_INIT_TIME}"> </script>
    <script src="${staticResourcePathH5}/caseBase/js/vue.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${njxBasePath!}/js/navigate.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/vue/home/bigDataDetails.js"></script>
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
        // var content='<p style="text-align: justify; line-height: 33px;"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">　　“我不吃我不喝我要钱”，“我想喝手磨咖啡”，“我活着还有什么脸面呀”……大眼袋、黄背心、精准的表情、传神的台词，一夜之间，以电视剧《都挺好》中“作爹苏大强”为创作蓝本的表情包，忽然火遍全网。表情包里的“苏大强”不仅成为了网友表达自我心情的媒介，更成了政务大V们普法的新载体。</span></p><h2 style="line-height: 33px; text-align: center;"><span style="font-size: 24px; color: rgb(0, 0, 0); font-family: 宋体, SimSun;"><strong><span style="font-size: 24px; font-family: 微软雅黑; color: rgb(77, 79, 83); letter-spacing: 1px; background: rgb(255, 255, 255);">政务大V再添新作 开启表情包流量狂欢</span></strong></span></h2><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">近日，以@警民直通车-上海、@最高人民检察院、@中国长安网、@金山消防 为代表的许多政务微博纷纷发布苏大强热点相关的普法内容。“什么？明成打人被警察抓了？”“所谓‘民族大业’‘精准扶贫’‘养老帮扶’‘慈善富民’等项目都是骗人的”图片上醒目的文字配上苏大强传神的表情让人印象深刻。</span></p><p style="text-align: center"><img src="http://files.51wyq.cn/yunying/hot/banner/201904/01/09/1554082639958.png" title="1554082639958.png" alt="1554082639958.png"/></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">以@警民直通车-上海 发布的相关微博为例，据微热点（wrd.cn）数据统计，该微博共收获236次有效转发，98条评论和164个赞，同时覆盖4450万人次，而@环球时报 共带动再次转发71次，成为这条微博的关键传播用户。</span></p><p style="text-align: center"><img src="http://files.51wyq.cn/yunying/hot/banner/201904/01/09/1554082665543.jpg" title="1554082665543.jpg" alt="1554082665543.jpg"/></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">从微博引爆点来看，除@环球时报 带动二次转发71次外，其余9个引爆微博账号全部来自于公安政务微博账号，其中@中国警察网 、@海南警方 、@苏州公安 、@警民直通车-上海 分别带动二次转发68次、25次、22次和12次。</span></p><p style="text-align: center"><img src="http://files.51wyq.cn/yunying/hot/banner/201904/01/09/1554082685588.jpg" title="1554082685588.jpg" alt="1554082685588.jpg"/></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">而从该条微博转发和评论热词分析来看，“警察”、“大意”、“要钱”、“提醒”和“上海”五个词汇位列提及量TOP5,其中“卡通”、“可爱”等热词也位列其中，可见这样生动有趣的普法方式受到了大家的喜爱。</span></p><p style="text-align: center"><img src="http://files.51wyq.cn/yunying/hot/banner/201904/01/09/1554082721746.jpg" title="1554082721746.jpg" alt="1554082721746.jpg"/></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">从内容上看，公安类账号内容，聚焦反诈骗、安全出行、证件办理等日常民生问题；检察院类账号则侧重辨别诈骗套路和各类纠纷，深入普法。网约车、扰乱单位秩序、骗婚套路、理财投资被骗等一个个常见问题，被小编们充分代入剧情，用更轻松活泼的普法形式，广而告之。政务小编们正充分融入网络对话情景，政策文件、提醒标语、执法办公被更具传播能力的形式包装，同时也使服务形象更加亲切。</span></p><p style="line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">&nbsp;</span></p><h2 style="line-height: 33px; text-align: center;"><span style="text-indent: 32px; letter-spacing: 1px; background: rgb(255, 255, 255); font-size: 24px; color: rgb(0, 0, 0); font-family: 宋体, SimSun;">洞察热点成为政务微博运营基本功</span></h2><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">从流浪地球造句大赛到热播剧集，政务微博运营人围绕网络热点、社会热点、网络话题孵化出一套独有的“热点系统”。借助各类热点，发布相关普法内容从而获取更多关注。紧跟热点的普法形式，不仅让公众感受到了政务微博对热点的感知力和把握力；同时也提高政务微博的传播力，塑造良好的政府形象。</span></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;"></span></p><p style="text-align: center"><img src="http://files.51wyq.cn/yunying/hot/banner/201904/01/09/1554082803629.png" title="1554082803629.png" alt="1554082803629.png"/></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">据U媒数据统计，在2019年3月19日-26日期间，全网共有1326个政务微博发布了1934条涉“苏大强”的微博，其中公安类政务微博共发布了813条相关微博，包括转评赞在内的互动总量达到5547，排在所有政务微博二级行业中第一位，其次为检察院和政法委政务微博，互动总量分别为2486和1270。</span></p><p style="text-align: center"><img src="http://files.51wyq.cn/yunying/hot/banner/201904/01/09/1554082832064.png" title="1554082832064.png" alt="1554082832064.png"/></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">综上来看，这些紧跟热点、文风生动活泼的政务微博，更容易引发大量的围观与跟评转发。纵观网络留言，“蜀黍好可爱哦”、“大家遇事莫着急，有事找警察啊”、“这样的防诈骗系列请给我来一打”……转发、评论、点赞，网友用不同的形式表达意见抒发情感。相较于自说自话地照本宣科，紧扣社会热点的宣传方式被越来越多的运用在运营工作中。</span></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;"><br/></span></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">自然而然，快速发现热点、洞察同行活跃内容已经成为运营人的必备技能。而在信息洪流充斥现代生活的今天，如何洞悉网络热点，不仅变相考验着运营人的能力和经验，而时刻发生信息裂变的新媒体平台更对资源有限的运营团队提出了变相的人力要求。半夜突发消息、热点几个小时就过去了……快速、准确、合理成为运营人跟热点不成文的规定。</span></p><p style="text-indent:32px;line-height:33px"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;"><br/></span></p><p style="text-indent: 32px; line-height: 33px;"><span style="text-indent: 32px; letter-spacing: 1px; font-size: 14px; background: rgb(255, 255, 255); color: rgb(0, 0, 0); font-family: 宋体, SimSun;">科学跟热点，追得高效，追得吸睛。不仅能与网友产生情感共鸣，有态度有温度，还可以增强传播力度，从而达到既拉近网民距离又达成深入普法的双重目标。</span></p>';
        //
        // //小标题
        // content = content.replace(/box-sizing: border-box; margin: 0px 0px 15px; color: rgba\(0, 0, 0, 0.85\); text-rendering: optimizeLegibility; line-height: 1.7; font-size: 24px; font-family: -apple-system, "SF UI Text", Arial, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif; white-space: normal; background-color: rgb\(255, 255, 255\); text-align: center;/g,"text-indent:40px;text-autospace:ideograph-numeric; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; text-align: center; line-height: 150%;");
        // content = content.replace(/box-sizing: border-box; margin: 0px 0px 15px; color: rgba\(0, 0, 0, 0.85\); text-rendering: optimizeLegibility; line-height: 1.7; font-size: 24px; font-family: -apple-system, &quot;SF UI Text&quot;, Arial, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, sans-serif; white-space: normal; background-color: rgb\(255, 255, 255\); text-align: center;/g, "text-indent:40px;text-autospace:ideograph-numeric;margin-top: 0px; margin-right: 0px; margin-bottom: 0px; text-align: center; line-height: 150%;");
        // //内容
        // content =  content.replace(/padding: 0px; max-width: 100%; clear: both; min-height: 1em; color: rgb\(51, 51, 51\); font-family: -apple-system-font, BlinkMacSystemFont, "Helvetica Neue", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei UI", "Microsoft YaHei", Arial, sans-serif; font-size: 17px; letter-spacing: 0.544px; text-align: justify; white-space: normal; background-color: rgb\(255, 255, 255\); text-indent: 32px; line-height: 2em; box-sizing: border-box !important; word-wrap: break-word !important;/,"text-indent:37px;text-autospace:ideograph-numeric;");
        // content =  content.replace(/box-sizing: border-box; margin-top: 0px; margin-bottom: 25px; word-break: break-word; color: rgb\(51, 51, 51\); font-family: -apple-system, "SF UI Text", Arial, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif; white-space: normal; background-color: rgb\(255, 255, 255\);/g, "text-indent:37px;text-autospace:ideograph-numeric;");
        // content =  content.replace(/box-sizing: border-box; margin-top: 0px; margin-bottom: 25px; word-break: break-word; color: rgb\(51, 51, 51\); font-family: -apple-system, &quot;SF UI Text&quot;, Arial, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, sans-serif; white-space: normal; background-color: rgb\(255, 255, 255\);/g, "text-indent:37px;text-autospace:ideograph-numeric;");
        // content =  content.replace(/font-family: 微软雅黑;color: rgb\(51, 51, 51\);letter-spacing: 0;font-size: 16px;background: rgb\(255, 255, 255\)/g,"");
        // content =  content.replace(/font-family: 微软雅黑;line-height: 150%;color: rgb\(51, 51, 51\);letter-spacing: 0;font-size: 16px;background: rgb\(255, 255, 255\)/g,"");




        // content = content.replace(/background-color: rgb\(255, 255, 255\);/g,"");
        // content = content.replace(/background: rgb\(255, 255, 255\)/g,"");
        // content = content.replace(/color: rgb\(51, 51, 51\)/g,"");
        // content = content.replace(/color: rgba\(0, 0, 0, 0.85\)/g,"");
        // content = content.replace(/color: rgb\(0, 0, 0\)/g,"");
        var new_content = content;//replace(/<(?!img|p|\/p).*?>/g, "");

        $("#article-body").html(new_content);
        $("#article-body1").html(new_content);
        $("#article-body2").html(new_content);
        function goPayreport(){
            commonBuyProductFee();
        }
    </script>
    </body>
</html>
