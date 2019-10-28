package com.miduchina.wrd.webthermalquery.util;


import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.util.MD5Utils;
import com.miduchina.wrd.webthermalquery.fegin.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author  qiuqiu
 * @date 创建时间：2018年3月18日 下午2:34:03
 */
@Slf4j
@Component
public class LoginUtils {

    @Autowired
    private  UserFeignClient userFeignClient;
//    @Autowired
//    @Qualifier("stringRedisTemplate")
//    private  StringRedisTemplate redisTemplate;
    private static LoginUtils messageset;
    @PostConstruct //服务器加载时运行，只运行一次
    public void init(){
        messageset = this;
//        messageset.redisTemplate = this.redisTemplate;
        messageset.userFeignClient = this.userFeignClient;
    }
    /**
     * 设置cookie
     * return sid
     */
    public static void setSidToCookie(HttpServletRequest request, HttpServletResponse response, String sid) {

        //存入登录缓存
        String domain = getBaseUrl(getUrlPrefix(request));
        Cookie cookie = new Cookie(getCookieName(), sid);
        cookie.setMaxAge(SystemConstants.SYS_ADMIN_CACHE_TIME);
        if ((domain != null) && (domain.indexOf('.') != -1)) {
            cookie.setDomain('.' + domain);
        }
        cookie.setPath(SystemConstants.COOKIE_PATH);
        response.addCookie(cookie);
    }

    /**
     * 从cookie获取sid
     */
    public static String getSidFromCookie(HttpServletRequest request) {
        String sid = null;
        Cookie cookie = getCookie(request, getCookieName());
        if ((cookie != null) && (!cookie.getValue().isEmpty())) {
            sid = cookie.getValue();
            if(StringUtils.isNotBlank(sid)){
                String[] utype = sid.split("_");
                if ((utype == null) || ("".equals(utype)) || (utype.length < 5)) {
                    log.info("utype is null or utype.length < 5, sid={}",sid);
                    sid = null;
                }
            }
        } else {
            log.info("cookie is null ");
        }
        return sid;
    }

    /**
     * 从cookie获取sid
     */
    public static Integer getUserIdFromSid(String sid) {
        Integer userId = null;
        Pattern pattern = Pattern.compile("^[\\d]*$");
        if(StringUtils.isNotBlank(sid)){
            String[] strs = sid.split("_");
            if(strs.length == 5 && pattern.matcher(strs[3]).matches()){
                userId = Integer.valueOf(strs[3]);
            }
        }
        return userId;
    }

    public static Integer getUserIdFromCookie(HttpServletRequest request){
        return getUserIdFromSid(getSidFromCookie(request));
    }

    /**
     * 登录操作：
     * 1.验证登录次数
     * 2.验证单个cookie登录用户数
     * 3.sid存入cookie
     * 4.添加子账号表示或单点登录标识
     */
    public static String doLogin(HttpServletRequest request, HttpServletResponse response, UserDto userDto, boolean isCheckLoginNum){

        if(userDto != null ){

            String userId = userDto.getUserId().toString();

            boolean isPro = false;
            if(userDto.getProUserValidEndTime() != null && userDto.getProUserValidEndTime().getTime() > System.currentTimeMillis()){
                isPro = true;
            }

            //验证登录次数
            if(isCheckLoginNum && !isPro && "www".equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME"))){
                String loginCountLimitKey = RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_LOGIN_COUNT_LIMIT).append(userDto.getUserId()).toString());
//                String loginCountLimitValue = messageset.redisTemplate.opsForValue().get(loginCountLimitKey);
                String loginCountLimitValue = RedisUtils.getAttribute(loginCountLimitKey);
                if (StringUtils.isNotBlank(loginCountLimitValue)) {
                    doLogout(request, response, userDto.getSid());
                    return "登录过于频繁，请明天再试！";
                }else{
                    int loginNum = 0;
                    try {
                        Map<String, Object> param = new HashMap<String, Object>();
                        param.put("userId",userDto.getUserId());
                        BaseDto todayLoginCountByUserId = messageset.userFeignClient.findTodayLoginCountByUserId(param);
                        loginNum = (int) todayLoginCountByUserId.getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(loginNum > Integer.valueOf(SysConfig.cfgMap.get("USER_MAX_LOGIN_COUNT"))){
                        // 获取距当天零点的时间差
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        Long timeDifference = calendar.getTime().getTime() - (new Date().getTime());
//                        messageset.redisTemplate.opsForValue().set(loginCountLimitKey, "1", (int) (timeDifference / 1000));
                        RedisUtils.setAttribute(loginCountLimitKey, "1", (int) (timeDifference / 1000));
                        log.info("login too more : User [" + userDto.getUsername() + "] Time = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
                        doLogout(request, response, userDto.getSid());
                        return "登录过于频繁，请明天再试！";
                    }
                }
            }

            //记录单个cookie登录用户数
            String singleCookieJedisFlag = "";
            Cookie singlecookie = getCookie(request, getSingleCookieName());
            if(singlecookie != null){
                singleCookieJedisFlag = singlecookie.getValue();
            }
            if(StringUtils.isNotBlank(singleCookieJedisFlag)){
//                String singlecookieUserStr = messageset.redisTemplate.opsForValue().get(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SINGLE_COOKIE_FLAG + singleCookieJedisFlag));
                String singlecookieUserStr = RedisUtils.getAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SINGLE_COOKIE_FLAG + singleCookieJedisFlag));
                if(StringUtils.isNotBlank(singlecookieUserStr)){
                    List<String> singlecookieUsers = Arrays.asList(singlecookieUserStr.split(","));
                    if(!singlecookieUsers.contains(userId)){
                        if(singlecookieUsers.size() >= SystemConstants.SINGLE_COOKIE_MAX_USER_NUM){
                            doLogout(request, response, userDto.getSid());
                            return "登录失败！";
                        }else{
                            singlecookieUserStr += "," + userId;
                        }
                    }
                }else{
                    singlecookieUserStr = userId;
                }
//                messageset.redisTemplate.opsForValue().set(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SINGLE_COOKIE_FLAG + singleCookieJedisFlag), singlecookieUserStr, 24*3600);
                RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SINGLE_COOKIE_FLAG + singleCookieJedisFlag), singlecookieUserStr,SystemConstants.SESSION_TIME_ONE_DAY);
            }else{
                singleCookieJedisFlag = getSingleCookieFlag(userId);
//                messageset.redisTemplate.opsForValue().set(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SINGLE_COOKIE_FLAG + singleCookieJedisFlag), userId, 24*3600);
                RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SINGLE_COOKIE_FLAG + singleCookieJedisFlag), userId, SystemConstants.SESSION_TIME_ONE_DAY);
                String domain = getBaseUrl(getUrlPrefix(request));
                Cookie cookie = new Cookie(getSingleCookieName(), singleCookieJedisFlag);
                cookie.setMaxAge(SystemConstants.SINGLE_COOKIE_TIME);
                if ((domain != null) && (domain.indexOf('.') != -1)) {
                    cookie.setDomain('.' + domain);
                }
                cookie.setPath(SystemConstants.COOKIE_PATH);
                response.addCookie(cookie);
            }


            String sid = userDto.getSid();
            Boolean subUser = userDto.isSubUser();
            //sid存入cookie
            LoginUtils.setSidToCookie(request, response, sid);
            //非子账号登录记录单点登录标识
            if (!userDto.isSubUser()){
//                messageset.redisTemplate.opsForValue().set(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SSO_FLAG + userId), sid, SystemConstants.SYS_ADMIN_CACHE_TIME);
                RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SSO_FLAG + userId), sid, SystemConstants.SYS_ADMIN_CACHE_TIME);
            }else{
//                messageset.redisTemplate.opsForValue().set(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SUB_USER + sid), subUser.toString(), SystemConstants.SYS_ADMIN_CACHE_TIME);
                RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SUB_USER + sid), subUser.toString(), SystemConstants.SYS_ADMIN_CACHE_TIME);
            }
            // 加入异常监测标识
            AbnormalAnalysisUtil.setAblesc();
            return CodeConstant.SUCCESS_CODE;
        }else{
            return "获取用户信息失败！";
        }
    }

    /**
     * 退出登录
     */
    public static void doLogout(HttpServletRequest request, HttpServletResponse response, String sid){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sid", sid);
        BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userLogoutURL"), params);
        clearCookieSid(request, response);
    }

    /**
     * 清除cookie中的sid
     */
    public static void clearCookieSid(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = getCookie(request, getCookieName());
        if ((cookie != null) && (!cookie.getValue().isEmpty())) {
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        } else {
            log.info("cookie is null ");
        }
    }

    public static String getCookieName(){
        return new StringBuilder(SystemConstants.SESSION_WEB_FLAG_OF_COOKIE).append("_").append(SysConfig.cfgMap.get("WEBID_COOKIE_NAME") ).toString();
    }

    public static String getSingleCookieName(){
        return "CNZZSINGLE" + MD5Utils.MD5Encode("wyq_cookie").substring(0, 6);
    }


    public static String getEncryptCookieName(){
        return "CNZZENCRYPT" + MD5Utils.MD5Encode("wyq_cookie").substring(0, 6);
    }

    public static String getSingleCookieFlag(String userId){
        return MD5Utils.MD5Encode(System.currentTimeMillis() + userId);
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName){
        if(request == null || StringUtils.isBlank(cookieName)){
            return null;
        }
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookieName.equals(cookies[i].getName())) {
                cookie = cookies[i];
            }
        }
        return cookie;
    }

    public static String getBaseUrl(String url) {
        try {
            URL burl = new URL(url);
            String host = burl.getHost();
            if (host.endsWith(".")) {
                host = host.substring(0, host.length() - 1);
            }
            int index = 0;
            String candidate = host;
            index = candidate.indexOf('.');
            return candidate.substring(index + 1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getUrlPrefix(HttpServletRequest request){
        StringBuffer url = new StringBuffer(request.getScheme());
        url.append("://");
        url.append(request.getServerName());
        int port = request.getServerPort();
        if (port != 80) {
            url.append(":");
            url.append(port);
        }
        return url.toString();
    }


}

