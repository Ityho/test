package com.miduchina.wrd.api.mapper.hotevents;

import com.miduchina.wrd.po.hotspot.HotCall;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther Administrator
 * @vreate 2019-06 13:48
 */
@Mapper
public interface HotCallMapper {
    boolean saveHotCall(HotCall hotCall);
}
