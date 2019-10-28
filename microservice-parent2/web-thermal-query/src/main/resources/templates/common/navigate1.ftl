<%--<%@page import="java.util.Date"%>--%>
<%--<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>--%>
<%--<%@ taglib prefix="s" uri="/struts-tags"%>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>--%>
<%--<%@page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>--%>
<%@ include file="/resourcePath.jsp"%>
<link rel="stylesheet" href="<%=staticResourcePath%>/css/accordion.css?v=<%=SystemConfig.SYSTEMINITTIME %>"/>
<link rel="stylesheet" href="<%=staticResourcePath%>/css/animate.min.css?v=<%=SystemConfig.SYSTEMINITTIME %>">
<link rel="stylesheet" href="<%=staticResourcePath%>/css/anina.css?v=<%=SystemConfig.SYSTEMINITTIME %>">
<link rel="stylesheet" href="<%=staticResourcePath%>/css/indexV4/base/css/less/w-site.css">
<script src='<%=staticResourcePath%>/js/prettify.js?v=<%=SystemConfig.SYSTEMINITTIME %>'></script>
<script src='<%=staticResourcePath %>/js/jquery-ui-1.9.1.custom.js?v=<%=SystemConfig.SYSTEMINITTIME %>'></script>
<script src='<%=staticResourcePath%>/js/jquery.slimscroll.js?v=<%=SystemConfig.SYSTEMINITTIME %>'></script>
<script src='<%=staticResourcePath %>/js/jquery.tabs.js?v=<%=SystemConfig.SYSTEMINITTIME %>'></script>
<script src="<%=staticResourcePath%>/js/bootstrap.min.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
<script src='<%=staticResourcePath %>/js/taskMessage.js?v=<%=SystemConfig.SYSTEMINITTIME %>' charset="GBK"></script>
<script src='<%=njxBasePath %>/js/city.js?v=<%=SystemConfig.SYSTEMINITTIME %>' charset="UTF-8"></script>
<link rel="stylesheet" href="<%=njxBasePath%>/css/css/bootstrap-select.css?v=<%=SystemConfig.SYSTEMINITTIME %>"/>
<%
    ((OgnlValueStack) request.getAttribute("struts.valueStack")).set("currentPage", request.getParameter("currentPage"));
    ((OgnlValueStack) request.getAttribute("struts.valueStack")).set("noNeedForm", request.getParameter("noNeedForm"));
%>
<style type="text/css">

.btn-orange {
    color: #fff !important;
    background-color: #f5773b !important;
    border-color: #f5773b !important;
}
</style>

<!--活动-->
<div class="activityDis">
	<a class="activity-img" data-target="#example2" data-toggle="modal">
		<img src="<%=staticResourcePath%>/css/indexV4/base/images/activity_dis_img.png" alt="">
	</a>
	<span class="activity-close"><i class="icon icon-guanbi"></i></span>
</div>
<!--活动弹框-->
<div class="modal-default modal-circle fade Discount" id="example2" tabindex="-1" role="dialog" aria-labelledby="example1">
	<div class="modal-dialog" role="document" style="width: 723px;height: 464px">
		<div class="modal-content">
			<a href="javascript:;" class="default-close" data-dismiss="modal" aria-label="Close">×</a>
			<div class="modal-body">
				<div>
					<img class="activity_dis_title" src="<%=staticResourcePath%>/css/indexV4/base/images/activity_dis_title.png" alt="">
					<div id="countdownTime" class="activity-time iconFamily">

					</div>
				</div>

				<div style="padding-top: 100px">
					<div class="text-center">
						<ul class="w-tabs-2 clearfix inline-block">
							<li class="active">
								<a href="#dispage1" data-toggle="tab">基础优惠卷</a>
							</li>
							<li>
								<a href="#dispage2" data-toggle="tab">热度指数折扣券</a>
							</li>
							<!--<li>
								<a href="#dispage3" data-toggle="tab">新闻稿折扣券</a>
							</li>-->
							<li>
								<a href="#dispage4" data-toggle="tab">信息监测折扣券</a>
							</li>
						</ul>
					</div>
					<div class="tab-content padding-vertical-15 padding-horizontal-40">
						<div id="dispage1" class="tab-pane fade active in">
							<div class="padding-vertical-50">
								<div class="coupon block">
									<p class="coupon-title">微积分抵用券</p>
									<p>无最低消费限制</p>
									<p>领取后7日内有效</p>
									<div class="coupon-price">
										<span class="coupon-num">500</span>
										<a href="javascript:;" id="500" ind="1" class="coupon-btn" con_id="70">立即领取</a>
									</div>
								</div>
							</div>
							<div class="text-center">
								<a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">暂不领取</a>
							</div>
						</div>
						<div id="dispage2" class="tab-pane fade">
							<div class="row clearfix">
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">微积分抵用券</p>
										<p>满900微积分可用</p>
										<p>领取后30日内有效</p>
										<div class="coupon-price">
											<span class="coupon-num">300</span>
											<a href="javascript:void(0);" id="300" ind="1" class="coupon-btn"  con_id="71">立即领取</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">微积分抵用券</p>
										<p>满5000微积分可用</p>
										<p>领取后30日内有效</p>
										<div class="coupon-price">
											<span class="coupon-num">2000</span>
											<a href="javascript:void(0);" id="2000" ind="1" class="coupon-btn"  con_id="72">立即领取</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">微积分抵用券</p>
										<p>满14000微积分可用</p>
										<p>领取后30日内有效</p>
										<div class="coupon-price">
											<span class="coupon-num">6000</span>
											<a href="javascript:void(0);" id="6000" ind="1" class="coupon-btn"  con_id="73">立即领取</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">微积分抵用券</p>
										<p>满25000微积分可用</p>
										<p>领取后30日内有效</p>
										<div class="coupon-price">
											<span class="coupon-num">9000</span>
											<a href="javascript:void(0);" id="9000" ind="1" class="coupon-btn"  con_id="74">立即领取</a>
										</div>
									</div>
								</div>
							</div>
							<div class="text-center">
								<a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">暂不领取</a>
							</div>
						</div>
						<!--<div id="dispage3" class="tab-pane fade">
							<div class="row clearfix">
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">免费体验券</p>
										<p>三次免费分析机会</p>
										<p>领取后3日内有效</p>
										<div class="coupon-price">
											<span class="coupon-num">3次</span>
											<a href="javascript:void(0);" class="coupon-btn-red">去使用</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">打包券</p>
										<p>2000微积分</p>
										<p>领取后30日内有效</p>
										<div class="coupon-price">
											<span class="coupon-num">1次</span>
											<a href="javascript:void(0);" class="coupon-btn">立即领取</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">打包券</p>
										<p>10000微积分</p>
										<p>领取后永久有效</p>
										<div class="coupon-price">
											<span class="coupon-num">3次</span>
											<a href="javascript:void(0);" class="coupon-btn">立即领取</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">打包券</p>
										<p>10000微积分</p>
										<p>领取后永久有效</p>
										<div class="coupon-price">
											<span class="coupon-num">5次</span>
											<a href="javascript:void(0);" class="coupon-btn">立即领取</a>
										</div>
									</div>
								</div>
							</div>
							<div class="text-center">
								<a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">暂不领取</a>
							</div>
						</div>-->
						<div id="dispage4" class="tab-pane fade">
							<div class="padding-vertical-50">
								<div class="coupon block">
									<p class="coupon-title">微积分抵用券</p>
									<p>领取后30日内有效</p>
									<div class="coupon-price">
										<span class="coupon-num">10000</span>
										<a href="javascript:void(0);" id="10000" ind="1" class="coupon-btn"  con_id="75">立即领取</a>
									</div>
								</div>
							</div>
							<div class="text-center">
								<a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">暂不领取</a>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    var baseAction = '<%=njxBasePath %>';

	$(function () {
		$('.rank-menu .rank-menuCustom').on('mouseenter', function () {
            //鼠标移入
            $(".rank_hover").stop().slideDown(400);
        });
        $('.rank-menu').on('mouseleave', function () {
            //鼠标移入
            $(".rank_hover").stop().slideUp(400);

        });
        $('.banner-tabs li').on('click', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $('.w-tabs-content').find('.w-tabs-tabpane').removeClass('active').eq(index).addClass('active');
        });
        $('.w-headbar-list li.j_hover').on('mouseenter', function () {
            // $(this).addClass('active').siblings().removeClass('active');
            $(this).find('.w-headbar-dropdown').stop().show();
        });
        $('.w-headbar-nav .j_hover').on('mouseleave', function () {
            $(this).find('.w-headbar-dropdown').stop().hide();
            // $(this).removeClass('active');
        });


        var sid = getQueryString("sid");
      	if(sid != null){
      		$.ajax({
                url : baseAction+"/view/user/outIntoLogin.action",
                type : "post",
                data : {'sid':sid},
                success : function(data){
                	var outIntoType = getQueryString("outIntoType");
                  	if(outIntoType != null){
                    	if(data == 1){
                  			loginIntoTo(outIntoType);
                    	}else{
                  			intoTo(outIntoType);
                    	}
             		}
                }
            })
      	}else{
          	var outIntoType = getQueryString("outIntoType");
          	if(outIntoType != null){
          		<s:if test="#attr.admin!=null">
          			loginIntoTo(outIntoType);
          		</s:if>
          		<s:else>
          			intoTo(outIntoType);
          		</s:else>
     		}
      	}
      	
      	Date.prototype.format = function(format){
      	 var o = {
	      	 "M+" : this.getMonth()+1, //month
	      	 "d+" : this.getDate(),    //day
	      	 "h+" : this.getHours(),   //hour
	      	 "m+" : this.getMinutes(), //minute
	      	 "s+" : this.getSeconds(), //second
	      	 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
	      	 "S" : this.getMilliseconds() //millisecond
      	 };
      	 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
      	 (this.getFullYear()+"").substr(4 - RegExp.$1.length));
      	 for(var k in o)if(new RegExp("("+ k +")").test(format))
      	 format = format.replace(RegExp.$1,
      	 RegExp.$1.length==1 ? o[k] :
      	 ("00"+ o[k]).substr((""+ o[k]).length));
      	 return format;
      	}
    });
	//获取url中的参数
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); // 匹配目标参数
		var result = window.location.search.substr(1).match(reg); // 对querystring匹配目标参数
		if (result != null) {
			return decodeURIComponent(result[2]);
		} else {
			return null;
		}
	}
	
	//登录跳转
    function loginIntoTo(type){
		if(type == 0){
			window.location = baseAction + "/home.shtml";
		}else{
	        var data = {"notLoginOperateRecord.operateType":type};
	        $.ajax({
	            url : baseAction+"/view/hotSearch/recordOperateInfo.action",
	            type : "post",
	            data : data,
	            success : function(){
	            	window.location = baseAction + "/home.shtml";
	                //$("#promptModal").modal();
	                //$("#login").click();
	            }
	        })
		}
    }

	//登录自动跳转产品页
    function intoTo(type){
        var data = {"notLoginOperateRecord.operateType":type};
        $.ajax({
            url : baseAction+"/view/hotSearch/recordOperateInfo.action",
            type : "post",
            data : data,
            success : function(){
                $("#loginModal").modal();
            }
        })
    }
    (function($){
        var _ajax=$.ajax;
        $.ajax=function(opt){
            var fn = {
            		beforeSend:function(XMLHttpRequest){},
                    error:function(XMLHttpRequest, textStatus, errorThrown){},
                    success:function(data, textStatus){},
                    complete:function(data, textStatus){}
                };
            if(opt.error){
                fn.error=opt.error;
            }
            if(opt.success){
                fn.success=opt.success;
            }
            if(opt.complete){
            	fn.complete=opt.complete;
            }
            if(opt.beforeSend){
            	fn.beforeSend=opt.beforeSend;
            }
            var _opt = $.extend(opt,{
            	beforeSend:function(XMLHttpRequest){
            		XMLHttpRequest.setRequestHeader("browser-w", window.innerWidth||document.documentElement.clientWidth);
            		XMLHttpRequest.setRequestHeader("browser-h", window.innerHeight||document.documentElement.clientHeight);
                    fn.beforeSend(XMLHttpRequest);
                },
    			complete:function(data, textStatus){
                    fn.complete(data, textStatus);
                },
                success:function(data, textStatus){
                    fn.success(data, textStatus);
                },
    			error:function(XMLHttpRequest, textStatus, errorThrown){
                    fn.error(XMLHttpRequest, textStatus, errorThrown);
                },
            });
            return _ajax(opt);
        };
        
    })(jQuery);
    


  //验证码以毫秒+四位随机数作为唯一凭证
  function getRequestRan() {
  	// 若框架页内session失效，则整个页面重新加载
  	if(window != top){  
  		top.location.reload(true);
  		return;
  	}
  	
  	var _t = new Date().getMilliseconds();
  	var _ran = _t + '' + Math.round(Math.random()*10) + '' + Math.round(Math.random()*10) + '' + Math.round(Math.random()*10) + '' + Math.round(Math.random()*10);
  	var _u = baseAction + "/view/common/validation.jsp?c=lg_" + _ran;
  	$('#imgVcode_request').attr('src', _u);
  	$('#_ran_request').val(_ran);
  }
</script>
<s:if test="#attr.admin!=null">

    <script>
        var jquery2 = $;
        var njxBasePath = '<%=njxBasePath %>';
        var baseAction = '<%=njxBasePath %>';
        function logout(){
            window.location.href="<%=njxBasePath%>/logout.action";
        }
        $(function() {
            $('.Oper1a').click(function(){
                $('.fold1').stop().hide();
                $('.open1').stop().show();   //显示
            });
            $('.Oper1b').click(function(){
                $('.fold1').stop().show();
                $('.open1').stop().hide();   //隐藏
            });
        });

        $(function(){
            $("#noticeLink").on("click",function(e){
                  $("#notice").addClass('rightIn'); 
                  $("#notice").removeClass('rightOut'); 
                    e.stopPropagation();
            });
        		$("#notice").on("click",function(e){
        			e.stopPropagation();
        		});
        		/* setTimeout(function(){
            		$("body").on("click",function(event){
            			var theEvent = window.event || event;
            		    var theObj=theEvent.target || theEvent.srcElement;
            		    var obj = $(theObj).parents("#newZhifuModal");
                        if(obj.length == 0 && $("#notice").hasClass("rightIn")){
                            $("#notice").removeClass('rightIn');
                        }
            		});
        		}, 5000);	//延迟3秒 防止页面加载完成后的点击事件影响 */
                  
           
            $("#closeNotice").on("click",function(){
                  $("#notice").addClass('rightOut'); 
                  $("#notice").removeClass('rightIn'); 
            });
         });
        var myScroll;
		function loaded() {
			myScroll = new IScroll('#wrapperPrize', {
				scrollX: false,
				scrollY: true,
			    vScrollbar : false,
				vScroll:true,
				mouseWheel: true,
			});
		}
		function Refresh() {
		    setTimeout(function () {
				loaded();
		    }, 500);
		}
		function clickHrefBlank(url){
			window.open(njxBasePath+url,"_blank");
		}
		
        //用户第一次登录自动弹出公告
        $(function(){
            var firstLoginSign = $("#firstLoginSign").val();
            if(firstLoginSign == 1 || firstLoginSign == 3 || firstLoginSign == 4){
            	$('#robot').hide();
            	$('.spirit-mask').hide();
            	$('.spiritBox').show();
                $("#showNoticeModalBtn").click();
                
    					Refresh();
    				document.ondragover = function (e) { e.preventDefault(); };
    				document.ondrop = function (e) { e.preventDefault(); };
    				$(document).on("dragstart", function (e) {
    				    return false;
    				});
            }else if(firstLoginSign==6){
            	$('#robot').hide();
            	$('.spirit-mask').hide();
            	$('.spiritBox').show();
            	$("#activeLastClick").click();
            }else if(firstLoginSign==2){
            	$('.spiritBox').hide();
    			setTimeout(function() { 
    				$('.animateSpirit').addClass('animateTime bounceOutRight infinite');
    				$('.spirit-mask').hide();
    				setTimeout(function() { 
    					$('.spiritBox').show();
    				}, 1200);
    			}, 3000);
            }else if(firstLoginSign==0){
            	$('#robot').hide();
            	$('.spirit-mask').hide();
            	$('.spiritBox').show();
            	/* $('.spiritBox').hide(); */
            }
            var urll = window.location.href;
            if(urll.indexOf("firstLoginSign")>=0){
            	$('#robot').hide();
            	$('.spirit-mask').hide();
            	$('.spiritBox').show();
            	$.ajax({
            		url : '<%=njxBasePath%>/view/usercenterV2/findGiftCardByUserId.action',
            		type : 'POST',
            		success : function(data) {
            			if(data.code=="0000"){
            				$("#queryCardMony").html(data.data.count);
            				$("#queryCardTime").html(data.data.validEndDateStr);
            				for(var i=1;i<5;i++){
            					$("#queryCardNo"+i).html(data.data.cardNum.substring((i-1)*4,i*4));
            				}
            				$("#cardModelClick").click();
                    	}else{

                    	}
            		}
            	});
            	$("#invesImgId").attr("style","");
            }
        });


        // 购买
        function goConfirmOrder(id, price) {
            $("#buyKeywordId").val(id);
            $("#buyKeywordPackagePrice").val(price);

            document.buyForm.submit();
        }

        $(function() {
            jquery2('#noticeCon ul').slimScroll({
                height: '100%'
            });
        });

        function getSelectedText(){
            if(window.getSelection){  //FF
                return window.getSelection().toString();
            }else{ //IE
                return document.selection.createRange().text;
            }
        }
        <!--选中文字显示按钮效果 end-->


        $(function(){
            $("#noticeLink").on("click",function(){
                $("#notice").addClass('rightIn');
                $("#notice").removeClass('rightOut');

            });
            $("#closeNotice").on("click",function(){
                $("#notice").addClass('rightOut');
                $("#notice").removeClass('rightIn');
            });
            
        });
        
        function changetab(type){
        	if(type==1){
        		$("#xx").removeClass("current");
            	$("#gg").addClass("current");
            	$("#xx_tab").addClass("hide");
            	$("#gg_tab").removeClass("hide");
        	}else{
        		//每次切换展示的时候再去请求列表
        		taskListStatus();
        		$("#gg").removeClass("current");
            	$("#xx").addClass("current");
            	$("#gg_tab").addClass("hide");
            	$("#xx_tab").removeClass("hide");
        	}
        }

        $(function(){
        	//初始化加载弹窗消息
        	getMessagePopList();
        	//定时加载弹窗消息
        	var timer = setInterval(function(){
        		getMessagePopList();
        		
        		if(messagePopList.length>0){
        			takeMessagePopList();
        		}
        	}, 120000);
        });
        
        $(function(){
        	//信息弹框 显示/隐藏
        	$(".w-heabar-user").on("mouseenter",function(e){
               	$(this).find(".showObject").stop().show();
            });
            $(".w-heabar-user").on("mouseleave",function(){
                $(".showObject").stop().hide();
            });
            $(".showObject").on("click",function(e){
        		e.stopPropagation();
        	});
        	$("body").on("click",function(){
                $(".showObject").hide(); 	 	 
        	});
        //用户剩余权益显示更多
           $(".UserPrompt .dl_list").each(function(i){
        	   $("#dl_list"+(i)+">dd:gt(3)").hide();
        	});
        	$(".bottomMore").on("click",function(){
        		if(!$(this).find("i").hasClass("rotate180")){
        			$(this).parents(".UserPrompt .dl_list").find(">dd:gt(3)").show(300);
        			 $(this).html("更多 <i class='icon-angle-up rotate180'></i>");
        		}else{
        			$(this).parents(".UserPrompt .dl_list").find(">dd:gt(3)").hide(300);
        			$(this).html("更多 <i class='icon-angle-down rotate0'></i>");
        		}
        	});
        });
        
        $(function() {
        	$.ajax({
        		url : actionBase + '/view/user/commentsAnalsysMenuShow.action',
        		type : 'POST',
        		success : function(result) {
        			if (result)
        				$('#commentsAnalysisMenuLi').css('display', '');
        			else
        				$('#commentsAnalysisMenuLi').remove();
        		}
        	});
        	
        	$.ajax({
        		url : actionBase + '/view/usercenter/proType.action',
        		type : 'POST',
        		success : function(result) {
        			if (!$.isEmptyObject(result)) {
        				if (result.code == '0000') {
        					result.productPackage.packageName = result.productPackage.packageName.replace('A', '【A】');
        					result.productPackage.packageName = result.productPackage.packageName.replace('B', '【B】');
        					result.productPackage.packageName = result.productPackage.packageName.replace('C', '【C】');
        					result.productPackage.packageName = result.productPackage.packageName.replace('D', '【D】');
        					var now = new Date();
        					var proValidEndDate = new Date('${admin.proUserValidEndTime}');
        					if (proValidEndDate > now) {
        						$('#proDl dd').html('您的' + result.productPackage.packageName + '距离到期还有<font class="fc_red fz20">' + parseInt((proValidEndDate - now) / 1000 / 60 / 60 / 24) + '</font>天');
        					} else {
        						$('#proDl dd').html('您的' + result.productPackage.packageName + '已过期');
        					}
        					$('#proDl').css('display', '');
        					$('#proDlBtn').css('display', '');
        					$('#proDlBtn button').click(function() {
        						//location.href = actionBase + '/usercenter/account.shtml';
 								var params = {packageId : result.productPackage.productPackageId, packageName : result.productPackage.packageName, packagePrice : result.productPackage.packagePrice, proContinue : true};
 								openBuyCommon(7, params);
        					});
        				} else {
        					$('#dl_list0').css('display', '');
        				}
        			}
        		}
        	});
        });
        

        //屏蔽 浏览器backspace键返回前一页的功能
        function banBackSpace(e){
        	var ev = e || window.event;
        	//各种浏览器下获取事件对象
        	var obj = ev.relatedTarget || ev.srcElement || ev.target ||ev.currentTarget;
        	//按下Backspace键
        	if(ev.keyCode == 8){
        		var tagName = obj.nodeName; //标签名称
        		//如果标签不是input或者textarea则阻止Backspace
        		if(tagName!='INPUT' && tagName!='TEXTAREA'){
        			return stopIt(ev);
        		}
        		var tagType = obj.type.toUpperCase();//标签类型
        		//input标签除了下面几种类型，全部阻止Backspace
        		if(tagName=='INPUT' && (tagType!='TEXT' && tagType!='TEXTAREA' && tagType!='PASSWORD')){
        			return stopIt(ev);
        		}
        		//input或者textarea输入框如果不可编辑则阻止Backspace
        		if((tagName=='INPUT' || tagName=='TEXTAREA') && (obj.readOnly==true || obj.disabled ==true)){
        			return stopIt(ev);
        		}
        	}
        }

        function stopIt(ev){
        	if(ev.preventDefault ){
        		//preventDefault()方法阻止元素发生默认的行为
        		ev.preventDefault();
        	}
        	if(ev.returnValue){
        		//IE浏览器下用window.event.returnValue = false;实现阻止元素发生默认的行为
        		ev.returnValue = false;
        	}
        	return false;
        }
        
        $(function(){
        	//实现对字符码的截获，keypress中屏蔽了这些功能按键
        	document.onkeypress = banBackSpace;
        	//对功能按键的获取
        	document.onkeydown = banBackSpace;
        	
        })
    </script>
    <!-- 新版购买 -->
<!-- 所有登录弹框start -->
<link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/notice.css?v=<%=SystemConfig.SYSTEMINITTIME %>"/>
<a href="javascript:" style="display: none;" id="showNoticeModalBtn" data-toggle="modal" data-target="#noticeModal"></a>
<s:if test="firstLoginSign == 4">
	<!-- 后台赠送兑换券start -->
<script src="<%=njxBasePath%>/js/iscroll.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
<s:if test="activitySendRecordList != null && activitySendRecordList.size() > 0">
		<div id="noticeModal" class="setModal fade redPacket" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 1044px; height: 768px;">
			<a href="javascript:" type="button" class="close icon-guanbi" data-dismiss="modal" aria-hidden="true"></a>
			<div class="modal-con">
				<div class="redPacket-content">
				<div id="wrapperPrize" style="touch-action: none;">
					<ul id="scroller">
						<s:iterator id="item" value="activitySendRecordList" status="i">
							
							<s:if test="#item.coupon.couponType ==1">
								<li>
									<div onclick="clickHrefBlank('/pay/buy.shtml?type=1');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
										<span class="fz32">
										￥<s:number name="#item.coupon.discountAmount" maximumFractionDigits="0" />
										</span>
										<div class="redPacket-des">
											<p class="fz18 fw600">${item.coupon.couponName }</p>
											<p class="fz14 mb5">满${item.coupon.minSpentAmount }可用</p>
										</div>
										</a>
									</div>
								</li>
							</s:if>
							
							<s:if test="#item.coupon.couponType==2">
								<li>
									<div onclick="clickHrefBlank('/keyword.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
										<span class="fz32">
											<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
										</span>
										<div class="redPacket-des">
											<p class="fz18 fw600">${item.coupon.couponName }</p>
											<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
	                             		</div>
										</a>
									</div>
								</li>
							</s:if>
												
							<s:if test='#item.coupon.couponType ==103'>
								<li>
									<div onclick="clickHrefBlank('/keyword.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
										<span class="fz32">
											<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
										</span>
										<div class="redPacket-des">
											<p class="fz18 fw600">${item.coupon.couponName }</p>
											<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
	                             			<p class="fc666 fz12" style="font-size: 10px !important;">仅限监测方案购买使用</p>
	                             		</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==104'  >
	                             <li>
									<div onclick="clickHrefBlank('/view/eventAnalysis/taskList.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
											<span class="fz32">
												<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
											</span>
											<div class="redPacket-des">
												<p class="fz18 fw600">${item.coupon.couponName }</p>
												<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">仅限全网事件分析购买使用</p>
		                             		</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==105'>
	                             <li>
									<div onclick="clickHrefBlank('/view/weiboEventAnalysis/taskList.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
											<span class="fz32">
												<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
											</span>
											<div class="redPacket-des">
												<p class="fz18 fw600">${item.coupon.couponName }</p>
												<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">仅限微博事件分析购买使用</p>
		                             		</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==106'>
	                             <li>
									<div onclick="clickHrefBlank('/productsAnalysis.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
											<span class="fz32">
												<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
											</span>
											<div class="redPacket-des">
												<p class="fz18 fw600">${item.coupon.couponName }</p>
												<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">仅限竞品分析购买使用</p>
		                             		</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==107'>
	                             <li>
									<div onclick="clickHrefBlank('/i/singleWeiboAnalysis/index.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
											<span class="fz32">
											<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
										</span>
										<div class="redPacket-des">
											<p class="fz18 fw600">${item.coupon.couponName }</p>
											<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
		                             		<p class="fc666 fz12" style="font-size: 10px !important;">仅限微博传播效果分析购买使用</p>
		                             	</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==108'>
	                             <li>
									<div onclick="clickHrefBlank('/view/reviewAnalysis/reviewIndex.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
											<span class="fz32">
												<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
											</span>
											<div class="redPacket-des">
												<p class="fz18 fw600">${item.coupon.couponName }</p>
												<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">仅限评论分析购买使用</p>
		                             		</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==109'>
	                             <li>
									<div onclick="clickHrefBlank('/keyword.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
											<span class="fz32">
												<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
											</span>
											<div class="redPacket-des">
												<p class="fz18 fw600">${item.coupon.couponName }</p>
												<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
			                             	<p class="fc666 fz12" style="font-size: 10px !important;">仅限数据导出购买使用</p>
			                             	</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==110'>
	                             <li>
									<div onclick="clickHrefBlank('/home.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
												<span class="fz32">
												<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
											</span>
											<div class="redPacket-des">
												<p class="fz18 fw600">${item.coupon.couponName }</p>
												<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">仅限热度指数购买使用</p>
		                             		</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==111'>
	                             <li>
									<div onclick="clickHrefBlank('/myCoupons.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
											<span class="fz32">
												<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
											</span>
											<div class="redPacket-des">
												<p class="fz18 fw600">${item.coupon.couponName }</p>
												<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">仅限发票包邮券购买使用</p>
		                             		</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType ==112'>
	                             <li>
									<div onclick="clickHrefBlank('/briefReport/briefReport.shtml');" style="cursor: pointer;" class="redPacket-item">
										<a style="text-decoration:none;">
											<span class="fz32">
												<i class="icon fz18">&#xe6ff;</i><font class="fw600"><s:number name="#item.coupon.discountCreditCount" maximumFractionDigits="0" /></font>
											</span>
											<div class="redPacket-des">
												<p class="fz18 fw600">${item.coupon.couponName }</p>
												<p class="fz14 mb5">满${item.coupon.minSpentCreditCount }可用</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">仅限简报购买使用</p>
		                             		</div>
										</a>
									</div>
								</li>
                             </s:if>
                             <s:if test='#item.coupon.couponType !=1 &&#item.coupon.couponType !=2&&#item.coupon.couponType !=103&&#item.coupon.couponType !=104&&#item.coupon.couponType !=105&&#item.coupon.couponType !=106&&#item.coupon.couponType !=107&&#item.coupon.couponType !=108&&#item.coupon.couponType !=109&&#item.coupon.couponType !=110&&#item.coupon.couponType !=111&&#item.coupon.couponType !=112'>
                             	<li>
									<div onclick="clickHrefBlank('/myCoupons.shtml?operateType=2');" style="cursor: pointer;" class="redPacket-item">
										<a  style="text-decoration:none;">
										<span>
											<img src="<%=njxBasePath%>/images/shouye/icon-prize${item.coupon.couponType }.png"/>
										</span>
										<div class="redPacket-des">
											<p class="fz18 fw600">${item.coupon.couponName }</p>
											<p class="fz14 mb5">${item.coupon.description }</p>
											<p class="fc666 fz12">${item.coupon.validDays }天后失效</p>
										</div>
										</a>
									</div>
								</li>
                             </s:if>
						</s:iterator>
					</ul>
				</div>
				</div>
				<div class="redPacket-footer">
					<img src="<%=njxBasePath%>/css/images/model-foot.png" />
				</div>
			</div>
			</s:if>
			<div class="clear"></div>

		</div>
	<!-- 后台赠送兑换券end -->
</s:if>
<s:elseif test="firstLoginSign == 3">
<script src="<%=njxBasePath%>/js/iscroll.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
	<!-- 活动弹框start -->
	<div id="noticeModal" class="actSign fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 768px; left: 0;right: 0;margin: auto;">
		<div class="modal-header" style="border-bottom-width: 0px;">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" >×</button>
		</div>
		<div class="modal-body icheckbox-list" style="overflow-y:initial;">
			<div class="modal-con">
				<img style="width: 596px;" src="<%=staticResourcePath%>/images/icon_bg01.png" />
				<a href="<%=njxBasePath%>/usercenter/account.shtml" target="_Blank" class="btn-receive" style="padding-left: 20px;">马上领福利<i class="jiantou"></i></a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!-- 活动弹框end -->
	
</s:elseif>
<s:elseif test="firstLoginSign == 1">
<script src="<%=njxBasePath%>/js/iscroll.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
	<!-- 公告start -->
	<div id="noticeModal" class="modal modalTwo fade Upgrade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 768px; left: 0;right: 0;margin: auto;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		</div>
		<div class="modal-body icheckbox-list" style="overflow-y:initial;">
			<s:if test="#attr.noticeList != null && #attr.noticeList.size() > 0">
				<div class="modal-title">
					<img class="model-tit-bg" src="<%=staticResourcePath%>/images/model-top.png" />
					<div class="title">
						<h3>${noticeList[0].noticeTitle}</h3>
						<p>来自微热点(微舆情) <s:date name="#attr.noticeList.get(0).noticeSendTime" format="yyyy-MM-dd HH:mm:ss"/></p>
					</div>
				</div>
				<div class="modal-con">
					<div>
						${noticeList[0].noticeContent}
					</div>
					
				</div>
			</s:if>
			<div class="modal-footer" style="text-align: center;">
				<a class="" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">我知道了</a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!-- 公告end -->
</s:elseif>
<s:elseif test="firstLoginSign == 6">
<style type="text/css">
.endModal .icon-guanbi {
    color: #fff;
    font-weight: 300;
    text-shadow: none;
    font-size: 24px;
    position: absolute;
    right: 40px;
    top: 55px;
    z-index: 2;
    text-decoration: none;
}
</style>
<link href="<%=njxBasePath%>/css/hotSearch/recharge.css?v=<%=SystemConfig.SYSTEMINITTIME %>" rel="stylesheet" type="text/css">
	<input type="hidden" id="activeLastClick" class="cl cl_1 btn" style="color: #fff;" data-toggle="modal" data-target="#endModal"/>
	<div id="endModal" class="modal fade base-modal endModal" role="dialog" tabindex="-1" aria-labelledby="endModal" aria-hidden="true" style="display: none; width: 580px; height: 521px;background: transparent;border-top: 0px;box-shadow: none;">
		<a href="javascript:" type="button" class="close icon-guanbi" data-dismiss="modal" aria-hidden="true" ></a>
		<div class="" style="position: relative;">			
			<div class="">
<!-- 				<a href=""> -->
					<img onclick="goEndActive();" src="<%=njxBasePath%>/images/shouye/modal_end.png" style="width: 100%;cursor: pointer;"/>
<!-- 				</a> -->
			</div>

		</div>
		<div class="clear"></div>
	</div>
</s:elseif>
<!-- 礼品卡弹框 -->
<s:elseif test="firstLoginSign == 0 || firstLoginSign == 5">
    <style type="text/css">
        .giftCard.modal {
            border: none !important;
            box-shadow: none;
            background: transparent;
        }
        .giftCard.modal.fade.in {
            border-radius: 35px;
            overflow: hidden !important;
        }

        .giftCard.modalTwo .modal-con p {
            line-height: 1.8;
        }
        .giftCard.modalTwo .modal-body {
            background: url("images/case/giftCard/model_card_bg.png") no-repeat;
            background-size: cover;
            width: 644px;
            height: 499px;
        }
        .giftCard.modalTwo .modal-con{
            padding: 50px 50px 0px;
            position: relative;
            background: transparent;
        }
        .giftCard .modal-footer p{
            color: #FFFFEFE7;
        }
        .card_txt{
            margin-top: 10px;
            margin-left: 40px;

        }
        .card_txt p:last-child{
            margin-left: 50px;
        }
        .card_txt p{
            font-size: 16px;
            color: #333333;
        }
        .card_logo{
            position: absolute;
            top: 48px;
            left: 130px;
            width: 95px;
            height: 31px;
        }
        .card_logo img{
            width: 100%;
            height: 100%;
        }
        .card_number{
            font-size: 22px;
            color: #ffffff;
            position: absolute;
            top: 115px;
            left: 155px;
        }
        .card_number span{
            margin-right: 10px;
        }
        .card_number span:last-child{
            margin-right: 0;
        }
        .card_value{
            position: absolute;
            bottom: 6px;
            left: 130px;
            font-size: 16px;
            color: #ffffff;
        }
        .card_data{
            position: absolute;
            bottom: 6px;
            font-size: 12px;
            color: #fff4e7;
            right: 113px;
        }
        .btn_card_bg{
            width: 144px;
            height: 44px;
            line-height: 44px;
            padding: 0;
            border: 0;
            color: #E65D2B;
            font-size: 16px;
            background: url(images/case/giftCard/model_card_btn.png) no-repeat;
            background-size: cover;
        }
        .giftCard .modal-header .close:hover {
             background-color: transparent;
             color: transparent;
        }
    </style>
<input type="hidden" id="cardModelClick" class="cl cl_1 btn" data-toggle="modal" data-target="#cardModel"/>
<div id="cardModel" class="modal modalTwo fade giftCard" role="dialog" tabindex="-1" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none; width: 644px; margin-left: -322px;">
    <div class="modal-header" style="right: -8px;top: 23px;">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    </div>
    <div class="modal-body icheckbox-list" style="overflow-y:initial;">
        <div class="modal-con">
            <div class="card_txt">
                <p>亲爱的会员:</p>
                <p>为了感谢您对微舆情的支持,特送您一张微积分礼品卡~</p>
            </div>
            <div class="rel" style="height: 240px">
                <div class="card_logo">
                    <img src="<%=staticResourcePath %>/images/logo-w.png" alt="">
                </div>
                <div class="card_number">
                    <span id="queryCardNo1"></span><span id="queryCardNo2"></span><span id="queryCardNo3"></span><span id="queryCardNo4"></span>
                </div>
                <div class="">
                    <p class="card_value" style="color: #ffffff;"><span id="queryCardMony"></span> <span class="fz12">微积分</span></p>
                    <p class="card_data" style="color: #ffffff;">兑换截至<span id="queryCardTime"></span></p>
                </div>
            </div>

        </div>
        <div class="modal-footer pading-top-30 pading-bottom-5" style="text-align: center;">
            <p style="color: #ffffff;" class="fz12 pading-bottom-10">点击头像前往个人中心-优惠中心-礼品卡处兑换即可使用</p>
            <button style="cursor: pointer;" class="btn_card_bg" data-dismiss="modal" aria-hidden="true" onclick="goGiftCard();">前往兑换</button>
        </div>
    </div>
    <div class="clear"></div>

</div>
<script src="<%=staticResourcePath%>/js/icheck.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
<script>
	function goGiftCard(){
		var href = "<%=njxBasePath%>/giftCard.shtml";
		window.open(href,"_blank"); 
	}
</script>

</s:elseif>
<link href="<%=njxBasePath%>/css/hotSearch/modal.css?v=<%=SystemConfig.SYSTEMINITTIME %>" rel="stylesheet" type="text/css">
<!-- <input  id="isGetActive" class="cl cl_1 btn" data-toggle="modal" data-target="#isGetActiveModel"/> -->
<div type="hidden" class="remind  animated fadeInUpBig" id="isGetActive2" style="display: none;">
	 <div>
		<div class="remind-header">
	        <a href="javascript:" type="button" class="close remind-close icon-guanbi"></a>
	        <h4 class="modal-title">微热点（微舆情）</h4>
	   </div>
		<div class="remind-body">
			<div class="remind-lf">
				<p class="fz16">您有一份奖励待领取哦</p>
				<a style="cursor: pointer;" onclick="goActivePay();" class="btn-remind">马上领取</a>
			</div>
			<div class="remind-rg">
				<img src="<%=njxBasePath %>/images/newuser/modal-pic.png"/>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
			$(function(){
				$('.remind-close').on('click',function(){
					$('.remind').hide();
				})
			})
		</script>
<!-- 所有登录弹框end -->

	
<!-- 右下角消息提醒弹框start -->
<%-- <div class="remind fadeInUpBig animated">
	 <div>
		<div class="remind-header">
	        <a href="javascript:;" type="button" class="close remind-close icon-guanbi"></a>
	        <h4 class="modal-title">微热点</h4>
	   </div>
		<div class="remind-body">
			<div class="remind-lf">
				<p class="fz16">您有一份奖励待领取哦</p>
				<a href="javascript:;" class="btn-remind">马上领取</a>
			</div>
			<div class="remind-rg">
				<img src="images/modal-pic.png"/>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$('.remind-close').on('click',function(){
		$('.remind').hide();
	})
</script> --%>
<!-- 右下角消息提醒弹框end -->
	
<!-- 新用户领取方案弹框start -->
<s:if test="admin.userExtendInfoElement!=null && admin.userExtendInfoElement.isGiftPackage==0">
	<script src='<%=njxBasePath%>/js/divselect.js?v=<%=SystemConfig.SYSTEMINITTIME %>' type="text/javascript"></script>
		<style type="text/css">
 			.modal-con p{font-size: 13px;color: #333333;   line-height: 1.8;}
 			.btn-orange {
			    color: #fff !important;
			    background-color: #f5773b !important;
			    border-color: #f5773b !important;
			}
			.fc_orange-b {
			    color: #f5773b !important;
			}
			.modal-con p{font-size: 13px;color: #333333;   line-height: 1.8;}
			.Upgrade .modal-header button.close {
			    color: #888;
			}
		</style>
	<!--领取方案 start-->
		<div id="getKeywordProject" class="modal modalTwo fade Upgrade b-radius10 border-n" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 600px; margin-left: -300px;">
			<div class="modal-header">
				 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			</div>
			<div class="modal-body icheckbox-list text-center" style="">
				<div class="modal-title bg_white">
					<img class="mt30"  src="<%=njxBasePath %>/images/newuser/model-icon-1.png" />					
				</div>
				<div class="modal-con p_h_40 bg_white">
					<div class="pading-top-30">
						<strong class="fz18">Hi 亲爱的微友</strong>
						<p class="mt20">恭喜您获得由微热点(微舆情)官方赠送的免费监测方案一个（有效期为7天）<br> 可享受免费的预警和日报等服务哦，快去领取吧~ </p>
					</div>					
				</div>
				<div class="modal-footer pading-bottom-30 bg_white" style="text-align: center;">
					<button onclick="getKeywordProject();" class="btn btn-orange btn-half" data-dismiss="modal" style="width: 160px;">立即领取</button><br>	
					<button class="btn btn-link fc_orange-b" data-dismiss="modal" style="width: 160px;background-color: unset !important;">下次领取吧~</button>
				</div>
			</div>
		</div>
		<!--领取方案 end-->
		<!--去设置方案 start-->
		<div id="goSetKeyword" class="modal modalTwo fade Upgrade b-radius10 border-n" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 600px; margin-left: -300px;">
			<div class="modal-header">
				 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			</div>
			<div class="modal-body icheckbox-list text-center" style="overflow-y:initial;">
				<div class="modal-title bg_white">
					<img class="mt30"  src="<%=njxBasePath %>/images/newuser/model-icon-1.png" />					
				</div>
				<div class="modal-con p_h_40 bg_white">
					<div class="pading-top-30">
						<strong class="fz18">Hi 亲爱的微友，恭喜您领取成功！</strong>
						<p class="mt20">快跟小微一起去监测你想知道的信息吧，点击以下按钮开始设置~</p>
					</div>					
				</div>
				<div class="modal-footer pading-bottom-30 bg_white" style="text-align: center;">
					<button onclick="goSetKeyword();" class="btn btn-orange btn-half" data-dismiss="modal" style="width: 160px;">去设置方案</button><br>	
					<button class="btn btn-link fc_orange-b" data-dismiss="modal" style="width: 160px;">下次设置吧~</button>
				</div>
			</div>
 
		</div>
		<!--去设置方案 end-->
</s:if>
<!-- 新用户领取方案弹框end -->

 <!-- 用于查看事件分析 -->
<form target="_blank" method="post" action="" id="messageForm"></form>
	<!--实时消息提醒 start-->
<div class="message_alert">
	<div class="ma_cont" style="display:none;">
		<div class="float_l">
			<div id="sinWaitMessage" class="nText shengyin-ico"></div>
		</div>

		<div class="float_r" id="action_param"></div>
		<div class="clear"></div>
	</div>
</div>
    <div id="notice" class="topFloat_r">
	<div class="notice">
		<a class="close" id="closeNotice" href="#" style="background: none;">×</a>
		<div>
			<div class="nTit">
				<h1>
					<i class="icon-shengyin fz18"></i>系统消息
				</h1>
			</div>
			<div class="noticetab">
				<ul class="tab_menu">
					<li id="gg" onclick="changetab('1');" class="current">公告</li>
					<li id="xx" onclick="changetab('2');">消息</li>
				</ul>

				<div class="tab_box" >
					<div id="gg_tab">
						<div class="accordion" id="noticeCon">
                    <ul>
                        <s:iterator value="#attr.noticeList" status="st" var="notice">
                            <%-- <li>
                                <div class="nText">
                                        ${notice.noticeContent}
                                </div>
                                <div class="nFoot">
                                    <span><s:date name="#notice.noticeSendTime" format="yyyy-MM-dd HH:mm:ss"/></span>
                                    <span class="tit">${notice.noticeTitle}</span>
                                </div>
                            </li> --%>
                            <li class="line">
                               <div class="nheard"><s:date name="#notice.noticeSendTime" format="yyyy-MM-dd HH:mm:ss"/></div>
                                <div class="nText">
                                     <p>${notice.noticeContent}</p>
                                </div>
                                <div class="nFoot">
                                    <span>${notice.noticeTitle}</span>
                                </div>
                            </li>
                        </s:iterator>
                    </ul>
                </div>
					</div>

					<div id="xx_tab" class="hide">
						<div class="accordion" id="noticeCon">
							<!--消息部分 start-->
							<ul>
								<li id="waitCheckMessage"></li>
								<li id="checkedMessage"></li>
							</ul>
							<!--消息部分 end-->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--系统公告 end-->

    <form name="buyForm" method="post" action="<%=njxBasePath%>/pay/confirm.shtml">
        <input type="hidden" name="myProduct.keywordId" id="buyKeywordId" value="0">
        <input type="hidden" name="myProduct.keywordPackageNum" id="buyKeywordPackageNum" value="1">
        <input type="hidden" name="myProduct.keywordPackagePrice" id="buyKeywordPackagePrice" value="0">
    </form>
    <input type="hidden" id="sucaiTotal" value="${sucaiTotal}" />
    <input type="hidden" id="collectTotal" value="${collectTotal}"/>
    <input type="hidden" id="cartTotal" value="${cartTotal}"/>
    <input type="hidden" id="userId" value="${admin.userId}"/>
    <input type="hidden" id="firstLoginSign" value="${firstLoginSign}">
	<div id="toolCase"></div>
    <!--head代码 登陆后 start-->
    <div class="w-head ${param.isShowTop == 1? 'abs w-headHover':''} min-layout1200" id="weiyi_head_shouye">
    <div class="w-headbar-top">
        <div class="w-headbar-logo">
            <%--<img src="<%=njxBasePath%>/images/shouye/w-sm-logo.png" alt="">--%>
				<img src="<%=njxBasePath%>/css/indexV4/base/images/w-md-logo.png" alt="">
        </div>
        <!--<img src="base/images/w-logo-title.png" class="w-headbar-txt margin-left-20" alt="">-->
        <div class="w-headbar-r">
            <div class="w-heabar-menu">
                <div class="w-heabar-menu-item">
                    <a href="javascript:void(0);" id="noticeLink">系统公告</a>
                </div>
                <div class="w-heabar-menu-item <s:if test='currentPage=="productsBuy"'>active</s:if>">
                    <a href="<%=njxBasePath%>/pay/buy.shtml">产品选购</a>
                </div>
                <div class="w-heabar-menu-item <s:if test='currentPage=="novice"'>active</s:if>">
                    <a href="<%=njxBasePath%>/novice.shtml">新手专区</a>
                </div>
                <div class="w-heabar-menu-item <s:if test='currentPage=="bill"'>active</s:if>">
                    <a href="<%=njxBasePath%>/bill/order.shtml">索取发票</a>
                </div>
                <div class="w-heabar-user rel">
                    <a href="<%=njxBasePath%>/usercenter/account.shtml">
                    	<s:if test='admin.userHead == null||admin.userHead==""'>
	                        <div class="w-heabar-user-img">
	                            <img src="<%=staticResourcePath%>/css/indexV4/base/images/user.png" alt="">
	                        </div>
                        </s:if>
                        <s:else>
                        	<div class="w-heabar-user-img">
                        		<img src='${admin.userHead }'  class="tx">
                        	</div>
                        </s:else>
                        
                              <span class="w-heabar-name">
                              <s:if test="admin.nickname==null||admin.nickname==''">${admin.username} </s:if>
                              <s:else><s:property value="#attr.admin.nickname" escape="true"/></s:else>
                              </span>
                        
                    </a>
                    <div class="UserPrompt abs showObject" style="display: none;">
                                	<div>
	                                    <dl class="lineBottom">
							      	        <dd>
							      	        	<span class="float_l">您好，<s:if test="admin.nickname==null||admin.nickname==''">${admin.username} </s:if><s:else><s:property value="#attr.admin.nickname" escape="true"/></s:else></span>
				      							<span class="float_r"><a href="javascript:" class="fc_gray2" onclick="logout();">【退出】</a></span>
							      	        </dd>
						                </dl>
						                <dl class="mt10">
									    	<dd>
									      		<span>
									      			<img src="<%=staticResourcePath %>/images/v-icon.png"/>
									      			<font class="fc_red" id="userHaveCreditNum">${admin.creditAmount }</font> 微积分
									      		</span>
									      		<span class="float_r">
									      			<button class="btn btn-danger btn-half" onclick="location.href = actionBase + '/pay/buy.shtml';">购买</button>
									      		</span>
									    	</dd>
									    </dl>
									    <dl class="mt10 <s:if test='admin.isProUser==1'>pro-y</s:if>" id="proDl" style="display: none;">
					      					<dd>
					      					</dd>
					      				</dl>
					      				<dl id="proDlBtn" style="display: none;">
					      					<dd>
					      						<button class="btn btn-danger btn-half btn-big btn-block">去续费</button>
					      					</dd>
					      				</dl>
					      				<dl class="dl_list rel" id="dl_list0" style="display: none;">
									      	<dd class="abs" style="bottom: 10px; right: 0;">
									      		<a class="link_yellowHover bottomMore" href="javascript:">更多 <i class="icon-angle-down"></i></a>
									      	</dd>
									      	<dd>
									      		<h1><strong>用户剩余权益：</strong></h1>
									      	</dd>
									      	<c:if test="${admin.noUseKeywordCount > 0}">
									      		<dd>监测方案<font class="fc_red"> X ${admin.noUseKeywordCount }</font></dd>
									      	</c:if>
									      	<c:if test="${admin.userAnalysisValidCount > 0 }">
									      		<dd>全网事件分析<font class="fc_red"> X ${admin.userAnalysisValidCount }</font></dd>
									      	</c:if>
									      	<c:if test="${admin.userWeiboAnalysisValidCount > 0 }">
									      		<dd>微博事件分析<font class="fc_red"> X ${admin.userWeiboAnalysisValidCount }</font></dd>
									      	</c:if>
									      	<c:if test="${admin.userBriefValidCount > 0 }">
									      		<dd>简报制作<font class="fc_red"> X ${admin.userBriefValidCount }</font></dd>
									      	</c:if>
									      	<c:if test="${admin.userProductAnalysisValidCount > 0 }">
									    		<dd>竞品分析<font class="fc_red"> X ${admin.userProductAnalysisValidCount }</font></dd>
									    	</c:if>
									    	<c:if test="${admin.userSingleWeiboAnalysisValidCount > 0 }">
									    		<dd>微博传播效果分析<font class="fc_red"> X ${admin.userSingleWeiboAnalysisValidCount }</font></dd>
									    	</c:if>
									    	<c:if test="${admin.exportDataCount > 0 }">
									    		<dd>导出数据<font class="fc_red"> X ${admin.exportDataCount }</font></dd>
									    	</c:if>
									    </dl>
                                	</div>
                                </div>
                </div>

            </div>

        </div>

    </div>
<!--     登录后的 -->
    <div class="w-headbar-bottom">
        <div class="w-headbar-nav">
            <ul class="w-headbar-list clearfix">
                <li <s:if test='currentPage=="hot"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/home.shtml">热点发现</a>
                </li>
                <li <s:if test='currentPage=="caseAnalysis"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/caseAnalysis.shtml">案例库</a>
                </li>
                <li  <s:if test='currentPage=="bigDataRead"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/infoData.shtml">大数据报告</a>
                </li>
                <li <s:if test='currentPage=="jiance"'>class="active"</s:if> <s:if test='currentPage=="brief"'>class="active"</s:if>>
                    <s:if test="admin.stopFlag">
                    		<a data-toggle="modal" data-target="#stopUserModal" href="<%=njxBasePath%>/keyword.shtml">信息监测</a>
                    	</s:if>
                    	<s:elseif test="admin.userExtendInfoElement!=null && admin.userExtendInfoElement.isGiftPackage==0">
                    		<a data-toggle="modal" data-target="#getKeywordProject" href="<%=njxBasePath%>/keyword.shtml">信息监测</a>
                    	</s:elseif>
                    	<s:else>
                    		<a href="<%=njxBasePath%>/keyword.shtml">信息监测</a>
                    	</s:else>
                </li>
                <%--<li class='j_hover'>--%>
                    <%--<a href="JavaScript:void(0)">大数据工具</a>--%>
                    <%--<div class="w-headbar-dropdown">--%>
		                <%--<div class="w-dropdown-inner active">--%>
		                    <%--<ul>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/view/weiboEventAnalysis/taskList.shtml">微博事件分析</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/view/eventAnalysis/taskList.shtml">全网事件分析</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/productsAnalysis.shtml">竞品分析</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/i/singleWeiboAnalysis/index.shtml">微博传播分析</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/view/reviewAnalysis/reviewIndex.shtml">评论分析</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/splitWords.shtml">文本挖掘</a>--%>
		                        <%--</li>--%>
<%--<!-- 		                    	<li> -->--%>
<%--<!-- 		                            <a >新闻稿分析</a> -->--%>
<%--<!-- 		                        </li> -->--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/emotionMap.shtml">微博情绪</a>--%>
		                        <%--</li>--%>
		                    <%--</ul>--%>
		                <%--</div>--%>
		            <%--</div>--%>
                <%--</li>--%>
				<%--<li>--%>
					<%--<a href="JavaScript:void(0)">专业解决方案</a>--%>
				<%--</li>--%>
                <li class="j_hover
					<s:if test='currentPage=="analysisWb"'>active</s:if> <s:if test='currentPage=="analysis"'>active</s:if>
<s:if test='currentPage=="productsAnalysis"'>active</s:if>
<s:if test='currentPage=="reviewAnalysis"'>active</s:if>
<s:if test='currentPage=="singleWeiboAnalysis"'>active</s:if>
<s:if test='currentPage=="emotionMap"'>active</s:if>
<s:if test='currentPage=="search"'>active</s:if>
">
                    <a href="JavaScript:void(0)">分析与评估工具</a>
                    <div class="w-headbar-dropdown">
                        <div class="w-dropdown-inner active" style="left: -85px;">
                            <ul>
                                <li>
                                    <a href="<%=njxBasePath%>/view/weiboEventAnalysis/taskList.shtml">微博事件分析</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/view/eventAnalysis/taskList.shtml">全网事件分析</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/productsAnalysis.shtml">竞品分析</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/view/reviewAnalysis/reviewIndex.shtml">评论分析</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/i/singleWeiboAnalysis/index.shtml">微博传播分析</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/emotionMap.shtml">微博情绪</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/splitWords.shtml">文本挖掘</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>
                <%--<li class="j_hover">--%>
                    <%--<a href="JavaScript:void(0)">专业解决方案</a>--%>
                    <%--<div class="w-headbar-dropdown">--%>
                        <%--<div class="w-dropdown-inner active" style="left: -85px;">--%>
                            <%--<ul>--%>
                                <%--<li>--%>
                                    <%--<a href="https://www.u-mei.com/web/index.html" target="_blank">U媒</a>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<a href="https://www.yqt365.com" target="_blank">舆情通</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</li>--%>

                <%--<li>--%>
					<%--<a href="JavaScript:void(0)">蜜度-福州高峰论坛</a>--%>
				<%--</li>--%>
            </ul>
        </div>
    </div>

</div>
   
<s:if test="admin.stopFlag == 1">
	<!-- 用户监测方案封停提示弹框start -->
	<style type="text/css">
		.Upgrade.modal{border: none !important;}
		.Upgrade .modal-title {position: relative; height: 160px; background: #FFFFFF; border-radius: 18px;}
		.Upgrade .model-tit-bg {position: absolute; left: 0; right: 0; margin: auto; width: 100%; height: 150px; top: -5px;}
		.Upgrade.modal.fade.in {border-radius: 35px; overflow: hidden !important;}
		.Upgrade .modal-body {border-radius: 18px;}
		.Upgrade .modal-title .title {position: absolute; top: 30px; margin: auto; display: inline-block; margin-left: 35px;}
		.Upgrade .modal-title .title h3 {font-size: 28px; font-weight: bold; color: #fff; margin-bottom: 10px;}
		.Upgrade .modal-title .title p {font-size: 12px; color: #fff;}
		.modal-con{background: #FFFFFF; padding: 0 40px;}
		.modal-con p{font-size: 13px; color: #333333; line-height: 1.8;}
		.Upgrade  .modal-footer{background-color: #FFFFFF;}
		.Upgrade .modal-footer a{ display: inline-block; background: url(<%=staticResourcePath %>/images/model-btn2.png) no-repeat; background-size: cover; width: 200px; height: 50px;color: #FFFFFF;font-size: 14px;line-height: 50px;padding: 0;text-decoration: none;}
		.Upgrade .modal-header button.close{color: #FFFFFF;}
	</style>
	<div id="stopUserModal" class="modal modalTwo fade Upgrade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 650px; margin-left: -300px;">
		<div class="modal-header">
		</div>
		<div class="modal-body icheckbox-list" style="overflow-y:initial;">
			<div class="modal-title">
				<img class="model-tit-bg" src="<%=staticResourcePath %>/images/model-top-1.jpg" />					
			</div>
			<div class="modal-con">
				<div class="pading-top-30">
					<strong class="fz18">Hi 亲爱的&nbsp;<s:if test="admin.nickname==null||admin.nickname==''">${admin.username} </s:if><s:else><s:property value="#attr.admin.nickname" escape="true"/></s:else>,</strong>
					<p class="mt20">&nbsp;&nbsp;&nbsp;&nbsp;经微热点(微舆情)系统实时监控，发现您的账号近期有异常行为，为了能让广大微友们有一个良好的系统运行环境，小微决定忍痛对您的账号进行部分功能锁定，<fmt:formatDate value="${admin.stopEndTime}" pattern="yyyy-MM-dd HH:mm:ss" />之后自动解锁，如有疑问，可直接联系微热点(微舆情)客服妹妹哦~</p>
				</div>					
			</div>
			<div class="modal-footer pading-bottom-30" style="text-align: center;">
				<a href="javascript:void(0)" class="" data-dismiss="modal" aria-hidden="true">我知道了，谢谢提醒</a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!-- 用户监测方案封停提示弹框end -->
	</s:if>
<!--head代码 end-->
</s:if>
<s:else>
	<link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/reginReadPage.css?v=<%=SystemConfig.SYSTEMINITTIME %>">
	<script src='<%=staticResourcePath%>/js/MD5.js?v=<%=SystemConfig.SYSTEMINITTIME %>' type="text/javascript"></script>
	<script src='<%=njxBasePath%>/js/login.js?v=<%=SystemConfig.SYSTEMINITTIME %>' type="text/javascript"></script>
	<link href="<%=njxBasePath%>/css/newRegin/loginStyle.css?v=<%=SystemConfig.SYSTEMINITTIME %>" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<%=staticResourcePath%>/js/twitter-bootstrap-hover-dropdown.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
	<!--头部导航start-->
	<div id="MainTool"></div>
<div class="w-head headHover min-layout1200" id="noLoginCssId">
    <div class="w-headbar-top">
        <div class="w-headbar-logo">
            <%--<img src="<%=njxBasePath%>/images/shouye/w-sm-logo.png" alt="">--%>
				<img src="<%=njxBasePath%>/css/indexV4/base/images/w-md-logo.png" alt="">
        </div>
        <img src="<%=njxBasePath%>/images/shouye/w-logo-title.png" class="w-headbar-txt margin-left-20" alt="">
        <%--<div class="w-headbar-r">--%>
            <%--<a id="toRegisterID" style="cursor: pointer;" data-toggle="modal" data-target="#reginTop" class="font-size-14 color_1 padding-horizontal-30">免费注册</a>--%>
            <%--<a id="login" href="#" onclick="getRan()" data-toggle="modal" data-target="#loginModal" class="btn-login">登录</a>--%>
        <%--</div>--%>

    </div>
    <div class="w-headbar-bottom">
        <div class="w-headbar-nav">
        	<ul class="w-headbar-list clearfix" id="m">
                <li <s:if test='currentPage=="hot"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/home.shtml" data-toggle="modal">热点发现</a>
                </li>
                <li>
                    <a href="JavaScript:intoTo(24)">案例库</a>
                </li>
                <li  <s:if test='currentPage=="bigDataRead"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/infoData.shtml">大数据报告</a>
                </li>
                <li>
                    <a href="JavaScript:intoTo(5)" >信息监测</a>
                </li>
				<li class='j_hover'>
					<a href="JavaScript:void(0)">大数据工具</a>
					<div class="w-headbar-dropdown">
						<div class="w-dropdown-inner active">
							<ul>
								<li>
									<a href="JavaScript:intoTo(11)">微博事件分析</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(10)">全网事件分析</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(13)">竞品分析</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(12)">微博传播分析</a>
								</li>
								<li>
									<a href="#" data-toggle="modal" data-target="#loginModal">评论分析</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(25)">文本挖掘</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(31)">微博情绪</a>
								</li>
							</ul>
						</div>
					</div>
				</li>
				<%--<li class="j_hover">--%>
					<%--<a href="JavaScript:void(0)">专业解决方案</a>--%>
					<%--<div class="w-headbar-dropdown">--%>
						<%--<div class="w-dropdown-inner active" style="left: -85px;">--%>
							<%--<ul>--%>
								<%--<li>--%>
									<%--<a href="https://www.u-mei.com/web/index.html" target="_blank">U媒</a>--%>
								<%--</li>--%>
								<%--<li>--%>
									<%--<a href="https://www.yqt365.com" target="_blank">舆情通</a>--%>
								<%--</li>--%>
							<%--</ul>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--<a href="JavaScript:void(0)">蜜度-福州高峰论坛</a>--%>
				<%--</li>--%>
            </ul>
			<div class="pull-right">
				<a id="toRegisterID" style="cursor: pointer;" data-toggle="modal" data-target="#reginTop" class="font-size-14 color_1 padding-horizontal-30">10秒注册</a>
				<a id="login" href="#" onclick="getRan()" data-toggle="modal" data-target="#loginModal" class="btn-login">登录</a>
			</div>
        </div>
        <%--<div class="w-headbar-search" style="display: none;">--%>
            <%--<input type="text" class="w-search-input" placeholder="人名、企业名、品牌名或事件关键词" id="search-keyword"/>--%>
            <%--<span onclick="goOpenTools();" class="w-headbar-search-icon"><i class="icon icon-sousuo1 font-size-20 color_2"></i></span>--%>
            <%--<div class="w-search-outline"></div>--%>
        <%--</div>--%>
    </div>

</div>
<!--头部导航end-->
    
    <!--注册页 start-->
		<div id="reginTop" class="modal modalTwo fade Upgrade b-radius10 border-n" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 960px; margin-left: -480px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			</div>
			<div class="modal-body bg_white icheckbox-list text-center clearfix" style="overflow-y:initial;">
				<div class="title p_v_30 fz24">
					<h3>微热点(微舆情)网站服务条款</h3>
				</div>
				<div class="servers mt10 text-left" style="height: 370px; overflow: hidden; overflow-y: auto;">
             

            <p>本服务条款是微热点(微舆情)网站（<a href="www.wrd.cn" id="help_title">www.wrd.cn</a>，以下称为“本网站”）与用户（下称为“您”），共同缔结的对双方具有约束力的有效契约。</p>
            <p>微热点(微舆情)向用户提供本网站上所展示的产品与服务（下称“微热点(微舆情)”、“微热点(微舆情)服务”、“本服务”），并将不断更新服务内容，最新的微热点(微舆情)服务以本网站上的相关产品及服务介绍的页面展示以及向用户实际提供的为准。</p>
            <h1>一、总则</h1>

            <p>1.1 您确认：您在使用本服务之前，已经充分阅读、理解并接受本服务条款的全部内容（特别是以加粗及/或下划线标注的内容），一旦您选择“同意”并完成注册流程或使用本服务，即表示您同意遵循本服务条款之所有约定。</p>

            <p>1.2 您同意：微热点(微舆情)有权随时对本服务条款及相应的服务规则内容进行单方面的变更，并有权以消息推送、网页公告等方式予以公布，而无需另行单独通知您；若您在本服务条款内容公告变更后继续使用本服务的，表示您已充分阅读、理解并接受修改后的协议内容，也将遵循修改后的条款内容使用本服务；若您不同意修改后的服务条款，您应立即停止使用本服务。</p>
            <h1>二、账户</h1>
            <p>2.1 注册</p>
            <p>2.1.1 注册者资格</p>
            <p>2.1.1.1 您确认在您完成注册程序或以其他微热点(微舆情)允许的方式实际使用本服务时，您应当是具备完全民事权利能力和完全民事行为能力的自然人、法人或其他组织（以下统称为“法律主体”）。</p>
            <p>2.1.1.2 若您是未成年人或限制民事行为能力人，则您不具备前述主体资格，您及您的监护人应承担因您的不当注册行为而导致的一切后果，且微热点(微舆情)有权注销（永久冻结）您的账户。</p>
            <p>2.1.2 注册、账户</p>
            <p>2.1.2.1 在您按照注册页面提示填写信息、阅读并同意本服务条款且完成全部注册程序后，或在您按照激活页面提示填写信息、阅读并同意本服务条款且完成全部激活程序后，或您以其他微热点(微舆情)允许的方式实际使用本网站服务时，您即受本服务条款约束。您可以使用您确认手机号码作为登录手段进入本网站。</p>
            <p>2.1.2.2 目前微热点(微舆情)允许一个法律主体拥有多个微热点(微舆情)账户，但一个微热点(微舆情)账户仅能对应唯一的法律主体。除非有法律规定或生效法律文书确定，或者符合微热点(微舆情)公布的条件，否则您不得以任何方式转让、赠与或让他人继承您的微热点(微舆情)账户。同时，在进行符合条件的微热点(微舆情)账户转让、赠与或继承时，微热点(微舆情)有权要求您、及/或受让受赠者、或您的继承人提供合格的文件材料并按照微热点(微舆情)要求的操作流程办理。</p>
            <p>2.1.2.3 通常情况下，您的微热点(微舆情)账户是您在本网站进行一切活动的唯一身份识别依据，每一个微热点(微舆情)账户都可以在本网站独立开展活动。但在下列情形下，微热点(微舆情)有权根据自己的判断，对同一及/或关联法律主体拥有的多个微热点(微舆情)账户进行统一处理，包括但不限于：</p>
            <p>2.1.2.3.1多个微热点(微舆情)账户之间存在一项或多项注册信息相同、代为付款、购买的产品或服务用于同一目的，或其他关联情形，并存在违反法律法规、本服务条款、微热点(微舆情)各产品条款或其他微热点(微舆情)规则的行为，且微热点(微舆情)通过结合其他相关证据足以判断上述微热点(微舆情)账户实际属于同一法律主体或同一团体的；</p>
            <p>2.1.2.3.2 其他微热点(微舆情)有充足理由需要对多个微热点(微舆情)账户进行统一处理的情形。</p>
            <p>2.1.3 信息</p>
            <p>2.1.3.1 在完成注册或激活流程时，您应当按照法律法规要求，按相应页面的提示准确提供并及时更新您的资料，以使之真实、及时，完整和准确。</p>
            <p>2.1.3.2 您应当准确填写并及时更新您提供的联系电话、联系地址、邮政编码等联系方式，以便微热点(微舆情)与您进行有效联系，因通过这些联系方式无法与您取得联系，导致您在使用微热点(微舆情)服务过程中产生任何损失或增加费用的，应由您完全独自承担。您了解并同意，您有义务保持您提供的联系方式的有效性，如有变更需要更新的，您应按微热点(微舆情)的要求进行操作。</p>
            <p>2.2 账户安全</p>
            <p>2.2.1 您须自行负责对您的微热点(微舆情)账户和密码保密，且须对您在该登录名和密码下发生的所有活动（包括但不限于获取信息、转发信息、撰写报告或提交各类规则协议、网上续签协议或购买服务等）承担责任。您同意：(a)如发现任何人未经授权使用您的微热点(微舆情)账户和密码，或发生违反保密规定的任何其他情况，您会立即通知微热点(微舆情)；及(b)确保您在每个上网时段结束时，以正确步骤离开网站。微热点(微舆情)不能也不会对因您未能遵守本款规定而发生的任何损失或损毁负责。您理解微热点(微舆情)对您的请求采取行动需要合理时间，微热点(微舆情)对在采取行动前已经产生的后果（包括但不限于您的任何损失）不承担任何责任。</p>
            <p>2.2.2 除非有法律规定或司法裁定，且征得微热点(微舆情)的同意，否则，您的登录和密码不得以任何方式转让、赠与或继承（与账户相关的财产权益除外）。</p>
            <p>2.2.3您理解并同意，微热点(微舆情)有权按照国家司法、行政、军事、安全等机关（包括但不限于公安机关、检察机关、法院、海关、税务机关、安全部门等）的要求向上述机关提交您的个人注册信息和使用记录。</p>
            <p>2.3 账户注销</p>
            <p>2.3.1 微热点(微舆情)保留在您违反国家、地方法律法规规定或违反本服务条款的情况下，中止或终止为您提供部分或全部服务、直至注销微热点(微舆情)账户的权利。</p>
            <p>2.3.2 微热点(微舆情)登录名的注销</p>
            <p>2.3.2.1 违反微热点(微舆情)公布的任何服务协议/条款、管理规范等规范内容；</p>
            <p>2.3.2.2 破坏或试图破坏微热点(微舆情)系统正常的使用秩序；</p>
            <p>2.3.2.3 任何使用含有微热点(微舆情)名称、品牌且对他人有误导嫌疑或任何使用某种中英文(全称或简称)、数字、域名等意图表示或映射与微热点(微舆情)具有某种关系的；</p>
            <p>2.3.2.4微热点(微舆情)根据自行合理判断，认为可能是与如上行为性质相同或产生如上类似风险的其他情况。</p>
            <h1>三、网站服务使用守则</h1>
            <p>为有效保障您使用本服务的合法权益，您理解并同意接受以下规则：</p>
            <p>3.1 您通过包括但不限于以下方式向微热点(微舆情)发出的指令，均视为您本人的指令，不可撤回或撤销，您应自行对微热点(微舆情)执行前述指令所产生的任何结果承担责任。</p>
            <p>3.1.1 通过您的微热点(微舆情)账户和密码进行的所有操作；</p>
            <p>3.1.2 通过与您的账号绑定的手机号码向微热点(微舆情)发送的全部信息；</p>
            <p>3.1.3 通过与您的账号绑定的其他硬件、终端、软件、代号、编码、代码、其他账户名等有形体或无形体向微热点(微舆情)发送的信息；</p>
            <p>3.1.4 其他微热点(微舆情)与您约定或微热点(微舆情)认可的其他方式。</p>
            <p>3.2 您在使用本服务过程中，微热点(微舆情)网站上出现的关于网站操作的提示或微热点(微舆情)发送到您手机的信息（短信或推送等）内容是您使用本服务的相关规则，您使用本服务即表示您同意接受本服务的相关规则。您了解并同意微热点(微舆情)有权单方修改服务的相关规则，而无须征得您的同意，服务规则应以您使用服务时的页面提示（或发送到该手机的短信或电话等）为准，您同意并遵照服务规则是您使用本服务的前提。</p>
            <p>3.3 微热点(微舆情)可能会以电子邮件（或发送到您手机的短信或电话等）方式通知您服务进展情况以及提示您进行下一步的操作，但微热点(微舆情)不保证您能够收到或者及时收到该邮件（或发送到该手机的短信或电话等），且不对此承担任何后果。因此，在服务过程中您应当及时登录到本网站查看和进行操作。因您没有及时查看和对服务状态进行修改或确认或未能提交相关申请而导致的任何纠纷或损失，微热点(微舆情)不负任何责任。</p>
            <p>3.4 您授权微热点(微舆情)可以通过向第三方审核您的身份和资格，取得您使用本服务的相关资料。</p>
            <p>3.5 在您开始使用微热点(微舆情)的某一产品或服务前，可能需要和微热点(微舆情)就这一产品或服务签订单独的服务协议。您只有在接受该服务协议的全部内容后方可使用该产品或服务；如您不同意该服务协议的部分或者全部的，请您不要进行后续操作。</p>
            <p>3.6 在您使用微热点(微舆情)服务时，微热点(微舆情)有权依照相应的产品/及或服务收费介绍、订单及/或相关协议向您收取服务费用。微热点(微舆情)拥有制订及调整服务费之权利，具体服务费用以您使用本服务时页面上所列之收费方式公告或您与微热点(微舆情)达成的其他书面协议为准。</p>
            <h2>3.7 特别提示</h2>
            <p>3.7.1 微热点(微舆情)服务开通后，即使您未新增服务项目或资源，亦未进行新的操作，但因占用资源的收费依旧会发生。</p>
            <p>3.7.2 微积分作为微热点(微舆情)的预支付站内货币，享受特别折扣优惠，一旦购买，不支持退换，请您酌情考虑后购买。</p>
            <h2>3.8 申诉及处理</h2>
            <p>3.8.1 在您使用微热点(微舆情)服务的过程中，有可能因为存在本服务条款第5.3条所列情形之一，而被微热点(微舆情)采取了包括但不限于停止全部或部分服务、限制服务的功能措施，微热点(微舆情)将通过邮件、站内信、短信或电话等方式通知您按照相应的程序进行申诉。</p>
            <p>3.8.2 您通过申诉程序，向微热点(微舆情)申请解除上述限制或冻结或恢复服务的，应按照微热点(微舆情)的要求，如实提供身份证明及相关资料，以及微热点(微舆情)要求的其他信息或文件，以便微热点(微舆情)进行核实。您应充分理解您的申诉并不必然被允许，微热点(微舆情)有权依照自行判断来决定是否同意您的申诉请求。</p>
            <p>3.8.3 您理解并同意，如果您拒绝如实提供身份证明及相关资料的，或未能通过微热点(微舆情)审核的，微热点(微舆情)有权长期冻结该等账户且长期限制该等产品或者服务的部分或全部功能。</p>
            <p>3.9 关于第三方</p>
            <p>3.9.1 如果您通过使用本服务，将获取使用来自第三方的任何产品或服务，您还可能受限于该等第三方的相关条款和条件，微热点(微舆情)对此不予过问亦不承担任何责任，本服务条款不影响您与该第三方的法律关系。</p>
            <p>3.9.2 您确认并同意，微热点(微舆情)的各关联公司均为本服务条款的第三方受益人，且微热点(微舆情)关联公司有权直接强制执行并依赖本服务条款中授予其利益的任何规定。除此之外，任何第三方均不得作为本服务条款的第三方受益人。</p>
            <h1>四、 您的权利和义务</h1>
            <p>4.1 您有权利享受微热点(微舆情)提供的互联网技术和信息服务，并有权利在接受微热点(微舆情)提供的服务时获得微热点(微舆情)的技术支持、咨询等服务，服务内容详见本网站相关产品介绍。</p>
            <p>4.2 您保证不会利用技术或其他手段破坏或扰乱本网站及微热点(微舆情)其他服务。</p>
            <p>4.3 您应尊重微热点(微舆情)及其他第三方的知识产权和其他合法权利，并保证在发生侵犯上述权益的违法事件时尽力保护微热点(微舆情)及其股东、雇员、合作伙伴等免于因该等事件受到影响或损失；微热点(微舆情)保留您侵犯微热点(微舆情)合法权益时终止向您提供服务并不退还任何款项的权利。</p>
            <p>4.4 对由于您向微热点(微舆情)提供的联络方式有误以及您用于接受微热点(微舆情)邮件的电子邮箱安全性、稳定性不佳而导致的一切后果，您应自行承担责任，包括但不限于因您未能及时收到微热点(微舆情)的相关通知而导致的后果和损失。</p>
            <p>4.5您保证：</p>
            <p>4.5.1.您使用微热点(微舆情)产品或服务时将遵从国家、地方法律法规、行业惯例和社会公共道德，不会利用微热点(微舆情)提供的服务进行存储、发布、传播如下信息和内容：违反国家法律法规政策的任何内容（信息）；违反国家规定的政治宣传和/或新闻信息；涉及国家秘密和/或安全的信息；封建迷信和/或淫秽、色情、下流的信息或教唆犯罪的信息；博彩有奖、赌博游戏；违反国家民族和宗教政策的信息；妨碍互联网运行安全的信息；侵害他人合法权益的信息和/或其他有损于社会秩序、社会治安、公共道德的信息或内容;您同时承诺不得为他人发布上述不符合国家规定和/或本服务条款约定的信息内容提供任何便利;</p>
            <p>4.5.2. 使用微热点(微舆情)产品或服务时，应遵守您与微热点(微舆情)签订的服务条款和您确认同意的订购页面的内容，包括但不限于：</p>
            <p>4.5.2.1. 您应按时付款；</p>
            <p>4.5.2.2. 不应出现任何破坏或试图破坏网络安全的行为等；</p>
            <p>如您违反上述保证，微热点(微舆情)除有权根据相关服务条款采取删除信息、中止服务、终止服务的措施，并有权限制您账户如新购产品或服务、续费等部分或全部功能，如因您上述行为给微热点(微舆情)造成损失的，您应予赔偿。
            </p>
            <p>4.6 若您使用的某项服务中包含可下载的微热点(微舆情)软件，则微热点(微舆情)仅授予您非独占性的、不可转让的、非商业运营目的的个人使用许可。除非微热点(微舆情)另有明示或与您另有约定外，您不得复制、修改、发布、出售或出租服务或所含软件的任何部分，也不得进行反向工程或试图提取该软件的源代码。</p>
            <h1>五、 微热点(微舆情)的权利和义务</h1>
            <p>5.1 微热点(微舆情)应根据您选择的服务以及交纳款项的情况向您提供合格的网络技术和信息服务。</p>
            <p>5.2微热点(微舆情)承诺对您资料采取对外保密措施，不向第三方披露您资料，不授权第三方使用您资料，除非：</p>
            <p>5.2.1 依据本服务条款或者您与微热点(微舆情)之间其他服务协议、合同、在线条款等规定可以提供；</p>
            <p>5.2.2 依据法律法规的规定应当提供；</p>
            <p>5.2.3 行政、司法等职权部门要求微热点(微舆情)提供；</p>
            <p>5.2.4 您同意微热点(微舆情)向第三方提供；</p>
            <p>5.2.5 微热点(微舆情)解决举报事件、提起诉讼而提交的；</p>
            <p>5.2.6 微热点(微舆情)为防止严重违法行为或涉嫌犯罪行为发生而采取必要合理行动所必须提交的；</p>
            <p>5.2.7 微热点(微舆情)为向您提供产品、服务、信息而向第三方提供的，包括微热点(微舆情)通过第三方的技术及服务向您提供产品、服务、信息的情况。</p>
            <h1>六、 隐私及其他个人信息的保护</h1>
            <p>一旦您同意本服务条款或使用本服务，您即同意微热点(微舆情)按照以下条款来使用和披露您的个人信息。</p>
            <p>6.1 登录名和密码</p>
            <p>在您注册帐户时，微热点(微舆情)会要求您设置微热点(微舆情)账户登录名和密码来识别您的身份，您的帐号是您的手机号码，也是唯一你通过验证码找回您的密码的方式。您仅可通过您设置的密码来使用该账户，如果您泄漏了密码，您可能会丢失您的个人识别信息。该账户和密码因任何原因受到潜在或现实危险时，您应该立即和微热点(微舆情)取得联系，在微热点(微舆情)采取行动前，微热点(微舆情)对此不负任何责任。</p>
            <p>6.2 用户信息</p>
            <p>您完成账户注册时，可以向微热点(微舆情)提供您的昵称和电子邮件地址，您还可以选择来填写相关附加信息（包括但不限于您公司所在的省份和城市、时区和邮政编码、姓名）。为有针对性地向您提供新的服务和机会，您了解并同意微热点(微舆情)及其关联公司将通过您的电子邮件地址或该手机通知您这些信息。</p>
            <p>6.3 登录记录</p>
            <p>为了保障您使用本服务的安全以及不断改进服务质量，微热点(微舆情)将记录并保存您登录和使用本服务的相关信息，但微热点(微舆情)承诺不将此类信息提供给任何第三方（除双方另有约定或法律法规另有规定及微热点(微舆情)关联公司外）。</p>
            <p>6.4外部链接</p>
            <p>本网站可能含有到其他网站的链接，但微热点(微舆情)对其他网站的隐私保护措施不负任何责任。微热点(微舆情)可能在任何需要的时候增加商业伙伴或共用品牌的网站。</p>
            <p>6.5安全</p>
            <p>微热点(微舆情)仅按现有技术提供相应的安全措施来使微热点(微舆情)掌握的信息不丢失，不被滥用和变造。这些安全措施包括向其他服务器备份数据和对用户密码加密。尽管有这些安全措施，但微热点(微舆情)不保证这些信息的绝对安全。</p>
            <h1>七、 系统中断或故障</h1>
            <p>系统可能因下列状况无法正常运作，使您无法使用各项互联网服务时，微热点(微舆情)不承担损害赔偿责任，该状况包括但不限于：</p>
            <p>7.1 微热点(微舆情)在系统停机维护期间。</p>
            <p>7.2 电信设备出现故障不能进行数据传输的。</p>
            <p>7.3 因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成微热点(微舆情)系统障碍不能执行业务的。</p>
            <p>7.4 由于黑客攻击、电信部门技术调整或故障、网站升级、银行方面的问题等原因而造成的服务中断或者延迟。</p>

            <h1>八、 责任范围及责任限制</h1>
            <p>8.1 微热点(微舆情)仅对本服务条款中列明的责任承担范围负责。</p>
            <p>8.2 本服务之合作单位，所提供之服务品质及内容由该合作单位自行负责。</p>
            <p>8.3 您了解并同意，因您使用本服务、违反本服务条款或在您的账户下采取的任何行动，而导致的任何第三方索赔，应且仅应由您本人承担。如果由此引起微热点(微舆情)及其关联公司、员工、客户和合作伙伴被第三方索赔的，您应负责处理，并承担由此造成的全部责任。</p>
            <p>8.4 因互联网条件和服务能力的限制，微热点(微舆情)不保证信息服务的内容是绝对完整和准确的，微热点(微舆情)提供的内容仅供用户参考。微热点(微舆情)的所有内容来源于网络，微热点(微舆情)仅对互联网内容提供抓取、搜索和归纳汇总服务，对于因互联网源内容不准确、源内容侵权、源内容抓取不完整、抓取到的内容分析不准确等原因引起的任何直接的、间接的、惩罚性的、特殊的、派生的损失（包括业务损失、收益损失、利润损失、使用数据、商誉或其他经济利益的损失），不论是如何产生的，也不论是由对本服务条款的违约（包括违反保证）还是由侵权造成的，均不负有任何责任，即使事先已被告知此等损失的可能性。另外即使本服务条款规定的排他性救济没有达到其基本目的，也应排除微热点(微舆情)对上述损失的责任。</p>
            <p>8.5 除本服务条款另有规定或微热点(微舆情)与您就某一具体产品及/或服务另有约定外，在任何情况下，您同意微热点(微舆情)对本服务条款所承担的赔偿责任总额不超过向您收取的当月服务费用总额。</p>

            <h1>九、 完整协议</h1>
            <p>9.1 本服务条款由本服务条款与本网站公示的各项规则组成，相关名词可互相引用参照，如有不同理解，以本服务条款为准。</p>
            <p>9.2 本服务条款的章节标题仅为行文方便而设，不具有法律或合同效力。</p>
            <p>9.3 您对本服务条款理解和认同，您即对本服务条款所有组成部分的内容理解并认同，一旦您使用本服务，您和微热点(微舆情)即受本服务条款所有组成部分的约束。</p>
            <p>9.4 本服务条款部分内容被有管辖权的法院认定为违法的，不因此影响其他内容的效力。</p>

            <h1>十、商标、知识产权的保护</h1>
            <p>10.1 除第三方产品或服务外，本网站上的架构、页面设计，均由微热点(微舆情)或微热点(微舆情)关联企业依法拥有其知识产权，包括但不限于商标权、专利权、著作权、商业秘密等。</p>
            <p>10.2微热点(微舆情)抓取的舆情内容，都来源于互联网，微热点(微舆情)仅为用户提供个性化信息搜索服务。</p>
            <p>10.2 非经微热点(微舆情)或微热点(微舆情)关联企业书面同意，任何人不得擅自使用、修改、复制、公开传播、改变、散布、发行或公开发表本网站上程序或内容。</p>
            <p>10.3 尊重知识产权是您应尽的义务，如有违反，您应承担损害赔偿责任。</p>

            <h1>十一、通知送达</h1>
            <p>11.1 您理解并同意，微热点(微舆情)可依据自行判断，通过网页公告、电子邮件、手机短信或常规的信件传送等方式向您发出通知，且微热点(微舆情)可以信赖您所提供的联系信息是完整、准确且当前有效的；上述通知于发送之日视为已送达收件人。</p>
            <p>11.2 除非本服务条款另有约定或微热点(微舆情)与您另行签订的协议明确规定了通知方式，您发送给微热点(微舆情)的通知，应当通过微热点(微舆情)对外正式公布的通信地址、传真号码、电子邮件地址等联系信息进行送达。</p>

            <h1>十二、 法律适用与管辖</h1>
            <p>本服务条款之效力、解释、变更、执行与争议解决均适用中华人民共和国法律。因本服务条款产生之争议，均应依照中华人民共和国法律予以处理，并提交上海市浦东新区人民法院审判。</p>

        </div>

			</div>
			<div class="media_footer text-center mb30">
				<button class="btn btn-gray-a btn-half w200 mr30" data-dismiss="modal" >不同意</button>
				<button onclick="goNextRegin();" class="btn btn-orange btn-half w200" data-dismiss="modal" >同意并继续</button>
			</div>
		</div>
		<!--注册页 end-->

    <!-- 新登陆框 start -->
    <div id="loginModal" class="modal modalTwo fade Upgrade b-radius10 border-n login-group" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 760px; margin-left: -380px;height: 495px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			</div>
			<div class="modal-body icheckbox-list text-center clearfix" style="overflow-y:initial;">
				<div class="modal-left pading20" style="min-height: 560px;">
					<div class="text-left">
						<img src="<%=staticResourcePath %>/images/logo.png" width="120px"/>
					</div>
					
					<div class="leftIcon pading-bottom-20">
						<img src="<%=njxBasePath%>/images/newuser/login-left-pic1.png" />
					</div>
					<div class="title">
						<h1 class="fc_dark_grey-900 fz16">新用户注册并登录可免费获得：</h1>
					</div>					
					<ul class="list-group list-group-sm list-group-none text-left fz14 mb20">
						<li class="list-group-item"><i class="icon fc_blue mr5">&#xe725;</i> 一个有效期为7天的监测方案</li>
						<li class="list-group-item"><i class="icon fc_blue mr5">&#xe725;</i>可查看近3天的全网信息</li>
						<li class="list-group-item"><i class="icon fc_blue mr5">&#xe725;</i>随时随地收取实时预警</li>
						<li class="list-group-item"><i class="icon fc_blue mr5">&#xe725;</i>每日9点定时发送日报</li>
					</ul>
					 
				</div>
				<div class="modal-right">
					<div class="modal-rightCon p_h_60" style="height: 495px;">
						 <div class="row">
						 	<div class="col-md-12">
						 		<ul class="tabs-menu" id="myTab">
		  		                	<li id="selectTabID" class="active col-md-6"><a onclick="changeLoginType(1);" href="#accountLogin" data-toggle="tab">帐号登录</a></li>
                                	<li class="col-md-6"><a onclick="changeLoginType(2);" href="#messageLogin" data-toggle="tab">短信登录</a></li>
		  	                    </ul>
		  	                    <div class="tab_box"  id="myTabContent">
		  	                    <FORM method=post name=F id="loginFormID">
		  	                    	<input name="password" id="passwords" type="hidden" autocomplete="off"/>
		  	                   		<input name="imgVcode" id="img_v_code" type="hidden" autocomplete="off"/>
		  	                   		<div class="tab-pane fade in active" id="accountLogin">
								  		<!--帐号密码登录 start-->
										<div class="login">
											
											<div class="phoneLogin">
												<div class="mb10 rel">	
													<input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;ime-mode: disabled;" class="inputText" type="text" id="username" name="username" autocomplete="off" placeholder="请输入手机号" value="">
												</div>
						
												<div class="mb10 rel">	
													<input style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" class="inputText" id="password" type="password" onfocus="this.type='password'" autocomplete="off" placeholder="请输入密码">
												</div>
												<div class="row " style="height:42px">
													<div class="col-md-7 rel">								
														<input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" id="userPCode" class="inputText" type="text" autocomplete="off" placeholder="请输入验证码">
														<input type="hidden" id="_ran" name="_ran" />
													</div>
													<div class="col-md-5 imgVcode">
														<img id="imgVcode" onClick="getRan()" width="110" height="42">
													</div>
												</div>
						
												<div class="mt20 text-center fc_dark_grey"><a href="<%=njxBasePath%>/lostPassword.shtml" class="fc_orange-b ml10"> 忘记密码</a></div>
						
												<div class="btn btn-orange btn-block btn-half mt10" onclick="userLogin()">立即登录</div>
											</div>
											
											<div class="loginBtn mt10">
												<h1><span>使用其他方式登录</span></h1>
												<div class="text-center">
													<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toSinaWeiboLogin.action';" id="wbLoginBtn" class="btn-circle btn-icon50 btn-icon" title="微博登录" style="margin-right: 16px;">
													   <i class="icon">&#xe724;</i>
													</a>
													<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toWeixinLogin.action';" id="wxLoginBtn" class="btn-circle btn-icon50 btn-icon" title="微信登录" style="margin-right: 16px;">
													   <i class="icon">&#xe716;</i>
													</a>
													<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toTencentLogin.action';" id="qqLoginBtn" class="btn-circle btn-icon50 btn-icon" title="QQ登录" style="margin-right: 16px;">
													   <i class="icon">&#xe728;</i>
													</a>
<%-- 													<a href="#" onclick="javascript:location.href='<%=njxBasePath%>/thirdParty/toJCloudLogin.action';" id="jCloudLoginBtn" class="btn-circle btn-icon50 btn-icon" title="京东万象"> --%>
<!-- 													   <i class="icon">&#xe6fd;</i> -->
<!-- 													</a> -->
												</div>
						
											</div>
						                    <div class="mt20 text-center fc_dark_grey">没有帐号？
												<a class="fc_orange-b" href="<%=njxBasePath%>/login.shtml?login=2" >免费注册</a></div>
										</div>
										<!--帐号密码登录 end-->
		  	                      </div>
		  	 
		                          <div class="tab-pane fade" id="messageLogin">
							     	<!--短信登录 start-->
									<div class="login">					
										<div class="phoneLogin">
											<div class="mb20 rel">							
												<input onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" class="inputText" type="text" id="mobile" name="mobile" autocomplete="off" placeholder="请输入手机号" value="">
											</div>
											
											<div class="row  mb20" style="height: 40px;">
												<div class="col-md-7 rel">								
													<input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" id="mobilePCode" class="inputText" type="text" placeholder="请输入验证码">
												</div>
					 							<div class="col-md-5 imgVcode">
													<img id="imgVcodeTwo" onClick="getRan()" width="110" height="42">
												</div>
												
											</div>
											
											<div class="message mb20 rel">
												<input onclick="getMsmCoad();" readonly id="getVCodeSpan" class="btn btn-orange btn-half abs" value="获取短信验证码"/>
												<input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" class="inputText" id="pCode2" name="imgVcode2" type="text" placeholder="请输入短信验证码">
											</div>
											<div class="btn btn-orange btn-block btn-half mt20" onclick="mobileLogin()">立即登录</div>
										</div>
								
										<div class="loginBtn mt10">
											<h1><span>使用其他方式登录</span></h1>
											<div class="text-center">
												<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toSinaWeiboLogin.action';" id="wbLoginBtn" class="btn-circle btn-icon50 btn-icon" title="微博登录" style="margin-right: 16px;">
												   <i class="icon">&#xe724;</i>
												</a>
												<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toWeixinLogin.action';" id="wxLoginBtn" class="btn-circle btn-icon50 btn-icon" title="微信登录" style="margin-right: 16px;">
												   <i class="icon">&#xe716;</i>
												</a>
												<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toTencentLogin.action';" id="qqLoginBtn" class="btn-circle btn-icon50 btn-icon" title="QQ登录" style="margin-right: 16px;">
												   <i class="icon">&#xe728;</i>
												</a>
<%-- 												<a href="#" onclick="javascript:location.href='<%=njxBasePath%>/thirdParty/toJCloudLogin.action';" id="jCloudLoginBtn" class="btn-circle btn-icon50 btn-icon" title="京东万象"> --%>
<!-- 												   <i class="icon">&#xe6fd;</i> -->
<!-- 												</a> -->
											</div>
										</div>
				                    	<div class="mt20 text-center fc_dark_grey">没有帐号？
											<a class="fc_orange-b" href="<%=njxBasePath%>/login.shtml?login=2">免费注册</a></div>
										</div>
										<!--短信登录 end-->
		                          	</div>
		                          </form>
		                        </div>
						 	</div>
						 </div>
					</div>
				</div>
			</div>
				<div id="new_mfzc" class="promptTip" style="left: 318px;top: 44%; display:none" >
	             	
	            </div>
		</div>
    <!-- 新登陆框 end -->
    <input type="hidden" id="loginTypePoint" value="1">

	<script type="text/javascript">
		var mobile_filter  = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
			$(function() {
				
				$("#username").on("click", function() {
					changeLoginType(1);
				});
				$("#password").on("click", function() {
					changeLoginType(1);
				});
				$("#userPCode").on("click", function() {
					changeLoginType(1);
				});
				$("#remember_password").on("click", function() {
					changeLoginType(1);
				});
				$("#userPCode").on("click", function() {
					changeLoginType(1);
				});
// 				$("#imgVcode").on("click", function() {
// 					changeLoginType(1);
// 				});
				
				$("#mobile").on("click", function() {
					changeLoginType(2);
				});
				$("#mobilePCode").on("click", function() {
					changeLoginType(2);
				});
// 				$("#imgVcodeTwo").on("click", function() {
// 					changeLoginType(2);
// 				});
				$("#pCode2").on("click", function() {
					changeLoginType(2);
				});
				var navDropdown = $(".nav-dropdown-canvas.J-nav-wrapper .nav-dropdown-inner");
				var navWrapper = $(".nav-dropdown-canvas.J-nav-wrapper");
				$(navWrapper).css("height", "auto").css("opacity", "1").hide();
				$(navDropdown).hide();
				$(" .head-nav .rightNav li.link").on("mouseenter", function() {
					$(this).parents(".rightNav").find("li").removeClass("hover");
					$(this).parents(".head-nav").find(navDropdown).hide();
					$(this).addClass("hover");

				});

				$(" .head-nav .rightNav li.nav-hover").on("mouseenter", function(e) {
					$(this).parents(".head-nav").find(navWrapper).show(300);
					e.stopPropagation();
				});
				$(navWrapper).on("mouseover", function(e) {
					e.stopPropagation();
				});
				$(".head-nav .rightNav").on("mouseover", function(e) {
					e.stopPropagation();
				});
				$(".head-nav .rightNav li.nav-hover").on("mouseover", function(e) {
					e.stopPropagation();
				});
				$("body").on("mouseover", function() {
					$(".head-nav").find(navWrapper).hide();
					$(".rightNav").find("li").removeClass("hover");
				});

				$(" .head-nav .rightNav li.nav-hover-1").on("mouseenter", function() {
					$(this).parents(".head-nav").find(navDropdown).hide();
					$(this).parents(".head-nav").find(navDropdown).eq(0).css("display", "block");
				});
				$(" .head-nav .rightNav li.nav-hover-2").on("mouseenter", function() {
					$(this).parents(".head-nav").find(navDropdown).hide();
					$(this).parents(".head-nav").find(navDropdown).eq(1).css("display", "block");
				});
				$(" .head-nav .rightNav li.nav-hover-3").on("mouseenter", function() {
					$(this).parents(".head-nav").find(navDropdown).hide();
					$(this).parents(".head-nav").find(navDropdown).eq(2).css("display", "block");
				});
				$(" .head-nav .rightNav li.nav-hover-4").on("mouseenter", function() {
					$(this).parents(".head-nav").find(navDropdown).hide();
					$(this).parents(".head-nav").find(navDropdown).eq(3).css("display", "block");
				});
				$(" .head-nav .rightNav li.nav-hover-5").on("mouseenter", function() {
					$(this).parents(".head-nav").find(navDropdown).hide();
					$(this).parents(".head-nav").find(navDropdown).eq(4).css("display", "block");
				});
				
				 //绑定回车事件
	            $('.modal-right').keydown(function(event) {
	        		if (event.keyCode == 13) {
	        			event.preventDefault();
	        			$(this).change();
	        			var i = $("#loginTypePoint").val();
	        			if(i==1){
	    					userLogin();
	    				}else{
	    					mobileLogin();
	    				}
	        		}
	        	});

			});
			
			function changeLoginType(i){
				$("#new_mfzc").html("");
		    	$("#new_mfzc").hide();
		    	$("#loginTypePoint").val(i);
				if(i==1){
					$("#accountLogin").show();
					$("#messageLogin").hide();
				}else{
					$("#accountLogin").hide();
					$("#messageLogin").show();
				}
			}
			//验证码倒计时
			var countdown = 120;

			function sendemail() {
				var obj = $("#getVCodeSpan");
				settime(obj);
				obj.css("background",'rgb(196, 196, 196)')

			}
			
			function settime(obj) { //发送验证码倒计时
				if(countdown == 0) {
					obj.attr('disabled', false);
					obj.val("免费获取验证码");
					countdown = 120;
					return;
				} else {
					obj.attr('disabled', true);
					obj.val("重新发送(" + countdown + ")");
					countdown--;
				}
				setTimeout(function() {
					settime(obj)
				}, 1000)
			}
			function messageSettime(threeM){
				if(threeM == 0){
					$("#new_mfzc").hide();
					return;
				} else {
					$("#new_mfzc").show();
					threeM--;
				}
				setTimeout(function() {
					messageSettime(threeM);
				}, 1000)
			}
			function getMsmCoad(){
				changeLoginType(2);
				if($("#mobile").val() == '') {
					$("#new_mfzc").html("<span class='errorText'>请输入您的手机号</span>");
					messageSettime(3);
					return false;
				}
				if(!mobile_filter.test($("#mobile").val())) {
					$("#new_mfzc").html("<span class='errorText'>手机号码不正确！</span>");
					messageSettime(3);
					return false;
				}
				
				$.ajax({
					type:"post",
					url:"<%=njxBasePath%>/view/user/sendVcodeSMS.action",
				data:{
					'mobile':$("#mobile").val()
				},
				success: function(data, textStatus){
					$('#new_mfzc').html("");
					if (!$.isEmptyObject(data)) {
                           if (data.succ) {
                           	sendemail();
                           }
						if (!data.succ){
							$("#new_mfzc").html("<span class='errorText'>"+data.msg+"</span>");
							messageSettime(3);
						}
                   	}
					
				},
				error:function(data){}
			});
		}
	</script>
    <!--提示登录框 start-->
    <div id="promptModal" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 350px; margin-left: -175px;">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">&nbsp;</h3>
        </div>
        <div class="modal-body align_c" style="overflow: initial;">
            <p class="fz16">亲爱的微友，您还没有登录哦~</p>
        </div>
        <div class="modal-footer" style="text-align: center;">
            <a class="btn btn-warning"  href="#loginModal" onclick="getRan()" data-dismiss="modal" data-toggle="modal" aria-hidden="true">去登录</a>
        </div>
    </div>
    <!--提示登录框 end-->
    <script type="text/javascript">
	    var loginFlag = true;
	    function userLogin() {
	    	$("#new_mfzc").html("");
	    	$("#new_mfzc").hide();
	        if(!loginFlag){
	            return;
	        }
	        loginFlag = false;
	        var mobile_filter = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
	        var password_filter = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\,\~]{6,20}$/;
	        if($.trim($("#username").val()) == '') {
	            $("#new_mfzc").html("<span class='errorText'>请输入您的手机号！</span>");
	            messageSettime(3);
	            loginFlag = true;
	            return false;
	        }
	        if($.trim($("#password").val()) == '') {
	            $("#new_mfzc").html("<span class='errorText'>请输入您的密码！</span>");
	            messageSettime(3);
	            loginFlag = true;
	            return false;
	        }
	        if($.trim($('#userPCode').val()) == '') {
	            $("#new_mfzc").html("<span class='errorText'>请输入验证码！</span>");
	            messageSettime(3);
	            loginFlag = true;
	            return false;
	        }
	
	        if(!password_filter.test($("#password").val())) {
	            $("#new_mfzc").html("<span class='errorText'>密码格式不正确！</span>");
	            messageSettime(3);
	            loginFlag = true;
	            return false;
	        }
	
// 	        SetCookie("username", $("#username").val());
// 	        if($("#remember_password").get(0).checked){
// 	            SetCookie("password", $("#password").val());
// 	            SetCookie("remember_password",true);
// 	        }else{
// 	            SetCookie("password", "");
// 	            SetCookie("remember_password",false);
// 	        }
	        if($("#password").val()!=''){
	            $("#passwords").val(hex_md5($("#password").val()));
	        }
	
	        
	        $("#img_v_code").val($("#userPCode").val());
			$.ajax({
	            url : "<%=njxBasePath%>/userLogin.action" , 
	            type : "post",
	            dataType : "json",
	//             data: $('#loginFormID').serialize(),
				data:{
					"imgVcode":$("#img_v_code").val(),
					"username":$("#username").val(),
					"password":$("#passwords").val(),
					"_ran":$("#_ran").val(),
					"firstLoginSign":$("#firstLoginSign").val()
				},
	            success : function(data) {
	            	if(data.code=="0000"){
	            		window.location.href="<%=njxBasePath%>/shouye.action?firstLoginSign="+data.firstLoginSign;
	            	} else{
	        	            $("#new_mfzc").html("<span class='errorText'>"+data.msg+"</span>");
	        	            messageSettime(3);
	        	            loginFlag = true;
	        	            getRan();
	            	}
	            }
	        })
	    }
        var loginFlag2 = true;
        function mobileLogin() {
        	$("#new_mfzc").html("");
        	$("#new_mfzc").show();
        	changeLoginType(2);
            if(!loginFlag2){
                return;
            }
            loginFlag2 = false;
            var mobile_filter = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
            if($.trim($("#mobile").val()) == '') {
                $("#new_mfzc").html("<span class='errorText'>请输入您的手机号</span>");
                messageSettime(3);
                loginFlag2 = true;
                return false;
            }
            if($.trim($('#pCode2').val()) == '') {
                $("#new_mfzc").html("<span class='errorText'>请输入验证码！</span>");
                messageSettime(3);
                loginFlag2 = true;
                return false;
            }
            if($.trim($('#mobilePCode').val()) == '') {
                $("#new_mfzc").html("<span class='errorText'>请输入验证码！</span>");
                messageSettime(3);
                loginFlag2 = true;
                return false;
            }
            SetCookie("username", $("#mobile").val());
            
            $("#img_v_code").val($("#mobilePCode").val());

			$.ajax({
	            url : "<%=njxBasePath%>/indexLogin2.action" , 
	            type : "post",
	            dataType : "json",
// 	            data: $('#loginFormID').serialize(),
				data:{
					"_ran":$("#_ran").val(),
					"imgVcode2":$("#pCode2").val(),
					"mobile":$("#mobile").val(),
					"imgVcode":$("#img_v_code").val(),
					"firstLoginSign":$("#firstLoginSign").val()
				},
	            success : function(data) {
	            	if(data.code=="0000"){
	            		window.location.href="<%=njxBasePath%>/shouye.action?firstLoginSign="+data.firstLoginSign;
	            	}else{
	            		getRan();
        	            $("#new_mfzc").html("<span class='errorText'>"+data.msg+"</span>");
        	            messageSettime(3);
        	            loginFlag2 = true;
	            	}
	            }
	        })
        }

        var geeTestData;
        $(function() {
            /*$.ajax({
                url : "<%=njxBasePath%>/StartCaptchaServlet?t=" + (new Date()).getTime(), // 加随机数防止缓存
                type : "get",
                dataType : "json",
                success : function(data) {
                    // 使用initGeetest接口
                    // 参数1：配置参数
                    // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
                    initGeetest({
                        gt : data.gt,
                        challenge : data.challenge,
                        product : "float",
                        offline : !data.success
                    }, handlerEmbed);
                }
            })
            var handlerEmbed = function(captchaObj) {
                // 将验证码加到id为captcha的元素里
                captchaObj.appendTo("#float-captcha");
                captchaObj.onReady(function() {
                    $("#wait").hide();
                });
                captchaObj.onSuccess(function(data) {
                    var validate = captchaObj.getValidate();
                    if (!validate) {
                        return;
                    }
                    $.ajax({
                        url: "<%=njxBasePath%>/VerifyLoginServlet?t=" + (new Date()).getTime(), // 进行二次验证
                        type: "post",
                        dataType: "json",
                        data: {
                            // 二次验证所需的三个值
                            geetest_challenge: validate.geetest_challenge,
                            geetest_validate: validate.geetest_validate,
                            geetest_seccode: validate.geetest_seccode
                        },
                        success: function (data) {
                            geeTestData = data;
                            userLogin();//验证完成直接登录 by lcz
                        }
                    });
                });
            };*/

            $('#loginBtn').click(function() {
            	var i = $("#selectLoginTypeID").val();
				if(i==1){
					//手机号快捷登录
					mobileLogin();
				}else{
					//用户名密码登陆
	                userLogin();
				}
            });
               
            $('#loginModal').keydown(function(event) {
        		if (event.keyCode == 13) {
        			event.preventDefault();
        			$("#loginBtn").click();
        		}
        	});
        });

		// 滚动
	    $(window).scroll(function () {
	        if ($(document).scrollTop() > 57) {
	            $('#head.head-croll').removeClass('head-bg')
	        } else {
	        	 $('#head.head-croll').addClass('head-bg')
	        }
	    })
    </script>
    
    <!--关于我们 end-->
    <script type="text/javascript">
        var operateLogPageCode = '1001';
        var operateLogPageName = '登录';
        var operateLogRequestPath = '<%=request.getContextPath() %>';
    </script>

</s:else>
<script type="text/javascript">

    $(function(){
        $(".jcfaNav li").mouseover(function(){
            $(this).find(".dele").show();
        });
        $(".jcfaNav li").mouseout(function(){
            $(this).find(".dele").hide();
        });
        var i = "${selectLoginType}";
		if(i==1){
			$("#messageLoginId").show();
			$("#miMaLoginID").hide();
		}else{
			$("#messageLoginId").hide();
			$("#miMaLoginID").show();
		}
    });
    
    function getKeywordProject(){
    	$.ajax({
    		url : actionBase + '/view/user/getKeywordProject.action',
    		type : 'POST',
    		success : function(result) {
    			if(null!=result && result.code=="0000"){
    				$("#goSetKeyword").modal();
    			}else{
    				showMsgInfo(0, result.message,0);
    			}
    		}
    	});
    }
    
    function goEndActive(){
    	window.open("<%=njxBasePath%>/goActivePay.shtml", "_blank");
    }
    
    function goSetKeyword(){
    	window.location = "<%=njxBasePath%>/keyword.shtml";
    }
    
    function goNextRegin(){
    	window.location = "<%=njxBasePath%>/user/newGoRegister.shtml";
    }
    
    function goActivePay(){
		var href='<%=njxBasePath%>/goActivePay.shtml';
        window.open(href,"_blank");
	}
	
    function isMobile(){
        var bs = {
            versions: function () {
                var u = navigator.userAgent, app = navigator.appVersion;
                return {
                    windowsPhone: u.indexOf('IEMobile') > -1,
                    trident: u.indexOf('Trident') > -1,
                    presto: u.indexOf('Presto') > -1,
                    webKit: u.indexOf('AppleWebKit') > -1,
                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') === -1,
                    mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/) || !!u.match(/IEMobile/),
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
                    android: u.indexOf('Android') > -1 || u.indexOf('UCBrowser') > -1,
                    iPhone: u.indexOf('iPhone') > -1,
                    iPad: u.indexOf('iPad') > -1,
                    webApp: u.indexOf('Safari') === -1
                };
            } (),
            language: (navigator.browserLanguage || navigator.language).toLowerCase()
        };
        if (bs.versions.mobile) {
            if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.windowsPhone) {
                //$('.fenxiang').css('display', 'none');
                //$('#weibo_task_result_star_content_div,#weibo_task_result_line_content_div,#weibo_task_result_fans_div').css('height', '220px');
                location.href="onclick","http://m.wrd.cn/register.action";
            }
        }else{
            location.href="<%=njxBasePath%>/user/goRegister.shtml";
        }
    }
    function loadingMoreFun(){
		if("${admin}" == ''){
			$("#loginModal").modal();
		}else{
			for(var i=10; i<=19; i++){
				$(".ma-li-" + i).show();
			}
			$("#loadingMoreTrId").hide();
		}
	}


</script>
<script>
    // 删除按钮
    $('.activity-close').on('click',function () {
        $(this).parent().hide();
    });
    //倒计时
    $('#countdownTime').countdown('2019/4/24 18:25:00', function(event) {
        var $this = $(this).html(event.strftime(''
            + '<span>%H</span>：'
            + '<span>%M</span>：'
            + '<span>%S</span>'));
    }).on('finish.countdown', function(){
        // $('.activityDis').hide();
        $('#example2').modal('hide')
    });
    function Tips(text){
        var element = document.createElement("div");
        var span = document.createElement("span");
        span.innerHTML = text;
        element.appendChild(span);
        element.setAttribute("class", "wrd-tips");
        $(document.body).append(element);
        setTimeout(function () {
            element.remove()
        },1200)
    }
    <s:if test="#attr.admin!=null">
    $(function () {
        $.ajax({
            type: "post",
            url: actionBase+'/view/home/hotEvent/selectCoupons.action',
            data: {'nearGet': 1,
                'nearDue': 1,
                'couponType': 1,
                'couponUse': 2,
                'page': 1},
            success: function (res) {
                var x;
                var y=res.data.coupons;
              for(x in y){
                  if (y[x].discountCreditCount===500){
                      $("#500").attr("ind",2);
                      $("#500").addClass("coupon-btn-red").text("去使用");
				  }if (y[x].discountCreditCount===300){
                      $("#300").attr("ind",2);
                      $("#300").addClass("coupon-btn-red").text("去使用");
				  }if (y[x].discountCreditCount===2000){
                      $("#2000").attr("ind",2);
                      $("#2000").addClass("coupon-btn-red").text("去使用");
				  }if (y[x].discountCreditCount===6000){
                      $("#6000").attr("ind",2);
                      $("#6000").addClass("coupon-btn-red").text("去使用");
				  } if (y[x].discountCreditCount===9000){
                      $("#9000").attr("ind",2);
                      $("#9000").addClass("coupon-btn-red").text("去使用");
				  } if (y[x].discountCreditCount===10000){
                      $("#10000").attr("ind",2);
                      $("#10000").addClass("coupon-btn-red").text("去使用");
				  }
			  }
            },
            error: function () {
                Tips("请求失败！");
            }
        })
    })
    </s:if>
   $(".coupon-price a").on('click',function () {
       <s:if test="#attr.admin==null">
       Tips("您还没有登陆，请先登录再领取奖励！")
       </s:if>
       <s:else>
       var v=$(this).attr("ind");
       var id=$(this).attr("id");
       var con_id=$(this).attr("con_id");
       if(v==1){
           $.ajax({
               type: "post",
               url: actionBase+'/view/home/hotEvent/getCoupon.action',
               data: {"id":con_id},
               success: function (res) {
                   if(res.code==="0000"){
                       Tips("领取成功！");
                   }if(res.code==="30025"){
                       Tips("不能重复领取优惠券！");
                   }
                   if(id==="500"){
                       $("#500").addClass("coupon-btn-red").text("去使用");
                       $("#500").attr("ind",2);
                   }if(id==="300"){
                       $("#300").addClass("coupon-btn-red").text("去使用");
                       $("#300").attr("ind",2);
                   }if(id==="2000"){
                       $("#2000").addClass("coupon-btn-red").text("去使用");
                       $("#2000").attr("ind",2);
                   }if(id==="6000"){
                       $("#6000").addClass("coupon-btn-red").text("去使用");
                       $("#6000").attr("ind",2);
                   }if(id==="9000"){
                       $("#9000").addClass("coupon-btn-red").text("去使用");
                       v=2;
                   }if(id==="10000"){
                       $("#10000").addClass("coupon-btn-red").text("去使用");
                       $("#10000").attr("ind",2);
                   }
               },
               error: function () {
                   Tips("请求失败！");
               }
           })
       }else {
           if(id==="500"){
               window.location.href=actionBase +"/pay/buy.shtml?type=2";
           }if(id==="300"){
               window.location.href=actionBase+"/home.shtml"
           }if(id==="2000"){
               window.location.href=actionBase+"/home.shtml"
           }if(id==="6000"){
               window.location.href=actionBase+"/home.shtml"
           }if(id==="9000"){
               window.location.href=actionBase+"/home.shtml"
           }if(id==="10000"){
               window.location.href=actionBase+"/home.shtml"
           }
       }

       </s:else>
   })

</script>