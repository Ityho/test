<#include "../../top.ftl">
<link href="${staticResourcePathH5}/js/swa/bootstrap/css/bootstrap.min.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link href="${staticResourcePathH5}/css/swa/style.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link href="${staticResourcePathH5}/css/style.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link href="${staticResourcePathH5}/css/common.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link href="${staticResourcePathH5}/css/swa/openWeiboImg.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/common.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/singleWeiboAnalysis.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/util.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript">
    $(function() {
        $('#fxbtn').click(function() {
            var url = $.trim($('#weiboURL').val());
            if (url != null && url != '' && (url.indexOf('weibo.com') != -1 || url.indexOf('m.weibo.cn') != -1)) {
                goAnalysisWeibo(null,null, url, false);
            } else {
                alertMsg('微博链接不正确，请重新输入！');
                $('#weiboURL').select();
            }
        });
    });

    function demo() {
        createForm(actionBase + '/ui/singleWeiboAnalysis/demo.shtml', 'POST', '_self', null);
    }
    // 消息弹窗
    function alertMsg(content) {
        $('#msgContent').text(content);
        $(".zhezhao").addClass('downShow');
        $("#msgPOP").css("display","block");
        $(".prompPOP").addClass('scaleShow');
        $(".prompPOP").removeClass('scaleOut');
    }
</script>
<body>
<input type="hidden" id="uid" value="${uid!""}" />

<section class="section" style="border-left:solid 6px #fd8c25;">
    <h2 class="float_l">微博传播分析</h2>
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
                            <#if uid??>
                                <div style="float:right;font-size:14px;margin-top:-20px;color:#dfdfdf">您有${(admin.singleWeiboAnalysisValidCount)!0}条剩余转发条数哦！<br /></div>
                            <#else>
                                <div style="font-size:14px;margin-top:-15px;color:#dfdfdf">您有${(admin.singleWeiboAnalysisValidCount)!0}条剩余转发条数哦！<br /></div>
							</#if>
                        </div>
                    </div>
                </div>
                <div class="row-fluid rel">
                    <div class="demoPlay" style="display: none;">
                        <div class="playBox">
                            <button type="button" id="closePlay" class="close close-sm"></button>
                            <img src="${staticResourcePathH5}/images/swa/demoplay.gif?v=${SYSTEM_INIT_TIME}" width="100%" />
                        </div>
                    </div>
                    <div class="mwbBorder mwbBorder2">
                        <h2>如何分析微博？</h2>
                        <div class="mwbcon">

                            <ul class="stepList">
                                <li><i></i><a href="${njxBasePath}/ui/singleWeiboAnalysis/help.action">1.在输入框内粘贴微博链接 <em>（如何粘贴）</em></li>
                                <li><i></i>2.点击分析按钮</li>
                                <li><i></i>3.等待系统分析数据（20-30分钟）</li>
                                <li><i></i><a href="javascript:;" onclick="demo()">4.查看微博分析结果 <em>（查看案例）</em></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!--我的博文弹窗 start-->

<div class="modal fade" id="checkMyBoWen" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="position: absolute;left: 275px; width: 100%;">
    <div class="modal-dialog" style="margin-top: 100px;">
        <div class="modal-content" style="margin-top:-90px;">
            <div class="popover-title popover-title1">
                <button type="button" class="close close-sm" data-dismiss="modal"></button>
                <h4 class="modal-title">
                    我的博文 <span class="small">(同步显示最新20条博文)</span>
                </h4>
            </div>
            <div class="form-list form-list2">
                <ul class="bwList" id="navDiv">
                    <img src="${staticResourcePathH5}/images/loading.gif?v=${SYSTEM_INIT_TIME}" /> Loading<span class="ani_dot">...</span>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--我的博文弹窗 end-->
<c:set var="type" value="11"></c:set>

<!--消息弹出框 -->
<div id="msgPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
    <div class="prConBox">
        <div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

        <div class="PromptCon" id="msgContent" style="text-align: center;"></div>

        <div class="bottom"><a style=" float: initial" class="submitBtn " onclick="" style="text-align: center;">确定</a></div>
    </div>
</div>
<div class="zhezhao" style="display: none;"></div>
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
    });
</script>
<#include "../../buttom.ftl">
</body>
</html>