<#include "../../top.ftl">

<link rel="stylesheet" href="${staticResourcePathH5}/css/reset.css?v=${SYSTEM_INIT_TIME}" />
<script type="text/javascript" src="${staticResourcePathH5}/js/jquery-2.1.1.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript">saveOperateLog('事件分析报告页','1032');</script>
<!--主要内容 start-->
<div id="container">
    <section class="section">
        <h2 class="float_l" style="border-left:solid 6px #fd8c25;">全网事件分析报告</h2>
        <div style="margin-top: 10px;margin-right:15px;" class="float_r">
            <a href="${njxBasePath}/analysis">返回</a></div>
    </section>
    <input type="hidden" id="tickets" value=""/>
    <script>
        $("#tickets").val("");
    </script>

    <#list reportList!  as task>
        <section class="section analysisList rel" >
            <tr id="${task.taskTicket!}">
                <input type="hidden" value="task.taskId"/>
                <div ><img src="${njxBasePath}/images/application/circleDouble.png" style="float: left; margin-top:12px; margin-right: 10px; width: 15px; height: 15px;"/></div>
                <span>
                <p class="t1" style="width:50%;font-size:16px;line-height:40px;height:40px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">
                    ${task.incidentTitle}
                </p>
                <div id="${task.taskId!}" style="display:block;">
                    <div onclick="showDelete('${task.taskTicket}');"class="delReport">删除</div>
                    <div onclick="preview('${task.taskTicket}');"class="checkReport">查看 </div>
                </div>
                <p class="t2" style="margin-left:20px;color:#c5c5c5;height:30px;line-height:30px;">
                 ${(task.createTime?string("yyyy-M-d"))!}
                </p>
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

<!--我要分析弹窗 end -->
<!--底部 start-->
<footer class="warning-footer" style="box-shadow: none; padding-top: 5px;">
    <p><i class="icon-plus"></i></p>
    <a onclick="javascript:location.href ='${njxBasePath}/createAnalysis?createType=1';" class="sure-btn">创建全网事件分析</a>
</footer>
<#--<c:set var="type" value="11"></c:set>-->
<#--<c:set var="productPackageType" value="3"></c:set>-->
<#--<#include "../pay/productPackage.ftl" > 用户相关，以后要加的-->
<!--底部 end-->
<script type="text/javascript">
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



        // alert(schedule);

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
                            /*
                            var percent = (parseFloat(this.analysisSchedulePercent)*100).toFixed(1)+"%";
                            var tr = document.getElementById(taskId);
                            //$(tr).find(".progressLine").css("width",percent).text(percent);
                            $(tr).find(".badge").text("分析中").addClass("blue");
                            $(tr).find("td :last").removeClass("link").html('<div class="progress rel"><span style="position: absolute; top: 0; left: 45%;">'
                                +percent+'</span><span style="width: '+percent+';" class="progressLine">&nbsp;</div>');*/
                            location.reload();
                        }else if(this.analysisStatus==5){
                            var getCreateTime = document.getElementById("createTime").value;

                            /*var tickets = $("#tickets").val().split(taskId).join();
                            $("#tickets").val(tickets.replace(",,",","));
                            var tr = document.getElementById(taskId);
                            $(tr).find(".badge").text("分析完成").removeClass("blue").addClass("green");
                            $(tr).find("td:eq(1)").css("cursor","pointer");
                            $(tr).find("td:eq(1)").bind("click",showEventFrame(3,this));*/
                            // alert("完成");
                            //   alert(taskId+"+"+getCreateTime);
                            $.ajax({
                                url:"getContentresult.action",
                                type:"post",
                                data:{"tickets":taskId ,"createTime":getCreateTime},
                                success:function(data){

                                    alert(data);


                                }
                            });
                            location.reload();
                        }else if(this.analysisStatus==0){
                            /*var tickets = $("#tickets").val().split(taskId).join();
                            $("#tickets").val(tickets.replace(",,",","));
                            var tr = document.getElementById(taskId);
                            $(tr).find(".badge").text("分析失败").removeClass("blue").addClass("red");
                            $(tr).find("td:last").addClass("link").html('<a href="javascript:showEventFrame(3,"<s:property value='#task.taskId'/>");">详情</a> |'
                            +'<a href="javascript:void(0);" onclick="del(this);">删除</a> |'
                            +'<a href="javascript:showEventFrame(2,"<s:property value="#task.taskId"/>");">更新</a>');*/
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
        window.location.href="${njxBasePath}/analysisPreview?tickets="+encodeURIComponent(tickets);
    }
    //明星股票等分类
    function category(type){
        //$("#type").val(type);
        //$("#page").val(1);
        $("#listDiv").html("");

        window.location.href="${njxBasePath}/shouye.action?myOrder=1&type="+type;
    }
    $("#openjp").on("click",function(){
        //$(".zhezhao").show();
        // $("#buyPrompt").show();

        if(!"${admin!}"){
            window.location.href="/indexLocal";
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
                                showBuy({type:3});
                            }else{
                                $(".zhezhao").show();
                                $.ajax({
                                    type:  "get",
                                    url: '${njxBasePath}/view/eventAnalysis/createAnalysis.action',

                                    cache:false,
                                    success:function(data){
                                        if(data&&$.trim(data)!=""){
                                            $("#loading_gif").css('display','none');
                                            window.location.href='${njxBasePath}/view/eventAnalysis/createAnalysis.action?createType=1';
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
                    url: '${njxBasePath}/view/eventAnalysis/createAnalysis.action',

                    cache:false,
                    success:function(data){
                        if(data&&$.trim(data)!=""){
                            $("#loading_gif").css('display','none');
                            window.location.href='${njxBasePath}/view/eventAnalysis/createAnalysis.action?createType=1';
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
                url: 'taskList.action',
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
    //删除
    function del(){
        //showMsgInfo(0, '确定要删除吗?', 1);
        // alert("1");
        // $(".submitBtn").one("click",function(){
        var tickets = document.getElementById("setTick").value;
        $.ajax({
            url:"${njxBasePath}/delTaskStatusC",
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
            url:"addTask.action",
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
            url : "addTask.action",
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
        $('.zhezhao').fadeIn('slow');
        $('#other_time').css('display', 'block');
        document.getElementById('setId').value=obj;

    }
    function hideUpdate(){
        $("#other_time").css('display', 'none');
        $('.zhezhao').fadeOut('slow');
    }
</script>
<#include "../../buttom.ftl" >
