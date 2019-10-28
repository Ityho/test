<#include "../../init_top.ftl" >
	<title>案例库</title>
	<link rel="stylesheet" href="${staticResourcePathH5}/caseBase/css/weui.css?v=${SYSTEM_INIT_TIME}">
	<link rel="stylesheet" href="${staticResourcePathH5}/caseBase/css/common.css?v=${SYSTEM_INIT_TIME}">
	<link rel="stylesheet" href="${staticResourcePathH5}/caseBase/css/style.css?v=${SYSTEM_INIT_TIME}">
	<link rel="stylesheet" href="${staticResourcePathH5}/caseBase/css/details.css?v=${SYSTEM_INIT_TIME}">
</head>

<body>
	<div id="app">
        <input type="hidden" id="caseId" value="${caseId}"/>
		<div class="container" v-cloak>
			<div class="content-scroll-y" v-if="eventLabel != 18">
				<div class="section">
					<div class="title rel">
                        <div class="badge badge-color1" v-if="eventLabel === 1">
                            时事
                        </div>
                        <div class="badge badge-color2" v-if="eventLabel === 2">
                            社会
                        </div>
                        <div class="badge badge-color3" v-if="eventLabel === 3">
                            体育
                        </div>
                        <div class="badge badge-color4" v-if="eventLabel === 4">
                            科技
                        </div>
                        <div class="badge badge-color5" v-if="eventLabel === 5">
                            灾害
                        </div>
                        <div class="badge badge-color6" v-if="eventLabel === 6">
                            娱乐
                        </div>
                        <div class="badge badge-color7" v-if="eventLabel === 7">
                            人物
                        </div>
                        <div class="badge badge-color8" v-if="eventLabel === 8">
                            财经
                        </div>
                        <div class="badge badge-color9" v-if="eventLabel === 9">
                            房产
                        </div>
                        <div class="badge badge-color10" v-if="eventLabel === 10">
                            金融
                        </div>
                        <div class="badge badge-color11" v-if="eventLabel === 11">
                            医疗
                        </div>
                        <div class="badge badge-color12" v-if="eventLabel === 12">
                            教育
                        </div>
                        <div class="badge badge-color13" v-if="eventLabel === 13">
                            文化
                        </div>
                        <div class="badge badge-color14" v-if="eventLabel === 14">
                            汽车
                        </div>
                        <div class="badge badge-color15" v-if="eventLabel === 15">
                            旅游
                        </div>
                        <div class="badge badge-color16" v-if="eventLabel === 16">
                            政务
                        </div>
                        <div class="badge badge-color17" v-if="eventLabel === 17">
                            法治
                        </div>
                        <div class="badge badge-color18" v-if="eventLabel === 19">
                            突发
                        </div>
						<h3 >{{title}}</h3>
					</div>
					<div class="time">
						数据时间:{{startTime|formatDate()}}至{{endTime|formatDate()}}
					</div>
				</div>
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">01</span>事件概述</h3>
					</div>
					<div class="w-panel-body">
						<p class="fz14 fc_6" v-text="summary">斯拉轿车突然冒出白烟，进而起火燃烧，有附近车辆也遭遇烧毁斯拉轿车突然冒出白烟，进而起火燃烧，有附近车辆也遭遇烧毁。</p>
					</div>
				</div>
				<!--热度指数-->
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">02</span>热度指数</h3>
					</div>
					<div class="w-panel-body">
						<div class="echart-body">
							<div id="echarts1" style="width: 100%;height:250px"></div>
						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
				</div>
				<!--信息概括-->
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">03</span>信息概括</h3>
					</div>
					<div class="w-panel-body">
						<div class="weui-weight padding-bottom-10 clearfix" >
							<div class="weui_item">
								<div class="text-center">
									<div class="fz22 fc_blue">{{allsumMG}}</div>
									<div class="fz11 fc_9">信息总量</div>
								</div>
							</div>
							<div class="weui_item">
								<div class="text-center">
									<div class="fz22 fc_red">{{informationData.sumMG}}</div>
									<div class="fz11 fc_9">敏感信息量</div>
								</div>
							</div>
							<div class="weui_item">
								<div class="text-center">
									<div class="fz22 fc_green">{{informationData.unSumMG}}</div>
									<div class="fz11 fc_9">非敏感信息量</div>
								</div>
							</div>
						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
					<div class="err" style="display: none">此时间段暂无信息</div>
				</div>
				<!--事件趋势图-->
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">04</span>事件趋势图</h3>
					</div>
					<div class="w-panel-body">
						<div class="echart-body">
							<div id="echarts2" style="width: 100%;height:300px"></div>
						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
					<div class="err" style="display: none">此时间段暂无信息</div>
				</div>
				<!--来源类型-->
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">05</span>来源类型</h3>
					</div>
					<div class="w-panel-body">
						<div class="echart-body">
							<div id="echarts3" style="width: 100%;height:360px">

							</div>
						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
					<div class="err" style="display: none">此时间段暂无信息</div>
				</div>
				<!--热点词云-->
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">06</span>热点词云</h3>
					</div>
					<div class="w-panel-body padding-bottom-10" v-if="errShow_3">
						<div class="echart-body">
							<div id="ciyunEchart" style="width: 100%;height:250px"></div>

						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
					<div class="err" style="display: none">此时间段暂无信息</div>
				</div>
				<!--媒体友好度-->
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">07</span>媒体友好度</h3>
					</div>
					<div class="w-panel-body">
						<div class="echart-body">
							<div class="mediaSource blue">
								<ul>
									<li>
										<p>微博</p>
										<p>{{informationData.wb}}%</p>
									</li>
									<li>
										<p>论坛</p>
										<p>{{informationData.lt}}%</p>
									</li>
									<li>
										<p>微信</p>
										<p>{{informationData.wx}}%</p>
									</li>
									<li>
										<p>网站</p>
										<p>{{informationData.wz}}%</p>
									</li>
									<li>
										<p>客户端</p>
										<p>{{informationData.app}}%</p>
									</li>
								</ul>
							</div>
							<div id="echarts5" style="width: 100%;height:300px;"></div>
						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
					<div class="err" style="display: none">此时间段暂无信息</div>
				</div>
				<!--情绪占比-->
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">08</span>情绪占比</h3>
					</div>
					<div class="w-panel-body">
						<div class="echart-body">
							<div id="echarts6" style="width: 100%;height:300px;"></div>
						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
					<div class="err" style="display: none">此时间段暂无信息</div>
				</div>
				<!--地域分析-->
				<div class="section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">09</span>地域分析</h3>
					</div>
					<div class="w-panel-body">
						<div>
							<div class="echart-body">
								<div id="mapEcharts" style="width: 100%;height:300px;"></div>
							</div>
							<div class="err" style="display: none">此时间段暂无信息</div>
						</div>
						<div>
							<div class="echart-body">
								<div id="echarts8" style="width: 100%;height:300px;"></div>
							</div>
							<div class="err" style="display: none">此时间段暂无信息</div>
						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
				</div>
				<!--同类事件-->
				<div class="section last-section">
					<div class="w-panel-title">
						<h3><span class="w-panel-label">10</span>同类事件</h3>
					</div>
					<div class="w-panel-body padding-bottom-10" v-if="errShow_8">
						<div class="media-item" v-if="index<3" v-for="(sim, index) in similarData">
							<div class="media-icon" v-if="index==0">
								<img src="${staticResourcePathH5}/caseBase/images/shuzi1.png" alt="">
							</div>
                            <div class="media-icon" v-if="index==1">
                                <img src="${staticResourcePathH5}/caseBase/images/shuzi2.png" alt="">
                            </div>
                            <div class="media-icon" v-if="index==2">
                                <img src="${staticResourcePathH5}/caseBase/images/shuzi3.png" alt="">
                            </div>
							
							<div class="media-item-info" @click="getOne(sim.id)">
								<div class="fz15 inline-block" v-text="sim.title">特斯拉自燃</div>
								<div class="pull-right fz22" v-text="sim.hotValue">39.62</div>
								<div class="weui-progress margin-top-15 weui-progress-blue1">
									<div class="weui-progress__bar">
										<div class="weui-progress__inner-bar js_progress" style="width: 39.62%"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="watermark">
							©️微热点（wrd.cn）
						</div>
					</div>
					<div class="err" style="display: none">此时间段暂无信息</div>
				</div>
				<div class="footer-text">
					欢迎您登陆www.wrd.cn查看深度报告
				</div>
			</div>
		</div>
	</div>
    <script src="${staticResourcePathH5}/js/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/caseBase/js/vue.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/caseBase/js/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
	<script src="${staticResourcePathH5}/caseBase/js/echarts/echarts3.js?v=${SYSTEM_INIT_TIME}"></script>
	<script src="${staticResourcePathH5}/caseBase/js/echarts/china.js?v=${SYSTEM_INIT_TIME}"></script>
	<script src="${staticResourcePathH5}/caseBase/js/echarts/echarts-liquidfill.js?v=${SYSTEM_INIT_TIME}"></script>
	<script src="${staticResourcePathH5}/caseBase/js/echarts/wordcloud.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/vue/moment.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/vue/casebase/cbdetails.js?v=${SYSTEM_INIT_TIME}"></script>


</body>

</html>