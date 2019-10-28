package com.miduchina.wrd.eventanalysis.service.impl;

import com.miduchina.wrd.dto.user.FavoriteNews;
import com.miduchina.wrd.eventanalysis.base.BaseService;
import com.miduchina.wrd.eventanalysis.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl extends BaseService implements FavoriteService {
    @Override
    public List<FavoriteNews> getFavoriteNewsList(int userId, int type, int order) {
//        Map<String,Object> params = WyqDataConfig.getDataInitMap(userId);
//        params.put("page", 1);
//        params.put("pagesize", 1000);
//        params.put("date", -1);
//        params.put("featuresType", 1);
//        params.put("directory", 0);
//        params.put("order", order);
//        String jsonStr = HttpRequestUtils.sendGet(SysConfig.cfgMap.get("API_BRIEF_API_URL")+"WYQ_MATERIAL_FIND_API", params);
//        Map<String, Object> map=new HashMap<String, Object>();
//        map.put("userEncode", CommonUtils.getUserEncodeNew(userId));
//        map.put("platformTag", "wyq");
//        map.put("directory", "0");
//        map.put("directoryType", "1");
//        String jsonStr=Utils.sendIntraBusinessAPIPOST(Utils.getRequest(), "briefTotalSc", map);
//        if (StringUtils.isNotBlank(jsonStr)) {
//            IContentCommonNetView icns = JSON.parseObject(jsonStr, IContentCommonNetView.class);
//            focusCount = icns.getTotalCount();
//        }
//        IContentCommonNetView icns = JSON.parseObject(jsonStr, IContentCommonNetView.class);
        List<FavoriteNews> list = new ArrayList<FavoriteNews>();
//        if (icns != null && icns.getTotalCount() > 0) {
//            for (IContentCommonNet icn : icns.getiContentCommonNetList()) {
//                FavoriteNews fn = new FavoriteNews();
//                //fn.setId(icn.getId());
//                fn.setNewsSeId(icn.getId());
//                fn.setNewsAuthor(icn.getAuthor());
//                fn.setNewsSourceType(icn.getOriginType());
//                fn.setNewsTitleHash(icn.getTitleHs());
//                fn.setNewsUrl(icn.getWebpageUrl());
//                fn.setNewsSourceWebsite(icn.getCaptureWebsiteName());
//                fn.setNewsTitle(icn.getTitle());
//                fn.setNewsContent(icn.getContent());
//                fn.setNewsforwarderContent(icn.getForwarderContent());
//                fn.setNewsSummary(icn.getSummary());
//                fn.setNewsPublishTime(icn.getPublished());
//                list.add(fn);
//            }
//        }
        return list;
    }
}
