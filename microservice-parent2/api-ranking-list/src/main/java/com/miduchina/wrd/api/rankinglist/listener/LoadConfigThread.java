package com.miduchina.wrd.api.rankinglist.listener;

import com.miduchina.wrd.api.rankinglist.constant.Constants;
import com.miduchina.wrd.api.rankinglist.service.HotIncidentService;
import com.miduchina.wrd.api.rankinglist.service.HotLabelService;
import com.miduchina.wrd.api.rankinglist.util.SpringUtils;
import com.miduchina.wrd.po.ranking.HotLabel;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @ClassName: LoadConfigThread
 * @Description: 加载初始配置
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:12:54
 */
public class LoadConfigThread extends Thread{

    @Override
    public void run() {
        try {
            while (true) {
                HotLabelService hotLabelService = (HotLabelService)SpringUtils.getBean("hotLabelServiceImpl");
                if (hotLabelService != null) {
                    // 加载配置
                    List<HotLabel> hotLabelList = hotLabelService.findAllHotLabel(1);
                    if(CollectionUtils.isNotEmpty(hotLabelList)){
                        for (HotLabel hotLabel : hotLabelList) {
                            Constants.HOT_LABEL_MAP.put(hotLabel.getId(),hotLabel.getName());
                            Constants.HOT_PARENT_ID_MAP.put(hotLabel.getId(),hotLabel.getParentId());
                        }
                    }
                }
                Thread.sleep(600000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
