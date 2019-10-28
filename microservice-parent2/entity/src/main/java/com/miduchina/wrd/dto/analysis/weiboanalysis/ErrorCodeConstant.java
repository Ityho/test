package com.miduchina.wrd.dto.analysis.weiboanalysis;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeConstant {
	public static final String T_REQUEST_HANDLE_SUCCESS = "0000";
	public static final String T_REQUEST_HANDLE_FAILURE = "9999";

	// 系统参数必要性检验
	// 101-999
	public static final String F_LACK_RIGHT_SYSTEM_PARAM = "101";
	public static final String F_LACK_RIGHT_FORMAT = "102";
	public static final String F_LACK_RIGHT_TIME_FORMAT = "103";

	// 过程数据状态校验结果
	// 1001-1999

	// 处理结果状态
	// 2001-2099
	public static final String F_OPERATE_SUCCESS = "2001";
	public static final String F_OPERATE_FAILURE = "2002";

	// 业务参数必要性检验
	// 2101-2199

	// 2201-2299
	public static final String F_LACK_NECESSARY_INPUT_TICKET = "2201";
	public static final String F_LACK_NECESSARY_INPUT_TICKETS = "2202";
	public static final String F_LACK_NECESSARY_INPUT_PLATFORMTAG = "2203";
	public static final String F_LACK_NECESSARY_INPUT_KEYWORD = "2204";

	// 2301-2399

	// 2401-2499

	// 过程数据存在性
	// 3001-3999
	public static final String F_NOT_FOUNF_TASK = "3001";
	public static final String F_TASK_INVALID_STATUS = "3002";
	public static final String F_TASK_EXIST = "3003";

	// 过程数据校验
	// 4001-4999
	public static final String F_LACK_STARTTIME_NOT_EARLIER_THAN_ENDTIME = "4001";

	public static final Map<String, String> messageMap = new HashMap<String, String>();

	static {

		messageMap.put(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS, "请求已正常响应");
		messageMap.put(ErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, "请求处理失败");

		messageMap.put(ErrorCodeConstant.F_LACK_RIGHT_SYSTEM_PARAM, "请提交正确的系统参数");
		messageMap.put(ErrorCodeConstant.F_LACK_RIGHT_FORMAT, "请提交正确的返回格式(format)");
		messageMap.put(ErrorCodeConstant.F_LACK_RIGHT_TIME_FORMAT, "请提交正确的时间格式");

		messageMap.put(ErrorCodeConstant.F_OPERATE_SUCCESS, "处理成功");
		messageMap.put(ErrorCodeConstant.F_OPERATE_FAILURE, "处理失败");

		messageMap.put(ErrorCodeConstant.F_LACK_NECESSARY_INPUT_TICKET, "请提交处理批次号(ticket)");
		messageMap.put(ErrorCodeConstant.F_LACK_NECESSARY_INPUT_TICKETS, "请提交处理批次号(们)(tickets)");
		messageMap.put(ErrorCodeConstant.F_LACK_NECESSARY_INPUT_PLATFORMTAG, "请提交平台标识(platformTag)");
		messageMap.put(ErrorCodeConstant.F_LACK_NECESSARY_INPUT_KEYWORD, "请提交事件匹配关键词(keyword)");


		messageMap.put(ErrorCodeConstant.F_NOT_FOUNF_TASK, "数据库中没有该任务");
		messageMap.put(ErrorCodeConstant.F_TASK_INVALID_STATUS, "数据库中该任务状态为无效");
		messageMap.put(ErrorCodeConstant.F_TASK_EXIST, "数据库中该任务已存在");

		messageMap.put(ErrorCodeConstant.F_LACK_STARTTIME_NOT_EARLIER_THAN_ENDTIME, "开始时间不能大于结束时间");

	}

	public static String getMsg(String code) {
		return messageMap.get(code);
	}


}
