<#include "../../top.ftl">
<link href="${staticResourcePathH5}/js/swa/bootstrap/css/bootstrap.min.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link href="${staticResourcePathH5}/css/swa/style.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link href="${staticResourcePathH5}/css/swa/openWeiboImg.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link href="${staticResourcePathH5}/css/swa/message.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/style.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/font-icon.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/layoutpay.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath}/css/productBuy/single.css?v=${SYSTEMINITTIME}" />
<script src="${njxBasePath}/js/productBuy/jquery.min.js"></script>
<script src="${njxBasePath}/js/productBuy/wyrem.js"></script>
<script src="${njxBasePath}/js/productBuy/icheck.js"></script>
<script src="${njxBasePath}/js/productBuy/iscroll.js"></script>
<script>
    var njxBasePath= "${njxBasePath}";
</script>
<script src="${njxBasePath}/js/navigate.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${njxBasePath}/js/common-order.js?v=${SYSTEMINITTIME}"></script>
<style>
    .modal{
        display: none;
        overflow: hidden;
        position: fixed;
        top: 0px;
        right: 0px;
        bottom: 0px;
        left: 0px;
        z-index: 1050;
        outline: 0px;
        margin-left: 0px;
        width: 100% !important;
        border-top: 0
    }

    .ybdChart > ul > li {
        font-size: 12px !important;
    }
    .ybdChart > ul > li a{
        font-size: 12px !important;
    }

    .ani_dot {
        font-family: simsun;
    }
    :root .ani_dot {
        display: inline-block;
        width: 1.5em;
        vertical-align: bottom;
        overflow: hidden;
    }
    @-webkit-keyframes dot {
        0% { width: 0; margin-right: 1.5em; }
        33% { width: .5em; margin-right: 1em; }
        66% { width: 1em; margin-right: .5em; }
        100% { width: 1.5em; margin-right: 0;}
    }
    .ani_dot {
        -webkit-animation: dot 3s infinite step-start;
    }

    @keyframes dot {
        0% { width: 0; margin-right: 1.5em; }
        33% { width: .5em; margin-right: 1em; }
        66% { width: 1em; margin-right: .5em; }
        100% { width: 1.5em; margin-right: 0;}
    }
    .ani_dot {
        animation: dot 3s infinite step-start;
    }

    .sk-wave {
        margin: 10px auto;
        margin-bottom: 0px;
        width: 50px;
        height: 40px;
        text-align: center;
        font-size: 10px; }
    .sk-wave .sk-rect {
        background-color: #fff;
        height: 100%;
        width: 4px;
        display: inline-block;
        -webkit-animation: sk-waveStretchDelay 1.2s infinite ease-in-out;
        animation: sk-waveStretchDelay 1.2s infinite ease-in-out;
        margin-left: 6px; }
    .sk-wave .sk-rect1 {
        -webkit-animation-delay: -1.2s;
        animation-delay: -1.2s; }
    .sk-wave .sk-rect2 {
        -webkit-animation-delay: -1.1s;
        animation-delay: -1.1s; }
    .sk-wave .sk-rect3 {
        -webkit-animation-delay: -1s;
        animation-delay: -1s; }
    .sk-wave .sk-rect4 {
        -webkit-animation-delay: -0.9s;
        animation-delay: -0.9s; }

    @-webkit-keyframes sk-waveStretchDelay {
        0%, 40%, 100% {
            -webkit-transform: scaleY(0.4);
            transform: scaleY(0.4); }
        20% {
            -webkit-transform: scaleY(1);
            transform: scaleY(1); } }

    @keyframes sk-waveStretchDelay {
        0%, 40%, 100% {
            -webkit-transform: scaleY(0.4);
            transform: scaleY(0.4); }
        20% {
            -webkit-transform: scaleY(1);
            transform: scaleY(1); } }
</style>
<script type="text/javascript">saveOperateLog('微分析','1055');</script>
<script type="text/javascript">
    var staticResourcePath='${staticResourcePath}';
    var actionBase="${njxBasePath}";
    $(function() {
        $('.icheckbox-list input').on('ifChecked', function(event) {
            var value = $(this).val();
            if(value=="03"){
                $("#payType").val("03");
                $("#useCredit1").val("false");
                $("#goProductCartBtn").attr("onclick","buyProductpro()");
            }
            if(value=="02"){
                $("#payType").val("02");
                $("#useCredit1").val("false");
                $("#goProductCartBtn").attr("onclick","buyProductpro()");
            }
            if(value=="00"){
                $("#payType").val("00");
                $("#useCredit1").val("true");
                $("#goProductCartBtn").attr("onclick","showTips()");
            }
            commonBuyProductFee();
        }).iCheck({
            checkboxClass: 'icheckbox_square-red',
            radioClass: 'iradio_square-red',
            increaseArea: '20%'
        });

        $('#currentTaskTier').val(0);
        $('#shareURL').val('http://' + window.location.host);

        // 是否已支付
        checkSWAPayment();

        // 微博分析
        $('#fxbtn').click(function() {
            var url = $.trim($('#weiboURL').val());
            if (url != null && url != '' && (url.indexOf('weibo.com') != -1 || url.indexOf('m.weibo.cn') != -1)) {
                // 检查是否已分析过
                $.ajax({
                    url : actionBase + '/weiboAnalysis/isExists',
                    type : 'POST',
                    data : {
                        'weiboURL' : url
                    },
                    success : function(result) {
                        if (!$.isEmptyObject(result)) {
                            $('#weiboId').val(result.weiboId);
                            $('#reAnalysisTime').text(new Date(result.createTime).format('yyyy年MM月dd日 hh:mm'));
                            $('#reAnalysis').modal('show');
                            console.log("分析过");
                        } else {
                            console.log("没有分析过");
                            goAnalysisWeibo(null,null, url, false);
                        }
                    }
                });
            } else {
                alertMsg('微博链接不正确，请重新输入！');
                $('#weiboURL').select();
            }
        });

        // 重新分析
        $('#reAnalysisYes').click(function() {
            goAnalysisWeibo(null,null, $.trim($('#weiboURL').val()), true);
        });

        // 不重新分析
        $('#reAnalysisCancel').click(function() {
            goAnalysisWeibo(null,$('#weiboId').val(), $.trim($('#weiboURL').val()), false);
        });

        // 支付
        $('#weiboConfirmOrderBtn').click(function() {
            if ($('#finalCost').val() > 0) {
                if ($('#orderRecordId').val() != null && $('#orderRecordId').val() != '' && $('#orderRecordId').val() > 0) {
                    var params = {'order.orderRecordId' : $('#orderRecordId').val()};
                    createForm(actionBase + '/pay/gorder.action', 'POST', '_self', params);
                } else {
                    var params = {'myProducts[\'product\'].keywordId' : $('#packageId').val(), 'myProducts[\'product\'].keywordPackageNum' : $('#repostsCount').val(), 'fenxiWeiboId' : $('#weiboId').val()};
                    console.log(params);
                    createForm(actionBase + '/pay/confirmOrderV3', 'POST', '_self', params);
                }
            }
        });
        // 消息弹窗
        function alertMsg(content) {
            $('#msgContent').text(content);
            $(".zhezhao").addClass('downShow');
            $("#msgPOP").css("display","block");
            //$("body").css({overflow:"hidden"});    //禁用滚动条
            $(".prompPOP").addClass('scaleShow');
            $(".prompPOP").removeClass('scaleOut');
        }
    });

    // 删除微博分析
    function delWeibo() {
        if ($('#delId').val() > 0) {
            $.ajax({
                url : actionBase + 'weiboAnalysis/delWeibo',
                type : 'POST',
                data : {
                    'markType':$('#markType').val(),
                    'weiboId' : $('#delId').val()
                },
                success : function(result) {
                    if (result)
                        location.href = actionBase + '/weiboAnalysis/weiboAnalysisIndex';
                }
            });
        }
    }
</script>
<body>
<!--head代码 start-->
<script>
    var actionBase="${njxBasePath}";
</script>
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/common.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/sigma/sigma.min.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/sigma/sigma.parsers.gexf.min.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/highcharts/highcharts.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/highcharts/highcharts-more.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/echarts/echarts.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/bar.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/pie.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/map.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/echarts/chart/wordCloud.js?v=${SYSTEM_INIT_TIME}" charset="UTF-8"></script>
<script type="text/javascript" src="${njxBasePath}/js/swa/singleWeiboAnalysis.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/util.js?v=${SYSTEM_INIT_TIME}"></script>
<!--head代码 end-->
<input type="hidden" id="_weiboUrl" value='${(fenxiWeibo.weiboUrl)!""}' />
<input type="hidden" id="markType" value='${(fenxiWeibo.markType)!0}' />
<input type="hidden" id="createTime" value='${(fenxiWeibo.createTime?string('yyyyMMdd'))!""}' />
<input type="hidden" id="weiboId" value='${(fenxiWeibo.taskId)!0}' />
<input type="hidden" id="shareCode" value='${(fenxiWeibo.weiboShareCode)!""}' />
<input type="hidden" id="taskTicket" value='${(fenxiWeibo.taskTicket)!""}' />
<input type="hidden" id="currentTaskTier" value="0" />
<input type="hidden" id="delId" name="id" />
<input type="hidden" id="needPay" value='${needPay?string("true","false")}' />
<input type="hidden" id="repostsCount" value='${repostsCount!0}' />
<input type="hidden" id="firstBuy" value='${firstBuy!""}' />
<input type="hidden" id="price" value='${price!0}' />
<input type="hidden" id="cost" value='${cost!0}' />
<input type="hidden" id="discount_nofirstbuy" value='${discount_nofirstbuy!""}' />
<input type="hidden" id="no_first_v_normal" value='${no_first_v_normal!""}' />
<input type="hidden" id="shareURL" />
<input type="hidden" id="pageType" value="web" />
<input type="hidden" id="userId" value='${(admin.userId)!0}' />
<input type="hidden" id="uid" value='${uid!0}' />
<input type="hidden" id="finalCost" />
<input type="hidden" id="orderRecordId" value='${orderRecordId!0}' />
<input type="hidden" id="packageId" value='${packageId!0}' />
<section class="section" style="border-left:solid 6px #fd8c25;">
    <h2 class="float_l" style="font-size:1.2em;">微博传播效果分析</h2>
    <div style="margin-top: 10px;margin-right:15px;" class="float_r">
        <a href="${njxBasePath}/analysis">返回</a></div>
</section>
<div class="page-container2">
    <div class="content mt15">
        <div class="wrapper980">
            <div class="wrapCon">
                <!--APP头部导航 end  -->
                <div class="row-fluid">
                    <div class="mwbcon">
                        <div class="analyze float_l" style="width: 100%;">
                            <a href="javascript:;" class="fxbtn" id="fxbtn">确定</a>
                            <input type="text" class="inputBox" name="weiboURL" id="weiboURL" oninput="dis();" onfocus="dis();" placeholder="请粘贴微博链接进行分析" />
                            <div id="empty" style="display: none;" class="empty abs" onClick="" title="清空全部">×</div>
                            <br /><br />
						<#if uid ??>
                            <div style="float:right;font-size:14px;margin-top:-20px;color:#dfdfdf">您有${(admin.userSingleWeiboAnalysisValidCount)!0}条剩余转发条数哦！<br /></div>
						<#else>
                            <div style="font-size:14px;margin-top:-15px;color:#dfdfdf">您有${(admin.userSingleWeiboAnalysisValidCount)!0}条剩余转发条数哦！<br /></div>
						</#if>

                        </div>
                    </div>
                </div>

                <div class="row-fluid" style="min-height: 300px;">
                    <div class="loadingMask">
                        <span class="waiting">Loading<span class="ani_dot">...</span></span>
                    </div>
                    <div class="loadingData" style="display: none;">
                        <p>正在分析微博<span class="ani_dot">...</span></p>
                        <div class="progress2">
                            <span class="progressLine"></span>
                        </div>
                    </div>
                    <div class="mwbBorder border">
                        <div class="mwbcon">
                            <div class="m_l">
                                <img id="weiboContentUserHead" style="cursor: pointer;" />
                            </div>
                            <div class="m_r">
                                <p id="weiboContentUserNickname"></p>
                                <div class="mwbcontext" id="weiboContentPic">
                                    <!--图片展示 start -->
                                    <!--图片展示 end -->
                                </div>
                                <div class="mfont-buttom" id="weiboContentButtomTime" style="display: none;">
                                    <div class="mfont-buttom_l" id="weiboContentTime">
                                        <span>分析时间：${(fenxiWeibo.createTime?string("yyyy-MM-dd HH:mm:ss"))!""}</span>
                                    </div>
                                    <div class="mfont-buttom_r">
                                        <span class="weibo-multi-panel weibo-list-a" id="weiboContentForwardNumber"></span> |
                                        <span class="weibo-multi-panel weibo-list-a" id="weiboContentCommentNumber"></span> |
                                        <span class="weibo-multi-panel weibo-list-a" id="weiboContentPraiseNumber"></span> |

									<#if demo >
                                        <a href="#" style="display: none;" class="link" data-toggle="modal" data-target="#checkDelete" onclick="javascript:$('#delId').val(${(fenxiWeibo.weiboId)!0 });">删除分析</a>
									</#if>
                                    </div>
                                </div>

                            </div>
                            <div class="clear explain" id="weiboContentNote" style="display: none; bottom: 10px;">（系统分析的数据包括微博的所有层级的转发及评论数据）</div>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>传播路径</h2>
                        <div class="tit titlBar" style="margin-left:100px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                完整展示微博的传播路径，灰色节点为根节点，通过节点图可找到微博关键传播用户。
                            </div>
                        </div>
                        <div class="chartMask"></div>
                        <div class="mwbcon wrapChart" id="weibo_task_result_star_content_div" style="width: 98%; height: 450px; text-align: left; padding: 0px;">
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>转发层级</h2>
                        <div class="tit titlBar" style="margin-left:100px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                层级是指在这条微博在传播中的传递次序，层级越多，通常说明该微博传播的范围越广；通过转发层级图可以看出该条微博转发的所有层级数，以及对每一层级转发者的排名和个人详情进行分析、总结。
                            </div>
                        </div>
                        <div class="mwbcon2">
                            <ul class="table_title">
                                <li>层级 <font>/</font> <span id="tierSpan"></span>
                                </li>
                                <li>有效转发 <font>/</font> <span id="tierRepostsSpan"></span>
                                </li>
                                <li>转发<span>占比</span>
                                </li>
                                <li>覆盖人数 <font>/</font> <span id="tierCoverUserSpan"></span>
                                </li>
                                <li>核心转发 <span>TOP15</span>
                                </li>
                            </ul>
                            <ul class="table_list table_list_h5">
                            </ul>
                            <div class="more">
                                <img src="${staticResourcePathH5}/images/swa/arrow-down.png" title="更多" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid clear">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>
                            <div class="float_l">转发评论趋势图</div>
                            <div class="float_r">
                                <span><i class="c_3" style="background-color: #ff6633;"></i>转发</span>
                                <span><i class="c_4" style="background-color: #3399ff;"></i>评论</span>
                                <span><i class="c_4" style="background-color: #f29b22;"></i>意见领袖</span>
                            </div>

                        </h2>
                        <div class="mwbcon" id="weibo_task_result_line_content_div" style="width: 98%; height: 350px;">
                        </div>
                        <p class="text" style="margin-left:10px;">(上图泡泡表示在该时间点转发微博的意见领袖，点击可查看详情)</p>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid mwb5 line_r float_l">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>微传播关键用户</h2>
                        <div class="tit titlBar" style="margin-left:120px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                转发该条微博中粉丝量最多的意见领袖。
                            </div>
                        </div>
                        <div class="mwbcon2 primaryUser">
                            <div class="pr_l">
                                <p>
                                <div class="tx">
                                    <img id="keyUserHead" style="cursor: pointer;" />
                                </div>
                                <div class="wbname">
                                    <p>
                                        <a href="javascript:;" id="keyUserNickname"></a>
                                    </p>
                                    <p>
                                        粉丝数：<em id="keyUserFollowsCount"></em>
                                    </p>
                                </div>
                                </p>

                                <dl class="zf_stat">
                                    <dd>
                                        <p>转发时间</p>
                                        <p id="keyUserRepostsTime"></p>
                                    </dd>
                                    <dd>
                                        <p>转发数</p>
                                        <p id="keyUserRepostsCount"></p>
                                    </dd>
                                </dl>
                            </div>
                            <div class="pr_r">
                                <p id="keyUserRepostsContent">
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row-fluid mwb5 float_r">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>关键用户传播路径</h2>
                        <div class="tit titlBar" style="margin-left:150px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                转发该条微博中粉丝量最多的意见领袖。
                            </div>
                        </div>
                        <div class="mwbcon2 cbPath">
                            <ul id="keyUserRoad">
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid clear">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>引爆点</h2>
                        <div class="tit titlBar" style="margin-left:100px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                引起该条微博二次转发量最多的前10位博主，通过引爆点可以看到转发者的信息和转发时间。
                            </div>
                        </div>
                        <div class="mwbcon2">
                            <div class="ybdChart">
                                <ul id="keyUserTop">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid clear">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>
                            <div class="float_l">转发评论表情分析</div>
                            <div class="float_r">
                                <span><i class="c_3" style="background-color: #607cbe;"></i>转发</span>
                                <span><i class="c_4" style="background-color: #e6b053;"></i>评论</span>
                            </div>
                        </h2>
                        <div class="mwbcon2" style="width:100%;">
                            <div style="padding:10px;" id="resultFace">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <!--影响力 TOP10 转发 start-->
                <div class="row-fluid clear">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>影响力排名</h2>
                        <div class="tit titlBar" style="margin-left:100px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                新浪认证的用户中，最具影响力、覆盖力度最大的分类型博主排名。
                            </div>
                        </div>
                        <div class="mwbcon2" id="verifyUser">
                        </div>
                    </div>
                </div>
                <!--影响力 TOP10 转发 end-->

                <div class="line12"></div>
                <div class="row-fluid mwb5 line_r float_l">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>转发者地域分析</h2>
                        <div class="tit titlBar" style="margin-left:120px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                根据对该事件进行转发的所有博主在操作时的IP地址所进行的地域统计。
                            </div>
                        </div>
                        <div class="mwbcon" id="weibo_task_result_location_reposts_div" style="width: 420px;height:230px;">
                        </div>
                        <table style="position: absolute; bottom: 1px; left: 1px; background-color: #269EE8; font-size: 12px; color: #fff; text-align: center; width: 110px; font-family: 'Microsoft Yahei'; z-index: 1;">
                            <thead>
                            <tr><th colspan="2" style="text-align: center;">地域TOP5</th></tr>
                            </thead>
                            <tbody id="repostsPH">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row-fluid mwb5 float_r">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>评论者地域分析</h2>
                        <div class="tit titlBar" style="margin-left:120px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                根据对该事件进行评论的所有博主在操作时的IP地址所进行的地域统计。
                            </div>
                        </div>
                        <div class="mwbcon" id="weibo_task_result_location_comments_div" style="width: 420px;height:230px;">
                        </div>
                        <table style="position: absolute; bottom: 1px; left: 1px; background-color: #269EE8; font-size: 12px; color: #fff; text-align: center; width: 110px; font-family: 'Microsoft Yahei'; z-index: 1;">
                            <thead>
                            <tr><th colspan="2" style="text-align: center;">地域TOP5</th></tr>
                            </thead>
                            <tbody id="commontsPH">
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid mwb5 line_r float_l">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>转发者性别分析</h2>
                        <div class="tit titlBar" style="margin-left:120px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                参与该事件微博传播的男女占比。
                            </div>
                        </div>
                        <div class="mwbcon2 wrapChart wrapChart2 rel" id="weibo_task_result_gender_reposts_div" style="width: 420px;height:230px;">
                        </div>
                        <div class="genderBox">
                            <div class="gender male" style="margin-left:-50px;">
                                <i></i> <strong id="reposts_male-fans-scale">0.0%</strong>男性粉丝
                            </div>
                            <div class="gender female">
                                <i></i> <strong id="reposts_female-fans-scale">0.0%</strong>女性粉丝
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row-fluid mwb5 float_r">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>评论者性别分析</h2>
                        <div class="tit titlBar" style="margin-left:120px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                评论该事件微博的男女占比。
                            </div>
                        </div>
                        <div class="mwbcon2 wrapChart wrapChart2 rel" id="weibo_task_result_gender_comments_div" style="width: 420px;height:230px;">
                        </div>
                        <div class="genderBox">
                            <div class="gender male" style="margin-left:-50px;">
                                <i></i> <strong id="comments_male-fans-scale">0.0%</strong>男性粉丝
                            </div>
                            <div class="gender female">
                                <i></i> <strong id="comments_female-fans-scale">0.0%</strong>女性粉丝
                            </div>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid mwb5 line_r float_l">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>转发者兴趣标签</h2>
                        <div class="tit titlBar" style="margin-left:120px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                在该条微博传播的过程中，所有转发者兴趣标签的词汇，词频由高到低展示。
                            </div>
                        </div>
                        <div class="mwbcon2" id="weibo_task_result_user_tag_reposts_div" style="margin-left:-30px;width: 420px;height:230px;">
                        </div>
                    </div>
                </div>
                <div class="row-fluid mwb5 float_r">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>评论者兴趣标签</h2>
                        <div class="tit titlBar" style="margin-left:120px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                在该条微博传播的过程中，所有评论者兴趣标签的词汇，词频由高到低展示。
                            </div>
                        </div>
                        <div class="mwbcon2" id="weibo_task_result_user_tag_comments_div" style="margin-left:-30px;width: 420px;height:230px;">
                        </div>
                    </div>
                </div>

                <div class="line12"></div>
                <div class="row-fluid clear">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>
                            <div class="float_l">转评内容敏感度分析</div>
                        </h2>
                        <div class="tit titlBar" style="margin-left:100px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                基于中文语义分析的机器自学习内容情感研判模型，对转评内容进行敏感占比分析。
                            </div>
                        </div>
                        <div class="mwbcon">
                            <div class="senAn">
                                <div class="senAnCon">
                                    <div class="mgDiv">
                                        <span id="sensitivePercent">0%</span><br>敏感
                                    </div>
                                    <div class="senBar senAnprogress-bar">
                                        <div class="mgBar senAnprogress-bar" id="sensitiveBarWidth">&nbsp;</div>
                                    </div>
                                    <div class="fmgDiv">
                                        <span id="insensitivePercent">0%</span><br>非敏感
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid mwb5 float_l">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>
                            <div class="float_l">热词分析</div>
                            <div class="float_r"
                                 style="margin-right: 20px; margin-top: 14px;">
                                <a class="btn ord-btn btn-primary" href="javascript:;"
                                   id="switchChart">更换图表</a>
                            </div>
                        </h2>
                        <div class="tit titlBar" style="margin-left:100px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                利用自然语义分析法，对微博及传播微博所提及的关键词进行分词聚合，呈现出被提及频次最多的前20个关键词。
                            </div>
                        </div>
                        <div class="mwbcon" style="padding: 5px 20px;">
                            <div class="wordTable">
                                <table border="0" cellspacing="0" cellpadding="0"
                                       class="table table_border tableNoEven table_th_yellow">
                                    <thead>
                                    <tr>
                                        <th>排名</th>
                                        <th>热词</th>
                                        <th>提及量</th>
                                        <th>排名</th>
                                        <th>热词</th>
                                        <th>提及量</th>
                                    </tr>
                                    </thead>
                                    <tbody class="tdOneColor" id="hotWordsTBody">
                                    </tbody>
                                </table>
                            </div>
                            <div class="wordCloud" id="weibo_task_result_hot_words_div"
                                 style="display: none; margin-left:-50px;width: 430px; height: 370px;"></div>
                        </div>
                    </div>
                </div>
                <div class="row-fluid mwb5 float_l">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>
                            <div class="float_l">转发提及内容</div>
                            <div class="float_r">
                                <span class="small">（展示提及热词的转发内容TOP20）</span>
                            </div>
                        </h2>
                        <div class="mwbcon" style="padding: 5px 20px;">
                            <div style="height: 375px; width: 100%; overflow: auto;">
                                <table id="TableID" border="0" cellspacing="0" cellpadding="0"
                                       class="table table_border table_border_x table_th_blue">
                                    <thead>
                                    <tr>
                                        <th width="30%">微博昵称</th>
                                        <th width="50%">转发内容</th>
                                        <th width="20%">转发数</th>
                                    </tr>
                                    </thead>
                                    <tbody id="hotWordsDetailTBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="line12"></div>
                <div class="row-fluid clear">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>
                            <div class="float_l">网友观点分析</div>
                        </h2>
                        <div class="tit titlBar" style="margin-left:100px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                对该微博的评论进行聚类分析后展示。
                            </div>
                        </div>
                        <div class="mwbcon" id="weibo_task_result_view_point_div"
                             style="width: 93%; height: 440px;"></div>
                        <div class="viewpoint">
                            <div class="title">
                                <h1>
                                    <i class="icon-users"></i> 网友观点主要表现在如下几个方面：
                                </h1>
                            </div>
                            <ul id="weibo_task_result_view_point_ul"></ul>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid clear">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>
                            <div class="float_l">粉丝数量区间分布</div>
                            <div class="float_r">
                                <span><i class="c_3" style="background-color:#ff7f50;"></i>转发者</span>
                                <span><i class="c_4" style="background-color:#87cefa;"></i>评论者</span>
                            </div>
                        </h2>
                        <div class="mwbcon2" id="weibo_task_result_fans_div" style="width: 98%; height: 350px;">
                        </div>
                    </div>
                </div>

                <div class="line12"></div>
                <div class="row-fluid clear">
                    <div class="mwbBorder">
                        <h2>
                            <div class="float_l">发布设备分布</div>
                        </h2>
                        <div class="tit titlBar" style="margin-left:100px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                参与该微博传播的发布设备统计。
                            </div>
                        </div>
                        <div class="mwbcon chart_shuiyin">
                            <div class="float_r picBox-r"
                                 style="width: 630px; height: 250px;"
                                 id="weibo_task_result_source_r_div"></div>
                            <div class="biliBox biliBox2 biliBox3 "
                                 id="weibo_task_result_source_l_div" style="margin-top:20px;"></div>
                        </div>
                    </div>
                </div>
                <div class="line12"></div>
                <div class="row-fluid">
                    <div class="loadingMask"></div>
                    <div class="mwbBorder">
                        <h2>转发微博详情(TOP10)</h2>
                        <div class="tit titlBar" style="margin-left:160px;margin-top:-45px;">
                            <i class="iconfont icon-tishi" style="line-height:40px;color:#ff9b2f;font-size:16px;"></i>
                        </div>
                        <div class="tipinfo showing">
                            <div class="tiparro">
                                <i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i>
                                <i class="icon-remove close"></i>
                            </div>
                            <div class="tipcont">
                                在转发微博详情中可以看出所有博主中对该条微博转发量最大的前10位博主以及他（她）们的相关信息。
                            </div>
                        </div>
                        <ul class="mwblist2" id="forwardTop10">
                        </ul>
                    </div>
                </div>
                <div class="bottomC">
                    <a href="javascript:void(0)" class="more2">展开更多 <img src="${staticResourcePathH5}/images/swa/arrow-down2.png" /></a>
                </div>
            </div>
        </div>
    </div>
</div>

<!--底部部分代码 start-->
<!-- 遮罩层start -->
<div id="maskModal"  style="position: fixed;top: 45px;left: 0px;width: 100%;height: 100%;overflow:hidden;display:none;z-index:1;">
    <div style="width:100%;height:100%;background: rgba(0,0,0,0.5);filter: alpha(opacity=50); /* IE的透明度 */opacity: 0.5;  /* 透明度 */ z-index: 2; /* 此处的图层要大于页面 */">

    </div>
    <div id="maskContent" style="z-index:3;position:absolute;top:0px;left:0px;width:100%;height:100%;margin-top:20%;">

    </div>
</div>
<!-- 遮罩层end -->
<!--底部部分代码 end-->

<!--我的博文弹窗 start-->

<div class="modal fade" id="checkMyBoWen" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" style="position: absolute;left: 275px; width: 100%;">
    <div class="modal-dialog" style="margin-top: 100px;">
        <div class="modal-content" style="margin-top:-90px;">
            <div class="popover-title">
                <button type="button" class="close close-sm" data-dismiss="modal"></button>
                <h4 class="modal-title">
                    我的博文 <span class="small">(同步显示最新20条博文)</span>
                </h4>
            </div>

            <div class="form-list form-list2">
                <ul class="bwList" id="navDiv">
                    <img src="${staticResourcePathH5}/images/loading.gif" /> Loading<span class="ani_dot">...</span>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--我的博文弹窗 end-->

<!--微分析支付弹窗 start-->
<div class="modal fade" id="payPOP" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 50px; width: 360px;">
        <div class="modal-content">
            <div class="popover-title">
                <button type="button" class="close close-sm" data-dismiss="modal"></button>
                <h4 class="modal-title">微分析</h4>
            </div>
            <div class="modal-body">
                <div class="pay">
                    <div class="payHead">
                        <span class="f1_1">微博分析</span> <span>每条转发仅需${price!0}元</span>
                    </div>
                    <div class="price" id="divPay">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary float_r" id="weiboConfirmOrderBtn">去支付</button>
            </div>
        </div>
    </div>
</div>
<!--微分析支付弹窗 end-->
<#assign type=11>
<!--提示删除弹窗 start-->
<div class="modal fade" id="checkDelete" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 100px;">
        <div class="modal-content">
            <div class="popover-title">
                <button type="button" class="close close-sm" data-dismiss="modal"></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body align_c">
                <p>确定删除此条微博分析记录？</p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" onclick="delWeibo()">确定</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
            </div>
        </div>
    </div>
</div>
<!--提示删除弹窗 end-->


<!--提示是否重新分析弹窗 start-->
<div class="modal fade" id="reAnalysis" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 100px;">
        <div class="modal-content">
            <div class="popover-title">
                <button type="button" class="close close-sm" data-dismiss="modal"></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body align_c">
                <p>
                    您于<span id="reAnalysisTime"></span> 分析过该条微博，<br>是否确认重新分析？
                </p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="reAnalysisYes">确定</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true" id="reAnalysisCancel">取消</button>
            </div>
        </div>
    </div>
</div>
<!--提示是否重新分析弹窗 end-->
<!--消息弹出框 -->
<div id="msgPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
    <div class="prConBox">
        <div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

        <div class="PromptCon" id="msgContent" style="text-align: center;"></div>

        <div class="bottom"><a style=" float: initial" class="submitBtn " onclick="" style="text-align: center;">确定</a></div>
    </div>
</div>
<div class="zhezhao" style="display: none;"></div>

<div class="popver popver-single" id="singlediv">
<#list singleWeiboAnalysisProductList! as item>
    <div class="pop-top">
        <h3 class="pop-title"><span>微博传播效果分析</span></h3>
        <span class="price">0.01元/条</span>
        <p class="fz11">输入近期事件或话题关键词，微热点针对全网信息进行深度挖掘和多重分析；记录事件从始发到发酵期、发展期、高涨期、回落期和反馈期等阶段的演变过程，分析信息传播路径传播节点、发展态势和受众反馈等。</p>
    </div>
    <div class="pop-con">
        <section>
            <ul class="pay-choose clearfix">
                <li class="borderb" >
                    <span class="fc-gray6">购买数量</span>

                    <form  name="payForm" method="post" id = "selfProductForm">
                        <input id = "payType" type="hidden" name="payType" value="00"/>
                        <input id = "productPackageId"  type="hidden" name="myProductDto.keywordId" value="${(item.productPackageId)!0}" />
                        <input id = "productFlag"  type="hidden" name="myProductDto.productFlag" value="product1006" />
                        <input id = "useCredit1" type="hidden" name="useCredit" value="true"/>
                        <input id = "fenxiWeiboId" type="hidden" name="fenxiWeiboId" value=""/>
                        <input id = "wfx_createTime" type="hidden" name="wfx_createTime" value=""/>
                        <input id = "payTaskTicket" type="hidden" name="payTaskTicket" value='${(fenxiWeibo.taskTicket)!""}'/>
                        <input id = "orderType" type="hidden" name="orderType" value="1"/>
                        <div class="addsub">
                            <span class="fc-gray6" id="forwardedNum1"></span>
                            <input type="hidden" id = "product1006Num" name="myProducts['product${item.productPackageId}'].keywordPackageNum" value="0" />
                        </div>
                    </form>
                </li>
            </ul>

        </section>

        <div class="paymode" style="display: none;" id="paymode">
            <ul>
                <li class="border0">
                    <p class="fz15 fc-gray6"><i class="icon-pay pay-weijifen"></i>微积分支付</p>
                    <div class="icheckbox-list">
                        <input id="in_weijifen" type="radio" checked="" name="paybox" value="00">
                    </div>
                </li>
				<#if userPlatform == 2>
                    <li class="border0" id="pay-weibo">
                        <p class="fz15 fc-gray6"><i class="icon-pay pay-weibo"></i> 微博支付</p>
                        <div class="icheckbox-list">
                            <input type="radio"   name="paybox" value="03">
                        </div>
                    </li>
				</#if>
				<#if userPlatform == 3>
                    <li class="borderb" id="pay-weixin">
                        <p class="fz15 fc-gray6"><i class="icon-pay pay-weixin"></i> 微信支付</p>
                        <div class="icheckbox-list">
                            <input type="radio"   name="paybox" value="02"/>
                        </div>
                    </li>
				</#if>
            </ul>
        </div>
        <p class="fz17 fc-black taligncenter" style="margin: 25px 0;">应付金额：<span class="fc-p-red fz23" id="creditTotalFee1"></span></p>
        <a class="btn-pay" id="goProductCartBtn" onclick="showTips()">马上购买</a>
    </div>

</#list>
</div>
<!--微积分不足-->
<div class="fail-popver" id="fail">
    <div class="fail-tit">
        <img src="../../images/productBuy/fail.png" style="width: 50px;">
        <p class="mt10">微积分不够啦</p>
    </div>
    <div class="taligncenter fail-txt">
        <p>当前可用微积分为<span class="fc-p-red" id="nowwjf"></span></p>
        <p>还需要<span class="fc-p-red" id="needwjf"></span>才能购买</p>
    </div>
    <div class="btn-footer">
        <a href="javascript:;" class="pay-close">取消支付</a>
        <a href="${njxBasePath}/userCenter/goBuy?type=0" class="btn-recharge">去充值</a>
    </div>
</div>

<div class="fail-popver" style="height: 150px;" id="overdue">
    <div class="taligncenter fail-txt">
        <br>
        <p>该订单已过期，请重新分析</p>
    </div>
    <div class="btn-footer">
        <a href="javascript:;" class="pay-close">取消</a>
        <a  id="goReanalysis" class="btn-recharge" style="background:  #f18d00;color:  #FFFFFF;" onclick="goReanalysis()">重新分析</a>
    </div>
</div>
<!--确认支付-->
<div class="fail-popver" id="success">
    <div class="fail-tit">
        <p class="mt10" style="font-size: 18px;margin-top: 25px;">确认支付</p>
    </div>
    <div class="taligncenter fail-txt">
        <br>
        <p>当前可用微积分为<span class="fc-p-red" id="nowwjf2">${(admin.creditAmount)!0}</span></p>
        <p>本次需消耗<span class="fc-p-red" id="needwjf2"></span></p>
    </div>
    <div class="btn-footer">
        <a href="javascript:;" class="pay-close">取消支付</a>
        <a  id="goPay2" class="btn-recharge" style="background:  #f18d00;color:  #FFFFFF;" onclick="buyProductpro()">确认支付</a>
    </div>
</div>
<div class="popver-mask"></div>
<script type="text/javascript">
    /* 已过期，重新分析 */
    function goReanalysis(){
        $("#overdue").hide();
        $("#wfx_createTime").val("");
        goAnalysisWeibo(null,null, $.trim($('#_weiboUrl').val()), true);
    }

    /*打开弹框*/
    $('.btn-quan').on('click', function() {
        $('.popver1').show();
//				$('.popver-mask').show();
    });
    /*关闭*/
    $('.btn-close').on('click', function() {
        $(this).parent().hide();
//				$('.popver-mask').hide();
    })
    $('.pay-close').on('click', function() {
        $('.fail-popver').hide();
        $('.popver-mask').hide();
        $('#singlediv').hide();
        $('html').removeClass('noscroll');
    });
    $('.popver-mask').on('click', function() {
        $('.popver').hide();
        $('.about-popver').hide();
        $('.fail-popver').hide();
        $(this).hide();
        $('html').removeClass('noscroll');
    });
</script>

<script type="text/x-jquery-tmpl" id="attachment-thumb-tmpl">
<div class="preview-img" data-count="\${(items.length)!0}">
    <ul class="feed-img{{if items.length > 1}} multi-attachment{{/if}}{{if items.length == 4}} narrow-frame{{/if}}">
        {{each items}}
        <li>
            <img data-index="$" src="\${(tp)!''}" data-images='{"zoom_img": "\${($value.mp || $value.op)!""}", "origin_img":"\${(op)!""}"}' class="zoom-move thumb-zoom">
        </li>
        {{/each}}
    </ul>
</div>
</script>
<#--<script type="text/x-jquery-tmpl" id="attachment-show-tmpl">-->
<#--<div>-->
<#--    <div class="image-box{{if isMulti}} multi-image-box{{/if}}">-->
<#--        {{tmpl($data) '#image-box-tmpl'}}-->
<#--    </div>-->
<#--    {{if isMulti}}-->
<#--    <div class="thumb-slider-wrap">-->
<#--        <div class="visible-box"><ul></ul></div>-->
<#--        <a class="slider-dir slider-prev{{if count < 8}} disabled{{/if}}"><</a>-->
<#--        <a class="slider-dir slider-next{{if count < 8}} disabled{{/if}}">></a>-->
<#--    </div>-->
<#--    {{/if}}-->
<#--</div>-->
<#--</script>-->
<#--<script type="text/x-jquery-tmpl" id="image-box-tmpl">-->
<#--    <p class="ctrl-bar">-->
<#--        <a data-action="piup" class="icon-piup icon-bg">-->
<#--            收起-->
<#--        </a>-->
<#--        <a class="origin-image inco-bg" href="\${origin_img!""}" target="_blank">-->
<#--            查看原图-->
<#--        </a>-->
<#--        <a data-action="left" class="icon-trunleft icon-bg">-->
<#--            向左转-->
<#--        </a>-->
<#--        <a data-action="right" class="icon-trunright icon-bg">-->
<#--            向右转-->
<#--        </a>-->
<#--    </p>-->
<#--    <div class="image-wrap">-->
<#--        <a data-action="piup">-->
<#--            <img class="narrow-move thumb-zoom-out" src="\${zoom_img!""}" />-->
<#--        </a>-->
<#--        <p class="loading-img"></p>-->
<#--        {{if index - 1 >= 0}}-->
<#--        <p data-index="\${index!0 - 1}" class="pic-nav pic-nav-prev"></p>-->
<#--        {{/if}}-->
<#--        {{if index + 1 < count}}-->
<#--        <p data-index="\${index!0 + 1}" class="pic-nav pic-nav-next"></p>-->
<#--        {{/if}}-->
<#--    </div>-->
<#--</script>-->

<script src="${staticResourcePathH5}/js/swa/bootstrap/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/swa/jquery.tmpl.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/swa/openWeiboImg.js?v=${SYSTEM_INIT_TIME}"></script>

<!--虚拟滚动条 js-->
<link href="${staticResourcePathH5}/js/swa/scrollbar/css/prettify.css?v=${SYSTEM_INIT_TIME}" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/scrollbar/js/prettify.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/scrollbar/js/jquery.slimscroll.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript">
    $(function() {
        $('#navDiv').slimScroll({
            height : 'auto'
        });
        //说明文字显示隐藏
        $(".titlBar .icon-tishi").on("click",function(){
            $(this).parents(".titlBar").next(".tipinfo").toggle(300)
        });
        $(".tipinfo .close").on("click",function(){
            $(this).parents(".tipinfo").hide(300)
        });
    });

</script>
<#include "../../buttom.ftl">
</body>
</html>