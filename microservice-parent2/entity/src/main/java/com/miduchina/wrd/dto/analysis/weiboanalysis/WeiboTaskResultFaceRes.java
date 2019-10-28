package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;

@Data
public class WeiboTaskResultFaceRes {
    private String code;
    private String message;
    private List<Face> repostsList;
    private List<Face> commentsList;
}
