package com.miduchina.wrd.webthermalquery.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.eventanalysis.PackageListView;
import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.hotsportview.ActivitySendRecordsView;
import com.miduchina.wrd.dto.user.IContentCommonNetView;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.hotspot.ActivitySendRecord;
import com.miduchina.wrd.po.hotspot.GiftCard;
import com.miduchina.wrd.po.ranking.Notice;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.webthermalquery.fegin.UserFeignClient;
import com.miduchina.wrd.webthermalquery.util.LoginUtils;
import com.xd.tools.common.ErrorCodeConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ���е�Action ��̳д�Action����Ҫ���ڹ��������������ü�����������ó��󣬼򻯴���
 *
 * @author James
 *
 */
//@Controller
	@Slf4j
public abstract class MSBaseController extends GBaseController {
//	private static final long serialVersionUID = 9177757921156121922L;
//
//	/**
//	 * org.apache.log4j.Logger����log
//	 */
//	private static final Logger logger = Logger.getLogger(MSBaseController.class);
//
//	/**
//	 * �����˻�-session��ȡ
//	 */
@Autowired
private UserFeignClient userFeignClient;
//@Autowired
//@Qualifier("stringRedisTemplate")
//private StringRedisTemplate redisTemplate;
//@Resource(name = "jsonRedisTemplate")
//private RedisTemplate redisTemplate1;
//
//	/**
//	 * 0.�޲��� 1.ϵͳ���� 2.ÿ�յ�һ�ε�¼����ǩ�� 3.ע���״ε�½����� 4.�·���������
//	 */
//	protected int firstLoginSign;//�û���¼���
//
//	@Autowired
//	private Orders authenticationBean;
//	@Autowired
//	private OrderRecordBean orderRecordBean;
//	@Autowired
//	private SubscriptionCenterBean subscriptionCenterBean;
//
//	private List<Notice> noticeList;
//	@Autowired
//	private WarningBean warningBean;
//	@Autowired
//	private UserCenterBean userCenterBean;
//	@Autowired
//	private NewsBean newsBean;
//
//	public List<Notice> getNoticeList() {
//		return noticeList;
//	}
//
//	public void setNoticeList(List<Notice> noticeList) {
//		this.noticeList = noticeList;
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
//	public User getAdmin() {
//		return admin;
//	}
//
//	public void setAdmin(User admin) {
//		this.admin = admin;
//	}
//
//	public AuthenticationBean getAuthenticationBean() {
//		return authenticationBean;
//	}
//
//	public void setAuthenticationBean(AuthenticationBean authenticationBean) {
//		this.authenticationBean = authenticationBean;
//	}
//
//	public OrderRecordBean getOrderRecordBean() {
//		return orderRecordBean;
//	}
//
//	public void setOrderRecordBean(OrderRecordBean orderRecordBean) {
//		this.orderRecordBean = orderRecordBean;
//	}
//
//	public SubscriptionCenterBean getSubscriptionCenterBean() {
//		return subscriptionCenterBean;
//	}
//
//	public void setSubscriptionCenterBean(SubscriptionCenterBean subscriptionCenterBean) {
//		this.subscriptionCenterBean = subscriptionCenterBean;
//	}
//
@Override
protected UserDto fetchSessionAdmin() {
	UserDto admin = new UserDto();
	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

	admin = (UserDto) request.getAttribute(BusinessConstant.USER_TEMP_FLAG);
	List<Notice> noticeList =new ArrayList<>();
	int robotSwitch;
	if(admin == null){	//先取临时MAP中存的用户对象,取不到再通过接口取用户对象
		String sid = LoginUtils.getSidFromCookie(request);
		if (StringUtils.isNotBlank(sid)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sid", sid);
			params.put("platform",BusinessConstant.PLATFORM_Web);
			String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userSession"), params);
			if (StringUtils.isNotBlank(rtnStr)) {
				BaseDto<UserDto> baseDto = JSONObject.parseObject(rtnStr, BaseDto.class);
				if (baseDto != null && CodeConstant.SUCCESS_CODE.equals(baseDto.getCode())) {
					JSONObject jsonObject = JSONObject.parseObject(rtnStr);
					String userInfo = jsonObject.getString("userInfo");
					admin = JSONObject.parseObject(userInfo, UserDto.class);
					admin.setSid(jsonObject.getString("sid"));
				}
			}
		}
	}

	// 专业版到期，降为基础版用户
	if (admin != null && (admin.getUserType() == BusinessConstant.USER_TYPE_PRO || admin.getUserType() == BusinessConstant.USER_TYPE_PRO_B)) {
		if (admin.getProUserValidEndTime() != null && admin.getProUserValidEndTime().getTime() <= new Date().getTime()) {
			admin.setUserType(BusinessConstant.USER_TYPE_BASE);
			admin.setProUserValidEndTime(null);
			HashMap<String, Object> param = new HashMap<>();
			param.put("userDto",admin);
//			param.put("userDto","admin");
			userFeignClient.doModifyUserType(param);
//			orderRecordBean.doModifyUserType(admin);

			// 关闭用户的日周月报
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("userId",admin.getUserId());
			userFeignClient.closeReport(param1);
//			subscriptionCenterBean.doCloseReport(admin);
		}
	}

	if (admin != null) {
		String noticeListStr = RedisUtils.getAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_NOTICE_LIST));
		if (StringUtils.isNotBlank(noticeListStr))
			noticeList = JSONObject.parseArray(noticeListStr, Notice.class);
		if (CollectionUtils.isEmpty(noticeList)) {
			HashMap<String, Object> map = new HashMap<>();
			BaseDto notice = userFeignClient.getNotice(map);
			noticeList= (List<Notice>) notice.getData();
//			noticeList = warningBean.getNotice();
			RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_NOTICE_LIST), JSONObject.toJSONString(noticeList), SystemConstants.HOT_NOTICE_TIME);
		}

		// 获取显示菜单权限
		String attribute = RedisUtils.getAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_CLOSE_ROBOT + admin.getUserId()));
		if (StringUtils.isNotBlank(attribute) && attribute.equals("1")) {
			robotSwitch=0;
		}else {
			robotSwitch=1;
		}
	}
return admin;
}

//	/**
//	 * 接口返回用户对象转为admin对象
//	 */
//	public static User userResConvertAdmin(UserDto baseDto){
//		User user = null;
//		if (baseDto != null && Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())) {
//			user = new User();
//			user.setUserId(baseDto.getUserId());
//			user.setUsername(baseDto.getUsername());
//			user.setNickname(baseDto.getNickname());
//			user.setEmail(baseDto.getEmail());
//			user.setMobile(baseDto.getMobile());
//			user.setPassword(baseDto.getPassword());
//			user.setPasswordStrength(baseDto.getPasswordStrength());
//			user.setLastLoginTime(baseDto.getLastLoginTime());
//			user.setRemark(baseDto.getRemark());
//			user.setStatus(baseDto.getStatus());
//			user.setCreateTime(baseDto.getCreateTime());
//			user.setUpdateTime(baseDto.getUpdateTime());
//			user.setUserHead(baseDto.getUserHead());
//			user.setAppUserStatus(baseDto.getAppUserStatus());
//			user.setUserChannel(baseDto.getUserChannel());
//			user.setUserType(baseDto.getUserType());
//			user.setProUserValidEndTime(baseDto.getProUserValidEndTime());
//			user.setUserAnalysisValidCount(baseDto.getUserAnalysisValidCount());
//			user.setUserWeiboAnalysisValidCount(baseDto.getUserWeiboAnalysisValidCount());
//			user.setUserBriefValidCount(baseDto.getUserBriefValidCount());
//			user.setUserReportValidCount(baseDto.getUserReportValidCount());
//			user.setUserProductAnalysisValidCount(baseDto.getUserProductAnalysisValidCount());
//			user.setUserSingleWeiboAnalysisValidCount(baseDto.getUserSingleWeiboAnalysisValidCount());
//			user.setExportDataCount(baseDto.getExportDataCount());
//			user.setCreditAmount(baseDto.getCreditAmount());
//			user.setPlatform(baseDto.getPlatform());
//			user.setSharePlanAmount(Double.parseDouble(new DecimalFormat("0.00").format(baseDto.getSharePlanAmount())));
//			user.setCommentsCount(baseDto.getCommentsCount());
//			user.setAllKeywordCount(baseDto.getAllKeywordCount());
//			user.setNoUseKeywordCount(baseDto.getNoUseKeywordCount());
//			user.setVipInfo(baseDto.getVipInfo());
//			user.setValidWdSum(baseDto.getValidWdSum());
//			user.setWillOverdueWdSum(baseDto.getWillOverdueWdSum());
//			user.setLastSignInDate(baseDto.getLastSignInDate());
//			user.setSeriesSignInDays(baseDto.getSeriesSignInDays());
//			user.setStopEndTime(baseDto.getStopEndTime());
//			user.setUserExtendInfoElement(userRes.getUserInfo().getUserExtendInfo());
//
//			// 检测子账号
//			String subObj = RedisUtils.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_SUB_USER + userRes.getSid()));
//			if (StringUtils.isBlank(subObj))
//				user.setSubUser(false);
//			else
//				user.setSubUser(Boolean.parseBoolean(subObj));
//		}
//		return user;
//	}
//
//	@Override
//	protected void fetchDwrSessionAdmin() {
//		HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
//
//		admin = (User) request.getAttribute(Constants.USER_TEMP_FLAG);
//
//		if(admin == null){	//��ȡ��ʱMAP�д���û�����,ȡ������ͨ���ӿ�ȡ�û�����
//			String sid = LoginUtils.getSidFromCookie(request);
//			if (StringUtils.isNotBlank(sid)) {
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("sid", sid);
//
//				String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userSessionURL"), params);
//				if (StringUtils.isNotBlank(rtnStr)) {
//					UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//					if (userRes != null && Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())) {
//						admin = userResConvertAdmin(userRes);
//					}
//				}
//			}
//		}
//
//		// רҵ�浽�ڣ���Ϊ�������û�
//		if (admin != null && (admin.getUserType() == Constants.USER_TYPE_PRO || admin.getUserType() == Constants.USER_TYPE_PRO_B)) {
//			if (admin.getProUserValidEndTime() != null && admin.getProUserValidEndTime().getTime() <= new Date().getTime()) {
//				admin.setUserType(Constants.USER_TYPE_BASE);
//				admin.setProUserValidEndTime(null);
//
//				orderRecordBean.doModifyUserType(admin);
//
//				// �ر��û��������±�
//				subscriptionCenterBean.doCloseReport(admin);
//			}
//		}
//
//		if (admin != null) {
//			String noticeListStr = RedisUtils.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_NOTICE_LIST));
//			if (StringUtils.isNotBlank(noticeListStr))
//				noticeList = JSONObject.parseArray(noticeListStr, Notice.class);
//
//			if (CollectionUtils.isEmpty(noticeList)) {
//				noticeList = warningBean.getNotice();
//				RedisUtils.setAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_NOTICE_LIST), JSONObject.toJSONString(noticeList), 60 * 30);
//			}
//
//			// ��ȡ��ʾ�˵�Ȩ��
//			/*fetchShowMenu(admin);*/
//			String attribute = RedisUtils.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_CLOSE_ROBOT + admin.getUserId()));
//			if (StringUtils.isNotBlank(attribute) && attribute.equals("1")) {
//				robotSwitch=0;
//			}else {
//				robotSwitch=1;
//			}
//		}
//	}
//
//	/**
//	 * �ӿڷ����û�����תΪadmin����
//	 */
//	public static User userResConvertAdmin(UserRes userRes){
//		User user = null;
//		if (userRes != null && Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())) {
//			user = new User();
//			user.setUserId(userRes.getUserInfo().getUserId());
//			user.setUsername(userRes.getUserInfo().getUsername());
//			user.setNickname(userRes.getUserInfo().getNickname());
//			user.setEmail(userRes.getUserInfo().getEmail());
//			user.setMobile(userRes.getUserInfo().getMobile());
//			user.setPassword(userRes.getUserInfo().getPassword());
//			user.setPasswordStrength(userRes.getUserInfo().getPasswordStrength());
//			user.setLastLoginTime(userRes.getUserInfo().getLastLoginTime());
//			user.setRemark(userRes.getUserInfo().getRemark());
//			user.setStatus(userRes.getUserInfo().getStatus());
//			user.setCreateTime(userRes.getUserInfo().getCreateTime());
//			user.setUpdateTime(userRes.getUserInfo().getUpdateTime());
//			user.setUserHead(userRes.getUserInfo().getUserHead());
//			user.setAppUserStatus(userRes.getUserInfo().getAppUserStatus());
//			user.setUserChannel(userRes.getUserInfo().getUserChannel());
//			user.setUserType(userRes.getUserInfo().getUserType());
//			user.setProUserValidEndTime(userRes.getUserInfo().getProUserValidEndTime());
//			user.setUserAnalysisValidCount(userRes.getUserInfo().getUserAnalysisValidCount());
//			user.setUserWeiboAnalysisValidCount(userRes.getUserInfo().getUserWeiboAnalysisValidCount());
//			user.setUserBriefValidCount(userRes.getUserInfo().getUserBriefValidCount());
//			user.setUserReportValidCount(userRes.getUserInfo().getUserReportValidCount());
//			user.setUserProductAnalysisValidCount(userRes.getUserInfo().getUserProductAnalysisValidCount());
//			user.setUserSingleWeiboAnalysisValidCount(userRes.getUserInfo().getUserSingleWeiboAnalysisValidCount());
//			user.setExportDataCount(userRes.getUserInfo().getExportDataCount());
//			user.setCreditAmount(userRes.getUserInfo().getCreditAmount());
//			user.setPlatform(userRes.getUserInfo().getPlatform());
//			user.setSharePlanAmount(Double.parseDouble(new DecimalFormat("0.00").format(userRes.getUserInfo().getSharePlanAmount())));
//			user.setCommentsCount(userRes.getUserInfo().getCommentsCount());
//			user.setAllKeywordCount(userRes.getUserInfo().getAllKeywordCount());
//			user.setNoUseKeywordCount(userRes.getUserInfo().getNoUseKeywordCount());
//			user.setVipInfo(userRes.getUserInfo().getVipInfo());
//			user.setValidWdSum(userRes.getUserInfo().getValidWdSum());
//			user.setWillOverdueWdSum(userRes.getUserInfo().getWillOverdueWdSum());
//			user.setLastSignInDate(userRes.getUserInfo().getLastSignInDate());
//			user.setSeriesSignInDays(userRes.getUserInfo().getSeriesSignInDays());
//			user.setStopEndTime(userRes.getUserInfo().getStopEndTime());
//			user.setUserExtendInfoElement(userRes.getUserInfo().getUserExtendInfo());
//
//			// ������˺�
//			String subObj = RedisUtils.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_SUB_USER + userRes.getSid()));
//			if (StringUtils.isBlank(subObj))
//				user.setSubUser(false);
//			else
//				user.setSubUser(Boolean.parseBoolean(subObj));
//		}
//		return user;
//	}

	public Integer totalSc(String userId,HttpServletRequest request) {
		Map<String,Object> params = new HashMap<>();
		params.put("userEncode", CommonUtils.buildUserEncode(userId));
		params.put("platformTag", BusinessConstant.PLATFORM_TAG);
		params.put("directory", BusinessConstant.JB_DIRECTORY_SCJ);
		params.put("directoryType", BusinessConstant.JB_DIRECTORY_TYPE_SCJ);
		String jsonStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request,
				IntraBusinessAPIConfig.getValue("totalSc"), params);
		if(StringUtils.isBlank(jsonStr)) {
			return null;
		}
		IContentCommonNetView view = JSON.parseObject(jsonStr, IContentCommonNetView.class);
		if(view == null || !CodeConstant.SUCCESS_CODE.equals(view.getCode())) {
			return null;
		}
		return (int) view.getTotalCount();
	}
	protected Map<String,Object> fetchRightNumber() throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		UserDto admin = fetchSessionAdmin();
//		resetPagesize();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		int cartTotal=0;
		int collectTotal=0;
		if (admin != null && admin.getUserId() > 0) {

			Object object = RedisUtils.getAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_COLLECT + admin.getUserId()));
			if (object == null) {
				log.info(DateUtils.format(new Date()) + "--fetchRightNumber object is null userId=" + admin.getUserId());
				Integer total = totalSc(String.valueOf(admin.getUserId()),request);
				if(total != null) {
					cartTotal = total;
					collectTotal = total;
					RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_COLLECT + admin.getUserId()), String.valueOf(collectTotal), 300);
				}
			} else {
				log.info(DateUtils.format(new Date()) + "--fetchRightNumber object=" + Integer.parseInt(object.toString()) + "  userId=" + admin.getUserId());
				cartTotal = Integer.parseInt(object.toString());
				collectTotal = cartTotal;
			}

		}
		map.put("cartTotal",cartTotal);
		map.put("collectTotal",collectTotal);

		return map;
	}
	/**
	 * 加载产品包列表
	 *
	 * @param type
	 *            产品包类型<br />
	 *            0 - 全部<br />
	 *            1 - 体验型产品包<br />
	 *            2 - 监测方案产品包<br />
	 *            3 - 全网事件分析产品包<br />
	 *            4 - 微博事件分析产品包<br />
	 *            5 - 简报制作产品包<br />
	 *            6 - 竞品分析产品包<br />
	 *            7 - 单条微博分析产品包<br />
	 *            8 - 导出数据产品包<br />
	 *            9 - 人工简报产品包<br />
	 *            10 - 专业版产品包<br />
	 *            12 - 微积分产品包<br />
	 *            13 - 热度报告按次数产品包<br />
	 *            14 - 热度报告按时长产品包<br />
	 *            18 - 评论产品包
	 *            19 - 京东万象产品包
	 *            20 - 新微积分产品包
	 *            21 - 新版热度指数（新版开放工具）产品包
	 * @throws Exception
	 */
	protected BaseDto fetchProductlist(int type) throws Exception {
		log.info("fetchProductlist type = [" + type + "]");
		BaseDto baseDto = new BaseDto();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		Map<String, Object> params = new HashMap<String, Object>();
		List<ProductPackage> ProductPackages = new ArrayList<ProductPackage>();
		params.put("type", type);
		params.put("platform", BusinessConstant.PLATFORM_Web);
		String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("packageList"), params);
		log.info("fetchProductlist type = [" + type + "] rtnStr = [" + rtnStr + "]");
		if (!StringUtils.isBlank(rtnStr)) {
			PackageListView packageListRes = JSONObject.parseObject(rtnStr, PackageListView.class);
			if (packageListRes != null && BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(packageListRes.getCode()) && packageListRes.getPackageList() != null && packageListRes.getPackageList().size() > 0) {
				List<ProductPackage> packages = new ArrayList<ProductPackage>();
				for (ProductPackage vo : packageListRes.getPackageList()) {
					if (vo.getProductPackageId() != 33 && vo.getProductPackageId() != 34 && vo.getProductPackageId() != 35 && vo.getProductPackageId() != 36)
//						packages.add(parseProductPackage(vo));
						packages.add(vo);
				}

				switch (type) {
					case BusinessConstant.PACKAGE_TYPE_FREE:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_KEYWORD:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_ANALYSIS:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_WEIBO_ANALYSIS:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_BRIEF:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_PRODUCT_ANALYSIS:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_SINGLE_WEIBO_ANALYSIS:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_EXPORT_DATA:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_MUAL_BRIEF:
						break;
					case BusinessConstant.PACKAGE_TYPE_PRO:
						ProductPackages = packages;
						break;
				/*case Constants.PACKAGE_TYPE_CREDIT:
					packageListCredit = packages;
					break;*/
					case BusinessConstant.PACKAGE_TYPE_HOT_REPORT_COUNT:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_HOT_REPORT_DAYS:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_COMMENTS:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_CREDIT_NEW:
						ProductPackages = packages;
						break;
					case BusinessConstant.PACKAGE_TYPE_HOT_SEARCH_NEW:
						ProductPackages = packages;
						break;
					default:
						ProductPackages = packages;
						break;
				}
			}

			/*if (type != 12 && CollectionUtils.isEmpty(packageListCredit))
				fetchProductlist(Constants.PACKAGE_TYPE_CREDIT);*/
			if (type != BusinessConstant.PACKAGE_TYPE_CREDIT_NEW && CollectionUtils.isEmpty(ProductPackages))
				fetchProductlist(BusinessConstant.PACKAGE_TYPE_CREDIT_NEW);
		}
		return baseDto.setData(ProductPackages);
	}
//	/**
//	 * PackageVOתProductPackage
//	 *
//	 * @param vo
//	 * @return
//	 */
//	public static ProductPackage parseProductPackage(PackageVO vo) {
//		if (vo != null) {
//			ProductPackage productPackage = new ProductPackage();
//			productPackage.setProductPackageId(vo.getProductPackageId());
//			productPackage.setPackageName(vo.getPackageName());
//			productPackage.setPackagePrice(vo.getPackagePrice().floatValue());
//			productPackage.setPackageType(vo.getPackageType());
//			productPackage.setKeywordServeDays(vo.getKeywordServeDays());
//			productPackage.setKeywordCount(vo.getKeywordCount());
//			productPackage.setAnalysisCount(vo.getAnalysisCount());
//			productPackage.setWeiboAnalysisCount(vo.getWeiboAnalysisCount());
//			productPackage.setBriefCount(vo.getBriefCount());
//			productPackage.setProductAnalysisCount(vo.getProductAnalysisCount());
//			productPackage.setSingleWeiboAnalysisCount(vo.getSingleWeiboAnalysisCount());
//			productPackage.setExportDataCount(vo.getExportDataCount());
//			productPackage.setCreditCount(vo.getCreditCount());
//			productPackage.setGiftCreditCount(vo.getGiftCreditCount());
//			productPackage.setHotReportCount(vo.getHotReportCount());
//			productPackage.setHotReportServerDays(vo.getHotReportServerDays());
//			productPackage.setCommentsAnalysisCount(vo.getCommentsCount());
//			productPackage.setDescription(vo.getDescription());
//			productPackage.setSortSeq(vo.getSortSeq());
//			productPackage.setStatus(vo.getStatus());
//			productPackage.setRenewPackageId(vo.getRenewPackageId());
//
//			return productPackage;
//		}
//		return null;
//	}
//
//	/**
//	 * ������ʾ�˵�Ȩ��
//	 *
//	 * @param user
//	 */
//	protected void fetchShowMenu(User user) {
//		if (user != null) {
//			String key = Constants.generateJedisKey(Constants.JEDIS_KEYS_SHARE_PLAN_AMOUNT_MENU + user.getUserId());
//			Object showSharePlanAmountObj = RedisUtils.getAttribute(key);
//			if (showSharePlanAmountObj != null) {
//				showSharePlanAmountMenu = Boolean.parseBoolean(showSharePlanAmountObj.toString());
//			} else {
//				try {
//					UserExclusiveChannelRes userExclusiveChannelRes = fetchUserExclusiveChannel(user.getUserId());
//					if (userExclusiveChannelRes != null && userExclusiveChannelRes.getUserExclusiveChannel().getUecRewardFlag() == 1) {
//						showSharePlanAmountMenu = true;
//					} else {
//						showSharePlanAmountMenu = false;
//					}
//					RedisUtils.setAttribute(key, String.valueOf(showSharePlanAmountMenu), Constants.SYS_ADMIN_CACHE_TIME);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
	protected void clearShowMenu(UserDto user) {
		if (user != null) {
			String key = RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SHARE_PLAN_AMOUNT_MENU + user.getUserId());
			RedisUtils.removeAttribute(key);
		}
	}
//
//	/**
//	 * ��ȡ����ר������
//	 *
//	 * @param userId
//	 * @return
//	 * @throws Exception
//	 */
//	protected UserExclusiveChannelRes fetchUserExclusiveChannel(int userId) throws Exception {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(userId)));
//
//		String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(ServletActionContext.getRequest(), IntraBusinessAPIConfig.getValue("userExclusiveChannelURL"), params);
//		System.out.println("fetchUserExclusiveChannel rtnStr = [" + rtnStr + "]");
//		if (StringUtils.isNotBlank(rtnStr)) {
//			UserExclusiveChannelRes userExclusiveChannelRes = JSONObject.parseObject(rtnStr, UserExclusiveChannelRes.class);
//			if (userExclusiveChannelRes != null && Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userExclusiveChannelRes.getCode())) {
//				return userExclusiveChannelRes;
//			}
//		}
//		return null;
//	}
//
//
//
protected String firstLoginOperate(HttpServletRequest request, UserDto user) {
	// 查询新的公告
//	List<Notice> nL = userCenterBean.showNewNotice(user.getLastLoginTime());
	Map<String, Object> param = new HashMap<String, Object>();
	param.put("lastLoginTime",user.getLastLoginTime());
	BaseDto bsd=userFeignClient.showNewNotice(param);
	List<Notice> nL= (List<Notice>) bsd.getData();
	// 更新待下发活动奖励
	updateUserActivitySendFlag(1, user.getUserId(), request);

	Date lastLoginTime = user.getLastLoginTime();
		/*if(lastLoginTime == null){	//当前活动弹框
			firstLoginSign = 3;
		}else*/
	int firstLoginSign=0;
	if(nL.size() > 0){	//系统公告
		firstLoginSign = 1;
	}else if(!DateUtils.format(lastLoginTime, DateUtils.FORMAT_SHORT)
			.equals(DateUtils.getNow(DateUtils.FORMAT_SHORT))){	//每日签到提醒弹框
		try {
			String is = isOverActive();
			if(StringUtils.isNotBlank(is) && "1".equals(is)){
				firstLoginSign=6;//活动页最后一天的弹框
			}else{
				//判断用户是否有可兑换礼品卡
				Map<String, Object> param1 = new HashMap<String, Object>();
				param1.put("userId",user.getUserId());
				BaseDto bsd1=userFeignClient.showNewNotice(param1);
				List<GiftCard> giftCards = (List<GiftCard>) bsd1.getData();
//				List<GiftCard> giftCards = userCenterBean.queryGiftCardByUserId(user.getUserId());
				if(null!=giftCards && CollectionUtils.isNotEmpty(giftCards)){
					firstLoginSign = 5;//优惠券弹框
				}else{
					firstLoginSign = 2;//机器人弹框
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}else{	//什么都不弹
		firstLoginSign = 0;
	}
	nL = null;
	return String.valueOf(firstLoginSign);
}
    public void updateUserActivitySendFlag(int type, Integer userId, HttpServletRequest request){
        if(type == 1){
            // 获取待下发奖励，并放入缓存
            List<ActivitySendRecord> activitySendRecords = null;
            String rtnStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("getActivitySendRecord"), CommonUtils.getIntraBusinessParams(userId));
            if(StringUtils.isNotBlank(rtnStr)){
                ActivitySendRecordsView view = JSONObject.parseObject(rtnStr, ActivitySendRecordsView.class);
                if(view != null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())){
                    activitySendRecords = view.getActivitySendRecords();
                }
            }
            if(CollectionUtils.isNotEmpty(activitySendRecords)){
                RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_ACTIVITY_SEND_FLAG + userId), JSONObject.toJSONString(activitySendRecords),SystemConstants.ACTIVITY_SEND);
            }
        }else if(type == 2){
            // 删除缓存
            RedisUtils.removeAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_ACTIVITY_SEND_FLAG + userId));
        }
    }

	public String isOverActive() throws Exception {
		Date date = new Date();
		String is = "1";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastDate = "2018-06-30 23:59:59";
		Date last = dateFormat.parse(lastDate);
		if((last.getTime()-date.getTime())>0){
			System.out.println("还没结束");
			is = "1";
		}else{
			System.out.println("结束");
			is = "2";
		}
		return is;
	}
//
//	public int getFirstLoginSign() {
//		return firstLoginSign;
//	}
//
//	public void setFirstLoginSign(int firstLoginSign) {
//		this.firstLoginSign = firstLoginSign;
//	}

}
