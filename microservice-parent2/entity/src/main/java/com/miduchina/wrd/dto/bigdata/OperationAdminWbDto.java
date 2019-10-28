package com.miduchina.wrd.dto.bigdata;/**
 * Created by sty on 2019/5/9.
 */

import lombok.Data;

import java.util.Date;
import java.util.HashMap;

/**
 * @version v1.0.0
 * @ClassName: OperationAdminWb
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/9 10:25 AM
 */
@Data
public class OperationAdminWbDto {
    //1.时事 2.社会 3.体育 4.科技 5.自然灾害 6.娱乐 7.人物 8.财经 9.房产 10.金融 11.医疗 12.教育 13.文化 14.汽车 15.旅游 16.政务 17.法治
    static HashMap<Integer, String> hashMap=new HashMap<Integer, String>();
    static {
        hashMap.put(1, "时事");
        hashMap.put(2, "社会");
        hashMap.put(3, "体育");
        hashMap.put(4, "科技");
        hashMap.put(5, "自然灾害");
        hashMap.put(6, "娱乐");
        hashMap.put(7, "人物");
        hashMap.put(8, "财经");
        hashMap.put(9, "房产");
        hashMap.put(10, "金融");
        hashMap.put(11, "医疗");
        hashMap.put(12, "教育");
        hashMap.put(13, "文化");
        hashMap.put(14, "汽车");
        hashMap.put(15, "旅游");
        hashMap.put(16, "政务");
        hashMap.put(17, "法治");
        hashMap.put(18, "品牌舆情");
    }
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

    public String getTypeValue(){
        return hashMap.get(eventLabel);
    }
}
