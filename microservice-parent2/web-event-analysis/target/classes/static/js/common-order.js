

/*
	初始打开窗口
	type：
		1：监测方案
		2：全网事件分析
		3：微博事件分析
		4：简报制作
		5：竞品分析
		6：充值
		7：套餐
		8：单条微博分析
		9：热度日报按次
	   10：热度日报时长
	   11：自由搭配
	   12：单条微博分析优惠包
	   13：数据导出
	   14：评论分析
	params：
		参数
*/
var commonBuyOpenTypeKeyword = 1;
var commonBuyOpenTypeAnalysis = 2;
var commonBuyOpenTypeWeiboAnalysis = 3;
var commonBuyOpenTypeBrief = 4;
var commonBuyOpenTypeProductAnalysis = 5;
var commonBuyOpenTypeCredit = 6;
var commonBuyOpenTypePro = 7;
var commonBuyOpenTypeSWA = 8;
var commonBuyOpenTypeHotReportCount = 9;
var commonBuyOpenTypeHotReportDays = 10;
var commonBuyOpenTypeZYDP = 11;
var commonBuyOpenTypeSWA1 = 12;
var commonBuyOpenTypeExportData = 13;
var commonBuyOpenTypeComments = 14;
var isOpen = true;

var userHaveCreditNum = 0;
var useCreditNum = 0;

function openBuyCommon(type, params) {
	buyJq('#commonBuySelectIds').val('');
	buyJq('#commonBuyFenxiWeiboId').val('');
	buyJq('#commonBuyKeywords').val('');
	buyJq('#commonBuyHeatReportId').val('');
	buyJq('#commonBuyDays').val('');

	isOpen = true;
	buyJq('#commonBuyOpenType').val(type);
	setPackageCount(1,true);
	if (buyJq.isEmptyObject(params))
		params = {};
	initUseCredit(); // 初始化是否使用微积分
	initPayChannel(); // 初始化支付方式
	buyJq('ul[name="commonBuySetCountUL"] > li').each(function(i, n) {
		if (buyJq(this).attr('data-cmd') == 1)
			buyJq(this).addClass('active');
		else
			buyJq(this).removeClass('active');
	});

	// 打开窗口
	if (type == commonBuyOpenTypeKeyword) {
		buyJq('#commonBuyKeywordBtn').trigger('click');

		initKeywordSelect(params.keywordIds); // 初始化监测方案选中
	} else if (type == commonBuyOpenTypeAnalysis) {
		buyJq('#commonBuyAnalysisBtn').trigger('click');
		buyJq('#commonBuyId').val(buyJq('#commonBuyAnalysisFirstId').val());
	} else if (type == commonBuyOpenTypeWeiboAnalysis) {
		buyJq('#commonBuyWeiboAnalysisBtn').trigger('click');
		buyJq('#commonBuyId').val(buyJq('#commonBuyWeiboAnalysisFirstId').val());
	} else if (type == commonBuyOpenTypeBrief) {
		buyJq('#commonBuyBriefBtn').trigger('click');
		buyJq('#commonBuyId').val(buyJq('#commonBuyBriefFirstId').val());
	} else if (type == commonBuyOpenTypeProductAnalysis) {
		buyJq('#commonBuyProductAnalysisBtn').trigger('click');
		buyJq('#commonBuyId').val(buyJq('#commonBuyProductAnalysisFirstId').val());
	}  else if (type == commonBuyOpenTypePro) {
		if (params.packageId) {
			buyJq('#commonBuyProBtn').trigger('click');
			buyJq('#commonBuyProName').text(params.packageName);
			buyJq('#commonBuyProPrice').text(params.packagePrice);
			buyJq('#commonBuyUseCredit').val(false);
			buyJq('#payChannel').val(1);
			initPayChannel();
			buyJq('#commonBuyId').val(params.packageId);
		}
		if (params.proContinue) {
			buyJq('#commonBuyProContinue').val(true);
		}
	} else if (type == commonBuyOpenTypeSWA) {
		if (params.packageCount) {
			buyJq('#commonBuySWABtn').trigger('click');
			buyJq('#commonBuyId').val(buyJq('#commonBuySWAFirstId').val());
			buyJq('#commonBuyFenxiWeiboId').val(buyJq('#weiboId').val());
			buyJq('#commonBuySWACount').text(params.packageCount);
			setPackageCount(params.packageCount,true);
		}
	} else if (type == commonBuyOpenTypeHotReportCount) {
		if (params.packageCount && params.keywords) {
			buyJq('#commonBuyHotReportCountBtn').trigger('click');
			buyJq('#commonBuyId').val(buyJq('#commonBuyHotReportCountFirstId').val());
			buyJq('#commonBuyKeywords').val(params.keywords);
			if (params.days)
				buyJq('#commonBuyDays').val(params.days);
			setPackageCount(params.packageCount,true);
		}
	} else if (type == commonBuyOpenTypeHotReportDays) {
		if (params.heatReportId) {
			buyJq('#commonBuyHotReportDaysBtn').trigger('click');
			buyJq('#commonBuyHeatReportId').val(params.heatReportId);

			initHotReportDaysSelect(); // 初始化热度日报时长选中
		}
	} else if (type == commonBuyOpenTypeSWA1) {
		if (buyJq('#commonBuySWAHighFrequently').val() == 'true') {
			buyJq('#commonBuySWA2Btn').trigger('click');
			buyJq('#commonBuyId').val(buyJq('#commonBuySWA2FirstId').val());
			buyJq('#swa2commonBuySetCountLI').trigger('click');
		} else {
			buyJq('#commonBuySWA1Btn').trigger('click');
			buyJq('#commonBuyId').val(buyJq('#commonBuySWA1FirstId').val());
			buyJq('#swa1commonBuySetCountLI').trigger('click');
		}
	} else if (type == commonBuyOpenTypeExportData) {
		if (params.packageCount) {
			buyJq('#commonBuyExportDataBtn').trigger('click');
			buyJq('#commonBuyId').val(buyJq('#commonBuyExportDataFirstId').val());
			buyJq('#commonBuyExportDataCount').text(params.packageCount);
			var exportConditionId = params.exportConditionId;
			buyJq('#commonBuyExportConditionId').val(exportConditionId);
			setPackageCount(params.packageCount,true);
		}
	} else if (type == commonBuyOpenTypeComments) {
		if (params.packageCount) {
			buyJq('#commonBuyCommentsBtn').trigger('click');
			buyJq('#commonBuyId').val(buyJq('#commonBuyCommentsFirstId').val());
			buyJq('#commonBuyCommentsId').val(buyJq('#reviewAnalysisId').val());
			buyJq('#commonBuyCommentsCount').text(params.packageCount);
			setPackageCount(params.packageCount,true);
		}
	}
	setTimeout(function(){
		commonBuyOrderFee();
	}, 100);
}



// 设置支付方式
function initPayChannel() {
	buyJq('.payment .orderLabel input[type="radio"]').each(function() {
		if (buyJq(this).val() == buyJq('#payChannel').val()){
			buyJq(this).iCheck('check');
		}
	});
}



// 设置是否使用微积分
function initUseCredit() {
	if (buyJq('#commonBuyUseCredit').val() == 'true')
		buyJq('input[name="commonBuyUseCreditChk"]').iCheck('check');
	else
		buyJq('input[name="commonBuyUseCreditChk"]').iCheck('uncheck');
}

// 初始化监测方案选中
function initKeywordSelect(keywordIds) {
	if (keywordIds) {
		buyJq('#commonBuySelectIds').val(keywordIds);
		buyJq('div.setcount').hide();
		buyJq('.buy-keyword-set-count').hide();
		buyJq('.continue-keyword-set-count').show();
	} else {
		buyJq('#commonBuySelectIds').val('');
		buyJq('div.setcount').show();
		buyJq('.continue-keyword-set-count').hide();
		buyJq('.buy-keyword-set-count').show();
	}
	buyJq("#keywordExtendType").text("（年）");
	buyJq('#keywordPackagesUL > li').each(function(i) {
		if (i == 0) {
			buyJq(this).addClass('active');
		} else {
			buyJq(this).removeClass('active');
		}
	});
	buyJq('#commonBuyId').val(buyJq('#commonBuyKeywordFirstId').val());
}

// 初始化热度日报时长选中
function initHotReportDaysSelect() {
	buyJq('#hotReportDaysPackagesUL > li').each(function(i) {
		if (i == 0) {
			buyJq(this).addClass('active');
		} else {
			buyJq(this).removeClass('active');
		}
	});
	buyJq('#commonBuyId').val(buyJq('#commonBuyHotReportDaysFirstId').val());
}

//获取待支付金额
var getOrderFeeFlag = 0;
function commonBuyOrderFee(){
	getOrderFeeFlag++;
	setTimeout(function(){
		getOrderFeeFlag--;
		if(getOrderFeeFlag == 0){
			toGetOrderFee();
		}
	}, 500);
}

function toGetOrderFee() {
	hideBuyBtn();
	var timestamp = (new Date()).valueOf();
	$("#commonBuyTimeFlag").val(timestamp);
	$.ajax({
		url : njxBasePath + '/view/pay/getOrderFee.action',
		type : 'POST',
		cache : 'false',
		data : $('#commonBuyForm').serialize(),
		success : function(data) {
			if (!$.isEmptyObject(data)) {
				timestamp = $("#commonBuyTimeFlag").val();
				if(timestamp != data.timeFlag){	//判断是否是当前金额
					return false;
				}
				showBuyBtn();
				userHaveCreditNum = data.userCreditNum;
				useCreditNum = parseInt(data.totalFee*100 + data.useCreditAmount);

				if(data.discountFee > 0){
					$('span[name="commonBuyDiscountFeeSpan"]').text(data.discountFee.toFixed(2) + '元');
					$('.discountFeeSpan').show();
				}else{
					$('.discountFeeSpan').hide();
				}
				if($("#commonBuyUseCredit").val() == 'true'){
					$('span[name="commonBuyTotalFeeSpan"]').text(useCreditNum + '微积分');
					if(data.totalFee > 0){	//微积分不足
						//showBuyPageInfoDiv(2, 0, userHaveCreditNum);
						$("#userHaveCreditNum").text(userHaveCreditNum);
						$(".buy-btn").hide();
						$(".go-buy-credit-btn").show();
					}else{
						$(".go-buy-credit-btn").hide();
						$(".buy-btn").show();
					}
					$('span[name="commonBuyOrderFeeSpan"]').text(useCreditNum + '微积分');
				}else{
					$(".go-buy-credit-btn").hide();
					$(".buy-btn").show();
					$('span[name="commonBuyTotalFeeSpan"]').text(data.totalFee.toFixed(2) + '元');
					$('span[name="commonBuyOrderFeeSpan"]').text((data.totalFee - data.discountFee - data.voucherFee).toFixed(2) + '元');
				}
			}
		}
	});
}

//隐藏购买按钮
function hideBuyBtn() {
	$(".commonBuyBtn").removeAttr("onclick");
	/*var type = $('#commonBuyOpenType').val();
	if (type == commonBuyOpenTypeKeyword) {
		$('#commonBuyKeywordBuyBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeAnalysis) {
		$('#commonBuyAnalysisBuyBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeWeiboAnalysis) {
		$('#commonBuyWeiboAnalysisBuyBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeBrief) {
		$('#commonBuyBriefBuyBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeProductAnalysis) {
		$('#commonBuyProductAnalysisBuyBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeCredit) {
		$('#commonBuyCreditBuyBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypePro) {
		$('#commonBuyProBuyBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeSWA) {
		$('#commonBuySWABuyBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeHotReportCount) {
		$('#commonBuyHotReportCountBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeHotReportDays) {
		$('#commonBuyHotReportDaysBtn').removeAttr('onclick');
	} else if (type == commonBuyOpenTypeSWA1) {
		$('#commonBuySWA1Btn').removeAttr('onclick');
	}*/
}

// 显示购买按钮
function showBuyBtn() {
	$(".commonBuyBtn").attr("onclick", "goCommonBuy()");
	/*var clickEvent = 'goCommonBuy()';
	var type = $('#commonBuyOpenType').val();
	if (type == commonBuyOpenTypeKeyword) {
		$('#commonBuyKeywordBuyBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeAnalysis) {
		$('#commonBuyAnalysisBuyBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeWeiboAnalysis) {
		$('#commonBuyWeiboAnalysisBuyBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeBrief) {
		$('#commonBuyBriefBuyBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeProductAnalysis) {
		$('#commonBuyProductAnalysisBuyBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeCredit) {
		$('#commonBuyCreditBuyBtn').attr('onclick', 'goCommonBuy(1)');
	} else if (type == commonBuyOpenTypePro) {
		$('#commonBuyProBuyBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeSWA) {
		$('#commonBuySWABuyBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeHotReportCount) {
		$('#commonBuyHotReportCountBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeHotReportDays) {
		$('#commonBuyHotReportDaysBtn').attr('onclick', clickEvent);
	} else if (type == commonBuyOpenTypeSWA1) {
		$('#commonBuySWA1Btn').attr('onclick', clickEvent);
	}*/
}


function commonChangeAgreementCheck(value){
	if(!value){
		$(".commonBuyBtn").removeAttr("onclick");
		$(".commonBuyBtn").removeClass("btn-warning");
		$(".commonBuyBtn").addClass("btn-disabled");
	}else{
		$(".commonBuyBtn").attr("onclick","goCommonBuy();");
		$(".commonBuyBtn").removeClass("btn-disabled");
		$(".commonBuyBtn").addClass("btn-warning");
	}
}

function stopMoreClick(){
	showMsgInfo(0,"亲，不可重复提交哦!",0);
}


//支付
var commonBuyFlag = 0;
var intervalId;
function goCommonBuy(n){
	var type = $('#payChannel').val();
	/*if(operateLogPageCode == '1023' && type != 0)	//产品购买页禁止多次点击
		$(".commonBuyBtn").attr("onclick", "stopMoreClick()");*/
	var type = $('#payChannel').val();
	if(type == 0){
		$('#confirm-payment-credit-btn').click(function(){
			doCommonBuy(n);
		})
		showBuyPageInfoDiv(1, useCreditNum);
	}else{
		doCommonBuy(n);
	}
}

function doCommonBuy(n) {
	hideBuyPageInfoDiv();
	$('.buy-modal-close').click();

	var orderFee = $('span[name="commonBuyOrderFeeSpan"]').text();
	if(orderFee == ''){
		return false;
	}
	if (commonBuyFlag == 0) {
		commonBuyFlag = 1;
		var type = $('#payChannel').val();
		var newTab;
		if (type != 3 && orderFee.indexOf('元') > 0 && parseFloat(orderFee.replace('元', '')) > 0)
			newTab = window.open('about:blank');
		$.ajax({
			url : njxBasePath + '/view/pay/goPayV3.action',
			type : 'POST',
			data : $('#commonBuyForm').serialize(),
			success : function(data) {
				if (!$.isEmptyObject(data)) {
					if (data.orderInfo.totalFee == 0) {
						$.ajax({
							url : njxBasePath + '/view/pay/doPayEnd.action',
							type : 'POST',
							data : {
								'payRecord.innerTradeNo' : data.payInfo.innerTradeNo
							},
							success : function(data){}
						});
						setTimeout("myInterval(" + data.payInfo.payRecordId + ","+ data.orderInfo.orderRecordId + ")", 1000);//1000为1秒钟
					} else {
						$('#commonBuyCurrentAdminCreditAmount').val(parseInt($('#commonBuyCurrentAdminCreditAmount').val()) - data.useCreditAmount);

						function stopMoreClick(){

							if (type == 3) {
								sendPostForm(url, '_self', {'order.orderNo' : data.orderInfo.orderNo, 'order.totalFee' : data.orderInfo.totalFee});
								if($("#exportPay").val()==1){
									$("#exportPay").val(0);
									exportOfflineTransfer(data.orderInfo.orderRecordId);
								}else if($("#exportPay").val()==2){
									$("#exportPay").val(0);
									exportOfflineTransferPage(data.orderInfo.orderRecordId);
								}else if($("#favoritePay").val()>0){
									$("#favoritePay").val(0);
									favoriteExportTransfer(data.orderInfo.orderRecordId);
								}else if($("#similarConPay").val()>0){
									$("#similarConPay").val(0);
									similarConExport(data.orderInfo.orderRecordId);
								}
							} else {
								if($("#exportPay").val()==1){
									exportOfflineTransfer(data.orderInfo.orderRecordId);
								}else if($("#exportPay").val()==2){
									exportOfflineTransferPage(data.orderInfo.orderRecordId);
								}else if($("#favoritePay").val()>0){
									favoriteExportTransfer(data.orderInfo.orderRecordId);
								}else if($("#similarConPay").val()>0){
									similarConExport(data.orderInfo.orderRecordId);
								}
								newTab.location.href = url;
								if(!n||n!=1){
									setTimeout("myInterval(" + data.payInfo.payRecordId + ","+data.orderInfo.orderRecordId+")", 1000);//1000为1秒钟
								}
							}
						}

						$('.modal-header button.close').trigger('click');
					}
				}
			}});
	}
}

function myInterval(id,orderRecordId) {
	$.ajax({
		type:"post",
		url: njxBasePath + "/view/user/checkOrderStatus.action?payRecord.payRecordId="+id,
		cache:false,
		success: function(data){
			if (!$.isEmptyObject(data)) {
				commonBuyFlag = 0;
				if (data.payRecord.payStatus == 1) {
					if($("#oneOrMore").val()=="2"&&$("#theTwoOrOnePageID").val()=="1"){
						window.clearInterval(intervalId);
// 						$("#isEver").val(orderRecordId);
						var categoryId=$("#categoryId").val();
						var categoryType=$("#categoryType").val();
						var secondCategory=$("#secondCategory").val();
						var categoryLevel=$("#categoryLevel").val();
						goSearchMorePage(orderRecordId,categoryId,categoryType,secondCategory,categoryLevel);
// 						getSolidifyQuery(orderRecordId);
						return;
					}
					if($("#oneOrMore").val()=="1"&&$("#theTwoOrOnePageID").val()=="2"){
						window.clearInterval(intervalId);
						goSearchOnePage();
						return;
					}


					if($("#recordName").length!=0&&$("#recordName").val()!=""){
						window.clearInterval(intervalId);
//                      $("#loginRecordForm").submit();
						goAnalysisStart();
					}else if($("#heatName").length!=0&&$("#heatName").val()!=""){
						window.clearInterval(intervalId);
						goCreate();
					}else if($("#heatNameWB").length!=0&&$("#heatNameWB").val()!=""){
						window.clearInterval(intervalId);
						goCreate();
					}else if($("#currentPage").length!=0&&$("#currentPage").val()=="订阅热度报告"){
						window.clearInterval(intervalId);
						showMsgInfo(0,"订阅成功!",0);
						$("#submitBtn").click(function(){
							window.location.href=njxBasePath + "/findHotReport.action";
						});
						//setTimeout(function(){hideInfoDiv();window.location.reload(true);},2000);
					}else if($("#currentPage").length!=0&&$("#currentPage").val()=="热度分析"&&$("#monitorFlag").val()==2){
						window.clearInterval(intervalId);
						$("#monitorFlag").val(0);
						$("#setSubReport").text("已订阅");
						$("#submitBtn").html("查看报告");
						$("#cancelBtn").html("留在当前页");
						showMsgInfo(0,"订阅成功！",1);
						$("#submitBtn").click(function(){
							window.location.href=njxBasePath + "/findHotReport.action";
						});
						/*$("#submitBtn").click(function(){
                            window.location.href="${ctx }/view/hotSearch/goSearch.action?myReport=1";
                        });*/
					}else if($("#currentPage").length!=0&&$("#currentPage").val()=="热度分析"&&$("#monitorFlag").val()==1){
						window.clearInterval(intervalId);
						$("#monitorFlag").val(0);
						hotKeywordSet();
					}else if($("#currentPage").length!=0&&$("#currentPage").val()=="热度分析"&&$("#monitorFlag").val()==0 &&$("#ticket").val()!=""){
						window.clearInterval(intervalId);
						$("#myModalHotReportCount .close").click();
						$("#num").val(0);
						confirmHotAnalysis(orderRecordId);
					}else if($("#currentPage").length!=0&&$("#currentPage").val()=="热度分析"&&$("#monitorFlag").val()==0){
						window.clearInterval(intervalId);
//                      $("#myModalHotReportCount .close").click();
//                      $("#num").val(0);
// 						beginQuery();
						//beginQuery(1);
						solidifyQuery(orderRecordId);
						beginQuery();
					}else if (data.redirectPage == 'swa' && (operateLogPageName == null || operateLogPageName!='产品选购')) {
						window.location.href= njxBasePath + "/weiboAnalysis/weiboAnalysisIndex";
					}else if ($("#reviewAnalysisSDK").val() == '评论分析') {
						$("#loadingMaskalert").find(".waiting").text("页面正在跳转。。。");
						window.location.href= njxBasePath + "/view/reviewAnalysis/reviewAnalysisl.shtml";
					}else if($("#heatName").val()==""){
						window.location.href= njxBasePath + "/view/eventAnalysis/taskList.action";
					}else if($("#heatNameWB").val()==""){
						window.location.href= njxBasePath + "/view/weiboEventAnalysis/taskList.action";
					}else if($("#exportPay").val()==1){
						window.clearInterval(intervalId);
						$("#exportPay").val(0);
						exportEXCELTXTCSV(orderRecordId, null, null);
					}else if($("#exportPay").val()==2){
						window.clearInterval(intervalId);
						$("#exportPay").val(0);
						if(data.payRecord.totalFee==0){
							indexExportEXCELTXTCSV(1,orderRecordId);
						}else{
							indexExportEXCELTXTCSV(0,orderRecordId);
						}
					}else if($("#favoritePay").val()==1){
						window.clearInterval(intervalId);
						$("#favoritePay").val(0);
						favoriteExportEXCELTXTCSV(orderRecordId, null, null);
					}else if($("#similarConPay").val()==1){
						window.clearInterval(intervalId);
						$("#similarConPay").val(0);
						if(data.payRecord.totalFee==0){
							similarConExportEXCEL(1);
						}else{
							similarConExportEXCEL(0);
						}
					}else if(operateLogPageName != null && operateLogPageName=='产品选购'){
						$(".pay-buy-page").hide();
						$(".pay-buy-success").show();
					}else{
						window.location.reload(true);
					}
				}else{
					setTimeout("myInterval(" + id + ","+orderRecordId+")", 1000);
				}
			}else{
				setTimeout("myInterval(" + id + ","+orderRecordId+")", 1000);
			}
		}
	});
}


function showBuyPageInfoDiv(type, useCreditNum){
	showBuyPageBgDiv();
	var nowCreditNum = $("#userHaveCreditNum").text();
	var screenWidth =  window.screen.width;
	var clientWidth = document.body.clientWidth;
	var screenAvailWidth = window.screen.availWidth;
	var divH = $(".buyPageInfoDiv").height();
	var divW = $(".buyPageInfoDiv").width();
	var reWidth = (clientWidth - divW)/2;
	var clientHeight = document.body.clientHeight;
	var topH = (clientHeight - divH)/2.5;
	$(".cancelBtn").css({
		display:"",
		margin:"0",
		marginLeft:"5px"
	});
	$(".submitBtn").css({
		display:"",
		margin:"0",
		marginRight:"5px"
	});
	$('.info_close,.cancelBtn').click(function(){
		hideInfoDiv();
	});

	$(".buyPageInfoDiv").css({
		display:"none",
		zIndex:9999,
		top:topH,
		left:reWidth
	});
	if(type == 1){
		$('.now_credit_num').text(nowCreditNum);
		$('.use_credit_num').text(useCreditNum);
		$('#goPayInfoDiv').show();
	}else if(type == 2){
		$('.now_credit_num').text(nowCreditNum);
		$('#creditNotEnoughInfoDiv').show();
	}
}

function showBuyPageBgDiv(){
	var scrollWidth = document.body.scrollWidth;
	var scrollHeight = document.body.scrollHeight;
	$(".buyPageBgDiv").css({
		height:scrollHeight,
		width:scrollWidth,
		display:"block",
		zIndex:9999
	});
}

//隐藏消息弹出层
function hideBuyPageInfoDiv(){
	hideBuyPageBgDiv();
	$(".buyPageInfoDiv").css({
		display:"none"
	});
}

function hideBuyPageBgDiv(){
	$(".buyPageBgDiv").css({
		display:"none"
	});
}

function IsNum(num){
	if(num == null || num == '')
		return false;
	var reNum=/^-?\+?(\d){0,13}$/;
	return(reNum.test(num));
}





