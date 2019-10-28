package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class WeiboTaskResultGenderRes {
    private String code;
    private String message;
    private Gender reposts;
    private Gender comments;
}
