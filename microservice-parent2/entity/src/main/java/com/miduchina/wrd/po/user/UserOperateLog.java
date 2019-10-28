package com.miduchina.wrd.po.user;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserExtraInfo
 * @Description: 用户操作日志
 * @author: sty
 * @date: 2019/7/18 10:12 AM
 */
@Data
public class UserOperateLog {

    /**
     * id
     */
    private Integer userOperateLogId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 操作类型，10xx-系统操作，20xx-用户相关，30xx-监测相关，40xx-预警相关，50xx-全文搜索相关，60xx-事件分析相关，70xx-简报制作相关，80xx-订阅中心相关，90xx-支付相关
     */
    private Integer operateType;
    /**
     * 用户发起操作的来源IP
     */
    private String sourceIp;
    /**
     * 来源产品，1-微舆情WEB，2-微舆情客户端，3-微博微舆情WEB，4-微博微舆情H5
     */
    private Integer sourceProduct;
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 操作详情，由各模块根据需要记录的信息定义各自日志详情对象，转成json格式保存，方便扩展
     */
    private String operateDetail;


    //操作详情
    private String userHead;

    private String nickname;

    private String mobile;


}
