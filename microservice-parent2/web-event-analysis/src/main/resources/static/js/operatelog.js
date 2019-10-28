/**
 * 记录用户浏览行为日志
 */
function saveOperateLog(operateLogPageName,operateLogPageCode){
    if (typeof operateLogPageCode != 'undefined' && $.trim(operateLogPageCode) != '' && typeof operateLogPageName != 'undefined') {
        //console.log(operateLogPageName);
        $.ajax({
            url : actionBase + '/operatePageLog',
            type : 'POST',
            data : {
                'pageCode' : operateLogPageCode,
                'pageName' : operateLogPageName
            },
            success : function(){}
        });
    }
}