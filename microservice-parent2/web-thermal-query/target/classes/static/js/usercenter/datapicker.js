/**
 * 完整代码
 */

// 关于月份： 在设置时要-1，使用时要+1

(function($, window, document, undefined) {

	var Calendar = function(elem, options) {
		this.$calendar = elem;

		this.defaults = {
			ifSwitch: true,
			hoverDate: false,
			backToday: false
		};

		this.opts = $.extend({}, this.defaults, options);
		
	};

	Calendar.prototype = {
		showCalendar: function() { // 输入数据并显示
			var self = this;
			var year = dateObj.getDate().getFullYear();
			var month = dateObj.getDate().getMonth() + 1;
			var dateStr = returnDateStr(dateObj.getDate());
			var firstDay = new Date(year, month - 1, 1); // 当前月的第一天

			this.$calendarTitle_text.text(year + '/' + dateStr.substr(4, 2));

			this.$calendarDate_item.each(function(i) {
				// allDay: 得到当前列表显示的所有天数
				var allDay = new Date(year, month - 1, i + 1 - firstDay.getDay());
				var allDay_str = returnDateStr(allDay);

				$(this).text(allDay.getDate()).attr('data', allDay_str);

				if(returnDateStr(firstDay).substr(0, 6) === allDay_str.substr(0, 6)) {
					$(this).attr('class', 'item item-curMonth');
				} else {
					$(this).attr('class', 'item');
				}
			});

			// 已选择的情况下，切换日期也不会改变
			if(self.selected_data) {
				var selected_elem = self.$calendar_date.find('[data=' + self.selected_data + ']');

				selected_elem.addClass('selected-bg');
			}
			getSignInLogs(year + "-" + (month <= 9 ? ('0' + month) : ('' + month)));
		},

		renderDOM: function() { // 渲染DOM
			this.$calendar_title = $('<div class="rili-tit"></div>');
			this.$calendar_week = $('<div class="rili-week clearfix"></div>');
			this.$calendar_date = $('<ul class="rili-data clearfix"></ul>');
			
			
			var _titleStr = '<span class="triangle-left"></span>' +
				'<a href="javascript:;" class="tit-time">' +
				
				'</a>'+
				'<span class="triangle-right"></span>';
			var _weekStr = '<ul>' +
				'<li class="item">日</li>' +
				'<li class="item">一</li>' +
				'<li class="item">二</li>' +
				'<li class="item">三</li>' +
				'<li class="item">四</li>' +
				'<li class="item">五</li>' +		
				'<li class="item">六</li>'+
				'</ul>';
			var _dateStr = '';

			for(var i = 0; i < 6; i++) {
				_dateStr += '<li class="item">'+
					'<span>26</span>'+
					'</li>' +
					'<li class="item">'+
					'<span>26</span>'+
					'</li>' +
					'<li class="item">'+
					'<span>26</span>'+
					'</li>' +
					'<li class="item">'+
					'<span>26</span>'+
					'</li>' +
					'<li class="item">'+
					'<span>26</span>'+
					'</li>' +
					'<li class="item">'+
					'<span>26</span>'+
					'</li>' +
					'<li class="item">'+
					'<span>26</span>'+
					'</li>';
			}

			this.$calendar_title.html(_titleStr);
			this.$calendar_week.html(_weekStr);
			this.$calendar_date.html(_dateStr);
			
			
			this.$calendar.append(this.$calendar_title, this.$calendar_week, this.$calendar_date, this.$calendar_today);
			this.$calendar.show();
		},

		inital: function() { // 初始化
			var self = this;

			this.renderDOM();

			this.$calendarTitle_text = this.$calendar_title.find('.tit-time');
			this.$arrow_prev = this.$calendar_title.find('.triangle-left');
			this.$arrow_next = this.$calendar_title.find('.triangle-right');
			this.$calendarDate_item = this.$calendar_date.find('span');

			this.selected_data = 0;

			this.showCalendar();

			if(this.opts.ifSwitch) {
				this.$arrow_prev.bind('click', function() {
					var _date = dateObj.getDate();

					dateObj.setDate(new Date(_date.getFullYear(), _date.getMonth() - 1, 1));

					self.showCalendar();
				});

				this.$arrow_next.bind('click', function() {
					var _date = dateObj.getDate();

					dateObj.setDate(new Date(_date.getFullYear(), _date.getMonth() + 1, 1));

					self.showCalendar();
				});
			}
			//点击
//			this.$calendarDate_item.click(function() {
//				var _dateStr = $(this).attr('data');
//				var _date = changingStr(addMark(_dateStr));
//				var $curClick = null;
//
//				self.selected_data = $(this).attr('data');
//
//				dateObj.setDate(new Date(_date.getFullYear(), _date.getMonth(), 1));
//
//				if(!$(this).hasClass('item-curMonth')) {
//					self.showCalendar();
//				}
//
//				$curClick = self.$calendar_date.find('[data=' + _dateStr + ']');
//				$curDay = self.$calendar_date.find('.item-curDay');
//				if(!$curClick.hasClass('selected-bg')) {
//					self.$calendarDate_item.removeClass('selected-bg');
//
//					$curClick.addClass('selected-bg');
//				}
//			});
		},

		constructor: Calendar
	};

	$.fn.calendar = function(options) {
		var calendar = new Calendar(this, options);

		return calendar.inital();
	};

	// ========== 使用到的方法 ==========

	var dateObj = (function() {
		var _date = new Date();

		return {
			getDate: function() {
				return _date;
			},

			setDate: function(date) {
				_date = date;
			}
		}
	})();

	function returnDateStr(date) { // 日期转字符串
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();

		month = month <= 9 ? ('0' + month) : ('' + month);
		day = day <= 9 ? ('0' + day) : ('' + day);

		return year + month + day;
	};

	function changingStr(fDate) { // 字符串转日期
		var fullDate = fDate.split("-");

		return new Date(fullDate[0], fullDate[1] - 1, fullDate[2]);
	};

	function addMark(dateStr) { // 给传进来的日期字符串加-
		return dateStr.substr(0, 4) + '-' + dateStr.substr(4, 2) + '-' + dateStr.substring(6);
	};

	// 条件1：年份必须要能被4整除
	// 条件2：年份不能是整百数
	// 条件3：年份是400的倍数
	function isLeapYear(year) { // 判断闰年
		return(year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
	}

})(jQuery, window, document);