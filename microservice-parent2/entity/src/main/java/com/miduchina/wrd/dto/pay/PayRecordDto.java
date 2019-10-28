package com.miduchina.wrd.dto.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PayRecordDto implements java.io.Serializable {

    @ApiModelProperty(value ="ID")
    private Integer payRecordId;
    @ApiModelProperty(value ="用户ID")
    private Integer userId;
    @ApiModelProperty(value ="创建时间")
    private Date createTime;
    @ApiModelProperty(value ="金额")
    private Double totalFee;
    @ApiModelProperty(value ="支付名称")
    private String payName;
    @ApiModelProperty(value ="支付描述")
    private String payDescription;
    @ApiModelProperty(value ="支付状态")
    private Integer payStatus;
    @ApiModelProperty(value ="内部支付编号")
    private String innerTradeNo;
    @ApiModelProperty(value ="支付方式")
    private String payChannel;
    @ApiModelProperty(value ="更新时间")
    private Date updateTime;
    @ApiModelProperty(value ="外部支付编号")
    private String outerTradeNo;
}
