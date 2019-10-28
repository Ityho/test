package com.miduchina.wrd.feign;

import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.ImportantEventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: shitao
 * @date: 2019.07.30
 */

@FeignClient(value="api-ranking-list",path="/api/v1/importantEvent/")
public interface BigEventClient {

    @RequestMapping(value = "web/list",method = RequestMethod.POST)
    PageDto<ImportantEventDto> getEventList(@RequestParam Map<String, Object> params);

    @RequestMapping(value = "web/get",method = RequestMethod.POST)
    BaseDto<ImportantEventDto> getEvent(@RequestParam Map<String, Object> params);

}