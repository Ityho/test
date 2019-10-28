/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: CodeConstant.java 
 * @Prject: wyq-ad-supervision
 * @Package: com.midu.wyq.constant 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年11月1日 下午6:42:59 
 * @version: V1.0   
 */
package com.miduchina.wrd;

import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: CodeConstant 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2017年11月1日 下午6:42:59  
 */
public class CodeConstant {
	public final static Map<String, String> MSG_MAP = new HashMap<>();
	public static final String NOT_LOGIN_CDEO = "1111";//未登录
	static {
		MSG_MAP.put(CodeConstant.SUCCESS_CODE, "处理正常！");
		MSG_MAP.put(CodeConstant.FAILURE_CODE, "请求处理失败！");

		MSG_MAP.put(CodeConstant.E_REQUEST_HANDLE_IGNORE_CODE_0001, "忽略的请求");
		MSG_MAP.put(CodeConstant.E_REQUEST_TIMESTAMP_CODE_0002, "客户端时间设置异常");

		/**
		 * 101-999(系统参数必要性检验)
		 */
		MSG_MAP.put(CodeConstant.E_NECESSARY_SERIALID_CODE_103, "请提交序列号");
		MSG_MAP.put(CodeConstant.E_NECESSARY_USERENCODE_CODE_104, "请提交用户加密唯一标识");
		MSG_MAP.put(CodeConstant.E_NECESSARY_UPGRADE_CLIENT_CODE_105, "请升级App到最新版本");

		/**
		 * 1001-1999(过程数据状态校验结果)
		 */
		MSG_MAP.put(CodeConstant.E_STATUS_SERIALID_NOT_EXIST_CODE_1003, "序列号不存在");
		MSG_MAP.put(CodeConstant.E_STATUS_SERIALID_FREEZED_CODE_1004, "序列号被系统冻结");
		MSG_MAP.put(CodeConstant.E_STATUS_DEVICE_ID_CODE_1005, "该设备禁止访问");


		/**
		 * 10001-19999(参数问题)
		 */
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_TYPE_PARAM, "请提交正确的type参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_ACCESSTOKEN_PARAM, "请提交accessToken参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_PARAM, "请提交创建订单参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_TYPE_PARAM, "请提交订单类型参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_KEYWORD_IDS_PARAM, "请提交续费监测方案IDS参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_RENEW_PACKAGE_ID_PARAM, "请提交续费产品包ID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_RENEW_PACKAGE_COUNT_PARAM, "请提交续费产品包数量参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_PACKAGES_PARAM, "请提交购买产品包列表参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_PACKAGES_COUNT_PARAM, "请提交购买产品数量参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_FENXI_WEIBO_ID_PARAM, "请提交分析微博ID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_MANUAL_BRIEF_ID_PARAM, "请提交人工简报ID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_EXPORT_CONDITION_ID_PARAM, "请提交数据导出记录ID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_PACKAGE_ID_PARAM, "请提交产品ID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_PAY_INNER_TRADE_NO, "请提交内部支付交易单号参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_PAY_TRADE_NO, "请提交第三方交易单号参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_ORDER_NO, "请提交订单编号参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_ORDER_RECORD_ID, "请提交订单ID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_KEYWORDS_PARAM, "请提交关键词参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_KEYWORDS_PARAM_ERROR, "关键词参数错误");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_ORDER_HEAT_REPORT_IDS_PARAM, "请提交热度日报IDS参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_CREATE_USER_EXCLUSIVE_CHANNEL, "请提交渠道号参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_EXCLUSIVE_EXCHANGE_AMOUNT, "请提交正确的兑换金额参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_KEYWORD_ID_PARAM, "请提交监测方案ID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_KEYWORD_PARAM, "请提交监测方案关键词参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_MONITOR_TYPE_PARAM, "请提交监测类型参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_USERNAME_PARAM, "请提交用户名参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_PASSWORD_PARAM, "请提交密码参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_APP_USER_STATUS_PARAM, "请提交用户所属平台参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_USER_CHANNEL_PARAM, "请提交用户渠道参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_SID, "请提交SID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_USER_PARAM, "请提交注册用户参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_UID_PARAM, "请提交第三方唯一标识(uid)参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_PLATFORM_TYPE_PARAM, "请提交平台类型参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_USERRIGHTSRECORD_ITEM_PARAM, "请提交变动项参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_USERRIGHTSRECORD_TYPE_PARAM, "请提交变动类型参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_USERRIGHTSRECORD_COUNT_PARAM, "请提交变动数量参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_SUBUSER_ID_PARAM, "请提交子账号ID参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_VALIDENDDATE_PARAM, "请提交有效截止日期参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_STATUS_PARAM, "请提交状态参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_MOBILE_PARAM, "请提交手机号码参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_SMS_TYPE, "请提交短信类型参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_SMS_CONTENT, "请提交短信内容参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_SMS_VCODE, "请提交验证码参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_COUPON_TYPE, "请提交兑换券类型参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_ERROR_PARAM, "请提交正确的参数");
		MSG_MAP.put(CodeConstant.F_LACK_NECESSARY_ERROR, "请提交必要的参数");
		/**
		 * 2001-2099(处理结果状态)
		 */

		/**
		 * 20000-2999(处理结果状态)
		 */

		MSG_MAP.put(CodeConstant.F_NOT_FOUND_USER, "获取用户信息失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_PRODUCT_PACKAGE, "获取产品套餐失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_RENEW_PRODUCT_PACKAGE, "获取续费产品套餐失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_PAY_RECORD, "获取支付记录失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_ORDER_RECORD, "获取订单记录失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_KEYWORD, "获取监测方案记录失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_H5ACTIVITY, "获取H5活动记录失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_SUBUSERINFO, "获取子账号信息失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_COUPON, "获取兑换券信息失败");
		MSG_MAP.put(CodeConstant.F_NOT_FOUND_SIGN_IN_REWARD, "获取连续签到奖励信息失败");
		MSG_MAP.put(CodeConstant.F_NOT_COUPON_USED, "您的兑换券已被用过了哦！~");
		MSG_MAP.put(CodeConstant.F_NOT_WYQ_CASE, "未找到相关案例！~");
		MSG_MAP.put(CodeConstant.F_NOT_ACTIVITY, "未查到相关活动信息！~");
		MSG_MAP.put(CodeConstant.F_NOT_ACTIVITY_PRIZE, "未查到相关奖品信息！~");


		/**
		 * 2101-2199(业务参数必要性检验)
		 */

		/**
		 * 2201-2299
		 */

		/**
		 * 2301-2399
		 */

		/**
		 * 2501-2599
		 */

		/**
		 * 2601-2699
		 */

		/**
		 * 2701-2799
		 */


		/**
		 * 2401-2499
		 */


		/**
		 * 3001-3999(过程数据存在性)
		 */
		MSG_MAP.put(CodeConstant.E_NOT_FOUND_SERIALID_3002, "序列号不存在");

		/**
		 * 30001-39999(处理错误)
		 */
		MSG_MAP.put(CodeConstant.F_ERROR_VERIFY_REQUEST, "请求参数校验失败");
		MSG_MAP.put(CodeConstant.F_ERROR_USER_ENCODE, "用户加密唯一标识校验失败");
		MSG_MAP.put(CodeConstant.F_ERROR_PARSE_CONFIRM_ORDER, "解析创建订单参数失败");
		MSG_MAP.put(CodeConstant.F_ERROR_PAY_STATUS, "订单支付状态不正确");
		MSG_MAP.put(CodeConstant.F_ERROR_PAY_USER_NOT_MATCH, "支付记录和用户不匹配");
		MSG_MAP.put(CodeConstant.F_ERROR_USER_ALREADY_EXISTS, "该用户/手机号码已存在");
		MSG_MAP.put(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_00, "用户名或密码错误！");
		MSG_MAP.put(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_01, "用户名或密码错误！还可以再试 ${count} 次！");
		MSG_MAP.put(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_02, "用户名或密码错误！密码输入次数已超过限制，请明天再试！");
		MSG_MAP.put(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_03, "密码输入次数已超过限制，请明天再试！");
		MSG_MAP.put(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_04, "仅剩一次机会，建议选择短信验证登陆或重置密码！");
		MSG_MAP.put(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_05, "您的账号已被锁定，请1小时之后再试！");
		MSG_MAP.put(CodeConstant.F_ERROR_H5_ACTIVIED, "已参加过该活动");
		MSG_MAP.put(CodeConstant.F_ERROR_PARSE_USER, "解析用户参数失败");
		MSG_MAP.put(CodeConstant.F_ERROR_MIN_USERRIGHTSRECORD, "扣减用户权益失败");
		MSG_MAP.put(CodeConstant.F_ERROR_USERNAME_NOT_EXISTS, "该帐号不存在");
		MSG_MAP.put(CodeConstant.F_ERROR_USERID_AND_SUBUSERID, "子账号归属不匹配");
		MSG_MAP.put(CodeConstant.F_ERROR_NOT_PRO_USER, "非套餐用户");
		MSG_MAP.put(CodeConstant.F_ERROR_LIMIT_EXCEEDED_SUBUSER, "超过子账号开通限额");
		MSG_MAP.put(CodeConstant.F_ERROR_SUBUSER_VALIDENDTIME_LIMIT_PROVALIDENDDATE, "子账号有效期超过专业版有效期");
		MSG_MAP.put(CodeConstant.F_ERROR_USER_VALIDENDTIME_LIMIT, "该帐号已过期");
		MSG_MAP.put(CodeConstant.F_ERROR_USER_PAUSE, "该帐号已被暂停使用");
		MSG_MAP.put(CodeConstant.F_ERROR_H5ACTIVE_TIME_RANGE, "不在活动时间范围内");
		MSG_MAP.put(CodeConstant.F_ERROR_KEYWORD_MODIFY_NUM, "方案修改次数已用完");
		MSG_MAP.put(CodeConstant.F_ERROR_NO_NEED_REAL_NAME, "用户不需要实名认证");
		MSG_MAP.put(CodeConstant.F_ERROR_VOCDE_NO_OVERDUE, "短信验证码尚未过期");
		MSG_MAP.put(CodeConstant.F_ERROR_VOCDE_OVERDUE, "短信验证码已过期");
		MSG_MAP.put(CodeConstant.F_ERROR_VOCDE_OVER_LIMIT, "短信发送数量已达上限");
		MSG_MAP.put(CodeConstant.F_ERROR_COUPON_EXCHANGED, "兑换券重复兑换");
		MSG_MAP.put(CodeConstant.F_ERROR_WD_SUM_NOT_ENOUGH, "您当前微豆数量不足哦！");
		MSG_MAP.put(CodeConstant.F_ERROR_TIME_PARAM, "请提交正确的时间参数");
		MSG_MAP.put(CodeConstant.F_ERROR_RE_SIGN_IN, "今日已签到");
		MSG_MAP.put(CodeConstant.F_ERROR_SIGN_IN_REWARD_EXCHANGED, "签到奖励本月已领取！");
		MSG_MAP.put(CodeConstant.F_ERROR_MAX_SERIES_DAYS_NOT_ENOUGH, "您的连续签到天数不满足！");
		MSG_MAP.put(CodeConstant.F_ERROR_BEFORE_OPERATE_LOCK, "上一操作处理中");
		MSG_MAP.put(CodeConstant.F_ERROR_LUCKY_DRAW_TIME_NOT_ENOUGH, "今日抽奖次数已用完，请明天再来哦！~");
		MSG_MAP.put(CodeConstant.F_ERROR_TASK_ADD_WD_TIMES_TOO_MORE, "超出奖励次数！");
		MSG_MAP.put(CodeConstant.F_ERROR_USER_MOBILE_NOT_BIND, "你还没绑定手机号哦~！");
		MSG_MAP.put(CodeConstant.F_ERROR_REWARD_CONDITION_NOT_ENOUGH, "领取条件不满足哦~！");
		MSG_MAP.put(CodeConstant.F_ERROR_REGISTER_USER_IP_AND_MOBILE_PROVINCE_NOT_SAME, "请在手机号归属地注册哦！");
		MSG_MAP.put(CodeConstant.F_ERROR_USER_HAS_GIFT_PACKAGE, "赠送产品包已领取过喽！");
		MSG_MAP.put(CodeConstant.F_ERROR_REALNAME_USER_IP_AND_MOBILE_PROVINCE_NOT_SAME, "绑定失败喽！~");
		MSG_MAP.put(CodeConstant.F_ERROR_MAX_REGISTER_USER_NUM, "注册失败啦！~");
		MSG_MAP.put(CodeConstant.F_ERROR_IP_IS_ABNORMAL, "网络环境异常！~");
		MSG_MAP.put(CodeConstant.F_ERROR_ACTIVITY_PRIZE_BE_RECEIVED, "奖品不可重复领取！~");
		MSG_MAP.put(CodeConstant.F_ERROR_ACTIVITY_PRIZE_CAN_NOT_RECEIVE, "不满足领取条件！~");
		MSG_MAP.put(CodeConstant.F_ERROR_NOT_IN_THE_PERIOD_OF_ACTIVITY, "活动已结束！~");
		MSG_MAP.put(CodeConstant.F_ERROR_CONTAIN_SINGLE_AREA_KEYWORD, "对不起，您当前的地域关键词设置的太宽泛，可能包含太多冗杂信息，为了使得到的信息更准确，建议用“地域+事件”的形式创建搜索~");
		MSG_MAP.put(CodeConstant.F_ERROR_GIFT_CARD_NOT_FOUND, "礼品卡不存在！~");
		MSG_MAP.put(CodeConstant.F_ERROR_GIFT_CARD_BE_USED, "礼品卡已使用！~");
		MSG_MAP.put(CodeConstant.F_ERROR_GIFT_CARD_OVER_DUE, "礼品卡已过期！~");

		/**
		 * 4001-4999(过程数据校验)
		 */
		MSG_MAP.put(CodeConstant.E_USER_ENCODE_4005, "用户标识错误，请稍后重试");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_VALID_COUNT_CODE_4021, "您的事件分析数量不足，请购买后在添加！");
		MSG_MAP.put(CodeConstant.E_EVENT_KEYWORD_SIZEMIN_CODE_4022, "关键词必须设置两个字以上！");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_TASK_NOT_EXIST_CODE_4023, "事件不存在或者已被删除！");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_TASK_UPDATED_MAX_COUNT_CODE_4024, "三次更新已用光，请建立新的全网分析！");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_TASK_NO_DATA_CODE_4025, "没有数据，请修改关键字或事件范围！");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_TASK_MORE_THAN_MAX_DATA_CODE_4026, "数据量过大，请修改关键字或事件范围！");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_FORMAT_ERROE_CODE_4027, "创建事件任务必须满足两个字，您的创建不符合规范！");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_TASK_REQUEST_FAILED_CODE_4028, "查询事件分析任务信息失败！");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_LAST_TASK_NOT_FINISHED_CODE_4029, "该任务的上一次分析活动没有结束!");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_ONLY_UPDATE_FINISHED_TASK_CODE_4030, "只能更新分析完成的监测方案!");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_CREATE_CODE_4031, "创建任务失败");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_KEYWORD_CODE_4032, "事件关键词包含敏感信息");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_CONFIRM_CODE_4033, "任务确认失败");
		MSG_MAP.put(CodeConstant.E_EVENT_ANALYSIS_DELETE_CODE_4034, "任务删除失败");
	}

	/**
	 * 返回正常
	 */
	public static final String DATA_API_SUCCESS_CODE = "2001";
	public static final String SUCCESS_CODE = "0000";

	/**
	 * 返回错误
	 */
	public static final String FAILURE_CODE = "9999";
	// 前端数据为空
	public static final String ERROR_CODE_1000 = "1000";
	public static final String ERROR_CODE_1001 = "1001";
	public static final String ERROR_CODE_1002 = "1002";
	public static final String ERROR_CODE_1003 = "1003";

	// 本地处理错误
	public static final String ERROR_CODE_2000 = "2000";
	// 查无数据
	public static final String ERROR_CODE_2001 = "2001";
	public static final String ERROR_CODE_2002 = "2002";
	// 权益不够
	public static final String ERROR_CODE_2003 = "2003";
	// 缺少appkey
	public static final String ERROR_CODE_2004 = "2004";
	// appkey不存在
	public static final String ERROR_CODE_2005 = "2005";
	// 缺少sign
	public static final String ERROR_CODE_2006 = "2006";
	// sign认证失败
	public static final String ERROR_CODE_2007 = "2007";

	// 数据接口返回错误
	public static final String ERROR_CODE_3000 = "3000";
	// 数据接口查无数据
	public static final String ERROR_CODE_3001 = "3001";

	/**
	 * 主要用于重复请求
	 * 忽略的请求
	 */
	public static final String E_REQUEST_HANDLE_IGNORE_CODE_0001 = "0001";
	/**
	 * 客户端时间设置异常
	 */
	public static final String E_REQUEST_TIMESTAMP_CODE_0002 = "0002";


	//系统参数必要性检验
	//101-999
	/**
	 * 系统参数必要性检验(101-999)
	 * 请提交序列号
	 */
	public static final String E_LACK_NECESSARY_SYSTEM_PARAM_CODE_101= "101";

	public static final String E_NECESSARY_SERIALID_CODE_103 = "103";
	/**
	 * 请提交用户加密唯一标识
	 */
	public static final String E_NECESSARY_USERENCODE_CODE_104 = "104";
	/**
	 * 请升级App到最新版本
	 */
	public static final String E_NECESSARY_UPGRADE_CLIENT_CODE_105 = "105";
	/**
	 * 缺少参数
	 */
	public static final String F_LACK_NECESSARY_TYPE_PARAM = "10001";
	public static final String F_LACK_NECESSARY_ACCESSTOKEN_PARAM = "10002";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_PARAM = "10003";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_TYPE_PARAM = "10004";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_KEYWORD_IDS_PARAM = "10005";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_RENEW_PACKAGE_ID_PARAM = "10006";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_PACKAGES_PARAM = "10007";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_PACKAGES_COUNT_PARAM = "10008";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_FENXI_WEIBO_ID_PARAM = "10009";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_MANUAL_BRIEF_ID_PARAM = "10010";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_RENEW_PACKAGE_COUNT_PARAM = "10041";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_EXPORT_CONDITION_ID_PARAM = "10042";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_KEYWORDS_PARAM_ERROR = "10043";
	public static final String F_LACK_NECESSARY_PACKAGE_ID_PARAM = "10011";
	public static final String F_LACK_NECESSARY_PAY_INNER_TRADE_NO = "10012";
	public static final String F_LACK_NECESSARY_PAY_TRADE_NO = "10013";
	public static final String F_LACK_NECESSARY_ORDER_NO = "10014";
	public static final String F_LACK_NECESSARY_ORDER_RECORD_ID = "10015";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_KEYWORDS_PARAM = "10016";
	public static final String F_LACK_NECESSARY_CREATE_ORDER_HEAT_REPORT_IDS_PARAM = "10017";
	public static final String F_LACK_NECESSARY_CREATE_USER_EXCLUSIVE_CHANNEL = "10018";
	public static final String F_LACK_NECESSARY_EXCLUSIVE_EXCHANGE_AMOUNT = "10019";
	public static final String F_LACK_NECESSARY_KEYWORD_ID_PARAM = "10020";
	public static final String F_LACK_NECESSARY_KEYWORD_PARAM = "10021";
	public static final String F_LACK_NECESSARY_MONITOR_TYPE_PARAM = "10022";
	public static final String F_LACK_NECESSARY_USERNAME_PARAM = "10023";
	public static final String F_LACK_NECESSARY_PASSWORD_PARAM = "10024";
	public static final String F_LACK_NECESSARY_APP_USER_STATUS_PARAM = "10025";
	public static final String F_LACK_NECESSARY_USER_CHANNEL_PARAM = "10026";
	public static final String F_LACK_NECESSARY_SID = "10027";
	public static final String F_LACK_NECESSARY_USER_PARAM = "10028";
	public static final String F_LACK_NECESSARY_UID_PARAM = "10029";
	public static final String F_LACK_NECESSARY_PLATFORM_TYPE_PARAM = "10030";
	public static final String F_LACK_NECESSARY_USERRIGHTSRECORD_ITEM_PARAM = "10031";
	public static final String F_LACK_NECESSARY_USERRIGHTSRECORD_TYPE_PARAM = "10032";
	public static final String F_LACK_NECESSARY_USERRIGHTSRECORD_COUNT_PARAM = "10033";
	public static final String F_LACK_NECESSARY_SUBUSER_ID_PARAM = "10034";
	public static final String F_LACK_NECESSARY_VALIDENDDATE_PARAM = "10035";
	public static final String F_LACK_NECESSARY_STATUS_PARAM = "10036";
	public static final String F_LACK_NECESSARY_MOBILE_PARAM = "10037";
	public static final String F_LACK_NECESSARY_SMS_TYPE = "10038";
	public static final String F_LACK_NECESSARY_SMS_CONTENT = "10039";
	public static final String F_LACK_NECESSARY_SMS_VCODE = "10040";
	public static final String F_LACK_NECESSARY_COUPON_TYPE = "10044";
	public static final String F_LACK_NECESSARY_ERROR_PARAM = "10045";
	public static final String F_LACK_NECESSARY_ERROR = "10046";

	// 获取信息失败
	public static final String F_NOT_FOUND_USER = "20001";
	public static final String F_NOT_FOUND_PRODUCT_PACKAGE = "20002";
	public static final String F_NOT_FOUND_RENEW_PRODUCT_PACKAGE = "20003";
	public static final String F_NOT_FOUND_PAY_RECORD = "20004";
	public static final String F_NOT_FOUND_ORDER_RECORD = "20005";
	public static final String F_NOT_FOUND_KEYWORD = "20006";
	public static final String F_NOT_FOUND_H5ACTIVITY = "20007";
	public static final String F_NOT_FOUND_SUBUSERINFO = "20008";
	public static final String F_NOT_FOUND_COUPON = "20009";
	public static final String F_NOT_FOUND_SIGN_IN_REWARD = "20010";
	public static final String F_NOT_COUPON_USED = "20011";
	public static final String F_NOT_WYQ_CASE = "20012";
	public static final String F_NOT_ACTIVITY_PRIZE = "20013";
	public static final String F_NOT_ACTIVITY = "20014";

	/**
	 * 处理出错
	 */
	public static final String F_ERROR_VERIFY_REQUEST = "30001";
	public static final String F_ERROR_USER_ENCODE = "30002";
	public static final String F_ERROR_PARSE_CONFIRM_ORDER = "30003";
	public static final String F_ERROR_PAY_STATUS = "30004";
	public static final String F_ERROR_PAY_USER_NOT_MATCH = "30005";
	public static final String F_ERROR_USER_ALREADY_EXISTS = "30006";
	public static final String F_ERROR_USERNAME_OR_PASSWORD_00 = "3000700";
	public static final String F_ERROR_USERNAME_OR_PASSWORD_01 = "3000701";
	public static final String F_ERROR_USERNAME_OR_PASSWORD_02 = "3000702";
	public static final String F_ERROR_USERNAME_OR_PASSWORD_03 = "3000703";
	public static final String F_ERROR_USERNAME_OR_PASSWORD_04 = "3000704";
	public static final String F_ERROR_USERNAME_OR_PASSWORD_05 = "3000705";
	public static final String F_ERROR_H5_ACTIVIED = "30008";
	public static final String F_ERROR_PARSE_USER = "30009";
	public static final String F_ERROR_MIN_USERRIGHTSRECORD = "30010";
	public static final String F_ERROR_USERNAME_NOT_EXISTS = "30011";
	public static final String F_ERROR_USERID_AND_SUBUSERID = "30012";
	public static final String F_ERROR_NOT_PRO_USER = "30013";
	public static final String F_ERROR_LIMIT_EXCEEDED_SUBUSER = "30014";
	public static final String F_ERROR_SUBUSER_VALIDENDTIME_LIMIT_PROVALIDENDDATE = "30015";
	public static final String F_ERROR_USER_VALIDENDTIME_LIMIT = "30016";
	public static final String F_ERROR_USER_PAUSE = "30018";
	public static final String F_ERROR_H5ACTIVE_TIME_RANGE = "30019";
	public static final String F_ERROR_KEYWORD_MODIFY_NUM = "30020";
	public static final String F_ERROR_NO_NEED_REAL_NAME = "30021";
	public static final String F_ERROR_VOCDE_NO_OVERDUE = "30022";
	public static final String F_ERROR_VOCDE_OVERDUE = "30023";
	public static final String F_ERROR_VOCDE_OVER_LIMIT = "30024";
	public static final String F_ERROR_COUPON_EXCHANGED = "30025";
	public static final String F_ERROR_WD_SUM_NOT_ENOUGH = "30026";
	public static final String F_ERROR_TIME_PARAM = "30027";
	public static final String F_ERROR_RE_SIGN_IN = "30028";
	public static final String F_ERROR_SIGN_IN_REWARD_EXCHANGED = "30029";
	public static final String F_ERROR_MAX_SERIES_DAYS_NOT_ENOUGH = "30030";
	public static final String F_ERROR_BEFORE_OPERATE_LOCK = "30031";
	public static final String F_ERROR_LUCKY_DRAW_TIME_NOT_ENOUGH = "30032";
	public static final String F_ERROR_TASK_ADD_WD_TIMES_TOO_MORE = "30033";
	public static final String F_ERROR_USER_MOBILE_NOT_BIND = "30034";
	public static final String F_ERROR_REWARD_CONDITION_NOT_ENOUGH = "30035";
	public static final String F_ERROR_REGISTER_USER_IP_AND_MOBILE_PROVINCE_NOT_SAME = "30036";
	public static final String F_ERROR_USER_HAS_GIFT_PACKAGE = "30037";
	public static final String F_ERROR_REALNAME_USER_IP_AND_MOBILE_PROVINCE_NOT_SAME = "30038";
	public static final String F_ERROR_MAX_REGISTER_USER_NUM = "30039";
	public static final String F_ERROR_IP_IS_ABNORMAL = "10046";
	public static final String F_ERROR_ACTIVITY_PRIZE_BE_RECEIVED = "10047";
	public static final String F_ERROR_ACTIVITY_PRIZE_CAN_NOT_RECEIVE = "10048";
	public static final String F_ERROR_NOT_IN_THE_PERIOD_OF_ACTIVITY = "10049";
	public static final String F_ERROR_CONTAIN_SINGLE_AREA_KEYWORD = "10050";
	public static final String F_ERROR_GIFT_CARD_NOT_FOUND = "10051";
	public static final String F_ERROR_GIFT_CARD_BE_USED = "10052";
	public static final String F_ERROR_GIFT_CARD_OVER_DUE = "10053";

	/**
	 * 过程数据状态校验结果(1001-1999)
	 * 序列号不存在
	 */
	public static final String E_STATUS_SERIALID_NOT_EXIST_CODE_1003 = "1003";
	/**
	 * 序列号被系统冻结
	 */
	public static final String E_STATUS_SERIALID_FREEZED_CODE_1004 = "1004";
	/**
	 * 该设备禁止访问
	 */
	public static final String E_STATUS_DEVICE_ID_CODE_1005 = "1005";


	/**
	 * 过程数据存在性(3001-3999)
	 * 序列号不存在
	 */
	public static final String E_NOT_FOUND_SERIALID_3002 = "3002";


	/**
	 * 过程数据校验(4001-4999)
	 * 用户标识错误，请稍后重试
	 */
	public static final String E_USER_ENCODE_4005 = "4005";
	/**
	 * 事件分析异常
	 * 您的事件分析数量不足，请购买后在添加
	 */
	public static final String E_EVENT_ANALYSIS_VALID_COUNT_CODE_4021 = "4021";
	/**
	 * 事件分析异常
	 * 关键词必须设置两个字以上！
	 */
	public static final String E_EVENT_KEYWORD_SIZEMIN_CODE_4022 = "4022";

	/**
	 * 事件不存在或者已被删除！
	 */
	public static final String E_EVENT_ANALYSIS_TASK_NOT_EXIST_CODE_4023 = "4023";
	/**
	 * 三次更新已用光，请建立新的全网分析
	 */
	public static final String E_EVENT_ANALYSIS_TASK_UPDATED_MAX_COUNT_CODE_4024 = "4024";
	/**
	 * 没有数据，请修改关键字或事件范围!
	 */
	public static final String E_EVENT_ANALYSIS_TASK_NO_DATA_CODE_4025 = "4025";
	/**
	 * 数据量过大，请修改关键字或事件范围!
	 */
	public static final String E_EVENT_ANALYSIS_TASK_MORE_THAN_MAX_DATA_CODE_4026 = "4026";
	/**
	 * 创建事件任务必须满足两个字，您的创建不符合规范!
	 */
	public static final String E_EVENT_ANALYSIS_FORMAT_ERROE_CODE_4027 = "4027";
	/**
	 * 查询事件分析任务信息失败
	 */
	public static final String E_EVENT_ANALYSIS_TASK_REQUEST_FAILED_CODE_4028 = "4028";
	/**
	 * 该任务的上一次分析活动没有结束
	 */
	public static final String E_EVENT_ANALYSIS_LAST_TASK_NOT_FINISHED_CODE_4029 = "4029";
	/**
	 * 只能更新分析完成的监测方案
	 */
	public static final String E_EVENT_ANALYSIS_ONLY_UPDATE_FINISHED_TASK_CODE_4030 = "4030";

	/**
	 * 分析创建失败
	 */
	public static final String E_EVENT_ANALYSIS_CREATE_CODE_4031 = "4031";

	/**
	 * 事件关键词包含敏感信息
	 */
	public static final String E_EVENT_ANALYSIS_KEYWORD_CODE_4032 = "4032";


	/**
	 * 确认失败
	 */
	public static final String E_EVENT_ANALYSIS_CONFIRM_CODE_4033 = "4033";


	/**
	 * 删除任务失败
	 */
	public static final String E_EVENT_ANALYSIS_DELETE_CODE_4034 = "4034";

}
