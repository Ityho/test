function goAnalysisWeibo(weiboId, weiboUrl, retry,isNo) {
        if ((weiboUrl != null && weiboUrl != '') || (weiboId != null && weiboId != '')) {
            var url = actionBase + "/view/reviewAnalysis/analysis.action";
            if (url != ''){
            	$.ajax({
                    url : url,
                    type : 'POST',
                    data:{
                    	'reviewAnalysisId':weiboId,
                    	'weiboNewURL':weiboUrl,
                    	'retry':retry,
                    	'whether':isNo
                    },
                    success : function(result) {
    					if("0000" == result.code){
//                             location.href = "history.shtml";
							//扣除权益
    						goPayMoney(result.id);
    					}else{
    						reflag = true;
                            showMsgInfo(0,result.msg,0);
    					}
                    }
                });
//                 createForm(url, 'POST', '_self', params);
            }else{
            	reflag = true;
            }
        }else{
        	reflag = true;
        }
    }

function goAnalysisWeiboto() {
    var url = actionBase + '/view/reviewAnalysis/payComplete.action';
    var params = {};
    createForm(url, 'POST', '_self', params);
}


function goretryAnalysis(weiboId, weiboUrl, retry,isNo) {
    $.ajax({
        url:actionBase + '/view/reviewAnalysis/goAnalysisto.action',
        type:'POST',
        async: false,
        data:{'reviewAnalysisId':weiboId,'weiboNewURL':weiboUrl,retry:true},
        success:function (result) {
            if (result==true){
                goAnalysisWeiboto();
            }else{
                showMsgInfo(0, '�������', 0);
            }
        }
    })
}
function updateReview(ticket,status) {
    $.ajax({
        url: actionBase + "/nra/reviewAnalysis/updateReiviewAnalysis.action",
        type:"POST",
        data:{'ticket':ticket,'fixed_status':status},
        sunccess:function (result) {
            if (result){}
        }
    })
}