package com.miduchina.wrd.api.mapper.user;

import com.miduchina.wrd.po.user.SubUserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: SubUserInfoMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:49 AM
 */
@Mapper
public interface SubUserInfoMapper {

    SubUserInfo selectSubUserInfoByUsernameAndPassword(String username, String password);
}
