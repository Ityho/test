package com.miduchina.wrd.eventanalysis.log.model;

import java.util.HashMap;
import java.util.Map;

public class MyErrorCodeConstant {
	public static final String T_REQUEST_HANDLE_SUCCESS = "0000";
	public static final String T_REQUEST_HANDLE_FAILURE = "9999";

	// 系统参数必要性检验
	// 101-999
	public static final String F_LACK_RIGHT_SYSTEM_PARAM = "101";
	public static final String F_LACK_RIGHT_FORMAT = "102";

	// 过程数据状态校验结果
	// 1001-1999
	public static final String F_LACK_INVALID_INPUT_PAGE = "1301";
	public static final String F_LACK_INVALID_INPUT_PAGE_SIZE = "1302";
	public static final String F_LACK_INVALID_INPUT_NUM = "1303";

	public static final String F_LACK_INVALID_INPUT_FIMALYNUM = "1401";

	// 处理结果状态
	// 2001-2099

	// 业务参数必要性检验
	// 2101-2199
	public static final String F_LACK_NECESSARY_INPUT_TICKET = "2101";

	// 2201-2299
	public static final String F_LACK_NECESSARY_INPUT_KEYWORD = "2202";
	public static final String F_LACK_NECESSARY_INPUT_FILTERKEYWORD = "2203";

	// 2301-2399
	public static final String F_LACK_NECESSARY_INPUT_PAGE = "2301";
	public static final String F_LACK_NECESSARY_INPUT_PAGE_SIZE = "2302";
	public static final String F_LACK_NECESSARY_INPUT_NUM = "2303";

	// 2401-2499
	public static final String F_LACK_NECESSARY_INPUT_NICKNAME = "2401";
	public static final String F_LACK_NECESSARY_INPUT_WEIBOID_ID = "2402";
	public static final String F_LACK_NECESSARY_INPUT_FIMALYNUM = "2403";

	// 过程数据存在性
	// 3001-3999

	// 过程数据校验
	// 4001-4999

	public static final Map<String, String> messageMap = new HashMap<String, String>();

	static {

		messageMap.put(MyErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS, "请求已正常响应");
		messageMap.put(MyErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, "请求处理失败");

		messageMap.put(MyErrorCodeConstant.F_LACK_RIGHT_SYSTEM_PARAM, "请提交正确的系统参数");
		messageMap.put(MyErrorCodeConstant.F_LACK_RIGHT_FORMAT, "请提交正确的返回格式(format)");

		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_TICKET, "请提交处理批次号(ticket)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_KEYWORD, "请提交事件分析中的关键词(keyword)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_FILTERKEYWORD, "请提交微博中的排除关键词(filterkeyword)");

		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_PAGE, "请提交页码数(page)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_PAGE_SIZE, "请提交每页数量(pagesize)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_NUM, "请提交返回数量(num)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_FIMALYNUM, "请提交返回词群数量(fimalyNum)");

		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_NICKNAME, "请提交微博用户昵称(nickName)");
		messageMap.put(MyErrorCodeConstant.F_LACK_NECESSARY_INPUT_WEIBOID_ID, "请提交微博ID(weiboId)");

		messageMap.put(MyErrorCodeConstant.F_LACK_INVALID_INPUT_PAGE, "页码数(page)不在有效范围内");
		messageMap.put(MyErrorCodeConstant.F_LACK_INVALID_INPUT_PAGE_SIZE, "返回每页数量(pagesize)不在有效范围内");
		messageMap.put(MyErrorCodeConstant.F_LACK_INVALID_INPUT_NUM, "返回数量(num)不在有效范围内");
		messageMap.put(MyErrorCodeConstant.F_LACK_INVALID_INPUT_FIMALYNUM, "返回词群数量(fimalyNum)不在有效范围内");

	}

	public static String getMsg(String code) {
		return messageMap.get(code);
	}
}
