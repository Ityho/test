package com.miduchina.wrd.api.casebase.commons;

/**
 *operation_admin_wb中event_label事件类型
 */
public enum EventType {
	
	currentAffairs("1","时事"),
	social("2","社会"),
	sports("3","体育"),
	scienceAndTechnology("4","科技"),
	naturalDisasters("5","灾害"),
	entertainment("6","娱乐"),
	figure("7","人物"),
	financeAndEconomics("8","财经"),
	realEstate("9","房产"),
	financial("10","金融"),
	medical("11","医疗"),
	education("12","教育"),
	culture("13","文化"),
	car("14","汽车"),
	tourism("15","旅游"),
	governmentAffairs("16","政务"),
	law("17","法治"),
	accent("18","突发");
	
	private String code;
    private String desc;
    
    EventType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
     * @param code
     * @return
     */
    public static String getDesc(String code){
        for (EventType defaultStep :EventType.values()){
            if (defaultStep.getCode().equals(code)){
                return defaultStep.getDesc();
            }
        }
        return null;
    }

    /**
     * @param code
     * @return
     */
    public static EventType getEventType(String code){
        for (EventType defaultStep :EventType.values()){
            if (defaultStep.getCode() .equals(code)){
                return defaultStep;
            }
        }
        return null;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
