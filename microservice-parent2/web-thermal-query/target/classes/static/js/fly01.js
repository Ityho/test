//�����ղصķ���jsЧ��
$(function(){
	var offset = $("#end").offset(); 
	var userId = $("#userId",parent.window.document).val();
	var silderRightDiv = $("#izl_rmenu",parent.window.document);
	var tipSucai = $("#tipSucai",parent.window.document);
	var tipCollect = $("#tipCollect",parent.window.document);
	var countSucaiObj = $(silderRightDiv).find("a").eq(0).find("div").find("span");  //��ȡ�Ҳ��ز� ����
	var countCollectObj = $(silderRightDiv).find("a").eq(2).find("div").find("span");  //��ȡ�Ҳ��ղ� ����
	var countCartObj = $(silderRightDiv).find("a").eq(2).find("div").find("span");  //��ȡ�Ҳ๺�ﳵ����
	var offset1 = $(silderRightDiv).find("a").eq(0).offset();
	//��������Ǽ����زģ�������ʱ
	 $(".addSucai").one("click",function(event){ 
		var scrollTopValue = $(parent.window).scrollTop();
        var flyer = $('<span class="u-flyer"></span>'); 
        flyer.fly({ 
             start: { 
                left: event.pageX, //��ʼλ�ã����#flyԪ�ػᱻ���ó�position: fixed 
                top: event.pageY //��ʼλ�ã���� 
            }, 
            end: { 
            	 left: offset.left+50, //����λ�ã���� 
                 top: parseInt(offset1.top)+parseInt(scrollTopValue)-280 //����λ�ã���� 
            }, 
            onEnd: function(){ //�����ص� 
            	if(scrollTopValue>200){
            		$(tipSucai).css({top:"260px"});
            	}
                $(tipSucai).show().animate({width: '200px'}, 200).fadeOut(500); //��ʾ��Ϣ 
                $(this).css("cursor","default").removeClass('orange').unbind('click'); 
                importNewsSingleSenior(2,'0',userId);
            /*    $(countSucaiObj).html(parseInt($(countSucaiObj).html())+1);
                var bottomSucaiCount =  $("#bottomSucaiCount",parent.window.document);
                $(bottomSucaiCount).html(parseInt($(bottomSucaiCount).html())+1);*/
                $(this).empty(); //�Ƴ�dom 
                //this.destory();
            } 
        }); 
    }); 
	
	
	
	//��������Ǽ����ղ�ʱ
    $(".addCollect").one("click",function(event){ 
    	var scrollTopValue = $(parent.window).scrollTop();
        var flyer = $('<span class="u-flyer"></span>'); 
        flyer.fly({ 
             start: { 
                left: event.pageX, //��ʼλ�ã����#flyԪ�ػᱻ���ó�position: fixed 
                top: event.pageY //��ʼλ�ã���� 
            }, 
            end: { 
            	 left: offset.left+50, //����λ�ã���� 
                 top: parseInt(offset1.top)+parseInt(scrollTopValue)-230 //����λ�ã���� 
            }, 
            onEnd: function(){ //�����ص� 
            	if(scrollTopValue>200){
            		$(tipCollect).css({top:"310px"});
            	}
                $(tipCollect).show().animate({width: '200px'}, 200).fadeOut(500); //��ʾ��Ϣ 
                $(this).css("cursor","default").removeClass('orange').unbind('click'); 
                importNewsSingle(1,'0',userId);
              // $(countCollectObj).html(parseInt($(countCollectObj).html())+1);
                $(this).empty(); //�Ƴ�dom 
                //this.destory();
            } 
        }); 
    }); 
    
    
    
    //��������Ǽ����زģ�������ʱ
	 $("#addSucais").click(function(event){ 
	  var scrollTopValue = $(parent.window).scrollTop();
      var flyer = $('<span class="u-flyer"></span>'); 
      flyer.fly({ 
           start: { 
              left: event.pageX, //��ʼλ�ã����#flyԪ�ػᱻ���ó�position: fixed 
              top: event.pageY //��ʼλ�ã���� 
          }, 
          end: { 
        	  left: offset.left+50, //����λ�ã���� 
              top: parseInt(offset1.top)+parseInt(scrollTopValue)-280 //����λ�ã���� 
          }, 
          onEnd: function(){ //�����ص� 
        	  if(scrollTopValue>200){
          		$(tipSucai).css({top:"260px"});
          	  }
              $(tipSucai).show().animate({width: '200px'}, 200).fadeOut(500); //��ʾ��Ϣ 
              //addCart.css("cursor","default").removeClass('orange').unbind('click'); 
              importNewsSenior(2,'0',userId);
              $(this).empty(); //�Ƴ�dom 
              //this.destory();
          } 
      }); 
  }); 
    
	 
	 
	   
	    //��������Ǽ����ղأ�������ʱ
		 $("#addCollects").click(function(event){ 
		  var scrollTopValue = $(parent.window).scrollTop();
	      var flyer = $('<span class="u-flyer"></span>'); 
	      flyer.fly({ 
	           start: { 
	              left: event.pageX, //��ʼλ�ã����#flyԪ�ػᱻ���ó�position: fixed 
	              top: event.pageY //��ʼλ�ã���� 
	          }, 
	          end: { 
	        	  left: offset.left+50, //����λ�ã���� 
	              top: parseInt(offset1.top)+parseInt(scrollTopValue)-230 //����λ�ã���� 
	          }, 
	          onEnd: function(){ //�����ص� 
	        	  if(scrollTopValue>200){
	            		$(tipCollect).css({top:"310px"});
	              }
	              $(tipCollect).show().animate({width: '200px'}, 200).fadeOut(500); //��ʾ��Ϣ 
	              importNewsSenior(1,'0',userId);
	              $(this).empty(); //�Ƴ�dom 
	          } 
	      }); 
	  }); 
	  
	   //��������Ǽ����ղؼУ�������ʱ
	 $("#addColles").click(function(event){ 
       var flyer = $('<span class="u-flyer"></span>'); 
       flyer.fly({ 
            start: { 
               left: event.pageX, //��ʼλ�ã����#flyԪ�ػᱻ���ó�position: fixed 
               top: event.pageY //��ʼλ�ã���� 
           }, 
           end: { 
               left: offset.left+50, //����λ�ã���� 
               top: offset.top+100 //����λ�ã���� 
           }, 
           onEnd: function(){ //�����ص� 
               $(tipSucai).show().animate({width: '200px'}, 200).fadeOut(500); //��ʾ��Ϣ 
               addCollects();
               $(this).empty(); //�Ƴ�dom 
               //this.destory();
           } 
       }); 
   }); 
	    
    
    
});