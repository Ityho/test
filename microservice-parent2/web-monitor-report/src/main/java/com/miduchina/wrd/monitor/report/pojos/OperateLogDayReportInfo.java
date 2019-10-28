package com.miduchina.wrd.monitor.report.pojos;

import lombok.Data;

/**
 * Created by 罗朝州 on 2016/9/5.
 */
@Data
public class OperateLogDayReportInfo {

    private Integer userId;
    private Integer reportId;
    private String shareCode;
    private String sendTime;
}
