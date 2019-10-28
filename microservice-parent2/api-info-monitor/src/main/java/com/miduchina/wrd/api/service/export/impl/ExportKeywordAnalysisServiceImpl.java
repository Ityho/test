package com.miduchina.wrd.api.service.export.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.export.ExportKeywordAnalysisMapper;
import com.miduchina.wrd.api.service.export.ExportKeywordAnalysisService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.keyword.ExportKeywordAnalysisCondition;
import com.miduchina.wrd.po.keyword.ExportKeywordAnalysisTask;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 16:43
 */
@Service
public class ExportKeywordAnalysisServiceImpl implements ExportKeywordAnalysisService {
    @Resource
    private ExportKeywordAnalysisMapper exportKeywordAnalysisMapper;

    @Override
    public ExportKeywordAnalysisCondition findConditionId(String conditionId) {
        return exportKeywordAnalysisMapper.findConditionId(conditionId);
    }

    @Override
    public Boolean insertExportKeywordAnalysisCondition(ExportKeywordAnalysisCondition oer){
        return exportKeywordAnalysisMapper.insertExportKeywordAnalysisCondition(oer);
    }



    @Override
    public ExportKeywordAnalysisCondition getCondition(JSONObject params){

        List<ExportKeywordAnalysisCondition> list = exportKeywordAnalysisMapper.getListCondition(params);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return new ExportKeywordAnalysisCondition();
    }

    @Override
    public PageInfo<ExportKeywordAnalysisCondition> getListCondition(JSONObject params){
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> exportKeywordAnalysisMapper.getListCondition(params));
    }
    @Override
    public Boolean insertCondition(JSONObject params){
        return exportKeywordAnalysisMapper.insertCondition(params);
    }
    @Override
    public Boolean updateCondition(JSONObject params){
        return exportKeywordAnalysisMapper.updateCondition(params);
    }

    @Override
    public ExportKeywordAnalysisTask getTask(JSONObject params){
        List<ExportKeywordAnalysisTask> list = exportKeywordAnalysisMapper.getListTask(params);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return new ExportKeywordAnalysisTask();
    }
    @Override
    public PageInfo<ExportKeywordAnalysisTask> getListTask(JSONObject params){
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> exportKeywordAnalysisMapper.getListTask(params));
    }
    @Override
    public Boolean insertTask(JSONObject params){
        return exportKeywordAnalysisMapper.insertCondition(params);
    }
    @Override
    public Boolean updateTask(JSONObject params){
        return exportKeywordAnalysisMapper.updateCondition(params);
    }


}
