<#--<%@ page language="java" pageEncoding="gbk"%>-->
<#--<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>-->
<#--<%@ taglib prefix="s" uri="/struts-tags"%>-->
<#--<c:set var="ctx" value="${pageContext.request.contextPath}" />-->
<head>
   <#include "../common/top.ftl"/>
    <link href="${staticResourcePath}/css/openTools/openTools.css" rel="stylesheet" type="text/css">
    <link href="${staticResourcePath}/css/indexV4/base/css/less/w-site.css" rel="stylesheet" type="text/css">
    <script src="${staticResourcePath}/js/switchery.min.js" charset="utf-8"></script>
    <script src='${staticResourcePath}/js/engine.js'></script>
    <script src='${staticResourcePath}/js/swa/util.js'></script>
<#--<script src='${staticResourcePath}/dwr/interface/EChartsDwr.js'></script>-->
<#--<script src='${staticResourcePath}/dwr/interface/HeatReportDwr.js'></script>-->
    <script src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
    <script src="${staticResourcePath}/js/WeChatShareConfig.js"></script>

    <!--echart js -->
    <script type="text/javascript"
            src="${staticResourcePath}/js/echarts3.3.1/echarts.min.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="${staticResourcePath}/js/echarts3.3.1/dark.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="${staticResourcePath}/js/echarts/echarts-liquidfill.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="${staticResourcePath}/js/echarts3.3.1/map/china.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="${staticResourcePath}/js/echarts3.3.1/theme/infographic.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="${staticResourcePath}/js/wordcloud/dist/echarts-wordcloud.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="${staticResourcePath}/js/common/common-checkKeyword.js"></script>
    <!--分享-->
    <script type="text/javascript" charset="utf-8"
            src="http://static.bshare.cn/b/buttonLite.js#uuid=ad6ca8fe-7189-423a-b7b6-ed44605dd77a&style=-1"></script>
</head>

<script type="text/javascript">
    var operateLogPageCode = '1044';
    var operateLogPageName = '热度查询结果';
    var operateLogRequestPath = '<%=request.getContextPath()%>';
</script>

<body class="layoutBody">
<!--head代码 start-->
<#include "../common/tips.ftl"/>
<#if flag !=0>
    <#include "../common/navigate.ftl"/>
<#--<s:param name="currentPage">shouye</s:param>-->
</#if>
<!--head代码 end-->
<form id="loginRecordForm"
      action="${staticResourcePath}/view/hotSearch/verifyOperateInfo.action"
      method="post">
    <#if notLoginOperateRecord??>
        <input type="hidden" name="notLoginOperateRecord.name" id="recordName"
               value="${notLoginOperateRecord.name}">

    <input type="hidden"
           name="notLoginOperateRecord.keyword" id="recordKeyword"
           value="${notLoginOperateRecord.keyword}">
        <input
        type="hidden" name="notLoginOperateRecord.filterKeyword"
        id="recordFilterKeyword"
        value="${notLoginOperateRecord.filterKeyword}"> <input
        type="hidden" name="notLoginOperateRecord.jsonData" id="recordData"
        value="${notLoginOperateRecord.jsonData}"> <input
        type="hidden" name="notLoginOperateRecord.operateType"
        id="recordType" value="${notLoginOperateRecord.operateType}">

    </#if>
</form>

<form method="post" name="searchForm" id="searchForm">
    <#--<input type="hidden" id="categoryType" name="categoryType"-->
           <#--value="${categoryType}" />-->
    <#--<input type="hidden" id="secondCategory"-->
                                             <#--name="secondCategory" value="${secondCategory}" /> <input-->
        <#--type="hidden" id="categoryLevel" name="categoryLevel"-->
        <#--value="${categoryLevel}" /> -->
    <input type="hidden" id="title_id"
                                           name="title" />
    <input type="hidden" id="keyword_id" name="keyword" />
    <#--<input type="hidden" id="categoryId" name="categoryId"-->
           <#--value="${categoryId}" /> -->
    <input type="hidden" id="date_id" name="date" value="${date }" />
    <#--<input type="hidden"-->
                                                                                  <#--id="startTime_id" name="startTime" /> <input type="hidden"-->
                                                                                                                               <#--id="endTime_id" name="endTime" /> <input type="hidden" id="num_id"-->
                                                                                                                                                                        <#--name="num" /> <input type="hidden" id="isEver" name="isEver" /> <input-->
        <#--type="hidden" id="filterKeyword_id" name="filterKeyword" /> <INPUT-->
        <#--type=hidden id="allTimeTe" name="allTimeTe" value="">-->
</form>

<form method="post" name="frmPopWin2" id="jumpGoPageForm">
    <input type="hidden" id="ticket" name="ticket" value="" />
    <#if admin1??>
        <input  type="hidden" id="userId" name="userId" value="${admin.userId}" />
    </#if>

    <input
        type="hidden" id="reportId" name="heatReport.id" value="0" /> <input
        type="hidden" id="title" name="heatReport.reportName"
        value="${title}" /> <input type="hidden" id="filterKeyword"
                                   name="heatReport.filterKeyword" value="${filterKeyword}" /> <input
        type="hidden" id="keyword" name="heatReport.keyword"
        value="${keyword}"> <INPUT type="hidden" name="cemails"
                                   id="cemails" value=""> <INPUT type="hidden"
                                                                 name="heatReport.emailSwitch" id="emailSwitch" value="0"> <INPUT
        type="hidden" name="heatReport.appSwitch" id="appSwitch" value="0">
    <INPUT type="hidden" name="heatReport.weiboSwitch" id="weiboSwitch"
           value="0"> <input type="hidden" id="productId"
                             name="productId" value="0" /> <input type="hidden" id="starttime"
                                                                  name="starttime" value="" /> <input type="hidden" id="endtime"
                                                                                                      name="endtime" value="" />

    <!--goSearch 参数START-->
    <input type="hidden" name="date" id="date" value="${date}" />
    <input
        type="hidden" id="num" name="num" value="${num}" />
    <input
        type="hidden" id="shareCode" name="shareCode" value="${shareCode}" />
    <input type="hidden" id="keywords" value="" />
    <#--<input type="hidden"-->
                                                          <#--id="threeClassifyName" name="threeClassifyName"-->
                                                          <#--value="${threeClassifyName}" /> <input type="hidden"-->
                                                                                                 <#--id="secondClassifyName" name="secondClassifyName"-->
                                                                                                 <#--value="${secondClassifyName}" /> <input type="hidden" id="isAll"-->
                                                                                                                                         <#--name="isAll" value="${isAll}" />-->
    <#--<!--goSearch 参数END&ndash;&gt;-->
</form>
<div class="page-layout">
    <div class="page-layout-content">
        <!--对比设置栏 start -->
        <div class="row clearfix" id="buttonHiddenId">
            <div class="col-md-12">
                <div class="panel panel-border">
                    <div class="panel-heading ngSearch-heading clearfix">
                        <div class="float_l">
                            <div class="float_l">
                                <div class="compBox">
                                    <ul class="compUl" id="inputWord">
                                        <li class="compLi comCtl colorOne" id="li_1"><i
                                                onclick="delWord(1);" class="compClose">×</i>
                                            <div id="kw1_1" class="comBorderL comBorderLOne">
                                                <div class="inner">
                                                    <input name="word" class="comWord" maxlength="20" value=""
                                                           autocomplete="off" id="title1_1"
                                                           onchange="onChangeInput('1_1')" placeholder=""> <input
                                                        id="keyword1_1" value="" type="hidden"> <input
                                                        id="filterKeyword1_1" value="" type="hidden">
                                                </div>
                                                <ul id="hotwords1" class="hotwords"
                                                    style="display: none; width: 450px;"></ul>
                                            </div></li>
                                        <li class="compLi comCtl colorTwo" id="li_2"><i
                                                onclick="delWord(2);" class="compClose">×</i>
                                            <div id="kw2_1" class="comBorderL">
                                                <div class="inner">
                                                    <input name="word" class="comWord" maxlength="20" value=""
                                                           autocomplete="off" id="title2_1"
                                                           onchange="onChangeInput('2_1')" placeholder=""> <input
                                                        id="keyword2_1" type="hidden"> <input
                                                        id="filterKeyword2_1" type="hidden">
                                                </div>
                                                <ul id="hotwords2" class="hotwords"
                                                    style="display: none; width: 450px;"></ul>
                                            </div></li>
                                    </ul>
                                    <a href="javascript:lookBox();"
                                       class="btn btn-big btn-gray addComp" style="display: none;"><i
                                            class="icon-add more-icon-add-class"></i><span>添加对比词</span></a>
                                </div>

                                <a id="advSearchSubmit" class="btn btn-big btn-warning" onclick = "goSearch(2)"
                                   href="javascript:void(0);" style="padding: 10px 20px;">搜索</a>
                            </div>
                        </div>

                        <div class="float_r">
                            <div class="btn-group green" data-toggle="buttons" id="timeQuantum">
                                <label onclick="goIntoTo(30); " class="btn <#if (date==24)??>active</#if>" style="width: 72px;">
                                    <input type="radio" name="options" id="option1" value="24"/> 24小时
                                </label>
                                <label onclick="goIntoTo(31) " data-toggle="modal" data-target="#loginModal" class="btn <#if (date==3)??>active</#if>" style="width: 72px;">
                                    <input type="radio" name="options" id="option2" value="3"/> 3天
                                </label>
                                <label onclick="goIntoTo(32) " data-toggle="modal" data-target="#loginModal" class="date-btn5 btn <#if (date==5)??>active</#if>" style="width: 72px;">
                                    <input type="radio" name="options" id="option3" value="5"/> 5天
                                </label>
                                <label onclick="goIntoTo(33) " data-toggle="modal" data-target="#loginModal" class="date-btn7 btn <#if (date==7)??>active</#if>" style="width: 72px;">
                                    <input type="radio" name="options" id="option4" value="7"/> 7天
                                </label>
                                <label onclick="goIntoTo(34)" data-toggle="modal" data-target="#loginModal" class="btn" style="width: 72px;">
                                    自定义
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!--对比设置栏 end -->
        <!--四个头部导航start-->

        <!-- 			<div class="row clearfix mb20"> -->
        <!-- 				<div class="col-md"> -->
        <!-- 					<ul class="navbar2 toggleTab-ul"> -->
        <!-- 						<li class="col-md-3 toggleTab-li1" onclick="toggleTabHot(1);"> -->
        <!-- 							<a href="javascript:void(0)">热度指数</a> -->
        <!-- 						</li> -->
        <!-- 						<li class="col-md-3 toggleTab-li2" onclick="toggleTabHot(2);"> -->
        <!-- 							<a href="javascript:void(0)">传播分析</a> -->
        <!-- 						</li> -->
        <!-- 						<li class="col-md-3 toggleTab-li3" onclick="toggleTabHot(3);"> -->
        <!-- 							<a href="javascript:void(0)">口碑分析</a> -->
        <!-- 						</li> -->
        <!-- 						<li class="col-md-3 toggleTab-li4" onclick="toggleTabHot(4);"> -->
        <!-- 							<a href="javascript:void(0)">微博情绪</a> -->
        <!-- 						</li> -->
        <!-- 					</ul> -->
        <!-- 				</div> -->
        <!-- 			</div> -->
        <div id="search-result-body">
            <div class="spinner" style="display: block; margin-top: 100px;">
                <div class="bounce1"></div>
            </div>
            <br />
            <div style="font-size: 12px; color: #999999;" align="center">系统拼命加载中，不要离开，马上呈现～</div>
        </div>
        <!--         <div class="row clearfix"> -->
        <!-- 			<div class="col-md-12"> -->
        <!-- 				<div class="fenxiang phone_hide"> -->
    <#--<%-- 		       	  <span class="fz14">分享到：</span> --%>-->
        <!-- 		       	  	<a href="#" class="btn-icon btn-icon30 btn-circle bg_red" title="微博" onclick="shareTo(event,'sinaminiblog');return false;"><i class="icon-weibo"></i></a> -->
        <!--                     <a href="#" class="btn-icon btn-icon30 btn-circle bg_green ml5" title="微信" onclick="shareTo(event,'weixin');return false;"><i class="icon-weixin"></i></a> -->
        <!--                     <a href="#" class="btn-icon btn-icon30 btn-circle bg_blue ml5" title="QQ" onclick="shareTo(event,'qqim');return false;"><i class="icon-qq"></i></a> -->
        <!--                     <a href="#" class="btn-icon btn-icon30 btn-circle bg_yellow ml5" title="QQ空间" onclick="shareTo(event,'qzone');return false;"> <i class="icon-qqkongjian" style="margin-top: -2px;"></i></a> -->
        <!-- 		        </div> -->
        <!-- 			</div> -->
        <!-- 		</div> -->
    </div>
</div>

<!-- 验证码 -->
<style type="text/css">
    .validate input {
        height: 40px;
        line-height: 40px;
        width: 240px;
        padding-left: 10px;
        font-size: 14px;
    }

    .validate .imgVcode {
        display: inline-block;
        width: 100px;
        height: 40px;
    }

    .validate .imgVcode img {
        width: 100%;
        height: 100%;
    }
</style>
<div id="requestCheckModal" class="modal fade" role="dialog"
     tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none; width: 500px; margin-left: -250px;">
    <div class="modal-header">
        <button type="button" id="requestCheckModal-close" class="close"
                data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">验证</h3>
    </div>
    <div class="modal-body" style="padding-left: 70px;">
        <p class="fz16 mb15">热度查询需要先输入下方验证码~</p>
        <div class="validate">
            <input type="hidden" name="searchKey" id="search_key" value="" /> <input
                type="hidden" name="searchTag" id="search_tag" value="" /> <input
                type="hidden" name="" id="_ran_request" value="" /> <input
                type="text" name="" id="_ran_request_input" value=""
                placeholder="请输入验证码" />
            <div class="imgVcode" onclick="getRequestRan()">
                <img id="imgVcode_request" src="${staticResourcePath}/images/validation.png" />
            </div>
        </div>
        <small class="fz12 pading-top-10 fc_orange"
               id="request_check_msg_div"> <i class="icon mr5">&#xe609;</i><span
                id="request_check_msg"></span>
        </small>
    </div>
    <div class="modal-footer">
        <button class="btn btn-big btn-warning mr25" onclick="checkRequest()">确定</button>
        <button class="btn btn-big btn-info" data-dismiss="modal"
                aria-hidden="true">取消</button>
    </div>
</div>


<!--底部部分代码 start-->
<#include "../common/bottoms.ftl"/>
<!--底部部分代码 end-->

<#if admin1??>
    <!-- 新版购买 -->
    <#include value="/view/pay/show_buy_common_v2.jsp"/>
</#if>

<script type="text/javascript"
        src="${staticResourcePath}/js/hotSearchchart.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="${staticResourcePath}/js/jquery.slimscroll.js"></script>
<script type="text/javascript"
        src="${staticResourcePath}/js/icheck.js"></script>
// <#--<%--时间插件 start--%>-->
<link
        href="${staticResourcePath}/css/jquery-ui.css"
        rel="stylesheet" type="text/css">
<link
        href="${staticResourcePath}/js/datepicker/jquery-ui-timepicker-addon.css"
        rel="stylesheet" type="text/css">
<script type="text/javascript"
        src="${staticResourcePath}/js/datepicker/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
        src="${staticResourcePath}/js/datepicker/jquery-ui-timepicker-zh-CN.js"></script>
// <#--<%--时间插件 end--%>-->
<script type="text/javascript"
        src="${staticResourcePath}/js/switchery.min.js"></script>
<script type="text/javascript"
        src="${staticResourcePath}/js/js/moment.js"
        charset="UTF-8"></script>
<script type="text/javascript"
        src="${staticResourcePath}/css/tg/360/js/flowmonitoring.js"></script>
<script type="text/javascript" src="http://static.geetest.com/static/tools/gt.js"></script>
<script type="text/javascript" src="${staticResourcePath}/js/geetest.js"></script>
<script type="text/javascript">
    //虚拟滚动条
    $('#relatedWordsTable .fixedtablewrap>div+div').slimScroll({height: 'auto'});

    function chartloading(id){
        jquery('#' + id).loader('show', loaders);
        if("counter"==id){
            jquery('.' + id).loader('show', loaders);
        }
    }

    function chartloadingTab(id){
        if(id == "counter"){
            jquery('.' + id).loader('show',loaders);
        }else if(id == "selectTimeWait"){
            jquery('#' + id).loader('show',loaders2);
        }else if(id == "doubleSelectId"){
            jquery('#' + id).loader('show',loaders2);
        }else if(id == "timeToggle2"){
            jquery('#' + id).loader('show',loaders2);
        }else{
            jquery('#' + id).loader('show',loaders);
        }
    }

    function sendChartPost(url, params, callback, demo) {
        if(demo != null && demo.length > 3){
            $("#" + demo).removeAttr("_echarts_instance_");
        }
        $.ajax({
            url : url,
            data : params,
            async : true,
            type : "post",
            success : function(data) {
                if(data&& data.code == "8888" && geeTestFlag){
                    geeTestFlag = false;
                    isUnseal(data);
                }else{
                    callback(data, demo);
                }
            }
        });
    }

    var tag = 0;

    //一个输入框内添加框
    function addWord(id){
        if(id==1){
            if($("#kw1_2").is(":hidden")){
                $("#kw1_2").show();
            }else{
                $("#kw1_3").show();
                $("#w1").hide();
            }
        }else if(id==2){
            if($("#kw2_2").is(":hidden")){
                $("#kw2_2").show();
            }else{
                $("#kw2_3").show();
                $("#w2").hide();
            }

        }else if(id==3){
            if($("#kw3_2").is(":hidden")){
                $("#kw3_2").show();
            }else{
                $("#kw3_3").show();
                $("#w3").hide();
            }
        }
    }

    <#--<%-- function shareTo(event,type){-->
    <#--var pre = location.href.split("/goSearch")[0];-->
    <#--if(location.href.indexOf("/goSearch")!=-1){-->
    <#--if(location.href.indexOf("/view")!=-1){-->
    <#--pre = location.href.split("/view")[0];-->
    <#--}-->
    <#--}else if(location.href.indexOf("/heatPreview")!=-1){-->
    <#--if(location.href.indexOf("/heatPreview")!=-1){-->
    <#--pre = location.href.split("/heatPreview")[0];-->
    <#--}-->
    <#--if(location.href.indexOf("/share")!=-1){-->
    <#--pre = location.href.split("/share")[0];-->
    <#--}-->
    <#--}else if(location.href.indexOf("/shouye")!=-1){-->
    <#--if(location.href.indexOf("/shouye")!=-1){-->
    <#--pre = location.href.split("/shouye")[0];-->
    <#--}-->
    <#--}-->
    <#--var shareCode = $("#shareCode").val();-->
    <#--var title=$("#title").val();-->
    <#--if(title.indexOf(',') != -1){-->
    <#--title=$("#title").val().split(",")[0];-->
    <#--}-->
    <#--var hotCus=$("#hotCus").val();-->
    <#--if(hotCus<=30){-->
    <#--hotCus="0～30";-->
    <#--}else if(hotCus>30&&hotCus<=60){-->
    <#--hotCus="30～60";-->
    <#--}else if(hotCus>60&&hotCus<=80){-->
    <#--hotCus="60～80";-->
    <#--}else if(hotCus>80&&hotCus<=100){-->
    <#--hotCus="80～100";-->
    <#--}else{-->
    <#--hotCus="80～100";-->
    <#--}-->
    <#--var titleDes = "【微热点(微舆情)】一起来比比热度指数吧~"+title+"的热度指数在"+hotCus+"之间，快戳";-->
    <#--if('sinaminiblog'==type){-->
    <#--titleDes="【来自@微热点(微舆情)】#热度比拼#"+title+"的热度指数在"+hotCus+"之间，快戳";-->
    <#--}-->
    <#--var url="";-->
    <#--if(shareCode.length==0) {-->
    <#--if(phone){-->
    <#--url ="http://h5.51wyq.cn/indexLocal.shtml";-->
    <#--}else{-->
    <#--url ="www.wrd.cn/login.shtml";-->
    <#--}-->
    <#--if($("#keywords").val().indexOf(",")!=-1){-->
    <#--var buyKeywords = "{";-->
    <#--var keywordNum=0;-->
    <#--var keywords=[];-->
    <#--keywords=$("#keywords").val().split(",");-->
    <#--var titles=[];-->
    <#--titles=$("#title").val().split(",");-->
    <#--var filterKeywords=[];-->
    <#--filterKeywords=$("#filterKeyword").val().split(",");-->
    <#--var categoryTypeArr = [];-->
    <#--var categoryTypes = $("#categoryType").val();-->
    <#--categoryTypeArr = categoryTypes.split(",");-->
    <#--for(var i=0;i<titles.length;i++){-->
    <#--var title=titles[i];-->
    <#--var keyword=keywords[i];-->
    <#--var filterKeyword=filterKeywords[i];-->
    <#--var categoryType = categoryTypeArr[i];-->
    <#--if(keyword) {-->
    <#--buyKeywords += '"title'+(i+1)+'":"'+title + '","keyword'+(i+1)+'":"' + keyword + '"';-->
    <#--if(filterKeyword) {-->
    <#--buyKeywords += ',"filterKeyword' + (i + 1) + '":"' + filterKeyword + '"';-->
    <#--}-->
    <#--if(categoryType) {-->
    <#--buyKeywords += ',"type'+(i+1)+'":'+categoryType+',';-->
    <#--}-->
    <#--if($("#secondCategory").val()!=""){-->
    <#--buyKeywords += ',"secondCategory'+(i+1)+'":'+$("#secondCategory").val();-->
    <#--}-->
    <#--if($("#categoryLevel").val()!=""){-->
    <#--buyKeywords += ',"categoryLevel'+(i+1)+'":'+$("#categoryLevel").val()+',';-->
    <#--}-->
    <#--buyKeywords += ",";-->
    <#--keywordNum++;-->
    <#--}-->
    <#--}-->
    <#--if($("#date").val()){-->
    <#--buyKeywords += '"date":'+$("#date").val()+",";-->
    <#--}-->
    <#--if($("#starttime").val()){-->
    <#--buyKeywords += '"startTime":'+$("#starttime").val()+",";-->
    <#--}-->
    <#--if($("#endtime").val()){-->
    <#--buyKeywords += '"endTime":'+$("#endtime").val()+",";-->
    <#--}-->
    <#--buyKeywords = buyKeywords.substring(0, buyKeywords.length - 1);-->
    <#--buyKeywords +="}";-->
    <#--if (keywordNum < 2) {-->
    <#--var parms = {'heatShare.title':$("#title").val(),'heatShare.keyword':$("#keywords").val(),'heatShare.filterKeyword':$("#filterKeyword").val(),'heatShare.categoryId':$("#categoryId").val(),'categoryType':$("#categoryType").val(),'secondCategory':$("#secondCategory").val(),'categoryLevel':$("#categoryLevel").val(),'date':$("#date").val(),'heatShare.shareDate':$("#allTimeTe").val(),'starttime':$("#starttime").val(),'endtime':$("#endtime").val(),'newOrOld':"1"};-->
    <#--$.ajax({-->
    <#--url : "${staticResourcePath}/view/hotSearch/shareSingleHotSearch.action",-->
    <#--type : "post",-->
    <#--async:false,-->
    <#--data:parms,-->
    <#--success : function(data){-->
    <#--if(data){-->
    <#--$("#shareCode").val(data);-->
    <#--url =  pre + "/share/heat/heatPreviewNot.shtml?notIntercept=1&shareCode=" + encodeURIComponent(data)+"&searchOne=1&newOrOld=1";-->
    <#--}-->
    <#--}-->
    <#--})-->
    <#--} else {-->
    <#--showMsgInfo(0,"请付费之后再分享，谢谢！",0);-->
    <#--return;-->
    <#--}-->
    <#--}else{-->
    <#--var parms = {'heatShare.title':$("#title").val(),'heatShare.keyword':$("#keywords").val(),'heatShare.filterKeyword':$("#filterKeyword").val(),'heatShare.categoryId':$("#categoryId").val(),'categoryType':$("#categoryType").val(),'secondCategory':$("#secondCategory").val(),'categoryLevel':$("#categoryLevel").val(),'date':$("#date").val(),'heatShare.shareDate':$("#allTimeTe").val(),'starttime':$("#starttime").val(),'endtime':$("#endtime").val(),'newOrOld':"1"};-->
    <#--$.ajax({-->
    <#--url : "${staticResourcePath}/view/hotSearch/shareSingleHotSearch.action",-->
    <#--type : "post",-->
    <#--async:false,-->
    <#--data:parms,-->
    <#--success : function(data){-->
    <#--if(data){-->
    <#--$("#shareCode").val(data);-->
    <#--url =  pre + "/share/heat/heatPreviewNot.shtml?notIntercept=1&shareCode=" + encodeURIComponent(data)+"&searchOne=1&newOrOld=1";-->
    <#--}-->
    <#--}-->
    <#--})-->
    <#--}-->

    <#--}else{-->
    <#--url = pre + "/share/heat/heatPreviewNot.shtml?notIntercept=1&shareCode=" + encodeURIComponent(shareCode)+"&searchOne=1&newOrOld=1";-->
    <#--}-->
    <#--//清除自定义分享内容的方法，在设置前清空，重新自定义内容-->
    <#--bShare.entries = [];-->
    <#--bShare.addEntry({-->
    <#--title:titleDes,-->
    <#--url: url-->
    <#--});-->
    <#--bShare.init();-->
    <#--bShare.share(event,type,0);-->
    <#--} --%>-->

    //多个输入框 删除框,单个输入框 清空内容
    function delWord(id){
        var curIds="";
        $(".page-layout-content .compUl li").each(function(){
            curIds+=$(this).attr("class");
        });
        if(id==1) {
            if (curIds.indexOf('colorTwo') == -1 && curIds.indexOf('colorThree') == -1) {
                $("#title1_1,#keyword1_1,#keyword1_2,#keyword1_3,#filterKeyword1_1,#title,#keyword,#filterKeyword,#keywords").val("");
                $("#kw1_2,#kw1_3").hide();
                $("#w1").show();
            } else {
                $(".colorOne").remove();
                $(".addComp").show();
            }
        }
        if(id==2) {
            if (curIds.indexOf('colorOne') == -1 && curIds.indexOf('colorThree') == -1) {
                $("#title2_1,#keyword2_1,#keyword2_2,#keyword2_3,#filterKeyword2_1,#title,#keyword,#filterKeyword,#keywords").val("");
                $("#kw2_2,#kw2_3").hide();
                $("#w2").show();
            } else {
                $(".colorTwo").remove();
                $(".addComp").show();
            }
        }
        if(id==3) {
            if (curIds.indexOf('colorOne') == -1 && curIds.indexOf('colorTwo') == -1) {
                $("#title3_1,#keyword3_1,#keyword3_2,#keyword3_3,#filterKeyword3_1,#title,#keyword,#filterKeyword,#keywords").val("");
                $("#kw3_2,#kw3_3").hide();
                $("#w3").show();
            } else {
                $(".colorThree").remove();
                $(".addComp").show();
            }
        }
    }

    //关键词搜索事件
    function goSearch(flag,date){
        if(tag == 1){
            showMsgInfo(0,"系统正在努力加载中，请不要频繁操作哟!",0);
            return;
        }
        $("#shareCode,#title,#keywords,#filterKeyword,#ticket,#keyword").val("");

        if(flag==1){
            SetRQ(24);
            var kv = $("#search-keyword").val().replace(/、/g,"+").replace(/\s+/g,"+");
            if($.trim(kv)=="人名、企业名、品牌名或事件关键词"){
                showMsgInfo(0,"请输入搜索词!",0);
                return;
            }
            if($.trim(kv).length>20){
                showMsgInfo(0,"搜索词不得大于20个字!",0);
                return;
            }
            if($.trim(kv)==""||$.trim(kv)=="请输入您想查询的关键字"){
                showMsgInfo(0,"请输入你想搜索的词语",0);
                return ;
            }
            if($.trim(kv).length==1){
                showMsgInfo(0,"请至少输入2个字符",0);
                return ;
            }

            $("#search-keyword").val(kv);
            $("#keywords,#keyword").val(kv);
            $("#title").val(kv);
            $("#filterKeyword").val("");
            $("#categoryId,#categoryType,#secondCategory,#categoryLevel,#secondClassifyName,#threeClassifyName").val("");
            $("#num").val(0);
            var dat = $("#date").val();
            var startt = $("#starttime").val();
            var endt = $("#endtime").val();
            tag = 0;
            $("#setContrast").show();
            $("#timeQuantum").show();
            unset($("#title").val(),$("#keywords").val(),$("#filterKeyword").val());
            toggleTabHot(1);

        }else{
            //按钮分析
            if(date){
                SetRQ(date);
            }else {
                SetRQ($("#date").val());
            }
            $("#dropdown_time").hide();

            var titles="";
            var keywords = "";
            var filterKeywords="";
            if($("#num").val()!=0){
                titles+='${title}';
                keywords+='${keyword}';
                if('${keyword2}'!=""){
                    keywords+=","+'${keyword2}';
                }
                if('${keyword3}'!=""){
                    keywords+=","+'${keyword3}';
                }
                filterKeywords+='${filterKeyword}';
            }else{
                $.each($(".page-layout-content .compUl li:visible"),function(){
                    var id = $(this).attr("id");
                    var num = id.split("_")[1];
                    var k1 = "";
                    var fk1="";
                    var tit1="";
                    var k_1 = $("#keyword"+num+"_1").val();
                    var k_2 = $("#keyword"+num+"_2").val();
                    var k_3 = $("#keyword"+num+"_3").val();
                    var fk_1 = $("#filterKeyword"+num+"_1").val();
                    var tit_1 = $("#title"+num+"_1").val();

                    if(k_1!=""){
                        k_1 = $.trim(k_1).replace(/\s+/g,"+");
                        k1 += k_1+"_";
                        fk1+=fk_1+",";
                        tit1+=$.trim(tit_1).replace(/\s+/g,"+")+"_";
                    }else if(tit_1!=""&&k_1==""){
                        k_1 = $.trim(tit_1).replace(/\s+/g,"+");
                        k1 += k_1+"_";
                        fk1+=fk_1+",";
                        tit1+=$.trim(k_1).replace(/\s+/g,"+")+"_";
                    }
                    if(k_2!=""){
                        k_2 = $.trim(k_2).replace(/\s+/g,"+");
                        k1 += k_2+"_";
                        fk1+=fk_1+",";
                        tit1+=$.trim(k_2).replace(/\s+/g,"+")+"_";
                    }
                    if(k_3!=""){
                        k_3 = $.trim(k_3).replace(/\s+/g,"+");
                        k1 += k_3+"_";
                        fk1+=fk_1+",";
                        tit1+=$.trim(k_3).replace(/\s+/g,"+")+"_";
                    }
                    k1 = k1.substr(0,k1.length-1);
                    fk1=fk1.substr(0,fk1.length-1);
                    tit1=tit1.substr(0,tit1.length-1);
                    if(tit1) {
                        titles += tit1 + ",";
                    }
                    if(k1) {
                        keywords += k1 + ",";
                    }
                    if(fk1) {
                        filterKeywords += fk1 + ",";
                    }
                });
            }
            var keywordNum = 0;
            titles=titles.replace(/,$/,"");
            keywords=keywords.replace(/,$/,"");
            filterKeywords=filterKeywords.replace(/,$/,"");
            if($.trim(keywords)==""){
                showMsgInfo(0,"请输入对比词语!",0);
                return;
            }
            if(flag==3){
            }else if(flag==4){
            }else{
                var flag=0;
            }
            var arr = [];
            $("#inputWord li").each(function(){
                var $this = $(this);
                var input = $this.find("div.comBorderL div.inner input:eq(0)").val();
                if($.trim(input)!=""&&$.inArray(input,arr)!=-1){
                    showMsgInfo(0,"对不起,重复的内容对比会浪费您的使用次数,请修改后重新提交!",0);
                    flag=1;
                    return false;
                }else if($.trim(input)!=""){
                    arr.push(input);
                }
            });

            var keywordArr=[];
            keywordArr=keywords.split(",");
            var titleArr=[];
            titleArr=titles.split(",");
            var filterKeywordArr=[];
            filterKeywordArr=filterKeywords.split(",");

            for(var i=0;i<titleArr.length;i++){
                if($.trim(titleArr[i])!=""&&$.trim(titleArr[i]).length==1){
                    showMsgInfo(0,"请至少输入2个字符",0);
                    flag=1;
                    return false;
                }
            }
            if(flag==1){
                return;
            }
            var buyKeywords = "{";
            var count=0;
            if(keywords.indexOf(',') != -1){
                count=keywords.match(/,/g).length;
            }
            if(count > 0 || $("#date").val() < 1){
                var curIds="";
                var html="";
                var queryKeyword="";
                var queryTitle="";
                var queryFilterKeyword="";
                $(".compUl> li").each(function(){
                    if($(this).attr("class").indexOf('colorOne') != -1){
                        $(".colorOne").remove();
                    }else if($(this).attr("class").indexOf('colorTwo') != -1){
                        $(".colorTwo").remove();
                    }
                });
                curIds="";
                var categoryTypeArr = [];
                var categoryTypes = $("#categoryType").val();
                categoryTypeArr = categoryTypes.split(",");
                for(var i=0;i<titleArr.length;i++){
                    var title=titleArr[i];
                    var keyword=keywordArr[i];
                    var filterKeyword=filterKeywordArr[i];
                    var categoryType = categoryTypeArr[i];
                    if(i==0){
                        if($.trim(keyword)==""){
                            //$(".colorOne").remove();
                            continue;
                        }else{
                            curIds += "colorOne";
                            html += '<li class="compLi comCtl colorOne" id="li_1">' +
                                    //                             '<i onclick="addWord(1);" id="w1" title="添加组合词" class="addCompS">+</i>' +
                                    '<i onclick="delWord(1);"class="compClose">×</i>' +
                                    '<div id="kw1_1" class="comBorderL comBorderLOne">' +
                                    '<div class="inner">' +
                                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="title1_1"  onchange="onChangeInput(\'1_1\')"  placeholder="">' +
                                    '<input id="keyword1_1" type="hidden">'+
                                    '<input id="filterKeyword1_1" type="hidden">'+
                                    '</div>' +
                                    '<ul id="hotwords1" class="hotwords" style="display: none;width: 450px;">'+
                                    '</ul>'+
                                    '</div>' +
                                    '<div id="kw1_2" style="display: none;" class="comBorderL">' +
                                    '<span class="addicon">+</span>' +
                                    '<div class="inner">' +
                                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword1_2" placeholder="">' +
                                    '</div>' +
                                    '</div>' +
                                    '<div id="kw1_3" style="display: none;" class="comBorderL">' +
                                    '<span class="addicon">+</span>' +
                                    '<div class="inner">' +
                                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword1_3" placeholder="">' +
                                    '</div>' +
                                    '</div>' +
                                    '</li>';
                            $(".compUl").append(html);
                            html = '';
                        }
                    }
                    if(i==1) {
                        if($.trim(keyword)==""){
                            //$(".colorTwo").remove();
                            continue;
                        }else{
                            curIds += "colorTwo";
                            html += '<li class="compLi comCtl colorTwo" id="li_2">' +
                                    //                             '<i onclick="addWord(2);" id="w1" title="添加组合词" class="addCompS">+</i>' +
                                    '<i onclick="delWord(2);"class="compClose">×</i>' +
                                    '<div id="kw2_1" class="comBorderL">' +
                                    '<div class="inner">' +
                                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="title2_1"  onchange="onChangeInput(\'2_1\')"  placeholder="">' +
                                    '<input id="keyword2_1" type="hidden">' +
                                    '<input id="filterKeyword2_1" type="hidden">' +
                                    '</div>' +
                                    '<ul id="hotwords2" class="hotwords" style="display: none;width: 450px;">' +
                                    '</ul>' +
                                    '</div>' +
                                    '<div id="kw2_2" style="display: none;" class="comBorderL">' +
                                    '<span class="addicon">+</span>' +
                                    '<div class="inner">' +
                                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword2_2" placeholder="">' +
                                    '</div>' +
                                    '</div>' +
                                    '<div id="kw2_3" style="display: none;" class="comBorderL">' +
                                    '<span class="addicon">+</span>' +
                                    '<div class="inner">' +
                                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword2_3" placeholder="">' +
                                    '</div>' +
                                    '</div>' +
                                    '</li>';
                            $(".compUl").append(html);
                            html = '';
                        }
                    }

                    $("#keyword"+(i+1)+"_1").val(keyword);
                    $("#title"+(i+1)+"_1").val(title.split("_")[0]);
                    $("#filterKeyword"+(i+1)+"_1").val(filterKeyword);
                    if($.trim(keyword)!="") {
                        queryKeyword+=keyword+",";
                        queryTitle+=title+",";
                        if(!filterKeyword){
                            filterKeyword = "";
                        }
                        queryFilterKeyword += filterKeyword+",";
                        buyKeywords += '"title'+(i+1)+'":"'+title + '","keyword'+(i+1)+'":"' + keyword + '"';
                        if(filterKeyword) {
                            buyKeywords += ',"filterKeyword' + (i + 1) + '":"' + filterKeyword + '"';
                        }
                        if(categoryType) {
                            buyKeywords += ',"type'+(i+1)+'":'+categoryType;
                        }
                        if($("#secondCategory").val()!=""){
                            buyKeywords += ',"secondCategory'+(i+1)+'":'+$("#secondCategory").val();
                        }
                        if($("#categoryLevel").val()!=""){
                            buyKeywords += ',"categoryLevel'+(i+1)+'":'+$("#categoryLevel").val();
                        }
                        buyKeywords +=",";
                        keywordNum++;
                    }
                }
                if($("#date").val()){
                    buyKeywords += '"date":'+$("#date").val()+",";
                }
                if($("#starttime").val()){
                    buyKeywords += '"startTime":"'+$("#starttime").val()+'",';
                }
                if($("#endtime").val()){
                    buyKeywords += '"endTime":"'+$("#endtime").val()+'",';
                }
                if($("#secondClassifyName").val()){
                    buyKeywords += '"secondClassifyName":"'+$("#secondClassifyName").val()+'",';
                }
                if($("#threeClassifyName").val()){
                    buyKeywords += '"threeClassifyName":"'+$("#threeClassifyName").val()+'",';
                }

                if($("#isAll")){
                    buyKeywords += '"isAll":"'+$("#isAll").val()+'",';
                }

                buyKeywords = buyKeywords.substring(0, buyKeywords.length - 1);
                buyKeywords += "}";
                $("#keywords").val(queryKeyword.replace(/,$/,""));
                $("#keyword").val(queryKeyword.replace(/,$/,""));
                $("#title").val(queryTitle.replace(/,$/,""));
                $("#filterKeyword").val(queryFilterKeyword.replace(/,$/,""));
                $("#search-keyword").val(queryTitle.replace(/,$/,"").replace(/,/g, "、"));
            }else{
                $("#keywords").val(keywords);
                $("#keyword").val(keywords);
                $("#title").val(titles);
                $("#filterKeyword").val(filterKeywords);
                $("#search-keyword").val(titles.replace(/,/g, "、").replace(/、$/,""));
            }

            if($(".page-layout-content .compUl li:visible").length<2){
                $(".addComp").show();
            }else{
                $(".addComp").hide();
            }

            //绑定回车事件
            $('.comWord').keydown(function(event) {
                if (event.keyCode == 13) {
                    event.preventDefault();
                    $(this).change();
                    goSearch(2, 24);
                }
            });

            if(flag == 3){
                var starttime = $("#starttime").val();
                var endtime = $("#endtime").val();
                var d1 = new Date(starttime.replace(/\-/g, "\/"));
                var d2 = new Date(endtime.replace(/\-/g, "\/"));
                if(starttime!=""&&endtime!=""&&d1 >=d2){
                    showMsgInfo(0,"开始时间不能大于或等于结束时间！",0);
                    return;
                }
                if(starttime.length<10||endtime.length<10){
                    showMsgInfo(0,"选择开始时间或者结束时间有误！",0);
                    return;
                }
            }
            var keyWords = $("#keywords").val();
            var keyWordsList = keyWords.split(",");
            for(n in keyWordsList){
                if(!checkKeywordFilter(keyWordsList[n])){
                    showMsgInfo(0, "关键词只能包含中文、英文和数字！", 0);
                    return;
                }
                if(!checkSingleKeyword(keyWordsList[n])){
                    showMsgInfo(0, "必须输入两个字的词语！", 0);
                    return;
                }
            }
            if(count > 0) {
                $("#oneOrMore").val("2");
                if ($("#date").val() < 1) {
                    showMsgInfo(0, "多个词暂不支持自定义时间！", 0);
                    return;
                }
            }
            var dat = $("#date").val();
            var startt = $("#starttime").val();
            var endt = $("#endtime").val();
            if($("#num").val()!=0){
                if (keywordNum < 2) {
                    $("#num").val(0);
                    toggleTabHot(1);
                }
            }else {
                if (keywordNum < 2) {
                    if($("#date").val() < 1){
                        tag = 1;
                        var days = GetDateDiff($("#starttime").val(),$("#endtime").val());
                        $.ajax({
                            url: "${staticResourcePath}/creatHotAnalysisTask.action",
                            type: "post",
                            data: {
                                "title":$("#title").val(),
                                "keyword": $("#keywords").val(),
                                "filterKeyword":$("#filterKeyword").val(),
                                "starttime":$("#starttime").val(),
                                "endtime":$("#endtime").val()
                            },
                            success: function (data) {
                                if(data.msg) {
                                    tag = 0;
                                    $("#ticket").val(data.ticket);
//                                    openBuyCommon(9, {
//                                        packageCount: keywordNum,
//                                        days: parseInt(days),
//                                        keywords: buyKeywords
//                                    })
                                }else{
                                    tag = 0;
                                    showMsgInfo(0,data.message,0);
                                }
                            }
                        });
                    } else {
                        if(flag==3){
                            chartloadingTab("timeToggle2");
                            chartloadingTabCluster("container1");
                            chartloadingTab("doubleSelectId");
                            var title = $("#title").val();
                            var keywords = $("#keywords").val();
                            var filterKeyword = $("#filterKeyword").val();
                            var categoryId=$("#categoryId").val();
                            var categoryType=$("#categoryType").val();
                            var secondCategory=$("#secondCategory").val();
                            var date = $("#date").val();
                            var categoryLevel=$("#categoryLevel").val();
                            var starttime = $("#starttime").val();
                            var endtime = $("#endtime").val();
                            var secondClassifyName = $("#secondClassifyName").val();
                            var threeClassifyName = $("#threeClassifyName").val();
                            var isAll = $("#isAll").val();
                            $("#labelChangeClass1").attr("class","btn btn-sm ");
                            $("#labelChangeClass2").attr("class","btn btn-sm active");
                            goVolumePig(keywords,date,title,filterKeyword,starttime,endtime);
                        }else if(flag==4){
                            chartloadingTab("timeToggle2");
                            chartloadingTabCluster("container1");
                            chartloadingTab("doubleSelectId");
                            var title = $("#title").val();
                            var keywords = $("#keywords").val();
                            var filterKeyword = $("#filterKeyword").val();
                            var categoryId=$("#categoryId").val();
                            var categoryType=$("#categoryType").val();
                            var secondCategory=$("#secondCategory").val();
                            var date = $("#date").val();
                            var categoryLevel=$("#categoryLevel").val();
                            var starttime = $("#starttime").val();
                            var endtime = $("#endtime").val();
                            var secondClassifyName = $("#secondClassifyName").val();
                            var threeClassifyName = $("#threeClassifyName").val();
                            var isAll = $("#isAll").val();
                            goHotWorth(keywords,date,title,filterKeyword,starttime,endtime,"1");
                        }else{
                            var dat = $("#date").val();
                            var startt = $("#starttime").val();
                            var endt = $("#endtime").val();
                            $("#categoryLevel,#categoryId").val("");
                            hotCallLog();
                        }
                    }
                } else {
                    $("#categoryId,#categoryType,#secondCategory,#categoryLevel,#secondClassifyName,#threeClassifyName").val("");
                    //2个词
                    hotCallLog();
                }

            }
        }
    }

    function hotCallLog(){
        var title = $("#title").val();
        var keywords = $("#keywords").val();
        var filterKeyword = $("#filterKeyword").val();
        var date = $("#date").val();
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        $("#title_id").val(title);
        $("#keyword_id").val(keywords);
        $("#date_id").val(date);
        $("#startTime_id").val(starttime);
        $("#endTime_id").val(endtime);
        $("#filterKeyword_id").val(filterKeyword);
        $.ajax({
            url : "${staticResourcePath}/view/openTools/hotCallLog.action",
            type : 'POST',
            data : $('#searchForm').serialize(),
            success : function(html){
            }
        });
        toggleTabHot(1);
    }

    var toggleTabHotFlag = false;
    function toggleTabHot(type){
        if(toggleTabHotFlag)	//避免重复点击
            return false;

        var keyWords = $("#keywords").val();
        if(""==keyWords){
            return false;
        }
        var keyWordsList = keyWords.split(",");
        for(n in keyWordsList){
            if(!checkKeywordFilter(keyWordsList[n])){
                showMsgInfo(0, "关键词只能包含中文、英文和数字！", 0);
                return;
            }
            if(!checkSingleKeyword(keyWordsList[n])){
                showMsgInfo(0, "必须输入两个字的词语！", 0);
                return;
            }
        }

        toggleTabHotFlag = true;
        $.ajax({
            url:"${staticResourcePath}/hot/opentools/checkKeyword",
            type:"post",
            data:{
                "keyword" :$("#keywords").val().replace(/\s+/g,"+"),
                "count" : 0,
                "date" : $("#date").val(),
                "startTime" : $("#starttime").val(),
                "endTime" : $("#endtime").val(),
                "title" : $("#title").val()
            },
            success:function(data){
                if(data.code=="8888"){
                    toggleTabHotFlag = false;
                    $("#search_key").val(data.searchKey);
                    $("#search_tag").val(type);
                    $("#_ran_request_input").val("");
                    $("#request_check_msg").text("");
                    $("#request_check_msg_div").hide();
                    getRequestRan();
                    $("#requestCheckModal").modal();
                }else if(data.code=="0000" || data.code=="5555"){
                    if(data.code == "5555" && type == 4){
                        toggleTabHotFlag = false;
                        showMsgInfo(0, data.message, 0);
                        return false;
                    }
                    var title = $("#title").val();
                    var keywords = $("#keywords").val();
                    var filterKeyword = $("#filterKeyword").val();
                    var date = $("#date").val();
                    var starttime = $("#starttime").val();
                    var endtime = $("#endtime").val();
                    $("#title_id").val(title);
                    $("#keyword_id").val(keywords);
                    $("#date_id").val(date);
                    $("#startTime_id").val(starttime);
                    $("#endTime_id").val(endtime);
                    $("#filterKeyword_id").val(filterKeyword);

                    $(".toggleTab-ul li").removeClass("active");
                    $(".toggleTab-li" + type).addClass("active");
                <#--<%--                     var url = "${staticResourcePath}/view/openTools/goHotSearch.action";	 --%>-->
                <#--//                     if(type == 1){-->
                <#--<%--                     	url = "${staticResourcePath}/view/openTools/goHotSearch.action";	 --%>-->
                <#--//                     }else if(type == 2){-->
                <#--<%--                     	url = "${staticResourcePath}/view/openTools/goPropagationAnalysis.action"; --%>-->
                <#--//                     }else if(type == 3){-->
                <#--<%--                     	url = "${staticResourcePath}/view/openTools/goPraiseAnalysis.action"; --%>-->
                <#--//                     }else if(type == 4){-->
                <#--<%--                     	url = "${staticResourcePath}/view/openTools/goEmotionMap.action" --%>-->
//                     }
                    var url = "${staticResourcePath}/hot/opentools/goHotSearchv2";

                    $("#search-result-body").html('<div class="spinner" style="display: block;"><div class="bounce1"></div></div><br> <div align=center style="font-size:12px;color:#999999;margin-bottom: 100px;">加载中～</div>');

                    $.ajax({
                        url : url,
                        type : 'POST',
                        data : $('#searchForm').serialize(),
                        success : function(html){
                            toggleTabHotFlag = false;
                            $("#search-result-body").html(html);
                        }
                    });
                }else if(data.code=="6666"){
                    toggleTabHotFlag = false;
                    window.location.href = '${staticResourcePath}/login.shtml';

                }else{
                    toggleTabHotFlag = false;
                    showMsgInfo(0,data.message,0);
                }
            }
        })
    }

    function goIntoTo(n){
        var date;
        if(n==31){
            date=3;
        }else if(n==32){
            date=5;
        }else if(n==33){
            date=7;
        }else{
            date=24;
        }
        var data = {"notLoginOperateRecord.operateType":30,"notLoginOperateRecord.name":$("#title").val(),"notLoginOperateRecord.keyword":$("#keywords").val(),"notLoginOperateRecord.filterKeyword":$("#filterKeyword").val(),"notLoginOperateRecord.options":date};
        $.ajax({
            url : baseAction+"/view/hotSearch/recordOperateInfo.action",
            type : "post",
            data : data,
            success : function(){
                if(n!=30){
                    $("#loginModal").modal();
                }
            }
        })
    }

    function checkRequest(){
        var ran = $("#_ran_request").val();
        var searchKey = $("#search_key").val();
        var imgVcode = $("#_ran_request_input").val();
        if(imgVcode == ''){
            showMsgInfo(0, "请输入验证码！", 0);
            return;
        }
        $.ajax({
            url : "${staticResourcePath}/view/openTools/checkRequest.action",
            type : 'POST',
            data : {
                'ran' : ran,
                'searchKey' : searchKey,
                'imgVcode' : imgVcode
            },
            success : function(data){
                if(data.code == "1"){
                    $("#requestCheckModal-close").click();
                    var searchTag = $("#search_tag").val();
                    toggleTabHot(searchTag);
                }else{
                    $("#request_check_msg").text(data.message);
                    $("#request_check_msg_div").show();
                }
            }
        });
    }

    //获取开始时间
    function startTime(){
        var d = new Date(new Date().getTime() - 24*60*60*1000);  //24小时前
        var str = d.format('yyyy-MM-dd hh:mm:ss');
        return str;
    }
    //获取结束时间
    function endTime(){
        var str = new Date().format('yyyy-MM-dd hh:mm:ss');
        return str;
    }
    //未登录状态跳登录
    function jumpLogin(){
        var data = {"notLoginOperateRecord.operateType":15,"notLoginOperateRecord.name":$("#title").val(),"notLoginOperateRecord.keyword":$("#keyword").val(),"notLoginOperateRecord.filterKeyword":$("#filterKeyword").val()};
        $.ajax({
            url : "${staticResourcePath}/view/hotSearch/recordOperateInfo.action",
            type : "post",
            data : data,
            success : function(){$("#login").click()}
        })
    }

    //添加输入框
    function lookBox(){
//         if($("#userId").val()==""){
//             jumpLogin();return;
//         }
        var curIds = "";
        var html='';
        $(".compUl> li").each(function(){
            curIds += $(this).attr("class");

        });
        if(curIds.indexOf('colorOne')==-1){
            html+='<li class="compLi comCtl colorOne" id="li_1">'+
                    //             '<i onclick="addWord(1);" id="w1" title="添加组合词" class="addCompS">+</i>'+
                    '<i onclick="delWord(1);"class="compClose">×</i>'+
                    '<div id="kw1_1" class="comBorderL comBorderLOne">'+
                    '<div class="inner">'+
                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="title1_1"  onchange="onChangeInput(\'1_1\')"  placeholder="">'+
                    '<input id="keyword1_1" type="hidden">'+
                    '<input id="filterKeyword1_1" type="hidden">'+
                    '</div>'+
                    '<ul id="hotwords1" class="hotwords" style="display: none;width: 450px;">'+
                    '</ul>'+
                    '</div>'+
                    '<div id="kw1_2" style="display: none;" class="comBorderL">'+
                    '<span class="addicon">+</span>'+
                    '<div class="inner">'+
                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword1_2" placeholder="">'+
                    '</div>'+
                    '</div>'+
                    '<div id="kw1_3" style="display: none;" class="comBorderL">'+
                    '<span class="addicon">+</span>'+
                    '<div class="inner">'+
                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword1_3" placeholder="">'+
                    '</div>'+
                    '</div>'+
                    '</li>';
            $(".compUl").append(html);
            count();

            //绑定回车事件
            $('.comWord').keydown(function(event) {
                if (event.keyCode == 13) {
                    event.preventDefault();
                    $(this).change();
                    goSearch(2, 24);
                }
            });

        }else if(curIds.indexOf('colorTwo')==-1){
            html+='<li class="compLi comCtl colorTwo" id="li_2">'+
                    //             '<i onclick="addWord(2);" id="w2" title="添加组合词" class="addCompS">+</i>'+
                    '<i onclick="delWord(2);"class="compClose">×</i>'+
                    '<div id="kw2_1" class="comBorderL">'+
                    '<div class="inner">'+
                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="title2_1"  onchange="onChangeInput(\'2_1\')"  placeholder="">'+
                    '<input id="keyword2_1" type="hidden">'+
                    '<input id="filterKeyword2_1" type="hidden">'+
                    '</div>'+
                    '<ul id="hotwords2" class="hotwords" style="display: none;width: 450px;">'+
                    '</ul>'+
                    '</div>'+
                    '<div id="kw2_2" style="display: none;" class="comBorderL">'+
                    '<span class="addicon">+</span>'+
                    '<div class="inner">'+
                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword2_2" placeholder="">'+
                    '</div>'+
                    '</div>'+
                    '<div id="kw2_3" style="display: none;" class="comBorderL">'+
                    '<span class="addicon">+</span>'+
                    '<div class="inner">'+
                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword2_3" placeholder="">'+
                    '</div>'+
                    '</div>'+
                    '</li>';
            $(".compUl").append(html);
            count();

            //绑定回车事件
            $('.comWord').keydown(function(event) {
                if (event.keyCode == 13) {
                    event.preventDefault();
                    $(this).change();
                    goSearch(2, 24);
                }
            });

        }
    }

    //隐藏或显示 添加对比词语 按钮
    function count(){
        if($(".page-layout-content .compUl li:visible").length<2){
            $(".addComp").show();
        }else{
            $(".addComp").hide();
        }
    }

    function onChangeInput(id){
        $("#filterKeyword"+id).val("");
        $("#keyword"+id).val($("#title"+id).val());
    }

    //获取时间区间
    function SetRQ(date) {
        $("#ticket").val("");
//         if(date == 0){
//             $("#dropdown_time").show();
//         }else{
//             $("#allTimeTe").val("");
//             $("#dropdown_time").hide();
//         }
        $("#date").val(date);
        var MyDate = new Date();
        var seperator2 = ":";
        var year = MyDate.getFullYear();
        var month = MyDate.getMonth() + 1;
        var strDate = MyDate.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }

        var mc=MyDate.getMinutes();
        var minutesCur =mc> 9 ? mc.toString() : "0" + mc.toString();
        var sc=MyDate.getSeconds();
        var secondsCur=sc> 9 ? sc.toString() : "0" + sc.toString();

        var currentdate = year + "-" + month + "-" + strDate
                + " " + MyDate.getHours() + seperator2 + /*MyDate.getMinutes()*/minutesCur
                + seperator2 + /*MyDate.getSeconds()*/secondsCur;
        var currentdate2 = year + "." + month + "." + strDate;
        if(phone){
            currentdate = year.toString().substr(2)+"-"+ month + "-" + strDate
        }

        $("#timeQuantum .btn").removeClass("active");
        $("#endtime").val(currentdate);
        if(date==24) {
            $("#timeQuantum .btn:eq(0)").addClass("active");
            //获得昨天的日期
            var yesterday_miliseconds = MyDate.getTime() - 1000 * 60 * 60 * 24;
            var Yesterday = new Date();
            Yesterday.setTime(yesterday_miliseconds);

            var yesterday_year = Yesterday.getFullYear().toString();
            var month_temp = Yesterday.getMonth() + 1;
            var yesterday_month = month_temp > 9 ? month_temp.toString() : "0" + month_temp.toString();
            var d = Yesterday.getDate();
            var Day = d > 9 ? d.toString() : "0" + d.toString();
            var my=Yesterday.getMinutes();
            var minutesYes=my>9?my.toString():"0"+my.toString();
            var sy=Yesterday.getSeconds();
            var secondsYes=sy>9?sy.toString():"0"+sy.toString();

            var time = yesterday_year + "-" + yesterday_month + "-" + Day
                    + " " + Yesterday.getHours() + seperator2 + /*Yesterday.getMinutes()*/minutesYes + seperator2 +/* Yesterday.getSeconds()*/secondsYes;
            var time2 = yesterday_year + "." + yesterday_month + "." + Day;
            if(phone){
                time = yesterday_year.substr(2)+"-"+ yesterday_month + "-" + Day
            }
            $("#starttime").val(time);
            $(".time22").text("微热点(微舆情)全网数据 " + time2 + "至" + currentdate2);
            $("#allTimeTe").val("微热点(微舆情)全网数据 " + time2 + "至" + currentdate2);
        }else if(date==3){
            $("#timeQuantum .btn:eq(1)").addClass("active");
            //获得三天的日期
            var three_miliseconds = MyDate.getTime() - 1000 * 3 *60 * 60 * 24;
            var threeDay = new Date();
            threeDay.setTime(three_miliseconds);

            var three_year = threeDay.getFullYear().toString();
            var month_temps = threeDay.getMonth() + 1;
            var three_month = month_temps > 9 ? month_temps.toString() : "0" + month_temps.toString();
            var d = threeDay.getDate();
            var Day = d > 9 ? d.toString() : "0" + d.toString();
            var mt=threeDay.getMinutes();
            var minutesThree=mt>9? mt.toString():"0"+ mt.toString();
            var st=threeDay.getSeconds();
            var secondsThree=st>9?st.toString():"0"+st.toString();

            var time3 = three_year + "-" + three_month + "-" + Day
                    + " " + threeDay.getHours() + seperator2 + /*threeDay.getMinutes()*/minutesThree + seperator2 + /*threeDay.getSeconds()*/secondsThree;
            var time32 = three_year + "." + three_month + "." + Day;
            if(phone){
                time3 = three_year.substr(2)+"-"+ yesterday_month + "-" + Day
            }
            $("#starttime").val(time3);
//             $("#endtime").val(currentdate);
            $(".time22").text("微热点(微舆情)全网数据 " + time32 + "至" + currentdate2);
            $("#allTimeTe").val("微热点(微舆情)全网数据 " + time32 + "至" + currentdate2);
        }else if(date==5){//5天
            $("#timeQuantum .btn:eq(2)").addClass("active");
            //获得5天的日期
            var three_miliseconds = MyDate.getTime() - 1000 * 5 *60 * 60 * 24;
            var threeDay = new Date();
            threeDay.setTime(three_miliseconds);

            var three_year = threeDay.getFullYear().toString();
            var month_temps = threeDay.getMonth() + 1;
            var three_month = month_temps > 9 ? month_temps.toString() : "0" + month_temps.toString();
            var d = threeDay.getDate();
            var Day = d > 9 ? d.toString() : "0" + d.toString();
            var mt=threeDay.getMinutes();
            var minutesThree=mt>9? mt.toString():"0"+ mt.toString();
            var st=threeDay.getSeconds();
            var secondsThree=st>9?st.toString():"0"+st.toString();

            var time3 = three_year + "-" + three_month + "-" + Day
                    + " " + threeDay.getHours() + seperator2 + /*threeDay.getMinutes()*/minutesThree + seperator2 + /*threeDay.getSeconds()*/secondsThree;
            var time32 = three_year + "." + three_month + "." + Day;
            if(phone){
                time3 = three_year.substr(2)+"-"+ yesterday_month + "-" + Day
            }
            $("#starttime").val(time3);
//             $("#endtime").val(currentdate);
            $(".time22").text("微热点(微舆情)全网数据 " + time32 + "至" + currentdate2);
            $("#allTimeTe").val("微热点(微舆情)全网数据 " + time32 + "至" + currentdate2);
        }else if(date==7){
            $("#timeQuantum .btn:eq(3)").addClass("active");
            //获得7天的日期
            var three_miliseconds = MyDate.getTime() - 1000 * 7 *60 * 60 * 24;
            var threeDay = new Date();
            threeDay.setTime(three_miliseconds);

            var three_year = threeDay.getFullYear().toString();
            var month_temps = threeDay.getMonth() + 1;
            var three_month = month_temps > 9 ? month_temps.toString() : "0" + month_temps.toString();
            var d = threeDay.getDate();
            var Day = d > 9 ? d.toString() : "0" + d.toString();
            var mt=threeDay.getMinutes();
            var minutesThree=mt>9? mt.toString():"0"+ mt.toString();
            var st=threeDay.getSeconds();
            var secondsThree=st>9?st.toString():"0"+st.toString();

            var time3 = three_year + "-" + three_month + "-" + Day
                    + " " + threeDay.getHours() + seperator2 + /*threeDay.getMinutes()*/minutesThree + seperator2 + /*threeDay.getSeconds()*/secondsThree;
            var time32 = three_year + "." + three_month + "." + Day;
            if(phone){
                time3 = three_year.substr(2)+"-"+ yesterday_month + "-" + Day
            }
            $("#starttime").val(time3);
//             $("#endtime").val(currentdate);
            $(".time22").text("微热点(微舆情)全网数据 " + time32 + "至" + currentdate2);
            $("#allTimeTe").val("微热点(微舆情)全网数据 " + time32 + "至" + currentdate2);
        }else if(date==0){
            $("#timeQuantum .btn:eq(4)").addClass("active");
            if($("#userId").val()=="" && $("#userId").val()<=0){
                jumpLogin();
                return;
            }
            var showStarttime=$("#showStarttime").val();
            var showEndtime= $("#showEndtime").val();
            $("#starttime").val(showStarttime);
            $("#endtime").val(showEndtime);
            var d1 = new Date(showStarttime.replace(/\-/g, "\/"));
            var d2 = new Date(showEndtime.replace(/\-/g, "\/"));
            if(showStarttime!=""&&showEndtime!=""&&d1 >=d2){

            }else {
                $(".time22").text("");
                var sta=$("#starttime").val();
                var stt=sta.toString().substr(0, 10);
                $(".time22").text("微热点(微舆情)全网数据 " + stt.replace(/-/g,".") + "至" + currentdate2);
            }
            $("#allTimeTe").val("");
            $("#allTimeTe").val("微热点(微舆情)全网数据 " + stt.replace(/-/g,".") + "至" + currentdate2);
            $("#oneTime").text("数据时间：" + showStarttime + "至" + showEndtime + "   全网");
        }
    }

    function queryAgain(){
        if($("#num").val()>1){
            $("#advSearchSubmit").click();
        }else {
            $("#num").val(0);
            $("#search-keyword").val('${title}'.replace(/,/g, "、").replace(/、$/,""));
            $("#title").val('${title}');
            $("#keywords,#keyword").val('${keyword}');
            $("#filterKeyword").val('${filterKeyword}');

            unset($("#title").val(),$("#keywords").val(),$("#filterKeyword").val());
            if($("#title").val()||$("#keywords").val()){
                tag = 0;
                $("#setContrast").show();
                $("#timeQuantum").show();
                unset($("#title").val(),$("#keywords").val(),$("#filterKeyword").val());
                toggleTabHot(1);
            }else{
                showMsgInfo(0, "请填写要查询的关键字！", 0);
            }
        }
    }

    //一个词 输入框
    function unset(title,keyword,filterKeyword){
        var curIds="";
        var html="";
        var count=0;
        if($("#advSearchSubmit:visible")){
            $("#advSearchSubmit").show();
        }
        if($("#riverButton:visible")){
            $("#riverButton").show();
        }

        $(".compUl> li").each(function(){
            if($(this).attr("class").indexOf('colorOne') != -1){
                $(".colorOne").remove();
            }else if($(this).attr("class").indexOf('colorTwo') != -1){
                $(".colorTwo").remove();
            }
        });
        if(keyword.indexOf(',') != -1){
            count=keyword.match(/,/g).length;
        }
        if(count>0){
            var keywordArr=[];
            keywordArr=keyword.split(",");
            var titleArr=[];
            titleArr=title.split(",");
            var filterKeywordArr=[];
            filterKeywordArr=filterKeyword.split(",");
            curIds="";
            for(var i=0;i<titleArr.length;i++){
                var tit=titleArr[i];
                var kw=keywordArr[i];
                var fk=filterKeywordArr[i];
                if(i==0){
                    if($.trim(kw)==""){
                        continue;
                    }else{
                        curIds += "colorOne";
                        html += '<li class="compLi comCtl colorOne" id="li_1">' +
                                //                         '<i onclick="addWord(1);" id="w1" title="添加组合词" class="addCompS">+</i>' +
                                '<i onclick="delWord(1);"class="compClose">×</i>' +
                                '<div id="kw1_1" class="comBorderL comBorderLOne">' +
                                '<div class="inner">' +
                                '<input name="word" class="comWord" maxlength="20" value="'+tit+'" autocomplete="off" id="title1_1"  onchange="onChangeInput(\'1_1\')"  placeholder="">' +
                                '<input id="keyword1_1" type="hidden" value="'+kw+'">'+
                                '<input id="filterKeyword1_1" type="hidden" value="'+fk+'">'+
                                '</div>' +
                                '<ul id="hotwords1" class="hotwords" style="display: none;width: 450px;">'+
                                '</ul>'+
                                '</div>'+
                                '<div id="kw1_2" style="display: none;" class="comBorderL">' +
                                '<span class="addicon">+</span>' +
                                '<div class="inner">' +
                                '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword1_2" placeholder="">' +
                                '</div>' +
                                '</div>' +
                                '<div id="kw1_3" style="display: none;" class="comBorderL">' +
                                '<span class="addicon">+</span>' +
                                '<div class="inner">' +
                                '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword1_3" placeholder="">' +
                                '</div>' +
                                '</div>' +
                                '</li>';
                        $(".compUl").append(html);
                        html = '';
                    }
                }
                if(i==1) {
                    if($.trim(kw)==""){

                    }else{
                        curIds += "colorTwo";
                        html += '<li class="compLi comCtl colorTwo" id="li_2">' +
                                //                         '<i onclick="addWord(2);" id="w1" title="添加组合词" class="addCompS">+</i>' +
                                '<i onclick="delWord(2);"class="compClose">×</i>' +
                                '<div id="kw2_1" class="comBorderL">' +
                                '<div class="inner">' +
                                '<input name="word" class="comWord" maxlength="20" value="'+tit+'" autocomplete="off" id="title2_1"  onchange="onChangeInput(\'2_1\')"  placeholder="">' +
                                '<input id="keyword2_1" type="hidden" value="'+kw+'">' +
                                '<input id="filterKeyword2_1" type="hidden" value="'+fk+'">' +
                                '</div>' +
                                '<ul id="hotwords2" class="hotwords" style="display: none;width: 450px;">' +
                                '</ul>' +
                                '</div>' +
                                '<div id="kw2_2" style="display: none;" class="comBorderL">' +
                                '<span class="addicon">+</span>' +
                                '<div class="inner">' +
                                '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword2_2" placeholder="">' +
                                '</div>' +
                                '</div>' +
                                '<div id="kw2_3" style="display: none;" class="comBorderL">' +
                                '<span class="addicon">+</span>' +
                                '<div class="inner">' +
                                '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword2_3" placeholder="">' +
                                '</div>' +
                                '</div>' +
                                '</li>';
                        $(".compUl").append(html);
                        html = '';
                    }
                }
            }
        }else{
            html += '<li class="compLi comCtl colorOne" id="li_1">' +
                    //             '<i onclick="addWord(1);" id="w1" title="添加组合词" class="addCompS">+</i>' +
                    '<i onclick="delWord(1);"class="compClose">×</i>' +
                    '<div id="kw1_1" class="comBorderL comBorderLOne">' +
                    '<div class="inner">' +
                    '<input name="word" class="comWord" maxlength="20" value="'+title+'" autocomplete="off" id="title1_1"  onchange="onChangeInput(\'1_1\')"  placeholder="">' +
                    '<input id="keyword1_1" type="hidden" value="'+keyword+'">'+
                    '<input id="filterKeyword1_1" type="hidden" value="'+filterKeyword+'">'+
                    '</div>' +
                    '<ul id="hotwords1" class="hotwords" style="display: none;width: 450px;">'+
                    '</ul>'+
                    '</div>' +
                    '<div id="kw1_2" style="display: none;" class="comBorderL">' +
                    '<span class="addicon">+</span>' +
                    '<div class="inner">' +
                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword1_2" placeholder="">' +
                    '</div>' +
                    '</div>' +
                    '<div id="kw1_3" style="display: none;" class="comBorderL">' +
                    '<span class="addicon">+</span>' +
                    '<div class="inner">' +
                    '<input name="word" class="comWord" maxlength="20" value="" autocomplete="off" id="keyword1_3" placeholder="">' +
                    '</div>' +
                    '</div>' +
                    '</li>';
            $(".compUl").append(html);
        }
        if($(".page-layout-content .compUl li:visible").length<2){
            $(".addComp").show();
        }else{
            $(".addComp").hide();
        }
    }
    var phone = false;
    var timeout="";
    //联想事件
    function thinkKeywords(obj,id){
        $("#keyWordLogs_ul").hide();
        var txt = $(obj).val();
        if(txt==""){
            return;
        }
        if(timeout!=""){
            window.clearTimeout(timeout);
        }
        timeout = setTimeout(function(){beginThink(txt,id)},1000);
    }
    function beginThink(txt,id){
        var data = {"thinkKeyword":txt};
        $.ajax({
            url : "${staticResourcePath}/view/hotSearch/thinkKeywords.action",
            type : "post",
            data:data,
            success : function(data){
                if(data&&data instanceof Array){
                    data = data[0];
                    if(data==null){
                        $("#hotwords").hide();
                    }
                    var html = '';
                    $.each(data,function(){
                        html += '<li style="cursor:pointer;" onclick="hotSearch(\''+this.title+'\',\''+this.keyword+'\',\''+this.filterKeyword+'\',\''+id+'\')"><a href="javascript:void(0)"><h1>'+this.title+'</h1></a>';
                        if(this.type==1){
                            html += '<em>来自地域';
                        }else if(this.type==2){
                            html += '<em>来自股票';
                        }else if(this.type==3){
                            html += '<em>来自明星';
                        }else if(this.type==4){
                            html += '<em>来自行业';
                        }else if(this.type==5){
                            html += '<em>来自品牌';
                        }else if(this.type==6){
                            html += '<em>来自人物';
                        }else if(this.type==7){
                            html += '<em>来自景区';
                        }else if(this.type==8){
                            html += '<em>来自地域';
                        }else if(this.type==9){
                            html += '<em>来自汽车';
                        }else if(this.type==10){
                            html += '<em>来自手机';
                        }else if(this.type==11){
                            html += '<em>来自美妆';
                        }else if(this.type==12){
                            html += '<em>来自金融';
                        }else if(this.type==13){
                            html += '<em>来自电脑';
                        }else if(this.type==14){
                            html += '<em>来自家电';
                        }else if(this.type==15){
                            html += '<em>来自食品';
                        }else if(this.type==16){
                            html += '<em>来自教育';
                        }
                        if(!this.category||this.category==""||this.category=="undefined"){
                            html += '</em></li>';
                        }else{
                            html += '（'+this.category+'）</em></li>';
                        }

                    });
                    if(id==0){
                        $("#hotwords").html(html);
                        $("#hotwords").show();
                    }else if(id==1){
                        $("#hotwords1").html(html);
                        $("#hotwords1").show();
                        $("#keyword1_1").val(txt);
                        $("#filterKeyword1_1").val("");
                    }else if(id==2){
                        $("#hotwords2").html(html);
                        $("#hotwords2").show();
                        $("#keyword2_1").val(txt);
                        $("#filterKeyword2_1").val("");
                    }else if(id==3){
                        $("#hotwords3").html(html);
                        $("#hotwords3").show();
                        $("#keyword3_1").val(txt);
                        $("#filterKeyword3_1").val("");
                    }
                }
            }
        })
    }
    $(function(){
        $(".phonemenu").on("touchend",function(){
            $("body").css("margin-left","50%");
            $(".page-sidebar-tree").animate({left:'0'});
            $(".page-layout").after("<div class='modal-backdrop fade in'></div>");
        });

        $("body").on("touchend",".modal-backdrop",function(){
            $("body").removeAttr("style");
            $(".page-sidebar-tree").animate({left:'-50%'});
            $(this).remove();
        });
        $.get('${staticResourcePath}/json-template/china.json', function (chinaJson) {
            echarts.registerMap('china', chinaJson);
        });
        var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
        elems.forEach(function(html) {
            var switchery = new Switchery(html);
        });

        $(".info_close").hide();
        if($("#singleShare").val()==1){
            document.title = $("#title").val()+'的24小时网络热度辣么高，竟然是因为...'
        }

        /* $.datepicker.regional['zh-CN'] = {
            changeMonth: true,
            changeYear: true,
            clearText: '清除',
            clearStatus: '清除已选日期',
            closeText: '确定',
            closeStatus: '不改变当前选择',
            prevText: '<上月',
            prevStatus: '显示上月',
            prevBigText: '<<',
            prevBigStatus: '显示上一年',
            nextText: '下月>',
            nextStatus: '显示下月',
            nextBigText: '>>',
            nextBigStatus: '显示下一年',
            currentText: '今天',
            currentStatus: '显示本月',
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
            monthStatus: '选择月份',
            yearStatus: '选择年份',
            weekHeader: '周',
            weekStatus: '年内周次',
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
            dayStatus: '设置 DD 为一周起始',
            dateStatus: '选择 m月 d日, DD',
            dateFormat: 'yy-mm-dd',
            firstDay: 1,
            initStatus: '请选择日期',
            isRTL: false
        };
        $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
        if($("#timeQuantum:visible")) {
            if ($('#showStarttime').val() == '') {
                $('#showStarttime').val(startTime());
            }

            if ($('#showEndtime').val() == "") {
                $('#showEndtime').val(endTime());
            }

            var str =$("#showStarttime").val();
            str = str.replace(/-/g,"/");
            var curDate = new Date(str);
            //var minDate=new Date(curDate.getTime() - 12*24*60*60*1000*30);

            var strE =$("#showEndtime").val();
            strE = strE.replace(/-/g,"/");
            var curDateE = new Date(strE);
            //var minDateE=new Date(curDateE.getTime() - 12*24*60*60*1000*30);

            $('#showStarttime,#showEndtime').datetimepicker({
				minDate:'2017-01-01 00:00:00',
                maxDate:new Date(),
                timeInput: true,
                showSecond:true,
                timeFormat: 'HH:mm:ss',
                closeText:'确定'
            })
        } */
//         toggleTabHot(1);
        SetRQ('${date}');

        if('${title}' != '' || '${keyword}' != ''){
            queryAgain();
        }

        //绑定回车事件
        $('.comWord').keydown(function(event) {
            if (event.keyCode == 13) {
                event.preventDefault();
                $(this).change();
                goSearch(2, 24);
            }
        });

    });
</script>
<link rel="stylesheet" type="text/css"
      href="${staticResourcePath}/css/openTools/newgoSearch.css">
<link rel="stylesheet" type="text/css"
      href="${staticResourcePath}/css/case/style.css">
<link rel="stylesheet" type="text/css"
      href="${staticResourcePath}/css/case/common.css">
<link rel="stylesheet" type="text/css"
      href="${staticResourcePath}/css/case/font-icon.css">
</body>
</html>