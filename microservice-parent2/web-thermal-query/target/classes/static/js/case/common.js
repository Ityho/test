
$(document).ready(function(){
$(function(){
          $('.empty').click(function(){  
          $('.inputBox').val(""); 
           document.getElementById('empty').style.display="none";//隐藏
         }); 
         });
        
         });   
          function dis(){
          var t=document.getElementById('inputText').value;
          if(t==""){
          document.getElementById('empty').style.display="none";//隐藏   
          }
          else{
          document.getElementById('empty').style.display="block";//显示 
          }
          }
   
  
 

$(function(){
	  
    
 	
	//转发层级下拉更多
	$(".table_list > li:gt(3)").hide();
			        var num=1;
			        $(".more").on("click",function(){
			           if(num){
			               num--;
			               $(".table_list > li:gt(3)").show(300);
			               $(this).html("<img src='images/arrow-down.png' class='rotate180'/>");
			           }else{
			               num++;
			               $(".table_list > li:gt(3)").hide(300);
			              $(this).html("<img src='images/arrow-down.png' class='rotate0'/>");
			           }
			        });
			        

 
   //转发层级更多
    $(".m_hide").hide();
			        //var num=1;
			        $(".arrow").on("click",function(){
			           if(!$(this).hasClass("rotate90")){
			               //num--;
			               $(this).prev().find(".m_hide").show(300);
			               $(this).addClass('rotate90'); 
			           }else{
			               //num++;
			               $(this).prev().find(".m_hide").hide(300);
			              $(this).removeClass('rotate90'); 
			           }
		});
 
 
 
      //转发量TOP10博文更多
    $(".mwblist2 > li:gt(2)").hide();
			        var num=1;
			        $(".more2").on("click",function(){
			           if(num){
			               num--;
			               $(".mwblist2 > li:gt(2)").show(300);
			               $(this).html("隐藏更多 <img src='images/arrow-down2.png' class='rotate180'/>");
			           }else{
			               num++;
			               $(".mwblist2 > li:gt(2)").hide(300);
			              $(this).html("展开更多 <img src='images/arrow-down2.png' class='rotate0'/>");
			           }
			        });
			        
	 $("#play_btn").on("click",function(){
          $(".demoPlay").fadeIn(300);
     });  
     $("#closePlay").on("click",function(){
          $(".demoPlay").fadeOut(300);
     });  
     
     
      //显示/隐藏帮助页面
		$("#helpBtn").on("click",function(){
				$(".J-slider").css("zIndex",1000);
				$(".wrapper").css("display",'none');
       	$(".J-slider").show();
    });
    
    $(".J-slider .close").on("click",function(){
				$(".J-slider").css("zIndex",-1);
				$(".wrapper").css("display",'block');
       	$(".J-slider").hide();
    });
    
    
    
    $(".zhezhao").on("touchend",function(){
    	window.ontouchmove=function(e){
            e.preventDefault && e.preventDefault();
            e.returnValue=true;
            e.stopPropagation && e.stopPropagation();
            return true;
      } ;
          $(".zhezhao").fadeOut(300);
          $(".footPOP").fadeOut(300);   
			    $(".footPOP").removeClass('downShow'); 
			    $(".footPOP").addClass('downOut'); 
			   
        });
		    $(".cancel").on("click",function(){
		    	window.ontouchmove=function(e){
            e.preventDefault && e.preventDefault();
            e.returnValue=true;
            e.stopPropagation && e.stopPropagation();
            return true;
      } ;
          $(".zhezhao").fadeOut(300);
         $(".footPOP").addClass('downOut'); 
          $(".footPOP").removeClass('downShow'); 
        });
        
        
    //分享弹出层
     $("#fenxiang").on("touchend",function(){
    	window.ontouchmove=function(e){
            e.preventDefault && e.preventDefault();
            e.returnValue=false;
            e.stopPropagation && e.stopPropagation();
            return false;
      } ;
         $(".zhezhao").fadeIn(300);
          $("#fenxiangPOP").fadeIn(300);
          $(".footPOP").addClass('downShow'); 
          $(".footPOP").removeClass('downOut'); 
    });
          
});
