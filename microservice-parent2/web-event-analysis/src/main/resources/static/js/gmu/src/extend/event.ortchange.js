/**
 * @file 扩展转屏事件
 * @name ortchange
 * @short ortchange
 * @desc 扩展转屏事件orientation，解决原生转屏事件的兼容性问题
 * @import zepto.js, extend/matchMedia.js
 */

$(function () {
    /**
     * @name ortchange
     * @desc 扩展转屏事件orientation，解决原生转屏事件的兼容性问题
     * - ***ortchange*** : 当转屏的时候触发，兼容uc和其他不支持orientationchange的设备，利用css media query实现，解决了转屏延时及orientation事件的兼容性问题
     * $(window).on('ortchange', function () {        //当转屏的时候触发
     *     console.log('ortchange');
     * });
     */
    //扩展常用media query
    Zepto.mediaQuery = {
        ortchange: 'screen and (width: ' + window.innerWidth + 'px)'
    };
    console.log(Zepto.matchMedia);
    //通过matchMedia派生转屏事件
    Zepto.matchMedia(Zepto.mediaQuery.ortchange).addListener(function () {
    	Zepto(window).trigger('ortchange');
    });
});