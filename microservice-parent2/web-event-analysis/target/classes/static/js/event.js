/**
 * Created by �޳��� on 2016/4/6.
 */

function toggleTab(type,obj){
    if($(obj).hasClass("current"))
        return;
    if(type == 1){
        location.href = "taskList.action?mt=1";
    }else if(type == 2){
        location.href = "completeList.action";
    }else if(type == 3){
        location.href = "templateList.action";
    }else if(type == 4){
        location.href = "oldReportList.action";
    }else if(type == 5){
        location.href = "analysisCase.action";
    }
}

//�����¼���������
//function showEventFrame(type,taskId){
//    var url = "createAnalysis.action?createType="+type;
//    if(taskId!=""){
//        url += "&analysisTask.taskId="+taskId;
//    }
//    $("#event_frame").attr("src",url);
//    $("#BgDiv").fadeIn("slow");
//    //$("body").css({overflow:"hidden"});    //���ù�����
//    $("#eventDiv").fadeIn("slow");
//}
//����type:1-�¼��������䰸��,2-�ҵķ�����3-�ҵı��棩
function showEventFrame(type,taskId){
	var url="";
	if(type == 1){
		url = njxBasePath+"/view/eventCase.action";
	}else if(type == 2){
		url = njxBasePath+"/view/eventAnalysis1/taskList.action";
	}else if(type ==3){
		url = njxBasePath+"/view/eventAnalysis1/completeList.action";
	}
    if(taskId!=""){
        url += "&analysisTask.taskId="+taskId;
    }
    $("#event_frame").attr("src",url);
    $("#zhezhao").fadeIn("slow");
    //$("body").css({overflow:"hidden"});    //���ù�����
    $("#eventDiv").fadeIn("slow");
}
//����type:1-΢���������䰸��,2-�ҵķ�����3-�ҵı��棩
function showWeiboFrame(type,taskId){
	var url="";
	if(type == 1){
		url = njxBasePath+"/weiboEventCase.action";
	}else if(type == 2){
		url = njxBasePath+"/view/weiboEventAnalysis1/weiboTaskList.action";
	}else if(type ==3){
		url = njxBasePath+"/view/weiboEventAnalysis1/weiboCompleteList.action";
	}
    if(taskId!=""){
        url += "&analysisTask.taskId="+taskId;
    }
    $("#event_frame").attr("src",url);
    $("#zhezhao").fadeIn("slow");
    //$("body").css({overflow:"hidden"});    //���ù�����
    $("#eventDiv").fadeIn("slow");
}
//����type:1-��Ʒ�������䰸��,2-�ҵķ�����3-�ҵı��棩
function showProductAnalysisFrame(type,taskId){
	var url="";
	if(type == 1){
		url = njxBasePath+"/productsAnalysisCase.action";
	}else if(type == 2){
		url = njxBasePath+"/view/weiboEventAnalysis1/weiboTaskList.action";
	}else if(type ==3){
		url = njxBasePath+"/view/weiboEventAnalysis1/weiboCompleteList.action";
	}
    if(taskId!=""){
        url += "&analysisTask.taskId="+taskId;
    }
    $("#event_frame").attr("src",url);
    $("#zhezhao").fadeIn("slow");
    //$("body").css({overflow:"hidden"});    //���ù�����
    $("#eventDiv").fadeIn("slow");
}

function hideEventDiv(){
    $("#BgDiv").fadeOut("slow");
    //$("body").css({overflow:"auto"});
    $("#eventDiv").fadeOut("slow");
}

//����
function showBuy(){
    $('.zhezhao').fadeIn('slow');
    $('#buyPrompt_sj').css('display', 'block');
}


// �����¼���������
function buyAnalysis() {
    var packageNum = $('#packageNum').val();
    if (packageNum > 0) {
        var analysisForm = $('form[name="analysisForm"]')[0];
        if (analysisForm)
            analysisForm.submit();
    }
}

function count(){
    console.log(1);
    var packageNum = $('#packageNum').val();
    if (!isNaN(packageNum)) {
        $('input[name="myProducts[\'product\'].keywordPackageNum"]').val(packageNum);
        var price = $('input[name="myProducts[\'product\'].keywordPackagePrice"]').val();
        var totalPrice = price * packageNum;
        var youhuiPrice = 0;
        if (totalPrice >= 5000)
            youhuiPrice = totalPrice * 0.6;
        else if (totalPrice >= 4000)
            youhuiPrice = totalPrice - 1500;
        else if (totalPrice >= 3000)
            youhuiPrice = totalPrice - 1000;
        else if (totalPrice >= 2000)
            youhuiPrice = totalPrice - 500;
        else if (totalPrice >= 1000)
            youhuiPrice = totalPrice - 100;
        else
            youhuiPrice = totalPrice;

        $('#yuanjiaFont').text('��' + youhuiPrice.toFixed(2));
        if (youhuiPrice != totalPrice) {
            $('#jieshengSpan').text('Ϊ����ʡ��' + (totalPrice - youhuiPrice).toFixed(2) + 'Ԫ');
            $('#jieshengSpan').css('display', '');
        } else {
            $('#jieshengSpan').css('display', 'none');
        }
    }
}
