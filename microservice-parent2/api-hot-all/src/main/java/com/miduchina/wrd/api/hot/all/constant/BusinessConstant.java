package com.miduchina.wrd.api.hot.all.constant;

import com.xd.tools.ownutils.GetCommon;
import com.xd.tools.ownutils.MeUtils;
import com.xd.tools.pojo.action.DirectAccount;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BusinessConstant {

	public static final int ANALYSIS_STATUS_FAIL = 0; // 处理失败
	public static final int ANALYSIS_STATUS_INHAND = 2; // 正在处理
	public static final int ANALYSIS_STATUS_PROCESSED = 4; // 处理完成
	public static final int ANALYSIS_STATUS_PROCESSING = 3; // 已处理
	public static final int ANALYSIS_STATUS_STATED = 5; // 处理完成
	public static final int ANALYSIS_STATUS_UNTREATED = 1; // 待处理

	public static final String CAPTURE_WEBSITE_NAME = "新浪微博";
	public static final String CAPTURE_WEBSITE_NAME_WX = "微信";

	public static String IMAGE_SRC = "http:\\\"?(.*?)(\\\"|>|\\\\s+)";
	// 图片正则匹配
	public static String IMAGE_TAG = "<img.*src=(.*?)[^>]*?>";

	public static List<DirectAccount> keywordFilterWbAllList = new CopyOnWriteArrayList<DirectAccount>();
	public static List<DirectAccount> KeywordFilterWxList = new CopyOnWriteArrayList<DirectAccount>();

	public static int MAX_THREAD_POOL_SIZE = MeUtils
			.string2Integer(GetCommon.getBusinessConfigMapValue("MAX_THREAD_POOL_SIZE", "100"));

	public static final int PLATFORM_WYQ = 2;
	public static final int PLATFORM_YQT = 3;

	public static final String secretKey = "midu-idba@2016";
	public static final int STATUS_INVALID = 0; // 无效

	public static final int STATUS_VALID = 1; // 有效

	public static String VIDEO_TAG = "/<a[^>]*>.+?<\\/a>/is";

}