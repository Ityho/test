<#include "../../top.ftl" >
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${staticResourcePathH5}/js/wxpay.js?v=${SYSTEM_INIT_TIME }"></script>
<script>
    $(function(){
        weixinPay();
    })
</script>
</head>
<body>
<h1>正在进行微信支付....
    <br/>
    <small>请不要关闭</small>
</h1>
<input id="appId" type="hidden" value="${appId}" /><br/>
<input id="timeStamp" type="hidden" value="${timeStamp}" /><br/>
<input id="nonceStr" type="hidden" value="${nonceStr}" /><br/>
<input id="signature" type="hidden" value="${signature}" /><br/>

<input id="signType" type="hidden" value="${signType}" /><br/>
<input id="package" type="hidden" value="${package}" /><br/>
<input id="paySign" type="hidden" value="${paySign}" /><br/>
<input id="outTradeNo" type="hidden" value="${out_trade_no}" /><br/>
<input id="packageId" type="hidden" value="${packageId}" /><br/>
<input id="packageNum" type="hidden" value="${packageNum}" /><br/>
<input id="orderType" type="hidden" value="${orderType}" /><br/>
<#include "../../buttom.ftl" >
</body>
</html>