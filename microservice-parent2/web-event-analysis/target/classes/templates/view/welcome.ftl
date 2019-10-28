<#include "../top.ftl" >
<script type="text/javascript">saveOperateLog('欢迎界面','2001');</script>
<link rel="stylesheet" href="${staticResourcePathH5}/css/swiper.min.css?v=${SYSTEM_INIT_TIME}">
<style>.swiper-container {
	width: 100%;
	height: 100%;
}
</style>
</head>
<body class="blackWhite">
<div id="login" style="width:100%;margin-left:0px;height: 100%;">
	<div  class="col" style="margin-top:0px;width:100%;height:30%;display: inline-block;background-image: url(${njxBasePath}/images/loginbg.png);background-size:100%;background-repeat:no-repeat;">
		<div class="logo" style="margin-top:30px;background-size:80%;height:50%;"></div>
		<h1 style="line-height:20px;">欢迎使用微热点</h1>
	</div>
	<div  class="col" style="margin-top:5px;height:48%;">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="${njxBasePath}/images/welcome/rdcx.png" style="height: 80%;">
				</div>
				<div class="swiper-slide">
					<img src="${njxBasePath}/images/welcome/bdph.png" style="height: 80%;">
				</div>
				<div class="swiper-slide">
					<img src="${njxBasePath}/images/welcome/xxjc.png" style="height: 80%;">
				</div>
				<div class="swiper-slide">
					<img src="${njxBasePath}/images/welcome/dzfx.png" style="height: 80%;">
				</div>
			</div>
			<!-- Add Pagination -->
			<div class="swiper-pagination"></div>
		</div>
	</div>
	<div  class="col" style="margin-top:20px;height:20%;">
		<button style="background: #fc9c41;border-radius:5px;width:200px;height:40px;" onclick="javascript:location.href='${njxBasePath}/weiboHome.shtml';">立即体验</button>
	</div>
</div>
<script src="${staticResourcePathH5}/js/swiper.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript">
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        autoplay: 2500,
    });
</script>
<#include "../buttom.ftl" >
</body>
</html>