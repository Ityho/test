var myChart = echarts.init(document.getElementById('mapEcharts'));
var option = null;
var geoCoordMap = {
    '上海': [119.1803, 31.2891],
    "福建": [119.4543, 25.9222],
    "重庆": [108.384366, 30.439702],
    '北京': [116.4551, 40.2539],
    "辽宁": [123.1238, 42.1216],
    "河北": [114.4995, 38.1006],
    "天津": [117.4219, 39.4189],
    "山西": [112.3352, 37.9413],
    "陕西": [109.1162, 34.2004],
    "甘肃": [103.5901, 36.3043],
    "宁夏": [106.3586, 38.1775],
    "青海": [101.4038, 36.8207],
    "新疆": [87.9236, 43.5883],
    "西藏": [91.11, 29.97],
    "四川": [103.9526, 30.7617],
    "吉林": [125.8154, 44.2584],
    "山东": [117.1582, 36.8701],
    "河南": [113.4668, 34.6234],
    "江苏": [118.8062, 31.9208],
    "安徽": [117.29, 32.0581],
    "湖北": [114.3896, 30.6628],
    "浙江": [119.5313, 29.8773],
    '内蒙古': [110.3467, 41.4899],
    "江西": [116.0046, 28.6633],
    "湖南": [113.0823, 28.2568],
    "贵州": [106.6992, 26.7682],
    "云南": [102.9199, 25.4663],
    "广东": [113.12244, 23.009505],
    "广西": [108.479, 23.1152],
    "海南": [110.3893, 19.8516],
    '黑龙江': [127.9688, 45.368],
    '台湾': [121.4648, 25.5630],
    '钓鱼岛': [123.0254, 25.1986],
    '赤尾屿': [126.0054, 26.1986]
};
var chinaDatas = [{
    name: '北京',
    eventName: '国庆十月一放假通知',
    value: 86
},
    {
        name: '福建',
        eventName: '国庆十月一放假通知',
        value: 65
    },
    {
        name: '广东',
        eventName: '国庆十月一放假通知',
        value: 53
    },
    {
        name: '上海',
        eventName: '国庆十月一放假通知',
        value: 48
    },

    {
        name: '河北',
        eventName: '国庆十月一放假通知',
        value: 2
    },
    {
        name: '天津',
        eventName: '国庆十月一放假通知',
        value: 6
    },
    {
        name: '山西',
        eventName: '国庆十月一放假通知',
        value: 12
    },
    {
        name: '陕西',
        eventName: '国庆十月一放假通知',
        value: 2
    },
    {
        name: '甘肃',
        eventName: '国庆十月一放假通知',
        value: 4
    },
    {
        name: '宁夏',
        eventName: '国庆十月一放假通知',
        value: 5
    },
    {
        name: '青海',
        eventName: '国庆十月一放假通知',
        value: 3
    },
    {
        name: '新疆',
        eventName: '国庆十月一放假通知',
        value: 0.4
    },
    {
        name: '西藏',
        eventName: '国庆十月一放假通知',
        value: 0.3
    },
    {
        name: '四川',
        eventName: '国庆十月一放假通知',
        value: 22
    },
    {
        name: '重庆',
        eventName: '国庆十月一放假通知',
        value: 12
    },
    {
        name: '山东',
        eventName: '通知',
        value: 9
    },
    {
        name: '河南',
        eventName: '通知',
        value: 0.9
    },
    {
        name: '江苏',
        eventName: '通知',
        value: 24
    },
    {
        name: '安徽',
        eventName: '通知',
        value: 15
    },
    {
        name: '湖北',
        eventName: '通知',
        value: 6
    },
    {
        name: '浙江',
        eventName: '通知',
        value: 15
    },
    {
        name: '内蒙古',
        eventName: '通知',
        value: 0.3
    },
    {
        name: '江西',
        eventName: '通知',
        value: 34
    },
    {
        name: '湖南',
        eventName: '通知',
        value: 12
    },
    {
        name: '贵州',
        value: 0.8,
        eventName: '通知',
    },
    {
        name: '广西',
        eventName: '通知',
        value: 16
    },
    {
        name: '海南',
        eventName: '通知',
        value: 37
    },
    {
        name: '辽宁',
        eventName: '通知',
        value: 0.2
    },
    {
        name: '吉林',
        eventName: '通知',
        value: 0.4
    },
    {
        name: '云南',
        eventName: '通知',
        value: 34
    },
    {
        name: '黑龙江',
        eventName: '通知',
        value: 5
    },
    {
        name: '台湾',
        eventName: '通知',
        value: 43
    }
];
var convertData = function (data, type) {
    /*type:1 地图参数；type:2数据参数*/
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        if (geoCoord) {
            if (type == 2) {
                res.push({
                    name: data[i].name,
                    value: geoCoord.concat(data[i].value),
                    username: data[i].username,
                    telphone: data[i].telphone,
                    address: data[i].address
                });
            } else {
                res.push({
                    name: data[i].name,
                    value: geoCoord.concat(data[i].value)
                });
            }
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
var option = {
    // backgroundColor: '#00013a',
    grid: {},
    geo: {
        map: 'china',
        label: {
            show: true,
            color: '#ffffff',
            emphasis: {
                color: 'white',
                show: false
            }
        },
        roam: false, //是否允许缩放
        top: 0,
        bottom: '0',
        aspectScale: 0.75, //长宽比
        itemStyle: {
            normal: {
                borderColor: 'rgba(0, 168, 232, 1)',
                borderWidth: 1,
                areaColor: {
                    type: 'radial',
                    x: 0.67,
                    y: 0.2,
                    r: 0.88,
                    colorStops: [{
                        offset: 1,
                        color: 'rgba(0, 50, 101, 1)' // 0% 处的颜色
                    }, {
                        offset: 0,
                        color: 'rgba(4, 14, 66, 1)' // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
                shadowColor: 'rgba(255, 255, 255, .1)',
                shadowOffsetX: -2,
                shadowOffsetY: 2,
                shadowBlur: 10
            },
            emphasis: {
                areaColor: 'rgba(0, 50, 101, 1)',
                borderWidth: 3
            }
        },

        tooltip: {
            show: true
        }
    },
    series: [{
        type: 'effectScatter',
        coordinateSystem: 'geo',
        z: 1,
        data: [],
        symbolSize: 12,
        label: {
            normal: {
                show: true,
                formatter: function (params) {
                    return params.data.eventName;
                },
                position: 'top',
                backgroundColor: 'rgba(140, 0, 93, .88)',
                padding: [5, 5],
                borderRadius: 3,
                lineHeight: 32,
                color: '#ffffff',

            },
            emphasis: {
                show: true
            }
        },
        itemStyle: {
            color: 'rgba(140, 0, 93, .5)',
        }
    },

        //地图
        {
            type: 'map',
            mapType: 'china',
            geoIndex: 0,
            z: 0,
            data: convertData(chinaDatas, 1)
        },
        {
            name: 'credit',
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
    ]

}
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}

var timer = setInterval(() => {
    var dataidx = Math.floor(Math.random() * 32);
var ranval = Math.floor(Math.random() * 10);
chinaDatas[dataidx].value = chinaDatas[dataidx].value + ranval;
var valarr = geoCoordMap[chinaDatas[dataidx].name];
valarr.push(ranval);
option.series[0].data = [{
    name: '',
    eventName: chinaDatas[dataidx].eventName,
    value: valarr
}];
myChart.setOption(option, true);
}, 3000);
myChart.setOption(option);
setTimeout(function () {
    window.onresize = function () {
        myChart.resize();

    }
});
