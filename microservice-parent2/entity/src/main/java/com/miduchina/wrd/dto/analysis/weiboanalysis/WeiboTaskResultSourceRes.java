package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;
@Data
public class WeiboTaskResultSourceRes {
    private String code;
    private String message;
    private List<WeiboTaskResultSourceVO> sourceList;
}
