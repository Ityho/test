$(function(){
    /**
     * ģ��select
     **/
    $(".w-select .w-select-input").on('click',function () {
        $(this).parent().find(".w-select-option").slideUp(300);
        if($(this).parent().find(".w-select-option").is(":hidden")){
            $(this).parent().addClass("w-select-open");
            $(this).parent().find(".w-select-option").slideDown(300);
            var evt =  new Object;
            if ( typeof(window.event) == "undefined" ){//����ǻ�������
                evt = arguments.callee.caller.arguments[0];
            }else{
                evt = event || window.event;
            }
            evt.cancelBubble = true;
        }else{
            $(this).parent().removeClass("w-select-open");
            $(this).parent().find(".w-select-option").slideUp(300);
            var evt =  new Object;
            if ( typeof(window.event) == "undefined" ){//����ǻ�������
                evt = arguments.callee.caller.arguments[0];
            }else{
                evt = event || window.event;
            }
            evt.cancelBubble = true;
        }
    });
    $(document).on('click',function () {
        $(".w-select-input").parent().removeClass("w-select-open");
        $(".w-select-option").slideUp(300);
    });
    $(".w-select-option li a").on("click",function(){
        $(this).parent().parent().parent().find(".w-select-input").val($(this).text());
    });

});
