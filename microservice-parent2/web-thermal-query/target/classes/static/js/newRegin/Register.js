
var mobile_data=0;
function doRegister(){
	 if(mobile_data==1){
            return false;
		 }
	 //var mail=$("#email").val();
	 var password1=$("#password1").val();
	 var password2=$("#password2").val();
	 $("#password3").val(password1);
	 var password3=$("#password3").val();
	 var mobile=$("#mobile").val();
	 var authcode=$("#authcode").val();
	 if(mobile==''){
		 //alert('�������ֻ���!');
		 $("#mobile_show_info").text("�������ֻ���!");
		 return false;
     }
	 if(password1==''){
		 //alert('����������!');
		 $("#password_show_info").text("����������!");
		 return false;
     }
	 if(password1!=password2){
		 //alert('��������벻һ��!');
		 $("#show_info").text("������������벻һ��!");
		 return false;
     }
	
	 if(authcode==''){
		 //alert('��������֤��!');
		 $("#authcode_show_info").text("��������֤��!");
		 return false;
     }
	 var mail_filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	 var mobile_filter  = /^(((1[0-9]{1}[0-9]{1}))+\d{8})$/;
	 //if (!mail_filter.test(mail)){ 
	 //	 alert('���ĵ����ʼ���ʽ����ȷ!');
	//	 return false;
	// }
	 if (!mobile_filter.test(mobile)){ 
		 //alert('�����ֻ������ʽ����ȷ!');
		 $("#mobile_show_info").text("�����ֻ������ʽ����ȷ!");
		 return false;
	 }

	 $.ajax({
			type:"post",
			url:"/view/user/checkRegVcode.action?user.mobile="+mobile,
			data:{authcode:$("#authcode").val()},
			cache:false,
			success: function(data){
				//alert(data);
				if(data==1){
					//alert($("#password").val());
					$("#password3").val(hex_md5($("#password3").val()));
					if(password1.length<=6){
                        $("#passwordStrength").val(1);
				     }
					if(password1.length>6){
                        $("#passwordStrength").val(2);
				     }
					if(password1.length>8){
                        $("#passwordStrength").val(3);
				     }
					document.frm.action="/view/user/doRegister.action?username="+mobile;
	                document.frm.submit();
				}else if(data==2){
					//alert("�ú����Ѿ����ڣ���ֱ�ӵ�¼!");
					 $("#mobile_show_info").html("�ú����Ѿ����ڣ���ֱ��<a href='/logon.action'>��¼</a>");
				}else{
					//alert("��֤�벻��ȷ�����������!");
					$("#authcode_show_info").text("��֤�벻��ȷ������������!");
				}
			
			},
			error:function(data){
				//alert("��֤�벻��ȷ�����������!");
				$("#authcode_show_info").text("��֤�벻��ȷ������������!");
			}
		});
	//document.frm.action="/view/user/doRegister.action";
	//document.frm.submit();
}
//������֤���·�
var wait=90; 
function time(o) { 
	  if (wait == 0) { 
		  o.removeAttribute("disabled");           
		  o.value="��ȡ��֤��"; 
		  wait = 90; 
	  } else { 
		  o.setAttribute("disabled", true); 
		  o.value=wait+"���������»�ȡ"; 
		  wait--; 
		  setTimeout(function() { 
			  time(o);
		  }, 
		  1000); 
	  } 
}

function getMsmCoad(obj){
  	var me = obj;


  	 var mobile_filter  = /^(((1[0-9]{1}[0-9]{1}))+\d{8})$/;
	 var mobile=$("#mobile").val();

     if(mobile==''){
    	 $("#mobile_show_info").text("�������ֻ���!");
    	 return false;
     }
	 if (!mobile_filter.test(mobile)){ 
		 //alert('�����ֻ������ʽ����ȷ!');
		 $("#mobile_show_info").text("�ֻ�����λ������!");
		 return false;
	 }
	
	 $.ajax({
			type:"post",
			url:"/view/user/checkRegVcode.action?user.mobile="+$("#mobile").val(),
			data:{authcode:$("#authcode").val()},
			cache:false,
			success: function(data){
				//alert(data);
				if(data==2){
					//alert("�ֻ��ű�ռ�ã����������!");
					 $("#mobile_show_info").html("�ú����Ѿ����ڣ���ֱ��<a href='/logon.action'>��¼</a>");
					 mobile_data=1;
					 return false;
				 }else{
			    	 $("#mobile_show_info").text("");
			    	 mobile_data=0;
			    	 $.ajax({
			    			type:"post",
			    			url:"/view/user/sendRegVcode.action",
			    			data:{mobile:$("#mobile").val()},
			    			cache:false,
			    			beforeSend: function(){// Handle the beforeSend event
			    				 //time(me);
			    			},
			    			success: function(data, textStatus){
			    				if(data){
			    					//alert(data);
			    					var result = eval("("+data+")");
			    					if(result){
			    						if(result.success==0){
			    							//alert("������֤���ѳɹ����͵������ֻ����뾡�����룡");
			    							showMsgInfo(0,"������֤���ѳɹ����͵������ֻ����뾡�����룡",0);
			    						}else if(result.success==1){
			    							//alert("���յ��Ķ�����֤����δ���ڣ����Ժ����ԣ�");
			    							showMsgInfo(0,"���յ��Ķ�����֤����δ���ڣ����Ժ����ԣ�",0);
			    							wait = 10;
			    						}else{
			    							//alert("����ʧ�ܣ����ٴλ�ȡ��");
			    							showMsgInfo(0,"����ʧ�ܣ����ٴλ�ȡ��",0);
			    							wait = 0;
			    						}
			    					}
			    				}
			    			},
			    			error:function(data){}
			    		});
			    	 
			     }
			
			}
		});


  	
	
}

$(function(){
    
	
	$("#mobile").focus(function(){
		$("#mobile_show_info").text("");
	});
	$("#password1").focus(function(){
		$("#password_show_info").text("");
	});
	$("#password2").focus(function(){
		$("#show_info").text("");
	});
	$("#authcode").focus(function(){
		$("#authcode_show_info").text("");
	});
	
	var passwordReg=/^([a-z]+(?=[0-9])|[0-9]+(?=[a-z]))[a-z0-9]+$/;
	$("#password1").blur(function(){
		if(mobile_data==0){
		  var password1=$("#password1").val();
		  //alert(password1.length);
		  if(password1.length<6){
			  $("#password_show_info").text("��¼��������Ϊ6λ�����Ұ���Ӣ�ĺ�����!");
		  }else{
			 // alert(password1);
			  if(passwordReg.test(password1)){
				  //alert(password1);
					 $("#password_show_info").text("");
			  }else{
			    	 $("#password_show_info").text("��¼��������Ϊ6λ�����Ұ���Ӣ�ĺ�����!");
			    	 
			  }
	      }
		 }
		});
	
	$("#password2").blur(function(){
		if(mobile_data==0){
		  var password1=$("#password1").val();
		  var password2=$("#password2").val();
		 if(password1!=password2){
			 $("#show_info").text("������������벻һ��!");
	     }else{
	    	 $("#show_info").text("");
	     }
		}
		});
	

$("#mobile").blur(function(){
	 var mobile_filter  = /^(((1[0-9]{1}[0-9]{1}))+\d{8})$/;
	 var mobile=$("#mobile").val();
	 if (!mobile_filter.test(mobile)){ 
		 //alert('�����ֻ������ʽ����ȷ!');
		 $("#mobile_show_info").text("�ֻ�����λ������!");
		 return false;
	 }
	
	 $.ajax({
			type:"post",
			url:"/view/user/checkRegVcode.action?user.mobile="+$("#mobile").val(),
			data:{authcode:$("#authcode").val()},
			cache:false,
			success: function(data){
				//alert(data);
				if(data==2){
					//alert("�ֻ��ű�ռ�ã����������!");
					 $("#mobile_show_info").html("�ú����Ѿ����ڣ���ֱ��<a href='/logon.action'>��¼</a>");
					 mobile_data=1;
			     }else{
			    	 $("#mobile_show_info").text("");
			    	 mobile_data=0;
			     }
			
			}
		});
});
});


function agreeProtocol(obj){
    //alert(obj.checked);
    if(obj.checked){
      $("#ty_zc").removeAttr("disabled");
      $("#ty_zc").attr("class", "ty_zc"); 
    }else{
      $("#ty_zc").attr("disabled","disabled");
      $("#ty_zc").attr("class", "ty_hs");
    }
	
}
