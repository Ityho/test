package com.miduchina.wrd.eventanalysis.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class MapList implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private int value;
}
