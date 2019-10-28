	//点击排行榜四个选项卡
	function goRankingList(n){
		$.ajax({
            url:actionBase + "/goRankingList.action",
            type:"post",
            data:{
                "flag":n,
            },
            success:function(data){
                $("#goRankingList").html(data);
            }
        });
	}
	
	function chartLoading(id){
        var stat = document.getElementById(id);
        stat.innerHTML = '<div class="spinner" style="display: block;"><div class="bounce1"></div></div><br/><div style="font-size:12px;color:#999999;height: 50px;" align="center">系统拼命加载中，不要离开，马上呈现～</div>';
    }
	
	 function savePicShow(nn){
		if(adminJs == ''){
			$("#loginModal").modal();
			return;
		}
 		var type = $("#typeId").val();//热点类型
     	var label = $("#rankLabelId").val();//行业
     	var province = $("#provinceId").val();//地域
     	var flag = $("#flag").val();
     	var nationalTypeId = $("#nationalTypeId").val();
     	var nationalTimeId = $("#nationalTimeId").val();
     	var searchName;
     	if(flag == 2){
     		searchName = $("#searchName_2").val();
    	}else if(flag == 3){
    		searchName = $("#searchName_3").val();
    	}else{
    		searchName = $("#searchName_1").val();
    	}
     	var params = {
             	"type":type,
             	"label":label,
             	"province":province,
             	"page" : $("#page").val(),
             	"rankingType" : $("#nationalTypeId").val(),
             	"time" : $("#nationalTimeId").val(),
             	"flag" : $("#flag").val(),
             	"categoryId" : $("#category").val(),
             	"flag1TimeType" : $("#flag1TimeType").val(),
             	"parentId" : $("#parentId").val(),
             	"rankTypeId1" : $("#rankTypeId1").val(),
             	"rankTypeId2" : $("#rankTypeId2").val(),
             	"searchName" : searchName,
             	"sort" : $("#sort").val(),
             	"order" : $("#order").val(),
             	"page" : $("#page").val(),
             	"pagesize" : 20,
             	"classifyName" : $("#classifyName").val(),
             	"isAll" : $("#isAll").val(),
             	"version" : 2
             };
     	if(flag==1){
     		if(nn==1){
     			showMsgInfo(0,"确认数据导出吗？",1);
    			$("#submitBtn").one("click",function(){
    				hideInfoDiv();
    				sendPostForm("${ctx}/downLoadExcelRankingList.action", '_blank', params);
    			})
     			//导出excel
     		}else{
     			//生成长图
     			$.ajax({
                    url : "${ctx}/savePicShow.action",
                    type:"post",
                    data:params,
                    success:function(data){
                    	if(data.data==null){
                    		showMsgInfo(0,"暂不支持长图！",0);
                    		return;
                    	}
                    	var hm = '';
                    	hm += '<table border="0" cellspacing="0" cellpadding="0">';
                    	hm += '<thead>';
                    	hm += '<tr>';
                    	hm += '';
                    	if(type ==2){//热门现象
                    		hm += '<th width="15%">排名</th>';
                    		hm += '<th>热门现象</th>';
                    		hm += '<th width="20%">全网信息量</th>';
                    		hm += '<th width="20%">包含的事件个数</th>';
                    		hm += '<th width="20%">行业</th>';
                    	}else if(type ==3){//重大事件
                    		hm += '<th width="15%">排名</th>';
                    		hm += '<th>重大事件</th>';
                    		hm += '<th width="20%">全网信息量</th>';
                    		hm += '<th width="20%">包含的事件个数</th>';
                    		hm += '<th width="20%">行业</th>';
                    		hm += '<th width="20%">地域</th>';
                    	}else if(type ==6){//热词
                    		hm += '<th width="15%">排名</th>';
                    		hm += '<th>热词</th>';
                    		hm += '<th width="20%">热度指数</th>';
                    		hm += '<th width="20%">行业</th>';
                    	}else if(type ==7){//热门微博
                    		hm += '<th width="15%">排名</th>';
                    		hm += '<th>热门微博</th>';
                    	}else if(type ==8){//热门人物
                    		hm += '<th width="15%">排名</th>';
                    		hm += '<th>名称</th>';
                    		hm += '<th>热度指数</th>';
                    		hm += '<th>全网信息量</th>';
                    	}else{//热门事件
                    		hm += '<th width="15%">排名</th>';
                    		hm += '<th>热点事件</th>';
                    		hm += '<th width="20%">热度指数</th>';
                    		hm += '<th width="20%">行业</th>';
                    		hm += '<th width="20%">地域</th>';
                    	}
                    	hm += '</tr>';
                    	hm += '</thead>';
                    	hm += '<tbody>';
                    	for(var i=0;i<data.data.length;i++){
                    		if(type ==2){//热门现象
                        		hm += '<tr>';
                        		hm += '<td><div class="rank_1">'+(i+1)+'</div></td>';
                        		hm += '<td><div class="rank_tab"><div class="rank_tab_cell">'+data.data[i].name+'</div></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].numberDay+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].eventNum+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].labelName+'</span></div></td>';
                        		hm += '</tr>';
                        	}else if(type ==3){//重大事件
                        		hm += '<tr>';
                        		hm += '<td><div class="rank_1">'+(i+1)+'</div></td>';
                        		hm += '<td><div class="rank_tab"><div class="rank_tab_cell">'+data.data[i].name+'</div></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].numberDay+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].eventNum+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].labelName+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].province+'</span></div></td>';
                        		hm += '</tr>';
                        	}else if(type ==6){//热词
                        		hm += '<tr>';
                        		hm += '<td><div class="rank_1">'+(i+1)+'</div></td>';
                        		hm += '<td><div class="rank_tab"><div class="rank_tab_cell">'+data.data[i].name+'</div></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].ratioHotDay+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].labelName+'</span></div></td>';
                        		hm += '</tr>';
                        	}else if(type ==7){//热门微博
                        		hm += '<tr>';
                        		hm += '<td><div class="rank_1">'+(i+1)+'</div></td>';
                        		hm += '<td><div class="rank_tab"><div class="rank_tab_cell">'+data.data[i].status.text+'</div></div></td>';
                        		hm += '</tr>';
                        	}else if(type ==8){//热门人物
                        		hm += '<tr>';
                        		hm += '<td><div class="rank_1">'+(i+1)+'</div></td>';
                        		hm += '<td><div class="rank_tab"><div class="rank_tab_cell">'+data.data[i].title+'</div></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].numberDay+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].ratioHotDay.toFixed(2)+'</span></div></td>';
                        		hm += '</tr>';
                        	}else{//热门事件
                        		hm += '<tr>';
                        		hm += '<td><div class="rank_1">'+(i+1)+'</div></td>';
                        		hm += '<td><div class="rank_tab"><div class="rank_tab_cell">'+data.data[i].incidentTitle+'</div></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].ratioHotDay.toFixed(2)+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].labelName+'</span></div></td>';
                        		hm += '<td><div class="rank_1"><span class="">'+data.data[i].province+'</span></div></td>';
                        		hm += '</tr>';
                        	}
                    	}
                    	hm += '</tbody>';
                    	hm += '</table>';
                    	$("#bodyListShow").html(hm);
    					$("#savePictureClickShow").click();
                    }
                });
     		}
     	}else{
     		showMsgInfo(0,"只能选择热点排行",0);
     	}
 	}
	 
	    
	function goTankAnalysis(){
		if(adminJs == ''){
			$("#loginModal").modal();
			return;
		}
		$.ajax({
            url : "${ctx}/goTankAnalysis.action",
            type : "post",
            async: false,
            success : function(data){
                if(data.code == "0000"){
                	var hm = '<select id="rankLabelListVal" name="" class="control-select bgColor_2" >';
                	hm += '';
                	for(var i=0;i<data.data.length;i++){
                		hm += '<option value="'+data.data[i].id+'">'+data.data[i].name+'</option>';
                	}
                	hm += '</select>';
                	$("#rankLabelList").html(hm);
                	
                	var html = '';
    				html += '<select class="control-select bgColor_2" id="rankAreaListVal">';
    	        	for(var key in pc){
    	        	    html += '<option value="'+key+'">'+key+'</option>';
    	        	}
    	        	html += '</select>';
    	        	$("#rankAreaList").html(html);
                	$("#hideAnalysisId").click();
                }else{
                	showMsgInfo(0,data.msg,0);
                }
            }
        });
		
	}
	function gosearch(title,keyword,filterKeyword){
	 	$("#title").val(title);
	    $("#keyword").val(keyword);
	    $("#filterKeyword").val(filterKeyword);
	    goJumpSearch($("#keyword").val());
	}
	function goJumpSearch(keyword){
		/*直接跳转不进行验证*/
		var form = document.getElementById("searchForm");
	    form.action =actionBase + "/goSearch.shtml";
	    $("#categoryId").val("");
	    $("#categoryType").val("");
	    $("#secondCategory").val("");
	    form.submit();
	}
	function goWeiBoUrlShow(url){
		window.open(url, "_blank");
	}
	
	