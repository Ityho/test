package com.miduchina.wrd.dto.hotsportview;


import lombok.Data;

@Data
public class AbilityVO {

	private String serviceUnit;
	private StatisData wbData;
	private StatisData wxData;
	private StatisData pcData;
	private StatisData appData;


	private Long abilityTotal=0L;
	private Long abilityCB=0L;
	private Long abilityGX=0L;
	private Long abilityYD=0L;
	private Long abilityYX=0L;

	private Long differTotal;
	private Long differCB;
	private Long differGX;
	private Long differYD;
	private Long differYX;

	//附加字段
	private String name;
	private String property;

	public AbilityVO(){

	}

	public AbilityVO(String serviceUnit){
		super();
		this.serviceUnit = serviceUnit;
		wbData = new StatisData();
		wxData = new StatisData();
		pcData = new StatisData();
		appData = new StatisData();
	}
}
