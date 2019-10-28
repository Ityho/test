package com.miduchina.wrd.monitor.report.common;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.monitor.report.pojos.OperateLogObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j(topic = "")
public class OperateLogUtil {

    public static void log(String msg) throws Exception {
        if (StringUtils.isNotBlank(msg)) {
            log.info(msg);
        }
    }

    /**
     * 记录用户操作行为日志
     *
     * @param request
     *            请求
     * @param user
     *            用户ID
     * @param productSection
     *            产品模块
     * @param operateType
     *            操作类型
     * @param beforeObject
     *            操作前对象
     * @param afterObject
     *            操作后对象
     * @throws Exception
     */
    public static void opreateLog(HttpServletRequest request, Object user, int productSection, int operateType, Object beforeObject, Object afterObject,Integer channelTag) throws Exception {
        OperateLogObject operateLogObject = new OperateLogObject();
        operateLogObject.setLogType(2); // 浏览
        operateLogObject.setProduct(1); // 微舆情
        operateLogObject.setReqTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 请求时间
        operateLogObject.setProductChannel(5); // 产品入口

        operateLogObject.setUser(user); // 用户ID
        operateLogObject.setReqIp(getIp(request)); // 请求IP
        operateLogObject.setProductPageCode(productSection); // 页面代码
        operateLogObject.setProductPageDesc("日报查看"); // 页面描述
        operateLogObject.setReqUA(request.getHeader("user-agent")); // 请求UA
        operateLogObject.setReqReferer(StringUtils.isBlank(request.getHeader("referer")) ? "-" : request.getHeader("referer"));
        if (beforeObject != null) {
            operateLogObject.setOperateBeforeObj(beforeObject); // 操作前对象
        }
        if (afterObject != null) {
            operateLogObject.setOperateAfterObj(afterObject); // 操作后对象
        }
        if(channelTag!=null){
            operateLogObject.setChannelTag(channelTag);
        }

        OperateLogUtil.log(JSONObject.toJSONString(operateLogObject));
    }

    /**
     * 记录用户浏览行为日志
     *
     * @param request
     *            请求
     * @param user
     *            用户ID
     * @param productPageCode
     *            页面代码
     * @param productPageDesc
     *            页面描述
     * @throws Exception
     */
    public static void operatePageLog(HttpServletRequest request, Object user, int productPageCode, String productPageDesc,Integer channelTag,String logExtraInfo) throws Exception {
        OperateLogObject operateLogObject = new OperateLogObject();
        operateLogObject.setLogType(2); // 浏览
        operateLogObject.setProduct(1); // 微舆情
        operateLogObject.setReqTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 请求时间
        operateLogObject.setProductChannel(5); // 产品入口

        operateLogObject.setUser(user); // 用户ID
        operateLogObject.setReqIp(getIp(request)); // 请求IP
        operateLogObject.setProductPageCode(productPageCode); // 页面代码
        operateLogObject.setProductPageDesc(productPageDesc); // 页面描述
        operateLogObject.setReqUA(request.getHeader("user-agent")); // 请求UA
        operateLogObject.setReqReferer(StringUtils.isBlank(request.getHeader("referer")) ? "-" : request.getHeader("referer"));
        if(channelTag!=null){
            operateLogObject.setChannelTag(channelTag);
        }
        if(logExtraInfo!=null){
            operateLogObject.setLogExtraInfo(logExtraInfo);
        }

        OperateLogUtil.log(JSONObject.toJSONString(operateLogObject));
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
