package com.miduchina.wrd.api.mapper.productsanalysis;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.analysis.ProductsAnalysis;
import com.miduchina.wrd.po.analysis.ProductsAnalysisBrief;
import com.miduchina.wrd.po.analysis.ProductsAnalysisShare;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by shitao on 2019-05-30 18:16.
 *
 * @author shitao
 */
@Mapper
public interface ProductsAnalysisMapper {
    /**
     * 竞品分析总数
     * */
    Integer selectCount(JSONObject params);

    /**
     * 竞品list
     * @see #getListTask(JSONObject)
     * @param params 热门现象
     * @return List<ProductsAnalysis>
     * */
    List<ProductsAnalysis> getListTask(JSONObject params);

    /**
     * 插入竞品
     * @param params
     * @return Boolean
     */
    Boolean insertTask(JSONObject params);

    /**
     * 更新竞品
     * @param params
     * @return Boolean
     */
    Boolean updateTask(JSONObject params);

    /**
     *
     * @see #getListTask(JSONObject)
     * @param params
     * @return List<ProductsAnalysisBrief>
     * */
    List<ProductsAnalysisBrief> getListBrief(JSONObject params);

    /**
     * 插入竞品
     * @param params
     * @return Boolean
     */
    Boolean insertBrief(JSONObject params);

    /**
     * 更新竞品
     * @param params
     * @return Boolean
     */
    Boolean updateBrief(JSONObject params);


    /**
     *
     * @see #getListTask(JSONObject)
     * @param params
     * @return List<User>
     * */
    List<ProductsAnalysisShare> getListShare(JSONObject params);

    /**
     * 插入竞品
     * @param params
     * @return Boolean
     */
    Boolean insertShare(JSONObject params);

    /**
     * 更新竞品
     * @param params
     * @return Boolean
     */
    Boolean updateShare(JSONObject params);

    List<ProductsAnalysisBrief> getPABList(JSONObject params);

}
