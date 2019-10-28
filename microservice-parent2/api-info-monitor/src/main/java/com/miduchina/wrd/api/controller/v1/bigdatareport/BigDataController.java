package com.miduchina.wrd.api.controller.v1.bigdatareport;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.bigdatareport.BigDataService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.bigdata.BigReportVo;
import com.miduchina.wrd.dto.bigdata.OperationAdminWbDto;
import com.miduchina.wrd.po.bigdata.OperationAdminWb;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0.0
 * @ClassName: BigDataController
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/9 10:24 AM
 */
@Api(tags = {"大数据"})
@RestController
@RequestMapping("api/v1/bigdata")
public class BigDataController {

    @Autowired
    private BigDataService bigDataService;

    @ApiOperation(value = "查询大数据报告列表", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list")
    public PageDto<OperationAdminWbDto> list(@RequestBody String params) {
        PageDto<OperationAdminWbDto> pageDto = new PageDto<>();
        if (StringUtils.isBlank(params)) {
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        JSONObject jsonParams = JSONObject.parseObject(params);
        PageInfo<OperationAdminWb> pageInfo = bigDataService.list(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }

        return pageDto.setList(BeanUtils.copyTo(pageInfo.getList(),OperationAdminWbDto.class))
                .setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setMaxPage(pageInfo.getPages())
                .setTotalCount(pageInfo.getTotal());
    }

    @ApiOperation(value = "根据参数查询大数据报告列表", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门事件") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "findHotEventByNameTypePage", headers = "Accept=application/json")
    public PageDto<OperationAdminWbDto> findHotEventByNameTypePage(@RequestBody String params) {
        PageDto<OperationAdminWbDto> pageDto = new PageDto<>();
        if (StringUtils.isBlank(params)) {
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        JSONObject jsonParams = JSONObject.parseObject(params);
        PageInfo<OperationAdminWb> pageInfo = bigDataService.findHotEventByNameTypePage(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }

        return pageDto.setList(BeanUtils.copyTo(pageInfo.getList(),OperationAdminWbDto.class))
                .setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setMaxPage(pageInfo.getPages())
                .setTotalCount(pageInfo.getTotal());
    }

    @ApiOperation(value = "查询大数据报告列表", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "findBigDataByNameTypePage")
    public PageDto<OperationAdminWbDto> findBigDataByNameTypePage(int page, int pagesize, Integer type,Integer isPackagePrice) {
        if(page == 0){
            page = 1;
        }
        if (pagesize ==0 ){
            pagesize = 15;
        }
        PageDto<OperationAdminWbDto> pageDto = new PageDto<>();
        Map<String ,Object> params=new HashMap<>();
        params.put("page",page);
        params.put("pageSize",pagesize);
        params.put("type",type);
        params.put("isPackagePrice",isPackagePrice);
        JSONObject jsonParams = new JSONObject(params);
        PageInfo<OperationAdminWb> pageInfo = bigDataService.list(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }

        return pageDto.setList(BeanUtils.copyTo(pageInfo.getList(),OperationAdminWbDto.class))
                .setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setMaxPage(pageInfo.getPages())
                .setTotalCount(pageInfo.getTotal());
    }

    @ApiOperation(value = "查询大数据解读详情页", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "queryBigDataDetail")
    public BaseDto<OperationAdminWbDto> queryBigDataDetail(int id) {
        BaseDto baseDto=new BaseDto();
        Map<String,Object> params=new HashMap<>();
        JSONObject jsonObject=new JSONObject(params);
        params.put("id",id);
        if(id!=0){
            OperationAdminWb adminWbDtoList=bigDataService.queryOne(jsonObject);
            if(adminWbDtoList!=null){
                baseDto.setData(adminWbDtoList).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }
        return baseDto;
    }

    @ApiOperation(value = "查询用户已购买的大数据报告", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "queryBuyedBigData")
    public BaseDto queryBuyedBigData(int userId) {
        BaseDto baseDto=new BaseDto();
        Map<String,Object> params=new HashMap<>();
        JSONObject jsonObject=new JSONObject(params);
        params.put("userId",userId);
        if(userId!=0){
            List<BigReportVo> adminWbDtoList=bigDataService.queryBuyedBigData(jsonObject);
            if(adminWbDtoList!=null&&adminWbDtoList.size()>0){
                baseDto.setData(adminWbDtoList).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }
        return baseDto;
    }

    @ApiOperation(value = "根据参数查询已购买大数据报告列表", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "根据参数查询已购买大数据报告列表") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "queryBuyedBigDataByUserId", headers = "Accept=application/json")
    public PageDto<BigReportVo> queryBuyedBigDataByUserId(@RequestBody String params) {
        PageDto<BigReportVo> pageDto = new PageDto<>();
        if (StringUtils.isBlank(params)) {
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        JSONObject jsonParams = JSONObject.parseObject(params);
        PageInfo<BigReportVo> pageInfo = bigDataService.queryBuyedBigDataByUserId(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }

        return pageDto.setList(BeanUtils.copyTo(pageInfo.getList(),BigReportVo.class))
                .setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setMaxPage(pageInfo.getPages())
                .setTotalCount(pageInfo.getTotal());
    }
    @ApiOperation(value = "删除用户已购买的大数据报告", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "deletReport")
    public BaseDto deletReport(@RequestBody String params) {
        BaseDto baseDto=new BaseDto();
        if (StringUtils.isBlank(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        JSONObject jsonParams = JSONObject.parseObject(params);
        Boolean b=bigDataService.deletReport(jsonParams);
        if(b){
            return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
        }else{
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }
    }


}
