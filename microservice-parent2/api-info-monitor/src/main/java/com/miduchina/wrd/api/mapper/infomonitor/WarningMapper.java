package com.miduchina.wrd.api.mapper.infomonitor;

import com.miduchina.wrd.po.ranking.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 11:14
 */
@Mapper
public interface WarningMapper {
    List<Notice> getNotice();
}
