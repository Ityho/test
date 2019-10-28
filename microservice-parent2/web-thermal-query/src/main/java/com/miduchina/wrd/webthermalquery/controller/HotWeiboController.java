package com.miduchina.wrd.webthermalquery.controller;


import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.hotsportview.AbilityVO;
import com.miduchina.wrd.dto.hotsportview.HotWeiBoView;
import com.miduchina.wrd.dto.hotsportview.Statuses;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.HttpRequestUtils;
import com.tuke.gospel.core.util.PaginationUtils;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: HotWeiboAction
 * @Description: 首页-热门微博
 * @author: 任汉军
 * @date: 2018年11月6日 下午1:57:33
 */
@EqualsAndHashCode(callSuper=false)
@Slf4j
@Controller
@RequestMapping("hot/weibo")
public class HotWeiboController extends BaseController{


//	private static final long serialVersionUID = 1L;
//
//	private Integer sort;
//	private String startTime;
//	private String endTime;
//	private Integer importantEventId;
//	private String keyword;
//	private String filterKeyword;
//	private Integer switchRatioDay = 1;
//
//	private Integer numb;//page
//	private int flag;
//	private Integer flag1TimeType;//1:24小时 2：72小时
//	private String type;
//	private String label;
//	private Date createTime;
//	private String province = "全国";
//	private String searchName;
//	private Integer hotPhenomenoneId;
//	private String categoryId;
//	private String rankingType;
//	private Integer time;
//	private List<AbilityVO> abilityVOList;
//	Map<String, Object> mapData = new HashMap<String, Object>();
//	private int pageNum;
//	private List<Statuses> statuses;
//	private List<HotIncident> hotIncidentList;
//	private List<HotPhenomenone> hotPhenomenone;
//	private List<ImportantEvent> importantEvent;
//	private List<HotWord> hotWord;
//	private String durTime;
//    @Resource(name = "jsonRedisTemplate")
//    private RedisTemplate redisTemplate;
	@GetMapping("goHotWeiBoList")
	public String goHotWeiBoList(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map){
		fetchSessionAdmin();
		String startTime = DateUtils.getStringDurTime(7, "yyyy-MM-dd");
		String endTime = DateUtils.getStringDurTime(0, "yyyy-MM-dd");
		String durTime = startTime + " ~ " + endTime;
//		return "goHotWeiBoList";
		String url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
		map.put("sysConfig",System.currentTimeMillis());
		map.put("url",url);
		map.put("request",request);
		UserDto userDto = new UserDto();
		userDto.setProUserValidEndTime(new Date());
		userDto.setEmail("sfsaf");
		userDto.setNickname("wew");
		userDto.setUserId(1249);
		userDto.setUsername("wer");
		userDto.setNoUseKeywordCount(1);
		userDto.setUserAnalysisValidCount(1);
		userDto.setUserWeiboAnalysisValidCount(1);
		userDto.setUserBriefValidCount(1);
		userDto.setStopFlag(true);
		map.put("proUserValidEndTime",userDto.getProUserValidEndTime().toString());
		map.put("admin",userDto);
		map.put("nickname","7777" );
		map.put("item","7777" );
		map.put("isShowTop",1 );
		String qrCodeImg = SysConfig.cfgMap.get("QR_CODE_IMG");
		map.put("qrCodeImg",qrCodeImg );
		map.put("msg",null );
		map.put("selectLoginType",1 );
		map.put("categoryId","8698");
		map.put("numb",1);
		return "events/hotWeiBo";
	}

	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "hotWeiBoList")
	@ResponseBody
	BaseDto hotWeiBoList(Integer page,Integer pagesize,Integer flag,String type,String categoryId) {
		BaseDto bd = new BaseDto();
			Map<String, Object> resultDate = null;
			try {
				resultDate = getResuitDateTwo(page,pagesize,flag,type,categoryId);// 通過getResultDate()
				if (flag == 2) {
					// return "abilityVOList";
				} else if (flag == 1) {
					if ("1".equals(type)) { // 热门事件 行业 地域 时间
					} else if ("2".equals(type)) { // 热门现象 行业 时间
						// return "hotPhenomenone";
					} else if ("3".equals(type)) { // 重大事件 行业 地域 时间
						// return "importantEvent";
					} else if ("6".equals(type)) { // 热词 行业 时间
						// return "hotWord";
					} else if ("7".equals(type)) { // 热门微博
						// return "hotWeiBoList";
					} else if ("8".equals(type)) { // 热门人物
						// return "hotPeople";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		bd.setData(resultDate);
//			CommonUtils.writeJSON(resultDate);
		return bd;
	}
		public Map<String, Object> getResuitDateTwo(Integer page,Integer pagesize,Integer flag,String type,String categoryId){
			fetchSessionAdmin();
			Map<String, Object> mapData = new HashMap<String, Object>();
			List<Statuses> statuses=new ArrayList<>();
			String hotWBURL = "http://api.u-mei.com/sh/weibo/get_hot_weibo";
			String sessionName = getSessionName(SystemConstants.JEDIS_KEYS_HOT_WEIBO + page + categoryId);
			String json = RedisUtils.getAttribute(sessionName);
//            redisTemplate.setValueSerializer(new GenericToStringSerializer<String>(String.class));
//            String json= (String)redisTemplate.opsForValue().get(sessionName);
            pagesize=20;
			page = page == 0 ? 1 : page;
//            String json=null;
			String resWB = "";
//			if (StringUtils.isBlank(json)) {
			boolean blank = StringUtils.isBlank(json);
			boolean blank12 = StringUtils.isEmpty(json);
			boolean blank1 = StringUtils.isBlank(resWB);
			if (StringUtils.isBlank(json)) {
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("count", pagesize);
				params.put("page", page);
				params.put("category", categoryId);
				resWB = HttpRequestUtils.sendPost(hotWBURL, params);

				if (!TextUtils.isEmpty(resWB)) {
					HotWeiBoView hotWeiBoView = JSON.parseObject(resWB, HotWeiBoView.class);
					if (null != hotWeiBoView) {
//                            redisTemplate.opsForValue().set(sessionName,resWB,SystemConstants.HOT_WEIBO_TIME);
						RedisUtils.setAttribute(sessionName, resWB, SystemConstants.HOT_WEIBO_TIME);
					}
				}
			} else {
				resWB = json;
			}

			HotWeiBoView hotWeiBoView = JSON.parseObject(resWB, HotWeiBoView.class);
			if (null != hotWeiBoView) {
				int total = 200;
				int maxpage = 4;
				page = PaginationUtils.getRightPage(pagesize, page);
//				pageNum = (page - 1) * 50;
				if (hotWeiBoView.getData()!=null && hotWeiBoView.getStatuses() != null && hotWeiBoView.getStatuses().size() > 0) {
					mapData.put("message", "请求成功");
					mapData.put("code", "0000");
					statuses = hotWeiBoView.getStatuses();
				}else {
					Map<String, Object> params=new HashMap<String, Object>();
					params.put("count", pagesize);
					params.put("page", page);
					params.put("category", categoryId);
					resWB = HttpRequestUtils.sendPost(hotWBURL, params);

					if (!TextUtils.isEmpty(resWB)) {
						hotWeiBoView = JSON.parseObject(resWB, HotWeiBoView.class);
						if (null != hotWeiBoView) {
//
//                                redisTemplate.opsForValue().set(sessionName,resWB,SystemConstants.HOT_WEIBO_TIME);
							RedisUtils.setAttribute(sessionName, resWB, SystemConstants.HOT_WEIBO_TIME);
							if (hotWeiBoView.getData()!=null && hotWeiBoView.getData().getStatuses() != null && hotWeiBoView.getData().getStatuses().size() > 0) {
								mapData.put("message", "请求成功");
								mapData.put("code", "0000");
								statuses = hotWeiBoView.getData().getStatuses();
							}
						}
					}
				}

				System.out.println("resWB="+resWB);

				if (CollectionUtils.isNotEmpty(statuses)) {
					for (Statuses s : statuses) {
						s.setUrl("https://weibo.com/" + s.getUser().getUserId() + "/"
								+ s.getTrueMid());// id2Mid()
						s.setOrig_text(s.getOrig_text().trim());
//					s.setText(Utils.decode2(s.getText()));

						s.setText(s.getText().replaceAll("\u200b", ""));
//					s.setText(CommonUtils.replaceEmoji(s.getText()));
					}
				}
				mapData.put("data", statuses);
			}
			return mapData;
		}

//		public Map<String, Object> getResultDate(Integer page,Integer pagesize) {
//			fetchSessionAdmin();
//			Map<String, Object> result = new HashMap<String, Object>();
//			Map<String, Object> paramsMap = new HashMap<String, Object>();
//			page = page == 0 ? 1 : page;
//			pagesize = 20;
//			String userTag = "";
//			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//			HttpServletRequest request = servletRequestAttributes.getRequest();
////			HttpServletRequest request = ServletActionContext.getRequest();
//			if (admin != null) {
//				userTag = admin.getUserId() + "";
//			} else {
//				userTag = CommonUtils.getIp(request);
//			}
//			numb = page;
//			paramsMap.put("platform", 2);
//			paramsMap.put("channel", 2);
//			paramsMap.put("page", page);
//			paramsMap.put("userTag", userTag);
//			result.put("areaType", 1);// 国内
//			result.put("showTag", 1);// 显示
//			String url = "";
//			String hotWBURL = "";
//			// String url = SysConfig.API_RANKLIST_URL+CommonAPIConfig.getValue("HotWords");
//			if (flag == 1) {
//				List<String> llst = new ArrayList<String>();
//				if (flag1TimeType != null) {
//					if (flag1TimeType == 1) {
//						llst = getTimeByTimeType(1);
//					} else {
//						if ("1".equals(type)) {
//							llst = getTimeByTimeType(3);
//						} else {
//							llst = getTimeByTimeType(7);
//						}
//					}
//				}
//				// url = SysConfig.API_RANKLIST_URL+CommonAPIConfig.getValue("newRankingV2Hot");
//				url = "http://api.intra-ywwrd.51wyq.cn/ranklist/api/v2/list/hot";
//				// url = "http://localhost:8090/api/ranklist/api/v2/list/hot";
////			hotWBURL = SysConfig.HOT_WEIBO_API + CommonAPIConfig.getValue("newRankingHotWeiBoUrl");
//				hotWBURL = "http://api.u-mei.com/sh/weibo/get_hot_weibo";
//				;
//				if ("1".equals(type)) { // 热门事件 行业 地域 时间
//					if (StringUtils.isNoneBlank(label) && !"1".equals(label)) {
//						result.put("label", label);
//					}
//					if (StringUtils.isNoneBlank(province) && !"全国".equals(province)) {
//						result.put("province", province);
//					}
//					if (CollectionUtils.isNotEmpty(llst) && llst.size() >= 2) {
//						result.put("startTime", llst.get(0));
//						result.put("endTime", llst.get(1));
//					}
//					if (StringUtils.isNotBlank(searchName)) {
//						result.put("incidentTitle", searchName);
//					}
//					result.put("sort", 2);
//				} else if ("2".equals(type)) { // 热门现象 行业 时间
//					if (StringUtils.isNoneBlank(label) && !"1".equals(label)) {
//						result.put("label", label);
//					}
//					if (CollectionUtils.isNotEmpty(llst) && llst.size() >= 2) {
//						result.put("startTime", llst.get(0));
//						result.put("endTime", llst.get(1));
//					}
//					if (StringUtils.isNotBlank(searchName)) {
//						result.put("name", searchName);
//					}
//					if (null != hotPhenomenoneId && hotPhenomenoneId > 0) {
//						pagesize = 4;
//						type = "1";
//						result.put("hotPhenomenoneId", hotPhenomenoneId);
//					}
//				} else if ("3".equals(type)) { // 重大事件 行业 地域 时间
//					if (CollectionUtils.isNotEmpty(llst) && llst.size() >= 2) {
//						result.put("startTime", llst.get(0));
//						result.put("endTime", llst.get(1));
//					}
//					if (StringUtils.isNotBlank(searchName)) {
//						result.put("name", searchName);
//					}
//					if (null != importantEventId && importantEventId > 0) {
//						pagesize = 4;
//						type = "1";
//						result.put("importantEventId", importantEventId);
//					}
//					if (StringUtils.isNoneBlank(label) && !"1".equals(label)) {
//						result.put("label", label);
//					}
//					if (StringUtils.isNoneBlank(province) && !"全国".equals(province)) {
//						result.put("province", province);
//					}
//				} else if ("6".equals(type)) { // 热词 行业 时间
//					if (CollectionUtils.isNotEmpty(llst) && llst.size() >= 2) {
//						result.put("startTime", llst.get(0));
//						result.put("endTime", llst.get(1));
//					}
//					if (StringUtils.isNotBlank(searchName)) {
//						result.put("name", searchName);
//					}
//					if (StringUtils.isNoneBlank(label) && !"1".equals(label)) {
//						result.put("label", label);
//					}
//				} else if ("7".equals(type)) { // 热门微博
////				hotWBURL += "&count=" + pagesize + "&page=" + page + "&category=" + categoryId;
//					result.put("sort", 2);
//				}
//				result.put("type", type);
//				paramsMap.put("objStr", JSONObject.toJSONString(result));
//			} else if (flag == 2) {
//				paramsMap.put("rankingType", rankingType);
//				paramsMap.put("time", time);
//				if (StringUtils.isNotBlank(searchName)) {
//					paramsMap.put("keyword", searchName);
//				}
//			}
//			paramsMap.put("pagesize", pagesize);
//			try {
//				if (flag == 2) {
//					// abilityVOList = (List<AbilityVO>) pageVo.getData();
//					abilityVOList = getAbilityVolistData(rankingType);
//					mapData.put("data", abilityVOList);
//				} else if (flag == 1 && "7".equals(type)) {// 热门微博
//					String sessionName = getSessionName(SystemConstants.JEDIS_KEYS_HOT_WEIBO + page + categoryId);
//					String json = RedisUtils.getAttribute(sessionName);
////				json=null;
//					String resWB = "";
//					if (StringUtils.isBlank(json)) {
//						Map<String, Object> params=new HashMap<String, Object>();
//						params.put("count", pagesize);
//						params.put("page", page);
//						params.put("category", categoryId);
//						resWB = HttpRequestUtils.sendPost(hotWBURL, params);
//
//						if (!TextUtils.isEmpty(resWB)) {
//							HotWeiBoView hotWeiBoView = JSON.parseObject(resWB, HotWeiBoView.class);
//							if (null != hotWeiBoView) {
//								RedisUtils.setAttribute(sessionName, resWB, SystemConstants.HOT_WEIBO_TIME);
//							}
//						}
//					} else {
//						resWB = json;
//					}
//					System.out.println("resWB="+resWB);
//					HotWeiBoView hotWeiBoView = JSON.parseObject(resWB, HotWeiBoView.class);
//					if (null != hotWeiBoView) {
//						int total = 200;
//						int maxpage = 4;
//						page = PaginationUtils.getRightPage(pagesize, page);
//						pageNum = (page - 1) * 50;
//						if (hotWeiBoView.getData()!=null && hotWeiBoView.getStatuses() != null && hotWeiBoView.getStatuses().size() > 0) {
//							mapData.put("message", "请求成功");
//							mapData.put("code", "0000");
//							statuses = hotWeiBoView.getStatuses();
//						}
//
//						if (CollectionUtils.isNotEmpty(statuses)) {
//							for (Statuses s : statuses) {
//								s.setUrl("https://weibo.com/" + s.getUser().getUserId() + "/"
//										+ s.getTrueMid());// id2Mid()
//								s.setOrig_text(s.getOrig_text().trim());
////							s.setText(Utils.decode2(s.getText()));
//
//								s.setText(s.getText().replaceAll("\u200b", ""));
//							}
//						}
//						mapData.put("data", statuses);
//					}
//				} else {
//					String res = HttpRequestUtils.sendPost(url, paramsMap);
//					PageVo pageVo = JSON.parseObject(res, PageVo.class);
//					if (null != pageVo && "0000".equals(pageVo.getCode())) {
//						int total = pageVo.getTotalCount().intValue();
//						total = total > 200 ? 200 : total;
//						if (total > 0) {
////							int maxpage = pageVo.getMaxpage();
//							int maxpage = pageVo.getMaxPage();
//							maxpage = maxpage > 4 ? 4 : maxpage;
//							page = PaginationUtils.getRightPage(pagesize, page);
//							pageNum = (page - 1) * 50;
//							if ("1".equals(type)) { // 热门事件 行业 地域 时间
//								hotIncidentList = (List<HotIncident>) pageVo.getData();
//								if (CollectionUtils.isNotEmpty(hotIncidentList)) {
//									mapData.put("data", hotIncidentList);
//								} else {
//									mapData.put("data", null);
//								}
//							} else if ("2".equals(type)) { // 热门现象 行业 时间
//								hotPhenomenone = (List<HotPhenomenone>) pageVo.getData();
//								if (CollectionUtils.isNotEmpty(hotPhenomenone)) {
//									mapData.put("data", hotPhenomenone);
//								} else {
//									mapData.put("data", null);
//								}
//							} else if ("3".equals(type)) { // 重大事件 行业 地域 时间
//								importantEvent = (List<ImportantEvent>) pageVo.getData();
//								if (CollectionUtils.isNotEmpty(importantEvent)) {
//									mapData.put("data", importantEvent);
//								} else {
//									mapData.put("data", null);
//								}
//							} else if ("6".equals(type)) { // 热词 行业 时间
//								hotWord = (List<HotWord>) pageVo.getData();
//								if (CollectionUtils.isNotEmpty(hotWord)) {
//									mapData.put("data", hotWord);
//								} else {
//									mapData.put("data", null);
//								}
//							}
//						}
//					} else {
//						mapData.put("data", null);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return mapData;
//		}

		private String getSessionName(String name) {
			StringBuilder sessionName = new StringBuilder(SystemConstants.JEDIS_KEYS_HOT_RANK);
			sessionName.append(name);
			return RedisUtils.generateJedisKey(sessionName.toString());
		}

		private List<AbilityVO> getAbilityVolistData(String type) {
			String name = "";
			String val = "";
			if ("稿源网站".equals(type)) {
				name = "新华社,人民日报,中央电视台,齐鲁晚报,解放日报,中国日报,环球时报,光明日报,福建日报,新民晚报,重庆晨报,新华日报,经济日报,湖南日报,解放军报,河南日报,大众日报,四川日报,云南日报,济南广播电视台,成都商报,宁波日报,郑州晚报,山西日报,长江日报,中国国际广播电台,南方日报,广州日报,中国青年报,新京报,三联生活周刊,金陵晚报,钱江晚报,兰州广播电视台,陕西广播电视台,华西都市报,人民日报海外版,北京青年报,南京广播电视台,成都晚报,北京晚报,中国教育报,南宁电视台,广西日报,北京晨报,重庆晚报,经济参考报,生活报,深圳特区报,杭州日报,河北广播电视台,新疆日报,瞭望东方周刊,长沙晚报,宁波广播电视台,中国税务报,天津广播电视台,河北日报,沈阳晚报,西宁人民广播电台,中华工商时报,海南日报,中国经济时报,浙江卫视,西宁晚报,武汉晚报,昆明日报,厦门广播电视台,广州市广播电视台,江苏省广播电视总台,北京日报,昆明广播电视台,广西电视台,浙江日报,黑龙江晨报,中国证券报,山西晚报,南京日报,西海都市报,银川晚报,青岛日报,北京广播电视台,太原广播电视台,贵州日报,内蒙古日报,江西日报,深圳广播电视台,成都日报,重庆日报,浙江之声,天津日报,福州日报,兰州晚报,中央人民广播电台,新华每日电讯,人民法院报,重庆电视台,合肥晚报,《网络传播》杂志,山东商报,燕赵晚报,中国纪检监察报,宁夏日报,瞭望,贵阳晚报,安徽日报,长春晚报,黑龙江日报,科技日报,《中国报道》杂志,哈尔滨日报,辽宁日报,贵阳日报,当代世界,青海广播电视台,中国民航报,今日中国,西宁电视台,法制日报,工人日报,中国妇女报,南昌晚报,武汉广播电视台,海南特区报,乌鲁木齐晚报,海口日报,哈尔滨电视台,新疆人民广播电台,大连日报,厦门日报,中国消费者报,太原晚报,新疆电视台,郑州电视台,广东广播电视台,中国绿色时报,吉林日报,郑州日报,呼和浩特市日报,兵团广播电视台,贵州广播电视台,《党建》杂志,西藏电视台,河南电视台,湖北广播电视台,甘肃日报,四川广播电视台,南宁日报,国际商报,合肥日报,大连广播电视台,青岛广播电视台,太原日报,长春日报,河南人民广播电台,福州广播电视台,人民邮电报,人民画报,内蒙古广播电视台,中国财经报,兵团日报,湖南广播电视台,宁夏广播电视总台,中国建设报,哈尔滨人民广播电台,吉林广播电视台,海口广播电视台,中国交通报,兰州日报,人民政协报,青海日报,西藏人民广播电视台,沈阳广播电视台,南昌日报,人民中国,中国质量报,上海广播电视台,呼和浩特晚报,合肥广播电视台,拉萨晚报,石家庄日报,济南日报,中国体育报,中国经济导报,国际旅游岛商报,人民公安报,中国气象报,沈阳日报,郑州人民广播电台,北京周报,《人民论坛》杂志,环球人物,西安广播电视台,中华儿女,南宁晚报,中国审计报,中国环境报,福建广播电视台,中国安全生产报,长沙广播电视台,呼和浩特广播电视台,贵阳广播电视台,杭州广播电视台,重庆人民广播电台,石家庄广播电视台,西藏日报,中国劳动保障报,中国文化报,中国商报,中国工商报,《紫光阁》杂志,中国新闻周刊,中国水利报,中国新闻社,中国经济周刊,中国海洋报,健康报,农民日报,半月谈,广西人民广播电台,安徽广播电视台,海南广播电视台,湖北日报,扬子晚报,羊城晚报,西安日报,金融时报,西安晚报,辽宁广播电视台,甘肃广播电影电视总台,中国组织人事报,中国电力报,中国医药报,长春广播电视台,中国煤炭报,山东广播电视台,云南广播电视台,银川广播电视台,南方科技报,山西广播电视台,南宁人民广播电台,滨海时报,江西广播电视台,求是,乌鲁木齐电视台,南昌广播电视台,乌鲁木齐人民广播电台,成都广播电视台,黑龙江广播电视台";
				val = "92.72,92.70,92.15,91.87,91.23,91.05,91.05,90.82,90.69,90.45,90.38,90.33,90.08,89.49,89.48,89.39,89.24,88.94,88.74,88.04,87.97,87.91,87.87,87.12,87.11,87.09,86.93,86.73,86.54,86.02,85.93,85.80,85.70,85.40,85.34,85.23,85.10,84.76,84.64,84.61,84.50,83.75,83.64,83.56,83.25,83.08,83.00,82.77,82.64,82.50,82.42,82.01,81.78,81.59,81.11,81.05,80.68,80.33,80.29,79.98,79.95,79.94,79.51,79.42,78.83,78.81,78.18,78.10,78.06,77.90,77.59,77.58,77.44,77.18,77.14,76.91,76.88,76.68,75.84,75.72,75.65,75.15,75.14,74.69,74.62,74.60,74.48,74.44,73.91,73.28,73.05,73.04,72.87,72.79,72.47,72.05,71.95,71.88,71.86,70.98,70.85,70.71,70.63,70.55,70.31,70.07,69.78,69.68,69.45,69.35,69.34,68.99,68.77,68.20,68.20,67.75,67.71,67.15,67.02,66.94,66.89,66.81,66.48,66.38,66.33,66.11,65.65,65.32,65.13,64.95,64.58,64.36,64.26,63.82,63.79,63.73,63.49,63.07,62.84,62.43,62.41,62.10,62.09,61.88,61.60,61.28,61.10,61.01,60.78,60.33,59.84,59.76,59.72,59.47,59.27,59.23,58.91,58.65,58.16,58.06,57.73,57.65,57.31,57.25,56.87,56.82,56.77,56.70,56.26,56.15,55.77,55.68,55.53,54.94,54.88,54.79,54.40,54.37,53.73,53.56,53.36,53.19,52.96,52.68,52.52,52.42,52.12,51.95,51.94,51.68,51.44,51.27,50.89,50.62,50.53,50.29,49.69,49.59,49.56,49.25,49.23,49.13,49.08,48.86,48.44,47.82,47.62,47.53,47.23,47.13,47.13,46.87,46.65,46.26,46.21,46.17,45.78,45.25,45.22,44.77,44.67,44.55,44.47,44.21,44.01,43.53,43.29,43.27,42.76,42.76,42.69,42.55,42.40,42.33,41.68,41.28,41.26,41.01,40.65,40.31,40.23,39.88,39.50,39.49,39.30,39.27,39.26,39.00,38.45";
			} else if ("中央重点新闻网站".equals(type)) {
				name = "人民网,新华网,央视网,中国新闻网,央广网,中青在线,中国网,中国青年网,中国军网,中国经济网,光明网,中国日报网,国际在线,中国台湾网,中国西藏网";
				val = "92.00,84.19,83.67,82.96,80.79,80.48,77.53,76.40,76.17,76.07,75.86,74.49,71.76,71.39,70.00";
			} else if ("城市新闻网站".equals(type)) {
				name = "青岛新闻网,名城苏州网,东莞阳光网,大洋网,胶东在线,深圳新闻网,杭州网,宜宾新闻网,温州新闻网,洛阳网,成都全搜索,中华泰山网,水母网,长江网,中原网,漳州新闻网,中国衡阳新闻网,昆明信息港,海口网,舜网,合肥在线,新疆网,泸州新闻网,沈阳网,厦门网,遂宁新闻网,延边新闻网,西安网,春城壹网,湘潭在线,南昌新闻网,泉州网,西安新闻网,石家庄新闻网,三峡宜昌网,开封网,中国兰州网,安庆新闻网,福州新闻网,星辰在线,环渤海新闻网,中国常州网,张家口新闻网,太原新闻网,海广网,佛山新闻网,贵阳网,南宁新闻网,宁德网,龙虎网,中国宁波网,一点关注,怒江大峡谷网,银川新闻网,南充新闻网,文山新闻网,邢台网,无锡新传媒网,承德新闻网,金山网,尚一网,伊犁新闻网,普洱广播网,扬州网,红山网,邯郸新闻网,苏州新闻网,环京津新闻网,客家新闻网,乐山新闻网,松花江网,北纬网,中国喀什网,中国徐州网,普洱新闻网,中国通辽网,大连天健网,秦皇岛新闻网,岳阳网,衡水新闻网,芜湖新闻网,怒江网络广播电视台,三亚新闻网,香格里拉网,香格里拉藏文网";
				val = "92.83,92.50,91.62,91.60,91.57,91.04,90.60,90.46,90.41,90.13,90.08,89.74,89.52,89.44,88.38,88.38,88.34,87.93,87.51,87.34,87.14,86.78,86.66,86.63,86.57,86.02,85.38,85.18,85.07,84.80,84.46,84.08,83.93,83.38,83.36,83.21,83.17,83.05,82.19,82.10,82.08,81.98,81.73,81.21,80.79,80.63,80.38,80.00,79.78,79.56,79.33,79.09,78.98,78.71,78.43,78.33,77.81,77.72,77.29,77.19,76.92,76.92,76.27,76.06,75.76,75.42,74.94,74.80,74.74,74.23,73.93,73.78,73.39,73.17,73.00,72.83,72.40,72.18,71.90,71.90,71.36,71.12,70.90,70.55,70.10";
			} else if ("全国行业新闻网站".equals(type)) {
				name = "环球网,中国搜索,海外网,正义网,未来网,半月谈网,中国侨网,中工网,法制网,中国警察网,环球人物网,中国质量新闻网,中国军视网,中国文明网,证券时报网,求是网,中国法院网,中国安全生产网,中国电力新闻网,中国新闻周刊网,人民铁道网,中国石化新闻网,中国商务新闻网,人民论坛网,党建网,中国民航网,中国发展网,民主与法制网,中国交通新闻网,中国教育新闻网,紫光阁网,国防部网,中国小康网,人民政协网,中国水利网,中国农业新闻网,华夏经纬网,中国组织人事报新闻网,中国文艺网,中国民族广播网,中宏网,经济视野网,健康报网,中国信息产业网,理论网,百姓生活网,中国税网,中国文化传媒网,中国金融信息网,中国经济周刊网,中国汽车报网,中国税务网,中国金融新闻网,中国农村网,神州学人网,中国工商报网,中国有色网,中国改革网,中国旅游新闻网,中国记协网,中国煤炭网,中国劳动保障新闻网,中国石油新闻中心,消费日报网,中国科技网,中国海洋在线,中国教育网络电视台,中国体育在线,中国人大网,中国商网,中国财经新闻网,中国消费网,绿色中国网络电视,中国国防动员网,国防大学国际防务学院外宣网站,中国建筑新闻网,中国工农红军长征网,中国食品药品网";
				val = "92.79,92.10,91.85,91.70,91.60,91.06,90.86,90.69,90.14,90.02,89.67,89.50,89.23,88.89,88.40,87.84,87.83,87.54,87.53,87.06,86.87,86.69,86.65,85.83,85.74,85.60,84.95,84.46,84.46,84.32,84.20,83.90,83.81,82.98,82.47,82.46,82.20,81.99,81.44,81.38,80.88,80.80,80.54,80.09,79.85,79.82,79.75,79.14,78.60,78.46,77.90,77.89,77.73,77.10,76.76,76.54,76.29,76.24,76.17,76.06,75.49,74.99,74.88,74.60,73.95,73.71,73.50,73.44,73.22,72.56,72.19,72.01,71.90,71.55,71.11,71.10,70.77,70.70";
			} else if ("省级新闻网站".equals(type)) {
				name = "东方网,华龙网,红网,大众网,澎湃新闻网,浙江在线,中国江西网,南方网,齐鲁网,四川新闻网,界面,看看新闻网,金羊网,千龙网,多彩贵州网,上观新闻,北方网,大河网,荔枝网,西部网,南海网,长城网,华声在线,封面新闻,中国江苏网,每日甘肃网,黄河新闻网,鲁网,中安在线,芒果TV网,扬子晚报网,第一财经网,云南网,今晚网,当代先锋网,新民网,周到上海网,广西网络广播电视台,山西新闻网,中国西藏新闻网,亚心网,山西网络广播电视台,四川网络广播电视台网,河北新闻网,广西新闻网,四川在线,天山网,东北新闻网,文汇网,青海新闻网,宁夏新闻网,湖北日报网,北青网,云视网,映象网,新华报业网,海南网络广播电视台,江西文明网,东北网,中国山东网,中国甘肃网,云南日报网,新蓝网,内蒙古新闻网,天津网,青海网络广播电视台,陕西传媒网,四川日报网,中国吉林网,正北方,津滨网,陕西网,北部湾在线,河北共产党员网,东南网,吉林广播网,中华先锋网,江西网络广播电视台,黑龙江新闻网,中国西部网,视界网,中国西藏之声,吉视网,乐云网,云南法治网,新疆亚欧网,青海藏语网络广播电视台,蓝网,龙广听友网,北国网,华广网,安徽网络广播电视台,湖北网络广播电视台,宁夏网,云广网,中国江西新闻网,福建网络广播电视台,中国彩虹网,海南农垦网,邻邦网,南方英文网,第六声";
				val = "92.76,92.46,92.41,92.20,91.76,91.69,90.89,90.83,90.49,90.29,90.17,89.81,89.69,89.42,89.38,89.12,89.06,88.58,88.53,88.17,87.99,87.88,87.55,87.07,86.88,86.87,86.84,86.45,86.11,85.84,85.76,85.13,84.80,84.59,84.43,84.38,84.25,84.06,83.65,83.57,83.28,83.20,83.13,83.03,82.53,81.90,81.74,81.53,81.35,81.28,81.18,80.81,80.72,80.40,79.90,79.82,79.70,79.65,79.64,79.50,79.32,79.27,79.04,78.97,78.67,78.55,78.28,78.25,78.15,78.01,77.65,77.56,77.03,76.61,76.45,76.32,76.13,76.08,75.63,75.39,75.26,74.88,74.75,74.63,74.16,74.13,73.51,73.34,73.34,73.22,72.83,72.81,72.75,72.12,71.79,71.74,71.69,71.07,71.04,70.67,70.48,70.29";
			}
			String[] na = name.split(",");
			String[] va = val.split(",");
			List<AbilityVO> items = new ArrayList<>();
			for (int i = 0; i < na.length; i++) {
				if (i < 20) {
					AbilityVO b = new AbilityVO();
					b.setName(na[i]);
					b.setProperty(va[i]);
					items.add(b);
				}
			}
			return items;
		}

		public static List<String> getTimeByTimeType(Integer num) {
			List<String> list = new ArrayList<String>();
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String endt = formatDate.format(now);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.DAY_OF_MONTH, 0 - num);
			String startt = formatDate.format(calendar.getTime());
			list.add(startt);
			list.add(endt);
			return list;
		}

		private String getUserTag() {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = servletRequestAttributes.getRequest();
//			HttpServletRequest request = ServletActionContext.getRequest();
			UserDto admin = fetchSessionAdmin();
			String userTag = "";
			if (admin != null)
				userTag = admin.getUserId().toString();
			else
				userTag = CommonUtils.getIp(request);
			return userTag;
		}
}