package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.po.eventanalysis.MyErrorCodeConstant;
import com.miduchina.wrd.po.eventanalysis.weiboevent.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class WeiboExtendStatsView implements Serializable {
    private boolean newVersion=true;
    private List<Stats> statsList = new ArrayList<Stats>();
    private List<Stat> statList;
    private static final long serialVersionUID = 1;
    private String code;
    private String message;
    private ExtendStats extendStats;
    private List<IContentCommonNet> iContentCommonNetList;
    private List<IContentCommonNets> iContentCommonNetLists = new ArrayList<IContentCommonNets>();

    public WeiboExtendStatsView() {
        super();
    }

    public WeiboExtendStatsView(String code, ExtendStats extendStats) {
        super();
        this.code = code;
        this.message = MyErrorCodeConstant.getMsg(code);
        this.extendStats = extendStats;
    }

    public WeiboExtendStatsView(String code, String message, ExtendStats extendStats) {
        super();
        this.code = code;
        this.message = message;
        this.extendStats = extendStats;
    }

}

