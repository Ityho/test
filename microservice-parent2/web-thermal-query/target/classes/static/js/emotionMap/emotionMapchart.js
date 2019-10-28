var warnHtml = '<div style="text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';
var warnHtml1 = '<div style="text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 154px;"><br><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';
var loaders = '<i class="icon-reload-a fa-spin"></i>';
function initEmotion(){
    for(var i=1;i<=6;i++){
		goEmotionMapCharts(i);
    }
}
/**
 * 加载
 * @param demo 需要隐藏的id
 */
function loading(demo) {
    $("#"+demo).loader('show',loaders);
    $("#table"+demo).loader('show',loaders);
}

/*情绪地图start*/

function goEmotionMapCharts(index){
	
	setTimeout(function(){
		try {
			emotionMapChartsAjax(index);
		}catch (e){
			console.log(e.message);
		}
	}, 8000*(index-1));
}

function emotionMapChartsAjax(index) {
	
	var keyword=$("#keyword"+index).val();
	var filterKeyword=$("#filterKeyword"+index).val();
	if('undefined'==keyword){
		keyword = "";
	}
	if('undefined'==filterKeyword){
		filterKeyword = "";
	}
	var demo = "mapChart" + index;
	var date = 24;
	var includeOthers = 0;
    $.ajax({
        url:actionBase+'/en/emotion/emotionMapCharts.action',
        type:'POST',
        data:{'keyword':keyword,'date':date,'filterKeyword':filterKeyword,'includeOthers':includeOthers},
        
        beforeSend:function(){
            
            for(var i=1;i<=7;i++) {
                loading(demo);
                loading("table" + demo);
            }
        },
        success:function (result) {
        	if(result!=null && result.map1 != null){
                callEmotionMapXZ(result.map1,demo);	//显著情绪
        	}else{
                for(var i=1;i<=7;i++) {
                    $("#" + demo).html(warnHtml);
                    $("#table" + demo).html(warnHtml);
                }
        	}
        }
    })
}
function callEmotionMapXZ(result,demo) {
    if(result!=null){
        emotionIndexTable(result[0],demo,null,1,2);	//top10
        emotionMapCharts(result,demo);	//显著情绪地图
    }
}

//top10
function emotionIndexTable(result,demo,emotion,type,home){
    var classz="";
    if (home==1){
        classz="table table-th-lg";
    }else{
        classz="table table2 table3 table-striped table-th-w";
    }
    if (result != null) {
        var html = '';
        html+='<table border="0" cellspacing="0" cellpadding="0" class="'+classz+'" width="100%">';
        html+='<thead>';
        html+='<tr>';
        html+='<th width="80px">排名</th>';
        html+='<th width="">地区</th>';
        if(emotion==null){
            html+='<th width="125px">微博总声量</th>';
        }else{
            html+='<th width="125px">微博声量</th>';
        }
        if(emotion==null) {
            html += '<th width="105px">显著情绪</th>';
        }else{
            html += '<th width="105px">情绪标签</th>';
        }
        html+='</tr>';
        html+='</thead>';
        html+='<tbody>';
        var index = 0;
        $.each(result, function (i, n) {
            if (index == 10 || n == null) {
                return true;
            }
            var province = "";
            var num = 0;
            if(type==1){
                province = n[0];
                num = formatNum(n[1]);
                emotion = n[2];
            }else{
            	province = n.name;
                num = n.value;
            }
            html += '<tr>';
            html += '<td class="num"><em>' + (index + 1) + '</em></td>';
            html += '<td>' + province + '</td>';
            html += '<td>' + num + '</td>';
            if (emotion == "愤怒")
                html += '<td><span class="badge red">愤怒</span></td>';
            if (emotion == "恐惧")
                html += '<td><span class="badge black">恐惧</span></td>';
            if (emotion == "喜悦")
                html += '<td><span class="badge yellow">喜悦</span></td>';
            if (emotion == "惊奇")
                html += '<td><span class="badge yellowGreen">惊奇</span></td>';
            if (emotion == "悲伤")
                html += '<td><span class="badge blue">悲伤</span></td>';
            if (emotion == "中性")
                html += '<td><span class="badge" style="background-color:#d6d6d6;">中性</span></td>';
            html += '</tr>';
            index++;
        });
        if (index==0){
            $("#table" + demo).html(warnHtml1);
        }
        html += '</tbody>';
        html += '</table>';
        //html += '<p style="text-align: left;margin-top: 5px;">仅展示我国34个省级行政区域（包括23个省，5个自治区，4个直辖市，以及香港，澳门2个特别行政区）的声量TOP10</p>';
        $("#table" + demo).html(html);
    }else{
        $("#table" + demo).html(warnHtml1);
    }
}

//显著情绪地图
function emotionMapCharts(data,demo){
    $("#"+demo).empty();
    $("#"+demo).removeAttr('_echarts_instance_');
	var emotions = data[1];
    var color_option = data[3];
	data[2].push({
		name: '南海诸岛',
		itemStyle: {
			normal: {
				borderColor: '#959595',
				areaColor: '#efefef', 
			}
		}
	});
    var myChart = echarts.init(document.getElementById(demo));
    var option = {
		tooltip : {
			trigger: 'item'
		},
		visualMap:{
			show:true,
			type: 'piecewise',
			orient: 'vertical',
			x:'left',
			y:'10',
			categories: data[1],
			inverse: true,
			pieces: [
				{value: 0, label: emotions[0], color: color_option.xy}, 
				{value: 1, label: emotions[1], color: color_option.zx}, 
				{value: 2, label: emotions[2], color: color_option.fn},
				{value: 3, label: emotions[3], color: color_option.bs},
				{value: 4, label: emotions[4], color: color_option.jq},
				{value: 5, label: emotions[5], color: color_option.kj},
				]
		},
		/*toolbox: {
			show: true,
			orient: 'vertical',
			left: 'right',
			top: 'center',
			feature: {
				saveAsImage: {}
			}
		},*/
		series : [
			{
				name: '显著情绪',
				type: 'map',
				mapType: 'china',
				showLegendSymbol:false,
				roam: false,
				zoom: 1.2,
				itemStyle:{
					normal:{
						label:{show:false},
						borderWidth:1,
						borderColor:'#FFFFFF'
					},
					emphasis:{
						label:{show:true}
						
						
					}
				},
				data:data[2],
				tooltip:{
					textStyle:{
						align:'left'
					},
					formatter:function(a){
						var label = '显著情绪 - ' + emotions[a.value] + "<br/>";
						var emotion = a.data.emotion;
						for(var i=0; i<emotion.length; i++){
							label += emotion[i].name + " : ";
							if(emotion[i].value > 0){
								label += emotion[i].value;
							}else{
								label += "-";
							}
							label += "<br/>";
						}
						return label;
					}
				}
			}
		]
	};
    
    var title = $("#emotionMapTitle").val();
    if(title != null && title != ''){
    	option.title = {
            text: title,
            left: 'center',
            textStyle: {
                color: '#717171',
                fontWeight: 'normal',
                fontSize:14
            }
        };
    }
	myChart.setOption(option);
	setTimeout(function (){
		window.onresize = function () {
			myChart.resize();
		}
	})
}


function quanpingChart(obj,type){
    var demo=$(obj).parents(".panel-heading-padd").next();
    demo.find(".chart-bady").html('');
    if ($(obj).find("i").hasClass("icon-full")){
        if(type==1){
            demo.find(".swiper-container1").css("height",$(window).height()*0.85+"px");
            demo.find(".swiper-slide").width($(window).width());
        }
        demo.find(".chart-bady").height($(window).height()*0.85);
        demo.find(".chart-bady").width($(window).width()*0.9);
    }else {
        if (type==1){
            demo.find(".swiper-container1").height(495);
            demo.find(".chart-bady").height(495);
            demo.find(".chart-bady").width(670);
            demo.find(".swiper-slide").width(670);
        }else{
            demo.find(".chart-bady").height(460);
        }
    }
    if(type==1){
        refushMapChart();
    }else if (type==2){
        refushBarPieChart();
    }else if(type==3){
        refushLineChart();
    }
}
function isNozifu(strForText){
    var containSpecial = RegExp(/[(\ )(\~)(\！)(\!)(\@)(\#)(\#)(\￥)(\$)(\……)(\%)(\^)(\&)(\*)(\（)(\()(\）)(\))(\-)(\——)(\_)(\=)(\【)(\[)(\】)(\])(\{)(\})(\|)(\\)(\；)(\;)(\：)(\:)(\‘)(\’)(\')(\“)(\”)(\")(\，)(\,)(\。)(\.)(\、)(\/)(\<)(\《)(\》)(\>)(\?)(\？)(\)]+/);
    if( containSpecial.test(strForText) ){
        return 0;
    }else if (strForText.indexOf("+")!=-1){
        var strs= new Array(); //定义一数组
        strs = strForText.split("+");
        for (i=0;i<strs.length;i++){
            if (strs[i].length<=1){
                return 1;
            }
        }
    }else{
        return 2;
    }

}
function goEmotionAnalysis(){
        var kv = $("#search-keyword").val();
        if($.trim(kv)=="人名、企业名、品牌名或事件关键词"){
            showMsgInfo(0,"请输入搜索词!",0);
            return;
        }
        kv = kv.replace(/、/g,"+").replace(/\s+/g,"+");
        if($.trim(kv).length>20){
            showMsgInfo(0,"搜索词不得大于20个字!",0);
            return;
        }
        if($.trim(kv)==""||$.trim(kv)=="请输入您想查询的关键字"){
            showMsgInfo(0,"请输入你想搜索的词语",0);
            return ;
        }
        if($.trim(kv).length==1){
            showMsgInfo(0,"请至少输入2个字符",0);
            return ;
        }
        $("#title,#keyword").val(kv);
        checkKeyword(kv);
}
function checkKeyword(keyword){
    if (isNozifu(keyword)==0){
        showMsgInfo(0, "对不起，你的关键词不能包含非法字符，如#@￥%……&*等", 0);
        return;
    }else if(isNozifu(keyword)==1){
        showMsgInfo(0, "请至少输入2个字符", 0);
        return;
    }
    $.ajax({
        url:actionBase+"/view/emotion/checkEmotionKeyword.action",
        type:"post",
        data:{
            "keyword":keyword,
            "count" : 0,
            "checkExit":$("#isNoCheck").val()
        },
        success:function(data){
            if(data!=null && data!=""){
                var form = document.getElementById("searchForm");
                form.action = actionBase+"/view/emotion/goEmotionAnalysis.shtml";
                if($("#adminId").val()!="") {
                    if (data == "true") {
                        form.submit();
                    } else {
                        showMsgInfo(0, data, 0);
                    }
                }else{
                    if (data == "true") {
                        form.submit();
                    }else{
                        intoTo(17);
                    }
                }
            }else{
                showMsgInfo(0,"对不起,您所搜索的词语“"+keyword+"”暂无数据,如有需要请联系客服",0);
            }
        }
    });
}
function goClickEmotionAnalysis(title,kw,fkw) {
    $("#title").val(title);
    $("#keyword").val(kw);
    $("#filterKeyword").val(fkw);
    $("#includeOthers").val(0);
    var form = document.getElementById("searchForm");
    form.action = actionBase+"/view/emotion/goEmotionAnalysis.shtml";
    form.submit();
}


function enterClick(event){
    event = event || window.event;
    if(event.keyCode==13){
        goEmotionAnalysis();
    }
}
/*$(function () {
    checkUserRealName();
})*/

/**
 * 检查是否需要实名认证
 */
function checkUserRealName() {
    $.ajax({
        url:actionBase+"/view/emotion/checkUserRealNameAuthentication.action",
        type:"post",
        success:function (data) {
            if(data=="true"){
                $("#isNoCheck").val(true);
               /* $("#phoneVerificationModal").modal({show:true});*/
            }
        }
    })
}
function timer(intDiff){
    $('#getValidation').attr("disabled","true");
    window.setInterval(function(){
        var second=0;//时间默认值
        if(intDiff > 0){
            second = Math.floor(intDiff);
        }
        $('#getValidation').html('<s></s>'+second+'秒');
        intDiff--;
    }, 1000);
}
function jump(count) {
    window.setTimeout(function(){
        count--;
        if(count > 0) {
            $("#localmiao").attr('innerHTML', count);
            jump(count);
        } else {
            $("#VerificationModal2").modal('hide');
            goEmotionAnalysis();
        }
    }, 1000);
}
/**
 * 获取验证码
 */
$("#getValidation").click(function () {
    if($.trim($("#username").val())!="" && $.trim($("#username").val())!=null && $.trim($("#password").val())!=""&& $.trim($("#password").val())!=null){
        timer(120);
        $.ajax({
            url:actionBase+"/view/emotion/getvalidationCode.action",
            type:'post',
            data:{'username': $("#username").val()},
            success:function (data) {
                $("messageValidation").css("display","block");
                if (!data)
                    $("messageValidation").text("发送失败");
            }
        })
    }
})
/**
 * 立即验证
 */
function  userLogin2() {
    if($.trim($("#pCode").val())!="" && $.trim($("#username").val())!="" && $.trim($("#password").val())!=""){
        $.ajax({
            url:actionBase+"/view/emotion/validationCode.action",
            type:"post",
            data:{"validationCode":$("#pCode").val(),'username': $("#username").val(),'password': $("#password").val()},
            success:function (data) {
                $("#phoneVerificationModal2").modal('hide');
                if(data){
                    $("#isNoCheck").val(false);
                    $("#mobilenumber").html($("#username").val());
                    $("#VerificationModal2").modal({show:true});
                    jump(4);
                }else{
                    $("#VerificationModal2error").modal({show:true});
                }
            }
        })
    }
}

function history() {
    $("#hotwords").hide();
    $.ajax({
        url: actionBase+"/keyWordLogs.action?" + new Date(),
        type: "get",
        dataType: "json",
        success: function (data) {
            $("#history_search").find("ul").eq(0).find("li:gt(0)").remove();
            var keyWordLogs = $("#history_search").find("ul").eq(0).html();
            if (data != null) {
                var len = data.size;
                var keyWord = "";
                for (var i = 0; i < len; i++) {
                    var vl = data.logs["keyWord" + i];
                    keyWord += "<li style='cursor:pointer;' onclick='ajaxClickEvl(this)'><h1>" + vl + "</h1></li>";
                }
                keyWordLogs += keyWord;
                $("#keyWordLogs_ul").css({"display": "block"}).html(keyWordLogs);

                if (len != 0) {
                    $("#history_search").css({"display": "block"});
                }
            }
        }, error: function (data) {
        }
    });
};
function ajaxClickEvl(ev) {
    $("#search-keyword").val($(ev).text());
    $("#keywords").val($(ev).text());
    $("#keyWordLogs_ul").css( {
        "display" : "none"
    });
    goEmotionAnalysis();
}
var timeout="";
function thinkKeywords(obj,id){
    $("#keyWordLogs_ul").hide();
    var txt = $(obj).val();
    if(txt==""){
        return;
    }
    if(timeout!=""){
        window.clearTimeout(timeout);
    }
    timeout = setTimeout(function(){beginThink(txt,id)},1000);
}
function beginThink(txt){
    var data = {"thinkKeyword":txt};
    $.ajax({
        url : actionBase+"/view/hotSearch/thinkKeywords.action",
        type : "post",
        data:data,
        success : function(data){
            if (data && data instanceof Array) {
                data = data[0];
                if (data == null) {
                    $("#hotwords").hide();
                }
                var html = '';
                if (data!=null) {
                    $.each(data, function () {
                        html += '<li style="cursor:pointer;" onclick="hotSearch(\'' + this.title + '\',\'' + this.keyword + '\',\'' + this.filterKeyword + '\')"><a href="javascript:void(0)"><h1>' + this.title + '</h1></a>';
                        if (this.type == 1) {
                            html += '<em>来自地域';
                        } else if (this.type == 2) {
                            html += '<em>来自股票';
                        } else if (this.type == 3) {
                            html += '<em>来自明星';
                        } else if (this.type == 4) {
                            html += '<em>来自行业';
                        } else if (this.type == 5) {
                            html += '<em>来自品牌';
                        } else if (this.type == 6) {
                            html += '<em>来自人物';
                        } else if (this.type == 7) {
                            html += '<em>来自景区';
                        } else if (this.type == 8) {
                            html += '<em>来自地域';
                        } else if (this.type == 9) {
                            html += '<em>来自汽车';
                        } else if (this.type == 10) {
                            html += '<em>来自手机';
                        } else if (this.type == 11) {
                            html += '<em>来自美妆';
                        } else if (this.type == 12) {
                            html += '<em>来自金融';
                        } else if (this.type == 13) {
                            html += '<em>来自电脑';
                        } else if (this.type == 14) {
                            html += '<em>来自家电';
                        } else if (this.type == 15) {
                            html += '<em>来自食品';
                        } else if (this.type == 16) {
                            html += '<em>来自教育';
                        }
                        if (!this.category || this.category == "" || this.category == "undefined") {
                            html += '</em></li>';
                        } else {
                            html += '（' + this.category + '）</em></li>';
                        }

                    });
                }
                $("#hotwords").html(html);
                $("#hotwords").show();
            }
        }
    })
}
function hotSearch(title,keyword,filterKeyword){
    if('undefined'==keyword){
        keyword = "";
    }
    if('undefined'==filterKeyword){
        filterKeyword = "";
    }
    $("#title").val(title);
    $("#keyword").val(keyword);
    $("#filterKeyword").val(filterKeyword);
    var kv = $("#keyword").val();
    if($.trim(kv)==""||$.trim(kv)=="请输入您想查询的关键字"){
        showMsgInfo(0,"请输入你想搜索的词语",0)
        return ;
    }
    if($.trim(kv).length==1){
        showMsgInfo(0,"请至少输入2个字符",0)
        return ;
    }
    var form = document.getElementById("searchForm");
    form.action = actionBase+"/view/emotion/goEmotionAnalysis.shtml";
    form.submit();
}
//格式化数字，从右向左每三个一个逗号xx,xxx
function formatNum(number){
    var n=0;
    number="'"+number+"'";
    if(n != 0 ){
        n = (n > 0 && n <= 20) ? n : 2;
    }
    number = parseFloat((number + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var sub_val = number.split(".")[0].split("").reverse();
    var sub_xs = number.split(".")[1];

    var show_html = "";
    for (i = 0; i < sub_val.length; i++){
        show_html += sub_val[i] + ((i + 1) % 3 == 0 && (i + 1) != sub_val.length ? "," : "");
    }
    if(n == 0 ){
        return show_html.split("").reverse().join("");
    }else{
        return show_html.split("").reverse().join("") + "." + sub_xs;
    }

}