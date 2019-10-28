package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class WeiboTaskStatusRes {
    private boolean newVersion=false;
    private String code;
    private String message;
    private WeiboTaskStatusVO task;


}
