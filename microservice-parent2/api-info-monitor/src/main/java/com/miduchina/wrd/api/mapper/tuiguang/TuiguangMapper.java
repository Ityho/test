package com.miduchina.wrd.api.mapper.tuiguang;

import com.miduchina.wrd.po.tuiguang.Tuiguang;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther Administrator
 * @vreate 2019-05 10:26
 */
@Mapper
public interface TuiguangMapper {
    Boolean insert(Tuiguang tuiguang);
}
