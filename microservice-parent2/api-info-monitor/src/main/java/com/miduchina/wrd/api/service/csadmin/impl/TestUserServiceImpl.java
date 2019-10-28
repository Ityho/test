package com.miduchina.wrd.api.service.csadmin.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.csadmin.TestUserMapper;
import com.miduchina.wrd.api.service.csadmin.TestUserService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.po.csadmin.CsTestUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @auther yho
 * @vreate 2019-09 17:06
 */
@Service
public class TestUserServiceImpl implements TestUserService {
    @Resource
    private TestUserMapper testUserMapper;
    @Override
    public boolean saveTestUser(Map<String, Object> param) {
        return testUserMapper.saveTestUser(param);
    }

    @Override
    public PageInfo<CsTestUser> findAllTestList(Map<String, Object> param) {
        Integer page = param.get("page") == null ? BusinessConstant.DEFAULT_PAGE : Integer.valueOf(String.valueOf(param.get("page")));
        Integer pageSize = param.get("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE : Integer.valueOf(String.valueOf(param.get("pageSize")));
        PageHelper.startPage(page, pageSize);
        List<CsTestUser> list=testUserMapper.findAllTestList(param);
        PageInfo<CsTestUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public boolean doDelete(Map<String, Object> param) {

        return testUserMapper.doDelete(param);
    }

    @Override
    public PageInfo<CsTestUser> findTestUserByParam(Map<String, Object> param) {
        Integer page = param.get("page") == null ? BusinessConstant.DEFAULT_PAGE : Integer.valueOf(String.valueOf(param.get("page")));
        Integer pageSize = param.get("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE : Integer.valueOf(String.valueOf(param.get("pageSize")));
        PageHelper.startPage(page, pageSize);
        List<CsTestUser> list=testUserMapper.findTestUserByParam(param);
        PageInfo<CsTestUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public boolean doDeleteUser(Map<String, Object> param) {
        return testUserMapper.doDeleteUser(param);
    }
}
