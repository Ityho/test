package com.miduchina.wrd.api.service.bigdatareport;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.dto.bigdata.BigReportVo;
import com.miduchina.wrd.po.bigdata.OperationAdminWb;

import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: BigDataService
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/9 3:41 PM
 */
public interface BigDataService {

    /**
     * 查询大数据列表
     * @see #list(JSONObject)
     * @param params 查询参数
     * @return PageInfo<HotIncident>
     * */
    PageInfo<OperationAdminWb> list(JSONObject params);

    OperationAdminWb queryOne(JSONObject params);

    PageInfo<OperationAdminWb> listByType(JSONObject params);

    PageInfo<OperationAdminWb> findHotEventByNameTypePage(JSONObject jsonParams);

    List<BigReportVo> queryBuyedBigData(JSONObject jsonObject);

    PageInfo<BigReportVo> queryBuyedBigDataByUserId(JSONObject jsonParams);

    Boolean deletReport(JSONObject jsonParams);
}
