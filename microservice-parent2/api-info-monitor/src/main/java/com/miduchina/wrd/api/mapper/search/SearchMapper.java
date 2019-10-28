package com.miduchina.wrd.api.mapper.search;

import com.xd.tools.pojo.db.mysql.wyq.weiyuqing.H5SearchShare;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 10:26
 */
@Mapper
public interface SearchMapper {
    Boolean saveH5SearchShare(H5SearchShare share);
    Boolean updateH5SearchShare(H5SearchShare share);
    List<H5SearchShare> findH5SearchShare(String shareCode);
}
