(function(window, $, undefined) {
    var FALSE = false,
    NULL = null,
    toInt = parseInt,
    km = window.km || {};

    var templates = {
        'attachmentShow': $('#attachment-show-tmpl').template(),
        'attachmentThumb': $('#attachment-thumb-tmpl').template(),
        'imageBox': $('#image-box-tmpl').template()
    },   canvas_enabled = !!document.createElement('canvas').getContext;

	
    km.attachmentShow = function (data, define) {
        var items, rt = data.rt, meta = rt ? rt : data;
       if (define === 'main' && rt) return '';

        if (meta.pic_urls && meta.pic_urls.length) items = meta.pic_urls;
        else if (meta.tp) items = [{
            'op': meta.op,
            'mp': meta.mp,
            'tp': meta.tp
        }];

        return items ? $.tmpl(templates.attachmentThumb, {'items': items})[0].outerHTML
                     : ''; 
    };

    var imgCtrler = function() {
        this.cid = 'imageCtrler';
        this.canvas = NULL;
        this.maxWidth = 440;
        this.width = 0;
        this.height = 0;
        this.curAngle = 0;
    };

    imgCtrler.prototype = {
        init: function(data) {
            var _el = data.el;

            this.width = _el.offsetWidth;
            this.height = _el.offsetHeight;

            if (canvas_enabled) {
                this.canvas = $('<canvas>').attr({
                    'height': this.height,
                    'width': this.width
                }).addClass('narrow-move').insertBefore(_el)[0];
                
                $(_el).hide();
                var ctx = this.canvas.getContext('2d');
                //ctx.drawImage(_el,0,0);
            } else {
                var _matrix = 'DXImageTransform.Microsoft.Matrix';
                _el.style.filter = 'progid:DXImageTransform.Microsoft.Matrix()';
                _el.filters.item(_matrix).SizingMethod = "auto expand";
                $(_el).addClass('narrow-move');
                _matrix = NULL;
            }

            this.element = _el;
        },
        rotate: function(dir) {
            if (!this.element) {
                return;
            }

            var _angle, drawW, drawH, h = this.width, w = this.height;
            if (dir === 'right') {
                _angle = this.curAngle + 90;
                this.curAngle = _angle >= 360 ? 0: _angle;
            } else if (dir === 'left') {
                _angle = this.curAngle - 90;
                this.curAngle = _angle < 0 ? 360 + _angle: _angle;
            } else {
                w = this.width;
                h = this.height;
            }
            _angle = NULL;

            this.width = w;
            this.height = h;

            if (w > this.maxWidth) {
                h = toInt(this.maxWidth * h / w);
                w = this.maxWidth;
            }
            if (this.canvas) {
                var ctx = this.canvas.getContext('2d'),
                el = this.element,
                cpx = 0,
                cpy = 0;
                $(this.canvas).attr({
                    'width': w,
                    'height': h
                });
                ctx.clearRect(0, 0, w, h);
                switch (this.curAngle) {
                case 0:
                    cpx = 0;
                    cpy = 0;
                    drawW = w;
                    drawH = h;
                    break;
                case 90:
                    cpx = w;
                    cpy = 0;
                    drawW = h;
                    drawH = w;
                    break;
                case 180:
                    cpx = w;
                    cpy = h;
                    drawW = w;
                    drawH = h;
                    break;
                case 270:
                    cpx = 0;
                    cpy = h;
                    drawW = h;
                    drawH = w;
                    break;
                }
                ctx.save();
                ctx.translate(cpx, cpy);
                ctx.rotate(this.curAngle * Math.PI / 180);
                ctx.drawImage(el, 0, 0, drawW, drawH);
                ctx.restore();
            } else {
                var _rad = this.curAngle * Math.PI / 180,
                _cos = Math.cos(_rad),
                _sin = Math.sin(_rad),
                _el = this.element,
                _matrix = 'DXImageTransform.Microsoft.Matrix';

                _el.filters.item(_matrix).M11 = _cos;
                _el.filters.item(_matrix).M12 = - _sin;
                _el.filters.item(_matrix).M21 = _sin;
                _el.filters.item(_matrix).M22 = _cos;

                switch (this.curAngle) {
                case 0:
                case 180:
                    drawW = w;
                    drawH = h;
                    break;
                case 90:
                case 270:
                    drawW = h;
                    drawH = w;
                    break;
                }
                _el.width = drawW;
                _el.height = drawH;
                var _parent = _el.parentNode;
                _parent.style.height = h;
                _parent.style.display = "block";
            }
        }
    };

    $.fn.imgRotate = function(dir) {
        this.each(function() {
            if (this.tagName !== 'IMG') {
                return FALSE;
            }
            var img = $(this).data('img');
            if (!img) {
                img = new imgCtrler();
                img.init({
                    el: this
                });

                $(this).data('img', img);
            }
            // @tofishes parent()改为.closest('div')，parent取值错误 2013.11.6
            img.maxWidth = $(this).closest('div').width();
            img.rotate(dir);
        });
        return this;
    };
    function multiHandler($thumb, $thumbWrap, $zoomWrap) {
        $thumb.addClass('active'); 
        var $items = $thumbWrap.children().clone(),
            $slider = $zoomWrap.find('.thumb-slider-wrap ul');

        $thumb.removeClass('active');
        $items.children().removeClass('thumb-zoom zoom-move');
        $slider.html($items);
    }

    function slider($ul, index) {
        var $li = $ul.children(),
            width = $li.outerWidth(true),
            size = $li.length,
            maxWidth = width * size;
        var visibleCount = 7, half = 3, 
            visibleWidth = width * visibleCount;

        $ul.width(maxWidth);

        if (maxWidth < visibleWidth) return;

        var offset = (index - half) * width;
        offset = Math.max(0, offset); 
        offset = Math.min(maxWidth - visibleWidth, offset); 

        $ul.animate({
            'left': 0 - offset 
        }, 300);
    }

    $(document).on('click.img-zoom', 'img.thumb-zoom', function(e) {
        var $thumb = $(this), $thumbWrap = $thumb.closest('.feed-img');

        if ($thumbWrap.isLocked()) {
            return;
        };

        var loadingClass = 'zoom-loading', isMulti = $thumbWrap.hasClass('multi-attachment');
        var $wrapper = $thumbWrap.closest('.preview-img');

        $thumbWrap.lock();

        var $zoomWrap = $thumbWrap.siblings('.zoom-wrap');
        if (! $zoomWrap.length) {
            $zoomWrap = $('<div class="zoom-wrap show-img"/>').hide();
            $thumbWrap.before($zoomWrap);
        }
      
        $thumb.addClass(loadingClass).after('<div class="loading-img" />');
        var data_images = $thumb.data('images'), zoom_img = data_images.zoom_img;
        
       
        
        var image = new Image();
        image.onload = function() {
            $thumb.removeClass(loadingClass).siblings('.loading-img').remove();
            $thumbWrap.unLock().hide();
			
            data_images.isMulti = isMulti;
            data_images.index = $thumb.data('index');
            data_images.count = $wrapper.data('count');
			
			
            $zoomWrap.html($.tmpl(templates.attachmentShow, data_images)).show();
            if (isMulti) multiHandler($thumb, $thumbWrap, $zoomWrap);
            slider($zoomWrap.find('.thumb-slider-wrap ul'), data_images.index);
        };
        image.src = zoom_img;

        var scrollElement = window.MultiPanel && $wrapper.closest(MultiPanel.$scrollHook).length ? MultiPanel.$scrollHook : $(window);
        $wrapper.data('go-origin-scroll', {'scrollElement': scrollElement, 'top': scrollElement.scrollTop()});

    }).on('click.img-zoom', '.image-box a', function(e) {
        var $this = $(this), action = $this.data('action');
        var $imageBox = $this.closest('.image-box'),
            $zoomWrap = $imageBox.closest('.zoom-wrap'),
            $wrapper = $zoomWrap.closest('.preview-img');

        var actions = {
            'piup': function() {
                var $thumbWrap = $zoomWrap.siblings('.feed-img');
                $zoomWrap.hide();
                $thumbWrap.show();
                var originScroll = $wrapper.data('go-origin-scroll');
                if ($(window).scrollTop() > $wrapper.offset().top && originScroll) {
                    originScroll.scrollElement.scrollTop(originScroll.top);
                };
            },
            'left': function() {
                $imageBox.find('img').imgRotate('left');
            },
            'right': function() {
                $imageBox.find('img').imgRotate('right');
            }
        };

        actions[action] && actions[action]();
    }).on('click', '.thumb-slider-wrap img', function () {
        var $this = $(this), $sliderWrap, $imageBox, data, $loading, $wrapper;
        if ($this.hasClass('active')) return;

        $sliderWrap = $this.closest('.thumb-slider-wrap');
        $sliderWrap.find('.active').removeClass('active');
        $this.addClass('active');

        $imageBox = $sliderWrap.siblings('.image-box');
        data = $this.data('images');

        $loading = $imageBox.find('.loading-img').show();

        $wrapper = $imageBox.closest('.preview-img');

        var timestamp = + new Date();
        $wrapper.data('timestamp', timestamp);
        var image = new Image();
        image.onload = function () {
            if (timestamp != $wrapper.data('timestamp')) return;

            data.index = $this.data('index');
            data.count = $wrapper.data('count');

            slider($sliderWrap.find('ul'), data.index);

            $imageBox.html($.tmpl(templates.imageBox, data));   
            $loading.hide();

            var originScroll = $wrapper.data('go-origin-scroll');
            if ($(window).scrollTop() > $wrapper.offset().top && originScroll) {
                originScroll.scrollElement.scrollTop(originScroll.top);
            };
        };
        image.src = data.zoom_img;
    }).on('click', '.pic-nav', function () {
        var $this = $(this), index = $this.data('index'),
            $imageBox = $this.closest('.image-box'),
            $thumbWrap = $imageBox.siblings('.thumb-slider-wrap');

        $thumbWrap.find('li img').eq(index).trigger('click');
    }).on('click', '.slider-dir', function () {
        var $this = $(this);

        if ($this.hasClass('disabled')) return;

        var $other = $this.siblings('.slider-dir'),
            isPrev = $this.hasClass('slider-prev');

        var $ul = $this.parent().find('ul'), $li = $ul.children(),
            size = $li.length, index = isPrev ? 0 : size;

        slider($ul, index);
        $this.addClass('disabled');
        $other.removeClass('disabled');
    });
    
    
    $.fn.extend({
    	'isLocked': function() {
    		return $(this).data('jquery-fn-locked');
    	},
    	'lock': function() {
    		return $(this).data('jquery-fn-locked', true);
    	},
    	'unLock': function() {
    		return $(this).data('jquery-fn-locked', false);
    	}
    });
    
})(window, jQuery);