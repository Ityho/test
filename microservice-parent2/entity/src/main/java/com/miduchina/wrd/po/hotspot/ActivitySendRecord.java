package com.miduchina.wrd.po.hotspot;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chengfan
 * @time 2018年4月12日 下午4:47:43
 *
 */
@Data
public class ActivitySendRecord implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3371982289463785822L;

	private Integer id;
	// 用户Id
	private Integer userId;
	// 下发类型 1 优惠券
	private Integer sendType;
	// 优惠券Id
	private Integer couponId;
	// 提示  1 已提示 0 未提示
	private Integer isTips;
	// 下发数量
	private Integer sendNum;
	// 描述
	private String description;
	// 状态
	private Integer status;
	// 操作人Id
	private Integer adminId;
	// 创建时间
	private String createTime;

	private Coupon coupon;


}
