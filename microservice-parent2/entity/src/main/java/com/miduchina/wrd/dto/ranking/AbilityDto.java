package com.miduchina.wrd.dto.ranking;

import com.miduchina.wrd.po.ranking.StatisData;
import lombok.Data;

/**
 * @version v1.0.0
 * @ClassName: AbilityDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 2:14 PM
 */
@Data
public class AbilityDto {
    private String serviceUnit;
    private StatisData wbData;
    private StatisData wxData;
    private StatisData pcData;
    private StatisData appData;

    //附加字段
    private Long abilityTotal=0L;// 综合力
    private Long abilityCB=0L;// 传播力
    private Long abilityGX=0L;// 公信力
    private Long abilityYD=0L;// 引导力
    private Long abilityYX=0L;// 影响力

    private Long differTotal;//综合力 0：<0.25  1:>=0.25&<0.3  2:>=0.3&<0.5 3:>=0.5
    private Long differCB;//传播力
    private Long differGX;//公信力
    private Long differYD;//引导力
    private Long differYX;//影响力

    public AbilityDto(){

    }

    public AbilityDto(String serviceUnit){
        super();
        this.serviceUnit = serviceUnit;
        wbData = new StatisData();
        wxData = new StatisData();
        pcData = new StatisData();
        appData = new StatisData();
    }

}
