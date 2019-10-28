package com.miduchina.wrd.eventanalysis.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.ModelDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.dto.user.SignInRewardWdNumDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.dto.user.UserRightsRechargeRecord;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Constants;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.log.model.UserRes;
import com.miduchina.wrd.eventanalysis.service.OrderClientService;
import com.miduchina.wrd.eventanalysis.service.UserCenterService;
import com.miduchina.wrd.eventanalysis.utils.LoginUtils;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.eventanalysis.BaseView;
import com.miduchina.wrd.po.user.User;
import com.miduchina.wrd.po.user.UserRegRewardRecord;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.MD5Utils;
import com.tuke.gospel.core.util.SimpleUtils;
import com.xd.tools.common.ErrorCodeConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/user")
@Controller
public class UserController extends BaseController{

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private APIInfoClient apiInfoClient;

    @Autowired
    private OrderClientService orderClientService;

    @RequestMapping(value = "/login")
    public String userLogin(String urlType,ModelAndView modelAndView,HttpServletRequest req,HttpServletResponse rep){
        System.out.println("----"+req.getHeader("Referer"));
        if(org.apache.commons.lang3.StringUtils.isBlank(urlType)){
            urlType = req.getHeader("Referer");
        }else{
            urlType = URLDecoder.decode(urlType);
        }
        System.out.println("MemoCacheName:"+ SysConfig.cfgMap.get("WEBID_COOKIE_NAME"));
        if (SysConfig.cfgMap.get("WEBID_COOKIE_NAME").equals("www")) {
            try {
                rep.sendRedirect("http://d.51wyq.cn");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        modelAndView.setViewName("index_local");
        modelAndView.addObject("urlType",urlType);
        return "index_local";
    }

    @RequestMapping(value = "/toSign")
    @ResponseBody
    public ModelDto toSign(HttpServletRequest request, HttpServletResponse response){
        admin = fetchSessionAdmin(request);
        if (admin != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("userEncode",CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
            String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,"doSignIn", params);
            SignInRewardWdNumDto dto = JSON.parseObject(jsonStr,SignInRewardWdNumDto.class);
            if(dto!=null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(dto.getCode())){
                if (StringUtils.isNoneBlank(dto.getLastSignInDate())){
                    admin.setLastSignInDate(DateUtils.parse(dto.getLastSignInDate(),DateUtils.FORMAT_SHORT_TIME));
                }
                return  new ModelDto(1,dto.getTodaySignInRewardWdNum());
            }else if(dto!=null && dto.getCode().equals("30028")){
                return  new ModelDto(2,dto.getMessage());
            }else{
                return  new ModelDto(0);
            }
        }else {
            return  new ModelDto(0);
        }
    }
    /**
     * ajax登陆处理
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/indexLogin")
    @ResponseBody
    public ModelAndView indexLogin(ModelAndView modelAndView,String urlType,String username,String password,HttpServletRequest request,HttpServletResponse response) throws Exception {
        String msg="";
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            msg = "请输入您的账号和密码！";
            modelAndView.addObject("msg",msg);
            modelAndView.setViewName("index_local");
            return modelAndView;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", MD5Utils.md5(password));

        String rtnStr = Utils.sendWrdIntraBusinessAPIPOST(request, "userLoginMobile", params);
        log.info("IdentificationAction.indexLogin : rtnStr = [" + rtnStr + "]");

        if (StringUtils.isBlank(rtnStr)) {
            msg = "登录失败，请稍候再试！";
            modelAndView.addObject("msg",msg);
            modelAndView.setViewName("index_local");
            return modelAndView;
        }

        UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
        if (userRes == null || !CodeConstant.SUCCESS_CODE.equals(userRes.getCode())) {
            msg = userRes.getMessage();
            modelAndView.addObject("msg",msg);
            modelAndView.setViewName("index_local");
            return modelAndView;
        }
        LoginUtils.saveLoginFlag(request, response, userRes);

        //判断登陆后 跳转回哪一个页面
        String redirecturl = request.getParameter("urlType");

        if(StringUtils.isNotBlank(urlType) && !redirecturl.contains("register") && !redirecturl.contains("forgetPassword")){
            modelAndView.addObject("urlType",urlType);
            modelAndView.setViewName("redirect:/"+urlType);
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    /**
     * 获取h5首页地址
     * @param str
     * @return
     */
    private String getH5DNS(String str){
        str = "http://apps.weibo.com/3960037780/8rXM111J";
        String helpUrl = "http://m.wyq.cn/contectUs.action";
        return str;
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping("/forgetPassword")
    public String forgetPpassword(){
        return "retrievePassword";
    }


    /**
     * 短信登陆
     * @param username
     * @param authcode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkLoginVcode")
    @ResponseBody
    public String  checkLoginVcode(HttpServletRequest request,HttpServletResponse response,String username,String authcode) throws Exception{

        BaseView bv = new BaseView();
        if (StringUtils.isNotBlank(username)) {
            Map<String,Object> p = new HashMap<>();
            p.put("mobile", username);
            p.put("authcode", authcode);
            String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request, "checkSMSURL", p);
            if(StringUtils.isNotBlank(jsonStr)) {
                bv = JSON.parseObject(jsonStr, BaseView.class);
            }else {
                bv.setCode(CodeConstant.FAILURE_CODE);
                bv.setMessage("登陆失败，请稍后尝试！");
            }
            if(bv != null && bv.getCode().equals(CodeConstant.SUCCESS_CODE)){

                BaseDto<UserDto> userBase = apiInfoClient.findUserByMobile(username);
                admin = userBase.getData();
                if(userBase.getData() != null){
                    log.info(SimpleUtils.appendFlag("login-result success" + admin.getUserId()));
                    try {
                        if (request != null && request.getCookies() != null) {
                            log.info(SimpleUtils.appendFlag("cookie ============ " + request.getCookies().length));
                            log.info(SimpleUtils.appendFlag("HttpServletResponse  ============ " + response));
                        }
                        System.out.println("---"+request.getParameter("urlType"));
                        admin.setLastLoginTime(new Date());
                        apiInfoClient.updateUser(admin);

                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("username", username);
                        params.put("vchecked", true);

                        String rtnStr = Utils.sendWrdIntraBusinessAPIPOST(request, "userLoginMobile", params);
                        System.out.println("----------rtnStr--------"+rtnStr);
                        log.info("IdentificationAction.indexLogin : rtnStr = [" + rtnStr + "]");
                        if (org.apache.commons.lang.StringUtils.isBlank(rtnStr)) {
                            UserRes userRes = JSONObject.parseObject(rtnStr, UserRes.class);
                            LoginUtils.saveLoginFlag(request, response, userRes);
                        }
                        log.info("memocache success");

                    } catch (Exception e) {
                        log.info("memocache err");
                    }
                }
            }
        }else{
            bv.setCode(CodeConstant.FAILURE_CODE);
            bv.setMessage("请提交必要参数");
        }
        return JSON.toJSONString(bv);
    }


    /**
     * 登录用户，检查手机号
     * @throws Exception
     */
    @RequestMapping(value = "/checkLoginMobile")
    @ResponseBody
    public String  checkLoginMobile(String username) throws Exception{
        Integer result = 0;
        if (StringUtils.isNotBlank(username)) {
            BaseDto baseDto=apiInfoClient.checkMobile(username);
            if(baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                result = 1;// 手机号码存在
            }
        }
        /**
         * 返回json格式数据
         */
        return String.valueOf(result);
    }
    /**
     * 登录用户，发送短信验证码
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sendLoginVcode")
    public BaseView sendLoginVcode(HttpServletRequest request,String username) throws Exception {
        if (StringUtils.isNotBlank(username)) {
            Map<String,Object> params = new HashMap<>();
            params.put("type", 2);
            params.put("mobile", username);
            params.put("content", URLEncoder.encode("验证码：$(rand)，欢迎登陆微热点，请在2分钟内按页面提示提交验证码。","GBK"));
            String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,
                    "sendSMSURL", params);
            BaseView bv = null;
            if(StringUtils.isNotBlank(jsonStr)) {
                bv = JSON.parseObject(jsonStr, BaseView.class);
            }
            return bv;
        }
        return null;
    }
    @InitBinder("user")
    public void userRegister(WebDataBinder binder){
        binder.setFieldDefaultPrefix("user.");
    }
    /**
     * 注册用户，检测短信验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/checkRegVcode")
    @ResponseBody
    public BaseView checkRegVcode(@ModelAttribute("user")User user,String authcode ,HttpServletRequest request) throws Exception {
        if (user != null && StringUtils.isNotBlank(user.getMobile())) {
            Map<String,Object> p = new HashMap<>();
            p.put("mobile", user.getMobile());
            p.put("authcode", authcode);
            String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,
                    "checkSMSURL", p);
            BaseView bv = null;
            if(StringUtils.isNotBlank(jsonStr)) {
                bv = JSON.parseObject(jsonStr, BaseView.class);
            }
            return bv;
        }
        return new BaseView(CodeConstant.FAILURE_CODE,"请求数据为空");
    }
    /**
     * 处理注册请求的方法
     *
     * @return
     */
    @RequestMapping(value = "/doRegister")
    public ModelAndView doRegister(ModelAndView modelAndView,Integer keywordId,HttpServletRequest req,HttpServletResponse resp) {
        try {
            //从cookie中找到渠道代码
            String uecChannel = doUecChannel(req);
            UserDto user = new UserDto();
            user.setPassword(req.getParameter("password").toString());
            user.setMobile(req.getParameter("mobile").toString());
            UserDto register = userCenterService.doRegByMobile(req,resp,user,uecChannel,-1);
            if(register==null){
                modelAndView.setViewName(Constants.ERROR_VIEW);
                return modelAndView;
            }
            int keywordIds = userCenterService.doRegister(register,req);
            if (keywordIds != 0) {
                keywordId = keywordIds;
            }
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",register);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(req, null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SYS_REG,objectDto, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, null, null);
            fetchRightNumber(req,modelAndView);
            //活动页进入
            Cookie[] cookies = req.getCookies();
            if(cookies != null){
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals(BusinessConstant.STRETCH_MOBILE_CODE)){
                        if(register.getMobile().equals(cookie.getValue())){//该手机号用户不存在
                            //获取手机号赠送的积分
                            BaseDto baseDto=apiInfoClient.queryRegisterCreditByMobile(cookie.getValue());
                            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
                                UserRegRewardRecord urrr = (UserRegRewardRecord) baseDto.getData();
                                if(urrr != null && urrr.getStatus() != 0){
                                    //把活动手机号用户绑定至微博中打开活动界面授权进来的用户
                                    register.setMobile(cookie.getValue());
                                    //把赠送积分给用户
                                    register.setCreditAmount(register.getCreditAmount() + urrr.getCreditAmount());
                                    UserDto userDto=new UserDto();
                                    userDto.setUserId(register.getUserId());
                                    userDto.setCreditAmount(register.getCreditAmount());
                                    userDto.setMobile(register.getMobile());
                                    apiInfoClient.updateUser(userDto);
                                    //修改赠送积分用户的状态，被领取
                                    apiInfoClient.updateRewardRecord(cookie.getValue());
                                    //用户权益变动
                                    UserRightsRechargeRecord rechargeRecord=new UserRightsRechargeRecord();
                                    rechargeRecord.setUserId(Integer.valueOf(register.getUserId()));
                                    rechargeRecord.setRelationId(urrr.getId());
                                    rechargeRecord.setType(BusinessConstant.USER_RIGHTS_RECHARGE_TYPE_ADD);
                                    rechargeRecord.setCount(urrr.getCreditAmount());
                                    rechargeRecord.setCurrentCount(register.getCreditAmount());
                                    rechargeRecord.setItem(BusinessConstant.USER_RIGHTS_RECHARGE_ITEM_CA);
                                    rechargeRecord.setRecordDesc("赠送100000微积分");
                                    rechargeRecord.setPlatform(1);
                                    rechargeRecord.setStatus(BusinessConstant.STATUS_VALID);
                                    rechargeRecord.setCreateTime(new Date());
                                    apiInfoClient.addRightsRechargeRecord(rechargeRecord);
                                }
                            }
                            modelAndView.setViewName("redirect:/goComplimentaryPointsResult");
                            return modelAndView;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("view/home");
        return modelAndView;
    }


    @RequestMapping(value = "/logout")
    @ResponseBody
    public ModelDto logout(ModelAndView modelAndView,HttpServletRequest request,HttpServletResponse response) {
        admin=fetchSessionAdmin(request);
        if (admin != null){
            try {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("sid", admin.getSid());
                RedisUtils.removeAttribute(admin.getSid());
                Utils.sendWrdIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("userLogout"), params);
                LoginUtils.clearCookieSid(request,response);
                admin = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ModelDto(1,"请求已正常响应");
    }

    @RequestMapping(value = "/sendRegVcode")
    @ResponseBody
    public String sendRegVcode(String mobile,HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> params = new HashMap<>();
        params.put("type", 2);
        params.put("mobile", mobile);
        params.put("content", "验证码：$(rand)，欢迎注册使用微热点(微舆情)，请在15分钟内按页面提示提交验证码。");
        String jsonStr = Utils.sendWrdIntraBusinessAPIPOST(request,"sendSMSURL", params);
        BaseView bv = null;
        if(StringUtils.isNotBlank(jsonStr)) {
            bv = JSON.parseObject(jsonStr, BaseView.class);
        }
        return JSONObject.toJSONString(bv);
    }

    @RequestMapping(value = "/checkStretchMobile")
    @ResponseBody
    public String checkStretchMobile(HttpServletRequest request,HttpServletResponse response) {

        String mobile = request.getParameter("user.mobile").toString();
        Integer result = 2;
        if(StringUtils.isNoneBlank(mobile)){
            //手机号是否存在
            //flag = userBean.checkMobile(user);

            //用户是否有权限领取赠送的积分
            BaseDto baseDto = apiInfoClient.queryRegisterCreditByMobile(mobile);
            UserRegRewardRecord urrr =null;
            if(baseDto.getData()!=null){
                urrr=JSONObject.parseObject(JSONObject.toJSONString(baseDto.getData()),UserRegRewardRecord.class) ;
            }
            if(urrr == null){
                result = 2;//没有权限领取
            }else if(urrr != null && urrr.getStatus() == 0){
                result = 3;//你已经领取
            }else{
                result = 4;//你没有领取
            }
        }
        return JSONObject.toJSONString(result);
    }



    /**
     * 处理邀请注册请求的方法
     *
     * @return
     */
    @RequestMapping(value = "/doRegisterByInvite")
    @ResponseBody
    public ModelDto doRegisterByInvite(HttpServletRequest request,HttpServletResponse response,String mobile,Integer inviteUserId,String channel) {

        UserDto user = new UserDto();
        user.setMobile(mobile);
        UserDto dto = userCenterService.doRegByMobile(request,response,user,channel,inviteUserId);
        if (dto != null){
            return  new ModelDto(1,dto);
        }else {
            return  new ModelDto(0);
        }

    }



}
