package com.miduchina.wrd.po.eventanalysis.weiboevent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Stats  {

    private boolean isUndulate = false;// 是否起伏
    @ApiModelProperty(value = "统计键值", required = true)
    private String key;
    private int maxPosition;// 最大位置
    private int maxUndulatePosition;// 最陡位置
    @ApiModelProperty(value = "统计名称(时间)", required = true)
    private String name;
    @ApiModelProperty(value = " 统计数量", required = true)
    private long num;
    @ApiModelProperty(value = "统计百分比", required = true)
    private Double percent;
    @ApiModelProperty(value = "统计百分比", required = true)
    private String percentStr;
    @ApiModelProperty(value = "统计百分比", required = true)
    private List<Stat> statList = new ArrayList<Stat>();// 统计列表
    private int type = 1;// 统计类型


}
