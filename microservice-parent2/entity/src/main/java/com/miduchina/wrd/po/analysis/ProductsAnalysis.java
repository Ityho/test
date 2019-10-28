package com.miduchina.wrd.po.analysis;

import lombok.Data;

import java.util.Date;
/**
 * 竞品条件
 * @author yff
 *
 */
@Data
public class ProductsAnalysis  {
	
	private Integer id;
	private int userId;
	private int status;
	private Date createTime;
	private Date updateTime;
	/**
	 * 1-自定义词对比；2-监测方案对比
	 */
	private int type;

	private String jsonData;
	/**
	 * 选中的监测方案ID集合
	 */
	private String keywordIds;

	/**
	 * 24-24小时 3-3天 7-一周 10-10天 30- 一月
	 */
	private String timeDomain;

}
