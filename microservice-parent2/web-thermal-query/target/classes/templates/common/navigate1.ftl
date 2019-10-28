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

<!--�-->
<div class="activityDis">
	<a class="activity-img" data-target="#example2" data-toggle="modal">
		<img src="<%=staticResourcePath%>/css/indexV4/base/images/activity_dis_img.png" alt="">
	</a>
	<span class="activity-close"><i class="icon icon-guanbi"></i></span>
</div>
<!--�����-->
<div class="modal-default modal-circle fade Discount" id="example2" tabindex="-1" role="dialog" aria-labelledby="example1">
	<div class="modal-dialog" role="document" style="width: 723px;height: 464px">
		<div class="modal-content">
			<a href="javascript:;" class="default-close" data-dismiss="modal" aria-label="Close">��</a>
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
								<a href="#dispage1" data-toggle="tab">�����Żݾ�</a>
							</li>
							<li>
								<a href="#dispage2" data-toggle="tab">�ȶ�ָ���ۿ�ȯ</a>
							</li>
							<!--<li>
								<a href="#dispage3" data-toggle="tab">���Ÿ��ۿ�ȯ</a>
							</li>-->
							<li>
								<a href="#dispage4" data-toggle="tab">��Ϣ����ۿ�ȯ</a>
							</li>
						</ul>
					</div>
					<div class="tab-content padding-vertical-15 padding-horizontal-40">
						<div id="dispage1" class="tab-pane fade active in">
							<div class="padding-vertical-50">
								<div class="coupon block">
									<p class="coupon-title">΢���ֵ���ȯ</p>
									<p>�������������</p>
									<p>��ȡ��7������Ч</p>
									<div class="coupon-price">
										<span class="coupon-num">500</span>
										<a href="javascript:;" id="500" ind="1" class="coupon-btn" con_id="70">������ȡ</a>
									</div>
								</div>
							</div>
							<div class="text-center">
								<a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">�ݲ���ȡ</a>
							</div>
						</div>
						<div id="dispage2" class="tab-pane fade">
							<div class="row clearfix">
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">΢���ֵ���ȯ</p>
										<p>��900΢���ֿ���</p>
										<p>��ȡ��30������Ч</p>
										<div class="coupon-price">
											<span class="coupon-num">300</span>
											<a href="javascript:void(0);" id="300" ind="1" class="coupon-btn"  con_id="71">������ȡ</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">΢���ֵ���ȯ</p>
										<p>��5000΢���ֿ���</p>
										<p>��ȡ��30������Ч</p>
										<div class="coupon-price">
											<span class="coupon-num">2000</span>
											<a href="javascript:void(0);" id="2000" ind="1" class="coupon-btn"  con_id="72">������ȡ</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">΢���ֵ���ȯ</p>
										<p>��14000΢���ֿ���</p>
										<p>��ȡ��30������Ч</p>
										<div class="coupon-price">
											<span class="coupon-num">6000</span>
											<a href="javascript:void(0);" id="6000" ind="1" class="coupon-btn"  con_id="73">������ȡ</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">΢���ֵ���ȯ</p>
										<p>��25000΢���ֿ���</p>
										<p>��ȡ��30������Ч</p>
										<div class="coupon-price">
											<span class="coupon-num">9000</span>
											<a href="javascript:void(0);" id="9000" ind="1" class="coupon-btn"  con_id="74">������ȡ</a>
										</div>
									</div>
								</div>
							</div>
							<div class="text-center">
								<a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">�ݲ���ȡ</a>
							</div>
						</div>
						<!--<div id="dispage3" class="tab-pane fade">
							<div class="row clearfix">
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">�������ȯ</p>
										<p>������ѷ�������</p>
										<p>��ȡ��3������Ч</p>
										<div class="coupon-price">
											<span class="coupon-num">3��</span>
											<a href="javascript:void(0);" class="coupon-btn-red">ȥʹ��</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">���ȯ</p>
										<p>2000΢����</p>
										<p>��ȡ��30������Ч</p>
										<div class="coupon-price">
											<span class="coupon-num">1��</span>
											<a href="javascript:void(0);" class="coupon-btn">������ȡ</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">���ȯ</p>
										<p>10000΢����</p>
										<p>��ȡ��������Ч</p>
										<div class="coupon-price">
											<span class="coupon-num">3��</span>
											<a href="javascript:void(0);" class="coupon-btn">������ȡ</a>
										</div>
									</div>
								</div>
								<div class="col-w-5">
									<div class="coupon block">
										<p class="coupon-title">���ȯ</p>
										<p>10000΢����</p>
										<p>��ȡ��������Ч</p>
										<div class="coupon-price">
											<span class="coupon-num">5��</span>
											<a href="javascript:void(0);" class="coupon-btn">������ȡ</a>
										</div>
									</div>
								</div>
							</div>
							<div class="text-center">
								<a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">�ݲ���ȡ</a>
							</div>
						</div>-->
						<div id="dispage4" class="tab-pane fade">
							<div class="padding-vertical-50">
								<div class="coupon block">
									<p class="coupon-title">΢���ֵ���ȯ</p>
									<p>��ȡ��30������Ч</p>
									<div class="coupon-price">
										<span class="coupon-num">10000</span>
										<a href="javascript:void(0);" id="10000" ind="1" class="coupon-btn"  con_id="75">������ȡ</a>
									</div>
								</div>
							</div>
							<div class="text-center">
								<a href="javascript:;" class="notCollect" data-dismiss="modal" aria-label="Close">�ݲ���ȡ</a>
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
            //�������
            $(".rank_hover").stop().slideDown(400);
        });
        $('.rank-menu').on('mouseleave', function () {
            //�������
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
	//��ȡurl�еĲ���
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); // ƥ��Ŀ�����
		var result = window.location.search.substr(1).match(reg); // ��querystringƥ��Ŀ�����
		if (result != null) {
			return decodeURIComponent(result[2]);
		} else {
			return null;
		}
	}
	
	//��¼��ת
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

	//��¼�Զ���ת��Ʒҳ
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
    


  //��֤���Ժ���+��λ�������ΪΨһƾ֤
  function getRequestRan() {
  	// �����ҳ��sessionʧЧ��������ҳ�����¼���
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
                $('.open1').stop().show();   //��ʾ
            });
            $('.Oper1b').click(function(){
                $('.fold1').stop().show();
                $('.open1').stop().hide();   //����
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
        		}, 5000);	//�ӳ�3�� ��ֹҳ�������ɺ�ĵ���¼�Ӱ�� */
                  
           
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
		
        //�û���һ�ε�¼�Զ���������
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


        // ����
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
        <!--ѡ��������ʾ��ťЧ�� end-->


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
        		//ÿ���л�չʾ��ʱ����ȥ�����б�
        		taskListStatus();
        		$("#gg").removeClass("current");
            	$("#xx").addClass("current");
            	$("#gg_tab").addClass("hide");
            	$("#xx_tab").removeClass("hide");
        	}
        }

        $(function(){
        	//��ʼ�����ص�����Ϣ
        	getMessagePopList();
        	//��ʱ���ص�����Ϣ
        	var timer = setInterval(function(){
        		getMessagePopList();
        		
        		if(messagePopList.length>0){
        			takeMessagePopList();
        		}
        	}, 120000);
        });
        
        $(function(){
        	//��Ϣ���� ��ʾ/����
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
        //�û�ʣ��Ȩ����ʾ����
           $(".UserPrompt .dl_list").each(function(i){
        	   $("#dl_list"+(i)+">dd:gt(3)").hide();
        	});
        	$(".bottomMore").on("click",function(){
        		if(!$(this).find("i").hasClass("rotate180")){
        			$(this).parents(".UserPrompt .dl_list").find(">dd:gt(3)").show(300);
        			 $(this).html("���� <i class='icon-angle-up rotate180'></i>");
        		}else{
        			$(this).parents(".UserPrompt .dl_list").find(">dd:gt(3)").hide(300);
        			$(this).html("���� <i class='icon-angle-down rotate0'></i>");
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
        					result.productPackage.packageName = result.productPackage.packageName.replace('A', '��A��');
        					result.productPackage.packageName = result.productPackage.packageName.replace('B', '��B��');
        					result.productPackage.packageName = result.productPackage.packageName.replace('C', '��C��');
        					result.productPackage.packageName = result.productPackage.packageName.replace('D', '��D��');
        					var now = new Date();
        					var proValidEndDate = new Date('${admin.proUserValidEndTime}');
        					if (proValidEndDate > now) {
        						$('#proDl dd').html('����' + result.productPackage.packageName + '���뵽�ڻ���<font class="fc_red fz20">' + parseInt((proValidEndDate - now) / 1000 / 60 / 60 / 24) + '</font>��');
        					} else {
        						$('#proDl dd').html('����' + result.productPackage.packageName + '�ѹ���');
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
        

        //���� �����backspace������ǰһҳ�Ĺ���
        function banBackSpace(e){
        	var ev = e || window.event;
        	//����������»�ȡ�¼�����
        	var obj = ev.relatedTarget || ev.srcElement || ev.target ||ev.currentTarget;
        	//����Backspace��
        	if(ev.keyCode == 8){
        		var tagName = obj.nodeName; //��ǩ����
        		//�����ǩ����input����textarea����ֹBackspace
        		if(tagName!='INPUT' && tagName!='TEXTAREA'){
        			return stopIt(ev);
        		}
        		var tagType = obj.type.toUpperCase();//��ǩ����
        		//input��ǩ�������漸�����ͣ�ȫ����ֹBackspace
        		if(tagName=='INPUT' && (tagType!='TEXT' && tagType!='TEXTAREA' && tagType!='PASSWORD')){
        			return stopIt(ev);
        		}
        		//input����textarea�����������ɱ༭����ֹBackspace
        		if((tagName=='INPUT' || tagName=='TEXTAREA') && (obj.readOnly==true || obj.disabled ==true)){
        			return stopIt(ev);
        		}
        	}
        }

        function stopIt(ev){
        	if(ev.preventDefault ){
        		//preventDefault()������ֹԪ�ط���Ĭ�ϵ���Ϊ
        		ev.preventDefault();
        	}
        	if(ev.returnValue){
        		//IE���������window.event.returnValue = false;ʵ����ֹԪ�ط���Ĭ�ϵ���Ϊ
        		ev.returnValue = false;
        	}
        	return false;
        }
        
        $(function(){
        	//ʵ�ֶ��ַ���Ľػ�keypress����������Щ���ܰ���
        	document.onkeypress = banBackSpace;
        	//�Թ��ܰ����Ļ�ȡ
        	document.onkeydown = banBackSpace;
        	
        })
    </script>
    <!-- �°湺�� -->
<!-- ���е�¼����start -->
<link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/notice.css?v=<%=SystemConfig.SYSTEMINITTIME %>"/>
<a href="javascript:" style="display: none;" id="showNoticeModalBtn" data-toggle="modal" data-target="#noticeModal"></a>
<s:if test="firstLoginSign == 4">
	<!-- ��̨���Ͷһ�ȯstart -->
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
										��<s:number name="#item.coupon.discountAmount" maximumFractionDigits="0" />
										</span>
										<div class="redPacket-des">
											<p class="fz18 fw600">${item.coupon.couponName }</p>
											<p class="fz14 mb5">��${item.coupon.minSpentAmount }����</p>
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
											<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
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
											<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
	                             			<p class="fc666 fz12" style="font-size: 10px !important;">���޼�ⷽ������ʹ��</p>
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
												<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">����ȫ���¼���������ʹ��</p>
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
												<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">����΢���¼���������ʹ��</p>
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
												<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">���޾�Ʒ��������ʹ��</p>
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
											<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
		                             		<p class="fc666 fz12" style="font-size: 10px !important;">����΢������Ч����������ʹ��</p>
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
												<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">�������۷�������ʹ��</p>
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
												<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
			                             	<p class="fc666 fz12" style="font-size: 10px !important;">�������ݵ�������ʹ��</p>
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
												<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">�����ȶ�ָ������ʹ��</p>
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
												<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">���޷�Ʊ����ȯ����ʹ��</p>
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
												<p class="fz14 mb5">��${item.coupon.minSpentCreditCount }����</p>
		                             			<p class="fc666 fz12" style="font-size: 10px !important;">���޼򱨹���ʹ��</p>
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
											<p class="fc666 fz12">${item.coupon.validDays }���ʧЧ</p>
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
	<!-- ��̨���Ͷһ�ȯend -->
</s:if>
<s:elseif test="firstLoginSign == 3">
<script src="<%=njxBasePath%>/js/iscroll.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
	<!-- �����start -->
	<div id="noticeModal" class="actSign fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 768px; left: 0;right: 0;margin: auto;">
		<div class="modal-header" style="border-bottom-width: 0px;">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" >��</button>
		</div>
		<div class="modal-body icheckbox-list" style="overflow-y:initial;">
			<div class="modal-con">
				<img style="width: 596px;" src="<%=staticResourcePath%>/images/icon_bg01.png" />
				<a href="<%=njxBasePath%>/usercenter/account.shtml" target="_Blank" class="btn-receive" style="padding-left: 20px;">�����츣��<i class="jiantou"></i></a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!-- �����end -->
	
</s:elseif>
<s:elseif test="firstLoginSign == 1">
<script src="<%=njxBasePath%>/js/iscroll.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
	<!-- ����start -->
	<div id="noticeModal" class="modal modalTwo fade Upgrade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 768px; left: 0;right: 0;margin: auto;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">��</button>
		</div>
		<div class="modal-body icheckbox-list" style="overflow-y:initial;">
			<s:if test="#attr.noticeList != null && #attr.noticeList.size() > 0">
				<div class="modal-title">
					<img class="model-tit-bg" src="<%=staticResourcePath%>/images/model-top.png" />
					<div class="title">
						<h3>${noticeList[0].noticeTitle}</h3>
						<p>����΢�ȵ�(΢����) <s:date name="#attr.noticeList.get(0).noticeSendTime" format="yyyy-MM-dd HH:mm:ss"/></p>
					</div>
				</div>
				<div class="modal-con">
					<div>
						${noticeList[0].noticeContent}
					</div>
					
				</div>
			</s:if>
			<div class="modal-footer" style="text-align: center;">
				<a class="" data-dismiss="modal" aria-hidden="true" style="cursor:pointer;">��֪����</a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!-- ����end -->
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
<!-- ��Ʒ������ -->
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
                <p>�װ��Ļ�Ա:</p>
                <p>Ϊ�˸�л����΢�����֧��,������һ��΢������Ʒ��~</p>
            </div>
            <div class="rel" style="height: 240px">
                <div class="card_logo">
                    <img src="<%=staticResourcePath %>/images/logo-w.png" alt="">
                </div>
                <div class="card_number">
                    <span id="queryCardNo1"></span><span id="queryCardNo2"></span><span id="queryCardNo3"></span><span id="queryCardNo4"></span>
                </div>
                <div class="">
                    <p class="card_value" style="color: #ffffff;"><span id="queryCardMony"></span> <span class="fz12">΢����</span></p>
                    <p class="card_data" style="color: #ffffff;">�һ�����<span id="queryCardTime"></span></p>
                </div>
            </div>

        </div>
        <div class="modal-footer pading-top-30 pading-bottom-5" style="text-align: center;">
            <p style="color: #ffffff;" class="fz12 pading-bottom-10">���ͷ��ǰ����������-�Ż�����-��Ʒ�����һ�����ʹ��</p>
            <button style="cursor: pointer;" class="btn_card_bg" data-dismiss="modal" aria-hidden="true" onclick="goGiftCard();">ǰ���һ�</button>
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
	        <h4 class="modal-title">΢�ȵ㣨΢���飩</h4>
	   </div>
		<div class="remind-body">
			<div class="remind-lf">
				<p class="fz16">����һ�ݽ�������ȡŶ</p>
				<a style="cursor: pointer;" onclick="goActivePay();" class="btn-remind">������ȡ</a>
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
<!-- ���е�¼����end -->

	
<!-- ���½���Ϣ���ѵ���start -->
<%-- <div class="remind fadeInUpBig animated">
	 <div>
		<div class="remind-header">
	        <a href="javascript:;" type="button" class="close remind-close icon-guanbi"></a>
	        <h4 class="modal-title">΢�ȵ�</h4>
	   </div>
		<div class="remind-body">
			<div class="remind-lf">
				<p class="fz16">����һ�ݽ�������ȡŶ</p>
				<a href="javascript:;" class="btn-remind">������ȡ</a>
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
<!-- ���½���Ϣ���ѵ���end -->
	
<!-- ���û���ȡ��������start -->
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
	<!--��ȡ���� start-->
		<div id="getKeywordProject" class="modal modalTwo fade Upgrade b-radius10 border-n" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 600px; margin-left: -300px;">
			<div class="modal-header">
				 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">��</button>
			</div>
			<div class="modal-body icheckbox-list text-center" style="">
				<div class="modal-title bg_white">
					<img class="mt30"  src="<%=njxBasePath %>/images/newuser/model-icon-1.png" />					
				</div>
				<div class="modal-con p_h_40 bg_white">
					<div class="pading-top-30">
						<strong class="fz18">Hi �װ���΢��</strong>
						<p class="mt20">��ϲ�������΢�ȵ�(΢����)�ٷ����͵���Ѽ�ⷽ��һ������Ч��Ϊ7�죩<br> ��������ѵ�Ԥ�����ձ��ȷ���Ŷ����ȥ��ȡ��~ </p>
					</div>					
				</div>
				<div class="modal-footer pading-bottom-30 bg_white" style="text-align: center;">
					<button onclick="getKeywordProject();" class="btn btn-orange btn-half" data-dismiss="modal" style="width: 160px;">������ȡ</button><br>	
					<button class="btn btn-link fc_orange-b" data-dismiss="modal" style="width: 160px;background-color: unset !important;">�´���ȡ��~</button>
				</div>
			</div>
		</div>
		<!--��ȡ���� end-->
		<!--ȥ���÷��� start-->
		<div id="goSetKeyword" class="modal modalTwo fade Upgrade b-radius10 border-n" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 600px; margin-left: -300px;">
			<div class="modal-header">
				 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">��</button>
			</div>
			<div class="modal-body icheckbox-list text-center" style="overflow-y:initial;">
				<div class="modal-title bg_white">
					<img class="mt30"  src="<%=njxBasePath %>/images/newuser/model-icon-1.png" />					
				</div>
				<div class="modal-con p_h_40 bg_white">
					<div class="pading-top-30">
						<strong class="fz18">Hi �װ���΢�ѣ���ϲ����ȡ�ɹ���</strong>
						<p class="mt20">���С΢һ��ȥ�������֪������Ϣ�ɣ�������°�ť��ʼ����~</p>
					</div>					
				</div>
				<div class="modal-footer pading-bottom-30 bg_white" style="text-align: center;">
					<button onclick="goSetKeyword();" class="btn btn-orange btn-half" data-dismiss="modal" style="width: 160px;">ȥ���÷���</button><br>	
					<button class="btn btn-link fc_orange-b" data-dismiss="modal" style="width: 160px;">�´����ð�~</button>
				</div>
			</div>
 
		</div>
		<!--ȥ���÷��� end-->
</s:if>
<!-- ���û���ȡ��������end -->

 <!-- ���ڲ鿴�¼����� -->
<form target="_blank" method="post" action="" id="messageForm"></form>
	<!--ʵʱ��Ϣ���� start-->
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
		<a class="close" id="closeNotice" href="#" style="background: none;">��</a>
		<div>
			<div class="nTit">
				<h1>
					<i class="icon-shengyin fz18"></i>ϵͳ��Ϣ
				</h1>
			</div>
			<div class="noticetab">
				<ul class="tab_menu">
					<li id="gg" onclick="changetab('1');" class="current">����</li>
					<li id="xx" onclick="changetab('2');">��Ϣ</li>
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
							<!--��Ϣ���� start-->
							<ul>
								<li id="waitCheckMessage"></li>
								<li id="checkedMessage"></li>
							</ul>
							<!--��Ϣ���� end-->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--ϵͳ���� end-->

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
    <!--head���� ��½�� start-->
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
                    <a href="javascript:void(0);" id="noticeLink">ϵͳ����</a>
                </div>
                <div class="w-heabar-menu-item <s:if test='currentPage=="productsBuy"'>active</s:if>">
                    <a href="<%=njxBasePath%>/pay/buy.shtml">��Ʒѡ��</a>
                </div>
                <div class="w-heabar-menu-item <s:if test='currentPage=="novice"'>active</s:if>">
                    <a href="<%=njxBasePath%>/novice.shtml">����ר��</a>
                </div>
                <div class="w-heabar-menu-item <s:if test='currentPage=="bill"'>active</s:if>">
                    <a href="<%=njxBasePath%>/bill/order.shtml">��ȡ��Ʊ</a>
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
							      	        	<span class="float_l">���ã�<s:if test="admin.nickname==null||admin.nickname==''">${admin.username} </s:if><s:else><s:property value="#attr.admin.nickname" escape="true"/></s:else></span>
				      							<span class="float_r"><a href="javascript:" class="fc_gray2" onclick="logout();">���˳���</a></span>
							      	        </dd>
						                </dl>
						                <dl class="mt10">
									    	<dd>
									      		<span>
									      			<img src="<%=staticResourcePath %>/images/v-icon.png"/>
									      			<font class="fc_red" id="userHaveCreditNum">${admin.creditAmount }</font> ΢����
									      		</span>
									      		<span class="float_r">
									      			<button class="btn btn-danger btn-half" onclick="location.href = actionBase + '/pay/buy.shtml';">����</button>
									      		</span>
									    	</dd>
									    </dl>
									    <dl class="mt10 <s:if test='admin.isProUser==1'>pro-y</s:if>" id="proDl" style="display: none;">
					      					<dd>
					      					</dd>
					      				</dl>
					      				<dl id="proDlBtn" style="display: none;">
					      					<dd>
					      						<button class="btn btn-danger btn-half btn-big btn-block">ȥ����</button>
					      					</dd>
					      				</dl>
					      				<dl class="dl_list rel" id="dl_list0" style="display: none;">
									      	<dd class="abs" style="bottom: 10px; right: 0;">
									      		<a class="link_yellowHover bottomMore" href="javascript:">���� <i class="icon-angle-down"></i></a>
									      	</dd>
									      	<dd>
									      		<h1><strong>�û�ʣ��Ȩ�棺</strong></h1>
									      	</dd>
									      	<c:if test="${admin.noUseKeywordCount > 0}">
									      		<dd>��ⷽ��<font class="fc_red"> X ${admin.noUseKeywordCount }</font></dd>
									      	</c:if>
									      	<c:if test="${admin.userAnalysisValidCount > 0 }">
									      		<dd>ȫ���¼�����<font class="fc_red"> X ${admin.userAnalysisValidCount }</font></dd>
									      	</c:if>
									      	<c:if test="${admin.userWeiboAnalysisValidCount > 0 }">
									      		<dd>΢���¼�����<font class="fc_red"> X ${admin.userWeiboAnalysisValidCount }</font></dd>
									      	</c:if>
									      	<c:if test="${admin.userBriefValidCount > 0 }">
									      		<dd>������<font class="fc_red"> X ${admin.userBriefValidCount }</font></dd>
									      	</c:if>
									      	<c:if test="${admin.userProductAnalysisValidCount > 0 }">
									    		<dd>��Ʒ����<font class="fc_red"> X ${admin.userProductAnalysisValidCount }</font></dd>
									    	</c:if>
									    	<c:if test="${admin.userSingleWeiboAnalysisValidCount > 0 }">
									    		<dd>΢������Ч������<font class="fc_red"> X ${admin.userSingleWeiboAnalysisValidCount }</font></dd>
									    	</c:if>
									    	<c:if test="${admin.exportDataCount > 0 }">
									    		<dd>��������<font class="fc_red"> X ${admin.exportDataCount }</font></dd>
									    	</c:if>
									    </dl>
                                	</div>
                                </div>
                </div>

            </div>

        </div>

    </div>
<!--     ��¼��� -->
    <div class="w-headbar-bottom">
        <div class="w-headbar-nav">
            <ul class="w-headbar-list clearfix">
                <li <s:if test='currentPage=="hot"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/home.shtml">�ȵ㷢��</a>
                </li>
                <li <s:if test='currentPage=="caseAnalysis"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/caseAnalysis.shtml">������</a>
                </li>
                <li  <s:if test='currentPage=="bigDataRead"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/infoData.shtml">�����ݱ���</a>
                </li>
                <li <s:if test='currentPage=="jiance"'>class="active"</s:if> <s:if test='currentPage=="brief"'>class="active"</s:if>>
                    <s:if test="admin.stopFlag">
                    		<a data-toggle="modal" data-target="#stopUserModal" href="<%=njxBasePath%>/keyword.shtml">��Ϣ���</a>
                    	</s:if>
                    	<s:elseif test="admin.userExtendInfoElement!=null && admin.userExtendInfoElement.isGiftPackage==0">
                    		<a data-toggle="modal" data-target="#getKeywordProject" href="<%=njxBasePath%>/keyword.shtml">��Ϣ���</a>
                    	</s:elseif>
                    	<s:else>
                    		<a href="<%=njxBasePath%>/keyword.shtml">��Ϣ���</a>
                    	</s:else>
                </li>
                <%--<li class='j_hover'>--%>
                    <%--<a href="JavaScript:void(0)">�����ݹ���</a>--%>
                    <%--<div class="w-headbar-dropdown">--%>
		                <%--<div class="w-dropdown-inner active">--%>
		                    <%--<ul>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/view/weiboEventAnalysis/taskList.shtml">΢���¼�����</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/view/eventAnalysis/taskList.shtml">ȫ���¼�����</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/productsAnalysis.shtml">��Ʒ����</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/i/singleWeiboAnalysis/index.shtml">΢����������</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/view/reviewAnalysis/reviewIndex.shtml">���۷���</a>--%>
		                        <%--</li>--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/splitWords.shtml">�ı��ھ�</a>--%>
		                        <%--</li>--%>
<%--<!-- 		                    	<li> -->--%>
<%--<!-- 		                            <a >���Ÿ����</a> -->--%>
<%--<!-- 		                        </li> -->--%>
		                    	<%--<li>--%>
		                            <%--<a href="<%=njxBasePath%>/emotionMap.shtml">΢������</a>--%>
		                        <%--</li>--%>
		                    <%--</ul>--%>
		                <%--</div>--%>
		            <%--</div>--%>
                <%--</li>--%>
				<%--<li>--%>
					<%--<a href="JavaScript:void(0)">רҵ�������</a>--%>
				<%--</li>--%>
                <li class="j_hover
					<s:if test='currentPage=="analysisWb"'>active</s:if> <s:if test='currentPage=="analysis"'>active</s:if>
<s:if test='currentPage=="productsAnalysis"'>active</s:if>
<s:if test='currentPage=="reviewAnalysis"'>active</s:if>
<s:if test='currentPage=="singleWeiboAnalysis"'>active</s:if>
<s:if test='currentPage=="emotionMap"'>active</s:if>
<s:if test='currentPage=="search"'>active</s:if>
">
                    <a href="JavaScript:void(0)">��������������</a>
                    <div class="w-headbar-dropdown">
                        <div class="w-dropdown-inner active" style="left: -85px;">
                            <ul>
                                <li>
                                    <a href="<%=njxBasePath%>/view/weiboEventAnalysis/taskList.shtml">΢���¼�����</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/view/eventAnalysis/taskList.shtml">ȫ���¼�����</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/productsAnalysis.shtml">��Ʒ����</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/view/reviewAnalysis/reviewIndex.shtml">���۷���</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/i/singleWeiboAnalysis/index.shtml">΢����������</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/emotionMap.shtml">΢������</a>
                                </li>
                                <li>
                                    <a href="<%=njxBasePath%>/splitWords.shtml">�ı��ھ�</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>
                <%--<li class="j_hover">--%>
                    <%--<a href="JavaScript:void(0)">רҵ�������</a>--%>
                    <%--<div class="w-headbar-dropdown">--%>
                        <%--<div class="w-dropdown-inner active" style="left: -85px;">--%>
                            <%--<ul>--%>
                                <%--<li>--%>
                                    <%--<a href="https://www.u-mei.com/web/index.html" target="_blank">Uý</a>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<a href="https://www.yqt365.com" target="_blank">����ͨ</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</li>--%>

                <%--<li>--%>
					<%--<a href="JavaScript:void(0)">�۶�-���ݸ߷���̳</a>--%>
				<%--</li>--%>
            </ul>
        </div>
    </div>

</div>
   
<s:if test="admin.stopFlag == 1">
	<!-- �û���ⷽ����ͣ��ʾ����start -->
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
					<strong class="fz18">Hi �װ���&nbsp;<s:if test="admin.nickname==null||admin.nickname==''">${admin.username} </s:if><s:else><s:property value="#attr.admin.nickname" escape="true"/></s:else>,</strong>
					<p class="mt20">&nbsp;&nbsp;&nbsp;&nbsp;��΢�ȵ�(΢����)ϵͳʵʱ��أ����������˺Ž������쳣��Ϊ��Ϊ�����ù��΢������һ�����õ�ϵͳ���л�����С΢������ʹ�������˺Ž��в��ֹ���������<fmt:formatDate value="${admin.stopEndTime}" pattern="yyyy-MM-dd HH:mm:ss" />֮���Զ��������������ʣ���ֱ����ϵ΢�ȵ�(΢����)�ͷ�����Ŷ~</p>
				</div>					
			</div>
			<div class="modal-footer pading-bottom-30" style="text-align: center;">
				<a href="javascript:void(0)" class="" data-dismiss="modal" aria-hidden="true">��֪���ˣ�лл����</a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!-- �û���ⷽ����ͣ��ʾ����end -->
	</s:if>
<!--head���� end-->
</s:if>
<s:else>
	<link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/reginReadPage.css?v=<%=SystemConfig.SYSTEMINITTIME %>">
	<script src='<%=staticResourcePath%>/js/MD5.js?v=<%=SystemConfig.SYSTEMINITTIME %>' type="text/javascript"></script>
	<script src='<%=njxBasePath%>/js/login.js?v=<%=SystemConfig.SYSTEMINITTIME %>' type="text/javascript"></script>
	<link href="<%=njxBasePath%>/css/newRegin/loginStyle.css?v=<%=SystemConfig.SYSTEMINITTIME %>" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<%=staticResourcePath%>/js/twitter-bootstrap-hover-dropdown.js?v=<%=SystemConfig.SYSTEMINITTIME %>"></script>
	<!--ͷ������start-->
	<div id="MainTool"></div>
<div class="w-head headHover min-layout1200" id="noLoginCssId">
    <div class="w-headbar-top">
        <div class="w-headbar-logo">
            <%--<img src="<%=njxBasePath%>/images/shouye/w-sm-logo.png" alt="">--%>
				<img src="<%=njxBasePath%>/css/indexV4/base/images/w-md-logo.png" alt="">
        </div>
        <img src="<%=njxBasePath%>/images/shouye/w-logo-title.png" class="w-headbar-txt margin-left-20" alt="">
        <%--<div class="w-headbar-r">--%>
            <%--<a id="toRegisterID" style="cursor: pointer;" data-toggle="modal" data-target="#reginTop" class="font-size-14 color_1 padding-horizontal-30">���ע��</a>--%>
            <%--<a id="login" href="#" onclick="getRan()" data-toggle="modal" data-target="#loginModal" class="btn-login">��¼</a>--%>
        <%--</div>--%>

    </div>
    <div class="w-headbar-bottom">
        <div class="w-headbar-nav">
        	<ul class="w-headbar-list clearfix" id="m">
                <li <s:if test='currentPage=="hot"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/home.shtml" data-toggle="modal">�ȵ㷢��</a>
                </li>
                <li>
                    <a href="JavaScript:intoTo(24)">������</a>
                </li>
                <li  <s:if test='currentPage=="bigDataRead"'>class="active"</s:if>>
                    <a href="<%=njxBasePath%>/infoData.shtml">�����ݱ���</a>
                </li>
                <li>
                    <a href="JavaScript:intoTo(5)" >��Ϣ���</a>
                </li>
				<li class='j_hover'>
					<a href="JavaScript:void(0)">�����ݹ���</a>
					<div class="w-headbar-dropdown">
						<div class="w-dropdown-inner active">
							<ul>
								<li>
									<a href="JavaScript:intoTo(11)">΢���¼�����</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(10)">ȫ���¼�����</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(13)">��Ʒ����</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(12)">΢����������</a>
								</li>
								<li>
									<a href="#" data-toggle="modal" data-target="#loginModal">���۷���</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(25)">�ı��ھ�</a>
								</li>
								<li>
									<a href="JavaScript:intoTo(31)">΢������</a>
								</li>
							</ul>
						</div>
					</div>
				</li>
				<%--<li class="j_hover">--%>
					<%--<a href="JavaScript:void(0)">רҵ�������</a>--%>
					<%--<div class="w-headbar-dropdown">--%>
						<%--<div class="w-dropdown-inner active" style="left: -85px;">--%>
							<%--<ul>--%>
								<%--<li>--%>
									<%--<a href="https://www.u-mei.com/web/index.html" target="_blank">Uý</a>--%>
								<%--</li>--%>
								<%--<li>--%>
									<%--<a href="https://www.yqt365.com" target="_blank">����ͨ</a>--%>
								<%--</li>--%>
							<%--</ul>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--<a href="JavaScript:void(0)">�۶�-���ݸ߷���̳</a>--%>
				<%--</li>--%>
            </ul>
			<div class="pull-right">
				<a id="toRegisterID" style="cursor: pointer;" data-toggle="modal" data-target="#reginTop" class="font-size-14 color_1 padding-horizontal-30">10��ע��</a>
				<a id="login" href="#" onclick="getRan()" data-toggle="modal" data-target="#loginModal" class="btn-login">��¼</a>
			</div>
        </div>
        <%--<div class="w-headbar-search" style="display: none;">--%>
            <%--<input type="text" class="w-search-input" placeholder="��������ҵ����Ʒ�������¼��ؼ���" id="search-keyword"/>--%>
            <%--<span onclick="goOpenTools();" class="w-headbar-search-icon"><i class="icon icon-sousuo1 font-size-20 color_2"></i></span>--%>
            <%--<div class="w-search-outline"></div>--%>
        <%--</div>--%>
    </div>

</div>
<!--ͷ������end-->
    
    <!--ע��ҳ start-->
		<div id="reginTop" class="modal modalTwo fade Upgrade b-radius10 border-n" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 960px; margin-left: -480px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">��</button>
			</div>
			<div class="modal-body bg_white icheckbox-list text-center clearfix" style="overflow-y:initial;">
				<div class="title p_v_30 fz24">
					<h3>΢�ȵ�(΢����)��վ��������</h3>
				</div>
				<div class="servers mt10 text-left" style="height: 370px; overflow: hidden; overflow-y: auto;">
             

            <p>������������΢�ȵ�(΢����)��վ��<a href="www.wrd.cn" id="help_title">www.wrd.cn</a>�����³�Ϊ������վ�������û����³�Ϊ������������ͬ�޽�Ķ�˫������Լ��������Ч��Լ��</p>
            <p>΢�ȵ�(΢����)���û��ṩ����վ����չʾ�Ĳ�Ʒ������³ơ�΢�ȵ�(΢����)������΢�ȵ�(΢����)���񡱡��������񡱣����������ϸ��·������ݣ����µ�΢�ȵ�(΢����)�����Ա���վ�ϵ���ز�Ʒ��������ܵ�ҳ��չʾ�Լ����û�ʵ���ṩ��Ϊ׼��</p>
            <h1>һ������</h1>

            <p>1.1 ��ȷ�ϣ�����ʹ�ñ�����֮ǰ���Ѿ�����Ķ�����Ⲣ���ܱ����������ȫ�����ݣ��ر����ԼӴּ�/���»��߱�ע�����ݣ���һ����ѡ��ͬ�⡱�����ע�����̻�ʹ�ñ����񣬼���ʾ��ͬ����ѭ����������֮����Լ����</p>

            <p>1.2 ��ͬ�⣺΢�ȵ�(΢����)��Ȩ��ʱ�Ա����������Ӧ�ķ���������ݽ��е�����ı��������Ȩ����Ϣ���͡���ҳ����ȷ�ʽ���Թ��������������е���֪ͨ���������ڱ������������ݹ����������ʹ�ñ�����ģ���ʾ���ѳ���Ķ�����Ⲣ�����޸ĺ��Э�����ݣ�Ҳ����ѭ�޸ĺ����������ʹ�ñ�����������ͬ���޸ĺ�ķ��������Ӧ����ֹͣʹ�ñ�����</p>
            <h1>�����˻�</h1>
            <p>2.1 ע��</p>
            <p>2.1.1 ע�����ʸ�</p>
            <p>2.1.1.1 ��ȷ���������ע������������΢�ȵ�(΢����)����ķ�ʽʵ��ʹ�ñ�����ʱ����Ӧ���Ǿ߱���ȫ����Ȩ����������ȫ������Ϊ��������Ȼ�ˡ����˻�������֯������ͳ��Ϊ���������塱����</p>
            <p>2.1.1.2 ������δ�����˻�����������Ϊ�����ˣ��������߱�ǰ�������ʸ��������ļ໤��Ӧ�е������Ĳ���ע����Ϊ�����µ�һ�к������΢�ȵ�(΢����)��Ȩע�������ö��ᣩ�����˻���</p>
            <p>2.1.2 ע�ᡢ�˻�</p>
            <p>2.1.2.1 ��������ע��ҳ����ʾ��д��Ϣ���Ķ���ͬ�Ȿ�������������ȫ��ע�����󣬻��������ռ���ҳ����ʾ��д��Ϣ���Ķ���ͬ�Ȿ�������������ȫ���������󣬻���������΢�ȵ�(΢����)����ķ�ʽʵ��ʹ�ñ���վ����ʱ�������ܱ���������Լ����������ʹ����ȷ���ֻ�������Ϊ��¼�ֶν��뱾��վ��</p>
            <p>2.1.2.2 Ŀǰ΢�ȵ�(΢����)����һ����������ӵ�ж��΢�ȵ�(΢����)�˻�����һ��΢�ȵ�(΢����)�˻����ܶ�ӦΨһ�ķ������塣�����з��ɹ涨����Ч��������ȷ�������߷���΢�ȵ�(΢����)�������������������������κη�ʽת�á�����������˼̳�����΢�ȵ�(΢����)�˻���ͬʱ���ڽ��з���������΢�ȵ�(΢����)�˻�ת�á������̳�ʱ��΢�ȵ�(΢����)��ȨҪ��������/�����������ߡ������ļ̳����ṩ�ϸ���ļ����ϲ�����΢�ȵ�(΢����)Ҫ��Ĳ������̰���</p>
            <p>2.1.2.3 ͨ������£�����΢�ȵ�(΢����)�˻������ڱ���վ����һ�л��Ψһ���ʶ�����ݣ�ÿһ��΢�ȵ�(΢����)�˻��������ڱ���վ������չ����������������£�΢�ȵ�(΢����)��Ȩ�����Լ����жϣ���ͬһ��/�������������ӵ�еĶ��΢�ȵ�(΢����)�˻�����ͳһ���������������ڣ�</p>
            <p>2.1.2.3.1���΢�ȵ�(΢����)�˻�֮�����һ������ע����Ϣ��ͬ����Ϊ�������Ĳ�Ʒ���������ͬһĿ�ģ��������������Σ�������Υ�����ɷ��桢���������΢�ȵ�(΢����)����Ʒ���������΢�ȵ�(΢����)�������Ϊ����΢�ȵ�(΢����)ͨ������������֤�������ж�����΢�ȵ�(΢����)�˻�ʵ������ͬһ���������ͬһ����ģ�</p>
            <p>2.1.2.3.2 ����΢�ȵ�(΢����)�г���������Ҫ�Զ��΢�ȵ�(΢����)�˻�����ͳһ��������Ρ�</p>
            <p>2.1.3 ��Ϣ</p>
            <p>2.1.3.1 �����ע��򼤻�����ʱ����Ӧ�����շ��ɷ���Ҫ�󣬰���Ӧҳ�����ʾ׼ȷ�ṩ����ʱ�����������ϣ���ʹ֮��ʵ����ʱ��������׼ȷ��</p>
            <p>2.1.3.2 ��Ӧ��׼ȷ��д����ʱ�������ṩ����ϵ�绰����ϵ��ַ�������������ϵ��ʽ���Ա�΢�ȵ�(΢����)����������Ч��ϵ����ͨ����Щ��ϵ��ʽ�޷�����ȡ����ϵ����������ʹ��΢�ȵ�(΢����)��������в����κ���ʧ�����ӷ��õģ�Ӧ������ȫ���Գе������˽Ⲣͬ�⣬�������񱣳����ṩ����ϵ��ʽ����Ч�ԣ����б����Ҫ���µģ���Ӧ��΢�ȵ�(΢����)��Ҫ����в�����</p>
            <p>2.2 �˻���ȫ</p>
            <p>2.2.1 �������и��������΢�ȵ�(΢����)�˻������뱣�ܣ���������ڸõ�¼���������·��������л�������������ڻ�ȡ��Ϣ��ת����Ϣ��׫д������ύ�������Э�顢������ǩЭ��������ȣ��е����Ρ���ͬ�⣺(a)�緢���κ���δ����Ȩʹ������΢�ȵ�(΢����)�˻������룬����Υ�����ܹ涨���κ������������������֪ͨ΢�ȵ�(΢����)����(b)ȷ������ÿ������ʱ�ν���ʱ������ȷ�����뿪��վ��΢�ȵ�(΢����)����Ҳ���������δ�����ر���涨���������κ���ʧ����ٸ��������΢�ȵ�(΢����)�����������ȡ�ж���Ҫ����ʱ�䣬΢�ȵ�(΢����)���ڲ�ȡ�ж�ǰ�Ѿ������ĺ���������������������κ���ʧ�����е��κ����Ρ�</p>
            <p>2.2.2 �����з��ɹ涨��˾���ö���������΢�ȵ�(΢����)��ͬ�⣬�������ĵ�¼�����벻�����κη�ʽת�á������̳У����˻���صĲƲ�Ȩ����⣩��</p>
            <p>2.2.3����Ⲣͬ�⣬΢�ȵ�(΢����)��Ȩ���չ���˾�������������¡���ȫ�Ȼ��أ������������ڹ������ء������ء���Ժ�����ء�˰����ء���ȫ���ŵȣ���Ҫ�������������ύ���ĸ���ע����Ϣ��ʹ�ü�¼��</p>
            <p>2.3 �˻�ע��</p>
            <p>2.3.1 ΢�ȵ�(΢����)��������Υ�����ҡ��ط����ɷ���涨��Υ�����������������£���ֹ����ֹΪ���ṩ���ֻ�ȫ������ֱ��ע��΢�ȵ�(΢����)�˻���Ȩ����</p>
            <p>2.3.2 ΢�ȵ�(΢����)��¼����ע��</p>
            <p>2.3.2.1 Υ��΢�ȵ�(΢����)�������κη���Э��/�������淶�ȹ淶���ݣ�</p>
            <p>2.3.2.2 �ƻ�����ͼ�ƻ�΢�ȵ�(΢����)ϵͳ������ʹ������</p>
            <p>2.3.2.3 �κ�ʹ�ú���΢�ȵ�(΢����)���ơ�Ʒ���Ҷ������������ɻ��κ�ʹ��ĳ����Ӣ��(ȫ�ƻ���)�����֡���������ͼ��ʾ��ӳ����΢�ȵ�(΢����)����ĳ�ֹ�ϵ�ģ�</p>
            <p>2.3.2.4΢�ȵ�(΢����)�������к����жϣ���Ϊ��������������Ϊ������ͬ������������Ʒ��յ����������</p>
            <h1>������վ����ʹ������</h1>
            <p>Ϊ��Ч������ʹ�ñ�����ĺϷ�Ȩ�棬����Ⲣͬ��������¹���</p>
            <p>3.1 ��ͨ�����������������·�ʽ��΢�ȵ�(΢����)������ָ�����Ϊ�����˵�ָ����ɳ��ػ�������Ӧ���ж�΢�ȵ�(΢����)ִ��ǰ��ָ�����������κν���е����Ρ�</p>
            <p>3.1.1 ͨ������΢�ȵ�(΢����)�˻���������е����в�����</p>
            <p>3.1.2 ͨ���������˺Ű󶨵��ֻ�������΢�ȵ�(΢����)���͵�ȫ����Ϣ��</p>
            <p>3.1.3 ͨ���������˺Ű󶨵�����Ӳ�����նˡ���������š����롢���롢�����˻��������������������΢�ȵ�(΢����)���͵���Ϣ��</p>
            <p>3.1.4 ����΢�ȵ�(΢����)����Լ����΢�ȵ�(΢����)�Ͽɵ�������ʽ��</p>
            <p>3.2 ����ʹ�ñ���������У�΢�ȵ�(΢����)��վ�ϳ��ֵĹ�����վ��������ʾ��΢�ȵ�(΢����)���͵����ֻ�����Ϣ�����Ż����͵ȣ���������ʹ�ñ��������ع�����ʹ�ñ����񼴱�ʾ��ͬ����ܱ��������ع������˽Ⲣͬ��΢�ȵ�(΢����)��Ȩ�����޸ķ������ع��򣬶�������������ͬ�⣬�������Ӧ����ʹ�÷���ʱ��ҳ����ʾ�����͵����ֻ��Ķ��Ż�绰�ȣ�Ϊ׼����ͬ�Ⲣ���շ����������ʹ�ñ������ǰ�ᡣ</p>
            <p>3.3 ΢�ȵ�(΢����)���ܻ��Ե����ʼ������͵����ֻ��Ķ��Ż�绰�ȣ���ʽ֪ͨ�������չ����Լ���ʾ��������һ���Ĳ�������΢�ȵ�(΢����)����֤���ܹ��յ����߼�ʱ�յ����ʼ������͵����ֻ��Ķ��Ż�绰�ȣ����Ҳ��Դ˳е��κκ������ˣ��ڷ����������Ӧ����ʱ��¼������վ�鿴�ͽ��в���������û�м�ʱ�鿴�ͶԷ���״̬�����޸Ļ�ȷ�ϻ�δ���ύ�����������µ��κξ��׻���ʧ��΢�ȵ�(΢����)�����κ����Ρ�</p>
            <p>3.4 ����Ȩ΢�ȵ�(΢����)����ͨ������������������ݺ��ʸ�ȡ����ʹ�ñ������������ϡ�</p>
            <p>3.5 ������ʼʹ��΢�ȵ�(΢����)��ĳһ��Ʒ�����ǰ��������Ҫ��΢�ȵ�(΢����)����һ��Ʒ�����ǩ�������ķ���Э�顣��ֻ���ڽ��ܸ÷���Э���ȫ�����ݺ󷽿�ʹ�øò�Ʒ�����������ͬ��÷���Э��Ĳ��ֻ���ȫ���ģ�������Ҫ���к���������</p>
            <p>3.6 ����ʹ��΢�ȵ�(΢����)����ʱ��΢�ȵ�(΢����)��Ȩ������Ӧ�Ĳ�Ʒ/��������շѽ��ܡ�������/�����Э��������ȡ������á�΢�ȵ�(΢����)ӵ���ƶ������������֮Ȩ������������������ʹ�ñ�����ʱҳ��������֮�շѷ�ʽ���������΢�ȵ�(΢����)��ɵ���������Э��Ϊ׼��</p>
            <h2>3.7 �ر���ʾ</h2>
            <p>3.7.1 ΢�ȵ�(΢����)����ͨ�󣬼�ʹ��δ����������Ŀ����Դ����δ�����µĲ���������ռ����Դ���շ����ɻᷢ����</p>
            <p>3.7.2 ΢������Ϊ΢�ȵ�(΢����)��Ԥ֧��վ�ڻ��ң������ر��ۿ��Żݣ�һ�����򣬲�֧���˻����������鿼�Ǻ���</p>
            <h2>3.8 ���߼�����</h2>
            <p>3.8.1 ����ʹ��΢�ȵ�(΢����)����Ĺ����У��п�����Ϊ���ڱ����������5.3����������֮һ������΢�ȵ�(΢����)��ȡ�˰�����������ֹͣȫ���򲿷ַ������Ʒ���Ĺ��ܴ�ʩ��΢�ȵ�(΢����)��ͨ���ʼ���վ���š����Ż�绰�ȷ�ʽ֪ͨ��������Ӧ�ĳ���������ߡ�</p>
            <p>3.8.2 ��ͨ�����߳�����΢�ȵ�(΢����)�������������ƻ򶳽��ָ�����ģ�Ӧ����΢�ȵ�(΢����)��Ҫ����ʵ�ṩ���֤����������ϣ��Լ�΢�ȵ�(΢����)Ҫ���������Ϣ���ļ����Ա�΢�ȵ�(΢����)���к�ʵ����Ӧ�������������߲�����Ȼ������΢�ȵ�(΢����)��Ȩ���������ж��������Ƿ�ͬ��������������</p>
            <p>3.8.3 ����Ⲣͬ�⣬������ܾ���ʵ�ṩ���֤����������ϵģ���δ��ͨ��΢�ȵ�(΢����)��˵ģ�΢�ȵ�(΢����)��Ȩ���ڶ���õ��˻��ҳ������ƸõȲ�Ʒ���߷���Ĳ��ֻ�ȫ�����ܡ�</p>
            <p>3.9 ���ڵ�����</p>
            <p>3.9.1 �����ͨ��ʹ�ñ����񣬽���ȡʹ�����Ե��������κβ�Ʒ������������������ڸõȵ���������������������΢�ȵ�(΢����)�Դ˲�������಻�е��κ����Σ����������Ӱ������õ������ķ��ɹ�ϵ��</p>
            <p>3.9.2 ��ȷ�ϲ�ͬ�⣬΢�ȵ�(΢����)�ĸ�������˾��Ϊ����������ĵ����������ˣ���΢�ȵ�(΢����)������˾��Ȩֱ��ǿ��ִ�в�����������������������������κι涨������֮�⣬�κε�������������Ϊ����������ĵ����������ˡ�</p>
            <h1>�ġ� ����Ȩ��������</h1>
            <p>4.1 ����Ȩ������΢�ȵ�(΢����)�ṩ�Ļ�������������Ϣ���񣬲���Ȩ���ڽ���΢�ȵ�(΢����)�ṩ�ķ���ʱ���΢�ȵ�(΢����)�ļ���֧�֡���ѯ�ȷ��񣬷��������������վ��ز�Ʒ���ܡ�</p>
            <p>4.2 ����֤�������ü����������ֶ��ƻ������ұ���վ��΢�ȵ�(΢����)��������</p>
            <p>4.3 ��Ӧ����΢�ȵ�(΢����)��������������֪ʶ��Ȩ�������Ϸ�Ȩ��������֤�ڷ����ַ�����Ȩ���Υ���¼�ʱ��������΢�ȵ�(΢����)����ɶ�����Ա����������������õ��¼��ܵ�Ӱ�����ʧ��΢�ȵ�(΢����)�������ַ�΢�ȵ�(΢����)�Ϸ�Ȩ��ʱ��ֹ�����ṩ���񲢲��˻��κο����Ȩ����</p>
            <p>4.4 ����������΢�ȵ�(΢����)�ṩ�����緽ʽ�����Լ������ڽ���΢�ȵ�(΢����)�ʼ��ĵ������䰲ȫ�ԡ��ȶ��Բ��Ѷ����µ�һ�к������Ӧ���ге����Σ�����������������δ�ܼ�ʱ�յ�΢�ȵ�(΢����)�����֪ͨ�����µĺ������ʧ��</p>
            <p>4.5����֤��</p>
            <p>4.5.1.��ʹ��΢�ȵ�(΢����)��Ʒ�����ʱ����ӹ��ҡ��ط����ɷ��桢��ҵ��������ṫ�����£���������΢�ȵ�(΢����)�ṩ�ķ�����д洢������������������Ϣ�����ݣ�Υ�����ҷ��ɷ������ߵ��κ����ݣ���Ϣ����Υ�����ҹ涨������������/��������Ϣ���漰�������ܺ�/��ȫ����Ϣ���⽨���ź�/�����ࡢɫ�顢��������Ϣ������������Ϣ�������н����Ĳ���Ϸ��Υ������������ڽ����ߵ���Ϣ���������������а�ȫ����Ϣ���ֺ����˺Ϸ�Ȩ�����Ϣ��/�����������������������ΰ����������µ���Ϣ������;��ͬʱ��ŵ����Ϊ���˷������������Ϲ��ҹ涨��/�򱾷�������Լ������Ϣ�����ṩ�κα���;</p>
            <p>4.5.2. ʹ��΢�ȵ�(΢����)��Ʒ�����ʱ��Ӧ��������΢�ȵ�(΢����)ǩ���ķ����������ȷ��ͬ��Ķ���ҳ������ݣ������������ڣ�</p>
            <p>4.5.2.1. ��Ӧ��ʱ���</p>
            <p>4.5.2.2. ��Ӧ�����κ��ƻ�����ͼ�ƻ����簲ȫ����Ϊ�ȣ�</p>
            <p>����Υ��������֤��΢�ȵ�(΢����)����Ȩ������ط��������ȡɾ����Ϣ����ֹ������ֹ����Ĵ�ʩ������Ȩ�������˻����¹���Ʒ��������ѵȲ��ֻ�ȫ�����ܣ�������������Ϊ��΢�ȵ�(΢����)�����ʧ�ģ���Ӧ���⳥��
            </p>
            <p>4.6 ����ʹ�õ�ĳ������а��������ص�΢�ȵ�(΢����)�������΢�ȵ�(΢����)���������Ƕ�ռ�Եġ�����ת�õġ�����ҵ��ӪĿ�ĵĸ���ʹ����ɡ�����΢�ȵ�(΢����)������ʾ����������Լ���⣬�����ø��ơ��޸ġ����������ۻ������������������κβ��֣�Ҳ���ý��з��򹤳̻���ͼ��ȡ�������Դ���롣</p>
            <h1>�塢 ΢�ȵ�(΢����)��Ȩ��������</h1>
            <p>5.1 ΢�ȵ�(΢����)Ӧ������ѡ��ķ����Լ����ɿ������������ṩ�ϸ�����缼������Ϣ����</p>
            <p>5.2΢�ȵ�(΢����)��ŵ�������ϲ�ȡ���Ᵽ�ܴ�ʩ�������������¶�����ϣ�����Ȩ������ʹ�������ϣ����ǣ�</p>
            <p>5.2.1 ���ݱ����������������΢�ȵ�(΢����)֮����������Э�顢��ͬ����������ȹ涨�����ṩ��</p>
            <p>5.2.2 ���ݷ��ɷ���Ĺ涨Ӧ���ṩ��</p>
            <p>5.2.3 ������˾����ְȨ����Ҫ��΢�ȵ�(΢����)�ṩ��</p>
            <p>5.2.4 ��ͬ��΢�ȵ�(΢����)��������ṩ��</p>
            <p>5.2.5 ΢�ȵ�(΢����)����ٱ��¼����������϶��ύ�ģ�</p>
            <p>5.2.6 ΢�ȵ�(΢����)Ϊ��ֹ����Υ����Ϊ�����ӷ�����Ϊ��������ȡ��Ҫ�����ж��������ύ�ģ�</p>
            <p>5.2.7 ΢�ȵ�(΢����)Ϊ�����ṩ��Ʒ��������Ϣ����������ṩ�ģ�����΢�ȵ�(΢����)ͨ���������ļ��������������ṩ��Ʒ��������Ϣ�������</p>
            <h1>���� ��˽������������Ϣ�ı���</h1>
            <p>һ����ͬ�Ȿ���������ʹ�ñ���������ͬ��΢�ȵ�(΢����)��������������ʹ�ú���¶���ĸ�����Ϣ��</p>
            <p>6.1 ��¼��������</p>
            <p>����ע���ʻ�ʱ��΢�ȵ�(΢����)��Ҫ��������΢�ȵ�(΢����)�˻���¼����������ʶ��������ݣ������ʺ��������ֻ����룬Ҳ��Ψһ��ͨ����֤���һ���������ķ�ʽ��������ͨ�������õ�������ʹ�ø��˻��������й©�����룬�����ܻᶪʧ���ĸ���ʶ����Ϣ�����˻����������κ�ԭ���ܵ�Ǳ�ڻ���ʵΣ��ʱ����Ӧ��������΢�ȵ�(΢����)ȡ����ϵ����΢�ȵ�(΢����)��ȡ�ж�ǰ��΢�ȵ�(΢����)�Դ˲����κ����Ρ�</p>
            <p>6.2 �û���Ϣ</p>
            <p>������˻�ע��ʱ��������΢�ȵ�(΢����)�ṩ�����ǳƺ͵����ʼ���ַ����������ѡ������д��ظ�����Ϣ������������������˾���ڵ�ʡ�ݺͳ��С�ʱ�����������롢��������Ϊ������Ե������ṩ�µķ���ͻ��ᣬ���˽Ⲣͬ��΢�ȵ�(΢����)���������˾��ͨ�����ĵ����ʼ���ַ����ֻ�֪ͨ����Щ��Ϣ��</p>
            <p>6.3 ��¼��¼</p>
            <p>Ϊ�˱�����ʹ�ñ�����İ�ȫ�Լ����ϸĽ�����������΢�ȵ�(΢����)����¼����������¼��ʹ�ñ�����������Ϣ����΢�ȵ�(΢����)��ŵ����������Ϣ�ṩ���κε���������˫������Լ�����ɷ������й涨��΢�ȵ�(΢����)������˾�⣩��</p>
            <p>6.4�ⲿ����</p>
            <p>����վ���ܺ��е�������վ�����ӣ���΢�ȵ�(΢����)��������վ����˽������ʩ�����κ����Ρ�΢�ȵ�(΢����)�������κ���Ҫ��ʱ��������ҵ������Ʒ�Ƶ���վ��</p>
            <p>6.5��ȫ</p>
            <p>΢�ȵ�(΢����)�������м����ṩ��Ӧ�İ�ȫ��ʩ��ʹ΢�ȵ�(΢����)���յ���Ϣ����ʧ���������úͱ��졣��Щ��ȫ��ʩ�����������������������ݺͶ��û�������ܡ���������Щ��ȫ��ʩ����΢�ȵ�(΢����)����֤��Щ��Ϣ�ľ��԰�ȫ��</p>
            <h1>�ߡ� ϵͳ�жϻ����</h1>
            <p>ϵͳ����������״���޷�����������ʹ���޷�ʹ�ø����������ʱ��΢�ȵ�(΢����)���е����⳥���Σ���״�������������ڣ�</p>
            <p>7.1 ΢�ȵ�(΢����)��ϵͳͣ��ά���ڼ䡣</p>
            <p>7.2 �����豸���ֹ��ϲ��ܽ������ݴ���ġ�</p>
            <p>7.3 ��̨�硢���𡢺�Х����ˮ��ͣ�硢ս�����ֲ�Ϯ���Ȳ��ɿ���֮���أ����΢�ȵ�(΢����)ϵͳ�ϰ�����ִ��ҵ��ġ�</p>
            <p>7.4 ���ںڿ͹��������Ų��ż�����������ϡ���վ���������з���������ԭ�����ɵķ����жϻ����ӳ١�</p>

            <h1>�ˡ� ���η�Χ����������</h1>
            <p>8.1 ΢�ȵ�(΢����)���Ա��������������������γе���Χ����</p>
            <p>8.2 ������֮������λ�����ṩ֮����Ʒ�ʼ������ɸú�����λ���и���</p>
            <p>8.3 ���˽Ⲣͬ�⣬����ʹ�ñ�����Υ��������������������˻��²�ȡ���κ��ж��������µ��κε��������⣬Ӧ�ҽ�Ӧ�������˳е�������ɴ�����΢�ȵ�(΢����)���������˾��Ա�����ͻ��ͺ�����鱻����������ģ���Ӧ���������е��ɴ���ɵ�ȫ�����Ρ�</p>
            <p>8.4 �����������ͷ������������ƣ�΢�ȵ�(΢����)����֤��Ϣ����������Ǿ���������׼ȷ�ģ�΢�ȵ�(΢����)�ṩ�����ݽ����û��ο���΢�ȵ�(΢����)������������Դ�����磬΢�ȵ�(΢����)���Ի����������ṩץȡ�������͹��ɻ��ܷ��񣬶���������Դ���ݲ�׼ȷ��Դ������Ȩ��Դ����ץȡ��������ץȡ�������ݷ�����׼ȷ��ԭ��������κ�ֱ�ӵġ���ӵġ��ͷ��Եġ�����ġ���������ʧ������ҵ����ʧ��������ʧ��������ʧ��ʹ�����ݡ����������������������ʧ������������β����ģ�Ҳ�������ɶԱ����������ΥԼ������Υ����֤����������Ȩ��ɵģ����������κ����Σ���ʹ�����ѱ���֪�˵���ʧ�Ŀ����ԡ����⼴ʹ����������涨�������Ծȼ�û�дﵽ�����Ŀ�ģ�ҲӦ�ų�΢�ȵ�(΢����)��������ʧ�����Ρ�</p>
            <p>8.5 ���������������й涨��΢�ȵ�(΢����)������ĳһ�����Ʒ��/���������Լ���⣬���κ�����£���ͬ��΢�ȵ�(΢����)�Ա������������е����⳥�����ܶ����������ȡ�ĵ��·�������ܶ</p>

            <h1>�š� ����Э��</h1>
            <p>9.1 �����������ɱ����������뱾��վ��ʾ�ĸ��������ɣ�������ʿɻ������ò��գ����в�ͬ��⣬�Ա���������Ϊ׼��</p>
            <p>9.2 ������������½ڱ����Ϊ���ķ�����裬�����з��ɻ��ͬЧ����</p>
            <p>9.3 ���Ա���������������ͬ�������Ա���������������ɲ��ֵ�������Ⲣ��ͬ��һ����ʹ�ñ���������΢�ȵ�(΢����)���ܱ���������������ɲ��ֵ�Լ����</p>
            <p>9.4 ��������������ݱ��й�ϽȨ�ķ�Ժ�϶�ΪΥ���ģ������Ӱ���������ݵ�Ч����</p>

            <h1>ʮ���̱ꡢ֪ʶ��Ȩ�ı���</h1>
            <p>10.1 ����������Ʒ������⣬����վ�ϵļܹ���ҳ����ƣ�����΢�ȵ�(΢����)��΢�ȵ�(΢����)������ҵ����ӵ����֪ʶ��Ȩ���������������̱�Ȩ��ר��Ȩ������Ȩ����ҵ���ܵȡ�</p>
            <p>10.2΢�ȵ�(΢����)ץȡ���������ݣ�����Դ�ڻ�������΢�ȵ�(΢����)��Ϊ�û��ṩ���Ի���Ϣ��������</p>
            <p>10.2 �Ǿ�΢�ȵ�(΢����)��΢�ȵ�(΢����)������ҵ����ͬ�⣬�κ��˲�������ʹ�á��޸ġ����ơ������������ı䡢ɢ�������л򹫿�������վ�ϳ�������ݡ�</p>
            <p>10.3 ����֪ʶ��Ȩ����Ӧ������������Υ������Ӧ�е����⳥���Ρ�</p>

            <h1>ʮһ��֪ͨ�ʹ�</h1>
            <p>11.1 ����Ⲣͬ�⣬΢�ȵ�(΢����)�����������жϣ�ͨ����ҳ���桢�����ʼ����ֻ����Ż򳣹���ż����͵ȷ�ʽ��������֪ͨ����΢�ȵ�(΢����)�������������ṩ����ϵ��Ϣ��������׼ȷ�ҵ�ǰ��Ч�ģ�����֪ͨ�ڷ���֮����Ϊ���ʹ��ռ��ˡ�</p>
            <p>11.2 ���Ǳ�������������Լ����΢�ȵ�(΢����)��������ǩ����Э����ȷ�涨��֪ͨ��ʽ�������͸�΢�ȵ�(΢����)��֪ͨ��Ӧ��ͨ��΢�ȵ�(΢����)������ʽ������ͨ�ŵ�ַ��������롢�����ʼ���ַ����ϵ��Ϣ�����ʹ</p>

            <h1>ʮ���� �����������Ͻ</h1>
            <p>����������֮Ч�������͡������ִ�����������������л����񹲺͹����ɡ��򱾷����������֮���飬��Ӧ�����л����񹲺͹��������Դ������ύ�Ϻ����ֶ���������Ժ���С�</p>

        </div>

			</div>
			<div class="media_footer text-center mb30">
				<button class="btn btn-gray-a btn-half w200 mr30" data-dismiss="modal" >��ͬ��</button>
				<button onclick="goNextRegin();" class="btn btn-orange btn-half w200" data-dismiss="modal" >ͬ�Ⲣ����</button>
			</div>
		</div>
		<!--ע��ҳ end-->

    <!-- �µ�½�� start -->
    <div id="loginModal" class="modal modalTwo fade Upgrade b-radius10 border-n login-group" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 760px; margin-left: -380px;height: 495px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">��</button>
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
						<h1 class="fc_dark_grey-900 fz16">���û�ע�Ტ��¼����ѻ�ã�</h1>
					</div>					
					<ul class="list-group list-group-sm list-group-none text-left fz14 mb20">
						<li class="list-group-item"><i class="icon fc_blue mr5">&#xe725;</i> һ����Ч��Ϊ7��ļ�ⷽ��</li>
						<li class="list-group-item"><i class="icon fc_blue mr5">&#xe725;</i>�ɲ鿴��3���ȫ����Ϣ</li>
						<li class="list-group-item"><i class="icon fc_blue mr5">&#xe725;</i>��ʱ�����ȡʵʱԤ��</li>
						<li class="list-group-item"><i class="icon fc_blue mr5">&#xe725;</i>ÿ��9�㶨ʱ�����ձ�</li>
					</ul>
					 
				</div>
				<div class="modal-right">
					<div class="modal-rightCon p_h_60" style="height: 495px;">
						 <div class="row">
						 	<div class="col-md-12">
						 		<ul class="tabs-menu" id="myTab">
		  		                	<li id="selectTabID" class="active col-md-6"><a onclick="changeLoginType(1);" href="#accountLogin" data-toggle="tab">�ʺŵ�¼</a></li>
                                	<li class="col-md-6"><a onclick="changeLoginType(2);" href="#messageLogin" data-toggle="tab">���ŵ�¼</a></li>
		  	                    </ul>
		  	                    <div class="tab_box"  id="myTabContent">
		  	                    <FORM method=post name=F id="loginFormID">
		  	                    	<input name="password" id="passwords" type="hidden" autocomplete="off"/>
		  	                   		<input name="imgVcode" id="img_v_code" type="hidden" autocomplete="off"/>
		  	                   		<div class="tab-pane fade in active" id="accountLogin">
								  		<!--�ʺ������¼ start-->
										<div class="login">
											
											<div class="phoneLogin">
												<div class="mb10 rel">	
													<input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;ime-mode: disabled;" class="inputText" type="text" id="username" name="username" autocomplete="off" placeholder="�������ֻ���" value="">
												</div>
						
												<div class="mb10 rel">	
													<input style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" class="inputText" id="password" type="password" onfocus="this.type='password'" autocomplete="off" placeholder="����������">
												</div>
												<div class="row " style="height:42px">
													<div class="col-md-7 rel">								
														<input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" id="userPCode" class="inputText" type="text" autocomplete="off" placeholder="��������֤��">
														<input type="hidden" id="_ran" name="_ran" />
													</div>
													<div class="col-md-5 imgVcode">
														<img id="imgVcode" onClick="getRan()" width="110" height="42">
													</div>
												</div>
						
												<div class="mt20 text-center fc_dark_grey"><a href="<%=njxBasePath%>/lostPassword.shtml" class="fc_orange-b ml10"> ��������</a></div>
						
												<div class="btn btn-orange btn-block btn-half mt10" onclick="userLogin()">������¼</div>
											</div>
											
											<div class="loginBtn mt10">
												<h1><span>ʹ��������ʽ��¼</span></h1>
												<div class="text-center">
													<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toSinaWeiboLogin.action';" id="wbLoginBtn" class="btn-circle btn-icon50 btn-icon" title="΢����¼" style="margin-right: 16px;">
													   <i class="icon">&#xe724;</i>
													</a>
													<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toWeixinLogin.action';" id="wxLoginBtn" class="btn-circle btn-icon50 btn-icon" title="΢�ŵ�¼" style="margin-right: 16px;">
													   <i class="icon">&#xe716;</i>
													</a>
													<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toTencentLogin.action';" id="qqLoginBtn" class="btn-circle btn-icon50 btn-icon" title="QQ��¼" style="margin-right: 16px;">
													   <i class="icon">&#xe728;</i>
													</a>
<%-- 													<a href="#" onclick="javascript:location.href='<%=njxBasePath%>/thirdParty/toJCloudLogin.action';" id="jCloudLoginBtn" class="btn-circle btn-icon50 btn-icon" title="��������"> --%>
<!-- 													   <i class="icon">&#xe6fd;</i> -->
<!-- 													</a> -->
												</div>
						
											</div>
						                    <div class="mt20 text-center fc_dark_grey">û���ʺţ�
												<a class="fc_orange-b" href="<%=njxBasePath%>/login.shtml?login=2" >���ע��</a></div>
										</div>
										<!--�ʺ������¼ end-->
		  	                      </div>
		  	 
		                          <div class="tab-pane fade" id="messageLogin">
							     	<!--���ŵ�¼ start-->
									<div class="login">					
										<div class="phoneLogin">
											<div class="mb20 rel">							
												<input onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" class="inputText" type="text" id="mobile" name="mobile" autocomplete="off" placeholder="�������ֻ���" value="">
											</div>
											
											<div class="row  mb20" style="height: 40px;">
												<div class="col-md-7 rel">								
													<input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" id="mobilePCode" class="inputText" type="text" placeholder="��������֤��">
												</div>
					 							<div class="col-md-5 imgVcode">
													<img id="imgVcodeTwo" onClick="getRan()" width="110" height="42">
												</div>
												
											</div>
											
											<div class="message mb20 rel">
												<input onclick="getMsmCoad();" readonly id="getVCodeSpan" class="btn btn-orange btn-half abs" value="��ȡ������֤��"/>
												<input onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'') " ng-pattern="/[\u4e00-\u9fa5]/" style="width: 100%;padding: 10px 20px;line-height: 1.5;border: solid 1px #dfdfdf;border-radius: 30px;background-color: #fff;color: #666;" class="inputText" id="pCode2" name="imgVcode2" type="text" placeholder="�����������֤��">
											</div>
											<div class="btn btn-orange btn-block btn-half mt20" onclick="mobileLogin()">������¼</div>
										</div>
								
										<div class="loginBtn mt10">
											<h1><span>ʹ��������ʽ��¼</span></h1>
											<div class="text-center">
												<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toSinaWeiboLogin.action';" id="wbLoginBtn" class="btn-circle btn-icon50 btn-icon" title="΢����¼" style="margin-right: 16px;">
												   <i class="icon">&#xe724;</i>
												</a>
												<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toWeixinLogin.action';" id="wxLoginBtn" class="btn-circle btn-icon50 btn-icon" title="΢�ŵ�¼" style="margin-right: 16px;">
												   <i class="icon">&#xe716;</i>
												</a>
												<a href="#" onclick="location.href='<%=njxBasePath%>/thirdParty/toTencentLogin.action';" id="qqLoginBtn" class="btn-circle btn-icon50 btn-icon" title="QQ��¼" style="margin-right: 16px;">
												   <i class="icon">&#xe728;</i>
												</a>
<%-- 												<a href="#" onclick="javascript:location.href='<%=njxBasePath%>/thirdParty/toJCloudLogin.action';" id="jCloudLoginBtn" class="btn-circle btn-icon50 btn-icon" title="��������"> --%>
<!-- 												   <i class="icon">&#xe6fd;</i> -->
<!-- 												</a> -->
											</div>
										</div>
				                    	<div class="mt20 text-center fc_dark_grey">û���ʺţ�
											<a class="fc_orange-b" href="<%=njxBasePath%>/login.shtml?login=2">���ע��</a></div>
										</div>
										<!--���ŵ�¼ end-->
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
    <!-- �µ�½�� end -->
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
				
				 //�󶨻س��¼�
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
			//��֤�뵹��ʱ
			var countdown = 120;

			function sendemail() {
				var obj = $("#getVCodeSpan");
				settime(obj);
				obj.css("background",'rgb(196, 196, 196)')

			}
			
			function settime(obj) { //������֤�뵹��ʱ
				if(countdown == 0) {
					obj.attr('disabled', false);
					obj.val("��ѻ�ȡ��֤��");
					countdown = 120;
					return;
				} else {
					obj.attr('disabled', true);
					obj.val("���·���(" + countdown + ")");
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
					$("#new_mfzc").html("<span class='errorText'>�����������ֻ���</span>");
					messageSettime(3);
					return false;
				}
				if(!mobile_filter.test($("#mobile").val())) {
					$("#new_mfzc").html("<span class='errorText'>�ֻ����벻��ȷ��</span>");
					messageSettime(3);
					return false;
				}
				
				$.ajax({
					type:"post",
					url:"${staticResourcePath}/user/events/sendVcodeSMS",
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
    <!--��ʾ��¼�� start-->
    <div id="promptModal" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none; width: 350px; margin-left: -175px;">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">��</button>
            <h3 id="myModalLabel">&nbsp;</h3>
        </div>
        <div class="modal-body align_c" style="overflow: initial;">
            <p class="fz16">�װ���΢�ѣ�����û�е�¼Ŷ~</p>
        </div>
        <div class="modal-footer" style="text-align: center;">
            <a class="btn btn-warning"  href="#loginModal" onclick="getRan()" data-dismiss="modal" data-toggle="modal" aria-hidden="true">ȥ��¼</a>
        </div>
    </div>
    <!--��ʾ��¼�� end-->
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
	            $("#new_mfzc").html("<span class='errorText'>�����������ֻ��ţ�</span>");
	            messageSettime(3);
	            loginFlag = true;
	            return false;
	        }
	        if($.trim($("#password").val()) == '') {
	            $("#new_mfzc").html("<span class='errorText'>�������������룡</span>");
	            messageSettime(3);
	            loginFlag = true;
	            return false;
	        }
	        if($.trim($('#userPCode').val()) == '') {
	            $("#new_mfzc").html("<span class='errorText'>��������֤�룡</span>");
	            messageSettime(3);
	            loginFlag = true;
	            return false;
	        }
	
	        if(!password_filter.test($("#password").val())) {
	            $("#new_mfzc").html("<span class='errorText'>�����ʽ����ȷ��</span>");
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
                $("#new_mfzc").html("<span class='errorText'>�����������ֻ���</span>");
                messageSettime(3);
                loginFlag2 = true;
                return false;
            }
            if($.trim($('#pCode2').val()) == '') {
                $("#new_mfzc").html("<span class='errorText'>��������֤�룡</span>");
                messageSettime(3);
                loginFlag2 = true;
                return false;
            }
            if($.trim($('#mobilePCode').val()) == '') {
                $("#new_mfzc").html("<span class='errorText'>��������֤�룡</span>");
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
                url : "<%=njxBasePath%>/StartCaptchaServlet?t=" + (new Date()).getTime(), // ���������ֹ����
                type : "get",
                dataType : "json",
                success : function(data) {
                    // ʹ��initGeetest�ӿ�
                    // ����1�����ò���
                    // ����2���ص����ص��ĵ�һ��������֤�����֮�����ʹ������appendTo֮����¼�
                    initGeetest({
                        gt : data.gt,
                        challenge : data.challenge,
                        product : "float",
                        offline : !data.success
                    }, handlerEmbed);
                }
            })
            var handlerEmbed = function(captchaObj) {
                // ����֤��ӵ�idΪcaptcha��Ԫ����
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
                        url: "<%=njxBasePath%>/VerifyLoginServlet?t=" + (new Date()).getTime(), // ���ж�����֤
                        type: "post",
                        dataType: "json",
                        data: {
                            // ������֤���������ֵ
                            geetest_challenge: validate.geetest_challenge,
                            geetest_validate: validate.geetest_validate,
                            geetest_seccode: validate.geetest_seccode
                        },
                        success: function (data) {
                            geeTestData = data;
                            userLogin();//��֤���ֱ�ӵ�¼ by lcz
                        }
                    });
                });
            };*/

            $('#loginBtn').click(function() {
            	var i = $("#selectLoginTypeID").val();
				if(i==1){
					//�ֻ��ſ�ݵ�¼
					mobileLogin();
				}else{
					//�û��������½
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

		// ����
	    $(window).scroll(function () {
	        if ($(document).scrollTop() > 57) {
	            $('#head.head-croll').removeClass('head-bg')
	        } else {
	        	 $('#head.head-croll').addClass('head-bg')
	        }
	    })
    </script>
    
    <!--�������� end-->
    <script type="text/javascript">
        var operateLogPageCode = '1001';
        var operateLogPageName = '��¼';
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
    // ɾ����ť
    $('.activity-close').on('click',function () {
        $(this).parent().hide();
    });
    //����ʱ
    $('#countdownTime').countdown('2019/4/24 18:25:00', function(event) {
        var $this = $(this).html(event.strftime(''
            + '<span>%H</span>��'
            + '<span>%M</span>��'
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
                      $("#500").addClass("coupon-btn-red").text("ȥʹ��");
				  }if (y[x].discountCreditCount===300){
                      $("#300").attr("ind",2);
                      $("#300").addClass("coupon-btn-red").text("ȥʹ��");
				  }if (y[x].discountCreditCount===2000){
                      $("#2000").attr("ind",2);
                      $("#2000").addClass("coupon-btn-red").text("ȥʹ��");
				  }if (y[x].discountCreditCount===6000){
                      $("#6000").attr("ind",2);
                      $("#6000").addClass("coupon-btn-red").text("ȥʹ��");
				  } if (y[x].discountCreditCount===9000){
                      $("#9000").attr("ind",2);
                      $("#9000").addClass("coupon-btn-red").text("ȥʹ��");
				  } if (y[x].discountCreditCount===10000){
                      $("#10000").attr("ind",2);
                      $("#10000").addClass("coupon-btn-red").text("ȥʹ��");
				  }
			  }
            },
            error: function () {
                Tips("����ʧ�ܣ�");
            }
        })
    })
    </s:if>
   $(".coupon-price a").on('click',function () {
       <s:if test="#attr.admin==null">
       Tips("����û�е�½�����ȵ�¼����ȡ������")
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
                       Tips("��ȡ�ɹ���");
                   }if(res.code==="30025"){
                       Tips("�����ظ���ȡ�Ż�ȯ��");
                   }
                   if(id==="500"){
                       $("#500").addClass("coupon-btn-red").text("ȥʹ��");
                       $("#500").attr("ind",2);
                   }if(id==="300"){
                       $("#300").addClass("coupon-btn-red").text("ȥʹ��");
                       $("#300").attr("ind",2);
                   }if(id==="2000"){
                       $("#2000").addClass("coupon-btn-red").text("ȥʹ��");
                       $("#2000").attr("ind",2);
                   }if(id==="6000"){
                       $("#6000").addClass("coupon-btn-red").text("ȥʹ��");
                       $("#6000").attr("ind",2);
                   }if(id==="9000"){
                       $("#9000").addClass("coupon-btn-red").text("ȥʹ��");
                       v=2;
                   }if(id==="10000"){
                       $("#10000").addClass("coupon-btn-red").text("ȥʹ��");
                       $("#10000").attr("ind",2);
                   }
               },
               error: function () {
                   Tips("����ʧ�ܣ�");
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