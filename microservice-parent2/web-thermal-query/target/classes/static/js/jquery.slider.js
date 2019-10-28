/**
 * Created by sky on 2015/6/18.
 */
(function($){
    // our plugin constructor
    var Slider = function(elem,option){
        this.elem = elem,
        this.$elem = $(elem),
        this.$sliderGroup = $(elem).find(".my-slider-group"),
        this.$sliderItem =  $(elem).find(".my-slider-item"),
        this.$sliderLeft = $(".my-slider-left"),
        this.$sliderRight = $(".my-slider-right"),
        this.spike = 10,
        this.sumSpike = 0,
        this.w = 0;
        $.extend(this,option);
    };
    // the plugin prototype
    Slider.prototype = {
        init:function(){
            var tg = this,w=0;
            tg.$sliderItem.each(function(i,item){
                w = w+(tg.$sliderItem[i].offsetWidth+parseInt(tg.$sliderItem.css("marginRight"))+parseInt(tg.$sliderItem.css("marginLeft")));
            });
            tg.$sliderGroup.css("width",w+"px"),
            //tg.$sliderItem.css({width:tg.$sliderItem.width()-parseInt(tg.$sliderItem.css("padding"))*2}),
            tg.$sliderLeft.on("click",  $.proxy(tg.handleClickLeft,tg)),
            tg.$sliderRight.on("click", $.proxy(tg.handleClickRight,tg));
            if(tg.$sliderGroup.width()<tg.$elem.width()){
                tg.$sliderLeft.hide();
                tg.$sliderRight.hide();
            }
        },
        handleClickLeft:function(){
            this.sumSpike -= this.spike;
            if(this.sumSpike<this.$elem.width()-this.$sliderGroup.width()){
                this.sumSpike = this.$elem.width()-this.$sliderGroup.width();
            }
            if(this.sumSpike>=0){
                this.sumSpike = 0;
            }
            this.$sliderGroup.css("marginLeft",this.sumSpike+"px");
        },
        handleClickRight:function(){
            this.sumSpike += this.spike;
            if(this.sumSpike>0){
                this.sumSpike = 0;
            }
            this.$sliderGroup.css("marginLeft",this.sumSpike+"px");
        }
    }

    $.fn.slider = function(option){
        return this.each(function(){
            new Slider(this,option).init();
        })
    }
}($))