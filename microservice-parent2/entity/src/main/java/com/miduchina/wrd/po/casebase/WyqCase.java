/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: WyqCase.java 
 * @Prject: wyq-enterprise-api
 * @Package: com.midu.wyq.enterprise.domain.entity 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年12月18日 上午9:58:19 
 * @version: V1.0   
 */
package com.miduchina.wrd.po.casebase;

import java.util.Date;

import lombok.Data;

/** 
 * @ClassName: WyqCase 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年12月18日 上午9:58:19  
 */
@Data
public class WyqCase{
	
	private Integer id;
	private Integer operationAdminWbId;
	private String title;//标题
	private String summary;//摘要
	private Integer eventLabel;//事件类型 1.时事 2.社会 3.体育 4.科技 5.自然灾害 6.娱乐 7.人物 8.财经 9.房产 10.金融 11.医疗 12.教育 13.文化 14.汽车 15.旅游 16.政务 17.法治
	private String keyword;//关键词
	private String filterKeyword;//排除词
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Date maxHotValueTime;//最高热度值时间点
	private Integer analysisStatus;//任务状态
	private Double analysisSchedule;//任务进度（百分比）
	private Integer status;//
	private Date createTime;//
	private Date updateTime;//
	private String analysisTicket;//
	private String analysisTicket2;//正在分析的ticket
	private Integer solidifyStatus;//
	private Double hotValue;//热度值
	private Double maxHotValue;//最高热度值
	private String filePath;//文件路径
	private String initialWebsite;//首发网站
	private Integer reportPrice;//
	private String pdfPath;//pdf文件路径
	private Integer readNumber;//阅读数
	
	//附加字段
	private Byte byteEventLabel;//byte类型的事件类型
	private String startT;
	private String endT;
	private String maxTime;//最高热度值时间点  MM-dd
	private String eventLabelStr = "";
	private String eventType;//事件类型
	private String startCreateTime;//发布时间

	
}
