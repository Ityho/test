package com.miduchina.wrd.dto.eventanalysis.products;

import lombok.Data;

@Data
public class PackageVO {
    private static final long serialVersionUID = -4102066077352520983L;
    private Integer productPackageId; // ID
    private String packageName; // 产品名称
    private Double packagePrice; // 产品价格
    private Integer packageType; // 产品类型
    private Integer keywordServeDays; // 监测方案有效天数
    private Integer keywordCount; // 监测方案数量
    private Integer analysisCount; // 全网事件分析次数
    private Integer weiboAnalysisCount; // 微博事件分析次数
    private Integer briefCount; // 简报制作次数
    private Integer productAnalysisCount; // 竞品分析次数
    private Integer singleWeiboAnalysisCount; // 单条微博分析转发条数
    private Integer exportDataCount; // 导出数据量
    private Integer creditCount; // 微积分
    private Integer giftCreditCount; // 赠送微积分
    private Integer hotReportCount; // 热度日报次数
    private Integer hotReportServerDays; // 热度日报时长
    private Integer commentsCount; // 评论分析次数
    private String description; // 描述
    private Integer sortSeq; // 排序
    private Integer status; // 状态
    private Integer renewPackageId; // 续费套餐ID
}
