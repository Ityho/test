package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;

@Data
public class WeiboTaskResultLineRes {
    private String code;
    private String message;
    private List<Coordinates> repostsList;
    private List<Coordinates> commentsList;


}