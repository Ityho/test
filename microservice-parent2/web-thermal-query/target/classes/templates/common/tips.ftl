<#--<# page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>-->
<#include "../common/resourcePath.ftl"/>
<#--<%@ taglib prefix="s" uri="/struts-tags"%>-->
<#--<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>-->
<link type="text/css" rel="stylesheet" href="${staticResourcePath}/css/tips.css?v=${sysConfig}">
<script type='text/javascript' src='${staticResourcePath}/js/tips.js?v=${sysConfig}'></script>
<input id="style" name="style" value="0" type="hidden"/>
<div id="BgDiv" style="display: none;"></div>
<div id="SystemSetDiv">
    <iframe style="margin: 0px;" frameborder=0 marginheight=0	marginwidth=0 id="search_set" name="search_set" width="700" height="420" src="">
    </iframe>
</div>
<div id="InfoDiv">
    <div class="td_title rel"><h1>温馨提示</h1><a href="javascript:void(0)" class="info_close abs">×</a></div>
    <div class=td_msg><SPAN id=msg class=msg></SPAN></div>
    <DIV class="btn_div">
        <div class="row clearfix">
            <div class="col-xs-6">
                <a  id=submitBtn class="submitBtn btn btn-warning btn-block">确定</a>
            </div>
            <div class="col-xs-6">
                <a  id=cancelBtn class="cancelBtn btn btn-default btn-block">取消</a>
            </div>
        </div>
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