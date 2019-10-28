function goView(title, id, titleHs, province, customFlag1, repeatNum, involveKeyword, kwId) {
	
	var newlstSelect = $("#newlstSelect").val();
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var filterOrigina = $("#filterOrigina").val();
	var secondSearchWord = $("#secondSearchWord").val();
	var otherAttribute = $("#otherAttribute").val();
	var paixu = $("#paixu").val();
	var timeDomain = $("#timeDomain").val();
	var mcFilterOrigina = $("#mcFilterOrigina").val();
	var solrType = $("#solrType").val();
	var collectFlot = false;
	if(kwId == null || kwId == ''){
		collectFlot = true;
	}
	if(!newlstSelect){
		newlstSelect = "";
	}
	if(!starttime){
		starttime = "";
	}
	if(!endtime){
		endtime = "";
	}
	if(!filterOrigina){
		filterOrigina = "";
	}
	if(!secondSearchWord){
		secondSearchWord = "";
	}
	if(!otherAttribute){
		otherAttribute = "";
	}
	if(!paixu){
		paixu = "";
	}
	if(!timeDomain){
		timeDomain = "";
	}
	if(!mcFilterOrigina){
		mcFilterOrigina = "";
	}
	if(!solrType){
		solrType = "";
	}
	if (secondSearchWord == "在结果中搜索" || secondSearchWord == null) {
		secondSearchWord = "";
	}
	var params = {
		'kw.keywordId' : kwId,
		'con.id' : id,
		'titleHs' : titleHs,
		'newlstSelect' : newlstSelect,
		'start_time' : starttime,
		'end_time' : endtime,
		'otherAttribute' : otherAttribute,
		'filterOrigina' : filterOrigina,
		'province' : province,
		'con.customFlag1' : customFlag1,
		'con.repeatNum' : repeatNum,
		'secondSearchWord' : secondSearchWord,
		'clickOtherAttribute' : otherAttribute,
		'clickPaixu' : paixu,
		'clickTimeDomain' : timeDomain,
		'mcFilterOrigina' : mcFilterOrigina,
		'clickmcFilterOrigina' : mcFilterOrigina,
		'solrType' : solrType,
		'involveKeyword' : involveKeyword,
		'collectFlot' : collectFlot
	};

	sendPostForm(njxBasePath + "/content/detail.shtml", '_blank', params);
}