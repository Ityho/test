package com.miduchina.wrd.api.service.infomonitor;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.dto.log.LoginLog;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.po.keyword.KeyWordLog;

import java.util.List;

/**
 * Created by shitao on 2019-05-09 20:58.
 *
 * @author shitao
 */
public interface KeyWordService {
    /**
     * 获取单个关键词
     * @see #getKeyWordById(Integer)
     * @param id 查询参数
     * */
    KeyWord getKeyWordById(Integer id);
    /**
     * 获取关键词列表
     * @param userId 查询参数
     * */
    List<KeyWord> getKeyWordByUserId(Integer userId);

    /**
     * 获取登录渠道
     * @param userId
     * @return
     */
    LoginLog getLoginLogByUserId(Integer userId);

    /**
     * 获取关键词列表，排除过期的
     * @param userId
     * @return
     */
    List<KeyWord> selectKeyWordByUserTime(Integer userId);

    Boolean saveKeyWordLog(KeyWordLog oer, Integer type);

    /**
     * 获取待续费监测方案
     * @param userId
     * @param page
     * @param pagesize
     * @param expiredCondition
     * @return
     */
    PageInfo<KeyWord> getRenewKeywordList(Integer userId, Integer page, Integer pagesize, Integer expiredCondition);

    /**
     * 获取有效监测方案列表
     *
     * @param userId
     * @return
     */
    List<KeyWord> queryValidKeywords(Integer userId);
}
