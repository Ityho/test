package com.miduchina.wrd.webthermalquery.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.HttpRequestUtils;
import com.miduchina.wrd.util.MD5Utils;
import com.miduchina.wrd.vo.BaseVo;
import com.xd.tools.method.wyq.web.abilityseal.WyqWebAbilitysealMethodV1;
import com.xd.tools.pojo.CtksStats;
import com.xd.tools.pojo.Params;
import com.xd.tools.pojo.Stat;
import com.xd.tools.pojo.Stats;
import com.xd.tools.view.CtksStatsView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @ClassName: BignessEventAction
 * @Description: 首页-重大事件
 * @author: 许双龙
 * @date: 2018年11月6日 下午1:57:33
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Slf4j
@Controller
@RequestMapping("major/events")
public class MajorEventsController extends BaseController{
	
	private static final long serialVersionUID = 1L;



    @GetMapping("goBigness")
    String goBigness(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map) {
//		return Action.SUCCESS;
		String url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
		map.put("sysConfig",System.currentTimeMillis());
		map.put("url",url);
		map.put("request",request);
		UserDto userDto = new UserDto();
		userDto.setProUserValidEndTime(new Date());
		userDto.setEmail("sfsaf");
		map.put("admin",userDto.toString());
		map.put("nickname","7777" );
		map.put("item","7777" );
		String qrCodeImg = SysConfig.cfgMap.get("QR_CODE_IMG");
		map.put("qrCodeImg",qrCodeImg );
		map.put("msg",null );
		map.put("selectLoginType",1 );
        return "events/majorEvents";
    }

	/**
	 * 重大事件列表
	 * */
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getBignessEventList")
	@ResponseBody
	BaseDto getBignessEventList(String startTime,String endTime) {
		BaseDto bd = new BaseDto();
		Map<String,Object> params = new HashMap<>();
		params.put("sort", 2);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("webShow", 1);
		params.put("showTag", 1);
		params.put("labelShowTag", 0);
		String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL")+"/v1/importantEvent/web/list", params);
//		CommonUtils.writeJSONString(jsonStr);
//		bd.setData(result);
		bd= JSON.parseObject(result,BaseDto.class);
		return bd;
	}

	/**
	 * 热门事件列表
	 * */
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getHotIncidentList")
	@ResponseBody
	BaseDto getHotIncidentList(Integer sort,Integer importantEventId,String startTime,String endTime) {
		BaseDto bd = new BaseDto();
		Map<String,Object> params = new HashMap<>();
		params.put("sort", sort);
		params.put("importantEventId", importantEventId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("page", 1);
		params.put("pageSize", 4);
		params.put("showTag", 1);
		params.put("labelShowTag", 0);
		String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL")+"/v3/hotIncident/list", params);
//		CommonUtils.writeJSONString(jsonStr);
		bd.setData(result);
		return bd;
	}

	/**
	 * 热门事件走势  */
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getHotIncidentLine")
	@ResponseBody
	BaseDto getHotIncidentLine(String startTime,String endTime,String keyword,String filterKeyword,Integer switchRatioDay ) {
		BaseDto bd = new BaseDto();
		fetchSessionAdmin();
		BaseVo bv = new BaseVo();
		String sessionName = getSessionName(SystemConstants.JEDIS_KEYS_HOT_SEARCH + SystemConstants.JEDIS_KEYS_HOTTABLE, false,keyword,filterKeyword);
		String json = RedisUtils.getAttribute(sessionName);
		if(StringUtils.isNotBlank(json)) {
//			CommonUtils.writeJSONString(json);
			bd.setData(json);
			return bd;
		}
		Params params = new Params();
		params.setDate(-1);
		params.setStartTime(startTime);
		params.setEndTime(endTime);
		if(StringUtils.isNotBlank(keyword)) {
			params.setKeyword(keyword);
		}
		if(StringUtils.isNotBlank(filterKeyword)) {
			params.setFilterKeyword(filterKeyword);
		}
		CtksStatsView ctksStatsView = null;
		try {
			ctksStatsView = WyqWebAbilitysealMethodV1.hotLine(getUserTag(), params, switchRatioDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ctksStatsView == null || !CodeConstant.SUCCESS_CODE.equals(ctksStatsView.getCode())
				|| ctksStatsView.getCtksStats() == null) {
//			bv.setCode(CodeConstant.ERROR_CODE_4001);
			bv.setCode(CodeConstant.ERROR_CODE_2001);
//			CommonUtils.writeJSON(bv);
			bd.setData(bv);
			return bd;
		}
		CtksStats ctksStats = ctksStatsView.getCtksStats();
		if(CollectionUtils.isEmpty(ctksStats.getStatsList()) || CollectionUtils.isEmpty(ctksStats.getCtkList())) {
//			bv.setCode(CodeConstants.ERROR_CODE_4001);
//			CommonUtils.writeJSON(bv);
			bv.setCode(CodeConstant.ERROR_CODE_2001);
			bd.setData(bv);
			return bd;
		}
		JSONObject data = new JSONObject();
		
		List<JSONObject> hotValueList = new ArrayList<JSONObject>();
		List<Stats> statsList = ctksStats.getStatsList();
		for (Stats stats : statsList) {
			List<Stat> statList = stats.getStatList();
			int j = 0;
			List<String> dates = CommonUtils.getTimeAxis(DateUtils.parse(startTime), DateUtils.parse(endTime), 1);
			for (String date : dates) {
				JSONObject stat = new JSONObject();
				stat.put("name", date);
				//????????
				if (j < statList.size() && statList.get(j).getName().indexOf(date) >= 0) {
					stat.put("total", new DecimalFormat("##.##").format(statList.get(j).getTotal()));
					j++;
				} else {
					stat.put("total", 0);
				}
				hotValueList.add(stat);
			}
			Collections.sort(hotValueList, new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					return o1.getString("name").compareTo(o2.getString("name"));
				}
			});
			data.put("hotValueList", hotValueList);
			double allHotValue = 0;
			double maxHotValue = 0;
			String maxTime = "";
			for (JSONObject hotValue : hotValueList) {
				allHotValue += hotValue.getDoubleValue("total");
				if(hotValue.getDoubleValue("total") > maxHotValue) {
					maxHotValue = hotValue.getDoubleValue("total");
					maxTime = hotValue.getString("name");
				}
			}
			data.put("ratioHotTopTime", maxTime);
			data.put("ratioHotTopCustom", maxHotValue);
			data.put("ratioHotAvg", new DecimalFormat("##.##").format(allHotValue/hotValueList.size()));
		}
		bv.setCode(CodeConstant.SUCCESS_CODE);
		bv.setData(data);
//		RedisUtils.setAttribute(sessionName, JSON.toJSONString(bv), Constants.SESSION_TIME_TEN_MIN);
		RedisUtils.setAttribute(sessionName, JSON.toJSONString(bv), SystemConstants.SESSION_TIME_TEN_MIN);
//		CommonUtils.writeJSON(bv);
		bd.setData(bv);
		return bd;
	}
	
	private String getUserTag() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
//		HttpServletRequest request = ServletActionContext.getRequest();
		UserDto admin = fetchSessionAdmin();
		String userTag = "";
		if (admin != null)
			userTag = admin.getUserId().toString();
		else
			userTag = CommonUtils.getIp(request);
		return userTag;
	}

	/**
	 * 获取sessionName
	 *
	 * @param name
	 * @param isPrecise 是否获取精确的缓存名称（对于数据的起止时间不需要精确到分、秒的传 false）
	 */
	private String getSessionName(String name, boolean isPrecise,String keyword,String filterKeyword) {
		StringBuilder sessionName = new StringBuilder(SystemConstants.JEDIS_KEYS_OPEN_TOOLS);
		sessionName.append(name);
		sessionName.append("_" + 24);
		if(StringUtils.isNotBlank(keyword)){
			sessionName.append("_" + MD5Utils.MD5Encode(keyword));
		}
		if(StringUtils.isNotBlank(filterKeyword)){
			sessionName.append("_" + MD5Utils.MD5Encode(filterKeyword));
		}
		return RedisUtils.generateJedisKey(sessionName.toString());
	}
}