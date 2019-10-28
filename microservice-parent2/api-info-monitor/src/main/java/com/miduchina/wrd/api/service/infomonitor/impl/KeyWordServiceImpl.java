package com.miduchina.wrd.api.service.infomonitor.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.infomonitor.KeyWordMapper;
import com.miduchina.wrd.api.service.infomonitor.KeyWordService;
import com.miduchina.wrd.dto.log.LoginLog;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.po.keyword.KeyWordLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class KeyWordServiceImpl implements KeyWordService {

    @Resource
    private KeyWordMapper keyWordMapper;

    @Override
    public KeyWord getKeyWordById(Integer id) {
        Map<String,Object> map=new HashMap<>();
        map.put("keywordId",id);
        JSONObject parma=new JSONObject(map);
        List<KeyWord> keyWordList=keyWordMapper.selectKeyWord(parma);
        if(keyWordList!=null && keyWordList.size()>0){
            return keyWordList.get(0);
        }
        return null;
    }

    @Override
    public List<KeyWord> getKeyWordByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        JSONObject parma=new JSONObject(map);
        return keyWordMapper.selectKeyWord(parma);
    }

    @Override
    public LoginLog getLoginLogByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        JSONObject parma=new JSONObject(map);
        List<LoginLog> loginLoglist=keyWordMapper.selectOneLoginLog(parma);
        if (loginLoglist.size() > 0){
            return loginLoglist.get(0);
        }
        return null;
    }

    @Override
    public List<KeyWord> selectKeyWordByUserTime(Integer userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        JSONObject parma=new JSONObject(map);
        return keyWordMapper.selectKeyWordByUserTime(parma);
    }

    @Override
    public Boolean saveKeyWordLog(KeyWordLog keyWordLog,Integer type) {
        boolean bl=true;
        if (!isExistV2(keyWordLog,type)) {
            bl= keyWordMapper.saveKeyWordLog(keyWordLog);
        } else {
            bl=keyWordMapper.updateKeyWordLog(keyWordLog);
        }
        return bl;
    }
    public boolean isExistV2(KeyWordLog keyWordLog,int type) {
        int count=keyWordMapper.selectKeyWordLog(keyWordLog,type);
        if (count>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public PageInfo<KeyWord> getRenewKeywordList(Integer userId, Integer page, Integer pageSize, Integer expiredCondition){

        return  PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> keyWordMapper.selectRenewKeywordList(userId,expiredCondition));
    }

    @Override
    public List<KeyWord> queryValidKeywords(Integer userId) {
        return keyWordMapper.selectValidKeywords(userId);
    }

}
