package com.miduchina.wrd.dto.pay;

import com.miduchina.wrd.po.eventanalysis.BaseView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class ConfirmOrderViewDto extends BaseView {
    @ApiModelProperty(value ="使用微积分")
    private Integer useCreditAmount;
    @ApiModelProperty(value ="订单信息")
    private ConfirmOrderViewOrderElementDto orderInfo;
    @ApiModelProperty(value ="支付信息")
    private PayRecordDto payInfo;
    @ApiModelProperty(value ="产品信息")
    private List<ConfirmOrderViewPackageElementDto> packagesInfo;
    @ApiModelProperty(value ="产品信息")
    private List<ConfirmOrderViewPackageElementDto> packages;

}