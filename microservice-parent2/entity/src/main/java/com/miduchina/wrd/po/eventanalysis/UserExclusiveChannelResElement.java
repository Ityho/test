package com.miduchina.wrd.po.eventanalysis;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserExclusiveChannelResElement implements Serializable {
	private static final long serialVersionUID = 1324888290686221701L;
	private Integer uecId;
	private Integer userId;
	private String uecCode;
	private String uecDesc;
	private String uecQrcodeLogoPath;
	private Integer uecPersonal;
	private Integer uecChannel;
	private Integer uecRewardFlag;
}
