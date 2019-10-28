package com.miduchina.wrd.api.hot.all.controller;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.api.hot.all.executor.HotExecutor;
import com.miduchina.wrd.api.hot.all.service.HotService;
import com.miduchina.wrd.api.hot.all.util.CacheUtils;
import com.miduchina.wrd.api.hot.all.util.MyUtil;
import com.xd.intersys.common.BaseController;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.method.base.BaseMethodV1;
import com.xd.tools.method.sjfx.db.SjfxDbMethodV1;
import com.xd.tools.method.sjfx.es.time.SjfxEsTimeMethodV1;
import com.xd.tools.method.wyq.web.hot.WyqWebHotMethodV1;
import com.xd.tools.ownutils.DateUtils;
import com.xd.tools.ownutils.ExceptionUtils;
import com.xd.tools.ownutils.MeUtils;
import com.xd.tools.ownutils.StringUtil;
import com.xd.tools.pojo.*;
import com.xd.tools.support.Init;
import com.xd.tools.view.*;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.Map.Entry;
@Api(value = "HotControllerHighV2", description = "热度接口部分(第二版)")
@Controller
@RestController
@RequestMapping("api/all")
public class HotControllerHighV2 extends BaseController {

	public static void writeErrorResultMapResponse(HttpServletRequest request, HttpServletResponse response,
			Map<String, String> resultMap) {
		if (resultMap != null && !resultMap.isEmpty()) {
			String code = StringUtils.EMPTY;
			String message = StringUtils.EMPTY;
			for (Entry<String, String> entry : resultMap.entrySet()) {
				code = entry.getKey();
				message = entry.getValue();
			}
			writeErrorResponse(request, response, code, message);
		}
	}

	private String filePath2 = "/home/web/webfiles/files.sjfx.wyq.cn/rd_all/";

	// 后台任务管理
	@Autowired
	HotExecutor hotExecutor;

	// 热度service
	@Autowired
	@Qualifier("hotService")
	private HotService hotService;

	@ApiOperation(value = "生成全年热度数据缓存", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "生成全年热度数据缓存") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/all", headers = "Accept=application/json")
	@ResponseBody
	public void hotAll(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {
		Map<String, String> resultMap = new HashMap<String, String>();
		// 日志map
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "hot all() start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info("hot all", logMap);

		try {
			Set<String> ticketSet = new HashSet<String>();
			DbParams dbParams = new DbParams();
			dbParams.setAnalysisStatus(2);
			dbParams.setIsAll(2);
			dbParams.setAnalysisSource(6);
            BaseMethodV1.setPlatform(platform);
            BaseMethodV1.setChannel(channel);
			AnalysisTasksView view = SjfxDbMethodV1.analysisTaskSelect(userTag, dbParams);
			List<AnalysisTask> analysisTaskList = view.getAnalysisTaskList();
			if (CollectionUtils.isNotEmpty(analysisTaskList)) {
				for (AnalysisTask analysisTask : analysisTaskList) {
					String ticket = analysisTask.getAnalysisTaskTicket();
					ticketSet.add(ticket);
				}
			}
			if (!ticketSet.contains(analysisTaskTicket)) {
				logMap.put("process", "============================正在进行中的任务没有这个ticket, 拜拜!");
				MeUtils.info("hot all", logMap);
				return;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Init init = new Init();
		init.setPlatform(platform);
		init.setChannel(channel);
		init.setUserTag(userTag);
		// 创建一个后台任务
		hotExecutor.doCreateHotYearAllCache(init, analysisTaskTicket);
		logMap.put("process", "hot all() end");
		MeUtils.info("hot all", logMap);
		if (MeUtils.isEmpty(resultMap)) {
			resultMap.put(ErrorCodeConstant.F_OPERATE_SUCCESS,
					ErrorCodeConstant.getMsg(ErrorCodeConstant.F_OPERATE_SUCCESS));
		}
		writeErrorResultMapResponse(request, response, resultMap);
	}

	// =========================================================

	@ApiOperation(value = "媒体友好度", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "媒体友好度") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/hotMediaFriendly", headers = "Accept=application/json")
	@ResponseBody
	public StatsView hotMediaFriendly(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotMediaFriendly";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		// 运行
		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotMediaFriendly");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {

				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_ORIGINTYPE + "," + Params.STATFIELD_CUSTOMFLAG1);
					params.setOrder(Params.ORDER_KEY);
					params.setSort(Params.SORT_ASC + "");
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);

					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (Exception e) {
			MeUtils.error(e);
		}
		return view;
	}

	@ApiOperation(value = "统计来源", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "统计来源") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/origin", headers = "Accept=application/json")
	@ResponseBody
	public StatView hotOrigin(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotorigin";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);
		StatView view = new StatView();

		try {
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotOrigin");
			if (!file.exists()) {
				try {
					AnalysisTask analysisTask = MyUtil.getAnalysisTask(userTag, analysisTaskTicket, function,
							logMap);
					String paramsJson = MyUtil.getParamsJson(analysisTask, function, logMap);
					Params params = MyUtil.getParams(paramsJson, function, logMap);
					if (params != null) {
						// 按照来源统计
						params.setStatField(Params.STATFIELD_ORIGINTYPE);

						view = SjfxEsTimeMethodV1.statV1_001(analysisTask.getUserTag(), params);
						logMap.put("step", "SjfxEsTimeMethodV1.statV1_001 finished");
						logMap.put("query", view.getSearchQuery());
						MeUtils.info(function, logMap);

						// 如果请求失败
						if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
							throw new RuntimeException("statV1_001_" + view.getMessage());
						}

						String json = JSON.toJSONString(view);
						MeUtils.createFile(request, response, file, json);
					}
				} catch (Exception e) {
					view = new StatView(ErrorCodeConstant.F_OPERATE_FAILURE, 0, null);
					view.setMessage(view.getMessage());
					logMap.put("message", ExceptionUtils.exceptionToString(e));
				}
				MeUtils.info(function, logMap);
			} else {
				String json = MeUtils.readFile(file);
				view = JSON.parseObject(json, StatView.class);
			}
		} catch (Exception e) {
			MeUtils.error(e);
		}
		return view;
	}

	@ApiOperation(value = "统计省份", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "统计省份") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/province", headers = "Accept=application/json")
	@ResponseBody
	public StatView hotProvince(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {
		String function = "hotprovince";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);
		StatView view = new StatView();
		try {
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotProvince");
			if (!file.exists()) {
				try {
					AnalysisTask analysisTask = MyUtil.getAnalysisTask(userTag, analysisTaskTicket, function, logMap);
					String paramsJson = MyUtil.getParamsJson(analysisTask, function, logMap);
					Params params = MyUtil.getParams(paramsJson, function, logMap);
					if (params != null) {
						// 按照省份统计
						params.setStatField(Params.STATFIELD_PROVINCE);
						view = SjfxEsTimeMethodV1.statV1_001(userTag, params);
						logMap.put("step", "SjfxEsTimeMethodV1.statV1_001 finished");
						logMap.put("query", view.getSearchQuery());
						MeUtils.info(function, logMap);
						// 如果请求失败
						if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
							throw new RuntimeException("statV1_" + view.getMessage());
						}
						String json = JSON.toJSONString(view);
						MeUtils.createFile(request, response, file, json);
					}
				} catch (Exception e) {
					view = new StatView(ErrorCodeConstant.F_OPERATE_FAILURE, 0, null);
					view.setMessage(view.getMessage());
					logMap.put("message", ExceptionUtils.exceptionToString(e));
				}
			} else {
				String json = MeUtils.readFile(file);
				view = JSON.parseObject(json, StatView.class);
			}
		} catch (Exception e) {
			MeUtils.error(e);
		}
		return view;
	}

	/**
	 * 获取热度及热度走势图及峰值聚类列表Api
	 * 
	 * @param request
	 * @param response
	 * @param platform
	 * @param channel
	 * @param userTag
	 * @param analysisTaskTicket
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "全年热度获取热度及热度走势图 带聚类", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "全年热度获取热度及热度走势图 带聚类Api") })
	@RequestMapping(method = { RequestMethod.POST,
			RequestMethod.GET }, value = "/hotStatAndLineYear", headers = "Accept=application/json")
	@ResponseBody
	public CtksStatStatListsView hotStatAndLineYear(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Throwable {
		String function = "hotStatAndLineYear";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);
		CtksStatStatListsView view = new CtksStatStatListsView(ErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, null);
		try {
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotStatAndLineYear");
			// 最大热度值文件
			File maxTotalDayfile = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "maxTotalDay");
			// 如果没有缓存
			if (!file.exists() || file.length() <= 0) {
				AnalysisTask analysisTask = MyUtil.getAnalysisTask(userTag, analysisTaskTicket, function, logMap);
				String paramsJson = MyUtil.getParamsJson(analysisTask, function, logMap);
				Params params = MyUtil.getParams(paramsJson, function, logMap);
				if (params != null) {
					String keyword = params.getKeyword();
					String filterKeyword = params.getFilterKeyword();
					// 对关键字进行过滤
					if (!StringUtils.isEmpty(keyword)) {
						keyword = StringUtil.conversionString(keyword);
					}
					if (!StringUtils.isEmpty(filterKeyword)) {
						filterKeyword = StringUtil.conversionString(filterKeyword);
					}
					// 返回结果集
					CtksStatStatLists ctksStats = new CtksStatStatLists();
					// 指数概况
					List<CategoryTypeKeywords> ctkList = new ArrayList<CategoryTypeKeywords>();
					// 热度指数统计
					CtksStatsView ctkStatView = hotService.getHot(userTag, params);

					// 获取热度指数概况
					CategoryTypeKeywords ctk = ctkStatView.getCtksStats().getCtkList().get(0);
					ctk.setTitle(keyword);
					ctk.setKeyword(keyword);
					ctk.setFilterKeyword(filterKeyword);
					ctkList.add(ctk);
					// 获取热度指数趋势
					List<Stat> statList = ctkStatView.getCtksStats().getStatsList().get(0).getStatList();
					List<StatStatList> statStatListList = MyUtil.statListToStatStatListList(statList);
					double deltaTDay = DateUtils.deltaTDay(DateUtils.getDateFromString(params.getStartTime()),
							DateUtils.getDateFromString(params.getEndTime()));
					// 7天以内还是按照小时
					if (deltaTDay >= 7d) {
						// 将以小时为点改成日期
						statStatListList = MyUtil.formatToDay(statStatListList);
					}
					// 填充聚类
					hotService.doFillAnlyzerComputerInSide(userTag, statStatListList, params);
					// 找出热度最高的日期
					String maxDay = MyUtil.getMaxDayAadFormatDay(statStatListList);
					logMap.put("maxDay", maxDay);
					MeUtils.info(function, logMap);
					// 将热度最高的日期存到文件中
					// MeUtils.createFile(request, response, maxTotalDayfile,
					// maxDay);
					try {
						FileUtils.writeStringToFile(maxTotalDayfile, maxDay, "GBK");
					} catch (Exception e) {
						logMap.put("process", "maxDay固化文件失败,重新固化");
						MeUtils.error(function, logMap);
						maxTotalDayfile = MeUtils.getFile(request, response, filePath2, analysisTaskTicket,
								"maxTotalDay");
						MeUtils.createFile(request, response, maxTotalDayfile, maxDay);
						MeUtils.error(e);
					}
					// 填充数据
					ctksStats.setCtkList(ctkList);
					// 热度指数趋势
					List<StatStatLists> statStatListsList = new ArrayList<StatStatLists>();
					StatStatLists statStatLists = new StatStatLists();
					statStatLists.setName(keyword);
					statStatLists.setType(1);
					statStatLists.setStatStatListList(statStatListList);
					// 计算出其他值
					statStatLists = MyUtil.setStatStatLists(statStatLists);
					statStatListsList.add(statStatLists);
					ctksStats.setStatStatListsList(statStatListsList);
					view = new CtksStatStatListsView(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS, ctksStats);
					// 如果成功
					// 保存缓存文件
					String json = JSON.toJSONString(view);
					// MeUtils.createFile(request, response, file, json);
					try {
						FileUtils.writeStringToFile(file, json, "GBK");
					} catch (Exception e) {
						logMap.put("process", "固化文件失败,重新固化");
						MeUtils.error(function, logMap);
						file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotStatAndLineYear");
						MeUtils.createFile(request, response, file, json);
						MeUtils.error(e);
					}
					String viewJson = MeUtils.subStrByEnd(json, 1200);
					logMap.put("viewJson", viewJson);
					MeUtils.info(function, logMap);
				}
			} else {
				// 转为view对象直接返回
				view = JSON.parseObject(MeUtils.readFile(file), CtksStatStatListsView.class);
				logMap.put("process", "固化文件已存在,读取文件");
			}
		} catch (Exception e) {
			view = new CtksStatStatListsView(ErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, null);
			view.setMessage(e.getMessage());
			logMap.put("success", ErrorCodeConstant.F_OPERATE_FAILURE);
			logMap.put("message", ExceptionUtils.exceptionToString(e));
		} finally {
			// 记录日志
			logMap.put("process", "finally");
			MeUtils.info("hotStatAndLineYear", logMap);
		}
		return view;
	}

	@ApiOperation(value = "声量统计图  天", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "声量统计图  天") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/hotStatVolumeDay", headers = "Accept=application/json")
	@ResponseBody
	public StatView hotStatVolumeDay(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotStatVolumeDay";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatView view = new StatView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotStatVolumeDay");
			// 业务代码
			CacheUtils.Work<StatView> work = new CacheUtils.Work<StatView>() {

				@Override
				public StatView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {

					// 按照天统计
					params.setStatField(Params.STATFIELD_PUBLISHEDDAY);
					params.setOrder(Params.ORDER_KEY);
					params.setSort(Params.SORT_ASC + "");
					// 7天以内按小时
					double deltaTDay = DateUtils.deltaTDay(DateUtils.getDateFromString(params.getStartTime()),
							DateUtils.getDateFromString(params.getEndTime()));
					if (deltaTDay < 7d) {
						params.setStatField(Params.STATFIELD_PUBLISHEDHOUR);
					}

					StatView view = SjfxEsTimeMethodV1.statV1_001(userTag, params);

					LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
					logMap.put("query", view.getSearchQuery());
					MyUtil.printLog("hotStatVolumeDay", logMap, "process", "SjfxEsTimeMethodV1.statV1_001 finished",
							true);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatView.class, work,
					function, logMap);
		} catch (Exception e) {
			MeUtils.error(e);
		}
		return view;
	}

	@ApiOperation(value = "关联词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "关联词") })
	@RequestMapping(method = { RequestMethod.POST,
			RequestMethod.GET }, value = "/relatedWord", headers = "Accept=application/json")
	@ResponseBody
	public RelatedWordView relatedWord(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		RelatedWordView view = new RelatedWordView(ErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, 0, null);

		// 日志map
		String function = "relatedWord";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		try {
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "anlyzerWordCloudInSide");
			logMap.put("filePath", file.getPath());

			// 如果没有缓存
			if (!file.exists() || file.length() <= 0) {

				logMap.put("process", "inHand");
				MeUtils.info("relatedWord", logMap);

				AnalysisTask analysisTask = MyUtil.getAnalysisTask(userTag, analysisTaskTicket, function, logMap);
				String paramsJson = MyUtil.getParamsJson(analysisTask, function, logMap);
				Params params = MyUtil.getParams(paramsJson, function, logMap);
				if (params != null) {

					String keyword = params.getKeyword();
					String filterKeyword = params.getFilterKeyword();

					// 对关键字进行过滤
					if (!StringUtils.isEmpty(keyword)) {
						keyword = StringUtil.conversionString(keyword);
					}

					if (!StringUtils.isEmpty(filterKeyword)) {
						filterKeyword = StringUtil.conversionString(filterKeyword);
					}

					// 最大热度值文件
					File maxTotalDayfile = MeUtils.getFile(request, response, filePath2, analysisTaskTicket,
							"maxTotalDay");

					if (!maxTotalDayfile.exists()) {
						writeErrorResponse(request, response, ErrorCodeConstant.F_UNTREATED);
						return null;
					}

					// 获取全年热度最高的一天
					String dayStr = MeUtils.readFile(maxTotalDayfile);

					String startTime = dayStr + " 00:00:00";
					String endTime = dayStr + " 23:59:59";

					// 记录时间
					logMap.put("startTime_endTime", startTime + "_" + endTime);

					// 设置搜索参数
					params.setDate(-1);
					params.setStartTime(startTime);
					params.setEndTime(endTime);
					params.setComblineflg(Params.COMBLINEFLG_MERGE);
					params.setOrder(Params.ORDER_SIMILAR);
					params.setSort(Params.SORT_DESC + "");
					params.setPage(1);
					params.setPagesize(100);

					// 查询关联词
					CommAnalysisRelateWord wordParams = new CommAnalysisRelateWord();
					wordParams.setNum(10);
					wordParams.setFimalyNum(1);
					wordParams.setLevel(2);

					long currentTimeMillis = System.currentTimeMillis();
					view = SjfxEsTimeMethodV1.anlyzerRelatedWordInsideV1_001(userTag, (Params) params.clone(),
							wordParams);

					logMap.put("process", "SjfxEsAllMethodV1.anlyzerRelatedWordInsideV1_001() finished");
					MeUtils.showUsedTime("relatedWord", logMap, currentTimeMillis, System.currentTimeMillis());

					// 如果请求失败
					if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
						throw new RuntimeException(view.getMessage());
					}

					// 填充数量
					hotService.doFillRelatedWordNumAndRatio(userTag, analysisTaskTicket, view.getRelatedWordList(),
							(Params) params.clone(), false);

					// 保存缓存文件
					String json = JSON.toJSONString(view);
					MeUtils.createFile(request, response, file, json);

				}
				logMap.put("process", "Hand_succ");
				MeUtils.info("relatedWord", logMap);
			} else {
				// 转为view对象直接返回
				view = JSON.parseObject(MeUtils.readFile(file), RelatedWordView.class);
				logMap.put("process", "read_succ");
			}
		} catch (Exception e) {
			view = new RelatedWordView(ErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, 0, null);
			view.setMessage(e.getMessage());
			logMap.put("success", ErrorCodeConstant.F_OPERATE_FAILURE);
			logMap.put("message", ExceptionUtils.exceptionToString(e));
		} finally {
			MeUtils.info("relatedWord", logMap);
		}
		return view;
	}

	@Test
	public void test() throws Throwable {
		// com.xd.tools.ownutils.PropertiesFactory.setEnvironmentTag("beta");
		com.xd.tools.ownutils.PropertiesFactory.setEnvironmentTag("production");
		String ticket = "w1wyqrdf1604100180105181901052";
		hotStatAndLineYear(null, null, 3, 2, "123", ticket);
	}

	/**
	 * 关键词云分析
	 * 
	 * @param request
	 * @param response
	 * @param platform
	 * @param channel
	 * @param userTag
	 * @param analysisTaskTicket
	 * @return
	 */
	@ApiOperation(value = "关键词云", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "关键词云") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/wordCloud", headers = "Accept=application/json")
	@ResponseBody
	public StatView wordCloud(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {
		StatView view = new StatView(ErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, 0, null);
		// 日志map
		String function = "wordCloud";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("analysisTaskTicket", analysisTaskTicket);
		MeUtils.info(function, logMap);
		try {
			// 创建缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "wordCloud");
			// 如果没有缓存
			if (!file.exists() || file.length() <= 0) {
				AnalysisTask analysisTask = MyUtil.getAnalysisTask(userTag, analysisTaskTicket, function, logMap);
				String paramsJson = MyUtil.getParamsJson(analysisTask, function, logMap);
				Params params = MyUtil.getParams(paramsJson, function, logMap);
				if (params != null) {
					// 最大热度值文件
					File maxTotalDayfile = MeUtils.getFile(request, response, filePath2, analysisTaskTicket,
							"maxTotalDay");
					if (!maxTotalDayfile.exists()) {
						writeErrorResponse(request, response, ErrorCodeConstant.F_UNTREATED);
					} else {
						// 获取全年热度最高的一天
						String dayStr = MeUtils.readFile(maxTotalDayfile);
						String startTime = dayStr + " 00:00:00";
						String endTime = dayStr + " 23:59:59";
						// 记录时间
						logMap.put("startTime_endTime", startTime + "_" + endTime);
						// 设置搜索参数
						params.setDate(-1);
						params.setStartTime(startTime);
						params.setEndTime(endTime);
						params.setComblineflg(Params.COMBLINEFLG_MERGE);
						params.setOrder(Params.ORDER_SIMILAR);
						params.setSort(Params.SORT_DESC + "");
						params.setPage(1);
						params.setPagesize(100);
						// 查询词云
						CommAnalysisWordCloud wordParams = new CommAnalysisWordCloud();
						wordParams.setType("1,2");
						wordParams.setNum(200);
						long currentTimeMillis = System.currentTimeMillis();
						view = SjfxEsTimeMethodV1.anlyzerWordCloudInSideV1_001(userTag, params, wordParams);
						logMap.put("process", "SjfxEsAllMethodV1.anlyzerWordCloudInSideV1_001() finished");
						MeUtils.showUsedTime("wordCloud", logMap, currentTimeMillis, System.currentTimeMillis());
						// 如果请求失败
						if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
							throw new RuntimeException(view.getMessage());
						}
						// 如果成功
						// 保存缓存文件
						String json = JSON.toJSONString(view);
						MeUtils.createFile(request, response, file, json);
					}
				}
			} else {
				// 转为view对象直接返回
				view = JSON.parseObject(MeUtils.readFile(file), StatView.class);
			}
		} catch (Exception e) {
			view = new StatView(ErrorCodeConstant.T_REQUEST_HANDLE_FAILURE, 0, null);
			view.setMessage(e.getMessage());
			logMap.put("success", ErrorCodeConstant.F_OPERATE_FAILURE);
			logMap.put("message", ExceptionUtils.exceptionToString(e));
		} finally {
			MeUtils.info("/api/v2/hot/wordCloud", logMap);
		}
		return view;
	}

    @ApiOperation(value = "非微博 热门信息", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "非微博 热门信息")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/noWeiBoHotPeople", headers = "Accept=application/json")
    @ResponseBody
    public IContentCommonNetView noWeiBoHotMessage(HttpServletRequest request, HttpServletResponse response,
                                                   @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                                   @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                                   @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                                   @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {
        //非微博  热门信息
        IContentCommonNetView view = WyqWebHotMethodV1.mediaViewPoint(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = "微博 热门信息", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "微博 热门信息")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yesWeiBoHotPeople", headers = "Accept=application/json")
    @ResponseBody
    public IContentCommonNetView yesWeiBoHotPeople(HttpServletRequest request, HttpServletResponse response,
                                                   @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                                   @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                                   @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                                   @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {
        //微博  热门信息
        IContentCommonNetView view = WyqWebHotMethodV1.wbViewPoint(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = "活跃媒体", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "活跃媒体")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yearLiveMedia", headers = "Accept=application/json")
    @ResponseBody
    public StatView yearLiveMedia(HttpServletRequest request, HttpServletResponse response,
                                                   @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                                   @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                                   @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                                   @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {
	    userTag = "rdfx-"+userTag;
        StatView view = WyqWebHotMethodV1.hotCaptureWebsite(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = "热点网民", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "热点网民")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yearHotPeople", headers = "Accept=application/json")
    @ResponseBody
    public IContentCommonNetView yearHotPeople(HttpServletRequest request, HttpServletResponse response,
                                  @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                  @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                  @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                  @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {

        IContentCommonNetView view = WyqWebHotMethodV1.hotNetizen(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = "用户情绪洞察（性别）", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "用户情绪洞察（性别）")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yearMoodSix", headers = "Accept=application/json")
    @ResponseBody
    public StatsView yearMoodSix(HttpServletRequest request, HttpServletResponse response,
                                               @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                               @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                               @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                               @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {

        StatsView view = WyqWebHotMethodV1.GenderRatio(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = " 用户情绪洞察(用户认证类型)", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = " 用户情绪洞察(用户认证类型)")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yearUserAuthenType", headers = "Accept=application/json")
    @ResponseBody
    public StatsView yearUserAuthenType(HttpServletRequest request, HttpServletResponse response,
                                 @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                 @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                 @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                 @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {

        StatsView view = WyqWebHotMethodV1.hotUserVerified(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = " 用户情绪洞察(粉丝数量分布)", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "用户情绪洞察(粉丝数量分布)")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yearUserFuns", headers = "Accept=application/json")
    @ResponseBody
    public StatsView yearUserFuns(HttpServletRequest request, HttpServletResponse response,
                                        @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                        @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                        @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                        @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {

        StatsView view = WyqWebHotMethodV1.fansNumberDistribution(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = "用户情绪洞察(转发层级)", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "用户情绪洞察(转发层级)")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yearForwardHierarchy", headers = "Accept=application/json")
    @ResponseBody
    public StatsView yearForwardHierarchy(HttpServletRequest request, HttpServletResponse response,
                                  @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                  @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                  @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                  @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {

        StatsView view = WyqWebHotMethodV1.hotLevel(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = "用户情绪洞察(兴趣标签)", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "用户情绪洞察(兴趣标签)")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yearInterestLabel", headers = "Accept=application/json")
    @ResponseBody
    public StatsView yearInterestLabel(HttpServletRequest request, HttpServletResponse response,
                                          @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                          @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                          @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                          @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {

        StatsView view = WyqWebHotMethodV1.hotUserTag(userTag, analysisTaskTicket);
        return view;
    }

    @ApiOperation(value = "口碑热词", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "口碑热词")})
    @RequestMapping(method = {RequestMethod.GET,
            RequestMethod.POST}, value = "/yearMouthWords", headers = "Accept=application/json")
    @ResponseBody
    public StatsView yearMouthWords(HttpServletRequest request, HttpServletResponse response,
                                       @ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
                                       @ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
                                       @ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
                                       @ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) throws Exception {

        StatsView view = WyqWebHotMethodV1.hotWords(userTag, analysisTaskTicket);
        return view;
    }

}