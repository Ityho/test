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
		list:[]
	},
	created:function(){

		var param = {
			type : 0,
			page : 1,
			pagesize : 15
		};
		this.initData(param);
	},
	methods:{
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
				type:0
			};
			app.appPage++;
			param.page=app.appPage;
			this.initData(param);
		},searchRefresh:function(index,item){
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