
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/base.css">
    <link rel="stylesheet" href="/css/font-icon.css">
    <link rel="stylesheet" href="/css/site.css">
    <link rel="stylesheet" href="/js/swiper/swiper.min.css">
    <link rel="stylesheet" href="/css/hot.css">
    <script src="/js/jquery-1.10.2.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/swiper/swiper.min.js"></script>
    <title>热点发现</title>


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
                    <li class="active">
                        <a href="hotEvents.html">热门事件</a>
                    </li>
                    <li class="">
                        <a href="bigEvents.html">重大事件</a>
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
            <div class="screen-body height-full" id="hotLargeScreen">
                <div class="item-side rel active height-full">
                    <!-- 第一页 -->
                    <div class="hotNav">
                        <span>热点类型：</span>
                        <a href="javascript:;" class="active">全部</a>
                        <a href="javascript:;">政务</a>
                        <a href="javascript:;">经济</a>
                        <a href="javascript:;">国际</a>
                        <a href="javascript:;">法制</a>
                        <a href="javascript:;">教育</a>
                        <a href="javascript:;">商业</a>
                        <a href="javascript:;">民生</a>
                        <a href="javascript:;">医疗</a>
                        <a href="javascript:;">交通</a>
                        <a href="javascript:;">文娱</a>
                        <a href="javascript:;">体育</a>
                        <span class="float_r color_1 font-size-20">*数据统计时间：24小时</span>
                    </div>
                    <!-- 地图 -->
                    <div class="module module-map item inline-block">
                        <!-- 边框 -->
                        <div class="line">
                            <div id="mapEcharts" style="height: 100%;width:100%;"></div>
                        </div>

                    </div>
                    <!-- 数据详情热点均值 -->
                    <div class="module hot-left inline-block">
                        <div class="item inline-block hotData1">
                            <div class="line">
                                <div class="module-title">
                                    数据详情
                                </div>
                                <div class="module-body">
                                    <div class="eventNum">
                                        <span>8</span><span>7</span><span class="mr0">7</span>
                                        <p class="color_1 font-size-26 margin-top-20">事件数 / 件</p>
                                    </div>
                                    <div>
                                        <div class="inline-block text-center w50">
                                            <p class="font-size-44 color_2">623<i class="icon font-size-40">&#xe903;</i>
                                            </p>
                                            <p class="font-size-20 color_1">热度上升 / 件</p>
                                        </div>
                                        <div class="inline-block text-center w50">
                                            <p class="font-size-44 color_3">245<i class="icon font-size-40">&#xe902;</i>
                                            </p>
                                            <p class="font-size-20 color_1">热度下降 / 件</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="wui-nodata">
                                    <img src="/images/nodata.svg" alt="">
                                    <p>当前暂无数据哦～</p>
                                </div>
                            </div>

                        </div>
                        <div class="item inline-block hotData2">
                            <div class="line">
                                <div class="module-title">热点均值</div>
                                <div class="module-line">
                                    <div class="inline-block w50 text-center">
                                        <p class="color_5 font-size-18">24小时热度均值</p>
                                        <div class="circular circular-blue animation-roll1">100.00</div>
                                    </div>
                                    <div class="inline-block w50 text-center">
                                        <p class="color_5 font-size-18">24小时热度均值变化</p>
                                        <div class="circular circular-pink animation-roll2">+99.00</div>
                                    </div>
                                </div>
                                <div class="wui-nodata">
                                    <img src="/images/nodata.svg" alt="">
                                    <p>当前暂无数据哦～</p>
                                </div>
                            </div>

                        </div>
                    </div>
                    <!-- 1小时热点事件盘点 -->
                    <div class="module hotList item inline-block">
                        <div class="line">
                            <div class="module-title">1小时热点事件盘点</div>
                            <div style="z-index: 2;">
                                <table class="table hot-table">
                                    <tbody>
                                    <th></th>
                                    <th>事件名称</th>
                                    <th>热度指数</th>
                                    <th>1h同比变化</th>
                                    <th>类型</th>
                                    <th>地域</th>
                                    </tbody>
                                    <tbody>
                                    <tr>
                                        <td><span class="color_4">1</span></td>
                                        <td><span class="color_0">孙杨世锦赛400自项目4连冠</span> <span class="badge badge-outline badge-primary badge-circular">热</span>
                                        </td>
                                        <td><span class="color_4">98.34</span></td>
                                        <td><span class="color_3">-22.22</span></td>
                                        <td>赛事&nbsp;&nbsp;体育&nbsp;&nbsp; 政治</td>
                                        <td>全国</td>
                                    </tr>
                                    <tr>
                                        <td><span class="color_4">2</span></td>
                                        <td><span class="color_0">孙杨世锦赛400自项目4连冠</span> <span class="badge badge-outline badge-warning badge-circular">爆</span>
                                        </td>
                                        <td><span class="color_4">98.34</span></td>
                                        <td><span class="color_2">+41.92</span></td>
                                        <td>赛事&nbsp;&nbsp;体育&nbsp;&nbsp; 政治</td>
                                        <td>全国</td>
                                    </tr>
                                    <tr>
                                        <td><span class="color_4">3</span></td>
                                        <td><span class="color_0">孙杨世锦赛400自项目4连冠</span> <span class="badge badge-outline badge-success badge-circular">新</span>
                                        </td>
                                        <td><span class="color_4">98.34</span></td>
                                        <td><span class="color_2">+41.92</span></td>
                                        <td>赛事&nbsp;&nbsp;体育&nbsp;&nbsp; 政治</td>
                                        <td>全国</td>
                                    </tr>
                                    <tr>
                                        <td><span class="color_4">1</span></td>
                                        <td><span class="color_0">孙杨世锦赛400自项目4连冠</span> <span class="badge badge-outline badge-primary badge-circular">热</span>
                                        </td>
                                        <td><span class="color_4">98.34</span></td>
                                        <td><span class="color_3">-22.22</span></td>
                                        <td>赛事&nbsp;&nbsp;体育&nbsp;&nbsp; 政治</td>
                                        <td>全国</td>
                                    </tr>
                                    <tr>
                                        <td><span class="color_4">2</span></td>
                                        <td><span class="color_0">孙杨世锦赛400自项目4连冠</span> <span class="badge badge-outline badge-warning badge-circular">爆</span>
                                        </td>
                                        <td><span class="color_4">98.34</span></td>
                                        <td><span class="color_2">+41.92</span></td>
                                        <td>赛事&nbsp;&nbsp;体育</td>
                                        <td>全国</td>
                                    </tr>
                                    <tr>
                                        <td><span class="color_4">3</span></td>
                                        <td><span class="color_0">孙杨世锦赛400自项目4连冠</span> <span class="badge badge-outline badge-success badge-circular">新</span>
                                        </td>
                                        <td><span class="color_4">98.34</span></td>
                                        <td><span class="color_2">+41.92</span></td>
                                        <td>赛事&nbsp;&nbsp;体育</td>
                                        <td>全国</td>
                                    </tr>
                                    <tr>
                                        <td><span class="color_4">3</span></td>
                                        <td><span class="color_0">孙杨世锦赛400自项目4连冠</span> <span class="badge badge-outline badge-success badge-circular">新</span>
                                        </td>
                                        <td><span class="color_4">98.34</span></td>
                                        <td><span class="color_2">+41.92</span></td>
                                        <td>赛事&nbsp;&nbsp;体育</td>
                                        <td>全国</td>
                                    </tr>
                                    <tr>
                                        <td><span class="color_4">3</span></td>
                                        <td><span class="color_0">孙杨世锦赛400自项目4连冠</span> <span class="badge badge-outline badge-success badge-circular">新</span>
                                        </td>
                                        <td><span class="color_4">98.34</span></td>
                                        <td><span class="color_2">+41.92</span></td>
                                        <td>赛事</td>
                                        <td>全国</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="wui-nodata">
                                <img src="/images/nodata.svg" alt="">
                                <p>当前暂无数据哦～</p>
                            </div>
                        </div>

                    </div>
                    <!-- 变化最大TOP5 -->
                    <div class="module hotTopList item inline-block">
                        <div class="line">
                            <div class="module-title">变化最大TOP5</div>
                            <div class="hotTopScroll swiper-container swiper-media-1 swiper-container-vertical">
                                <ul class="swiper-wrapper">
                                    <li class="swiper-slide">
                                        <div class="card">
                                            <div class="card-title"><span class="color_4 margin-right-15 v-a-m">1</span><span class="nowrap inline-block" style="width: 85%">霍顿颁奖礼拒与孙杨合影</span></div>
                                            <div class="module-line margin-bottom-0">
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-blue">2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值</p>
                                                </div>
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-pink">+2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值变化</p>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="swiper-slide">
                                        <div class="card">
                                            <div class="card-title"><span class="color_4 margin-right-15 v-a-m">2</span><span class="nowrap inline-block" style="width: 85%">霍顿颁奖礼拒与孙杨合影</span></div>
                                            <div class="module-line margin-bottom-0">
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-blue">2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值</p>
                                                </div>
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-pink">+2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值变化</p>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="swiper-slide">
                                        <div class="card">
                                            <div class="card-title"><span class="color_4 margin-right-15 v-a-m">3</span><span class="nowrap inline-block" style="width: 85%">霍顿颁奖礼拒与孙杨合影</span></div>
                                            <div class="module-line margin-bottom-0">
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-blue">2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值</p>
                                                </div>
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-pink">+2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值变化</p>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="swiper-slide">
                                        <div class="card">
                                            <div class="card-title"><span class="margin-right-15 v-a-m">4</span><span class="nowrap inline-block" style="width: 85%">霍顿颁奖礼拒与孙杨合影</span></div>
                                            <div class="module-line margin-bottom-0">
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-blue">2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值</p>
                                                </div>
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-pink">+2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值变化</p>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="swiper-slide">
                                        <div class="card">
                                            <div class="card-title"><span class="margin-right-15 v-a-m">5</span><span class="nowrap inline-block" style="width: 85%">霍顿颁奖礼拒与孙杨合影</span></div>
                                            <div class="module-line margin-bottom-0">
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-blue">2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值</p>
                                                </div>
                                                <div class="inline-block w50 text-center">
                                                    <div class="hotImg hotImg-pink">+2.03</div>
                                                    <p class="color_1 font-size-18">24小时热度均值变化</p>
                                                </div>
                                            </div>
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
<script src="/js/echarts.4.2.min.js"></script>
<script src="/js/china.js"></script>
<script src="/js/hotEcharts.js"></script>
<script src="/js/vue.min.js"></script>
<script src="/js/vue-resource.min.js"></script>
<script src="/js/hotLargeScreenVue/hotLargeScreen.js"></script>
<script>
    //热门事件top5
    var swiper = new Swiper('.swiper-media-1', {
        direction: 'vertical',
        //loop: true,
        preventClicks: false, //默认true
        preventLinksPropagation: true,
        slidesPerView: 2,
        autoplay: 5000,
        noSwiping: true,
        speed: 1000,
        autoplayDisableOnInteraction: false,
        paginationClickable: true,
        spaceBetween: 0,
        mousewheelControl: false
    });
    //			var swiper = new Swiper('.big-swiper-2', {
    //    spaceBetween: 30,
    //    centeredSlides: true,
    //    autoplay: {
    //      delay: 2500,
    //      disableOnInteraction: false,
    //    },
    //    pagination: {
    //      el: '.swiper-pagination',
    //      clickable: true,
    //    },
    //    navigation: {
    //      nextEl: '.swiper-button-next',
    //      prevEl: '.swiper-button-prev',
    //    },
    //  });

</script>
</body>

</html>