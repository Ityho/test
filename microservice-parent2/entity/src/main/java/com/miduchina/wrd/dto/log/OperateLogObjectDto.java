package com.miduchina.wrd.dto.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-08 16:54.
 *
 * @author shitao
 */
@Data
public class OperateLogObjectDto {

    @ApiModelProperty(value = "日志类型：1-操作行为日志 2-浏览行为日志")
    private Integer logType;
    @ApiModelProperty(value = "产品：1-微舆情")
    private Integer product;
    @ApiModelProperty(value = "访问时间")
    private String reqTime;
    @ApiModelProperty(value = "访问IP")
    private String reqIp;
    @ApiModelProperty(value = "产品入口：1-WEB 2-APP 3-H5 4-微博WEB 0-未知")
    private Integer productChannel;
    @ApiModelProperty(value = "产品版块： 定义好的产品版块编码")
    private Integer productSection;
    @ApiModelProperty(value = "页面标识：定义好的页面描述字典")
    private Integer productPageCode;
    @ApiModelProperty(value = "页面名称：定义好的页面描述字典")
    private String productPageDesc;
    @ApiModelProperty(value = "操作类型：1-增 2-删 3-改 4-查")
    private Integer operateType;
    @ApiModelProperty(value = "请求ua信息")
    private String reqUA;
    @ApiModelProperty(value = "客户端平台：ios、android")
    private String mobilePlatform;
    @ApiModelProperty(value = "客户端版本")
    private String mobileVersion;
    @ApiModelProperty(value = "扩展信息")
    private String logExtraInfo;
    @ApiModelProperty(value = "浏览日志界面停留时间(s)")
    private Integer stayTime;
    @ApiModelProperty(value = "api接口前缀")
    private String ApiURL;
    @ApiModelProperty(value = "")
    private String reqReferer;
    @ApiModelProperty(value = "1. 邮件，2. 短信，3. PUSH-安卓，4. PUSH-小米，5. PUSH-华为，6. PUSH-IOS，7. WEIBO私信，8.WEB弹窗，9.WEIXIN，10.QQ，11.WEIBO（内容）")
    private Integer channelTag;
    @ApiModelProperty(value = "操作前对象")
    private Object operateBeforeObj;
    @ApiModelProperty(value = "操作后对象")
    private Object operateAfterObj;
    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "邮件")
    private String email = "";
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "第三方账号")
    private String thirdpartyAccount;
    @ApiModelProperty(value = "第三方账号类型")
    private Integer thirdpartyType;
    @ApiModelProperty(value = "是否验证")
    private Integer isVerified = 0;
    @ApiModelProperty(value = "验证时间")
    private Date verifiedTime;
    @ApiModelProperty(value = "验证次数")
    private Integer validSmsCount;
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "任务创建时间")
    private Date taskCreateTime;
    @ApiModelProperty(value = "任务更新时间")
    private Date taskUpdateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "发送短信总次数")
    private Integer smsTotalCount;
    @ApiModelProperty(value = "用户头像地址")
    private String userHead;
    @ApiModelProperty(value = "用户所属平台(0-WEB用户，1-客户端用户，2-H5用户)")
    private Integer appUserStatus;
    @ApiModelProperty(value = "用户类型(1：基础用户 2：专业版用户 3：专业版ABC用户)")
    private Integer userType;
    @ApiModelProperty(value = "专业版到期时间")
    private Date proUserValidEndTime;
    @ApiModelProperty(value = "用户渠道")
    private Integer userChannel;
    @ApiModelProperty(value = "全网事件分析剩余次数")
    private Integer userAnalysisValidCount;
    @ApiModelProperty(value = "微博事件分析有效次数")
    private Integer userWeiboAnalysisValidCount;
    @ApiModelProperty(value = "简报制作次数")
    private Integer userBriefValidCount;
    @ApiModelProperty(value = "自动报表次数")
    private Integer userProductAnalysisValidCount;
    @ApiModelProperty(value = "竞品分析")
    private Integer userReportValidCount;
    @ApiModelProperty(value = "单条微博分析转发条数")
    private Integer userSingleWeiboAnalysisValidCount;
    @ApiModelProperty(value = "导出数据剩余条数")
    private Integer exportDataCount;
    @ApiModelProperty(value = "微积分余额")
    private Integer creditAmount;
    @ApiModelProperty(value = "客户端平台")
    private String platform;
    @ApiModelProperty(value = "互动基金")
    private Double sharePlanAmount;
    @ApiModelProperty(value = "")
    private Integer commentsCount;
    @ApiModelProperty(value = "")
    private Integer allKeywordCount;
    @ApiModelProperty(value = "")
    private Integer noUseKeywordCount;
    @ApiModelProperty(value = "有效微豆数量")
    private Integer validWdSum;
    @ApiModelProperty(value = "今年过期的微豆数量")
    private Integer willOverdueWdSum;
    @ApiModelProperty(value = "最后一次签到日期")
    private String lastSignInDate;
    @ApiModelProperty(value = "连续签到天数")
    private Integer seriesSignInDays;
    private boolean isProUser;
    @ApiModelProperty(value = "是否子用户")
    private boolean subUser;
    @ApiModelProperty(value = "封停时间")
    private Date stopEndTime;
    @ApiModelProperty(value = "封停")
    private boolean stopFlag;
    @ApiModelProperty(value = "签到提醒开关")
    private Integer signInRemindSwitch;
    @ApiModelProperty(value = "1，已赠送产品包，2，未赠送产品包")
    private Integer isGiftPackage;
    @ApiModelProperty(value = "")
    private String sid;
    @ApiModelProperty(value = "最低充值金额")
    private double minAmount;
    @ApiModelProperty(value = "最高充值金额")
    private double maxAmount;
    @ApiModelProperty(value = "微积分累计充值金额")
    private double creditRechargeAmount;
    @ApiModelProperty(value = "vip等级")
    private Integer level;
    @ApiModelProperty(value = "折扣")
    private double discount;
    @ApiModelProperty(value = "分析Id")
    private Integer taskId;

    private Integer reportId;

    /**
     * 监测方案信息
     */
    @ApiModelProperty(value = "监测方案内容s")
    private String taskKeyword;

    @ApiModelProperty(value = "监测方案ID")
    private Integer keywordId;
    @ApiModelProperty(value = "监测方案名称")
    private String keywordName;
    @ApiModelProperty(value = "监测方案内容")
    private String keyword;
    @ApiModelProperty(value = "用户输入内容")
    private String inputKeyword;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String keyword4;
    @ApiModelProperty(value = "用户输入过滤内容")
    private String inputFilterKeyword;
    @ApiModelProperty(value = "过滤内容")
    private String filterKeyword;
    @ApiModelProperty(value = "监测方案类型")
    private Integer keywordType;
    @ApiModelProperty(value = "有效期")
    private String validEndDate;
    @ApiModelProperty(value = "引导类型")
    private Integer monitorType;
    /**
     * 预警信息
     */
    @ApiModelProperty(value = "预警ID")
    private Integer warningId;
    @ApiModelProperty(value = "预警名称")
    private String warningName;
    @ApiModelProperty(value = "预警开始时间")
    private String warningStartTime;
    @ApiModelProperty(value = "预警结束时间")
    private String warningEndTime;
    @ApiModelProperty(value = "预警时间间隔(分钟)")
    private Integer warningIntervalTime;
    @ApiModelProperty(value = "短信开关")
    private Integer smsSwitch;
    @ApiModelProperty(value = "APP开关")
    private Integer appSwitch;
    @ApiModelProperty(value = "WEB开关")
    private Integer webSwitch;
    @ApiModelProperty(value = "邮箱开关")
    private Integer emailSwitch;
    @ApiModelProperty(value = "私信开关")
    private Integer weiboSwitch;
    @ApiModelProperty(value = "周末是否推送")
    private Integer weekendSendSwitch;
    @ApiModelProperty(value = "相同内容合并")
    private Integer sameContentMergeSwitch;
    @ApiModelProperty(value = "来源类型")
    private String sourceCondition;
    @ApiModelProperty(value = "内容属性")
    private Integer warningContentType;
    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "平台标识")
    private String platformTag;
    @ApiModelProperty(value = "分析范围(1:全网，2:微博，3:非微博，default:1)")
    private Integer contentType;
    @ApiModelProperty(value = "事件标题")
    private String incidentTitle;
    @ApiModelProperty(value = "事件补充词语")
    private String supplementKeyword;
    @ApiModelProperty(value = "分析类型(1:单次，2:持续)")
    private Integer analysisType;
    @ApiModelProperty(value = "分析参数详情，JSON格式，配合“分析参数类型”进行扩展")
    private String analysisParams;
    @ApiModelProperty(value = "处理状态(0:失败，1:待处理，2:正在处理，3:已处理，4:处理完成)")
    private Integer analysisStatus;
    @ApiModelProperty(value = "处理开始时间(default:1970-01-01 00:00:00)")
    private Date startTime;
    @ApiModelProperty(value = "处理结束时间(default:当前时间)")
    private Date endTime;
    @ApiModelProperty(value = "处理截止时间")
    private Date analysisUpToTime;
    @ApiModelProperty(value = "任务进度（百分比）")
    private Double analysisSchedule;
    @ApiModelProperty(value = "任务进度说明")
    private String analysisScheduleTips;
    @ApiModelProperty(value = "处理批次号，用于跟处理结果关联")
    private String taskTicket;
    @ApiModelProperty(value = "微博分析内容类型：0-全部微博，1-原创微博")
    private Integer analysisWbContentType;
    @ApiModelProperty(value = "更新次数")
    private Integer updateNum;
    @ApiModelProperty(value = "创建类型 1：创建 2：更新 3：重新分析")
    private Integer createType;
    @ApiModelProperty(value = "信息属性")
    private Integer options;
    @ApiModelProperty(value = "匹配方式")
    private Integer matchType;
    @ApiModelProperty(value = "内容来源")
    private String origin;
    @ApiModelProperty(value = "预计时间 毫秒")
    private Integer analysisTotalConsumeExpect=0;
    @ApiModelProperty(value = "预计数量")
    private Integer analysisSolrFirstCountExpect=0;
    @ApiModelProperty(value = "含有敏感词")
    private boolean sensitive;
    @ApiModelProperty(value = "预计时间（分钟）")
    private int estimateMinute=0;

    @ApiModelProperty(value = "微博URL")
    private String weiboUrl;
    @ApiModelProperty(value = "微博内容")
    private String weiboContent;
    @ApiModelProperty(value = "微博转发数")
    private int forwardCount;
    @ApiModelProperty(value = "评论数")
    private int commentCount;
    @ApiModelProperty(value = "点赞数")
    private int praiseCount;
    @ApiModelProperty(value = "微博发布时间")
    private Date publishedTime;
    @ApiModelProperty(value = "微博用户ID")
    private String weiboUid;
    @ApiModelProperty(value = "微博用户昵称")
    private String weiboNickname;
    @ApiModelProperty(value = "微博用户头像url")
    private String weiboUserhead;
    @ApiModelProperty(value = "分享code")
    private String shareCode;
    @ApiModelProperty(value = "分享链接")
    private String shareUrl;
    @ApiModelProperty(value = "转发内容")
    private String forwardContent;
    @ApiModelProperty(value = "是否案例")
    private int isSample;
    @ApiModelProperty(value = "旧版新版（0旧 新1）")
    private int markType;
    @ApiModelProperty(value = "分享阅读数")
    private  int shareRedCount;
    @ApiModelProperty(value = "博主认证类型")
    private int verifiedType;
    @ApiModelProperty(value = "是否支付")
    private int payment;
    @ApiModelProperty(value = "分享平台（分享平台  1-微博  2-微信  3-微信朋友圈  4-QQ  5-QQ空间）")
    private int sharePlatform;
    /**
     * 简报
     */
    @ApiModelProperty(value = "简报ID")
    private Integer briefId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "摘要")
    private String summary;
    @ApiModelProperty(value = "模版ID")
    private int templateId;
    @ApiModelProperty(value = "普通简报，党政简报等（头有差异）")
    private int type;
    @ApiModelProperty(value = "素材总数量")
    private int analysisNum;
    @ApiModelProperty(value = "进度")
    private Double schedule = 0.00;
    @ApiModelProperty(value = "进度")
    private String scheduleTips;
    @ApiModelProperty(value = "期数")
    private int briefSeq;
    @ApiModelProperty(value = "1：行业版，带期数；2：党政版，无期数的；3：摘要版")
    private Integer styleType;
    @ApiModelProperty(value = "1:生成简报  2：修改简报")
    private int clickType;
    @ApiModelProperty(value = "是否编辑(0-未编辑 1-已编辑)")
    private int isEdit = 0;

    @ApiModelProperty(value = "精确搜索")
    private String searchAccurate;
    @ApiModelProperty(value = "模糊搜索")
    private String searchFuzzy;
    @ApiModelProperty(value = "热度搜索")
    private String searchHot;
    @ApiModelProperty(value = "排行榜搜索")
    private String searchRanking;


    @ApiModelProperty(value = "竞品ID")
    private Integer pabId;
    @ApiModelProperty(value = "文件路径")
    private String filePath;
    @ApiModelProperty(value = "bar图片")
    private String barImg;


    @ApiModelProperty(value = "登录时间")
    private Integer loginLogTime;
    @ApiModelProperty(value = "1-手机号码登录  2-新浪微博登录  3-微信登录  4-QQ登录 5-京东万象")
    private String loginLogType;
    @ApiModelProperty(value = " 平台 1-微舆情WEB 2-微舆情客户端  3-微博微舆情WEB 4-微博微舆情H5")
    private String loginLogPlatform;
    @ApiModelProperty(value = "IP")
    private Integer loginLogIp;
    @ApiModelProperty(value = "数字ip")
    private String loginLogNumIp;
    @ApiModelProperty(value = "登录省")
    private String loginProvince;
    @ApiModelProperty(value = "登录市")
    private Integer loginCity;
    @ApiModelProperty(value = "运营商")
    private String loginIsp;


    @ApiModelProperty(value = "监测方案偏好ID")
    private Integer keywordPreferencesId;
    @ApiModelProperty(value = "")
    private Integer searchTimeQuantum; //timeType
    @ApiModelProperty(value = "")
    private Integer sortType; //sortCondition
    @ApiModelProperty(value = "")
    private Integer commentSwitch;
    @ApiModelProperty(value = "")
    private Integer contentPropertySwitch; //tendencyCondition
    @ApiModelProperty(value = "")
    private Integer batchOperateType;
    @ApiModelProperty(value = "")
    private Integer singleOperateType;
    @ApiModelProperty(value = "")
    private Integer sourceWebType; //sourceCondition
    @ApiModelProperty(value = "")
    private Integer filterWebsiteType;
    @ApiModelProperty(value = "")
    private String searchStartTime;
    @ApiModelProperty(value = "")
    private String searchEndTime;
    @ApiModelProperty(value = "")
    private Integer autoRefreshSwitch;
    @ApiModelProperty(value = "")
    private Integer autoRefreshInterval;
    @ApiModelProperty(value = "")
    private Integer summarySwitch;
    @ApiModelProperty(value = "")
    private Integer fontSize;
    @ApiModelProperty(value = "")
    private Integer newlstSelect;
    @ApiModelProperty(value = "")
    private Integer matchingType;
    @ApiModelProperty(value = "")
    private String direct;

    @ApiModelProperty(value = "")
    private String reportName;
    @ApiModelProperty(value = "")
    private int reportSwitch;
    @ApiModelProperty(value = "")
    private String loadFlag;
    @ApiModelProperty(value = "")
    private Date lastProcTime;





}
