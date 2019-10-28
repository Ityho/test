let hp = new Vue({
    el: "#activeTab3",
    data: {
        hotPeopleClassList:[],//人物类型标签
        labActive:-1,//热点标签点击选中效果控制
        myData:['02-21 00:11', '02-21 00:11', '02-21 00:11', '02-21 00:11', '02-21 00:11', '02-21 00:11', '02-21 00:11', '02-21 00:11'],//敏感信息图中时间数据
        labels:"",//TODO 默认先传""，了解参数后再定
        time:1,//TODO 默认先传1，了解参数后再定（1：表示24小时，2：表示72小时）
        peopleRankData:[],//热门人物数据
        xx:0,
        oo:0,
        lineTitle:[],
        lineName:"",
        lineEventData:[],
        lineEventTime:[],
    },
    created:function () {
        this.peopleRank();
        this.hotPeopleClass();
    },
    mounted:function () {
        this.itemLength();
        //点击更多效果js
        $('.btn-more').on('click', function () {
            $(this).find('i').toggleClass('rotate180');
            $(this).parents('.group-item').find('.open').toggle()
        });
        // 导航部分下拉菜单
        $('.w-headbar-list li.j_hover').on('mouseenter', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $(this).find('.w-headbar-dropdown').stop().show(300);
        });
        $('.w-headbar-nav .j_hover').on('mouseleave', function () {
            $('.w-headbar-dropdown').stop().hide(300);
            $(this).removeClass('active');
        });
    },
    filters: {
        //处理时间戳
        formatDate: function (date, formatStr) {
            var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
            return moment(date).format(formatStr);

        },
    },
    methods:{
        itemLength: function(){
            var index = $('.w-person .wb-p-card .wb-p-item').length;
            var heightBox = index *114+28;
            document.getElementById('barEchart').style.height = heightBox+'px';
        },
        //查看详情
        ListOpen: function(me,dc) {
            var $li = $(me.target).parents(".wb-p-item").siblings(".wb-p-item");
            $li.find(".cardHead").removeClass('active');
            $li.find(".wb-sub-card").hide();
            $(me.target).toggleClass("active");
            $(me.target).nextAll(".wb-sub-card").toggle();
            this.oo=0;
            this.lineTitle=[dc.name,dc.hotIncidentList[0].incidentTitle];
            this.peopleName=dc.name;
            this.lineName=dc.hotIncidentList[0].incidentTitle;
            let eve=dc.hotIncidentList[0].lineData;
            let en=JSON.parse(eve);
            for (var i=0;i<en.length-1;i++){
                this.lineEventData[i]=en[i].total;
                this.lineEventTime[i]=en[i].name;
            }
            hp.peopleLine();
        },
        //人物类型标签
        hotPeopleClass:function () {
            $.post(actionBase+'/hot/people/getHotPeopleClassList',{},function(result){
                // this.$http.post(actionBase + '/view/home/hotPeople/getHotPeopleClassList.action', {}, {emulateJSON: true}).then(function (res) {
                this.hotPeopleClassList=result.data.list;
            });
        },
        //热门人物排行
        peopleRank:function () {
            this.peopleRankData=[];
            var  searchObj={labels: this.labels,time: this.time,};
            $.post(actionBase+'/hot/people/getHotPeopleRank',{searchObj},function(result){
                // this.$http.post(actionBase + '/view/home/hotPeople/getHotPeopleRank.action', {
                //     labels: this.labels,
                //     time: this.time,
                // }, {emulateJSON: true}).then(function (res) {
                if (result.data!==null){
                    this.peopleRankData=res.data.list;
                    this.lineTitle=[this.peopleRankData[0].name,this.peopleRankData[0].hotIncidentList[0].incidentTitle];
                    this.peopleName=this.peopleRankData[0].name;
                    this.lineName=this.peopleRankData[0].hotIncidentList[0].incidentTitle;
                    let eve=this.peopleRankData[0].hotIncidentList[0].lineData;
                    let en=JSON.parse(eve);
                    for (var i=0;i<en.length-1;i++){
                        this.lineEventData[i]=en[i].total;
                        this.lineEventTime[i]=en[i].name;
                    }
                    hp.peopleLine();
                    hp.hotPeopleMG();
                }else {
                    alert("暂无数据")
                }

            });
        },
        //切换人物标签
        cutLable:function (labData, index) {
            this.labActive=index;
        },
        //点击人物标签中的全部
        QBLab:function () {
            this.labActive=-1;
        },
        //人物VS事件 热度指数变化趋势折线图
        peopleLine:function () {
            var informationChart= echarts.init(document.getElementById('inforChart'));
            var option = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    show: true,
                    data: hp.lineTitle,
                    textStyle: {
                        color: '#fff',
                    },
                },
                grid: {
                    left: '3%',
                    right: '5%',
                    top: '80',
                    bottom: '2%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'category',
                    axisLine: { //坐标轴轴线 默认 true,
                        show: false
                    },
                    axisTick: { //坐标轴刻度
                        show: false,
                        lineStyle: {
                            color: '#9AA1BD',
                            width: 1,
                            type: 'solid'
                        }
                    },
                    axisLabel: { //坐标轴刻度标签
                        show: true,
                        //rotate: 30,  //旋转角度
                        textStyle: {
                            color: '#9AA1BD'
                        }
                    },
                    boundaryGap: false,
                    data:hp.lineEventTime
                }],
                yAxis: [{
                    type: 'value',
                    axisLine: { //坐标轴轴线 默认 true,
                        show: false
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: '#1C2654',
                            width: 1,
                            type: 'solid'
                        }
                    },
                    axisTick: { //坐标轴刻度
                        show: false
                    },
                    axisLabel: { //坐标轴刻度标签
                        show: true,
                        textStyle: {
                            color: '#9AA1BD'
                        }
                    },
                }],

                series: [{
                    name: hp.peopleName,
                    type: 'line',
                    symbol: 'circle', //拐点样式
                    data: [1, 0.12, 0.22, 0.13, 0.15, 0.17, 0.24, 0.65, 0.94, 0.11,1.2,1.3,2.1,3.2, 0.13, 0.15, 0.17, 0.24, 0.65, 0.94, 0.15, 0.17, 0.24],
                    markPoint: {
                        data: [{
                            type: 'max',
                            name: '最大值',
                            symbol: 'rect',
                            symbolOffset: [0, '-100%'],
                            symbolSize: [60, 22],
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideTop',
                                    formatter: '{c}',
                                    textStyle: {
                                        fontSize: '12',
                                        fontWeight: 'bold',
                                        color: '#fff',
                                    }
                                }
                            }
                        }, {
                            symbol: 'circle',
                            type: 'max',
                            symbolSize: [10, 10],
                            itemStyle: {
                                normal: {
                                    color: "#F18D00",
                                    borderColor: 'rgba(241, 141, 0, 0.3)', //rgba(255, 199, 43, .3)
                                    borderWidth: 10,
                                    shadowColor: '#ffc72b',
                                    shadowBlur: 30
                                }
                            },
                            label: {
                                normal: {
                                    show: false
                                }
                            }
                        }]
                    },

                    itemStyle: {
                        normal: {
                            color: '#F18D00'
                        }
                    },
                    lineStyle: {
                        normal: {
                            color: '#F18D00',
                            width: 1.5,
                            type: 'solid'
                        }
                    },
                    smooth: false,
                },
                    {
                        name: hp.lineName,
                        type: 'line',
                        symbol: 'circle', //拐点样式
                        data:hp.lineEventData,
                        markPoint: {
                            data: [{
                                type: 'max',
                                name: '最大值',
                                symbol: 'rect',
                                symbolOffset: [0, '-100%'],
                                symbolSize: [60, 22],
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'insideTop',
                                        formatter: '{c}',
                                        textStyle: {
                                            fontSize: '12',
                                            fontWeight: 'bold',
                                            color: '#fff',
                                        }
                                    }
                                }
                            }, {
                                symbol: 'circle',
                                type: 'max',
                                symbolSize: [10, 10],
                                itemStyle: {
                                    normal: {
                                        color: "#0777EF",
                                        borderColor: 'rgba(7, 119, 239, 0.3)', //rgba(255, 199, 43, .3)
                                        borderWidth: 10,
                                        shadowColor: '#0777EF',
                                        shadowBlur: 30
                                    }
                                },
                                label: {
                                    normal: {
                                        show: false
                                    }
                                }
                            }]
                        },

                        itemStyle: {
                            normal: {
                                color: '#0777EF'
                            }
                        },
                        lineStyle: {
                            normal: {
                                color: '#0777EF',
                                width: 1.5,
                                type: 'solid'
                            }
                        },
                        smooth: false,

                    },
                ]
            };
            informationChart.setOption(option);
        },
        //人物VS事件 信息敏感量 图表
        hotPeopleMG:function () {
            var barEchartend = echarts.init(document.getElementById('barEchartend'));
            var myData = this.myData;
            var option = {
                baseOption: {
                    legend: {
                        data: ['王思聪', '熊猫直播破产'],
                        top: 4,
                        right: 'center',
                        textStyle: {
                            color: '#fff',
                        },
                    },
                    tooltip: {
                        show: true,
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow',
                        }
                    },
                    grid: [{
                        left: '8%',
                        top: 40,
                        bottom: 10,
                        containLabel: true,
                        width: '38%',
                        show: true,
                        borderColor: "transparent",
                    }, {
                        show: false,
                        left: '50.5%',
                        top: 65,
                        bottom: 15,
                        width: '0%',
                    }, {
                        right: '8%',
                        top: 40,
                        bottom: 10,
                        containLabel: true,
                        width: '38%',
                        show: true,
                        borderColor: "transparent",
                    }, ],

                    xAxis: [{
                        type: 'value',
                        inverse: true,
                        name: '敏感信息量',
                        axisLine: {
                            show: false,
                            textStyle: {
                                color: '#9AA1BD',
                            },
                            lineStyle: {
                                color: '#ffffff',

                            }
                        },
                        axisTick: {
                            show: false,
                        },
                        position: 'top',
                        axisLabel: {
                            show: true,
                            formatter: '{value}',
                            textStyle: {
                                color: '#B2B2B2',
                                fontSize: 12,
                            },
                        },
                        splitLine: {
                            show: false,
                        },
                    }, {
                        gridIndex: 1,
                        show: false,
                    }, {
                        gridIndex: 2,
                        type: 'value',
                        name: '非敏感信息量',
                        axisLine: {
                            show: false,
                            textStyle: {
                                color: '#9AA1BD',
                            },
                            lineStyle: {
                                color: '#ffffff',

                            }
                        },
                        axisTick: {
                            show: false,
                        },
                        position: 'top',
                        axisLabel: {
                            show: true,
                            formatter: '{value}',
                            textStyle: {
                                color: '#B2B2B2',
                                fontSize: 12,
                            },
                        },
                        splitLine: {
                            show: false,
                        },
                    }, ],
                    yAxis: [{
                        type: 'category',
                        inverse: true,
                        position: 'right',
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: '#1C2654'
                            }
                        },
                        axisLabel: {
                            show: false,
                            margin: 8,
                            textStyle: {
                                color: '#9D9EA0',
                                fontSize: 12,
                            },

                        },
                        data: myData,
                    }, {
                        gridIndex: 1,
                        type: 'category',
                        inverse: true,
                        position: 'left',
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#9D9EA0',
                                fontSize: 12,
                            },

                        },
                        data: myData.map(function(value) {
                            return {
                                value: value,
                                textStyle: {
                                    align: 'center',
                                }
                            }
                        }),
                    }, {
                        gridIndex: 2,
                        type: 'category',
                        inverse: true,
                        position: 'left',
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: '#1C2654'
                            }
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            show: false,
                            textStyle: {
                                color: '#9D9EA0',
                                fontSize: 12,
                            },

                        },
                        data: myData,
                    }, ],
                    series: [{
                        name: '王思聪',
                        type: 'bar',
                        barGap: 1,
                        barWidth: 15,
                        label: {
                            normal: {
                                show: true,
                                position: 'left',
                                formatter: '{c}条',
                                color: '#F18D00'
                            },
                        },
                        itemStyle: {
                            normal: {
                                color: {
                                    type: 'linear',
                                    x: 0,
                                    y: 0,
                                    x2: 1,
                                    y2: 1,
                                    colorStops: [{
                                        offset: 0,
                                        color: 'rgba(255, 188, 92, 1)' // 0% 处的颜色
                                    }, {
                                        offset: 1,
                                        color: 'rgba(241, 141, 0, 1)' // 100% 处的颜色
                                    }],
                                    global: false // 缺省为 false
                                }
                            }
                        },
                        data: [389, 259, 262, 324, 232, 176, 196,100],
                    },
                        {
                            name: '熊猫直播破产',
                            type: 'bar',
                            // stack: 'one',
                            barGap: 1,
                            barWidth: 15,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'left',
                                    formatter: '{c}条',
                                    color: '#0777EF'
                                },
                            },
                            itemStyle: {
                                normal: {
                                    color: {
                                        type: 'linear',
                                        x: 0,
                                        y: 0,
                                        x2: 1,
                                        y2: 1,
                                        colorStops: [{
                                            offset: 0,
                                            color: 'rgba(26, 153, 238, 1)' // 0% 处的颜色
                                        }, {
                                            offset: 1,
                                            color: 'rgba(0, 104, 211, 1)' // 100% 处的颜色
                                        }],
                                        global: false // 缺省为 false
                                    }
                                }
                            },
                            data: [380, 250, 260, 320, 230, 176, 196,100],
                        },
                        {
                            name: '王思聪',
                            type: 'bar',
                            barGap: 1,
                            barWidth: 15,
                            xAxisIndex: 2,
                            yAxisIndex: 2,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'right',
                                    formatter: '{c}条',
                                    color: '#F18D00'
                                },
                            },
                            itemStyle: {
                                normal: {
                                    color: {
                                        type: 'linear',
                                        x: 0,
                                        y: 0,
                                        x2: 1,
                                        y2: 1,
                                        colorStops: [{
                                            offset: 0,
                                            color: 'rgba(255, 188, 92, 1)' // 0% 处的颜色
                                        }, {
                                            offset: 1,
                                            color: 'rgba(241, 141, 0, 1)' // 100% 处的颜色
                                        }],
                                        global: false // 缺省为 false
                                    }
                                }
                            },
                            data: [389, 259, 262, 324, 232, 176, 200,100],
                        },

                        {
                            name: '熊猫直播破产',
                            type: 'bar',
                            barGap: 1,
                            barWidth: 15,
                            xAxisIndex: 2,
                            yAxisIndex: 2,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'right',
                                    formatter: '{c}条',
                                    color: '#0777EF'
                                },
                            },
                            itemStyle: {
                                normal: {
                                    color: {
                                        type: 'linear',
                                        x: 0,
                                        y: 0,
                                        x2: 1,
                                        y2: 1,
                                        colorStops: [{
                                            offset: 0,
                                            color: 'rgba(26, 153, 238, 1)' // 0% 处的颜色
                                        }, {
                                            offset: 1,
                                            color: 'rgba(0, 104, 211, 1)' // 100% 处的颜色
                                        }],
                                        global: false // 缺省为 false
                                    }
                                }
                            },
                            data: [389, 259, 262, 324, 232, 176, 196,100],
                        }
                    ]

                },
            };
            barEchartend.setOption(option);
        },
        //人物整体热度排行榜 对比图
        hotPeopleRank:function () {
            var barEchart = echarts.init(document.getElementById('barEchart'));
            var xAxisData=['王思聪', '王思聪','王思聪', '王思聪','王思聪', '王思聪','王思聪', '王思聪',];
            var option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter:function(param){
                        return param[0].name+'<br/>'+param[0].seriesName+':'+param[0].value+
                            '<br/>'+param[2].seriesName+':'+param[2].value
                    }
                },
                title:{
                    "text": "人物整体热度变化趋势对比",
                    "subtext": '',
                    x: "0%",
                    textStyle: {
                        color: '#fff',
                        fontSize: '16',
                        fontWeight:500
                    },
                    subtextStyle: {
                        color: '#90979c',
                        fontSize: '16',

                    },
                },
                legend: {
                    selectedMode:false,
                    itemWidth:30,
                    itemHeight:8,
                    x:'right',
                    data: [{
                        name:'最新热度值',
                        icon: 'image://css/indexV4/base/images/wicon1.png',
                        textStyle:{
                            color:'#ffffff',
                            padding:[0,10]
                        }

                    }, {
                        name:'昨日热度值',
                        icon: 'image://css/indexV4/base/images/wicon2.png',
                        textStyle:{
                            color:'#ffffff',
                            padding:[0,10]
                        }
                    }]
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '1%',
                    top:'40',
                    containLabel: true
                },
                xAxis: [{
                    type: 'value',
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: '#1C2654',
                        }
                    },
                    splitLine: {
                        show: false,
                    },
                    axisLabel: {
                        inside: false,
                        textStyle: {
                            color: '#bac0c0',
                            fontWeight: 'normal',
                            fontSize: '12',
                        },
                    },
                    position:"top",
                }],
                yAxis: [{
                    type: 'category',
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: '#1C2654',
                        }
                    },
                    splitLine: {
                        show: false,
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#bac0c0',
                            fontWeight: 'normal',
                            fontSize: '12',
                        },
                        formatter: function(a) {
                            for (let i in a)
                                return a.length > 6 ? a.substring(0, 5) + "..." : a
                        }
                    },
                    data: xAxisData
                }, {
                    type: 'category',
                    axisLine: {
                        show: false,
                        lineStyle: {
                            color: '#1C2654',
                        }
                    },
                    splitLine: {
                        show: false,
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    },
                    splitArea: {
                        show: false
                    },
                    data: xAxisData
                }],
                series: [
                    {
                        name: '最新热度值',
                        type: 'bar',
                        barGap: 10,
                        barWidth: 2,
                        stack: 1,
                        yAxisIndex:0,
                        itemStyle: {
                            normal: {
                                color: {
                                    type: 'linear',
                                    x: 0,
                                    y: 0,
                                    x2: 1,
                                    y2: 1,
                                    colorStops: [{
                                        offset: 0,
                                        color: 'rgba(255, 188, 92, 1)' // 0% 处的颜色
                                    }, {
                                        offset: 1,
                                        color: 'rgba(241, 141, 0, 1)' // 100% 处的颜色
                                    }],
                                    global: false // 缺省为 false
                                }
                            }
                        },
                        label:{
                            normal:{
                                show:true,
                                position:'insideTopRight',
                                // distance:'5',
                                offset:[42,-32],
                                color:"#FF8800",
                                textStyle:{
                                    fontSize:16,
                                    fontWeight:600

                                },
                                formatter:function(param){
                                    return param.value + '{img1|}' //上升
                                    // return param.value + '{img2|}' //下降

                                },
                                rich :{
                                    img1:{
                                        backgroundColor : {
                                            image : 'css/indexV4/base/images/up.png'
                                        },
                                        height: 20

                                    },
                                    img2:{
                                        backgroundColor : {
                                            image : 'css/indexV4/base/images/down.png'
                                        },
                                        height: 20

                                    }
                                }
                            }
                        },
                        data: [18203, 23489, 29034,30000,18203, 23489, 29034,30000]

                    },
                    {
                        name: '最新热度值',
                        type: 'scatter',
                        stack: '1',
                        xAxisIndex: 0,
                        symbolOffset: [0, -13], //相对于原本位置的偏移量
                        data: [0, 0,0, 0,0, 0,0, 0],
                        yAxisIndex: 0,
                        symbolSize: 14,
                        itemStyle: {
                            normal: {
                                color: 'rgba(241, 141, 0, 1)',
                                opacity: 1,
                            }
                        },
                        z: 2
                    },
                    {
                        name: '昨日热度值',
                        type: 'bar',
                        barMinHeight:0,
                        stack: 2,
                        barWidth: 1,
                        itemStyle:{
                            normal:{
                                borderType :'dotted',
                                borderColor:"#0777EF",
                                borderWidth:1,
                                color: 'rgba(241, 141, 0, 0)',
                            }
                        },
                        label:{
                            normal:{
                                show:true,
                                position:'right',
                                offset:[10,0],
                                color:"#0777EF",
                                textStyle:{
                                    fontSize:12,
                                    fontWeight:500

                                }
                            }
                        },
                        data: [19325, 23438, 31000,20000,19325, 23438, 31000,20000]
                    },
                    {
                        name: '昨日热度值',
                        type: 'scatter',
                        stack: '2',
                        xAxisIndex: 0,
                        symbolOffset: [0, 12], //相对于原本位置的偏移量
                        data: [0, 0,0, 0,0, 0,0, 0,],
                        yAxisIndex: 1,
                        symbolSize: 14,
                        itemStyle: {
                            normal: {
                                color: '#0777EF',
                                opacity: 1,
                            }
                        },
                        z: 1
                    },

                ]
            };
            barEchart.setOption(option);
        },
        //解决tab切换时图表初始化
        tab:function () {
            setTimeout(function () {
                hp.hotPeopleRank()
            },800)

        },
        //点击事件
        eventClick:function (dt,pt,ind) {
            this.oo=ind;
            this.lineTitle=[pt.name,dt.incidentTitle];
            this.peopleName=pt.name;
            this.lineName=dt.incidentTitle;
            let eve=dt.lineData;
            let en=JSON.parse(eve);
            for (var i=0;i<en.length-1;i++){
                this.lineEventData[i]=en[i].total;
                this.lineEventTime[i]=en[i].name;
            }
            hp.peopleLine();
        }
    }

});