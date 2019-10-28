<%@ page  pageEncoding="gbk" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%
        String njxBasePath = request.getContextPath();
        String staticResourcePath = njxBasePath;
    %>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <meta charset="GBK">
    <title>΢�ȵ�(΢����)_������Ϣ���</title>
    <META name="keywords" content="΢�ȵ�(΢����)_����������">
    <META name="description" content="΢�ȵ�(΢����)_����������">
    <link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/font-icon.css"/>
    <link href="<%=njxBasePath%>/css/style.css" rel="stylesheet" type="text/css">
    <link href="<%=njxBasePath%>/css/common.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/dayReport.css"/>
    <link href="<%=njxBasePath%>/css/tips.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="<%=njxBasePath%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/index.js"></script>
    <script src="<%=njxBasePath%>/js/jquery.JPlaceholder.js"></script>

    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/echarts.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/pie.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/bar.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/line.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/wordCloud.js" charset="UTF-8"></script>
</head>
<body class="mobileStyle dayReport">

<input type="hidden" id="userId" value="${keywordReportRecord.userId}">
<input type="hidden" id="keywordId" value="${keywordReportRecord.keywordId}">
<input type="hidden" id="code" value="${keywordReportRecord.shareCode}">

<div id="head" class="rel">
    <span class="logo abs" onclick="javascript:location.href='http://www.wrd.cn'"></span>
    <div class="nav abs">
        <a href="http://www.wrd.cn/login.shtml">��ҳ</a>
        <a href="http://www.wrd.cn/product.shtml">��Ʒ����</a>
        <a href="http://www.wrd.cn/novice.shtml">��������</a>
        <a href="http://www.wrd.cn/help.shtml">��������</a>
        <c:if test="${empty loginType}"><a href="http://www.wrd.cn/downLoad.shtml" onclick="recordDown()">�ͻ�������</a></c:if>
    </div>
    <span class="r_btn abs">
        <c:if test="${not empty loginType}">
            <c:if test="${empty userHead}">
                <img width="43" height="43" src="<%=njxBasePath%>/images/001.jpg" class="tx">
            </c:if>
            <c:if test="${not empty userHead}">
                <img src='${userHead }' width="43" height="43"  class="tx">
            </c:if>
        </c:if>
        <c:if test="${empty loginType}">
            <a href="www.wrd.cn" class="loginBtn" id="loginBtn" onclick="recordSign()">ǩ��</a><%--<a href="javascript:void(0);" class="RegistBtn" onclick="isMobile();">ע��</a>--%>
        </c:if>

    </span>
</div>

<div class="page-container2">
    <!--�ձ� start-->
    <div class="content mt15">
        <!--����ͷ�� start-->
        <div class="row-fluid">
            <div class="con Report-header">
                <em>�ձ�</em>
                <h1 id="reportName"></h1>
                <p id="reportDate"></p>
                <%--<p>��99��</p>--%>
            </div>
        </div>
        <!--����ͷ�� end-->

        <!--������Ϣ start-->
        <c:if test="${empty shareType}">
            <div class="row-fluid" style="margin-top: -20px;">
                <div class="con">
                    <div class="title">
                        <h3>- �װ���:${nickname}-</h3>
                        <%--<h2><span>���ļ�ⷽ��Ϊ(${keywordReportRecord.keywordContent})</span></h2>--%>
                    </div>
                    <div class="align_c">
                        <p>����Լ���ձ������⣬���Ե��<a href="javascript:jumpLogin()" class="link" id="loginBtn2">����</a>��¼�޸�Ŷ��</p>
                    </div>
                </div>
            </div>
        </c:if>
        <!--������Ϣ end-->
        <div class="line"></div>
        <!--�����ձ�ӡ�� start-->
        <div class="row-fluid">
            <div class="con">
                <div class="title">
                    <h1>ȫ��ӡ��</h1>
                </div>
                <div class="align_c">
                    <p>�����漰����Ϣ����ͳ��</p>
                </div>

                <div class="WordCloudTable" style="width: 94%;height: 500px;" id="treeMap">

                </div>
            </div>
        </div>
        <!--�����ձ�ӡ�� end-->

        <div class="line"></div>

        <!--��Ϣ���� start-->
        <%--<div class="row-fluid">
            <div class="con">
                <div class="title">
                    <h1>��Ϣ����</h1>
                </div>
                <div class="mwbBorder chart" style="width: 100%;height: 400px;" id="hotLine">

                </div>
            </div>
        </div>--%>
        <!--��Ϣ���� end-->

        <div class="line"></div>

        <!--��Դռ��ͼ start-->
        <div class="row-fluid">
            <div class="con">
                <div class="title">
                    <h1>��Դռ��</h1>
                </div>
                <div class="mwbBorder chart">
                    <div style="width:100%;height: 300px;" id="originPie">

                    </div>
                    <div class="censusBox">
                        <ul>
                            <li>
                                <p>����</p>
                                <p id="mgNum"></p>
                            </li>
                            <li>
                                <p>������</p>
                                <p id="fmgNum">198��</p>
                            </li>
                        </ul>
                    </div>
                </div>
                <%--<div class="text">����������Ϣռ�Ȳ���20%�����ʵ���ע</div>--%>
            </div>
        </div>
        <!--��Դռ��ͼ end-->

        <div class="line"></div>

        <%--<!--��΢������ start-->
        <div class="row-fluid">
            <div class="con">
                <div class="title">
                    <h1>�۵����</h1>
                </div>
                <div class="text" id="weiboView"></div>
                <div class="mwbBorder chart" style="width:100%;height: 400px" id="viewBar">

                </div>

            </div>
            &lt;%&ndash;</div>
            <!--΢���۵���� end-->

            <div class="line"></div>

            <!--��Ҫ��Ϣ���� start-->
            <div class="row-fluid">&ndash;%&gt;
            <div class="con">
                &lt;%&ndash;<div class="title">
                    <h1><span>��Ҫ��Ϣ����</span></h1>
                </div>&ndash;%&gt;
                <div class="julei">
                    <c:if test="${empty importantInfoList}">
                        <br>
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>��ʱ���������Ϣ</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty importantInfoList}">
                        <c:forEach var="im" items="${importantInfoList}" varStatus="st">
                            <div class="main_list">
                                <ul>

                                    <li>
                                        <div class="m_r m_r2">
                                            <h1>
                                                <i class="num">${im.num}</i>
                                                ${im.name}${im.percentStr}
                                            </h1>
                                        </div>
                                    </li>
                                    <c:forEach items="${im.iContentCommonNetList}" var="icn">
                                        <li style="display: none">
                                            <div class="m_l">
                                                <c:if test="${not empty icn.profileImageUrl}">
                                                    <img src='${icn.profileImageUrl}'/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='wb'}">
                                                    <c:if test='${icn.captureWebsiteName=="��Ѷ΢��"}'>
                                                        <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-txwb.jpg"/>
                                                    </c:if>
                                                    <c:if test='${icn.captureWebsiteName=="����΢��"}'>
                                                        <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weibo.jpg"/>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='sp'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-video.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='wx'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weixin.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='xw'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-news.jpg" />
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='lt'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-bbs.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='bk'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-blog.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='app'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-app.jpg" />
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='zw'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-affairs.jpg" />
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='baokan'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-press.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='jw'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-media.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='wz'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-web.jpg" />
                                                </c:if>
                                            </div>
                                            <div class="m_r">
                                                <p class="quickLink">
                                                    <a href="${icn.webpageUrl}">
                                                        <c:if test="${icn.originType=='wb'}">
                                                            <c:if test="${empty icn.author}">
                                                                ${icn.captureWebsiteName}
                                                            </c:if>
                                                            <c:if test="${not empty icn.author}">
                                                                ${icn.author}
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${icn.originType!='wb'}">
                                                            ${icn.title}
                                                        </c:if>
                                                    </a>
                                                </p>
                                                <p class="conText conText2">
                                                    ${icn.summary}
                                                </p>
                                                <p class="infor">
                                                    <span>${icn.captureWebsiteName}</span>
                                                    <span>
                                                        <fmt:formatDate value="${icn.published}" pattern="MM��dd�� HH:mm"/>
                                                    </span>
                                                </p>
                                            </div>
                                        </li>
                                    </c:forEach>

                                </ul>
                                <div class="bottom">
                                    <a href="javascript:void(0)" class="more bottomMore"> <img src="<%=njxBasePath%>/images/expand3-32.png"/></a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>

                </div>

            </div>
        </div>
        <!--��΢������ end-->
        <div class="line"></div>--%>

        <!--����1 start-->
        <div class="row-fluid" <c:if test="${empty importantInfoList}"> style="display:none;" </c:if>>
            <div class="con">
                <div class="title">
                    <h1>��Ҫ��Ϣ</h1>
                </div>
                <%--<div class="text" id="weiboView"></div>
                <div class="mwbBorder chart" style="width:100%;height:400px;" id="viewBar">

                </div>--%>

            </div>

            <div class="con">

                <div class="main_list">
                    <c:if test="${empty importantInfoList}">
                        <br>
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>��ʱ���������Ϣ</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty importantInfoList}">
                        <ul>
                            <c:forEach var="mg" items="${importantInfoList}" >
                                <c:forEach items="${mg.iContentCommonNetList}" var="icn">
                                    <li>
                                        <div class="m_l">
                                            <c:if test="${not empty icn.profileImageUrl}">
                                                <img src='${icn.profileImageUrl}'/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wb'}">
                                                <c:if test='${icn.captureWebsiteName=="��Ѷ΢��"}'>
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-txwb.jpg"/>
                                                </c:if>
                                                <c:if test='${icn.captureWebsiteName=="����΢��"}'>
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weibo.jpg"/>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='sp'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-video.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wx'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weixin.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='xw'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-news.jpg" />
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='lt'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-bbs.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='bk'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-blog.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='app'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-app.jpg" />
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='zw'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-affairs.jpg" />
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='baokan'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-press.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='jw'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-media.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wz'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-web.jpg" />
                                            </c:if>
                                        </div>
                                        <div class="m_r">
                                            <p class="quickLink">
                                                <a href="${icn.webpageUrl}" >
                                                    <c:if test="${icn.originType=='wb'}">
                                                        <c:if test="${empty icn.author}">
                                                            ${icn.captureWebsiteName}
                                                        </c:if>
                                                        <c:if test="${not empty icn.author}">
                                                            ${icn.author}
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${icn.originType!='wb'}">
                                                        ${mg.name}
                                                    </c:if>
                                                </a>
                                            </p>
                                            <p class="conText">
                                                <c:if test="${not empty icn.summary}">
                                                    ${icn.summary}
                                                </c:if>
                                            </p>
                                            <p class="infor">
                                                <span>${icn.captureWebsiteName}</span>
                                                <span><fmt:formatDate value="${icn.published}" pattern="MM��dd�� HH:mm"/> </span>
                                                <span>Ӱ����:${mg.num}</span>
                                            </p>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </c:if>


                </div>

            </div>
        </div>
        <!--����1 end-->
        <div class="line"></div>

        <!--�۵���ࣨ��΢���� start-->
        <div class="row-fluid">
            <div class="con">
                <div class="title">
                    <h1>��Ϣ���ࣨ��΢����</h1>
                </div>
                <%--<div class="text" id="fweiboView2"></div>--%>
                <div class="mwbBorder chart" style="width:100%;height:${notImgHeight}px;" id="fweiboBar">

                </div>

            </div>

            <div class="con">

                <div class="main_list" style="display: none">
                    <c:if test="${empty notWeiboInfoList}">
                        <br>
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>��ʱ���������Ϣ</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty notWeiboInfoList}">
                        <ul>
                            <c:forEach var="mg" items="${notWeiboInfoList}" >
                                <c:forEach items="${mg.iContentCommonNetList}" var="icn">
                                    <li>
                                        <div class="m_l">
                                            <c:if test="${not empty icn.profileImageUrl}">
                                                <img src='${icn.profileImageUrl}'/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wb'}">
                                                <c:if test='${icn.captureWebsiteName=="��Ѷ΢��"}'>
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-txwb.jpg"/>
                                                </c:if>
                                                <c:if test='${icn.captureWebsiteName=="����΢��"}'>
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weibo.jpg"/>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='sp'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-video.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wx'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weixin.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='xw'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-news.jpg" />
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='lt'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-bbs.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='bk'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-blog.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='app'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-app.jpg" />
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='zw'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-affairs.jpg" />
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='baokan'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-press.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='jw'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-media.jpg"/>
                                            </c:if>
                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wz'}">
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-web.jpg" />
                                            </c:if>
                                        </div>
                                        <div class="m_r">
                                            <p class="quickLink">
                                                <a href="${icn.webpageUrl}" >
                                                    <c:if test="${icn.originType=='wb'}">
                                                        <c:if test="${empty icn.author}">
                                                            ${icn.captureWebsiteName}
                                                        </c:if>
                                                        <c:if test="${not empty icn.author}">
                                                            ${icn.author}
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${icn.originType!='wb'}">
                                                        ${icn.title}
                                                    </c:if>
                                                </a>
                                            </p>
                                            <p class="conText">
                                                    ${icn.summary}
                                            </p>
                                            <p class="infor">
                                                <span>${icn.captureWebsiteName}</span>
                                                <span><fmt:formatDate value="${icn.published}" pattern="MM��dd�� HH:mm"/> </span>
                                            </p>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </c:if>


                </div>

            </div>
        </div>
        <!--����3 end-->
        <div class="line"></div>

        <!--�۵���ࣨ΢���� start-->
        <div class="row-fluid">
            <div class="con">
                <div class="title">
                    <h1>��Ϣ���ࣨ΢����</h1>
                </div>
                <%--<div class="text" id="weiboView2"></div>--%>
                <div class="mwbBorder chart" style="width:100%;height:${weiboImgHeight}px;" id="weiboBar">

                </div>

            </div>

            <div class="con">

                <div class="main_list" style="display: none">
                    <c:if test="${empty weiboInfoList}">
                        <br>
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>��ʱ���������Ϣ</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty weiboInfoList}">
                            <ul>
                                <c:forEach var="mg" items="${weiboInfoList}" >
                                    <c:forEach items="${mg.iContentCommonNetList}" var="icn">
                                        <li>
                                            <div class="m_l">
                                                <c:if test="${not empty icn.profileImageUrl}">
                                                    <img src='${icn.profileImageUrl}'/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='wb'}">
                                                    <c:if test='${icn.captureWebsiteName=="��Ѷ΢��"}'>
                                                        <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-txwb.jpg"/>
                                                    </c:if>
                                                    <c:if test='${icn.captureWebsiteName=="����΢��"}'>
                                                        <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weibo.jpg"/>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='sp'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-video.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='wx'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weixin.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='xw'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-news.jpg" />
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='lt'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-bbs.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='bk'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-blog.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='app'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-app.jpg" />
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='zw'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-affairs.jpg" />
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='baokan'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-press.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='jw'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-media.jpg"/>
                                                </c:if>
                                                <c:if test="${empty icn.profileImageUrl&&icn.originType=='wz'}">
                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-web.jpg" />
                                                </c:if>
                                            </div>
                                            <div class="m_r">
                                                <p class="quickLink">
                                                    <a href="${icn.webpageUrl}" >
                                                        <c:if test="${icn.originType=='wb'}">
                                                            <c:if test="${empty icn.author}">
                                                                ${icn.captureWebsiteName}
                                                            </c:if>
                                                            <c:if test="${not empty icn.author}">
                                                                ${icn.author}
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${icn.originType!='wb'}">
                                                            ${icn.title}
                                                        </c:if>
                                                    </a>
                                                </p>
                                                <p class="conText">
                                                        ${icn.summary}
                                                </p>
                                                <p class="infor">
                                                    <span>${icn.captureWebsiteName}</span>
                                                    <span><fmt:formatDate value="${icn.published}" pattern="MM��dd�� HH:mm"/> </span>
                                                </p>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </c:forEach>
                            </ul>

                    </c:if>

                </div>

            </div>
        </div>
        <!--΢������ end-->
        <div class="line"></div>


        <!--������ϢTop20 start-->
        <div class="row-fluid" <c:if test="${empty mgList}">style="display: none" </c:if>>
            <div class="con">
                <div class="title">
                    <h1>${mgTitle}</h1>
                </div>
                <div class="main_list" id="mgList">
                    <c:if test="${empty mgList}">
                        <br>
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>��ʱ���������Ϣ</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty mgList}">
                        <ul>
                            <c:forEach var="mg" items="${mgList}" >
                                <li>
                                    <div class="m_l">
                                        <c:if test="${not empty mg.profileImageUrl}">
                                            <img src='${mg.profileImageUrl}'/>
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='wb'}">
                                            <c:if test='${mg.captureWebsiteName=="��Ѷ΢��"}'>
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-txwb.jpg"/>
                                            </c:if>
                                            <c:if test='${mg.captureWebsiteName=="����΢��"}'>
                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weibo.jpg"/>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='sp'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-video.jpg"/>
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='wx'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weixin.jpg"/>
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='xw'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-news.jpg" />
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='lt'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-bbs.jpg"/>
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='bk'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-blog.jpg"/>
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='app'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-app.jpg" />
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='zw'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-affairs.jpg" />
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='baokan'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-press.jpg"/>
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='jw'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-media.jpg"/>
                                        </c:if>
                                        <c:if test="${empty mg.profileImageUrl&&mg.originType=='wz'}">
                                            <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-web.jpg" />
                                        </c:if>
                                    </div>
                                    <div class="m_r">
                                        <p class="quickLink">
                                            <a href="${mg.webpageUrl}" >
                                                <c:if test="${mg.originType=='wb'||mg.originType=='txwb'}">
                                                    <c:if test="${empty mg.author}">
                                                        ${mg.captureWebsiteName}
                                                    </c:if>
                                                    <c:if test="${not empty mg.author}">
                                                        ${mg.author}
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${mg.originType!='wb'&&mg.originType!='txwb'}">
                                                    ${mg.title}
                                                </c:if>
                                            </a>
                                        </p>
                                        <p class="conText">
                                            <c:if test="${mg.originType=='wb'||mg.originType=='txwb'}">
                                                ${mg.summary}
                                            </c:if>
                                            <c:if test="${mg.originType!='wb'&&mg.originType!='txwb'&&not empty mg.summary}">
                                                ${mg.summary}
                                            </c:if>
                                        </p>
                                        <c:if test="${(mg.originType=='wb'||mg.originType=='txwb')&&not empty mg.forwarderContent}">
                                            <div class="zfCon">${mg.forwarderContent}</div>
                                        </c:if>
                                        <p class="infor">
                                            <span>${mg.captureWebsiteName}</span>
                                            <span><fmt:formatDate value="${mg.published}" pattern="MM��dd�� HH:mm"/> </span>
                                            <c:if test="${mgTitle=='������ϢTop10'}">
                                                <span>Ӱ����:${mg.num}</span>
                                            </c:if>
                                        </p>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>

                </div>
            </div>
        </div>
        <!--������ϢTop20 end-->

        <div class="row-fluid">
            <a href="http://www.wrd.cn/downLoad.shtml" onclick="recordDown()">
                <img width="100%" src="<%=njxBasePath%>/images/bottomPic.png"/>
            </a>
        </div>

     <%--   <!--����������� start-->
        <div class="row-fluid foot" style="text-align: center;">
            <a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq"><img src="images/bottomPic.jpg" /></a>
        </div>
        <!--����������� end-->
        <div class="h10 clear"></div>--%>

    </div>
</div>
<!--�ձ� end-->
<div style="display: none">
<%--cnzzͳ�ƴ���start  --%>
<%--<script type="text/javascript" src="<%=njxBasePath%>/js/cnzz.js"></script>--%>
<%--cnzzͳ�ƴ���end  --%>
</div>
<script>
    var loginHref = "www.wrd.cn";
    $(function() {

        if (/weibo/i.test(navigator.userAgent.toLowerCase())) {
            $("#loginBtn").attr("href","http://apps.weibo.com/3960037780/8rXM111J");
            loginHref = "http://apps.weibo.com/3960037780/8rXM111J";
        }
        if (/micromessenger/i.test(navigator.userAgent.toLowerCase())) {
        	if(!"${loginType}"){
        		$("#loginBtn").text("����");
            	$("#loginBtn").attr("href","http://h5.51wyq.cn/view/pay/dingYueKeywordReport.action?reportCode=${keywordReportRecord.shareCode}");
        	}else{
        		$("#loginBtn,#loginBtn2").attr("href","http://h5.51wyq.cn");
        	}
        }

        $(".julei .main_list").each(function(i){
            $("#cluster"+(i)+">ul>li:gt(0)").hide();
        });
        $(".bottomMore").on("click",function(){
            if(!$(this).find("img").hasClass("rotate180")){
                $(this).parents(".julei .main_list").find(">ul>li:gt(0)").show(300);
                $(this).html(" <img src='images/expand3-32.png' class='rotate180'/>");
            }else{
                $(this).parents(".julei .main_list").find(">ul>li:gt(0)").hide(300);
                $(this).html(" <img src='images/expand3-32.png' class='rotate0'/>");
            }
        });

        var data = ${reportJsonData};
        console.log(data);
        $("#reportName").text(data[8]);
        $("#reportDate").text(data[0]);
        //$("#weiboView").text(data[1]);
        $("#weiboView2").text(data[10]);
        $("#fweiboView2").text(data[11]);
        $("#mgNum").text(data[2]);
        $("#fmgNum").text(data[3]);

        cloudChart(data[4],"treeMap");

        //LineChart(data[5],"hotLine");

        originPieChart(data[6],"originPie");

        //Chart(data[7],"viewBar");

        viewBarChart(data[9],"weiboBar");

        viewBarChart(data[12],"fweiboBar");

    });

    function treeMapChart(data,dom){
        if (data==null||data==""){
            document.getElementById(dom).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=njxBasePath %>/images/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return false;
        }
        data = eval(data);
        var colors = ["#00b2f4","#2c4f79","#00b2f4","#2c4f79"];
        $.each(data[0].data,function(i){
            this.itemStyle.normal.color = colors[i%4];
        });
        var config = require(
                [
                    'echarts',
                    'echarts/chart/treemap',
                ],
                function (ec) {
                    var chart4 = ec.init(document.getElementById(dom));
                    var option = {
                        /*title : {
                         text: '�ֻ�ռ����',
                         subtext: '�鹹����'
                         },*/
                        tooltip : {
                            trigger: 'item',
                            formatter: "{b}: {c}"
                        },
                        toolbox: {
                            show : false,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : false,
                        /*grid:{
                         y2:80,
                         x:30,
                         x2:30

                         },*/
                        color:["#00b2f4","#2c4f79"],
                        series : [
                            {
                                name:'����ͼ',
                                type:'treemap',
                                size:['95%','95%'],
                                itemStyle: {
                                    normal: {
                                        label: {
                                            show: true,
                                            formatter: "{b}",
                                            /*x:50,
                                             y:50,*/
                                            textStyle: {
                                                fontSize:'18',
                                                color:"#fff"
                                                /*fontWeight:'bold'*/

                                            }
                                        },
                                        borderWidth: 1
                                    },
                                    emphasis: {
                                        label: {
                                            show: true
                                        }
                                    },
                                    breadcrumb:{
                                        show:true
                                    }
                                },
                                data:data[0].data
                            }
                        ]
                    }
                    chart4.setOption(option);
                    var enConfig = require('echarts/config');

                }
        );

    }

    function cloudChart(data,dom){
        if (data==null||data==""){
            document.getElementById(dom).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=njxBasePath %>/images/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            $("#"+dom).parents(".row-fluid").hide();
            return false;
        }
        data = eval(data);
        var colors = ["#72c1be","#f29300","#a05623","#277bc0"];
        $.each(data[0].data,function(i){
            this.itemStyle.normal.color = colors[i%4];
        });
        var config = require(
                [
                    'echarts',
                    'echarts/chart/wordCloud'
                ],
                function (ec) {
                    var chart2 = ec.init(document.getElementById(dom));
                    var option = {
                        animation : false,
                        tooltip: {
                            show: false,
                            formatter:function(params){
                                var num = params.value;
                                /*num = parseInt(num)/10;*/
                                return params.name+" : "+ num;
                            }
                        },
                        toolbox: {
                            show : true,
                            orient:'horizontal',
                            y:30,
                            x:'right',

                            feature : {
                                mark : {show: false},
                                dataView : {
                                    show: false,
                                    readOnly: false,
                                    lang: ['������ͼ', '�ر�', 'ˢ��']
                                },
                                restore : {show: true},
                                saveAsImage : {
                                    show: true,
                                    name:data.title
                                }
                            }
                        },
                        series: [{
                            /*name: 'Google Trends',*/
                            type: 'wordCloud',
                            size: ['90%', '90%'],
                            textRotation : [0, 45, 90, -45],
                            textPadding: 2,
                            autoSize: {
                                enable: true,
                                minSize: 18
                            },
                            data: data[0].data
                        }]
                    };
                    chart2.setOption(option);
                    var enConfig = require('echarts/config');
                }
        );
    }

    function LineChart(data,dom){
        if (data==null||data==""){
            document.getElementById(dom).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=njxBasePath %>/images/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return false;
        }
        data = eval(data)[0];
        var splitNum = 0;
        if(data.datetime.length>12){
            splitNum = 2;
        }
        /*$.each(data.data,function(){
         this.symbolSize = 6;
         this.itemStyle={'normal':{'lineStyle':{'width':2.8}}};
         });*/
        var config = require(
                [
                    'echarts',
                    'echarts/chart/line'
                ],
                function (ec) {
                    var chart1 = ec.init(document.getElementById(dom));
                    var option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter:function(params){
                                var v = new Date(parseInt(params[0].name)).format("MM-dd hh:00");

                                for (var i = 0, l = params.length; i < l; i++) {
                                    v += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                                }

                                return v;
                            }
                        },
                        toolbox: {
                            show : true,
                            orient:'horizontal',
                            y:10,
                            x:'right',

                            feature : {
                                mark : {show: false},
                                dataView : {
                                    show: false,
                                    readOnly: false,
                                    lang: ['������ͼ', '�ر�', 'ˢ��']
                                },
                                restore : {show: true},
                                saveAsImage : {
                                    show: true,
                                    name:data.title
                                },
                            }
                        },
                        legend: {
                            data:["ȫ��"],
                            show:false
                        },
                        grid:{
                            x:50,
                            x2:30
                        },
                        xAxis:[{
                            type : 'category',//category|time
                            boundaryGap: false ,
                            data : data.datetime,
                            /*axisLine: {
                             onZero: false,
                             show:false
                             },
                             splitLine:{
                             show:false
                             },*/
                            splitNumber:splitNum,
                            axisLabel : {
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#fff"
                                },
                                formatter:function(v){
                                    v = new Date(parseInt(v)).format("MM-dd hh:00");
                                    return v;
                                }
                            },
                        }
                        ],
                        yAxis : [{
                            type : 'value',
                            /*axisLine: {
                             onZero: false,
                             show:false
                             },
                             splitLine:{
                             show:false
                             },*/
                            splitArea:{
                                show:true,
                                areaStyle:{
                                    color:['#08172c','#08172c']
                                }
                            },
                            axisLabel:{
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#fff"
                                },
                                formatter:function(v){
                                    if(v>=1000){
                                        return (v/1000)+"k";
                                    }else{
                                        return v;
                                    }
                                }
                            },
                        }],


                        calculable : false,
                        series : [{
                            name:"ȫ��",
                            type:"line",
                            data:data.data
                        }]
                    }
                    chart1.setOption(option);
                    chart1.setTheme('infographic');
                    var enConfig = require('echarts/config');

                }
        );
    }

    function originPieChart(data,dom){
        if (data==null||data==""){
            document.getElementById(dom).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=njxBasePath %>/images/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            $("#"+dom).parents(".row-fluid").hide();
            return false;
        }
        data = eval(data)[0];
        var config = require(
                [
                    'echarts',
                    'echarts/chart/pie',
                ],
                function (ec) {
                    var chart4 = ec.init(document.getElementById(dom));
                    var option = {
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        toolbox: {
                            show : true,
                            orient:'horizontal',
                            y:10,
                            feature : {
                                mark : {show: false},
                                dataView : {
                                    show: false,
                                    readOnly: false,
                                    lang: ['������ͼ', '�ر�', 'ˢ��']
                                },
                                restore : {show: true},
                                saveAsImage : {
                                    show: true,
                                    name:data.title,
                                    type:'jpeg',
                                    lang : ['�������']
                                }
                            }
                        },
                        calculable : false,
                        /*legend:{
                         orient : 'vertical',
                         x : '60%',
                         y : 'center',
                         data:data.legend,
                         formatter:function(v){
                         return v.length>30? v.substr(0,30):v;
                         }
                         },*/
                        series : [
                            {
                                name:data.title,
                                type:'pie',
                                center:['50%','50%'],
                                radius : ['50%', '70%'],
                                startAngle:0,
                                itemStyle : {
                                    normal : {
                                        label : {
                                            show : true,
                                            textStyle:{
                                                fontSize:'12',
                                                fontWeight:'normal'
                                            },
                                            formatter: "{b}:{d}%"
                                        },
                                        labelLine : {
                                            show : true,
                                            length:1
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
                                data:data.data
                            }
                        ]
                    }
                    chart4.setOption(option);
                    var enConfig = require('echarts/config');

                }
        );

    }

    function viewBarChart(data,dom){
        if (data==null||data==""){
            document.getElementById(dom).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=njxBasePath %>/images/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            $("#"+dom).parents(".row-fluid").hide();
            return false;
        }
        data = eval(data)[0];
        var colors = ["#e6b322","#c5c669","#ff7a5e","#0170c1","#37b48a","#4e5ab0"];
        var num = 0;
        $.each(data.data,function(i){
            this.itemStyle.normal.color = colors[i%6];
            num =i;
        });
        num++;
        var height = num*45>150?num*45:150;
        //$("#"+dom).css("height",height+"px");
        var config = require(
                [
                    'echarts',
                    'echarts/chart/bar'
                ],
                function (ec) {
                    var chart2 = ec.init(document.getElementById(dom));
                    var option = {
                        tooltip : {         // Option config. Can be overwrited by series or data
                            trigger: 'axis',
                            formatter:"{b}  Ӱ����:{c}"
                        },
                        toolbox: {
                            show : true,
                            orient:'horizontal',
                            y:5,
                            x:'right',

                            feature : {
                                mark : {show: false},
                                dataView : {
                                    show: false,
                                    readOnly: false,
                                    lang: ['������ͼ', '�ر�', 'ˢ��']
                                },
                                restore : {show: true},
                                saveAsImage : {
                                    show: true,
                                    name:data.title
                                }
                            }
                        },
                        xAxis:[{
                            type : 'value',
                            /*axisLine: {
                             onZero: false,
                             show:false
                             },
                            splitArea: {
                             show: false
                             },*/
                            splitLine:{
                                show:false
                            },
                            axisLabel:{
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#fff"
                                },
                                formatter:function(v){
                                    if(v>=1000){
                                        return (v/1000)+"k";
                                    }else{
                                        return v;
                                    }
                                }
                            }
                        }],
                        grid:{
                            y:30,
                            y2:30,
                            x:30,
                            x2:30,
                            borderWidth:0
                        },
                        yAxis : [{
                            show:false,
                            type : 'category',
                            data : data.legend,
                            axisLabel : {
                                formatter: function(value){
                                    if(value.length>10){
                                        value = value.substring(0,10);
                                    }
                                    return value;
                                },
                                /*rotate:45,*/
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#fff"
                                }

                            },
                            /*axisLine: {
                             onZero: false,
                             show:false
                             },
                             splitLine:{
                             show:false
                             },*/
                            splitArea:{
                                show:true,
                                areaStyle:{
                                    color:['#08172c','#08172c']
                                }
                            }

                        }],

                        /*color:['#87cefa','#ff7f50'],*/
                        calculable : false,
                        animation:false,
                        series : [{
                            name:'����',
                            type:'bar',
                            itemStyle : {
                                normal: {label : {
                                    show: true, position: 'insideLeft',
                                    formatter:function(param){
                                        var value = param.name;
                                        if(value.length>30){
                                            value = value.substring(0,30);
                                        }
                                        return value;
                                    },
                                    textStyle:{fontSize:12}
                                }
                                },
                                emphasis:{label : {show: true,textStyle:{fontSize:12}}}},
                            data:data.data
                        }]
                    }
                    chart2.setOption(option);
                    var enConfig = require('echarts/config');

                }
        );
    }

    function isMobile(){
        var bs = {
            versions: function () {
                var u = navigator.userAgent, app = navigator.appVersion;
                return {
                    windowsPhone: u.indexOf('IEMobile') > -1,
                    trident: u.indexOf('Trident') > -1,
                    presto: u.indexOf('Presto') > -1,
                    webKit: u.indexOf('AppleWebKit') > -1,
                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') === -1,
                    mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/) || !!u.match(/IEMobile/),
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
                    android: u.indexOf('Android') > -1 || u.indexOf('UCBrowser') > -1,
                    iPhone: u.indexOf('iPhone') > -1,
                    iPad: u.indexOf('iPad') > -1,
                    webApp: u.indexOf('Safari') === -1
                };
            } (),
            language: (navigator.browserLanguage || navigator.language).toLowerCase()
        };
        if (bs.versions.mobile) {
            if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.windowsPhone) {
                //$('.fenxiang').css('display', 'none');
                //$('#weibo_task_result_star_content_div,#weibo_task_result_line_content_div,#weibo_task_result_fans_div').css('height', '220px');
                location.href="onclick","http://h5.wyq.cn/register.action";
            }
        }else{
            location.href="http://www.wrd.cn/user/goRegister.shtml";
        }
    }

    function recordSign(){
        $.ajax({
            url : "<%=njxBasePath %>/recordSign.shtml?userId="+$("#userId").val(),
            type : "get",
            success : function(result){
            }
        })
    }

    function recordDown(){
        $.ajax({
            url : "<%=njxBasePath %>/recordDown.shtml?userId="+$("#userId").val(),
            type : "get",
            success : function(result){
            }
        })
    }

    function jumpLogin(){
        var data = {"userId":$("#userId").val(),"keywordId":$("#keywordId").val()};

        $.getJSON("http://h5.wyq.cn/api/doAddAccessRecords.action?callback=?",
                data,
                function(data){
                    if(data.status){
                        location.href= loginHref;
                    }else{
                        console.log(data.status);
                    }
                });

    }

</script>
</body>
</html>

