package com.miduchina.wrd.api.service.search;

import com.xd.tools.pojo.db.mysql.wyq.weiyuqing.H5SearchShare;

public interface SearchService {


    Boolean saveH5SearchShare (H5SearchShare h5SearchShare);

    Boolean updateH5SearchShare (H5SearchShare h5SearchShare);

    H5SearchShare findH5SearchShare (String share);


}
