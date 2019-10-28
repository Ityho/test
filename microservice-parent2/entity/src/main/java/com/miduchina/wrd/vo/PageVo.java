/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: PageVo.java 
 * @Prject: entity
 * @Package: com.midu.wyq.vo 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年11月1日 下午8:31:33 
 * @version: V1.0   
 */
package com.miduchina.wrd.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** 
 * @ClassName: PageVo 
 * @Description: 分页视图对象
 * @author: 许双龙
 * @date: 2017年11月1日 下午8:31:33  
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PageVo extends BaseVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer maxPage;
	private Integer page;
	private Integer pageSize;
	private Long totalCount;
	private Long lastTotalCount;

	public PageVo setCode(String code) {
		super.setCode(code);
		return this;
	}

	public PageVo setMessage(String message) {
		super.setMessage(message);
		return this;
	}

	public PageVo setData(Object data) {
		super.setData(data);
		return this;
	}
	
	public PageVo setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
		return this;
	}
	
	public PageVo setPage(Integer page) {
		this.page = page;
		return this;
	}
	
	public PageVo setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
	public PageVo setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		return this;
	}
	
	public PageVo setLastTotalCount(Long lastTotalCount) {
		this.lastTotalCount = lastTotalCount;
		return this;
	}
}
