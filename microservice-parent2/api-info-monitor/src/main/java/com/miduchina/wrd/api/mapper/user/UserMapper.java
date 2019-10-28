package com.miduchina.wrd.api.mapper.user;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.h5.AuthJumpRelation;
import com.miduchina.wrd.po.user.User;
import com.miduchina.wrd.po.user.UserRegRewardRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: UserMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/17 2:53 PM
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户列表
     * @see #getListUser(JSONObject)
     * @param params 热门现象
     * @return List<User>
     * */
    List<User> getListUser(JSONObject params);

    /**
     * 插入用户
     * @param params
     * @return Boolean
     */
    Boolean insertUser(JSONObject params);

    /**
     * 更新用户
     * @param params
     * @return Boolean
     */
    Boolean updateUser(JSONObject params);

    /**
     * 获取用户注册赠送的微积分
     * @param params
     * @return
     */
    List<UserRegRewardRecord> selectRegisterCreditByMobile(JSONObject params);

    /**
     * 查询同一ip下用户数
     *
     * @param ip
     * @return
     */
    Integer selectSameIpUserCount(String ip);

    /**
     * 查询用户名是否已存在
     *
     * @param username
     * @return boolean
     */
    boolean selectUsernameExists(String username);

    /**
     * 根据用户ID获取用户
     *
     * @param userId
     * @return User
     */
    User selectUserByUserId(Integer userId);

    /**
     * 根据用户ID获取有效用户
     *
     * @param userId
     * @return User
     */
    User selectValidUserById(Integer userId);

    /**
     * 根据username取获取有效用户
     *
     * @param username
     * @return User
     */
    User selectValidUserByUsername(String username);

    /**
     * 修改最后一次登录时间
     *
     * @param userId
     * @return boolean
     */
    boolean updateLastLoginTime(int userId);

    User findUserByThirdpartyccount(String thirdpartyAccount);

    AuthJumpRelation findAccessRecords(String userId);

    boolean deleteAccessRecords(@Param("userId")Integer userId, @Param("keywordId")Integer keywordId);
}
