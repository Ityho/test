package com.miduchina.wrd.api.mapper.export;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.keyword.ExportKeywordAnalysisCondition;
import com.miduchina.wrd.po.keyword.ExportKeywordAnalysisTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 16:43
 */
@Mapper
public interface ExportKeywordAnalysisMapper {
    ExportKeywordAnalysisCondition findConditionId(String conditionId);


    Boolean insertExportKeywordAnalysisCondition(ExportKeywordAnalysisCondition oer);


    /**
     * 数据导出
     * @see #getListTask(JSONObject)
     * @param params 热门现象
     * @return List<ExportKeywordAnalysisTask>
     * */
    List<ExportKeywordAnalysisTask> getListTask(JSONObject params);

    /**
     * 插入导出任务
     * @param params
     * @return Boolean
     */
    Boolean insertTask(JSONObject params);

    /**
     * 更新导出任务
     * @param params
     * @return Boolean
     */
    Boolean updateTask(JSONObject params);

    /**
     * 数据导出条件
     * @see #getListCondition(JSONObject)
     * @param params
     * @return List<ExportKeywordAnalysisCondition>
     * */
    List<ExportKeywordAnalysisCondition> getListCondition(JSONObject params);

    /**
     * 插入数据导出条件
     * @param params
     * @return Boolean
     */
    Boolean insertCondition(JSONObject params);

    /**
     * 更新数据导出条件
     * @param params
     * @return Boolean
     */
    Boolean updateCondition(JSONObject params);


}
