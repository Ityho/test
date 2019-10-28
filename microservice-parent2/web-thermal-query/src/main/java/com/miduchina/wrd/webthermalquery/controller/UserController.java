package com.miduchina.wrd.webthermalquery.controller;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.user.UserCreateVO;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.eventanalysis.BaseRes;
import com.miduchina.wrd.util.MD5Utils;
import com.miduchina.wrd.webthermalquery.fegin.UserFeignClient;
import com.miduchina.wrd.webthermalquery.util.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @auther Administrator
 * @vreate 2019-05 16:05
 */
@Slf4j
@Controller
@RequestMapping("user/events")
public class UserController extends BaseController{
    @Autowired
    private UserFeignClient userFeignClient;

//    /**
//     * 激活邮箱
//     * @return
//     */
//    public String activityMail(){
//        try {
//            user.setIsVerified(1);
//            userBean.updateMailStatus(user);
//        } catch (Exception e) {
//            printLog(e);
//        }
//        //user=userBean.findUserById(user.getUserId());
//        return "activityMail";
//    }
//
//    /**
//     * 进入注册页面
//     * @return
//     */
//    public String goRegisterPage(){
//        try {
//            return SUCCESS;
//        } catch (Exception e) {
//            log.error(e);
//            return null;
//        }
//    }
//    /**
//     * 进入新注册页面
//     * @return
//     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "newGoRegisterPage")
    public String newGoRegisterPage(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map){
       String mobile_number_regex = SysConfig.cfgMap.get("MOBILE_NUMBER_REGEX");
        String firstLoginSign = request.getParameter("firstLoginSign");
        if(StringUtils.isNotBlank(firstLoginSign)){
            map.put("firstLoginSign",firstLoginSign);
        }
        String url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
        map.put("sysConfig",System.currentTimeMillis());
        map.put("url",url);
        map.put("request",request);
        map.put("nickname","7777" );
        map.put("item","7777" );
        String qrCodeImg = SysConfig.cfgMap.get("QR_CODE_IMG");
        map.put("qrCodeImg",qrCodeImg );
        map.put("msg",null );
        map.put("selectLoginType",1 );
        map.put("isShowTop","1");
        map.put("currentPage","");
        map.put("mobile_number_regex",mobile_number_regex);
       return "common/new_register_v2";
    }
//
//    /**
//     * 处理注册请求的方法
//     *
//     * @return
//     */
//    public String doRegisterV2() throws Exception {
//        if (StringUtils.isBlank(authcode) || user == null || StringUtils.isBlank(user.getMobile()) || StringUtils.isBlank(user.getPassword())){
//            msg = "请先完成验证！";
//            if(StringUtils.isNotBlank(inviteUserId)){
//                return "inviteFriendsSuccess";
//            }else{
//                return SUCCESS;
//            }
//        }
//
//        if(StringUtils.isBlank(inviteUserId)){
//            String geeTest = JedisUtil.getAttribute(Constants.generateJedisKey(new StringBuilder(Constants.JEDIS_KEYS_SMS_CHECK_SUCCESS).append(user.getMobile()).toString()));
//            if (StringUtils.isBlank(geeTest)){
//                msg = "请先完成验证！";
//                if(StringUtils.isNotBlank(inviteUserId)){
//                    return "inviteFriendsSuccess";
//                }else{
//                    return SUCCESS;
//                }
//            }
//        }
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("mobile", user.getMobile());
//        params.put("authcode", authcode);
//        String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(ServletActionContext.getRequest(), IntraBusinessAPIConfig.getValue("checkSMSURL"), params);
//        if (StringUtils.isBlank(rtnStr)) {
//            msg = "验证码不正确，请重新输入!";
//            if(StringUtils.isNotBlank(inviteUserId)){
//                return "inviteFriendsSuccess";
//            }else{
//                return SUCCESS;
//            }
//        } else {
//            JSONObject jsonObject = JSONObject.parseObject(rtnStr);
//            if (jsonObject == null){
//                msg = "验证码不正确，请重新输入!";
//                if(StringUtils.isNotBlank(inviteUserId)){
//                    return "inviteFriendsSuccess";
//                }else{
//                    return SUCCESS;
//                }
//            }else if (!CodeConstants.SUCCESS_CODE.equals(jsonObject.getString("code"))){
//                msg = jsonObject.getString("message");
//                if(StringUtils.isNotBlank(inviteUserId)){
//                    return "inviteFriendsSuccess";
//                }else{
//                    return SUCCESS;
//                }
//            }else if (!jsonObject.getBooleanValue("succ")){
//                msg = "验证码不正确，请重新输入!";
//                if(StringUtils.isNotBlank(inviteUserId)){
//                    return "inviteFriendsSuccess";
//                }else{
//                    return SUCCESS;
//                }
//            }
//        }
//
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        fetchProductlist(Constants.PACKAGE_TYPE_FREE);
//
//        UserCreateVO userCreateVO = new UserCreateVO();
//        userCreateVO.setUsername(user.getMobile());
//        userCreateVO.setPassword(MD5Util.md5(user.getPassword()));
//        userCreateVO.setAppUserStatus(0);
//        userCreateVO.setUserChannel(Constants.USER_CHANNEL_WYQ);
//        userCreateVO.setGiftPackageId(packageListFree.get(0).getProductPackageId());
//        userCreateVO.setLogin(true);
//
//        String userExclusiveChannelCode = null;
//        if (request.getCookies() != null && request.getCookies().length > 0) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("exclusive_channel_code".equals(cookie.getName())) {
//                    String code = cookie.getValue();
//                    if (StringUtils.isNoneBlank(code))
//                        userExclusiveChannelCode = code;
//
//                    break;
//                }
//            }
//        }
//        if (StringUtils.isNotBlank(userExclusiveChannelCode))
//            userCreateVO.setExclusiveChannelCode(userExclusiveChannelCode);
//
//        if(StringUtils.isNotBlank(inviteUserId)){
//            userCreateVO.setInviteUserId(CommonUtils.parseInviteUserEncode(inviteUserId));
//            userCreateVO.setUserChannel(Constants.USER_CHANNEL_INVITE);
//            userCreateVO.setPassword(MD5Util.md5(MD5Util.md5(user.getMobile().substring(user.getMobile().length()-6,user.getMobile().length()))));
//        }
//        params = new HashMap<String, Object>();
//        params.put("userParam", JSONObject.toJSONString(userCreateVO));
//
//
//        rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userCreateMobileURL"), params);
//        log.info("doRegisterV2 create user rtnStr = [" + rtnStr + "] authcode = ["+authcode+"] mobile = ["+user.getMobile()+"]");
//        if (StringUtils.isBlank(rtnStr)){
//            msg = "系统繁忙，请稍后再试！";
//            if(StringUtils.isNotBlank(inviteUserId)){
//                return "inviteFriendsSuccess";
//            }else{
//                return SUCCESS;
//            }
//        }
//
//        UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//        if (userRes == null || !CodeConstants.SUCCESS_CODE.equals(userRes.getCode())){
//            msg = userRes.getMessage();
//            if(StringUtils.isNotBlank(inviteUserId)){
//                return "inviteFriendsSuccess";
//            }else{
//                return SUCCESS;
//            }
//        }
//
//        String loginRes = LoginUtils.doLogin(request, response, userRes, false);
//        if(!CodeConstants.SUCCESS_CODE.equals(loginRes)){
//            msg = loginRes;
//            if(StringUtils.isNotBlank(inviteUserId)){
//                return "inviteFriendsSuccess";
//            }else{
//                return SUCCESS;
//            }
//        };
//
//        log.info("doRegister success!");
//
//        fetchRightNumber();
//        if (admin == null) {
//            admin = new User();
//            admin.setUserId(userRes.getUserInfo().getUserId());
//        }
//        return "doRegister";
//    }

    /**
     * 邀请注册
     * @throws Exception
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "invideGoRegisterV2")
    @ResponseBody
    public BaseDto invideGoRegisterV2(String mobile,String authcode,String password,String inviteUserId) throws Exception {
        BaseDto<Object> baseDto = new BaseDto<>();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Map<String, Object> m = new HashMap<String, Object>();
        if (StringUtils.isBlank(authcode)  || StringUtils.isBlank(mobile) || StringUtils.isBlank(password)){
//            m.put("code", "1111");
//            m.put("msg", "验证码或者手机号不能为空");
//            CommonUtils.writeJSONString(JSONObject.toJSONString(m));
            return baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("验证码或者手机号不能为空");
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("authcode", authcode);
        params.put("platform", BusinessConstant.PLATFORM_Web);
        String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("checkSMSURL"), params);
        if (StringUtils.isBlank(rtnStr)) {
//            m.put("code", "1111");
//            m.put("msg", "验证码不正确，请重新输入!");
//            CommonUtils.writeJSONString(JSONObject.toJSONString(m));
            return baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("验证码不正确，请重新输入!");
        } else {
            JSONObject jsonObject = JSONObject.parseObject(rtnStr);
            if (jsonObject == null){
//                m.put("code", "1111");
//                m.put("msg", "验证码不正确，请重新输入!");
//                CommonUtils.writeJSONString(JSONObject.toJSONString(m));
                return baseDto.setMessage("验证码不正确，请重新输入!").setCode(CodeConstant.FAILURE_CODE);
            }else if (!BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(jsonObject.getString("code"))){
                String msg = jsonObject.getString("message");
//                m.put("code", "1111");
//                m.put("msg", msg);
//                CommonUtils.writeJSONString(JSONObject.toJSONString(m));
                return baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage(msg);
            }else if (!jsonObject.getBooleanValue("succ")){
//                m.put("code", "1111");
//                m.put("msg", "验证码不正确，请重新输入!");
//                CommonUtils.writeJSONString(JSONObject.toJSONString(m));
                return baseDto.setMessage("验证码不正确，请重新输入!").setCode(CodeConstant.FAILURE_CODE);
            }
        }

//        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        BaseDto baseDto1 = fetchProductlist(BusinessConstant.PACKAGE_TYPE_FREE);
        List<ProductPackage> packageListFree = (List<ProductPackage>) baseDto1.getData();
        UserCreateVO userCreateVO = new UserCreateVO();
        userCreateVO.setUsername(mobile);
        userCreateVO.setPassword(MD5Utils.md5(password));
        userCreateVO.setAppUserStatus(0);
        userCreateVO.setUserChannel(BusinessConstant.USER_CHANNEL_WYQ);
        userCreateVO.setGiftPackageId(packageListFree.get(0).getProductPackageId());
        userCreateVO.setLogin(true);

        String userExclusiveChannelCode = null;
        if (request.getCookies() != null && request.getCookies().length > 0) {
            for (Cookie cookie : request.getCookies()) {
                if ("exclusive_channel_code".equals(cookie.getName())) {
                    String code = cookie.getValue();
                    if (StringUtils.isNoneBlank(code))
                        userExclusiveChannelCode = code;

                    break;
                }
            }
        }
        if (StringUtils.isNotBlank(userExclusiveChannelCode))
            userCreateVO.setExclusiveChannelCode(userExclusiveChannelCode);

        if(StringUtils.isNotBlank(inviteUserId)){
            userCreateVO.setInviteUserId(CommonUtils.parseInviteUserEncode(inviteUserId));
            userCreateVO.setUserChannel(BusinessConstant.USER_CHANNEL_WYQ_INVITE);
            userCreateVO.setPassword(MD5Utils.md5(MD5Utils.md5(mobile.substring(mobile.length()-6,mobile.length()))));
        }
        params = new HashMap<String, Object>();
        params.put("userParam", JSONObject.toJSONString(userCreateVO));
        params.put("platform", BusinessConstant.PLATFORM_Web);

        rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userCreateMobile"), params);
        log.info("invideGoRegisterV2 create user rtnStr = [" + rtnStr + "] authcode = ["+authcode+"] mobile = ["+mobile+"]");
        if (StringUtils.isBlank(rtnStr)){
//            m.put("code", "1111");
//            m.put("msg", "系统繁忙，请稍后再试！");
//            CommonUtils.writeJSONString(JSONObject.toJSONString(m));
            return baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("系统繁忙，请稍后再试！");
        }

        BaseDto<UserDto> userRes = JSONObject.parseObject(rtnStr, BaseDto.class);
        if (userRes == null || !BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())){
//            msg = userRes.getMessage();
//            m.put("code", "1111");
//            m.put("msg", "验证码或者手机号不能为空");
//            CommonUtils.writeJSONString(JSONObject.toJSONString(m));
            return baseDto.setMessage("验证码或者手机号不能为空").setCode(CodeConstant.FAILURE_CODE);
        }
//        m.put("code", "0000");

        JSONObject jsonObject = JSONObject.parseObject(rtnStr);
        String userInfo = jsonObject.getString("userInfo");
        UserDto userDto = JSONObject.parseObject(userInfo, UserDto.class);
        userDto.setSid(jsonObject.getString("sid"));
        String loginRes = LoginUtils.doLogin(request, response, userDto, false);
        if(!"0000".equals(loginRes)){
//            m.put("code", "1111");
//            m.put("msg", loginRes);
//            CommonUtils.writeJSON(m);
            return baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage(loginRes);
        };

        log.info("doRegister success!");

        Map<String, Object> map = fetchRightNumber();
//        if (admin == null) {
////            admin = new User();
////            admin.setUserId(userRes.getUserId());
//        }
        return baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(map);
//        CommonUtils.writeJSONString(JSONObject.toJSONString(m));

    }
    @GetMapping("registerV2Success")
    public String registerV2Success(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map){
        try {
            Map<String, Object> map1 = fetchRightNumber();
            Integer collectTotal = (Integer) map1.get("collectTotal");
            Integer cartTotal = (Integer) map1.get("cartTotal");
            map.put("collectTotal",collectTotal);
            map.put("cartTotal",cartTotal);

            String url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
            map.put("sysConfig",System.currentTimeMillis());
            map.put("url",url);
            map.put("request",request);
            map.put("collectTotal",0);
            map.put("cartTotal",0);

            UserDto userDto = fetchSessionAdmin();
            if(userDto==null){
                UserDto userDto1 = new UserDto();
                map.put("admin",userDto1);
                map.put("admin1",userDto);
                map.put("islogin",false);
            }else{
                map.put("admin",userDto);
                map.put("admin1",userDto);
                map.put("islogin",true);
            }
            map.put("firstLoginSign","0");
            String qrCodeImg = SysConfig.cfgMap.get("QR_CODE_IMG");
            map.put("qrCodeImg",qrCodeImg );
            map.put("msg",null );
            map.put("isShowTop","1");
            map.put("currentPage","");
            map.put("login","");
            map.put("selectLoginType",1 );
            map.put("sysConfig",System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "common/new_register_success";
//		return "doRegister";
    }

    /**
     * 打印日志
     * @param e
     */
    private void printLog(Exception e) {
        String errMsg = "";
        errMsg += "Exception : " + e + " {";
        StackTraceElement[] ste = e.getStackTrace();
        for (int k = 0; k < ste.length; k++)
        {
            errMsg += ste[k].toString() + ";";
        }
        errMsg += "}";
        log.info(errMsg);
    }

//    /**
//     * 获取用户VIP信息
//     *
//     * @throws Exception
//     */
//    public void getUserVipInfo() throws Exception {
//        fetchSessionAdmin();
//        VipInfoVO vipInfo = null;
//        if (admin != null) {
//            vipInfo = admin.getVipInfo();
//        }
//        CommonUtils.writeJSON(vipInfo);
//    }
//
//
//    /**
//     * 检测注册号码是否存在
//     *
//     * @return
//     * @throws Exception
//     */
//    public String checkRegUid() throws Exception {
//
//        try {
//            boolean result = false; //userService.checkUidExist(username);
//            JSONObject jsonObj = new JSONObject();
//            if (!result) {
//                jsonObj.put("success", 0);
//                jsonObj.put("returnval", 1);
//            } else {
//                jsonObj.put("success", -1);
//                jsonObj.put("returnval", 0);
//            }
//            String resultDatas = jsonObj.toString();
//            // 返回页面信息
//            HttpServletResponse response = ServletActionContext.getResponse();
//            response.setContentType("text/plain;charset=gbk");
//            response.setHeader("Pragma", "No-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
//            PrintWriter out = response.getWriter();
//            out.println(resultDatas);// 返回json格式数据
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            printLog(e);
//        }
//        return null;
//    }
//
//    /**
//     * 检测注册邮箱是否被使用
//     *
//     * @return
//     * @throws Exception
//     */
//    public String checkRegEmail() throws Exception {
//
//        try {
//            boolean result =false;// userService.checkEmailExist(str);
//
//            JSONObject jsonObj = new JSONObject();
//            if (!result) {
//                jsonObj.put("success", 0);
//            } else {
//                jsonObj.put("success", -1);
//            }
//
//            String resultDatas = jsonObj.toString();
//            // 返回页面信息
//            HttpServletResponse response = ServletActionContext.getResponse();
//            response.setContentType("text/plain;charset=gbk");
//            response.setHeader("Pragma", "No-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
//            PrintWriter out = response.getWriter();
//
//            out.println(resultDatas);// 返回json格式数据
//
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            printLog(e);
//        }
//
//        return null;
//    }
//
//
//
//    /**
//     * 发送注册短信验证码
//     *
//     * @return
//     * @throws Exception
//     */
//    public void sendRegVcode() throws Exception {
//        if (mobile != null && !"".equals(mobile)) {
//            int result = -1;
//            CommonUtils.opreateLog(ServletActionContext.getRequest(), null, Constants.OPERATE_LOG_PRODUCT_SECTION_SYS_SMS, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);
//            result = SMSCommonUtil.sendSMSVcode(SMSCommonUtil.SEND_VCODE_REG,
//                    SMSCommonUtil.SEND_VCODE_REG_VALID_COUNT, mobile,
//                    systemConfig.getSmsHttpSendConfs().getProperty("wyq_sendsms_url"),
//                    ServletActionContext.getRequest());
//            CommonUtils.writeJSON(result);
//        }
//    }
//
//    /**
//     * 检测注册验证码
//     *
//     * @return
//     * @throws Exception
//     */
//
//    public void checkRegVcode() throws Exception {
//        if (user != null && user.getMobile() != null && !"".equals(user.getMobile())) {
//            int result = SMSCommonUtil
//                    .checkSMSVcode(SMSCommonUtil.SEND_VCODE_REG,
//                            SMSCommonUtil.SEND_VCODE_REG_VALID_COUNT,
//                            user.getMobile(),
//                            authcode);
//
//            CommonUtils.writeJSON(result);
//        }
//    }
//
//    /**
//     * 发送人工简报短信验证码
//     *
//     * @return
//     * @throws Exception
//     */
//    public void sendEventVcode() throws Exception {
//        if (mobile != null && !"".equals(mobile)/* && _ran != null && !"".equals(_ran) && imgVcode != null && !"".equals(imgVcode)*/) {
//            int result = -1;
//           /* String vcodepre_login = "";
//            Object obj = SessionUtil.getAttribute(SysConfig.memoCacheName, "wyq_lg_" + _ran);
//            if (obj != null)
//                vcodepre_login = obj.toString();
//
//            if (vcodepre_login.trim().toLowerCase().equals(imgVcode.trim().toLowerCase())) {*/
////                saveUserOperateLog(0, Constants.OPERATE_LOG_TYPE_SYS_SMS, "人工简报发送短信：mobile = [" + mobile + "]");
//            CommonUtils.opreateLog(ServletActionContext.getRequest(), null, Constants.OPERATE_LOG_PRODUCT_SECTION_SYS_SMS, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);
//            result = SMSCommonUtil.sendSMSVcode(
//                    SMSCommonUtil.SEND_VCODE_EVENT,
//                    SMSCommonUtil.SEND_VCODE_EVENT_VALID_COUNT,
//                    mobile,
//                    systemConfig.getSmsHttpSendConfs().getProperty("wyq_sendsms_url"),
//                    ServletActionContext.getRequest());
//            /*}*/
//            CommonUtils.writeJSON(result);
//        }
//
////		try {
////			this.vcodeType = this.SYS_SESSION_VCODETYPE_REG;
////		} catch (Exception e) {
////			printLog(e);
////		}
////		return sendSmsVcode();
//    }
//
//    /**
//     * 检测人工简报验证码
//     *
//     * @return
//     * @throws Exception
//     */
//
//    public void checkEventVcode() throws Exception {
//        if (user != null && user.getMobile() != null && !"".equals(user.getMobile())) {
//            int result = SMSCommonUtil
//                    .checkSMSVcode(SMSCommonUtil.SEND_VCODE_EVENT,
//                            SMSCommonUtil.SEND_VCODE_EVENT_VALID_COUNT, user.getMobile(),
//                            authcode);
//
//            CommonUtils.writeJSON(result);
//        }
//    }
//
//    /**
//     * 验证手机号
//     * @return
//     */
//    public boolean validateMobile() {
//        try {
//            User admin = authenticationBean.getAdmin(user.getMobile());
//            if(admin!=null){
//                return true;
//            }
//        } catch (Exception e) {
//            printLog(e);
//        }
//        return false;
//    }
//
//    /**
//     * 发送短信验证码
//     *
//     * @return
//     * @throws Exception
//     */
//    public String sendSmsVcode() throws Exception {
//        int result = -1;
//        try {
//            String vcodeKey = Constants.generateJedisKey(SMSCommonUtil.SEND_VCODE_REG + mobile);
//            String value=null;
//            try {
//                value = JedisUtil.getAttribute(vcodeKey);
//            } catch (Exception e1) {
//                log.info(e1);
//            }
//            boolean sendFlag = false;
//            if (StringUtils.isEmpty(value)) {
//                // 下发短信
//                sendFlag = true;
//            } else {
//                result = 1;
//                sendFlag = false;
//            }
//            if (sendFlag) {
//                // 下发短信
//
//                Random random = new Random();
//                StringBuilder vcode = new StringBuilder();
//                vcode.append(random.nextInt(10)).append(random.nextInt(10))
//                        .append(random.nextInt(10)).append(random.nextInt(10));
//                boolean f = false;
//                try {
//                    //
//                    String vcodeText = "您的验证码是：$(rand)。请不要把验证码泄露给其他人。";
//                    String serviceUrl =systemConfig.getSmsHttpSendConfs().getProperty("wyq_sendsms_url");
//                    String smsmsg = vcodeText.replace("$(rand)", vcode);
//                    String smsurl = serviceUrl.replace("$(mobile)", mobile);
//                    smsurl = smsurl.replace("$(smsType)", "4");
//                    smsurl = smsurl.replace("$(content)", URLEncoder.encode(smsmsg, "gbk"));
//                    log.info(smsurl);
//                    IHttpURLHandler urlHandler = new SimpleHttpURLHandler();
//                    urlHandler.setUrl(smsurl);
//                    urlHandler.setEncoding("gbk");
//                    urlHandler.setRequestMethod(IHttpURLHandler.REQUEST_MODE_GET);
//                    urlHandler.setResponseContentType(IHttpURLHandler.RESPONSE_CONTENTTYPE_TEXT);
//                    urlHandler.setConnectTimeout(20000);// 设置连接超时 20秒
//                    urlHandler.setReadTimeout(40000);// 设置读取超时 40秒
//                    String response = (String) SimpleHttpUtils.get(urlHandler);
//                    if (response != null && response.indexOf("<code>1</code>") != -1) {
//                        f = true;
//                    }
//                } catch (Exception e) {
//                    SimpleUtils.log(e, log);
//                }
//                if (f) {
//                    // 保存
//                    JedisUtil.setAttribute(vcodeKey, vcode.toString(), 120);
//                    // 记录验证次数
//                    JedisUtil.setAttribute(Constants.generateJedisKey(SMSCommonUtil.SEND_VCODE_REG_VALID_COUNT + mobile), "0", 120);
//
//                    result = 0;
//                } else {
//                    result = 2;
//                }
//            }
//        } catch (Exception e) {
//            SimpleUtils.log(e, log);
//            printLog(e);
//        } finally {
//            HttpServletResponse response = ServletActionContext.getResponse();
//            response.setContentType("text/plain; charset=UTF-8");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setCharacterEncoding("utf-8");
//
//            JSONObject jsonObj = new JSONObject();
//            jsonObj.put("success", result);
//            String resultDatas = jsonObj.toString();
//            PrintWriter out = response.getWriter();
//            out.print(resultDatas);
//            out.flush();
//            out.close();
//        }
//
//        return null;
//    }
//
//    /**
//     * 忘记密码修改页
//     *
//     * @return
//     * @throws Exception
//     */
//    public String lostPassword() throws Exception {
//        log.info("-----lostPassword-----");
//        mobile_number_regex = SysConfig.MOBILE_NUMBER_REGEX;
//        return "lostPassword";
//    }
    /**
     * 检查手机号
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "checkMobile")
    @ResponseBody
    public void checkMobile(String mobile){
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpServletResponse response = servletRequestAttributes.getResponse();
//            HttpServletRequest request = ServletActionContext.getRequest();
//            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/plain;charset=gbk");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            PrintWriter out = response.getWriter();
            String key = RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_JSESSIONID).append("checkMobile_").append(request.getSession().getId()).toString());
            String jsCountStr = RedisUtils.getAttribute(key);
            Integer jsCount = 0;//请求次数
            if(StringUtils.isNotBlank(jsCountStr)) {
                jsCount = Integer.valueOf(jsCountStr);
            }

            if(jsCount >= 3) {
                out.println(2);
            }else {
                RedisUtils.setAttribute(RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_JSESSIONID).append("checkMobile_").append(request.getSession().getId()).toString()), String.valueOf(++jsCount), 10 * 60);
                BaseDto baseDto = userFeignClient.checkMobile(mobile);
                String code = baseDto.getCode();
                if (code.equals(CodeConstant.SUCCESS_CODE)) {
                    out.println(1);// 返回json格式数据
                } else {
                    out.println(0);// 返回json格式数据
                }
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            printLog(e);
        }
    }
//    /**
//     * 修改密码
//     * @return
//     */
//    public void doUpdatePassword(){
//        int result = 2;
//        try {
//            //验证验证码是否正确
//            if (admin != null && StringUtils.isNotBlank(admin.getMobile()) && StringUtils.isNotBlank(passwordVCode)) {
//                result = SMSCommonUtil.checkSMSVcode(SMSCommonUtil.SEND_VCODE_PASSWORD,
//                        SMSCommonUtil.SEND_VCODE_PASSWORD_VALID_COUNT,
//                        admin.getMobile(),
//                        passwordVCode);
//            }
//            if(result == 1){
//                int num = admin.getPasswordStrength();
//                password = MD5Util.md5(admin.getPassword());
//                admin = userBean.findUserByMobilePhone(admin.getMobile());
//                OperateLogUserInfo beforeObject = (OperateLogUserInfo) CommonUtils.generateOperateLogObject(1, admin);
//                admin.setPasswordStrength(num);
//                admin.setPassword(password);
//                userBean.updateUserPassword(admin);
//                OperateLogUserInfo afterObject = (OperateLogUserInfo) CommonUtils.generateOperateLogObject(1, admin);
//
//                CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_USER_U, Constants.OPERATE_LOG_OPERATE_TYPE_U, beforeObject, afterObject, null);
//                mobile = admin.getMobile();
//                admin = null;
//            }
//
//        } catch (Exception e) {
//            printLog(e);
//            result = 2;
//        }
//        CommonUtils.writeJSON(result);
//    }
//    public static void main(String[] args) {
//        log.info(MD5Util.md5("123456"));
//        log.info(MD5Util.md5(MD5Util.md5("abc123")));
//    }
//
//    /**
//     * 修改密码
//     *
//     * @return
//     */
//    public String updatePasswordSuccess() {
//        return "updatePasswordSuccess";
//    }
//
//    /**
//     * 支付完成的异步处理方法
//     *
//     * @throws Exception
//     */
//    @SuppressWarnings("rawtypes")
//    public void doNotify() throws Exception {
//        log.info("doNotify --> start");
//        try {
//            Map<String, String> params = new HashMap<String, String>();
//            HttpServletRequest request = ServletActionContext.getRequest();
//            Map requestParams = request.getParameterMap();
//            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//                String name = (String) iter.next();
//                String[] values = (String[]) requestParams.get(name);
//                String valueStr = "";
//                for (int i = 0; i < values.length; i++) {
//                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//                }
//
//                params.put(name, valueStr);
//                log.info("doNotify --> [" + name + "] = [" + valueStr + "]");
//            }
//
//            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//            // 商户订单号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "gbk");
//
//            // 支付宝交易号
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "gbk");
//
//            // 交易状态
//            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "gbk");
//
//            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
//
//            // 计算得出通知验证结果
//            boolean verify_result = AlipayNotify.verify(params);
//            log.info("doNotify --> verify_result = [" + verify_result + "] innerTradeNo = [" + out_trade_no + "]");
//            HttpServletResponse response = ServletActionContext.getResponse();
//            if (verify_result) {
//                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
//                    PayRecord p = orderRecordBean.findPayRecordById(orderRecordBean.findPayRecordByInnerTradeNo(out_trade_no).getPayRecordId());
//                    log.info("doNotify --> innerTradeNo = [" + out_trade_no + "] trade_status = [" + trade_status + "]");
//                    if (p != null && p.getPayStatus() != 1) {
//                        admin = userBean.findUserById(p.getUserId());
//                        String rtnStr = orderRecordBean.doPayEndV3(out_trade_no, admin, trade_no);
//                        log.info("doNotify --> innerTradeNo = [" + out_trade_no + "] trade_status = [" + trade_status + "] rtnStr = [" + rtnStr + "]");
//                        if (!StringUtils.isBlank(rtnStr)) {
//                            JSONObject jsonObject = JSONObject.parseObject(rtnStr);
//                            if (Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(jsonObject.get("code"))) {
//                                log.info("doNotify --> innerTradeNo = [" + out_trade_no + "] payResult = [SUCCESS]");
//                                // 通知支付宝成功
//                                response.getWriter().println("success");
//                            }
//                        }
//                    } else {
//                        if (p == null)
//                            log.info("doNotify --> payRecord is null! out_trade_no = [" + out_trade_no + "]");
//                        else
//                            log.info("doNotify --> pay_record_id = [" + p.getPayRecordId() + "] payStatus = [" + p.getPayStatus() + "]  out_trade_no = [" + out_trade_no + "]");
//                    }
//                } else {
//                    orderRecordBean.doPayRecordFail(out_trade_no, "trade_status=" + trade_status);
////					saveUserOperateLog(0, Constants.OPERATE_LOG_TYPE_PAY, "支付宝NOTIFY：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + trade_status + "]");
//                    log.info("doNotify --> innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + trade_status + "]");
//                }
//
//            } else {
//                orderRecordBean.doPayRecordFail(out_trade_no, "verify_result=" + verify_result);
////				saveUserOperateLog(0, Constants.OPERATE_LOG_TYPE_PAY, "支付宝NOTIFY：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + verify_result + "]");
//                log.info("doNotify --> innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + verify_result + "]");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            printLog(e);
//        }
//
//    }
//
//
//    /**
//     * 检查订单的状态
//     *
//     * @throws Exception
//     */
//    public void checkOrderStatus() throws Exception {
//        fetchSessionAdmin();
//        if(admin != null) {
//            PayRecord p = orderRecordBean.findPayRecord(payRecord.getPayRecordId(),admin.getUserId());
//            String redirectPage = "";
//
//            if (p != null && p.getPayStatus() != null && p.getPayStatus() == 1) {
//                String[] keywordProducts = ArrayUtils.addAll(SysConfig.PACKAGE_TYPE_CONTAIN_KEYWORD_IDS.split(","), SysConfig.PACKAGE_TYPE_CONTAIN_KEYWORD_RENEW_IDS.split(","));
//                String[] keywordProProducts = ArrayUtils.addAll(SysConfig.PACKAGE_TYPE_CONTAIN_KEYWORD_AND_PRO_FUNCTION_IDS.split(","), SysConfig.PACKAGE_TYPE_CONTAIN_KEYWORD_AND_PRO_RENEW_FUNCTION_IDS.split(","));
//                String[] kProducts = ArrayUtils.addAll(keywordProducts, keywordProProducts);
//                String[] analysisProducts = SysConfig.PACKAGE_TYPE_ANALYSIS_IDS.split(",");
//                String[] weiboAnalysisProducts = SysConfig.PACKAGE_TYPE_WEIBO_ANALYSIS_IDS.split(",");
//                String[] briefProducts = SysConfig.PACKAGE_TYPE_BRIEF_IDS.split(",");
//                String[] reportProducts = SysConfig.PACKAGE_TYPE_REPORT_IDS.split(",");
//                String[] productAnalysisProducts = SysConfig.PACKAGE_TYPE_PRODUCT_ANALYSIS_IDS.split(",");
//                String[] swaProducts = SysConfig.PACKAGE_TYPE_SINGLE_WEIBO_ANALYSIS_IDS.split(",");
//                int keywordCount = 0;
//                int analysisCount = 0;
//                int weiboAnalysisCount = 0;
//                int briefCout = 0;
//                int reportCount = 0;
//                int productAnalysisCount = 0;
//                int swaCount = 0;
//                if (payRecord != null) {
//                    if (payRecord.getPayRecordId() != null && payRecord.getPayRecordId() > 0) {
//                        List<PayOrderRelation> listPayOrderRelation = orderRecordBean.findPayOrderRelationById(payRecord.getPayRecordId());
//                        if (listPayOrderRelation != null && listPayOrderRelation.size() > 0) {
//                            for (PayOrderRelation payOrderRelation : listPayOrderRelation) {
//                                Order order = orderRecordBean.getOrderById(payOrderRelation.getOrderRecordId());
//                                if (order != null) {
//                                    List<OrderCartRelation> listOrderCartRelation = orderRecordBean.findCatIdByOrderId(payOrderRelation.getOrderRecordId());
//                                    if (listOrderCartRelation != null && listOrderCartRelation.size() > 0) {
//                                        for (OrderCartRelation orderCartRelation : listOrderCartRelation) {
//                                            CartRecord c = orderRecordBean.findCartRecordById(orderCartRelation.getCartRecordId());
//                                            if (c != null) {
//                                                if (Arrays.asList(kProducts).contains(c.getProductPackageId().toString())) {
//                                                    keywordCount++;
//                                                } else if (Arrays.asList(analysisProducts).contains(c.getProductPackageId().toString())) {
//                                                    analysisCount++;
//                                                } else if (Arrays.asList(briefProducts).contains(c.getProductPackageId().toString())) {
//                                                    briefCout++;
//                                                } else if (Arrays.asList(reportProducts).contains(c.getProductPackageId().toString())) {
//                                                    reportCount++;
//                                                } else if (Arrays.asList(productAnalysisProducts).contains(c.getProductPackageId().toString())) {
//                                                    productAnalysisCount++;
//                                                } else if (Arrays.asList(swaProducts).contains(c.getProductPackageId().toString())) {
//                                                    swaCount++;
//                                                } else if (Arrays.asList(weiboAnalysisProducts).contains(c.getProductPackageId().toString())) {
//                                                    weiboAnalysisCount++;
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (keywordCount > 0 || analysisCount > 0 || briefCout > 0 || reportCount > 0 || productAnalysisCount > 0 || swaCount > 0 || weiboAnalysisCount > 0) {
//                    if (keywordCount > 0 && analysisCount == 0 && briefCout == 0 && reportCount == 0 && productAnalysisCount == 0 && weiboAnalysisCount == 0) { // 单独监测方案
//                        redirectPage = "keyword";
//                    } else if (keywordCount == 0 && analysisCount > 0 && briefCout == 0 && reportCount == 0 && productAnalysisCount == 0 && swaCount == 0 && weiboAnalysisCount == 0) { // 单独全网事件分析
//                        redirectPage = "analysis";
//                    } else if (keywordCount == 0 && analysisCount == 0 && briefCout > 0 && reportCount == 0 && productAnalysisCount == 0 && swaCount == 0 && weiboAnalysisCount == 0) { // 单独简报制作
//                        redirectPage = "brief";
//                    } else if (keywordCount == 0 && analysisCount == 0 && briefCout == 0 && reportCount > 0 && productAnalysisCount == 0 && swaCount == 0 && weiboAnalysisCount == 0) { // 单独订阅中心
//                        redirectPage = "report";
//                    } else if (keywordCount == 0 && analysisCount == 0 && briefCout == 0 && reportCount == 0 && productAnalysisCount > 0 && swaCount == 0 && weiboAnalysisCount == 0) { // 单独竞品分析
//                        redirectPage = "productAnalysis";
//                    } else if (keywordCount == 0 && analysisCount == 0 && briefCout == 0 && reportCount == 0 && productAnalysisCount == 0 && swaCount > 0 && weiboAnalysisCount == 0) { // 单独单条微博分析
//                        redirectPage = "swa";
//                    } else if (keywordCount == 0 && analysisCount == 0 && briefCout == 0 && reportCount == 0 && productAnalysisCount == 0 && swaCount == 0 && weiboAnalysisCount > 0) { // 单独微博事件分析
//                        redirectPage = "weiboAnalysis";
//                    }
//                }
//            }
//
//            Map<String, Object> rtnMap = new HashMap<String, Object>();
//            rtnMap.put("payRecord", p);
//            rtnMap.put("redirectPage", redirectPage);
//
//            CommonUtils.writeJSON(rtnMap);
//        }
//    }
//
//    /**
//     * 校验验证码
//     *
//     * @throws Exception
//     */
//    public void checkUpdatePasswordVcode() throws Exception {
//        int result = 2;
//        if (admin != null && StringUtils.isNotBlank(admin.getMobile()) && StringUtils.isNotBlank(passwordVCode)) {
//            result = SMSCommonUtil.checkSMSVcode(SMSCommonUtil.SEND_VCODE_PASSWORD,
//                    SMSCommonUtil.SEND_VCODE_PASSWORD_VALID_COUNT,
//                    admin.getMobile(),
//                    passwordVCode);
//        }
//        CommonUtils.writeJSON(result);
//    }
//
//
//    /**
//     * 发送短信验证码
//     *
//     * @return
//     * @throws Exception
//     */
//    public void sendSmsLostVcode() throws Exception {
//        log.info("sendSmsLostVcode --> into action");
//        log.info("sendSmsLostVcode --> mobile is " + mobile);
//        if (mobile != null && !"".equals(mobile)) {
//            log.info("sendSmsLostVcode --> begin send vcode");
////			saveUserOperateLog(0, Constants.OPERATE_LOG_TYPE_SYS_SMS, "找回密码发送短信：mobile = [" + mobile + "]");
//            CommonUtils.opreateLog(ServletActionContext.getRequest(), null, Constants.OPERATE_LOG_PRODUCT_SECTION_SYS_SMS, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);
//            int result = SMSCommonUtil.sendSMSVcode(
//                    SMSCommonUtil.SEND_VCODE_PASSWORD,
//                    SMSCommonUtil.SEND_VCODE_PASSWORD_VALID_COUNT,
//                    mobile,
//                    systemConfig.getSmsHttpSendConfs().getProperty("wyq_sendsms_url"),
//                    ServletActionContext.getRequest());
//            log.info("sendSmsLostVcode --> send vcode success result is " + result);
//
//            CommonUtils.writeJSON(result);
//        }
//    }
//
//    /**
//     * 绑定第三方账号
//     * @return
//     * @throws Exception
//     */
//    public String doBindThirdParty() throws Exception {
//        fetchSessionAdmin();
//        fetchProductlist(Constants.PACKAGE_TYPE_FREE);
//        HttpServletRequest req = ServletActionContext.getRequest();
//        HttpServletResponse rep = ServletActionContext.getResponse();
//        if (showFlag == 1) { // 注册并绑定
//            /*
//             * int result = SMSCommonUtil.checkSMSVcode(SMSCommonUtil.SEND_VCODE_REG,
//             * SMSCommonUtil.SEND_VCODE_REG_VALID_COUNT, user.getMobile(), authcode); if (result ==
//             * 1) {
//             */
//            UserCreateVO userCreateVO = new UserCreateVO();
//            userCreateVO.setUsername(user.getMobile());
//            userCreateVO.setPassword(MD5Util.md5(user.getPassword()));
//            userCreateVO.setAppUserStatus(0);
//            userCreateVO.setUserChannel(Constants.USER_CHANNEL_WYQ);
//            userCreateVO.setGiftPackageId(packageListFree.get(0).getProductPackageId());
//            userCreateVO.setLogin(true);
//            String userExclusiveChannelCode = null;
//            if (req.getCookies() != null && req.getCookies().length > 0) {
//                for (Cookie cookie : req.getCookies()) {
//                    if ("exclusive_channel_code".equals(cookie.getName())) {
//                        String code = cookie.getValue();
//                        if (StringUtils.isNoneBlank(code))
//                            userExclusiveChannelCode = code;
//                        break;
//                    }
//                }
//            }
//            if (StringUtils.isNotBlank(userExclusiveChannelCode))
//                userCreateVO.setExclusiveChannelCode(userExclusiveChannelCode);
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("userParam", JSONObject.toJSONString(userCreateVO));
//            String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(req,
//                    IntraBusinessAPIConfig.getValue("userCreateMobileURL"), params);
//            log.info("doRegisterV2 create user rtnStr = [" + rtnStr + "]");
//            if (StringUtils.isBlank(rtnStr))
//                return ERROR;
//            UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//            if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode()))
//                return ERROR;
//            String loginRes = LoginUtils.doLogin(req, rep, userRes, false);
//            if (!"0000".equals(loginRes)) {
//                return ERROR;
//            }
//            log.info("doRegister success!");
//            admin = userResConvertAdmin(userRes);
//            userThirdpartyAuthInfo.setUserId(admin.getUserId());
//            userThirdpartyAuthInfo.setApplicationType(1);
//            userThirdpartyAuthInfo.setStatus(Constants.STATUS_VALID);
//            userThirdpartyAuthInfo.setAuthTime(new Date());
//            userThirdpartyAuthInfo.setExpireTime(new Date(new Date().getTime() + userThirdpartyAuthInfo.getExpireIn() * 1000));
//            userBean.doBindThirdpartyAccount(userThirdpartyAuthInfo);
//            /*
//             * }else{ return "toBindThirdParty"; }
//             */
//        } else if (showFlag == 2) { // 登录并绑定
//            if (!validateLogins()) // 登录验证失败
//                return "toBindThirdParty";
//            // 绑定用户
//            userThirdpartyAuthInfo.setUserId(admin.getUserId());
//            userThirdpartyAuthInfo.setApplicationType(1);
//            userThirdpartyAuthInfo.setStatus(Constants.STATUS_VALID);
//            userThirdpartyAuthInfo.setAuthTime(new Date());
//            userThirdpartyAuthInfo.setExpireTime(new Date(new Date().getTime() + userThirdpartyAuthInfo.getExpireIn() * 1000));
//            userBean.doBindThirdpartyAccount(userThirdpartyAuthInfo);
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("username", username);
//            params.put("password", password);
//            String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(req,
//                    IntraBusinessAPIConfig.getValue("userLoginMobileURL"), params);
//            if (StringUtils.isBlank(rtnStr))
//                return ERROR;
//            UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//            if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode()))
//                return ERROR;
//            String loginRes = LoginUtils.doLogin(req, rep, userRes, false);
//            if (!"0000".equals(loginRes)) {
//                return ERROR;
//            }
//            log.info("UserAction.indexLogin : rtnStr = [" + rtnStr + "]");
//        } else if (showFlag == 3) { // 不绑定，直接登录
//        }
//        return "doBindThirdParty";
//    }
//
//    /**
//     *
//     * 绑定手机号
//     *
//     * @param
//     * @since 2017年3月7日 下午3:23:06
//     * @author  virgo
//     * @return
//     */
//    public String doBindPhone() throws Exception {
//        fetchSessionAdmin();
//        if (admin != null) {
//            user.setUserId(admin.getUserId());
//            user.setUsername(user.getMobile());
//            user.setPassword(MD5Util.md5(user.getPassword()));
//            userBean.doModifyUserInfo4BindPhone(user);
//
//            HttpServletRequest request = ServletActionContext.getRequest();
//            String sid = LoginUtils.getSidFromCookie(request);
//
//            Map < String, Object > params = new HashMap < String, Object >();
//            params.put("sid", sid);
//            //刷新用户缓存信息
//            String result =  CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("refreshSesssionUserInfo"), params);
//            log.info("refresh user info return result is "+result);
//
//            //绑定手机号增加微豆
//            params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
//            params.put("userWdRightsItem", Constants.USER_WD_RIGHTS_ITEM_TASK_BIND_PHONE);
//            params.put("relationId", admin.getUserId());
//            CommonUtils.sendIntraBusinessAPIPOST(ServletActionContext.getRequest(), IntraBusinessAPIConfig.getValue("taskAddWd"), params);
//
//            return "doBindPhone";
//        }
//        return LOGIN;
//    }
//    /**
//     * 验证登陆
//     *
//     */
//    public boolean validateLogins() {
//        ServletActionContext.getResponse().setHeader("P3P", "CP=CAO PSA OUR");
//
//        if (username == null || "".equals(username)) {
//            addActionError("请输入您的帐号！");
//            msg = "请输入您的手机号";
//            return false;
//        }
//        if (password == null || "".equals(password.trim())) {
//            addActionError("请输入您的密码！");
//            msg = "请输入您的密码！";
//            return false;
//        }
//
//		/*if (imgVcode == null || "".equals(imgVcode.trim())) {
//			addActionError("请输入您的验证码！");
//			msg = "请输入您的验证码！";
//			return false;
//		}*/
//
//        // 验证当日登录输错密码次数
//        int userLoginValidCount = 0;
//        int validCount = Integer.parseInt(SysConfig.USER_LOGIN_VALID_COUNT);
//        String userLoginValid = JedisUtil.getAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_USER_LOGIN_VALID_COUNT + username));
//        if (StringUtils.isNotBlank(userLoginValid))
//            userLoginValidCount = Integer.parseInt(userLoginValid);
//
//        if (userLoginValidCount >= validCount) {
//            msg = "密码输入次数已超过限制，请明天再试！";
//            return false;
//        }
//
//        password = MD5Util.md5(password);
//        User admin = authenticationBean.getAdmin(username);
//        if (admin != null) {
//            if (!admin.getPassword().equals(password)) {
//                if (userLoginValidCount < (validCount - 1))
//                    msg = "用户名或密码错误！还可以再试 " + (validCount - userLoginValidCount - 1) + " 次！";
//                else
//                    msg = "用户名或密码错误！密码输入次数已超过限制，请明天再试！";
//
//                // 获取距当天零点的时间差
//                Calendar calendar = Calendar.getInstance();
//                calendar.add(Calendar.DAY_OF_MONTH, 1);
//                calendar.set(Calendar.HOUR_OF_DAY, 0);
//                calendar.set(Calendar.MINUTE, 0);
//                calendar.set(Calendar.SECOND, 0);
//                calendar.set(Calendar.MILLISECOND, 0);
//                int timeDifference = (int) ((calendar.getTime().getTime() - (new Date().getTime()))/1000);
//
//                JedisUtil.setAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_USER_LOGIN_VALID_COUNT + username), String.valueOf((userLoginValidCount + 1)), timeDifference);
//                return false;
//            }
//        }
//
//		/*log.info(SessionUtil.getAttribute(SysConfig.memoCacheName, "wyq_lg_" + _ran));
//		String vcodepre_login = "";
//		Object obj = SessionUtil.getAttribute(SysConfig.memoCacheName, "wyq_lg_" + _ran);
//		if (obj != null) {
//			vcodepre_login = obj.toString();
//		}
//
//		if (!vcodepre_login.trim().toLowerCase().equals(imgVcode.trim().toLowerCase())) {
//			addActionError("验证码不正确！");
//			msg = "验证码不正确！";
//			return false;
//		}*/
//        if (admin == null) {
//            addActionError("账号“" + username + "”不存在！");
//            msg = "用户名不存在！";
//            return false;
//        }
//        if (admin != null && admin.getStatus() != 1) {
//            admin = null;
//            addActionError("账号“" + username + "”已停用！");
//            msg = "该用户已被停用，请联系管理员！";
//            return false;
//        }
//        String inputPassword = password.trim();
//
//        if (admin.getPassword().length() > 30) {// 用户的密码为加密密码，需加密对比
//            inputPassword = authenticationBean.encodePassword(password);
//        }
//        if (!admin.getPassword().equals(inputPassword)) {
//
//            // addActionError("账号密码" + inputPassword );
//
//        }
//        if (admin.getStatus() == 2) {
//            addActionError("账号“" + username + "”已被删除！");
//        }
//
//        if (!getActionErrors().isEmpty()) {
//
//            return false;
//        }
//        if (admin != null) {
//
//        }
//        if (getActionErrors().isEmpty()) {
//            // authenticationBean.updateUserLoginTime(admin);// 记录登陆时间
//            this.admin = admin;
//        }
//
//        JedisUtil.removeAttribute(Constants.generateJedisKey(Constants.JEDIS_KEYS_USER_LOGIN_VALID_COUNT + username));
//        // 是否需要验证码判断
//        return true;
//    }
//
//
//    /**
//     * 支付回调
//     *
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    public void weixinPayResult() throws Exception {
//        String return_code = null, return_msg = null, result_code = null, err_code_des = null;
//
//        String prepayId = null;
//
//        // 接收回调参数
//        HttpServletRequest request = ServletActionContext.getRequest();
//        InputStream is = request.getInputStream();
//
//        // 解析
//        SAXReader reader = new SAXReader();
//        Document readDocument = reader.read(is);
//        Element readRoot = readDocument.getRootElement();
//        List<Element> nodes = readRoot.elements();
//        Map<String, String> params = new HashMap<String, String>();
//        for (Element element : nodes) {
//            params.put(element.getName(), element.getText());
//        }
//
//        // 签名
//        String sign = PayUtil.generateSign(params, WeixinPayConfig.KEY);
//
//        // 签名校验通过
//        if (sign != null && !"".equals(sign) && sign.equals(readRoot.elementText("sign"))) {
//            String innerTradeNo = readRoot.elementText("product_id");
//            PayRecord payRecord = orderRecordBean.findPayRecordByInnerTradeNo(innerTradeNo);
//
//            // 调用微信下单API
//            // 组装参数
//            Map<String, String> sendParams = new HashMap<String, String>();
//            Document document = DocumentHelper.createDocument();
//            Element root = document.addElement("xml");
//
//            // appid
//            Element appIdElement = root.addElement("appid");
//            appIdElement.addCDATA(WeixinPayConfig.APP_ID);
//            sendParams.put("appid", WeixinPayConfig.APP_ID);
//
//            // mch_id
//            Element mchidElement = root.addElement("mch_id");
//            mchidElement.addCDATA(WeixinPayConfig.MCH_ID);
//            sendParams.put("mch_id", WeixinPayConfig.MCH_ID);
//
//            // nonce_str
//            Element nonceStrElement = root.addElement("nonce_str");
//            String randomStr = PayUtil.getRandomStringByLength(32);
//            nonceStrElement.addCDATA(randomStr);
//            sendParams.put("nonce_str", randomStr);
//
//            // body
//            Element bodyElement = root.addElement("body");
//            bodyElement.addCDATA(payRecord.getPayName());
//            sendParams.put("body", payRecord.getPayName());
//
//            // out_trade_no
//            Element outTradeNoElement = root.addElement("out_trade_no");
//            outTradeNoElement.addCDATA(innerTradeNo);
//            sendParams.put("out_trade_no", innerTradeNo);
//
//            // total_fee
//            String totalFee = String.valueOf(Math.round(payRecord.getTotalFee() * 100));
////			String totalFee = String.valueOf((int) (0.01 * 100));
//            Element totalFeeElement = root.addElement("total_fee");
//            totalFeeElement.addCDATA(totalFee);
//            sendParams.put("total_fee", totalFee);
//
//            // spbill_create_ip
//            InetAddress address = InetAddress.getLocalHost();
//            Element spbillCreateIPElement = root.addElement("spbill_create_ip");
//            spbillCreateIPElement.addCDATA(address.getHostAddress());
//            sendParams.put("spbill_create_ip", address.getHostAddress());
//
//            // notify_url
//            Element notifyURLElement = root.addElement("notify_url");
//            notifyURLElement.addCDATA(WeixinPayConfig.NOTIFY_URL);
//            sendParams.put("notify_url", WeixinPayConfig.NOTIFY_URL);
//
//            // trade_type
//            Element tradeTypeElement = root.addElement("trade_type");
//            tradeTypeElement.addCDATA(WeixinPayConfig.TRADE_TYPE);
//            sendParams.put("trade_type", WeixinPayConfig.TRADE_TYPE);
//
//            // 签名
//            String sendSign = PayUtil.generateSign(sendParams, WeixinPayConfig.KEY);
//
//            Element signElement = root.addElement("sign");
//            signElement.addCDATA(sendSign);
//
//            String result = PayUtil.sendPost(WeixinPayConfig.PAY_URL, root.asXML());
//
//            // 解析
//            Document payDocument = DocumentHelper.parseText(result);
//            Element payRoot = payDocument.getRootElement();
//            List<Element> payNodes = payRoot.elements();
//            Map<String, String> payParams = new HashMap<String, String>();
//            for (Element element : payNodes) {
//                payParams.put(element.getName(), element.getText());
//            }
//
//            // 正常返回
//            if (WeixinPayConfig.RETURN_CODE_SUCCESS.equals(payRoot.elementText("return_code")) && WeixinPayConfig.RETURN_MSG_OK.equals(payRoot.elementText("return_msg")) && WeixinPayConfig.RETURN_CODE_SUCCESS.equals(payRoot.elementText("result_code"))) {
//                // 签名
//                String paySign = PayUtil.generateSign(payParams, WeixinPayConfig.KEY);
//
//                // 签名校验通过
//                if (paySign.equals(payRoot.elementText("sign"))) {
//                    prepayId = payRoot.elementText("prepay_id");
//                    return_code = WeixinPayConfig.RETURN_CODE_SUCCESS;
//                    return_msg = WeixinPayConfig.RETURN_MSG_OK;
//                    result_code = WeixinPayConfig.RETURN_CODE_SUCCESS;
//                } else {
//                    return_code = WeixinPayConfig.RETURN_CODE_FAIL;
//                    return_msg = "签名失败";
//                }
//            } else {
//                return_code = payRoot.elementText("return_code");
//                return_msg = payRoot.elementText("return_msg");
//                result_code = payRoot.elementText("result_code");
//                err_code_des = payRoot.elementText("err_code_des");
//            }
//        } else {
//            return_code = WeixinPayConfig.RETURN_CODE_FAIL;
//            return_msg = "签名失败";
//        }
//
//        // 返回
//        Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("xml");
//        Map<String, String> params1 = new HashMap<String, String>();
//
//        // return_code
//        Element returnCodeElement = root.addElement("return_code");
//        returnCodeElement.addCDATA(return_code);
//        params1.put("return_code", return_code);
//
//        // return_msg
//        Element returnMsgElement = root.addElement("return_msg");
//        returnMsgElement.addCDATA(return_msg);
//        params1.put("return_msg", return_msg);
//
//        // appid
//        Element appidElement = root.addElement("appid");
//        appidElement.addCDATA(WeixinPayConfig.APP_ID);
//        params1.put("appid", WeixinPayConfig.APP_ID);
//
//        // mch_id
//        Element mchIdElement = root.addElement("mch_id");
//        mchIdElement.addCDATA(WeixinPayConfig.MCH_ID);
//        params1.put("mch_id", WeixinPayConfig.MCH_ID);
//
//        // nonce_str
//        String nonceStr = PayUtil.getRandomStringByLength(32);
//        Element nonceStrElement = root.addElement("nonce_str");
//        nonceStrElement.addCDATA(nonceStr);
//        params1.put("nonce_str", nonceStr);
//
//        // prepay_id
//        Element prepayElement = root.addElement("prepay_id");
//        prepayElement.addCDATA(prepayId);
//        params1.put("prepay_id", prepayId);
//
//        // result_code
//        Element resultCodeElement = root.addElement("result_code");
//        resultCodeElement.addCDATA(result_code);
//        params1.put("result_code", result_code);
//
//        if (err_code_des != null) {
//            // err_code_des
//            Element errCodeDesElement = root.addElement("err_code_des");
//            errCodeDesElement.addCDATA(err_code_des);
//            params1.put("err_code_des", err_code_des);
//        }
//
//        String sign1 = PayUtil.generateSign(params1, WeixinPayConfig.KEY);
//        Element signElement = root.addElement("sign");
//        signElement.addCDATA(sign1);
//
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setContentType("text/xml;charset=UTF-8");
//        response.getWriter().println(root.asXML());
//    }
//
//    /**
//     * 微信支付回调
//     *
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    public void doWeixinNotify() throws Exception {
//        log.info("doWeixinNotify --> start");
//        // 接收回调参数
//        HttpServletRequest request = ServletActionContext.getRequest();
//        InputStream is = request.getInputStream();
//
//        // 解析
//        SAXReader reader = new SAXReader();
//        Document readDocument = reader.read(is);
//        Element readRoot = readDocument.getRootElement();
//        List<Element> nodes = readRoot.elements();
//
//        Map<String, String> params = new HashMap<String, String>();
//        StringBuilder wgwNotifyUrl = new StringBuilder();
//        wgwNotifyUrl.append("http://wgw.city.sina.com.cn/oauth/weixin_pay.php?");
//        for (Element element : nodes) {
//            params.put(element.getName(), element.getText());
//            wgwNotifyUrl.append(element.getName() + "=" + element.getText() + "&");
//            log.info("doWeixinNotify --> " + element.getName() + " = [" + element.getText() + "]");
//        }
//        wgwNotifyUrl = new StringBuilder(wgwNotifyUrl.substring(0, wgwNotifyUrl.length() - 1));
//        log.info("doWeixinNotify --> wgwNotifyUrl = [" + wgwNotifyUrl + "]");
//
//        // 通信成功
//        if (WeixinPayConfig.RETURN_CODE_SUCCESS.equals(readRoot.elementText("return_code"))) {
//            log.info("doWeixinNotify --> return_code = [" + readRoot.elementText("return_code") + "]");
//            String innerTradeNo = readRoot.elementText("out_trade_no");
//            if (innerTradeNo != null && !"".equals(innerTradeNo)) {
//                log.info("doWeixinNotify --> innerTradeNo = [" + innerTradeNo + "]");
//                if (innerTradeNo.toLowerCase().contains("wgw")) { // 微官网订单
//                    log.info("doWeixinNotify : wgw order start innerTradeNo = [" + innerTradeNo + "]");
//                    log.info("doWeixinNotify : wgw order forward url is " + wgwNotifyUrl + " innerTradeNo = [" + innerTradeNo + "]");
//                    String result = HttpRequestUtils.sendGet(wgwNotifyUrl.toString(), StringUtils.EMPTY);
//                    log.info("doWeixinNotify : wgw order forward result is " + result + " innerTradeNo = [" + innerTradeNo + "]");
//                    if (result != null && !"".equals(result) && "SUCCESS".equals(result.toUpperCase())) {
//                        log.info("doWeixinNotify : wgw order success innerTradeNo = [" + innerTradeNo + "]");
//                        ServletActionContext.getResponse().getWriter().println("SUCCESS");
//                    } else {
//                        log.info("doWeixinNotify : wgw order error " + result + " innerTradeNo = [" + innerTradeNo + "]");
//                    }
//                } else { // 微热点订单
//                    log.info("doWeixinNotify : wyq order start innerTradeNo = [" + innerTradeNo + "]");
//                    // 交易成功
//                    log.info("doWeixinNotify --> result_code = [" + readRoot.elementText("result_code") + "] innerTradeNo = [" + innerTradeNo + "]");
//                    if (WeixinPayConfig.RETURN_CODE_SUCCESS.equals(readRoot.elementText("result_code"))) {
//                        // 签名
//                        String sign = PayUtil.generateSign(params, WeixinPayConfig.KEY);
//
//                        // 签名通过
//                        if (sign != null && !"".equals(sign) && sign.equals(readRoot.elementText("sign"))) {
//                            String trade_no = readRoot.elementText("transaction_id");
//                            PayRecord payRecord = orderRecordBean.findPayRecordByInnerTradeNo(innerTradeNo);
//
//                            if (payRecord != null && payRecord.getPayStatus() != Constants.PAY_STATUS_YES) {
//                                log.info("doWeixinNotify --> processing... pay_record_id = [" + payRecord.getPayRecordId() + "] innerTradeNo = [" + innerTradeNo + "]");
//                                user = userBean.findUserById(payRecord.getUserId());
//                                String rtnStr = orderRecordBean.doPayEndV3(innerTradeNo, user, trade_no);
//                                log.info("doWeixinNotify --> processing... pay_record_id = [" + payRecord.getPayRecordId() + "] innerTradeNo = [" + innerTradeNo + "] rtnStr = [" + rtnStr + "]");
//                                if (!StringUtils.isBlank(rtnStr)) {
//                                    JSONObject jsonObject = JSONObject.parseObject(rtnStr);
//                                    if (Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(jsonObject.get("code"))) {
//                                        // 通知微信支付成功
//                                        log.info("doWeixinNotify --> print success innerTradeNo = [" + innerTradeNo + "]");
//                                        ServletActionContext.getResponse().getWriter().println("SUCCESS");
//                                    }
//                                }
//                                log.info("doWeixinNotify --> success innerTradeNo = [" + innerTradeNo + "]");
//                            } else {
//                                if (payRecord == null)
//                                    log.info("doWeixinNotify --> payRecord is null! innerTradeNo = [" + innerTradeNo + "]");
//                                else
//                                    log.info("doWeixinNotify --> pay_record_id = [" + payRecord.getPayRecordId() + "] payStatus is [" + payRecord.getPayStatus() + "]! innerTradeNo = [" + innerTradeNo + "]");
//                            }
//
//                            UserAction.myOrderStatus.put("order_" + (user==null ? "":user.getUsername()), "1");
//                        } else {
//                            log.info("doWeixinNotify --> sign not matched! sign = [" + sign + "] paramSign = [" + readRoot.elementText("sign") + "] innerTradeNo = [" + innerTradeNo + "]");
//                        }
//                    } else {
//                        String failReason = "支付失败";
//                        if (readRoot.elementText("err_code_des") != null && !"".equals(readRoot.elementText("err_code_des").trim()))
//                            failReason = readRoot.elementText("err_code_des");
//                        // 交易失败
//                        orderRecordBean.doPayRecordFail(innerTradeNo, failReason);
//                        log.info("doWeixinNotify : 微信NOTIFY：innerTradeNo = [" + innerTradeNo + "] payResult = [FAILED] reason = [" + failReason + "]");
//                    }
//                }
//            } else {
//                log.info("doWeixinNotify --> innerTradeNo = [" + innerTradeNo + "]");
//            }
//        } else {
//            log.info("doWeixinNotify --> return_code = [" + readRoot.elementText("return_code") + "]");
//        }
//    }
//
//    /**
//     * 新浪轻应用授权跳转
//     *
//     * @return
//     * @throws Exception
//     */
//    public String sinaAuthRedirect() throws Exception {
//		/*HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//		log.info("sinaAuthRedirect sid = [" + sid + "]");
//		if (StringUtils.isNotBlank(sid)) {
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("sid", sid);
//
//			String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userSessionURL"), params);
//			log.info("sinaAuthRedirect rtnStr = [" + rtnStr + "]");
//
//			if (StringUtils.isBlank(rtnStr))
//				return LOGIN;
//
//			UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//			if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode()))
//				return LOGIN;
//
//			admin = userBean.findUserById(userRes.getUserInfo().getUserId());
//			if (admin != null) {
//				String memcacheSSOFlag = RequestInterFace.setUserForMemcachedWyq(request, response, userRes.getUserInfo().getUserId() + userRes.getUserInfo().getUsername(), userRes.getSid(), SystemConstants.SYS_ADMIN_CACHE_TIME, SysConfig.memoCacheName);
//				SessionUtil.setAttribute(Constants.MEMCACHE_SSO_FLAG + "_" + SysConfig.memoCacheName + userRes.getUserInfo().getUserId(), userRes.getUserInfo().getUserId() + "", memcacheSSOFlag, new Date().getTime() + Constants.MEMCACHE_SSO_EXPIRY);
//
//				// 检测活动推广页面
//				String activityCookie = null;
//				String activityCookieName = SysConfig.memoCacheName + "_activity";
//				Cookie[] cookies = request.getCookies();
//                if (cookies != null && cookies.length > 0) {
//                    for (Cookie cookie : cookies) {
//                        if (cookie.getName().equalsIgnoreCase(activityCookieName)) {
//                        	activityCookie = cookie.getValue();
//                            break;
//                        }
//                    }
//                }
//
//                if (StringUtils.isNotBlank(activityCookie)) {
//                	ActionContext.getContext().getValueStack().set("activityId", activityCookie);
//                	return "activityCookie";
//                }
//
//                // 获取监测列表
//                List<KeyWord> keyWords = keywordBean.getKwListByUserId(admin, Constants.STATUS_VALID);
//				if (keyWords != null && keyWords.size() > 0) {
//					return "sinaAuthRedirect";
//				} else {
//					return "sinaAuthRedirect_set";
//				}
//			}
//		}
//		*/
//        return "toSinaAuthRedirect";
//    }
//
//    /**
//     * 检测是否需要实名认证
//     *
//     * @param request
//     * @param response
//     * @param userRes
//     * @return
//     */
//    public String needRealName(HttpServletRequest request, HttpServletResponse response, UserRes userRes) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(userRes.getUserInfo().getUserId())));
//
//        String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("checkUserRealNameURL"), params);
//        if (StringUtils.isBlank(rtnStr))
//            return ERROR;
//
//        JSONObject jsonObject = JSONObject.parseObject(rtnStr);
//        if (jsonObject == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(jsonObject.getString("code")))
//            return ERROR;
//
//        if (jsonObject.getBooleanValue("needRealName")) { // 需要
//            user = new User();
//            user.setUserId(userRes.getUserInfo().getUserId());
//            user.setNickname(StringUtils.isBlank(userRes.getUserInfo().getNickname()) ? userRes.getUserInfo().getUsername() : userRes.getUserInfo().getNickname());
//            user.setUserHead(userRes.getUserInfo().getUserHead());
//
//            log.info("ThirdPartyAction.needRealName : User [" + userRes.getUserInfo().getUsername() + "] Time = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
//
//            return "needRealName";
//        }
//
//        return SUCCESS;
//    }
//
//    /**
//     * 检测登录次数是否超过短信验证次数
//     *
//     * @param userRes
//     * @return
//     */
//    public String needLoginCount(UserRes userRes) {
//        String loginCountLimitKey = Constants.generateJedisKey(new StringBuilder(Constants.JEDIS_KEYS_LOGIN_COUNT_LIMIT).append(userRes.getUserInfo().getMobile()).toString());
//        String loginCountLimitValue = JedisUtil.getAttribute(loginCountLimitKey);
//        if (StringUtils.isNotBlank(loginCountLimitValue)) {
//            JedisUtil.removeAttribute(loginCountLimitKey);
//            return SUCCESS;
//        }
//
//        int todayLoginCount = userCenterBean.findTodayLoginCountByUserId(userRes.getUserInfo().getUserId());
//        if (todayLoginCount >= 3) {
//            user = new User();
//            user.setMobile(userRes.getUserInfo().getMobile());
//            user.setNickname(StringUtils.isBlank(userRes.getUserInfo().getNickname()) ? userRes.getUserInfo().getUsername() : userRes.getUserInfo().getNickname());
//            user.setUserHead(userRes.getUserInfo().getUserHead());
//
//            // 获取距当天零点的时间差
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);
//            Long timeDifference = calendar.getTime().getTime() - (new Date().getTime());
//            JedisUtil.setAttribute(loginCountLimitKey, "1", (int) (timeDifference / 1000));
//
//            log.info("ThirdPartyAction.needLoginCount : User [" + userRes.getUserInfo().getUsername() + "] Time = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
//
//            return "needLoginCount";
//        }
//
//        return SUCCESS;
//    }
//
//    /**
//     * 支付完成后的异步处理方法
//     *
//     * @return
//     * @throws Exception
//     */
//    @SuppressWarnings("rawtypes")
//    public void doSinaNotify() throws Exception {
//        log.info("doSinaNotify --> start");
//        HttpServletRequest request = ServletActionContext.getRequest();
//        Map requestParams = request.getParameterMap();
//        Map<String, String> newParams = new TreeMap<String, String>();
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++)
//                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//
//            if ("sign".equals(name) || "sign_type".equals(name) || valueStr == null || "".equals(valueStr))
//                continue;
//
//            newParams.put(name, valueStr);
//            log.info("doSinaNotify --> " + name + " = [" + valueStr + "]");
//        }
//
//        List<String> pairs = new ArrayList<String>();
//        for (String key : newParams.keySet())
//            pairs.add(key + "=" + newParams.get(key));
//        String sign_data = StringUtils.join(pairs.toArray(), "&");
//        String sign = request.getParameter("sign").toString();
//
//        // 获取商户参数
//        String trade_status = request.getParameter("status").toString();
//        String out_trade_no = request.getParameter("out_pay_id").toString();
//        String trade_no = request.getParameter("pay_id").toString();
//        log.info("doSinaNotify --> out_trade_no = [" + out_trade_no + "]");
//
//        // 计算得出通知验证结果
//        boolean verify_result = SinaPayConfig.checkRSA(sign_data, sign, SinaPayConfig.WEIBO_RSA_PUBLIC_KEY);
//        log.info("doSinaNotify --> verify_result=" + verify_result + " out_trade_no = [" + out_trade_no + "]");
//        HttpServletResponse response = ServletActionContext.getResponse();
//        if (verify_result) { // 验证成功
//            log.info("doSinaNotify --> trade_status=" + trade_status + " out_trade_no = [" + out_trade_no + "]");
//            if (SinaPayConfig.PAY_STATUS_SUCCESS.equals(trade_status)) { // 交易成功
//                PayRecord p = orderRecordBean.findPayRecordById(orderRecordBean.findPayRecordByInnerTradeNo(out_trade_no).getPayRecordId());
//                if (p != null && p.getPayStatus() != 1) {
//                    log.info("doSinaNotify --> processing... pay_record_id = [" + p.getPayRecordId() + "] out_trade_no = [" + out_trade_no + "]");
//                    admin = userBean.findUserById(p.getUserId());
//                    String rtnStr = orderRecordBean.doPayEndV3(out_trade_no, admin, trade_no);
//                    log.info("doSinaNotify --> success out_trade_no = [" + out_trade_no + "] rtnStr = [" + rtnStr + "]");
//
//                    if (!StringUtils.isBlank(rtnStr)) {
//                        JSONObject jsonObject = JSONObject.parseObject(rtnStr);
//                        if (Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(jsonObject.get("code"))) {
//                            // 通知微博成功
//                            log.info("doSinaNotify --> print success out_trade_no = [" + out_trade_no + "]");
//                            response.getWriter().println("success");
//                        }
//                    }
//
//                    // 设置订单支付状态，页面会定时刷新取该状态
//                    UserAction.myOrderStatus.put("order_" + admin.getUsername(), "1");
//                } else {
//                    if (p == null)
//                        log.info("doSinaNotify --> payRecord is null! out_trade_no = [" + out_trade_no + "]");
//                    else
//                        log.info("doSinaNotify --> pay_record_id = [" + p.getPayRecordId() + "] payStatus = [" + p.getPayStatus() + "]  out_trade_no = [" + out_trade_no + "]");
//                }
//            } else {
//                orderRecordBean.doPayRecordFail(out_trade_no, "trade_status=" + trade_status);
////				saveUserOperateLog(0, Constants.OPERATE_LOG_TYPE_PAY, "微博NOTIFY：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + trade_status + "]");
//                log.info("doSinaNotify : 微博NOTIFY：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + trade_status + "]");
//            }
//        } else {
//            orderRecordBean.doPayRecordFail(out_trade_no, "verify_result=" + verify_result);
//            log.info("do notify fail");
////			saveUserOperateLog(0, Constants.OPERATE_LOG_TYPE_PAY, "微博NOTIFY：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + verify_result + "]");
//            log.info("doSinaNotify : 微博NOTIFY：innerTradeNo = [" + out_trade_no + "] payResult = [FAILED] reason = [" + verify_result + "]");
//        }
//
//        log.info("do notify end");
//    }
//
//    /**
//     * 新浪profile授权
//     *
//     * @throws Exception
//     */
//    public void sinaProfileAuth() throws Exception {
//        fetchProductlist(Constants.PACKAGE_TYPE_FREE);
//        log.info("sinaProfileAuth --> start");
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        String uid = request.getParameter("uid");
//        int a = 1;
//        if (a == 1)
//            uid = null;
//        if (StringUtils.isBlank(uid)) {
//            log.info("sinaProfileAuth --> uid is null");
//            ProfileRes profileRes = new ProfileRes();
//            profileRes.setError_code("1001");
//            profileRes.setError_msg("uid is null");
//            CommonUtils.writeJSON(profileRes);
//        } else {
//            uid = uid.trim();
//            log.info("sinaProfileAuth --> uid is " + uid);
//            // 判断授权信息是否存在
//            UserThirdpartyAuthInfo userThirdpartyAuthInfo = new UserThirdpartyAuthInfo();
//            userThirdpartyAuthInfo.setThirdpartyUserId(uid);
//            userThirdpartyAuthInfo.setPlatformType(Constants.THIRDPARTY_TYPE_SINA);
//
//			/*UserThirdpartyAuthInfo tempInfo = userBean.findUserByThirdparty(userThirdpartyAuthInfo);
//			User loginUser = null;
//			if (tempInfo != null) {
//				log.info("sinaProfileAuth --> auth info exists");
//				loginUser = userBean.findUserById(tempInfo.getUserId());
//				if (loginUser == null) { // 用户失效，重新绑定
//					log.info("sinaProfileAuth --> auth user not exists");
//					userBean.doDelThirdpartyById(userThirdpartyAuthInfo);
//					tempInfo = null;
//				}
//			} else {
//				log.info("sinaProfileAuth --> auth info not exists");
//			}
//
//			if (tempInfo == null) {
//				log.info("sinaProfileAuth --> create new user");
//				// 保存用户信息
//				user = new User();
//				user.setUsername(userThirdpartyAuthInfo.getThirdpartyUserId());
//				user.setNickname(userThirdpartyAuthInfo.getThirdpartyUserId());
//				user.setUserHead(systemConfig.getFileviewPath() + "user_head/default.jpg");
//				user.setThirdpartyType(1);
//				user.setThirdpartyAccount(userThirdpartyAuthInfo.getThirdpartyUserId());
//				user.setAppUserStatus(0);
//				user.setUserChannel(Constants.USER_CHANNEL_SINA);
//				User regUser = userBean.doRegisterV2(request, response, user);
//				userBean.doRegisterAddPackage(regUser, packageListFree.get(0));
//				admin = authenticationBean.getAdmin(user.getUserId());
//
//				// 保存授权信息
//				userThirdpartyAuthInfo.setUserId(admin.getUserId());
//				userThirdpartyAuthInfo.setApplicationType(1);
//				userThirdpartyAuthInfo.setStatus(Constants.STATUS_VALID);
//				userThirdpartyAuthInfo.setAuthTime(new Date());
//				userBean.doBindThirdpartyAccount(userThirdpartyAuthInfo);
//
//				loginUser = admin;
//			}
//
//			if (loginUser == null) {
//				log.info("sinaProfileAuth --> get user error");
//				ProfileRes profileRes = new ProfileRes();
//				profileRes.setError_code("1002");
//				profileRes.setError_msg("get user error");
//				writeJSON(JSONObject.toJSONString(profileRes));
//			} else {
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("username", username);
//				params.put("password", MD5Util.md5(password));
//				String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userCreateThirdpartyURL"), params);
//				log.info("sinaProfileAuth --> user login");
//				HttpServletRequest req = ServletActionContext.getRequest();
//				HttpServletResponse rep = ServletActionContext.getResponse();
//				//RequestInterFace.setUserForMemcachedWyq(req, rep, loginUser.getUserId() + loginUser.getUsername(), loginUser.getUserId(), SystemConstants.SYS_ADMIN_CACHE_TIME, SysConfig.memoCacheName);
//
//				loginUser.setLastLoginTime(new Date());
//				userCenterBean.doModifyUserLastLoginTime(loginUser);
//
//				// 记录登录日志
//				LoginLog loginLog = new LoginLog();
//				loginLog.setLoginUserId(loginUser.getUserId());
//				loginLog.setLoginTime(new Date());
//				loginLog.setLoginType(Constants.LOGIN_TYPE_SINA);
//				loginLog.setLoginPlatform(Constants.PLATFORM_TYPE_WYQ);
//				loginLog.setLoginIp(CommonUtils.getIp(req));
//				userCenterBean.doSaveLoginLog(loginLog);
//
////				saveUserOperateLog(loginUser.getUserId(), Constants.OPERATE_LOG_TYPE_SYS_LOGIN, "微博profile第三方登录");
//				CommonUtils.opreateLog(request, loginUser, Constants.OPERATE_LOG_PRODUCT_SECTION_SYS_LOGIN, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);
//
//				log.info("sinaProfileAuth --> user login success");
//				ProfileRes profileRes = new ProfileRes();
//				profileRes.setError_code("1000");
//				profileRes.setError_msg("success");
//				Map<String, Object> data = new HashMap<String, Object>();
//				data.put("type", "static");
//				profileRes.setData(data);
//				writeJSON(JSONObject.toJSONString(profileRes));
//			}*/
//
//            extraInfo = CommonUtils.getWeiboUserInfo(uid);
//
//            String nickname = uid;
//            String userhead = systemConfig.getFileviewPath() + "user_head/default.jpg";
//            String verifiedType = "-1";
//            if (extraInfo != null && !"".equals(extraInfo)) {
//                WeiboUserInfo weiboUserInfo = JSONObject.parseObject(extraInfo, WeiboUserInfo.class);
//                if (weiboUserInfo != null) {
//                    nickname = weiboUserInfo.getScreen_name();
//                    userhead = weiboUserInfo.getAvatar_large();
//                    verifiedType = weiboUserInfo.getVerified_type();
//                }
//            }
//
//            UserCreateThirdpartyVO userCreateThirdpartyVO = new UserCreateThirdpartyVO();
//            userCreateThirdpartyVO.setUid(uid);
//            userCreateThirdpartyVO.setPlatformType(Constants.THIRDPARTY_TYPE_SINA);
//            userCreateThirdpartyVO.setAppUserStatus(0);
//            userCreateThirdpartyVO.setUserChannel(Constants.USER_CHANNEL_WB_INNER);
//            userCreateThirdpartyVO.setGiftPackageId(CollectionUtils.isEmpty(packageListFree) ? 1 : packageListFree.get(0).getProductPackageId());
//            userCreateThirdpartyVO.setNickname(nickname);
//            userCreateThirdpartyVO.setUserHead(userhead);
//            userCreateThirdpartyVO.setIsLogin(true);
//            userCreateThirdpartyVO.setVerifiedType(verifiedType);
//            userCreateThirdpartyVO.setExtraInfo(extraInfo);
//
//            Map<String, Object> intraParams = new HashMap<String, Object>();
//            intraParams.put("userParam", JSONObject.toJSONString(userCreateThirdpartyVO));
//
//            String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userCreateThirdpartyURL"), intraParams);
//            log.info("spa --> rtnStr = [" + rtnStr + "]");
//
//            if (StringUtils.isBlank(rtnStr)){
//                log.info("sinaProfileAuth --> get user error");
//                ProfileRes profileRes = new ProfileRes();
//                profileRes.setError_code("1002");
//                profileRes.setError_msg("get user error");
//                CommonUtils.writeJSON(profileRes);
//                return;
//            }
//
//            UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//            if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())){
//                log.info("sinaProfileAuth --> get user error");
//                ProfileRes profileRes = new ProfileRes();
//                profileRes.setError_code("1002");
//                profileRes.setError_msg("get user error");
//                CommonUtils.writeJSON(profileRes);
//                return;
//            }
//
//            String loginRes = LoginUtils.doLogin(request, response, userRes, true);
//            if(!"0000".equals(loginRes)){
//                ProfileRes profileRes = new ProfileRes();
//                profileRes.setError_code("1002");
//                profileRes.setError_msg("get user error");
//                CommonUtils.writeJSON(profileRes);
//                return;
//            }
//
//            log.info("sinaProfileAuth --> user login success");
//            ProfileRes profileRes = new ProfileRes();
//            profileRes.setError_code("1000");
//            profileRes.setError_msg("success");
//            Map<String, Object> data = new HashMap<String, Object>();
//            data.put("type", "static");
//            profileRes.setData(data);
//            CommonUtils.writeJSON(profileRes);
//        }
//    }
//
//    /**
//     * 外部跳转登录
//     *
//     * @throws Exception
//     */
//    public void outIntoLogin() throws Exception {
//
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        Integer loginRes = 0;
//        if(StringUtils.isNotBlank(sid)){
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("sid", sid);
//
//            String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userSessionURL"), params);
//            if (StringUtils.isNotBlank(rtnStr)) {
//                UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//                if (userRes != null && "0000".equals(userRes.getCode())) {
//                    loginRes = 1;
//                    LoginUtils.setSidToCookie(request, response, sid);
//                }
//            }
//        }
//        CommonUtils.writeJSON(loginRes);
//    }
//
//    /**
//     * 微博内嵌登录
//     *
//     * @throws Exception
//     */
//    public void spaLogin() throws Exception {
//        log.info("spaLogin --> start");
//
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//
//        response.setHeader("Access-Control-Allow-Origin","*");	//允许跨域请求
//        doSpaLogin(request, response, true, Constants.USER_CHANNEL_WB_INNER);
//    }
//
//    /**
//     * 微博内嵌登录操作
//     *
//     * @throws Exception
//     */
//    public String doSpaLogin(HttpServletRequest request, HttpServletResponse response, boolean isLogin, int channel) throws Exception{
//
//        fetchProductlist(Constants.PACKAGE_TYPE_FREE);
//
//        String referer = request.getHeader("referer");
//        //String referer = "https://weibo.com/u/3559757710/home?wvr=5";
//        if ("www".equals(SysConfig.memoCacheName) && (StringUtils.isBlank(referer) || !referer.trim().toLowerCase().contains("weibo"))) // 非微博请求
//            return LOGIN;
//
//        String secretKey = "[fe&^3??fe#%&97OPk21";
//        String uid = request.getParameter("uid");
//        String time = request.getParameter("time");
//        String sign = request.getParameter("sign");
//
//        log.info("doSpaLogin --> uid = [" + uid + "] time = [" + time + "] sign = [" + sign + "]");
//        if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(time) && StringUtils.isNotBlank(sign)) {
//            String signURL = new StringBuilder("https://wyq.sina.com/api/spa.shtml?").append("uid=").append(uid).append(secretKey).append(time).toString();
//            String signMD5 = MD5Util.md5(signURL);
//            log.info("doSpaLogin --> signURL = [" + signURL + "] signMD5 = [" + signMD5 + "]");
//            if (!sign.equalsIgnoreCase(signMD5)) {
//                signURL = new StringBuilder("uid=").append(uid).append(secretKey).append(time).toString();
//                signMD5 = MD5Util.md5(signURL);
//                log.info("doSpaLogin --> signURL = [" + signURL + "] signMD5 = [" + signMD5 + "]");
//                if (!sign.equalsIgnoreCase(signMD5)) {
//                    log.info("spaLogin --> sign do not matched! sign = [" + sign + "] signMD5 = [" + signMD5 + "]");
//                    return LOGIN;
//                }
//            }
//        } else {
//            log.info("doSpaLogin --> direct pass parameters is null, get uid from referer");
//            referer = referer.trim();
//            log.info("doSpaLogin --> referer is " + referer);
//            Pattern pattern = Pattern.compile("(\\d+)");
//            Matcher matcher = pattern.matcher(referer);
//            if (matcher.find())
//                uid = matcher.group();
//
//            if (StringUtils.isNotBlank(uid)) {
//                uid = uid.trim();
//                if (referer.indexOf("/p/") != -1 && ("100106".equals(uid.substring(0, 6)) || "100206".equals(uid.substring(0, 6)) || "100606".equals(uid.substring(0, 6)))) {
//                    uid = uid.substring(6);
//                } else {
//                    log.info("doSpaLogin --> unknow uid");
//                    uid = null;
//                }
//            }
//        }
//
//        if (uid == null || "".equals(uid.trim()) || uid.trim().length() < 5) {
//            log.info("doSpaLogin --> uid is null or length lt 5 [" + uid + "]");
//            return LOGIN;
//        } else {
//            uid = uid.trim();
//            log.info("doSpaLogin --> uid is " + uid);
//
//            // 获取用户信息
////			String weiboApi = Constants.WEIBO_API_URI + "uri=" + new BASE64Encoder().encode(Constants.WEIBO_API_USER_INFO_URI.getBytes()) + "&params=" + new BASE64Encoder().encode(("uid=" + uid).getBytes());
////			log.info("spa --> get weibo user info! uri = [" + weiboApi + "] uid = [" + uid + "]");
////			try {
////				extraInfo = CommonUtils.sendGet(weiboApi, null);
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
//            extraInfo = CommonUtils.getWeiboUserInfo(uid);
//
//            String nickname = uid;
//            String userhead = systemConfig.getFileviewPath() + "user_head/default.jpg";
//            String verifiedType = "-1";
//            if (extraInfo != null && !"".equals(extraInfo)) {
//                WeiboUserInfo weiboUserInfo = JSONObject.parseObject(extraInfo, WeiboUserInfo.class);
//                if (weiboUserInfo != null) {
//                    nickname = weiboUserInfo.getScreen_name();
//                    userhead = weiboUserInfo.getAvatar_large();
//                    verifiedType = weiboUserInfo.getVerified_type();
//                }
//            }
//            //判断蓝V用户
//            if(StringUtils.isNotBlank(verifiedType) && Params.VERIFIED_BLUE_V.contains(verifiedType)){
//                blueVUser = true;
//            }else{
//                blueVUser = false;
//            }
//
//            try {
//                fetchProductlist(Constants.PACKAGE_TYPE_FREE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            UserCreateThirdpartyVO userCreateThirdpartyVO = new UserCreateThirdpartyVO();
//            userCreateThirdpartyVO.setUid(uid);
//            userCreateThirdpartyVO.setPlatformType(Constants.THIRDPARTY_TYPE_SINA);
//            userCreateThirdpartyVO.setAppUserStatus(0);
//            userCreateThirdpartyVO.setUserChannel(channel);
//            userCreateThirdpartyVO.setGiftPackageId(CollectionUtils.isEmpty(packageListFree) ? 1 : packageListFree.get(0).getProductPackageId());
//            userCreateThirdpartyVO.setNickname(nickname);
//            userCreateThirdpartyVO.setUserHead(userhead);
//            userCreateThirdpartyVO.setIsLogin(isLogin);
//            userCreateThirdpartyVO.setVerifiedType(verifiedType);
//            userCreateThirdpartyVO.setExtraInfo(extraInfo);
//
//            Map<String, Object> intraParams = new HashMap<String, Object>();
//            intraParams.put("userParam", JSONObject.toJSONString(userCreateThirdpartyVO));
//
//            String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userCreateThirdpartyURL"), intraParams);
//            log.info("doSpaLogin --> rtnStr = [" + rtnStr + "]");
//
//            if (StringUtils.isBlank(rtnStr))
//                return ERROR;
//
//            UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
//            if (userRes == null || !Constants.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(userRes.getCode())){
//                return ERROR;
//            }else{
//                sid = userRes.getSid();
//            }
//
//            String loginRes = LoginUtils.doLogin(request, response, userRes, false);
//            if(!"0000".equals(loginRes)){
//                return ERROR;
//            }else{
//                admin = new User();
//                admin.setUserId(userRes.getUserInfo().getUserId());
//                return "0000";
//            }
//        }
//    }
//
//    /**
//     * 新浪profile授权(微博内嵌)
//     *
//     * @throws Exception
//     */
//    public String spa() throws Exception {
//        log.info("spa --> start");
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许跨域请求
//        String loginRes = doSpaLogin(request, response, true, Constants.USER_CHANNEL_WB_INNER);
//        if (!"0000".equals(loginRes)) {
//            return loginRes;
//        }
//        if (admin == null) {
//            log.info("spa --> get user error");
//            return LOGIN;
//        } else {
//            log.info("spa --> user login success! userId = [" + admin.getUserId() + "]");
//            showFlag = 2; // 热点事件
//            String url = SysConfig.API_MICROSERVICE_RANKLIST_URL + "/v3/hotIncident/web/list";
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("page", 1);
//            params.put("pageSize", 10);
//            params.put("sort", 2);
//            params.put("showTag", 1);
//            params.put("labelShowTag", 0);
//            params.put("labels", "1001,1002");
//            Date now = new Date();
//            params.put("startTime", DateUtils.format(DateUtils.addDay(now, -3)));
//            params.put("endTime", DateUtils.getNow());
//            String result = HttpRequestUtils.sendGet(url, params);
//            if (StringUtils.isNoneBlank(result)) {
//                PageDto<HotIncident> pageDto = JSON.parseObject(result, new TypeReference<PageDto<HotIncident>>() {});
//                if (pageDto != null && CollectionUtils.isNotEmpty(pageDto.getList())) {
//                    keyword = pageDto.getList().get(0).getKeyword();
//                    filterKeyword = pageDto.getList().get(0).getFilterKeyword();
//                    title = pageDto.getList().get(0).getIncidentTitle();
//                    // 检测模块敏感词 不同模块需要使用不同的，敏感词判断 ，
//                    List<String> sensitiveWordList = GetCommon.analyzerSensitiveWordsHot(keyword + " " + filterKeyword);
//                    if (null != sensitiveWordList) {
//                        // 标准的敏感词所有的模块都需要验证
//                        sensitiveWordList.addAll((GetCommon.analyzerSensitiveWords(keyword + " " + filterKeyword)));
//                        if (sensitiveWordList != null && sensitiveWordList.size() > 0) {
//                            code = "1111";
//                            message = "对不起，您的关键词包含敏感词语，请检查后再试！";
//                        } else {
//                            code = "0000";
//                        }
//                    } else {
//                        code = "3333";
//                        message = "请联系客服或者稍候再试！";
//                    }
//                }
//            }
//            List<KeyWord> allKeywordList = keywordBean.getKwListByUserId(admin, 0);
//            if (allKeywordList != null && allKeywordList.size() > 0) {
//                for (KeyWord k : allKeywordList) {
//                    if (k.getStatus() == Constants.STATUS_INVALID) {
//                        emptyKeywordId = k.getKeywordId();
//                        break;
//                    }
//                }
//            }
//            return "spa";
//        }
//    }
//
//    /**
//     * 快速登录
//     *
//     * @return
//     * @throws Exception
//     */
//    public String quickLogin() throws Exception {
//        fetchSessionAdmin();
//
////		saveUserOperateLog(admin.getUserId(), Constants.OPERATE_LOG_TYPE_SYS_LOGIN, "快速登录");
//        CommonUtils.opreateLog(ServletActionContext.getRequest(), admin, Constants.OPERATE_LOG_PRODUCT_SECTION_SYS_LOGIN, Constants.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);
//
//        return "quickLogin";
//    }
//
//    public String initHot() throws Exception {
//        fetchProductlist(Constants.PACKAGE_TYPE_FREE);
//
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//
//        response.setHeader("Access-Control-Allow-Origin","*");	//允许跨域请求
//
//        String loginRes = doSpaLogin(request, response, true, Constants.USER_CHANNEL_DATA_ASSISTANT);
//        if(!CodeConstants.SUCCESS_CODE.equals(loginRes)){
//            return loginRes;
//        }
//
//        if (admin == null) {
//            log.info("spa --> get user error");
//            return LOGIN;
//        } else {
//            log.info("spa --> user login success! userId = [" + admin.getUserId() + "]");
//
//            showFlag = 2; // 热点事件
//            String url = SysConfig.API_MICROSERVICE_RANKLIST_URL + "/v3/hotIncident/web/list";
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("page", 1);
//            params.put("pageSize", 10);
//            params.put("sort", 2);
//            params.put("showTag", 1);
//            params.put("labelShowTag", 0);
//            params.put("labels", "1001,1002");
//            Date now = new Date();
//            params.put("startTime", DateUtils.format(DateUtils.addDay(now, -3)));
//            params.put("endTime", DateUtils.getNow());
//            String result = HttpRequestUtils.sendGet(url, params);
//            if (StringUtils.isNoneBlank(result)) {
//                PageDto<HotIncident> pageDto = JSON.parseObject(result, new TypeReference<PageDto<HotIncident>>() {});
//                if (pageDto != null && CollectionUtils.isNotEmpty(pageDto.getList())) {
//                    keyword = pageDto.getList().get(0).getKeyword();
//                    filterKeyword = pageDto.getList().get(0).getFilterKeyword();
//                    title = pageDto.getList().get(0).getIncidentTitle();
//                    // 检测模块敏感词 不同模块需要使用不同的，敏感词判断 ，
//                    List<String> sensitiveWordList = GetCommon.analyzerSensitiveWordsHot(keyword + " " + filterKeyword);
//                    if (null != sensitiveWordList) {
//                        // 标准的敏感词所有的模块都需要验证
//                        sensitiveWordList.addAll((GetCommon.analyzerSensitiveWords(keyword + " " + filterKeyword)));
//                        if (sensitiveWordList != null && sensitiveWordList.size() > 0) {
//                            code = "1111";
//                            message = "对不起，您的关键词包含敏感词语，请检查后再试！";
//                        } else {
//                            code = CodeConstants.SUCCESS_CODE;
//                        }
//                    } else {
//                        code = "3333";
//                        message = "请联系客服或者稍候再试！";
//                    }
//                }
//            }
//
//            List<KeyWord> allKeywordList = keywordBean.getKwListByUserId(admin, 0);
//            if (allKeywordList != null && allKeywordList.size() > 0) {
//                for (KeyWord k : allKeywordList) {
//                    if (k.getStatus() == Constants.STATUS_INVALID) {
//                        emptyKeywordId = k.getKeywordId();
//                        break;
//                    }
//                }
//            }
//
//            return "initHot";
//        }
//    }
//
//    public String initSplitWords() throws Exception {
//        fetchProductlist(Constants.PACKAGE_TYPE_FREE);
//
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        response.setHeader("Access-Control-Allow-Origin","*");	//允许跨域请求
//
//        String loginRes = doSpaLogin(request, response, true, Constants.USER_CHANNEL_DATA_ASSISTANT);
//        if(!CodeConstants.SUCCESS_CODE.equals(loginRes)){
//            return loginRes;
//        }
//
//        return "initSplitWords";
//    }
//
//    public String hotCheck(String keyword) {
//        fetchSessionAdmin();
//        String nullStr = null;
//        try {
//            if (keyword == null || "".equals(keyword)) {
//                return nullStr;
//            }
//            String url = SysConfig.API_ALL_URL + CommonAPIConfig.getValue("HotCheck");
//            String params = "platform=2&channel=2&keyword=" + URLEncoder.encode(keyword, "utf-8");
//            if (admin != null) {
//                params += "&userTag=" + admin.getUserId();
//            } else {
//                HttpServletRequest request = ServletActionContext.getRequest();
//                params += "&userTag=" + CommonUtils.getIp(request);
//            }
//            params += "&date=24";
//            String result = HttpRequestUtils.sendGet(url, params);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return nullStr;
//    }
//
//    private String passwordVCode;
//
//    public String getPasswordVCode() {
//        return passwordVCode;
//    }
//
//    public void setPasswordVCode(String passwordVCode) {
//        this.passwordVCode = passwordVCode;
//    }
//
//    public String readPage() {
//        apkHref = systemConfig.getApkHref();
//        iosHref = systemConfig.getIosHref();
//        qrCodeImg = systemConfig.getQrCodeImg();
//        currentPageCode = 10;
//        fetchSessionAdmin();
//        return "readPage";
//    }
//
//    /**
//     * 记录用户浏览行为日志
//     *
//     * @return
//     * @throws Exception
//     */
//    public void operatePageLog() throws Exception {
//        fetchSessionAdmin();
//        HttpServletRequest request = ServletActionContext.getRequest();
//
//        if (pageCode != null && pageCode > 0 && pageName != null && !"".equals(pageName)) {
//            if((pageCode == 1005 && pageName.equals("详细内容页"))
//                    || (pageCode == 1007 && pageName.equals("相似文章列表页"))){
//                AbnormalAnalysisUtil.checkAbnormalRequest(AbnormalAnalysisUtil.ABLE_TYPE_OPERATE_LOG, pageCode, pageName, null);
//            }
//            CommonUtils.operatePageLog(request, admin, pageCode, pageName, extraInfo);
//        }
//    }
//
//    /**
//     * 转到邀请页面
//     *
//     * @return
//     * @throws Exception
//     */
//    public String toInvitation() throws Exception {
//        if ((userExclusiveChannelResElement != null && StringUtils.isNoneBlank(userExclusiveChannelResElement.getUecCode())) || StringUtils.isNotBlank(cid)) {
//            String code = "";
//            if (userExclusiveChannelResElement != null && StringUtils.isNoneBlank(userExclusiveChannelResElement.getUecCode())) {
//                code = userExclusiveChannelResElement.getUecCode();
//            } else {
//                code = cid;
//            }
//            userExclusiveChannel = userCenterBean.queryUserExclusiveChannelByCode(code);
//
//            if (userExclusiveChannel != null)
//                return "toInvitation";
//        }
//        return ERROR;
//    }
//
//    /**
//     * 评论分析菜单是否显示
//     *
//     * @throws Exception
//     */
//    public void commentsAnalsysMenuShow() throws Exception {
//        boolean show = false;
//        fetchSessionAdmin();
//        if (admin != null) {
//            if (admin.getUserType() != Constants.USER_TYPE_BASE || Arrays.asList(SysConfig.COMMENTS_ANALYSIS_WRITE_USER_LIST.split(",")).contains(String.valueOf(admin.getUserId())))
//                show = true;
//        }
//        CommonUtils.writeJSON(show);
//    }
//    /**
//     * 新用户领取监测方案
//     *
//     * @throws Exception
//     */
//    public void getKeywordProject() throws Exception {
//        fetchSessionAdmin();
//        Map<Object,Object> map = new HashMap<Object,Object>();
//        if(admin != null){
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
//            String rtnStr = CommonUtils.sendIntraBusinessAPIPOST(ServletActionContext.getRequest(), IntraBusinessAPIConfig.getValue("giftPackages"), params);
//            log.info(rtnStr);
//            if (StringUtils.isNotBlank(rtnStr)) {
//                BaseView view = JSONObject.parseObject(rtnStr, BaseView.class);
//                map.put("code", view.getCode());
//                map.put("message", view.getMessage());
//            }else{
//                map.put("code", "3333");
//                map.put("message", "系统繁忙，请联系客服或稍后再试！");
//            }
//        }
//        CommonUtils.writeJSONString(JSONObject.toJSONString(map));
//    }
//
    /**
     * 发送验证短信
     *
     * @throws Exception
     */
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "sendVcodeSMS")
    @ResponseBody
    public BaseDto sendVcodeSMS(String mobile) throws Exception {
        BaseDto<Object> baseDto = new BaseDto<>();
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("succ", false);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
//        HttpServletRequest request = ServletActionContext.getRequest();
        String key = RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_JSESSIONID).append("sendVcodeSMS_").append(request.getSession().getId()).toString());
        String jsCountStr = RedisUtils.getAttribute(key);
        Integer jsCount = 0;//请求次数
        if(StringUtils.isNotBlank(jsCountStr)) {
            jsCount = Integer.valueOf(jsCountStr);
        }

        if(jsCount >= 3) {
//            rtnMap.put("msg", "请求太频繁，稍后再试！");
//            CommonUtils.writeJSON(rtnMap);
            return baseDto.setMessage("请求太频繁，稍后再试！");
        }

        RedisUtils.setAttribute(RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_JSESSIONID).append("sendVcodeSMS_").append(request.getSession().getId()).toString()), String.valueOf(++jsCount), 10 * 60);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", "2");
        params.put("mobile", mobile);
        params.put("platform", BusinessConstant.PLATFORM_Web);
        String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(servletRequestAttributes.getRequest(), IntraBusinessAPIConfig.getValue("sendSMSURL"), params);
        if (StringUtils.isNotBlank(rtnStr)) {
            BaseRes baseRes = JSONObject.parseObject(rtnStr, BaseRes.class);
            if (baseRes != null && CodeConstant.SUCCESS_CODE.equals(baseRes.getCode())) {
//                rtnMap.put("succ", true);
                baseDto.setCode(CodeConstant.SUCCESS_CODE);
            } else {
                baseDto.setMessage(baseRes.getMessage()).setCode(baseRes.getCode());
//                rtnMap.put("msg", baseRes.getMessage());
            }
        }

//        CommonUtils.writeJSON(rtnMap);
        return baseDto;
    }

    /**
     * 验证短信
     *
     * @throws Exception
     */
    public BaseDto checkSMS(String  mobile,String authcode) throws Exception {
        BaseDto baseDto = new BaseDto();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("authcode", authcode);
        params.put("platform", BusinessConstant.PLATFORM_Web);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String rtnStr = BusinessReuqestUtils.sendWrdIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("checkSMSURL"), params);
        if (StringUtils.isNotBlank(rtnStr)) {
            JSONObject jsonObject = JSONObject.parseObject(rtnStr);
            if (jsonObject != null && BusinessConstant.SINGLE_WEIBO_ANALYSIS_RETURN_CODE_SUCCESS.equals(jsonObject.getString("code"))) {
                boolean succ = jsonObject.getBooleanValue("succ");
                if (succ)
                    RedisUtils.setAttribute(RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_SMS_CHECK_SUCCESS).append(mobile).toString()), "1", 10 * 60);
//                CommonUtils.writeJSON(succ);
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(succ);
            }
        }
        return baseDto;
    }
//
//    /**
//     * 实名认证
//     *
//     * @throws Exception
//     */
//    public void realName() throws Exception {
//        if(admin != null && admin.getUserId() != null && StringUtils.isNotBlank(admin.getMobile()) && StringUtils.isNotBlank(admin.getPassword())){
//            String realNameSmsCheckSucc = JedisUtil.getAttribute(Constants.generateJedisKey(new StringBuilder(Constants.JEDIS_KEYS_SMS_CHECK_SUCCESS).append(admin.getMobile()).toString()));
//            if (StringUtils.isNotBlank(realNameSmsCheckSucc)) {
//                Map<String, Object> params = new HashMap<String, Object>();
//                params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
//                params.put("mobile", admin.getMobile());
//                params.put("password", MD5Util.md5(MD5Util.md5(admin.getPassword())));
//                CommonUtils.sendIntraBusinessAPIPOST(ServletActionContext.getRequest(), IntraBusinessAPIConfig.getValue("realNameURL"), params);
//            }
//        }
//    }
//}




}
