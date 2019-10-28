package com.miduchina.wrd.po.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserRightsRecord
 * @Description: 用户权益记录表
 * @author: sty
 * @date: 2019/7/18 1:33 PM
 */
@Data
@NoArgsConstructor
public class UserRightsRecord {
    private Integer userRightsRecordId;
    private Integer userRightsRecordUserId;
    private Integer userRightsRecordItem;
    private Integer userRightsRecordInitCount;
    private Integer userRightsRecordCurrCount;
    private Double userRightsRecordInitAmount;
    private Double userRightsRecordCurrAmount;
    private Date userRightsRecordValidEndTime;
    private String userRightsRecordDesc;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    private Integer userRightsRecordGiftCount;
    private Double userRightsRecordGiftAmount;

    public UserRightsRecord(Integer userRightsRecordUserId){
        this.userRightsRecordUserId = userRightsRecordUserId;
    }
}
