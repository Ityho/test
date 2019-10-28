window.onload = function() {
   
    var swiper = new Swiper(".swiper-container", {
				speed: 500,
				direction: "vertical",
				pagination: ".swiper-pagination",
				mousewheelControl: !0,
				onInit: function(a) {
					a.myactive = 0;
					for(var b = 0; b < a.slides.length; b++)
						a.slides[b].style.zIndex = 0;
					a.slides[a.myactive].style.zIndex = 1,
						swiperAnimateCache(a),
						swiperAnimate(a)
				},
				onTransitionStart: function(a) {
					a.params.onlyExternal = !0
				},
				onTransitionEnd: function(a) {
					a.params.onlyExternal = !1,
						a.myactive = a.activeIndex;
					for(var b = 0; b < a.slides.length; b++)
						a.slides[b].style.zIndex = 0;
					a.slides[a.myactive].style.zIndex = 1,
						swiperAnimate(a)
				},
				watchSlidesProgress: !0,
				onProgress: function(a) {
					var b, c, d, e;
					for(a.myactive || (a.myactive = 0),
						b = 0; b < a.slides.length; b++)
						c = a.slides[b],
						es = c.style,
						d = c.progress,
						e = d * a.height,
						opacity = 0,
						b == a.myactive && (opacity = 1 - Math.abs(d) / 2),
						(a.slides[a.myactive].progress > 0 && b == a.myactive + 1 || a.slides[a.myactive].progress < 0 && b == a.myactive - 1) && (opacity = .5 + Math.abs(.5 * a.slides[a.myactive].progress)),
						c.style.opacity = opacity,
						b != a.myactive && (es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = "translate3d(0," + e + "px,0)")
				},
				onSetTransition: function(a, b) {
					for(var c = 0; c < a.slides.length; c++)
						es = a.slides[c].style,
						es.webkitTransitionDuration = es.MsTransitionDuration = es.msTransitionDuration = es.MozTransitionDuration = es.OTransitionDuration = es.transitionDuration = b + "ms"
				}
			})
			$("#btn-sign").click(function() {
				swiper.slideNext();
			})
    
}
;
