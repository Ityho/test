package com.miduchina.wrd.po.bigdata;/**
 * Created by sty on 2019/5/9.
 */

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: BigReport
 * @Description: TODO
 * @author: sty
 * @date: 2019/5/9 10:25 AM
 */
@Data
public class BigReport {
    private Integer id;
    private Integer userId;
    private Integer bigReportID;
    private String title;
    private String summary;
    private Integer eventLabel;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date updateTime;
    private Integer filePath;
    private Integer initialWebsite;
    private Integer reportPrice;
    private Integer pdfPath;
    private Integer readNumber;
    private Integer status;



}
