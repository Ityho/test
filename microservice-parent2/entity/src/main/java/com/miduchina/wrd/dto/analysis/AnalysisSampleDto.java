package com.miduchina.wrd.dto.analysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-14 20:20.
 *
 * @author shitao
 */
@Data
public class AnalysisSampleDto {

    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "链接url")
    private String url;
    @ApiModelProperty(value = "分享链接url")
    private String shareUrl;
    @ApiModelProperty(value = "案例类型（1:竞品分析，2:全网事件分析，3:微博事件分析，4:微博传播分析）")
    private Integer sampleType;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "有效状态")
    private Integer status;


}
