package com.miduchina.wrd.po.order;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: PackgeManagent
 * @Description: 套餐包
 * @author: sty
 * @date: 2019/7/18 2:59 PM
 */
@Data
public class PackgeManagent {
    private Integer pmId;//套餐管理表id
    private Integer userId;//用户id
    private Integer packgeId;//套餐id
    private Date updataTime;//更新时间
    private Date creatTime;//创建时间
    private Date expirationTime;//过期时间
    private Integer PackgeStatus;//套餐状态
}
