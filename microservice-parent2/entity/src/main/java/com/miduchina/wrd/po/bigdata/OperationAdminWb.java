package com.miduchina.wrd.po.bigdata;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: OperationAdminWb
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/9 10:25 AM
 */
@Data
public class OperationAdminWb {
    private Integer id;
    private Integer uid;
    private String title;

    private String abstracts;
    private String link;
    private String imageUrl;
    private Integer type;
    private String subTitle;
    private String content;
    private String topImage;
    private Integer status;
    private Date createTime;
    private Date paixuTime;
    private String articleUrl;

    private String numberPeriods;
    private String imageurlTwo;
    private Integer readNumber=0;
    private String platform; // 平台
    private Integer eventLabel; // 事件类型
    private String platformStr;
    private String eventLabelStr = "";
    private Integer packagePrice;
    private String pdfPath;

    //附加字段
    private Double hotValue;//热度值
    private Integer caseId;//wyq_case主键id
    private Date startTime;//开始分析时间
    private Date endTime;//结束分析时间
    private String eventType;//事件类型
    private Byte byteEventLabel;//byte类型的事件类型
    private Date maxHotValueTime;//最高热度值时间点
    private Double maxHotValue;//最高热度值
    private String keyword;//关键词
    private String filterKeyword;//排除词
    private String startT;
    private String endT;
    private String filePath;//文件路径
    private String startCreateTime;//发布时间
    private String maxTime;//最高热度值时间点  MM-dd
    private String initialWebsite;//首发网站
    private Integer recommend;

    private Integer isPackagePrice;



}
