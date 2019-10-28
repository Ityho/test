package com.miduchina.wrd.monitor.report.pojos;

import java.util.List;

/**
 * Created by 罗朝州 on 2016/7/25.
 */
public class IGroupResult {
    private String groupValue = "";
    private int total;
    private double calculatedValue;
    private List<IGroupResult> subGroups;

    public IGroupResult() {
    }

    public IGroupResult(String groupValue, int total) {
        this.setGroupValue(groupValue);
        this.total = total;
    }

    public IGroupResult(String groupValue, double calculatedValue) {
        this.setGroupValue(groupValue);
        this.calculatedValue = calculatedValue;
    }

    public String getGroupValue() {
        return this.groupValue;
    }

    public void setGroupValue(String groupValue) {
        if(groupValue != null) {
            this.groupValue = groupValue;
        }

    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getCalculatedValue() {
        return this.calculatedValue;
    }

    public void setCalculatedValue(double calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public List<IGroupResult> getSubGroups() {
        return this.subGroups;
    }

    public void setSubGroups(List<IGroupResult> subGroups) {
        this.subGroups = subGroups;
    }
}
