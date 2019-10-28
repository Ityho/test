/********
 ************************************************** 首次购买后优惠包 start**************************************************************/
/**
 * 首次购买体验价
 * @author virgo
 * @param userVerifyType  用户类型
 */
function FirstBuyDiscountPackage(){
	var experiencePrice = getFirstExperiencePrice($('#repostsCount').val());
	$("#item_price_price").html('<span class="fz16">您分析的微博转发数为'+$('#repostsCount').val()+'，原价：<font class="f-r-d">'+$('#cost').val()+'元</font>，超值体验仅需：<font class="f-r">'+experiencePrice+'元</font></span>');
	$("#total_price").html(' 总价：<font id="total_price" class="f-t">'+experiencePrice+'</font>元');
	$('#finalCost').val(experiencePrice);
	document.getElementById("firstAllFee").innerHTML = experiencePrice;
	//设置产品包参数，首次直接购买
	$('#commonBuyId').val(22);
	$('input[name="myProducts[\'product1\'].keywordPackageNum"]').val($('#repostsCount').val());
	$('input[name="fenxiWeiboId"]').val($('#weiboId').val());
	showValidCredit(0,parseFloat(experiencePrice));
}

/**
 * 根据转发数获取首次体验价
 * @author virgo
 * @param repostsCount  转发数
 * @returns
 */
function getFirstExperiencePrice(repostsCount){
	if(repostsCount > 0 && repostsCount <= 100){
		return 0.01;
	}else if(repostsCount > 100 && repostsCount <= 1000){
		return 0.98;
	}else if(repostsCount > 1000 && repostsCount <= 10000){
		return 6.98;
	}else if(repostsCount > 10000 && repostsCount <= 20000){
		return 15.98;
	}else if(repostsCount > 20000 && repostsCount <= 50000){
		return 25.98;
	}else if(repostsCount > 50000 && repostsCount <= 100000){
		return 29.98;
	}else if(repostsCount > 100000 && repostsCount <= 200000){
		return 39.98;
	}else if(repostsCount > 200000 && repostsCount <= 300000){
		return 49.98;
	}else if(repostsCount > 300000 && repostsCount <= 500000){
		return 59.98;
	}else if(repostsCount > 500000 && repostsCount <= 1000000){
		return 99.98;
	}else if(repostsCount > 1000000 && repostsCount <= 2000000){
		return 149.98;
	}else if(repostsCount > 2000000 && repostsCount <= 4000000){
		return 259.98;
	}else if(repostsCount > 4000000){
		return -1;
	}
}
/********************************************************* 首次购买后优惠包 end  **************************************************************/

/********************************************************* 首次购买之后弹出权益包 start   ********************************************************/
/**
 * 权益包返回
 * @author virgo
 * @param repostsCount  转发数
 * @param type 权益包购买方式
 */
function afterFirstBuyPopRightsPackage(repostsCount,type){
	var rightsPackageCurentPriceArray = null;
	var rightsPackageOriginalPriceArray = null;
	var rightsPackageBuyItemsArray = null;
	var str_package_html = "";
	
	if(repostsCount > 0 && repostsCount <= 100){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(4.98);
		rightsPackageOriginalPriceArray.push(10);
		rightsPackageBuyItemsArray.push('1000');
		
		rightsPackageCurentPriceArray.push(29.98);
		rightsPackageOriginalPriceArray.push(100);
		rightsPackageBuyItemsArray.push('1万');
		
		rightsPackageCurentPriceArray.push(49.98);
		rightsPackageOriginalPriceArray.push(200);
		rightsPackageBuyItemsArray.push('2万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "1000";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "4.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "1000";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "4.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(4.98/10).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(4.98);
		setRightsPackageParams(4.98,1,1000);
		setRightsPackageParams4Buy(4.98,1,1000);
		
	}else if(repostsCount > 100 && repostsCount <= 1000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(29.98);
		rightsPackageOriginalPriceArray.push(100);
		rightsPackageBuyItemsArray.push('1万');
		
		rightsPackageCurentPriceArray.push(49.98);
		rightsPackageOriginalPriceArray.push(200);
		rightsPackageBuyItemsArray.push('2万');
		
		rightsPackageCurentPriceArray.push(69.98);
		rightsPackageOriginalPriceArray.push(300);
		rightsPackageBuyItemsArray.push('3万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "1万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "29.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "1万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "29.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(29.98/100).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(29.98);
		setRightsPackageParams(29.98,1,10000);
		setRightsPackageParams4Buy(29.98,1,10000);
	}else if(repostsCount > 1000 && repostsCount <= 10000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(49.98);
		rightsPackageOriginalPriceArray.push(200);
		rightsPackageBuyItemsArray.push('2万');
		
		rightsPackageCurentPriceArray.push(69.98);
		rightsPackageOriginalPriceArray.push(300);
		rightsPackageBuyItemsArray.push('3万');
		
		rightsPackageCurentPriceArray.push(129.98);
		rightsPackageOriginalPriceArray.push(1000);
		rightsPackageBuyItemsArray.push('10万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "2万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "49.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "2万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "49.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(49.98/200).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(49.98);
		setRightsPackageParams(49.98,1,20000);
		setRightsPackageParams4Buy(49.98,1,20000);
	}else if(repostsCount > 10000 && repostsCount <= 20000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(69.98);
		rightsPackageOriginalPriceArray.push(300);
		rightsPackageBuyItemsArray.push('3万');
		
		rightsPackageCurentPriceArray.push(129.98);
		rightsPackageOriginalPriceArray.push(1000);
		rightsPackageBuyItemsArray.push('10万');
		
		rightsPackageCurentPriceArray.push(189.98);
		rightsPackageOriginalPriceArray.push(2000);
		rightsPackageBuyItemsArray.push('20万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "3万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "69.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "3万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "69.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(69.98/300).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(69.98);
		setRightsPackageParams(69.98,1,30000);
		setRightsPackageParams4Buy(69.98,1,30000);
	}else if(repostsCount > 20000 && repostsCount <= 50000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(129.98);
		rightsPackageOriginalPriceArray.push(1000);
		rightsPackageBuyItemsArray.push('10万');
		
		rightsPackageCurentPriceArray.push(189.98);
		rightsPackageOriginalPriceArray.push(2000);
		rightsPackageBuyItemsArray.push('20万');
		
		rightsPackageCurentPriceArray.push(269.98);
		rightsPackageOriginalPriceArray.push(3000);
		rightsPackageBuyItemsArray.push('30万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "10万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "129.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "10万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "129.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(129.98/1000).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(129.98);
		setRightsPackageParams(129.98,1,100000);
		setRightsPackageParams4Buy(129.98,1,100000);
	}else if(repostsCount > 50000 && repostsCount <= 100000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(189.98);
		rightsPackageOriginalPriceArray.push(2000);
		rightsPackageBuyItemsArray.push('20万');
		
		rightsPackageCurentPriceArray.push(269.98);
		rightsPackageOriginalPriceArray.push(3000);
		rightsPackageBuyItemsArray.push('30万');
		
		rightsPackageCurentPriceArray.push(399.98);
		rightsPackageOriginalPriceArray.push(5000);
		rightsPackageBuyItemsArray.push('50万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "20万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "189.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "20万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "189.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*0.905).toFixed(2);
		}
		$("#finalCost").val(189.98);
		setRightsPackageParams(189.98,1,200000);
		setRightsPackageParams4Buy(189.98,1,200000);
	}else if(repostsCount > 100000 && repostsCount <= 200000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(269.98);
		rightsPackageOriginalPriceArray.push(3000);
		rightsPackageBuyItemsArray.push('30万');
		
		rightsPackageCurentPriceArray.push(399.98);
		rightsPackageOriginalPriceArray.push(5000);
		rightsPackageBuyItemsArray.push('50万');
		
		rightsPackageCurentPriceArray.push(699.98);
		rightsPackageOriginalPriceArray.push(10000);
		rightsPackageBuyItemsArray.push('100万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "30万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "269.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "30万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "269.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*0.91).toFixed(2);
		}
		$("#finalCost").val(269.98);
		setRightsPackageParams(269.98,1,300000);
		setRightsPackageParams4Buy(269.98,1,300000);
	}else if(repostsCount > 200000 && repostsCount <= 300000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(399.98);
		rightsPackageOriginalPriceArray.push(5000);
		rightsPackageBuyItemsArray.push('50万');
		
		rightsPackageCurentPriceArray.push(699.98);
		rightsPackageOriginalPriceArray.push(10000);
		rightsPackageBuyItemsArray.push('100万');
		
		rightsPackageCurentPriceArray.push(999.98);
		rightsPackageOriginalPriceArray.push(20000);
		rightsPackageBuyItemsArray.push('200万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "50万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "399.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "50万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "399.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(399.98/5000).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(399.98);
		setRightsPackageParams(399.98,1,500000);
		setRightsPackageParams4Buy(399.98,1,500000);
	}else if((repostsCount > 300000 && repostsCount <= 500000)||(repostsCount > 500000 && repostsCount <= 1000000)){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(699.98);
		rightsPackageOriginalPriceArray.push(10000);
		rightsPackageBuyItemsArray.push('100万');
		
		rightsPackageCurentPriceArray.push(999.98);
		rightsPackageOriginalPriceArray.push(20000);
		rightsPackageBuyItemsArray.push('200万');
		
		rightsPackageCurentPriceArray.push(1899.98);
		rightsPackageOriginalPriceArray.push(40000);
		rightsPackageBuyItemsArray.push('400万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,3,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "100万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "699.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "100万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "699.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(699.98/10000).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(699.98);
		setRightsPackageParams(699.98,1,1000000);
		setRightsPackageParams4Buy(699.98,1,1000000);
	}else if(repostsCount > 1000000 && repostsCount <= 2000000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(999.98);
		rightsPackageOriginalPriceArray.push(20000);
		rightsPackageBuyItemsArray.push('200万');
		
		rightsPackageCurentPriceArray.push(1899.98);
		rightsPackageOriginalPriceArray.push(40000);
		rightsPackageBuyItemsArray.push('400万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,2,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "200万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "999.98";
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "200万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "999.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(999.98/20000).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(999.98);
		setRightsPackageParams(999.98,1,2000000);
		setRightsPackageParams4Buy(999.98,1,2000000);
	}else if(repostsCount > 2000000 && repostsCount <= 4000000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(1899.98);
		rightsPackageOriginalPriceArray.push(40000);
		rightsPackageBuyItemsArray.push('400万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,1,type);
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = "400万";
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = "1899.98";
		}else{
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = "400万";
//			document.getElementById("unfirst_buy_total_price").innerHTML = "1899.98";
			document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(1899.98/40000).toFixed(2))).toFixed(2);
		}
		$("#finalCost").val(1899.98);
		setRightsPackageParams(1899.98,1,4000000);
		setRightsPackageParams4Buy(1899.98,1,4000000);
	}else if(repostsCount > 4000000){
		rightsPackageCurentPriceArray = new Array();
		rightsPackageOriginalPriceArray = new Array();
		rightsPackageBuyItemsArray = new Array();
		rightsPackageCurentPriceArray.push(1899.98);
		rightsPackageOriginalPriceArray.push(40000);
		rightsPackageBuyItemsArray.push('400万');
		str_package_html = makeRightsPackageNeedData(rightsPackageCurentPriceArray,rightsPackageOriginalPriceArray,rightsPackageBuyItemsArray,1,type);
		var pi = repostsCount/4000000;
		if(pi != Math.floor(pi)){
			pi = Math.floor(pi) + 1;
		}
		if(type == 1){
			$("#after_first_buy_pop_package").html(str_package_html);
			document.getElementById("after_first_buy_pop_buy_number").innerHTML = 4000000*pi;
//			document.getElementById("after_first_buy_pop_buy_total_price").innerHTML =  (1899.98*pi).toFixed(2);
		}else if(type == 2){
			$("#unfirst_buy_pop_package").html(str_package_html);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = 4000000*pi;
//			document.getElementById("unfirst_buy_total_price").innerHTML = (1899.98*pi).toFixed(2);
		}
		document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-(1899.98/20000).toFixed(2))).toFixed(2);
		$("#finalCost").val( (1899.98*pi).toFixed(2));//最终价格
		setRightsPackageParams( (1899.98*pi).toFixed(2),pi,4000000*pi);//初始权益包价格
		setRightsPackageParams4Buy( (1899.98*pi).toFixed(2),pi,4000000*pi);//存储总价
		document.getElementById("productCount1").innerHTML = pi;
		document.getElementById("productCount2").innerHTML = pi;
		$("#low_package_item").val(pi);
	}
	
}
/**
 * 构造权益包
 * @author virgo
 * @param rightsPackageArray  权益数据集合
 * @returns make_str_rights_package  权益包
 */
function makeRightsPackage(rightsPackageArray,type){
	var make_str_rights_package = "";
	var packageSize = rightsPackageArray.length;
	$.each(rightsPackageArray,function(i,rightsPackage){
		make_str_rights_package += '<div class="col-md-4">';
		if(packageSize == 1){
			make_str_rights_package = "";
			make_str_rights_package += '<div class="col-md-4" style="float: none;">';
		}
		if(i == 0){
			make_str_rights_package += '<div class="buyBox-item active" id="firstPackage" sale="'+(rightsPackage.currentPrice/rightsPackage.originalPrice).toFixed(4)+'" package="'+i+'" onclick="selectRightsPackage(this,'+type+')">'+
			'<h2 class="f-r">'+
				''+rightsPackage.currentPrice+'元<font class="f-r-d">(原价：'+rightsPackage.originalPrice+'元)</font>'+
			'</h2>'+
			'<input type="hidden" id="rights_package_current_price_'+i+'" value="'+rightsPackage.currentPrice+'" />'+
			'<div class="buyNum">'+
				'<span class="fz12">可累计分析博文转发数'+rightsPackage.repostsCount+'条</span>'+
				'<input type="hidden" id="rights_package_reposts_count_'+i+'" value="'+rightsPackage.repostsCount+'" />'+
			'</div>'+
		'</div>'+
		'</div>';
			$("#rights_total_price").val(rightsPackage.currentPrice);
			$("#rights_total_counts").val(1);
			$("#rights_total_items").val(rightsPackage.repostsCount);
		}else{
			make_str_rights_package += '<div class="buyBox-item" sale="'+(rightsPackage.currentPrice/rightsPackage.originalPrice).toFixed(4)+'" package="'+i+'" onclick="selectRightsPackage(this,'+type+')">'+
			'<h2 class="f-r">'+
				''+rightsPackage.currentPrice+'元<font class="f-r-d">(原价：'+rightsPackage.originalPrice+'元)</font>'+
			'</h2>'+
			'<input type="hidden" id="rights_package_current_price_'+i+'" value="'+rightsPackage.currentPrice+'" />'+
			'<div class="buyNum">'+
				'<span class="fz12">可累计分析博文转发数'+rightsPackage.repostsCount+'条</span>'+
				'<input type="hidden" id="rights_package_reposts_count_'+i+'" value="'+rightsPackage.repostsCount+'" />'+
			'</div>'+
		'</div>'+
		'</div>';
		}
		$("#rights_sale_num").val((rightsPackage.currentPrice/rightsPackage.originalPrice).toFixed(4));//设置权益包的折扣
	});
	return make_str_rights_package;
}
/**
 * 构造权益包数据集合
 * @author virgo
 * @param cPriceArray  折扣后价格
 * @param oPriceArray  原价
 * @param bItemArray   可分析条数
 * @param pItems   权益包个数
 * @returns   权益包html
 */
function makeRightsPackageNeedData(cPriceArray,oPriceArray,bItemsArray,pItems,type){
	var afterPopRightsPackageArray = new Array();
	var afterPopRightsPackage = null;
	for(var i = 0;i < pItems;i++){
		afterPopRightsPackage = new Object();
		afterPopRightsPackage.currentPrice = cPriceArray[i];
		afterPopRightsPackage.originalPrice = oPriceArray[i];
		afterPopRightsPackage.repostsCount = bItemsArray[i];
		afterPopRightsPackageArray.push(afterPopRightsPackage);
	}
	return makeRightsPackage(afterPopRightsPackageArray,type);
}
/********************************************************* 首次购买之后弹出权益包 end     ********************************************************/

/********************************************************* 权益包事件 start     ********************************************************/
/**
 * 选择权益包事件
 * @author virgo
 */
function selectRightsPackage(obj,type){
	$(".buyBox-item").removeClass("active");
	obj.setAttribute("class","buyBox-item active");
	var page = obj.getAttribute("package");
	var item = $("#rights_package_reposts_count_"+page).val();
	if(item.split("万").length == 2){
		item = item.split("万")[0]*10000;
	}
	setRightsPackageParams($("#rights_package_current_price_"+page).val(),1,item);
	setRightsPackageParams4Buy($("#rights_package_current_price_"+page).val(),1,item);
	document.getElementById("after_first_buy_pop_buy_number").innerHTML = item;
//	document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = $("#rights_package_current_price_"+page).val();
	if(type == 2){
		document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = item;
//		document.getElementById("unfirst_buy_total_price").innerHTML = $("#rights_package_current_price_"+page).val();
		document.getElementById("unfirst_buy_save_cost").innerHTML =  ($("#cost").val()*(1-obj.getAttribute("sale"))).toFixed(2);
	}
	$('#finalCost').val($("#rights_total_price_buy").val());
	$("#rights_sale_num").val(obj.getAttribute("sale"));
	//更改总次数，还有总价格，隐藏域总价格
	if($("#repostsCount").val()>4000000){
		document.getElementById("productCount1").innerHTML = $("#low_package_item").val();
		document.getElementById("productCount2").innerHTML = $("#low_package_item").val();
	}else{
		document.getElementById("productCount1").innerHTML = 1;
		document.getElementById("productCount2").innerHTML = 1;
	}
	$('span[name="commonBuyOrderFeeSpan"]').text($("#rights_total_price").val());
	$('#commonBuyId').val(22);
	$('input[name="myProducts[\'product1\'].keywordPackageNum"]').val($('#rights_total_items_buy').val());
}

/**
 * 建减少权益包个数事件
 * @author virgo
 * @param type 购买权益包的方式 1-首次  2-非首次
 */
function delKeyword(type){
	var id = "productCount2";
	if(type == 1)
		id="productCount1";
	var productCount = document.getElementById(id).innerHTML;
	if(productCount > 1){
		if(parseInt($('#repostsCount').val())>4000000 && productCount == $("#low_package_item").val()){
				return false;
		}
		productCount--;
		document.getElementById(id).innerHTML = productCount;
		
	}
	setRightsPackageParams4Buy(($("#rights_total_price").val()*productCount).toFixed(2),productCount,$("#rights_total_items").val()*productCount);
	$('#finalCost').val($("#rights_total_price_buy").val());
	if(type == 1){
//		document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = $("#rights_total_price_buy").val();
		document.getElementById("after_first_buy_pop_buy_number").innerHTML = $("#rights_total_items").val()*productCount;
	}else if(type == 2){
//		document.getElementById("unfirst_buy_total_price").innerHTML = $("#rights_total_price_buy").val();
		document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = $("#rights_total_items").val()*productCount;
		document.getElementById("unfirst_buy_save_cost").innerHTML =  ($("#cost").val()*(1-$("#rights_sale_num").val())).toFixed(2);
	}
	$('span[name="commonBuyOrderFeeSpan"]').text($("#rights_total_price_buy").val());
	showValidCredit(0,parseFloat($("#rights_total_price_buy").val()));
	$('input[name="myProducts[\'product1\'].keywordPackageNum"]').val($('#rights_total_items_buy').val());
}
/**
 * 增加权益包个数事件
 * @author virgo
 * @param type 购买权益包的方式 1-首次  2-非首次
 */
function addKeyword(type){
	var id = "productCount2";
	if(type == 1)
		id="productCount1";
	var productCount = document.getElementById(id).innerHTML;
	productCount++;
	document.getElementById(id).innerHTML = ""+productCount;
	setRightsPackageParams4Buy(($("#rights_total_price").val()*productCount).toFixed(2),productCount,$("#rights_total_items").val()*productCount);
	$('#finalCost').val($("#rights_total_price_buy").val());
	if(type == 1){
//		document.getElementById("after_first_buy_pop_buy_total_price").innerHTML = $("#rights_total_price_buy").val();
		document.getElementById("after_first_buy_pop_buy_number").innerHTML = $("#rights_total_items").val()*productCount;
	}else if(type == 2){
//		document.getElementById("unfirst_buy_total_price").innerHTML = $("#rights_total_price_buy").val();
		document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = $("#rights_total_items").val()*productCount;
		document.getElementById("unfirst_buy_save_cost").innerHTML =  ($("#cost").val()*(1-$("#rights_sale_num").val())).toFixed(2);
	}
	$('span[name="commonBuyOrderFeeSpan"]').text($("#rights_total_price_buy").val());
	showValidCredit(0,parseFloat($("#rights_total_price_buy").val()));
	$('input[name="myProducts[\'product1\'].keywordPackageNum"]').val($('#rights_total_items_buy').val());
}
/**
 * 
 * @author virgo
 * @param totalPrice 单个权益包的价格
 * @param packageCounts 单个权益包
 * @param analysisItems 单个权益包的次数
 */
function setRightsPackageParams(totalPrice,packageCounts,analysisItems){
	$("#rights_total_price").val(totalPrice);
	$("#rights_total_counts").val(packageCounts);
	$("#rights_total_items").val(analysisItems);
}
/**
 * 选择权益包设置权益包的参数信息
 * @author virgo
 * @param totalPrice 总价格
 * @param packageCounts 权益包数量
 * @param analysisItems 总的分析次数
 */
function setRightsPackageParams4Buy(totalPrice,packageCounts,analysisItems){
	$("#rights_total_price_buy").val(totalPrice);
	$("#rights_total_counts_buy").val(packageCounts);
	$("#rights_total_items_buy").val(analysisItems);
}
/********************************************************* 权益包事件 end       ********************************************************/


/********************************************************* 非首次购买 start      ********************************************************/
/**
 * 非首次购买的购买弹窗
 * @author virgo
 */
function unfirstBuyPop(){
	if ($('#needPay').val() == 'true' && parseInt($('#repostsCount').val()) > 0) {
		$("#unfirst_buy_all_info").html('您分析的微博转发数为'+$('#repostsCount').val()+'条，原价：<font class="f-r-d">'+$('#cost').val()+'元</font>，本次分析共为您节省：<font class="f-r" id="unfirst_buy_save_cost">0.00</font>元');
//		if(document.getElementById("unfirst_buy_payment_3_total_items"))
//			document.getElementById("unfirst_buy_payment_3_total_items").innerHTML = $("#currentItems").val();
		//payment_1
		document.getElementById("unfirst_buy_save_cost").innerHTML = (parseFloat($('#cost').val()) - parseFloat($('#no_first_v_normal').val())).toFixed(2);
		$("#unfirst_buy_payment_1_info").html('立享'+parseFloat($('#discount_nofirstbuy').val())*10+'折优惠，立省<font id="unfirst_buy_user_type_save_cost" class="f-r">'+(parseFloat($('#cost').val()) - parseFloat($('#no_first_v_normal').val())).toFixed(2)+'元</font>');
//		document.getElementById("unfirst_buy_total_price").innerHTML = $('#no_first_v_normal').val();
		$("#unfirst_buy_user_type_total_price").val($('#no_first_v_normal').val());
		$("#finalCost").val($('#no_first_v_normal').val());
		//payment_2
		afterFirstBuyPopRightsPackage(parseInt($('#repostsCount').val()),2);
		$('span[name="commonBuyOrderFeeSpan"]').text($("#rights_total_price").val());
		//payment_3
		document.getElementById("unfirst_buy_payment_3_total_items").innerHTML = $('#currentItems').val();
		if(parseInt($('#repostsCount').val()) > parseInt($('#currentItems').val()))
			$("#unfirst_buy_payment_3_info").append('<font class="f-g">（账户剩余转发数不足，请购买权益包）</font>');
		
		showValidCredit(0,parseFloat($("#rights_total_price").val()));
		$('#commonBuyId').val(22);
		$('input[name="myProducts[\'product1\'].keywordPackageNum"]').val($('#repostsCount').val());
	} else if ($('#needPay').val() == 'false' && parseInt($('#repostsCount').val()) > 0 && $('#exists').val() == 'false') {
		$('#divPay').empty();
		
		var html = '';
		html += '<ul>';
		html += '	<li>';
		html += '		<span>转发' + $('#repostsCount').val() + '条</span>';
		html += '		<span class="float_r">';
		html += '			<font class="f-d">原价' + $('#cost').val() + '元</font>共为您节省<font class="f-r">' + $('#cost').val() + '</font>元';
		html += '		</span>';
		html += '	</li>';
		if ($('#isFirstBuy').val() == 'true') {
			html += '	<li><span class="f-g">还可享受的优惠</span></li>';
			html += '	<li>';
			html += '		<span class="f-g"><i>多买惠</i>本月首次购买，下次享' + $('#discount_nofirstbuy').val() + '折</span>';
			html += '	</li>';
		}
		html += '</ul>';
		html += '<div class="buttom">';
		html += '	总价：<font class="f-t">0.00</font>元';
		html += '</div>';
		$('#divPay').append(html);
		$('#payPOP').modal({backdrop: 'static'});
		
		$('#weiboConfirmOrderBtn').text('确认');
		$('#weiboConfirmOrderBtn').click(function() {
			$('#payPOP').modal('hide');
			
			// 获取微博分析状态
			weiboTaskStatus();
		});
	} else if(parseInt($('#repostsCount').val())==0) {
		$('.loadingMask .waiting').html('<a class="closeBtn" href="javascript:;" onclick="javascript:location.href = \'' + actionBase + '/weibo/toAnalysis\';">×</a>该微博暂无转发层级！');
	}else{
		// 获取微博分析状态
		weiboTaskStatus();
	}
}

/********************************************************* 非首次购买 end        ********************************************************/

/********************************************************* 选择购买方式 start     ********************************************************/
/**
 * 选择支付方式
 * @author virgo
 */
function selectPayment(obj){
	var _payment = obj.getAttribute("payment");
	$("#payment").val(_payment);
	if(!(_payment == 3 && (parseInt($('#repostsCount').val()) > parseInt($('#currentItems').val())))){
		$(".price-radio li").removeClass("active");
		obj.setAttribute("class","active");
		setPayParamsByPayment(_payment);//设置总价、原价、节省价钱
		//初始化权益包的信息
		if(_payment != 2){
			$(".buyBox-item").removeClass("active");
			$("#firstPackage")[0].setAttribute("class","buyBox-item active");
			var page = $("#firstPackage")[0].getAttribute("package");
			var item = $("#rights_package_reposts_count_"+page).val();
			if(item.split("万").length == 2){
				item = item.split("万")[0]*10000;
			}
			setRightsPackageParams($("#rights_package_current_price_"+page).val(),1,item);
			setRightsPackageParams4Buy($("#rights_package_current_price_"+page).val(),1,item);
			document.getElementById("unfirst_buy_payment_2_total_items").innerHTML = item;
			
			//更改总次数，还有总价格，隐藏域总价格
			if($("#repostsCount").val()>4000000){
				document.getElementById("productCount1").innerHTML = $("#low_package_item").val();
				document.getElementById("productCount2").innerHTML = $("#low_package_item").val();
			}else{
				document.getElementById("productCount1").innerHTML = 1;
				document.getElementById("productCount2").innerHTML = 1;
			}
		}
		$("#zhifu_1").css("display","block");
		$("#zhifu_2").css("display","none");
	}
	if(_payment == 3 && (parseInt($('#repostsCount').val()) <= parseInt($('#currentItems').val()))){
		var html = '<div class="buttom line" style="padding-top: 40px;">'+
									'<div class="row-fluid clearfix ">'+
										'<span class="float_r">'+
											'<button class="btn btn-primary float_r" id="weiboUnfirstConfirmOrderBtn">确认</button>'+
										'</span>'+
									'</div>'+
								'</div>';
		
		$("#zhifu_1").css("display","none");
		$("#zhifu_2").css("display","block");
	}
}
/**
 * 选择不同的支付方式设置不同的参数信息
 * @author virgov
 * @param payment 支付方式
 */
function setPayParamsByPayment(payment){
	if(payment == 1){
		document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()-$("#unfirst_buy_user_type_total_price").val()).toFixed(2);//节约钱数
//		document.getElementById("unfirst_buy_total_price").innerHTML = $("#unfirst_buy_user_type_total_price").val();//支付总价
		$("#finalCost").val($("#unfirst_buy_user_type_total_price").val());
		$('span[name="commonBuyOrderFeeSpan"]').text($("#unfirst_buy_user_type_total_price").val());
		$('input[name="fenxiWeiboId"]').val($('#weiboId').val());
		showValidCredit(0,parseFloat($("#unfirst_buy_user_type_total_price").val()));
		$('input[name="commonBuyUseCreditChk"]').iCheck('uncheck');
	}else if(payment == 2){
//		document.getElementById("unfirst_buy_total_price").innerHTML = $("#rights_total_price_buy").val();
		document.getElementById("unfirst_buy_save_cost").innerHTML = ($("#cost").val()*(1-$("#rights_sale_num").val())).toFixed(2);//节约钱数
		$("#finalCost").val($("#rights_total_price_buy").val());
		$('span[name="commonBuyOrderFeeSpan"]').text($("#rights_total_price_buy").val());
		$('input[name="fenxiWeiboId"]').val("");
		showValidCredit(0,parseFloat($("#rights_total_price_buy").val()));
		$('input[name="commonBuyUseCreditChk"]').iCheck('uncheck');
	}else if(payment == 3){
		document.getElementById("unfirst_buy_save_cost").innerHTML = $('#cost').val();//节约钱数
//		document.getElementById("unfirst_buy_total_price").innerHTML = 0.00;//支付总价
		$("#finalCost").val(0.01);
		$('#weiboUnfirstConfirmOrderBtn').text('确认');
		$('input[name="fenxiWeiboId"]').val("");
		$('input[name="commonBuyUseCreditChk"]').iCheck('uncheck');
	}
}
/********************************************************* 选择购买方式 end       ********************************************************/

/********************************************************* 直接支付和权益包购买再分析    ********************************************************************/

function goCommonBuy1(){
	var payWay = $("#payment").val();
	if(payWay == 2){
		//构造购买权益包提交参数
		$('#commonBuyId').val(22);
		$('input[name="myProducts[\'product1\'].keywordPackageNum"]').val($('#rights_total_items_buy').val());
	}
	goCommonBuy();
}
