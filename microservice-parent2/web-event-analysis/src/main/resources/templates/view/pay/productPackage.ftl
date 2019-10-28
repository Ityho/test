<input type="hidden" id = "creditAmount" value="${(admin.creditAmount)!0 }">
<input type="hidden" id = "userPlatform" value="${(userPlatform)!0 }">
<div id="buyPrompt_jp3" class="dialog">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
		<#list analysisProductList! as item>
            <form action="${njxBasePath}/pay/confirmOrderV3" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${item.productPackageId!}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="<#if admin.creditAmount>0>true<#else>false</#if>"/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>购买全网事件分析</h1>
                <div style="margin:20px auto;width:300px ">
                    <div class="setcount" style="width:40%;display:inline-block">
                        <a class="float_l" href="javascript:void(0);" onclick="delKeyword()">－</a>
                        <div id="numdiv" class="number" style="float:left">1</div>
                        <a class="float_l" href="javascript:void(0);" onclick="addKeyword()">+</a>
                    </div>
                    <div class="paylist" style="display:none;">
                        <ul>
                            <li <#if status.index==0>class="cur"</#if>>
                                <div class="paydesc">
                                    <ul>
                                        <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥${item.packagePrice!}
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="paymethod" style="width:40%;display:inline-block"><span>支付方式：</span>
						<#if userPlatform == 2>
                            <input type="hidden" value="03" id="payType" name="payType" />
                            <em class="pay-icon pay-icon4"></em>
						<#elseIf userPlatform == 3>
                            <input type="hidden" value="02" id="payType" name="payType" />
                            <em class="pay-icon pay-icon2"></em>
						<#else>
                            <input type="hidden" value="01" id="payType" name="payType" />
                            <em class="pay-icon pay-icon1"></em>
						</#if>
                    </div>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;"><i class="icon-check-circle <#if admin.creditAmount>0>check-circle-click</#if>" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div><br>
                <div class="yf">应付<span>${item.packagePrice!}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
		</#list>
        </div>
        <div class="desc">（全网事件分析）即输入近期事件或话题关键词，微热点针对全网信息进行深度挖掘和多重分
            析；记录事件从始发到发酵期、发展期、高涨期、回落期和反馈期等阶段的演变过程，分析信息传播路径
            传播节点、发展态势和受众反馈等。
        </div>
    </div>
</div>
<div id="buyPrompt_jp4" class="dialog">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
		<#list weiboAnalysisProductList! as item>
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${item.productPackageId!}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="<#if ((admin.creditAmount)!>0)>true<#else>false</#if>"/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>购买微博事件分析</h1>

                <div style="margin:20px auto;width:300px ">
                    <div class="setcount" style="width:40%;display:inline-block">
                        <a class="float_l" href="javascript:void(0);" onclick="delKeyword()">－</a>
                        <div id="numdiv" class="number" style="float:left">1</div>
                        <a class="float_l" href="javascript:void(0);" onclick="addKeyword()">+</a>
                    </div>
                    <div class="paylist" style="display:none;">
                        <ul>
                            <li <#if (status.index)!0==0>class="cur"</#if>>
                                <div class="paydesc">
                                    <ul>
                                        <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥${item.packagePrice!}
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="paymethod" style="width:40%;display:inline-block"><span>支付方式：</span>
						<#if  userPlatform == 2>
                            <input type="hidden" value="03" id="payType" name="payType" />
                            <em class="pay-icon pay-icon4"></em>
						<#elseIf userPlatform == 3>
                            <input type="hidden" value="02" id="payType" name="payType" />
                            <em class="pay-icon pay-icon2"></em>
						<#else>
                            <input type="hidden" value="01" id="payType" name="payType" />
                            <em class="pay-icon pay-icon1"></em>
						</#if>
                    </div>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;"><i class="icon-check-circle <#if ((admin.creditAmount)!>0)>check-circle-click</#if>" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div><br>
                <div class="yf">应付<span>${item.packagePrice!}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
		</#list>
        </div>
        <div class="desc">（微博事件分析）输入近期事件或话题关键词，微热点针对微博信息进行深度挖掘和多重分析；记录事件从始发到发酵期、发展期、高涨期、回落期和反馈期等阶段的演变过程，分析信息传播路径、传播节点、发展态势和受众反馈等。
        </div>
    </div>
</div>
<div id="buyPrompt_jp6" class="dialog">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
		<#list productAnalysisProductList! as item>
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${item.productPackageId!}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="<#if admin.creditAmount>0>true<#else>false</#if>"/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>购买竞品分析</h1>
                <div style="margin:20px auto;width:300px ">
                    <div class="setcount" style="width:40%;display:inline-block">
                        <a class="float_l" href="javascript:void(0);" onclick="delKeyword()">－</a>
                        <div id="numdiv" class="number" style="float:left">1</div>
                        <a class="float_l" href="javascript:void(0);" onclick="addKeyword()">+</a>
                    </div>
                    <div class="paylist" style="display:none;">
                        <ul>
                            <li <#if status.index==0>class="cur"</#if>>
                                <div class="paydesc">
                                    <ul>
                                        <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥${item.packagePrice!}
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="paymethod" style="width:40%;display:inline-block"><span>支付方式：</span>
						<#if userPlatform == 2>
                            <input type="hidden" value="03" id="payType" name="payType" />
                            <em class="pay-icon pay-icon4"></em>
						<#elseIf userPlatform == 3>
                            <input type="hidden" value="02" id="payType" name="payType" />
                            <em class="pay-icon pay-icon2"></em>
						<#else>
                            <input type="hidden" value="01" id="payType" name="payType" />
                            <em class="pay-icon pay-icon1"></em>
						</#if>
                    </div>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;"><i class="icon-check-circle <#if admin.creditAmount>0>check-circle-click</#if>" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div><br>
                <div class="yf">应付<span>${item.packagePrice!}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
		</#list>
        </div>
        <div class="desc">（竞品分析）输入您关注的品牌或企业与其竞争对手名称。微热点从全网数据中，对双方进行市场走势、媒体投放情况、负面信息、用户分析等方面进行对比分析。
        </div>
    </div>
</div>
<div id="buyPrompt_jp7" class="dialog">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
		<#list singleWeiboAnalysisProductList! as item>
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${item.productPackageId!}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="<#if admin.creditAmount?exists>0>true<#else>false</#if>"/>
                <input id = "fenxiWeiboId" type="hidden" name="fenxiWeiboId" value=""/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>购买微博传播分析</h1>
                <label>(每条转发0.01元，以后每次${item.packagePrice!}元)</label>
                <div>转发数：<span id = "forwardedNum"></span></div>
                <div class="paylist" style="display:none;">
                    <ul>
                        <li <#if status.index==0>class="cur"</#if>>
                            <div class="paydesc">
                                <ul>
                                    <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥${item.packagePrice!}
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="paymethod" style="margin:20px auto;"><span>支付方式：</span>
					<#if userPlatform == 2>
                        <input type="hidden" value="03" id="payType" name="payType" />
                        <em class="pay-icon pay-icon4"></em>
					<#elseIf userPlatform == 3>
                        <input type="hidden" value="02" id="payType" name="payType" />
                        <em class="pay-icon pay-icon2"></em>
					<#else>
                        <input type="hidden" value="01" id="payType" name="payType" />
                        <em class="pay-icon pay-icon1"></em>
					</#if>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;"><i class="icon-check-circle <#if admin.creditAmount>0>check-circle-click</#if>" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div><br>
                <div class="yf">应付<span><${item.packagePrice!}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
		</#list>
        </div>
        <div class="desc">（微博传播分析）即输入近期事件或话题关键词，微热点针对全网信息进行深度挖掘和多重分
            析；记录事件从始发到发酵期、发展期、高涨期、回落期和反馈期等阶段的演变过程，分析信息传播路径
            传播节点、发展态势和受众反馈等。
        </div>
    </div>
</div>
<div id="buyPrompt_jp12" class="dialog">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="<#if useCreditProductList?exists>${useCreditProductList[0].productPackageId!}</#if>" />
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="false"/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>充值微积分</h1>
                <!-- <label>(1元＝100微积分)</label> -->
                <div class="paylist paylist1">
				<#list useCreditProductList! as item>
                    <ul>
                        <li <#if item_index==0>class="cur"</#if>>
                            <div class="paydesc">
                                <ul>
                                    <li data-price = "${item.packagePrice!}" data-productPackageId = "${item.productPackageId!}" style="line-height:25px;">￥${item.packagePrice!}</li>
                                    <li style="line-height:20px;">微积分<br>${item.creditCount!+item.giftCreditCount!}</li>
                                </ul>
                            </div>
                        </li>
                    </ul>
				</#list>
                </div>
                <div class="paymethod" style="margin:10px auto;"><span>支付方式：</span>
				<#if userPlatform == 2>
                    <input type="hidden" value="03" id="payType" name="payType" />
                    <em class="pay-icon pay-icon4"></em>
				<#elseIf userPlatform == 3>
                    <input type="hidden" value="02" id="payType" name="payType" />
                    <em class="pay-icon pay-icon2"></em>
				<#else>
                    <input type="hidden" value="01" id="payType" name="payType" />
                    <em class="pay-icon pay-icon1"></em>
				</#if>
                </div>
                <div class="yf">应付<span><#if useCreditProductList?exists>${useCreditProductList[0].packagePrice!}</#if></span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
        </div>
    </div>
</div>
<div id="buyPrompt_jp13" class="dialog">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
		<#list hotDailyMoreProductList! as item>
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${item.productPackageId!}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="<#if admin.creditAmount>0>true<#else>false</#if>"/>
                <input id = "keywords" type="hidden" name="keywords" value=""/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>购买热度对比</h1>
                <div class="paylist" style="display:none;">
                    <ul>
                        <li <#if item_index==0>class="cur"</#if>>
                            <div class="paydesc">
                                <ul>
                                    <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥${item.packagePrice!}
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="paymethod" style="margin:20px auto;"><span>支付方式：</span>
					<#if userPlatform == 2>
                        <input type="hidden" value="03" id="payType" name="payType" />
                        <em class="pay-icon pay-icon4"></em></s:if>
					<#elseIf userPlatform == 3>
                        <input type="hidden" value="02" id="payType" name="payType" />
                        <em class="pay-icon pay-icon2"></em>
					<#else>
                        <input type="hidden" value="01" id="payType" name="payType" />
                        <em class="pay-icon pay-icon1"></em>
					</#if>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;"><i class="icon-check-circle <#if admin.creditAmount>0>check-circle-click</#if>" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div><br>
                <div class="yf">应付<span>${item.packagePrice!}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
		</#list>
        </div>
        <div class="desc">（热度对比）通过对热度指数的计算，和对多个维度分析结果的对比，客观呈现您所关注的行业、品牌或人物的网络受关注度情况
        </div>
    </div>
</div>
<div id="buyPrompt_jp14" class="dialog">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="<#if hotDailyTimeProductList?exists>${(hotDailyTimeProductList??[0]??.productPackageId??)??}!0<#else>1111</#if>" />
				<#--<input id = "productPackageId"  type="hidden" name="productPackageId" value="<#if hotDailyTimeProductList?exists>222<#else>1111</#if>" />-->
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="<#if admin.creditAmount?exists>0>true<#else>false</#if>"/>
                <input id = "heatReportId" type="hidden" name="heatReportId" value=""/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>订阅热度日报</h1>
                <div class="paylist paylist1">
				<#list hotDailyTimeProductList! as item>
                    <ul>
                        <li <#if item_index==0>class="cur"</#if>>
							<#if item_index==0><i class="ty"></i></#if>
							<#if item_index==2><i class="cz"></i></#if>
                            <div class="paydesc">
                                <ul>
                                    <li>${item.packageName!}</li>
                                    <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥${item.packagePrice!}
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
				</#list>
                </div>
                <div class="paymethod" style="margin:20px auto;"><span>支付方式：</span>
				<#if  userPlatform  == 2 >
                    <input type="hidden" value="03" id="payType" name="payType" />
                    <em class="pay-icon pay-icon4"></em>
				<#elseIf userPlatform  == 3>
                    <input type="hidden" value="02" id="payType" name="payType" />
                    <em class="pay-icon pay-icon2"></em>
				<#else>
                    <input type="hidden" value="01" id="payType" name="payType" />
                    <em class="pay-icon pay-icon1"></em>
				</#if>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;"><i class="icon-check-circle <#if admin.creditAmount?? && (admin.creditAmount>0)>check-circle-click</#if>" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div><br>
                <div class="yf">应付<span>${(productPackageList[0]??.packagePrice??)!0}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
        </div>
        <div class="desc">（热度日报）一键订阅后，系统将于每天早晨及时向您报告所关注内容的网络热度指数变化情况，及最新相关信息详情。
        </div>
    </div>
</div>
<div id="buyPrompt_jp15" class="dialog">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${(keywordProductList[0]??.productPackageId)!0}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="<#if admin.creditAmount?exists>0>true<#else>false</#if>"/>
                <input id = "orderType" type="hidden" name="orderType" value="2"/>
                <input id = "keywordIds" type="hidden" name="keywordIds"/>
                <h1>购买监测方案</h1>
                <div style="text-align:left;">请选择方案时长：</div>
                <div class="paylist paylist1">
				<#list keywordProductList! as item>
					<#if item.productPackageId != 33 && item.productPackageId != 35 && item.productPackageId != 36>
                        <ul>
                            <li <#if item_index==0>class="cur"</#if>>
								<#if item.productPackageId==3><i class="cz"></i></#if>
                                <div class="paydesc">
                                    <ul>
                                        <li>${item.packageName}</li>
                                        <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥${item.packagePrice!}/>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
					</#if>
				</#list>
                </div>
                <div style="margin:0px auto;width:300px ">
                    <div class="paymethod" style="width:40%;display:inline-block"><span>支付方式：</span>
					<#if userPlatform == 2 >
                        <input type="hidden" value="03" id="payType" name="payType" />
                        <em class="pay-icon pay-icon4"></em>
					<#elseIf userPlatform == 3 >
                        <input type="hidden" value="02" id="payType" name="payType" />
                        <em class="pay-icon pay-icon2"></em>
					<#else>
                        <input type="hidden" value="01" id="payType" name="payType" />
                        <em class="pay-icon pay-icon1"></em>
					</#if>
                    </div>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;"><i class="icon-check-circle <#if admin.creditAmount?exists>0>check-circle-click</#if>" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div>
                <div class="yf">应付<span>${(productPackageList[0]??.packagePrice??)!0}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
        </div>
        <div class="desc">（监测方案）通过设置关键词获取您关注的全网信息，关键字总数不超过500个字。
        </div>
    </div>
</div>
<div id="buyPrompt_jp17" class="dialog" style="top:30%;">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${(stretchProductList[0]??.productPackageId??)!0}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="${packageNum!}" />
                <input id = "useCredit" type="hidden" name="useCredit" value="false"/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>王俊凯大数据分析报告</h1>
                <div class="paylist paylist1" style="display:none;">
				<#list stretchProductList! as item>
                    <ul>
                        <li <#if item_index==0>class="cur"</#if>>
                            <div class="paydesc">
                                <ul>
                                    <li>${item.packageName!}</li>
                                    <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥$item.packagePrice!}
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
				</#list>
                </div>
                <div style="margin:0px auto;width:300px ">
                    <div class="paymethod" style="width:40%;display:inline-block"><span>支付方式：</span>
					<#if userPlatform == 2>
                        <input type="hidden" value="03" id="payType" name="payType" />
                        <em class="pay-icon pay-icon4"></em></s:if>
					<#elseIf userPlatform == 3>
                        <input type="hidden" value="02" id="payType" name="payType" />
                        <em class="pay-icon pay-icon2"></em>
					<#else>
                        <input type="hidden" value="01" id="payType" name="payType" />
                        <em class="pay-icon pay-icon1"></em>
					</#if>
                    </div>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;display:none;"><i class="icon-check-circle" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div>
                <div class="yf">应付<span>￥{productPackageList.get(0).packagePrice!}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
        </div>
    </div>
</div>
<div id="buyPrompt_jp18" class="dialog" style="top:30%;">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${(stretchProductList[0]??.productPackageId)!0}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="${packageNum!}" />
                <input id = "useCredit" type="hidden" name="useCredit" value="false"/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>鹿晗大数据分析报告</h1>
                <div class="paylist paylist1" style="display:none;">
				<#list stretchProductList! as item>
                    <ul>
                        <li <#if item_index==0>class="cur"</#if>>
                            <div class="paydesc">
                                <ul>
                                    <li>${item.packageName!}</li>
                                    <li data-price = "${item.packagePrice!}"  data-productPackageId = "${item.productPackageId!}">￥${item.packagePrice!}
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>>
				</#list>
                </div>
                <div style="margin:0px auto;width:300px ">
                    <div class="paymethod" style="width:40%;display:inline-block"><span>支付方式：</span>
					<#if userPlatform == 2>
                        <input type="hidden" value="03" id="payType" name="payType" />
                        <em class="pay-icon pay-icon4"></em>
					<#elseIf userPlatform == 3>
                        <input type="hidden" value="02" id="payType" name="payType" />
                        <em class="pay-icon pay-icon2"></em>
					<#else>
                        <input type="hidden" value="01" id="payType" name="payType" />
                        <em class="pay-icon pay-icon1"></em>
					</#if>
                    </div>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;display:none;"><i class="icon-check-circle" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div>
                <div class="yf">应付<span>${(productPackageList[0]??.packagePrice)!0}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
        </div>
    </div>
</div>
<div id="buyPrompt_jp100${(h5Activity.id!)!0}" class="dialog" style="top:30%;">
    <div class="formPackage">
        <i class="icon-remove"></i>
        <div class="cont">
            <form action="${njxBasePath}/view/pay/confirmOrder.action" name="payForm" method="post" id = "payForm">
                <input id = "productPackageId"  type="hidden" name="productPackageId" value="${(productPackage.productPackageId!)!0}" />
                <input id = "packageCount" type="hidden" name="packageCount" value="1" />
                <input id = "useCredit" type="hidden" name="useCredit" value="false"/>
                <input id = "orderType" type="hidden" name="orderType" value="1"/>
                <h1>${(h5Activity.title!"")!""}</h1>
                <div class="paylist paylist1" style="display:none;">
                    <ul>
                        <li class="cur">
                            <div class="paydesc">
                                <ul>
                                    <li>${(productPackage.packageName!"")!""}</li>
                                    <li data-price = "${(productPackage.packagePrice!0)!0}"  data-productPackageId = "${(productPackage.productPackageId!"")!""}">￥${(productPackage.packagePrice!0)!0}
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
                <div style="margin:0px auto;width:300px ">
                    <div class="paymethod" style="width:40%;display:inline-block"><span>支付方式：</span>
					<#if userPlatform  == 2>
                        <input type="hidden" value="03" id="payType" name="payType" />
                        <em class="pay-icon pay-icon4"></em>
					<#elseIf userPlatform == 3>
                        <input type="hidden" value="02" id="payType" name="payType" />
                        <em class="pay-icon pay-icon2"></em>
					<#else>
                        <input type="hidden" value="01" id="payType" name="payType" />
                        <em class="pay-icon pay-icon1"></em>
					</#if>
                    </div>
                </div>
                <div class="warnpage" style="line-height:30px;font-size:1.2em;display:none;"><i class="icon-check-circle" style="vertical-align: sub;background:none;font-size:22px;"></i>可用<span class = "dcreditAmount"></span>微积分抵扣￥<span class = "dprice"></span></div>
                <div class="yf">应付<span>${(productPackageList[0]??.packagePrice!0)!0}</span>元</div>
                <a class="btn-pay" onclick="buyProduct()">购买</a>
            </form>
        </div>
        <!-- <div class="desc">（监测方案）通过设置关键词获取您关注的全网信息，关键字总数不超过500个字。
        </div> -->
    </div>
</div>



<script>
    $(function() {
        // 选择数量
        $('ul[name="commonBuySetCountUL"] > li').click(function() {
            setPackageCount($(this).attr('data-cmd'));
            $('ul[name="commonBuySetCountUL"] > li').removeClass('active');
            $(this).addClass('active');
        });

        // 套餐弹窗关闭
        $('#myModalPro').on('hide.bs.modal', function () {
            $('#commonBuyProContinue').val(false);
        });

        //初始化数量
        $('#commonBuyCount').val(1);
    });

    // 增加产品包数量
    function addSinglePackageCount() {
        var c = parseInt($('#commonBuyCount').val());
        setPackageCount(c+1);
    }

    // 减少产品包数量
    function minSinglePackageCount() {
        var c = parseInt($('#commonBuyCount').val());
        if (c > 1) {
            setPackageCount(c-1);
        }
    }

    // 设置产品包数量
    function setPackageCount(c, flag) {
        if (c) {
            $('#commonBuyCount').val(c);
            $('.package-count').val($('#commonBuyCount').val());
            if(!flag)
                commonBuyOrderFee();
        }
    }
</script>
