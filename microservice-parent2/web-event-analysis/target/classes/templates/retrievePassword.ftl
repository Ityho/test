<#include "top.ftl" >
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
<link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/sm.sp.css"/>
</head>
<body class="blackWhite">
<div class="page-group">
	<div class="page page-current">
		<!--头部导航 start -->
		<header class="bar bar-nav">
			<a class="button button-link button-nav pull-left back" href="${njxBasePath}/indexLocal" data-transition='slide-out'>
				<span class="icon icon-left"></span>
			</a>
			<h1 class="title">找回密码</h1>
		</header>
		<!--头部导航 end -->
		<div class="content" style="top:20px;">
			<div id="login" style="margin-left:0px;width:100%;">
				<div  class="col" >
					<form action="${njxBasePath}/view/user/updatePassword.shtml" name="doUpdateForm" method="post">
						<input type="hidden" name="admin.username" id="username" />
						<input type="hidden" name="password" id="password3" />
						<input type="hidden" name="admin.passwordStrength" id="passwordStrength" value="1">
					</form>
					<div class="inpoutBox inpoutBox4 content-block" style="border:0px;border-radius:0px;">
						<div class="rel clearfix">
							<div style="float: left;margin-top:18px;width: 40px;"><i style="font-size:22px;" class="iconfont icon-shouji"></i></div>
							<div style="float: left;border-bottom:solid 1px #cacaca;width: 85%; height:60px;">
								<input type="text" id="mobile" name="admin.mobile" placeholder="请输入有效手机号" class="login_input" style="float:left;font-size:16px;"/>
							</div>
						</div>
						<div class="rel clearfix">
							<div style="float: left;margin-top:18px;width: 40px;"><i style="font-size:22px;" class="iconfont icon-yanzhengma"></i></div>
							<div style="float: left;border-bottom:solid 1px #cacaca;width: 85%; height:60px;">
								<input type="text" id="authcode" name="authcode" placeholder="请输入验证码" class="login_input" style="float:left;width:50%;font-size:16px;"/>
								<a id = "get_code"  href="javascript:void(0)" class="yzmBtn" style="float:right;margin-top:12px;background-color:#fca342;color:#FFF;">获取验证码</a>
							</div>
						</div>

						<div class="rel clearfix">
							<div style="float: left;margin-top:18px;width: 40px;"><i style="font-size:22px;" class="iconfont icon-mima"></i></div>
							<div style="float: left;border-bottom:solid 1px #cacaca;width: 85%; height:60px;">
								<input type="password" id="password1" placeholder="新密码" class="login_input" style="float:left;font-size:16px;"/>
							</div>
						</div>

						<div class="rel clearfix">
							<div style="float: left;margin-top:18px;width: 40px;"><i style="font-size:22px;" class="iconfont icon-mima"></i></div>
							<div style="float: left;width: 85%; height:60px;">
								<input type="password" id="password2" placeholder="确认密码" class="login_input" style="float:left;font-size:16px;"/>
							</div>
						</div>
					</div>
					<a id = "update_password" href="javascript:void(0)" class="button button-big button-fill button-warning" style="width:90%;margin-left:5%;">完成</a>

					<span id="new_mfzc" style="color:red;"></span>
				</div>

			</div>
		</div>
	</div>
</div>

<script type="text/javascript">saveOperateLog('忘记密码','1003');</script>
<script src='${staticResourcePathH5}/js/MD5.js?v=${SYSTEM_INIT_TIME}'></script>
<SCRIPT type="text/javascript">
    var mobile_filter  = /^1[3|4|5|7|8|9][0-9]\d{8}$/;
    var password_filter = /^[\@A-Za-z0-9\!#$\%\^\&\*\.\~]{6,20}$/;


    $(function(){
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

        var flag = 0;
        $("#mobile").blur(function(){
            if (!mobile_filter.test($("#mobile").val())){
                $("#new_mfzc").html("手机号码不正确!");
                flag = 2;
            }else{
                $.ajax({
                    type:"post",
                    url:actionBase+"/user/checkLoginMobile",
                    data:{'user.mobile':$("#mobile").val()},
                    cache:false,
                    success: function(data){
                        if(data==1){
                            flag=1;
                            $("#new_mfzc").html("");
                        }else{
                            flag = 0;
                            $("#new_mfzc").html("手机号码不存在!");
                        }
                    }
                });
            }
        });

        $("#get_code").click(function(){
            if(wait != 120)
                return;
            if(flag==1){
                $.ajax({
                    type:"post",
                    url:actionBase+"/view/user/sendSmsLostVcode.shtml",
                    data:{'mobile':$("#mobile").val()},
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
            }else if(flag==0){
                $("#new_mfzc").html("手机号码不存在!");
            }else if(flag==2){
                $("#new_mfzc").html("手机号式不正确!");
            }
        });

        $("#update_password").click(function(){
            if(flag==1){
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
                    url:actionBase+"/view/user/checkUpdatePasswordVcode.shtml",
                    data:{
                        'admin.mobile':$("#mobile").val(),
                        'passwordVCode':$("#authcode").val(),
                    },
                    cache:false,
                    success: function(data){
                        if(data && data.code == "0000" && data.succ){
                            $('#username').val($("#mobile").val());
                            $("#password3").val(hex_md5($("#password1").val()));

                            var str1 = /^[0-9]{6,64}$/;
                            var str2 = /^[a-zA-Z]{6,64}$/;
                            var str3 = /^[0-9|a-z|A-Z]{6,64}$/;

                            var pas = $('#password2').val();
                            if(str1.test(pas))
                                $("#passwordStrength").val(1);
                            else if(str2.test(pas))
                                $("#passwordStrength").val(2);
                            else if(str3.test(pas))
                                $("#passwordStrength").val(3);
                            else
                                $("#passwordStrength").val(3);
                            document.doUpdateForm.submit();
                        } else if(data && data.code == "0000" && !data.succ){
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
            } else if(flag == 0) {
                $("#new_mfzc").html("手机号码不存在!");
            } else{
                $("#new_mfzc").html("手机号码不正确!");
            }
        });


    });

    //短信验证码下发
    var wait = 120;
    function time() {
        if (wait == 0) {
            $('#get_code').text('获取验证码');
            wait = 120;
        } else {
            $('#get_code').text(wait + ' 秒');
            wait--;
            setTimeout(function() {
                time();
            }, 1000);
        }
    }

</SCRIPT>
<script src="https://files.wyq.cn/web/js/banner.js" type="text/javascript"></script>
<script>
    $(function() {
        if (window.PIE) {
            $('.rounded').each(function() {
                PIE.attach(this);
            });

        }
    });
</script>
<#include "buttom.ftl" >
</body>
</html>