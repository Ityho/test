Vue.filter("formatDate",function(date,formatStr){
    var formatStr = formatStr || 'YYYY-MM-DD';
    return moment(date).format(formatStr);
});
var app = new Vue({
    el: '#app',
    data:{
            id: '',
            title: '',
            summary: '',
            eventLabel: '',
            startTime: '',
            endTime: '',
            hotValue: '',
            informationData: {},
            allsumMG: '',
            plantCap: [],
            mapData: [],
            similarData: [],
            // pieCanvas: {},
            // lineCanvas: {},
            // sourceCanvas: {},
            // cloudCanvas: {},
            // friendlyCanvas: {},
            // pie2Canvas: {},
            // MapInit: {},
            // line2Canvas: {},
            errShow_1: true,
            errShow_2: true,
            errShow_3: true,
            errShow: true,
            errShow_5: true,
            errShow_6: true,
            errShow_7: true,
            errShow_8: true,
    },
    methods: {
        pieCanvas: function (val) {
            var echarts1 = echarts.init(document.getElementById('echarts1'));
            var option = {
                title: {
                    text: val,
                    x: 'center',
                    y: 'center',
                    textStyle: {
                        fontWeight: 'normal',
                        color: '#000000',
                        fontSize: '25'
                    }
                },
                color: ['rgba(226, 226, 226, 1)'],
                legend: {
                    show: false
                },

                series: [{
                    name: '热度指数',
                    type: 'pie',
                    clockWise: true,
                    radius: ['50%', '60%'],
                    itemStyle: {
                        normal: {
                            label: {
                                show: false
                            },
                            labelLine: {
                                show: false
                            }
                        }
                    },
                    hoverAnimation: false,
                    data: [{
                        value: val,
                        name: '01',
                        itemStyle: {
                            normal: {
                                color: '#1E77CE',
                                label: {
                                    show: false
                                },
                                labelLine: {
                                    show: false
                                }
                            }
                        }
                    }, {
                        name: '02',
                        value: 100 - val
                    }]
                }]

            }
            echarts1.setOption(option);
        },
        lineCanvas: function  (data, line1) {
            var echarts2 = echarts.init(document.getElementById('echarts2'));
            var option = {
                tooltip: {
                    trigger: 'axis',
                    formatter: '{b} : {c}',
                    position: ['50%', '50%']
                },
                grid: {
                    left: '5%',
                    right: '10%',
                    top: '15%',
                    bottom: '5%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'category',
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false,
                        lineStyle: {
                            color: '#888',
                            width: 1,
                            type: 'solid'
                        }
                    },
                    axisLabel: { // 坐标轴刻度标签
                        show: true,
                        formatter: function(params) {
                            return params.split(' ')[0] + '\n' + params.split(' ')[1]
                        },
                        // rotate: 30,  //旋转角度
                        textStyle: {
                            color: '#888'
                        }
                    },
                    boundaryGap: false,
                    data: data
                }],
                yAxis: [{
                    type: 'value',
                    axisLine: { // 坐标轴轴线 默认 true,
                        show: false
                    },
                    axisTick: { // 坐标轴刻度
                        show: false
                    },
                    axisLabel: { // 坐标轴刻度标签
                        show: true,
                        // rotate: 30,  //旋转角度
                        textStyle: {
                            color: '#888'
                        }
                    }
                }],
                series: [{
                    name: '',
                    type: 'line',
                    symbol: 'emptyCircle',
                    symbolSize: 8,
                    markPoint: {
                        data: [{
                            type: 'max',
                            name: '最大值',
                            symbol: 'images://https://www.w3.org/TR/SVG/images/paths/triangle01.svg',
                            symbolOffset: [0, '-100%'],
                            symbolSize: [68, 22],
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideTop',
                                    textStyle: {
                                        fontSize: '11',
                                        fontWeight: 'bold',
                                        color: '#fff'
                                    }
                                }
                            }
                        }, {
                            symbol: 'circle',
                            type: 'max',
                            symbolSize: [10, 10],
                            itemStyle: {
                                normal: {
                                    color: '#1E77CE',
                                    borderColor: 'rgba(30, 119, 206, 0.3)',
                                    borderWidth: 10
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
                            color: '#1E77CE'
                        }
                    },
                    lineStyle: {
                        normal: {
                            color: '#1E77CE',
                            width: 1.5,
                            type: 'solid'
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: '#1E77CE',
                            shadowColor: 'rgba(0, 0, 0, 0.1)',
                            shadowBlur: 10
                        }
                    },
                    smooth: false,
                    data: line1
                }],

            }
            echarts2.setOption(option);
        },
        sourceCanvas: function  (plantCap) {
               let datalist = [{
                offset: [50, 50],
                symbolSize: 140,
                opacity:1,
                color: 'rgba(30, 119, 206,1)'
            }, {
                offset: [18, 66],
                symbolSize: 80,
                opacity:1,
                color: 'rgba(30, 119, 206,0.95)'
            }, {
                offset: [35, 84],
                symbolSize: 75,
                opacity:1,
                color: 'rgba(30, 119, 206,0.88)'
            }, {
                offset: [60, 88],
                symbolSize: 70,
                opacity:1,
                color: 'rgba(30, 119, 206,0.85)'
            }, {
                offset: [80, 80],
                symbolSize: 70,
                opacity:1,
                color: 'rgba(30, 119, 206,0.85)'
            }, {
                offset: [89, 60],
                symbolSize: 68,
                opacity:1,
                color: 'rgba(30, 119, 206,0.8)'
            }, {
                offset: [88, 39],
                symbolSize: 65,
                opacity:1,
                color: 'rgba(30, 119, 206,0.78)'
            }, {
                offset: [78, 20],
                symbolSize: 60,
                opacity:1,
                color: 'rgba(30, 119, 206,0.7)'
            }, {
                offset: [56, 12],
                symbolSize: 55,
                opacity:1,
                color: 'rgba(30, 119, 206,0.7)'
            }, {
                offset: [30, 15],
                symbolSize: 50,
                opacity:1,
                color: 'rgba(30, 119, 206,0.7)'
            }, {
                offset: [18, 27],
                symbolSize: 50,
                opacity:1,
                color: 'rgba(30, 119, 206,0.7)'
            }, {
                offset: [10, 45],
                symbolSize: 50,
                opacity:1,
                color: 'rgba(30, 119, 206,0.7)'
            }]
            let datas = []
            for (let i = 0; i < plantCap.length; i++) {
                let item = plantCap[i]
                let itemToStyle = datalist[i]
                datas.push({
                    name: item.name + '\n' + item.value + '\n' + item.per,
                    value: itemToStyle.offset,
                    symbolSize: itemToStyle.symbolSize,
                    label: {
                        normal: {
                            color:"#ffffff",
                            textStyle: {
                                fontSize: 13
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: itemToStyle.color,
                            opacity: itemToStyle.opacity
                        }
                    }
                })
            }
            var echarts2 = echarts.init(document.getElementById('echarts3'));
            var option = {
                grid: {
                    show: false,
                    top: 10,
                    bottom: 10,
                    left: 0,
                    right: 0
                },
                xAxis: [{
                    gridIndex: 0,
                    type: 'value',
                    show: false,
                    min: 0,
                    max: 100,
                    nameLocation: 'middle',
                    nameGap: 5
                }],
                yAxis: [{
                    gridIndex: 0,
                    min: 0,
                    show: false,
                    max: 100,
                    nameLocation: 'middle',
                    nameGap: 30
                }],
                series: [{
                    type: 'scatter',
                    symbol: 'circle',
                    symbolSize: 120,
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}',
                            color: '#fff',
                            textStyle: {
                                fontSize: '20'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#00acea'
                        }
                    },
                    data: datas
                }]

            }
            echarts2.setOption(option);
        },
        cloudCanvas: function  (data) {
            var echarts2 = echarts.init(document.getElementById('ciyunEchart'));
            var CYCOLORS = ['#1E77CE', '#FAA67D', '#4581BC', '#46C7FA']; //词云颜色
            var CYtextStyle = {
                normal: {
                    color: function(v) {
                        var color = CYCOLORS;
                        var num = Math.floor(Math.random() * (4));
                        return color[num];
                    },
                    shadowBlur: 20,
                    shadowColor: 'rgba(0,0,0,0.1)'
                }
            }
            var imgDom = "data:image/png;base64,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";
            var maskImage = new Image()
            maskImage.src = imgDom;
            // var option = {
            //     tooltip: {
            //         trigger: 'item',
            //         formatter: "{b}: {c} "
            //     },
            //     legend: {
            //         show: false
            //     },
            //     xAxis: [{
            //         type: 'category',
            //         show: false
            //
            //     }],
            //     series: [{
            //         type: 'wordCloud',
            //         gridSize: 8,
            //         sizeRange: [10, 30],
            //         rotationRange: [0, 0],
            //         maskImage: imgDom,
            //         textStyle: CYtextStyle,
            //         left: 'center',
            //         top: 'center',
            //         width: '90%',
            //         height: '65%',
            //         data: data
            //     }]
            //
            // };
            maskImage.onload = function() {
                echarts2.setOption({
                    tooltip: {
                        trigger: 'item',
                        formatter: "{b}: {c} "
                    },
                    legend: {
                        show: false
                    },
                    xAxis: [{
                        type: 'category',
                        show: false

                    }],
                    series: [{
                        type: 'wordCloud',
                        gridSize: 8,
                        sizeRange: [12, 25],
                        rotationRange: [0, 0],
                        maskImage: maskImage,
                        textStyle: CYtextStyle,
                        left: 'center',
                        top: 'center',
                        width: '100%',
                        height: '100%',
                        data: data
                    }]

                });
            }

        },
        friendlyCanvas: function  (data) {
            var echarts2 = echarts.init(document.getElementById('echarts5'));
            var option = {
                series: [{
                    name: '媒体友好度',
                    type: 'liquidFill',
                    data: [data],
                    radius: '55%',
                    color: ['#1E77CE'],
                    waveAnimation: true,
                    animationDuration: 0,
                    animationDurationUpdate: 0,
                    outline: {
                        borderDistance: 0,
                        itemStyle: {
                            borderWidth: 5,
                            borderColor: '#1E77CE'
                        }
                    },
                    backgroundStyle: {
                        borderColor: '#1E77CE',
                        borderWidth: 2,
                        color: '#fff'
                    },
                    label: {
                        normal: {
                            position: ['50%', '52%'],
                            align: 'center',
                            formatter: function (v) {
                                return (v.data * 100).toFixed(0) + '%' + '\n媒体友好度'
                            },
                            textStyle: {
                                fontSize: 14
                            }
                        }
                    }
                }]
            }
            echarts2.setOption(option);
        },
        pie2Canvas: function  (datas) {
            var dt=datas.data;
            var map=[];
            for (var key in dt) {
                if (dt[key].name === '愤怒') {
                    let data1 = {name: '', value: ''}
                    data1.name = dt[key].name
                    data1.value = dt[key].value
                    map.push(data1)
                    // map.set(dt[key].name, dt[key].value);
                }
            }
            for (var key in dt) {
                if (dt[key].name === '喜悦') {
                    let data1 = {name: '', value: ''}
                    data1.name = dt[key].name
                    data1.value = dt[key].value
                    map.push(data1)
                    // map.set(dt[key].name, dt[key].value);
                }
            }
            for (var key in dt) {
                if (dt[key].name === '中性') {
                    let data1 = {name: '', value: ''}
                    data1.name = dt[key].name
                    data1.value = dt[key].value
                    map.push(data1)
                    // map.set(dt[key].name, dt[key].value);
                }
            }
            for (var key in dt) {
                if (dt[key].name === '悲伤') {
                    let data1 = {name: '', value: ''}
                    data1.name = dt[key].name
                    data1.value = dt[key].value
                    map.push(data1)
                    // map.set(dt[key].name, dt[key].value);
                }
            }
            for (var key in dt) {
                if (dt[key].name === '恐惧') {
                    let data1 = {name: '', value: ''}
                    data1.name = dt[key].name
                    data1.value = dt[key].value
                    map.push(data1)
                    // map.set(dt[key].name, dt[key].value);
                }
            }
            for (var key in dt) {
                if (dt[key].name === '惊奇') {
                    let data1 = {name: '', value: ''}
                    data1.name = dt[key].name
                    data1.value = dt[key].value
                    map.push(data1)
                    // map.set(dt[key].name, dt[key].value);
                }
            }
            var echarts2 = echarts.init(document.getElementById('echarts6'));
            // let colorList = ['#778DCC', '#8BE3EC', '#C7DEF5', '#1E77CE', '#7BBA89', '#5AC8E0']
            let colorList = ['#CE431E', '#F08E00', '#9DA7B5', '#147BC0', '#6360D9', '#46B585']
            let option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b} : {d}%'
                },
                legend: {
                    orient: 'vertical',
                    top: 'center',
                    right: '10',
                    formatter: function (name) {
                        let oa = option.series[0].data
                        let num = oa[0].value + oa[1].value + oa[2].value + oa[3].value + oa[4].value + oa[5].value
                        for (let i = 0; i < option.series[0].data.length; i++) {
                            if (name === oa[i].name) {
                                return name + '' + (oa[i].value / num * 100).toFixed(2) + '%'
                            }
                        }
                    },
                    textStyle: {
                        fontSize: 12
                    },
                    data: ['愤怒', '喜悦', '中性', '悲伤', '恐惧', '惊奇']
                },
                series: [{
                    name: '情绪占比',
                    type: 'pie',
                    radius: '55%',
                    center: ['35%', '50%'],
                    color: colorList,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    data: map
                }]
            }
            echarts2.setOption(option);
        },

        mapChina: function (data) {
            var geoCoordMap = {
                '钓鱼岛': [123.0254, 25.1986],
                '赤尾屿': [126.0054, 26.1986]
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
            var echarts2 = echarts.init(document.getElementById('mapEcharts'));
            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{b} : {c}'
                },
                visualMap: {
                    min: 0,
                    max: data[0].value,
                    left: '0',
                    bottom: '10',
                    orient: 'vertical',
                    text: ['高', '低'],
                    calculable: true,
                    inRange: {
                        color: ['#D3E8FD', '#66A4E1', '#1E77CE']
                    }
                },
                geo: {
                    map: 'china',
                    top: 'center',
                    left: 'center',
                    selectedMode: 'single',
                    label: {
                        normal: {
                            textStyle: {
                                color: '#000'
                            },
                            show: false
                        },
                    },
                },
                series: [
                    {
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

                                textStyle:{
                                    fontSize:10,
                                    color: '#000',
                                }
                            },

                        },

                    },
                    {
                        name: '南海诸岛',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false,
                            },

                        },
                        itemStyle: {
                            normal: {
                                borderColor: '#959595',
                                areaColor: '#efefef', //地图背景颜色
                            },
                            emphasis: {
                                areaColor: '#bdd3f5'
                            }
                        },

                        data: [{
                            name: '南海诸岛',
                            tooltip: {
                                trigger: 'item',
                                show: true,
                                formatter: function(params) {
                                    return params.name + ':-'

                                }

                            },
                            itemStyle: {
                                normal: {
                                    borderColor: '#959595',
                                    areaColor: '#efefef'
                                }
                            }
                        }]
                    },

                    {
                        name: '全国',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: data
                    },
                ]
            }
            echarts2.setOption(option);
        },

        line2Canvas: function  (data1, data2) {
            var echarts2 = echarts.init(document.getElementById('echarts8'));
            var option = {
                title: {
                    text: '地域分布Top10',
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: 16
                    },
                    left: '15',
                    top: '5'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: { // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    },
                    formatter: '{b} : {c}'
                },
                grid: {
                    left: '3%',
                    right: '12%',
                    bottom: '10%',
                    top: '10%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'value',
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    splitLine: {
                        show: false

                    },
                    axisLabel: {
                        show: false
                    }
                }],
                yAxis: [{
                    type: 'category',
                    inverse: true,
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#888'
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    boundaryGap: true,
                    data: data1
                }],
                series: [{
            name: '微热点',
            type: 'bar',
            barWidth: '40%',
            itemStyle: {
                normal: {
                    color: '#1E77CE'
                }
            },
            label: {
                normal: {
                    formatter: function (param) {
                        if (param.data) {
                            return String(param.data).replace(/(\d)(?=(\d{3})+$)/g, '$1,')
                        } else {
                            return param
                        }
                    },
                    position: 'right',
                    show: true,
                    textStyle: {
                        color: '#444'
                    }
                }
            },
            data: data2
        }]
            }
            echarts2.setOption(option);
        },
        formatDate: function (stamp) {
            const date2 = stamp.replace(/-/g, '/')
            const date = new Date(date2)
            let Y = date.getFullYear() + '/'
            let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/'
            let D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate())
            return Y + M + D
        },
        // getPieCanvas: function (hotValue) {
        //     app.pieCanvas(hotValue);
        // },
        ecLineInit: function (id) {
            // const host = 'https://api-open.51wyq.cn/wrd/casebase/api/v1/chart/goVolume'
            const host ='/casebase/ecLineInit';
            let param = {
                caseId: id
            }
            this.$http.post(host, param,{emulateJSON: true}).then((res) => {
                if(res.data.data !== null) {
                    // this.lineCanvas = {
                    //     options: lineCanvas(res.data.dates, res.data.jsonLine),
                    //     disableTouch: true
                    // }
                    app.lineCanvas(res.data.data.dates, res.data.data.jsonLine);
                }else{
                    this.errShow_1 = false
                }
            }).catch(err => {
                console.log(err+'jiazai')
            })
        },
        ecSourceInit: function (id) {
            const host = '/casebase/ecSourceInit';
            // const host = 'https://api-open.51wyq.cn/wrd/casebase/api/v1/chart/goMedaiFrom'
            let param = {
                caseId: id
            }
            this.$http.post(host, param,{emulateJSON: true}).then((res) => {
                if(res.data.data !== null) {
                    let top = {name: '各类型媒体报道', value: res.data.data.sum, per: ''}
                    app.plantCap.push(top)
                    for (let key in res.data.data) {
                        if (key !== 'sum'&&key !== '其他') {
                            let percent = res.data.data[key] / res.data.data.sum
                            let boby = {name: key, value: res.data.data[key], per: (percent * 100).toFixed(2) + '%'}
                            app.plantCap.push(boby)
                        }
                    }
                    let compare = function (obj1, obj2) {
                        let val1 = parseFloat(obj1.value)
                        let val2 = parseFloat(obj2.value)
                        if (val1 > val2) {
                            return -1
                        } else if (val1 < val2) {
                            return 1
                        } else {
                            return 0
                        }
                    }
                    app.plantCap.sort(compare)
                    // this.sourceCanvas = {
                    //     options: sourceCanvas(this.plantCap),
                    //     disableTouch: true
                    // }
                    app.sourceCanvas(app.plantCap);
                } else {
                    app.errShow_2 = false
                }

            }).catch(err => {
                console.log(err)
            })
        },
        ecCloudInit: function (id) {
            const host = '/casebase/ecCloudInit';
            // const host = 'https://api-open.51wyq.cn/wrd/casebase/api/v1/chart/goMoreWords'
            let param = {
                caseId: id
            }
            this.$http.post(host, param,{emulateJSON: true}).then((res) => {
                if(res.data.data !== null) {
                    for (let word in res.data.data.li) {
                        res.data.data.li[word].itemStyle.normal.color='#1E77CE'
                    }
                    // this.cloudCanvas = {
                    //     options: cloudCanvas(res.data.li),
                    //     disableTouch: true
                    // }
                    app.cloudCanvas(res.data.data.li);
                }else{
                    app.errShow_3 = false
                }
            }).catch(err => {
                console.log(err)
            })
        },
        ecPie2Init: function (id) {
            const host = '/casebase/ecPie2Init';
            // const host = 'https://api-open.51wyq.cn/wrd/casebase/api/v1/chart/goEmotionStatAnalysis'
            let param = {
                caseId: id
            }
            this.$http.post(host, param,{emulateJSON: true}).then((res) => {
                if(res.data.data !== null) {
                    // this.pie2Canvas = {
                    //     options: pie2Canvas(res.data.data),
                    //     disableTouch: true
                    // }
                    app.pie2Canvas(res.data.data);
                } else {
                    app.errShow_5 = false
                }
            }).catch(err => {
                console.log(err)
            })
        },
        ecMapInit: function (id) {
            const host = '/casebase/ecMapInit';
            // const host = 'https://api-open.51wyq.cn/wrd/casebase/api/v1/chart/goMap'
            let param = {
                caseId: id
            }
            this.$http.post(host, param,{emulateJSON: true}).then((res) => {
                if(res.data.data !== null) {
                    for (let k in res.data.data) {
                        let data1 = {name: '', value: ''}
                        data1.name = k
                        data1.value = res.data.data[k]
                        app.mapData.push(data1)
                    }
                    // this.MapInit = {
                    //     options: mapChina(this.mapData),
                    //     disableTouch: true
                    // }
                    app.mapChina(app.mapData);
                } else {
                    app.errShow_6 = false
                }
            }).catch(err => {
                console.log(err)
            })
        },
        ecLine2Init: function (id) {
            const host = '/casebase/ecLine2Init';
            // const host = 'https://api-open.51wyq.cn/wrd/casebase/api/v1/chart/goMap'
            let param = {
                caseId: id
            }
            this.$http.post(host, param,{emulateJSON: true}).then((res) => {
                if(res.data.data !== null) {
                    let da2 = []
                    let da3 = []
                    for (let k in res.data.data) {
                        if (da2.length < 10) {
                            da2.push(k)
                            da3.push(res.data.data[k])
                        }
                    }
                    // this.line2Canvas = {
                    //     options: line2Canvas(da2, da3),
                    //     disableTouch: true
                    // }
                    app.line2Canvas(da2, da3);
                }else{
                    app.errShow_7 = false
                }
            }).catch(err => {
                console.log(err)
            })
        },
        getServer: function (id) {
            const host ='/casebase/getServer';
            // const host = 'https://api-open.51wyq.cn/wrd/casebase/api/v1/chart/goMedaiFriend'
            let param = {caseId:id};
            // this.$http.post(host,param,{emulateJSON: true}).then(function(res){
            this.$http.post(host, param,{emulateJSON: true}).then((res) => {
                if(res.data.data !== null) {
                    app.allsumMG = res.data.data.sumMG + res.data.data.unSumMG
                    res.data.data.sumMG = app.fixNum(res.data.data.sumMG)
                    res.data.data.unSumMG = app.fixNum(res.data.data.unSumMG)
                    app.allsumMG = app.fixNum(this.allsumMG)
                    app.informationData = res.data.data
                    // this.friendlyCanvas = {
                    //     options: friendlyCanvas(this.informationData.youhaodu),
                    //     disableTouch: true
                    // }
                    app.friendlyCanvas(app.informationData.youhaodu);
                }else {
                    app.errShow = false
                }
            }).catch(err => {
                console.log(err)
            })
        },
        fixNum: function (num) {
            return num > 9999 ? (num / 10000).toFixed(1) + '万' : num
        },
        getServerSimi: function (id) {
            const host = '/casebase/getServerSimi';
            // const host = 'https://api-open.51wyq.cn/wrd/casebase/api/v1/chart/goSimilarIncidents'
            let param = {
                caseId: id
            }
            this.$http.post(host, param,{emulateJSON: true}).then((res) => {
                if(res.data.data !== null) {
                    app.similarData = res.data.data
                }else{
                    app.errShow_8 = false
                }
            }).catch(err => {
                console.log(err)
            })
        },
        getOne:function(id){
            // console.log(val)
            // console.log(valtipe)
            // const host = '/casebase/caseBaseDetail';
            // // const host ='https://api-open.51wyq.cn/wrd/casebase/api/v1/list/casebase'
            // let param = {id:val,valtipe:valtipe};
            window.location.href="/casebase/caseBaseDetail?id="+id;
        },
        getDetails: function (caseId){
            const host = '/casebase/caseBaseDetailResult';
            // const host ='https://api-open.51wyq.cn/wrd/casebase/api/v1/list/casebase'
            let param = {caseId:caseId}
            this.$http.post(host,param,{emulateJSON: true}).then(function(res){
                if(res.data.data !== null){
                    var op=res.data.data;
                    app.pieCanvas(op.hotValue);
                    app.title=op.title;
                    app.startTime=op.startTime;
                    app.endTime=op.endTime;
                    // app.startTime=app.formatDate(op.startTime);
                    // app.endTime= app.formatDate(op.endTime);
                    app.summary=op.summary;
                    app.eventLabel=op.byteEventLabel;
                    app.hotValue=op.hotValue;
                }
            }).catch(function(err){
                console.log(err)
                app.errShow = false
            })
        }
    },
    created:function() {
        // var id = this.$root.$mp.query.id;
        var caseId=$("#caseId").val();
        this.getDetails(caseId);
        this.getServer(caseId);
        this.getServerSimi(caseId);
        // this.getPieCanvas(caseId);
        this.ecLineInit(caseId);
        this.ecSourceInit(caseId);
        this.ecCloudInit(caseId);
        this.ecPie2Init(caseId);
        this.ecMapInit(caseId);
        this.ecLine2Init(caseId);
    }
    // onLoad: function() {
    // //     // this.id = this.$root.$mp.query.id
    // //     // this.title = this.$root.$mp.query.title
    // //     // this.summary = this.$root.$mp.query.summary
    // //     // this.eventLabel = this.$root.$mp.query.eventLabel
    // //     // this.startTime = this.$root.$mp.query.startTimeStr
    // //     // this.endTime = this.$root.$mp.query.endTimeStr
    // //     // this.hotValue = this.$root.$mp.query.hotValue
    //         this.getDetails();
    //         this.pieCanvas();
    // //     // this.getServerSimi()
    // //     // this.getPieCanvas()
    // //     // this.ecLineInit()
    // //     // this.ecSourceInit()
    // //     // this.ecCloudInit()
    // //     // this.ecPie2Init()
    // //     // this.ecMapInit()
    // //     // this.ecLine2Init()
    // }
});