package com.miduchina.wrd.monitor.warning.constant;


public class BusinessConstant
{
	public static final String SEPARATE_CHAR_COMMA = ",";

	public static final int SERIAL_STATUS_FREEZE = 0; // 序列号冻结状态
	public static final int SERIAL_STATUS_ACTIVE = 1; // 序列号激活状态

	public static final int SEARCH_CONDITION_SAME_CONTENT_MERGE_SWITCH_ON = 1; // 相同文章-合并
	public static final int SEARCH_CONDITION_SAME_CONTENT_MERGE_SWITCH_OFF = 2; // 相同文章-不合并

	// int类型
	public static final String SEARCH_CONDITION_CONTENT_TENDENCY_ALL = "0"; // 内容属性-全部内容
	public static final String SEARCH_CONDITION_CONTENT_TENDENCY_SUSPECTED_NEGATIVE = "1"; // 内容属性-疑似负面
	public static final String SEARCH_CONDITION_CONTENT_TENDENCY_NEGATIVE = "2"; // 内容属性-负面
	public static final String SEARCH_CONDITION_CONTENT_TENDENCY_SENSITIVE = "3"; // 内容属性-敏感
	public static final String SEARCH_CONDITION_CONTENT_TENDENCY_NON_NEGATIVE = "4"; // 内容属性-正面（非负面&非疑似负面）

	public static final int SEARCH_CONDITION_SHOW_TYPE_ALL = 1; // 结果呈现-全部信息
	public static final int SEARCH_CONDITION_SHOW_TYPE_NEWEST = 2; // 结果呈现-智能推荐

	public static final int SEARCH_CONDITION_SUMMARY_TYPE_SUMMARY = 1; // 显示方式-摘要
	public static final int SEARCH_CONDITION_SUMMARY_TYPE_BRIEF = 2; // 显示方式-简要

	// int类型
	public static final int SEARCH_ORIGINAL_TYPE_ALL = 0; // 搜索内容来源-全部
	public static final int SEARCH_ORIGINAL_TYPE_WZ = 1; // 搜索内容来源-网站
	public static final int SEARCH_ORIGINAL_TYPE_LT = 2; // 搜索内容来源-论坛
	public static final int SEARCH_ORIGINAL_TYPE_BK = 3; // 搜索内容来源-博客
	public static final int SEARCH_ORIGINAL_TYPE_TB = 4; // 搜索内容来源-贴吧
	public static final int SEARCH_ORIGINAL_TYPE_WB = 5; // 搜索内容来源-微博
	public static final int SEARCH_ORIGINAL_TYPE_JW = 6; // 搜索内容来源-境外
	public static final int SEARCH_ORIGINAL_TYPE_MG = 7; // 搜索内容来源-敏感
	public static final int SEARCH_ORIGINAL_TYPE_WX = 8; // 搜索内容来源-微信

	// 搜索来源：1-舆情网站，2-预警系统，3-客户端接口
	public static final int SEARCH_SOURCE_WEB = 1; // 搜索来源-舆情网站，2-预警系统，3-客户端接口
	public static final int SEARCH_SOURCE_WARNING = 2; // 搜索来源-预警系统
	public static final int SEARCH_SOURCE_CLIENT = 3; // 搜索来源-客户端接口

	public static final String FORWARD_ORIGINAL_WEIBO_CONTENT_LINK_STR = " 【原微博】 ";
	public static final String FORWARD_WEIBO_TITLE_TAG = "【转发】 ";
	public static final String SOURCE_WEB_TAG_WEIBO = "微博";
	public static final String SOURCE_WEB_TAG_WEIBO_SINA = "新浪微博";
	public static final String SOURCE_WEB_TAG_WEIBO_TENCENT = "腾讯微博";
	public static final int WBTT_MAX_SIZE = 150;

	// 0-非微博，1-新浪微博，2-腾讯微博
	public static final int WEIBO_TYPE_FALSE = 0;
	public static final int WEIBO_TYPE_SINA = 1;
	public static final int WEIBO_TYPE_TENCENT = 2;

	// 0-非转发，1-转发
	public static final int FORWARD_TYPE_TRUE = 1;
	public static final int FORWARD_TYPE_FALSE = 0;

	// 0-非长微博，1-长微博
	public static final int LONG_LENGTH_DEFAULT = 140;
	public static final int LONG_TYPE_TRUE = 1;
	public static final int LONG_TYPE_FALSE = 0;
	
	public static final int TITLE_LENGTH_DEFAULT = 80;
	public static final String TITLE_TAIL_DEFAULT = "...";
	
	//微信跳转判断参数
	
	//热点榜分享
	public static final String WX_EVENT_SHARECODE = "1301";
	
	//日报分享
	public static final String WX_DAILY_SHARECODE = "2001";
	
	//竞品分享
	public static final String WX_PRODUCTSANALYSIS_SHARECODE = "1002";
	
	//竞品
	public static final String WX_PRODUCTSANALYSIS_HOME = "1001";
	//业务接口请求成功
	public static final String API_BUSINESS_SUCCESS = "0000";
	
	//预警数量
	public static final Integer MAX_WARNING_K = 100000;
	
}
