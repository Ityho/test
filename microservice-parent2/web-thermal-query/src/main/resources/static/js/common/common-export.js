var jquery = $;
// �����б�(����)
function ExportExcelCommon(){
    var checkIdAll=document.getElementById("checkIdAll").checked;
    var exportDataCount=0;
    var st = typeof $('#starttime').val()=='undefined'?'':$('#starttime').val();//��ʼʱ��
    var et = typeof $('#endtime').val()=='undefined'?'':$('#endtime').val();//����ʱ��
    var td = typeof $('#timeDomain').val()=='undefined'?'':$('#timeDomain').val();//���ʱ��
    var origina = "";//��Դ����  0:ȫ����1:��վ��2:��̳��3:���ͣ�4:���ɣ�5:΢����6:���⣬7:΢�ţ�8:��Ƶ��9:���ţ�10:������11:����12:������13:�ͻ��ˣ�default:0
    var i = 0;
    $.each($("#statList a"),function(){
        if($(this).hasClass("cur")){
            i++;
            var originas = $(this).attr("id").split("_")[1];
            /*if (originas==8) {
                originas=7;
            }else if(originas==11){
                originas=10;
            }else if(originas==10){
                originas=11;
            }else if(originas==12){
                originas=8;
            }*/
            origina += originas+",";
        }
    });
    if(i==0){
        origina = "1";
    }else if(i>=($("#statList a").size()-1)){
        origina = "1";
    }/*else{
        origina = origina.substr(0,origina.length-1);
    }*/
    var paixu = typeof $("#paixu").val()=='undefined'?'':$('#paixu').val();//��Ϣ����
    var otherAttribute = typeof $("#otherAttribute").val()=='undefined'?'':$('#otherAttribute').val();//��Ϣ����
    var solrType = typeof $("#solrType").val()=='undefined'?'':$('#solrType').val();//ƥ�䷽ʽ
    var zhaiyaoShow = typeof $('#zhaiyaoShow').val()=='undefined'?'':$('#zhaiyaoShow').val();//��ʾ��ʽ
    var newlstSelect = typeof $("#newlstSelect").val()=='undefined'?'':$('#newlstSelect').val();//�������
    var comblineflg = typeof $("#comblineflg").val()=='undefined'?'':$('#comblineflg').val();//��������
    
    //������Դ
    var keywordId = typeof $('#kw\\.keywordId').val()=='undefined'?'':$('#kw\\.keywordId').val();
    var website = typeof $('#website').val()=='undefined'?'':$('#website').val();
    var province = typeof $('form[name="frmPopWin"] #province').val()=='undefined'?'':$('form[name="frmPopWin"] #province').val();
    var directSet = typeof $('form[name="frmPopWin"] #directSet').val()=='undefined'?'':$('form[name="frmPopWin"] #directSet').val();//������
    directSet = directSet==""?0:directSet;
    if(directSet!=0||directSet!=1){
        directSet=encodeURIComponent(encodeURIComponent(directSet));
    }
    var directType = typeof $('#directType').val()=='undefined'?'':$('#directType').val();
	
	//��������
	var solrSecondType = typeof $("#solrSecondType").val()=='undefined'?'':$('#solrSecondType').val();
	var secondSearchWord = typeof $("#secondSearchWord").val()=='undefined'?'':$('#secondSearchWord').val();
    //��ͬ����
    var similarCon = typeof $('#similarCon').val()=='undefined'? 0 :$('#similarCon').val();
    var titleHs = typeof $('#con\\.titleHs').val()=='undefined'?'':$('#con\\.titleHs').val();
    
    var publishedWeek = typeof $('#publishedWeek').val()=='undefined'?'':$('#publishedWeek').val();
    var publishedHourInDay = typeof $('#publishedHourInDay').val()=='undefined'?'':$('#publishedHourInDay').val();
    if(similarCon != 1 && checkIdAll){
        
        var url = actionBase + "/lookInformation.action?timeDomain="+td+"&mcFilterOrigina="+origina+"&starttime="+st+
            "&endtime="+et+"&paixu="+paixu+"&otherAttribute="+otherAttribute+"&solrType="+solrType+"&zhaiyaoShow="+
            zhaiyaoShow+"&comblineflg="+comblineflg+"&directSet="+directSet+"&newlstSelect="+newlstSelect+"&keywordId="+
            keywordId+"&directType="+directType+"&similarCon="+similarCon;
        if(titleHs != '')
        	url += "&con.titleHs="+titleHs;
        if(publishedWeek != '')
        	url += "&publishedWeek="+publishedWeek;
        if(publishedHourInDay != '')
        	url += "&publishedHourInDay="+publishedHourInDay;
        if(website != '')
        	url += "&website="+website;
        if(province != '')
        	url += "&province="+province;
        $("#exportInformationFrame").html("");
        $("#exportInformationFrame").attr("src",url);
        jquery("#exportInforModal").modal();
    }else{
        var checkedNum = getCheckIDsNum();
        var checkedIds = getCheckIDs();

    	if(!checkIdAll){
	        if (checkedNum < 1) {
	            showMsgInfo(0,"��ѡ����Ҫ��������Ϣ!",0);
	            return ;
	        }
    	}else{
    		checkedNum = 0;
    		checkedIds = "";
    	}
        exportDataCount=$("#exportDataCount").val();
        $("#checkedIdsXls").val(checkedIds);

    	var params = {
    			'keywordId' : keywordId,
    			'checkedIds' : checkedIds,
    			'mcFilterOrigina' : origina,
    			'timeDomain' : td,
    			'starttime' : st,
    			'endtime' : et,
    			'paixu' : paixu,
    			'otherAttribute' : otherAttribute,
    			'solrType' : solrType,
    			'zhaiyaoShow' : zhaiyaoShow,
    			'comblineflg' : comblineflg,
    			'directSet' : directSet,
    			'directType' : directType,
    			'website' : website,
    			'province' : province,
    			'newlstSelect' : newlstSelect,
    			'similarCon' : similarCon,
    			'con.titleHs' : titleHs,
    			'solrSecondType' : solrSecondType,
    			'secondSearchWord' : secondSearchWord,
    			'publishedWeek' : publishedWeek,
    			'publishedHourInDay' : publishedHourInDay
    	}
    	
        if(!checkIdAll && exportDataCount >= checkedNum) {	//��ȫѡ�����㹻�ĵ�������
            exportEXCELTXTCSV(1, null, params);
        }else{
        	$.ajax({
                url: njxBasePath + '/createExportCondition.action',
                type: 'POST',
                data: params,
                success: function (result) {
                    if (result.code == 200) {
                    	if(result.estimateNumber <= exportDataCount){	//���㹻�ĵ�������
                    		exportEXCELTXTCSV(1, result.conditionId, null);
                    	}else{
                    		$("#exportPay").val(1);//�ж�ִ�е���
                    		var params2 = {'packageCount': result.estimateNumber, 'exportConditionId' : result.conditionId};
                    		openBuyCommon(13, params2);
                    	}
                    } else {
                        showMsgInfo(0, result.message, 0);
                    }
                }
            });
        }
    }
}

function clickTooFrequent(){
	showMsgInfo(0, '�ף����Ĳ���̫����!', 0);
}

function exportPic(){
	$("#exportPicBtn").attr("onclick", "clickTooFrequent()");
	setTimeout(function(){
		$("#exportPicBtn").attr("onclick", "exportPic()");
	}, 5000);
    var obj = document.getElementById("exportInformationFrame").contentWindow;
    var doms = $("input:checkbox[name='dataDimensionality']", obj.document);
    var writeUser = $("#writeUserFlag", obj.document).val();
    var isCheckbox = true;
    for(var i = 0 ; i < doms.length ; i++){
        if($(doms[i]).prop("checked")){
            isCheckbox = false;
            break;
        }
    }
    if(isCheckbox && '1' === writeUser){
        showMsgInfo(0, '��ѡ������ά��!', 0);
        return false;
    }

    $.ajax({
        url: njxBasePath + '/createExportTask.action',
        type: 'POST',
        data: $("form[name=frmPopWin4]", obj.document).serialize(),
        success: function (resultJson) {
            if (resultJson.code == 200) {
                var exportDataCount = 0;
                var obj = document.getElementById("exportInformationFrame").contentWindow;
                jquery("#exportInforModal").modal("hide");
                var message = $("#message", obj.document).val();
                var number = resultJson.estimateNumber;
                if (number == 0 || message) {
                    showMsgInfo(0, '�ü�ⷽ��û������!', 0);
                    return;
                }
                exportDataCount = $("#exportDataCount").val();
                if (exportDataCount >= parseInt(number)) {
                    exportEXCELTXTCSV(1, $("#exportConditionId", obj.document).val(), null);
                } else {
                    var params= {'packageCount': number};
                    params.exportConditionId = $("#exportConditionId", obj.document).val();
                    jquery("#exportPay").val(1);//�ж�ִ�е���
                    openBuyCommon(13, params);
                }
            } else {
                showMsgInfo(0, resultJson.message, 0);
            }
        }
    });
}

function recordUserLog(code,name){
    $.ajax({
        url : njxBasePath + '/view/hotSearch/recordUserLog.action',
        type : "post",
        data:{'operateLogObject.productPageCode':code,'operateLogObject.productPageDesc':name},
        success : function(data){

        }
    })
}

function skipUrl(){
    location.href = njxBasePath + '/exportInfortmation.shtml';
}

//��ⷽ������
function exportEXCELTXTCSV(orderRecordId, conditionId, params){
	
    var obj=document.getElementById("exportInformationFrame").contentWindow;
    var url=njxBasePath + '/confirmExportTask.action';
    recordUserLog(1046,"��ⷽ�����ݵ�������id :"+orderRecordId);
    if(orderRecordId) {
        $("#orderRecordId",obj.document).val(orderRecordId);
        $("#kwOrderRecordId").val(orderRecordId);
    }
    if(orderRecordId > 1){	//��������
    	params = {'orderRecordId' : orderRecordId};
    }else{	//���㹻����������۳�
    	if(params == null && conditionId == null){
    		showMsgInfo(0, "ϵͳ��С����������ϵ�ͷ����Ժ�����~", 0);
    		return false;
    	}else if(conditionId > 0){
			params = {
				'orderRecordId' : 1,
				'conditionId' : conditionId
			}
    	}else{
    		params.orderRecordId = 1;
    	}
    }
    $.ajax({
        url:url,
        type:'POST',
        data:params,
        success:function(result){
        	if(result.code == 200){
                $("#exportName").text(result.keywordName);
                $("#orderRecordId", obj.document).val(0);
                $("#exportConditionId", obj.document).val(0);
                $("#exportDataCount").val(result.exportDataCount);
                //����������ʾ��ʾ
                $('#top #izl_rmenu a .btn-export').addClass("btn-highlight high-dataload");
                $('#top #izl_rmenu').css({"z-index": "1042"});
                jquery("#myModal4").modal();
        	}else{
        		showMsgInfo(0, result.message, 0);
        	}
        }
    });
}

//�ҵ��ղص���
function favoriteExportEXCELTXTCSV(orderRecordId, conditionId, params){
	
    var number=($("#checkedIds").val().split(',')).length-1;
    var url=njxBasePath + '/confirmExportTask.action';
    if(orderRecordId){
        $("#orderRecordId").val(orderRecordId);
    }
    if(orderRecordId > 1){	//��������
    	params = {'orderRecordId' : orderRecordId};
    }else{	//���㹻����������۳�
    	if(params == null && conditionId == null){
    		showMsgInfo(0, "ϵͳ��С����������ϵ�ͷ����Ժ�����~", 0);
    		return false;
    	}else if(conditionId > 0){
			params = {
				'orderRecordId' : 1,
				'conditionId' : conditionId
			}
    	}else{
    		params.orderRecordId = 1;
    	}
    }
    $.ajax({
        url:url,
        type:'POST',
        data:params,
        success:function(result){
        	if(result.code == 200){
                $("#exportDataCount").val(result.exportDataCount);
                //����������ʾ��ʾ
                $('#top #izl_rmenu a .btn-export').addClass("btn-highlight high-dataload");
                $('#top #izl_rmenu').css({"z-index": "1042"});
                jquery("#myModal4").modal();
        	}else{
        		showMsgInfo(0, result.message, 0);
        	}
        }
    });
}
