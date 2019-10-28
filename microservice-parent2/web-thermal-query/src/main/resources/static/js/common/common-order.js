//����ҳ֧����ʽ��ʼ��
				function initPayPage(){
					// ��ȡ��¼ƽ̨
					buyJq.ajax({
						url : actionBase + '/view/usercenter/currUserLoginPlatform.action',
						type : 'POST',
						success : function(data) {
							if (!buyJq.isEmptyObject(data)) {
								if (data.loginPlatform == 3) { // ΢����Ӧ�õ�¼������ʾ΢��֧��
									isWbLightAppLogin = true;
									buyJq('.payMode-ul li:not(.payChannel-weibo)').remove();
									buyJq('.payChannel-weibo').show();
								} else if (data.loginType == 2) { // ΢����¼����ʾ΢��֧��
									buyJq('.payChannel-zz').remove();
									buyJq('.payChannel-weibo').remove();
								} else {
									buyJq('.payChannel-weibo').remove();
									buyJq('.payChannel-more').remove();
								}
								buyJq('.payMode-ul').show();
							}
						}
					});
					
					//֧����ʽѡ��
					buyJq('.payMode-ul li.payChannel').click(function(){
						var payChannel = buyJq(this).attr('payChannel');
						buyJq(this).parents('.payMode-ul').find('li').removeClass('active');
						buyJq(this).addClass('active');
						if(payChannel == '3' || payChannel == '4'){
							buyJq(this).parents('.payChannel-more').find('cite').text(buyJq(this).text());
							buyJq(this).parents('li.payChannel-more').addClass('active');
						}else{
							buyJq(this).nextAll(".payChannel-more").find("cite").html('���෽ʽ');
						}
						buyJq(this).parents('.payMode-ul').find('ul.divselect').slideUp("fast");
						var val = buyJq(this).attr('payChannel');
						buyJq('#payChannel').val(val);
						if(val == '0'){
							buyJq('#commonBuyUseCredit').val(true);
						}else{
							buyJq('#commonBuyUseCredit').val(false);
						}
						commonBuyOrderFee();
					});
					
					//������
					buyJq('li.payChannel-more').click(function(){
						var ul = buyJq(this).find('ul.divselect');
						if(ul){
							if(ul.css("display")=="none"){
								ul.slideDown("fast");
							}else{
								ul.slideUp("fast");
							}
						}
					});
				}


				var isWbLightAppLogin = false;
				// ����֧����ʽ
				function initPayChannel(payChannel) {
					if(isWbLightAppLogin){
						payChannel = 4;
					}
					if(!payChannel)
						payChannel = 0;
					if(payChannel == 0){
						buyJq('#commonBuyUseCredit').val(true);
					}else{
						buyJq('#commonBuyUseCredit').val(false);
					}
					buyJq('#payChannel').val(payChannel);
					buyJq('.payMode-ul li.payChannel').each(function() {
						if (payChannel == parseInt(buyJq(this).attr('payChannel'))){
							buyJq(this).parents('.payMode-ul').find('li').removeClass('active');
							buyJq(this).addClass('active');
						}
					});
				}

				/*
					��ʼ�򿪴���
					type��
						1����ⷽ��
						2��ȫ���¼�����
						3��΢���¼�����
						4��������
						5����Ʒ����
						6����ֵ
						7���ײ�
						8������΢������
						9���ȶ��ձ�����
					   10���ȶ��ձ�ʱ��
					   11�����ɴ���
					   12������΢�������Żݰ�
					   13�����ݵ���
					   14�����۷���
					   15��������
					params��
						����
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
				var commonBuyOpenTypeCase = 15;
				var isOpen = true;

				var userHaveCreditNum = 0;
				var useCreditNum = 0;
				var openBuyProductFlag = 0;
				function openBuyCommon(type, params) {
					openBuyProductFlag = type;
					buyJq('#commonBuySelectIds').val('');
					buyJq('#commonBuyFenxiWeiboId').val('');
					buyJq('#commonBuyKeywords').val('');
					buyJq('#commonBuyHeatReportId').val('');
					buyJq('#commonBuyDays').val('');
					buyJq('span[name="commonBuyOrderFeeSpan"]').text('');
					buyJq(".buy-btn").hide();
					
					packageCountTimes = 1;
					isOpen = true;
					var isNeedInitPayChannel = true;
					buyJq('#commonBuyOpenType').val(type);
					setPackageCount(1,true);
					if (buyJq.isEmptyObject(params))
						params = {};
					buyJq('ul[name="commonBuySetCountUL"] > li').each(function(i, n) {	//��ʼ������
						if (buyJq(this).attr('data-cmd') == 1)
							buyJq(this).addClass('active');
						else
							buyJq(this).removeClass('active');
					});
					
					// �򿪴���
					if (type == commonBuyOpenTypeKeyword) {
						buyJq('#commonBuyKeywordBtn').trigger('click');
						
						initKeywordSelect(params.keywordIds, params.keywordType); // ��ʼ����ⷽ��ѡ��
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
							initPayChannel(1);
							isNeedInitPayChannel = false;
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
							var ticket = buyJq('#ticket').val();
							var shareDate = buyJq("#oneTime").text();
							if(ticket != null && ticket != ''){
								params.keywords = params.keywords.replace('}', ',"ticket":"' + ticket + '"}');
							}
							if(shareDate != null && shareDate != ''){
								params.keywords = params.keywords.replace('}', ',"shareDate":"' + shareDate + '"}');
							}
							
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
							
							initHotReportDaysSelect(); // ��ʼ���ȶ��ձ�ʱ��ѡ��
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
					} else if (type == commonBuyOpenTypeCase) {
						buyJq('#commonBuyCaseBtn').trigger('click');
					}
					if(isNeedInitPayChannel){
						initPayChannel(0); // ��ʼ��֧����ʽ
					}
					
					setTimeout(function(){
						commonBuyOrderFee();
					}, 100);
				}

				//��ȡ�û���֧�����Ä�  2֧��    1΢���ֳ�ֵ
				function getUserCoupons(n,num){
					$.ajax({
						url : actionBase + '/view/usercenterV2/selectAllWJFCoupons.action',
						type : 'POST',
						data : {
							'sType' : n,
							'discountsNum':num
						},
						success : function(data) {
							if(data!=null && data.length>0){
								var hm = '';
								hm += '<div class="agree agdis" >';
								hm += '<input type="checkbox" name="" id="" value="" checked="checked" /><span class="txt-left fc_dark_grey">';
								if(n.indexOf("2")>=0){
									hm+='΢����֧�����Ä���</span>';
								}else{
									hm+='΢���ֳ�ֵ���Ä���</span>';
								}
								hm += '<div class="order-quan email">';
								if(n.indexOf("2")>=0){
									hm += '<select size="1" class="input input_color fc_dark_grey inputList float_l mr5" onchange="selectUserCoupon2(this.options[this.options.selectedIndex].value);" id="jcy_select">';
								}else{
									hm += '<select size="1" class="input input_color fc_dark_grey inputList float_l mr5" onchange="selectUserCoupon1(this.options[this.options.selectedIndex].value);" id="jcy_select">';
								}
								
									hm += '<option value="0">��ѡ����Ä�<em class="fc_red"></em></option>';
								for(var i=0;i<data.length;i++){
									var a = (data[i].validEndTime-new Date().getTime())/(24*60*60*1000);
									if(n.indexOf("2")>=0){
										hm += '<option value="'+data[i].userCouponRecordId+'">��'+data[i].minSpentCreditCount+'΢���ּ�'+data[i].discountCreditCount+'(<em class="fc_red">'+Math.ceil(a)+'������</em>)</option>';
									}else{
										hm += '<option value="'+data[i].userCouponRecordId+'">��'+data[i].minSpentAmount+'Ԫ��'+data[i].discountAmount+'Ԫ(<em class="fc_red">'+Math.ceil(a)+'������</em>)</option>';
									}
								}
								hm += '</select>';
								hm += '</div>';
								hm += '</div>';
								$(".wjfSelectID").html(hm);
							}else{
								var hm = '';
								hm += '<div class="agree agdis" >';
								hm += '<input type="checkbox" name="" id="" value="" checked="checked" /><span class="txt-left fc_dark_grey">';
								if(n.indexOf("2")>=0){
									hm+='΢����֧�����Ä���</span>';
								}else{
									hm+='΢���ֳ�ֵ���Ä���</span>';
								}
								hm += '<div class="order-quan email">';
								hm += '<div size="1" class="input input_color fc_dark_grey inputList float_l mr5" style="width: 133px;" id="jcy_select">';
								hm +='<div id="aaa" style="margin-left: 5px;">';
								hm += '���޿��õ��Ä�';
								hm +='</div>';
								hm += '</div>';
								hm += '</div>';
								hm += '</div>';
								$(".wjfSelectID").html(hm);
							}
						}
					});
				}
				
				//ѡ����Ä�
				function selectUserCoupon2(num){
					$("#commonBuyCouponUseID").val(num);
					toGetOrderFee1(2);
				}
				function selectUserCoupon1(num){
					$("#commonBuyCouponUseID").val(num);
					toGetOrderFee1(1);
				}

				// ��ʼ����ⷽ��ѡ��
				function initKeywordSelect(keywordIds, keywordType) {
					if (keywordIds) {
						buyJq('#commonBuySelectIds').val(keywordIds);
						buyJq('.buy-keyword-set-count').hide();
						buyJq('.continue-keyword-set-count').show();
					} else {
						buyJq('#commonBuySelectIds').val('');
						buyJq('.continue-keyword-set-count').hide();
						buyJq('.buy-keyword-set-count').show();
					}
					buyJq("#keywordExtendType").text("���꣩");
					buyJq('#keywordPackagesUL > li').each(function(i) {
						if (i == 0) {
							buyJq(this).addClass('active');
						} else {
							buyJq(this).removeClass('active');
						}
					});
					buyJq('#commonBuyId').val(buyJq('#commonBuyKeywordFirstId').val());
					
					if(buyJq('#proDl').hasClass('pro-y')){
						buyJq('#buyMaxKeywordSize').text(500);
					}else{
						if (keywordIds) {
							if(keywordType == 2)
								buyJq('#buyMaxKeywordSize').text(500);
							else
								buyJq('#buyMaxKeywordSize').text(50);
						}else{
							buyJq('#buyMaxKeywordSize').text(50);
						}
					}
				}
				var packageCountTimes = 1;
				//
				function setPackageCountTimes(obj, num){
					buyJq(obj).parent('ul').find('li').removeClass('active');
					buyJq(obj).addClass('active');
					var c = parseInt(buyJq('#commonBuyCount').val())/packageCountTimes;
					packageCountTimes = num;
					setPackageCount(c);
				}


				// ��ʼ���ȶ��ձ�ʱ��ѡ��
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

				//��ȡ��֧�����
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
						url : actionBase + '/view/pay/getOrderFee.action',
						type : 'POST',
						cache : 'false',
						data : $('#commonBuyForm').serialize(),
						success : function(data) {
							if (!$.isEmptyObject(data)) {
								timestamp = $("#commonBuyTimeFlag").val();
								if(timestamp != data.timeFlag){	//�ж��Ƿ��ǵ�ǰ���
									return false;
								}
								showBuyBtn();
								userHaveCreditNum = data.userCreditNum;
								useCreditNum = data.creditAmountFee;
								
								if(data.discountFee > 0){
									$('span[name="commonBuyDiscountFeeSpan"]').text(data.discountFee.toFixed(2) + 'Ԫ');
									$('.discountFeeSpan').show();
								}else{
									$('.discountFeeSpan').hide();
								}
								if($("#commonBuyUseCredit").val() == 'true'){
									if((useCreditNum) < 10000){
										$('.payMode-ul .payChannel:not([payChannel="0"])').hide();
									}else{
										$('.payMode-ul .payChannel:not([payChannel="0"])').show();
									}
									$(".wjfSelectID").show();
									$('span[name="commonBuyTotalFeeSpan"]').text(useCreditNum + '΢����');
									if(useCreditNum > userHaveCreditNum){	//΢���ֲ���
										//showBuyPageInfoDiv(2, 0, userHaveCreditNum);
										$("#userHaveCreditNum").text(userHaveCreditNum);
										$(".buy-btn").hide();
										$(".go-buy-credit-btn").show();
									}else{
										$(".go-buy-credit-btn").hide();
										$(".buy-btn").show();
									}
									$('span[name="commonBuyOrderFeeSpan"]').text(useCreditNum + '΢����');
									var sr;
									if(openBuyProductFlag==1){
										sr="2,103";
									}else if(openBuyProductFlag==2){
										sr="2,104";
									}else if(openBuyProductFlag==3){
										sr="2,105";
									}else if(openBuyProductFlag==5){
										sr="2,106";
									}else if(openBuyProductFlag==8){
										sr="2,107";
									}else if(openBuyProductFlag==14){
										sr="2,108";
									}else if(openBuyProductFlag==13){
										sr="2,109";
									}else if(openBuyProductFlag==9){
										sr="2,110";
									}else if(openBuyProductFlag==4){
										sr="2,112";
									}else{
										sr="2";
									}
									getUserCoupons(sr,useCreditNum);
								}else{
									if((data.totalFee*100) < 10000){
										$('.payMode-ul .payChannel:not([payChannel="0"])').hide();
										$('.payMode-ul .payChannel[payChannel="0"]').click();
									}else{
										$('.payMode-ul .payChannel:not([payChannel="0"])').show();
									}
									$(".wjfSelectID").hide();
									$(".go-buy-credit-btn").hide();
									$(".buy-btn").show();
									$('span[name="commonBuyTotalFeeSpan"]').text(data.notDiscountFee.toFixed(2) + 'Ԫ');
									$('span[name="commonBuyOrderFeeSpan"]').text(data.totalFee.toFixed(2) + 'Ԫ');
									getUserCoupons(1,data.totalFee);
								}
							}
						}
					});
				}
				function toGetOrderFee1(n) {
					hideBuyBtn();
					var timestamp = (new Date()).valueOf();
					$("#commonBuyTimeFlag").val(timestamp);
					$.ajax({
						url : actionBase + '/view/pay/getOrderFee.action',
						type : 'POST',
						cache : 'false',
						data : $('#commonBuyForm').serialize(),
						success : function(data) {
							if (!$.isEmptyObject(data)) {
								timestamp = $("#commonBuyTimeFlag").val();
								if(timestamp != data.timeFlag){	//�ж��Ƿ��ǵ�ǰ���
									return false;
								}
								showBuyBtn();
								userHaveCreditNum = data.userCreditNum;
								useCreditNum = data.creditAmountFee;
								
								if(data.discountFee > 0){
									$('span[name="commonBuyDiscountFeeSpan"]').text(data.discountFee.toFixed(2) + 'Ԫ');
									$('.discountFeeSpan').show();
								}else{
									$('.discountFeeSpan').hide();
								}
								if($("#commonBuyUseCredit").val() == 'true'){
									$(".wjfSelectID").show();
									$('span[name="commonBuyTotalFeeSpan"]').text(useCreditNum + '΢����');
									if(useCreditNum > userHaveCreditNum){	//΢���ֲ���
										//showBuyPageInfoDiv(2, 0, userHaveCreditNum);
										$("#userHaveCreditNum").text(userHaveCreditNum);
										$(".buy-btn").hide();
										$(".go-buy-credit-btn").show();
									}else{
										$(".go-buy-credit-btn").hide();
										$(".buy-btn").show();
									}
									$('span[name="commonBuyOrderFeeSpan"]').text(useCreditNum + '΢����');
								}else{
									if(n==2){
										$(".wjfSelectID").hide();
									}
									$(".go-buy-credit-btn").hide();
									$(".buy-btn").show();
									$('span[name="commonBuyTotalFeeSpan"]').text(data.notDiscountFee.toFixed(2) + 'Ԫ');
									$('span[name="commonBuyOrderFeeSpan"]').text(data.totalFee.toFixed(2) + 'Ԫ');
								}
							}
						}
					});
				}

				//�Ƴ�����ť����¼�
				function hideBuyBtn() {
					$(".commonBuyBtn").removeAttr("onclick");
				}

				// ��ӹ���ť����¼�
				function showBuyBtn() {
					$(".commonBuyBtn").attr("onclick", "goCommonBuy()");
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
					showMsgInfo(0,"�ף������ظ��ύŶ!",0);
				}


				//֧��
				var commonBuyFlag = 0;
				var intervalId;
				function goCommonBuy(n){
					var type = $('#payChannel').val();
					/*if(operateLogPageCode == '1023' && type != 0)	//��Ʒ����ҳ��ֹ��ε��
						$(".commonBuyBtn").attr("onclick", "stopMoreClick()");*/
					var type = $('#payChannel').val();
					if(type == 0){
						$('#confirm-payment-credit-btn').click(function(){
							if(openBuyProductFlag==9){
								hideEventDiv();
							}
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
						if (type != 3 && orderFee.indexOf('Ԫ') > 0 && parseFloat(orderFee.replace('Ԫ', '')) > 0)
							newTab = window.open('about:blank');
						$.ajax({
							url : actionBase + '/view/pay/goPayV3.action',
							type : 'POST',
							data : $('#commonBuyForm').serialize(),
							success : function(data) {
								if (!$.isEmptyObject(data)) {
									if (data.orderInfo.totalFee == 0) {
										$.ajax({
											url : actionBase + '/view/pay/doPayEnd.action',
											type : 'POST',
											data : {
												'payRecord.innerTradeNo' : data.payInfo.innerTradeNo
											},
											success : function(data){}
										});
										setTimeout("myInterval(" + data.payInfo.payRecordId + ","+ data.orderInfo.orderRecordId + ")", 1000);//1000Ϊ1����
									} else {
										$('#commonBuyCurrentAdminCreditAmount').val(parseInt($('#commonBuyCurrentAdminCreditAmount').val()) - data.useCreditAmount);
						
										if (type == 1) // ֧����֧��
											url = actionBase + "/view/pay/goPay.action?payRecord.payRecordId=" + data.payInfo.payRecordId;
										else if (type == 2) // ΢��֧��
											url = actionBase + "/view/pay/goWeixinPay.action?payRecord.payRecordId=" + data.payInfo.payRecordId + "&order.orderNo=" + data.orderInfo.orderNo;
										else if (type == 3) // ����֧��
											url = actionBase + "/view/pay/goOffLinePay.action";
										else if (type == 4) // ΢��֧��
											url = actionBase + "/view/pay/goSinaPay.action?payRecord.payRecordId=" + data.payInfo.payRecordId;
										else
											return false;
						
										if (type == 3) {
											sendPostForm(url, '_self', {'order.orderNo' : data.orderInfo.orderNo, 'order.totalFee' : data.orderInfo.totalFee});
											
										} else {
											
											newTab.location.href = url;
											if(!n||n!=1){
												setTimeout("myInterval(" + data.payInfo.payRecordId + ","+data.orderInfo.orderRecordId+")", 1000);//1000Ϊ1����
											}
										}
									}
						
									$('.modal-header button.close').trigger('click');
								}
							}
						});
					}
				}

				function myInterval(id,orderRecordId) {
					$.ajax({
						type:"post",
						url: actionBase + "/view/user/checkOrderStatus.action?payRecord.payRecordId="+id,
						cache:false,
						success: function(data){
							if (!$.isEmptyObject(data)) {
								commonBuyFlag = 0;
								if (data.payRecord.payStatus == 1) {
									//�����⹺�����
									if(buyJq("#caseBuySuccess").val()=="0000"){
										caseBuySuccess();
										return;
									}
									
									//֧�������ת
									if(openBuyProductFlag == commonBuyOpenTypeKeyword){		//��ⷽ��
										window.location.href=actionBase + "/keyword.shtml";
									}else if(openBuyProductFlag == commonBuyOpenTypeExportData && $("#exportPay").val()==1){	//����б���
				                        window.clearInterval(intervalId);
				                        $("#exportPay").val(0);
				                        exportEXCELTXTCSV(orderRecordId, null, null);
				                    }else if(openBuyProductFlag == commonBuyOpenTypeExportData && $("#favoritePay").val()==1){	//�ղؼе���
				                        window.clearInterval(intervalId);
				                        $("#favoritePay").val(0);
				                        favoriteExportEXCELTXTCSV(orderRecordId, null, null);
				                    }else if($("#typeOfEventId").val() == "1"){
										setTimeout(function(){goTypeOfEvent("1");},2000);
				                    }else if($("#typeOfEventId").val() == "2"){
										setTimeout(function(){goTypeOfEvent("2");},2000);
				                    }else if($("#heatName").length!=0&&$("#heatName").val()!=""){
				                        window.clearInterval(intervalId);
				                        goCreate();
				                    }else if($("#heatNameWB").length!=0&&$("#heatNameWB").val()!=""){
				                    	window.clearInterval(intervalId);
				                    	goCreate();
				                    }else if($("#currentPage").length!=0&&$("#currentPage").val()=="�����ȶȱ���"){
				                    	window.clearInterval(intervalId);
				                        showMsgInfo(0,"���ĳɹ�!",0);
				                        $("#submitBtn").one('click',function(){
				                        	window.location.href=actionBase + "/findHotReport.action";
				                        });
				                        //setTimeout(function(){hideInfoDiv();window.location.reload(true);},2000);
				                    }else if($("#currentPage").length!=0&&$("#currentPage").val()=="�ȶȷ���"&&$("#monitorFlag").val()==2){
				                        window.clearInterval(intervalId);
				                        $("#monitorFlag").val(0);
				                        $("#setSubReport").text("�Ѷ���");
				                        $("#submitBtn").html("�鿴����");
				                        $("#cancelBtn").html("���ڵ�ǰҳ");
				                        showMsgInfo(0,"���ĳɹ���",1);
				                        $("#submitBtn").one('click',function(){
				                            window.location.href=actionBase + "/findHotReport.action";
				                        });
				                        /*$("#submitBtn").one('click',function(){
				                            window.location.href="${ctx }/view/hotSearch/goSearch.action?myReport=1";
				                        });*/
				                    }else if($("#currentPage").length!=0&&$("#currentPage").val()=="�ȶȷ���"&&$("#monitorFlag").val()==1){
				                        window.clearInterval(intervalId);
				                        $("#monitorFlag").val(0);
				                        hotKeywordSet();
				                    }else if($("#currentPage").length!=0&&$("#currentPage").val()=="�ȶȷ���"&&$("#monitorFlag").val()==0 &&$("#ticket").val()!=""){
				                        window.clearInterval(intervalId);
				                        $("#myModalHotReportCount .close").click();
				                        $("#num").val(0);
				                        confirmHotAnalysis(orderRecordId);
				                    }else if($("#currentPage").length!=0&&$("#currentPage").val()=="�ȶȷ���"&&$("#monitorFlag").val()==0){
				                    	 window.clearInterval(intervalId);
				                         $("#myModalHotReportCount .close").click();
				                         $("#num").val(0);
				                         confirmHotAnalysis(orderRecordId);
				                    }else if ($("#reviewAnalysisSDK").val() == '���۷���') {
				                    	confirmReview($("#reviewAnalysisTicket").val());	
//				                    	goPayMoney($("#reviewAnalysisId").val());
//				                        $("#loadingMaskalert").find(".waiting").text("ҳ��������ת������");
//				                        window.location.href= actionBase + "/view/reviewAnalysis/reviewAnalysisl.shtml";
				                    }else if (data.redirectPage == 'swa' && (operateLogPageName == null || operateLogPageName!='��Ʒѡ��')) {
				                    	window.location.href= actionBase + "/i/singleWeiboAnalysis/index.shtml";
				                    }else if($("#heatName").val()==""){
				                        window.location.href= actionBase + "/view/eventAnalysis/taskList.action";
				                    }else if($("#heatNameWB").val()==""){
				                        window.location.href= actionBase + "/view/weiboEventAnalysis/taskList.action";
				                    }else if(operateLogPageName != null && operateLogPageName=='��Ʒѡ��'){
				                        $(".pay-buy-page").hide();
				                        $(".pay-buy-success").show();
				                    }else if($("#recordName").length!=0&&$("#recordName").val()!=""){
				                        window.clearInterval(intervalId);
//				                      $("#loginRecordForm").submit();
				                        goAnalysisStart();
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

				//��ʾ���е�΢����֧������ 1��΢�����㹻 2΢���ֲ���
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
				    $(".cancelBtn-order").css({
				        display:"",
				        margin:"0",
				        marginLeft:"5px"
				    });
				    $(".submitBtn-order").css({
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

				//������Ϣ������
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