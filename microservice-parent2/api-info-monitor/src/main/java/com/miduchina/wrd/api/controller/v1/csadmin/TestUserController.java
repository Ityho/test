package com.miduchina.wrd.api.controller.v1.csadmin;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.csadmin.TestUserService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.casebase.WyqCaseDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.csadmin.CsTestUser;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther yho
 * @vreate 2019-09 17:03
 */
@RestController
@RequestMapping("api/v1/csAdminTestUser")
public class TestUserController {
    @Autowired
    private TestUserService testUserService;

    @ApiOperation(value = "添加运营后台测试用户", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "添加测试用户")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "create")
    public BaseDto create(@ApiParam(value = "参数集合") @RequestParam Map<String, Object> param){
        BaseDto baseDto = new BaseDto();
        boolean bl=testUserService.saveTestUser(param);
        if(bl){
            baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }else{
            baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("测试用户创建异常");
        }
        return baseDto;
    }
    @ApiOperation(value = "测试用户列表查询", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "测试用户列表")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "list")
    public PageDto<CsTestUser> list(@ApiParam(value = "参数集合") @RequestParam Map<String, Object> param){
        PageDto<CsTestUser> pageDto = new PageDto();
        PageInfo<CsTestUser> pageInfo=testUserService.findAllTestList(param);
        if(CollectionUtils.isEmpty(pageInfo.getList())) {
            return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
        }
        pageDto.setCode(CodeConstant.SUCCESS_CODE).setData(BeanUtils.copyTo(pageInfo.getList(),CsTestUser.class));
        pageDto.setPageSize(pageInfo.getPageSize());
        pageDto.setPage(pageInfo.getPageNum());
        pageDto.setTotalCount(pageInfo.getTotal());
        pageDto.setMaxPage(pageInfo.getPages());
        return pageDto;
    }

    @ApiOperation(value = "删除测试用户", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "删除测试用户")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "doDelete")
    public BaseDto doDelete(@ApiParam(value = "参数集合") @RequestParam Map<String, Object> param) {
        BaseDto baseDto = new BaseDto();
        boolean bl=testUserService.doDelete(param);
        if(bl){
            baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }else{
            baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("删除测试用户异常");
        }
        return baseDto;

    }
    @ApiOperation(value = "删除wrd用户", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "删除wrd用户")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "doDeleteUser")
    public BaseDto doDeleteUser(@ApiParam(value = "参数集合") @RequestParam Map<String, Object> param) {
        BaseDto baseDto = new BaseDto();
        boolean bl=testUserService.doDeleteUser(param);
        if(bl){
            baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }else{
            baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("删除测试用户异常");
        }
        return baseDto;

    }
    @ApiOperation(value = "查询具体用户", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询具体用户")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "findTestUserByParam")
    public BaseDto findTestUserByParam(@ApiParam(value = "参数集合") @RequestParam Map<String, Object> param) {
        PageDto<CsTestUser> pageDto = new PageDto();
        PageInfo<CsTestUser> pageInfo=testUserService.findTestUserByParam(param);
        if(CollectionUtils.isEmpty(pageInfo.getList())) {
            return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
        }
        pageDto.setCode(CodeConstant.SUCCESS_CODE).setData(BeanUtils.copyTo(pageInfo.getList(),WyqCaseDto.class));
        pageDto.setPageSize(pageInfo.getPageSize());
        pageDto.setPage(pageInfo.getPageNum());
        pageDto.setTotalCount(pageInfo.getTotal());
        pageDto.setMaxPage(pageInfo.getPages());
        return pageDto;

    }

}
