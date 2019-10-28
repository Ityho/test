package com.miduchina.wrd.po.eventanalysis;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserExclusiveChannelRes extends BaseRes {
	private static final long serialVersionUID = -5429084719165409790L;
	private UserExclusiveChannelResElement userExclusiveChannel;
}
