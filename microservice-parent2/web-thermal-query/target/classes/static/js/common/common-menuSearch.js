

var menu_selectText;
function goContentMenuSearch(isProUser) {
	var maxKeywordSize = 50;
	if(isProUser == 1)
		maxKeywordSize = 500;
	menu_selectText = getSelect();
    $(".submitBtn").html("ȷ��");
    if(menu_selectText == ''){
    	showMsgInfo(0, '��ѡ������', 0);
    	return;
    }
    if (menu_selectText.length<2){
        showMsgInfo(0,"�Բ��𣬼�ⷽ����֧�ֵ��ִ���������������!",0);
        return;
    }
    if(menu_selectText.length > maxKeywordSize) {
    	showMsgInfo(0,"�ؼ��ָ����뱣����"+maxKeywordSize+"��������!",0);
        return;
    }
    if(!checkKeywordFilter(menu_selectText)) {
    	showMsgInfo(0, '��������ֻ�ܰ������ġ�Ӣ�ĺ����֣�', 0);
        return;
    }
    showMsgInfo(0,"ȷ������������",1);
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
				$(".submitBtn").text("ȷ��");
                showMsgInfo(0, data.message, 0);
                return;
			}
			if(data.msg > 2){
				$(".submitBtn").html("�鿴����");
				$("#cancelBtn").html("���ڵ�ǰҳ");
				showMsgInfo(0,"��Ӽ�ⷽ���ɹ���",1);
				$(".submitBtn").one('click',function(){
					var params = {'kw.keywordId':data.msg};
					sendPostForm(njxBasePath + "/keyword.shtml", '_blank', params);
				});
			}else if(data.msg instanceof Array){
				$(".submitBtn").html("��������");
				$("#cancelBtn").html("ȡ��");
				showMsgInfo(0,"����ǰδʹ�ü�ⷽ������Ϊ0���Ƿ���",1);
				$(".submitBtn").one('click',function(){
					hideInfoDiv();
					$("#monitorFlag").val(1);
					openBuyCommon(1);
				});
			}
		}
	});
}


//�����ų���
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
            	$(".submitBtn").text("ȷ��");
                showMsgInfo(0, '�ų��ؼ���ֻ�ܰ������ġ�Ӣ�ĺ����֣�', 0);
                return;
            }
        	showMsgInfo(0,"ȷ������" + menu_selectText + "������÷����ų�����",1);
        	$(".submitBtn").one('click',function (){
        		toContentMenuFilter(keywordId, keywordType, maxFilterSize );
        	});
        } else {
        	$(".submitBtn").text("ȷ��");
            showMsgInfo(0, '�ų��ؼ��ʲ��ܳ���' + maxFilterSize + '���ַ���', 0);
        }
    } else {
    	$(".submitBtn").text("ȷ��");
        showMsgInfo(0, '��ѡ��Ҫ�ų��Ĺؼ��ʣ�', 0);
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
                showMsgInfo(0, '�����ų��ؼ��ʳɹ���', 0);
            	$(".submitBtn").text("ȷ��");
                $(".submitBtn",this.document).one("click",function(){
                    location.href = actionBase + "/keywordSearch.action?kw.keywordId="+keywordId;
                });
            } else {
            	$(".submitBtn").text("ȷ��");
                showMsgInfo(0, '�ų��ؼ��ʲ��ܳ���' + maxFilterSize + '���ַ���', 0);
            }
        }
    });
}

// ѡ���ı�
function getSelect() {
    if(window.getSelection)
        return window.getSelection().toString();
    else
        return document.selection.createRange().text;
}
    
    
    
    
    
    
    