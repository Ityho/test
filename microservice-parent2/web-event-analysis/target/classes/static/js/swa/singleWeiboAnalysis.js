// 分析微博
function goAnalysisWeibo(count,weiboId, weiboUrl, retry,createTime) {
    console.log("---count---");
    console.log("---goAnalysisWeibo---");
    console.log("---weiboId---"+weiboId);
    console.log("---weiboUrl---"+weiboUrl);
    console.log("---retry---"+retry);
    if(count == 0){
        console.log("转发为0");
        $("#checkMyBoWen").css("display","none");
        $(".modal-backdrop").css("display","none");
        alertMsg('该条微博暂时没有转发层级！');
        return;
    }else{
        if ((weiboUrl != null && weiboUrl != '') || (weiboId != null && weiboId != '')) {
            var url =actionBase+ '/weiboAnalysis/analysis';
            var params = {};
//			'createTime':$('#createTime').val(),
            if(createTime!=null){
                params.createTime=createTime;
            }
            if (weiboId != null && weiboId != '')
                params.weiboId = weiboId;
            if (weiboUrl != null && weiboUrl != '')
                params.weiboURL = weiboUrl;
            // if (retry)
                params.retry = retry;

            if (url != '')
                createForm(url, 'POST', '_self', params);//发送微博分析请求
        }
    }

}
function alertMsg(content) {
    $('#msgContent').text(content);
    $(".zhezhao").addClass('downShow');
    $("#msgPOP").css("display","block");
    //$("body").css({overflow:"hidden"});    //禁用滚动条
    $(".prompPOP").addClass('scaleShow');
    $(".prompPOP").removeClass('scaleOut');
}
// 我的博文
function myWeibo(uid) {
    if (uid != null && uid != "") {
        console.log(uid);
        $.ajax({
            url : njxBasePath + '/weiboAnalysis/myWeibo',
            type : 'POST',
            data : {
                'uid':uid
            },
            success : function(result) {
                if (!$.isEmptyObject(result)) {
                    console.log(result);
                    $('#navDiv').empty();
                    $.each(result.statusList, function(i, n) {
                        var html = '<li style="width: 100%;">';
                        html += '		<div class="mwbcontext">';
                        html += '			<p>' + n.statusText + '</p>';
                        if (n.retweetedStatus != null) {
                            html += '<div class="zf_text">';
                            html += '	<div class="m_r">';
                            html += '		<p>';
                            html += '			<a class="mscrame" href="javascript:;" target="_bank" onclick="javascript:window.open(\'http://weibo.com/' + n.retweetedStatus.user.userId + '\');">' + n.retweetedStatus.user.userScreenName + '</a>';
                            if (n.retweetedStatus.user.userVerifiedType == 0)
                                html += '		<a href="javascript:;"><i title="微博个人认证" class="W_icon icon_approve"></i></a>';
                            else if (n.retweetedStatus.user.userVerifiedType >= 1 && n.retweetedStatus.user.userVerifiedType <= 7)
                                html += '		<a href="javascript:;"><i title="微博机构认证" class="W_icon icon_approve_co"></i></a>';
                            html += '		</p>';
                            html += '		<div class="mwbcontext">';
                            html += n.retweetedStatus.statusText;
                            html += '		</div>';
                            html += '		<div class="mfont-buttom">';
                            html += '			<div class="mfont-buttom_l">';
                            html += '				<span style="cursor: pointer;" onclick="javascript:window.open(\'' + n.retweetedStatus.url + '\');">' + n.retweetedStatus.statusCreatedAt + '</span>';
                            html += '			</div>';
                            html += '			<div class="mfont-buttom_r">';
                            html += '				<a class="weibo-multi-panel weibo-list-a">转发(' + n.retweetedStatus.statusRepostsCount + ')</a> |';
                            html += '				<a class="weibo-multi-panel weibo-list-a">评论(' + n.retweetedStatus.statusCommentsCount + ')</a> |';
                            html += '				<a class="weibo-multi-panel weibo-list-a">赞(' + n.retweetedStatus.statusAttitudesCount + ')</a> |';
                            html += '				<a href="javascript:;" onclick="goAnalysisWeibo('+n.retweetedStatus.statusRepostsCount+',null, \'' + n.retweetedStatus.url + '\', false)" class="link">分析</a>';
                            html += '			</div>';
                            html += '		</div>';
                            html += '	</div>';
                            html += '</div>';
                        }
                        html += '		</div>';
                        html += '		<div class="mfont-buttom">';
                        html += '			<div class="mfont-buttom_l">';
                        html += '				<span style="cursor: pointer;" onclick="javascript:window.open(\'' + n.url + '\');">' + new Date(n.statusCreatedAt.split('-').join('/')).format("yyyy-MM-dd hh:mm") + '</span>';
                        html += '			</div>';
                        html += '			<div class="mfont-buttom_r">';
                        html += '				<a class="weibo-multi-panel weibo-list-a">转发(' + n.statusRepostsCount + ')</a> |';
                        html += '				<a class="weibo-multi-panel weibo-list-a">评论(' + n.statusCommentsCount + ')</a> |';
                        html += '				<a class="weibo-multi-panel weibo-list-a">赞(' + n.statusAttitudesCount + ')</a> |';
                        html += '			</div>';
                        html += '			<div class="analyze2">';
                        html += '				<a href="javascript:;" onclick="goAnalysisWeibo('+n.statusRepostsCount+',null, \'' + n.url + '\', false)" class="fxbtn">分析</a>';
                        html += '			</div>';
                        html += '		</div>';
                        html += '	</li>';
                        $('#navDiv').append(html);
                    });
                }
            }
        });
    }
}

// 获取微博内容
function weiboTaskResultStatus() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultStatus',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
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
                    $('#weiboContentForwardNumber').text('转发(' + result.status.statusRepostsCount + ')');
                    $('#weiboContentCommentNumber').text('评论(' + result.status.statusCommentsCount + ')');
                    $('#weiboContentPraiseNumber').text('赞(' + result.status.statusAttitudesCount + ')');

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
                        sharePic = result.status.user.userLogoUrl;
                    }
                    $('#weiboContentButtomTime,#weiboContentNote').css('display', 'block');
                } else {
                    setTimeout('weiboTaskResultStatus()', 1000);
                }
            } else {
                setTimeout('weiboTaskResultStatus()', 1000);
            }
        }
    });
}
var timeFormat = new Date().Format("yyyy-MM-dd HH:mm:ss");
// 获取微博分析状态
function weiboTaskStatus() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskStatus',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket' : $('#taskTicket').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
            if (!$.isEmptyObject(result) && !$.isEmptyObject(result.task)) {
                if (result.task.analysisStatus == 0) {
                    $('.loadingMask .waiting').text('微博分析失败，请确认微博链接是否正确！');
                } else {

                    if (result.task.analysisStatus == 1) { // 待分析
                        if(result.newVersion==true){
                            console.log("result.task.schedulePercent="+result.task.schedulePercent+"|timeFormat="+timeFormat);
                        }
                        console.log('status waiting...');
                    } else if (result.task.analysisStatus == 2 || result.task.analysisStatus == 3) { // 正在分析/分析完成
                        if(result.newVersion==true){
                            console.log("Status="+result.task.analysisStatus+"result.task.schedulePercent="+result.task.schedulePercent+"|timeFormat="+timeFormat);
                            if(result.task.analysisStatus == 3 && result.task.schedulePercent==100){
                                console.log("analysisStatus="+result.task.schedulePercent+"|timeFormat="+timeFormat);
                                reloadWeiboTask();
                            }
                        }else{
                            if (result.task.finishedTier > $('#currentTaskTier').val()) { // 有新层级
                                $('#currentTaskTier').val(result.task.finishedTier);
                                reloadWeiboTask();

                                var finishPercent = (result.task.finishedTier / result.task.totalTier * 100).toFixed(0);
                                $('.progressLine').css('width', finishPercent + '%');
                                $('.progressLine').text(finishPercent + '%');
                            }
                        }
                    } else if (result.task.analysisStatus == 4) { // 等待确认分析
                        console.log('waiting confirm analysis!');
                    }
                    if (result.task.analysisStatus != 3 || result.task.schedulePercent<100) { // 未分析完成，则继续请求更新
                        $("#maskModal").css("display","block");
                        $('body').css("position","fixed");
                        $('.loadingMask .waiting').text('系统正在分析');
                        $('.loadingMask .waiting').append('<div class="sk-wave"><div class="sk-rect sk-rect1"></div><div class="sk-rect sk-rect2"></div><div class="sk-rect sk-rect3"></div><div class="sk-rect sk-rect4"></div></div>');
                        $("#maskContent").append($('.loadingMask .waiting'));
                        setTimeout('weiboTaskStatus()', 5000);

                    } else {
                        $('.loadingData').text('微博分析完成');
                        setTimeout(function() {
                            $('.loadingData').css('display', 'none');
                            $('#maskModal').css('display','none');
                            $('body').css('position','');
                        }, 3000);
                        if (result.task.totalTier == 0) {
                            $('.loadingMask .waiting').html('<a class="closeBtn" href="javascript:;" onclick="javascript:location.href = \'' + basePath + '/weibo/toAnalysis\';">×</a>该条微博无转发层级！');
                        }
                    }
                }
            }
        }
    });
}

// 显示内容DIV
function showContent() {
    $('.loadingMask').css('display', 'none');
    $('.loadingData').css('display', 'block');
}

// 微博分析
function reloadWeiboTask() {
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
    showContent(); // 显示内容DIV
}

// 传播节点
function weiboTaskResultStar() {
    $('#weibo_task_result_star_content_div').html('<img src="' + staticResourcePath + '/images/loading.gif" style="width:16px; height:16px;" />');
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultStar',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
            $('#weibo_task_result_star_content_div').empty();
            try {
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
                });
            } catch (e) {
                alert(e);
            }
        }
    });
}

// 转发层级
function weiboTaskResultTier() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultTier',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
            if (!$.isEmptyObject(result)) {
                var html = '';
                $('.table_list.table_list_h5').empty();
                $.each(result.transTierList, function(i, n) {
                    if (i == 0) { // 总
                        $('#tierSpan').text(result.transTierList.length - 1);
                        $('#tierRepostsSpan').text(n.tierInfo.repostsUserCount);
                        $('#tierCoverUserSpan').text(n.tierInfo.repostsCoverUserCount);
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

//转发评论曲线
function weiboTaskResultLine() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultLine',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
            if (!$.isEmptyObject(result)) {
                var bubbleHide;
                var lineHide;
                var interval;
                if (result.legend.length > 9)
                    interval = ~~ (result.legend.length / 9);
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
                        color: "#ec6a3a",
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
            }
        }
    });
}

// 传播关键用户
function weiboTaskResultKeyUser() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultKeyUser',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
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
                });
                $('#keyUserFollowsCount').text(result.user.userFollowersCount);
                $('#keyUserRepostsTime').text(result.user.statusCreatedAt);
                $('#keyUserRepostsCount').text(result.user.statusRepostsCount);
                $('#keyUserRepostsContent').html('<em>转发内容：</em>' + result.user.statusText);
            }
        }
    });
}

// 关键用户传播路径
function weiboTaskResultKeyUserRoad() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultKeyUserRoad',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
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

// 引爆点
function weiboTaskResultKeyUserTop() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultKeyUserTop',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
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
                    html1 += '				<div class="mwbcontext">';
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
        url : actionBase + '/weiboAnalysis/taskResultFace',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
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
                        if (i > 9){
                            return false;
                        }
                        html += '	<li style="margin-bottom:5px;padding-top:5px;list-style-type:none;">';
                        if (n.faceUrl == null || n.faceUrl == '')
                            html += n.faceName;
                        else
                            html +=' <img src="' + n.faceUrl + '" border="0" border="0" title="' + n.faceName + '" alt="' + n.faceName + '" style="height:22px;margin-right:6px;width:22px;vertical-align:middle;" />';
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
function newVerifyUser(result){
    var html = '';
    $('#verifyUser').empty();
    html += '<div class="yxl_top rel">';
    if (result.extendStats.iContentCommonNetLists[0] == null ||
        result.extendStats.iContentCommonNetLists[0].iContentCommonNetList==null ||
        result.extendStats.iContentCommonNetLists[0].iContentCommonNetList.length < 1) {
        html += '	<div class="loadingMask">';
        html += '		<span class="waiting noData" style="line-height:2px;">无该类转发用户</span>';
        html += '	</div>';
    }
    html += '	<div class="y1 float_l">';
    html += '		<div class="y1_1">普通<br>用户</div>';
    html += '	</div>';
    html += '	<div class="y2 y2_1 float_l">';
    html += '		<ul>';
    if (result.extendStats.iContentCommonNetLists[0].iContentCommonNetList != null && result.extendStats.iContentCommonNetLists[0].iContentCommonNetList.length > 0) { // 草根用户
        $.each(result.extendStats.iContentCommonNetLists[0].iContentCommonNetList, function(i, n) {
            if (i > 0 && i % 5 == 0) {
                html += '</ul>';
                html += '<ul>';
            }
            if (n.author == null)
                n.author = '';
            html += '		<li title="' + n.author+ '" onclick="javascript:window.open(\'' + n.webpageUrl + '\');">';
            html += '			<p>' + n.author.substring(0, 5);
            if (n.author.length > 5)
                html += '...';
            html += '			</p>';
            html += '			<p><div class="tx"><img src="' + n.profileImageUrl + '" /></div></p>';
            html += '			<p>粉丝数：' + n.fansNumber + '</p>';
            html += '			<p>转发：' + n.forwardNumber + '</p>';
            html += '		</li>';
        });
    }
    html += '		</ul>';
    html += '	</div>';
    html += '</div>';
    html += '<div class="line4"></div>';

    html += '<div class="yxl_top rel">';
    if (result.extendStats.iContentCommonNetLists[2].iContentCommonNetList == null || result.extendStats.iContentCommonNetLists[2].iContentCommonNetList.length < 1) {
        html += '	<div class="loadingMask">';
        html += '		<span class="waiting noData" style="line-height:2px;">无该类转发用户</span>';
        html += '	</div>';
    }
    html += '	<div class="y1 float_l">';
    html += '		<div class="y1_2">橙 V<br>用户</div>';
    html += '	</div>';
    html += '	<div class="y2 y2_2 float_l">';
    html += '		<ul>';
    if (result.extendStats.iContentCommonNetLists[2].iContentCommonNetList != null && result.extendStats.iContentCommonNetLists[2].iContentCommonNetList.length > 0) { // 橙V用户
        $.each(result.extendStats.iContentCommonNetLists[2].iContentCommonNetList, function(i, n) {
            if (i > 0 && i % 5 == 0) {
                html += '</ul>';
                html += '<ul>';
            }
            if (n.author == null)
                n.author = '';
            html += '		<li title="' + n.author+ '" onclick="javascript:window.open(\'' + n.webpageUrl + '\');">';
            html += '			<p>' + n.author.substring(0, 5);
            if (n.author > 5)
                html += '...';
            html += '			</p>';
            html += '			<p><div class="tx v_yellow"><img src="' + n.profileImageUrl + '" /></div></p>';
            html += '			<p>粉丝数：' + n.fansNumber + '</p>';
            html += '			<p>转发：' + n.forwardNumber + '</p>';
            html += '		</li>';
        });
    }
    html += '		</ul>';
    html += '	</div>';
    html += '</div>';
    html += '<div class="line4"></div>';

    html += '<div class="yxl_top rel">';
    if (result.extendStats.iContentCommonNetLists[1].iContentCommonNetList == null || result.extendStats.iContentCommonNetLists[1].iContentCommonNetList.length < 1) {
        html += '	<div class="loadingMask">';
        html += '		<span class="waiting noData" style="line-height:2px;">无该类转发用户</span>';
        html += '	</div>';
    }
    html += '	<div class="y1 float_l">';
    html += '		<div class="y1_3">蓝 V<br>用户</div>';
    html += '	</div>';
    html += '	<div class="y2 y2_3 float_l">';
    html += '		<ul>';
    if (result.extendStats.iContentCommonNetLists[1].iContentCommonNetList != null && result.extendStats.iContentCommonNetLists[1].iContentCommonNetList.length > 0) { // 蓝V用户
        $.each(result.extendStats.iContentCommonNetLists[1].iContentCommonNetList, function(i, n) {
            if (i > 0 && i % 5 == 0) {
                html += '</ul>';
                html += '<ul>';
            }
            if (n.author == null)
                n.author = '';
            html += '		<li title="' + n.author+ '" onclick="javascript:window.open(\'' + n.webpageUrl + '\');">';
            html += '			<p>' + n.author.substring(0, 5);
            if (n.author.length > 5)
                html += '...';
            html += '			</p>';
            html += '			<p><div class="tx v_blue"><img src="' + n.profileImageUrl + '" /></div></p>';
            html += '			<p>粉丝数：' + n.fansNumber + '</p>';
            html += '			<p>转发：' + n.forwardNumber + '</p>';
            var verTag = '';
            switch (n.verifiedType) {
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
        });
    }
    html += '		</ul>';
    html += '	</div>';
    html += '</div>';
    $('#verifyUser').append(html);

    $.merge($.merge(result.extendStats.iContentCommonNetLists[0].iContentCommonNetList, result.extendStats.iContentCommonNetLists[2].iContentCommonNetList), result.extendStats.iContentCommonNetLists[1].iContentCommonNetList);
    result.extendStats.iContentCommonNetLists[0].iContentCommonNetList.sort(compare('fansNumber'));
    if (result.extendStats.iContentCommonNetLists[0].iContentCommonNetList[0])
        shareTopUser1 = result.extendStats.iContentCommonNetLists[0].iContentCommonNetList[0].author;
    if (shareTopUser1 = result.extendStats.iContentCommonNetLists[0].iContentCommonNetList[1])
        shareTopUser2 = shareTopUser1 = result.extendStats.iContentCommonNetLists[0].iContentCommonNetList[1].author;
    if (shareTopUser1 = result.extendStats.iContentCommonNetLists[0].iContentCommonNetList[2])
        shareTopUser3 = shareTopUser1 = result.extendStats.iContentCommonNetLists[0].iContentCommonNetList[2].author;
}
// 影响力TOP10
function weiboTaskResultVerifyUser() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultVerifyUser',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
            if(!$.isEmptyObject(result)  && result.newVersion==true){
                newVerifyUser(result);
            }else if (!$.isEmptyObject(result)) {
                var html = '';
                $('#verifyUser').empty();
                html += '<div class="yxl_top rel">';
                if (result.normalUserList == null || result.normalUserList.length < 1) {
                    html += '	<div class="loadingMask">';
                    html += '		<span class="waiting noData" style="line-height:2px;">无该类转发用户</span>';
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
                    });
                }
                html += '		</ul>';
                html += '	</div>';
                html += '</div>';
                html += '<div class="line4"></div>';

                html += '<div class="yxl_top rel">';
                if (result.personalUserList == null || result.personalUserList.length < 1) {
                    html += '	<div class="loadingMask">';
                    html += '		<span class="waiting noData" style="line-height:2px;">无该类转发用户</span>';
                    html += '	</div>';
                }
                html += '	<div class="y1 float_l">';
                html += '		<div class="y1_2">橙 V<br>用户</div>';
                html += '	</div>';
                html += '	<div class="y2 y2_2 float_l">';
                html += '		<ul>';
                if (result.personalUserList != null && result.personalUserList.length > 0) { // 橙V用户
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
                    });
                }
                html += '		</ul>';
                html += '	</div>';
                html += '</div>';
                html += '<div class="line4"></div>';

                html += '<div class="yxl_top rel">';
                if (result.officalUserList == null || result.officalUserList.length < 1) {
                    html += '	<div class="loadingMask">';
                    html += '		<span class="waiting noData" style="line-height:2px;">无该类转发用户</span>';
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
            }
        }
    });
}

// 转发评论地域分析
//regionalDistribution
function weiboTaskResultLocation() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultLocation',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
            if (!$.isEmptyObject(result)) {
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
                        if(n.name!="其它"){
                            html += '<tr>';
                            html += '	<td style="border: 1px solid #fff;">' + n.name + '</td>';
                            html += '	<td style="border: 1px solid #fff;">' + ((n.value / sumValue * 100).toFixed(2)) + '%</td>';
                            html += '</tr>';
                        }
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
                                    text: ['高', '低'],
                                    color: ['orangered','yellow','lightskyblue']
                                },
                                series : [ {
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
                            var enConfig = require('echarts/config');
                        }
                    );
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
                        if(n.name!="其它"){
                            html += '<tr>';
                            html += '	<td style="border: 1px solid #fff;">' + n.name + '</td>';
                            html += '	<td style="border: 1px solid #fff;">' + ((n.value / sumValue * 100).toFixed(2)) + '%</td>';
                            html += '</tr>';
                        }

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
                                    text: ['高', '低'],
                                    color: ['orangered','yellow','lightskyblue']
                                },
                                series : [ {
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
                            var enConfig = require('echarts/config');
                        }
                    );
                }
            }
        }
    });
}

// 转发评论性别分析
function weiboTaskResultGender() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultGender',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
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
                            if (n.name == '男')
                                $('#reposts_male-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
                            if (n.name == '女')
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
                                        center: ['45%', '50%'],
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
                            if (n.name == '男')
                                $('#comments_male-fans-scale').text((n.value / sum * 100).toFixed(2) + '%');
                            if (n.name == '女')
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
                                        center: ['45%', '50%'],
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

// 生成随机样式
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

// 转发评论用户兴趣标签
function weiboTaskResultUserTag() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultUserTag',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
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
//敏感度分析
function weiboTaskResultSensitive() {
    console.log("entrance weiboTaskResultSensitive method!");
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultSensitive',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
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

//转发评论热词
function weiboTaskResultHotWords() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultHotWords',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
            if (!$.isEmptyObject(result)) {
                var html = '';
                var index = 0;
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
                        if (i == 19)
                            return false;
                    });
                }
                $('#hotWordsTBody').empty();
                $('#hotWordsTBody').html(html);
                // 词云
//				result.sort(function(a, b) {
//					return (b.value - a.value);
//				});
                //放大词云过小的情况
                for(var i=0;i<result.length;i++){
                    if(result[i]!=null&&result[i].value<=10){
                        result[i].value = result[i].value*10;
                    }
                }
                var arr = new Array();
                var index = 0;
                $.each(result, function(i, n) {
                    if(n!=null){
                        n.itemStyle = createRandomItemStyle();
                        arr[index] = n;
                        index++;
                    }
                });
                var config = require(
                    [
                        'echarts',
                        'echarts/chart/wordCloud'
                    ],
                    function (ec) {
                        var chart = ec.init(document.getElementById('weibo_task_result_hot_words_div'));
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
                        var enConfig = require('echarts/config');
                        chart.on(enConfig.EVENT.CLICK, function(param) {
                            if (param) {
                                weiboTaskResultHotWordsDetail(param.name);
                            }
                        });
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
        url : actionBase + '/weiboAnalysis/taskResultHotWordsDetail',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val(),
            'keyWords':keywords
        },
        success : function(result) {
            if (!$.isEmptyObject(result)) {
                var html = '';
                $.each(result.userList, function(i, n) {
                    if(n!=null){
                        html += '<tr>';
                        html += '	<td><span class="f_c1">' + n.userScreenName + '</span></td>';
                        html += '	<td>' + n.statusText + '</td>';
                        html += '	<td>' + n.statusRepostsCount + '</td>';
                        html += '</tr>';
                    }

                    if (i == 19)
                        return false;
                });
                $('#hotWordsDetailTBody').empty();
                $('#hotWordsDetailTBody').html(html);
            }
        }
    });
}
// 转发评论粉丝分布
function weiboTaskResultFans() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultFans',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
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
                                        v = '粉丝量区间：' + params[0].name;
                                        for (var i = 0, l = params.length; i < l; i++)
                                            v += '<br/>' + params[i].seriesName + '者人数 : ' + params[i].value + '人';
                                        return v;
                                    }
                                },
                                grid:{
                                    y2:80,
                                    x:30,
                                    x2:30,
                                    //y:60
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
                                        data:result.repostsList
                                    },
                                    {
                                        name:'评论',
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
//发布设备来源
function weiboTaskResultSource() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultSource',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
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

                var config = require(
                    [
                        'echarts',
                        'echarts/chart/pie'
                    ],
                    function (ec) {
                        var chart = ec.init(document.getElementById('weibo_task_result_source_r_div'));
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
                                    center:['72%',100],
                                    radius : ['15%','45%'],
                                    roseType : 'radius',
                                    sort : 'ascending',
                                    itemStyle : {
                                        normal : {
                                            label : {
                                                show : true,
                                                textStyle:{
                                                    fontSize:'12',
                                                    fontWeight:'normal'
                                                },
                                                formatter: "{d}%"
                                            },
                                            labelLine : {
                                                show : true
                                            }
                                        },
                                        emphasis : {
                                            label : {
                                                show : false,
                                                position : 'center',
                                                textStyle : {
                                                    fontSize : '30',
                                                    fontWeight : 'bold'
                                                }
                                            }
                                        }
                                    },
                                    data : objArr
                                }
                            ]
                        };
                        chart.setOption(option);
                        var enConfig = require('echarts/config');
                    });
            }
        }
    });
}
//网友观点分析
function weiboTaskResultViewPoint() {
    $.ajax({
        url : actionBase + '/weiboAnalysis/taskResultViewPoint',
        type : 'POST',
        data : {
            'markType':$('#markType').val(),
            'createTime':$('#createTime').val(),
            'taskTicket':$('#taskTicket').val(),
            'tier':$('#currentTaskTier').val(),
            'userId':$('#userId').val()
        },
        success : function(result) {
            if (!$.isEmptyObject(result)) {
                var sum = 0;
                $.each(result.list, function(i, n) {
                    sum  = sum + n.viewPointCount;
                });
                var html = '';
                $.each(result.list, function(i, n) {
                    html += '<li>';
                    html += '	<h2>' + n.viewPointName + '(' + (n.viewPointCount / sum * 100).toFixed(0) + '%)</h2>';
                    $.each(n.viewPointList, function(i1, n1) {
                        html += '	<p>';
                        html += '		<a href="javascript:;">' + n1 + '</a>';
                        html += '	</p>';
                    });
                    html += '</li>';
                });
                $('#weibo_task_result_view_point_ul').empty();
                $('#weibo_task_result_view_point_ul').html(html);

                var objArr = new Array();
                var legendArr = new Array();
                var colors = ['#f5bc00', '#bb814e', '#f29300', '#3454a1', '#277bc0', '#72c1be', '#9e9d9e', '#ec6a3a', '#d44e24', '#a05623'];
                $.each(result.chart, function(i, n) {
                    if(n.viewPointName!=null){
                        var obj = {};
                        obj.name = n.viewPointName.length > 20 ? n.viewPointName.substring(0, 20) : n.viewPointName + ' ' + (n.viewPointCount / sum * 100).toFixed(0) + '%';
                        obj.value = n.viewPointCount;
                        obj.itemStyle = {};
                        obj.itemStyle.normal = {};
                        obj.itemStyle.normal.color = colors[i];
                        objArr.push(obj);
                        if (n.viewPointName.length > 20)
                            legendArr.push(n.viewPointName.substring(0, 20) + ' ' + (n.viewPointCount / sum * 100).toFixed(0) + '%');
                        else
                            legendArr.push(n.viewPointName + ' ' + (n.viewPointCount / sum * 100).toFixed(0) + '%');
                    }
                });
                objArr.reverse();
                legendArr.reverse();

                var config = require(
                    [
                        'echarts',
                        'echarts/chart/bar'
                    ],
                    function (ec) {
                        var chart = ec.init(document.getElementById('weibo_task_result_view_point_div'));
                        option = {
                            tooltip : {
                                trigger: 'axis',
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
                                    type : 'value'
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
                        var enConfig = require('echarts/config');
                    }
                );
            }
        }
    });
}

var sc = {
    svg : 'svg',
    canvas : 'canvas'
};
// 是否已支付
function checkSWAPayment() {
    console.log("是否支付");
    weiboTaskResultStatus(); // 获取微博内容
    console.log("获取微博内容");
    console.log("needPay val = "+$('#needPay').val());
    if ($('#needPay').val() == 'true' && parseInt($('#repostsCount').val()) > 0) {
        $('#divPay').empty();
        var html = '';
        html += '<ul>';
        html += '	<li>';
        html += '		<span>转发' + $('#repostsCount').val() + '条</span>';
        html += '		<span class="float_r">';
        var _a = 0;
        if ($('#firstBuy').val() == 'false')
            _a = (parseFloat($('#cost').val()) - parseFloat($('#no_first_v_normal').val())).toFixed(2);
        html += '			<font class="f-d">原价' + $('#cost').val() + '元</font>共为您节省<font class="f-r">' + _a + '</font>元';
        html += '		</span>';
        html += '	</li>';
        if ($('#firstBuy').val() == 'false') {
            html += '	<li>';
            html += '		<span>本月非首次购买，享' + ($('#discount_nofirstbuy').val() * 10) + '折</span>';
            html += '		<span class="float_r">立省<font class="f-r">' + (parseFloat($('#cost').val()) - parseFloat($('#no_first_v_normal').val())).toFixed(2) + '</font>元</span>';
            html += '	</li>';
        }
        if ($('#firstBuy').val() == 'true') {
            html += '	<li><span class="f-g">还可享受的优惠</span></li>';
            html += '	<li>';
            html += '		<span class="f-g">本月首次购买，下次享' + ($('#discount_nofirstbuy').val() * 10) + '折</span>';
            html += '		<span class="float_r f-g">可省<font class="f-r">' + (parseFloat($('#cost').val()) - parseFloat($('#no_first_v_normal').val())).toFixed(2) + '</font>元</span>';
            html += '	</li>';
        }
        html += '</ul>';
        html += '<div class="buttom">';
        if ($('#firstBuy').val() == 'true') {
            html += '	总价：<font class="f-t">' + $('#cost').val() + '</font>元';
            $('#finalCost').val($('#cost').val());
        } else {
            html += '	总价：<font class="f-t">' + $('#no_first_v_normal').val() + '</font>元';
            $('#finalCost').val($('#no_first_v_normal').val());
        }
        html += '</div>';

        $('#divPay').append(html);
        //$('#payPOP').modal('show');
        $(".formPackage .cont .yf span").text($('#finalCost').val());


        $("#forwardedNum1").text($('#repostsCount').val());
        $("#product1006Num").val($('#repostsCount').val());
        $("#fenxiWeiboId").val($("#weiboId").val());
        $.post(actionBase+"/pay/getOrderFeeV2",$("#selfProductForm").serialize(),function(data){
            if(data.obj.totalFee!=null){
                if($("#useCredit1").val() == 'false'){
                    $("#creditTotalFee1").text('￥'+data.obj.totalFee.toFixed(2));
                    $("#needwjf2").text('￥'+data.obj.totalFee.toFixed(2));
                    $(".btn-pay").attr("onclick","buyProductpro()");
                }else {
                    $("#creditTotalFee1").text(data.obj.creditAmountFee+'微积分');
                    $("#needwjf2").text(data.obj.creditAmountFee+'微积分');
                    if(data.obj.userCreditNum<data.obj.creditAmountFee){
                        $("#nowwjf").text(data.obj.userCreditNum);
                        $("#needwjf").text(data.obj.creditAmountFee-data.obj.userCreditNum);
                        $("#goProductCartBtn").attr("onclick","failCredit()");
                    }
                }
            }else {
                $("#creditTotalFee1").text('￥0.00');
            }
        });
        $('.popver-single').show();
        $('.popver-mask').show();

        $('.loadingMask .waiting').html('<a class="closeBtn" href="javascript:;" onclick="javascript:location.href = \'' + actionBase + '/i/singleWeiboAnalysis/index.shtml\';">×</a>您还未支付分析费用<br />可前往分析记录中完成支付哦~^0^~');
        /*showBuy({type:7,buyBefore:function(){
            $("#forwardedNum").text($('#repostsCount').val());
            $(".openBuyPrompt input[name='packageCount']").val(parseInt($('#repostsCount').val()));
            $(".openBuyPrompt input[name='fenxiWeiboId']").val($("#weiboId").val());
            $('.loadingMask .waiting').html('<a class="closeBtn" href="javascript:;" onclick="javascript:location.href = \'' + actionBase + '/i/singleWeiboAnalysis/index.shtml\';">×</a>您还未支付分析费用<br />可前往分析记录中完成支付哦~^0^~');
        }});*/
        $('#payPOP').on('hide.bs.modal', function () {
            $('.loadingMask .waiting').html('<a class="closeBtn" href="javascript:;" onclick="javascript:location.href = \'' + actionBase + '/i/singleWeiboAnalysis/index.shtml\';">×</a>您还未支付分析费用<br />可前往分析记录中完成支付哦~^0^~');
        });
    } else {
        if ($('#taskTicket').val()) {
            // 获取微博分析状态
            weiboTaskStatus();
        } else {
            $('.loadingMask .waiting').text('微博分析失败，请确认微博链接是否正确！');
        }
    }
}
