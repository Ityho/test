package com.miduchina.wrd.eventanalysis.controller.casebase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.po.eventanalysis.weiboevent.IContentCommonNet;
import com.miduchina.wrd.util.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther yho
 * @vreate 2019-08 16:58
 */
@Slf4j
@Controller
@RequestMapping("/casebase")
public class CaseBaseController extends BaseController {
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "index")
    String goHotEvent(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map) {
        return "view/caseBase/cbindex";
    }

    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "caseBaseDetail")
    String caseBaseDetail(HttpServletRequest request, HttpServletResponse response,String id ,Map<String,Object> map) {
        map.put("caseId",id);
        return "view/caseBase/cbdetails";
    }
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "caseBaseList")
    @ResponseBody
    PageDto caseBaseList(Integer page, Integer order, Integer sort, String label,Integer pageSize,String title) {
        PageDto bd = new PageDto();
        HashMap<String, Object> params = new HashMap<>();
        if(page!=null){
            params.put("page",page);
        }
        if(order!=null){
            params.put("order",order);
        }
        if(sort!=null){
            params.put("sort",sort);
        }
        if(pageSize!=null){
            params.put("pageSize",pageSize);
        }
        if(title!=null){
            params.put("title",title);
        }
        if(StringUtils.isNotBlank(label)){
            params.put("label",label);
        }

        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/list/casebase", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/list/casebase", params);
        bd=JSONObject.parseObject(result,PageDto.class);
        return bd;
    }

    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "caseBaseDetailResult")
    @ResponseBody
    BaseDto caseBaseDetailResult(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }

        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/see/casebase", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/see/casebase", params);
        bd=JSONObject.parseObject(result,BaseDto.class);
        return bd;
    }

    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getServer")
    @ResponseBody
    BaseDto getServer(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }
        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/chart/goMedaiFriend", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/chart/goMedaiFriend", params);
        bd=JSONObject.parseObject(result,BaseDto.class);
        return bd;
    }

    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getServerSimi")
    @ResponseBody
    BaseDto getServerSimi(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }
        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/chart/goSimilarIncidents", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/chart/goSimilarIncidents", params);
        bd=JSONObject.parseObject(result,BaseDto.class,Feature.OrderedField);
        return bd;
    }
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "ecLineInit")
    @ResponseBody
    BaseDto ecLineInit(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }
        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/chart/goVolume", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/chart/goVolume", params);
        bd=JSONObject.parseObject(result,BaseDto.class);
        return bd;
    }

    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "ecSourceInit")
    @ResponseBody
    BaseDto ecSourceInit(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }
        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/chart/goMedaiFrom", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/chart/goMedaiFrom", params);
        bd=JSONObject.parseObject(result,BaseDto.class);
        return bd;
    }
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "ecCloudInit")
    @ResponseBody
    BaseDto ecCloudInit(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }
        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/chart/goMoreWords", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/chart/goMoreWords", params);
        bd=JSONObject.parseObject(result,BaseDto.class);
        return bd;
    }
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "ecPie2Init")
    @ResponseBody
    BaseDto ecPie2Init(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }
        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/chart/goEmotionStatAnalysis", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/chart/goEmotionStatAnalysis", params);
        bd=JSONObject.parseObject(result,BaseDto.class, Feature.OrderedField);
        return bd;
    }
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "ecMapInit")
    @ResponseBody
    BaseDto ecMapInit(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }
        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/chart/goMap", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/chart/goMap", params);
        bd=JSONObject.parseObject(result,BaseDto.class);
        return bd;
    }

    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "ecLine2Init")
    @ResponseBody
    BaseDto ecLine2Init(String caseId ) {
        BaseDto bd = new BaseDto();
        HashMap<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(caseId)){
            params.put("caseId",caseId);
        }
        String result = HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_MICROSERVICE_CASEBASE_URL")+"/v1/chart/goMap", params);
//        String result = HttpRequestUtils.sendPost("http://api.intra-ywwrd.51wyq.cn/wrd/casebase/api/v1/chart/goMap", params);
        bd=JSONObject.parseObject(result,BaseDto.class, Feature.OrderedField);
        return bd;
    }

}
