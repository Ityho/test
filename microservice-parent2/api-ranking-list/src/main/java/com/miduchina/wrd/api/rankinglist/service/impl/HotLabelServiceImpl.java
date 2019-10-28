package com.miduchina.wrd.api.rankinglist.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.miduchina.wrd.api.rankinglist.mapper.HotLabelMapper;
import com.miduchina.wrd.api.rankinglist.service.HotLabelService;
import com.miduchina.wrd.api.rankinglist.util.CommonUtils;
import com.miduchina.wrd.api.rankinglist.util.JedisUtil;
import com.miduchina.wrd.po.ranking.HotLabel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: HotLabelServiceImpl
 * @Description: 标签业务接口类
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:13:23
 */
@Service
//@CacheConfig(cacheNames = "hotLabel")
public class HotLabelServiceImpl implements HotLabelService{

    @Resource
    private HotLabelMapper hotLabelMapper;

//    @Cacheable(key = "#root.methodName+'['+#showTag+']'")
    @Override
    public HotLabel findAllHotLabelGroupByLevel(Integer showTag) {
        List<HotLabel> hotLabels=null;
        String redisKey = "findAllHotLabelGroupByLevel"+showTag;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes==null){
            hotLabels = hotLabelMapper.findAllByShowTag(showTag);
        }else {
            HttpServletRequest request = requestAttributes.getRequest();
            String ip = CommonUtils.getIp(request);
            String jsonStr = JedisUtil.getAttribute(redisKey, ip);
            if (jsonStr!=null){
                hotLabels = JSONObject.parseObject(jsonStr, new TypeReference<List<HotLabel>>() {});
            }else {
                hotLabels = hotLabelMapper.findAllByShowTag(showTag);
                JedisUtil.setAttribute(redisKey, JSON.toJSONString(hotLabels),30*60,ip);
            }
        }
        HotLabel all = new HotLabel();
        all.setId(1);
        if(CollectionUtils.isNotEmpty(hotLabels)) {
            getHotLabel(all,hotLabels);
        }
        return all;
    }

//    @Cacheable(key = "#root.methodName+'['+#showTag+']'")
    @Override
    public List<HotLabel> findAllHotLabel(Integer showTag) {
        List<HotLabel> hotLabels=null;
        String redisKey="findAllHotLabel"+showTag;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes==null){
            hotLabels = hotLabelMapper.findAllByShowTag(showTag);
        }else {
            HttpServletRequest request = requestAttributes.getRequest();
            String ip = CommonUtils.getIp(request);
            String jsonStr = JedisUtil.getAttribute(redisKey, ip);
            if (jsonStr!=null){
                hotLabels = JSONObject.parseObject(jsonStr,new TypeReference<List<HotLabel>>(){});
            }else {
                hotLabels = hotLabelMapper.findAllByShowTag(showTag);
                JedisUtil.setAttribute(redisKey,JSON.toJSONString(hotLabels),30*60,ip);
            }
        }

        return hotLabels;
    }

    private void getHotLabel(HotLabel hotLabel,List<HotLabel> hotLabels) {
        List<HotLabel> childrens = new ArrayList<>();
        for (HotLabel hl : hotLabels) {
            if(hotLabel.getId().intValue() == hl.getParentId().intValue()) {
                getHotLabel(hl,hotLabels);
                childrens.add(hl);
            }
        }
        hotLabel.setChildrens(childrens);
    }
}
