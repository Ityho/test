//�����ղصķ���jsЧ��
$(function(){
	var offset = $("#end").offset(); 
	var userId = $("#userId").val();
	var silderRightDiv = $("#izl_rmenu");
	var tipSucai = $("#tipSucai");
	var tipCollect = $("#tipCollect");
	var countSucaiObj = $(silderRightDiv).find("a").eq(0).find("div").find("span");  //��ȡ�Ҳ��ز� ����
	var countCollectObj = $(silderRightDiv).find("div[class='btn btn-scj rel']").find("span");  //��ȡ�Ҳ��ղ� ����
	//��������Ǽ����زģ�������ʱ
	 $(".addSucai").one("click",function(event){ 
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
                $(this).css("cursor","default").removeClass('orange').unbind('click'); 
                importNewsSingle(2,userId);
                $(countSucaiObj).html(parseInt($(countSucaiObj).html())+1);
                var bottomSucaiCount =  $("#bottomSucaiCount");
                $(bottomSucaiCount).html(parseInt($(bottomSucaiCount).html())+1);
                $(this).empty(); //�Ƴ�dom 
                //this.destory();
            } 
        }); 
    }); 
	
	
	
	//��������Ǽ����ղ�ʱ
    $(".addCollect").one("click",function(event){ 
        var flyer = $('<span class="u-flyer"></span>'); 
        flyer.fly({ 
             start: { 
                left: event.pageX, //��ʼλ�ã����#flyԪ�ػᱻ���ó�position: fixed 
                top: event.pageY //��ʼλ�ã���� 
            }, 
            end: { 
                left: offset.left+50, //����λ�ã���� 
                top: offset.top+160 //����λ�ã���� 
            }, 
            onEnd: function(){ //�����ص� 
                $(tipCollect).show().animate({width: '200px'}, 200).fadeOut(500); //��ʾ��Ϣ 
                $(this).css("cursor","default").removeClass('orange').unbind('click'); 
                importNewsSingle(1,userId);
                $(countCollectObj).html(parseInt($(countCollectObj).html())+1);
                $(this).empty(); //�Ƴ�dom 
                //this.destory();
            } 
        }); 
    });  
    
    //���������زģ������ֻ���ղؼ����иù��ܣ�
    
  //��������Ǽ����زģ�������ʱ
	 $("#addSucais").one("click",function(event){ 
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
               addJianBaoSucais();
               $(this).empty(); //�Ƴ�dom 
               //this.destory();
           } 
       }); 
   }); 
    
    
    
    
});