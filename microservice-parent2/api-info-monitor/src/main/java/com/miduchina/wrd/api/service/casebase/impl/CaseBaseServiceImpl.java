/**
 * Copyright © 2018 公司名. All rights reserved.
 *
 * @Title: CaseBaseServiceImpl.java
 * @Prject: wyq-enterprise-api
 * @Package: com.midu.wyq.enterprise.wrd.service.impl
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年12月18日 上午9:52:19
 * @version: V1.0
 */
package com.miduchina.wrd.api.service.casebase.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.mapper.casebase.CaseBaseMapper;
import com.miduchina.wrd.api.service.casebase.CaseBaseService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.casebase.WyqCase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CaseBaseServiceImpl
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年12月18日 上午9:52:19
 */
@Service
public class CaseBaseServiceImpl implements CaseBaseService {

	@Resource
	private CaseBaseMapper caseBaseMapper;

	@Override
	public PageInfo<WyqCase> findAll(Map<String,Object> param) {
		Integer page = param.get("page") == null ? BusinessConstant.DEFAULT_PAGE : Integer.valueOf(String.valueOf(param.get("page")));
		Integer pageSize = param.get("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE : Integer.valueOf(String.valueOf(param.get("pageSize")));

		PageHelper.startPage(page, pageSize);
		List<WyqCase> list = caseBaseMapper.findAll(param);
		PageInfo<WyqCase> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public WyqCase findById(Integer caseId) {
		return caseBaseMapper.findById(caseId);
	}

	@Override
	public PageInfo<WyqCase> findByTopLabel(Integer label) {
		PageHelper.startPage(1, 3);
		List<WyqCase> list = caseBaseMapper.findByTopLabel(label);
		PageInfo<WyqCase> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}
