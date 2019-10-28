var lineEcharts = echarts.init(document.getElementById('lineEcharts'));
var option = {

    tooltip: {
        trigger: 'axis',
        axisPointer:{
            type:'line',
            lineStyle:{
                color:'rgba(0,0,0,0)'
            }
        },
        backgroundColor:'rgb(58, 58, 58)',

    },
    toolbox: {
        orient: 'vertical',
    },
    legend: {
        top: '0px',
        data: ['']
    },
    grid: {
        left: '2%',
        right: '5%',
        top: '20%',
        bottom: '12%',
        containLabel: true
    },
    xAxis: [{
        type: 'category',
        axisLine: { //坐标轴轴线 默认 true,
            show: true,
            lineStyle: {
                color: '#F18D00',
                width: 1,
                type: 'solid'
            }
        },
        axisTick: { //坐标轴刻度
            show: false,
            lineStyle: {
                color: '#888',
                width: 1,
                type: 'solid'
            }
        },
        axisLabel: { //坐标轴刻度标签
            show: true,
            //rotate: 30,  //旋转角度
            textStyle: {
                color: '#8F9DBA'
            }
        },
        boundaryGap: false,
        data: (function() {
            var now = new Date();
            var res = [];
            var len = 10;
            while (len--) {
                res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
                now = new Date(now - 2000);
            }
            return res;
        })()
    }],
    yAxis: [{
        type: 'value',
        axisLine: { //坐标轴轴线 默认 true,
            show: false
        },
        axisTick: { //坐标轴刻度
            show: false
        },
        axisLabel: { //坐标轴刻度标签
            show: true,
            textStyle: {
                color: '#8F9DBA'
            }
        },
        splitLine: {
            show: true,
            lineStyle: {
                color: 'rgba(106, 116, 139, 0.1)',

            }
        },
    }],

    series: [{
        name: '陈伟霆',
        type: 'line',
        smooth: true, //这句就是让曲线变平滑的
        showAllSymbol: true,
        symbol: 'circle', //拐点样式
        symbolSize:8,
        markPoint: {
            data: [{
                type: 'max',
                name: '最大值',
                symbol: 'images://https://www.w3.org/TR/SVG/images/paths/triangle01.svg',
                symbolOffset: [0, '-100%'],
                symbolSize: [50, 21],
                label: {
                    normal: {
                        show: true,
                        position: 'insideTop',
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
                        color: "#f18d00",
                        borderColor: 'rgba(241,141,0, .3)', //rgba(255, 199, 43, .3)
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
        markLine: {
            data: [{
                type: 'average',
                name: '平均值',
                label: {
                    normal: {
                        show: true,
                        textStyle: {
                            fontSize: '12',
                            fontWeight: 'bold',
                            color: '#F18D00',
                        },
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
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(241, 141, 1, 0.5)'
                }, {
                    offset: 1,
                    color: 'rgba(241, 141, 1, 0)'
                }], false),
                shadowColor: 'rgba(0, 0, 0, 0.1)',
                shadowBlur: 10
            }
        },
        data: [20, 20, 58, 39, 58, 99, 42, 39, 29, 10]
    }

    ]
};
lineEcharts.setOption(option);
setTimeout(function() {
    window.onresize = function() {
        lineEcharts.resize();

    }
});
