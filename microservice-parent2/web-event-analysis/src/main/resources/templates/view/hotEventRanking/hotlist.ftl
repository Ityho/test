<#include "../../frame_top.ftl" >

<#--<script type='text/javascript' src='${staticResourcePathH5}/dwr/engine.js'></script>-->
<#--<script type='text/javascript' src='${staticResourcePathH5}/dwr/util.js'></script>-->
<#--<script type='text/javascript' src='${staticResourcePathH5}/dwr/interface/StatDwr.js'></script>-->
<script type="text/javascript" src="${staticResourcePathH5}/js/index.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/categoryOrder.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript">saveOperateLog('热门事件','1018');</script>
<style>

</style>
<input type="hidden" name="maxpage" id="maxpage" value="${maxpage}"/>
<input type="hidden" name="page" id="page" value="${page}"/>
<input type="hidden" name="keywordId" id="keywordId" />
<input type="hidden" name="userId" id="userId" value="${admin.userId }"/>
<section class="section2">

    <#list hotList! as item>

        <#if item_index<3 >
            <article class="context">
                <input type="hidden" name="event" value='${item.event}'/>
                <div class="icon-dy abs" id="dyadd" style="top: 10px; right: 0px;">
                    <i class="icon-plus"></i>
                </div>
                <a class="check" href="javascript:void(0);" onclick="javascript:goSearch('${item.event}');">
                <ul>
                    <li class="g_3">

                        <#if item.hpList != null >
                            <#list item.hpList! as hp>
                                <#if hp_size>1 && hp_index==1 >
                                    <#if hp.url!=null && hp.url!=''>
                                        <img src='hp.url' class="img">
                                    </#if>
                                <#else>
                                    <#if hp_index==0 && hp.url!=null && hp.url!=''>
                                        <img src='hp.url' class="img">
                                    </#if>
                                </#if>
                            </#list>
                        </#if>
                    </li>
                    <li class="g_7">
                        <div class="con">
                            <p class="mb30">
			               	  	<span class="tit"> ${item.event}
                                    <#if page ==1 && item_index+1<4 >
                                        <i class="tit_mark">新</i>
                                    </#if>
			               	  	</span>

                            </p>
                            <p class="g_text">
                                <span>文章数：${item.number} 篇</span>
                            </p>
                        </div>
                    </li>
                </ul>
                </a>
            </article>
        </#if>

        <#if item_index==3 >
            <!--3张图新闻 start -->
            <article class="context">
                <input type="hidden" name="event" value='${item.event}'/>
                <div class="icon-dy abs" id="dyadd" style="top: 10px; right: 0px;">
                    <i class="icon-plus"></i>
                </div>
                <a class="check" href="javascript:void(0);" onclick="javascript:goSearch('${item.event}');">
                <ul>
                    <li class="g_12">
                        <div class="con con_1">
                            <p>
                                <span class="tit">${item.event}</span>
                            </p>

                        </div>
                    </li>
                    <li class="g_12">
                        <div class="tree_pic">

                        <#if item.hpList!=nul >
                            <#list item.hpList! as hp>
                                <#if item.hpList_size>3 >
                                    <#if hp_index>0  && hp_index<=3>
                                        <#if hp.url!=null && hp.url!=''>
                                            <img src='hp.url' class="img">
                                        </#if>
                                    </#if>
                                <#else>
                                     <img src='hp.url' class="img">
                                </#if>
                            </#list>
                        </#if>
                        </div>
                    </li>
                    <li class="g_12">
                        <div class="con con_1">
                            <p class="g_text">
                                <span>文章数：${item.number} 篇</span>
                            </p>
                        </div>
                    </li>
                </ul>
                </a>
            </article>
            <!--3张图新闻 end -->
        </#if>

        <#if item_index==4 >
            <!--单张图新闻 start -->
            <article class="context">
                <input type="hidden" name="event" value='${item.event}'/>
                <div class="icon-dy abs" id="dyadd" style="top: 10px; right: 0px;">
                    <i class="icon-plus"></i>
                </div>
                <a class="check" href="javascript:void(0);" onclick="javascript:goSearch('${item.event}');">
                <ul>
                    <li class="g_12">
                        <div class="con con_1">
                            <p>
                                <span class="tit">${item.event}</span>
                            </p>

                        </div>
                    </li>
                    <li class="g_12">
                     <#if item.hpList!=null >
                        <#if item.hpList_size>0>
                             <#list item.hpList! as hp>
                                    <div class="one_pic" style="background-image:url('${hp.url}');">
                                </#list>
                        </#if>
                     </#if>
                    </li>
                    <li class="g_12">
                        <div class="con con_1">
                            <p class="g_text">
                                <span>文章数：${item.number}篇</span>
                            </p>
                        </div>
                    </li>
                </ul>
                </a>
            </article>
            <!--单张图新闻 end -->
        </#if>


    </#list>


    <!--微博实时热点 start -->
    <article class="context context_weibo">
        <div class="g_12">
            <div class="heard"><h1>微博实时热点</h1></div>
        </div>
        <div class="con con_2">
            <div class="g_12">
                <ul>
                    <#list hotList! as item>
                        <#if item_index>9 >
                            <li class="rel">
                                <span><a href="javascript:void(0)" class="tit float_l" onclick="javascript:goSearch('${item.event}');"> ${item.event} </a></span>
                                <span class="float_l g_text">文章数 ${item.number}</span>
                                <input type="hidden" name="event" value='${item.event}'/>
                                <div class="icon-dy abs" id="dyadd" style="top: 10px; right: 0px;">
                                    <i class="icon-plus"></i>
                                </div>
                            </li>
                        </#if>
                    </#list>
                </ul>
            </div>
        </div>
    </article>
    <!--微博实时热点 end -->
    <#list hotList! as item>
        <#if item_index<7 && item_index>4>
            <!--3张图新闻 start -->
            <article class="context">
                <input type="hidden" name="event" value=${item.event}/>
                <div class="icon-dy abs" id="dyadd" style="top: 10px; right: 0px;">
                    <i class="icon-plus"></i>
                </div>
                <a class="check" href="javascript:void(0);" onclick="javascript:goSearch('${item.event}');">
                <ul>
                    <li class="g_12">
                        <div class="con con_1">
                            <p>
                                <span class="tit">${item.event}</span>
                            </p>

                        </div>
                    </li>
                    <li class="g_12">
                        <div class="tree_pic">
                            <#if item.hpList!=nul && item.hpList_size>2 >
                                 <#list item.hpList! as hp>
                                     <#if hp_index>=0 && hp_index <= 2 >
                                         <#if hp.url!=null && hp.url!=''>
                                             <img src='hp.url' class="img">
                                         </#if>
                                     </#if>
                                 </#list>
                            </#if>
                        </div>
                    </li>
                    <li class="g_12">
                        <div class="con con_1">
                            <p class="g_text">
                                <span>文章数：${item.number} 篇</span>
                            </p>
                        </div>
                    </li>
                </ul>
                </a>
            </article>
            <!--3张图新闻 end -->
        </#if>

        <#if item_index>6 && item_index<10 >
            <article class="context">
                <input type="hidden" name="event" value=${item.event}/>
                <div class="icon-dy abs" id="dyadd" style="top: 10px; right: 0px;">
                    <i class="icon-plus"></i>
                </div>
                <a class="check" href="javascript:void(0);" onclick="javascript:goSearch('${item.event}');">
                <ul>
                    <li class="g_3">
                        <#if item.hpList!=nul && item.hpList_size>2 >
                                 <#list item.hpList! as hp>
                                     <#if hp.url!=null && hp.url!=''>
                                         <img src='hp.url' class="img">
                                     </#if>
                                 </#list>
                        </#if>
                    </li>
                    <li class="g_7">
                        <div class="con">
                            <p class="mb30">
		               	  	<span class="tit">${item.event} </span>
                            </p>
                            <p class="g_text">
                                <span>文章数：${item.number}篇</span>
                            </p>
                        </div>
                    </li>
                </ul>
                </a>
            </article>
        </#if>
    </#list>

</section>
<#include "../hotEventRanking/ordertips.ftl" >

<!--替换/购买监测提示 -->
<div id="replacePOP" class="preplaceframe" style="display: none;">
    <input type="hidden" id="replaceKeyWordId" name="replaceKeyWordId" />
    <a href="javascript:void(0)" onclick="cancelOrder()" class="cancel">×</a>
    <div class="prConBox">
        <section class="col">很遗憾！ 您的免费监测方案已经用完，您可以选择替换已有的检测方案或购买新的监测方案。</section>

        <section class="col2">
            <ul class="dyList">
                <#list kwList! as item>
                    <li id="item.keywordId"
                        <#if item_index==0>
                         class="li_click"
                         </#if>><span>item.keywordName</span><i></i></li>
                </#list>
            </ul>
        </section>

        <div class="bottom">
            <div onclick="replaceOrdering()" class="button">确定</div>

            <div class="dinggouLink">
                如果您不愿意替换，<br>那么就再购买一个监测方案吧~ <a class="buybtn"
                                               onclick="javascript:doOrder();">立即订购</a>
            </div>
        </div>
    </div>
</div>
<script>
    //改变时间显示
    dayBetween();


    function DYCallBack(result) {
        if(result!=null&&result.length>0){
            for(var i=0;i<result.length;i++){
                var title = result[i]['title'];
                var keywordId = result[i]['type'];
                $("div[class='icon-dy abs']").each(function(){
                    var event = $(this).prev('input').val();
                    if(event!=''&&event==title){
                        $(this).html(cancelOrderMark);
                        $("#keywordId").val(keywordId);
                        $(this).attr('onclick','cancelKeyword('+keywordId+')');
                    }
                });
            }
        }

    }

    //动态变化监测和取消监测的显示
    var cancelOrderMark = '<i class="icon-minus"></i>';
    hadDY(0,'${admin.userId}');


    //监测或者取消监测的点击事件
    $("div[class='icon-dy abs']").each(function(){
        $(this).one('click',function(){
            if(!"${admin}"){
                window.location.href="indexLocal.action";
                return;
            }
            var title = $(this).prev('input').val();
            if($(this).html()==cancelOrderMark){
                cancelKeyword(keywordId);
            }else{
                existKeyWord(title,0);
            }
        });
    });
</script>
<#include "../../buttom.ftl" >