package com.miduchina.wrd.monitor.warning.operatelog.model;

import lombok.Data;

@Data
public class OperateLogWarningInfo {

    private Integer userId;
    private Integer warningId;
    private String reviewCode;
    private String sendTime;
}
