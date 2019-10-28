<!--head代码 start-->
<div class="w-head w-headHover min-layout1200">
    <div class="w-headbar-top">
        <div class="w-headbar-logo">
            <img src="${staticResourcePath}/images/w-md-logo.png" alt="">
        </div>
        <div class="w-headbar-r">
            <div class="w-heabar-menu">
                <div class="w-heabar-menu-item">
                    <a href="">系统公告</a>
                </div>
                <div class="w-heabar-menu-item">
                    <a href="${url}pay/buy.shtml">产品选购</a>
                </div>
                <div class="w-heabar-menu-item">
                    <a href="${url}novice.shtml">新手专区</a>
                </div>
                <div class="w-heabar-menu-item">
                    <a href="${url}bill/order.shtml">索取发票</a>
                </div>
                <div class="w-heabar-user hoverShow">
                    <a href="${url}usercenter/account.shtml">
                        <div class="w-heabar-user-img">
                            <img src="${userHead}" alt="">
                        </div>
                        <div class="w-heabar-name">${nickName}</div>
                    </a>
                    <div class="w-user-memu" style="display: none;">
                        <div class="w-user-memu-wrap">
                            <div class="w-user-memu-top">
                                <span class="pull-left">您好，${nickName}</span>
                                <a href="JavaScript:void(0);" @click="goOut($event)" class="pull-right">【退出】</a>
                            </div>
                            <div class="text-right">
                                <div class="pull-left">
                                    <img src="http://cdn.files.51wyq.cn/web/images/v-icon.png" alt=""><span class="color_9">${creditAmount}</span>微积分
                                </div>
                                <button class="w-btn btn-round w-btn-red" @click="getOrderFee($event)">购买</button>
                            </div>
                            <dl id="dl_list0" class="margin-bottom-10 dl_list rel">
                                <dd class="abs" style="bottom: 0;right: 0"> <a href="javascript:void(0);" class="bottomMore">更多<i class="icon-angle-down rotate0"></i></a></dd>
                                <dd class="font-weight-600">用户剩余权益：</dd>
                                <dd>全网事件分析：<span class="fc_red"> X ${user.userAnalysisValidCount}</span></dd>
                                <dd>微博事件分析：<span class="fc_red"> X ${user.userWeiboAnalysisValidCount}</span></dd>
                                <dd>简报制作：<span class="fc_red"> X ${user.userBriefValidCount}</span></dd>
                            	<dd>新闻稿监测：<span class="fc_red"> X ${total}</span></dd>
                            </dl>
                        </div>
                    </div>
                </div>
            
            </div>
        
        </div>
    
    </div>
    <div class="w-headbar-bottom">
        <div class="w-headbar-nav">
            <ul class="w-headbar-list clearfix">
                <li>
                    <a href="${url}home.shtml">热点发现</a>
                </li>
                <li>
                    <a href="${url}caseAnalysis.shtml">案例库</a>
                </li>
                <li>
                    <a href="${url}infoData.shtml">大数据报告</a>
                </li>
                <li>
                    <a href="${url}keyword.shtml">舆情监测</a>
                </li>
                <li class="j_hover">
                    <a href="JavaScript:void(0)">分析与评估工具</a>
                    <div class="w-headbar-dropdown">
                        <div class="w-dropdown-inner active" style="left: -85px;">
                            <ul>
                                <li>
                                    <a href="${url}view/weiboEventAnalysis/taskList.shtml">微博事件分析</a>
                                </li>
                                <li>
                                    <a href="${url}view/eventAnalysis/taskList.shtml">全网事件分析</a>
                                </li>
                                <li>
                                    <a href="${url}productsAnalysis.shtml">竞品分析</a>
                                </li>
                                <li>
                                    <a href="${url}view/reviewAnalysis/reviewIndex.shtml">评论分析</a>
                                </li>
                                <li>
                                    <a href="${url}i/singleWeiboAnalysis/index.shtml">微博传播分析</a>
                                </li>
                                <li>
                                    <a href="${url}emotionMap.shtml">微博情绪</a>
                                </li>
                                <li>
                                    <a href="${url}splitWords.shtml">文本挖掘</a>
                                </li>
                                <li>
                                    <a href="${staticResourcePath}/xwg/NewsTask/getXwgSurveyList">新闻稿监测</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>
                <li class="j_hover">
                    <a href="JavaScript:void(0)">专业解决方案</a>
                    <div class="w-headbar-dropdown">
                        <div class="w-dropdown-inner active" style="left: -85px;">
                            <ul>
                                <li>
                                    <a href="https://www.u-mei.com/web/index.html">U媒</a>
                                </li>
                                <li>
                                    <a href="https://www.yqt365.com/logon.action">舆情通</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>
                <!--
                <li>
                    <a href="JavaScript:void(0)">蜜度-福州高峰论坛</a>
                </li>
                -->
            </ul>
        </div>
    </div>
 </div>
<!--head代码 end-->