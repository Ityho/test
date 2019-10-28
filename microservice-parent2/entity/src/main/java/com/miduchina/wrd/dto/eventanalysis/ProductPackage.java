package com.miduchina.wrd.dto.eventanalysis;

import lombok.Data;

import java.util.Date;

@Data
public class ProductPackage implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer productPackageId;
    private String packageName;
    private Float packagePrice;
    private Integer packageType;
    private Integer keywordServeDays;
    private Integer productAnalysisCount;
    private Integer keywordCount;
    private Integer smsCount;
    private String description;
    private Integer sortSeq;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Integer productCount;
    private Integer renewPackageId;
    private Integer analysisCount;
    private Integer weiboAnalysisCount;
    private Integer briefCount;
    private Integer reportCount;
    private Integer singleWeiboAnalysisCount;
    private Integer creditCount;
    private Integer giftCreditCount;
    private Integer packageCategoryType;
    private Integer hotReportCount; // 热度日报次数
    private Integer hotReportServerDays; // 热度日报时长

    private Integer exportDataCount; // 导出数据量

    private Integer commentsAnalysisCount; // 评论分析次数

    private Float productTotalPrice;	//产品总价
    private Float productDiscountPrice;	//产品折后价

}
