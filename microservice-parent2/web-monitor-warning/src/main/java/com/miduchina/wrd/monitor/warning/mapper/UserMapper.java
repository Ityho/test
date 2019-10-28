package com.miduchina.wrd.monitor.warning.mapper;

import com.miduchina.wrd.po.user.User;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: UserMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13
 */
public interface UserMapper {

    /**
     * 查询单个用户
     * @see #findById(Integer)
     * @param id 条件
     * @return User
     * */
    User findById(@Param("id") Integer id);
}
