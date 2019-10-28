package com.miduchina.wrd.po.eventanalysis;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRes implements Serializable {
	private static final long serialVersionUID = 54522908240780361L;
	private String code;
	private String message;
	private String needRealName;
}
