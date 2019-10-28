package com.miduchina.wrd.po.user;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-10 14:28.
 *
 * @author shitao
 */
@Data
public class ClientSerial {

    /**
     * 用户安装序列号
     */
    private String clientSerialId;
    /**
     * 用户ID
     */
    private int userId;
    /**
     * 软件分发渠道
     */
    private String channel;
    /**
     * 软件平台（iPhone，android）
     */
    private String platform;
    /**
     * 软件类型（内部使用1，2，3）
     */
    private int softType;
    /**
     * 软件版本号（1.0.0）
     */
    private String version;
    /**
     * 用户安装终端设备唯一ID
     */
    private String deviceId;
    /**
     * 推送用TOKEN
     */
    private String deviceToken;
    /**
     * 设备操作系统
     */
    private String deviceOs;
    /**
     * 设备型号
     */
    private String deviceModel;
    /**
     * 设备标签（1-华为，2-小米，3-待扩展）
     */
    private int deviceLabel;
    /**
     * 冻结状态（0-冻结，1-激活）
     */
    private int freeze;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 推送提示数字
     */
    private int badge;
    /**
     * 安全码
     */
    private String securityKey;
    /**
     * 用户redis缓存中的sid
     */
    private String sid;
    /**
     * 如果是子账号，对应子账号Id
     */
    private Integer subUserId;
}
