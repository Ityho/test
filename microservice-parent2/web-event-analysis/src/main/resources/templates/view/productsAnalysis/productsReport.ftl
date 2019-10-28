
<#include "../../top.ftl" >
<#--<#include "../commonJsp/dwr.ftl" >-->
<#--<script src='${njxBasePath!}/dwr/engine.js'></script>-->
<#--<script src='${njxBasePath!}/dwr/util.js'></script>-->
<script type="text/javascript">saveOperateLog('竞品分析','1024');</script>
<style>
    .checkReport {
        margin-right: 80px;
        margin-top: -30px;
        line-height: 20px;
        font-size: 12px;
        text-align: center;
        float: right;
        width:auto !important;
        border: solid 1px #ccc;
        border-radius: 25px;
        color: #2eb5e6;
        height: 20px;
    }
</style>
<!--主要内容 start-->
<div id="container">
    <section class="section" style="margin-bottom:0px;border-left:solid 6px #fca832;">
        <h2 class="float_l">竞品分析报告</h2>
        <div style="margin-top: 10px;margin-right:20px;" class="float_r">
            <a href="${njxBasePath!}/analysis" class="">返回</a></div>
    </section>
    <input type="hidden" id="tickets" value="" name="tickets"/>
<#if pabList??>
    <section class="section anpanl">
        <article class="context5">
            <div id="reportList">
				<#list newPabList! as item>
                    <script>
                        if("${(item.analysisStatus)!0}"!=0 && "${(item.analysisStatus)!0}"!=5){
                            //$("#tickets").val("11111");
                            var s=$("#tickets").val()
                            if($("#tickets").val()==""){
                                $("#tickets").val('${(item.ticket)!""}')
                            }else{
                                $("#tickets").val($("#tickets").val()+"/"+'${(item.ticket)!""}')
                            }
                            //$("#tickets").val()==""?$("#tickets").val('<s:property value="#item.ticket"/>'):$("#tickets").val($("#tickets").val()+";"+'<s:property value="#item.ticket"/>');
                        }

                    </script>

                    <ul class="list list2"  id="${(item.id)!0}" data-sharecode = "${(item.code)!""}">
                        <li class="rel">
                            <a href="javascript:void(0)" class="topTree" >
                                <input type="hidden" id="href_${(item.id)!0}" value="${(item.monitoringDesc)!""}"/>
                                <img src="${njxBasePath!}/images/application/circleDouble.png" style="float: left; margin-top:-2px; margin-right: 10px; width: 23px; height: 23px;"/>
                                <div class="title" style="text-indent:0px;">
                                    <p class="ta1" style="width:50%;"id="title_${(item.id)!0}" >${(item.title)!""}</p>
                                    <div onclick="showDelete('${(item.id)!0}');"class="delReport" style="margin-top:-20px;">
                                        删除
                                    </div>
                                    <input type="hidden" id="input_${item.id}" value="${item.ticket}" />
                                    <div id="${item.ticket?replace(',', '')}" style="margin-top:-20px;margin-right:20px;" onclick = "analysis(this,'${(item.id)!0}','${(item.hrefUrl)!""}')" class="checkReport">
										<#if item.analysisStatus == 5>
                                            查看

										<#elseif item.analysisStatus == 0>
                                            失败,重新分析
										<#elseif item.analysisStatus == 1>
                                            排队中
										<#else>
                                            分析中
										</#if>
                                    </div>
                                </div>
                                <div class="bottom">${item.createTime?string("yyyy-MM-dd HH:mm")}
                                    <div id="progress_${item.ticket?replace(',', '')}" class="progress  progress-striped active" style="position: absolute;width: 80px; top: 55px; right: 10px; z-index: 2;display:none">
                                        <div id="progress-bar" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                                            <span class="sr-only">40% 完成</span>
                                        </div>
                                    </div>

                                </div>
                            </a>
                        </li>
                    </ul>
				</#list>
            </div>
        </article>
    </section>
</#if>
    <!--我的竟品分析 end-->

    <!--竞品导航 end -->
    <!--底部 start-->
</div>
<footer class="warning-footer">
    <p><i class="icon-plus"></i></p>
    <a onclick="javascript:location.href ='${njxBasePath!}/compet/productsAnalysis';">创建对比分析</a>
</footer>
<#assign type=11>
<div id="shareNewsPOP" class="footPOP" style="display: none;">
    <ul class="footList fenxiang bdsharebuttonbox" data-tag="share_1">
        <li><a class="icon icon_2" data-cmd="tsina"></a>新浪微博</li>
        <li><a class="icon icon_1" data-cmd="weixin"></a>微信</li>
        <li><a class="icon icon_4" data-cmd="sqq"></a>QQ好友</li>
        <li><a class="icon icon_9" data-cmd="tqq"></a>腾讯微博</li>
        <li><a class="icon icon_5" data-cmd="qzone"></a>QQ空间</li>
    </ul>
</div>


<form target="_blank" method="post" action="" id="aForm">
    <input type="hidden" value="" name="reportId">
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

<div class="zhezhao" style="display: none;"></div>
<input type="hidden" id="setReportId">


<#assign productPackageType=6>
<#include "../pay/productPackage.ftl" >
<!--竞品分析功能过期提示弹窗 end-->
<script src='${staticResourcePath!}/js/findResult.js'></script>
<#--<script src='${njxBasePath!}/dwr/interface/NewsOperate.js'></script>-->
<script>

    $(function() {
        //监测方案选中效果
        $(".keyWordList > ul > li").on("click",function(){
            $(this).toggleClass("active");
        });
        function isEq(list,str){
            for(var i = 1;i<list.length;i++){
                if(list[i].value == str && $.trim(str)!= ""){
                    return true;
                }else{
                    isEq(list,list[i].value);
                }
            }
        }
    });
    var flag = true;

</script>
<script type="text/javascript">
    $(function(){
        //分享
        var bdShareTitle, bdShareDesc, bdShareUrl;
        $(".icon-share").on("click",function(){
            $("#createTime").val($(this).find("input").val());
            $("#title").val($(this).data("title"));
            $(".zhezhao").fadeIn(300);
            $("#shareNewsPOP").fadeIn(300);
            $(".footPOP").addClass('downShow');
            $(".footPOP").removeClass('downOut');
            $(".prompPOP").removeClass('scaleShow');
            $(".prompPOP").addClass('scaleOut');
            var shareCode = $(this).parents("ul").data("sharecode");
            console.log(shareCode);
            $(".footPOP a").off("click");
            shareReportCallBack(shareCode);
            //shareTo("${admin.userId}",$(this).data("id"));

        });
        var tickets = $("#tickets").val();
        if(tickets!=""){
            queryTaskStatus(tickets);
        }
    });
    //分享的beforeclick事件
    function bdShareBeforeClick(cmd,config) {
        config.bdText = bdShareTitle;
        config.bdDesc = bdShareDesc;
        config.bdUrl = bdShareUrl;

        return config;
    }
    function productsAnalysisChatLook(id){
        $("#pForm").find("input").val(id);
        $("#pForm").attr("action","productsAnalysisChatLook.action");
        $("#pForm").submit();
    }
    function shareReportCallBack(data){
        var createTime = $("#createTime").val();
        var url = "";
        if(new Date(createTime).getTime()<= new Date('2016-06-1 18:00:00').getTime()){
            url = $("#viewPath").val()+$("#filePath").val()+".html";
        }else if(new Date(createTime).getTime()<= new Date('2016-06-30 18:00:00').getTime()){
            url = $("#viewPath").val()+$("#filePath").val()+"_h5.html";
        }else{
            url=$("#viewUri").val()+"/lookShareCodeReport.action?shareCode=" + data;
        }
        bdShareTitle = $("#title").val()+"比一比，来看看你关注的TA有多牛掰";
        bdShareDesc = '自定义分享摘要';
        bdShareUrl = url;

        window._bd_share_config = {
            common : {
                onBeforeClick : bdShareBeforeClick
            },
            share : []
        }
        with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
    }

    function showDelete(id){
        $(".zhezhao").show();
        document.getElementById("setReportId").value = id;
        $("#cancelPOP").css("display","block");

        $(".prompPOP").addClass('scaleShow');
        $(".prompPOP").removeClass('scaleOut');
    }
    //删除
    function del(){
        var getId = document.getElementById("setReportId").value;
        console.log(getId);
        $.ajax({
            url:"${njxBasePath!}/compet/delTaskStatusR",
            type:"POST",
            data:{
                "reportId":getId,
            },
            success:function(data){
                if(data==true) {
                    window.location.reload();
                }
            }
        });
    }
    function queryTaskStatus(tickets){
        console.log("---queryTaskStatus");
        $.ajax({
            url:"queryTaskInfo",
            type:"post",
            data:{"ticket":tickets},
            success:function(str){
                var data=JSON.parse(str);
                console.log(data);
                if(data){
                    var list = data.solidifyList;
                    $.each(list,function(){
                        var ticket = this.ticket.replace(",","");
                        if(this.analysisStatus==2||this.analysisStatus==3||this.analysisStatus==1 || this.analysisStatus==4){
                            var percent = (parseFloat(this.schedulePercent)*100).toFixed(1)+"%";
                            console.log("percent="+percent+"#"+ticket);
                            $("#"+ticket).text("分析中");
                            //$("#progress_"+ticket).width((parseFloat(this.schedulePercent)*100).toFixed(1));
                            $("#progress_"+ticket).val(percent);
                            $("#progress_"+ticket).show();
                            /* var tr = document.getElementById(ticket);
                            $(tr).find(".badge").addClass("blue").html('<i class="icon-reload-a rotate360 dis-i"></i>分析中');
                            $(tr).find("td:last").removeClass("link").html('<div class="progress rel"><span style="position: absolute; top: 0; left: 45%;">'
                            +percent+'</span><span style="width: '+percent+';" class="progressLine">&nbsp;</div>'); */

                        }else if(this.analysisStatus==5){
                            $("#"+ticket).text("查看");
                            $("#progress_"+ticket).hide();
//                         	$.ajax({
//                                 url:"saveProductsAnalysisShare.action",
//                                 type:"post",
//                                 data:{"pabId":this.id},
//                                 success:function(data){
                            location.reload();
//                                 }
//                             })
                        }else if(this.analysisStatus==0){
                            $("#"+ticket).text("失败,重新分析");
                            $("#progress_"+ticket).hide();
                            location.reload();
                        }

                    });

                    if($("#tickets").val()!=""){
                        setTimeout(function(){
                            queryTaskStatus(tickets)
                        },3000);
                    }
                }

            }
        });
    }
    function analysis(object,id,hrefUrl){
        if(object.innerText=="失败,重新分析"){
            againAnalysis(id)
        }else if(object.innerText=="查看"){
            window.location.href=hrefUrl;
        }
    }
    var reanalyseNum = 0;
    function againAnalysis(id){
        if(reanalyseNum == 0) {
            reanalyseNum = 1;
            var ticket=$("#input_"+id).val();
            var newTicket= ticket.replace(",","");
            $("#"+newTicket).text("分析中");
            //$("#progress_"+ticket).width((parseFloat(this.schedulePercent)*100).toFixed(1));
            $("#progress_"+newTicket).show();
            $.ajax({
                url: "productsAnalysisAgain.action",
                type: "post",
                data: {
                    "pabId": id,
                },
                success: function (data) {
                    var obj = JSON.parse(data);
                    reanalyseNum = 0;
                    if("0000" == obj.code){
                        location.reload();
                    }else{
                        console.log(obj.msg);
                        //showMsgInfo(0,data.msg,0);
                    }
                }
            });
        }
    }
</script>
<#include "../../buttom.ftl" >
</body>
</html>
