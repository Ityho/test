$(function() {
	parent.$(window).scroll(function() {
		var vtop = $(document).scrollTop(); // ����������������ڶ����ľ���
			var pWidth = $(document.body).width();
			var pHeight = $(window).height();
			alert(vtop);
			if (vtop >= 1) { // ���������ڵ���1px��ʱ��
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