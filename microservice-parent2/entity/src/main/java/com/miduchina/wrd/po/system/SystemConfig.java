package com.miduchina.wrd.po.system;/**
 * Created by sty on 2019/4/25.
 */

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: SystemConfig
 * @Description: TODO
 * @author: sty
 * @date: 2019/4/25 6:39 PM
 */
@Data
public class SystemConfig {

    private Integer systemConfigId;
    private String cfgName;
    private String cfgValue;
    private Date createTime;
    private Date updateTime;
    private Integer status;
    private String remark;
}
