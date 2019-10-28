package com.miduchina.wrd.api.mapper.log;

import com.miduchina.wrd.po.log.SignInLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: SignInLogMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:44 PM
 */
@Mapper
public interface SignInLogMapper {

    SignInLog selectLastSignInLog(Integer userId);
}
