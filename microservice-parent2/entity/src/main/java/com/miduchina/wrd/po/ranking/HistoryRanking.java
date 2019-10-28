package com.miduchina.wrd.po.ranking;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: HistoryRanking
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 1:41 PM
 */
@Data
public class HistoryRanking {
    private Integer id;

    private String filePath;

    private String fileName;

    private Integer rankingType;

    private Integer month;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
