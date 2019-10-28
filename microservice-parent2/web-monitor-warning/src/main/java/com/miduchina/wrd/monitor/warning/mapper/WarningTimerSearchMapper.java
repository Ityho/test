package com.miduchina.wrd.monitor.warning.mapper;

import com.miduchina.wrd.po.keyword.WarningTimerSearchResult;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @ClassName: WarningTimerSearchMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13
 */
public interface WarningTimerSearchMapper {

    /**
     * 查询预警列表
     * @see #findWTSRByCode(String)
     * @param reviewCode 预警code
     * @return  List<WarningTimerSearchResult>
     * */
    List<WarningTimerSearchResult> findWTSRByCode(@Param("reviewCode")String reviewCode);

    /**
     * 修改预警是否读过
     * @see #updateWarningTimerSearchResult(String)
     * @param reviewCode 条件
     * */
    void updateWarningTimerSearchResult(@Param("reviewCode")String reviewCode);
}
