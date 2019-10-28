package com.miduchina.wrd.dto.hotsportview;

import lombok.Data;

/**
 * 热搜查询关键词记录
 * Created by xujing on 2017/3/29 0029.
 */
@Data
public class HotKeywordRecord implements java.io.Serializable {

    private int type;//类型 1-一个关键词 2-二个关键词 3-三个关键词 4-到全网 5-到微博 6、创建全网分析 7、创建微博分析
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String filterKeyword1;
    private String filterKeyword2;
    private String filterKeyword3;


}
