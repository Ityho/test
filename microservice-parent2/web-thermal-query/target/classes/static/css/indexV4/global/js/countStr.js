(function($) {
	var methods = {
		init: function(options) {
			var settings = $.extend({
				length: '',
				child: 'sizeNum',
				input: 'input'
			}, options);
			function getLength(str) {
				var iLength = 0;
				for(var i = 0; i < str.length; i++) {
					iLength += 1
				}
				return iLength;
			}
			function cutStr(str, len) {
				var curStr = "";
				for(var i = 0; i < str.length; i++) {
					curStr += str.charAt(i);
					if(getLength(curStr) > len) {
						str = str.substring(0, i);
						return str;
					}
				}
				return str;
			}
			return this.each(function() {
				var text = $(this).find(settings.input);
				var sizeNum = $(this).find(settings.child);
				if(text.selector == 'textarea'){
					$(this).data('countStr', {
						child: settings.child,
						cont: sizeNum.html(),
						input: settings.input
					});
					sizeNum.html('0' + settings.length + '×Ö');
				}else {
					$(this).data('countStr', {
						child: settings.child,
						cont: sizeNum.html(),
						input: settings.input
					});
					sizeNum.html('0/' + settings.length + '×Ö');
				}
				$(this).removeData();
				var set = function() {
					setTimeout(function(args) {
						if(getLength(text.val()) > settings.length) {
							if(settings.length) {
								text.val(cutStr(text.val(), settings.length));
							}
							if(sizeNum.length) {
								sizeNum.html(settings.length + '/' + settings.length + '×Ö');
							}
						}
						if(text.selector == 'textarea'){
							if(sizeNum.length) {
								sizeNum.html(getLength(text.val())  + settings.length + '×Ö');
							}
						}else{
							if(sizeNum.length) {
								sizeNum.html(getLength(text.val())+ '/'  + settings.length + '×Ö');
							}
						}
					}, 20)
				}
				text.on('keyup.countStr', set);
				text.on('paste.countStr', set);
				text.on('change.countStr', set);
			});
		},
		remove: function() {
			return this.each(function() {
				var data = $(this).data('countStr') || '';
				if(data) {
					var text = $(this).find(data.input);
					text.off('keyup.countStr paste.countStr change.countStr');
					if(data.child) {
						$(this).find(data.child).html(data.cont);
					}
				}
			});
		}
	};
	$.fn.countStr = function(method) {
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method' + method + 'does not exist on jQuery.countStr');
		}
	};
})(jQuery);
