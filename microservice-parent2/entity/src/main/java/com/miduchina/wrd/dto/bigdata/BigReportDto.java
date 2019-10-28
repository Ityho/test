package com.miduchina.wrd.dto.bigdata;/**
 * Created by sty on 2019/5/9.
 */

import io.swagger.annotations.ApiModelProperty;
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
public class BigReportDto {
    @ApiModelProperty(value = "编号")
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "大数据报告ID")
    private Integer bigReportID;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "事件类型 1.时事 2.社会 3.体育 4.科技 5.自然灾害 6.娱乐 7.人物 8.财经 9.房产 10.金融 11.医疗 12.教育 13.文化 14.汽车 15.旅游 16.政务 17.法治")
    private Integer eventLabel;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "文件路径")
    private Integer filePath;

    @ApiModelProperty(value = "首发网站")
    private Integer initialWebsite;

    @ApiModelProperty(value = "价格")
    private Integer reportPrice;

    @ApiModelProperty(value = "pdf路径")
    private Integer pdfPath;

    @ApiModelProperty(value = "阅读数")
    private Integer readNumber;

    @ApiModelProperty(value = "状态")
    private Integer status;



}
