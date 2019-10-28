Vue.filter("formatDate",function(date,formatStr){
    var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
    return moment(date).format(formatStr);
});
/**
 *
 */
var app = new Vue({
    el:'#app',
    data:{
        loadMoreFlag:true,
        page : 1,
        pagesize : 15,
        activeClass: 0,
        list:[],
        bList:[],
        idList:[],
        recommendList:[],
        firstRecommend:{},
        totalCount:0,
        name:'',
        type:0,
        selectType:'0',
        likeName:'',
        //1.时事 2.社会 3.体育 4.科技 5.自然灾害 6.娱乐 7.人物 8.财经 9.房产 10.金融 11.医疗 12.教育 13.文化 14.汽车 15.旅游 16.政务 17.法治
        typeList:[
            {type:'0',value:'全部'},
            {type:'1',value:'时事'},
            {type:'2',value:'社会'},
            {type:'3',value:'体育'},
            {type:'4',value:'科技'},

            {type:'5',value:'自然灾害'},
            {type:'6',value:'娱乐'},
            {type:'7',value:'人物'}

        ],
        typeListTwo:[
            {type:'8',value:'财经'},
            {type:'9',value:'房产'},
            {type:'10',value:'金融'},
            {type:'11',value:'医疗'},
            {type:'12',value:'教育'},

            {type:'13',value:'文化'},
            {type:'14',value:'汽车'},
            {type:'15',value:'旅游'},
            {type:'16',value:'政务'},

            {type:'17',value:'法治'},
            {type:'18',value:'品牌舆情'},
        ]
    },
    created:function(){
        var param = {
            page : 1,
            pageSize : 15
        };
        this.readBuy(param);
    },
    // mounted: function () {
    //     this.readBuy();
    // },
    methods:{
        deletReport:function (id,index) {
            $("#wui-modal-tootips").modal('show');
            $("#dodel").one("click",function() {
                $("#wui-modal-tootips").modal('hide');
                $.post(njxBasePath+'/deletReport',{reportId:id},function(result){
                    if(result.code == "0000"){
                        app.bList.splice(index,1);
                    }else{
                        $("#mesg").text("删除失败");
                        $("#wui-modal-tootips1").modal('show');
                    }

                });
            });

        },
        readBuy:function (param) {
            $.ajax({
                url: njxBasePath + '/queryBuyedBigDataByUserId',
                type: 'POST',
                cache: 'false',
                data:param,
                success: function (res) {
                    app.totalCount = 0;
                    if(res.code=='9999'){
                        app.bList=[];
                        app.loadMoreFlag=false;
                        $("#d_loading").hide();
                        $("#getButtom").show();
                    }else{
                        app.bList=res.data;
                        app.appPagesize=15;
                        if (app.totalCount < 10 ) {
                            app.loadMoreFlag=false;
                            $("#d_loading").hide();
                            $("#getButtom").show();
                        } else {
                            app.loadMoreFlag=true;
                        }
                    }
                }
            });
        },
        initData:function(param){
            $.post(njxBasePath+'/getBigDataList',param,function(result){
                app.totalCount = 0;
                if(result.code == "0000"){
                    var data=result.data;
                    for(var i=0;i<data.length;i++){
                        var item=data[i];
                        app.list.push(item);
                    }
                    app.appPage=1;
                    app.appPagesize=15;
                    app.totalCount=result.totalCount;
                    if (app.totalCount < 10 ) {
                        app.loadMoreFlag=false;
                        $("#d_loading").hide();
                        $("#getButtom").show();
                    } else {
                        app.loadMoreFlag=true;
                    }
                }else {
                    app.loadMoreFlag=false;
                    $("#d_loading").hide();
                    $("#getButtom").show();
                }
            });
            this.$nextTick(function () {

            });
        },
        loadMore:function(){
            this.page++;
            var param = {
                page : this.page,
                pagesize : 15,
                type : this.type
            };
            this.initData(param);
        },
        searchRefresh:function(index,item){
            var type=item.type;
            this.selectType=type;
            this.activeClass = type;
            var param = {
                page : 1,
                pagesize : 15,
                type:type,
            };
            app.list=[];
            this.initData(param);
        }

    }
});
$(document).ready(function(){
    $(document).endlessScroll({
        bottomPixels: 250,
        fireDelay: 10,
        callback: function(p){
            if(app.loadMoreFlag){
                app.loadMoreFlag=false;
                app.loadMore();
            }
        }

    });

});

