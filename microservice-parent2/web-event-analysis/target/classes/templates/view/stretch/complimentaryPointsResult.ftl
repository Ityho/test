<#include "../../top.ftl" >
<style>
    @media screen and (min-width: 320px){
        .formtext>div{height: 50px!important;line-height: 50px!important;}
        .formtext div input{height: 30px!important;line-height: 30px!important;}
        .formtext div .yzmBtn1{height: 23.8px!important;line-height: 23.8px!important;}
        .formtext div .yzblq{height: 35px!important;line-height: 35px!important;}
    }
    @media screen and (min-width: 375px){
        .formtext>div{height: 60px!important;line-height: 60px!important;}
        .formtext div input{height: 35px!important;line-height: 35px!important;}
        .formtext div .yzmBtn1{height: 28.8px!important;line-height: 28.8px!important;}
        .formtext div .yzblq{height: 40px!important;line-height: 40px!important;}
    }
    @media screen and (min-width: 414px){
        .formtext>div{height: 65px!important;line-height: 65px!important;}
        .formtext div input{height: 40px!important;line-height: 40px!important;}
        .formtext div .yzmBtn1{height:33.8px!important;line-height: 33.8px!important;}
        .formtext div .yzblq{height: 45px!important;line-height: 45px!important;}
    }
</style>
</head>
<body>
<div id="container" style="background: #FCA800;">
    <section style="">
        <img src="${staticResourcePathH5%}/images/stretch/head2.png?v=${SYSTEM_INIT_TIME}" style="width: 100%;">
    </section>
    <section style="background: #FFC935;margin: 0px 10px;padding: 10px;position: relative;">
        <img src="${staticResourcePathH5%}/images/stretch/count.png?v=${SYSTEM_INIT_TIME}" width="100%">
        <div style="position: absolute; overflow: hidden;top:0px;width: 90%;margin: 40px;">
            <article style=" float: left;width: 60%;">
                <div style="font-size: 14px;line-height: 30px;">微热点积分</div>
                <div style="color: red;line-height: 30px;"><span style="font-weight: 600;font-size: 20px;">${stretchCreditAmount}</span>&nbsp;微积分</div>
            </article>
            <article style="float: right;width: 40%;margin-top: -5px;">
                <img src="${staticResourcePathH5%}/images/stretch/logo.png?v=${SYSTEM_INIT_TIME}" width="60%">
            </article>
        </div>
        <div style="font-size: 12px;text-align: center;margin-bottom: 10px;">微积分已放入账户<span>${admin.mobile }</span></div>
        <div style="height: 50px;line-height: 50px;text-align: center;">
            <a href="javascript:openApp()" class="yzblq" style="display: inline-block;width: 80%;height: 35px;background: #07A9FB;text-align: center;line-height: 35px;border-radius: 5px;color: #fff;margin: 0px auto;">下载APP</a>
        </div>
        <div style="height: 50px;line-height: 50px;text-align: center;">
            <a href="javascript:goHome()" class="yzblq" style="display: inline-block;width: 80%;height: 35px;background: #FCA001;text-align: center;line-height: 35px;border-radius: 5px;color: #fff;margin: 0px auto;">立即体验</a>
        </div>
        <div style="margin-top: 10px;">
            <img src="${staticResourcePathH5%}/images/stretch/hdgz.png" width="100%">
        </div>
    </section>
</div>
<script>
    function openApp(){
        saveOperateLog('首页下载app','1052');
        if("${userPlatform}" == "2"){
            location.href = 'http://d.51wyq.cn';
        }else{
            location.href = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq';
        }

    }
    function goHome(){
        if(bs.versions.mobile){
            var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
            if (ua.match(/WeiBo/i) == "weibo") {
                location.href = "http://apps.weibo.com/3960037780/8rXM111J";
            }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
                location.href = njxBasePath + "/home.shtml";
            }else{
                location.href = njxBasePath + "/home.shtml";
            }
        }
    }
</script>
</body>
</html>