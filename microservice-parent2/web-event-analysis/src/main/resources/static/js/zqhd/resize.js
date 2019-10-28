var origW = 480,
	origH = 800;

function resizeArea() {
	var a = $(window).innerWidth();
	var b = a / origW * origH;
	if (b > $(window).innerHeight()) {
		b = $(window).innerHeight();
		a = b / origH * origW
	}
	if (b > a && b < $(window).innerHeight()) {
		b = $(window).innerHeight()
	}
	$("#container").css("width", a + "px");
	$("#container").css("height", b + "px");
	$("#container").css("margin-left", (($(window).innerWidth() - a) / 2) + "px");
	$("#container").css("margin-top", (($(window).innerHeight() - b) / 2) + "px");
	calPosition()
}
function calPosition() {
	var f = $("#container");
	var windwoHeight = $(window).height();
	var windowWidth = $(window).width();
	var d = parseInt(f.css("margin-left").split("px")[0]),
		h = parseInt(f.css("margin-top").split("px")[0]),
		e = f.width(),
		c = f.height();
	var g = e / 480;
	var b = null;
	b = seek("c1");
	b.rect.w = e;
	b.rect.h = c;
	b.rect.l = d;
	b.rect.t = 0;
	b = seek("c2");
	b.rect.w = e / 480 * 299;
	b.rect.h = e / 480 * 80;
	b.rect.l = Math.round((e - b.rect.w) / 2) + d;
	b.rect.t = Math.round(c - 160 * g);
	
	b = seek("c4");
	b.rect.w = e;
	b.rect.h = c;
	b.rect.l = d;
	b.rect.t = Math.round((c - b.rect.h) / 2);
	b = seek("c13");
	b.rect.w = g * 149;
	b.rect.h = g * 51;
	b.rect.l = g * 11 + d;
	b.rect.t = g * 16;
	b = seek("c14");
	b.rect.w = g * 42;
	b.rect.h = g * 29;
	b.rect.l = g * 197 + d;
	b.rect.t = g * 19;
	$("#c14_").css("left", b.rect.l + b.rect.w + 8 + "px");
	$("#c14_").css("top", b.rect.t + "px");
	$("#c14_").css("line-height", g * 29 + "px");
	$("#c14_").css("font-size", g * 24 + "px");
	b = seek("c16");
	b.rect.w = g * 119;
	b.rect.h = g * 25;
	b.rect.l = g * 322 + d;
	b.rect.t = g * 24;
	b = seek("c17");
	b.rect.w = g * 106;
	b.rect.h = g * 13;
	b.rect.l = g * 329 + d;
	b.rect.t = g * 30;
	b = seek("c21");
	b.rect.w = e;
	b.rect.h = g * 477*0.796;
	b.rect.l = 0;
	b.rect.t = (c - b.rect.h - g * 50)*1.51*0.768;
	b = seek("c25");
	b.rect.w = g * 329/0.813;
	b.rect.h = g * 382/0.772;
	b.rect.l = (g * 78 + d)*0.817;
	b.rect.t = c*0.48 - (800 - 82) * g*0.48;
	b = seek("c26");
	b.rect.w = g * 329/0.813;
	b.rect.h = g * 382/0.772;
	b.rect.l = (g * 78 + d)*0.817;
	b.rect.t = c*0.48 - (800 - 82) * g*0.48;
	b = seek("c27");
	b.rect.w = g * 329/0.813;
	b.rect.h = g * 382/0.772;
	b.rect.l = (g * 78 + d)*0.817;
	b.rect.t = c*0.48 - (800 - 82) * g*0.48;
	b = seek("c28");
	b.rect.w = g * 329/0.813;
	b.rect.h = g * 382/0.772;
	b.rect.l = (g * 78 + d)*0.817;
	b.rect.t = c*0.48 - (800 - 82) * g*0.48;
	b = seek("c5");
	b.rect.w = e;
	b.rect.h = g * 114;
	b.rect.l = 0 + d;
	b.rect.t = c - b.rect.h;
	b = seek("c6");
	b.rect.w = g * 330;
	b.rect.h = g * 210;
	b.rect.l = g * 80 + d;
	b.rect.t = c - (800 - 550) * g;
	b = seek("c7");
	b.rect.w = g * 330;
	b.rect.h = g * 210*0.691;
	b.rect.l = g * 80 + d;
	b.rect.t = (c - (800 - 550) * g)*1.091;
	b = seek("c8");
	b.rect.w = g * 330;
	b.rect.h = g * 210*0.503;
	b.rect.l = g * 80 + d;
	b.rect.t = (c - (800 - 550) * g)*1.125;
	b = seek("c9");
	b.rect.w = g * 330;
	b.rect.h = g * 210;
	b.rect.l = g * 80 + d;
	b.rect.t = c - (800 - 550) * g;
	b = seek("c29");
	b.rect.w = g * 244;
	b.rect.h = g * 72;
	b.rect.l = g * 115 + d;
	b.rect.t = c - (800 - 690) * g;
	b = seek("c30");
	b.rect.w = g * 419;
	b.rect.h = g * 624;
	b.rect.l = g * 60 + d;
	b.rect.t = 0;
	b = seek("c31");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = 0 + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c32");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = 0 + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c33");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = 0 + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c34");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = 0 + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c35");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = 0 + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c31m");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = e - b.rect.w + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c32m");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = e - b.rect.w + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c33m");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = e - b.rect.w + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c34m");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = e - b.rect.w + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c35m");
	b.rect.w = g * 235;
	b.rect.h = g * 342;
	b.rect.l = e - b.rect.w + d;
	b.rect.t = c - (800 - 720) * g - b.rect.h;
	b = seek("c22");
	b.rect.w = g * 480;
	b.rect.h = g * 648*1.13;
	b.rect.l = 0 + d;
	b.rect.t = (c - (800 - 44) * g)*0.342;
	b = seek("c23");
	b.rect.w = g * 480;
	b.rect.h = g * 648*1.13;
	b.rect.l = 0 + d;
	b.rect.t = (c - (800 - 44) * g)*0.342;
	b = seek("c24");
	b.rect.w = g * 480;
	b.rect.h = g * 648*1.13;
	b.rect.l = 0 + d;
	b.rect.t = (c - (800 - 44) * g)*0.342;

	
	b = seek("c12");
	b.rect.w = windowWidth
	b.rect.h = windwoHeight;
	b.rect.l = 0 + d;
	b.rect.t = 0;
	b = seek("c20");
	b.rect.w = Math.round(e * 0.9) - g * 80 * 2;
	b.rect.h = (Math.round((e * 0.9) / 470 * 306) - g * 60 * 2)*0.602;
	b.rect.l = Math.round(e * 0.05) + d;
	b.rect.t = (Math.round((c - b.rect.h) / 2) - g * 60)*0.882*0.83*0.724;
	$("#c20").css("font-size", g * 26 + "px");
	var a = b.rect.t + b.rect.t;
	b = seek("c19");
	b.rect.w = g * 170;
	b.rect.h = g * 81*0.746;
	b.rect.l = g * 53 + d;
	b.rect.t = (a + g * 50)*1.147*1.293;
	b = seek("c18");
	b.rect.w = g * 166;
	b.rect.h = g * 81*0.746;
	b.rect.l = g * 264 + d;
	b.rect.t = (a + g * 50)*1.147*1.293;
	b = seek("c10");
	b.rect.w = g * 450;
	b.rect.h = g * 236;
	b.rect.l = Math.round((e - b.rect.w) / 2) + d;
	b.rect.t = (800 - 305) * g;
	$("#c20").css("padding-left", g * 80 + "px");
	$("#c20").css("padding-right", g * 80 + "px");
	$("#c20").css("padding-top", g * 60*2.87*1.26 + "px");
	$("#c20").css("padding-bottom", g * 60 + "px");
	items.forEach(function(j) {
		$("#" + j.name).css("top", j.rect.t + "px");
		$("#" + j.name).css("left", j.rect.l + "px");
		$("#" + j.name).css("width", j.rect.w + "px");
		$("#" + j.name).css("height", j.rect.h + "px")
	})
}
function seek(a) {
	for (i = 0; i < items.length; i++) {
		if (items[i]["name"] == a) {
			return items[i]
		}
	}
	return null
}; 