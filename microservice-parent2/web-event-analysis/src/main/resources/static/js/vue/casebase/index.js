var app = new Vue({
    el: '#app',
    data:{
            isActive: true,
            navData: [
                {
                    id: 0,
                    text: '全部'
                },
                {
                    id: 1,
                    text: '时事'
                },
                {
                    id: 2,
                    text: '社会'
                },
                {
                    id: 3,
                    text: '体育'
                },
                {
                    id: 4,
                    text: '科技'
                },
                {
                    id: 5,
                    text: '灾害'
                },
                {
                    id: 6,
                    text: '娱乐'
                },
                {
                    id: 7,
                    text: '人物'
                },
                {
                    id: 8,
                    text: '财经'
                },
                {
                    id: 9,
                    text: '房产'
                },
                {
                    id: 10,
                    text: '金融'
                },
                {
                    id: 11,
                    text: '医疗'
                },
                {
                    id: 12,
                    text: '教育'
                },
                {
                    id: 13,
                    text: '文化'
                },
                {
                    id: 14,
                    text: '汽车'
                },
                {
                    id: 15,
                    text: '旅游'
                },
                {
                    id: 16,
                    text: '政务'
                },
                {
                    id: 17,
                    text: '法治'
                }, {
                    id: 19,
                    text: '突发'
                }
            ],
            currentTab: 0,
            navScrollLeft: 0,
            responseData: {},
            listData: [],
            pageCount: 1,
            pageSize: 20,
            maxPage: '',
            labelNum: '',
            order: 1, // 热度-时间
            sort: 1, // 升降序
            isTrue: true,
            errShow: true,
            hasMore: true,
            doList: true,
            clo: false

    },
    methods: {
        searchClick: function () {
            app.pageCount=1
            // const url = '../../pages/search/main'
            // wx.navigateTo({ url })
            var title=$("#title").val();
            const host = '/casebase/caseBaseList';
            // const host ='https://api-open.51wyq.cn/wrd/casebase/api/v1/list/casebase'
            let param = {}
                param = {
                    title: title,
                    label: this.labelNum,
                    page: app.pageCount,
                    pageSize: app.pageSize,
                    order: app.order,
                    sort: app.sort
                }
                if(app.doList&&title!=null&&title!=''){
                app.doList=false;
                    this.$http.post(host,param,{emulateJSON: true}).then(function(res){
                        if (res.data.data !== null) {
                            for (let item in res.data.data) {
                                res.data.data[item].startTimeStr = this.formatDate(res.data.data[item].startTimeStr)
                                res.data.data[item].endTimeStr = this.formatDate(res.data.data[item].endTimeStr)
                            }
                            app.doList=true;
                            app.listData = res.data.data
                            app.maxPage = res.data.maxPage
                            app.pageCount++;
                            if (res.data.maxPage === 1||res.data.maxPage===app.pageCount) {
                                this.hasMore = false
                                app.pageCount=1
                            }
                        } else {
                            app.hasMore = false
                            app.doList=true;
                            app.listData = []
                        }
                        console.log(res.data)
                        console.log(this.listData)
                    }).catch(function(err){
                        console.log(err)
                        app.errShow = false
                        app.doList=true;
                    })
                }
        },
        switchNav: function (index, ind) {
            var title=$("#title").val();
            if (parseInt(this.currentTab) === ind) {
                return false
            } else {
                this.currentTab = ind
                this.navScrollLeft = (this.currentTab - 2) * 75
            }
            if (index === 0) {
                this.clear()
                this.isTrue = true
                this.labelNum = ''
                if(title!=null&&title!=''){
                    this.searchClick();
                }else{
                    this.getServerData()
                }

            } else {
                this.clear()
                this.isTrue = false
                this.labelNum = index
                if(title!=null&&title!=''){
                    this.searchClick();
                }else{
                    this.getServerData()
                }
            }
        },
        goClose: function(){
            app.clo=false;
            $("#title").val("");
            app.reloadData(1,1);
        },
        reloadData: function (order, sort) {
            if (this.labelNum !== '') {
                this.isTrue = false
            }
            this.order = order
            this.sort = sort
            this.clear()
            this.getServerData()
        },
        formatDate: function (stamp) {
            const date2 = stamp.replace(/-/g, '/')
            const date = new Date(date2)
            let Y = date.getFullYear() + '/'
            let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/'
            let D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate())
            return Y + M + D
        },
        getServerData: function () {
            const host = '/casebase/caseBaseList';
            var title=$("#title").val();
            if(title==''){
                title=null;
            }
            // const host ='https://api-open.51wyq.cn/wrd/casebase/api/v1/list/casebase'
            let param = {}
            if (this.isTrue) {
                param = {
                    title: title,
                    page: this.pageCount,
                    pageSize: this.pageSize,
                    order: this.order,
                    sort: this.sort
                }
            } else {
                param = {
                    title: title,
                    label: this.labelNum,
                    page: this.pageCount,
                    pageSize: this.pageSize,
                    order: this.order,
                    sort: this.sort
                }
            }
            if(this.doList){
                this.doList=false;
                this.$http.post(host,param,{emulateJSON: true}).then(function(res){
                    if (res.data.data !== null) {
                        for (let item in res.data.data) {
                            res.data.data[item].startTimeStr = app.formatDate(res.data.data[item].startTimeStr)
                            res.data.data[item].endTimeStr = app.formatDate(res.data.data[item].endTimeStr)
                        }
                        app.listData = app.listData.concat(res.data.data)
                        app.maxPage = res.data.maxPage
                        app.doList=true;
                        app.pageCount++;
                        if (res.data.maxPage === 1||res.data.maxPage===app.pageCount) {
                            app.hasMore = false
                            app.pageCount=1
                        }
                    } else {
                        app.hasMore = false
                        app.doList=true;
                    }
                    console.log(res.data)
                    console.log(this.listData)
                }).catch(function(err){
                    console.log(err)
                    app.errShow = false
                    app.doList=true;
                })
            }

        },
        clear: function () {
            this.pageCount = 1;
                this.maxPage = '';
                if(this.listData!=null&&this.listData!=''){
                    this.listData.splice(0, this.listData.length)
                }
            this.hasMore = true
        },
        refresh:function(){
            console.log('refresh')
        },
        infinite:function(){
            console.log('infinite')
        },
        ontip:function(id){
            // console.log(val)
            // console.log(valtipe)
            // const host = '/casebase/caseBaseDetail';
            // // const host ='https://api-open.51wyq.cn/wrd/casebase/api/v1/list/casebase'
            // let param = {id:val,valtipe:valtipe};
            window.location.href="/casebase/caseBaseDetail?id="+id;
            // window.open("/casebase/caseBaseDetail?id="+id,"blank");

        }
    },
    onReachBottom() {
        this.pageCount = this.pageCount + 1
        if (this.pageCount <= this.maxPage) {
            this.getServerData()
        } else {
            this.hasMore = false
            return false
        }
    },
    created() {
        this.getServerData()
    },
    onLoad() {
        this.getServerData()
    }
})