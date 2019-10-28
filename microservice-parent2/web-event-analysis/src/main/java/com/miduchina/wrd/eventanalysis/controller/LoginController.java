package com.miduchina.wrd.eventanalysis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.user.UserCreateThirdpartyDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.dto.user.UserRightsRechargeRecord;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.config.CommonAPIConfig;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.log.model.UserRes;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.utils.LoginUtils;
import com.miduchina.wrd.eventanalysis.utils.PayUtil;
import com.miduchina.wrd.eventanalysis.weibo4j.AuthUtils;
import com.miduchina.wrd.eventanalysis.weibo4j.Users;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.h5.AuthJumpRelation;
import com.miduchina.wrd.po.h5.H5ShortUrl;
import com.miduchina.wrd.po.h5.WeiXinMaterial;
import com.miduchina.wrd.po.hotspot.NotLoginOperateRecord;
import com.miduchina.wrd.po.user.User;
import com.miduchina.wrd.po.user.UserRegRewardRecord;
import com.miduchina.wrd.po.user.UserThirdpartyAuthInfo;
import com.miduchina.wrd.util.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.*;

@Controller
@Slf4j
public class LoginController extends BaseController{
    @Autowired
    private OrderClientService orderClientService;

    @RequestMapping(value = "userLogin")
    public String index(HttpServletRequest req ,HttpServletResponse rep,String urlType) throws IOException {
        System.out.println("----"+req.getHeader("Referer"));
        if(org.apache.commons.lang3.StringUtils.isBlank(urlType)){
            urlType = req.getHeader("Referer");
        }else{
            urlType = URLDecoder.decode(urlType);
        }
        System.out.println("MemoCacheName:"+SysConfig.cfgMap.get("WEBID_COOKIE_NAME"));
        if (SysConfig.cfgMap.get("WEBID_COOKIE_NAME").equals("www")) {
            rep.sendRedirect("http://d.51wyq.cn");
        }
        return "index_local";
    }

    @RequestMapping(value = "goComplimentaryPointsResult")
    public ModelAndView goComplimentaryPointsResult(ModelAndView modelAndView, HttpServletRequest req){
        fetchSessionAdmin(req);
        if(admin != null){
            if(StringUtils.isNotBlank(admin.getMobile())){
                BaseDto baseDto=apiInfoClient.queryRegisterCreditByMobile(admin.getMobile());
                if(baseDto!=null){
                    if(baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                        if(baseDto.getData() != null){
                            UserRegRewardRecord urrr= (UserRegRewardRecord) baseDto.getData();
                            Integer stretchCreditAmount = urrr.getCreditAmount();
                            modelAndView.addObject("stretchCreditAmount",stretchCreditAmount);
                        }
                    }
                }
            }else{
                //活动页进入
                Cookie[] cookies = req.getCookies();
                if(cookies != null){
                    for (Cookie cookie : cookies) {
                        if(cookie.getName().equals(BusinessConstant.STRETCH_MOBILE_CODE)){
                            BaseDto baseDto=apiInfoClient.checkMobile(cookie.getValue());
                            if(!(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null)){
                                //获取手机号赠送的积分
                                BaseDto baseDto1=apiInfoClient.queryRegisterCreditByMobile(cookie.getValue());
                                if(baseDto1!=null && baseDto1.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto1.getData()!=null){
                                    UserRegRewardRecord urrr = (UserRegRewardRecord) baseDto1.getData();
                                    if(urrr != null && urrr.getStatus() != 0){
                                        //把活动手机号用户绑定至微博中打开活动界面授权进来的用户
                                        admin.setMobile(cookie.getValue());
                                        //把赠送积分给用户
                                        admin.setCreditAmount(admin.getCreditAmount() + urrr.getCreditAmount());
                                        apiInfoClient.updateUser(admin);
                                        //修改赠送积分用户的状态，被领取
                                        apiInfoClient.queryRegisterCreditByMobile(cookie.getValue());
                                        //用户权益变动
                                        UserRightsRechargeRecord rechargeRecord=new UserRightsRechargeRecord();
                                        rechargeRecord.setUserId(Integer.valueOf(admin.getUserId()));
                                        rechargeRecord.setRelationId(urrr.getId());
                                        rechargeRecord.setType(BusinessConstant.USER_RIGHTS_RECHARGE_TYPE_ADD);
                                        rechargeRecord.setCount(urrr.getCreditAmount());
                                        rechargeRecord.setCurrentCount(admin.getCreditAmount());
                                        rechargeRecord.setItem(BusinessConstant.USER_RIGHTS_RECHARGE_ITEM_CA);
                                        rechargeRecord.setRecordDesc("赠送100000微积分");
                                        rechargeRecord.setPlatform(BusinessConstant.RECHARGE_RECORD_PLATFORM_TYPE_WYQ_H5);
                                        rechargeRecord.setStatus(BusinessConstant.STATUS_VALID);
                                        rechargeRecord.setCreateTime(new Date());
                                        apiInfoClient.addRightsRechargeRecord(rechargeRecord);
                                        int stretchCreditAmount = urrr.getCreditAmount();
                                        modelAndView.addObject("stretchCreditAmount",stretchCreditAmount);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        modelAndView.setViewName("view/stretch/complimentaryPointsResult");
        return modelAndView;
    }

    //微博授权
    /**
     * 新浪微博轻应用授权
     *
     * @throws Exception
     */
    @RequestMapping(value = "view/user/sinaAuth")
    public ModelAndView sinaAuth(ModelAndView modelAndView) throws Exception {
        boolean flag = false;
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = servletRequestAttributes.getRequest();
        String signedRequest = req.getParameter("signed_request");
//        signedRequest = "IKdL2cxt_fyB6uLAGIfip5BhgW6DaBP1BCnQvXIA1fE.eyJ1c2VyIjp7ImNvdW50cnkiOiJjbiIsImxvY2FsZSI6IiIsInZlcnNpb24iOjV9LCJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImlzc3VlZF9hdCI6MTU2ODY0NTQwNCwiZXhwaXJlcyI6MjUyNzY3Miwib2F1dGhfdG9rZW4iOiIyLjAwR0dvZWdIbnZLc1JCYzM2OGE2OTQ4OTBUSUpTdyIsInVzZXJfaWQiOjcwNDMyNjE2MzQsInJlZmVyZXIiOiIiLCJzY29wZSI6IiIsImV4dF9kYXRhIjoiIiwib3VpZCI6IjM5NjAwMzc3ODAiLCJvcmlnaW4iOiJwcmV2aWV3In0";
//        String signedRequest = "dSV7KoK_SVTDmymSf6Sp5yGqRQuau99rc4I6NJmDbn8.eyJ1c2VyIjp7ImNvdW50cnkiOiJjbiIsImxvY2FsZSI6IiIsInZlcnNpb24iOjV9LCJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImlzc3VlZF9hdCI6MTU2NjgwNjIwMywiZXhwaXJlcyI6MTI2MTk3LCJvYXV0aF90b2tlbiI6IjIuMDBlMWF5RkV2ZjN6NEI1MmVlM2Q5MTQ0MFpkQWd4IiwidXNlcl9pZCI6Mzc1Mjg1MjM1MiwicmVmZXJlciI6IiIsInNjb3BlIjoiIiwiZXh0X2RhdGEiOiIiLCJvdWlkIjoiNjA5NzA1MDcxMyIsIm9yaWdpbiI6InRlc3RfcHJldmlldyJ9";
        HttpServletResponse response = servletRequestAttributes.getResponse();
        if (signedRequest == null) {
            log.info("Sina Auth --> signed_request is null");
//            return "sinaAuthInputLocal";
            modelAndView.setViewName("index_local");
            return modelAndView;
//            return "index_local";
//            return "redirect:http://baidu.com/downloadRequestElecCont.action?contNo="+contNo;
        }
        log.info("Sina Auth --> signed_request is " + signedRequest);

        // 获取access_token
        Map<String, String> tokenMap = AuthUtils.parseSignedRequest(signedRequest);
        if (tokenMap == null) {
            log.info("Sina Auth --> access_token is null");
//            return "sinaAuthInput";
            modelAndView.setViewName("myviews/index");
            return modelAndView;
//            return "myviews/index";
        }
        String accessToken = tokenMap.get("access_token");
        log.info("Sina Auth --> access_token is " + accessToken);

        long expires = 0L;
        if (tokenMap.get("expires") != null && !"".equals(tokenMap.get("expires"))){
            expires = Long.parseLong(tokenMap.get("expires"));
        }
        log.info("Sina Auth --> expires is " + expires);
        String uid = tokenMap.get("uid");
        log.info("Sina Auth --> uid is " + uid);
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(uid)) {
            log.info("Sina Auth --> accessToken or uid is null");
//            return "sinaAuthInput";
            modelAndView.setViewName("myviews/index");
            return modelAndView;
//            return "myviews/index";
        }


        Users users = null;
        com.miduchina.wrd.eventanalysis.weibo4j.User  sinaUser = null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("thirdpartyAccount",uid);
        BaseDto userByThirdpartyccount = apiInfoClient.findUserByThirdpartyccount(map);
        User user=null;
        if(userByThirdpartyccount.getData()!=null){
            String data = JSON.toJSONString(userByThirdpartyccount.getData());
            user = JSONObject.parseObject(data, User.class);
        }
        if(user == null) {
            // 获取用户信息（昵称+头像）
            users = new Users(accessToken);
            try {
                sinaUser = users.showUserById(uid);
            } catch (Exception e) {
                log.info("sinaAuthError");
//                return "sinaAuthError";
                modelAndView.setViewName("view/error/sina-auth-error");
                return modelAndView;
//                return "view/error/sina-auth-error";
            }
            if (sinaUser == null) {
                log.info("Sina Auth --> sinaUser is null");
//                return "sinaAuthInput";
                modelAndView.setViewName("myviews/index");
                return modelAndView;
//                return "myviews/index";
            }
        }else {
            sinaUser = new com.miduchina.wrd.eventanalysis.weibo4j.User ();
            sinaUser.setId(user.getThirdpartyAccount());
            sinaUser.setName(user.getNickname());
            sinaUser.setAvatarLarge(user.getUserHead());
        }
//        UserExtraInfo userExtraInfo = new UserExtraInfo();
        String ExtraInfo = JSON.toJSONString(sinaUser);
//        userExtraInfo.setInfo(JSON.toJSONString(sinaUser));
        String nickName = sinaUser.getName();
        log.info("Sina Auth --> nickName is " + nickName);
        String userHead = sinaUser.getAvatarLarge();
        log.info("Sina Auth --> userHead is " + userHead);
        String desc = sinaUser.getDescription() == null ? "" : sinaUser.getDescription();
        log.info("Sina Auth --> desc is " + desc);

        UserDto loginUser = null;

        String rtnStr = doThirdpartyLogin(req, response, sinaUser.getId(), accessToken, tokenMap.get("access_token"), expires,BusinessConstant.THIRDPARTY_TYPE_SINA ,BusinessConstant.USER_CHANNEL_SINA, nickName, userHead, String.valueOf(sinaUser.getVerifiedType()),ExtraInfo,modelAndView);
        log.info("sina Auth referer is "+tokenMap.get("referer"));
        UserRes userRes = JSON.parseObject(rtnStr, UserRes.class);
        if(userRes != null && "0000".equals(userRes.getCode())){
            flag = true;
            loginUser = new UserDto();
            BeanUtils.copyProperties(loginUser, userRes.getUserInfo());
            log.info("----------loginUser----"+JSON.toJSONString(loginUser));
        }
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("userId",userRes.getUserInfo().getUserId());
        //从私信进入轻应用
        BaseDto accessRecords = apiInfoClient.findAccessRecords(map1);
        AuthJumpRelation saj =null;
        if(accessRecords.getData()!=null){
            saj=JSONObject.parseObject(JSONObject.toJSONString(accessRecords.getData()),AuthJumpRelation.class) ;
        }
        if(saj != null){
            if(saj.getType() == 1){
                log.info("sina Auth SinaAuthJump userId = " + saj.getUserId());
                log.info("sina Auth SinaAuthJump keywordId = " + saj.getKeywordId());
//                ActionContext.getContext().getValueStack().set("keywordId", saj.getKeywordId());
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("userId",saj.getUserId());
                map2.put("keywordId",saj.getKeywordId());
                apiInfoClient.deleteAccessRecords(map2);
//                return "keywordMenu";
                modelAndView.setViewName("view/home/home");
                return modelAndView;
//                return "view/home/home";
            }
        }

        //微博消息页的微热点按钮进入
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("ip", CommonUtils.getIp(req));
        map2.put("ua",req.getHeader("user-agent"));
        BaseDto notLoginOperate = apiInfoClient.findNotLoginOperateRecordByIpAndUA(map2);
        NotLoginOperateRecord notLoginOperateRecord =null;
        if(notLoginOperate.getData()!=null){
            notLoginOperateRecord=JSONObject.parseObject(JSONObject.toJSONString(notLoginOperate.getData()),NotLoginOperateRecord.class) ;
        }
        log.info("sinaAuth() notLoginOperateRecord = "+JSON.toJSONString(notLoginOperateRecord));
        if(notLoginOperateRecord != null){
            HashMap<String, Object> map3 = new HashMap<>();
            map3.put("ip",CommonUtils.getIp(req));
            map3.put("ua",req.getHeader("user-agent"));
            apiInfoClient.updateNotLoginOperateRecord(map3);
            String operateType = String.valueOf(notLoginOperateRecord.getOperateType());
            if(operateType.equals(BusinessConstant.NOT_LOGIN_OPERATE_APPLICATION)) {
//                return "view/home/home";
                modelAndView.setViewName("view/home/home");
                return modelAndView;
            }
//            }else if(operateType.equals(BusinessConstant.NOT_LOGIN_OPERATE_APPLICATION)){
//                return "goApplicationResult";
//            }else if(operateType.equals(BusinessConstant.NOT_LOGIN_OPERATE_STARLIST)){
//                return "getStarListResult";
//            }else if(operateType.equals(BusinessConstant.NOT_LOGIN_OPERATE_ACTIVITY1)){
//                return "getGeneralizeBootResult1";
//            }else if(operateType.equals(BusinessConstant.NOT_LOGIN_OPERATE_TOUR)){
//                return "getTourContent";
//            }else if(operateType.equals(BusinessConstant.NOT_LOGIN_OPERATE_ACTIVITY2)){
//                return "getGeneralizeBootResult2";
//            }else if(operateType.contains(BusinessConstant.NOT_LOGIN_OPERATE_ACTIVITY)){
//                ActionContext.getContext().getValueStack().set("activityId", operateType.replace(Constants.NOT_LOGIN_OPERATE_ACTIVITY, ""));
//                return "getGeneralizeBootResult";
//            }else if(operateType.equals(BusinessConstant.NOT_LOGIN_OPERATE_ENTERTAINMENT)){
//                return "getEntertainmentContent";
//            }else if(operateType.equals(BusinessConstant.NOT_LOGIN_OPERATE_HOTSEARCH)){
//                return "getHotSearch";
//            }
            if(StringUtils.isNotBlank(notLoginOperateRecord.getShareCode())){
                String[] share = notLoginOperateRecord.getShareCode().split("_");
                if(share[0].equals("goReport")){
                    String shareCode = share[1];
                    Integer version = Integer.parseInt(share[2]);
//                    ActionContext.getContext().getValueStack().set("shareCode", shareCode);
//                    ActionContext.getContext().getValueStack().set("version", version);
//                    return "view/home/home";
                    modelAndView.setViewName("view/home/home");
                    return modelAndView;
                }
            }
        }

        //颜总文章渠道，进入排行榜
//        if(keywordId != null && keywordId == -1){
//            return "getStockListResult";
//        }

        //活动页进入
        Cookie[] cookies = req.getCookies();

        log.info("sina Auth SinaAuthJump cookie ===>>"+JSON.toJSONString(cookies));
        log.info(LoginUtils.getUrlPrefix(req));
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(BusinessConstant.STRETCH_MOBILE_CODE)){
                    //User stretchUser = new User();
                    //stretchUser.setMobile(cookie.getValue().trim());
                    BaseDto baseDto1 = apiInfoClient.checkMobile(cookie.getValue());
                    String code1 = baseDto1.getCode();
                    if(!code1.equals("0000")){//该手机号用户不存在
                        //获取手机号赠送的积分
                        BaseDto baseDto = apiInfoClient.queryRegisterCreditByMobile(cookie.getValue());
                        UserRegRewardRecord urrr =null;
                        if(baseDto.getData()!=null){
                            urrr=JSONObject.parseObject(JSONObject.toJSONString(baseDto.getData()),UserRegRewardRecord.class) ;
                        }
                        if(urrr != null && urrr.getStatus() != 0){
                            //把活动手机号用户绑定至微博中打开活动界面授权进来的用户
                            loginUser.setMobile(cookie.getValue());

                            loginUser.setCreditAmount(userRes.getUserInfo().getCreditAmount() + urrr.getCreditAmount());

                            apiInfoClient.updateUser(loginUser);
                            //修改赠送积分用户的状态，被领取
                            apiInfoClient.updateRewardRecord(cookie.getValue());
                            //用户权益变动
                            UserRightsRechargeRecord userRightsRechargeRecord = new UserRightsRechargeRecord();
                            userRightsRechargeRecord.setUserId(userRes.getUserInfo().getUserId());
                            userRightsRechargeRecord.setRelationId(urrr.getId());
                            userRightsRechargeRecord.setType(BusinessConstant.USER_RIGHTS_RECHARGE_TYPE_ADD);
                            userRightsRechargeRecord.setCount(urrr.getCreditAmount());
                            userRightsRechargeRecord.setCurrentCount(userRes.getUserInfo().getCreditAmount());
                            userRightsRechargeRecord.setItem(BusinessConstant.USER_RIGHTS_RECHARGE_ITEM_CA);
                            userRightsRechargeRecord.setRecordDesc("赠送100000微积分");
                            userRightsRechargeRecord.setPlatform(BusinessConstant.RECHARGE_RECORD_PLATFORM_TYPE_WYQ_H5);
                            userRightsRechargeRecord.setStatus(BusinessConstant.STATUS_VALID);
                            userRightsRechargeRecord.setCreateTime(new Date());
                            apiInfoClient.addRightsRechargeRecord(userRightsRechargeRecord);
//                            apiInfoClient.saveUserRightsRechargeRecord(userRes.getUserInfo().getUserId(),urrr.getId(),
//                                    Constants.USER_RIGHTS_RECHARGE_TYPE_ADD,urrr.getCreditAmount(),userRes.getUserInfo().getCreditAmount(),
//                                    Constants.USER_RIGHTS_RECHARGE_ITEM_CA,"赠送100000微积分");
                        }
                        modelAndView.setViewName("view/stretch/complimentaryPointsResult");
                        return modelAndView;
//                        return "view/stretch/complimentaryPointsResult";
                    }
                }else if(cookie.getName().equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME")+"_activity")){
                    if(StringUtils.isNotBlank(cookie.getValue())){
                        String activityUrl = "redirect:/view/product/activity/goGeneralizeBoot?activityId="+cookie.getValue();
                        cookie.setValue(null);
                        //活动cookie失效
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        cookie.setDomain(LoginUtils.getBaseUrl(LoginUtils.getUrlPrefix(req)));
                        response.addCookie(cookie);
                        log.info("activityUrl = {}",activityUrl);
                        modelAndView.setViewName(activityUrl);
                        return modelAndView;
//                        /view/generalize
                    }
                }else if(cookie.getName().equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME")+"_stardb")){
                    cookie.setMaxAge(0);//活动cookie失效
                    cookie.setPath("/");
                    cookie.setDomain(LoginUtils.getBaseUrl(LoginUtils.getUrlPrefix(req)));
                    response.addCookie(cookie);
                    modelAndView.setViewName("getStardbBootResult");
                    return modelAndView;
//                    return "getStardbBootResult";
                }else if((SysConfig.cfgMap.get("WEBID_COOKIE_NAME")+"_nie").equals(cookie.getName())){
                    cookie.setMaxAge(0);//活动cookie失效
                    cookie.setPath("/");
                    cookie.setDomain(LoginUtils.getBaseUrl(LoginUtils.getUrlPrefix(req)));
                    response.addCookie(cookie);
                    modelAndView.setViewName("getKeywordBootResult");
                    return modelAndView;
//                    return "getKeywordBootResult";
                }
            }
        }

        log.info("sina Auth SinaAuthJump is null");
        flag = userRes.isRegister();
        if(flag){
            modelAndView.setViewName("view/welcome");
        }else{
            modelAndView.setViewName("view/home/home");
        }
        return modelAndView;
//        return flag? "view/welcome" : "view/home/home";
    }
    /**
     * 验证测试平台的token
     */
    @RequestMapping(value = "/token",method = RequestMethod.GET)
    public void token(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String echostr = request.getParameter("echostr");
        PrintWriter print = response.getWriter();
        print.write(echostr);
        print.flush();
    }

    //微信授权
    /**
     * 调用微信授权
     *
     * @throws Exception
     */
    @RequestMapping(value = "toWxAuth")
    @ResponseBody
    public void toWxAuth() throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String wxUrl = request.getParameter("wxUrl");
        log.info("toWxAuth authJumpRelation -->"+wxUrl);
        if(StringUtils.isBlank(wxUrl)){
//            wxUrl = "http://m-beta1.51wyq.cn/HTTDLX8R";
            wxUrl = PayUtil.getRandomStringByLength(8);
//            wxUrl = com.miduchina.wrd.eventanalysis.utils.CommonUtils.getRandomStringByLength(8);
        }
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixinconnectconfig.properties"));

        String codeURL = props.getProperty("authorizeURL").trim() + "?appid=" + props.getProperty("AppID1").trim() + "&redirect_uri=" + props.getProperty("redirectURI1").trim() + "&response_type=code&scope=snsapi_userinfo&state=" + wxUrl + "#wechat_redirect";
        log.info("codeURL:{}",codeURL);
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.sendRedirect(codeURL);
    }

    /**
     * 微信网页授权
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "view/user/wxAuth")
    public ModelAndView wxAuth(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) throws Exception {
        BaseDto baseDto = new BaseDto();
        log.info("wxAuth --> start");
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = servletRequestAttributes.getResponse();
        boolean flag = false;
        String code = request.getParameter("code");
//        String code ="023FGGBo1XwEjj0GUaDo1wcQBo1FGGBT";
        if (StringUtils.isBlank(code)) {
            log.info("wxAuth --> code is null");
            modelAndView.setViewName(Flags.error_view);
            return modelAndView;
//            return "view/error/error";
        }
        log.info("wxAuth --> code is " + code);

        // 获取access_token
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixinconnectconfig.properties"));
        String accessTokenURL = props.getProperty("accessTokenURL").trim() + "?appid=" + props.getProperty("AppID1").trim() + "&secret=" + props.getProperty("AppSecret1").trim() + "&code=" + code + "&grant_type=authorization_code";
        String result = HttpRequestUtils.sendGet(accessTokenURL);
        if (result == null || "".equals(result.trim())) {
            log.info("wxAuth --> result is null");
            modelAndView.setViewName(Flags.error_view);
            return modelAndView;
//            return "view/error/error";
        }
        log.info("wxAuth --> result is " + result);

        JSONObject json = JSONArray.parseObject(result);
        String accessToken = "";
        if (StringUtils.isNoneBlank(json.getString("access_token"))){
            accessToken = json.getString("access_token");
        }
        log.info("wxAuth --> access_token is " + accessToken);
        String openId ="";
        if (StringUtils.isNoneBlank(json.getString("openid"))){
            openId = json.getString("openid");
        }
        log.info("wxAuth --> open_id is " + openId);
        long expiressIn = 0;
        if (StringUtils.isNoneBlank(json.getString("expires_in"))){
            expiressIn = Long.parseLong(json.getString("expires_in"));
        }
        log.info("wxAuth --> expiress_in is " + expiressIn);
        String refresh_token = "";
        if (StringUtils.isNoneBlank(json.getString("refresh_token"))){
            refresh_token = json.getString("refresh_token");
        }
        log.info("wxAuth --> refresh_token is " + refresh_token);
        String unionId = "";
        if (StringUtils.isNoneBlank(json.getString("unionid"))){
            unionId = json.getString("unionid");
        }
//        String unionId ="op2BysyO3lV-PsQWxQXT2njoCfkY";
        log.info("wxAuth --> union_id is " + unionId);

        // 获取用户信息
        String userInfoURL = props.getProperty("userInfoURL").trim() + "?access_token=" + accessToken + "&openid=" + openId;
        result = HttpRequestUtils.sendGet(userInfoURL);
        if (result == null || "".equals(result.trim())) {
            log.info("wxAuth --> userinfo result is null");
            modelAndView.setViewName(Flags.error_view);
            return modelAndView;
//            return "view/error/error";
        }
        log.info("wxAuth --> userinfo result is " + result);

        json = JSONArray.parseObject(result);
        String nickName = json.getString("nickname");
//        nickName = SimpleUtils.stringConvert(nickName, "ISO-8859-1", "UTF-8");
//        nickName=new String(nickName.getBytes(),"UTF-8");
        log.info("wxAuth --> nickName is " + nickName);
        String userHead = json.getString("headimgurl");
        log.info("wxAuth --> userHead is " + userHead);

        User user = new User();
        user.setNickname(nickName);
        user.setUserHead(userHead);
        UserDto loginUser = null;
        String userExtraInfo = JSON.toJSONString(result);
//        UserExtraInfo userExtraInfo = new UserExtraInfo();
//        UserExtraInfo userExtraInfo = JSON.parseObject(JSON.toJSONString(result), UserExtraInfo.class);
//        userExtraInfo.set(JSON.toJSONString(result));
        log.info("----------weixin---params----"+unionId+"----"+nickName+"---"+userExtraInfo);
        String rtnStr = doThirdpartyLogin(request, response, unionId, accessToken, refresh_token, expiressIn, BusinessConstant.THIRDPARTY_TYPE_WX, BusinessConstant.USER_CHANNEL_WEIXIN, nickName, userHead, null,userExtraInfo,modelAndView);
        log.info("----------weixin---rtnStr----"+rtnStr);
        //		log.info("sina Auth referer is "+tokenMap.get("referer"));
         UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
        if(userRes != null && "0000".equals(userRes.getCode())){
            flag = true;
            loginUser = new UserDto();
            BeanUtils.copyProperties(loginUser, userRes.getUserInfo());
            log.info("----------weixin---loginUser----"+JSON.toJSONString(loginUser));
        }

//        HttpServletRequest req = ServletActionContext.getRequest();

        //活动页进入
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(BusinessConstant.STRETCH_MOBILE_CODE)){
                    log.info("Cookie getDomain=" + cookie.getDomain());
                    log.info("Cookie getValue=" + cookie.getValue());
                    log.info("Cookie getPath=" + cookie.getPath());
                    log.info("-----------------Cookies  end------------");

                    //User stretchUser = new User();
                    //stretchUser.setMobile(cookie.getValue().trim());
                    BaseDto baseDto1 = apiInfoClient.checkMobile(cookie.getValue());
                    String code1 = baseDto1.getCode();
                    if(!code1.equals("0000")){//该手机号用户不存在
                        //获取手机号赠送的积分
                        BaseDto baseDto2 = apiInfoClient.queryRegisterCreditByMobile(cookie.getValue());
                        UserRegRewardRecord urrr =null;
                        if(baseDto2.getData()!=null){
                            urrr=JSONObject.parseObject(JSONObject.toJSONString(baseDto2.getData()),UserRegRewardRecord.class) ;
                        }
                        if(urrr != null && urrr.getStatus() != 0){
                            //把活动手机号用户绑定至微博中打开活动界面授权进来的用户
                            loginUser.setMobile(cookie.getValue());
                            //把赠送积分给用户
                            loginUser.setCreditAmount(userRes.getUserInfo().getCreditAmount() + urrr.getCreditAmount());

                            apiInfoClient.updateUser(loginUser);
                            //修改赠送积分用户的状态，被领取
                            apiInfoClient.updateRewardRecord(cookie.getValue());
                            //用户权益变动
                            UserRightsRechargeRecord userRightsRechargeRecord = new UserRightsRechargeRecord();
                            userRightsRechargeRecord.setUserId(userRes.getUserInfo().getUserId());
                            userRightsRechargeRecord.setRelationId(urrr.getId());
                            userRightsRechargeRecord.setType(BusinessConstant.USER_RIGHTS_RECHARGE_TYPE_ADD);
                            userRightsRechargeRecord.setCount(urrr.getCreditAmount());
                            userRightsRechargeRecord.setCurrentCount(userRes.getUserInfo().getCreditAmount());
                            userRightsRechargeRecord.setItem(BusinessConstant.USER_RIGHTS_RECHARGE_ITEM_CA);
                            userRightsRechargeRecord.setRecordDesc("赠送100000微积分");
                            userRightsRechargeRecord.setPlatform(BusinessConstant.RECHARGE_RECORD_PLATFORM_TYPE_WYQ_H5);
                            userRightsRechargeRecord.setStatus(BusinessConstant.STATUS_VALID);
                            userRightsRechargeRecord.setCreateTime(new Date());
                            apiInfoClient.addRightsRechargeRecord(userRightsRechargeRecord);
                        }
                        modelAndView.setViewName("complimentaryPointsResult");
                        return modelAndView;
//                        return "complimentaryPointsResult";
                    }
                }
            }
        }

        String state = request.getParameter("state");
        log.info("wxAuth --> state is " +state);
        //微信用户跳转
        if(StringUtils.isNotBlank(state)){
            String shortUrl = state;
            String sourceUrl = null;
            HashMap<String, Object> map = new HashMap<>();
            map.put("shortUrl",shortUrl);
            BaseDto bs = apiInfoClient.findH5ShortUrlByShortUrl(map);
            H5ShortUrl h5ShortUrl =null;
            if(bs.getData()!=null){
                h5ShortUrl=JSONObject.parseObject(JSONObject.toJSONString(bs.getData()),H5ShortUrl.class) ;
            }
            if(h5ShortUrl == null){
                log.info("h5ShortUrl is null");
            }else{
                sourceUrl = h5ShortUrl.getSourceUrl();
            }

            //中秋活动
            if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(sourceUrl) && sourceUrl.contains("wxActivity/goMooncake")) {
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("openId",openId);
                map2.put("type",2);
                BaseDto bss = apiInfoClient.findWeiXinMaterialByOpenIdAndType(map2);
                WeiXinMaterial wxm =null;
                if(bss.getData()!=null){
                    wxm=JSONObject.parseObject(JSONObject.toJSONString(bss.getData()),WeiXinMaterial.class) ;
                }
                log.info("weixin wxActivity/goMooncakeShare.shtml openId="+openId+",sourceUrl="+sourceUrl+",wxm="+wxm);
                if(wxm == null){
                    WeiXinMaterial wx = new WeiXinMaterial();
                    wx.setOpenId(openId);
                    wx.setStatus(BusinessConstant.STATUS_VALID);
                    wx.setCreateTime(new Date());
                    wx.setType(2);
                    HashMap<String, Object> map1 = new HashMap<>();
                    map1.put("wxMaterial",wx);
                    apiInfoClient.saveWeiXinMaterial(map1);
                }
            }
//            ActionContext.getContext().getValueStack().set("actionUrl", sourceUrl);
//            return "more";
            modelAndView.setViewName("redirect:"+sourceUrl);
            return modelAndView;
//            return "redirect:"+sourceUrl;
        }

        //未设置监测方案的新用户
//		if(admin!=null&&admin.getUserId()>0){
//			flag = keyWordBean.findKeywordByUpdateTime(admin.getUserId());
//		}
//        if(keywordId != null && keywordId == -1){
//            return "getStockListResult";
//        }
        log.info("wxAuth --> state is null");
        flag = userRes.isRegister();
        if(flag){
            modelAndView.setViewName("view/home/home");
        }else{
            modelAndView.setViewName("wxAuth");
        }

        return modelAndView;
//        return flag? "view/home/home" : "wxAuth";
    }
    private String  doThirdpartyLogin(HttpServletRequest request, HttpServletResponse response, String uid, String accessToken, String refreshToken, long expireIn, int platformType,int channel ,String nickName, String userHead, String verifiedType,String extraInfo,ModelAndView modelAndView){
        log.info("--------doThirdpartyLogin--------Strat----------uid:{}",uid);
        String rtnPage = "success";
        UserThirdpartyAuthInfo userThirdpartyAuthInfo = new UserThirdpartyAuthInfo();
        userThirdpartyAuthInfo.setThirdpartyUserId(uid);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("userThirdpartyAuthInfo",userThirdpartyAuthInfo);
        log.info("userThirdpartyAuthInfo:{}",JSONObject.toJSONString(userThirdpartyAuthInfo));
        BaseDto baseDto = apiInfoClient.queryUserThirdpartyAuthInfoByUid(map1);
        log.info("queryUserThirdpartyAuthInfoByUid:{}",JSONObject.toJSONString(baseDto));
        UserThirdpartyAuthInfo infoTemp = null;
        if(baseDto.getData()!=null){
            infoTemp=JSONObject.parseObject(JSONObject.toJSONString(baseDto.getData()),UserThirdpartyAuthInfo.class) ;
        }
        if (infoTemp == null)
            rtnPage = "toBindThirdParty";
        try {
            fetchProductlist(request,modelAndView,1);
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
        log.info("userCreateThirdpartyVO:{}",JSONObject.toJSONString(userCreateThirdpartyVO));
        if(channel == BusinessConstant.USER_CHANNEL_SINA){
            userCreateThirdpartyVO.setUserChannel(BusinessConstant.USER_CHANNEL_SINA);
        }else if(channel == BusinessConstant.USER_CHANNEL_WEIXIN){
            userCreateThirdpartyVO.setUserChannel(BusinessConstant.USER_CHANNEL_WEIXIN);
        }else{
            userCreateThirdpartyVO.setUserChannel(BusinessConstant.USER_CHANNEL_WYQ);
        }

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
        params.put("platform", BusinessConstant.PLATFORM_H5);
        log.info("-------URL-----------"+ SysConfig.cfgMap.get("API_INTRA_BUSINESS_URL")+ CommonAPIConfig.getValue("WYQ_THIRDPARTY_API")+"?"+params);
//		String rtnStr = HttpRequestUtils.sendPost(SysConfig.API_INTRA_BUSINESS_URL+CommonAPIConfig.getValue("WYQ_THIRDPARTY_API"), params);
        log.info("-----------params111----------"+params.toString());
        String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, CommonAPIConfig.getValue("WYQ_THIRDPARTY_API"), params);

        log.info("-----------rtnStr----------"+rtnStr);
        log.info("ThirdPartyAction doThirdpartyLogin rtnStr = [" + rtnStr + "]");

        if (StringUtils.isBlank(rtnStr))
            return "view/error/error";
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        UserRes userRes = JSON.parseObject(rtnStr, UserRes.class);
        log.info("doThirdpartyLogin--userRes:{}",userRes);
        log.info("-----------userRes code----------"+userRes.getCode());
        if (userRes == null || !BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode()))
            return "view/error/error";

        //String memcacheSSOFlag = RequestInterFace.setUserForMemcachedWyq(request, response, userRes.getUserInfo().getUserId() + userRes.getUserInfo().getUsername(), userRes.getSid(), SystemConstants.SYS_ADMIN_CACHE_TIME, SysConfig.memoCacheName);
        //SessionUtil.setAttribute(Constants.MEMCACHE_SSO_FLAG + "_" + SysConfig.memoCacheName + userRes.getUserInfo().getUserId(), userRes.getUserInfo().getUserId() + "", memcacheSSOFlag, new Date().getTime() + Constants.MEMCACHE_SSO_EXPIRY);
        LoginUtils.saveLoginFlag(request, response, userRes);
        log.info("--------doThirdpartyLogin--------End----------");
        log.info("--------doThirdpartyLogin--------result---is----------"+rtnPage);
        return rtnStr;
    }

    public static void main(String[] args) throws Exception {
//        String nickName="Melony";
//        String newName=new String(nickName.getBytes(),"UTF-8");
//        nickName = SimpleUtils.stringConvert(nickName, "ISO-8859-1", "UTF-8");
//        Params p = new Params();
//        p.setKeyword("华为");
////        p.setDate(24);
//        p.setStartTime("2019-07-16 00:00:00");
//        p.setEndTime("2019-07-16 23:59:59");
//        p.setStatField(Params.STATFIELD_PUBLISHEDHOUR);
////        p.setStatField(Params.STATFIELD_PUBLISHEDHOUR);
//        p.setDate(-1);
//        Map<String,Object> map = new HashMap<>();
//        map.put("channel",2);
//        map.put("platform",3);
//        map.put("userTag","wrd27586");
//
////        map.put("paramsJson", JSON.toJSONString(p));
////        map.put("switchRatioDay",1);
//        map.put("attributeProductTag","rz");
//        Params params = JSON.parseObject(
//                "{\"navyFlg\":3,\"origin\":\"2,5\",\"verifiedTypeList\":[\"6\",\"0\",\"200\",\"600\",\"-1\",\"220\",\"1\",\"2\",\"3\",\"4\",\"5\",\"7\"],\"keyword\":\"(肯德基)|(麦当劳)\"}",
//                Params.class);
//        params.setPage(1);
//        params.setPagesize(10);
//        IContentCommonNetView searchListV1_001 = SjfxEsIndustryEnterpriseMethodV1.searchListV1_001("wrd27586", params);
//        List<IContentCommonNet> iContentCommonNetList = searchListV1_001.getIContentCommonNetList();
//        for (IContentCommonNet iContentCommonNet : iContentCommonNetList) {
//            iContentCommonNet.setKeywordId("2");
//        }
//        HashMap<String, Object> whereMap = new HashMap<String, Object>();
//        whereMap.put(">:id",0);
//        DbComParams dbComParams = new DbComParams();
//        dbComParams.setWhereMap(whereMap);
//        dbComParams.setTableName(Tag.class.getName());
//        dbComParams.setPage(1);
//        dbComParams.setPageSize(50);
//        PageView view = SjfxDbMethodV1.selectWrdEnterprise("sjfx12052", dbComParams);
//        List<Tag> list = (List<Tag>) view.getList();
//        map.put("iccList",JSON.toJSONString(iContentCommonNetList));
//        map.put("tagList",JSON.toJSONString(list));
//        String csv = HttpRequestUtils.sendPost("http://localhost:8080/abilityseal/api/doHytagActive",map);
//        System.out.println(csv);
//
//        System.out.println(JSON.toJSONString(list));
    }



}
