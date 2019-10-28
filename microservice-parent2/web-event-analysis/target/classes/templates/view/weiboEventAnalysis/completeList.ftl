<#include "../../top.ftl">
<link rel="stylesheet" href="${staticResourcePathH5! }/css/reset.css?v=${SYSTEM_INIT_TIME! }" />

<script type="text/javascript">saveOperateLog('微博分析报告页','1034');</script>
<!--主要内容 start-->
<div id="container">
    <section class="section">
        <h2 class="float_l" style="border-left:solid 6px #fd8c25;">微博事件分析报告</h2>
        <div style="margin-top: 10px;margin-right:15px;" class="float_r">
            <a href="${njxBasePath! }/analysis">返回</a></div>
    </section>
    <input type="hidden" id="tickets" value=""/>
    <script>
        $("#tickets").val("");
    </script>

<#list reportList! as task>
    <section class="section analysisList rel" >

        <tr id="${task.taskTicket!}">
            <input type="hidden" value="${task.taskId!}"/>
            <div ><img src="${njxBasePath! }/images/application/circleDouble.png" style="float: left; margin-top:12px; margin-right: 10px; width: 15px; height: 15px;"/></div>
            <span>
					<p class="t1" style="width:50%;font-size:16px;line-height:40px;height:40px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${task.incidentTitle!}</p>
						<div id="open_${task.taskId!}" style="display:block;">
						<div onclick="showDelete('${task.taskTicket!}');" class="delReport">
							删除
						</div>
						<div onclick="preview('${task.taskTicket!}');" class="checkReport">
							查看
						</div>

					</div>
                    <p class="t2" style="border-top:solid 1px #eaeaea;text-indent:20px;color:#c5c5c5;height:30px;line-height:30px;">${task.createTime?string("yyyy-M-d")}</p>

					<input type="hidden" name="analysisTask.setTime" id="setTime" value="3">
				</span>
        </tr>

    </section>
</#list>
</div>
<!--主要内容 end-->

<form target="_blank" method="post" action="" id="aForm">
    <input type="hidden" value="" name="tickets">
</form>

<div id="other_time" class="zz_content" style="display: none; left: 5%; top: 10%; width: 90%; max-height: 90%; position: fixed;">
    <div class="td_title rel">
        <h1>提示</h1>
        <a onclick="javascript:$('.zhezhao').removeClass('downShow');$('#buyPrompt_jp').css('display', 'none');" class="info_close abs">×</a>
    </div>
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
    <div class="form-actions align_c" >
        <input type="hidden" id="setId" value="">
        <button class=" button" onclick="updateTask(this)">更新</button>
        <button class=" button button5" onclick="hideUpdate()">取消</button>
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
<input type="hidden" id="setTick">
<div class="zhezhao" style="display: none;"></div>
<!--我要分析弹窗 start -->
<div id="buyPrompt" class="zz_content" style="display: none;">
    <div class="td_title rel">
        <h1>我要分析</h1>
        <a class="info_close abs">×</a>
    </div>
    <div class="content-body">
        <div class="buttonGroup">
            技术gg正在玩(ren)命(zhen)开发中,请登录网页版(wyq.sina.com)使用全部功能
        </div>
    </div>
</div>
<!--我要分析弹窗 end -->
<!--底部 start-->
<footer class="warning-footer" style="box-shadow: none; padding-top: 5px;">
    <p><i class="icon-plus"></i></p>
    <a onclick="javascript:location.href ='${njxBasePath! }/weibo/createWeiBoAnalysis?createType=1';" class="sure-btn">创建微博事件分析</a>
</footer>
<#assign type=11>
<#assign productPackageType=4>
<#include "../pay/productPackage.ftl" >
<#include "../../buttom.ftl">
<!--底部 end-->
<script type="text/javascript">
    var type=${type};
    var productPackageType=${productPackageType}
    function showDelete(tickets) {
        $('#msgContent').text("确定要删除吗?");
        $(".zhezhao").show();
        document.getElementById("setTick").value = tickets;
        $("#cancelPOP").css("display","block");

        $(".prompPOP").addClass('scaleShow');
        $(".prompPOP").removeClass('scaleOut');
    }
    function openCh(id){
        var setId = '#open_'+id;
        $(setId).css("display","block");
    }
    $(function(){
        $("#setTime li").on("touchend",function(){
            $("#setTime li").removeClass("click");
            $(this).addClass("click");
            var liTextStr = $(this).attr('value');
            // alert(liTextStr);
            document.getElementById('setTime').value=liTextStr;

        });
    });

    $(function(){
        var tickets = $("#tickets").val();
        if(tickets!=""){
            setTimeout(function(){queryTaskStatus(tickets)},5000);
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
                            location.reload();
                        }else if(this.analysisStatus==5){
                            var getCreateTime = document.getElementById("createTime").value;
                            location.reload();
                        }else if(this.analysisStatus==0){
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
    function preview(obj){
        var tickets =obj;
        window.location.href="${njxBasePath! }/weibo/analysisPreview?tickets="+encodeURIComponent(tickets);
    }
    //明星股票等分类
    function category(type){
        $("#listDiv").html("");
        window.location.href="${njxBasePath! }/shouye.action?myOrder=1&type="+type;
    }
    $("#openjp").on("click",function(){
        if(!"${admin}"){
            window.location.href="indexLocal";
            return;
        }else{
            var userType= "${admin.userType}";
            //alert(userType);
            if(userType!=2){
                $.ajax({
                    url : actionBase + '/checkProValidCount',
                    type : 'POST',
                    success : function(result) {
                        if (!$.isEmptyObject(result)) {
                            if (!result.isPro && result.analysisCount < 1) {
                                $("#buyPrompt_s").hide();
                                $("#buyPrompt").hide();
                                $("#buyTips").empty().text("您的“比一比”次数已用完，如需继续使用，请立即购买！ ");
                                showBuy({type:4});
                            }else{

                                $.ajax({
                                    type:  "get",
                                    url: '${njxBasePath}/weibo/createWeiBoAnalysis?createType=1',
                                    cache:false,
                                    success:function(data){
                                        if(data&&$.trim(data)!=""){
                                            $("#loading_gif").css('display','none');
                                            window.location.href='${njxBasePath}/weibo/createWeiBoAnalysis?createType=1';
                                        }
                                        flag = true;
                                    }
                                });
                            }
                        }
                    }
                });
            }else{

                $.ajax({
                    type:  "get",
                    url: '${njxBasePath! }/weibo/createWeiBoAnalysis?createType=1',

                    cache:false,
                    success:function(data){
                        if(data&&$.trim(data)!=""){
                            $("#loading_gif").css('display','none');
                            window.location.href='${njxBasePath! }/weibo/createWeiBoAnalysis?createType=1';
                        }
                        flag = true;
                    }
                });
            }

        }
    });
    $(".info_close").on("click",function(){
        $(".zhezhao").hide();
        $(".zz_content").hide();
    })
    function changetab(No){
        //alert(No);
        var type = 5;
        if(No==1){
            $("#listDiv").html("");
            $.ajax({
                type:  "get",
                url: 'eventCase.action',
                /*data: {page:page,type:type},*/
                cache:false,
                success:function(data){
                    if(data&&$.trim(data)!=""){
                        $("#loading_gif").css('display','none');
                        $("#listDiv").html(data);
                    }
                    flag = true;
                }
            });
        }else if(No==2){
            $("#listDiv").html("");
            $.ajax({
                type:  "get",
                url: 'weiboTaskList.action',
                cache:false,
                success:function(data){
                    if(data&&$.trim(data)!=""){
                        $("#loading_gif").css('display','none');
                        $("#listDiv").html(data);
                    }
                    flag = true;
                }
            });
        }
    }
    function del(){
        //showMsgInfo(0, '确定要删除吗?', 1);
        // alert("1");
        // $(".submitBtn").one("click",function(){
        var tickets = document.getElementById("setTick").value;
        //  alert(tickets);
        $.ajax({
            url:"${njxBasePath! }/weibo/delTaskStatusC",
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
//        });

    }

    //重新分析
    function restartAnalysis(taskId){

        var params = {"analysisTask.taskId":taskId,"createType":3};
        $.ajax({
            url:"addTask",
            type:"POST",
            data:params,
            success:function(data){
                if(data=="true") {
                    location.reload();
                }else{
                    flag = true;
                    showMsgInfo(0, data, 0);
                }
            }
        });
    }
    //更新
    function updateTask(obj){

        var taskId = document.getElementById("setId").value;
        var setTime = document.getElementById("setTime").value;
        console.log(taskId);

        var params = {"analysisTask.taskId":taskId,"setTime":setTime,"createType":2};

        $.ajax({
            url : "${njxBasePath! }/weibo/addWeiboTask",
            type : "post",
            data : params,
            success : function(result){
                if(result=="true"){
                    hideUpdate();

                    location.reload();
                }else{
                    showMsgInfo(0,result,0);
                }
            }
        });
    }
    function showUpdate(obj){
        $(".taskTimeSegment").hide();
        $('.zhezhao').addClass('downShow');
        $('#other_time').css('display', 'block');
        document.getElementById('setId').value=obj;

    }
    function hideUpdate(){
        $("#other_time").css('display', 'none');
        $('.zhezhao').removeClass('downShow');
    }
</script>
			
