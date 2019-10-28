package com.miduchina.wrd.webthermalquery.controller;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.util.MD5Utils;
import com.miduchina.wrd.webthermalquery.fegin.UserFeignClient;
import com.miduchina.wrd.webthermalquery.util.LoginUtils;
import com.tuke.gospel.core.util.SimpleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 账号鉴权Action（登陆与登出）
 *
 * @author James
 *
 */
@Slf4j
@Controller
@RequestMapping("identiy/login")
public class IdentificationController extends BaseController {
	/**
	 *
	 */
//	@Autowired
//	@Qualifier("stringRedisTemplate")
//	private StringRedisTemplate redisTemplate;
	@Resource
	private UserFeignClient userFeignClient;

//	private String vcode;// 验证码参数
//	private String mToken;
//	private String isRemengConfigSite;
//	public String wap_url;
//	public String uuid;
//	public String neiyeLogo;
//	public String logoUrl;
//	public String dnsid;
//	public int needQQService = 1;
//	private int flag = 0;
//	private int isJiankongManager = 0;
//	private String imgVcode;
//	private Integer isValidate = 1;
//	private String msg;
//	private String code;
//	private String _ran;// 毫秒+四位随机数，作为验证码唯一凭证
//	private String apkHref; // apk下载路径
//	private String iosHref; // ios下载路径
//	private String qrCodeImg; // 二维码图片路径
//	protected SystemConfig systemConfig;
//	private boolean kickLogout;
//	private String kickTime;
//	private int helptype;// 常见问题helptype
//	private List<Help> helplist;// 常见问题列表
//	private List<Help> helphotlist;// 常见问题列表
//	private int helpid;
//	private String searchname;
//	private List<OperationAdminWb> hotEvents;// 首页热点事件
//	private String categoryStr = "";// 排行榜
//	private String smallClassStr = "";
//	private String middleClassStr = "";
//	private String paramValueStr = "";
//	private int quickLogin;
//	private List<AboutUsArticle> mediaArticleList;	//媒体报导
//	private List<AboutUsArticle> wyqCaseList;	//微舆情解读案例
//	private String platform;
//	private Integer eventLabel;
//	private int aboutUsFlag = 0;
//	private String viewUri;
//	//手机号验证码登陆
//	private String mobile;//手机号
//	private String imgVcode2;//输入的验证码
//	private Integer selectLoginType;//1；打开手机验证码登录界面
//
//	/*关于我们文章接口*/
//	private String title;
//	private Integer category;
//	private Integer label;
//	private Integer type;
//	private String dateFormat;
//	private Integer articleId;
//	private Integer id;
//
//	/*关于我们文章接口*/
//
//	@Autowired
//	private UserCenterBean userCenterBean;
//	@Autowired
//	private QuestionList questionList;
//	@Autowired
//	private UserBean userBean;
//	@Autowired
//	private JavaMailSender mailSender;
//	@Autowired
//	private AboutUsArticleBean aboutUsArticleBean;
//
//	public Integer getSelectLoginType() {
//		return selectLoginType;
//	}
//
//	public String getViewUri() {
//		return viewUri;
//	}
//
//	public void setViewUri(String viewUri) {
//		this.viewUri = viewUri;
//	}
//
//	public void setSelectLoginType(Integer selectLoginType) {
//		this.selectLoginType = selectLoginType;
//	}
//
//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//
//	public String getImgVcode2() {
//		return imgVcode2;
//	}
//
//	public void setImgVcode2(String imgVcode2) {
//		this.imgVcode2 = imgVcode2;
//	}
//
//	public QuestionList getQuestionList() {
//		return questionList;
//	}
//
//	public void setQuestionList(QuestionList questionList) {
//		this.questionList = questionList;
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
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//
//	public Integer getIsValidate() {
//		return isValidate;
//	}
//
//	public void setIsValidate(Integer isValidate) {
//		this.isValidate = isValidate;
//	}
//
//	public String getImgVcode() {
//		return imgVcode;
//	}
//
//	public void setImgVcode(String imgVcode) {
//		this.imgVcode = imgVcode;
//	}
//
//	public int getFlag() {
//		return flag;
//	}
//
//	public void setFlag(int flag) {
//		this.flag = flag;
//	}
//
//	public String getWap_url() {
//		return wap_url;
//	}
//
//	public void setWap_url(String wapUrl) {
//		wap_url = wapUrl;
//	}
//
//	public int getHelptype() {
//		return helptype;
//	}
//
//	public void setHelptype(int helptype) {
//		this.helptype = helptype;
//	}
//
//	public List<Help> getHelplist() {
//		return helplist;
//	}
//
//	public void setHelplist(List<Help> helplist) {
//		this.helplist = helplist;
//	}
//
//	public List<Help> getHelphotlist() {
//		return helphotlist;
//	}
//
//	public void setHelphotlist(List<Help> helphotlist) {
//		this.helphotlist = helphotlist;
//	}
//
//	public int getHelpid() {
//		return helpid;
//	}
//
//	public void setHelpid(int helpid) {
//		this.helpid = helpid;
//	}
//
//	public String getSearchname() {
//		return searchname;
//	}
//
//	public void setSearchname(String searchname) {
//		this.searchname = searchname;
//	}
//
//	public int getQuickLogin() {
//		return quickLogin;
//	}
//
//	public void setQuickLogin(int quickLogin) {
//		this.quickLogin = quickLogin;
//	}
//
//	/**
//	 * org.apache.log4j.Logger对象log
//	 */
//	// private static final Logger logger = Logger
//	// .getLogger(IdentificationAction.class);
//	private static final Log logger = LogFactory.getLog(IdentificationAction.class);
//
//	@Autowired
//	private AuthenticationBean authenticationBean;
//
//	private KeyWordBean keywordBean;
//
//	private WarningBean warningBean;
//
//	public KeyWordBean getKeywordBean() {
//		return keywordBean;
//	}
//
//	public void setKeywordBean(KeyWordBean keywordBean) {
//		this.keywordBean = keywordBean;
//	}
//
//	public void setAuthenticationBean(AuthenticationBean authenticationBean) {
//		this.authenticationBean = authenticationBean;
//	}
//
//	public WarningBean getWarningBean() {
//		return warningBean;
//	}
//
//	public void setWarningBean(WarningBean warningBean) {
//		this.warningBean = warningBean;
//	}

//	public String index() {
//		/*hotEvents = userCenterBean.findHotEvent();
//		if (CollectionUtils.isNotEmpty(hotEvents) && hotEvents.size() > 5) {
//			hotEvents = hotEvents.subList(0, 5);
//		}*/
//		viewUri = HotSearchAction.getDNSUrl(ServletActionContext.getRequest().getRequestURL().toString());
//		return SUCCESS;
//	}
//
//	public String logon() {
//
//		logger.info("cpId logon");
//
//		return SUCCESS;
//	}

	/**
	 * 热搜展示页
	 *
	 * @return
	 * @throws Exception
	 */
//	public String indexContent() throws Exception {
//		// apkHref = systemConfig.getApkHref();
//		// iosHref = systemConfig.getIosHref();
//		// qrCodeImg = systemConfig.getQrCodeImg();
//		// fetchSessionAdmin();
//
//		return Action.SUCCESS;
//	}

//	/**
//	 * 产品介绍
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public String productIntroduction() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		return Action.SUCCESS;
//	}

	/**
	 * 新手专区
	 *
	 * @return
	 * @throws Exception
	 */
//	public String noviceArea() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		fetchRightNumber();
//		return Action.SUCCESS;
//	}

	/**
	 * 客户端下载
	 *
	 * @return
	 * @throws Exception
	 */
//	public String downLoad() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		fetchRightNumber();
//		return Action.SUCCESS;
//	}

	/**
	 * 帮助中心
	 *
	 * @return
	 * @throws Exception
	 */
//	public String help() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		fetchRightNumber();
//
//		helphotlist = questionList.HotQuestionContent(1);
//		return Action.SUCCESS;
//	}

	/**
	 * 关于我们
	 *
	 * @return
	 * @throws Exception
	 */
//	public String aboutUs() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		fetchRightNumber();
//		return Action.SUCCESS;
//	}

	/**
	 * 联系我们
	 *
	 * @return
	 * @throws Exception
	 */
//	public String contectUs() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		fetchRightNumber();
//		return Action.SUCCESS;
//	}

	/**
	 * 常见问题列表
	 *
	 * @return
	 * @throws Exception
	 */
//	public String cquestion() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		fetchRightNumber();
//		helphotlist = questionList.HotQuestionContent(1);
//		helplist = questionList.QuestionContent(helptype);
//		return Action.SUCCESS;
//	}
//
//	/**
//	 * 常见热点问题
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public String cquestionhot() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		fetchRightNumber();
//		helphotlist = questionList.HotQuestionContent(1);
//		helplist = questionList.CquestionHotTitle(helpid);
//		return Action.SUCCESS;
//	}
//
//	/**
//	 * 搜索常见问题
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public String searchquestion() throws Exception {
//		apkHref = systemConfig.getApkHref();
//		iosHref = systemConfig.getIosHref();
//		qrCodeImg = systemConfig.getQrCodeImg();
//		fetchSessionAdmin();
//		fetchRightNumber();
//		helphotlist = questionList.HotQuestionContent(1);
//		helplist = questionList.SearchQuestion(searchname);
//		return Action.SUCCESS;
//	}
//
//	/**
//	 * 搜索常见问题标题
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public void searchKquestion() throws Exception {
//
//		fetchSessionAdmin();
//		fetchRightNumber();
//		helplist = questionList.SearchKQuestion(searchname);
//		CommonUtils.writeJSON(helplist);
//	}
//
//    /**
//     * 应用场景
//     *
//     * @return
//     * @throws Exception
//     */
//    public String useScene() throws Exception {
//        fetchSessionAdmin();
//        fetchRightNumber();
//        return Action.SUCCESS;
//    }
//
//    /**
//     * 公司介绍
//     *
//     * @return
//     * @throws Exception
//     */
//    public String introCompany() throws Exception{
//    	fetchSessionAdmin();
//        fetchRightNumber();
//        return Action.SUCCESS;
//    }
//
//    /**
//     * 功能特色
//     *
//     * @return
//     * @throws Exception
//     */
//    public String functionChar() throws Exception{
//    	fetchSessionAdmin();
//        fetchRightNumber();
//        return Action.SUCCESS;
//    }
//
//    /**
//     * head关于我们
//     *
//     * @return
//     * @throws Exception
//     */
//	public String aboutUser() throws Exception {
//        fetchSessionAdmin();
//        fetchRightNumber();
//        page = 1;
//        aboutUsFlag = 0;
//        mediaArticleGotoPage();
//
//        return Action.SUCCESS;
//    }
//	/**
//     * 关于我们文章
//     *
//     * @return
//     * @throws Exception
//     */
//	public void searchAboutUsArticle() throws Exception {
//        if(type == null){
//        	CommonUtils.writeJSON(null);
//        }else{
//        	if(page == 0)
//        		page = 1;
//        	if(pagesize == 0)
//        		pagesize = 15;
//
//        	String sessionName;
//        	List<AboutUsArticle> articles;
//        	if(type == Constants.ABOUT_US_MEDIA_ARTICLE){
//        		sessionName = Constants.JEDIS_KEYS_MEDIA_ARTICLE_LIST;	//媒体报告
//        	}else if(type == Constants.ABOUT_US_WYQ_CASE){
//        		sessionName = Constants.JEDIS_KEYS_WYQ_CASE_LIST;	//大数据解读（微舆情解读案例）
//        	}else if(type == Constants.ABOUT_US_WYQ_CASE2){
//        		sessionName = Constants.JEDIS_KEYS_WYQ_CASE_LIST2;	//大数据解读（微舆情解读案例）
//        	}else if(type == Constants.ABOUT_US_COMPANY_DYNAMIC){
//        		sessionName = Constants.JEDIS_KEYS_COMPANY_DYNAMIC_LIST;	//公司动态
//        	}else{
//        		CommonUtils.writeJSON(null);
//        		return;
//        	}
//
//    		if(type == Constants.ABOUT_US_WYQ_CASE2){
//            	PaginationSupport<OperationAdminWb> res = null;
//        		res = userCenterBean.findHotEventByPage(page, pagesize);
//        		articles = new ArrayList<AboutUsArticle>();
//            	maxpage = 0;
//        		if(res != null){
//                	maxpage = res.getTotalCount()/pagesize + (res.getTotalCount()%pagesize==0?0:1);
//        			if(CollectionUtils.isNotEmpty(res.getItems())){
//            			List<OperationAdminWb> operationAdminWbList = res.getItems();
//            			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
//        				for(OperationAdminWb operationAdminWb : operationAdminWbList){
//        					AboutUsArticle aboutUsArticle = new AboutUsArticle();
//        					aboutUsArticle.setId(operationAdminWb.getId());
//        					aboutUsArticle.setTitle(operationAdminWb.getTitle());
//        					aboutUsArticle.setSummary(operationAdminWb.getAbstracts());
//        					aboutUsArticle.setTopImage(operationAdminWb.getTopImage());
//        					aboutUsArticle.setArticleUrl(operationAdminWb.getLink());
//        					aboutUsArticle.setImgUrl(operationAdminWb.getImageUrl());
//        					aboutUsArticle.setPublishTime(operationAdminWb.getCreateTime());
//        					aboutUsArticle.setPublishTimeStr(dateFormat.format(operationAdminWb.getCreateTime()));
//        					aboutUsArticle.setTotalCount(res.getTotalCount());
//        					articles.add(aboutUsArticle);
//        				}
//        			}
//            	}
//    		}else{
//            	String json = JedisUtil.getAttribute(Constants.generateJedisKey(sessionName));
//            	if(StringUtils.isNotBlank(json)){
//                	articles = JSON.parseArray(json, AboutUsArticle.class);
//            	}else{
//            		articles = aboutUsArticleBean.getAboutUsArticleList(type, null, null);
//            		if(CollectionUtils.isNotEmpty(articles)){
//            			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
//                		for(AboutUsArticle a : articles){
//                			if(a.getPublishTime() != null)
//                				a.setPublishTimeStr(dateFormat.format(a.getPublishTime()));
//                		}
//            		}
//            		JedisUtil.setAttribute(Constants.generateJedisKey(sessionName), JSON.toJSONString(articles), 1800);
//            	}
//    		}
//
//        	SimpleDateFormat format = null;
//			if(StringUtils.isNotBlank(dateFormat) && (dateFormat.equals("yyyy-MM-dd") || dateFormat.equals("yyyy/MM/dd") || dateFormat.equals("yyyy.MM"))){
//				format = new SimpleDateFormat(dateFormat);
//			}
//        	if(CollectionUtils.isNotEmpty(articles)){
//	        	if(type == Constants.ABOUT_US_MEDIA_ARTICLE){
//	        		if(StringUtils.isNotBlank(title)){
//		        		List<AboutUsArticle> articles2 = new ArrayList<AboutUsArticle>();
//		        		for(AboutUsArticle article : articles){
//		        			if(StringUtils.isNotBlank(article.getTitle()) && article.getTitle().indexOf(title) >= 0){
//		        				articles2.add(article);
//		        			}
//		        		}
//		        		articles = articles2;
//	        		}
//	        	}else if(type == Constants.ABOUT_US_WYQ_CASE){
//	        		List<AboutUsArticle> articles2 = new ArrayList<AboutUsArticle>();
//	        		for(AboutUsArticle article : articles){
//	        			boolean isAccord = true;
//	        			if(StringUtils.isNotBlank(title)){
//	        				if(StringUtils.isBlank(article.getTitle()) || article.getTitle().indexOf(title) < 0)
//	        					isAccord = false;
//	        			}
//
//	        			if(isAccord && category != null){
//	        				if(article.getCategory() == null || article.getCategory().intValue() != category.intValue())
//	        					isAccord = false;
//	        			}
//
//	        			if(isAccord && label != null){
//	        				if(article.getLabel() == null || article.getLabel().intValue() != label.intValue())
//	        					isAccord = false;
//	        			}
//		        		if(isAccord)
//		        			articles2.add(article);
//	        		}
//	        		articles = articles2;
//	        	}
//
//	        	if(type != Constants.ABOUT_US_WYQ_CASE2 && CollectionUtils.isNotEmpty(articles)){
//	                int startPage = (page-1)*pagesize;
//	                int endPage = page*pagesize;
//	        		int size = articles.size();
//	            	maxpage = size/pagesize + (size%pagesize==0?0:1);
//	            	if(size > 0){
//	            		if(size < startPage)
//	            			startPage = size;
//	    	        	if(size < endPage)
//	    	        		endPage = size;
//	    	        	articles = articles.subList(startPage, endPage);
//	    	        	if(format != null)
//		    	        	for(AboutUsArticle a : articles){
//		    	        		if(a.getPublishTime() != null)
//		    	        			a.setPublishTimeStr(format.format(a.getPublishTime()));
//		    	        	}
//	            	}
//	        	}
//        	}
//        	HttpServletResponse httpServletRespons = ServletActionContext.getResponse();
//    		httpServletRespons.setHeader("Access-Control-Allow-Origin","*");	//允许跨域请求
//    		httpServletRespons.setHeader("Access-Control-Allow-Methods","GET,POST");
//    		httpServletRespons.setContentType("application/json;charset=GBK;");
//    		PrintWriter printWriter = null;
//    		try {
//    			printWriter = httpServletRespons.getWriter();
//    			printWriter.print(JSONObject.toJSON(articles));
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    		} finally {
//    			if (printWriter != null) {
//    				printWriter.flush();
//    				printWriter.close();
//    			}
//    		}
//        }
//    }
//
//	public void wyqCaseList(){
//		if(page == 0)
//    		page = 1;
//    	if(pagesize == 0)
//    		pagesize = 15;
//		if(StringUtils.isNotBlank(platform) && platform.indexOf("wyq")<0 && platform.indexOf("yqt")<0 && platform.indexOf("edu")<0){
//			CommonUtils.writeJSON("9999", "平台标识错误");
//			return;
//		}
//		StringBuilder sessionName = new StringBuilder(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page).append("_" + pagesize);	//大数据解读（微舆情解读案例）
//		if(StringUtils.isNotBlank(platform)){
//			sessionName.append("_" + platform);
//		}
//		if(eventLabel != null){
//			sessionName.append("_" + eventLabel);
//		}
//		if(StringUtils.isNotBlank(title)){
//			title = CommonUtils.decodeKeyword(title);
//			sessionName.append("_" + MD5Util.md5(title));
//		}
//
//		String json = JedisUtil.getAttribute(Constants.generateJedisKey(sessionName.toString()));
//    	if(StringUtils.isBlank(json)){
//    		PaginationSupport<OperationAdminWb> res = userCenterBean.findOperationAdminWbList(page, pagesize, platform, eventLabel, title);
//    		//PaginationSupport<OperationAdminWb> res = userCenterBean.findHotEventByPage(page, pagesize);
//    		if(res != null){
//    			List<OperationAdminWb> operationAdminWbs = res.getItems();
//    			maxpage = res.getTotalCount()/15 + (res.getTotalCount()%15==0?0:1);
//    			OperationAdminWbListView view = new OperationAdminWbListView("0000", "请求成功", page, pagesize, maxpage, res.getTotalCount(),
//    					operationAdminWbs);
//    			json = JSONObject.toJSONString(view);
//    			if(operationAdminWbs != null && operationAdminWbs.size() > 0){
//
//    				JedisUtil.setAttribute(Constants.generateJedisKey(sessionName.toString()), json, 1800);
//    			}
//    		}
//    	}
//    	HttpServletResponse httpServletRespons = ServletActionContext.getResponse();
//		httpServletRespons.setHeader("Access-Control-Allow-Origin","*");	//允许跨域请求
//		httpServletRespons.setHeader("Access-Control-Allow-Methods","GET,POST");
//		httpServletRespons.setContentType("application/json;charset=GBK;");
//		PrintWriter printWriter = null;
//    	CommonUtils.writeJSONString(json);
//    	try {
//			printWriter = httpServletRespons.getWriter();
//			printWriter.print(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (printWriter != null) {
//				printWriter.flush();
//				printWriter.close();
//			}
//		}
//	}
//	public void queryInitBigDataGoto() throws Exception {
//		HashMap<String, Object> hashMap=new HashMap<String, Object>();
//        if(page == 0)
//    		page = 1;
//    	pagesize = 15;
//    	//String wyqCaseStr = JedisUtil.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page + "_" + pagesize));
//    	PaginationSupport<OperationAdminWb> res = null;
//        /*if(StringUtils.isNotBlank(wyqCaseStr)){
//        	res = JSON.parseObject(wyqCaseStr, PaginationSupport.class);
//        }else{*/
//    	if(likeName==null){
//    		likeName="";
//    	}
//    	if(type==null){
//    		type=0;
//    	}
//    	List<OperationAdminWb> operationAdminWbs=userCenterBean.findHotEventByRecommend();
//    	if(CollectionUtils.isNotEmpty(operationAdminWbs)){
//    		for(OperationAdminWb oAdminWb : operationAdminWbs){
//    			oAdminWb.setArticleUrl(SysConfig.SYSTEM_WEB_URL + "share/hotSearch/hotEventPreview.shtml?hotEventPreview.id=" + oAdminWb.getId());
//    		}
//    		hashMap.put("recommendList", operationAdminWbs);
//    	}
//
//		res = userCenterBean.findHotEventByNameTypePage(page, pagesize,likeName,type);
//			/*	JedisUtil.setAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page + "_" + pagesize), JSON.toJSONString(res), 1800);
//        }*/
//    	maxpage = 0;
//		if(res != null){
//        	maxpage = res.getTotalCount()/15 + (res.getTotalCount()%15==0?0:1);
//
//			if(CollectionUtils.isNotEmpty(res.getItems())){
//				oAdminWbs = res.getItems();
//    			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
//    			if(oAdminWbs!=null && oAdminWbs.size()>0){
//    				for(OperationAdminWb oAdminWb : oAdminWbs){
//    					oAdminWb.setArticleUrl(SysConfig.SYSTEM_WEB_URL + "share/hotSearch/hotEventPreview.shtml?hotEventPreview.id=" + oAdminWb.getId());
//    				}
//    				hashMap.put("code", "0000");
//    				hashMap.put("data", oAdminWbs);
//    				hashMap.put("totalCount", maxpage);
//
//    			}
//			}
//		}
//		hashMap.put("code", "0000");
//		CommonUtils.writeJSONString(JSONObject.toJSONString(hashMap));
//
//    }
//	/**
//     * 大数据解读文章详情
//     *
//     * @return
//     * @throws Exception
//     */
//	public void wyqCaseDetailV2() throws Exception {
//        if(id == null){
//        	CommonUtils.writeJSON(null);
//        }else{
//
//        	String sessionName = Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + "detail_" + id;	//大数据解读（微舆情解读案例）
//
//        	String json = JedisUtil.getAttribute(Constants.generateJedisKey(sessionName));
//        	OperationAdminWb operationAdminWb = null;
//        	if(StringUtils.isBlank(json)){
//        		operationAdminWb = userCenterBean.findOperationAdminWbById(id);
//    			JedisUtil.setAttribute(Constants.generateJedisKey(sessionName), json, 3600);
//        	}else{
//        		operationAdminWb = JSONObject.parseObject(json, OperationAdminWb.class);
//        	}
//        	if(operationAdminWb != null){
//        		Integer readNum = operationAdminWb.getReadNumber();
//        		if(readNum == null){
//        			readNum = 1;
//        		}else{
//        			readNum++;
//        		}
//        		operationAdminWb.setReadNumber(readNum);
//        		userCenterBean.updateOperationAdminWbReadNum(operationAdminWb);
//        	}
//			json = JSONObject.toJSONString(operationAdminWb);
//
//        	HttpServletResponse httpServletRespons = ServletActionContext.getResponse();
//    		httpServletRespons.setHeader("Access-Control-Allow-Origin","*");	//允许跨域请求
//    		httpServletRespons.setHeader("Access-Control-Allow-Methods","GET,POST");
//    		httpServletRespons.setContentType("application/json;charset=GBK;");
//    		PrintWriter printWriter = null;
//        	try {
//    			printWriter = httpServletRespons.getWriter();
//    			printWriter.print(json);
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    		} finally {
//    			if (printWriter != null) {
//    				printWriter.flush();
//    				printWriter.close();
//    			}
//    		}
//        }
//    }
//	String likeName;
//
//	public String getLikeName() {
//		return likeName;
//	}
//
//	public void setLikeName(String likeName) {
//		this.likeName = likeName;
//	}
//
//	public void queryDataGotoListPage() throws Exception {
//		HashMap<String, Object> hashMap=new HashMap<String, Object>();
//        if(page == 0)
//    		page = 1;
//    	pagesize = 15;
//    	//String wyqCaseStr = JedisUtil.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page + "_" + pagesize));
//    	PaginationSupport<OperationAdminWb> res = null;
//        /*if(StringUtils.isNotBlank(wyqCaseStr)){
//        	res = JSON.parseObject(wyqCaseStr, PaginationSupport.class);
//        }else{*/
//    	if(likeName==null){
//    		likeName="";
//    	}
//    	if(type==null){
//    		type=0;
//    	}
//		res = userCenterBean.findBigDataByNameTypePage(page, pagesize,likeName,type);
//			/*	JedisUtil.setAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page + "_" + pagesize), JSON.toJSONString(res), 1800);
//        }*/
//    	maxpage = 0;
//		if(res != null){
//        	maxpage = res.getTotalCount()/15 + (res.getTotalCount()%15==0?0:1);
//
//			if(CollectionUtils.isNotEmpty(res.getItems())){
//				oAdminWbs = res.getItems();
//
//    			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
//    			if(oAdminWbs!=null && oAdminWbs.size()>0){
//    				for(OperationAdminWb oAdminWb : oAdminWbs){
//    					oAdminWb.setArticleUrl(SysConfig.SYSTEM_WEB_URL + "share/hotSearch/hotEventPreview.shtml?hotEventPreview.id=" + oAdminWb.getId());
//    				}
//    				hashMap.put("code", "0000");
//    				hashMap.put("data", oAdminWbs);
//    				hashMap.put("totalCount", maxpage);
//    				CommonUtils.writeJSONString(JSONObject.toJSONString(hashMap));
//    				return ;
//    			}else{
//    				hashMap.put("code", "9999");
//    				hashMap.put("message", "暂无数据");
//    				CommonUtils.writeJSONString(JSONObject.toJSONString(hashMap));
//    				return;
//    			}
//			}
//			else{
//				hashMap.put("code", "9999");
//				hashMap.put("message", "暂无数据");
//				CommonUtils.writeJSONString(JSONObject.toJSONString(hashMap));
//				return;
//			}
//		}
//		hashMap.put("code", "9999");
//		hashMap.put("message", "请求失败，请重试");
//		CommonUtils.writeJSONString(JSONObject.toJSONString(hashMap));
//
//    }
//
//	/**
//     * 大数据解读文章详情
//     *
//     * @return
//     * @throws Exception
//     */
//	public void wyqCaseDetail() throws Exception {
//        if(articleId == null){
//        	CommonUtils.writeJSON(null);
//        }else{
//
//        	String sessionName = Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + "detail1_" + articleId;	//大数据解读（微舆情解读案例）
//
//        	String json = JedisUtil.getAttribute(Constants.generateJedisKey(sessionName));
//
//        	AboutUsArticle article = null;
//        	if(StringUtils.isNotBlank(json)){
//        		article = JSON.parseObject(json, AboutUsArticle.class);
//        	}else{
//        		OperationAdminWb operationAdminWb = userCenterBean.findOperationAdminWbById(articleId);
//        		if(operationAdminWb != null){
//        			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
//        			article = new AboutUsArticle();
//        			article.setId(operationAdminWb.getId());
//        			article.setTitle(operationAdminWb.getTitle());
//        			article.setSummary(operationAdminWb.getAbstracts());
//        			article.setArticleUrl(operationAdminWb.getLink());
//        			article.setImgUrl(operationAdminWb.getImageUrl());
//        			article.setPublishTime(operationAdminWb.getCreateTime());
//        			article.setPublishTimeStr(dateFormat.format(operationAdminWb.getCreateTime()));
//        			article.setContent(operationAdminWb.getContent());
//        			article.setTopImage(operationAdminWb.getTopImage());
//        		}
//        	}
//        	HttpServletResponse httpServletRespons = ServletActionContext.getResponse();
//    		httpServletRespons.setHeader("Access-Control-Allow-Origin","*");	//允许跨域请求
//    		httpServletRespons.setHeader("Access-Control-Allow-Methods","GET,POST");
//    		httpServletRespons.setContentType("application/json;charset=GBK;");
//    		PrintWriter printWriter = null;
//    		try {
//    			printWriter = httpServletRespons.getWriter();
//    			printWriter.print(JSONObject.toJSON(article));
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    		} finally {
//    			if (printWriter != null) {
//    				printWriter.flush();
//    				printWriter.close();
//    			}
//    		}
//        }
//    }
//	List<OperationAdminWb> oAdminWbs;
//
//	public List<OperationAdminWb> getoAdminWbs() {
//		return oAdminWbs;
//	}
//
//	public void setoAdminWbs(List<OperationAdminWb> oAdminWbs) {
//		this.oAdminWbs = oAdminWbs;
//	}
//
//	public String infoData() throws Exception {
//		fetchSessionAdmin();
//        if(page == 0)
//    		page = 1;
//    	pagesize = 15;
//    	//String wyqCaseStr = JedisUtil.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page + "_" + pagesize));
//    	PaginationSupport<OperationAdminWb> res = null;
//        /*if(StringUtils.isNotBlank(wyqCaseStr)){
//        	res = JSON.parseObject(wyqCaseStr, PaginationSupport.class);
//        }else{*/
//			res = userCenterBean.findHotEventByPage(page, pagesize);
//			/*	JedisUtil.setAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page + "_" + pagesize), JSON.toJSONString(res), 1800);
//        }*/
//    	maxpage = 0;
//		if(res != null){
//        	maxpage = res.getTotalCount()/15 + (res.getTotalCount()%15==0?0:1);
//
//			if(CollectionUtils.isNotEmpty(res.getItems())){
//				oAdminWbs = res.getItems();
//    			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
//			}
//		}
//
////        fetchRightNumber();
////        page = 1;
////        aboutUsFlag = 0;
////
////        DataGotoPage();
//        return Action.SUCCESS;
//    }
//	//查询未登录下的大数据解读的信息
//	private PaginationSupport<OperationAdminWb> DataPage(){
//		if(page == 0)
//			page = 1;
//		pagesize = 15;
//		PaginationSupport<OperationAdminWb> res = null;
//		res = userCenterBean.findHotEventByPage(page, pagesize);
//		return res;
//	}
//	public void queryDataRead() throws Exception{
//		PaginationSupport<OperationAdminWb> result = DataPage();
//		CommonUtils.writeJSON(result);
//	}
//	//查询未登录下的大数据解读的信息end
//	public String queryDataGotoPage() throws Exception {
//        fetchSessionAdmin();
//        fetchRightNumber();
//        DataGotoPage();
//        return SUCCESS;
//    }
//
//	private void DataGotoPage(){
//    	if(page == 0)
//    		page = 1;
//    	pagesize = 15;
//    	//String wyqCaseStr = JedisUtil.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page + "_" + pagesize));
//    	PaginationSupport<OperationAdminWb> res = null;
//        /*if(StringUtils.isNotBlank(wyqCaseStr)){
//        	res = JSON.parseObject(wyqCaseStr, PaginationSupport.class);
//        }else{*/
//			res = userCenterBean.findHotEventByPage(page, pagesize);
//			/*	JedisUtil.setAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_WYQ_CASE_LIST2 + page + "_" + pagesize), JSON.toJSONString(res), 1800);
//        }*/
//    	wyqCaseList = new ArrayList<AboutUsArticle>();
//    	maxpage = 0;
//		if(res != null){
//        	maxpage = res.getTotalCount()/15 + (res.getTotalCount()%15==0?0:1);
//
//			if(CollectionUtils.isNotEmpty(res.getItems())){
//    			List<OperationAdminWb> operationAdminWbList = res.getItems();
//    			SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
//				for(OperationAdminWb operationAdminWb : operationAdminWbList){
//					AboutUsArticle aboutUsArticle = new AboutUsArticle();
//					aboutUsArticle.setId(operationAdminWb.getId());
//					aboutUsArticle.setTitle(operationAdminWb.getTitle());
//					aboutUsArticle.setSummary(operationAdminWb.getAbstracts());
//					aboutUsArticle.setTopImage(operationAdminWb.getTopImage());
//					aboutUsArticle.setArticleUrl(SysConfig.SYSTEM_WEB_URL + "share/hotSearch/hotEventPreview.shtml?hotEventPreview.id=" + operationAdminWb.getId());
//					aboutUsArticle.setImgUrl(operationAdminWb.getImageUrl());
//					aboutUsArticle.setPublishTime(operationAdminWb.getCreateTime());
//					aboutUsArticle.setPublishTimeStr(dateFormat.format(operationAdminWb.getCreateTime()));
//					wyqCaseList.add(aboutUsArticle);
//				}
//			}
//		}
//    }
//
//	public String queryMediaArticleList() throws Exception {
//        fetchSessionAdmin();
//        fetchRightNumber();
//        mediaArticleGotoPage();
//        return SUCCESS;
//    }
//
//	private void mediaArticleGotoPage(){
//    	if(page == 0)
//    		page = 1;
//    	String mediaArticleStr = JedisUtil.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_MEDIA_ARTICLE_LIST + "11"));
//        if(StringUtils.isNotBlank(mediaArticleStr)){
//        	mediaArticleList = JSON.parseArray(mediaArticleStr, AboutUsArticle.class);
//        }else{
//        	mediaArticleList = aboutUsArticleBean.getAboutUsArticleList(Constants.ABOUT_US_MEDIA_ARTICLE, null, null);
//        	if(CollectionUtils.isNotEmpty(mediaArticleList)){
//        		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_SHORT_TIME);
//        		for(AboutUsArticle a : mediaArticleList){
//        			if(a.getPublishTime() != null)
//        				a.setPublishTimeStr(dateFormat.format(a.getPublishTime()));
//        		}
//        	}
//        	JedisUtil.setAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_MEDIA_ARTICLE_LIST + "11"), JSON.toJSONString(mediaArticleList), 1800);
//        }
//
//        int startPage = (page-1)*15;
//        int endPage = page*15;
//        if(mediaArticleList != null){
//        	int size = mediaArticleList.size();
//        	maxpage = size/15 + (size%15==0?0:1);
//        	if(size > 0){
//	        	if(size < startPage)
//	        		startPage = size;
//	        	if(size < endPage)
//	        		endPage = size;
//	        	mediaArticleList = mediaArticleList.subList(startPage, endPage);
//        	}
//        }
//
//    }
//
//	/**
//	 * 激活邮件地址
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public String activeMailAddress() throws Exception {
//		long currentTime = new Date().getTime();
//		VerifiedCode vc = warningBean.findVerifiedCode(code);
//		if (vc == null || !code.equals(vc.getVerifiedCode())) {
//			// 验证信息不存在
//			isValidate = 2;
//			return Action.INPUT;
//		} else {
//			int userId = vc.getUserId(); // 获取用户ID
//			long time = vc.getCreateTime().getTime();
//			String mail = vc.getContactInfo();
//			UserContact uc = warningBean.findUserContact(mail, userId, 1); // 获取用户联系列表
//			if(uc == null) {
//				isValidate = 2;
//				return Action.INPUT;
//			}
//
//			// 已经验证成功的邮件
//			if (uc.getIsVerified() == 1) {
//				isValidate = 4;
//				return Action.SUCCESS;
//			}
//
//			// 48小时有效验证时间
//			if (currentTime - time >= 48 * 60 * 60 * 1000) {
//				isValidate = 3;
//				return Action.INPUT;
//			}
//
//			if (isValidate == 1) {
//				warningBean.updateMailActive(mail, userId, 1);
//			}
//			// 验证后会删除该条记录
//			warningBean.deleteVerifiedCode(mail, 1, code);
//		}
//		return Action.SUCCESS;
//	}
//
//	@Override
//	public String execute() throws Exception {
//		// index.action
//		fetchSessionAdmin();
//
//		if (admin != null) {
//			System.out.println("wap_url=" + wap_url);
//			// UserSearchSet userSearchSet = keywordBean.getUserSearchSet(admin
//			// .getAccount());
//			/*
//			 * if (userSearchSet != null) {
//			 * ActionContext.getContext().getSession().put(
//			 * UserAdapter.SEARCH_DEFAULT_TIME, userSearchSet.getTimeDomain());
//			 * } else { ActionContext.getContext().getSession().put(
//			 * UserAdapter.SEARCH_DEFAULT_TIME,
//			 * authenticationBean.getHttpInterfaceHandler()
//			 * .getSearchDefaultLen()); }
//			 */
//			return Action.SUCCESS;
//
//		}
//
//		return Action.INPUT;
//
//	}
//
//	public String gonggao() throws Exception {
//
//		fetchSessionAdmin();
//		if (admin != null) {
//
//			return Action.SUCCESS;
//		}
//
//		return Action.INPUT;
//
//	}
//
//	public String logoutFrm() throws Exception {
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/html");
//		try {
//
//			HttpServletRequest req = ServletActionContext.getRequest();
//			HttpServletResponse rep = ServletActionContext.getResponse();
//			logger.info(SimpleUtils.appendFlag("logoutFrm cookie ============ " + req.getCookies().length));
//			logger.info(SimpleUtils.appendFlag(" logoutFrm HttpServletResponse  ============ " + rep));
//			LoginUtils.clearCookieSid(req, rep);
//
//			// ActionContext.getContext().getSession().remove(
//			// UserAdapter.SYS_CACHE_SESSION_NAME);
//			// ActionContext.getContext().getSession().remove(
//			// UserAdapter.SYS_SESSION_NAME);
//			/*
//			 * ActionContext.getContext().getSession().remove(
//			 * UserAdapter.FILTER_WEBSITE_TYPE);
//			 */
//			response.getWriter().print("0");
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//		return null;
//	}

	/**
	 * 检测用户是否已经登录
	 *
	 * @return
	 */
	public String isLogin() {
		log.info("is Login");
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		response.setContentType("text/html");
		try {

			UserDto admin = fetchSessionAdmin();

			log.info(SimpleUtils.appendFlag("getAdmin cache"));
			if (admin == null) {

				log.info(SimpleUtils.appendFlag("getAdmin cache null 22"));
				response.getWriter().print("0");
			} else {
				response.getWriter().print("1");
				log.info(SimpleUtils.appendFlag("getAdmin SYS_CACHE_SESSION_NAME success ; accout=" + admin.getUserId()));
			}

		} catch (Exception e) {
			try {
				response.getWriter().print("0");
			} catch (Exception e2) {

			}

		}
		return null;
	}

	/**
	 * 检测用户是否已经登录
	 *
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "ajaxIsLogin")
	@ResponseBody
	public void ajaxIsLogin() throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		response.setContentType("text/html");
		UserDto admin = fetchSessionAdmin();

		if (admin == null){
			response.getWriter().print("0");
		}else{
			response.getWriter().print("1");
		}
	}

	/**
	 * 登陆处理
	 *
	 * @return
	 * @throws Exception
	 */
//	public String indexLogin() throws Exception {
//		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = servletRequestAttributes.getRequest();
//		HttpServletResponse response = servletRequestAttributes.getResponse();
//
//		if (StringUtils.isBlank(imgVcode)) {
//			msg = "请输入您的验证码！";
//			return "index";
//		}
//		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
//			msg = "请输入您的账号和密码！";
//			return "index";
//		}
//
//		String vcodeKey = Constants.generateJedisKey(new StringBuilder(Constants.JEDIS_KEYS_LOGIN_VCODE).append("lg_").append(_ran).toString());
//		String vcodeValue = JedisUtil.getAttribute(vcodeKey);
//		System.out.println("validation jsp c = [lg_" + _ran + "] key = [" + vcodeKey + "] value = [" + vcodeValue + "] userInputValue = [" + imgVcode + "]");
//
//		if (StringUtils.isBlank(vcodeValue) || !imgVcode.trim().equalsIgnoreCase(vcodeValue.trim())) {
//			msg = "验证码不正确！";
//			return "index";
//		}
//		JedisUtil.removeAttribute(vcodeKey);
//
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("username", username);
//		params.put("password", MD5Util.md5(password));
//
//		String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userLoginMobileURL"), params);
//		logger.info("IdentificationAction.indexLogin : rtnStr = [" + rtnStr + "]");
//
//		if (StringUtils.isBlank(rtnStr)) {
//			msg = "登录失败，请稍候再试！";
//			return "index";
//		}
//
//		UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//		if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())) {
//			msg = userRes == null ? "登录失败，请稍候再试！" : userRes.getMessage();
//			return "index";
//		}
//
//		String loginRes = LoginUtils.doLogin(request, response, userRes, true);
//		if(!"0000".equals(loginRes)){
//			return "index";
//		}
//
//		//登录操作
//		firstLoginOperate(request, userRes.getUserInfo());
//
//		return Action.SUCCESS;
//	}


	/**
	 * ajax登陆处理
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "userLogin")
	@ResponseBody
	BaseDto userLogin(String imgVcode, String username, String password, String _ran, String firstLoginSign) throws Exception {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		HttpServletResponse response = servletRequestAttributes.getResponse();
		BaseDto baseDto = new BaseDto();
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isBlank(imgVcode)) {
//			String msg = "请输入您的验证码！";
//			result.put("code", "1111");
//			result.put("msg", msg);
			return baseDto.setCode(CodeConstant.ERROR_CODE_1001).setMessage("请输入您的验证码！");
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
		}
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
//			msg = "请输入您的账号和密码！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
			return baseDto.setCode(CodeConstant.ERROR_CODE_1001).setMessage("请输入您的账号和密码！");
		}

		String vcodeKey = RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_LOGIN_VCODE).append("lg_").append(_ran).toString());//_loginVcode_lg_3822872
//		String vcodeValue = redisTemplate.opsForValue().get(vcodeKey);
		String vcodeValue = RedisUtils.getAttribute(vcodeKey);
		System.out.println("validation jsp c = [lg_" + _ran + "] key = [" + vcodeKey + "] value = [" + vcodeValue + "] userInputValue = [" + imgVcode + "]");
		log.info("validation jsp c = [lg_" + _ran + "] key = [" + vcodeKey + "] value = [" + vcodeValue + "] userInputValue = [" + imgVcode + "]");
		if (StringUtils.isBlank(vcodeValue) || !imgVcode.trim().equalsIgnoreCase(vcodeValue.trim())) {
//			msg = "验证码不正确！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
			return baseDto.setCode(CodeConstant.ERROR_CODE_1001).setMessage("验证码不正确！");
		}
//		redisTemplate.delete(vcodeKey);
		RedisUtils.removeAttribute(vcodeKey);
//		UserDto userRes = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("password", MD5Utils.MD5Encode(password));
		params.put("platform", BusinessConstant.PLATFORM_Web);
		String userLoginMobileURL = IntraBusinessAPIConfig.getValue("userLoginMobile");
		String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userLoginMobile"), params);
		log.info("IdentificationAction.indexLogin : rtnStr = [" + rtnStr + "]");

		if (StringUtils.isBlank(rtnStr)) {
//			msg = "登录失败，请稍候再试！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
			return baseDto.setCode(CodeConstant.ERROR_CODE_1001).setMessage("登录失败，请稍候再试！");
		}
		JSONObject jsonObject = JSONObject.parseObject(rtnStr);
		String userInfo = jsonObject.getString("userInfo");
		UserDto userDto  = JSONObject.parseObject(userInfo, UserDto.class);
		userDto.setSid(jsonObject.getString("sid"));
//		userRes = JSONObject.parseObject(rtnStr, UserDto.class);
//		UserDto userDto = baseDto1.getData();
		String code = jsonObject.getString("code");
		String message = jsonObject.getString("message");
		if (jsonObject == null || !CodeConstant.SUCCESS_CODE.equals(code)) {
			String msg = jsonObject == null ? "登录失败，请稍候再试！" : message;
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
			return baseDto.setCode(CodeConstant.ERROR_CODE_1001).setMessage(msg);
		}
		String loginRes = LoginUtils.doLogin(request, response, userDto, true);
		if(!"0000".equals(loginRes)){
//			result.put("code", "1111");
//			result.put("msg", loginRes);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
			return baseDto.setCode(CodeConstant.ERROR_CODE_1001).setMessage(loginRes);
		}

		//登录操作
		firstLoginSign = firstLoginOperate(request, userDto);
		result.put("firstLoginSign", firstLoginSign);
//		result.put("selectLoginType", selectLoginType);
//		CommonUtils.writeJSONString(JSONObject.toJSONString(result));
		return baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(result);
	}
//	public void caseBaseLogin() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
//			msg = "请输入您的账号和密码！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		UserRes userRes = null;
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("username", username);
//		params.put("password", MD5Util.md5(password));
//
//		String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userLoginMobileURL"), params);
//		logger.info("IdentificationAction.indexLogin : rtnStr = [" + rtnStr + "]");
//
//		if (StringUtils.isBlank(rtnStr)) {
//			msg = "登录失败，请稍候再试！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//		if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())) {
//			msg = userRes == null ? "登录失败，请稍候再试！" : userRes.getMessage();
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//		String loginRes = LoginUtils.doLogin(request, response, userRes, true);
//		if(!"0000".equals(loginRes)){
//			result.put("code", "1111");
//			result.put("msg", loginRes);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		result.put("code", "0000");
//		//登录操作
////		firstLoginOperate(request, userRes.getUserInfo());
////		result.put("firstLoginSign", firstLoginSign);
////		result.put("selectLoginType", selectLoginType);
//		CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//	}
	/**
	 * 活动页ajax登陆处理
	 *
	 * @return
	 * @throws Exception
	 */
//	public void activeUserLogin() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
//			msg = "请输入您的账号和密码！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		codeCheck();
//
//		UserRes userRes = null;
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("username", username);
//		params.put("password", MD5Util.md5(password));
//
//		String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userLoginMobileURL"), params);
//		logger.info("IdentificationAction.indexLogin : rtnStr = [" + rtnStr + "]");
//
//		if (StringUtils.isBlank(rtnStr)) {
//			msg = "登录失败，请稍候再试！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//		if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())) {
//			msg = userRes == null ? "登录失败，请稍候再试！" : userRes.getMessage();
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//		String loginRes = LoginUtils.doLogin(request, response, userRes, true);
//		if(!"0000".equals(loginRes)){
//			result.put("code", "1111");
//			result.put("msg", loginRes);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		result.put("code", "0000");
//		//登录操作
//		firstLoginOperate(request, userRes.getUserInfo());
//		CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//	}
//
//	public void codeCheck(){
//		Map<String, Object> result = new HashMap<String, Object>();
//		String msg = "";
//		if (StringUtils.isBlank(imgVcode) ) {
//			msg = "请输入验证码！";
//			result.put("code", "2222");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		String vcodeKey = Constants.generateJedisKey(new StringBuilder(Constants.JEDIS_KEYS_LOGIN_VCODE).append("lg_").append(_ran).toString());
//		String vcodeValue = JedisUtil.getAttribute(vcodeKey);
//
//		if (StringUtils.isBlank(vcodeValue) || !imgVcode.trim().equalsIgnoreCase(vcodeValue.trim())) {
//			msg = "验证码不正确！";
//			result.put("code", "3333");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//		JedisUtil.removeAttribute(vcodeKey);
//	}
//
//	/**
//	 * 手机登陆登陆处理
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public void indexLogin2() throws Exception {
//		Map<String, Object> result = new HashMap<String, Object>();
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//
//		selectLoginType = 1;
//
//		String vcodeKey = Constants.generateJedisKey(new StringBuilder(Constants.JEDIS_KEYS_LOGIN_VCODE).append("lg_").append(_ran).toString());
//		String vcodeValue = JedisUtil.getAttribute(vcodeKey);
//		System.out.println("validation jsp c = [lg_" + _ran + "] key = [" + vcodeKey + "] value = [" + vcodeValue + "] userInputValue = [" + imgVcode + "]");
//
//		if (StringUtils.isBlank(vcodeValue) || !imgVcode.trim().equalsIgnoreCase(vcodeValue.trim())) {
//			msg = "验证码不正确！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//		JedisUtil.removeAttribute(vcodeKey);
//
//		if (StringUtils.isBlank(imgVcode2)) {
//			msg = "请输入您的短信验证码！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//		if (StringUtils.isBlank(mobile) ) {
//			msg = "请输入您的手机号！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		Map<String, Object> params2 = new HashMap<String, Object>();
//		params2.put("mobile", mobile);
//		params2.put("authcode", imgVcode2);
//		String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(ServletActionContext.getRequest(), IntraBusinessAPIConfig.getValue("checkSMSURL"), params2);
//		if (StringUtils.isBlank(rtnStr)) {
//			msg = "短信验证码不正确，请重新输入!";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		} else {
//			JSONObject jsonObject = JSONObject.parseObject(rtnStr);
//			if (jsonObject == null){
//				msg = "短信验证码不正确，请重新输入!";
//				result.put("code", "1111");
//				result.put("msg", msg);
//				CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//				return;
//			}else if (!Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(jsonObject.getString("code"))){
//				msg = jsonObject.getString("message");
//				result.put("code", "1111");
//				result.put("msg", msg);
//				CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//				return;
//			}else if (!jsonObject.getBooleanValue("succ")){
//				msg = "短信验证码不正确，请重新输入!";
//				result.put("code", "1111");
//				result.put("msg", msg);
//				CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//				return;
//			}
//		}
//
//		//根据手机号查询user表
//		User user = userBean.findUserByMobile(mobile);
//		if(null!=user){
//			//绑定过此手机号的账号
//			password = user.getPassword();
//			username = user.getUsername();
//		}else{
//			//没有注册过的手机号，先注册
//			fetchProductlist(Constants.PACKAGE_TYPE_FREE);
//
//			UserCreateVO userCreateVO = new UserCreateVO();
//			userCreateVO.setUsername(mobile);
//			userCreateVO.setPassword(MD5Util.md5(MD5Util.md5(mobile.substring(mobile.length()-6,mobile.length()))));
//			userCreateVO.setAppUserStatus(0);
//			userCreateVO.setUserChannel(Constants.USER_CHANNEL_WYQ);
//			userCreateVO.setGiftPackageId(packageListFree.get(0).getProductPackageId());
//			userCreateVO.setLogin(false);
//
//			String userExclusiveChannelCode = null;
//			if (request.getCookies() != null && request.getCookies().length > 0) {
//				for (Cookie cookie : request.getCookies()) {
//					if ("exclusive_channel_code".equals(cookie.getName())) {
//						String code = cookie.getValue();
//						if (StringUtils.isNoneBlank(code))
//							userExclusiveChannelCode = code;
//
//						break;
//					}
//				}
//			}
//			if (StringUtils.isNotBlank(userExclusiveChannelCode))
//				userCreateVO.setExclusiveChannelCode(userExclusiveChannelCode);
//			params2 = new HashMap<String, Object>();
//			params2.put("userParam", JSONObject.toJSONString(userCreateVO));
//
//
//			String rtnStr2 = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userCreateMobileURL"), params2);
//			logger.info("indexLogin2 create user rtnStr2 = [" + rtnStr2 + "]");
//			if (StringUtils.isBlank(rtnStr2)){
//				msg = "系统繁忙，请稍候再试！";
//				result.put("code", "1111");
//				result.put("msg", msg);
//				CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//				return;
//			}
//
//			UserRes userRes = JSONObject.parseObject(rtnStr2, UserRes.class);
//			if (userRes == null){
//				msg = "系统繁忙，请稍候再试！";
//				result.put("code", "1111");
//				result.put("msg", msg);
//				CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//				return;
//			}else if(!Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())){
//				msg = userRes.getMessage();
//				result.put("code", "1111");
//				result.put("msg", msg);
//				CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//				return;
//			}else{
//				username = userRes.getUserInfo().getUsername();
//				password = userRes.getUserInfo().getPassword();
//			}
//		}
//
//
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("username", username);
//		params.put("password", password);
//
//		String rtnStr3 = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userLoginMobileURL"), params);
//		logger.info("IdentificationAction.indexLogin : rtnStr = [" + rtnStr3 + "]");
//
//		if (StringUtils.isBlank(rtnStr3)) {
//			msg = "登录失败，请稍候再试！";
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		UserRes userRes = JSONObject.parseObject(rtnStr3, UserRes.class);
//		if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())) {
//			msg = userRes == null ? "登录失败，请稍候再试！" : userRes.getMessage();
//			result.put("code", "1111");
//			result.put("msg", msg);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		String loginRes = LoginUtils.doLogin(request, response, userRes, true);
//		if(!"0000".equals(loginRes)){
//			result.put("code", "1111");
//			result.put("msg", loginRes);
//			CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//			return;
//		}
//
//		result.put("code", "0000");
//
//		//登录后操作
//		firstLoginOperate(request, userRes.getUserInfo());
//		result.put("firstLoginSign", firstLoginSign);
////		result.put("selectLoginType", selectLoginType);
//		CommonUtils.writeJSONString(JSONObject.toJSONString(result));
//
//	}
//
//	/**
//	 * 登陆处理
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public String login() throws Exception {
//		return indexLogin();
//	}
//	/**
//	 * 微运营
//	 *
//	 * @return
//	 */
//	public String goMicroOperations() {
//		return "goMicroOperations";
//	}
//
//	/**
//	 * ajax登录
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("rawtypes")
//	public String ajaxLogin() throws Exception {
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/html");
//		response.setCharacterEncoding("GBK");
//		try {
//			logger.info(SimpleUtils.appendFlag("------------ajaxLogin start111 ---------"));
//			nvlogin();
//			logger.info(SimpleUtils.appendFlag("------------ajaxLogin end 111---------"));
//			Iterator iterator = getActionErrors().iterator();
//			if (iterator.hasNext()) {
//				response.getWriter().print((String) iterator.next());
//			} else {
//				try {
//					indexLogin();
//					response.getWriter().print("登录成功");
//				} catch (Exception e) {
//					response.getWriter().print("登录失败");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info(SimpleUtils.appendFlag("------------ajaxLogin end 222---------"));
//		return null;
//	}
//
//	/**
//	 * 不验证验证码
//	 */
//	public void nvlogin() {
//
//	}
//
//	/**
//	 * 验证登陆
//	 *
//	 */
//	public boolean validateLogins() {
//		ServletActionContext.getResponse().setHeader("P3P", "CP=CAO PSA OUR");
//
//		if (username == null || "".equals(username)) {
//			addActionError("请输入您的帐号！");
//			msg = "请输入您的手机号";
//			return false;
//		}
//		if (password == null || "".equals(password.trim())) {
//			addActionError("请输入您的密码！");
//			msg = "请输入您的密码！";
//			return false;
//		}
//
//		// if (imgVcode == null || "".equals(imgVcode.trim())) {
//		// addActionError("请输入您的验证码！");
//		// msg = "请输入您的验证码！";
//		// return false;
//		// }
//
//		// 验证当日登录输错密码次数
//		int userLoginValidCount = 0;
//		int validCount = Integer.parseInt(SysConfig.USER_LOGIN_VALID_COUNT);
//		String userLoginValid = JedisUtil.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_USER_LOGIN_VALID_COUNT + username));
//		if (StringUtils.isNotBlank(userLoginValid))
//			userLoginValidCount = Integer.parseInt(userLoginValid);
//
//		if (userLoginValidCount >= validCount) {
//			msg = "密码输入次数已超过限制，请明天再试！";
//			return false;
//		}
//
//		password = MD5Util.md5(password);
//		User admin = authenticationBean.getAdmin(username);
//		if (admin != null) {
//			if (!admin.getPassword().equals(password)) {
//				if (userLoginValidCount < (validCount - 1))
//					msg = "用户名或密码错误！还可以再试 " + (validCount - userLoginValidCount - 1) + " 次！";
//				else
//					msg = "用户名或密码错误！密码输入次数已超过限制，请明天再试！";
//
//				// 获取距当天零点的时间差
//				Calendar calendar = Calendar.getInstance();
//				calendar.add(Calendar.DAY_OF_MONTH, 1);
//				calendar.set(Calendar.HOUR_OF_DAY, 0);
//				calendar.set(Calendar.MINUTE, 0);
//				calendar.set(Calendar.SECOND, 0);
//				calendar.set(Calendar.MILLISECOND, 0);
//				int timeDifference = (int) ((calendar.getTime().getTime() - (new Date().getTime()))/1000);
//				JedisUtil.setAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_USER_LOGIN_VALID_COUNT + username), String.valueOf((userLoginValidCount + 1)), timeDifference);
//				return false;
//			}
//		}
//
//		// String vcodepre_login = "";
//		// Object obj = SessionUtil.getAttribute(SysConfig.memoCacheName,
//		// "wyq_lg_" + _ran);
//		// if (obj != null) {
//		// vcodepre_login = obj.toString();
//		// }
//		//
//		// if
//		// (!vcodepre_login.trim().toLowerCase().equals(imgVcode.trim().toLowerCase()))
//		// {
//		// addActionError("验证码不正确！");
//		// msg = "验证码不正确！";
//		// return false;
//		// }
//
//		// if (admin != null && admin.getStatus() == 0) {
//		// Calendar nowDate = Calendar.getInstance();
//		// Calendar createDate = Calendar.getInstance();
//		// nowDate.setTime(new Date());// 设置为当前系统时间
//		// createDate.setTime(admin.getCreateTime());
//		// long nowTime = nowDate.getTimeInMillis();
//		// long createTime = createDate.getTimeInMillis();
//		// float day = (nowTime - createTime) / (1000 * 60 * 60 * 24);// 化为天
//		// System.out.println("相隔" + day + "天");
//		// if (day >= 2) {
//		// admin = null;
//		// }
//		// }
//		if (admin == null) {
//			addActionError("账号“" + username + "”不存在！");
//			msg = "用户名不存在！";
//			return false;
//		}
//		if (admin != null && admin.getStatus() != 1) {
//			admin = null;
//			addActionError("账号“" + username + "”已停用！");
//			msg = "该用户已被停用，请联系管理员！";
//			return false;
//		}
//		String inputPassword = password.trim();
//
//		if (admin.getPassword().length() > 30) {// 用户的密码为加密密码，需加密对比
//			inputPassword = authenticationBean.encodePassword(password);
//		}
//		if (!admin.getPassword().equals(inputPassword)) {
//
//			// addActionError("账号密码" + inputPassword );
//
//		}
//		if (admin.getStatus() == 2) {
//			addActionError("账号“" + username + "”已被删除！");
//		}
//
//		if (!getActionErrors().isEmpty()) {
//
//			return false;
//		}
//		if (admin != null) {
//
//		}
//		if (getActionErrors().isEmpty()) {
//			// authenticationBean.updateUserLoginTime(admin);// 记录登陆时间
//			this.admin = admin;
//		}
//
//		JedisUtil.removeAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_USER_LOGIN_VALID_COUNT + username));
//
//		// 是否需要验证码判断
//		return true;
//	}
//
	/**
	 * 模拟登出页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "logout")
	@ResponseBody
	public String logout() throws Exception {
		UserDto admin = fetchSessionAdmin();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = servletRequestAttributes.getRequest();
		HttpServletResponse rep = servletRequestAttributes.getResponse();
		if (admin != null) {
			clearShowMenu(admin);

			String username = admin.getUsername();

//			HttpServletRequest req = ServletActionContext.getRequest();
//			HttpServletResponse rep =ServletActionContext.getResponse();
			String sid = LoginUtils.getSidFromCookie(req);
			if(StringUtils.isNotBlank(sid)){
				LoginUtils.doLogout(req, rep, sid);
			}
		}
		admin = null;
		String url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
//		rep.sendRedirect(url+"hot/events/goHotHome?firstLoginSign=0");
		return	"redirect:http://localhost:1188/hot/events/goHotHome";
//		return;
//
//
//		return Action.SUCCESS;
	}
//
//	/**
//	 * 被踢下线
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public String kickLogout() throws Exception {
//		fetchSessionAdmin();
//		if (admin != null) {
//			kickLogout = true;
//			LoginLog loginLog = userCenterBean.findLatestLoginLogByUserId(admin.getUserId());
//			if (loginLog != null)
//				kickTime = new SimpleDateFormat("HH:mm").format(loginLog.getLoginTime());
//		}
//		hotEvents = userCenterBean.findHotEvent();
//		if (CollectionUtils.isNotEmpty(hotEvents) && hotEvents.size() > 5) {
//			hotEvents = hotEvents.subList(0, 5);
//		}
//		return logout();
//	}
//
//	public String requestFrequentlyInterceptor() throws Exception {
//		return SUCCESS;
//	}


//	/**
//	 * @return the vcode
//	 */
//	public String getVcode() {
//		return vcode;
//	}
//
//	/**
//	 * @param vcode
//	 *            the vcode to set
//	 */
//	public void setVcode(String vcode) {
//		this.vcode = vcode;
//	}
//
//	/**
//	 * @return the menuToken
//	 */
//	public String getMToken() {
//		return mToken;
//	}
//
//	/**
//	 * @param
//	 *
//	 */
//	public void setMToken(String mToken) {
//		this.mToken = mToken;
//	}
//
//	public String getIsRemengConfigSite() {
//		return isRemengConfigSite;
//	}
//
//	public void setIsRemengConfigSite(String isRemengConfigSite) {
//		this.isRemengConfigSite = isRemengConfigSite;
//	}
//
//	public String getUuid() {
//		return uuid;
//	}
//
//	public void setUuid(String uuid) {
//		this.uuid = uuid;
//	}
//
//	public String getNeiyeLogo() {
//		return neiyeLogo;
//	}
//
//	public void setNeiyeLogo(String neiyeLogo) {
//		this.neiyeLogo = neiyeLogo;
//	}
//
//	// public static Logger getLogger() {
//	// return logger;
//	// }
//
//	public static Log getLogger() {
//		return logger;
//	}
//
//	public String getLogoUrl() {
//		return logoUrl;
//	}
//
//	public void setLogoUrl(String logoUrl) {
//		this.logoUrl = logoUrl;
//	}
//
//	public String getDnsid() {
//		return dnsid;
//	}
//
//	public void setDnsid(String dnsid) {
//		this.dnsid = dnsid;
//	}
//
//	public int getNeedQQService() {
//		return needQQService;
//	}
//
//	public void setNeedQQService(int needQQService) {
//		this.needQQService = needQQService;
//	}
//
//	public int getIsJiankongManager() {
//		return isJiankongManager;
//	}
//
//	public void setIsJiankongManager(int isJiankongManager) {
//		this.isJiankongManager = isJiankongManager;
//	}
//
//	public String get_ran() {
//		return _ran;
//	}
//
//	public void set_ran(String _ran) {
//		this._ran = _ran;
//	}
//
//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public String getApkHref() {
//		return apkHref;
//	}
//
//	public void setApkHref(String apkHref) {
//		this.apkHref = apkHref;
//	}
//
//	public String getIosHref() {
//		return iosHref;
//	}
//
//	public void setIosHref(String iosHref) {
//		this.iosHref = iosHref;
//	}
//
//	public String getQrCodeImg() {
//		return qrCodeImg;
//	}
//
//	public void setQrCodeImg(String qrCodeImg) {
//		this.qrCodeImg = qrCodeImg;
//	}
//
//	public SystemConfig getSystemConfig() {
//		return systemConfig;
//	}
//
//	public void setSystemConfig(SystemConfig systemConfig) {
//		this.systemConfig = systemConfig;
//	}
//
//	public boolean isKickLogout() {
//		return kickLogout;
//	}
//
//	public void setKickLogout(boolean kickLogout) {
//		this.kickLogout = kickLogout;
//	}
//
//	public String getKickTime() {
//		return kickTime;
//	}
//
//	public void setKickTime(String kickTime) {
//		this.kickTime = kickTime;
//	}
//
//	public UserBean getUserBean() {
//		return userBean;
//	}
//
//	public void setUserBean(UserBean userBean) {
//		this.userBean = userBean;
//	}
//
//	public AuthenticationBean getAuthenticationBean() {
//		return authenticationBean;
//	}
//
//	public List<OperationAdminWb> getHotEvents() {
//		return hotEvents;
//	}
//
//	public void setHotEvents(List<OperationAdminWb> hotEvents) {
//		this.hotEvents = hotEvents;
//	}
//
//	public String getCategoryStr() {
//		return categoryStr;
//	}
//
//	public void setCategoryStr(String categoryStr) {
//		this.categoryStr = categoryStr;
//	}
//
//	public String getSmallClassStr() {
//		return smallClassStr;
//	}
//
//	public void setSmallClassStr(String smallClassStr) {
//		this.smallClassStr = smallClassStr;
//	}
//
//	public String getMiddleClassStr() {
//		return middleClassStr;
//	}
//
//	public void setMiddleClassStr(String middleClassStr) {
//		this.middleClassStr = middleClassStr;
//	}
//
//	public String getParamValueStr() {
//		return paramValueStr;
//	}
//
//	public void setParamValueStr(String paramValueStr) {
//		this.paramValueStr = paramValueStr;
//	}
//
//	public List<AboutUsArticle> getMediaArticleList() {
//		return mediaArticleList;
//	}
//
//	public void setMediaArticleList(List<AboutUsArticle> mediaArticleList) {
//		this.mediaArticleList = mediaArticleList;
//	}
//
//	public List<AboutUsArticle> getWyqCaseList() {
//		return wyqCaseList;
//	}
//
//	public void setWyqCaseList(List<AboutUsArticle> wyqCaseList) {
//		this.wyqCaseList = wyqCaseList;
//	}
//
//	public int getAboutUsFlag() {
//		return aboutUsFlag;
//	}
//
//	public void setAboutUsFlag(int aboutUsFlag) {
//		this.aboutUsFlag = aboutUsFlag;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public Integer getCategory() {
//		return category;
//	}
//
//	public void setCategory(Integer category) {
//		this.category = category;
//	}
//
//	public Integer getLabel() {
//		return label;
//	}
//
//	public void setLabel(Integer label) {
//		this.label = label;
//	}
//
//	public Integer getType() {
//		return type;
//	}
//
//	public void setType(Integer type) {
//		this.type = type;
//	}
//
//	public String getDateFormat() {
//		return dateFormat;
//	}
//
//	public void setDateFormat(String dateFormat) {
//		this.dateFormat = dateFormat;
//	}
//
//	public Integer getArticleId() {
//		return articleId;
//	}
//
//	public void setArticleId(Integer articleId) {
//		this.articleId = articleId;
//	}
//
//	public String getPlatform() {
//		return platform;
//	}
//
//	public void setPlatform(String platform) {
//		this.platform = platform;
//	}
//
//	public Integer getEventLabel() {
//		return eventLabel;
//	}
//
//	public void setEventLabel(Integer eventLabel) {
//		this.eventLabel = eventLabel;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

}
