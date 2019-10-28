$(function(){
	var idx = 0;
	var $header = $("#homepic");
	var $leftBtn = $(".banner_left_arrow");
	var $rightBtn = $(".banner_right_arrow");
	var $indicators = $(".banner_indicator li");
	var $qiehuan = $(".qiehuan li");
	var $bannerBgs = $("#homepic .banner_bg");
	var $bannerContents = $(".banner_content li");
	var $lunbo = $(".qiehuan li");

	var isRocketShown = false;
	var isRocketFly = false;

	var timer = null;

	var length = $indicators.length;
	var bgArray = ["images/img-lunbo1.jpg", "images/img-lunbo2.jpg", "images/img-lunbo3.jpg"];

	function next(){
		idx++;
		if(idx >= length){
			idx = 0;
		}
		change();
	}
	function prev(){
		idx--;
		if(idx < 0){
			idx = length - 1;
		}
		change();
	}

	function change(){
		
		stopScroll();
		$bannerBgs.fadeOut().eq(idx).fadeIn();

		$indicators.filter(".indicator").removeClass("indicator");
		$indicators.eq(idx).addClass("indicator");

		$bannerContents.filter(".active").fadeOut().removeClass("active");
		$bannerContents.eq(idx).fadeIn().addClass("active");
		
		$lunbo.filter(".ff").removeClass("ff");
		$lunbo.eq(idx).fadeIn().addClass("ff");

		beginScroll();
	}

	$leftBtn.on("click", function(event){
		prev();
	});
	$rightBtn.on("click", function(event){
		next();
	});
    $indicators.on("click", function(event){
         var index = $(this).index();
         if(index != idx){
            idx = index;
            change();
         }
    });
    $indicators.on("mouseover", function(event){
         var index = $(this).index();
         if(index != idx){
            idx = index;
            change();
         }
    });
    $qiehuan.on("click", function(event){
         var index = $(this).index();
         if(index != idx){
            idx = index;
            change();
         }
    });
    $qiehuan.on("mouseover", function(event){
         var index = $(this).index();
         if(index != idx){
            idx = index;
            change();
         }
    });

	function beginScroll(){
		timer = setInterval(function(){
			next();
		}, 5000);
	}
	function stopScroll(){
		if(timer != null){
			clearInterval(timer);
			timer = null;
		}
	}

	$("table.download").hover(function(){
		stopScroll();
	}, function(){
		beginScroll();
	});

	beginScroll();
});
