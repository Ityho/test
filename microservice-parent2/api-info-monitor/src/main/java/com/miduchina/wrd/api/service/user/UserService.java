package com.miduchina.wrd.api.service.user;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.bo.user.UserBO;
import com.miduchina.wrd.bo.user.UserThirdpartyBO;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.ip.IpInfoDto;
import com.miduchina.wrd.dto.user.UserCreateDto;
import com.miduchina.wrd.dto.user.UserCreateThirdpartyDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.dto.user.UserThirdpartyDto;
import com.miduchina.wrd.po.h5.AuthJumpRelation;
import com.miduchina.wrd.po.user.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @version v1.0.0
 * @ClassName: UserService
 * @Description: 用户业务处理接口
 * @author: sty
 * @date: 2019/7/17 2:51 PM
 */
public interface UserService {

    /**
     * 获取单个用户
     * @see #getUser(JSONObject)
     * @param params 查询参数
     * @return User
     * */
    User getUser(JSONObject params);


    /**
     * 获取List用户
     * @see #getListUser(JSONObject)
     * @param params 查询参数
     * @return PageInfo<User>
     * */
    PageInfo<User> getListUser(JSONObject params);

    /**
     * 插入用户
     * @see #insertUser(JSONObject)
     * @param params
     * @return Boolean
     */
    Boolean insertUser(JSONObject params);


    /**
     * 更新用户
     * @see #updateUser(JSONObject)
     * @param params
     * @return Boolean
     */
    Boolean updateUser(JSONObject params);
    /**
     * 查询同一ip下用户数
     *
     * @param ip
     * @return
     */
    Integer querySameIpUserCount(String ip);

    /**
     * 查询用户名是否已存在
     *
     * @param username
     * @return
     */
    boolean queryUsernameExists(String username);

    /**
     * 根据用户ID获取用户
     *
     * @param userId
     * @return
     */
    User queryUserByUserId(Integer userId);

    /**
     * 根据用户ID获取有效用户
     *
     * @param userId
     * @return
     */
    User queryValidUserByUserId(int userId);

    /**
     * 获取套餐等级
     *
     * @param user
     * @return
     */
    Integer queryUserProLevel(UserBO user);

    /**
     * 用户登录
     *
     * @param request
     * @param username
     * @param password
     * @param vchecked
     * @param ipInfo
     * @return
     */
    BaseDto<UserDto> doLogin(HttpServletRequest request, String username, String password, Boolean vchecked, IpInfoDto ipInfo);

    /**
     * 注册用户
     *
     * @param request
     * @param userCreateDto
     * @return
     */
    UserBO doRegister(HttpServletRequest request, UserCreateDto userCreateDto);

    /**
     * 第三方用户登录
     *
     * @param request
     * @param user
     * @param userCreateThirdpartyDto
     * @return
     */
    BaseDto<UserThirdpartyDto> doLoginThirdparty(HttpServletRequest request, UserBO user, UserCreateThirdpartyDto userCreateThirdpartyDto, IpInfoDto ipInfo);

    /**
     * 第三方用户注册
     *
     * @param request
     * @param userCreateThirdpartyDto
     * @return
     */
    UserBO doRegisterThirdparty(HttpServletRequest request, UserCreateThirdpartyDto userCreateThirdpartyDto);

    /**
     * 修改最后登录日期
     *
     * @param userId
     * @return
     */
    boolean doModifyLastLoginTime(int userId);

    User findUserByThirdpartyccount(String thirdpartyAccount);

    AuthJumpRelation findAccessRecords(String userId);

    boolean deleteAccessRecords(Integer userId, Integer keywordId);
}
