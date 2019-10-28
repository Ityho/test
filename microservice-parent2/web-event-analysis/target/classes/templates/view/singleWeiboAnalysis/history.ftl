<#include "../../top.ftl">
<link href="${staticResourcePathH5}/js/swa/bootstrap/css/bootstrap.min.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<link href="${staticResourcePathH5}/css/swa/style.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/common.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/singleWeiboAnalysis.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/swa/util.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript">saveOperateLog('微分析历史记录','1054');</script>

<body style="background-color:#dfdfdf;">
<input type="hidden" id="delId" name="id" />
<input type="hidden" id="markType" name="id" />
<section class="section" style="margin-bottom:11px;border-left:solid 6px #fd8c25;" >
	<h2 class="float_l" style="font-size:1.2em;">微博传播效果分析报告</h2>
	<div style="color:#666;font-size:18px;margin-top: 10px;margin-right:15px;" class="float_r">
		<a style="color:#666;" href="${njxBasePath}/analysis">返回</a></div>
</section>
<section class="section">
	<div class="page-container2">
		<div class="content mt15">
			<div class="wrapper980">
				<div class="wrapCon">
					<!--头部导航 start -->
					<!--APP头部导航 end  -->
					<div class="row-fluid">
						<div class="mwbBorder">
							<div class="mwbcon2 Div-list3">
								<ul class="mwblist2">
									<#if fenxiWeibos??>
										<#list fenxiWeibos as weibo>
											<li>
												<div class="mwbcon">
													<#if (weibo.weiboVerifiedType)!0 == 0>
														<div class="m_l v_yellow" style="cursor: pointer;" onclick="javascript:window.open('http://weibo.com/${(weibo.weiboUid)!0}');">
															<img src='${weibo.weiboUserhead!""}' />
														</div>
													<#elseif weibo.weiboVerifiedType >= 1 && weibo.weiboVerifiedType <= 7>
														<div class="m_l v_blue" style="cursor: pointer;" onclick="javascript:window.open('http://weibo.com/${(weibo.weiboUid)!0}');">
															<img src='<s:property value="#weibo.weiboUserhead" />' />
														</div>
													<#else>
														<div class="m_l" style="cursor: pointer;" onclick="javascript:window.open('http://weibo.com/${(weibo.weiboUid)!0}');">
															<img src='${(weibo.weiboUserhead)!""}' />
														</div>
													</#if>
													<div class="m_r">
														<p>
															<a class="mscrame" href="javascript:;" target="_bank" onclick="javascript:window.open('http://weibo.com/${weibo.weiboUid!0}');">${(weibo.weiboNickname)!""}</a>
														</p>
														<div class="mwbcontext">
															<p>${(weibo.weiboContent)!""}</p>
														</div>
														<div class="mfont-buttom">
															<div class="mfont-buttom_l">
																<span style="cursor: pointer;" onclick="javascript:window.open('${(weibo.weiboUrl)!""}');">
																	${weibo.publishedTimeStr} </span>
																<span>分析时间：${weibo.createTimeStr}</span>

															</div>
															<div class="mfont-buttom_r">
																<a class="weibo-multi-panel weibo-list-a">转发(${(weibo.forwardCount)!0})</a> |
																<a class="weibo-multi-panel weibo-list-a">评论(${(weibo.commentCount)!0})</a> |
																<a class="weibo-multi-panel weibo-list-a">赞(${(weibo.praiseCount)!0})</a>
															</div>
															<div class="mfont-buttom_r">
																<#if weibo.payment == 1 || weibo.forwardCount == 0>
																	<a class="link" href="javascript:;" onclick="goAnalysisWeibo(null,${(weibo.taskId)!0}, '${(weibo.weiboUrl)!""}', false,'${(weibo.createTime)!0}')">查看详情</a>
																	<a class="link" href="javascript:;" onclick="goAnalysisWeibo(null,${(weibo.taskId)!0}, '${(weibo.weiboUrl)!""}', true)">重新分析</a>

																<#elseif (weibo.payment != 1 && weibo.forwardCount > 0)>
																	<#if weibo.isExceed == 1>
																		<a class="link" href="javascript:;" onclick="goAnalysisWeibo(null,${(weibo.taskId)!0}, '${(weibo.weiboUrl)!""}', true)">已失效，重新分析</a>
																	</#if>
																	<#if weibo.isExceed != 1>
																		<a class="link" href="javascript:;" onclick="goAnalysisWeibo(null,${(weibo.taskId)!0}, '${(weibo.weiboUrl)!""}', false)">去支付</a>
																	</#if>
																</#if>
																<a class="link" data-toggle="modal" data-target="#checkDelete" onclick="javascript:setParam(${(weibo.taskId)!0},${(weibo.markType)!0})">删除分析</a>
															</div>
														</div>
													</div>
												</div>
											</li>
										</#list>
									</#if>
								</ul>
							</div>
						</div>
					</div>
					<div class="bottomC">
						<a href="javascript:void(0)" class="more2">展开更多 <img src="${staticResourcePathH5}/images/swa/arrow-down2.png?v=${SYSTEM_INIT_TIME}" /></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<!--提示删除弹窗 start-->

<div class=" modal fade modal-dialog" style="margin-top: 100px;border-top:0px solid" id="checkDelete" role="dialog"
	 aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-content" style="width:70%;">
		<div class="popover-title">
			<button type="button" class="close close-sm" data-dismiss="modal"></button>
			<h4 class="modal-title"></h4>
		</div>
		<div class="modal-body align_c">
			<p contenteditable="false">确定删除此条微博分析记录？</p>
		</div>
		<div class="modal-footer">
			<button class="btn btn-primary" onclick="delWeibo()">确定</button>
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
		</div>
	</div>
</div>

<!--提示删除弹窗 end-->

<#assign type=11>
<#include "../../buttom.ftl">
<script src="${staticResourcePathH5}/js/swa/bootstrap/js/bootstrap.min.js?v=${SYSTEM_INIT_TIME}"></script>
</body>
<script type="text/javascript">
    function setParam(taskId,markType){
        $('#delId').val(taskId);
        $('#markType').val(markType);
    }
    // 删除微博分析
    function delWeibo() {
        if ($('#delId').val() > 0) {
            $.ajax({
                url : '${njxBasePath}/weiboAnalysis/delWeibo',
                type : 'POST',
                data : {
                    'markType':$('#markType').val(),
                            'taskId' : $('#delId').val()
        },
            success : function(result) {
                if (result)
                    location.reload(true);
            }
        });
        }
    }
</script>
</html>