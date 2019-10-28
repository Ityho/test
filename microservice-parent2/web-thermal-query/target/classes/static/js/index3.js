$(function  () {
     
    var siz=0;
    var flag=true;
    flag1=true;
    hi=$(window).height();
    var le=$('#content li').length,
        index = retuanIndex();

    $('html,body').mousewheel(function(event, delta, deltaX, deltaY){
        if (delta<0&&flag) {
            flag=false;
            siz=$(window).scrollTop()>hi*(le-1)?hi*(le-1):hi+$(window).scrollTop();
            index++;
            $('html,body').stop().animate({scrollTop:siz},time,function  () {
                flag=true;
            })
        }else{
            if (flag) {
                flag=false;
                siz=$(window).scrollTop()<hi*2?0:$(window).scrollTop()-hi;
                index--;
                $('html,body').stop().animate({scrollTop:siz},time,function  () {
                    flag=true;
                })
            }
        }
        isIndex();
        return false;
    })
    $('html,body').keydown(function  (event) {
        if(event.keyCode==40&&flag){
            flag=false;
            siz=$(window).scrollTop()>hi*(le-1)?hi*(le-1):hi+$(window).scrollTop();
            index++;
            $('html,body').stop().animate({scrollTop:siz},time,function  () {
                flag=true;
            })
        }else if(event.keyCode==38&&flag) {
            if (flag) {
                flag=false;
                siz=$(window).scrollTop()<hi*2?0:$(window).scrollTop()-hi;
                index--;
                $('html,body').stop().animate({scrollTop:siz},time,function  () {
                    flag=true;
                })
            }
        }
        isIndex();
        //return false;
    })

    $(".arrowGo").on("click",function(){
        flag=false;
        siz=$(window).scrollTop()>hi*(le-1)?hi*(le-1):hi+$(window).scrollTop();
        index++;
        $('html,body').stop().animate({scrollTop:siz},time,function  () {
            flag=true;
        })
        isIndex();
    });
    $(window).resize(function  () {
        $(window).scrollTop((siz/hi)*$(window).height());
        siz=$(window).scrollTop();
        hi=$(window).height();
    }).scroll(function  () {
        if ($(window).scrollTop()>0&&flag1) {
            flag1=false;
            $('#back').show().hover(function  () {
                $(this).css({background:'none'})
            },function  () {
                $(this).css({background:'#ccc'})
            }).stop().animate({borderRadius:'50%',opacity:1},1000).click(function  () {
                $('html,body').stop().animate({scrollTop:0},1000,function  () {
                    flag1=true;
                })
            });
        }else {
            if ($(window).scrollTop()==0) {
                $('#back').stop().animate({opacity:0},500,function  () {
                    flag1=true;
                    $('#back').css({borderRadius:0}).hide();
                })
            }

        }


    })
    function isIndex(){
        if(index<0){
            index = 0;
        }
        if(index>5){
            index = 5;
        }
        if(index == 1){
            $(".one").removeClass("ones");
            setTimeout(function(){
                $(".one").addClass("ones");
            },50);
        }else if(index == 2){
            $(".two").removeClass("twos");
            setTimeout(function(){
                $(".two").addClass("twos");
            },50);
        }else if(index == 3){
            $(".three").removeClass("threes");
            setTimeout(function(){
                $(".three").addClass("threes");
            },50);
        }else if(index == 4){
            $(".four").removeClass("fours");
            setTimeout(function(){
                $(".four").addClass("fours");
            },50);
        }else if(index == 5){
            $(".five").removeClass("fives");
            setTimeout(function(){
                $(".five").addClass("fives");
            },50);
        }
        
    }
});
function retuanIndex(){
    var index = 0;
    $("#content").find("li").each(function(i,item){
        if($(item).offset().top - 6<$(document).scrollTop() && $(item).offset().top + 6 > $(document).scrollTop()){
            index = i+1;
        }
    });
    return index;
}
