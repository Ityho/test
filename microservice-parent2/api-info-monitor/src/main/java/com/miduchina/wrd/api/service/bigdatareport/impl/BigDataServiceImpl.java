package com.miduchina.wrd.api.service.bigdatareport.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.bigdatareport.BigDataMapper;
import com.miduchina.wrd.api.service.bigdatareport.BigDataService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.bigdata.BigReportVo;
import com.miduchina.wrd.po.bigdata.OperationAdminWb;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: BigDataServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/9 3:41 PM
 */
@Service
public class BigDataServiceImpl implements BigDataService{

    @Resource
    private BigDataMapper bigDataMapper;

    @Override
    public PageInfo<OperationAdminWb> list(JSONObject params) {
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return PageHelper.startPage(page, pageSize).doSelectPageInfo(() ->  bigDataMapper.findAll(params));
    }

    @Override
    public OperationAdminWb queryOne(JSONObject params) {
        List<OperationAdminWb> adminWbList=bigDataMapper.selectOne(params);
        if(adminWbList!=null && adminWbList.size()>0){
            return adminWbList.get(0);
        }
        return null;
    }

    @Override
    public PageInfo<OperationAdminWb> listByType(JSONObject params) {
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return PageHelper.startPage(page, pageSize).doSelectPageInfo(() ->  bigDataMapper.findAll(params));
    }

//    @Override
//    public PageInfo<OperationAdminWb> list(int page, int pageSize, int type) {
//        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
//        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
//        return PageHelper.startPage(page, pageSize).doSelectPageInfo(() ->  bigDataMapper.findAll(params));
//    }

    @Override
    public PageInfo<OperationAdminWb> findHotEventByNameTypePage(JSONObject params) {
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return PageHelper.startPage(page, pageSize).doSelectPageInfo(() ->  bigDataMapper.findHotEventByNameTypePage(params));
    }

    @Override
    public List<BigReportVo> queryBuyedBigData(JSONObject jsonObject) {
        return bigDataMapper.queryBuyedBigData(jsonObject);
    }

    @Override
    public PageInfo<BigReportVo> queryBuyedBigDataByUserId(JSONObject params) {
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return PageHelper.startPage(page, pageSize).doSelectPageInfo(() ->  bigDataMapper.queryBuyedBigDataByUserId(params));
    }

    @Override
    public Boolean deletReport(JSONObject jsonParams) {
        return bigDataMapper.deletReport(jsonParams);
    }
}
