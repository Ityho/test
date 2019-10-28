package com.miduchina.wrd.po.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserExtendInfo
 * @Description: 用户扩展信息
 * @author: sty
 * @date: 2019/7/18 10:12 AM
 */
@Data
@NoArgsConstructor
public class UserExtendInfo {
    //ID
    private Integer id;
    //用户ID
    private Integer userId;
    //签到提醒开关
    private Integer signInRemindSwitch;
    //是否赠送产品包
    private Integer isGiftPackage;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public UserExtendInfo(Integer userId){
        this.userId = userId;
    }
}
