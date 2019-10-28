(function() {
	//提示框
	$('.wui-tooltips').on('click', function(e) {
		e.stopPropagation();
		$(this).parent().find('.wui-tooltips-body').show()
	})
	$(document).mouseup(function(e) {
		var _con = $('.wui-tooltips-body '); // 设置目标区域
		if(!_con.is(e.target) && _con.has(e.target).length === 0) {
			$('.wui-tooltips-body ').hide()
		}
	});
})()