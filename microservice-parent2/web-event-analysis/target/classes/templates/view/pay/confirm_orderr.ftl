<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/top.jsp" %>
<link href="<%=staticResourcePathH5 %>/css/user.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet" type="text/css">
<link href="<%=staticResourcePathH5 %>/css/common.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet" type="text/css">
<script src="<%=staticResourcePathH5 %>/js/jquery.JPlaceholder.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<!--[if lt IE 10]>
<script type="text/javascript" src="<%=staticResourcePathH5 %>/js/PIE.js"></script>
<![endif]-->
<script type="text/javascript">saveOperateLog('订单确认','1031');</script>
<script>
    $(function() {
        if (window.PIE) {
            $('.rounded').each(function() {
                PIE.attach(this);
            });
        }
    });
</script>
<script type="text/javascript">
    function openDiv(){
        $("#modifyHead").show();
        $("#blackDiv").show();
    }
    function delDiv(){
        $("#modifyHead").hide();
        $("#blackDiv").hide();
    }

    var flag=0;
    function goPay(id, orderNo){
        if (flag == 0) {
            flag = 1;
            var url = '';
            var winObj
            var type = $('#payChannel').val();
            if (type == 1) // 支付宝支付
                url = "${ctx }/pay/goPay?payRecord.payRecordId=" + id;
            else if (type == 2) // 微信支付
                url = "${ctx }/pay/goWeixinPay?payRecord.payRecordId=" + id + "&order.orderNo=" + orderNo;
            else if (type == 3) // 线下支付
                url = "${ctx }/pay/goOffLinePay";
            else if (type == 4) // 微博支付
                url = "${ctx }/pay/goSinaPay?payRecord.payRecordId=" + id;
            else
                return false;

            if (type == 3) {
                sendPostForm('${ctx }/pay/goOffLinePay', '_self', {'payRecord.payRecordId' : id, 'order.orderNo' : orderNo});
            } else {
                winObj = window.open (url) ;
            }
            setInterval("myInterval(" + id + ")", 1000);//1000为1秒钟
            var t = setInterval(function() {
                if(winObj.closed) {
                    flag = 0;
                    clearInterval(t)
                }
            }, 500);
        }else{
            showMsgInfo(0,"订单支付中，请不要重复支付!",0);
        }
    }

    function myInterval(id) {
        $.ajax({
            type:"post",
            url:"${ctx }/view/user/checkOrderStatus?payRecord.payRecordId="+id,
            cache:false,
            success: function(data){
                if (!$.isEmptyObject(data)) {
                    if (data.payRecord.payStatus == 1) {
                        if (data.redirectPage == 'analysis')
                            window.location.href="${ctx }/view/eventAnalysis/taskList.action";
                        else if (data.redirectPage == 'brief')
                            window.location.href="${ctx }/brief/brief.shtml";
                        else if (data.redirectPage == 'report')
                            window.location.href="${ctx }/daily.shtml";
                        else if (data.redirectPage == 'productAnalysis')
                            window.location.href="${ctx }/productsAnalysis.shtml";
                        else if (data.redirectPage == 'swa')
                            window.location.href="${ctx }/weiboAnalysis/weiboAnalysisIndex";
                        else if (data.redirectPage == 'weiboAnalysis')
                            window.location.href="${ctx }/view/weiboEventAnalysis/taskList.action";
                        else
                            window.location.href="${ctx }/pay/success.shtml";
                    }
                }
            }
        });
    }

    function changePayChannel(channel) {
        if (channel != null && channel > 0)
            $('#payChannel').val(channel);
    }

    $(function() {
        if ($('#loginPlatform').val() == 3) {
            $('#payChannel').val(4);
            $('ul.pay > li').each(function(i, n) {
                if ($(n).attr('id') != 'weiboPay')
                    $(n).css('display', 'none');
                if ($(n).attr('id') == 'weiboPay')
                    $(n).addClass('li_click');
            });
        }
    });
</script>


<body>
<s:include value="/view/tips.jsp" />
<div id="top"></div>
<!--head代码 start-->
<s:include value="/view/navigate.jsp" />
<!--head代码 end-->

<!--导航 start-->
<%--<div class="nav2 clear">
    <div class="content">
        <a href="${ctx }/home.shtml" class="nav2icon home_icon float_l" title="返回首页">首页</a><h1><a href="${ctx }/usercenter/account.shtml">用户中心</a> </h1> <h2>产品选购</h2>
    </div>
</div>--%>
<!--导航 end-->

<input type="hidden" id="loginPlatform" value='<s:property value="loginPlatform" />' />

<div class="page-container2">
    <!--主要内容 start-->
    <div class="contentUser mt15">
        <!--左侧导航 start-->
        <script type="text/javascript" src="<%=staticResourcePathH5 %>/js/jquery.tree.js?v=<%=SYSTEM_INIT_TIME %>"></script>
        <script type="text/javascript">
            $(function(){
                $('#files').tree({
                    expanded: 'li'
                });
            });
        </script>
        <div class="user_l float_l">
            <h1>用户中心</h1>
            <ul id="files">
                <li><a href="javascript:void(0);" class="leve1">会员信息</a>
                    <ul>
                        <li><a href="${ctx }/usercenter/account.shtml" title="个人中心">个人中心</a></li>
                        <li><a href="${ctx}/usercenter/vipInfo.shtml" title="用户信息">用户信息</a></li>
                    </ul>
                </li>

                <li><a href="javascript:void(0);" class="leve1">订单信息</a>
                    <ul>
                        <li><a href="${ctx }/order/list.shtml" title="订单管理">订单管理</a></li>
                        <li><a href="${ctx }/continue/keyword.shtml" title="续费管理">续费管理</a></li>
                    </ul>
                </li>

                <li><a href="javascript:void(0);" class="leve1">发票管理</a>
                    <ul>
                        <li><a href="${ctx }/bill/order.shtml" title="索取发票">索取发票</a></li>
                        <li><a href="${ctx }/bill/list.shtml" title="发票列表">发票列表</a></li>
                        <li><a href="${ctx }/bill/address/list.shtml" title="邮寄地址">邮寄地址</a></li>
                    </ul>
                </li>

                <li><a href="javascript:void(0);" class="leve1">我的订购</a>
                    <ul>
                        <li><a href="${ctx }/pay/buy.shtml" class="cur"  title="产品选购">产品选购</a></li>
                        <%--             <li><a href="${ctx }/pay/cart.shtml" title="购物车">购物车</a></li> --%>
                    </ul>
                </li>

            </ul>
        </div>
        <!--左侧导航 end-->

        <!--右侧内容 start-->
        <!--发票管理 start-->
        <div class="user_r mb15 float_r user_height">
            <div class="user_tit"><h2>订单支付</h2></div>
            <div class="user_con user_con3">
                <h3>已选商品</h3>
                <div class="productsBox">
               <span class="pr1 float_l">
               	<!--
                 <p>账户名称：${admin.username}</p>
                  -->
                  <div class="pro">
                    <dl>
                    	<c:forEach items="${listKeyword}" var="item" varStatus="status">
                    		<dd class="proBox">${item.keywordPackageName}<span>${item.keywordPackageNum}</span>个 </dd>
                    		<c:if test="${!status.last}"><dd class="fh">+</dd></c:if>
                    		<c:if test="${status.last}"><dd class="fh">=</dd></c:if>
                    	</c:forEach>
                    	<dd class="price"><fmt:formatNumber value="${totalPrice }" pattern="#.##" />元</dd>
                    </dl>
                  </div>
               </span>

                    <!--
               <span class="pr2 float_r">
                 <p>本次需支付：</p>
                 <p><span class="price"><fmt:formatNumber value="${totalPrice }" pattern=".00" /> </span>元</p>
                 <p class="mt15"><input name="" type="button" onclick="openDiv()" value="确认支付" class="button button3"></p>
               </span>
               -->
                </div>

                <div class="h10 clear"></div>
                <h3>支付方式</h3>
                <script>
                    $(function(){
                        //点击选中效果
                        $(".pay>li").on("click",function(){
                            $(".pay>li").removeClass("li_click");
                            $(this).addClass("li_click");
                            $("#zhuanzhangCon").hide();

                        });
                        $("#zhuanzhang").click(function(){
                            $("#zhuanzhangCon").show();
                        });
                    });
                </script>
                <input type="hidden" value="1" id="payChannel" />
                <ul class="pay">
                    <li class="li_click" onclick="changePayChannel(1);"><i></i><em class="icon icon1"></em>支付宝支付</li>
                    <li onclick="changePayChannel(2);"><i></i><em class="icon icon2"></em>微信支付</li>
                    <li id="weiboPay" onclick="changePayChannel(4);"><i></i><em class="icon icon5"></em>微博支付</li>
                    <s:if test="order != null && order.orderNo != null && order.orderNo != ''">
                        <li onclick="changePayChannel(3);" id="zhuanzhang"><i></i><em class="icon icon4"></em>公司转帐（适合于线下付款）</li>
                    </s:if>
                </ul>
                <div id="zhuanzhangCon" style="display: none;">
                    <ul class="zzBox">
                        <li>户名：上海蜜度信息技术有限公司</li>
                        <li>帐号：121908363710708</li>
                        <li>开户行：招商银行上海分行四平支行</li>
                    </ul>
                    <p class="f_c2 fz12 mt10">＊请在转帐时备注您的订单号、手机号码，便于我们马上为您办理开通业务的审核工作</p>
                </div>
                <div class=" h10 clear"></div>
                <div class="orderDet">
                    <div class="tit"><h4 class="float_l">订单详情</h4>
                        <s:if test="order != null && order.orderNo != null && order.orderNo != ''">
                            <span class="ml100">订单号：${order.orderNo }</span>
                        </s:if>
                    </div>
                    <%--           <%=com.xd.iis.sp.weiyuqing.pay.config.AlipayConfig.orderId_pre %>${order.orderRecordId} --%>
                    <table border="0" cellspacing="0" cellpadding="0" class="table table3 float_l">
                        <thead>
                        <tr>
                            <th width="40%" class="td_l">产品名称</th>
                            <th width="33%">数量</th>
                            <th width="27%">单位 </th>
                            <!--             <th width="24%">费用</th> -->
                        </tr>
                        </thead>
                        <TBODY>
                        <c:forEach items="${listKeyword}" var="item" >
                            <tr>
                                <td class="td_l">${item.keywordPackageName }</td>
                                <td>${item.keywordPackageNum }</td>
                                <td>
                                    <c:if test="${item.keywordId == 2 || item.keywordId == 3 || item.keywordId == 20 }">
                                        <!-- <fmt:formatNumber value="${item.keywordServeDays/365 }" pattern="#" /> -->年
                                    </c:if>
                                    <c:if test="${item.keywordId == 18 || item.keywordId == 19 || item.keywordId == 21 || item.keywordId == 32 }">
                                        <!-- ${item.keywordServeDays } -->次
                                    </c:if>
                                    <c:if test="${item.keywordId == 22 || item.keywordId == 23 || item.keywordId == 24 || item.keywordId == 25 || item.keywordId == 26 }">
                                        <!-- ${item.keywordServeDays } -->个
                                    </c:if>
                                </td>
                                <%--             <td><span class="f_c7"><fmt:formatNumber value="${item.keywordPackageNum*item.keywordPackagePrice }" pattern=".00" />元</span></td> --%>
                            </tr>
                        </c:forEach>
                        </TBODY>
                    </table>
                </div>
                <div class="bottom">
                    <span class="float_r"><font class="fz24">应付总额：</font><font class="fz24 f_c7 mr15"><fmt:formatNumber value="${totalPrice }" pattern="#.##" /> 元</font> <input name="" onclick="goPay(${payRecord.payRecordId}, '${order.orderNo }')" type="button" value="去支付" class="button button2"></span>
                </div>
                <!--发票管理 end-->

                <!--右侧内容 end-->

            </div>
        </div>
    </div>
    <!--主要内容 end-->
</div>
<!--底部部分代码 start-->
<div class="h35 clear"></div>
<s:include value="/view/bottom.jsp" />
<!--底部部分代码 end-->

<!--加黑色弹窗-->
<div class="cover_tan" style="z-index: 99;height: 2692px; display: none; opacity: 0.5;filter:alpha(opacity=50);" id="blackDiv"></div>

<div style="display: none;z-index: 99" class="cover_box" id="modifyHead">

    <div class="cover_boxline">
        <div class="cover_box_tit rel">
            <h1>请选择支付方式</h1>
            <span class="closeBtn abs" onClick="delDiv()">×</span>
        </div>
        <div class="cover_con">
            <ul style=" margin:30px 0px 30px 50px;">
                <li>订单金额：<fmt:formatNumber value="${totalPrice }" pattern=".00" />元</li>
                <li>订单编号：${order.orderNo }</li>
            </ul>
        </div>

        <div class="cover_bottom2">

            <div class="weixinBtn" onClick="goPay(${payRecord.payRecordId}, 2, '${order.orderNo }')">微信支付</div>
            <div class="zhifubaoBtn" onClick="goPay(${payRecord.payRecordId}, 1)" style="border-right:none;">支付宝支付</div>
            <div class=" clear"></div>
        </div>


    </div>

</div>
<%@ include file="../../buttom.jsp" %>
</body>
</html>
