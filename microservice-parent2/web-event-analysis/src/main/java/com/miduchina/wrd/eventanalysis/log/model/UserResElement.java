package com.miduchina.wrd.eventanalysis.log.model;

import com.miduchina.wrd.dto.user.VipInfoDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserResElement implements Serializable {
	private static final long serialVersionUID = -3599338672264285163L;
	private Integer userId; // ID
	private String username; // 用户名
	private String nickname; // 昵称
	private String email; // 邮箱
	private String mobile; // 手机号码
	private String password; // 密码
	private Integer passwordStrength; // 密码强度
	private Date lastLoginTime; // 最后登录时间
	private String remark; // 备注
	private Integer status; // 状态
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间
	private String userHead; // 用户头像
	private Integer appUserStatus; // 用户所属平台
	private Integer userChannel; // 用户渠道
	private Integer userType; // 用户类型
	private Date proUserValidEndTime; // 专业版到期时间
	private Integer userAnalysisValidCount; // 全网事件分析剩余次数
	private Integer userWeiboAnalysisValidCount; // 微博事件分析剩余次数
	private Integer userBriefValidCount; // 简报制作次数
	private Integer userReportValidCount; // 自动报表次数
	private Integer userProductAnalysisValidCount; // 竞品分析次数
	private Integer userSingleWeiboAnalysisValidCount; // 单条微博分析转发条数
	private Integer exportDataCount; // 导出数据剩余条数
	private Integer creditAmount; // 微积分余额
	private String platform; // 客户端平台
	private Integer heatReportCount; // 热度对比剩余次数
	private Double sharePlanAmount; // 互动基金

	private Integer commentsCount;
	private Integer allKeywordCount; // 监测方案总数
	private Integer noUseKeywordCount; // 未使用监测方案总数
	private Integer validWdSum;	//有效微豆数量
	private VipInfoDto vipInfo;
	private Boolean subUser; // 是否子用户
	private Date stopEndTime; // 封停时间
	/**
	 * 最后一次签到日期
	 */
	private String lastSignInDate;

}
