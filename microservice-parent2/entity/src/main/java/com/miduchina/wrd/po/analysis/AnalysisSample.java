package com.miduchina.wrd.po.analysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-14 20:26.
 *
 * @author shitao
 */
@Data
public class AnalysisSample {

    /**
     * ID
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 链接url
     */
    private String url;
    /**
     * 分享链接url
     */
    private String shareUrl;
    /**
     * 案例类型（1:竞品分析，2:全网事件分析，3:微博事件分析，4:微博传播分析）
     */
    private Integer sampleType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 有效状态（1：有效 0 无效）
     */
    private Integer status;

}
