/**
 * lb
 * 
 * ���ܿ�
 * 
 */
function GetRandom(n){
	GetRandomn=Math.floor(Math.random()*n+1);
	return GetRandomn;
}
function changeZhaiyaoType(v,id){
	ifrmview.$("#zhaiyaoShow").val(v);
	ifrmview.$("#clickZhaiyaoShow").val(v);
	/*$("#clickZhaiyaoShow",window.parent.document).val(v);
	$("#zhaiyaoShow",window.parent.document).val(v);*/
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
	ifrmview.changeZhaiyaoType(v, 1);
}

function changeComblineFlag(v,id){
	
	var flgCount=2;
	ifrmview.$("#comblineflg").val(v);
	ifrmview.$("#clickComblineflg").val(v);
	/*$("#clickComblineflg",window.parent.document).val(v);
	$("#comblineflg",window.parent.document).val(v);*/
	$("#clickComblineflg").val(v);
	$("#comblineflg").val(v);
	for ( var i = 1; i <= flgCount; i++) {
		if (i != id) {
			 

			document.getElementById("combline_flg_"+i).className="";
		} else {
			document.getElementById("combline_flg_"+i).className="cur";
		}
	}
	ifrmview.changeComblineFlg(v, 1);
}

//�����С����
function changeFontSize(v,id){
	var count=2;
	ifrmview.$("#fontSizeType").val(v);
	ifrmview.$("#clickFontSizeType").val(v);
/*	$("#clickFontSizeType",window.parent.document).val(v);
	$("#fontSizeType",window.parent.document).val(v);*/
	$("#clickFontSizeType").val(v);
	("#fontSizeType").val(v);
	for ( var i = 1; i <= count; i++) {
		if (i != id) {
			$("#font_size_" + i).css( {
				"color":"#a1a1a1",
				"background-color":"#FFFFFF"
			});
		} else {
			$("#font_size_" + i).css( {
				/*"background-color" : "#93c92b",
				"color":"#FFFFFF"*/
				"background-color" : "#FFFFFF",
				"color":"#93c92b"
			});
		}
	}
	ifrmview.changeFontSizeType(v, 1);
}
//��Ϣ�б�ҳ�л�
function changeList(id,v){
	
	var listLen=2;
	ifrmview.$("#newlstSelect").val(v);
	ifrmview.$("#clickNewlstSelect").val(v);
	/*$("#clickNewlstSelect",window.parent.document).val(v);
	$("#newlstSelect",window.parent.document).val(v);*/
	$("#newlstSelect").val(v);
	$("#clickNewlstSelect").val(v);
	for(var i=1;i<=listLen;i++){
		if (i != id) {
			document.getElementById("info_list_"+i).className="";
		} else {
			document.getElementById("info_list_"+i).className="cur";
		}
	}
	ifrmview.changeNewlstSelect(1);
}
//��ת��ƫ������ҳ��
function goSystemSet(){
	addTab('Ĭ�ϼ������','searchset.action');
}
//��ת����Դ��վ��������
function goCptWebSite(){
	addTab('��Դ��վ����','websiteTypeList.action');
}
// ����
function parXuCommon(id, v) {
	
	var paixuLen = 4;
	ifrmview.$("#paixu").val(v);
	ifrmview.$("#clickPaixu").val(v);
	/*$("#clickPaixu",window.parent.document).val(v);
	$("#paixu",window.parent.document).val(v);*/
	$("#clickPaixu").val(v);
	$("#paixu").val(v);
	for ( var i = 1; i <= paixuLen; i++) {
		if (i != id) {
		 

			 document.getElementById("dateTimeA_"+i).className= "";
		} else {
			 document.getElementById("dateTimeA_"+i).className= "cur";
		}
	}
	ifrmview.changeSort(1);
}

function piPeiTypeCommon(id) {
	for ( var i = 1; i <= 2; i++) {
		if (i != id) {
			$("#pipeiTypeA_" + i).css( {
				"color":"#a1a1a1",
				"background-color":"#FFFFFF"
			});
		} else {
			$("#pipeiTypeA_" + i).css( {
				/*"background-color" : "#93c92b",
				"color":"#FFFFFF"*/
				"background-color" : "#FFFFFF",
				"color":"#93c92b"
			});
		}
	}
	ifrmview.changePipeiType(id);
}




// ��Դ��վ
function cptWebSiteCommon(id, v) {
	var cptWebSiteLen = $("#cptWebSiteLen").val();
	ifrmview.$("#duplicateShow").val(v);
	ifrmview.$("#clickDuplicateShow").val(v);
	/*$("#clickDuplicateShow",window.parent.document).val(v);
	$("#duplicateShow",window.parent.document).val(v);*/
	$("#duplicateShow").val(v);
	$("#clickDuplicateShow").val(v);
	for ( var i = 1; i <= cptWebSiteLen; i++) {
		if (i != id) {
			$("#cptWebSiteA_" + i).css( {
				"color":"#a1a1a1",
				"background-color":"#FFFFFF"
			});
		} else {
			$("#cptWebSiteA_" + i).css( {
				/*"background-color" : "#93c92b",
				"color":"#FFFFFF"*/
				"background-color" : "#FFFFFF",
				"color":"#93c92b"
			});
		}
	}
	ifrmview.changeDuplicate(v, 1);
}

// ����
var genTieLen = 2;
function genTieCommon(id, isShow) {
	for ( var i = 1; i <= genTieLen; i++) {
		if (i != id) {
			$("#genTieA_" + i).css( {
				"color":"#a1a1a1",
				"background-color":"#FFFFFF"
			});
		} else {
			$("#genTieA_" + i).css( {
			/*	"background-color" : "#93c92b",
				"color":"#FFFFFF"*/
				"background-color" : "#FFFFFF",
				"color":"#93c92b"
			});
		}
	}
	ifrmview.changeGengtie(isShow);
}

// �������� id,����(-1��ʾ����������),λ��(0��ʾ����,1��ʾ����)
function operateACommon(id, type, postion, account) {
	if (id == 4) {// �Ǹ���
		if (postion == 0) {// ����
			ifrmview.TagNews(4, 0, account);
		} else {// ����
			ifrmview.TagNewsSingle(4, 0, account);
		}
	} else if (id == 5) {// ����
		ifrmview.showSmsSendFrame(postion);
	}
}
function showSmsSend(ids){
	$("#send_sms_frame").attr("src","pre_send_sms.action?checkedIds="+ids);
	$("#BgDiv").css({display:"block", height:$(document).height()});
	var yscroll = document.documentElement.scrollTop;
	parent.$("#DialogDiv").css("display", "block");
	parent.document.documentElement.scrollTop = 0;
	parent.document.body.scrollTop =0;

 
}
function aMouseImg(id, imgSrc) {
	// $("#"+id).attr({"src":imgSrc});
}
// ����
var attributeALen = 3;
function attributeACommon(id, v) {
	
	ifrmview.$("#otherAttribute").val(v);
	ifrmview.$("#clickOtherAttribute").val(v);
	/*$("#clickOtherAttribute",window.parent.document).val(v);
	$("#otherAttribute",window.parent.document).val(v);*/
	$("#otherAttribute").val(v);
	$("#clickOtherAttribute").val(v);
	for ( var i = 1; i <= attributeALen; i++) {
		if (i != id) {
			 document.getElementById("attributeA_"+i).className="";
			 
		} else {
			  document.getElementById("attributeA_"+i).className="cur";
			 
		}
	}
	ifrmview.changeDuplicate(1);
}

// ����Ĭ��ʱ��
function setDefaultLenLoadCommon() {
	// Ĭ��ʱ��
	var defaultDaytime = 1;
	var defaultTime = '${search_default_time}';
	var date = new Date().getTime();
	if (defaultTime == 'today') {
		var today = new Date();
		var todayTime = new Date((today.getFullYear() + "-" + today.getMonth()
				+ "-" + today.getDate() + " 00:00:00").replace(/-/g, "/"))
				.getTime();
		date = date - todayTime;
	} else {
		if (defaultTime == '24h') {
			defaultDaytime = 1;
		} else if (defaultTime == 'week') {
			defaultDaytime = 6;
		} else if (defaultTime == '10days') {
			defaultDaytime = 9;
		} else if (defaultTime == '15days') {
			defaultDaytime = 14;
		} else if (defaultTime == 'month') {
			defaultDaytime = 30;
		}
		date = date - defaultDaytime * 24 * 3600 * 1000;
	}

	if ($.trim($("#starttime").val()) == '') {

		$("#starttime").val(new Date(date).format("yyyy-MM-dd hh:mm:ss"));
		$("#endtime").val(
				new Date(new Date().getTime()).format("yyyy-MM-dd hh:mm:ss"));

		$("#showStarttime").val(new Date(date).format("yyyy-MM-dd hh:mm:ss"));
		$("#showEndtime").val(
				new Date(new Date().getTime()).format("yyyy-MM-dd hh:mm:ss"));
	} else {
		var showStarttime = $("#starttime").val();
		var showEndtime = $("#endtime").val();
		//$("#showStarttime").val(showStarttime.substring(0, 10));
		//$("#showEndtime").val(showEndtime.substring(0, 10));
	}
}

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
function getToDay() {
	var now = new Date();
	var nowYear = now.getFullYear();
	var nowMonth = (now.getMonth() + 1);
	var nowDate = now.getDate();
	if (nowMonth < 10) {
		nowMonth = "0" + nowMonth;
	}
	if (nowDate < 10) {
		nowDate = "0" + nowDate;
	}
	return nowYear + "-" + nowMonth + "-" + nowDate + " 00:00:00";
}
//op 0 - ͼ�� , 1 - ����
function selectTimeSubmit(ev, id,op) {
	
	var lenA = 7;
	ifrmview.$("#clickTimeDomain").val(ev);
	ifrmview.$("#timeDomain").val(ev);
	
	var start_time = $("#start_time").val();
	var end_time = $("#end_time").val();
	if(op == 1){
		//document.getElementById("defaultLen").value= ev;
	}
	
	for ( var i = 1; i <= lenA; i++) {
		if (i != id) {

			document.getElementById("selectTimeA_"+i).className="";
			//$("#selectTimeA_" + i).attr({className: ''});
		} else {
			 document.getElementById("selectTimeA_"+i).className="cur";
			//$("#selectTimeA_" + i).attr({className: 'cur'});
		}
	}
	//����
	if ('0' == ev) {
		$("#clickTimeDomain").val(ev);
		$("#other_time").show();
		$("#timeDomain").val(ev);
		if(!start_time && typeof(start_time)!="undefined" && start_time!=0){
			$("#starttime").val(start_time);
		}else{
			$("#start_time").val($("#starttime").val());
		}
		if(!end_time && typeof(end_time)!="undefined" && end_time!=0){
			$("#endtime").val(end_time);
		}else{
			$("#end_time").val($("#endtime").val());
		}
	} else {
		if(op == 1){
			//ifrmview.$("#selectOtherTime").val(0);
			//$("#dataParam").val(id);
			//$("#defaultLen").val(ev);
			$("#timeDomain").val(ev);
		}else if(op == 0){
			//$("#selectOtherTime").val(0);
			//$("#dataParam").val(id);
			//$("#defaultLen").val(ev);
			$("#timeDomain").val(ev);
		}
		var now = new Date().getTime();
		var end = 0;
		var isHourSeach = false;
		if ('24' == ev || '1' == ev) {// 24Сʱ ���߽���
			if ('1' == ev) {// ����
				end = getToDay();
			} else {
				end = now - 1 * 24 * 3600 * 1000;
			}
			isHourSeach = true;
			$("#isHourSeach").val(true);
		} else {
			$("#isHourSeach").val(false);
			if ('7' == ev) {// 1��
				end = now - 6 * 24 * 3600 * 1000;
			} else if ('10' == ev) {// 10��
				end = now - 9 * 24 * 3600 * 1000;
			} else if ('15' == ev) {// ����
				end = now - 14 * 24 * 3600 * 1000;
			} else if ('30' == ev) {// 1��
				end = now - 29 * 24 * 3600 * 1000;
			}  else if ('60' == ev) {// 2��
				end = now - 59 * 24 * 3600 * 1000;
			} else if ('90' == ev) {// 3��
				end = now - 89 * 24 * 3600 * 1000;
			}else if ('365' == ev) {// ��
				end = now - 364 * 24 * 3600 * 1000;
			}
		}
		$("#starttime").val(new Date(end).format("yyyy-MM-dd 00:00:00"));
		$("#endtime").val(new Date(now).format("yyyy-MM-dd hh:mm:ss"));
		if (ev == '1') {
			$("#starttime").val(end);
		} else if(ev=='24'){
			$("#starttime").val(new Date(end).format("yyyy-MM-dd hh:mm:ss"));
			$("#endtime").val(new Date(now).format("yyyy-MM-dd hh:mm:ss"));
		}
		if (end >= now) {
			alert("��ʼʱ�䲻�ܴ��ڻ���ڽ���ʱ��");
			return;
		}
		
		$("#clickTimeDomain").val(ev);
		$("#timeDomain").val(ev);
		//$("#starttime").val(starttime);
		//$("#endtime").val(endtime);
	 	subForm();
	}
}

// ����ҳ����Ĭ������ʱ��
function setStartAndEndTime() {
	var date = new Date($("#starttime").val().replace(/-/g, "/")).getTime();
	var enddate = new Date($("#endtime").val().replace(/-/g, "/")).getTime();
	$("#showStarttime").val(new Date(date).format("yyyy-MM-dd hh:mm:ss"));
	//$("#showEndtime").val(new Date(new Date().getTime()).format("yyyy-MM-dd"));
	$("#showEndtime").val(new Date(enddate).format("yyyy-MM-dd hh:mm:ss"));
}


function selectTimeOver() {
	$("#other_time").show();
}

function selectTimeOut() {
	$("#other_time").hide();
}

function showSubForm() {
	if($("#showStarttime").val()=="undefined" || $("#showStarttime").val()==null || $("#showStarttime").val()==""){
		window.parent.showMsgInfo(0,"������������ʼʱ��",0);
		return;
	}
	if($("#showEndtime").val()=="undefined" || $("#showEndtime").val()==null || $("#showEndtime").val()==""){
		window.parent.showMsgInfo(0,"��������������ʱ��",0);
		return;
	}
	
	ifrmview.$("#selectOtherTime").val(1);

	$("#starttime").val($("#showStarttime").val());
	$("#endtime").val($("#showEndtime").val());
	
   // $("#start_time").val($("#starttime").val());
   // $("#end_time").val($("#endtime").val());
    
	 //ʱ���
	var end_stamp = Date.parse(new Date($("#showEndtime").val().replace(/-/g, "/")));
	var start_stamp  = Date.parse(new Date($("#showStarttime").val().replace(/-/g, "/")));
	if(start_stamp > end_stamp ){
		//alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
		window.parent.showMsgInfo(0,"��ʼʱ�䲻�ܴ��ڽ���ʱ��",0);
		return ;	
	}
	$("#clickTimeDomain").val(0);
	$("#timeDomain").val(0);
	subForm();
}