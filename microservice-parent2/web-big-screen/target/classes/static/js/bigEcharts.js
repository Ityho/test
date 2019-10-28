var pieEchart1 = echarts.init(document.getElementById('pieEchart1'));
var option = {
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
        data: [{
                value: 335,
                name: '敏感',

            },
            {
                value: 310,
                name: '非敏感'
            }
        ],
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
};
pieEchart1.setOption(option);
var barEchart1 = echarts.init(document.getElementById('barEchart1'));
var option = {
        legend: {
            data: ['新闻', '政务', '报刊'],
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
            data: ['报刊', '政务', '新闻']
        }],
        series: [{
                name: '报刊',
                type: 'bar',
                stack: '订单',
                data: [4100, 0, 0],
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
                },
            },
            {
                name: '政务',
                type: 'bar',
                stack: '订单',
                data: [0, 3600, 0],
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
                },
            },
            {
                name: '新闻',
                type: 'bar',
                stack: '订单',
                data: [0, 0, 500],
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
                },
            }
        ]
    };
barEchart1.setOption(option);
var bigBar =echarts.init(document.getElementById('bigBar'));
// 指定图表的配置项和数据
var option = {
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        show:false,
        type:'plain',
        data:['陈伟霆'],
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
        data: ['2018 01-02', '2018 01-02', '2018 01-02', '2018 01-02', '2018 01-02', '2018 01-02', '2018 01-02', '2018 01-02', '2018 01-02', '2018 01-03']
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
            name: '陈伟霆',
            type: 'line',
            smooth: true, //这句就是让曲线变平滑的
            symbol: 'circle', //拐点样式
            symbolSize: [10,10],
            data: [99.0, 85, 75, 65, 55, 65, 55, 50, 99, 99.99],
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
};
// 使用刚指定的配置项和数据显示图表。
bigBar.setOption(option)
setTimeout(function() {
    window.onresize = function() {
        pieEchart1.resize();
        barEchart1.resize();
        bigBar.resize();

    }
});
