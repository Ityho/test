package com.miduchina.wrd.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-10 14:21.
 *
 * @author shitao
 */
@Data
public class ClientSerialDto {

    @ApiModelProperty(value = "用户安装序列号")
    private String clientSerialId;
    @ApiModelProperty(value = "用户ID")
    private int userId;
    @ApiModelProperty(value = "软件分发渠道")
    private String channel;
    @ApiModelProperty(value = "软件平台（iPhone，android）")
    private String platform;
    @ApiModelProperty(value = "软件类型（内部使用1，2，3）")
    private int softType;
    @ApiModelProperty(value = "软件版本号（1.0.0）")
    private String version;
    @ApiModelProperty(value = "用户安装终端设备唯一ID")
    private String deviceId;
    @ApiModelProperty(value = "推送用TOKEN")
    private String deviceToken;
    @ApiModelProperty(value = "设备操作系统")
    private String deviceOs;
    @ApiModelProperty(value = "设备型号")
    private String deviceModel;
    @ApiModelProperty(value = "设备标签（1-华为，2-小米，3-待扩展）")
    private int deviceLabel;
    @ApiModelProperty(value = "冻结状态（0-冻结，1-激活）")
    private int freeze;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "推送提示数字")
    private int badge;
    @ApiModelProperty(value = "安全码")
    private String securityKey;
    @ApiModelProperty(value = "用户redis缓存中的sid")
    private String sid;
    @ApiModelProperty(value = "如果是子账号，对应子账号Id")
    private Integer subUserId;

}
