package com.miduchina.wrd.monitor.warning.operatelog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.miduchina.wrd.po.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.monitor.warning.operatelog.model.OperateLogObject;
import com.miduchina.wrd.monitor.warning.operatelog.model.OperateLogUserInfo;

@Slf4j(topic = "wyq_op_warning")
public class OperateLogUtils {

	public static void log(String msg) throws Exception {
		if (StringUtils.isNotBlank(msg)) {
            msg = new String(msg.trim().getBytes("UTF-8"), "ISO-8859-1");
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
        operateLogObject.setProduct(1); // 微热点
        operateLogObject.setReqTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 请求时间
        operateLogObject.setProductChannel(4); // 产品入口

        operateLogObject.setUser(user); // 用户ID
        operateLogObject.setReqIp(getIp(request)); // 请求IP
        operateLogObject.setProductPageCode(productSection); // 页面代码
        operateLogObject.setProductPageDesc("预警查看"); // 页面描述
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

        OperateLogUtils.log(JSONObject.toJSONString(operateLogObject));
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
    public static void operatePageLog(HttpServletRequest request, Object user, int productPageCode, String productPageDesc,String extraInfo,Integer channelTag) throws Exception {
        OperateLogObject operateLogObject = new OperateLogObject();
        operateLogObject.setLogType(2); // 浏览
        operateLogObject.setProduct(1); // 微热点
        operateLogObject.setReqTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 请求时间
        operateLogObject.setProductChannel(4); // 产品入口

        operateLogObject.setUser(user); // 用户ID
        operateLogObject.setReqIp(getIp(request)); // 请求IP
        operateLogObject.setProductPageCode(productPageCode); // 页面代码
        operateLogObject.setProductPageDesc(productPageDesc); // 页面描述
        operateLogObject.setReqUA(request.getHeader("user-agent")); // 请求UA
        operateLogObject.setReqReferer(StringUtils.isBlank(request.getHeader("referer")) ? "-" : request.getHeader("referer"));
        if(extraInfo!=null){
            operateLogObject.setLogExtraInfo(extraInfo);
        }
        if(channelTag!=null){
            operateLogObject.setChannelTag(channelTag);
        }

        OperateLogUtils.log(JSONObject.toJSONString(operateLogObject));
    }
    
    public static void userToUserInfo(User user, OperateLogUserInfo operateLogUserInfo){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        operateLogUserInfo.setUserId(user.getUserId());
        operateLogUserInfo.setUsername(user.getUsername());
        operateLogUserInfo.setNickname(user.getNickname());
        operateLogUserInfo.setEmail(user.getEmail());
        operateLogUserInfo.setMobile(user.getMobile());
        operateLogUserInfo.setPassword(user.getPassword());
        operateLogUserInfo.setUserHead(user.getUserHead());
        operateLogUserInfo.setAppUserStatus(user.getAppUserStatus());
        operateLogUserInfo.setUserChannel(user.getUserChannel());
        operateLogUserInfo.setUsreType(user.getUserType());
        operateLogUserInfo.setPlatform(user.getPlatform());
        operateLogUserInfo.setUserAnalysisValidCount(user.getUserAnalysisValidCount());
        operateLogUserInfo.setUserBriefValidCount(user.getUserBriefValidCount());
        operateLogUserInfo.setUserReportValidCount(user.getUserReportValidCount());
        operateLogUserInfo.setUserProductAnalysisValidCount(user.getUserProductAnalysisValidCount());
        operateLogUserInfo.setUserSingleWeiboAnalysisValidCount(user.getUserSingleWeiboAnalysisValidCount());
        operateLogUserInfo.setStatus(user.getStatus());
        operateLogUserInfo.setCreateTime(sdf.format(user.getCreateTime()));
        if(user.getProUserValidEndTime()!=null){
            operateLogUserInfo.setProUserValidEndTime(sdf.format(user.getProUserValidEndTime()));
        }
        if(user.getLastLoginTime()!=null){
            operateLogUserInfo.setLastLoginTime(sdf.format(user.getLastLoginTime()));
        }
        if(user.getUpdateTime()!=null){
            operateLogUserInfo.setUpdateTime(sdf.format(user.getUpdateTime()));
        }
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
