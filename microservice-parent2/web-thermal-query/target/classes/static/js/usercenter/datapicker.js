/**
 * ��������
 */

// �����·ݣ� ������ʱҪ-1��ʹ��ʱҪ+1

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
		showCalendar: function() { // �������ݲ���ʾ
			var self = this;
			var year = dateObj.getDate().getFullYear();
			var month = dateObj.getDate().getMonth() + 1;
			var dateStr = returnDateStr(dateObj.getDate());
			var firstDay = new Date(year, month - 1, 1); // ��ǰ�µĵ�һ��

			this.$calendarTitle_text.text(year + '/' + dateStr.substr(4, 2));

			this.$calendarDate_item.each(function(i) {
				// allDay: �õ���ǰ�б���ʾ����������
				var allDay = new Date(year, month - 1, i + 1 - firstDay.getDay());
				var allDay_str = returnDateStr(allDay);

				$(this).text(allDay.getDate()).attr('data', allDay_str);

				if(returnDateStr(firstDay).substr(0, 6) === allDay_str.substr(0, 6)) {
					$(this).attr('class', 'item item-curMonth');
				} else {
					$(this).attr('class', 'item');
				}
			});

			// ��ѡ�������£��л�����Ҳ����ı�
			if(self.selected_data) {
				var selected_elem = self.$calendar_date.find('[data=' + self.selected_data + ']');

				selected_elem.addClass('selected-bg');
			}
			getSignInLogs(year + "-" + (month <= 9 ? ('0' + month) : ('' + month)));
		},

		renderDOM: function() { // ��ȾDOM
			this.$calendar_title = $('<div class="rili-tit"></div>');
			this.$calendar_week = $('<div class="rili-week clearfix"></div>');
			this.$calendar_date = $('<ul class="rili-data clearfix"></ul>');
			
			
			var _titleStr = '<span class="triangle-left"></span>' +
				'<a href="javascript:;" class="tit-time">' +
				
				'</a>'+
				'<span class="triangle-right"></span>';
			var _weekStr = '<ul>' +
				'<li class="item">��</li>' +
				'<li class="item">һ</li>' +
				'<li class="item">��</li>' +
				'<li class="item">��</li>' +
				'<li class="item">��</li>' +
				'<li class="item">��</li>' +		
				'<li class="item">��</li>'+
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

		inital: function() { // ��ʼ��
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
			//���
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

	// ========== ʹ�õ��ķ��� ==========

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

	function returnDateStr(date) { // ����ת�ַ���
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();

		month = month <= 9 ? ('0' + month) : ('' + month);
		day = day <= 9 ? ('0' + day) : ('' + day);

		return year + month + day;
	};

	function changingStr(fDate) { // �ַ���ת����
		var fullDate = fDate.split("-");

		return new Date(fullDate[0], fullDate[1] - 1, fullDate[2]);
	};

	function addMark(dateStr) { // ���������������ַ�����-
		return dateStr.substr(0, 4) + '-' + dateStr.substr(4, 2) + '-' + dateStr.substring(6);
	};

	// ����1����ݱ���Ҫ�ܱ�4����
	// ����2����ݲ�����������
	// ����3�������400�ı���
	function isLeapYear(year) { // �ж�����
		return(year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
	}

})(jQuery, window, document);