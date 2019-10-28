/**
 * Created by sty on 2018/6/27.
 */
var vm = new Vue({
    el: '#activeTab1',
    data: {
        rankData: [],//热门事件排行数据
        hotEventData: {},//地图右侧详细信息数据
        hotClassData: {},//热点类型列表
        newHotClassData: [],//新的热点类型列表
        bigDataRead: [],//大数据解读数据
        typePropDate: [],//重构的类型占比数据
        returnTypePropData: [],//返回的类型占比数据
        type: [],//热点类型名称
        xqData: {},//排行详情数据
        activeClass: 0, // 0为默认选择第一个，-1为不选择
        activeTypeClass: -1,
        indexData: {},//排行榜文字数据
        timeType1: 1,//事件类型：1，24小时。2,72小时
        labels1: "",//热点类型标签ID
        province1: "",//省份
        province2: "",
        startTime: "",//开始时间
        endTime: "",//结束时间
        keyword: "",//热度趋势图关键字
        filterKeyword: "",//热度趋势过滤词
        mg: 0,//敏感
        fmg: 0,//非敏感
        xw: "",//新聞
        zw: "", //政务
        bk: "",//报刊
        sort: 2,//排行榜排序
        x: 0,//控制热点事件top5的开关
        y: 0,//控制变化最大top5的开关
        isLogin: $("#isLogin").val(),//判断是否登录用
        chinaData: [],//国内信息（全国），为了展示地图全国信息
        mapData1: [],//全国性质的事件数据
        number: 0,//地图全国性事件轮播数据判断浮动用
        allProvinceData: [],//全国省热点事件
        mapData2: [],//所有省性质的事件数据
        changeMaxDate: [],//变化最大数据
        hotTopData: [],//热点事件top数据
        timeClass: 1,//时间标签点击效果控制
        hotClass: 1,//排行榜排序点击效果控制
        qg: "全国",//切换至全国的按钮
        zt: false,//判断排行榜加载状态
        box: {},//地图中获取到的id的对象
        myChart: undefined,//地图中地图初始化的对象
        isPlay: true,//地图中各省份事件轮播控制
        timer: null,//地图中时间参数
        lab: "",//热点类型标签(用于收费提示)
        dt:false,//地图数据加载时水印控制
    },
    watch: {
        rankData: function () {
            if (this.isLogin === "true") {
                if (this.rankData != null) {
                    this.bar5((parseFloat(this.rankData[0].ratioHotDay).toFixed(2)) / 100);
                    this.bar7((parseFloat(this.rankData[0].ratioHotDay - this.rankData[0].ratioHotLastDay).toFixed(2)) / 100);
                }

            }
        },
        hotEventData: function () {
            setTimeout(function () {
                vm.bar1();
                vm.bar2();
            }, 500);
        },
        typePropDate: function () {
            this.bar3(this.typePropDate, this.type);
        },
        xqData: function () {
            if (this.isLogin === "true") {
                this.bar5((parseFloat(this.xqData.ratioHotDay).toFixed(2)) / 100);
                this.bar7((parseFloat(this.xqData.ratioHotDay - this.xqData.ratioHotLastDay).toFixed(2)) / 100);
            }
        },
    },
    created: function () {
        this.China();
        this.request();
        this.getChooseListData();
        this.changeMax();
        this.hotTop5();
        this.hot();
        this.classList();
        this.typeData();
        this.bigData();
    },
    mounted: function () {
        $('[data-toggle="tooltip"]').tooltip();
        this.bar2();
        //点击更多效果js
        $('.btn-more').on('click', function () {
            $(this).find('i').toggleClass('rotate180');
            $(this).parents('.group-item').find('.open').toggle()
        });
        //热点事件top5效果
        $('.map-tabs li').on('click', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $(".module-content .module-item").eq(index).addClass('active').siblings().removeClass('active');
            if (index == 1) {
                setTimeout(function () {
                    vm.bar4_1(0);
                    vm.bar4_2(0);
                }, 500)
            } else if (index == 2) {
                setTimeout(function () {
                    vm.bar4_1_1(0);
                    vm.bar4_2_1(0);
                }, 500)
            }

        });
        var animData = {
            wrapper: document.getElementById('bodymovin'),
            animType: 'svg',
            loop: true,
            prerender: true,
            autoplay: true,
            path: '/css/no-data.json'

        };
        //排行榜数据加载时水印
        bodymovin.loadAnimation({
            path: '/css/loading.json',   //json文件路径
            loop: true,
            autoplay: true,
            renderer: 'svg',  //渲染方式，有"html"、"canvas"和"svg"三种
            container: document.querySelector('.bodymovin')
        });
        //排行榜无数据时水印
        var anim = bodymovin.loadAnimation(animData);
        bodymovin.setSubframeRendering(false);
        //地图数据加载时水印
        //排行榜数据加载时水印
        bodymovin.loadAnimation({
            path: '/css/loading.json',   //json文件路径
            loop: true,
            autoplay: true,
            renderer: 'svg',  //渲染方式，有"html"、"canvas"和"svg"三种
            container: document.querySelector('.bodymovin2')
        });

    },
    filters: {
        //处理时间戳
        formatDate: function (date, formatStr) {
            var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
            return moment(date).format(formatStr);

        },
    },
    methods: {
        //提取的地图中的方法star
        autoPlay: function () {
            if (!this.isPlay) {
                return;
            }
            let index = 0;
            if (this.timer) clearInterval(this.timer);
            this.timer = setInterval(function () {
                vm.myChart.dispatchAction({
                    type: 'showTip',
                    seriesIndex: 1,
                    dataIndex: index
                });

                index++;
                if (index >= vm.mapData1.length) {
                    index = 0
                }
            }, 3000);
            return index
        },
        stopPlay: function () {
            clearTimeout(this.timer);
            this.isPlay = false;
        },
        startPlay: function () {
            this.isPlay = true;
            this.autoPlay();
        },
        //提取的地图中的方法end
        //排行榜小眼睛
        eye: function (value) {
            $("#title").val(value.incidentTitle);
            $("#keyword").val(value.keyword);
            $("#filterKeyword").val(value.filterKeyword);
            var $form = $("#searchForm");
            $form.prop("action", actionBase + "/hot/opentools/goOpenTools");
            $form.submit();
        },
        //切至全国
        qh: function () {
            this.startPlay();
            this.province1 = "";
            this.province2 = "";
            this.qg = "全国";
            this.hot();
            this.getChooseListData();
            this.changeMax();
            this.hotTop5();
            this.typeData();
            this.myChart.dispatchAction({
                type: 'downplay'
            });


        },
        //变化最大top
        changeMax: function () {
            //发送 post 请求
            // this.$http.post(actionBase + '/hot/events/selectChooseListData', {
            //     timeType: this.timeType1,
            //     sort: 5,
            //     labels: this.labels1,
            //     province: this.province2,
            //     areaType: 1,
            // }, {emulateJSON: true}).then(function (res) {
            //     this.changeMaxDate = res.data.data.list;
            // });
            var  searchObj={timeType: this.timeType1,sort: 5,labels: this.labels1,province: this.province2,areaType: 1};
            $.post(actionBase+'/hot/events/selectChooseListData',searchObj,function(result){
                vm.changeMaxDate = result.data.list;
            });
        },
        //热点事件top5
        hotTop5: function () {
            //发送 post 请求
            // this.$http.post(actionBase + '/hot/events/selectChooseListData', {
            //     timeType: this.timeType1,
            //     sort: 2,
            //     labels: this.labels1,
            //     province: this.province2,
            //     areaType: 1,
            // }, {emulateJSON: true}).then(function (res) {
            //     this.hotTopData = res.data.list;
            // });
            var  searchObj={timeType: this.timeType1,sort: 2,labels: this.labels1,province: this.province2,areaType: 1};
            $.post(actionBase+'/hot/events/selectChooseListData',searchObj,function(result){
                vm.hotTopData = result.data.list;
            });
        },
        //所有省份数据
        allProvince: function () {
            this.dt=false;
            this.mapData2 = [];
            //发送 post 请求
            // var  searchObj={timeType: this.timeType1,sort: 2,labels: this.labels1,province: this.province2,areaType: 1};
            $.post(actionBase+'/hot/events/allProvince',{timeType: this.timeType1},function(result){
                // this.hotTopData = result.data.list;
            // });
            // this.$http.post(actionBase + '/hot/events/allProvince', {
            //     timeType: this.timeType1,
            // }, {emulateJSON: true}).then(function (res) {
                vm.dt=true;
                vm.allProvinceData = result.data;
                if (vm.allProvinceData != null) {
                    for (let i = 0; i < vm.allProvinceData.length; i++) {
                        let x = {
                            name: "",
                            value: [{name: "", value: 0}],
                            incidentTitle: "",
                            keyword: "",
                            filterKeyword: ""
                        };
                        x.name = vm.allProvinceData[i].province;
                        x.value[0].name = (vm.allProvinceData[i].incidentTitle)/*.substring(0, 10) + "..."*/;
                        x.value[0].value = parseFloat(vm.allProvinceData[i].ratioHotDay).toFixed(2);
                        x.incidentTitle = vm.allProvinceData[i].incidentTitle;
                        x.keyword = vm.allProvinceData[i].keyword;
                        x.filterKeyword = vm.allProvinceData[i].filterKeyword;
                        vm.mapData2.push(x)
                    }
                }
                vm.getDataMap('dataMap');
            });
        },
        //为了展示地图的全国信息
        China: function () {
            // this.mapData1=[];
            // //发送 post 请求
            // this.$http.post(actionBase + '/view/home/hotEvent/allChina.action', {
            //     timeType: this.timeType1,
            //     sort: 2,
            //     labels: this.labels1,
            // }, {emulateJSON: true}).then(function (res) {
            //     this.chinaData = res.data.list;
            //     if (this.chinaData != null) {
            //         for (let i = 0; i < this.chinaData.length - 1; i++) {
            //                 let map2 = {event: "", hot: "", incidentTitle: "", keyword: "", filterKeyword: ""};
            //                 map2.event = this.chinaData[i].incidentTitle;
            //                 map2.hot = this.chinaData[i].ratioHotDay;
            //                 map2.incidentTitle = this.chinaData[i].incidentTitle;
            //                 map2.keyword = this.chinaData[i].keyword;
            //                 map2.filterKeyword = this.chinaData[i].filterKeyword;
            //                 this.mapData1.push(map2);
            //         }
            //     }
            //     this.number = Math.ceil(this.mapData1.length / 3);
            //
            // });
            //地图各省份遍历事件
            this.mapData1 = [];
            var  searchObj={timeType: this.timeType1,sort: 2,areaType: 1,};
            $.post(actionBase+'/hot/events/allChina',searchObj,function(result){
            //     this.hotTopData = result.data.list;
            // });
            // this.$http.post(actionBase + '/hot/events/allChina', {
            //     timeType: this.timeType1,
            //     sort: 2,
            //     areaType: 1,
            // }, {emulateJSON: true}).then(function (res) {
                vm.chinaData = result.data.list;
                if (vm.chinaData != null) {
                    for (let i = 0; i < vm.chinaData.length; i++) {
                        let v = {
                            name: "",
                            value: [{
                                name: "", value: 0, incidentTitle: "",
                                keyword: "",
                                filterKeyword: ""
                            }],

                        };
                        if (vm.chinaData[i].province === "全国") {
                            v.name = "北京"
                        } else {
                            v.name = vm.chinaData[i].province;
                        }
                        if (vm.chinaData[i].incidentTitle.length>=15){
                            v.value[0].name = (vm.chinaData[i].incidentTitle).substring(0, 15) + "...";
                        }else {
                            v.value[0].name = vm.chinaData[i].incidentTitle;
                        }

                        v.value[0].value = parseFloat(vm.chinaData[i].ratioHotDay).toFixed(2);
                        v.value[0].incidentTitle = vm.chinaData[i].incidentTitle;
                        v.value[0].keyword = vm.chinaData[i].keyword;
                        v.value[0].filterKeyword = vm.chinaData[i].filterKeyword;
                        vm.mapData1.push(v);
                    }
                    vm.allProvince();
                }

            });
        },
        //初始页面的请求
        request: function () {
            //发送 post 请求
            this.$http.post(actionBase + '/hot/events/goHotEvent', {}, {emulateJSON: true}).then(function (res) {

            });
        },
        //点击热度排序切换参数
        hotSort: function () {
            this.hotClass = 1;
            this.activeClass = 0;
            this.sort = 2;
            this.getChooseListData();
        },
        //点击热度变化排序切换参数
        chartSort: function () {
            this.hotClass = 2;
            this.activeClass = 0;
            this.sort = 5;
            this.getChooseListData();
        },
        //热点事件top5
        detailsBtn: function (me, index) {
            this.x = index;
            var $li = $(me.target).parents(".module-map-item").siblings(".module-map-item");
            $li.find(".mmi-title").removeClass("active");
            $li.find(".chart-body").hide();
            $(me.target).addClass('active');
            $(me.target).parents(".mmi-title").toggleClass("active");
            $(me.target).parents(".mmi-title").nextAll(".chart-body").toggle();
            if ($(me.target).parents(".mmi-title").hasClass("active")) {
                $(me.target).removeClass('active');
                setTimeout(function () {
                    vm.bar4_1(index);
                    vm.bar4_2(index);
                }, 500);
            } else {
                $(me.target).addClass('active');
                setTimeout(function () {
                    vm.bar4_1(index);
                    vm.bar4_2(index);
                }, 500);

            }
        },
        //变化最大top5
        detailsBtns: function (me, index) {
            this.y = index;
            var $li = $(me.target).parents(".module-map-item").siblings(".module-map-item");
            $li.find(".mmi-title").removeClass("active");
            $li.find(".chart-body").hide();
            $(me.target).addClass('active');
            $(me.target).parents(".mmi-title").toggleClass("active");
            $(me.target).parents(".mmi-title").nextAll(".chart-body").toggle();
            if ($(me.target).parents(".mmi-title").hasClass("active")) {
                $(me.target).removeClass('active');
                setTimeout(function () {
                    vm.bar4_1_1(index);
                    vm.bar4_2_1(index);
                }, 500);
            } else {
                $(me.target).addClass('active');
                setTimeout(function () {
                    vm.bar4_1_1(index);
                    vm.bar4_2_1(index);
                }, 500);

            }

        },
        //初始化新闻政务报刊数据、
        three: function () {
            var opt = $.extend(true, {}, echartsOpts["bar"]);
            if (this.rankData != null) {
                opt.series[0].data[0] = this.rankData[0].mediaNumberDay;
                opt.series[0].data[1] = this.rankData[0].zwNumberDay;
                opt.series[0].data[2] = this.rankData[0].bkNumberDay;
                Vue.nextTick(function () {
                    setEchartsOpion({$id: $("#lineEchartend"), opt: opt});
                });
            }

        },
        clickThree: function () {
            var opt = $.extend(true, {}, echartsOpts["bar"]);
            opt.series[0].data[0] = this.xw;
            opt.series[0].data[1] = this.zw;
            opt.series[0].data[2] = this.bk;
            setEchartsOpion({$id: $("#lineEchartend"), opt: opt});
        },
        //初始化排行榜敏感非敏感的请求参数，使其默认展示第一条
        initMGParm: function () {
            var opt = $.extend(true, {}, echartsOpts["pie"]);
            if (this.rankData != null) {
                opt.series[0].data[0].value = this.rankData[0].mgNumberDay;
                opt.series[0].data[1].value = this.rankData[0].fmgNumberDay;
                Vue.nextTick(function () {
                    setEchartsOpion({$id: $("#pieEchartend"), opt: opt});
                });

            }

        },
        clickMG: function () {
            var opt = $.extend(true, {}, echartsOpts["pie"]);
            opt.series[0].data[0].value = this.mg;
            opt.series[0].data[1].value = this.fmg;
            setEchartsOpion({$id: $("#pieEchartend"), opt: opt});
        },
        //初始化热度指数趋势请求的参数，使其默认画出第一条数据
        initParam: function () {
            this.getHotCharLine(this.rankData[0]);
        },
        // 热度指数趋势图数据
        getHotCharLine: function (value) {
            var v = JSON.parse(value.lineData);
            var opt = $.extend(true, {}, echartsOpts["line"]);
            for (var i = 0; i < v.length - 1; i++) {
                opt.xAxis[0].data[i] = v[i].name;
                opt.series[0].data[i] = v[i].total;
            }
            Vue.nextTick(function () {
                setEchartsOpion({$id: $("#informationChart"), opt: opt});
            });
        },
        //获取排行列表
        getChooseListData: function () {
            //发送 post 请求
            var  searchObj={timeType: this.timeType1,sort: this.sort,labels: this.labels1,province: this.province2,areaType: 1,};
            $.post(actionBase+'/hot/events/selectChooseListData',searchObj,function(result){
            // this.$http.post(actionBase + '/hot/events/selectChooseListData', {
            //     timeType: this.timeType1,
            //     sort: this.sort,
            //     labels: this.labels1,
            //     province: this.province2,
            //     areaType: 1,
            // }, {emulateJSON: true}).then(function (res) {
                if (result.data.code === "0000" || result.data.code === null || result.data.data === null) {
                    vm.zt = true;
                }
                vm.rankData = result.data.list;
                //top图形变换
                if (vm.rankData != null) {
                    setTimeout(function () {
                        if ($("#h5").hasClass("active")) {
                            vm.bar4_1(vm.x);
                            vm.bar4_2(vm.x);
                        } else if ($("#b5").hasClass("active")) {
                            vm.bar4_1_1(vm.y);
                            vm.bar4_2_1(vm.y);
                        }
                    }, 500);

                    vm.indexData = {
                        index: 0,
                        name: vm.rankData[0].labelNames,
                        title: vm.rankData[0].incidentTitle,
                        time: vm.rankData[0].createTime
                    };
                } else {
                    vm.mapData1 = null;
                }
                if (vm.isLogin == "true") {
                    vm.initParam();
                    vm.initMGParm();
                    vm.three();
                }
            });
        },
        //获取地图右侧均值等数据
        hot: function () {
            // let params = {
            //     timeType: this.timeType1,
            //     labels: this.labels1,
            //     province: this.province1
            // };
            //发送 post 请求
            var  searchObj={timeType: this.timeType1,labels: this.labels1,province: this.province1};
            $.post(actionBase+'/hot/events/selectRankData',searchObj,function(result){
            // $.post(actionBase+'/hot/events/selectRankData',params,function(res){
            // this.$http.post(actionBase + '/hot/events/selectRankData', params, {emulateJSON: true}).then(function (res) {
                vm.hotEventData = result.data.data;
            });
        },
        //点击切换热点类型标签
        changeParm: function (obj, index) {
            if (this.province1 === "") {
                $("#piezhan").hide();
                $(".layout100").css('float', 'inherit');
                this.activeTypeClass = index;
                var value = obj.id;
                this.labels1 = value;
                this.lab = obj.name;
                this.getChooseListData();
                this.changeMax();
                this.hotTop5();
                this.hot();
            } else {
                $('#moneyTip').modal('show');
            }
        },
        //点击全部
        all: function (value) {
            this.activeTypeClass = -1;
            this.labels1 = "";
            this.lab = "";
            this.getChooseListData();
            this.changeMax();
            this.hotTop5();
            this.hot();
            $("#piezhan").show();
            $(".layout100").css('float', 'left')
        },
        //点击切换时间参数
        changeTime: function (value) {
            this.timeClass = 1;
            this.timeType1 = value;
            this.getChooseListData();
            this.changeMax();
            this.hotTop5();
            this.hot();
            this.typeData();
        },
        changeTime2: function (value) {
            this.timeClass = 2;
            this.timeType1 = value;
            this.getChooseListData();
            this.changeMax();
            this.hotTop5();
            this.hot();
            this.typeData();
        },
        //获取热点类型标签
        classList: function (id) {
            $.get(actionBase+'/hot/events/getHotClassList',{},function(res){
            // this.$http.post(actionBase + '/hot/events/getHotClassList', {}, {emulateJSON: true}).then(function (res) {
                vm.hotClassData = res.data.data.childrens;
                vm.newHotClassData = [
                    vm.hotClassData[9],
                    vm.hotClassData[4],
                    vm.hotClassData[8],
                    vm.hotClassData[2],
                    vm.hotClassData[5],
                    vm.hotClassData[7],
                    vm.hotClassData[3],
                    vm.hotClassData[6],
                    vm.hotClassData[0],
                    vm.hotClassData[1],

                ];

            });
        },
        //24小时均值变化数据
        bar1: function () {
            var opt = $.extend(true, {}, echartsOpts["bar1"]);
            opt.series[0].data[0].value = parseFloat(this.hotEventData.hotAvg = 0 ? 0.00 : this.hotEventData.hotAvg).toFixed(2);
            setEchartsOpion({$id: $("#pieBox1"), opt: opt});

        },
        //24小时均值数据
        bar2: function () {
            var opt = $.extend(true, {}, echartsOpts["bar2"]);
            opt.series[0].data[0].value = parseFloat(this.hotEventData.hotDifferenceAvg = 0 ? 0.00 : this.hotEventData.hotDifferenceAvg).toFixed(2);
            setEchartsOpion({$id: $("#pieBox2"), opt: opt});


        },
        //类型占比
        bar3: function (data, type) {
            var opt = $.extend(true, {}, echartsOpts["bar3"]);
            opt.legend.data = type;
            opt.series[0].data = data;
            setEchartsOpion({$id: $("#pieBox3"), opt: opt});
        },
        //top敏感非敏感
        bar4_1: function (x) {
            var opt = $.extend(true, {}, echartsOpts["bar4_1"]);
            if (this.hotTopData != null) {
                opt.series[0].data[0].value = this.hotTopData[x].mgNumberDay;
                opt.series[0].data[1].value = this.hotTopData[x].fmgNumberDay;
                setEchartsOpion({$id: $("#pieE" + x), opt: opt});
            }

        },
        //top类型数量
        bar4_2: function (x) {
            let max = 0;
            var opt = $.extend(true, {}, echartsOpts["bar4_2"]);
            if (this.hotTopData != null) {
                max = (this.hotTopData[x].zwNumberDay > this.hotTopData[x].mediaNumberDay) ? this.hotTopData[x].zwNumberDay : this.hotTopData[x].mediaNumberDay;
                max = (max > this.hotTopData[x].bkNumberDay) ? max : this.hotTopData[x].bkNumberDay;
                opt.series[0].data[0] = max;
                opt.series[0].data[1] = max;
                opt.series[0].data[2] = max;
                opt.series[0].data[0] = this.hotTopData[x].zwNumberDay;
                opt.series[0].data[1] = this.hotTopData[x].mediaNumberDay;
                opt.series[0].data[2] = this.hotTopData[x].bkNumberDay;
                setEchartsOpion({$id: $("#barE" + x), opt: opt});
                console.log(opt)
            }

        },
        //变化最大top
        bar4_1_1: function (y) {
            var opt = $.extend(true, {}, echartsOpts["bar4_1_1"]);
            if (this.changeMaxDate != null) {
                opt.series[0].data[0].value = this.changeMaxDate[y].mgNumberDay;
                opt.series[0].data[1].value = this.changeMaxDate[y].fmgNumberDay;
                setEchartsOpion({$id: $("#pieEchart1" + y), opt: opt});
            }

        },
        //top类型数量
        bar4_2_1: function (y) {
            let max = 0;
            var opt = $.extend(true, {}, echartsOpts["bar4_2_1"]);
            if (this.changeMaxDate != null) {
                max = (this.changeMaxDate[y].zwNumberDay > this.changeMaxDate[y].mediaNumberDay) ? this.changeMaxDate[y].zwNumberDay : this.changeMaxDate[y].mediaNumberDay;
                max = (max > this.changeMaxDate[y].bkNumberDay) ? max : this.changeMaxDate[y].bkNumberDay;
                opt.series[0].data[0] = max;
                opt.series[0].data[1] = max;
                opt.series[0].data[2] = max;
                // opt.series[0].data[0].value = this.changeMaxDate[y].zwNumberDay;
                // opt.series[0].data[1].value = this.changeMaxDate[y].mediaNumberDay;
                // opt.series[0].data[2].value = this.changeMaxDate[y].bkNumberDay;
                opt.series[0].data[0] = this.changeMaxDate[y].zwNumberDay;
                opt.series[0].data[1] = this.changeMaxDate[y].mediaNumberDay;
                opt.series[0].data[2] = this.changeMaxDate[y].bkNumberDay;
                setEchartsOpion({$id: $("#barEchart1" + y), opt: opt});
            }

        },
        clearTimeTickers: function () {
            for (var i = 0; i < vm.intervalIdList.length; i++) {
                clearInterval(vm.intervalIdList[i]);
            }
            for (var i = 0; i < vm.timeoutIdList.length; i++) {
                clearTimeout(vm.timeoutIdList[i]);
            }
            vm.intervalIdList = [];
            vm.timeoutIdList = [];
        },
        //地图
        getDataMap: function (id) {
            vm.box = document.getElementById(id);
            if (vm.myChart === undefined) {
                vm.myChart = echarts.init(vm.box);
            }
            var mapName = 'china';
            var geoCoordMap = {
                '钓鱼岛': [123.0254, 25.1986]
            };
            const rawData = this.mapData2;
            //各省份遍历事件
            const rawData2 = this.mapData1;
            const dataT = [
                {
                    name: '钓鱼岛',
                    value: 5
                },
            ];
            // var geoCoordMap ={};
            var mapFeatures = echarts.getMap(mapName).geoJson.features;
            mapFeatures.forEach(function (v) {
                // 地区名称
                var name = v.properties.name;
                // 地区经纬度
                geoCoordMap[name] = v.properties.cp;
            });
            var convertData = function (data) {
                var res = [];
                for (var i = 0; i < data.length; i++) {
                    var geoCoord = geoCoordMap[data[i].name];
                    if (geoCoord) {
                        res.push({
                            name: data[i].name,
                            value: geoCoord.concat(data[i].value)
                        });
                    }
                }
                return res;
            };
            let myTooltip = new myTooltipC(id);
            var namearr = ['1'];
            let option = {
                tooltip: {
                    trigger: 'item',
                    triggerOn: 'click',
                },
                legend: [{
                    show: false,
                    data: namearr,
                    itemWidth: 5,
                    itemHeight: 5,
                    textStyle: {
                        color: '#ddd',
                        fontSize: 10
                    },
                    top: '95%',
                }],
                xAxis: [],
                yAxis: [],
                grid: [],
                geo: {
                    type: 'map',
                    map: 'china',
                    top: '5',
                    bottom: '80',
                    mapType: mapName,
                    selectedMode: 'single',
                    label: {
                        normal: {
                            textStyle: {
                                color: '#ddd'
                            },
                            show: true
                        },
                        emphasis: {
                            textStyle: {
                                color: '#ffffff'
                            },
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            areaColor: "rgba(19, 72, 174, 0.8)",
                            borderColor: "rgba(15, 106, 207,1)",
                            borderWidth: 1,
                            shadowColor: 'rgba(255, 255, 255, 0.3)',
                            shadowBlur: 1
                        },
                        emphasis: {
                            areaColor: "#29C2F5"
                        }
                    }
                },
                series: [{
                    name: 'credit',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: convertData(dataT),
                    symbolSize: function (val) {
                        return val[2] / 10000;
                    },
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'right',
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#FFFFFF'
                        }
                    }
                },
                    {
                        name: '轮播',
                        type: 'scatter',
                        coordinateSystem: 'geo',
                        data: convertData(rawData2),
                        symbolSize: 0,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: false
                            }
                        },
                        tooltip: {
                            backgroundColor: 'transparent',
                            axisPointer: {
                                type: 'line',
                                lineStyle: {
                                    opacity: 0
                                }
                            },
                            showDelay: 0,                                  //浮层显示的延迟，单位为 ms
                            hideDelay: 1000,
                            transitionDuration: 0.5,
                            enterable: true,
                            position (pos) {
                                let position = myTooltip.getPosOrSize('pos', pos);
                                return position
                            },
                            formatter (params) {
                                if (params.dataIndex != -1) {
                                    $("#title").val(params.data.value[2].incidentTitle);
                                    $("#keyword").val(params.data.value[2].keyword);
                                    $("#filterKeyword").val(params.data.value[2].filterKeyword);
                                    let text = `${params.value[2].name}\n热度：${params.value[2].value}`;
                                    let tooltipDom = myTooltip.getTooltipDom(text);
                                    return '<a href="javascript:;" onclick="mp()">'+tooltipDom+'</a>'
                                }
                            },

                            // formatter: function (params) {
                            //     if (params.dataIndex != -1) {
                            //         $("#title").val(params.data.value[2].incidentTitle);
                            //         $("#keyword").val(params.data.value[2].keyword);
                            //         $("#filterKeyword").val(params.data.value[2].filterKeyword);
                            //         var htmlBox = '<div class="country-item">\n' +
                            //             '                    <ul class="active">\n' +
                            //             '                        <li>\n' +
                            //             '                            <a href="javascript:;" onclick="mp()">\n' +
                            //             '                                <p>' + params.data.value[2].name + '</p>\n' +
                            //             '                                <p>热度：' + params.data.value[2].value + '</p>\n' +
                            //             '                                <div class="edgeHorn lt"></div>\n' +
                            //             '                                <div class="edgeHorn rt"></div>\n' +
                            //             '                                <div class="edgeHorn rb"></div>\n' +
                            //             '                                <div class="edgeHorn lb"></div>\n' +
                            //             '                            </a>\n' +
                            //             '                        </li>\n' +
                            //             '                    </ul>\n' +
                            //             '                </div>'
                            //         return htmlBox;
                            //     }
                            // }
                        },
                        itemStyle: {
                            normal: {
                                color: '#FFFFFF'
                            }
                        }
                    },

                    {
                        type: 'map',
                        map: mapName,
                        geoIndex: 0,
                        aspectScale: 0.75, //长宽比
                        showLegendSymbol: false, // 存在legend时显示
                        selectedMode: 'single',
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: false,
                                textStyle: {
                                    color: '#fff'
                                }
                            }
                        },
                        tooltip: {
                            backgroundColor: 'transparent',
                            axisPointer: {
                                type: 'line',
                                lineStyle: {
                                    opacity: 0
                                }
                            },
                            enterable: true,
                            formatter: function (params) {
                                if (params.dataIndex != -1) {
                                    $("#title").val(params.data.incidentTitle);
                                    $("#keyword").val(params.data.keyword);
                                    $("#filterKeyword").val(params.data.filterKeyword);
                                    var htmlBox = '<div class="country-item">\n' +
                                        '                    <ul class="active">\n' +
                                        '                        <li>\n' +
                                        '                            <a href="javascript:;" onclick="mp()">\n' +
                                        '                                <p>' + params.data.value[0].name + '</p>\n' +
                                        '                                <p>热度：' + params.data.value[0].value + '</p>\n' +
                                        '                                <div class="edgeHorn lt"></div>\n' +
                                        '                                <div class="edgeHorn rt"></div>\n' +
                                        '                                <div class="edgeHorn rb"></div>\n' +
                                        '                                <div class="edgeHorn lb"></div>\n' +
                                        '                            </a>\n' +
                                        '                        </li>\n' +
                                        '                    </ul>\n' +
                                        '                </div>';
                                    return htmlBox;
                                }

                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: "rgba(19, 72, 174, 0.8)",
                                borderColor: "rgba(0, 0, 0,0.5)",
                                borderWidth: 1.5,
                                shadowColor: 'rgba(255, 255, 255, 0.8)',
                                shadowBlur: 5
                            },
                            emphasis: {
                                areaColor: "#29C2F5"
                            }
                        },
                        animation: false,
                        data: rawData
                    },
                    {
                        type: 'effectScatter',
                        name: '触发',
                        coordinateSystem: 'geo',
                        effectType: 'ripple',
                        rippleEffect: { //涟漪特效
                            period: 6, //动画时间，值越小速度越快
                            brushType: 'fill', //波纹绘制方式 stroke, fill
                            scale: 3 //波纹圆环最大限制，值越大波纹越大
                        },
                        large: true,        // 大规模散点图
                        largeThreshold: 400,
                        zlevel: 1,
                        symbolSize: function (val) {
                            return [val[2].value / 3, val[2].value / 4];
                        },
                        tooltip: {
                            backgroundColor: 'transparent',
                            axisPointer: {
                                type: 'line',
                                lineStyle: {
                                    opacity: 0
                                }
                            },
                            enterable: true,
                            formatter: function (params) {
                                var htmlBox = '<div class="country-item">\n' +
                                    '                    <ul class="active">\n' +
                                    '                        <li>\n' +
                                    '                            <a href="javascript:;">\n' +
                                    '                                <p>' + params.data.value[2].name + '</p>\n' +
                                    '                                <p>热度：' + params.data.value[2].value + '</p>\n' +
                                    '                                <div class="edgeHorn lt"></div>\n' +
                                    '                                <div class="edgeHorn rt"></div>\n' +
                                    '                                <div class="edgeHorn rb"></div>\n' +
                                    '                                <div class="edgeHorn lb"></div>\n' +
                                    '                            </a>\n' +
                                    '                        </li>\n' +
                                    '                    </ul>\n' +
                                    '                </div>';
                                return htmlBox;
                            }
                        },

                        data: convertData(rawData.sort(function (a, b) {
                            return b.value - a.value;
                        })),
                        itemStyle: {
                            normal: {
                                show: true,
                                color: {
                                    type: 'radial',
                                    x: 0.5,
                                    y: 0.5,
                                    r: 0.5,
                                    colorStops: [{
                                        offset: 0,
                                        color: "rgba(252, 225, 86, 0.3)",
                                    }, {
                                        offset: 0.9,
                                        color: "rgba(252, 225, 86, 0.3)",
                                    },
                                        {
                                            offset: 1,
                                            color: "rgba(252, 225, 86, 1)"
                                        }
                                    ],
                                    globalCoord: false // 缺省为 false
                                }
                            }
                        }
                    },]
            };

            function renderEachCity() {

                echarts.util.each(rawData, function (dataItem, idx) {
                    var geoCoord = geoCoordMap[dataItem.name];
                    var coord = vm.myChart.convertToPixel('geo', geoCoord);
                    idx += '';


                    option.xAxis.push({
                        id: idx,
                        gridId: idx,
                        type: 'category',
                        name: dataItem.name,
                        nameStyle: {
                            color: '#fff',
                            fontSize: 12
                        },
                        nameLocation: 'middle',
                        nameGap: 3,
                        splitLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            show: false
                        },
                        axisLine: {
                            show: false,
                            lineStyle: {
                                color: 'rgba(0,0,0,0)'
                            }
                        },
                        data: [dataItem.name],
                    });

                    option.yAxis.push({
                        id: idx,
                        gridId: idx,
                        show: false,
                        type: 'value',
                        min: 0,
                        max: 50,
                    });

                    option.grid.push({
                        id: idx,
                        z: 100,
                        width: 30,
                        height: 220,
                        left: coord[0] - 15,
                        top: coord[1] - 220,
                    });


                    for (var i = 0; i < namearr.length; i++) {
                        option.series.push({
                            name: namearr[i],
                            type: 'bar',
                            stack: 'bar' + idx,
                            xAxisId: idx,
                            yAxisId: idx,
                            large: true,        // 大规模散点图
                            largeThreshold: 400,
                            barWidth: 6,
                            tooltip: {
                                backgroundColor: 'transparent',
                                axisPointer: {
                                    type: 'line',
                                    lineStyle: {
                                        opacity: 0
                                    }
                                },
                                formatter: function (params) {
                                    var htmlBox = '<div class="country-item">\n' +
                                        '                    <ul class="active">\n' +
                                        '                        <li>\n' +
                                        '                            <a href="javascript:;">\n' +
                                        '                                <p>' + params.data.name + '</p>\n' +
                                        '                                <p>热度：' + params.data.value + '</p>\n' +
                                        '                                <div class="edgeHorn lt"></div>\n' +
                                        '                                <div class="edgeHorn rt"></div>\n' +
                                        '                                <div class="edgeHorn rb"></div>\n' +
                                        '                                <div class="edgeHorn lb"></div>\n' +
                                        '                            </a>\n' +
                                        '                        </li>\n' +
                                        '                    </ul>\n' +
                                        '                </div>';
                                    return htmlBox;
                                }
                            },
                            itemStyle: {
                                normal: {
                                    barBorderRadius: 20, // 统一设置四个角的圆角大小
                                    // barBorderRadius: [30, 30, 0, 0], //（顺时针左上，右上，右下，左下）
                                    // color : colorarr[i]
                                    color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                                        offset: 0,
                                        color: "rgba(251, 225, 86, 1)" // 0% 处的颜色
                                    }, {
                                        offset: 1,
                                        color: "rgba(251, 225, 86, 0)" // 100% 处的颜色
                                    }], false)

                                }

                            },
                            label: {
                                normal: {
                                    show: false
                                }
                            },
                            data: [dataItem.value[0]]
                        });
                    }

                });
                vm.myChart.setOption(option);
            }

            setTimeout(function () {
                renderEachCity();
            }, 1);
            vm.myChart.setOption(option);
            window.onresize = vm.myChart.resize;
            // var allowRun = false; //设置标记
            /**
             * @return {number}
             */
            vm.autoPlay();
            vm.myChart.on('click', function (params) {
                if (vm.lab === "") {
                    vm.stopPlay();
                    vm.myChart.dispatchAction({
                        type: 'highlight',
                        seriesIndex: 2,
                        dataIndex: params.dataIndex
                    });
                    vm.province1 = params.name;
                    vm.hot();
                    vm.province2 = params.name;
                    vm.activeClass = 0;
                    vm.getChooseListData();
                    vm.changeMax();
                    vm.hotTop5();
                    if (params.dataIndex == -1) {
                        vm.myChart.dispatchAction({
                            type: 'downplay'
                        });
                        $('#tipsModal').modal('show');
                        vm.qg = "全国";
                        vm.typeData();
                    } else {
                        vm.qg = params.name;
                        vm.typeData();
                    }
                } else {
                    vm.myChart.dispatchAction({
                        type: 'downplay'
                    });
                    $('#moneyTip').modal('show');
                }

            })
        },
        //排行榜三个环形图数据详情
        bar5: function (xq) {
            var piegEchart1 = echarts.init(document.getElementById('pie-g1'));
            if (this.rankData != null) {
                var option = {
                    series: [{
                        type: 'liquidFill',
                        data: [xq, xq],
                        color: ['#71D0D5', 'rgba(178, 246, 249, 0.5)',],
                        center: ['50%', '50%'],
                        radius: '80%',
                        label: {
                            normal: {
                                textStyle: {
                                    color: '#ffffff',
                                    insideColor: 'yellow',
                                    fontSize: 16
                                },
                                formatter: function (param) {
                                    return (Math.floor(param.value * 10000) / 100);


                                },
                            }
                        },

                        itemStyle: {
                            normal: {
                                shadowBlur: 0
                            }
                        },
                        backgroundStyle: {
                            color: 'rgba(4,24,74,0)',
                        },
                        outline: {
                            borderDistance: 0,
                            itemStyle: {
                                borderWidth: 0.3,
                                borderColor: '#71D0D5',
                                shadowBlur: '50',
                                shadowColor: 'rgba(178, 246, 249, 0.5)'
                            }
                        }
                    }]
                };

                piegEchart1.setOption(option);
            }

        },
        bar7: function (xq) {
            var piegEchart3 = echarts.init(document.getElementById('pie-g3'));
            if (this.rankData != null) {
                var option = {
                    series: [{
                        type: 'liquidFill',
                        data: [xq, xq],
                        color: ['#FF910C', 'rgba(255, 145, 12, 0.3)'],
                        center: ['50%', '50%'],
                        radius: '80%',
                        label: {
                            normal: {
                                textStyle: {
                                    color: '#ffffff',
                                    insideColor: 'yellow',
                                    fontSize: 16
                                },
                                formatter: function (param) {
                                    return (Math.floor(param.value * 10000) / 100);

                                },
                            }
                        },

                        itemStyle: {
                            normal: {
                                shadowBlur: 0
                            }
                        },
                        backgroundStyle: {
                            color: 'rgba(4,24,74,0)',
                        },
                        outline: {
                            borderDistance: 0,
                            itemStyle: {
                                borderWidth: 0.3,
                                borderColor: '#FF910C',
                                shadowBlur: '50',
                                shadowColor: 'rgba(255, 145, 12, 0.3))'
                            }
                        }
                    }]
                };

                piegEchart3.setOption(option);
            }

        },
        //未登录大数据解读数据
        bigData: function () {
            //发送 post 请求
            $.post(actionBase+'/hot/events/dataRead',{},function(result){
            // this.$http.post(actionBase + '/hot/events/dataRead', {}, {emulateJSON: true}).then(function (res) {
                var map=result.data;
                vm.bigDataRead = map.data;
            });
        },
        //类型占比数据
        typeData: function () {
            //发送 post 请求
            var  searchObj={timeType: this.timeType1,province: this.province1};
            $.post(actionBase+'/hot/events/typeProp',searchObj,function(result){
            // this.$http.post(actionBase + '/hot/events/typeProp', {
            //     timeType: this.timeType1,
            //     province: this.province1,
            // }, {emulateJSON: true}).then(function (res) {
                vm.returnTypePropData = result.data;
                if (result.data.data != null) {
                    let s = result.data.data;
                    for (let k in s) {
                        let newData = {name: "", value: ""};
                        newData.name = k;
                        newData.value = s[k];
                        vm.typePropDate.push(newData);
                        vm.type.push(k)
                    }
                } else {
                    alert("暂无数据！")
                }

            });
        },
        //点击获取排行详情
        XQ: function (rank, index) {
            this.xqData = rank;
            this.activeClass = index;
            this.indexData = {index: "", name: "", title: "", time: ""};
            this.indexData.index = index;
            this.indexData.name = rank.labelNames;
            this.indexData.title = rank.incidentTitle;
            this.indexData.time = rank.createTime;
            //热度趋势图点击改变参数操作
            this.getHotCharLine(rank);
            //排行榜敏感非敏感点击改变参数
            this.mg = rank.mgNumberDay;
            this.fmg = rank.fmgNumberDay;
            this.clickMG();
            //媒体发文来源
            this.xw = rank.mediaNumberDay;
            this.zw = rank.zwNumberDay;
            this.bk = rank.bkNumberDay;
            this.clickThree();
        },
        //大数据解读详情
        goBlackMore: function (url) {
            window.open(url, "_blank");
        }
    },
});