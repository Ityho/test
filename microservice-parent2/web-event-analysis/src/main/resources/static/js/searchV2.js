// JavaScript Document
//搜索框显示隐藏清除按钮
$(function(){  
	 $("#searchKeyword").on("input",function(){
		  var t=$(this).val();
	      if(t==""){
		      $("#hotwords").show();
		      $("#searchHelp").hide();
	      }else{
	    	  searchHelp(t);
	      }
	      $("#emCancel").show();
	  });
	 
	  $(".comWord").on("input",function(){
		   var t=$(this).val();
	      if(t==""){
		      $(".compUl").next().hide();
		      $("#emCancel1").hide();
			  $("#goSearch").show();
	      }else{
	    	  searchHelp1($(this));
	      }
	  });
	  
	/*  $("#goSearch").on("click",function(){
		  var searchKeyword1 = $.trim($("#searchKeyword1").val());
		  var searchKeyword2 = $.trim($("#searchKeyword2").val());
		  var searchKeyword3 = $.trim($("#searchKeyword3").val());
		  
		  if(!searchKeyword1 && !searchKeyword2 && !searchKeyword3){
			  swal("请输入内容");
			  return false;
		  }
		  if(searchKeyword1.length == 1 || searchKeyword2.length == 1 ||
				  searchKeyword3 == 1){
			  swal("对比词至少输入2个字");
			  return false;
		  }
		  if((searchKeyword1 && searchKeyword1 == searchKeyword2) ||
				 (searchKeyword1 && searchKeyword1 == searchKeyword3) ||
				  (searchKeyword2 && searchKeyword2 == searchKeyword3)){
			  swal("对不起,重复的内容对比会浪费您的使用次数,请修改关键词后重新提交!");
			  return false;
		  }
		  var i = 0,keywordstr1 = "",keywordstr2 = "",keywordstr3 = "",keywordsJson={date:$("#hotsearchdate").val()};
		  if($.trim($("#searchKeyword1").val())){
		  	  keywordstr1 = searchKeyword1;
		  	  keywordsJson.title1 = searchKeyword1;
		  	  keywordsJson.categoryId1 = $.trim($("#categoryId1").val());
		  	  keywordsJson.type1 = $.trim($("#type1").val());
		  	  if($.trim($("#keyword1").val())){
		  		  keywordstr1 = $.trim($("#keyword1").val())+"_"+searchKeyword1;
		  		  keywordsJson.keyword1 = $.trim($("#keyword1").val());
		  	  }
		  	  if($.trim($("#filterKeyword1").val())){
		  		  keywordstr1 = $.trim($("#keyword1").val())+"_"+searchKeyword1+"_"+$.trim($("#filterKeyword1").val());
		  		  keywordsJson.filterKeyword1 = $.trim($("#filterKeyword1").val());
		  	  }
		  	  //$("#keywords").val(keywordstr1);
		  	  i++;
	  	  }
	  	  if($.trim($("#searchKeyword2").val())){
	  		  if(i>0){
	  			  keywordstr2 = searchKeyword2;
	  			  keywordsJson.title2 = searchKeyword2;
	  			  keywordsJson.categoryId2 = $.trim($("#categoryId2").val());
	  			  keywordsJson.type2 = $.trim($("#type2").val());
	  			  if($.trim($("#keyword2").val())){
	  				  keywordstr2 = $.trim($("#keyword2").val())+"_"+searchKeyword2;
	  				  keywordsJson.keyword2 = $.trim($("#keyword2").val());
	  			  }
	  			  if($.trim($("#filterKeyword2").val())){
	  				  keywordstr2 = $.trim($("#keyword2").val())+"_"+searchKeyword2+"_"+$.trim($("#filterKeyword2").val());
	  				  keywordsJson.filterKeyword2 = $.trim($("#filterKeyword2").val());
	  			  }
	  			  //$("#keywords").val( $("#keywords").val()+","+keywordstr2);
	  		  }else{
	  			   //$("#keywords").val(keywordstr2);
	  			   keywordsJson.title1 = searchKeyword2;
	  			   keywordsJson.keyword1 = $.trim($("#keyword2").val());
	  			   keywordsJson.filterKeyword1 = $.trim($("#filterKeyword2").val());
	  			   keywordsJson.categoryId1 = $.trim($("#categoryId2").val());
	  			   keywordsJson.type1 = $.trim($("#type2").val());
	  		  }
	  		  i++;
	  	  }
		  if($.trim($("#searchKeyword3").val())){
		  	 if(i>0){
	  			  keywordstr3 = searchKeyword3;
	  			  keywordsJson.title3 = searchKeyword3;
	  			  keywordsJson.categoryId3 = $.trim($("#categoryId3").val());
	  			  keywordsJson.type3 = $.trim($("#type3").val());
	  			  if($.trim($("#keyword3").val())){
	  				  keywordstr3 = $.trim($("#keyword3").val())+"_"+searchKeyword3;
	  				  keywordsJson.keyword3 = $.trim($("#keyword3").val());
	  			  }
	  			  if($.trim($("#filterKeyword3").val())){
	  				  keywordstr3 = $.trim($("#keyword3").val())+"_"+searchKeyword3+"_"+$.trim($("#filterKeyword3").val());
	  				  keywordsJson.filterKeyword3 = $.trim($("#filterKeyword3").val());
	  			  }
	  			  //$("#keywords").val( $("#keywords").val()+","+keywordstr3);
	  		  }else{
	  			  //$("#keywords").val(keywordstr3);
	  			  keywordsJson.title1 = searchKeyword3;
	  			  keywordsJson.keyword1 = $.trim($("#keyword3").val());
	  			  keywordsJson.filterKeyword1 = $.trim($("#filterKeyword3").val());
	  			  keywordsJson.categoryId1 = $.trim($("#categoryId3").val());
	  			  keywordsJson.type1 = $.trim($("#type3").val());
	  		  }
	  		  i++;
	  	  }
		  if(i>=2){
			  if(!$("#adminId").val() && !$("#platform").val()){
				  location.href = actionBase+"/indexLocal.shtml";
				  return false;
			  }
			  $("#keywords").val(JSON.stringify(keywordsJson));
			  $("#buyPrompt_jp13 input[name='packageCount']").val(i);
			  showBuy({type:13});
			  return false;
		  }else{
			  goSearchLoad();
		  }
	  });*/
	
});


 var timeout;

  
   function searchHelp(keyword){
	   if(timeout){
		   window.clearTimeout(timeout);
	   } 
	   timeout = setTimeout(function(){
		   $.ajax({
				type:  "post",
				url: njxBasePath + '/searchHelp.action',
			 	data: {historyKeyword:keyword},
				cache:false,
				success:function(data){
					if(data.status){
						var divHtml = "<section class='section' style = 'margin-bottom:0px;'><ul class='list'>";
						for(var i=0;i<data.obj.length;i++){
							var type = data.obj[i]["type"];
							var category = data.obj[i]["category"];//1:地域关键词,2:股票关键词,3:明星关键词,4:企业关键词,5:热门事件
							var titleType = "";
							if(type || category){
								titleType = "<i>";
							}
							if(type==1){
								titleType += "来自地域";
							}else if(type==2){
								titleType += "来自股票";
							}else if(type==3){
								titleType += "来自明星";
							}else if(type==4){
	                            titleType += "来自企业";
	                        }else if(type==5){
	                            titleType += "来自品牌";
	                        }else if(type==6){
	                            titleType += "来自人物";
	                        }else if(type==7){
	                            titleType += "来自景区";
	                        }else if(type==9){
	                            titleType += "来自汽车";
	                        }else if(type==10){
	                            titleType += "来自手机";
	                        }else if(type==11){
	                            titleType += "来自美妆";
	                        }else if(type==13){
	                            titleType += "来自电脑";
	                        }
							if(category){
								titleType += "("+category+")";
							}
							if(type){
								titleType += "</i>"
							}
							var title = data.obj[i]["title"];
							title = title.replace(keyword,"<label class = 'f_c3'>"+keyword+"</label>");
							var liHtml = "<li onclick=\"setSearch('"+data.obj[i]["title"]+"','"+data.obj[i]["keyword"]+"','"+data.obj[i]["filterKeyword"]+"'"+")\"><h1><span>"+title+titleType+"</span></h1></li>";
							divHtml +=liHtml;
						}
						divHtml = divHtml+"</ul><div class='emCancel' style = 'background:#f2f2f2;line-height:35px;text-align:center;'>关闭</div></section>";
						$("#searchHelp").html(divHtml);
						$('.emCancel').click(function(){  
					    	  $("#searchHelp").hide();
					    	  $("#searchKeyword").focus();
					    }); 
						$("#searchHelp").show();
					}else{
						$("#searchHelp").html("");
						$("#searchHelp").hide();
					}
				}
			});
		   $("#hotwords").css('display','none');
		},800);
   }       
function setSearch(name,keyword,filterKeyword){
	document.getElementById("hotTitle").value=name;
	document.getElementById("hotKeyword").value=keyword;
	document.getElementById("hotfilterKeyword").value=filterKeyword;
	var hotForm = $('form[name="frmGohot"]')[0];
 	if (hotForm)
		hotForm.submit();
}
  
   $(function () {
        $(".dele").click(function () {
        	$(this).parent().remove();
        })
   })  
          

		
//搜索框检测代码
function checkinput(){
	var search = $('#searchKeyword');
	var isNull = /^[\s' ']*$/;
	if(search.val() == '输入关键词，回车搜索全国设计素材资源' || search.val().length <= 0 || isNull.test(search.val())){
		search.focus();
		window.alert("请输入关键词，搜索关键词不能为空");
		return false;
	}
}


