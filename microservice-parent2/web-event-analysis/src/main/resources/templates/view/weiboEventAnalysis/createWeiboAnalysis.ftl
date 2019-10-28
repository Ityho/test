<#include "../../top.ftl" >

<link rel="stylesheet" href="${staticResourcePathH5!}/css/reset.css?v=${SYSTEM_INIT_TIME!}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath!}/css/productBuy/style.css?v=${SYSTEMINITTIME!}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath!}/css/productBuy/font-icon.css?v=${SYSTEMINITTIME!}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath!}/css/productBuy/layoutpay.css?v=${SYSTEMINITTIME!}" />
<link rel="stylesheet" type="text/css" href="${njxBasePath!}/css/productBuy/single.css?v=${SYSTEMINITTIME!}" />
<script src="${njxBasePath!}/js/productBuy/jquery.min.js"></script>
<script src="${njxBasePath!}/js/productBuy/wyrem.js"></script>
<script src="${njxBasePath!}/js/productBuy/icheck.js"></script>
<script src="${njxBasePath!}/js/productBuy/iscroll.js"></script>
<script>
    var njxBasePath="${njxBasePath}";
</script>
<script src="${njxBasePath!}/js/navigate.js?v=${SYSTEM_INIT_TIME!}"></script>
<script src="${njxBasePath!}/js/common-order.js?v=${SYSTEMINITTIME!}"></script>
<script type="text/javascript">saveOperateLog('微博分析创建页','1033');</script>
<style>
    .cohead{
        background-color: #db2e25;
        display: inline-block;
        float: left;
        margin-top: 4px;
        margin-right: 10px;
        width: 11px;
        height: 11px;
        padding:0;
        border:none;
        border-radius: 50%;
        margin-left: 0px;
    }
    .bdage{
        margin-left:32px;margin-right:15px;margin-bottom:-2px;height: 12px;

    }
</style>
<body>
<!--主要内容 start-->
<input type="hidden" id="setUpdateTime" value="3">
<div id="container">
    <section class="section" style="border-left:solid 6px #fd8c25;">
        <h2 class="float_l">微博事件分析</h2>
        <div style="margin-top: 10px;margin-right:15px;" class="float_r">
            <a href="${njxBasePath!}/analysis">返回</a></div>
    </section>

    <!--创建监测方案 start -->
    <section class="section rel">

        <!--标准模式 start -->
        <article class = "moshi">
            <form id="frmPopWin" name="frmPopWin" action="#" class="form-horizontal">
                <input type="hidden" name="analysisTask.userId" value="${(admin.userId)!0}">
                <input type="hidden" name="createType" value="${createType!0}">
                <input type="hidden" name="analysisTask.taskId" value="${(analysisTask.taskId)!0}">
                <input type="hidden" name="analysisTask.setTime" id="setTime" value="3">
                <input type="hidden" name="analysisTask.contentType" value="2">
                <div class="control-group">
                    <label class="control-label" style="width:150px;">事件名称<i class="f_c13">(必填)</i>：</label>
                    <div class="controls">
                        <input type="text" style="background-color:#FFF;" placeholder="请输入事件名称" class="input-text" id = "incidentTitle" name="analysisTask.incidentTitle" maxlength="30" style="height:45px;">
                        <input type="hidden" value="${(admin.userWeiboAnalysisValidCount)!0}" id="eventName">
                    </div>
                </div>
                <div class="control-group" style="padding-bottom:0px;">
                    <label class="control-label" style="width:150px;">关键词<i class="f_c13">(必填)</i>：</label>
                    <div class="controls">
                        <input type="text" style="background-color:#FFF;"value="" placeholder="词语之间请用空格隔开" class="input-text inputGray" id="keyword"  name="analysisTask.keyword" style="height:45px;">
                    </div>
                </div>
				<div class="control-group" style="padding-bottom:0px;">
                <label class="control-label" style="width:150px;">时间范围<i class="f_c13">(必填)</i>：</label>
                <div class="controls" style="margin:10px 0px 0px;">
                    <section class="section">
                        <article class="context context2" style="line-height: 22px;">
                            <ul class="align_c hot_num" id="setTime">
                                <li class="g_25 click" value="3"><p>最近3天</p></li>
                                <li class="g_25" value="7"><p>最近7天</p></li>
                                <li class="g_25" value="10"><p>最近10天</p></li>
                                <li class="g_25" value="30"><p>最近30天</p></li>
                            </ul>
                        </article>
                    </section>
                </div>
            </div>
                <div class="control-group" id="expectTime" style="border-bottom: solid 1px #efecec;height: 30px;padding:0px;display: none;">
                    <label class="control-label" style="display:inline-block;width: 120px;">预计分析时间：</label>
                    <span>&nbsp;</span>
                    <div class="controls">
                        <input type="hidden" value="" name="analysisTask.analysisTotalConsumeExpect" id="analysisTotalConsumeExpect">
                    </div>
                </div>
                <div class="control-group" id="expectNum" style="border-bottom: solid 1px #efecec;height: 30px;padding:0px;display: none;">
                    <label class="control-label" style="display:inline-block;">预计数量：</label>
                    <span>&nbsp;</span>
                    <div class="controls">
                        <input type="hidden" value="" name="analysisTask.analysisSolrFirstCountExpect" id="analysisSolrFirstCountExpect">
                    </div>
                </div>
            </form>
        </article>
        <!--标准模式 end -->
        <!--消息弹出框 -->
        <div id="msgPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
            <div class="prConBox">
                <div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

                <div class="PromptCon" id="msgContent" style="text-align: center;"></div>

                <div class="bottom"><a  style="float: initial" class="submitBtn " onclick="hideUpdate()" style="text-align: center;">确定</a></div>
            </div>
        </div>


        <script type="text/javascript">
            $(function(){
                Zepto("#setTime li").on("touchend",function(){
                    if(!$.trim($("#incidentTitle").val())){
                        alertMsg("请输入名称！");
                        return;
                    }
                    if(!$.trim($("#keyword").val())){
                        alertMsg("请输入关键字！");
                        return;
                    }
                    $("#setTime li").removeClass("click");
                    $(this).addClass("click");
                    var liTextStr = $(this).attr('value');
                    // alert(liTextStr);
                    document.getElementById('setTime').value=liTextStr;
                    expectTask();

                });
            });
            //添加字符+|()
            function addCh(ch) {
                var textBox = document.getElementById('keyword');
                /*if (textBox.selectionStart) {*/
                var start = textBox.selectionStart;
                var end = textBox.selectionEnd;

                var pre = textBox.value.substr(0, start);
                var post = textBox.value.substr(end);
                textBox.value = pre + ch + post;
                start = start + 1;
                textBox.setSelectionRange(start,start);
                textBox.focus();
                /*}*/
            }
            var createParmas = "";
            var createFlag  = 0;
            var isCreate = true;
            function createTask(){
                $.ajax({
                    url : "${njxBasePath!}/weibo/addWeiboTask",
                    type : "post",
                    data : $("#frmPopWin").serialize(),
                    success : function(result){
                        if(result.status == 1) {
                            parent.location.href="${njxBasePath!}/weibo/createWeiBoAnalysis";
                        }else{
                            // alertMsg(result.msg);
                            swal(result.msg);
                        }
                    }
                });
            <#--$.ajax({-->
                    <#--url : actionBase + '//checkProValidCount',-->
                    <#--type : 'POST',-->
                    <#--success : function(result) {-->
                        <#--if (!$.isEmptyObject(result)) {-->
                            <#--console.log(result.wbAnalysisCount);-->
                            <#--if (!result.isPro && result.wbAnalysisCount < 1) {-->
                                <#--gochat();-->
                            <#--}else{-->
                                <#--var incidentTitle = $("#incidentTitle").val();-->
                                <#--var setTime = document.getElementById("setTime").value;-->


                                <#--if($.trim(incidentTitle)==""){-->
                                    <#--alertMsg("请输入标题！");-->

                                    <#--return false;-->
                                <#--}-->
                                <#--var keyword = $("#keyword").val();-->
                                <#--if($.trim(keyword)==""/*||$.trim($("#supplementKeyword").val())==""*/){-->
                                    <#--alertMsg("请输入事件涉及词！");-->
                                    <#--return false;-->
                                <#--}else{-->
                                    <#--var arr = keyword.split(/\+|\|/);-->
                                    <#--if(arr.length<1){-->
                                        <#--alertMsg("事件涉及词至少包含一个词！");-->
                                        <#--return false;-->
                                    <#--}else if(arr[0].length<2){-->
                                        <#--alertMsg("词语至少包含二个字！");-->
                                        <#--return false;-->
                                    <#--}-->
                                    <#--if(setTime == 0){-->
                                        <#--alertMsg("请选择一个时间！");-->
                                        <#--return false;-->
                                    <#--}-->
                                <#--}-->
                                <#--var getFilterKeyword = $("#filterKeyword").val();-->
                                <#--if(getFilterKeyword !=null && getFilterKeyword !=""){-->
                                    <#--$("#filterKeyword").val($("#filterKeyword").val().replace(/\s+/g,""));-->
                                <#--}-->
                                <#--//$("#keyword").val($("#keyword").val().replace(/\s+/g,""));-->

                                <#--$.ajax({-->
                                    <#--url : "${njxBasePath!}/view/weiboEventAnalysis/addWeiboTask.action",-->
                                    <#--type : "post",-->
                                    <#--data : $("#frmPopWin").serialize(),-->
                                    <#--success : function(result){-->
                                        <#--if(result){-->
                                            <#--parent.location.href="${njxBasePath!}/view/weiboEventAnalysis/createWeiBoAnalysis.shtml";-->
                                        <#--}else{-->
                                            <#--alertMsg(result);-->
                                        <#--}-->
                                    <#--}-->
                                <#--});-->

                            <#--}-->
                        <#--}-->
                    <#--}-->
                <#--});-->


            }

            function expectTask(){

                var setTime = document.getElementById("setTime").value;
                var params = $("#keyword").val()+"_"+$("#filterKeyword").val()+setTime;

                if(createParmas==params){
                    return;
                }
                createParmas=params;
                createFlag = 0;
                $("#expectTime span").last().html('<img src=\"${njxBasePath!}/images/loading_c.gif\" width="15">');
                $("#expectNum  span").last().html('<img src=\"${njxBasePath!}/images/loading_c.gif\" width="15">');

                $.ajax({
                    url : "${njxBasePath!}/weibo/expectTask",
                    type : "post",
                    data : $("#frmPopWin").serialize(),
                    success : function(result){
                        if((typeof result)=="string"){

                            //showMsgInfo(0,result,0);
                            alertMsg(result);
                            unClick();
                        }else if(result){
                            createFlag = 1;
                            var analysisTotalConsumeExpect = result.analysisTotalConsumeExpect;
                            var analysisSolrFirstCountExpect = result.analysisSolrFirstCountExpect;
                            $("#analysisTotalConsumeExpect").val(analysisTotalConsumeExpect);
                            $("#analysisSolrFirstCountExpect").val(analysisSolrFirstCountExpect);
                            $("#expectTime span").last().text((parseInt(analysisTotalConsumeExpect)/60).toFixed(0)+"分钟");
                            $("#expectNum span").last().text(analysisSolrFirstCountExpect);
                            $("#expectTime").show();
                            //$("#expectNum").show();
                            openClick();
                        }
                    }
                })

            }
            // 消息弹窗
            function alertMsg(content) {
                $('#msgContent').text(content);
                $(".zhezhao").addClass('downShow');
                $("#msgPOP").css("display","block");
                //$("body").css({overflow:"hidden"});    //禁用滚动条
                $(".prompPOP").addClass('scaleShow');
                $(".prompPOP").removeClass('scaleOut');
            }
            function hideUpdate(){
                $("#msgPOP").css("display","none");
                $('.zhezhao').removeClass('downShow');
            }
            function unClick(){
                isCreate = false;
                $("#senior_set").attr("disabled", "true");

            }
            function openClick(){
                isCreate = true;
                $("#senior_set").removeAttr("disabled");
            }


        </script>



        <article class="senior senior2">
            <button type="button" style="background-color:  #fd8c25;border: solid 0px;color: #fff;border-radius: 6px;" id="senior_set"  onclick="createTask()" >创建</button>

        </article>
    </section>
    <!-- 我的分析页面start -->
    <section class="section" style="margin-bottom:0px;">
        <h2 class="float_l">历史分析</h2>
    </section>
    <input type="hidden" id="tickets" value=""/>
    <input type="hidden" id="tickets2" value=""/>
    <script>
        $("#tickets").val("");
        $("#setCreateTime").val("");
    </script>
	<#list taskList! as task>

        <section class="section analysisList rel" style="margin-bottom:0px;">
            <#if task.analysisStatus==1>
                <input type="hidden" name="weifenxi" value="1">
                <tr id="${(task.taskTicket)!""}">
                <input type="hidden" value="${(task.taskId)!0}"/>
                <div class="conhead">
                    <div ><img src="${njxBasePath!}/images/analysisic.png" style="float: left;  margin-right: 10px; width: 18px; height: 18px;"/></div>
                    <span>
					<p class="t1"><a href="#">${(task.incidentTitle)!""}</a></p>
					<p class="t2">时间范围：${task.startTime?string("yyyy-M-d")}  — ${task.endTime?string("yyyy-M-d")}</p>
				</span></div>
                <td><span class="badge" style="margin-left:35px;font-size:13px;">分析引擎启动中</span> </td>
                <td>
                    <div class="padding8">
                        <div class="progress  progress-striped active" style="display: none;">
                            <div id="setSchedule"class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${task.schedule*100}%; " >
                            <input type="hidden" id="schedule" value=${task.schedule*100}>
                        </div>
                    </div>
			</div>
</td>
<td class="link">
</td>
</tr>
<script>
    $("#tickets").val()==""?$("#tickets").val('${(task.taskTicket)!""}'):$("#tickets").val($("#tickets").val()+","+'${(task.taskTicket)!""}');
</script>

<#elseIf task.analysisStatus==2||task.analysisStatus==3>
    <tr id="${(task.taskTicket)!""}">
    <input type="hidden" value="${(task.taskId)!0}"/>
    <div class="conhead">
        <div><img src="${njxBasePath!}/images/analysisic.png" style="float: left;  margin-right: 10px; width: 18px; height: 18px;"/></div>
        <span>
					<p class="t1"><a href="#">${(task.incidentTitle)!""}</a></p>
					<p class="t2">时间范围：${task.startTime?string("yyyy-M-d")}— ${task.endTime?string("yyyy-M-d")}</p>
							<input id="createTime"type="hidden" value = "${task.createTime?string("yyyyMMdd")}" >
				</span>
    </div>
    <td><span class="badge blue" style="margin-left:35px;font-size:13px;">分析中</span> </td>
    <td>

        <div class="padding8">
            <div class="progress  progress-striped active">
                <div id="setSchedule"class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${task.schedule*100}%;">
                <input type="hidden" id="schedule" value=${task.schedule*100}>
            </div>
        </div>
        </div>

    </td>
    </tr>
    <script>
        $("#tickets").val()==""?$("#tickets").val('${(task.taskTicket)!""}'):$("#tickets").val($("#tickets").val()+","+'${(task.taskTicket)!""}');
    </script>
<#elseIf task.analysisStatus==5 || task.analysisStatus==4>
    <tr id="${(task.taskTicket)!""}">
    <input type="hidden" value="${(task.taskId)!0}"/>
    <div class="conhead">
        <div ><img src="${njxBasePath!}/images/analysisic.png" style="float: left;  margin-right: 10px; width: 18px; height: 18px;"/></div>
        <span>
					<p class="t1"><a href="#">${(task.incidentTitle)!""}</a></p>
					<p class="t2">时间范围：${task.startTime?string("yyyy-M-d")} — ${task.endTime?string("yyyy-M-d")}</p>
					<input id="createTime"type="hidden" value = "${task.createTime?string("yyyyMMdd")}" >
					<input type="hidden" name="analysisTask.setTime" id="setTime" value="3">
					<input id="keyword_${(task.taskId)} "type="hidden" value = "${(task.keyword)!""}" >
					<input id="filterKeyword_${(task.taskId)!0}"type="hidden" value = "${(task.filterKeyword)!""}" >

				</span>
    </div>
    <div style="font-size:13px;height: 40px;line-height:35px;">
        <td><img  class="bdage"src="${njxBasePath!}/images/analysis1.png"/> </td>
        <td  class="link">
            <a style="color:#333;"href="javascript:void(0);" onclick="preview('${(task.taskTicket)!""}');">查看</a>
            <a style="color:#333;margin-left:15px;"href="javascript:void(0);" onclick="showDelete('${(task.taskTicket)}');">删除</a>
            <div class="rel" style="display: inline-block;">
                <!--任务更新弹出框 start-->

                <!--任务更新弹出框 end-->
                <a class="updateA" style="margin-left:15px;color:#333;"href="javascript:void(0)" onclick="showUpdate(${(task.taskId)!0});">更新</a>

            </div>
        </td>
    </div>
    </tr>

<#elseIf task.analysisStatus==0>
    <tr id="${(task.taskTicket)!""}">
    <input type="hidden" value="${(task.taskId)!0}" />
    <div class="conhead">
        <div ><img src="${njxBasePath!}/images/analysisic.png" style="float: left; margin-right: 10px; width: 18px; height: 18px;"/></div>
        <span>
					<p class="t1"><a href="#">${(task.incidentTitle)!}</a></p>
					<p class="t2">时间范围：<${task.startTime?string("yyyy-M-d")} — ${task.endTime?string("yyyy-M-d")}</p>
										<input id="createTime"type="hidden" value = "${task.createTime?string("yyyyMMdd")}" >
					<input type="hidden" name="analysisTask.setTime" id="setTime" value="3">
					<input id="keyword_${(task.taskId)!0}"type="hidden" value = "${(task.keyword)!0}" >
					<input id="filterKeyword_${(task.taskId)!0}"type="hidden" value = "${(task.filterKeyword)!""}" >


				</span>
    </div>
    <div style="font-size:13px;height: 40px;line-height:35px;">
        <td><img  class="bdage"src="${njxBasePath!}/images/analysis0.png"/> </td>

        <td  class="link" id="submitBtn">
            <a style="color:#333;"href="javascript:restartAnalysis('${(task.taskId)!0}');">重新分析</a>
            <div class="rel" style="display: inline-block;"></div>
        </td>

        <div class="rel" style="display: inline-block;">
            <!--任务更新弹出框 start-->

            <!--任务更新弹出框 end-->
            <a style="color:#333;"class="updateA" href="javascript:void(0)" onclick="showUpdate(${(task.taskId)!0});">更新</a>

        </div>
    </div>
    </tr>
			</#if>
</section>
	</#list>


<form target="_blank" method="post" action="" id="aForm">
    <input type="hidden" value="" name="tickets">
</form>
<!-- 更新弹框 -->
<div id="other_time" class="zz_content" style="display: none; left: 5%; top: 10%; width: 90%; max-height: 90%; position: fixed;">
    <div class="td_title rel">
        <h1>提示</h1>
        <a onclick="javascript:$('.zhezhao').fadeOut();$('#other_time').css('display', 'none');" class="info_close abs">×</a>
    </div>
    <section class="section">
        <article class="context context2" style="line-height: 22px;">
            <ul class="align_c hot_num" id="setUpdateTime">
                <li class="g_25 click" value="3"><p>最近3天</p></li>
                <li class="g_25" value="7"><p>最近7天</p></li>
                <li class="g_25" value="10"><p>最近10天</p></li>
                <li class="g_25" value="30"><p>最近30天</p></li>
            </ul>
        </article>
    </section>
    <div class="control-group" id="expectTime2" style="display: none;padding:0px;height:30px;">
        <label class="control-label" style="width: 120px;">预计分析时间：</label><span>&nbsp;</span>
        <div class="controls">
            <input type="hidden" value="" name="analysisTask.analysisTotalConsumeExpect" id="analysisTotalConsumeExpect2">
        </div>
    </div>
    <div class="control-group" id="expectNum2" style="display: none;">
        <label class="control-label">预计数量：</label><span>&nbsp;</span>
        <div class="controls">
            <input type="hidden" value="" name="analysisTask.analysisSolrFirstCountExpect" id="analysisSolrFirstCountExpect2">
        </div>
    </div>
    <div class="form-actions align_c" >
        <input type="hidden" id="setUpadateId" value="">
        <button class=" button" onclick="updateTask(this)" id="senior_set">更新</button>
        <button class=" button button5" onclick="hideUpdateT()">取消</button>
    </div>
</div>
<div id="cancelPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
    <div class="prConBox">
        <div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

        <div class="PromptCon" style="text-align: center;">确定删除吗？</div>

        <div class="bottom">
            <a href="javascript:;" onclick="del()" class="submitBtn">确定</a>
            <a href="javascript:;" class="submitBtn cancelFot">取消</a>
        </div>
    </div>
</div>
<div class="popver popver-single" id="singlediv">
	<#list weiboAnalysisProductList! as  item>
        <div class="pop-top">
            <h3 class="pop-title"><span>微博事件分析</span></h3>
            <span class="price">99元/次</span>
            <p class="fz11">输入近期事件或话题关键词，微热点针对微博信息进行深度挖掘和多重分析；记录事件从始发到发酵期、发展期、高涨期、回落期和反馈期等阶段的演变过程，分析信息传播路径、传播节点、发展态势和受众反馈等。</p>
        </div>
        <div class="pop-con">
            <section>
                <ul class="pay-choose clearfix">
                    <li class="borderb">
                        <span class="fc-gray6">购买数量</span>
                        <form  name="payForm" method="post" id = "selfProductForm">
                            <input id = "payType" type="hidden" name="payType" value="00"/>
                            <input id = "productPackageId"  type="hidden" name="myProductDto.keywordId" value="${(item.productPackageId)!0}" />
                            <input id = "productFlag"  type="hidden" name="myProductDto.productFlag" value="product1003" />
                            <input id = "useCredit1" type="hidden" name="useCredit" value="true"/>
                            <input id = "orderType" type="hidden" name="orderType" value="1"/>
                            <div class="addsub">
                                <a href="javascript:;" class="sub" onclick="minSinglePackageCount('#product1003Num', 3)"></a>
                                <input type="number" id = "product1003Num" onchange="getBuyGroupProductFee(3, this.value)" name="myProducts['product${item.productPackageId}'].keywordPackageNum" value="1" />
                                <a href="javascript:;" class="add" onclick="addPackageCount('#product1003Num', 3)"></a>
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
            <a  class="btn-pay" id="goProductCartBtn" >马上购买</a>
        </div>
	</#list>
</div>

<input type="hidden" id="setTick">
<!--微积分不足-->
<div class="fail-popver" id="fail">
    <div class="fail-tit">
        <img src="${njxBasePath!}/images/productBuy/fail.png" style="width: 50px;">
        <p class="mt10">微积分不够啦</p>
    </div>
    <div class="taligncenter fail-txt">
        <p>当前可用微积分为<span class="fc-p-red" id="nowwjf"></span></p>
        <p>还需要<span class="fc-p-red" id="needwjf"></span>才能购买</p>
    </div>
    <div class="btn-footer">
        <a href="javascript:;" class="pay-close">取消支付</a>
        <a href="${njxBasePath!}/userCenter/goBuy?type=0" class="btn-recharge">去充值</a>
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
<div id="jiazai" style="z-index: 99;display:  none;position: fixed;top: 45%;left: 45%;">
    <img src="${njxBasePath!}/images/productBuy/jiazai.gif">
</div>
<div class="zhezhao" style="display: none;"></div>
<div class="popver-mask"></div>
<#assign type=11>
<#assign productPackageType=4>
<#include "../../buttom.ftl">
<script type="text/javascript">
    $(document).ready(function() {
        $('.icheckbox-list input').on('ifChecked', function(event) {
            var value = $(this).val();
            console.log(value);
            if(value=="03"){
                $("#payType").val("03");
                $("#useCredit1").val("false");
            }
            if(value=="02"){
                $("#payType").val("02");
                $("#useCredit1").val("false");
            }
            if(value=="00"){
                $("#payType").val("00");
                $("#useCredit1").val("true");
            }
            commonBuyProductFee();
        }).iCheck({
            checkboxClass: 'icheckbox_square-red',
            radioClass: 'iradio_square-red',
            increaseArea: '20%'
        });
    });
    /*打开弹框*/
    $('#incidentTitle, #keyword').on('click', function() {
        if(${(admin.userWeiboAnalysisValidCount)!0}<1){
           gochat();
        }
    });
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

    function gochat(){
        commonBuyProductFee();
        $('.popver-single').show();
        $('.popver-mask').show();
    }
</script>

<script type="text/javascript">
    function checkCount(obj){
        if($("#eventName").val()<1&&"${(admin.userType)!0}" != "2")  {
            showBuy({type:4});
            $(obj).blur();
        }
    }
    $(function(){

        $("#setUpdateTime li").on("touchend",function(){
            $("#setUpdateTime li").removeClass("click");
            $(this).addClass("click");
            var liTextStr = $(this).attr('value');
            document.getElementById('setUpdateTime').value=liTextStr;
            expectUpdateTask();
        });
    });

    $(function(){
        var tickets = $("#tickets").val();
        if($("input[name='weifenxi']").length)
            setTimeout(function(){location.reload()},20000);
        if(tickets!=""){
            setTimeout(function(){queryTaskStatus(tickets)},10000);
        }


    })

    function queryTaskStatus(tickets){
        $.ajax({
            url:"queryTaskInfo",
            type:"post",
            data:{"tickets":tickets},
            success:function(data){
                if(data){
                    var list = data.tasksList;
                    $.each(list,function(){
                        var taskId = this.analysisTaskTicket;

                        if(this.analysisStatus==2||this.analysisStatus==3||this.analysisStatus==4){
                            var percent = (parseFloat(this.analysisSchedulePercent)*100).toFixed(1)+"%";
                            document.getElementById("setSchedule").style.width=percent;
                        }else if(this.analysisStatus==5){
                            var getCreateTime = document.getElementById("createTime").value;

                            location.reload();
                        }
                    });
                    if($("#tickets").val()!=""){
                        setTimeout(function(){queryTaskStatus(tickets)},5000);
                    }
                }

            }
        })
    }
    function expectUpdateTask(){
        var setTime = document.getElementById("setUpdateTime").value;
        console.log(setTime);
        var taskId = document.getElementById("setUpadateId").value;
        console.log(taskId);
        var params = $("#keyword").val()+"_"+setTime;
        var getKeyword = "keyword_"+taskId;
        var keyword = document.getElementById(getKeyword).value;
        console.log(keyword);
        var WbContentType = 2;
        if(createParmas==params){
            return;
        }
        createParmas=params;
        createFlag = 0;
        $("#expectTime2 span").last().html('<img src=\"${njxBasePath!}/images/loading_c.gif\" width="15">');
        $("#expectNum2  span").last().html('<img src=\"${njxBasePath!}/images/loading_c.gif\" width="15">');

        $.ajax({
            url : "${njxBasePath!}/weibo/expectTask",
            type : "post",
            data : {keyword: keyword,'analysisTask.setTime':setTime,WbContentType:WbContentType},
            success : function(result){
                if((typeof result)=="string"){
                    alertMsg(result);
                    unClick();
                }else if(result){
                    createFlag = 1;
                    var analysisTotalConsumeExpect = result.analysisTotalConsumeExpect;
                    var analysisSolrFirstCountExpect = result.analysisSolrFirstCountExpect;
                    $("#analysisTotalConsumeExpect2").val(analysisTotalConsumeExpect);
                    $("#analysisSolrFirstCountExpect2").val(analysisSolrFirstCountExpect);
                    $("#expectTime2 span").last().text((parseInt(analysisTotalConsumeExpect)/60).toFixed(0)+"分钟");
                    $("#expectNum2 span").last().text(analysisSolrFirstCountExpect);
                    $("#expectTime2").show();
                    $("#expectNum2").show();
                    openClick();
                }
            }
        })

    }
    function preview(obj){

        var tickets =obj;

        window.location.href="${njxBasePath!}/weibo/analysisPreview?tickets="+encodeURIComponent(tickets);
    }
    //重新分析
    function restartAnalysis(taskId){

        var params = {"analysisTask.taskId":taskId,"createType":3};
        $.ajax({
            url:"${njxBasePath!}/weibo/addWeiboTask",
            type:"POST",
            data:params,
            success:function(data){
                if(data) {
                    location.reload();
                }else{
                    flag = true;
                    alertMsg(0, data, 0);
                }
            }
        });
    }
    //更新
    function updateTask(obj){

        var taskId = document.getElementById("setUpadateId").value;
        var setTime = document.getElementById("setUpdateTime").value;
        console.log(taskId+"-------"+setTime);

        var params = {"taskId":taskId,"setTime":setTime,"createType":2};

        $.ajax({
            url : "${njxBasePath!}/weibo/addWeiboTask",
            type : "post",
            data : params,
            success : function(result){
                if(result){
                    hideUpdate();

                    location.reload();
                }else{
                    alertMsg(result);
                }
            }
        });
    }
    function showUpdate(obj){
        $(".taskTimeSegment").hide();
        $(".zhezhao").show(300);
        $('#other_time').css('display', 'block');

        document.getElementById('setUpadateId').value=obj;

    }
    function hideUpdateT(){
        $("#other_time").css('display', 'none');
        $('.zhezhao').fadeOut();
    }
    function showDelete(tickets) {
        $('#msgContent').text("确定要删除吗?");
        $(".zhezhao").show();
        document.getElementById("setTick").value = tickets;
        $("#cancelPOP").css("display","block");

        $(".prompPOP").addClass('scaleShow');
        $(".prompPOP").removeClass('scaleOut');
    }
    //删除
    function del(){
        //showMsgInfo(0, '确定要删除吗?', 1);
        // alert("1");
        // $(".submitBtn").one("click",function(){
        var tickets = document.getElementById("setTick").value;
        //  alert(tickets);
        $.ajax({
            url:"${njxBasePath!}/weibo/delTaskStatus",
            type:"POST",
            data:{
                "tickets":tickets,
            },
            success:function(data){
                if(data==true) {
                    window.location.reload();
                }else{
                    alertMsg("系统繁忙，请稍候再试！");
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alertMsg("系统繁忙，请稍候再试！");
            }
        });
//    });

    }
</script>
</div>
<!--主要内容 end-->


</body>
</html>