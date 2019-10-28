<#include "../common/resourcePath.ftl"/>
<script>
    function mp() {
        var $form = $("#searchForm");
        $form.prop("action",actionBase + "/goSearch.shtml");
        $form.submit();
    }
    $("#noLoginCssId").attr("w-head abs headHover min-layout1200");
</script>
<!--搜索-->
<input type="hidden" id="isLogin" value="${islogin?c}">
            <div class="w-navigation">
                <div class="group-item">
                    <ul>
                        <li>
                            <span>热点类型:</span>
                            <a href="javascript:" :class="{active:activeTypeClass==-1}" @click="all()" id="qb">全部</a>
                            <a :class="{active:activeTypeClass == index}" href="javascript:void(0);"
                               v-for="(hcd,index) in newHotClassData" v-text="hcd.name" @click="changeParm(hcd,index)"></a>
                            <span class="btn-more" v-show="hotClassData.length>13">更多<i class="icon icon-open inline-block"></i></span>
                        </li>
                        <li class="open" style="display: none">
                            <a :class="{active:activeTypeClass == index}" href="javascript:void(0);" v-for="(hld,index) in hotClassData" v-if="index>13" v-text="hld.name" @click="changeParm(hld,index)"></a>
                        </li>
                    </ul>
                </div>
                <div class="group-item">
                    <ul>
                        <li>
                            <span>统计时间:</span>
                            <a href="javascript:" :class="{active:timeClass==1}" @click="changeTime(1)">24小时热点</a>
                            <a href="javascript:" :class="{active:timeClass==2}" @click="changeTime2(2)">72小时热点</a>
                        </li>
                    </ul>
                </div>
            </div>
            <!--数据-->
            <span id="goRankingList"></span>
            <div class="map-content clearfix" id="map-content" style="z-index: 2;">
                <div class="map-chart">
                    <div class="map-chart-body">
                        <div class="map-chart-bar">
                            <span class="color_1">热点地域分布</span>
                            <a href="javascript:" id="tt" class="color_5" data-toggle="tooltip" data-placement="top" title="" data-original-title="全网各省份热点事件的热度均值情况">
                                <#--<%--<i class="icon">&#xe694;</i>--%>-->
                            </a>
                            <span class="white font-size-14 margin-left-25">点击地图可查看各地域详细数据</span>
                            <div class="pull-right">
                                <a href="javascript:" class="switch-btn" @click="qh()">切至全国</a>
                            </div>
                        </div>
                        <div id="map-chart-content">
                            <div id="dataMap" style="z-index: 22;"></div>
                            <div class="w-loading" v-show="dt==false">
                                <div style="width:200px;height:120px;" class="bodymovin2"></div>
                                <p class="white font-size-14" style="margin-top: -20px">数据正在加载中...</p>
                            </div>
                            <!--全国-->
                            <div class="countryList">
                                <ul v-for="ind in number">
                                    <li v-for="(e,index) in mapData1" v-if="index%number==ind-1">
                                        <a href="javascript:" class="white" @click="eye(e)"><p>事件：{{e.event}}</p>
                                            <p>热度：{{parseFloat(e.hot).toFixed(2)}}</p>
                                            <div class="edgeHorn lt" v-if="index<number"></div>
                                            <div class="edgeHorn rt" v-if="index<number"></div>
                                            <div class="edgeHorn rb" v-if="index<number"></div>
                                            <div class="edgeHorn lb" v-if="index<number"></div></a>
                                    </li>
                                </ul>
                            </div>
                            <#--<%--<div id="mapMin" style="display: none;"></div>--%>-->
                            <div class="mapBg">
                                <img src="${staticResourcePath}/css/indexV4/base/images/map-rootbg.png" class="fa-spin12s" alt="">
                            </div>
                            <#--<%--<p class="font-size-12 white abs" style="bottom: 12px;">圆柱代表该省热度值的大小，圆柱越高，热度值越高。</p>--%>-->
                        </div>
                    </div>
                </div>
                <div class="map-aside">
                    <div class="map-aside-body">
                        <div class="horn lt"></div>
                        <div class="horn rt"></div>
                        <div class="horn rb"></div>
                        <div class="horn lb"></div>
                        <div class="map-title"></div>
                        <div class="module-map-title">
                            <span class="btn btn-warning" v-text="qg+':'"></span>
                            <div class="map-tabs">
                                <ul class="clearfix">
                                    <li class="active">
                                        <a href="javascript:" title="省份概况">数据概况</a>
                                    </li>
                                    <li id="h5">
                                        <a href="javascript:" title="省份概况">热点事件Top5</a>
                                    </li>
                                    <li id="b5">
                                        <a href="javascript:" title="省份概况">变化最大Top5</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="module-content module-map-body">
                            <div class="module-item active">
                                <div class="card-group bgColor_3">
                                    <div class="card">
                                        <p class="font-size-14 white">基础数据</p>
                                        <div class="row row0">
                                            <div class="card-widget col-md-4">
                                                <p class="font-size-20 color_10" v-text="(hotEventData.hotCount | 0)+'件'"></p>
                                                <p class="font-size-12 color_5 margin-top-5">事件数</p>
                                            </div>
                                            <div class="card-widget col-md-4">
                                                <p class="font-size-20 color_10" v-text="(hotEventData.hotAsc | 0)+'件'">0</p>
                                                <p class="font-size-12 trendColor_3 margin-top-5">热度上升</p>
                                            </div>
                                            <div class="card-widget col-md-4">
                                                <p class="font-size-20 color_10" v-text="(hotEventData.hotDesc | 0)+'件'">0</p>
                                                <p class="font-size-12 trendColor_5 margin-top-5">热度下降</p>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="card-group row clearfix margin-top-15">
                                    <div class="col-md-6 layout100">
                                        <p class="text-center color_10">24小时热度均值</p>
                                        <div class="chart-bady margin-vertical-20" v-show="rankData!=null">
                                            <div id="pieBox1" style="width: 100%;height: 140px"></div>
                                        </div>
                                        <!--没有数据的情况下-->
                                        <div class="no_data clear padding-vertical-25" v-show="rankData==null">
                                            <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                            <p class="white font-size-14">很抱歉，目前没有数据</p>
                                        </div>
                                    </div>
                                    <div class="col-md-6 layout100">
                                        <p class="text-center color_10">24小时热度均值变化</p>
                                        <div class="chart-bady margin-vertical-20" v-show="rankData!=null">
                                            <div id="pieBox2" style="width: 100%;height: 140px"></div>
                                        </div>
                                        <!--没有数据的情况下-->
                                        <div class="no_data clear padding-vertical-25" v-show="rankData==null">
                                            <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                            <p class="white font-size-14">很抱歉，目前没有数据</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-group" id="piezhan">
                                    <div class="rel">
                                        <p class="font-size-14 white">类型占比
                                            <span class="font-size-12 color_10 pull-right">统计选中省份下各类型事件的占比<i
                                                    class="icon margin-left-5">&#xe720;</i></span>
                                        </p>

                                    </div>
                                    <!--没有数据的情况下-->
                                    <div class="no_data clear padding-vertical-25" v-show="rankData==null">
                                        <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                        <p class="white font-size-14">很抱歉，目前没有数据</p>
                                    </div>
                                    <div id="pieBox3" style="width: 100%;height: 200px" v-show="rankData!=null"></div>
                                </div>
                                <p class="padding-horizontal-25 color_2 font-size-12 abs" style="bottom: 15px">
                                    点击事件标题可以查看热点详情。</p>
                            </div>
                            <div class="module-item">
                                <ul>
                                    <li class="module-map-item" v-for="(rd,index) in hotTopData" v-if="index<=4" v-show="rankData!=null">
                                        <div :class="x==index?'mmi-title active':'mmi-title'">
                                            <span v-text="(index+1)+'.'"></span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                             ( rd.labelNames.split(',').indexOf('明星')>-1
                                            || rd.labelNames.split(',').indexOf('电影')>-1
                                            || rd.labelNames.split(',').indexOf('电视剧')>-1
                                            || rd.labelNames.split(',').indexOf('综艺')>-1
                                            || rd.labelNames.split(',').indexOf('文化')>-1)
                                             && 30 <= (rd.ratioHotDay).toFixed(2)
                                             &&(rd.ratioHotDay).toFixed(2)< 50
                                             && 60<=rd.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                             (rd.labelNames.split(',').indexOf('明星')>-1
                                            || rd.labelNames.split(',').indexOf('电影')>-1
                                            || rd.labelNames.split(',').indexOf('电视剧')>-1
                                            || rd.labelNames.split(',').indexOf('综艺')>-1
                                            || rd.labelNames.split(',').indexOf('文化')>-1)
                                            &&  (rd.ratioHotDay).toFixed(2)>=50
                                            && rd.ratioHotTopCustom >= 80">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                             ( rd.labelNames.split(',').indexOf('明星')>-1
                                            || rd.labelNames.split(',').indexOf('电影')>-1
                                            || rd.labelNames.split(',').indexOf('电视剧')>-1
                                            || rd.labelNames.split(',').indexOf('综艺')>-1
                                            || rd.labelNames.split(',').indexOf('文化')>-1)
                                             && 15 <= (rd.ratioHotDay).toFixed(2)
                                             && (rd.ratioHotDay).toFixed(2)<30
                                             && 'rd.createTime | del()' < '2' ">新</span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                            rd.labelNames.split(',').indexOf('小事件')>-1
                                            && 1 <= (rd.ratioHotDay).toFixed(2)
                                            &&(rd.ratioHotDay).toFixed(2)<2
                                            && 10 <= rd.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                            rd.labelNames.split(',').indexOf('小事件')>-1
                                            && 2 <= (rd.ratioHotDay).toFixed(2)
                                            && rd.ratioHotTopCustom >= 20">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                            rd.labelNames.split(',').indexOf('小事件')>-1
                                            && 0.8 <= (rd.ratioHotDay).toFixed(2)
                                            &&(rd.ratioHotDay).toFixed(2) < 1
                                            && 'rd.createTime | del()' < '2'">新</span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                            rd.labelNames.split(',').indexOf('明星')==-1
                                            && rd.labelNames.split(',').indexOf('电影')==-1
                                            && rd.labelNames.split(',').indexOf('电视剧')==-1
                                            && rd.labelNames.split(',').indexOf('综艺')==-1
                                            && rd.labelNames.split(',').indexOf('文化')==-1
                                            && rd.labelNames.split(',').indexOf('小事件')==-1
                                            && 10 <= (rd.ratioHotDay).toFixed(2)
                                            &&(rd.ratioHotDay).toFixed(2) <= 30
                                            && 30 <= rd.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                             rd.labelNames.split(',').indexOf('明星')==-1
                                            && rd.labelNames.split(',').indexOf('电影')==-1
                                            && rd.labelNames.split(',').indexOf('电视剧')==-1
                                            && rd.labelNames.split(',').indexOf('综艺')==-1
                                            && rd.labelNames.split(',').indexOf('文化')==-1
                                            && rd.labelNames.split(',').indexOf('小事件')==-1
                                             && 30 <= (rd.ratioHotDay).toFixed(2)  && rd.ratioHotTopCustom >=60">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                             rd.labelNames.split(',').indexOf('明星')==-1
                                            && rd.labelNames.split(',').indexOf('电影')==-1
                                            && rd.labelNames.split(',').indexOf('电视剧')==-1
                                            && rd.labelNames.split(',').indexOf('综艺')==-1
                                            && rd.labelNames.split(',').indexOf('文化')==-1
                                            && rd.labelNames.split(',').indexOf('小事件')==-1
                                            && 5 <= (rd.ratioHotDay).toFixed(2)
                                            && (rd.ratioHotDay).toFixed(2)< 10
                                            && 'rd.createTime | del()' < '2'">新</span>
                                            <a href="javascript:" class="mmi-txt nowrap white" v-text="rd.incidentTitle" @click="eye(rd)"></a>
                                            <span><i class="icon color_9 margin-right-3">&#xe756;</i>{{parseFloat(rd.ratioHotDay).toFixed(2)}}</span>
                                            <span :class="parseFloat(rd.ratioHotDay-rd.ratioHotLastDay).toFixed(2)>0? 'color_9 margin-left-10':'trendColor_5 margin-left-10'"
                                                  v-text="parseFloat(rd.ratioHotDay-rd.ratioHotLastDay).toFixed(2)"></span>
                                            <a href="javascript:" class="inline-block arrow color_2 pull-right"
                                               @click="detailsBtn($event,index)"><i class="icon icon-open"></i></a>
                                        </div>
                                        <div class="chart-body clearfix" :style="x==index?'display: block;':'display:none'">
                                            <div :id="'pieE'+index" class="pull-left"
                                                 style="width: 50%;height: 100px"></div>
                                            <div :id="'barE'+index" class="pull-left"
                                                 style="width: 50%;height: 100px"></div>
                                        </div>
                                        <div class="mmi-footer">
                                            <span class="white margin-right-3" v-text="rd.labelNames"></span>
                                            <span><i class="icon margin-right-5">&#xe6e2;</i>{{rd.province}}</span>
                                        </div>
                                    </li>
                                    <!--没有数据的情况下-->
                                    <div class="no_data clear padding-vertical-25" v-show="rankData==null">
                                        <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                        <p class="white font-size-14">很抱歉，目前没有数据</p>
                                    </div>
                                </ul>
                                <p class="padding-horizontal-25 color_2 font-size-12 abs" style="bottom: 15px">
                                    点击事件标题可以查看热点详情.</p>
                            </div>
                            <div class="module-item">
                                <ul>
                                    <li class="module-map-item" v-for="(bh,index) in changeMaxDate" v-if="index<=4" v-show="rankData!=null">
                                        <div :class="y==index?'mmi-title active':'mmi-title'">
                                            <span v-text="(index+1)+'.'"></span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                             ( bh.labelNames.split(',').indexOf('明星')>-1
                                            || bh.labelNames.split(',').indexOf('电影')>-1
                                            || bh.labelNames.split(',').indexOf('电视剧')>-1
                                            || bh.labelNames.split(',').indexOf('综艺')>-1
                                            || bh.labelNames.split(',').indexOf('文化')>-1)
                                             && 30 <= (bh.ratioHotDay).toFixed(2)
                                             &&(bh.ratioHotDay).toFixed(2)< 50
                                             && 60<=bh.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                             (bh.labelNames.split(',').indexOf('明星')>-1
                                            || bh.labelNames.split(',').indexOf('电影')>-1
                                            || bh.labelNames.split(',').indexOf('电视剧')>-1
                                            || bh.labelNames.split(',').indexOf('综艺')>-1
                                            || bh.labelNames.split(',').indexOf('文化')>-1)
                                            &&  (bh.ratioHotDay).toFixed(2)>=50
                                            && bh.ratioHotTopCustom >= 80">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                             ( bh.labelNames.split(',').indexOf('明星')>-1
                                            || bh.labelNames.split(',').indexOf('电影')>-1
                                            || bh.labelNames.split(',').indexOf('电视剧')>-1
                                            || bh.labelNames.split(',').indexOf('综艺')>-1
                                            || bh.labelNames.split(',').indexOf('文化')>-1)
                                             && 15 <= (bh.ratioHotDay).toFixed(2)
                                             && (bh.ratioHotDay).toFixed(2)<30
                                             && 'bh.createTime | del()' < '2' ">新</span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                            bh.labelNames.split(',').indexOf('小事件')>-1
                                            && 1 <= (bh.ratioHotDay).toFixed(2)
                                            &&(bh.ratioHotDay).toFixed(2)<2
                                            && 10 <= bh.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                            bh.labelNames.split(',').indexOf('小事件')>-1
                                            && 2 <= (bh.ratioHotDay).toFixed(2)
                                            && bh.ratioHotTopCustom >= 20">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                            bh.labelNames.split(',').indexOf('小事件')>-1
                                            && 0.8 <= (bh.ratioHotDay).toFixed(2)
                                            &&(bh.ratioHotDay).toFixed(2) < 1
                                            && 'bh.createTime | del()' < '2'">新</span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                            bh.labelNames.split(',').indexOf('明星')==-1
                                            && bh.labelNames.split(',').indexOf('电影')==-1
                                            && bh.labelNames.split(',').indexOf('电视剧')==-1
                                            && bh.labelNames.split(',').indexOf('综艺')==-1
                                            && bh.labelNames.split(',').indexOf('文化')==-1
                                            && bh.labelNames.split(',').indexOf('小事件')==-1
                                            && 10 <= (bh.ratioHotDay).toFixed(2)
                                            &&(bh.ratioHotDay).toFixed(2) <= 30
                                            && 30 <= bh.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                             bh.labelNames.split(',').indexOf('明星')==-1
                                            && bh.labelNames.split(',').indexOf('电影')==-1
                                            && bh.labelNames.split(',').indexOf('电视剧')==-1
                                            && bh.labelNames.split(',').indexOf('综艺')==-1
                                            && bh.labelNames.split(',').indexOf('文化')==-1
                                            && bh.labelNames.split(',').indexOf('小事件')==-1
                                             && 30 <= (bh.ratioHotDay).toFixed(2)  && bh.ratioHotTopCustom >=60">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                             bh.labelNames.split(',').indexOf('明星')==-1
                                            && bh.labelNames.split(',').indexOf('电影')==-1
                                            && bh.labelNames.split(',').indexOf('电视剧')==-1
                                            && bh.labelNames.split(',').indexOf('综艺')==-1
                                            && bh.labelNames.split(',').indexOf('文化')==-1
                                            && bh.labelNames.split(',').indexOf('小事件')==-1
                                            && 5 <= (bh.ratioHotDay).toFixed(2)
                                            && (bh.ratioHotDay).toFixed(2)< 10
                                            && 'bh.createTime | del()' < '2'">新</span>
                                            <a href="javascript:" class="mmi-txt nowrap white" v-text="bh.incidentTitle" @click="eye(bh)"></a>
                                            <span><i class="icon color_9 margin-right-3">&#xe756;</i>{{parseFloat(bh.ratioHotDay).toFixed(2)}}</span>
                                            <span :class="parseFloat(bh.ratioHotDay-bh.ratioHotLastDay).toFixed(2)>0? 'color_9 margin-left-10':'trendColor_5 margin-left-10'" v-text="parseFloat(bh.ratioHotDay-bh.ratioHotLastDay).toFixed(2)"></span>
                                            <a href="javascript:" class="inline-block arrow color_2 pull-right"
                                               @click="detailsBtns($event,index)"><i class="icon icon-open"></i></a>
                                        </div>
                                        <div class="chart-body clearfix" :style="y==index?'display: block;':'display:none'">
                                            <div :id="'pieEchart1'+index" class="pull-left"
                                                 style="width: 50%;height: 100px"></div>
                                            <div :id="'barEchart1'+index" class="pull-left"
                                                 style="width: 50%;height: 100px"></div>
                                        </div>
                                        <div class="mmi-footer">
                                            <span class="white margin-right-3" v-text="bh.labelNames"></span>
                                            <span><i class="icon margin-right-5">&#xe6e2;</i>{{bh.province}}</span>
                                        </div>
                                    </li>
                                    <!--没有数据的情况下-->
                                    <div class="no_data clear padding-vertical-25" v-show="rankData==null">
                                        <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                        <p class="white font-size-14">很抱歉，目前没有数据</p>
                                    </div>
                                </ul>
                                <p class="padding-horizontal-25 color_2 font-size-12 abs" style="bottom: 15px">
                                    点击事件标题可以查看热点详情.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <#if !admin1??>
                <p class="text-center font-size-16 white" style="z-index: 22">
                    详细列表 <a id="login" href="javascript:void(0);" onclick="getRan()" data-toggle="modal"
                            data-target="#loginModal" class="color_1">登录</a>后可查看
                </p>
                <!--大数据解读-->
                <div class="w-layout">
                    <section class="section-panel">
                        <div class="section-panel-title">
                            <h3>大数据解读</h3>
                            <div class="section-panel-action">
                                <#--<a href="<%=njxBasePath%>/infoData.shtml">更多</a>-->
                                    <a href="${staticResourcePath}/infoData.shtml">更多</a>
                            </div>
                        </div>
                        <div class="section-panel-body row clearfix">
                            <div class="col-md-3" v-for="item in bigDataRead" v-show="bigDataRead.length<=4">
                                <div class="card-wrap-item card-shadow-hover ">
                                    <a href="javascript:void(0);">
                                        <div class="boxImg">
                                            <img :src="item.imageUrl"
                                                 alt="">
                                        </div>
                                        <div class="card-intro-box">
                                            <div class="clearfix">
                                                <span class="badge badge-outline badge-warning pull-left"
                                                      v-text="item.typeValue" v-if="item.typeValue!=null"></span>
                                                <span class="color_3 pull-right font-size-12" style="line-height: 20px">{{item.createTime | formatDate('YYYY-MM-DD')}}</span>
                                            </div>
                                            <div class="nowrap color_3 font-size-14 margin-vertical-10"
                                                 title="item.title" v-text="item.title">
                                            </div>
                                            <p class="card-intro-txt"
                                               title="" v-text="item.abstracts"></p>
                                        </div>
                                        <div class="btn-end" @click="goBlackMore(item.articleUrl)">
                                            <span>详情></span>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </section>
                    <section class="section-panel">
                        <div class="section-panel-title">
                            <h3>解决方案</h3>
                        </div>
                        <div class="section-panel-body row clearfix">
                            <div class="col-md-4">
                                <div class="card-wrap-item card-shadow-hover">
                                    <a href="javascript:">
                                        <div class="bg-white">
                                            <div class="padding-10">
                                                <div>
                                                    <img src="" alt="">
                                                    <span class="color_8 font-size-16">新浪舆情通</span>
                                                    <span class="badge badge-primary pull-right">专业</span>
                                                </div>
                                                <p class="card-intro-txt2 padding-vertical-5"
                                                   title="从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。">
                                                    政企舆情大数据服务平台。全网11大信息源，更有微博独家授权全量政务舆情数据，致力于为客户提供全网舆情监测、舆情预警、舆情大数据分析、舆情报告等全方位的舆情服务。
                                                </p>
                                            </div>
                                            <a href="https://www.yqt365.com/goToNewGeneralizeMain.action?searchFrom=0&logonOrRegister=0&webGamesType=9&industryType=1">
                                                <div class="btn-end">
                                                    <span>前往</span>
                                                </div>
                                            </a>
                                        </div>
                                    </a>
                                </div>

                            </div>
                            <div class="col-md-4">
                                <div class="card-wrap-item card-shadow-hover">
                                    <a href="javascript:">
                                        <div class="bg-white">
                                            <div class="padding-10">
                                                <div>
                                                    <img src="" alt="">
                                                    <span class="color_8 font-size-16">U媒</span>
                                                    <span class="badge badge-success pull-right">高效</span>
                                                </div>
                                                <p class="card-intro-txt2 padding-vertical-5"
                                                   title="从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。">
                                                    7个平台一键通发节省时间、3级审核杜绝内容风险、7*24小时账号数据监测、20余项数据维度运营评估……为提升政务账号服务效能、增强企业品牌形象提供新媒体账号运营一站式解决方案。
                                                </p>
                                            </div>
                                            <a href="https://www.u-mei.com/web/index.html?extendtype=030011">
                                                <div class="btn-end">
                                                    <span>前往</span>
                                                </div>
                                            </a>
                                        </div>

                                    </a>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card-wrap-item card-shadow-hover">
                                    <a href="javascript:">
                                        <div class="bg-white">
                                            <div class="padding-10">
                                                <div>
                                                    <img src="" alt="">
                                                    <span class="color_8 font-size-16">行业解决方案</span>
                                                    <span class="badge badge-warning pull-right">精准</span>
                                                </div>
                                                <p class="card-intro-txt2 padding-vertical-5"
                                                   title="从 2015 年 4 月起，Ant Design 在蚂蚁金服中后台产品线迅速推广，对接多条业务线，覆盖系统 800 个以上。">
                                                    深度了解各行业的特性，将行业数据进行定性分析、定量分析、数据挖掘等技术，为行业发展和服务提升提供高效帮助。目前已为汽车、检察院、食品药品、教育等多个行业提供解决方案。
                                                </p>
                                            </div>
                                            <a href="http://wpa.qq.com/msgrd?v=3&uin=3002432217&site=qq&menu=yes" target="_blank">
                                                <div class="btn-end">
                                                    <span>前往</span>
                                                </div>
                                            </a>
                                        </div>
                                    </a>
                                </div>

                            </div>
                        </div>
                    </section>
                </div>
            <#else >
                <div class="module-data" id="module-data">
                    <div class="w-navigation">
                        <div class="group-item">
                            <ul>
                                <li>
                                    <span v-text="qg+':'"></span>
                                    <a href="javascript:" :class="{active:hotClass==1}" @click="hotSort()">24小时热度指数降序</a>
                                    <a href="javascript:" :class="{active:hotClass==2}" @click="chartSort()">24小时指数变化降序</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="module-data-info clearfix">
                        <div class="module-data-table">
                            <table class="tableCase" border="0" cellspacing="0" cellpadding="0">
                                <thead>
                                <tr>
                                    <th width="5%"></th>
                                    <th width="330">事件名称</th>
                                    <th width="10%">热度指数</th>
                                    <th width="120">24小时同比变化</th>
                                    <th width="20%">类型</th>
                                    <th width="8%">地域</th>
                                    <th width="5%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr :class="activeClass == index ? 'active':''" v-for="(rank,index) in rankData"
                                    @click="XQ(rank,index)">
                                    <td><span v-text="(index+1)+'.'"></span></td>
                                    <#--<td>-->
                                        <#--<span class="badge badge-outline badge-green-300 margin-right-3" v-for="x in rank.labelNames.split(',')">{{x}}</span>-->
                                    <#--</td>-->
                                    <#--<td v-text="rank.province"></td>-->
                                    <#--<td>-->
                                        <#--<a href="javascript:" class="nowrap width-250 inline-block white" @click="eye(rank)" v-bind:title="rank.incidentTitle" v-text="rank.incidentTitle"></a>-->
                                    <#--</td>-->
                                    <td class="text-left">
                                        <a href="javascript:" class="inline-block white" @click="eye(rank)" v-bind:title="rank.incidentTitle">
                                            <span class="inline-block v_a_m"  v-if="rank.incidentTitle.length>17">{{rank.incidentTitle.substring(0,17)+'...'}}</span>
                                            <span class="inline-block v_a_m"  v-if="rank.incidentTitle.length<=17">{{rank.incidentTitle}}</span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                             ( rank.labelNames.split(',').indexOf('明星')>-1
                                            || rank.labelNames.split(',').indexOf('电影')>-1
                                            || rank.labelNames.split(',').indexOf('电视剧')>-1
                                            || rank.labelNames.split(',').indexOf('综艺')>-1
                                            || rank.labelNames.split(',').indexOf('文化')>-1)
                                             && 30 <= (rank.ratioHotDay).toFixed(2)
                                             &&(rank.ratioHotDay).toFixed(2)< 50
                                             && 60<=rank.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                             (rank.labelNames.split(',').indexOf('明星')>-1
                                            || rank.labelNames.split(',').indexOf('电影')>-1
                                            || rank.labelNames.split(',').indexOf('电视剧')>-1
                                            || rank.labelNames.split(',').indexOf('综艺')>-1
                                            || rank.labelNames.split(',').indexOf('文化')>-1)
                                            &&  (rank.ratioHotDay).toFixed(2)>=50
                                            && rank.ratioHotTopCustom >= 80">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                             ( rank.labelNames.split(',').indexOf('明星')>-1
                                            || rank.labelNames.split(',').indexOf('电影')>-1
                                            || rank.labelNames.split(',').indexOf('电视剧')>-1
                                            || rank.labelNames.split(',').indexOf('综艺')>-1
                                            || rank.labelNames.split(',').indexOf('文化')>-1)
                                             && 15 <= (rank.ratioHotDay).toFixed(2)
                                             && (rank.ratioHotDay).toFixed(2)<30
                                             && 'rank.createTime | del()' < '2' ">新</span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                            rank.labelNames.split(',').indexOf('小事件')>-1
                                            && 1 <= (rank.ratioHotDay).toFixed(2)
                                            &&(rank.ratioHotDay).toFixed(2)<2
                                            && 10 <= rank.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                            rank.labelNames.split(',').indexOf('小事件')>-1
                                            && 2 <= (rank.ratioHotDay).toFixed(2)
                                            && rank.ratioHotTopCustom >= 20">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                            rank.labelNames.split(',').indexOf('小事件')>-1
                                            && 0.8 <= (rank.ratioHotDay).toFixed(2)
                                            &&(rank.ratioHotDay).toFixed(2) < 1
                                            && 'rank.createTime | del()' < '2'">新</span>
                                            <span class="badge badge-warning margin-right-5 v_a_m" v-if="
                                            rank.labelNames.split(',').indexOf('明星')==-1
                                            && rank.labelNames.split(',').indexOf('电影')==-1
                                            && rank.labelNames.split(',').indexOf('电视剧')==-1
                                            && rank.labelNames.split(',').indexOf('综艺')==-1
                                            && rank.labelNames.split(',').indexOf('文化')==-1
                                            && rank.labelNames.split(',').indexOf('小事件')==-1
                                            && 10 <= (rank.ratioHotDay).toFixed(2)
                                            &&(rank.ratioHotDay).toFixed(2) <= 30
                                            && 30 <= rank.ratioHotTopCustom">热</span>
                                            <span class="badge badge-red margin-right-5 v_a_m" v-if="
                                             rank.labelNames.split(',').indexOf('明星')==-1
                                            && rank.labelNames.split(',').indexOf('电影')==-1
                                            && rank.labelNames.split(',').indexOf('电视剧')==-1
                                            && rank.labelNames.split(',').indexOf('综艺')==-1
                                            && rank.labelNames.split(',').indexOf('文化')==-1
                                            && rank.labelNames.split(',').indexOf('小事件')==-1
                                             && 30 <= (rank.ratioHotDay).toFixed(2)  && rank.ratioHotTopCustom >=60">爆</span>
                                            <span class="badge badge-green-500 margin-right-5 v_a_m" v-if="
                                             rank.labelNames.split(',').indexOf('明星')==-1
                                            && rank.labelNames.split(',').indexOf('电影')==-1
                                            && rank.labelNames.split(',').indexOf('电视剧')==-1
                                            && rank.labelNames.split(',').indexOf('综艺')==-1
                                            && rank.labelNames.split(',').indexOf('文化')==-1
                                            && rank.labelNames.split(',').indexOf('小事件')==-1
                                            && 5 <= (rank.ratioHotDay).toFixed(2)
                                            && (rank.ratioHotDay).toFixed(2)< 10
                                            && 'rank.createTime | del()' < '2'">新</span>

                                        </a>
                                    </td>
                                    <td>
                                        <#--<div class="w-progress bg-warning width-100">-->
                                            <#--<div class="w-progress-bar" :style="'width:' + rank.ratioHotDay + '%'">-->
                                            <#--</div>-->
                                        <#--</div>-->
                                            <a style="color: white" v-text="parseFloat(rank.ratioHotDay).toFixed(2)"></a>
                                    </td>
                                    <td :class="parseFloat(rank.ratioHotDay-rank.ratioHotLastDay).toFixed(2)>=0? 'color_9':'trendColor_5'" v-text="parseFloat(rank.ratioHotDay-rank.ratioHotLastDay).toFixed(2)"></td>
                                    <td>
                                        <span class="badge badge-outline badge-green-300 margin-right-3" v-for="x in rank.labelNames.split(',')">{{x}}</span>
                                    </td>
                                    <td v-text="rank.province"></td>
                                    <td><a href="javascript:" class="btn-see"><i class="icon" @click="eye(rank)">&#xe758;</i></a></td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="w-loading" v-show="zt==false">
                                <div style="width:200px;height:120px;" class="bodymovin"></div>
                                <p class="white font-size-14" style="margin-top: -20px">数据正在加载中...</p>
                            </div>
                            <!--没有数据的情况下-->
                            <div class="no_data clear padding-top-150" :style="rankData==null?'':'display: none'">
                                <div style="width:300px;height:194px;" id="bodymovin"></div>
                                <p class="white font-size-14">很抱歉，目前没有数据</p>
                            </div>

                        </div>
                        <div class="module-data-item">
                            <div class="card-group">
                                <p class="font-size-14 white rel" v-show="rankData!=null">
                                    <#--<%--<span v-text="indexData.index+1+':'"></span>--%>-->
                                    {{indexData.title}}
                                    <span class="font-size-14 white pull-right">{{indexData.time | formatDate('YYYY-MM-DD')}}</span>
                                </p>
                                <!--没有数据的情况下-->
                                <div class="no_data clear padding-vertical-50" v-show="rankData==null">
                                    <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                    <p class="white font-size-14">很抱歉，目前没有数据</p>
                                </div>
                                <div class="text-center margin-vertical-30" v-show="rankData!=null">
                                    <div class="inline-block">
                                        <p class="font-size-12 white text-center margin-bottom-10">热度指数</p>
                                        <div class="rel inline-block">
                                            <div id="pie-g1" class="pie-g1"
                                                 style="width: 140px;height: 140px;z-index: 1"></div>
                                            <img src="${staticResourcePath}/css/indexV4/base/images/pie-g1.png"
                                                 class="abs fa-spin4s" style="width: 100%;top: 0;left: 0;z-index: 0" alt="">
                                        </div>
                                    </div>
                                    <div class="inline-block">
                                        <p class="font-size-12 white text-center margin-bottom-10">24小时同比变化</p>
                                        <div class="rel inline-block">
                                            <div id="pie-g3" class="pie-g3"
                                                 style="width: 140px;height: 140px;z-index: 1"></div>
                                            <img src="${staticResourcePath}/css/indexV4/base/images/pie-g3.png"
                                                 class="abs fa-spin4s" style="width: 100%;top: 0;left: 0;z-index: 0" alt="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-group">
                                <p class="font-size-14 white rel">
                                    热度走势 :
                                </p>
                                <div class="text-center white margin-vertical-30">
                                    <span class="font-size-12"><i class="legend-icon"></i> 24小时热度趋势</span>
                                    <span class="font-size-12 margin-left-5"><i class="legend-icon-average"></i> 24小时热度均值</span>
                                </div>
                                <div id="informationChart" style="width: 100%;height: 350px" v-show="rankData!=null"></div>
                                <!--没有数据的情况下-->
                                <div class="no_data clear padding-vertical-50" v-show="rankData==null">
                                    <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                    <p class="white font-size-14">很抱歉，目前没有数据</p>
                                </div>
                            </div>
                            <div class="card-group row no-space clearfix">
                                <div class="col-md-12">
                                    <p class="font-size-14 white">
                                        敏感占比
                                    </p>
                                    <!--没有数据的情况下-->
                                    <div class="no_data clear padding-vertical-50" v-show="rankData==null">
                                        <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                        <p class="white font-size-14">很抱歉，目前没有数据</p>
                                    </div>
                                    <div id="pieEchartend" style="width: 100%;height:252px" v-show="rankData!=null"></div>
                                </div>
                            </div>
                            <div class="card-group row no-space clearfix">
                                <div class="col-md-12">
                                    <p class="font-size-14 white ">
                                        媒体来源发文
                                    </p>
                                    <!--没有数据的情况下-->
                                    <div class="no_data clear padding-vertical-50" v-show="rankData==null">
                                        <img src="${staticResourcePath}/css/indexV4/global/img/no-data.png" alt="">
                                        <p class="white font-size-14">很抱歉，目前没有数据</p>
                                    </div>
                                    <div id="lineEchartend" style="width: 100%;height:252px" v-show="rankData!=null"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </#if>
<#--<%--新版--%>-->



<script type="text/javascript" src="${staticResourcePath}/js/home/hotEvent.js"></script>
