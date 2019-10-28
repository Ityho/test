package com.miduchina.wrd.api.service.client.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.client.ClientSerialMapper;
import com.miduchina.wrd.api.service.client.ClientSerialService;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.po.user.ClientSerial;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shitao on 2019-05-10 15:14.
 *
 * @author shitao
 */

@Slf4j
@Service
@CacheConfig(cacheNames = "client_serial")
public class ClientSerialServiceImpl implements ClientSerialService {


    @Resource
    private ClientSerialMapper serialMapper;

    @Override
    public ClientSerial getSerial(JSONObject params) {

        List<ClientSerial> userList = serialMapper.getListSerial(params);
        if (CollectionUtils.isNotEmpty(userList)){
            return userList.get(0);
        }
        return new ClientSerial();
    }

    @Override
    public PageInfo<ClientSerial> getListSerial(JSONObject params){
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> serialMapper.getListSerial(params));
    }


    @Override
    public Boolean insertSerial(JSONObject params){
        return serialMapper.insertSerial(params);
    }


    @Override
    public Boolean updateSerial(JSONObject params){
        return serialMapper.updateSerial(params);
    }

    @Override
    public ClientSerial doBindingUserSerial(ClientSerial serial, int userId, int subUserId, String version){

        ClientSerial clientSerial = null;
        if (serial!=null)
        {
            try
            {
                int freezeDeviceTokenSerialResult =
                        freezeDeviceSerialByDeviceTokenExcludeSerialId(serial.getDeviceToken(), serial.getClientSerialId());

                int freezeDeviceSerialResult =
                        freezeDeviceSerialByDeviceIdExcludeSerialId(serial.getDeviceId(), serial.getClientSerialId());

                log.info("v2doBindingUserSerial freezeDeviceSerial ===>>> deviceId={}+serialId={}+result={}",
                        serial.getDeviceId(),serial.getClientSerialId(),freezeDeviceSerialResult);

                int freezeUserSerialResult =
                        freezeUserSerialByUserIdExcludeSerialId(userId, subUserId, serial.getClientSerialId());

                log.info("v2doBindingUserSerial freezeUserSerial ===>>> userId={}+subUserId={}+serialId={}+result={}",
                        userId,subUserId,serial.getClientSerialId(),freezeUserSerialResult);

                int bindingUserSerialResult = bindingUserSerial(serial, userId, subUserId, version);

                log.info("v2doBindingUserSerial bindingUserSerial ===>>> userId={}+subUserId={}+serialId={}+result={}",
                        userId,subUserId,serial.getClientSerialId(),bindingUserSerialResult);


                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userId",userId);
                params.put("subUserId",subUserId);
                params.put("clientSerialId",serial.getClientSerialId());
                params.put("freeze",1);
                JSONObject jsonParams = new JSONObject(params);
                clientSerial = getSerial(jsonParams);

                StringBuilder key = new StringBuilder(RedisUtils.generateJedisKey(SysConfig.cfgMap.get("pushSystemId"))).append(SystemConstants.JEDIS_KEYS_CLIENT_SERIAL);
                key.append("u_" + userId + "s_" + subUserId);
                String clientSerialJson = JSON.toJSON(clientSerial).toString();
                RedisUtils.setAttribute(key.toString(), clientSerialJson);
            }
            catch (Exception ex)
            {
                String errMsg = "";
                errMsg += "Exception : " + ex + " {";
                StackTraceElement[] ste = ex.getStackTrace();
                for (int k = 0; k < ste.length; k++)
                {
                    errMsg += ste[k].toString() + ";";
                }
                errMsg += "}";
                log.error("v2doBindingSerial " + errMsg);
            }
        }
        return clientSerial;

    }


    @Override
    public ClientSerial doBindingUserSerial(ClientSerial serial, int userId, String version){
        ClientSerial clientSerial = null;

        if (serial!=null)
        {
            try
            {

                int freezeDeviceTokenSerialResult =
                        freezeDeviceSerialByDeviceTokenExcludeSerialId(serial.getDeviceToken(), serial.getClientSerialId());

                int freezeDeviceSerialResult =
                        freezeDeviceSerialByDeviceIdExcludeSerialId(serial.getDeviceId(), serial.getClientSerialId());
                log.info("v2doBindingUserSerial freezeDeviceSerial ==> deviceId={},serialId={},result={}",serial.getDeviceId(),serial.getClientSerialId(),freezeDeviceSerialResult);

                int freezeUserSerialResult = freezeUserSerialByUserIdExcludeSerialId(userId, serial.getClientSerialId());
                log.info("v2doBindingUserSerial freezeUserSerial ==> userId={},serialId={},result={}",userId,serial.getClientSerialId(),freezeUserSerialResult);

                int bindingUserSerialResult = bindingUserSerial(serial, userId, 0,version);
                log.info("v2doBindingUserSerial bindingUserSerial ==> userId={},serialId={},result={}",userId,serial.getClientSerialId(),bindingUserSerialResult);


                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userId",userId);
                params.put("clientSerialId",serial.getClientSerialId());
                params.put("freeze",1);
                JSONObject jsonParams = new JSONObject(params);
                clientSerial = getSerial(jsonParams);


                StringBuilder key = new StringBuilder(RedisUtils.generateJedisKey(SysConfig.cfgMap.get("pushSystemId"))).append(SystemConstants.JEDIS_KEYS_CLIENT_SERIAL);
                key.append("u_" + userId + "s_0");
                String clientSerialJson = JSON.toJSON(clientSerial).toString();
                RedisUtils.setAttribute(key.toString(), clientSerialJson);
            }
            catch (Exception ex)
            {
                String errMsg = "";
                errMsg += "Exception : " + ex + " {";
                StackTraceElement[] ste = ex.getStackTrace();
                for (int k = 0; k < ste.length; k++)
                {
                    errMsg += ste[k].toString() + ";";
                }
                errMsg += "}";
                log.error("v2doBindingSerial " + errMsg);
            }
        }

        return clientSerial;
    }


    /**
     * 冻结该设备号上非本次提交的序列号相关的所有序列号记录
     */
    private int freezeDeviceSerialByDeviceTokenExcludeSerialId(String deviceToken, String serialId) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("freeze",BusinessConstant.SERIAL_STATUS_FREEZED);
        params.put("wDeviceToken",deviceToken);
        params.put("wClientSerialId",serialId);
        params.put("wFreeze",BusinessConstant.SERIAL_STATUS_ACTIVE);
        JSONObject jsonParams = new JSONObject(params);
        log.info("freezeDeviceSerialByDeviceTokenExcludeSerialId = {}",params.toString());
        if (serialMapper.updateSerial(jsonParams)){
            return 1;
        }
        return 0;
    }



    /**
     * 冻结该设备号上非本次提交的序列号相关的所有序列号记录
     */
    private int freezeDeviceSerialByDeviceIdExcludeSerialId(String deviceId, String serialId)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("freeze",BusinessConstant.SERIAL_STATUS_FREEZED);
        params.put("wDeviceId",deviceId);
        params.put("wClientSerialId",serialId);
        params.put("wFreeze",BusinessConstant.SERIAL_STATUS_ACTIVE);
        JSONObject jsonParams = new JSONObject(params);
        log.info("freezeDeviceSerialByDeviceTokenExcludeSerialId = {}",params.toString());
        if (serialMapper.updateSerial(jsonParams)){
            return 1;
        }
        return 0;
    }



    /**
     * 冻结该用户非本次提交的序列号相关的所有序列号记录
     */
    private int freezeUserSerialByUserIdExcludeSerialId( int userId,  String serialId)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("freeze",BusinessConstant.SERIAL_STATUS_FREEZED);
        params.put("wUserId",userId);
        params.put("wClientSerialId",serialId);
        params.put("wFreeze",BusinessConstant.SERIAL_STATUS_ACTIVE);
        params.put("wSubUserId",0);
        JSONObject jsonParams = new JSONObject(params);
        log.info("freezeUserSerialByUserIdExcludeSerialId = {}",params.toString());
        if (serialMapper.updateSerial(jsonParams)){
            return 1;
        }
        return 0;
    }




    /**
     * 冻结该用户非本次提交的序列号相关的所有序列号记录
     */
    private int freezeUserSerialByUserIdExcludeSerialId(int userId, int subUserId,  String serialId)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("freeze",BusinessConstant.SERIAL_STATUS_FREEZED);
        params.put("wUserId",userId);
        params.put("wClientSerialId",serialId);
        params.put("wFreeze",BusinessConstant.SERIAL_STATUS_ACTIVE);
        params.put("wSubUserId",subUserId);
        JSONObject jsonParams = new JSONObject(params);
        log.info("freezeUserSerialByUserIdExcludeSerialId = {}",params.toString());
        if (serialMapper.updateSerial(jsonParams)){
            return 1;
        }
        return 0;
    }


    /**
     * 绑定用户ID和上传的序列号
     * @param serial
     * @param userId
     * @param version
     * @return
     */
    private int bindingUserSerial(ClientSerial serial, int userId, int subUserId, String version)
    {
        String deviceToken = serial.getDeviceToken();
        if (StringUtils.isBlank(deviceToken)) {
            deviceToken = BusinessConstant.STRING_EMPTY;
        }
        String deviceModel = serial.getDeviceModel();
        if (StringUtils.isBlank(deviceModel)) {
            deviceModel = BusinessConstant.STRING_EMPTY;
        }
        int deviceLabel = BusinessConstant.ANDROID_DEVICE_LABEL_OTHER;
        String deviceOs = serial.getDeviceOs();
        if (StringUtils.isBlank(deviceOs))
        {
            deviceOs = BusinessConstant.STRING_EMPTY;
        }
        if (StringUtils.isNotBlank(serial.getDeviceOs()))
        {
            deviceLabel = transToDeviceLabel(serial.getDeviceOs());
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("freeze",BusinessConstant.SERIAL_STATUS_ACTIVE);
        params.put("userId",userId);
        if (subUserId == 0){
            params.put("subUserId",null);
        }else {
            params.put("subUserId",subUserId);
        }
        params.put("deviceLabel",deviceLabel);
        params.put("deviceModel",deviceModel);
        params.put("deviceOs",deviceOs);
        params.put("deviceToken",deviceToken);
        params.put("version",version);
        params.put("wClientSerialId",serial.getClientSerialId());
        JSONObject jsonParams = new JSONObject(params);
        log.info("bindingUserSerial = {}",params.toString());
        if (serialMapper.updateSerial(jsonParams)){
            return 1;
        }
        return 0;
    }


    /**
     * 根据客户端上传的手机操作系统区分Android设备 0-其它，1-华为，2-小米
     *
     * @param deviceOs
     * @return
     */
    private Integer transToDeviceLabel(String deviceOs)
    {
        deviceOs = deviceOs.toUpperCase();
        if (deviceOs.toUpperCase().indexOf(BusinessConstant.ANDROID_DEVICE_OS_HUAWEI) != -1)
        {
            return BusinessConstant.ANDROID_DEVICE_LABEL_HUAWEI;
        }
        else if (deviceOs.toUpperCase().indexOf(BusinessConstant.ANDROID_DEVICE_OS_XIAOMI) != -1)
        {
            return BusinessConstant.ANDROID_DEVICE_LABEL_XIAOMI;
        }
        else
        {
            return BusinessConstant.ANDROID_DEVICE_LABEL_OTHER;
        }
    }




}
