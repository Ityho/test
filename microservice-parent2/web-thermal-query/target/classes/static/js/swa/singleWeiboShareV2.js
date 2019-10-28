
    function sharePage(event,cmd){
    	if ($('#pageType').val() == 'web'){
    		var sharePlatform = 0;
    		if ('sinaminiblog' == cmd)
    			sharePlatform = 1;
    		else if ('weixin' == cmd)
    			sharePlatform = 2;
    		else if ('qqim' == cmd || 'qzone' == cmd)
    			sharePlatform = 4;
    		
    		$.ajax({
    			url : actionBase + '/ui/singleWeiboAnalysis/recordSharePlatform.action',
    			type : 'POST',
    			data : {
    				'fenxiWeibo.weiboShareCode' : $('#shareCode').val(),
    				'fenxiWeibo.weiboSharePlatform' : sharePlatform
    			},
    			success : function() {}
    		});
    	}else if($('#pageType').val() == 'share'){
    		var sharePlatform = 0;
    		if ('sinaminiblog' == cmd)
    			sharePlatform = 1;
    		else if ('weixin' == cmd)
    			sharePlatform = 2;
    		else if ('qqim' == cmd || 'qzone' == cmd)
    			sharePlatform = 4;
    		
    		$.ajax({
    			url : actionBase + '/ui/singleWeiboAnalysis/recordSharePlatform.action',
    			type : 'POST',
    			data : {
    				'fenxiWeibo.weiboShareCode' : $('#shareCode').val(),
    				'fenxiWeibo.weiboSharePlatform' : sharePlatform
    			},
    			success : function() {}
    		});
    	}
		
    	var shareContent = getShareContent();
    	if ('sinaminiblog' == cmd)
    		shareContent = "������@΢�ȵ�(΢����)������û�뵽��һ��"+shareReadsCount+"�˹�ע��΢���������Ȼ��������˴������~ ��Ϣ���������΢�ȵ�(΢����)";
    	else if ('weixin' == cmd)
    		shareContent = "��΢�ȵ�(΢����)������û�뵽��һ��"+shareReadsCount+"�˹�ע��΢���������Ȼ��������˴������~ ��Ϣ���������΢�ȵ�(΢����)";
    	else if ('qqim' == cmd || 'qzone' == cmd)
    		shareContent = "��΢�ȵ�(΢����)�ݴ�ұ𳳣�����ר�Ŀ��� ��Ϣ���������΢�ȵ�(΢����)";
    	var url = $('#shareURL').val() + '/share/singleWeiboAnalysis/shareView.shtml?shareCode=' + $('#shareCode').val()+"&isApp=0";
    	 bShare.entries = [];
    	if ('sinaminiblog' == cmd){
        	 bShare.addEntry({
         	    title: shareContent,
         	    url: url,
         	    summary: shareContent,
         	    pic: sharePic
         	});
         }else{
        	 bShare.addEntry({
         	    title: shareContent,
         	    url: url,
         	    summary: shareContent
         	});
         }
         
       //����Զ���������ݵķ�����������ǰ��գ������Զ�������
       
         bShare.init();
         bShare.share(event,cmd,0);
    }
    
    
    
    var sharePic;
    var shareOriginalUser;
    var shareReportsCount = 0;
    var shareCommentCount = 0;
    var sharePriseCount = 0;
    var shareReadsCount = 0;
    var shareTopUser1, shareTopUser2, shareTopUser3;
    function getShareContent() {
    	var shareContentArr = [
           '���Ľ�ôק�������΢����' + shareReportsCount + '��ת����' + shareCommentCount + '�����ۣ�' + sharePriseCount + '�˵��ޡ�',
           '��ѽ��ѽ�������΢������Ȼ����@' + shareTopUser1 + '��@' + shareTopUser2 + '��@' + shareTopUser3 + '��ת���ˣ����� ��С��������ֱ����',
           '�ʹ�����̸��������ʲô���飿��������΢��һ��������һ˿���ң�',
           '�����϶�����㲻֪�����£�����΢����' + shareReportsCount + '��ת������Ȼû���ң��Ҳ�����',
           '�����࣡' + shareReadsCount + '�˹�ע������΢���������Ʒλ���ң���Ȼ���ڲſ�����',
           '��˵90%�����Ѷ���㿪������Ϣ������������������㿴��~',
           '@' + shareOriginalUser + ' ������΢��������ȴը���ˣ�' + shareReportsCount + '��ת����' + shareCommentCount + '�����ۣ��ٲ������out�ˣ�',
           '����û�뵽��һ��' + shareReadsCount + '�˹�ע��΢���������Ȼ��������˴������~',
           '��~~����΢����������ô�������ģ������һ���о�һ�¡�',
           '��ұ𳳣�����ר�Ŀ�������'
        ];
    	
    	var shareContent = shareContentArr[Math.round(Math.random() * 9)];
    	return shareContent;
    }