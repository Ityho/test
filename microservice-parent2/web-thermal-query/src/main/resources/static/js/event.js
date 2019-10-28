/**
 * Created by �޳��� on 2016/4/6.
 */

function toggleTab(type,obj){
    if($(obj).hasClass("current"))
        return;
    if(type == 1){
        location.href = "taskListRW.shtml";//�����б�
    }else if(type == 2){
        location.href = "completeList.shtml";//�����ɱ���
    }else if(type == 3){
        location.href = "templateList.action";
    }else if(type == 4){
        location.href = "oldReportList.action";
    }else if(type == 5){
        location.href = "analysisCase.shtml";//���䰸��
    }
}

//�����¼���������
function showEventFrame(type,taskId){
    var url = operateLogRequestPath+"/view/eventAnalysis/createAnalysis.action?createType="+type;
    if(taskId!=""){
        url += "&analysisTask.taskId="+taskId;
    }
    if($("#recordKeyword").length!=0&&$("#recordKeyword").val()!=""){
        url += "&analysisTask.incidentTitle="+encodeURIComponent(encodeURIComponent($("#recordName").val()));
        url += "&analysisTask.keyword="+encodeURIComponent(encodeURIComponent($("#recordKeyword").val()));
        if($("#recordFilterKeyword").val()!=""){
            url += "&analysisTask.filterKeyword="+encodeURIComponent(encodeURIComponent($("#recordFilterKeyword").val()));
        }
    }else if( $("#heatName").length!=0&& $("#heatName").val()!=null){
        url +="&type=1&analysisTask.incidentTitle="+encodeURIComponent(encodeURIComponent( $("#heatName").val()));
    }
    $("#event_frame").attr("src",url);
    /*$("#BgDiv").fadeIn("slow");*/
    //$("body").css({overflow:"hidden"});    //���ù�����
    $("#eventDiv").fadeIn("slow");
}

//����΢���¼���������
function showWeiBoEventFrame(type,taskId){
	var url = operateLogRequestPath+"/view/weiboEventAnalysis/createWeiBoAnalysis.action?createType="+type;
    if(taskId!=""){
        url += "&analysisTask.taskId="+taskId;
    }else if( $("#heatNameWB").length!=0&& $("#heatNameWB").val()!=null){
        url +="&type=1&analysisTask.incidentTitle="+encodeURIComponent(encodeURIComponent($("#heatNameWB").val()));
    }
	$("#event_frame").attr("src",url);
    /*$("#BgDiv").fadeIn("slow");*/
   // $("body").css({overflow:"hidden"});    //���ù�����
    $("#eventDiv").fadeIn("slow");
}

function hideEventDiv(){
    $("#heatNameWB").val("");
    $("#heatName").val("");
    $("#recordName").val("");
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