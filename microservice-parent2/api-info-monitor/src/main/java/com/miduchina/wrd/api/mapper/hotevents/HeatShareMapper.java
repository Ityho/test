package com.miduchina.wrd.api.mapper.hotevents;

import com.miduchina.wrd.po.hotspot.HeatShare;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther Administrator
 * @vreate 2019-06 11:43
 */
@Mapper
public interface HeatShareMapper {

    HeatShare findHs(String shareCode);
}
