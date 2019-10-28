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
		//1.ʱ�� 2.��� 3.���� 4.�Ƽ� 5.��Ȼ�ֺ� 6.���� 7.���� 8.�ƾ� 9.���� 10.���� 11.ҽ�� 12.���� 13.�Ļ� 14.���� 15.���� 16.���� 17.����
		typeList:[
		          {type:'0',value:'ȫ��'},
		          {type:'1',value:'ʱ��'},
		          {type:'2',value:'���'},
		          {type:'3',value:'����'},
		          {type:'4',value:'�Ƽ�'},
		          
		          {type:'5',value:'��Ȼ�ֺ�'},
		          {type:'6',value:'����'},
		          {type:'7',value:'����'}
//		          {type:'8',value:'�ƾ�'},
//		          
//		          {type:'9',value:'����'},
//		          {type:'10',value:'����'},
//		          {type:'11',value:'ҽ��'},
//		          {type:'12',value:'����'},
//		          
//		          {type:'13',value:'�Ļ�'},
//		          {type:'14',value:'����'},
//		          {type:'15',value:'����'},
//		          {type:'16',value:'����'},
//		          
//		          {type:'17',value:'����'},
		],
		typeListTwo:[
		          {type:'8',value:'�ƾ�'},
		          {type:'9',value:'����'},
		          {type:'10',value:'����'},
		          {type:'11',value:'ҽ��'},
		          {type:'12',value:'����'},
		          
		          {type:'13',value:'�Ļ�'},
		          {type:'14',value:'����'},
		          {type:'15',value:'����'},
		          {type:'16',value:'����'},
		          
		          {type:'17',value:'����'},
		          {type:'18',value:'Ʒ������'},
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
//			this.$set(this.typeList, 0 , item); //VUEʵ����Vue.set(this.number, 0 , "A")
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