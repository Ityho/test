


var myChart = [], echartsOpts = {

    pieEchart :{
        tooltip: {
            trigger: 'item',
            formatter: "{b} : {d}% <br/> {c}"
        },
        legend: {
            show: false,
        },
        series: [{
            type: 'pie',
            radius: ['40%', '50%'],
            center: ['50%', '50%'],
            color: ['#FE1973', '#1AC0FF'],
            data: [],
            labelLine: {
                normal: {
                    show: true,
                    length: 5,
                    length2: 20,
                    lineStyle: {
                        width: 1
                    }
                }

            },
            label: {
                normal: {
                    formatter: '{b}\n{d}%({c})',
                    textStyle: {
                        fontSize: 20
                    }
                }
            }
        }]
    },
    barEchart:{
        legend: {
            data: [],
            textStyle: {
                color: '#fff',
                fontSize: '10'
            },
            show: false
        },
        grid: {
            left: '2%',
            right: '3%',
            bottom: '10%',
            containLabel: true
        },
        xAxis: [{
            type: 'value',
            name: '',
            axisLabel: {
                color: '#CAEFFD'
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: 'rgba(170, 190, 196, .1)'
                }
            },
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: 'rgba(170, 190, 196, .1)'
                }
            }
        }],
        yAxis: [{
            type: 'category',
            axisLabel: {
                color: '#CAEFFD',
                fontSize: '16'
                // formatter: '{value}'
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: 'rgba(170, 190, 196, .1)'
                }
            },
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: 'rgba(170, 190, 196, .1)'
                }
            },
            data: []
        }],
        series: []
    },
    bigBar:{
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            show:false,
            type:'plain',
            data:[],
            textStyle:{
                color:'#fff',
                fontSize:16
            }
        },
        grid: {
            left: '5%',
            right: '10%',
            top: '10%',
            bottom: '8%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            axisLine: {
                show: false,
            },
            axisTick: { //坐标轴刻度
                show: false,
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: 'rgba(170, 190, 196, .1)'
                }
            },
            axisLabel: { //坐标轴刻度标签
                show: true,
                textStyle: {
                    color: 'rgba(202, 239, 253, 1)',
                    fontSize:14
                }
            },
            boundaryGap: false,
            data: []
        }],
        yAxis: [{
            type: 'value',
            axisLine: { //坐标轴轴线 默认 true,
                show: false
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: 'rgba(170, 190, 196, .1)'
                }
            },
            axisTick: { //坐标轴刻度
                show: false
            },
            axisLabel: { //坐标轴刻度标签
                show: true,
                textStyle: {
                    color: 'rgba(202, 239, 253, 1)',
                    fontSize:14
                }
            },
        }],

        series: [{
            name: '',
            type: 'line',
            smooth: true, //这句就是让曲线变平滑的
            symbol: 'circle', //拐点样式
            symbolSize: [10,10],
            data: [],
            markPoint: {
                data: [{
                    type: 'max',
                    name: '最大值',
                    symbol: 'roundRect',
                    symbolOffset: [0, '-130%'],
                    symbolSize: [0,30],
                    label: {
                        normal: {
                            show: true,
                            position: 'insideTop',
                            formatter: '历史最高: {c}',
                            textStyle: {
                                fontSize: '14',
                                color: '#fff',
                                backgroundColor:'rgba(254, 25, 115, .6)',
                                padding:10
                            }
                        }
                    }
                }, {
                    symbol: 'circle',
                    type: 'max',
                    symbolSize: [8, 8],
                    itemStyle: {
                        normal: {
                            color: "#FFFFFF",
                            borderColor: 'rgba(255,255,255,1)', //rgba(255, 199, 43, .3)
                            borderWidth: 10,
                            shadowColor: '#FFFFFF',
                            shadowBlur: 20
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
                    color: '#E83B7F'
                }
            },
            lineStyle: {
                normal: {
                    color: '#E83B7F',
                    width: 3,
                    type: 'solid'
                }
            },
            areaStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: 'rgba(254, 25, 115, 1)'
                    }, {
                        offset: 1,
                        color: 'rgba(255, 58, 135, 0.1)'
                    }], false),
                    shadowColor: 'rgba(0, 0, 0, 0.1)',
                    shadowBlur: 20
                }
            },
            markLine: {
                silent: true,
                data: [{
                    type: 'average',
                    name: '平均值',

                }],
                precision: 2,
                label: {
                    normal: {
                        position: "middle",
                        formatter: '热度均值: 0.5',
                        textStyle: {
                            fontSize: '14',
                            color: '#fff',
                            backgroundColor:'rgba(26, 192, 255, .6)',
                            padding:10
                        }

                    }
                },
                lineStyle: {
                    normal: {
                        color: 'rgba(26, 192, 255, 1)',
                        width: 2,
                    }
                }
            },
        }

        ]
    }
};
//刷新图表数据
refresh = function($id,opt){
    if(!opt.noDataText){
        opt.noDataText = "暂无数据";
    }
    opt.noDataLoadingechartsOpts = {
        text : opt.noDataText,
        effect : 'bubble',
        textStyle : {
            fontSize : 16
        }
    }
    myChart[$id.prop("id")].setOption(opt,true);
};
//设置图表数据
setEchartsOpion = function(opts){
    var type = opts.$id.data("type");

    myChart[opts.$id.prop("id")] = echarts.init(opts.$id[0],"macarons");
    if(opts.event){
        myChart[opts.$id.prop("id")].on(echarts.config.EVENT.CLICK,opts.event);
    }
    //深度拷贝
    refresh(opts.$id,$.extend(true,{},echartsOpts[type],opts.opt));

};





