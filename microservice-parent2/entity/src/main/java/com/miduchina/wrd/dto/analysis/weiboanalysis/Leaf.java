package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class Leaf {
    private String userScreenName;
    private String userLogoUrl;
    private String parentStatusId;
    private String statusId;
    private int level;

}
