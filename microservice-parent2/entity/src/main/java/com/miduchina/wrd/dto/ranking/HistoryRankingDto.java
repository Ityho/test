package com.miduchina.wrd.dto.ranking;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: HistoryRankingDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 1:43 PM
 */
@Data
public class HistoryRankingDto {
    private Integer id;

    private String filePath;

    private String fileName;

    private Integer rankingType;

    private Integer month;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
