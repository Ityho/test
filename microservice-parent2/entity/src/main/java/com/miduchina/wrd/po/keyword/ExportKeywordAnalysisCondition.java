package com.miduchina.wrd.po.keyword;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-30 10:25.
 *
 * @author shitao
 */
@Data
public class ExportKeywordAnalysisCondition {


    /**
     * 条件ID
     */
    private int conditionId;
    /**
     * 任务唯一标识
     */
    private String analysisTaskTicket;
    /**
     * 用户标识
     */
    private int userId;
    /**
     * 监测方案id
     */
    private Integer keywordId;
    /**
     * 任务名称
     */
    private String keywordName;
    /**
     * 任务关键字
     */
    private String keyword;
    /**
     * 任务过滤关键字
     */
    private String filterKeyword;
    /**
     * 匹配方式
     */
    private int matchType;
    /**
     * 信息来源
     */
    private String origin;
    /**
     * 信息属性
     */
    private int otherAttribute;
    /**
     * 结果呈现
     */
    private int newlstSelect;
    /**
     * 相似文章
     */
    private int comblineflg;
    /**
     * 分析开始时间
     */
    private String analysisStartTime;
    /**
     * 分析结束时间
     */
    private String analysisEndTime;
    /**
     * 定向监测源
     */
    private String captureWebsit;
    /**
     * 定向监测省份
     */
    private String provinces;
    /**
     * 导出数量
     */
    private int exportCount;
    /**
     * 导出类型
     */
    private String exportType;
    /**
     * 状态
     */
    private int status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 单条导出信息IDS
     */
    private String checkedIds;
    /**
     * 1、收藏夹   2、监测方案  3、相似文章
     */
    private int favoritesOrMonitoring;
    /**
     * 相似文章
     */
    private String titleHs;
    /**
     * 来源网站
     */
    private String website;
    /**
     * 定向名
     */
    private String directSet;
    /**
     * 定向类型
     */
    private Integer directType;
    /**
     * 0 代表全部 多值勾选：1、标题 2、信息属性 3、原创/转发 4、地址 5、媒体名称 6、昵称 7、发布日期 8、媒体类型 9、原微博内容 10、认证类型
     * 11、地域 12、性别 13、正文切词 14、全文内容 15、作者ID 16、粉丝数 17、微博数 18、转 19、评 20、赞 21、阅 22、话题
     */
    private String dataType;

}
