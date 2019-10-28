package com.miduchina.wrd.dto.analysis.eventanalysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-29 15:32.
 *
 * @author shitao
 */

@Data
public class EventAnalysisInsertElementDto {

    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "模块ID")
    private Integer elementId;
    @ApiModelProperty(value = "元素ID")
    private Integer elementStyleId;
    @ApiModelProperty(value = "报告ID")
    private Integer analysisReportId;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "任务状态")
    private int status;
    @ApiModelProperty(value = "元素顺序")
    private int seq;
    @ApiModelProperty(value = "标题名称")
    private String name;
    @ApiModelProperty(value = "文本内容")
    private String content;
    @ApiModelProperty(value = "标题列")
    private String titleColumn;
    @ApiModelProperty(value = "任务唯一标识")
    private String analysisTaskTicket;
    @ApiModelProperty(value = "模版ID")
    private int modelId;
    @ApiModelProperty(value = "事件走势时间")
    private String treeTime;
    @ApiModelProperty(value = "事件走势说明文本")
    private String treeText;
    @ApiModelProperty(value = "热门信息说明文本")
    private String hotText;
    @ApiModelProperty(value = "事件标题")
    private String analysisTitle;
    @ApiModelProperty(value = "微博链接")
    private String weiboUrl;
    @ApiModelProperty(value = "1列表样式 2圆环样式")
    private int picStyle;

}
