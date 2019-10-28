package com.miduchina.wrd.api.mapper.bigdatareport;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.dto.bigdata.BigReportVo;
import com.miduchina.wrd.po.bigdata.OperationAdminWb;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: BigDataMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/9 3:44 PM
 */
@Mapper
public interface BigDataMapper {

    /**
     * 查询大数据报告列表
     * @see #findAll(JSONObject)
     * @param params 条件
     * @return List<HotIncident>
     * */
    List<OperationAdminWb> findAll(JSONObject params);

    List<OperationAdminWb> findHotEventByNameTypePage(JSONObject params);

    List<OperationAdminWb> selectOne(JSONObject params);

    List<BigReportVo> queryBuyedBigData(JSONObject jsonObject);

    List<BigReportVo> queryBuyedBigDataByUserId(JSONObject params);

    Boolean deletReport(JSONObject jsonParams);
}
