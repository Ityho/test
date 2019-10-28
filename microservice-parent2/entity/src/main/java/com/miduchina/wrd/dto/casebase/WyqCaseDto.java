package com.miduchina.wrd.dto.casebase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sty on 2018/12/25.
 * @author sty
 */
@Data
public class WyqCaseDto{

    @ApiModelProperty(value = "编号")
    private Integer id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "摘要")
    private String summary;
    @ApiModelProperty(value = "事件类型 1.时事 2.社会 3.体育 4.科技 5.自然灾害 6.娱乐 7.人物 8.财经 9.房产 10.金融 11.医疗 12.教育 13.文化 14.汽车 15.旅游 16.政务 17.法治")
    private Integer eventLabel;
    @ApiModelProperty(value = "关键词")
    private String keyword;
    @ApiModelProperty(value = "排除词")
    private String filterKeyword;
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    @ApiModelProperty(value = "最高热度值时间点")
    private Date maxHotValueTime;
    @ApiModelProperty(value = "任务状态")
    private Integer analysisStatus;
    @ApiModelProperty(value = "任务进度（百分比）")
    private Double analysisSchedule;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "热度值")
    private Double hotValue;
    @ApiModelProperty(value = "最高热度值")
    private Double maxHotValue;
    @ApiModelProperty(value = "文件路径")
    private String filePath;
    @ApiModelProperty(value = "首发网站")
    private String initialWebsite;
    @ApiModelProperty(value = "pdf文件路径")
    private String pdfPath;
    @ApiModelProperty(value = "阅读数")
    private Integer readNumber;

    @ApiModelProperty(value = "byte类型的事件类型")
    private Byte byteEventLabel;
    private String startT;
    private String endT;
    @ApiModelProperty(value = "最高热度值时间点  MM-dd")
    private String maxTime;
    private String eventLabelStr = "";
    @ApiModelProperty(value = "事件类型")
    private String eventType;
    @ApiModelProperty(value = "发布时间")
    private String startCreateTime;

    public String getStartTimeStr(){
        if(this.startTime == null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.startTime);
    }

    public String getEndTimeStr(){
        if(this.endTime == null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime);
    }

    public String getCreateTimeStr(){
        if(this.createTime == null){
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
    }
}
