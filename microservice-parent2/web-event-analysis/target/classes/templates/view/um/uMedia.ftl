<#include "../../init_top.ftl" >
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css?v=${SYSTEM_INIT_TIME}"/>
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
		<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css?v=${SYSTEM_INIT_TIME}" />
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/sweetalert.css?v=${SYSTEM_INIT_TIME}" />
		<script src="${staticResourcePathH5}/js/newHome/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>
		<title>微热点</title>
		<style>
            [v-cloak] {
                display: none;
            }
        </style>
	</head>

	<body class="mainBackground">
    	<div id="app">
            <#if sid??>
			<input type="hidden" id="sid" value="${sid}"/>
            </#if>
            <#if nickname??>
			<input type="hidden" id="nickname" value="${nickname}"/>
            </#if>
            <#if umBlueVUser??>
			<input type="hidden" id="umBlueVUser" value="${umBlueVUser?string("true","false")}"/>
            </#if>
		<div>
            <#if !umBlueVUser>
                <!--普通用户-->
                <div  class="um-head">
                    <div class="um-logo">
                        <img src="${staticResourcePathH5}/images/um_logo.png" alt="" />
                    </div>
                    <div class="um-caption">
                        <h3>新媒体账号一站式管理工具</h3>
                    </div>
                    <#--<div class="um-head-img">-->
                        <#--<img src="${staticResourcePathH5}/images/um_7.png" />-->
                    <#--</div>-->
                </div>
            <#else>
                <!--重要用户-->
                <div v-show="noMessage" class="um-users" v-cloak>
                    <div class="um-head um-dash-bg">
                        <div class="um-search">
                            <input @keyup.enter="goSearch()" type="search" name="" id="searchNme" value="" placeholder="查询其他账户" />
                            <span class="um-search-btn" @click="goSearch()"></span>
                        </div>
                        <div class="um-caption">
                            <h3 id="nick">@{{nickname}}</h3>
                            <p class="font-size-12 color_1 margin-vertical-10">统计周期：<span>{{duringData}}</span></p>
                        </div>
                        <!--仪表盘-->
                        <div class="um-dashboard">
                            <div id="um-dashboard-echart" data-type="yibiaopan" style="width: 100%;height: 280px;"></div>
                            <div class="um-dashboard-text">
                                <p class="um-dt-1">{{biiIndex}}</p>
                                <p class="um-dt-2">微博影响力（BII）</p>
                            </div>
                        </div>

                    </div>
                    <div>
                        <ul class="um-card clearfix margin-top-20">
                            <li>
                                <div class="um-card-item">
                                    <img src="${staticResourcePathH5}/images/um_u_1.png" alt="" />
                                    <div>
                                        累计指数：<span class="color_5">{{cumulativeIndex}}</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="um-card-item">
                                    <img src="${staticResourcePathH5}/images/um_u_2.png" alt="" />
                                    <div>
                                        活跃指数：<span class="color_5">{{activeIndex}}</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="um-card-item">
                                    <img src="${staticResourcePathH5}/images/um_u_3.png" alt="" />
                                    <div>
                                        发布指数：<span class="color_5">{{publishingIndex}}</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="um-card-item">
                                    <img src="${staticResourcePathH5}/images/um_u_4.png" alt="" />
                                    <div>
                                        互动指数：<span class="color_5">{{interactionIndex}}</span>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="padding-15">
                        <div v-show="nofirstRankId||nosecondRankId" class="um-ranking color_6 text-center font-size-12">
                            <p v-show="nosecondRankId" class="margin-bottom-5">行业排名：第<span class="color_5">{{secondRankId}}</span>位</p>
                            <p v-show="nofirstRankId">全国政务排名：第<span class="color_5">{{firstRankId}}</span>位</p>
                        </div>
                    </div>
                    <div class="text-center">
                        <a href="javascript:;" @click="getum()" class="um-more"></a>
                    </div>

                </div>
			</#if>
			<!--核心功能-->
			<#--<div class="um-line"></div>-->
			<section>
				<div class="um-title">
					<h3>核心功能</h3>
				</div>
				<div class="um-body">
					<ul class="um-list clearfix">
						<li>
							<div class="um-item">
								<div class="um-item-img">
									<img src="${staticResourcePathH5}/images/um_1.png" />
								</div>
								<p class="um-item-title">跨平台使用</p>
							</div>
						</li>
						<li>
							<div class="um-item">
								<div class="um-item-img">
									<img src="${staticResourcePathH5}/images/um_2.png" />
								</div>
								<p class="um-item-title">新媒体账号监测</p>
							</div>
						</li>
						<li>
							<div class="um-item">
								<div class="um-item-img">
									<img src="${staticResourcePathH5}/images/um_3.png" />
								</div>
								<p class="um-item-title">粉丝画像</p>
							</div>
						</li>

						<li>
							<div class="um-item">
								<div class="um-item-img">
									<img src="${staticResourcePathH5}/images/um_4.png" />
								</div>
								<p class="um-item-title">影响力评估</p>
							</div>
						</li>

						<li>
							<div class="um-item">
								<div class="um-item-img">
									<img src="${staticResourcePathH5}/images/um_5.png" />
								</div>
								<p class="um-item-title">消息监测</p>
							</div>
						</li>

						<li>
							<div class="um-item">
								<div class="um-item-img">
									<img src="${staticResourcePathH5}/images/um_6.png" />
								</div>
								<p class="um-item-title">小程序</p>
							</div>
						</li>

					</ul>
				</div>
				<div class="um-title">
					<h3>使用流程</h3>
				</div>
                <div class="um-liucheng">
                    <a href="javascript:;" @click="jp(3)">
                        <img src="${staticResourcePathH5}/images/newHome/um_liucheng_1.png" />
                    </a>
                    <a href="javascript:;" @click="jp(3)">
                        <img src="${staticResourcePathH5}/images/newHome/um_liucheng_2.png" />
                    </a>
                    <a href="javascript:;" @click="jp(3)">
                        <img src="${staticResourcePathH5}/images/newHome/um_liucheng_3.png" />
                    </a>
                </div>
				<div class="um-title-line">
					<span>新媒体账号一站式管理工具</span>
				</div>
			</section>
		</div>
		<!--底部导航-->
		<div class="wui-tabbar wui-tabbar-fixed">
			<a class="wui-tabbar-item" href="home">
				<div class="wui-tabbar-item_icon">
				</div>
				<div class="wui-tabbar-item_text">首页</div>
			</a>
			<a class="wui-tabbar-item" href="infoData">
				<div class="wui-tabbar-item_icon wui-icon-data">
				</div>
				<div class="wui-tabbar-item_text">大数据解读</div>
			</a>
			<a class="wui-tabbar-item" href="analysis">
				<div class="wui-tabbar-item_icon wui-icon-analysis">
				</div>
				<div class="wui-tabbar-item_text">热点分析</div>
			</a>
			<a class="wui-tabbar-item wui-tabbar-item-active" href="uMedia">
				<div class="wui-tabbar-item_icon wui-icon-um">
				</div>
				<div class="wui-tabbar-item_text">新媒体运营</div>
			</a>
			<a class="wui-tabbar-item" href="user">
				<div class="wui-tabbar-item_icon wui-icon-my">
				</div>
				<div class="wui-tabbar-item_text">我</div>
			</a>
		</div>
        <!-- 弹框 -->
        <div class="modal fade um-model" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <a href="javascript:;" class="um-model-close" data-dismiss="modal" aria-label="Close"></a>
                            <img src="${staticResourcePathH5}/images/newHome/um-model-img.png" style="width: 100%;" alt="">
                            <div class="um-model-text">
                                <p>抱歉，该账户未收录</p>
                                <p>您可以登录U媒查看该账户数据。</p>
                            </div>
                            <a href="javascript:;" @click="getum1()" class="btn-getMore"></a>
                        </div>
                    </div>
                </div>
            </div>
		</div>
		<script src="${staticResourcePathH5}/js/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script type="text/javascript" src="${staticResourcePathH5}/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/sweetalert.min.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePathH5}/js/echarts/echarts3.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/echarts/echartsOpt1.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js?v=${SYSTEM_INIT_TIME}"></script>
        <script src="${staticResourcePathH5}/js/um/wUMedia.js?v=${SYSTEM_INIT_TIME}"></script>
		<script>
		</script>
	</body>

</html>