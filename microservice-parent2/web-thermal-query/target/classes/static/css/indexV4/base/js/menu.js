$.sidebarMenu = function(menu) {
    var animationSpeed = 300;
    $(menu).on('click', 'li a', function(e) {
        var $this = $(this);
        var checkElement = $this.next();
        if (checkElement.is('.site-menu-sub') && checkElement.is(':visible')) {
            checkElement.slideUp(animationSpeed, function() {
                checkElement.removeClass('active open');
            });
            checkElement.parent("li").removeClass("active open");
        } else if ((checkElement.is('.site-menu-sub')) && (!checkElement.is(':visible'))) {
            var parent = $this.parents('ul').first();
            var ul = parent.find('ul:visible').slideUp(animationSpeed);
            ul.removeClass('active open');
            var parent_li = $this.parent("li");
            checkElement.slideDown(animationSpeed, function() {
                checkElement.addClass('active open');
                parent.find('li.active').removeClass('active open');
                parent_li.addClass('active open');
            });
        }
        if (checkElement.is('.site-menu-sub')) {
            e.preventDefault();
        }
    });
}
