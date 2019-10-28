package com.miduchina.wrd.po.h5;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther yho
 * @vreate 2019-07 14:50
 */
@Data
public class H5ShortUrl implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String shortUrl;
    private String sourceUrl;
    private Date createTime;
    private Date updateTime;
    private Integer status;
}
