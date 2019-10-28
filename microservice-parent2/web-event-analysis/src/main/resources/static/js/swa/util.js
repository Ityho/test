// 创建并提交FORM
function createForm(url, type, target, params) {
    var temp = document.createElement("form");
    temp.action = url;
    temp.method = type || 'POST';
    temp.target = target || '_self';
    if (params != null) {
        for(var x in params) {
            var opt = document.createElement('input');
            opt.type = 'hidden';
            opt.name = x;
            opt.value = params[x];
            temp.appendChild(opt);
        }
    }
    document.body.appendChild(temp);
    temp.submit();
}

// 动态加载JS
function scriptLoader(src) {
    if (src != null && src != '') {
        var js = document.createElement("script");
        js.type = 'text/javascript';
        js.src = src;
        js.charset = 'utf-8';
        document.getElementsByTagName("head")[0].appendChild(js);
    }
}

// 对象排序
function compare(propertyName) {
    return function(object1, object2) {
        var value1 = object1[propertyName];
        var value2 = object2[propertyName];
        if (value2 < value1) {
            return -1;
        } else if (value2 > value1) {
            return 1;
        } else {
            return 0;
        }
    }
}