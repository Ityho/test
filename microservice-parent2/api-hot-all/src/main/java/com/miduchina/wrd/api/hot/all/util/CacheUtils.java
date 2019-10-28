package com.miduchina.wrd.api.hot.all.util;

import com.alibaba.fastjson.JSON;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.ownutils.ExceptionUtils;
import com.xd.tools.ownutils.MeUtils;
import com.xd.tools.pojo.AnalysisTask;
import com.xd.tools.pojo.AnalysisTaskTagRdfx;
import com.xd.tools.pojo.Params;
import com.xd.tools.view.BaseView;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.LinkedHashMap;

/**
 * lkc 生成缓存utils
 */
public class CacheUtils {

	/**
	 * 工作实现方法
	 * 
	 * @param <T>
	 */
	public static interface Work<T extends BaseView> {

		// 实现方法
		// public T run(Params params) throws Throwable;

		public T run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx) throws Throwable;

	}

	private static String filePath2 = "/home/web/webfiles/files.sjfx.wyq.cn/rd_all/";

	/**
	 * 生成缓存
	 * 
	 * @param cacheFile
	 * @param ticket
	 * @param run
	 * @param           <T>
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <T extends BaseView> T doWordAndCache(HttpServletRequest request, HttpServletResponse response,
                                                        String userTag, File cacheFile, String ticket, Class<T> returnClass, Work<T> run, String function,
                                                        LinkedHashMap<String, Object> logMap) throws InstantiationException, IllegalAccessException {

		T view = returnClass.newInstance();
		String readFileJson = MeUtils.readFile(cacheFile);
		BaseView baseView = new BaseView();
		if (StringUtils.isNotEmpty(readFileJson)) {
			baseView = JSON.parseObject(readFileJson, BaseView.class);
		}
		if (!cacheFile.exists() || (StringUtils.isNotEmpty(readFileJson)
				&& !ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(baseView.getCode()))) {
			try {
				logMap.put("process", "inHand");
				// 最大热度值文件
				File maxTotalDayfile = MeUtils.getFile(null, null, filePath2, ticket, "maxTotalDay");
				if (!maxTotalDayfile.exists()) {
					logMap.put("process", "====================最大热度值文件maxTotalDay文件不存在!!!");
					MeUtils.error(function, logMap);
					// return null;
				}
				// 获取全年热度最高的一天
				String dayStr = MeUtils.readFile(maxTotalDayfile);
				logMap.put("maxTotalDay", dayStr);
				String startTime = dayStr + " 00:00:00";
				String endTime = dayStr + " 23:59:59";
				Params paramsTemp = new Params();
				paramsTemp.setStartTime(startTime);
				paramsTemp.setEndTime(endTime);

				AnalysisTask analysisTask = MyUtil.getAnalysisTask(userTag, ticket, function, logMap);
				String paramsJson = MyUtil.getParamsJson(analysisTask, function, logMap);
				Params params = MyUtil.getParams(paramsJson, function, logMap);
				/*
				 * 个性化参数获取并转对象
				 */
				String analysisTaskTagRdfxJson = MyUtil.getAnalysisTaskTagRdfxJson(analysisTask, function, logMap);
				AnalysisTaskTagRdfx analysisTaskTagRdfx = MyUtil.getAnalysisTaskTagRdfx(analysisTaskTagRdfxJson,
						AnalysisTaskTagRdfx.class, function, logMap);
				if (params != null) {
					// 按照来源统计
					view = run.run(params, paramsTemp, analysisTaskTagRdfx);
					logMap.put("process", "网关接口请求完毕");
					logMap.put("view", "null");
					if (view != null) {
						String json = JSON.toJSONString(view);
						MeUtils.createFile(request, response, cacheFile, json);
						if (json.length() > 3000) {
							json = json.substring(0, 3000) + "......";
						}
						logMap.put("view", json);
					}
					MeUtils.info(function, logMap);
					logMap.remove("view");
				}
			} catch (Throwable e) {
				try {
					view = returnClass.newInstance();
				} catch (Exception e1) {
					e = e1;
				}
				view.setMessage(view.getMessage());
				logMap.put("message", ExceptionUtils.exceptionToString(e));

				e.printStackTrace();
			}
			MeUtils.info(function, logMap);
		} else {
			String json = MeUtils.readFile(cacheFile);
			view = JSON.parseObject(json, returnClass);
		}

		return view;

	}

}
