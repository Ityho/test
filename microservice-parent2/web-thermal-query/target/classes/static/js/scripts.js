
$(function() {
    $( "#sortable ul" ).sortable({ handle: ".tool .icon-move" });
});

$(function() {
    //�ı���ʽѡ��
	 $(".textSele>li").on("click",function(i) {
			if ( $(this).html().indexOf("����1")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("����1 <i class=\"icon-angle-down\"></i>");
		}
		else if( $(this).html().indexOf("����2")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("����2 <i class=\"icon-angle-down\"></i>");
		}
		else if ( $(this).html().indexOf("����")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("���� <i class=\"icon-angle-down\"></i>");
		}
        $(this).parent(".textSele").find("li").removeClass("active");
        $(this).addClass("active");
    });
});

$(function() {
    //ɾ������ģ��
    $(" .row-fluid .textShow .tool .icon-trash").on("click",function() {
        $(this).parents("#sortable >ul>li").remove();
    });
});

//��ิ��ģ��Ч�� start
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
                       //ͳ��ͼ����ʽ�л�
      $( this ).find(".chart-menu>li> a").on("click",function() {
    	var chart=$(this).html();
    	var btn=$(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html();
        $(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html(chart);
        $(this).html(btn);
    });
     //�ı���ʽѡ��
    $( this ).find(".textSele>li").on("click",function(i) {
		if ( $(this).html().indexOf("����1")>0)	{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("����1 <i class=\"icon-angle-down\"></i>");
		}
		else if( $(this).html().indexOf("����2")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("����2 <i class=\"icon-angle-down\"></i>");
		}
		else if ( $(this).html().indexOf("����")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("���� <i class=\"icon-angle-down\"></i>");
		}
		$(this).parent(".btn-group").find(".dropdown-toggle").html("22222");
        $(this).parent(".textSele").find("li").removeClass("active");
        $(this).addClass("active");
    });
            });
            if(flag){
                $( "<li><div class='view'></div></li>" ).html( ui.draggable.html() ).appendTo( this );
            }
  
    
    
            //ɾ������ģ��
            $( this ).find(".row-fluid .textShow .tool .icon-trash").on("click",function() {
                $(this).parents("#sortable >ul>li").remove();
            });
            //���鵼����ʽ�л�
            $( this ).find(" .style1 a").on("click",function() {
				 
			   
			 //	$(this).parents("ul").find(".style2").removeClass("yqddStyle");
                $(this).parents("li").addClass("yqddStyle");

				 
            });
            $( this ).find(" .style2 a").on("click",function() {
 
			//	$(this).parents("ul").find(".style1").removeClass("yqddStyle");
                $(this).parents("li").removeClass("yqddStyle");
 
            });
            //ͳ��ͼ����ʽ�л�
     $( this ).find(".chart-menu>li> a").on("click",function() {
    	var chart=$(this).html();
    	var btn=$(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html();
        $(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html(chart);
        $(this).html(btn);
    });

 //�ı���ʽѡ��
    $( this ).find(".textSele>li").on("click",function(i) {
		 
		if ( $(this).html().indexOf("����1")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("����1 <i class=\"icon-angle-down\"></i>");
		}
		else if( $(this).html().indexOf("����2")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("����2 <i class=\"icon-angle-down\"></i>");
		}
		else if ( $(this).html().indexOf("����")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("���� <i class=\"icon-angle-down\"></i>");
		}
        $(this).parent(".textSele").find("li").removeClass("active");
        $(this).addClass("active");
    });
    
        }
    }).sortable({
        items: "li:not(.placeholder)",
        sort: function() {
            // ��ȡ�� droppable �� sortable �������������
            // ʹ�� connectWithSortable ���Խ��������⣬�����������Զ��� active/hoverClass ѡ��
            $( this ).removeClass( "ui-state-default" );
        }
    });
});

$(function() {
    //���鵼����ʽ�л�
    $(" #tooltipClose").on("click",function() {
        $(".tooltipBlack").css("display","none");
    });
});

$(function() {
    //�л�Ԥ��ģʽ
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
    //���鵼����ʽ�л�
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
    //ͳ��ͼ����ʽ�л�
    $(".chart-menu>li> a").on("click",function() {
    	var chart=$(this).html();
    	var btn=$(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html();
        $(this).parents("li .row-fluid").find(".textShow.chart .text .chartMap").html(chart);
        $(this).html(btn);
    });
    
});

 