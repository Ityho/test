package com.miduchina.wrd.po.eventanalysis;

import lombok.Data;

import java.util.List;

@Data
public class EventTasksView {
	private String code;
	private String message;
	private int maxPage;
	private int page;
	private int pageSize;
	private int totalCount;
	private List<IncidentAnalysisTask> tasks;
	private IncidentAnalysisTask task;
	private List<IncidentAnalysisReport> reports;
}
