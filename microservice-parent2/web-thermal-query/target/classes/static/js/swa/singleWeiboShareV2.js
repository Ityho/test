
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
    		shareContent = "［来自@微热点(微舆情)］万万没想到，一条"+shareReadsCount+"人关注的微博，背后居然暗藏着如此大的玄机~ 信息、商情就用微热点(微舆情)";
    	else if ('weixin' == cmd)
    		shareContent = "［微热点(微舆情)］万万没想到，一条"+shareReadsCount+"人关注的微博，背后居然暗藏着如此大的玄机~ 信息、商情就用微热点(微舆情)";
    	else if ('qqim' == cmd || 'qzone' == cmd)
    		shareContent = "［微热点(微舆情)］大家别吵，我正专心看… 信息、商情就用微热点(微舆情)";
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
         
       //清除自定义分享内容的方法，在设置前清空，重新自定义内容
       
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
           '就四介么拽，看介个微博，' + shareReportsCount + '人转发，' + shareCommentCount + '人评论，' + sharePriseCount + '人点赞。',
           '哎呀妈呀，看介个微博，竟然被“@' + shareTopUser1 + '、@' + shareTopUser2 + '、@' + shareTopUser3 + '”转发了，鸡冻 的小心脏怦怦直跳。',
           '和大数据谈恋爱是种什么体验？就像这条微博一样被剥得一丝不挂！',
           '这世上多的是你不知道的事，这条微博被' + shareReportsCount + '人转发，居然没有我，我不服！',
           '天了噜！' + shareReadsCount + '人关注了这条微博，如此有品位的我，居然现在才看到！',
           '据说90%的网友都会点开这条信息，这是真的吗？我先替你看看~',
           '@' + shareOriginalUser + ' 发了条微博，网友却炸锅了，' + shareReportsCount + '人转发，' + shareCommentCount + '人评论，再不看你就out了！',
           '万万没想到，一条' + shareReadsCount + '人关注的微博，背后居然暗藏着如此大的玄机~',
           '咦~~这条微博究竟是怎么火起来的？快跟我一起研究一下。',
           '大家别吵，我正专心看。。。'
        ];
    	
    	var shareContent = shareContentArr[Math.round(Math.random() * 9)];
    	return shareContent;
    }