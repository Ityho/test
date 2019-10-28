package com.miduchina.wrd.eventanalysis.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.user.UserCreateThirdpartyDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.config.CommonAPIConfig;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.log.model.UserRes;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.LoginUtils;
import com.miduchina.wrd.eventanalysis.weibo4j.Users;
import com.miduchina.wrd.eventanalysis.weibo4j.authorize.Account;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.po.user.UserThirdpartyAuthInfo;
import com.miduchina.wrd.util.HttpRequestUtils;
import com.tuke.gospel.core.util.SimpleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @auther yho
 * @vreate 2019-08 15:43
 */
@Slf4j
@RequestMapping(value = "/thirdParty")
@Controller
public class ThirdPartyController extends BaseController {
    @Autowired
    private APIInfoClient apiInfoClient;
    @Autowired
    private OrderClientService orderClientService;

    /**
     * 调用新浪微博登录
     *
     * @throws Exception
     */
    @RequestMapping(value = "/toSinaWeiboLogin")
    @ResponseBody
    public void toSinaWeiboLogin() throws Exception {
        com.miduchina.wrd.eventanalysis.weibo4j.authorize.Oauth oauth = new com.miduchina.wrd.eventanalysis.weibo4j.authorize.Oauth();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.sendRedirect(oauth.authorize("code"));
//		BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
    }

    /**
     * 新浪微博登录回调
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sinaWeiboCallback")
    @ResponseBody
    public String sinaWeiboCallback() throws Exception {
        log.info("sinaWeibo Callback --> begin");
        System.out.println("SinaWeibo Callback --> start");
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        String code = request.getParameter("code");
        log.info("sinaWeibo Callback --> code" + code);
        if (code == null || "".equals(code)) {
            log.info("SinaWeibo Callback --> code is null");
            return "index_local";
        }

        // 获取access_token
        com.miduchina.wrd.eventanalysis.weibo4j.authorize.AccessToken accessToken = null;
        try {
            accessToken = new com.miduchina.wrd.eventanalysis.weibo4j.authorize.Oauth().getAccessTokenByCode(code);
        } catch (Exception e) {
            System.out.println("SinaWeibo Callback --> " + System.currentTimeMillis() + " error");
            e.printStackTrace();
        }
        if (accessToken == null) {
            log.info("SinaWeibo Callback --> access_token is null");
            return "index_local";
        } else {
            log.info("SinaWeibo Callback --> access_token is " + accessToken.getAccessToken());
        }

        // 获取uid
        Account account = new Account(accessToken.getAccessToken());
        com.miduchina.wrd.eventanalysis.weibo4j.JSONObject uid = account.getUid();
        log.info("SinaWeibo Callback --> uid is " + uid.getString("uid"));

        // 获取用户信息（昵称+头像）
        Users users = new Users(accessToken.getAccessToken());
        com.miduchina.wrd.eventanalysis.weibo4j.User sinaUser = users.showUserById(uid.getString("uid"));

        String nickName = sinaUser.getScreenName();
        log.info("SinaWeibo Callback --> nickName is " + nickName);
        String userHead = sinaUser.getAvatarLarge();
        log.info("SinaWeibo Callback --> userHead is " + userHead);
        System.out.println("SinaWeibo Callback --> " + System.currentTimeMillis() + " userHead is " + userHead);

//        UserExtraInfo userExtraInfo = new UserExtraInfo();
//        userExtraInfo.setInfo(JSON.toJSONString(sinaUser));

        String rtnStr = doThirdpartyLogin(request, response, sinaUser.getId(), accessToken.getAccessToken(), accessToken.getRefreshToken(), Long.parseLong(accessToken.getExpireIn()), BusinessConstant.THIRDPARTY_TYPE_SINA, nickName, userHead, String.valueOf(sinaUser.getVerifiedType()),JSON.toJSONString(sinaUser));
        if(rtnStr == null || "".equals(rtnStr.trim())){
            return "index_local";
        }
        UserRes userRes = JSON.parseObject(rtnStr, UserRes.class);
        boolean flag = userRes.isRegister();
        return flag? "index_local" : "view/home/home";

    }

    /**
     * 调用微信登录
     *
     * @throws Exception
     */
    @RequestMapping(value = "/toWeixinLogin")
    @ResponseBody
    public void toWeixinLogin() throws Exception {
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixinconnectconfig.properties"));

        String codeURL = props.getProperty("codeURL").trim() + "?appid=" + props.getProperty("AppID").trim() + "&redirect_uri=" + props.getProperty("redirectURI").trim() + "&response_type=code&scope=snsapi_login";
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.sendRedirect(codeURL);
    }

    /**
     * 微信登录回调
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/weixinCallBack")
    @ResponseBody
    public String weixinCallBack() throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        boolean flag = false;
        String code = request.getParameter("code");
        if (code == null || "".equals(code)) {
            log.info("Weixin Callback --> code is null");
            return "index_local";
        }

        // 获取access_token
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixinconnectconfig.properties"));
        String accessTokenURL = props.getProperty("accessTokenURL").trim() + "?appid=" + props.getProperty("AppID").trim() + "&secret=" + props.getProperty("AppSecret").trim() + "&code=" + code + "&grant_type=authorization_code";
        String result = HttpRequestUtils.sendGet(accessTokenURL);
        if (result == null || "".equals(result.trim()))
            return "index_local";
        JSONObject json = JSONArray.parseObject(result);
        String accessToken = json.getString("access_token");
        log.info("Weixin Callback --> access_token is " + accessToken);
        String openId = json.getString("openid");
        log.info("Weixin Callback --> open_id is " + openId);
        long expiressIn = Long.parseLong(json.getString("expires_in"));
        log.info("Weixin Callback --> expiress_in is " + expiressIn);
        String refresh_token = json.getString("refresh_token");
        log.info("Weixin Callback --> refresh_token is " + refresh_token);
        String unionId = json.getString("unionid");
        log.info("Weixin Callback --> union_id is " + unionId);

        // 获取用户信息
        String userInfoURL = props.getProperty("userInfoURL").trim() + "?access_token=" + accessToken + "&openid=" + openId;
        result = HttpRequestUtils.sendGet(userInfoURL);
        if (result == null || "".equals(result.trim())) {
            log.info("Weixin Callback --> userInfo is null");
            return "index_local";
        }

        json = JSONArray.parseObject(result);
        String nickName = json.getString("nickname");
        nickName = SimpleUtils.stringConvert(nickName, "ISO-8859-1", "UTF-8");
        log.info("Weixin Callback --> nickName is " + nickName);
        String userHead = json.getString("headimgurl");
        log.info("Weixin Callback --> userHead is " + userHead);

//        UserExtraInfo userExtraInfo = new UserExtraInfo();
//        userExtraInfo.setInfo(result);
        String rtnStr = doThirdpartyLogin(request, response, unionId, accessToken, refresh_token, expiressIn, BusinessConstant.THIRDPARTY_TYPE_WX, nickName, userHead, null,result);
        if(rtnStr == null || "".equals(rtnStr.trim())){
            return "index_local";
        }
        UserRes userRes = JSON.parseObject(rtnStr, UserRes.class);
        flag = userRes.isRegister();
        return flag? "index_local" : "view/home/home";
    }

    /**
     * 用户第三方授权登陆
     * @return
     */
    private String doThirdpartyLogin(HttpServletRequest request, HttpServletResponse response, String uid, String accessToken, String refreshToken, long expireIn, int platformType, String nickName, String userHead, String verifiedType,String extraInfo){
        System.out.println("--------doThirdpartyLogin--------Strat----------");
        String rtnPage = "success";
        UserThirdpartyAuthInfo userThirdpartyAuthInfo = new UserThirdpartyAuthInfo();
        userThirdpartyAuthInfo.setThirdpartyUserId(uid);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("userThirdpartyAuthInfo",userThirdpartyAuthInfo);
        BaseDto baseDto = apiInfoClient.queryUserThirdpartyAuthInfoByUid(map1);
        UserThirdpartyAuthInfo infoTemp = (UserThirdpartyAuthInfo) baseDto.getData();
        if (infoTemp == null)
            rtnPage = "toBindThirdParty";
        try {
            fetchProductlist(request,null,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserCreateThirdpartyDto userCreateThirdpartyVO = new UserCreateThirdpartyDto();
        userCreateThirdpartyVO.setUid(uid);
        userCreateThirdpartyVO.setAccessToken(accessToken);
        userCreateThirdpartyVO.setRefreshToken(refreshToken);
        userCreateThirdpartyVO.setExpireIn(expireIn);
        userCreateThirdpartyVO.setPlatformType(platformType);
        userCreateThirdpartyVO.setAppUserStatus(2);
        userCreateThirdpartyVO.setUserChannel(BusinessConstant.USER_CHANNEL_WYQ);
        userCreateThirdpartyVO.setExtraInfo(extraInfo);
        //赠送产品ID
        List<ProductPackage> packageListFree = orderClientService.findProductPackageByType(request,BusinessConstant.PRODUCT_PACKAGE_TASTE);
        userCreateThirdpartyVO.setGiftPackageId(CollectionUtils.isEmpty(packageListFree) ? 1 : packageListFree.get(0).getProductPackageId());
        userCreateThirdpartyVO.setNickname(nickName);
        userCreateThirdpartyVO.setUserHead(userHead);
        userCreateThirdpartyVO.setIsLogin(true);
        userCreateThirdpartyVO.setVerifiedType(verifiedType);

        String userExclusiveChannelCode = doUecChannel(request);
        if (StringUtils.isNotBlank(userExclusiveChannelCode))
            userCreateThirdpartyVO.setExclusiveChannelCode(userExclusiveChannelCode);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userParam", JSON.toJSONString(userCreateThirdpartyVO));
        System.out.println("-------URL-----------"+ SysConfig.cfgMap.get("API_INTRA_BUSINESS_URL")+ CommonAPIConfig.getValue("WYQ_THIRDPARTY_API")+"?"+params);
//		String rtnStr = HttpRequestUtils.sendPost(SysConfig.API_INTRA_BUSINESS_URL+CommonAPIConfig.getValue("WYQ_THIRDPARTY_API"), params);

        String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, CommonAPIConfig.getValue("WYQ_THIRDPARTY_API"), params);
        System.out.println("-----------rtnStr----------"+rtnStr);
        log.info("ThirdPartyAction doThirdpartyLogin rtnStr = [" + rtnStr + "]");

        if (StringUtils.isBlank(rtnStr))
            return "view/error/error";

        UserRes userRes = JSON.parseObject(rtnStr, UserRes.class);
        System.out.println("-----------userRes code----------"+userRes.getCode());
        if (userRes == null || !BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode()))
            return "view/error/error";

        //String memcacheSSOFlag = RequestInterFace.setUserForMemcachedWyq(request, response, userRes.getUserInfo().getUserId() + userRes.getUserInfo().getUsername(), userRes.getSid(), SystemConstants.SYS_ADMIN_CACHE_TIME, SysConfig.memoCacheName);
        //SessionUtil.setAttribute(Constants.MEMCACHE_SSO_FLAG + "_" + SysConfig.memoCacheName + userRes.getUserInfo().getUserId(), userRes.getUserInfo().getUserId() + "", memcacheSSOFlag, new Date().getTime() + Constants.MEMCACHE_SSO_EXPIRY);
        LoginUtils.saveLoginFlag(request, response, userRes);
        System.out.println("--------doThirdpartyLogin--------End----------");
        System.out.println("--------doThirdpartyLogin--------result---is----------"+rtnPage);
        return rtnStr;
    }
    /**
     *@see
     *
     * @param req
     *            请求
     * @return uecCode
     *            返回找到的渠道代码，有返回代码，没有返回 null
     *
     */
    public String doUecChannel(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        String uecCode = null;
        System.out.println("doUecChannel().cookies = "+cookies);
        if(cookies != null){
            for (Cookie cookie : cookies) {
                System.out.println("doUecChannel().Cookie getName=" + cookie.getName());
                System.out.println("doUecChannel().Cookie getDomain=" + cookie.getDomain());
                System.out.println("doUecChannel().Cookie getValue=" + cookie.getValue());
                // 火车票类型单独判断
                if(StringUtils.isNotEmpty(cookie.getName()) && cookie.getName().equals(BusinessConstant.UEC_CHANNEL_CODE+"_"+BusinessConstant.UEC_CHANNEL_MHCP)){
                    uecCode = BusinessConstant.UEC_CHANNEL_MHCP+"_"+cookie.getValue();
                    break;
                }else if(StringUtils.isNotEmpty(cookie.getName()) && cookie.getName().contains(BusinessConstant.UEC_CHANNEL_CODE)){
                    //其它渠道类型
                    String[] userinfos = cookie.getName().split("_");
                    uecCode = userinfos[1]+"_"+cookie.getValue();
                    break;
                }
            }
        }
        System.out.println("doUecChannel().uecCode="+uecCode);
        return uecCode;
    }
}
