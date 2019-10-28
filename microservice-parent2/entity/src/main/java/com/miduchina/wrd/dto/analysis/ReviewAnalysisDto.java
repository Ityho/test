package com.miduchina.wrd.dto.analysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-30 17:16.
 *
 * @author shitao
 */
@Data
public class ReviewAnalysisDto {

    @ApiModelProperty(value = "评论分析id")
    private Integer reviewAnalysisId;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "评论标题")
    private String reviewAnalysisTitle;
    @ApiModelProperty(value = "评论内容")
    private String reviewAnalysisContent;
    @ApiModelProperty(value = "标题链接")
    private String reviewAnalysisLink;
    @ApiModelProperty(value = "发布时间")
    private Date releaseTime;
    @ApiModelProperty(value = "发布网站")
    private String releaseWeb;
    @ApiModelProperty(value = "来源网站")
    private String fromWeb;
    @ApiModelProperty(value = "分析时间")
    private Date analysisTime;
    @ApiModelProperty(value = "评论数")
    private Integer reviewNum;
    @ApiModelProperty(value = "参与人数")
    private Integer participateInNum;
    @ApiModelProperty(value = "评论生成的ticket")
    private String ticket;
    @ApiModelProperty(value = "支付状态")
    private Integer payStatus;
    @ApiModelProperty(value = "状态 --0 删除  --1有效")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "分享码")
    private String reviewAnalysisShareCode;
    @ApiModelProperty(value = "固话状态")
    private Integer fixed_status;

}
