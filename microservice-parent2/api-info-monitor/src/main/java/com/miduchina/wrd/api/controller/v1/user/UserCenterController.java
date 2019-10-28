package com.miduchina.wrd.api.controller.v1.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.user.UserCenterService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.hotspot.GiftCard;
import com.miduchina.wrd.po.hotspot.NotLoginOperateRecord;
import com.miduchina.wrd.po.ranking.Notice;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: UserCenterController
 * @Description: 用户中心-礼品卡、公告
 * @author: 许双龙
 * @date: 2019/7/17 2:49 PM
 */
@Slf4j
@RestController
@RequestMapping("api/v1/userCenter")
public class UserCenterController {
    @Autowired
    private UserCenterService userCenterService ;

    @ApiOperation(value = "查询新的公告", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "showNewNotice")
    BaseDto showNewNotice(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        Date str = jsonObject.getDate("lastLoginTime");
        if(str==null) {
            str = new Date();
        }
        List<Notice> nL= userCenterService.showNewNotice(str);
        if (nL.size()>0){
            baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(nL);
        }else if(nL==null){
            baseDto.setCode(CodeConstant.ERROR_CODE_2001).setData(nL);
        }else{
            baseDto.setCode(CodeConstant.FAILURE_CODE).setData(nL);
        }
        return baseDto;
    }
    @ApiOperation(value = "判断用户是否有可兑换礼品卡", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "queryGiftCardByUserId")
    BaseDto queryGiftCardByUserId(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("userId");
        if(StringUtils.isNotBlank(str)){
            List<GiftCard> b = userCenterService.queryGiftCardByUserId(str);
            if (b.size()>0){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作类型type参数(type:1-save，2-update ,3-select)");
        }
        return baseDto;
    }
    @ApiOperation(value = "查询用户一天登录次数", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findTodayLoginCountByUserId")
    BaseDto findTodayLoginCountByUserId(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("userId");
        Integer b=0;
        if(StringUtils.isNotBlank(str)){
           b = userCenterService.findTodayLoginCountByUserId(str);
            if (b!=0){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作类型type参数(type:1-save，2-update ,3-select)");
        }
        return baseDto;
    }
    @ApiOperation(value = "saveOrUpdateNotLoginOperateRecord", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "saveOrUpdateNotLoginOperateRecord")
    BaseDto saveOrUpdateNotLoginOperateRecord(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("notLoginOperateRecord");
        if(StringUtils.isNotBlank(str)){
            NotLoginOperateRecord nor = JSON.parseObject(str, NotLoginOperateRecord.class);
            boolean b = userCenterService.saveOrUpdateNotLoginOperateRecord(nor);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作类型type参数(type:1-save，2-update ,3-select)");
        }
        return baseDto;
    }
    @ApiOperation(value = "根据手机号查找用户", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findUserByMobile")
    BaseDto findUserByMobile(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String mobile = jsonObject.getString("mobile");
        if(StringUtils.isNotBlank(mobile)){
            UserDto userDto = userCenterService.findUserByMobile(mobile);
            if (userDto!=null){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(userDto);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作类型type参数(type:1-save，2-update ,3-select)");
        }
        return baseDto;
    }

    @ApiOperation(value = "findNotLoginOperateRecordByIpAndUA", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findNotLoginOperateRecordByIpAndUA")
    BaseDto findNotLoginOperateRecordByIpAndUA(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String ip = jsonObject.getString("ip");
        String ua = jsonObject.getString("ua");
        if(StringUtils.isNotBlank(ip)&&StringUtils.isNotBlank(ua)){
            NotLoginOperateRecord notLoginOperateRecord = userCenterService.findNotLoginOperateRecordByIpAndUA(ip,ua);
            if (notLoginOperateRecord!=null){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(notLoginOperateRecord);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作类型type参数(type:1-save，2-update ,3-select)");
        }
        return baseDto;
    }
    @ApiOperation(value = "updateNotLoginOperateRecord", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "updateNotLoginOperateRecord")
    BaseDto updateNotLoginOperateRecord(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String ip = jsonObject.getString("ip");
        String ua = jsonObject.getString("ua");
        if(StringUtils.isNotBlank(ip)&&StringUtils.isNotBlank(ua)){
            boolean b = userCenterService.updateNotLoginOperateRecord(ip,ua);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作类型type参数(type:1-save，2-update ,3-select)");
        }
        return baseDto;
    }


}
