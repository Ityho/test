/**
 * Created by sty on 2018/6/27.
 */
Vue.filter("formatDate",function(date,formatStr){
    var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
    return moment(date).format(formatStr);
});
var app = new Vue({
    el:'#app',
    data: {
        eventCount:0,
        nickname:'',
        duringData:'',
        biiIndex:0,//微博传播指数
        cumulativeIndex: 0,//累计指数
        publishingIndex: 0,//发布指数
        interactionIndex: 0,//互动指数
        activeIndex: 0,//活跃指数
        firstRankId: 0,
        secondRankId:0,
        noMessage:true,
        nofirstRankId:true,
        nosecondRankId:true


    },
    created:function(){
        // var searchObj ={labels:-1};
        this.getUserResult();
    },
    methods: {
        getum: function () {
            var nickname=$("#nick").text();
            nickname=nickname.substring(1,nickname.length);
            nickname=encodeURI(nickname);
            window.open("https://www.u-mei.com/check/add_no_user?extendtype=030041&name="+nickname)
        },
        getum1: function () {
            var nickname=$("#searchNme").val();
            nickname=encodeURI(nickname);
            window.open("https://www.u-mei.com/check/add_no_user?extendtype=030041&name="+nickname)
        },
        jp: function (num) {
            window.open("https://www.u-mei.com/check/check_new_t?extendtype=030041&type="+num)
        },
        rotateLine: function (num) {
            var opt = $.extend(true, {}, echartsOpts["yibiaopan"]);
            opt.series[1].axisLine.lineStyle.color=[
                [num / 100, new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
                    offset: 0,
                    color: '#fad202'
                },
                    {
                        offset: 1,
                        color: '#fef901'
                    }
                ])],
                [
                    1, 'rgba(0,0,0,0)'
                ]
            ]
            opt.series[1].data[0].name="微博影响力";
            opt.series[1].data[0].value=num;
            setEchartsOpion({
                $id : $("#um-dashboard-echart"),
                opt : opt
            });

        },
        getUserResult: function () {
            var umBlueVUser=$("#umBlueVUser").val();
            if(umBlueVUser!="false"){
                $.post(njxBasePath + '/getUmResult', {type:1}, function (res) {
                    if(res!=null){
                        if(res.code=="200"){
                            var list=res.data;
                            app.nickname=list.screenName;
                            app.biiIndex=list.biiIndex;

                            app.cumulativeIndex=list.cumulativeIndex;
                            app.publishingIndex=list.publishingIndex;
                            app.interactionIndex=list.interactionIndex;
                            app.duringData=list.rankDate;
                            app.activeIndex=list.activeIndex;
                            app.rotateLine(list.biiIndex);
                            if(list.firstRankId==null||list.firstRankId==''||list.firstRankId ==0){
                                app.nofirstRankId=false;
                            }else{
                                app.firstRankId= list.firstRankId;
                            }
                            if(list.secondRankId==null||list.secondRankId==''||list.secondRankId ==0){
                                app.nosecondRankId=false;
                            }else{
                                app.secondRankId=list.secondRankId;
                            }
                        }
                    }else if(res.code=="0401"){
                        app.noMessage=false;
                        // $('#myModal').modal('show');
                    }else{
                        swal({
                            title: "该账号不存在，请输入正确的微博昵称！！",
                            timer: 3000,
                            showConfirmButton: false
                        });

                    }
                });
            }
        },
        goSearch: function () {
            var searchNme=$("#searchNme").val();
            if (searchNme!=''&&searchNme!=null){
                $.post(njxBasePath + '/getUmResult', {type:2,nickname:searchNme}, function (res) {
                    if(res!=null){
                        if(res.code=="200"){
                            app.nofirstRankId=true;
                            app.nosecondRankId=true;
                            var list=res.data;
                            app.nickname=list.screenName;
                            app.biiIndex=list.biiIndex;
                            app.cumulativeIndex=list.cumulativeIndex;
                            app.publishingIndex=list.publishingIndex;
                            app.interactionIndex=list.interactionIndex;
                            app.duringData=list.rankDate;
                            app.activeIndex=list.activeIndex;
                            app.rotateLine(list.biiIndex);
                            if(list.firstRankId==null||list.firstRankId==''||list.firstRankId==0){
                                app.nofirstRankId=false;
                            }else{
                                app.firstRankId= list.firstRankId;
                            }
                            if(list.secondRankId==null||list.secondRankId==''||list.secondRankId==0){
                                app.nosecondRankId=false;
                            }else{
                                app.secondRankId=list.secondRankId;
                            }

                        }else if(res.code=="0401"){
                            // app.noMessage=false;
                            $('#myModal').modal('show');
                        }else{
                            swal({
                                title: "该账号不存在，请输入正确的微博昵称！！",
                                timer: 3000,
                                showConfirmButton: false
                            });

                        }

                    }

                });
            }
        }
        //热门人物排行

    }
});