<#include "../../top.ftl" >
<c:set var = "rankType" value="2"></c:set>
<%@ include file="../rankTop.jsp" %>
<!--头部导航栏 -->
<nav class="subnav" id="subnav" >
	<div style="margin-left:10px;">
		<span id="s0"class="active" onClick="changeRankType(0);">全部</span>
	</div>
	<div>
		<span id="s1"onClick="changeRankType(1);">男演员</span>
	</div>
	<div>
		<span id="s2"onClick="changeRankType(2);">女演员</span>
	</div>
	<div>
		<span id="s3"onClick="changeRankType(3);">男歌手</span>
	</div>
	<div style="margin-left:10px;">
		<span id="s4"onClick="changeRankType(4);">女歌手</span>
	</div>
	<div>
		<span id="s5"onClick="changeRankType(5);">组合</span>
	</div>


</nav>
<script type="text/javascript">document.title = "微热点";saveOperateLog('明星榜','1020');</script>
<script type="text/javascript">
    //实现对比按钮 废除效果
    function Move(obj,type) {
        if(!"${admin}"){
            window.location.href=actionBase+"/indexLocal";
            return;
        }


        //type：0-全部  1- 2-沪市A股 ... 以此类推
        if(type==0){
            if($("th[class='stockList2 cur0']").length>2){
                alertMsg("您最多可以选择三个");
                return;
            }
            $(obj).addClass("cur0");
            //console.log("type --- "+type);
            $("#count0").html($("th[class='stockList2 cur0']").length);//已选计数
        }else if(type==1){
            if($("th[class='stockList2 cur1']").length>2){
                alertMsg("您最多可以选择三个");
                return;
            }
            $(obj).addClass("cur1");
            //console.log("type --- "+type);
            $("#count1").html($("th[class='stockList2 cur1']").length);
        }else if(type==2){
            if($("th[class='stockList2 cur2']").length>2){
                alertMsg("您最多可以选择三个");
                return;
            }
            $(obj).addClass("cur2");
            //console.log("type --- "+type);
            $("#count2").html($("th[class='stockList2 cur2']").length);
        }else if(type==3){
            if($("th[class='stockList2 cur3']").length>2){
                alertMsg("您最多可以选择三个");
                return;
            }
            $(obj).addClass("cur3");
            //console.log("type --- "+type);
            $("#count3").html($("th[class='stockList2 cur3']").length);
        }else if(type==4){
            if($("th[class='stockList2 cur4']").length>2){
                alertMsg("您最多可以选择三个");
                return;
            }
            $(obj).addClass("cur4");
            //console.log("type --- "+type);
            $("#count4").html($("th[class='stockList2 cur4']").length);
        }else if(type==5){
            if($("th[class='stockList2 cur5']").length>2){
                alertMsg("您最多可以选择三个");
                return;
            }
            $(obj).addClass("cur5");
            //console.log("type --- "+type);
            $("#count5").html($("th[class='stockList2 cur5']").length);
        }
        if($(obj).find("img").eq(0).attr("src").indexOf("compared") == -1){
            if(type == 0){
                var comparedLeft =  $("#collectBox").offset().left+15;
                var comparedTop = $("#collectBox").offset().top -$(document).scrollTop();
            }else{
                var comparedLeft =  $("#count"+type).parent(".vs2").offset().left+15;
                var comparedTop = $("#count"+type).parent(".vs2").offset().top -$(document).scrollTop();
            }
            $(obj).find(".compared").show();
            console.log($(obj).scrollTop() +","+ $(obj).offset().top+","+$(document).scrollTop());
            $(obj).find(".compared").css({
                "position": "fixed",
                "z-index": "500",
                "width": "50px",
                "height": "30px",
                "left":  $(obj).offset().left  + 10  + "px",
                "top": ($(obj).offset().top-$(document).scrollTop()) + "px",
            });
            $(obj).find(".compared").animate({
                    "left": $(obj).offset().left - 30 + "px",
                    "top":  ($(obj).offset().top-$(document).scrollTop()) + "px",
                    "width": "50px",
                    "height": "30px"
                },500,
                function() {
                    $(obj).find(".compared").animate({
                        "left": comparedLeft + "px",
                        "top":comparedTop+ "px",
                        "width": "10px",
                        "height": "10px"
                    },500).fadeTo(0).hide(0);
                });

            $(obj).find("img").eq(0).attr("src","../../images/stockList/compared.png");
        }
    }

</script>
<script type="text/javascript">
    function changeRankType(type){
        if(type==0){
            $('#All').css('display','block');
            $('#SA').css('display','none');
            $("#subnav").find("span").attr("class","");
            $("#s0").addClass('active');
            saveOperateLog('明星榜_全部','1020');

        }else{
            $("#starList").html(
                "<tr class='thbg'>"
                +	"<th colspan='2' class='stockList4'>名称<br></th>"
                +	"<th class='stockList4'>全网信息量<br></th>"
                +	"<th class='stockList4'>热度指数<br></th>"
                +	"<th class='stockList4'>趋势<br></th>"
                +"</tr>");
            var moreButton = "";
            var clickType="";
            var moreTable = "";
            var clickCp = "";
            var vsCount = "";
            if(type==1){
                moreButton = "'bMa'";
                clickType="'getMore(1,1);'";
                moreTable = "'moreMa'";
                clickCp = "'goToCp(1);'";
                vsCount = "'count1'";
            }else if(type==2){
                moreButton = "'bFa'";
                clickType="'getMore(2,2);'";
                moreTable = "'moreFa'";
                clickCp = "'goToCp(2);'";
                vsCount = "'count2'";
            }else if(type==3){
                moreButton = "'bMs'";
                clickType="'getMore(3,3);'";
                moreTable = "'moreMs'";
                clickCp = "'goToCp(3);'";
                vsCount = "'count3'";
            }else if(type==4){
                moreButton = "'bFs'";
                clickType="'getMore(4,4);'";
                moreTable = "'moreFs'";
                clickCp = "'goToCp(4);'";
                vsCount = "'count4'";
            }else if(type==5){
                moreButton = "'bCo'";
                clickType="'getMore(5,5);'";
                moreTable = "'moreCo'";
                clickCp = "'goToCp(5);'";
                vsCount = "'count5'";
            }
            $("#starList").empty();
            $("#moreButton").empty();
            $("#toCp").empty();
            $("#starList").html(
                "<tr class='thbg'>"
                +	"<th colspan='2' class='stockList4'>名称<br></th>"
                +	"<th class='stockList4'>全网信息量<br></th>"
                +	"<th class='stockList4'>热度指数<br></th>"
                +	"<th class='stockList4'>趋势<br></th>"
                +"</tr>");
            $("#moreButton").html(
                "<button class='moreButton'"+"id="+moreButton+"onClick="+clickType+">更多</button>");
            $("#moreTable").html("<table id="+moreTable+"></table>");
            /* $("#toCp").html(
                    "<div onClick="+clickCp+">"
                    +	"<div class='vs2'>"
                    +		"<div id="+vsCount+"class='vsText'>0</div>"
                    +	"</div>"
                    +"</div>"); */
            var add = "#s"+type;
            if($(add).hasClass("disabled")){
                return;
            }else{
                $.ajax({
                    url : actionBase + '/view/hotEvent/getMoreStarList.action?flag=0&listType='+type,
                    type : 'POST',
                    success : function(result) {
                        var listPo="#starList";

                        var more = "more"+type;
                        if(result.length == 0){
                            $("#SA").css("display","none");
                            $("#None").css("display","block");
                            $("#None").append(
                                "<div style='text-align:center;padding-top:20px' >"
                                +"<p style='display:inline;font-size: 14px;'>"
                                +"<img src='<%=staticResourcePath %>/images/shouye/warn.png'><br/>对不起,暂无信息!</p></div>"
                            );
                        }else{
                            var listType = result[0].listType;
                            if(result.length < 10){
                                $("#moreButton").css("display","none");
                            }else{
                                $("#moreButton").css("display","block");
                            }
                            for(var i =0;i < result.length;i++){
                                if(result[i].rankDifference>0){
                                    var sequencestr = "<td class='stockList'><div class='sort'>"+result[i].sequence+"</div>";
                                    if(result[i].sequence>3){
                                        sequencestr = "<td class='stockList'><div class='sort1'>"+result[i].sequence+"</div>";
                                    }

                                    if(i%2==1){
                                        var background = "style = 'background:#f2f5fa;'";
                                    }else{
                                        var background = "style = 'background:#fff;'";
                                    }
                                    $(listPo).append(
                                        "<tr "+background+">"
                                        +sequencestr
                                        +	"</td><td><a onClick='goHot(this);'  value='"+result[i].title+"'>"
                                        +result[i].title
                                        +	"</a>"
                                        +	"<input type='hidden' name='keyword' value='"+result[i].keyword+"'>"
                                        +	"<input type='hidden' name='name' value='"+result[i].title+"'>"
                                        +	"<input type='hidden' name='filterKeyword' value='"+result[i].filterKeyword+"'>"
                                        +	"<input type='hidden' name='categoryId' value='"+result[i].categoryId+"'>"
                                        +	"<input type='hidden' name='type' value='"+result[i].type+"'>"
                                        +"</td>"
                                        +"<td class='stockList'>"+result[i].numberDay+"<br></td>"
                                        +"<td class='stockList'>"+result[i].ratioHotDay+"<br></td>"
                                        +"<td class='stockList'>"
                                        +"<img style='height:15px;'src='../../images/stockList/up.png'/>"
                                        +"<br></td>"
                                        +"</tr>");
                                }else if(result[i].rankDifference<0){
                                    var sequencestr = "<td class='stockList'><div class='sort'>"+result[i].sequence+"</div>";
                                    if(result[i].sequence>3){
                                        sequencestr = "<td class='stockList'><div class='sort1'>"+result[i].sequence+"</div>";
                                    }

                                    if(i%2==1){
                                        var background = "style = 'background:#f2f5fa;'";
                                    }else{
                                        var background = "style = 'background:#fff;'";
                                    }
                                    $(listPo).append(
                                        "<tr "+background+">"
                                        +sequencestr
                                        +	"</td><td><a onClick='goHot(this);'  value='"+result[i].title+"'>"
                                        +result[i].title
                                        +	"</a>"
                                        +	"<input type='hidden' name='keyword' value='"+result[i].keyword+"'>"
                                        +	"<input type='hidden' name='name' value='"+result[i].title+"'>"
                                        +	"<input type='hidden' name='filterKeyword' value='"+result[i].filterKeyword+"'>"
                                        +	"<input type='hidden' name='categoryId' value='"+result[i].categoryId+"'>"
                                        +	"<input type='hidden' name='type' value='"+result[i].type+"'>"
                                        +"</td>"
                                        +"<td class='stockList'>"+result[i].numberDay+"<br></th>"
                                        +"<td class='stockList'>"+result[i].ratioHotDay+"<br></th>"
                                        +"<td class='stockList'>"
                                        +"<img style='height:15px;'src='../../images/stockList/down.png'/>"
                                        +"<br></td>"
                                        +"</tr>");
                                }else{
                                    var sequencestr = "<td class='stockList'><div class='sort'>"+result[i].sequence+"</div>";
                                    if(result[i].sequence>3){
                                        sequencestr = "<td class='stockList'><div class='sort1'>"+result[i].sequence+"</div>";
                                    }

                                    if(i%2==1){
                                        var background = "style = 'background:#f2f5fa;'";
                                    }else{
                                        var background = "style = 'background:#fff;'";
                                    }
                                    $(listPo).append(
                                        "<tr "+background+">"
                                        +sequencestr
                                        +	"</td><td><a onClick='goHot(this);'  value='"+result[i].title+"'>"
                                        +result[i].title
                                        +	"</a>"
                                        +	"<input type='hidden' name='keyword' value='"+result[i].keyword+"'>"
                                        +	"<input type='hidden' name='name' value='"+result[i].title+"'>"
                                        +	"<input type='hidden' name='filterKeyword' value='"+result[i].filterKeyword+"'>"
                                        +	"<input type='hidden' name='categoryId' value='"+result[i].categoryId+"'>"
                                        +	"<input type='hidden' name='type' value='"+result[i].type+"'>"
                                        +"</td>"
                                        +"<td class='stockList'>"+result[i].numberDay+"<br></td>"
                                        +"<td class='stockList'>"+result[i].ratioHotDay+"<br></td>"
                                        +"<td class='stockList'>"+"-<br></td>"
                                        +"</tr>");
                                }
                            }
                            for(var i =1;i<6 ;i++){
                                var remove = "#s"+i;
                                $(remove).removeClass('disabled');
                            }
                        }

                    },error:function(XMLHttpRequest, textStatus, errorThrown){
                        console.log(XMLHttpRequest);
                        console.log(textStatus);
                        console.log(errorThrown);
                    }
                });
            }

            if(type==1){
                $('#All').css('display','none');
                $('#SA').css('display','block');
                $("#subnav").find("span").attr("class","");
                $("#s1").addClass('active disabled');
                $("#None").empty();
                $("#None").css("display","none");
                saveOperateLog('明星榜_男演员','1020');
            }else if(type == 2){
                $('#All').css('display','none');
                $('#SA').css('display','block');
                $("#subnav").find("span").attr("class","");
                $("#s2").addClass('active disabled');
                $("#None").empty();
                $("#None").css("display","none");
                saveOperateLog('明星榜_女演员','1020');
            }else if(type == 3){
                $('#All').css('display','none');
                $('#SA').css('display','block');
                $("#subnav").find("span").attr("class","");
                $("#s3").addClass('active disabled');
                $("#None").empty();
                $("#None").css("display","none");
                saveOperateLog('明星榜_男歌手','1020');
            }else if(type == 4){
                $('#All').css('display','none');
                $('#SA').css('display','block');
                $("#subnav").find("span").attr("class","");
                $("#s4").addClass('active disabled');
                $("#None").empty();
                $("#None").css("display","none");
                saveOperateLog('明星榜_女歌手','1020');
            }else if(type == 5){
                $('#All').css('display','none');
                $('#SA').css('display','block');
                $("#subnav").find("span").attr("class","");
                $("#s5").addClass('active disabled');
                $("#None").empty();
                $("#None").css("display","none");
                saveOperateLog('明星榜_组合','1020');
            }
        }
    }
</script>
<section class="section2" style="margin-top:-12px">
	<article class="context">
		<!--每日热点排行报告 start-->
		<!--报告头部 start -->
		<input type="hidden" id="viewUri" value="${viewUri}">
		<input type="hidden" id="pageAll" value="2">
		<input type="hidden" id="pageMa" value="2">
		<input type="hidden" id="pageFa" value="2">
		<input type="hidden" id="pageMs" value="2">
		<input type="hidden" id="pageFs" value="2">
		<input type="hidden" id="pageCo" value="2">
		<div id="All" style="display:block">
			<!-- <div onClick="goToCp(0);">
                <div class="vs2"id="collectBox" >
                    <div id="count0" class="vsText">0</div>
                </div>
            </div>   -->

			<!--报告头部 end -->
			<table id = "alltable" style="border:solid 0px #eef7fc">
				<tr class="thbg">
					<th colspan="2" class="stockList4">名称<br></th>
					<th class="stockList4">全网信息量<br></th>
					<th class="stockList4">热度指数<br></th>
					<th class="stockList4">趋势<br></th>
				</tr>

				<s:iterator value="#attr.starListAll" status="st" var="ct">
					<tr style="height:52px; <s:if test = "#st.even">background:#f2f5fa;</s:if>">
						<td class="stockList2">
							<div>
								<div class="sort<s:if test='#st.index >= 3'>1</s:if>" ><s:property value="#st.count"/></div>
							</div>
						</td>
						<td>
							<a href="javascript:void(0)" onclick="goHot(this);" >
								<s:property value="#ct.title" escape="false"/><br>
							</a>
							<input type="hidden" name="keyword" value="<s:property value="#ct.keyword"/>">
							<input type="hidden" name="name" value="<s:property value="#ct.title"/>">
							<input type="hidden" name="filterKeyword" value="<s:property value="#ct.filterKeyword"/>">
							<input type="hidden" name="categoryId" value="<s:property value="#ct.categoryId"/>">
							<input type="hidden" name="type" value="<s:property value="#ct.type"/>">
						</td>
						<td class="stockList2"><s:property value="#ct.numberDay"/><br></td>
						<td class="stockList2"><s:property value="#ct.ratioHotDay"/><br></td>
						<td class="stockList2">
							<s:if test="#ct.rankDifference > 0">
								<img style="height:15px;"src="../../images/stockList/up.png"/>
							</s:if>
							<s:elseif test="#ct.rankDifference < 0">
								<img style="height:15px;"src="../../images/stockList/down.png"/>
							</s:elseif>
							<s:else>
								-
							</s:else><br>
						</td>
					</tr>
				</s:iterator>
			</table>
			<table  id="moreAll"style="border:solid 2px #eef7fc;">

			</table>

			<button id="bAll" class="moreButton" onClick="getMore(0,0);">更多</button></div>

		</div>

		<div id="SA" style="display:none;">
			<input type="hidden" id="viewUri" value="${viewUri}">
			<!-- <div id="toCp">

            </div> -->

			<table id="starList"style="border:solid 0px #eef7fc"></table>
			<div id="moreTable">

			</div>

			<div id="moreButton">

			</div>
		</div>
		<div id="None" style="display:none;"></div>
	</article>
	<!--
    <div class="stockList2" style="text-align: center;">
         <a class="icon-share">分享</a>
    </div>	-->
</section>
<form name="frmGohot" action="<%=njxBasePath %>/view/heatsearch/searchHeatResult.shtml?heatShareCode=${heatShareCode}"method="post">
	<input name="keyword1" id="hotKeyword" type="hidden" value="">
	<input name="searchKeyword" id="hotsearchKeyword" type="hidden" value="">
	<input name="filterKeyword1" id="hotfilterKeyword" type="hidden" value="">
	<input name="categoryId1" id="hotcategoryId" type="hidden" value="">
	<input name="type1" id="hottype" type="hidden" value="">
</form>
<c:set var = "type" value="3"></c:set>
<%-- <%@ include file="../bottomNav.jsp" %> --%>
<%@ include file="../pay/productPackage.jsp" %>
<!--分享弹出框 -->
<div id="shareNewsPOP" class="footPOP" style="display: none;">
	<ul class="footList fenxiang bdsharebuttonbox" data-tag="share_1">
		<li><a class="icon icon_2" data-cmd="tsina"></a>新浪微博</li>
		<li><a class="icon icon_1" data-cmd="weixin"></a>微信</li>
		<li><a class="icon icon_4" data-cmd="sqq"></a>QQ好友</li>
		<li><a class="icon icon_9" data-cmd="tqq"></a>腾讯微博</li>
		<li><a class="icon icon_5" data-cmd="qzone"></a>QQ空间</li>
	</ul>
</div>
<div class="zhezhao" ></div>

<!--<img style="height:23px;"src="../../images/stockList/compare.png"/> 弹窗start-->
<!--
<form name="frmPopWin1" action="" method="post">
<input type="hidden" id="stockTitle" name="pak.stockTitle">
<input type="hidden" value="${token}" name = "token">
<input type="hidden" name="pak.keyword1" id="keyword1" value="">
<input type="hidden" name="pak.keyword2" id="keyword2" value="">
<input type="hidden" name="pak.keyword3" id="keyword3" value="">
<input type="hidden" name="pak.keyword4" id="keyword4" value="">
<input type="hidden" name="pak.keyword5" id="keyword5" value="">
<input type="hidden" name="pak.keyword6" id="keyword6" value="">
<input type="hidden" name="pak.name1" id="name1" value="">
<input type="hidden" name="pak.name2" id="name2" value="">
<input type="hidden" name="pak.name3" id="name3" value="">
<input type="hidden" name="pak.name4" id="name4" value="">
<input type="hidden" name="pak.name5" id="name5" value="">
<input type="hidden" name="pak.name6" id="name6" value="">
<div class="prompPOP2" id="compareModal"  style="display: none;width: 95%;left: 2.5%;height:100px;>
<div class="modal-dialog" style="margin-top: 100px;">
<div class="prConBox" style="border: solid 1px #f3f8f9;">
<div style="background-color:#f3f8f9;height:40px;line-height:40px;color:#181b1c">
<h4 class="tit" style="font-size:18px;">
提示<span style="font-size:14px">(您最多可以选择六个)</span>
<a href="javascript:void(0)" onClick="hideUpdate();"class="cancel">×</a>
</h4>
</div>
<div class="PromptCon">
<ul class="bwList" id="navDiv">
<img src="<%=staticResourcePathH5 %>/images/loading.gif" /> Loading<span class="ani_dot">...</span>
</ul>
</div>
<div style="margin-left:80px;margin-bottom:10px;background-color:#fc6921;height:30px;color:#FFF;width:120px;">
<a href="javascript:void(0)" onClick="toCompare();">
<h4 class="tit" style="font-size:18px;color:#FFF;text-align:center;">开始对比</h4>
</a>
</div>
</div>
</div>
</div>
</form>-->
<!--<img style="height:23px;"src="../../images/stockList/compare.png"/>弹窗 end-->
<!--消息弹出框 -->
<div id="msgPOP" class="prompPOP" style="display: none;width: 80%;left: 10%;">
	<div class="prConBox">
		<div class="tit"><h1>提示</h1><a href="javascript:void(0)" class="cancel">×</a></div>

		<div class="PromptCon" id="msgContent" style="text-align: center;"></div>

		<div class="bottom"><a href="javascript:void(0);" class="submitBtn ">确定</a></div>
	</div>
</div>
<form action="<%=njxBasePath %>/view/heatsearch/searchHeatResult.shtml?heatShareCode=${heatShareCode}" name = "searchForm" method="post">
	<input id ="searchKeyword" name="searchKeyword" type="hidden"  value="">
	<input id = "searchKeyword1" name="searchKeyword1"type="hidden" value="">
	<input id = "keyword1" name="keyword1"  type="hidden" value="">
	<input id = "filterKeyword1" name="filterKeyword1"  type="hidden" value="">
	<input id = "categoryId1" name="categoryId1"  type="hidden" value="">
	<input id = "type1" name="type1"  type="hidden" value="">
	<input id = "searchKeyword2" type="hidden" name="searchKeyword2"value="">
	<input id = "keyword2" name="keyword2"  type="hidden" value="">
	<input id = "filterKeyword2" name="filterKeyword2"  type="hidden" value="">
	<input id = "categoryId2" name="categoryId2"  type="hidden" value="">
	<input id = "type2" name="type2"  type="hidden" value="">
	<input id = "searchKeyword3" type="hidden" name="searchKeyword3"value="">
	<input id = "keyword3" name="keyword3"  type="hidden" value="">
	<input id = "filterKeyword3" name="filterKeyword3"  type="hidden" value="">
	<input id = "categoryId3" name="categoryId3"  type="hidden" value="">
	<input id = "type3" name="type3"  type="hidden" value="">
	<div class="prompPOP2" id="compareModal"  style="display: none;width: 95%;left: 2.5%;height:100px;>
		<div class="modal-dialog" style="margin-top: 100px;">
	<div class="prConBox" style="height:auto;border: solid 1px #f3f8f9;">
		<div style="background-color:#f3f8f9;height:40px;line-height:40px;color:#666">
			<h4 class="tit" style="font-size:16px;height:40px;line-height:40px;">
				&nbsp;提示<span style="font-size:16px">(您最多可以选择3个)</span>
				<a href="javascript:void(0)" onClick="hideUpdate();"class="cancel">×</a>
			</h4>
		</div>
		<div class="PromptCon2">
			<ul class="bwList" id="navDiv">
				<img src="<%=staticResourcePathH5 %>/images/loading.gif?v=<%=SYSTEM_INIT_TIME %>" /> Loading<span class="ani_dot">...</span>
			</ul>
		</div>
		<div style="margin-left:50px;background-color:#fc6921;height:30px;color:#FFF;width:30%;border-radius: 5px;">
			<a href="javascript:void(0)" onClick="showBuy({type:13})">
				<h4 class="tit" style="font-size:14px;color:#FFF;text-align:center;">开始对比<br></h4>
			</a>
		</div>
		<div style="margin-left:50%;margin-top:-30px;margin-bottom:20px;background-color:#f9f7fa;height:30px;color:#FFF;width:30%;">
			<a href="">
				<h4 class="tit" style="font-size:14px;;text-align:center;background-color: #e5e5e5;border-radius: 5px;">重新选择<br></h4>
			</a>
		</div>
	</div>
	</div>
	</div>
</form>

<script src="<%=staticResourcePathH5%>/js/swa/bootstrap/js/bootstrap.min.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<script src="<%=staticResourcePathH5%>/js/swa/jquery.tmpl.min.js?v=<%=SYSTEM_INIT_TIME %>"></script>

<script type="text/javascript">
    $(function(){
        $(".icon-share").on("click",function(){
            $(".zhezhao").show(300);
            $("#shareNewsPOP").fadeIn(300);
            $(".footPOP").addClass('downShow');
            $(".footPOP").removeClass('downOut');
            $(".prompPOP").removeClass('scaleShow');
            $(".prompPOP").addClass('scaleOut');
            shareHotlistCallBack();
        });
        $("#topcontrol").css('bottom','59px');
        $("#topcontrol").css('right','25px');
    })

    //分享的beforeclick事件
    function bdShareBeforeClick(cmd,config) {
        config.bdText = bdShareTitle;
        config.bdDesc = bdShareDesc;
        config.bdUrl = bdShareUrl;

        return config;
    }
    function shareHotlistCallBack(){
        var url = "";
        url = $("#viewUri").val()+'/view/hotEvent/getStockList.action';
        //console.log(url);
        bdShareTitle ="股榜";
        bdShareDesc = '自定义分享摘要';
        bdShareUrl = url;
        //console.log(bdShareUrl);
        window._bd_share_config = {
            common : {
                onBeforeClick : bdShareBeforeClick
            },
            share : []
        }
        $(".footPOP a").off("click");
        with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
    }
</script>
<script type="text/javascript">
    function goToCp(type){

        if(!"${admin}"){
            window.location.href=actionBase+"/indexLocal";
            return;
        }

        var keywordsJson = {};
        var keyword="";
        var name="";
        var checked="";
        var filterKeyword="";
        var categoryId="";
        var type2="";
        //if(type==0){
        $("th[class='stockList2 cur"+type+"']").each(function(i){
            var inputObj = $(this).find('input[name=keyword]');
            keywordsJson["keyword"+(i+1)] = $(inputObj).val();
            keyword += $(inputObj).val()+"_";
            var inputObj1 = $(this).find('input[name=name]');
            keywordsJson["title"+(i+1)] = $(inputObj1).val();
            name += $(inputObj1).val()+",";
            var inputObj2 = $(this).find('input[name=filterKeyword]');
            keywordsJson["filterKeyword"+(i+1)] = $(inputObj2).val();
            filterKeyword += $(inputObj2).val()+",";
            var inputObj3 = $(this).find('input[name=categoryId]');
            keywordsJson["categoryId"+(i+1)] = $(inputObj3).val();
            categoryId += $(inputObj3).val()+",";
            var inputObj4 = $(this).find('input[name=type]');
            keywordsJson["type"+(i+1)] = $(inputObj4).val();
            type2 += $(inputObj4).val()+",";
        });
        checked = $("th[class='stockList2 cur"+type+"']").length;
        /* }else if(type==1){
            $("th[class='stockList2 cur1']").each(function(){
                var inputObj = $(this).find('input[name=keyword]');
                keyword += $(inputObj).val()+"_";
                var inputObj1 = $(this).find('input[name=name]');
                name += $(inputObj1).val()+",";
                var inputObj2 = $(this).find('input[name=filterKeyword]');
                filterKeyword += $(inputObj2).val()+",";
                var inputObj3 = $(this).find('input[name=categoryId]');
                categoryId += $(inputObj3).val()+",";
                var inputObj4 = $(this).find('input[name=type]');
                type2 += $(inputObj4).val()+",";
            });
            checked = $("th[class='stockList2 cur1']").length;
        }else if(type==2){
            $("th[class='stockList2 cur2']").each(function(){
                var inputObj = $(this).find('input[name=keyword]');
                keyword += $(inputObj).val()+"_";
                var inputObj1 = $(this).find('input[name=name]');
                name += $(inputObj1).val()+",";
                var inputObj2 = $(this).find('input[name=filterKeyword]');
                filterKeyword += $(inputObj2).val()+",";
                var inputObj3 = $(this).find('input[name=categoryId]');
                categoryId += $(inputObj3).val()+",";
                var inputObj4 = $(this).find('input[name=type]');
                type2 += $(inputObj4).val()+",";
            });
            checked = $("th[class='stockList2 cur2']").length;
        }else if(type==3){
            $("th[class='stockList2 cur3']").each(function(){
                var inputObj = $(this).find('input[name=keyword]');
                keyword += $(inputObj).val()+"_";
                var inputObj1 = $(this).find('input[name=name]');
                name += $(inputObj1).val()+",";
                var inputObj2 = $(this).find('input[name=filterKeyword]');
                filterKeyword += $(inputObj2).val()+",";
                var inputObj3 = $(this).find('input[name=categoryId]');
                categoryId += $(inputObj3).val()+",";
                var inputObj4 = $(this).find('input[name=type]');
                type2 += $(inputObj4).val()+",";
            });
            checked = $("th[class='stockList2 cur3']").length;
        }else if(type==4){
            $("th[class='stockList2 cur4']").each(function(){
                var inputObj = $(this).find('input[name=keyword]');
                keyword += $(inputObj).val()+"_";
                var inputObj1 = $(this).find('input[name=name]');
                name += $(inputObj1).val()+",";
                var inputObj2 = $(this).find('input[name=filterKeyword]');
                filterKeyword += $(inputObj2).val()+",";
                var inputObj3 = $(this).find('input[name=categoryId]');
                categoryId += $(inputObj3).val()+",";
                var inputObj4 = $(this).find('input[name=type]');
                type2 += $(inputObj4).val()+",";
            });
            checked = $("th[class='stockList2 cur4']").length;
        }else if(type==5){
            $("th[class='stockList2 cur5']").each(function(){
                 var inputObj = $(this).find('input[name=keyword]');
                keyword += $(inputObj).val()+"_";
                var inputObj1 = $(this).find('input[name=name]');
                name += $(inputObj1).val()+",";
                var inputObj2 = $(this).find('input[name=filterKeyword]');
                filterKeyword += $(inputObj2).val()+",";
                var inputObj3 = $(this).find('input[name=categoryId]');
                categoryId += $(inputObj3).val()+",";
                var inputObj4 = $(this).find('input[name=type]');
                type2 += $(inputObj4).val()+",";
            });
            checked = $("th[class='stockList2 cur5']").length;
        } */
        if(checked<2){
            alertMsg("请至少选择两个进行对比");
            return;
        }
        //console.log(name.length);
        var strsKeyword= new Array();
        strsKeyword=keyword.substring(0,keyword.length-1).split("_");
        var strsName= new Array();
        strsName=name.substring(0,name.length-1).split(",");
        var strsFilter= new Array();
        strsFilter=filterKeyword.substring(0,filterKeyword.length-1).split(",");
        var strsCategory= new Array();
        strsCategory=categoryId.substring(0,categoryId.length-1).split(",");
        var strsType= new Array();
        strsType=type2.substring(0,type2.length-1).split(",");
        //console.log(strsKeyword);
        //console.log(strsName);
        for(var i =1;i<4;i++){
            var keyword = "keyword"+i;
            document.getElementById(keyword).value = "";
            var n = "searchKeyword"+i;
            document.getElementById(n).value = "";
            var filter = "filterKeyword"+i;
            document.getElementById(filter).value = "";
            var ca = "categoryId"+i;
            document.getElementById(ca).value = "";
            var ty = "type"+i;
            document.getElementById(ty).value = "";
        }

        for(var i = 1;i < strsKeyword.length+1;i++){
            var keyword = "keyword"+i;
            document.getElementById(keyword).value = strsKeyword[i-1];
            var n = "searchKeyword"+i;
            document.getElementById(n).value = strsName[i-1];
            var m = "filterKeyword"+i;
            document.getElementById(m).value = strsFilter[i-1];
            var c = "categoryId"+i;
            document.getElementById(c).value = strsCategory[i-1];
            var t = "type"+i;
            document.getElementById(t).value = strsType[i-1];
        }

        //console.log(name.substring(0,name.length-1));

        $("#buyPrompt_jp13 input[name='packageCount']").val(strsName.length);
        //document.getElementById("keywords").value=name.substring(0,name.length-1);
        $("#keywords").val(JSON.stringify(keywordsJson));
        $(".zhezhao").show(300);
        $("#compareModal").css('display', 'block');
        $('#navDiv').empty();
        for(var i =1;i < strsKeyword.length+1;i++){
            $('#navDiv').append("<li class=''>"
                +"<em style='margin-right:20px;'>"+i+"</em>"
                +"<span>"+strsName[i-1]+"</span>"
                +"<input type='hidden' name='keywordName' value='"+strsKeyword[i-1]+"' />"
                +"<input type='hidden' name='name' value='"+strsName[i-1]+"' />"
                +"</li>"
            );
        }
        //showBuy({type:13});

    }

</script>
<script type="text/javascript">
    window.onload = function(){
        var url = window.location.href;
        var ps = url.split("#");
        try{
            if(ps[1] != 1){
                url += "#1";
            }else{
                window.location = ps[0];
            }
        }catch(ex){
            url += "#1";
        }
        //console.log("url = "+url);
        window.location.replace(url);

    };
    function hideUpdate(){
        $("#compareModal").css('display', 'none');
        $('.zhezhao').removeClass('downShow');
    }
    function goHot(obj){
        var keyword = $(obj).parent().find('input[name=keyword]').val();
        var name = $(obj).parent().find('input[name=name]').val();
        var filterKeyword = $(obj).parent().find('input[name=filterKeyword]').val();
        var categoryId = $(obj).parent().find('input[name=categoryId]').val();
        var type = $(obj).parent().find('input[name=type]').val();
        document.getElementById("hotKeyword").value=keyword;
        document.getElementById("hotsearchKeyword").value=name;
        document.getElementById("hotfilterKeyword").value=filterKeyword;
        document.getElementById("hotcategoryId").value=categoryId;
        document.getElementById("hottype").value=type;
        $.ajax({url:njxBasePath+"/doHeatShareCode.shtml",cache:false,async:false,success:function(result){
            if(result.status){
                $("form[name='frmGohot']").attr("action",njxBasePath+"/view/heatsearch/searchHeatResult.shtml?heatShareCode="+result.obj);
                var hotForm = $('form[name="frmGohot"]')[0];
                if (hotForm)
                    hotForm.submit();
            }
        }});
//console.log(keyword+"_"+name+"_"+filterKeyword);

    }

    function getMore(type,stockType){
        var page="";
        if(type==0){
            page = document.getElementById("pageAll").value;
            document.getElementById("bAll").setAttribute("disabled","disabled");
        }else if(type==1){
            page = document.getElementById("pageMa").value;
            document.getElementById("bMa").setAttribute("disabled","disabled");
        }else if(type==2){
            page = document.getElementById("pageFa").value;
            document.getElementById("bFa").setAttribute("disabled","disabled");
        }else if(type==3){
            page = document.getElementById("pageMs").value;
            document.getElementById("bMs").setAttribute("disabled","disabled");
        }else if(type==4){
            page = document.getElementById("pageFs").value;
            document.getElementById("bFs").setAttribute("disabled","disabled");
        }else if(type==5){
            page = document.getElementById("pageCo").value;
            document.getElementById("bCo").setAttribute("disabled","disabled");
        }
        $.ajax({
            url : actionBase + '/view/hotEvent/getMoreStarList.action?flag=1&pageMore='+page+'&listType='+stockType,
            type : 'POST',
            success : function(result) {
                //console.log(result);
                //console.log(result.length);
                var page = result[0].page;
                var listType = result[0].listType;
                var listPo = "";
                if(listType == 0){
                    document.getElementById("pageAll").value=result[0].page;
                    listPo = "#alltable";
                    $('#bAll').removeAttr("disabled");
                    if(result.length<10){
                        $('#bAll').css("display","none");
                    }
                }else if(listType == 1){
                    document.getElementById("pageMa").value=result[0].page;
                    listPo = "#starList";
                    $('#bMa').removeAttr("disabled");
                    if(result.length<10){
                        $('#bMa').css("display","none");
                    }
                }else if(listType ==2){
                    document.getElementById("pageFa").value=result[0].page;
                    listPo = "#starList";
                    $('#bFa').removeAttr("disabled");
                    if(result.length<10){
                        $('#bFa').css("display","none");
                    }
                }else if(listType ==3){
                    document.getElementById("pageMs").value=result[0].page;
                    listPo = "#starList";
                    $('#bMs').removeAttr("disabled");
                    if(result.length<10){
                        $('#bMs').css("display","none");
                    }
                }else if(listType ==4){
                    document.getElementById("pageFs").value=result[0].page;
                    listPo = "#starList";
                    $('#bFs').removeAttr("disabled");
                    if(result.length<10){
                        $('#bFs').css("display","none");
                    }
                }else if(listType =5){
                    document.getElementById("pageCo").value=result[0].page;
                    listPo = "#starList";
                    $('#bCo').removeAttr("disabled");
                    if(result.length<10){
                        $('#bCo').css("display","none");
                    }
                }
                for(var i =0;i < result.length;i++){
                    //$('#moreAll').append(result[i].city);
                    if(result[i].rankDifference>0){
                        var sequencestr = "<td class='stockList'><div class='sort'>"+result[i].sequence+"</div>";
                        if(result[i].sequence>3){
                            sequencestr = "<td class='stockList'><div class='sort1'>"+result[i].sequence+"</div>";
                        }

                        if(i%2==1){
                            var background = "style = 'background:#f2f5fa;'";
                        }else{
                            var background = "style = 'background:#fff;'";
                        }
                        $(listPo).append(
                            "<tr "+background+">"
                            +sequencestr
                            +	"</td><td><a onClick='goHot(this);'  value='"+result[i].title+"'>"
                            +result[i].title
                            +	"</a>"
                            +	"<input type='hidden' name='keyword' value='"+result[i].keyword+"'>"
                            +	"<input type='hidden' name='name' value='"+result[i].title+"'>"
                            +	"<input type='hidden' name='filterKeyword' value='"+result[i].filterKeyword+"'>"
                            +	"<input type='hidden' name='categoryId' value='"+result[i].categoryId+"'>"
                            +	"<input type='hidden' name='type' value='"+result[i].type+"'>"
                            +"</td>"
                            +"<td class='stockList'>"+result[i].numberDay+"<br></td>"
                            +"<td class='stockList'>"+result[i].ratioHotDay+"<br></td>"
                            +"<td class='stockList'>"
                            +"<img style='height:15px;'src='../../images/stockList/up.png'/>"
                            +"<br></td>"
                            +"</tr>");
                    }else if(result[i].rankDifference<0){
                        var sequencestr = "<td class='stockList'><div class='sort'>"+result[i].sequence+"</div>";
                        if(result[i].sequence>3){
                            sequencestr = "<td class='stockList'><div class='sort1'>"+result[i].sequence+"</div>";
                        }

                        if(i%2==1){
                            var background = "style = 'background:#f2f5fa;'";
                        }else{
                            var background = "style = 'background:#fff;'";
                        }
                        $(listPo).append(
                            "<tr "+background+">"
                            +sequencestr
                            +	"</td><td><a onClick='goHot(this);'  value='"+result[i].title+"'>"
                            +result[i].title
                            +	"</a>"
                            +	"<input type='hidden' name='keyword' value='"+result[i].keyword+"'>"
                            +	"<input type='hidden' name='name' value='"+result[i].title+"'>"
                            +	"<input type='hidden' name='filterKeyword' value='"+result[i].filterKeyword+"'>"
                            +	"<input type='hidden' name='categoryId' value='"+result[i].categoryId+"'>"
                            +	"<input type='hidden' name='type' value='"+result[i].type+"'>"
                            +"</td>"
                            +"<td class='stockList'>"+result[i].numberDay+"<br></td>"
                            +"<td class='stockList'>"+result[i].ratioHotDay+"<br></td>"
                            +"<td class='stockList'>"
                            +"<img style='height:15px;'src='../../images/stockList/down.png'/>"
                            +"<br></td>"
                            +"</tr>");
                    }else{
                        var sequencestr = "<td class='stockList'><div class='sort'>"+result[i].sequence+"</div>";
                        if(result[i].sequence>3){
                            sequencestr = "<td class='stockList'><div class='sort1'>"+result[i].sequence+"</div>";
                        }

                        if(i%2==1){
                            var background = "style = 'background:#f2f5fa;'";
                        }else{
                            var background = "style = 'background:#fff;'";
                        }
                        $(listPo).append(
                            "<tr "+background+">"
                            +sequencestr
                            +	"</td><td><a onClick='goHot(this);'  value='"+result[i].title+"'>"
                            +result[i].title
                            +	"</a>"
                            +	"<input type='hidden' name='keyword' value='"+result[i].keyword+"'>"
                            +	"<input type='hidden' name='name' value='"+result[i].title+"'>"
                            +	"<input type='hidden' name='filterKeyword' value='"+result[i].filterKeyword+"'>"
                            +	"<input type='hidden' name='categoryId' value='"+result[i].categoryId+"'>"
                            +	"<input type='hidden' name='type' value='"+result[i].type+"'>"
                            +"</td>"
                            +"<td class='stockList'>"+result[i].numberDay+"<br></td>"
                            +"<td class='stockList'>"+result[i].ratioHotDay+"<br></td>"
                            +"<td class='stockList'>"+"-<br></td>"
                            +"</tr>");
                    }
                }
            },error:function(XMLHttpRequest, textStatus, errorThrown){
                //console.log(XMLHttpRequest);
                //console.log(textStatus);
                //console.log(errorThrown);
            }
        });

    }

    // 消息弹窗
    function alertMsg(content) {
        $('#msgContent').text(content);
        $(".zhezhao").show(300);
        $("#msgPOP").css("display","block");
        //$("body").css({overflow:"hidden"});    //禁用滚动条
        $(".prompPOP").addClass('scaleShow');
        $(".prompPOP").removeClass('scaleOut');
    }

    //购买竞品分析次数
    function buyProductAnalysis() {
        //var packageNum = $('#packageNum').val();
        //if (packageNum > 0) {
        var productAnalysisForm = $('form[name="productAnalysisForm"]')[0];
        if (productAnalysisForm)
            productAnalysisForm.submit();
        //}
    }
</script>
