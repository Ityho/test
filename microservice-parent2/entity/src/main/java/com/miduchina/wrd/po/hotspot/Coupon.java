package com.miduchina.wrd.po.hotspot;


import lombok.Data;

import java.util.Date;

/**
 * @author  qiuqiu
 * @date 创建时间：2018年1月25日 下午4:27:41
 */
@Data
public class Coupon implements java.io.Serializable {

	private static final long serialVersionUID = -1631476615062391268L;
	//兑换券ID
	private Integer couponId;
	//兑换券名称
	private String couponName;
	//是否显示兑换
	private Integer isShowSwap;
	//兑换需要的微豆
	private Integer wdPrice;
	//产品类型	1.微积分充值抵扣券 2.微积分消费抵用券 3.监测方案 4.全网事件分析 5.微博事件分析
	//6.竞品分析 7.微博传播效果分析 8.评论分析 9.数据导出 10.热度指数 11.发票包邮券 12.简报制作
	private Integer couponType;
	//兑换监测方案时长
	private Integer keywordServeDays;
	//兑换数量
	private Integer exchangeNum;
	//抵扣金额（微积分充值抵扣券）
	private Float discountAmount;
	//最低消费金额（微积分充值抵扣券）
	private Float minSpentAmount;
	//抵扣微积分（微积分消费抵扣券）
	private Integer discountCreditCount;
	//最低消费微积分（微积分消费抵扣券）
	private Integer minSpentCreditCount;
	//有效天数
	private Integer validDays;
	//描述（一次、一条、一个月...）
	private String description;
	//供手动排序使用
	private int sortSeq;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	//是否被兑换
	private boolean isExchanged;
	//来源 1.连续签到 2.抽奖 3.兑换 4.活动
	private Integer stemFrom;
	//使用时间
	private Date usedTime;
	//过期时间
	private Date validEndTime;
	//是否过期
	private boolean isOverDue;

	//附加字段
	private String userTimeToEndTime;
	private Integer userCouponRecordId;
	private Integer type;
}
