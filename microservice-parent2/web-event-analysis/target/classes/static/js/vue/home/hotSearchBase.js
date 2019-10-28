/**
 * Created by sty on 2018/6/27.
 */

var common_keyword_filter = /^[/0-9a-zA-Z\u4e00-\u9fa5\s\.\_\-\[\]@#_《》']+$/;
var common_keyword_char_filter = /[\s\.\-\[\]@#_《》']/g;

function checkKeywordFilter(keyword){

    if(keyword == null || keyword == ''){
        return false;
    }
    var newKeywords = $.trim(keyword);
    newKeywords = newKeywords.split(' ').join('');
    newKeywords = newKeywords.split('+').join('');
    newKeywords = newKeywords.split('|').join('');
    newKeywords = newKeywords.split('(').join('');
    newKeywords = newKeywords.split(')').join('');
    if(newKeywords == ''){
        return false;
    }
    if(!common_keyword_filter.test(newKeywords)){
        return false;
    }
    newKeywords = newKeywords.replace(common_keyword_char_filter, '');
    if(newKeywords.length == 0){
        return false;
    }
    return true;
};


function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}

var app = new Vue({
    el: '#app',
    data: {
        keywordStr:"",
    },
    created: function () {
    },
    methods: {
        getSearchDetail:function(title,keyword,filterKeyword){
            var keyword = keyword.replace(/\s+/g, "+");
            if(!keyword){
                swal("输入的内容不能为空");
                return;
            }
            if(keyword.length < 2){
                swal("内容至少输入2个字");
                return false;
            }
            if(!checkKeywordFilter(keyword)){
                swal("关键词只能包含中文、英文和数字");
                return false;
            }
            var arrKeyWord=keyword.split(/\+|\|/);
            if(arrKeyWord.length>1){
                for(var i=0;i<arrKeyWord.length;i++){
                    var charStr=arrKeyWord[i];
                    if($.trim(charStr).length<2){
                        swal("您的方案中不能出现单字词语，请修改后重新查询!");
                        return false;
                    }
                }
            }
            this.keywordStr = keyword;
            $.ajax({
                url : actionBase + '/checkSensitive',
                type : 'POST',
                data : {
                    'index':1,
                    'keyword' :keyword
                },
                success : function(res) {

                    if (res.status != 0){
                        $.ajax({
                            url : actionBase + '/doSearchShareCode',
                            type : 'POST',
                            data : {
                                'title':title,
                                'keyword' :keyword,
                                'filterKeyword':filterKeyword
                            },
                            success : function(res) {
                                if (res != null){
                                    window.location.href = actionBase+"/searchDetailsShare?shareCode="+res.obj;
                                }else {
                                    window.location.href = actionBase + "/searchDetails?title="+encodeURIComponent(title)+"&keyword="+encodeURIComponent(keyword)+"&filterKeyword="+encodeURIComponent(filterKeyword);
                                }
                            }
                        });
                    }else {
                        $('#wui-modal-tootips2').modal('show');
                        // swal("对不起，您的关键词包含敏感词语，请检查后再试！");
                        return false;
                    }
                }
            });
        },
    }
});