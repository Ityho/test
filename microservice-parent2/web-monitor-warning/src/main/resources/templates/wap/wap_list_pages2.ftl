<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Ĭ�ϰ汾 -->
<html>
<head lang="en">
 <meta charset="gbk">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<title>Ԥ���б�</title>

<link href="css/YJmail.css" rel="stylesheet" type="text/css" />
</head>
 <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
 <script type="text/javascript" src="js/zepto.js"></script>
	
  <script type="text/javascript">
  var isWeiboClient = /weibo/i.test(navigator.userAgent);
        var shouyeHref = "http://www.wrd.cn";
        var appHref = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq';
        $(function(){
        	/* if(isWeiboClient){
				$("#shouyeHref").attr("href","http://apps.weibo.com/3960037780/QoXyelm");
			} */
            var num2= 1;
            $(".more").on("touchstart",function(e){
                   if(num2) {
                        num2--;
                        $(".zhezhao").show();
                        $(".content").show();
                    }else{
                        num2++;
                        $(".zhezhao").hide();
                        $(".content").hide();
                    }
			})
			
			var paramHtml =  $(".mail_Parameter").html();
            if(paramHtml!='��ͬ�����б�'){
            	$("#titleHs").val('');
            }


            if (/weibo/i.test(navigator.userAgent.toLowerCase())) {
                shouyeHref = "http://apps.weibo.com/3960037780/8rXM111J";
                appHref = "d.51wyq.cn";
            }
            if (/micromessenger/i.test(navigator.userAgent.toLowerCase())) {
                shouyeHref = "http://h5.wyq.cn";
            }
			
        });
        
        
        
        
        

    	function goView(cid,num){
    		var platform ;
    		if('${paltform}'!=null&&'${paltform}'!=''&&'${paltform}'!=undefined){
    			platform = '${platform}';
    		}
    		var highLightKeywords = $("#highLightKeywords").val();
    		var arr = new Array();
    		var newStr ="";
    		if(highLightKeywords.indexOf(",")>-1){
    			highLightKeywords = highLightKeywords.substring(0,highLightKeywords.length-1);
    			arr = highLightKeywords.split(",");
    			if(arr.length>0){
    				if(arr.length>1){
    					for(var i=0;i<=arr.length-2;i++){
       						newStr +=arr[i]+"###";
    					}
    				}
    				newStr += arr[arr.length-1];
    			}
    		}
    		newStr = encodeURIComponent(encodeURIComponent(newStr));
    		var url = "conView.action?con.id="+cid+"&paltform="+platform+"&reviewCode=${reviewCode}&repeatNum="+num+"&defaultRepeatNum=${defaultRepeatNum}&highLightKeywords="+newStr;
    		window.open(url,"_self"); 
    	}
    	
    	
    	//��ҳ
    	function GotoPage(i)
    	{	
    		if (document.frmPopWin.page)
    		{
    			document.frmPopWin.page.value = i;
    			subForm();
    		}
    		else{
    			subForm(); 
    		}
    	} 
    	
    	function goZZNewsList(titleHs){
    		$("#titleHs").val(titleHs);
    		subForm();
    	}

    	
    	function subForm(){
    		frmPopWin.action="more.action";
    		frmPopWin.submit();
    	}

          function recordSign(){
              $.ajax({
                  url : "recordSign.action?userId="+$("#userId").val(),
                  type : "get",
                  success : function(result){
                  },
                  complete:function(){
                      location.href=shouyeHref;
                  }
              })

          }

          function recordDown(){
              $.ajax({
                  url : "recordDownApp.action?userId="+$("#userId").val(),
                  type : "get",
                  success : function(result){
                  },
                  complete:function(){
                      location.href=appHref;
                  }
              })
          }

          function jumpLogin(){
              var data = {"userId":$("#userId").val(),"keywordId":$("#keywordId").val()};

              $.getJSON("http://h5.wyq.cn/api/doAddAccessRecords.action?callback=?",
                      data,
                      function(data){
                          if(data.status){
                              console.log(data.status);
                              location.href= shouyeHref;
                          }else{
                              console.log(data.status);
                              console.log(data.msg);
                          }
                      });

          }

    </script>
<body>

 <div class="YJmail"  id="container">

     <!--ͷ������ start-->
     <div class="mail_heard">
         <img src="images/logo.png"  class="logo"/>
         <div class="topRight" >
             <a class="btn" href="javascript:recordSign()" >��ҳ</a>
         </div>
     </div>
     <!--ͷ������ end-->
     <!--banner���� start-->
     <section class="section" onclick="jumpLogin()" style="cursor: pointer;">
         <div class="mail_Banner rel">
             <div class="picBg"></div>
             <div>����Ԥ���������ü����������~
                 ȥ�޸� ></div>
         </div>
     </section>
     <!--banner���� end-->

     <FORM method=post name=frmPopWin action="">
     <input type="hidden" name="reviewCode" id="reviewCode" value="${reviewCode}"/>
     <input type="hidden" name="highLightKeywords" id="highLightKeywords" value="${highLightKeywords}"/>
     <input type="hidden" name="titleHs" id="titleHs" value="${titleHs}"/>
     <input type="hidden" name="paltform" id="paltform" value="${paltform }"/>
     <input type="hidden" name="contentId" id="contentId" value="${contentId }"/>
     <input type="hidden" name="listFlag" id="listFlag" value="${listFlag}"/>
     <input type="hidden" name="repeatNum" id="repeatNum" value="${repeatNum}"/>
     <input type="hidden" name="defaultRepeatNum" id="defaultRepeatNum" value="${defaultRepeatNum}"/>
     <input type="hidden" id="userId" value="${userId}">
     <input type="hidden" id="keywordId" value="${wr.keywordId}">
     <s:if test="#attr.titleHs!=null&&#attr.titleHs!=''">
     	<div class="mail_Parameter">��ͬ�����б�</div>
     </s:if>
     <s:else>
         <!--Ԥ����Ϣ start-->
         <section class="section line">
             <div class="mail_Parameter">
                 <p>�װ��ģ�${userName}</p>
                 <p>&nbsp;&nbsp;&nbsp;&nbsp;��ֹ <span style="font-weight: bold"><s:date name="#attr.wr.createTime" format="MM��dd�� HH:mm"/> </span>������Ԥ������<em style="color: #72ab00">��${wr.keywordName}��</em>
                     ����${total}��Ԥ��,<s:if test="#attr.mgNum==0">δ����������Ϣ</s:if><s:else>����<em>${mgNum}</em>��������Ϣ</s:else> ����
                     <s:if test="#attr.similarCondition==1">�ϲ�</s:if><s:else>���ϲ�</s:else>��ʾ���£�</p>
             </div>
         </section>
         <!--Ԥ����Ϣ end-->
     </s:else>
     
     
     <!--�б����� start-->
     <section class="section">
     <div class="mwbBorder listMinH">
       <ul class="mwblist">
       	 <s:set name="nowTime" value="new java.util.Date()"></s:set>
       	 <s:iterator value="list" id="con" status="st">
	         <li>

                 <a href="${con.webpageUrl}"  target="_blank">
                     <s:if test="#con.customFlag1 == 1||#con.customFlag1 == 2 || #con.customFlag1 == 3">
                         <span class="label label_mg">����</span>
                     </s:if>

                     <div class="mwbHead">
                         <div class="m_l">
                             <s:if test="#con.profileImageUrl!=null&&#con.profileImageUrl!=''">
                                 <img src="${con.profileImageUrl}">
                             </s:if>
                             <s:elseif test="#con.originType=='wb'">
                                 <img src="images/userlogo/app-more-icon-weibo.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='sp'">
                                 <img src="images/userlogo/app-more-icon-video.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='wx'">
                                 <img src="images/userlogo/app-more-icon-weixin.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='xw'">
                                 <img src="images/userlogo/app-more-icon-news.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='lt'">
                                 <img src="images/userlogo/app-more-icon-bbs.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='bk'">
                                 <img src="images/userlogo/app-more-icon-blog.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='app'">
                                 <img src="images/userlogo/app-more-icon-app.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='zw'">
                                 <img src="images/userlogo/app-more-icon-affairs.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='baokan'">
                                 <img src="images/userlogo/app-more-icon-press.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='jw'">
                                 <img src="images/userlogo/app-more-icon-media.jpg">
                             </s:elseif>
                             <s:else>
                                 <img src="images/userlogo/app-more-icon-web.jpg">
                             </s:else>
                         </div>
                         <div class="m_r">
                             <h1>
                                 <s:if test='#con.captureWebsiteName=="����΢��"'>
                                     <s:if test='#con.author!=null&&#con.author!=""'>
                                         ${con.author}
                                     </s:if>
                                     <s:else>
                                         ${con.captureWebsiteName }
                                     </s:else>
                                 </s:if>
                                 <s:else>
                                     ${con.title}
                                 </s:else>
                             </h1>
                             <div class="mr-buttom">
                                 <span id="pdate_<s:property value='#st.index+1'/>">

                                      <script>
                                          <s:set name="pdate"	value="#con.published" />
                                          if ('<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>' =='<s:date name="#pdate" format="yyyy-MM-dd" />'){
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="HH:mm" />');
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend("���� ");
                                          }else if ('<%=new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %>' =='<s:date name="#pdate" format="yyyy" />'){
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="HH:mm" />');
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="M��d��" /> ');
                                          }else {
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="HH:mm" />');
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="yyyy��M��d��" /> ');
                                          }

                                      </script>
                                 </span>
                                 <span>��ͬ���£�<s:if test="#con.repeatNum==0">1</s:if><s:else><s:property value="#con.repeatNum"/></s:else></span>
                                 <span>���ԣ�${con.captureWebsiteName }</span>
                             </div>
                         </div>
                     </div>
                     <div class="mwbCon">
                         <s:if test="#con.originType=='wb'">
                            <div class="txt">
                                 <!-- ת������ԭ΢�� -->
                                 <s:property value="#con.content" escape="false"/>
                            </div>
                             <!-- ԭ΢�� -->
                             <s:if test="#con.forwarderContent!=''&&#con.forwarderContent!=null">
                                 <div class="txt zftxt"><s:property value="#con.forwarderContent" escape="false"/></div>
                             </s:if>

                         </s:if>
                         <s:else>
                             <div class="txt">
                                 <!-- ת������ԭ΢�� -->
                                 <s:property value="#con.summary" escape="false"/>
                             </div>
                         </s:else>
                     </div>
                 </a>
		         
	         </li>
         </s:iterator>
       </ul>
     </div>
     </section>
    <!--�б����� end-->
     <section>
         <div class="footer">
             <s:if test="list!=null">
                 <s:include value="../common/paginated_list_bottom.jsp">
                 </s:include>
             </s:if>
         </div>
     </section>
     <section>
         <div class="footer">
             <a href="javascript:recordDown()">
                 <img class="visible-xs" src="images/bottomPic.jpg"/>
                 <img class="visible-lg" src="images/bottomPic2.jpg"/>
             </a>
         </div>
     </section>
	<%--<!--�����б� start-->
		<s:if test="#attr.titleHs!=null">
    	<a href="javascript:history.go(-1);" title="�����б�"><img src="images/back-64.png"  class="back"></a>
    	</s:if>
    <!--�����б� end-->
    <!--�ײ� start-->
    <div class="footer">
    <s:if test="list!=null">

		<s:include value="../paginated_list_bottom.jsp">
			 
		</s:include>
	</s:if>
		
    </div>--%>

    </FORM>
    <!--�ײ� end-->
 </div>

 <%--cnzzͳ�ƴ���start  --%>
 <div style="display:none;">
     <%--<script type="text/javascript" src="js/cnzz.js"></script>--%>
 </div>
 <%--cnzzͳ�ƴ���end  --%>
</body>
</html>
