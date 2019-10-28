package com.miduchina.wrd.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserOperateLogDto implements Serializable {


    @ApiModelProperty(value = "记录流水号")
    private Integer userOperateLogId;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "操作类型，10xx-系统操作，20xx-用户相关，30xx-监测相关，40xx-预警相关，50xx-全文搜索相关，60xx-事件分析相关，70xx-简报制作相关，80xx-订阅中心相关，90xx-支付相关")
    private Integer operateType;
    @ApiModelProperty(value = "用户发起操作的来源IP")
    private String sourceIp;
    @ApiModelProperty(value = "来源产品，1-微舆情WEB，2-微舆情客户端，3-微博微舆情WEB，4-微博微舆情H5")
    private Integer sourceProduct;
    @ApiModelProperty(value = "操作时间")
    private Date operateTime;
    @ApiModelProperty(value = "操作详情，由各模块根据需要记录的信息定义各自日志详情对象，转成json格式保存，方便扩展")
    private String operateDetail;


    private String userHead;

    private String nickname;

    private String mobile;


}
