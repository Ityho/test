package com.miduchina.wrd.api.service.infomonitor;

import com.miduchina.wrd.po.ranking.Notice;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 11:14
 */
public interface WarningService {
    List<Notice> getNotice();
}
