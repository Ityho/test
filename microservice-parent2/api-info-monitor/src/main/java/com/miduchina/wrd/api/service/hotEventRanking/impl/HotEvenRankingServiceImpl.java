package com.miduchina.wrd.api.service.hotEventRanking.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.ranking.RankingMapper;
import com.miduchina.wrd.api.service.hotEventRanking.HotEventRankingService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.ranking.OperationAdminHot;
import com.miduchina.wrd.po.ranking.OperationAdminHotContent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HotEvenRankingServiceImpl implements HotEventRankingService {

    @Resource
    private RankingMapper rankingMapper;

    @Override
    public PageInfo<OperationAdminHotContent> getList(JSONObject params) {
//        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
//        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
//        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> rankingMapper.list(params));
        PageInfo info = new PageInfo();
        info.setList(rankingMapper.list(params));
        return info;
    }

    @Override
    public Boolean insert(JSONObject params) {
        return rankingMapper.insert(params);
    }

    @Override
    public Boolean update(JSONObject params) {
        return rankingMapper.update(params);
    }

    @Override
    public PageInfo<OperationAdminHot> getHotList(JSONObject params) {
        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        PageInfo info = new PageInfo();
        info.setList(rankingMapper.hotList(params));
        return info;
//
//        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> rankingMapper.hotList(params));
    }
}
