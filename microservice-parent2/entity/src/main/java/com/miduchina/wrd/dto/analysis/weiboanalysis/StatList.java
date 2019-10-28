package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.po.eventanalysis.weiboevent.Stat;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "statList")
@Data
public class StatList implements Serializable{

	private static final long serialVersionUID = 1L;
	private Stat stat = new Stat();
	private List<StatList> StatListList = new ArrayList<StatList>();
}
