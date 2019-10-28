$(document).ready(function($) {
    //侧边栏
    $.sidebarMenu($('.site-menubar'));
    //按钮样式
    Waves.attach('button', ['waves-round', 'waves-light']);
    Waves.init();

});