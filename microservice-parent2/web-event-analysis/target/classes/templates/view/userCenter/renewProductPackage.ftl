<#include "../../init_top.ftl" >
	<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/style.css?v=${SYSTEMINITTIME}" />
	<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/font-icon.css?v=${SYSTEMINITTIME}" />
	<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/layoutpay.css?v=${SYSTEMINITTIME}" />
	<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/single.css?v=${SYSTEMINITTIME}" />
		<script src="${njxBasePath}/js/productBuy/jquery.min.js"></script>
		<script src="${njxBasePath}/js/productBuy/wyrem.js"></script>
		<script src="${njxBasePath}/js/productBuy/icheck.js"></script>
		<script src="${njxBasePath}/js/productBuy/iscroll.js"></script>
		<script src="${njxBasePath}/js/navigate.js?v=${SYSTEM_INIT_TIME}"></script>
		<script src="${njxBasePath}/js/common-order.js?v=${SYSTEMINITTIME}"></script>
<style type="text/css">
.selected
{
 width: 100%; background: #ffffff;margin-bottom: 11px;overflow: hidden;/*box-shadow: 1px 0px 1px rgba(0, 0, 0, 0.2);*/ 


}
.selected h2{padding: 10px;line-height: 22px;float:left;}
.selected h2.cur{padding: 10px;line-height: 22px;/*border-bottom: solid 2px #fc6921;*/float:left;color:#fc6921}
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
<body data-ng-controller = "initController" style="background:#fff">
	<!--��Ҫ���� start-->
	<div id="container" style="margin-top:0px;">
		 <section class="section" style="border-left:solid 6px #fca832;">
            <h2 class="float_l">���ѹ���</h2>
            <div style="margin-top: 10px;margin-right:20px;" class="float_r"><%-- <a href="${njxBasePath}/keywordInfo.shtml" class="">����</a> --%>
            <a href="${njxBasePath}/weiboHome.shtml" class="">����</a></div>
        </section>

        <section class="selected selected1" style="border-radius:0px;height:40px;line-height:20px;position: relative;margin-bottom:1px;">
			<h2 data-ng-click = "doFindAllKeyword(1,$event)" style="width:45%;height:18px;text-align:center;padding:10px 0px;" class="cur">��������</h2>	
			<h2 class="float_l" style="color:#D8D8D8;width:10%;padding:10px 0px;text-align: center;">|</h2>		
			<h2 data-ng-click = "doFindAllKeyword(0,$event)" class="float_l" id="openy" style="height:18px;width:45%;text-align: center;padding:10px 0px;">ȫ��</h2>
		</section>
		<section class="section" data-ng-init = "doFindAllKeyword(1)" style="box-shadow:none;">
			<header style="background: #fff;padding:0px 10px;overflow: hidden;top:104px;display:none;">
				<ul style="overflow: hidden;height:40px;border-bottom:solid 1px;">
					<li class="float_l">
						<div>
							<ul style="overflow: hidden;">
								<li class="float_l"><i class="float_l icon-circle-chooseAll" style="width: auto;"></i></li>
								<li class="float_l" style="line-height:40px;"><label style="color: #2d2d2d;">ȫѡ</label></li>
							</ul>
						</div>
					</li>
					<li class="float_r" style="margin:5px 0px 5px 0px;color: #2d2d2d;padding:0px;height: 30px;line-height: 30px;border-radius:3px;">����ʱ��</li>
				</ul>
			</header>
			<div style="margin-bottom: 60px;">
			<ul data-ng-repeat = "keyword in keywords" style="line-height: 40px;height: 40px;padding:0px 10px;overflow: hidden;">
				<li class="float_l" style="width: 38px;height:100%;"><i class="float_l icon-circle-choose"  data-key="{{keyword}}" data-ng-click = "iconChoose($event,keyword)"></i></li>
				<li class="float_l" style="width: 40%;height:100%;line-height:40px;" data-ng-bind = "keyword.keywordName"></li>
				<li class="float_r" data-ng-class = "{'f_c2':keyword.overdue}" style="width: 40%;height:100%;line-height:40px;text-align:right;" data-ng-bind = "keyword.overdue? '�ѹ���':keyword.validEndDate | limitTo:10"></li>
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
				<button type="button" style="background-color: #fd8c25; border: solid 0px; color: #fff;border-radius: 6px;" id = "buyKeyword">����</button>
			</div>
		</section>
	</div>
	
	
	 <div class="popver popver-single" id="singlediv">
				<div class="pop-top" style="height: 150px;">
					<h3 class="pop-title" style="padding-top: 5px;margin-bottom: 25px;"><span>���Ѽ�ⷽ��</span></h3>
					<p class="fz11">ͨ�����ùؼ��ʻ�ȡ����ע��ȫ����Ϣ���ɽ���50���޸ġ�</p>
				</div>
				<div class="pop-con" id="app">
							<ul class="pay-choose clearfix" id="pay-choose">
								
							</ul>
					<section>
						<ul class="pay-choose clearfix">
							<li class="borderb">
								<span class="fc-gray6">��������</span>
								<form  name="payForm" method="post" id = "selfProductForm">
								<input id = "payType" type="hidden" name="payType" value="00"/>
								<input type="hidden" id="myProducts['product1001'].keywordId" name="myProducts['product1001'].keywordId" value="0" />
								<input id = "productPackageId"  name="productPackageId" type="hidden"  value="3"/>
								<input id = "renewPackageCount"  name="renewPackageCount" type="hidden"  value="1"/>
								<input id = "useCredit1" type="hidden" name="useCredit" value="true"/>
								<input id = "orderType" type="hidden" name="orderType" value="2"/>
								<input id = "keywordIds" type="hidden" name="keywordIds"/>
								<input id = "monitor_version" type="hidden" name="monitor_version"/>
								<div class="addsub">
									<a id="minSinglePackageCount" class="sub" onclick="minSinglePackageCount('#product1001Num', 1)"></a>
									<input type="number"  id = "product1001Num" onchange="getBuyGroupProductFee(1, this.value)" name="myProducts['product1001'].keywordPackageNum" value="1"  />
									<a  class="add" id="addPackageCount" onclick="addPackageCount('#product1001Num', 1)"></a>
								</div>
								</form>	
							</li>
						</ul>
							
					</section>
				
				
					<div class="paymode" style="display: none;" id="paymode">
						<ul>
							<li class="border0">
								<p class="fz15 fc-gray6"><i class="icon-pay pay-weijifen"></i>΢����֧��</p>
								<div class="icheckbox-list">
									<input id="in_weijifen" type="radio" checked name="paybox" value="00">
								</div>
							</li>
							<s:if test = "#attr.userPlatform == 2">
							<li class="border0" id="pay-weibo">
								<p class="fz15 fc-gray6"><i class="icon-pay pay-weibo"></i> ΢��֧��</p>
								<div class="icheckbox-list">
									<input type="radio"   name="paybox" value="03">
								</div>
							</li>
							</s:if>
							<s:if test = "#attr.userPlatform == 3">
							<li class="borderb" id="pay-weixin">
								<p class="fz15 fc-gray6"><i class="icon-pay pay-weixin"></i> ΢��֧��</p>
								<div class="icheckbox-list">
									<input type="radio"   name="paybox" value="02"/>
								</div>
							</li>
							</s:if>
						</ul>
					</div>
					<%-- <div class="dis-section pay-quan">
						<p class="fc-gray6">΢����֧�����þ�</p>
						<a class="btn-quan">
							<span class="fc-black">ʡ100΢���֣���1000��100</span>
							<i class="iconfont icon-rightjiantou"></i>
						</a>
					</div> --%>
					<p class="fz17 fc-black taligncenter" style="margin: 25px 0;">Ӧ����<span class="fc-p-red fz23" id="creditTotalFee1"></span></p>
					<a  class="btn-pay" id="goProductCartBtn" onclick="showTips(1)">���Ϲ���</a>
				</div>
			
			
</div>
<!--΢���ֲ���-->
<div class="fail-popver" id="fail">
			<div class="fail-tit">
				<img src="${njxBasePath}/images/productBuy/fail.png" style="width: 50px;">
				<p class="mt10">΢���ֲ�����</p>
			</div>
			<div class="taligncenter fail-txt">
				<p>��ǰ����΢����Ϊ<span class="fc-p-red" id="nowwjf"></span></p>
				<p>����Ҫ<span class="fc-p-red" id="needwjf"></span>���ܹ���</p>
			</div>
			<div class="btn-footer">
				<a href="javascript:;" class="pay-close">ȡ��֧��</a>
				<a href="${njxBasePath}/userCenter/goBuy?type=0" class="btn-recharge">ȥ��ֵ</a>
			</div>
</div>
<!--ȷ��֧��-->
<div class="fail-popver" id="success">
			<div class="fail-tit">
				<p class="mt10" style="font-size: 18px;margin-top: 25px;">ȷ��֧��</p>
			</div>
			<div class="taligncenter fail-txt">
			<br>
				<p>��ǰ����΢����Ϊ<span class="fc-p-red" id="nowwjf2">${admin.creditAmount}</span></p>
				<p>����������<span class="fc-p-red" id="needwjf2"></span></p>
			</div>
			<div class="btn-footer">
				<a href="javascript:;" class="pay-close">ȡ��</a>
				<a  id="goPay2" class="btn-recharge" style="background:  #f18d00;color:  #FFFFFF;" onclick="buyProductpro()">ȷ��֧��</a>
			</div>
</div>

		<div id="jiazai" style="z-index: 99;display:  none;position: fixed;top: 45%;left: 45%;">
			<img src="${njxBasePath}/images/productBuy/jiazai.gif">
        </div>
<div class="popver-mask"></div>
	
	<script src="${staticResourcePath}/js/vue.min.js?v=${SYSTEMINITTIME}"></script>
	<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js?v=${SYSTEMINITTIME}"></script>
	<script src="${staticResourcePathH5}/js/angular/angular.min.js?v=${SYSTEM_INIT_TIME}"></script>
	<script src="${staticResourcePathH5}/js/angular/orderAngular.js?v=${SYSTEM_INIT_TIME}" type="text/javascript"></script>
	<script src="${staticResourcePathH5}/js/vue/keyword/payChoose.js?v=${SYSTEM_INIT_TIME}" type="text/javascript"></script>
	<script type="text/javascript">
// 		var param={
// 			type:"2",
// 			monitor_version:$("monitor_version").val()
// 		};
// 		$.post(actionBase+"/view/usercenter1/getKeywordProduct.action",param,function(data){
// 			if(data!=null && data.code=="0000"){
// 				keywordProductList
// 			}
// 		});
	
		$(function(){
			$("#paymode input").on('ifClicked ifChanged', function(event){
			})
			.iCheck({
				checkboxClass: 'icheckbox_square-red',
				radioClass: 'iradio_square-red',
				increaseArea: '20%'
			});
			$("#buyKeyword").on("click",function(){
				if(!curNum()){
					swal("��ѡ��Ҫ���ѵļ�ⷽ��!");
					return;
				}
				$("#renewPackageCount").val("1");
				$("#product1001Num").val("1");
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
					if($(item).data("key")){
						keywordIds += $(item).data("key").keywordId+",";
						$('input[name="myProducts[\'product1001\'].keywordId"]').val($(item).data("key").keywordId);
						var product= $(item).data("key");
						var vers=$(item).data("key").monitorVersion;
						$("#monitor_version").val(vers);
					}
					
				});
				if(keywordIds)
					keywordIds = keywordIds.substring(0,keywordIds.length-1);
				
				return keywordIds;
			}
		});
		
		function gochat(){
			initData();
			
			$('.popver-single').show();
			$('.popver-mask').show();
		}
	</script>
	<div class="zhezhao"></div>
	<c:set var="type" value="2"></c:set>
	<#include "../../buttom.ftl" >
	
	<script>
			/*�򿪵���*/
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
			/*�ر�*/
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