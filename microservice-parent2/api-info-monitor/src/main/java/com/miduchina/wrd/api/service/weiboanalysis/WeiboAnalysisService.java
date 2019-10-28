package com.miduchina.wrd.api.service.weiboanalysis;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.analysis.weiboanalysis.WeiBoAnalysisTask;

import java.util.List;

/**
 * Created by shitao on 2019-05-23 16:01.
 *
 * @author shitao
 */
public interface WeiboAnalysisService {
    /**
     * 取出最新一条
     * @param params
     * @return
     */
    List<WeiBoAnalysisTask> getLatestWeibo(JSONObject params);
    /**
     * h5微分析任务列表
     * @param params
     * @return
     */
    List<WeiBoAnalysisTask> getH5TaskList(JSONObject params);
    /**
     * 获取单个任务
     * @param params
     * @return
     */
    WeiBoAnalysisTask getTask(JSONObject params);


    /**
     * 获取任务列表
     * @param params
     * @return
     */
    PageInfo<WeiBoAnalysisTask> getTasks(JSONObject params);


    /**
     * 插入任务
     * @param params
     * @return
     */
    Boolean insertTask(JSONObject params);


    /**
     * 更新任务
     * @param params
     * @return
     */
    Boolean updateTask(JSONObject params);


    List<WeiBoAnalysisTask> queryWeiboExistsByUrl(JSONObject jsonObject);
}
