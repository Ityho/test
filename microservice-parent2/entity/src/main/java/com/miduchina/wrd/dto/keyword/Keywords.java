package com.miduchina.wrd.dto.keyword;

import lombok.Data;

import java.util.Date;

@Data
public class Keywords{
    private String title1;
    private String title2;
    private String title3;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String filterKeyword1;
    private String filterKeyword2;
    private String filterKeyword3;
    private Integer type1;
    private Integer type2;
    private Integer type3;
    private Integer categoryId1;
    private Integer categoryId2;
    private Integer categoryId3;

    //24小时，72小时，0自定义
    private Integer date;
    private Date startTime;
    private Date endTime;
}
