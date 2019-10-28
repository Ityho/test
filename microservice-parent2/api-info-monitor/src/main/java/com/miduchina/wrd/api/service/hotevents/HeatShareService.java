package com.miduchina.wrd.api.service.hotevents;

import com.miduchina.wrd.po.hotspot.HeatShare;

/**
 * @auther Administrator
 * @vreate 2019-06 11:41
 */
public interface HeatShareService {
    HeatShare findHs(String shareCode);
}
