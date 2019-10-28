package com.miduchina.wrd.dto.report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KeywordReportRecordDto implements Serializable {

    @ApiModelProperty(value = "记录流水号")
    private Integer id;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "监测方案ID")
    private Integer keywordId;
    @ApiModelProperty(value = "监测方案内容")
    private String keywordContent;
    @ApiModelProperty(value = "日报名称")
    private String reportName;
    @ApiModelProperty(value = "日报邮件内容")
    private String reportEmailContent;
    @ApiModelProperty(value = "日报发送状态")
    private Integer sendStatus;
    @ApiModelProperty(value = "日报状态")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "Y:开 L：关")
    private String loadFlag;
    @ApiModelProperty(value = "分享code")
    private String shareCode;
    @ApiModelProperty(value = "是否已读 0-未读；1-已读")
    private Integer isRead;
    @ApiModelProperty(value = "监测日报版式(1,2,3,4,5)共五种")
    private Integer dailyReportModel;

}
