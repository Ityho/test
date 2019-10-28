//初始播放背景音乐
	audioAutoPlay("music");
function audioAutoPlay(id){  
	var audio = document.getElementById(id),  
	    play = function(){  
	        audio.play();  
	        document.removeEventListener("touchstart",play, false);  
	    };  
	audio.play();
	document.addEventListener("WeixinJSBridgeReady", function () {  
	    play();  
	}, false);  
	document.addEventListener('YixinJSBridgeReady', function() {  
	    play();  
	}, false);  
	document.addEventListener("touchstart",play, false);  
}
//按钮控制播放背景音乐
   function a(){
     var audio = document.getElementById('music'); 
     if(audio.paused){                 
         audio.play();//audio.play();// 播放  
         $(".musicPlay").find("img").addClass('rotate360'); 
     }
     else{
          audio.pause();// 暂停
          $(".musicPlay").find("img").removeClass('rotate360'); 
     } 
   }

//页面滑动效果
(function(){

var now = { row:1, col:1 }, last = { row:0, col:0};
const towards = { up:1, right:2, down:3, left:4};
var isAnimating = false;

s=window.innerHeight/500;
ss=250*(1-s);

$('.wrap').css('-webkit-transform','scale('+s+','+s+') translate(0px,-'+ss+'px)');

document.addEventListener('touchmove',function(event){
	event.preventDefault(); },false);

Zepto(document).swipeUp(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row != 10) { now.row = last.row+1; now.col = 1; pageMove(towards.up);}	
})

Zepto(document).swipeDown(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row!=1) { now.row = last.row-1; now.col = 1; pageMove(towards.down);}	
})
//
//$(document).swipeLeft(function(){
//	if (isAnimating) return;
//	last.row = now.row;
//	last.col = now.col;
//	if (last.row>1 && last.row<10 && last.col==1) { now.row = last.row; now.col = 2; pageMove(towards.left);}	
//})
//
//
//$(document).swipeLeft(function(){
//	if (isAnimating) return;
//	last.row = now.row;
//	last.col = now.col;
//	if (last.row==2 && last.row<10 && last.col==1) { now.row = last.row; now.col = 1; pageMove(towards.left);}	
//})

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==3 && last.row<10 && last.col==2) { now.row = last.row; now.col = 3; pageMove(towards.left);}	
})

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==5 && last.row<10 && last.col==1) { now.row = last.row; now.col = 2; pageMove(towards.left);}	
})
Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==5 && last.row<10 && last.col==2) { now.row = last.row; now.col = 3; pageMove(towards.left);}	
})

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==5 && last.row<10 && last.col==3) { now.row = last.row; now.col = 4; pageMove(towards.left);}	
})

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==6 && last.row<10 && last.col==1) { now.row = last.row; now.col = 2; pageMove(towards.left);}	
})
Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==6 && last.row<10 && last.col==2) { now.row = last.row; now.col = 3; pageMove(towards.left);}	
})

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==6 && last.row<10 && last.col==3) { now.row = last.row; now.col = 4; pageMove(towards.left);}	
})

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==7 && last.row<10 && last.col==1) { now.row = last.row; now.col = 2; pageMove(towards.left);}	
})
Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==7 && last.row<10 && last.col==2) { now.row = last.row; now.col = 3; pageMove(towards.left);}	
})

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==7 && last.row<10 && last.col==3) { now.row = last.row; now.col = 4; pageMove(towards.left);}	
})
Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==7 && last.row<10 && last.col==4) { now.row = last.row; now.col = 5; pageMove(towards.left);}	
})

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==8 && last.row<10 && last.col==1) { now.row = last.row; now.col = 2; pageMove(towards.left);}	
})
 

Zepto(document).swipeLeft(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row==9 && last.row<10 && last.col==1) { now.row = last.row; now.col = 2; pageMove(towards.left);}	
})
 

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==2) { now.row = last.row; now.col = 1; pageMove(towards.right);}	
})

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==3) { now.row = last.row; now.col = 2; pageMove(towards.right);}	
})

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==4) { now.row = last.row; now.col = 3; pageMove(towards.right);}	
})

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==5) { now.row = last.row; now.col = 4; pageMove(towards.right);}	
})

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==6) { now.row = last.row; now.col = 5; pageMove(towards.right);}	
})

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==7) { now.row = last.row; now.col = 6; pageMove(towards.right);}	
})

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==8) { now.row = last.row; now.col = 7; pageMove(towards.right);}	
})

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==9) { now.row = last.row; now.col = 8; pageMove(towards.right);}	
})

Zepto(document).swipeRight(function(){
	if (isAnimating) return;
	last.row = now.row;
	last.col = now.col;
	if (last.row>1 && last.row<10 && last.col==10) { now.row = last.row; now.col = 9; pageMove(towards.right);}	
})

function pageMove(tw){
	var lastPage = ".page-"+last.row+"-"+last.col,
		nowPage = ".page-"+now.row+"-"+now.col;
	
	switch(tw) {
		case towards.up:
			outClass = 'pt-page-moveToTop';
			inClass = 'pt-page-moveFromBottom';
			break;
		case towards.right:
			outClass = 'pt-page-moveToRight';
			inClass = 'pt-page-moveFromLeft';
			break;
		case towards.down:
			outClass = 'pt-page-moveToBottom';
			inClass = 'pt-page-moveFromTop';
			break;
		case towards.left:
			outClass = 'pt-page-moveToLeft';
			inClass = 'pt-page-moveFromRight';
			break;
	}
	isAnimating = true;
	$(nowPage).removeClass("hide");
	
	$(lastPage).addClass(outClass);
	$(nowPage).addClass(inClass);
	
	setTimeout(function(){
		$(lastPage).removeClass('page-current');
		$(lastPage).removeClass(outClass);
		$(lastPage).addClass("hide");
		$(lastPage).find("img").addClass("hide");
		$(lastPage).find(".wrap>div").addClass("hide");
		$(lastPage).find(".title").addClass("hide");
		$(lastPage).find(".conterText").addClass("hide");
		
		$(nowPage).addClass('page-current');
		$(nowPage).removeClass(inClass);
		$(nowPage).find("img").removeClass("hide");
		$(nowPage).find(".wrap>div").removeClass("hide");
		$(nowPage).find(".title").removeClass("hide");
		$(nowPage).find(".conterText").removeClass("hide");
		
		isAnimating = false;
	},600);
}

})();