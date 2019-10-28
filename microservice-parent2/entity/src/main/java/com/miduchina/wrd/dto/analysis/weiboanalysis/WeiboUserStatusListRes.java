package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

import java.util.List;
@Data
public class WeiboUserStatusListRes {
    private String code;
    private String message;
    private List<Weibo> statusList;

}

