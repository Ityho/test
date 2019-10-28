package com.miduchina.wrd.api.controller.v1.search;

import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.search.SearchService;
import com.miduchina.wrd.dto.BaseDto;
import com.xd.tools.pojo.db.mysql.wyq.weiyuqing.H5SearchShare;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shitao on 2019-05-27 10:00.
 *
 * @author shitao
 */
@Slf4j
@Api(tags = {"搜索"})
@RestController
@RequestMapping("api/v1/search")
public class H5SearchShareController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "添加搜索share", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "添加搜索share")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "saveH5SearchShare", headers = "Accept=application/json")

    public BaseDto saveH5SearchShare(@RequestBody H5SearchShare h5SearchShare) {
        BaseDto baseDto = new BaseDto();

        if(h5SearchShare!=null){
            Boolean flag =  searchService.saveH5SearchShare(h5SearchShare);
            if(flag!=null){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
    }



    @ApiOperation(value = "更新搜索share", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "更新搜索share")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "updateH5SearchShare", headers = "Accept=application/json")
    public BaseDto updateH5SearchShare(@RequestBody H5SearchShare h5SearchShare) {
        BaseDto baseDto = new BaseDto();

        if(h5SearchShare!=null){
            Boolean flag =  searchService.updateH5SearchShare(h5SearchShare);
            if(flag!=null){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
    }



    @ApiOperation(value = "查找搜索share", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查找搜索share")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "findH5SearchShare", headers = "Accept=application/json")
    public BaseDto findH5SearchShare(@RequestParam String shareCode) {
        BaseDto baseDto = new BaseDto();

        if(StringUtils.isNotBlank(shareCode)){
            H5SearchShare share =  searchService.findH5SearchShare(shareCode);
            if(share!=null){
                baseDto.setData(share).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
    }







}
