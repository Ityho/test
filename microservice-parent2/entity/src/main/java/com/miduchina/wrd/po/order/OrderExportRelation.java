package com.miduchina.wrd.po.order;

import lombok.Data;

import java.util.Date;

/**
 * Created by XUJING on 2016/12/28 0028.
 */
@Data
public class OrderExportRelation {
    private Integer id;
    //订单ID
    private int orderRecordId;
    //条件ID
    private Integer conditionId;
    //0-未导出 1-已导出
    private int status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //状态 0-未创建任务 1-已创建任务
    private int exportTaskStatus;

    }
