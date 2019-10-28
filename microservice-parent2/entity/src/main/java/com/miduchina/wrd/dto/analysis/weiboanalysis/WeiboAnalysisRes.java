package com.miduchina.wrd.dto.analysis.weiboanalysis;
import com.miduchina.wrd.dto.analysis.AnalysisDto;
import com.miduchina.wrd.dto.user.UserDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2019 bejson.com
 */

/**
 * Auto-generated: 2019-03-29 13:48:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 * 任汉军
 */
@Data
public class WeiboAnalysisRes implements Serializable{
    private String code;
    private String message;
    private int page;
    private int pageSize;
    private int maxPage;
    private int totalCount;
    private int allCount;
    private WeiboTask task;
    private List<WeiboTask> taskList;
    private UserDto user;
    private List<AnalysisDto> solidifyList;




}
