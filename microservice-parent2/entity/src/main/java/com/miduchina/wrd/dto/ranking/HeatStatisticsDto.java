package com.miduchina.wrd.dto.ranking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: HeatStatisticsDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 1:27 PM
 */
@Data
public class HeatStatisticsDto {
    @ApiModelProperty(value = "批次码", required = false)
    private String analysisTaskTicket;
    @ApiModelProperty(value = "分类型关键字表外键ID", required = true)
    private Integer categoryId;
    @ApiModelProperty(value = "渠道标识", required = true)
    private String channelTag;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime = new Date();
    @ApiModelProperty(value = "提及数据量", required = true)
    private Integer dataAmount;
    @ApiModelProperty(value = "热度指数变化", required = true)
    private Double heatIndexChange;
    @ApiModelProperty(value = "热度值", required = true)
    private Double heatValue;
    @ApiModelProperty(value = "id", required = true)
    private int id;
    @ApiModelProperty(value = "明星名称", required = false)
    private String name;
    @ApiModelProperty(value = "平台标识", required = true)
    private String platformTag;
    @ApiModelProperty(value = "排名", required = true)
    private Integer rank;
    @ApiModelProperty(value = "排名变化", required = true)
    private Integer rankDifference;
    @ApiModelProperty(value = "状态(1.有效 0.无效)", required = true)
    private int status;
    @ApiModelProperty(value = "修改时间", required = false)
    private Date updateTime;
    @ApiModelProperty(value = "用户标识", required = true)
    private String userTag;
}
