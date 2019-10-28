$(function() {
    var sid = '';
    var reg = new RegExp("(^|&)sid=([^&]*)(&|$)", "i"); // 匹配目标参数
    var result = window.location.search.substr(1).match(reg); // 对querystring匹配目标参数
    if (result != null) {
        sid = decodeURIComponent(result[2]);
    }

    if(sid == ''){
        // 检测用户是否已经登录
        $.ajax({
            url:njxBasePath + '/identiy/login/ajaxIsLogin',
            type:'POST',
            success:function(data) {
                if(data.data == 1) {
                    location.href = njxBasePath + '/quickLogin.shtml';
                } else {
                    getRan();
//					$('#pCode').keydown(function(e){
//						if(e.keyCode==13)
//							userLogin();
//					});
                    if(GetCookie("remember_password")=='true'){
                        $("#username").val(GetCookie("username"));
                        $("#password").val(GetCookie("password"));
                        if ($("#remember_password").length != 0) {
                            $("#remember_password").get(0).checked = GetCookie("remember_password");
                        }
                    }else{
                        if ($("#remember_password").length != 0) {
                            $("#remember_password").removeAttr("checked");
                            $("#username").val("");
                            $("#password").val("");
                            $("#userPCode").val("");
                        }
                    }
                }
            }
        });
    }
});

function refreshVcode(imgId,c) {
    document.getElementById(imgId).src = null;
    document.getElementById(imgId).src = "${ctx }/view/common/validation.jsp?c=" + c + "&t=" + new Date().getTime();
}

//验证码以毫秒+四位随机数作为唯一凭证
function getRan() {
    // 若框架页内session失效，则整个页面重新加载
    if(window != top){
        top.location.reload(true);
        return;
    }

    var _t = new Date().getMilliseconds();
    var _ran = _t + '' + Math.round(Math.random()*10) + '' + Math.round(Math.random()*10) + '' + Math.round(Math.random()*10) + '' + Math.round(Math.random()*10);
    // var _u = baseAction + "/view/common/validation.jsp?c=lg_" + _ran;
    $.get(actionBase+'/hot/events/getRan',{},function(result){
        var _u='/hot/events/getRan?c=lg_'+ _ran;
        $('#imgVcode').attr('src', _u);
        $('#imgVcodeTwo').attr('src', _u);
        $('#_ran').val(_ran);
    });

}

//获得Cookie解码后的值
function GetCookieVal(offset){
    var endstr = document.cookie.indexOf (";", offset);
    if (endstr == -1)
        endstr = document.cookie.length;
    return unescape(document.cookie.substring(offset, endstr));
}
function SetCookie(name, value) {
    var today = new Date();
    var expires = new Date();
    expires.setTime(today.getTime() + 1000*60*60*24*365);
    document.cookie = name + "=" + escape(value)    + "; expires=" + expires.toGMTString();
}
//删除Cookie
function DelCookie(name){
    var exp = new Date();
    exp.setTime (exp.getTime() - 1);
    var cval = GetCookie (name);
    document.cookie = name + "=" + cval + "; expires="+ exp.toGMTString();
}
//获得Cookie的原始值
function GetCookie(name){
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg)
            return GetCookieVal (j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0)
            break;
    }
    return null;
}

//QC.Login({
//	//btnId：插入按钮的节点id，必选
//    btnId:"qq_login_btn",
//    //用户需要确认的scope授权项，可选，默认all
//    scope:"all",
//    //按钮尺寸，可用值[A_XL| A_L| A_M| A_S|  B_M| B_S| C_S]，可选，默认B_S
//    size: "B_M"
//},
//function(reqData, opts) {
//	if(QC.Login.check()){//如果已登录
//		QC.Login.getMe(function(openId, accessToken){
//			$.ajax({
//				url:baseAction + '/view/user/checkThirdPartyExists.action',
//				method:'POST',
//				data:{
//					'user.thirdpartyType':1,
//					'user.thirdpartyAccount':openId
//				},
//				success:function(data) {
//					if (data == 1) { // 用户存在
//						location.href = baseAction + '/home.shtml';
//					} else { // 用户不存在
//						$('#thirdpartyType').val(1);
//						$('#thirdpartyAccount').val(openId);
//						$('#nickname').val(QC.String.escHTML(reqData.nickname));
//						document.bindForm.submit();
//					}
//					QC.Login.signOut();
//				}
//			});
////			console.log(["当前登录用户的", "openId为："+openId, "accessToken为："+accessToken].join("\n"));
//		});
//	}
//
//	//根据返回数据，更换按钮显示状态方法
////    var dom = document.getElementById(opts['btnId']),
////    _logoutTemplate=[
////         //头像
////         '<span><img src="{figureurl}" class="{size_key}"/></span>',
////         //昵称
////         '<span>{nickname}</span>',
////         //退出
////         '<span><a href="javascript:QC.Login.signOut();">退出</a></span>'
////    ].join("");
////    dom && (dom.innerHTML = QC.String.format(_logoutTemplate, {
////        nickname : QC.String.escHTML(reqData.nickname), //做xss过滤
////        figureurl : reqData.figureurl
////    }));
//});