<#include "../common/resourcePath.ftl"/>
	<div class="w-big-event clearfix">
        <!--左侧-->
        <div class="wb-primary">
            <div class="wb-p-header">
                <h3>
                    统计时间 <span class="color_5"><label v-text="startTime"></label>
						～ <label v-text="endTime"></label></span>
                </h3>
            </div>
            <div class="wb-p-content">
                <div id="scrollBtnDown" class="wb-p-top"></div>
                <div id="autoBox" class="wb-p-card">
                    <div id="autoBoxItem" class="autoScroll">
                        <div class="wb-p-item" v-for="bigEvent in activeBigEventList">
                            <div class="wbp-item-info"
                                 :class="{active:bigEvent.id==activeId}"
                                 @click="getHotIncidentList(bigEvent,1,1)">
                                <p class="margin-bottom-20 nowrap" v-text="bigEvent.name"></p>
                                <p>
									<span class="margin-right-20">7日内包含事件数：<label
                                            v-text="bigEvent.eventNum"></label></span> <span>24小时总信息量：<label v-if = "bigEvent.numberDay >= 10000"
                                                                                                             v-text="(bigEvent.numberDay / 10000).toFixed(2) + 'W'"></label>
										<label v-if = "bigEvent.numberDay < 10000" v-text="bigEvent.numberDay"></label>
									</span>
                                </p>
                                <a href="javascript:;" class="wb-arrow"><i
                                        class="icon icon-arrow-downTwo"></i></a>
                            </div>
                            <div class="wb-sub-card"
                                 :style="{display:bigEvent.id==activeId?'block':'none'}">
                                <div class="wb-sub-item">
                                    <div class="hypotenuse-tabs">
                                        <a class="tabs-item" :class="{active:sort==1}"
                                           @click="getHotIncidentList(bigEvent,1,0)" data-index="1"
                                           target="_blank"><i
                                                class="icon font-size-18 v_a_m margin-right-5 icon-jiangxu"></i>时间<span
                                                class="sort_1">降序</span></a> <a class="tabs-item"
                                                                                :class="{active:sort==3}"
                                                                                @click="getHotIncidentList(bigEvent,3,0)" data-index="1"
                                                                                target="_blank"><i
                                            class="icon font-size-18 v_a_m margin-right-5 icon-jiangxu"></i>信息量<span
                                            class="sort_1">降序</span></a> <a class="tabs-item"
                                                                            :class="{active:sort==2}"
                                                                            @click="getHotIncidentList(bigEvent,2,0)" data-index="1"
                                                                            target="_blank"><i
                                            class="icon font-size-18 v_a_m margin-right-5 icon-jiangxu"></i>热度均值<span
                                            class="sort_1">降序</span></a>
                                    </div>
                                    <div class="hypotenuse-list scrollbar-1">
                                        <ul>
                                            <li v-for="(hi,index) in hiList" :class="chooseControl==index?'active':''" @click="dotHotIncidentEchart(hi,index)">
                                                <div class="hypotenuse-list-item">
                                                    <p class="nowrap margin-bottom-20"
                                                       v-text="hi.incidentTitle"></p>
                                                    <p class="text-center margin-bottom-20">
														<span class="margin-right-50">信息量：<font
                                                                class="margin-left-10" v-text="hi.numberDay"></font></span><span>热度值：<font
                                                            class="margin-left-10"
                                                            v-text="hi.ratioHotDay.toFixed(2)"></font></span>
                                                    </p>
                                                    <p class="rel">
                                                        <i class="icon font-size-16 margin-right-5">&#xe6e2;</i><span
                                                            v-text="hi.province"></span> <span v-cloak>{{hi.createTime
															| formatDate('YYYY-MM-DD HH:mm')}}</span> <a @click ="goHotSearch(hi)"
                                                                                                         class="hypotenuse-see"><i
                                                            class="icon font-size-16 v_a_m margin-right-5">&#xe758;</i>查看</a>
                                                    </p>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="autoScrollend"></div>
                </div>
                <div id="scrollBtnUp" class="wb-p-bottom"></div>
            </div>
        </div>
        <!--右侧-->
        <div class="wb-secondary">
            <div class="wb-secondary-body">
                <div class="wb-secondary-head">
					<span class="badge badge-outline badge-primary"
                          v-text="activeHotEvent.labelNames"></span> <span><label
                        v-text="activeBigEvent.name"></label> > <label
                        v-text="activeHotEvent.incidentTitle" @click ="goHotSearch(activeHotEvent)"></label> </span>
                    <p class="actions">
                        <i class="icon font-size-16 margin-right-5">&#xe6e2;</i>
                        <label v-text="activeHotEvent.province"></label>
                        <!-- <span v-cloak>{{activeHotEvent.createTime
                            | formatDate('YYYY-MM-DD HH:mm')}}</span> -->
                    </p>
                </div>
                <!-- <p class="white font-size-14 p_v_20 p_h_10">
                    <i class="icon font-size-16 margin-right-5">&#xe6b5;</i>统计时间 <span
                        class="color_5 margin-left-20"><label v-text="startTime"></label>
                        ～ <label v-text="endTime"></label></span>
                </p> -->
                <p class="white font-size-14 p_v_20 p_h_10">
                    <i class="icon font-size-16 margin-right-5">&#xe6b5;</i>24小时数据概括
                </p>
                <div class="card-group bgColor_3">
                    <div class="card">
                        <div class="row row0">
                            <div class="card-widget col-md-3">
                                <p class="font-size-18 color_10"
                                   v-text="activeHotEvent.numberDay || 0"></p>
                                <p class="font-size-12 color_5 margin-top-5">信息总量</p>
                            </div>
                            <div class="card-widget col-md-3">
                                <p class="font-size-18 color_10">
                                    <label v-text="activeHotEvent.mgNumberDay || 0"></label>/<span
                                        class="trendColor_3" v-text="mgProportion"></span>
                                </p>
                                <p class="font-size-12 color_5 margin-top-5">敏感信息/占比</p>
                            </div>
                            <div class="card-widget col-md-3">
                                <p class="font-size-18 color_10" v-text="ratioHotAvg"></p>
                                <p class="font-size-12 trendColor_3 margin-top-5">热度均值</p>
                            </div>
                            <div class="card-widget col-md-3">
                                <p class="font-size-18 color_10">
                                    <label v-text="ratioHotTopCustom"></label>/<label v-cloak>{{ratioHotTopTime
                                    | formatDate('MM-DD HH:mm')}}</label>
                                </p>
                                <p class="font-size-12 trendColor_5 margin-top-5">热度峰值/时间</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-group row no-space clearfix margin-top-15">
                    <div class="col-md-12">
                        <p class="color_10 font-size-14">热度走势</p>
                        <div class="text-center white margin-vertical-10">
							<span class="font-size-12"><i class="legend-icon"></i>
								热度走势</span> <span class="font-size-12 margin-left-5"><i
                                class="legend-icon-average"></i>热度均值</span>
                        </div>

                        <div class="chart-bady margin-vertical-20" style="height: 300px">
                            <!--没有数据的情况下-->
                            <div class="no_data clear padding-vertical-25" v-show="!lineData">
                                <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                <p class="white font-size-14">很抱歉，目前没有数据</p>
                            </div>
                            <div id="bignessEventLine" style="width: 100%; height: 300px" v-show="lineData"></div>

                        </div>
                    </div>
                </div>
                <div class="card-group row no-space clearfix">
                    <div class="col-md-6" >
                        <p class="font-size-14 white">敏感占比</p>
                        <div id="bignessEventPie" style="width: 100%; height: 262px" v-show="pieData"></div>
                        <!--没有数据的情况下-->
                        <div class="no_data clear padding-vertical-25" v-show="!pieData">
                            <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                            <p class="white font-size-14">很抱歉，目前没有数据</p>
                        </div>
                    </div>

                    <div class="col-md-6" >
                        <p class="font-size-14 white">媒体来源发文</p>
                        <!--没有数据的情况下-->
                        <div class="no_data clear padding-vertical-25" v-show="!barData">
                            <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                            <p class="white font-size-14">很抱歉，目前没有数据</p>
                        </div>
                        <div id="bignessEventBar" style="width: 100%; height: 262px" v-show="barData"></div>
                    </div>

                </div>
            </div>
        </div>
    </div>
<script src="${staticResourcePath}/js/home/majorEvents.js?v=${sysConfig}" charset="utf-8"></script>
