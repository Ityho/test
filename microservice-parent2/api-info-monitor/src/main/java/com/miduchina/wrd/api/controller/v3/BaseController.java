package com.miduchina.wrd.api.controller.v3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.client.ClientSerialService;
import com.miduchina.wrd.api.service.user.UserService;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.user.ClientSerialDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.StringUtil;
import com.miduchina.wrd.po.user.ClientSerial;
import com.miduchina.wrd.util.BeanUtils;
import com.xd.tools.ownutils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shitao on 2019-05-10 14:03.
 *
 * @author shitao
 */

@Slf4j
public class BaseController  {


    @Autowired
    private ClientSerialService serialService;

    @Autowired
    private UserService userService;


    /**
     * 将请求request中key参数还原获取加密前内容
     * @param key
     * @return
     */
    protected String decryptKey(String key) {
        return EncryptUtil.decrypt(key);
    }

    protected BaseDto<UserDto> checkUserEncode(HttpServletRequest request, String userEncode) {

        String subUserIdStr = request.getParameter("subUserId");
        String productChannelStr = request.getParameter("productChannel");
        Integer productChannel = BusinessConstant.PLATFORM_Web;
        if (StringUtils.isNotEmpty(productChannelStr)){
            productChannel = Integer.valueOf(productChannelStr);
        }
        String sidStr = request.getParameter("sid");

        Integer subUserId = null;
        BaseDto baseDto = new BaseDto();
        if(StringUtils.isNotBlank(subUserIdStr) && Integer.valueOf(subUserIdStr) > 0 ){
            subUserId = Integer.valueOf(subUserIdStr);
            if (subUserId == 0) {
                subUserId = null;
            }
        }
        if (StringUtils.isBlank(userEncode)) {
            return baseDto.setCodeMessage(CodeConstant.E_NECESSARY_USERENCODE_CODE_104);
        }

        int userId = StringUtil.parseUserEncode(userEncode);
        if (userId == 0) {
            return baseDto.setCodeMessage(CodeConstant.E_NECESSARY_USERENCODE_CODE_104);
        }
        UserDto userDto = null;
        if (productChannel == BusinessConstant.PLATFORM_APP){
            ClientSerial serial = null;
            StringBuilder key = new StringBuilder(RedisUtils.generateJedisKey(SysConfig.cfgMap.get("PUSH_SYSTEM_ID"))).append(SystemConstants.JEDIS_KEYS_CLIENT_SERIAL);
            if (subUserId != null) {
                key.append("u_" + userId + "s_" + subUserId);
                String clientSerialJson = RedisUtils.getAttribute(key.toString());
                if (StringUtils.isNotEmpty(clientSerialJson)){
                    serial = JSON.parseObject(clientSerialJson, ClientSerial.class);
                }
            }else {
                key.append("u_" + userId + "s_0");
                String clientSerialJson = RedisUtils.getAttribute(key.toString());
                if (StringUtils.isNotEmpty(clientSerialJson)) {
                    serial = JSON.parseObject(clientSerialJson, ClientSerial.class);
                }
            }

            Map<String, Object> params = new HashMap<String, Object>();
            if (serial == null){
                params.put("userId",userId);
                if (subUserId != null) {
                    params.put("subUserId",subUserId);
                }
                params.put("freeze",1);
                JSONObject jsonParams = new JSONObject(params);
                serial = serialService.getSerial(jsonParams);
            }

            if (serial != null){
                String json = JSON.toJSON(serial).toString();
                RedisUtils.setAttribute(key.toString(), json);
            }

            ClientSerialDto serialDto = BeanUtils.copyProperties(serial, ClientSerialDto.class);
            if (serialDto == null) {
                return baseDto.setCodeMessage(CodeConstant.E_NOT_FOUND_SERIALID_3002);
            }
            if (serialDto.getFreeze() == 0) {
                return baseDto.setCodeMessage(CodeConstant.E_STATUS_SERIALID_FREEZED_CODE_1004);
            }
            userDto = BusinessReuqestUtils.getUserBySid(request,serial.getSid());
            if (userDto != null){
                if (!userDto.getSid().equals(serial.getSid())) {
                    /**
                     * 保存新的Serial
                     */
                    serialService.doBindingUserSerial(serial,Integer.valueOf(userDto.getUserId()), serial.getVersion());
                }
            }else {
                return baseDto.setCodeMessage(CodeConstant.E_STATUS_SERIALID_FREEZED_CODE_1004);
            }
            userDto.setPlatform(serial.getPlatform());
        }else {
            if (StringUtils.isNotEmpty(sidStr)){
                userDto = BusinessReuqestUtils.getUserBySid(request,sidStr);
            }
        }
        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(userDto);
    }

    //封停用户
    protected boolean checkIsStopUser(UserDto userDto) {
        if (userDto.getStopEndTime()!=null && userDto.getStopEndTime().getTime()>System.currentTimeMillis()) {
            return true;
        }else {
            return false;
        }
    }
}
