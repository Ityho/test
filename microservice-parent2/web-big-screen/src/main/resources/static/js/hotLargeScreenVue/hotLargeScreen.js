var large = new Vue({
    el: '#hotLargeScreen',
    data: {
        hotAvgValue: 0,
        hotAvgDifValue: 0,
        areaType: "",//1,国内   2，国外
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
        // isLogin: $("#isLogin").val(),//判断是否登录用
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
        dt: false,//地图数据加载时水印控制
        userAdmin: $("#isDZ").val(),//判断登陆用户的用户名
        fxCount: $("#fx").val(),
        userT: $("#ut").val(),
        wb: $("#wx").val(),
        capital: 0,
        city: "",
        page: 1,
        pageSize: 100,
        xs: 0,
        pageActive: 1,
        controlPage: 1,
        area1: $("#area1").val(),
        area2: $("#area2").val(),
        area3: $("#area3").val(),
        countTime: $("#countTime").val(),
        countBranches: $("#countBranches").val(),
        labelIds: $("#labelIds").val(),
        GG: "国际",
        DZUser: 0,
        BS: 0,//标识是否点击的省份，来判断是否剔除文娱小于30热度的
        // isLastHotDay:0,//0不是，1是
        oneOrTwo: 0,//0，排行榜显示一小时，1排行榜显示24小时
        addParame: [],
        dd: 1,//控制排行榜详情水球图所展示的数据1：一小时的，24：24小时的
    },
    created: function () {
        this.rankListData();
        this.hot();
        this.classList();
    },
    mounted: function () {
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
        //                 large.bar4_1(0);
        //                 large.bar4_2(0);
        //             }, 500)
        //         } else if (index == 2) {
        //             setTimeout(function () {
        //                 large.bar4_1_1(0);
        //                 large.bar4_2_1(0);
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
    },
    methods: {
        //获取排行列表
        rankListData: function () {
            //发送 post 请求
            this.$http.post('/api/hot/largeScreen/rankListData', {
                sort: this.sort,
                labels: this.labels1,
                province: this.province2,
                areaType: this.areaType,
                page: this.page,
                pageSize: this.pageSize,
                city: this.city,
            }, {emulateJSON: true}).then(function (res) {
                large.rankData = res.data.list;
                //全部里剔除热度小于30的娱乐事件
                if (large.rankData!==null){
                    if(this.labels1===""&&this.areaType!==2){
                        var na=res.data.list;
                        var list = [];
                        var resu = [];
                        for (var i=0;i<na.length;i++){
                            if (na[i].labelNames!==null && na[i].labelNames!=="" && na[i].labelNames!==undefined){
                                if (na[i].labelNames.split(",").indexOf('明星')>-1 || na[i].labelNames.split(",").indexOf('电影')>-1
                                    || na[i].labelNames.split(",").indexOf('电视剧')>-1
                                    || na[i].labelNames.split(",").indexOf('综艺')>-1
                                    || na[i].labelNames.split(",").indexOf('文化')>-1){
                                    if(na[i].ratioHotDay>30){
                                        list.push(na[i]);
                                    }
                                }else {
                                    list.push(na[i])
                                }
                            }
                        }
                        if(list.length>=20){
                            for(var g=0;g<20;g++){
                                resu.push(list[g]);
                            }
                            large.rankData = resu;
                        }else {
                            large.rankData = list;
                        }


                    }else {
                        var na=res.data.list;
                        var list = [];
                        for (var i=0;i<na.length;i++){
                            if (na[i].labelNames!==null && na[i].labelNames!=="" && na[i].labelNames!==undefined){
                                list.push(na[i])
                            }
                        }
                        this.rankData =list;
                    }
                }
            });
        },
        //获取地图右侧均值等数据
        hot: function () {
            var params = {
                labels: this.labels1,
                province: this.province1,
                city: this.city
            };
            //发送 post 请求
            this.$http.post('/api/hot/largeScreen/hotAverageValue', params, {emulateJSON: true}).then(function (res) {
                large.hotEventData = res.data.data;
                if (large.hotEventData !== null) {
                    large.hotAvgValue = parseFloat(large.hotEventData.hotAvg).toFixed(2);
                    large.hotAvgDifValue = parseFloat(large.hotEventData.hotDifferenceAvg).toFixed(2)
                }
            });
        },
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
            $.post('/api/hot/largeScreen/labels', {}, function (result) {
                large.hotClassData = result.data.childrens;
                var gj = {id: 1127, name: "国际"};
                large.newHotClassData = [
                    large.hotClassData[9],
                    large.hotClassData[4],
                    gj,
                    large.hotClassData[8],
                    large.hotClassData[2],
                    large.hotClassData[5],
                    large.hotClassData[7],
                    large.hotClassData[3],
                    large.hotClassData[6],
                    large.hotClassData[0],
                    large.hotClassData[1]

                ];
            })
        }
    }
});