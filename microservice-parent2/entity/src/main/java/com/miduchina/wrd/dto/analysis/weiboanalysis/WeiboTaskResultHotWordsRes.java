package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;
@Data
public class WeiboTaskResultHotWordsRes {
    private String code;
    private String message;
    private List<WeiboTaskResultHotWordsVO> repostsList;
    private List<WeiboTaskResultHotWordsVO> commentsList;
}
