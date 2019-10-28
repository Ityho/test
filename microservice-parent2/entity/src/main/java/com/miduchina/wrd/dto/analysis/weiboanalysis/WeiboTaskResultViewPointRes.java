package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;

@Data
public class WeiboTaskResultViewPointRes {
    private String code;
    private String message;
    private List<WeiboTaskResultViewPointVO> viewPointList;

}
