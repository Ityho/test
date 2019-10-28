package com.miduchina.wrd.po.keyword;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Warning implements Serializable {
	
	private static final long serialVersionUID = 1353480269734061253L;
	
	private Integer warningId;
	private int userId;
	private String warningName;// 方案名称
	private int warningContentType;//全部0；负面2，疑似负面1；负面和疑似负面3
	private int warningType;// 预警类型,1-为单个关键词预警, 2 - 多个关键词预警
	
	private String warningStartTime;
	private String warningEndTime;
	
	private int warningIntervalTime;//间隔时间、定时预警，30分钟
 
	private int smsSwitch;
	private int appSwitch;
	private int webSwitch;
	private int emailSwitch;
    private int weiboSwitch;
	
	private int sameContentMergeSwitch;//0-不合并 1-合并
	private int weekendSendSwitch;//0 - 关闭，1 - 开启
 
	private Date createTime;
	private Date updateTime;
	private int status;
	private int isValid;

    private String warKeyword;//预警关键词
    private String sourceCondition;//来源类型，多选，用逗号隔开
    private String province;//省份，单选

    private Date processTime;//理想发送时间

    public String toPrintString(){
		return "预警id:"+this.warningId+";预警名称:"+this.warningName+";开始时间:"+this.warningStartTime+";结束时间:"
				+this.warningEndTime+";间隔时间:"+this.warningIntervalTime+";周末是否推送:"+(this.weekendSendSwitch==0?"不推送":"推送")
				+";预警状态："+(this.emailSwitch==1?"开":"关")
				+";预警内容属性："+(this.warningContentType==3?"敏感":"全部");
	}
}
