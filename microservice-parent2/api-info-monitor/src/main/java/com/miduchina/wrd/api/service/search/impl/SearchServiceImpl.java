package com.miduchina.wrd.api.service.search.impl;

import com.miduchina.wrd.api.mapper.search.SearchMapper;
import com.miduchina.wrd.api.service.search.SearchService;
import com.xd.tools.pojo.db.mysql.wyq.weiyuqing.H5SearchShare;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    SearchMapper mapper;

    @Override
    public Boolean saveH5SearchShare(H5SearchShare h5SearchShare) {
        return mapper.saveH5SearchShare(h5SearchShare);
    }

    @Override
    public Boolean updateH5SearchShare(H5SearchShare h5SearchShare) {
        return mapper.updateH5SearchShare(h5SearchShare);
    }

    @Override
    public H5SearchShare findH5SearchShare(String share) {
        List<H5SearchShare> list = mapper.findH5SearchShare(share);
        if(CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return new H5SearchShare();
    }

}
