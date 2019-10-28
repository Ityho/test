
//¼ì²é¹Ø¼ü´Ê

var common_keyword_filter = /^[/0-9a-zA-Z\u4e00-\u9fa5\s\.\_\-\[\]@#_¡¶¡·']+$/;
var common_keyword_char_filter = /[\s\.\-\[\]@#_¡¶¡·']/g;
function checkKeywordFilter(keyword){
	
	if(keyword == null || keyword == ''){
		return false;
	}
	var newKeywords = $.trim(keyword);
	newKeywords = newKeywords.split(' ').join('');
	newKeywords = newKeywords.split('+').join('');
	newKeywords = newKeywords.split('|').join('');
	newKeywords = newKeywords.split('(').join('');
	newKeywords = newKeywords.split(')').join('');
	if(newKeywords == ''){
		return false;
	}
	if(!common_keyword_filter.test(newKeywords)){
        return false;
    }
	newKeywords = newKeywords.replace(common_keyword_char_filter, '');
	if(newKeywords.length == 0){
		return false;
	}
	return true;
}


function checkSingleKeyword(keyword){
	if(keyword == null || keyword == ''){
		return false;
	}
	var reg = /[+\(\)]/g;
	var keywords = keyword.split("|");
    for(var i=0;i<keywords.length;i++){
    	var kw = keywords[i].trim().replace(reg, '');
    	if(keywords[i].trim().length < 2){
            return false;
    	}
    }
    return true;
}