<#include "top.ftl" >
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/sm.sp.css"/>
</head>
<body class="blackWhite">
<div class="page-group">
    <div class="page page-current">
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left back" href="${njxBasePath}/userLogin" data-transition='slide-out'>
                <span class="icon icon-left"></span>
            </a>
            <h1 class="title">注册</h1>
        </header>
        <div class="content" style="top:20px;">
            <div id="login" style="margin-left:0px;width:100%;">
                <div  class="col">
                    <form name="frm" method="post">
                        <input type="hidden" name="user.passwordStrength" id="passwordStrength" value="1">
                        <div class="inpoutBox inpoutBox4 content-block" style="border:0px;border-radius:0px;">
                            <div class="rel clearfix">
                                <div style="float: left;margin-top:18px;width: 40px;"><i style="font-size:22px;" class="iconfont icon-shouji"></i></div>
                                <div style="float: left;border-bottom:solid 1px #cacaca;width: 85%; height:60px;">
                                    <input type="text" id = "mobile" name="user.mobile" placeholder="请输入手机号码" class="login_input" style="float:left;font-size:16px;" />
                                </div>
                            </div>

                            <div class="rel clearfix">
                                <div style="float: left;margin-top:18px;width: 40px;"><i style="font-size:22px;" class="iconfont icon-mima"></i></div>
                                <div style="float: left;border-bottom:solid 1px #cacaca;width: 85%; height:60px;">
                                    <input type="password" id="password1" placeholder="请输入密码" class="login_input" style="float:left;font-size:16px;"/>
                                    <input type="hidden" name="user.password" id="password3">
                                </div>
                            </div>
                            <div class="rel clearfix">
                                <div style="float: left;margin-top:18px;width: 40px;"><i style="font-size:22px;" class="iconfont icon-mima"></i></div>
                                <div style="float: left;border-bottom:solid 1px #cacaca;width: 85%; height:60px;">
                                    <input type="password" id="password2" placeholder="请输入密码" class="login_input" style="float:left;font-size:16px;"/>
                                </div>
                            </div>
                            <div class="rel clearfix">
                                <div style="float: left;margin-top:18px;width: 40px;"><i style="font-size:22px;" class="iconfont icon-yanzhengma"></i></div>
                                <div style="float: left;width: 85%; height:60px;">
                                    <input type="text" id="authcode" name="authcode" placeholder="请输入验证码" class="login_input" style="float:left;width:50%;font-size:16px;"/>
                                    <a id = "getVCodeSpan" onclick="getMsmCoad()" href="javascript:void(0)" class="yzmBtn" style="float:right;margin-top:12px;background-color:#fca342;color:#FFF;">获取验证码</a>
                                </div>
                            </div>

                        </div>
                        <a href="javascript:void(0)" class="button button-big button-fill button-warning" style="width:90%;margin-left:5%;"onclick="doRegister()">注册</a>
                        <div class="foot-login">
                            注册即视为同意<a href="${njxBasePath}/readPage" style="color: #9aa9e4;">《微热点服务协议》</a>
                        </div>
                    </form>
                    <div class="content-padded" style="text-align:center;">
                        <span id="new_mfzc" style="color:red;"></span>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<script src='${staticResourcePathH5}/js/MD5.js?v=${SYSTEM_INIT_TIME }'></script>
<script type="text/javascript">saveOperateLog('注册页面','1002');</script>
<script type="text/javascript">
    var baseAction = '';
    var mail_filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var mobile_filter  = /^1[3|4|5|7|8|9][0-9]\d{8}$/;
    var password_filter = /^[\@A-Za-z0-9\!#$\%\^\&\*\.\~]{6,20}$/;

    function doRegister(){
        if($("#mobile").val() == '') {
            $("#new_mfzc").text("请输入手机号码！");
            return false;
        }
        if(!mobile_filter.test($("#mobile").val())) {
            $("#new_mfzc").text("手机号码不正确!");
            return false;
        }
        var password = $("#password1").val();
        var password2 = $("#password2").val();

        if(!password_filter.test(password)) {
            $("#new_mfzc").text("密码格式不正确!");
            return false;
        }
        if(password != password2) {
            $("#new_mfzc").text("两次输入的密码不一致!");
            return false;
        }
        if($("#authcode").val() == '') {
            $("#new_mfzc").text("请输入验证码!");
            return false;
        }

        $.ajax({
            type:"post",
            url:actionBase+"/user/checkLoginMobile",
            data:{
                'user.mobile':$("#mobile").val()
            },
            cache:false,
            success: function(data){
                if(data==1) {
                    $("#new_mfzc").html("该号码已经存在，请直接<a href=actionBase+'/user/login'>登录</a>");
                    return false;
                } else {
                    $('#password3').val(password);

                    $.ajax({
                        type:"post",
                        url:actionBase+"/user/checkLoginMobile",
                        data:{
                            'user.mobile':$("#mobile").val()
                        },
                        cache:false,
                        success: function(data){
                            if(data==1){
                                $("#new_mfzc").html("该号码已经存在，请直接<a href=actionBase+'/user/login'>登录</a>");
                            }else{
                                $("#new_mfzc").text("");

                                $.ajax({
                                    type:"post",
                                    url:actionBase+"/user/checkRegVcode?user.mobile="+$("#mobile").val(),
                                    data:{authcode:$("#authcode").val()},
                                    cache:false,
                                    success: function(data){
                                        if(data && data.code == "0000" && data.succ){
                                            $("#password3").val(hex_md5($("#password3").val()));

                                            var str1 = /^[0-9]{6,64}$/;
                                            var str2 = /^[a-zA-Z]{6,64}$/;
                                            var str3 = /^[0-9|a-z|A-Z]{6,64}$/;

                                            var pas = $('#password1').val();
                                            if(str1.test(pas))
                                                $("#passwordStrength").val(1);
                                            else if(str2.test(pas))
                                                $("#passwordStrength").val(2);
                                            else if(str3.test(pas))
                                                $("#passwordStrength").val(3);
                                            else
                                                $("#passwordStrength").val(3);

                                            $.ajax({
                                                type: "post",
                                                url: actionBase + "/user/doRegister",
                                                data: {
                                                    'mobile': $("#mobile").val(),
                                                    'password': $("#password1").val()

                                                },
                                                cache: false,
                                                success: function (data) {

                                                }
                                            });
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

    function getMsmCoad(){
        if($("#mobile").val() == '') {
            $("#new_mfzc").text("请输入手机号码！");
            return false;
        }
        if(!mobile_filter.test($("#mobile").val())) {
            $("#new_mfzc").text("手机号码不正确！");
            return false;
        }

        $.ajax({
            type:"post",
            url:actionBase+"/user/checkLoginMobile",
            data:{
                'user.mobile':$("#mobile").val()
            },
            cache:false,
            success: function(data){
                if(data==1) {
                    $("#new_mfzc").html("该号码已经存在，请直接<a href='/indexLocal'>登录</a>");
                    return false;
                } else {
                    $.ajax({
                        type:"post",
                        url:actionBase+"/user/sendRegVcode",
                        data:{
                            'mobile':$("#mobile").val()
                        },
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
                        error:function(data){}
                    });
                }
            }
        });
    }

    $(function(){
        $('.RegistBtn').css('display', 'none');

        $("#mobile").focus(function(){
            $("#new_mfzc").text("");
        });
        $("#password1").focus(function(){
            $("#new_mfzc").text("");
        });
        $("#password2").focus(function(){
            $("#new_mfzc").text("");
        });
        $("#authcode").focus(function(){
            $("#new_mfzc").text("");
        });

        $("#password1").blur(function(){
            var password = $("#password1").val();
            if(password != '' && password.length >= 6)
                $("#new_mfzc").text("");
            else
                $("#new_mfzc").text("登录密码最少为6位!");
        });

        $("#password2").blur(function(){
            if($("#password1").val() != $("#password2").val())
                $("#new_mfzc").text("两次输入的密码不一致!");
            else
                $("#new_mfzc").text("");
        });


        $("#mobile").blur(function(){
            if (!mobile_filter.test($("#mobile").val())){
                $("#new_mfzc").text("手机号码不正确!");
                return false;
            }

            $.ajax({
                type:"post",
                url:actionBase+"/user/checkLoginMobile",
                data:{
                    'user.mobile':$("#mobile").val()
                },
                cache:false,
                success: function(data){
                    if(data==1)
                        $("#new_mfzc").html("该号码已经存在，请直接<a href=actionBase+'/user/login'>登录</a>");
                    else
                        $("#new_mfzc").text("");
                }
            });
        });
    });

    function agreeProtocol(obj){
        if(obj.checked){
            $("#ty_zc").removeAttr("disabled");
            $("#ty_zc").attr("class", "ty_zc");
        }else{
            $("#ty_zc").attr("disabled","disabled");
            $("#ty_zc").attr("class", "ty_hs");
        }
    }

</script>
<#include "buttom.ftl">
</body>
</html>