package com.miduchina.wrd.api.hot.all.executor.impl;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.api.hot.all.controller.HotControllerHighV2;
import com.miduchina.wrd.api.hot.all.controller.HotControllerHighV3;
import com.miduchina.wrd.api.hot.all.executor.HotExecutor;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.common.SystemConstants;
import com.xd.tools.method.base.BaseMethodV1;
import com.xd.tools.method.sjfx.db.SjfxDbMethodV1;
import com.xd.tools.ownutils.ExceptionUtils;
import com.xd.tools.ownutils.MeUtils;
import com.xd.tools.pojo.AnalysisTask;
import com.xd.tools.pojo.AnalysisTaskTagRdfx;
import com.xd.tools.pojo.DbParams;
import com.xd.tools.support.Init;
import com.xd.tools.view.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * 热度后台线程执行类
 * 
 * @author lkc
 * @date 2017年4月12日 下午10:46:28
 * @version V1.0
 *
 */
@Configuration
@EnableAsync
public class HotExecutorImpl implements HotExecutor {

	@Autowired
	HotControllerHighV2 hotv2;

	@Autowired
	HotControllerHighV3 hotv3;

	// 保存缓存
	Set<String> ticketCacheSet = new HashSet<>();

	@Async
	@Override
	public void doCreateHotYearAllCache(Init init, String analysisTaskTicket) {

		long startTime = System.currentTimeMillis();
		String function = "hot all doCreateHotYearAllCache()";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("analysisTaskTicket", analysisTaskTicket);
		logMap.put("platform", init.getPlatform());
		logMap.put("channel", init.getChannel());
		logMap.put("userTag", init.getUserTag());

		try {
			// 设置到线程中
			Init.setThreadLocaInit(init);
			// MeUtils.setTransactionIdThreadLocal(analysisTaskTicket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MeUtils.showUsedTime(function, logMap, startTime, System.currentTimeMillis());

		// 获取用户标识
		String userTag = init.getUserTag();

		try {

			// 如果已经存在
			if (ticketCacheSet.contains(analysisTaskTicket)) {
				logMap.put("succ", "analysisTaskTicket is In the pure");
				return;
			}

			ticketCacheSet.add(analysisTaskTicket);

			long processStartTime = System.currentTimeMillis();

			// 读取任务信息
			DbParams dbPar = new DbParams();
			dbPar.setAnalysisTaskTicket(analysisTaskTicket);
			AnalysisTasksView analysisTasksView = SjfxDbMethodV1.analysisTaskSelect(userTag, dbPar);
			if (analysisTasksView == null) {
				logMap.put("process", "analysisTasksView is null");
				MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());
				throw new RuntimeException("analysisTasksView is null");
			}

			// 获取任务列表
			List<AnalysisTask> analysisTaskList = analysisTasksView.getAnalysisTaskList();
			if (CollectionUtils.isEmpty(analysisTaskList)) {

				logMap.put("process", "analysisTaskList is empty");
				MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

				throw new RuntimeException("analysisTaskList is empty");

			}

			AnalysisTask analysisTask = analysisTaskList.get(0);

			if (analysisTask == null) {
				logMap.put("process", "analysisTask is empty");
				MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());
				throw new RuntimeException("analysisTask is empty");
			}

			userTag = analysisTask.getUserTag();

			// 热度加上特殊标识(不会被拦截),否则wyq平台只能查询一个月

//			 userTag = SystemConstants.HOT_USER_TAG + userTag;
//			 userTag = SystemConstants.ANALYSIS_RDFX_USER_TAG +"-"+ userTag;

			// 刷新用户tag
			init.setUserTag(userTag);
			Init.setThreadLocaInit(init);

			Init init11 = new Init();
			init11.setUserTag(userTag);

			logMap.put("userTag", userTag);
			BaseMethodV1.setUserTag(userTag);
			BaseMethodV1.setPlatform(analysisTask.getPlatform());
			BaseMethodV1.setChannel(analysisTask.getChannel());
			BaseMethodV1.setTicket(analysisTaskTicket);

			// 获取热度及热度走势图及峰值聚类列表Api
			CtksStatStatListsView yearView = hotv2.hotStatAndLineYear(null, null, 0, 0, userTag, analysisTaskTicket);

			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(yearView.getCode())) {
				throw new RuntimeException("hotStatAndLineYear_" + yearView.getMessage());
			}

			logMap.put("process", "hotStatAndLineYear_end");
			MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

			// 统计来源
			processStartTime = System.currentTimeMillis();
			StatView originView = hotv2.hotOrigin(null, null, userTag, analysisTaskTicket);
			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(originView.getCode())) {
				throw new RuntimeException("hotOrigin_" + originView.getMessage());
			}

			logMap.put("process", "hotOrigin_end");
			MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

			// 统计省份
			processStartTime = System.currentTimeMillis();
			StatView provinceView = hotv2.hotProvince(null, null, 0, 0, userTag, analysisTaskTicket);

			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(provinceView.getCode())) {
				throw new RuntimeException("hotProvince_" + provinceView.getMessage());
			}
			logMap.put("process", "hotProvince_end");
			MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

			// 统计声量 for 天
			processStartTime = System.currentTimeMillis();
			StatView hotStatVolumeDay = hotv2.hotStatVolumeDay(null, null, 0, 0, userTag, analysisTaskTicket);

			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(hotStatVolumeDay.getCode())) {
				throw new RuntimeException("hotStatVolumeDay_" + hotStatVolumeDay.getMessage());
			}
			logMap.put("process", "hotStatVolumeDay_end");
			MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

			// 媒体友好度
			processStartTime = System.currentTimeMillis();
			StatsView hotMediaFriendly = hotv2.hotMediaFriendly(null, null, 0, 0, userTag, analysisTaskTicket);

			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(hotMediaFriendly.getCode())) {
				throw new RuntimeException("hotMediaFriendly_" + hotMediaFriendly.getMessage());
			}
			logMap.put("process", "hotMediaFriendly_end");
			MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());


//			//媒体活跃度
//			processStartTime = System.currentTimeMillis();
//            StatView yearLiveMedia = hotv2.yearLiveMedia(null, null, 0, 0, userTag, analysisTaskTicket);
//            // 如果请求失败
//            if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(yearLiveMedia.getCode())) {
//                throw new RuntimeException("yearLiveMedia_" + yearLiveMedia.getMessage());
//            }
//            logMap.put("process", "yearLiveMedia_end");
//            MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

            // 词云
			processStartTime = System.currentTimeMillis();
			StatView cloudView = hotv2.wordCloud(null, null, 0, 0, userTag, analysisTaskTicket);
			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(cloudView.getCode())) {
				throw new RuntimeException("wordCloud_" + cloudView.getMessage());
			}

			logMap.put("process", "wordCloud_end");
			MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

			// 关联词
			processStartTime = System.currentTimeMillis();
			RelatedWordView relatedWordView = hotv2.relatedWord(null, null, 0, 0, userTag, analysisTaskTicket);
			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(relatedWordView.getCode())) {
				throw new RuntimeException("wordCloud_" + relatedWordView.getMessage());
			}

			logMap.put("process", "relatedWord_end");
			MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

			/**
			 * 处理逻辑
			 */
			handle(init, analysisTask, analysisTaskTicket, function, logMap);

			// 通知处理批次号
			processStartTime = System.currentTimeMillis();
			SjfxDbMethodV1.analysisAnalysisTaskFinishAnalysis(userTag, analysisTaskTicket);
			logMap.put("process", "analysisAnalysisTaskFinishAnalysis_end");
			MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());

			logMap.put("succ", "succ");

			// 成功后删除
			ticketCacheSet.remove(analysisTaskTicket);

		} catch (Throwable e) {
			// 异常时删除
			ticketCacheSet.remove(analysisTaskTicket);
			e.printStackTrace();
			logMap.put("error", ExceptionUtils.exceptionToString(e));
			// 修改为失败
			try {
				SjfxDbMethodV1.analysisTaskSingleResetAnalysis(userTag, analysisTaskTicket);
			} catch (Exception e1) {
				logMap.put("error1", ExceptionUtils.exceptionToString(e1));
			}
		} finally {
			try {
				BaseMethodV1.setTicket(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			MeUtils.showUsedTime(function, logMap, startTime, endTime);
		}

	}

	private void handle(Init init, AnalysisTask analysisTask, String analysisTaskTicket, String function,
                        LinkedHashMap<String, Object> logMap) {
		String perParams = analysisTask.getPerParams();
		if (StringUtils.isNotEmpty(perParams)) {
			AnalysisTaskTagRdfx taskTagRdfx = JSON.parseObject(perParams, AnalysisTaskTagRdfx.class);
			String modules = taskTagRdfx.getModules();
			if (StringUtils.isNotEmpty(modules)) {
				String[] modulesArr = modules.split(",");
				int platform = init.getPlatform();
				int channel = init.getChannel();
				// String userTag = init.getUserTag();
				String userTag = analysisTask.getUserTag();
				String explain = "";

				// hotStatVolumeDay 15声量走势
				// hotStatAndLineYear 热度指数趋势

				long processStartTime = System.currentTimeMillis();
				for (String module : modulesArr) {
					switch (module) {
					case "18":
						explain = "significantEmotion";
						hotv3.significantEmotion(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "6":
						explain = "mediaViewPoint";
						hotv3.mediaViewPoint(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "7":
						explain = "wbViewPoint";
						hotv3.wbViewPoint(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "19":
						explain = "brandRelevancy";
						hotv3.brandRelevancy(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "20":
						explain = "emotionThread";
						hotv3.emotionThread(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "21":
						explain = "emotionNationalMap";
						hotv3.emotionNationalMap(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					// case "22": //
					// hotv3.emotionNationalMap(null, null, platform, channel,
					// userTag, analysisTaskTicket);
					// break;
					case "23":
						explain = "emotionDistribution";
						hotv3.emotionDistribution(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "24":
						explain = "emotionGenderRatio";
						hotv3.emotionGenderRatio(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "25":
						explain = "hotNetizen";
						hotv3.hotNetizen(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "26":
						explain = "GenderRatio";
						hotv3.GenderRatio(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "27":
						explain = "hotUserVerified";
						hotv3.hotUserVerified(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "28":
						explain = "fansNumberDistribution";
						hotv3.fansNumberDistribution(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "29":
						explain = "hotLevel";
						hotv3.hotLevel(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "30":
						explain = "hotUserTag";
						hotv3.hotUserTag(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "31":
						explain = "hotWords";
						hotv3.hotWords(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					case "35":
						explain = "startingSite";
						hotv3.startingSite(null, null, platform, channel, userTag, analysisTaskTicket);
						break;
					default:
						break;
					}
				}
				// if
				// (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(relatedWordView.getCode()))
				// {
				// throw new RuntimeException("wordCloud_" +
				// relatedWordView.getMessage());
				// }
				logMap.put("process", explain + "_end");
				MeUtils.showUsedTime(function, logMap, processStartTime, System.currentTimeMillis());
			}
		}
	}

}