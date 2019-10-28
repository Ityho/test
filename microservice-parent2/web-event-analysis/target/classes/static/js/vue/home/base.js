
/*
   刷新用户缓存
 */
function refreshUser(){
    $.ajax({
        url : actionBase + '/refreshUser',
        type : 'POST',
        data : {},
        success : function(res) {
        }
    });
};