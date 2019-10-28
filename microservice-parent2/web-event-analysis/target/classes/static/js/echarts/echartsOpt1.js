function createRandomItemStyle(opa) {
    return {
        normal : {
            color : 'rgba(' + [ 1, 161, 248, opa ].join(',') + ')'
        }
    };
}

//钓鱼岛其他岛屿start
var geoCoordMap = {
    '钓鱼岛': [123.0254, 25.1986],
    '赤尾屿': [125.0054, 26.1986]
};
var mapFeatures = echarts.getMap('china').geoJson.features;
mapFeatures.forEach(function(v) {
    var name = v.properties.name;
    geoCoordMap[name] = v.properties.cp;
});
var convertData = function(data) {
    var res = [];
    for(var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if(geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    return res;
};
var dataT = [{
    name: '钓鱼岛',
    value: 5
}, {
    name: "赤尾屿",
    value: 14
}, ];

var myChart = [], echartsOpts = {
        map : {
            legend:{show:false},
            animation : false,
            title : {
                text : '',
                subtext : '',
                x : 'center',
                y : 'top',
            },
            tooltip : {
                trigger : 'item',
                enterable : true,
                textStyle : {
                    fontSize : 12
                }
            },
            dataRange : {
                min : 0,
                max : 10000,
                calculable : false,
                text : [ '高', '低' ],
                color : [ '#d44e24', '#f29300', '#f3d647' ]
            },
            series : [ {
                name : '数量',
                type : 'map',
                mapType : 'china',
                selectedMode : 'single',// single|multiple
                roam : false,
                itemStyle : {
                    normal : {
                        label : {
                            show : true
                        },
                        borderColor : '#FFF'
                    },
                    emphasis : {
                        label : {
                            show : true
                        }
                    }
                },
                data : []
            } ]
        },
        map1 : {
            legend:{show:false},
            series : [ {
                name : 'Map',
                type : 'map',
                mapLocation : {
                    x : 'center',
                    y : 'center'
                },
                itemStyle : {
                    normal : {
                        borderWidth : 2,
                        borderColor : '#d5d5d5',
                        color : '#ccc',
                        label : {
                            show : false
                        }
                    },
                    emphasis : { // 也是选中样式
                        borderWidth : 2,
                        borderColor : '#d5d5d5',
                        color : '#92D0FD',
                        label : {
                            show : true,
                            textStyle : {
                                color : '#fff'
                            }
                        }
                    }
                },
                data : [ {
                    name : '江西',
                    value : Math.round(Math.random() * 1000),
                    itemStyle : {
                        normal : {
                            color : '#92D0FD',
                            label : {
                                show : true,
                                textStyle : {
                                    color : '#fff',
                                    fontSize : 15
                                }
                            }
                        },
                        emphasis : { // 也是选中样式
                            borderWidth : 1,
                            borderColor : '#d5d5d5',
                            color : '#92D0FD',
                            label : {
                                show : false,
                                textStyle : {
                                    color : '#92D0FD'
                                }
                            }
                        }
                    }
                } ]
            } ]
        },
        map2 : {
            legend:{show:false},
            tooltip : {
                trigger : 'item'
            },
            visualMap : {
                show : true,
                type : 'piecewise',
                orient : 'vertical',
                x : 'left',
                y : '10',
                inverse : true,
                pieces : []
            },
            geo: {
                map: 'china',
                zoom : 1.2,
                itemStyle: {
                    normal: {
                        // borderWidth: 2,
                        // shadowBlur: 50,
                        // shadowColor: 'rgba(0, 0, 0, 0.2)'
                    }
                }
            },
            series : [ {
                name : '',
                type : 'map',
                mapType : 'china',
                showLegendSymbol : false,
                roam : false,
                zoom : 1.2,
                itemStyle : {
                    normal : {
                        label : {
                            show : false
                        },
                        borderWidth : 1,
                        borderColor : '#FFFFFF'
                    },
                    emphasis : {
                        label : {
                            show : true
                        }
                    }
                },
                data : [{
                    name : '南海诸岛',
                    "value" : 0,
                    itemStyle : {
                        normal : {
                            borderColor : '#959595',
                            areaColor : '#efefef',
                        }
                    }
                }],
                tooltip : {
                    textStyle : {
                        align : 'left'
                    }
                }
            },{
                name: '钓鱼岛',
                type: 'scatter',
                coordinateSystem: 'geo',
                data: convertData(dataT),
                symbolSize: function(val) {
                    return val[2] / 10000;
                },
                label: {
                    normal: {
                        formatter: '{b}',
                        position: 'top',
                        show: true,
                        textStyle: {
                            color: "#333333",
                            fontSize: 10
                        }
                    },

                },

            } ]
        },
        emotionFans:{

            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            /*toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },*/
            grid: {
                left: '0',
                right: '10%',
                bottom: '10%',
                containLabel: true
            },
            xAxis: [{
                type: 'category',
                data: [],
                'axisLine': {
                    show: true,
                    lineStyle:{
                        color:"#6f7786"
                    }
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    interval: 0,
                    rotate: 30,
                    show: true,
                    splitNumber: 15,
                    textStyle: {
                        fontFamily: "微软雅黑",
                        fontSize: 12
                    }
                },
                splitLine: {
                    show:true,
                    lineStyle:{
                        width:24,
                        color:'#EEEEEE'
                    }
                },
                boundaryGap: false
            }],
            yAxis: [{
                type: 'value',
                show: false
            }],
            series: [{
                name: '非敏感',
                type: 'bar',
                stack: '粉丝分布',
                barWidth: 24,
                data: [],
                label: {
                    normal: {
                        show: false
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#0E7DC0'
                    }
                }
            }, {
                name: '敏感',
                type: 'bar',
                stack: '粉丝分布',
                barWidth: 24,
                data: [],
                label: {
                    normal: {
                        show: false
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#D9684B'
                    }
                }
            }, ]
        }
        ,
        // 微博情绪
        // 情绪占比饼
        emotionProportion : {
            tooltip : {
                trigger : 'item',
                formatter : "{a} <br/>{b} : {c} ({d}%)"
            },
            legend : {
                top : '0',
                left : 'center',
                bottom : '20',
                itemGap : 10,
                itemWidth : 10,
                itemHeight : 10,
                borderRadius : 0,
                padding : 10,
                data : [ {
                    name : '喜悦',
                    textStyle : {
                        color : '#F18D00',
                        fontSize : 14,

                    }
                }, {
                    name : '愤怒',
                    textStyle : {
                        color : '#CF421E',
                        fontSize : 14,
                        padding : 4,
                    }
                }, {
                    name : '悲伤',
                    textStyle : {
                        color : '#0C7DC0',
                        fontSize : 14,
                        padding : 4,
                    }
                }, {
                    name : '惊奇',
                    textStyle : {
                        color : '#45B485',
                        fontSize : 14,
                        padding : 4,
                    }
                }, {
                    name : '恐惧',
                    textStyle : {
                        color : '#2F3237',
                        fontSize : 14,
                        padding : 4,
                    }
                }, {
                    name : '中性',
                    textStyle : {
                        color : '#9DA7B5',
                        fontSize : 14,
                        padding : 4,
                    }
                }, ]
            },

            series : [ {
                name : '情绪构成',
                type : 'pie',
                radius : [ '30%', '55%' ],
                center : [ '50%', '65%' ],
                data : [],
                label : {
                    normal : {
                        textStyle : {
                            fontSize : 12
                        },
                        formatter : function(param) {
                            return param.name + ':\n' + param.percent + '%';
                        }
                    }
                },

            } ]
        },
        emotionProportion2 : {
            tooltip : {
                trigger : 'axis',
                axisPointer : { // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            toolbox : {
                orient : 'vertical',
            },
            grid : {
                left : '10%',
                right : '8%',
                bottom : '10%',
                containLabel : true
            },

            xAxis : [ {
                name : '',
                nameTextStyle : {
                    color : '#0086ce'
                },
                type : 'value',
                axisLine : { // 坐标轴轴线 默认 true,
                    show : false,
                    lineStyle : {
                        color : '#dbdbdb',
                        width : 1,
                        type : 'solid'
                    }
                },
                axisTick : { // 坐标轴刻度
                    show : false,
                    lineStyle : {
                        color : '#888',
                        width : 1,
                        type : 'solid'
                    }
                },
                splitLine : {
                    show : false
                },
                axisLabel : { // 坐标轴刻度标签
                    show : false,
                    textStyle : {
                        color : '#888'
                    }
                }
            } ],
            yAxis : [ {
                type : 'category',
                axisLine : { // 坐标轴轴线 默认 true,
                    show : true,
                    lineStyle : {
                        color : '#dbdbdb',
                        width : 1,
                        type : 'solid'
                    }
                },
                axisTick : { // 坐标轴刻度
                    show : false,
                    lineStyle : {
                        color : '#888',
                        width : 1,
                        type : 'solid'
                    }
                },
                axisLabel : { // 坐标轴刻度标签
                    show : true,
                    textStyle : {
                        color : '#888'
                    }
                },

                boundaryGap : false,
                data : []
            } ],
            series : [ {
                name : '情绪构成',
                type : 'bar',
                barWidth : '40%',
                itemStyle : {
                    normal : {
                        color : '#3fad7e'
                    }
                },
                data : []
            } ]
        },
        emotionType_kb:{
            legend: {
                type: 'plain',
                show: false,
                data: ['非敏感', '敏感'],
                orient: 'horizontal',
                textStyle: {
                    color: '#000000',
                    align: 'left',
                    fontSize: 16
                },
                itemGap: 550,
                itemWidth: 5,
                itemHeight: 15
            },
            grid: [{
                show: false,
                left: '13%',
                top: '5%',
                bottom: 20,
                containLabel: false,
                width: '29%'
            }, {
                show: false,
                left: '51.5%',
                top: '6%',
                bottom: 30,
                width: '10%',
            }, {
                show: false,
                right: '14%',
                top: '5%',
                bottom: 20,
                containLabel: false,
                width: '29%'
            }, ],
            xAxis: [{
                gridIndex: 0,
                type: 'value',
                position: 'bottom',
                inverse: true,
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    show: false,
                },
                splitLine: {
                    show: false,
                },
            }, {
                gridIndex: 1,
                type: 'value',
                show: false,
            }, {
                gridIndex: 2,
                type: 'value',
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },

                axisLabel: {
                    show: false,
                },
                splitLine: {
                    show: false,
                },
            }],
            yAxis: [{
                type: 'category',
                inverse: true,
                position: 'right',
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: false,
                },
                data: [],

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
                        align: 'center',
                        color: '#888888',
                        fontSize: 12,
                    }

                },
                data: []

            }, {
                gridIndex: 2,
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
                    show: false,
                },
                data: [],
            } ],
            series: [{
                name: '非敏感',
                type: 'bar',
                xAxisIndex: 0,
                yAxisIndex: 0,
                z: 10,
                barCategoryGap: '40%',
                barWidth: '25%',
                label: {
                    normal: {
                        show: false,
                        position: 'insideRight',
                        textStyle: {
                            color: '#000000',
                        }

                    }
                },
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'inner',
                            formatter: function(data) {
                                return data.name
                            },
                        },
                        color: '#357CBA',
                    }
                },
                data: []
            },
                {
                    name: '非敏感',
                    type: 'bar',
                    xAxisIndex: 0,
                    yAxisIndex: 0,
                    silent: false,
                    barGap: '-100%',
                    barWidth: '25%',
                    label: {
                        normal: {
                            show: true,
                            position: 'left',
                            textStyle: {
                                color: '#888888',
                            }

                        },
                    },
                    itemStyle: {
                        normal: {
                            show: false,
                            color: '#F4F4F4',
                            label: {
                                show: false,
                                position: 'right',
                                color: '#000',
                                formatter: function(params) {
                                    return params.name
                                },
                            },
                        },
                        emphasis: {
                            color: '#F4F4F4',
                        }
                    },
                    data: []
                },
                {
                    name: '敏感',
                    type: 'bar',
                    z: 10,
                    barCategoryGap: '40%',
                    xAxisIndex: 2,
                    yAxisIndex: 2,
                    barWidth: '25%',
                    label: {
                        normal: {
                            show: false,
                            position: 'insideLeft',
                            textStyle: {
                                color: '#000000',
                            }

                        },
                    },
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'inner',
                                formatter: function(data) {
                                    return data.name
                                },
                            },
                            color: '#C77055',
                        }

                    },
                    data: []
                }, {
                    name: '敏感',
                    type: 'bar',
                    xAxisIndex: 2,
                    yAxisIndex: 2,
                    silent: false,
                    barGap: '-100%',
                    cursor: "pointer",
                    barWidth: '25%',
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            textStyle: {
                                color: '#888888',
                            }

                        },
                    },
                    itemStyle: {
                        normal: {
                            show: false,
                            color: '#F4F4F4',
                            label: {
                                show: false,
                                position: 'right',
                                color: '#000',
                                formatter: function(params) {
                                    return params.name
                                },
                            },

                        },
                        emphasis: {
                            color: '#EEEEEE',
                        },
                    },
                    data: []
                }
            ]
        }
        ,
        emotionSex_kb:{
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '10%',
                right: '20%',
                top: '5%',
                bottom: '0',
                containLabel: true
            },
            legend: {
                show: false,
                selectedMode:false,
                orient: 'horizontal',
                data: [{
                    name: '非敏感',
                    textStyle: {
                        color: '#0E7DC0',
                        fontSize: 16,

                    }
                }, {
                    name: '敏感',
                    textStyle: {
                        color: '#C14B2D',
                        fontSize: 16,

                    }
                }, ],
//			itemWidth: 15,
//			itemHeight: 15
            },
            xAxis: [{
                type: 'category',
                data: [],
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: "#000"
                    }
                },
                axisTick: {
                    show: false,
                }
            }],
            yAxis: [{
                type: 'value',
                show: false
            }],
            series: [{
                type: 'bar',
                barGap: '-100%',
                barWidth: '20%',
                data: [{
                    name: [],
                    value: '110',
                }, {
                    name: [],
                    value: '110',
                }],
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: '#666666',
                            fontSize: 14,
                        }

                    },
                },
                itemStyle: {
                    normal: {
                        show: false,
                        color: 'rgba(0,0,0,0.05)',
                        label: {
                            show: false,
                            position: 'right',
                            color: '#000',
                            formatter: function(params) {
                                return params.name
                            },
                        },
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0.05)'
                    }
                }
            },
                {
                    name: '男',
                    type: 'bar',
                    stack: '性别统计',
                    barWidth: '20%',
                    data: [10, 10],
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#ffffff'
                        }
                    }
                }, {
                    name: '非敏感',
                    type: 'bar',
                    stack: '性别统计',
                    barWidth: '20%',
                    data: [{
                        name: "非敏感",
                        value: [],
                        itemStyle: {
                            normal: {
                                color: '#0E7DC0'
                            }
                        }

                    }, {
                        name: "敏感",
                        value: [],
                        itemStyle: {
                            normal: {
                                color: '#C14B2D'
                            }
                        }
                    }],
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#0E7DC0'
                        }
                    }
                }, {
                    type: 'bar',
                    barGap: '-100%',
                    barWidth: '20%',
                    data: [{
                        name: [],
                        value: '-110',
                    }, {
                        name: [],
                        value: '-110',
                    }],
                    label: {
                        normal: {
                            show: true,
                            position: 'bottom',
                            textStyle: {
                                color: '#666666',
                                fontSize: 14,
                            }

                        },
                    },
                    itemStyle: {
                        normal: {
                            show: false,
                            color: 'rgba(0,0,0,0.05)',
                            label: {
                                show: false,
                                position: 'right',
                                color: '#000',
                                formatter: function(params) {
                                    return params.name
                                },
                            },
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0.05)'
                        }
                    },
                }, {
                    name: '女',
                    type: 'bar',
                    stack: '性别统计',
                    barWidth: '20%',
                    data: [-10, -10],
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#ffffff'
                        }
                    }
                }, {
                    name: '敏感',
                    type: 'bar',
                    stack: '性别统计',
                    barWidth: '20%',
                    data: [{
                        name: "非敏感",
                        value: [],
                        itemStyle: {
                            normal: {
                                color: '#0E7DC0'
                            }
                        }

                    }, {
                        name: "敏感",
                        value: [],
                        itemStyle: {
                            normal: {
                                color: '#C14B2D'
                            }
                        }
                    }],
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#C14B2D'
                        }
                    }
                },
            ]
        }
        ,
        emotionSex : {

            tooltip : {
                show : true,
                trigger : 'item',
                formatter : '{c}',
                axisPointer : {
                    type : 'shadow',
                }
            },
            legend : {
                type : 'plain',
                itemGap : 150,
                data : [ {
                    name : "男",
                    textStyle : {
                        fontSize : 14,
                        color : '#0E7DC0',
                    }

                }, {
                    name : "女",
                    textStyle : {
                        fontSize : 14,
                        color : '#CF421E',
                    }
                } ],
                width : '100%',
                show : true,
                orient : 'horizontal',
                itemWidth : 0,
                itemHeight : 0
            },
            grid : [ {
                show : false,
                left : '8%',
                top : '8%',
                bottom : '10%',
                containLabel : false,
                width : '35%'
            }, {
                show : false,
                left : '51%',
                top : '8%',
                bottom : '11%',
                width : '10%'
            }, {
                show : false,
                right : '10%',
                top : '8%',
                bottom : '10%',
                containLabel : false,
                width : '35%'
            }, ],
            xAxis : [ {
                gridIndex : 0,
                type : 'value',
                position : 'bottom',
                inverse : true,
                axisLine : {
                    show : true,
                    lineStyle : {
                        color : "#798091"
                    }
                },
                axisTick : {
                    show : false,
                },
                axisLabel : {
                    show : true,
                },
                splitLine : {
                    show : false,
                },
                splitNumber : 1,
                max : []
            }, {
                gridIndex : 1,
                type : 'value',
                show : false,
            }, {
                gridIndex : 2,
                type : 'value',
                axisLine : {
                    show : true,
                    lineStyle : {
                        color : "#798091"
                    }
                },
                axisTick : {
                    show : false,
                },

                axisLabel : {
                    show : true,
                },
                splitLine : {
                    show : false,
                },
                splitNumber : 1,
                max : []
            }, ],
            yAxis : [ {
                type : 'category',
                inverse : true,
                position : 'right',
                axisLine : {
                    show : false,
                },
                axisTick : {
                    show : false
                },
                axisLabel : {
                    show : false,
                },
                data : [],

            }, {
                gridIndex : 1,
                type : 'category',
                inverse : true,
                position : 'left',
                axisLine : {
                    show : false
                },
                axisTick : {
                    show : false
                },
                axisLabel : {
                    show : true,
                    textStyle : {
                        align : 'center',
                        color : '#888888',
                        fontSize : 13,
                    }

                },
                data : [ '喜悦', '中性', '愤怒', '悲伤', '惊奇', '恐惧' ]

            }, {
                gridIndex : 2,
                type : 'category',
                inverse : true,
                position : 'left',
                axisLine : {
                    show : false
                },
                axisTick : {
                    show : false
                },
                axisLabel : {
                    show : false,
                },
                data : [],
            }, ],
            series : [ {
                name : '男',
                type : 'bar',
                xAxisIndex : 0,
                yAxisIndex : 0,
                z : 10,
                barWidth : 20,
                barCategoryGap : '10%',
                label : {
                    normal : {
                        show : true,
                        position : 'left',
                        textStyle : {
                            color : '#888888',
                        }

                    },
                },
                data : [ {
                    name : '',
                    value : '',
                    itemStyle : {
                        normal : {
                            color : '#F18D00',
                        }
                    }
                }, {
                    name : '',
                    value : '',
                    itemStyle : {
                        normal : {
                            color : '#6E7584',
                        }
                    }
                }, {
                    name : '',
                    value : '',
                    itemStyle : {
                        normal : {
                            color : '#CF421E',
                        }
                    }
                }, {
                    name : '',
                    value : '',
                    itemStyle : {
                        normal : {
                            color : '#0E7DC0',
                        }
                    }
                }, {
                    name : '',
                    value : '',
                    itemStyle : {
                        normal : {
                            color : '#3FA579',
                        }
                    }
                }, {
                    name : '',
                    value : '',
                    itemStyle : {
                        normal : {
                            color : '#313131',
                        }
                    }
                } ]
            }, {
                name : '男',
                type : 'bar',
                xAxisIndex : 0,
                yAxisIndex : 0,
                silent : false,
                barGap : '-100%',
                barWidth : 20,
                label : {
                    normal : {
                        show : false,
                        position : 'left',
                        textStyle : {
                            color : '#888888',
                        }

                    },
                },
                itemStyle : {
                    normal : {
                        show : false,
                        color : '#F4F4F4',

                    }
                },
                data : []
            },

                {
                    name : '女',
                    type : 'bar',
                    z : 10,
                    barCategoryGap : '20%',
                    barWidth : 20,
                    xAxisIndex : 2,
                    yAxisIndex : 2,
                    label : {
                        normal : {
                            show : true,
                            position : 'right',
                            textStyle : {
                                color : '#888888',
                            }

                        },
                    },

                    data : [ {
                        name : '',
                        value : [],
                        itemStyle : {
                            normal : {
                                color : '#F18D00',
                            }
                        }
                    }, {
                        name : '',
                        value : [],
                        itemStyle : {
                            normal : {
                                color : '#6E7584',
                            }
                        }
                    }, {
                        name : '',
                        value : [],
                        itemStyle : {
                            normal : {
                                color : '#CF421E',
                            }
                        }
                    }, {
                        name : '',
                        value : [],
                        itemStyle : {
                            normal : {
                                color : '#0E7DC0',
                            }
                        }
                    }, {
                        name : '',
                        value : [],
                        itemStyle : {
                            normal : {
                                color : '#3FA579',
                            }
                        }
                    }, {
                        name : '',
                        value : [],
                        itemStyle : {
                            normal : {
                                color : '#313131',
                            }
                        }
                    } ]
                }, {
                    name : '女',
                    type : 'bar',
                    xAxisIndex : 2,
                    yAxisIndex : 2,
                    silent : false,
                    barGap : '-100%',
                    cursor : "pointer",
                    barWidth : 20,
                    label : {
                        normal : {
                            show : false,
                        },
                    },
                    itemStyle : {
                        normal : {
                            show : false,
                            color : '#F4F4F4',

                        },
                        emphasis : {
                            color : '#EEEEEE',
                        },
                    },
                    data : []
                } ]
        },
        emotionType : {
            tooltip : {
                trigger : 'axis',
                axisPointer : { // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend : {
                top : '0',
                left : 'center',
                bottom : '20',
                itemGap : 10,
                itemWidth : 10,
                itemHeight : 10,
                padding : 10,
                data : [ '喜悦', '中性', '愤怒', '悲伤', '惊奇', '恐惧' ]
            },
            grid : {
                // top:"10%",
                left : '3%',
                right : '3%',
                bottom : '0',
                containLabel : true
            },
            xAxis : {
                type : 'value',
                axisLine : { // 坐标轴轴线 默认 true,
                    show : false,
                    lineStyle : {
                        color : '#dbdbdb',
                        width : 1,
                        type : 'solid'
                    }
                },
                axisTick : { // 坐标轴刻度
                    show : false,
                    lineStyle : {
                        color : '#888',
                        width : 1,
                        type : 'solid'
                    }
                },
                splitLine : {
                    show : false
                },
                axisLabel : { // 坐标轴刻度标签
                    show : false,
                    // rotate: 30, //旋转角度
                    textStyle : {
                        color : '#888'
                    }
                }
            },
            yAxis : {
                type : 'category',
                axisLine : { // 坐标轴轴线 默认 true,
                    show : false,
                    lineStyle : {
                        color : '#dbdbdb',
                        width : 1,
                        type : 'solid'
                    }
                },
                axisTick : { // 坐标轴刻度
                    show : false,
                    lineStyle : {
                        color : '#888',
                        width : 1,
                        type : 'solid'
                    }
                },
                axisLabel : { // 坐标轴刻度标签
                    show : true,
                    textStyle : {
                        color : '#888'
                    }
                },

                data : [ '普通用户', '橙V用户', '蓝V用户', '达人用户' ]
            },
            series : [ {
                name : '喜悦',
                type : 'bar',
                stack : '总量',
                barWidth : '40%',
                label : {
                    normal : {
                        show : false,
                        position : 'insideRight'
                    }
                },
                itemStyle : {
                    normal : {
                        color : '#f18d00',
                    }
                },
                data : []
            }, {
                name : '中性',
                type : 'bar',
                stack : '总量',
                barWidth : '40%',
                label : {
                    normal : {
                        show : false,
                        position : 'insideRight'
                    }
                },
                itemStyle : {
                    normal : {
                        color : '#6F7583',
                    }
                },
                data : []
            }, {
                name : '愤怒',
                type : 'bar',
                stack : '总量',
                barWidth : '40%',
                label : {
                    normal : {
                        show : false,
                        position : 'insideRight'
                    }
                },
                itemStyle : {
                    normal : {
                        color : '#C14B2D',
                    }
                },
                data : []
            }, {
                name : '悲伤',
                type : 'bar',
                stack : '总量',
                barWidth : '40%',
                label : {
                    normal : {
                        show : false,
                        position : 'insideRight'
                    }
                },
                itemStyle : {
                    normal : {
                        color : '#357CBA',
                    }
                },
                data : []
            }, {
                name : '惊奇',
                type : 'bar',
                stack : '总量',
                barWidth : '40%',
                label : {
                    normal : {
                        show : false,
                        position : 'insideRight'
                    }
                },
                itemStyle : {
                    normal : {
                        color : '#5CA27C',
                    }
                },
                data : []
            }, {
                name : '恐惧',
                type : 'bar',
                stack : '总量',
                barWidth : '40%',
                label : {
                    normal : {
                        show : false,
                        position : 'insideRight'
                    }
                },
                itemStyle : {
                    normal : {
                        color : '#313131',
                    }
                },
                data : []
            } ]
        },
        line : {
            animation : false,
            tooltip : {
                trigger : 'axis',
                formatter : function(params) {
                    v = params[0].name;
                    for (var i = 0, l = params.length; i < l; i++) {
                        v += '<br/>' + params[i].seriesName + ' : '
                            + params[i].value;
                    }

                    return v;
                }
            },
            // toolbox: {
            // show : true,
            // orient:'vertical',
            // y:30,
            // x:'right',
            //
            // feature : {
            // mark : {show: false},
            // dataView : {
            // show: false,
            // readOnly: false,
            // lang: ['数据视图', '关闭', '刷新']
            // },
            // restore : {show: true},
            // saveAsImage : {
            // show: true,
            // name:""
            // },
            // }
            // },
            legend : {
                y : 20,
                data : []
            },
            grid : {
                x : 50,
                x2 : 50
            },
            xAxis : [ {
                type : 'category',// category|time
                boundaryGap : false,
                data : [],
                axisLine : {
                    onZero : false,
                    show : false
                },
                splitLine : {
                    show : false
                },
                splitNumber : 0,
                axisLabel : {
                    textStyle : {
                        decoration : 'none',
                        fontFamily : 'Microsoft YaHei',
                        fontSize : 12,
                    },
                    formatter : function(v) {
                        console.log(v.substring(5, v.length));
                        return v.substring(5, v.length);
                    }
                },
            } ],
            yAxis : [ {
                type : 'value',
                axisLine : {
                    onZero : false,
                    show : false
                },
                splitLine : {
                    show : false
                },
                splitArea : {
                    show : true,
                    areaStyle : {
                        color : [ '#FFF', '#F7F7F7' ]
                    }
                },
                axisLabel : {
                    formatter : function(v) {
                        if (v >= 1000) {
                            return (v / 1000) + "k";
                        } else {
                            return v;
                        }
                    }
                },
            } ],
            calculable : false,
            series : [ {
                showAllSymbol : true,
                type : 'line',
                data : []
            } ]
        },
        pie : {
            animation : false,
            color : [ "#f29300", "#3454a1", "#277bc0" ],
            tooltip : {
                trigger : 'item',
                formatter : "{a} <br/>{b} : {c} ({d}%)"
            },
            // toolbox: {
            // show : true,
            // orient:'vertical',
            // y:30,
            // feature : {
            // mark : {show: false},
            // dataView : {
            // show: false,
            // readOnly: false
            // },
            // restore : {show: true},
            // saveAsImage : {
            // show: true,
            // name:"",
            // type:'jpeg',
            // lang : ['点击保存']
            // }
            // }
            // },
            calculable : false,
            legend : {
                // orient : 'vertical',
                x : 'left',
                data : [],
            },
            series : [ {
                name : "",
                type : 'pie',
                radius : [ '40%', '65%' ],
                startAngle : 0,
                itemStyle : {
                    normal : {
                        label : {
                            show : true,
                            textStyle : {
                                fontSize : '12',
                                fontWeight : 'normal'
                            },
                            formatter : "{b} : {d}%"
                        },
                        labelLine : {
                            show : true,
                            length : 2
                        }
                    },
                    emphasis : {
                        label : {
                            show : true,
                            position : 'center',
                            textStyle : {
                                fontSize : '30',
                                fontWeight : 'bold'
                            }
                        }
                    }
                },
                data : []
            } ]
        },
        line2 : {
            tooltip : {
                trigger : 'axis'
            },
            legend : {
                top : '10',
                left : 'center',
                bottom : '20',
                itemGap : 5,
                textStyle : {
                    fontSize : 10,

                },
                data : [ '喜悦', '愤怒', '悲伤', '惊奇', '恐惧', '中性' ]
            },

            grid : {
                left : '3%',
                right : '2%',
                top : '18%',
                bottom : '15%',
                containLabel : true
            },
            xAxis : [ {
                type : 'category',
                axisLine : { // 坐标轴轴线 默认 true,
                    show : false
                },
                axisTick : { // 坐标轴刻度
                    show : false,
                    lineStyle : {
                        color : '#888',
                        width : 1,
                        type : 'solid'
                    }
                },
                axisLabel : { // 坐标轴刻度标签
                    show : true,
                    // rotate: 30, //旋转角度
                    textStyle : {
                        color : '#888'
                    },
                    formatter : function(v) {
//					console.log(v)
//					console.log(v.substring(5, 13))
                        return v.substring(5, 13);
                    }
                },
                boundaryGap : false,
                data : []
            } ],
            yAxis : [ {
                type : 'value',
                axisLine : { // 坐标轴轴线 默认 true,
                    show : false
                },
                axisTick : { // 坐标轴刻度
                    show : false
                },
                axisLabel : { // 坐标轴刻度标签
                    show : true,
                    // rotate: 30, //旋转角度
                    textStyle : {
                        color : '#888'
                    }
                },
            } ],
            series : [ {
                name : '',
                type : 'line',
                symbol : 'emptyCircle',
                symbolSize : 10,
                itemStyle : {
                    normal : {
                        color : '#F08C00',
                        borderColor : '#F08C00',
                        borderWidth : 2
                    }
                },
                lineStyle : {
                    normal : {
                        color : '#F08C00',
                        width : 2,
                        type : 'solid'
                    }
                },
                areaStyle : {
                    normal : {
                        color : 'rgba(0,0,0,0.5)',
                        opacity : 0
                    }
                },
                smooth : true,
                data : []
            } ]
        },
        bar : {
            animation : false,
            // color:["#bb814e","#bb814e","#bb814e","#f29300","#f29300","#f29300","#f29300","#72c1be","#72c1be","#72c1be"],
            tooltip : { // Option config. Can be overwrited by series or data
                trigger : 'axis',
            },
            // toolbox: {
            // show : true,
            // orient:'vertical',
            // y:30,
            // x:'right',
            //
            // feature : {
            // mark : {show: false},
            // dataView : {
            // show: false,
            // readOnly: false,
            // lang: ['数据视图', '关闭', '刷新']
            // },
            // restore : {show: true},
            // saveAsImage : {
            // show: true,
            // name:""
            // }
            // }
            // },
            xAxis : [ {
                type : 'category',
                data : [],
                axisLine : {
                    onZero : false,
                    show : false
                },
                axisLabel : {
                    formatter : function(value) {
                        if (value.length > 4) {
                            value = value.substring(0, 6);
                        }
                        return value;
                    },
                    rotate : 45,
                    textStyle : {
                        decoration : 'none',
                        fontFamily : 'Microsoft YaHei',
                        fontSize : 12,
                    }

                },
                splitArea : {
                    show : false
                }
            } ],
            grid : {
                y2 : 80,
                x : 55,
                x2 : 30
            },
            yAxis : [ {
                type : 'value',
                data : [],
                axisLine : {
                    onZero : false,
                    show : false
                },
                splitLine : {
                    show : false
                },
                splitArea : {
                    show : true,
                    areaStyle : {
                        color : [ '#FFF', '#F7F7F7' ]
                    }
                },
                axisLabel : {
                    textStyle : {
                        decoration : 'none',
                        fontFamily : 'Microsoft YaHei',
                        fontSize : 12,
                    },
                    formatter : function(v) {
                        if (v >= 1000) {
                            return (v / 1000) + "k";
                        } else {
                            return v;
                        }
                    }
                }
            } ],
            /* color:['#87cefa','#ff7f50'], */
            series : [ {
                name : '数量',
                type : 'bar',
                data : [],
            } ]
        },
        bar1 : {
            tooltip : { // Option config. Can be overwrited by series or data
                trigger : 'axis',
                formatter : "{b}  影响力:{c}"
            },
            toolbox : {
                show : true,
                orient : 'horizontal',
                y : 5,
                x : 'right',

                feature : {
                    mark : {
                        show : false
                    },
                    dataView : {
                        show : false,
                        readOnly : false,
                        lang : [ '数据视图', '关闭', '刷新' ]
                    },
                    restore : {
                        show : true
                    },
                    saveAsImage : {
                        show : true,
                        name : ""
                    }
                }
            },
            xAxis : [ {
                type : 'value',
                splitLine : {
                    show : false
                },
                axisLabel : {
                    textStyle : {
                        decoration : 'none',
                        fontFamily : 'Microsoft YaHei',
                        fontSize : 12,
                        // color:"#fff"
                    },
                    formatter : function(v) {
                        if (v >= 1000) {
                            return (v / 1000) + "k";
                        } else {
                            return v;
                        }
                    }
                }
            } ],
            grid : {
                y : 30,
                y2 : 30,
                x : 30,
                x2 : 30,
                borderWidth : 0
            },
            yAxis : [ {
                show : false,
                type : 'category',
                data : [],
                axisLabel : {
                    formatter : function(value) {
                        if (value.length > 10) {
                            value = value.substring(0, 10);
                        }
                        return value;
                    },
                    /* rotate:45, */
                    textStyle : {
                        decoration : 'none',
                        fontFamily : 'Microsoft YaHei',
                        fontSize : 12,
                        // color:"#fff"
                    }

                },
                splitArea : {
                    show : true,
                    areaStyle : {
                        color : [ '#08172c', '#08172c' ]
                    }
                }

            } ],
            calculable : false,
            animation : false,
            series : [ {
                name : '数量',
                type : 'bar',
                itemStyle : {
                    normal : {
                        label : {
                            show : true,
                            position : 'insideLeft',
                            formatter : function(param) {
                                var value = param.name;
                                if (value.length > 30) {
                                    value = value.substring(0, 30);
                                }
                                return value;
                            },
                            textStyle : {
                                fontSize : 14,
                                color : '#6c6c6c'
                            }
                        }
                    },
                    emphasis : {
                        label : {
                            show : true,
                            textStyle : {
                                fontSize : 12
                            }
                        }
                    }
                },
                data : []
            } ]
        },
        pie1 : {
            color : [ '#D2D2D2', '#4C8AD0', ],
            series : [ {
                name : '访问来源',
                type : 'pie',
                radius : [ '80%', '85%' ],
                itemStyle : {
                    normal : {
                        label : {
                            show : false,

                        },
                        labelLine : {
                            show : false
                        }
                    },
                    emphasis : {
                        label : {
                            show : true,
                            position : 'center',
                            textStyle : {
                                fontSize : '16',
                                fontWeight : 'bold'
                            }
                        }
                    }
                },
                data : []
            } ]
        },
        wordCloud : {
            animation : false,
            // backgroundColor: '#050A41',
            tooltip : {
                show : false,
            },
            series : [ {
                type : 'wordCloud',
                size : [ '100%', '100%' ],
                textRotation : [ 0, 45, 90, -45 ],
                textPadding : 10,
                autoSize : {
                    enable : true,
                    minSize : 14,
                    maxSize : 24
                },
                data : []
            } ]
        },
        chord : {
            tooltip : {
                trigger : 'item',
                formatter : function(params) {
                    if (params.indicator2) { // is edge
                        return params.indicator2 + ' ' + params.name + ' '
                            + params.indicator;
                    } else { // is node
                        return params.name
                    }
                }
            },
            legend : {
                x : 'left',
                data : []
            },
            series : [ {
                name : '',
                type : 'chord',
                sort : 'ascending',
                sortSub : 'descending',
                ribbonType : false,
                radius : '60%',
                itemStyle : {
                    normal : {
                        label : {
                            rotate : true
                        }
                    }
                },
                minRadius : 7,
                maxRadius : 20,
                // 使用 nodes links 表达和弦图
                nodes : [],
                links : []
            } ]
        },
        yibiaopan:{
            series: [{
                name: '刻度',
                type: 'gauge',
                radius: '74%',
                min: 0,
                max: 100,
                splitNumber: 10, //刻度数量
                startAngle: 200,
                endAngle: -20,
                axisLine: {
                    show: true,
                    lineStyle: {
                        width: 1,
                        color: [
                            [1, '#877d83']
                        ]
                    }
                }, //仪表盘轴线
                axisLabel: {
                    show: true,
                    color: '#877d83',
                    distance: 30
                }, //刻度标签。
                axisTick: {
                    show: false,
                    lineStyle: {
                        color: '#877d83',
                        width: 1
                    },
                    length: -8
                }, //刻度样式
                splitLine: {
                    show: false,
                    length: -20,
                    lineStyle: {
                        color: '#877d83'
                    }
                }, //分隔线样式
                detail: {
                    show: false
                },
                pointer: {
                    show: false
                }
            },
                {

                    min: 0,
                    max: 100,
                    type: 'gauge',
                    radius: '91%',
                    center: ['50%', '50%'],
                    splitNumber: 0, //刻度数量
                    startAngle: 200,
                    endAngle: -20,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 15,
                            color: [
                                [66.6 / 100, new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
                                    offset: 0,
                                    color: '#fad202'
                                },
                                    {
                                        offset: 1,
                                        color: '#fef901'
                                    }
                                ])],
                                [
                                    1, 'rgba(0,0,0,0)'
                                ]
                            ],
                            shadowColor: '#9a4110', //默认透明
                            shadowOffsetX: 0,
                            shadowOffsetY: 0,
                            shadowBlur: 20,
                            opacity: 1,
                        }
                    },
                    splitLine: {
                        show: false,
                    },
                    axisLabel: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    pointer: {
                        show: false
                    },
                    title: {
                        show: false,
                        offsetCenter: [0, '-20%'], // x, y，单位px
                        textStyle: {
                            color: '#ddd',
                            fontSize: 10
                        }
                    },
                    //仪表盘详情，用于显示数据。
                    detail: {
                        show: false,
                        offsetCenter: [0, 0],
                        color: '#ddd',
                        formatter: function(params) {
                            return params
                        },
                        textStyle: {
                            fontSize: 10
                        }
                    },
                    data: [{
                        name: "当前用户总数",
                        value: 66.6,
                    }]
                }
            ]
        },
        tree : {
            animation : false,
            calculable : false,
            tooltip : {
                trigger : 'item',
                formatter : "{b}"
            },
            toolbox : {
                show : true,
                orient : 'vertical',
                y : 30,
                x : 'right',

                feature : {
                    mark : {
                        show : false
                    },
                    dataView : {
                        show : false,
                        readOnly : false,
                        lang : [ '数据视图', '关闭', '刷新' ]
                    },
                    restore : {
                        show : true
                    },
                    saveAsImage : {
                        show : true
                    }
                }
            },
            series : [ {
                name : '树图',
                type : 'tree',
                orient : 'horizontal', // vertical horizontal
                rootLocation : {
                    x : 30,
                    y : '40%'
                }, // 根节点位置 {x: 100, y: 'center'}
                nodePadding : 55,
                layerPadding : 100,
                hoverable : false,
                roam : false,
                /* symbolSize: 3, */
                itemStyle : {
                    normal : {
                        color : '#fb8435',
                        label : {
                            show : true,
                            position : 'right',
                            formatter : "{b}",
                            textStyle : {
                                color : '#6c6c6c',
                                fontSize : 14
                            }
                        },
                        lineStyle : {
                            color : '#ccc',
                            type : 'curve' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

                        }
                    },
                    emphasis : {
                        color : '#4883b4',
                        label : {
                            show : false
                        },
                        borderWidth : 0
                    }
                },

                data : []
            } ]
        }
    },
// 刷新图表数据
    refresh = function($id, opt) {
        if (!opt.noDataText) {
            opt.noDataText = "暂无数据";
        }
        opt.noDataLoadingOption = {
            text : opt.noDataText,
            effect : 'bubble',
            textStyle : {
                fontSize : 16
            }
        }
        console.log(JSON.stringify(opt));
        myChart[$id.prop("id")].setOption(opt);
    },

// 设置图表数据
    setEchartsOpion = function(opts) {
        var type = opts.$id.data("type");
        opts.$id.removeProp("_echarts_instance_");
        myChart[opts.$id.prop("id")] = echarts.init(opts.$id[0], "macarons");
        if (opts.event) {
            myChart[opts.$id.prop("id")].on(echarts.config.EVENT.CLICK, opts.event);
        }
        // 深度拷贝
        refresh(opts.$id, $.extend(true, {}, echartsOpts[type], opts.opt));
    };
// //数据load效果
// allLoading = function(){
// $(".echarts").each(function(){
// if($(this).width()){
// //初始化图表
// if(echarts){
// if(!myChart[$(this).prop("id")]){
// myChart[$(this).prop("id")] = echarts.init(this,"macarons");
// setLoading($(this));
// }
// }
// }
// });
// },
// //数据load效果
// allLoading1 = function(){
// var eles = document.querySelectorAll("div[data-type]");
// for(var i = 0;i<eles.length;i++){
// if(eles[i].clientHeight){
// //初始化图表
// if(echarts){
// myChart[eles[i].id] = echarts.init(eles[i],"macarons");
// setLoading1(eles[i]);
// }
// }
// };
// },
//
// //数据load效果
// setLoading = function($id){
// //setTimeout(function(){
// myChart[$id.prop("id")] = echarts.init($id[0],"macarons");
// //数据load效果
// myChart[$id.prop("id")].showLoading({
// text : "数据加载中，请稍等...",
// effect : "whirling",
// textStyle : {
// fontSize : 20
// }
// });
// //},300);
// },
// //数据load效果
// setLoading1 = function(ele){
// //setTimeout(function(){
// myChart[ele.id] = echarts.init(ele,"macarons");
// //数据load效果
// myChart[ele.id].showLoading({
// text : "数据加载中，请稍等...",
// effect : "whirling",
// textStyle : {
// fontSize : 20
// }
// });
// //},300);
// };
// allLoading1();
