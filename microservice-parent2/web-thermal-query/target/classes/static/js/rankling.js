/**
 * Created by xujing on 2017/6/19 0019.
 */

var noThreereClassify = '股票、汽车、美妆、金融、电脑、家电、食品、教育、人物、地域、娱乐';
function secondaryClassification(rankingName,id,obj){
    var url = basePath+"/rankLevelList.action";
    $(obj).parent().find("li").removeClass("active");
    $(obj).addClass("active");
    $("#searchName").val("");
    $("#parentId").val(id);
    $.ajax({
        url : url,
        type : "post",
        data:{'reclassify':id},
        success : function(data){
            if(data){
                var html;
                $("#rankingName").text(rankingName+"榜");
                console.log(rankingName);
                console.log(noThreereClassify);
                console.log(noThreereClassify.indexOf(rankingName));
                for(var i = 0 ; i < data.length; i++) {
                        if (data[i].typeName == '全部') {
                            html += '<label class="btn" onclick="allClick(' + data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\'' + ',' + 1 + ')"><input type="radio" name="options1" id="option11">' + data[i].typeName + '</label>';
                        }else if(noThreereClassify.indexOf(rankingName) != -1){
                            html += '<label class="btn" onclick="allClick(' + data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\'' + ',' + 1 + ')"><input type="radio" name="options1" id="option11">' + data[i].typeName + '</label>';
                        } else {
                            html += '<label class="btn" onclick="threeClassification(' + data[i].id + ',this)"><input type="radio" name="options1" id="option11">' + data[i].typeName + '</label>';

                        }
                }
                $("#smallClassUl").html(html);
                $("#smallClassUl").find("label:eq(0)").click();
            }
        }
    });
}

function threeClassification(id,obj){
    if($(obj).hasClass("active")){
        return;
    }
    $("#rankTypeId1").val(id);
    var url = basePath+"/rankLevelList.action";
    $.ajax({
        url : url,
        type : "post",
        data:{'reclassify':id},
        success : function(data){
            if(data){
                var html;
                for(var i = 0 ; i < data.length; i++) {
                    if (i == 0) {
                        html = '<label class="btn" onclick="allClick(' + data[i].parentId + ',' + data[i].id +',\''+data[i].typeName +'\''+','+2+')"><input type="radio" name="options1" id="option11">' + data[i].typeName + '</label>';
                    } else {
                        html += '<label class="btn" onclick="allClick(' + data[i].parentId + ',' + data[i].id + ',\'' + data[i].typeName + '\'' + ','+2+')"><input type="radio" name="options1" id="option11">' + data[i].typeName + '</label>';
                    }
                }
                $("#threeClassUl").html(html);
                tipsLoading("rankList");
            }
        }
    });
}

function allClick(parentId,id,classifyName,type){
    chartLoading("rankList");
    var url = basePath+"/rankClassifyList.action";
    if(type == 1){
        $("#threeClassUl").html("");
        $("#rankTypeId1").val(id);
        $("#rankTypeId2").val(0);
    }else {
        $("#rankTypeId2").val(id);
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

    $.ajax({
        url : url,
        type : "post",
        data:data,
        success : function(data){
            $("#rankList").html(data);
        }
    });
}

function goRanking(){
    var url = basePath+"/rankClassifyList.action";
    var data = {};
    data.parentId = $("#parentId").val();
    data.rankTypeId1 = $("#rankTypeId1").val();
    data.searchName = $("#searchName").val();
    data.sort = $("#sort").val();
    data.order = $("#order").val();
    data.page = $("#page").val();
    data.pagesize = $("#pagesize").val();
    data.classifyName = $("#classifyName").val();
    data.sort = $("#sort").val();
    data.order = $("#order").val();
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

function addHotAnalysis(type){
    var checkID = [];
    $.each($("#sharesList li"),function(n){
        if($(this).hasClass("active")){
            checkID.push(n);
        }
    });
    if(checkID.length>2){
        alert("最多选择2个！");
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
            categoryType += $("#type_" + this).val() + ",";
        }
    });
    title = title.replace(/,$/,"");
    keyword = keyword.replace(/,$/,"");
    filterKeyword = filterKeyword.replace(/,$/,"");
    if(type == 2) {
        categoryId = categoryId.replace(/,$/, "");
        categoryType = categoryType.replace(/,$/, "");
    }
    if($("#userId").length!=0){
        $("#title").val(title);
        $("#keyword").val(keyword);
        $("#filterKeyword").val(filterKeyword);
        $("#num").val(checkID.length);
        if(type == 2) {
            $("#categoryId").val(categoryId);
            $("#categoryType").val(categoryType);
            $("#secondCategory").val($("#paramValue").val());
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

function findKeyword(type,obj,page){
    $("#rankingName").text("热点榜");
    $("#hotOrRank").val(1);
    $("#smallClassUl").next("label").remove();
    $("#smallClassUl").parent().find(".sma").remove();
    var html = '<label id="nationwideLabel" class="btn btn-w" onclick="findKeyword(1,this);"><input type="radio" name="options" id="option1" value="1"> 全部</label><label id="provinceLabel" class="btn btn-w" onclick="findKeyword(2,this);"><input type="radio" name="options" id="option1" value="2">'+ $("#provinceIp").val() +' </label>';
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

function openCompare(n){
    $("#sharesList li").removeClass("active");
    $("#sharesList li:eq("+n+")").addClass("active");
    $("#checkNum").text(1);
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
    $("#categoryType").val(types);
    $("#secondCategory").val($("#paramValue").val());
    $("#num").val(1);
    $("#categoryLevel").val($("#level").val());
    goSearch();
}

function goSearch(){
    var kv = $("#keyword").val();
    if($.trim(kv)==""||$.trim(kv)=="请输入您想查询的关键字"){
        showMsgInfo(0,"请输入你想搜索的词语",0);
        return ;
    }
    if($.trim(kv).length==1){
        showMsgInfo(0,"请至少输入2个字符",0)
        return ;
    }
    if($("#title").val()==""){
        $("#title").val(kv);
    }
	var form = document.getElementById("searchForm");
	form.action = basePath+"/goSearch.shtml";
	form.submit();
}

function chartLoading(id){
    var stat = document.getElementById(id);
    stat.innerHTML = '<div class="spinner" style="display: block;"><div class="bounce1"></div></div><br/><div style="font-size:12px;color:#999999;height: 50px;" align="center">系统拼命加载中，不要离开，马上呈现～</div>';
}

function tipsLoading(id) {
    $("#"+id).html( "<div class= 'no_data clear'><img  width = '60' height = '62' src = '"+ njxBasePath +"/images/user/logo_hs.png'><p style = \"font-size: 14px;\" > 请继续选择下一级分类 ！</p></div >");
}
