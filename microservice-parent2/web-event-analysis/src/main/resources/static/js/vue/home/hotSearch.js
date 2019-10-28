/**
 * Created by sty on 2018/6/27.
 */

var common_keyword_filter = /^[/0-9a-zA-Z\u4e00-\u9fa5\s\.\_\-\[\]@#_《》']+$/;
var common_keyword_char_filter = /[\s\.\-\[\]@#_《》']/g;

function checkKeywordFilter(keyword){

    if(keyword == null || keyword == ''){
        return 0;
    }
    var newKeywords = $.trim(keyword);
    newKeywords = newKeywords.split(' ').join('');
    newKeywords = newKeywords.split('+').join('');
    newKeywords = newKeywords.split('|').join('');
    newKeywords = newKeywords.split('(').join('');
    newKeywords = newKeywords.split(')').join('');
    if(newKeywords == ''){
        return 0;
    }
    if (newKeywords.length > 20){
        return -1;
    }
    if(!common_keyword_filter.test(newKeywords)){
        return 0;
    }
    newKeywords = newKeywords.replace(common_keyword_char_filter, '');
    if(newKeywords.length == 0){
        return 0;
    }
    return 1;
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
        keywordStr:"",//临时
        index:0,
        date:24,
        emotionDate:24,
        searchKeyword1:"",
        keyword1:"",
        filterKeyword1:"",
        searchKeyword2:"",
        keyword2:"",
        filterKeyword2:"",
        shareCode:"",
        startTime:"",
        endTime:"",
        hotAvg:"--",//热度均值
        topHotAvg:"--",//热度峰值
        hotAvgQs:"--",//热度变化趋势
        hotResultMsg:"",//提及词（最高频次）
        searchKeyword:"", //用户输入
        refresh:" <div class='spinner1'> <div class='spinner-container container1'> <div class='circle1'></div>  <div class='circle2'></div> <div class='circle3'></div>  <div class='circle4'></div> </div> <div class='spinner-container container2'>  <div class='circle1'></div> <div class='circle2'></div> <div class='circle3'></div> <div class='circle4'></div> </div> <div class='spinner-container container3'> <div class='circle1'></div>  <div class='circle2'></div> <div class='circle3'></div>  <div class='circle4'></div>  </div> </div>",
        prompt : "<div class='text-center font-size-12 color_1' style='padding-top: 50px;'><img src='"+njxBasePath+"/images/shouye/warn.png' style='opacity: 0.6;' /> <p>emm~您的信息量太少了</p> <p>热度君暂时计算不出您的热度</p> <p>换个词试试呢~</p> </div>",
        prompt2: "<div class='text-center font-size-12 color_1' style='padding-top: 50px;'><img src='"+njxBasePath+"/images/shouye/warn.png' style='opacity: 0.6;' /> <p>emm~您的信息量太少了</p> <p>情绪君暂时计算不出您的情绪</p> <p>换个词试试呢~</p> </div>",
        areaStatistics:[],
        emotionMap:[],
        emotionSex2M_M:'',
        emotionSex2M_F:'',
        emotionSex2F_M:'',
        emotionSex2F_F:'',
        emotionLevel1_M:'',
        emotionLevel1_F:'',
        emotionLevel2_M:'',
        emotionLevel2_F:'',
        emotionLevel3_M:'',
        emotionLevel3_F:'',
        emotionLevel4_M:'',
        emotionLevel4_F:'',


        doHotline_flag:false,
        doWordcloud_flag:false,

        doEmotionProportion_flag:false,
        doEmotionMap_falg:false,
        doContentTrends_flag:false,
        doEmotionSex_flag:false,
        doEmotionSex2_flag:false,
        doEmotionType_flag:false,
        doEmotionType2_flag:false,
        doEmotionFans_flag:false,
        doEmotionLevel_flag:false,
        doFMHotWord_flag:false,
        doZMHotWord_flag:false,
        doEmotionHobby_flag:false,

        geeTestFlag:true,
        loadMore_flag:true,
        loadMore_flag_two:true

},
    created: function () {


    },
    methods: {


        initFlag:function(){
            app.doHotline_flag=false;
            app.doWordcloud_flag=false;
            app.doEmotionProportion_flag=false;
            app.doEmotionMap_falg=false;
            app.doContentTrends_flag=false;
            app.doEmotionSex_flag=false;
            app.doEmotionSex2_flag=false;
            app.doEmotionType_flag=false;
            app.doEmotionType2_flag=false;
            app.doEmotionFans_flag=false;
            app.doEmotionLevel_flag=false;
            app.doFMHotWord_flag=false;
            app.doZMHotWord_flag=false;
            app.doEmotionHobby_flag=false;
            app.geeTestFlag=true;
            app.loadMore_flag=true;
            app.loadMore_flag_two=true;
            app.hotAvg = "--";
            app.topHotAvg= "--";
            app.hotAvgQs= "--";
        },
        checkIndex:function(index,keyword){
            /**
             * 热度和情绪切换 判断敏感
             */
            this.keywordStr = keyword;
            this.$http.post(njxBasePath+'/checkSensitive', {
                index: index,
                keyword: keyword,
            }, {emulateJSON: true}).then(function (res) {
                if (res.body.status == 0) {
                    // swal("对不起，您的关键词包含敏感词语，请检查后再试！");
                    $('#wui-modal-tootips2').modal('show')
                    return false;
                } else {
                    app.keyword = "";
                    if (index == 0) {
                        app.loadMore(-1);
                    } else {
                        app.loadMore(0);
                    }
                }
            })
        },
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
            if(checkKeywordFilter(keyword) == 0){
                swal("关键词只能包含中文、英文和数字");
                return false;
            }else if(checkKeywordFilter(keyword) == -1){
                swal("内容不能超过20个字");
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
            if(keyword.length > 20){
                swal("内容不能超过20个字");
                return false;
            }
            this.keywordStr = keyword;
            this.$http.post(njxBasePath+'/checkSensitive', {
                index: app.index,
                keyword: keyword,
            }, {emulateJSON: true}).then(function (res) {
                app.keyword = "";
                if (res.body.status != 0){

                    window.scrollTo(0,0);
                    $('#searchTitle').val(title);
                    $('#searchKeyword').val(keyword);
                    $('#searchFilterKeyword').val(filterKeyword);
                    if (keyword != app.keyword1){
                        app.initFlag();
                        app.searchKeyword1 = $("#searchTitle").val();
                        app.keyword1 = $("#searchKeyword").val();
                        app.filterKeyword1 = $("#searchFilterKeyword").val();
                        app.shareCode = $("#shareCode").val();
                    }

                    this.$http.post(njxBasePath+'/doSearchShareCode', {
                        title: title,
                        keyword: keyword,
                        filterKeyword: filterKeyword
                    }, {emulateJSON: true}).then(function (res) {
                        if (res != null){
                            window.location.href = njxBasePath+"/searchDetailsShare?shareCode="+res.body.obj;
                        }else {
                            window.location.href = njxBasePath+"/searchDetails?title="+encodeURIComponent(title)+"&keyword="+encodeURIComponent(keyword)+"&filterKeyword="+encodeURIComponent(filterKeyword);
                        }
                    })
                }else {
                    // swal("对不起，您的关键词包含敏感词语，请检查后再试！");
                    $('#wui-modal-tootips2').modal('show');
                    return false;
                }
            })
        },
        goSearch:function(e,time){
            $(e.target).addClass("active");
            $(e.target).siblings().removeClass("active");
            $("#date").val(time);

            if($("#searchKeyword1").length>0 || $("#searchKeyword2").length>0 || $("#searchKeyword3").length>0){
                var searchKeyword = "";
                if($.trim($("#searchKeyword1").val())){
                    searchKeyword += $.trim($("#searchKeyword1").val());
                }
                if($.trim($("#searchKeyword2").val())){
                    if(searchKeyword){
                        searchKeyword += ",";
                    }
                    searchKeyword += $.trim($("#searchKeyword2").val())
                }
                if($.trim($("#searchKeyword3").val())){
                    if(searchKeyword){
                        searchKeyword += ",";
                    }
                    searchKeyword += $.trim($("#searchKeyword3").val())
                }
                $("#searchKeyword").val(searchKeyword);
                $("#searchTitle").val(searchKeyword);
            }
        },
        sealReload:function(){
            /**
             * 解封之后刷新
             */
            if (app.index == 0){
                app.loadMore(-1);
            }else {
                app.loadMore(0);
                app.loadMore(1);
                app.loadMore(2);
            }
        },
        loadData:function(){
            this.searchKeyword1 = $("#searchTitle").val();
            this.keyword1 = $("#searchKeyword").val();
            this.filterKeyword1 = $("#searchFilterKeyword").val();
            this.shareCode = $("#shareCode").val();
            this.keyword1 = this.keyword1.replace(/\s+/g, "+");
        },
        loadMore:function(type){
            if (type == -1 || type == 0){
                app.loadData();
            }
            if (type == -1){

                if(!this.doHotline_flag){
                    this.getHotline();
                }

                if(!this.doWordcloud_flag){
                    this.getWordcloud();
                }
            }else {
                if (type == 0){
                    if(!this.doEmotionProportion_flag){
                        this.getEmotionProportion();
                    }

                    if(!this.doEmotionMap_falg || ($("#platform").val() != "android")){
                        this.getEmotionMap();
                    }
                    if(!this.doContentTrends_flag || ($("#platform").val() != "android")){
                        this.getContentTrends();
                    }
                }else if (type == 1){
                    if(!this.doEmotionSex_flag){
                        this.getEmotionSex();
                    }
                    //用户认证类型
                    if(!this.doEmotionType_flag){
                        this.getEmotionType();
                    }
                    //性别
                    if(!this.doEmotionSex2_flag){
                        this.getEmotionSex2();
                    }
                    //用户认证类型
                    if(!this.doEmotionType2_flag){
                        this.getEmotionType2();
                    }
                }else if (type ==2){
                    if(!this.doEmotionFans_flag){
                        this.getEmotionFans();
                    }
                    if(!this.doEmotionLevel_flag){
                        this.getEmotionLevel();
                    }
                    if(!this.doEmotionHobby_flag){
                        this.getEmotionHobby();
                    }
                    if(!this.doZMHotWord_flag){
                        this.getZMHotWord();
                    }
                    if(!this.doFMHotWord_flag){
                        this.getFMHotWord();
                    }
                }
            }
        },
        handleSealedData:function(data,url){

            if (!isEmpty(data) && data.obj!==undefined && data.obj.code !== undefined){
                if(data.obj.code=="8888"){
                    if(geeTestFlag){
                        $("#showCode").show();
                        if(!($("#platform").val() == "android"))
                            isUnseal(data.obj);
                        geeTestFlag=false;
                        if($("#platform").val() == "android")
                            window.AndroidModel.unSeal();
                    }
                    return false;
                }else{
                    return true;
                }
            }else {

                if (!isEmpty(data) && data.obj!==undefined && data.obj[0] !== undefined){
                    if(data.obj[0].code=="8888"){
                        if(geeTestFlag){
                            $("#showCode").show();
                            if(!($("#platform").val() == "android"))
                                isUnseal(data.obj);
                            geeTestFlag=false;
                            if($("#platform").val() == "android")
                                window.AndroidModel.unSeal();
                        }
                        return false;
                    }else{
                        return true;
                    }
                }
                if (data.body != null && data.body.code !== undefined){
                    if (data.body.code=="8888"){
                        if(geeTestFlag){
                            $("#showCode").show();
                            if(!($("#platform").val() == "android"))
                                isUnseal(data.obj);
                            geeTestFlag=false;
                            if($("#platform").val() == "android")
                                window.AndroidModel.unSeal();
                        }
                        return false;
                    }else {
                        return true;
                    }
                }
            }
        },


        /**
         * 微博情绪占比
         */
        getEmotionProportion:function(){

            app.loadData();

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionProportion',{
                date: this.emotionDate,
                searchKeyword1: this.searchKeyword1,
                keyword1: this.keyword1,
                filterKeyword1: this.filterKeyword1,
                shareCode:this.shareCode,
                startTime:startTime,
                endTime:endTime
            },function(data){
                app.doEmotionProportion_flag=app.handleSealedData(data,"getEmotionProportion");

                if (!isEmpty(data.obj.data)) {
                    if (data.obj.data.length==0) {
                        $("#emotionpieEchart").html(app.prompt2);
                        // $("#emotionpieEchart2").html(app.prompt2);

                        $("#emotionpieEchart_msg").text(data.message);
                        $("#emotionpieEchart_msg").attr("name",data.msg);
                    }else{
                        $("#emotionpieEchart").attr("name",data.obj.msg);
                        var opt = $.extend(true,{},echartsOpts["emotionProportion"]);
                        var colors = data.obj.color;
                        var data = data.obj.data;
                        var datas = new Array();
                        var legend = new Array();

                        for(n in data){
                            var color = "";
                            if(data[n].name == "喜悦"){
                                color = colors.xy;
                            }else if(data[n].name == "愤怒"){
                                color = colors.fn;
                            }else if(data[n].name == "悲伤"){
                                color = colors.bs;
                            }else if(data[n].name == "惊奇"){
                                color = colors.jq;
                            }else if(data[n].name == "恐惧"){
                                color = "#6360d9";//colors.kj;
                            }else if(data[n].name == "中性"){
                                color = colors.zx;
                            }
                            legend.push({
                                name : data[n].name,
                                textStyle: {
                                    color: color,
                                    fontSize: 14,
                                    padding: 4,
                                }
                            });
                            datas.push({
                                value: data[n].value,
                                name: data[n].name,
                                itemStyle: {
                                    normal: {
                                        color: color,
                                        borderColor: '#1B172C',
                                        borderWidth: 2
                                    }
                                },
                                label: {
                                    normal: {
                                        textStyle: {
                                            fontSize: 13
                                        }
                                    }
                                }
                            });
                        }
                        opt.legend.data=legend;
                        opt.series[0].data=datas;
                        setEchartsOpion({$id:$("#emotionpieEchart"),opt:opt});

                        var opt2 = $.extend(true,{},echartsOpts["emotionProportion2"]);

                        var yAxis = new Array();
                        var datas2 = new Array();
                        data.reverse();
                        for(n in data){
                            var color = "";
                            if(data[n].name == "喜悦"){
                                color = colors.xy;
                            }else if(data[n].name == "愤怒"){
                                color = colors.fn;
                            }else if(data[n].name == "悲伤"){
                                color = colors.bs;
                            }else if(data[n].name == "惊奇"){
                                color = colors.jq;
                            }else if(data[n].name == "恐惧"){
                                color = "#6360d9";//colors.kj;
                            }else if(data[n].name == "中性"){
                                color = colors.zx;
                            }

                            yAxis.push(data[n].name);
                            datas2.push({
                                name: data[n].name,
                                value: data[n].value,
                                itemStyle: {
                                    normal: {
                                        color: color,
                                        barBorderRadius: 5
                                    }
                                }
                            });
                        }
                        opt2.yAxis[0].data=yAxis;
                        opt2.series[0].data=datas2;
                    }
                }else{
                    $("#emotionpieEchart").html(app.prompt2);
                }
            })
        },
        /**
         * 微博情绪地图
         */
        getEmotionMap:function(){
            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionMap',{
                date: this.emotionDate,
                searchKeyword1: this.searchKeyword1,
                keyword1: this.keyword1,
                filterKeyword1: this.filterKeyword1,
                shareCode:this.shareCode,
                startTime:startTime,
                endTime:endTime
            },function(result){
                app.doEmotionMap_falg=app.handleSealedData(result,"getEmotionMap");
                if(result.obj.code=="0000" && result.status==1 &&  result.obj != null){

                    app.emotionMap =  result;
                    var emotions = result.obj.map1[1];
                    var colors = result.obj.map1[3];
                    var color_fn=['#cf421e','#d3582d','#d86b3d','#dd7f52','#e19167'];
                    var color_kj=['#717273','#8d8d8d','#b0afaf','#cdcccc','#e8e8e8']; //['#313131','#423e3d','#544d4d','#665e5b','#79716d'];
                    var color_xy=['#f18d00','#ea9630','#eca147','#efae5e','#f2ba76'];
                    var color_jq=['#3fa579','#5da782','#74b08e','#8aba9c','#9dc4aa'];
                    var color_bs=['#0e7dc0','#4381ba','#5e8dc1','#759ac8','#8ba8d0'];
                    var color_zx=['#9098a5','#9ba0ab','#a6aab4','#b2b4bd','#bdbec6'];

                    var opt = $.extend(true,{},echartsOpts["emotionMap"]);
                    opt.visualMap.categories = emotions;
                    opt.visualMap.pieces = [{
                        value : 0,
                        label : emotions[0],
                        color : colors.xy
                    }, {
                        value : 1,
                        label : emotions[1],
                        color : colors.zx
                    }, {
                        value : 2,
                        label : emotions[2],
                        color : colors.fn
                    }, {
                        value : 3,
                        label : emotions[3],
                        color : colors.bs
                    }, {
                        value : 4,
                        label : emotions[4],
                        color : colors.jq
                    }, {
                        value : 5,
                        label : emotions[5],
                        color : "#6360d9"
                    }]; ////colors.kj
                    opt.series[1].name = "显著情绪";
                    opt.series[1].data = result.obj.map1[2].concat(opt.series[0].data);
                    opt.series[1].tooltip = {
                        textStyle : {
                            align : 'left'
                        },
                        formatter : function(a) {
                            var label = '显著情绪 - ' + emotions[a.value] + "<br/>";
                            var emotion = a.data.emotion;
                            if (emotion != null) {
                                for (var i = 0; i < emotion.length; i++) {
                                    label += emotion[i].name + " : ";
                                    if (emotion[i].value > 0) {
                                        label += emotion[i].value;
                                    } else {
                                        label += "-";
                                    }
                                    label += "<br/>";
                                }
                            } else {
                                label = "暂无信息";
                            }
                            return label;
                        }
                    };
                    var list = result.obj.map1[0];
                    if (list.length >10){
                        list = result.obj.map1[0].slice(0,10);
                    }
                    app.areaStatistics = list;
                    app.emotionMap = result.obj;
                    setEchartsOpion({$id:$("#emotionMap"),opt:opt});

                }else{
                    $("#mapChart1").html(app.prompt2);
                    if(!isEmpty(result.message)){
                        $("#emotionpieEchart_msg").text(result.message);
                    }
                    $("#emotionpieEchart_msg").attr("name",result.msg);
                }
            })
        },
        changeEmotionMap:function(index){

            var opt = $.extend(true,{},echartsOpts["emotionMap"]);
            //各情绪地图
            if(index == 0){
                var emotions = this.emotionMap.map1[1];
                var colors = this.emotionMap.map1[3];
                var color_fn=['#cf421e','#d3582d','#d86b3d','#dd7f52','#e19167'];
                var color_kj=['#717273','#8d8d8d','#b0afaf','#cdcccc','#e8e8e8']; //['#313131','#423e3d','#544d4d','#665e5b','#79716d'];
                var color_xy=['#f18d00','#ea9630','#eca147','#efae5e','#f2ba76'];
                var color_jq=['#3fa579','#5da782','#74b08e','#8aba9c','#9dc4aa'];
                var color_bs=['#0e7dc0','#4381ba','#5e8dc1','#759ac8','#8ba8d0'];
                var color_zx=['#9098a5','#9ba0ab','#a6aab4','#b2b4bd','#bdbec6'];

                opt.visualMap.categories = emotions;
                opt.visualMap.pieces = [{
                    value : 0,
                    label : emotions[0],
                    color : colors.xy
                }, {
                    value : 1,
                    label : emotions[1],
                    color : colors.zx
                }, {
                    value : 2,
                    label : emotions[2],
                    color : colors.fn
                }, {
                    value : 3,
                    label : emotions[3],
                    color : colors.bs
                }, {
                    value : 4,
                    label : emotions[4],
                    color : colors.jq
                }, {
                    value : 5,
                    label : emotions[5],
                    color : "#6360d9"
                }];
                opt.series[0].name = "显著情绪";
                opt.series[1].data = this.emotionMap.map1[2].concat(opt.series[0].data);
                var list = this.emotionMap.map1[0];
                if (list.length >10){
                    list = this.emotionMap.map1[0].slice(0,10);
                }
                myChart["emotionMap"].clear();
                app.areaStatistics = list;
                setEchartsOpion({$id:$("#emotionMap"),opt:opt});
            }else{
                var text = "中性";
                if(index == 1){
                    var colormap = ['#f18d00','#ea9630','#eca147','#efae5e','#f2ba76'];
                    text = "喜悦";
                }else if(index == 2){
                    var colormap = ['#9098a5','#9ba0ab','#a6aab4','#b2b4bd','#bdbec6'];
                    text = "中性";
                }else if(index == 3){
                    var colormap = ['#cf421e','#d3582d','#d86b3d','#dd7f52','#e19167'];
                    text = "愤怒";
                }else if(index == 4){
                    var colormap = ['#0e7dc0','#4381ba','#5e8dc1','#759ac8','#8ba8d0'];
                    text = "悲伤";
                }else if(index == 5){
                    var colormap = ['#3fa579','#5da782','#74b08e','#8aba9c','#9dc4aa'];
                    text = "惊奇";
                }else if(index == 6){
                    // var colormap = ['#313131','#423e3d','#544d4d','#665e5b','#79716d'];
                    // var colormap = ['#717273','#8d8d8d','#b0afaf','#cdcccc','#e8e8e8'];
                    var colormap = ['rgba(99, 96, 217,1)','rgba(99, 96, 217,.8)','rgba(99, 96, 217,.7)','rgba(99, 96, 217,.5)','rgba(99, 96, 217,.35)']
                    text = "恐惧";
                }

                if(this.emotionMap){
                    var max=0,min=0,svg=0;

                    if (this.emotionMap.map2[index-1].data != null){
                        max = this.emotionMap.map2[index-1].data[0].value;
                        min = this.emotionMap.map2[index-1].data[this.emotionMap.map2[index-1].data.length-1].value;
                        svg = Math.ceil((max-min)/5);
                        if(svg==0){
                            svg=max;
                        }
                        var pieces = [
                            {min: min,max: svg,color:colormap[4]},
                            {min: svg+1,max: svg*2,color:colormap[3]},
                            {min: svg*2+1,max: svg*3,color:colormap[2]},
                            {min: svg*3+1,max: svg*4,color:colormap[1]},
                            {min: svg*4+1,max: max,color:colormap[0]}
                        ];
                        // opt.legend={show:false};
                        opt.series[1].name = text;
                        opt.series[1].data = this.emotionMap.map2[index-1].data;
                        opt.visualMap = {
                            type: 'piecewise', // 分段型。
                            splitNumber: 5,
                            color: '#ebdba4',
                            pieces:pieces,
                            left: 'left',
                            top: 'bottom',// 文本，默认为数值文本
                            calculable: true,
                            inverse : false,
                            textStyle: {
                                color: '#8F9DBA',
                                fontSize:12,
                            },
                        };
                        var list = this.emotionMap.map2[index-1].data;
                        var areaList = new Array();
                        var  count =  list.length;
                        if (count > 10){
                            count = 10;
                        }
                        for (var y = 0; y < count; y++) {
                            var map = list[y];
                            areaList.push([map.name,map.value,text]);
                        }
                        app.areaStatistics = areaList;
                        myChart["emotionMap"].clear();
                        setEchartsOpion({$id:$("#emotionMap"),opt:opt});
                    }else {
                        max = 0;
                        min = 0;
                        svg = Math.ceil((max-min)/5);
                        if(svg==0){
                            svg=max;
                        }
                        var pieces = [
                            {min: min,max: svg,color:colormap[4]},
                            {min: svg+1,max: svg*2,color:colormap[3]},
                            {min: svg*2+1,max: svg*3,color:colormap[2]},
                            {min: svg*3+1,max: svg*4,color:colormap[1]},
                            {min: svg*4+1,max: max,color:colormap[0]}
                        ];
                        // opt.legend={show:false};
                        opt.series[1].name = text;
                        opt.series[1].data = []
                        opt.visualMap = {
                            type: 'piecewise', // 分段型。
                            splitNumber: 5,
                            color: '#ebdba4',
                            pieces:pieces,
                            left: 'left',
                            top: 'bottom',// 文本，默认为数值文本
                            calculable: true,
                            inverse : false,
                            textStyle: {
                                color: '#8F9DBA',
                                fontSize:12,
                            },
                        };
                        app.areaStatistics = [];
                        myChart["emotionMap"].clear();
                        setEchartsOpion({$id:$("#emotionMap"),opt:opt});
                    }
                }else{
                    $("#emotionMap"+(index+1)).html(app.prompt2);
                }
            }
        },
        getContentTrends:function(){

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getContentTrends',{
                    date: this.emotionDate,
                    searchKeyword1: this.searchKeyword1,
                    keyword1: this.keyword1,
                    filterKeyword1: this.filterKeyword1,
                    shareCode:this.shareCode,
                    startTime:startTime,
                    endTime:endTime
                },function(result) {
                app.doContentTrends_flag=app.handleSealedData(result,"getContentTrends");
                $("#contentTrends").attr("name",result.obj.msg);
                if(!isEmpty(result.obj.data) && result.obj.code=="0000"){
                    var contentTrends1 = result.obj.data;
                    var opt = $.extend(true,{},echartsOpts["trendsLine"]);
                    opt.color = [];
                    var legend = new Array();
                    for(var i = 0;i<contentTrends1.length;i++){
                        opt.series[i] = {name:contentTrends1[i].name,type:'line',symbol: 'emptyCircle',symbolSize: 10,itemStyle: {
                                normal: {
                                    color: '#F08C00',
                                    borderColor: '#F08C00',
                                    borderWidth: 2
                                }
                            },lineStyle: {
                                normal: {
                                    color: '#F08C00',
                                    width: 2,
                                    type: 'solid'
                                }
                            },areaStyle: {
                                normal: {
                                    color: 'rgba(0,0,0,0.5)',
                                    opacity: 0
                                }
                            },smooth: true,data:[]
                        };
                        for(var j = 0;j<contentTrends1[i].statList.length;j++){
                            opt.xAxis[0].data[j] = contentTrends1[i].statList[j].name;
                            opt.series[i].data[j] = contentTrends1[i].statList[j].num;
                        }
                        var color = "";
                        if(contentTrends1[i].name == "愤怒"){
                            color = "#ec4444";
                        }else if(contentTrends1[i].name == "恐惧"){
                            color = "#6360d9";
                        }else if(contentTrends1[i].name == "悲伤"){
                            color =  "#24AEEB";
                        }else if(contentTrends1[i].name == "惊奇"){
                            color =  "#80ab10";
                        }else if(contentTrends1[i].name == "喜悦"){
                            color =  "#ff8500";
                        }else if(contentTrends1[i].name == "中性"){
                            color =  "#d6d6d6";
                        }
                        legend.push({
                            name : contentTrends1[i].name,
                            textStyle: {
                                color: color
                            }
                        });
                        opt.series[i].itemStyle.normal.color = color;
                        opt.series[i].itemStyle.normal.borderColor = color;
                        opt.series[i].lineStyle.normal.color = color;
                    }
                    opt.legend.data=legend;
                    setEchartsOpion({$id:$("#contentTrends"),opt:opt});

                }else{
                    $("#contentTrends").html(app.prompt2);
                }
            })
        },
        getEmotionSex:function(){
            //情绪性别
            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionSex',{
                    date: this.emotionDate,
                    searchKeyword1: this.searchKeyword1,
                    keyword1: this.keyword1,
                    filterKeyword1: this.filterKeyword1,
                    shareCode:this.shareCode,
                    startTime:startTime,
                    endTime:endTime
                },function(data) {
                app.doEmotionSex_flag=app.handleSealedData(data,"getEmotionSex");
                $("#emotionSex").attr("name",data.obj.msg);
                if (!isEmpty(data.status) && data.status==1 && data.obj.code=="0000") {
                    var opt = $.extend(true,{},echartsOpts["emotionSex"]);
                    var datas= [data.obj.max*2,
                        data.obj.max*2,
                        data.obj.max*2,
                        data.obj.max*2,
                        data.obj.max*2,
                        data.obj.max*2];

                    opt.series[0].data[0].value=data.obj.m.xy;
                    opt.series[0].data[1].value=data.obj.m.zx;
                    opt.series[0].data[2].value=data.obj.m.fn;
                    opt.series[0].data[3].value=data.obj.m.bs;
                    opt.series[0].data[4].value=data.obj.m.jq;
                    opt.series[0].data[5].value=data.obj.m.kj;

                    opt.series[2].data[0].value=data.obj.f.xy;
                    opt.series[2].data[1].value=data.obj.f.zx;
                    opt.series[2].data[2].value=data.obj.f.fn;
                    opt.series[2].data[3].value=data.obj.f.bs;
                    opt.series[2].data[4].value=data.obj.f.jq;
                    opt.series[2].data[5].value=data.obj.f.kj;

                    opt.series[1].data=datas;
                    opt.series[3].data=datas;

                    opt.xAxis[0].max=data.obj.max*2;
                    opt.xAxis[2].max=data.obj.max*2;
                    setEchartsOpion({$id:$("#emotionSex"),opt:opt});
                }else{
                    $("#emotionSex").html(app.prompt2);
                }
            })
        },
        getEmotionSex2:function(){
            //二次元（男女）性别

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionSex2',{
                    date: this.emotionDate,
                    searchKeyword1: this.searchKeyword1,
                    keyword1: this.keyword1,
                    filterKeyword1: this.filterKeyword1,
                    shareCode:this.shareCode,
                    startTime:startTime,
                    endTime:endTime
                },function(data) {
                app.doEmotionSex2_flag=app.handleSealedData(data,"getEmotionSex2");
                // $("#emotionBar1").attr("name",data.obj[0].msg);
                if (!isEmpty(data.status) && data.status==1 && !isEmpty(data.obj[0]) && data.obj[0].code=="0000") {
                    var optM = $.extend(true,{},echartsOpts["emotionSex_M"]);
                    var optF = $.extend(true,{},echartsOpts["emotionSex_F"]);

                    optM.series[0].data[0].name = '男'
                    optM.series[0].data[0].value = data.obj[0].fm[0];
                    optM.series[0].data[1].value = data.obj[0].zm[0];


                    optF.series[0].data[0].name = '女'
                    optF.series[0].data[0].value = data.obj[0].fm[1];
                    optF.series[0].data[1].value = data.obj[0].zm[1];

                    app.emotionSex2M_M = data.obj[0].fm[0].toFixed(2)+ '%';
                    app.emotionSex2M_F =data.obj[0].zm[0].toFixed(2)+ '%';
                    app.emotionSex2F_M = data.obj[0].fm[1].toFixed(2)+ '%';
                    app.emotionSex2F_F = data.obj[0].zm[1].toFixed(2)+ '%';

                    setEchartsOpion({$id:$("#emotionSex2M"),opt:optM});
                    setEchartsOpion({$id:$("#emotionSex2F"),opt:optF});

                }else{
                    $("#emotionSex2M").html(app.prompt2);
                    $("#emotionSex2F").html(app.prompt2);
                }
            })
        },
        getEmotionType:function(){

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionType',{
                    date: this.emotionDate,
                    searchKeyword1: this.searchKeyword1,
                    keyword1: this.keyword1,
                    filterKeyword1: this.filterKeyword1,
                    shareCode:this.shareCode,
                    startTime:startTime,
                    endTime:endTime
                },function(data){
                app.doEmotionType_flag=app.handleSealedData(data,"getEmotionType");
                $("#emotionType").attr("name",data.obj.msg);
                if (!isEmpty(data.status) && data.status==1 && data.obj.code=="0000") {
                    var opt = $.extend(true,{},echartsOpts["emotionType"]);
                    opt.series[0].data=data.obj.xy[0];
                    opt.series[1].data=data.obj.fn[0];
                    opt.series[2].data=data.obj.bs[0];
                    opt.series[3].data=data.obj.jq[0];
                    opt.series[4].data=data.obj.kj[0];
                    opt.series[5].data=data.obj.zx[0];
                    setEchartsOpion({$id:$("#emotionType"),opt:opt});
                }else{
                    $("#emotionType").html(app.prompt2);
                }
            })
        },
        getEmotionType2:function(){

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionType2',{
                date: this.emotionDate,
                searchKeyword1: this.searchKeyword1,
                keyword1: this.keyword1,
                filterKeyword1: this.filterKeyword1,
                shareCode:this.shareCode,
                startTime:startTime,
                endTime:endTime
            },function(data){
                app.doEmotionType2_flag=app.handleSealedData(data,"getEmotionType2");
                $("#emotionType2").attr("name",data.obj[0].msg);
                if (!isEmpty(data.status) && data.status==1 && !isEmpty(data.obj[0]) && data.obj[0].code=="0000") {
                    var opt = $.extend(true,{},echartsOpts["emotionType2"]);
                    var list = [
                        {name:'', value: data.obj[0].zm[0].toFixed(2)},
                        {name:'', value: data.obj[0].zm[1].toFixed(2)},
                        {name:'', value: data.obj[0].zm[2].toFixed(2)},
                        {name:'', value: data.obj[0].zm[3].toFixed(2)}
                    ];
                    opt.series[0].data = list ;
                    list = [
                        {name:data.obj[0].zm[0].toFixed(2)+"%", value: data.obj[0].zm[0].toFixed(2)},
                        {name:data.obj[0].zm[1].toFixed(2)+"%", value: data.obj[0].zm[1].toFixed(2)},
                        {name:data.obj[0].zm[2].toFixed(2)+"%", value: data.obj[0].zm[2].toFixed(2)},
                        {name:data.obj[0].zm[3].toFixed(2)+"%", value: data.obj[0].zm[3].toFixed(2)}
                    ];
                    opt.series[1].data = list ;

                    list = [
                        {name:'', value: data.obj[0].fm[0].toFixed(2)},
                        {name:'', value: data.obj[0].fm[1].toFixed(2)},
                        {name:'', value: data.obj[0].fm[2].toFixed(2)},
                        {name:'', value: data.obj[0].fm[3].toFixed(2)}
                    ];
                    opt.series[2].data = list ;
                    list = [
                        {name:data.obj[0].fm[0].toFixed(2)+"%", value: data.obj[0].fm[0].toFixed(2)},
                        {name:data.obj[0].fm[1].toFixed(2)+"%", value: data.obj[0].fm[1].toFixed(2)},
                        {name:data.obj[0].fm[2].toFixed(2)+"%", value: data.obj[0].fm[2].toFixed(2)},
                        {name:data.obj[0].fm[3].toFixed(2)+"%", value: data.obj[0].fm[3].toFixed(2)}
                    ];
                    opt.series[3].data = list ;
                    setEchartsOpion({$id:$("#emotionType2"),opt:opt});

                }else{
                    $("#emotionType2").html(app.prompt2);
                }
            })
        },
        getEmotionFans:function(){

            //二元情绪洞察-粉丝数量分布
            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionFans',{
                    date: this.emotionDate,
                    searchKeyword1: this.searchKeyword1,
                    keyword1: this.keyword1,
                    filterKeyword1: this.filterKeyword1,
                    shareCode:this.shareCode,
                    startTime:startTime,
                    endTime:endTime
                },function(data) {

                app.doEmotionFans_flag=app.handleSealedData(data,"getEmotionFans");
                if (data.status && data.obj != null && data.obj[0] != null) {
                    var opt = $.extend(true,{},echartsOpts["emotionFans"]);

                    opt.xAxis[0].data=data.obj[0].xAxis;
                    opt.series[1].data=data.obj[0].zm;
                    opt.series[2].data=data.obj[0].fm;
                    setEchartsOpion({$id:$("#emotionFans"),opt:opt});
                }else{
                    $("#emotionFans").html(app.prompt2);
                }
            })
        },
        getEmotionLevel:function(){
            //二元情绪洞察-转发层级

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionLevel',{
                    date: this.emotionDate,
                    searchKeyword1: this.searchKeyword1,
                    keyword1: this.keyword1,
                    filterKeyword1: this.filterKeyword1,
                    shareCode:this.shareCode,
                    startTime:startTime,
                    endTime:endTime
                },function(data) {

                app.doEmotionLevel_flag=app.handleSealedData(data,"getEmotionLevel");
                // $("#getEmotionLevel").attr("name",data.obj[0].msg);
                if (data.status && data.obj != null && data.obj[0] != null) {
                    var opt1 = $.extend(true,{},echartsOpts["emotionLevel1"]);
                    var opt2 = $.extend(true,{},echartsOpts["emotionLevel2"]);
                    var opt3 = $.extend(true,{},echartsOpts["emotionLevel3"]);
                    var opt4 = $.extend(true,{},echartsOpts["emotionLevel4"]);

                    var zm = data.obj[0].zm;
                    var fm = data.obj[0].fm;
                    var name = data.obj[0].name;

                    for(var n = 0;n<name.length;n++){
                        var total = parseInt(zm[n]) + parseInt(fm[n]);
                        if(total != 0){
                            if (n==0){
                                app.emotionLevel1_M = "敏感："+(100*parseInt(fm[n])/total).toFixed(2)+"%";
                                app.emotionLevel1_F = "非敏感："+(100*parseInt(zm[n])/total).toFixed(2)+"%";

                                opt1.series[0].data[0].name ="Line";
                                opt1.series[0].data[0].value =(100*parseInt(fm[n])/total).toFixed(2);
                                opt1.series[0].data[1].name ="";
                                opt1.series[0].data[1].value = 100-(100*parseInt(fm[n])/total).toFixed(2);

                                opt1.series[1].data[0].name ="Line";
                                opt1.series[1].data[0].value =(100*parseInt(zm[n])/total).toFixed(2);
                                opt1.series[1].data[1].name ="";
                                opt1.series[1].data[1].value = 100-(100*parseInt(zm[n])/total).toFixed(2);

                            }
                            if (n==1){
                                app.emotionLevel2_M = "敏感："+(100*parseInt(fm[n])/total).toFixed(2)+"%";
                                app.emotionLevel2_F = "非敏感："+(100*parseInt(zm[n])/total).toFixed(2)+"%";

                                opt2.series[0].data[0].name ="Line";
                                opt2.series[0].data[0].value =(100*parseInt(fm[n])/total).toFixed(2);
                                opt2.series[0].data[1].name ="";
                                opt2.series[0].data[1].value = 100-(100*parseInt(fm[n])/total).toFixed(2);

                                opt2.series[1].data[0].name ="Line";
                                opt2.series[1].data[0].value =(100*parseInt(zm[n])/total).toFixed(2);
                                opt2.series[1].data[1].name ="";
                                opt2.series[1].data[1].value = 100-(100*parseInt(zm[n])/total).toFixed(2);
                            }
                            if (n==2){
                                app.emotionLevel3_M = "敏感："+(100*parseInt(fm[n])/total).toFixed(2)+"%";
                                app.emotionLevel3_F = "非敏感："+(100*parseInt(zm[n])/total).toFixed(2)+"%";
                                opt3.series[0].data[0].name ="Line";
                                opt3.series[0].data[0].value =(100*parseInt(fm[n])/total).toFixed(2);
                                opt3.series[0].data[1].name ="";
                                opt3.series[0].data[1].value = 100-(100*parseInt(fm[n])/total).toFixed(2);

                                opt3.series[1].data[0].name ="Line";
                                opt3.series[1].data[0].value =(100*parseInt(zm[n])/total).toFixed(2);
                                opt3.series[1].data[1].name ="";
                                opt3.series[1].data[1].value = 100-(100*parseInt(zm[n])/total).toFixed(2);

                            }
                            if (n==3){
                                app.emotionLevel4_M = "敏感："+(100*parseInt(fm[n])/total).toFixed(2)+"%";
                                app.emotionLevel4_F = "非敏感："+(100*parseInt(zm[n])/total).toFixed(2)+"%";

                                opt4.series[0].data[0].name ="Line";
                                opt4.series[0].data[0].value =(100*parseInt(fm[n])/total).toFixed(2);
                                opt4.series[0].data[1].name ="";
                                opt4.series[0].data[1].value = 100-(100*parseInt(fm[n])/total).toFixed(2);

                                opt4.series[1].data[0].name ="Line";
                                opt4.series[1].data[0].value =(100*parseInt(zm[n])/total).toFixed(2);
                                opt4.series[1].data[1].name ="";
                                opt4.series[1].data[1].value = 100-(100*parseInt(zm[n])/total).toFixed(2);

                            }
                        }
                    }
                    setEchartsOpion({$id:$("#getEmotionLevel"),opt:opt1});
                    setEchartsOpion({$id:$("#getEmotionLevel2"),opt:opt2});
                    setEchartsOpion({$id:$("#getEmotionLevel3"),opt:opt3});
                    setEchartsOpion({$id:$("#getEmotionLevel4"),opt:opt4});
                }else{
                    $("#getEmotionLevel").html(app.prompt2);
                }
            })
        },
        getEmotionHobby:function(){
            //二元情绪洞察-兴趣标签

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getEmotionHobby',{
                    date: this.emotionDate,
                    searchKeyword1: this.searchKeyword1,
                    keyword1: this.keyword1,
                    filterKeyword1: this.filterKeyword1,
                    shareCode:this.shareCode,
                    startTime:startTime,
                    endTime:endTime
                },function(data) {
                app.doEmotionHobby_flag=app.handleSealedData(data,"getEmotionHobby");

                if (data.status && data.obj != null && data.obj[0] != null) {

                    $("#interestWordCloud-zm").attr("name",data.obj[0].msg);

                    var zm = data.obj[0].zm;
                    var fm = data.obj[0].fm;
                    var html = '';
                    if(zm == null || zm.length == 0){
                        $("#interestWordCloud-zm").html(app.prompt2);
                        $("#interestWordCloud-zm").removeClass("positive-con");
                    }else{
                        for(n in zm){
                            var index = parseInt(n)+1;
                            html +='<li> <a href="javascript:;">'+zm[n].name+'<em>'+zm[n].name+'：'+zm[n].value+'</em></a> </li>';
                        }
                        $("#interestWordCloud-zm").html(html);
                    }

                    html = '';
                    if(fm == null || fm.length == 0){
                        $("#interestWordCloud-fm").html(app.prompt2);
                        $("#interestWordCloud-fm").removeClass("positive-con");
                    }else{
                        for(n in fm){
                            var index = parseInt(n)+1;
                            html +='<li> <a href="javascript:;">'+fm[n].name+'<em>'+fm[n].name+'：'+fm[n].value+'</em></a> </li>';
                        }
                        $("#interestWordCloud-fm").html(html);
                    }
                }
            })
        },
        getZMHotWord:function(){


            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getZMHotWord',{
                    date: this.emotionDate,
                    searchKeyword1: this.searchKeyword1,
                    keyword1: this.keyword1,
                    filterKeyword1: this.filterKeyword1,
                    shareCode:this.shareCode,
                    startTime:startTime,
                    endTime:endTime
                },function(data) {
                app.doZMHotWord_flag=app.handleSealedData(data,"getZMHotWord");
                $("#zmHotWord").attr("name",data.obj[0].msg);
                if (data.status && data.obj != null && data.obj[0] != null && data.obj[0] != null) {
                    var zmHotWords = data.obj[0].zmWords;
                    if(zmHotWords.length==0){
                        $("#zmHotWord").html(app.prompt2);
                    }else{

                        var opt = $.extend(true,{},echartsOpts["zmHotWord"]);
                        // var list = new Array();
                        // for (n in zmHotWords){
                        //     var name = zmHotWords[n].name;
                        //     if(name.length > 5){
                        //         name = name.substring(0, 4) + "...";
                        //     }
                        //     list.push({name:name,value:zmHotWords[n].value});
                        // }
                        opt.series[0].data = zmHotWords;
                        setEchartsOpion({$id:$("#zmHotWord"),opt:opt});
                    }
                }else{
                    $("#zmHotWord").html(app.prompt2);
                }
            })
        },
        getFMHotWord:function(){
            //二元情绪洞察-负面热词
            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.emotionDate == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }
            $.post(njxBasePath+'/getFMHotWord',{
                date: this.emotionDate,
                searchKeyword1: this.searchKeyword1,
                keyword1: this.keyword1,
                filterKeyword1: this.filterKeyword1,
                shareCode:this.shareCode,
                startTime:startTime,
                endTime:endTime
            },function(data) {
                app.doZMHotWord_flag=app.handleSealedData(data,"getFMHotWord");
                $("#fmHotWord").attr("name",data.obj[0].msg);
                if (data.status && data.obj != null && data.obj[0] != null && data.obj[0] != null ) {
                    var fmHotWords = data.obj[0].fmWords;
                    if(fmHotWords != null && fmHotWords.length>0){
                        var opt = $.extend(true,{},echartsOpts["fmHotWord"]);
                        opt.series[0].data = fmHotWords;
                        setEchartsOpion({$id:$("#fmHotWord"),opt:opt});
                    }else{
                        $("#fmHotWord").html(app.prompt2);
                    }
                }else{
                    $("#fmHotWord").html(app.prompt2);
                }
            })
        },
        searchEnter:function(){
            var text = $("#getSearch").val();

            if(!text){
                swal("请输入内容");
                return false;
            }
            if(text.length == 1){
                swal("内容至少输入2个字");
                return false;
            }
            if(!(text)){
                swal("关键词只能包含中文、英文和数字");
                return false;
            }
            if (text!=null){
                app.getSearchDetail(text,text,"");
            }
        },
        getHotline:function() {

            // $("#emptyRefresh").html(this.refresh);

            moment.locale('zh-cn');
            app.loadData();

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.date == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }

            this.$http.post(njxBasePath+'/getHotline', {
                date: this.date,
                searchKeyword1: this.searchKeyword1,
                keyword1: this.keyword1,
                filterKeyword1: this.filterKeyword1,
                shareCode:this.shareCode,
                startTime:startTime,
                endTime:endTime

            }, {emulateJSON: true}).then(function (res) {

                app.doHotline_flag=app.handleSealedData(res,"getHotline");

                if (res.body != null)
                {
                    this.hotAvgQs =  parseFloat(res.body.ratioCustom).toFixed(2)
                    var list = new Array();
                    list = res.body.doHeatValue;
                    if (list != null && list.length > 0) {
                        app.hotAvg = parseFloat(list[0].ratioHotCustom).toFixed(2)
                        app.topHotAvg = parseFloat(list[0].ratioHotTopCustom).toFixed(2)
                    }
                    var trend = res.body.doEventTrend[0];

                    var opt = $.extend(true, {}, echartsOpts["hotline"]);
                    var alertMsg = "从热度指数变化趋势来看,";
                    opt.series[0] = $.extend(true, {}, echartsOpts["hotline"]).series[0];
                    if (trend!=null && trend.name != null){
                        opt.series[0].name = trend.name;
                    }else {
                        opt.series[0].name = app.searchKeyword1;
                    }

                    if (trend!=null && trend.statList.length) {
                        //峰值
                        var topValue = trend.statList[0].total.toFixed(2), topTime, subMsg;
                        var topTime = trend.statList[0].name;
                        var avgValue = 0;
                        for (var i = 0; i < trend.statList.length; i++) {
                            opt.series[0].data[i] = trend.statList[i].total.toFixed(2);
                            opt.xAxis[0].data[i] = trend.statList[i].name + ":00";
                            if (parseFloat(trend.statList[i].total) > parseFloat(topValue)) {
                                topValue = trend.statList[i].total.toFixed(2);
                                topTime = trend.statList[i].name;
                            }
                            //均值计算
                            avgValue = (avgValue + parseFloat(trend.statList[i].total.toFixed(2)));
                        }
                        avgValue = avgValue / trend.statList.length;

                        // $("#hot_avg").html(avgValue.toFixed(2));
                        // $("#top_hot_avg").html(topValue);



                        subMsg = $("#searchKeyword1").val();
                        alertMsg += '<font class="f_c17">';
                        alertMsg += subMsg + '</font>在' + topTime + '时达到了<font class="f_c22">' + topValue + '</font>的峰值。';
                        $("#hotTrends_msg").html(alertMsg)
                        setEchartsOpion({$id:$("#lineEcharts"),opt:opt});

                    }
                }else {
                    $("#lineEcharts").html(app.prompt);
                }
            });
        },
        getWordcloud: function () {

            moment.locale('zh-cn');

            var endTime = moment().format('YYYY-MM-DD HH:mm:ss');
            var startTime =  moment().subtract(1, 'days').format('YYYY-MM-DD HH:mm:ss');
            if (this.date == 72 ){
                startTime =  moment().subtract(3, 'days').format('YYYY-MM-DD HH:mm:ss');
            }

            //发送 post 请求
            this.$http.post(njxBasePath+'/getWordcloud', {
                date: this.date,
                searchKeyword1: this.searchKeyword1,
                keyword1: this.keyword1,
                filterKeyword1: this.filterKeyword1,
                shareCode:this.shareCode,
                startTime:startTime,
                endTime:endTime
            }, {emulateJSON: true}).then(function (res) {

                app.doWordcloud_flag=app.handleSealedData(res,"getWordcloud");

                if (res.body != null)
                {

                    var opt = $.extend(true,{},echartsOpts["wordCloud"]);
                    var maskImage = new Image();

                    opt.maskImage = maskImage;
                    var colors = ["#f18d00","#f1b192","#b9a7af"];//词云颜色

                    maskImage.onload = function(){
                        opt.series[0].maskImage;
                        setEchartsOpion({$id:$("#wordcloud"),opt:opt});
                    }

                    var list = res.body[0];
                    if (list != null){
                        var msg = "";
                        for(var i = 0;i<list.length;i++){
                            opt.series[0].data[i] = {name:list[i].name,value:list[i].num*100, textStyle:{normal:{color:colors[i%3]}}};
                            if(i<3){
                                if(i==2){
                                    msg += list[i].name+"。";
                                }else {
                                    msg += list[i].name+"、";
                                }
                            }
                        }

                        //
                        // opt.series[0] = $.extend(true,opt.series[0],{ width:'120%',height:"100%",top:'-6%'});
                        opt.series[0].sizeRange = [12,20];

                        maskImage.src = "data:image/png;base64,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";


                        // maskImage.src = njxBasePath+'/images/cloud.png';

                        // if(window.screen.width>=320 && window.screen.width<375){
                        //     opt.series[0] = $.extend(true,opt.series[0],{ width:'120%',height:"100%",top:'-6%'});
                        //     opt.series[0].sizeRange = [12,20];
                        //     $(".wbWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-320.png')");
                        //     // maskImage.src = njxBasePath+'/images/cloud-320.png';
                        //     maskImage.src = njxBasePath+'/images/cloud.png';
                        // }
                        // if(window.screen.width>=375 && window.screen.width<412){
                        //     opt.series[0] = $.extend(true,opt.series[0],{ width:'109%',height:"100%",top:'-2%'});
                        //     opt.series[0].sizeRange = [12,32];
                        //     $(".wbWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-375.png')");
                        //     // maskImage.src = njxBasePath+'/images/cloud-375.png';
                        //     maskImage.src = njxBasePath+'/images/cloud.png';
                        // }
                        // if(window.screen.width>=412){
                        //     opt.series[0] = $.extend(true,opt.series[0],{ width:'100%',height:"102%",top:'-5%'});
                        //     opt.series[0].sizeRange = [12,40];
                        //     $(".wbWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud.png')");
                        //     maskImage.src = njxBasePath+'/images/cloud.png';
                        // }

                        app.hotResultMsg = msg
                    }
                }else {
                    $("#wordcloud").html(app.prompt);
                }
            });
        }
    }
});