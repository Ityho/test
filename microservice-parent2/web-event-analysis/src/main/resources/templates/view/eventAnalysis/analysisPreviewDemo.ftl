
<#include "../../top.ftl">
<%
	String webPath = systemConfig.getSystemWeb();
%>
<html>
<head>
    <!-- saved from url=(0014)index:internet -->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta charset="GBK">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" type="text/css" href="<%=staticResourcePathH5 %>/css/analysis/common.css?v=<%=SYSTEM_INIT_TIME %>"/>
    <link rel="stylesheet" type="text/css" href="<%=staticResourcePathH5 %>/css/frame.css?v=<%=SYSTEM_INIT_TIME %>"/>
    <link href="<%=staticResourcePathH5 %>/css/analysis/style.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet" type="text/css">
    <link href="<%=staticResourcePathH5 %>/css/other.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet" type="text/css">
    <link href="<%=staticResourcePathH5 %>/css/eventAnalysis.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet" type="text/css">
    <link href="<%=staticResourcePathH5 %>/css/report.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet" type="text/css">
    <script src="<%=staticResourcePathH5 %>/js/jquery-1.10.2.min.js?v=<%=SYSTEM_INIT_TIME %>"></script>
    <script src="<%=staticResourcePathH5 %>/js/jquery.chromatable.js?v=<%=SYSTEM_INIT_TIME %>"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/echarts.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/pie.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/bar.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/map.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/line.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/radar.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/wordCloud.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/force.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/tree.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/echarts/chart/funnel.js?v=<%=SYSTEM_INIT_TIME %>" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=staticResourcePathH5%>/js/eventAnalysisPreview.js?v=<%=SYSTEM_INIT_TIME %>"></script>
    <link type="text/css" rel="stylesheet" href="<%=staticResourcePathH5%>/css/popModal.css?v=<%=SYSTEM_INIT_TIME %>">
    <!--����-->
    <script type="text/javascript" charset="utf-8" src="<%=staticResourcePathH5%>/js/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=3&amp;lang=zh"></script>
    <script type="text/javascript" charset="utf-8" src="<%=staticResourcePathH5%>/js/bshareC0.js?v=<%=SYSTEM_INIT_TIME %>"></script>
    <script type="text/javascript" charset="utf-8">
        $(function(){
            var pre = location.href.split("/view")[0];
            var ticket = '${tickets}';
            var title = '${reportName}�¼��������棬�������ھ�����-΢�ȵ�';
            document.title = title;
            var url = pre+"/view/eventAnalysis1/analysisPreview.action?isShare=1&tickets="+encodeURIComponent(ticket);
            var wbStr = "wb.wyq.cn";
            if(pre.split(wbStr).length > 1){//�����Ӧ�÷����h5ҳ�棬�ڵ��Զ˴򿪵Ŀհ׵����(�ֻ�����תh5,���Զ�����ֱ����ת��webҳ��)
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
	              
   		         if(bs.versions.mobile){
   		          	if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.windowsPhone) {
   		          		  pre = "http://h5.51wyq.cn";
   		          		  url = pre+"/view/eventAnalysis1/analysisPreview.action?isShare=1&tickets="+encodeURIComponent(ticket);
   		              }else{
   		            	  pre = "http://wyq.sina.com";
   		             	  url = pre+"/analysisPreview.shtml?isShare=1&shareType=1&tickets="+encodeURIComponent(ticket);
   		               }
   		          }
            	
            }
            //΢����qq,�ռ�����ʽ
            bShare.addEntry({
                title:"[΢�ȵ�]${reportName}",
                summary:"��ȫ���¼��������������ھ�����",
                url: url,
                pic:staticResourcePathH5+"/images/fenxiang/weibowyq-icon300.jpg"
            });
          //΢��
            bShare.addEntry({
                title:"[����@΢�ȵ�]${reportName}��ȫ���¼��������������ھ�����~��������",
                url: url,
                pic:staticResourcePathH5+"/images/fenxiang/weibowyq-icon300.jpg"
            });
            bShare.init();
        })
    </script>
<style id = "style">
        @media only screen and (max-width:600px ) {
            .mobileStyle #top,.mobileStyle #head nav,.mobileStyle #head .nav{display: none;}
            .mobileStyle #head{position: fixed; z-index: 100; top: 0; width: 100%;}
            .mobileStyle #head .logo{margin-left: 10px;}
            .mobileStyle #head .r_btn{right: 15px;}
            .mobileStyle .phoneTopHeight{margin-top: 70px;}
            .mobileStyle #footer,.mobileStyle .h35{display: none;}
            .mobileStyle #head .logo{border: none;}
        }
    </style>

</head>
<style>
#hotNews tr td:first-child{
    text-align:left;
    padding-left: 20px;
}
</style>
<script>
    var phone = false;
    var echarts;
    require.config({
        paths: {
            echarts: '../js/echarts',
        }
    });
    function checkLength(which) {
        iCount = which.value.replace(/[^\u0000-\u00ff]/g,"aa");
        which.size=iCount.length+1;
    }

    $(function(){
        $("#scrollTab").chromatable({
            width: "100%",
            height: "400px",
            scrolling: "yes"
        });

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
                $('.echartAdpter').css('width', '100%');
                phone = true;
                //$(".fenxiang span").hide();
                //$(".fenxiang a[class='icon icon_wx']").hide();
               // $(".fenxiang").addClass("fenxiang2");
               // $(".fenxiang").css("margin-top","10px");
               // $(".fenxiang").removeClass("fenxiang float_l");

               
            }
        }
       // $(".fenxiang,.fenxiang2").show();
        var url = location.href;
		var isApp = document.getElementById("isApp").value;
		var Uid = "${admin}";
		//console.log( Uid);
		if((Uid == "") && bs.versions.mobile){
			var text = $("#style").text().replace("margin-top: 70px;","margin-top: 0px;");
			$("#style").text(text);
			$("#head").css("display","none");
			$("#headText").css("display","none");
			$(function(){
				$(".loginBtn").on("click",function(){
					location.href="<%=njxBasePath%>/indexLocal.action";
				});
				$(".RegistBtn").on("click",function(){
					location.href="<%=njxBasePath%>/register.action";
				});
			});
			
		}else if(bs.versions.mobile && Uid!=""){
		    var ua = navigator.userAgent.toLowerCase();//��ȡ�ж��õĶ���
			if (ua.match(/WeiBo/i) == "weibo") {
				 //������΢���ͻ��˴�
				 //document.querySelector(".RegistBtn").style.display="none";
				 $(".RegistBtn").css("display","none");
				//console.log("������΢���ͻ��˴�");
				//$(function(){
					$(".loginBtn").on("click",function(){
						location.href="http://apps.weibo.com/3960037780/8rXM111J";
					});
				//});
		    }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
		    	 //��΢���д�
		    	//document.querySelector(".RegistBtn").style.display="none";
		    	 $(".RegistBtn").css("display","none");
		    	//console.log("��΢���д�");
		    	//$(function(){
					$(".loginBtn").on("click",function(){
						location.href="<%=njxBasePath%>/weiboHome.action";
					});
				//});
		    }else{
		    	//�ֻ��������
		    	//console.log("�ֻ��������");
			    	//$(function(){
						$(".loginBtn").on("click",function(){
							location.href='<%=njxBasePath %>/view/eventAnalysis/createAnalysis.shtml?createType=1';
						});
						$(".RegistBtn").on("click",function(){
							location.href="<%=njxBasePath%>/register.action";
						});
					//});
	    		}	    
		}else if(bs.versions.mobile){
			var ua = navigator.userAgent.toLowerCase();//��ȡ�ж��õĶ���
			if (ua.match(/WeiBo/i) == "weibo") {
				 //������΢���ͻ��˴�
				 $(".RegistBtn").css("display","none");
				//console.log("������΢���ͻ��˴�");
				//$(function(){
					$(".loginBtn").on("click",function(){
						location.href="http://apps.weibo.com/3960037780/8rXM111J";
					});
				//});
		    }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
		    	 //��΢���д�
		    	$(".RegistBtn").css("display","none");
		    	//console.log("��΢���д�");
		    	//$(function(){
					$(".loginBtn").on("click",function(){
						location.href="<%=njxBasePath%>/weiboHome.action";
					});
				//});
		    }else{
		    	//�ֻ��������
		    	//console.log("�ֻ��������");
		    	//$(function(){
					$(".loginBtn").on("click",function(){
						location.href="<%=njxBasePath%>/indexLocal.action";
					});
					$(".RegistBtn").on("click",function(){
						location.href="<%=njxBasePath%>/register.action";
					});
				//});
	    		}	    
		}else{
			//pc�д�
			//$(function(){
				$(".loginBtn").on("click",function(){
					location.href="<%=webPath%>logon.shtml";
				});
				$(".RegistBtn").on("click",function(){
					location.href="<%=webPath%>user/goRegister.shtml";
				});
			//})
		}
    });
</script>
<body class="mobileStyle">
<img src="<%=staticResourcePathH5 %>/images/case-analysis-n.png?v=<%=SYSTEM_INIT_TIME %>" style="width: 100%;">
<input value="${isApp}" type="hidden" id = "isApp">
	<%-- <div id="head" class="rel" <s:if test = "#attr.isApp == 1">style = 'display:none;'</s:if>>
     <span class="logo abs"></span>
      <div class="nav abs">
         <a href="<%=webPath%>login.shtml" <s:if test='currentPage=="shouye"'> class="active"</s:if>>��ҳ</a>
         <a href="<%=webPath%>product.shtml" <s:if test='currentPage=="jieshao"'> class="active"</s:if> >��Ʒ����</a>
         <a href="<%=webPath%>novice.shtml" <s:if test='currentPage=="novice"'> class="active"</s:if> >��������</a>
         <a href="<%=webPath%>help.shtml" <s:if test='currentPage=="help"'> class="active"</s:if> >��������</a>
         <a href="<%=webPath%>downLoad.shtml" <s:if test='currentPage=="download"'> class="active"</s:if> >�ͻ�������</a>
      </div>
      <span class="r_btn abs"><a class="loginBtn">��ҲҪ����</a></span>
   </div>
    <div class="textShow" style="" id="headText"></div> --%>
   <!--  
           <div class="row-fluid">
            <div class="reportTit">
                <div class="textShow" style="margin-top:25px;" id="headText">
                    <h1>${reportName}�¼���������</h1>
                </div>
            </div>
        </div>-->
<!-- app����ʱ�ṩͼƬ -->
<div id='wx_pic' style='margin:0 auto;display:none;'>
    <img src='<%=njxBasePath %>/images/wxfx-pic4.jpg' />
</div>

<div class="page-container reportWeb rel" style="width: 1200px; margin: auto;">
<ul id="nav2">
    <li class="current"><a href="#pr1">�¼����</a></li>
    <li><a href="#pr2">�¼�����</a></li>
    <li><a href="#pr3">��վͳ��</a></li>
    <li><a href="#pr4">��������</a></li>
    <li><a href="#pr5">�ؼ�����</a></li>
    <li><a href="#pr6">������Ϣ</a></li>
    <li><a href="#pr7">�ȵ�����</a></li>
    <li><a href="#pr8">����·��</a></li>
    <li><a href="#pr9">��ش�</a></li>
    <li><a href="#pr10">����۵�</a></li>
    <li><a href="#pr11">�ȵ��ܽ�</a></li>
</ul>

<!--������� start-->
<div class="reportPreview">
<s:if test = 'isApp==1'>
    <div class="logo"style="margin-left:5px;"><img src="<%=njxBasePath %>/images/logo_analysis.png" width="120"></div>
</s:if>
	<%-- <div id="head" class="rel" <s:if test = "#attr.isApp == 1">style = 'display:none;'</s:if>>
     <span class="logo abs"></span>
      <span class="r_btn abs"><a class="loginBtn">��ҲҪ����</a></span>
   </div> --%>
    <div class="reportCon">
        <!--������� start-->
        <div class="row-fluid">
           <%--  <div class="reportTit">
                <div class="textShow">
                    <h1>${reportName}�¼���������</h1>
                </div>
            </div> --%>
            
            <div class="con Report-header">
                   <div style="width:100%;height:65px;">
                       <div class="info" style="background-color:#FFF;">
	                    	<div class="float_l align_l" style="width:100%;font-weight:bold;">
	                        	<p>${reportName}�¼���������</p>                        
	                    	</div>
	                    	<div class="float_l">
	                    		<img style="height:20px;width:20px;" src="<%=staticResourcePathH5 %>/images/daily/report.jpg?v=<%=SYSTEM_INIT_TIME %>">
	                    		<span style="margin-left:10px;">΢�ȵ�</span>
	                    		<img style="height:15px;width:15px;" src="<%=staticResourcePathH5 %>/images/daily/v_blue.png?v=<%=SYSTEM_INIT_TIME %>">
	                    	</div>
	                    	<div style="margin-left:20px;float:left;color:#a6a6a6;weight:1px;line-height: 22px;height: 22px;font-size: 14px;">|</div>
	                    	<div class="float_l align_l" style="margin-left:20px;line-height: 22px;height: 22px;">
	                        	<span>${reportTime1}</span>
	                    	</div>
                   			<div class="clear"></div>
               			</div>
                   </div>
            </div>
        </div>
        
        
          			<%-- 
     <s:if test = 'isApp!=1'>
        <div class="row-fluid">
            <div class="fenxiang float_l" style="display: none">
     
                <span>������</span>
                <a class="icon icon_wb" title="����΢��" href="javascript:void(0);" onclick="javascript:bShare.share(event,'sinaminiblog',0);return false;"></a>
                <a class="icon icon_wx" title="΢��" href="javascript:void(0);" onclick="javascript:bShare.share(event,'weixin',1);return false;"></a>
                <a class="icon icon_pyq" title="����Ȧ" href="#"></a>
                <a class="icon icon_qq" title="QQ����" href="javascript:void(0);" onclick="javascript:bShare.share(event,'qqim',0);return false;"></a>
                <a class="icon icon_qqkj" title="QQ�ռ�" href="javascript:void(0);" onclick="javascript:bShare.share(event,'qzone',0);return false;"></a>

               <div class="bshare-custom">
                    <a title="��������΢��" class="bshare-sinaminiblog"></a>
                    <a title="����΢��" class="bshare-weixin"></a>
                    <a title="����QQ����" class="bshare-qqim"></a>
                    <a title="����QQ�ռ�" class="bshare-qzone"></a>
                    <a title="����ƽ̨" class="bshare-more bshare-more-icon more-style-addthis"></a>
                    <span class="BSHARE_COUNT bshare-share-count">0</span>
                </div>
          
            </div>

        </div>
 </s:if>--%>
        <!--������� end-->
        <div class="reportBox">

            <!--�¼���� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr1">
                        <h2>�¼����</h2>
                    </div>
                </div>

                <div class="textShow">
                    <div class="text">
                        <div class="textCon" id="introduce">
                            <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px">
                        </div>
                    </div>

                </div>
                <div class="editBox">
      			<textarea class="inputList" id="textarea3">������Ͳɼ������½��з�������2016��1��21�յ�2016��2��20���������������ϵ�������۽���������ط�����������߷������2016��1��28�գ�����30ƪ������ۡ����·����ؼ���Χ�ƺ��ڡ��������ݡ�׹�����ڷ���ʱ�䷶Χ�ڣ�����55ƪ������ۡ������������2016��1��21�շ������й���������Ϊʵ�ģ�����С��ס���������ݽ�3Сʱ��׹��������������Ҫ�����ھ��ڣ��������͵����������࣬��Ҫ��Դ���Ѻ��������������������ٶȡ��й��������ȼ���վ�㡣������˵�������¼���չ���ƽ�Ϊƽ������ϸ�������¡�
      			</textarea>

                </div>

            </div>
            <!--�¼���� end-->

            <!--�¼����� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr2">
                        <h2>�¼�����</h2>
                    </div>
                </div>
                <div class="yb2 rel" id="eventProfile">
                    <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px">
                </div>
            </div>
            <!--�¼����� end-->

            <!--��վͳ�� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr3">
                        <h2>��վͳ��</h2>
                        <!-- 
                        <span class="rightbtn rightbtn2">
                            <button onclick="javascript:return false;" style="margin-top:-10px;"id="notifyModal_ex1"  class="hintModal"><img src="<%=njxBasePath%>/images/querybtn.png"><div class="hintModal_container">ʱ�����ȫ����Դ��Ϣ�ķ�ʱͳ��</div></button>
                        </span> -->
                    </div>
                </div>

                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder">
                                <div class="mwbcon rel">
                                    <div id="container1" class="echartAdpter" style="height: 400px;">
                                        <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px">
                                    </div>
                                </div>
                                <div class="text"></div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
            <!--��վͳ�� end-->

            <!--�������� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr4">
                        <h2>��������</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="row-fluid">
                                <div class=" mwb5 float_l">
                                    <div class="mwbBorder">
                                        <h2><div style="float:left;width:20%;">��з���</div>
                                         <!--  
                                        <span class="rightbtn rightbtn2">
                                            <button style="margin-top:-8px;"onclick="javascript:return false;" id="notifyModal_ex1"  class="hintModal"><img style="width:20px" src="<%=njxBasePath%>/images/querybtn.png"><div class="hintModal_container">ʱ��������кͷ�������Ϣ��ռ��</div></button>
                                        </span>--></h2>
                                        <div class="mwbcon">
                                            <div id="container2" class="echartAdpter" style="height: 300px;">
                                                <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class=" mwb5 float_r">
                                    <div class="mwbBorder">
                                        <h2>������ֲ�</h2>
                                        <div class="mwbcon">
                                            <div id="container3"  class="echartAdpter"  style="height: 300px;">
                                                <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class=" mwb5 float_l">
                                    <div class="mwbBorder">
                                        <h2>ý����Դռ��</h2>
                                        <div class="mwbcon mwbcon2">
                                            <div id="container4">
                                                <ul class="circularChart">
                                                    <li class="c1">
                                                        <p>������ý�屨��</p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c2">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c3">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c4">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c5">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c6">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c7">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c8">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c9">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c10">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c11">
                                                        <p></p>
                                                        <p></p>
                                                    </li>
                                                    <li class="c12">
                                                        <p></p>
                                                        <p></p>
                                                    </li>

                                                </ul>

                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class=" mwb5 float_r">
                                    <div class="mwbBorder">
                                        <h2><div style="float:left;width:25%;">ý���Ծ��</div>
                                        <!--  
                                        <span class="rightbtn rightbtn2">
                                            <button style="margin-top:-8px;"onclick="javascript:return false;" id="notifyModal_ex1"  class="hintModal"><img style="width:20px" src="<%=njxBasePath%>/images/querybtn.png"><div class="hintModal_container">ʱ����ڷ���������ǰʮ��ý��</div></button>
                                        </span>--></h2>
                                        <div class="mwbcon mwbcon2">
                                            <div id="container5" class="echartAdpter"  style="height: 410px;">
                                                <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="mwbBorder">
                                    <h2>����ֲ�ͼ</h2>
                                    <div class="mwbcon rel">
                                        <div id="container6" class="echartAdpter"  style="width: 550px;height: 468px;float: left;padding-left: 20px;">
                                            <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px">
                                        </div>
                                        <div class="info-section">
                                            <table id="scrollTab" border="" cellspacing="" cellpadding="" class="map_table map_table2">
                                                <thead>
                                                <tr>
                                                    <th width="48%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ʡ��</th>
                                                    <th width="">��Ϣ��</th>
                                                </tr>
                                                </thead>
                                                <tbody id="c6_tb">

                                                </tbody>
                                            </table>
                                            <%--<div class="map_table_pr">��ע����ʾ����ǰʮ��ʡ�ݣ�</div>--%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--�������� end-->
            <!--�ؼ����� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr5">
                        <h2>�ؼ�����</h2>
                       <%-- <div class="tool toolhover abs">
                            <button href="#" id="edit" class="button button4">
                                <i class="icon-edit"></i>
                                �༭</button>
                            <button href="#" class="button button4">
                                <i class="icon-dele dele"></i>
                                ɾ��</button>
                        </div>--%>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder">
                                <div class="mwbcon rel">
                                    <div id="container7" class="echartAdpter"  style="height: 410px;">
                                        <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <!--�ؼ����� end-->
            <!--������Ϣ start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr6">
                        <h2>������Ϣ</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <table border="0" cellspacing="0" cellpadding="0" class="table table2 float_l">
                                <thead>
                                <tr>
                                    <th width="">����</th>
                                    <th width="20%">��Դվ��</th>
                                    <th width="10%" id="hotTime">ʱ��</th>
                                    <th width="15%">ת����</th>
                                </tr>
                                </thead>
                                <tbody id="hotNews">
                                    <tr>
                                        <td><img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;"> </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                
            </div>
            <!--������Ϣ end-->
            <!--�ȵ����� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr7">
                        <h2>�ȵ�����</h2>
                    </div>
                </div>
                <div class="">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder mwbBorder2">
                                <div class="yq_con yqtabs tabsBorder">
                                    <ul class="tab_menu clear">
                                        <li class="current">ȫ��</li>
                                        <li>΢��</li>
                                        <li>��̳</li>
                                        <li>����</li>
                                    </ul>
                                    <div class="tab_box" id="hotPeople">
                                        <div>
                                            <td><img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;"> </td>
                                        </div>
                                        <div class="hide">

                                        </div>
                                        <div class="hide">

                                        </div>
                                        <div class="hide">

                                        </div>
                                    </div>
                                    <div id="peopleTxt" class="text2" style="margin:10px;margin-top: 20px;text-indent:0px;">
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>

            </div>
            <!--�ȵ����� end-->
            <!--����;�� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr8">
                        <h2>����;��</h2>
                        <!-- 
                        <span class="rightbtn rightbtn2">
                            <button onclick="javascript:return false;" style="margin-top:-10px;"id="notifyModal_ex1"  class="hintModal"><img src="<%=njxBasePath%>/images/querybtn.png"><div class="hintModal_container">ʱ����ڷ�΢����Ϣ�Ĵ����켣ͼ</div></button>
                        </span>-->
                    </div>
                </div>

                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder">
                                <div class="mwbcon rel">
                                    <div id="container8" class="echartAdpter"  style="height: 600px;">
                                        <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;">
                                    </div>
                                </div>
                             
                                <div id="pathTxt" style="background-color:#FFF;margin:10px;"class="text">
                                </div> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--����;�� end-->
            <!--��ش� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr9">
                        <h2>��ش�</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="mwbBorder">
                                <div class="mwbcon rel">
                                    <div id="container9" class="echartAdpter"  style="height: 410px;">
                                        <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--��ش� end-->
            <!--����۵� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr10">
                        <h2>����۵�</h2>

                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon">
                            <div class="row-fluid">
                                <div class="mwbBorder">
                                    <h2>���Ź۵����</h2>
                                    <div class="mwbcon">
                                        <div id="container10" class="echartAdpter"  style="height: 410px;">
                                            <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;">
                                        </div>
                                        <div class="viewpoint" id="container10List">
                                        
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="mwbBorder">
                                    <h2>��̳�۵����</h2>
                                    <div class="mwbcon">
                                        <div id="container11" class="echartAdpter"  style="height: 410px;">
                                            <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;">
                                        </div>
                                        <div class="viewpoint" id="container11List">
                                        
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="mwbBorder">
                                    <h2>΢���۵����</h2>
                                    <div class="mwbcon">
                                        <div id="container12" class="echartAdpter"  style="height: 600px;">
                                            <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;">
                                        </div>
                                        <div class="viewpoint" id="container12List">
                                        
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
            <!--����۵� end-->
            <!--�ȵ��ܽ� start-->
            <div class="row-fluid">
                <div class="textShow">
                    <div class="tit" id="pr11">
                        <h2>�ȵ��ܽ�</h2>
                    </div>
                </div>
                <div class="textShow">
                    <div class="text chart">
                        <div class="textCon" id="summary" style="margin:10px;">
                            <img src="<%=njxBasePath %>/images/loading_c.gif" style="width: 15px;height: 15px;">
                        </div>
                    </div>
                </div>

            </div>
            <!--�ȵ��ܽ� end-->
            <%-- 
            <s:if test = 'isApp!=1'>
            <div class="row-fluid">
                <div class="fenxiang float_l" style="display: none">
                    <span>������</span>
                    <a class="icon icon_wb" title="����΢��" href="javascript:void(0);" onclick="javascript:bShare.share(event,'sinaminiblog',0);return false;"></a>
                    <a class="icon icon_wx" title="΢��" href="javascript:void(0);" onclick="javascript:bShare.share(event,'weixin',1);return false;"></a>
                    <a class="icon icon_pyq" title="����Ȧ" href="#"></a>
                    <a class="icon icon_qq" title="QQ����" href="javascript:void(0);" onclick="javascript:bShare.share(event,'qqim',0);return false;"></a>
                    <a class="icon icon_qqkj" title="QQ�ռ�" href="javascript:void(0);" onclick="javascript:bShare.share(event,'qzone',0);return false;"></a>
                </div>
            </div>
            </s:if>--%>
        </div>

    </div>
    <s:if test = 'isApp!=1'>
		<div class="clear" style="height: 35px;"></div>
		 <s:if test="#parameters.platform == '' || #parameters.platform ==null">
		 	<section id = "appdownload" class="section" style="margin-bottom: 0px;bottom:0px;left:0;z-index: 50;width: 100%;position: fixed;background-color: rgba(0,0,0,0.6);">
				<div style="height:45px;">
					<div style="position: absolute;top:12px;left:10px;height:100%;right:0%;color:#FFF;font-size:16px;"></div>
					<div class = "subBtn" style="background-color:#fd9237;border-radius:5px;position: absolute;top:8px;font-size:14px;text-align:center;line-height:30px;width: 60%;height:30px;right:20%;color:#FFF;cursor:pointer;" onClick="goSubscribe();">��ҲҪ����</div>
				</div>
			</section>
		</s:if>
	</s:if>
</div>
</div>
<!--������� end-->
<script type="text/javascript">saveOperateLog('�¼������鿴','1013');</script> 
<script src="<%=staticResourcePathH5 %>/js/jquery.nav2.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<script src="<%=staticResourcePathH5 %>/js/jquery.tabs.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<script src="<%=staticResourcePathH5%>/js/popModal.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<script>
    $(function(){
        $('#nav2').onePageNav();
        $('.yqtabs').Tabs({
            event:'click'
        });
        //��ʾ/���ر༭��
       /* $(".row-fluid .textShow .tool.toolhover #edit").on("click",function(){
            var $this = $(this);
            if($this.parents(".row-fluid").hasClass("hover")){
                $this.parents(".row-fluid").removeClass("hover")
            } else{
                $(".row-fluid").removeClass("hover");
                $this.parents(".row-fluid").addClass("hover");
            }
        });*/

        //�¼�������ʾ/���ر༭��
       /* $(".row-fluid .yb .tool.toolhover #edit").on("click",function(){
            var $this = $(this);
            if($this.parents(".row-fluid").hasClass("hover")){
                $this.parents(".row-fluid").removeClass("hover")
            } else{
                $(".row-fluid").removeClass("hover");
                $this.parents(".row-fluid").addClass("hover");
            }
        });*/



        $(".row-fluid .editBox .tool.tooledit #save").on("click",function(){
            var $this = $(this);
            if($this.parents(".row-fluid").hasClass("hover")){
                $this.parents(".row-fluid").removeClass("hover")
            } else{
                $(".row-fluid").removeClass("hover");
                $this.parents(".row-fluid").addClass("hover");
            }
        });

        //ͳ��ͼ��ʽѡ��Ч��
        $(".chartChoice >ul>li").on("click",function(){
            var $this = $(this);
            $this.siblings("li").removeClass("clicked");
            $(this).addClass("clicked");

        });

    });
</script>

<script type="text/javascript">
    /*----���ú���star----*/
    var ie = !!window.attachEvent && !window.opera;
    var ie9 = ie && (!!+"\v1");
    var inputhandler = function(node,fun){
        if("oninput" in node){
            node.oninput = fun;
        }else{
            node.onpropertychange = fun;
        }
        if(ie9) node.onkeyup = fun;
    }

    /*----���ú���end---*/
    var main = document.getElementById("textarea3");
    inputhandler(main,function(){
        if(!ie) main.style.height = 48+"px";
        var height = main.scrollHeight;if(height>=48){
            main.style.height = height+"px";
        }else{
            main.style.height = 48+"px";
        }
    });
</script>
<script type="text/javascript">
    function sendPost(param,url,callback){
        $.ajax({
            url : url,
            type : "post",
            data : param,
            success : function(data){
                callback(data);
            },
            error:function(jqXHR, textStatus, errorThrown){
            	/*����jqXHR�������Ϣ*/
                //alert(jqXHR.responseText);
                //alert(jqXHR.status);
                //alert(jqXHR.readyState);
                //alert(jqXHR.statusText);
                /*��������������������Ϣ*/
                //alert(textStatus);
                //alert(errorThrown);
                sendPost(param,url,callback);
            }
        })
    };
    
    function goSubscribe(){
    	if("${admin}"){
    		location.href = actionBase+"/createAnalysis?createType=1";
    	}else{
            location.href = actionBase+"/analysis";
    	}
    }

    
    
    $(function(){
//    	//console.log('${tickets}');
//    	//console.log('${reportTime}');
         var params = {"tickets":'${tickets}',"reportTime":'${reportTime}'};
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/eventProfile.action",profileCallback);
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/sitesStatistics.action",LineCallBack);
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/dataType.action",QGPieAdapter);
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/mediaMap.action",mediaCallback);
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/wordCloud.action",cloudCallback);
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/hotNews.action",hotNewsCallback);
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/propagationPath.action",pathCallback);
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/relatedWord.action",relatedWordCallback);
         sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/typicalViews.action",typicalPieAdapter);
        sendPost(params,"<%=njxBasePath %>/view/eventAnalysisAjax/hotPeople.action",hotPeopleCallback);
    });
</script>
<%@ include file="../../buttom.jsp" %>
</body>
</html>