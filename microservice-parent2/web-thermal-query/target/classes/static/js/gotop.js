$(function() {
	parent.$(window).scroll(function() {
		var vtop = $(document).scrollTop(); // 滚动条到浏览器窗口顶部的距离
			var pWidth = $(document.body).width();
			var pHeight = $(window).height();
			alert(vtop);
			if (vtop >= 1) { // 滚动到大于等于1px的时候
				$("#pgtoppng").css( {
					"display" : "block"
				});
			} else {
				$("#pgtoppng").css( {
					"display" : "none"
				});
			}
		});

	$("#pgtopIMG").mouseover(function() {
		$(this).attr( {
			"src" : "images/pgtop2.png"
		});
	});

	$("#pgtopIMG").mouseout(function() {
		$(this).attr( {
			"src" : "images/pgtop.png"
		});
	});

});