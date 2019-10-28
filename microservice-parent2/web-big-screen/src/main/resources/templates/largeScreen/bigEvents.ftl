
<#include "../common/frame_top.ftl">
	<link rel="stylesheet" href="${staticResourcePath}/css/base.css" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
	<link rel="stylesheet" href="${staticResourcePath}/css/font-icon.css">
	<link rel="stylesheet" href="${staticResourcePath}/css/site.css">
	<link rel="stylesheet" href="${staticResourcePath}/js/swiper/swiper.min.css">
	<link rel="stylesheet" href="${staticResourcePath}/css/hot.css">
	<title>热点发现</title>
</head>

<body>

<div class="screen-wrapper screen-bg">
	<div class="screen-main font0 height-full">
		<!-- top side -->
		<div class="screen-head">
			<div class="screen-search inline-block">
				<input type="search" placeholder="网络传播热度指数查询">
				<span class="search-btn"><i class="icon">&#xe74c;</i></span>
			</div>
			<div class="screen-title inline-block">
				<span>热点发现</span>
			</div>
			<div class="inline-block screen-head-right">
				<button class="screen-btn">案例库<i class="icon"></i></button>
			</div>
		</div>
		<div class="screen-contain">
			<!-- left side -->
			<div class="screen-side">
				<ul class="screen-side-nav">
					<li class="">
						<a href="${njxBasePath}/api/hot/largeScreen/showScreen">热门事件</a>
					</li>
					<li class="active">
						<a href="${njxBasePath}/largeScreen/bigEventScreen">重大事件</a>
					</li>
					<li class="">
						<a href="javascript:;">热门微博</a>
					</li>
					<li class="">
						<a href="javascript:;">热门人物</a>
					</li>
				</ul>
			</div>
			<!-- content side -->
			<div class="screen-body height-full" id="bigEvent">
				<div class="item-side rel active height-full">
					<!-- 第二页 -->
					<p class="abs font-size-20 color_1" style="right: 0;top:-28px;">*数据统计时间：<span v-text=startTime></span> 至 <span v-text=endTime></span>  </p>
					<div class="bigNav clearfix">
						<ul>
							<template  v-for="(model,index) in allBigEventList">
								<template  v-if = "index == 0">
									<li class="active">
										<template v-if="model.name.length>10">
											<a href="javascript:;" v-text="model.name.substring(0,10)+'...'"></a>
										</template>
										<template v-else>
											<a href="javascript:;" v-text="model.name"></a>
										</template>
									</li>
								</template>
								<template v-else>
									<li>
										<template v-if="model.name.length>10">
											<a href="javascript:;" v-text="model.name.substring(0,10)+'...'"></a>
										</template>
										<template v-else>
											<a href="javascript:;" v-text="model.name"></a>
										</template>
									</li>
								</template>
							</template>
						</ul>
					</div>
					<div class="module-big big-swiper-1 swiper-container">
						<div class="swiper-wrapper">
							<template  v-for="(model,index) in allBigEventList">

								<div class="item inline-block width-full swiper-slide">
									<!-- 边框 -->
									<div class="line">
										<div class="module-big-left inline-block">
											<div class="margin-bottom-40">
												<div class="inline-block text-center big-circular animation-roll">
													<p class="num1" v-text="model.eventNum"></p>
													<p class="font-size-18 color_1">7日内包含事件数</p>
												</div>
											</div>
											<div>
												<div class="inline-block text-center big-circular animation-roll animation-roll-90">
													<p class="num1">
														<span v-if="model.numberDay >= 10000" v-text="(model.numberDay / 10000).toFixed(2) + 'w'"></span>
														<span v-else v-text="model.numberDay"></span>
													</p>
													<p class="font-size-18 color_1">24小时总信息量</p>
												</div>
											</div>
										</div>
										<div class="module-big-group inline-block height-full">
											<p class="module-action font-size-20 color_1">*数据统计时间：<span v-text="startTime"></span> 至 <span
														v-text="endTime"></span></p>

											<span v-if="index == 0">
												<div class="module-tabs inline-block height-full active ">
													<ul class="height-full">
														<template v-for="(hotModel,hotIndex) in model.hotIncidentList">
															<template v-if="hotIndex == 0">
																<li class="active">
																	<a href="javascript:;">
																		<p class="txt" v-if="model.name.length>10"
																		   v-text="hotModel.incidentTitle.substring(0,15)+'...'"></p>
																		<p class="txt" v-else v-text="hotModel.incidentTitle"></p>
																		<p><span>{{hotModel.createTime | formatDate('YYYY-MM-DD HH:mm')}}</span><span
																					v-text=""><i class="icon">&#xe6e1;</i>{{hotModel.province}}</span></p>
																	</a>
																</li>
															</template>
															<span v-else-if="hotIndex <= 3">
																	<li>
																		<a href="javascript:;">
																			<p class="txt" v-if="model.name.length>10"
																			   v-text="hotModel.incidentTitle.substring(0,15)+'...'"></p>
																			<p class="txt" v-else v-text="hotModel.incidentTitle"> </p>
																			<p><template>{{hotModel.createTime | formatDate('YYYY-MM-DD HH:mm')}}</template>
																				<template v-text=""><i class="icon">&#xe6e1;</i>{{hotModel.province}}</template>
																			</p>
																		</a>
																	</li>
															</span>
														</template>
													</ul>
												</div>
											</span>
											<span v-if="index != 0">
												<div class="module-tabs inline-block height-full">
													<ul class="height-full">
														<template v-for="(hotModel,hotIndex) in model.hotIncidentList">
															<template v-if="hotIndex == 0">
																<li class="active">
																	<a href="javascript:;">
																		<p class="txt" v-if="model.name.length>10"
																		   v-text="hotModel.incidentTitle.substring(0,15)+'...'"></p>
																		<p class="txt" v-else v-text="hotModel.incidentTitle"></p>
																		<p><span>{{hotModel.createTime | formatDate('YYYY-MM-DD HH:mm')}}</span><span
																					v-text=""><i class="icon">&#xe6e1;</i>{{hotModel.province}}</span></p>
																	</a>
																</li>
															</template>
															<span v-else-if="hotIndex <= 3">
																	<li>
																		<a href="javascript:;">
																			<p class="txt" v-if="model.name.length>10"
																			   v-text="hotModel.incidentTitle.substring(0,15)+'...'"></p>
																			<p class="txt" v-else v-text="hotModel.incidentTitle"> </p>
																			<p><template>{{hotModel.createTime | formatDate('YYYY-MM-DD HH:mm')}}</template>
																				<template v-text=""><i class="icon">&#xe6e1;</i>{{hotModel.province}}</template>
																			</p>
																		</a>
																	</li>
															</span>
														</template>
													</ul>
												</div>
											</span>

											<div class="module-tabs-content big-module-swiper-1 inline-block swiper-container swiper-container-vertical height-full">
												<!--<div class="">-->
												<div class="swiper-wrapper inline-block height-full">

													<template v-for="(hotModel,hotIndex) in model.hotIncidentList">
														<template v-if="hotIndex == 0">
															<div class="module-tabs-item height-full swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt" v-text="hotModel.numberDay"> </p>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt" v-text="hotModel.mgNumberDay"> </p>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<span v-if="hotModel.numberDay == 0">
																						<p class="txt" v-text="0">%</p>
																					</span>
																					<span v-else>
																						<p class="txt" v-text="parseFloat(hotModel.mgNumberDay/hotModel.numberDay*100).toFixed(2)">%</p>
																					</span>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt" v-text="parseFloat(hotModel.ratioHotDay).toFixed(2)"></p>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt" v-text="parseFloat(hotModel.ratioHotTopCustom).toFixed(2)"></p>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">{{hotModel.topTotalTime | formatDate('MM-DD HH:mm')}}</p>
																				</div>
																			</div>

																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div :id="'pieEchart'+index+hotIndex" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div :id="'barEchart'+index+hotIndex" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div :id="'bigBar'+index+hotIndex" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
														</template>
														<template v-else-if="hotIndex <= 3">
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt" v-text="hotModel.numberDay"> </p>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt" v-text="hotModel.mgNumberDay"> </p>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<span v-if="hotModel.numberDay == 0">
																						<p class="txt" v-text="0">%</p>
																					</span>
																					<span v-else>
																						<p class="txt" v-text="parseFloat(hotModel.mgNumberDay/hotModel.numberDay*100).toFixed(2)">%</p>
																					</span>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt" v-text="parseFloat(hotModel.ratioHotDay).toFixed(2)"></p>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt" v-text="parseFloat(hotModel.ratioHotTopCustom).toFixed(2)"></p>
																				</div>
																			</div>

																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePath}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">{{hotModel.topTotalTime | formatDate('MM-DD HH:mm')}}</p>
																				</div>
																			</div>

																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div :id="'pieEchart'+index+hotIndex" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div :id="'barEchart'+index+hotIndex" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div :id="'bigBar'+index+hotIndex" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
														</template>
													</template>
												</div>
												<!--</div>-->
											</div>
										</div>
									</div>
								</div>
							</template>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

		<script src="${staticResourcePath}/js/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePath}/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePath}/js/echarts.4.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePath}/js/china.js?v=${SYSTEM_INIT_TIME}"></script>
    	<script src="${staticResourcePath}/js/vue.min.js?v=${SYSTEM_INIT_TIME}"></script>
    	<script src="${staticResourcePath}/js/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
    	<script src="${staticResourcePath}/js/moment.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePath}/js/bigEcharts.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePath}/js/swiper/swiper.min.js?v=${SYSTEM_INIT_TIME}"></script>
    	<script src="${staticResourcePath}/js/hotLargeScreenVue/bigEventsSvreen.js?v=${SYSTEM_INIT_TIME}"></script>

</body>

</html>