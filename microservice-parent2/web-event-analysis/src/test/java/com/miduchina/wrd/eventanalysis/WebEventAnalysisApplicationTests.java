package com.miduchina.wrd.eventanalysis;

import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.eventanalysis.service.ProductsAnalysisService;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.util.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebEventAnalysisApplicationTests {
	@Autowired
	ProductsAnalysisService productsAnalysisService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUp(){
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
	}

	@Test
	public void contextLoads() {
		Map<String,Object> objectMap=new HashMap<>();
		objectMap.put("userId",435);
		objectMap.put("platformTag","wyq");
		String jsonS=Utils.sendIntraBusinessAPIPOST(request,"productAnalysisTotal",objectMap);
		System.out.println(jsonS);
	}
	@Test
	public void logTest(){
//		String a="你们好";
//		System.out.println("a"+a);
//		String a="AO53szrvx61tRjKQt4yggLNgZIMOlzTRwy5E__MQ9M9nyyYG6AjHLJ4Ex5wGO4bWoG2frX-u75dSGaXl13ygXBMx-e6GeVHyj_1p2NUtdY6N69sFwJS_A4cbiWMFc8Uzxg";
		String a="APVmKTZndsyZteHVCtU_zni6pI0lVZYAjgISScnl-EZryLGaGpUvyxzgs_cQO_eGTO0X7rhCl3lX717hTJAOkgS0WMApv0R-v6KMXY5yLHhBuc1Rf9D7WdKDhcqye8L3tg";
		System.out.println("a.length()="+a.length());
	}
	@Test
	public void hotEventTest(){
		String url = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL")+ "/v3/hotIncident/web/list";
		HashMap<String, Object> params = new HashMap<>(16);
		//即24小时时差
		//获取前1天的当前时间
		params.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -1)));
		//获取当天当前时间
		params.put("endTime", DateUtils.getNow());
		params.put("page", 1);
		params.put("pageSize", 20);
		params.put("showTag", 1);
		String sort = request.getParameter("sort");
		params.put("sort", sort);
		String areaType = request.getParameter("areaType");
		params.put("areaType", areaType);
		params.put("labelShowTag", 0);
		String result = Utils.sendGet(url, params);
		System.out.println("result="+result);
	}
	@Test
	public void TestPush(){
		
		System.out.println("--------------------完成------------------------");
	}

}
