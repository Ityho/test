package com.miduchina.wrd.po.researchpo;

import lombok.Data;

import java.util.Date;

/**
 * @auther yho
 * @vreate 2019-08 14:17
 */
@Data
public class Applicant {
    /**
     * id
     */
    private Integer id;
    /**
     * 项目归类
     */
    private String type;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 申请人
     */
    private String applicant;
    /**
     * 申请人单位
     */
    private String unit;
    /**
     * 申请日期
     */
    private Date date;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 地址
     */
    private String address;
}
