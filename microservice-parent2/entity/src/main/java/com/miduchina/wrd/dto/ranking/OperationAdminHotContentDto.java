package com.miduchina.wrd.dto.ranking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: AbilityDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 2:14 PM
 */
@Data
public class OperationAdminHotContentDto {

    @ApiModelProperty(value = "编号")
    private Integer id;
    @ApiModelProperty(value = "所属热点ID")
    private Integer hid;
    @ApiModelProperty(value = "摘要")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "信息总量")
    private Integer  infocount;
    @ApiModelProperty(value = "舆论高峰值")
    private double  peakvalue;
    @ApiModelProperty(value = "媒体报道量")
    private Integer  reportcount;
    @ApiModelProperty(value = "热度指数")
    private double  heatvalue;
    @ApiModelProperty(value = "热点图片路径")
    private String picpath;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "修改时间")
    private Date updatetime;
    @ApiModelProperty(value = "原文链接")
    private String originallink;
    @ApiModelProperty(value = "所属地域名称")
    private String keywordsname;
    @ApiModelProperty(value = "事件开始时间")
    private Date kwstartime;
    @ApiModelProperty(value = "事件结束时间")
    private Date kwendtime;
    @ApiModelProperty(value = "事件链接")
    private String eventLink;
    @ApiModelProperty(value = "事件id")
    private Integer iataskid;
    @ApiModelProperty(value = "热点标题")
    private String hotTitle;
    @ApiModelProperty(value = "热点开始时间")
    private Date hotStartTime;
    @ApiModelProperty(value = "热点结束时间")
    private Date hotEndTime;
    @ApiModelProperty(value = "标识--0事件，1新闻，2地域")
    private Integer mark;
    private String subtitle;
}
