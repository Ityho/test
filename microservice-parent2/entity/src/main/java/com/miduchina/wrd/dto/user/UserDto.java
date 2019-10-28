package com.miduchina.wrd.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shitao on 2019-05-08 19:04.
 *
 * @author shitao
 */
@Data
public class UserDto implements Serializable{

    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "邮件")
    private String email = "";
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "密码强度")
    private int passwordStrength;
    @ApiModelProperty(value = "第三方账号")
    private String thirdpartyAccount;
    @ApiModelProperty(value = "第三方账号类型")
    private int thirdpartyType;
    @ApiModelProperty(value = "是否验证")
    private int isVerified = 0;
    @ApiModelProperty(value = "验证时间")
    private Date verifiedTime;
    @ApiModelProperty(value = "验证次数")
    private int validSmsCount;
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;
    @ApiModelProperty(value = "状态")
    private int status;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "发送短信总次数")
    private Integer smsTotalCount;
    @ApiModelProperty(value = "用户头像地址")
    private String userHead;
    @ApiModelProperty(value = "用户所属平台(0-WEB用户，1-客户端用户，2-H5用户)")
    private int appUserStatus;
    @ApiModelProperty(value = "用户类型(1：基础用户 2：专业版用户 3：专业版ABC用户)")
    private int userType;
    @ApiModelProperty(value = "专业版到期时间")
    private Date proUserValidEndTime;
    @ApiModelProperty(value = "用户渠道")
    private int userChannel;
    @ApiModelProperty(value = "全网事件分析剩余次数")
    private int userAnalysisValidCount;
    @ApiModelProperty(value = "微博事件分析有效次数")
    private int userWeiboAnalysisValidCount;
    @ApiModelProperty(value = "简报制作次数")
    private int userBriefValidCount;
    @ApiModelProperty(value = "自动报表次数")
    private int userProductAnalysisValidCount;
    @ApiModelProperty(value = "竞品分析")
    private int userReportValidCount;
    @ApiModelProperty(value = "单条微博分析转发条数")
    private int userSingleWeiboAnalysisValidCount;
    @ApiModelProperty(value = "导出数据剩余条数")
    private int exportDataCount;
    @ApiModelProperty(value = "微积分余额")
    private int creditAmount;
    @ApiModelProperty(value = "客户端平台")
    private String platform;
    @ApiModelProperty(value = "互动基金")
    private Double sharePlanAmount;
    @ApiModelProperty(value = "")
    private int commentsCount;
    @ApiModelProperty(value = "")
    private int allKeywordCount;
    @ApiModelProperty(value = "")
    private int noUseKeywordCount;
    @ApiModelProperty(value = "有效微豆数量")
    private Integer validWdSum;
    @ApiModelProperty(value = "今年过期的微豆数量")
    private Integer willOverdueWdSum;
    @ApiModelProperty(value = "最后一次签到日期")
    private Date lastSignInDate;
    @ApiModelProperty(value = "连续签到天数")
    private Integer seriesSignInDays;
    private boolean isProUser;
    @ApiModelProperty(value = "是否子用户")
    private Boolean subUser;
    @ApiModelProperty(value = "封停时间")
    private Date stopEndTime;
    @ApiModelProperty(value = "封停")
    private boolean stopFlag;
    @ApiModelProperty(value = "vip信息")
    private VipInfoDto vipInfo;
    @ApiModelProperty(value = "签到提醒开关")
    private Integer signInRemindSwitch;
    @ApiModelProperty(value = "1，已赠送产品包，2，未赠送产品包")
    private Integer isGiftPackage;
    @ApiModelProperty(value = "登录缓存id")
    private String sid;

    @ApiModelProperty(value = "博主认证类型")
    private Integer userVerifiedType;

    @ApiModelProperty(value = "微博URL")
    private String pageUrl;
    /**
     * 微分析模块
     */
    private Integer statusAttitudesCount;
    private Integer statusCommentsCount;
    private String statusCreatedAt;
    private Integer statusReadsCount;
    private Integer statusRepostsCount;
    private String statusText;
    private Integer userFriendsCount;
    private Integer userFollowersCount;
    private String userLogoUrl;
    private String userScreenName;
    private String statusId;
    private String weiboUrl;
    private Integer userStatusCount;

    //微博用户
    private String id;                      //用户UID
    private String screenName;            //微博昵称
    private String name;                  //友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
    private int province;                 //省份编码（参考省份编码表）
    private int city;                     //城市编码（参考城市编码表）
    private String location;              //地址
    private String description;           //个人描述
    private String url;                   //用户博客地址
    private String profileImageUrl;       //自定义图像
    private String userDomain;            //用户个性化URL
    private String gender;                //性别,m--男，f--女,n--未知
    private int followersCount;           //粉丝数
    private int friendsCount;             //关注数
    private int statusesCount;            //微博数
    private int favouritesCount;          //收藏数
    private Date createdAt;               //创建时间
    private boolean following;            //保留字段,是否已关注(此特性暂不支持)
    private boolean verified;             //加V标示，是否微博认证用户
    private int verifiedType;             //认证类型
    private boolean allowAllActMsg;       //是否允许所有人给我发私信
    private boolean allowAllComment;      //是否允许所有人对我的微博进行评论
    private boolean followMe;             //此用户是否关注我
    private String avatarLarge;           //大头像地址
    private int onlineStatus;             //用户在线状态
//    private Status status = null;         //用户最新一条微博
    private int biFollowersCount;         //互粉数
//    private String remark;                //备注信息，在查询用户关系时提供此字段。
    private String lang;                  //用户语言版本
    private String verifiedReason;		  //认证原因
    private String weihao;				  //微号
//    private String statusId;

    private String appPlatform;
    private String exclusiveChannelCode;
    private Integer giftPackageId;
    private Integer inviteUserId;
    private boolean isLogin;
}
