package com.miduchina.wrd.dto.home;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: shitao
 * @date: 2019.07.18
 */

@Data
public class NewsContenDto implements Serializable {

    private String newsId;
    private String newsTitleHash;
    private String title;
    private int similarCount;
    private int sensitive;
    private String summary;
    private String content;
    private String sourceUrl;
    private String author;
    private String keyword;
    private String originalType;
    private String sourceWebsite;
    private Date publishTime;

}

