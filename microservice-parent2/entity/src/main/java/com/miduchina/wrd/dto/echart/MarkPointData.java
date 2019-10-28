package com.miduchina.wrd.dto.echart;


public class MarkPointData implements java.io.Serializable{
    private Object name;
    private Object value;
    private Object xAxis;
    private Object yAxis;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getxAxis() {
        return xAxis;
    }

    public void setxAxis(Object xAxis) {
        this.xAxis = xAxis;
    }

    public Object getyAxis() {
        return yAxis;
    }

    public void setyAxis(Object yAxis) {
        this.yAxis = yAxis;
    }
}
