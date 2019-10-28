package com.miduchina.wrd.po.csadmin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther yho
 * @vreate 2019-09 14:13
 */
@Data
public class CsTestUser implements Serializable {
    private static final long serialVersionUID = 2019946330747972122L;

    private Integer testUserId; // ID
    private String userName; // 用户名
    private String phone; // 手机
    private String password; // 密码
    private String address; // 归属单位
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Integer status; // 状态
    private String agent; // 经办人
    private String allRights; // 所有权益
    private Float cost; // 费用
    private Integer isvirtual; // 是否虚拟
    private Integer creditAmount1; // 微积分余额x5000
    private Integer creditAmount2; // 微积分余额x10000
    private Integer creditAmount3; // 微积分余额x50000
    private Integer creditAmount4; // 微积分余额x100000
    private Integer monitorMOnth; // 监测方案月
    private Integer monitorYear; // 监测方案年
    private Integer weiboAnalysisValidCount; // 微博事件分析剩余次数
    private Integer analysisValidCount; // 全网事件分析剩余次数
    private Integer productAnalysisValidCount; // 竞品分析剩余次数
    private Integer exportDataCount;//数据导出次数
    private Integer effects;//传播效果
    private String userId;//用户id
}
