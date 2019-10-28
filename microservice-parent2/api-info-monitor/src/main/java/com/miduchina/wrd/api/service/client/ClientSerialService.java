package com.miduchina.wrd.api.service.client;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.user.ClientSerial;

/**
 * Created by shitao on 2019-05-10 15:13.
 *
 * @author shitao
 */
public interface ClientSerialService {

    /**
     * 获取单个用户
     * @see #getSerial(JSONObject)
     * @param params 查询参数
     * @return ClientSerial
     * */
    ClientSerial getSerial(JSONObject params);


    /**
     * 获取List用户
     * @see #getListSerial(JSONObject)
     * @param params 查询参数
     * @return PageInfo<ClientSerial>
     * */
    PageInfo<ClientSerial> getListSerial(JSONObject params);

    /**
     * 插入用户
     * @see #insertSerial(JSONObject)
     * @param params
     * @return Boolean
     */
    Boolean insertSerial(JSONObject params);


    /**
     * 更新用户
     * @see #updateSerial(JSONObject)
     * @param params
     * @return Boolean
     */
    Boolean updateSerial(JSONObject params);


    /**
     * 序列号绑定完整事务控制
     * @param serial
     * @param userId
     * @param subUserId
     * @param version
     * @return
     */
    ClientSerial doBindingUserSerial(ClientSerial serial, int userId, int subUserId, String version);

    /**
     * 序列号绑定完整事务控制
     * @param serial
     * @param userId
     * @param version
     * @return
     */
    ClientSerial doBindingUserSerial(ClientSerial serial, int userId, String version);


}
