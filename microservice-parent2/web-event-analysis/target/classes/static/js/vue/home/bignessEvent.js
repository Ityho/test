/**
 * Created by sty on 2018/6/27.
 */
var app = new Vue({
	el:'#bigEvent',
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
		loadMoreFlag:true,
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
			$.post(actionBase + 'home/hotEvent/getHotClassList.action',function (result) {
				app.hotLabelList = result.data.childrens;
			});
		},
		chooseHotLabel:function(hl){
			app.activeBigEventList = [];
			if(!hl){
				app.activeBigEventList = app.allBigEventList;
				app.activehotLabel = '';
				return;
			}
			app.activehotLabel = hl.name;
			if(app.allBigEventList.length){
				for (var i = 0; i < app.allBigEventList.length; i++) {
					var bigEvent =  app.allBigEventList[i];
					if(bigEvent.labels.indexOf(hl.id)){
						app.activeBigEventList.push(bigEvent);
					}
				}
			}
		},
		getBignessEventList:function(){
			var searchObj = {startTime:moment().add(-1, 'week').format('YYYY-MM-DD HH:mm:ss'),endTime:moment().format('YYYY-MM-DD HH:mm:ss')};
			$.post('getBignessEventList',searchObj,function(result){
				if(result.code == "0000"){
					app.allBigEventList = result.data;
					app.activeBigEventList = result.data;
					app.getHotIncidentList(result.data[0],1,1);
					app.activeId = result.data[0].id;
					app.activeBigEvent = result.data[0];
					Vue.nextTick(function(){
						app.scrollBigEventList();
					});
				}
			});
		},
		getHotIncidentList:function(be,sort,flag){
			//var searchObj = {importantEventId:be.id,sort:sort,startTime:moment().add(-1, 'months').format('YYYY-MM-DD HH:mm:ss'),endTime:moment().format('YYYY-MM-DD HH:mm:ss')};
			app.hiList = [];
			app.activeBigEvent = be;
			app.sort = sort;
			if(flag){
				if(app.activeId == be.id){
					app.activeId = '';
					return;
				}
				app.activeId = be.id;
			}
			app.hiList = be.hotIncidentList.sort(function(hi1,hi2){
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

			app.activeHotEvent = app.hiList[0];
			app.getHotIncidentLine(app.activeHotEvent);
			app.getHotIncidentPie(app.activeHotEvent);
			app.getHotIncidentBar(app.activeHotEvent);
//			$.post(basePath+'/view/home/bignessEvent/getHotIncidentList.action',searchObj,function(result){
//				if(result.code == "0000"){
//					app.hiList = result.data;
//					app.activeHotEvent = result.data[0];
//					if(app.activeHotEvent.mgNumberDay != 0){
//						app.mgProportion = (app.activeHotEvent.mgNumberDay / (app.activeHotEvent.mgNumberDay+app.activeHotEvent.fmgNumberDay) * 100).toFixed(2) + '%';
//					}else{
//						app.mgProportion = "0.0%";
//					}
//					app.getHotIncidentLine(result.data[0]);
//					app.getHotIncidentPie(result.data[0]);
//					app.getHotIncidentBar(result.data[0]);
//				}
//			});

		},
		getHotIncidentLine:function(hi){
			app.ratioHotAvg = 0;
			app.ratioHotTopCustom = 0;
			app.ratioHotTopTime = "-";
			if(!hi.lineData){
				app.lineData=false;
				return;
			}
			app.lineData=true;
			Vue.nextTick(function() {
				var lineData = JSON.parse(hi.lineData);
				var opt = $.extend(true,{},echartsOpts["line"]);
				for (var i = 0; i < lineData.length; i++) {
					opt.xAxis[0].data[i] = lineData[i].name;
					var total = parseFloat(lineData[i].total);
					opt.series[0].data[i] = total;
					app.ratioHotAvg += total;
					if(total > app.ratioHotTopCustom){
						app.ratioHotTopCustom = total;
						app.ratioHotTopTime = lineData[i].name;
					}
				}
				app.ratioHotAvg = (app.ratioHotAvg / lineData.length).toFixed(2);
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
//					app.lineData=true;
//					app.ratioHotAvg = result.data.ratioHotAvg;
//					app.ratioHotTopCustom = result.data.ratioHotTopCustom;
//					app.ratioHotTopTime = result.data.ratioHotTopTime;
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
//					app.lineData=false;
//				}
//			});
		},
		getHotIncidentPie:function(hi){
			if (hi.mgNumberDay == 0 && hi.fmgNumberDay == 0) {
				app.pieData = false;
				app.mgProportion = "0.0%";
				return;
			}
			if(hi.mgNumberDay == 0){
				app.mgProportion = "0.0%";
			}else{
				app.mgProportion = (hi.mgNumberDay / (hi.mgNumberDay+hi.fmgNumberDay) * 100).toFixed(2) + '%';
			}
			app.pieData = true;
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
				app.barData=false
			}else {
				app.barData = true;
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

			app.activeHotEvent = hi;
			app.getHotIncidentLine(hi);
			app.getHotIncidentPie(hi);
			app.getHotIncidentBar(hi);
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
					var h = autoTop.offsetHeight - autoCard.clientHeight;
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
			var scrollDown, scrollUp;
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
//滑动到底部加载更多
// $(document).ready(function(){
//
// 	$(document).endlessScroll({
//
// 		bottomPixels: 450,
//
// 		fireDelay: 10,
//
// 		callback: function(p){
// 			if(activeTab4.loadMoreFlag){
// 				activeTab4.loadMoreFlag=false;
// 				activeTab4.loadMore();
// 			}
// 		}
//
// 	});
//
// });
