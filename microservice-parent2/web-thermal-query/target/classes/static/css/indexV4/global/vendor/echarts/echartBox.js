var barEchart1 = echarts.init(document.getElementById('barEchart1'));
var option = {

};
barEchart1.setOption(option);
var pieEchart2 = echarts.init(document.getElementById('pieEchart2'));
var option = {
    tooltip: {
        trigger: 'item',
        formatter: "{b} : {d}% <br/> {c}"
    },
    legend: {
        show:false,
    },
    series: [{
        type: 'pie',
        radius: ['30%', '40%'],
        center: ['50%', '50%'],
        color: ['#53C1C3', '#0A5ADE'],
        data: [{
            value: 335,
            name: '敏感'
        },
            {
                value: 310,
                name: '非敏感'
            }
        ],
        labelLine: {
            normal: {
                show: true,
                length: 8,
                length2: 8,
                lineStyle: {
                    width: 2
                }
            }
        },
        label: {
            normal: {
                formatter:'{b}\n{d}%\n({c})',
                textStyle:{
                    fontSize:12
                }
            }
        }
    }]
};
pieEchart2.setOption(option);
var barEchart2 = echarts.init(document.getElementById('barEchart2'));
var option = {
    tooltip: {
        show: false,
    },
    legend: {
        type: 'plain',
        data: [],
        show: false,

    },
    grid: [{
        left: '20%',
        top: '1%',
        right: '33%',
        bottom:'1%'
    }, {
        left: '12%',
        top: '1%',
        bottom:'1%'
    }],
    xAxis: [{
        gridIndex: 0,
        type: 'value',
        show: false,

    }, {
        gridIndex: 1,
        type: 'value',
        show: false,

    }],
    yAxis: [{
        gridIndex: 0,
        type: 'category',
        show: false,
        data: []
    }, {
        gridIndex: 1,
        type: 'category',
        data: ['政务', '新闻', '报刊', ],
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
                color: '#ffffff',
                fontSize: 12,
            }

        },
    }],
    series: [

        {
            name: '事件',
            type: 'bar',
            z: 10,
            barCategoryGap: '20%',
            barWidth: '30%',
            xAxisIndex: 0,
            yAxisIndex: 0,
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
                    color: '#EDCC32',
                }

            },
            data: [{
                name: '',
                value: '200',
            }, {
                name: '',
                value: '100',
            }, {
                name: '',
                value: '50',
            }],
        },
        {
            name: '',
            type: 'bar',
            xAxisIndex: 0,
            yAxisIndex: 0,
            silent: false,
            barGap: '-100%',
            barWidth: '30%',
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    textStyle: {
                        color: '#ffffff',
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
                    color: '#1C2654',
                }

            },
            data: [{
                name: '123456',
                value: '400',
            }, {
                name: '123456',
                value: '400',
            }, {
                name: '123456',
                value: '400',
            }]
        }
    ]
};
barEchart2.setOption(option);
//重新定义所有图表自适应容器大小
setTimeout(function() {
    window.onresize = function() {
        pieEchart2.resize();
        barEchart1.resize();
        barEchart2.resize();

    }
});
