package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;

@Data
public class WeiboTaskResultUserTagRes {
    private String code;
    private String message;
    private List<Tag> repostsList;
    private List<Tag> commentsList;
}
