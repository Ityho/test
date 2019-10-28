package com.miduchina.wrd.dto.stock;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 股票板块表
 * @author yff
 *
 */
@Data
public class StockPlate implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer stockPlateId;	//主键
    private String plateName;		//板块名称
    private Integer parentId;		//父级ID
    private String plateCode;		//板块代码
    private Date createTime;		//入库时间
    private Date updateTime;		//更新时间
}
