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

function selectTimeSubmit(id,ev,init) {
	initHeight();
	var numDiv = document.getElementById("numname");
	for ( var i = 1; i <= 7; i++) {
		if (i != id) {
			$("#filterTime_" + i).css( {
				"color" : "#000000" ,
			});
		} else {
			$("#filterTime_" + i).css( {
				"color" : "#08B1FF"
			});
		}
	}
	var now = new Date().getTime();
	var today = new Date();
	var start = 0;
	var end  = "";
	var isHourSeach = false;
	if(init==0){
		if (0 == ev) {
			$("#other_time").show();
			numDiv.innerHTML = "";
		}else{
		
		if (24 == ev  ) {// 24小时 或者今天
			numDiv.innerHTML ="24小时";
			start = now - 1 * 24 * 3600 * 1000;
			isHourSeach = true;
			$("#isHourSeach").val(true);
		}else if(1 == ev ){
			numDiv.innerHTML ="今天";
			end = getToDay();
			start = now - 1 * 24 * 3600 * 1000;
			isHourSeach = true;
			$("#isHourSeach").val(true);
		}else {
			$("#isHourSeach").val(false);
			if (7 == ev) {// 1周
				numDiv.innerHTML ="1周";
				start = now - 7 * 24 * 3600 * 1000;
			} else if (30 == ev) {// 1月
				numDiv.innerHTML ="1月";
				start = now - 30 * 24 * 3600 * 1000;
			} else if (60 == ev) {// 年
				numDiv.innerHTML ="2月";
				start = now - 60 * 24 * 3600 * 1000;
			}else if (90 == ev) {// 年
				numDiv.innerHTML ="3月";
				start = now - 90 * 24 * 3600 * 1000;
			}
		}
		starttime = new Date(start).format("yyyy-MM-dd 00:00:00") ;  
		endtime = new Date(now).format("yyyy-MM-dd 23:59:59") ;
		var isHourSeach = $("#isHourSeach").val();
		if(ev==1){
			starttime = end;
		}
		if(ev==24){
			starttime = new Date(start).format("yyyy-MM-dd hh:mm:ss");
			endtime = new Date(now).format("yyyy-MM-dd hh:mm:ss");
		}
		//alert(starttime+"\n"+endtime);
		//时间戳
		var end_stamp = Date.parse(new Date(endtime.replace(/-/g, "/")));
		var start_stamp  = Date.parse(new Date(starttime.replace(/-/g, "/")));
		
		if(start_stamp > end_stamp ){
			alert("开始时间不能大于或等于结束时间");
			return ;	
		}
		
		if((end_stamp-start_stamp)/3600/1000/24 <= 3){
			$("#isHourSeach").val(true);
		}else{
			$("#isHourSeach").val(false);
		}
		$("#starttime").val(starttime);
		$("#endtime").val(endtime);
		
		$("#timeDomain").val(ev);
		$("#clickTimeDomain").val(ev);
		$("#clickTimeDomain",window.parent.document).val(ev);
		$("#timeDomain",window.parent.document).val(ev);
		//alert($("#timeDomain").val());
		//alert($("#starttime").val()+"\n"+$("#endtime").val())
		subForm();
		}
	}else if(init==1){
		if (0 == ev) {
			numDiv.innerHTML = "";
			showSubForm();
		}else{
		
		if (24 == ev  ) {// 24小时 或者今天
			numDiv.innerHTML ="24小时";
			start = now - 1 * 24 * 3600 * 1000;
			isHourSeach = true;
			$("#isHourSeach").val(true);
		}else if(1 == ev ){
			numDiv.innerHTML ="今天";
			end = getToDay();
			start = now - 1 * 24 * 3600 * 1000;
			isHourSeach = true;
			$("#isHourSeach").val(true);
		}else {
			$("#isHourSeach").val(false);
			if (7 == ev) {// 1周
				numDiv.innerHTML ="1周";
				start = now - 7 * 24 * 3600 * 1000;
			} else if (30 == ev) {// 1月
				numDiv.innerHTML ="1月";
				start = now - 30 * 24 * 3600 * 1000;
			} else if (60 == ev) {// 年
				numDiv.innerHTML ="2月";
				start = now - 60 * 24 * 3600 * 1000;
			}else if (90 == ev) {// 年
				numDiv.innerHTML ="3月";
				start = now - 90 * 24 * 3600 * 1000;
			}
		}
		starttime = new Date(start).format("yyyy-MM-dd 00:00:00") ;  
		endtime = new Date(now).format("yyyy-MM-dd 23:59:59") ;
		var isHourSeach = $("#isHourSeach").val();
		if(ev==1){
			starttime = end;
		}
		if(ev==24){
			starttime = new Date(start).format("yyyy-MM-dd hh:mm:ss");
			endtime = new Date(now).format("yyyy-MM-dd hh:mm:ss");
		}
		//alert(starttime+"\n"+endtime);
		//时间戳
		var end_stamp = Date.parse(new Date(endtime.replace(/-/g, "/")));
		var start_stamp  = Date.parse(new Date(starttime.replace(/-/g, "/")));
		
		if(start_stamp > end_stamp ){
			alert("开始时间不能大于或等于结束时间");
			return ;	
		}
		
		if((end_stamp-start_stamp)/3600/1000/24 <= 3){
			$("#isHourSeach").val(true);
		}else{
			$("#isHourSeach").val(false);
		}
		$("#starttime").val(starttime);
		$("#endtime").val(endtime);
		
		$("#timeDomain").val(ev);
		$("#clickTimeDomain").val(ev);
		$("#clickTimeDomain",window.parent.document).val(ev);
		$("#timeDomain",window.parent.document).val(ev);
		//alert($("#timeDomain").val());
		//alert($("#starttime").val()+"\n"+$("#endtime").val())
		subForm();
		}
	}
	
}
//点击的不是显示操作时将他隐藏
document.onclick = function(event) {
	var e = event || window.event;
	var elem = e.srcElement || e.target;
	while (elem) {
		var reg = /^search/g;
		if (reg.test(elem.id)) {
			return;
		}
		elem = elem.parentNode;
	}
	//隐藏div的方法   
	document.getElementById("other_time").style.display = "none";
};
function getOs(){ 
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
	if (navType=="Chrome"||navType=="Safari"){
		b_height = document.documentElement.scrollHeight;
	} 
	else{
		b_height = document.body.scrollHeight;
	}
	b_height = b_height + 120;
	if (b_height < 900){
		b_height = 900;
	}
	var c_iframe2 = parent.document.getElementById('iframe');
	var c_iframe3 = parent.parent.document.getElementById('top_ifrmview');
	if (c_iframe2){
		c_iframe2.height = parseInt(b_height);
	}
	if(c_iframe3){
		c_iframe3.height = parseInt(c_iframe2.height) + 20; 
	}
	//alert("top_ifrmview.height="+c_iframe3.height+"\niframe="+c_iframe2.height)
	//parent.document.getElementById('list_height').value=c_iframe2.height;
}

function initHeight(){
	var c_iframe2 = parent.document.getElementById('iframe');
	var c_iframe3 = parent.parent.document.getElementById('top_ifrmview');
	if (c_iframe2){
		c_iframe2.height = 1360;
	}
	if(c_iframe3){
		c_iframe3.height = 1380; 
	}
}
//initHeight();
/*//走势图
function goLineChart(){
		var kwid = document.getElementById("kw.keywordId").value;
		var stat = document.getElementById("satusTree1");
		stat.innerHTML = '<img src=\"images/loading.gif\">';
		var isHourSeach = $("#isHourSeach").val();
		var newlstSelect = $("#newlstSelect").val();
		starttime = $("#starttime").val();
		endtime = $("#endtime").val();
		ChatDwr.getLineChat('${admin.userId}',kwid,1,'',0,starttime,endtime,GetRandom(30),isHourSeach,0,'',newlstSelect,lineCallBack);  //2 表示查询的是热门关键字
}*/
function lineCallBack(data) {
	var c1 = document.getElementById("container1");
	var stat1 = document.getElementById("satusTree1");
	if (data==""||data==null||eval(data) ==null||eval(data)[0].data==null||eval(data)[0].data==""){
		//c1.innerHTML = "<br> <div align=\"center\">此时间段没有统计数据</div>";
		c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"images/shouye/warn.png\"><br/>此时间段没有统计数据</p></div>";
		stat1.innerHTML = '';
		return false;
	}
	else{
		var _chartColumn = lineChart('container1','spline',eval(data));
		
	}
	stat1.innerHTML = '';
}


/*//情感饼图
function goQGPieChart(){
		var kwid = document.getElementById("kw.keywordId").value;
		var stat2 = document.getElementById("satusTree2");
		stat2.innerHTML = '<img src=\"images/loading.gif\">';
		var isHourSeach = $("#isHourSeach").val();
		var newlstSelect = $("#newlstSelect").val();
		starttime = $("#starttime").val();
		endtime = $("#endtime").val();
		ChatDwr.getqgPieChat('${admin.userId}',kwid,1,'',0,starttime,endtime,0,GetRandom(30),'',newlstSelect,qgPieCallBack);
}*/
function qgPieCallBack(data) {
	var stat2 = document.getElementById("satusTree2");
	stat2.innerHTML = '';
	var c2 = document.getElementById("container2");
	if (data==null||data==""||data[0]==""){
		//c2.innerHTML = "<br> <div align=\"center\">此时间段没有统计数据</div>";
		c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"images/shouye/warn.png\"><br/>此时间段没有统计数据</p></div>";
		stat2.innerHTML = '';
		document.getElementById("num1").innerHTML = "0 ";
		document.getElementById("num2").innerHTML= "&nbsp; 0%";
		document.getElementById("num3").innerHTML= "&nbsp; 0%";
		return false;
	}
	else{
		var datav = eval(data[0]);
		document.getElementById("num1").innerHTML = data[1];
		document.getElementById("num2").innerHTML= "&nbsp;<font color='#e7312c'>"+ data[2]+"</font>";
		document.getElementById("num3").innerHTML= "&nbsp;"+ data[3];
		var _chartColumn20 = qgPieChart(datav[0].data);
	}
}

/*//来源类型饼图
function goPieChart(){
	var kwid = document.getElementById("kw.keywordId").value;
	var stat3 = document.getElementById("satusTree3");
	stat3.innerHTML = '<img src=\"images/loading.gif\">';
	var isHourSeach = $("#isHourSeach").val();
	var newlstSelect = $("#newlstSelect").val();
	starttime = $("#starttime").val();
	endtime = $("#endtime").val();
	ChatDwr.getPieChat('${admin.userId}',kwid,1,'',0,starttime,endtime,GetRandom(30),isHourSeach,'',newlstSelect,pieCallBack);
}*/
function pieCallBack(data) {
	var stat3 = document.getElementById("satusTree3");
	if (data==null||data==""){
		var c3 = document.getElementById("container3");
		//c3.innerHTML = "<br> <div align=\"center\">此时间段没有统计数据</div>";
		c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"images/shouye/warn.png\"><br/>此时间段没有统计数据</p></div>";
		stat3.innerHTML = '';
		return false;
	}
	data = eval(data);
	var _chartColumn3 = pieChart(data[0].data);
	stat3.innerHTML = '';
}

/*//媒体活跃度
function goColumnChart(){
		var kwid = document.getElementById("kw.keywordId").value;
		var stat4 = document.getElementById("satusTree4");
		stat4.innerHTML = '<img src=\"images/loading.gif\">';
		var isHourSeach = $("#isHourSeach").val();
		var newlstSelect = $("#newlstSelect").val();
		starttime = $("#starttime").val();
		endtime = $("#endtime").val();
		ChatDwr.getTreeChat('${admin.userId}',kwid,1,'',0,starttime,endtime,GetRandom(30),isHourSeach,'',newlstSelect,columnChartCallBack);
}*/
function columnChartCallBack(data){
	var stat4 = document.getElementById("satusTree4");
	stat4.innerHTML = '';
	var c4 = document.getElementById("container4");
	if (data==null||data==""||data.length==0){
		document.getElementById("container4").style.width = "917px";
		//c4.innerHTML = "<br> <div align=\"center\">此时间段没有统计数据</div>";
		c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"images/shouye/warn.png\"><br/>此时间段没有统计数据</p></div>";
		stat4.innerHTML = '';
		return false;
	}else{
		data= eval(data);
		var h = data[0].name.length ;
		if  (h>0){
			var hgt = 100 + (h-1)*60;
		 	if(hgt>917){
		 		hgt=917;
		 	}
			document.getElementById("container4").style.width = hgt + "px";
			var temp=data[0].name.toString();
			var array=temp.split(",");
			for(var i=0;i<array.length;i++){
				if(array[i].length>4){
					array[i]=array[i].substring(0,4);
				}
			}
			var _chartColumn1 = ColumnChart(array, data,temp.split(","));
		}
	}
}

/*//热点新闻
function goHotNewsList(){
	var tb = document.getElementById("relateTab");//获取Table
	dataLoading(tb);
	var kwid = document.getElementById("kw.keywordId").value;
	var newlstSelect = $("#newlstSelect").val();
	starttime = $("#starttime").val();
	endtime = $("#endtime").val();
	ChatDwr.getRelateList('${admin.userId}',kwid,1,'',0,starttime,endtime,GetRandom(30),'',newlstSelect,CallBackHotNewsList);
}*/
function CallBackHotNewsList1(data){
 	var tb = document.getElementById("relateTab");//获取Table
 	var addLength = tb.rows.length;
	 while (addLength>2){
		tb.deleteRow(addLength-1);
		addLength = tb.rows.length;
	 }
	var rowL = 2;
	if(data==null){
		
		newTrC = tb.insertRow(rowL); 
		newNameTD  = newTrC.insertCell(0);
		newNameTD.colSpan = "7";
		newNameTD.style.textAlign = "center";
		newNameTD.style.height = "150px"
		//newNameTD.innerHTML  = "<font color=\" \">你所关注的媒体暂无报道!</font>";
		newNameTD.innerHTML  = "<p style=\"display:inline;font-size: 14px\"><img src=\"images/shouye/warn.png\"><br/>此时间段暂无热点信息</p>";
		SetCwinHeight();
		return;
	}
	if(null!=data){
		var hotNewsList=data[0];
 		$("#highLightKeywords").val(data[1]);
	}
	
    if(null!=hotNewsList){
		for (var i=0; i<hotNewsList.length; i++ ){
			newTrC = tb.insertRow(rowL); 
			/* if (i%2==0){
				newTrC.style.backgroundColor= '#e6f0f9';
			}
			else{
				newTrC.style.backgroundColor= '#f6f9fb';
			} */
			newTrC.style.height = "40px";
			newNameTD  = newTrC.insertCell(0);
			newNameTD.innerHTML="";
			/* newNameTD.innerHTML = i+1 ; */
			newNameTD.innerHTML = "<img src=\"images/list/"+(i+1)+".png\">" ;
			newNameTD.style.width = "50px";
			newNameTD.className='rbcon';
		    newNameTD  = newTrC.insertCell(1);
			newNameTD.className='rbcontitle';
			var title = hotNewsList[i]["title"];
			//goView(title,id,kwid,titleHs,province,otherAttribute)
			newNameTD.innerHTML = "<a href=\"javascript:goView('原文','"+hotNewsList[i]["id"]+"','"+hotNewsList[i]["titleHs"]+"','"+hotNewsList[i]["province"]+"','0');\"  class=\"linkA\">"+hotNewsList[i]["title"] + "</a>";
			newNameTD  = newTrC.insertCell(2);
			newNameTD.className='rbcon';
			newNameTD.style.width = "50px";
			if (hotNewsList[i]!='0'){
				/* newNameTD.innerHTML= "<a href=\"javascript:goRbList('话题新闻列表','"+hotNewsList[i]["id"] + "');\">"+ hotNewsList[i]["num"]+"</a>"; */
				newNameTD.innerHTML= hotNewsList[i]["repeatNum"];
			}
			else{
				newNameTD.innerHTML='0';
			}
				newNameTD  = newTrC.insertCell(3);
				newNameTD.className='rbcon';
				newNameTD.style.width = "50px";


				if (hotNewsList[i]["negtive"]!='0'){
					newNameTD.innerHTML ="<font color=\"#ea5653\">" +hotNewsList[i]["negtive"]+"</font>";
				}
				else{
					newNameTD.innerHTML= hotNewsList[i]["negtive"];
				}
				newNameTD  = newTrC.insertCell(4);
				newNameTD.className='rbcon';
				newNameTD.style.width = "50px";
				if (hotNewsList[i]["sunegtive"]!='0'){
					newNameTD.innerHTML ="<font color=\"#b0ac00\">" +hotNewsList[i]["sunegtive"]+"</font>";
				}
				else{
					newNameTD.innerHTML= hotNewsList[i]["sunegtive"] ;
				}
				newNameTD  = newTrC.insertCell(5);
				newNameTD.className='rbcon';
				newNameTD.style.width = "50px";
				newNameTD.innerHTML= hotNewsList[i]["unnegtive"];
				newNameTD  = newTrC.insertCell(6);
				newNameTD.className='rbcon';
				newNameTD.style.width = "50px";
				if (hotNewsList[i]["negtiveflg"]=="负面"){
					newNameTD.style.color = "#E11E1E";
					newNameTD.innerHTML = "负面";
				}
				else if (hotNewsList[i]["negtiveflg"]=="正面"){
					 
					newNameTD.innerHTML = "非负面";
				}
			rowL++;
		}
	}
    SetCwinHeight();
 	/* var arrLinks = $(".relateTab a");
	arrLinks.each(function(){
		var s = $(this).text(),l=$(this).text().length,n=22; //设置字符长度为48个
		if(l>n){
			$(this).attr("title",s); //讲文本所有内容用a标签的title属性提示文本全部内容
			s=$(this).text(s.substring(0,n)+"...");
		}
	}); */
}
function CallBackHotNewsList2(data){
 	var tb = document.getElementById("relateTab");//获取Table
 	var addLength = tb.rows.length;
	 while (addLength>2){
		tb.deleteRow(addLength-1);
		addLength = tb.rows.length;
	 }
	var rowL = 2;
	if(data==null){
		
		newTrC = tb.insertRow(rowL); 
		newNameTD  = newTrC.insertCell(0);
		newNameTD.colSpan = "7";
		newNameTD.style.textAlign = "center";
		newNameTD.style.height = "150px"
		//newNameTD.innerHTML  = "<font color=\" \">你所关注的媒体暂无报道!</font>";
		newNameTD.innerHTML  = "<p style=\"display:inline;font-size: 14px\"><img src=\"images/shouye/warn.png\"><br/>此时间段暂无热点信息</p>";
		SetCwinHeight();
		return;
	}
	if(null!=data){
		var hotNewsList=data[0];
 		$("#highLightKeywords").val(data[1]);
	}
	
    if(null!=hotNewsList){
		for (var i=0; i<hotNewsList.length; i++ ){
			newTrC = tb.insertRow(rowL); 
			/* if (i%2==0){
				newTrC.style.backgroundColor= '#e6f0f9';
			}
			else{
				newTrC.style.backgroundColor= '#f6f9fb';
			} */
			newTrC.style.height = "40px";
			newNameTD  = newTrC.insertCell(0);
			newNameTD.innerHTML="";
			/* newNameTD.innerHTML = i+1 ; */
			newNameTD.innerHTML = "<img src=\"images/list/"+(i+1)+".png\">" ;
			newNameTD.style.width = "50px";
			newNameTD.className='rbcon';
		    newNameTD  = newTrC.insertCell(1);
			newNameTD.className='rbcontitle';
			var title = hotNewsList[i]["title"];
			newNameTD.innerHTML = "<a href=\"javascript:goView('原文','"+hotNewsList[i]["id"]+"');\"  class=\"linkA\">"+hotNewsList[i]["title"] + "</a>";
			newNameTD  = newTrC.insertCell(2);
			newNameTD.className='rbcon';
			newNameTD.style.width = "50px";
			if (hotNewsList[i]!='0'){
				/* newNameTD.innerHTML= "<a href=\"javascript:goRbList('话题新闻列表','"+hotNewsList[i]["id"] + "');\">"+ hotNewsList[i]["num"]+"</a>"; */
				newNameTD.innerHTML= hotNewsList[i]["num"];
			}
			else{
				newNameTD.innerHTML='0';
			}
				newNameTD  = newTrC.insertCell(3);
				newNameTD.className='rbcon';
				newNameTD.style.width = "50px";


				if (hotNewsList[i]["negtive"]!='0'){
					newNameTD.innerHTML ="<font color=\"#ea5653\">" +hotNewsList[i]["negtive"]+"</font>";
				}
				else{
					newNameTD.innerHTML= hotNewsList[i]["negtive"];
				}
				newNameTD  = newTrC.insertCell(4);
				newNameTD.className='rbcon';
				newNameTD.style.width = "50px";
				if (hotNewsList[i]["sunegtive"]!='0'){
					newNameTD.innerHTML ="<font color=\"#b0ac00\">" +hotNewsList[i]["sunegtive"]+"</font>";
				}
				else{
					newNameTD.innerHTML= hotNewsList[i]["sunegtive"] ;
				}
				newNameTD  = newTrC.insertCell(5);
				newNameTD.className='rbcon';
				newNameTD.style.width = "50px";
				newNameTD.innerHTML= hotNewsList[i]["unnegtive"];
				newNameTD  = newTrC.insertCell(6);
				newNameTD.className='rbcon';
				newNameTD.style.width = "50px";
				if (hotNewsList[i]["negtiveflg"]=="负面"){
					newNameTD.style.color = "#E11E1E";
					newNameTD.innerHTML = "负面";
				}
				else if (hotNewsList[i]["negtiveflg"]=="正面"){
					 
					newNameTD.innerHTML = "非负面";
				}
			rowL++;
		}
	}
    SetCwinHeight();
    /* var arrLinks = $(".relateTab a");
	arrLinks.each(function(){
		var s = $(this).text(),l=$(this).text().length,n=22; //设置字符长度为48个
		if(l>n){
			$(this).attr("title",s); //讲文本所有内容用a标签的title属性提示文本全部内容
			s=$(this).text(s.substring(0,n)+"...");
		}
	}); */
}

function dataLoading(tableObj){
	var addLength = tableObj.rows.length;
	while (addLength>2){
			tableObj.deleteRow(addLength-1);
			addLength = tableObj.rows.length;
	}
	var newTrC = tableObj.insertRow(2); 
	newTrC.style.height = "29px";

	newTrC.style.backgroundColor = "#ffffff";
	var newNameTD  = newTrC.insertCell(0);
 
	newNameTD.innerHTML = "<img src=\"images/loading.gif\">";
	newNameTD.style.paddingLeft = 15 ;
	for (var i=2; i<5; i++ ){
	   newTrC = tableObj.insertRow(i);
	   newNameTD  = newTrC.insertCell(0);
	}

}
function showSubForm(){
	if($("#showStarttime").val()=="undefined" || $("#showStarttime").val()==null || $("#showStarttime").val()==""){
		window.parent.parent.showMsgInfo(0,"请输入搜索开始时间",0);
		return;
	}
	if($("#showEndtime").val()=="undefined" || $("#showEndtime").val()==null || $("#showEndtime").val()==""){
		window.parent.parent.showMsgInfo(0,"请输入搜索结束时间",0);
		return;
	}
	$("#timeDomain").val(0);
	$("#clickTimeDomain").val(0);
	$("#clickTimeDomain",window.parent.document).val(0);
	$("#starttime").val($("#showStarttime").val());
	$("#endtime").val($("#showEndtime").val());
    
	$("#starttime",window.parent.document).val($("#showStarttime").val());
	$("#endtime",window.parent.document).val($("#showEndtime").val());
	subForm();
}
function subForm(){
	var kwid = document.getElementById("kw.keywordId").value;
	timeDomain= $("#timeDomain").val();
	var now = new Date().getTime();
	var end = 0;
	var stime = 0;
	if ( 24 == timeDomain  ) {// 24小时 或者今天
		stime = now - 1 * 24 * 3600 * 1000;
		$("#isHourSeach").val(true);
	}else if(1 == timeDomain ){
		end = getToDay();
		stime = now - 1 * 24 * 3600 * 1000;
		$("#isHourSeach").val(true);
	}else {
		$("#isHourSeach").val(false);
		if (7 == timeDomain) {// 1周
			stime = now - 7 * 24 * 3600 * 1000;
		} else if (10 == timeDomain) {// 10天
			stime = now - 10 * 24 * 3600 * 1000;
		} else if (15 == timeDomain) {// 半月
			stime = now - 15 * 24 * 3600 * 1000;
		} else if (30 == timeDomain) {// 1月
			stime = now - 30 * 24 * 3600 * 1000;
		}  else if (60 == timeDomain) {// 2月
			stime = now - 60 * 24 * 3600 * 1000;
		}  else if (90 == timeDomain) {// 3月
			stime = now - 90 * 24 * 3600 * 1000;
		} else if (365 == timeDomain) {// 年
			stime = now - 365 * 24 * 3600 * 1000;
		}
	}
	starttime = new Date(stime).format("yyyy-MM-dd 00:00:00");
	endtime = new Date(now).format("yyyy-MM-dd 23:59:59");
	if (timeDomain == 1) {
		starttime = end;
		endtime = new Date(now).format("yyyy-MM-dd 23:59:59");
	}else if(timeDomain==24){
		starttime = new Date(stime).format("yyyy-MM-dd hh:mm:ss") ;
		endtime = new Date(now).format("yyyy-MM-dd hh:mm:ss") ;
	}else if(timeDomain==0){
		starttime = $("#starttime").val();
		endtime = $("#endtime").val();
		$("#clickTimeDomain",window.parent.document).val(0);
	}
	//时间戳
	var end_stamp = Date.parse(new Date(endtime.replace(/-/g, "/")));
	var start_stamp  = Date.parse(new Date(starttime.replace(/-/g, "/")));
	
	if(start_stamp > end_stamp ){
		//alert("开始时间不能大于或等于结束时间");
		window.parent.showMsgInfo(0,"开始时间不能大于或等于结束时间",0);
		return ;	
	}
	
	if((end_stamp-start_stamp)/3600/1000/24 <= 3){
		$("#isHourSeach").val(true);
	}else{
		$("#isHourSeach").val(false);
	}
	$("#starttime").val(starttime);
	$("#endtime").val(endtime);
	
	$("#timeDomain",window.parent.document).val(timeDomain);
	$("#clickDomain",window.parent.document).val(timeDomain);
	$("#starttime",window.parent.document).val(starttime);
	$("#endtime",window.parent.document).val(endtime);
	goLineChart();
	goQGPieChart();
	goPieChart();
	goColumnChart();
	goHotNewsList();
}
function loadPage(init){
	
		var timeDomain = $("#timeDomain").val();
		var id =1;
		if(timeDomain==1){
			id=1;
		}else if(timeDomain==24){
			id=2;
		}else if (timeDomain==7){
			id=3;
		}else if(timeDomain==30){
			id=4;
		}else if(timeDomain==60){
			id=5;
		}else if(timeDomain==90){
			id=6;
		}else if(timeDomain==0){
			id=7;
			init = 1;
		}
		selectTimeSubmit(id,timeDomain,init);  //init=0初始化
	 
}