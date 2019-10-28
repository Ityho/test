package com.miduchina.wrd.api.service.csadmin;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.po.csadmin.CsTestUser;

import java.util.Map; /**
 * @auther yho
 * @vreate 2019-09 17:05
 */
public interface TestUserService {
    boolean saveTestUser(Map<String, Object> param);

    PageInfo<CsTestUser> findAllTestList(Map<String, Object> param);

    boolean doDelete(Map<String, Object> param);

    PageInfo<CsTestUser> findTestUserByParam(Map<String, Object> param);

    boolean doDeleteUser(Map<String, Object> param);
}
