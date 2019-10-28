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
public class OperationAdminHotDto {

    @ApiModelProperty(value = "编号")
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer uId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    @ApiModelProperty(value = "背景图片路径")
    private String picPath;
    @ApiModelProperty(value = "分享码")
    private String shareCode;
    @ApiModelProperty(value = "情绪指数")
    private double sentiment;
    @ApiModelProperty(value = "情绪指数")
    private double sentimentend;
    @ApiModelProperty(value = "排序字段")
    private String sortTime;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private String updateTime;
}
