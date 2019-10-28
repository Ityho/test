<#include "../../top.ftl">
<#--<%@ include file="../commonJsp/dwr.ftl" %>-->
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/style.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/font-icon.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/layoutpay.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/single.css?v=${SYSTEMINITTIME}" />
<script src="${njxBasePath}/js/productBuy/jquery.min.js"></script>
<script src="${njxBasePath}/js/productBuy/wyrem.js"></script>
<script src="${njxBasePath}/js/productBuy/icheck.js"></script>
<script src="${njxBasePath}/js/productBuy/iscroll.js"></script>

<script src="${njxBasePath}/js/common-order.js?v=${SYSTEMINITTIME}"></script>
<script>
    var njxBasePath ="${njxBasePath!""}";
</script>
<script src="${njxBasePath}/js/navigate.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript">saveOperateLog('竞品分析','1024');</script>
<!--主要内容 start-->
<style type="text/css">
    .selected{
        width: 100%; background: #ffffff;margin-bottom: 11px;overflow: hidden;
    }
    .selected h2{padding: 10px;line-height: 22px;float:left;}
    .selected h2.cur{padding: 10px;line-height: 22px;float:left;color:#fc6921}
    .selected h2 i{ width: 4px;height:22px; float: left;margin-right: 8px;margin-top: 0px; }
</style>
<div id="container">
    <section class="section" style="margin-bottom:0px;border-left:solid 5px #fca832;">
        <h2 class="float_l">竞品分析</h2>
        <div style="margin-top: 10px;margin-right:20px;" class="float_r"> <a href="${njxBasePath}/compet/productsReport" class="">返回</a></div>
<#--         <a href="javascript:history.back();" class="">返回</a>-->
    </section>

    <!--竞品导航 start -->
    <!--请直接输入词语弹窗 start -->
    <section class="selected" style="border-radius:0px 0px 0px 0px;margin-top:20px;height:40px;line-height:20px;">
        <h2  style="color:#333333;width:100%;height:18px;text-align:center;"class="cur"id = "openyq">词语对比</h2>
<#--        <h2  style="width:50%;height:18px;text-align:center;"class="cur"id = "openyq">词语对比</h2>-->
<#--        <h2  class="float_l" style="margin-left:-15px;color:#D8D8D8;">|</h2>-->
<#--        <h2  class="float_l"id = "openy" style="height:18px;margin-left:20px;">方案对比</h2>-->
    </section>
    <div id="buyPrompt_s" style="display: block; top: 10%;">
        <div class="content-body contrastSet" style="height: 240px !important;">
            <form id = "frmPopWin1" name="frmPopWin1" action="" method="post">
                <input type="hidden" value="${token!0}" name = "token">
                <input type="hidden"  name = "timeDomain" id="timeDomain" value='24'>
                <div style="text-align:center;margin-top:70px;">
                    <section class="section">
                        <article class="context context2" style="line-height: 22px;">
                            <ul class="align_c hot_num" id="setTime">
                                <li class="g_26 click" value="24"><p>24小时</p></li>
                                <li class="g_26" value="3"><p>3天</p></li>
                                <li class="g_26" value="7"><p>7天</p></li>
                                <li class="g_26" value="10"><p>10天</p></li>
                                <li class="g_26" value="30"><p>30天</p></li>
                            </ul>
                        </article>
                    </section>
                </div>
                <section class="section" >
                    <div class="keyWord">
                        <div id="kw" style="margin-top:10px;margin-left:-10px;">
                            <label><input type="text" class="input" placeholder="输入分析词语" name="pak.keyword1" value="${(pak.keyword1)!""}">
                                <div class="empty abs" >×</div>
                            </label>
                            <label><input type="text" class="input" placeholder="输入分析词语" name="pak.keyword2" value="">
                                <div class="empty abs" >×</div>
                            </label>
                            <label><input type="text" class="input" placeholder="输入分析词语" name="pak.keyword3" value="">
                                <div class="empty abs" >×</div>
                            </label>
                        </div>

                        <div id="keyWordShow" style="display: none; margin-left: -10px;">
                            <label><input type="text" class="input"
                                          placeholder="输入分析词语" name="pak.keyword4" value="">
                                <div class="empty abs">×</div> </label> <label><input
                                type="text" class="input" placeholder="输入分析词语"
                                name="pak.keyword5" value="">
                            <div class="empty abs">×</div> </label> <label><input
                                type="text" class="input" placeholder="输入分析词语"
                                name="pak.keyword6" value="">
                            <div class="empty abs">×</div> </label>
                        </div>
                    </div>
                    <div style="height:20px;"></div>
                </section>
                <section class="section" style="width:100%;height:120px;margin-top:-10px;">
                    <div style="height:40px;float:left;width:50%;margin-top:30px;position:relative">
                        <a href="javascript:void(0)"  class="stfenxi" style="border:solid 0px;"onclick="gochat()">开始分析</a>
                    </div>
                    <div style="height:40px;float:left;width:50%;margin-top:30px;">
                        <span class="cafenxi"onclick="location.href= '${njxBasePath}/analysis' "> 取消</span>
                    </div>
                </section>
            </form>
        </div>

    </div>
    <div id="buyPrompt_y"  style="display: none; top: 10%;">
        <div class="content-body" style="height: 240px !important;">
            <input type="hidden" id="title" name = "title" />
            <form method="post" action="" id="pForm">
                <input type="hidden" value="" name="pabId" id="pabId">
            </form>
            <form id = "frmPopWin2" name="frmPopWin2" action="" method="post">
                <input type="hidden" value="${token}" name = "token">
                <input type="hidden" name="keywordIds" id="keywordIds" value=""/>
                <input type="hidden" id="shareCode"/>
                <input type="hidden"  name = "timeDomain" id="timeDomain2" value='24'>
                <div style="text-align:center; margin-top:70px;">
                    <section class="section">
                        <article class="context context2" style="line-height: 22px;">
                            <ul class="align_c hot_num" id="setTime2">
                                <li class="g_26 click" value="24"><p>24小时</p></li>
                                <li class="g_26" value="3"><p>3天</p></li>
                                <li class="g_26" value="7"><p>7天</p></li>
                                <li class="g_26" value="10"><p>10天</p></li>
                                <li class="g_26" value="30"><p>30天</p></li>
                            </ul>
                        </article>
                    </section>
                </div>
                <section class="section">
                    <div class="keyWordList">
                    <ul>
					<#list kwList! as item>
                        <li style="border-left:solid 0px;background-color:#FFF;border-bottom:solid 1px #dfdfdf;" id="<s:property value='#item.keywordId'/>">
                            <p style="background-color:#FFF;">
                                <img src="${njxBasePath}/images/application/circleDouble.png" style="float: left; margin-right: 10px; width: 23px; height: 23px;"/>
								<#if test="#item.keywordName.length()>10">
								${item.keywordName.substring(0,10)}...
								<#else>
								${(item.keywordName)!""}
								</#if>
                            </p>
                        </li>
						<#if item_index%4==3 ></ul><ul></#if>
					</#list>
                    </ul>
                    </div>
                </section>
                <section  style="width:100%;height:120px;margin-top:20px;">
                    <div style="height:40px;float:left;width:50%;margin-top:30px;position:relative">
                        <a href="javascript:void(0)"class="stfenxi" style="border:solid 0px;"onclick="gochat()">开始分析</a>
                    </div>
                    <div style="height:40px;float:left;width:50%;margin-top:30px;">
                        <span class="cafenxi"onclick="location.href=  '${njxBasePath}/analysis'"> 取消</span>
                    </div>
                </section>
            </form>
        </div>
    </div>
    <!--已有监测方案弹窗 end -->
    <!--加载-->
    <div class="loading">
        <div class="loadingBox">
            <div class="zp-loading la-2x">
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
            </div>
            <div class="mask"></div>
        </div>
    </div>

</div>
<#assign type=11>
<div id="shareNewsPOP" class="footPOP" style="display: none;">
    <ul class="footList fenxiang bdsharebuttonbox" data-tag="share_1">
        <li><a class="icon icon_2" data-cmd="tsina"></a>新浪微博</li>
        <li><a class="icon icon_1" data-cmd="weixin"></a>微信</li>
        <li><a class="icon icon_4" data-cmd="sqq"></a>QQ好友</li>
        <li><a class="icon icon_9" data-cmd="tqq"></a>腾讯微博</li>
        <li><a class="icon icon_5" data-cmd="qzone"></a>QQ空间</li>
    </ul>
</div>

<div class="popver popver-single" id="singlediv">
<#list productAnalysisProductList! as item>
    <div class="pop-top">
        <h3 class="pop-title"><span>竞品事件分析</span></h3>
        <span class="price">9.9元/次</span>
        <p class="fz11">输入您关注的品牌或企业与其竞争对手名称。微热点从全网数据中，对双方进行市场走势、媒体投放情况、负面信息、用户分析等方面进行对比分析。</p>
    </div>
    <div class="pop-con">
        <section>
            <ul class="pay-choose clearfix">
                <li class="borderb">
                    <span class="fc-gray6">购买数量</span>
                    <form  name="payForm" method="post" id = "selfProductForm">
                        <input id = "useCredit1" type="hidden" name="useCredit" value="true"/>
                        <input id = "payType" type="hidden" name="payType" value="00"/>
                        <input id = "productPackageId"  type="hidden" name="myProductDto.keywordId" value="${(item.productPackageId)!0}" />
                        <input id = "productFlag"  type="hidden" name="myProductDto.productFlag" value="product1005" />
                        <input id = "orderType" type="hidden" name="orderType" value="1"/>
                        <div class="addsub">
                            <a href="javascript:;" class="sub" onclick="minSinglePackageCount('#product1005Num', 5)"></a>
                            <input type="number" id = "product1005Num" onchange="getBuyGroupProductFee(5, this.value)"  name="myProducts['product${item.productPackageId}'].keywordPackageNum" value="1" />
                            <a href="javascript:;" class="add" onclick="addPackageCount('#product1005Num', 5)"></a>
                        </div>
                    </form>
                </li>
            </ul>
        </section>

        <div class="paymode" id="paymode">
            <ul>
                <li class="border0">
                    <p class="fz15 fc-gray6"><i class="icon-pay pay-weijifen"></i>微积分支付</p>
                    <div class="icheckbox-list">
                        <input id="in_weijifen" type="radio" checked="" name="paybox" value="00">
                    </div>
                </li>
				<#if userPlatform?? && userPlatform == 2>
                    <li class="border0" id="pay-weibo">
                        <p class="fz15 fc-gray6"><i class="icon-pay pay-weibo"></i> 微博支付</p>
                        <div class="icheckbox-list">
                            <input type="radio"   name="paybox" value="03">
                        </div>
                    </li>

				</#if>
				<#if userPlatform?? && userPlatform == 3>
                    <li class="borderb" id="pay-weixin">
                        <p class="fz15 fc-gray6"><i class="icon-pay pay-weixin"></i> 微信支付</p>
                        <div class="icheckbox-list">
                            <input type="radio"   name="paybox" value="02"/>
                        </div>
                    </li>
				</#if>
            </ul>
        </div>
        <div class="dis-section pay-quan" style="display: none;">
            <p class="fc-gray6">微积分支付抵用卷</p>
            <a class="btn-quan">
                <span class="fc-black">省100微积分：满1000减100</span>
                <i class="iconfont icon-rightjiantou"></i>
            </a>
        </div>
        <p class="fz17 fc-black taligncenter" style="margin: 25px 0;">应付金额：<span class="fc-p-red fz23" id="creditTotalFee1"></span></p>
        <a  class="btn-pay" id="goProductCartBtn" onclick="showTips()">马上购买</a>
    </div>
</#list>
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
        <p>当前可用微积分为<span class="fc-p-red" id="nowwjf2">${(admin.creditAmount)!}</span></p>
        <p>本次需消耗<span class="fc-p-red" id="needwjf2"></span></p>
    </div>
    <div class="btn-footer">
        <a href="javascript:;" class="pay-close">取消支付</a>
        <a  id="goPay2" class="btn-recharge" style="background:  #f18d00;color:  #FFFFFF;" onclick="buyProductpro()">确认支付</a>
    </div>
</div>
<div class="zhezhao" style="display: none;"></div>
<div class="popver-mask"></div>
<!--对比类型选择弹窗 start -->
<div id="buyPrompt" class="zz_content" style="display: none;">
    <div class="td_title rel">
        <h1>我要分析</h1>
        <a class="info_close abs">×</a>
    </div>
    <div class="content-body" style="height: 140px !important;overflow-y:scroll !important;">
        <div class="buttonGroup">
            <a class="button white" id = "opens">直接输入词语对比</a>
        </div>
    </div>
</div>
<!--对比类型选择弹窗 end -->
<div id="jiazai" style="z-index: 99;display:  none;position: fixed;top: 45%;left: 45%;">
    <img src="${njxBasePath}/images/productBuy/jiazai.gif">
</div>

<!--竞品分析功能过期提示弹窗 end-->
<script src='${staticResourcePath}/js/findResult.js'></script>
<#--<script src='${njxBasePath}/dwr/interface/NewsOperate.js'></script>-->
<script>
    if("${msg!""}"){
        $(".loadingMask").hide();
        swal("${msg!""}");
    }
    $(function() {
        $('.icheckbox-list input').on('ifClicked', function(event) {
            var value = $(this).val();
            console.log(value);
            if(value=="00"){
                $("#payType").val("00");
                $("#useCredit1").val("true");
            }else if(value=="03"){
                $("#payType").val("03");
                $("#useCredit1").val("false");
            }else if(value=="02"){
                $("#payType").val("02");
                $("#useCredit1").val("false");
            }else{
                $("#payType").val("00");
                $("#useCredit1").val("true");
            }
            commonBuyProductFee();
        }).iCheck({
            checkboxClass: 'icheckbox_square-red',
            radioClass: 'iradio_square-red',
            increaseArea: '20%'
        });

        /*打开弹框*/
        $('.btn-quan').on('click', function() {
            $('.popver1').show();
//		$('.popver-mask').show();
        });
        /*关闭*/
        $('.btn-close').on('click', function() {
            $(this).parent().hide();
//		$('.popver-mask').hide();
        })
        $('.pay-close').on('click', function() {
            $('.fail-popver').hide();
            $('.popver-mask').hide();
            $('html').removeClass('noscroll');
        });
        $('.popver-mask').on('click', function() {
            $('.popver').hide();
            $('.about-popver').hide();
            $(this).hide();
        });

        //监测方案选中效果
        $(".keyWordList > ul > li").on("click",function(){

            var num = 0;
            $(".keyWordList > ul > li").each(function(){
                if($(this).hasClass("active")){
                    num++;
                }
            });
            if(num >= 6){
                if($(this).hasClass("active")){
                    $(this).removeClass("active");
                }else{
                    swal("竞品分析最多可以选择6个方案！");
                    return false;
                }
            }else{
                $(this).toggleClass("active");
            }

        });
        $("#setTime li").on("touchend",function(){
            $("#setTime li").removeClass("click");
            $(this).addClass("click");
            var liTextStr = $(this).attr('value');
            document.getElementById('timeDomain').value=liTextStr;
        });
        $("#setTime2 li").on("touchend",function(){
            $("#setTime2 li").removeClass("click");
            $(this).addClass("click");
            var liTextStr = $(this).attr('value');
            document.getElementById('timeDomain2').value=liTextStr;
        });
        function isEq(list,str){
            for(var i = 1;i<list.length;i++){
                if(list[i].value == str && $.trim(str)!= ""){
                    return true;
                }else{
                    isEq(list,list[i].value);
                }
            }
        }
    });
    var flag = true;
    function gochat(){
        // $.post(actionBase + '/compet/doStartProductsAnalysis',
		// 	$("#frmPopWin1").serialize(),function(result) {
		// 		$(".loading").hide();
		// 		if(result.code != "0000"){
		// 			swal(result.message);
		// 		}else{
		// 			location.href = actionBase + "/compet/productsReport";
		// 		}
		// });
        // 检测竞品分析剩余次数
        $.ajax({
            url : actionBase + '/checkProValidCount',
            type : 'POST',
            success : function(result) {
                if (!$.isEmptyObject(result)) {
                    if (!result.isPro && result.productAnalysisCount < 1) {
                        commonBuyProductFee();
                        $('.popver-single').show();
                        $('.popver-mask').show();
                    }else{
                        console.log("这里");
                        if(checkInput()||checkSelected()){
                            if(flag){
                                flag = false;
                                if (result) {
                                    checkedKeywordId();
                                    if(checkInput()){
                                        $(".loading").show();
                                        $.post(actionBase + '/compet/doStartProductsAnalysis',
                                                $("#frmPopWin1").serialize(),function(result) {
                                                    $(".loading").hide();
                                                    if(result.code != "0000"){
                                                        swal(result.message);
                                                    }else{
                                                        location.href = actionBase + "/compet/productsReport";
                                                    }
                                                    flag = true;
                                                });
                                    }else if(checkSelected()){
                                        $(".loading").show();
                                        $.post(actionBase + '/compet/doStartProductsAnalysis',
                                                $("#frmPopWin2").serialize(),function(result) {
                                                    $(".loading").hide();
                                                    if(result.code != "0000"){
                                                        swal(result.message);
                                                    }else{
                                                        location.href = actionBase + "/compet/productsReport";
                                                    }
                                                    flag = true;
                                                });
                                    }
                                } else {
                                    swal("${msg!""}");
                                }
                            }
                        }
                    }
                }
            },error:function(XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest);
                console.log(textStatus);
                console.log(errorThrown);
            }
        });

    }
    //检查输入是否有信息
    function checkInput(){
        var inputValue = '';
        var num = 0;
        $(".keyWord").find("input").each(function(){
            console.log($(this).val());
            var thisValue = $(this).val();
            if($.trim(thisValue)!=null&&$.trim(thisValue)!=''){
                inputValue += $(this).val()+",";
                num++;
            }

        });
        var arr = $(".keyWord").find("input");
        var first = $(".keyWord").find("input")[0].value;
        if(num == 1){
            swal("提示","请选择两个及两个以上进行对比");
            return false;
        }
        /* else if(arr.length>3){
            swal("提示","选择的竞品不能超过三个");
            return false;
        } */
        for(var i=0;i<arr.length-1;i++){
            if(first == arr[i+1].value && first!= ''){
                swal("提示","不能输入相同内容对比");
                return false;
            } else {
                first = arr[i+1].value;
            }
        }
        if(inputValue!=''&&inputValue.length>1){
            inputValue = inputValue.substring(0, inputValue.length-1);
            return true;
        }else{
            return false;
        }
    }

    //检查选择是否有信息
    function checkSelected(){
        var num = 0;
        $(".keyWordList > ul > li").each(function(){
            var $this = $(this);
            if($this.attr('class') && $this.attr('class').indexOf('active')!=-1){
                num++;
            }
        });
        var arr = $(".keyWordList > ul > li.active > p");
        if(arr.length>0)
            var first = $.trim(arr[0].innerText);
        if(num == 1){
            swal("提示","请选择两个及两个以上进行对比");
            return false;
        }else if(arr.length>3){
            swal("提示","选择的竞品不能超过三个");
            return false;
        }
        for(var i=0;i<arr.length-1;i++){
            if(first == arr[i+1].innerText && first!= ''){
                swal("提示","不能输入相同内容对比");
                return false;
            } else {
                first = $.trim(arr[i+1].innerText);
            }
        }
        if(num>0){
            return true;
        }else{
            return false;
        }

    }
    //查询已经选取的监测方案

    function checkedKeywordId(){
        $("#keywordIds").val('');	//先清空隐藏域中的值
        var checkedIds = "";
        $(".keyWordList > ul > li").each(function(){
            var $this = $(this);
            if($this.attr('class') && $this.attr('class').indexOf('active')!=-1){
                checkedIds+=$this.attr('id')+",";	//选中的监测方案ID
            }
        });

        if(checkedIds!=','&&checkedIds.length>1){
            checkedIds = checkedIds.substring(0,checkedIds.length-1);
            $("#keywordIds").val(checkedIds);	//监测方案ID集合放到隐藏域中
        }
    }
    //获取开始时间
    function startTime(){
        var d = new Date(new Date().getTime() - 24*60*60*1000);  //24小时前
        var str = d.format('yyyy-MM-dd hh:mm:ss');
        return str;
    }
    //获取结束时间
    function endTime(){
        var str = new Date().format('yyyy-MM-dd hh:mm:ss');
        return str;
    }
    function addKeywords(){
        $("#addKeyword").css("display","none");
        $("#keyWordShow").css("display","block");
    }
    $(function() {
        $('#packageNum').blur(function() {
            var packageNum = $(this).val();
            if (!isNaN(packageNum)) {
                $('input[name="myProducts[\'product\'].keywordPackageNum"]').val(packageNum);
                var price = $('input[name="myProducts[\'product\'].keywordPackagePrice"]').val();
                var totalPrice = price * packageNum;
                var youhuiPrice = 0;
                if (totalPrice >= 5000)
                    youhuiPrice = totalPrice * 0.6;
                else if (totalPrice >= 4000)
                    youhuiPrice = totalPrice - 1500;
                else if (totalPrice >= 3000)
                    youhuiPrice = totalPrice - 1000;
                else if (totalPrice >= 2000)
                    youhuiPrice = totalPrice - 500;
                else if (totalPrice >= 1000)
                    youhuiPrice = totalPrice - 100;
                else
                    youhuiPrice = totalPrice;

                $('#yuanjiaFont').text('￥' + youhuiPrice.toFixed(2));
                if (youhuiPrice != totalPrice) {
                    $('#jieshengSpan').text('为您节省：' + (totalPrice - youhuiPrice).toFixed(2) + '元');
                    $('#jieshengSpan').css('display', '');
                } else {
                    $('#jieshengSpan').css('display', 'none');
                }
            }
        });
    });

</script>
<script type="text/javascript">
    $(function(){
        $(".footer .icon-cross").on("click",function(){
            $(".footer").fadeOut(300);
            $(".floatHeight").css("display","none");
        });
        $(".content-body").on("touchmove",function(){
            $(".keyWord").on("selectstart",function(){
                event.preventDefault();
            });
            $(".keyWordList ul li").on("selectstart",function(){
                event.preventDefault();
            });
        });

        $(".empty").on("click",function(){
            $(this).prev().val("");
        });

        $("#openy").on("click",function(){
            $(".keyWord input").val("");
            $("#buyPrompt_s").hide();
            $("#buyPrompt_y").show();
            $("#openy").addClass("cur");
            $("#openyq").removeClass("cur");

        });
        $("#openyq").on("click",function(){
            $(".keyWordList>ul>li").removeClass("active");
            $("#buyPrompt_y").hide();
            $("#buyPrompt_s").show();

            $("#openyq").addClass("cur");
            $("#openy").removeClass("cur");

        });

        $(".info_close").on("click",function(){
            $(".zhezhao").hide();
            $(".zz_content").hide();
            $(".keyWord").find("input").val("");
            $(".keyWordList > ul > li").removeClass("active");
            startScroll();
        })
        $(".zhezhao").on("click",function(){
            $(".info_close").click();
        })
        $(".form-actions .gray").on("click",function(){
            $(".info_close").click();
        });
        $(".form-actions2 .gray").on("click",function(){
            $(".info_close").click();
        });
        $("#openinput").on("click",function(){
            if($("#openinput i").hasClass("icon-plus")){
                $("#openinput i").addClass("icon-minus").removeClass("icon-plus");
                $("#keyWordShow").show();
                $("#keyWordShow").find("input").val("");
            }else{
                $("#openinput i").removeClass("icon-minus").addClass("icon-plus");
                $("#keyWordShow").hide();
            }
        })
        if($("input[name='pak.keyword1']").val()){
            $("#opens").click();
        }
        //分享
        var bdShareTitle, bdShareDesc, bdShareUrl;
        $(".icon-share").on("click",function(){
            $("#createTime").val($(this).find("input").val());
            $("#title").val($(this).data("title"));
            $(".zhezhao").fadeIn(300);
            $("#shareNewsPOP").fadeIn(300);
            $(".footPOP").addClass('downShow');
            $(".footPOP").removeClass('downOut');
            $(".prompPOP").removeClass('scaleShow');
            $(".prompPOP").addClass('scaleOut');
            var shareCode = $(this).parents("ul").data("sharecode");
            console.log(shareCode);
            $(".footPOP a").off("click");
            shareReportCallBack(shareCode);

        });
    });
    //分享的beforeclick事件
    function bdShareBeforeClick(cmd,config) {
        config.bdText = bdShareTitle;
        config.bdDesc = bdShareDesc;
        config.bdUrl = bdShareUrl;

        return config;
    }
    function productsAnalysisChatLook(id){
        $("#pForm").find("input").val(id);
        $("#pForm").attr("action","productsAnalysisChatLook.action");
        $("#pForm").submit();
    }
    function shareReportCallBack(data){
        //if(data!=null&&data!="") {
        var createTime = $("#createTime").val();
        var url = "";
        if(new Date(createTime).getTime()<= new Date('2016-06-1 18:00:00').getTime()){
            url = $("#viewPath").val()+$("#filePath").val()+".html";
        }else if(new Date(createTime).getTime()<= new Date('2016-06-30 18:00:00').getTime()){
            url = $("#viewPath").val()+$("#filePath").val()+"_h5.html";
        }else{
            url=$("#viewUri").val()+"/lookShareCodeReport.action?shareCode=" + data;
        }
        bdShareTitle = $("#title").val()+"比一比，来看看你关注的TA有多牛掰";
        bdShareDesc = '自定义分享摘要';
        bdShareUrl = url;

        window._bd_share_config = {
            common : {
                onBeforeClick : bdShareBeforeClick
            },
            share : []
        }
        with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
    }
</script>
</body>
</html>
