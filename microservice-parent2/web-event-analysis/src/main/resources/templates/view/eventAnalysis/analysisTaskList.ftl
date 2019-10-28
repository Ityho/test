<!--底部 start--><%@ page language="java" pageEncoding="gbk"%><% request.setAttribute("page_title",""); %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/top.jsp" %>

<link rel="stylesheet" href="<%=staticResourcePathH5 %>/css/reset.css?v=<%=SYSTEM_INIT_TIME %>" />
<script type="text/javascript">saveOperateLog('事件分析任务列表页','1012');</script>
<!--主要内容 start-->
<div id="container">
	<section class="section">
		<article class="context context2">
			<ul class="menuList-ul menuList-ul-three menuList-ul-three-big menuList-ul-colorOne" id="Allnetwork">
				<li onclick="location.href= njxBasePath + '/eventCase.action'"><i class="icon-box"></i><span>经典案例</span></li>
				<li class="active"><i class="icon-bar-graph-2" onclick="location.href= njxBasePath + '/view/eventAnalysis1/taskList.action'"></i><span>我的分析</span></li>
				<li><i class="icon-paper" onclick="location.href= njxBasePath + '/view/eventAnalysis1/completeList.action'"></i><span>我的报告</span></li>
			</ul>
		</article>
	</section>

	<%-- <section class="section">
		<article class="bottom bottom2">
			<a id="openjpUp"><i class="icon-plus"></i>我有事件要分析</a>
		</article>
	</section> --%>
	<section class="section2 rel">
		<article class="context">

			<ul>
				<li class="g_12">
					<div class="con con_1">
						<span class="tit tit5 float_l"><strong><i class="icon-ribbon f_c1"></i>我的分析</strong></span>
					</div>

				</li>
			</ul>

		</article>

	</section>

	<input type="hidden" id="tickets" value=""/>
	<input type="hidden" id="tickets2" value=""/>

	<script>
		$("#tickets").val("");
		$("#setCreateTime").val("");
	</script>
	<s:iterator value="#attr.taskList" status="st" var="task">
		<section class="section analysisList rel" >
			<s:if test="#task.analysisStatus==1">
				<input type="hidden" name="weifenxi" value="1">
				<tr id="<s:property value="#task.taskTicket"/>">
				<input type="hidden" value="<s:property value="#task.taskId"/>"/>
				<div class="conhead">
					<img src="<%=njxBasePath %>/images/userlogo/icon_fx.jpg" class="u_logo">
					<span>
					<p class="t1"><a href="#"><s:property value="#task.incidentTitle"/></a></p>
					<p class="t2"><s:date name="#task.startTime" format="yyyy-M-d"/> — <s:date name="#task.endTime" format="yyyy-M-d"/></p>
				</span>
				</div>
				<td><span class="badge">未分析</span> </td>
				<td>
					<div class="padding8">
						<div class="progress  progress-striped active" style="display: none;">
							<div id="setSchedule"class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <s:property value="#task.schedule*100"/>%; " >
							<input type="hidden" id="schedule" value=${task.schedule*100}>
						</div>
					</div>
</div>
</td>
<td class="link">
	<!--  <a href="javascript:showEventFrame(3,'<s:property value='#task.taskId'/>');">详情</a>-->
	<%--|<a href="#">删除</a> |
	<a href="javascript:showEventFrame(2,'<s:property value='#task.taskId'/>');">更新</a>--%>
</td>
</tr>
<script>
	$("#tickets").val()==""?$("#tickets").val('<s:property value="#task.taskTicket"/>'):$("#tickets").val($("#tickets").val()+","+'<s:property value="#task.taskTicket"/>');
</script>
</s:if>
<s:elseif test="#task.analysisStatus==2||#task.analysisStatus==3||#task.analysisStatus==4">
	<tr id="<s:property value="#task.taskTicket"/>">
	<input type="hidden" value="<s:property value="#task.taskId"/>"/>
	<div class="conhead">
		<img src="<%=njxBasePath %>/images/userlogo/icon_fx.jpg" class="u_logo">
		<span>
					<p class="t1"><a href="#"><s:property value="#task.incidentTitle"/></a></p>
					<p class="t2"><s:date name="#task.startTime" format="yyyy-M-d"/> — <s:date name="#task.endTime" format="yyyy-M-d"/></p>
							<input id="createTime"type="hidden" value = "<s:date name="#task.createTime" format="yyyyMMdd"/>" >
				</span>
	</div>
	<td><span class="badge blue">分析中</span> </td>
	<td>

		<div class="padding8">
			<div class="progress  progress-striped active">
				<div id="setSchedule"class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: <s:property value="#task.schedule*100"/>%;">
				<input type="hidden" id="schedule" value=${task.schedule*100}>
			</div>
		</div>
		</div>

	</td>
	</tr>
	<script>
		$("#tickets").val()==""?$("#tickets").val('<s:property value="#task.taskTicket"/>'):$("#tickets").val($("#tickets").val()+","+'<s:property value="#task.taskTicket"/>');
	</script>
</s:elseif>
<s:elseif test="#task.analysisStatus==5">
	<tr id="<s:property value="#task.taskTicket"/>">
	<input type="hidden" value="<s:property value="#task.taskId"/>"/>
	<div class="conhead">
		<img src="<%=njxBasePath %>/images/userlogo/icon_fx.jpg" class="u_logo">
		<span>
					<p class="t1"><a href="#"><s:property value="#task.incidentTitle"/></a></p>
					<p class="t2"><s:date name="#task.startTime" format="yyyy-M-d"/> — <s:date name="#task.endTime" format="yyyy-M-d"/></p>
					<input id="createTime"type="hidden" value = "<s:date name="#task.createTime" format="yyyyMMdd"/>" >
					<input type="hidden" name="analysisTask.setTime" id="setTime" value="3">
					<input id="keyword_<s:property value="#task.taskId"/>"type="hidden" value = "<s:property value="#task.keyword"/>" >
					<input id="filterKeyword_<s:property value="#task.taskId"/>"type="hidden" value = "<s:property value="#task.filterKeyword"/>" >

				</span>
	</div>
	<!--
    <div class="contentBox" id="introduce">
        <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px">
    </div>-->
	<td><span class="badge green">分析完成</span> </td>
	<td  class="link">
		<a href="javascript:void(0);" onclick="preview('<s:property value='#task.taskTicket'/>');">查看</a> |
		<a href="javascript:void(0);" onclick="del('<s:property value='#task.taskTicket'/>');">删除</a> |
		<div class="rel" style="display: inline-block;">
			<!--任务更新弹出框 start-->

			<!--任务更新弹出框 end-->
			<a class="updateA" href="javascript:void(0)" onclick="showUpdate(<s:property value="#task.taskId"/>);">更新</a>

		</div>
	</td>
	</tr>
	<!--
    <script>
        $("#tickets2").val()==""?$("#tickets2").val('<s:property value="#task.taskTicket"/>'):$("#tickets2").val($("#tickets2").val()+","+'<s:property value="#task.taskTicket"/>');

    </script>-->
</s:elseif>
<s:elseif test="#task.analysisStatus==0">
	<tr id="<s:property value="#task.taskTicket"/>">
	<input type="hidden" value="<s:property value="#task.taskId"/>" />
	<div class="conhead">
		<img src="<%=njxBasePath %>/images/userlogo/icon_fx.jpg" class="u_logo">
		<span>
					<p class="t1"><a href="#"><s:property value="#task.incidentTitle"/></a></p>
					<p class="t2"><s:date name="#task.startTime" format="yyyy-M-d"/> — <s:date name="#task.endTime" format="yyyy-M-d"/></p>
					<input type="hidden" name="analysisTask.setTime" id="setTime" value="3">


				</span>
	</div>
	<td><span class="badge red">分析失败</span> </td>

	<td  class="link" id="submitBtn">
		<a href="javascript:restartAnalysis('<s:property value='#task.taskId'/>');">重新分析</a>

		<div class="rel" style="display: inline-block;">
			<!--任务更新弹出框 start-->
			<!--
            <div id="other_time" class="zz_content" style="display: none; left: 5%; top: 10%; width: 90%; max-height: 90%; position: fixed;">
                <div class="td_title rel">
                    <h1>提示</h1>
                        <a onclick="javascript:$('.zhezhao').fadeOut('slow');$('#buyPrompt_jp').css('display', 'none');" class="info_close abs">×</a>
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
                <div class="form-actions align_c">
                 <input type="hidden" id="setId" value="">
                    <button class=" button" onclick="updateTask('<s:property value="#task.taskId"/>')">更新</button>
                    <button class=" button button5" onclick="hideUpdate()">取消</button>
                </div>
            </div>-->

			<!--任务更新弹出框 end-->


		</div>
	</td>
	</tr>
</s:elseif>
</section>
</s:iterator>




<section class="section">
	<article  class="listMore">
		<ul class="list">
			<li class="rel" style="height: 16px;">

				<s:if test="#attr.admin.userType != 2">
					<a href="javascript:void(0)" class="topTree">
	                     <span class="float_l"><p class="ta1"><font class="">您的事件分析剩余次数：
	                     <s:if test="#attr.admin.userAnalysisValidCount < 0">
	                     	</font><font class="f_c3">0</font></p></span>
				</s:if>
				<s:else>
					</font><font class="f_c3">${admin.userAnalysisValidCount}</font></p></span>
				</s:else>
				</a>
				<input type="hidden" name="analysisNum" id="analysisNum" value="${admin.userAnalysisValidCount}"/>
				<a href="javascript:showBuy({type:3})" class="button default abs" style="right: 0px; top: 0;">购买</a>
				</s:if>
			</li>
		</ul>
	</article>
	<!--
    <article class="bottom bottom2">
        <a id = "openjp" ><i class="icon-plus"></i>我有事件要分析</a>
    </article>-->
</section>

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
	<div class="control-group" id="expectTime">
		<label class="control-label">预计时间：</label><span>&nbsp;</span>
		<div class="controls">
			<input type="hidden" value="" name="analysisTask.analysisTotalConsumeExpect" id="analysisTotalConsumeExpect">
		</div>
	</div>
	<div class="control-group" id="expectNum">
		<label class="control-label">预计数量：</label><span>&nbsp;</span>
		<div class="controls">
			<input type="hidden" value="" name="analysisTask.analysisSolrFirstCountExpect" id="analysisSolrFirstCountExpect">
		</div>
	</div>
	<div class="form-actions align_c" >
		<input type="hidden" id="setId" value="">
		<button class=" button" onclick="updateTask(this)" id="senior_set">更新</button>
		<button class=" button button5" onclick="hideUpdate()">取消</button>
	</div>
</div>

<div class="zhezhao" style="display: none;"></div>

<!--消息弹出框 -->
<div id="msgPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
	<div class="prConBox">
		<div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

		<div class="PromptCon" id="msgContent" style="text-align: center;"></div>

		<div class="bottom"><a style=" float: initial" class="submitBtn " onclick="hideUpdate()" style="text-align: center;">确定</a></div>
	</div>
</div>
<!--我要分析弹窗 end -->
<footer class="warning-footer">
	<p><i class="icon-plus"></i></p>
	<a id="openjp">创建全网事件分析</a>
</footer>
<c:set var="type" value="15"></c:set>
<%-- <%@ include file="../bottomNav.jsp" %> --%>
<c:set var="productPackageType" value="3"></c:set>
<%@ include file="../pay/productPackage.jsp" %>
<!--底部 end-->
<script type="text/javascript">

	$(function(){
		$("#setTime li").on("touchend",function(){
			$("#setTime li").removeClass("click");
			$(this).addClass("click");
			var liTextStr = $(this).attr('value');
			document.getElementById('setTime').value=liTextStr;
			expectTask();
		});
	});

	$(function(){
		var tickets = $("#tickets").val();
		if($("input[name='weifenxi']").length)
			setTimeout(function(){location.reload()},5000);
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
							// location.reload();
						}else if(this.analysisStatus==5){
							var getCreateTime = document.getElementById("createTime").value;

							/*var tickets = $("#tickets").val().split(taskId).join();
                            $("#tickets").val(tickets.replace(",,",","));
                            var tr = document.getElementById(taskId);
                            $(tr).find(".badge").text("分析完成").removeClass("blue").addClass("green");
                            $(tr).find("td:eq(1)").css("cursor","pointer");
                            $(tr).find("td:eq(1)").bind("click",showEventFrame(3,this));*/
							// alert("完成");
							//alert(taskId+"+"+getCreateTime);
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
						}else if(this.analysisStatus==1){
							/*var tickets = $("#tickets").val().split(taskId).join();
                            $("#tickets").val(tickets.replace(",,",","));
                            var tr = document.getElementById(taskId);
                            $(tr).find(".badge").text("分析失败").removeClass("blue").addClass("red");
                            $(tr).find("td:last").addClass("link").html('<a href="javascript:showEventFrame(3,"<s:property value='#task.taskId'/>");">详情</a> |'
                            +'<a href="javascript:void(0);" onclick="del(this);">删除</a> |'
                            +'<a href="javascript:showEventFrame(2,"<s:property value="#task.taskId"/>");">更新</a>');*/
							//location.reload();
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


		$("#aForm").find("input").val(tickets);
		$("#aForm").attr("action",'<%=njxBasePath %>/view/eventAnalysis1/analysisPreview.action');
		$("#aForm").submit();

	}
	// 购买竞品分析次数
	function buyEventAnalysis() {
		//var packageNum = $('#packageNum').val();


		//if (packageNum > 0) {
		var analysisForm = $('form[name="analysisForm"]')[0];
		if (analysisForm)
			analysisForm.submit();
		//}
	}
	//明星股票等分类
	function category(type){
		//$("#type").val(type);
		//$("#page").val(1);
		$("#listDiv").html("");

		window.location.href="<%=njxBasePath %>/shouye.action?myOrder=1&type="+type;
	}
	$("#openjp").on("click",function(){
		//$(".zhezhao").show();
		// $("#buyPrompt").show();

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
								showBuy({type:3});
							}else{
								$(".zhezhao").show();
								$.ajax({
									type:  "get",
									url: '<%=njxBasePath %>/view/eventAnalysis/createAnalysis.action',

									cache:false,
									success:function(data){
										if(data&&$.trim(data)!=""){
											$("#loading_gif").css('display','none');
											window.location.href='<%=njxBasePath %>/view/eventAnalysis/createAnalysis.action?createType=1';
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
					url: '<%=njxBasePath %>/view/eventAnalysis/createAnalysis.action',

					cache:false,
					success:function(data){
						if(data&&$.trim(data)!=""){
							$("#loading_gif").css('display','none');
							window.location.href='<%=njxBasePath %>/view/eventAnalysis/createAnalysis.action?createType=1';
						}
						flag = true;
					}
				});
			}
		}
	});
	//热搜过来的
	if("${param.HeatValue}"){
		$("#openjp").click();
	};
	$("#openjpUp").on("click",function(){
		//$(".zhezhao").show();
		// $("#buyPrompt").show();

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
								showBuy({type:3});
							}else{
								$(".zhezhao").show();
								$.ajax({
									type:  "get",
									url: '<%=njxBasePath %>/view/eventAnalysis/createAnalysis.action',

									cache:false,
									success:function(data){
										if(data&&$.trim(data)!=""){
											$("#loading_gif").css('display','none');
											window.location.href='<%=njxBasePath %>/view/eventAnalysis/createAnalysis.action?createType=1';
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
					url: '<%=njxBasePath %>/view/eventAnalysis/createAnalysis.action',

					cache:false,
					success:function(data){
						if(data&&$.trim(data)!=""){
							$("#loading_gif").css('display','none');
							window.location.href='<%=njxBasePath %>/view/eventAnalysis/createAnalysis.action?createType=1';
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

	//删除
	function del(obj){
		var tickets = obj;
		$.ajax({
			url:"<%=njxBasePath %>/view/eventAnalysis/delTaskStatus.action",
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

	}

	//重新分析
	function restartAnalysis(taskId){

		var params = {"analysisTask.taskId":taskId,"createType":3};
		$.ajax({
			url:"<%=njxBasePath %>/view/eventAnalysis/addTask.action",
			type:"POST",
			data:params,
			success:function(data){
				if(data) {
					location.reload();
				}else{
					flag = true;
					alertMsg(data);
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
			url : "<%=njxBasePath %>/view/eventAnalysis/addTask.action",
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
		$('.zhezhao').addClass("downShow");
		$('#other_time').css('display', 'block');

		document.getElementById('setId').value=obj;

	}
	function hideUpdate(){
		$("#other_time").css('display', 'none');
		$('.zhezhao').removeClass("downShow");
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
	function unClick(){
		isCreate = false;
		$("#senior_set").attr("disabled", "true");

	}
	function openClick(){
		isCreate = true;
		$("#senior_set").removeAttr("disabled");
	}
	var createParmas = "";
	var createFlag  = 0;
	var isCreate = true;
	function expectTask(){
		var setTime = document.getElementById("setTime").value;
		var taskId = document.getElementById("setId").value;
		var params = $("#keyword").val()+"_"+$("#filterKeyword").val()+setTime;
		var getKeyword = "keyword_"+taskId;
		var getFilterkeyword = "filterKeyword_"+taskId;
		var keyword = document.getElementById(getKeyword).value;
		var filterKeyword = document.getElementById(getFilterkeyword).value;

		if(createParmas==params){
			return;
		}
		createParmas=params;
		createFlag = 0;
		$("#expectTime span").last().html('<img src=\"<%=njxBasePath%>/images/loading_c.gif\" width="15">');
		$("#expectNum  span").last().html('<img src=\"<%=njxBasePath%>/images/loading_c.gif\" width="15">');

		$.ajax({
			url : "<%=njxBasePath %>/view/eventAnalysis/expectTask.action",
			type : "post",
			data : {keyword: keyword,filterKeyword:filterKeyword,'analysisTask.Time':setTime},
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
					$("#expectNum").show();
					openClick();
				}
			}
		})

	}
</script>
<%@ include file="../../buttom.jsp" %>
