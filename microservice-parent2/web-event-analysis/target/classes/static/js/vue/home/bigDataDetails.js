Vue.filter("formatDate",function(date,formatStr){
    var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
    return moment(date).format(formatStr);
});
/**
 *
 */
var app = new Vue({
    el:'#app',
    data:{
        loadMoreFlag:true,
        page : 1,
        pagesize : 15,
        activeClass: 0,
        list:[],
        recommendList:[],
        bList:[],
        idList:[],
        newLike:[],
        firstRecommend:{},
        totalCount:0,
        name:'',
        type:0,
        selectType:'0',
        likeName:'',
        orderFeeFlag:0,
        dataShow:false
    },
    created:function(){
        // this.page=1;
        // var param = {
        //     type : 0,
        //     page : this.page,
        //     pagesize : 15
        // };
        // this.initData(param);
        this.readBuy();
    },
    // mounted: function () {
    //     this.readBuy();
    // },
    methods:{
        //同类型推荐
        likeType: function (x,y,z) {
            this.newLike=[];
            $.post(njxBasePath + '/getBigDataList', {
                page: 1,
                pagesize: 15,
                type: x,
                isPackagePrice:0
            }, function (result) {
                if (result.code == "0000") {
                    for (var i = 0; i < 5; i++) {
                        app.newLike.push(result.data[i + 1])
                    }
                }
            })
        },
        readBuy:function () {
            $.ajax({
                url: njxBasePath + '/readBuy',
                type: 'POST',
                cache: 'false',
                success: function (res) {
                    var reportId=$("#infoDataId").val();
                    reportId=parseInt(reportId);
                    if(res.code=='9999'){
                        app.bList=[];
                        app.dataShow=false;
                        $("#d_loading").hide();
                        $("#getButtom").show();
                    }else{
                        app.bList=res.data;
                        if (app.bList!==null){
                            for (var t=0;t<app.bList.length;t++){
                                app.idList.push(app.bList[t].bigReportId)
                            }
                            if(app.idList.indexOf(reportId)>-1){
                                app.dataShow=true;
                            }else{
                                app.dataShow=false;
                            }
                            $("#d_loading").hide();
                            $("#getButtom").show();
                        }else {
                            app.bList=[];
                            app.dataShow=false;
                            $("#d_loading").hide();
                            $("#getButtom").show();
                        }
                    }

                }
            });
            var reportId=$("#infoDataId").val();
            var packagePrice=$("#packagePrice").val();
            var eventLabel=$("#eventLabel").val();
            this.likeType(eventLabel,reportId,packagePrice);
        },
        //购买产品
        buyBigDate:function () {
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
                            $("#wjf-tootips").modal('hide');
                            var zCredit=$("#zCredit").text();
                            var pCredit=$("#pCredit").text();
                            var str=pCredit.split("微积分");
                            var sd=parseInt(str[0]);
                            var sd1=parseInt(zCredit);
                            $("#shortCredit").text((sd-sd1)+str[1]);
                            $("#pay-wjf").modal('show');
                            // goPay(data.obj);
                        }else if(data.status==2){
                            if(data.obj == 18){
                                location.href =  njxBasePath +'/createAnalysis?createType=1';
                            }else if(data.obj == 32){
                                location.href = njxBasePath + "/weibo/createWeiBoAnalysis?createType=1"
                            }else if(data.obj  == 21){
                                location.href =  njxBasePath +"/compet/productsAnalysis";
                            }else if(data.obj  == 22){
                                location.href = njxBasePath + '/weiboAnalysis/weiboAnalysisIndex';
                            }else if(data.obj  == 68||data.obj  == 69||data.obj  == 70){
                                // var infoDataId=$("#infoDataId").val();
                                // location.href = njxBasePath + '/getBigDataDetail?id='+infoDataId;
                                $("#wjf-tootips").modal('hide');
                                $("#pay-success").modal('show');
                            }else{
                                location.href =  njxBasePath + "/userCenter/goBuy?type=0";
                            }
                        }else if(data.status == 3){
                            location.href = njxBasePath +"/userCenter/renewProductPackage";
                        }
                    }
                });
            }
        },
        goBuyed:function () {
            var infoDataId=$("#infoDataId").val();
            location.href = njxBasePath + '/getBigDataDetail?id='+infoDataId;
        },
        // 跳转 (支付宝 微信 支付宝)
        goPay:function (payRecordId) {
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
        },

        // getshow: function (type) {
        //     var str="";
        //     var packagePrice=$("#packagePrice").val();
        //     if(){
        //         if(packagePrice!=0){
        //             str="article-body article-lock";
        //         }else{
        //             str="article-body";
        //         }
        //     }else{
        //
        //     }
        //     return str;
        // },
        //获取待支付金额
        commonBuyOrderFee:function (){
            app.orderFeeFlag++;
            setTimeout(function(){
                app.orderFeeFlag--;
                if(app.orderFeeFlag == 0){
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
        },
        //购买
        // goPayreport: function () {
        //     // var adminWb=$("#adminWb").val();
        //     // var id=adminWb.id;
        //     var packagePrice=$("#packagePrice").val();
        //     var sf=packagePrice+'00';
        //     var credit=$("#credit").val();
        //     var timestamp = (new Date()).valueOf();
        //     // $("#commonBuyTimeFlag").val(timestamp);
        //     var type;
        //     if (packagePrice === 399) {
        //         type=68;
        //         // $('#commonBuyId').val("68");
        //     } else if (packagePrice === 299) {
        //         type=69;
        //         // $('#commonBuyId').val("69");
        //     } else {
        //         type=70;
        //         // $('#commonBuyId').val("70");
        //     }
        //     // var param = {
        //     //     commonBuyBigReportId : id,
        //     //     commonBuyId : type,
        //     //     commonBuyTimeFlag:timestamp
        //     // };
        //
        //     $("#zCredit").text(credit);
        //     $("#pCredit").text(packagePrice+'00');
        //     if(credit<sf){
        //         $("#pay-wjf").modal('show');
        //     }else{
        //         $("#wjf-tootips").modal('show');
        //     }
        //     // $("#wjf-tootips").show();
        //
        // },
        doPayPrice:function(){
            var packagePrice=$("#packagePrice").text();
            var param = {
                commonBuyBigReportId : id,
                commonBuyId : type,
                commonBuyTimeFlag:timestamp
            };
            $.ajax({
                url: njxBasePath + '/pay/confirmOrderV3',
                type: 'POST',
                cache: false,
                data: param,
                success: function (data) {

                }
            });
        },
        init:function(){
            var adminWb=$("#adminWb").value();
            var id=adminWb.id;
            var packagePrice=adminWb.packagePrice;
            $.post(njxBasePath+'/getBigDataList',param,function(result){
                app.totalCount = 0;
                if(result.code == "0000"){
                    var data=result.data;
                    for(var i=0;i<data.length;i++){
                        var item=data[i];
                        app.list.push(item);
                    }
                    app.appPage=1;
                    app.appPagesize=15;
                    app.totalCount=result.totalCount;
                    if (app.totalCount < 10 ) {
                        app.loadMoreFlag=false;
                        $("#d_loading").hide();
                        $("#getButtom").show();
                    } else {
                        app.loadMoreFlag=true;
                    }
                }else {
                    app.loadMoreFlag=false;
                    $("#d_loading").hide();
                    $("#getButtom").show();
                }
            });
            this.$nextTick(function () {

            });
        },
        loadMore:function(){
            this.page++;
            var param = {
                page : this.page,
                pagesize : 15,
                type:0
            };
            // app.appPage++;
            // param.page=app.appPage;
            this.initData(param);
        },searchRefresh:function(index,item){
            var type=item.type;
            this.selectType=type;
            this.activeClass = type;
            var param = {
                page : 1,
                pagesize : 15,
                type:type,
            };
            app.list=[];
            this.initData(param);
        }

    }
});

