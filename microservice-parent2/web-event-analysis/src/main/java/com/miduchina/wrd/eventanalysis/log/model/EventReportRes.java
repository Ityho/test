package com.miduchina.wrd.eventanalysis.log.model;

import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisReport;

public class EventReportRes extends BaseRes {
	private static final long serialVersionUID = 6794467802676867598L;
	private IncidentAnalysisReport report;

    public IncidentAnalysisReport getReport() {
        return report;
    }

    public void setReport(IncidentAnalysisReport report) {
        this.report = report;
    }
}
