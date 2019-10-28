package com.miduchina.wrd.dto.keyword;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-29 18:14.
 *
 * @author shitao
 */
@Data
public class ExportKeywordAnalysisConditionDto {

    @ApiModelProperty(value = "条件ID")
    private int conditionId;
    @ApiModelProperty(value = "任务唯一标识")
    private String analysisTaskTicket;
    @ApiModelProperty(value = "用户标识")
    private int userId;
    @ApiModelProperty(value = "监测方案id")
    private Integer keywordId;
    @ApiModelProperty(value = "任务名称")
    private String keywordName;
    @ApiModelProperty(value = "任务关键字")
    private String keyword;
    @ApiModelProperty(value = "任务过滤关键字")
    private String filterKeyword;
    @ApiModelProperty(value = "匹配方式")
    private int matchType;
    @ApiModelProperty(value = "信息来源")
    private String origin;
    @ApiModelProperty(value = "信息属性")
    private int otherAttribute;
    @ApiModelProperty(value = "结果呈现")
    private int newlstSelect;
    @ApiModelProperty(value = "相似文章")
    private int comblineflg;
    @ApiModelProperty(value = "分析开始时间")
    private String analysisStartTime;
    @ApiModelProperty(value = "分析结束时间")
    private String analysisEndTime;
    @ApiModelProperty(value = "定向监测源")
    private String captureWebsit;
    @ApiModelProperty(value = "定向监测省份")
    private String provinces;
    @ApiModelProperty(value = "导出数量")
    private int exportCount;
    @ApiModelProperty(value = "导出类型")
    private String exportType;
    @ApiModelProperty(value = "状态")
    private int status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "单条导出信息IDS")
    private String checkedIds;
    @ApiModelProperty(value = "1、收藏夹   2、监测方案  3、相似文章")
    private int favoritesOrMonitoring;
    @ApiModelProperty(value = "相似文章")
    private String titleHs;
    @ApiModelProperty(value = "来源网站")
    private String website;
    @ApiModelProperty(value = "定向名")
    private String directSet;
    @ApiModelProperty(value = "定向类型")
    private Integer directType;
    @ApiModelProperty(value = "0 代表全部 多值勾选：1、标题 2、信息属性 3、原创/转发 4、地址 5、媒体名称 6、昵称\n" +
            "    // 7、发布日期 8、媒体类型 9、原微博内容 10、认证类型 11、地域 12、性别\n" +
            "    // 13、正文切词 14、全文内容 15、作者ID 16、粉丝数 17、微博数 18、转\n" +
            "    // 19、评 20、赞 21、阅 22、话题")
    private String dataType;



}
