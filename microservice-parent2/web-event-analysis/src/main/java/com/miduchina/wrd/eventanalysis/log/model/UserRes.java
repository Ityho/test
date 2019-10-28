package com.miduchina.wrd.eventanalysis.log.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserRes extends BaseRes {
	
	private static final long serialVersionUID = 6794467802676867598L;
	private String sid;
	private boolean register;
	private UserResElement userInfo;
}
