package com.miduchina.wrd.api.mapper.weiboanalysis;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.analysis.weiboanalysis.WeiBoAnalysisTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by shitao on 2019-05-23 16:07.
 *
 * @author shitao
 */
@Mapper
public interface WeiboAnalysisMapper {


    /**
     * 查询微博传播效果分析列表
     * @see #getListTask(JSONObject)
     * @param params 热门现象
     * @return List<User>
     * */
    List<WeiBoAnalysisTask> getListTask(JSONObject params);

    /**
     * 插入微博传播效果分析
     * @param params
     * @return Boolean
     */
    Boolean insertTask(JSONObject params);

    /**
     * 更新微博传播效果分析
     * @param params
     * @return Boolean
     */
    Boolean updateTask(JSONObject params);


    List<WeiBoAnalysisTask> getLatestWeibo(JSONObject params);

    List<WeiBoAnalysisTask> queryWeiboExistsByUrl(JSONObject jsonObject);
}
