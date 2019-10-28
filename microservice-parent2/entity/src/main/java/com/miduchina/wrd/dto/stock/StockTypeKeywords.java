package com.miduchina.wrd.dto.stock;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 股票总表
 * @author yff
 *
 */
@Data
public class StockTypeKeywords implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer stockId;	//主键
    private Integer categoryId;
    private String stockCode;	//股票代码
    private String stockName;	//股票名称
    private String stockLetter;		//股票首字母
    private Integer stockPlateId;	//股票板块外键
    private Integer type;			//类型
    private String companyAddress;	//公司地址
    private String companyName;		//公司名称
    private String province;		//省份
    private String city;			//城市
    private String logoUrl;			//股票180x180的logoUrl
    private String smallLogoUrl;	//存放50x50的logoUrl
    private Date createTime;		//入库时间
    private Date updateTime;		//更新时间
    private String plateName;
    private int number;
    private int difference;
    private double ratio;
    private String ratioFormat;
    private int status;
    private List<StockConcept> stockConceptList = new ArrayList<StockConcept>(); 	//股票概念
    private List<StockPlate> stockPlate = new ArrayList<StockPlate>();	//股票板块
    private Double ratioHot;//热度指数
    private int differenceRank;//（本周与上周）排名增量
    private String keyword; //关键词
    private String celebrityName;	//明星名称
    private String brandsName;	//汽车名称
    private int rankDifference;
    private double ratioHotDay;
    private String title;
    private String name;
    private int rank;
    private int page;
    private int listType;
    private int sequence;
    private String filterKeyword;
    private String shortName;
    private int numberDay;
    private String category;
}
