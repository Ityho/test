package com.miduchina.wrd.eventanalysis.log.model;

import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class EventTaskRes extends BaseRes {
	private static final long serialVersionUID = 6794467802676867598L;
	private IncidentAnalysisTask task;
}
