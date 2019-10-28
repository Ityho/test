package com.miduchina.wrd.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class FavoriteNews implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer userId;
    private String newsSeId;
    private String newsTitle;
    private String newsSourceWebsite;
    private String newsSourceType;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String newsUrl;
    private Date newsPublishTime;
    private String newsSummary;
    private int newsSimilarCount;
    private int newsTendency;
    private String newsAuthor;
    private String newsProvince;
    private String newsTitleHash;
    private Date newsCaptureTime;
    private boolean addPro;
    private String newsforwarderContent;//原微博内容
    private String newsContent;//转发微博
    private int newsForwardNumber;//转发数
    private int newsComments;//评论数
    private int newsPraiseNum;//点赞数
}

