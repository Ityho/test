package com.miduchina.wrd.po.analysis.eventanalysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-29 17:51.
 *
 * @author shitao
 */
@Data
public class EventAnalysisModelElement {

    /**
     * 模块ID
     */
    private Integer elementId;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 有效状态
     */
    private int status;


}
