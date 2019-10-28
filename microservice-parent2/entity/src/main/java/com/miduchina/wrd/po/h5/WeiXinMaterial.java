package com.miduchina.wrd.po.h5;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther yho
 * @vreate 2019-07 16:02
 */
@Data
public class WeiXinMaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String openId;
    private String imgUrl;
    private int status;
    private Date createTime;
    private Date updateTime;

    private int type;
}
