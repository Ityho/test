
<!--取消监测弹出框 -->
<div id="cancelKeywordPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
	<div class="prConBox">
		<div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

		<div class="PromptCon" style="text-align: center;">确定取消监测吗？</div>

		<div class="bottom">
			<a href="javascript:;" onclick="doCancelKeyword()" class="submitBtn">确定</a>
			<a href="javascript:;" class="submitBtn cancelFot">取消</a>
		</div>
	</div>
</div>

<!--已有相同监测的弹出框 -->
<div id="existsKeyWordPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
	<div class="prConBox">
		<div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

		<div class="PromptCon" style="text-align: center;">该热门内容已监测，是否继续？</div>

		<div class="bottom">
			<a href="javascript:;" onclick="pOrdering()" class="submitBtn">是</a>
			<a href="javascript:;" class="submitBtn cancelFot">否</a>
		</div>
	</div>
</div>

<div id="subPOP" class="prompPOP prompBlackPOP" style="display: none; width: 80%; left: 10%; top: 30%;">
	<div class="prConBox">
		<div class="icon-ok"></div>
		<div class="PromptCon" style="text-align: center;">
			<p>监测成功</p>
			<p>新信息会通过私信密送给殿下，闲暇之余要来看看哦～</p>
		</div>
	</div>
</div>

<!--提示去修改监测或者关闭推送-->
<div id="markPOP" class="mark" style="display: none;">
	<a href="javascript:void(0)" class="cancel">×</a>
	<div class="m_1 abs"></div>
</div>
<!--屏蔽信息失败提示 -->
<div id="cancleFalsePOP" class="prompPOP prompBlackPOP"  style="display: none; width: 50%; left: 25%;">
	<div class="prConBox">
		<div class="icon-false"></div>
		<div class="PromptCon" style="text-align: center;">信息屏蔽失败！</div>
	</div>
</div>

<!--收藏信息失败提示 -->
<div id="collectFalsePOP" class="prompPOP prompBlackPOP" style="display: none; width: 50%; left: 25%;">
	<div class="prConBox">
		<div class="icon-false"></div>
		<div class="PromptCon" style="text-align: center;">失败！</div>
	</div>
</div>

<!--无监测数量提示购买弹出框 -->
<div id="buyPOP" class="prompPOP" style="display: none;">
	<div class="prConBox">
		<div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

		<div class="PromptCon">对不起，你的可用监测方案已经用光，补充监测方案，监测更多信息。</div>

		<div class="bottom">
			<a href="#" class="submitBtn cancelFot">放弃监测</a>
			<a onclick="doOrder()" class="submitBtn">49元，去监测</a>
		</div>
	</div>
</div>
<!--取消监测弹出框 -->
<div id="cancelKeywordPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
	<div class="prConBox">
		<div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

		<div class="PromptCon" style="text-align: center;">确定取消监测吗？</div>

		<div class="bottom">
			<a href="javascript:;" onclick="doCancelKeyword()" class="submitBtn">确定</a>
			<a href="javascript:;" class="submitBtn cancelFot">取消</a>
		</div>
	</div>
</div>
<#include "../../buttom.ftl">