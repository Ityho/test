var messagePopList = new Array();//消息弹窗队列
var nextPopMessFlag = true;
/**************************************************    消息列表  start   ******************************************************/
//任务状态信息
function taskListStatus(){
    //消息总列表
    $.ajax({
        url : njxBasePath + '/sys/taskMessage/getTaskMessageList.action',
        type : 'POST',
        success : function(result) {
            if(result == null)
                return false;
            if(result.messages!=null&&result.messages.length>0){
                //待查看单条消息展示
                var messagecheckList = "";
                var messageChecked ="";
                var i=0;
                //待查看消息列表
                $.each(result.messages,function(i,n){
                    if(n.status == 1){
                        i++;
                        if(n.messageType == 1){
                            //全网
                            messagecheckList += "<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText shengyin-ico'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的全网事件分析{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>"+
                                "<div class='n-btn'>"+
                                "<a href='#' id='checkDetail' type_m="+n.messageType+" url="+n.messageUrl+" messageId="+n.messageId+" onclick='waitMessageList(this)'>查看</a> <a href='#' messageId="+n.messageId+"  onclick='waitMessageList1(this)'>忽略</a>"+
                                "</div>";
                        }else if(n.messageType ==2){
                            //微博事件
                            messagecheckList += "<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText shengyin-ico'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的微博事件分析{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>"+
                                "<div class='n-btn'>"+
                                "<a href='#' id='checkDetail' type_m="+n.messageType+" url="+n.messageUrl+" messageId="+n.messageId+" onclick='waitMessageList(this)'>查看</a> <a href='#' messageId="+n.messageId+"  onclick='waitMessageList1(this)'>忽略</a>"+
                                "</div>";
                        }else if(n.messageType ==3){
                            //微博传播
                            messagecheckList += "<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText shengyin-ico'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的微博传播分析{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>"+
                                "<div class='n-btn'>"+
                                "<a href='#' id='checkDetail' type_m="+n.messageType+" url="+n.messageUrl+" messageId="+n.messageId+" onclick='waitMessageList(this)'>查看</a> <a href='#' messageId="+n.messageId+"  onclick='waitMessageList1(this)'>忽略</a>"+
                                "</div>";
                        }else if(n.messageType ==4){
                            //简报
                            messagecheckList += "<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText shengyin-ico'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的简报制作{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>"+
                                "<div class='n-btn'>"+
                                "<a href='#' id='checkDetail' type_m="+n.messageType+" url="+n.messageUrl+" messageId="+n.messageId+" onclick='waitMessageList(this)'>查看</a> <a href='#' messageId="+n.messageId+"  onclick='waitMessageList1(this)'>忽略</a>"+
                                "</div>";
                        }else if(n.messageType ==5){
                            // 情绪
                            messagecheckList += "<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText shengyin-ico'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的情绪关键词{"+n.messageDesc+"}已经通过审核啦~</p>"+
                                "</div>"+
                                "<div class='n-btn'>"+
                                "<a href='#' id='checkDetail' type_m="+n.messageType+" url="+n.messageUrl+" messageId="+n.messageId+" onclick='waitMessageList(this)'>查看</a> <a href='#' messageId="+n.messageId+"  onclick='waitMessageList1(this)'>忽略</a>"+
                                "</div>";
                        }else if(n.messageType ==6){
                            // 情绪
                            messagecheckList += "<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText shengyin-ico'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的评论分析{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>"+
                                "<div class='n-btn'>"+
                                "<a href='#' id='checkDetail' type_m="+n.messageType+" url="+n.messageUrl+" messageId="+n.messageId+" onclick='waitMessageList(this)'>查看</a> <a href='#' messageId="+n.messageId+"  onclick='waitMessageList1(this)'>忽略</a>"+
                                "</div>";
                        }
                    }else{
                        if(n.messageType ==1){
                            //全网
                            messageChecked +="<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的全网事件分析{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>";
                        }else if(n.messageType ==2){
                            //微博事件
                            messageChecked +="<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的微博事件分析{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>";
                        }else if(n.messageType ==3){
                            //微博传播
                            messageChecked +="<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的微博传播分析{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>";
                        }else if(n.messageType ==4){
                            //简报
                            messageChecked +="<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的简报制作{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>";
                        }else if(n.messageType ==5){
                            //情绪
                            messagecheckList += "<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText shengyin-ico'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的情绪关键词{"+n.messageDesc+"}已经通过审核啦~</p>"+
                                "</div>"+
                                "<div class='n-btn'>"+
                                "<a href='#' id='checkDetail' type_m="+n.messageType+" url="+n.messageUrl+" messageId="+n.messageId+" onclick='waitMessageList(this)'>查看</a> <a href='#' messageId="+n.messageId+"  onclick='waitMessageList1(this)'>忽略</a>"+
                                "</div>";
                        }else if(n.messageType ==6){
                            //情绪
                            messagecheckList += "<div class='nheard'>"+(new Date(n.createTime)).pattern("yyyy-MM-dd hh:mm:ss")+"</div>"+
                                "<div class='nText shengyin-ico'>"+
                                "<p>亲爱哒微友~</p>"+
                                "<p>您的评论分析{"+n.messageDesc+"}任务已经完成啦~</p>"+
                                "</div>"+
                                "<div class='n-btn'>"+
                                "<a href='#' id='checkDetail' type_m="+n.messageType+" url="+n.messageUrl+" messageId="+n.messageId+" onclick='waitMessageList(this)'>查看</a> <a href='#' messageId="+n.messageId+"  onclick='waitMessageList1(this)'>忽略</a>"+
                                "</div>";
                        }
                    }
                });
                if(i>0){
                    messagecheckList += "<div class='nFoot c-line'>"+
                        "<span>以上为待处理消息</span>"+
                        "</div>";
                }
                $("#waitCheckMessage").html(messagecheckList);
                $("#checkedMessage").html(messageChecked);
            }else{
                $(".ma_cont").css("display","none");
                $("#waitCheckMessage").html("");
                $("#checkedMessage").html("");
            }
        }
    });
}
/**************************************************    消息列表  end     ******************************************************/


/**************************************************    弹窗消息列表  start ******************************************************/
/**
 * 定时请求任务队列消息
 */
function getMessagePopList(){
    //消息弹窗列表
    $.ajax({
        url : njxBasePath + '/sys/taskMessage/getTaskMessageList4Pop.action',
        type : 'POST',
        success:function(result){
            if(result!=null){
                if((result.messages!=null&&result.messages.length>0)||(messagePopList!=null&&messagePopList.length>0)){
                    if('undefined' != typeof result.messages)
                        messagePopList = result.messages.concat(messagePopList);//合并消息队列
                    if(messagePopList.length>0)
                        takeMessagePopList();
                }
            }
        }
    });
}

/**
 * 任务队列消息弹框处理
 */
function takeMessagePopList(){
    messagePopList.reverse();//调整顺序
    for(var i=0;i<messagePopList.length;i++){
        if(nextPopMessFlag)
            popWaitMessage(messagePopList.pop());
    }
    messagePopList.reverse();//顺序调回
}

/**************************************************    弹窗消息列表  end *********************************************************/

/**********************************************    待查看任务信息处理  start *******************************************************/
function popWaitMessage(waitMessage){
    nextPopMessFlag = false;//设置false,等待点击关闭按钮后更改状态
    var html = "<p>亲爱哒微友~</p>";
    var actionParam = "";
    if(waitMessage.messageType ==1){
        //全网
        html += "<p>您的全网事件分析<font class='f_c3'>{"+waitMessage.messageDesc+"}</font>已经完成啦~</p>";
        actionParam +="<a href='#' type_m="+waitMessage.messageType+" url="+waitMessage.messageUrl+" messageId="+waitMessage.messageId+" onclick='waitMessageList(this)'>查看</a>"+
            "<a href='#' onclick='closePop(this)'>关闭</a>";
    }else if(waitMessage.messageType ==2){
        //微博事件
        html += "<p>您的微博事件分析<font class='f_c3'>{"+waitMessage.messageDesc+"}</font>已经完成啦~</p>";
        actionParam +="<a href='#' type_m="+waitMessage.messageType+" url="+waitMessage.messageUrl+" messageId="+waitMessage.messageId+" onclick='waitMessageList(this)'>查看</a>"+
            "<a href='#' onclick='closePop(this)'>关闭</a>";
    }else if(waitMessage.messageType ==3){
        //微博传播
        //html += "<p>您的微博传播分析<font class='f_c3'>{"+waitMessage.taskName+"}</font>已经完成啦~</p>"+
        html += "<p>您的微博传播分析已经完成啦~</p>";
        actionParam +="<a href='#' type_m="+waitMessage.messageType+" url="+waitMessage.messageUrl+" messageId="+waitMessage.messageId+" onclick='waitMessageList(this)'>查看</a>"+
            "<a href='#' onclick='closePop(this)'>关闭</a>";
    }else if(waitMessage.messageType ==4){
        //简报
        html += "<p>您的简报制作<font class='f_c3'>{"+waitMessage.messageDesc+"}</font>已经完成啦~</p>";
        actionParam +="<a href='#' type_m="+waitMessage.messageType+" url="+waitMessage.messageUrl+" messageId="+waitMessage.messageId+" onclick='waitMessageList(this)'>查看</a>"+
            "<a href='#' onclick='closePop(this)'>关闭</a>";
    }else if(waitMessage.messageType ==5){
        //简报
        html += "<p>您的情绪关键词<font class='f_c3'>{"+waitMessage.messageDesc+"}</font>已经审核通过啦~</p>";
        actionParam +="<a href='#' type_m="+waitMessage.messageType+" url="+waitMessage.messageUrl+" messageId="+waitMessage.messageId+" onclick='waitMessageList(this)'>查看</a>"+
            "<a href='#' onclick='closePop(this)'>关闭</a>";
    }else if(waitMessage.messageType ==6){
        //简报
        html += "<p>您的评论分析<font class='f_c3'>{"+waitMessage.messageDesc+"}</font>已经完成啦~</p>";
        actionParam +="<a href='#' type_m="+waitMessage.messageType+" url="+waitMessage.messageUrl+" messageId="+waitMessage.messageId+" onclick='waitMessageList(this)'>查看</a>"+
            "<a href='#' onclick='closePop(this)'>关闭</a>";
    }
    $("#sinWaitMessage").html(html);
    $("#action_param").html(actionParam);
    $(".ma_cont").css("display","block");
}
/**********************************************   待查看任务信息处理  end *********************************************************/

/**********************************************    稍后/忽略事件 start **********************************************************/

function waitMessageList(obj){
    nextPopMessFlag = true;
    var isChange = false;
    var type = obj.getAttribute("type_m");
    $.ajax({
        url : njxBasePath + '/sys/taskMessage/setMessageStatus.action',
        type : 'POST',
        data : {
            'messageId' : obj.getAttribute("messageId")
        },
        success:function(result){
            closePop();
            taskListStatus();
        }
    });
    if (type == 1 || type == 2)
        previewEvent2(obj);
    else
        previewEvent(obj);
}

function waitMessageList1(obj){
    nextPopMessFlag = true;
    $.ajax({
        url : njxBasePath + '/sys/taskMessage/setMessageStatus.action',
        type : 'POST',
        data : {
            'messageId' : obj.getAttribute("messageId")
        },
        success:function(result){
            taskListStatus();
        }
    });
}
/**********************************************    稍后/忽略事件 end ************************************************************/

/*******************************************          查看转到详情页     start  **************************************************/
function previewEvent(obj){
    window.open(njxBasePath + obj.getAttribute("url"),"_self");
}

function previewEvent2(obj){
    $("#messageForm").attr("action", njxBasePath + obj.getAttribute("url"),"_self");
    $("#messageForm").submit();
}
/*******************************************          查看转到详情页     end  ****************************************************/

/*********************************************   关闭消息弹窗   start   *********************************************************/
function closePop(obj){
    nextPopMessFlag = true;//点击关闭后,更改true,List不为空则显示下一条message
    $(".ma_cont").css("display","none");
    $("#sinWaitMessage").html("");
    $("#action_param").html("");
    if(messagePopList.length>0){
        takeMessagePopList();
    }
}
/*********************************************   关闭消息弹窗   end   ***********************************************************/

/*********************************************   时间处理函数  start ************************************************************/
/** * 对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
 可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
 Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.pattern=function(fmt) {
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0" : "/u65e5",
        "1" : "/u4e00",
        "2" : "/u4e8c",
        "3" : "/u4e09",
        "4" : "/u56db",
        "5" : "/u4e94",
        "6" : "/u516d"
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    if(/(E+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}
/*********************************************   时间处理函数  end **************************************************************/