package com.miduchina.wrd.dto.ranking;/**
 * Created by sty on 2019/5/14.
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @version v1.0.0
 * @ClassName: RankingListWebDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/14 5:38 PM
 */
@Data
public class RankingListWebDto {

    private Set<RankingListWebDto> children;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    @ApiModelProperty(value = "id", required = true)
    private int id;
    @ApiModelProperty(value = "标记是否含有全部分类0没有1有", required = false)
    private int isAll;
    @ApiModelProperty(value = "是否显示0不显示1显示", required = false)
    private int isShow;
    @ApiModelProperty(value = "1.一级 2.二级 3.三级", required = true)
    private int level;
    private RankingListWebDto parent;
    @ApiModelProperty(value = "父级id", required = true)
    private int parentId;
    @ApiModelProperty(value = "排名参考数", required = false)
    private Integer rankNum;
    @ApiModelProperty(value = "排序字段", required = false)
    private int sort;
    @ApiModelProperty(value = "状态(1.启用 0.停用)", required = true)
    private int status;
    @ApiModelProperty(value = "分表id名称", required = false)
    private String tableIdName;
    @ApiModelProperty(value = "分表名", required = false)
    private String tableName;
    @ApiModelProperty(value = "类型名字", required = true)
    private String typeName;

    @ApiModelProperty(value = "修改时间", required = false)
    private Date updateTime;

    @ApiModelProperty(value = "操作人", required = true)
    private String username;
}
