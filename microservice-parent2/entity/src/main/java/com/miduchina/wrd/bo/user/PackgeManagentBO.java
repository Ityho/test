package com.miduchina.wrd.bo.user;/**
 * Created by sty on 2019/7/17.
 */

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: PackgeManagentBO
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/17 2:36 PM
 */
@Data
public class PackgeManagentBO {
    private Integer pmId;
    private Integer userId;
    private Integer packgeId;
    private Date updataTime;
    private Date creatTime;
    private Date expirationTime;
    private Integer PackgeStatus;
}
