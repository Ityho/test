package com.miduchina.wrd.po.ranking;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: StatisData
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 1:41 PM
 */
@Data
public class StatisData {

    //附加字段
    private Integer differCBPCSum;// 0：<0.25  1:>=0.25&<0.3  2:>=0.3&<0.5 3:>=0.5
    private Integer differCBPCReferPresidentOriginNum;//
    private Integer differCBPCReferGovAffairsNum;//

    private Integer differCBPCSocietyPositiveEnergyNum;//

    private Integer differCBPCPolicyUnscrambleNum;//

    private Integer differCBPCReprintedIndex;//

    private Integer differCBPCAlexaValue;//

    private Integer differCBPCReferEntNum;//

    private Integer differCBPCRumourNum;//

    private Integer differCBPCDistortHistoryAndDiscreditHeroNum;//

    private Integer differCBPCTotalNum;//

    private Integer differCBPCOriginNum;//

    private Integer differCBPCRightNum;//


    private Integer differCBWXSum;// 0：<0.25  1:>=0.25&<0.3  2:>=0.3&<0.5 3:>=0.5

    private Integer differCBWXReferPresidentOriginNum;//

    private Integer differCBWXReferGovAffairsNum;//

    private Integer differCBWXSocietyPositiveEnergyNum;//

    private Integer differCBWXPolicyUnscrambleNum;//

    private Integer differCBWXReadsNum;//

    private Integer differCBWXReadsRate;//

    private Integer differCBWXReferEntNum;//

    private Integer differCBWXRumourNum;//

    private Integer differCBWXDistortHistoryAndDiscreditHeroNum;//

    private Integer differCBWXAttitudesNum;//

    private Integer differCBWXAttitudesRate;//

    private Integer differCBWXFollowersWXNum;//

    private Integer differCBWXOriginNum;//

    private Integer differCBWXTotalNum;//


    private Integer differCBAppSum;// 0：<0.25  1:>=0.25&<0.3  2:>=0.3&<0.5 3:>=0.5

    private Integer differCBAppReferPresidentOriginNum;//

    private Integer differCBAppReferGovAffairsNum;//

    private Integer differCBAppSocietyPositiveEnergyNum;//

    private Integer differCBAppPolicyUnscrambleNum;//

    private Integer differCBAppTotalDownloadNum;//

    private Integer differCBAppMonthDownloadNum;//

    private Integer differCBAPPReferEntNum;//

    private Integer differCBAPPRumourNum;//

    private Integer differCBAPPDistortHistoryAndDiscreditHeroNum;//

    private Integer differCBAppTotalNum;//

    private Integer differCBAppOriginNum;//


    private Integer differCBWBReadsNum;//

    private Integer differCBWBReadsRate;//

    private Integer differCBWBReferEntNum;//

    private Integer differCBWBRumourNum;//

    private Integer differCBWBDistortHistoryAndDiscreditHeroNum;//

    private Integer differCBWBCommentsNum;//

    private Integer differCBWBCommentsRate;//

    private Integer differCBWBAttitudesNum;//

    private Integer differCBWBAttitudesRate;//

    private Integer differCBWBRepostsNum;//

    private Integer differCBWBRepostsRate;//

    private Integer differCBWBFollowersWBNum;//


    private Integer differCBPCPer;//

    private Integer differCBWXPer;//

    private Integer differCBWBPer;//

    private Integer differCBAPPPer;//

    private double pcPer;//

    private double wxPer;//

    private double wbPer;//

    private double appPer;//

    //上个月的微信粉丝数

    private Long followersWXNum=0L;
    //上个月的微博粉丝数

    private Long followersWBNum=0L;


    private String area; // 地域


    private String serviceUnit; // 服务单位


    private String name; // 名称，微博-微博昵称、微信-公众号名称、网站-网站名称、app-app名称


    private String property;// 属性


    private Integer originType;// 来源


    private Long abilityTotal=0L;// 综合力

    private Long abilityCB=0L;// 传播力

    private Long abilityGX=0L;// 公信力

    private Long abilityYD=0L;// 引导力

    private Long abilityYX=0L;// 影响力



    private Integer id; //主键

    private Integer baseOriginId; //基础表id

    private Integer month; //月份

    private Integer referPresidentNum=0; //涉总书记稿件量

    private Integer referPresidentOriginNum=0; //涉总书记原创稿件(pc)

    private Integer presidentTitleNum=0; //总书记标题出现

    private Integer referOtherStandingSommitteeNum=0; //其他常委稿件量(pc)

    private Integer otherStandingSommitteeTitleNum=0; //其他常委标题出现

    private Integer referOtherStandingSommitteeOriginNum=0; //其他常委原创量(pc)

    private Integer referCentralLeadershipNum=0; //涉中央领导稿件量

    private Integer referCentralLeadershipOriginNum=0; //涉中央领导原创稿件量

    private Integer centralLeadershipTitleNum=0; //中央领导标题出现

    private Integer referGovAffairsNum=0; //涉时政稿件量

    private Integer societyPositiveEnergyNum=0; //社会暖新闻量

    private Integer policyUnscrambleNum=0; //政策解读类稿件量

    private Integer h5VideoCaricatureNum=0; //H5、短视频、漫画等

    private Integer referEntNum=0; //涉娱乐炒作类稿件量

    private Integer rumourNum=0; //谣言信息数

    private Integer distortHistoryAndDiscreditHeroNum=0; //歪曲党史国史、抹黑英雄人物等违规情况

    private Long totalNum=0L; //文章数(发稿量-pc、app)

    private Long repostsNum=0L; //转发数

    private Long commentsNum=0L; //评论数

    private Long attitudesNum=0L; //点赞数

    private Long readsNum=0L; //阅读数

    private Double repostsRate=0D; //转发率

    private Double commentsRate=0D; //评论率

    private Double attitudesRate=0D; //点赞率

    private Double readsRate=0D; //阅读率

    private Long followersNum=0L; //粉丝数

    private Long originNum=0L; //原创量(pc、app)

    private Integer alexaValue=0; //Alexa值(pc)

    private Long reprintedIndex=0L; //被转载指数(被转载表-pc)

    private Long totalDownloadNum=0L; //总下载量(app)

    private Long monthDownloadNum=0L; //月度下载增量(app)

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer praisedNum=0; //被表扬

    private Integer pushedNum=0; //被推送

    private Integer directiveImplementationNum=0; //指令落实

    private Integer trustDegree=0; //公众信任度（美誉度）

    private Integer punishedNum=0; //被通报、约谈、处罚

    private Integer reportedNum=0; //被举报情况

    private Integer rightNum=0; //被表扬


}