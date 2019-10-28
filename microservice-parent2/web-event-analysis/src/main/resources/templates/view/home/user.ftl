<#include "../../init_top.ftl" >
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
        <link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css" />
        <script src="${staticResourcePathH5}/js/newHome/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>
		<title>微热点——用大数据洞察传播</title>
	</head>
	<body class="mainBackground">
    <div class="rel margin-bottom-50">
        <!--用户头像-->
        <section class="wui-user-header">
            <div class="wui-user-logo">
                <div class="wui-user-logo-img" onclick="javascript:logout()">
                    <#if admin.userHead != "">
                        <img  src="${admin.userHead}"   alt="">
                    <#else>
                        <img  src="${staticResourcePathH5}/images/newHome/default.jpg" alt="">
                    </#if>
                </div>

                <#if admin.nickname != "">
                    <h3>${admin.nickname}</h3>
                <#elseif admin.mobile != "" >
                    <h3>${admin.mobile}</h3>
                <#else>
                    <h3>${admin.username}</h3>
                </#if>
                <p >微积分：${admin.creditAmount}</p>
            </div>

            <#if goSign == 0 >
                <a class="wui-sign-in"  >已签到 </a>
            <#else>
                <a class="wui-sign-in" onclick="javascript:goSign();" >每日签到 <i class="iconfont icon-qiandao margin-left-3 font-size-10"></i></a>
            </#if>

        </section>
        <!--        目录列表-->
        <ul class="wui-table-view margin-top-20">
            <li class="wui-table-view-cell">
                <a href="${baseUrl}/userCenter/goBuy?type=0">
                    <div class="wui-table-view-icon">
                        <img src="${staticResourcePathH5}/images/newHome/view_1.svg" alt="">
                    </div>
                    微积分充值
                    <span class="wui-table-view-act">首充享优惠</span>
                </a>
            </li>
            <li class="wui-table-view-cell">
                <a href="${baseUrl}/userCenter/goBuy?type=1">
                    <div class="wui-table-view-icon">
                        <img src="${staticResourcePathH5}/images/newHome/view_2.svg" alt="">
                    </div>
                    产品选购
                </a>
            </li>
        <#--            <li class="wui-table-view-cell">-->
        <#--                <a href="${baseUrl}/userCenter/renewProductPackage">-->
        <#--                    <div class="wui-table-view-icon">-->
        <#--                        <img src="${staticResourcePathH5}/images/newHome/view_3.svg" alt="">-->
        <#--                    </div>-->
        <#--                    续费管理-->
        <#--                </a>-->
        <#--            </li>-->
            <li class="wui-table-view-cell">
                <a href="${baseUrl}/userCenter/orderList">
                    <div class="wui-table-view-icon">
                        <img src="${staticResourcePathH5}/images/newHome/view_4.svg" alt="">
                    </div>
                    我的订单
                </a>
            </li>
            <li class="wui-table-view-cell">
                <a href="${baseUrl}/infoDataReport">
                    <div class="wui-table-view-icon">
                        <img src="${staticResourcePathH5}/images/newHome/view_5.svg" alt="">
                    </div>
                    大数据深度报告
                </a>
            </li>
        </ul>
        <ul class="wui-table-view margin-top-20">
            <li class="wui-table-view-cell">
                <a href="${baseUrl}/userCenter/doUserSharecode">
                    <div class="wui-table-view-icon iconfont icon-fenxiang fontColor_1"></div>
                    分享计划
                </a>
            </li>
            <li class="wui-table-view-cell">
                <a href="javascript:;message()">
                    <div class="wui-table-view-icon iconfont icon-kefu fontColor_1"></div>
                    联系客服
                </a>
            </li>
        </ul>
        <section>

        </section>
        <!-- 列表-->
    </div>
    <!--底部导航-->
    <div class="wui-tabbar wui-tabbar-fixed">
        <a class="wui-tabbar-item" href="${njxBasePath}/home">
            <div class="wui-tabbar-item_icon">
            </div>
            <div class="wui-tabbar-item_text">首页</div>
        </a>
        <a class="wui-tabbar-item" href="${njxBasePath}/infoData">
            <div class="wui-tabbar-item_icon wui-icon-data">
            </div>
            <div class="wui-tabbar-item_text">大数据报告</div>
        </a>
        <a class="wui-tabbar-item" href="${njxBasePath}/analysis">
            <div class="wui-tabbar-item_icon wui-icon-analysis">
            </div>
            <div class="wui-tabbar-item_text">热点分析</div>
        </a>
        <a class="wui-tabbar-item" href="${njxBasePath}/uMedia">
            <div class="wui-tabbar-item_icon wui-icon-um">
            </div>
            <div class="wui-tabbar-item_text">新媒体运营</div>
        </a>
        <a class="wui-tabbar-item wui-tabbar-item-active" href="${njxBasePath}/user">
            <div class="wui-tabbar-item_icon wui-icon-my">
            </div>
            <div class="wui-tabbar-item_text">我</div>
        </a>
    </div>
    <script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
    <script>
        function message(){
            swal("<img id = 'imgid' src = '${staticResourcePathH5 }/images/lxkf.png' width='100%'  height='100%' usemap='#planetmap'><map name='planetmap' id='planetmap'><area shape='rect' coords='0,166,350,250' href ='tel:400-600-7505'/></map>");
            $(".showSweetAlert").css("marginTop","-200px");
            var height = $("#imgid").height(),
                    width = $("#imgid").width();
            console.log($("#imgid").height());
            console.log($("#imgid").width());
            $("area").prop("coords","0,"+parseInt(height*0.66)+","+width+","+height);
        }

        function  logout() {
            // $.ajax({
            //     url :njxBasePath + '/user/logout',
            //     type : 'POST',
            //     success : function(result){
            //         window.location.href ="/home"
            //     }
            // });
        }
        function goSign(){
            $.ajax({
                url :njxBasePath + '/user/toSign',
                type : 'POST',
                success : function(result){
                    if(result.status == 1){
                        $("#signNum").text("+"+result.obj);
                        $("#myWd").text(($("#myWd").html() + result.obj));
                        $("#myNowWd").text(($("#myNowWd").html() + result.obj));
                        $('.modal1').show();
                        $('.popver-mask').show();
                        window.location.href ="/user"
                    }else if(result.status == 2){
                        swal(result.msg);
                    }else {
                        swal("访问出错，请稍后尝试！");
                    }
                    refreshUser()
                }
            });
        }
    </script>
    </body>
</html>
