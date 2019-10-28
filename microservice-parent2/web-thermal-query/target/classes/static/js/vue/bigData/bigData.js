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
		activeClass: 0,
		list:[],
		flagMoreData:false,
		recommendList:[],
		firstRecommend:{},
		totalCount:0,
		name:'',
		appPage:1,
		appPagesize:15,
		totalCount:0,
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
//		          {type:'8',value:'财经'},
//		          
//		          {type:'9',value:'房产'},
//		          {type:'10',value:'金融'},
//		          {type:'11',value:'医疗'},
//		          {type:'12',value:'教育'},
//		          
//		          {type:'13',value:'文化'},
//		          {type:'14',value:'汽车'},
//		          {type:'15',value:'旅游'},
//		          {type:'16',value:'政务'},
//		          
//		          {type:'17',value:'法治'},
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
		console.log("---created");
		this.initData();
	},
	methods:{
		GotoPage:function(searchObj,isInit){
			$.post(njxImgSrc+'/bigData/queryDataGotoListPage.action',searchObj,function(result){
				if(result.code == "0000"){
					var data=result.data;
					for(var i=0;i<data.length;i++){
						var item=data[i];
						app.list.push(item);
					}
					if(isInit){
						app.appPage=1;
						app.appPagesize=15;
						if(result.totalCount>1){
							app.flagMoreData=true;
						}else{
							app.flagMoreData=false;
						}
					}else{
						if(result.totalCount>app.appPage){
							app.flagMoreData=true;
						}else{
							app.flagMoreData=false;
						}
					}
				}else if(result.code == "9999"){
					app.flagMoreData=false;
//					showMsgInfo(0, result.message, 0)
				}
			});
		},
		initData:function(){
			var param = {
				page : 1,  
				pagesize : 15,
			};
			$.post(njxImgSrc+'/bigData/queryInitBigDataGoto.action',param,function(result){
				app.totalCount = 0;
				if(result.code == "0000"){
					var data=result.data;
					for(var i=0;i<data.length;i++){
						var item=data[i];
						app.list.push(item);
					}
					app.totalCount = result.totalCount;
					app.recommendList= result.recommendList;
					if (result.recommendList === undefined || result.recommendList.length  == 0) {
						
					}else{
						app.firstRecommend=result.recommendList[0];
					}
					if(result.totalCount>1){
						app.flagMoreData=true;
					}else{
						app.flagMoreData=false;
					}
					app.appPage=1;
					app.appPagesize=15;
				}else if(result.code == "9999"){
					app.flagMoreData=false;
//					showMsgInfo(0, result.message, 0)
				}
			});
		},
//		onRefresh:function(type,likeName){
//			var param = {
//				page : 1,  
//				pagesize : 15,
//				type:type,
//				likeName:likeName
//			};
//			this.GotoPage(param,true);
//		},
		searchRefresh:function(index,item){
			var type=item.type;
			this.selectType=type;
			this.activeClass = type;
			var likeName=$("#searchKeyword").val();
			var param = {
				page : 1,  
				pagesize : 15,
				type:type,
				likeName:likeName
			};
			app.list=[];
			this.GotoPage(param,true);
		},
		searchLikeName:function(){
			var type=this.selectType;
			this.activeClass = type;
			var likeName=$("#searchKeyword").val();
			var param = {
				page : 1,  
				pagesize : 15,
				type:type,
				likeName:likeName
			};
			app.list=[];
			this.GotoPage(param,true);
		},
		searchRefreshTwo:function(index,item){
//			this.$set(this.typeList, 0 , item); //VUE实例用Vue.set(this.number, 0 , "A")
			var typeItem=this.typeList[this.typeList.length-1];
			this.typeList.splice(this.typeList.length-1,1,item);
			this.typeListTwo.splice(index,1,typeItem);
			
			var type=item.type;
			this.selectType=type;
			this.activeClass = type;
			var likeName=$("#searchKeyword").val();
			var param = {
				page : 1,  
				pagesize : 15,
				type:type,
				likeName:likeName
			};
			app.list=[];
			this.GotoPage(param,true);
		},
		loadMore:function(type,likeName){
			var type1=this.selectType;
			var param = {
				page : 2,
				pagesize : 15,
				type:type1,
				likeName:likeName
			};
			app.appPage++;
			param.page=app.appPage;
			this.GotoPage(param,false);
		},
		getTypeValue:function(type){
			for(var i=0;i<app.typeList.lenght;i++){
				var typeObj=app.typeList[i];
				if(type==typeObj.type){
					return typeObj.value;
				}
			}
			return "";
		},articleDetail:function(item){
//			window.open(item.articleUrl);
			openNewWin(item.articleUrl);
//			var param = {
//					articleId : item.id,  
//				};
//			$.post(njxImgSrc+'/bigData/wyqCaseDetail.action',param,function(result){
//				app.totalCount = 0;
//				if(result.code == "0000"){
//					var data=result.data;
//					for(var i=0;i<data.length;i++){
//						var item=data[i];
//						app.list.push(item);
//					}
//					app.totalCount = result.totalCount;
//					app.recommendList= result.recommendList;
//					if (result.recommendList === undefined || result.recommendList.length  == 0) {
//						
//					}else{
//						app.firstRecommend=result.recommendList[0];
//					}
//					if(result.totalCount>1){
//						app.flagMoreData=true;
//					}else{
//						app.flagMoreData=false;
//					}
//					app.appPage=1;
//					app.appPagesize=15;
//				}else{
//					showMsgInfo(0, result.message, 0)
//				}
//			});
		}
		
	}
});