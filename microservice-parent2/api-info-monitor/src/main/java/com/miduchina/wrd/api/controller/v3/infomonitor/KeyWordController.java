package com.miduchina.wrd.api.controller.v3.infomonitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.controller.v3.BaseController;
import com.miduchina.wrd.api.service.infomonitor.KeyWordService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.log.LoginLog;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.po.keyword.KeyWordLog;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/keyword")
public class KeyWordController extends BaseController {

    @Autowired
    private KeyWordService keyWordService;

    @RequestMapping("/quertyOneById")
    @ResponseBody
    public BaseDto<KeyWord> quertyOne(String keywordId){
        BaseDto<KeyWord> baseDto=new BaseDto();
        KeyWord keyWord=keyWordService.getKeyWordById(Integer.parseInt(keywordId));
        if(keyWord!=null){
            baseDto.setData(keyWord).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
        }else {
            if(TextUtils.isEmpty(keywordId)){
                baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }
    @RequestMapping("/queryByUser")
    @ResponseBody
    public BaseDto quertyByUser(String userId){
        BaseDto baseDto=new BaseDto();
        List<KeyWord> keyWordList=keyWordService.getKeyWordByUserId(Integer.parseInt(userId));
        if(keyWordList!=null){
            baseDto.setData(keyWordList).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
        }else {
            if(TextUtils.isEmpty(userId)){
                baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }
    @RequestMapping("/quertyUserPlatform")
    @ResponseBody
    public BaseDto quertyUserPlatform(Integer userId){
        BaseDto baseDto=new BaseDto();
        LoginLog loginLog=keyWordService.getLoginLogByUserId(userId);
        if(loginLog!=null){
            baseDto.setData(loginLog).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
        }else {
            if(userId==null){
                baseDto.setData(loginLog).setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
            }else {
                baseDto.setData(loginLog).setCode(CodeConstant.FAILURE_CODE).setCodeMessage("请求数据为空");
            }
        }
        return baseDto;
    }

    /**
     * 排除过期的列表
     * @param userId
     * @return
     */
    @RequestMapping("/queryByUserTime")
    public BaseDto quertyByUserTime(String userId){
        BaseDto baseDto=new BaseDto();
        List<KeyWord> keyWordList=keyWordService.selectKeyWordByUserTime(Integer.parseInt(userId));
        if(keyWordList!=null){
            baseDto.setData(keyWordList).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
        }else {
            if(TextUtils.isEmpty(userId)){
                baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }
    @ApiOperation(value = "插入or更新热度查询记录", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "saveKeyWordLog")
    public BaseDto saveKeyWordLog(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("keyWordLog");
        Integer type = jsonObject.getInteger("type");
        if(StringUtils.isNotBlank(str)&&type!=null){
            KeyWordLog oer = JSON.parseObject(str, KeyWordLog.class);
            Boolean b = keyWordService.saveKeyWordLog(oer,type);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少用户id");
        }
        return baseDto;
    }

    /**
     * 获取续费监测方案
     * @param userId
     * @param page
     * @param pageSize
     * @param expiredCondition
     * @return
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getRenewKeywordList")
    @ResponseBody
    public PageDto getRenewKeywordList(String userId,Integer page,Integer pageSize,Integer expiredCondition){
        PageDto baseDto=new PageDto();

        if (page == null){
            page = BusinessConstant.DEFAULT_PAGE;
        }
        if (pageSize == null){
            pageSize = BusinessConstant.DEFAULT_PAGE_SIZE;
        }
        if (expiredCondition == null){
            expiredCondition = 0;
        }

        PageInfo pageInfo =keyWordService.getRenewKeywordList(Integer.parseInt(userId),page,pageSize,expiredCondition);

        if (pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())) {
            baseDto.setMaxPage(Integer.valueOf(pageInfo.getPageNum()));
            baseDto.setTotalCount(pageInfo.getTotal());
            baseDto.setPage(pageInfo.getPages());
            baseDto.setPageSize(pageInfo.getPageSize());
            baseDto.setList(pageInfo.getList()).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
        }else {
            if(TextUtils.isEmpty(userId)){
                baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }


}

