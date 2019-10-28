/**
 * Created by sty on 2018/6/27.
 */
var activeTab2 = new Vue({
    el:'#activeTab2',
    filters:{
        // 处理时间戳
        formatDate: function (date, formatStr) {
            if(!date || date == "-"){
                return date;
            }
            var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
            return moment(date).format(formatStr);
        }
    },
    data:{
        list:[],
        hiList:[],
        allBigEventList:[],
        activeBigEventList:[],
        hotLabelList: {},//热点类型列表
        activehotLabel:'',
        activeId:'',
        activeBigEvent:{},
        activeHotEvent:{},
        startTime:moment().add(-1, 'week').format('YYYY-MM-DD'),
        endTime:moment().format('YYYY-MM-DD'),
        hotLineList:[],
        ratioHotAvg:0,
        sort:1,
        ratioHotTopCustom:0,
        ratioHotTopTime:null,
        mgProportion:'0.0%',
        lineData:false,//曲线走势图数据
        chooseControl:0,//页面选中效果开关
        pieData:false,//敏感图
        barData:false,//柱形图
    },
    created:function(){
        //this.getLabelList();
        this.getBignessEventList();


    },
    methods:{
        // 获取热点类型标签
        getLabelList: function (id) {
            $.post(actionBase + '/hot/events/getHotClassList',function (result) {
                activeTab2.hotLabelList = result.data.childrens;
            });
        },
        chooseHotLabel:function(hl){
            activeTab2.activeBigEventList = [];
            if(!hl){
                activeTab2.activeBigEventList = activeTab2.allBigEventList;
                activeTab2.activehotLabel = '';
                return;
            }
            activeTab2.activehotLabel = hl.name;
            if(activeTab2.allBigEventList.length){
                for (var i = 0; i < activeTab2.allBigEventList.length; i++) {
                    var bigEvent =  activeTab2.allBigEventList[i];
                    if(bigEvent.labels.indexOf(hl.id)){
                        activeTab2.activeBigEventList.push(bigEvent);
                    }
                }
            }
        },
        getBignessEventList:function(){
            var searchObj = {startTime:moment().add(-1, 'week').format('YYYY-MM-DD HH:mm:ss'),endTime:moment().format('YYYY-MM-DD HH:mm:ss')};
            $.post(actionBase+'/major/events/getBignessEventList',searchObj,function(result){
                if(result.code == "0000"){
                    activeTab2.allBigEventList = result.data;
                    activeTab2.activeBigEventList = result.data;
                    activeTab2.getHotIncidentList(result.data[0],1,1);
                    activeTab2.activeId = result.data[0].id;
                    activeTab2.activeBigEvent = result.data[0];
                    Vue.nextTick(function(){
                        activeTab2.scrollBigEventList();
                    });
                }
            });
        },
        getHotIncidentList:function(be,sort,flag){
            //var searchObj = {importantEventId:be.id,sort:sort,startTime:moment().add(-1, 'months').format('YYYY-MM-DD HH:mm:ss'),endTime:moment().format('YYYY-MM-DD HH:mm:ss')};
            activeTab2.hiList = [];
            activeTab2.activeBigEvent = be;
            activeTab2.sort = sort;
            if(flag){
                if(activeTab2.activeId == be.id){
                    activeTab2.activeId = '';
                    return;
                }
                activeTab2.activeId = be.id;
            }
            activeTab2.hiList = be.hotIncidentList.sort(function(hi1,hi2){
                if(sort == 1){
                    if (hi1.createTime < hi2.createTime) {
                        return 1;
                    }else if(hi1.createTime > hi2.createTime){
                        return -1
                    }else{
                        return 0;
                    }
                }else if(sort == 2){
                    if (hi1.ratioHotDay < hi2.ratioHotDay) {
                        return 1;
                    }else if(hi1.ratioHotDay > hi2.ratioHotDay){
                        return -1
                    }else{
                        return 0;
                    }
                }else if(sort == 3){
                    if (hi1.numberDay < hi2.numberDay) {
                        return 1;
                    }else if(hi1.numberDay > hi2.numberDay){
                        return -1
                    }else{
                        return 0;
                    }
                }
            });

            activeTab2.activeHotEvent = activeTab2.hiList[0];
            activeTab2.getHotIncidentLine(activeTab2.activeHotEvent);
            activeTab2.getHotIncidentPie(activeTab2.activeHotEvent);
            activeTab2.getHotIncidentBar(activeTab2.activeHotEvent);
//			$.post(basePath+'/view/home/bignessEvent/getHotIncidentList.action',searchObj,function(result){
//				if(result.code == "0000"){
//					activeTab2.hiList = result.data;
//					activeTab2.activeHotEvent = result.data[0];
//					if(activeTab2.activeHotEvent.mgNumberDay != 0){
//						activeTab2.mgProportion = (activeTab2.activeHotEvent.mgNumberDay / (activeTab2.activeHotEvent.mgNumberDay+activeTab2.activeHotEvent.fmgNumberDay) * 100).toFixed(2) + '%';
//					}else{
//						activeTab2.mgProportion = "0.0%";
//					}
//					activeTab2.getHotIncidentLine(result.data[0]);
//					activeTab2.getHotIncidentPie(result.data[0]);
//					activeTab2.getHotIncidentBar(result.data[0]);
//				}
//			});
        },
        getHotIncidentLine:function(hi){
            activeTab2.ratioHotAvg = 0;
            activeTab2.ratioHotTopCustom = 0;
            activeTab2.ratioHotTopTime = "-";
            if(!hi.lineData){
                activeTab2.lineData=false;
                return;
            }
            activeTab2.lineData=true;
            Vue.nextTick(function() {
                var lineData = JSON.parse(hi.lineData);
                var opt = $.extend(true,{},echartsOpts["line"]);
                for (var i = 0; i < lineData.length; i++) {
                    opt.xAxis[0].data[i] = lineData[i].name;
                    var total = parseFloat(lineData[i].total);
                    opt.series[0].data[i] = total;
                    activeTab2.ratioHotAvg += total;
                    if(total > activeTab2.ratioHotTopCustom){
                        activeTab2.ratioHotTopCustom = total;
                        activeTab2.ratioHotTopTime = lineData[i].name;
                    }
                }
                activeTab2.ratioHotAvg = (activeTab2.ratioHotAvg / lineData.length).toFixed(2);
                setEchartsOpion({
                    $id : $("#bignessEventLine"),
                    opt : opt
                });
            });
//			var searchObj = {keyword:hi.keyword,
//							filterKeyword:hi.filterKeyword,
//							startTime:moment().add(-1, 'day').format('YYYY-MM-DD HH:mm:ss'),
//							endTime:moment().format('YYYY-MM-DD HH:mm:ss')};
//			$.post(basePath+'/view/home/bignessEvent/getHotIncidentLine.action',searchObj,function(result){
//				if(result.code == "0000"){
//					activeTab2.lineData=true;
//					activeTab2.ratioHotAvg = result.data.ratioHotAvg;
//					activeTab2.ratioHotTopCustom = result.data.ratioHotTopCustom;
//					activeTab2.ratioHotTopTime = result.data.ratioHotTopTime;
//					var opt = $.extend(true,{},echartsOpts["line"]);
//					for (var i = 0; i < result.data.hotValueList.length; i++) {
//						opt.xAxis[0].data[i] = result.data.hotValueList[i].name;
//						opt.series[0].data[i] = result.data.hotValueList[i].total;
//					}
//					Vue.nextTick(function() {
//							setEchartsOpion({
//								$id : $("#bignessEventLine"),
//								opt : opt
//							});
//						});
//				}else {
//					activeTab2.lineData=false;
//				}
//			});
        },
        getHotIncidentPie:function(hi){
            if (hi.mgNumberDay == 0 && hi.fmgNumberDay == 0) {
                activeTab2.pieData = false;
                activeTab2.mgProportion = "0.0%";
                return;
            }
            if(hi.mgNumberDay == 0){
                activeTab2.mgProportion = "0.0%";
            }else{
                activeTab2.mgProportion = (hi.mgNumberDay / (hi.mgNumberDay+hi.fmgNumberDay) * 100).toFixed(2) + '%';
            }
            activeTab2.pieData = true;
            var opt = $.extend(true, {}, echartsOpts["pie"]);
            opt.series[0].data[0].value = hi.mgNumberDay;
            opt.series[0].data[1].value = hi.fmgNumberDay;
            Vue.nextTick(function() {
                setEchartsOpion({
                    $id : $("#bignessEventPie"),
                    opt : opt
                });
            });
        },
        getHotIncidentBar:function(hi){
            if (hi.mediaNumberDay==0 && hi.zwNumberDay==0 && hi.bkNumberDay==0){
                activeTab2.barData=false
            }else {
                activeTab2.barData = true;
                var opt = $.extend(true, {}, echartsOpts["bar"]);
                opt.series[0].data[0] = hi.mediaNumberDay;
                opt.series[0].data[1] = hi.zwNumberDay;
                opt.series[0].data[2] = hi.bkNumberDay;
                Vue.nextTick(function() {
                    setEchartsOpion({
                        $id : $("#bignessEventBar"),
                        opt : opt
                    });
                });
            }
        },
        dotHotIncidentEchart:function(hi,value){
            if(this.chooseControl==value){
                this.chooseControl=null;
            }else {
                this.chooseControl=value;
            }

            activeTab2.activeHotEvent = hi;
            activeTab2.getHotIncidentLine(hi);
            activeTab2.getHotIncidentPie(hi);
            activeTab2.getHotIncidentBar(hi);
        },
        goHotSearch:function(hi){
            $("#title").val(hi.incidentTitle);
            $("#keyword").val(hi.keyword);
            $("#filterKeyword").val(hi.filterKeyword);
            var $form = $("#searchForm");
            $form.prop("action",actionBase + "/goSearch.shtml");
            $form.submit();
        },
        scrollBigEventList:function(){
            var speed = 5;
            var autoCard = document.getElementById('autoBox');
            var autoTop = document.getElementById('autoBoxItem');
            var autoEnd = document.getElementById('autoScrollend');
            var scrollBtnUp = document.getElementById('scrollBtnUp');
            var scrollBtnDown = document.getElementById('scrollBtnDown');

            /**
             * @return {boolean}
             */
            function ScrollDown() {
                if (autoEnd.offsetTop - autoCard.scrollTop <= 0)
                    autoCard.scrollTop -= autoTop.offsetHeight;
                else {
                    autoCard.scrollTop++;
                    let h = autoTop.offsetHeight - autoCard.clientHeight;
                    if (autoCard.scrollTop == h) {
                        return false;
                    }
                }
            }

            /**
             * @return {boolean}
             */
            function ScrollUp() {
                if (autoEnd.offsetTop - autoCard.scrollTop <= 0)
                    autoCard.scrollTop += autoTop.offsetHeight;
                else {
                    autoCard.scrollTop--;
                    if (autoCard.scrollTop === 0) {
                        return false;
                    }
                }
            }
            let scrollDown, scrollUp;
            scrollBtnUp.onmouseover = function() {
                scrollDown = setInterval(ScrollDown, speed);
            };
            scrollBtnUp.onmouseout = function() {
                clearInterval(scrollDown)
            };
            scrollBtnDown.onmouseover = function() {
                scrollUp = setInterval(ScrollUp, speed)
            };
            scrollBtnDown.onmouseout = function() {
                clearInterval(scrollUp)
            };
        }
    }
});