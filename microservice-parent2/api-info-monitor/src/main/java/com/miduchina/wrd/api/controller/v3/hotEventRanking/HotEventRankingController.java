package com.miduchina.wrd.api.controller.v3.hotEventRanking;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.controller.v3.BaseController;
import com.miduchina.wrd.api.service.hotEventRanking.HotEventRankingService;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.po.ranking.OperationAdminHot;
import com.miduchina.wrd.po.ranking.OperationAdminHotContent;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by shitao on 2019-05-08 15:25.
 *
 * @author shitao
 */
@Slf4j
@Api(tags = {"热点事件排行榜"})
@RestController
@RequestMapping("api/v3/hotEventRanking")
public class HotEventRankingController extends BaseController {

    @Autowired
    private HotEventRankingService hotEventRankingService;


    @ApiOperation(value = "列表", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "列表") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "getList", headers = "Accept=application/json")
    public PageDto getList(@ApiParam(value = "筛选条件(id：id，status:有效状态,mark:(1:，0:),page:页码,pageSize:页面大小)") @RequestParam Map<String,Object> params) {

        PageDto baseDto = new PageDto();

        JSONObject jsonParams = new JSONObject(params);
        PageInfo<OperationAdminHotContent> pageInfo =  hotEventRankingService.getList(jsonParams);
        if (pageInfo != null){
            return baseDto.setList(pageInfo.getList()).setCodeMessage(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setData(null).setCodeMessage(CodeConstant.FAILURE_CODE);
    }



    @ApiOperation(value = "列表", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "列表") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "getHotList", headers = "Accept=application/json")
    public PageDto getHotList(@ApiParam(value = "筛选条件(shareCode：shareCode，status:有效状态,page:页码,pageSize:页面大小)") @RequestParam Map<String,Object> params) {

        PageDto baseDto = new PageDto();
//        if (MapUtils.isEmpty(params)) {
//            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
//        }
        JSONObject jsonParams = new JSONObject(params);
        PageInfo<OperationAdminHot> list =  hotEventRankingService.getHotList(jsonParams);
        if (list != null){
            return baseDto.setList(list.getList()).setCodeMessage(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setData(null).setCodeMessage(CodeConstant.FAILURE_CODE);
    }

}
