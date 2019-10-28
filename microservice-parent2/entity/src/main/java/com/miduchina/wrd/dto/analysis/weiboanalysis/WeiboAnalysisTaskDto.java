package com.miduchina.wrd.dto.analysis.weiboanalysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-23 14:59.
 *
 * @author shitao
 */
@Data
public class WeiboAnalysisTaskDto {

    @ApiModelProperty(value = "任务ID")
    private int taskId;
    @ApiModelProperty(value = "用户ID")
    private int userId;
    @ApiModelProperty(value = "微博URL")
    private String weiboUrl;
    @ApiModelProperty(value = "任务唯一标识")
    private String taskTicket;
    @ApiModelProperty(value = "微博内容")
    private String weiboContent;
    @ApiModelProperty(value = "微博转发数")
    private int forwardCount;
    @ApiModelProperty(value = "评论数")
    private int commentCount;
    @ApiModelProperty(value = "点赞数")
    private int praiseCount;
    @ApiModelProperty(value = "微博发布时间")
    private Date publishedTime;
    @ApiModelProperty(value = "微博用户ID")
    private String weiboUid;
    @ApiModelProperty(value = "微博用户昵称")
    private String weiboNickname;
    @ApiModelProperty(value = "微博用户头像url")
    private String weiboUserhead;
    @ApiModelProperty(value = "分享code")
    private String shareCode;
    @ApiModelProperty(value = "任务进度(状态:0失败，1等待分析，2正在拉取数据，3完成，4等待确认，5正在分析)")
    private int analysisStatus;
    @ApiModelProperty(value = "分享链接")
    private String shareUrl;
    @ApiModelProperty(value = "任务创建时间")
    private Date createTime;
    @ApiModelProperty(value = "任务更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "转发内容")
    private String forwardContent;
    @ApiModelProperty(value = "是否案例")
    private int isSample;
    @ApiModelProperty(value = "旧版新版（0旧 新1）")
    private int markType;
    @ApiModelProperty(value = "分享阅读数")
    private  int shareRedCount;
    @ApiModelProperty(value = "博主认证类型")
    private int verifiedType;
    @ApiModelProperty(value = "是否支付")
    private int payment;
    @ApiModelProperty(value = "分享平台（分享平台  1-微博  2-微信  3-微信朋友圈  4-QQ  5-QQ空间）")
    private int sharePlatform;
    @ApiModelProperty(value ="有效状态")
    private int status;

    @ApiModelProperty(value ="支付时间是否超过15分钟 ")
    private int isExceed = 0;

}
