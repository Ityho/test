package com.miduchina.wrd.dto.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ConfirmOrderViewOrderElementDto implements Serializable {

    @ApiModelProperty(value ="ID")
    private Integer orderRecordId;
    @ApiModelProperty(value ="用户ID")
    private Integer userId;
    @ApiModelProperty(value ="订单时间")
    private Date orderTime;
    @ApiModelProperty(value ="支付状态")
    private Integer payStatus;
    @ApiModelProperty(value ="订单状态")
    private Integer status;
    @ApiModelProperty(value ="创建时间")
    private Date createTime;
    @ApiModelProperty(value ="订单名称")
    private String orderName;
    @ApiModelProperty(value ="订单描述")
    private String orderDescription;
    @ApiModelProperty(value ="订单金额")
    private Double totalFee;
    @ApiModelProperty(value ="产品数量")
    private Integer packageCount;
    @ApiModelProperty(value ="订单类型")
    private Integer orderType;
    @ApiModelProperty(value ="订单号")
    private String orderNo;

    private String createTimeStr;
    private String orderTimeStr;



}
