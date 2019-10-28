package com.miduchina.wrd.api.service.analysis;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.analysis.AnalysisSample;

/**
 * Created by shitao on 2019-05-14 20:31.
 *
 * @author shitao
 */
public interface ClientAnalysisSampleService {

    /**
     * 获取单个用户
     * @see #getSample(JSONObject)
     * @param params 查询参数
     * @return AnalysisSample
     * */
    AnalysisSample getSample(JSONObject params);


    /**
     * 获取List用户
     * @see #getListSample(JSONObject)
     * @param params 查询参数
     * @return PageInfo<AnalysisSample>
     * */
    PageInfo<AnalysisSample> getListSample(JSONObject params);


}
