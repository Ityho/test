package com.miduchina.wrd.webthermalquery.controller;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.tuiguang.Tuiguang;
import com.miduchina.wrd.webthermalquery.fegin.UserFeignClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 推广
 *
 * @author liym
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Slf4j
@Controller
@RequestMapping("tuiguang/tg")
public class TuiGuangController extends BaseController {

	@Autowired
	private UserFeignClient userFeignClient;


	/**
	 * 百度推广
	 */
//	@Override
//	public String execute() throws Exception {
//		return SUCCESS;
//	}

	/**
	 * 360推广
	 *
	 * @return
	 * @throws Exception
	 */
//	public String sll() throws Exception {
//		return SUCCESS;
//	}

	/**
	 * 搜狗推广
	 *
	 * @return
	 * @throws Exception
	 */
//	public String sg() throws Exception {
//		return SUCCESS;
//	}

	/**
	 * 保存记录
	 *
	 * @throws Exception
	 */
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "record")
	@ResponseBody
	BaseDto record(Integer from, Integer type) throws Exception {
		BaseDto bto=new BaseDto();
		if (from > 0 && type > 0) {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = servletRequestAttributes.getRequest();
			String ip = CommonUtils.getIp(request);

			Tuiguang tuiguang = new Tuiguang();
			tuiguang.setFrom(from);
			tuiguang.setType(type);
			if (ip != null && !"".equals(ip))
				tuiguang.setIp(ip);
			tuiguang.setStatus(BusinessConstant.STATUS_VALID);
			tuiguang.setCreateTime(new Date());
			Map<String,Object> params=new HashMap<>();
			params.put("tuiguang", tuiguang);
			bto = userFeignClient.saveTuiguang(params);
		}
		return bto;
	}


}
