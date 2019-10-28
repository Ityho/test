/**
 * Created by sky on 2015/6/16.
 */
(function($){
    $.fn.extend({
        selectedMenu:function(option){
            var option = option || {},defaults = {selected:$(".contenttext"),menu:$(".menu")};
            $.fn.extend(defaults,option);
            $(document).on('mouseup',function(){
                getSelectedText().length || defaults.menu.hide();
            });
            defaults.selected.each(function(){
                var $this = $(this),txt;
                $this.on("mouseup",function (e) {
                    var parentOffset = $this.offset();
                    txt = getSelectedText();
                    if (txt.length > 0) {
                        defaults.menu.css({'top':e.pageY-$(document).scrollTop(),'left':e.pageX-$(document).scrollLeft()}).show();
                    }
                });
            });
            //获取页面中选中的文字
            function getSelectedText(){
                if(window.getSelection){  //FF
                    return window.getSelection().toString();
                }else{ //IE
                    return document.selection.createRange().text;
                }
            }
        }
    })
}($));