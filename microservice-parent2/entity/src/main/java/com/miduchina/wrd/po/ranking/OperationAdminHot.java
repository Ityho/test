package com.miduchina.wrd.po.ranking;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: HeatStatistics
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 1:27 PM
 */
@Data
public class OperationAdminHot {
    private Integer id;
    private Integer uid;//用户id
    private String title;//标题
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private String picPath;//背景图片路径
    private String shareCode;//分享码
    private double sentiment;//情绪指数
    private double sentimentend;//情绪指数
    private String sortTime;//排序字段
    private Integer status;//状态
    private Date createTime;//创建时间
    private String updateTime;//修改时间
    private List<OperationAdminHotContent> hotconts;
}
