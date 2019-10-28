package com.miduchina.wrd.dto.analysis.eventanalysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-29 15:55.
 *
 * @author shitao
 */
@Data
public class EventAnalysisModelNextmoduleDto {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "模版ID")
    private int elementId;
    @ApiModelProperty(value = "模块名称")
    private String modelNextName;
    @ApiModelProperty(value = "模块说明")
    private String modelNextText;
    @ApiModelProperty(value = "模块状态 1-显示  2-删除")
    private int status;
    @ApiModelProperty(value = "元素顺序")
    private int seq;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "元素id")
    private Integer elementStyleId;
    @ApiModelProperty(value = "事件分析报告ID")
    private Integer analysisReportId;
    private String containerId;
    private String viewpointText;
    private String viewpoint;
    @ApiModelProperty(value = "微博URL")
    private String weiboUrl;


}
