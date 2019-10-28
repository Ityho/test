package com.miduchina.wrd.po.ranking;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: HotPhenomenoneRelation
 * @Description: 热门现象关联表
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13
 */
@Data
public class HotPhenomenoneRelation {
    private Long id;
    private Long hotPhenomenoneId;
    private Long hotIncidentId;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
