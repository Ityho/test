package com.miduchina.wrd.api.mapper.heatanalysis;

import com.miduchina.wrd.po.home.HeatShare;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther Administrator
 * @vreate 2019-05 16:43
 */
@Mapper
public interface HeatMapper {
    HeatShare findShareCode(String shareCode);
}
