package com.miduchina.wrd.eventanalysis.service.impl;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.user.UserCreateDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.dto.user.UserExclusiveChannelDto;
import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import com.miduchina.wrd.eventanalysis.base.BaseService;
import com.miduchina.wrd.eventanalysis.config.WyqBusinessConfig;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.log.model.UserRes;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.service.UserCenterService;
import com.miduchina.wrd.eventanalysis.utils.LoginUtils;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.util.MD5Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserCenterServiceImpl extends BaseService implements UserCenterService {
    @Autowired
    OrderClientService orderService;
    
    @Autowired
    APIInfoClient apiInfoClient;


    @Override
    public UserDto doRegByMobile(HttpServletRequest req, HttpServletResponse resp, UserDto user, String channel, Integer inviteUserId) {
        if(user != null){
            //设置用户基础属性
            user.setUserHead(SysConfig.cfgMap.get("FILE_VIEW_PATH") + "user_head/default.jpg");
            user.setAppUserStatus(BusinessConstant.PLATFORM_TYPE_WYQ_H5);
            if (StringUtils.isNotBlank(user.getPassword())){
                user.setPassword(MD5Utils.md5(user.getPassword()));
            }else{
                user.setPassword(MD5Utils.md5(MD5Utils.md5(user.getMobile().substring(user.getMobile().length()-6,user.getMobile().length()))));
            }
            if(StringUtils.isNotBlank(user.getMobile())){
                if(StringUtils.isBlank(user.getNickname())){
                    user.setNickname(user.getMobile());
                }
                if(StringUtils.isBlank(user.getUsername())){
                    user.setUsername(user.getMobile());
                }
            }
            UserCreateDto vo = new UserCreateDto();
            try {
                BeanUtils.copyProperties(vo, user);
            } catch (IllegalAccessException | InvocationTargetException e1) {
                System.out.println("doRegByMobile() userView.getUserInfo() error ==>>"+e1.getMessage());
            }

            //赠送产品ID
            List<ProductPackage> list = orderService.findProductPackageByType(req,BusinessConstant.PACKAGE_TYPE_FREE);
            if(CollectionUtils.isNotEmpty(list)){
                vo.setGiftPackageId(list.get(0).getProductPackageId());
            }
            if(channel != null){
                vo.setExclusiveChannelCode(channel);
            }
            if (null!=inviteUserId && inviteUserId>0) {
                vo.setUserChannel(BusinessConstant.USER_CHANNEL_INVITE);
                vo.setInviteUserId(inviteUserId);
            }else{
                vo.setUserChannel(BusinessConstant.USER_CHANNEL_WYQ);
            }
            //注册后登录
            vo.setLogin(true);
            // 组装参数
            Map<String,Object> params = WyqBusinessConfig.getDataInitMap();
            params.put("userParam", JSON.toJSONString(vo));
            // 加密
            params.put("platform", BusinessConstant.PLATFORM_H5);
            String jsonStr = Utils.sendWrdIntraBusinessAPIPOST (req, "userCreateMobile", params);
            System.out.println("doRegByMobile().jsonStr ==>>"+jsonStr);
            if(StringUtils.isNotBlank(jsonStr)){
                UserRes userRes = JSON.parseObject(jsonStr,UserRes.class);
                if(userRes != null && "0000".equals(userRes.getCode()) && userRes.getUserInfo() != null){
                    UserDto u = new UserDto();
                    try {
                        LoginUtils.saveLoginFlag(req, resp, userRes);
                        BeanUtils.copyProperties(u, userRes.getUserInfo());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println("doRegByMobile() userView.getUserInfo() error ==>>"+e.getMessage());
                        e.printStackTrace();
                    }
                    return u;
                }
            }
        }
        return null;
    }
    @Override
    public int doRegister(UserDto user,HttpServletRequest request) {
        System.out.println("doRegister().partook cookies ="+JSON.toJSONString(request.getCookies()));
        String code = null;
        // 判断分享计划
        if (request.getCookies() != null && request.getCookies().length > 0) {
            for (Cookie cookie : request.getCookies()) {
                if ((BusinessConstant.UEC_CHANNEL_CODE+"_"+BusinessConstant.UEC_CHANNEL_GR).equals(cookie.getName())) {
                    code = cookie.getValue();
                    System.out.println("doRegister().partook code ="+JSON.toJSONString(code));
                    if (StringUtils.isNotBlank(code)) {
                        BaseDto<UserExclusiveChannelDto> baseDto=apiInfoClient.queryOneUserChannel(code);
                        if(baseDto!=null){
                            if(baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                                UserExclusiveChannelDto dto = baseDto.getData();
                                if (dto != null) {
                                    UserExclusiveChannelRelation userExclusiveChannelRelation = new UserExclusiveChannelRelation();
                                    userExclusiveChannelRelation.setUecId(dto.getUecId());
                                    userExclusiveChannelRelation.setUserId(Integer.valueOf(user.getUserId()));
                                    userExclusiveChannelRelation.setStatus(BusinessConstant.STATUS_VALID);
                                    userExclusiveChannelRelation.setCreateTime(new Date());

                                    apiInfoClient.doSaveUserExclusiveChannelRelation(userExclusiveChannelRelation);
                                }
                            }
                        }


                    }
                    break;
                }
            }
        }
        if("54b40c256f4041018a34ab960ea34459".equals(code)){
            return -1;
        }
        return 0;
    }
}
