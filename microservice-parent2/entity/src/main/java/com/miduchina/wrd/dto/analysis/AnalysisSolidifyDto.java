package com.miduchina.wrd.dto.analysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-17 16:45.
 *
 * @author shitao
 */
@Data
public class AnalysisSolidifyDto {

    @ApiModelProperty(value = "分析状态")
    private Integer analysisStatus;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    private Integer id;
    @ApiModelProperty(value = "平台")
    private String platform;
    @ApiModelProperty(value = "进度")
    private String schedule;
    @ApiModelProperty(value = "进度 Double")
    private Double schedulePercent;
    @ApiModelProperty(value = "进度状态")
    private Integer solidifyStatus;
    @ApiModelProperty(value = "任务唯一标识")
    private String ticket;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
