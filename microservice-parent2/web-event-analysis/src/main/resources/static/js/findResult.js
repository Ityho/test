/**
  * ������Ϣstart
  */
 var shareType;
 function goShareNews(){
 	 $("#shareNewsPOP").fadeIn(300);
// 	 window.ontouchmove=function(e){
// 		    e.preventDefault && e.preventDefault();
// 		    e.returnValue=false;
// 		    e.stopPropagation && e.stopPropagation();
// 		    return false;
// 		} ;
 		  //$(".footPOP").show(); 
 		 $(".footPOP").addClass('downShow'); 
 		 //$(".footPOP").out(); 
 		 $(".footPOP").removeClass('downOut'); 
 	var userAgentInfo = navigator.userAgent;
 	var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
 	var flag = true;
 	for (var v = 0; v < Agents.length; v++) {
 		if (userAgentInfo.indexOf(Agents[v]) > 0) {
 			flag = false;
 			break;
 		}
 	}
 	if (flag) {
 		var html1 ="<a href='javascript:;' onclick='doShare(1)'>����΢��</a>";
 		    html1 +="<a href='javascript:;' onclick='doShare(2)'>QQ</a>";
 			html1 += "<a href='javascription:void(0);' onclick='javascript:cancelShare();' class='cancel'>ȡ��</a>";
 		$("#shareNewsPOP").html(html1);
 	} else {
 		var html2 = "<a href='javascript:;' onclick='doShare(3)'>���ŷ���</a>";
 		html2 +="<a href='javascript:;' onclick='doShare(1)'>����΢��</a>";
 		html2 +="<a href='javascript:;' onclick='doShare(2)'>QQ</a>"; 
 		html2 += "<a href='javascription:void(0);' onclick='javascript:cancelShare();' class='cancel'>ȡ��</a>";
 		 $("#shareNewsPOP").html(html2);
 	} 
 }
 function SingleShareCallBack(data){
 	shareType  = data[2];
if(data!=null){
 		var content = data[0];
 		//var originUrl = data[1];
 		var originUrl = $("#wapUrl").val();
 		var title = data[3];
 		var summary = data[4];
 	if(content!=null&&content!=''){
 		var userAgentInfo = navigator.userAgent;
 		title = title.split('#').join('');
 		summary = summary.split('#').join('');
 		 content = data[0].split('#').join('');
 		 url = "javascript:history.go(-1);";
 		if (shareType == 1) { // sina
 			$('#shareHref').attr('href', 'http://v.t.sina.com.cn/share/share.php?title=' + encodeURI(summary) + '&url=' + encodeURIComponent(originUrl));
 			$('#shareHref').attr('target','_self');
 			if(userAgentInfo.indexOf("Safari") > 0 || userAgentInfo.indexOf('iPhone') > -1 || userAgentInfo.indexOf('iPad') || userAgentInfo.indexOf('iPod') ){
 				  var e = document.createEvent('MouseEvent');     
	 			   e.initEvent('click', false, false);     
	 			   setTimeout(document.getElementById("shareHref").dispatchEvent(e),2000);   
 			
 			}else{
 				document.getElementById('shareHref').click();
 			}	
 		} else if (shareType == 2) { // QQ
 			$('#shareHref').attr('href', 'http://connect.qq.com/widget/shareqq/index.html?url='+encodeURIComponent(originUrl)+'&desc=' + encodeURI(content)+'&summary='+ encodeURI(summary)+'&title='+ encodeURI(title)+'&site='+encodeURI("΢�ȵ�(΢����)")+'&pics=http://p1.global.jmstatic.com/assets/global/3_03_2.jpg');
 			$('#shareHref').attr('target','_self');
 			if(userAgentInfo.indexOf("Safari") > 0 || userAgentInfo.indexOf('iPhone') > -1 || userAgentInfo.indexOf('iPad') || userAgentInfo.indexOf('iPod') ){
 				var e = document.createEvent('MouseEvent');     
	 			   e.initEvent('click', false, false);     
	 			   setTimeout(document.getElementById("shareHref").dispatchEvent(e),2000);   	
 				
 			}else{
 				document.getElementById('shareHref').click();
 			}
 		} else if (shareType == 3) { // sms
 		  	if(userAgentInfo.indexOf('iPhone') > -1){
 		    	$('#smsShareHref').attr('href', 'sms:&body=' + encodeURI(summary) + ' ' + originUrl);
 		  	}else{
 		  	  $('#smsShareHref').attr('href', 'sms:?body=' + encodeURI(summary) + ' ' + originUrl);
 		  	}
 			document.getElementById('smsShareHref').click();
 		}
 	}
 	}else{
 		$(".zhezhao").fadeIn(300);
         $("#cancleFalsePOP").fadeIn(300);
         $(".prompPOP").addClass('scaleShow'); 
         $(".prompPOP").removeClass('scaleOut'); 
         setTimeout(function(){
         	$(".zhezhao").fadeOut(300);
         	$("#cancleFalsePOP").fadeOut(300);
         	$(".prompPOP").addClass('scaleOut'); 
         	$(".prompPOP").removeClass('scaleShow'); 
         },2000);
 	}
 }
 function doShare(type) {
 	shareType = type;
 	var checkedId = document.getElementById("selectedMenuId").value + ",";
 	var userId = document.getElementById("userId").value;
 	cancelShare();
 	NewsOperate.shareSingleNews(userId,checkedId,shareType,SingleShareCallBack);
 }
/**
 * ������Ϣstart
 */
function goNewsDelete(){	
	
	var checkedId = document.getElementById("selectedMenuId").value + ",";
	var userId = document.getElementById("userId").value;
	 NewsOperate.deleteSyNews(userId,checkedId,SingleDelCallBack);
	 
}
function SingleDelCallBack(checkedIds){ 
	var filterOrigina = 0;
	if(document.getElementById("filterOrigina")!=null){
		filterOrigina = document.getElementById("filterOrigina").value;
	}
	if(checkedIds!=null){
		$("input[name=menuCheckId]").each(function(){
		  if(checkedIds.indexOf($(this).val())>=0) {
			$(this).parent('section').slideUp(300, function() {
 				 $(this).parent('section').remove();
		            });
			   }
			}); 
		//$(".zhezhao").fadeIn(300);
        $("#referPOP").fadeIn(300);
        $(".prompPOP").addClass('scaleShow'); 
        $(".prompPOP").removeClass('scaleOut'); 
        setTimeout(function(){
        	$(".zhezhao").fadeOut(300);
        	$("#referPOP").fadeOut(300);
        	$(".prompPOP").addClass('scaleOut'); 
        	$(".prompPOP").removeClass('scaleShow'); 
        },2000);
		if($("#listDiv").find('section').length==0){
			setOriginTypeCommon(filterOrigina);
		 }
		}else{
			//$(".zhezhao").fadeIn(300);
	          $("#cancleFalsePOP").fadeIn(300);
	          $(".prompPOP").addClass('scaleShow'); 
	          $(".prompPOP").removeClass('scaleOut'); 
	          setTimeout(function(){
	          	$(".zhezhao").fadeOut(300);
	          	$("#cancleFalsePOP").fadeOut(300);
	          	$(".prompPOP").addClass('scaleOut'); 
	          	$(".prompPOP").removeClass('scaleShow'); 
	          },2000);
	    }
}

/**
 * �ղ���Ϣstart
 */

//���ݵ��������ղؼ�
function importNewsSingleSenior(id) {
	$("#selectedMenuId").val(id);
	var repeatNum = document.getElementById("repeatNum").value;
	var checkedId = document.getElementById("selectedMenuId").value + ",";
	var userId = document.getElementById("userId").value;
	
	if(!userId){
		window.location.href="indexLocal";
		return;
	}
	var collectId = "#"+id;
	if($(collectId).hasClass("importChoose")){
		$(collectId).removeClass("importChoose");
		$(collectId).removeClass("icon-star");
		$(collectId).addClass("icon-star2");
		NewsOperate.operateNewsSenior("deleteSingle", checkedId,userId,repeatNum,CallBackSenior);
	}else{
		$.ajax({
			url : actionBase + '/getFavTotalNumber.action',
			type : 'POST',
			data :{
				
			},
			success : function(object) {
				var result=object.obj;
				if(result.code=="0000"){
					if(result.data<1000){
						$(collectId).removeClass("icon-star2");
						$(collectId).addClass("icon-star importChoose");
						NewsOperate.operateNewsSenior("insert", checkedId,userId,repeatNum,CallBackSenior);
					}else{
						$("#collectLimit").fadeIn(300);
				        $(".prompPOP").addClass('scaleShow'); 
				        $(".prompPOP").removeClass('scaleOut'); 
				        setTimeout(function(){
				        	$(".zhezhao").fadeOut(300);
				        	$("#collectLimit").fadeOut(300);
				        	$(".prompPOP").addClass('scaleOut'); 
				        	$(".prompPOP").removeClass('scaleShow'); 
				        },2000);
					}
				}
			}
		});
	    
	}
	
}

/**
 * ȡ���ղ���Ϣstart
 */
//���ݵ��������ղؼ�
function deleteNewsSingleSenior(id) {	
	$("#selectedMenuId").val(id);
	var repeatNum = document.getElementById("repeatNum").value;
	var checkedId = document.getElementById("selectedMenuId").value + ",";
	var userId = document.getElementById("userId").value;
	var collectId = "#"+id;
	if(!userId){
		window.location.href="indexLocal";
		return;
	}
	if($(collectId).hasClass("importChoose")){
		$(collectId).removeClass("importChoose");
		$(collectId).removeClass("icon-star");
		$(collectId).addClass("icon-star2");
		NewsOperate.operateNewsSenior("deleteSingle", checkedId,userId,repeatNum,CallBackSenior);
	}else{
		$(collectId).removeClass("icon-star2");
		$(collectId).addClass("icon-star importChoose");
		 NewsOperate.operateNewsSenior("insert", checkedId,userId,repeatNum,CallBackSenior);
	}
    
}
function CallBackSenior(result) {
	if (result) {
		result = JSON.parse(result);
		if(result.flag){
			console.log($("input[data-id='"+result.ids+"']"));
			 $("input[data-id='"+result.ids+"']").val("false");
			 $("#cancelCollectPOP").fadeIn(300);
	         $(".prompPOP").addClass('scaleShow'); 
	         $(".prompPOP").removeClass('scaleOut'); 
	         setTimeout(function(){
	         	$(".zhezhao").fadeOut(300);
	         	$("#cancelCollectPOP").fadeOut(300);
	         	$(".prompPOP").addClass('scaleOut'); 
	         	$(".prompPOP").removeClass('scaleShow'); 
	         },2000);
		}else{
			console.log($("input[data-id='"+result.ids+"']"));
			$("input[data-id='"+result.ids+"']").val("true");
			 $("#collectPOP").fadeIn(300);
	         $(".prompPOP").addClass('scaleShow'); 
	         $(".prompPOP").removeClass('scaleOut'); 
	         setTimeout(function(){
	         	$(".zhezhao").fadeOut(300);
	         	$("#collectPOP").fadeOut(300);
	         	$(".prompPOP").addClass('scaleOut'); 
	         	$(".prompPOP").removeClass('scaleShow'); 
	         },2000);
		}
	}else{
        $("#collectFalsePOP").fadeIn(300);
        $(".prompPOP").addClass('scaleShow'); 
        $(".prompPOP").removeClass('scaleOut'); 
        setTimeout(function(){
        	$(".zhezhao").fadeOut(300);
        	$("#collectFalsePOP").fadeOut(300);
        	$(".prompPOP").addClass('scaleOut'); 
        	$(".prompPOP").removeClass('scaleShow'); 
        },2000);
	}
	
}

