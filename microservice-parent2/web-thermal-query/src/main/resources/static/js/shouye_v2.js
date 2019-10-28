/**
 * Created by ????? on 2016/9/2.
 */
//0:??? 1?????? 2?????? 3????? 4????? 5?????? 6?????? 7?????? 8????? 9???? 10??????
/*var categoryParams = ['','???,????','???????','???????/???','???????','','????','???????','???????','??????','???????'];
var smallClassArr = ['????A?ɡ?????A?ɡ?????B?ɡ?????B?ɡ???С?塢?????','??????????????и?????????????','????????????γ???????',
    '','??????????????','','???С??????????????','??????????','?????????????????????','?????????????????????','??????????'];*/
function toggleCategory(type,rankId,obj,paramName){
    $("#hotOrRank").val(2);
    $("#province").val("");
    var currentPage = $("#currentPageCode").val();
    if(currentPage&&currentPage==1){
        var txt = "??"
        if(type==9||type==10||type==11||type==13||type==14||type==15){
            txt = "????";
        }
        $("#rankingName").text($.trim($(obj).text())+txt);
    }
    $("#type").val(type);
    $(obj).parent().find("li").removeClass("active");
    $(obj).addClass("active");
    $("#middleClass").val("");
    $("#smallClass").val("");
    $("#stockType").val("");
    $("#starProfession").val("");
    $("#starSex").val("");
    $("#carTag").val("");
    $("#computerTag").val("");
    $("#celebrityTag").val("");
    $("#appliancesTag").val("");
    $("#searchName").val("");
    $("#page").val(1);
    $("#sort").val(3);
    $("#order").val(1);
    $("#paramName").val(paramName);
    $("#paramValue").val("");
    /*var params = categoryParams[type];
    if(params!=""){
        var paramsArr = params.split("/");
        $("#category").val(paramsArr[0]);
        if(paramsArr[1]&&paramsArr[1]!=""){
            $("#smallClass").val(paramsArr[1]);
        }
    }
    var middleClass = middleClassArr[type];
    if(middleClass!=""){
        $("#middleClass").val(middleClass);
    }*/
    $("#smallClassUl").next("label").remove();
    $("#smallClassUl").parent().find(".sma").remove();
    var smallClass = smallClassArr[rankId];
    var paramClass=paramValueStr[rankId];
    if(smallClass!=""){
        var classArr = smallClass.split("??");
        var paramArr= paramClass.split(",");
        $("#paramValue").val(paramArr[0].split("(")[0]);
        var classHtml = '';
        var classHtmlz = '';
        var param="'"+paramArr[0]+"'";
        if(currentPage&&currentPage==1){//???а?
            if(classArr[0].indexOf("(")==-1){
            	classHtml += '<label class="btn active" onclick="allClick('+type+','+param+')"><input type="radio" name="options1" id="option11"> ???</label>';
            	$.each(classArr,function(n){
            		classHtml+= '<label class="btn" onclick="smallCalssClick('+type+','+paramArr[n+1]+',this)"><input type="radio" name="options1" id="option12">'+this+'</label>';
            	});
            }else{
            	classHtmlz+= '<label>??? <select name="" class="btn btn-gray" onchange="smallCalssChenge(this)">';
            	var paramthis2="'"+classArr[0].split("(")[1].split(")")[0];
            	classHtmlz+=	'<option value="'+paramArr[0]+','+paramthis2+'">???</option>';
            	$.each(classArr,function(n){
            		var pramthis="'"+this.split("(")[1].split(")")[0];
            		classHtmlz+=	'<option value="'+paramArr[n+1]+','+pramthis+'">'+this.split("(")[0]+'</option>';
            	})
            	classHtmlz+='</select></label>';
            	classHtmlz+= '<label class="sma ml20">???? <select name="" class="btn btn-gray" onchange="smallThirdChenge(this)">';
				$.each(paramArr[0].split("(")[1].split(")")[0].split("??"),function(n){
					classHtmlz+=	'<option value="'+this+'">'+classArr[0].split("(")[1].split(")")[0].split(",")[n]+'</option>';
				})
            	classHtmlz+='</select></label>';
    			$("#smallClassUl").after(classHtmlz);
            }
        }else{
        	if(classArr[0].indexOf("(")==-1){
        		classHtml += '<li class="active" onclick="allClick('+type+','+param+')">???</li>';
        		$.each(classArr,function(n){
        			classHtml+= '<li onclick="smallCalssClick('+type+','+paramArr[n+1]+',this)">'+this+'</li>';
        		});
        	}
        }
        $("#smallClassUl").html(classHtml);
        if(paramArr[0]==paramArr[1]){
            $("#smallClassUl label:eq(1),#smallClassUl li:eq(1)").addClass("active");
            $("#smallClassUl label:first,#smallClassUl li:first").remove();
        }
    }else{
        $("#smallClassUl").html("");
    }
    getRank();
}

function smallCalssChenge(obj){
	var value=obj.value.split("'")[0];
	var ne=obj.value.split("'")[1];
	var classHtmlz='';
	classHtmlz+= '<label class="sma ml20">???? <select name="" class="btn btn-gray" onchange="smallThirdChenge(this)">';
	var classArr=value.split("(")[1].split(")")[0].split("??");
	$.each(classArr,function(n){
		classHtmlz+=	'<option value="'+this+'">'+ne.split(",")[n]+'</option>';
	})
	classHtmlz+='</select></label>'
	$("#smallClassUl").next("label").next("label").remove();
	$("#smallClassUl").next("label").after(classHtmlz);


	$("#searchName").val("");
	$("#page").val(1);
	$("#sort").val(3);
	$("#order").val(1);
	$("#paramValue").val(value.split("(")[0]);
	getRank();
}

function smallThirdChenge(obj){
	$("#page").val(1);
    $("#sort").val(3);
    $("#order").val(1);
	var value=$("#paramValue").val();
	$("#paramValue").val(value+"#"+obj.value);
	getRank();
	$("#level").val(obj.value);
	$("#paramValue").val(value);
}

function smallCalssClick(type,i,obj){
    $("#searchName").val("");
    $("#page").val(1);
    $("#sort").val(3);
    $("#order").val(1);
    $("#smallClassUl li").removeClass("active");
    obj = $(obj);
    obj.addClass("active");
    var txt = obj.text();
    $("#paramValue").val(i);
    getRank();

}

/*function allClick(type,pv){
    $("#searchName").val("");
    $("#page").val(1);
    $("#sort").val(3);
    $("#order").val(1);
    $("#smallClassUl li").removeClass("active");
    var obj = $("#smallClassUl li:eq(0)");
    obj.addClass("active");
    $("#smallClass").val("");
    $("#stockType").val("");
    $("#starProfession").val("");
    $("#starSex").val("");
    $("#carTag").val("");
    $("#computerTag").val("");
    $("#celebrityTag").val("");
    $("#appliancesTag").val("");
    $("#paramValue").val(pv);
    getRank();
}*/

function getRank(){//1:???????? 2:???????? 3:???????? 4:???????? 5:??????? 6:???????? 7:????????? 8:????????(iis???) 9:????????? 10:???????? 11:???????? 12:???????? 13:???????? 14:??????? 15:??????? 16:?????????
	chartLoading("rankList");
    var type = $("#type").val();
    var category = $("#category").val();
    var middleClass = $("#middleClass").val();
    var smallClass = $("#smallClass").val();
    var paramName=$("#paramName").val();
    var paramValue=$("#paramValue").val();
    var data = {};
    var url = basePath+"/view/hotSearch/rankList.action";

    /*if(type==2){
        if($("#stockType").val()!=""){
            data.stockType=$("#stockType").val();
        }
    }else if(type==3){
        if($("#celebrityTag").val()!=""){
            data.celebrityTag=$("#celebrityTag").val();
        }
    }else if(type==9){
        if($("#carTag").val()==""){
            $("#carTag").val(1);
        }
        data.carTag = $("#carTag").val();
    }else if(type==13){
        if($("#computerTag").val()==""){
            $("#computerTag").val(1);
        }
        data.computerTag = $("#computerTag").val();
    }else if(type==14){
        if($("#appliancesTag").val()==""){
            $("#appliancesTag").val(1);
        }
        data.appliancesTag = $("#appliancesTag").val();
    }*/
    if(paramName!=""){
    	data.paramName=paramName;
    }
    if(paramValue!=""){
    	if(paramName.split("#").length==paramValue.split("#").length){
    		data.paramValue=paramValue;
    	}else{
    		data.paramValue=paramValue+"#null";
    	}
    }
    data.type = type;
    data.page = $("#page").val();
    data.pagesize = $("#pagesize").val();
    data.currentPageCode = $("#currentPageCode").val();
    data.searchName = $("#searchName").val();
    data.sort = $("#sort").val();
    data.order = $("#order").val();
    $.ajax({
        url : url,
        type : "post",
        data:data,
        success : function(data){

            $("#rankList").html(data);

        }
    })
    var desc = "???а?_"+ $.trim($("#categoryUl .active").text());
    if($("#smallClassUl:visible").length==1){
        desc += "_"+$.trim($("#smallClassUl .active:last").text());
    }
    recordUserLog(operateLogPageCode,desc);
}

function rankToSearch(n){
    var title = $("#name_"+n).val();
    var keyword = $("#keyword_"+n).val();
    var filterKeyword = $("#filterKeyword_"+n).val();
    var categoryIds=$("#categoryIds_"+n).val();
    var types=$("#types_"+n).val();
    if('undefined'==keyword){
        keyword = "";
    }else{
        keyword = keyword.replace(/\r\n/g,"");
    }
    if('undefined'==filterKeyword){
        filterKeyword = "";
    }else{
        filterKeyword = filterKeyword.replace(/\r\n/g,"");
    }
    $("#title").val(title);
    $("#keyword").val(keyword);
    $("#filterKeyword").val(filterKeyword);
    $("#categoryId").val(categoryIds);
    $("#categoryType").val($("#rankTypeId2").val());
    $("#secondCategory").val($("#rankTypeId1").val());
    $("#num").val(1);
    $("#categoryLevel").val($("#parentId").val());
    /*goSearch(2);*/
    goSearch();
}

function addKeyword(n){
    if($("#userId").length!=0){
        $("#recordName").val($("#name_"+n).val());
        $("#recordKeyword").val($("#keyword_"+n).val());
        $("#recordFilterKeyword").val($("#filterKeyword_"+n).val());
        $("#recordType").val(1);
        $("#loginRecordForm").submit();
    }else{
        var data = {"notLoginOperateRecord.operateType":1,"notLoginOperateRecord.name":$("#name_"+n).val(),"notLoginOperateRecord.keyword":$("#keyword_"+n).val(),"notLoginOperateRecord.filterKeyword":$("#filterKeyword_"+n).val()}
        $.ajax({
            url : basePath+"/view/hotSearch/recordOperateInfo.action",
            type : "post",
            data : data,
            success : function(){$("#login").click()}
        })
    }

}

function addHotAnalysis(type){
    var checkID = [];
    $.each($("#sharesList li"),function(n){
       /* n+=1;*/
        if($(this).hasClass("active")){
            checkID.push(n);
        }
    });
    if(checkID.length>2){
        alert("??????2????");
        return;
    }
    var title = "";
    var keyword="";
    var filterKeyword="";
    var categoryId="";
    var categoryType="";
    $.each(checkID,function(n){
        title += $("#names_"+this).val()+",";
        keyword += $("#keywords_"+this).val()+",";
        filterKeyword+=$("#filterKeywords_"+this).val()+",";
        if(type == 2) {
            categoryId += $("#categoryId_" + this).val() + ",";
        }
    });
    title = title.replace(/,$/,"");
    keyword = keyword.replace(/,$/,"");
    filterKeyword = filterKeyword.replace(/,$/,"");
    if(type == 2) {
        categoryId = categoryId.replace(/,$/, "");
    }
    if($("#userId").length!=0){
        $("#title").val(title);
        $("#keyword").val(keyword);
        $("#filterKeyword").val(filterKeyword);
        $("#num").val(checkID.length);
        if(type == 2) {
            $("#categoryId").val(categoryId);
            $("#categoryLevel").val($("#parentId").val());
            $("#categoryType").val($("#rankTypeId2").val());
            $("#secondCategory").val($("#rankTypeId1").val());
        }
        var form = document.getElementById("searchForm");
//        if(type==2){
//        	form.action = basePath+"/view/hotSearch/goTwoSearch.action";
//        }else{
        	form.action = basePath+"/goSearch.shtml";
//        }
        form.submit();
    }else{
        var data = {"notLoginOperateRecord.operateType":15,"notLoginOperateRecord.name":title,"notLoginOperateRecord.keyword":keyword,"notLoginOperateRecord.filterKeyword":filterKeyword,"notLoginOperateRecord.categoryId":categoryId,"notLoginOperateRecord.categoryType":categoryType,"notLoginOperateRecord.num":checkID.length};

        $.ajax({
            url : basePath+"/view/hotSearch/recordOperateInfo.action",
            type : "post",
            data : data,
            success : function(){
                $("#sharesModal .close").click();
                $("#login").click();
            }
        })
    }
}

function goSearch(){
    var kv = $("#keyword").val();
    if($.trim(kv)==""||$.trim(kv)=="??????????????????"){
        showMsgInfo(0,"???????????????????",0);
        return ;
    }
    if($.trim(kv).length==1){
        showMsgInfo(0,"??????????2?????",0)
        return ;
    }
    if($("#title").val()==""){
        $("#title").val(kv);
    }
    var form = document.getElementById("searchForm");
    form.action = basePath+"/goSearch.shtml";
    form.submit();
}

function mtSearch(){
    var kv = $("#title").val();
    if($.trim(kv)=="????????????????????????????"){
        showMsgInfo(0,"????????????!",0);
        return;
    }

    if($.trim(kv).length>20){
        showMsgInfo(0,"????????????20????!",0);
        return;
    }

    kv = $.trim(kv).replace(/\s+/g,"+");

    $("#keyword").val(kv);
    $("#filterKeyword").val("");
    goSearch();
}

function enterClick(event){
    event = event || window.event;
    if(event.keyCode==13){
        mtSearch();
    }
}

function hotSearch(title,keyword,filterKeyword){
    if('undefined'==keyword){
        keyword = "";
    }else{
        keyword = keyword.replace(/\r\n/g,"");
    }
    if('undefined'==filterKeyword){
        filterKeyword = "";
    }else{
        filterKeyword = filterKeyword.replace(/\r\n/g,"");
    }
    $("#title").val(title);
    $("#keyword").val(keyword);
    $("#filterKeyword").val(filterKeyword);
    $("#categoryId").val("");
    $("#categoryType").val("");
    $("#secondCategory").val("");
    /*goSearch(3);*/
    goSearch();
}

function getHotKeywords(){
    $.ajax({
        url : basePath+"/view/hotSearch/hotKeywords.action",
        type : "get",
        success : function(data){
            if(data&&data instanceof Array){
                data = data[0];
                var html = '';//<div><a href="javascript:refreshHotKeywords()"><i class="icon-repeat"></i> ?????</a></div>
                $.each(data,function(){
                    html += '<a href="javascript:hotSearch(\''+this.incidentTitle+'\',\''+this.keyword+'\',\''+this.filterKeyword+'\')">'+this.incidentTitle;
                    if(this.type==1){
                        html += '<em class="newLabel">??</em>';
                    }else if(this.type==2){
                        html += '<em class="newLabel red">??</em>';
                    }else if(this.type==3){
                        html += '<em class="newLabel blue">??</em>';
                    }
                    html += '</a>';
                });
                $("#hotKeyword").html(html);
            }
        }
    })
}

function refreshHotKeywords(){
    window.clearInterval(intervalId);
    getHotKeywords();
    intervalId = setInterval(function(){getHotKeywords()},1000*60*10);
}

function openCompare(n){
    $("#sharesList li").removeClass("active");
    $("#sharesList li:eq("+n+")").addClass("active");
    $("#checkNum").text(1);
    /*if($("#sharesList li").length==1){
        $("#sharesModal .btn").addClass("btn-disabled").removeClass("btn-warning");
    }*/
}

var timeout="";
function thinkKeywords(obj){
    $("#keyWordLogs_ul").hide();
    var txt = $(obj).val();
    if(txt==""){
        return;
    }
    if(timeout!=""){
        window.clearTimeout(timeout);
    }
    timeout = setTimeout(function(){beginThink(txt)},1000);
}

function beginThink(txt){
    var data = {"thinkKeyword":txt};
    $.ajax({
        url : basePath+"/view/hotSearch/thinkKeywords.action",
        type : "post",
        data:data,
        success : function(data){
            if(data&&data instanceof Array){
                data = data[0];
                if(data==null){
                    $("#hotwords").hide();
                }
                var html = '';
                $.each(data,function(){
                    html += '<li style="cursor:pointer;" onclick="hotSearch(\''+this.title+'\',\''+this.keyword+'\',\''+this.filterKeyword+'\')"><a href="javascript:void(0)"><h1>'+this.title+'</h1></a>';
                    if(this.type==1){
                        html += '<em>???????';
                    }else if(this.type==2){
                        html += '<em>??????';
                    }else if(this.type==3){
                        html += '<em>????????';
                    }else if(this.type==4){
                        html += '<em>???????';
                    }else if(this.type==5){
                        html += '<em>???????';
                    }else if(this.type==6){
                        html += '<em>????????';
                    }else if(this.type==7){
                        html += '<em>???????';
                    }else if(this.type==8){
                        html += '<em>???????';
                    }else if(this.type==9){
                        html += '<em>????????';
                    }else if(this.type==10){
                        html += '<em>???????';
                    }else if(this.type==11){
                        html += '<em>???????';
                    }else if(this.type==12){
                        html += '<em>???????';
                    }else if(this.type==13){
                        html += '<em>???????';
                    }else if(this.type==14){
                        html += '<em>??????';
                    }else if(this.type==15){
                        html += '<em>??????';
                    }else if(this.type==16){
                        html += '<em>???????';
                    }
                    if(!this.category||this.category==""||this.category=="undefined"){
                        html += '</em></li>';
                    }else{
                        html += '??'+this.category+'??</em></li>';
                    }

                });
                $("#hotwords").html(html);
                $("#hotwords").show();
            }
        }
    })
}

function chartLoading(id){
    var stat = document.getElementById(id);
    stat.innerHTML = '<div class="spinner" style="display: block;"><div class="bounce1"></div></div><br/><div style="font-size:12px;color:#999999;height: 50px;" align="center">??????????У????????????????</div>';


}

//????????????????????????
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
    //????div?????
    $("#keyWordLogs_ul").hide();
    $("#hotwords").hide();
};

function checkLi(obj){
       var checkID = [];
       $.each($("#sharesList li"),function(n){
           if($(this).hasClass("active")){
               checkID.push(n);
           }
       });
       var len = checkID.length;
       if($(obj).hasClass("active")){
           len -= 1;
       }else{
           len += 1;
       }
       if(len<1){
           $("#sharesModal .btn").addClass("btn-disabled").removeClass("btn-warning");
       }else{
           $("#sharesModal .btn").addClass("btn-warning").removeClass("btn-disabled");
       }
}

function recordUserLog(code,name){
    $.ajax({
        url : basePath+"/view/hotSearch/recordUserLog.action",
        type : "post",
        data:{'operateLogObject.productPageCode':code,'operateLogObject.productPageDesc':name},
        success : function(data){

        }
    })
}

//************************************** ?°????а?  XUJING**************************************
function findKeyword(type,obj,page){
	$("#secondSearchId").hide();
    $("#rankingName").text("????");
    $("#hotOrRank").val(1);
    $("#smallClassUl").next("label").remove();
    $("#smallClassUl").parent().find(".sma").remove();
    $("#multiUl").html("");
    $("#threeMultiUl").html("");
    //var html = '<label id="nationwideLabel" class="btn btn-w" onclick="findKeyword(1,this);"><input type="radio" name="options" id="option1" value="1"> ???</label><label id="provinceLabel" class="btn btn-w" onclick="findKeyword(2,this);"><input type="radio" name="options" id="option1" value="2">'+ $("#provinceIp").val() +' </label>';
    var html = '<label id="nationwideLabel" class="btn btn-w" onclick="findKeyword(1,this);"><input type="radio" name="options" id="option1" value="1"> ???</label>';
    $("#smallClassUl").html(html);
    chartLoading("rankList");
    $(obj).parent().find("li").removeClass("active");
    $(obj).addClass("active");
    $("#smallClassUl").find("label").removeClass("active");

    if(type == 1){
        if(!$.trim($("#searchName").val())){
            $("#province").val("");
        }else{
            $("#province").val($.trim($("#searchName").val()));
        }
        $("#nationwideLabel").addClass("active");
    }else if(type == 2){
        $("#province").val($("#provinceIp").val());
        $("#provinceLabel").addClass("active");
    }

    $.ajax({
        url:actionBase + "/heatKeywords.action",
        type:"post",
        data:{
            "flag":type,
            'province':$("#province").val(),
            'page' : page
        },
        success:function(data){
            $("#rankList").html(data);
        }
    });
}

var noThreereClassify = '????????????????????????????硢??????????????????????';

$(function(){
	$('#searchName').keydown(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			searchRank();
		}
	});
})

function secondaryClassification(rankingName,id,isAll,obj){
        var url = basePath+"/rankLevelList.action";
        if(obj){
        	$(obj).parent().find("li").removeClass("active");
        	$(obj).addClass("active");
        }
        $("#smallClassUl").html("");
        $("#threeClassUl").html("");
        $("#searchName").val("");
        $("#parentId").val(id);
        $.ajax({
            url : url,
            type : "post",
            data:{'reclassify':id},
            success : function(data){
                if(data){
                    var html;
                    $("#rankingName").text(rankingName+"??");
                    if(rankingName == '????' || rankingName == '????'){
                        $("#smallClassUl").html("");
                        $("#threeMultiUl").html("");
                        for(var i = 0 ; i < data.length; i++) {
                            if(i == 0){
                                $("#secondClassifyName").val(data[i].typeName);
                                html = '<a style="background-color: white;" href="javascript:void(0);" class="btn btn-default btn-select active dropdown-toggle fc_orange w150" data-toggle="dropdown"><span id="className">'+data[i].typeName+'</span><i class="icon-arrow_drop_down"></i></a>';
                                html += '<ul class="dropdown-menu" style="max-height: 200px; overflow: hidden;overflow-y:auto;">';
                                if(data[i].typeName == '???'){
                                    html += '<li id="selectTypeID'+i+'" class="active" onclick="allClick(' + isAll + ','+ data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\'' + ',' + 1 + ','+ 2 +','+i+')"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse'+i+'"></span></li>';
                                }else{
                                    html += '<li id="selectTypeID'+i+'" onclick="threeClassification(' + isAll + ',' + data[i].id + ',\''+data[i].typeName+ '\','+2+',this)"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse'+i+'"></span></li>';
                                }
                            }else if(data[i].typeName == '???'){
                                html += '<li onclick="allClick(' + isAll + ','+ data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\'' + ',' + 1 +  ','+ 2 +','+i+')"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse'+i+'"></span></li>';
                            }else{
                                html += '<li id="selectTypeID'+i+'" onclick="threeClassification(' + isAll + ',' + data[i].id + ',\''+data[i].typeName+ '\','+ 2 + ',this,'+i+')"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse'+i+'"></span></li>';
                            }
                        }
                        html += '</ul>';
                        $("#multiUl").html(html);
                        $("#multiUl").find("ul li:eq(0)").click();
                    }else{
                    	$("#smallClassUl").html("");
                        $("#threeMultiUl").html("");
                        for(var i = 0 ; i < data.length; i++) {
                            if(i == 0){
                                $("#secondClassifyName").val(data[i].typeName);
                                html = '<a style="background-color: white;" href="javascript:void(0);" class="btn btn-default btn-select active dropdown-toggle fc_orange w150" data-toggle="dropdown"><span id="className">'+data[i].typeName+'</span><i class="icon-arrow_drop_down"></i></a>';
                                html += '<ul class="dropdown-menu" style="max-height: 200px; overflow: hidden;overflow-y:auto;">';
//                                 console.log(data[i].typeName);
//                                 if(data[i].typeName == '???'){
                                    html += '<li id="selectTypeID'+i+'" class="active" onclick="allClick(' + isAll + ','+ data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\'' + ',' + 1 + ','+ 2 +','+i+')"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse'+i+'"></span></li>';
//                                 }else{
//                                     html += '<li id="selectTypeID'+i+'" onclick="threeClassification(' + isAll + ',' + data[i].id + ',\''+data[i].typeName+ '\','+2+',this)"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse"'+i+'></span></li>';
//                                 }
                            }else if(data[i].typeName == '???'){
                                html += '<li id="selectTypeID'+i+'" class="active" onclick="allClick(' + isAll + ','+ data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\'' + ',' + 1 +  ','+ 2 +','+i+')"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse'+i+'"></span></li>';
                            }else{
                                html += '<li id="selectTypeID'+i+'" onclick="allClick(' + isAll + ','+ data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\'' + ',' + 1 +  ','+ 1 +','+i+')"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse'+i+'"></span></li>';
                            	
                            }
                        }
                        html += '</ul>';
                        $("#multiUl").html(html);
                        $("#multiUl").find("ul li:eq(0)").click();
                    }
                }
            }
        });
    }
    function allClick(isAll,parentId,id,classifyName,type,classType,n){
    	if(type==1){
			$("#multiUl").find("ul li").attr("class"," ");
			$("#multiUl").find("ul span").attr("class"," ");
			
			$("#selectTypeID"+n).attr("class","active");
			$("#lastYse"+n).attr("class","icon icon-ok-a check-mark");
    	}else{
			$("#threeMultiUl").find("ul li").attr("class"," ");
			$("#threeMultiUl").find("ul span").attr("class"," ");
			
			$("#selectTypeID2"+n).attr("class","active");
			$("#lastYse2"+n).attr("class","icon icon-ok-a check-mark");
    		
    	}
//    	$("#className").html(classifyName);
    chartLoading("rankList");
    var url = basePath+"/rankClassifyList.action";
    $("#hotOrRank").val(2);
    if(classType&&classType == 2){
        if(classifyName.trim() == "???" && type == 1) {
            $("#className").text(classifyName);
            $("#threeMultiUl").html("");
        }else {
            $("#threeClassName").text(classifyName);
            $("#threeClassifyName").val(classifyName);
        }
    }else if(classType&&classType == 1){
        if($("#className").length > 0) {
            $("#className").text(classifyName);
        }
        $("#secondClassifyName").val(classifyName);
        $("#threeClassifyName").val("");
    }

    if(type&&type == 1){
        $("#threeClassUl").html("");
        $("#rankTypeId1").val(id);
        $("#rankTypeId2").val(0);
    }else {
        $("#rankTypeId2").val(id);
    }

    $("#isAll").val(isAll);
    if(isAll == 1){
        $("#rankTypeId1").val(id);
    }

    $("#classifyName").val(classifyName);
    $("#page").val(1);
    var data = {};
    data.parentId = $("#parentId").val();
    data.rankTypeId1 = $("#rankTypeId1").val();
    data.rankTypeId2 = $("#rankTypeId2").val();
    data.searchName = $("#searchName").val();
    data.sort = $("#sort").val();
    data.order = $("#order").val();
    data.page = $("#page").val();
    data.pagesize = $("#pagesize").val();
    data.classifyName = $("#classifyName").val();
    data.isAll = $("#isAll").val();

    $.ajax({
        url : url,
        type : "post",
        data:data,
        success : function(data){
            $("#rankList").html(data);
        }
    });
}


    function threeClassification(isAll,id,name,type,obj,n){
    	$("#multiUl").find("ul li").attr("class"," ");
		$("#multiUl").find("ul span").attr("class"," ");
		
		$("#selectTypeID"+n).attr("class","active");
		$("#lastYse"+n).attr("class","icon icon-ok-a check-mark");
//         if($(obj).hasClass("active")){
//             return;
//         }
        $("#hotOrRank").val(2);
        $("#rankTypeId1").val(id);
        if(type == 2){
            $("#className").text(name);
        }
        $("#secondClassifyName").val(name);
        var url = basePath+"/rankLevelList.action";
        $.ajax({
            url : url,
            type : "post",
            data:{'reclassify':id},
            success : function(data){
                if(data){
                    var html;
                    $("#threeClassUl").html("");
                    for(var i = 0 ; i < data.length; i++) {
                        if(i == 0){
                            html = '<a style="background-color: white;" href="javascript:void(0);" class="btn btn-default btn-select active dropdown-toggle fc_orange w150" data-toggle="dropdown"><span id="threeClassName">'+data[i].typeName+'</span><i class="icon-arrow_drop_down"></i></a>';
                            html += '<ul class="dropdown-menu" style="max-height: 200px; overflow: hidden;overflow-y:auto;">';
                            html += '<li id="selectTypeID2'+i+'" onclick="allClick(' + isAll + ',' + data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\',' + 2 + ',' + 2 +','+i+')"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse2'+i+'"></span></li>';
                        }else{
                            html += '<li id="selectTypeID2'+i+'" onclick="allClick(' + isAll + ',' + data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName +  '\',' + 2 + ',' + 2 +','+i+ ')"><a href="javascript:void(0);">'+data[i].typeName+'</a><span id="lastYse2'+i+'"></span></li>';
                        }
                    }
                    html += '</ul>';
                    $("#threeMultiUl").html(html);
                    $("#threeMultiUl").find("ul li:eq(0)").click();
                }
            }
        });
    }

function goRanking(){
    var url = basePath+"/rankClassifyList.action";
    var data = {};
    data.parentId = $("#parentId").val();
    data.rankTypeId1 = $("#rankTypeId1").val();
    data.rankTypeId2 = $("#rankTypeId2").val();
    data.searchName = $("#searchName").val();
    data.sort = $("#sort").val();
    data.order = $("#order").val();
    data.page = $("#page").val();
    data.pagesize = $("#pagesize").val();
    data.classifyName = $("#classifyName").val();
    data.isAll = $("#isAll").val();
    /*data.currentPageCode = $("#currentPageCode").val();*/
    $.ajax({
        url : url,
        type : "post",
        data:data,
        success : function(data){
            $("#rankList").html(data);
        }
    });
}

function tipsLoading(id) {
    $("#"+id).html( "<div class= 'no_data clear'><img  width = '60' height = '62' src = '"+ njxBasePath +"/images/user/logo_hs.png'><p style = \"font-size: 14px;\" > ????????????????? ??</p></div >");
}