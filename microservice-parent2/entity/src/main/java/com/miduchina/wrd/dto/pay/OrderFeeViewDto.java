package com.miduchina.wrd.dto.pay;

import com.miduchina.wrd.po.eventanalysis.BaseView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class OrderFeeViewDto extends BaseView {

    @ApiModelProperty(value = "使用微积分(旧版参数)")
    private Integer useCreditAmount;
    @ApiModelProperty(value = "应付金额")
    private Double totalFee;
    @ApiModelProperty(value = "无折扣金额(无折扣金额=应付金额 + 微积分会员折扣金额 + 微积分充值抵用券金额)")
    private Double notDiscountFee;
    @ApiModelProperty(value ="微积分会员折扣金额")
    private Double discountFee;
    @ApiModelProperty(value ="微积分充值抵用券金额")
    private Double voucherFee;
    @ApiModelProperty(value ="微积分支付抵用券微积分数")
    private Integer voucherAmount;
    @ApiModelProperty(value ="用户当前微积分数量")
    private Integer userCreditNum;
    @ApiModelProperty(value ="使用微积分(新版参数)")
    private long creditAmountFee;
    private Long timeFlag;

}

