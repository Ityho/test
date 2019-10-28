package com.miduchina.wrd.api.rankinglist.service;

import com.miduchina.wrd.po.ranking.HotLabel;
import java.util.List;

/**
 * @ClassName: HotLabelService
 * @Description: 标签业务接口类
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:12:54
 */
public interface HotLabelService {

    /**
     * 查询标签列表
     * @see #findAllHotLabel(Integer)
     * @param showTag 显示
     * @return List<HotLabel>
     * */
    List<HotLabel> findAllHotLabel(Integer showTag);

    /**
     * 查询标签分组后对象
     * @see #findAllHotLabelGroupByLevel(Integer)
     * @param showTag 显示
     * @return HotLabel
     * */
    HotLabel findAllHotLabelGroupByLevel(Integer showTag);
}
