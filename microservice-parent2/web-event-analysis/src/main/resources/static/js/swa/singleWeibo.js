// ��ȡ΢������
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
					$('#weiboContentPic').html('<p>' + result.status.statusText + '</p>');
					var statusCreatedAt = result.status.statusCreatedAt.split('-').join('/');
					$('#weiboContentTime').prepend('<span style="cursor: pointer;" onclick="javascript:window.open(\'' + result.status.url + '\');">' + new Date(statusCreatedAt).format('yyyy-MM-dd hh:mm') + '</span>');
					$('#weiboContentForwardNumber').text('ת��(' + result.status.statusRepostsCount + ')');
					$('#weiboContentCommentNumber').text('����(' + result.status.statusCommentsCount + ')');
					$('#weiboContentPraiseNumber').text('��(' + result.status.statusAttitudesCount + ')');
					
					shareReportsCount = result.status.statusRepostsCount;
					shareCommentCount = result.status.statusCommentsCount;
					sharePriseCount = result.status.statusAttitudesCount;
					shareReadsCount = result.status.statusReadsCount;
					
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
				} else {
					setTimeout('weiboTaskResultStatus()', 1000);
				}
			} else {
				setTimeout('weiboTaskResultStatus()', 1000);
			}
		}
	});
}

// ��ȡ΢������״̬
function weiboTaskStatus() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskStatus.action',
		type : 'POST',
		data : {
			'taskTicket' : $('#taskTicket').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.task.analysisStatus == 0) {
					alertMsg(0, '΢������ʧ�ܣ���ȷ��΢�������Ƿ���ȷ��', 0);
				} else {
					if (result.task.finishedTier > $('#currentTaskTier').val()) { // ���²㼶
						$('#currentTaskTier').val(result.task.finishedTier);
						reloadWeiboTask();
					}
				}
			}
		}
	});
}

// ΢������
function reloadWeiboTask() {
	weiboTaskResultStar(); // �����ڵ�
	weiboTaskResultTier(); // ת���㼶
//	weiboTaskResultLine(); // ת����������
	weiboTaskResultKeyUser(); // �����ؼ��û�
	weiboTaskResultKeyUserRoad(); // �ؼ��û�����·��
	weiboTaskResultKeyUserTop(); // ������
	weiboTaskResultFace(); // ת�����۱������
	weiboTaskResultVerifyUser(); // Ӱ����TOP10
	weiboTaskResultLocation(); // ת�����۵������
	weiboTaskResultGender(); // ת�������Ա����
	weiboTaskResultUserTag(); // ת�������û���Ȥ��ǩ
	weiboTaskResultFans(); // ת�����۷�˿�ֲ�
	weiboTaskResultSensitive(); // ���жȷ���
}

//�����ڵ�
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
			'userId':$('#userId').val()
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


// ת���㼶
function weiboTaskResultTier() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultTier.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var html = '';
				$('.table_list.table_list_h5').empty();
				$.each(result.transTierList, function(i, n) {
					if (i == 0) { // ��
						$('#tierSpan').text(result.transTierList.length - 1);
						$('#tierRepostsSpan').text(n.tierInfo.repostsUserCount);
						$('#tierCoverUserSpan').text(n.tierInfo.repostsCoverUserCount);
					} else { // �ֲ㼶
						if (i > 4)
							html += '	<li style="display: none;">';
						else
							html += '	<li>';
						html += '		<div class="level_tit">';
						html += '			<div>�� ' + i + ' ��</div>';
						html += '			<div>';
						html += '				<font>ת������</font>' + n.tierInfo.repostsUserCount;
						html += '			</div>';
						html += '			<div>';
						html += '				<font>ת��ռ��</font>' + n.tierInfo.repostsProportion.toFixed(2) + '%';
						html += '			</div>';
						html += '			<div>';
						html += '				<font>����������</font>' + n.tierInfo.repostsCoverUserCount;
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
							html += '		<p class="fz12">ת����' + n1.statusRepostsCount + '</p>';
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
							html += '	<a class="arrow" href="javascript:void(0)" title="����"></a>';
						}
						html += '	</li>';
					}
				});
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

// ת����������
function weiboTaskResultLine() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultLine.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
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
		                		
//		                		v += '<br/>�ؼ�ת���ߣ�' + keyUser + '<br/>����ת����' + keyUserCount;
		                		
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
	                                return '�û��ǳƣ�' + user.userScreenName + '<br />'
	                                        + '����ת����' + user.statusRepostsCount + '<br />' 
	                                        + 'ת��ʱ�䣺' + user.statusCreatedAt;
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
		            	name: "ת��",
		                type: "area",
		                color: "#ff6633",
		                data: result.repostsList
		            },{
		            	name: "����",
		                type: "area",
		                color: "#3399ff",
		                data: result.commentsList
		            }, {
		            	name: "�ؼ���",
		                type: "bubble",
		                color: "#f29b22",
		                data: (function () {
                            var d = [];
                            $.each(keyUserTop, function(i, n) {
                            	var value = n.statusCreatedAt.split('-').join('/');
                            	
                            	var obj = {};
                            	obj.name = 'name' + i;
                            	obj.x = result.legend.indexOf(new Date(value).format("yyyy-MM-dd hh:00:00"));
                            	if (obj.x == -1)
                            		obj.x = result.legend.indexOf(new Date(value).format("yyyy-MM-dd 00:00:00"));
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
			}
		}
	});
}

// �����ؼ��û�
function weiboTaskResultKeyUser() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultKeyUser.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result) && result.user != null) {
				keyUser = result.user.userScreenName;
				keyUserCount = result.user.statusRepostsCount;
				$('#keyUserHead').attr('src', result.user.userLogoUrl);
				if (result.user.userVerifiedType == 0) {
					$('#keyUserHead').parent('div').addClass('v_yellow');
				} else if(result.user.userVerifiedType >= 1 && result.user.userVerifiedType <= 7) {
					$('#keyUserHead').parent('div').addClass('v_blue');
				}
				$('#keyUserNickname').text(result.user.userScreenName);
				$('#keyUserHead, #keyUserNickname').click(function() {
					window.open(result.user.pageUrl);
				});;
				$('#keyUserFollowsCount').text(result.user.userFollowersCount);
				$('#keyUserRepostsTime').text(result.user.statusCreatedAt.replace(' ', '<br />'));
				$('#keyUserRepostsCount').text(result.user.statusRepostsCount);
				$('#keyUserRepostsContent').html('<em>ת�����ݣ�</em>' + result.user.statusText);
			}
		}
	});
}

// �ؼ��û�����·��
function weiboTaskResultKeyUserRoad() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultKeyUserRoad.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
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

// ������
function weiboTaskResultKeyUserTop() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultKeyUserTop.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				keyUserTop = result.userList;
				weiboTaskResultLine(); // ת�����۷���
				
				$('#keyUserTop').empty();
				$('#forwardTop10').empty();
				var html = '';
				var html1 = '';
				$.each(result.userList, function(i, n) {
					if (i > 9)
						return false;
					// ������
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
					html += '			<p>��˿��<em>' + n.userFollowersCount + '</em></p>';
					html += '			<p>����ת����<em>' + n.statusRepostsCount + '</em></p>';
					var statusCreatedAt = n.statusCreatedAt.split('-').join('/');
					html += '			<p><font>ת��ʱ�䣺</font>' + new Date(statusCreatedAt).format("yyyy-MM-dd hh:mm") + '</p>';
					html += '		</div>';
					html += '	</li>';
					
					// ת��TOP
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
					html1 += '						<p>��ע</p>';
					html1 += '					</dd>';
					html1 += '					<dd>';
					html1 += '						<p>' + n.userFollowersCount + '</p>';
					html1 += '						<p>��˿</p>';
					html1 += '					</dd>';
					html1 += '					<dd>';
					html1 += '						<p>' + n.userStatusCount + '</p>';
					html1 += '						<p>΢��</p>';
					html1 += '					</dd>';
					html1 += '				</dl>';
					html1 += '				<div class="mwbcontext">';
					html1 += '					<p>' + n.statusText + '</p>';
					html1 += '				</div>';
					html1 += '				<div class="mfont-buttom">';
					html1 += '					<div class="mfont-buttom_l">';
					html1 += '						<span style="cursor: pointer;" onclick="javascript:window.open(\'' + n.weiboUrl + '\');">' + new Date(statusCreatedAt).format("yyyy-MM-dd hh:mm") + ' </span>';
					// html1 += '						<span>ת����<a href="javascript:;" class="link">@��������</a></span>';
					html1 += '					</div>';
					html1 += '					<div class="mfont-buttom_r">';
					html1 += '						<span class="weibo-multi-panel weibo-list-a">ת��(' + n.statusRepostsCount + ')</span> | ';
					html1 += '						<span class="weibo-multi-panel weibo-list-a">����(' + n.statusCommentsCount + ')</span> | ';
					html1 += '						<span class="weibo-multi-panel weibo-list-a">��(' + n.statusAttitudesCount + ')</span> | ';
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

// ת�����۱������
function weiboTaskResultFace() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultFace.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
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
						html += '		<div style="display:inline-block;width:90%;vertical-align:middle;">';
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

// Ӱ����TOP10
function weiboTaskResultVerifyUser() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultVerifyUser.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				var html = '';
				$('#verifyUser').empty();
				html += '<div class="yxl_top rel">';
				if (result.normalUserList == null || result.normalUserList.length < 1) {
					html += '	<div class="loadingMask">';
					html += '		<span class="waiting noData">�޸���ת���û�</span>';
					html += '	</div>';
				}
				html += '	<div class="y1 float_l">';
				html += '		<div class="y1_1">��ͨ<br>�û�</div>';
				html += '	</div>';
				html += '	<div class="y2 y2_1 float_l">';
				html += '		<ul>';
				if (result.normalUserList != null && result.normalUserList.length > 0) { // �ݸ��û�
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
						html += '			<p>��˿����' + n.userFollowersCount + '</p>';
						html += '			<p>ת����' + n.statusRepostsCount + '</p>';
						html += '		</li>';
					});
				}
				html += '		</ul>';
				html += '	</div>';
				html += '</div>';
				html += '<div class="line4"></div>';
				
				html += '<div class="yxl_top rel">';
				if (result.personalUserList == null || result.personalUserList.length < 1) {
					html += '	<div class="loadingMask">';
					html += '		<span class="waiting noData">�޸���ת���û�</span>';
					html += '	</div>';
				}
				html += '	<div class="y1 float_l">';
				html += '		<div class="y1_2">�� V<br>�û�</div>';
				html += '	</div>';
				html += '	<div class="y2 y2_2 float_l">';
				html += '		<ul>';
				if (result.personalUserList != null && result.personalUserList.length > 0) { // ��V�û�
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
						html += '			<p>��˿����' + n.userFollowersCount + '</p>';
						html += '			<p>ת����' + n.statusRepostsCount + '</p>';
						html += '		</li>';
					});
				}
				html += '		</ul>';
				html += '	</div>';
				html += '</div>';
				html += '<div class="line4"></div>';
				
				html += '<div class="yxl_top rel">';
				if (result.officalUserList == null || result.officalUserList.length < 1) {
					html += '	<div class="loadingMask">';
					html += '		<span class="waiting noData">�޸���ת���û�</span>';
					html += '	</div>';
				}
				html += '	<div class="y1 float_l">';
				html += '		<div class="y1_3">�� V<br>�û�</div>';
				html += '	</div>';
				html += '	<div class="y2 y2_3 float_l">';
				html += '		<ul>';
				if (result.officalUserList != null && result.officalUserList.length > 0) { // ��V�û�
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
						html += '			<p>��˿����' + n.userFollowersCount + '</p>';
						html += '			<p>ת����' + n.statusRepostsCount + '</p>';
						var verTag = '';
						switch (n.userVerifiedType) {
						case 1:
							verTag = '����';
							break;
						case 2:
							verTag = '��ҵ';
							break;
						case 3:
							verTag = 'ý��';
							break;
						case 4:
							verTag = 'У԰';
							break;
						case 5:
							verTag = '��վ';
							break;
						case 6:
							verTag = 'Ӧ��';
							break;
						case 7:
							verTag = '����';
							break;
						default:
							verTag = '��ҵ';
						break;
						}
						if (verTag != '')
							html += '			<em class="tag">' + verTag + '</em>';
						html += '		</li>';
					});
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
				
				shareReadyB = true;
			}
		}
	});
}
//���жȷ���
function weiboTaskResultSensitive() {
	console.log("entrance weiboTaskResultSensitive method!");
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultSensitive.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			console.log(result);
			if (!$.isEmptyObject(result)) {
				var sum = result.sensitive.sensitiveCount + result.sensitive.insensitiveCount;
				if (sum > 0) {
					var sensitivePercent = (result.sensitive.sensitiveCount / sum * 100).toFixed(2);
					$('#sensitivePercent').text(sensitivePercent + '%');
					$('#sensitiveBarWidth').css('width', sensitivePercent + '%');
					$('#insensitivePercent').text((100 - sensitivePercent).toFixed(2) + '%');
				}
			}
		}
	});
}
// ת�����۵������
function weiboTaskResultLocation() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultLocation.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.repostsList != null && result.repostsList.length > 0) { // ת��
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
						html += '<tr>';
						html += '	<td style="border: 1px solid #fff;">' + n.name + '</td>';
						html += '	<td style="border: 1px solid #fff;">' + ((n.value / sumValue * 100).toFixed(2)) + '%</td>';
						html += '</tr>';
					});
					$('#repostsPH').append(html);
					
					var config = require(
						[  
							'echarts',
							'echarts/chart/map'
						],
						function (ec) {
							var chart = ec.init(document.getElementById('weibo_task_result_location_reposts_div'));
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
			                        text: ['��', '��'],
			                        color: ['orangered','yellow','lightskyblue']
							    },
								series : [ {
									name : '����',
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
			       						},
			       						emphasis : {
			       							label : {
			       								show : true
			       							}
			       						}
			       					},
									geoCoord: {
										 "����":[116.46,39.92],  
										 "����":[123.14,41.66],
										 "����":[116.98,32.62],
										 "ɽ��":[117.52,36.23],
										 "�Ϻ�":[121.42,31.12],
										 "����":[119.72,33.66],
										 "����":[111.99,31.54],
										 "�㽭":[119.87,29.24],
										 "�㶫":[112.73,24.02],
										 "����":[117.73,26.17],
										 "����":[111.33,27.75],
										 "����":[113.57,22.51],
										 "����":[113.57,33.86],
										 "����":[109.53,19.15],
										 "�½�":[85.90,40.98],
										 "���ɹ�":[113.87,43.49],
										 "����":[87.01,32.53],
										 "�ຣ":[95.03,36.19],
										 "����":[101.25,24.48],
										 "�Ĵ�":[102.20,30.77],
										 "����":[106.62,26.94],
										 "����":[96.46,40.19],
										 "����":[108.31,23.60],
										 "����":[115.52,27.60],
										 "������":[127.37,47.73],
										 "����":[126.49,43.49],
										 "ɽ��":[111.99,37.20],
										 "�ӱ�":[115.01,38.31],
										 "����":[108.75,34.09],
										 "����":[106.56,29.64],
										 "����":[105.98,37.25],
										 "���":[117.14,39.29],
										 "̨��":[120.90,23.81]
									},
									data :result.repostsList
								} ]
							};
			                chart.setOption(option);
			                var enConfig = require('echarts/config');
						}
					);
				}
				if (result.commentsList != null && result.commentsList.length > 0) { // ����
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
						html += '<tr>';
						html += '	<td style="border: 1px solid #fff;">' + n.name + '</td>';
						html += '	<td style="border: 1px solid #fff;">' + ((n.value / sumValue * 100).toFixed(2)) + '%</td>';
						html += '</tr>';
					});
					$('#commontsPH').append(html);
					
					var config = require(
						[  
							'echarts',
							'echarts/chart/map'
						],
						function (ec) {
							var chart = ec.init(document.getElementById('weibo_task_result_location_comments_div'));
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
			                        text: ['��', '��'],
			                        color: ['orangered','yellow','lightskyblue']
							    },
								series : [ {
									name : '����',
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
			       						},
			       						emphasis : {
			       							label : {
			       								show : true
			       							}
			       						}
			       					},
									geoCoord: {
										 "����":[116.46,39.92],  
										 "����":[123.14,41.66],
										 "����":[116.98,32.62],
										 "ɽ��":[117.52,36.23],
										 "�Ϻ�":[121.42,31.12],
										 "����":[119.72,33.66],
										 "����":[111.99,31.54],
										 "�㽭":[119.87,29.24],
										 "�㶫":[112.73,24.02],
										 "����":[117.73,26.17],
										 "����":[111.33,27.75],
										 "����":[113.57,22.51],
										 "����":[113.57,33.86],
										 "����":[109.53,19.15],
										 "�½�":[85.90,40.98],
										 "���ɹ�":[113.87,43.49],
										 "����":[87.01,32.53],
										 "�ຣ":[95.03,36.19],
										 "����":[101.25,24.48],
										 "�Ĵ�":[102.20,30.77],
										 "����":[106.62,26.94],
										 "����":[96.46,40.19],
										 "����":[108.31,23.60],
										 "����":[115.52,27.60],
										 "������":[127.37,47.73],
										 "����":[126.49,43.49],
										 "ɽ��":[111.99,37.20],
										 "�ӱ�":[115.01,38.31],
										 "����":[108.75,34.09],
										 "����":[106.56,29.64],
										 "����":[105.98,37.25],
										 "���":[117.14,39.29],
										 "̨��":[120.90,23.81]
									},
									data :result.commentsList
								} ]
							};
			                chart.setOption(option);
			                var enConfig = require('echarts/config');
						}
					);
				}
			}
		}
	});
}

// ת�������Ա����
function weiboTaskResultGender() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultGender.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.repostsList != null && result.repostsList.length > 0) {
					var sum = result.repostsList[0].value + result.repostsList[1].value;
					if (sum > 0) {
						$.each(result.repostsList, function(i, n) {
							if (n.name == '��')
								$('#reposts_male-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
							if (n.name == 'Ů')
								$('#reposts_female-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
						});
					}
					var config = require(
						[  
							'echarts',
							'echarts/chart/pie'
						],
						function (ec) {
							var chart = ec.init(document.getElementById('weibo_task_result_gender_reposts_div'));
							option = {
								tooltip : {
							        trigger: 'item',
							        formatter: "{b} : {c} ({d}%)"
							    },
							    color : ['#6a8fbb', '#DB7070'],
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
							var enConfig = require('echarts/config');
						});
				}
					
				if (result.commentsList != null && result.commentsList.length > 0) {
					var sum = result.commentsList[0].value + result.commentsList[1].value;
					if (sum > 0) {
						$.each(result.commentsList, function(i, n) {
							if (n.name == '��')
								$('#comments_male-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
							if (n.name == 'Ů')
								$('#comments_female-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
						});
					}
					var config = require(
						[  
							'echarts',
							'echarts/chart/pie'
						],
						function (ec) {
							var chart = ec.init(document.getElementById('weibo_task_result_gender_comments_div'));
							option = {
								tooltip : {
							        trigger: 'item',
							        formatter: "{b} : {c} ({d}%)"
							    },
							    color : ['#6a8fbb', '#DB7070'],
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
							var enConfig = require('echarts/config');
						});
				}
			}
		}
	});
}

// ���������ʽ
function createRandomItemStyle() {
    return {
        normal: {
            color: 'rgb(' + [
                Math.round(Math.random() * 255),
                Math.round(Math.random() * 255),
                Math.round(Math.random() * 255)
            ].join(',') + ')'
        }
    };
}

// ת�������û���Ȥ��ǩ
function weiboTaskResultUserTag() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultUserTag.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.repostsList != null && result.repostsList.length > 0) {
					$.each(result.repostsList, function(i, n) {
						n.itemStyle = createRandomItemStyle();
					});
					var config = require(
						[  
							'echarts',
							'echarts/chart/wordCloud'
						],
						function (ec) {
							var chart = ec.init(document.getElementById('weibo_task_result_user_tag_reposts_div'));
							option = {
							    series : [
							        {
							        	type : 'wordCloud',
							        	size: ['100%', '100%'],
							            textRotation : [0, 45, 90, -45],
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
							var enConfig = require('echarts/config');
						});
				}
				if (result.commentsList != null && result.commentsList.length > 0) {
					$.each(result.commentsList, function(i, n) {
						n.itemStyle = createRandomItemStyle();
					});
					var config = require(
						[  
							'echarts',
							'echarts/chart/wordCloud'
						],
						function (ec) {
							var chart = ec.init(document.getElementById('weibo_task_result_user_tag_comments_div'));
							option = {
							    series : [
							        {
							        	type : 'wordCloud',
							        	size: ['100%', '100%'],
							            textRotation : [0, 45, 90, -45],
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
							var enConfig = require('echarts/config');
						});
				}
			}
		}
	});
}

// ת�����۷�˿�ֲ�
function weiboTaskResultFans() {
	$.ajax({
		url : actionBase + '/ui/singleWeiboAnalysis/taskResultFans.action',
		type : 'POST',
		data : {
			'taskTicket':$('#taskTicket').val(),
			'tier':$('#currentTaskTier').val(),
			'userId':$('#userId').val()
		},
		success : function(result) {
			if (!$.isEmptyObject(result)) {
				if (result.repostsList != null && result.repostsList.length > 0 && result.commentsList != null && result.commentsList.length > 0) {
					var config = require(
						[
						 	'echarts',
						 	'echarts/chart/bar'
						],
						function (ec) {
							var chart = ec.init(document.getElementById('weibo_task_result_fans_div'));
							option = {
							    tooltip : {
							        trigger: 'axis',
							        formatter: function(params) {
							        	v = '��˿�����䣺' + params[0].name;
							        	for (var i = 0, l = params.length; i < l; i++)
											v += '<br/>' + params[i].seriesName + '������ : ' + params[i].value + '��';
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
					                  name:'ת��',
					                  type:'bar',
					                  data:result.repostsList
					              },
					              {
					                  name:'����',
					                  type:'bar',
					                  data:result.commentsList
					              }
					          ]
							};
							chart.setOption(option);
							var enConfig = require('echarts/config');
						}
					);
				}
			}
		}
	});
}

var shareReadyA = false, shareReadyB = false;
var shareReadyT = setInterval(function() {
	if (shareReadyA && shareReadyB) {
		clearInterval(shareReadyT);
    	
    	var shareContent = getShareContent();
    	if (shareContent != null && shareContent != '')
    		$('head title').text(shareContent);
	}
}, 100);