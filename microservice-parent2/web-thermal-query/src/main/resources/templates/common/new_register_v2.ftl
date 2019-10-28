<#--<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>-->
<#--<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>-->
<#--<%@ taglib prefix="s" uri="/struts-tags"%>-->
<#--<% request.setAttribute("page_title",""); %>-->
<#--<c:set var="ctx" value="${pageContext.request.contextPath}" />-->
<#include "../common/top.ftl"/>
<link href="${staticResourcePath}/css/newRegin/font-icon.css" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/newRegin/style.css" rel="stylesheet" type="text/css">
<#--<%-- <link href="${staticResourcePath}/css/newRegin/loginStyle.css" rel="stylesheet" type="text/css"> --%>-->
<link href="${staticResourcePath}/css/newRegin/common.css" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/newRegin/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${staticResourcePath}/js/newRegin/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${staticResourcePath}/js/newRegin/jquery-ui-1.9.1.custom.js"></script>
<#--<%-- <script type="text/javascript" src="${staticResourcePath}/js/newRegin/bootstrap.min.js"></script> --%>-->
<script type="text/javascript" src="${staticResourcePath}/js/newRegin/Register.js"></script>
<style type="text/css">
    .layout-row11 {
        background-color: #3b4c54 !important;
    }
</style>
<#assign njxBasePath = request.getContextPath() />
<script type="text/javascript">
    var operateLogPageCode = '1002';
    var operateLogPageName = '注册';
    var operateLogRequestPath = '${njxBasePath}';

</script>
<script language="javascript">
    $(function() {
        if (window.PIE) {
            $('.rounded').each(function() {
                PIE.attach(this);
            });

        }
    });
</script>
<script type="text/javascript">
    var baseAction = '${staticResourcePath}';
    var ctx = '${staticResourcePath}';
    var mail_filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var mobile_filter  = '${mobile_number_regex}';
    var password_filter = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\,\~]{6,20}$/;

    function doRegister2(){
        $("#mobile_show_info2").text("");
        $("#mobile_show_info2").hide();
        if($("#mobile2").val() == '') {
            $("#mobile_show_info2").text("请输入手机号码！");
            $("#mobile_show_info2").show();
            return false;
        }
        if(!mobile_filter.test($("#mobile2").val())) {
            $("#mobile_show_info2").text("手机号码不正确!");
            $("#mobile_show_info2").show();
            return false;
        }
        var password = $("#password22").val();
        if($("#password22").val() == '') {
            $("#mobile_show_info2").text("请输入密码!");
            $("#mobile_show_info2").show();
            return false;
        }
        if(!password_filter.test(password)) {
            $("#mobile_show_info2").text("密码格式不正确!");
            $("#mobile_show_info2").show();
            return false;
        }

        if($("#authcode2").val() == '') {
            $("#mobile_show_info2").text("请输入验证码!");
            $("#mobile_show_info2").show();
            return false;
        }

        $.ajax({
            type:"post",
            url: actionBase + "/user/events/checkMobile",
            data:{
                'mobile':$("#mobile2").val()
            },
            success: function(data){
                if(data==2){
                    $("#mobile_show_info2").text("请求太频繁，请稍后再试！");
                    $("#mobile_show_info2").show();
                }else if(data==1) {
                    $("#mobile_show_info2").html("该号码已经存在，请直接<a class='link_orange' href='${staticResourcePath}/hot/events/goHotHome?login=1'>登录</a>");
                    $("#mobile_show_info2").show();
                } else {
                    $("#mobile_show_info2").text("");
                    $("#mobile_show_info2").hide();
                    $.ajax({
                        type:"post",
                        url:actionBase+"/user/events/invideGoRegisterV2",
                        data:{
                            "mobile":$("#mobile2").val(),
                            "authcode":$("#authcode2").val(),
                            "password":hex_md5($("#password22").val())
                        },
                        success: function(data){
                            if(data.code=="0000"){
                                document.F.action=actionBase+"/user/events/registerV2Success";
                                document.F.submit();
                            }else{
                                $("#mobile_show_info2").html(data.message);
                                $("#mobile_show_info2").show();
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
            $('#getVCodeSpan2').text('获取验证码');
            $('#getVCodeSpan2').attr('onclick', 'getMsmCoad()');
            wait = 120;
        } else {
            $('#getVCodeSpan2').text(wait + ' 秒');
            $('#getVCodeSpan2').attr('onclick', '');
            wait--;
            setTimeout(function() {
                time();
            }, 1000);
        }
    }

    function getMsmCoad2(){
        $("#mobile_show_info2").text("");
        $("#mobile_show_info2").hide();
        if($("#mobile2").val() == '') {
            $("#mobile_show_info2").text("请输入手机号码！");
            $("#mobile_show_info2").show();
            return ;
        }
        if(!mobile_filter.test($("#mobile2").val())) {
            $("#mobile_show_info2").text("手机号码不正确！");
            $("#mobile_show_info2").show();
            return ;
        }

        $.ajax({
            type:"post",
            url:actionBase + "/user/events/checkMobile",
            data:{
                'mobile':$("#mobile2").val()
            },
            cache:false,
            success: function(data){
                if(data==1) {
                    $("#mobile_show_info2").html("该号码已经存在，请直接<a class='link_orange' href='${staticResourcePath}/hot/events/goHotHome?login=1'>登录</a>");
                    $("#mobile_show_info2").show();
                    return ;
                } else {
                    $.ajax({
                        type:"post",
                        url:actionBase + "/user/events/sendVcodeSMS",
                        data:{
                            'mobile':$("#mobile2").val()
                        },
                        cache:false,
                        success: function(data, textStatus){
                            $('#vcode_show_info').text("");
                            if (!$.isEmptyObject(data)) {
                                var code=data.code;
                                if (code=='0000') {
                                    time();
                                    $("#mobile_show_info2").show().text('验证短信已发送，请输入验证码！');
                                }else{
                                    $("#mobile_show_info2").show().text(data.message);
                                }

                            }

                        },
                        error:function(data){}
                    });
                }
            }
        });
    }

    $(function(){
<#--// 	$('.RegistBtn').css('display', 'none');-->

<#--// 	$("#mobile2").focus(function(){-->
<#--// 		$("#mobile_show_info2").text("请输入你的11位手机号码");-->
<#--//         $("#mobile_show_info2").show();-->
<#--// 	});-->
<#--// 	$("#password22").focus(function(){-->
<#--// 		$("#mobile_show_info2").text("数字和字母结合，至少6位");-->
<#--//         $("#mobile_show_info2").show();-->
<#--// 	});-->
<#--// 	$("#password2").focus(function(){-->
<#--// 		$("#show_info").text("");-->
<#--// 	});-->
<#--// 	$("#authcode2").focus(function(){-->
<#--// 		$("#mobile_show_info2").text("");-->
<#--// 	});-->

<#--// 	$("#password22").blur(function(){-->
<#--// 		var password = $("#password22").val();-->
<#--// 		if(password != '' && password.length >= 6) {-->
<#--//             $("#mobile_show_info2").text("");-->
<#--//             $("#mobile_show_info2").hide();-->
<#--//         }else{-->
<#--//             $("#mobile_show_info2").show();-->
<#--//             $("#mobile_show_info2").text("登录密码最少为6位!");-->
<#--//         }-->
<#--// 	});-->

<#--// 	$("#mobile2").blur(function(){-->
<#--// 		$('#float-captcha-li').hide();-->
<#--// 		 if (!mobile_filter.test($("#mobile2").val())){-->
<#--// 			 $("#mobile_show_info2").text("手机号码不正确!");-->
<#--//              $("#mobile_show_info2").show();-->
<#--// 			 return false;-->
<#--// 		 }-->

<#--// 		 $.ajax({-->
<#--// 			type:"post",-->
<#--// 			url:"${ctx}/view/user/checkMobile.action",-->
<#--// 			data:{-->
<#--// 				'user.mobile':$("#mobile2").val()-->
<#--// 			},-->
<#--// 			cache:false,-->
<#--// 			success: function(data){-->
<#--// 				if(data==1) {-->
    <#--&lt;#&ndash;<%--                     $("#mobile_show_info2").html("该号码已经存在，请直接<a href='${staticResourcePath}/login.shtml?login=1'>登录</a>"); --%>&ndash;&gt;-->
<#--//                     $("#mobile_show_info2").show();-->
<#--//                 }else{-->
<#--//                 	$('#float-captcha-li').show();-->
<#--// 			    	 $("#mobile_show_info2").text("");-->
<#--//                     $("#mobile_show_info2").show();-->
<#--//                 }-->
<#--//             }-->
<#--// 		});-->
<#--// 	});-->
    });

    function agreeProtocol(obj){
        if(obj.checked){
            $("#ty_zc").removeAttr("disabled");
            //$("#ty_zc").attr("class", "ty_zc");
        }else{
            $("#ty_zc").attr("disabled","disabled");
        }
    }
</script>

<body class="RegisterBody rel layoutBody">
<#include "../common/navigate.ftl"/>
<div class="page-container" style="padding-top: 120px;padding-bottom: 80px;background-color: #edeef0;">
    <div class="content" style="margin: 0px auto;">
        <div class="pading30 p_v_60">

            <div class="row clearfix">
                <div class="col-md-4">&nbsp;</div>
                <div class="col-md-4">
                    <div class="form-horizontal">

                        <div class="form-group mb20">
                            <div class="mb5 fz14 fc_dark_grey">请填写手机号</div>
                            <input style="display:none" class="input input_red b-radius5" type="text"   name="user.mobile" value=""  style="width: 100%;"/>
                            <input onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/" class="input input_red b-radius5" type="text" autocomplete="off" id="mobile2" name="user.mobile" value=""  style="width: 100%;"/>
                        </div>
                        <#--<%-- 		  		    <span id="mobile_show_info2" style="color: red"></span> --%>-->

                        <div class="form-group mb20">
                            <div class="mb5 fz14 fc_dark_grey">请设置密码（至少6个字符）</div>
                            <input style="display:none" class="input input_red b-radius5" type="password" name="user.password"  value=""  style="width: 100%;"/>
                            <input class="input input_red b-radius5" type="password" autocomplete="off" name="user.password" id="password22" value=""  style="width: 100%;"/>
                        </div>
                        <span id="password_show_info2" style="color: red"></span>
                        <div class="form-group mb20 rel">
                            <div class="mb5 fz14 fc_dark_grey">请填写验证码</div>
                            <a class="btn btn-orange abs" style="bottom: 0; right: 0; z-index: 1;" readonly id="getVCodeSpan2" onclick="getMsmCoad2()">获取验证码</a>
                            <input style="display:none" name="authcode">
                            <input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" class="input input_red b-radius5" autocomplete="off" type="text" id="authcode2" name="authcode" value=""  style="width: 100%;" value=""/>
                        </div>
                        <span  id="authcode_show_info2" style="color: red"></span>
                        <div class="text-center">
                            <a onclick="doRegister2()" class="btn btn-orange mt10 w200">立即注册</a>
                        </div>


                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-horizontal">
                        <div class="form-group mb20">
                            <div class="mb5 fz14 fc_dark_grey">&nbsp;</div>
                            <div id="mobile_show_info2" class="mt10 fc_dark_grey"></div>
                            <!-- 		  		    <div id="mobile_show_info2" class="mt10 fc_dark_grey">该号码已存在，<a class="link_orange" href="#">去登录</a></div> -->
                        </div>
                    </div>
                </div>
            </div>

            <div class="row clearfix mt30">
                <div class="col-md-2">&nbsp;</div>
                <div class="col-md-8">
                    <div class="loginBtn mt10">
                        <h1><span>使用其他方式登录</span></h1>
                        <div class="text-center">
                            <a href="${staticResourcePath}/thirdParty/toSinaWeiboLogin.action" class="btn-circle btn-icon50 btn-icon" title="微博登录">
                                <i class="icon">&#xe724;</i>
                            </a>
                            <a href="${staticResourcePath}/thirdParty/toWeixinLogin.action" class="btn-circle btn-icon50 btn-icon" title="微信登录">
                                <i class="icon">&#xe716;</i>
                            </a>
                            <a href="${staticResourcePath}/thirdParty/toTencentLogin.action" class="btn-circle btn-icon50 btn-icon" title="QQ登录">
                                <i class="icon">&#xe728;</i>
                            </a>
                            <!-- 							<a  class="btn-icon btn-icon50 btn-circle bg_red ml20" title="京东万象登录"><i class="icon-jingdongwanxiang"></i></a> -->
                            <#--<%-- 							<a href="<%=njxBasePath%>/thirdParty/toJCloudLogin.action" class="btn-circle btn-icon50 btn-icon" title="京东万象登录"> --%>-->
                            <!-- 							   <i class="icon">&#xe6fd;</i> -->
                            <!-- 							</a> -->
                        </div>

                    </div>
                </div>
                <div class="col-md-2">&nbsp;</div>
            </div>
            <div class="row clearfix">
                <div class="text-right fc_dark_grey">
                    已有帐号？ <a class="fc_orange-b" href="${staticResourcePath}/hot/events/goHotHome?login=1" >立即登录</a>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- <form method="post" id="registerForm" name="registerForm"></form> -->
<script type="text/javascript">
    var geeTestData;
    $(function() {
        var handlerEmbed = function(captchaObj) {
            // 将验证码加到id为captcha的元素里
            captchaObj.appendTo("#float-captcha");
            captchaObj.onReady(function() {
                $("#wait").hide();
            });
            captchaObj.onSuccess(function(data) {
                $('#vcode_show_info').css('display', 'none');
                var validate = captchaObj.getValidate();
                if (!validate) {
                    return;
                }
                $.ajax({
                    url: baseAction + "/VerifyLoginServlet?t=" + (new Date()).getTime(), // 进行二次验证
                    type: "post",
                    dataType: "json",
                    data: {
                        // 二次验证所需的三个值
                        geetest_challenge: validate.geetest_challenge,
                        geetest_validate: validate.geetest_validate,
                        geetest_seccode: validate.geetest_seccode,
                        'mobile' : $("#mobile2").val()
                    },
                    success: function (data) {
                        geeTestData = data;
                    }
                });
            });
        };
        $("#login").attr("href","${staticResourcePath}/hot/events/goHotHome?login=1");
        $("#login").attr("onclick","");
        $("#login").attr("data-toggle","");
        $("#login").attr("data-target","");

        //绑定回车事件
        $('.content').keydown(function(event) {
            if (event.keyCode == 13) {
                event.preventDefault();
                $(this).change();
                doRegister2();
            }
        });
        $("#mobile2").focus(function(){
            $("#mobile_show_info2").html("");
            $("#mobile_show_info2").hide();
        });
        $("#password22").focus(function(){
            $("#mobile_show_info2").html("");
            $("#mobile_show_info2").hide();
        });
        $("#authcode2").focus(function(){
            $("#mobile_show_info2").html("");
            $("#mobile_show_info2").hide();
        });
    });
</script>

<!--底部部分代码 start-->
<#include "../common/bottoms.ftl"/>
<!--底部部分代码 end-->
<!--增强复选框和单选按钮 js-->
<script src="${staticResourcePath}/js/icheck.js"></script>
<script>
    $(document).ready(function(){
        $('.icheckbox-list input').on('ifCreated ifClicked ifChanged ifChecked ifUnchecked ifDisabled ifEnabled ifDestroyed', function(event){
        }).iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%'
        });

        $('#agreeInput').on('ifChecked', function(event){
            var $selectedvalue = $("#agreeInput")[0];
            agreeProtocol($selectedvalue);
        });
    });
</script>
</body>
<#if msg??>
    <script type="text/javascript">
        $("#mobile_show_info2").html("${msg }");
    </script>
</#if>
</html>

