package com.miduchina.wrd.api.mapper.infomonitor;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.dto.log.LoginLog;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.po.keyword.KeyWordLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by shitao on 2019-05-09 20:52.
 *
 * @author shitao
 */
@Mapper
public interface KeyWordMapper {
    /**
     *
     *查询keyword
     * */
    List<KeyWord> selectKeyWord(JSONObject params);

    /**
     *
     *查询keyword排除过期的
     * */
    List<KeyWord> selectKeyWordByUserTime(JSONObject params);

    List<LoginLog> selectOneLoginLog(JSONObject params);

    boolean saveKeyWordLog(KeyWordLog keyWordLog);

    boolean updateKeyWordLog(KeyWordLog keyWordLog);

    int selectKeyWordLog(KeyWordLog keyWordLog, int type);

    List<KeyWord> selectValidKeywords(Integer userId);

    List<KeyWord> selectRenewKeywordList(Integer userId, Integer expiredCondition);
}
