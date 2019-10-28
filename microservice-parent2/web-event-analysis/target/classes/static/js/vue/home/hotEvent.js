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

var app = new Vue({
    el: '#app',
    data: {
        bigDataList:new Array(),
        typeList:[
            "政务",
            "经济",
            "国际",
            "法治",
            "教育",
            "商业",
            "民生",
            "医疗",
            "交通",
            "文娱",
            "体育"
         ],
        allBigEventList:new Array(),
        rankData: new Array(),//热门事件排行数据
        hotEventData: {},//地图右侧详细信息数据
        hotClassData: {},//热点类型列表
        newHotClassData: [],//新的热点类型列表
        bigDataRead: new Array(),//大数据解读数据
        typePropDate: [],//重构的类型占比数据
        returnTypePropData: [],//返回的类型占比数据
        type: [],//热点类型名称
        xqData: {},//排行详情数据
        activeClass: 0, // 0为默认选择第一个，-1为不选择
        activeTypeClass: -1,
        indexData: {},//排行榜文字数据
        timeType1: 1,//事件类型：1，24小时。2,72小时
        labels1: "",//热点类型标签ID
        province1: "",//省份
        province2: "",
        startTime: moment().add(-1, 'day').format('YYYY-MM-DD HH:mm:ss'),
        endTime: moment().format('YYYY-MM-DD HH:mm:ss'),
        bigEventStartTime:moment().add(-1, 'week').format('YYYY-MM-DD'),
        bigEventEndTime:moment().format('YYYY-MM-DD'),
        keyword: "",//热度趋势图关键字
        filterKeyword: "",//热度趋势过滤词
        mg: 0,//敏感
        fmg: 0,//非敏感
        xw: "",//新聞
        zw: "", //政务
        bk: "",//报刊
        sort: 2,//排行榜排序
        x: 0,//控制热点事件top5的开关
        y: 0,//控制变化最大top5的开关
        isLogin: $("#isLogin").val(),//判断是否登录用
        chinaData: [],//国内信息（全国），为了展示地图全国信息
        mapData1: [],//全国性质的事件数据
        number: 0,//地图全国性事件轮播数据判断浮动用
        allProvinceData: [],//全国省热点事件
        mapData2: [],//所有省性质的事件数据
        changeMaxDate: [],//变化最大数据
        hotTopData: [],//热点事件top数据
        timeClass: 1,//时间标签点击效果控制
        hotClass: 1,//排行榜排序点击效果控制
        qg: "全国",//切换至全国的按钮
        zt: false,//判断排行榜加载状态
        box: {},//地图中获取到的id的对象
        myChart: undefined,//地图中地图初始化的对象
        isPlay: true,//地图中各省份事件轮播控制
        timer: null,//地图中时间参数
        lab: "",//热点类型标签(用于收费提示)
        dt:false,//地图数据加载时水印控制
        userAdmin:$("#isDZ").val(),//判断登陆用户的用户名
        capital:0,
        city:"",
        page:1,
        pageSize:20,
        xs:0,
        pageActive:1,
        controlPage:1,

        eventNum:0,
        upEventNum:0,
        downEventNum:0,
        hotLabelData:[],
        chooseIndex:0, //0热门事件 1重大事件
        areaType:1, //1国内 2国外
        labelShowTag:0,
        shareCode:"",
        isShowBar:true
    },
    created: function () {
        /**
         * 获取热门事件标签
         */
        this.getHotLabelList();
        /**
         * 获取热门事件
         */
        this.getHotEventList();

        this.getHotEventCount();
        /**
         * 获取重大事件
         */
        this.getBigEventList();

        this.myBrowser();
        var param = {
            type : 0,
            page : 1,
            pagesize : 3,
            isPackagePrice:1
        };
        this.bigData(param);
    },
    methods: {
        myBrowser:function(){
            var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
            if (ua.match(/WeiBo/i) == "weibo") {
                this.isShowBar = true;
            }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
                this.isShowBar = true;
            }else{
                this.isShowBar = false;
            }
        },
        downloadApp:function(){
            var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
            if(ua.match(/WeiBo/i) == "weibo"){
                window.location.href = 'http://d.51wyq.cn';
            } else if (/iphone|ipad|ipod/.test(ua)) {
                window.location.href = "https://itunes.apple.com/us/app/wei-yu-qing/id953382632?l=zh&amp;ls=1&amp;mt=8";
            }else{
                window.location.href = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq';
            }
        },
        getBigDataDetail:function(id){
            window.location.href=njxBasePath+"/getBigDataDetail?id="+id;
        },
         getSearchDetail:function(title,keyword,filterKeyword){
             this.keyword = keyword;
             if(!keyword){
                 swal("请输入内容");
                 return;
             }else if(keyword.length == 1){
                 swal("内容至少输入2个字");
                 return;
             }else if(checkKeywordFilter(keyword) == 0){
                 swal("关键词只能包含中文、英文和数字");
                 return;
             }else if(checkKeywordFilter(keyword) == -1){
                 swal("内容不能超过20个字");
                 return;
             }
            this.$http.post(njxBasePath+'/checkSensitive', {
                index: 0,
                keyword: keyword,
            }, {emulateJSON: true}).then(function (res) {

                if (res.body.status != 0){
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
                    $('#wui-modal-tootips2').modal('show')
                    // swal("对不起，您的关键词包含敏感词语，请检查后再试！");
                    return false;
                }
            })
        },
        getBigEventDetail:function(id){
            window.location.href=njxBasePath+"/getBigEventDetail?id="+id;
        },
        searchEnter:function(){
            var text = $("#getSearch").val();
            app.getSearchDetail(text,text,"");
        },
        getHotLabelList:function() {
            this.$http.post(njxBasePath+'/getHotLabelList', {}, {emulateJSON: true}).then(function (res) {
                if (  res.data.code != null && res.data.data != null && res.data.code === "0000") {

                    var list =  res.body.data;
                    var data = [];
                    data.push({
                        'text': "全部",
                        'value': "0",
                    });
                    data.push({
                        'text': "国际",
                        'value': "-1",
                    });
                    for (var j = 0; j < app.typeList.length; j++) {
                        for (var i = 0; i < list.childrens.length; i++) {
                            var children = list.childrens[i];
                            if (app.typeList[j] == children.name){
                                data.push({
                                    'text': children.name,
                                    'value': children.id,
                                });
                            }
                        }
                    }
                    app.hotLabelData = data;
                }
            });
        },
        bigData:function(param){
            $.post(njxBasePath+'/getBigDataList',param,function(result){
                app.totalCount = 0;
                app.bigDataList = new Array();
                if(result.code == "0000"){
                    var data=result.data;
                    for(var i=0;i<data.length;i++){
                        var item=data[i];
                        app.bigDataList.push(item);
                    }
                    app.appPage=1;
                    app.appPagesize=15;
                    app.loadMoreFlag=true;
                }
            });
            this.$nextTick(function () {

            });
        },
        getBigEventList:function(){
            var searchObj = {startTime:this.bigEventStartTime,endTime:this.bigEventEndTime,labels:this.labels1,
                province:this.province1,city:this.city,page:this.page,pageSize:this.pageSize,webShow:1,showTag:1,sort:1,areaType:this.areaType,labelShowTag:this.labelShowTag};
            $.post(njxBasePath+'/getBigEventList',searchObj,function(result){
                result=$.parseJSON( result );
                app.allBigEventList = new Array();
                if(result.code == "0000"){
                    app.allBigEventList = result.data;
                }
            });
        },
        getHotEventCount: function () {
            //发送 post 请求
            this.$http.post(njxBasePath+'/getHotEventCount', {
                sort: this.sort,
                labels: this.labels1,
                province: this.province1,
                startTime: this.startTime,
                page:this.page,
                pageSize:this.pageSize,
                city:this.city,
                endTime:this.endTime,
                showTag:1,
                areaType:this.areaType,
                labelShowTag:this.labelShowTag
            }, {emulateJSON: true}).then(function (res) {
                if (res.data.code === "0000" || res.data.code === null || res.data.data === null) {
                    this.zt = true;
                }
                app.upEventNum = 0;
                app.downEventNum = 0;
                app.eventNum = 0;
                if (res.body.data != null){
                    app.upEventNum = res.body.data.hotAsc;
                    app.downEventNum = res.body.data.hotDesc;
                    app.eventNum = res.body.data.hotCount;
                }
            });
        },

        getHotEventList: function () {
            //发送 post 请求
            this.$http.post(njxBasePath+'/getHotEventList', {
                sort: this.sort,
                labels: this.labels1,
                province: this.province1,
                startTime: this.startTime,
                page:this.page,
                pageSize:this.pageSize,
                city:this.city,
                endTime:this.endTime,
                showTag:1,
                areaType:this.areaType,
                labelShowTag:this.labelShowTag
            }, {emulateJSON: true}).then(function (res) {
                if (res.data.code === "0000" || res.data.code === null || res.data.data === null) {
                    this.zt = true;
                }
                app.rankData = new Array();;
                if (res.body.list != null){
                    for(var i=0;i<res.body.list.length;i++){
                        var hot =  res.body.list[i];
                        if(this.labels1===""){
                            if (hot.labelNames.split(",").indexOf('明星')>-1 || hot.labelNames.split(",").indexOf('电影')>-1
                                || hot.labelNames.split(",").indexOf('电视剧')>-1
                                || hot.labelNames.split(",").indexOf('综艺')>-1
                                || hot.labelNames.split(",").indexOf('文化')>-1){
                                if(hot.ratioHotDay>30){
                                    app.rankData.push(hot);
                                }
                            }else {
                                app.rankData.push(hot);
                            }
                        }else {
                            app.rankData.push(hot);
                        }
                    }
                }
            });
        }
    },
    watch:{
        'bigDataList':function(){
            this.$nextTick(function () {
                var swiper = new Swiper('.swiper-container', {
                    pagination: {
                        el: '.swiper-pagination',
                        dynamicBullets: true,
                    },
                    autoplay: {
                        delay: 3000,
                        stopOnLastSlide: false,
                        disableOnInteraction: true,
                    },
                });
                $('.wui-tabs li').on("click",function () {
                    $(this).addClass('active').siblings().removeClass('active');
                    var index = $(this).index();
                    $(".tab-content .tab-item").eq(index).addClass('in active').siblings().removeClass('in active');
                    if($(this).attr('data-index') == 1){
                        $('.wui-dropdown').hide()
                    }else{
                        $('.wui-dropdown').show()
                    }
                });
                $('.wui-search-btn').on("click",function () {
                    var text = $("#getSearch").val()

                    app.getSearchDetail(text,text,"");
                });
                $('.big .wui-panel-item').on("click",function () {
                    window.location.href="listDetails.ftl";
                });
            });
        }
    }
});