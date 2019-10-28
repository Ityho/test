package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.dto.user.UserDto;
import lombok.Data;

import java.util.List;
@Data
public class Weibo  {
    private String statusId;
    private String statusText;
    private Integer statusAttitudesCount;
    private Integer statusCommentsCount;
    private Integer statusReadsCount;
    private Integer statusRepostsCount;
    private String statusCreatedAt;
    private List<String> statusPicList;
    private UserDto user;
    private Weibo retweetedStatus;
    private Integer sensitiveFlag;

    private String url;
}
