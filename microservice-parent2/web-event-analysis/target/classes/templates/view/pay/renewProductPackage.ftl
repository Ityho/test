<#include "../../init_top.ftl" >

<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/style.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/font-icon.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/layoutpay.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/single.css?v=${SYSTEMINITTIME}" />

<link rel="stylesheet" href="${staticResourcePathH5}/css/style.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/layout.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/layout20160825.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/loading.css" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/tips.css?v=${SYSTEM_INIT_TIME }" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/animation.css?v=${SYSTEM_INIT_TIME }" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/fsgallery.css?v=${SYSTEM_INIT_TIME }" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/fonts-icomoon.css?v=${SYSTEM_INIT_TIME }"/>
<link rel="stylesheet" href="${staticResourcePathH5 }/css/iconfont.css?v=${SYSTEM_INIT_TIME }"/>
<link rel="stylesheet" href="${staticResourcePathH5 }/css/loading.css?v=${SYSTEM_INIT_TIME }" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/sweetalert.css?v=${SYSTEM_INIT_TIME }">
<link rel="stylesheet" href="http://at.alicdn.com/t/font_189126_38986hdj15ib2o6r.css?v=${SYSTEM_INIT_TIME }">

<script src="${njxBasePath}/js/productBuy/jquery.min.js"></script>
<script src="${njxBasePath}/js/productBuy/wyrem.js"></script>
<script src="${njxBasePath}/js/productBuy/icheck.js"></script>
<script src="${njxBasePath}/js/productBuy/iscroll.js"></script>
<script src="${njxBasePath}/js/navigate.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${njxBasePath}/js/common-order.js?v=${SYSTEMINITTIME}"></script>
<style type="text/css">
    .selected
    {
        width: 100%; background: #ffffff;margin-bottom: 11px;overflow: hidden;
    }
    .selected h2{padding: 10px;line-height: 22px;float:left;}
    .selected h2.cur{padding: 10px;line-height: 22px;float:left;color:#fc6921}
    .selected h2 i{ width: 4px;height:22px; float: left;margin-right: 8px;margin-top: 0px; }
    .tip-bar{
        font-size: 13px;
        padding: 10px 20px;
        background: #FFEAE8;
        color: #E93A2C;
        position: relative;
        padding-left: 45px;
    }
    .tip-bar i{
        font-size: 14px;
        position: absolute;
        top: 13px;
        left: 20px;
    }

</style>
</head>
<body data-ng-controller = "initController">
<!--主要内容 start-->
<div id="container" style="margin-top:0px;">
    <section class="section" style="border-left:solid 6px #fca832;">
        <h2 class="float_l">续费管理</h2>
        <div style="margin-top: 10px;margin-right:20px;" class="float_r">
            <a href="${njxBasePath}/user" class="">返回</a></div>
    </section>
    <!--         <div class="tip-bar" style="margin-top: -11px;"><i class="iconfont icon-tongzhi"></i> -->
    <!--         	通知：由于信息监测的功能调整，过期方案将于2019年7月1日全部清除，请微友们知悉。 -->
    <!--         </div> -->

    <section class="selected selected1" style="border-radius:0px;height:40px;line-height:20px;position: relative;">
        <h2 data-ng-click = "doFindAllKeyword(1,$event)" style="width:45%;height:18px;text-align:center;padding:10px 0px;" class="cur">急需续费</h2>
        <h2 class="float_l" style="color:#D8D8D8;width:10%;padding:10px 0px;text-align: center;">|</h2>
        <h2 data-ng-click = "doFindAllKeyword(0,$event)" class="float_l" id="openy" style="height:18px;width:45%;text-align: center;padding:10px 0px;">全部</h2>
    </section>
    <section class="section" data-ng-init = "doFindAllKeyword(1)" style="max-height:350px;overflow:scroll !important;">
        <header style="background: #fff;padding:0px 10px;overflow: hidden;top:104px;">
            <ul style="overflow: hidden;height:40px;border-bottom:solid 1px;">
                <li class="float_l">
                    <div>
                        <ul style="overflow: hidden;">
                            <li class="float_l"><i class="float_l icon-circle-chooseAll" style="width: auto;"></i></li>
                            <li class="float_l" style="line-height:40px;"><label style="color: #2d2d2d;">全选</label></li>
                        </ul>
                    </div>
                </li>
                <li class="float_r" style="margin:5px 0px 5px 0px;color: #2d2d2d;padding:0px;height: 30px;line-height: 30px;border-radius:3px;">到期时间</li>
            </ul>
        </header>
        <div style="padding-top: 40px;">
            <ul data-ng-repeat = "keyword in keywords" style="line-height: 40px;height: 40px;padding:0px 10px;overflow: hidden;">
                <li class="float_l" style="width: 38px;height:100%;"><i class="float_l icon-circle-choose" data-key="{{keyword.keywordId}}" data-ng-click = "iconChoose($event)"></i></li>
                <li class="float_l" style="width: 40%;height:100%;line-height:40px;" data-ng-bind = "keyword.keywordName"></li>
                <li class="float_r" data-ng-class = "{'f_c2':keyword.overdue}" style="width: 40%;height:100%;line-height:40px;text-align:right;" data-ng-bind = "keyword.overdue? '已过期':keyword.validEndDate | limitTo:10"></li>
            </ul>
        </div>
        <div class="spinner" id="loading_gif">
            <div class="bounce1"></div>
            <div class="bounce2"></div>
            <div class="bounce3"></div>
        </div>
    </section>
    <section style="position: fixed;bottom:0px;width:100%;background: #e4e4e6;">
        <div class="senior">
            <button type="button" style="background-color: #fd8c25; border: solid 0px; color: #fff;border-radius: 6px;" id = "buyKeyword">续费</button>
        </div>
    </section>
</div>


<div class="popver popver-single" id="singlediv">
    <div class="pop-top" style="height: 150px;">
        <h3 class="pop-title" style="padding-top: 5px;margin-bottom: 25px;"><span>续费监测方案</span></h3>
        <p class="fz11">通过设置关键词获取您关注的全网信息，可进行50次修改。</p>
    </div>
    <div class="pop-con">
        <s:iterator value="keywordProductList" var="item" status="status">
            <s:if test="#item.productPackageId != 33 && #item.productPackageId != 35 && #item.productPackageId != 36">
                <section>
                    <ul class="pay-choose clearfix">
                        <li class="border0">
                            <span class="fc-gray6 g_4"><s:property value="#item.packageName" /></span>
                            <span class="fc-p-red g_3">&yen;<s:number name="#item.packagePrice" /></span>
                            <div class="icheckbox-list" style="margin: 0;">
                                <s:if test="#status.index<1">
                                    <input type="radio"  id="payPackage1" checked="" name="payPackage" value="<s:property value="#item.productPackageId" />">
                                </s:if>
                                <s:else>
                                    <input type="radio"  name="payPackage" value="<s:property value="#item.productPackageId" />">
                                </s:else>
                            </div>
                        </li>

                    </ul>
                </section>
            </s:if>
        </s:iterator>
        <section>
            <ul class="pay-choose clearfix">
                <li class="borderb">
                    <span class="fc-gray6">购买数量</span>
                    <form  name="payForm" method="post" id = "selfProductForm">
                        <input id = "payType" type="hidden" name="payType" value="00"/>
                        <input id = "productPackageId"  name="productPackageId" type="hidden" name="myProducts['product1001'].keywordId"  value="3"/>
                        <input id = "useCredit1" type="hidden" name="useCredit" value="true"/>
                        <input id = "orderType" type="hidden" name="orderType" value="2"/>
                        <input id = "keywordIds" type="hidden" name="keywordIds"/>
                        <div class="addsub">
                            <a  class="sub" onclick="minSinglePackageCount('#product1001Num', 1)"></a>
                            <input type="number"  id = "product1001Num" onchange="getBuyGroupProductFee(1, this.value)" name="myProducts['product1001'].keywordPackageNum" value="1"  />
                            <a  class="add" onclick="addPackageCount('#product1001Num', 1)"></a>
                        </div>
                    </form>
                </li>
            </ul>

        </section>


        <div class="paymode" style="display: none;" id="paymode">
            <ul>
                <li class="border0">
                    <p class="fz15 fc-gray6"><i class="icon-pay pay-weijifen"></i>微积分支付</p>
                    <div class="icheckbox-list">
                        <input id="in_weijifen" type="radio" checked="" name="paybox" value="00">
                    </div>
                </li>
                <s:if test = "#attr.userPlatform == 2">
                    <li class="border0" id="pay-weibo">
                        <p class="fz15 fc-gray6"><i class="icon-pay pay-weibo"></i> 微博支付</p>
                        <div class="icheckbox-list">
                            <input type="radio"   name="paybox" value="03">
                        </div>
                    </li>
                </s:if>
                <s:if test = "#attr.userPlatform == 3">
                    <li class="borderb" id="pay-weixin">
                        <p class="fz15 fc-gray6"><i class="icon-pay pay-weixin"></i> 微信支付</p>
                        <div class="icheckbox-list">
                            <input type="radio"   name="paybox" value="02"/>
                        </div>
                    </li>
                </s:if>
            </ul>
        </div>

        <p class="fz17 fc-black taligncenter" style="margin: 25px 0;">应付金额：<span class="fc-p-red fz23" id="creditTotalFee1"></span></p>
        <a  class="btn-pay" id="goProductCartBtn" onclick="showTips(1)">马上购买</a>
    </div>


</div>
<!--微积分不足-->
<div class="fail-popver" id="fail">
    <div class="fail-tit">
        <img src="${njxBasePath}/images/productBuy/fail.png" style="width: 50px;">
        <p class="mt10">微积分不够啦</p>
    </div>
    <div class="taligncenter fail-txt">
        <p>当前可用微积分为<span class="fc-p-red" id="nowwjf"></span></p>
        <p>还需要<span class="fc-p-red" id="needwjf"></span>才能购买</p>
    </div>
    <div class="btn-footer">
        <a href="javascript:;" class="pay-close">取消支付</a>
        <a href="${njxBasePath}/userCenter/goBuy?type=0" class="btn-recharge">去充值</a>
    </div>
</div>
<!--确认支付-->
<div class="fail-popver" id="success">
    <div class="fail-tit">
        <p class="mt10" style="font-size: 18px;margin-top: 25px;">确认支付</p>
    </div>
    <div class="taligncenter fail-txt">
        <br>
        <p>当前可用微积分为<span class="fc-p-red" id="nowwjf2">${admin.creditAmount}</span></p>
        <p>本次需消耗<span class="fc-p-red" id="needwjf2"></span></p>
    </div>
    <div class="btn-footer">
        <a href="javascript:;" class="pay-close">取消</a>
        <a  id="goPay2" class="btn-recharge" style="background:  #f18d00;color:  #FFFFFF;" onclick="buyProductpro()">确认支付</a>
    </div>
</div>

<div id="jiazai" style="z-index: 99;display:  none;position: fixed;top: 45%;left: 45%;">
    <img src="${njxBasePath}/images/productBuy/jiazai.gif">
</div>
<div class="popver-mask"></div>


<script>
    var njxBasePath="${njxBasePath}";
</script>

<script src="${staticResourcePathH5}/js/angular/angular.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/angular/orderAngular.js?v=${SYSTEM_INIT_TIME}" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        $("#buyKeyword").on("click",function(){
            if(!curNum()){
                swal("请选择要续费的监测方案!");
                return;
            }
            $("#keywordIds").val(curNum());
            gochat();
            /* showBuy({type:15,buyBefore:buyBefore});  */
        });
        function buyBefore(){
            $(".openBuyPrompt").find("#keywordIds").val(curNum());
        }
        function curNum(){
            var keywordIds = "";
            $(".icon-circle-seleted").each(function(i,item){
                if($(item).data("key"))
                    keywordIds += $(item).data("key")+",";
            });
            if(keywordIds)
                keywordIds = keywordIds.substring(0,keywordIds.length-1);

            return keywordIds;
        }
    });

    function gochat(){
        commonBuyProductFee();
        $('.popver-single').show();
        $('.popver-mask').show();
    }
</script>
<div class="zhezhao"></div>
<c:set var="type" value="2"></c:set>
<#include "../../buttom.ftl" >

<script>
    $(document).ready(function() {
        $('.icheckbox-list input').on('ifChecked', function(event) {
            var value = $(this).val();
            console.log(value);
            if(value=="3"){
                $("#productPackageId").val("3");
            }
            if(value=="34"){
                $("#productPackageId").val("34");
            }
            if(value=="03"){
                $("#payType").val("03");
                $("#useCredit1").val("false");
            }
            if(value=="02"){
                $("#payType").val("02");
                $("#useCredit1").val("false");
            }
            if(value=="00"){
                $("#payType").val("00");
                $("#useCredit1").val("true");
            }
            commonBuyProductFee();
        }).iCheck({
            checkboxClass: 'icheckbox_square-red',
            radioClass: 'iradio_square-red',
            increaseArea: '20%'
        });
    });
    /*打开弹框*/
    /* $('#incidentTitle, #keyword').on('click', function() {
        if(${admin.userWeiboAnalysisValidCount}<1){
				    commonBuyProductFee();
					$('.popver-single').show();
					$('.popver-mask').show();
				}
			}); */
    $('.btn-quan').on('click', function() {
        $('.popver1').show();
//				$('.popver-mask').show();
    });
    /*关闭*/
    $('.btn-close').on('click', function() {
        $(this).parent().hide();
//				$('.popver-mask').hide();
    })
    $('.pay-close').on('click', function() {
        $('.fail-popver').hide();
        $('.popver-mask').hide();
        $('#singlediv').hide();
        $('html').removeClass('noscroll');
    });
    $('.popver-mask').on('click', function() {
        $('.popver').hide();
        $('.about-popver').hide();
        $('.fail-popver').hide();
        $(this).hide();
        $('html').removeClass('noscroll');
    });
</script>

</body>
</html>