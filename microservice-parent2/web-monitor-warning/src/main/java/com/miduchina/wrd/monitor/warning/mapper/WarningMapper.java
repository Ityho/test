package com.miduchina.wrd.monitor.warning.mapper;

import com.miduchina.wrd.po.keyword.Warning;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: WarningMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13
 */
public interface WarningMapper {

    /**
     * 查询单个预警
     * @see #findById(Integer)
     * @param id 条件
     * @return Warning
     * */
    Warning findById(@Param("id") Integer id);
}
