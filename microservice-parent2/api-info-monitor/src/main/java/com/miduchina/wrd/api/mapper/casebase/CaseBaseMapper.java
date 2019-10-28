/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: RankingListMapper.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.com.miduchina.wrd.api.rankinglist.mapper
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月16日 上午10:00:13 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.mapper.casebase;

import com.miduchina.wrd.po.casebase.WyqCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: HotLabelMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
@Mapper
public interface CaseBaseMapper {

	List<WyqCase> findAll(Map<String, Object> param);

	WyqCase findById(Integer caseId);

	List<WyqCase> findByTopLabel(Integer label);
}
