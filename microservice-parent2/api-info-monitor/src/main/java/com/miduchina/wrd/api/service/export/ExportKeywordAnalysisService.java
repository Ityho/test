package com.miduchina.wrd.api.service.export;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.keyword.ExportKeywordAnalysisCondition;
import com.miduchina.wrd.po.keyword.ExportKeywordAnalysisTask;

/**
 * @auther Administrator
 * @vreate 2019-05 16:39
 */
public interface ExportKeywordAnalysisService {
    ExportKeywordAnalysisCondition findConditionId(String conditionId);
    Boolean insertExportKeywordAnalysisCondition(ExportKeywordAnalysisCondition oer);



    ExportKeywordAnalysisCondition getCondition(JSONObject params);
    PageInfo<ExportKeywordAnalysisCondition> getListCondition(JSONObject params);
    Boolean insertCondition(JSONObject params);
    Boolean updateCondition(JSONObject params);


    ExportKeywordAnalysisTask getTask(JSONObject params);
    PageInfo<ExportKeywordAnalysisTask> getListTask(JSONObject params);
    Boolean insertTask(JSONObject params);
    Boolean updateTask(JSONObject params);





}
