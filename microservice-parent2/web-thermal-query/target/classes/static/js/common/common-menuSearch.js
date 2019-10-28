

var menu_selectText;
function goContentMenuSearch(isProUser) {
	var maxKeywordSize = 50;
	if(isProUser == 1)
		maxKeywordSize = 500;
	menu_selectText = getSelect();
    $(".submitBtn").html("确定");
    if(menu_selectText == ''){
    	showMsgInfo(0, '请选择监测词语！', 0);
    	return;
    }
    if (menu_selectText.length<2){
        showMsgInfo(0,"对不起，监测方案不支持单字创建，请重新输入!",0);
        return;
    }
    if(menu_selectText.length > maxKeywordSize) {
    	showMsgInfo(0,"关键字个数请保持在"+maxKeywordSize+"个字以内!",0);
        return;
    }
    if(!checkKeywordFilter(menu_selectText)) {
    	showMsgInfo(0, '方案名称只能包含中文、英文和数字！', 0);
        return;
    }
    showMsgInfo(0,"确定新增方案吗？",1);
	$(".submitBtn").one('click',function (){
		toContentMenuSearch();
	});
}

function toContentMenuSearch() {
	$.ajax({
		url : njxBasePath + '/keyword_replace.action',
		type : "post",
		data:{
			"searchKeyword":menu_selectText,
			"searchType":1
		},
		dataType : "json",
		success : function(data){
			if(data.code && data.code != '0000'){
				$(".submitBtn").text("确定");
                showMsgInfo(0, data.message, 0);
                return;
			}
			if(data.msg > 2){
				$(".submitBtn").html("查看方案");
				$("#cancelBtn").html("留在当前页");
				showMsgInfo(0,"添加监测方案成功！",1);
				$(".submitBtn").one('click',function(){
					var params = {'kw.keywordId':data.msg};
					sendPostForm(njxBasePath + "/keyword.shtml", '_blank', params);
				});
			}else if(data.msg instanceof Array){
				$(".submitBtn").html("立即购买");
				$("#cancelBtn").html("取消");
				showMsgInfo(0,"您当前未使用监测方案个数为0，是否购买？",1);
				$(".submitBtn").one('click',function(){
					hideInfoDiv();
					$("#monitorFlag").val(1);
					openBuyCommon(1);
				});
			}
		}
	});
}


//加入排除词
function goContentMenuFilter(keywordId, keywordType) {
	var maxFilterSize = 50;
	var maxKeywordSize = 50;
	if(keywordType == 0 || keywordType == 1) {
        maxKeywordSize = 50;
        maxFilterSize = 50;
    } else if(keywordType == 2) {
        maxKeywordSize = 500;
        maxFilterSize = 500;
    }
	menu_selectText = getSelect();
    if(menu_selectText != '') {
        if(menu_selectText.length < maxFilterSize) {
            if(!checkKeywordFilter(menu_selectText)) {
            	$(".submitBtn").text("确定");
                showMsgInfo(0, '排除关键词只能包含中文、英文和数字！', 0);
                return;
            }
        	showMsgInfo(0,"确定将“" + menu_selectText + "”加入该方案排除词吗？",1);
        	$(".submitBtn").one('click',function (){
        		toContentMenuFilter(keywordId, keywordType, maxFilterSize );
        	});
        } else {
        	$(".submitBtn").text("确定");
            showMsgInfo(0, '排除关键词不能超过' + maxFilterSize + '个字符！', 0);
        }
    } else {
    	$(".submitBtn").text("确定");
        showMsgInfo(0, '请选择要排除的关键词！', 0);
    }
}
function toContentMenuFilter(keywordId, keywordType, maxFilterSize) {
    $.ajax({
        url:'keyword_add_filter.action',
        type:'POST',
        data:{
            'kw.keywordId':keywordId,
            'kw.inputFilterKeyword':menu_selectText,
            'kw.keywordType':keywordType
        },
        success:function(data) {
            if(data) {
                showMsgInfo(0, '加入排除关键词成功！', 0);
            	$(".submitBtn").text("确定");
                $(".submitBtn",this.document).one("click",function(){
                    location.href = actionBase + "/keywordSearch.action?kw.keywordId="+keywordId;
                });
            } else {
            	$(".submitBtn").text("确定");
                showMsgInfo(0, '排除关键词不能超过' + maxFilterSize + '个字符！', 0);
            }
        }
    });
}

// 选择文本
function getSelect() {
    if(window.getSelection)
        return window.getSelection().toString();
    else
        return document.selection.createRange().text;
}
    
    
    
    
    
    
    