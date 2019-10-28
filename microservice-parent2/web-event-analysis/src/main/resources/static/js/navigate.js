//支付
var tempSearchKeyword = '';
function doOrder() {
    var url = njxBasePath + '/view/pay/doOrder.action';
    var params = {'searchKeyword':tempSearchKeyword};
    sendPostForm(url, '_self', params);
}

// 订单确认
var payflag = 0;
function confirmOrder() {
    if(payflag == 0){
        payflag == 1;
        $("#confirmForm").submit();
    }
}

// 跳转 (支付宝 微信 支付宝)
function goPay(payRecordId) {
    var url = njxBasePath + "/pay/goPay?payRecordId=" + payRecordId;
    if ($('#loginPlatform').val() == 2 || $('#payType').val() == "03")
        url = njxBasePath + "/pay/goSinaPay?payRecordId=" + payRecordId;
    if ($('#loginPlatform').val() == 3 || $('#payType').val() == "02"){
        var redirect_uri = "http://m.wrd.cn/view/pay/goWeiXinPay.action?payRecordId=" + payRecordId;
        // var redirect_uri = "http://api-open-beta.wrd.cn/pay/goWeiXinPay?payRecordId=" + payRecordId;
        url = "https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + encodeURIComponent(redirect_uri);
        url += "&appid=wxffe32990bd5bd9c9&response_type=code&scope=snsapi_base";
        // url += "&appid=wx30ce0a6d80b3a132&response_type=code&scope=snsapi_base";
        location.href = url;
        return
    }
    location.href = url;
}

// 定时检测订单状态
function myInterval(payRecordId) {
    $.ajax({
        type:'post',
        url:njxBasePath + '/view/user/checkOrderStatus.action?payRecord.payRecordId='+payRecordId,
        cache:false,
        success: function(data){
            if(data==1)
                buySuccess();
        }
    });
}

// 支付成功
function buySuccess(){
    //关注列表打开提示层
    $(".zhezhao").fadeIn(300);
    $("#subPOP").fadeIn(300);
    //$("body").css({overflow:"hidden"});    //禁用滚动条
    $(".prompPOP").addClass('scaleShow');
    $(".prompPOP").removeClass('scaleOut');
}

// 发送POST请求
function sendPostForm(url, target, params) {
    var temp = document.createElement("form");
    temp.action = url;
    temp.method = 'POST';
    temp.target = target;
    for(var x in params) {
        var opt = document.createElement('input');
        opt.type = 'hidden';
        opt.name = x;
        opt.value = params[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
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

//单条删除搜索历史
function deleteKwLog(id){
    $.ajax({
        type:'post',
        url:njxBasePath+"/deleteSearchLog.action?searchLogId="+id,
        cache:false,
        success: function(msg){
            if(msg==0){
                alert('删除失败!');
            }else{
                var count = parseInt($('#searchHistoryCount').val()) - 1;
                $('#searchHistoryCount').val(count);
                if (count == 0) {
                    $('#hotwords').remove();
                }
            }
        }
    });
}


//删除用户的所有搜索历史
function deleteUserSearchLog(userId){
    $.ajax({
        type:'post',
        url:njxBasePath+"/deleteUserSearchLog.action",
        cache:false,
        success: function(msg){
            if(msg==0){
                alert('删除失败!');
            }else{
                $('#hotwords').remove();
            }
        }
    });
}

// 购买产品
function buyProduct() {
    if(payflag == 0){
        payflag = 1;
        $.ajax({
            url:njxBasePath + '/pay/confirmOrderV3',
            type:'POST',
            cache:false,
            data:$(".openBuyPrompt form[name='payForm']").serialize(),
            success:function(data) {
                //payflag = 0;
                if(data.status==1){
                    goPay(data.obj);
                }else if(data.status==2){
                    if(data.obj == 18){
                        location.href =  njxBasePath + '/createAnalysis?createType=1';
                    }else if(data.obj == 32){
                        location.href = njxBasePath + "/weibo/createWeiBoAnalysis?createType=1"
                    }else if(data.obj  == 21){
                        location.href = njxBasePath + "/compet/productsAnalysis";
                    }else if(data.obj  == 22){
                        location.href = njxBasePath + '/weiboAnalysis/weiboAnalysisIndex';
                    }else {
                        location.href =  njxBasePath + "/userCenter/goBuy?type=0";
                    }
                }else if(data.status == 3){
                    location.href = njxBasePath + "userCenter/renewProductPackage";
                }
            }
        });
    }
}
//购买微积分
function buyProductwjf() {
    if(payflag == 0){
        payflag = 1;
        $.ajax({
            url:njxBasePath + '/pay/confirmOrderV3',
            type:'POST',
            cache:false,
            data:$("#commonBuyCreditForm").serialize(),
            success:function(data) {
                payflag = 0;
                if(data.status==1){
                    goPay(data.obj);
                }else if(data.status==2){
                    if(data.obj == 18){
                        location.href =  njxBasePath +'/createAnalysis?createType=1';
                    }else if(data.obj == 32){
                        location.href = njxBasePath +"/weibo/createWeiBoAnalysis?createType=1"
                    }else if(data.obj  == 21){
                        location.href = njxBasePath +"/compet/productsAnalysis";
                    }else if(data.obj  == 22){
                        location.href = njxBasePath + '/weiboAnalysis/weiboAnalysisIndex';
                    }else{
                        location.href = njxBasePath + "/userCenter/goBuy?type=0";
                    }
                }else if(data.status == 3){
                    location.href = njxBasePath +"/userCenter/renewProductPackage";
                }
            }
        });
    }
}
//购买产品
function buyProductpro() {
    if(payflag == 0){
        payflag = 1;
        $(".fail-popver").hide();
        $("#jiazai").show();
        $('.popver-mask').unbind("click");
        $.ajax({
            url:njxBasePath + '/pay/confirmOrderV3',
            type:'POST',
            cache:false,
            data:$("#selfProductForm").serialize(),
            success:function(data) {
                payflag = 0;
                if(data.status==1){
                    goPay(data.obj);
                }else if(data.status==2){
                    if(data.obj == 18){
                        location.href =  njxBasePath +'/createAnalysis?createType=1';
                    }else if(data.obj == 32){
                        location.href = njxBasePath + "/weibo/createWeiBoAnalysis?createType=1"
                    }else if(data.obj  == 21){
                        location.href =  njxBasePath +"/compet/productsAnalysis";
                    }else if(data.obj  == 22){
                        location.href = njxBasePath + '/weiboAnalysis/weiboAnalysisIndex';
                    }else if(data.obj  == 38||data.obj  == 39||data.obj  == 40){
                        location.href = njxBasePath + '/weiboAnalysis/weiboAnalysisIndex';
                    }else{
                        location.href =  njxBasePath + "/userCenter/goBuy?type=0";
                    }
                }else if(data.status == 3){
                    location.href = njxBasePath +"/userCenter/renewProductPackage";
                }
            }
        });
    }
}
// 打开购买窗
function showBuy(buy){
    if(!"${admin}"){
        window.location.href=njxBasePath+"/indexLocal";
        return;
    }
    $('#buyPrompt_jp'+buy.type).addClass("openBuyPrompt");
    buy.buyBefore&&buy.buyBefore();
    computePrice();
    $('.zhezhao').fadeIn(300);
    $('#buyPrompt_jp'+buy.type).fadeIn(300);
}
//增加购买的产品
function addKeyword(){
    $(".openBuyPrompt input[name='packageCount']").val(parseInt($(".openBuyPrompt .number").text())+1);
    $(".openBuyPrompt .number").text(parseInt($(".openBuyPrompt .number").text())+1)
    computePrice();
}

// 减少购买的产品
function delKeyword(){
    if(parseInt($(".openBuyPrompt .number").text())>1){
        $(".openBuyPrompt input[name='packageCount']").val(parseInt($(".openBuyPrompt .number").text())-1);
        $(".openBuyPrompt .number").text(parseInt($(".openBuyPrompt .number").text())-1)
        computePrice();
    }
}

//计算价格
function computePrice(){
    var creditAmount = $("#creditAmount").val();
    $.post(actionBase+"/pay/getOrderFee",$(".openBuyPrompt form[name='payForm']").serialize(),function(data){
        if(data.status){
            $(".openBuyPrompt .formPackage .yf span").text(data.obj.totalFee);
            if(parseInt(creditAmount) > data.obj.useCreditAmount){
                $(".openBuyPrompt .dcreditAmount").text(data.obj.useCreditAmount);
                $(".openBuyPrompt .dprice").text(parseInt(data.obj.useCreditAmount)/100);
            }else{
                $(".openBuyPrompt .dcreditAmount").text(creditAmount);
                $(".openBuyPrompt .dprice").text(parseInt(creditAmount)/100);
            }
        }
    });
}
$(function(){
    //是否使用微积分
    $(".formPackage .cont .icon-check-circle").on("click",function(){
        $(this).toggleClass("check-circle-click");
        $(".openBuyPrompt input[name='useCredit']").val($(this).hasClass("check-circle-click"));
        computePrice();
    });
    //选择不同的产品
    $(".paylist>ul>li").on("click",function(){
        $(this).addClass("cur").parent().siblings().find("li").removeClass("cur");
        $(".openBuyPrompt input[name='productPackageId']").val($(this).find(".paydesc li[data-productpackageid]").data("productpackageid"));
        computePrice();
    });
});

function searchAlert(){
    $("#searchPop").fadeIn(300);
    $(".prompPOP").addClass('scaleShow');
    $(".prompPOP").removeClass('scaleOut');
    setTimeout(function(){
        $(".zhezhao").fadeOut(300);
        $("#searchPop").fadeOut(300);
        $(".prompPOP").addClass('scaleOut');
        $(".prompPOP").removeClass('scaleShow');
    },3000);
}
//明星股票等分类
function category(type){
    $("#type").val(type);
    $("#page").val(1);
    $("#listDiv").html("");

    window.location.href= actionBase+"/weiboHome?myOrder=1&type="+type;
}


//微积分购买
//增加产品包数量
function addPackageCount(demo, index) {
    var c = parseInt($(demo).val());

    if((index < 6 && index > 0 && c == 100) || (index == 6 && c == 100000))
        return false;

    c = c+1;
    $(demo).val(c);
    if(index == 0){
        commonBuyOrderFee();
    }else{
        getBuyGroupProductFee(index, c);
        commonBuyProductFee();
    }

}
//减少产品包数量
function minPackageCount(demo, index) {
    var c = parseInt($(demo).val());
    var num = demo.split("_")[1];
    if (c > 0) {
        c = c-1;
        $(demo).val(c);
        if(c==0){
            $("#creditPackagesLI_"+num).trigger("click");
        }
        if(index == 0){
            commonBuyOrderFee();
        }else{
            getBuyGroupProductFee(index, c);
            commonBuyProductFee();
        }
    }
}
//减少产品包数量
function minSinglePackageCount(demo, index) {
    console.log("dddd");
    var c = parseInt($(demo).val());
    var num = demo.split("_")[1];
    if (c > 0) {
        c = c-1;
        $(demo).val(c);
        if(c==0){
            $("#creditPackagesLI_"+num).click();
        }
        if(index == 0){
            commonBuyOrderFee();
        }else{
            getBuySingleGroupProductFee(index, c);
            commonBuyProductFee();
        }
    }
}
//获取待支付金额
var getOrderFeeFlag = 0;
function commonBuyOrderFee(){
    getOrderFeeFlag++;
    setTimeout(function(){
        getOrderFeeFlag--;
        if(getOrderFeeFlag == 0){
            $.post(actionBase+"/pay/getOrderFeeV2",$("#commonBuyCreditForm").serialize(),function(data){
                if(data.obj.totalFee!=null){
                    $("#creditTotalFee").text('￥'+data.obj.totalFee.toFixed(2));
                    $(".btn-pay").attr("onclick","buyProductwjf()");
                }else{
                    $("#creditTotalFee").text('￥0.00');
                    $(".btn-pay").removeAttr("onclick");
                }
            });
        }
    }, 500);
}
function commonBuyProductFee(){
    getOrderFeeFlag++;
    setTimeout(function(){
        getOrderFeeFlag--;
        console.log("getOrderFeeFlag======"+getOrderFeeFlag)
        if(getOrderFeeFlag == 0){
            var serialize=$("#selfProductForm").serialize();
            $.post(njxBasePath+"/pay/getOrderFeeV2",serialize,function(data){

                if(data.obj.totalFee!=null){
//					if($("#useCredit1").val() == 'false'){
//						$("#goProductCartBtn").attr("onclick","buyProductpro()");
//						$("#creditTotalFee1").text('￥'+data.obj.totalFee.toFixed(2));
//						$("#needwjf2").text('￥'+data.obj.totalFee.toFixed(2));
//						/*$("#goPay2").attr("onclick","buyProductpro()");*/
//	    			}else {//微积分支付
//	    				$("#creditTotalFee1").text(data.obj.creditAmountFee+'微积分');
//	    				$("#needwjf2").text(data.obj.creditAmountFee+'微积分');
//	    				if(data.obj.userCreditNum<data.obj.creditAmountFee){
//	    					$("#nowwjf").text(data.obj.userCreditNum);
//	    					$("#needwjf").text(data.obj.creditAmountFee-data.obj.userCreditNum);
//	    					$("#goProductCartBtn").attr("onclick","failCredit()");
//	    				}else{
//	    					$("#goProductCartBtn").attr("onclick","showTips()");
//	    				}
//	    			}
                    if(data.obj.creditAmountFee<10000){//微积分支付
                        $("#paymode").show();
//						$(".icon-pay.pay-weibo").parent().hide();
//						$(".icon-pay.pay-weixin").parent().hide();
                        $("#pay-weibo").hide();
                        $("#pay-weixin").hide();

                        $("#payType").val("00");
                        $("#useCredit1").val("true");
                        $("#in_weijifen").attr("checked");
                        $("#goProductCartBtn").attr("onclick","showTips()");

                        $("#creditTotalFee1").text(data.obj.creditAmountFee+'微积分');
                        $("#needwjf2").text(data.obj.creditAmountFee+'微积分');
                        $("#pCredit").text(data.obj.creditAmountFee+'微积分');
                        if(data.obj.userCreditNum<data.obj.creditAmountFee){
                            $("#nowwjf").text(data.obj.userCreditNum);
                            $("#needwjf").text(data.obj.creditAmountFee-data.obj.userCreditNum);
                            $("#goProductCartBtn").attr("onclick","failCredit()");
                        }else{
                            $("#goProductCartBtn").attr("onclick","showTips()");
                        }
                    }else{//人民币支付或微积分
                        $("#pay-weixin").show();
                        $("#pay-weibo").show();
                        $("#paymode").show();
                        $("#wjf-tootips").modal('show');
//						$(".icon-pay.pay-weibo").parent().show();
//						$(".icon-pay.pay-weixin").parent().show();
                        if($("#useCredit1").val() == 'false'){
                            $("#goProductCartBtn").attr("onclick","buyProductpro()");
                            $("#creditTotalFee1").text('￥'+data.obj.totalFee.toFixed(2));
                            $("#needwjf2").text('￥'+data.obj.totalFee.toFixed(2));
                            $("#pCredit").text('￥'+data.obj.totalFee.toFixed(2));
                            /*$("#goPay2").attr("onclick","buyProductpro()");*/
                        }else {//微积分支付
                            $("#creditTotalFee1").text(data.obj.creditAmountFee+'微积分');
                            $("#needwjf2").text(data.obj.creditAmountFee+'微积分');
                            $("#pCredit").text(data.obj.creditAmountFee+'微积分');
                            if(data.obj.userCreditNum<data.obj.creditAmountFee){
                                $("#nowwjf").text(data.obj.userCreditNum);
                                $("#needwjf").text(data.obj.creditAmountFee-data.obj.userCreditNum);
                                $("#goProductCartBtn").attr("onclick","failCredit()");
                            }else{
                                $("#goProductCartBtn").attr("onclick","showTips()");
                            }
                        }
                    }
                }else {
                    $("#creditTotalFee1").text('￥0.00');
                }
            });
        }
    }, 500);
}
function showTips(){
    $('#singlediv').hide();
    $('#success').show();
    $('#fail').hide();
    $('.popver-mask').show();
    $('html').addClass('noscroll');
}

function failCredit(){
    $('#singlediv').hide();
    $('#success').hide();
    $('#fail').show();
    $('.popver-mask').show();
    $('html').addClass('noscroll');
}
//获取微积分购买应付总额
function getBuyCreditFee(index, value){
    var totalFee = 0;
    $("#creditBuyTab li").each(function(i, n) {
        var productPackageId = $(this).attr("data-cmd");
        var creditPackageCount = $("#creditPackageCount_" + productPackageId).val();
        var creditCount = $("#commonBuyCreditCount_" + productPackageId).val();
        if(!IsNum(creditPackageCount) || creditPackageCount < 0){
            creditPackageCount = 0;
        }else if(creditPackageCount > 99){
            creditPackageCount = 99;
        }
        $("#creditPackageCount_" + productPackageId).val(creditPackageCount)

        var creditPrice = $("#commonBuyCreditPrice_" + productPackageId).val();
        if(creditPackageCount > 0){
            totalFee += creditPackageCount*creditPrice;
        }
    });

    commonBuyOrderFee();
}

//获取套餐包购买应付总额
function getBuyGroupProductFee(index, value){
    $("#groupPackBuyShow li:not()").remove();
    var totalFee = 0;
    var keywordCount = $('input[name="myProducts[\'product1001\'].keywordPackageNum"]').val();
    var analysisCount = $('input[name="myProducts[\'product1002\'].keywordPackageNum"]').val();
    var weiboAnalysisCount = $('input[name="myProducts[\'product1003\'].keywordPackageNum"]').val();
    var briefCount = $('input[name="myProducts[\'product1004\'].keywordPackageNum"]').val();
    var productAnalysisCount = $('input[name="myProducts[\'product1005\'].keywordPackageNum"]').val();
    var singleWeiboAnalysisCount = $('input[name="myProducts[\'product1006\'].keywordPackageNum"]').val();
    if(!IsNum(keywordCount) || keywordCount < 0){
        keywordCount = 0;
    }else if(keywordCount > 99){
        keywordCount = 99;
    }
    $('input[name="myProducts[\'product1001\'].keywordPackageNum"]').val(keywordCount);
    if(!IsNum(analysisCount) || analysisCount < 0){
        analysisCount = 0;
    }else if(analysisCount > 99){
        analysisCount = 99;
    }
    $('input[name="myProducts[\'product1002\'].keywordPackageNum"]').val(analysisCount);
    if(!IsNum(weiboAnalysisCount) || weiboAnalysisCount < 0){
        weiboAnalysisCount = 0;
    }else if(weiboAnalysisCount > 99){
        weiboAnalysisCount = 99;
    }
    $('input[name="myProducts[\'product1003\'].keywordPackageNum"]').val(weiboAnalysisCount);
    if(!IsNum(briefCount) || briefCount < 0){
        briefCount = 0;
    }else if(briefCount > 99){
        briefCount = 99;
    }
    $('input[name="myProducts[\'product1004\'].keywordPackageNum"]').val(briefCount);
    if(!IsNum(productAnalysisCount) || productAnalysisCount < 0){
        productAnalysisCount = 0;
    }else if(productAnalysisCount > 99){
        productAnalysisCount = 99;
    }
    $('input[name="myProducts[\'product1005\'].keywordPackageNum"]').val(productAnalysisCount);
    if(!IsNum(singleWeiboAnalysisCount) || singleWeiboAnalysisCount < 0){
        singleWeiboAnalysisCount = 0;
    }else if(singleWeiboAnalysisCount > 999){
        singleWeiboAnalysisCount = 999;
    }
    $('input[name="myProducts[\'product1006\'].keywordPackageNum"]').val(singleWeiboAnalysisCount);

    var keywordPrice = $('input[name="myProducts[\'product1001\'].keywordPackagePrice"]').val();
    var analysisPrice = $('input[name="myProducts[\'product1002\'].keywordPackagePrice"]').val();
    var weiboAnalysisPrice = $('input[name="myProducts[\'product1003\'].keywordPackagePrice"]').val();
    var briefPrice = $('input[name="myProducts[\'product1004\'].keywordPackagePrice"]').val();
    var productAnalysisPrice = $('input[name="myProducts[\'product1005\'].keywordPackagePrice"]').val();
    var singleWeiboAnalysisPrice = $('input[name="myProducts[\'product1006\'].keywordPackagePrice"]').val();

    if(keywordCount > 0)
        $("#groupPackBuyShow").append('<li><span class="fc-gray9 g_6">监测方案</span><span class="fc-black g_1">&times;'+keywordCount+'</span><span class="fc-black">&yen;'+(keywordPrice*keywordCount).toFixed(2)+'</span></li>');
    if(analysisCount > 0)
        $("#groupPackBuyShow").append('<li><span class="fc-gray9 g_6">全网事件分析</span><span class="fc-black g_1">&times;'+analysisCount+'</span><span class="fc-black">&yen;'+(analysisPrice*analysisCount).toFixed(2)+'</span></li>');
    if(weiboAnalysisCount > 0)
        $("#groupPackBuyShow").append('<li><span class="fc-gray9 g_6">微博事件分析</span><span class="fc-black g_1">&times;'+weiboAnalysisCount+'</span><span class="fc-black">&yen;'+(weiboAnalysisPrice*weiboAnalysisCount).toFixed(2)+'</span></li>');
    if(briefCount > 0)
        $("#groupPackBuyShow").append('<li><span class="fc-gray9 g_6">简报制作</span><span class="fc-black g_1">&times;'+briefCount+'</span><span class="fc-black">&yen;'+(briefPrice*briefCount).toFixed(2)+'</span></li>');
    if(productAnalysisCount > 0)
        $("#groupPackBuyShow").append('<li><span class="fc-gray9 g_6">竞品分析</span><span class="fc-black g_1">&times;'+productAnalysisCount+'</span><span class="fc-black">&yen;'+(productAnalysisPrice*productAnalysisCount).toFixed(2)+'</span></li>');
    if(singleWeiboAnalysisCount > 0)
        $("#groupPackBuyShow").append('<li><span class="fc-gray9 g_6">微博传播效果分析</span><span class="fc-black g_1">&times;'+singleWeiboAnalysisCount+'</span><span class="fc-black">&yen;'+(singleWeiboAnalysisPrice*singleWeiboAnalysisCount).toFixed(2)+'</span></li>');
    commonBuyProductFee();
}

//获取套餐包购买应付总额
function getBuySingleGroupProductFee(index, value){
    $("#groupPackBuyShow li:not()").remove();
    var totalFee = 0;
    var keywordCount = $('input[name="myProducts[\'product1001\'].keywordPackageNum"]').val();
    var analysisCount = $('input[name="myProducts[\'product1002\'].keywordPackageNum"]').val();
    var weiboAnalysisCount = $('input[name="myProducts[\'product1003\'].keywordPackageNum"]').val();
    var briefCount = $('input[name="myProducts[\'product1004\'].keywordPackageNum"]').val();
    var productAnalysisCount = $('input[name="myProducts[\'product1005\'].keywordPackageNum"]').val();
    var singleWeiboAnalysisCount = $('input[name="myProducts[\'product1006\'].keywordPackageNum"]').val();
    if(!IsNum(keywordCount) || keywordCount < 1){
        keywordCount = 1;
    }else if(keywordCount > 99){
        keywordCount = 99;
    }
    $('input[name="myProducts[\'product1001\'].keywordPackageNum"]').val(keywordCount);
    if(!IsNum(analysisCount) || analysisCount < 1){
        analysisCount = 1;
    }else if(analysisCount > 99){
        analysisCount = 99;
    }
    $('input[name="myProducts[\'product1002\'].keywordPackageNum"]').val(analysisCount);
    if(!IsNum(weiboAnalysisCount) || weiboAnalysisCount < 1){
        weiboAnalysisCount = 1;
    }else if(weiboAnalysisCount > 99){
        weiboAnalysisCount = 99;
    }
    $('input[name="myProducts[\'product1003\'].keywordPackageNum"]').val(weiboAnalysisCount);
    if(!IsNum(briefCount) || briefCount < 1){
        briefCount = 1;
    }else if(briefCount > 99){
        briefCount = 99;
    }
    $('input[name="myProducts[\'product1004\'].keywordPackageNum"]').val(briefCount);
    if(!IsNum(productAnalysisCount) || productAnalysisCount < 1){
        productAnalysisCount = 1;
    }else if(productAnalysisCount > 99){
        productAnalysisCount = 99;
    }
    $('input[name="myProducts[\'product1005\'].keywordPackageNum"]').val(productAnalysisCount);
    if(!IsNum(singleWeiboAnalysisCount) || singleWeiboAnalysisCount < 1){
        singleWeiboAnalysisCount = 1;
    }else if(singleWeiboAnalysisCount > 999){
        singleWeiboAnalysisCount = 999;
    }
    $('input[name="myProducts[\'product1006\'].keywordPackageNum"]').val(singleWeiboAnalysisCount);
}