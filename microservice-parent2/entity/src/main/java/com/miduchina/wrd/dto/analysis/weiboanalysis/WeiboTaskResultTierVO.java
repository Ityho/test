package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.user.User;

import java.util.List;

/**
 * 转发层级VO
 * 
 * @author liym
 */
public class WeiboTaskResultTierVO {
	private Tier tierInfo;
	private List<UserDto> userInfoList;

	public Tier getTierInfo() {
		return tierInfo;
	}

	public void setTierInfo(Tier tierInfo) {
		this.tierInfo = tierInfo;
	}

	public List<UserDto> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserDto> userInfoList) {
		this.userInfoList = userInfoList;
	}
}
