package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;
@Data
public class WeiboTaskResultFansRes {
    private String code;
    private String message;
    private List<Fan> repostsList;
    private List<Fan> commentsList;
}
