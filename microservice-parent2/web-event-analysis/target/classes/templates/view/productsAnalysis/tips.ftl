<#include "../../frame_top.ftl">
<link rel="stylesheet" href="${staticResourcePath}/css/tips.css">
<script src='${staticResourcePath}/js/productsAnalysis/tips.js'></script>

<input id="style" name="style" value="0" type="hidden"/>
<div id="BgDiv" style="display: none"></div>
<div id="SystemSetDiv">
		<iframe style="margin: 0px;" frameborder=0 marginheight=0	marginwidth=0 id="search_set" name="search_set" width="700" height="420" src="">
		</iframe>
</div>
<div id="InfoDiv">
    <div class="td_title rel"><h1>温馨提示</h1><a href="#" class="info_close abs">×</a></div>
      <div class=td_msg><SPAN id=msg class=msg></SPAN></div>
      <DIV class="btn_div">
       <a  id=submitBtn class="submitBtn button float_l">确定</a>
       <a  id=cancelBtn class="cancelBtn button float_r">取消</a>
      </DIV>
    
</div>
<div id="SmsDiv">
		<iframe scrolling="no" style="" frameborder=0
			marginheight=0 marginwidth=0 id="send_sms_frame" width="100%" 
			 height="100%" src=""> </iframe>
</div>
<div id="EditBSDiv">
		<iframe scrolling="no" style="" frameborder=0
			marginheight=0 marginwidth=0 id="edit_bs_frame" width="100%" 
			 height="100%" src=""> </iframe>
</div>