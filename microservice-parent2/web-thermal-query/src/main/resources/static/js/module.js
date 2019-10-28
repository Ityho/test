/*微热点(微舆情)基于jquery的扩展模块，整合项目中常用的基础js效果*/
/*1. panel面板效果：刷新、全屏缩放、内容显示隐藏*/

/*表格头部固定JS start*/
(function($) {
	$.fn
			.extend({
				FixedHead : function(options) {
					var op = $.extend({
						tableLayout : "auto"
					}, options);
					return this
							.each(function() {
								var $this = $(this); // 指向当前的table
								var $thisParentDiv = $(this).parent(); // 指向当前table的父级DIV，这个DIV要自己手动加上去
								$thisParentDiv.wrap(
										"<div class='fixedtablewrap'></div>")
										.parent().css({
											"position" : "relative"
										}); // 在当前table的父级DIV上，再加一个DIV
								var x = $thisParentDiv.position();

								var fixedDiv = $(
										"<div class='fixedheadwrap' style='clear:both;overflow:hidden;z-index:2;position:absolute;' ></div>")
										.insertBefore($thisParentDiv)// 在当前table的父级DIV的前面加一个DIV，此DIV用来包装tabelr的表头
										.css({
											"width" : "100%",
											"left" : x.left,
											"top" : x.top
										});

								var $thisClone = $this.clone(true);
								$thisClone.find("tbody").remove(); // 复制一份table，并将tbody中的内容删除，这样就仅余thead，所以要求表格的表头要放在thead中
								$thisClone.appendTo(fixedDiv); // 将表头添加到fixedDiv中

								$this.css({
									"marginTop" : 0,
									"table-layout" : op.tableLayout
								});
								// 当前TABLE的父级DIV有水平滚动条，并水平滚动时，同时滚动包装thead的DIV
								$thisParentDiv
										.scroll(function() {
											fixedDiv[0].scrollLeft = $(this)[0].scrollLeft;
										});

								// 因为固定后的表头与原来的表格分离开了，难免会有一些宽度问题
								// 下面的代码是将原来表格中每一个TD的宽度赋给新的固定表头
								var $fixHeadTrs = $thisClone.find("thead tr");
								var $orginalHeadTrs = $this.find("thead");
								$fixHeadTrs
										.each(function(indexTr) {
											var $curFixTds = $(this).find("td");
											var $curOrgTr = $orginalHeadTrs
													.find("tr:eq(" + indexTr
															+ ")");
											$curFixTds
													.each(function(indexTd) {
														$(this)
																.css(
																		"width",
																		$curOrgTr
																				.find(
																						"td:eq("
																								+ indexTd
																								+ ")")
																				.width());
													});
										});
							});
				}
			});
})(jQuery);
/* 加载动画效果 */
$.fn.loader = function(action, spinner) {
	var action = action || 'show';
	if (action === 'show') {
		if (this.find('.loader').length == 0) {
			parent_position = this.css('position');
			this.css('position', 'relative');
			paddingTop = parseInt(this.css('padding-top'));
			paddingRight = parseInt(this.css('padding-right'));
			paddingBottom = parseInt(this.css('padding-bottom'));
			paddingLeft = parseInt(this.css('padding-left'));
			width = this.innerWidth();
			height = this.innerHeight();

			$loader = $('<div class="loader"></div>').css({
				'position' : 'absolute',
				'top' : 0,
				'left' : 0,
				'width' : '100%',
				'height' : '100%',
				'z-index' : 30,
				'background-color' : 'rgba(255,255,255,0.7)',
				'border-radius' : '3px'
			});

			$loader.attr('parent_position', parent_position);

			$spinner = $(spinner).css({
				'position' : 'absolute',
				'top' : '50%',
				'left' : '50%',
				'color' : '#000',
				'margin-top' : '-' + paddingTop + 'px',
				'margin-right' : '-' + paddingRight + 'px',
				'margin-bottom' : '-' + paddingBottom + 'px',
				'margin-left' : '-' + paddingLeft + 'px'
			});

			$loader.html($spinner);
			this.prepend($loader);
			marginTop = $spinner.height() / 2;
			marginLeft = +$spinner.width() / 2;
			$spinner.css({
				'margin-top' : '-' + marginTop + 'px',
				'margin-left' : '-' + marginLeft + 'px'
			});
		}
	} else if (action === 'hide') {
		this.css('position', this.find('.loader').attr('parent_position'));
		this.find('.loader').remove();
	}
};

$(function() {

	// 以下是panel面板标题栏右侧工具js效果
	// panel full全屏缩放
	var full = 1;
	$(" .panel .panel-heading").find(".tools a.full").on(
			"click",
			function() {
				if (full) {
					full--;
					$(this).parents(".panel").addClass("fullCon");
					$("body").addClass("o_hide");
					$(this).html("<i class='icon-nofull'></i>").attr(
							"data-original-title", "缩小");
					$(this).parents('.panel').find('.panel-body').resize();
				} else {
					full++;
					$(this).parents(".panel").removeClass("fullCon");

					$("body").removeClass("o_hide");
					$(this).html("<i class='icon-full'></i>").attr(
							"data-original-title", "全屏");
					$(this).parents('.panel').find('.panel-body').resize();
				}
				$(this).blur();
				var fullFunction = $(this).attr("fullFunction");
				var index = $(this).attr("full-index");
				var demo = $(this).attr("full-demo");
				if (fullFunction) {
					var backFun = window[fullFunction];
					backFun(full, index, demo);
				}
			});

	// panel collapse隐藏/展开内容
	$('.panel-collapse').on(
			'show.bs.collapse',
			function() {
				$(this).parents(".panel").find(".tools a.collapse i").addClass(
						"icon-angle-down").removeClass("icon-angle-up").attr(
						"data-original-title", "收起");
			})
	$('.panel-collapse').on(
			'hide.bs.collapse',
			function() {
				$(this).parents(".panel").find(".tools a.collapse i").addClass(
						"icon-angle-up").removeClass("icon-angle-down").attr(
						"data-original-title", "展开");
			})

	$(".tools a.collapse").click(
			function() {
				if ($(this).attr("aria-expanded") == "true") {
					$(this).find("i").addClass("icon-angle-up").removeClass(
							"icon-angle-down")
							.attr("data-original-title", "展开");
				} else {
					$(this).find("i").addClass("icon-angle-down").removeClass(
							"icon-angle-up").attr("data-original-title", "收起");
				}
			})

	// panel 刷新内容
	// var loaders3 = '<i class="icon-reload-a fa-spin"></i>';
	// $(" .panel .panel-heading").find(".tools a.reload").on("click",
	// function() {
	// $(this).parents(".panel").find(".panel-body").loader('show',loaders3);
	// setTimeout(function(){
	// $(this).parents(".panel").find(".panel-body").loader('hide')
	// },3000)
	// });

	// 标题栏说明文字显示隐藏
	$(".titlBar .icon-help").on("click", function() {
		$(this).parents(".titlBar").next(".tipinfo").toggle(300)
	});
	$(".tipinfo .close").on("click", function() {
		$(this).parents(".tipinfo").hide(300)
	});

	// 单选复选样式美化js
	/*
	 * $('.icheckbox-list input').on('ifCreated ifClicked ifChanged ifChecked
	 * ifUnchecked ifDisabled ifEnabled ifDestroyed', function(event){
	 * }).iCheck({ checkboxClass: 'icheckbox_square-orange', radioClass:
	 * 'iradio_square-orange', increaseArea: '20%' });
	 */

	// 小屏幕左侧导航显示隐藏效果
	$(" .md-sidebar-box .btn").on("click", function(e) {
		$(this).parents(".md-sidebar").toggleClass("open");
		e.stopPropagation();
	});
	$(".page-sidebar-tree").on("click", function(e) {
		e.stopPropagation();
	})
	$("body").on("click", function() {
		$(".md-sidebar").removeClass("open");
	});

});
