<#include "../common/resourcePath.ftl"/>
    <style type="text/css">

    </style>
<link href="${staticResourcePath}/css/newRegin/font-icon.css" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/newRegin/style.css" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/newRegin/loginStyle.css" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/newRegin/common.css" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/newRegin/jquery-ui-1.9.1.custom.css?" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${staticResourcePath}/js/newRegin/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${staticResourcePath}/js/newRegin/jquery-ui-1.9.1.custom.js"></script>
<#--<%-- <script type="text/javascript" src="${staticResourcePath}/js/newRegin/bootstrap.min.js"></script> --%>-->
<script type="text/javascript" src="${staticResourcePath}/js/newRegin/Register.js"></script>
<style type="text/css">
    .layout-row11 {
        background-color: #3b4c54 !important;
    }
</style>
<script language="javascript">
    var baseAction = '${staticResourcePath}';
    var ctx = '${staticResourcePath}';
    $(function() {
        if (window.PIE) {
            $('.rounded').each(function() {
                PIE.attach(this);
            });

        }
    });

    $(function () { $("[data-toggle='tooltip']").tooltip(); });

    function userLogin() {
//            mailCheck();
        location.href = baseAction+'/hot/events/goHotHome';
    }
    var emails;
    //输入邮件地址的检查（格式和是否已存在）
    function mailCheck(){
        var emailRe = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        var contentEl = document.getElementById("mobile");
        if(""==contentEl){
            location.href =baseAction+ '/hot/events/goHotHome';
            return;
        }
        emails = contentEl.value;
        if (emails.length>0){
            if (emails.indexOf(',')>0){
                var email  = emails.split(",");
                for (var i=0; i<email.length; i++){
                    if (email[i].length>0){
                        if(!emailRe.exec(email[i])){
                            /*$("#tips").text("邮箱地址:[" + email[i] + "],格式不正确!").show();
                             contentEl.focus();*/
                            location.href = baseAction+'/hot/events/goHotHome';
                            return;
                        }
                    }
                }
            }
            else{
                if(!emailRe.test(emails)){
                    /*$("#tips").text("邮箱地址:[" + emails + "],格式不正确!").show();
                     contentEl.focus();*/
                    location.href =baseAction +'/hot/events/goHotHome';
                    return;
                }
            }
            <#--//NewsOperate.checkMailOrNot(emails,${admin.userId},GetRandom(30),CheckCallBack);-->
            mailSend();
        }else{
            location.href = baseAction+'/hot/events/goHotHome';
        }
    }

    function GetRandom(n){
        GetRandomn=Math.floor(Math.random()*n+1);
        return GetRandomn;
    }

    //邮件的验证发送
    function mailSend(){
        var emailAddress = emails;
        if(!$.trim(emailAddress)){
            location.href = baseAction+'/hot/events/goHotHome';
        }else{
            var emailTitle = "请激活接收邮件";
            NewsOperate.sendMailValidate(emailAddress, emailTitle,GetRandom(30), function(){location.href = baseAction+'/hot/events/goHotHome';});
        }
    }
</script>

<body class="RegisterBody rel">
<#include "../common/navigate.ftl"/>
<!-- <div id="head" class="rel head-nav head-bg">        -->
<!--             <div class="head-top"> -->
<!--                 <div class="float_l"> -->
<#--<%--                     <img src="<%=staticResourcePath%>/images/logo.png" class="logo"> --%>-->
<!--                 </div> -->
<!--                 <div class="rightNav mt10 ml30 fz16 dis-i"> -->

<!-- 						<ul> -->
<!-- 							<li class="link"> -->
<!-- 								<a href="/login.shtml">首页</a> -->
<!-- 							</li> -->
<!-- 							<li class="link nav-hover nav-hover-1"> -->
<!-- 								<a href="javascript:void(0)">信息监测</a> -->
<!-- 							</li> -->
<!-- 							<li class="link nav-hover nav-hover-2"> -->
<!-- 								<a href="javascript:void(0)">简报制作</a> -->
<!-- 							</li> -->
<!-- 							<li class="link nav-hover nav-hover-3"> -->
<!-- 								<a href="javascript:void(0)">大数据分析</a> -->
<!-- 							</li> -->
<!-- 							<li class="link"> -->
<!-- 								<a href="http://www.yqt365.com/introducePage.action?fromChannel=5" target="_Blank">政务专业版</a> -->
<!-- 							</li> -->
<!-- 							<li class="link nav-hover nav-hover-4"> -->
<!-- 								<a href="javascript:void(0)">新媒体运营</a> -->
<!-- 							</li> -->
<!-- 							<li class="link"> -->
<!-- 								<a href="/infoData.shtml">大数据解读</a> -->
<!-- 							</li> -->
<!-- 							<li class="link nav-hover nav-hover-5"> -->
<!-- 								<a href="javascript:void(0)">关于我们</a> -->
<!-- 							</li> -->
<!-- 						</ul> -->
<!-- 				</div> -->

<!--              </div> -->

<!--    </div> -->
<div class="page-container">
    <div class="content" style="height: 500px;margin: 90px auto;">
        <div class="pading30 p_v_60 text-center">

            <div class="row clearfix">
                <img src="${staticResourcePath}/images/newuser/reg-icon1.png"/>
                <!-- 		   <img src="images/reg-icon1.png"/> -->
                <h3 class="fz20 mt25 mb15">Hi 亲爱的微友，恭喜您，注册完成~</h3>
                <p class="fc_dark_grey">立即登录可领取免费监测方案 </p>
                <div class="text-center mt50">
                    <button onclick="userLogin()" class="btn btn-orange w200" href="#">马上登录</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!--底部部分代码 start-->
<#include "../common/bottoms.ftl"/>
<!--底部部分代码 end-->
<script type="text/javascript">
    $(function() {
        $("#login").attr("href","${staticResourcePath}/hot/events/goHotHome?login=1");
        $("#login").attr("onclick","");
        $("#login").attr("data-toggle","");
        $("#login").attr("data-target","");
    });
</script>
</body>
</html>

