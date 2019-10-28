package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;

@Data
public class WeiboTaskResultViewPointVO {
    private String viewPointName;
    private Integer viewPointCount;
    private List<String> viewPointList;
}
