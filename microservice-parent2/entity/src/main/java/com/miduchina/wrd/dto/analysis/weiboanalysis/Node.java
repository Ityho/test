package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class Node {
    private String name;
    private Integer value;
    private String id;
    private Integer depth;
    private Integer category;

    private String label;
    private Integer x;
    private Integer y;
    private Integer size;
}
