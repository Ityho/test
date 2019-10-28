package com.miduchina.wrd.dto.analysis.eventanalysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-29 17:56.
 *
 * @author shitao
 */
@Data
public class EventAnalysisModelElementStyleDto {

    @ApiModelProperty(value = "元素ID")
    private Integer elementStyleId;
    @ApiModelProperty(value = "模块ID")
    private Integer elementId;
    @ApiModelProperty(value = "元素(图)名称")
    private String name;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value="有效状态")
    private int status;
    @ApiModelProperty(value = "模块名称")
    private String memo;

}
