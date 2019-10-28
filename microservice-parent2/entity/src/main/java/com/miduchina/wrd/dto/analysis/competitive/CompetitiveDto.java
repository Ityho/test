package com.miduchina.wrd.dto.analysis.competitive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-27 15:11.
 *
 * @author shitao
 */
@Data
public class CompetitiveDto {

    @ApiModelProperty(value = "竞品ID")
    private Integer pabId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "文件路径")
    private String filePath;
    @ApiModelProperty(value = "分享URL")
    private String shareUrl;
    @ApiModelProperty(value = "bar图片")
    private String barImg;
    @ApiModelProperty(value = "任务进度状态（进度状态 1-完成 2-正在分析 0分析失败）")
    private Integer schedulesStatus;
    @ApiModelProperty(value = "是否是案例")
    private Integer isSample;
    @ApiModelProperty(value = "任务唯一标识")
    private String ticket;

}
