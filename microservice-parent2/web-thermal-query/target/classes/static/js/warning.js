/**
 * 关键词修改添加JS
 * 
 * @author liubei 2014-05-22
 */
var isIE = !!document.all;

var ieRange = false, editor, win, doc;

 
function goBack() {
	parent.isLoginFrm(function() {
		frmPopWin.action = "sys_warning_find.action";
		frmPopWin.target = "_self";
		frmPopWin.submit();
	});
}

function changeColor(obj) {
	obj.value = '';
	obj.style.color = "#000000";
}

 
 function checkAllMenu(el_name,parent)  {
	var elements = document.getElementsByName(el_name);
	
	var parentChecked = parent.checked;

	
	for (var i=0;i<elements.length;i++)    {
			var e = elements[i];

			e.checked = parentChecked;
				
		}
		

}
/**
 * 暂停预警
 * 
 * @param href
 * @return
 */
function goPause(id) {
	 
	var turl = "warning_pause.action?warning.id=" + id  ;
 
		$.ajax( {
			url : turl,
			type : 'POST',
			dataType : 'json',
			success : function(retMsg) {
				if (retMsg.msg) {
					 //document.getElementById("warningStatus_"+id).className	= 'OFF';
					// document.getElementById("warningStatus_"+id).innerHTML	=	"OFF"; 

					//document.getElementById("warn_op_"+id).innerHTML="<a class=\"warn_op\" href=\"javascript:goUse('"+ id + "');\">启动</a>";
 
				}
				 
			}
		});
	 
}
/**
 * 启动预警
 * 
 * @param href
 * @return
 */
function goUse(id) {
	
	var turl = "warning_start.action?warning.id=" + id ;
	 
		$.ajax( {
			url : turl,
			type : 'POST',
			dataType : 'json',
			success : function(retMsg) {
				if (retMsg.msg) {
					//document.getElementById("warningStatus_"+id).className	= 'ON';
					// document.getElementById("warningStatus_"+id).innerHTML	=	"ON"; 
					// document.getElementById("warn_op_"+id).innerHTML="<a class=\"warn_op\" href=\"javascript:goPause('"+ id + "');\">暂停</a>";
				}
			}
		});
	 
}


/**
 * 暂停app预警
 * 
 * @param href
 * @return
 */
function goAppPause(id) {
	 
	var turl = "warningApp_pause.action?warning.id=" + id  ;
 
		$.ajax( {
			url : turl,
			type : 'POST',
			dataType : 'json',
			success : function(retMsg) {
				if (retMsg.msg) {
					// document.getElementById("warningAppStatus_"+id).className	= 'OFF';
					 //document.getElementById("warningAppStatus_"+id).innerHTML	=	"OFF"; 

					//document.getElementById("warn_app_"+id).innerHTML="<a class=\"warn_app\" href=\"javascript:goAppUse('"+ id + "');\">启动</a>";
 
				}
				 
			}
		});
	 
}
/**
 * 启动app预警
 * 
 * @param href
 * @return
 */
function goAppUse(id) {
	
	var turl = "warningApp_start.action?warning.id=" + id ;
	 
		$.ajax( {
			url : turl,
			type : 'POST',
			dataType : 'json',
			success : function(retMsg) {
				if (retMsg.msg) {
					//document.getElementById("warningAppStatus_"+id).className	= 'ON';
					// document.getElementById("warningAppStatus_"+id).innerHTML	=	"ON"; 
					 //document.getElementById("warn_app_"+id).innerHTML="<a class=\"warn_app\" href=\"javascript:goAppPause('"+ id + "');\">暂停</a>";
				}
			}
		});
	 
}

 
 
function checkAllMenu(el_name, parent) {
	var elements = document.getElementsByName(el_name);

	var parentChecked = parent.checked;

	for ( var i = 0; i < elements.length; i++) {
		var e = elements[i];

		e.checked = parentChecked;

	}

}

function GetRandom(n) {
	GetRandomn = Math.floor(Math.random() * n + 1);
	return GetRandomn;
}


//预警方法相关
function setWarningClick(){

    var saveWarning = true;

    var origina = "";
    var i = 0;
    $.each($("#originaList :checkbox"),function(){
        if($(this).parent().hasClass("checked")){
            i++;
            origina += $(this).val()+",";
        }
    });
    if(i==0){
        showMsgInfo(0,"请至少选择一个来源类型！",0);
        return false;
    }else if(i>=($("#originaList input").size()-1)){
        origina = "0";
    }else{
        origina = origina.substr(0,origina.length-1);
    }
    $("#sourceCondition").val(origina);


    var intervalLabel = $("#isInterval").find('label');
    var checkLabel = intervalLabel[1];
    if($("#timing:visible").length==1){
        checkLabel = intervalLabel[0];
    }
    var IntervalNum = $(checkLabel).find("input:eq(1)").val();
    if(IntervalNum==0){
        $("#intervalTime").val(0);
    }
    if (IntervalNum == 1) {
        $("#intervalTime").val(60);
    }
    if (IntervalNum == 2) {
        $("#intervalTime").val(120);
    }
    if (IntervalNum == 3) {
        $("#intervalTime").val(180);
    }
    if (IntervalNum == 4) {
        $("#intervalTime").val(240);
    }
    if (IntervalNum == 5) {
        $("#intervalTime").val(300);
    }
    if (IntervalNum == 6) {
        $("#intervalTime").val(360);
    }
    if (IntervalNum == 7) {
        $("#intervalTime").val(420);
    }
    if (IntervalNum == 8) {
        $("#intervalTime").val(480);
    }
    
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(startTime == null || startTime == ""){
		showMsgInfo(0,"请设置接收开始时间！",0);
        return false;
	}
	if(endTime == null || endTime == ""){
		showMsgInfo(0,"请设置接收结束时间！",0);
        return false;
	}
    
    if($("#startTime").val()>$("#endTime").val()){
        showMsgInfo(0,"开始时间不能大于结束时间！",0);
        return false;
    }

    var cemails = "";
    var emailNum = 0;
    var address = mailCheck();

    $("#mailAdds").find('label').each(function(){
        var inputObj = $(this).find('input[name=email]');
        if($(inputObj).is(':checked')==true){
            cemails =cemails+$(inputObj).val()+",";
            emailNum++;
        }
    });


    if($("#weiboSw:checked").length==0&&$("#emailSw:checked").length==0&&$("#appSw:checked").length==0){
        showMsgInfo(0,"最少选择一种预警方式！",0);
        return;
    }
    if($.trim(address)==""&&cemails.length<=1&&$("#emailSw:checked").length==1){
        showMsgInfo(0,"请选择或填写下发预警的邮件地址！",0);
        saveWarning = false;
    }else if(cemails.length>1){
        $("#cemails").val(cemails.substring(0,cemails.length-1));
    }

    if(emailNum>3){
        showMsgInfo(0,"邮件地址最多为3个！",0);
        return;
    }

    var cids = $("#checkedKeywords").val();

    if (cids.indexOf(",")==0){
        cids = cids.substr(1,cids.length-1);

        $("#checkedKeywords").val(cids);
    }
    if (cids==''){

        showMsgInfo(0,"请选择需要预警的监测方案！",0);
        saveWarning = false;
    }

    if (saveWarning){
        $.ajax( {
            url : actionBase + "/warning_edit.action",
            type : 'POST',
            data : $("form[name=frmPopWin2]").serialize(),
            dataType : 'json',
            success : function() {
                location.reload();
            },
            complete:function(){
                location.href = actionBase + "/keywordSearch.action?tab=5&kw.keywordId="+document.getElementById("kw.keywordId").value;
            }
        });
    }


}

//日报相关方法
function setDayReportClick(){
    var cemailsDay = "";
    var emailNumDay = 0;
    $("#mailAddsDay").find('label').each(function () {
        var inputObj = $(this).find('input[name=email]');
        if ($(inputObj).is(':checked') == true) {
            cemailsDay = cemailsDay + $(inputObj).val() + ",";
            emailNumDay++;
        }
    });

    if ($("#emailSwitch:checked").length == 0 && $("#appSwitch:checked").length == 0 && $("#weiboSwitch:checked").length == 0) {
        showMsgInfo(0, "最少选择一种接收方式！", 0);
        return;
    }
    if (cemailsDay.length < 1 && $("#emailSwitch:checked").length == 1) {
        showMsgInfo(0, "请选择或填写下发接收的邮件地址！", 0);
        return;
    } else if (cemailsDay.length > 1) {
        $("#cemailsDay").val(cemailsDay.substring(0, cemailsDay.length - 1));
    }
    if (emailNumDay > 3) {
        showMsgInfo(0, "邮件地址最多为3个！", 0);
        return;
    }

    $.ajax( {
        url : actionBase + "/saveReportSet.action",
        type : 'POST',
        data : $("form[name=frmPopWin3]").serialize(),
        dataType : 'json',
        success : function(data) {
            if(data){
                window.parent.location.href = actionBase + "/keywordSearch.action?tab=6&kw.keywordId="+document.getElementById("kw.keywordId").value;
            }
        }/*,
         complete:function(){
         location.href = actionBase + "/keywordSearch.action?tab=5&kw.keywordId="+document.getElementById("kw.keywordId").value;
         }*/
    });

}


function CheckOrNot(obj){
    if($(obj).find('span').eq(0).find('input').is(':checked')==true){
        $(obj).parent('li').addClass('cur');
    }else{
        $(obj).parent('li').removeClass('cur');
    }
}

//邮件地址检查的回调函数
function CheckCallBack(result){
    if(null!=result && result>0){
        showMsgInfo(0,"该邮箱地址已存在，请重新输入!",0);
        $("#mailAddress").val("");
        $("#mailAddressDay").val("");
        mailFlag = false;
        return;
    }
    if(null!=result && result==0){
        mailFlag = true;
        mailSend();
    }
}

//邮件发送是否成功的回调函数
function SendMailCallBack(result) {
    //var sendStat = document.getElementById("sendStatus");
    if(null!=result){
        var type =$("#mailType").val();
        var address = $("#mailAddress").val();
        if(type && type == 2){
            address = $("#mailAddressDay").val();
        }
        if(result[1]==2){
			showMsgInfo(0,"请求太频繁，请稍后再试！",0);
		}else if(result[1]==1){
            showMsgInfo(0,"邮箱添加成功!",0);
        }else if(result[1]==0){
            showMsgInfo(0,"激活邮件发送失败，请检查您输入的邮箱地址!",0);
        }
        if(result[0]>0 && address!=""){
            var  liHtml = '<li><label><input checked="checked" name="email" type="checkbox" value="'+result[0]+'">'
            +address+'</label></li>';
            $("#mailAdds").append(liHtml);
            $("#mailAddress").val("");
            $("#mailAddsDay").append(liHtml);
            $("#mailAddressDay").val("");
            $('#mailAdds input:last').on('ifCreated ifClicked ifChanged ifChecked ifUnchecked ifDisabled ifEnabled ifDestroyed', function(event){
            }).iCheck({
                checkboxClass: 'icheckbox_square-orange',
                radioClass: 'iradio_square-orange',
                increaseArea: '20%'
            });
            $('#mailAddsDay input:last').on('ifCreated ifClicked ifChanged ifChecked ifUnchecked ifDisabled ifEnabled ifDestroyed', function(event){
            }).iCheck({
                checkboxClass: 'icheckbox_square-orange',
                radioClass: 'iradio_square-orange',
                increaseArea: '20%'
            });
        }
    }

}


//暂停邮件
function goUse1(id,type,obj,isSina){
    var count = $(obj).parent('div').parent('label').parent('div').parent('td').find('input[name=warningContactCount]').val();
    if(isSina==1){
        $(obj).parent('div').parent('label').removeAttr('for');
        goEdit();
    }else{
        if(count==0){
            $(obj).parent('div').parent('label').removeAttr('for');
            goEdit();
        }else{
            goUse(id,type);
        }
    }

}
function goUse2(id,type,obj,isSina){
    obj=$(obj).parent().find(".onoffswitch-inner .onoffswitch-inactive");
    goUse1(id,type,obj,isSina);
}

function goDeleteAddrSingle(id,type,warningId){
    showMsgInfo(0,"删除收件人可能会引起您无法正常接收邮件，确定要删除吗？",1);
    $(".submitBtn").html("确定");
    $(".submitBtn").one("click",function(){
        $.ajax( {
            url : "mailAddr_delete.action",
            type : 'POST',
            data : {checkedIds: id+",",type:type,warningId:warningId },
            dataType : 'json',
            success : function(retMsg) {
                if (retMsg.msg) {
                    hideInfoDiv();
                    $("#addr_tr_"+id).css( {
                        "display" : "none"
                    });
                    //只是删除和预警关联邮箱时 需要刷新
                    if(retMsg.type==2){
                        location.reload();
                    }
                }
            }
        });
    });
}

function deleteBatchMail(type){
    var ids = "";
    $("#mailAdds input[name=email]:checked").each(function(){
        ids += $(this).val()+",";
    });
    if(type && type == 2){
        ids = "";
        $("#mailAddsDay input[name=email]:checked").each(function(){
            ids += $(this).val()+",";
        });
    }
    if(ids==""){
        showMsgInfo(0,"请选择要删除的邮箱",0);
        return;
    }
    showMsgInfo(0,"删除收件人可能会引起您无法正常接收邮件，确定要删除吗？",1);
    $(".submitBtn").html("确定");
    $(".submitBtn").one("click",function(){
        $.ajax( {
            url : "deleteMail.action",
            type : 'POST',
            data : {checkedIds: ids},
            dataType : 'json',
            success : function(retMsg) {
                if (retMsg.msg) {
                    hideInfoDiv();
                    $("#mailAdds input[name=email]:checked").each(function(){
                        var cmails = $("#cemails").val();
                        $("#cemails").val(cmails.replace($(this).val(),""));
                        $(this).parents("li").hide().empty();
                        if ($("#checkAll").hasClass('active')) {
                            $("#checkAll").removeClass("active");
                        }
                    });

                    $("#mailAddsDay input[name=email]:checked").each(function(){
                        var cmails = $("#cemails").val();
                        $("#cemails").val(cmails.replace($(this).val(),""));
                        $(this).parents("li").hide().empty();
                        if ($("#checkAll").hasClass('active')) {
                            $("#checkAll").removeClass("active");
                        }
                    });
                }
            }
        });
    });
}

function goEdit(){
    $("#warningSet").show();
}

function goDailyEdit(){
    $("#dailySet").show();
}

function changeSource(obj){
    var sources=$(obj).find('input').val();
    if (sources != "0") {
        $("#source0").removeClass('active');
    } else {
        $("#isSource").find('label').removeClass('active');
    }
}

function provinceWarning(){
    var province=$("#province").val();
    if(province!=""&&province!=null){
        $('ol.hasallcity a').each(function () {
            if ($(this).text() == province) {
                $(this).addClass('cur');
            }
        });
    }

}

 