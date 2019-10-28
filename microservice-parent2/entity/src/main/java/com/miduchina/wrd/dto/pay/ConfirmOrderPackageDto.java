package com.miduchina.wrd.dto.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConfirmOrderPackageDto implements Serializable {

    @ApiModelProperty(value ="ID")
    private Integer productPackageId;
    @ApiModelProperty(value ="产品数量")
    private Integer packageCount;

}
