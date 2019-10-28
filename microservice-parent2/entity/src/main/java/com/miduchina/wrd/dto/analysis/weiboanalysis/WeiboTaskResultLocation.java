package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;
@Data
public class WeiboTaskResultLocation {
    private String code;
    private String message;
    private List<Location> repostsList;
    private List<Location> commentsList;
}
