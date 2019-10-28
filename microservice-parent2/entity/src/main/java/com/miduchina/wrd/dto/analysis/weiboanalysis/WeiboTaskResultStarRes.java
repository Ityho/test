package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;

@Data
public class WeiboTaskResultStarRes {
    private String code;
    private String message;
    private List<List<Leaf>> leafList;

}

