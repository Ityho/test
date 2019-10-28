package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class Link {
    private String source;
    private String target;
    private Integer weight;

    private String id;
}
