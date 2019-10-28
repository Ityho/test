package com.miduchina.wrd.api.controller.v1.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.order.OrderService;
import com.miduchina.wrd.api.service.order.ProductPackageService;
import com.miduchina.wrd.api.service.user.*;
import com.miduchina.wrd.bo.user.UserBO;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.ip.IpInfoDto;
import com.miduchina.wrd.dto.user.*;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.h5.AuthJumpRelation;
import com.miduchina.wrd.po.h5.H5ShortUrl;
import com.miduchina.wrd.po.h5.WeiXinMaterial;
import com.miduchina.wrd.po.order.ProductPackage;
import com.miduchina.wrd.po.user.*;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0.0
 * @ClassName: UserController
 * @Description: 用户-登录、注册、获取、删除
 * @author: 许双龙
 * @date: 2019/7/17 2:49 PM
 */
@Slf4j
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService ;

    @Autowired
    private ProductPackageService productPackageService;

    @Autowired
    private UserWdRightsRecordService userWdRightsRecordService;

    @Autowired
    private UserRightsRecordService userRightsRecordService;

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private UserChannelRelationService userChannelRelationService;

    @Autowired
    private UserRegRewardRecordService recordService;

    @Autowired
    private UserThirdpartyAuthInfoService userThirdpartyAuthInfoService;

    @Autowired
    private OrderService orderService;

    /**
     * 检查用户手机号
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/checkMobile")
    public BaseDto checkMobile(String mobile){
        BaseDto baseDto=new BaseDto();
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("mobile",mobile);
        JSONObject jsonObject=new JSONObject(objectMap);
        User user=userService.getUser(jsonObject);
        if(user!=null){
            baseDto.setData(user).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
        }else {
            if(TextUtils.isEmpty(mobile)){
                baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }




    @RequestMapping(value = "/doSaveUserExclusiveChannelRelation")
    public BaseDto doSaveUserExclusiveChannelRelation(UserExclusiveChannelRelation relation){
        BaseDto baseDto=new BaseDto();
        boolean vale=userChannelService.saveUserExclusiveChannelRelation(relation);
        if(vale){
            baseDto.setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功");
        }else {
            baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("保存失败");
        }
        return baseDto;
    }

    /**
     * 查询cookie的code对应的用户渠道
     * @param code
     * @return
     */
    @RequestMapping(value = "/queryOneUserChannel")
    public BaseDto queryUserChannelByCode(String code){
        BaseDto baseDto=new BaseDto();
        UserExclusiveChannel channel=userChannelService.queryOneUserChannel(code);
        if(channel!=null){
            UserExclusiveChannelDto dto =  BeanUtils.copyProperties(channel, UserExclusiveChannelDto.class);
            baseDto.setData(dto).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
        }else {
            if(TextUtils.isEmpty(code)){
                baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }

    @RequestMapping(value = "/addUserChannelRelation")
    public BaseDto addUserChannelRelation(@RequestBody UserExclusiveChannelRelation channelRelation){
        BaseDto baseDto=new BaseDto();
        if(channelRelation!=null){
            Boolean flag=userChannelRelationService.addUserChannelRelation(channelRelation);
            if(flag!=null && flag){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("添加成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("添加失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }
        return baseDto;
    }

    @RequestMapping(value = "/queryRegisterCreditByMobile")
    public BaseDto queryRegisterCreditByMobile(String mobile){
        BaseDto baseDto=new BaseDto();
        if(!TextUtils.isEmpty(mobile)){
            List<UserRegRewardRecord> regRewardRecordList=recordService.queryRegisterCreditByMobile(mobile);
            if(regRewardRecordList!=null && regRewardRecordList.size()>0){
                baseDto.setData(regRewardRecordList.get(0)).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto;
    }
    @RequestMapping(value = "/updateRewardRecord")
    public BaseDto updateRewardRecord(String mobile){
        BaseDto baseDto=new BaseDto();
        if(!TextUtils.isEmpty(mobile)){
            Boolean flag=recordService.updateRewardRecord(mobile);
            if(flag!=null){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto;
    }
    @RequestMapping(value = "/updateUser")
    public BaseDto updateUser(@RequestBody UserDto userDto){
        BaseDto baseDto=new BaseDto();
        if(userDto!=null){
            Map<String,Object> objectMap=new HashMap<>();
            objectMap.put("mobile",userDto.getMobile());
            objectMap.put("id",userDto.getUserId());
            objectMap.put("creditAmount",userDto.getCreditAmount());

            objectMap.put("creditAmount",userDto.getCreditAmount());
            JSONObject jsonObject=new JSONObject(objectMap);
            Boolean flag=userService.updateUser(jsonObject);
            if(flag!=null){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/addRightsRechargeRecord")
    @ResponseBody
    public BaseDto addRightsRechargeRecord(@RequestBody UserRightsRechargeRecord userRightsRechargeRecord){
        BaseDto baseDto=new BaseDto();
        if(userRightsRechargeRecord!=null){
            Boolean flag=userRightsRecordService.addRightsRechargeRecord(userRightsRechargeRecord);
            if(flag!=null){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto;
    }
    @RequestMapping(value = "/queryOneUserThirdpartyAuth")
    @ResponseBody
    public BaseDto queryOneUserThirdpartyAuth(String userId){
        BaseDto baseDto=new BaseDto();
        if(!TextUtils.isEmpty(userId)){
            UserThirdpartyAuthInfo authInfo=userThirdpartyAuthInfoService.queryOneUserThirdpartyAuth(userId);
            if(authInfo!=null){
                baseDto.setData(authInfo).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto;
    }
    @RequestMapping(value = "/queryUserRightsRechargeRecord")
    @ResponseBody
    public BaseDto queryUserRightsRechargeRecord(UserRightsRechargeRecord userRightsRechargeRecord){
        Map<String,Object> objectMap =new HashMap<>();
        objectMap.put("userId",userRightsRechargeRecord.getUserId());
        objectMap.put("item",userRightsRechargeRecord.getItem());
        objectMap.put("type",userRightsRechargeRecord.getType());
        objectMap.put("relationId",userRightsRechargeRecord.getRelationId());
        JSONObject jsonObject=new JSONObject(objectMap);
        BaseDto baseDto=new BaseDto();
        if(userRightsRechargeRecord!=null){
            List<UserRightsRechargeRecord> rightsRechargeRecordsList=userRightsRecordService.queryUserRightsRechargeRecord(jsonObject);
            if(rightsRechargeRecordsList!=null && rightsRechargeRecordsList.size()>0){
                baseDto.setData(rightsRechargeRecordsList.get(0)).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto;
    }
    @RequestMapping(value = "/findPayRecordByUserIdAndPackageId")
    @ResponseBody
    public BaseDto findPayRecordByUserIdAndPackageId(Integer userId,Integer packageId){
        Map<String,Object> objectMap =new HashMap<>();
        objectMap.put("userId",userId);
        objectMap.put("packageId",packageId);
        JSONObject jsonObject=new JSONObject(objectMap);
        BaseDto baseDto=new BaseDto();
        if(userId!=null && packageId!=null){
            List<Integer> integerList=orderService.findPayRecordByUserIdAndPackageId(jsonObject);
            if(integerList!=null && integerList.size()>0){
                baseDto.setData(integerList.get(0)).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto;
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param vchecked
     *            是否短信验证
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public BaseDto<UserDto> login(HttpServletRequest request, HttpServletResponse response, String username, String password, Boolean vchecked) {
        log.info("UserController.login username = [" + username + "] password = [" + password + "] vchecked = [" + vchecked + "]");
        BaseDto<UserDto> baseDto = new BaseDto<>();
        
        if (StringUtils.isBlank(username) || username.contains("'") || username.contains("(")) {
            return baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_USERNAME_PARAM);
        }

        if (vchecked == null) {
            vchecked = false;
        }

        if (!vchecked) {
            if (StringUtils.isBlank(password)) {
                return baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_PASSWORD_PARAM);
            }
        }

        //验证ip异常
        String ip = CommonUtils.getOriginalRequestIp(request);
        IpInfoDto ipInfo = CommonUtils.getIpInfoByTbApi(ip);
        if(CommonUtils.checkIsAbnormalIp(ipInfo)){
            log.info("login fail, ip is abnormal, ip=" + CommonUtils.getOriginalRequestIp(request) + ", username=" + username);
            return baseDto.setCodeMessage(CodeConstant.F_ERROR_IP_IS_ABNORMAL);
        }

        try {
            baseDto = userService.doLogin(request, username, password, vchecked, ipInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
        }

        log.info("UserController.login username = [" + username + "] password = [" + password + "] vchecked = [" + vchecked + "] user login success!");

        return baseDto;
    }

    /**
     * 退出
     *
     * @param request
     * @param response
     * @param sid
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public BaseDto logout(HttpServletRequest request, HttpServletResponse response, String sid) {
        log.info("UserController.logout sid = [" + sid + "]");
        BaseDto baseDto = new BaseDto<>();

        if (StringUtils.isBlank(sid)) {
            return baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_SID);
        }

        RedisUtils.removeAttribute(sid);

        log.info("UserController.logout sid = [" + sid + "] logout success!");

        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
    }

    /**
     * 创建用户
     * @param request
     * @param response
     * @param userParam
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public BaseDto<UserDto> create(HttpServletRequest request, HttpServletResponse response, String userParam) {
        log.info("UserController.create userParam = [" + userParam + "]");
        BaseDto<UserDto> baseDto = new BaseDto<>();
        // 参数校验
        if (StringUtils.isBlank(userParam)) {
            return baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_USER_PARAM);
        }

        UserCreateDto userCreateDto = JSONObject.parseObject(userParam, UserCreateDto.class);
        if (userCreateDto == null) {
            return baseDto.setCodeMessage(CodeConstant.F_ERROR_PARSE_USER);
        }

        if (StringUtils.isBlank(userCreateDto.getUsername()) || userCreateDto.getUsername().contains("'") || userCreateDto.getUsername().contains("(")) {
            return baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_USERNAME_PARAM);
        }

        String ip = CommonUtils.getOriginalRequestIp(request);
        int platform = Integer.parseInt(request.getParameter("platform"));
        IpInfoDto ipInfo = CommonUtils.getIpInfoByTbApi(ip);

        //验证ip和手机号省份是否一致
        if(SysConfig.cfgMap.get("IP_AND_MOBILE_PROVINCE_CHECK_SWITCH") != null && "1".equals(SysConfig.cfgMap.get("IP_AND_MOBILE_PROVINCE_CHECK_SWITCH"))
                && CommonUtils.checkMobile(userCreateDto.getUsername())){
            String mobileProvince = CommonUtils.getMobileProvince(userCreateDto.getUsername());
            String ipProvince = ipInfo.getProvince();
            if(StringUtils.isBlank(mobileProvince) || StringUtils.isBlank(ipProvince) || !ipProvince.equals(mobileProvince)){
                log.info("UserController.create mobile and ip province no same, mobile:" + userCreateDto.getUsername()
                        + " mobile province:" + mobileProvince + ", ip:" + ip + " ip province:" + ipProvince);
                return baseDto.setCodeMessage(CodeConstant.F_ERROR_REGISTER_USER_IP_AND_MOBILE_PROVINCE_NOT_SAME);
            }
        }

        //验证ip下用户数是否超限
        if(platform != BusinessConstant.PLATFORM_TYPE_H5 && "www".equals(SysConfig.cfgMap.get("WEBID_COOKIE_NAME")) && !Arrays.asList(SysConfig.cfgMap.get("REGISTER_WRITE_IP").split(",")).contains(ip)
                && StringUtils.isNotBlank(userCreateDto.getPassword()) && !userCreateDto.getPassword().equals("b59c57da1a398e440d126638c11f774a")){
            Integer sameIpUserCount = userService.querySameIpUserCount(ip);
            if(sameIpUserCount != null && sameIpUserCount.intValue() >= Integer.valueOf(SysConfig.cfgMap.get("SINGLE_IP_MAX_REGISTER_USER_NUM"))){
                //saveRestrictRegisterUser(null, userCreateDto.getUsername(), ip, sameIpUserCount, ipInfo);
                log.info("UserController.create single ip user num too more, mpbile=" + userCreateDto.getUsername() + ", sameIpUserCount=" + sameIpUserCount + ", ip=" + ip);
                return baseDto.setCodeMessage(CodeConstant.F_ERROR_MAX_REGISTER_USER_NUM);
            }
        }

        if (StringUtils.isBlank(userCreateDto.getPassword())) {
            return baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_PASSWORD_PARAM);
        }

        if (userCreateDto.getAppUserStatus() == null) {
            return baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_APP_USER_STATUS_PARAM);
        }

        if (userCreateDto.getUserChannel() == null) {
            return baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_USER_CHANNEL_PARAM);
        }

        if (userCreateDto.getGiftPackageId() == null || userCreateDto.getGiftPackageId() < 0) {
            userCreateDto.setGiftPackageId(0);
        }

        // 判断用户是否已存在
        boolean userExists = userService.queryUsernameExists(userCreateDto.getUsername());
        if (userExists) {
            return baseDto.setCodeMessage(CodeConstant.F_ERROR_USER_ALREADY_EXISTS);
        }

        // 加载赠送产品包
        ProductPackage giftProductPackage = null;
        if (userCreateDto.getGiftPackageId() > 0) {
            //web注册不赠送体验型产品包
            if((platform == BusinessConstant.PLATFORM_TYPE_WEB || platform == BusinessConstant.PLATFORM_TYPE_APP) && userCreateDto.getGiftPackageId().intValue() == Integer.valueOf(BusinessConstant.PACKAGE_TYPE_FREE)){	//(暂时web不自动赠送产品包)
                userCreateDto.setGiftPackage(false);
            }else{
                userCreateDto.setGiftPackage(true);
                giftProductPackage = productPackageService.queryProductPackageById(userCreateDto.getGiftPackageId());

                if (giftProductPackage == null) {
                    return baseDto.setCodeMessage(CodeConstant.F_NOT_FOUND_PRODUCT_PACKAGE);
                }
            }
        }

        // 用户注册
        UserBO rtnUser = userService.doRegister(request, userCreateDto);
        if (rtnUser == null) {
            baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
        }

        // 赠送产品包
        if (giftProductPackage != null) {
            // 初始化用户权益
            rtnUser = initUserRightsRecord(rtnUser);
//            boolean succ = giftPackages(request, rtnUser, giftProductPackage);
//            if (!succ) {
//                return baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
//            }
        }

        log.info("UserController.create userParam = [" + userParam + "] user register success!");

        // 登录
        if (userCreateDto.isLogin()) {
            return login(request, response, rtnUser.getUsername(), rtnUser.getPassword(), false);
        }

        // 初始化用户权益
        rtnUser = initUserRightsRecord(rtnUser);

        //绑定手机号赠送微豆
        if(CommonUtils.checkMobile(rtnUser.getMobile()) && RedisUtils.lockUserOperate(rtnUser.getUserId())){
            Integer times = userWdRightsRecordService.queryAddWdTimes(rtnUser.getUserId(), BusinessConstant.USER_WD_RIGHTS_ITEM_TASK_BIND_PHONE, rtnUser.getUserId(), false);
            if(times != null && times == 0){
                UserWdRightsRecord userWdRightsRecord = new UserWdRightsRecord(rtnUser.getUserId(), BusinessConstant.USER_WD_RIGHTS_ITEM_TASK_BIND_PHONE, BusinessConstant.USER_WD_FROM_BIND_PHONE_ADD_NUM, null, null, BusinessConstant.USER_WD_FROM_BIND_PHONE_ADD_DESC, null, null);
                rtnUser = userWdRightsRecordService.doAddUserWdRightsRecord(request, rtnUser, userWdRightsRecord, rtnUser.getUserId());
                rtnUser = refreshSessionUser(rtnUser.getUserId(), null);
            }
            RedisUtils.unLockUserOperate(rtnUser.getUserId());
        }

        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(BeanUtils.copyProperties(rtnUser,UserDto.class));
    }

//    /**
//     * 赠送产品包
//     *
//     * @param request
//     * @param user
//     * @param giftProductPackage
//     * @return
//     */
//    private boolean giftPackages(HttpServletRequest request, UserBO user, ProductPackage giftProductPackage) {
//        ConfirmOrderDto confirmOrderDto = new ConfirmOrderDto();
//        confirmOrderDto.setOrderType(BusinessConstant.ORDER_TYPE_GIFT);
//        List<ConfirmOrderPackageDto> packageElements = new ArrayList<>();
//        ConfirmOrderPackageDto packageElement = new ConfirmOrderPackageDto();
//        packageElement.setProductPackageId(giftProductPackage.getProductPackageId());
//        packageElement.setPackageCount(1);
//        packageElements.add(packageElement);
//        confirmOrderDto.setPackages(packageElements);
//
//        Map<String, Object> confirmOrderMap = orderService.doConfirmOrder(request, user, confirmOrderDto);
//
//        ConfirmOrderViewPayDto payRecordInfo = (ConfirmOrderViewPayDto) confirmOrderMap.get("payRecord");
//        PayRecord payRecord = payService.queryPayRecordByInnerTradeNo(payRecordInfo.getInnerTradeNo());
//        orderService.doPayEnd(request, user, payRecord, null, BusinessConstant.ORDER_VIRTUAL_SJ);
//
//        return true;
//    }
//
//    /**
//     * 记录IP限制的用户
//     */
//    private void saveRestrictRegisterUser(String nickname, String mobile, String ip, Integer num, IpInfoDto ipInfo){
//        RestrictRegisterUserView user = null;
//        String key = null;
//        if(StringUtils.isNotBlank(mobile)){
//            nickname = mobile;
//            key = RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_RESTRICT_REGISTER_USER + mobile);
//        }else if(StringUtils.isNotBlank(nickname)){
//            key = RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_RESTRICT_REGISTER_USER + MD5Utils.MD5Encode(nickname));
//        }
//        String json = RedisUtils.getAttribute(key);
//        if(StringUtils.isNotBlank(json)){
//            user = JSONObject.parseObject(json, RestrictRegisterUserView.class);
//        }
//        if(user == null){
//            user = new RestrictRegisterUserView();
//            user.setUsername(nickname);
//        }
//        List<RestrictRegisterUserVO> restrictInfos = user.getRestrictInfos();
//        if(restrictInfos == null){
//            restrictInfos = new ArrayList<RestrictRegisterUserVO>();
//        }
//        for(RestrictRegisterUserVO restrictInfo : restrictInfos){
//            if(restrictInfo.getIp().equals(ip)){
//                return;
//            }
//        }
//        RestrictRegisterUserVO restrictInfo = new RestrictRegisterUserVO();
//        restrictInfo.setIp(ip);
//        restrictInfo.setUsername(nickname);
//        restrictInfo.setSameNum(num);
//        restrictInfo.setIpInfo(ipInfo);
//        restrictInfos.add(restrictInfo);
//        user.setRestrictInfos(restrictInfos);
//
//        RedisUtils.setAttribute(key, JSONObject.toJSONString(user));
//    }



    @RequestMapping(value = "/findUserByMobile")
    @ResponseBody
    public BaseDto findUserByMobile(String mobile){
        Map<String,Object> objectMap =new HashMap<>();
        objectMap.put("mobile",mobile);
        JSONObject jsonObject=new JSONObject(objectMap);
        BaseDto baseDto=new BaseDto();
        if(StringUtils.isNotBlank(mobile)){
            User user= userService.getUser(jsonObject);
            if(user!= null){
                baseDto.setData(BeanUtils.copyProperties(user,UserDto.class)).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            } else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数为空");
        }
        return baseDto;
    }


    /**
     * 初始化用户权益
     *
     * @param user
     * @return
     */
    protected UserBO initUserRightsRecord(UserBO user) {
        return userRightsRecordService.initUserRightsRecord(user);
    }

    /**
     * 刷新缓存对象
     *
     * @param userId
     * @param key
     */
    protected UserBO refreshSessionUser(int userId, String key) {
        return userRightsRecordService.refreshSessionUser(userId, key);
    }


    @ApiOperation(value = "saveH5ShortUrl", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "saveH5ShortUrl")
    @ResponseBody
    public BaseDto saveH5ShortUrl(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("h5ShortUrl");
        if(StringUtils.isNotBlank(str)){
            H5ShortUrl Hh5url=JSON.parseObject(str, H5ShortUrl.class);
            boolean b =userThirdpartyAuthInfoService.saveH5ShortUrl(Hh5url);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少必要参数");
        }
        return baseDto;
    }

    @ApiOperation(value = "findH5ShortUrlByShortUrl", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findH5ShortUrlByShortUrl")
    @ResponseBody
    public BaseDto findH5ShortUrlByShortUrl(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String shortUrl = jsonObject.getString("shortUrl");
        if(StringUtils.isNotBlank(shortUrl)){
            H5ShortUrl b =userThirdpartyAuthInfoService.findH5ShortUrlByShortUrl(shortUrl);
            if (b!=null){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少必要参数");
        }
        return baseDto;
    }

    //微信授权相关接口
    @ApiOperation(value = "findWeiXinMaterialByOpenIdAndType", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findWeiXinMaterialByOpenIdAndType")
    @ResponseBody
    public BaseDto findWeiXinMaterialByOpenIdAndType(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String openId = jsonObject.getString("openId");
        Integer type = jsonObject.getInteger("type");
        if(StringUtils.isNotBlank(openId)&&type!=null){
            WeiXinMaterial b =userThirdpartyAuthInfoService.findWeiXinMaterialByOpenIdAndType(openId,type);
            if (b!=null){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少必要参数");
        }
        return baseDto;
    }
    @ApiOperation(value = "saveWeiXinMaterial", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "saveWeiXinMaterial")
    @ResponseBody
    public BaseDto saveWeiXinMaterial(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String wxMaterial = jsonObject.getString("wxMaterial");
        if(StringUtils.isNotBlank(wxMaterial)){
            WeiXinMaterial weiXinMaterial=JSON.parseObject(wxMaterial, WeiXinMaterial.class);
            boolean b =userThirdpartyAuthInfoService.saveWeiXinMaterial(weiXinMaterial);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少必要参数");
        }
        return baseDto;
    }
    @ApiOperation(value = "queryUserThirdpartyAuthInfoByUid", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "queryUserThirdpartyAuthInfoByUid")
    @ResponseBody
    public BaseDto queryUserThirdpartyAuthInfoByUid(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("userThirdpartyAuthInfo");
        if(StringUtils.isNotBlank(str)){
            UserThirdpartyAuthInfo uThirdparty=JSON.parseObject(str, UserThirdpartyAuthInfo.class);
            UserThirdpartyAuthInfo userThirdpartyAuthInfo = userThirdpartyAuthInfoService.queryUserThirdpartyAuthInfoByUid(uThirdparty);
            log.info("userThirdpartyAuthInfo:{}",JSONObject.toJSONString(userThirdpartyAuthInfo));
            if (userThirdpartyAuthInfo!=null){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(userThirdpartyAuthInfo);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少必要参数");
        }
        log.info("userThirdpartyAuthInfo:{}",JSONObject.toJSONString(baseDto));
        return baseDto;
    }

    @ApiOperation(value = "根据第三方用户查询用户", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findUserByThirdpartyccount")
    public BaseDto findUserByThirdpartyccount(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String thirdpartyAccount = jsonObject.getString("thirdpartyAccount");
        if(StringUtils.isNotBlank(thirdpartyAccount)){
            User user = userService.findUserByThirdpartyccount(thirdpartyAccount);
            if (user!=null){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(user);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少必要参数");
        }
        return baseDto;
    }

    @ApiOperation(value = "查看用户点击微博私信记录", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findAccessRecords")
    public BaseDto findAccessRecords(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String userId = jsonObject.getString("userId");
        if(StringUtils.isNotBlank(userId)){
            AuthJumpRelation user = userService.findAccessRecords(userId);
            if (user!=null){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(user);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少必要参数");
        }
        return baseDto;
    }

    @ApiOperation(value = "删除用户点击微博私信记录", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "deleteAccessRecords")
    public BaseDto deleteAccessRecords(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        Integer userId = jsonObject.getInteger("userId");
        Integer keywordId = jsonObject.getInteger("keywordId");
        if(userId!=null&&keywordId!=null){
            boolean b = userService.deleteAccessRecords(userId,keywordId);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少必要参数");
        }
        return baseDto;
    }




    @ApiOperation(value = "查询用户", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findUserByUserId")
    public BaseDto findUserByUserId(Integer id) {
        BaseDto baseDto = new BaseDto();

        if (id != null){
            Map<String,Object> objectMap=new HashMap<>();
            objectMap.put("userId",id);
            JSONObject jsonObject=new JSONObject(objectMap);
            User user =  userService.getUser(jsonObject);
            if (user != null){
                UserDto dto = BeanUtils.copyProperties(user, UserDto.class);
                baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(dto);
            }else {
                baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
            }
        }else {
            baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_ERROR);
        }
        return baseDto;
    }



}
