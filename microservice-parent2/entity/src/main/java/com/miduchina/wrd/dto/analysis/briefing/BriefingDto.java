package com.miduchina.wrd.dto.analysis.briefing;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-27 14:40.
 *
 * @author shitao
 */
@Data
public class BriefingDto {

    @ApiModelProperty(value = "简报ID")
    private Integer briefId;
    @ApiModelProperty(value = "用户ID")
    private int userId;
    @ApiModelProperty(value = "简报状态")
    private int status;
    @ApiModelProperty(value = "监测方案ID")
    private int keywordId;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "摘要")
    private String summary;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "模版ID")
    private int templateId;
    private String pieImg;
    private String treeImg;
    private String areaImg;
    private String lineImg;
    private String jsonData;
    private String saveFileName;
    private String selectedRead;
    private String propose;
    @ApiModelProperty(value = "普通简报，党政简报等（头有差异）")
    private int type;
    @ApiModelProperty(value = "任务唯一标识")
    private String taskTicket;
    @ApiModelProperty(value = "任务开始时间")
    private Date startTime;
    @ApiModelProperty(value = "任务结束时间")
    private Date endTime;
    @ApiModelProperty(value = "素材总数量")
    private int analysisNum;
    @ApiModelProperty(value = "进度")
    private Double schedule = 0.00;
    @ApiModelProperty(value = "进度")
    private String scheduleTips;
    @ApiModelProperty(value = "任务处理状态（:待处理，2:正在处理，3:已处理，4 5 :处理完成:）")
    private int analysisStatus = 1;
    @ApiModelProperty(value = "期数")
    private int briefSeq;
    @ApiModelProperty(value = "1：行业版，带期数；2：党政版，无期数的；3：摘要版")
    private Integer styleType;
    @ApiModelProperty(value = "1:旧版简报  2：新版简报")
    private int markType;
    @ApiModelProperty(value = "1:生成简报  2：修改简报")
    private int clickType;
    @ApiModelProperty(value = "是否编辑(0-未编辑 1-已编辑)")
    private int isEdit = 0;
    @ApiModelProperty(value = "预计时间 毫秒")
    private int analysisTotalConsumeExpect;
    @ApiModelProperty(value = "预计数量")
    private int analysisSolrFirstCountExpect;


}
