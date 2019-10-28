
$(function() {
    $( "#sortable ul" ).sortable({ handle: ".tool .icon-move" });
});

$(function() {
    //文本样式选择
	 $(".textSele>li").on("click",function(i) {
			if ( $(this).html().indexOf("标题1")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("标题1 <i class=\"icon-angle-down\"></i>");
		}
		else if( $(this).html().indexOf("标题2")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("标题2 <i class=\"icon-angle-down\"></i>");
		}
		else if ( $(this).html().indexOf("正文")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("正文 <i class=\"icon-angle-down\"></i>");
		}
        $(this).parent(".textSele").find("li").removeClass("active");
        $(this).addClass("active");
    });
});

$(function() {
    //删除单条模块
    $(" .row-fluid .textShow .tool .icon-trash").on("click",function() {
        $(this).parents("#sortable >ul>li").remove();
    });
});

//左侧复制模块效果 start
$(function() {
    $( "#catalog" ).accordion();
    $( "#catalog li" ).draggable({
        appendTo: "body",
        helper: "clone"
    });
    $( "#sortable ul" ).droppable({
        activeClass: "ui-state-default",
        hoverClass: "ui-state-hover",
        accept: ":not(.ui-sortable-helper)",
        drop: function( event, ui ) {
            $( this ).find( ".placeholder" ).remove();

            var flag = true;
            $(this).children("li").each(function(i,item){
                if(ui.offset.top<$(item).offset().top && flag){
                    $(item).before( $( "<li><div class='view'></div></li>" ).html( ui.draggable.html()));
                    flag = false;
                }
                       //统计图表样式切换
      $( this ).find(".chart-menu>li> a").on("click",function() {
    	var chart=$(this).html();
    	var btn=$(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html();
        $(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html(chart);
        $(this).html(btn);
    });
     //文本样式选择
    $( this ).find(".textSele>li").on("click",function(i) {
		if ( $(this).html().indexOf("标题1")>0)	{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("标题1 <i class=\"icon-angle-down\"></i>");
		}
		else if( $(this).html().indexOf("标题2")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("标题2 <i class=\"icon-angle-down\"></i>");
		}
		else if ( $(this).html().indexOf("正文")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("正文 <i class=\"icon-angle-down\"></i>");
		}
		$(this).parent(".btn-group").find(".dropdown-toggle").html("22222");
        $(this).parent(".textSele").find("li").removeClass("active");
        $(this).addClass("active");
    });
            });
            if(flag){
                $( "<li><div class='view'></div></li>" ).html( ui.draggable.html() ).appendTo( this );
            }
  
    
    
            //删除单条模块
            $( this ).find(".row-fluid .textShow .tool .icon-trash").on("click",function() {
                $(this).parents("#sortable >ul>li").remove();
            });
            //舆情导读样式切换
            $( this ).find(" .style1 a").on("click",function() {
				 
			   
			 //	$(this).parents("ul").find(".style2").removeClass("yqddStyle");
                $(this).parents("li").addClass("yqddStyle");

				 
            });
            $( this ).find(" .style2 a").on("click",function() {
 
			//	$(this).parents("ul").find(".style1").removeClass("yqddStyle");
                $(this).parents("li").removeClass("yqddStyle");
 
            });
            //统计图表样式切换
     $( this ).find(".chart-menu>li> a").on("click",function() {
    	var chart=$(this).html();
    	var btn=$(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html();
        $(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html(chart);
        $(this).html(btn);
    });

 //文本样式选择
    $( this ).find(".textSele>li").on("click",function(i) {
		 
		if ( $(this).html().indexOf("标题1")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("标题1 <i class=\"icon-angle-down\"></i>");
		}
		else if( $(this).html().indexOf("标题2")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("标题2 <i class=\"icon-angle-down\"></i>");
		}
		else if ( $(this).html().indexOf("正文")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("正文 <i class=\"icon-angle-down\"></i>");
		}
        $(this).parent(".textSele").find("li").removeClass("active");
        $(this).addClass("active");
    });
    
        }
    }).sortable({
        items: "li:not(.placeholder)",
        sort: function() {
            // 获取由 droppable 与 sortable 交互而加入的条
            // 使用 connectWithSortable 可以解决这个问题，但不允许您自定义 active/hoverClass 选项
            $( this ).removeClass( "ui-state-default" );
        }
    });
});

$(function() {
    //舆情导读样式切换
    $(" #tooltipClose").on("click",function() {
        $(".tooltipBlack").css("display","none");
    });
});

$(function() {
    //切换预览模式
    $(" #previewOpen").on("click",function() {
        $("body").addClass("finalDraft").css("background-color","rgba(0, 0, 0,.8)");
        $(".tool").css("display","none");
        $(".tit .btn-group").css("display","none");
        $(" [contenteditable=true]").removeClass("contenteditable");
        $("[contenteditable=true]").attr("contenteditable","false");
    });
    $(" #previewClose").on("click",function() {
        $("body").removeClass("finalDraft").css("background-color","rgba(0, 0, 0,.1)");
        $(".tool").css("display","inline-block");
        $(".tit .btn-group").css("display","inline-block");
        $(" [contenteditable=false]").addClass("contenteditable");
        $("[contenteditable=false]").attr("contenteditable","true");
    });
});

$(function() {
    //舆情导读样式切换
    $(" .style1 a").on("click",function() {

	//	alert( $(this).parents("li").html());
        $(this).parents("li").addClass("yqddStyle");
    });

    $(" .style2 a").on("click",function() {

		//alert( $(this).parents("li").html());
        $(this).parents("li").removeClass("yqddStyle");
    });

});

$(function() {
    //统计图表样式切换
    $(".chart-menu>li> a").on("click",function() {
    	var chart=$(this).html();
    	var btn=$(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html();
        $(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html(chart);
        $(this).html(btn);
    });
    
});

 