package com.miduchina.wrd.dto.ip;

import lombok.Data;

/**
 * @author  qiuqiu
 * @date 创建时间：2018年6月16日 上午6:53:17
 */
@Data
public class IpInfoDto {

	private String ip;
	private Long ipNum;
	private String province;
	private String city;
	private String isp;
}
