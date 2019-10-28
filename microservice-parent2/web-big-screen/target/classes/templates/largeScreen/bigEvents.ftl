<#include "../common/frame_top.ftl">
	<link rel="stylesheet" href="${staticResourcePathH5}/css/base.css">
	<link rel="stylesheet" href="${staticResourcePathH5}/css/font-icon.css">
	<link rel="stylesheet" href="${staticResourcePathH5}/css/site.css">
	<link rel="stylesheet" href="${staticResourcePathH5}/js/swiper/swiper.min.css">
	<link rel="stylesheet" href="${staticResourcePathH5}/css/hot.css">
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
							<a href="${njxBasePath}/api/hot/largeScreen/showScreen"">热门事件</a>
						</li>
						<li class="active">
							<a href="${njxBasePath}/api/hot/largeScreen/bigEventScreen">重大事件</a>
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
				<div class="screen-body height-full">
					<div class="item-side rel active height-full">
						<!-- 第二页 -->
							<p class="abs font-size-20 color_1" style="right: 0;top:-28px;">*数据统计时间：2017-09-12 至 2017-09-18</p>
							<div class="bigNav clearfix">
								<ul>
									<li class="active">
										<a href="javascript:;">国航监督员事件</a>
									</li>
									<li>
										<a href="javascript:;">杭州9岁女童被租客带走</a>
									</li>
									<li>
										<a href="javascript:;">班主任辱骂女学生事件</a>
									</li>
									<li>
										<a href="javascript:;">河南30名村医集体辞职</a>
									</li>
									<li>
										<a href="javascript:;">上饶小学生被家长刺死</a>
									</li>
								</ul>
							</div>
							<div class="module-big big-swiper-1 swiper-container">
								<div class="swiper-wrapper">
									<div class="item inline-block width-full swiper-slide">
										<!-- 边框 -->
										<div class="line">
												<div class="module-big-left inline-block">
													<div class="margin-bottom-40">
														<div class="inline-block text-center big-circular animation-roll">
															<p class="num1">56</p>
															<p class="font-size-18 color_1">7日内包含事件数</p>
														</div>
													</div>
													<div>
														<div class="inline-block text-center big-circular animation-roll animation-roll-90">
															<p class="num1">9.2w</p>
															<p class="font-size-18 color_1">24小时总信息量</p>
														</div>
													</div>
												</div>
												<div class="module-big-group inline-block height-full">
													<div class="module-tabs inline-block height-full">
														<ul class="height-full">
															<li class="active">
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
														</ul>
													</div>
													<div class="module-tabs-content big-module-swiper-1 inline-block swiper-container swiper-container-vertical height-full">
														<!--<div class="">-->
														<div class="swiper-wrapper inline-block height-full">
															<div class="module-tabs-item height-full swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart2" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar2" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar3" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar4" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
														</div>
		
														<!--</div>-->
													</div>
												</div>
											</div>
									</div>
									<div class="item inline-block width-full swiper-slide">
										<!-- 边框 -->
										<div class="line">
												<div class="module-big-left inline-block">
													<div class="margin-bottom-40">
														<div class="inline-block text-center big-circular animation-roll">
															<p class="num1">56</p>
															<p class="font-size-18 color_1">7日内包含事件数</p>
														</div>
													</div>
													<div>
														<div class="inline-block text-center big-circular animation-roll animation-roll-90">
															<p class="num1">9.2w</p>
															<p class="font-size-18 color_1">24小时总信息量</p>
														</div>
													</div>
												</div>
												<div class="module-big-group inline-block height-full">
		
													<p class="module-action font-size-20 color_1">*数据统计时间：2017-09-12 至 2017-09-18</p>
													<div class="module-tabs inline-block height-full">
														<ul class="height-full">
															<li class="active">
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
														</ul>
													</div>
													<div class="module-tabs-content big-module-swiper-1 inline-block swiper-container swiper-container-vertical height-full">
														<!--<div class="">-->
														<div class="swiper-wrapper inline-block height-full">
															<div class="module-tabs-item height-full swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart2" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar2" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar3" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar4" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
														</div>
		
														<!--</div>-->
													</div>
												</div>
											</div>
									</div>
									<div class="item inline-block width-full swiper-slide">
										<!-- 边框 -->
										<div class="line">
												<div class="module-big-left inline-block">
													<div class="margin-bottom-40">
														<div class="inline-block text-center big-circular animation-roll">
															<p class="num1">56</p>
															<p class="font-size-18 color_1">7日内包含事件数</p>
														</div>
													</div>
													<div>
														<div class="inline-block text-center big-circular animation-roll animation-roll-90">
															<p class="num1">9.2w</p>
															<p class="font-size-18 color_1">24小时总信息量</p>
														</div>
													</div>
												</div>
												<div class="module-big-group inline-block height-full">
		
													<p class="module-action font-size-20 color_1">*数据统计时间：2017-09-12 至 2017-09-18</p>
													<div class="module-tabs inline-block height-full">
														<ul class="height-full">
															<li class="active">
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
														</ul>
													</div>
													<div class="module-tabs-content big-module-swiper-1 inline-block swiper-container swiper-container-vertical height-full">
														<!--<div class="">-->
														<div class="swiper-wrapper inline-block height-full">
															<div class="module-tabs-item height-full swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart2" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar2" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar3" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar4" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
														</div>
		
														<!--</div>-->
													</div>
												</div>
											</div>
									</div>
									<div class="item inline-block width-full swiper-slide">
										<!-- 边框 -->
										<div class="line">
												<div class="module-big-left inline-block">
													<div class="margin-bottom-40">
														<div class="inline-block text-center big-circular animation-roll">
															<p class="num1">56</p>
															<p class="font-size-18 color_1">7日内包含事件数</p>
														</div>
													</div>
													<div>
														<div class="inline-block text-center big-circular animation-roll animation-roll-90">
															<p class="num1">9.2w</p>
															<p class="font-size-18 color_1">24小时总信息量</p>
														</div>
													</div>
												</div>
												<div class="module-big-group inline-block height-full">
		
													<p class="module-action font-size-20 color_1">*数据统计时间：2017-09-12 至 2017-09-18</p>
													<div class="module-tabs inline-block height-full">
														<ul class="height-full">
															<li class="active">
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
														</ul>
													</div>
													<div class="module-tabs-content big-module-swiper-1 inline-block swiper-container swiper-container-vertical height-full">
														<!--<div class="">-->
														<div class="swiper-wrapper inline-block height-full">
															<div class="module-tabs-item height-full swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart2" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar2" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar3" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar4" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
														</div>
		
														<!--</div>-->
													</div>
												</div>
											</div>
									</div>
									<div class="item inline-block width-full swiper-slide">
										<!-- 边框 -->
										<div class="line">
												<div class="module-big-left inline-block">
													<div class="margin-bottom-40">
														<div class="inline-block text-center big-circular animation-roll">
															<p class="num1">56</p>
															<p class="font-size-18 color_1">7日内包含事件数</p>
														</div>
													</div>
													<div>
														<div class="inline-block text-center big-circular animation-roll animation-roll-90">
															<p class="num1">9.2w</p>
															<p class="font-size-18 color_1">24小时总信息量</p>
														</div>
													</div>
												</div>
												<div class="module-big-group inline-block height-full">
		
													<p class="module-action font-size-20 color_1">*数据统计时间：2017-09-12 至 2017-09-18</p>
													<div class="module-tabs inline-block height-full">
														<ul class="height-full">
															<li class="active">
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
															<li>
																<a href="javascript:;">
																	<p class="txt">六问“国航监督员”大闹客舱事件</p>
																	<p><span>2019-07-12 12:21</span><span><i
																			class="icon">&#xe6e1;</i>全国</span></p>
																</a>
															</li>
														</ul>
													</div>
													<div class="module-tabs-content big-module-swiper-1 inline-block swiper-container swiper-container-vertical height-full">
														<!--<div class="">-->
														<div class="swiper-wrapper inline-block height-full">
															<div class="module-tabs-item height-full swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart1" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart2" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar2" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart3" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar3" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
															<div class="module-tabs-item swiper-slide active">
																<!-- 第一页 -->
																<div class="big-item-left inline-block">
																	<div class="row no-space no-gutters clearfix">
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-1.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">信息总量</p>
																					<p class="txt">13249</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-2.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量</p>
																					<p class="txt">1324</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-3.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">敏感信息量占比</p>
																					<p class="txt">36%</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-4.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度均值</p>
																					<p class="txt">18.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-5.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值</p>
																					<p class="txt">98.92</p>
																				</div>
																			</div>
		
																		</div>
																		<div class="col-4">
																			<div class="counter">
																				<div class="counter-icon">
																					<img src="${staticResourcePathH5}/images/big-icon-6.svg" alt="">
																				</div>
																				<div class="counter-info">
																					<p class="font-size-20 color_1">热度峰值时间</p>
																					<p class="txt">07-15 18:32</p>
																				</div>
																			</div>
		
																		</div>
																	</div>
																	<div class="border-bottom"></div>
																	<div class="row">
																		<div class="col-6 border-right">
																			<div id="pieEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">敏感占比</p>
																		</div>
																		<div class="col-6">
																			<div id="barEchart4" style="width: 100%;height:220px"></div>
																			<p class="font-size-20 color_1 text-center">媒体发文</p>
																		</div>
																	</div>
																</div>
		
																<!-- 图表 -->
																<div class="big-item-right inline-block">
																	<div id="bigBar4" style="width: 100%;height: 100%;"></div>
																</div>
															</div>
														</div>
		
														<!--</div>-->
													</div>
												</div>
											</div>
									</div>
								</div>
							</div>
							
					</div>
					
				</div>
			</div>
		</div>
	</div>
		<script src="${staticResourcePathH5}/js/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePathH5}/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePathH5}/js/echarts.4.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePathH5}/js/china.js?v=${SYSTEM_INIT_TIME}"></script>
    	<script src="${staticResourcePathH5}/js/vue.min.js?v=${SYSTEM_INIT_TIME}"></script>
    	<script src="${staticResourcePathH5}/js/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/moment.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePathH5}/js/bigEcharts.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${staticResourcePathH5}/js/swiper/swiper.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script src="${staticResourcePathH5}/js/hotLargeScreenVue/bigEventsSvreen.js?v=${SYSTEM_INIT_TIME}"></script>
	<script>
		$(function(){
			//页面 滚动设置
			var view = new Swiper('.big-swiper-1', {
				//				direction: 'vertical',
				//				paginationClickable: true,
				autoplay: 15000,
				speed: 1000,
				noSwiping: true,
				autoplayDisableOnInteraction: false,
				noSwiping: true,
				slidesPerView: 1,
				preventClicks: true, //默认true开启点击事件
				onSetTransition: function(swiper) {
					if(viewPage.isEnd) {
						$('.bigNav ul li').addClass("active");
					} else {
						$('.bigNav ul li').removeClass("active");
					}

					$(".bigNav ul li").removeClass("active");
					$(".bigNav ul li").eq(swiper.activeIndex).addClass("active");
				}

			})
			var viewPage = new Swiper('.big-module-swiper-1', {
				direction: 'vertical',
				autoplay: 3000,
				speed: 1000,
				noSwiping: true,
				autoplayDisableOnInteraction: false,
				noSwiping: true,
				slidesPerView: 1,
				preventClicks: true, //默认true开启点击事件
				onSetTransition: function(swiper) {
					if(viewPage.isEnd) {
						console.log($(this))
						$('.module-tabs ul li').eq(swiper.previousIndex).addClass("active");
					} else {
						$('.module-tabs ul li').eq(swiper.previousIndex).removeClass("active");
					}
					console.log($(this))
					$(".module-tabs ul li").eq(swiper.previousIndex).removeClass("active");
					$(".module-tabs ul li").eq(swiper.activeIndex).addClass("active");
				}

			})

			$('.bigNav ul li').on('click', function() {
				$(this).addClass('active').siblings().removeClass('active');
				var index = $(this).index();
    			view.slideTo(index);
			})
	
		})
	</script>
</body>

</html>