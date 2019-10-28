package com.miduchina.wrd.api.mapper.heatanalysis;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by shitao on 2019-05-09 17:08.
 *
 * @author shitao
 */
@Mapper
public interface HeatReportMapper {

    /**
     *
     * */
    Integer selectCountHeatReportByUserId(JSONObject params);

}
