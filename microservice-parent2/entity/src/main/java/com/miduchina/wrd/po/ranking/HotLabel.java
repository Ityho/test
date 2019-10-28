/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotLabel.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.model 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年11月6日 上午11:50:26 
 * @version: V1.0   
 */
package com.miduchina.wrd.po.ranking;

import java.util.Date;
import java.util.List;

import lombok.Data;

/** 
 * @ClassName: HotLabel 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年11月6日 上午11:50:26  
 */
@Data
public class HotLabel {
	
	private Integer id;
	private String name;
	private Integer parentId;
	private Integer level;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	
	private List<HotLabel> childrens;
}
