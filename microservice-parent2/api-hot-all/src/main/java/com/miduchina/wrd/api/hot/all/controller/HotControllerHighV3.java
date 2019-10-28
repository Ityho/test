package com.miduchina.wrd.api.hot.all.controller;

import com.miduchina.wrd.api.hot.all.util.CacheUtils;
import com.miduchina.wrd.api.hot.all.util.EmotionNational;
import com.miduchina.wrd.api.hot.all.util.MyUtil;
import com.xd.intersys.common.BaseController;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.method.sjfx.es.time.SjfxEsTimeMethodV1;
import com.xd.tools.ownutils.DateUtils;
import com.xd.tools.ownutils.MeUtils;
import com.xd.tools.pojo.*;
import com.xd.tools.view.IContentCommonNetView;
import com.xd.tools.view.StatView;
import com.xd.tools.view.StatsView;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author luyu
 * @其实这应该是第三版,但是所有的url中v2忘了改了
 */
@Api(value = "HotControllerHighV3", description = "热度接口部分(第三版新增16个接口)")
@Controller
@RestController
@RequestMapping("/all")
public class HotControllerHighV3 extends BaseController {

	private String filePath2 = "/home/web/webfiles/files.sjfx.wyq.cn/rd_all/";

	@ApiOperation(value = "品牌关联度", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "品牌关联度") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/brandRelevancy", headers = "Accept=application/json")
	@ResponseBody
	public StatView brandRelevancy(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "brandRelevancy";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatView view = new StatView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "brandRelevancy");
			// 业务代码
			CacheUtils.Work<StatView> work = new CacheUtils.Work<StatView>() {
				@Override
				public StatView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setPagesize(500);
					CommAnalysisWordCloud commAnalysisWordCloud = new CommAnalysisWordCloud();
					commAnalysisWordCloud.setType("3"); // 类型(1:负面词 2:正面词,3:行业汽车
					commAnalysisWordCloud.setNum(100); // 100热词
					StatView view = SjfxEsTimeMethodV1.anlyzerWordCloudInSideV1_001(userTag, params,
							commAnalysisWordCloud);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatView.class, work,
					function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "关注人群情绪分布", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "关注人群情绪分布") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/emotionDistribution", headers = "Accept=application/json")
	@ResponseBody
	public StatView emotionDistribution(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "emotionDistribution";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatView view = new StatView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "emotionDistribution");
			// 业务代码
			CacheUtils.Work<StatView> work = new CacheUtils.Work<StatView>() {
				@Override
				public StatView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_EMOTION);
					StatView view = SjfxEsTimeMethodV1.statV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatView.class, work,
					function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "男女情绪占比", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "男女情绪占比") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/emotionGenderRatio", headers = "Accept=application/json")
	@ResponseBody
	public StatsView emotionGenderRatio(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "emotionGenderRatio";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "emotionGenderRatio");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_USERGENDER);
					if (StringUtils.equals("2", analysisTaskTagRdfx.getEmotionIsWb())) {
						params.setOrigin(Params.ORIGIN_WB + "");
					}
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "情绪地图", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "情绪地图") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/emotionNationalMap", headers = "Accept=application/json")
	@ResponseBody
	public StatsView emotionNationalMap(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "emotionNationalMap";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();

		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "emotionNationalMap");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					// 6种情绪地图
					params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_PROVINCE);
					if (StringUtils.equals("2", analysisTaskTagRdfx.getEmotionIsWb())) {
						params.setOrigin(Params.ORIGIN_WB + "");
					}
					StatsView viewTemp = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					List<Stats> statsList = viewTemp.getStatsList();
					HashMap<String, EmotionNational> hashMap = new HashMap<>();
					for (Stats stats : statsList) {
						List<Stat> statList = stats.getStatList();
						String emotion = stats.getName();
						if (CollectionUtils.isNotEmpty(statList)) {
							for (Stat stat : statList) {
								if (hashMap.containsKey(stat.getName())) {
									EmotionNational emotionNationalTemp = hashMap.get(stat.getName());
									if (emotionNationalTemp.getNum() < stat.getNum()) {
										emotionNationalTemp.setEmotion(emotion);
										emotionNationalTemp.setNum((int) stat.getNum());
										hashMap.put(stat.getName(), emotionNationalTemp);
									}
								} else {
									EmotionNational emotionNational = new EmotionNational();
									emotionNational.setProvince(stat.getName());
									emotionNational.setEmotion(emotion);
									emotionNational.setNum((int) stat.getNum());
									hashMap.put(emotionNational.getProvince(), emotionNational);
								}
							}
						}
					}
					List<Stat> statList = new ArrayList<Stat>();
					Stats stats = new Stats();
					stats.setName("显著情绪");
					stats.setType(7);
					for (String key : hashMap.keySet()) {
						Stat stat = new Stat();
						stat.setName(key);
						stat.setKey(hashMap.get(key).getEmotion());
						stat.setNum(hashMap.get(key).getNum());
						statList.add(stat);
					}
					stats.setStatList(statList);
					statsList.add(stats);

					StatsView view = new StatsView(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS, statsList.size(),
							statsList);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "情绪占比", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "情绪占比") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/emotionRatio", headers = "Accept=application/json")
	@ResponseBody
	public StatsView emotionRatio(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "emotionRatio";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "emotionRatio");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_CUSTOMFLAG1);
					if (StringUtils.equals("2", analysisTaskTagRdfx.getEmotionIsWb())) {
						params.setOrigin(Params.ORIGIN_WB + "");
					}
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "情绪走势", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "情绪走势") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/emotionTrend", headers = "Accept=application/json")
	@ResponseBody
	public StatsView emotionThread(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "emotionTrend";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "emotionTrend");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					// List<Stats> statsList = new ArrayList<Stats>();
					// List<KeyValueForDate> timesList =
					// SplitDateUtil.getTimes(params.getStartTime(),
					// params.getEndTime());
					if (StringUtils.equals("2", analysisTaskTagRdfx.getEmotionIsWb())) {
						params.setOrigin(Params.ORIGIN_WB + "");
					}
					double deltaTDay = DateUtils.deltaTDay(DateUtils.getDateFromString(params.getStartTime()),
							DateUtils.getDateFromString(params.getEndTime()));
					if (deltaTDay > 90d) {
						params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_PUBLISHEDMONTH);
					} else if (deltaTDay <= 90d && deltaTDay >= 7d) {
						params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_PUBLISHEDDAY);
					} else {
						params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_PUBLISHEDHOUR);
					}
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "情绪认证类型", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "情绪认证类型") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/emotionVerified", headers = "Accept=application/json")
	@ResponseBody
	public StatsView emotionVerified(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "emotionVerified";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "emotionVerified");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_USERVERIFIED + "," + Params.STATFIELD_EMOTION);
					if (StringUtils.equals("2", analysisTaskTagRdfx.getEmotionIsWb())) {
						params.setOrigin(Params.ORIGIN_WB + "");
					}
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "粉丝数量分布(区间)", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "粉丝数量分布(区间)") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/fansNumDistribution", headers = "Accept=application/json")
	@ResponseBody
	public StatsView fansNumberDistribution(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "fansNumDistribution";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "fansNumDistribution");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					List<Stats> statsList = new ArrayList<Stats>();
					LinkedHashMap<Long, Long> fansNumlinkedHashMap = MyUtil.fansNumlinkedHashMap;
					LinkedHashMap<String, String> optionsMap = MyUtil.optionsMap;
					for (Entry<String, String> entryOptions : optionsMap.entrySet()) {
						Stats stats = new Stats();
						stats.setName(entryOptions.getValue());
						List<Stat> statList = new ArrayList<Stat>();
						for (Entry<Long, Long> entry : fansNumlinkedHashMap.entrySet()) {
							params.setOptions(entryOptions.getKey());
							params.setFollowersCountStart(entry.getKey());
							params.setFollowersCountEnd(entry.getValue());
							IContentCommonNetView view = SjfxEsTimeMethodV1.searchTotalV1_001(userTag, params);
							long totalCount = view.getTotalCount();

							Stat stat = new Stat();
							stat.setName(entry.getKey() + "-" + entry.getValue());
							stat.setCount(totalCount);
							statList.add(stat);
						}
						stats.setStatList(statList);
						statsList.add(stats);
					}
					StatsView view = new StatsView(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS, statsList.size(),
							statsList);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "男女占比", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "男女占比") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/genderRatio", headers = "Accept=application/json")
	@ResponseBody
	public StatsView GenderRatio(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "GenderRatio";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "GenderRatio");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_USERGENDER + "," + Params.STATFIELD_CUSTOMFLAG1);
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "活跃媒体", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "活跃媒体") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/hotCaptureWebsite", headers = "Accept=application/json")
	@ResponseBody
	public StatView hotCaptureWebsite(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotCaptureWebsite";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);
		StatView view = new StatView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotCaptureWebsite");
			// 业务代码
            CacheUtils.Work<StatView> work = new CacheUtils.Work<StatView>() {
				@Override
				public StatView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_CAPTUREWEBSITENAME);
					params.setStatCount(10);
					StatView view = SjfxEsTimeMethodV1.statV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatView.class, work,
					function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "转发层级", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "转发层级") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/hotLevel", headers = "Accept=application/json")
	@ResponseBody
	public StatsView hotLevel(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotLevel";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotLevel");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_CUSTOMFLAG1 + "," + Params.STATFIELD_LEVEL);
					params.setStatCount(100);
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "热点网名", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "热点网名") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/hotNetizen", headers = "Accept=application/json")
	@ResponseBody
	public IContentCommonNetView hotNetizen(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotNetizen";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		IContentCommonNetView view = new IContentCommonNetView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotNetizen");
			// 业务代码
			CacheUtils.Work<IContentCommonNetView> work = new CacheUtils.Work<IContentCommonNetView>() {
				@Override
				public IContentCommonNetView run(Params params, Params paramsTemp,
						AnalysisTaskTagRdfx analysisTaskTagRdfx) throws Throwable {

					params.setComblineflg(1);
					params.setComblineField(Params.COMBLINE_FIELD_AUTHOR);
					params.setOrigin(Params.ORIGIN_ALL + "");
					params.setOrder(Params.ORDER_SIMILAR);
					params.setSort(Params.SORT_DESC + "");
					params.setPagesize(8);
					if (StringUtils.equals("2", analysisTaskTagRdfx.getEmotionIsWb())) {
						params.setOrigin(Params.ORIGIN_WB + "");
					}
					params.setStartTime(paramsTemp.getStartTime());
					params.setEndTime(paramsTemp.getEndTime());
					IContentCommonNetView view = SjfxEsTimeMethodV1.searchListV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket,
					IContentCommonNetView.class, work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	/**
	 * 兴趣标签呢
	 * 
	 * @param request
	 * @param response
	 * @param platform
	 * @param channel
	 * @param userTag
	 * @param analysisTaskTicket
	 * @return
	 */
	@ApiOperation(value = "兴趣标签", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "兴趣标签") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/hotUserTag", headers = "Accept=application/json")
	@ResponseBody
	public StatsView hotUserTag(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotUserTag";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotUserTag");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_CUSTOMFLAG1 + "," + Params.STATFIELD_USERTAG);
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "认证类型", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "认证类型") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/hotUserVerified", headers = "Accept=application/json")
	@ResponseBody
	public StatsView hotUserVerified(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotUserVerified";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotUserVerified");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_CUSTOMFLAG1 + "," + Params.STATFIELD_USERVERIFIED);
					StatsView view = SjfxEsTimeMethodV1.statStackedV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "口碑热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "口碑热词") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/hotWords", headers = "Accept=application/json")
	@ResponseBody
	public StatsView hotWords(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "hotWords";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatsView view = new StatsView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "hotWords");
			// 业务代码
			CacheUtils.Work<StatsView> work = new CacheUtils.Work<StatsView>() {
				@Override
				public StatsView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					List<Stats> statsList = new ArrayList<Stats>();
					LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
					params.setPagesize(500);
					params.setStartTime(paramsTemp.getStartTime());
					params.setEndTime(paramsTemp.getEndTime());
					CommAnalysisWordCloud commAnalysisWordCloud = new CommAnalysisWordCloud();
					commAnalysisWordCloud.setNum(9);
					commAnalysisWordCloud.setType("2");
					StatView viewTemp = SjfxEsTimeMethodV1.anlyzerWordCloudInSideV1_001(userTag, params,
							commAnalysisWordCloud);
					List<Stat> statList1 = viewTemp.getStatList();
					Stats stats = new Stats();
					stats.setName("正面口碑热词");
					stats.setType(1);
					stats.setStatList(statList1);
					statsList.add(stats);
					logMap.put("process", "正面口碑热词finished");
					MeUtils.info("hotWords", logMap);

					// 显著情绪地图
					commAnalysisWordCloud.setType("1");
					viewTemp = SjfxEsTimeMethodV1.anlyzerWordCloudInSideV1_001(userTag, params, commAnalysisWordCloud);
					List<Stat> statList2 = viewTemp.getStatList();
					stats = new Stats();
					stats.setName("负面口碑热词");
					stats.setType(2);
					stats.setStatList(statList2);
					statsList.add(stats);
					logMap.put("process", "负面口碑热词finished");
					MeUtils.info("hotWords", logMap);

					StatsView view = new StatsView(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS, statsList.size(),
							statsList);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatsView.class,
					work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return view;
	}

	@ApiOperation(value = "非微博观点,相同文章数前5", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "非微博,相同文章数前5") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/mediaViewPoint", headers = "Accept=application/json")
	@ResponseBody
	public IContentCommonNetView mediaViewPoint(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "mediaViewPoint";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		IContentCommonNetView view = new IContentCommonNetView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "mediaViewPoint");
			// 业务代码
			CacheUtils.Work<IContentCommonNetView> work = new CacheUtils.Work<IContentCommonNetView>() {
				@Override
				public IContentCommonNetView run(Params params, Params paramsTemp,
						AnalysisTaskTagRdfx analysisTaskTagRdfx) throws Throwable {

					List<String> excludeCaptureWebsiteNameList = new ArrayList<String>();
					excludeCaptureWebsiteNameList.add(String.valueOf(Params.ORIGIN_WB));
					params.setExcludeCaptureWebsiteNameList(excludeCaptureWebsiteNameList);
					params.setComblineflg(Params.COMBLINEFLG_MERGE);
					params.setOrigin("3,4,5,6,7,8,9,10,11,12");
					params.setOrder(Params.ORDER_SIMILAR);
					params.setPagesize(10);
					params.setStartTime(paramsTemp.getStartTime());
					params.setEndTime(paramsTemp.getEndTime());
					IContentCommonNetView view = SjfxEsTimeMethodV1.searchListV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket,
					IContentCommonNetView.class, work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "显著情绪", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "显著情绪") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/significantEmotion", headers = "Accept=application/json")
	@ResponseBody
	public StatView significantEmotion(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "significantEmotion";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		StatView view = new StatView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "significantEmotion");
			// 业务代码
			CacheUtils.Work<StatView> work = new CacheUtils.Work<StatView>() {
				@Override
				public StatView run(Params params, Params paramsTemp, AnalysisTaskTagRdfx analysisTaskTagRdfx)
						throws Throwable {
					params.setStatField(Params.STATFIELD_EMOTION);
					params.setStatCount(1);
					StatView view = SjfxEsTimeMethodV1.statV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket, StatView.class, work,
					function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "首发网站", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "首发网站") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/startingSite", headers = "Accept=application/json")
	@ResponseBody
	public IContentCommonNetView startingSite(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "startingSite";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		IContentCommonNetView view = new IContentCommonNetView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "startingSite");
			// 业务代码
			CacheUtils.Work<IContentCommonNetView> work = new CacheUtils.Work<IContentCommonNetView>() {
				@Override
				public IContentCommonNetView run(Params params, Params paramsTemp,
						AnalysisTaskTagRdfx analysisTaskTagRdfx) throws Throwable {

					params.setOrder(Params.ORDER_PUBLISHED);
					params.setSort("" + Params.SORT_ASC);
					params.setPagesize(1);
					IContentCommonNetView view = SjfxEsTimeMethodV1.searchListV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket,
					IContentCommonNetView.class, work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	@ApiOperation(value = "微博观点,相同文章数前5", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "微博,相同文章数前5") })
	@RequestMapping(method = { RequestMethod.GET,
			RequestMethod.POST }, value = "/api/v2/hot/wbViewPoint", headers = "Accept=application/json")
	@ResponseBody
	public IContentCommonNetView wbViewPoint(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "处理批次号") @RequestParam(required = true) String analysisTaskTicket) {

		String function = "wbViewPoint";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("process", "start");
		logMap.put("ticket", analysisTaskTicket);
		MeUtils.info(function, logMap);

		IContentCommonNetView view = new IContentCommonNetView();
		try {
			// 获取缓存文件
			File file = MeUtils.getFile(request, response, filePath2, analysisTaskTicket, "wbViewPoint");
			// 业务代码
			CacheUtils.Work<IContentCommonNetView> work = new CacheUtils.Work<IContentCommonNetView>() {
				@Override
				public IContentCommonNetView run(Params params, Params paramsTemp,
						AnalysisTaskTagRdfx analysisTaskTagRdfx) throws Throwable {
					params.setComblineflg(Params.COMBLINEFLG_MERGE);
					params.setOrigin(Params.ORIGIN_WB + "");
					params.setOrder(Params.ORDER_SIMILAR);
					params.setSort(Params.SORT_DESC + "");
					params.setPagesize(10);
					params.setStartTime(paramsTemp.getStartTime());
					params.setEndTime(paramsTemp.getEndTime());
					IContentCommonNetView view = SjfxEsTimeMethodV1.searchListV1_001(userTag, params);
					return view;
				}
			};
			view = CacheUtils.doWordAndCache(request, response, userTag, file, analysisTaskTicket,
					IContentCommonNetView.class, work, function, logMap);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}
}