package com.miduchina.wrd.controller;

import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.ImportantEventDto;
import com.miduchina.wrd.feign.BigEventClient;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther yho
 * @vreate 2019-10 18:55
 */
@Controller
@Slf4j
@RequestMapping("/largeScreen")
public class BigEventController {


    @Autowired
    protected BigEventClient bigEventClient;


    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "bigEventScreen")
    String bigEventScreen(HttpServletRequest request, HttpServletResponse response) {
        return "largeScreen/bigEvents";
    }

    /**
     * 重大事件列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBigEventList")
    @ResponseBody
    public PageDto<ImportantEventDto> getBigEventList(HttpServletRequest request){
        Map<String, Object> params = new HashMap<>();
        String varStr = request.getParameter("page");
        Integer varInt = 1;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("page", varInt);

        varStr = request.getParameter("pageSize");
        varInt = 5;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("pageSize", varInt);


        varStr = request.getParameter("sort");
        varInt = 2;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("sort", varInt);

        String startTime = request.getParameter("startTime");
        if (StringUtils.isNotEmpty(startTime)) {
            params.put("startTime", startTime);
        }

        String endTime = request.getParameter("endTime");
        if (StringUtils.isNotEmpty(endTime)) {
            params.put("endTime", endTime);
        }

        varStr = request.getParameter("webShow");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("webShow", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("showTag");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("showTag", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("province");
        if (StringUtils.isNotBlank(varStr) && !varStr.equals("全部") ){
            params.put("province", varStr);
        }

        varStr = request.getParameter("areaType");
        if (StringUtils.isNotBlank(varStr)){
            params.put("areaType", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("city");
        if (StringUtils.isNotBlank(varStr)){
            params.put("city", varStr);
        }

        varStr = request.getParameter("labels");
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
            if (varInt != 0) {
                if (varInt == -1){
                    params.put("areaType", 2);
                }else {
                    params.put("labels", varInt);
                }
            }
        }

        varStr = request.getParameter("labelShowTag");
        varInt = 1;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("labelShowTag", varInt);
        log.info("getBigEventList=" + params.toString());



//        List<ImportantEventDto>  eventDtoList = new ArrayList<>();
        PageDto<ImportantEventDto> pageDto =  bigEventClient.getEventList(params);
//        if (pageDto != null ){
//            List<ImportantEventDto> importantEventDtoList = JSON.parseObject(JSON.toJSONString(pageDto.getData()), new TypeReference<List<ImportantEventDto>>() {
//            });
//            pageDto.setList(importantEventDtoList);
//            pageDto.setData(importantEventDtoList);
//        }
        return pageDto;
    }
}
