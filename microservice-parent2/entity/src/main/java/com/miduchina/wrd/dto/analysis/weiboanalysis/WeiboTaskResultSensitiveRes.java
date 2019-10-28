package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class WeiboTaskResultSensitiveRes {
    private String code;
    private String message;
    private WeiboTaskResultSensitiveVO sensitive;
}
