package com.miduchina.wrd.po.tuiguang;

import lombok.Data;

import java.util.Date;

/**
 * 推广
 *
 * @author liym
 */
@Data
public class Tuiguang {
	private Integer id;
	private Integer from; // 来源：1-百度推广
	private Integer type; // 类型：1-登录  2-注册  3-产品介绍  4-陪你入门  5-帮助中心  6-客户端下载
	private String ip; // 来源IP
	private Integer status;
	private Date createTime;
	private Date updateTime;


}
