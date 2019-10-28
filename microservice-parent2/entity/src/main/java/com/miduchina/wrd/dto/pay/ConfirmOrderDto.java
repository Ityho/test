package com.miduchina.wrd.dto.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConfirmOrderDto implements Serializable {
    @ApiModelProperty(value ="产品列表")
    private List<ConfirmOrderPackageDto> packages;
    @ApiModelProperty(value ="分析微博ID")
    private Integer fenxiWeiboId;
    @ApiModelProperty(value ="人工简报ID")
    private Integer manualBriefId;
    @ApiModelProperty(value ="监测方案IDS")
    private String keywordIds;
    @ApiModelProperty(value ="关键词")
    private String keywords;
    @ApiModelProperty(value ="热度日报ID")
    private String heatReportIds;
    @ApiModelProperty(value ="续费套餐ID")
    private Integer renewPackageId;
    @ApiModelProperty(value ="订单类型")
    private Integer orderType;
    @ApiModelProperty(value ="是否使用微积分")
    private boolean useCredit;
    @ApiModelProperty(value ="支付方式")
    private String payChannel;
    @ApiModelProperty(value ="描述")
    private String desc;
    @ApiModelProperty(value ="大数据报告主键id")
    private Integer bigReportId;
}
