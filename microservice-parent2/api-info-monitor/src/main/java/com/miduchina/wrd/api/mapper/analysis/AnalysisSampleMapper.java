package com.miduchina.wrd.api.mapper.analysis;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.analysis.AnalysisSample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by shitao on 2019-05-14 20:28.
 *
 * @author shitao
 */
@Mapper
public interface AnalysisSampleMapper {

    /**
     * 查询用户列表
     * @see #getListSample(JSONObject)
     * @param params 热门现象
     * @return List<AnalysisSample>
     * */
    List<AnalysisSample> getListSample(JSONObject params);


}
