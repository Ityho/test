<#include "../common/resourcePath.ftl"/>
<#--热门人物板块——start-->
<div class="w-container-body">
    <div class="w-navigation">
        <div class="group-item bgTrans">
            <ul>
                <li>
                    <span>人物类型:</span>
                    <a href="javascript:;" :class="{active:labActive==-1}" @click="QBLab()">全部</a>
                    <a href="javascript:;" :class="{active:labActive==index}" v-for="(pl,index) in hotPeopleClassList" v-text="pl.typeName" v-if="index<=19" @click="cutLable(pl,index)"></a>
                    <span class="btn-more" v-show="hotPeopleClassList.length>20">更多<i class="icon icon-open inline-block"></i></span>
                </li>
                <li class="open" style="display: block">
                    <a href="javascript:;" :class="{active:labActive==inx}" v-for="(mpl,inx) in hotPeopleClassList" v-if="inx>19" v-text="mpl.typeName" @click="cutLable(mpl,inx)"></a>
                </li>
            </ul>
        </div>
        <div class="group-item bgTrans">
            <ul>
                <li>
                    <span>统计时间:</span>
                    <a href="javascript:;" class="active">24小时</a>
                    <a href="javascript:;">72小时</a>
                </li>
            </ul>
        </div>
        <div class="group-item bgTrans mTabs">
            <span>展示形式:</span>
            <ul id="myTab" class="clearfix">
                <li class="active">
                    <a href="#modelOne" data-toggle="tab" aria-expanded="true">人物VS单一事件对比</a>
                </li>
                <li>
                    <a href="#modelTwo" data-toggle="tab" aria-expanded="false" @click="tab(2)">人物整体热度对比</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="tab-content margin-top-15">
        <div class="tab-pane fade active in" id="modelOne">
            <div class="w-big-event clearfix">
                <!--左侧-->
                <div class="wb-primary" style="height: auto">
                    <div class="wb-p-content">
                        <div class="wb-p-card" style="max-height: 1184px;height: auto;">
                            <div id="autoBoxItem" class="autoScroll">
                                <div class="wb-p-item" v-for="(po,indPo) in peopleRankData">
                                    <div :class="xx==indPo?'cardHead active font-size-12':'cardHead font-size-12'" @click="ListOpen($event,po)">
                                        <span class="color_12 cardHead-num" v-text="(indPo+1)+'.'"></span>
                                        <div class="cardHead-avatar">
                                            <div class="avatar avatar-lg">
                                                <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                            </div>
                                            <div class="margin-left-15" v-if="po.labelName==='事件人物'">
                                                <p class="font-size-14" v-text="po.name"></p>
                                                <p class="color_10 margin-top-10" v-text="po.summary"></p>
                                            </div>
                                            <div class="margin-left-15" v-if="po.labelName!=='事件人物'">
                                                <p class="font-size-14" v-text="po.name"></p>
                                                <p class="color_10 margin-top-10" v-text="po.labelName"></p>
                                            </div>
                                        </div>
                                        <div class="cardHead-end">
                                            <p>热度值：<span class="font-size-18 font-weight-600" v-text="po.ratioHotDay | '0.00'"></span></p>
                                            <p class="trendColor_3 margin-top-5" v-text="po.ratioHotDay | 0.00"></p>
                                        </div>
                                        <a href="javascript:;" class="wb-arrow" style="top: 35px;"><i class="icon icon-arrow-downTwo"></i></a>
                                    </div>
                                    <div class="wb-sub-card" :style="xx==indPo?'display: block':'display: none'">
                                        <div class="wb-sub-item">
                                            <div class="hypotenuse-list scrollbar-1">
                                                <ul>
                                                    <li :class="{active:oo==indEv}" v-for="(ev,indEv) in po.hotIncidentList" @click="eventClick(ev,po,indEv)">
                                                        <div class="hypotenuse-list-item">
                                                            <div class="clearfix margin-bottom-30">
                                                                <p class="nowrap width-280 pull-left" v-text="(indEv+1)+':'+ev.incidentTitle" v-bind:title="ev.incidentTitle"></p>
                                                                <span class="pull-right">{{ev.createTime | formatDate()}}</span>
                                                            </div>
                                                            <p class="text-center margin-bottom-10"><span class="margin-right-50">信息量：<font class="margin-left-10" v-text="ev.numberDay | 0"></font></span><span>热度值：<font class="margin-left-10" v-text="(ev.ratioHotDay).toFixed(2)"></font></span></p>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
                <!--右侧-->
                <div class="wb-secondary" style="height: auto;">
                    <div class="wb-secondary-body">
                        <div class="cardHead-avatar padding-horizontal-20 padding-vertical-30">
                            <div class="avatar avatar-lg margin-0">
                                <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                            </div>
                            <div class="margin-left-15">
                                <p class="font-size-14 white">王思聪</p>
                                <p class="color_10 margin-top-10">熊猫TV投资人</p>
                            </div>
                        </div>
                        <div class="card-group row no-space clearfix">
                            <div class="col-md-12">
                                <p class="color_10 font-size-16">人物VS事件 热度指数变化趋势</p>
                                <div class="chart-bady">
                                    <div id="inforChart" style="width: 100%;height: 300px"></div>
                                </div>
                                <!--没有数据的情况下-->
                                <div class="no_data clear" style="display: none;min-height: 350px;padding-top: 100px">
                                    <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                    <p class="white font-size-14">很抱歉，目前没有数据</p>
                                </div>
                            </div>
                        </div>
                        <div class="card-group row no-space clearfix">
                            <div class="col-md-12">
                                <p class="font-size-16 white">人物VS事件 信息敏感量</p>
                                <div class="chart-bady margin-vertical-10">
                                    <div id="barEchartend" style="width: 100%;height: 650px"></div>
                                </div>
                                <!--没有数据的情况下-->
                                <div class="no_data clear" style="display: none;min-height: 350px;padding-top: 100px">
                                    <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                    <p class="white font-size-14">很抱歉，目前没有数据</p>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="tab-pane fade" id="modelTwo">
            <div class="w-person clearfix scrollbar-1">
                <!--左侧-->
                <div class="wb-primary">
                    <div class="wb-p-header">
                        <h3>人物整体热度排行榜</h3>
                    </div>
                    <div class="wb-p-content">
                        <div class="wb-p-card" style="height: auto;">
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">8.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">9.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">1.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                            <div class="wb-p-item">
                                <div class="cardHead active font-size-12">
                                    <span class="color_12 cardHead-num">13.</span>
                                    <div class="cardHead-avatar">
                                        <div class="avatar avatar-lg">
                                            <img src="${staticResourcePath}/css/indexV4/base/images/user.png" alt="">
                                        </div>
                                        <div class="margin-left-15">
                                            <p class="font-size-14">王思聪</p>
                                            <p class="color_10 margin-top-10">熊猫TV投资人</p>
                                        </div>
                                    </div>
                                    <div class="cardHead-end">
                                        <p>热度值：<span class="font-size-18 font-weight-600">77.87</span></p>
                                        <p class="trendColor_3 margin-top-5">24小时同比上升32.11</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--右侧-->
                <div class="wb-secondary" style="height: auto;padding-left: 0">
                    <div class="wb-secondary-body" style="background: rgba(1, 34, 116, 0.4);">
                        <div class="card-group row no-space clearfix">
                            <div class="col-md-12">
                                <div class="chart-bady">
                                    <div id="barEchart" style="width: 100%;height: 256px"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <!--没有数据的情况下-->
            <div class="no_data clear" style="display: none;background: rgba(1, 34, 116, 0.4);min-height: 350px;padding-top: 100px">
                <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                <p class="white font-size-14">很抱歉，目前没有数据</p>
            </div>
        </div>
    </div>

</div>
<#--<%--热门人物板块——end --%>-->
<script type="text/javascript" src="${staticResourcePath}/js/home/hotPeople.js?></script>


