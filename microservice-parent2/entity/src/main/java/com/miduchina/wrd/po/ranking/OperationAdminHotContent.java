package com.miduchina.wrd.po.ranking;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: HeatStatistics
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 1:27 PM
 */
@Data
public class OperationAdminHotContent {
    private Integer id;
    private Integer hId;//所属热点ID
    private String title;//摘要
    private String content;//内容
    private Integer  infocount;//信息总量
    private double  peakvalue;//舆论高峰值
    private Integer  reportcount;//媒体报道量
    private double  heatvalue;//热度指数
    private String picpath;//热点图片路径
    private Integer sort;//排序
    private Integer status;//状态
    private Date createtime;//创建时间
    private Date updatetime;//修改时间
    private String originallink;//原文链接

    private String keywordsname;//所属地域名称
    private Date kwstartime;//事件开始时间
    private Date kwendtime;//事件结束时间
    private String eventLink;//事件链接
    private Integer iataskid;//事件id
    private String hotTitle;//热点标题
    private Date hotStartTime;//热点开始时间
    private Date hotEndTime;//热点结束时间
    private Integer mark;//标识--0事件，1新闻，2地域
    private String subtitle;
}
