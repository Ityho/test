/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: UserFeignClient.java 
 * @Prject: spring-cloud-wrd-wb-other
 * @Package: com.midu.wrd.wb.api.service 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年6月8日 上午11:45:13 
 * @version: V1.0   
 */
package com.miduchina.wrd.webthermalquery.fegin;

import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.bigdata.OperationAdminWbDto;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/** 
 * @ClassName: UserFeignClient 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年6月8日 上午11:45:13  
 */
@FeignClient(value="api-info-monitor",path="api/v1")
public interface UserFeignClient {

	@RequestMapping(value = "/orderRecord",method = RequestMethod.POST)
	BaseDto doModifyUserType(@RequestBody  Map<String,Object> params);

	@RequestMapping(value = "/tuiguang/saveTuiguang",method = RequestMethod.POST)
	BaseDto saveTuiguang( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/bigdata/list",method = RequestMethod.POST)
	PageDto<OperationAdminWbDto> list(@RequestBody Map<String,Object> params);
	@RequestMapping(value = "/bigdata/findHotEventByNameTypePage",method = RequestMethod.POST)
	PageDto<OperationAdminWbDto> findHotEventByNameTypePage(@RequestBody Map<String,Object> params);
	@RequestMapping(value = "/report/closeReport",method = RequestMethod.POST)
	BaseDto closeReport( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/user/showNewNotice",method = RequestMethod.POST)
	BaseDto showNewNotice( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/user/findTodayLoginCountByUserId",method = RequestMethod.POST)
	BaseDto findTodayLoginCountByUserId( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/warning/getNotice",method = RequestMethod.POST)
	BaseDto getNotice( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/user/saveOrUpdateNotLoginOperateRecord",method = RequestMethod.POST)
	BaseDto saveOrUpdateNotLoginOperateRecord( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/orderExportRelation/findOrderExportStatus",method = RequestMethod.POST)
	BaseDto findOrderExportStatus( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/exportKeywordAnalysis/findConditionId",method = RequestMethod.POST)
	BaseDto findConditionId( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/orderExportRelation/saveOrUpdateOrderExport",method = RequestMethod.POST)
	BaseDto saveOrUpdateOrderExport( @RequestBody Map<String,Object> params);
	@RequestMapping(value = "/userDto/checkMobile",method = RequestMethod.POST)
	BaseDto checkMobile( @RequestParam("mobile") String  mobile);

}
