package com.miduchina.wrd.po.ranking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by sty on 2018/12/19.
 */
@Data
public class HotIncidentNews {

    @ApiModelProperty(value = "作者", required = true)
    private String author = "";

    @ApiModelProperty(value = "观点", required = true)
    private String content = "";

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    @ApiModelProperty(value = "热词id", required = true)
    private int hotIncidentId;

    @ApiModelProperty(value = "聚类id", required = true)
    private int hotIncidentNewsId;

    @ApiModelProperty(value = "发布时间", required = true)
    private Date publishTime;

    @ApiModelProperty(value = "来源", required = true)
    private String source = "";

    @ApiModelProperty(value = "状态(0:无效 1:有效)", required = true)
    private int status;

    @ApiModelProperty(value = "修改时间", required = false)
    private Date updateTime;

    @ApiModelProperty(value = "链接", required = true)
    private String url = "";
}
