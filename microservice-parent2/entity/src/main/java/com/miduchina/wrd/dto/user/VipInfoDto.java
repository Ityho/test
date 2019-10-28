package com.miduchina.wrd.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by shitao on 2019-05-09 14:09.
 *
 * @author shitao
 */
@Data
public class VipInfoDto {

    @ApiModelProperty(value = "最低充值金额")
    private double minAmount;
    @ApiModelProperty(value = "最高充值金额")
    private double maxAmount;
    @ApiModelProperty(value = "微积分累计充值金额")
    private double creditRechargeAmount;
    @ApiModelProperty(value = "vip等级")
    private int level;
    @ApiModelProperty(value = "折扣")
    private double discount;
    @ApiModelProperty(value = "下一级vip权益")
    private VipInfoDto nextVipInfo;
    @ApiModelProperty(value = "vip对应权益表")
    private List<VipInfoDto> vipInfos;

}
