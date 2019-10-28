package com.miduchina.wrd.dto.echart;
import java.util.List;

public class IGroupResult implements java.io.Serializable {

    private String groupValue = "";

    private long total;// 整数值，原始数据

    private double calculatedValue;// 计算结果数值

    private List<IGroupResult> subGroups;

    private String cluster;

    public String published;

    public String authorOrCaptureWebsiteName;

    public double totalHot;//热度值


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
        return groupValue;
    }

    public void setGroupValue(String groupValue) {
        if (groupValue != null)
            this.groupValue = groupValue;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public double getCalculatedValue() {
        return calculatedValue;
    }

    public void setCalculatedValue(double calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public List<IGroupResult> getSubGroups() {
        return subGroups;
    }

    public void setSubGroups(List<IGroupResult> subGroups) {
        this.subGroups = subGroups;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAuthorOrCaptureWebsiteName() {
        return authorOrCaptureWebsiteName;
    }

    public void setAuthorOrCaptureWebsiteName(String authorOrCaptureWebsiteName) {
        this.authorOrCaptureWebsiteName = authorOrCaptureWebsiteName;
    }

    public double getTotalHot() {
        return totalHot;
    }

    public void setTotalHot(double totalHot) {
        this.totalHot = totalHot;
    }
}

