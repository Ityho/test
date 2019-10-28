<#include "../../init_top.ftl" >
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/productBuy/style.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/productBuy/font-icon.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/productBuy/layoutpay.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/productBuy/single.css?v=${SYSTEM_INIT_TIME}" />




</head>

<body style="background: #f5f5f9;">
<input type="hidden" id="loginPlatform" value='${userPlatform}'/>
<!--  -->
<header>

	<h3 class="fz15"><a href="${njxBasePath}/user"><i class="icon-back"></i></a>产品选购</h3>
	<div class="layout-nav clearfix">
		<ul>
			<#if product_select == 1>
				<li>
					<a href="javascript:;">微积分充值</a>
				</li>
				<li class="active">
					<a href="javascript:;">产品购买</a>
				</li>

			<#else>
				<li class="active">
					<a href="javascript:;">微积分充值</a>
				</li>
				<li>
					<a href="javascript:;">产品购买</a>
				</li>
			</#if>
		</ul>
	</div>
</header>
<div class="layout-con">

	<div class="pay-content">
		<div class="item dis-b">
			<div class="myJf">
				<span>目前积分</span>
				<h3>${admin.creditAmount}</h3>
			</div>
			<section>
				<p class="payTitle">充值类型选择</p>
			</section>
			<form id="commonBuyCreditForm">
				<input type="hidden" name="buyCredit" value="true">
				<input id = "useCredit" type="hidden" name="useCredit" value="false"/>
				<input type="hidden" name="orderType" value="1"/>
				<section>
					<ul class="myjf-type clearfix" id="creditBuyTab">
						<#list packageListCredit! as item>
							<li  data-cmd='item.productPackageId' id='creditPackagesLI_${(item.productPackageId)!0}'>
								<input type="hidden" id="commonBuyCreditCount_${item.productPackageId!-1}" value='${item.creditCount!0}'/>
								<input type="hidden" id="commonBuyCreditPrice_${item.productPackageId!-1 }" value='${item.packagePrice!0}'/>
								<input type="hidden" name="myProducts['product_${item.productPackageId!-1 }'].keywordId" value='${item.productPackageId}' />

								<h3><i class="icon-jifen"></i><fmt:formatNumber  pattern="#,##0"/>${item.creditCount}</h3>
								<p><fmt:formatNumber  pattern="#0"/>${item.packagePrice}元</p>
								<#if admin.vipInfo.creditRechargeAmount == 0.0>
									<div class="myfirst-txt">
										首充额外赠送${item.giftCreditCount}微积分
									</div>
								</#if>
							</li>
						</#list>
					</ul>
				</section>
				<section class="dis-section">
					<p class="payTitle">您已选择</p>
					<p class="fc-gray9 fz12 mr15">清空已选<i class="iconfont icon-lajitong2 fz14 ml5"></i></p>
				</section>
				<div  id="nochoose" style="width: 100%; height: 4.0625rem;background:white;text-align:center; padding-top:5px;" ><img src="../../images/productBuy/nochoose.png" style="height: 55px;">&nbsp;&nbsp;<span style="color:#999999 !important;font-size:0.9375rem;">您当前还没有选择产品</span></div>
				<section>
					<ul class="pay-choose clearfix">
						<#list packageListCredit! as item>
							<li class="display-none">
								<span class="fc-gray9"><i class="icon-jifen"></i><fmt:formatNumber value="${item.creditCount}" pattern="#,##0"/>${item.creditCount}微积分</span>
								<span class="fc-black">￥<fmt:formatNumber value="${item.packagePrice}" pattern="#0"/>${item.packagePrice}</span>
								<div class="addsub">
									<a href="javascript:;" class="sub"  onclick="minPackageCount('#creditPackageCount_${item.productPackageId }', 0)"></a>
									<input type="number"  onchange="getBuyCreditFee(1, this.value)" id="creditPackageCount_${item.productPackageId }" name="myProducts['product_${item.productPackageId }'].keywordPackageNum" value="0"></input>
									<a href="javascript:;" class="add"  onclick="addPackageCount('#creditPackageCount_${item.productPackageId }', 0)"></a>
								</div>
							</li>
						</#list>
					</ul>
				</section>
				<!-- 优惠暂时不用 -->
				<section>
					<p class="payTitle">支付方式选择</p>
				</section>
				<div class="paymode">
					<ul class="paymode-ul">

						<#if userPlatform == 1>
							<li>
								<p class="fz15 fc-gray9"><i class="icon-pay pay-zhifubao"></i>支付宝支付</p>
								<div class="icheckbox-list">
									<input type="radio"  checked="" value="01" name="payType" />
								</div>
							</li>
<#--							<li>-->
<#--								<p class="fz15 fc-gray9"><i class="icon-pay pay-weixin"></i> 微信支付</p>-->
<#--								<div class="icheckbox-list">-->
<#--									<input type="radio"  checked="" value="02" name="payType" />-->
<#--								</div>-->
<#--							</li>-->
<#--							<li>-->
<#--								<p class="fz15 fc-gray9"><i class="icon-pay pay-weibo"></i> 微博支付</p>-->
<#--								<div class="icheckbox-list">-->
<#--									<input type="radio" checked="" name="payType" value="03">-->
<#--								</div>-->
<#--							</li>-->
						</#if>
						<#if userPlatform == 2>
							<li>
								<p class="fz15 fc-gray9"><i class="icon-pay pay-weibo"></i> 微博支付</p>
								<div class="icheckbox-list">
									<input type="radio" checked="" name="payType" value="03">
								</div>
							</li>
						</#if>
						<#if userPlatform == 3>
							<li>
								<p class="fz15 fc-gray9"><i class="icon-pay pay-weixin"></i> 微信支付</p>
								<div class="icheckbox-list">
									<input type="radio"  checked="" value="02" name="payType" />
								</div>
							</li>
						</#if>
					</ul>
				</div>
			</form>

			<div style="width: 100%; height: 4.0625rem;"></div>
			<div class="paymoney-fixed">
				<div class="dis-section pay-guize" >
					<p class="fz12">您当前累计充值<fmt:formatNumber value="${admin.vipInfo.creditRechargeAmount}" pattern="#,##0.00"/>${admin.vipInfo.creditRechargeAmount}元，
						<#if admin.vipInfo.level != 0>
						已享受<span class="fc-p-red"><fmt:formatNumber value="${admin.vipInfo.discount*10}" pattern="0.0"/>折</span>${admin.vipInfo.discount*10}优惠 </p>
					</#if>
					<#if admin.vipInfo.creditRechargeAmount == 0.0>
						可享受<span class="fc-p-red">首充</span>优惠 </p>
					</#if>
					<a href="javascript:;" class="btn-guizhe fz12">优惠规则 <i class="iconfont icon-rightjiantou fz14"></i></a>
				</div>
				<div class="paymoney">
					<p class="fz17 fw600">需支付<span class="fz23 fc-p-red" id="creditTotalFee"></span></p>
					<a  id="goPay1" class="btn-pay" onclick="buyProductwjf()">去支付</a>
				</div>
			</div>
		</div>

		<div class="item">
			<form id="selfProductForm" method="post">
				<input id = "orderType" type="hidden" name="orderType" value="1"/>
				<input id = "payType" type="hidden" name="payType" value="00"/>
				<input id = "useCredit1" type="hidden" name="useCredit" value="true"/>
				<section>
					<ul class="product-list">
						<#list packageListKeyword! as item>
							<#if item.productPackageId == 64>
								<input type="hidden" name="myProducts['product64'].keywordId" value="${item.productPackageId!-1}" />
								<input type="hidden" name="myProducts['product64'].keywordPackagePrice" value="${item.packagePrice!""}" />
								<li>
									<div>
										<h3 class="fz15 mb10"><i class="iconfont icon-kaiguan fc-p-orange fz17"></i> 监测方案(月)</h3>
										<p class="fz13 fc-gray6">剩余<span class="fc-p-orange">${admin.noUseKeywordCount!0}</span>个</p>
									</div>
									<div class="pro-right">
										<p class="fz14 fc-gray6 mb10"><span class="fc-p-orange">${item.packagePrice!"0"}</span>元/次</p>
										<div class="addnumber">
											<a href="javascript:void(0);" onclick="minPackageCount('#product64Num', 1)">&minus;</a>
											<input type="number" id="product64Num" onchange="getBuyGroupProductFee(1, this.value)" name="myProducts['product64'].keywordPackageNum" value="0" />
											<a href="javascript:void(0);" onclick="addPackageCount('#product64Num', 1)">&#43;</a>
										</div>
									</div>
								</li>
							</#if>
							<#if item.productPackageId == 65>
								<input type="hidden" name="myProducts['product65'].keywordId" value="${item.productPackageId!"-1"}" />
								<input type="hidden" name="myProducts['product65'].keywordPackagePrice" value="${item.packagePrice!0}" />
								<li>
									<div>
										<h3 class="fz15 mb10"><i class="iconfont icon-kaiguan fc-p-orange fz17"></i> 监测方案(年)</h3>
										<p class="fz13 fc-gray6">剩余<span class="fc-p-orange">${admin.noUseKeywordCount!0 }</span>个</p>
									</div>
									<div class="pro-right">
										<p class="fz14 fc-gray6 mb10"><span class="fc-p-orange">${item.packagePrice!0 }</span>元/次</p>
										<div class="addnumber">
											<a href="javascript:void(0);" onclick="minPackageCount('#product65Num', 1)">&minus;</a>
											<input type="number" id="product65Num" onchange="getBuyGroupProductFee(1, this.value)" name="myProducts['product65'].keywordPackageNum" value="0" />
											<a href="javascript:void(0);" onclick="addPackageCount('#product65Num', 1)">&#43;</a>
										</div>
									</div>
								</li>
							</#if>
						</#list>
						<#list packageListAnalysis! as item>
							<input type="hidden" name="myProducts['product18'].keywordId" value="${item.productPackageId!-1}" />
							<input type="hidden" name="myProducts['product18'].keywordPackagePrice" value="${item.packagePrice!0}" />
							<li>
								<div>
									<h3 class="fz15 mb10"><i class="iconfont icon-quanwang fc-p-blue5 fz17"></i> 全网事件分析</h3>
									<p class="fz13 fc-gray6">剩余<span class="fc-p-orange">${admin.userAnalysisValidCount!0 }</span>个</p>
								</div>
								<div class="pro-right">
									<p class="fz14 fc-gray6 mb10"><span class="fc-p-orange">99.00</span>元/次</p>
									<div class="addnumber">
										<a href="javascript:void(0);" onclick="minPackageCount('#product18Num', 2)">&minus;</a>
										<input type="number" id="product18Num" onchange="getBuyGroupProductFee(2, this.value)" name="myProducts['product18'].keywordPackageNum" value="0" />
										<a href="javascript:void(0);" onclick="addPackageCount('#product18Num', 2)">&#43;</a>
									</div>
								</div>
							</li>
						</#list>

						<#list packageListWeiboAnalysis! as item>
							<input type="hidden" name="myProducts['product32'].keywordId" value="${item.productPackageId!-1}" />
							<input type="hidden" name="myProducts['product32'].keywordPackagePrice" value="${item.packagePrice!0}" />
							<li>
								<div>
									<h3 class="fz15 mb10"><i class="iconfont icon-unie61d fc-p-orange fz17"></i> 微博事件分析</h3>
									<p class="fz13 fc-gray6">剩余<span class="fc-p-orange">${admin.userWeiboAnalysisValidCount!0 }</span>个</p>
								</div>
								<div class="pro-right">
									<p class="fz14 fc-gray6 mb10"><span class="fc-p-orange">99.00</span>元/次</p>
									<div class="addnumber">
										<a href="javascript:void(0);" onclick="minPackageCount('#product32Num', 3)">&minus;</a>
										<input type="number" id="product32Num" onchange="getBuyGroupProductFee(3, this.value)" name="myProducts['product32'].keywordPackageNum" value="0" />
										<a href="javascript:void(0);" onclick="addPackageCount('#product32Num', 3)">&#43;</a>
									</div>
								</div>
							</li>
						</#list>

						<#list packageListBrief! as item>
							<input type="hidden" name="myProducts['product19'].keywordId" value="${item.productPackageId!-1}" />
							<input type="hidden" name="myProducts['product19'].keywordPackagePrice" value="${item.packagePrice!0}" />
							<li>
								<div>
									<h3 class="fz15 mb10"><i class="iconfont icon-jianbaozhizuo fc-p-blue fz17"></i> 简报制作</h3>
									<p class="fz13 fc-gray6">剩余<span class="fc-p-orange">${admin.userBriefValidCount!0}</span>个</p>
								</div>
								<div class="pro-right">
									<p class="fz14 fc-gray6 mb10"><span class="fc-p-orange">9.90</span>元/次</p>
									<div class="addnumber">
										<a href="javascript:void(0);" onclick="minPackageCount('#product19Num', 4)">&minus;</a>
										<input type="number" id="product19Num" onchange="getBuyGroupProductFee(4, this.value)" name="myProducts['product19'].keywordPackageNum" value="0" />
										<a href="javascript:void(0);" onclick="addPackageCount('#product19Num', 4)">&#43;</a>
									</div>
								</div>
							</li>
						</#list>

						<#list packageListProductAnalysis! as item>
							<input type="hidden" name="myProducts['product21'].keywordId" value="${item.productPackageId!-1}" />
							<input type="hidden" name="myProducts['product21'].keywordPackagePrice" value="${item.packagePrice!0}" />
							<li>
								<div>
									<h3 class="fz15 mb10"><i class="iconfont icon-duibi fc-p-blue5 fz17"></i> 竞品分析</h3>
									<p class="fz13 fc-gray6">剩余<span class="fc-p-orange">${admin.userProductAnalysisValidCount!0}</span>个</p>
								</div>
								<div class="pro-right">
									<p class="fz14 fc-gray6 mb10"><span class="fc-p-orange">9.90</span>元/次</p>
									<div class="addnumber">
										<a href="javascript:void(0);" onclick="minPackageCount('#product21Num', 5)">&minus;</a>
										<input type="number" id="product21Num" onchange="getBuyGroupProductFee(5, this.value)" name="myProducts['product21'].keywordPackageNum" value="0" />
										<a href="javascript:void(0);" onclick="addPackageCount('#product21Num', 5)">&#43;</a>
									</div>
								</div>
							</li>
						</#list>

						<#list packageListSingleWeiboAnalysis! as item>
							<input type="hidden" name="myProducts['product22'].keywordId" value="${item.productPackageId!-1}" />
							<input type="hidden" name="myProducts['product22'].keywordPackagePrice" value="${item.packagePrice!0}" />
							<li>
								<div>
									<h3 class="fz15 mb10"><i class="iconfont icon-chuanbo fc-red5 fz17"></i> 微博传播效果分析</h3>
									<p class="fz13 fc-gray6">剩余<span class="fc-p-orange">${admin.userSingleWeiboAnalysisValidCount!0}</span>条</p>
								</div>
								<div class="pro-right">
									<p class="fz14 fc-gray6 mb10"><span class="fc-p-orange">0.01</span>元/条</p>
									<div class="addnumber">
										<a href="javascript:void(0);" onclick="minPackageCount('#product22Num', 6)">&minus;</a>
										<input type="number" id="product22Num" onchange="getBuyGroupProductFee(6, this.value)" name="myProducts['product22'].keywordPackageNum" value="0" />
										<a href="javascript:void(0);" onclick="addPackageCount('#product22Num', 6)">&#43;</a>
									</div>
								</div>
							</li>
						</#list>

					</ul>
				</section>
			</form>
			<section class="dis-section">
				<p class="payTitle">您已选择</p>
				<p class="fc-gray9 fz12 mr15">清空已选<i class="iconfont icon-lajitong2 fz14 ml5"></i></p>
			</section>
			<section>
				<div  id="nochoose2" style="width: 100%; height: 4.0625rem;background:white;text-align:center; padding-top:5px;" ><img src="../../images/productBuy/nochoose.png" style="height: 55px;">&nbsp;&nbsp;<span style="color:#999999 !important;font-size:0.9375rem;">您当前还没有选择产品</span></div>
				<ul class="pay-choose clearfix" id="groupPackBuyShow">

				</ul>
			</section>
			<!-- 优惠暂时不用 -->
			<section>
				<p class="payTitle">支付方式选择</p>
			</section>
			<div class="paymode" id="paymode">
				<ul class="paymode-ul">
					<li>
						<p class="fz15 fc-gray9 paymode-lf"><i class="icon-pay pay-weijifen"></i>微积分支付 <span class="pay-sign">享优惠</span></p>
						<div class="icheckbox-list">
							<input type="radio" checked="" name="paybox" value="00">
						</div>
					</li>
					<#if userPlatform == 1>
						<li id="pay-zhifubao" style="display: none;">
							<p class="fz15 fc-gray9"><i class="icon-pay pay-zhifubao"></i> 支付宝支付</p>
							<div class="icheckbox-list">
								<input type="radio"   name="paybox" value="01"/>
							</div>
						</li>

<#--						<li id="pay-weixin" style="display: none;">-->
<#--							<p class="fz15 fc-gray9"><i class="icon-pay pay-weixin"></i> 微信支付</p>-->
<#--							<div class="icheckbox-list">-->
<#--								<input type="radio"   name="paybox" value="02"/>-->
<#--							</div>-->
<#--						</li>-->
<#--						<li id="pay-weibo" style="display: none;">-->
<#--							<p class="fz15 fc-gray9"><i class="icon-pay pay-weibo"></i> 微博支付</p>-->
<#--							<div class="icheckbox-list">-->
<#--								<input type="radio"   name="paybox" value="03">-->
<#--							</div>-->
<#--						</li>-->
					</#if>
					<#if userPlatform == 2>
						<li id="pay-weibo" style="display: none;">
							<p class="fz15 fc-gray9"><i class="icon-pay pay-weibo"></i> 微博支付</p>
							<div class="icheckbox-list">
								<input type="radio"   name="paybox" value="03">
							</div>
						</li>

					</#if>
					<#if userPlatform == 3>
						<li id="pay-weixin" style="display: none;">
							<p class="fz15 fc-gray9"><i class="icon-pay pay-weixin"></i> 微信支付</p>
							<div class="icheckbox-list">
								<input type="radio"   name="paybox" value="02"/>
							</div>
						</li>

					</#if>
				</ul>
			</div>
			<div style="width: 100%; height: 4.0625rem;"></div>
			<div class="paymoney paymoney-fixed">
				<p class="fz17 fw600">需支付<span class="fz23 fc-p-red" id="creditTotalFee1"></span></p>
				<a id="goProductCartBtn" class="btn-pay" onclick="showTips()">去支付</a>
			</div>
		</div>
	</div>
</div>
<!--左右弹框-->
<div class="about-popver">
	<p class="popver-tit">微积分充值折扣是如何计算的？</p>
	<p class="txt">微积分购买所获得的折扣优惠按照用户在微热点累计 充值额而定。</p>
	<div class="popver-con clearfix">
		<div id="wrapper" style="touch-action: none;">
			<ul id="scroller" class="about-con">
				<li>
					<div class="linetop">
						<span class="line"></span>
						<span class="membpic"><img src="../../images/productBuy/icon_member.png"/></span>
					</div>
					<div class="discount">
						<p>累计充值<span class="fc-p-red">0元</span></p>
						<p>可享受<span class="fc-p-red">首充优惠</span></p>
					</div>
				</li>
				<li>
					<div class="linetop">
						<span class="line"></span>
						<span class="membpic"><img src="../../images/productBuy/icon_member1.png"/></span>
					</div>
					<div class="discount">
						<p>累计充值<span class="fc-p-red">100元</span></p>
						<p>可享受<span class="fc-p-red">9.8折优惠</span></p>
					</div>
				</li>
				<li>
					<div class="linetop">
						<span class="line"></span>
						<span class="membpic"><img src="../../images/productBuy/icon_member2.png"/></span>
					</div>
					<div class="discount">
						<p>累计充值<span class="fc-p-red">500元</span></p>
						<p>可享受<span class="fc-p-red">9.2折优惠</span></p>
					</div>
				</li>
				<li>
					<div class="linetop">
						<span class="line"></span>
						<span class="membpic"><img src="../../images/productBuy/icon_member3.png"/></span>
					</div>
					<div class="discount">
						<p>累计充值<span class="fc-p-red">1000元</span></p>
						<p>可享受<span class="fc-p-red">8.8折优惠</span></p>
					</div>
				</li>
				<li>
					<div class="linetop">
						<span class="line"></span>
						<span class="membpic"><img src="../../images/productBuy/icon_member4.png"/></span>
					</div>
					<div class="discount">
						<p>累计充值<span class="fc-p-red">5000元</span></p>
						<p>可享受<span class="fc-p-red">8.2折优惠</span></p>
					</div>
				</li>
				<li>
					<div class="linetop">
						<span class="line"></span>
						<span class="membpic"><img src="../../images/productBuy/icon_member5.png"/></span>
					</div>
					<div class="discount">
						<p>累计充值<span class="fc-p-red">10000元</span></p>
						<p>可享受<span class="fc-p-red">7.8折优惠</span></p>
					</div>
				</li>
			</ul>

		</div>

	</div>
	<div id="indicator" style="display: none;">
		<div id="dotty"></div>
	</div>
	<div class="btn-close delete"><img src="../../images/productBuy/icon_nclose.png" /></div>
</div>
<!--微积分充值抵用卷弹框-->
<div class="popver popver1">
	<p class="popver-tit">微积分充值抵用卷</p>
	<div class="popver-con clearfix">
		<ul>
			<li>
				<span class="fc-black">省10元：满100元减10</span>
				<div class="icheckbox-list">
					<input type="checkbox" checked="" name="options2" id="option10">
				</div>
			</li>
			<li>
				<span class="fc-black">省10元：满100元减10</span>
				<div class="icheckbox-list">
					<input type="checkbox" checked="" name="options2" id="option11">
				</div>
			</li>
		</ul>
	</div>
	<a href="javascript:;" class="btn-close">关闭</a>
</div>
<!--微积分支付抵用券弹框-->
<div class="popver popver2">
	<p class="popver-tit">微积分支付抵用卷</p>
	<div class="popver-con clearfix">
		<ul>
			<li>
				<span class="fc-black">省100微积分：满1000减100</span>
				<div class="icheckbox-list">
					<input type="checkbox" checked="" name="options2" id="option12">
				</div>
			</li>
			<li>
				<span class="fc-black">省100微积分：满1000减100</span>
				<div class="icheckbox-list">
					<input type="checkbox" checked="" name="options2" id="option13">
				</div>
			</li>
		</ul>
	</div>
	<a href="javascript:;" class="btn-close">关闭</a>
</div>
<!--微积分不足-->
<div class="fail-popver" id="fail">
	<div class="fail-tit">
		<img src="../../images/productBuy/fail.png" style="width: 50px;">
		<p class="mt10">微积分不够啦</p>
	</div>
	<div class="taligncenter fail-txt">
		<p>当前可用微积分为<span class="fc-p-red" id="nowwjf"></span></p>
		<p>还需要<span class="fc-p-red" id="needwjf"></span>才能购买</p>
	</div>
	<div class="btn-footer">
		<a href="javascript:;" class="pay-close">取消支付</a>
		<a href="" class="btn-recharge">去充值</a>
	</div>
</div>
<!--确认支付-->
<div class="fail-popver" id="success">
	<div class="fail-tit">
		<p class="mt10" style="font-size: 18px;margin-top: 25px;">确认支付</p>
	</div>
	<br>
	<div class="taligncenter fail-txt">

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

<script src="${staticResourcePathH5}/js/productBuy/jquery.min.js"></script>
<script src="${staticResourcePathH5}/js/productBuy/wyrem.js"></script>
<script src="${staticResourcePathH5}/js/productBuy/icheck.js"></script>
<script src="${staticResourcePathH5}/js/productBuy/iscroll.js"></script>
<script src="${staticResourcePathH5}/js/navigate.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/common-order.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript">
	var product_select="${product_select!0}";
	if(product_select==1){
		$(".pay-content>.item").eq(1).addClass('dis-b').siblings().removeClass('dis-b');
	}
	//获取待支付金额-----
	var getOrderFeeFlag = 0;
	function commonBuyOrderFee(){
		getOrderFeeFlag++;
		setTimeout(function(){
			getOrderFeeFlag--;
			if(getOrderFeeFlag == 0){
				$.post(${njxBasePath}+"/pay/getOrderFeeV2",$("#commonBuyCreditForm").serialize(),function(data){
					if(data.obj.totalFee!=null){
						$("#creditTotalFee").text('￥'+data.obj.totalFee.toFixed(2));
						$("#goPay1").attr("onclick","buyProductwjf()");
					}else{
						$("#creditTotalFee").text('￥0.00');
						$("#goPay1").removeAttr("onclick");
					}
				});
			}
		}, 500);
	}

	//获取微积分购买应付总额
	function getBuyCreditFee(){
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
		if(totalFee == 0){
			$("#goPay1").removeAttr("onclick");
		}else{
			$("#goPay1").attr("onclick","goCreditCart();");
		}
		$("#creditTotalFee").text('￥'+totalFee.toFixed(2));
	}

	//获取套餐包购买应付总额
	function getBuyGroupProductFee(index, value){
		$("#groupPackBuyShow li:not()").remove();
		var totalFee = 0;
		var keywordCount64 = $('input[name="myProducts[\'product64\'].keywordPackageNum"]').val();
		var keywordCount65 = $('input[name="myProducts[\'product65\'].keywordPackageNum"]').val();
		var analysisCount = $('input[name="myProducts[\'product18\'].keywordPackageNum"]').val();
		var weiboAnalysisCount = $('input[name="myProducts[\'product32\'].keywordPackageNum"]').val();
		var briefCount = $('input[name="myProducts[\'product19\'].keywordPackageNum"]').val();
		var productAnalysisCount = $('input[name="myProducts[\'product21\'].keywordPackageNum"]').val();
		var singleWeiboAnalysisCount = $('input[name="myProducts[\'product22\'].keywordPackageNum"]').val();
		if(!IsNum(keywordCount64) || keywordCount64 < 0){
			keywordCount64 = 0;
		}else if(keywordCount64 > 99){
			keywordCount64 = 99;
		}
		if(!IsNum(keywordCount65) || keywordCount65 < 0){
			keywordCount65 = 0;
		}else if(keywordCount65 > 99){
			keywordCount65 = 99;
		}
		$('input[name="myProducts[\'product64\'].keywordPackageNum"]').val(keywordCount64);
		$('input[name="myProducts[\'product65\'].keywordPackageNum"]').val(keywordCount65);
		if(!IsNum(analysisCount) || analysisCount < 0){
			analysisCount = 0;
		}else if(analysisCount > 99){
			analysisCount = 99;
		}
		$('input[name="myProducts[\'product18\'].keywordPackageNum"]').val(analysisCount);
		if(!IsNum(weiboAnalysisCount) || weiboAnalysisCount < 0){
			weiboAnalysisCount = 0;
		}else if(weiboAnalysisCount > 99){
			weiboAnalysisCount = 99;
		}
		$('input[name="myProducts[\'product32\'].keywordPackageNum"]').val(weiboAnalysisCount);
		if(!IsNum(briefCount) || briefCount < 0){
			briefCount = 0;
		}else if(briefCount > 99){
			briefCount = 99;
		}
		$('input[name="myProducts[\'product19\'].keywordPackageNum"]').val(briefCount);
		if(!IsNum(productAnalysisCount) || productAnalysisCount < 0){
			productAnalysisCount = 0;
		}else if(productAnalysisCount > 99){
			productAnalysisCount = 99;
		}
		$('input[name="myProducts[\'product21\'].keywordPackageNum"]').val(productAnalysisCount);
		if(!IsNum(singleWeiboAnalysisCount) || singleWeiboAnalysisCount < 0){
			singleWeiboAnalysisCount = 0;
		}else if(singleWeiboAnalysisCount > 999){
			singleWeiboAnalysisCount = 999;
		}
		$('input[name="myProducts[\'product22\'].keywordPackageNum"]').val(singleWeiboAnalysisCount);

		var keywordPrice64 = $('input[name="myProducts[\'product64\'].keywordPackagePrice"]').val();
		var keywordPrice65 = $('input[name="myProducts[\'product65\'].keywordPackagePrice"]').val();
		var analysisPrice = $('input[name="myProducts[\'product18\'].keywordPackagePrice"]').val();
		var weiboAnalysisPrice = $('input[name="myProducts[\'product32\'].keywordPackagePrice"]').val();
		var briefPrice = $('input[name="myProducts[\'product19\'].keywordPackagePrice"]').val();
		var productAnalysisPrice = $('input[name="myProducts[\'product21\'].keywordPackagePrice"]').val();
		var singleWeiboAnalysisPrice = $('input[name="myProducts[\'product22\'].keywordPackagePrice"]').val();
		if( keywordCount64>0 ||keywordCount65>0 || analysisCount > 0 || weiboAnalysisCount > 0 || briefCount > 0 || productAnalysisCount > 0 || singleWeiboAnalysisCount > 0){
			$("#nochoose2").hide();
		}else{
			$("#nochoose2").show();
		}
		if(keywordCount64 > 0)
			$("#groupPackBuyShow").append('<li><span class="fc-gray9 g_6">监测方案(月)</span><span class="fc-black g_1">&times;'+keywordCount64+'</span><span class="fc-black">&yen;'+(keywordPrice64*keywordCount64).toFixed(2)+'</span></li>');
		if(keywordCount65 > 0)
			$("#groupPackBuyShow").append('<li><span class="fc-gray9 g_6">监测方案(年)</span><span class="fc-black g_1">&times;'+keywordCount65+'</span><span class="fc-black">&yen;'+(keywordPrice65*keywordCount65).toFixed(2)+'</span></li>');
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

</script>
<script type="text/javascript">
	var myScroll;

	function loaded() {
		myScroll = new IScroll('#wrapper', {
			scrollX: true,
			scrollY: false,
			hScrollbar:false,
			vScrollbar : false,
			vScroll:false
		});
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.paymode-ul input:first').click();

		$('.icheckbox-list input').on('ifChecked', function(event) {
			var value = $(this).val();
			console.log(value);
			if(value=="03"){
				$("#payType").val("03");
				$("#useCredit1").val("false");
				$("#goProductCartBtn").attr("onclick","buyProductpro()");
			}
			if(value=="02"){
				$("#payType").val("02");
				$("#useCredit1").val("false");
				$("#goProductCartBtn").attr("onclick","buyProductpro()");
			}
			if(value=="00"){
				$("#payType").val("00");
				$("#useCredit1").val("true");
				$("#goProductCartBtn").attr("onclick","showTips()");
			}
			commonBuyProductFee();
		}).iCheck({
			checkboxClass: 'icheckbox_square-red',
			radioClass: 'iradio_square-red',
			increaseArea: '20%'
		});
	});
	/*打开弹框*/
	$('.btn-quan').on('click', function() {
		$('.popver1').show();
		$('.popver-mask').show();
		$('html').addClass('noscroll');
	})
	$('.btn-quan2').on('click', function() {
		$('.popver2').show();
		$('.popver-mask').show();
		$('html').addClass('noscroll');
	});
	/* $('#goProductCartBtn').on('click', function() {
        $('.fail-popver').show();
        $('.popver-mask').show();
        $('html').addClass('noscroll');
    }); */
	/*关闭*/
	$('.btn-close').on('click', function() {
		$(this).parent().hide();
		$('.popver-mask').hide();
		$('html').removeClass('noscroll');
	})
	$('.pay-close').on('click', function() {
		$('.fail-popver').hide();
		$('.popver-mask').hide();
		$('html').removeClass('noscroll');
	});
	$('.popver-mask').on('click', function() {
		$('.popver').hide();
		$('.about-popver').hide();
		$('.fail-popver').hide();
		$(this).hide();
		$('html').removeClass('noscroll');
	});
	$('.btn-guizhe').on('click', function() {
		$('.about-popver').show();
		$('.popver-mask').show();
		$('html').addClass('noscroll');
		loaded();
	})

	$(".layout-nav  li").on('click', function() {
		$(this).addClass('active').siblings().removeClass('active');
		var index = $(this).index();
		$(".pay-content>.item").eq(index).addClass('dis-b').siblings().removeClass('dis-b');
	});
	$(".myjf-type  li").on('click', function() {

		$(this).toggleClass('jf-active');
		$('.pay-choose li').eq($(this).index()).toggleClass('display-none');
		if ($(this).index()==0) {
			if($(this).prop("class")=="jf-active"){
				$("#creditPackageCount_56").val("1");
			}else{
				$("#creditPackageCount_56").val("0");

			}
		}else if($(this).index()==1){
			if($(this).prop("class")=="jf-active"){
				$("#creditPackageCount_57").val("1");
			}else{
				$("#creditPackageCount_57").val("0");
			}

		}else if($(this).index()==2){
			if($(this).prop("class")=="jf-active"){
				$("#creditPackageCount_58").val("1");
			}else{
				$("#creditPackageCount_58").val("0");
			}

		}else if($(this).index()==3){
			if($(this).prop("class")=="jf-active"){
				$("#creditPackageCount_59").val("1");
			}else{
				$("#creditPackageCount_59").val("0");
			}
		}
		if( ( $("#creditPackageCount_56").val()!=0 ) || ( $("#creditPackageCount_57").val()!=0 ) || ( $("#creditPackageCount_58").val()!=0 ) || ( $("#creditPackageCount_59").val()!=0 )){
			$("#nochoose").hide();
		}else{
			$("#nochoose").show();
		}
		$.post('/pay/getOrderFeeV2',$("#commonBuyCreditForm").serialize(),function(data){
			if(data.obj.totalFee!=null){
				$("#creditTotalFee").text('￥'+data.obj.totalFee.toFixed(2));
				$("#goPay1").attr("onclick","buyProductwjf()");
			}else{
				$("#creditTotalFee").text('￥0.00');
				$("#goPay1").removeAttr("onclick");
			}
		});
	});
	$('.dis-section .icon-lajitong2').click(() => {
		$(".myjf-type  li").removeClass('jf-active');
	$('.pay-choose li').addClass('display-none');
	$("#creditPackageCount_56").val("0");
	$("#creditPackageCount_57").val("0");
	$("#creditPackageCount_58").val("0");
	$("#creditPackageCount_59").val("0");
	$("#product1001Num").val('0');
	$("#product18Num").val('0');
	$("#product32Num").val('0');
	$("#product19Num").val('0');
	$("#product21Num").val('0');
	$("#product22Num").val('0');
	$("#creditTotalFee").text('￥0.00');
	$("#nochoose").show();
	$("#nochoose2").show();
	commonBuyProductFee();
	});
</script>
</body>

</html>