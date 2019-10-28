package com.miduchina.wrd.dto.pay;

import lombok.Data;

@Data
public class MyProductDto implements java.io.Serializable {

    private String productFlag;
    private Integer keywordId;
    private Integer msgId;
    private Integer keywordPackageNum = 0;
    private Integer msgPackageNum = 0;
    private Integer keywordNum;
    private Integer msgNum;
    private String keywordPackageName;
    private String msgPackageName;
    private Float keywordPackagePrice = 0F;
    private Float msgPackagePrice = 0F;
    private Float totalPrice = 0F;
    private Integer keywordServeDays = 0;
    private Integer msgServeDays = 0;
    private Integer cartRecordId;

    /**
     * 监测方案+短信套餐
     */
    private Integer keywordMsgId;
    private Integer keywordMsgPackageNum = 0;
    private Integer keywordMsgNum;
    private String keywordMsgPackageName;
    private Float keywordMsgPackagePrice = 0F;
    private Integer keywordMsgServeDays = 0;

    public Float getTotalPrice() {
        totalPrice = keywordPackagePrice * keywordPackageNum + msgPackageNum * msgPackagePrice + keywordMsgPackageNum * keywordMsgPackagePrice;
        return totalPrice;
    }

    @Override
    public String toString() {
        return "MyProductDto [keywordId=" + keywordId + ", msgId=" + msgId
                + ", keywordPackageNum=" + keywordPackageNum
                + ", msgPackageNum=" + msgPackageNum + ", keywordNum="
                + keywordNum + ", msgNum=" + msgNum + ", keywordPackageName="
                + keywordPackageName + ", msgPackageName=" + msgPackageName
                + ", keywordPackagePrice=" + keywordPackagePrice
                + ", msgPackagePrice=" + msgPackagePrice + ", totalPrice="
                + totalPrice + ", keywordServeDays=" + keywordServeDays
                + ", msgServeDays=" + msgServeDays + ", cartRecordId="
                + cartRecordId + ", keywordMsgId=" + keywordMsgId
                + ", keywordMsgPackageNum=" + keywordMsgPackageNum
                + ", keywordMsgNum=" + keywordMsgNum
                + ", keywordMsgPackageName=" + keywordMsgPackageName
                + ", keywordMsgPackagePrice=" + keywordMsgPackagePrice
                + ", keywordMsgServeDays=" + keywordMsgServeDays + "]";
    }

}
