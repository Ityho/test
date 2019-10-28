var large=new Vue({
    el: '#hotLargeScreen',
    data: {
        areaType:"",//1,国内   2，国外
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
        sort: 7,//排行榜排序
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
        qg: "全部",//切换至全国的按钮
        zt: false,//判断排行榜加载状态
        box: {},//地图中获取到的id的对象
        myChart: undefined,//地图中地图初始化的对象
        isPlay: true,//地图中各省份事件轮播控制
        timer: null,//地图中时间参数
        lab: "",//热点类型标签(用于收费提示)
        dt:false,//地图数据加载时水印控制
        userAdmin:$("#isDZ").val(),//判断登陆用户的用户名
        fxCount:$("#fx").val(),
        userT:$("#ut").val(),
        wb:$("#wx").val(),
        capital:0,
        city:"",
        page:1,
        pageSize:100,
        xs:0,
        pageActive:1,
        controlPage:1,
        area1:$("#area1").val(),
        area2:$("#area2").val(),
        area3:$("#area3").val(),
        countTime:$("#countTime").val(),
        countBranches:$("#countBranches").val(),
        labelIds:$("#labelIds").val(),
        GG:"国际",
        DZUser:0,
        BS:0,//标识是否点击的省份，来判断是否剔除文娱小于30热度的
        // isLastHotDay:0,//0不是，1是
        oneOrTwo:0,//0，排行榜显示一小时，1排行榜显示24小时
        addParame:[],
        dd:1,//控制排行榜详情水球图所展示的数据1：一小时的，24：24小时的
    },
    // watch: {
    //     rankData: function () {
    //         if (this.isLogin === "true") {
    //             if (this.rankData != null && this.dd ==1) {
    //                 this.bar5((parseFloat(this.rankData[0].ratioHotDayHour).toFixed(2)) / 100);
    //                 this.bar7((parseFloat(this.rankData[0].ratioHotDifferenceDayHour).toFixed(2)) / 100);
    //             }else {
    //                 this.bar5((parseFloat(this.rankData[0].ratioHotDay).toFixed(2)) / 100);
    //                 this.bar7((parseFloat(this.rankData[0].ratioHotDay-this.rankData[0].ratioHotLastDay).toFixed(2)) / 100);
    //             }
    //
    //         }
    //     },
    //     hotEventData: function () {
    //         setTimeout(function () {
    //             vm.bar1();
    //             vm.bar2();
    //         }, 500);
    //     },
    //     typePropDate: function () {
    //         this.bar3(this.typePropDate, this.type);
    //     },
    //     xqData: function () {
    //         if (this.isLogin === "true" && this.dd===24) {
    //             this.bar5(parseFloat((this.xqData.ratioHotDay)/100).toFixed(4));
    //             this.bar7(parseFloat((this.xqData.ratioHotDay - this.xqData.ratioHotLastDay)/100).toFixed(4));
    //         }else {
    //             this.bar5(parseFloat((this.xqData.ratioHotDayHour)/100).toFixed(4));
    //             this.bar7(parseFloat((this.xqData.ratioHotDifferenceDayHour)/100).toFixed(4));
    //         }
    //     },
    // },
    created: function () {
        // this.China();
        // this.getChooseListData();
        // this.changeMax();
        // this.hotTop5();
        // this.hot();
        this.classList();
        // this.typeData();
        // this.bigData();
    },
    // mounted: function () {
    //     //用于验证页面是否加载而发起的请求
    //     $(function () {
    //         $('[data-toggle="tooltip"]').tooltip();
    //     });
    //     this.bar2();
    //     //点击更多效果js
    //     $('.btn-more').on('click', function () {
    //         $(this).find('i').toggleClass('rotate180');
    //         $(this).parents('.group-item').find('.open').toggle()
    //     });
    //     //热点事件top5效果
    //     $('.map-tabs li').on('click', function () {
    //         $(this).addClass('active').siblings().removeClass('active');
    //         var index = $(this).index();
    //         $(".module-content .module-item").eq(index).addClass('active').siblings().removeClass('active');
    //         if (index == 1) {
    //             setTimeout(function () {
    //                 vm.bar4_1(0);
    //                 vm.bar4_2(0);
    //             }, 500)
    //         } else if (index == 2) {
    //             setTimeout(function () {
    //                 vm.bar4_1_1(0);
    //                 vm.bar4_2_1(0);
    //             }, 500)
    //         }
    //
    //     });
    //     //新增
    //     //热点事件概况切换
    //     $('.module-tabs li').on('click', function() {
    //         $(this).addClass('active').siblings().removeClass('active');
    //         var index = $(this).index();
    //         $('.module-tabs-content').find('.module-tabs-item').removeClass('active').eq(index).addClass('active');
    //     });
    //
    //     var animData = {
    //         wrapper: document.getElementById('bodymovin'),
    //         animType: 'svg',
    //         loop: true,
    //         prerender: true,
    //         autoplay: true,
    //         path: 'css/indexV4/global/vendor/lottie/no-data.json'
    //
    //     };
    //     //排行榜无数据时水印
    //     var anim = bodymovin.loadAnimation(animData);
    //     bodymovin.setSubframeRendering(false);
    // },
    // filters: {
    //     del:function (time1) {
    //         var time2=new Date().getTime();
    //         var time1 = new Date(time1).getTime();
    //         var x=time2-time1;
    //         var result=x/(1000*60*60);
    //         return result;
    //     },
    //     //处理时间戳
    //     formatDate: function (date, formatStr) {
    //         var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
    //         return moment(date).format(formatStr);
    //
    //     },
    // },

    methods: {
        // //提取的地图中的方法star
        // autoPlay: function () {
        //     if (!this.isPlay) {
        //         return;
        //     }
        //     var index = 0;
        //     if (this.timer) clearInterval(this.timer);
        //     this.timer = setInterval(function () {
        //         vm.myChart.dispatchAction({
        //             type: 'showTip',
        //             seriesIndex: 1,
        //             dataIndex: index
        //         });
        //
        //         index++;
        //         if (index >= vm.mapData1.length) {
        //             index = 0
        //         }
        //     }, 3000);
        //     return index
        // },
        // stopPlay: function () {
        //     clearTimeout(this.timer);
        //     this.isPlay = false;
        // },
        // startPlay: function () {
        //     this.isPlay = true;
        //     this.autoPlay();
        // },
        // //提取的地图中的方法end
        // //排行榜小眼睛
        // // eye: function (value) {
        // //     $("#title").val(value.incidentTitle);
        // //     $("#keyword").val(value.keyword);
        // //     $("#filterKeyword").val(value.filterKeyword);
        // //     var $form = $("#searchForm");
        // //     $form.prop("action", actionBase + "/goSearch.shtml");
        // //     $form.submit();
        // // },
        // //切至全国
        // // qh: function () {
        // //     this.BS = 0;
        // //     this.DZUser = 0;
        // //     this.lab = "";
        // //     this.activeTypeClass=-1;
        // //     this.xs=0;
        // //     this.city="";
        // //     this.capital=0;
        // //     this.page=1;
        // //     this.pageSize=100;
        // //     this.labels1="";
        // //     this.areaType = "";
        // //     this.startPlay();
        // //     this.province1 = "";
        // //     this.province2 = "";
        // //     this.qg = "全部";
        // //     this.hot();
        // //     this.getChooseListData();
        // //     this.changeMax();
        // //     this.hotTop5();
        // //     this.typeData();
        // //     this.myChart.dispatchAction({
        // //         type: 'downplay'
        // //     });
        // //     $("#piezhan").show();
        // //     $(".layout100").css('float', 'left')
        // //
        // // },
        // //变化最大top
        // changeMax: function () {
        //     //发送 post 请求
        //     this.$http.post(actionBase + '/view/home/hotEvent/selectChooseListData.action', {
        //         timeType: this.timeType1,
        //         sort: 5,
        //         labels: this.labels1,
        //         province: this.province2,
        //         areaType: this.areaType,
        //         page:this.page,
        //         pageSize:this.pageSize,
        //         city:this.city,
        //         isLogin:this.isLogin
        //     }, {emulateJSON: true}).then(function (res) {
        //         if(this.labels1===""&&this.areaType!==2){
        //             var na=res.data.list;
        //             var list = [];
        //             for (var i=0;i<na.length;i++){
        //                 if (na[i].labelNames.split(",").indexOf('明星')>-1 || na[i].labelNames.split(",").indexOf('电影')>-1
        //                     || na[i].labelNames.split(",").indexOf('电视剧')>-1
        //                     || na[i].labelNames.split(",").indexOf('综艺')>-1
        //                     || na[i].labelNames.split(",").indexOf('文化')>-1){
        //                     if(na[i].ratioHotDay>30){
        //                         list.push(na[i]);
        //                     }
        //                 }else {
        //                     list.push(na[i])
        //                 }
        //             }
        //             vm.changeMaxDate = list;
        //         }else {
        //             this.changeMaxDate = res.data.list;
        //         }
        //     });
        // },
        // //热点事件top5
        // hotTop5: function () {
        //     //发送 post 请求
        //     this.$http.post(actionBase + '/view/home/hotEvent/selectChooseListData.action', {
        //         timeType: this.timeType1,
        //         sort: 2,
        //         labels: this.labels1,
        //         province: this.province2,
        //         areaType: this.areaType,
        //         page:this.page,
        //         pageSize:this.pageSize,
        //         city:this.city,
        //         isLogin:this.isLogin
        //     }, {emulateJSON: true}).then(function (res) {
        //         if(this.labels1===""&&this.areaType!==2){
        //             var na=res.data.list;
        //             var list = [];
        //             for (var i=0;i<na.length;i++){
        //                 if (na[i].labelNames.split(",").indexOf('明星')>-1 || na[i].labelNames.split(",").indexOf('电影')>-1
        //                     || na[i].labelNames.split(",").indexOf('电视剧')>-1
        //                     || na[i].labelNames.split(",").indexOf('综艺')>-1
        //                     || na[i].labelNames.split(",").indexOf('文化')>-1){
        //                     if(na[i].ratioHotDay>30){
        //                         list.push(na[i]);
        //                     }
        //                 }else {
        //                     list.push(na[i])
        //                 }
        //             }
        //             vm.hotTopData = list;
        //         }else {
        //             this.hotTopData = res.data.list;
        //         }
        //
        //     });
        // },
        // //所有省份数据
        // allProvince: function () {
        //     this.dt=false;
        //     this.mapData2 = [];
        //     //发送 post 请求
        //     this.$http.post(actionBase + '/view/home/hotEvent/allProvince.action', {
        //         timeType: this.timeType1,
        //     }, {emulateJSON: true}).then(function (res) {
        //         this.dt=true;
        //         this.allProvinceData = res.data.data;
        //         if (this.allProvinceData != null) {
        //             for (var i = 0; i < this.allProvinceData.length; i++) {
        //                 var x = {
        //                     name: "",
        //                     value: [{name: "", value: 0}],
        //                     incidentTitle: "",
        //                     keyword: "",
        //                     filterKeyword: ""
        //                 };
        //                 x.name = this.allProvinceData[i].province;
        //                 x.value[0].name = (this.allProvinceData[i].incidentTitle)/*.substring(0, 10) + "..."*/;
        //                 x.value[0].value = parseFloat(this.allProvinceData[i].ratioHotDay).toFixed(2);
        //                 x.incidentTitle = this.allProvinceData[i].incidentTitle;
        //                 x.keyword = this.allProvinceData[i].keyword;
        //                 x.filterKeyword = this.allProvinceData[i].filterKeyword;
        //                 this.mapData2.push(x)
        //             }
        //         }
        //         this.getDataMap('dataMap');
        //     });
        // },
        // //为了展示地图的全国信息
        // China: function () {
        //     //地图各省份遍历事件
        //     this.mapData1 = [];
        //     this.$http.post(actionBase + '/view/home/hotEvent/allChina.action', {
        //         timeType: this.timeType1,
        //         sort: 2,
        //         areaType: 1,
        //     }, {emulateJSON: true}).then(function (res) {
        //         this.chinaData = res.data.list;
        //         if (this.chinaData != null) {
        //             for (var i = 0; i < this.chinaData.length; i++) {
        //                 var v = {
        //                     name: "",
        //                     value: [{
        //                         name: "", value: 0, incidentTitle: "",
        //                         keyword: "",
        //                         filterKeyword: ""
        //                     }],
        //
        //                 };
        //                 if (this.chinaData[i].province === "全国") {
        //                     v.name = "北京"
        //                 } else {
        //                     v.name = this.chinaData[i].province;
        //                 }
        //                 if (this.chinaData[i].incidentTitle.length>=15){
        //                     v.value[0].name = (this.chinaData[i].incidentTitle).substring(0, 15) + "...";
        //                 }else {
        //                     v.value[0].name = this.chinaData[i].incidentTitle;
        //                 }
        //
        //                 v.value[0].value = parseFloat(this.chinaData[i].ratioHotDay).toFixed(2);
        //                 v.value[0].incidentTitle = this.chinaData[i].incidentTitle;
        //                 v.value[0].keyword = this.chinaData[i].keyword;
        //                 v.value[0].filterKeyword = this.chinaData[i].filterKeyword;
        //                 this.mapData1.push(v);
        //             }
        //             vm.allProvince();
        //         }
        //
        //     });
        // },
        // //点击热度排序切换参数
        // hotSort: function () {
        //     this.hotClass = 1;
        //     this.activeClass = 0;
        //     this.sort = 7;
        //     this.dd = 1;
        //     this.oneOrTwo = 0;
        //     this.getChooseListData();
        // },
        // //点击热度变化排序切换参数
        // chartSort: function () {
        //     this.hotClass = 2;
        //     this.activeClass = 0;
        //     this.sort = 2;
        //     this.dd = 24;
        //     this.oneOrTwo = 1;
        //     this.getChooseListData();
        // },
        // //热点事件top5
        // detailsBtn: function (me, index) {
        //     this.x = index;
        //     var $li = $(me.target).parents(".module-map-item").siblings(".module-map-item");
        //     $li.find(".mmi-title").removeClass("active");
        //     $li.find(".chart-body").hide();
        //     $(me.target).addClass('active');
        //     $(me.target).parents(".mmi-title").toggleClass("active");
        //     $(me.target).parents(".mmi-title").nextAll(".chart-body").toggle();
        //     if ($(me.target).parents(".mmi-title").hasClass("active")) {
        //         $(me.target).removeClass('active');
        //         setTimeout(function () {
        //             vm.bar4_1(index);
        //             vm.bar4_2(index);
        //         }, 500);
        //     } else {
        //         $(me.target).addClass('active');
        //         setTimeout(function () {
        //             vm.bar4_1(index);
        //             vm.bar4_2(index);
        //         }, 500);
        //
        //     }
        // },
        // //变化最大top5
        // detailsBtns: function (me, index) {
        //     this.y = index;
        //     var $li = $(me.target).parents(".module-map-item").siblings(".module-map-item");
        //     $li.find(".mmi-title").removeClass("active");
        //     $li.find(".chart-body").hide();
        //     $(me.target).addClass('active');
        //     $(me.target).parents(".mmi-title").toggleClass("active");
        //     $(me.target).parents(".mmi-title").nextAll(".chart-body").toggle();
        //     if ($(me.target).parents(".mmi-title").hasClass("active")) {
        //         $(me.target).removeClass('active');
        //         setTimeout(function () {
        //             vm.bar4_1_1(index);
        //             vm.bar4_2_1(index);
        //         }, 500);
        //     } else {
        //         $(me.target).addClass('active');
        //         setTimeout(function () {
        //             vm.bar4_1_1(index);
        //             vm.bar4_2_1(index);
        //         }, 500);
        //
        //     }
        //
        // },
        // //初始化新闻政务报刊数据、
        // three: function () {
        //     var opt = $.extend(true, {}, echartsOpts["bar"]);
        //     if (this.rankData != null) {
        //         opt.series[0].data[0] = this.rankData[0].mediaNumberDay;
        //         opt.series[0].data[1] = this.rankData[0].zwNumberDay;
        //         opt.series[0].data[2] = this.rankData[0].bkNumberDay;
        //         Vue.nextTick(function () {
        //             setEchartsOpion({$id: $("#lineEchartend"), opt: opt});
        //         });
        //     }
        //
        // },
        // clickThree: function () {
        //     var opt = $.extend(true, {}, echartsOpts["bar"]);
        //     opt.series[0].data[0] = this.xw;
        //     opt.series[0].data[1] = this.zw;
        //     opt.series[0].data[2] = this.bk;
        //     setEchartsOpion({$id: $("#lineEchartend"), opt: opt});
        // },
        // //初始化排行榜敏感非敏感的请求参数，使其默认展示第一条
        // initMGParm: function () {
        //     var opt = $.extend(true, {}, echartsOpts["pie"]);
        //     if (this.rankData != null) {
        //         opt.series[0].data[0].value = this.rankData[0].mgNumberDay;
        //         opt.series[0].data[1].value = this.rankData[0].fmgNumberDay;
        //         Vue.nextTick(function () {
        //             setEchartsOpion({$id: $("#pieEchartend"), opt: opt});
        //         });
        //
        //     }
        //
        // },
        // clickMG: function () {
        //     var opt = $.extend(true, {}, echartsOpts["pie"]);
        //     opt.series[0].data[0].value = this.mg;
        //     opt.series[0].data[1].value = this.fmg;
        //     setEchartsOpion({$id: $("#pieEchartend"), opt: opt});
        // },
        // //初始化热度指数趋势请求的参数，使其默认画出第一条数据
        // initParam: function () {
        //     this.getHotCharLine(this.rankData[0]);
        // },
        // // 热度指数趋势图数据
        // getHotCharLine: function (value) {
        //     var v = JSON.parse(value.lineData);
        //     var opt = $.extend(true, {}, echartsOpts["line"]);
        //     var x=[];
        //     var y=[];
        //     for (var i = 0; i < v.length; i++) {
        //         opt.xAxis[0].data[i] = v[i].name;
        //         x.push(v[i].name);
        //         opt.series[0].data[i] = v[i].total;
        //         y.push(v[i].total)
        //     }
        //     Vue.nextTick(function () {
        //         setEchartsOpion({$id: $("#informationChart"), opt: opt});
        //     });
        // },
        // //获取排行列表
        // getChooseListData: function () {
        //     //发送 post 请求
        //     this.$http.post(actionBase + '/view/home/hotEvent/selectChooseListData.action', {
        //         timeType: this.timeType1,
        //         sort: this.sort,
        //         labels: this.labels1,
        //         province: this.province2,
        //         areaType: this.areaType,
        //         page:this.page,
        //         pageSize:this.pageSize,
        //         city:this.city,
        //         isLogin:this.isLogin
        //     }, {emulateJSON: true}).then(function (res) {
        //         if (res.data.code === "0000" || res.data.code === null || res.data.data === null) {
        //             this.zt = true;
        //         }
        //         if(parseInt(this.countBranches)>res.data.totalCount){
        //             vm.controlPage = Math.ceil((res.data.totalCount)/20);
        //         }else {
        //             vm.controlPage = Math.ceil((parseInt(vm.countBranches))/20);
        //         }
        //         //全部里剔除热度小于30的娱乐事件
        //         if(this.labels1===""&&this.areaType!==2&&this.DZUser!==1&&this.BS!==1){
        //             var na=res.data.list;
        //             var list = [];
        //             var resu = [];
        //             for (var i=0;i<na.length;i++){
        //                 if (na[i].labelNames.split(",").indexOf('明星')>-1 || na[i].labelNames.split(",").indexOf('电影')>-1
        //                     || na[i].labelNames.split(",").indexOf('电视剧')>-1
        //                     || na[i].labelNames.split(",").indexOf('综艺')>-1
        //                     || na[i].labelNames.split(",").indexOf('文化')>-1){
        //                     if(na[i].ratioHotDay>30){
        //                         list.push(na[i]);
        //                     }
        //                 }else {
        //                     list.push(na[i])
        //                 }
        //             }
        //             if(list.length>=20){
        //                 for(var g=0;g<20;g++){
        //                     resu.push(list[g]);
        //                 }
        //                 vm.rankData = resu;
        //             }else {
        //                 vm.rankData = list;
        //             }
        //
        //
        //         }else {
        //             this.rankData = res.data.list;
        //         }
        //         //top图形变换
        //         if (this.rankData != null) {
        //             setTimeout(function () {
        //                 if ($("#h5").hasClass("active")) {
        //                     vm.bar4_1(vm.x);
        //                     vm.bar4_2(vm.x);
        //                 } else if ($("#b5").hasClass("active")) {
        //                     vm.bar4_1_1(vm.y);
        //                     vm.bar4_2_1(vm.y);
        //                 }
        //             }, 500);
        //             this.indexData = {
        //                 index: 0,
        //                 name: this.rankData[0].labelNames,
        //                 title: this.rankData[0].incidentTitle,
        //                 time: this.rankData[0].createTime
        //             };
        //             this.addParame=this.rankData[0];
        //         } else {
        //             this.mapData1 = null;
        //         }
        //         if (this.isLogin == "true") {
        //             this.initParam();
        //             this.initMGParm();
        //             this.three();
        //         }
        //     });
        // },
        // //获取地图右侧均值等数据
        // hot: function () {
        //     var params = {
        //         timeType: this.timeType1,
        //         labels: this.labels1,
        //         province: this.province1,
        //         city:this.city
        //     };
        //     //发送 post 请求
        //     this.$http.post(actionBase + '/view/home/hotEvent/selectRankData.action', params, {emulateJSON: true}).then(function (res) {
        //         this.hotEventData = res.data.data;
        //     });
        // },
        // //点击切换热点类型标签
        // changeParm: function (obj, index) {
        //     if (this.province1 === "") {
        //         $("#piezhan").hide();
        //         $(".layout100").css('float', 'inherit');
        //         this.activeTypeClass = index;
        //         this.BS = 0;
        //         this.DZUser = 0;
        //         var value = obj.id;
        //         if(value==1127){
        //             this.labels1 = "";
        //             this.page = 1;
        //             this.pageSize = 20;
        //             this.lab = obj.name;
        //             this.areaType = 2;
        //             this.getChooseListData();
        //
        //         }else {
        //             this.labels1 = value;
        //             this.page = 1;
        //             this.pageSize = 20;
        //             this.lab = obj.name;
        //             this.areaType = 1;
        //             this.getChooseListData();
        //             this.changeMax();
        //             this.hotTop5();
        //             this.hot();
        //         }
        //     } else {
        //         $('#moneyTip').modal('show');
        //     }
        // },
        //获取热点类型标签
        classList: function () {
            $.ajax({
                url: '/api/hot/largeScreen/labels',
                type: 'POST',
                cache: 'false',
                success: function (res) {
                    this.hotClassData = res.data.data.childrens;
                    var gj={id:1127,name:"国际"};
                    this.newHotClassData = [
                        this.hotClassData[9],
                        this.hotClassData[4],
                        gj,
                        this.hotClassData[8],
                        this.hotClassData[2],
                        this.hotClassData[5],
                        this.hotClassData[7],
                        this.hotClassData[3],
                        this.hotClassData[6],
                        this.hotClassData[0],
                        this.hotClassData[1],

                    ];
                }
            });
        },
        // //24小时均值变化数据
        // bar1: function () {
        //     var opt = $.extend(true, {}, echartsOpts["bar1"]);
        //     opt.series[0].data[0].value = parseFloat(this.hotEventData.hotAvg = 0 ? 0.00 : this.hotEventData.hotAvg).toFixed(2);
        //     setEchartsOpion({$id: $("#pieBox1"), opt: opt});
        //
        // },
        // //24小时均值数据
        // bar2: function () {
        //     var opt = $.extend(true, {}, echartsOpts["bar2"]);
        //     opt.series[0].data[0].value = parseFloat(this.hotEventData.hotDifferenceAvg = 0 ? 0.00 : this.hotEventData.hotDifferenceAvg).toFixed(2);
        //     setEchartsOpion({$id: $("#pieBox2"), opt: opt});
        //
        //
        // },
        // //类型占比
        // bar3: function (data, type) {
        //     var opt = $.extend(true, {}, echartsOpts["bar3"]);
        //     opt.legend.data = type;
        //     opt.series[0].data = data;
        //     setEchartsOpion({$id: $("#pieBox3"), opt: opt});
        // },
        // //top敏感非敏感
        // bar4_1: function (x) {
        //     var opt = $.extend(true, {}, echartsOpts["bar4_1"]);
        //     if (this.hotTopData != null) {
        //         opt.series[0].data[0].value = this.hotTopData[x].mgNumberDay;
        //         opt.series[0].data[1].value = this.hotTopData[x].fmgNumberDay;
        //         setEchartsOpion({$id: $("#pieE" + x), opt: opt});
        //     }
        //
        // },
        // //top类型数量
        // bar4_2: function (x) {
        //     var max = 0;
        //     var opt = $.extend(true, {}, echartsOpts["bar4_2"]);
        //     if (this.hotTopData != null) {
        //         max = (this.hotTopData[x].zwNumberDay > this.hotTopData[x].mediaNumberDay) ? this.hotTopData[x].zwNumberDay : this.hotTopData[x].mediaNumberDay;
        //         max = (max > this.hotTopData[x].bkNumberDay) ? max : this.hotTopData[x].bkNumberDay;
        //         opt.series[0].data[0] = max;
        //         opt.series[0].data[1] = max;
        //         opt.series[0].data[2] = max;
        //         opt.series[0].data[0] = this.hotTopData[x].zwNumberDay;
        //         opt.series[0].data[1] = this.hotTopData[x].mediaNumberDay;
        //         opt.series[0].data[2] = this.hotTopData[x].bkNumberDay;
        //         setEchartsOpion({$id: $("#barE" + x), opt: opt});
        //     }
        //
        // },
        // //变化最大top
        // bar4_1_1: function (y) {
        //     var opt = $.extend(true, {}, echartsOpts["bar4_1_1"]);
        //     if (this.changeMaxDate != null) {
        //         opt.series[0].data[0].value = this.changeMaxDate[y].mgNumberDay;
        //         opt.series[0].data[1].value = this.changeMaxDate[y].fmgNumberDay;
        //         setEchartsOpion({$id: $("#pieEchart1" + y), opt: opt});
        //     }
        //
        // },
        // //top类型数量
        // bar4_2_1: function (y) {
        //     var max = 0;
        //     var opt = $.extend(true, {}, echartsOpts["bar4_2_1"]);
        //     if (this.changeMaxDate != null) {
        //         max = (this.changeMaxDate[y].zwNumberDay > this.changeMaxDate[y].mediaNumberDay) ? this.changeMaxDate[y].zwNumberDay : this.changeMaxDate[y].mediaNumberDay;
        //         max = (max > this.changeMaxDate[y].bkNumberDay) ? max : this.changeMaxDate[y].bkNumberDay;
        //         opt.series[0].data[0] = max;
        //         opt.series[0].data[1] = max;
        //         opt.series[0].data[2] = max;
        //         // opt.series[0].data[0].value = this.changeMaxDate[y].zwNumberDay;
        //         // opt.series[0].data[1].value = this.changeMaxDate[y].mediaNumberDay;
        //         // opt.series[0].data[2].value = this.changeMaxDate[y].bkNumberDay;
        //         opt.series[0].data[0] = this.changeMaxDate[y].zwNumberDay;
        //         opt.series[0].data[1] = this.changeMaxDate[y].mediaNumberDay;
        //         opt.series[0].data[2] = this.changeMaxDate[y].bkNumberDay;
        //         setEchartsOpion({$id: $("#barEchart1" + y), opt: opt});
        //     }
        //
        // },
        // clearTimeTickers: function () {
        //     for (var i = 0; i < vm.intervalIdList.length; i++) {
        //         clearInterval(vm.intervalIdList[i]);
        //     }
        //     for (var i = 0; i < vm.timeoutIdList.length; i++) {
        //         clearTimeout(vm.timeoutIdList[i]);
        //     }
        //     vm.intervalIdList = [];
        //     vm.timeoutIdList = [];
        // },
        // //地图
        // getDataMap: function (id) {
        //     vm.box = document.getElementById(id);
        //     if (vm.myChart === undefined) {
        //         vm.myChart = echarts.init(vm.box);
        //     }
        //     var mapName = 'china';
        //     var geoCoordMap = {
        //         '钓鱼岛': [123.0254, 25.1986]
        //     };
        //     const rawData = this.mapData2;
        //     //各省份遍历事件
        //     const rawData2 = this.mapData1;
        //     const dataT = [
        //         {
        //             name: '钓鱼岛',
        //             value: 5
        //         },
        //     ];
        //     // var geoCoordMap ={};
        //     var mapFeatures = echarts.getMap(mapName).geoJson.features;
        //     mapFeatures.forEach(function (v) {
        //         // 地区名称
        //         var name = v.properties.name;
        //         // 地区经纬度
        //         geoCoordMap[name] = v.properties.cp;
        //     });
        //     var convertData = function (data) {
        //         var res = [];
        //         for (var i = 0; i < data.length; i++) {
        //             var geoCoord = geoCoordMap[data[i].name];
        //             if (geoCoord) {
        //                 res.push({
        //                     name: data[i].name,
        //                     value: geoCoord.concat(data[i].value)
        //                 });
        //             }
        //         }
        //         return res;
        //     };
        //     var myTooltip = new myTooltipC(id);
        //     var namearr = ['1'];
        //     var option = {
        //         tooltip: {
        //             trigger: 'item',
        //             triggerOn: 'click',
        //             enterable: true,
        //         },
        //         legend: [{
        //             show: false,
        //             data: namearr,
        //             itemWidth: 5,
        //             itemHeight: 5,
        //             textStyle: {
        //                 color: '#ddd',
        //                 fontSize: 10
        //             },
        //             top: '95%',
        //         }],
        //         xAxis: [],
        //         yAxis: [],
        //         grid: [],
        //         geo: {
        //             type: 'map',
        //             map: 'china',
        //             top: '5',
        //             bottom: '80',
        //             mapType: mapName,
        //             selectedMode: 'single',
        //             label: {
        //                 normal: {
        //                     textStyle: {
        //                         color: '#ddd'
        //                     },
        //                     show: true
        //                 },
        //                 emphasis: {
        //                     textStyle: {
        //                         color: '#ffffff'
        //                     },
        //                     show: true
        //                 }
        //             },
        //             itemStyle: {
        //                 normal: {
        //                     areaColor: "rgba(19, 72, 174, 0.8)",
        //                     borderColor: "rgba(15, 106, 207,1)",
        //                     borderWidth: 1,
        //                     shadowColor: 'rgba(255, 255, 255, 0.3)',
        //                     shadowBlur: 1
        //                 },
        //                 emphasis: {
        //                     areaColor: "#29C2F5"
        //                 }
        //             }
        //         },
        //         series: [{
        //             name: 'credit',
        //             type: 'scatter',
        //             coordinateSystem: 'geo',
        //             data: convertData(dataT),
        //             symbolSize: function (val) {
        //                 return val[2] / 10000;
        //             },
        //             label: {
        //                 normal: {
        //                     formatter: '{b}',
        //                     position: 'top',
        //                     distance: 15,
        //                     show: true
        //                 },
        //                 emphasis: {
        //                     show: true
        //                 }
        //             },
        //             itemStyle: {
        //                 normal: {
        //                     color: '#FFFFFF'
        //                 }
        //             }
        //         },
        //             {
        //                 name: '轮播',
        //                 type: 'scatter',
        //                 coordinateSystem: 'geo',
        //                 data: convertData(rawData2),
        //                 symbolSize: 0,
        //                 label: {
        //                     normal: {
        //                         show: false
        //                     },
        //                     emphasis: {
        //                         show: false
        //                     }
        //                 },
        //                 tooltip: {
        //                     backgroundColor: 'transparent',
        //                     axisPointer: {
        //                         type: 'line',
        //                         lineStyle: {
        //                             opacity: 0
        //                         }
        //                     },
        //                     showDelay: 0,                                  //浮层显示的延迟，单位为 ms
        //                     hideDelay: 1000,
        //                     transitionDuration: 0.5,
        //                     enterable: true,
        //                     position (pos) {
        //                         let position = myTooltip.getPosOrSize('pos', pos);
        //                         return position
        //                     },
        //                     formatter (params) {
        //                         if (params.dataIndex != -1) {
        //                             $("#title").val(params.data.value[2].incidentTitle);
        //                             $("#keyword").val(params.data.value[2].keyword);
        //                             $("#filterKeyword").val(params.data.value[2].filterKeyword);
        //                             var text = `${params.value[2].name}\n热度：${params.value[2].value}`;
        //                             var tooltipDom = myTooltip.getTooltipDom(text);
        //                             return '<a href="javascript:;" onclick="mp()">'+tooltipDom+'</a>'
        //                         }
        //                     },
        //                 },
        //                 itemStyle: {
        //                     normal: {
        //                         color: '#FFFFFF'
        //                     }
        //                 }
        //             },
        //
        //             {
        //                 type: 'map',
        //                 map: mapName,
        //                 geoIndex: 0,
        //                 aspectScale: 0.75, //长宽比
        //                 showLegendSymbol: false, // 存在legend时显示
        //                 selectedMode: 'single',
        //                 label: {
        //                     normal: {
        //                         show: true
        //                     },
        //                     emphasis: {
        //                         show: false,
        //                         textStyle: {
        //                             color: '#fff'
        //                         }
        //                     }
        //                 },
        //                 tooltip: {
        //                     backgroundColor: 'transparent',
        //                     axisPointer: {
        //                         type: 'line',
        //                         lineStyle: {
        //                             opacity: 0
        //                         }
        //                     },
        //                     enterable: true,
        //                     formatter: function (params) {
        //                         if (params.dataIndex != -1 && params.data!=undefined) {
        //                             $("#title").val(params.data.incidentTitle);
        //                             $("#keyword").val(params.data.keyword);
        //                             $("#filterKeyword").val(params.data.filterKeyword);
        //                             var htmlBox = '<div class="country-item">\n' +
        //                                 '                    <ul class="active">\n' +
        //                                 '                        <li>\n' +
        //                                 '                            <a href="javascript:;" onclick="mp()">\n' +
        //                                 '                                <p>' + params.data.value[0].name + '</p>\n' +
        //                                 '                                <p>热度：' + params.data.value[0].value + '</p>\n' +
        //                                 '                                <div class="edgeHorn lt"></div>\n' +
        //                                 '                                <div class="edgeHorn rt"></div>\n' +
        //                                 '                                <div class="edgeHorn rb"></div>\n' +
        //                                 '                                <div class="edgeHorn lb"></div>\n' +
        //                                 '                            </a>\n' +
        //                                 '                        </li>\n' +
        //                                 '                    </ul>\n' +
        //                                 '                </div>';
        //                             return htmlBox;
        //                         }
        //
        //                     }
        //                 },
        //                 itemStyle: {
        //                     normal: {
        //                         areaColor: "rgba(19, 72, 174, 0.8)",
        //                         borderColor: "rgba(0, 0, 0,0.5)",
        //                         borderWidth: 1.5,
        //                         shadowColor: 'rgba(255, 255, 255, 0.8)',
        //                         shadowBlur: 5
        //                     },
        //                     emphasis: {
        //                         areaColor: "#29C2F5"
        //                     }
        //                 },
        //                 animation: false,
        //                 data: rawData
        //             },
        //             {
        //                 type: 'effectScatter',
        //                 name: '触发',
        //                 coordinateSystem: 'geo',
        //                 effectType: 'ripple',
        //                 rippleEffect: { //涟漪特效
        //                     period: 6, //动画时间，值越小速度越快
        //                     brushType: 'fill', //波纹绘制方式 stroke, fill
        //                     scale: 3 //波纹圆环最大限制，值越大波纹越大
        //                 },
        //                 large: true,        // 大规模散点图
        //                 largeThreshold: 400,
        //                 zlevel: 1,
        //                 symbolSize: function (val) {
        //                     return [val[2].value / 3, val[2].value / 4];
        //                 },
        //                 tooltip: {
        //                     backgroundColor: 'transparent',
        //                     axisPointer: {
        //                         type: 'line',
        //                         lineStyle: {
        //                             opacity: 0
        //                         }
        //                     },
        //                     enterable: true,
        //                     formatter: function (params) {
        //                         var htmlBox = '<div class="country-item">\n' +
        //                             '                    <ul class="active">\n' +
        //                             '                        <li>\n' +
        //                             '                            <a href="javascript:;">\n' +
        //                             '                                <p>' + params.data.value[2].name + '</p>\n' +
        //                             '                                <p>热度：' + params.data.value[2].value + '</p>\n' +
        //                             '                                <div class="edgeHorn lt"></div>\n' +
        //                             '                                <div class="edgeHorn rt"></div>\n' +
        //                             '                                <div class="edgeHorn rb"></div>\n' +
        //                             '                                <div class="edgeHorn lb"></div>\n' +
        //                             '                            </a>\n' +
        //                             '                        </li>\n' +
        //                             '                    </ul>\n' +
        //                             '                </div>';
        //                         return htmlBox;
        //                     }
        //                 },
        //
        //                 data: convertData(rawData.sort(function (a, b) {
        //                     return b.value - a.value;
        //                 })),
        //                 itemStyle: {
        //                     normal: {
        //                         show: true,
        //                         color: {
        //                             type: 'radial',
        //                             x: 0.5,
        //                             y: 0.5,
        //                             r: 0.5,
        //                             colorStops: [{
        //                                 offset: 0,
        //                                 color: "rgba(252, 225, 86, 0.3)",
        //                             }, {
        //                                 offset: 0.9,
        //                                 color: "rgba(252, 225, 86, 0.3)",
        //                             },
        //                                 {
        //                                     offset: 1,
        //                                     color: "rgba(252, 225, 86, 1)"
        //                                 }
        //                             ],
        //                             globalCoord: false // 缺省为 false
        //                         }
        //                     }
        //                 }
        //             },]
        //     };
        //
        //     function renderEachCity() {
        //
        //         echarts.util.each(rawData, function (dataItem, idx) {
        //             var geoCoord = geoCoordMap[dataItem.name];
        //             var coord = vm.myChart.convertToPixel('geo', geoCoord);
        //             idx += '';
        //
        //
        //             option.xAxis.push({
        //                 id: idx,
        //                 gridId: idx,
        //                 type: 'category',
        //                 name: dataItem.name,
        //                 nameStyle: {
        //                     color: '#fff',
        //                     fontSize: 12
        //                 },
        //                 nameLocation: 'middle',
        //                 nameGap: 3,
        //                 splitLine: {
        //                     show: false
        //                 },
        //                 axisTick: {
        //                     show: false
        //                 },
        //                 axisLabel: {
        //                     show: false
        //                 },
        //                 axisLine: {
        //                     show: false,
        //                     lineStyle: {
        //                         color: 'rgba(0,0,0,0)'
        //                     }
        //                 },
        //                 data: [dataItem.name],
        //             });
        //
        //             option.yAxis.push({
        //                 id: idx,
        //                 gridId: idx,
        //                 show: false,
        //                 type: 'value',
        //                 min: 0,
        //                 max: 50,
        //             });
        //
        //             option.grid.push({
        //                 id: idx,
        //                 z: 100,
        //                 width: 30,
        //                 height: 220,
        //                 left: coord[0] - 15,
        //                 top: coord[1] - 220,
        //             });
        //
        //
        //             for (var i = 0; i < namearr.length; i++) {
        //                 option.series.push({
        //                     name: namearr[i],
        //                     type: 'bar',
        //                     stack: 'bar' + idx,
        //                     xAxisId: idx,
        //                     yAxisId: idx,
        //                     large: true,        // 大规模散点图
        //                     largeThreshold: 400,
        //                     barWidth: 6,
        //                     tooltip: {
        //                         show:false
        //                         // backgroundColor: 'transparent',
        //                         // axisPointer: {
        //                         //     type: 'line',
        //                         //     lineStyle: {
        //                         //         opacity: 0
        //                         //     }
        //                         // },
        //                         // formatter: function (params) {
        //                         //     var htmlBox = '<div class="country-item">\n' +
        //                         //         '                    <ul class="active">\n' +
        //                         //         '                        <li>\n' +
        //                         //         '                            <a href="javascript:;">\n' +
        //                         //         '                                <p>' + params.data.name + '</p>\n' +
        //                         //         '                                <p>热度：' + params.data.value + '</p>\n' +
        //                         //         '                                <div class="edgeHorn lt"></div>\n' +
        //                         //         '                                <div class="edgeHorn rt"></div>\n' +
        //                         //         '                                <div class="edgeHorn rb"></div>\n' +
        //                         //         '                                <div class="edgeHorn lb"></div>\n' +
        //                         //         '                            </a>\n' +
        //                         //         '                        </li>\n' +
        //                         //         '                    </ul>\n' +
        //                         //         '                </div>';
        //                         //     return htmlBox;
        //                         // }
        //                     },
        //                     itemStyle: {
        //                         normal: {
        //                             barBorderRadius: 20, // 统一设置四个角的圆角大小
        //                             // barBorderRadius: [30, 30, 0, 0], //（顺时针左上，右上，右下，左下）
        //                             // color : colorarr[i]
        //                             color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
        //                                 offset: 0,
        //                                 color: "rgba(251, 225, 86, 1)" // 0% 处的颜色
        //                             }, {
        //                                 offset: 1,
        //                                 color: "rgba(251, 225, 86, 0)" // 100% 处的颜色
        //                             }], false)
        //
        //                         }
        //
        //                     },
        //                     label: {
        //                         normal: {
        //                             show: false
        //                         }
        //                     },
        //                     data: [dataItem.value[0]]
        //                 });
        //             }
        //
        //         });
        //         vm.myChart.setOption(option);
        //     }
        //
        //     setTimeout(function () {
        //         renderEachCity();
        //     }, 1);
        //     vm.myChart.setOption(option);
        //     window.onresize = vm.myChart.resize;
        //     // var allowRun = false; //设置标记
        //     /**
        //      * @return {number}
        //      */
        //     vm.autoPlay();
        //     vm.myChart.on('click', function (params) {
        //         if (vm.lab === "") {
        //             if (params.seriesType !== 'bar'){
        //                 vm.stopPlay();
        //                 vm.myChart.dispatchAction({
        //                     type: 'highlight',
        //                     seriesIndex: 2,
        //                     dataIndex: params.dataIndex
        //                 });
        //                 vm.BS = 1;
        //                 vm.DZUser = 0;
        //                 vm.capital=0;
        //                 vm.city="";
        //                 vm.page=1;
        //                 vm.pageSize=20;
        //                 vm.areaType=1;
        //                 vm.province1 = params.name;
        //                 vm.hot();
        //                 vm.province2 = params.name;
        //                 vm.activeClass = 0;
        //                 vm.getChooseListData();
        //                 vm.changeMax();
        //                 vm.hotTop5();
        //                 if (params.data==undefined) {
        //                     vm.myChart.dispatchAction({
        //                         type: 'downplay'
        //                     });
        //                     $('#tipsModal').modal('show');
        //                     vm.qg = "全国";
        //                     vm.typeData();
        //                 } else {
        //                     vm.qg = params.name;
        //                     vm.typeData();
        //                 }
        //             }
        //
        //         } else {
        //             vm.myChart.dispatchAction({
        //                 type: 'downplay'
        //             });
        //             $('#moneyTip').modal('show');
        //         }
        //
        //     })
        // },
        // //排行榜三个环形图数据详情
        // bar5: function (xq) {
        //     var piegEchart1 = echarts.init(document.getElementById('pie-g1'));
        //     if (this.rankData != null) {
        //         var option = {
        //             series: [{
        //                 type: 'liquidFill',
        //                 data: [xq, xq],
        //                 color: ['#71D0D5', 'rgba(178, 246, 249, 0.5)',],
        //                 center: ['50%', '50%'],
        //                 radius: '80%',
        //                 label: {
        //                     normal: {
        //                         textStyle: {
        //                             color: '#ffffff',
        //                             insideColor: 'yellow',
        //                             fontSize: 16
        //                         },
        //                         formatter: function (param) {
        //                             return (parseFloat((param.value)*100).toFixed(2));
        //
        //
        //                         },
        //                     }
        //                 },
        //
        //                 itemStyle: {
        //                     normal: {
        //                         shadowBlur: 0
        //                     }
        //                 },
        //                 backgroundStyle: {
        //                     color: 'rgba(4,24,74,0)',
        //                 },
        //                 outline: {
        //                     borderDistance: 0,
        //                     itemStyle: {
        //                         borderWidth: 0.3,
        //                         borderColor: '#71D0D5',
        //                         shadowBlur: '50',
        //                         shadowColor: 'rgba(178, 246, 249, 0.5)'
        //                     }
        //                 }
        //             }]
        //         };
        //
        //         piegEchart1.setOption(option);
        //     }
        //
        // },
        // bar7: function (xq) {
        //     var piegEchart3 = echarts.init(document.getElementById('pie-g3'));
        //     if (this.rankData != null) {
        //         var option = {
        //             series: [{
        //                 type: 'liquidFill',
        //                 data: [xq, xq],
        //                 color: ['#FF910C', 'rgba(255, 145, 12, 0.3)'],
        //                 center: ['50%', '50%'],
        //                 radius: '80%',
        //                 label: {
        //                     normal: {
        //                         textStyle: {
        //                             color: '#ffffff',
        //                             insideColor: 'yellow',
        //                             fontSize: 16
        //                         },
        //                         formatter: function (param) {
        //                             return (parseFloat((param.value) * 100).toFixed(2));
        //
        //                         },
        //                     }
        //                 },
        //
        //                 itemStyle: {
        //                     normal: {
        //                         shadowBlur: 0
        //                     }
        //                 },
        //                 backgroundStyle: {
        //                     color: 'rgba(4,24,74,0)',
        //                 },
        //                 outline: {
        //                     borderDistance: 0,
        //                     itemStyle: {
        //                         borderWidth: 0.3,
        //                         borderColor: '#FF910C',
        //                         shadowBlur: '50',
        //                         shadowColor: 'rgba(255, 145, 12, 0.3))'
        //                     }
        //                 }
        //             }]
        //         };
        //
        //         piegEchart3.setOption(option);
        //     }
        //
        // },
        // //未登录大数据解读数据
        // bigData: function () {
        //     //发送 post 请求
        //     this.$http.post(actionBase + '/view/home/hotEvent/dataRead.action', {}, {emulateJSON: true}).then(function (res) {
        //         this.bigDataRead = res.data.data;
        //     });
        // },
        // //类型占比数据
        // typeData: function () {
        //     this.typePropDate = [];
        //     this.type = [];
        //     //发送 post 请求
        //     this.$http.post(actionBase + '/view/home/hotEvent/typeProp.action', {
        //         timeType: this.timeType1,
        //         province: this.province1,
        //         city:this.city,
        //     }, {emulateJSON: true}).then(function (res) {
        //         this.returnTypePropData = res.data.data;
        //         if (res.data.data != null) {
        //             var s = res.data.data;
        //             for (var k in s) {
        //                 var newData = {name: "", value: ""};
        //                 if (k!=="评论" && k!=='突发' && k!=='身边事'){
        //                     newData.name = k;
        //                     newData.value = s[k];
        //                 }
        //                 this.typePropDate.push(newData);
        //                 this.type.push(k)
        //             }
        //         }
        //
        //     });
        // },
        // //点击获取排行详情
        // XQ: function (rank, index) {
        //     this.xqData = rank;
        //     this.activeClass = index;
        //     this.indexData = {index: "", name: "", title: "", time: ""};
        //     this.indexData.index = index;
        //     this.indexData.name = rank.labelNames;
        //     this.indexData.title = rank.incidentTitle;
        //     this.indexData.time = rank.createTime;
        //     this.addParame=rank;
        //     //热度趋势图点击改变参数操作
        //     this.getHotCharLine(rank);
        //     //排行榜敏感非敏感点击改变参数
        //     this.mg = rank.mgNumberDay;
        //     this.fmg = rank.fmgNumberDay;
        //     this.clickMG();
        //     //媒体发文来源
        //     this.xw = rank.mediaNumberDay;
        //     this.zw = rank.zwNumberDay;
        //     this.bk = rank.bkNumberDay;
        //     this.clickThree();
        // },



    },
});