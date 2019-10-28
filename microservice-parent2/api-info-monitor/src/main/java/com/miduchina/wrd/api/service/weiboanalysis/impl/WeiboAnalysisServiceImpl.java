package com.miduchina.wrd.api.service.weiboanalysis.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.weiboanalysis.WeiboAnalysisMapper;
import com.miduchina.wrd.api.service.weiboanalysis.WeiboAnalysisService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.analysis.weiboanalysis.WeiBoAnalysisTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by shitao on 2019-05-23 16:02.
 *
 * @author shitao
 */

@Slf4j
@Service
@CacheConfig(cacheNames = "fenxi_weibo")
public class WeiboAnalysisServiceImpl implements WeiboAnalysisService {


    @Resource
    private WeiboAnalysisMapper weiboAnalysisMapper;

    @Override
    public List<WeiBoAnalysisTask> getLatestWeibo(JSONObject params) {
        return weiboAnalysisMapper.getLatestWeibo(params);
    }
    @Override
    public List<WeiBoAnalysisTask> getH5TaskList(JSONObject params) {
        return weiboAnalysisMapper.getListTask(params);
    }

    @Override
    public WeiBoAnalysisTask getTask(JSONObject params) {

        List<WeiBoAnalysisTask> list = weiboAnalysisMapper.getListTask(params);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return new WeiBoAnalysisTask();
    }


    @Override
    public PageInfo<WeiBoAnalysisTask> getTasks(JSONObject params) {


        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> weiboAnalysisMapper.getListTask(params));

    }


    @Override
    public Boolean insertTask(JSONObject params){
        return weiboAnalysisMapper.insertTask(params);
    }

    @Override
    public Boolean updateTask(JSONObject params){
        return weiboAnalysisMapper.updateTask(params);
    }

    @Override
    public List<WeiBoAnalysisTask> queryWeiboExistsByUrl(JSONObject jsonObject) {
        return weiboAnalysisMapper.queryWeiboExistsByUrl(jsonObject);
    }


}
