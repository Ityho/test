package com.miduchina.wrd.po.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserWdRightsRechargeRecord
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/19 2:36 PM
 */
@Data
@NoArgsConstructor
public class UserWdRightsRechargeRecord {
    private Integer rechargeRecordId;	//
    private Integer rechargeRecordUserId;	//用户ID
    private Integer rechargeRecordType;	//变动类型 1-增加 2-减少
    private Integer rechargeRecordCount;	//变动数量
    private Integer rechargeRecordCurrentCount;	//当前剩余数量
    private Integer rechargeRecordItem;	//1.签到 2.抽奖 3.活动 4.产品购买（按实际支付金额比例赠送）5.兑换
    private Integer rechargeRecordRelationId;	//关联Id
    private String rechargeRecordDesc;
    private Integer rechargeRecordPlatform;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public UserWdRightsRechargeRecord(Integer rechargeRecordUserId, Integer rechargeRecordRelationId, Integer rechargeRecordType, Integer rechargeRecordCount, Integer rechargeRecordCurrentCount, Integer rechargeRecordItem, String rechargeRecordDesc, Integer rechargeRecordPlatform, Integer status, Date createTime) {
        this.rechargeRecordUserId = rechargeRecordUserId;
        this.rechargeRecordRelationId = rechargeRecordRelationId;
        this.rechargeRecordType = rechargeRecordType;
        this.rechargeRecordCount = rechargeRecordCount;
        this.rechargeRecordCurrentCount = rechargeRecordCurrentCount;
        this.rechargeRecordItem = rechargeRecordItem;
        this.rechargeRecordDesc = rechargeRecordDesc;
        this.rechargeRecordPlatform = rechargeRecordPlatform;
        this.status = status;
        this.createTime = createTime;
    }
}
