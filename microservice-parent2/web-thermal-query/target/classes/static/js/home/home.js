/**
 * Created by sty on 2018/6/27.
 */
Vue.filter("formatDate", function (date, formatStr) {
    var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
    return moment(date).format(formatStr);
});
var app = new Vue({
    el: '#home',
    data: {
        // activeTab: 1,
        activeTab: 1,
        keyWordLogs:[],//搜索记录总数据
        show:false,//搜索记录显示控制
    },
    created: function () {
        let str = window.location.href;
        if (str.indexOf("?") !== -1 && str.indexOf("=") !== -1 && str.indexOf("page") !== -1) {
            let page = parseInt(str.split("?")[1].split("=")[1]);
            this.activeTab = page;
        }
        particlesJS.load('particles-js', staticResourcePath + '/css/indexV4/global/vendor/particles/particlesbase.json');
        this.goTab(this.activeTab);
    },
    methods: {
        goTab: function (tab) {
            this.activeTab = tab;
            if (tab == 1) {
                if (!$("#activeTab1 .data-content").html().length) {
                    $.ajax({
                        url: actionBase + "/hot/events/goHotEvent", success: function (html) {
                            $("#activeTab1 .data-content").html(html);
                            var loginsign= $("#loginsign").val();
                            if(loginsign==1){
                                $("#loginModal").modal("show");
                            }
                        }
                    });
                }
            } else if (tab == 2) {
                if (!$("#activeTab2 .data-content").html().length) {
                    $.ajax({
                        url: actionBase + "/major/events/goBigness", success: function (html) {
                            $("#activeTab2 .data-content").html(html);
                        }
                    });
                }
            } else if(tab == 3){
                if (!$("#activeTab3 .data-content").html().length) {
                    $.ajax({
                        url: actionBase + "/hot/people/goHotPeople", success: function (html) {
                            $("#activeTab3 .data-content").html(html);
                        }
                    });
                }
            } else if (tab == 4) {
                if (!$("#activeTab4 .data-content").html().length) {
                    $.ajax({
                        url: actionBase + "/hot/weibo/goHotWeiBoList", success: function (html) {
                            $("#activeTab4 .data-content").html(html);
                        }
                    });
                }
            }
        },
        goto: function (num) {
            var data = {"notLoginOperateRecord.operateType": num};
            $.ajax({
                // url: baseAction + "/view/hotSearch/recordOperateInfo.action",
                url: baseAction + "/shouye/active/recordOperateInfo",
                type: "post",
                data: data,
                success: function () {
                    $("#loginModal").modal();
                }
            })
        },
        //查询搜索记录
        searchHistory: function () {
            this.show=true;
            this.keyWordLogs=[];
            $.ajax({
                url: actionBase + "/keyWordLogs.action?" + new Date(),
                type: "get",
                async:false,
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        for (let i=0;i<data.size;i++){
                            let kw= data.logs["keyWord" + i];
                            app.keyWordLogs.push(kw)
                        }
                    }

                }, error: function (data) {
                    setTimeout(function () {
                        app.show=false;
                    },2000);

                }
            });
        },
        //鼠标失去焦点搜索记录消失
        miss:function () {
            setTimeout(function () {
                app.show=false;
            },200)

        },
        // 历史记录点击事件
        gokw:function (vl) {
            $("#title").val(vl);
            $("#keyword").val(vl);
            var $form = $("#searchForm");
            $form.prop("action", actionBase + "/goSearch.shtml");
            $form.submit();
        },
        //删除历史搜索记录
        del:function () {
            $.ajax( {
                url : njxBasePath+"/keyWordLogs_deleteAll_type.action?type=1" ,
                type : "get",
                success : function(data) {
                    setTimeout(function () {
                        app.show=false;
                    },500)
                },
                error : function(data) {
                    alert("删除失败!");
                }
            });
        }
    }
});