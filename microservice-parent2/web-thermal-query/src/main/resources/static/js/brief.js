function getOs() 
{ 
    var OsObject = ""; 
   if(navigator.userAgent.indexOf("MSIE")>0) { 
        return "MSIE"; 
   } 
   if(navigator.userAgent.indexOf("Firefox")>0){ 
        return "Firefox"; 
   } 
   if(navigator.userAgent.indexOf("Chrome")>0){ 
        return "Chrome"; 
   } 
   if(navigator.userAgent.indexOf("Safari")>0) { 
        return "Safari"; 
   }  
} 
function SetCwinHeight(){
	var navType= getOs();
	var b_height=0;
	if (navType=="Chrome"||navType=="Safari"||navType=="Firefox"){
		b_height = document.documentElement.scrollHeight;
	} 
	else if(navType=="MSIE"){
		b_height = document.body.scrollHeight;
	}else{
		b_height = document.documentElement.scrollHeight;
	}
	b_height = b_height + 100;
	if (b_height < 600){
		b_height = 600;
	}
	var c_iframe1 = parent.document.getElementById('top_ifrmview');
	var top_height = c_iframe1.height;
	if (c_iframe1){
		c_iframe1.height = parseInt(b_height);
	}
}





function GetRandom(n){
	GetRandomn=Math.floor(Math.random()*n+1);
	return GetRandomn;
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
//����ҳ����Ĭ������ʱ��
function setStartAndEndTime() {
	var len=2;
	for(var i=0;i<len;i++){
		$("#showStarttime_"+i).val(new Date().format("yyyy-MM-dd 00:00:00"));
		$("#showEndtime_"+i).val(new Date().format("yyyy-MM-dd 23:59:59"));
		if($("#starttime_"+i).val()==''){
			$("#starttime_"+i).val($("#showStarttime_"+i).val());
		}
		if($("#endtime_"+i).val()==''){
			$("#endtime_"+i).val($("#showEndtime_"+i).val());
		}
	}
	
}
/** 
 * @param modle
 * modle=0:����б�
 * modle=1:����
 */
function showSubForm(modle) {
	$("#starttime_"+modle).val($("#showStarttime_"+modle).val());
	$("#endtime_"+modle).val($("#showEndtime_"+modle).val());
	 //ʱ���
	var end_stamp = Date.parse(new Date($("#showEndtime_"+modle).val().replace(/-/g, "/")));
	var start_stamp  = Date.parse(new Date($("#showStarttime_"+modle).val().replace(/-/g, "/")));
	if(start_stamp > end_stamp ){
        //alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��");
        showMsgInfo(0,"��ʼʱ�䲻�ܴ��ڽ���ʱ��",0);
        return ;
    }
	
	subForm(modle);
}
/** 
 * @param modle
 * modle=0:����б�
 * modle=1:����
 */
function selectTimeSubmit(id,modle,ev) {
	
	if($("#selectTimeA_" + modle + "_" + id).hasClass("locking")){
		return;
	}
	
	$("#clickTimeDomain_"+modle).val(ev);

	var lenA = 7;
	for ( var i = 1; i <= lenA; i++) {
		if (i != id) {
			$("#selectTimeA_"+modle+"_"+i).removeClass("cur");
		} else {
			$("#selectTimeA_"+modle+"_"+i).addClass("cur");
		}
	}
	var now = new Date().getTime();
	var end = 0;
	if(ev==-1){
		$("#taskTime_"+modle).show();
		return;
	}else if(ev==1){
		end = getToDay();
	}else if(ev==24){
		end = now - 1 * 24 * 3600 * 1000;
	}else if ('7' == ev) {// 1��
			end = now - 6 * 24 * 3600 * 1000;
	} else if ('10' == ev) {// 10��
		end = now - 9 * 24 * 3600 * 1000;
	} else if ('15' == ev) {// ����
		end = now - 15 * 24 * 3600 * 1000;
	} else if ('30' == ev) {// 1��
		end = now - 30 * 24 * 3600 * 1000;
	}  else if ('60' == ev) {// 2��
		end = now - 60 * 24 * 3600 * 1000;
	} else if ('90' == ev) {// 3��
		end = now - 90 * 24 * 3600 * 1000;
	}else if ('365' == ev) {// ��
		end = now - 365 * 24 * 3600 * 1000;
	}else if ('3' == ev){//3��
        end = now - 2 * 24 * 3600 * 1000;
    }else if ('2' == ev){//2��
        end = now - 1 * 24 * 3600 * 1000;
    }
	$("#starttime_"+modle).val(new Date(end).format("yyyy-MM-dd 00:00:00"));
	$("#endtime_"+modle).val(new Date(now).format("yyyy-MM-dd hh:mm:ss"));
	if (ev == 1) {
		$("#starttime_"+modle).val(end);
	} else if(ev==24){
		$("#starttime_"+modle).val(new Date(end).format("yyyy-MM-dd hh:mm:ss"));
		$("#endtime_"+modle).val(new Date(now).format("yyyy-MM-dd hh:mm:ss"));
	}
	subForm(modle);
}
//����

function attributeACommon(id,modle, v) {
	var attributeALen = 3;
	$("#clickOtherAttribute_"+modle).val(v);
	for ( var i = 1; i <= attributeALen; i++) {
		if (i != id) {
			 document.getElementById("attributeA_"+modle+"_"+i).className="";
			 
		} else {
			 document.getElementById("attributeA_"+modle+"_"+i).className="cur";
			 
		}
	}
	subForm(modle);
}
//����
function parXuCommon(id,modle, v) {
	var paixuLen = 4;
	$("#clickPaixu_"+modle).val(v);
	for ( var i = 1; i <= paixuLen; i++) {
		if (i != id) {
			 document.getElementById("dateTimeA_"+modle+"_"+i).className= "";
		} else {
			 document.getElementById("dateTimeA_"+modle+"_"+i).className= "cur";
		}
	}
	subForm(modle);
}

//ƥ�䷽ʽ
function solrTypeCommon(id,modle, v) {
	var solrTypeLen = 3;
	$("#solrType_"+modle).val(v);
	for ( var i = 1; i <= solrTypeLen; i++) {
		if (i != id) {
			 document.getElementById("solrType_"+modle+"_"+i).className= "";
		} else {
			 document.getElementById("solrType_"+modle+"_"+i).className= "cur";
		}
	}
	subForm(modle);
}
//��Ϣ�б�ҳ�л�
function changeList(id,modle,v){
	var listLen=2;
	$("#clickNewlstSelect_"+modle).val(v);
	for(var i=1;i<=listLen;i++){
		if (i != id) {
			document.getElementById("info_list_"+modle+"_"+i).className="";
		} else {
			document.getElementById("info_list_"+modle+"_"+i).className="cur";
		}
	}
	subForm(modle);
}
//�ϲ�
function changeComblineFlag(id,modle,v){
	var flgCount=2;
	$("#clickComblineflg_"+modle).val(v);
	for ( var i = 1; i <= flgCount; i++) {
		if (i != id) {
			document.getElementById("combline_flg_"+modle+"_"+i).className="";
		} else {
			document.getElementById("combline_flg_"+modle+"_"+i).className="cur";
		}
	}
	subForm(modle);
}
function changeOrigina(dateType,init) {
	var divNa = document.getElementById("na" + dateType);
	var modle = document.getElementById("modle").value;
    $("#cpage_"+modle).val(1);
	document.getElementById("clickFilterOrigina_"+modle).value = dateType;
	if (divNa) {
		divNa.className = "nas";
	}
	if (init == 1) {
		subForm(modle);
	}
}


/**************************************����*************************************************/
function getSearchPipeiType(){
	var seType = 0;
		var ras = document.getElementsByName("searchPipeiType");
			for (var i=0; i<ras.length;i++){
				var tmpE = ras[i];
				if (tmpE.checked){
					seType = tmpE.value;
					break;
				}
			}
	return seType;
}
function EnterPress(e) {
	var e = e || window.event; 
	if (e.keyCode == 13) {
		var kv = document.getElementById("searchKeyword").value;
		if($.trim(kv)==""||$.trim(kv)=="������ؼ���"){
			showMsgInfo(0,"������ؼ���",0);
			document.getElementById("searchKeyword").focus();
			document.getElementById("searchKeyword").value = "";
			return false;
		}
		var keyTitleV = kv;
		kv =  kv.replace(/\%/g, "%25");
		subForm(1);
		return false;
	}
}

function setSelectIdVal(ev) {
	$("#selectedId").val(ev);
}
function deleteSucai(userId){
	var modle = $("#modle").val();
	var checkedIds = document.getElementById("selectedId").value + ",";
	var timeDomain = 0;
	if(document.getElementById("clickTimeDomain_"+modle)!=null){
		timeDomain = document.getElementById("clickTimeDomain_"+modle).value;
	}
	var starttime = endtime = "";
	if(document.getElementById("starttime_"+modle)!=null){
		starttime = document.getElementById("starttime_"+modle).value;
	}
	if(document.getElementById("endtime_"+modle)!=null){
		endtime = document.getElementById("endtime_"+modle).value;
	}
	var keywordId=null;
	var keyword=null;
	if(document.getElementById("kw.keywordId")){
		var keywordId = document.getElementById("kw.keywordId").value;
	}
	if(keywordId==null||keywordId==""){
		if(document.getElementById("searchKeyword")){
			keyword=document.getElementById("searchKeyword").value;
		}
	}
	operateNews("deleteSingle",checkedIds, 2, userId,timeDomain,starttime,endtime,keywordId,keyword);
}
function importBriefNews(modle,importNewType, folderId, account) {
		if (importNewType == 2) {
			importToName = "���ز�";
			importType = 2;
		} else if (importNewType == 1) {
			importToName = "�ղؼ�";
			importType = 1;
		}
		var checkedIds = document.getElementById("selectedId").value + ",";
		var timeDomain = 0;
		var keywordId=null;
		var keyword=null;
		if(document.getElementById("kw.keywordId")){
			var keywordId = document.getElementById("kw.keywordId").value;
		}
		if(keywordId==null||keywordId==""){
			if(document.getElementById("searchKeyword")){
				keyword=document.getElementById("searchKeyword").value;
			}
		}
		if(document.getElementById("clickTimeDomain_"+modle)!=null){
			timeDomain = document.getElementById("clickTimeDomain_"+modle).value;
		}
		var starttime = endtime = "";
		if(document.getElementById("starttime_"+modle)!=null){
			starttime = document.getElementById("starttime_"+modle).value;
		}
		if(document.getElementById("endtime_"+modle)!=null){
			endtime = document.getElementById("endtime_"+modle).value;
		}
		if(modle==1 || modle == 0){
			operateNews("insert",checkedIds, importType, account,timeDomain,starttime,endtime,keywordId,keyword);
		}else if(modle==2){
			timeDomain = -1;
			operateNews("insertFromSC",checkedIds, importType, account,timeDomain,starttime,endtime,keywordId,keyword);
		}
}
function getCheckIDsNum() {
	var elements = document.getElementsByTagName("input");
	var checkedMenu = 0;
	var modle = document.getElementById("modle").value;
	for ( var i = 0; i < elements.length; i++) {
		var e = elements[i];
		if (e.id == "menuCheckId_"+modle) {
			if (e.checked == true) {
				checkedMenu = checkedMenu + 1;
			}
		}
	}
	return checkedMenu;
}

function getCheckIDs() {
	var elements = document.getElementsByTagName("input");
	var checkedIds = "";
	var modle = document.getElementById("modle").value;
	for ( var i = 0; i < elements.length; i++) {
		var e = elements[i];
		if (e.id == "menuCheckId_"+modle) {
			if (e.checked == true) {
				// checkedMenu = checkedMenu + 1;
				checkedIds = checkedIds + e.value + ",";
			}
		}
	}
	return checkedIds;
}
//ȫѡ
function checkAllMenuIE(el_name, parent) {
	var elements = document.getElementsByTagName("input");
	var parentChecked = parent.checked;
	var modle = document.getElementById("modle").value;
	el_name = el_name+"_"+modle;
	for ( var i = 0; i < elements.length; i++) {
		var e = elements[i];
		if (e.id == el_name) {
			e.checked = parentChecked;
			}
	}
}
//����ѡ�У�-1��ȫѡ��20��ǰ20��
//type=0 ������type=1������
function pickNews(num,userId,type){
	var modle = $("#modle").val();
	 if(type==1){
			importBriefNews(modle,2, 0, userId);
	}else if(type==0){
		var checkedMenu = getCheckIDsNum();
		var checkedIds = getCheckIDs();
		if (checkedMenu < 1) {
			showMsgInfo(0,"��ѡ����Ҫ�����زĿ������!",0);
			return ;
		}else{
			showMsgInfo(0,"����ѡ��" + checkedMenu + "�����ţ�ȷ�ϼ����زĿ���?",1);
			 $(".submitBtn",this.document).one("click",function(){
				 if(getCheckIDsNum() >= 1) {
				 	$("#selectedId").val(checkedIds);
				 	importBriefNews(modle,2, 0, userId);
				 	$("#selectedId").val("");
					 hideInfoDiv();
				 }
			});
		}
	}
}