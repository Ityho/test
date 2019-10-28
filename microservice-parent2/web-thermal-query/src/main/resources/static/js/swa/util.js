// �������ύFORM
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

// ��̬����JS
function scriptLoader(src) {
	if (src != null && src != '') {
		var js = document.createElement("script");
		js.type = 'text/javascript';
		js.src = src;
		js.charset = 'utf-8';
		document.getElementsByTagName("head")[0].appendChild(js);
	}
}

// ��������
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

//�����ּ�ǧλ��
function format_number(n) {
	var b = parseInt(n).toString();
	var len = b.length;
	if (len <= 3) {
		return b;
	}
	var r = len % 3;
	return r > 0 ? b.slice(0, r) + ","
			+ b.slice(r, len).match(/\d{3}/g).join(",") : b.slice(r, len)
			.match(/\d{3}/g).join(",");
}

/**
 * ȥ���ַ�������ĩβ�����ַ�
 * param symbol �ָ�����str �ַ�����
 */
function delTailSymbol(symbol,str){
	var newStr = "";
	if(str == null||str == ""||symbol == null){
		return false;
	}else{
		$.each(str.split(symbol),function(i,n){
			if(i == (str.split(symbol).length-2))
				newStr += n;
			else if(i < (str.split(symbol).length-2))
				newStr += n+"��";
		});
	}
	return newStr;
}