package com.miduchina.wrd.api.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.mapper.log.LoginLogMapper;
import com.miduchina.wrd.api.mapper.user.UserExtendInfoMapper;
import com.miduchina.wrd.api.mapper.user.UserExtraInfoMapper;
import com.miduchina.wrd.api.mapper.user.UserMapper;
import com.miduchina.wrd.api.service.order.OrderService;
import com.miduchina.wrd.api.service.order.ProductPackageService;
import com.miduchina.wrd.api.service.user.*;
import com.miduchina.wrd.bo.user.UserBO;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.ip.IpInfoDto;
import com.miduchina.wrd.dto.user.*;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.h5.AuthJumpRelation;
import com.miduchina.wrd.po.log.LoginLog;
import com.miduchina.wrd.po.order.ProductPackage;
import com.miduchina.wrd.po.user.*;
import com.miduchina.wrd.util.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: UserServiceImpl
 * @Description: 用户业务处理实现
 * @author: sty
 * @date: 2019/7/17 2:52 PM
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserExtendInfoMapper userExtendInfoMapper;

    @Resource
    private UserExtraInfoMapper userExtraInfoMapper;

    @Resource
    private LoginLogMapper loginLogMapper;

    @Autowired
    private UserInviteService userInviteService;

    @Autowired
    private UserThirdpartyAuthInfoService userThirdpartyAuthInfoService;

    @Autowired
    private UserExclusiveChannelService userExclusiveChannelService;

    @Autowired
    private SubUserInfoService subUserInfoService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductPackageService productPackageService;

    @Autowired
    private UserRightsRecordService userRightsRecordService;

    @Override
    public User getUser(JSONObject params) {
        List<User> userList = userMapper.getListUser(params);
        if (CollectionUtils.isNotEmpty(userList)){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public PageInfo<User> getListUser(JSONObject params){
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> userMapper.getListUser(params));
    }


    @Override
    public Boolean insertUser(JSONObject params){
        return userMapper.insertUser(params);
    }


    @Override
    public Boolean updateUser(JSONObject params){
        return userMapper.updateUser(params);
    }

    @Override
    public Integer querySameIpUserCount(String ip){
        return userMapper.selectSameIpUserCount(ip);
    }

    @Override
    public boolean queryUsernameExists(String username) {
        return userMapper.selectUsernameExists(username);
    }

    @Override
    public User queryUserByUserId(Integer userId) {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public User queryValidUserByUserId(int userId) {
        return userMapper.selectValidUserById(userId);
    }

    @Override
    public BaseDto<UserDto> doLogin(HttpServletRequest request, String username, String password, Boolean vchecked, IpInfoDto ipInfo) {
        // 平台
        int platform = Integer.parseInt(request.getParameter("platform"));

        BaseDto<UserDto> baseDto = new BaseDto<>();
        User user;
        UserBO userBO;
        // 子账号登录
        if (username.startsWith("s_")) {
            SubUserInfo subUserInfo = subUserInfoService.querySubUserInfoByUsernameAndPassword(username, password);
            if (subUserInfo == null) {
                return baseDto.setCode(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_00);
            }

            if (subUserInfo.getStatus() == BusinessConstant.SUB_USER_STATUS_PAUSE) {
                return baseDto.setCode(CodeConstant.F_ERROR_USER_PAUSE);
            }

            if (subUserInfo.getValidEndDate().getTime() < System.currentTimeMillis()) {
                return baseDto.setCode(CodeConstant.F_ERROR_USER_VALIDENDTIME_LIMIT);
            }

            user = userMapper.selectValidUserById(subUserInfo.getParentUserId());
            if (user == null) {
                return baseDto.setCode(CodeConstant.F_NOT_FOUND_USER);
            }

            userBO = BeanUtils.copyProperties(user,UserBO.class);
            // 子账号
            userBO.setSubUser(true);
            // 子账号ID
            userBO.setSubUserId(subUserInfo.getSubUserId());
        } else { // 主账号登录
            user = userMapper.selectValidUserByUsername(username);
            if (user == null) {
                return baseDto.setCode(CodeConstant.F_ERROR_USERNAME_NOT_EXISTS);
            }

            if (!vchecked) {
                // 验证当日登录输错密码次数 intraApi_beta_login_valid_count_18717759523
                String key = RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_LOGIN_VALID_COUNT).append(username).toString());
                int userLoginValidCount = 0;
                int validCount = Integer.parseInt(SysConfig.cfgMap.get("USER_LOGIN_VALID_COUNT"));
                String userLoginValid = RedisUtils.getAttribute(key);
                if (StringUtils.isNotBlank(userLoginValid)) {
                    userLoginValidCount = Integer.parseInt(userLoginValid);
                }

                if (userLoginValidCount >= validCount) {
                    return baseDto.setCode(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_05);
                }

                if (!password.equals(user.getPassword())) {
                    // 获取距当天零点的时间差
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    //新需求=缓存1小时
                    RedisUtils.setAttribute(key, String.valueOf(userLoginValidCount + 1), 60*60);

                    if (userLoginValidCount < (validCount - 1)){
                        if(userLoginValidCount==3){
                            return baseDto.setCode(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_04);
                        }else{
                            return baseDto.setCode(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_01);
                        }
                    }else{
                        return baseDto.setCode(CodeConstant.F_ERROR_USERNAME_OR_PASSWORD_05);
                    }
                }
            }

            userBO = BeanUtils.copyProperties(user,UserBO.class);
            // 非子账号
            userBO.setSubUser(false);

            // 修改最后登录日期
            doModifyLastLoginTime(user.getUserId());

            // 记录扩展信息
            UserExtraInfo userExtraInfo = userExtraInfoMapper.selectUserExtraInfo(new UserExtraInfo(BusinessConstant.THIRDPARTY_TYPE_MOBILE, username, null, BusinessConstant.STATUS_VALID));
            if(ipInfo == null){
                String ip = CommonUtils.getOriginalRequestIp(request);
                ipInfo = CommonUtils.getIpInfoByTbApi(ip);
            }
            if (userExtraInfo == null) {
                userExtraInfo = new UserExtraInfo();
                userExtraInfo.setExtraAccountType(BusinessConstant.THIRDPARTY_TYPE_MOBILE);
                userExtraInfo.setExtraRegIp(ipInfo.getIp());
                userExtraInfo.setExtraMobile(username);
                userExtraInfo.setStatus(BusinessConstant.STATUS_VALID);
                userExtraInfo.setCreateTime(new Date());

                userExtraInfoMapper.insertUserExtraInfo(userExtraInfo);
            }

            // 记录登录日志
            LoginLog loginLog = new LoginLog();
            loginLog.setLoginLogUserId(user.getUserId());
            loginLog.setLoginLogTime(new Date());
            loginLog.setLoginLogType(BusinessConstant.LOGIN_TYPE_MOBILE);
            loginLog.setLoginLogPlatform(platform == BusinessConstant.PLATFORM_TYPE_H5 ? (platform + 1) : platform);
            loginLog.setLoginLogIp(ipInfo.getIp());
            if(StringUtils.isNotBlank(ipInfo.getIp())){
                loginLog.setLoginLogNumIp(ipInfo.getIpNum());
            }
            loginLog.setLoginProvince(ipInfo.getProvince());
            loginLog.setLoginCity(ipInfo.getCity());
            loginLog.setLoginIsp(ipInfo.getIsp());
            loginLogMapper.insertLoginLog(loginLog);

           // OperateLogUtil.opreateLog(request, user, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SYS_LOGIN, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);
        }

        // 初始化用户权益
        userBO = userRightsRecordService.initUserRightsRecord(userBO);

        // 将用户信息存储到jedis
        String loginKey = CommonUtils.setLoginJedis(request, userBO);

        UserDto userDto = BeanUtils.copyProperties(userBO,UserDto.class);
        userDto.setSid(loginKey);
        return baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(userDto);
    }

    @Override
    public UserBO doRegister(HttpServletRequest request, UserCreateDto userCreateDto) {
        // 生成用户信息
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setNickname(userCreateDto.getNickname());
        user.setPassword(userCreateDto.getPassword());
        user.setPasswordStrength(CommonUtils.getPasswordStrength(userCreateDto.getPassword()));
        user.setThirdpartyType(0);
        user.setMobile(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());
        user.setUserHead(StringUtils.isBlank(userCreateDto.getUserHead()) ? (SysConfig.cfgMap.get("FILE_VIEW_PATH") + "user_head/default.jpg") : userCreateDto.getUserHead());
        user.setUserType(BusinessConstant.USER_TYPE_BASE);
        user.setAppUserStatus(userCreateDto.getAppUserStatus());
        user.setUserChannel(userCreateDto.getUserChannel());
        user.setPlatform(userCreateDto.getAppPlatform());
        user.setStatus(BusinessConstant.STATUS_VALID);
        user.setCreateTime(new Date());
        JSONObject userJSon = JSON.parseObject(JSON.toJSONString(user));
        boolean succ = userMapper.insertUser(userJSon);
        if (!succ) {
            return null;
        }

        // 绑定渠道
        if (StringUtils.isNotBlank(userCreateDto.getExclusiveChannelCode())) {
            UserExclusiveChannel userExclusiveChannel = userExclusiveChannelService.queryExclusiveChannelByCode(userCreateDto.getExclusiveChannelCode());
            if (userExclusiveChannel != null) {
                UserExclusiveChannelRelation userExclusiveChannelRelation = new UserExclusiveChannelRelation();
                userExclusiveChannelRelation.setUserId(user.getUserId());
                userExclusiveChannelRelation.setUecId(userExclusiveChannel.getUecId());
                userExclusiveChannelRelation.setStatus(BusinessConstant.STATUS_VALID);
                userExclusiveChannelRelation.setCreateTime(new Date());

                userExclusiveChannelService.doSaveUserExclusiveChannelRelation(userExclusiveChannelRelation);
            }
        }

        // 添加扩展信息
        UserExtendInfo userExtendInfo = new UserExtendInfo(user.getUserId());
        if(userCreateDto.isGiftPackage()){
            userExtendInfo.setIsGiftPackage(1);
        }else{
            userExtendInfo.setIsGiftPackage(0);
        }
        userExtendInfoMapper.insertUserExtendInfo(userExtendInfo);

        // 邀请活动注册用户
        if(userCreateDto.getUserChannel() != null && userCreateDto.getInviteUserId() != null
                && userCreateDto.getUserChannel().intValue() == BusinessConstant.USER_CHANNEL_INVITE){
            UserInviteRelation userInviteRelation = new UserInviteRelation();
            userInviteRelation.setInviteUserId(userCreateDto.getInviteUserId());
            userInviteRelation.setUserId(user.getUserId());
            userInviteRelation.setStatus(BusinessConstant.STATUS_VALID);
            userInviteRelation.setCreateTime(new Date());
            userInviteService.doSaveUserInviteRelation(userInviteRelation);
        }

        // 记录注册日志
        //OperateLogUtil.opreateLog(request, user, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SYS_REG, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);

        return BeanUtils.copyProperties(user,UserBO.class);
    }

    @Override
    public BaseDto<UserThirdpartyDto> doLoginThirdparty(HttpServletRequest request, UserBO user, UserCreateThirdpartyDto userCreateThirdpartyDto, IpInfoDto ipInfo) {
        // 平台
        int platform = Integer.parseInt(request.getParameter("platform"));

        BaseDto<UserThirdpartyDto> baseDto = new BaseDto<>();
        // 修改最后登录日期
        doModifyLastLoginTime(user.getUserId());

        // 修改授权信息
        if (StringUtils.isNotBlank(userCreateThirdpartyDto.getAccessToken())) {
            UserThirdpartyAuthInfo userThirdpartyAuthInfo = userThirdpartyAuthInfoService.queryUserThirdpartyAuthInfoByUid(new UserThirdpartyAuthInfo(userCreateThirdpartyDto.getUid(), userCreateThirdpartyDto.getPlatformType(), BusinessConstant.STATUS_VALID));
            userThirdpartyAuthInfo.setAccessToken(userCreateThirdpartyDto.getAccessToken());
            if (StringUtils.isNotBlank(userCreateThirdpartyDto.getRefreshToken())) {
                userThirdpartyAuthInfo.setRefreshToken(userCreateThirdpartyDto.getRefreshToken());
            }
            if (userCreateThirdpartyDto.getExpireIn() != null && userCreateThirdpartyDto.getExpireIn() > 0) {
                userThirdpartyAuthInfo.setExpireTime(new Date(System.currentTimeMillis() + userCreateThirdpartyDto.getExpireIn() * 1000));
            }
            if (StringUtils.isNotBlank(userCreateThirdpartyDto.getVerifiedType())) {
                userThirdpartyAuthInfo.setVerifiedType(userCreateThirdpartyDto.getVerifiedType());
            }
            userThirdpartyAuthInfoService.doModifyUserThirdpartyAuthInfo(userThirdpartyAuthInfo);
        }

        // 记录登录日志
        if(ipInfo == null){
            String ip = CommonUtils.getOriginalRequestIp(request);
            ipInfo = CommonUtils.getIpInfoByTbApi(ip);
        }
        LoginLog loginLog = new LoginLog();
        loginLog.setLoginLogUserId(user.getUserId());
        loginLog.setLoginLogTime(new Date());
        loginLog.setLoginLogType(userCreateThirdpartyDto.getPlatformType() + 1);
        loginLog.setLoginLogPlatform(platform == BusinessConstant.PLATFORM_TYPE_H5 ? (platform + 1) : platform);
        loginLog.setLoginLogIp(ipInfo.getIp());
        loginLog.setLoginLogNumIp(ipInfo.getIpNum());
        loginLog.setLoginProvince(ipInfo.getProvince());
        loginLog.setLoginCity(ipInfo.getCity());
        loginLog.setLoginIsp(ipInfo.getIsp());
        loginLogMapper.insertLoginLog(loginLog);

        //OperateLogUtil.opreateLog(request, user, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SYS_LOGIN, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);

        // 将用户信息存储到jedis
        String loginKey = CommonUtils.setLoginJedis(request, user);

        UserThirdpartyDto userThirdparty = BeanUtils.copyProperties(user,UserThirdpartyDto.class);
        userThirdparty.setSid(loginKey);
        userThirdparty.setRegister(false);
        return baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(userThirdparty);
    }

    @Override
    public UserBO doRegisterThirdparty(HttpServletRequest request, UserCreateThirdpartyDto userCreateThirdpartyDto) {
        // 平台
        int platform = Integer.parseInt(request.getParameter("platform"));

        // 生成用户信息
        User user = new User();
        if (StringUtils.isNotBlank(userCreateThirdpartyDto.getMobile())) {
            User tempUser = userMapper.selectValidUserByUsername(userCreateThirdpartyDto.getMobile());
            if (tempUser != null) {
                user.setUsername(userCreateThirdpartyDto.getUid());
                user.setPasswordStrength(1);
            } else {
                user.setUsername(userCreateThirdpartyDto.getMobile());
                user.setPassword(userCreateThirdpartyDto.getPassword());
                user.setPasswordStrength(StringUtils.isBlank(userCreateThirdpartyDto.getPassword()) ? 1 : CommonUtils.getPasswordStrength(userCreateThirdpartyDto.getPassword()));
            }
        } else {
            user.setUsername(userCreateThirdpartyDto.getUid());
            user.setPasswordStrength(1);
        }
        user.setUsername(StringUtils.isBlank(userCreateThirdpartyDto.getMobile()) ? userCreateThirdpartyDto.getUid() : userCreateThirdpartyDto.getMobile());
        user.setNickname(userCreateThirdpartyDto.getNickname());
        user.setThirdpartyAccount(userCreateThirdpartyDto.getUid());
        user.setThirdpartyType(1);
        user.setMobile(userCreateThirdpartyDto.getMobile());
        user.setEmail(userCreateThirdpartyDto.getEmail());
        user.setUserHead(StringUtils.isBlank(userCreateThirdpartyDto.getUserHead()) ? (SysConfig.cfgMap.get("FILE_VIEW_PATH") + "user_head/default.jpg") : userCreateThirdpartyDto.getUserHead());
        user.setUserType(BusinessConstant.USER_TYPE_BASE);
        user.setAppUserStatus(userCreateThirdpartyDto.getAppUserStatus());
        user.setUserChannel(userCreateThirdpartyDto.getUserChannel());
        user.setPlatform(userCreateThirdpartyDto.getAppPlatform());
        user.setStatus(BusinessConstant.STATUS_VALID);
        user.setCreateTime(new Date());

        JSONObject userJSon = JSON.parseObject(JSON.toJSONString(user));
        boolean succ = userMapper.insertUser(userJSon);
        if (!succ) {
            return null;
        }

        // 生成授权信息
        UserThirdpartyAuthInfo userThirdpartyAuthInfo = new UserThirdpartyAuthInfo();
        userThirdpartyAuthInfo.setThirdpartyUserId(userCreateThirdpartyDto.getUid());
        userThirdpartyAuthInfo.setUserId(user.getUserId());
        userThirdpartyAuthInfo.setAccessToken(userCreateThirdpartyDto.getAccessToken());
        if (StringUtils.isNotBlank(userCreateThirdpartyDto.getRefreshToken())) {
            userThirdpartyAuthInfo.setRefreshToken(userCreateThirdpartyDto.getRefreshToken());
        }
        userThirdpartyAuthInfo.setAuthTime(new Date());
        if (userCreateThirdpartyDto.getExpireIn() != null && userCreateThirdpartyDto.getExpireIn() > 0) {
            userThirdpartyAuthInfo.setExpireTime(new Date(System.currentTimeMillis() + userCreateThirdpartyDto.getExpireIn() * 1000));
        }
        if (StringUtils.isNotBlank(userCreateThirdpartyDto.getVerifiedType())) {
            userThirdpartyAuthInfo.setVerifiedType(userCreateThirdpartyDto.getVerifiedType());
        }
        userThirdpartyAuthInfo.setPlatformType(userCreateThirdpartyDto.getPlatformType());
        userThirdpartyAuthInfo.setApplicationType(platform);
        userThirdpartyAuthInfo.setStatus(BusinessConstant.STATUS_VALID);
        succ = userThirdpartyAuthInfoService.doSaveUserThirdpartyAuthInfo(userThirdpartyAuthInfo);
        if (!succ) {
            return null;
        }

        // 绑定渠道
        if (StringUtils.isNotBlank(userCreateThirdpartyDto.getExclusiveChannelCode())) {
            UserExclusiveChannel userExclusiveChannel = userExclusiveChannelService.queryExclusiveChannelByCode(userCreateThirdpartyDto.getExclusiveChannelCode());
            if (userExclusiveChannel != null) {
                UserExclusiveChannelRelation userExclusiveChannelRelation = new UserExclusiveChannelRelation();
                userExclusiveChannelRelation.setUserId(user.getUserId());
                userExclusiveChannelRelation.setUecId(userExclusiveChannel.getUecId());
                userExclusiveChannelRelation.setStatus(BusinessConstant.STATUS_VALID);
                userExclusiveChannelRelation.setCreateTime(new Date());

                userExclusiveChannelService.doSaveUserExclusiveChannelRelation(userExclusiveChannelRelation);
            }
        }

        // 添加扩展信息
        UserExtendInfo userExtendInfo = new UserExtendInfo(user.getUserId());
        if(userCreateThirdpartyDto.isGiftPackage()){
            userExtendInfo.setIsGiftPackage(1);
        }else{
            userExtendInfo.setIsGiftPackage(0);
        }
        userExtendInfoMapper.insertUserExtendInfo(userExtendInfo);

        // 记录注册日志
        //OperateLogUtil.opreateLog(request, user, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SYS_REG, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_C, null, null, null);

        return BeanUtils.copyProperties(user,UserBO.class);
    }

    @Override
    public Integer queryUserProLevel(UserBO user){
        user.setProLevel(0);
        if(user.getProUserValidEndTime() == null || user.getProUserValidEndTime().getTime() < System.currentTimeMillis()){
            return 0;
        }else{
            int packageId = orderService.queryProPackageId(user.getUserId());
            if(packageId  > 0){
                ProductPackage productPackage = productPackageService.queryProductPackageById(packageId);
                if(productPackage != null){
                    if(productPackage.getProductPackageId() == 23){
                        user.setProLevel(1);
                    }else if(productPackage.getProductPackageId() == 24){
                        user.setProLevel(2);
                    }else if(productPackage.getProductPackageId() == 25){
                        user.setProLevel(3);
                    }else if(productPackage.getProductPackageId() == 26){
                        user.setProLevel(4);
                    }
                }
            }
            return user.getProLevel();
        }
    }

    @Override
    public boolean doModifyLastLoginTime(int userId) {
        return userMapper.updateLastLoginTime(userId);
    }

    @Override
    public User findUserByThirdpartyccount(String thirdpartyAccount) {
        return userMapper.findUserByThirdpartyccount(thirdpartyAccount);
    }

    @Override
    public AuthJumpRelation findAccessRecords(String userId) {
        return userMapper.findAccessRecords(userId);
    }

    @Override
    public boolean deleteAccessRecords(Integer userId, Integer keywordId) {
        return userMapper.deleteAccessRecords(userId,keywordId);
    }
}
