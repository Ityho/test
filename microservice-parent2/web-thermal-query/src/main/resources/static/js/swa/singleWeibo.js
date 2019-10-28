$(function(){
	var fllowsCount=0;//粉丝数
	var fire1 = "";var fire2="";
	var paramFlag = false;
	var time1 = "";
});
// 获取微博内容
function weiboTaskResultStatus() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultStatus.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.status != null) {
					time1 = new Date(result.status.statusCreatedAt.split('-').join('/')).format('MM-dd hh:mm');
					$('#weiboContentUserHead').attr('src', result.status.user.userLogoUrl);
					$('#weiboContentUserHead').click(function() {
						window.open(result.status.user.pageUrl);
					});
					if (result.status.user.userVerifiedType == 0) {
						$('#weiboContentUserHead').parent('div').addClass('v_yellow');
					} else if(result.status.user.userVerifiedType >= 1 && result.status.user.userVerifiedType <= 7) {
						$('#weiboContentUserHead').parent('div').addClass('v_blue');
					}
					$('#weiboContentUserNickname').html('<a class="mscrame" href="javascript:;" onclick="javascript:window.open(\'' + result.status.user.pageUrl + '\');" target="_blank">' + result.status.user.userScreenName + '</a>');
					shareOriginalUser = result.status.user.userScreenName;
					if(result.status.statusText.length>140){
						$('#weiboContentPic').html('<p>' + result.status.statusText.substring(0,140)+'......' + '</p>');
					}else{
						$('#weiboContentPic').html('<p>' + result.status.statusText + '</p>');
					}
					var statusCreatedAt = result.status.statusCreatedAt.split('-').join('/');
					$('#weiboContentTime').prepend('<span> <a class="fc_gray link_yellowHover" style="cursor: pointer;" onclick="javascript:window.open(\'' + result.status.url + '\');">' + new Date(statusCreatedAt).format('yyyy-MM-dd hh:mm') + '</a></span>');
					$('#weiboContentForwardNumber').text('转发数(' + result.status.statusRepostsCount + ')');
					$('#weiboContentCommentNumber').text('评论数(' + result.status.statusCommentsCount + ')');
					$('#weiboContentPraiseNumber').text('点赞数(' + result.status.statusAttitudesCount + ')');
					$('#weiboContentReadsNumber').text('阅读数(' + result.status.statusReadsCount + ')');

					var content = result.status.statusText;
					if ( content!= null && content != '') {
						content = content.replace(/<[^>]+>/g,"")
						if (content.length > 30){
							$("#shareTitle").val(content.substring(0, 30) + '...');
							document.getElementsByTagName("meta")[0].content=content.substring(0, 30);
						}
					}
					shareWeiboContent = "@"+result.status.user.userScreenName+" 的微博共收获转发"+format_number(result.status.statusRepostsCount)+"次、 评论"+format_number(result.status.statusAttitudesCount)+"条，点赞"+format_number(result.status.statusCommentsCount)+"个";
					$("#shareContent").val(shareWeiboContent);

					shareReportsCount = result.status.statusRepostsCount;
					shareCommentCount = result.status.statusCommentsCount;
					sharePriseCount = result.status.statusAttitudesCount;
					shareReadsCount = result.status.statusReadsCount;

					fllowsCount = result.status.user.userFollowersCount;
					//总说明
					document.getElementById("analysis_time").innerHTML = new Date(new Date(($("#anlysisTime").val()).split('-').join('/')).getTime()).format("MM-dd hh:mm");
					document.getElementById("transNum").innerHTML = format_number(result.status.statusRepostsCount);
					document.getElementById("likeNum").innerHTML = format_number(result.status.statusAttitudesCount);
					document.getElementById("comment").innerHTML = format_number(result.status.statusCommentsCount);
					document.getElementById("bloger").innerHTML = result.status.user.userScreenName;

					if (result.status.statusPicList != null && result.status.statusPicList.length > 0) {
						var html = '<div class="preview-img" data-count="' + result.status.statusPicList.length + '">';
						if (result.status.statusPicList.length > 1)
							html += '	<ul class="feed-img multi-attachment" style="display: block;">';
						else
							html += '	<ul class="feed-img thumb-zoom" style="display: block;">';
						$.each(result.status.statusPicList, function(i, n) {
							if (i == 0) {
								$('#wxSharePic').attr('src', n.replace('thumbnail', 'large'));
								sharePic = n.replace('thumbnail', 'large');
							}
							html += '		<li>';
							html += '			<img src="' + n + '" data-index="' + i + '" class="zoom-move thumb-zoom" data-images=\'{"zoom_img": "' + n.replace('thumbnail', 'bmiddle') + '", "origin_img":"' + n.replace('thumbnail', 'large') + '"}\'/>';
							html += '		</li>';
						});
						html += '		</ul>';
						html += '	</div>';

						$('#weiboContentPic').append(html);
					} else {
						$('#wxSharePic').attr('src', result.status.user.userLogoUrl);
						sharePic = result.status.user.userLogoUrl;
					}
					$('#weiboContentButtomTime,#weiboContentNote').css('display', 'block');

					shareReadyA = true;
					paramFlag = true;
				} else {
					setTimeout('weiboTaskResultStatus()', 1000);
				}
			} else {
				setTimeout('weiboTaskResultStatus()', 1000);
			}
		}
	});
}

// 获取微博分析状态
function weiboTaskStatus() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskStatus.action',
		type : 'POST',
		data : {
			'taskTicket' : $('#taskTicket').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.analysisStatus == 0) {
					alertMsg(0, '微博分析失败，请确认微博链接是否正确！', 0);
				} else {
					if (result.analysisStatus == 3) {
						$('#currentTaskTier').val(result.finishedTier);
						reloadWeiboTask();
					}
				}
			}
		}
	});
}

// 微博分析
function reloadWeiboTask() {
	weiboTaskResultBaseIndex(); // 微博指数
	weiboTaskResultStar(); // 传播节点
	weiboTaskResultTier(); // 转发层级
//	weiboTaskResultLine(); // 转发评论曲线
	weiboTaskResultKeyUser(); // 传播关键用户
	weiboTaskResultKeyUserRoad(); // 关键用户传播路径
	weiboTaskResultKeyUserTop(); // 引爆点
	weiboTaskResultFace(); // 转发评论表情分析
	weiboTaskResultVerifyUser(); // 影响力TOP10
	weiboTaskResultLocation(); // 转发评论地域分析
	weiboTaskResultGender(); // 转发评论性别分析
	weiboTaskResultUserTag(); // 转发评论用户兴趣标签
	weiboTaskResultFans(); // 转发评论粉丝分布
	weiboTaskResultSensitive(); // 敏感度分析
	weiboTaskResultHotWords(); // 转发评论热词
	weiboTaskResultSource(); // 发布设备来源
	weiboTaskResultViewPoint(); // 网友观点分析

	document.getElementById("all_dec_div").style.display="block";
}

//微博指数
function weiboTaskResultBaseIndex() {
	$('#oneWeiboSpreadChart').html('<img src="' + staticResourcePath + '/images/loading.gif" style="width:16px; height:16px;" />');
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultBaseIndex.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			result = $.parseJSON(result);
			if (!$.isEmptyObject(result)) {
				var compreInfIndex = 0;
				if ($('#markType').val() == 1){
					compreInfIndex = result.iContentCommonNetList[0].weiboIndex.toFixed(2);
				} else {
					if (!$.isEmptyObject(result.summary) && !$.isEmptyObject(result.summary.indexInfo)){
						compreInfIndex = result.summary.indexInfo.compreInfIndex.toFixed(2);
					}
				}
				var oneWeiboSpreadChart = echarts.init(document.getElementById('oneWeiboSpreadChart'));
				// 指定图表的配置项和数据
				option = {
					title: {
						x: "center",
						bottom: '5%',
						text: '单条微博传播指数：' + compreInfIndex,
						textStyle: {
							fontSize: 16,
							fontStyle: 'normal',
							color: '#e0521e'
						}
					},
					tooltip : {
						formatter: "{a} <br/>{b} : {c}%"
					},
					series: [{
						name: '单条微博传播指数',
						type: 'gauge',
						radius: '69%',
						splitLine:{show: false},
						axisTick:{show: false},
						axisLine: {
							width: 20,
							show: false,
							lineStyle: {
								width:0,
								shadowBlur: 0
							}
						},
						axisLabel:{show: false},
						pointer:{show: false},
						itemStyle:{
							normal:{
								color: '#e0521e'
							}
						},
						detail: {
							show: true,
							formatter:'{value}',
							offsetCenter: [0, -20],
							width: 50,
							textStyle:{color: '#e0521e',fontSize: 70},
						},
						data: [{value: compreInfIndex}],
						silent: true,
						z: 3
					},
						{
							tooltip: {show: false},
							name:'主体颜色',
							type: 'pie',
							radius: ['57%', '68%'],
							center: ['50%', '45%'],
							hoverAnimation: false,
							startAngle: 360,
							labelLine: {
								normal: {show: false}
							},
							label: {normal: {position: 'center'}},
							data: [{
								value: 75,
								itemStyle: {
									"normal": {
										"color": new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
											"offset": 0,
											"color": '#f1611a'
										}, {
											"offset": 1,
											"color": '#f3a43c'
										}]),
									}
								},
							}],
							z: 1
						},
						{
							tooltip: {show: false},
							name:'背景颜色',
							type: 'pie',
							radius: ['50%', '75%'],
							center: ['50%', '45%'],
							hoverAnimation: false,
							startAngle: 360,
							labelLine: {
								normal: {show: false}
							},
							label: {normal: {position: 'center'}},
							data: [{
								value: 75,
								itemStyle: {
									"normal": {
										"color":'rgba(0,0,0,0.05)'
									}
								},
							}],
							z: 0
						}]
				};

				// 使用刚指定的配置项和数据显示图表。
				oneWeiboSpreadChart.setOption(option);

				//重新定义所有图表自适应容器大小
				setTimeout(function() {
					window.onresize = function() {
						oneWeiboSpreadChart.resize();
					}
				});
			}
		}
	});
}

// 传播节点
var weiboTaskResultStarResult;
var weiboTaskResultStarResultEnd = true;
function reDrawWeiboTaskResultStar() {
	if (!weiboTaskResultStarResultEnd)
		return false;

	if (weiboTaskResultStarResult != null) {
		var maxLevel = 0;
		$.each(weiboTaskResultStarResult.graph.nodes(), function(i, n) {
			if (n.level > maxLevel)
				maxLevel = n.level;
		});

		if (maxLevel > 0) {
			weiboTaskResultStarResultEnd = false;
			var filter = new sigma.plugins.filter(weiboTaskResultStarResult);
			filter.undo('min-degree').nodesBy(function(n) {
				return n.level == 0;
			}, 'min-degree').apply();

			var v = 1;
			var _t = setInterval(function(){
				filter
					.undo('min-degree')
					.nodesBy(function(n) {
						return n.level <= v;
					}, 'min-degree')
					.apply();

				v++;
				if (v > maxLevel) {
					clearInterval(_t);
					weiboTaskResultStarResultEnd = true;
				}
			}, 1000);
		}
	}
}
function weiboTaskResultStar() {
	$('#weibo_task_result_star_content_div').html('<img src="' + staticResourcePath + '/images/loading.gif" style="width:16px; height:16px;" />');
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultStar.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			$('#weibo_task_result_star_content_div').empty();
			sigma.parsers.gexf(result, {
				renderer: {
					container: document.getElementById('weibo_task_result_star_content_div'),
					type: 'canvas'
				},
				settings: {
					maxEdgeSize: 0.3,
					defaultEdgeType: 'curve',
					zoomMin: 0
				}
			}, function(s) {
				weiboTaskResultStarResult = s;
				reDrawWeiboTaskResultStar();
			});
		}
	});
}

// 转发层级
function weiboTaskResultTier() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultTier.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var html = '';
				$('.table_list.table_list_h5').empty();
				var decHtml = "";
				var max_tran = 1;
				for(var i=1;i<result.transTierList.length;i++){
					if(result.transTierList[max_tran].tierInfo.repostsUserCount<result.transTierList[i].tierInfo.repostsUserCount)
						max_tran = i;
				}
				$.each(result.transTierList, function(i, n) {
					if (i == 0) { // 总
						$('#tierSpan').text(result.transTierList.length - 1);
						$('#tierRepostsSpan').text(n.tierInfo.repostsUserCount);
						$('#tierCoverUserSpan').text(n.tierInfo.repostsCoverUserCount);

						//博文概况--有效转发
						document.getElementById("validNum_li_val").innerHTML=format_number(n.tierInfo.repostsUserCount)+'次';
						document.getElementById("validNum_li").style.display="block";
						//博文概况--覆盖人次
						document.getElementById("fllowsCount_li_val").innerHTML=format_number(fllowsCount+n.tierInfo.repostsCoverUserCount)+'人';
						document.getElementById("fllowsCount_li").style.display="block";
						//总说明
						document.getElementById("repostsUserCount").innerHTML=format_number(n.tierInfo.repostsUserCount);

						decHtml = "微博在传播中共形成"+(result.transTierList.length - 1)+"个转发层级，经微博反垃圾系统处理后，剩余有效转发数"+n.tierInfo.repostsUserCount+"条，覆盖微博用户"+format_number(n.tierInfo.repostsCoverUserCount)+"人；";

						$("#wxShareContent").val("微博在传播中共形成"+(result.transTierList.length - 1)+"个转发层级，覆盖微博用户"+format_number(n.tierInfo.repostsCoverUserCount)+"人。。。");
						document.getElementsByTagName("meta")[0].content="微博在传播中共形成"+(result.transTierList.length - 1)+"个转发层级，覆盖微博用户"+format_number(n.tierInfo.repostsCoverUserCount)+"人。。。";
					} else { // 分层级
						if (i > 4)
							html += '	<li style="display: none;">';
						else
							html += '	<li>';
						html += '		<div class="level_tit">';
						html += '			<div>第 ' + i + ' 层</div>';
						html += '			<div>';
						html += '				<font>转发数：</font>' + n.tierInfo.repostsUserCount;
						html += '			</div>';
						html += '			<div>';
						html += '				<font>转发占比</font>' + n.tierInfo.repostsProportion.toFixed(2) + '%';
						html += '			</div>';
						html += '			<div>';
						html += '				<font>覆盖人数：</font>' + n.tierInfo.repostsCoverUserCount;
						html += '			</div>';
						html += '		</div>';
						html += '		<div class="mwblist">';
						$.each(n.userInfoList, function(i1, n1) {
							if (i1 == 3) {
								html += '	<div class="m_hide">';
							}
							if (i1 % 3 == 0) {
								html += '	<ul>';
							}
							html += '	<li>';
							if (n1.userVerifiedType == 0) {
								html += '	<div class="tx v_yellow">';
							} else if(n1.userVerifiedType >= 1 && n1.userVerifiedType <= 7) {
								html += '	<div class="tx v_blue">';
							} else {
								html += '	<div class="tx">';
							}
							html += '			<img src="' + n1.userLogoUrl + '" style="cursor: pointer;" onclick="javascript:window.open(\'' + n1.pageUrl + '\');" />';
							html += '		</div>';
							if (n1.userScreenName == null)
								n1.userScreenName = '';
							html += '		<p class="mscrame" title="' + n1.userScreenName + '" style="cursor: pointer;" onclick="javascript:window.open(\'' + n1.pageUrl + '\');">' + n1.userScreenName.substring(0, 5);
							if (n1.userScreenName.length > 5)
								html += '...';
							html += '		</p>';
							html += '		<p class="fz12">转发：' + n1.statusRepostsCount + '</p>';
							html += '	</li>';
							if ((i1 + 1) % 3 == 0 || i1 == (n.userInfoList.length - 1)) {
								html += '	</ul>';
							}
							if (i1 >= 3 && i1 == (n.userInfoList.length - 1)) {
								html += '	</div>';
							}
						});
						html += '		</div>';
						if (n.userInfoList.length > 3) {
							html += '	<a class="arrow" href="javascript:void(0)" title="更多"></a>';
						}
						html += '	</li>';
					}
					if(i == max_tran)
						decHtml += "第"+i+"层级转发者共转发"+n.tierInfo.repostsUserCount+"次，占总转发数的"+n.tierInfo.repostsProportion.toFixed(2)+"%，是微博传播的主要力量。";
				});
				$('#level_dec').empty();
				$('#level_dec').html('<div class="well fz14" style="margin-bottom: 0;">'+decHtml+'</div>');
				$('.table_list.table_list_h5').append(html);

				if (result.transTierList.length < 6)
					$('.more').css('display', 'none');

				$(".arrow").on("click",function(){
					if(!$(this).hasClass("rotate90")){
						$(this).prev().find(".m_hide").show(300);
						$(this).addClass('rotate90');
					}else{
						$(this).prev().find(".m_hide").hide(300);
						$(this).removeClass('rotate90');
					}
				});
			}
		}
	});
}

var keyUser = '';
var keyUserCount = 0;
var keyUserTop;

// 转发评论曲线
function weiboTaskResultLine() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultLine.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var bubbleHide;
				var lineHide;
				var interval;
				var maxLegendSize = isMobile ? 4 : 9;
				if (result.legend.length > maxLegendSize)
					interval = ~~ (result.legend.length / maxLegendSize);
				else
					interval = 1;
				var timebr =[];
				$.each(result.legend, function(i, n) {
					timebr.push(n);
				});
				$("#weibo_task_result_line_content_div").highcharts({
					title: {
						text: null
					},
					xAxis: {
						categories: timebr,
						tickInterval: interval,
						lineWidth: 1,
						labels: {
							formatter: function() {
								if (typeof this.value == 'string')
									return new Date(this.value.split('-').join('/')).format('MM-dd hh:mm').replace(' ', '<br />');
							}
						}
					},
					yAxis: {
						min: 0,
						gridLineWidth: 1,
						gridLineColor: "rgba(150,150,150,0.8)",
						title: {
							text: null
						},
					},
					legend: {
						enabled: false
					},
					tooltip: {
						shared: true,
						formatter: function() {
							var _this = this;
							if (_this.points) {
								var v = _this.x;

								for (var i = 0, l = _this.points.length; i < l; i++)
									v += '<br/>' + _this.points[i].series.name + ' : ' + _this.points[i].y;

//		                		v += '<br/>关键转发者：' + keyUser + '<br/>带动转发：' + keyUserCount;

								return v;
							} else {
								var users = [];
								var value;
								var user;
								$.each(keyUserTop, function(i, n){
									if (n.userId == _this.point.userId)
										user = n;
								});
								if (user != null) {
									return '用户昵称：' + user.userScreenName + '<br />'
										+ '二次转发：' + user.statusRepostsCount + '<br />'
										+ '转发时间：' + user.statusCreatedAt;
								} else {
									return '';
								}
							}
						}
					},
					plotOptions: {
						area: {
							lineWidth: 2,
							marker: {
								radius: 2.5,
								enabled: true
							},
							fillOpacity: 0.3
						},
						bubble: {
							minSize: 7,
							maxSize: 25
						}
					},
					series: [{
						name: "转发",
						type: "area",
						color: "#ec6a3a ",
						data: result.repostsList
					},{
						name: "评论",
						type: "area",
						color: "#277bc0",
						data: result.commentsList
					}, {
						name: "关键点",
						type: "bubble",
						color: "#f29300",
						data: (function () {
							var d = [];
							var legendHour = [];
							var legendDay = [];
							$.each(result.legend, function(i, n) {
								n = n.split('-').join('/');
								legendHour.push(new Date(n).format("yyyy-MM-dd hh:00:00"));
								legendDay.push(new Date(n).format("yyyy-MM-dd 00:00:00"))
							});
							$.each(keyUserTop, function(i, n) {
								var value = n.statusCreatedAt.split('-').join('/');

								var obj = {};
								obj.name = 'name' + i;
								obj.x = legendHour.indexOf(new Date(value).format("yyyy-MM-dd hh:00:00"));
								if (obj.x == -1)
									obj.x = legendDay.indexOf(new Date(value).format("yyyy-MM-dd 00:00:00"));
								if (obj.x == -1)
									obj.x = 1;
								var min = 0, max = result.repostsList[obj.x];
								obj.y = Math.floor(min + Math.random() * (max - min));
								obj.z = n.statusRepostsCount;
								obj.userId = n.userId;

								d.push(obj);
							});
							return d;
						})()
					}]
				});
				//添加说明部分
				if(result.mostValueList[0]!=null&&result.mostValueList[1]!=null){
					if(new Date(result.mostValueList[0].x).getTime() == new Date(result.mostValueList[1].x).getTime())
						fire2 = "<p>该微博于"+time1+"发布后，于"+new Date(result.mostValueList[0].x.split('-').join('/')).format("MM-dd hh:mm")+"达到转发、评论高峰，转发峰值"+result.mostValueList[0].y+"条、评论峰值"+result.mostValueList[1].y+"条，此后微博传播速度逐渐降低。</p>";
					else
						fire2 = "<p>该微博于"+time1+"发布后，于"+new Date(result.mostValueList[0].x.split('-').join('/')).format("MM-dd hh:mm")+"、"+new Date(result.mostValueList[1].x.split('-').join('/')).format("MM-dd hh:mm")+"达到转发、评论高峰，转发峰值"+result.mostValueList[0].y+"条、评论峰值"+result.mostValueList[1].y+"条，此后微博传播速度逐渐降低。</p>";
				}else if(result.mostValueList[0]!=null){
					fire2 = "<p>该微博于"+time1+"发布后，于"+new Date(result.mostValueList[0].x.split('-').join('/')).format("MM-dd hh:mm")+"达到转发高峰，转发峰值"+result.mostValueList[0].y+"条，此后微博传播速度逐渐降低。</p>";
				}else{
					fire2 = "<p>该微博于"+time1+"发布后，于"+new Date(result.mostValueList[1].x.split('-').join('/')).format("MM-dd hh:mm")+"达到评论高峰，评论峰值"+result.mostValueList[1].y+"条，此后微博传播速度逐渐降低。</p>";
				}
				var decHtml ='<div class="well fz14" style="margin-bottom: 0;">'+fire2+'</div>';
				$('#line_dec').empty();
				$('#line_dec').html(decHtml);
			}
		}
	});
}

// 传播关键用户
function weiboTaskResultKeyUser() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultKeyUser.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result) && result.user != null) {
				$('#keyUserHead_a').attr('src', result.user.userLogoUrl);
				if (result.user.userVerifiedType == 0) {
					$('#keyUserHead_a').parent('div').addClass('v_yellow');
				} else if(result.user.userVerifiedType >= 1 && result.user.userVerifiedType <= 7) {
					$('#keyUserHead_a').parent('div').addClass('v_blue');
				}
				$('#keyUserNickname_a').text(result.user.userScreenName);
				$('#keyUserHead_a, #keyUserNickname_a').click(function() {
					window.open(result.user.pageUrl);
				});

				keyUser = result.user.userScreenName;
				keyUserCount = result.user.statusRepostsCount;
				$('#keyUserHead').attr('src', result.user.userLogoUrl);
				if (result.user.userVerifiedType == 0) {
					$('#keyUserHead').parent('div').addClass('v_yellow');
				} else if(result.user.userVerifiedType >= 1 && result.user.userVerifiedType <= 7) {
					$('#keyUserHead').parent('div').addClass('v_blue');
				}

				//总说明
				document.getElementById("keyUserName").innerHTML = result.user.userScreenName;

				//博文概况--关键传播用户
				document.getElementById("keyUserNickname_a").innerHTML = result.user.userScreenName;
				document.getElementById("keyUser_li_tran").innerHTML = format_number(result.user.statusRepostsCount);
				document.getElementById("keyUser_li").style.display="block";

				$('#keyUserNickname').text(result.user.userScreenName);
				$('#keyUserHead, #keyUserNickname').click(function() {
					window.open(result.user.pageUrl);
				});;
				$('#keyUserFollowsCount').text(result.user.userFollowersCount);
				$('#keyUserRepostsTime_H').text(result.user.statusCreatedAt.split(" ")[0]);
				$('#keyUserRepostsTime_m').text(result.user.statusCreatedAt.split(" ")[1]);
				$('#keyUserRepostsCount').text(result.user.statusRepostsCount);
				$('#keyUserRepostsContent').html('<em>转发内容：</em>' + result.user.statusText);
			}
		}
	});
}

// 关键用户传播路径
function weiboTaskResultKeyUserRoad() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultKeyUserRoad.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				$('#keyUserRoad').empty();
				var html = '';
				$.each(result.userList, function(i, n) {
					html += '	<li>';
					html += '		<p>';
					if (n.userVerifiedType == 0) {
						html += '		<div class="tx v_yellow">';
					} else if(n.userVerifiedType >= 1 && n.userVerifiedType <= 7) {
						html += '		<div class="tx v_blue">';
					} else {
						html += '		<div class="tx">';
					}
					html += '				<img src="' + n.userLogoUrl + '" style="cursor: pointer;" onclick="javascript:window.open(\'' + n.pageUrl + '\');" />';
					html += '			</div>';
					html += '		</p>';
					html += '		<p><a class="mscrame" href="javascript:;" target="_blank" onclick="javascript:window.open(\'' + n.pageUrl + '\');">' + n.userScreenName + '</a>';
					if (i < (result.userList.length - 1))
						html += '		<a href="javascript:;" target="_blank"><i></i></a>';
					html += '		 </p>';
					html += '	</li>';
				});
				$('#keyUserRoad').append(html);
			}
		}
	});
}

// 引爆点
function weiboTaskResultKeyUserTop() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultKeyUserTop.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				keyUserTop = result.userList;
				weiboTaskResultLine(); // 转发评论分析

				$('#keyUserTop').empty();
				$('#forwardTop10').empty();
				var html = '';
				var html1 = '';
				$.each(result.userList, function(i, n) {
					if (i > 9)
						return false;
					// 引爆点
					var leftOrRight = '';
					var leftRightCount = 0;
					if (i == 0) {
						leftOrRight = 'r';
						leftRightCount = 1;
					} else if (i == 1) {
						leftOrRight = 'r';
						leftRightCount = 2;
					} else if (i == 2) {
						leftOrRight = 'r';
						leftRightCount = 3;
					} else if (i == 3) {
						leftOrRight = 'r';
						leftRightCount = 4;
					} else if (i == 4) {
						leftOrRight = 'r';
						leftRightCount = 5;
					} else if (i == 5) {
						leftOrRight = 'l';
						leftRightCount = 5;
					} else if (i == 6) {
						leftOrRight = 'l';
						leftRightCount = 4;
					} else if (i == 7) {
						leftOrRight = 'l';
						leftRightCount = 3;
					} else if (i == 8) {
						leftOrRight = 'l';
						leftRightCount = 2;
					} else if (i == 9) {
						leftOrRight = 'l';
						leftRightCount = 1;
					}

					html += '	<li class="ybd_' + leftOrRight + ' ybd_' + leftOrRight + '_' + leftRightCount + '">';
					if (n.userVerifiedType == 0) {
						html += '	<div class="tx v_yellow">';
					} else if(n.userVerifiedType >= 1 && n.userVerifiedType <= 7) {
						html += '	<div class="tx v_blue">';
					} else {
						html += '	<div class="tx">';
					}
					html += '			<img src="' + n.userLogoUrl + '" style="cursor: pointer;" onclick="javascript:window.open(\'' + n.pageUrl + '\');" />';
					html += '		</div>';
					html += '		<div class="con">';
					html += '			<p>';
					html += '				<a href="javascript:;" onclick="javascript:window.open(\'' + n.pageUrl + '\');">' + n.userScreenName + '</a>';
					html += '			</p>';
					html += '			<p>粉丝：<em>' + n.userFollowersCount + '</em></p>';
					html += '			<p>二次转发：<em>' + n.statusRepostsCount + '</em></p>';
					var statusCreatedAt = n.statusCreatedAt.split('-').join('/');
					html += '			<p><font>转发时间：</font>' + new Date(statusCreatedAt).format("yyyy-MM-dd hh:mm") + '</p>';
					html += '		</div>';
					html += '	</li>';

					// 转发TOP
					if (i > 2)
						html1 += '	<li style="display: none;">';
					else
						html1 += '	<li>';
					html1 += '		<div class="mwbcon">';
					if (n.userVerifiedType == 0) {
						html1 += '			<div class="m_l v_yellow">';
					} else if(n.userVerifiedType >= 1 && n.userVerifiedType <= 7) {
						html1 += '			<div class="m_l v_blue">';
					} else {
						html1 += '			<div class="m_l">';
					}
					html1 += '					<img src="' + n.userLogoUrl + '" style="cursor: pointer;" onclick="javascript:window.open(\'' + n.pageUrl + '\');">';
					html1 += '			</div>';
					html1 += '			<div class="m_r">';
					html1 += '				<p><a class="mscrame" href="javascript:;" target="_blank" onclick="javascript:window.open(\'' + n.pageUrl + '\');"> ' + n.userScreenName + '</a>';
					html1 += '				</p>';
					html1 += '				<dl class="stNum">';
					html1 += '					<dd>';
					html1 += '						<p>' + n.userFriendsCount + '</p>';
					html1 += '						<p>关注</p>';
					html1 += '					</dd>';
					html1 += '					<dd>';
					html1 += '						<p>' + n.userFollowersCount + '</p>';
					html1 += '						<p>粉丝</p>';
					html1 += '					</dd>';
					html1 += '					<dd>';
					html1 += '						<p>' + n.userStatusCount + '</p>';
					html1 += '						<p>微博</p>';
					html1 += '					</dd>';
					html1 += '				</dl>';
					html1 += '				<div class="mwbcontext img_small">';
					html1 += '					<p>' + n.statusText + '</p>';
					html1 += '				</div>';
					html1 += '				<div class="mfont-buttom">';
					html1 += '					<div class="mfont-buttom_l">';
					html1 += '						<span style="cursor: pointer;" onclick="javascript:window.open(\'' + n.weiboUrl + '\');">' + new Date(statusCreatedAt).format("yyyy-MM-dd hh:mm") + ' </span>';
					// html1 += '						<span>转发自<a href="javascript:;" class="link">@央视新闻</a></span>';
					html1 += '					</div>';
					html1 += '					<div class="mfont-buttom_r">';
					html1 += '						<span class="weibo-multi-panel weibo-list-a">转发(' + n.statusRepostsCount + ')</span> | ';
					html1 += '						<span class="weibo-multi-panel weibo-list-a">评论(' + n.statusCommentsCount + ')</span> | ';
					html1 += '						<span class="weibo-multi-panel weibo-list-a">赞(' + n.statusAttitudesCount + ')</span> | ';
					html1 += '					</div>';
					html1 += '				</div>';
					html1 += '			</div>';
					html1 += '		</div>';
					html1 += '	</li>';
				});
				$('#keyUserTop').append(html);
				$('#forwardTop10').append(html1);
			}
		}
	});
}

// 转发评论表情分析
function weiboTaskResultFace() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultFace.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.faceList != null && result.faceList.length > 0) {
					$('#resultFace').empty();
					var html = '';
					$.each(result.faceList, function(i, n) {
						if (i > 9)
							return false;
						html += '	<li style="margin-bottom:5px;padding-top:5px;list-style-type:none;">';
						if (n.faceUrl == null || n.faceUrl == '')
							html += n.faceName;
						else
							html += '		<img src="' + n.faceUrl + '" border="0" title="' + n.faceName + '" alt="' + n.faceName + '" style="height:22px;margin-right:6px;width:22px;vertical-align:middle;" />';
						html += '		<div style="display:inline-block;width:80%;vertical-align:middle;">';
						html += '			<p style="width:' + (n.faceCount[0] / result.maxValue * 100) + '%;background-color:#607cbe;background-image:linear-gradient(to right, #fff, #607cbe);background-repeat:repeat-x;border-radius:0 2px 2px 0;box-shadow:1px 1px 2px rgba(0, 0, 0, 0.1);height:10px;position:relative;margin:1px;">';
						html += '				<span style="color: #a1a1a1; font-family: arial; font-size: 12px; font-weight: 700; line-height: 10px; position: absolute; right: -68px; width: 60px;">';
						if (n.faceCount[0] > 0)
							html += n.faceCount[0];
						html += '				</span>';
						html += '			</p>';
						html += '			<p style="width:' + (n.faceCount[1] / result.maxValue * 100) + '%;background-color: #e6b053; background-image: linear-gradient(to right, #fff, #e6b053); background-repeat: repeat-x; margin-top: 2px;border-radius: 0 2px 2px 0; box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1); height: 10px; position: relative; margin: 1;">';
						html += '				<span style="color: #a1a1a1; font-family: arial; font-size: 12px; font-weight: 700; line-height: 10px; position: absolute; right: -68px; width: 60px;">';
						if (n.faceCount[1] > 0)
							html += n.faceCount[1];
						html += '				</span>';
						html += '			</p>';
						html += '		</div>';
						html += '	</li>';
					});
					$('#resultFace').append(html);
				}
			}
		}
	});
}

// 影响力TOP10
function weiboTaskResultVerifyUser() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultVerifyUser.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var html = '';
				var n = true;var b = true;var o = true;//三类用户的标识
				var norList = "";
				var bluList = "";
				var oraList = "";
				$('#verifyUser').empty();
				html += '<div class="yxl_top rel">';
				if (result.normalUserList == null || result.normalUserList.length < 1) {
					html += '	<div class="loadingMask">';
					html += '		<span class="waiting noData">无该类转发用户</span>';
					html += '	</div>';
				}
				html += '	<div class="y1 float_l">';
				html += '		<div class="y1_1">普通<br>用户</div>';
				html += '	</div>';
				html += '	<div class="y2 y2_1 float_l">';
				html += '		<ul>';
				if (result.normalUserList != null && result.normalUserList.length > 0) { // 草根用户
					$.each(result.normalUserList, function(i, n) {
						if (i > 0 && i % 5 == 0) {
							html += '</ul>';
							html += '<ul>';
						}
						if (n.userScreenName == null)
							n.userScreenName = '';
						html += '		<li title="' + n.userScreenName+ '" onclick="javascript:window.open(\'' + n.pageUrl + '\');">';
						html += '			<p>' + n.userScreenName.substring(0, 5);
						if (n.userScreenName.length > 5)
							html += '...';
						html += '			</p>';
						html += '			<p><div class="tx"><img src="' + n.userLogoUrl + '" /></div></p>';
						html += '			<p>粉丝数：' + n.userFollowersCount + '</p>';
						html += '			<p>转发：' + n.statusRepostsCount + '</p>';
						html += '		</li>';
						if(i<=2)
							norList += "@"+n.userScreenName+"、";
					});
					norList = "普通转发者"+delTailSymbol("、",norList)+"等影响力较大；";
				}
				html += '		</ul>';
				html += '	</div>';
				html += '</div>';
				html += '<div class="line4"></div>';

				html += '<div class="yxl_top rel">';
				if (result.personalUserList == null || result.personalUserList.length < 1) {
					html += '	<div class="loadingMask">';
					html += '		<span class="waiting noData">无该类转发用户</span>';
					html += '	</div>';
				}
				html += '	<div class="y1 float_l">';
				html += '		<div class="y1_2">橙 V<br>用户</div>';
				html += '	</div>';
				html += '	<div class="y2 y2_2 float_l">';
				html += '		<ul>';
				if (result.personalUserList != null && result.personalUserList.length > 0) { // 橙V用户
					var t1 = "";var t2 = "粉丝数分别为";var t3 = "转发微博并带动";
					$.each(result.personalUserList, function(i, n) {
						if (i > 0 && i % 5 == 0) {
							html += '</ul>';
							html += '<ul>';
						}
						if (n.userScreenName == null)
							n.userScreenName = '';
						html += '		<li title="' + n.userScreenName+ '" onclick="javascript:window.open(\'' + n.pageUrl + '\');">';
						html += '			<p>' + n.userScreenName.substring(0, 5);
						if (n.userScreenName > 5)
							html += '...';
						html += '			</p>';
						html += '			<p><div class="tx v_yellow"><img src="' + n.userLogoUrl + '" /></div></p>';
						html += '			<p>粉丝数：' + n.userFollowersCount + '</p>';
						html += '			<p>转发：' + n.statusRepostsCount + '</p>';
						html += '		</li>';
						if(i<=2){
							t1 += "@"+n.userScreenName+"、";t2 += n.userFollowersCount+"个、";t3 += n.statusRepostsCount+"次、";
						}
					});
					oraList = delTailSymbol("、",t1)+"，"+delTailSymbol("、",t2)+"，"+delTailSymbol("、",t3)+"转发；";
				}
				html += '		</ul>';
				html += '	</div>';
				html += '</div>';
				html += '<div class="line4"></div>';

				html += '<div class="yxl_top rel">';
				if (result.officalUserList == null || result.officalUserList.length < 1) {
					html += '	<div class="loadingMask">';
					html += '		<span class="waiting noData">无该类转发用户</span>';
					html += '	</div>';
				}
				html += '	<div class="y1 float_l">';
				html += '		<div class="y1_3">蓝 V<br>用户</div>';
				html += '	</div>';
				html += '	<div class="y2 y2_3 float_l">';
				html += '		<ul>';
				if (result.officalUserList != null && result.officalUserList.length > 0) { // 蓝V用户
					$.each(result.officalUserList, function(i, n) {
						if (i > 0 && i % 5 == 0) {
							html += '</ul>';
							html += '<ul>';
						}
						if (n.userScreenName == null)
							n.userScreenName = '';
						html += '		<li title="' + n.userScreenName+ '" onclick="javascript:window.open(\'' + n.pageUrl + '\');">';
						html += '			<p>' + n.userScreenName.substring(0, 5);
						if (n.userScreenName.length > 5)
							html += '...';
						html += '			</p>';
						html += '			<p><div class="tx v_blue"><img src="' + n.userLogoUrl + '" /></div></p>';
						html += '			<p>粉丝数：' + n.userFollowersCount + '</p>';
						html += '			<p>转发：' + n.statusRepostsCount + '</p>';
						var verTag = '';
						switch (n.userVerifiedType) {
							case 1:
								verTag = '政府';
								break;
							case 2:
								verTag = '企业';
								break;
							case 3:
								verTag = '媒体';
								break;
							case 4:
								verTag = '校园';
								break;
							case 5:
								verTag = '网站';
								break;
							case 6:
								verTag = '应用';
								break;
							case 7:
								verTag = '团体';
								break;
							default:
								verTag = '企业';
								break;
						}
						if (verTag != '')
							html += '			<em class="tag">' + verTag + '</em>';
						html += '		</li>';
						if(i<=2)
							bluList += "@"+n.userScreenName+"、";
					});
					bluList = delTailSymbol("、",bluList) + "等蓝V用户的转发。";
				}
				html += '		</ul>';
				html += '	</div>';
				html += '</div>';
				$('#verifyUser').append(html);

				$.merge($.merge(result.normalUserList, result.personalUserList), result.officalUserList);
				result.normalUserList.sort(compare('userFollowersCount'));
				if (result.normalUserList[0])
					shareTopUser1 = result.normalUserList[0].userScreenName;
				if (result.normalUserList[1])
					shareTopUser2 = result.normalUserList[1].userScreenName;
				if (result.normalUserList[2])
					shareTopUser3 = result.normalUserList[2].userScreenName;

				var decHtml = "";
				if(n&&b&&o)
					decHtml = norList + "橙V用户" + oraList + "此外，微博还吸引了" + bluList;
				else if(n&&b&&!o)
					decHtml = norList + "微博还吸引了"+bluList;
				else if(n&&!b&&o)
					decHtml = norList + "橙V用户" + oraList + "无蓝V用户转发该条微博。";
				else if(!n&&b&&o)
					decHtml ="橙V用户" + oraList + "此外，微博还吸引了" +  bluList;
				else if(!n&&!b&&o)
					decHtml = oraList + "无普通用户或蓝V用户转发该条微博。";
				else if(!n&&b&&!o)
					decHtml = bluList + "无普通用户或橙V用户转发该条微博。";
				else if(n&&!b&&!o)
					decHtml = norList + "无橙V用户或蓝V用户转发该条微博.";

				$('#top_dec').empty();
				$('#top_dec').html('<div class="well fz14" style="margin-bottom: 0;">'+decHtml+'</div>');
				shareReadyB = true;
			}
		}
	});
}

// 转发评论地域分析
function weiboTaskResultLocation() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultLocation.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var trans_dat = "";
				var comment_dat = "";
				if (result.repostsList != null && result.repostsList.length > 0) { // 转发
					var sumValue = 0;
					$.each(result.repostsList, function(i, n) {
						if (n.value > 0)
							sumValue += n.value;
					});

					$('#repostsPH').empty();
					var html = '';
					$.each(result.repostsList, function(i, n) {
						if (i > 4 || n.value <= 0 || sumValue <= 0)
							return false;
						if(i%2==0){
							html += '<tr>';
							html += '	<td style="border-bottom: dashed 1px #e8e8e8;background-color:#fdfbf5;">' + n.name + '</td>';
							html += '	<td style="border-bottom: dashed 1px #e8e8e8;background-color:#fdfbf5;">' + ((n.value / sumValue * 100).toFixed(2)) + '%</td>';
							html += '</tr>';
						}else{
							html += '<tr>';
							html += '	<td style="border-bottom: dashed 1px #e8e8e8;">' + n.name + '</td>';
							html += '	<td style="border-bottom: dashed 1px #e8e8e8;">' + ((n.value / sumValue * 100).toFixed(2)) + '%</td>';
							html += '</tr>';
						}
						//说明 去取前三
						if(i<=2){
							trans_dat += n.name+"、";
						}
					});
					$('#repostsPH').append(html);

					var chart = echarts.init(document.getElementById('weibo_task_result_location_reposts_div'));
					var option = {
						animation : true,
						tooltip : {
							trigger : 'item',
							enterable:true,
							textStyle : {
								fontSize : 12
							}
						},
						dataRange: {
							min: 0,
							max: result.repostsMax == 0 ? 1 : result.repostsMax,
							x: 'right',
							itemWidth: 13,
							itemHeight: 5,
							splitNumber: 0,
							text: ['高', '低'],
							color: ['#d44e24','#f5bc00']
						},
						series : [
							{
								name: '',
								type: 'map',
								mapType: 'china',
								roam: false,
								left:'center',
								label: {
									normal: {
										show: false,
										textStyle: {
											color: "#3a3a3a",//地图文字颜色
											fontSize: 8
										}
									}

								},
								itemStyle: {
									normal: {
										borderColor: '#fff',
										areaColor: '#efefef',//地图背景颜色
									},
									emphasis: {
										areaColor: '#bdd3f5'
									}
								}
								,
								data:[

//             {name: '湖北',value: randomData() },
									{
										name: '南海诸岛',
										itemStyle: {
											normal: {
												borderColor: '#959595',
												areaColor: '#efefef',
											}
										}
									},


								]
							},
							{
								name : '数量',
								type : 'map',
								mapType : 'china',
								selectedMode : 'single',//single|multiple
								mapLocation: {
									x: '90'
								},
								roam:false,
								itemStyle : {
									normal : {
										label : {
											show : false,
											textStyle : {
												fontSize : 10
											},
										},
										borderColor:'#FFF'
									},
									emphasis : {
										label : {
											show : true
										}
									}
								},
								geoCoord: {
									"北京":[116.46,39.92],
									"辽宁":[123.14,41.66],
									"安徽":[116.98,32.62],
									"山东":[117.52,36.23],
									"上海":[121.42,31.12],
									"江苏":[119.72,33.66],
									"湖北":[111.99,31.54],
									"浙江":[119.87,29.24],
									"广东":[112.73,24.02],
									"福建":[117.73,26.17],
									"湖南":[111.33,27.75],
									"澳门":[113.57,22.51],
									"河南":[113.57,33.86],
									"海南":[109.53,19.15],
									"新疆":[85.90,40.98],
									"内蒙古":[113.87,43.49],
									"西藏":[87.01,32.53],
									"青海":[95.03,36.19],
									"云南":[101.25,24.48],
									"四川":[102.20,30.77],
									"贵州":[106.62,26.94],
									"甘肃":[96.46,40.19],
									"广西":[108.31,23.60],
									"江西":[115.52,27.60],
									"黑龙江":[127.37,47.73],
									"吉林":[126.49,43.49],
									"山西":[111.99,37.20],
									"河北":[115.01,38.31],
									"陕西":[108.75,34.09],
									"重庆":[106.56,29.64],
									"宁夏":[105.98,37.25],
									"天津":[117.14,39.29],
									"台湾":[120.90,23.81]
								},
								data :result.repostsList
							} ]
					};
					chart.setOption(option);
				}
				if (result.commentsList != null && result.commentsList.length > 0) { // 评论
					var sumValue = 0;
					$.each(result.commentsList, function(i, n) {
						if (n.value > 0)
							sumValue += n.value;
					});

					$('#commontsPH').empty();
					var html = '';
					$.each(result.commentsList, function(i, n) {
						if (i > 4 || n.value <= 0 || sumValue <= 0)
							return false;
						if(i%2==0){
							html += '<tr>';
							html += '	<td style="border-bottom: dashed 1px #e8e8e8;background-color:#fdfbf5;">' + n.name + '</td>';
							html += '	<td style="border-bottom: dashed 1px #e8e8e8;background-color:#fdfbf5;">' + ((n.value / sumValue * 100).toFixed(2)) + '%</td>';
							html += '</tr>';
						}else{
							html += '<tr>';
							html += '	<td style="border-bottom: dashed 1px #e8e8e8;">' + n.name + '</td>';
							html += '	<td style="border-bottom: dashed 1px #e8e8e8;">' + ((n.value / sumValue * 100).toFixed(2)) + '%</td>';
							html += '</tr>';
						}
						//说明 去取前三
						if(i<=2){
							comment_dat += n.name+"、";
						}
					});
					$('#commontsPH').append(html);

					var chart = echarts.init(document.getElementById('weibo_task_result_location_comments_div'));
					var option = {
						animation : true,
						tooltip : {
							trigger : 'item',
							enterable:true,
							textStyle : {
								fontSize : 12
							}
						},
						dataRange: {
							min: 0,
							max: result.commentsMax == 0 ? 1 : result.commentsMax,
							x: 'right',
							itemWidth: 13,
							itemHeight: 5,
							splitNumber: 0,
							text: ['高', '低'],
							color: ['#d44e24','#f5bc00']
						},
						series : [
							{
								name: '',
								type: 'map',
								mapType: 'china',
								roam: false,
								left:'center',
								label: {
									normal: {
										show: false,
										textStyle: {
											color: "#3a3a3a",//地图文字颜色
											fontSize: 8
										}
									}

								},
								itemStyle: {
									normal: {
										borderColor: '#fff',
										areaColor: '#efefef',//地图背景颜色
									},
									emphasis: {
										areaColor: '#bdd3f5'
									}
								}
								,
								data:[

//             {name: '湖北',value: randomData() },
									{
										name: '南海诸岛',
										itemStyle: {
											normal: {
												borderColor: '#959595',
												areaColor: '#efefef',
											}
										}
									},


								]
							},
							{
								name : '数量',
								type : 'map',
								mapType : 'china',
								selectedMode : 'single',//single|multiple
								mapLocation: {
									x: '90'
								},
								roam:false,
								itemStyle : {
									normal : {
										label : {
											show : false,
											textStyle : {
												fontSize : 10
											},
										},
										borderColor:'#FFF'
									},
									emphasis : {
										label : {
											show : true
										}
									}
								},
								geoCoord: {
									"北京":[116.46,39.92],
									"辽宁":[123.14,41.66],
									"安徽":[116.98,32.62],
									"山东":[117.52,36.23],
									"上海":[121.42,31.12],
									"江苏":[119.72,33.66],
									"湖北":[111.99,31.54],
									"浙江":[119.87,29.24],
									"广东":[112.73,24.02],
									"福建":[117.73,26.17],
									"湖南":[111.33,27.75],
									"澳门":[113.57,22.51],
									"河南":[113.57,33.86],
									"海南":[109.53,19.15],
									"新疆":[85.90,40.98],
									"内蒙古":[113.87,43.49],
									"西藏":[87.01,32.53],
									"青海":[95.03,36.19],
									"云南":[101.25,24.48],
									"四川":[102.20,30.77],
									"贵州":[106.62,26.94],
									"甘肃":[96.46,40.19],
									"广西":[108.31,23.60],
									"江西":[115.52,27.60],
									"黑龙江":[127.37,47.73],
									"吉林":[126.49,43.49],
									"山西":[111.99,37.20],
									"河北":[115.01,38.31],
									"陕西":[108.75,34.09],
									"重庆":[106.56,29.64],
									"宁夏":[105.98,37.25],
									"天津":[117.14,39.29],
									"台湾":[120.90,23.81]
								},
								data :result.commentsList
							} ]
					};
					chart.setOption(option);
				}
				//说明  地域分析
				var decHtml = '<div class="well fz14" style="margin-bottom: 0;">该条微博转评用户在地域分布上较为集中，转发者主要分布于'+delTailSymbol("、",trans_dat)+'，评论者主要分布于'+delTailSymbol("、",comment_dat)+'。</div>';
				$('#area_dec').empty();
				$('#area_dec').html(decHtml);
			}
		}
	});
}

// 转发评论性别分析
function weiboTaskResultGender() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultGender.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.repostsList != null && result.repostsList.length > 0) {
					var sum = result.repostsList[0].value + result.repostsList[1].value;
					if (sum > 0) {
						$.each(result.repostsList, function(i, n) {
							if (n.name == '男')
								$('#reposts_male-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
							if (n.name == '女')
								$('#reposts_female-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
						});
					}
					var chart = echarts.init(document.getElementById('weibo_task_result_gender_reposts_div'));
					option = {
						tooltip : {
							trigger: 'item',
							formatter: "{b} : {c} ({d}%)"
						},
						color : ['#ec6a3a','#277bc0'],
						series : [
							{
								type : 'pie',
								radius : '80%',
								center: ['50%', '50%'],
								itemStyle : {
									normal : {
										label : {
											show : false
										},
										labelLine : {
											show : false
										}
									}
								},
								data : result.repostsList
							}
						]
					};
					chart.setOption(option);
				}

				if (result.commentsList != null && result.commentsList.length > 0) {
					var sum = result.commentsList[0].value + result.commentsList[1].value;
					if (sum > 0) {
						$.each(result.commentsList, function(i, n) {
							if (n.name == '男')
								$('#comments_male-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
							if (n.name == '女')
								$('#comments_female-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
						});
					}
					var chart = echarts.init(document.getElementById('weibo_task_result_gender_comments_div'));
					option = {
						tooltip : {
							trigger: 'item',
							formatter: "{b} : {c} ({d}%)"
						},
						color : ['#ec6a3a','#277bc0'],
						series : [
							{
								type : 'pie',
								radius : '80%',
								center: ['50%', '50%'],
								itemStyle : {
									normal : {
										label : {
											show : false
										},
										labelLine : {
											show : false
										}
									}
								},
								data : result.commentsList
							}
						]
					};
					chart.setOption(option);
				}
			}
		}
	});
}

//词云样式修改
var rgb=['#f29300','#72c1be','#a05623','#277bc0'];
function createRandomItemStyle() {
	return {
		normal: {
			color: rgb[GetRandomNum(0,3)]
		}
	};
}
function GetRandomNum(Min, Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return (Min + Math.round(Rand * Range));
}

// 转发评论用户兴趣标签
function weiboTaskResultUserTag() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultUserTag.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.repostsList != null && result.repostsList.length > 0) {
					$.each(result.repostsList, function(i, n) {
						n.itemStyle = createRandomItemStyle();
					});
					var chart = echarts.init(document.getElementById('weibo_task_result_user_tag_reposts_div'));
					option = {
						series : [
							{
								type : 'wordCloud',
								size: ['100%', '100%'],
								textRotation : [0, 0, 0, 0],
								textPadding: 1,
								itemStyle:{
									normal:{
										textStyle:{
											fontFamily:"Microsoft Yahei",
											fontWeight:'bold'
										},
									}
								},
								autoSize: {
									enable: true,
									minSize: 12
								},
								data : result.repostsList
							}
						]
					};
					chart.setOption(option);
				}
				if (result.commentsList != null && result.commentsList.length > 0) {
					$.each(result.commentsList, function(i, n) {
						n.itemStyle = createRandomItemStyle();
					});
					var chart = echarts.init(document.getElementById('weibo_task_result_user_tag_comments_div'));
					option = {
						series : [
							{
								type : 'wordCloud',
								size: ['100%', '100%'],
								textRotation : [0, 0, 0, 0],
								textPadding: 1,
								itemStyle:{
									normal:{
										textStyle:{
											fontFamily:"Microsoft Yahei",
											fontWeight:'bold'
										},
									}
								},
								autoSize: {
									enable: true,
									minSize: 12
								},
								data : result.commentsList
							}
						]
					};
					chart.setOption(option);
				}
			}
		}
	});
}

// 转发评论粉丝分布
function weiboTaskResultFans() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultFans.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.repostsList != null && result.repostsList.length > 0 && result.commentsList != null && result.commentsList.length > 0) {
					var chart = echarts.init(document.getElementById('weibo_task_result_fans_div'));
					option = {
						tooltip : {
							trigger: 'axis',
							formatter: function(params) {
								v = '粉丝量区间：' + params[0].name;
								for (var i = 0, l = params.length; i < l; i++)
									v += '<br/>' + params[i].seriesName + '者人数 : ' + params[i].value + '人';
								return v;
							}
						},
						grid : {
							x : isMobile ? 40 : 80,
							y : isMobile ? 10 : 60,
							x2 : isMobile ? 10 : 80,
							y2 : 80
						},
						xAxis : [
							{
								type : 'category',
								data : result.legend,
								axisLabel : {
									show : true,
									rotate : 45
								}
							}
						],
						yAxis : [
							{
								type : 'value'
							}
						],
						series : [
							{
								name:'转发',
								type:'bar',
								itemStyle:{
									normal: {
										color: '#ec6a3a'
									}
								},
								data:result.repostsList
							},
							{
								name:'评论',
								type:'bar',
								itemStyle:{
									normal: {
										color: '#277bc0'
									}
								},
								data:result.commentsList
							}
						]
					};
					chart.setOption(option);
				}
			}
		}
	});
}
//敏感度分析
function weiboTaskResultSensitive() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultSensitive.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var sum = result.sensitive.sensitiveCount + result.sensitive.insensitiveCount;
				if (sum > 0) {
					var sensitivePercent = (result.sensitive.sensitiveCount / sum * 100).toFixed(2);
					$('#sensitivePercent').text(sensitivePercent + '%');
					$('#sensitiveBarWidth').css('width', sensitivePercent + '%');
					$('#insensitivePercent').text((100 - sensitivePercent).toFixed(2) + '%');

					//博文概况--敏感度
					document.getElementById("sensitive_li_val").innerHTML=sensitivePercent + '%';
					document.getElementById("sensitive_li").style.display="block";

					//针对敏感度声明
					var decHtml = "";
					if(sensitivePercent<5){
						decHtml = '<div class="well fz14" style="margin-bottom: 0;">转评内容中敏感内容占比'+sensitivePercent+'%，在低敏感度范围内。</div>';
					}else if(sensitivePercent>=5&&sensitivePercent<10){
						decHtml = '<div class="well fz14" style="margin-bottom: 0;">转评内容中敏感内容占比'+sensitivePercent+'%，在中敏感度范围内。</div>';
					}else{
						decHtml = '<div class="well fz14" style="margin-bottom: 0;">转评内容中敏感内容占比'+sensitivePercent+'%，在高敏感度范围内。</div>';
					}
					$('#sensitive_dec').empty();
					$('#sensitive_dec').html(decHtml);
				}
			}
		}
	});
}

// 转发评论热词
function weiboTaskResultHotWords() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultHotWords.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var html = '';
				var index = 0;
				var dec = "";//说明文字
				if(result.length==20){
					$.each(result, function(i, n) {
						if (i % 2 == 0) {
							html += '<tr>';
							index++;
						}
						if (n != null) {
							html += '	<td>' + (i % 2 == 0 ? index : index + 10) + '</td>';
							if (i == 0)
								html += '	<td class="wd active">' + n.name + '</td>';
							else
								html += '	<td class="wd">' + n.name + '</td>';
							html += '	<td>' + n.value + '</td>';
						} else {
							html += '	<td>&nbsp;</td>';
							html += '	<td class="wd">&nbsp;</td>';
							html += '	<td>&nbsp;</td>';
						}
						if (i % 2 != 0)
							html += '</tr>';
						//说明字段拼接
						if(i==0){
							dec += "\""+n.name+"\"提及量"+n.value+"次，成为网友提及次数最多的词";
						}else if(i==2){
							dec += "，词语\""+n.name+"\"排名第二";
						}else if(i==4){
							dec += "、\""+n.name+"\"排名第三。";
						}
						if (i == 19)
							return false;
					});
				}else{
					result.sort(function(a, b) {
						return (b.value - a.value);
					});
					$.each(result, function(i, n) {
						if (i % 2 == 0) {
							html += '<tr>';
							index++;
						}
						if (n != null) {
							html += '	<td>' + (i+1) + '</td>';
							if (i == 0)
								html += '	<td class="wd active">' + n.name + '</td>';
							else
								html += '	<td class="wd">' + n.name + '</td>';
							html += '	<td>' + n.value + '</td>';
						} else {
							html += '	<td>&nbsp;</td>';
							html += '	<td class="wd">&nbsp;</td>';
							html += '	<td>&nbsp;</td>';
						}
						if (i % 2 != 0)
							html += '</tr>';
						//说明字段拼接
						if(i==0){
							dec += "\""+n.name+"\"提及量"+n.value+"次，成为网友提及次数最多的词";
						}else if(i==2){
							dec += "，词语\""+n.name+"\"排名第二";
						}else if(i==4){
							dec += "、\""+n.name+"\"排名第三。";
						}
						if (i == 19)
							return false;
					});
				}
				$('#hotWordsTBody').empty();
				$('#hotWordsTBody').html(html);
				var decHtml = '<div class="well fz14" style="margin-bottom: 0;">'+dec+'</div>';
				$('#hw_dec').empty();
				$('#hw_dec').html(decHtml);
				//给词云排序
				var word1 = new Array();
				var word2 = new Array();
				$.each(result,function(i,n){
					if(i%2==0){
						if(n!=null)
							word1.push(n);
					}else{
						if(n!=null)
							word2.push(n);
					}
				});
				var word3 = word1.concat(word2);
				//给词云值加倍数
				if(word3[0].value<=20){
					for(var i=0;i<word3.length;i++){
						if(word3[i]!=null){
							word3[i].value = word3[i].value*20+(word3.length-i);
						}
					}
				}
				var arr = new Array();
				var index = 0;
				$.each(word3, function(i, n) {
					if(n!=null){
						n.itemStyle = createRandomItemStyle();
						arr[index] = n;
						index++;
					}
				});
				var chart = echarts.init(document.getElementById('weibo_task_result_hot_words_div'));
				option = {
					series : [
						{
							type : 'wordCloud',
							size: ['100%', '100%'],
							textRotation : [0, 0, 0, 0],
							textPadding: 1,
							itemStyle:{
								normal:{
									textStyle:{
										fontFamily:"Microsoft Yahei",
										fontWeight:'bold'
									},
								}
							},
							autoSize: {
								enable: true,
								minSize: 12
							},
							data : arr
						}
					]
				};
				chart.setOption(option);
				chart.on('click', function(param) {
					if (param) {
						weiboTaskResultHotWordsDetail(param.name);
					}
				});

				// 详情
				weiboTaskResultHotWordsDetail(result[0].name);
			}
		}
	});
}

// 转发热词详情
function weiboTaskResultHotWordsDetail(keywords) {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultHotWordsDetail.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'keyWords':keywords,
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var html = '';
				$.each(result.userList, function(i, n) {
					html += '<tr>';
					html += '	<td><span class="f_c1">' + n.userScreenName + '</span></td>';
					html += '	<td>' + n.statusText + '</td>';
					html += '	<td>' + n.statusRepostsCount + '</td>';
					html += '</tr>';

					if (i == 19)
						return false;
				});
				$('#hotWordsDetailTBody').empty();
				$('#hotWordsDetailTBody').html(html);
			}
		}
	});
}

// 发布设备来源
function weiboTaskResultSource() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultSource.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			console.log($(window).width());
			if($(window).width()<700){
				if (!$.isEmptyObject(result)) {
					var sum = 0;
					var objArr = new Array();
					$.each(result, function(i, n) {
						sum = sum + n.sourceCount;
						var obj = {};
						obj.name = n.sourceName;
						obj.value = n.sourceCount;
						objArr.push(obj);
					});

					var html = '';
					$.each(result, function(i, n) {
						html += '<div class="bili b_' + (i + 1) + '">';
						html += '	<i></i><strong style="font-family: 微软雅黑;"> ' + n.sourceName + '：' + (n.sourceCount / sum * 100).toFixed(2) + '%</strong>';
						html += '</div>';
					});
					$('#weibo_task_result_source_l_div').empty();
					$('#weibo_task_result_source_l_div').html(html);

					var chart = echarts.init(document.getElementById('weibo_task_result_source_r_div'));
					option = {
						tooltip : {
							trigger: 'item',
							formatter: "{b} : {c} ({d}%)"
						},
						calculable : true,
						color : ['#f29300', '#3454a1', '#277bc0', '#72c1be', '#9e9d9e', '#ec6a3a', '#d44e24', '#a05623', '#bb814e', '#f5bc00'],
						series : [
							{
								type : 'pie',
								radius : ['15%', '50%'],
								center : ['70%', '50%'],
								roseType : 'area',
								sort : 'ascending',
								data : objArr
							}
						]
					};
					chart.setOption(option);
				}
			}else{
				if (!$.isEmptyObject(result)) {
					var sum = 0;
					var objArr = new Array();
					$.each(result, function(i, n) {
						sum = sum + n.sourceCount;
						var obj = {};
						obj.name = n.sourceName;
						obj.value = n.sourceCount;
						objArr.push(obj);
					});

					var html = '';
					$.each(result, function(i, n) {
						html += '<div class="bili b_' + (i + 1) + '">';
						html += '	<i></i><strong style="font-family: 微软雅黑;"> ' + n.sourceName + '：' + (n.sourceCount / sum * 100).toFixed(2) + '%</strong>';
						html += '</div>';
					});
					$('#weibo_task_result_source_l_div').empty();
					$('#weibo_task_result_source_l_div').html(html);

					var chart = echarts.init(document.getElementById('weibo_task_result_source_r_div'));
					option = {
						tooltip : {
							trigger: 'item',
							formatter: "{b} : {c} ({d}%)"
						},
						calculable : true,
						color : ['#f29300', '#3454a1', '#277bc0', '#72c1be', '#9e9d9e', '#ec6a3a', '#d44e24', '#a05623', '#bb814e', '#f5bc00'],
						series : [
							{
								type : 'pie',
								radius : ['20%', '62%'],
								center : ['68%', '50%'],
								roseType : 'area',
								sort : 'ascending',
								data : objArr
							}
						]
					};
					chart.setOption(option);
				}
			}

		}
	});
}

// 网友观点分析
function weiboTaskResultViewPoint() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultViewPoint.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val(),
			'taskCreateTime':$('#taskCreateTime').val(),
			'markType':$('#markType').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var sum = 0;
				$.each(result.list, function(i, n) {
					if(i<=9){
						sum  = sum + n.viewPointCount;
					}
				});
				var html = '';
				$.each(result.list, function(i, n) {
					if(i<=9){
						html += '<li>';
						if(i==0||i==1||i==2){
							html += '	<h2 style="color: #277bc0;"><em>Top'+(i+1)+'.</em><span>' + n.viewPointName + '(' + (n.viewPointCount / sum * 100).toFixed(2) + '%)</span></h2>';
						}else{
							html += '	<h2 style="color: #277bc0;"><em>'+(i+1)+'.</em><span>' + n.viewPointName + '(' + (n.viewPointCount / sum * 100).toFixed(2) + '%)</span></h2>';
						}
						$.each(n.viewPointList, function(i1, n1) {
							html += '	<p>';
							html += '		<a href="javascript:;" style="color: #3e3a39 !important;">' + n1 + '</a>';
							html += '	</p>';
						});
						html += '</li>';
					}
				});
				$('#weibo_task_result_view_point_ul').empty();
				$('#weibo_task_result_view_point_ul').html(html);

				var objArr = new Array();
				var legendArr = new Array();
				var colors = ['#bb814e', '#bb814e', '#bb814e', '#bb814e', '#bb814e', '#bb814e', '#bb814e', '#bb814e', '#bb814e', '#bb814e'];
				$.each(result.chart, function(i, n) {
					var obj = {};
					obj.name = n.viewPointName.length > 30 ? n.viewPointName.substring(0, 30) : n.viewPointName + ' ' + (n.viewPointCount / sum * 100).toFixed(2) + '%';
					obj.value = n.viewPointCount;
					obj.itemStyle = {};
					obj.itemStyle.normal = {};
					obj.itemStyle.normal.color = colors[i];
					objArr.push(obj);
					if (n.viewPointName.length > 30)
						legendArr.push(n.viewPointName.substring(0, 30) + ' ' + (n.viewPointCount / sum * 100).toFixed(2) + '%');
					else
						legendArr.push(n.viewPointName + ' ' + (n.viewPointCount / sum * 100).toFixed(2) + '%');
				});
				objArr.reverse();
				legendArr.reverse();

				var chart = echarts.init(document.getElementById('weibo_task_result_view_point_div'));
				option = {
					tooltip : {
						formatter: '{b}'
					},
					grid : {
						y:0,
						y2:30,
						x:10,
						x2:10,
						borderWidth:0
					},
					xAxis : [
						{
							type : 'value',
							show:false
						}
					],
					yAxis : [
						{
							type : 'category',
							show : false,
							data : legendArr
						}
					],
					calculable : false,
					animation : false,
					series : [
						{
							type:'bar',
							itemStyle : {
								normal: {
									label : {
										show: true,
										position: 'insideLeft',
										formatter:function(param){
											return param.name;
										},
										textStyle:{fontSize:12,color:"#000"}
									}
								},
								emphasis:{
									label : {
										show : true,
										textStyle : {
											fontSize : 12,
											color : "#000"
										}
									}
								}
							},
							sort : 'descending',
							data:objArr
						}
					]
				};
				chart.setOption(option);
			}
		}
	});
}

var sc = {
	svg : 'svg',
	canvas : 'canvas'
};

var shareReadyA = false, shareReadyB = false;
var shareReadyT = setInterval(function() {
	if (shareReadyA && shareReadyB) {
		clearInterval(shareReadyT);

		var shareContent = getShareContent();
		if (shareContent != null && shareContent != '')
			$('head title').text(shareContent);
	}
}, 100);

/***********************************************  微信分享参数设置  **************************************************/
function wxShareInit(){
	//微信分享
	var shareContentWX = $("#shareTitle").val();
	if($("#shareTitle").val()==null||$("#shareTitle").val()==""){
		shareContentWX = $("#wxShareContent").val();
	}
	var wcConfig = new Object();
	wcConfig.title = $("#shareContent").val();
	wcConfig.desc = shareContentWX;
	wcConfig.link = actionBase + '/share/singleWeiboAnalysis/shareView.shtml?shareCode=' + $('#shareCode').val();
	wcConfig.imgUrl = staticResourcePath + "/images/wyqlogo.jpg";
	wcConfig.basePath = actionBase;
	weixinLinkShare(wcConfig);
}