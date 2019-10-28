package com.miduchina.wrd.other.util;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.util.HttpRequestUtils;
import com.miduchina.wrd.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @version v1.0.0
 * @ClassName: BusinessReuqestUtils
 * @Description: TODO
 * @author: sty
 * @date: 2019/4/26 2:22 PM
 */
@Slf4j
public class BusinessReuqestUtils {


    public static UserDto getUserBySid(HttpServletRequest request, String sid){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sid", sid);
        String jsonStr =  BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("userSession"),params);

        JSONObject object = JSONObject.parseObject(jsonStr);
        JSONObject userObject = object.getJSONObject("userInfo");
        UserDto userDto = JSONObject.parseObject(userObject.toJSONString(), UserDto.class);
        userDto.setSid(sid);

        return  userDto;
    }

    /**
     * 发送内部业务接口请求
     *
     * @param request
     * @param url
     * @param params
     * @return
     */
    public static String sendIntraBusinessAPIPOST(HttpServletRequest request, String url, Map<String, Object> params) {
        return sendIntraBusinessAPIPOST(request,url,params, SystemConstants.SO_TIMEOUT,SystemConstants.CONNECTION_TIMEOUT);
    }


    public static String sendIntraBusinessAPIPOST(HttpServletRequest request, String url, Map<String, Object> params,
                                                  int soTimeout,int connectionTimeout) {
        // 排序map
        SortedMap<String, Object> sendParams = new TreeMap<>();
        // 系统参数
        sendParams.put("key", SysConfig.cfgMap.get("API_INTRA_BUSINESS_KEY"));
        sendParams.put("product", IntraBusinessAPIConfig.getValue("product"));
        sendParams.put("platform",request.getParameter("platform"));
        sendParams.put("platformTag", IntraBusinessAPIConfig.getValue("platformTag"));
        sendParams.put("softType", IntraBusinessAPIConfig.getValue("softType"));
        sendParams.put("version", IntraBusinessAPIConfig.getValue("version"));
        sendParams.put("timeStamp", System.currentTimeMillis());

        Map<String, Object> originalRequest = new HashMap<>();
        originalRequest.put("ip", CommonUtils.getIp(request));
        originalRequest.put("ua",request.getHeader("user-agent"));
        originalRequest.put("referer",request.getHeader("referer"));
        sendParams.put("originalRequest", JSONObject.toJSONString(originalRequest));
        sendParams.put("format",IntraBusinessAPIConfig.getValue("format"));

        // 业务参数
        if (MapUtils.isNotEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null){
                    sendParams.put(entry.getKey(), entry.getValue());
                }
            }
        }

        // 加密
        String encryptCode = encryptCode(IntraBusinessAPIConfig.getValue("salt"), sendParams);
        sendParams.put("encryptCode", encryptCode);
        //http://api.business.intra.51wyq.cn/api
//        return HttpRequestUtils.sendPost("http://api-beta.business.intra.51wyq.cn/api" + url, sendParams, soTimeout, connectionTimeout);
        return HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_INTRA_BUSINESS_URL") + url, sendParams, soTimeout, connectionTimeout);
    }



    /**
     * 发送Wrd内部业务接口请求
     *
     * @param request
     * @param url
     * @param params
     * @return
     */
    public static String sendWrdIntraBusinessAPIPOST(HttpServletRequest request, String url, Map<String, Object> params) {
        return sendWrdIntraBusinessAPIPOST(request,url,params, SystemConstants.SO_TIMEOUT,SystemConstants.CONNECTION_TIMEOUT);
    }


    /**
     * 发送Wrd内部业务接口请求
     *
     * @param request
     * @param url
     * @param params
     * @return
     */
    public static String sendWrdIntraBusinessAPIPOST(HttpServletRequest request, String url, Map<String, Object> params,
                                                  int soTimeout,int connectionTimeout) {
        // 排序map
        SortedMap<String, Object> sendParams = new TreeMap<>();
        // 系统参数
        sendParams.put("key", SysConfig.cfgMap.get("API_INTRA_BUSINESS_KEY"));
        sendParams.put("product", IntraBusinessAPIConfig.getValue("product"));
        sendParams.put("platform",request.getParameter("platform"));
        sendParams.put("platformTag", IntraBusinessAPIConfig.getValue("platformTag"));
        sendParams.put("softType", IntraBusinessAPIConfig.getValue("softType"));
        sendParams.put("version", IntraBusinessAPIConfig.getValue("version"));
        sendParams.put("timeStamp", System.currentTimeMillis());

        Map<String, Object> originalRequest = new HashMap<>();
        originalRequest.put("ip", CommonUtils.getIp(request));
        originalRequest.put("ua",request.getHeader("user-agent"));
        originalRequest.put("referer",request.getHeader("referer"));
        sendParams.put("originalRequest", JSONObject.toJSONString(originalRequest));
        sendParams.put("format",IntraBusinessAPIConfig.getValue("format"));

        // 业务参数
        if (MapUtils.isNotEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null){
                    sendParams.put(entry.getKey(), entry.getValue());
                }
            }
        }

        // 加密
        String encryptCode = encryptCode(IntraBusinessAPIConfig.getValue("salt"), sendParams);
        sendParams.put("encryptCode", encryptCode);
        //http://api.business.intra.51wyq.cn/api
        return HttpRequestUtils.sendPost(SysConfig.cfgMap.get("API_WRD_INTRA_BUSINESS_URL") + url, sendParams, soTimeout, connectionTimeout);
    }

    /**
     * 加密
     *
     * @param salt
     * @param params
     * @return
     */
    public static String encryptCode(String salt, Map<String, Object> params) {
        if (StringUtils.isNotBlank(salt) && MapUtils.isNotEmpty(params)) {

            // 排序
            List<String> paramKeyList = new ArrayList(params.keySet());
            Collections.sort(paramKeyList);

            // 拼接
            StringBuilder sb = new StringBuilder();
            for (String k : paramKeyList) {
                sb.append("&").append(k).append("=").append(params.get(k));
            }
            String jointParams = sb.toString().replaceFirst("&", "");
            System.out.println("encryptCode = [" + salt + jointParams + "]");

            // 加密
            String encryptCode = MD5Utils.MD5Encode(salt + jointParams);

            return encryptCode;
        }
        return null;
    }
}
