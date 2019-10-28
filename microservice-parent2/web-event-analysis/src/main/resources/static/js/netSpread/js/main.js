var audioDiv = document.getElementById('audio-bg')

function initClick() {
		$('.detail-btn').on('click', function() {
		$('.swiper-container').hide();
		$('#modulePiic').show();
	      	//当焦点在弹出层的输入框之间切换时先不归位
			html2canvas($("#modulePiic")[0]).then(function(canvas) {
				document.getElementById("div-image").src=canvas.toDataURL("image/png",1);
			});
			$('#share_save').show();
		
	})
	//音乐
	var musicOff = false;

	$('.open-music').on('click', function() {
		if(musicOff) {
			audioDiv.play();
			sessionStorage.bgmusic='play';
			$(this).addClass('on').removeClass('close');
			musicOff = false;
			return false;
		} else {
			audioDiv.pause();
			sessionStorage.bgmusic='pause';
			$(this).removeClass('on').addClass('close')
			musicOff = true;
		}
	})
}

document.addEventListener('DOMContentLoaded', function() {
	function audioAutoPlay() {
		audioDiv.play();
		sessionStorage.bgmusic='play';
		document.addEventListener("WeixinJSBridgeReady", function() {
			audioDiv.play();
			sessionStorage.bgmusic='play';
		}, false);
	}
	audioAutoPlay();
});
function audioAutoPlay() {
	audioDiv.play();
	sessionStorage.bgmusic='play';
	document.removeEventListener('touchstart', audioAutoPlay);
}
document.addEventListener('touchstart', audioAutoPlay);


var hiddenProperty = 'hidden' in document ? 'hidden' :
	'webkitHidden' in document ? 'webkitHidden' :
	'mozHidden' in document ? 'mozHidden' :
	null;
var visibilityChangeEvent = hiddenProperty.replace(/hidden/i, 'visibilitychange');
var onVisibilityChange = function() {
	if(!document[hiddenProperty]) {
		//激活状态
		
		if(sessionStorage.bgmusic=='pause'){
		    audioDiv.pause();
		    sessionStorage.bgmusic='pause';
		}else{
			audioDiv.play();
		    sessionStorage.bgmusic='play';
		} 	
	} else {
		//离开页面啦
		var audio = document.getElementById('audio-bg');
		audio.pause();
	}
}

document.addEventListener(visibilityChangeEvent, onVisibilityChange);
//解决微信无法播放音乐问题
document.addEventListener('WeixinJSBridgeReady', function() {
	audioDiv.load();
	audioDiv.play();
	sessionStorage.bgmusic='play';
}, false);
document.addEventListener('YixinJSBridgeReady', function() {
	audioDiv.load();
	$('#audio-bg')[0].play();
	sessionStorage.bgmusic='play';
}, false);
audioDiv.onended = function() {
    audioDiv.load();
    audioDiv.play();
    sessionStorage.bgmusic='play';
}
$(document).ready(function() {
	initClick();
});
