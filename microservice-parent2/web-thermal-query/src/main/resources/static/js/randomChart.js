/**
 * Created by 罗朝州 on 2017/4/14.
 */

var randomChart = (function(){

    function randomIndex(level,newData){//level 1-9
        var center = 50;
        var realRadius = level*5;
        var radiusUp = center + realRadius;
        var radiusDown = center - realRadius;
        var index;
        while(true){
            index = [randomNum(radiusDown,radiusUp),randomNum(radiusDown,radiusUp)];
            var val = (index[0]-center)*(index[0]-center) + (index[1]-center)*(index[1]-center);
            if(val==realRadius*realRadius){
                var flag = true;
                for(var i = 0;i<newData.length;i++){
                    if(index[0] == newData[i][0] && index[1] == newData[i][1]){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    break;
                }
            }
        }
        //console.log(index);
        return index;
    }

    function randomNum(min,max){
        return Math.round(Math.random()*(max-min)+min);
    }

    function dataConvert(data){
        //console.log(data);
        var newData = [];
        for(var i=0;i<data.length;i++){
            var index = randomIndex(data[i][0],newData);
            newData[i] = [index[0],index[1],data[i][1],data[i][2],data[i][3],data[i][4]];
        }
        return newData;
    }

    var markLineOpt = {};

    return {
        showChart:showChart
    };

    function showChart(data,dataCore,dom){
        var datalist = dataConvert(data);
        var dataMap = [];
        for(var i = 0;i<datalist.length;i++){
            dataMap[i] = {
                name: '关联度',
                type: 'scatter',
                xAxisIndex: 0,
                yAxisIndex: 0,
                symbol: 'circle',
                symbolSize: datalist[i][3],
                label: {
                    normal: {
                        show: true,
                        textStyle: {
                            color: '#555'
                        },
                        position: 'bottom',
                        formatter: function(param) {
                            return param.data[2];
                        }
                    }
                },
                itemStyle: {
                    normal: {
                    	color: datalist[i][4]==1? '#eaa269':'#b9a7af',
                        opacity: 1
                    }
                },
                data: [datalist[i]]
            };
        }

        dataMap.push({
            name: '弱相关',
            type: 'scatter',
            xAxisIndex: 0,
            yAxisIndex: 0,
            symbol: 'circle',
            symbolSize: 80,
            label: {
                normal: {
                    show: true,
                    textStyle: {
                        fontSize: '20'
                    },
                    formatter: function(param) {
                        return param.data[2];
                    }
                }

            },

            itemStyle: {
                normal: {
                	color: '#eaa269',
                    opacity: 1
                }
            },
            data: dataCore,
            markLine: {}
        });

        dataMap.push({
                name: '相关背景1',
                type: 'pie',
                avoidLabelOverlap: false,
                radius: ['0%', '50%'],
                itemStyle: {
                    normal: {
                        color: 'rgba(231,145,107,0.3)',
                    },
                    emphasis: {
                        color: 'rgba(231,145,107,0.3)',
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [{
                    value: 1
                }],
                animation: false
            });

        dataMap.push({
            name: '相关背景2',
                type: 'pie',
                avoidLabelOverlap: false,
                radius: ['50%', '100%'],
                itemStyle: {
                    normal: {
                        color: 'rgba(231,145,107,0.2)',
                    },
                    emphasis: {
                        color: 'rgba(231,145,107,0.2)',
                    }
                },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [{
                value: 1
            }],
                animation: false
        });

        dataMap.push({
                name: '相关背景3',
                type: 'pie',
                avoidLabelOverlap: false,
                radius: ['100%', '150%'],
                itemStyle: {
                    normal: {
                        color: 'rgba(231,145,107,0.1)',
                    },
                    emphasis: {
                        color: 'rgba(231,145,107,0.1)',
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [{
                    value: 1
                }],
                animation: false
            });

        dataMap.push({
                name: '相关背景4',
                type: 'pie',
                avoidLabelOverlap: false,
                radius: ['150%', '200%'],
                itemStyle: {
                    normal: {
                        color: 'rgba(231,145,107,0.07)',
                    },
                    emphasis: {
                        color: 'rgba(231,145,107,0.07)',
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [{
                    value: 1
                }],
                animation: false
            });
        var option = {
            title: {
                text: '',
                x: '35%',
                y: 0
            },
            backgroundColor: 'rgba(231,145,107,0.02)',
            tooltip: {
                trigger: 'item',
                backgroundColor: '#fff',
                textStyle: {
                    color: '#999'
                },
                position: 'right',
                formatter: function (param) {
                    if(param.data[2]) {
                        return param.data[2];
                    }
    }
},
    xAxis: [{
    gridIndex: 0,
    type: 'value',
    show: false,
    min: 0,
    max: 100,
    nameLocation: 'middle',
    nameGap: 30


}],
    yAxis: [{
    gridIndex: 0,
    min: 0,
    show: false,
    max: 100,
    nameLocation: 'middle',
    nameGap: 30
}],
    series:dataMap
};
var chart = echarts.init(document.getElementById(dom));
chart.setOption(option);
}
})();