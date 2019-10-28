package com.miduchina.wrd.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-09 14:09.
 *
 * @author shitao
 */
@Data
public class UserExclusiveChannelDto {

    @ApiModelProperty(value = "主键")
    private Integer uecId;
    @ApiModelProperty(value = "'账号类型：1-新浪微博  2-微信  3-QQ  4-手机号码")
    private Integer userId;
    private String uecCode;
    private String uecDesc;
    private String uecQrcodeLogoPath;
    private Integer uecPersonal;
    private Integer uecChannel;
    private int status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
