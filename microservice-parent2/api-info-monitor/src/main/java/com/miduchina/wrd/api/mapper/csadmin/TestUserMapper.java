package com.miduchina.wrd.api.mapper.csadmin;

import com.miduchina.wrd.po.csadmin.CsTestUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @auther yho
 * @vreate 2019-09 17:48
 */
@Mapper
public interface TestUserMapper {
    boolean saveTestUser(Map<String, Object> param);

    List<CsTestUser> findAllTestList(Map<String, Object> param);

    boolean doDelete(Map<String, Object> param);

    List<CsTestUser> findTestUserByParam(Map<String, Object> param);

    boolean doDeleteUser(Map<String, Object> param);
}
