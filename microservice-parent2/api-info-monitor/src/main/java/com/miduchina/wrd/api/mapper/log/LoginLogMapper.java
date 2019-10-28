package com.miduchina.wrd.api.mapper.log;

import com.miduchina.wrd.po.log.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: LoginLogMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:35 PM
 */
@Mapper
public interface LoginLogMapper {

    boolean insertLoginLog(LoginLog loginLog);
}
