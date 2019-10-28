package com.miduchina.wrd.dto.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConfirmOrderViewPackageElementDto implements Serializable {


    @ApiModelProperty(value ="ID")
    private Integer productPackageId;
    @ApiModelProperty(value ="产品名称")
    private String packageName;
    @ApiModelProperty(value ="产品价格")
    private Double packagePrice;
    @ApiModelProperty(value ="产品数量")
    private Integer packageCount;


}

