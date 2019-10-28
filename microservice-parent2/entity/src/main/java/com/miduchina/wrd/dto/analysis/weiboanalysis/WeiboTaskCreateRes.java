package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class WeiboTaskCreateRes {
    private String code;
    private String message;
    private WeiboTaskCreateVO task;
}
