Vue.directive('filterSpecialChar', {
        update: function (el, { value, modifiers }, vnode) {
                try {
                    //�˴�����debug����el����ֵ��ʲô,������ʾ����ԭ���ؼ�input,�����ʹ��element�е�<el-input />��ǩ,��Ҫͨ�� el.children[0] �õ�ԭ��input.
                        if(!el.value){
                           return false; 
                         }
                        el.value = el.value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g, "");
                        el.dispatchEvent(new Event(modifiers.lazy ? 'change' : 'input'))
                } catch (e) {
                }
        }
})

var activeTab4 = new Vue({
	el:'#activeTab4',
	filters:{
		// ����ʱ���
		formatDate: function (date, formatStr) {
			var formatStr = formatStr || 'YYYY-MM-DD HH:mm:ss';
			return moment(date*1000).format(formatStr);
		}
	},
	data:{
		loadMoreFlag:true,
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
		likeName:'',
		hotWeiboTypeList:[
			{id:'8698',name:'ȫ��',childType:4},
			{id:'4188',name:'���',childType:4},
			{id:'4388',name:'Ц��',childType:4},
			{id:'4488',name:'ʱ��',childType:4},
			{id:'1688',name:'����',childType:4},
			
			{id:'3288',name:'��Ӱ',childType:4},
			{id:'1388',name:'����',childType:4},
			{id:'1288',name:'���',childType:4},
			{id:'2788',name:'�ȳ�',childType:4},
			
			{id:'2688',name:'��ʳ',childType:4},
			{id:'2588',name:'����',childType:4},
			{id:'2188',name:'����',childType:4},
			{id:'1199',name:'��Ƶ',childType:4},
			
			{id:'5288',name:'����',childType:4},
			{id:'1488',name:'����',childType:4},
			{id:'5788',name:'����',childType:4}
		          
		],
		categoryId:"8698",
		activeTypeClass:"ȫ��",
		hotClassData:[]
	},
	created:function(){
		console.log("---created");
		this.initData();
		this.hotClassData=this.hotWeiboTypeList;
//		this.listenScroll();
		
//		window.onscroll = function(){
//			// ����scrollTop�ǹ���������ʱ�����붥���ľ���
//			var scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
//			// ����windowHeight�ǿ������ĸ߶�
//			var windowHeight = document.documentElement.clientHeight || document.body.clientHeight;
//			// ����scrollHeight�ǹ��������ܸ߶�
//			var scrollHeight = document.documentElement.scrollHeight||document.body.scrollHeight;
//			// ���������ײ�������
//			if(scrollTop+windowHeight==scrollHeight){
//				// д��̨�������ݵĺ���
//				activeTab4.loadMore();
//			}
//		};
	},
	
	methods:{
		GotoPage:function(searchObj,isInit){
			$.post(actionBase+'/hotWeiBoList/hotWeiBoList.action',searchObj,function(result){
				console.log("searchObj.categoryId="+searchObj.categoryId+"|page="+searchObj.page+"|pagesize="+searchObj.pagesize+"|type="+searchObj.type);
				if(result.code == "0000"){
					var data=result.data;
					for(var i=0;i<data.length;i++){
						var item=data[i];
						item.openFlag=true;
						activeTab4.list.push(item);
					}
					if(isInit){
						activeTab4.appPage=1;
						activeTab4.appPagesize=15;
						if(result.totalCount>1){
							activeTab4.flagMoreData=true;
						}else{
							activeTab4.flagMoreData=false;
						}
					}else{
						if(result.totalCount>activeTab4.appPage){
							activeTab4.flagMoreData=true;
						}else{
							activeTab4.flagMoreData=false;
						}
					}
				}else{
					console.log("error----searchObj.categoryId="+searchObj.categoryId+"|page="+searchObj.page+"|pagesize="+searchObj.pagesize+"|type="+searchObj.type);
					showMsgInfo(0, "��������", 0)
				}
				activeTab4.loadMoreFlag=true;
			});
		},
		initData:function(){
			var param = {
				categoryId : this.categoryId,
				flag : 1,
				type : "7",
				page : 1,  
				pagesize : 20,
			};
			$.post(actionBase+'/hotWeiBoList/hotWeiBoList.action',param,function(result){
				activeTab4.totalCount = 0;
				if(result.code == "0000"){
					var data=result.data;
					activeTab4.list.length=0;
					for(var i=0;i<data.length;i++){
						var item=data[i];
						item.openFlag=true;
						activeTab4.list.push(item);
					}
					activeTab4.totalCount = result.totalCount;
					
					if(result.totalCount>1){
						activeTab4.flagMoreData=true;
					}else{
						activeTab4.flagMoreData=false;
					}
					activeTab4.appPage=1;
					activeTab4.appPagesize=15;
				}else{
					showMsgInfo(0, "��������", 0)
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
		searchRefresh:function(type){
			this.activeClass = type;
			var likeName=$("#searchKeyword").val();
			var param = {
				page : 1,  
				pagesize : 20,
				type:type,
				likeName:likeName
			};
			activeTab4.list=[];
			this.GotoPage(param,true);
		},
		loadMore:function(type,likeName){
			var param = {
				categoryId : this.categoryId,
				flag : 1,
				type : "7",
				page : 2,  
				pagesize : 20,
			};
//			var param = {
//				page : 2,
//				pagesize : 15,
//				type:type,
//				likeName:likeName
//			};
			activeTab4.appPage++;
			param.page=activeTab4.appPage;
			this.GotoPage(param,false);
			
		},
		getTypeValue:function(type){
			for(var i=0;i<activeTab4.typeList.lenght;i++){
				var typeObj=activeTab4.typeList[i];
				if(type==typeObj.type){
					return typeObj.value;
				}
			}
			return "";
		},
		createAnalysis : function(url){
			window.open(actionBase+"/i/singleWeiboAnalysis/index.shtml?isFromKeywordList=true&weiboUrlDetail="+url);
		},
		weiboDetail : function(url){
			window.open(url);
		},changeParm:function (obj) {
        	this.activeTypeClass = obj.name;
//        	if("childType" in obj){//�ȶ�΢��
        		activeTab4.categoryId=obj.id;
        		activeTab4.list.length=0;
        		this.$forceUpdate();
        		this.initData();
//        	}
        },filerExcludeSpecial : function(s) {
            // ȥ��ת���ַ�  
            s = s.replace(/[\'\"\\\/\b\f\n\r\t]/g, '');  
            // ȥ�������ַ�  
            s = s.replace(/[\@\#\$\%\^\&\*\{\}\:\"\L\<\>\?]/);  
            return s;  
        }
        ,handleInfoShow :function(item){
//        	item.openFlag=!item.openFlag;
        	if($(item).attr('data-index')==1){
         		$(item).parent().find('span').css('height','initial');
                $(item).html('��������<i class="icon icon-shouqi"></i>');
                $(item).attr('data-index',2);
         	}else{
         		$(item).parent().find('span').css('height','90px');
                $(item).html('�鿴����<i class="icon icon-open"></i>');
                $(item).attr('data-index',1);
         	}

//        	if($(this).attr('data-index')==1){
//         		$(this).parent().siblings('.check-content').show();
//         		$(this).parent().hide()
//         	}else{
//         		$(this).parent().siblings('.check-text').show();
//         		$(this).parent().hide()
//         	}

        },viewUpdate : function(){
        	$(".weibo_a").each(function(){
    			var rowNum=Math.round($(this).height()/parseFloat(30));
    			if(rowNum>3){
    				//��ʾ�鿴����
    				$(this).parent().children('span').css('height','90px');
    				$(this).parent().children("a").show();
    			}else{//����ȫ��ʾ
    				$(this).parent().children("a").hide();
    				$(this).parent().children('span').css('height','initial');
    			}
    			$(this).attr("class", "weibo_b");
    			
    		});
        }
//        ,
//        listenScroll:function(){
//                  $(window).scroll(function () {
//                      let scrollTop = $(window).scrollTop();
//                      let windowTop = $(window).height();
//                      let documentTop = $(document).height();
//                      if(documentTop - windowTop <= scrollTop){
//                    	  this.loadMore();
//                      }
//                  });
//              }
		
	}
	,watch:function(){
		
	},updated:function(){
		console.log("------updated");
//		setTimeout('this.viewUpdate()',300);
//		this.viewUpdate();
		setTimeout(() =>{
			$(".weibo_a").each(function(){
				var rowNum=Math.round($(this).height()/parseFloat(30));
				if(rowNum>3){
					//��ʾ�鿴����
					$(this).parent().children('span').css('height','90px');
					$(this).parent().children("a").show();
				}else{//����ȫ��ʾ
					$(this).parent().children("a").hide();
					$(this).parent().children('span').css('height','initial');
				}
				$(this).attr("class", "weibo_b");
				
			});
		},300);
	}
});

$(document).ready(function(){
	
	$(document).endlessScroll({

		bottomPixels: 450,

		fireDelay: 10,

		callback: function(p){
			if(activeTab4.loadMoreFlag){
				activeTab4.loadMoreFlag=false;
				activeTab4.loadMore();
			}
		}

	});

});
