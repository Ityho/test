/**
 * Created by sty on 2018/6/27.
 */
var app = new Vue({
    el:'#bigEvent',
    filters:{
        // 处理时间戳
        formatDate: function (date, formatStr) {
            if(!date || date == "-"){
                return date;
            }
            var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
            return moment(date).format(formatStr);
        }
    },
    data:{
        allBigEventList:[],
        startTime:moment().add(-1, 'week').format('YYYY-MM-DD'),
        endTime:moment().format('YYYY-MM-DD'),
        bigEventIndex:0,
        hotEventIndex:0,
    },
    created:function(){
        this.getBignessEventList();
    },
    methods:{
        getBignessEventList:function(){
            //var searchObj = {startTime:moment().add(-1, 'week').format('YYYY-MM-DD HH:mm:ss'),endTime:moment().format('YYYY-MM-DD HH:mm:ss')};
            var searchObj = {startTime:"2000-10-15 14:00:00",endTime:'2019-10-15 14:00:00'};
            $.post(actionBase+'/largeScreen/getBigEventList',searchObj,function(result){
                if(result.code == "0000"){

                    var bigList = [];
                    for(var i = 0;i<result.data.length;i++){
                        var bigEvent = result.data[i];
                        var hotList = [];
                        for(var z = 0;z<bigEvent.hotIncidentList.length;z++){
                            var hotEvent = bigEvent.hotIncidentList[z];
                            var lineData = JSON.parse(hotEvent.lineData);
                            if (lineData != null){
                                var topTotalTime='';
                                var topTotal= -1;
                                for (var x = 0; x < lineData.length; x++) {
                                    var total = parseFloat(lineData[x].total);
                                    if (topTotal == -1){
                                        topTotal= total;
                                    }else {
                                        if(total > topTotal){
                                            topTotal= total;
                                            topTotalTime = lineData[x].name;
                                        }
                                    }
                                }

                                hotEvent.topTotalTime = topTotalTime;
                            } else {
                                hotEvent.topTotalTime = '-';
                            }
                            hotList.push(hotEvent);
                        }
                        bigEvent.hotIncidentList = hotList;
                        bigList.push(bigEvent);
                    }
                    app.allBigEventList =bigList;
                    Vue.nextTick(function(){
                        var view = new Swiper('.big-swiper-1', {
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

                                app.bigEventIndex =  swiper.activeIndex;
                                app.onChooseIndex(app.bigEventIndex,app.hotEventIndex);
                            }

                        });
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

                                app.hotEventIndex =  swiper.activeIndex;
                                app.onChooseIndex(app.bigEventIndex,app.hotEventIndex);
                            }

                        });

                        $('.bigNav ul li').on('click', function() {
                            $(this).addClass('active').siblings().removeClass('active');
                            var index = $(this).index();
                            view.slideTo(index);
                            app.bigEventIndex = index;
                            app.onChooseIndex(app.bigEventIndex,app.hotEventIndex);
                        })

                        app.onChooseIndex(app.bigEventIndex,app.hotEventIndex);
                    });
                }
            });
        },
        onChooseIndex:function (bigIndex,hotiIndex) {

            var pieStr = "#pieEchart"+bigIndex+hotiIndex;
            var barStr = "#barEchart"+bigIndex+hotiIndex;
            var bigStr = "#bigBar"+bigIndex+hotiIndex;


            var bigEvent = this.allBigEventList[bigIndex];
            for(var i = 0;i<bigEvent.hotIncidentList.length;i++){
                if (hotiIndex == i){
                    var hotEvent = bigEvent.hotIncidentList[i];
                    var pieOpt = $.extend(true, {}, echartsOpts["pieEchart"]);
                    pieOpt.series[0].data = [{ value: hotEvent.mgNumberDay, name: '敏感'},{ value: hotEvent.fmgNumberDay, name: '非敏感'}];
                    setEchartsOpion({$id:$(pieStr),opt:pieOpt});

                    var barOpt = $.extend(true, {}, echartsOpts["barEchart"]);
                    barOpt.legend.data = ['新闻', '政务', '报刊'];
                    barOpt.yAxis[0].data=['新闻', '政务', '报刊'];
                    var mapList =[];
                    var mediaMap = {
                        name: '新闻',
                        type: 'bar',
                        stack: '订单',
                        data: [hotEvent.mediaNumberDay, hotEvent.zwNumberDay, hotEvent.bkNumberDay],
                        barWidth: '20',
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 1, 1, 1, [{
                                        offset: 0,
                                        color: '#1AC0FF'
                                    },
                                        {
                                            offset: 1,
                                            color: '#3C83F5'
                                        }
                                    ]
                                )
                            }
                        }
                    }
                    var zwMap = {
                        name: '政务',
                        type: 'bar',
                        stack: '订单',
                        data: [hotEvent.mediaNumberDay, hotEvent.zwNumberDay, hotEvent.bkNumberDay],
                        barWidth: '20',
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 1, 1, 1, [{
                                        offset: 0,
                                        color: '#1AC0FF'
                                    },
                                        {
                                            offset: 1,
                                            color: '#3C83F5'
                                        }
                                    ]
                                )
                            }
                        }
                    }
                    var bkMap = {
                        name: '报刊',
                        type: 'bar',
                        stack: '订单',
                        data: [hotEvent.mediaNumberDay, hotEvent.zwNumberDay, hotEvent.bkNumberDay],
                        barWidth: '20',
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 1, 1, 1, [{
                                        offset: 0,
                                        color: '#1AC0FF'
                                    },
                                        {
                                            offset: 1,
                                            color: '#3C83F5'
                                        }
                                    ]
                                )
                            }
                        }
                    }
                    mapList.push(mediaMap);
                    mapList.push(zwMap);
                    mapList.push(bkMap);
                    barOpt.series = mapList;
                    setEchartsOpion({$id:$(barStr),opt:barOpt});

                    var bigOpt = $.extend(true, {}, echartsOpts["bigBar"]);
                    bigOpt.legend.data = [hotEvent.incidentTitle];
                    var timeList=[];
                    var totalList=[];
                    var lineData = JSON.parse(hotEvent.lineData);

                    if (lineData != null){
                        for(var i = 0;i<lineData.length;i++){
                            timeList.push(lineData[i].name);
                            totalList.push(lineData[i].total)
                        }
                        bigOpt.xAxis[0].data = timeList;
                        bigOpt.series[0].data = totalList;
                    }else {
                        bigOpt.xAxis[0].data = timeList;
                        bigOpt.xAxis[0].data = timeList;
                    }
                    bigOpt.series[0].markLine.label.normal.formatter = "热度均值: "+parseFloat(hotEvent.ratioHotDay).toFixed(2);
                    setEchartsOpion({$id:$(bigStr),opt:bigOpt});

                    break;
                }
            }
        }
    }
});

