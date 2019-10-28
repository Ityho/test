<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ include file="/top.jsp" %>
<style>
        @media screen and (min-width: 320px){
           /*  .formtext>div{height: 50px!important;line-height: 50px!important;} */
            .formtext div input{height: 30px!important;line-height: 30px!important;}
            .formtext div .yzmBtn1{height: 20px!important;line-height: 20px!important;}
            .formtext div .yzblq{height: 30px!important;line-height: 30px!important;}
        }
        @media screen and (min-width: 375px){
           /*  .formtext>div{height: 60px!important;line-height: 60px!important;} */
            .formtext div input{height: 35px!important;line-height: 35px!important;}
            .formtext div .yzmBtn1{height: 25px!important;line-height: 25px!important;}
            .formtext div .yzblq{height: 35px!important;line-height: 35px!important;}
        }
        @media screen and (min-width: 414px){
           /*  .formtext>div{height: 65px!important;line-height: 65px!important;} */
            .formtext div input{height: 40px!important;line-height: 40px!important;}
            .formtext div .yzmBtn1{height:30px!important;line-height: 30px!important;}
            .formtext div .yzblq{height: 40px!important;line-height: 40px!important;}
        }
    </style>
</head>
<body>
 	<div id="container">
        <section style="">
            <img src="<%=staticResourcePathH5%>/images/stretch/head1.jpg?v=<%=SYSTEM_INIT_TIME %>" style="width: 100%;">
        </section>
        <section style="text-align: center;position: relative;">
            <img src="<%=staticResourcePathH5%>/images/stretch/background2.jpg?v=<%=SYSTEM_INIT_TIME %>" width="100%">
            <form name="frm" method="post">
            <div style="position: absolute;top:45%;height: 55%;width:100%;">
                <div style="padding: 0px 40px;height:100%;" class = "formtext">
                    <div style="padding:8px 0px;text-align: center;width: 100%;">
                        <input id = "mobile" name="user.mobile" style="line-height: 30px;height: 30px;width: 100%;border-radius: 5px;text-indent: 10px;border:0;" placeholder="�������ֻ���">
                    </div>
                    <div style="padding:8px 0px;overflow: hidden;width: 100%">
                        <div style="float: left;height: 100%;width: 63%;">
                            <input id="authcode" name="authcode" style="line-height: 30px;height: 30px;width: 100%;border-radius: 5px;text-indent: 10px;border:0;" placeholder="��֤��">
                        </div>
                        <div style="float: right;height: 100%;width: 35%;">
                            <a id = "getVCodeSpan" href = "javascript:getMsmCoad()" class="yzmBtn1" style="display:block;background: #FCD94F;height: 20px;line-height: 20px;border-radius: 5px;color: #fff;padding: 5px 10px;margin:auto 0px;">��ȡ��֤��</a>
                        </div>
                    </div>
                    <div style="padding:8px 0px;text-align: center;">
                        <a href = "javascript:doStretch()" class="yzblq" style="display: block;width: 100%;height: 32px;background: #FBB741;text-align: center;line-height: 32px;border-radius: 5px;color: #fff;margin: 0px auto;">��֤����ȡ</a>
                    </div>
                </div>
            </div>
            </form>
        </section>
         <section id="new_mfzc" style="background: #E45744;height: 50px;line-height: 25px;color: #fff;text-align: center;margin-top: -15px;padding: 10px 0px;"> </section>
        <section style="background: #F5F5F5;padding:20px 0px;">
            <img src="<%=staticResourcePathH5%>/images/stretch/footer.png?v=<%=SYSTEM_INIT_TIME %>" style="width: 40%;margin: 0px auto;display: block;">
        </section>

    </div>
    <script type="text/javascript">
    var mobile_filter  = /^1[3|4|5|7|8|9][0-9]\d{8}$/;
    function doStretch(){
		if($("#mobile").val() == '') {
    		$("#new_mfzc").text("�������ֻ����룡");
    		return false;
    	}
    	if(!mobile_filter.test($("#mobile").val())) {
    		$("#new_mfzc").text("�ֻ����벻��ȷ!");
    		return false;
    	}
    	
    	if($("#authcode").val() == '') {
    		$("#new_mfzc").text("��������֤��!");
    		return false;
    	}
    	
    	$.ajax({
			type:"post",
			url:actionBase+"/user/checkStretchMobile",
			data:{
				'user.mobile':$("#mobile").val()
			},
			cache:false,
			success: function(data){
				if(data == 2) {
    				$("#new_mfzc").html("�Բ�����û�б�����");
    				return false;
    			}else if(data == 3){
    				$("#new_mfzc").html("��ӭ������������ȡ100000΢����");
    				return false;
    			}else if(data == 4){
			    	 $("#new_mfzc").text("");
			    	 
			    	 $.ajax({
			 			type:"post",
			 			url:actionBase+"/view/user/checkStretchVcode.action?user.mobile="+$("#mobile").val(),
			 			data:{authcode:$("#authcode").val()},
			 			cache:false,
			 			success: function(data){
			 				if(data==1){
			 					if(bs.versions.mobile){
			 						var ua = navigator.userAgent.toLowerCase();//��ȡ�ж��õĶ���
		 							if (ua.match(/WeiBo/i) == "weibo") {
	 										location.href = "http://apps.weibo.com/3960037780/8rXM111J";
		 						    }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
	 										location.href = njxBasePath + "/user/goComplimentaryPointsResult";
		 						    }else{
		 						    	document.frm.action = njxBasePath + "/user/doRegister";
					 	                document.frm.submit();
		 						    }
			 					}
			 			}else if(data==3){
			 					 $("#new_mfzc").text("��֤����ʧЧ�������»�ȡ!");
			 					 if(wait != 120)
			 						 wait = 0;
			 				}else{
			 					$("#new_mfzc").text("��֤�벻��ȷ������������!");
			 				}
			 			},
			 			error:function(data){
			 				$("#new_mfzc").text("��֤�벻��ȷ������������!");
			 			}
			 		});
			     }
			}
		});
    }
  //������֤���·�
    var wait = 120; 
    function time() { 
    	if (wait == 0) { 
    		$('#getVCodeSpan').text('��ȡ��֤��');
    		$('#getVCodeSpan').attr('onclick', 'getMsmCoad()');
    		wait = 120; 
    	} else { 
    		$('#getVCodeSpan').text(wait + ' ��');
    		$('#getVCodeSpan').attr('onclick', '');
    		wait--; 
    		setTimeout(function() { 
    			time();
    		}, 1000); 
    	} 
    }
    function getMsmCoad(){
    	if($("#mobile").val() == '') {
    		$("#new_mfzc").text("�������ֻ����룡");
    		return false;
    	}
    	if(!mobile_filter.test($("#mobile").val())) {
    		$("#new_mfzc").text("�ֻ����벻��ȷ��");
    		return false;
    	}
    	
    	$.ajax({
    		type:"post",
    		url:actionBase+"/view/user/checkStretchMobile.action",
    		data:{
    			'user.mobile':$("#mobile").val()
    		},
    		cache:false,
    		success: function(data){
    			if(data == 2) {
    				$("#new_mfzc").html("�Բ�����û�б�����");
    				return false;
    			}else if(data == 3){
    				$("#new_mfzc").html("��ӭ������������ȡ100000΢����");
    				return false;
    			} else if(data == 4) {
    				$('#new_mfzc').text("");
    				
    				$.ajax({
    					type:"post",
    					url:actionBase+"/view/user/sendStretchVcode.action",
    					data:{
    						'mobile':$("#mobile").val()
    					},
    					cache:false,
    					success: function(data, textStatus){
    						if (data == -1) {
    							$('#new_mfzc').text("��֤�벻��ȷ�����������룡");
    							getRan();
    						} else if(data == 1) {
    							$('#new_mfzc').text("������֤���ѳɹ����͵������ֻ����뾡�����룡");
    							time();
    						} else if(data == 3)
    							$('#new_mfzc').text("���յ��Ķ�����֤����δ���ڣ����Ժ����ԣ�");
    						else if(data == 4)
    							$('#new_mfzc').text("�����Ͷ��Ź���Ƶ�������Ժ����ԣ�");
    						else if(data == 5)
    							$('#new_mfzc').text("�����շ��Ͷ��������Ѵ����ޣ����������ԣ�");
    						else
    							$('#new_mfzc').text("����ʧ�ܣ����ٴλ�ȡ��");
    						
    					},
    					error:function(data){}
    				});
    			}
    		}
    	});
    }

    </script>
</body>
</html>