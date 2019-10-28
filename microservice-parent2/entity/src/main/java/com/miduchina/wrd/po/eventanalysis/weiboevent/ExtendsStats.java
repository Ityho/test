package com.miduchina.wrd.po.eventanalysis.weiboevent;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExtendsStats implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<ExtendStats> extendStatsList = new ArrayList<>();
}
