package com.miduchina.wrd.monitor.warning.operatelog.model;

import lombok.Data;

@Data
public class OperateLogObject {
	private Integer logType; // 日志类型：1-操作行为日志 2-浏览行为日志
	private Integer product; // 产品：1-微热点
	private Object user; // 用户
	private String reqTime; // 访问时间
	private String reqIp; // 访问IP
	private Integer productChannel; // 产品入口：1-WEB 2-APP 3-H5 4-预警 5-日报
	private Integer productSection; // 产品版块： 定义好的产品版块编码
	private Integer productPageCode; // 页面标识：定义好的页面描述字典
	private String productPageDesc; // 页面名称：定义好的页面描述字典
	private Integer operateType; // 操作类型：1-增 2-删 3-改 4-查
	private Object operateBeforeObj; // 操作前对象：定义好的对象json
	private Object operateAfterObj; // 操作后对象：定义好的对象json
	private String reqUA; // 请求ua信息
	private String reqReferer;//请求referer
	private String mobilePlatform; // 客户端平台：ios、android
	private String mobileVersion; // 客户端版本
	private String logExtraInfo; // 扩展信息
	private Integer channelTag; // 渠道 1-邮件 2-短信 3-PUSH-安卓 4-PUSH-小米 5-PUSH-华为 6-PUSH-IOS 7-WEIBO私信 8-WEB弹窗 9-WEIXIN 10-QQ 11-WEIBO（内容） 12-WP
}
