package com.miduchina.wrd.po.ranking;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: HotPhenomenoneLabelRelation
 * @Description: 热门现象标签关联表
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13
 */
@Data
public class HotPhenomenoneLabelRelation {

    private Long id;
    private Long hotPhenomenoneId;
    private Long hotLabelId;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
