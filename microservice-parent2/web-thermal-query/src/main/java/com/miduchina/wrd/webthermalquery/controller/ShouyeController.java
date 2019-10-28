package com.miduchina.wrd.webthermalquery.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.hotspot.NotLoginOperateRecord;
import com.miduchina.wrd.webthermalquery.fegin.UserFeignClient;
import com.tuke.gospel.core.vo.PaginationSupport;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.method.wyq.web.WyqWebMethodV1;
import com.xd.tools.ownutils.GetCommon;
import com.xd.tools.pojo.Params;
import com.xd.tools.pojo.RankingListWeb;
import com.xd.tools.pojo.Statistics;
import com.xd.tools.view.IContentCommonNetView;
import com.xd.tools.view.StatisticsView;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
@Slf4j
@Controller
@RequestMapping("shouye/active")
public class ShouyeController extends BaseController {
    @Resource
    private UserFeignClient userFeignClient;
//	private static final long serialVersionUID = 3037727784973245614L;
//	@Autowired
//    protected SystemConfig systemConfig;
//	@Autowired
//	private KeyWordBean keywordBean;
//	@Autowired
//	private WarningBean warningBean;
//	@Autowired
//	private UserCenterBean userCenterBean;
//    @Autowired
//    private KeyWordLogBean keyWordLogBean;
//    @Autowired
//    private UserBean userBean;
//
//	private List<FocusWebsite> userFweblist;
//	private List<KeyWord> kwList;
//	private int firstWId = 0;
//	private int guideCount; // 首页引导次数
//    private int programno;//增强方案数量
//    private int basis;//基础型方案数量
//    private int tishi;//判断用户是否今天登陆
//
//    private String title;//热度显示名称
//    private String keyword;//热度搜索词
//    private String filterKeyword;//热度过滤词
//    private String keyword2;//热度搜索词2
//    private String keyword3;//热度搜索词3
//    private int date = 24;//热度时间
//    private String heatKeyword;//总热度搜索词
//    private String shareCode;//分享码
//    private int singleShare = 0;
//    private String viewUri;
//    private String category;//排行榜 大分类
//    private String middleClass;//排行榜 中分类
//    private String smallClass;//排行榜 小分类
//    private Integer stockType;
//    private String starProfession;
//    private Integer starSex;
//    private Integer type;//排行榜 来源类型
//    private Integer carTag;//排行榜 汽车标签
//    private Integer computerTag;//排行榜 电脑标签
//    private Integer celebrityTag;//排行榜 明星标签
//    private Integer appliancesTag;//排行榜 家电标签
//    private String paramName;
//    private String paramValue;
//    private NotLoginOperateRecord notLoginOperateRecord;
//    private KeyWord kw;
//    private IncidentAnalysisTask analysisTask;
//    private int operateType;
//    private ProductsAnalysisKeyword pak;	//竞品分析条件的关键词
//    private String startTime;
//    private String endTime;
//    private String thinkKeyword;
//    private List<OperationAdminWb> hotEvents;//首页热点事件
//
//    private List<RankingListWeb> rankingList;
//    private String categoryStr = "";
//    private String smallClassStr ="";
//    private String middleClassStr ="";
//    private String paramValueStr="";
//    private List rankingListPaging;
//    private int currentPageCode = 0;
//    private String searchName;//排行榜搜索
//    private int sort=5;
//    private int order=1;
//    private List<UserContact> tlist;
//    private int isSinaAuth = 0;
//    private List<HeatReport> hrList;
//    private List<HeatReportRecord> hrrList;
//    private List<HeatShare> hsList;
//    private String reportViewPath;
//    private Map<Integer,UserContact> ucMap = new HashMap<Integer, UserContact>();//联系人map
//
//    private OperationAdminWb hotEventPreview;
//    private OrderHeatReportRelation orderHeatReportRelation;
//
//    private String heatViewPath = "";//热度日报查看路径
//    private int num;//热度对比个数
//    private String categoryId;//多个排行榜分类ID
//    private  String categoryType;//多个排行榜分类类型
//    private String secondCategory;//二级分类
//    private Integer categoryLevel;//景区 1、5A 2、4A
//
//    private OperateLogObject operateLogObject;
//
//    private HeatShare heatShare;
//    private OperationAdminHot operationAdminHot;
//    private OperationAdminHotContent operationAdminHotContent;
//
//    private String exportName;//导出excel的名字
//    private int fenhuangType=0;
//
//    private int orderRecordId = 0;
//    private String starttime;
//    private String endtime;
//    private int count=0;
//    private String province = "上海";
//
//    private int searchType; //未登录首页搜索类型 0--热度 1--情绪
//    private String secondClassifyName;
//    private String threeClassifyName;
//    private int isAll = 0;
//    private String isEver;
//
//    private String data;
//
//    private String newOrOld;
//
//    private List<ActivitySendRecord> activitySendRecordList;
//
//    public String getNewOrOld() {
//        return newOrOld;
//    }
//
//    public void setNewOrOld(String newOrOld) {
//        this.newOrOld = newOrOld;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public String getIsEver() {
//        return isEver;
//    }
//
//    public void setIsEver(String isEver) {
//        this.isEver = isEver;
//    }

//    @Override
//	public String execute() throws Exception {
//        fetchSessionAdmin();
//        fetchRightNumber();
//        fetchProductlist(Constants.PACKAGE_TYPE_KEYWORD);
//        fetchProductlist(Constants.PACKAGE_TYPE_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_WEIBO_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_COUNT);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_DAYS);
//        viewUri = HotSearchAction.getDNSUrl(ServletActionContext.getRequest().getRequestURL().toString());
//        if(admin == null){
//        	return LOGIN;
//        }
//
//        // 未登录操作
//		HttpServletRequest request = ServletActionContext.getRequest();
//		notLoginOperateRecord = userCenterBean.findNotLoginOperateRecordByIpAndUA(CommonUtils.getIp(request), request.getHeader("user-agent"));
//		if (notLoginOperateRecord != null) {
//			userCenterBean.updateNotLoginOperateRecord(CommonUtils.getIp(request), request.getHeader("user-agent"));
//			String result = null;
//			result = operateJump(notLoginOperateRecord);
//			if (result != null) {
//				return result;
//			}
//		}
//
//		// 首页引导
//		GuideRecord gr = warningBean.getGuideRecord(admin.getUserId(), Constants.GUIDE_TYPE_SHO);
//		if (gr == null) {
//			guideCount = 0;
//			gr = new GuideRecord();
//			gr.setUserId(admin.getUserId());
//			gr.setType(Constants.GUIDE_TYPE_SHO);
//			gr.setCount(1);
//			gr.setCreateTime(new Date());
//			warningBean.saveGuideRecord(gr);
//		} else {
//			guideCount = gr.getCount();
//		}
//
//		try {
//            String userTag = null;
//            if(admin!=null){
//            	userTag = admin.getUserId().toString();
//            }else{
//                userTag = CommonUtils.getIp(request);
//            }
//            rankingList = new HotSearchAction().getRankingListByParentId(0, userTag);
//
//            /*String urlPre = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";
//			String params1 = "format=json&ip=" + CommonUtils.getIp(request);// 116.226.249.73
//			String result1 = sendGet(urlPre, params1);
//			if (StringUtils.isBlank(result1) || result1.equals("-3") || result1.equals("-2") || result1.equals("-1")) {
//				province = "上海";
//			} else {
//				SinaIpAddress sinIpAddress = JSON.parseObject(result1, SinaIpAddress.class);
//				if (sinIpAddress != null) {
//					if (sinIpAddress.getProvince() != null && !"".equals(sinIpAddress.getProvince())) {
//						province = URLDecoder.decode(sinIpAddress.getProvince(), "utf-8");
//					}
//				}
//			}*/
//            /*IpInfoVO ipInfo = IpUtil.getIpInfoByTbApi(CommonUtils.getIp(request));
//            if(ipInfo == null || StringUtils.isBlank(ipInfo.getProvince())){
//            	province = "上海";
//            }else{
//            	province = ipInfo.getProvince();
//            }*/
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		String json = JedisUtil.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_ACTIVITY_SEND_FLAG + admin.getUserId()));
//		if(StringUtils.isNotBlank(json)){
//			List<ActivitySendRecord> activitySendRecords = JSONObject.parseArray(json, ActivitySendRecord.class);
//			if(CollectionUtils.isNotEmpty(activitySendRecords)){
//				StringBuilder ids = new StringBuilder();
//				for(ActivitySendRecord activitySendRecord : activitySendRecords){
//					ids.append(activitySendRecord.getId() + ",");
//				}
//				if(ids.length() > 0){
//					Map<String, Object> params = CommonUtils.getIntraBusinessParams(admin.getUserId());
//					params.put("activitySendRecordIds", ids.substring(0, ids.length()-1));
//					String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("activityPrizeExchange"), params);
//					BaseView view = JSONObject.parseObject(rtnStr, BaseView.class);
//					if(view != null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())){
//						firstLoginSign = 4;
//						activitySendRecordList = activitySendRecords;
//						userCenterBean.updateUserActivitySendFlag(2, admin.getUserId(), request);
//					}
//				}
//			}
//		}
//
//		return Action.SUCCESS;
//	}

  /*  public String rankingList() throws Exception {
		fetchSessionAdmin();
		fetchRightNumber();
		resetPagesize();

		rankingList = userCenterBean.findRankingList();

		for (int i = 0; i < rankingList.size(); i++) {
			RankingListWeb ranking = rankingList.get(i);
			categoryStr += ranking.getCategory() + "#";
			smallClassStr += ranking.getSmallClass() + "#";
			middleClassStr += ranking.getMiddleClass() + "#";
			paramValueStr += ranking.getParamsValue() + "#";
		}
		categoryStr = categoryStr.substring(0, categoryStr.length() - 1);
		smallClassStr = smallClassStr.substring(0, smallClassStr.length() - 1);
		middleClassStr = middleClassStr.substring(0,middleClassStr.length() - 1);
		paramValueStr = paramValueStr.substring(0, paramValueStr.length() - 1);
		return Action.SUCCESS;
	}*/


//    public String goSearch() throws Exception {
//    	//和凤凰网热词对接
//    	if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(keyword) && fenhuangType>0){
//    		title=URLDecoder.decode(title,"utf-8");
//    		keyword=URLDecoder.decode(keyword,"utf-8");
//    	}
//        fetchSessionAdmin();
//        fetchRightNumber();
//        fetchProductlist(Constants.PACKAGE_TYPE_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_WEIBO_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_KEYWORD);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_COUNT);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_DAYS);
//        notLoginOperateRecord = null;
//        heatViewPath = systemConfig.getHeatViewPath();
//        currentPageCode = 10;
//        //title= title.replaceAll("\\s+?","+");
//        if(singleShare==1&&shareCode!=null){
//            heatShare = warningBean.findHs(shareCode);
//            if(heatShare!=null){
//                title = heatShare.getTitle();
//                keyword = heatShare.getKeyword();
//                filterKeyword = heatShare.getFilterKeyword();
//            }
//        }
//        if (title != null && !title.trim().equals("")) {
//            title = title.trim();
//            title = title.replaceAll(" +", "+");
//        }
//
//        System.out.print(num + "---------" + filterKeyword+"---------"+secondCategory+"--------"+categoryLevel+"---"+secondClassifyName+"----"+threeClassifyName+"----"+isAll);
//        System.out.print(categoryType + "---------" + categoryId);
//        if (title != null && !title.trim().equals("") && admin != null && title.equals(keyword)) {
//            KeyWordLog keyLog = new KeyWordLog();
//            keyLog.setUserId(admin.getUserId());
//            keyLog.setKeyWord(keyword);
//            keyLog.setCreateTime(new Date());
//            keyLog.setUpdateTime(new Date());
//            keyLog.setTotal(1);
//            keyLog.setStatus(1);
//            keyWordLogBean.save(keyLog);
//        }
//
//        CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_SEARCH_HOT, Constants.OPERATE_LOG_OPERATE_TYPE_R, null, null, keyword + "_" + keyword2 + "_" + keyword3);
//        if (admin != null) {
//            tlist = warningBean.getContactInfo(null, 1, admin.getUserId());
//            if (CollectionUtils.isNotEmpty(tlist)) {
//
//                for (int i = 0; i < tlist.size(); i++) {
//
//                    UserContact uc = (UserContact) tlist.get(i);
//
//                    ucMap.put(uc.getId(), uc);
//                }
//            }
//            /*UserThirdpartyAuthInfo userThirdpartyAuthInfo = userBean.findUTAByUserID(admin.getUserId());
//            if (userThirdpartyAuthInfo != null) {
//                if (userThirdpartyAuthInfo.getPlatformType() == 1) {
//                    isSinaAuth = 1;
//                }
//            }*/
//        }
//
//        return "goSearch";
//    }

//    public String goPropagationAnalysis() throws Exception {
//        //和凤凰网热词对接
//        if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(keyword) && fenhuangType>0){
//            title=URLDecoder.decode(title,"utf-8");
//            keyword=URLDecoder.decode(keyword,"utf-8");
//        }
//        fetchSessionAdmin();
//        fetchRightNumber();
//        fetchProductlist(Constants.PACKAGE_TYPE_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_WEIBO_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_KEYWORD);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_COUNT);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_DAYS);
//        notLoginOperateRecord = null;
//        heatViewPath = systemConfig.getHeatViewPath();
//        currentPageCode = 10;
//        //title= title.replaceAll("\\s+?","+");
//        if(singleShare==1&&shareCode!=null){
//            heatShare = warningBean.findHs(shareCode);
//            if(heatShare!=null){
//                title = heatShare.getTitle();
//                keyword = heatShare.getKeyword();
//                filterKeyword = heatShare.getFilterKeyword();
//            }
//        }
//        if (title != null && !title.trim().equals("")) {
//            title = title.trim();
//            title = title.replaceAll(" +", "+");
//        }
//
//        System.out.print(num + "---------" + filterKeyword+"---------"+secondCategory+"--------"+categoryLevel+"---"+secondClassifyName+"----"+threeClassifyName+"----"+isAll);
//        System.out.print(categoryType + "---------" + categoryId);
//        if (title != null && !title.trim().equals("") && admin != null && title.equals(keyword)) {
//            KeyWordLog keyLog = new KeyWordLog();
//            keyLog.setUserId(admin.getUserId());
//            keyLog.setKeyWord(keyword);
//            keyLog.setCreateTime(new Date());
//            keyLog.setUpdateTime(new Date());
//            keyLog.setTotal(1);
//            keyLog.setStatus(1);
//            keyWordLogBean.save(keyLog);
//        }
//
//        CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_SEARCH_HOT, Constants.OPERATE_LOG_OPERATE_TYPE_R, null, null, keyword + "_" + keyword2 + "_" + keyword3);
//        if (admin != null) {
//            tlist = warningBean.getContactInfo(null, 1, admin.getUserId());
//            if (CollectionUtils.isNotEmpty(tlist)) {
//
//                for (int i = 0; i < tlist.size(); i++) {
//
//                    UserContact uc = (UserContact) tlist.get(i);
//
//                    ucMap.put(uc.getId(), uc);
//                }
//            }
//            /*UserThirdpartyAuthInfo userThirdpartyAuthInfo = userBean.findUTAByUserID(admin.getUserId());
//            if (userThirdpartyAuthInfo != null) {
//                if (userThirdpartyAuthInfo.getPlatformType() == 1) {
//                    isSinaAuth = 1;
//                }
//            }*/
//        }
//
//        return "goPropagationAnalysis";
//    }
//    public String goTwoSearch() throws Exception {
//        //和凤凰网热词对接
//        if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(keyword) && fenhuangType>0){
//            title=URLDecoder.decode(title,"utf-8");
//            keyword=URLDecoder.decode(keyword,"utf-8");
//        }
//        fetchSessionAdmin();
//        fetchRightNumber();
//        fetchProductlist(Constants.PACKAGE_TYPE_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_WEIBO_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_KEYWORD);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_COUNT);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_DAYS);
//        notLoginOperateRecord = null;
//        heatViewPath = systemConfig.getHeatViewPath();
//        currentPageCode = 10;
//        //title= title.replaceAll("\\s+?","+");
//        if(singleShare==1&&shareCode!=null){
//            heatShare = warningBean.findHs(shareCode);
//            if(heatShare!=null){
//                title = heatShare.getTitle();
//                keyword = heatShare.getKeyword();
//                filterKeyword = heatShare.getFilterKeyword();
//            }
//        }
//        if (title != null && !title.trim().equals("")) {
//            title = title.trim();
//            title = title.replaceAll(" +", "+");
//        }
//
//        System.out.print(num + "---------" + filterKeyword+"---------"+secondCategory+"--------"+categoryLevel+"---"+secondClassifyName+"----"+threeClassifyName+"----"+isAll);
//        System.out.print(categoryType + "---------" + categoryId);
//        if (title != null && !title.trim().equals("") && admin != null && title.equals(keyword)) {
//            KeyWordLog keyLog = new KeyWordLog();
//            keyLog.setUserId(admin.getUserId());
//            keyLog.setKeyWord(keyword);
//            keyLog.setCreateTime(new Date());
//            keyLog.setUpdateTime(new Date());
//            keyLog.setTotal(1);
//            keyLog.setStatus(1);
//            keyWordLogBean.save(keyLog);
//        }
//
//        CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_SEARCH_HOT, Constants.OPERATE_LOG_OPERATE_TYPE_R, null, null, keyword + "_" + keyword2 + "_" + keyword3);
//        if (admin != null) {
//            tlist = warningBean.getContactInfo(null, 1, admin.getUserId());
//            if (CollectionUtils.isNotEmpty(tlist)) {
//
//                for (int i = 0; i < tlist.size(); i++) {
//
//                    UserContact uc = (UserContact) tlist.get(i);
//
//                    ucMap.put(uc.getId(), uc);
//                }
//            }
//            /*UserThirdpartyAuthInfo userThirdpartyAuthInfo = userBean.findUTAByUserID(admin.getUserId());
//            if (userThirdpartyAuthInfo != null) {
//                if (userThirdpartyAuthInfo.getPlatformType() == 1) {
//                    isSinaAuth = 1;
//                }
//            }*/
//        }
//            return "goTwoSearch";
//    }
//    public String goMoreSearch() throws Exception {
//      //和凤凰网热词对接
//        if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(keyword) && fenhuangType>0){
//                title=URLDecoder.decode(title,"utf-8");
//                keyword=URLDecoder.decode(keyword,"utf-8");
//        }
//        fetchSessionAdmin();
//        fetchRightNumber();
//        fetchProductlist(Constants.PACKAGE_TYPE_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_WEIBO_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_KEYWORD);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_COUNT);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_DAYS);
//        notLoginOperateRecord = null;
//        heatViewPath = systemConfig.getHeatViewPath();
//        currentPageCode = 10;
//        //title= title.replaceAll("\\s+?","+");
//        if(singleShare==1&&shareCode!=null){
//            heatShare = warningBean.findHs(shareCode);
//            if(heatShare!=null){
//                title = heatShare.getTitle();
//                keyword = heatShare.getKeyword();
//                filterKeyword = heatShare.getFilterKeyword();
//            }
//        }
//        if (title != null && !title.trim().equals("")) {
//            title = title.trim();
//            title = title.replaceAll(" +", "+");
//        }
//
//        System.out.print(num + "---------" + filterKeyword+"---------"+secondCategory+"--------"+categoryLevel+"---"+secondClassifyName+"----"+threeClassifyName+"----"+isAll);
//        System.out.print(categoryType + "---------" + categoryId);
//        if (title != null && !title.trim().equals("") && admin != null && title.equals(keyword)) {
//            KeyWordLog keyLog = new KeyWordLog();
//            keyLog.setUserId(admin.getUserId());
//            keyLog.setKeyWord(keyword);
//            keyLog.setCreateTime(new Date());
//            keyLog.setUpdateTime(new Date());
//            keyLog.setTotal(1);
//            keyLog.setStatus(1);
//            keyWordLogBean.save(keyLog);
//        }
//
//        CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_SEARCH_HOT, Constants.OPERATE_LOG_OPERATE_TYPE_R, null, null, keyword + "_" + keyword2 + "_" + keyword3);
//        if (admin != null) {
//            tlist = warningBean.getContactInfo(null, 1, admin.getUserId());
//            if (CollectionUtils.isNotEmpty(tlist)) {
//
//                for (int i = 0; i < tlist.size(); i++) {
//
//                    UserContact uc = (UserContact) tlist.get(i);
//
//                    ucMap.put(uc.getId(), uc);
//                }
//            }
//            /*UserThirdpartyAuthInfo userThirdpartyAuthInfo = userBean.findUTAByUserID(admin.getUserId());
//            if (userThirdpartyAuthInfo != null) {
//                if (userThirdpartyAuthInfo.getPlatformType() == 1) {
//                    isSinaAuth = 1;
//                }
//            }*/
//        }
//        return "goMoreSearch";
//    }

//    public void hotKeywords(){
//        fetchSessionAdmin();
//        String url = SysConfig.API_DB_URL+CommonAPIConfig.getValue("HotWords");
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setContentType("application/json;charset=GBK;");
//        PrintWriter printWriter = null;
//        Object[] json = new Object[1];
//        try {
//            printWriter = response.getWriter();
//            String params = "page=1&pagesize=10&platform=2&channel=2";
//            if(admin!=null){
//                params += "&userTag="+admin.getUserId();
//            }else{
//                HttpServletRequest request = ServletActionContext.getRequest();
//                params += "&userTag="+CommonUtils.getIp(request);
//            }
//            String result = sendGet(url, params);
//            HotIncidentView hotKeywordsView = JSON.parseObject(result, HotIncidentView.class);
//            List<HotIncident> list1 = hotKeywordsView.getHotIncidentList();
//            if(CollectionUtils.isNotEmpty(list1)){
//                json[0] = list1;
//            }
//            printWriter.print(JSON.toJSON(json));
//            return;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String nullStr = null;
//        printWriter.print(nullStr);
//    }

//    public String rankList(){
//    	fetchSessionAdmin();
//		String url = SysConfig.API_DB_URL
//				+ CommonAPIConfig.getValue("RankingList");
//		pagesize = pagesize == 0 ? 10 : pagesize;
//		page = page == 0 ? 1 : page;
//		try {
//			String params = "platform=2&channel=2&type=" + type + "&sort="
//					+ sort + "&order=" + order + "&pagesize=" + pagesize
//					+ "&page=" + page;
//			if (admin != null) {
//				params += "&userTag=" + admin.getUserId();
//			} else {
//				HttpServletRequest request = ServletActionContext.getRequest();
//				params += "&userTag=" + CommonUtils.getIp(request);
//			}
//			/*
//			 * if(stockType!=null){ params += "&stockType="+stockType; }
//			 * if(carTag!=null){ params += "&carTag="+carTag; }
//			 * if(computerTag!=null){ params += "&computerTag="+computerTag; }
//			 * if(celebrityTag!=null){ params += "&celebrityTag="+celebrityTag;
//			 * } if(appliancesTag!=null){ params +=
//			 * "&appliancesTag="+appliancesTag; }
//			 */
//
//			/*if (paramName != null && paramValue != null) {
//				params += "&" + paramName + "=" + paramValue;
//			}*/
//			if (paramName != null && paramValue != null) {
//				for(int i=0;i<paramName.split("#").length;i++){
//					if(paramValue.split("#")[i].equals("null")){
//						params += "&" + paramName.split("#")[i] + "=" +"";
//					}else {
//						params += "&" + paramName.split("#")[i] + "=" +paramValue.split("#")[i];
//					}
//				}
//			}
//
//
//
//
//			if (searchName != null && !"".equals(searchName)) {
//				params += "&name=" + searchName;
//				CommonUtils.opreateLog(ServletActionContext.getRequest(),
//						admin,
//						Constants.OPERATE_LOG_PRODUCT_SECTION_SEARCH_RANKING,
//						Constants.OPERATE_LOG_OPERATE_TYPE_R, null, null,
//						searchName);
//			}
//			String result = sendGet(url, params);
//			StatisticsView statisticsView = JSON.parseObject(result,
//					StatisticsView.class);
//			List<Statistics> list1 = statisticsView.getStatisticsList();
//			if (CollectionUtils.isNotEmpty(list1)) {
//				rankingListPaging = list1;
//				maxpage = statisticsView.getMaxPage();
//				total = (int) statisticsView.getTotalCount();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "rankingListPaging";
//    }

    /**
     * 排行榜导出为excel
     * @return
     */
//    public void rankingExportExcel() {
//        fetchSessionAdmin();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setContentType("application/json;charset=GBK;");
//        PrintWriter printWriter = null;
//        String url = SysConfig.API_DB_URL
//                + CommonAPIConfig.getValue("RankingList");
//        page = 1;
//
//        try{
//            printWriter = response.getWriter();
//            String params = "platform=2&channel=2&showTag=1&type=" + type + "&sort="
//                    + sort + "&order=" + order + "&page=" + page;
//            if (admin != null) {
//                params += "&userTag=" + admin.getUserId();
//            }else{
//                printWriter.print(false);
//                printWriter.close();
//                return;
//            }
//            /*if (paramName != null && paramValue != null) {
//                params += "&" + paramName + "=" + paramValue;
//            }*/
//
//            if (paramName != null && paramValue != null) {
//				for(int i=0;i<paramName.split("#").length;i++){
//					if(paramValue.split("#")[i].equals("null")){
//						params += "&" + paramName.split("#")[i] + "=" +"";
//					}else {
//						params += "&" + paramName.split("#")[i] + "=" +paramValue.split("#")[i];
//					}
//				}
//			}
//
//            String result = sendGet(url, params);
//            StatisticsView statisticsView = JSON.parseObject(result,
//                    StatisticsView.class);
//            List<Statistics> list1 = statisticsView.getStatisticsList();
//            if (CollectionUtils.isNotEmpty(list1)) {
//
//                String savePath = systemConfig.getUploadPath();
//                String urlPath = systemConfig.getFileviewPath();
//
//                // 图片放到挂载盘的路径上
//                String filePath = "rankingExport/"+new SimpleDateFormat("yyyyMMdd").format(new Date());
//                File dir = new File(savePath + filePath);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                savePath = savePath + filePath+ "/" + new Date().getTime();
//
//                File file = new File(savePath + ".xls");
//
//                file = generateExcelForRank(list1, file,exportName);
//
//                String downPath = savePath.replace(systemConfig.getUploadPath(),urlPath)+ ".xls";
//                printWriter.print(JSON.toJSONString(downPath));
//                printWriter.close();
//                return;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        if(printWriter!=null){
//            printWriter.print(false);
//            printWriter.close();
//        }
//
//
//        /*String fileName="";
//
//        if (list != null && list.size() > 0) {
//            try {
//
//                HttpServletRequest request = ServletActionContext.getRequest();
//
//                String savePath = systemConfig.getUploadPath();
//                String uriPath = systemConfig.getFileviewPath();
//
//                // 图片放到挂载盘的路径上
//                File dir = new File(savePath + "/svgpng");
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                savePath = savePath + "/svgpng" + "//" + new Date().getTime();
//
//                File file = new File(savePath + ".xls");
//
//                //file = searchResultExportExcel(list, file);
//
//                HttpServletResponse response = ServletActionContext
//                        .getResponse();
//
//                // 以流的形式下载文件。
//                InputStream fis = new BufferedInputStream(new FileInputStream(
//                        file));
//                byte[] buffer = new byte[fis.available()];
//                fis.read(buffer);
//                fis.close();
//                // 清空response
//                response.reset();
//                if(fileName==null || "".equals(fileName.trim())){
//                    fileName=new Date().getTime()+"";
//                }
//                if(fileName!=null){
//                    fileName = fileName.trim().replaceAll(" ", "");
//                }
//
//                String userAgent = request.getHeader("User-Agent");
//                logger.info("userAgent="+userAgent);
//
//
//                if (userAgent.indexOf("Safari") != -1){
//                    fileName = new String((fileName).getBytes("utf-8"),"iso8859-1");
//                }
//                else if (userAgent.indexOf("Firefox") != -1){
//                    fileName = new String((fileName).getBytes("utf-8"),"iso8859-1");
//                }
//                else{
//                    fileName = new String((fileName).getBytes("gb2312"),"iso8859-1");
//                }
//
//                // 设置response的Header
//                response.addHeader("Content-Disposition",
//                        "attachment;filename="+fileName+".xls");
//                response.addHeader("Content-Length", "" + file.length());
//                OutputStream toClient = new BufferedOutputStream(response
//                        .getOutputStream());
//                //response.setContentType("application/octet-stream");
//
//                response.setContentType("text/html;charset=GBK");
//                response.setContentType("application/vnd.ms-excel" );
//                toClient.write(buffer);
//                toClient.flush();
//                toClient.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }*/
//
//    }

//    public static File generateExcelForRank(List<Statistics> list, File file,String exportName) {
//        WritableWorkbook wwb = null;
//        try {
//            // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
//            wwb = Workbook.createWorkbook(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (wwb != null) {
//            // 创建一个可写入的工作表
//            WritableSheet ws = wwb.createSheet(exportName, 0);
//            try {
//                ws.setColumnView(0, 10);
//                ws.setColumnView(1, 30);
//                ws.setColumnView(2, 20);
//                ws.setColumnView(3, 20);
//                ws.setColumnView(4, 20);
//                ws.setColumnView(5, 20);
//                WritableFont font = new WritableFont(WritableFont.ARIAL,12);
//                WritableCellFormat colorFormat = new WritableCellFormat(font);
//                colorFormat.setBackground(Colour.LIGHT_BLUE);
//                // 第一行
//                ws.addCell(new Label(0, 0, "排名",colorFormat));
//                ws.addCell(new Label(1, 0, "名称",colorFormat));
//                ws.addCell(new Label(2, 0, "热度值",colorFormat));
//                ws.addCell(new Label(3, 0, "热度变化",colorFormat));
//                ws.addCell(new Label(4, 0, "全网信息量",colorFormat));
//                ws.addCell(new Label(5, 0, "排名变化",colorFormat));
//                //设置样式，居中对齐。
//                WritableCellFormat cellFormat = new WritableCellFormat(font);
//                cellFormat.setAlignment(jxl.format.Alignment.LEFT);
//                // 行
//                for (int i = 1; i <= list.size(); i++) {
//                    Statistics obj = list.get(i - 1);
//                    try {
//                        jxl.write.Number number = new jxl.write.Number(0, i, obj.getSequence());
//                        number.setCellFormat(cellFormat);
//                        ws.addCell(number);
////                        WritableHyperlink hlink = new WritableHyperlink(1, i,
////                                obj.getWebpageUrl() == null ? null : new URL(obj.getWebpageUrl()));
////                        hlink.setDescription(obj.getTitle());
////                        ws.addHyperlink(hlink);
//                        ws.addCell(new Label(1, i, obj.getTitle(),cellFormat));
//                        ws.addCell(new Label(2, i, new BigDecimal(obj.getRatioHotDay()).setScale(2, BigDecimal.ROUND_HALF_UP).toString(),cellFormat));
//                        ws.addCell(new Label(3, i, new BigDecimal(obj.getDifferenceDay()).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),cellFormat));
//                        ws.addCell(new Label(4, i, obj.getNumberDay()+"",cellFormat));
//                        ws.addCell(new Label(5, i, obj.getRankDifference()+"",cellFormat));
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                wwb.write();
//                wwb.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return file;
//    }

    /**
     * 排行榜导出为excel 大数据量级使用 分次加载 暂未使用 by lcz 20161208
     * @return
     */
//    public void rankingExportExcelByQueue() {
//        fetchSessionAdmin();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setContentType("application/json;charset=GBK;");
//        PrintWriter printWriter = null;
//        String url = SysConfig.API_DB_URL
//                + CommonAPIConfig.getValue("RankingList");
//        page = 1;
//        pagesize = 100;
//        try{
//            printWriter = response.getWriter();
//            String params = "platform=2&channel=2&showTag=1&pagesize=100&type=" + type + "&sort="
//                    + sort + "&order=" + order ;
//            if (admin != null) {
//                params += "&userTag=" + admin.getUserId();
//            }else{
//                printWriter.print(false);
//                printWriter.close();
//                return;
//            }
///*            if (paramName != null && paramValue != null) {
//                params += "&" + paramName + "=" + paramValue;
//            }*/
//            if (paramName != null && paramValue != null) {
//				for(int i=0;i<paramName.split("#").length;i++){
//					if(paramValue.split("#")[i].equals("null")){
//						params += "&" + paramName.split("#")[i] + "=" +"";
//					}else {
//						params += "&" + paramName.split("#")[i] + "=" +paramValue.split("#")[i];
//					}
//				}
//			}
//
//            ArrayBlockingQueue<Statistics> queue = new ArrayBlockingQueue<Statistics>(200);
//
//            new Thread(new LoadRankingData(queue,url,params)).start();
//
//            String savePath = systemConfig.getUploadPath();
//            String urlPath = systemConfig.getFileviewPath();
//
//            // 图片放到挂载盘的路径上
//            String filePath = "rankingExport/"+new SimpleDateFormat("yyyyMMdd").format(new Date());
//            File dir = new File(savePath + filePath);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            savePath = savePath + filePath+ "/" + new Date().getTime();
//
//            File file = new File(savePath + ".xls");
//
//            WritableWorkbook wwb = null;
//            try {
//                // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
//                wwb = Workbook.createWorkbook(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (wwb != null) {
//                // 创建一个可写入的工作表
//                WritableSheet ws = wwb.createSheet(exportName, 0);
//                try {
//                    ws.setColumnView(0, 10);
//                    ws.setColumnView(1, 20);
//                    ws.setColumnView(2, 20);
//                    ws.setColumnView(3, 20);
//                    ws.setColumnView(4, 20);
//                    ws.setColumnView(5, 20);
//                    // 第一行
//                    ws.addCell(new Label(0, 0, "排名"));
//                    ws.addCell(new Label(1, 0, "名称"));
//                    ws.addCell(new Label(2, 0, "热度值"));
//                    ws.addCell(new Label(3, 0, "热度变化"));
//                    ws.addCell(new Label(4, 0, "全网信息量"));
//                    ws.addCell(new Label(5, 0, "排名变化"));
//                    //设置样式，居中对齐。
//                    WritableCellFormat cellFormat = new WritableCellFormat();
//                    cellFormat.setAlignment(jxl.format.Alignment.LEFT);
//                    int i=0;
//                    // 行
//                    while (true){
//                        if(!LoadRankingData.OVERFLAG&&queue.size()==0){
//                            wwb.write();
//                            wwb.close();
//                            break;
//                        }
//                        Statistics obj = queue.take();
//                        i++;
//                        try {
//                            jxl.write.Number number = new jxl.write.Number(0, i, obj.getSequence());
//                            number.setCellFormat(cellFormat);
//                            ws.addCell(number);
//                            ws.addCell(new Label(1, i, obj.getTitle()));
//                            ws.addCell(new Label(2, i, new BigDecimal(obj.getRatioHotDay()).setScale(2, BigDecimal.ROUND_FLOOR).toString()));
//                            ws.addCell(new Label(3, i, new BigDecimal(obj.getDifferenceDay()).setScale(2,BigDecimal.ROUND_FLOOR).toString()));
//                            ws.addCell(new Label(4, i, obj.getNumberDay()+""));
//                            ws.addCell(new Label(5, i, obj.getRankDifference()+""));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            String downPath = savePath.replace(systemConfig.getUploadPath(),urlPath)+ ".xls";
//            printWriter.print(JSON.toJSONString(downPath));
//            printWriter.close();
//        }catch (Exception e){
//            printWriter.print(false);
//            printWriter.close();
//            e.printStackTrace();
//        }
//
//        if(printWriter!=null){
//            printWriter.print(false);
//            printWriter.close();
//        }
//
//
//    }
//
//    public void thinkKeywords(){
//        fetchSessionAdmin();
//        String url = SysConfig.API_DB_URL+CommonAPIConfig.getValue("ThinkKeywords");
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setContentType("application/json;charset=GBK;");
//        PrintWriter printWriter = null;
//        Object[] json = new Object[1];
//        String nullStr = null;
//        try {
//            printWriter = response.getWriter();
//            thinkKeyword=thinkKeyword.replaceAll("\\s*", "");
//            if(thinkKeyword==null||"".equals(thinkKeyword)){
//                printWriter.print(nullStr);
//                return;
//            }
//            String params = "page=1&pagesize=5&platform=2&channel=2&name="+thinkKeyword;
//            if(admin!=null){
//                params += "&userTag="+admin.getUserId();
//            }else{
//                HttpServletRequest request = ServletActionContext.getRequest();
//                params += "&userTag="+CommonUtils.getIp(request);
//            }
//            String result = sendGet(url, params);
//            CategoryTypeKeywordsView keywordsView = JSON.parseObject(result, CategoryTypeKeywordsView.class);
//            List<CategoryTypeKeywords> list1 = keywordsView.getCtkList();
//            if(CollectionUtils.isNotEmpty(list1)){
//                json[0] = list1;
//            }
//            printWriter.print(JSON.toJSON(json));
//            return;
//
//        } catch (Exception e) {
//            //e.printStackTrace();
//        }
//
//        printWriter.print(nullStr);
//    }
//
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "recordOperateInfo")
    @ResponseBody
    public void recordOperateInfo(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        try {

            printWriter = response.getWriter();
            NotLoginOperateRecord notLoginOperateRecord = new NotLoginOperateRecord();
            notLoginOperateRecord.setCreateTime(new Date());
            notLoginOperateRecord.setUserAgent(request.getHeader("user-agent"));
            notLoginOperateRecord.setIp(CommonUtils.getIp(request));
            HashMap<String, Object> map = new HashMap<>();
            map.put("notLoginOperateRecord",notLoginOperateRecord);
            BaseDto baseDto = userFeignClient.saveOrUpdateNotLoginOperateRecord(map);
//			userCenterBean.saveOrUpdateNotLoginOperateRecord(notLoginOperateRecord);
			printWriter.print(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    public String verifyOperateInfo() throws Exception{
//        fetchSessionAdmin();
//        String result = null;
//        result = operateJump(notLoginOperateRecord);
//        if(result==null){
//            return Action.ERROR;
//        }
//        return result;
//    }
//
//    public String operateJump(NotLoginOperateRecord record) throws Exception{
//    	if(admin == null)
//    		return LOGIN;
//        String result = null;
//        if(record.getOperateType()==1){
//            List<KeyWord> list1 = keywordBean.getUnUsedKw(admin.getUserId());
//            if(CollectionUtils.isNotEmpty(list1)){
//                kw = list1.get(0);
//                kw.setKeywordName(record.getName());
//                kw.setKeyword(record.getKeyword());
//                kw.setMonitorType(-1);
//                if(record.getFilterKeyword()!=null&&!"".equals(record.getFilterKeyword())){
//                    kw.setFilterKeyword(record.getFilterKeyword());
//                }
//                operateType = record.getOperateType();
//                result = "keywordAdd";
//            }else{//方案购买
//                result = "keywordBuy";
//            }
//        }else if(record.getOperateType()==2){
//            if (admin.getUserType() != Constants.USER_TYPE_PRO || admin.getProUserValidEndTime().getTime() - new Date().getTime() < 0) { // 是否专业版用户
//                if(admin.getUserAnalysisValidCount()<1){
//                    result = "eventBuy";
//                    return result;
//                }
//            }
//
//            analysisTask = new IncidentAnalysisTask();
//            analysisTask.setTaskId(0);
//            analysisTask.setCreateType(1);
//            analysisTask.setUserId(admin.getUserId());
//            analysisTask.setIncidentTitle(record.getName());
//            analysisTask.setKeyword(record.getKeyword());
//            if(record.getFilterKeyword()!=null&&!"".equals(record.getFilterKeyword())){
//                analysisTask.setFilterKeyword(record.getFilterKeyword());
//            }
//            if(record.getOptions() != null) {
//                int options=0;//全部
//                if(record.getOptions()==2){
//                    options=3;//敏感
//                }else if(record.getOptions()==3){
//                    options=4;//非敏感
//                }
//                analysisTask.setOptions(options);
//            }
//            if(StringUtils.isNotBlank(record.getOrigin())) {
//                analysisTask.setOrigin(orginArr(record.getOrigin()));
//            }
//            if(record.getMatchType() != null) {
//                analysisTask.setMatchType(record.getMatchType());
//            }
//            if(record.getStarttime() != null) {
//                analysisTask.setStartTime(record.getStarttime());
//                analysisTask.setEndTime(record.getEndtime());
//            }else {
//                Calendar c = Calendar.getInstance();
//                analysisTask.setEndTime(c.getTime());
//                c.add(Calendar.DAY_OF_MONTH, -1);
//                analysisTask.setStartTime(c.getTime());
//            }
//            result = "eventAdd";
//
//        }else if(record.getOperateType()==3){
//            if (admin.getUserType() != Constants.USER_TYPE_PRO || admin.getProUserValidEndTime().getTime() - new Date().getTime() < 0) { // 是否专业版用户
//                if(admin.getUserProductAnalysisValidCount()<1){
//                    result = "toProductsAnalysis";
//                    return result;
//                }
//            }
//            if(record.getJsonData()!=null&&!"".equals(record.getJsonData())){
//                result = "productsAnalysisAdd";
//            }else{
//                result = "toProductsAnalysis";
//            }
//
//        }else if(record.getOperateType()==4){
//            result = "toProductsAnalysis";
//        }else if(record.getOperateType()==5){
//            result = "keywordShow";
//        }else if(record.getOperateType()==6){
//            result = "keywordWarning";
//        }else if(record.getOperateType()==7){
//            result = "keywordDay";
//        }else if(record.getOperateType()==8){
//            result = "briefReport";
//        }else if(record.getOperateType()==9){
//            result = "briefChoose";
//        }else if(record.getOperateType()==10){
//            result = "fullEvent";
//        }else if(record.getOperateType()==11){
//            result = "wbEvent";
//        }else if(record.getOperateType()==12){
//            result = "singleWeiboAnalysis";
//        }else if(record.getOperateType()==13){
//            result = "productsAnalysisShow";
//        }else if(record.getOperateType()==14){
//            result = "rankingListShow";
//        }else if(record.getOperateType()==15){
//            title = record.getName();
//            keyword = record.getKeyword();
//            filterKeyword = record.getFilterKeyword();
//            categoryId=record.getCategoryId();
//            categoryType=record.getCategoryType();
//            num=record.getNum();
//            result = "hotSearch";
//        }else if(record.getOperateType()==16){
//            if (admin.getUserType() != Constants.USER_TYPE_PRO || admin.getProUserValidEndTime().getTime() - new Date().getTime() < 0) { // 是否专业版用户
//                if(admin.getUserWeiboAnalysisValidCount()<1){
//                    result = "eventWBBuy";
//                    return result;
//                }
//            }
//
//            analysisTask = new IncidentAnalysisTask();
//            analysisTask.setTaskId(0);
//            analysisTask.setCreateType(1);
//            analysisTask.setUserId(admin.getUserId());
//            analysisTask.setContentType(2);
//            analysisTask.setIncidentTitle(record.getName());
//            analysisTask.setKeyword(record.getKeyword());
//            if(record.getFilterKeyword()!=null&&!"".equals(record.getFilterKeyword())){
//                analysisTask.setFilterKeyword(record.getFilterKeyword());
//            }
//            if(record.getOptions() != null) {
//                int options=0;//全部
//                if(record.getOptions()==2){
//                    options=3;//敏感
//                }else if(record.getOptions()==3){
//                    options=4;//非敏感
//                }
//                analysisTask.setOptions(options);
//            }
//            if(record.getMatchType() != null) {
//                analysisTask.setMatchType(record.getMatchType());
//            }
//            if(record.getStarttime() != null) {
//                analysisTask.setStartTime(record.getStarttime());
//                analysisTask.setEndTime(record.getEndtime());
//            }else {
//                Calendar c = Calendar.getInstance();
//                analysisTask.setEndTime(c.getTime());
//                c.add(Calendar.DAY_OF_MONTH, -1);
//                analysisTask.setStartTime(c.getTime());
//            }
//            result = "eventWBAdd";
//        }else if(record.getOperateType()==17){
//            result = "emotionMap";
//        }else if(record.getOperateType()==18){
//            result = "reviewPage";
//        }else if(record.getOperateType()==23){
//            result = "goActity";
//        }else if(record.getOperateType()==24){
//            result = "goCase";
//        }else if(record.getOperateType()==25){
//            result = "goSplit";
//        }else if(record.getOperateType()==30){
//        	title = record.getName();
//        	keyword = record.getKeyword();
//        	filterKeyword = record.getFilterKeyword();
//        	date = record.getOptions();
//        	result = "log_open_tools";
//        }else if(record.getOperateType()==31){
//            result = "emotionMapV2";
//        }else if(record.getOperateType()==33){
//            result = "shouyeNo2";
//        }else if(record.getOperateType()==34){
//            result = "shouyeNo3";
//        }else if (record.getOperateType()==35){
//            result = "shouyeNo4";
//        }else if (record.getOperateType()==36){
//            result = "shouyeNo5";
//        }
//        return result;
//    }
//
//    public String orginArr(String originType){
//        String origins="";
//        String[] originArr=originType.split(",");
//        for(int i=0;i<originArr.length;i++){
//            if(originArr[i].equals("1")){//全部
//                origins+=0+",";
//            }else if(originArr[i].equals("2")){//微博
//                origins+=5+",";
//            }else if(originArr[i].equals("4")){//网站
//                origins+=1+",";
//            }else if(originArr[i].equals("7")){//新闻
//                origins+=9+",";
//            }else if(originArr[i].equals("3")){//论坛
//                origins+=2+",";
//            }else if(originArr[i].equals("8")){//博客
//                origins+=3+",";
//            }else if(originArr[i].equals("5")){//微信
//                origins+=7+",";
//            }else if(originArr[i].equals("6")){//客户端
//                origins+=13+",";
//            }else if(originArr[i].equals("9")){//政务
//                origins+=11+",";
//            }else if(originArr[i].equals("10")){//报刊
//                origins+=10+",";
//            }else if(originArr[i].equals("11")){//外媒
//                origins+=6+",";
//            }else if(originArr[i].equals("12")){//视频
//                origins+=8+",";
//            }
//        }
//        origins=origins.substring(0,origins.length() - 1);
//
//        return origins;
//    }
//
//    public String hotEventPreview() throws Exception{
//        if(hotEventPreview!=null&&hotEventPreview.getId()!=null){
//            hotEventPreview = userCenterBean.findOperationAdminWbById(hotEventPreview.getId());
//            if(null!=hotEventPreview){
//            	Integer readNum = hotEventPreview.getReadNumber();
//            	if(readNum == null){
//            		readNum = 1;
//            	}else{
//            		readNum++;
//            	}
//            	hotEventPreview.setReadNumber(readNum);
//            	userCenterBean.updateOperationAdminWbReadNum(hotEventPreview);
//            }
//        }
//        return "hotEventPreview";
//    }
//
//    public String hotEvents() throws Exception{
//        page = page==0?1:page;
//        pagesize = 20;
//        PaginationSupport ps = userCenterBean.findHotEventByPage(page,pagesize);
//        if(ps!=null&&ps.getTotalCount()>0){
//            total = ps.getTotalCount();
//            hotEvents = ps.getItems();
//            maxpage = ps.getIndexes().length;
//        }
//
//        return "hotEvents";
//    }
//
//    public static Long getNewHotCheckNum(Integer date,String starttime,String endtime,String keyword, User user ){
//        Long a =0l;
//        if(starttime!=null && endtime!=null){
//            a = getLongTime(starttime,endtime);
//        }
//        // 获取预估数量  3天内的方法和3天以上的方法走的是不一样的
//        Params params = new Params();
//        if (date == 72) {
//            params.setDate(Params.TIME_72HOURS);
//        } else if (date == 24) {
//            params.setDate(Params.TIME_24HOURS);
//        } else if (date == 3) {
//            params.setDate(Params.TIME_THREEDAY);
//        } else if (date == 5) {
//            params.setDate(Params.TIME_FIVEDAY);
//        } else if (date == 7) {
//            params.setDate(Params.TIME_WEEKDAY);
//        } else {
//            params.setDate(Params.TIME_OTHER);
//            params.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.parse(starttime)));
//            params.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.parse(endtime)));
//        }
//        if(StringUtils.isNotBlank(keyword)){
//            params.setKeyword(keyword);
//        }
//        long number = 0;
//        IContentCommonNetView view;
//        try {
//            if (a<=10*24*60*60*1000){
//            	if(user!=null){
//            		view = WyqWebMethodV1.searchTotalV1_001(user.getUserId()+"",params);
//            	}else{
//            		HttpServletRequest request = ServletActionContext.getRequest();
//            		view = WyqWebMethodV1.searchTotalV1_001(CommonUtils.getIp(request),params);
//            	}
//            	// 如果请求失败
//            	if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
//            		throw new RuntimeException(view.getMessage());
//            	}
//            	number = view.getTotalCount();
//            }
//            return number;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static Long getLongTime(String starttime,String endtime) {
//        long sum=0l;
//        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//        Date dt1;
//        Date dt2;
//        try {
//			dt1 = sdf.parse(starttime);
//			dt2 = sdf.parse(endtime);
//			long ts1 = dt1.getTime();
//			long ts2 = dt2.getTime();
//			sum=ts2-ts1;
//        } catch (Exception e) {
//        	e.printStackTrace();
//        }
//        System.out.println(sum);
//        return sum;
//    }
//
//    public void hotCheck(){
//        fetchSessionAdmin();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setContentType("application/json;charset=GBK;");
//        PrintWriter printWriter = null;
//        Object[] json = new Object[1];
//        String nullStr = null;
//        Map<String, String> map = new HashMap<String, String>();
//        try {
//            printWriter = response.getWriter();
//            if(keyword==null||"".equals(keyword)){
//                printWriter.print(nullStr);
//                return;
//            }
//            keyword=keyword.replace("_","+");
//            /*String url = SysConfig.API_ALL_URL+CommonAPIConfig.getValue("HotCheck");
//            String params = "platform=2&channel=2&keyword="+URLEncoder.encode(keyword,"utf-8");
//            if(admin!=null){
//                params += "&userTag="+admin.getUserId();
//            }else{
//            	HttpServletRequest request = ServletActionContext.getRequest();
//            	params += "&userTag="+11;
//            	params += "&userTag="+CommonUtils.getIp(request);
//         	}
//            if(StringUtils.isBlank(data)){
//                data="24";
//            }
//            if("24".equals(data) || "72".equals(data)){
//
//            }else{
//                data="-1";
//                params += "&startTime="+URLEncoder.encode(startTime, "UTF-8");
////                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.parse(startTime));
//                params += "&endTime="+URLEncoder.encode(endTime, "UTF-8");
////new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.parse(endTime));
//            }
//            params += "&date="+data;
//            if(filterKeyword!=null&&!"".equals(filterKeyword)){
//                params += "&filterKeyword="+filterKeyword;
//            }*/
//            HotKeywordRecord hotKeywordRecord =new HotKeywordRecord();
//            if(count == 0){
//                hotKeywordRecord.setType(1);
//            }else if(count == 1){
//                hotKeywordRecord.setType(2);
//            }else if(count == 2){
//                hotKeywordRecord.setType(3);
//            }
//            CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_SEARCH_HOT, Constants.OPERATE_LOG_OPERATE_TYPE_R, hotKeywordRecord, null, keyword);
////            String result = sendGet(url, params);
//            //新的查询敏感词和数量接口
//            if(StringUtils.isNotBlank(keyword)){
//                String[] keywordArray = keyword.split(",");
////                String[] filterKeywordArray = filterKeyword.split(",");
//                for(int i=0;i<keywordArray.length;i++){
//                 // 检测模块敏感词  不同模块需要使用不同的，敏感词判断 ，
//                    List<String> sensitiveWordList  = GetCommon.analyzerSensitiveWordsHot(keywordArray[i]);
//
//                    if (CollectionUtils.isNotEmpty(sensitiveWordList)) {
//                        map.put("code","1111");
//                        map.put("message", "对不起，您的关键词包含敏感词语，请检查后再试！");
//                        printWriter.print(JSONObject.toJSONString(map));
//                        return;
//                    }else{
//                        //无敏感词，查询数量
//                        Long a = getNewHotCheckNum(date ,startTime,endTime,keywordArray[i], admin);
//                        if(null!=a){
//                            if(a<=10){
//                                map.put("code","2222");
//                                map.put("message", "此关键词分析数量太少，请换个词试试！");
//                                printWriter.print(JSONObject.toJSONString(map));
//                                return;
//                            }else{
//                                map.put("code","0000");
//                            }
//                        }else{
//                            map.put("code","3333");
//                            map.put("message", "请联系客服或者稍候再试！");
//                            printWriter.print(JSONObject.toJSONString(map));
//                            return;
//                        }
//
//                    }
//                }
//            }
//
//            printWriter.print(JSONObject.toJSONString(map));
//            return;
//        } catch (Exception e) {
//            //e.printStackTrace();
//        }
//
//        map.put("code","4444");
//        map.put("message", "请联系客服或者稍候再试！");
//        printWriter.print(JSONObject.toJSONString(map));
//        return;
//    }
//
//    public void recordUserLog() throws Exception{
//        fetchSessionAdmin();
//        if(operateLogObject!=null&&operateLogObject.getProductPageCode()!=null){
//            CommonUtils.operatePageLog(ServletActionContext.getRequest(), admin,operateLogObject.getProductPageCode(),
//                operateLogObject.getProductPageDesc(),null);
//        }
//    }
//
//    public void shareSingleHotSearch() throws Exception{
//        if(heatShare!=null&&heatShare.getKeyword()!=null){
//            fetchSessionAdmin();
//            int userId = 0;
//            if(admin!=null){
//                userId = admin.getUserId();
//            }
//            int heatShareId = 0;
//            String[] result = new String[2];
//            try {
//                shareCode = SinaWeiboMid2Id.id2Mid("9" + getFullNumByUserId(8,userId) + System.currentTimeMillis());
//                int orderHeatId = 0;
//                orderHeatReportRelation = warningBean.findReportRelation(orderRecordId);
//                if(orderHeatReportRelation != null){
//                    orderHeatId = orderHeatReportRelation.getId();
//                }
//                //固化时数据库插入数据
//                System.out.println("shouyeAction   =====》"+new Date()+" sharecode= "+shareCode+" userId= "+userId+" title= "+heatShare.getTitle()+" categoryId= "+heatShare.getCategoryId()+" shareDate= "+heatShare.getShareDate()+" orderRecordId= "+orderHeatId+" starttime= "+starttime+" endtime= "+endtime);
//                heatShareId = warningBean.saveHeatShareAndVersion(shareCode,userId,heatShare.getTitle(),heatShare.getCategoryId(),heatShare.getShareDate(),orderHeatId,starttime,endtime,date,heatShare.getKeyword(),heatShare.getFilterKeyword());
//                System.out.println("shouyeAction   =====》 heatShareId="+new Date()+heatShareId);
////                HeatReportDwr dwr = new HeatReportDwr();
////                String[] keywordArray=heatShare.getKeyword().split(",");
////                System.out.println(newOrOld);
////                for(int a=0;a<keywordArray.length;a++){
////                    if("1".equals(newOrOld)){
////                        dwr.newShareReport2(keywordArray[a], userId, date, heatShare.getTitle(),
////                                heatShare.getFilterKeyword(), String.valueOf(Math.random() * 30),
////                                systemConfig,shareCode,warningBean,heatShare.getCategoryId(),
////                                categoryType,secondCategory,categoryLevel,heatShare.getShareDate(),
////                                heatShareId,orderHeatId,starttime,endtime,secondClassifyName,threeClassifyName,isAll,a+1);
////                    }else{
////                        dwr.shareReport2(keywordArray[a], userId, date, heatShare.getTitle(),
////                                heatShare.getFilterKeyword(), String.valueOf(Math.random() * 30),
////                                systemConfig,shareCode,warningBean,heatShare.getCategoryId(),
////                                categoryType,secondCategory,categoryLevel,heatShare.getShareDate(),
////                                heatShareId,orderHeatId,starttime,endtime,secondClassifyName,threeClassifyName,isAll,a+1);
////                    }
////                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            /*heatShare.setCreateTime(new Date());
//            heatShare.setStatus(1);
//            heatShare.setUserId(userId);
//            heatShare.setShareCode(shareCode);
//            warningBean.saveHeatShareSingle(heatShare);*/
//            HttpServletResponse response = ServletActionContext.getResponse();
//            response.setContentType("application/json;charset=GBK;");
//            PrintWriter printWriter = null;
//            printWriter = response.getWriter();
//            result[0] = heatShareId +"";
//            result[1] = shareCode +"";
//            printWriter.print(JSON.toJSON(result));
//            printWriter.close();
//        }
//    }
//
//    public String hotAndEmotion() throws Exception {
//
//        fetchSessionAdmin();
//        fetchRightNumber();
//        fetchProductlist(Constants.PACKAGE_TYPE_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_WEIBO_ANALYSIS);
//        fetchProductlist(Constants.PACKAGE_TYPE_KEYWORD);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_COUNT);
//        fetchProductlist(Constants.PACKAGE_TYPE_HOT_REPORT_DAYS);
//        notLoginOperateRecord = null;
//        heatViewPath = systemConfig.getHeatViewPath();
//        if (title != null && !title.trim().equals("")) {
//            title = title.trim();
//            title = title.replaceAll(" +", "+");
//        }
//        startTime = dateformat(24);
//        endTime = dateformat(0);
//        return "hotAndEmotion";
//    }
//    public static String dateformat(Integer type) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
//        Date date = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        if (type == 24) {
//            calendar.add(Calendar.DAY_OF_MONTH, -1);
//        }
//        if (type == 3) {
//            calendar.add(Calendar.DAY_OF_MONTH, -3);
//        }
//        date = calendar.getTime();
//        System.out.println(sdf.format(date));
//        return sdf.format(date);
//    }
//
//    public void getId(){
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setContentType("application/json;charset=GBK;");
//        PrintWriter printWriter = null;
//        try {
//            printWriter = response.getWriter();
//            if(shareCode!=null&&!"".equals(shareCode)) {
//                int hsId = warningBean.findHeatShareId(shareCode);
//                if (hsId > 0) {
//                    printWriter.print(hsId);
//                } else {
//                    printWriter.print(hsId);
//                }
//            }
//            printWriter.print(0);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private String getFullNumByUserId(int maxLen, int userId) {
//        String userIdStr = String.valueOf(userId);
//        while (userIdStr.length() < maxLen) {
//            userIdStr = "0" + userIdStr;
//        }
//        return userIdStr;
//    }
//
//    /**
//     * 发送GET请求
//     *
//     * @param url
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    public static String sendGet(String url, String params) {
//        try {
//            long s = System.currentTimeMillis();
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpGet httpGet = new HttpGet(url + "?" + params);
//            httpGet.setHeader("Connection", "Close");
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*60*5).setConnectTimeout(1000*60).build();//设置请求和传输超时时间
//            httpGet.setConfig(requestConfig);
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//            HttpEntity resultEntity = httpResponse.getEntity();
//            System.out.println("ShouyeController.sendGet() : cost = [" + (System.currentTimeMillis() - s) + " ms.] URL = [" + url + "?" + params + "]");
//            return EntityUtils.toString(resultEntity, "GBK");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//	public KeyWordBean getKeywordBean() {
//		return keywordBean;
//	}
//
//	public void setKeywordBean(KeyWordBean keywordBean) {
//		this.keywordBean = keywordBean;
//	}
//
//	public List getUserFweblist() {
//		return userFweblist;
//	}
//
//	public void setUserFweblist(List userFweblist) {
//		this.userFweblist = userFweblist;
//	}
//
//	public int getFirstWId() {
//		return firstWId;
//	}
//
//	public void setFirstWId(int firstWId) {
//		this.firstWId = firstWId;
//	}
//
//	public List<KeyWord> getKwList() {
//		return kwList;
//	}
//
//	public void setKwList(List<KeyWord> kwList) {
//		this.kwList = kwList;
//	}
//
//	public WarningBean getWarningBean() {
//		return warningBean;
//	}
//
//	public void setWarningBean(WarningBean warningBean) {
//		this.warningBean = warningBean;
//	}
//
//	public int getGuideCount() {
//		return guideCount;
//	}
//
//	public void setGuideCount(int guideCount) {
//		this.guideCount = guideCount;
//	}
//
//	public int getProgramno() {
//		return programno;
//	}
//
//	public void setProgramno(int programno) {
//		this.programno = programno;
//	}
//
//	public UserCenterBean getUserCenterBean() {
//		return userCenterBean;
//	}
//
//	public void setUserCenterBean(UserCenterBean userCenterBean) {
//		this.userCenterBean = userCenterBean;
//	}
//
//	public int getTishi() {
//		return tishi;
//	}
//
//	public void setTishi(int tishi) {
//		this.tishi = tishi;
//	}
//
//	public int getBasis() {
//		return basis;
//	}
//
//	public void setBasis(int basis) {
//		this.basis = basis;
//	}
//
//    @Override
//    public String getKeyword() {
//        return keyword;
//    }
//
//    @Override
//    public void setKeyword(String keyword) {
//        this.keyword = keyword;
//    }
//
//    public String getFilterKeyword() {
//        return filterKeyword;
//    }
//
//    public void setFilterKeyword(String filterKeyword) {
//        this.filterKeyword = filterKeyword;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getViewUri() {
//		return viewUri;
//	}
//
//	public void setViewUri(String viewUri) {
//		this.viewUri = viewUri;
//	}
//
//	public String getSmallClass() {
//        return smallClass;
//    }
//
//    public void setSmallClass(String smallClass) {
//        this.smallClass = smallClass;
//    }
//
//    public NotLoginOperateRecord getNotLoginOperateRecord() {
//        return notLoginOperateRecord;
//    }
//
//    public void setNotLoginOperateRecord(NotLoginOperateRecord notLoginOperateRecord) {
//        this.notLoginOperateRecord = notLoginOperateRecord;
//    }
//
//    public KeyWord getKw() {
//        return kw;
//    }
//
//    public void setKw(KeyWord kw) {
//        this.kw = kw;
//    }
//
//    public int getOperateType() {
//        return operateType;
//    }
//
//    public void setOperateType(int operateType) {
//        this.operateType = operateType;
//    }
//
//    public IncidentAnalysisTask getAnalysisTask() {
//        return analysisTask;
//    }
//
//    public void setAnalysisTask(IncidentAnalysisTask analysisTask) {
//        this.analysisTask = analysisTask;
//    }
//
//    public ProductsAnalysisKeyword getPak() {
//        return pak;
//    }
//
//    public void setPak(ProductsAnalysisKeyword pak) {
//        this.pak = pak;
//    }
//
//    public int getStarSex() {
//        return starSex;
//    }
//
//    public void setStarSex(int starSex) {
//        this.starSex = starSex;
//    }
//
//    public int getStockType() {
//        return stockType;
//    }
//
//    public void setStockType(int stockType) {
//        this.stockType = stockType;
//    }
//
//    public String getStarProfession() {
//        return starProfession;
//    }
//
//    public void setStarProfession(String starProfession) {
//        this.starProfession = starProfession;
//    }
//
//    public String getThinkKeyword() {
//        return thinkKeyword;
//    }
//
//    public void setThinkKeyword(String thinkKeyword) {
//        this.thinkKeyword = thinkKeyword;
//    }
//
//    public List<OperationAdminWb> getHotEvents() {
//        return hotEvents;
//    }
//
//    public void setHotEvents(List<OperationAdminWb> hotEvents) {
//        this.hotEvents = hotEvents;
//    }
//
//    public String getCategoryStr() {
//        return categoryStr;
//    }
//
//    public void setCategoryStr(String categoryStr) {
//        this.categoryStr = categoryStr;
//    }
//
//    public String getSmallClassStr() {
//        return smallClassStr;
//    }
//
//    public void setSmallClassStr(String smallClassStr) {
//        this.smallClassStr = smallClassStr;
//    }
//
//    public List<RankingListWeb> getRankingList() {
//        return rankingList;
//    }
//
//    public void setRankingList(List<RankingListWeb> rankingList) {
//        this.rankingList = rankingList;
//    }
//
//    public List getRankingListPaging() {
//        return rankingListPaging;
//    }
//
//    public void setRankingListPaging(List rankingListPaging) {
//        this.rankingListPaging = rankingListPaging;
//    }
//
//    public int getCurrentPageCode() {
//        return currentPageCode;
//    }
//
//    public void setCurrentPageCode(int currentPageCode) {
//        this.currentPageCode = currentPageCode;
//    }
//
//    public String getSearchName() {
//        return searchName;
//    }
//
//    public void setSearchName(String searchName) {
//        this.searchName = searchName;
//    }
//
//    public String getMiddleClassStr() {
//        return middleClassStr;
//    }
//
//    public void setMiddleClassStr(String middleClassStr) {
//        this.middleClassStr = middleClassStr;
//    }
//
//    public String getMiddleClass() {
//        return middleClass;
//    }
//
//    public void setMiddleClass(String middleClass) {
//        this.middleClass = middleClass;
//    }
//
//    public int getOrder() {
//        return order;
//    }
//
//    public void setOrder(int order) {
//        this.order = order;
//    }
//
//    public int getSort() {
//        return sort;
//    }
//
//    public void setSort(int sort) {
//        this.sort = sort;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public int getCarTag() {
//        return carTag;
//    }
//
//    public void setCarTag(int carTag) {
//        this.carTag = carTag;
//    }
//
//    public List<UserContact> getTlist() {
//        return tlist;
//    }
//
//    public void setTlist(List<UserContact> tlist) {
//        this.tlist = tlist;
//    }
//
//    public int getIsSinaAuth() {
//        return isSinaAuth;
//    }
//
//    public void setIsSinaAuth(int isSinaAuth) {
//        this.isSinaAuth = isSinaAuth;
//    }
//
//    public List<HeatReport> getHrList() {
//        return hrList;
//    }
//
//    public void setHrList(List<HeatReport> hrList) {
//        this.hrList = hrList;
//    }
//
//    public List<HeatReportRecord> getHrrList() {
//        return hrrList;
//    }
//
//    public void setHrrList(List<HeatReportRecord> hrrList) {
//        this.hrrList = hrrList;
//    }
//
//    public String getReportViewPath() {
//        return reportViewPath;
//    }
//
//    public void setReportViewPath(String reportViewPath) {
//        this.reportViewPath = reportViewPath;
//    }
//
//    public Map<Integer, UserContact> getUcMap() {
//        return ucMap;
//    }
//
//    public void setUcMap(Map<Integer, UserContact> ucMap) {
//        this.ucMap = ucMap;
//    }
//
//    public Integer getComputerTag() {
//        return computerTag;
//    }
//
//    public void setComputerTag(Integer computerTag) {
//        this.computerTag = computerTag;
//    }
//
//    public String getKeyword3() {
//        return keyword3;
//    }
//
//    public void setKeyword3(String keyword3) {
//        this.keyword3 = keyword3;
//    }
//
//    public String getKeyword2() {
//        return keyword2;
//    }
//
//    public void setKeyword2(String keyword2) {
//        this.keyword2 = keyword2;
//    }
//
//    public int getDate() {
//        return date;
//    }
//
//    public void setDate(int date) {
//        this.date = date;
//    }
//
//    public Integer getCelebrityTag() {
//        return celebrityTag;
//    }
//
//    public void setCelebrityTag(Integer celebrityTag) {
//        this.celebrityTag = celebrityTag;
//    }
//
//    public OperationAdminWb getHotEventPreview() {
//        return hotEventPreview;
//    }
//
//    public void setHotEventPreview(OperationAdminWb hotEventPreview) {
//        this.hotEventPreview = hotEventPreview;
//    }
//
//    public String getHeatViewPath() {
//        return heatViewPath;
//    }
//
//    public void setHeatViewPath(String heatViewPath) {
//        this.heatViewPath = heatViewPath;
//    }
//
//    public Integer getAppliancesTag() {
//        return appliancesTag;
//    }
//
//    public void setAppliancesTag(Integer appliancesTag) {
//        this.appliancesTag = appliancesTag;
//    }
//
//    public int getNum() {
//        return num;
//    }
//
//    public void setNum(int num) {
//        this.num = num;
//    }
//
//	public String getParamValueStr() {
//		return paramValueStr;
//	}
//
//	public void setParamValueStr(String paramValueStr) {
//		this.paramValueStr = paramValueStr;
//	}
//
//	public String getParamName() {
//		return paramName;
//	}
//
//	public void setParamName(String paramName) {
//		this.paramName = paramName;
//	}
//
//	public String getParamValue() {
//		return paramValue;
//	}
//
//	public void setParamValue(String paramValue) {
//		this.paramValue = paramValue;
//	}
//
//    public String getHeatKeyword() {
//        return heatKeyword;
//    }
//
//    public void setHeatKeyword(String heatKeyword) {
//        this.heatKeyword = heatKeyword;
//    }
//
//    public String getShareCode() {
//        return shareCode;
//    }
//
//    public void setShareCode(String shareCode) {
//        this.shareCode = shareCode;
//    }
//
//    public String getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(String categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCategoryType() {
//        return categoryType;
//    }
//
//    public void setCategoryType(String categoryType) {
//        this.categoryType = categoryType;
//    }
//
//    public String getSecondCategory() {
//        return secondCategory;
//    }
//
//    public void setSecondCategory(String secondCategory) {
//        this.secondCategory = secondCategory;
//    }
//
//    public OperateLogObject getOperateLogObject() {
//        return operateLogObject;
//    }
//
//    public void setOperateLogObject(OperateLogObject operateLogObject) {
//        this.operateLogObject = operateLogObject;
//    }
//
//    public HeatShare getHeatShare() {
//        return heatShare;
//    }
//
//    public void setHeatShare(HeatShare heatShare) {
//        this.heatShare = heatShare;
//    }
//
//    public int getSingleShare() {
//        return singleShare;
//    }
//
//    public void setSingleShare(int singleShare) {
//        this.singleShare = singleShare;
//    }
//
//    public OperationAdminHot getOperationAdminHot() {
//        return operationAdminHot;
//    }
//
//    public void setOperationAdminHot(OperationAdminHot operationAdminHot) {
//        this.operationAdminHot = operationAdminHot;
//    }
//
//    public OperationAdminHotContent getOperationAdminHotContent() {
//        return operationAdminHotContent;
//    }
//
//    public void setOperationAdminHotContent(OperationAdminHotContent operationAdminHotContent) {
//        this.operationAdminHotContent = operationAdminHotContent;
//    }
//
//    public OrderHeatReportRelation getOrderHeatReportRelation() {
//        return orderHeatReportRelation;
//    }
//
//    public void setOrderHeatReportRelation(OrderHeatReportRelation orderHeatReportRelation) {
//        this.orderHeatReportRelation = orderHeatReportRelation;
//    }
//
//    public String getExportName() {
//        return exportName;
//    }
//
//    public void setExportName(String exportName) {
//        this.exportName = exportName;
//    }
//
//    public Integer getCategoryLevel() {
//        return categoryLevel;
//    }
//
//    public void setCategoryLevel(Integer categoryLevel) {
//        this.categoryLevel = categoryLevel;
//    }
//
//    public int getFenhuangType() {
//        return fenhuangType;
//    }
//
//    public void setFenhuangType(int fenhuangType) {
//        this.fenhuangType = fenhuangType;
//    }
//
//    public List<HeatShare> getHsList() {
//        return hsList;
//    }
//
//    public void setHsList(List<HeatShare> hsList) {
//        this.hsList = hsList;
//    }
//
//    public int getOrderRecordId() {
//        return orderRecordId;
//    }
//
//    public void setOrderRecordId(int orderRecordId) {
//        this.orderRecordId = orderRecordId;
//    }
//
//    public String getStarttime() {
//        return starttime;
//    }
//
//    public void setStarttime(String starttime) {
//        this.starttime = starttime;
//    }
//
//    public String getEndtime() {
//        return endtime;
//    }
//
//    public void setEndtime(String endtime) {
//        this.endtime = endtime;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
//
//    public int getSearchType() {
//        return searchType;
//    }
//
//    public void setSearchType(int searchType) {
//        this.searchType = searchType;
//    }
//
//    public String getThreeClassifyName() {
//        return threeClassifyName;
//    }
//
//    public void setThreeClassifyName(String threeClassifyName) {
//        this.threeClassifyName = threeClassifyName;
//    }
//
//    public String getSecondClassifyName() {
//        return secondClassifyName;
//    }
//
//    public void setSecondClassifyName(String secondClassifyName) {
//        this.secondClassifyName = secondClassifyName;
//    }
//
//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }
//
//    public int getIsAll() {
//        return isAll;
//    }
//
//    public void setIsAll(int isAll) {
//        this.isAll = isAll;
//    }
//
//	public List<ActivitySendRecord> getActivitySendRecordList() {
//		return activitySendRecordList;
//	}
//
//	public void setActivitySendRecordList(
//			List<ActivitySendRecord> activitySendRecordList) {
//		this.activitySendRecordList = activitySendRecordList;
//	}

}
