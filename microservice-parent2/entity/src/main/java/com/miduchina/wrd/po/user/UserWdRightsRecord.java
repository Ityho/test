package com.miduchina.wrd.po.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserWdRightsRecord
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/19 2:39 PM
 */
@Data
@NoArgsConstructor
public class UserWdRightsRecord {
    private Integer userWdRightsRecordId;	//
    private Integer userWdRightsRecordUserId;	//用户ID
    private Integer userWdRightsRecordOrigin;	//来源类型 1.签到 2.抽奖 3.活动 4.产品购买（按实际支付金额比例赠送）
    private Integer userWdRightsRecordInitCount;	//初始数量
    private Integer userWdRightsRecordCurrCount;	//当前剩余数量
    private Date userWdRightsRecordValidEndTime;	//有效期
    private String userWdRightsRecordDesc;	//说明
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public UserWdRightsRecord(Integer userId, Integer userWdRightsRecordOrigin, Integer userWdRightsRecordInitCount, Integer userWdRightsRecordCurrCount,
                              Date userWdRightsRecordValidEndTime, String userWdRightsRecordDesc, Integer status, Date createTime) {
        this.userWdRightsRecordUserId = userId;
        this.userWdRightsRecordOrigin = userWdRightsRecordOrigin;
        this.userWdRightsRecordInitCount = userWdRightsRecordInitCount==null?0:userWdRightsRecordInitCount;
        this.userWdRightsRecordCurrCount = userWdRightsRecordCurrCount==null?0:userWdRightsRecordCurrCount;
        this.userWdRightsRecordValidEndTime = userWdRightsRecordValidEndTime;
        this.userWdRightsRecordDesc = userWdRightsRecordDesc;
        this.status = status;
        this.createTime = createTime;
    }
}
