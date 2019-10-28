package com.miduchina.wrd.api.service.analysis.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.analysis.AnalysisSampleMapper;
import com.miduchina.wrd.api.service.analysis.ClientAnalysisSampleService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.analysis.AnalysisSample;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by shitao on 2019-05-14 20:32.
 *
 * @author shitao
 */
@Service
public class ClientAnalysisSampleServiceImpl implements ClientAnalysisSampleService {


    @Resource
    private AnalysisSampleMapper analysisSampleMapper;

    @Override
    public AnalysisSample getSample(JSONObject params) {

        List<AnalysisSample> userList = analysisSampleMapper.getListSample(params);
        if (CollectionUtils.isNotEmpty(userList)){
            return userList.get(0);
        }
        return new AnalysisSample();
    }

    @Override
    public PageInfo<AnalysisSample> getListSample(JSONObject params){
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> analysisSampleMapper.getListSample(params));
    }



}
