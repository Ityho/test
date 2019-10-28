jQuery.fn.goToTop = function(settings) {
    settings = jQuery.extend({
        min: 1,  //设置距离顶部的最小值为1
        fadeSpeed: 200,  //设置一个淡出淡入的速度
        ieOffset: 50  //处理IE的兼容问题
    },settings);
    return this.each(function(){
        //listen for scroll
        var el = $(this);
        el.css("display","none");//in case the user forgot
        $(window).scroll(function(){
            //stupid IE hack
            if(!jQuery.support.hrefNormalized) {  //设置这个按钮的css属性
                el.css({
                    "position": "absolute",
                    "top" : $(window).scrollTop() + $(window).height() - settings.ieOffset
                });
            }


            if($(window).scrollTop() >= settings.min) {
                el.fadeIn(settings.fadeSpeed);
            } else {
                el.fadeOut(settings.fadeSpeed);
            }
        });
    });
};
