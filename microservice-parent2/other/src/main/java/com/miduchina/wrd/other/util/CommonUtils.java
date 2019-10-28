package com.miduchina.wrd.other.util;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.bo.user.UserBO;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.analysis.AnalysisDto;
import com.miduchina.wrd.dto.analysis.briefing.BriefingDto;
import com.miduchina.wrd.dto.analysis.competitive.CompetitiveDto;
import com.miduchina.wrd.dto.analysis.eventanalysis.EventAnalysisTaskDto;
import com.miduchina.wrd.dto.analysis.weiboanalysis.WeiboAnalysisTaskDto;
import com.miduchina.wrd.dto.ip.IpInfoDto;
import com.miduchina.wrd.dto.ip.OriginalRequestDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @version v1.0.0
 * @ClassName: CommonUtils
 * @Description: TODO
 * @author: sty
 * @date: 2019/4/26 2:47 PM
 */
@Slf4j
public class CommonUtils extends com.miduchina.wrd.util.CommonUtils{

    private static Pattern BLANK_SPACE_PATTERN = Pattern.compile("\\s*|\t|\r|\n");
    private static Pattern MOBILE_PATTERN = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
    private static Pattern EMOJI_PATTERN = Pattern.compile("\\[(.*?)\\]");
    /**
     * 获取微博表情URL
     *
     * @param faceName
     * @return
     */
    public static String getEmoji(String faceName) {
        String faceUrl = "";
        if (faceName != null && !"".equals(faceName) && IntraBusinessAPIConfig.expressions != null && !IntraBusinessAPIConfig.expressions.isEmpty()) {
            Matcher matcher = EMOJI_PATTERN.matcher(faceName);
            while (matcher.find()) {
                faceUrl = IntraBusinessAPIConfig.expressions.get(matcher.group());
            }
        }
        return faceUrl;
    }
    /**
     * 替换微博表情
     *
     * @param content
     * @return
     */
    public static String replaceEmoji(String content) {
        if (content != null && !"".equals(content) && IntraBusinessAPIConfig.expressions != null && !IntraBusinessAPIConfig.expressions.isEmpty()) {
            Matcher matcher = EMOJI_PATTERN.matcher(content);
            while (matcher.find()) {
                if (IntraBusinessAPIConfig.expressions.get(matcher.group()) != null && !"".equals(IntraBusinessAPIConfig.expressions.get(matcher.group()))) {
                    content = content.replace(matcher.group(), "<img src = \"" + IntraBusinessAPIConfig.expressions.get(matcher.group()) + "\"/>");
                }
            }
        }
        return content;
    }

    /**
     * 获取原始请求IP
     *
     * @param request
     * @return
     */
    public static String getOriginalRequestIp(HttpServletRequest request) {
        String originalRequest = convertRequestString(request.getMethod().toString(), request.getParameter("originalRequest"));
        if (StringUtils.isNotBlank(originalRequest)) {
            OriginalRequestDto originalRequestVO = JSONObject.parseObject(originalRequest, OriginalRequestDto.class);
            if (originalRequestVO != null){
                if (checkIp(originalRequestVO.getIp())) {
                    return originalRequestVO.getIp();
                }
            }
        }

        return getIp(request);
    }

    /**
     * 中文参数处理
     *
     * @param requestMethod
     * @param originalStr
     * @return
     */
    public static String convertRequestString(String requestMethod, String originalStr) {
        if (!StringUtils.isBlank(originalStr)) {
            String resultStr = originalStr;
            try {
                resultStr = "POST".equalsIgnoreCase(requestMethod) ? originalStr : new String(originalStr.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return resultStr;
        }
        return "";
    }

    /**
     * ip转数字格式
     */
    public static long ipToNum(String ipAddress) {
        long result = 0;
        String[] ipAddressInArray = ipAddress.split("\\.");
        if(ipAddressInArray.length >= 4){
            for (int i = 3; i >= 0; i--) {
                long ip = Long.parseLong(ipAddressInArray[3 - i]);
                result |= ip << (i * 8);
            }
        }
        return result;
    }

    /**
     * 数字转ip
     */
    public static String numToIp(Long ip) {
        if(ip == null){
            return null;
        }else{
            return new StringBuilder()
                    .append((ip >> 24) & 0xFF).append(".")
                    .append((ip >> 16) & 0xFF).append(".")
                    .append((ip >> 8) & 0xFF).append(".")
                    .append(ip & 0xFF).toString();
        }
    }

    public static boolean checkIsAbnormalIp(IpInfoDto ipInfo) {
        boolean res = false;
        if(ipInfo != null && StringUtils.isNotBlank(ipInfo.getIsp())){
            if(ipInfo.getIsp().indexOf("阿里云") >= 0){
                res = true;
                log.info("checkIsAbnormalIp, ip abnormal, ip=" + ipInfo.getIp() + ", isp=" + ipInfo.getIsp());
            }else{
                log.info("checkIsAbnormalIp, ip normal, ip=" + ipInfo.getIp() + ", isp=" + ipInfo.getIsp());
            }
        }else{
            log.info("checkIsAbnormalIp, not get isp, ip=" + ipInfo.getIp());
        }
        return res;
    }

    /**
     * 淘宝接口获取ip信息
     *
     * @param ip
     * @return
     */
    @SuppressWarnings("resource")
    public static IpInfoDto getIpInfoByTbApi(String ip) {
        IpInfoDto ipInfo = new IpInfoDto();
        ipInfo.setIp(ip);
        ipInfo.setIpNum(ipToNum(ip));
        String errorNumKey = RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_GETIPINFO_ERROR_NUM);
        String errorNum = RedisUtils.getAttribute(errorNumKey);
        // 接口异常次数达到5次不再获取ip信息
        if(StringUtils.isNotBlank(errorNum) && Integer.valueOf(errorNum) > 5){
            return ipInfo;
        }
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String urlWithParams = SysConfig.cfgMap.get("TAOBAO_IP_PROVINCE_API_URL").replace("$(ip)", ip);
        String rtnStr = null;
        try {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 30);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 30);
            httpGet = new HttpGet(urlWithParams);
            httpGet.setHeader("Host", "ip.taobao.com");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:52.0) Gecko/20100101 Firefox/52.0");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity resultEntity = httpResponse.getEntity();
                rtnStr = EntityUtils.toString(resultEntity, "UTF-8");
                if(StringUtils.isNotBlank(rtnStr)){
                    JSONObject object = null;
                    object = JSONObject.parseObject(rtnStr);
                    if("0".equals(object.getString("code"))){
                        JSONObject data = JSONObject.parseObject(object.getString("data"));
                        ipInfo.setProvince(data.getString("region"));
                        ipInfo.setCity(data.getString("city"));
                        ipInfo.setIsp(data.getString("isp"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(StringUtils.isBlank(errorNum)){
                errorNum = "1";
            }else{
                errorNum = String.valueOf(Integer.valueOf(errorNum) + 1);
            }
            RedisUtils.refreshAttribute(errorNumKey, null, errorNum, 60*60);
        } finally {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
        return ipInfo;
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
            //ip = "127.0.0.1";
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0){
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if (!CommonUtils.checkIp(ip)) {
            ip = "";
        }

        return ip;
    }

    public static boolean checkIp(String ip){
        if(StringUtils.isNotBlank(ip) && CommonUtils.match("^[\\d,\\.,:]+$", ip) && ip.length() <= 17){
            return true;
        }
        return false;
    }


    public static void opreateLog(String jsonStr) throws Exception {
        Logger logger = LoggerFactory.getLogger("com.miduchina.wrd.util.CommonUtils");
        logger.info(StringUtil.convertDB2LogString(jsonStr));
    }


    /**
     *
     * @param request
     * @param productSection 产品版块编码
     * @param logObjectDto
     * @param operateType 操作类型：1-增 2-删 3-改 4-查
     * @param beforeObject 操作前对象
     * @param afterObject 操作后对象
     * @throws Exception
     */
    public static void opreateLog(HttpServletRequest request, String url,Integer productSection, OperateLogObjectDto logObjectDto,
                                  int operateType, Object beforeObject, Object afterObject)
            throws Exception {

        OperateLogObjectDto operateLogObjectDto = new OperateLogObjectDto();
        if (logObjectDto != null){
            operateLogObjectDto = logObjectDto;
        }
        /**
         * 操作
         */
        operateLogObjectDto.setLogType(1);
        /**
         * 微舆情
         */
        operateLogObjectDto.setProduct(1);
        /**
         * 请求时间
         */
        operateLogObjectDto.setReqTime(DateUtils.getCurrentStringDate());
        /**
         * 产品入口
         */

        String productChannelStr = request.getParameter("productChannel");
        if (StringUtils.isNotBlank(productChannelStr)){
            operateLogObjectDto.setProductChannel(Integer.valueOf(productChannelStr));
        }else {
            operateLogObjectDto.setProductChannel(BusinessConstant.INTEGER_ZERO);
        }
        /**
         * 请求IP
         */
        operateLogObjectDto.setReqIp(CommonUtils.getIp(request));
        /**
         * 功能模块
         */
        operateLogObjectDto.setProductSection(productSection);
        /**
         * 操作类型
         */
        operateLogObjectDto.setOperateType(operateType);
        /**
         * api接口前缀
         */
        if (StringUtils.isNotBlank(url)){
            operateLogObjectDto.setApiURL(url);
        }else {
            operateLogObjectDto.setApiURL(request.getRequestURL().toString());
        }

        if (beforeObject != null) {
            /**
             * 操作前对象
             */
            operateLogObjectDto.setOperateBeforeObj(beforeObject);
        }
        if (afterObject != null){
            /**
             * 操作后对象
             */
            operateLogObjectDto.setOperateAfterObj(afterObject);
        }
        /**
         * 请求UA
         */
        operateLogObjectDto.setReqUA(request.getHeader("user-agent"));
        /**
         * 请求referer
         */
        operateLogObjectDto.setReqReferer(StringUtils.isBlank(request.getHeader("referer")) ? "-" : request.getHeader("referer"));
        //服务器中文转码
        String jsonString = JSONObject.toJSONString(operateLogObjectDto);
        log.info(StringUtil.convertDB2LogString(jsonString));

        Logger logger = LoggerFactory.getLogger("com.miduchina.wrd.util.CommonUtils");
        logger.info(StringUtil.convertDB2LogString(jsonString));

    }

    public static OperateLogObjectDto generateOperateLogObject(OperateLogObjectDto objectDto,Map<String,Object> map) {

        OperateLogObjectDto operateLogObjectDto = new OperateLogObjectDto();
        if (objectDto != null){
            operateLogObjectDto = objectDto;
        }

        UserDto userDto = (UserDto) map.get("UserDto");
        WeiboAnalysisTaskDto weiboAnalysisTaskDto = (WeiboAnalysisTaskDto)map.get("WeiboAnalysisTaskDto");
        AnalysisDto analysisDto =  (AnalysisDto)map.get("AnalysisDto");
        BriefingDto briefingDto = (BriefingDto) map.get("BriefingDto");
        CompetitiveDto competitiveDto =  (CompetitiveDto)map.get("CompetitiveDto");
        EventAnalysisTaskDto eventAnalysisTaskDto = (EventAnalysisTaskDto)map.get("EventAnalysisTaskDto");

        //。。。。
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> otherMap = new HashMap<>();
        if (userDto != null){
            otherMap = CommonUtils.entityToMap(userDto);
            map1.putAll(otherMap);
        }
        if (weiboAnalysisTaskDto != null){
            otherMap = CommonUtils.entityToMap(weiboAnalysisTaskDto);
            map1.putAll(otherMap);
        }
        if (analysisDto != null){
            otherMap = CommonUtils.entityToMap(analysisDto);
            map1.putAll(otherMap);
        }
        if (briefingDto != null){
            otherMap = CommonUtils.entityToMap(briefingDto);
            map1.putAll(otherMap);
        }
        if (competitiveDto != null){
            otherMap = CommonUtils.entityToMap(competitiveDto);
            map1.putAll(otherMap);
        }
        if (eventAnalysisTaskDto != null){
            otherMap = CommonUtils.entityToMap(eventAnalysisTaskDto);
            map1.putAll(otherMap);
        }

        if (userDto != null){
            if (StringUtils.isNoneBlank(userDto.getUserId())){
                operateLogObjectDto.setUserId(Integer.valueOf(userDto.getUserId()));
            }
            operateLogObjectDto.setUsername(userDto.getUsername());
            operateLogObjectDto.setNickname(userDto.getNickname());
            operateLogObjectDto.setEmail(userDto.getEmail());
            operateLogObjectDto.setMobile(userDto.getMobile());
            operateLogObjectDto.setUserHead(userDto.getUserHead());
            operateLogObjectDto.setLastLoginTime(userDto.getLastLoginTime());
            operateLogObjectDto.setAppUserStatus(userDto.getAppUserStatus());
            operateLogObjectDto.setUserChannel(userDto.getUserChannel());
            operateLogObjectDto.setUserType(userDto.getUserType());
            operateLogObjectDto.setProUserValidEndTime(userDto.getProUserValidEndTime());
            operateLogObjectDto.setPlatform(userDto.getPlatform());
            operateLogObjectDto.setUserAnalysisValidCount(userDto.getUserAnalysisValidCount());
            operateLogObjectDto.setUserBriefValidCount(userDto.getUserBriefValidCount());
            operateLogObjectDto.setUserReportValidCount(userDto.getUserReportValidCount());
            operateLogObjectDto.setUserProductAnalysisValidCount(userDto.getUserProductAnalysisValidCount());
            operateLogObjectDto.setUserSingleWeiboAnalysisValidCount(userDto.getUserWeiboAnalysisValidCount());
            operateLogObjectDto.setStatus(userDto.getStatus());
            operateLogObjectDto.setCreateTime(userDto.getCreateTime());
            operateLogObjectDto.setUpdateTime(userDto.getUpdateTime());
        }

        if (weiboAnalysisTaskDto != null){
            operateLogObjectDto.setTaskId(weiboAnalysisTaskDto.getTaskId());
            operateLogObjectDto.setUserId(weiboAnalysisTaskDto.getUserId());
            operateLogObjectDto.setWeiboUrl(weiboAnalysisTaskDto.getWeiboUrl());
            operateLogObjectDto.setTaskTicket(weiboAnalysisTaskDto.getTaskTicket());
            operateLogObjectDto.setWeiboContent(weiboAnalysisTaskDto.getWeiboContent());
            operateLogObjectDto.setForwardCount(weiboAnalysisTaskDto.getForwardCount());
            operateLogObjectDto.setCommentCount(weiboAnalysisTaskDto.getCommentCount());
            operateLogObjectDto.setPraiseCount(weiboAnalysisTaskDto.getPraiseCount());
            operateLogObjectDto.setPublishedTime(weiboAnalysisTaskDto.getPublishedTime());
            operateLogObjectDto.setWeiboUid(weiboAnalysisTaskDto.getWeiboUid());
            operateLogObjectDto.setWeiboNickname(weiboAnalysisTaskDto.getWeiboNickname());
            operateLogObjectDto.setWeiboUserhead(weiboAnalysisTaskDto.getWeiboUserhead());
            operateLogObjectDto.setShareCode(weiboAnalysisTaskDto.getShareCode());
            operateLogObjectDto.setAnalysisStatus(weiboAnalysisTaskDto.getAnalysisStatus());
            operateLogObjectDto.setShareUrl(weiboAnalysisTaskDto.getShareUrl());
            operateLogObjectDto.setTaskCreateTime(weiboAnalysisTaskDto.getCreateTime());
            operateLogObjectDto.setTaskUpdateTime(weiboAnalysisTaskDto.getUpdateTime());
            operateLogObjectDto.setForwardContent(weiboAnalysisTaskDto.getForwardContent());
            operateLogObjectDto.setIsSample(weiboAnalysisTaskDto.getIsSample());
            operateLogObjectDto.setMarkType(weiboAnalysisTaskDto.getMarkType());
            operateLogObjectDto.setShareRedCount(weiboAnalysisTaskDto.getShareRedCount());
            operateLogObjectDto.setVerifiedType(weiboAnalysisTaskDto.getVerifiedType());
            operateLogObjectDto.setPayment(weiboAnalysisTaskDto.getPayment());
            operateLogObjectDto.setSharePlatform(weiboAnalysisTaskDto.getSharePlatform());
            operateLogObjectDto.setStatus(weiboAnalysisTaskDto.getStatus());

            operateLogObjectDto.setAnalysisNum(weiboAnalysisTaskDto.getForwardCount());

        }

        if (analysisDto != null){

            //percent
            //sensitiveDesc
            //sourceMediaDesc
            //sourceTypeDesc
            //tendDesc
            //wordDesc

            operateLogObjectDto.setAnalysisStatus(analysisDto.getAnalysisStatus());
            operateLogObjectDto.setTaskCreateTime(analysisDto.getCreateTime());
            operateLogObjectDto.setEndTime(analysisDto.getEndTime());
            operateLogObjectDto.setTaskId(analysisDto.getProductAnalysisId());
            operateLogObjectDto.setTaskKeyword(analysisDto.getKeywords());
            operateLogObjectDto.setPlatform(analysisDto.getPlatform());
            operateLogObjectDto.setScheduleTips(analysisDto.getSchedule());
            operateLogObjectDto.setStartTime(analysisDto.getStartTime());
            operateLogObjectDto.setStatus(analysisDto.getStatus());
            operateLogObjectDto.setTaskTicket(analysisDto.getTicket());
            operateLogObjectDto.setTitle(analysisDto.getTitle());
            operateLogObjectDto.setTaskUpdateTime(analysisDto.getUpdateTime());
            operateLogObjectDto.setUserId(analysisDto.getUserId());
            operateLogObjectDto.setFilePath(analysisDto.getFilePath());
            operateLogObjectDto.setShareUrl(analysisDto.getShareUrl());
            operateLogObjectDto.setBarImg(analysisDto.getBarImg());
            operateLogObjectDto.setIsSample(analysisDto.getIsSample());
        }

        if (briefingDto != null){

            // pieImg;
            // treeImg;
            // areaImg;
            // lineImg;
            // jsonData;
            // saveFileName;
            // selectedRead;
            // propose;
            operateLogObjectDto.setBriefId(briefingDto.getBriefId());
            operateLogObjectDto.setUserId(briefingDto.getUserId());
            operateLogObjectDto.setStatus(briefingDto.getStatus());
            operateLogObjectDto.setStatus(briefingDto.getStatus());
            operateLogObjectDto.setKeywordId(briefingDto.getKeywordId());
            operateLogObjectDto.setTaskCreateTime(briefingDto.getCreateTime());
            operateLogObjectDto.setSummary(briefingDto.getSummary());
            operateLogObjectDto.setTitle(briefingDto.getTitle());
            operateLogObjectDto.setTaskUpdateTime(briefingDto.getUpdateTime());
            operateLogObjectDto.setTemplateId(briefingDto.getTemplateId());
            operateLogObjectDto.setType(briefingDto.getType());
            operateLogObjectDto.setTaskTicket(briefingDto.getTaskTicket());
            operateLogObjectDto.setStartTime(briefingDto.getStartTime());
            operateLogObjectDto.setEndTime(briefingDto.getEndTime());
            operateLogObjectDto.setAnalysisNum(briefingDto.getAnalysisNum());
            operateLogObjectDto.setSchedule(briefingDto.getSchedule());
            operateLogObjectDto.setScheduleTips(briefingDto.getScheduleTips());
            operateLogObjectDto.setAnalysisStatus(briefingDto.getAnalysisStatus());
            operateLogObjectDto.setBriefSeq(briefingDto.getBriefSeq());
            operateLogObjectDto.setStyleType(briefingDto.getStyleType());
            operateLogObjectDto.setMarkType(briefingDto.getMarkType());
            operateLogObjectDto.setClickType(briefingDto.getClickType());
            operateLogObjectDto.setIsEdit(briefingDto.getIsEdit());
            operateLogObjectDto.setAnalysisTotalConsumeExpect(briefingDto.getAnalysisTotalConsumeExpect());
            operateLogObjectDto.setAnalysisSolrFirstCountExpect(briefingDto.getAnalysisSolrFirstCountExpect());
        }

        if (competitiveDto != null){
            operateLogObjectDto.setPabId(competitiveDto.getPabId());
            operateLogObjectDto.setTitle(competitiveDto.getTitle());
            operateLogObjectDto.setTaskCreateTime(competitiveDto.getCreateTime());
            operateLogObjectDto.setFilePath(competitiveDto.getFilePath());
            operateLogObjectDto.setShareUrl(competitiveDto.getShareUrl());
            operateLogObjectDto.setBarImg(competitiveDto.getBarImg());
            operateLogObjectDto.setAnalysisStatus(competitiveDto.getSchedulesStatus());
            operateLogObjectDto.setIsSample(competitiveDto.getIsSample());
            operateLogObjectDto.setTaskTicket(competitiveDto.getTicket());
        }

        if (eventAnalysisTaskDto != null){

            operateLogObjectDto.setTaskId(eventAnalysisTaskDto.getTaskId());
            operateLogObjectDto.setPlatformTag(eventAnalysisTaskDto.getPlatformTag());
            operateLogObjectDto.setContentType(eventAnalysisTaskDto.getContentType());
            operateLogObjectDto.setTitle(eventAnalysisTaskDto.getIncidentTitle());
            operateLogObjectDto.setTaskKeyword(eventAnalysisTaskDto.getKeyword());
            operateLogObjectDto.setSupplementKeyword(eventAnalysisTaskDto.getSupplementKeyword());
            operateLogObjectDto.setFilterKeyword(eventAnalysisTaskDto.getFilterKeyword());
            operateLogObjectDto.setAnalysisType(eventAnalysisTaskDto.getAnalysisType());
            operateLogObjectDto.setAnalysisParams(eventAnalysisTaskDto.getAnalysisParams());
            operateLogObjectDto.setAnalysisStatus(eventAnalysisTaskDto.getAnalysisStatus());
            operateLogObjectDto.setStartTime(eventAnalysisTaskDto.getStartTime());
            operateLogObjectDto.setEndTime(eventAnalysisTaskDto.getEndTime());
            operateLogObjectDto.setAnalysisUpToTime(eventAnalysisTaskDto.getAnalysisUpToTime());
            operateLogObjectDto.setAnalysisSchedule(eventAnalysisTaskDto.getAnalysisSchedule());
            operateLogObjectDto.setAnalysisScheduleTips(eventAnalysisTaskDto.getAnalysisScheduleTips());
            operateLogObjectDto.setTaskTicket(eventAnalysisTaskDto.getTaskTicket());
            operateLogObjectDto.setStatus(eventAnalysisTaskDto.getStatus());
            operateLogObjectDto.setTaskCreateTime(eventAnalysisTaskDto.getCreateTime());
            operateLogObjectDto.setTaskUpdateTime(eventAnalysisTaskDto.getUpdateTime());
            operateLogObjectDto.setAnalysisWbContentType(eventAnalysisTaskDto.getAnalysisWbContentType());
            operateLogObjectDto.setUpdateNum(eventAnalysisTaskDto.getUpdateNum());
            operateLogObjectDto.setCreateType(eventAnalysisTaskDto.getCreateType());
            operateLogObjectDto.setOptions(eventAnalysisTaskDto.getOptions());
            operateLogObjectDto.setMatchType(eventAnalysisTaskDto.getMatchType());
            operateLogObjectDto.setOrigin(eventAnalysisTaskDto.getOrigin());
            operateLogObjectDto.setAnalysisTotalConsumeExpect(eventAnalysisTaskDto.getAnalysisTotalConsumeExpect());
            operateLogObjectDto.setAnalysisSolrFirstCountExpect(eventAnalysisTaskDto.getAnalysisSolrFirstCountExpect());
            operateLogObjectDto.setSensitive(eventAnalysisTaskDto.isSensitive());
            operateLogObjectDto.setEstimateMinute(eventAnalysisTaskDto.getEstimateMinute());

        }


        return operateLogObjectDto;
    }

    /**
     * 检查手机号码
     *
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            return MOBILE_PATTERN.matcher(mobile).matches();
        }

        return false;
    }

    /**
     * 获取手机号所在省份
     *
     */
    public static String getMobileProvince(String mobile){
        String province = null;
        if(CommonUtils.checkMobile(mobile)){
            try {
                String url = SysConfig.cfgMap.get("TAOBAO_MOBILE_PROVINCE_API_URL").replace("$(mobile)", mobile);
                String res = CommonUtils.sendGet(url, null);
                if(StringUtils.isNotBlank(res) && res.indexOf(mobile) > 0){
                    Matcher m = BLANK_SPACE_PATTERN.matcher(res);
                    res = m.replaceAll("").replace("__GetZoneResult_=", "");
                    JSONObject obj = JSONObject.parseObject(res);
                    if(obj != null){
                        province = obj.getString("province");
                    }
                }

                if(StringUtils.isNotBlank(province)){
                    url = SysConfig.cfgMap.get("BAIDU_MOBILE_PROVINCE_API_URL").replace("$(mobile)", mobile);
                    res = CommonUtils.sendGet(url, null);
                    if(StringUtils.isNotBlank(res)){
                        res = res.replace("/*fgg_again*/phone(", "").replace(")", "");
                        JSONObject obj = JSONObject.parseObject(res);
                        if(obj != null){
                            province = obj.getJSONObject("data").getString("area");
                        }
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return province;
    }

    public static List<Integer> strListToIntList(List<String> strList){
        List<Integer> ids = new ArrayList<>();
        if(CollectionUtils.isEmpty(strList)){
            return ids;
        }
        return strList.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());
    }

    public static Integer parseInviteUserEncode(String metedata){
        if (StringUtils.isBlank(metedata)) {
            return null;
        }
        String userId = metedata.replace(metedata.substring(0, 4), "").replace(metedata.substring(metedata.length()-4, metedata.length()), "");
        if (metedata.equals(buildInviteUserEncode(userId))) {
            return Integer.valueOf(userId);
        }
        return null;
    }

    public static String buildInviteUserEncode(String metedata){
        try {
            if (StringUtils.isBlank(metedata)) {
                return null;
            }
            String mD5Encode = MD5Utils.MD5Encode(metedata + "$$$$");
            metedata = mD5Encode.substring(0, 4) + metedata + mD5Encode.substring(mD5Encode.length()-4, mD5Encode.length());
        } catch (Exception e) {
            return null;
        }
        return metedata;
    }

    /**
     * 密码强度
     *
     * @author zhangrui
     * @param passwordStr
     * @return
     */
    public static int getPasswordStrength(String passwordStr) {
        String str1 = "^[0-9]{6,64}$"; // 不超过20位的数字组合
        String str2 = "^[a-zA-Z]{6,64}$"; // 由字母不超过20位
        String str3 = "^[0-9|a-z|A-Z]{6,64}$"; // 由字母、数字组成，不超过20位

        if (passwordStr.matches(str1)) {
            return 1;
        }
        if (passwordStr.matches(str2)) {
            return 2;
        }
        if (passwordStr.matches(str3)) {
            return 3;
        }

        return 3;
    }

    /**
     * 将用户信息存储到jedis
     *
     * @param request
     * @param user
     * @return
     */
    public static String setLoginJedis(HttpServletRequest request, UserBO user) {
        if (user != null) {
            String loginKey = RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_LOGIN).append(user.getUserId()).append("_").append(System.currentTimeMillis()).toString());

            return setLoginJedis(request, loginKey, user);
        }

        return null;
    }

    /**
     * 将用户信息存储到jedis
     *
     * @param request
     * @param sid
     * @param user
     * @return
     */
    public static String setLoginJedis(HttpServletRequest request, String sid, UserBO user) {
        if (StringUtils.isNotBlank(sid) && user != null) {
            int platform = Integer.parseInt(request.getParameter("platform"));

            int expiryIn = SystemConstants.JEDIS_SSO_EXPIRY;
            if (platform == BusinessConstant.PLATFORM_TYPE_WEB) {
                expiryIn = 7200;
                if (user.getUserChannel() == BusinessConstant.USER_CHANNEL_INTERNAL || (user.getProUserValidEndTime() != null && user.getProUserValidEndTime().getTime() > System.currentTimeMillis() && user.getUserType() != BusinessConstant.USER_TYPE_BASE)) {
                    expiryIn = SystemConstants.JEDIS_SSO_EXPIRY;
                }
            }

            if (platform == BusinessConstant.PLATFORM_TYPE_APP) {
                expiryIn = 86400;
            }
            RedisUtils.setAttribute(sid, JSONObject.toJSONString(user), expiryIn);

            return sid;
        }

        return null;
    }

    /**
     * 刷新
     *
     * @param key
     * @param pattern
     */
    public static void refreshSessionUser(String key, String pattern, UserBO user) {
        if ((StringUtils.isNotBlank(key) || StringUtils.isNotBlank(pattern)) && user != null) {
            Set<String> keys = new HashSet<>();
            if (StringUtils.isNotBlank(key)) {
                keys.add(key);
            }
            else if (StringUtils.isNotBlank(pattern)) {
                keys = RedisUtils.keys(pattern);
            }

            if (CollectionUtils.isNotEmpty(keys)) {
                for (String k : keys) {
                    String sessionUserStr = RedisUtils.getAttribute(k);
                    if (StringUtils.isNotBlank(sessionUserStr)) {
                        UserBO sessionUser = JSONObject.parseObject(sessionUserStr, UserBO.class);
                        if (sessionUser != null) {
                            user.setSubUser(sessionUser.getSubUser());
                            user.setSubUserId(sessionUser.getSubUserId());
                        }
                    }

                    Long ttl = RedisUtils.getExpire(k);
                    if (ttl == null || ttl < 0) {
                        RedisUtils.setAttribute(k, JSONObject.toJSONString(user));
                    }else {
                        RedisUtils.setAttribute(k, JSONObject.toJSONString(user), Integer.parseInt(ttl.toString()));
                    }
                }
            }
        }
    }


}
