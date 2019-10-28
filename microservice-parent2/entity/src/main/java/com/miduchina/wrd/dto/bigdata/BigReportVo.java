package com.miduchina.wrd.dto.bigdata;



import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname BigReport
 * @Description TODO
 * @Date 2019/8/29 20:01
 * @Author ZhuFangTao
 */
@Data
public class BigReportVo implements Serializable{
    private static final long serialVersionUID = -2651518351611148237L;
    private Integer id;
    private Integer userId;
    private Integer bigReportId;
    private String title;//标题
    private String summary;//摘要
    private Integer eventLabel;//事件类型 1.时事 2.社会 3.体育 4.科技 5.自然灾害 6.娱乐 7.人物 8.财经 9.房产 10.金融 11.医疗 12.教育 13.文化 14.汽车 15.旅游 16.政务 17.法治
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private Date createTime;//
    private Date updateTime;//
    private String filePath;//文件路径
    private Integer reportPrice;//
    private String pdfPath;//pdf文件路径
    private Integer readNumber;//阅读数
    private Integer status;



}
