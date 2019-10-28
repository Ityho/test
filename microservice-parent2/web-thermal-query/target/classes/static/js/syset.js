
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

function changeZhaiyaoTypeS(ev,id){
	var zhaiyaoCount = 2;
	for ( var i = 1; i <= zhaiyaoCount; i++) {
		if (i != id) {
			document.getElementById("zhaiyao_typeS_" + i).className="";
		} else {
			document.getElementById("zhaiyao_typeS_" + i).className="cur";
		}
	}
	document.getElementById("sset.summarySwitch").value = ev;
}

function selectTimeS(ev,id){
	var lenA = 7;
	for(var i=1; i<=lenA;i++){
		if(i!=id){
			document.getElementById("selectTimeS_" + i).className="";
		}else{
			document.getElementById("selectTimeS_" + i).className="cur";
		}
	}
	document.getElementById("sset.searchTimeQuantum").value = ev;
	if('0' == ev ){
		$("#syset_other_time").show();
		if(document.getElementById("sset.searchStartTime").value==''||document.getElementById("sset.searchEndTime").value==''){
			var now = new Date().getTime();
			document.getElementById("sset.searchStartTime").value = new Date(now).format("yyyy-MM-dd 00:00:00");
			document.getElementById("sset.searchEndTime").value = new Date(now).format("yyyy-MM-dd 23:59:59");
		}
	} 
}
// 排序
function parXuCommonS( ev ,id) {
	var paixuLen = 4;
	for ( var i = 1; i <= paixuLen; i++) {
		if (i != id) {
			document.getElementById("dateTimeS_" + i).className="";
		} else {
			document.getElementById("dateTimeS_" + i).className="cur";
		}
	}
	document.getElementById("sset.sortType").value = ev;
 }

// 过滤网站类型
function filterWebSiteCommon(id, ev) {
	var fltWebSiteLen = document.getElementById("filterWebSiteLen").value;
	for ( var i = 1; i <= fltWebSiteLen; i++) {
		if (i != id) {
			document.getElementById("filterWebSiteA_" + i).className = "";
		} else {
			document.getElementById("filterWebSiteA_" + i).className = "cur";
		}
	}
	document.getElementById("sset.filterWebsiteType").value = ev;  //默认的过滤网站类型
 }

function cptWebSiteCommonS(id, ev) {
	var cptWebSiteLen = 7;
	for ( var i = 1; i <= cptWebSiteLen; i++) {
		if (i != id) {
			document.getElementById("cptWebSiteS_" + i).className = "";
		} else {
			document.getElementById("cptWebSiteS_" + i).className = "cur";
		}
	}
	document.getElementById("sset.sourceWebType").value = ev;
 }
// 跟帖
function genTieCommon(ev,id) {
	var genTieLen = 2;
	for ( var i = 1; i <= genTieLen; i++) {
		if (i != id) {
			document.getElementById("genTieA_" + i).className = "";
		} else {
			document.getElementById("genTieA_" + i).className = "cur";
		}
	}
	document.getElementById("sset.commentSwitch").value = ev;
 }
// 属性
var attributeALen = 3;
function attributeACommonS( ev, id) {
 	for ( var i = 1; i <= attributeALen; i++) {
		if (i != id) {
			document.getElementById("attributeS_" + i).className = "";
		} else {
			document.getElementById("attributeS_" + i).className = "cur";
		}
	}
	document.getElementById("sset.contentPropertySwitch").value = ev;
 }

function opratePlbuttonS( ev,id ) {
	var opratePlbuttonLen = 3;
 	for ( var i = 1; i <= opratePlbuttonLen; i++) {
		if (i != id) {
			document.getElementById("opratePlbuttonS_" + i).className = "";
		} else {
			document.getElementById("opratePlbuttonS_" + i).className = "cur";
		}
	}
	document.getElementById("sset.batchOperateType").value = ev;
 }


function oprateDtbuttonS(ev,id) {
	var oprateDtbuttonLen = 3;
 	for ( var i = 1; i <= oprateDtbuttonLen; i++) {
		if (i != id) {
			document.getElementById("oprateDtbuttonS_" + i).className = "";
		} else {
			document.getElementById("oprateDtbuttonS_" + i).className = "cur";
		}
	}
	document.getElementById("sset.singleOperateType").value = ev;
 }


function oprateRefresh(ev,id,time) {
	var oprateRefreshLen = 6;
 	for ( var i = 1; i <= oprateRefreshLen; i++) {
		if (i != id) {
			document.getElementById("oprateRefresh_" + i).className = "";
		} else {
			document.getElementById("oprateRefresh_" + i).className = "cur";
		}
	}
	document.getElementById("sset.autoRefreshSwitch").value = ev;
	if(ev == 1){
		document.getElementById("sset.autoRefreshInterval").value = time;
	}else{
		document.getElementById("sset.autoRefreshInterval").value = 0;
	}
 }


//字体大小选择
function changeFontSize(ev,id){
	var count=2;
	for ( var i = 1; i <= count; i++) {
		if (i != id) {
			document.getElementById("font_size_type" + i).className = "";
		} else {
			document.getElementById("font_size_type" + i).className = "cur";
		}
	}
	document.getElementById("sset.fontSize").value = ev;
}
//信息列表页切换
function changeListS(ev,id){
	var listLen=2;
	for(var i=1;i<=listLen;i++){
		if (i != id) {
			document.getElementById("info_listS_" + i).className = "";
		} else {
			document.getElementById("info_listS_" + i).className = "cur";
		}
	}
	document.getElementById("sset.newlstSelect").value = ev;
}
function changeComblineFlagS(ev,id){
	var flgLen=2;
	for(var i=1;i<=flgLen;i++){
		if (i != id) {
			document.getElementById("combline_flgS_" + i).className = "";
		} else {
			document.getElementById("combline_flgS_" + i).className = "cur";
		}
	}
	document.getElementById("sset.sameContentMergeSwitch").value = ev;
}
//点击的不是显示操作时将他隐藏
document.onclick = function (event) {
	var e = event || window.event;
	var elem = e.srcElement || e.target;
	while (elem) {
		var reg = /^search/g;
		if (reg.test(elem.id)||elem.id=='syset_other_time') {
			return;
		}
		elem = elem.parentNode;
	}   
    //隐藏div的方法   
	$("#syset_other_time").hide();
}; 
function selectTimeOut(){
	var showStartTime = document.getElementById("sset.searchStartTime").value;
	var showEndTime = document.getElementById("sset.searchEndTime").value;
	var end_stamp = Date.parse(new Date(showEndTime.replace(/-/g, "/")));
	var start_stamp  = Date.parse(new Date(showStartTime.replace(/-/g, "/")));
	if(start_stamp > end_stamp ){
		window.parent.parent.showMsgInfo(0,"开始时间不能大于结束时间",0);
		$("#syset_other_time").show();
		return ;	
	}else{
		$("#syset_other_time").hide();
	}
}
function switchToolbar(ev,id){
	var flgLen=2;
	for(var i=1;i<=flgLen;i++){
		if (i != id) {
			document.getElementById("switchToolbar_" + i).className = "";
		} else {
			document.getElementById("switchToolbar_" + i).className = "cur";
		}
	}
	document.getElementById("sset.toolbarDisplaySwitch").value = ev;
}
function subFormS(){
	var keywordId =  document.getElementById("kw.keywordId").value;
	if(document.getElementById("sset.keywordId").value==null || document.getElementById("sset.keywordId").value=="" || document.getElementById("sset.keywordId").value==0){
		document.getElementById("sset.keywordId").value = keywordId;
	}
	//初始值
	if (document.getElementById("sset.searchTimeQuantum").value==""){
		document.getElementById("sset.searchTimeQuantum").value = 1;
	}
	if (document.getElementById("sset.sortType").value==""){
		document.getElementById("sset.sortType").value=1;
	}
	if (document.getElementById("sset.commentSwitch").value==""){
		document.getElementById("sset.commentSwitch").value= 0;
	}
	if (document.getElementById("sset.contentPropertySwitch").value==""){
		document.getElementById("sset.contentPropertySwitch").value=0;
	}
	if (document.getElementById("sset.batchOperateType").value==""){
		document.getElementById("sset.batchOperateType").value = 0;
	}
	if (document.getElementById("sset.singleOperateType").value==""){
		document.getElementById("sset.singleOperateType").value = 0;
	}
	if (document.getElementById("sset.filterWebsiteType").value==""){
		document.getElementById("sset.filterWebsiteType").value = 0;
	}
	if (document.getElementById("sset.sourceWebType").value==""){
		document.getElementById("sset.sourceWebType").value = 0;
	}
	if (document.getElementById("sset.fontSize").value==""){
		document.getElementById("sset.fontSize").value = 1;
	}
	if (document.getElementById("sset.newlstSelect").value==""){
		document.getElementById("sset.newlstSelect").value = 2;
	}
	if(document.getElementById("sset.sameContentMergeSwitch").value==""){
		document.getElementById("sset.sameContentMergeSwitch").value = 1;
	}
	if(document.getElementById("sset.autoRefreshSwitch").value==""){
		document.getElementById("sset.autoRefreshSwitch").value = 0;
	}
	if(document.getElementById("sset.summarySwitch").value==""){
		document.getElementById("sset.summarySwitch").value = 1;
	}
	if (document.getElementById("sset.toolbarDisplaySwitch").value==""){
		document.getElementById("sset.toolbarDisplaySwitch").value = 0;
	}
	
	if (document.getElementById("sset.searchTimeQuantum").value==0){
		var El = document.getElementById("sset.searchStartTime");
		var starttime = "";
		var endtime = "";
		if (El.value==""){
			window.parent.parent.showMsgInfo(0,"请填入其他的搜索起始日期!",0);
			 	$("#syset_other_time").show();
			return;
		}
		else{
			starttime = El.value;
		}
		El = document.getElementById("sset.searchEndTime");
		if (El.value==""){
			window.parent.parent.showMsgInfo(0,"请填入其他的搜索结束日期!",0);
				$("#syset_other_time").show();
			 return;
		}
		else{
			endtime = El.value;
		}
		if (starttime > endtime){
			window.parent.parent.showMsgInfo(0,"开始时间不能大于结束时间",0);
			$("#syset_other_time").show();
			return;
		}
	}
	var ssetid = document.getElementById("sset.id").value;
	
	
	$.ajax({
        cache: true,
        type: "POST",
        url: "saveSearchset.action",
        data: $('#sysetForm').serialize(),// 你的formid
		dataType : 'json',
        
        success: function(msg) {
    	   isNeedReloadList= true;
    	   isSystemSet = true;
           parent.showMsgInfoToAction(0,"保存成功,列表将会重新进行搜索!",2,'keywordSearch.action?kw.keywordId='+keywordId);
           
           var timeDomain = document.getElementById("sset.searchTimeQuantum").value;
           var startDate = 0;
           var endDate = 0;
           var id = 1;
           if(timeDomain==0){
        	   startDate = document.getElementById("sset.searchStartTime").value;
        	   endDate = document.getElementById("sset.searchEndTime").value;
        	   id = 7;
           }else if(timeDomain==1){
        	   id = 1;
           }else if(timeDomain==24){
        	   id = 2;
           }else if(timeDomain==7){
        	   id = 3;
           }else if(timeDomain==30){
        	   id = 4;
           }else if(timeDomain==60){
        	   id = 5;
           }else if(timeDomain==90){
        	   id = 6;
           }
		   changeTimeDomainFlag(timeDomain,id,startDate,endDate);
		   
		   var st = document.getElementById("sset.sortType").value;
		   changPaixuFlag(st,st);

		   var scms = document.getElementById("sset.sameContentMergeSwitch").value;
		   changeComblineTypeFlag(scms,scms);
		   
		   var cps = document.getElementById("sset.contentPropertySwitch").value;
		   changeAttributeFlag(parseInt(cps)+1, cps);
		   
		   var nst = document.getElementById("sset.newlstSelect").value;
		   changeListTypeFlag(nst,nst);
		   
		   var summarySwitch = document.getElementById("sset.summarySwitch").value;
           changeZhaiyaoTypeFlag(summarySwitch,summarySwitch);
           
           var batchOperateType = document.getElementById("sset.batchOperateType").value;
           changeBathOption(batchOperateType);
           
           var toolbarSwitch = document.getElementById("sset.toolbarDisplaySwitch").value;
           changeToolbar(toolbarSwitch);
        }
    });
}
function changeZhaiyaoTypeFlag(v,id){
	$("#clickZhaiyaoShow").val(v);
	$("#zhaiyaoShow").val(v);
	var zhaiyaoCount = 2;
	for ( var i = 1; i <= zhaiyaoCount; i++) {
		if (i != id) {
			document.getElementById("zhaiyao_type_"+i).className="";
		} else {
			document.getElementById("zhaiyao_type_"+i).className="cur";
		}
	}
}
function changeComblineTypeFlag(v,id){
	var flgCount=2;
	$("#clickComblineflg").val(v);
	$("#comblineflg").val(v);
	for ( var i = 1; i <= flgCount; i++) {
		if (i != id) {
			document.getElementById("combline_flg_"+i).className="";
		} else {
			document.getElementById("combline_flg_"+i).className="cur";
		}
	}
}

//字体大小设置
function changeFontSizeFlag(id,v){
	var count=2;
	$("#clickFontSizeType").val(v);
	("#fontSizeType").val(v);
	var count=2;
	for ( var i = 1; i <= count; i++) {
		if (i != id) {
			document.getElementById("font_size_" + i).className = "";
		} else {
			document.getElementById("font_size_" + i).className = "cur";
		}
	}
}
//信息列表页切换
function changeListTypeFlag(id,v){
	var listLen=2;
	$("#newlstSelect").val(v);
	$("#clickNewlstSelect").val(v);
	for(var i=1;i<=listLen;i++){
		if (i != id) {
			document.getElementById("info_list_"+i).className="";
		} else {
			document.getElementById("info_list_"+i).className="cur";
		}
	}
}
//排序
function changPaixuFlag(id, v) {
	var paixuLen = 4;
	$("#clickPaixu").val(v);
	$("#paixu").val(v);
	for ( var i = 1; i <= paixuLen; i++) {
		if (i != id) {
			 document.getElementById("dateTimeA_"+i).className= "";
		} else {
			 document.getElementById("dateTimeA_"+i).className= "cur";
		}
	}
}
function changeAttributeFlag(id, v) {
	var attributeALen = 3;
	$("#otherAttribute").val(v);
	$("#clickOtherAttribute").val(v);
	for ( var i = 1; i <= attributeALen; i++) {
		if (i != id) {
			 document.getElementById("attributeA_"+i).className="";
		} else {
			  document.getElementById("attributeA_"+i).className="cur";
			 
		}
	}
}
function changeTimeDomainFlag(ev, id,startDate,endDate) {
	var lenA = 7;
	$("#clickTimeDomain").val(ev);
	$("#timeDomain").val(ev);
	for ( var i = 1; i <= lenA; i++) {
		if (i != id) {
			document.getElementById("selectTimeA_"+i).className="";
		} else {
			 document.getElementById("selectTimeA_"+i).className="cur";
		}
	}
	if ('0' == ev) {
		$("#starttime").val(startDate);
		$("#endtime").val(endDate);
		$("#showStarttime").val(startDate);
		$("#showEndtime").val(endDate);
	}
}
function changeBathOption(ev){
	if(ev==2){
		$(".bath_optation").css("display","none");
	}else{
		$(".bath_optation").css("display","");
	}
}
function changeToolbar(ev){
	if(ev==0){
		$('.fold1').stop().hide(); 
		$('.open1').stop().show();   //显示
	}else if(ev==1){
		$('.fold1').stop().show(); 
		$('.open1').stop().hide();   //隐藏
	}
	
}

