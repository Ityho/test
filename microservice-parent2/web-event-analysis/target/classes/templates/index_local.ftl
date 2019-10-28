<#include "top.ftl">
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/sm.sp.css"/>
</head>
<body class="blackWhite">
<div class="page-group">
    <div class="page page-current">
        <!--头部导航 start -->
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left back" data-transition='slide-out'>
                <span class="icon icon-left"></span>
            </a>
            <h1 class="title">登录</h1>
            <a class="button button-link button-nav pull-right external" href="${njxBasePath}/user/register" data-transition='slide-out'>立即注册</a>
        </header>
        <!--头部导航 end -->

        <!--主要内容 start -->
        <div class="content">
            <div class="buttons-tab block-tab">
                <a href="#tab1" class="tab-link active button">账号密码登录</a>
                <a href="#tab2" class="tab-link button">手机号快捷登录</a>
            </div>
            <div class="content-block mp-none">

                <div class="tabs">
                    <!-- 账号密码登录 -->
                    <div  id="tab1" class="tab active">
                        <form name="pwdLoginForm" action="${njxBasePath}/user/indexLogin" method="post">
						<input type="hidden" value='${urlType!""}' name="urlType">
                            <div class="content-block mp-none">
                                <div class="list-block m-none">
                                    <ul>

                                        <li>
                                            <div class="item-content">
                                                <div class="item-inner">
                                                    <div class="item-media"><i class="iconfont icon-p-shouji"></i></div>
                                                    <div class="item-input">
                                                        <input type="tel" name="username" placeholder="请输入手机号码">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                        <li>
                                            <div class="item-content">
                                                <div class="item-inner">
                                                    <div class="item-media"><i class="iconfont icon-mima"></i></div>

                                                    <div class="item-input">
                                                        <input id="password" type="password"  placeholder="请输入密码" class="">
                                                        <input type="hidden" name="password" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                    </ul>
                                </div>
                                <div class="content-block"></div>
                                <div class="content-padded">
                                    <a id="pwdLoginBtn" class="button button-big button-fill button-warning">登录</a>
                                </div>
                                <div class="content-padded" style="text-align:right;">
                                    <a href="${njxBasePath}/user/forgetPassword" class="external" style="color: #666;font-size:.7rem">忘记密码？</a>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- 手机号快捷登录 -->
                    <div id="tab2" class="tab">
                        <form name="codeLoginForm" >
                            <input type="hidden" value='${urlType!""}' name="urlType">
                            <input type="hidden" value='${keywords!""}' name="keywords">
                            <div class="content-block docs-icons" style="margin: 0; padding: 0;">
                                <div class="list-block" style="margin: 0;">
                                    <ul>
                                        <li>
                                            <div class="item-content">

                                                <div class="item-inner">
                                                    <div class="item-media"><i class="iconfont icon-p-shouji"></i></div>
                                                    <div class="item-input">
                                                        <input type="tel" name="username" placeholder="请输入手机号码">
                                                    </div>
                                                    <div class="item-after">
                                                        <a id = "getVCodeSpan" href="javascript:getMsmCoad()" class="button button-fill button-warning">获取验证码</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                        <li>
                                            <div class="item-content">

                                                <div class="item-inner">
                                                    <div class="item-media"><i class="iconfont icon-shoujiyanzheng"></i></div>
                                                    <div class="item-input">
                                                        <input id="authcode" type="tel" name="authcode" placeholder="请输入验证码" class="">
                                                    </div>
                                                </div>
                                            </div>
                                        </li>

                                    </ul>
                                </div>
                                <div class="content-block"></div>

                                <div class="content-padded">
                                    <a id="codeLoginBtn" class="button button-big button-fill button-warning">登录</a>
                                </div>
                                <div class="content-padded" style="text-align:right;">
                                    <a href="${njxBasePath}/user/forgetPassword" class="external" style="color: #666;font-size:.7rem">忘记密码？</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="content-padded" style="text-align:center;">
                <span id="new_mfzc" style="color:red;"></span>
            </div>
            <div class="content-padded">
                <!--第三方登录 start -->
                <div class="otherLogin">
                    <div class="title">
                        <span class="iconfont icon-arrowDown"></span>
                        <h1>-第三方登录-</h1>
                    </div>
                    <ul class="otherLoginList">
                        <#--<a href="<%=njxBasePath%>/thirdParty/toSinaWeiboLogin.action" class="btn-circle btn-icon50 btn-icon" title="微博登录">-->
                            <#--<i class="icon">&#xe724;</i>-->
                        <#--</a>-->
                        <#--<a href="<%=njxBasePath%>/thirdParty/toWeixinLogin.action" class="btn-circle btn-icon50 btn-icon" title="微信登录">-->
                            <#--<i class="icon">&#xe716;</i>-->
                        <#--</a>-->
<#--                            <li><a href="${njxBasePath}/thirdParty/toSinaWeiboLogin"><i class="iconfont icon-weibo red external"></i></a></li>-->
<#--                            <li><a href="http://api-open-beta.wrd.cn/api/h5/toWxAuth"><i class="iconfont icon-weixin green external"></i></a></li>-->
                    </ul>
                </div>
                <!--第三方登录 end-->
            </div>

        </div>
        <!--主要内容 end -->
    </div>
</div>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type="text/javascript">saveOperateLog('登录页面','1001');</script>
<script src='${staticResourcePathH5}/js/MD5.js?v=${SYSTEM_INIT_TIME}'></script>
<script type="text/javascript">
    var msg="${msg!""}";
    console.log(msg);
    function onPwdLoginBtn(){

        if ($.trim($("form[name='pwdLoginForm'] input[name='username']").val()) == '') {
            $("#new_mfzc").html('请输入手机号！');
            $("form[name='pwdLoginForm'] input[name='username']").focus();
            return false;
        } else if ($.trim($("#password").val()) == '') {
            $("#new_mfzc").html('请输入密码！');
            $('#password').focus();
            return false;
        }
        var password = $("#password").val();
        var username = $.trim($("form[name='pwdLoginForm'] input[name='username']").val());
        $.ajax({
            type:"post",
            url:actionBase+"/doUserLogin",
            data:{
                'username':username,
                'password':hex_md5(password)
            },
            cache:false,
            success: function(data){
                if(data.code == "0000"){
                    window.location.href='${url!"user"}';
                }else{
                    $("#new_mfzc").html(data.message);
                }
            }
        });

    };
    $(function() {
        var mobile_filter  = /^1[3|4|5|7|8|9][0-9]\d{8}$/;
        //密码登录
        $('#pwdLoginBtn').click(function() {
            if ($.trim($("form[name='pwdLoginForm'] input[name='username']").val()) == '') {
                $("#new_mfzc").html('请输入手机号！');
                $("form[name='pwdLoginForm'] input[name='username']").focus();
                return false;
            } else if ($.trim($("#password").val()) == '') {
                $("#new_mfzc").html('请输入密码！');
                $('#password').focus();
                return false;
            }
            $("input[name='password']").val(hex_md5($("#password").val()));
            document.pwdLoginForm.submit();
        });
        //验证码登录
        $('#codeLoginBtn').click(function() {
            if ($.trim($("form[name='codeLoginForm'] input[name='username']").val()) == '') {
                $("#new_mfzc").html('请输入手机号！');
                $("form[name='codeLoginForm'] input[name='username']").focus();
                return false;
            } else if ($.trim($("#authcode").val()) == '') {
                $("#new_mfzc").html('请输入验证码！');
                $('#authcode').focus();
                return false;
            }
            //$("input[name='authcode']").val(hex_md5($("#password").val()));
            doLogin();
        });
        $(document).on("click",function(){
            $("#new_mfzc").html("");
        });
    });
    var mobile_filter  = /^1[3|4|5|7|8|9][0-9]\d{8}$/;
    function checkLoginMobile(){
        $.ajax({
            type:"post",
            url:actionBase+"/user/checkLoginMobile",
            data:{
                'username':$("form[name='codeLoginForm'] input[name='username']").val()
            },
            cache:false,
            success: function(data){
                if(data == 1){
                    return data;
                }else{
                    $("#new_mfzc").html("该用户不存在");
                    return data;
                }
            }
        });
    }
    function getMsmCoad(){
        var username = $.trim($("form[name='codeLoginForm'] input[name='username']").val()),
                authcode = $.trim($("#authcode").val());
        if(!username) {
            $("#new_mfzc").text("请输入手机号码！");
            return false;
        }
        if(!mobile_filter.test(username)) {
            $("#new_mfzc").text("手机号码不正确!");
            return false;
        }
        $.ajax({
            type:"post",
            url:actionBase+"/user/checkLoginMobile",
            data:{username:username},
            cache:false,
            success: function(data){
                if(data==0) {
                    $("#new_mfzc").html("该用户不存在");
                    return false;
                } else {
                    $.ajax({
                        type:"post",
                        url:actionBase+"/user/sendLoginVcode",
                        data:{username:username},
                        cache:false,
                        success: function(data, textStatus){
                            $('#new_mfzc').text("");
                            if(data && data.code == "0000") {
                                $('#new_mfzc').text("短信验证码已成功发送到您的手机，请尽快输入！");
                                time();
                            }else if(data && data.code != "0000"){
                                $('#new_mfzc').text(data.message);
                            }else{
                                $('#new_mfzc').text("获取验证码异常！");
                            }
                        },
                        error:function(e){
                            conaole.log(e);
                        }
                    });
                }
            }
        });
    }
    function doLogin(){
        var username = $.trim($("form[name='codeLoginForm'] input[name='username']").val()),
                authcode = $.trim($("#authcode").val());
        if(!username) {
            $("#new_mfzc").text("请输入手机号码！");
            return false;
        }
        if(!mobile_filter.test(username)) {
            $("#new_mfzc").text("手机号码不正确!");
            return false;
        }
        if(!authcode) {
            $("#new_mfzc").text("请输入验证码!");
            return false;
        }
        $.ajax({
            type:"post",
            url:actionBase+"/user/checkLoginMobile",
            data:{username:username},
            cache:false,
            success: function(data){
                if(data==0) {
                    $("#new_mfzc").html("该用户不存在");
                    return false;
                } else {
                    $.ajax({
                        type:"post",
                        url:actionBase+"/checkLoginVcode",
                        data:{username:username,authcode:authcode},
                        cache:false,
                        success: function(data){
                            if(data && data.code == "0000" && data.succ){
                                var urlType = "${urlType!""}";
                                if(urlType && urlType.indexOf("register") == -1 && urlType.indexOf("retrievePwd") == -1){
                                    if(urlType.indexOf("?") != -1){
                                        urlType += "&returning=1";
                                    }else{
                                        urlType += "?returning=1";
                                    }
                                    location.href = urlType;
                                }else{
                                    location.href = njxBasePath+"/home?returning=1";
                                }
                            }else if(data && data.code == "0000" && !data.succ){
                                $("#new_mfzc").text("验证码不对，请重新输入！");
                            }else if(data && data.code != "0000"){
                                $("#new_mfzc").text(data.message);
                            }else{
                                $("#new_mfzc").text("验证异常!");
                            }
                        },
                        error:function(data){
                            $("#new_mfzc").text("验证异常!");
                        }
                    });
                }
            }
        });
    }
    //短信验证码下发
    var wait = 120;
    function time() {
        if (wait == 0) {
            $('#getVCodeSpan').text('获取验证码');
            $('#getVCodeSpan').attr('onclick', 'getMsmCoad()');
            wait = 120;
        } else {
            $('#getVCodeSpan').text(wait + ' 秒');
            $('#getVCodeSpan').attr('onclick', '');
            wait--;
            setTimeout(function() {
                time();
            }, 1000);
        }
    }

</script>
<#include "buttom.ftl" >
</body>
</html>